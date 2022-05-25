/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import it.us.web.action.GenericAction;
import it.us.web.dao.UtenteDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;

public class Login extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		String erroreSpid = null;
		
		JSONObject jsonObj = ApplicationProperties.checkBrowser(req.getHeader("User-Agent"));
		if (jsonObj != null) 
		{
			if (jsonObj.getString("esito").equals("1")) 
				erroreSpid = jsonObj.getString("msg");
		}
		
		if( utenteGuc != null )
		{
			Enumeration<String> e = session.getAttributeNames();
			while( e.hasMoreElements() )
			{
				session.removeAttribute( e.nextElement() );
			}
			utenteGuc = null;
		}
		
		String un = req.getParameter( "utente" );
		String pw = req.getParameter( "password" );
		
		//Modifica spid INIZIO
		String cf_spid = req.getParameter( "cf_spid" );
		String tk_spid = req.getParameter( "tk_spid" );
		System.out.println("Login sca - cf_spid: " + cf_spid);
		System.out.println("Login sca - tk_spid: " + tk_spid);
		
		if((cf_spid==null || cf_spid.equals("") || cf_spid.equals("null")) && !Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
		{
			System.out.println("Login sca - cf_spid e NULL. Verifica accesso sara eseguito secondo la modalita standard.");
			utenteGuc = UtenteDAO.authenticateUnifiedAccess(un, pw, db);
		}
		else
		{
			System.out.println("Login sca - cf_spid e NOT NULL. Verifica accesso sara eseguito secondo la modalita SPID.");
			try
			{
				utenteGuc = UtenteDAO.authenticateUnifiedAccessSpid(cf_spid, tk_spid, db, false);
			}
			catch(Exception e)
			{
				erroreSpid = e.getMessage();
			}
		}
		if(erroreSpid==null)
		{
		//Modifica spid FINE
		
		HashMap<String, HttpSession> utenti = (HashMap<String, HttpSession>) session.getServletContext().getAttribute("utenti");
		
		int interval = Integer.parseInt(ApplicationProperties.getProperty("loginInterval"));
		int loginAttempt = Integer.parseInt(ApplicationProperties.getProperty("loginAttempt"));


		////Prima recupero il numero di tentativi e l'ultimo timestamp poi scrivo
		boolean isBlocked = UtenteDAO.getAttemptsByUser(un, interval, loginAttempt);
		
			
			if( utenteGuc == null)
			{
				setErrore("L'utente non risulta registrato all'ecosistema GISA");
				GenericAction.writeLoginFault(un, pw,req.getRemoteAddr(),"L'utente non risulta registrato all'ecosistema GISA",false);
			}
			
			if(isBlocked){
				setErrore("E' stato raggiunto il numero massimo di tentativi consecutivi per l'accesso al sistema. Si prega di riprovare piu' tardi");
				GenericAction.writeLoginFault(un,pw, req.getRemoteAddr(),"E' stato raggiunto il numero massimo di tentativi consecutivi per l'accesso al sistema. Si prega di riprovare pi� tardi", true);
			}

			else 
			{
				
				if(utenteGuc!=null)
				{
					if(!Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
					{
						int intervalPassword = Integer.parseInt(ApplicationProperties.getProperty("passwordInterval"));
						System.out.println("[SCA] Controllo password obsoleta per utente "+un);
						boolean isScadenzaPassword = UtenteDAO.getScadenzaPasswordByUser(un, intervalPassword, db);
						if (isScadenzaPassword)
						{
							String msg = "Attenzione. La password dell'utente "+utenteGuc.getUsername()+" risulta non modificata da piu' di "+intervalPassword+" giorni. Si consiglia di modificarla tramite la funzione di Cambio Password.";
							session.setAttribute("ScadenzaPassword", msg);
							System.out.println("[SCA] Controllo password obsoleta per utente "+un+": OBSOLETA");
	
						}
					}
					
					
					if(utenti!=null && utenti.containsKey(un))
					{
						HttpSession sessione = utenti.get(un);
						if(sessione!=null)
							try
							{
								sessione.invalidate();
							}
							catch(Exception e)
							{
								System.out.println("La sessione dell'utente " + utenteGuc.getUsername() + ", id: " + utenteGuc.getId() + " e' stata gia' invalidata"  );
							}
						System.out.println("remove"  );
						utenti.remove(un);
						System.out.println("post remove"  );
					}
					System.out.println("put utente"  );
					utenti.put(un, session); //?
					System.out.println("put utente eseguito"  );
					context.setAttribute("utenti", utenti);
					session.setAttribute( "utenteGuc", utenteGuc );	
					System.out.println("set attribute utente eseguito"  );
				}
			}
				
			redirectTo( "IndexSca.us?reload" );
		//Modifica spid INIZIO
		}
		else
		{
			setErrore(erroreSpid);
			redirectTo( "IndexSca.us" );
		}
		//Modifica spid FINE
			
		}
	
}
