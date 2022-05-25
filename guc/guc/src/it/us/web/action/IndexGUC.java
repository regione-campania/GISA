/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import java.util.ArrayList;

import it.us.web.dao.guc.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;

public class IndexGUC extends GenericAction
{
	
	
	public IndexGUC() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		if( utenteGuc == null)
		{
			gotoPage("/jsp/guc/indexguc.jsp" );
		}
		else
		{
			ArrayList<String> endpoints = RuoloDAO.getEndpointByIdUtente(utenteGuc.getId(), db);
			req.setAttribute("endpoints", endpoints);
			gotoPage("/jsp/guc/scelta.jsp" ); 
		}
			
	}

}
