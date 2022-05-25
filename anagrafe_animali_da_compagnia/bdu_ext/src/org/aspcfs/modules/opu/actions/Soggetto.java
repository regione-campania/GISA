/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.SoggettoList;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;


public class Soggetto extends CFSModule {
	
	
	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
		
	}
	  
	
	public String executeCommandAdd(ActionContext context){
		
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
		 	LookupList TitoloList = new LookupList(db, "lookup_titolo_list");
			TitoloList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Titolo", TitoloList);
			
			
			SoggettoFisico newSoggetto = (SoggettoFisico) context.getFormBean();
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

		return getReturn(context, "Add");
	}
	
	

	

	
	public String executeCommandInsert(ActionContext context) 
	throws SQLException{
		
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		SoggettoFisico insertedSogg = null;
		//Integer orgId = null;
		
		SoggettoFisico newSoggetto = (SoggettoFisico) context.getFormBean();


	


		newSoggetto.setEnteredBy(getUserId(context));
		newSoggetto.setModifiedBy(getUserId(context));
		newSoggetto.setOwner(getUserId(context));
		
		

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newSoggetto.setIpEnteredBy(ip);
		newSoggetto.setIpModifiedBy(ip);
		
		
		//Costruisci indirizzo
		
		newSoggetto.buildAddress(context);
		try {
			db = this.getConnection(context);

		

			isValid = this.validateObject(context, db, newSoggetto);

			

			if (isValid) {
				recordInserted = newSoggetto.insert(db);

			}
			if (recordInserted) {
				}

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD",
					"Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod",
					"&orgId=" + newSoggetto.getIdSoggetto());

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				context.getRequest().setAttribute("SoggettoAdded", newSoggetto);
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else if (context.getRequest().getParameter("iterId") != null && !context.getRequest().getParameter("iterId").equals("-1")){
						//gestione iter
							return ("InsertIter");
			}
		}

		context.getRequest().setAttribute("sogId", new Integer(newSoggetto.getIdSoggetto()).toString());
		return (executeCommandDetails(context));
		
	//	return ("InsertOK");
		
	}
	
	
	
	public String executeCommandSearchForm(ActionContext context) 
	{
	
		
			if (!(hasPermission(context, "accounts-accounts-view"))) {
			return ("PermissionError");
			}
		
		// Bypass search form for portal users
		if (isPortalUser(context)) {
			//return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
		
		
			LookupList TipoSoggettoList = new LookupList(db, "lookup_tipo_soggetto_fisico");
			TipoSoggettoList.addItem(-1,  "-- TUTTI --");
			context.getRequest().setAttribute("TipoSoggettoList", TipoSoggettoList);
			
			
			LookupList StatoRuoloSoggetto = new LookupList(db, "lookup_stato_ruolo_soggetto");
			StatoRuoloSoggetto.addItem(-1,  "-- TUTTI --");
			context.getRequest().setAttribute("StatoRuoloSoggetto", StatoRuoloSoggetto);
			
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");

		
			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context,
					"SearchSoggettoListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
								.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) orgListInfo
											.getSearchOptionValue("searchcodeContactCountry"),
									(String) orgListInfo
											.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect",
						stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
								.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) orgListInfo
											.getSearchOptionValue("searchcodeAccountCountry"),
									(String) orgListInfo
											.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect",
						stateSelect);
			}
		//	buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return getReturn(context, "Search");


}
	
	
	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		SoggettoList soggettoList = new SoggettoList();
		
		String source = (String) context.getRequest().getParameter("source");
		
		String codiceFiscale = (String) context.getRequest().getParameter(
				"codiceFiscale");
		String nome = (String) context.getRequest().getParameter("searchNome");
		String cognome = (String) context.getRequest().getParameter("searchCognome");
		String tipoSogg = (String) context.getRequest().getParameter("idTipo");
		int tipoSoggetto = -1;
		if (tipoSogg != null && ! "".equals(tipoSogg) && !("-1".equals(tipoSogg))){
			tipoSoggetto = new Integer(tipoSogg).intValue();
		}
		String ragSocialeAziendaRiferimento = (String) context.getRequest().getParameter("searchRagioneSociale");
		String statoRuolo = (String) context.getRequest().getParameter("statoRuolo");
		

		String popup = context.getRequest().getParameter("popup");
		
		

		if (!"".equals(codiceFiscale) && codiceFiscale != null) {
			soggettoList.setCodFiscale(codiceFiscale);
		}
		
		if (!"".equals(nome) && nome != null) {
			soggettoList.setNome(nome);
		}
		
		if (!"".equals(cognome) && cognome != null) {
			soggettoList.setCognome(cognome);
		}
		
		if (ragSocialeAziendaRiferimento != null && !"".equals(ragSocialeAziendaRiferimento)){
			soggettoList.setRagioneSociale(ragSocialeAziendaRiferimento);
		}
		
		
		soggettoList.setTipoSoggettoFisico(tipoSoggetto);
		
		if(statoRuolo != null && !"".equals(statoRuolo)){
			soggettoList.setStatoRuolo(statoRuolo);
		}
		

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchSoggettoListInfo");
		searchListInfo.setLink("Soggetto.do?command=Search&popup="+popup);
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			// return if no criteria is selected
			if ((searchListInfo.getListView() == null || ""
					.equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --"
					);
			context.getRequest().setAttribute("SiteIdList", siteList);




			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo
					.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchSoggettoListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchSoggettoListInfo");
					searchListInfo.setLink("Soggetto.do?command=Search&popup="+popup);
				}
				// Build the organization list
				soggettoList.setPagedListInfo(searchListInfo);
				soggettoList.setMinerOnly(false);
				soggettoList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));

				soggettoList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(soggettoList, context);
				// fetching criterea for account source (my accounts or all
				// accounts)
				if ("my".equals(searchListInfo.getListView())) {
					//operatoreList.setOwnerId(this.getUserId(context));
				}

				// fetching criterea for account status (active, disabled or
				// any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				// organizationList.setCessato(enabled);
			//	operatoreList.setIncludeEnabled(enabled);
				// If the user is a portal user, fetching only the
				// the organization that he access to
				// (i.e., the organization for which he is an account contact
