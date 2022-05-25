/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.funzioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BRuolo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.Vector;

	public class PermissionList
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
		
		String funzione		= stringaFromRequest( "funzione" );
		String subFunzione	= stringaFromRequest( "subfunzione" );
		String gui			= stringaFromRequest( "gui" );
		
		Vector<BRuolo> ruoli = RuoloDAO.getRuoli();
		BGuiView bGuiView = GuiViewDAO.getView( funzione, subFunzione, gui );
		req.setAttribute( "ruoli", ruoli );
		req.setAttribute( "bGuiView", bGuiView );
		
		gotoPage( "popup", "/jsp/amministrazione/funzioni/permissionList.jsp" );
		
	}
	
	public void can()
		throws
			AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView("AMMINISTRAZIONE", "FUNZIONI", "MAIN");
		can( gui, "r" );
	}
	
}
