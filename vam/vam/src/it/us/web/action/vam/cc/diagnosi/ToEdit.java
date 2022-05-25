/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.diagnosi;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.decessi.Detail;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.ArrayList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("diagnosi");
	}


	public void execute() throws Exception
	{		
			int id = interoFromRequest("idDiagnosi");		
			
			ArrayList<LookupDiagnosi> diagnosiMediche = null;
			ArrayList<LookupDiagnosi> diagnosiChirurgiche = null;
			ArrayList<LookupDiagnosi> diagnosiInfettive = null;
			ArrayList<LookupDiagnosi> diagnosiCP = null;
			
			//Recupero Bean Diagnosi
			Diagnosi d = (Diagnosi) persistence.find(Diagnosi.class, id);
			
			CartellaClinica cc = d.getCartellaClinica();
			
			int tipologiaAnimale = cc.getAccettazione().getAnimale().getLookupSpecie().getId();
			
			if (tipologiaAnimale == 1) {
			
			diagnosiMediche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
				.add( Restrictions.eq( "medica", true ) )
				.add( Restrictions.eq( "chirurgica", false ) )
				.add( Restrictions.eq( "infettiva", false ) )
				.add( Restrictions.eq( "controlloPeriodico", false ) )
				.add( Restrictions.eq( "cane", true ) )
				.addOrder( Order.asc( "description" ) )
				.list();
			
			diagnosiChirurgiche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
				.add( Restrictions.eq( "medica", false ) )
				.add( Restrictions.eq( "chirurgica", true ) )
				.add( Restrictions.eq( "infettiva", false ) )
				.add( Restrictions.eq( "controlloPeriodico", false ) )
				.add( Restrictions.eq( "cane", true ) )
				.addOrder( Order.asc( "description" ) )
				.list();
			
			diagnosiInfettive = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
				.add( Restrictions.eq( "medica", false ) )
				.add( Restrictions.eq( "chirurgica", false ) )
				.add( Restrictions.eq( "infettiva", true ) )
				.add( Restrictions.eq( "controlloPeriodico", false ) )
				.add( Restrictions.eq( "cane", true ) )
				.addOrder( Order.asc( "description" ) )
				.list();
			
			diagnosiCP = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
				.add( Restrictions.eq( "medica", false ) )
				.add( Restrictions.eq( "chirurgica", false ) )
				.add( Restrictions.eq( "infettiva", false ) )
				.add( Restrictions.eq( "controlloPeriodico", true ) )
				.add( Restrictions.eq( "cane", true ) )
				.addOrder( Order.asc( "description" ) )
				.list();
			}
			
			else if (tipologiaAnimale == 2) {
				
				diagnosiMediche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", true ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "gatto", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiChirurgiche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", true ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "gatto", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiInfettive = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", true ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "gatto", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiCP = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", true ) )
					.add( Restrictions.eq( "gatto", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				}
			
			if (tipologiaAnimale == 3) {
				
				diagnosiMediche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", true ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "sinantropo", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiChirurgiche = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", true ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "sinantropo", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiInfettive = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", true ) )
					.add( Restrictions.eq( "controlloPeriodico", false ) )
					.add( Restrictions.eq( "sinantropo", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				diagnosiCP = (ArrayList<LookupDiagnosi>) persistence.createCriteria( LookupDiagnosi.class )
					.add( Restrictions.eq( "medica", false ) )
					.add( Restrictions.eq( "chirurgica", false ) )
					.add( Restrictions.eq( "infettiva", false ) )
					.add( Restrictions.eq( "controlloPeriodico", true ) )
					.add( Restrictions.eq( "sinantropo", true ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				}
			req.setAttribute("d", d);	
			req.setAttribute( "diagnosiMediche", diagnosiMediche );
			req.setAttribute( "diagnosiChirurgiche", diagnosiChirurgiche );
			req.setAttribute( "diagnosiInfettive", diagnosiInfettive );
			req.setAttribute( "diagnosiCP", diagnosiCP );
				
			
			gotoPage("/jsp/vam/cc/diagnosi/edit.jsp");
	}
}

