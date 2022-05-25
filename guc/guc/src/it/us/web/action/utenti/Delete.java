/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.utenti;

import java.sql.Timestamp;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;
import it.us.web.util.properties.Message;

public class Delete extends GenericAction
{
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "DELETE" );
		can( gui, "w" );
	}

	public void execute() throws Exception
	{
		
		int id = interoFromRequest( "user_id" );
		BUtente userDetail =  UtenteDAO.getUtenteBId(db, id);//(BUtente) persistence.find( BUtente.class, id );
		userDetail.setEnabled( false );
		userDetail.setModified( new Timestamp( System.currentTimeMillis() ) );
		userDetail.setModifiedBy( (int)utente.getId() );
		
		UtenteDAO.update(db, userDetail);
		//persistence.update( userDetail );
		//persistence.commit();
		Permessi.removeFromAllCategory( userDetail );
		
		setMessaggio( Message.getSmart( "UTENTE_ELIMINATO" ) );
		
		goToAction( new List() );
		
	}
}
