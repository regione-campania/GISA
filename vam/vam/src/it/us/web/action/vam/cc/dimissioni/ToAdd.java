/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CCUtil;

import java.sql.Connection;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ToAdd extends GenericAction 
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("dimissioni");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void execute() throws Exception
	{
		Context ctxVAM = new InitialContext();
		javax.sql.DataSource dsVAM = (javax.sql.DataSource) ctxVAM.lookup("java:comp/env/jdbc/vamM");
		connection = dsVAM.getConnection();
		
			ArrayList<LookupDestinazioneAnimale> destinazioneAnimale = null;
			
			int tipologiaAnimale = cc.getAccettazione().getAnimale().getLookupSpecie().getId();
			
			if (tipologiaAnimale == 1) {
			
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "cane", true ) ).addOrder( Order.asc( "level" ) ).list();		
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "cane", false);
			}
			
			else if (tipologiaAnimale == 2) {
				
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "gatto", true ) ).addOrder( Order.asc( "level" ) ).list();		
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "gatto", false);

			}
			
			if (tipologiaAnimale == 3) {
				
				
				//destinazioneAnimale = (ArrayList<LookupDestinazioneAnimale>) persistence.createCriteria( LookupDestinazioneAnimale.class ).add( Restrictions.eq( "sinantropo", true ) ).addOrder( Order.asc( "level" ) ).list();		
				destinazioneAnimale=LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, "sinantropo", false);

			}
			
			//Se si è nella fase di riconsegna di un trasferimento, le dimissioni redirigono
			//alla gestione dello stessa
			if (CCUtil.riconsegnaPossibile(cc) == true) {
				redirectTo("vam.cc.trasferimenti.ToRiconsegna.us");
			}
			
			
			
			
			req.setAttribute( "destinazioneAnimale", destinazioneAnimale );
				
			req.setAttribute( "opRichieste", IdRichiesteVarie.getInstance() );
			
			
			gotoPage( "/jsp/vam/cc/dimissioni/add.jsp" );
	}

}
