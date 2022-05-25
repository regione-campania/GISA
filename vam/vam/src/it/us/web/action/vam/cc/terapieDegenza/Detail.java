/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.terapieDegenza;

import java.util.ArrayList;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.TerapiaAssegnata;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupModalitaSomministrazioneFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.bean.vam.lookup.LookupVieSomministrazioneFarmaci;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class Detail extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("terapia");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		int id = interoFromRequest("idTerapiaDegenza");
		
		//Recupero Bean Terapia Degenza
		TerapiaDegenza td = (TerapiaDegenza) persistence.find (TerapiaDegenza.class, id);
						
		ArrayList<LookupFarmaci> farmaci 
			= (ArrayList<LookupFarmaci>) persistence.findAll(LookupFarmaci.class);
		
		ArrayList<LookupModalitaSomministrazioneFarmaci> modalitaSomministrazione
			= (ArrayList<LookupModalitaSomministrazioneFarmaci>) persistence.findAll(LookupModalitaSomministrazioneFarmaci.class);
		
		ArrayList<LookupVieSomministrazioneFarmaci> vieSomministrazione	
			= (ArrayList<LookupVieSomministrazioneFarmaci>) persistence.findAll(LookupVieSomministrazioneFarmaci.class);
		
		ArrayList<LookupTipiFarmaco> tipiFarmaco
			= (ArrayList<LookupTipiFarmaco>) persistence.findAll(LookupTipiFarmaco.class);
	
					
		//Recupero di tutte le terapie assegnata per quella determinata degenza
		ArrayList<TerapiaAssegnata> taList = (ArrayList<TerapiaAssegnata>) persistence.getNamedQuery("GetTerapieAssegnate").setInteger("idTerapiaDegenza", id).list();

		ArrayList<MagazzinoFarmaci> mfList 
		= (ArrayList<MagazzinoFarmaci>) persistence.getNamedQuery("GetFarmaciByClinica").setInteger("idClinica", utente.getClinica().getId()).list();
		
		req.setAttribute("mfList", 	mfList);	
		
		
		
			
		req.setAttribute("taList", taList);		
		req.setAttribute("td", td);		
		
		req.setAttribute("farmaci", 					farmaci);	
		req.setAttribute("modalitaSomministrazione", 	modalitaSomministrazione);	
		req.setAttribute("vieSomministrazione", 		vieSomministrazione);	
		req.setAttribute("tipiFarmaco", 				tipiFarmaco);	
				
		gotoPage("/jsp/vam/cc/terapieDegenza/detail.jsp");
	}
}

