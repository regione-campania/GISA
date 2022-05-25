/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiUrine;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupEsameUrineColore;
import it.us.web.bean.vam.lookup.LookupEsameUrinePresenze;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAdd extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("urine");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
			//Recupero Bean CartellaClinica
		
			ArrayList<LookupEsameUrinePresenze> presenze = (ArrayList<LookupEsameUrinePresenze>) persistence.createCriteria( LookupEsameUrinePresenze.class )
				.addOrder( Order.asc( "level" ) )
				.list();
		
			ArrayList<LookupEsameUrinePresenze> presenzeB = (ArrayList<LookupEsameUrinePresenze>) persistence.createCriteria( LookupEsameUrinePresenze.class )
				.add( Restrictions.eq( "breve", true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
		
			ArrayList<LookupEsameUrineColore> colori = (ArrayList<LookupEsameUrineColore>) persistence.createCriteria( LookupEsameUrineColore.class )
				.addOrder( Order.asc( "level" ) )
				.list();

			req.setAttribute("presenze", presenze);
			req.setAttribute("presenzeB", presenzeB);
			req.setAttribute("colori", colori);
			gotoPage("/jsp/vam/cc/esamiUrine/addEdit.jsp");
	}
}
