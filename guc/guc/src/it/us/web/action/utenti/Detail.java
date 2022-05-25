/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.utenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;

public class Detail extends GenericAction
{
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "MAIN" );
		can( gui, "r" );
	}

	public void execute() throws Exception
	{
		BUtente userDetail = (BUtente)req.getAttribute( "userDetail" );
		
		if( userDetail == null )
		{
			int id = interoFromRequest( "user_id" );
			userDetail = UtenteDAO.getUtenteBId(db, id) ;//(BUtente) persistence.find( BUtente.class, id );
			req.setAttribute( "userDetail", userDetail );
		}
		
		gotoPage( "/jsp/amministrazione/utenti/detail.jsp" );
		
	}
}
