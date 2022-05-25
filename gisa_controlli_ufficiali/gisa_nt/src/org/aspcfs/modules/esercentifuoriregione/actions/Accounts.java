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
package org.aspcfs.modules.esercentifuoriregione.actions;

import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.esercentifuoriregione.base.Organization;
import org.aspcfs.modules.esercentifuoriregione.base.OrganizationList;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

  

  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "esercentifuoriregione-esercentifuoriregione-view"))) {
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
      
      //tabella modificata
//      LookupList stageList = new LookupList(db, "lookup_esercentifuoriregione_stage");
//      stageList.addItem(-1, systemStatus.getLabel("esercentifuoriregione.any"));
//      context.getRequest().setAttribute("StageList", stageList);

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
      buildFormElements(context, db);
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
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      

      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      
//      LookupList stageList = new LookupList(db, "lookup_esercentifuoriregione_stage");
//      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("StageList", stageList);

      Organization newOrg = (Organization) context.getFormBean();
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
      buildFormElements(context, db);
      if (newOrg.getEnteredBy() != -1) {
//        newOrg.setTypeListToTypes(db);
        context.getRequest().setAttribute("OrgDetails", newOrg);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
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


  /**
   *  Description of the Method
   *
   * @param  context        Description of the Parameter
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildFormElements(ActionContext context, Connection db) throws SQLException {
    String index = null;
    if (context.getRequest().getParameter("index") != null) {
      index = context.getRequest().getParameter("index");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);

    
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
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-view")) {
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
      
      
      //Dopo l'inserimento riconverti
      Iterator it_coords = newOrg.getAddressList().iterator();
      while(it_coords.hasNext()){
    	  
    	  org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress thisAddress = (org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress) it_coords.next();
    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
    		  String spatial_coords [] = null;
    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//      		  {
//      			 AjaxCalls ajaxCall = new AjaxCalls();
//      			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//      			thisAddress.setLatitude(coordinate[1]);
//      			thisAddress.setLongitude(coordinate[0]);
//      		  }
//      		  else
//      		  {
      			  thisAddress.setLatitude(spatial_coords[0]);
        		  thisAddress.setLongitude(spatial_coords[1]);
      		  //}

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
      
      
      
//      LookupList stageList = new LookupList(db, "lookup_esercentifuoriregione_stage");
//      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("StageList", stageList);
      
      buildFormElements(context, db);
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
    if (!hasPermission(context, "esercentifuoriregione-dashboard-view")) {
      if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-view")) {
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
          "Accounts", "org.aspcfs.modules.esercentifuoriregione.base.AccountsListScheduledActions", "Accounts");
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
  
  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-view")) {
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
    searchListInfo.setLink("EsercentiFuoriRegione.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        organizationList.setOrgSiteId(this.getUserSiteId(context));
        source = "searchForm";
      }
      organizationList.setTipologia(29);
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
      
//      LookupList stageList = new LookupList(db, "lookup_esercentifuoriregione_stage");
//      stageList.addItem(-1, systemStatus.getLabel("esercentifuoriregione.any"));
//      context.getRequest().setAttribute("StageList", stageList);

    //inserito da Carmela
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("EsercentiFuoriRegione.do?command=Search");
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
          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
        }
        organizationList.buildList(db);
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
        if (organizationList.size() == 1 && organizationList.getAssetSerialNumber() != null) {
      
       
        }
        return "ListOK";
      } else {}
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "" ;
  }

  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    Organization insertedOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg = (Organization) context.getFormBean();
   
    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
   
    try {
      db = this.getConnection(context);

     
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
  
      //set the name to namelastfirstmiddle if individual
//      if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
//          "individual")) {
//        newOrg.setIsIndividual(true);
//        newOrg.setName(newOrg.getNameLastFirstMiddle());
//        newOrg.populatePrimaryContact();
//        newOrg.getPrimaryContact().setRequestItems(context);
//        //set the access type for the contact to the default permission for Account Contacts (public)
//        AccessTypeList accessTypes = this.getSystemStatus(context).getAccessTypeList(
//            db, AccessType.ABUSIVISMI);
//        newOrg.getPrimaryContact().setAccessType(accessTypes.getDefaultItem());
//      } else {
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
        
        }
      }

      isValid = this.validateObject(context, db, newOrg);
      
      
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress thisAddress = (org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress) it.next(); 
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
    } catch (Exception errorMessage) {
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
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "esercentifuoriregione-esercentifuoriregione-edit"))) {
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
      if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
          "individual")) {
        newOrg.setIsIndividual(true);
        newOrg.populatePrimaryContact(db);
        
      } else {
        //don't want to populate the addresses, etc. if this is an individual account
        newOrg.setIsIndividual(false);
        newOrg.setRequestItems(context);
        newOrg.populatePrimaryContact(db);
       
      }
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress thisAddress = (org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      if (isValid) {
        resultCount = newOrg.update(db,context);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
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
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-delete")) {
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
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          recordDeleted = thisOrganization.disable(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        
        context.getRequest().setAttribute(
            "refreshUrl", "EsercentiFuoriRegione.do?command=Search");
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
      
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.accountDeletion"));
      context.getRequest().setAttribute(
          "refreshUrl", "EsercentiFuoriRegione.do?command=Search");
      return ("DeleteError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-delete")) {
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
     
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      oldOrganization = new Organization(db, Integer.parseInt(orgId));
      
      // NOTE: these may have different options later
      recordUpdated = thisOrganization.updateStatus(
          db, context, true, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisOrganization.getOwner());
      if (recordUpdated) {
        
     
      }
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "EsercentiFuoriRegione.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "EsercentiFuoriRegione.do?command=Search");
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
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-delete")) {
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
        if (!AccountsUtil.isCancellabile(db, thisOrg.getOrgId())){
      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
            htmlDialog.addMessage("<br/>");
            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
        }
        else {
        htmlDialog.addMessage("<form action=\"TrasportoAnimali.do?command=Trash&auto-populate=true\" method=\"post\">");
        htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
        htmlDialog.addMessage("<br/>");
        htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
        htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
        htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+thisOrg.getOrgId()+"\"/>");
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
    if (!hasPermission(context, "esercentifuoriregione-esercentifuoriregione-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      newOrg = (Organization) context.getFormBean();
      if (newOrg.getId() == -1) {
        newOrg = new Organization(db, tempid);
        
        
        //In fase di modifica
        Iterator it_coords = newOrg.getAddressList().iterator();
        while(it_coords.hasNext()){
      	  org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress thisAddress = (org.aspcfs.modules.esercentifuoriregione.base.OrganizationAddress) it_coords.next();
      	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
      		  String spatial_coords [] = null;
      		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//      		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//    		  {
//    			 AjaxCalls ajaxCall = new AjaxCalls();
//    			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//    			thisAddress.setLatitude(coordinate[1]);
//    		  thisAddress.setLongitude(coordinate[0]);
//    		  }
//    		  else
//    		  {
    			  thisAddress.setLatitude(spatial_coords[0]);
      		  thisAddress.setLongitude(spatial_coords[1]);
    		  //}

      	  }
      	  
        }
        
      
      } else {
//        newOrg.setTypeListToTypes(db);
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

//      LookupList accountSizeList = new LookupList(db, "lookup_esercentifuoriregione_size");
//      accountSizeList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("AccountSizeList", accountSizeList);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
//      LookupList stageList = new LookupList(db, "lookup_esercentifuoriregione_stage");
//      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
//      context.getRequest().setAttribute("StageList", stageList);



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



  /*
  private String[] convert2Wgs84UTM33N( String latitude, String longitude, Connection conn  ) throws SQLException
  {
		
		String[] ret = null;
		String sql = 
		"select 	X \n"	+ 
		"( \n"				+ 
		"	transform \n"	+ 
		"	(  \n"			+ 
		"		geomfromtext \n"	+ 
		"		(  \n"				+ 
		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
		"	 \n"					+ 
		"		), 32633 \n"			+ 
		"	) \n"					+ 
		") AS x, \n"				+ 
		"Y \n"						+ 
		"( \n"						+ 
		"	transform \n"			+ 
		"	(  \n"					+ 
		"		geomfromtext \n"	+ 
		"		(  \n"				+ 
		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
		"	 \n"			+ 
		"		), 32633 \n"	+ 
		"	) \n"			+ 
		") AS y \n";
		
		try
		{
			
			PreparedStatement stat = conn.prepareStatement( sql );
			//stat.setString( 1, wgs84[0] );
			//stat.setString( 2, wgs84[1] );
			//stat.setString( 3, wgs84[0] );
			//stat.setString( 4, wgs84[1] );
			
			ResultSet res = stat.executeQuery();
			if( res.next() )
			{
				ret = new String[2];
				ret[0] = res.getString( "x" );
				ret[1] = res.getString( "y" );
				
			}
			res.close();
			stat.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
  
  
  public String[] converToWgs84UTM33NInverter(String latitudine , String longitudine, Connection db) throws SQLException
  {
          String lat ="";
          String lon = "";
          String [] coord = new String [2];
          String sql1 = 
                  "select         X \n"        + 
                  "( \n"                                + 
                  "        transform \n"        + 
                  "        (  \n"                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
                  "         \n"                                        + 
                  "                ), 4326 \n"                        + 
                  "        ) \n"                                        + 
                  ") AS x, \n"                                + 
                  "Y \n"                                                + 
                  "( \n"                                                + 
                  "        transform \n"                        + 
                  "        (  \n"                                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
                  "         \n"                        + 
                  "                ), 4326 \n"        + 
                  "        ) \n"                        + 
                  ") AS y \n";
          
        try {
        	
          PreparedStatement stat1 = db.prepareStatement( sql1 );
          ResultSet res1 = stat1.executeQuery();
          if( res1.next() )
          {
                  lat = res1.getString( "y") ;
                  lon=res1.getString( "x" );
                  coord [0] =lat;
                  coord [1] =lon;
          
          }
          res1.close();
          stat1.close();
          
        }catch (Exception e){
        	e.printStackTrace();
        }
         
        return coord;
          
  } */
  
  
  
  
}




  


