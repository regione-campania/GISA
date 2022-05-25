/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.AslDAO;
import it.us.web.dao.RuoloDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.jmesa.AslDroplistFilterEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class ToGroup extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		
		boolean utentiAttivi = true;
		String endpoint = req.getParameter("endpoint") != null && !req.getParameter("endpoint").trim().equals("") ? stringaFromRequest("endpoint") : "";
		int ruolo = req.getParameter("ruolo") != null && !req.getParameter("ruolo").trim().equals("") ? interoFromRequest("ruolo") : -1;
		
		List<Asl> aslList = AslDAO.getAsl(db);
		for(Asl a : aslList){
			AslDroplistFilterEditor.getMapValueLabel().put(a.getNome(), a.getNome());
		}
		AslDroplistFilterEditor.getMapValueLabel().put("TUTTE LE ASL", "");
	
		ArrayList<Ruolo> listaRuoli ;
		List<String> endpoints = new ArrayList<String>();
		
		for(GUCEndpoint e : GUCEndpoint.values()){
			endpoints.add(e.toString());
			listaRuoli = RuoloDAO.getRuoliByEndPoint(e.toString(), db);
			req.setAttribute("listaRuoli" + e, listaRuoli);
		}
		
		ArrayList<Utente> utentiList2 = UtenteGucDAO.listaUtenti(db, endpoint, ruolo, utentiAttivi);
		ArrayList<BUtente> utentiListGuc = UtenteDAO.getUtenti(db);
		
		if(req.getParameterValues("groups") != null && !req.getParameterValues("groups").equals("")){
			String id_utenti = "";
			String [] values = req.getParameterValues("groups");
			for(int i=0;i<values.length;i++){
				id_utenti += "'"+values[i]+"',";
			}
			//Aggiorna il codice di raggruppamento
			if(id_utenti.endsWith(","))
			{
				id_utenti = id_utenti.substring(0,id_utenti.length() - 1);
			}
			
			RuoloDAO.setCodiceRaggruppamentoUtenti(db, id_utenti);
			setMessaggio( "Operazione eseguita con successo" );
		}
		
		
		
		//List<Utente> utentiList = criteria.addOrder( Order.desc("entered") ).list();
		req.setAttribute("utentiList", utentiList2);
		req.setAttribute("utentiListGuc", utentiListGuc);
        
		req.setAttribute("endpoints", endpoints);
		
		req.setAttribute("utentiAttivi", utentiAttivi);
		req.setAttribute("endpoint", endpoint);
		req.setAttribute("ruolo", ruolo);
		
		gotoPage( "/jsp/guc/usergroup.jsp" );
		
	}

}
