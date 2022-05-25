/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.LeishList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class ProfilassiLeishmania  extends CFSModule {
	
	
	public String executeCommandDefault(ActionContext context) {
		
		if (hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			
			return executeCommandAdd(context);
	}else{
		return executeCommandAdd(context);
	}
		
	}
	
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			
				return ("PermissionError");
		}


		Connection db = null;
		try {
			db = this.getConnection(context);

			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Add");
		
	}
	
	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			
				return ("PermissionError");
		}


		Connection db = null;
		try {
			
			db = this.getConnection(context);
			db.setAutoCommit(false);
			
			EventoPrelievoLeishmania prelievo = (EventoPrelievoLeishmania) context
			.getRequest().getAttribute(
					"EventoPrelievoLeishmania");
			
			prelievo.setEnteredby(this.getUserId(context));
			prelievo.setModifiedby(this.getUserId(context));
			
			Animale thisAnimale = new Animale(db, prelievo.getMicrochip());
			prelievo.setIdAnimale(thisAnimale.getIdAnimale());
			prelievo.setIdAslRiferimento(thisAnimale.getIdAslRiferimento()); //ASL INSERIMENTO EVENTO UGUALE ASL ANIMALE ???
			prelievo.setIdStatoOriginale(thisAnimale.getStato());
			prelievo.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
			prelievo.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
			
			prelievo.insert(db);
			
			context.getRequest().setAttribute("animale", thisAnimale);
			context.getRequest().setAttribute("EventoPrelievoLeishmania", prelievo);
			context.getRequest().setAttribute("avvisoCertificati", "Attenzione, non potrai più stampare il modulo precompilato per l'invio campioni dopo aver abbandonato la pagina");
			


		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			db.setAutoCommit(true);
			db.close();
			this.freeConnection(context, db);
			
		}
		

		return getReturn(context, "StampaCertificati");
		
	}
	
	
public String executeCommandSearchFormEisitLeish(ActionContext context) {
		
		if (!hasPermission(context, "visualizzazione_esiti_leishmania-view")) {

			return ("PermissionError");

		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			this.deletePagedListInfo(context, "EventiListInfo");
			PagedListInfo eventiListInfo = this.getPagedListInfo(context,
					"esitiLeishListInfo");
			eventiListInfo.setCurrentLetter("");
			eventiListInfo.setCurrentOffset(0);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.removeElementByLevel(13);
			context.getRequest().setAttribute("AslList", siteList);

			
			//Parametro ricerca non necessario
//			LookupList specieList = new LookupList(db, "lookup_specie");
//			specieList.addItem(-1, "--Tutti--");
//			context.getRequest().setAttribute("specieList", specieList);
			
			LookupList esitoList = new LookupList(db, "lookup_esito_leishmania");
			esitoList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("esitoList", esitoList);

			Calendar cal = new GregorianCalendar();
			int annoCorrenteInt = cal.get(Calendar.YEAR);
			String annoCorrente = new Integer(annoCorrenteInt).toString();
			// int annoCorrenteInt = Integer.parseInt(annoCorrente);
			LookupList annoList = new LookupList(); // creo una nuova lookup

			annoList.addItem(annoCorrenteInt - 3, String
					.valueOf(annoCorrenteInt - 3));
			annoList.addItem(annoCorrenteInt - 2, String
					.valueOf(annoCorrenteInt - 2));
			annoList.addItem(annoCorrenteInt - 1, String
					.valueOf(annoCorrenteInt - 1));
			annoList.addItem(annoCorrenteInt, annoCorrente);
			annoList.addItem(-1, "Tutti");

			context.getRequest().setAttribute("annoList", annoList);

			Calendar now = Calendar.getInstance();
			int year = (now.get(Calendar.YEAR));
			String anno = String.valueOf(year);

			context.getRequest().setAttribute("daDefault", "01/01/" + anno);

			context.getRequest().setAttribute("aDefault", "31/12/" + anno);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Profilassi Leishmania", "ProfilassiLeishmania");
		return ("SearchEsitiLeishOK");

	}
	
	
	public String executeCommandSearchEsitiLeish(ActionContext context) {
		if (!hasPermission(context, "visualizzazione_esiti_leishmania-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}
		PagedListInfo EventiListInfo = this.getPagedListInfo(context,
				"esitiLeishListInfo");
		String servletPath = context.getRequest().getServletPath();
		String userAction = servletPath.substring(servletPath.indexOf("/") + 1,
				servletPath.indexOf(".do"));
		EventiListInfo.setLink(userAction + ".do?command=SearchEsitiLeish");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LeishList listaEventi = new LeishList();
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setIdUtenteInserimento(getUserId(context));

			EventiListInfo.setSearchCriteria(listaEventi, context);
			listaEventi.setPagedListInfo(EventiListInfo);

			listaEventi.buildList(db);

			context.getRequest().setAttribute("ListaEsitiLeish", listaEventi);

			LookupList registrazioniList = new LookupList(db,
					"lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			// Action di provenienza
			// String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(
					servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);
			
			addModuleBean(context, "Profilassi Leishmania", "ProfilassiLeishmania");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "ViewEsitiLeish");

	}
	
	
	

}
