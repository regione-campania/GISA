/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ticket;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "LIST", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_anagrafica");
	}

	public void execute() throws Exception
	{
		
		int idClinica 	= utente.getClinica().getId();
		//ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>)  persistence.findAll(Sinantropo.class);
		
		
		//Recupero dei sinantropi di una determinata ASL
		ArrayList<Sinantropo> sinantropi = (ArrayList<Sinantropo>)  persistence.getNamedQuery("GetSinantropiByClinica")
				.setBoolean("zoo", false)
				.setBoolean("marini", false)
				.setBoolean("sinantropo", true)
				.setInteger("idClinica", idClinica).list();
		
		
		
		if (sinantropi.size() == 0) {
			
			setMessaggio("Nessun Sinantropo presente in banca dati");				
			gotoPage("sinantropi_default","/jsp/homepageS.jsp");	
			
			
		}
		else {			
						
			req.setAttribute("sinantropi", sinantropi);						
			gotoPage("sinantropi_default","/jsp/sinantropi/list.jsp");
			
		}
		
	}
		
}

