/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.partitecommerciali.actions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale;
import org.aspcfs.modules.partitecommerciali.base.PartiteCommercialiList;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCattura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoMicrochip;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRiconoscimentoPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class PartiteCommerciali extends CFSModule {
	
	
	public String executeCommandDefault(ActionContext context) {

		return executeCommandListaPartite(context);
	}

	public String executeCommandAggiungiPartita(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {

			db = this.getConnection(context);
			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie_partita"); //Non posso usare lookup_specie xkè le partite sono anche miste
			specieList.buildList(db);
			specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

			PartitaCommerciale partita = (PartitaCommerciale) context
					.getRequest().getAttribute("PartitaCommerciale");

			context.getRequest().setAttribute("partita", partita);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		/*
		 * addModuleBean(context, "Add Account", "Accounts Add");
		 * context.getRequest().setAttribute("systemStatus",
		 * this.getSystemStatus(context)); // if a different module reuses this
		 * action then do a explicit return if
		 * (context.getRequest().getParameter("actionSource") != null) { return
		 * getReturn(context, "AddAccount"); }
		 */

		return getReturn(context, "Add");
	}

	public String executeCommandInserisciPartita(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			PartitaCommerciale partita = (PartitaCommerciale) context
					.getRequest().getAttribute("PartitaCommerciale");

			partita.setDataInserimento(DatabaseUtils.getCurrentTimestamp(db));
			// Data modifica = Data inserimento
			partita.setDataModifica(DatabaseUtils.getCurrentTimestamp(db));

			// TODO IMPORTATORE, nella pagina è nel campo hidden idProprietario
			// xciò nn lo prende automaticamente. qsto per usare la solita
			// closepopup
			partita.setIdImportatore((String)context.getRequest().getParameter("idProprietario"));
			Operatore commerciale = new Operatore();
			commerciale.queryRecordOperatorebyIdLineaProduttiva(db, partita.getIdImportatore());
			partita.setOperatoreCommerciale(commerciale);
			partita.setIdUtenteInserimento(getUserId(context));
			// Data inserimento
			partita.setDataInserimento(DatabaseUtils.getCurrentTimestamp(db));
			// Utente modifica = Utente inserimento
			partita.setIdUtenteModifica(getUserId(context));
			if (context.getRequest().getParameter("statoPrenotifica") != null
					&& !("").equals(context.getRequest()
							.getParameter("statoPrenotifica")) && ( ("on")
							.equals(context.getRequest().getParameter(
							"statoPrenotifica")) ||  ("2")
							.equals(context.getRequest().getParameter(
							"statoPrenotifica")))){
				partita.setStatoPrenotifica(1);  //Prenotifica aperta per l'inserimento animali da parte degli importatori
				partita.setIdStatoImportatore(1); //Stato aperto per l'inserimento animali da parte degli importatori
				partita.setFlagPrenotificaImportatore(true);
			}else{
				partita.setStatoPrenotifica(2);  //Prenotifica non aperta per l'inserimento animali da parte degli importatori
				partita.setIdStatoImportatore(-1);
			}
			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals(
							"")
					&& context.getRequest().getParameter("doContinue").equals(
							"false")) {
				
				context.getRequest().setAttribute("PartitaCommerciale", partita);
				return executeCommandAggiungiPartita(context);
			}

			partita.insert(db);
			context.getRequest().setAttribute("partita", partita);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Insert");

	}

	public String executeCommandInserisciDettagliPartita(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("idPartita");
			int idPartita = new Integer(id).intValue();
			PartitaCommerciale partita = new PartitaCommerciale(db, idPartita);

			context.getRequest().setAttribute("partita", partita);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "InsertDettagli");

	}

	public String executeCommandModificaPartita(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			PartitaCommerciale partita = (PartitaCommerciale) context
					.getRequest().getAttribute("PartitaCommerciale");

			if (context.getRequest()
					.getParameterValues("listMicrochipAnimaliConVincolo") != null){
				String[] animaliConVincolo = context.getRequest()
						.getParameterValues("listMicrochipAnimaliConVincolo");
	
				partita.setListMicrochipAnimaliConVincolo(animaliConVincolo);
			}

			partita.setDataModifica(DatabaseUtils.getCurrentTimestamp(db));
			partita.setIdUtenteModifica(getUserId(context));

			partita.update(db);
			partita = new PartitaCommerciale(db, partita.getIdPartitaCommerciale());
			context.getRequest().setAttribute("partita", partita);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Insert");

	}

	public String executeCommandListaPartite(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-view")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		PartiteCommercialiList partiteList = new PartiteCommercialiList();

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"partiteListInfo");
		searchListInfo.setLink("PartiteCommerciali.do?command=ListaPartite");
		searchListInfo.setListView("all");
		try {
			db = this.getConnection(context);
			partiteList.setPagedListInfo(searchListInfo);
			partiteList.setIdAslRiferimento(user.getSiteId());
			partiteList.buildList(db);

			context.getRequest().setAttribute("partiteList", partiteList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList specieList = new LookupList(db, "lookup_specie_partita");
			specieList.addItem(-1, "");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");

		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "ListPartite");

	}
	
	public String executeCommandListaPartiteImportatori(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-view")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
	//	Connection dbImportatori = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		PartiteCommercialiList partiteList = new PartiteCommercialiList();

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"partiteListInfo");
		searchListInfo.setLink("PartiteCommerciali.do?command=ListaPartiteImportatori");
		searchListInfo.setListView("all");
		try {
			db = this.getConnection(context);
			
			//DbUtil.getConnection(ApplicationProperties.getProperty("IMPORTATORIDBNAME"), ApplicationProperties.getProperty("IMPORTATORIDBUSER"), ApplicationProperties.getProperty("IMPORTATORIDBPWD"), InetAddress.getByName("hostDbImportatori").getHostAddress());
			
		//	dbImportatori = GestoreConnessioni.getConnectionImportatori(context);

			partiteList.setPagedListInfo(searchListInfo);
			partiteList.setIdAslRiferimento(user.getSiteId());
			partiteList.setIdStatoImportatore(3); //Stato inviato ad asl
			partiteList.buildList(db);
		//	dbImportatori.close();
			context.getRequest().setAttribute("partiteList", partiteList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList specieList = new LookupList(db, "lookup_specie_partita");
			specieList.addItem(-1, "");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");

		}finally {
			this.freeConnection(context, db);
		//	GestoreConnessioni.freeConnection(dbImportatori);
		}

		return getReturn(context, "ListPartiteImportatori");

	}
	
	
	public String executeCommandListaAnimali(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-view")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		AnimaleList animaleList = new AnimaleList();
		PartitaCommerciale partita = null;
		boolean popup = false;
		if ((String) context.getRequest().getParameter("popup") != null) {

			popup = new Boolean((String) context.getRequest().getParameter(
					"popup")).booleanValue();
		}

		addModuleBean(context, "View Accounts", "Search Results");

		try {
			db = this.getConnection(context);

			partita = (PartitaCommerciale) context.getRequest().getAttribute(
					"partita");

			if (context.getRequest().getAttribute("nosenzavincolo") != null) {
				String noSenzaVincolo = "Nessun ";
				switch (partita.getIdTipoPartita()) {
				case Cane.idSpecie: {

					noSenzaVincolo += "cane ";
					break;
				}

				case Gatto.idSpecie: {
					noSenzaVincolo += "gatto ";
					break;
				}

				}
				noSenzaVincolo += "senza vincolo presente in questa partita!";
				context.getRequest().setAttribute("nosenzavincolo",
						noSenzaVincolo);
			}

			if (partita == null) {
				String id = (String) context.getRequest().getParameter(
						"idPartita");
				int idPartita = new Integer(id).intValue();
				partita = new PartitaCommerciale(db, idPartita);
			}
			context.getRequest().setAttribute("partita", partita);

			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context,
					"animaliListInfo");
			searchListInfo
					.setLink("PartiteCommerciali.do?command=ListaAnimali&idPartita="
							+ partita.getIdPartitaCommerciale()
							+ "&popup="
							+ popup);
			searchListInfo.setListView("all");
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

			animaleList.setStageId(searchListInfo
					.getCriteriaValue("searchcodeStageId"));
			animaleList.setIdPartita(partita.getIdPartitaCommerciale());
			
			//se sto aprendo la popup di aggiungi vincolo, includo solo animali Appartenenti alla partita commerciale
			
			if (context.getRequest().getParameter("popup")!=null && context.getRequest().getParameter("popup").equals("true")){
				ArrayList statiDaIncludere = new ArrayList();
				statiDaIncludere.add(42);
				animaleList.setStatiDaIncludere(statiDaIncludere);
			}
			
			animaleList.buildList(db);

			context.getRequest().setAttribute("animaleList", animaleList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");

		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "List");

	}

	public String executeCommandDettagliPartita(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		PartitaCommerciale partita = null;
		int idPartita = -1;
		try {

			db = this.getConnection(context);

			partita = (PartitaCommerciale) context.getRequest().getAttribute(
					"partita");

			if (partita == null) {
				String id = (String) context.getRequest().getParameter(
						"idPartita");

				if (id == null || ("").equals(id)) {
					 idPartita = ((Integer) context.getRequest()
							.getAttribute("idPartita")).intValue();
				} else {

					 idPartita = new Integer(id).intValue();
					
				}
				partita = new PartitaCommerciale(db, idPartita);
			}

			context.getRequest().setAttribute("partita", partita);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Details");

	}

	public String executeCommandAdd(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		PartitaCommerciale partita = null;
		int idProprietario = -1;
		String id = null;
		try {
			db = this.getConnection(context);

			if ((PartitaCommerciale) context.getRequest().getAttribute(
					"partita") != null) {
				partita = (PartitaCommerciale) context.getRequest()
						.getAttribute("partita");
			} else {
				 id = (String) context.getRequest().getParameter(
						"idPartita");
				 int idPartita = -1;
				 if (id == null){
					 idPartita = (Integer) context.getRequest().getAttribute("idPartita");
				 }else{
				
				 idPartita = new Integer(id).intValue();
				 }
				partita = new PartitaCommerciale(db, idPartita);
			}

			context.getRequest().setAttribute("partita", partita);

			int idSpecie = partita.getIdTipoPartita();
			

			
			
			if ((String) context.getRequest().getParameter("idLinea") != null
					&& !("-1").equals((String) context.getRequest()
							.getParameter("idLinea"))) {
				idProprietario = new Integer((String) context.getRequest()
						.getParameter("idLinea"));
			}else{
				idProprietario = partita.getIdImportatore();
			}
			
			
			Animale thisAnimale = (Animale) context.getRequest().getAttribute(
					"animale");
			
			if(idSpecie == 3){ //Partita mista
				if (thisAnimale == null)
					idSpecie = 1; //Imposto di default cane
				else
					idSpecie = thisAnimale.getIdSpecie(); //Ho già scelto la specie
			}
			
			context.getRequest().setAttribute("animale", thisAnimale);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			
			specieList.remove(1); //rimuovo furetti
			
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);
			razzaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			// Lookup esito
			LookupList esitoList = new LookupList(db, "lookup_esito_controlli");
			esitoList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoList", esitoList);

			// Lookup esito controllo laboratorio

			LookupList esitoControlloLaboratorioList = new LookupList(db,
					"lookup_esito_controlli_laboratorio");
			esitoControlloLaboratorioList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoControlloLaboratorioList",
					esitoControlloLaboratorioList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.buildList(db);
			mantelloList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);

			Cane newCane = (Cane) context.getRequest().getAttribute("Cane");
			Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
			Operatore proprietario = new Operatore();
			proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
					idProprietario);
			switch (idSpecie) {

			case Cane.idSpecie: {

				

				newCane.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newCane.setIdProprietario(idProprietario);
					newCane.setIdDetentore(idProprietario);
					newCane.setProprietario(proprietario);
					newCane.setDetentore(proprietario);
				}

				
				break;
			}
			case Gatto.idSpecie: {
				
				newGatto.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newGatto.setIdProprietario(idProprietario);
					newGatto.setIdDetentore(idProprietario);
					newGatto.setProprietario(proprietario);
					newGatto.setDetentore(proprietario);
				}
				
			}
			default:
				break;
			}
			
			
			if (context.getRequest().getAttribute("SalvaeClona")!=null)
				context.getRequest().setAttribute("SalvaeClona","OK");
			
			context.getRequest().setAttribute("Cane", newCane);
			context.getRequest().setAttribute("Gatto", newGatto);
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "AddAnimale");
	}

	public String executeCommandInsertAnimale(ActionContext context) throws SQLException {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Cane cane = null;
		Gatto gatto = null;
		boolean isValid = false;
		Animale thisAnimale = null;
		boolean recordInserted = false;
		int tipologiaRegistrazione = -1;

		// Integer orgId = null;
		Animale newAnimale = (Animale) context.getRequest().getAttribute(
				"Animale");

		try {

			db = this.getConnection(context);

			EsitoControllo esitoTatu = null;
			EsitoControllo esitoMc = null;
			

			PartitaCommerciale partita = new PartitaCommerciale(db, newAnimale
					.getIdPartitaCircuitoCommerciale());
			
			
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			
			if (newAnimale.getMicrochip() != null
					&& !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimale(newAnimale
						.getMicrochip(), user.getUserId());
			}

			if (newAnimale.getTatuaggio() != null
					&& !("").equals(newAnimale.getTatuaggio())) {
				esitoTatu = DwrUtil.verificaInserimentoAnimale(newAnimale
						.getTatuaggio(), user.getUserId());
			}
			
			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2 || (esitoMc.getIdEsito() == 4 )  ) 
							|| (esitoMc
							.getIdEsito() == 3 && user.getRoleId() == 24)) {
		if (esitoMc != null)
				context.getRequest().setAttribute("ErroreMicrochip",
						esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);
				context.getRequest().setAttribute("idPartita", partita.getIdPartitaCommerciale());

				return executeCommandAdd(context);

			}

			if (esitoTatu != null
					&& (esitoTatu.getIdEsito() == 2
							|| esitoTatu.getIdEsito() == 4 || (esitoTatu
									.getIdEsito() == 3 && user.getRoleId() == 24))) {
			if (esitoTatu != null)
				context.getRequest().setAttribute("ErroreTatuaggio",
						esitoTatu.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);
				context.getRequest().setAttribute("idPartita", partita.getIdPartitaCommerciale());

				return executeCommandAdd(context);

			}


