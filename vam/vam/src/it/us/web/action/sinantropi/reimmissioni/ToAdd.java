/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi.reimmissioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAdd extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_reimmissione");
	}

	public void execute() throws Exception
	{
			
		int idCattura = interoFromRequest("idCattura");
		
		Catture c = (Catture) persistence.find(Catture.class, idCattura);
		
		Sinantropo s = c.getSinantropo();
		
/*		ArrayList<LookupComuni> listComuniBN = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "bn", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniNA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "na", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniSA = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "sa", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniAV = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "av", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupComuni> listComuniCE = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.add( Restrictions.eq( "ce", true ) )
		.addOrder( Order.asc( "level" ) )
		.list(); */
		
		
		req.setAttribute("c", c);	
		req.setAttribute("s", s);			
		
/*		req.setAttribute("listComuniBN", listComuniBN);
		req.setAttribute("listComuniNA", listComuniNA);
		req.setAttribute("listComuniSA", listComuniSA);
		req.setAttribute("listComuniCE", listComuniCE);
		req.setAttribute("listComuniAV", listComuniAV);*/		
				
		
		gotoPage("sinantropi_default","/jsp/sinantropi/reimmissioni/add.jsp");
			
	}
}




