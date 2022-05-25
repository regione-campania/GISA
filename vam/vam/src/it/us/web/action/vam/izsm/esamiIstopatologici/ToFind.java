/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.izsm.esamiIstopatologici;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.dao.vam.ClinicaDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToFind extends GenericAction {

	
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "CC", "MAIN", "MAIN" );
//		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}

	public void execute() throws Exception
	{	
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		Integer idSalaSettoria = null;
		ArrayList<EsameIstopatologico> esamiIstopatologici = new ArrayList<EsameIstopatologico>();
		
		//Utenti izsm e unina vedono gli istologici inviati alla loro struttura
		if(utente.getRuolo()!=null && (utente.getRuolo().equals("IZSM") || 
									   utente.getRuolo().equals("Universita") ||
									   utente.getRuolo().equals("6") ||
									   utente.getRuolo().equals("8")
									  ) )
		{	
			if(utente.getClinica().getNomeBreve().toLowerCase().contains("unina"))
				idSalaSettoria = 7;
			else if(utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-portici"))
				idSalaSettoria = 6;
			else if(utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-avellino"))
				idSalaSettoria = 12;
			else if(utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-benevento"))
				idSalaSettoria = 13;
			else if(utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-caserta"))
				idSalaSettoria = 14;
			else if(utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-salerno"))
				idSalaSettoria = 15;
			
			esamiIstopatologici = (ArrayList<EsameIstopatologico>) ClinicaDAO.getEsamiIstopatologiciIzsmUnina(connection, idSalaSettoria);
			
			
			req.setAttribute("esamiIstopatologici", esamiIstopatologici);
			gotoPage("/jsp/vam/izsm/esamiIstopatologici/find.jsp");	
			
			
		}
		//Il resto degli utenti abilitati(utenti asl) vedono gli istologici inseriti nella loro clinica 
		//allo scopo di visualizzare se è arrivato qualche esito
		else
		{
			esamiIstopatologici = (ArrayList<EsameIstopatologico>) ClinicaDAO.getEsamiIstopatologici(connection, utente.getClinica().getId());
			
			req.setAttribute("esamiIstopatologici", esamiIstopatologici);
			gotoPage("/jsp/vam/cc/esamiIstopatologici/listAll.jsp");	
		}
		
						
		
	}
}

