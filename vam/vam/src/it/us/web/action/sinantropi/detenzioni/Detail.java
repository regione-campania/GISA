/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.detenzioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class Detail extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "DETAIL", "MAIN" );
		can( gui, "w" );
	}

	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_detenzione");
	}
	
	public void execute() throws Exception
	{
		int idDetenzione = interoFromRequest("idDetenzione");
		
		Detenzioni d = (Detenzioni) persistence.find(Detenzioni.class, idDetenzione);
		Sinantropo s = (Sinantropo) d.getCatture().getSinantropo();
		Catture c = (Catture) d.getCatture();
		
		req.setAttribute("s", s);
		req.setAttribute("d", d);	
		req.setAttribute("c", c);	
							
		gotoPage("sinantropi_default", "/jsp/sinantropi/detenzioni/detail.jsp");
	}
}

