/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.servlet;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.action.login.Ballot;
import it.us.web.bean.BUtente;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ActionNotValidException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.exceptions.ValidationBeanExceptionRedirect;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.MyDoubleConverter;
import it.us.web.util.MyIntegerConverter;
import it.us.web.util.properties.Application;
import it.us.web.util.properties.Message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.exception.JDBCConnectionException;

public class DispatcherCentric extends HttpServlet
{
	static
	{
		ConvertUtils.register( new DateUtils.MyUtilDateConverter(), Date.class );
		ConvertUtils.register( new FloatConverter()   ,  Float.class );
		ConvertUtils.register( new FloatConverter()   ,  Float.TYPE );
		ConvertUtils.register( new MyDoubleConverter(),  Double.class);
		ConvertUtils.register( new MyIntegerConverter(),   Integer.class);
	}

	private static final long serialVersionUID = -8397394451535054535L;
	private static final String actionPackage = "it.us.web.action.login.";

	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws
			ServletException,
			IOException
	{
		
		String action = parseAction( req );
		
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BUtente utente = null;
		Date dataStart = null;
		long memFreeStart = 0;
		
		try
		{
			memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
			dataStart 		= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
		}
		catch(Exception e)
		{
			
		}
		
		if (action.indexOf("Ballot")<0 && action.indexOf("Index")<0)
		{
			System.out.println("Modulo in esecuzione: " + action + " - utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		}
		
		Action act = null;
		
		try
		{
			boolean fromSca = getServletConfig().getInitParameter("") != null ? true : false;
			req.setAttribute("isFromSca",fromSca);
			
			
			act = getActionClass( action );
			
			act.setPersistence( PersistenceFactory.getPersistence() );
			aggiornaConnessioneApertaSessione(req);
			
			act.startup( req, res, getServletContext() );
			req.setAttribute("system", "vam");
			act.can();
			act.setSegnalibroDocumentazione();
			BUtente utenteTemp = (BUtente)req.getSession().getAttribute("utente");
			
			if (((action.indexOf("Ballot")>=0 || action.indexOf("Index")>=0) && utente!=null) && !getServletContext().getAttribute("dbMode").equals("slave"))
			{
				act.getPersistence().update(utente);
			}
			
			
			act.execute();
			if (!getServletContext().getAttribute("dbMode").equals("slave") && 
					Application.get("abilitaStoricoOperazioniUtente")!=null &&
	    			Application.get("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ) {
				
				if (action.equals("login.Ballot"))
					 utenteTemp = (BUtente)req.getSession().getAttribute("utente");
				act.logOperazioniUtente(utenteTemp, action, act.getDescrizione(), req.getRemoteAddr());
				PersistenceFactory.closePersistence( act.getPersistence(), true );
				aggiornaConnessioneChiusaSessione(req);
				if(act.getConnectionBdu()!=null)
				{
					act.getConnectionBdu().close();
					aggiornaConnessioneChiusaSessione(req);
				}
				if (act.getConnection()!=null)
				{
					act.getConnection().close();
					aggiornaConnessioneChiusaSessione(req);
				}
			}
		}
		catch (ValidationBeanException e)
		{
			e.printStackTrace();

			//PersistenceFactory.closePersistence( act.getPersistence(), false );
			
			req.setAttribute( "errore", "Errore: "+e.getMessage()+"\n" );
			try
			{
				if(e.getActionToGo()!=null)
					((GenericAction)act).goToAction( e.getActionToGo(), req, res );
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		catch (ValidationBeanExceptionRedirect e)
		{
			e.printStackTrace();

			PersistenceFactory.closePersistence( act.getPersistence(), false );
			aggiornaConnessioneChiusaSessione(req);
			
			req.setAttribute( "errore", "Errore: "+e.getMessage()+"\n" );
			try
			{
				if(e.getUrl()!=null && !e.getUrl().equals(""))
					GenericAction.redirectTo(e.getUrl(), req, res);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();

			PersistenceFactory.closePersistence( act.getPersistence(), false );
			aggiornaConnessioneChiusaSessione(req);
			
			
			if( e instanceof JDBCConnectionException )
				req.setAttribute( "errore", "Errore: "+e.getMessage()+"\n.Si prega di riprovare." );
			else
				req.setAttribute( "errore", "Errore: "+e.getMessage()+"\n" );
			if( e instanceof NotLoggedException || req.getSession().getAttribute("utente")==null )
			{
				try
				{
					GenericAction.redirectTo("Index.us", req, res);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				GenericAction.gotoPage( "/jsp/errore/errore.jsp", req, res );
			}
		}
		finally
		{
			if(act.getConnectionBdu()!=null)
			{
				try 
				{
					act.getConnectionBdu().close();
				} 
				catch (SQLException e) 
				{
					System.out.println("Non sono riuscito a chiudere la connessione in bdu");
					e.printStackTrace();
				}
				aggiornaConnessioneChiusaSessione(req);
			}
			if (act.getConnection()!=null)
			{
				try 
				{
					act.getConnection().close();
				} 
				catch (SQLException e) 
				{
					System.out.println("Non sono riuscito a chiudere la connessione in vam");
					e.printStackTrace();
				}
				aggiornaConnessioneChiusaSessione(req);
			}
		}
		
		if (!(act instanceof Ballot ))
		{
			long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
			Date dataEnd  	= new Date();
			utente = ((BUtente) req.getSession().getAttribute( "utente" ));
			long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		
			System.out.println("Modulo eseguito: " + action + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		}
	}
	
	private String parseAction(HttpServletRequest req)
	{
		String action = null;
		String temp = req.getParameter("command");
		if( temp != null )
		{
			action = temp;
		}
		return action;
	}
	
	@SuppressWarnings("unchecked")
	private Action getActionClass( String action ) throws ActionNotValidException
	{
		Class c = null;
		Action act = null;
		
		try
		{
			c = Class.forName( actionPackage + action );
			act = (Action) c.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ActionNotValidException( Message.getSmart( "azione_sconosciuta" ) );
		}
		
		return act;
	}
	
	private void aggiornaConnessioneApertaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	private void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
			numConnessioniDb = numConnessioniDb-1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			if(numConnessioniDb==0)
				req.getSession().setAttribute("timeConnOpen",     null);
		}
	}
}
