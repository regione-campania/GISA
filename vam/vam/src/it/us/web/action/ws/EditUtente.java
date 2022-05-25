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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;

public class EditUtente extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@Override
	public void execute() throws Exception
	{
		PrintWriter pw = res.getWriter();
		
		try
		{
			//Creazione del bean da modificare
			String username  = stringaFromRequest("oldUsername");
			
			ArrayList<SuperUtente> sups = (ArrayList<SuperUtente>) persistence.createCriteria( SuperUtente.class )
				.add( Restrictions.eq( "username", username ) )
				.add( Restrictions.isNull( "trashedDate") ).list();
			
			
			SuperUtente su = sups.get( 0 );
			BeanUtils.populate( su, req.getParameterMap() );
			su.setModified( new Timestamp( System.currentTimeMillis() ) );
			persistence.update( su );
	
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
	
			//disattivo tutti i vecchi utenti
			for( BUtente bu: su.getUtenti() )
			{
				bu.setEnabled( false );
				bu.setEnabledDate( su.getModified() );
			}
			
			//attivo gli utenti giusti (le cliniche giuste)
			if( cliniche.size() > 0 )
			{
				for( Clinica cl: cliniche )
				{
					boolean clinicaTrovata = false;
					for( BUtente bu: su.getUtenti() )
					{
						if( bu.getClinica() != null && bu.getClinica().getId() == cl.getId() )
						{
							clinicaTrovata = true;
							BeanUtils.populate( bu, req.getParameterMap() );
							bu.setModified( su.getEntered() );
							persistence.update( bu );
	
							Permessi.removeFromAllCategory( bu );
							Permessi.add2category( bRuolo.getRuolo(), bu );
						}
					}
					
					if( !clinicaTrovata )
					{
						BUtente newUser = new BUtente();
						BeanUtils.populate( newUser, req.getParameterMap() );
						newUser.setEntered( su.getModified() );
						newUser.setSuperutente( su );
						newUser.setClinica( cl );
						persistence.insert( newUser );
	
						if( newUser.getId() > 0 )
				 		{
				 			Permessi.startupUser( newUser );
				 			Permessi.add2category( bRuolo.getRuolo(), newUser );
				 		}
					}
				}
			}
			else //nessuna clinica associata
			{
				boolean utenteNoClinica = false;
				for( BUtente bu: su.getUtenti() )//cerco se c'era già un utente associato senza clinica
				{
					if( bu.getClinica() == null )
					{
						utenteNoClinica = true;
						bu.setEnabled( true );
						persistence.update( bu );
						
						Permessi.removeFromAllCategory( bu );
						Permessi.add2category( bRuolo.getRuolo(), bu );
					}
				}
				
				if( !utenteNoClinica )
				{
					BUtente newUser = new BUtente();
					BeanUtils.populate( newUser, req.getParameterMap() );
					newUser.setEntered( su.getModified() );
					newUser.setSuperutente( su );
					persistence.insert( newUser );
					
					if( newUser.getId() > 0 )
			 		{
			 			Permessi.startupUser( newUser );
			 			Permessi.add2category( bRuolo.getRuolo(), newUser );
			 		}
				}
			}
			
			persistence.commit();
			
			redirectTo("ws.AggiornamentoFunzioniConcesseEditUtente.us?idSuperutente=" + su.getId(), req, res);
		

		
//		ArrayList<BUtente> a = (ArrayList<BUtente>)persistence.getNamedQuery("GetUtente").setString("username",username).list();
//		BUtente editUser = a.get(0);
//		BeanUtils.populate( editUser, req.getParameterMap() );
//		editUser.setModified( new Timestamp( System.currentTimeMillis() ) );
//		
//		//GESTIONE CLINICA
//		//Se la clinica è stata assegnata
//		if(interoFromRequest("idClinica")>0)
//		{
//			editUser.setClinica( (Clinica)persistence.find(Clinica.class, interoFromRequest("idClinica") ));
//		}
//		//Se la clinica non è stata assegnata rimuovo l'eventuale precedente
//		else
//		{
//			editUser.setClinica( null );
//		}	
//		
//		//GESTIONE RUOLO
//		BRuolo bRuolo = null;
//		//Se il ruolo è stato assegnato
//		if(interoFromRequest("ruolo")>0)
//		{
//			bRuolo = RuoloDAO.getRuoloById(interoFromRequest("ruolo"));
//			Permessi.add2category( bRuolo.getRuolo(), editUser );
//		}
//		//Se il ruolo non è stato assegnato rimuovo l'eventuale precedente
//		else
//		{
//			Permessi.removeFromAllCategory(editUser);
//		}	
//			
//		//Validazione del bean da modificare
//		String validationError = validate( editUser );
//		
//		if( validationError == null )
//		{
//	 		persistence.update(editUser);
//	 		persistence.commit();
//	 		
//	 		if( editUser.getId() > 0 )
//	 		{
//	 			//Permessi.startupUser( editUser );
//				pw.write("OK");
//				pw.flush();
//	 		}
//	 		else
//	 		{
//				pw.write("KO");
//				pw.flush();
//	 		}
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
		
		validaBean( newUser,null );

//Il controllo il GUC lo fa lato js e mi passa solo il parametro password
		//	if( ret == null )
		//	{
		//		String password = stringaFromRequest( "password_1" );
		//	String confirmPassword = stringaFromRequest( "password_2" );
		//	if( password == null || !password.equals( confirmPassword ) )
		//	{
		//		ret = "\nla password e la sua conferma non coincidono";
		//	}
		//	else if( password.length() <= 8 )
		//	{
		//	ret = "\nlunghezza minima della password 8 caratteri";
		//}
		//else
		//{
		//	newUser.setPassword( password );
		//}
		//	}

//		La classe viene invocata dal GUC, pertanto non c'è un utente in sessione, ovvero quello loggato in vam
//		if( ret == null )
//		{
		//			newUser.setEntered( new Timestamp( System.currentTimeMillis() ) );
		//newUser.setEnteredBy( (int)utente.getId() );
		//newUser.setModified( newUser.getEntered() );
		//newUser.setModifiedBy( (int)utente.getId() );
		//}
		
		return ret;
	}

}
