/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToEditZoo extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_anagrafica");
	}

	public void execute() throws Exception
	{
		int idSinantropo = interoFromRequest("idSinantropo");
		
		Sinantropo s = (Sinantropo)persistence.find(Sinantropo.class, idSinantropo);
		
		ArrayList<LookupSpecieSinantropi> listUccelli = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
		.add( Restrictions.eq( "uccelloZ", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();	
		
		ArrayList<LookupSpecieSinantropi> listMammiferi = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
		.add( Restrictions.eq( "mammiferoZ", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupSpecieSinantropi> listRettiliAnfibi = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
		.add( Restrictions.eq( "rettileAnfibioZ", true ) )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		ArrayList<LookupTaglie>		taglie		= (ArrayList<LookupTaglie>) persistence.createCriteria( LookupTaglie.class )
		.add( Restrictions.eq( "enabled", true ) )
		.addOrder( Order.asc( "level" ) )
		.addOrder( Order.asc( "description" ) ).list();
		
		ArrayList<LookupSinantropiEta> listEta = (ArrayList<LookupSinantropiEta>) persistence.createCriteria( LookupSinantropiEta.class )
				.list();
		
				
		req.setAttribute("taglie", taglie);
		req.setAttribute("s", s);
		req.setAttribute("listUccelli", listUccelli);
		req.setAttribute("listMammiferi", listMammiferi);
		req.setAttribute("listRettiliAnfibi", listRettiliAnfibi);
		req.setAttribute("listEta", listEta);
		
		gotoPage("sinantropi_default","/jsp/sinantropi/editZoo.jsp");
	}
}

