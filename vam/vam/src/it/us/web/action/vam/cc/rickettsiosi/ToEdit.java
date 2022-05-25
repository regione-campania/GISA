/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.rickettsiosi;

import java.util.ArrayList;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.bean.vam.Rickettsiosi;
import it.us.web.bean.vam.lookup.LookupRickettsiosiEsiti;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("rickettsiosi");
	}

	public void execute() throws Exception
	{

			int id = interoFromRequest("idRickettsiosi");		
							
			//Recupero Bean Rickettsiosi
			Rickettsiosi r = (Rickettsiosi) persistence.find(Rickettsiosi.class, id);
			ArrayList<LookupRickettsiosiEsiti> listEsiti = (ArrayList<LookupRickettsiosiEsiti>) persistence.findAll(LookupRickettsiosiEsiti.class);
	
			req.setAttribute("r", r);	
			req.setAttribute("listEsiti", listEsiti);	
			
			gotoPage("/jsp/vam/cc/rickettsiosi/edit.jsp");
	}
	
}


