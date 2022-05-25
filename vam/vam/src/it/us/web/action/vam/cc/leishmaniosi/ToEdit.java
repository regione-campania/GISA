/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.leishmaniosi;

import java.util.ArrayList;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.bean.vam.lookup.LookupLeishmaniosiEsiti;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToEdit extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("leishmaniosi");
	}

	public void execute() throws Exception
	{
	
			int id = interoFromRequest("idLeishmaniosi");		
						
			//Recupero Bean Leishmaniosi
			Leishmaniosi l = (Leishmaniosi) persistence.find(Leishmaniosi.class, id);
			ArrayList<LookupLeishmaniosiEsiti> listEsiti = (ArrayList<LookupLeishmaniosiEsiti>) persistence.findAll(LookupLeishmaniosiEsiti.class);
		
		
			req.setAttribute("l", l);	
			req.setAttribute("listEsiti", listEsiti);	
		
			gotoPage("/jsp/vam/cc/leishmaniosi/edit.jsp");
	}
}


