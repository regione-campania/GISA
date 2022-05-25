/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.util.Enumeration;

import it.us.web.action.GenericAction;
import it.us.web.dao.UtenteDAO;

import it.us.web.exceptions.AuthorizationException;

public class LoginGUC extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		if( utenteGuc != null )
		{
			Enumeration<String> e = session.getAttributeNames();
			while( e.hasMoreElements() )
			{
				session.removeAttribute( e.nextElement() );
			}
			utenteGuc = null;
		}
		
		String un = stringaFromRequest( "utente" );
		String pw = stringaFromRequest( "password" );
		
		
		utenteGuc = UtenteDAO.authenticateUnifiedAccess(un, pw, db);
		
		if( utenteGuc == null)
		{
			setErrore("Autenticazione fallita");
		}
		
		else {
			
			session.setAttribute( "utenteGuc", utenteGuc );			
		}
			
		
		redirectTo( "IndexGUC.us" );
		
	}
}
