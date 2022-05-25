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
package org.aspcfs.modules.laboratorihaccp.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

import org.aspcfs.modules.laboratorihaccp.base.Organization;
import org.aspcfs.modules.laboratorihaccp.base.OrganizationList;
import org.aspcfs.modules.laboratorihaccp.base.Prova;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.AddressList;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.contacts.base.ContactType;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListElement;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.worksheet.WorksheetColumn;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;


/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class LaboratoriHACCP extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");
	
  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

	 		  return executeCommandDashboard(context);
  }
  
  
  public String executeCommandUpdateList(ActionContext context) {
	    if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
	      return ("PermissionError");
	    }
	    String tblName = context.getRequest().getParameter("tableName");
	  
	    String[] params = context.getRequest().getParameterValues("selectedList");
	    String[] names = new String[params.length];
	    int j = 0;
	    StringTokenizer st = new StringTokenizer(
	        context.getRequest().getParameter("selectNames"), "^");
	    while (st.hasMoreTokens()) {
	      names[j] = (String) st.nextToken();
	      j++;
	    }
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      //begin for all lookup lists
	      LookupList compareList = new LookupList(db, tblName);
	      LookupList newList = new LookupList(params, names);
	      Iterator i = compareList.iterator();
	      while (i.hasNext()) {
	        LookupElement thisElement = (LookupElement) i.next();
	        //still there, stay enabled, don't re-insert it
	        if (System.getProperty("DEBUG") != null) {
	        
	        }
	        //not there, disable it, leave it
	        if (newList.getSelectedValue(thisElement.getCode()).equals("") ||
	            newList.getSelectedValue(thisElement.getCode()) == null) {
	          thisElement.disableElement(db, tblName);
	        }
	      }
	      Iterator k = newList.iterator();
	      while (k.hasNext()) {
	        LookupElement thisElement = (LookupElement) k.next();
	        if (thisElement.getCode() == 0) {
	          int thisCode = -1;
	          if ((thisCode = thisElement.isDisabled(db, tblName)) != -1) {
	            thisElement.setCode(thisCode);
	            thisElement.enableElement(db, tblName);
	            thisElement.setNewOrder(db, tblName);
	          } else {
	            thisElement.insertElement(db, tblName);
	          }
	        } else {
	          thisElement.setNewOrder(db, tblName);
	          if (!thisElement.getDescription().equals(
	              compareList.getValueFromId(thisElement.getCode()))) {
	            thisElement.setNewDescription(db, tblName);
	          }
	        }
	      }
	      //invalidate the cache for this list
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      systemStatus.removeLookup(tblName);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute(
	        "moduleId", context.getRequest().getParameter("module"));
	    addModuleBean(context, "Configuration", "Configuration");
	    return (executeCommandSearch(context));
	  }

  
  /**
   * Action that prepares the selected lookup list for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyList(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int moduleId = -1;
    int lookupId = -1;
    LookupList selectedList = null;
    try {
      db = this.getConnection(context);
      moduleId = Integer.parseInt(context.getRequest().getParameter("module"));
      lookupId = Integer.parseInt(
          context.getRequest().getParameter("sublist"));
      String table = "";
      if (lookupId == 1 )
      {
    	  table="lookup_matrici_labhaccp" ; 
    	  
      }
      else
      {
    	  table="lookup_denominazioni_labhaccp" ; 
      }
      
      
      LookupList thisList = new LookupList(
          db,table);
     
      selectedList = thisList;
//      SWITCH (THISLIST.GETCATEGORYID()) {
//        CASE PERMISSIONCATEGORY.PERMISSION_CAT_CONTACTS:
//          IF (LOOKUPID == PERMISSIONCATEGORY.LOOKUP_CONTACTS_TYPE) {
//            CONTEXT.GETREQUEST().SETATTRIBUTE(
//                "CATEGORY", STRING.VALUEOF(CONTACTTYPE.GENERAL));
//          }
//          BREAK;
//        CASE PERMISSIONCATEGORY.PERMISSION_CAT_ACCOUNTS:
//          IF (LOOKUPID == PERMISSIONCATEGORY.LOOKUP_ACCOUNTS_CONTACTS_TYPE) {
//            CONTEXT.GETREQUEST().SETATTRIBUTE(
//                "CATEGORY", STRING.VALUEOF(CONTACTTYPE.ACCOUNT));
//          }
//          BREAK;
//        DEFAULT:
//          BREAK;
//      }
      context.getRequest().setAttribute("moduleId", String.valueOf(moduleId));
      context.getRequest().setAttribute("SelectedList", selectedList);
//      context.getRequest().setAttribute("SubTitle", thisList.getDescription());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (selectedList != null) {
      selectedList.setSelectSize(8);
      selectedList.setMultiple(true);
    }
    return ("ModifyListOK");
  }

  
  private String validateColumn(WorksheetColumn colonna, String changedValue) {
	  	String nomeColonna=colonna.getProperty();
		String errore="";
		String changedValue1 = colonna.getChangedValue();    

			try
			{
				Integer.parseInt(changedValue1);
			
			}catch(Exception e)
			{
				colonna.setError("Tipo Dato Non Valido"); 
				errore="Inserire un numero";
			}
			if(errore.equals(""))
				colonna.removeError();
	return errore;
	

}
   
  

  /**
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "laboratori-laboratori-reports-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "account-reports"));
      String filePath1 = this.getPath(context, "account-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath1);
      }
      String filePath2 = this.getPath(context, "account-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      java.io.File fileToDelete2 = new java.io.File(filePath2);
      if (!fileToDelete2.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath2);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }


  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
 
  
  public String executeCommandSearchForm(ActionContext context) {
	    if (!(hasPermission(context, "laboratori-laboratori-view"))) {
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
	      siteList.addItem(-1, "--Tutti--");
	      //siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      String tipoRicerca =  context.getRequest().getParameter("tipoRicerca");
	      if(tipoRicerca!=null)
	      context.getRequest().setAttribute("tipoRicerca", tipoRicerca);
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      denom.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	      
	      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	      ente.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ente", ente);
	       
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
	      Organization newOrg = (Organization) context.getFormBean();
	      //ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      
	      if (newOrg==null)
	      {
	    	  newOrg = new Organization();
	      }
	    	  newOrg.setComuni2(db);
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
	    if (!hasPermission(context, "laboratori-laboratori-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.removeElementByLevel(14);
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList stageList = new LookupList(db, "lookup_account_stage");
	      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("StageList", stageList);
	      
	      Organization newOrg = (Organization) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      if(newOrg == null)
	      {
	    	  newOrg = new Organization();
	      }
	      newOrg.setComuni2(db);
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
  
  
 



  public String executeCommandDetails(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-view")) {
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
	      //Caricamento Diffide
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	      
	      
	      String tipoRicerca =  context.getRequest().getParameter("tipoRicerca");
	      if(tipoRicerca!=null)
	      context.getRequest().setAttribute("tipoRicerca", tipoRicerca);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.removeElementByLevel(14);
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      denom.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	      
	      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	      ente.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ente", ente);
	       
	           
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if(context.getParameter("popup")!=null)
	    {
	    	 context.getRequest().setAttribute("OrgDetails", newOrg);
	    	return ("DetailsPopUpOK");
	    }
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
	    if (!hasPermission(context, "laboratori-laboratori-view")) {
	      if (!hasPermission(context, "laboratori-laboratori-view")) {
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
	    if (!hasPermission(context, "laboratori-laboratori-view")) {
	      return ("PermissionError");
	    }

	    String tipoRicerca =  context.getRequest().getParameter("tipoRicerca");
	    if(tipoRicerca==null){
	    	tipoRicerca =  (String) context.getRequest().getAttribute("tipoRicerca");
	    }
	    context.getRequest().setAttribute("tipoRicerca", tipoRicerca);
	    
	    String source = (String) context.getRequest().getParameter("source");
	    OrganizationList organizationList = new OrganizationList();
	    
	    String codiceMatrice = (String) context.getRequest().getParameter("searchcodiceMatrice");
	    String codiceDenominazione = (String) context.getRequest().getParameter("searchcodiceDenominazione");
	    String codiceEnte = (String) context.getRequest().getParameter("searchcodiceEnte");
	    
	    if(codiceMatrice!=null && !codiceMatrice.equals("-1")){
	    	organizationList.setCodiceMatrice(codiceMatrice);
	    }
	    
	    if(codiceDenominazione!=null && !codiceDenominazione.equals("-1")){
	    	organizationList.setCodiceDenominazione(codiceDenominazione);
	    }
	    
	    if(codiceEnte!=null && !codiceEnte.equals("-1")){
	    	organizationList.setCodiceEnte(codiceEnte);
	    }
	    
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfo");
	    searchListInfo.setLink("LaboratoriHACCP.do?command=Search&tipoRicerca="+tipoRicerca);
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      String addressType = context.getRequest().getParameter("searchcodeAddressType");
	      if(addressType!=null && !addressType.equals("")){
	    	    organizationList.setAddressType(addressType);
	    	  }
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      denom.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	      
	      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	      ente.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ente", ente);
	       
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      Organization newOrg = new Organization();
	      newOrg.setComuni2(db);
	            
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
	          searchListInfo.setLink("LaboratoriHACCP.do?command=Search");
	        }
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        organizationList.setMinerOnly(false);
	        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
	        organizationList.setAccountName(context.getRequest().getParameter("searchAccountName"));
	        organizationList.setCitta(context.getRequest().getParameter("searchAccountName2"));
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
	        if(tipoRicerca.equals("prova")){
	        	organizationList.buildListProve(db,tipoRicerca);
	        }
	        else{
	        	organizationList.buildList(db);
	        }
	        
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	         if(!tipoRicerca.equals("prova")){
		      	return "ListOK";
		      }
		      else{
		      	
		      	return "ListProveOK";
		      }
	      } else {}
	    } catch (Exception e) {
	        //Go through the SystemError process
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }return "" ;
	      
	      
	   
  }
   
  public String executeCommandInsert(ActionContext context) throws SQLException {
	    if (!hasPermission(context, "laboratori-laboratori-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Organization insertedOrg = null;

	    
	    Organization newOrg = (Organization) context.getFormBean();
	   
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setName(context.getRequest().getParameter("name"));
	    //newOrg.setSiteId(context.getRequest().getParameter("siteId"));
	    newOrg.setAccountNumber(context.getRequest().getParameter("accountNumber"));
	    newOrg.setPartitaIva(context.getRequest().getParameter("partita_iva"));
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newOrg.setIp_entered(ip);
	    newOrg.setIp_modified(ip);
	    
		
	     try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      newOrg.setComuni2(db);
	      newOrg.setRequestItems(context);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      /*if (this.getUserSiteId(context) != -1) {
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
	      }*/
	      
	      newOrg.setEntered(new Timestamp (new Date(System.currentTimeMillis()).getTime()));

	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	      
	      newOrg.setSiteId(context.getRequest().getParameter("asl"));
	      newOrg.setEnteredBy(user.getUserId());
	      newOrg.setModifiedBy(user.getUserId());

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Organization(db, newOrg.getOrgId());
	        context.getRequest().setAttribute("OrgDetails", insertedOrg);
	        
	        addRecentItem(context, newOrg);
	         
	          
	      }
	    } catch (Exception errorMessage) {
	      String forward = "";
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Laboratorio Insert ok");
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

  
  
  public String executeCommandUpdate(ActionContext context) {
	    if (!(hasPermission(context, "laboratori-laboratori-edit"))) {
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
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	        
	      oldOrg = new Organization(db, newOrg.getOrgId());
	      
	      //Inserisco la riga precedente nella tabella dello storico verificando che non sia gia' presente
	      int selectedRow = oldOrg.selectLab(db, oldOrg.getId());
	      
	      if(selectedRow == 0)	{
	    	  oldOrg.insertStorico(db,context);
	      }
	      
	      newOrg.setRequestItems(context);
	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	      newOrg.setModifiedBy(user.getUserId());
	      newOrg.setPartitaIva((context.getRequest().getParameter("partita_iva")));
	      newOrg.setSiteId(context.getRequest().getParameter("asl"));
	      	      
	      int result = this.compare_and_updateStorico(oldOrg,newOrg, db);
	      
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
  
  
  public String executeCommandDelete(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-delete")) {
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
	            "refreshUrl", "Farmacosorveglianza.do?command=Search");
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
	      //System.out.println(errorMessage);
	      context.getRequest().setAttribute(
	          "actionError", systemStatus.getLabel(
	          "object.validation.actionError.accountDeletion"));
	      context.getRequest().setAttribute(
	          "refreshUrl", "Farmacosorveglianza.do?command=Search");
	      return ("DeleteError");
	    }
	  }

  public String executeCommandTrash(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-delete")) {
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
	              "refreshUrl", "LaboratoriHACCP.do?command=SearchForm");
	          context.getRequest().setAttribute("Error", e);
	          return ("SystemError");
	        } finally {
	          this.freeConnection(context, db);
	        }
	        addModuleBean(context, "Accounts", "Delete Account");
	        if (recordUpdated) {
	        	context.getRequest().setAttribute(
	      	          "refreshUrl", "LaboratoriHACCP.do?command=SearchForm");
	      	      if ("list".equals(context.getRequest().getParameter("return"))) {
	      	        return executeCommandSearch(context);
	      	      }
	      	      return "DeleteOK";
	      	    } else {
	      	      return (executeCommandSearch(context));
	      	    }
	      	  }
	     
  
  public String executeCommandRestore(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-delete")) {
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
	          "refreshUrl", "LaboratoriHACCP.do?command=Search");
	      if ("list".equals(context.getRequest().getParameter("return"))) {
	        return executeCommandSearch(context);
	      }
	      return this.executeCommandDetails(context);
	    } else {
	      return (executeCommandSearch(context));
	    }
	  }
  
  public String executeCommandEnable(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-edit")) {
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

  
  public String executeCommandConfirmDelete(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-delete")) {
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
	        if (!AccountsUtil.isCancellabile(db, orgId)){
	      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
	            htmlDialog.addMessage("<br/>");
	            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
	        }
	        else {
	        htmlDialog.addMessage("<form action=\"LaboratoriHACCP.do?command=Trash&auto-populate=true\" method=\"post\">");
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
  
  public String executeCommandDeleteProve(ActionContext context)
	 {
		 
		 String id = context.getRequest().getParameter("id");
		 if (!hasPermission(context, "laboratori-laboratori-prove-delete")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Prova c = new Prova();
			c.delete(db, Integer.parseInt(id));
			String orgId = context.getRequest().getParameter("orgId");
			Organization thisLab = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisLab);
			
				
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		 return ("DeleteProveOK");
	 }

  
  public String executeCommandModify(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-edit")) {
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
	        	  
	        	  org.aspcfs.modules.laboratorihaccp.base.OrganizationAddress thisAddress = (org.aspcfs.modules.laboratorihaccp.base.OrganizationAddress) it_coords.next();
	        	 
	        }
	      
	      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
	        return ("PermissionError");
	      }
	      newOrg.setComuni2(db);
		   
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("systemStatus", systemStatus);
	      
	      //if this is an individual account
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.removeElementByLevel(14);
	      context.getRequest().setAttribute("SiteList", siteList);
	      
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
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandOwnerJSList(ActionContext context) {

    int siteId = -1;
    String siteIdString = context.getRequest().getParameter("siteId");
    if ((siteIdString != null) || !"".equals(siteIdString)) {
      siteId = Integer.parseInt(siteIdString);
    }
    if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
      return ("PermissionError");
    }
    UserList userList = new UserList();
    HtmlSelect userListSelect = new HtmlSelect();
    try {
      userList = filterOwnerListForSite(context, siteId);
      userListSelect = userList.getHtmlSelectObj("userId", this.getUserId(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    }
    context.getRequest().setAttribute("UserListSelect", userListSelect);
    return ("OwnerJsListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context        Description of the Parameter
   * @param  siteId         Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  private UserList filterOwnerListForSite(ActionContext context, int siteId) throws Exception {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    //this is how we get the multiple-level heirarchy...recursive function.
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);

    // filter possible owners for accounts with site ids.

    // An account WITH a site id can be owned by a user with the same
    // site id or by one who has access to all sites
    // (i.e., siteId = -1 or site_id IS NULL)
    if (siteId != -1) {
      Iterator itr = userList.iterator();
      while (itr.hasNext()) {
        User tmpUser = (User) itr.next();
        if (tmpUser.getSiteId() == -1) {
          continue;
        }
        if (siteId != tmpUser.getSiteId()) {
          itr.remove();
        }
      }
    }

    // An account WITHOUT a site id can ONLY be owned by a user with access
    // to all sites
    // (i.e., siteId = -1 or site_id IS NULL)
    if (siteId == -1) {
      Iterator itr = userList.iterator();
      while (itr.hasNext()) {
        User tmpUser = (User) itr.next();
        if (siteId != tmpUser.getSiteId()) {
          itr.remove();
        }
      }
    }
    return userList;
  }
  
  
  public String executeCommandStates(ActionContext context) {
    String country = context.getRequest().getParameter("country");
    String form = context.getRequest().getParameter("form");
    String obj = context.getRequest().getParameter("obj");
    String stateObj = context.getRequest().getParameter("stateObj");
    String defaultValue = context.getRequest().getParameter("selected");
    SystemStatus systemStatus = this.getSystemStatus(context);
    StateSelect stateSelect = new StateSelect(systemStatus, country);
    context.getRequest().setAttribute("stateSelect", stateSelect.getHtmlSelectObj(country));
    context.getRequest().setAttribute("form", form);
    context.getRequest().setAttribute("obj", obj);
    context.getRequest().setAttribute("stateObj", stateObj);
    if (defaultValue != null) {
      context.getRequest().setAttribute("selected", defaultValue);
    }
    return "StatesOK";
  }
	
  private int compare_and_updateStorico(Organization oldOrg, Organization newOrg, Connection db) throws SQLException {
		
	  String name = oldOrg.getName();
	  String account_number = oldOrg.getAccountNumber();
	  String cognome_rappresentante = oldOrg.getCognomeRappresentante();
	  String stato = oldOrg.getStato();
	  Timestamp dataCambioStato = oldOrg.getDataCambioStato();
	  Date date = null ;
	  if(dataCambioStato!=null)
		   date = new Date(dataCambioStato.getTime());  
	  Date dateN = new Date(newOrg.getDataCambioStato().getTime());  
	  
	  //Verifica anche gli indirizzi oldOrg.getAddressList()
	  PreparedStatement pst = null;
	  StringBuffer sql = new StringBuffer();
	  AddressList listAddressOld = oldOrg.getAddressList();
	  AddressList listAddressNew = newOrg.getAddressList();
	   
	  Vector <Object> campi =  new Vector<Object>();
	  int numCampiAggiornati =0;
	    //Richiamo l'insert
		  sql.append("INSERT INTO laboratorihaccp_storico_elenco_lab ( ");
		  if(name!= null && !name.equals(newOrg.getName())){
			  ++numCampiAggiornati;
			  sql.append("name, ");
			  campi.add(newOrg.getName());
		  }
		  if(account_number!= null && !account_number.equals(newOrg.getAccountNumber())){
			  ++numCampiAggiornati;
			  sql.append("account_number, ");
			  campi.add(newOrg.getAccountNumber());
			  
		  }
		  if(cognome_rappresentante!=null && !cognome_rappresentante.equals(newOrg.getCognomeRappresentante())){
			  ++numCampiAggiornati;
			  sql.append("cognome_rappresentante, ");
			  campi.add(newOrg.getCognomeRappresentante());
		  }
		  if(stato!=null && !stato.equals(newOrg.getStato())){
			  ++numCampiAggiornati;
			  sql.append("stato, ");
			  campi.add(newOrg.getStato());
		  }
		  if(date!=null && !date.equals(dateN)){
			  ++numCampiAggiornati;
			  sql.append("data_cambio_stato, ");
			  campi.add(newOrg.getDataCambioStato());
		  }
		 
		  int i = 0;
		  if(numCampiAggiornati != 0){
		      sql.append("modified, modifiedby, enteredby, org_id, lab_originario, tipologia ) VALUES (?, ?, ?, ? , ?, ? " );
		      for(int j=0; j<campi.size(); j++){
		    	  sql.append(", ?");
		      }
		      sql.append(" )");
		      
		      //+ 
			  pst = db.prepareStatement(sql.toString());
			  String listaC = "";
			  for (int k=0; k<numCampiAggiornati; k++){
				  listaC = listaC + "-" + campi.get(k);
				  pst.setObject(++i, campi.get(k));
			  }
			  //newOrg.setCampiModificati(listaC);
			  pst.setTimestamp(++i, newOrg.getModified());  
			  pst.setInt(++i, newOrg.getModifiedBy());
			  pst.setInt(++i, newOrg.getEnteredBy());
			  pst.setInt(++i,newOrg.getOrgId());
			  pst.setBoolean(++i, false);
			  pst.setInt(++i, 152);
			  pst.execute();
			 
		  }
	  //}
	 
	  
	  return numCampiAggiornati;
  }
  public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "laboratorihaccp-laboratorihaccp-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"LabHaccpTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink("LaboratoriHACCP.do?command=ViewVigilanza&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {

			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db,
					"lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db,
					"lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(passedId);
			/*
			 * int punteggioAccumulato =
			 * controlliList.buildListControlliUltimiAnni(db, passedId);
			 * context.getRequest().setAttribute("punteggioUltimiAnni",
			 * punteggioAccumulato);
			 */
			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			// find record permissions for portal users
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
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}

  
  
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

  
  
  
