/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ws;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.ValidationBeanException;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.hibernate.criterion.Restrictions;

public class DisableAllUtenti extends GenericAction
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
			ArrayList<SuperUtente> sups = (ArrayList<SuperUtente>) persistence.findAll(SuperUtente.class);
			for( SuperUtente superUtente : sups)
			{
				superUtente.setEnabled(false);
				superUtente.setEnabledDate( new Timestamp( System.currentTimeMillis() ) );
				persistence.update( superUtente );
			}
			persistence.commit();
			pw.write("OK");
			pw.flush();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			pw.write("OK");
			pw.flush();
			persistence.rollBack();
		}
		
//		//Creazione del bean da abilitare/disabilitare
//		String username  = stringaFromRequest("username");
//		ArrayList<BUtente> arrayUser = (ArrayList<BUtente>)persistence.getNamedQuery("GetUtente").setString("username",username).list();
//		if(!arrayUser.isEmpty())
//		{	
//			BUtente enableUser  = arrayUser.get(0);
//			enableUser.setEnabled(booleanoFromRequest("enabled"));
//			enableUser.setEnabledDate( new Timestamp( System.currentTimeMillis() ) );
//		
//			//Validazione del bean da abilitare
//			String validationError = validate( enableUser );
//		
//			if( validationError == null )
//			{
//				persistence.update(enableUser);
//				persistence.commit();
//	 		
//				if( enableUser.getId() > 0 )
//				{
//					Permessi.startupUser( enableUser );
//					pw.write("OK");
//					pw.flush();
//				}
//				else
//				{
//					pw.write("KO");
//					pw.flush();
//				}
//			
//			}
//			else
//			{
//				pw.write("KO");
//				pw.flush();
//			}
//		}
//		else
//		{
//			pw.write("KO");
//			pw.flush();
//		}
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
