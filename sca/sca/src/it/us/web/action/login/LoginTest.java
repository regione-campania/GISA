/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import crypto.nuova.gestione.ClientSCAAesServlet;
import crypto.nuova.gestione.SCAAesServlet;
import it.us.web.action.GenericAction;
import it.us.web.dao.UtenteDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;

public class LoginTest extends GenericAction {

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
		
		String cf_spid = req.getParameter( "cf_spid" );
		String pw_spid = req.getParameter( "pw_spid" );

		System.out.println("Login sca - cf_spid: " + cf_spid);
		
		System.out.println("Login sca - cf_spid e NOT NULL. Verifica accesso sara eseguito secondo la modalita SPID.");
		try
		{
			String originalToken = System.currentTimeMillis()+"" ;

			System.out.println("originalToken: " + originalToken);
			SCAAesServlet cclient = new SCAAesServlet();
			Properties p = new Properties();
			System.out.println("properties: " );
			p.load(cclient.getClass().getResourceAsStream("client.properties"));
			System.out.println("properties: " + p);
			System.out.println("properties: " + p.getProperty("key") );
			String encryptedToken = cclient.encrypt(originalToken, p.getProperty("key"));
			System.out.println("properties: " + p.getProperty("key") );
			utenteGuc = UtenteDAO.authenticateAdministratorAccessSpid(cf_spid, pw_spid, db);
			System.out.println("utenteGuc: " + utenteGuc );
			System.out.println("utenteGuc: "  );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			erroreSpid = e.getMessage();
		}
		
		if(erroreSpid==null)
		{
		//Modifica spid FINE
		
		HashMap<String, HttpSession> utenti = (HashMap<String, HttpSession>) session.getServletContext().getAttribute("utenti");
		
		int interval = Integer.parseInt(ApplicationProperties.getProperty("loginInterval"));
		int loginAttempt = Integer.parseInt(ApplicationProperties.getProperty("loginAttempt"));


			if( utenteGuc == null)
			{
				setErrore("L'utente non risulta registrato all'ecosistema GISA");
				GenericAction.writeLoginFault(null, null,req.getRemoteAddr(),"L'utente non risulta registrato all'ecosistema GISA",false);
			}
			
				if(utenteGuc!=null)
				{
					if(utenti!=null && utenti.containsKey(utenteGuc.getUsername()))
					{
						HttpSession sessione = utenti.get(utenteGuc.getUsername());
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
						utenti.remove(utenteGuc.getUsername());
						System.out.println("post remove"  );
					}
					System.out.println("put utente"  );
					utenti.put(utenteGuc.getUsername(), session); //?
					System.out.println("put utente eseguito"  );
					context.setAttribute("utenti", utenti);
					session.setAttribute( "utenteGuc", utenteGuc );	
					System.out.println("set attribute utente eseguito"  );
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
