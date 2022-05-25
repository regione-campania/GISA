/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stampa_verbale_prelievo.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import org.aspcfs.controller.SystemStatus;
//import org.aspcfs.modules.accounts.base.Organization;
//import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.stampa_verbale_prelievo.base.OrganizationList;
import org.aspcfs.modules.stampa_verbale_prelievo.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;


import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.returnsclause;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Cell;

public final class OsaSearch extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}
	
	public String executeCommandDashboard(ActionContext context) {
		
			 if (!hasPermission(context, "stampa-verbali-prelievo-view")) {
		        return ("PermissionError");
		     }
		      
			 //Bypass dashboard and search form for portal users
		     if (isPortalUser(context)) {
		        return (executeCommandSearch(context));
		     }
		  
		    return (executeCommandSearchForm(context));
		  
	}
	
	public String executeCommandSearchForm(ActionContext context) {
	    if (!(hasPermission(context, "stampa-verbali-prelievo-view"))) {
	      return ("PermissionError");
	    }
	    
	    //Bypass search form for portal users
	    if (isPortalUser(context)) {	
	      return (executeCommandSearch(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "-- SELEZIONA VOCE --");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      UserBean user = (UserBean) context.getSession().getAttribute("User");
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
	       
	      LookupList comuniList = new LookupList(listaComuni);
	      comuniList.addItem(-1, "");
	      context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      LookupList operatoriList = new LookupList(db, "lookup_tipologia_operatore");
	      operatoriList.addItem(-1, "");
	      operatoriList.removeElementByLevel(12);
	      operatoriList.remove(2); //Acque di rete rimosse
	      context.getRequest().setAttribute("OperatoriList", operatoriList);    
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchOK");
	    
	  }

	
	public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "stampa-verbali-prelievo-view")) {
      return ("PermissionError");
    }

	UserBean user = (UserBean) context.getSession().getAttribute("User");
	OrganizationList organizationList = new OrganizationList();
	
	String source = (String) context.getRequest().getParameter("source");
    String asl = (String) context.getRequest().getParameter("searchcodeOrgSiteId");
    String comune = (String) context.getRequest().getParameter("searchAccountCity");
    
    if (comune.replaceAll(" ", "").equals(""))
    	comune= "";
    
    String provincia = (String) context.getRequest().getParameter("searchAccountOtherState");
    String name = (String) context.getRequest().getParameter("searchAccountName");
    String codiceFiscale = (String) context.getRequest().getParameter("searchAccountCodiceFiscale"); 
    String operatore = (String) context.getRequest().getParameter("searchcodeTipologia_operatore");

    String tipo = context.getRequest().getParameter("tipoAction");
    //Se si decide di generare la stampa dei moduli
    if(tipo != null && tipo.equals("modulo")){
		 
    	String selectedValue = (String) context.getRequest().getParameter("selectMod");
    	context.getSession().setAttribute("tipoModulo", selectedValue);
    	org.aspcf.modules.report.util.PrintModulesHTML print = new org.aspcf.modules.report.util.PrintModulesHTML();
    	return print.executeCommandGenerateBarcode(context);
    }
    //Altrimenti...si richiama la ricerca Operatore
    else {
    	
    	 if( asl != null )
    	    	organizationList.setOrgSiteId(asl);
    	    
    	   if (comune != null){
    		   organizationList.setAccountCity(comune);
    		   organizationList.setAccountAddrline1(comune);
    	   }
    		    
    	  
    	   if(provincia != null && !provincia.equals("")){
    		   switch ( Integer.parseInt(provincia)) {
    		   	case 1: organizationList.setAccountState("AV"); break;
    		   	case 2: organizationList.setAccountState("BN"); break;
    		   	case 3: organizationList.setAccountState("CE"); break;
    		   	case 4: organizationList.setAccountState("NA"); break;
    		   	case 5: organizationList.setAccountState("SA"); break;
    		   	default: logger.info("Switch tipologia provincia"); break;
    		 } 
    	   }
    	   
    	   if (operatore != null){
    		   organizationList.setTipologia_operatore(operatore);
    	   }
    	   
    	   if(name != null)
    		   organizationList.setAccountName(name);
    	   
    	   if(codiceFiscale != null)
    		   organizationList.setCodiceFiscale(codiceFiscale);
    	  
    	    addModuleBean(context, "View Accounts", "Search Results");

    	    //Prepare pagedListInfo
    	    PagedListInfo searchListInfo = this.getPagedListInfo(
    	        context, "SearchOrgListInfo");
    	    searchListInfo.setLink("OsaSearch.do?command=Search");
    	    searchListInfo.setListView("all");
    	    SystemStatus systemStatus = this.getSystemStatus(context);
    	    //Need to reset any sub PagedListInfos since this is a new account
    	    this.resetPagedListInfo(context);
    	    Connection db = null;
    	    try {
    	    	
    	      db = this.getConnection(context); 
    	      
    	      if ((searchListInfo.getListView() == null || "".equals(
    	              searchListInfo.getListView())) && !"searchForm".equals(source)) {
    	            return "ListOK";
    	          }
    	      LookupList siteList = new LookupList(db, "lookup_site_id");
    	      siteList.addItem(-1, "-- SELEZIONA VOCE --");
    	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
    	      context.getRequest().setAttribute("SiteIdList", siteList);
    	      
    	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
    	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
    	      LookupList comuniList = new LookupList(listaComuni);
    	      comuniList.addItem(-1, "");
    	      context.getRequest().setAttribute("ComuniList", comuniList);
    	    
    	      LookupList operatoriList = new LookupList(db, "lookup_tipologia_operatore");
    	      operatoriList.addItem(-1, "");
    	      context.getRequest().setAttribute("OperatoriList", operatoriList); 
    	      
    	      //Display list of accounts if user chooses not to list contacts
    	      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
    	        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
    	          this.deletePagedListInfo(context, "SearchOrgListInfo");
    	          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
    	          searchListInfo.setLink("OsaSearch.do?command=Search");
    	        }
    	        organizationList.setPagedListInfo(searchListInfo);
    	        organizationList.setMinerOnly(false);
    	        searchListInfo.setSearchCriteria(organizationList, context);
    	        searchListInfo.setColumnToSortBy("name");
    	        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
    	          organizationList.setOrgSiteId(this.getUserSiteId(context));
    	        } 
    	        //fetching criterea for account status (active, disabled or any)
    	        int enabled = searchListInfo.getFilterKey("listFilter2");
    	        //organizationList.setCessato(enabled);
    	        organizationList.setIncludeEnabled(enabled);
    	        //If the user is a portal user, fetching only the
    	        //the organization that he access to
    	        //(i.e., the organization for which he is an account contact
    	        if (isPortalUser(context)) {
    	          organizationList.setOrgSiteId(this.getUserSiteId(context));
    	          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
    	        }
    	        organizationList.setEscludiAcque(true);
    	        organizationList.buildList(db);
    	        context.getSession().setAttribute("OrgList", organizationList);
    	        context.getSession().setAttribute("previousSearchType", "accounts");
    	      } 
    	    } catch (Exception e) {
    	      //Go through the SystemError process
    	      context.getRequest().setAttribute("Error", e);
    	      return ("SystemError");
    	    } finally {
    	      this.freeConnection(context, db);
    	    }
    	    
    	   return "ListOK";
    	   
    }
   
  }


	private void resetPagedListInfo(ActionContext context) {
		
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

}