/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.UtentiOperazioni;
import it.us.web.bean.UtentiOperazioniModifiche;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ehrlichiosi;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class StoricoModificheDetail extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "STORICO", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
		int id = interoFromRequest("id");
		String nuovagestione = stringaFromRequest("nuovagestione");
		UtentiOperazioni modifica = new UtentiOperazioni();
		if (nuovagestione.equalsIgnoreCase("false")){
			modifica = (UtentiOperazioni) persistence.find(UtentiOperazioni.class, id);
		} else { 
			UtentiOperazioniModifiche uom = (UtentiOperazioniModifiche)persistence.find(UtentiOperazioniModifiche.class , id);
			modifica.setId(uom.getId());
			modifica.setEntered(uom.getEntered());
			BUtenteAll ut = UtenteDAO.getUtenteAll(uom.getUserid());
			modifica.setEnteredBy(ut);
			modifica.setModified(modifica.getEntered());
			modifica.setModifiedBy(modifica.getEnteredBy());
			modifica.setCc((CartellaClinica)persistence.find(CartellaClinica.class, uom.getIdcc()));
			modifica.setOperazione(uom.getUrloperazione());
			modifica.setDescrizioneOperazione(uom.getDescrizioneoperazione());
			modifica.setIp("nuovagestione");
			modifica.setUsername(((BUtente)persistence.find(BUtente.class, uom.getUserid())).getUsername());
			
			Set<UtentiOperazioniModifiche> app = new HashSet<UtentiOperazioniModifiche>(0);
			app.add(uom);
			modifica.setModifiche(app);
		}
		req.setAttribute("modifica", modifica);
				
		gotoPage("/jsp/storicoModificheDetail.jsp");
	}
}



