/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.izsm.autopsie;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.EsameSangue;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("ecg");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnection(connection);
		
		if (cc==null){
			cc = (CartellaClinica)persistence.find (CartellaClinica.class, (Integer)session.getAttribute("idCc"));
		}
		
		//Recupero della necroscopia associata alla CC
		Autopsia autopsia = cc.getAutopsia();
		Set<Autopsia> autopsie = new HashSet<Autopsia>();
		if(autopsia!=null)
			autopsie.add(autopsia);

		req.setAttribute("autopsie", autopsie);
		
		//Controllo se posso modificare la richiesta
		boolean canEditRichiesta = true;
		boolean accettato = false;
		if(!TrasferimentoDAO.getTrasferimentiAccettati("I", cc.getAccettazione().getAnimale().getId(), utente.getClinica().getId(), connection).isEmpty())
			accettato=true;
		if(autopsia!=null &&  ((!autopsia.getLass().getEsterna() && autopsia.getDataEsito()!=null) || (autopsia.getLass().getEsterna() && accettato )))
			canEditRichiesta = false;
		req.setAttribute("canEditRichiesta", canEditRichiesta);
		//Fine controllo
		
		
		//Controllo se posso inserire la necroscopia
		boolean utenteStrutturaNecroscopia = true;
		if(autopsia!=null)
		{
			if(autopsia.getLass().getEsterna())
			{
				if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-avellino") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-avellino"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-avellino") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-avellino"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-benevento") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-benevento"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-caserta") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-caserta"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-portici") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-portici"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("izsm-salerno") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-salerno"))
					utenteStrutturaNecroscopia = true;
				else if(autopsia.getLass().getDescription().toLowerCase().contains("unina") && utente.getClinica().getNomeBreve().toLowerCase().contains("unina"))
					utenteStrutturaNecroscopia = true;
				else 
					utenteStrutturaNecroscopia = false;
			}
			else
			{
				if(utente.getClinica().getId()!=autopsia.getEnteredBy().getClinica().getId())
					utenteStrutturaNecroscopia = false;
			}
		}
		
		req.setAttribute("utenteStrutturaNecroscopia", utenteStrutturaNecroscopia);
		//Fine controllo
				
		gotoPage("/jsp/vam/izsm/autopsie/list.jsp");
	}
}