/*				if (isPortalUser(context)) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
					organizationList
							.setOrgId(getPortalUserPermittedOrgId(context));
				}*/
				
				soggettoList.buildList(db);
			
				context.getRequest().setAttribute("SoggettoList", soggettoList);
				context.getSession().setAttribute("previousSearchType",
						"accounts");
				
				return getReturn(context, "List");
			} else {
				if ("accounts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchSoggettoListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchSoggettoListInfo");
					searchListInfo.setLink("Soggetto.do?command=Search&popup="+popup);
				}
				

				
				context.getSession().setAttribute("previousSearchType",
						"contacts");
				return ("ContactListOK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	
	private void resetPagedListInfo(ActionContext context) {
		this.deletePagedListInfo(context, "ContactListInfo");
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}
	
	
	
	
	public String executeCommandScegliSoggetto(ActionContext context){
		
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		
		Connection db = null;
		try {
			db = getConnection(context);
			SoggettoFisico soggetto = new SoggettoFisico(db, new Integer((String)context.getRequest().getParameter("idSoggetto")).intValue());
			context.getRequest().setAttribute("SoggettoAdded", soggetto);
			
		}catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ClosePopupOK");
	}
	
	


	
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		SoggettoFisico newSoggetto = null;
		try {

			String tempsogId = context.getRequest().getParameter("sogId");
			if (tempsogId == null) {
				tempsogId = (String) context.getRequest().getAttribute("sogId");
			}
			Integer tempid = null;
			
			if (tempsogId != null) {
				tempid = Integer.parseInt(tempsogId);
			}

			db = this.getConnection(context);
			
			newSoggetto = new SoggettoFisico(db, tempid);
			context.getRequest().setAttribute("SoggettoDettagli", newSoggetto);
			
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
			
			
			LookupList TitoloList = new LookupList(db, "lookup_titolo_list");
			TitoloList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Titolo", TitoloList);
	
		}catch (Exception e)
		{
			
		}
		
		
		return getReturn(context, "Details");
	}
	
	
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		SoggettoFisico soggettoToModify = null;
		String sogId = context.getRequest().getParameter("sogId");

		Connection db = null;
		
		try{
			
			db = this.getConnection(context);
			if (sogId != null && ! "".equals(sogId)){
				context.getRequest().setAttribute("sogId", sogId);
				int tempid = Integer.parseInt(sogId);
				soggettoToModify = new SoggettoFisico(db, tempid);
			}else{
				soggettoToModify =(SoggettoFisico) context.getFormBean();
			}
			

			context.getRequest().setAttribute("Soggetto", soggettoToModify);
			
			LookupList TitoloList = new LookupList(db, "lookup_titolo_list");
			TitoloList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Titolo", TitoloList);
			
/*			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);*/
	
		}catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		return "ModifyOK";
	
	}
	
	
	public String executeCommandUpdate(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		int resultCount = -1;
		//Integer orgId = null;
		SoggettoFisico newSoggetto = (SoggettoFisico) context.getFormBean();

		
		newSoggetto.buildAddress(context);

		//newOperatore.setEnteredBy(getUserId(context));
		newSoggetto.setModifiedBy(getUserId(context));
	//	newOperatore.setOwner(getUserId(context));
		try {		
		
			db = this.getConnection(context);

		
		//	newOperatore.setTempSessionData(context); 
		
		//newOperatore.setRequestItems(context);

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = user.getUserRecord().getIp();
		//newOrg.setIp_entered(ip);
			//newOperatore.setIp_modified(ip);
		
			



			isValid = this.validateObject(context, db, newSoggetto);

			

			if (isValid) {
				resultCount = newSoggetto.update(db);

			}


			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD",
					"Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod",
					"&orgId=" + newSoggetto.getIdSoggetto());

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else if (context.getRequest().getParameter("iterId") != null && !context.getRequest().getParameter("iterId").equals("-1")){
						//gestione iter
							return ("InsertIter");
			}
		}

		return (executeCommandSearchForm(context));
	
	}
	
	
	
}
