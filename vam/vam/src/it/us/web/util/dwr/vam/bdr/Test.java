/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.dwr.vam.bdr;


import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.UtenteDAO;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.UnhandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test 
{
	Logger logger = LoggerFactory.getLogger( Test.class );
	
	public boolean registrazioneInserita(String microchip, int specie, int idTipoRegBdrDaInserire, HttpServletRequest req)
	{
		Connection connection = null;
		try
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			Integer idTipoUltimaRegistrazione = null;
			if(specie==Specie.CANE || specie==Specie.GATTO)
				idTipoUltimaRegistrazione = CaninaRemoteUtil.getIdTipoUltimaRegistrazione(microchip, connection,req);
			//else
				//idTipoUltimaRegistrazione = SinantropoUtil.getIdTipoUltimaRegistrazione(microchip);
			
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
			//Caso iscrizione anagrafe
			if(idTipoUltimaRegistrazione==null)
				return false;
			else
			{
				//Dopo il "ritrovamento non denunciato" viene fatto in automatico il ritorno a proprietario in bdu
				if(idTipoRegBdrDaInserire==IdOperazioniInBdr.ritrovamentoSmarrNonDenunciato)
					return (idTipoUltimaRegistrazione==idTipoRegBdrDaInserire || idTipoUltimaRegistrazione==IdOperazioniInBdr.ritornoProprietario);
				else	
					return idTipoUltimaRegistrazione==idTipoRegBdrDaInserire;
			}
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(SQLException ex1)
		{
			ex1.printStackTrace();
			return false;
		}
		catch(NamingException ex2)
		{
			ex2.printStackTrace();
			return false;
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		
	}
	
	
	public boolean iscrizioneInserita(String microchip, int idUtente, HttpServletRequest req)
	{
		boolean toReturn = false;
		Connection connection = null, connectionVam = null;
		try
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			BUtente utente = UtenteDAO.getUtente(idUtente);
			if(CaninaRemoteUtil.findCane(microchip, new ServicesStatus(), connection,req)==null && FelinaRemoteUtil.findGatto(microchip, new ServicesStatus(), connection,req)==null)
				toReturn = false;
			else
				toReturn = true;
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
			connectionVam.close();
			aggiornaConnessioneChiusaSessione(req);
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(SQLException ex1)
		{
			ex1.printStackTrace();
			return false;
		}
		catch(NamingException ex2)
		{
			ex2.printStackTrace();
			return false;
		}
		catch(UnhandledException ex3)
		{
			ex3.printStackTrace();
			return false;
		}
		catch(Exception ex4)
		{
			ex4.printStackTrace();
			return false;
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		
		return toReturn;
		
	}
	
	public void aggiornaConnessioneApertaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	public void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
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