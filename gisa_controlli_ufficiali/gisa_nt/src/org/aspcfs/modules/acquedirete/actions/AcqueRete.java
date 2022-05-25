/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.AddressList;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.acquedirete.base.Organization;
import org.aspcfs.modules.acquedirete.base.OrganizationList;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.worksheet.WorksheetColumn;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;


public final class AcqueRete extends CFSModule {
	
	 Logger logg = Logger.getLogger("MainLogger");

 /**
  *  Default: not used
  *
  * @param  context  Description of Parameter
  * @return          Description of the Returned Value
  */
 public String executeCommandDefault(ActionContext context) {

	 		  return executeCommandDashboard(context);
 } 


 /**
  *  Search: Displays the Account search form
  *
  * @param  context  Description of Parameter
  * @return          Description of the Returned Value
  */

 
 public String executeCommandSearchForm(ActionContext context) {
	    if (!(hasPermission(context, "acquedirete-acquedirete-view"))) {
	      return ("PermissionError");
	    }

	    //Bypass search form for portal users
	    if (isPortalUser(context)) {
	      return (executeCommandSearch(context));
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, "--Tutti--");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      siteList.remove(8); //rimuovo fuori regione
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      Organization newOrg = new Organization();
	      newOrg.setComuni2(db,getUserSiteId(context));
	      //if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	        
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      UserBean user = (UserBean) context.getSession().getAttribute("User");
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
	       
	      LookupList comuniList = new LookupList(listaComuni);
	      
	       comuniList.addItem(-1, "");
	       context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(
	          context, "SearchOrgListInfo");
	      orgListInfo.setCurrentLetter("");
	      orgListInfo.setCurrentOffset(0);
	      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
	        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
	      }
	      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
	        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
	        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
	          HashMap map = new HashMap();
	          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
	          stateSelect.setPreviousStates(map);
	        }
	        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
	      }
	      //ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	       
	      if (newOrg==null)
	      {
	    	  newOrg = new Organization();
	      }
	    	  newOrg.setComuni2(db,getUserSiteId(context));
	      //if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);

	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchOK");
	  }
 
 
 public String executeCommandAdd(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.remove(8); //rimuovo fuori regione
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      
	      Organization newOrg = (Organization) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      if(newOrg == null)
	      {
	    	  newOrg = new Organization();
	      }
	      newOrg.setComuni2(db,getUserSiteId(context));
	      //if (newOrg.getEnteredBy() != -1) {
	        
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      //}
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    //if a different module reuses this action then do a explicit return
	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

	    return getReturn(context, "Add");
	  }
 
 public String executeCommandInsert(ActionContext context) throws SQLException {
	    if (!hasPermission(context, "acquedirete-acquedirete-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Organization insertedOrg = null;

	    
	    Organization newOrg = (Organization) context.getFormBean();
	   
	    String comune = (String) context.getRequest().getParameter("address1city");
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setName(context.getRequest().getParameter("name"));
	    newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
	    newOrg.setEnte_gestore(context.getParameter("ente_gestore"));
	    //Calcolo l'asl in base al comune
	   
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newOrg.setIp_entered(ip);
	    newOrg.setIp_modified(ip);
	    
		
	     try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      
	      int tipoStruttura = Integer.parseInt(context.getRequest().getParameter("tipo_struttura"));
	      
	      String selectAcqua = "select identificativo from lookup_tipo_acque where code = "+tipoStruttura;
	      PreparedStatement pst2 = db.prepareStatement(selectAcqua);
	      String idAcqua = "";
	      	 ResultSet rs2 = pst2.executeQuery();
	      	 while (rs2.next())
	      	 {
	      		idAcqua = rs2.getString("identificativo");
	      	 }
	      newOrg.setAccountNumber(newOrg.generaNumeroRegistrazione(db, comune, idAcqua));
		   
	      newOrg.setComuni2(db,getUserSiteId(context));
	      newOrg.setTipo_struttura(tipoStruttura);
	      newOrg.setRequestItems(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      
	      newOrg.setEntered(new Timestamp (new Date(System.currentTimeMillis()).getTime()));

	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	     
	      newOrg.setEnteredBy(user.getUserId());
	      newOrg.setModifiedBy(user.getUserId());

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Organization(db, newOrg.getOrgId());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        
	       
	        
	         
	      }
	    } catch (Exception errorMessage) {
	      String forward = "";
	      logg.severe("Errore di forwarding nella MiddleServlet: " + forward);
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Pippo Insert ok");
	    if (recordInserted) {
	      String target = context.getRequest().getParameter("target");
	      if (context.getRequest().getParameter("popup") != null) {
	        return ("ClosePopupOK");
	      }
	      if (target != null && "add_contact".equals(target)) {
	        return ("InsertAndAddContactOK");
	      } else {
	        //return ("InsertOK");
	    	  return "GoToDetailsOk";
	      }
	    }
	    return (executeCommandAdd(context));
	  }





 
 
 public String executeCommandDetails(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("orgId");
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("orgId");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	      
	      newOrg = new Organization(db, tempid);
	      //check whether or not the owner is an active User
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);   
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);   
	      
	      String selectAcqua = "select identificativo from lookup_tipo_acque where code = "+newOrg.getTipo_struttura();
	      PreparedStatement pst2 = db.prepareStatement(selectAcqua);
	      String idAcqua = "";
	      	 ResultSet rs2 = pst2.executeQuery();
	      	 while (rs2.next())
	      	 {
	      		idAcqua = rs2.getString("identificativo");
	      	 }
	    	 context.getRequest().setAttribute("identificativoAcqua", idAcqua);
	    
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	        return ("DetailsOK");
	    }
	  }
 

 
 public String executeCommandDashboard(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-view")) {
	      if (!hasPermission(context, "acquedirete-acquedirete-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	    if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

	    //this is how we get the multiple-level heirarchy...recursive function.
	    User thisRec = thisUser.getUserRecord();

	    UserList shortChildList = thisRec.getShortChildList();
	    UserList newUserList = thisRec.getFullChildList(
	        shortChildList, new UserList());

	    newUserList.setMyId(getUserId(context));
	    newUserList.setMyValue(
	        thisUser.getUserRecord().getContact().getNameLastFirst());
	    newUserList.setIncludeMe(true);

	    newUserList.setJsEvent(
	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
	        "userId", getUserId(context));
	    userListSelect.addAttribute("id", "userId");
	    context.getRequest().setAttribute("Return", "Accounts");
	    context.getRequest().setAttribute("NewUserList", userListSelect);
	    return ("DashboardOK");
	  }
 

 
 
 public String executeCommandSearch(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-view")) {
	      return ("PermissionError");
	    }

	    String source = (String) context.getRequest().getParameter("source");
	    OrganizationList organizationList = new OrganizationList();
	    
	   
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfo");
	    searchListInfo.setLink("AcqueRete.do?command=Search");
	    searchListInfo.setListView("All");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);	      
	      
	      String addressType = context.getRequest().getParameter("addess_type_op");
	      if(addressType!=null && !addressType.equals("")){
	    	    organizationList.setAddressType(addressType);
	    	  }
	      
	   	   
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	         
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      
	      Organization newOrg = new Organization();
	      newOrg.setComuni2(db,getUserSiteId(context));
	            
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	      if (isPortalUser(context)) {
	        organizationList.setOrgSiteId(this.getUserSiteId(context));
	        source = "searchForm";
	      }
	      //return if no criteria is selected
	      if ((searchListInfo.getListView() == null || "".equals(
	          searchListInfo.getListView())) && !"searchForm".equals(source)) {
	        return "ListOK";
	      }
	      
	      //Display list of accounts if user chooses not to list contacts
	      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
	        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
	          this.deletePagedListInfo(context, "SearchOrgListInfo");
	          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
	          searchListInfo.setLink("acquedirete.do?command=Search");
	        }
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        organizationList.setMinerOnly(false);
	        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
	        
	     
	        if((context.getRequest().getParameter("searchcodeOrgSiteId")==null)){
	       		organizationList.setSiteId("-1");   	
	       	}else{
	        organizationList.setSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
	       	}
	        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
	        
	        searchListInfo.setSearchCriteria(organizationList, context);
	        //fetching criterea for account source (my accounts or all accounts)
	        if ("my".equals(searchListInfo.getListView())) {
	          organizationList.setOwnerId(this.getUserId(context));
	        }
	        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	        } else if (organizationList.getOrgSiteId() == -1) {
	          organizationList.setIncludeOrganizationWithoutSite(true);
	        }
	        //fetching criterea for account status (active, disabled or any)
	        int enabled = searchListInfo.getFilterKey("listFilter2");
	        organizationList.setIncludeEnabled(enabled);
	        //If the user is a portal user, fetching only the
	        //the organization that he access to
	        //(i.e., the organization for which he is an account contact
	        if (isPortalUser(context)) {
	          organizationList.setOrgSiteId(this.getUserSiteId(context));
	          organizationList.setIncludeOrganizationWithoutSite(false);
	          
	        }
	        
	        organizationList.buildList(db);
	        
	        
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        return "ListOK";
		      
	      } 
	    } catch (Exception e) {
	        //Go through the SystemError process
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	    return "";
	      
	      
	   
 }
  
 public String executeCommandList(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-view")) {
	      return ("PermissionError");
	    }

	    String source = (String) context.getRequest().getParameter("source");
	    OrganizationList organizationList = new OrganizationList();
	    
	   
	    addModuleBean(context, "View Accounts", "Search Results");

	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);	      
	      
	       
	   	   
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	         
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      
	      Organization newOrg = new Organization(db,Integer.parseInt(context.getParameter("orgId")));
	      newOrg.setComuni2(db,getUserSiteId(context));
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      
	      Address add = newOrg.getAddressList().getAddress("5");
	      String pdpSel = context.getParameter("sel");
	     
	     // organizationList.setAccountCity(add.getCity());
	      organizationList.setOrgSiteId(newOrg.getSiteId());
	      
	      //Filtro sul comune
	      String idComune = "";
	      if (context.getRequest().getParameter("comune")!=null && !context.getRequest().getParameter("comune").equals("null"))
	    	  idComune = context.getRequest().getParameter("comune");
	      if (!idComune.equals(""))
	    	  organizationList.setAccountCity(idComune);  
	      
	      organizationList.buildList(db,newOrg.getOrgId(),pdpSel);
	        
	        
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        return "ListPdPOK";
		      
	    } catch (Exception e) {
	        //Go through the SystemError process
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      
	      
	   
}
 
 public String executeCommandModify(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-edit")) {
	      return ("PermissionError");
	    }
	    String orgid = context.getRequest().getParameter("orgId");
	    context.getRequest().setAttribute("orgId", orgid);
	    int tempid = Integer.parseInt(orgid);
	    Connection db = null;
	    Organization newOrg = null;
	    try {
	      db = this.getConnection(context);
	    
	        newOrg = new Organization(db, tempid);
	        
	      //In fase di modifica
	        Iterator it_coords = newOrg.getAddressList().iterator();
	        while(it_coords.hasNext()){
	        	  
	        	  org.aspcfs.modules.acquedirete.base.OrganizationAddress thisAddress = (org.aspcfs.modules.acquedirete.base.OrganizationAddress) it_coords.next();
	        	 
	        }
	      
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      newOrg.setComuni2(db,getUserSiteId(context));
		   
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.remove(8); //rimuovo fuori regione
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
	      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAcque", tipoAcque);
	      
	      CountrySelect countrySelect = new CountrySelect(systemStatus);
	      context.getRequest().setAttribute("CountrySelect", countrySelect);
	      context.getRequest().setAttribute("systemStatus", systemStatus);

	      /*UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
	      context.getRequest().setAttribute("UserList", userList);*/
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("OrgDetails", newOrg);
	    if (context.getRequest().getParameter("popup") != null) {
	      return ("PopupModifyOK");
	    } else {
	      return ("ModifyOK");
	    }
	  }
 