//			PartitaCommerciale partita = new PartitaCommerciale(db, newAnimale
//					.getIdPartitaCircuitoCommerciale());


			newAnimale.setIdUtenteInserimento(user.getUserId());

			if (newAnimale.getMicrochip() != null
					&& !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimale(newAnimale
						.getMicrochip(), user.getUserId());
			}

			if (newAnimale.getTatuaggio() != null
					&& !("").equals(newAnimale.getTatuaggio())) {
				esitoTatu = DwrUtil.verificaInserimentoAnimale(newAnimale
						.getTatuaggio(), user.getUserId());
			}

			if (newAnimale.getIdSpecie() == Cane.idSpecie) {
				cane = (Cane) context.getRequest().getAttribute("Cane");
				// cane.insert(db);
			} else if (newAnimale.getIdSpecie() == Gatto.idSpecie) {
				gatto = (Gatto) context.getRequest().getAttribute("Gatto");
				// gatto.insert(db);
			}

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				thisAnimale = (Animale) cane;
				break;
			}
			case Gatto.idSpecie: {
				thisAnimale = (Animale) gatto;
				break;
			}
			}
			
			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals(
							"")
					&& context.getRequest().getParameter("doContinue").equals(
							"false")) {

				context.getRequest().setAttribute("Cane", cane);
				context.getRequest().setAttribute("Gatto", gatto);
				context.getRequest().setAttribute("animale", newAnimale);
				context.getRequest().setAttribute("partita", partita);

				return executeCommandAdd(context);
			}

			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2 || esitoMc.getIdEsito() == 4 || (esitoMc
							.getIdEsito() == 3 && user.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", cane);
				context.getRequest().setAttribute("Gatto", gatto);
				context.getRequest().setAttribute("ErroreMicrochip",
						esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAdd(context);

			}

			if (esitoTatu != null
					&& (esitoTatu.getIdEsito() == 2
							|| esitoTatu.getIdEsito() == 4 || (esitoTatu
							.getIdEsito() == 3 && user.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", cane);
				context.getRequest().setAttribute("Gatto", gatto);
				context.getRequest().setAttribute("ErroreTatuaggio",
						esitoTatu.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAdd(context);

			}
			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {
				thisAnimale.setIdUtenteInserimento(user.getUserId());
				recordInserted = thisAnimale.insert(db);
				if (esitoMc.getIdEsito() == 1 && recordInserted == true) {
					PreparedStatement pst = db
							.prepareStatement("update microchips set id_animale =? ,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getMicrochip());
					pst.execute();
				}
				if (esitoTatu != null && esitoTatu.getIdEsito() == 1
						&& recordInserted == true) {
					PreparedStatement pst = db
							.prepareStatement("update microchips set id_animale =? ,flag_secondo_microchip = true,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getTatuaggio());
					pst.execute();
				}

				EventoRegistrazioneBDU reg_bdu = (EventoRegistrazioneBDU) context
						.getRequest().getAttribute("EventoRegistrazioneBDU");
				reg_bdu.setIdTipologiaEvento(reg_bdu.idTipologiaDB);
				reg_bdu.setIdAnimale(thisAnimale.getIdAnimale());
				reg_bdu.setEnteredby(this.getUserId(context));
				reg_bdu.setModifiedby(this.getUserId(context));
				reg_bdu.insert(db);
				
				String[] esitoInvioSinaaf = null;
						
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
				esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),reg_bdu.getIdEvento()+"", "evento");
				context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[0]);
				context.getRequest().setAttribute("errore", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[1]);
				}
				tipologiaRegistrazione = reg_bdu.idTipologiaDB;
				
				if (context.getRequest().getParameter("microchip") != null
						&& !("").equals(context.getRequest().getParameter(
								"microchip"))) {
					EventoInserimentoMicrochip microchip = (EventoInserimentoMicrochip) context
							.getRequest().getAttribute(
									"EventoInserimentoMicrochip");
					microchip.setIdAnimale(thisAnimale.getIdAnimale());
					microchip.setEnteredby(this.getUserId(context));
					microchip.setModifiedby(this.getUserId(context));
					microchip.setIdTipologiaEvento(microchip.idTipologiaDB);
					microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					microchip.setDataInserimentoMicrochip((String)context.getRequest().getParameter("dataMicrochip"));
					microchip.setNumeroMicrochipAssegnato((String)context.getRequest().getParameter("microchip"));
					microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale.getIdVeterinarioMicrochip());
					microchip.insert(db);

				}

				if (context.getRequest().getParameter("numeroPassaporto") != null
						&& !("").equals(context.getRequest().getParameter(
								"numeroPassaporto"))) {
//					EventoRilascioPassaporto passaporto = (EventoRilascioPassaporto) context
//							.getRequest().getAttribute(
//								"EventoRilascioPassaporto");
					
					/**
					 * NON E' + necessario salvare il passaporto come registrazione
					 */
//					passaporto.setIdAnimale(thisAnimale.getIdAnimale());
//					passaporto.setIdTipologiaEvento(passaporto.idTipologiaDB);
//					passaporto.setEnteredby(this.getUserId(context));
//					passaporto.setModifiedby(this.getUserId(context));
//					passaporto.insert(db);
//					thisAnimale.setDataRilascioPassaporto(passaporto
//						.getDataRilascioPassaporto());
//					thisAnimale.setNumeroPassaporto(passaporto
//							.getNumeroPassaporto());
//					thisAnimale.updateStato(db);
					
					
					EventoRiconoscimentoPassaporto riconoscimentoPassaporto = (EventoRiconoscimentoPassaporto) context.getRequest().getAttribute(
							"EventoRiconoscimentoPassaporto");

					/*
					 * / Caso vet privati o ruolo anagrafecanina
					 */
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						riconoscimentoPassaporto.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						riconoscimentoPassaporto.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					riconoscimentoPassaporto.setIdAnimale(thisAnimale.getIdAnimale());
					riconoscimentoPassaporto.setIdTipologiaEvento(EventoRiconoscimentoPassaporto.idTipologiaDB);
					riconoscimentoPassaporto.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					riconoscimentoPassaporto.setEnteredby(this.getUserId(context));
					riconoscimentoPassaporto.setFlagPassaportoAttivo(true);
					riconoscimentoPassaporto.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					riconoscimentoPassaporto.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					riconoscimentoPassaporto.insert(db);

					thisAnimale.setDataRilascioPassaporto(riconoscimentoPassaporto.getDataRilascioPassaporto());
					thisAnimale.setNumeroPassaporto(riconoscimentoPassaporto.getNumeroPassaporto());
					thisAnimale.setDataScadenzaPassaporto(riconoscimentoPassaporto.getDataScadenzaPassaporto());
					thisAnimale.updateStato(db);
				}
				
				EventoInserimentoVaccinazioni rabbia = (EventoInserimentoVaccinazioni)context
				.getRequest().getAttribute(
						"EventoInserimentoVaccinazioni");
				
				rabbia.setIdTipoVaccino(EventoInserimentoVaccinazioni.antirabbica);
				rabbia.setDataVaccinazione((String)context.getRequest().getParameter("dataVaccino"));
				rabbia.setIdAnimale(thisAnimale.getIdAnimale());
				rabbia.setEnteredby(this.getUserId(context));
				rabbia.setModifiedby(this.getUserId(context));
				rabbia.setIdTipologiaEvento(rabbia.idTipologiaDB);
				rabbia.setSpecieAnimaleId(thisAnimale.getIdSpecie());
				rabbia.insert(db);
				
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
				esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),rabbia.getIdEvento()+"", "evento");
				context.getRequest().setAttribute("messaggio", "Registrazione di vaccinazione antirabbica: " + esitoInvioSinaaf[0]);
				context.getRequest().setAttribute("errore", "Registrazione di vaccinazione antirabbica: " + esitoInvioSinaaf[1]);
				}

				// if
				// (context.getRequest.getParameter("flagPresenzaEsitoControlloDocumentale"))

				tipologiaRegistrazione = 25; // Forzo tipologia registrazione a
												// "Registrazione a partita"

				// Aggiorno wkf privato / randagio
				RegistrazioniWKF r_wkf = new RegistrazioniWKF();
				r_wkf.setIdStato(thisAnimale.getStato());
				r_wkf.setIdRegistrazione(tipologiaRegistrazione);
				thisAnimale.setStato((r_wkf
						.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
						.getIdProssimoStato());
				thisAnimale.updateStato(db);
			}

			context.getRequest().setAttribute("idPartita",
					partita.getIdPartitaCommerciale());
			
			
			
			context.getRequest().setAttribute("SalvaeClona",context.getParameter("saveandclone"));
			if (context.getParameter("saveandclone")!= null && "1".equalsIgnoreCase(context.getParameter("saveandclone")))
			{
				context.getRequest().setAttribute("idPartita",
						partita.getIdPartitaCommerciale()+"");
				
				context.getRequest().setAttribute("partita",
						partita);
				
				context.getRequest().setAttribute("animale",
						thisAnimale);
				context.getRequest().setAttribute("SalvaeClona", "OK");
				
				
				
				return executeCommandAdd(context);
			}

		} catch (Exception e) {
			
			db.rollback();

			e.printStackTrace();
			context
				.getRequest()
				.setAttribute("Error",
						"Si è verificato un problema con l'inserimento dell'animale. Riprovare");
		return ("SystemError");

	} finally {

		db.setAutoCommit(true);
		db.close();
		this.freeConnection(context, db);

	}


		return getReturn(context, "Insert");
	}
	
	

	public String executeCommandRespingiPratica(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		//Connection dbImportatori = null;
		Connection db = null;
		try {

			
					//DbUtil.getConnection(ApplicationProperties.getProperty("IMPORTATORIDBNAME"), ApplicationProperties.getProperty("IMPORTATORIDBUSER"), ApplicationProperties.getProperty("IMPORTATORIDBPWD"), InetAddress.getByName("hostDbImportatori").getHostAddress());

				//dbImportatori = GestoreConnessioni.getConnectionImportatori(context);
			db = this.getConnection(context);
			db.setAutoCommit(false);
			String id = (String) context.getRequest().getParameter("idPartita");
			int idPartita = -1;
			if (id !=null)
			{
				idPartita =Integer.parseInt(id);
			}
			PartitaCommerciale partita = new PartitaCommerciale(db, idPartita );
			
//			PreparedStatement pst1 = db.prepareStatement("select * from aggiorna_stato_partita(?,?)");
//			pst1.setInt(1, Integer.parseInt(id));
//			pst1.setInt(2, 5);
//			pst1.execute();
			
			partita.setRespinta(db);
			db.commit();
		
			
			context.getRequest().setAttribute("idPartita", id);
			return executeCommandListaPartiteImportatori(context);
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally{
			//GestoreConnessioni.freeConnectionImportatori(dbImportatori);
			this.freeConnection(context, db);
		}
	}
	
	
	public String executeCommandListaAnimaliFromPartitaImportatori(ActionContext context)  {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		

		Connection dbImportatori = null;
				

		try {

			db = this.getConnection(context);
			dbImportatori = GestoreConnessioni.getConnectionImportatori(context);
			int idPartita= - 1 ;
			
			if (context.getParameter("idPartita")!=null)
			{
				idPartita =Integer.parseInt(context.getParameter("idPartita"));
			}else if (context.getRequest().getAttribute("idPartita") != null) {
				idPartita =Integer.parseInt((String) context.getRequest().getAttribute("idPartita"));
			}
			
			
			
			PartitaCommerciale partita = new PartitaCommerciale(db,idPartita );

			context.getRequest().setAttribute("partita", partita);
			
			

			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setIdPartita(idPartita);
			listaAnimali.buildListImportatori(dbImportatori,db);
			context.getRequest().setAttribute("animaleList",listaAnimali);

			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
		
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			GestoreConnessioni.freeConnection(dbImportatori);
		}

		return getReturn(context, "ListImportatori");
	
		
	
	}
	
	
	
	public String executeCommandValidate(ActionContext context) throws UnknownHostException, SQLException{


		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		

		Connection dbImportatori = null;
				

		try {

			db = this.getConnection(context);
			dbImportatori = GestoreConnessioni.getConnectionImportatori(context);
			int idPartita= - 1 ;
			
			if (context.getParameter("idPartita")!=null)
			{
				idPartita =Integer.parseInt(context.getParameter("idPartita"));
			
			  context.getRequest().setAttribute("idPartita", context.getParameter("idPartita"));
			
			
			PartitaCommerciale partita = new PartitaCommerciale(db,idPartita );

			context.getRequest().setAttribute("partita", partita);
			
			

			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setIdPartita(idPartita);
			listaAnimali.buildListImportatori(dbImportatori,db);


			Iterator i = listaAnimali.iterator();
			int countValidati = 0;
			
			while (i.hasNext()){
				Animale thisAnimale = (Animale) i.next();
				
			
				
				String check = context.getRequest()
				.getParameter("validate_" + thisAnimale.getMicrochip()); 
				
				if (check != null
				&& !("").equals(check)
				&& ("on").equals(check)){
					thisAnimale.updateValidazione(dbImportatori); //SU TABELLA IMPORTATORI PER ORA; POI LO TRASFERISCO IN BDU
					countValidati++;
				}
				
				
			}
			
			int nuovoCountValidati = partita.getNumeroAnimaliValidati() + countValidati;
			double percentuale_completamento = (nuovoCountValidati*100)/listaAnimali.size();
			
			partita.setPercentualeApprovati(percentuale_completamento);
			partita.setNumeroAnimaliValidati(nuovoCountValidati);
			
			partita.updateAnimaliValidati(db);
			

			}} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
		
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			GestoreConnessioni.freeConnection(dbImportatori);
		}

		if (context.getRequest().getParameter("importa") != null && ("si").equals(context.getRequest().getParameter("importa"))){
		return	executeCommandInsertAnimaleFromPartitaImportatori(context);
		}else{
		return executeCommandListaAnimaliFromPartitaImportatori(context);
		}
	
		
	
	
		
		
	}
	public String executeCommandInsertAnimaleFromPartitaImportatori(ActionContext context) throws SQLException  {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Cane cane = null;
		Gatto gatto = null;
		boolean isValid = false;
		Animale thisAnimale = null;
		boolean recordInserted = false;
		int tipologiaRegistrazione = -1;

		Connection dbImportatori = null;
				//DbUtil.getConnection(ApplicationProperties.getProperty("IMPORTATORIDBNAME"), ApplicationProperties.getProperty("IMPORTATORIDBUSER"), ApplicationProperties.getProperty("IMPORTATORIDBPWD"), InetAddress.getByName("hostDbImportatori").getHostAddress());

		
		

		try {

			dbImportatori = GestoreConnessioni.getConnectionImportatori(context);
			db = this.getConnection(context);
			db.setAutoCommit(false);
			int idPartita= - 1 ;
			
			if (context.getParameter("idPartita")!=null)
			{
				idPartita =Integer.parseInt(context.getParameter("idPartita"));
			}
			EsitoControllo esitoTatu = null;
			EsitoControllo esitoMc = null;

			
			/*inserimento della partita presa da importatori*/
			
			PartitaCommerciale partita = new PartitaCommerciale(db,idPartita );
			/*	partita.buildPartitaCommercialeImportatore(dbImportatori, idPartita);
			partita.insert(db);*/
			
			

			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setIdPartita(idPartita);
			listaAnimali.buildListImportatori(dbImportatori,db);
			
			Iterator<Animale> itAnimali = listaAnimali.iterator();
			
			while (itAnimali.hasNext())
			{
				Animale newAnimale = itAnimali.next();
				
			newAnimale.setIdPartitaCircuitoCommerciale(partita.getIdPartitaCommerciale());
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			newAnimale.setIdUtenteInserimento(user.getUserId());
			
		


			
			java.util.Date date = new java.util.Date();
			Timestamp now = new Timestamp(date.getTime());
			
			newAnimale.setDataRegistrazione(now);

			if (newAnimale.getIdSpecie() == Cane.idSpecie) {
				cane = new Cane(dbImportatori,newAnimale.getIdAnimale());
				
			} else if (newAnimale.getIdSpecie() == Gatto.idSpecie) {
				gatto = new Gatto(dbImportatori,newAnimale.getIdAnimale());
		
			}

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				thisAnimale = (Animale) cane;
				thisAnimale.setIdPartitaCircuitoCommerciale(partita.getIdPartitaCommerciale());
				break;
			}
			case Gatto.idSpecie: {
				thisAnimale = (Animale) gatto;
				thisAnimale.setIdPartitaCircuitoCommerciale(partita.getIdPartitaCommerciale());
				break;
			}
			}
			
		

		
			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {
                
				thisAnimale.setDataRegistrazione(new Timestamp(System.currentTimeMillis()));
				thisAnimale.setIdUtenteInserimento(getUserId(context));
				thisAnimale.setIdUtenteModifica(getUserId(context));
				thisAnimale.setFlagValidato(false);
				recordInserted = thisAnimale.insert(db);
				PreparedStatement pst = db
				.prepareStatement("update microchips set id_animale =? ,id_specie = ? where microchip =? ");
				pst.setInt(1, thisAnimale.getIdAnimale());
				pst.setInt(2, thisAnimale.getIdSpecie());
				pst.setString(3, thisAnimale.getMicrochip());
				pst.execute();
				

				EventoRegistrazioneBDU reg_bdu = (EventoRegistrazioneBDU) context
						.getRequest().getAttribute("EventoRegistrazioneBDU");
				reg_bdu.setIdTipologiaEvento(reg_bdu.idTipologiaDB);
				reg_bdu.setDataRegistrazione(new Timestamp(System.currentTimeMillis()));
				reg_bdu.setEnteredby(getUserId(context));
				reg_bdu.setModifiedby(getUserId(context));
				reg_bdu.setIdProprietario(newAnimale.getIdProprietario());
				reg_bdu.setIdDetentore(newAnimale.getIdProprietario());
				reg_bdu.setMicrochip(newAnimale.getMicrochip());
				reg_bdu.setIdAslRiferimento(newAnimale.getIdAslRiferimento());
				reg_bdu.setIdAnimale(thisAnimale.getIdAnimale());
				reg_bdu.insert(db);
				
				String[] esitoInvioSinaaf = null;
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),reg_bdu.getIdEvento()+"", "evento");
					context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[0]);
					context.getRequest().setAttribute("errore", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[1]);
				}
				
				tipologiaRegistrazione = reg_bdu.idTipologiaDB;
				
				if (newAnimale.getMicrochip() != null
						&& !("").equals(newAnimale.getMicrochip())) {
					EventoInserimentoMicrochip microchip = (EventoInserimentoMicrochip) context
							.getRequest().getAttribute(
									"EventoInserimentoMicrochip");
					microchip.setIdAnimale(thisAnimale.getIdAnimale());
					microchip.setEnteredby(this.getUserId(context));
					microchip.setIdTipologiaEvento(microchip.idTipologiaDB);
					microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					microchip.setDataInserimentoMicrochip((String)context.getRequest().getParameter("dataMicrochip"));
					microchip.setNumeroMicrochipAssegnato((String)context.getRequest().getParameter("microchip"));
					microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale.getIdVeterinarioMicrochip());
					microchip.setEnteredby(getUserId(context));
					microchip.setModifiedby(getUserId(context));
					microchip.setDataInserimentoMicrochip(newAnimale.getDataMicrochip());
					microchip.setMicrochip(newAnimale.getMicrochip());
					microchip.setIdAslRiferimento(newAnimale.getIdAslRiferimento());
					microchip.insert(db);

				}
				
				if (newAnimale.getDataVaccino() != null) {
					EventoInserimentoVaccinazioni rabbia = (EventoInserimentoVaccinazioni) context
					.getRequest().getAttribute(
					"EventoInserimentoVaccinazioni");
					
					if ((user.getSiteId() >0 &&user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						rabbia.setIdAslRiferimento(user.getSiteId());
					}else if (user.getSiteId() < 0){
						rabbia.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					
					
					
					rabbia
					.setIdTipoVaccino(EventoInserimentoVaccinazioni.antirabbica);
					rabbia.setDataVaccinazione(thisAnimale.getDataVaccino());
					rabbia.setDataScadenzaVaccino(thisAnimale.getDataScadenzaVaccino());
					rabbia.setNomeVaccino(thisAnimale.getNomeVaccino());
					rabbia.setNumeroLottoVaccino(thisAnimale.getNumeroLottoVaccino());
					rabbia.setProduttoreVaccino(thisAnimale.getProduttoreVaccino());
					rabbia.setIdAnimale(thisAnimale.getIdAnimale());
					rabbia.setEnteredby(getUserId(context));
					rabbia.setModifiedby(getUserId(context));
					rabbia.setIdTipologiaEvento(rabbia.idTipologiaDB);
					rabbia.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					rabbia.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					rabbia.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					rabbia.setMicrochip(newAnimale.getMicrochip());
					rabbia.setIdAslRiferimento(newAnimale.getIdAslRiferimento());
					rabbia.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),rabbia.getIdEvento()+"", "evento");
					context.getRequest().setAttribute("messaggio", "Registrazione di vaccinazione antirabbica: " + esitoInvioSinaaf[0]);
					context.getRequest().setAttribute("errore", "Registrazione di vaccinazione antirabbica: " + esitoInvioSinaaf[1]);
					}
				}

				if (newAnimale.getNumeroPassaporto() != null
						&& !("").equals(newAnimale.getNumeroPassaporto())) {
					EventoRilascioPassaporto passaporto = (EventoRilascioPassaporto) context
							.getRequest().getAttribute(
									"EventoRilascioPassaporto");
//					passaporto.setIdAnimale(thisAnimale.getIdAnimale());
//					passaporto.setIdTipologiaEvento(passaporto.idTipologiaDB);
//					passaporto.setNumeroPassaporto(newAnimale.getNumeroPassaporto());
//					passaporto.setDataRilascioPassaporto(newAnimale.getDataRilascioPassaporto());
//					passaporto.setMicrochip(newAnimale.getMicrochip());
//
//					passaporto.setEnteredby(getUserId(context));
//					passaporto.setModifiedby(getUserId(context));	
//					passaporto.setIdAslRiferimento(newAnimale.getIdAslRiferimento());
//					passaporto.insert(db);
					
					
//					
//					thisAnimale.setDataRilascioPassaporto(passaporto
//							.getDataRilascioPassaporto());
//					thisAnimale.setNumeroPassaporto(passaporto
//							.getNumeroPassaporto());
//					thisAnimale.updateStato(db);
//					thisAnimale.setDataRilascioPassaporto(passaporto
//							.getDataRilascioPassaporto());
//					thisAnimale.setNumeroPassaporto(passaporto
//							.getNumeroPassaporto());
//					thisAnimale.updateStato(db);
					
					
					
					EventoRiconoscimentoPassaporto riconoscimentoPassaporto = (EventoRiconoscimentoPassaporto) context.getRequest().getAttribute(
							"EventoRiconoscimentoPassaporto");

					/*
					 * / Caso vet privati o ruolo anagrafecanina
					 */
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						riconoscimentoPassaporto.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						riconoscimentoPassaporto.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					riconoscimentoPassaporto.setIdAnimale(thisAnimale.getIdAnimale());
					riconoscimentoPassaporto.setIdTipologiaEvento(EventoRiconoscimentoPassaporto.idTipologiaDB);
					riconoscimentoPassaporto.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					riconoscimentoPassaporto.setEnteredby(this.getUserId(context));
					riconoscimentoPassaporto.setFlagPassaportoAttivo(true);
					riconoscimentoPassaporto.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					riconoscimentoPassaporto.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					riconoscimentoPassaporto.insert(db);

					thisAnimale.setDataRilascioPassaporto(riconoscimentoPassaporto.getDataRilascioPassaporto());
					thisAnimale.setNumeroPassaporto(riconoscimentoPassaporto.getNumeroPassaporto());
					thisAnimale.setDataScadenzaPassaporto(riconoscimentoPassaporto.getDataScadenzaPassaporto());
					thisAnimale.updateStato(db);
				}

				// if
				// (context.getRequest.getParameter("flagPresenzaEsitoControlloDocumentale"))

				tipologiaRegistrazione = 25; // Forzo tipologia registrazione a
												// "Registrazione a partita"

				// Aggiorno wkf privato / randagio
				RegistrazioniWKF r_wkf = new RegistrazioniWKF();
				r_wkf.setIdStato(thisAnimale.getStato());
				r_wkf.setIdRegistrazione(tipologiaRegistrazione);
				thisAnimale.setStato((r_wkf
						.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
						.getIdProssimoStato());
				thisAnimale.updateStato(db);
				
				PreparedStatement pst1 = dbImportatori.prepareStatement("select * from aggiorna_stato_animale(?,?,'OK')");
				pst1.setInt(1, newAnimale.getIdAnimale());
				pst1.setInt(2, 1);
				pst1.execute();
			}
			}
			

			context.getRequest().setAttribute("partita",partita);
			
			
			partita.setApprovata(db);
			
			db.commit();
			
