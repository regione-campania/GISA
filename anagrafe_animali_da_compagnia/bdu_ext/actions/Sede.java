/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;


import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.SedeList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.SoggettoList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;


import com.darkhorseventures.framework.actions.ActionContext;


public class Sede extends CFSModule {
	
	
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
			
			  ComuniAnagrafica c = new ComuniAnagrafica();
			  ArrayList<ComuniAnagrafica> listaComuni =  c.buildList(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId(),ComuniAnagrafica.IN_REGIONE);
			  LookupList comuniList = new LookupList(listaComuni, -1);
			  
			 
			  comuniList.addItem(-1, "Seleziona Comune");
			  context.getRequest().setAttribute("ComuniList", comuniList);
			  
			
			  
			  LookupList listaProvince = new LookupList(db,"lookup_province");
			  listaProvince.addItem(-1,"Seleziona Provincia");
			  context.getRequest().setAttribute("ListaProvince", listaProvince);
			
			
			Indirizzo newIndirizzo = (Indirizzo) context.getFormBean();
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
		
		Indirizzo newIndirizzo = (Indirizzo) context.getFormBean();




		newIndirizzo.setEnteredBy(getUserId(context));
		newIndirizzo.setModifiedBy(getUserId(context));
		
		
		

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newIndirizzo.setIpEnteredBy(ip);
		newIndirizzo.setIpModifiedBy(ip);
		
		

	
		try {
			db = this.getConnection(context);

		

			isValid = this.validateObject(context, db, newIndirizzo);

			

			if (isValid) {
				recordInserted = newIndirizzo.insert(db);

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
					"&orgId=" + newIndirizzo.getIdIndirizzo());

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
				context.getRequest().setAttribute("SedeAdded", newIndirizzo);
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			}
		}

		context.getRequest().setAttribute("sedeId", new Integer(newIndirizzo.getIdIndirizzo()).toString());
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
		
			
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");

			 ComuniAnagrafica c = new ComuniAnagrafica();
			  ArrayList<ComuniAnagrafica> listaComuni =  c.buildList(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId(),ComuniAnagrafica.IN_REGIONE);
			  LookupList comuniList = new LookupList(listaComuni, -1);
			 
			  comuniList.addItem(-1, "Seleziona Comune");
			  context.getRequest().setAttribute("ComuniList", comuniList);
			  
			  
			  LookupList listaProvince = new LookupList(db,"lookup_province");
			  listaProvince.addItem(-1,"Seleziona Provincia");
			  context.getRequest().setAttribute("ListaProvince", listaProvince);
			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo SedeListInfo = this.getPagedListInfo(context,
					"SearchSedeListInfo");
			SedeListInfo.setCurrentLetter("");
			SedeListInfo.setCurrentOffset(0);
			if (SedeListInfo.getSearchOptionValue("searchcodeContactCountry") != null
					&& !"-1".equals(SedeListInfo
							.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						SedeListInfo
								.getSearchOptionValue("searchcodeContactCountry"));
				if (SedeListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) SedeListInfo
											.getSearchOptionValue("searchcodeContactCountry"),
									(String) SedeListInfo
											.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect",
						stateSelect);
			}
			context.getRequest().setAttribute ("tipologia", context.getRequest().getParameter("searchTipologia"));
			if (SedeListInfo.getSearchOptionValue("searchcodeAccountCountry") != null
					&& !"-1".equals(SedeListInfo
							.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						SedeListInfo
								.getSearchOptionValue("searchcodeAccountCountry"));
				if (SedeListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map
							.put(
									(String) SedeListInfo
											.getSearchOptionValue("searchcodeAccountCountry"),
									(String) SedeListInfo
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
		SedeList sedeList = new SedeList();
		
		String source = (String) context.getRequest().getParameter("source");

		String comune = (String) context.getRequest().getParameter(
				"searchComune");

		context.getRequest().setAttribute("tipologia", context.getRequest().getAttribute("tipologia"));
		
		
		//organizationList.setTipologia(1);

		if ( comune != null && !"".equals(comune)) {
			sedeList.setComune(comune);
		}

		
		String popup = context.getRequest().getParameter("popup");
		
		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchSedeListInfo");
		searchListInfo.setLink("Sede.do?command=Search&popup="+popup+"&tipologia="+context.getRequest().getParameter("tipologia"));
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		//this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

	

			// return if no criteria is selected
			if ((searchListInfo.getListView() == null || ""
					.equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}



			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo
					.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchSedeListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo.setLink("Sede.do?command=Search&popup="+popup+"&tipologia="+context.getRequest().getParameter("tipologia"));
				}
				// Build the organization list
				sedeList.setPagedListInfo(searchListInfo);
				sedeList.setMinerOnly(false);
				sedeList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));

				sedeList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(sedeList, context);
				// fetching criterea for account source (my accounts or all
				// accounts)
				if ("my".equals(searchListInfo.getListView())) {
					//operatoreList.setOwnerId(this.getUserId(context));
				}

				int enabled = searchListInfo.getFilterKey("listFilter2");

				
				sedeList.buildListOperatore(db);
			
				context.getRequest().setAttribute("sedeList", sedeList);
				
				
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni =  c.buildList_all(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());
				LookupList comuniList = new LookupList(listaComuni, -1);
				
				
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("ComuniList", comuniList);
				
				LookupList siteList = new LookupList(db, "lookup_site_id");
				context.getRequest().setAttribute("SiteIdList", siteList);
				
				
				context.getSession().setAttribute("previousSearchType",
						"accounts");
				
				return getReturn(context, "List");
			} else {
				if ("accounts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchSedeListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"searchListInfo");
					searchListInfo.setLink("Sede.do?command=Search&popup="+popup+"tipologia="+context.getRequest().getAttribute("tipologia"));
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
	


	public String executeCommandScegliSede(ActionContext context){
		
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		
		Connection db = null;
		try {
			db = getConnection(context);
			Indirizzo sede = new Indirizzo(db, new Integer((String)context.getRequest().getParameter("sedeId")).intValue());
			sede.setTipologiaSede((String)context.getRequest().getParameter("tipologia"));
			context.getRequest().setAttribute("SedeAdded", sede);
			
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
		Indirizzo newIndirizzo = null;
		try {

			String tempIndId = context.getRequest().getParameter("sedeId");
			if (tempIndId == null) {
				tempIndId = (String) context.getRequest().getAttribute("sedeId");
			}
			Integer tempid = null;
			
			if (tempIndId != null) {
				tempid = Integer.parseInt(tempIndId);
			}

			db = this.getConnection(context);
			
			newIndirizzo = new Indirizzo(db, tempid);
			context.getRequest().setAttribute("IndirizzoDettagli", newIndirizzo);
			
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
			
			
			LookupList TitoloList = new LookupList(db, "lookup_titolo_list");
			TitoloList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Titolo", TitoloList);
			
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			//Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni =  c.buildList_all(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			
			
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
	
		}catch (Exception e)
		{
			
		}
		
		
		return getReturn(context, "Details");
	}
	


}
