/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiCoprologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoElminti;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoProtozoi;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAdd extends GenericAction implements Specie
{

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("coprologico");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{

			String specieAnimale = null;
			switch ( cc.getAccettazione().getAnimale().getLookupSpecie().getId() )
			{
			case CANE:
				specieAnimale = "canina";
				break;
			case GATTO:
				specieAnimale = "felina";
				break;
			case SINANTROPO:
				specieAnimale = "sinantropi";
				break;
			}
			
			ArrayList<LookupEsameCoprologicoElminti> elminti = (ArrayList<LookupEsameCoprologicoElminti>) persistence.createCriteria( LookupEsameCoprologicoElminti.class )
				.add( Restrictions.eq( specieAnimale, true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			ArrayList<LookupEsameCoprologicoProtozoi> protozoi = (ArrayList<LookupEsameCoprologicoProtozoi>) persistence.createCriteria( LookupEsameCoprologicoProtozoi.class )
				.add( Restrictions.eq( specieAnimale, true ) )
				.addOrder( Order.asc( "level" ) )
				.list();
			
				
			req.setAttribute( "modify", false );
			req.setAttribute( "elminti", elminti );
			req.setAttribute( "protozoi", protozoi );
			
			gotoPage("/jsp/vam/cc/esamiCoprologici/addEdit.jsp");

	}
}

