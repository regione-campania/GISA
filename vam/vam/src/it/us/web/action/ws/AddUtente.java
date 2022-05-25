/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ws;

import it.us.web.action.GenericAction;
import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Clinica;
import it.us.web.dao.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.permessi.Permessi;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class AddUtente extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		PrintWriter pw = res.getWriter();
		
		try
		{
			SuperUtente su = new SuperUtente();
			BeanUtils.populate( su, req.getParameterMap() );
			su.setEntered( new Timestamp( System.currentTimeMillis() ) );
			su.setModified( su.getEntered() );
			persistence.insert( su );
			
			HashSet<Clinica> cliniche = new HashSet<Clinica>(); 
			String[] clinicheP = req.getParameterValues("cliniche");
			if(clinicheP!=null && clinicheP.length>0)
			{
				
				for(String c:clinicheP)
				{
					cliniche.add((Clinica)persistence.find(Clinica.class, Integer.parseInt(c) ));
				}
			}
			BRuolo bRuolo = RuoloDAO.getRuoloById(interoFromRequest("ruolo"));

			if( cliniche.size() > 0 )
			{
				for( Clinica c: cliniche )
				{
					BUtente newUser = new BUtente();
					BeanUtils.populate( newUser, req.getParameterMap() );
					newUser.setEntered( su.getEntered() );
					newUser.setSuperutente( su );
					newUser.setClinica( c );
					persistence.insert( newUser );

					if( newUser.getId() > 0 )
			 		{
			 			Permessi.startupUser( newUser );
			 			Permessi.add2category( bRuolo.getRuolo(), newUser );
			 		}
				}
			}
			else
			{
				BUtente newUser = new BUtente();
				BeanUtils.populate( newUser, req.getParameterMap() );
				newUser.setEntered( su.getEntered() );
				newUser.setSuperutente( su );
				persistence.insert( newUser );
				
				if( newUser.getId() > 0 )
		 		{
		 			Permessi.startupUser( newUser );
		 			Permessi.add2category( bRuolo.getRuolo(), newUser );
		 		}
			}
			
			persistence.commit();
			
			redirectTo("ws.AggiornamentoFunzioniConcesseAddUtente.us?idSuperutente=" + su.getId(), req, res);

//		//Creazione del bean da inserire
//		BUtente newUser = new BUtente();
//		BeanUtils.populate( newUser, req.getParameterMap() );
//		newUser.setEntered( new Timestamp( System.currentTimeMillis() ) );
//		newUser.setPassword( stringaFromRequest("password") );
//		
//		//GESTIONE CLINICA
//		//Se la clinica è stata assegnata
//		if(interoFromRequest("idClinica")>0)
//		{
//			newUser.setClinica( (Clinica)persistence.find(Clinica.class, interoFromRequest("idClinica") ));
//		}
//		//Se la clinica non è stata assegnata rimuovo l'eventuale precedente
//		else
//		{
//			newUser.setClinica( null );
//		}
		
		
		//Validazione del bean da inserire
//		String validationError = validate( newUser );
//		
//		if( validationError == null )
//		{
//	 		persistence.insert( newUser );
//	 		persistence.commit();
//	 		
//	 		if( newUser.getId() > 0 )
//	 		{
//	 			Permessi.startupUser( newUser );
//	 			
//				pw.write("OK");
//				pw.flush();
//	 		}
//	 		else
//	 		{
//				pw.write("KO");
//				pw.flush();
//	 		}
//	 		
//			//GESTIONE RUOLO
//			BRuolo bRuolo = RuoloDAO.getRuoloById(interoFromRequest("ruolo"));
//			Permessi.add2category( bRuolo.getRuolo(), newUser );
//			
//		}
//		else
//		{
//			pw.write("KO");
//			pw.flush();
//		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			pw.write("KO");
			pw.flush();
		}

	}
	
	/**
	 * controlla che i valori passati siano corretti e imposta i dati di sistema 
	 * @param newUser
	 * @return
	 * @throws ValidationBeanException 
	 */
	private String validate(BUtente newUser) throws ValidationBeanException
	{
		String ret = null;
		
		validaBean( newUser, null );

		//Il controllo il GUC lo fa lato js e mi passa solo il parametro password
//		if( ret == null )
//		{
//			String password = stringaFromRequest( "password_1" );
//			String confirmPassword = stringaFromRequest( "password_2" );
//			if( password == null || !password.equals( confirmPassword ) )
//			{
//				ret = "\nla password e la sua conferma non coincidono";
//			}
//			else if( password.length() <= 8 )
//			{
//				ret = "\nlunghezza minima della password 8 caratteri";
//			}
//			else
//			{
//				newUser.setPassword( Md5.encrypt( password ) );
//			}
//		}
		
//		La classe viene invocata dal GUC, pertanto non c'è un utente in sessione, ovvero quello loggato in vam
//		if( ret == null )
//		{
//			newUser.setEntered( new Timestamp( System.currentTimeMillis() ) );
//			newUser.setEnteredBy( (int)utente.getId() );
//			newUser.setModified( newUser.getEntered() );
//			newUser.setModifiedBy( (int)utente.getId() );
//		}
//		
		return ret;
	}

}
