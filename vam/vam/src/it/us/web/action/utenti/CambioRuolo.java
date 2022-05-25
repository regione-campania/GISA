/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.utenti;

import java.sql.SQLException;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;

public class CambioRuolo extends GenericAction
{

	@Override
	public void can() throws AuthorizationException, SQLException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "UTENTI", "CAMBIO RUOLO" );
		Permessi.can( connection, utente, gui, "w" );
	}

	@Override
	public void execute() throws Exception
	{
		int					user_id	= interoFromRequest( "user_id" );
		BUtente				ut		= (BUtente)persistence.find( BUtente.class, user_id );
		String				ruolo	= stringaFromRequest( "ruoli" );
		
		Permessi.add2category( ruolo, ut );
		
		setMessaggio( "Ruolo Assegnato con Successo" );
		
		goToAction( new List() );
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
