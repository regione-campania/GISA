/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.abusivismi.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.abusivismi.base.*;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;


import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcf.modules.controlliufficiali.action.Diffida;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

    return executeCommandDashboard(context);
  }

  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "Abusivismi.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
	      TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	      	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }
	      newOrg = new Organization(db, passedId);
	      ticList.setOrgId(passedId);
	      if (newOrg.isTrashed()) {
	        ticList.setIncludeOnlyTrashed(true);
	      }
	      
	      UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
	      ticList.buildList(db);
	      context.getRequest().setAttribute("TicList", ticList);
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      addModuleBean(context, "View Accounts", "Accounts View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "ViewVigilanza");
	  }
  
  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "abusivismi-abusivismi-view"))) {
      return ("PermissionError");
    }
    
   /* if (!(hasPermission(context, "request-view"))) {
        return ("PermissionError");
      }
*/
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      
      
      
/*    LookupList llist=new LookupList(db,"lookup_stabilimenti_types");
      llist.addItem(-1, "-nessuno-");
      context.getRequest().setAttribute("llist", llist);
*/
      
      LookupList comuniList = new LookupList(db, "lookup_comuni");
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList); 
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
     
      
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
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Search Accounts", "Accounts Search");
    return ("SearchOK");
  }


  /**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    String popup = "false";
    try {
    	
    Comuni c = new Comuni();
    ArrayList<Comuni> listaComuni = new ArrayList<Comuni>();
    
   	
    	
      db = this.getConnection(context);
      
      UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
    
     popup = context.getRequest().getParameter("popup");
      
      
      
  	 listaComuni = c.buildList(db, user.getSiteId());
  	 context.getRequest().setAttribute("Comuni", listaComuni);
  
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      

      Organization newOrg = (Organization) context.getFormBean();
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
     
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Add Account", "Accounts Add");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    
    context.getRequest().setAttribute("popup", popup);
    
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
        return getReturn(context, "AddAccount");
    }

    return getReturn(context, "Add");
  }


  /**
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-view")) {
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
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
        return ("PermissionError");
      }
      newOrg = new Organization(db, tempid);
      
    //Caricamento Diffide
      	Ticket t = new Ticket();
		context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
		context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
		
      
      //Dopo l'inserimento riconverti
      Iterator it_coords = newOrg.getAddressList().iterator();
      while(it_coords.hasNext()){
    	  
    	  org.aspcfs.modules.abusivismi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.abusivismi.base.OrganizationAddress) it_coords.next();
    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
    		  
    		  String spatial_coords [] = null;
        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//        	  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//      		  {
//      			 AjaxCalls ajaxCall = new AjaxCalls();
//      			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//      			thisAddress.setLatitude(coordinate[1]);
//      		  thisAddress.setLongitude(coordinate[0]);
//      		  }
//      		  else
//      		  {
      			  thisAddress.setLatitude(spatial_coords[0]);
        		  thisAddress.setLongitude(spatial_coords[1]);
//      		  }
    	  }
    	  
    	  //context.getSession().setAttribute("lat", Double.toString(thisAddress.getLatitude()));
          //context.getSession().setAttribute("lon", Double.toString(thisAddress.getLongitude()));
          
    	  
      }
      
      
      //check whether or not the owner is an active User
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      
      
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
      return getReturn(context, "Details");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "abusivismi-dashboard-view")) {
      if (!hasPermission(context, "abusivismi-abusivismi-view")) {
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
          "Accounts", "org.aspcfs.modules.abusivismi.base.AccountsListScheduledActions", "Accounts");
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
  
  public String executeCommandDashboardScelta(ActionContext context) {
	    if (!hasPermission(context, "altri-view")) {
	      if (!hasPermission(context, "altri-view")) {
	        return ("PermissionError");
	      }
	      //Bypass dashboard and search form for portal users
	      /*if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));*/
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
	        "AccountsCalendarInfo");
	   /* if (calendarInfo == null) {
	      calendarInfo = new CalendarBean(
	          this.getUser(context, this.getUserId(context)).getLocale());
	      calendarInfo.addAlertType(
	          "Accounts", "org.aspcfs.modules.abusivismi.base.AccountsListScheduledActions", "Accounts");
	      calendarInfo.setCalendarDetailsView("Accounts");
	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
	    }*/

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
	    return ("DashboardSceltaOK");
	  }


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-view")) {
      return ("PermissionError");
    }

    String source = (String) context.getRequest().getParameter("source");
    String banca = (String) context.getRequest().getParameter("searchBanca");
    OrganizationList organizationList = new OrganizationList();
    addModuleBean(context, "View Accounts", "Search Results");

    if(!"".equals(banca) && banca!=null) {
    	organizationList.setBanca(banca);
    	 }
    	 
    
    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("Abusivismi.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
    /*  if (isPortalUser(context)) {
        organizationList.setOrgSiteId(this.getUserSiteId(context));
        source = "searchForm";
      }*/
      organizationList.setTipologia(4);
      searchListInfo.setListView("all");
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      
   
      
      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("Abusivismi.do?command=Search");
        }
        //Build the organization list
        organizationList.setPagedListInfo(searchListInfo);
        organizationList.setMinerOnly(false);
        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

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
          organizationList.setIncludeOrganizationWithoutSite(false);//era true
        }
        if(this.getUserSiteId(context)>0){
        	 organizationList.setOrgSiteId(this.getUserSiteId(context));
             organizationList.setIncludeOrganizationWithoutSite(false);
        }
        
        //fetching criterea for account status (active, disabled or any)
        int enabled = searchListInfo.getFilterKey("listFilter2");
        organizationList.setIncludeEnabled(enabled);
        //If the user is a portal user, fetching only the
        //the organization that he access to
        //(i.e., the organization for which he is an account contact
      /*  if (isPortalUser(context)) {
          //organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
          //organizationList.setOrgId(getPortalUserPermittedOrgId(context));
       }*/
        organizationList.buildList(db);
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
        if (organizationList.size() == 1 && organizationList.getAssetSerialNumber() != null) {
          AssetList assets = new AssetList();
          assets.setOrgId(((Organization) organizationList.get(0)).getId());
          assets.setSerialNumber(organizationList.getAssetSerialNumber());
          assets.setSkipParentIdRequirement(true);
          assets.buildList(db);
          if (assets.size() == 1) {
            Asset asset = (Asset) assets.get(0);
            context.getRequest().setAttribute(
                "id", String.valueOf(asset.getId()));
            return "AssetDetailsOK";
          }
        }
        return "ListOK";
      } 
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "";
  }


  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandInsert(ActionContext context) throws SQLException {
    if (!hasPermission(context, "abusivismi-abusivismi-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    Organization insertedOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg = (Organization) context.getFormBean();
    Audit audit = new Audit();
    audit.setLivelloRischioFinale(-1);

    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
  //Process the login request

  
  
    
    try {
      db = this.getConnection(context);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      //aggiunto da d.dauria
    
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      

        //don't want to populate the addresses, etc. if this is an individual account
        newOrg.setIsIndividual(false);
        newOrg.setRequestItems(context);
      
      if (this.getUserSiteId(context) != -1) {
        // Set the site Id of the account to be equal to the site Id of the user
        // for a new account
        if (newOrg.getId() == -1) {
          newOrg.setSiteId(this.getUserSiteId(context));
        } else {
          // Check whether the user has access to update the organization
          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
            return ("PermissionError");
          }
        }
      }

      
      isValid = this.validateObject(context, db, newOrg);
      
      //Prima della insert trasformare i valori delle coordinate...aggiunta
      org.aspcfs.modules.abusivismi.base.OrganizationAddress so = null;
      org.aspcfs.modules.abusivismi.base.OrganizationAddress sedeLegale = null;
      org.aspcfs.modules.abusivismi.base.OrganizationAddress sedeMobile = null;
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.abusivismi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.abusivismi.base.OrganizationAddress) it.next();
    	  if(thisAddress.getType()==5)
    	  {
    		  so=thisAddress;
    	  }
    	  if(thisAddress.getType()==7)
    	  {
    		  sedeMobile=thisAddress;
    	  }
    	  if(thisAddress.getType()==1)
    	  {
    		  sedeLegale=thisAddress;
    	  }
    	  
    	  
    	  //Richiamo il metodo per il calcolo delle coordinate
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
        	  //Prova nel fare il passaggio inverso
        	  //this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
        	  
    	  }  
      }//Fine aggiunta
      
      
      if (isValid) {
        recordInserted = newOrg.insert(db,context);
      }
      if (recordInserted) {
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);  
          
      }
      
   
      /* Parametri necessari per l'invocazione della jsp go_to_detail.jsp invocata quando l'inserimento va a buon fine("InsertOK")*/
      context.getRequest().setAttribute("commandD", "Abusivismi.do?command=Details");
      context.getRequest().setAttribute("org_cod", "&orgId="+insertedOrg.getOrgId());
      
     
      
      
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
        return ("PopupCloseOK");
      }
      if (target != null && "add_contact".equals(target)) {
       	  return ("InsertAndAddContactOK");  
      } else {
    	  return ("InsertOK");
      }
    }
    return (executeCommandAdd(context));
  }

 

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandUpdate(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "abusivismi-abusivismi-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean();
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    newOrg.setTypeList(
    context.getRequest().getParameterValues("selectedList"));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
  newOrg.setIsIndividual(false);
        newOrg.setRequestItems(context);
     
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.abusivismi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.abusivismi.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      
      if (isValid) {
    	  if(context.getRequest().getParameter("name")==null || context.getRequest().getParameter("name").equals(""))
    		  newOrg.setName("IGNOTO");
        resultCount = newOrg.update(db,context);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        if (context.getRequest().getParameter("form_type")!=null && context.getRequest().getParameter("form_type").equalsIgnoreCase(
            "individual")) {
          
        }
        //update all contacts which are associated with this organization
       
      
        
         
        LookupList statoLab = new LookupList(db, "lookup_stato_lab");
        statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("statoLab", statoLab);
        
        LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
        OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
        
        LookupList impianto = new LookupList(db, "lookup_impianto");
        impianto.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("impianto", impianto);
        

      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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

  /**
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandDelete(ActionContext context) throws SQLException {
    if (!hasPermission(context, "abusivismi-abusivismi-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
      
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
        return ("PermissionError");
      }
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          // NOTE: these may have different options later
          thisOrganization.setContactDelete(true);
          thisOrganization.setRevenueDelete(true);
          thisOrganization.setDocumentDelete(true);
          OpportunityHeaderList opportunityList = new OpportunityHeaderList();
          opportunityList.setOrgId(thisOrganization.getOrgId());
          opportunityList.buildList(db);
          opportunityList.invalidateUserData(context, db);
          thisOrganization.setForceDelete(
              context.getRequest().getParameter("forceDelete"));
          recordDeleted = thisOrganization.delete(
              db, context, getDbNamePath(context));
          UserBean user = (UserBean)context.getSession().getAttribute("User");
         
         
          
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          recordDeleted = thisOrganization.disable(db);
          UserBean user = (UserBean)context.getSession().getAttribute("User");
         
        }
      }
    } catch (Exception e) {
    	 UserBean user = (UserBean)context.getSession().getAttribute("User");
        
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        context.getRequest().setAttribute(
            "refreshUrl", "Abusivismi.do?command=Search");
        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
            context.getRequest().getParameter("return"))) {
          return executeCommandSearch(context);
        }
        return "DeleteOK";
      } else {
        processErrors(context, thisOrganization.getErrors());
        return (executeCommandSearch(context));
      }
    } else {
      UserBean user = (UserBean)context.getSession().getAttribute("User");
    
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.accountDeletion"));
      context.getRequest().setAttribute(
          "refreshUrl", "Abusivismi.do?command=Search");
      return ("DeleteError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
 * @throws SQLException 
   */
  public String executeCommandTrash(ActionContext context) throws SQLException {
    if (!hasPermission(context, "abusivismi-abusivismi-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
      String note = context.getRequest().getParameter("note");

      //check permission to record
      if (!isRecordAccessPermitted(context, db, orgId)) {
        return ("PermissionError");
      }
    
      // NOTE: these may have different options later
      recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
     } catch (Exception e) {
    	
      context.getRequest().setAttribute(
          "refreshUrl", "Abusivismi.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Abusivismi.do?command=Search");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearch(context);
      }
      return "DeleteOK";
    } else {
      return (executeCommandSearch(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-delete")) {
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
      recordUpdated = thisOrganization.updateStatus(
          db, context, false, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisOrganization.getOwner());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Abusivismi.do?command=Search");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearch(context);
      }
      return this.executeCommandDetails(context);
    } else {
      return (executeCommandSearch(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
      recordEnabled = thisOrganization.enable(db);
      if (!recordEnabled) {
        this.validateObject(context, db, thisOrganization);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    return (executeCommandSearch(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    int orgId = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (context.getRequest().getParameter("id") != null) {
    	orgId = Integer.parseInt(context.getRequest().getParameter("id"));
    	}
    try {
      db = this.getConnection(context);
      //check permission to record
      if (!isRecordAccessPermitted(context, db, orgId)) {
        return ("PermissionError");
      }
      /*htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      
      
      if (!AccountsUtil.isCancellabile(db, orgId)){
    	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
          htmlDialog.addMessage("<br/>");
          htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
      }
      else {
      htmlDialog.addMessage("<form action=\"Abusivismi.do?command=Trash&auto-populate=true\" method=\"post\">");
      htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
      htmlDialog.addMessage("<br/>");
      htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
      htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
      htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
      htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
      htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
      htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+orgId+"\"/>");
      htmlDialog.addMessage("</form>");
      }

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getSession().setAttribute("Dialog", htmlDialog);
    return ("ConfirmDeleteOK");
  }


  /**
   *  Modify: Displays the form used for modifying the information of the
   *  currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
      ArrayList<Comuni> listaComuni = new ArrayList<Comuni>();
      Comuni c = new Comuni();
		listaComuni = c.buildList(db, user.getSiteId());
		 context.getRequest().setAttribute("Comuni", listaComuni);
      
      newOrg = (Organization) context.getFormBean();
      if (newOrg.getId() == -1) {
        newOrg = new Organization(db, tempid);
        
        
        //In fase di modifica
        Iterator it_coords = newOrg.getAddressList().iterator();
        while(it_coords.hasNext()){
        	  
        	  org.aspcfs.modules.abusivismi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.abusivismi.base.OrganizationAddress) it_coords.next();
        	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
        		  String spatial_coords [] = null;
        		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//        		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//        		  {
//        			 AjaxCalls ajaxCall = new AjaxCalls();
//        			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//        			thisAddress.setLatitude(coordinate[1]);
//        		  thisAddress.setLongitude(coordinate[0]);
//        		  }
//        		  else
//        		  {
        			  thisAddress.setLatitude(spatial_coords[0]);
        			  thisAddress.setLongitude(spatial_coords[1]);
        		//  }

        		  
        		  
        	  }
          }
        
        
        
      
      }
      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
        return ("PermissionError");
      }
   
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute("systemStatus", systemStatus);
      
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);



      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      ;

      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
     
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("CountrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
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

  
/**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
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


  /**
   *  Description of the Method
   *
   * @param  context     Description of the Parameter
   * @param  organization  Description of the Parameter
   * @param  db  Description of the Parameter
   *
   * @exception  Exception  Description of the Exception
   */
 
  public String executeCommandCessazioneAttivita(ActionContext context) throws ParseException
	{
	 
	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
		String dataFineString = context.getParameter("dataCessazioneAttivita");
		Timestamp dataFine = null;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
		String note = context.getParameter("noteCessazione");
		int userId = getUserId(context);
	  
		Connection db = null;
		try
		{
			db=super.getConnection(context);
			Organization org = new Organization(db,orgId);
			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
			context.getRequest().setAttribute("OrgDetails", org);
		}
		catch(SQLException e)
		{
			
		}
			finally{
				super.freeConnection(context, db);
			}
		return "InsertOK";
	}
}




  


