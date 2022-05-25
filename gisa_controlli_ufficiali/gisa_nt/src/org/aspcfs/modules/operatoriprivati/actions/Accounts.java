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
package org.aspcfs.modules.operatoriprivati.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneanagrafica.base.Anagrafica;
import org.aspcfs.modules.gestioneanagrafica.base.Impresa;
import org.aspcfs.modules.gestioneanagrafica.base.Indirizzo;
import org.aspcfs.modules.gestioneanagrafica.base.Istanza;
import org.aspcfs.modules.gestioneanagrafica.base.SoggettoFisico;
import org.aspcfs.modules.gestioneanagrafica.base.Stabilimento;
import org.aspcfs.modules.gestioneanagrafica.utils.GestioneAnagraficaUtil;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
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
//  public String executeCommandDefault(ActionContext context) {
//
//    return executeCommandDashboard(context);
//  }
//
//  
//  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "operatoriprivati-operatoriprivati-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("altId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriprivatiVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
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
	      newOrg = new Organization(db, passedId, "priv");
	      ticList.setAltId(passedId);
	    
	      
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
  
//  public String executeCommandViewCessazionevariazione(ActionContext context) {
//	    if (!hasPermission(context, "operatoriprivati-operatoriprivati-cessazionevariazione-view")) {
//	      return ("PermissionError");
//	    }
//	    Connection db = null;
//	    org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
//	    Organization newOrg = null;
//	    //Process request parameters
//	    int passedId = Integer.parseInt(
//	        context.getRequest().getParameter("altId"));
//
//	    //Prepare PagedListInfo
//	    PagedListInfo ticketListInfo = this.getPagedListInfo(
//	        context, "AccountTicketInfo", "t.entered", "desc");
//	    ticketListInfo.setLink(
//	        "Operatoriprivati.do?command=ViewCessazionevariazione&orgId=" + passedId);
//	    ticList.setPagedListInfo(ticketListInfo);
//	    try {
//	      db = this.getConnection(context);
//	      //find record permissions for portal users
//	      
//	      SystemStatus systemStatus = this.getSystemStatus(context);
//	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
//	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
//	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
//	      
//	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
//	      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
//	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
//	      if (!isRecordAccessPermitted(context, db, passedId)) {
//	        return ("PermissionError");
//	      }
//	      newOrg = new Organization(db, passedId);
//	      ticList.setOrgId(passedId);
//	      if (newOrg.isTrashed()) {
//	        ticList.setIncludeOnlyTrashed(true);
//	      }
//	      ticList.buildList(db);
//	      context.getRequest().setAttribute("TicList", ticList);
//	      context.getRequest().setAttribute("OrgDetails", newOrg);
//	      addModuleBean(context, "View Accounts", "Accounts View");
//	    } catch (Exception errorMessage) {
//	      context.getRequest().setAttribute("Error", errorMessage);
//	      return ("SystemError");
//	    } finally {
//	      this.freeConnection(context, db);
//	    }
//	    return getReturn(context, "ViewCessazionevariazione");
//	  }
//  
//
//  /**
//   *  Reports: Displays a list of previously generated reports with
//   *  view/delete/download options.
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandReports(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-reports-view")) {
//      return ("PermissionError");
//    }
//    //Set the menu: the user is in the Reports module
//    addModuleBean(context, "Reports", "ViewReports");
//    //Retrieve the paged list that will be used for paging through reports
//    PagedListInfo rptListInfo = this.getPagedListInfo(context, "RptListInfo");
//    rptListInfo.setLink("Operatoriprivati.do?command=Reports");
//    //Prepare the file list for accounts
//    FileItemList files = new FileItemList();
//    files.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
//    files.setPagedListInfo(rptListInfo);
//    //Check the combo box value from the report list for filtering reports
//    if ("all".equals(rptListInfo.getListView())) {
//      //Show only the reports that this user or someone below this user created
//      files.setOwnerIdRange(this.getUserRange(context));
//    } else {
//      //Show only this user's reports
//      files.setOwner(this.getUserId(context));
//    }
//    Connection db = null;
//    try {
//      //Get a connection from the connection pool for this user
//      db = this.getConnection(context);
//      //Generate the list of files based on the above criteria
//      files.buildList(db);
//      context.getRequest().setAttribute("FileList", files);
//    } catch (Exception errorMessage) {
//      //An error occurred, go to generic error message page
//      context.getRequest().setAttribute("Error", errorMessage);
//      return ("SystemError");
//    } finally {
//      //Always free the database connection
//      this.freeConnection(context, db);
//    }
//    return ("ReportsOK");
//  }
//
//
//  /**
//   *  Search: Displays the Account search form
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandSearchForm(ActionContext context) {
//    if (!(hasPermission(context, "operatoriprivati-operatoriprivati-view"))) {
//      return ("PermissionError");
//    }
//    
//   /* if (!(hasPermission(context, "request-view"))) {
//        return ("PermissionError");
//      }
//*/
//    //Bypass search form for portal users
//    if (isPortalUser(context)) {
//      return (executeCommandSearch(context));
//    }
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    Connection db = null;
//    try {
//      db = getConnection(context);
//      
//      
//      
///*    LookupList llist=new LookupList(db,"lookup_stabilimenti_types");
//      llist.addItem(-1, "-nessuno-");
//      context.getRequest().setAttribute("llist", llist);
//*/
//      
//      
//      LookupList siteList = new LookupList(db, "lookup_site_id");
//      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
//      context.getRequest().setAttribute("SiteList", siteList);
//      
//      
//      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("statoLab", statoLab);
//      
//      LookupList impianto = new LookupList(db, "lookup_impianto");
//      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("impianto", impianto);
//      
//      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
//      PagedListInfo orgListInfo = this.getPagedListInfo(
//          context, "SearchOrgListInfo");
//      orgListInfo.setCurrentLetter("");
//      orgListInfo.setCurrentOffset(0);
//      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
//        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
//        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
//          HashMap map = new HashMap();
//          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
//          stateSelect.setPreviousStates(map);
//        }
//        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
//      }
//      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
//        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
//        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
//          HashMap map = new HashMap();
//          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
//          stateSelect.setPreviousStates(map);
//        }
//        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
//      }
//    } catch (Exception e) {
//      context.getRequest().setAttribute("Error", e);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "Search Accounts", "Accounts Search");
//    return ("SearchOK");
//  }
//
//
//  /**
//   *  Add: Displays the form used for adding a new Account
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandAdd(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-add")) {
//      return ("PermissionError");
//    }
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    Connection db = null;
//    try {
//      db = this.getConnection(context);
//      LookupList siteList = new LookupList(db, "lookup_site_id");
//      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("SiteList", siteList);
//     
//      
//      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("statoLab", statoLab);
//      
//      LookupList impianto = new LookupList(db, "lookup_impianto");
//      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("impianto", impianto);
//      
//      
//
//      Organization newOrg = (Organization) context.getFormBean();
//      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
//      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
//      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
//      context.getRequest().setAttribute("StateSelect", stateSelect);
//      if (newOrg.getEnteredBy() != -1) {
//        newOrg.setTypeListToTypes(db);
//        context.getRequest().setAttribute("OrgDetails", newOrg);
//      }
//    } catch (Exception e) {
//      context.getRequest().setAttribute("Error", e);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "Add Account", "Accounts Add");
//    context.getRequest().setAttribute(
//        "systemStatus", this.getSystemStatus(context));
//    //if a different module reuses this action then do a explicit return
//    if (context.getRequest().getParameter("actionSource") != null) {
//        return getReturn(context, "AddAccount");
//    }
//
//    return getReturn(context, "Add");
//  }
//
//
//  /**
//   *  Details: Displays all details relating to the selected Account. The user
//   *  can also goto a modify page from this form or delete the Account entirely
//   *  from the database
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandDetails(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-view")) {
//      return ("PermissionError");
//    }
//
//
//	    int id = -1;
//		int stabId = -1;
//		int altId = -1;
//		
//		String idString = context.getRequest().getParameter("id");
//		String stabIdString = context.getRequest().getParameter("stabId");
//		String altIdString = context.getRequest().getParameter("altId");
//
//		
//		try {id = Integer.parseInt(idString);} catch (Exception e){}
//		if (id==-1)
//			idString = (String) context.getRequest().getAttribute("id");
//		try {id = Integer.parseInt(idString);} catch (Exception e){}
//		
//
//		try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
//		if (stabId==-1)
//			stabIdString = (String) context.getRequest().getAttribute("stabId");
//		try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
//		
//		try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
//		if (altId==-1)
//			altIdString = (String) context.getRequest().getAttribute("altId");
//		try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
//		
//		if (id<0 && stabId>0)
//			id=stabId;
//		
//	    Connection db = null;
//		
//		try {
//			db = this.getConnection(context);
//				Stabilimento stab = null;
//				
//				if (id>0)
//					stab =	new Stabilimento(db, id);
//				else if (altId>0)
//					stab =	new Stabilimento(db, altId, true);
//				context.getRequest().setAttribute("Stabilimento", stab);
//					
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//			this.freeConnection(context, db);
//		}
//    
//    
//    
//    
//    
//    
//      return getReturn(context, "Details");
//    
//  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
//   */
//  public String executeCommandDashboard(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-dashboard-view")) {
//      if (!hasPermission(context, "operatoriprivati-operatoriprivati-view")) {
//        return ("PermissionError");
//      }
//      //Bypass dashboard and search form for portal users
//      if (isPortalUser(context)) {
//        return (executeCommandSearch(context));
//      }
//      return (executeCommandSearchForm(context));
//    }
//    addModuleBean(context, "Dashboard", "Dashboard");
//    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
//        "AccountsCalendarInfo");
//    if (calendarInfo == null) {
//      calendarInfo = new CalendarBean(
//          this.getUser(context, this.getUserId(context)).getLocale());
//      calendarInfo.addAlertType(
//          "Accounts", "org.aspcfs.modules.operatoriprivati.base.AccountsListScheduledActions", "Accounts");
//      calendarInfo.setCalendarDetailsView("Accounts");
//      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
//    }
//
//    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
//
//    //this is how we get the multiple-level heirarchy...recursive function.
//    User thisRec = thisUser.getUserRecord();
//
//    UserList shortChildList = thisRec.getShortChildList();
//    UserList newUserList = thisRec.getFullChildList(
//        shortChildList, new UserList());
//
//    newUserList.setMyId(getUserId(context));
//    newUserList.setMyValue(
//        thisUser.getUserRecord().getContact().getNameLastFirst());
//    newUserList.setIncludeMe(true);
//
//    newUserList.setJsEvent(
//        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
//    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
//        "userId", getUserId(context));
//    userListSelect.addAttribute("id", "userId");
//    context.getRequest().setAttribute("Return", "Accounts");
//    context.getRequest().setAttribute("NewUserList", userListSelect);
//    return ("DashboardOK");
//  }
//  
//  public String executeCommandDashboardScelta(ActionContext context) {
//	    if (!hasPermission(context, "altri-view")) {
//	      if (!hasPermission(context, "altri-view")) {
//	        return ("PermissionError");
//	      }
//	      //Bypass dashboard and search form for portal users
//	      /*if (isPortalUser(context)) {
//	        return (executeCommandSearch(context));
//	      }
//	      return (executeCommandSearchForm(context));*/
//	    }
//	    addModuleBean(context, "Dashboard", "Dashboard");
//	    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
//	        "AccountsCalendarInfo");
//	    if (calendarInfo == null) {
//	      calendarInfo = new CalendarBean(
//	          this.getUser(context, this.getUserId(context)).getLocale());
//	      calendarInfo.addAlertType(
//	          "Accounts", "org.aspcfs.modules.operatoriprivati.base.AccountsListScheduledActions", "Accounts");
//	      calendarInfo.setCalendarDetailsView("Accounts");
//	      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
//	    }
//
//	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
//
//	    //this is how we get the multiple-level heirarchy...recursive function.
//	    User thisRec = thisUser.getUserRecord();
//
//	    UserList shortChildList = thisRec.getShortChildList();
//	    UserList newUserList = thisRec.getFullChildList(
//	        shortChildList, new UserList());
//
//	    newUserList.setMyId(getUserId(context));
//	    newUserList.setMyValue(
//	        thisUser.getUserRecord().getContact().getNameLastFirst());
//	    newUserList.setIncludeMe(true);
//
//	    newUserList.setJsEvent(
//	        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
//	    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
//	        "userId", getUserId(context));
//	    userListSelect.addAttribute("id", "userId");
//	    context.getRequest().setAttribute("Return", "Accounts");
//	    context.getRequest().setAttribute("NewUserList", userListSelect);
//	    return ("DashboardSceltaOK");
//	  }
//
//
//  /**
//   *  Search Accounts
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
//   */
//  public String executeCommandSearch(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-view")) {
//      return ("PermissionError");
//    }
//
//    String source = (String) context.getRequest().getParameter("source");
//    String banca = (String) context.getRequest().getParameter("searchBanca");
//    String codiceAllerta = context.getRequest().getParameter("searchcodiceAllerta");
//    OrganizationList organizationList = new OrganizationList();
//    addModuleBean(context, "View Accounts", "Search Results");
//
//    if(!"".equals(banca) && banca!=null) {
//    	organizationList.setBanca(banca);
//    	 }
//    	 
//    if(!"".equals(codiceAllerta) && codiceAllerta != null)
//    	organizationList.setCodiceAllerta(codiceAllerta);
//    //Prepare pagedListInfo
//    PagedListInfo searchListInfo = this.getPagedListInfo(
//        context, "SearchOrgListInfo");
//    searchListInfo.setLink("Operatoriprivati.do?command=Search");
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    //Need to reset any sub PagedListInfos since this is a new account
//    this.resetPagedListInfo(context);
//    Connection db = null;
//    try {
//      db = this.getConnection(context);
//
//      //For portal usr set source as 'searchForm' explicitly since
//      //the search form is bypassed.
//      //temporary solution for page redirection for portal user.
//      if (isPortalUser(context)) {
//        organizationList.setOrgSiteId(this.getUserSiteId(context));
//        source = "searchForm";
//      }
//      organizationList.setTipologia(13);
//      searchListInfo.setListView("all");
//      //return if no criteria is selected
//      if ((searchListInfo.getListView() == null || "".equals(
//          searchListInfo.getListView())) && !"searchForm".equals(source)) {
//        return "ListOK";
//      }
//      LookupList siteList = new LookupList(db, "lookup_site_id");
//      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//      siteList.addItem(-2, "-- TUTTI --");
//      context.getRequest().setAttribute("SiteIdList", siteList);
//      
//
//    //inserito da Carmela
//      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
//      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
//      
//      
//      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("statoLab", statoLab);
//      
//      LookupList impianto = new LookupList(db, "lookup_impianto");
//      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("impianto", impianto);
//      
//      //Display list of accounts if user chooses not to list contacts
//      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
//        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
//          this.deletePagedListInfo(context, "SearchOrgListInfo");
//          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
//          searchListInfo.setLink("Operatoriprivati.do?command=Search");
//        }
//        //Build the organization list
//        organizationList.setPagedListInfo(searchListInfo);
//        organizationList.setMinerOnly(false);
//        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
//
//        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
//        
//        searchListInfo.setSearchCriteria(organizationList, context);
//        //fetching criterea for account source (my accounts or all accounts)
//        if ("my".equals(searchListInfo.getListView())) {
//          organizationList.setOwnerId(this.getUserId(context));
//        }
//        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
//          organizationList.setOrgSiteId(this.getUserSiteId(context));
//          organizationList.setIncludeOrganizationWithoutSite(false);
//        } else if (organizationList.getOrgSiteId() == -1) {
//          organizationList.setIncludeOrganizationWithoutSite(true);
//        }
//        //fetching criterea for account status (active, disabled or any)
//        int enabled = searchListInfo.getFilterKey("listFilter2");
//        organizationList.setIncludeEnabled(enabled);
//        //If the user is a portal user, fetching only the
//        //the organization that he access to
//        //(i.e., the organization for which he is an account contact
//        if (isPortalUser(context)) {
//          organizationList.setOrgSiteId(this.getUserSiteId(context));
//          organizationList.setIncludeOrganizationWithoutSite(false);
//          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
//        }
//        organizationList.buildList(db);
//        context.getRequest().setAttribute("OrgList", organizationList);
//        context.getSession().setAttribute("previousSearchType", "accounts");
//        if (organizationList.size() == 1 && organizationList.getAssetSerialNumber() != null) {
//         
//        
//        }
//        return "ListOK";
//      } 
//    } catch (Exception e) {
//      //Go through the SystemError process
//      context.getRequest().setAttribute("Error", e);
//      e.printStackTrace();
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    return "";
//  }
//
//
//  /**
//   *  Insert: Inserts a new Account into the database.
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
// * @throws SQLException 
//   */
//  public String executeCommandInsert(ActionContext context) throws SQLException {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-add")) {
//      return ("PermissionError");
//    }
//    Connection db = null;
//    boolean recordInserted = false;
//    boolean isValid = false;
//    Organization insertedOrg = null;
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    Organization newOrg = (Organization) context.getFormBean();
//    Audit audit = new Audit();
//    audit.setLivelloRischioFinale(-1);
//
//    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
//    newOrg.setEnteredBy(getUserId(context));
//    newOrg.setModifiedBy(getUserId(context));
//    newOrg.setOwner(getUserId(context));
//    UserBean user = (UserBean) context.getSession().getAttribute("User");
//    String ip = user.getUserRecord().getIp();
//    newOrg.setIp_entered(ip);
//    newOrg.setIp_modified(ip);
//    
//    try {
//      db = this.getConnection(context);
//
//      LookupList siteList = new LookupList(db, "lookup_site_id");
//      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
//      context.getRequest().setAttribute("SiteList", siteList);
//      
//      
//      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("statoLab", statoLab);
//      
//      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
//      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
//      
//      LookupList impianto = new LookupList(db, "lookup_impianto");
//      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("impianto", impianto);
//      
//      //set the name to namelastfirstmiddle if individual
//
//       
//        newOrg.setRequestItems(context);
//      
//      if (this.getUserSiteId(context) != -1) {
//        // Set the site Id of the account to be equal to the site Id of the user
//        // for a new account
//        if (newOrg.getId() == -1) {
//          newOrg.setSiteId(this.getUserSiteId(context));
//        } else {
//          // Check whether the user has access to update the organization
//          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
//            return ("PermissionError");
//          }
//        }
//      }
//
//      isValid = this.validateObject(context, db, newOrg);
//      Iterator it = newOrg.getAddressList().iterator();
//      while(it.hasNext())
//      {
//    	  org.aspcfs.modules.operatoriprivati.base.OrganizationAddress thisAddress = (org.aspcfs.modules.operatoriprivati.base.OrganizationAddress) it.next(); 
//    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
//    	  String[] coords = null;
//    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
//    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//    		  thisAddress.setLatitude(coords[1]);
//        	  thisAddress.setLongitude(coords[0]);
//        	  //Prova nel fare il passaggio inverso
//        	  //this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//        	  
//    	  }  
//    	  
//      }//Fine aggiunta
//      
//      if (isValid) {
//        recordInserted = newOrg.insert(db,context);
//      }
//      if (recordInserted) {
//        insertedOrg = new Organization(db, newOrg.getAltId());
//        context.getRequest().setAttribute("OrgDetails", insertedOrg);
//        addRecentItem(context, newOrg);
//      
//          
//      }
//    } catch (Exception errorMessage) {
//    	 
//      context.getRequest().setAttribute("Error", errorMessage);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "View Accounts", "Accounts Insert ok");
//    if (recordInserted) {
//      String target = context.getRequest().getParameter("target");
//      if (context.getRequest().getParameter("popup") != null) {
//        return ("ClosePopupOK");
//      }
//      if (target != null && "add_contact".equals(target)) {
//       	  return ("InsertAndAddContactOK");  
//      } else {
//    	  return ("InsertOK");
//      }
//    }
//    return (executeCommandAdd(context));
//  }
//
//
//  /**
//   *  Update: Updates the Organization table to reflect user-entered
//   *  changes/modifications to the currently selected Account
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
// * @throws SQLException 
//   */
//  public String executeCommandUpdate(ActionContext context) throws SQLException {
//    if (!(hasPermission(context, "operatoriprivati-operatoriprivati-edit"))) {
//      return ("PermissionError");
//    }
//    Connection db = null;
//    int resultCount = -1;
//    boolean isValid = false;
//    Organization newOrg = (Organization) context.getFormBean();
//    Organization oldOrg = null;
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    newOrg.setTypeList(
//        context.getRequest().getParameterValues("selectedList"));
//    newOrg.setModifiedBy(getUserId(context));
//    newOrg.setEnteredBy(getUserId(context));
//    UserBean user = (UserBean) context.getSession().getAttribute("User");
//    String ip = user.getUserRecord().getIp();
//    newOrg.setIp_entered(ip);
//    newOrg.setIp_modified(ip);
//    try {
//      db = this.getConnection(context);
//      //set the name to namelastfirstmiddle if individual
//  
//        //don't want to populate the addresses, etc. if this is an individual account
//        newOrg.setIsIndividual(false);
//        newOrg.setRequestItems(context);
//    
//      oldOrg = new Organization(db, newOrg.getAltId());
//      isValid = this.validateObject(context, db, newOrg);
//      
//      
//      Iterator it = newOrg.getAddressList().iterator();
//      while(it.hasNext())
//      {
//    	  org.aspcfs.modules.operatoriprivati.base.OrganizationAddress thisAddress = (org.aspcfs.modules.operatoriprivati.base.OrganizationAddress) it.next();
//    	  
//    	  if (thisAddress.getState().equals("-1") && !thisAddress.getOtherState().equals("-1")){
//    		  thisAddress.setState(thisAddress.getOtherState());
//    	  }
//    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
//    	  String[] coords = null;
//    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
//    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//    		  thisAddress.setLatitude(coords[1]);
//        	  thisAddress.setLongitude(coords[0]);
//    	  }
//      }//Fine aggiunta
//      
//      
//      
//      if (isValid) {
//        resultCount = newOrg.update(db,context);
//      }
//      if (resultCount == 1) {
//        processUpdateHook(context, oldOrg, newOrg);
//        //if this is an individual account, populate and update the primary contact
//       
//        //update all contacts which are associated with this organization
//       
//     LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//        statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//        context.getRequest().setAttribute("statoLab", statoLab);
//        
//        LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
//        OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
//        context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
//        
//        LookupList impianto = new LookupList(db, "lookup_impianto");
//        impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//        context.getRequest().setAttribute("impianto", impianto);
//        
//      }
//    } catch (Exception errorMessage) {
//    	
//      context.getRequest().setAttribute("Error", errorMessage);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "View Accounts", "Modify Account");
//    
//    if (resultCount == -1 || !isValid) {
//      return (executeCommandModify(context));
//    } else if (resultCount == 1) {
//      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
//          "return").equals("list")) {
//        return (executeCommandSearch(context));
//      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
//          "return").equals("dashboard")) {
//        return (executeCommandDashboard(context));
//      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
//          "return").equals("Calendar")) {
//        if (context.getRequest().getParameter("popup") != null) {
//          return ("PopupCloseOK");
//        }
//      } else {
//        return ("UpdateOK");
//      }
//    } else {
//    	
//      
//      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
//      return ("UserError");
//    }
//    return ("UpdateOK");
//  }
//
//  /**
//   *  Update: Updates the Organization table to reflect user-entered
//   *  changes/modifications to the currently selected Account
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandUpdateCatRischio(ActionContext context) {
//    if (!(hasPermission(context, "operatoriprivati-tipochecklist-edit"))) {
//      return ("PermissionError");
//    }
//    Connection db = null;
//    int resultCount = -1;
//    boolean isValid = false;
//    String org_id = context.getRequest().getParameter( "altId" );
//    String account_size = context.getRequest().getParameter( "accountSize" );
//    Organization oldOrg = null;
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    try {
//      db = this.getConnection(context);
//      //set the name to namelastfirstmiddle if individual
//
//      Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
//      newOrg.setAccountSize( account_size );
//      newOrg.setTypeList(
//          context.getRequest().getParameterValues("selectedList"));
//      newOrg.setModifiedBy(getUserId(context));
//      newOrg.setEnteredBy(getUserId(context));
//      
//      oldOrg = new Organization(db, newOrg.getAltId());
//      isValid = this.validateObject(context, db, newOrg);
//      if (isValid) {
//        resultCount = newOrg.update(db,context);
//      }
//
//    } catch (Exception errorMessage) {
//      context.getRequest().setAttribute("Error", errorMessage);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "View Accounts", "Modify Account");
//
//    return ("UpdateCatRischioOK");
//  }
//  
//  /**
//   *  Delete: Deletes an Account from the Organization table
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
// * @throws SQLException 
//   */
//  public String executeCommandDelete(ActionContext context) throws SQLException {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-delete")) {
//      return ("PermissionError");
//    }
//    SystemStatus systemStatus = this.getSystemStatus(context);
//    Exception errorMessage = null;
//    boolean recordDeleted = false;
//    Organization thisOrganization = null;
//    Connection db = null;
//    try {
//      db = this.getConnection(context);
//      thisOrganization = new Organization(
//          db, Integer.parseInt(context.getRequest().getParameter("altId")));
//      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
//        return ("PermissionError");
//      }
//      if (context.getRequest().getParameter("action") != null) {
//        if (((String) context.getRequest().getParameter("action")).equals(
//            "delete")) {
//          // NOTE: these may have different options later
//          thisOrganization.setContactDelete(true);
//          thisOrganization.setRevenueDelete(true);
//          thisOrganization.setDocumentDelete(true);
//         
//          recordDeleted = thisOrganization.delete(
//              db, context, getDbNamePath(context));
//         
//          
//        } else if (((String) context.getRequest().getParameter("action")).equals(
//            "disable")) {
//          recordDeleted = thisOrganization.disable(db);
//       
//        }
//      }
//    } catch (Exception e) {
//    
//      errorMessage = e;
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "Accounts", "Delete Account");
//    if (errorMessage == null) {
//      if (recordDeleted) {
//        
//        context.getRequest().setAttribute(
//            "refreshUrl", "Operatoriprivati.do?command=Search");
//        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
//            context.getRequest().getParameter("return"))) {
//          return executeCommandSearch(context);
//        }
//        return "DeleteOK";
//      } else {
//        processErrors(context, thisOrganization.getErrors());
//        return (executeCommandSearch(context));
//      }
//    } else {
//    
//      context.getRequest().setAttribute(
//          "actionError", systemStatus.getLabel(
//          "object.validation.actionError.accountDeletion"));
//      context.getRequest().setAttribute(
//          "refreshUrl", "Operatoriprivati.do?command=Search");
//      return ("DeleteError");
//    }
//  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
// * @throws SQLException 
//   */
  public String executeCommandTrash(ActionContext context) throws SQLException {
    if (!hasPermission(context, "operatoriprivati-operatoriprivati-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;

    Connection db = null;
    try {
        db = this.getConnection(context);
        int altId = Integer.parseInt(context.getRequest().getParameter("altId"));
        String note = context.getRequest().getParameter("note");

       // NOTE: these may have different options later
        recordUpdated = GestioneAnagraficaUtil.deleteCentralizzato(db, altId, note, this.getUserId(context));
      
      } catch (Exception e) {
    	
      context.getRequest().setAttribute(
          "refreshUrl", "Operatoriprivati.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Operatoriprivati.do?command=Search");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearch(context);
      }
      return "DeleteOK";
    } else {
      return (executeCommandSearch(context));
    }
  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
//   */
//  public String executeCommandRestore(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-delete")) {
//      return ("PermissionError");
//    }
//    boolean recordUpdated = false;
//    Organization thisOrganization = null;
//    Connection db = null;
//    try {
//      db = this.getConnection(context);
//      String orgId = context.getRequest().getParameter("altId");
//      //check permission to record
//      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
//        return ("PermissionError");
//      }
//      thisOrganization = new Organization(db, Integer.parseInt(orgId));
//      // NOTE: these may have different options later
//      recordUpdated = thisOrganization.updateStatus(
//          db, context, false, this.getUserId(context));
//      this.invalidateUserData(context, this.getUserId(context));
//      this.invalidateUserData(context, thisOrganization.getOwner());
//    } catch (Exception e) {
//      context.getRequest().setAttribute("Error", e);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "Accounts", "Delete Account");
//    if (recordUpdated) {
//      context.getRequest().setAttribute(
//          "refreshUrl", "Operatoriprivati.do?command=Search");
//      if ("list".equals(context.getRequest().getParameter("return"))) {
//        return executeCommandSearch(context);
//      }
//      return this.executeCommandDetails(context);
//    } else {
//      return (executeCommandSearch(context));
//    }
//  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
//   */
//  public String executeCommandEnable(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-edit")) {
//      return ("PermissionError");
//    }
//    boolean recordEnabled = false;
//    Organization thisOrganization = null;
//    Connection db = null;
//    try {
//      db = this.getConnection(context);
//      thisOrganization = new Organization(
//          db, Integer.parseInt(context.getRequest().getParameter("altId")));
//      recordEnabled = thisOrganization.enable(db);
//      if (!recordEnabled) {
//        this.validateObject(context, db, thisOrganization);
//      }
//    } catch (Exception e) {
//      context.getRequest().setAttribute("Error", e);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "Accounts", "Delete Account");
//    return (executeCommandSearch(context));
//  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   * @return          Description of the Return Value
//   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "operatoriprivati-operatoriprivati-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    int altId = -1;
    SystemStatus systemStatus = this.getSystemStatus(context);
    if (context.getRequest().getParameter("id") != null) {
      altId = Integer.parseInt(context.getRequest().getParameter("id"));
    }
    try {
        db = this.getConnection(context);
        //check permission to record
        if (!GestioneAnagraficaUtil.isCancellabile(db, altId)){
      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
            htmlDialog.addMessage("<br/>");
            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
        }
        else {
        htmlDialog.addMessage("<form action=\"Operatoriprivati.do?command=Trash&auto-populate=true\" method=\"post\">");
        htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
        htmlDialog.addMessage("<br/>");
        htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
        htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
        htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"altId\" value=\""+altId+"\"/>");
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
//
//
//  /**
//   *  Modify: Displays the form used for modifying the information of the
//   *  currently selected Account
//   *
//   * @param  context  Description of Parameter
//   * @return          Description of the Returned Value
//   */
//  public String executeCommandModify(ActionContext context) {
//    if (!hasPermission(context, "operatoriprivati-operatoriprivati-edit")) {
//      return ("PermissionError");
//    }
//    String orgid = context.getRequest().getParameter("altId");
//    context.getRequest().setAttribute("altId", orgid);
//    
//    int tempid = Integer.parseInt(orgid);
//    Connection db = null;
//    Organization newOrg = null;
//    try {
//      db = this.getConnection(context);
//      newOrg = (Organization) context.getFormBean();
//      if (newOrg.getId() == -1) {
//        newOrg = new Organization(db, tempid);
//        
//        //In fase di modifica
//        Iterator it_coords = newOrg.getAddressList().iterator();
//        while(it_coords.hasNext()){
//      	  
//      	  org.aspcfs.modules.operatoriprivati.base.OrganizationAddress thisAddress = (org.aspcfs.modules.operatoriprivati.base.OrganizationAddress) it_coords.next();
//      	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
//      		  String spatial_coords [] = null;
//      		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
////    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
////    		  {
////    			 AjaxCalls ajaxCall = new AjaxCalls();
////    			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
////    			thisAddress.setLatitude(coordinate[1]);
////    		  thisAddress.setLongitude(coordinate[0]);
////    		  }
////    		  else
////    		  {
//    			  thisAddress.setLatitude(spatial_coords[0]);
//    			  thisAddress.setLongitude(spatial_coords[1]);
//    		  //}
//      	  }
//        }
//       
//    
//      } else {
//        newOrg.setTypeListToTypes(db);
//      }
//      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
//        return ("PermissionError");
//      }
//   
//      SystemStatus systemStatus = this.getSystemStatus(context);
//      context.getRequest().setAttribute("systemStatus", systemStatus);
//      
//
//      
//      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
//      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
//      
//      
//      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("statoLab", statoLab);
//      
//      LookupList impianto = new LookupList(db, "lookup_impianto");
//      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("impianto", impianto);
//
//
//      LookupList siteList = new LookupList(db, "lookup_site_id");
//      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("SiteList", siteList);
//      
//    
//      //Make the StateSelect and CountrySelect drop down menus available in the request.
//      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
//      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
//      
//      context.getRequest().setAttribute("systemStatus", systemStatus);
//
//      UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
//      context.getRequest().setAttribute("UserList", userList);
//    } catch (Exception e) {
//      context.getRequest().setAttribute("Error", e);
//      return ("SystemError");
//    } finally {
//      this.freeConnection(context, db);
//    }
//    addModuleBean(context, "View Accounts", "Account Modify");
//    context.getRequest().setAttribute("OrgDetails", newOrg);
//    if (context.getRequest().getParameter("popup") != null) {
//      return ("PopupModifyOK");
//    } else {
//      return ("ModifyOK");
//    }
//  }
//
//  
////inserito carmela
//  public String executeCommandModificaCatRischio(ActionContext context) {
//	    if ((!hasPermission(context, "operatoriprivati-tipochecklist-add"))) {
//	      return ("PermissionError");
//	    }
//	    Connection db = null;
//	    SystemStatus systemStatus = this.getSystemStatus(context);
//	    Organization newOrg = null;
//	    try {
//	      String temporgId = context.getRequest().getParameter("altId");
//	      if (temporgId == null) {
//	        temporgId = (String) context.getRequest().getAttribute("altId");
//	      }
//	      int tempid = Integer.parseInt(temporgId);
//	      db = this.getConnection(context);
//	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
//	        return ("PermissionError");
//	      }
//	      newOrg = new Organization(db, tempid);
//	      //check whether or not the owner is an active User
//	      LookupList siteList = new LookupList(db, "lookup_site_id");
//	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
//	      context.getRequest().setAttribute("SiteList", siteList);
//	      
//	    } catch (Exception e) {
//	      context.getRequest().setAttribute("Error", e);
//	      return ("SystemError");
//	    } finally {
//	      this.freeConnection(context, db);
//	    }
//	    addRecentItem(context, newOrg);
//	    String action = context.getRequest().getParameter("action");
//	    if (action != null && action.equals("modify")) {
//	      //If user is going to the modify form
//	      addModuleBean(context, "Accounts", "Modify Account Details");
//	      return ("ModificaCatRischioOK");
//	    } else {
//	      //If user is going to the detail screen
//	      addModuleBean(context, "View Accounts", "View Account Details");
//	      context.getRequest().setAttribute("OrgDetails", newOrg);
//	      return getReturn(context, "ModificaCatRischio");
//	    }
//	  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context  Description of the Parameter
//   */
//  private void resetPagedListInfo(ActionContext context) {
//    
//    this.deletePagedListInfo(context, "AccountFolderInfo");
//    this.deletePagedListInfo(context, "RptListInfo");
//    this.deletePagedListInfo(context, "OpportunityPagedInfo");
//    this.deletePagedListInfo(context, "AccountTicketInfo");
//    this.deletePagedListInfo(context, "AutoGuideAccountInfo");
//    this.deletePagedListInfo(context, "RevenueListInfo");
//    this.deletePagedListInfo(context, "AccountDocumentInfo");
//    this.deletePagedListInfo(context, "ServiceContractListInfo");
//    this.deletePagedListInfo(context, "AssetListInfo");
//    this.deletePagedListInfo(context, "AccountProjectInfo");
//    this.deletePagedListInfo(context, "orgHistoryListInfo");
//  }
//
//
//  /**
//   *  Description of the Method
//   *
//   * @param  context        Description of the Parameter
//   * @param  siteId         Description of the Parameter
//   * @return                Description of the Return Value
//   * @exception  Exception  Description of the Exception
//   */
//  private UserList filterOwnerListForSite(ActionContext context, int siteId) throws Exception {
//    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
//    //this is how we get the multiple-level heirarchy...recursive function.
//    User thisRec = thisUser.getUserRecord();
//    UserList shortChildList = thisRec.getShortChildList();
//    UserList userList = thisRec.getFullChildList(
//        shortChildList, new UserList());
//    userList.setMyId(getUserId(context));
//    userList.setMyValue(thisUser.getContact().getNameLastFirst());
//    userList.setIncludeMe(true);
//    userList.setExcludeDisabledIfUnselected(true);
//    userList.setExcludeExpiredIfUnselected(true);
//
//    // filter possible owners for accounts with site ids.
//
//    // An account WITH a site id can be owned by a user with the same
//    // site id or by one who has access to all sites
//    // (i.e., siteId = -1 or site_id IS NULL)
//    if (siteId != -1) {
//      Iterator itr = userList.iterator();
//      while (itr.hasNext()) {
//        User tmpUser = (User) itr.next();
//        if (tmpUser.getSiteId() == -1) {
//          continue;
//        }
//        if (siteId != tmpUser.getSiteId()) {
//          itr.remove();
//        }
//      }
//    }
//
//    // An account WITHOUT a site id can ONLY be owned by a user with access
//    // to all sites
//    // (i.e., siteId = -1 or site_id IS NULL)
//    if (siteId == -1) {
//      Iterator itr = userList.iterator();
//      while (itr.hasNext()) {
//        User tmpUser = (User) itr.next();
//        if (siteId != tmpUser.getSiteId()) {
//          itr.remove();
//        }
//      }
//    }
//    return userList;
//  }
//  
//  /**
//   *  Description of the Method
//   *
//   * @param  context     Description of the Parameter
//   * @param  organization  Description of the Parameter
//   * @param  db  Description of the Parameter
//   *
//   * @exception  Exception  Description of the Exception
//   */
//  protected void deleteRecentItems(ActionContext context, Connection db, Organization organization) throws SQLException {
//    //remove any recent contacts belonging to this account
//
//   
//  }
//
//  /*inserito da d.dauria */
//  public String executeCommandPrintReport(ActionContext context) {
//	    if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
//		  return ("PermissionError");
//		}
//		Connection db = null;
//		try {
//		  db = this.getConnection(context);
//		  String id = (String) context.getRequest().getParameter("id");
//		  HashMap map = new HashMap();
//		  map.put("altId", new Integer(id));
//		  map.put("path", getWebInfPath(context, "reports"));
//		  //provide the dictionary as a parameter to the quote report
//		  map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
//		  //String filename = "modB.xml";
//		  String filename = (String) context.getRequest().getParameter("file");
//		  
//		  //provide a seperate database connection for the subreports
//		  Connection scriptdb = this.getConnection(context);
//		  map.put("SCRIPT_DB_CONNECTION", scriptdb);
//
//		  //Replace the font based on the system language to support i18n chars
//		  String fontPath = getWebInfPath(context, "fonts");
//		  String reportDir = getWebInfPath(context, "reports");
//		  JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
//		  String language = getPref(context, "SYSTEM.LANGUAGE");
//
//		  JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);
//		  
//		  byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);
//
//		  if (bytes != null) {
//		    FileDownload fileDownload = new FileDownload();
//		    	fileDownload.setDisplayName("DettaglioOperatoreAbusivo_" + id + ".pdf");
//		    fileDownload.sendFile(context, bytes, "application/pdf");
//		  } else {
//		    return ("SystemError");
//		  }
//		} catch (Exception errorMessage) {
//		  context.getRequest().setAttribute("Error", errorMessage);
//		  return ("SystemError");
//		} finally {
//		  this.freeConnection(context, db);
//		}
//		return ("-none-");
//	  }
//	
//  /*
//  private String[] convert2Wgs84UTM33N( String latitude, String longitude, Connection conn  ) throws SQLException
//  {
//		
//		String[] ret = null;
//		String sql = 
//		"select 	X \n"	+ 
//		"( \n"				+ 
//		"	transform \n"	+ 
//		"	(  \n"			+ 
//		"		geomfromtext \n"	+ 
//		"		(  \n"				+ 
//		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
//		"	 \n"					+ 
//		"		), 32633 \n"			+ 
//		"	) \n"					+ 
//		") AS x, \n"				+ 
//		"Y \n"						+ 
//		"( \n"						+ 
//		"	transform \n"			+ 
//		"	(  \n"					+ 
//		"		geomfromtext \n"	+ 
//		"		(  \n"				+ 
//		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
//		"	 \n"			+ 
//		"		), 32633 \n"	+ 
//		"	) \n"			+ 
//		") AS y \n";
//		
//		try
//		{
//			
//			PreparedStatement stat = conn.prepareStatement( sql );
//			//stat.setString( 1, wgs84[0] );
//			//stat.setString( 2, wgs84[1] );
//			//stat.setString( 3, wgs84[0] );
//			//stat.setString( 4, wgs84[1] );
//			
//			ResultSet res = stat.executeQuery();
//			if( res.next() )
//			{
//				ret = new String[2];
//				ret[0] = res.getString( "x" );
//				ret[1] = res.getString( "y" );
//				
//			}
//			res.close();
//			stat.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return ret;
//	}
//  
//  
//  public String[] converToWgs84UTM33NInverter(String latitudine , String longitudine, Connection db) throws SQLException
//  {
//          String lat ="";
//          String lon = "";
//          String [] coord = new String [2];
//          String sql1 = 
//                  "select         X \n"        + 
//                  "( \n"                                + 
//                  "        transform \n"        + 
//                  "        (  \n"                        + 
//                  "                geomfromtext \n"        + 
//                  "                (  \n"                                + 
//                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
//                  "         \n"                                        + 
//                  "                ), 4326 \n"                        + 
//                  "        ) \n"                                        + 
//                  ") AS x, \n"                                + 
//                  "Y \n"                                                + 
//                  "( \n"                                                + 
//                  "        transform \n"                        + 
//                  "        (  \n"                                        + 
//                  "                geomfromtext \n"        + 
//                  "                (  \n"                                + 
//                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
//                  "         \n"                        + 
//                  "                ), 4326 \n"        + 
//                  "        ) \n"                        + 
//                  ") AS y \n";
//          
//        try {
//        	
//          PreparedStatement stat1 = db.prepareStatement( sql1 );
//          ResultSet res1 = stat1.executeQuery();
//          if( res1.next() )
//          {
//                  lat = res1.getString( "y") ;
//                  lon=res1.getString( "x" );
//                  coord [0] =lat;
//                  coord [1] =lon;
//          
//          }
//          res1.close();
//          stat1.close();
//          
//        }catch (Exception e){
//        	e.printStackTrace();
//        }
//         
//        return coord;
//          
//  } */
//  
//  
//  public String executeCommandCessazioneAttivita(ActionContext context) throws ParseException
//	{
//	 
//	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
//		String dataFineString = context.getParameter("dataCessazioneAttivita");
//		Timestamp dataFine = null;	
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
//		String note = context.getParameter("noteCessazione");
//		int userId = getUserId(context);
//	  
//		Connection db = null;
//		try
//		{
//			db=super.getConnection(context);
//			Organization org = new Organization(db,orgId);
//			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
//			context.getRequest().setAttribute("OrgDetails", org);
//		}
//		catch(SQLException e)
//		{
//			
//		}
//			finally{
//				super.freeConnection(context, db);
//			}
//		return "InsertOK";
//	}
	
	 public String executeCommandDetails(ActionContext context) {
		    if (!hasPermission(context, "operatoriprivati-operatoriprivati-view")) {
		      return ("PermissionError");
		    }


			    int id = -1;
			    int orgId = -1;
				int stabId = -1;
				int altId = -1;
				
				String idString = context.getRequest().getParameter("id");
				String orgIdString = context.getRequest().getParameter("altId");
				String stabIdString = context.getRequest().getParameter("stabId");
				String altIdString = context.getRequest().getParameter("altId");

				
				try {id = Integer.parseInt(idString);} catch (Exception e){}
				if (id==-1)
					idString = (String) context.getRequest().getAttribute("id");
				try {id = Integer.parseInt(idString);} catch (Exception e){}
				

				try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
				if (orgId==-1)
					orgIdString = (String) context.getRequest().getAttribute("altId");
				try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
				
				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				if (stabId==-1)
					stabIdString = (String) context.getRequest().getAttribute("stabId");
				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				
				try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
				if (altId==-1)
					altIdString = (String) context.getRequest().getAttribute("altId");
				try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
				
				if (id<0 && stabId>0)
					id=stabId;
				
			    Connection db = null;
				
				try {
					db = this.getConnection(context);
						Stabilimento stab = null;
						
						if (id>0)
							stab =	new Stabilimento(db, id);
						else if (altId>0)
							stab =	new Stabilimento(db, altId, true);
						else if (orgId>0)
							stab =	new Stabilimento(db, orgId, true);
						
						Anagrafica anag = new Anagrafica(db, stab.getAlt_id());
						context.getRequest().setAttribute("AnagraficaDetails", anag);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					this.freeConnection(context, db);
				}
		    
		     
		      return getReturn(context, "Details");
		    
		  }
	 
	 
	 public String executeCommandAdd(ActionContext context) {
			if (!hasPermission(context, "operatoriprivati-operatoriprivati-add")) {
				return ("PermissionError");
			}
			Connection db = null;
			try {
				db = this.getConnection(context);

				LookupList NazioniList = new LookupList(db,"anagrafica.lookup_nazioni");
				NazioniList.addItem(-1, "Seleziona Nazione");
				NazioniList.setRequired(true);
				context.getRequest().setAttribute("NazioniList", NazioniList);

				LookupList TipoImpresaList = new LookupList(db,"anagrafica.lookup_tipo_impresa_soc");
				context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);

				LookupList TipoCarattere = new LookupList(db,"anagrafica.lookup_tipo_carattere");
				context.getRequest().setAttribute("TipoCarattere", TipoCarattere);

				LookupList aslList = new LookupList(db, "anagrafica.lookup_asl");
				aslList.removeElementByCode(16);
				context.getRequest().setAttribute("AslList", aslList);

				LookupList listaToponimi = new LookupList();
				listaToponimi.setTable("anagrafica.lookup_toponimi");
				listaToponimi.buildList(db);
				listaToponimi.setRequired(true);
				context.getRequest().setAttribute("ToponimiList", listaToponimi);
				
				LookupList ProvinceList = new LookupList(db,"anagrafica.lookup_province");
				ProvinceList.addItem(-1, "---Seleziona---");
				context.getRequest().setAttribute("ProvinceList", ProvinceList);
				

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection(context, db);
			}

			return"AddOK";

		}
	 
	 public String executeCommandInsert(ActionContext context) {

			if (!hasPermission(context, "operatoriprivati-operatoriprivati-add")) {
				return ("PermissionError");
			}
			
			UserBean utente = (UserBean) context.getSession().getAttribute("User");
			Integer idImpresa 			= -1;
			Integer idSoggetto 			= -1;
			Integer idIndirizzoStab 	= -1;
			Integer idIndirizzoImpresa 	= -1;
			Integer idIndirizzoSoggetto = -1;
			Integer idStabilimento		= -1;
					
			Connection db = null;
			try {
				db = this.getConnection(context);
				
				//cerco l'impresa?Poi inserisco
				String ragione_sociale  = context.getParameter("nome")+ " " + context.getParameter("cognome");
				String codfisc = context.getParameter("codfisc");
				String piva = context.getParameter("piva");
				String tipo_impresa = context.getParameter("tipo_impresa");
				String pec = context.getParameter("pec");
				String note =  context.getParameter("note");
				
				/*esistenza...*/
				Impresa impresa = new Impresa(ragione_sociale, codfisc, piva, Integer.parseInt(tipo_impresa), pec, note);
				idImpresa = impresa.insert(db, utente.getUserId());
				
				//indirizzo impresa (uguale a residrenza)
				String via = context.getParameter("viaSoggetto");
				String cap = context.getParameter("capSoggetto");
				String idNazione = context.getParameter("idNazioneSoggetto");
				String latitudine = "";
				String longitudine = "";
				String idComune = context.getParameter("idComuneSoggetto");
				String comune_testo = context.getParameter("comune_testoSoggetto");
				String toponimo = context.getParameter("toponimoSoggettoId");
				String civico = context.getParameter("civicoSoggetto");
				Provincia p = new Provincia();
				Integer idProvincia = p.getId(db, context.getParameter("idProvinciaSoggetto"));
				
				Indirizzo indirizzo =  new Indirizzo(via, cap, idProvincia, Integer.parseInt(idNazione),
						latitudine,longitudine, Integer.parseInt(idComune), comune_testo, Integer.parseInt(toponimo), civico);
				idIndirizzoImpresa = indirizzo.insert(db, utente.getUserId());
				indirizzo.insertRelazioneImpresa(db, idImpresa, idIndirizzoImpresa, utente.getUserId());
					
				//soggetto fisico cerco?
				String cognome = context.getParameter("cognome");
				String nome = context.getParameter("nome");
				String nomeComuneNascita = context.getParameter("nomeComuneNascita");
				String codice_fiscale = context.getParameter("codice_fiscale");
				String sesso = context.getParameter("sesso");
				String telefono = context.getParameter("telefono");
				String documento_identita = context.getParameter("documento_identita");
				//String provenienza_estera = context.getParameter("provenienza_estera");
				String provincia_nascita = null;//context.getParameter("provincia_nascita");
				String data_nascita = context.getParameter("data_nascita");

				Integer idComuneNascita = -1;
				
				//come gestire la data di nascita?
				SoggettoFisico soggetto = new SoggettoFisico(cognome, nome, nomeComuneNascita,idComuneNascita, codice_fiscale,
						sesso, telefono, documento_identita, //provenienza_estera, 
						provincia_nascita, data_nascita);
				idSoggetto = soggetto.insert(db,utente.getUserId());
				soggetto.insertRelazioneImpresa(db, idImpresa, idSoggetto, utente.getUserId());

				String viaSoggetto = context.getParameter("viaSoggetto");
				String capSoggetto = context.getParameter("capSoggetto");
				String idNazioneSoggetto = context.getParameter("idNazioneSoggetto");
				String idComuneSoggetto = context.getParameter("idComuneSoggetto");
				String comune_testoSoggetto = context.getParameter("comune_testoSoggetto");
				String toponimoSoggetto = context.getParameter("toponimoSoggettoId");
				String civicoSoggetto = context.getParameter("civicoSoggetto");
				p = new Provincia();
				Integer idProvinciaSoggetto = p.getId(db, context.getParameter("idProvinciaSoggetto"));
				
				//indirizzoSoggetto
				Indirizzo indirizzoSoggetto =  new Indirizzo(viaSoggetto, capSoggetto, idProvinciaSoggetto, 
						Integer.parseInt(idNazioneSoggetto),null,null, Integer.parseInt(idComuneSoggetto), 
						comune_testoSoggetto, Integer.parseInt(toponimoSoggetto), civicoSoggetto);
				idIndirizzoSoggetto = indirizzoSoggetto.insert(db, utente.getUserId());
				
				if (!idComuneSoggetto.equals("-1"))
					indirizzoSoggetto.insertRelazioneSoggetto(db, idSoggetto,idIndirizzoSoggetto, utente.getUserId());
				
				//indirizzo stabilimento?
				String viaStab = context.getParameter("viaStab");
				String capStab  = context.getParameter("capStab");
				//String idProvinciaStab  = context.getParameter("idProvinciaStab ");
				String idNazioneStab  = context.getParameter("idNazioneStab");
				String latitudineStab  = context.getParameter("latitudineStab");
				String longitudineStab = context.getParameter("longitudineStab");
				String idComuneStab  = context.getParameter("idComuneStab");
				String comune_testoStab = context.getParameter("comune_testoStab");
				String toponimoStab = context.getParameter("toponimoStabId");
				String civicoStab  = context.getParameter("civicoStab");
				
				Indirizzo indirizzoStabilimento =  new Indirizzo(viaStab, capStab, -1, -1,latitudineStab,longitudineStab, 
						Integer.parseInt(idComuneStab), comune_testoStab, Integer.parseInt(toponimoStab), civicoStab);
				idIndirizzoStab = indirizzoStabilimento.insert(db, utente.getUserId());
				
				String stato = context.getParameter("stato");
				String categoria_rischio = context.getParameter("categoria_rischio");
				String data_prossimo_controllo = context.getParameter("data_prossimo_controllo");
				String data_inizio_attivita = context.getParameter("data_inizio_attivita");
				String data_fine_attivita = context.getParameter("data_fine_attivita");
				String noteStab = context.getParameter("note");
				Comuni c = new Comuni();
				String idAsl = c.getIdAsl(db, Integer.parseInt(idComuneStab));
				
				Stabilimento stabilimento = new Stabilimento(stato,categoria_rischio, data_prossimo_controllo,data_inizio_attivita, data_fine_attivita, noteStab, idAsl);
			
				//qui cosa verifichiamo?
				idStabilimento = stabilimento.insert(db, utente.getUserId());
				stabilimento.insertRelazioneImpresa(db, idImpresa, idStabilimento, utente.getUserId());
				stabilimento.insertRelazioneSoggetto(db,idStabilimento, idSoggetto, utente.getUserId());
				
				indirizzoStabilimento.insertRelazioneStabilimento(db,idStabilimento, idIndirizzoStab, utente.getUserId());
				
				stabilimento.generaNumeroRegistrazione(db,idStabilimento);

				String idLinea = context.getParameter("idLineaProduttiva");
				String dataInizio  = context.getParameter("dataInizioLinea ");
				
				Timestamp ts =  new Timestamp(System.currentTimeMillis());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
				dataInizio = format.format(ts);

				String dataFine  = context.getParameter("dataFineLinea ");
				String tipoCarattere  = context.getParameter("carattere");
				String cun = context.getParameter("cun");
				String numRegistrazione = stabilimento.getNumero_registrazione()+"001";

				//Inserisco la relazione tra stabilimento e linea
				
				Istanza ist = new Istanza(idStabilimento, idLinea, dataInizio, dataFine, tipoCarattere, cun,numRegistrazione);
				ist.setTipoAttivita(1);//FISSI
				ist.insert(db, utente.getUserId());
								
				RicercheAnagraficheTab.inserAnagrafica(db,idStabilimento);

					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				this.freeConnection(context, db);
			}
			
			context.getRequest().setAttribute("stabId", String.valueOf(idStabilimento));
			return executeCommandDetails(context);
			 
		}
	 
	 public String executeCommandModify(ActionContext context) {
		    if (!hasPermission(context, "operatoriprivati-operatoriprivati-edit")) {
		      return ("PermissionError");
		    }


			    int id = -1;
			    int orgId = -1;
				int stabId = -1;
				int altId = -1;
				
				String idString = context.getRequest().getParameter("id");
				String orgIdString = context.getRequest().getParameter("altId");
				String stabIdString = context.getRequest().getParameter("stabId");
				String altIdString = context.getRequest().getParameter("altId");

				
				try {id = Integer.parseInt(idString);} catch (Exception e){}
				if (id==-1)
					idString = (String) context.getRequest().getAttribute("id");
				try {id = Integer.parseInt(idString);} catch (Exception e){}
				

				try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
				if (orgId==-1)
					orgIdString = (String) context.getRequest().getAttribute("altId");
				try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
				
				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				if (stabId==-1)
					stabIdString = (String) context.getRequest().getAttribute("stabId");
				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				
				try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
				if (altId==-1)
					altIdString = (String) context.getRequest().getAttribute("altId");
				try {altId = Integer.parseInt(altIdString);} catch (Exception e){}
				
				if (id<0 && stabId>0)
					id=stabId;
				
			    Connection db = null;
				
				try {
					db = this.getConnection(context);
						Stabilimento stab = null;
						
						if (id>0)
							stab =	new Stabilimento(db, id);
						else if (altId>0)
							stab =	new Stabilimento(db, altId, true);
						else if (orgId>0)
							stab =	new Stabilimento(db, orgId, true);
						
						context.getRequest().setAttribute("StabilimentoDetails", stab);

						
						Anagrafica anag = new Anagrafica(db, stab.getAlt_id());
						context.getRequest().setAttribute("AnagraficaDetails", anag);
						
						LookupList StatiList = new LookupList(db,"anagrafica.lookup_stati");
						System.out.println(""+StatiList.size());
						
						/*for (int i = 0; i<StatiList.size(); i++){
							if (i!=Istanza.STATO_ATTIVO && i!=Istanza.STATO_CESSATO && i!=Istanza.STATO_SOSPESO)
								StatiList.removeElementByCode(i);
						}*/
						context.getRequest().setAttribute("StatiList", StatiList);
						
						
						
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					this.freeConnection(context, db);
				}
		    		     
		      return getReturn(context, "Modify");
		    
		  }
	 
	 public String executeCommandUpdate(ActionContext context) {
		    if (!hasPermission(context, "operatoriprivati-operatoriprivati-edit")) {
		      return ("PermissionError");
		    }
		    
			Stabilimento stab = null;

				int stabId = -1;

				String stabIdString = context.getRequest().getParameter("stabId");

				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				if (stabId==-1)
					stabIdString = (String) context.getRequest().getAttribute("stabId");
				try {stabId = Integer.parseInt(stabIdString);} catch (Exception e){}
				
				
			    Connection db = null;
				
				try {
						db = this.getConnection(context);
						
						stab =	new Stabilimento(db, stabId);
						
						
						for (int i = 0; i<stab.getLinee().size(); i++){
							
							Istanza ist = (Istanza) stab.getLinee().get(i);
							int id = ist.getId();
							int stato = Integer.parseInt(context.getRequest().getParameter("stato"+id));
							String dataInizio = context.getRequest().getParameter("dataInizio"+id);
							String dataFine = context.getRequest().getParameter("dataFine"+id);
							ist.setIdStato(stato);
							ist.setDataInizio(dataInizio);
							ist.setDataFine(dataFine);
							ist.update(db,  getUserId(context));
						}
						
						RicercheAnagraficheTab.inserAnagrafica(db, stab.getId());

						
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					this.freeConnection(context, db);
				}
		    		     
			context.getRequest().setAttribute("altId", String.valueOf(stab.getAlt_id())); 	
		   //   return executeCommandModify(context);
			   return executeCommandDetails(context);
		    
		  }
	 
	 public String executeCommandSearchForm(ActionContext context) {
		    if (!(hasPermission(context, "operatoriprivati-operatoriprivati-view"))) {
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
		      
		      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
		      PagedListInfo orgListInfo = this.getPagedListInfo(
		          context, "SearchOrgListInfo");
		      orgListInfo.setCurrentLetter("");
		      orgListInfo.setCurrentOffset(0);
		     
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
			if (!hasPermission(context, "opu-view")) {
				return ("PermissionError");
			}

			RicercaList organizationList = new RicercaList();
			//Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");

			if (context.getRequest().getRequestURI().contains("Suap"))
				searchListInfo.setLink("RicercaUnicaSuap.do?command=Search");
			else		
			{

				if (context.getParameter("tipoOperaione")!=null)
					searchListInfo.setLink("RicercaUnica.do?command=Search&tipoOperaione="+context.getParameter("tipoOperaione"));
				else
				{
					searchListInfo.setLink("RicercaUnica.do?command=Search");
				}

			}
			Connection db = null;
			try {
				db = this.getConnection(context);	      
//				organizationList.cessazioneAutomaticaAttivitaTemporane(db);
				
				searchListInfo.setSearchCriteria(organizationList, context);     
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.buildList(db);

				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.addItem(-1,  "-- SELEZIONA VOCE --");
				siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
				context.getRequest().setAttribute("SiteIdList", siteList);

				LookupList ListaStati = new LookupList(db,
						"lookup_stato_lab");
				ListaStati.addItem(-1, getSystemStatus(context).getLabel(
						"calendar.none.4dashes"));
				context.getRequest().setAttribute("ListaStati", ListaStati);

				context.getRequest().setAttribute("StabilimentiList", organizationList);

				return "ListOK";



			} catch (Exception e) {
				//Go through the SystemError process
				context.getRequest().setAttribute("Error", e);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}



		}
		    
}		   




  


