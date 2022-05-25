/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.ricoveri;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.StrutturaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
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
		setSegnalibroDocumentazione("ricoveri");
	}

	public void execute() throws Exception
	{

			ArrayList<StrutturaClinica> struttureCliniche = (ArrayList<StrutturaClinica>) persistence.getNamedQuery("GetStruttureRicovero").setInteger("idUtente", utente.getId()).list();
			
			ArrayList<LookupAlimentazioni> listAlimentazioni = (ArrayList<LookupAlimentazioni>) persistence.createCriteria( LookupAlimentazioni.class )
			.addOrder( Order.asc( "level" ) )
			.list();
			
			ArrayList<LookupHabitat> listHabitat = (ArrayList<LookupHabitat>) persistence.createCriteria( LookupHabitat.class )
			.addOrder( Order.asc( "level" ) )
			.list();
			
			
			
			req.setAttribute("sc", struttureCliniche);	
			
			req.setAttribute("listAlimentazioni", listAlimentazioni);		
			req.setAttribute("listHabitat", listHabitat);	
			
			ArrayList<LookupHabitat> listFerite = (ArrayList<LookupHabitat>) persistence.createCriteria( LookupFerite.class )
					.addOrder( Order.asc( "level" ) )
					.list();
					
			req.setAttribute("listFerite", listFerite);	
			
			gotoPage("/jsp/vam/cc/ricoveri/edit.jsp");
	}
}

