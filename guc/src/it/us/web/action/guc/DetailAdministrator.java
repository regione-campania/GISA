/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetailAdministrator extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );
	
	@Override
	public void can() throws AuthorizationException
	{
			isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		int id = interoFromRequest("id");
		BUtente utente = UtenteDAO.getUtenteBId(db,id); 
		req.setAttribute("UserRecord", utente);
		gotoPage( "/jsp/guc/detailAdministrator.jsp" );
	}
}