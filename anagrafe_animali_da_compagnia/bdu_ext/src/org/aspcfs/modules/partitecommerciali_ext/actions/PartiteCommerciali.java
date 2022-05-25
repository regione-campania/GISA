/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.partitecommerciali_ext.actions;


import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali_ext.base.Animale;
import org.aspcfs.modules.anagrafe_animali_ext.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali_ext.base.Cane;
import org.aspcfs.modules.anagrafe_animali_ext.base.Gatto;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu_ext.base.ComuniAnagrafica;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale;
import org.aspcfs.modules.partitecommerciali_ext.base.PartiteCommercialiList;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class PartiteCommerciali extends CFSModule {
	
	
	public String executeCommandDefault(ActionContext context) {

		return executeCommandListaPartite(context);
	}
	
	
	public String executeCommandChiudiPratica(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Connection dbBdu = null;
		try {

			db = this.getConnection(context);
			dbBdu = this.getConnectionBdu(context);
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
		//	String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
			//dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			String id = (String) context.getRequest().getParameter("idPartita");
			PartitaCommerciale partita = new PartitaCommerciale(dbBdu, Integer.parseInt(id));
			partita.setIdStato(PartitaCommerciale.ID_STATO_CHIUSO);
			partita.setIdUtenteModifica(getUserId(context));
			partita.cambiaStatoPartita(dbBdu);
			context.getRequest().setAttribute("idPartita", id);
			return executeCommandDettagliPartita(context);
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
		}
	}
	
	
	public String executeCommandInviaPratica(ActionContext context) {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Connection dbBdu = null;
		try {
			dbBdu = this.getConnectionBdu(context);
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("idPartita");
			PartitaCommerciale partita = new PartitaCommerciale(dbBdu, Integer.parseInt(id));
			partita.setIdStato(PartitaCommerciale.ID_STATO_INVIATO);
			partita.setIdUtenteModifica(getUserId(context));
			partita.cambiaStatoPartita(dbBdu);
			context.getRequest().setAttribute("idPartita", id);
			return executeCommandDettagliPartita(context);
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
		}
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

			LookupList siteList = new LookupList(db, "lookup_site_id");
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
			partita.setIdStato(PartitaCommerciale.ID_STATO_APERTO);
			
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
			LookupList siteList = new LookupList(db, "lookup_site_id");
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
			LookupList siteList = new LookupList(db, "lookup_site_id");
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
			LookupList siteList = new LookupList(db, "lookup_site_id");
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

	public String executeCommandListaPartite(ActionContext context)  {
		if (!hasPermission(context, "partite-commerciali-view")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection dbBDU = null;
		Connection db = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int idImportatore = user.getUserRecord().getIdImportatore();
		
		PartiteCommercialiList partiteList = new PartiteCommercialiList();

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"partiteListInfo");
		searchListInfo.setLink("PartiteCommerciali.do?command=ListaPartite");
		searchListInfo.setListView("all");
		try {
			dbBDU = this.getConnectionBdu(context);
			
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
			int i = 0;
			//dbBDU = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			db = this.getConnection(context);
			partiteList.setPagedListInfo(searchListInfo);
			
			partiteList.setIdImportatore(idImportatore);
			partiteList.setIdAslRiferimento(user.getSiteId());
			partiteList.buildList(dbBDU);

			context.getRequest().setAttribute("partiteList", partiteList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			LookupList statiList = new LookupList();
			statiList.setTable("lookup_stato_partita");
			statiList.buildList(db);
			
			context.getRequest().setAttribute("StatiList", statiList);

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
			this.freeConnectionBdu(context, dbBDU);
			//DbUtil.chiudiConnessioneJDBC(null, dbBDU);
		}

		return getReturn(context, "ListPartite");

	}

	
	public String executeCommandDettagliPartita(ActionContext context)  {
		if (!hasPermission(context, "partite-commerciali-edit")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Connection dbBdu = null;
		
		PartitaCommerciale partita = null;
		int idPartita = -1;
		try {

			db = this.getConnection(context);
			dbBdu = this.getConnectionBdu(context);

//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			
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
				partita = new PartitaCommerciale(dbBdu, idPartita);
			}

			context.getRequest().setAttribute("partita", partita);

			// lookups
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			LookupList nazioniList = new LookupList(db,
					"lookup_nazioni_partite_commerciali");
			nazioniList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setIdPartita(partita.getIdPartitaCommerciale());
			
			
			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(dbBdu);
		

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.buildList(dbBdu);
			
			LookupList statiList = new LookupList();
			statiList.setTable("lookup_stato_partita");
			statiList.buildList(db);
			
			context.getRequest().setAttribute("StatiList", statiList);
			


			LookupList tagliaList = new LookupList(dbBdu, "lookup_taglia");
		

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");

			mantelloList.buildList(dbBdu);
			
			List<Animale> list =listaAnimali.buildList(db,mantelloList,tagliaList,razzaList,specieList);
			
			//List<Utente> utentiList = criteria.addOrder( Order.desc("entered") ).list();
			context.getRequest().setAttribute("list", list);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
			//DbUtil.chiudiConnessioneJDBC(null, dbBdu);
		}

		return getReturn(context, "Details");

	}

	public String executeCommandAdd(ActionContext context)  {

		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		
		Connection dbBdu = null;
		PartitaCommerciale partita = null;
		int idProprietario = -1;
		try {
			db = this.getConnection(context);
			dbBdu = this.getConnectionBdu(context);
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			
			
			if ((PartitaCommerciale) context.getRequest().getAttribute(
					"partita") != null) {
				partita = (PartitaCommerciale) context.getRequest()
						.getAttribute("partita");
			} else {
				String id = (String) context.getRequest().getParameter(
						"idPartita");
				int idPartita = new Integer(id).intValue();
				partita = new PartitaCommerciale(dbBdu, idPartita);
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

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.setSpecieToExclude(3); //Furetto
			specieList.buildList(dbBdu);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(dbBdu);
			razzaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);


			LookupList tagliaList = new LookupList(dbBdu, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.buildList(dbBdu);
			mantelloList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			
			LookupList veterinariPrivati = new LookupList(db, "elenco_veterinari");
			veterinariPrivati.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariPrivatiList", veterinariPrivati);

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
			
			

			context.getRequest().setAttribute("Cane", newCane);
			context.getRequest().setAttribute("Gatto", newGatto);
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
			//DbUtil.chiudiConnessioneJDBC(null, dbBdu);
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

	public String executeCommandInsertAnimale(ActionContext context)  {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Connection dbBdu = null;
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
			dbBdu = this.getConnectionBdu(context);
			
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
			
			
			EsitoControllo esitoMc = null;
			PartitaCommerciale partita = new PartitaCommerciale(dbBdu, newAnimale
					.getIdPartitaCircuitoCommerciale());

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");

			newAnimale.setIdUtenteInserimento(user.getUserId());

			if (newAnimale.getMicrochip() != null
					&& !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimaleByMC(newAnimale
						.getMicrochip());
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
			
			
			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2 )) {
				context.getRequest().setAttribute("Cane", cane);
				context.getRequest().setAttribute("Gatto", gatto);
				if(esitoMc!=null)
				context.getRequest().setAttribute("ErroreMicrochip",
						esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);
				context.getRequest().setAttribute("partita", partita);
				return executeCommandAdd(context);

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

		
			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {

				recordInserted = thisAnimale.insert(db);

			}

			context.getRequest().setAttribute("idPartita",
					partita.getIdPartitaCommerciale());

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
			//DbUtil.chiudiConnessioneJDBC(null, dbBdu);
		}

		return getReturn(context, "Insert");
	}
	
	
	public String executeCommandInserteClonaAnimale(ActionContext context)  {
		if (!hasPermission(context, "partite-commerciali-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		Connection dbBdu = null;
		Cane cane = null;
		Gatto gatto = null;
		boolean isValid = false;
		Animale thisAnimale = null;
		boolean recordInserted = false;
		int tipologiaRegistrazione = -1;

		// Integer orgId = null;
		Animale newAnimale = (Animale) context.getRequest().getAttribute(
				"Animale");
		PartitaCommerciale partita = null ;
		try {

			db = this.getConnection(context);
			dbBdu = this.getConnectionBdu(context);
			
			EsitoControllo esitoMc = null;
			 partita = new PartitaCommerciale(dbBdu, newAnimale
					.getIdPartitaCircuitoCommerciale());

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");

			newAnimale.setIdUtenteInserimento(user.getUserId());

			if (newAnimale.getMicrochip() != null
					&& !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimaleByMC(newAnimale
						.getMicrochip());
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
			
			
			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2 || esitoMc.getIdEsito() == 4 || (esitoMc
							.getIdEsito() == 3 && user.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", cane);
				context.getRequest().setAttribute("Gatto", gatto);
				if(esitoMc!=null)
				context.getRequest().setAttribute("ErroreMicrochip",
						esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);
				context.getRequest().setAttribute("partita", partita);
				return executeCommandAdd(context);

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

		
			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {

				recordInserted = thisAnimale.insert(db);

			}

			context.getRequest().setAttribute("idPartita",
					partita.getIdPartitaCommerciale());

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		}finally {
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, dbBdu);
			//DbUtil.chiudiConnessioneJDBC(null, dbBdu);
			
		}

		context.getRequest().setAttribute("Cane", cane);
		context.getRequest().setAttribute("Gatto", gatto);
		context.getRequest().setAttribute("animale", newAnimale);
		context.getRequest().setAttribute("partita", partita);

		return executeCommandAdd(context);
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
	
	
	public String executeCommandAggiornaDataArrivoEffettivo(ActionContext context) {
		
		
		if (!hasPermission(context, "partite-commerciali-view")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection dbBdu = null;

		try {
			int idPartita = -1;
			dbBdu = this.getConnectionBdu(context);
			
//			String bdu_db_name = ApplicationProperties.getProperty("BDU_DBNAME");
//			String bdu_db_user = ApplicationProperties.getProperty("BDU_DBUSER");
//			String bdu_db_pwd = ApplicationProperties.getProperty("BDU_DBPWD");
//			String bdu_db_host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			dbBdu = DbUtil.getConnection(bdu_db_name, bdu_db_user, bdu_db_pwd, bdu_db_host);
//			
			if ( context.getRequest()
					.getParameter("idpartita") != null && !("").equals( context.getRequest()
					.getParameter("idpartita")) ){
			 idPartita = new Integer((String) context.getRequest()
					.getParameter("idpartita")).intValue();
			}
			PartitaCommerciale thisPartita = new PartitaCommerciale(dbBdu,
					idPartita);

			String data_arrivo = (String) context.getRequest().getParameter("dataArrivoEffettiva");
			thisPartita.setDataArrivoEffettiva(data_arrivo);
			thisPartita.setIdUtenteModifica(getUserId(context));
			thisPartita.updateDataArrivoEffettivo(dbBdu);


			context.getRequest().setAttribute("partita", thisPartita);
			return executeCommandDettagliPartita(context);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally {
			this.freeConnectionBdu(context, dbBdu);
			//DbUtil.chiudiConnessioneJDBC(null, dbBdu);
		}
		

	}

	

}
