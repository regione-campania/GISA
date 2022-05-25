/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.ruoli;

import it.us.web.action.GenericAction;
import it.us.web.bean.BFunzione;
import it.us.web.bean.BGuiView;
import it.us.web.dao.FunzioneDAO;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;

import java.util.Vector;

	public class ToPermissionEdit
		extends
			GenericAction
	{
		
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("indice");
	}
		
	public void execute()
		throws
			Exception 
	{
		String 				funzione	= stringaFromRequest( "funzione" );
		String 				ruolo		= stringaFromRequest( "ruolo" );
		Vector<BGuiView>	v2			= null;
		
		if( !isEmpty( funzione ) )
		{
			if( funzione.equalsIgnoreCase( "tutte" ) )
			{
				v2 = Permessi.getPermissionsOnRuolo( ruolo );
			}
			else
			{
				v2 = Permessi.getPermissionsOnRuolo( ruolo, funzione );
			}
		}

		Vector<BFunzione> funzioni	= FunzioneDAO.getFunzioni();
		req.setAttribute( "funzioni", funzioni );
		req.setAttribute( "permessi", v2 );
		
		req.setAttribute( "ruolo", RuoloDAO.getRuoloByName(ruolo) );
		
		gotoPage( "/jsp/amministrazione/ruoli/permissionEdit.jsp" );
	}
	
	public void can()
		throws
			AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "RUOLI", "MAIN" );
		can( gui, "w" );
	}
	
}
