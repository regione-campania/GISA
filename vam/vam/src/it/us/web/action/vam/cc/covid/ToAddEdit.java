/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.covid;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Covid;
import it.us.web.bean.vam.Fip;
import it.us.web.bean.vam.lookup.LookupCovidTipoTest;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

public class ToAddEdit extends GenericAction
{
	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("fip");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{		
	
			boolean modify = booleanoFromRequest( "modify" );
		
			if( modify )
			{
				Covid covid = (Covid) persistence.find( Covid.class, interoFromRequest("idCovid"));
				req.setAttribute( "covid", covid );
				req.setAttribute( "modify", true );
			}
			
			ArrayList<LookupCovidTipoTest> listCovidTipoTest = (ArrayList<LookupCovidTipoTest>) persistence.createCriteria( LookupCovidTipoTest.class ).list();
			req.setAttribute("listCovidTipoTest", listCovidTipoTest);
		
			gotoPage("/jsp/vam/cc/covid/addEdit.jsp");	
	}
}