public String executeCommandRestore(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordUpdated = false;
	    Organization thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String orgId = context.getRequest().getParameter("orgId");
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
	        return ("PermissionError");
	      }
	      thisOrganization = new Organization(db, Integer.parseInt(orgId));
	      // NOTE: these may have different options later
	      
	      this.invalidateUserData(context, this.getUserId(context));
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	    if (recordUpdated) {
	      context.getRequest().setAttribute(
	          "refreshUrl", "AcqueRete.do?command=Search");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearch(context);
	      }
	      return this.executeCommandDetails(context);
	    } else {
	      return (executeCommandSearch(context));
	    }
	  }



public String executeCommandConfirmDelete(ActionContext context) {
	    if (!hasPermission(context, "acquedirete-acquedirete-delete")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Organization thisOrg = null;
	    HtmlDialog htmlDialog = new HtmlDialog();
	    String id = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    if (context.getRequest().getParameter("id") != null) {
	      id = context.getRequest().getParameter("id");
	    }
	    try {
	      db = this.getConnection(context);
	      thisOrg = new Organization(db, Integer.parseInt(id));
	      //check permission to record
	      
	      
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.delete"), "javascript:window.location.href='AcqueRete.do?command=Trash&action=delete&orgId=" + thisOrg.getOrgId() + "&forceDelete=true" + "'");
	      
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getSession().setAttribute("Dialog", htmlDialog);
	    return ("ConfirmDeleteOK");
	  }

public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "acquedirete-acquedirete-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    String orgId = context.getRequest().getParameter("orgId");
    Organization newOrg = (Organization) context.getFormBean();
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    newOrg.setModifiedBy(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setOrgId(Integer.parseInt(orgId));
    newOrg.setTipo_struttura((Integer.parseInt(context.getRequest().getParameter("tipo_struttura"))));
    newOrg.setName(context.getRequest().getParameter("name"));
    newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
    newOrg.setEnte_gestore(context.getParameter("ente_gestore"));
    
    
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList tipoAcque = new LookupList(db, "lookup_tipo_acque");
      tipoAcque.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoAcque", tipoAcque);
      
      oldOrg = new Organization(db, newOrg.getOrgId());
      
      newOrg.setRequestItems(context);
      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
      newOrg.setModifiedBy(user.getUserId());
      
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        resultCount = newOrg.update(db,context);
      //  String prova = context.getRequest().getParameter("address1state");
       // newOrg.setState(prova);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        
      
       
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    if (resultCount == -1 || !isValid) {
      return (executeCommandModify(context));
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandSearch(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("dashboard")) {
        return (executeCommandDashboard(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("Calendar")) {
        if (context.getRequest().getParameter("popup") != null) {
          return ("PopupCloseOK");
        }
      } else {
        return ("UpdateOK");
      }
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
    return ("UpdateOK");
  }


public String executeCommandTrash(ActionContext context)
		throws SQLException {
	if (!hasPermission(context, "acquedirete-acquedirete-delete")) {
		return ("PermissionError");
	}
	boolean recordUpdated = false;
	Organization thisOrganization = null;
	Organization oldOrganization = null;
	Connection db = null;
	try {
		db = this.getConnection(context);
		String orgId = context.getRequest().getParameter("orgId");
		//check permission to record
		if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
			return ("PermissionError");
		}
		thisOrganization = new Organization(db, Integer.parseInt(orgId));
		oldOrganization = new Organization(db, Integer.parseInt(orgId));
		// NOTE: these may have different options later
		recordUpdated = thisOrganization.updateStatus(db, context, true,
				this.getUserId(context));
		if (recordUpdated) {
			

		} 
	} catch (Exception e) {

		context.getRequest().setAttribute("refreshUrl",
				"Accounts.do?command=Search");
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	addModuleBean(context, "Accounts", "Delete Account");
	if (recordUpdated) {
		context.getRequest().setAttribute("refreshUrl",
				"AcqueRete.do?command=Search");
		if ("list".equals(context.getRequest().getParameter("return"))) {
			return executeCommandSearch(context);
		}
		return "DeleteOK";
	} else {
		return (executeCommandSearch(context));
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

 
 
 
