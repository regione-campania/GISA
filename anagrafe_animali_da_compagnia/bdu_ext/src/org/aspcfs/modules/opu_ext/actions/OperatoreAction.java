/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu_ext.base.ComuniAnagrafica;
import org.aspcfs.modules.opu_ext.base.LineaProduttiva;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.modules.opu_ext.base.OperatoreList;
import org.aspcfs.modules.opu_ext.base.Stabilimento;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;

public class OperatoreAction extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
	}




	public String executeCommandSearchForm(ActionContext context) {
	
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);
			
			org.aspcfs.modules.opu_ext.base.ComuniAnagrafica c = new org.aspcfs.modules.opu_ext.base.ComuniAnagrafica();
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			ArrayList<org.aspcfs.modules.opu_ext.base.ComuniAnagrafica> listaComuni = c .buildList(db, user.getSiteId(),
			ComuniAnagrafica.IN_REGIONE);

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);

			PagedListInfo orgListInfo = this.getPagedListInfo(context,"SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			
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
		

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OperatoreList operatoreList = new OperatoreList();

		String source = (String) context.getRequest().getParameter("source");

		String partitaIva = (String) context.getRequest().getParameter(
				"PartitaIva");
		String codiceF = (String) context.getRequest().getParameter(
				"CodiceFiscale");
		String ragione_sociale = (String) context.getRequest().getParameter(
				"RagioneSociale");
		context.getRequest().setAttribute("TipologiaSoggetto",
				context.getRequest().getParameter("tipologiaSoggetto"));

		// organizationList.setTipologia(1);

		if (!"".equals(partitaIva) && partitaIva != null) {
			operatoreList.setPartIva(partitaIva);
		}
		if (!"".equals(codiceF) && codiceF != null) {
			operatoreList.setCodiceFiscale(codiceF);
		}

		if (ragione_sociale != null && !"".equals(ragione_sociale)) {
			operatoreList.setRagioneSociale(ragione_sociale);
		}

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo
				.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
						+ context.getRequest()
								.getParameter("tipologiaSoggetto") + RequestUtils.addLinkParams(
						                  context.getRequest(), "popup|popupType|actionId"));
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

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
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo
							.setLink("OperatoreAction.do?command=Search&tipologiaSoggetto="
									+ context.getRequest().getParameter(
											"tipologiaSoggetto") +  RequestUtils.addLinkParams(
									                  context.getRequest(), "popup|popupType|actionId"));
				}
				// Build the organization list
				operatoreList.setPagedListInfo(searchListInfo);
				operatoreList.setMinerOnly(false);
				operatoreList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));

				operatoreList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(operatoreList, context);

				
				int enabled = searchListInfo.getFilterKey("listFilter2");
				

				operatoreList.buildList(db);

				context.getRequest().setAttribute("OrgList", operatoreList);
				
				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute("tipoRegistrazione",
							context.getRequest().getParameter("tipoRegistrazione"));
				} else {
					context.getRequest().setAttribute("tipoRegistrazione", "-1");
				}

				LookupList siteList = new LookupList(db, "lookup_site_id");
				context.getRequest().setAttribute("SiteIdList", siteList);
				
				LookupList tipologiaList = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
				//registrazioniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("tipologiaList",
						tipologiaList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
						((UserBean) context.getSession().getAttribute("User"))
								.getSiteId());
				/*
				 * LookupList comuniList = new LookupList(listaComuni, -1);
				 * 
				 * 
				 * comuniList.addItem(-1, "");
				 * context.getRequest().setAttribute("ComuniList", comuniList);
				 */

				context.getSession().setAttribute("previousSearchType",
						"accounts");

				if (context.getRequest().getParameter("tipoRegistrazione") != null) {
					context.getRequest().setAttribute(
							"tipoRegistrazione",
							context.getRequest().getAttribute(
									"tipoRegistrazione"));
				} else {
					context.getRequest()
							.setAttribute("tipoRegistrazione", "-1");
				}

				return getReturn(context, "List");
			} else {
				if ("accounts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo.setLink("OperatoreAction.do?command=Search");
				}

				context.getSession().setAttribute("previousSearchType",
						"contacts");

				return getReturn(context, "ContactList");
				// return ("ContactListOK");
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

	public String executeCommandDetailsImportatore(ActionContext context) {
		
		Connection db = null;
		Operatore newOperatore = null;
		try {
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			String tempOpId = "" ;
			if (user.getSiteId() == -1 )
			{
			tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = (String) context.getRequest().getAttribute("opId");
			}

			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}
			else
			{
				
			}
			}
			
			

			db = this.getConnection(context);

			newOperatore = new Operatore();
			//newOperatore.queryRecordOperatorebyIdLineaProduttiva(db, tempid);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			return getReturn(context, "DetailsOperatore");
			// return "DetailsOperatoreOK";

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	
	
	

}
