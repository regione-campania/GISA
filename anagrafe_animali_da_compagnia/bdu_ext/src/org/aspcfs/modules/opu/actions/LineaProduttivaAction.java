/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale;
import org.aspcfs.modules.praticacontributi.base.PraticaDWR;
import org.aspcfs.modules.praticacontributi.base.PraticaList;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoCanile;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.Parameter;
import org.aspcfs.utils.ParameterUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class LineaProduttivaAction extends CFSModule {

	public String executeCommandSearch(ActionContext context) {
		Connection db = null;

		try {
			// System.out.println("Ciao Pippo qwewerghqwer");
			db = this.getConnection(context);
			LineaProduttivaList lpList = new LineaProduttivaList();
			PagedListInfo lpInfo = this.getPagedListInfo(context, "SearchLineaProduttiva");
			lpInfo.setLink("LineaProduttivaAction.do?command=Search");
			lpInfo.setSearchCriteria(lpList, context);
			lpInfo.setColumnToSortBy("lp.tipo_iter,lp.id_categoria");
			lpList.setPagedListInfo(lpInfo);
			lpList.setTipoSelezione(context.getRequest().getParameter("tipoSelezione"));
			ArrayList<Integer> lineeDaEscludere = new ArrayList<Integer>();

			if (getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) {
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindaco);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneSindacoFR);
				lineeDaEscludere.add(LineaProduttiva.idAggregazioneColonia);
			}

			lpList.setIdLineaProduttivaDaEscludere(lineeDaEscludere);

			lpList.buildList(db);

			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);

			LookupList listaMacroCategoria = new LookupList(db, "opu_lookup_macrocategorie_linee_produttive");
			listaMacroCategoria.addItem(-1, "-SELEZIONA CATEGORIA-");

			LookupList listaCategoria = new LookupList(db, "opu_lookup_aggregazioni_linee_produttive");
			listaCategoria.addItem(-1, "-SELEZIONA CATEGORIA-");

			context.getRequest().setAttribute("ListaMacroCategoria", listaMacroCategoria);
			context.getRequest().setAttribute("listaCategoria", listaCategoria);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return ("SearchOK");
		return getReturn(context, "Search");
	}

	public String executeCommandScegliLineaProduttiva(ActionContext context) {

		Connection db = null;
		try {
			db = getConnection(context);

			String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList();
			if (lineeProduttiveSelezionate != null && lineeProduttiveSelezionate.length > 0) {
				for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
					if (!lineeProduttiveSelezionate[i].equals("-1")) {
						LineaProduttiva lp = new LineaProduttiva(db, Integer.parseInt(lineeProduttiveSelezionate[i]));
						listaLineeProduttive.add(lp);
					}
				}

			}

			listaLineeProduttive.setTipoSelezione(context.getParameter("tipoSelezione"));

			context.getRequest().setAttribute("LineeProduttiveList", listaLineeProduttive);

			if (context.getRequest().getParameter("tipoRegistrazione") != null)
				context.getRequest().setAttribute("tipoRegistrazione",
						context.getRequest().getParameter("tipoRegistrazione"));

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ClosePopupOK");
	}

	public String executeCommandListaAnimali(ActionContext context) {

		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);

		try {

			String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");

			if (tempLineaProduttivaId == null) {
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
			}

			// String iter = context.getRequest().getParameter("tipo");
			Integer lineaPId = null;

			if (tempLineaProduttivaId != null) {
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();

			if ("animali".equals(context.getSession().getAttribute("previousSearchType"))
					|| "accounts".equals(context.getSession().getAttribute("previousSearchType"))) {
				// reset pagedListInfo
				this.deletePagedListInfo(context, "animaliListInfo");
			}
			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "animaliListInfo");
			searchListInfo.setLink("LineaProduttivaAction.do?command=ListaAnimali&idLinea=" + lineaPId);
			searchListInfo.setListView("all");

			db = this.getConnection(context);

			Operatore operatore = new Operatore();
			operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("operatore", operatore);

			LookupList lookupSpecie = new LookupList(db, "lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
			
			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("tagliaList", tagliaList);
			
			// Build the organization list
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

			animaleList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
			HashMap mapSaved = new HashMap();
			if (this.getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) {
				mapSaved = searchListInfo.getSavedCriteria();
				mapSaved.put("searchcodeIdUtenteInserimento", String.valueOf(this.getActualUserId(context)));
				searchListInfo.setSavedCriteria(mapSaved);
			}

			// animaleList.setIdUtenteInserimento(this.getUserId(context));

			searchListInfo.setSearchCriteria(animaleList, context);

			if ("my".equals(searchListInfo.getListView())) {
				// operatoreList.setOwnerId(this.getUserId(context));
			}
			if (animaleList.getIdAsl() == -1) {
				// animaleList.setIdAsl(this.getUserSiteId(context));
				// organizationList.setIncludeOrganizationWithoutSite(false);
			} else if (animaleList.getIdAsl() == -1) {
				// TUTTE LE ASLanimaleList.setIncludeAllAsl(true);
			}
			// fetching criterea for account status (active, disabled or
			// any)
			// int enabled = searchListInfo.getFilterKey("listFilter2");

			animaleList.setId_proprietario_o_detentore(lineaPId);

			animaleList.buildList(db);

			context.getRequest().setAttribute("animaleList", animaleList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			context.getSession().setAttribute("previousSearchType", "animaliProprietari");

			// Action di provenienza
			String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

			return getReturn(context, "List");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Operatore newOperatoreLineaProduttiva = null;
		try {

			String tempLineaProduttivaId = context.getRequest().getParameter("lineaId");
			if (tempLineaProduttivaId == null) {
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("lineaId");
			}
			if (tempLineaProduttivaId == null) {
				tempLineaProduttivaId = (String) context.getRequest().getParameter("idLinea");
			}

			// String iter = context.getRequest().getParameter("tipo");
			Integer lineaPId = null;

			if (tempLineaProduttivaId != null) {
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			db = this.getConnection(context);

			// newOperatore = new Operatore(db, tempid);
			newOperatoreLineaProduttiva = new Operatore();
			newOperatoreLineaProduttiva.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatoreLineaProduttiva);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, ((UserBean) context.getSession()
					.getAttribute("User")).getSiteId());

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);

			return getReturn(context, "Details");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandPrepareMovimentazioneDiMassa(ActionContext context) {

		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Connection newConnection = null; //per evitare timeout in fase di salvataggio
		SystemStatus systemStatus = this.getSystemStatus(context);
		boolean check = false;

		try {

			String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");

			if (tempLineaProduttivaId == null) {
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
			}

			// String iter = context.getRequest().getParameter("tipo");
			Integer lineaPId = null;

			if (tempLineaProduttivaId != null) {
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();

			if ("animali".equals(context.getSession().getAttribute("previousSearchType"))
					|| "accounts".equals(context.getSession().getAttribute("previousSearchType"))) {
				// reset pagedListInfo
				this.deletePagedListInfo(context, "animaliListInfo");
			}
			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "animaliListInfo");
			searchListInfo.setLinkForSubmit("LineaProduttivaAction.do?command=PrepareMovimentazioneDiMassa&idLinea="
					+ lineaPId);
			searchListInfo.setLink("LineaProduttivaAction.do?command=PrepareMovimentazioneDiMassa&idLinea=" + lineaPId);
			searchListInfo.setListView("all");

			db = this.getConnection(context);

			HashMap<Integer, Boolean> map = searchListInfo.getSelectedItems();

			context.getRequest().getParameter("aa");

			animaleList = (context.getSession().getAttribute("animaleList") != null) ? (AnimaleList) (context
					.getSession().getAttribute("animaleList")) : new AnimaleList();
			
			ArrayList statiDaEscludere = new ArrayList();

			statiDaEscludere.add(42);
			statiDaEscludere.add(46);
			statiDaEscludere.add(47);
			statiDaEscludere.add(43);
			statiDaEscludere.add(44);
			statiDaEscludere.add(45);
			
			if (context.getRequest().getParameter("save") != null
					&& ("true").equals(context.getRequest().getParameter("save")) && 
					context.getRequest().getParameter("tutti") != null && !("").equals(context.getRequest().getParameter("tutti"))){
				
				check = true;
				animaleList = new AnimaleList();
				animaleList.setStatiDaEscludere(statiDaEscludere);
				animaleList.setFlagDecesso(false);
				animaleList.setId_proprietario_o_detentore(lineaPId);
				animaleList.setBuildProprietario(false);
				animaleList.buildList(db);
				
			}

			if (animaleList != null && animaleList.size() > 0) {

				int numero_cani = animaleList.size();
				boolean valueCane = false;
				// ArrayList<String> microchips = new ArrayList<String>();
				for (int i = 0; i < numero_cani; i++) {
					Animale this_cane = (Animale) animaleList.get(i);
					if (!this_cane.isFlagFurto()){
				//	System.out.println(this_cane.getIdAnimale());
					valueCane = new Boolean((String) context.getRequest().getParameter(
							"checkCane" + this_cane.getIdAnimale())).booleanValue();
					if (valueCane || (check) ){
						map.put(Integer.valueOf(this_cane.getIdAnimale()), new Boolean(valueCane));

					}else{
						map.remove(Integer.valueOf(this_cane.getIdAnimale()));
					}
				}
				}
			}

			searchListInfo.setSelectedItems(map);

			animaleList = new AnimaleList();
			



			Operatore operatore = new Operatore();
			operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("operatore", operatore);

			LookupList lookupSpecie = new LookupList(db, "lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);

			Operatore newoperatore = new Operatore();

			int idnewlinea = -1;
			if (context.getRequest().getParameter("idOperatoreAdded") != null
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("")
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("-1")) {
				idnewlinea = Integer.parseInt(context.getRequest().getParameter("idOperatoreAdded"));
				newoperatore.queryRecordOperatorebyIdLineaProduttiva(db, idnewlinea);
			}

			context.getSession().setAttribute("newoperatore", newoperatore);
			context.getSession().setAttribute("idLp", context.getRequest().getParameter("idOperatoreAdded"));

			if (context.getRequest().getParameter("save") != null
					&& ("true").equals(context.getRequest().getParameter("save"))) {
				
				

				// Controllo asl nuovo operatore
				Stabilimento stabnew = (Stabilimento) newoperatore.getListaStabilimenti().get(0);

				Operatore oldProprietario = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
				Stabilimento stabold = (Stabilimento) oldProprietario.getListaStabilimenti().get(0);

				for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
					
					newConnection = GestoreConnessioni.getConnection();
					Integer key = entry.getKey();
					Animale thisAnimale = new Animale(newConnection, key);
					
					if (stabnew.getIdAsl() == stabold.getIdAsl()) {
						
						EventoTrasferimento eventoDaInserire = new EventoTrasferimento();
						eventoDaInserire.setEnteredby(getUserId(context));
						eventoDaInserire.setModifiedby(getUserId(context));
						eventoDaInserire.setIdProprietarioCorrente(lineaPId);
						eventoDaInserire.setIdDetentoreCorrente(lineaPId);
						eventoDaInserire.setOrigineInserimento(1);
						eventoDaInserire.setIdAnimale(thisAnimale.getIdAnimale());
						eventoDaInserire.setMicrochip(thisAnimale.getMicrochip());
						eventoDaInserire.setSpecieAnimaleId(thisAnimale.getIdSpecie());
						eventoDaInserire.setIdAslRiferimento(getUserAsl(context));
						eventoDaInserire.setIdStatoOriginale(thisAnimale.getStato());

						eventoDaInserire.setDataTrasferimento(context.getRequest().getParameter("dataMovimentazione"));
						eventoDaInserire.setIdAslNuovoProprietario(stabnew.getIdAsl());
						eventoDaInserire.setIdAslVecchioProprietario(stabold.getIdAsl());
						eventoDaInserire.setIdProprietario(idnewlinea);
						eventoDaInserire.setIdVecchioProprietario(lineaPId);
						eventoDaInserire.setIdDetentore(idnewlinea);
						eventoDaInserire.setIdVecchioDetentore(lineaPId);
						
						eventoDaInserire.salvaRegistrazione(getUserId(context), getUserRole(context), getUserAsl(context), thisAnimale, newConnection);
					}
					if (stabnew.getIdAsl() != stabold.getIdAsl()) {
						
						EventoCessione eventoDaInserire = new EventoCessione();
						eventoDaInserire.setEnteredby(getUserId(context));
						eventoDaInserire.setModifiedby(getUserId(context));
						eventoDaInserire.setIdProprietarioCorrente(lineaPId);
						eventoDaInserire.setIdDetentoreCorrente(lineaPId);
						eventoDaInserire.setOrigineInserimento(1);
						eventoDaInserire.setIdAnimale(thisAnimale.getIdAnimale());
						eventoDaInserire.setMicrochip(thisAnimale.getMicrochip());
						eventoDaInserire.setSpecieAnimaleId(thisAnimale.getIdSpecie());
						eventoDaInserire.setIdAslRiferimento(getUserAsl(context));

						eventoDaInserire.setDataCessione(context.getRequest().getParameter("dataMovimentazione"));
						eventoDaInserire.setIdAslNuovoProprietario(stabnew.getIdAsl());
						eventoDaInserire.setIdAslVecchioProprietario(stabold.getIdAsl());
						eventoDaInserire.setIdProprietario(idnewlinea);
						eventoDaInserire.setIdVecchioProprietario(lineaPId);
						eventoDaInserire.setIdNuovoDetentore(idnewlinea);
						eventoDaInserire.setIdVecchioDetentore(lineaPId);
						
						eventoDaInserire.salvaRegistrazione(getUserId(context), getUserRole(context), getUserAsl(context), thisAnimale, newConnection);

					}
					
					GestoreConnessioni.freeConnection(newConnection);
				}
				
				
				context.getSession().removeAttribute("animaleList");
				
				context.getRequest().setAttribute("idLinea", lineaPId);
				return executeCommandListaAnimali(context);

			} else {
				
				context.getRequest().setAttribute("dataMovimentazione", context.getRequest().getParameter("dataMovimentazione"));
				context.getRequest().setAttribute("tutti", context.getRequest().getParameter("tutti"));

				animaleList.setStatiDaEscludere(statiDaEscludere);
				
				animaleList.setFlagDecesso(false);
				animaleList.setPagedListInfo(searchListInfo);
				animaleList.setMinerOnly(false);
				animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

				animaleList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(animaleList, context);

				if ("my".equals(searchListInfo.getListView())) {
					// operatoreList.setOwnerId(this.getUserId(context));
				}
				if (animaleList.getIdAsl() == -1) {
					animaleList.setIdAsl(this.getUserSiteId(context));
					// organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (animaleList.getIdAsl() == -1) {
					// TUTTE LE ASLanimaleList.setIncludeAllAsl(true);
				}

				animaleList.setId_proprietario_o_detentore(lineaPId);

				animaleList.buildList(db);

				context.getRequest().setAttribute("animaleList", animaleList);
				context.getSession().setAttribute("animaleList", animaleList);

				LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
				context.getRequest().setAttribute("statoList", statoList);

				context.getSession().setAttribute("previousSearchType", "animaliProprietari");

				return getReturn(context, "MovimentazioneDiMassaList");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			if (newConnection != null)
				GestoreConnessioni.freeConnection(newConnection);
			this.freeConnection(context, db);
		}

	}
	
	
	
	public String executeCommandPrepareTrasferimentoAnimaliMassivo(ActionContext context) 
	{

		if (!hasPermission(context, "trasferimento_animali_massivo-view")) 
		{
			return ("PermissionError");
		}
		Connection db = null;
		Connection newConnection = null; //per evitare timeout in fase di salvataggio
		SystemStatus systemStatus = this.getSystemStatus(context);
		boolean check = false;

		try
		{
			String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");

			if (tempLineaProduttivaId == null) 
			{
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
			}

			Integer lineaPId = null;
			if (tempLineaProduttivaId != null) 
			{
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();

			if ("animali".equals(context.getSession().getAttribute("previousSearchType")) || "accounts".equals(context.getSession().getAttribute("previousSearchType"))) 
			{
				this.deletePagedListInfo(context, "animaliListInfo");
			}
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "animaliListInfo");
			searchListInfo.setLinkForSubmit("LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivoSave&idLinea="+ lineaPId);
			searchListInfo.setLink("LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivo&idLinea=" + lineaPId);
			searchListInfo.setListView("all");
			searchListInfo.setItemsPerPage(10000);
			searchListInfo.setCurrentOffset(10000);
			
			db = this.getConnection(context);

			animaleList = (context.getSession().getAttribute("animaleList") != null) ? (AnimaleList) (context.getSession().getAttribute("animaleList")) : new AnimaleList();
			
			ArrayList statiDaIncludere = new ArrayList();
			statiDaIncludere.add(2);
			statiDaIncludere.add(8);
			statiDaIncludere.add(20);
			statiDaIncludere.add(3);
			statiDaIncludere.add(9);
			statiDaIncludere.add(10);
			
			animaleList = new AnimaleList();
			animaleList.setStatiDaIncludere(statiDaIncludere);
			animaleList.setFlagDecesso(false);
			animaleList.setFlagSmarrimento(false);
			animaleList.setFlagFurto(false);
			animaleList.setId_detentore(lineaPId);
			animaleList.setBuildProprietario(false);
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
			animaleList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
			animaleList.buildList(db);
			
			
			searchListInfo.setSearchCriteria(animaleList, context);

			Operatore operatore = new Operatore();
			operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("operatore", operatore);

			Operatore newoperatore = new Operatore();

			int idnewlinea = -1;
			if (context.getRequest().getParameter("idOperatoreAdded") != null
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("")
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("-1")) {
				idnewlinea = Integer.parseInt(context.getRequest().getParameter("idOperatoreAdded"));
				newoperatore.queryRecordOperatorebyIdLineaProduttiva(db, idnewlinea);
			}

			LookupList lookupSpecie = new LookupList(db, "lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
			context.getSession().setAttribute("newoperatore", newoperatore);
			context.getSession().setAttribute("idLp", context.getRequest().getParameter("idOperatoreAdded"));
			context.getRequest().setAttribute("dataTrasferimento", context.getRequest().getParameter("dataTrasferimento"));
			context.getRequest().setAttribute("tutti", context.getRequest().getParameter("tutti"));

			if (animaleList.getIdAsl() == -1) 
			{
				animaleList.setIdAsl(this.getUserSiteId(context));
			} 

			context.getRequest().setAttribute("animaleList", animaleList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			context.getSession().setAttribute("previousSearchType", "animaliProprietari");

			return getReturn(context, "TrasferimentoAnimaliMassivo");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			if (newConnection != null)
				GestoreConnessioni.freeConnection(newConnection);
			this.freeConnection(context, db);
		}
	}
	
	
	
	public String executeCommandPrepareTrasferimentoAnimaliMassivoSave(ActionContext context) {

		if (!hasPermission(context, "trasferimento_animali_massivo-view")) 
		{
			return ("PermissionError");
		}
		Connection db = null;
		Connection newConnection = null; //per evitare timeout in fase di salvataggio
		SystemStatus systemStatus = this.getSystemStatus(context);

		try
		{

			String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");

			if (tempLineaProduttivaId == null) 
			{
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
			}

			Integer lineaPId = null;
			if (tempLineaProduttivaId != null) 
			{
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();

			if ("animali".equals(context.getSession().getAttribute("previousSearchType")) || "accounts".equals(context.getSession().getAttribute("previousSearchType"))) 
			{
				this.deletePagedListInfo(context, "animaliListInfo");
			}
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "animaliListInfo");
			searchListInfo.setLinkForSubmit("LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivoSave&idLinea="+ lineaPId);
			searchListInfo.setLink("LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivo&idLinea=" + lineaPId);
			searchListInfo.setListView("all");
			searchListInfo.setItemsPerPage(10000);
			searchListInfo.setCurrentOffset(10000);

			db = this.getConnection(context);

			animaleList = (context.getSession().getAttribute("animaleList") != null) ? (AnimaleList) (context.getSession().getAttribute("animaleList")) : new AnimaleList();
			
			ArrayList statiDaIncludere = new ArrayList();
			statiDaIncludere.add(2);
			statiDaIncludere.add(8);
			statiDaIncludere.add(20);
			statiDaIncludere.add(3);
			statiDaIncludere.add(9);
			statiDaIncludere.add(10);
			
			Operatore newoperatore = new Operatore();
			
			Operatore operatore = new Operatore();
			operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("operatore", operatore);

			int idnewlinea = -1;
			if (context.getRequest().getParameter("idOperatoreAdded") != null
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("")
					&& !context.getRequest().getParameter("idOperatoreAdded").equals("-1")) 
			{
				idnewlinea = Integer.parseInt(context.getRequest().getParameter("idOperatoreAdded"));
				newoperatore.queryRecordOperatorebyIdLineaProduttiva(db, idnewlinea);
			}

			context.getSession().setAttribute("newoperatore", newoperatore);
			context.getSession().setAttribute("idLp", context.getRequest().getParameter("idOperatoreAdded"));

			HashMap<Integer, Boolean> map = searchListInfo.getSelectedItems();
			
			Iterator<Parameter> listCheck = ParameterUtils.list(context.getRequest(), "checkCane", true).iterator();
			
			GestoreConnessioni.freeConnection(db);
			
			while(listCheck.hasNext()) 
			{
				Parameter parameter = listCheck.next();
				boolean valueCane = new Boolean(parameter.getValore()).booleanValue();
				if (valueCane)
				{
					newConnection = GestoreConnessioni.getConnection();
					Integer idAnimale 		 =	 Integer.valueOf(parameter.getId());
					String microchip 		 = context.getRequest().getParameter(idAnimale + "_microchip");
					Integer idSpecie 		 = Integer.parseInt(context.getRequest().getParameter(idAnimale + "_idSpecie"));
					Integer idAslRiferimento = Integer.parseInt(context.getRequest().getParameter(idAnimale + "_idAslRiferimento"));
					Integer idStato          = Integer.parseInt(context.getRequest().getParameter(idAnimale + "_idStato"));
					Integer idProprietario   = Integer.parseInt(context.getRequest().getParameter(idAnimale + "_idProprietario"));
					
					//Animale thisAnimale = new Animale(newConnection, idAnimale);
					Animale thisAnimale = new Animale();
					thisAnimale.setIdAnimale(idAnimale);
					thisAnimale.setIdSpecie(idSpecie);
					thisAnimale.setMicrochip(microchip);
					thisAnimale.setIdAslRiferimento(idAslRiferimento);
					thisAnimale.setStato(idStato);
					thisAnimale.setIdProprietario(idProprietario);
					thisAnimale.setDetentore(operatore);

					EventoTrasferimentoCanile eventoDaInserire = new EventoTrasferimentoCanile();
					eventoDaInserire.setEnteredby(getUserId(context));
					eventoDaInserire.setModifiedby(getUserId(context));
					eventoDaInserire.setIdProprietarioCorrente(lineaPId);
					eventoDaInserire.setIdDetentoreCorrente(lineaPId);
					eventoDaInserire.setOrigineInserimento(1);
					eventoDaInserire.setIdAnimale(idAnimale);
					eventoDaInserire.setMicrochip(thisAnimale.getMicrochip());
					eventoDaInserire.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					if(getUserAsl(context)==-1)
						eventoDaInserire.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					else
						eventoDaInserire.setIdAslRiferimento(getUserAsl(context));
					eventoDaInserire.setIdStatoOriginale(thisAnimale.getStato());

					eventoDaInserire.setDataTrasferimentoCanile(context.getRequest().getParameter("dataTrasferimento"));
					eventoDaInserire.setIdCanileOld(lineaPId);
					eventoDaInserire.setIdDetentore(idnewlinea);
					eventoDaInserire.setIdDetentoreCorrente(lineaPId);
					eventoDaInserire.setIdProprietario(thisAnimale.getIdProprietario());
					eventoDaInserire.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					eventoDaInserire.setIdTipologiaEvento(EventoTrasferimentoCanile.idTipologiaDB);
					eventoDaInserire.setNoteInternalUseOnly("Registrazione effettuata tramite funzione di 'Trasferimento Animali Massivo'.");
					
					eventoDaInserire.salvaRegistrazioneSenzaControlloCanilePieno(getUserId(context), getUserRole(context), getUserAsl(context), thisAnimale, newConnection);
					
					GestoreConnessioni.freeConnection(newConnection);
				
				}
			}
			
			newConnection = GestoreConnessioni.getConnection();
			
			animaleList = new AnimaleList();
			animaleList.setStatiDaIncludere(statiDaIncludere);
			animaleList.setFlagDecesso(false);
			animaleList.setFlagSmarrimento(false);
			animaleList.setFlagFurto(false);
			animaleList.setId_detentore(lineaPId);
			animaleList.setBuildProprietario(false);
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
			animaleList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
			animaleList.buildList(newConnection);
			
			searchListInfo.setSearchCriteria(animaleList, context);
			context.getRequest().setAttribute("animaleList", animaleList);
			
			LookupList lookupSpecie = new LookupList(newConnection, "lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
			
			context.getRequest().setAttribute("idLinea", lineaPId);
			
			GestoreConnessioni.freeConnection(newConnection);
			
			return getReturn(context, "TrasferimentoAnimaliMassivo");


		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			if (newConnection != null)
				GestoreConnessioni.freeConnection(newConnection);
			if (db != null)
				this.freeConnection(context, db);
		}
	}

	
	public String executeCommandDetailsAnimale(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
		
		Connection db = null;
		int idSpecie = 1; //default cane
		String microchip = "";
		Animale thisAnimale = new Animale();
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		try {

			String tempanimaleId = context.getRequest().getParameter(
			"animaleId");
			if (tempanimaleId == null) {
				tempanimaleId = (String) context.getRequest().getAttribute(
				"animaleId");
			}

			String specieAnimaleId = context.getRequest().getParameter(
			"idSpecie");
			if (specieAnimaleId == null) {
				specieAnimaleId = (String) context.getRequest().getAttribute(
				"idSpecie");
			}
			
			if (!(specieAnimaleId == null) && !("").equals(specieAnimaleId)){
				idSpecie = Integer.parseInt(specieAnimaleId);
			}
			
			
			if (!((String) context.getRequest().getParameter(
					"idLinea") == null) && !("").equals((String) context.getRequest().getParameter(
							"idLinea"))){
				context.getRequest().setAttribute("idLinea", (String) context.getRequest().getParameter(
							"idLinea"));
			}
			
			
			
			microchip = (String) context.getRequest().getParameter(
					"microchip");

			Integer tempid = -1;
			
			int idPartita = -1;

			if (tempanimaleId != null || microchip != null) {
				
				if (tempanimaleId != null)
					tempid = Integer.parseInt(tempanimaleId);

			
				

				Thread t = Thread.currentThread();
				db = this.getConnection(context);
				//costruisco la lookup per recuperare la tipologia
				LookupList lookupSpecie = new LookupList(db, "lookup_specie");
				context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
				RegistrazioniWKF wkf = new RegistrazioniWKF();				
			//	wkf.setFlagIncludiHd(hasPermission(context, "anagrafe_canina_registrazioni_pregresse-add"));
				

				switch (idSpecie) {
				case Cane.idSpecie: {
					Cane thisCane = new Cane();
				if (tempid > 0)	{
					 thisCane = new Cane(db, tempid);
				}
				else{
					 thisCane = new Cane(db, microchip);
				}
					
					context.getRequest()
					.setAttribute("caneDettaglio", thisCane);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisCane);
					thisAnimale = thisCane;
					idPartita = thisCane.getIdPartitaCircuitoCommerciale();
					break;
				}
				case Gatto.idSpecie: {

					Gatto thisGatto = new Gatto(db, tempid);
					context.getRequest().setAttribute("gattoDettaglio",
							thisGatto);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisGatto);
					idPartita = thisGatto.getIdPartitaCircuitoCommerciale();
					thisAnimale = thisGatto;
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				case Furetto.idSpecie: {

					Furetto thisFuretto = new Furetto(db, tempid);
					context.getRequest().setAttribute("furettoDettaglio",
							thisFuretto);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisFuretto);
					idPartita = thisFuretto.getIdPartitaCircuitoCommerciale();
					thisAnimale = thisFuretto;
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				}
				
				wkf.buildWkfDati(
						context,
						thisAnimale,
						hasPermission(context,
								"anagrafe_canina_registrazioni_pregresse-add"),
						user,
						isUgualeAslAnimaleAslUtente(context, thisAnimale), db);
				wkf.checkPossibilitaRegistrazioni(db);
				context.getRequest().setAttribute("wkf", wkf);

			}
			
			if (idPartita > -1){
				PartitaCommerciale partita = new PartitaCommerciale(db, idPartita);
				context.getRequest().setAttribute("partita", partita);
			}
			


			// lookups

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("comuniList", comuniList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList siteList = new LookupList();
			siteList.setShowDisabledFlag(true);
			siteList.setTable("lookup_asl_rif");
			siteList.buildList(db);
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

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

			LookupList esitoControlloList = new LookupList(db,
			"lookup_esito_controlli");
			context.getRequest().setAttribute("esitoControlloList",
					esitoControlloList);

			LookupList esitoControlloLabList = new LookupList(db,
			"lookup_esito_controlli_laboratorio");
			context.getRequest().setAttribute("esitoControlloLabList",
					esitoControlloLabList);
			
			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			context.getRequest().setAttribute("specieList", specieList);

			// Lista pratiche contributi
			PraticaList listaP = new PraticaList();
			ArrayList<PraticaDWR> listaPratiche = listaP.getListPratiche(db);
			LookupList praticheContributi = new LookupList(listaPratiche, -1);
			context.getRequest().setAttribute("listaPratiche",
					praticheContributi);

			LookupList veterinari = new LookupList(db, "elenco_veterinari_chippatori_with_asl_select_grouping");
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);
			
			
			//Action di provenienza
			String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);
			
			context.getRequest().setAttribute("origine", (String)context.getRequest().getParameter("origine"));
			
			//Registrazione ultima di vaccinazione antirabbia
			thisAnimale = new Animale(db, tempid);
			String microchip_to_search = (thisAnimale.getMicrochip() != null && !("").equals(thisAnimale.getMicrochip())) ? thisAnimale.getMicrochip() : thisAnimale.getTatuaggio();
		//	EventoInserimentoVaccinazioni vaccinazioneRabbia =  EventoInserimentoVaccinazioni.getUltimaVaccinazioneDaTipo(db, microchip_to_search, EventoInserimentoVaccinazioni.antirabbica);
			EventoInserimentoVaccinazioni vaccinazioneRabbia =  EventoInserimentoVaccinazioni.getUltimaVaccinazioneDaTipo(db, thisAnimale.getIdAnimale(), EventoInserimentoVaccinazioni.antirabbica);
			
			context.getRequest().setAttribute("dati_antirabbica", vaccinazioneRabbia);
			
			//Dati passaporto
			EventoRilascioPassaporto rilascioPassaporto = new EventoRilascioPassaporto();
			rilascioPassaporto.GetPassaportoAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("rilascioPassaporto", rilascioPassaporto);
			
			//Registrazione cessione aperta
			EventoCessione cessione = new EventoCessione();
			cessione.GetCessioneApertaByIdAnimale(db, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("dati_cessione", cessione);
			
			
			context.getRequest().setAttribute("dati_antirabbica", vaccinazioneRabbia);
			
			EventoPrelievoLeishmania prelievoLeish = new EventoPrelievoLeishmania();
			prelievoLeish.getUltimoPrelievo(thisAnimale.getMicrochip(), db);
			
			context.getRequest().setAttribute("prelievoLeish", prelievoLeish);
			
			
			if(context.getRequest().getAttribute("SalvaeClona")!=null && "1".equalsIgnoreCase(""+context.getRequest().getAttribute("SalvaeClona")))
			{
				 veterinari = new LookupList(db, "elenco_veterinari_chippatori");
				veterinari.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("veterinariList", veterinari); 
				
				siteList = new LookupList(db, "lookup_asl_rif");
					siteList.addItem(-1, "-- SELEZIONA VOCE --");
					context.getRequest().setAttribute("AslList", siteList);
				
				context.getRequest().setAttribute("SalvaeClona", "OK");
				return getReturn(context, "Add");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		
		if ((String) context.getSession().getAttribute("loginNoPassword") != null && !("").equals((String) context.getSession().getAttribute("loginNoPassword")) ){
			
			return ("DetailsNoNAVOK");
		}

		return getReturn(context, "DetailsAnimale");
	}
}