/*			PreparedStatement pst1 = dbImportatori.prepareStatement("select * from aggiorna_stato_partita(?,?)");
			pst1.setInt(1, idPartita);
			pst1.setInt(2, 4);
			pst1.execute();
			*/

		} catch (Exception e) {
			db.rollback();
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
		
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			GestoreConnessioni.freeConnection(dbImportatori);
		}

		return getReturn(context, "Insert");
	}

	public String executeCommandAggiungiAnimaliVincolati(ActionContext context) {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		try {

			db = this.getConnection(context);
			int idPartita = new Integer((String) context.getRequest()
					.getParameter("idPartita")).intValue();
			PartitaCommerciale thisPartita = new PartitaCommerciale(db,
					idPartita);

			ArrayList<String> microchips = new ArrayList<String>(Arrays
					.asList((String[]) context.getRequest().getParameterValues(
							"microchips[]")));
			thisPartita.setListMicrochipAnimaliConVincolo(microchips);

			context.getRequest()
					.setAttribute("PartitaCommerciale", thisPartita);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return "ClosePopupOK";
	}

	public String executeCommandLiberalizzaTutti(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		String id = (String) context.getRequest().getParameter("idPartita");
		int idPartita = new Integer(id).intValue();

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		AnimaleList animaleList = new AnimaleList();

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"animaliListInfo");
		searchListInfo
				.setLink("PartiteCommerciali.do?command=ListaAnimali&idPartita="
						+ idPartita);
		searchListInfo.setListView("all");
		try {
			db = this.getConnection(context);

			Evento thisEvento = new EventoRegistrazioneEsitoControlliCommerciali();
			context.getRequest().setAttribute("evento", thisEvento);

			PartitaCommerciale partita = new PartitaCommerciale(db, idPartita);

			context.getRequest().setAttribute("partita", partita);

			ArrayList<Integer> statiDaIncludere = new ArrayList<Integer>();

			statiDaIncludere.add(42);
			statiDaIncludere.add(43);
			statiDaIncludere.add(44);
			//statiDaIncludere.add(45); manca il mapping in registrazioni_wk (DECEDUTO)
			statiDaIncludere.add(46);
			statiDaIncludere.add(47);

			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

			animaleList.setStageId(searchListInfo
					.getCriteriaValue("searchcodeStageId"));
			animaleList.setIdPartita(idPartita);
			animaleList.setCheckFlagSulVincolo(true);
			animaleList.setFlagVincolato(false);
			animaleList.setStatiDaIncludere(statiDaIncludere);
			animaleList.buildList(db);

			if (animaleList.size() == 0) {
				context.getRequest().setAttribute("nosenzavincolo", true);
				return executeCommandListaAnimali(context);
			}

			context.getRequest().setAttribute("animaleList", animaleList);

			RegistrazioniWKF r_wkf = new RegistrazioniWKF();

			r_wkf.setIdRegistrazione(26);
			r_wkf.setIdProssimoStato(2);
			r_wkf.setDescrizioneRegistrazione("Controlli commerciali");

			ArrayList reg = new ArrayList();
			reg.add(r_wkf);
			LookupList registrazioniList = new LookupList(reg, 26); // Controllo
																	// commerciale
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "liberaSenzaVincolo");

	}

	public String executeCommandInserisciControlliLiberi(ActionContext context) throws SQLException {

		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		AnimaleList animaleList = new AnimaleList();
		int tipologiaRegistrazione = -1;

		addModuleBean(context, "View Accounts", "Search Results");

		try {
			db = this.getConnection(context);

			Evento dettagli_base = (Evento) context.getRequest().getAttribute(
					"Evento");

			String idPart = (String) context.getRequest().getParameter(
					"idPartita");
			int idPartita = new Integer(idPart).intValue();

			ArrayList<Integer> statiDaIncludere = new ArrayList<Integer>();

			statiDaIncludere.add(42);
			statiDaIncludere.add(43);
			statiDaIncludere.add(44);
			//statiDaIncludere.add(45); manca il mapping in registrazioni_wk (DECEDUTO)
			statiDaIncludere.add(46);
			statiDaIncludere.add(47);

			PartitaCommerciale partita = new PartitaCommerciale(db, idPartita);
			context.getRequest().setAttribute("partita", partita);

			animaleList.setIdPartita(idPartita);
			animaleList.setCheckFlagSulVincolo(true);
			animaleList.setFlagVincolato(false);
			animaleList.setStatiDaIncludere(statiDaIncludere);
			animaleList.buildList(db);

			EventoRegistrazioneEsitoControlliCommerciali commerciali = (EventoRegistrazioneEsitoControlliCommerciali) context
					.getRequest().getAttribute(
							"EventoRegistrazioneEsitoControlliCommerciali");
			context.getRequest().getAttribute(
					"flagPresenzaEsitoControlloIdentita");

			for (int h = 0; h < animaleList.size(); h++) {
				Animale thisAnimale = (Animale) animaleList.get(h);
				commerciali.setIdAnimale(thisAnimale.getIdAnimale());
				commerciali.setMicrochip(thisAnimale.getMicrochip());
				tipologiaRegistrazione = commerciali
				.setIdTipologiaDbByDecisione();
				commerciali.setIdDecisioneFinale(1); //liberalizza tutti
				commerciali.setEnteredby(user.getUserId());
				commerciali.setModifiedby(user.getUserId());
				commerciali.setIdStatoOriginale(thisAnimale.getStato()); //Aggiungi stato originale
				commerciali.insert(db);
				thisAnimale.updateControlliCommerciali(db, commerciali, true);
				thisAnimale.isFlagPresenzaEsitoControlloDocumentale();
				

				// Aggiorno dettagli animale e stato
				RegistrazioniWKF wkf = new RegistrazioniWKF();
				wkf.setIdStato(thisAnimale.getStato());
				wkf.setIdRegistrazione(tipologiaRegistrazione);
				wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db);
				thisAnimale.setStato(wkf.getIdProssimoStato());
				thisAnimale.updateStato(db);

			}

			context.getRequest().setAttribute("animaleList", animaleList);

			RegistrazioniWKF r_wkf = new RegistrazioniWKF();

			r_wkf.setIdRegistrazione(26);
			r_wkf.setIdProssimoStato(2);
			r_wkf.setDescrizioneRegistrazione("Controlli commerciali");

			ArrayList reg = new ArrayList();
			reg.add(r_wkf);
			LookupList registrazioniList = new LookupList(reg, 26); // Controllo
																	// commerciale
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);

		} catch (Exception e) {
			db.rollback();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			db.commit();
			this.freeConnection(context, db);
		}

		return executeCommandDettagliPartita(context);

	}

}
