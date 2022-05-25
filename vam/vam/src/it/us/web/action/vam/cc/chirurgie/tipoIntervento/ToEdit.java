/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.chirurgie.tipoIntervento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.autopsie.Detail;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.TipoIntervento;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupTipologiaAltroInterventoChirurgico;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SuperUtenteDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.util.vam.ComparatorSuperUtenti;
import it.us.web.util.vam.ComparatorUtenti;

public class ToEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("tipoIntervento");
	}

	public void execute() throws Exception
	{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
			connection = ds.getConnection();
			GenericAction.aggiornaConnessioneApertaSessione(req);
			
			int idTipoIntervento = interoFromRequest("idTipoIntervento");
		
			//Recupero Bean TipoIntervento
			TipoIntervento tipoIntervento = (TipoIntervento) persistence.find(TipoIntervento.class, idTipoIntervento);
		
			//Recupero lista operatori disponibili
			ArrayList<SuperUtente> operatori = new ArrayList<SuperUtente>();
			for(BUtente u :UtenteDAO.getUtenti(connection, -1, utente.getClinica().getId()))
			{
				if(u.getRuolo()!=null && 
						(u.getRuolo().equals("Ambulatorio - Veterinario") || u.getRuolo().equals("5") || 
						 u.getRuolo().equalsIgnoreCase("Referente Asl")   || u.getRuolo().equals("14") ||
						 u.getRuolo().equalsIgnoreCase("Sinantropi")   || u.getRuolo().equals("13") ||
						 u.getRuolo().equalsIgnoreCase("Borsisti")   || u.getRuolo().equals("12")
						 )
				  )
				{
					SuperUtente su = SuperUtenteDAO.getSuperUtente(u.getSuperutente().getId(), connection);
					ArrayList<BUtente> utentiS = new ArrayList<>();
					utentiS.add(u);
					su.setUtenti(utentiS);
					operatori.add(su);
				}
			}
			
			Collections.sort(operatori, new ComparatorSuperUtenti());
			req.setAttribute("operatori", operatori);

			req.setAttribute("tipoIntervento", tipoIntervento);	
			
			ArrayList<LookupTipologiaAltroInterventoChirurgico> listTipologieAltriInterventi = (ArrayList<LookupTipologiaAltroInterventoChirurgico>) persistence.createCriteria( LookupTipologiaAltroInterventoChirurgico.class )
					.addOrder( Order.asc( "level" ) )
					.list();
			req.setAttribute("listTipologieAltriInterventi", listTipologieAltriInterventi);	
				
			gotoPage("/jsp/vam/cc/chirurgie/tipoIntervento/edit.jsp");
	}
}


