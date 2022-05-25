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
		if( utente != null )
		{
			Enumeration<String> e = session.getAttributeNames();
			while( e.hasMoreElements() )
			{
				session.removeAttribute( e.nextElement() );
			}
			utente = null;
		}
		
		String cf_spid = req.getParameter( "cf_spid" );
		String pw_spid = req.getParameter( "pw_spid" );
		
		if(cf_spid!=null && !cf_spid.equals("") && !cf_spid.equals("null"))
			utente = UtenteDAO.authenticateAdministratorAccessSpid(cf_spid, pw_spid, db);
				
		if( utente == null )
		{
			setErrore("Autenticazione fallita");
		}
		else
		{	
			session.setAttribute( "utente", utente );
		}
		
		redirectTo( "Index.us" );
		
	}
	
}
