/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.action.IndexGUC;
import it.us.web.exceptions.AuthorizationException;

public class LogoutGUC extends GenericAction
{
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		if( utenteGuc != null )
		{
			String system = (String) session.getAttribute("system");
			session.setAttribute( "utenteGuc", null );
			session.invalidate();			
			utenteGuc = null;
			
			goToAction( new IndexGUC() );
			
			
		}
		else {
			
			session.invalidate();			
			goToAction( new IndexGUC() );
			
			
		}
		
		
	}
}