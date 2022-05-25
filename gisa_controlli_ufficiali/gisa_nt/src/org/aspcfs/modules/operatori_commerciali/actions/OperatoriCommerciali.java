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
package org.aspcfs.modules.operatori_commerciali.actions;
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
import java.util.List;

import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.operatori_commerciali.base.Organization;
import org.aspcfs.modules.operatori_commerciali.base.OrganizationEmailAddress;
import org.aspcfs.modules.operatori_commerciali.base.OrganizationList;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.GestoreComunicazioniVam;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.cessazionevariazione.base.Ticket;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DbiBdu;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class OperatoriCommerciali extends CFSModule {

	
	
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
	    if (!(hasPermission(context, "operatori-commerciali-view"))) {
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
	      //siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	   
	      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
	      PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
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
	      
	      //temp
	    /* 
	      if (orgListInfo.getSearchOptionValue("searchcodeAutorizzato")!= null ){
	    	  
	    	  StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAutorizzato"));
	      }*/
	      //fine temp
	 //     context.getRequest().setAttribute("searchcodeAutorizzato", orgListInfo.getSearchOptionValue("searchcodeAutorizzato"));
	      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	      
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, thisUser.getSiteId());
	       
	       LookupList comuniList = new LookupList(listaComuni);
	      
	       comuniList.addItem(-1, "");
	       context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      if (thisUser.getRoleId()==21){
	    	  context.getRequest().setAttribute("UserComune", thisUser);
	      }
	      if (thisUser.getRoleId()==23){
	    	  context.getRequest().setAttribute("UserProvincia", thisUser);
	      }
	      if (thisUser.getRoleId()==29){
	    	  context.getRequest().setAttribute("UserASL", thisUser);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Operatori Commerciali", "Operatori Commerciali Search");
	    return ("SearchOK");
	  }


  List<String> uniquePropertyValues=null;
	int k=-1;

	Integer[] array=null;
  Connection db = null;
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
	  if (!hasPermission(context, "accounts-dashboard-view")) {
	      if (!hasPermission(context, "operatori-commerciali-view")) {
	        return ("PermissionError");
	      }
	      
	    
	      
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));
	    }
    
    addModuleBean(context, "Dashboard", "DashboardOperatoriCommerciali");
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


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "operatori-commerciali-view")) {
      return ("PermissionError");
    }
    
	  UserBean user = (UserBean) context.getSession().getAttribute("User");
	 

	  addModuleBean(context, "SearchOperatoriCommerciali", "SearchOperatoriCommerciali");
    String source = (String) context.getRequest().getParameter("source");
    String partitaIva = (String) context.getRequest().getParameter("searchPartitaIva");
    String codiceF = (String) context.getRequest().getParameter("searchCodiceFiscale");
    String stato = (String) context.getRequest().getParameter("searchCessato");
    String cognomeR = (String) context.getRequest().getParameter("searchCognomeRappresentante");
    String nomeR = (String) context.getRequest().getParameter("searchNomeRappresentante");
    String codIstat = (String) context.getRequest().getParameter("searchCodiceFiscaleCorrentista");
    String categoriaR = (String) context.getRequest().getParameter("searchcodecategoriaRischio");
    String codiceAllerta = context.getRequest().getParameter("searchcodiceAllerta");
    String tipoDest = context.getRequest().getParameter("searchTipoDest");
    String addressType = context.getRequest().getParameter("searchcodeAddressType");
    String statoCu = context.getRequest().getParameter("searchstatoCu");
    OrganizationList organizationList = new OrganizationList();
    organizationList.setTipologia(20);
    if(addressType!=null && !addressType.equals("")){
    organizationList.setTipoDest(tipoDest);
    organizationList.setAddressType(addressType);
  }
    if(categoriaR!=null)
    organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));
    if(!"".equals(codIstat) && codIstat!=null) {
    	organizationList.setCodiceFiscaleCorrentista(codIstat);
    	 }
//    if(!"".equals(partitaIva) && partitaIva!=null) {
//    	organizationList.setPartitaIva(partitaIva);
//    	 }
    if(!"".equals(codiceF) && codiceF != null) {
    	organizationList.setCodiceFiscale(codiceF);
    	 }
    if(!"".equals(cognomeR) && cognomeR != null) {
    	organizationList.setCognomeRappresentante(cognomeR);
    	 }
    if(!"".equals(nomeR) && nomeR != null) {
    	organizationList.setNomeRappresentante(nomeR);
    	 }
    if(!"".equals(stato) && stato!=null && !stato.equals("-1")) {
    	organizationList.setCessato(stato);
    	 }
    if(!"".equals(statoCu) && statoCu!=null && !statoCu.equals("-1")) {
    	organizationList.setStatoCu(statoCu);
    	 }
    if(categoriaR!=null)
    organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));
    
    if(!"".equals(codiceAllerta) && codiceAllerta != null)
    	organizationList.setCodiceAllerta(codiceAllerta);
   
    
    addModuleBean(context, "View Operatori Commerciali", "Search Resaults Operatori Commerciali");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("OperatoriCommerciali.do?command=Search");
    searchListInfo.setListView("all");
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
     /* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
    //  audit.buildList(db);
      context.getRequest().setAttribute("AuditList",audit );
      */
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
     ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
      
      LookupList comuniList = new LookupList(listaComuni);
     
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
      /*String comune = comuniList.getSelectedValue(comcod);
      System.out.print("comune==="+comune);
      organizationList.setAccountCity(comune);*/
      //context.getRequest().setAttribute("searchAccountCity", comune);
      //inserito da Carmela
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("OperatoriCommerciali.do?command=Search");
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
        //organizationList.setCessato(enabled);
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
      } else {}
    } catch (Exception e) {
    	e.printStackTrace();
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "" ;
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


  public String executeCommandDetails(ActionContext context) {
		  
	    if (!hasPermission(context, "operatori-commerciali-view")) {
	    	context.getRequest().setAttribute("dettagli_problema", "PermissionError");
	    	return "PermissionError";
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
	    	  context.getRequest().setAttribute("dettagli_problema", "PermissionError");
	    	  return "PermissionError";
	      }
	      
	      //Utilizzato per far comparire messaggio utente non abilitato
	      String disable = context.getRequest().getParameter("disable");
	      if (disable != null && disable.equals("1"))
	      {
	        context.getRequest().setAttribute("disable", disable);
	      }
	                  
	      newOrg = new Organization(db, tempid);
	      //Caricamento Diffide
	      org.aspcfs.modules.diffida.base.Ticket t = new org.aspcfs.modules.diffida.base.Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
			

	      
	      //check whether or not the owner is an active User
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      
//	      if (newOrg.getSiteId()==getUserAsl(context)){
//				context.getRequest().setAttribute("visibilita", "si");
//			}
	      
	      //PROFILO AMMINISTRATORE --> Modifica Proprietari/Detentori
//	      if ( getUserRole(context) == 1 ) {
//	    	  if ( newOrg.getTipoOrg().equals("Canile") ) {
//	    		  context.getRequest().setAttribute("visibilita", "si");
//	    	  }
//	      }
	      
	      //RUOLO VETERINARIO PRIVATO --> Visibilita' M/C
//		  long time = System.currentTimeMillis();
//		  long entered = newOrg.getEntered().getTime(); 
//		  if ( getUserRole(context) == 24 ){
//			if ( getUserId(context) == newOrg.getEnteredBy() ){
//				long ris = time - entered;
//				if ( ris > 900000 ){ //15 minuti
//					context.getRequest().setAttribute("visibilita", "no");
//				}else{
//					context.getRequest().setAttribute("visibilita", "si");
//				}
//			}else{
//				context.getRequest().setAttribute("visibilita", "no");
//			}
//		  }
	      
//		  if ( newOrg.getTipoOrg().equals("Canile") ) {
//			  context.getRequest().setAttribute("tipologiaCanile", "CANILE"); //Utilizzato per differenziare i canili dal resto dei P/D
//		  }
		  
//		  if ( newOrg.getTipoOrg().equals("Privato") ) {
//			  context.getRequest().setAttribute("tipologiaPrivato", "Privato"); //Utilizzato per la visualizzazione delle Sanzioni
//		  }
		  
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      context.getRequest().setAttribute("dettagli_problema", "PermissionError: " + e.toString());
	      return "SystemError";
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	    if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Operatori Commerciali", "Modify Operatori Commerciali Details");
	      return "DetailsOK";
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Operatori Commerciali", "View Operatori Commerciali Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "Details");
	    }
	  }
  
  
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "operatori-commerciali-vigilanza-view")) {
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
	        "OperatoriCommerciali.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	    	
	         
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      
	      LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
	      AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("AuditTipo", AuditTipo);
	      
	      LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
	      TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAudit", TipoAudit);
	      
	      LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
	      TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	      
	      
	      org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
	        controlliList.setOrgId(passedId);
	    	 /*int punteggioAccumulato = controlliList.buildListControlliUltimiAnni(db, passedId);
	         context.getRequest().setAttribute("punteggioUltimiAnni", punteggioAccumulato);
	      */
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
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
	      addModuleBean(context, "View Operatori Commerciali", "Operatori Commerciali View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      errorMessage.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "ViewVigilanza");
	  }
  
  public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "operatori-commerciali-edit")) {
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
			}
				// In fase di modifica
				Iterator it_coords = newOrg.getAddressList().iterator();
				while (it_coords.hasNext()) {

					org.aspcfs.modules.operatori_commerciali.base.OrganizationAddress thisAddress = (org.aspcfs.modules.operatori_commerciali.base.OrganizationAddress) it_coords
							.next();
					if (thisAddress.getLatitude() != 0
							&& thisAddress.getLongitude() != 0) {
						String spatial_coords[] = null;
						spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()), db);
//						if (Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
//						{
//							AjaxCalls ajaxCall = new AjaxCalls();
//							String[] coordinate = ajaxCall.getCoordinate(
//									thisAddress.getStreetAddressLine1(),
//									thisAddress.getCity(), thisAddress
//											.getState(), thisAddress.getZip(),
//									"" + thisAddress.getLatitude(), ""
//											+ thisAddress.getLongitude(), "");
//							thisAddress.setLatitude(coordinate[1]);
//							thisAddress.setLongitude(coordinate[0]);
//						} else {
							thisAddress.setLatitude(spatial_coords[0]);
							thisAddress.setLongitude(spatial_coords[1]);
//						}

					}
				}

			
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}
		
			  LookupList siteList = new LookupList(db, "lookup_site_id");
		      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteList", siteList);
		      
		      
		      LookupList tipoOperatore = new LookupList(db, "lookup_tipo_operatore_commerciale");
		      tipoOperatore.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("tipoOperatore", tipoOperatore);
		      
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);
			
			

		
			
			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());
		
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

  
  
  public String executeCommandUpdate(ActionContext context)
	throws SQLException, ParseException {
if (!(hasPermission(context, "accounts-accounts-edit"))) {
	return ("PermissionError");
}
Connection db = null;
int resultCount = -1;
boolean isValid = false;
Organization newOrg = (Organization) context.getFormBean();
Organization oldOrg = null;
SystemStatus systemStatus = this.getSystemStatus(context);
newOrg.setTypeList(context.getRequest().getParameterValues(
		"selectedList"));
newOrg.setModifiedBy(getUserId(context));
newOrg.setEnteredBy(getUserId(context));
newOrg.setVoltura(context.getRequest().getParameter("voltura"));
newOrg.setDataPresentazione(context.getRequest().getParameter(
		"dataPresentazione"));
newOrg.setCity_legale_rapp(context.getRequest().getParameter(
		"city_legale_rapp"));
newOrg.setProv_legale_rapp(context.getRequest().getParameter(
		"prov_legale_rapp"));
newOrg.setAddress_legale_rapp(context.getRequest().getParameter(
		"address_legale_rapp"));
newOrg.setDataChiusuraCommerciale(context.getRequest().getParameter(
		"dataChiusuraCommerciale"));
UserBean user = (UserBean) context.getSession().getAttribute("User");
String ip = user.getUserRecord().getIp();
newOrg.setIp_entered(ip);
newOrg.setIp_modified(ip);
try {
	db = this.getConnection(context);
	// set the name to namelastfirstmiddle if individual

	

	
		// don't want to populate the addresses, etc. if this is an
		// individual account
		newOrg.setIsIndividual(false);
		newOrg.setRequestItems(context);
	
	

	oldOrg = new Organization(db, newOrg.getOrgId());
	isValid = this.validateObject(context, db, newOrg);

	
	
	Iterator it = newOrg.getAddressList().iterator();
	while (it.hasNext()) {
		org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress) it
				.next();
		// RICHIAMO METODO PER CONVERSIONE COORDINATE
		String[] coords = null;
		if (thisAddress.getLatitude() != 0
				&& thisAddress.getLongitude() != 0) {
			coords = this.convert2Wgs84UTM33N(Double
					.toString(thisAddress.getLatitude()), Double
					.toString(thisAddress.getLongitude()), db);
			thisAddress.setLatitude(coords[1]);
			thisAddress.setLongitude(coords[0]);
		}
	}
	
	resultCount = newOrg.update(db,context);
	
	DbiBdu.aggiorna_operatore_commerciale(context, db, newOrg);
	
	
	
	
	
	
	
	newOrg.insertTipoOperatoriCommerciali(db, context.getRequest().getParameterValues("tipologiaOperatoreCommerciale"));
	
	if (context.getRequest().getParameterValues("tipologiaOperatoreCommerciale") != null)
		newOrg.insertTipoOperatoriCommerciali(db, context.getRequest().getParameterValues("tipologiaOperatoreCommerciale"));

	if (resultCount == 1) {
		processUpdateHook(context, oldOrg, newOrg);
		

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
	if (context.getRequest().getParameter("return") != null
			&& context.getRequest().getParameter("return").equals(
					"list")) {
		return (executeCommandSearch(context));
	} else if (context.getRequest().getParameter("return") != null
			&& context.getRequest().getParameter("return").equals(
					"dashboard")) {
		return (executeCommandDashboard(context));
	} else if (context.getRequest().getParameter("return") != null
			&& context.getRequest().getParameter("return").equals(
					"Calendar")) {
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
  
  /*
  private String[] converToWgs84UTM33NInverter(String latitudine,
			String longitudine, Connection db) throws SQLException {
		String lat = "";
		String lon = "";
		String[] coord = new String[2];
		String sql1 = "select         X \n" + "( \n" + "        transform \n"
				+ "        (  \n" + "                geomfromtext \n"
				+ "                (  \n" + "                        'POINT("
				+ latitudine
				+ " "
				+ longitudine
				+ ")', 32633 \n"
				+ "         \n"
				+ "                ), 4326 \n"
				+ "        ) \n"
				+ ") AS x, \n"
				+ "Y \n"
				+ "( \n"
				+ "        transform \n"
				+ "        (  \n"
				+ "                geomfromtext \n"
				+ "                (  \n"
				+ "                        'POINT("
				+ latitudine
				+ " "
				+ longitudine
				+ ")', 32633 \n"
				+ "         \n"
				+ "                ), 4326 \n"
				+ "        ) \n"
				+ ") AS y \n";

		try {

			PreparedStatement stat1 = db.prepareStatement(sql1);
			ResultSet res1 = stat1.executeQuery();
			if (res1.next()) {
				lat = res1.getString("y");
				lon = res1.getString("x");
				coord[0] = lat;
				coord[1] = lon;

			}
			res1.close();
			stat1.close();

		} catch (SQLException e) {
			throw e ;
		}

		return coord;

	} */
  
  
  public String executeCommandDelete(ActionContext context)
	throws SQLException {
if (!hasPermission(context, "accounts-accounts-delete")) {
	return ("PermissionError");
}
SystemStatus systemStatus = this.getSystemStatus(context);
Exception errorMessage = null;
boolean recordDeleted = false;
Organization thisOrganization = null;
Connection db = null;
try {
	db = this.getConnection(context);
	thisOrganization = new Organization(db, Integer.parseInt(context
			.getRequest().getParameter("orgId")));
	//check permission to record
	if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
		return ("PermissionError");
	}
	
			recordDeleted = thisOrganization.delete(db, context,
					getDbNamePath(context));

	
} catch (Exception e) {

	errorMessage = e;
} finally {
	this.freeConnection(context, db);
}
addModuleBean(context, "Accounts", "Delete Account");
if (errorMessage == null) {
	if (recordDeleted) {
		
		context.getRequest().setAttribute("refreshUrl",
				"OperatoriCommerciali.do?command=Search");
		if ("disable".equals(context.getRequest()
				.getParameter("action"))
				&& "list".equals(context.getRequest().getParameter(
						"return"))) {
			return executeCommandSearch(context);
		}
		return "DeleteOK";
	} else {
		processErrors(context, thisOrganization.getErrors());
		return (executeCommandSearch(context));
	}
} else {

	context
			.getRequest()
			.setAttribute(
					"actionError",
					systemStatus
							.getLabel("object.validation.actionError.accountDeletion"));
	context.getRequest().setAttribute("refreshUrl",
			"OperatoriCommerciali.do?command=Search");
	return ("DeleteError");
}
}

  
  public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-delete")) {
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
			if (!isRecordAccessPermitted(context, db, thisOrg.getId())) {
				return ("PermissionError");
			}
			
	
				htmlDialog.addButton("Elimina",
						"javascript:window.location.href='OperatoriCommerciali.do?command=Delete&orgId="
								+ thisOrg.getOrgId() + "&action=disable'");
			
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"),
					"javascript:parent.window.close()");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getSession().setAttribute("Dialog", htmlDialog);
		return ("ConfirmDeleteOK");
	}

  
  public String executeCommandAdd(ActionContext context) {
	    if (!hasPermission(context, "operatori-commerciali-add")) {
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
	      
	      
	      LookupList tipoOperatore = new LookupList(db, "lookup_tipo_operatore_commerciale");
	      tipoOperatore.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("tipoOperatore", tipoOperatore);
	      Organization newOrg = (Organization) context.getFormBean();
	     
	      if (newOrg.getEnteredBy() != -1) {
	        newOrg.setTypeListToTypes(db);
	        context.getRequest().setAttribute("OrgDetails", newOrg);
	      }
	      newOrg.setComuni2(db, user.getSiteId());
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
  
  
  public String executeCommandInsert(ActionContext context) throws SQLException {
	    if (!hasPermission(context, "operatori-commerciali-add")) {
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
	      
	     
	      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
	      
	       
	     
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
	      org.aspcfs.modules.requestor.base.OrganizationAddress so = null;
	      org.aspcfs.modules.requestor.base.OrganizationAddress sedeLegale = null;
	      org.aspcfs.modules.requestor.base.OrganizationAddress sedeMobile = null;
	      Iterator it = newOrg.getAddressList().iterator();
	      while(it.hasNext())
	      {
	    	  org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress) it.next();
	    	  if(thisAddress.getType()==5 || thisAddress.getType()==2)
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
	        newOrg.insertTipoOperatoriCommerciali(db, context.getRequest().getParameterValues("tipologiaOperatoreCommerciale"));
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
	    if (recordInserted) {

	    	  return ("InsertOK");
	    }
	    return (executeCommandAdd(context));
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
	  Organization org = null;
 		
 		
 		try
 		{
 			db=super.getConnection(context);
 			org = new Organization(db,orgId);
 			
			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
	
			String cessazioneBDU = GestoreComunicazioniBdu.cessazioneAutomaticaBdu(org.getOrgIdC(), dataFine, note);
			context.getRequest().setAttribute("cessazioneBDU",cessazioneBDU);

			String cessazioneVAM = GestoreComunicazioniVam.cessazioneAutomaticaVam(org.getOrgIdC(), dataFine, note);
			context.getRequest().setAttribute("cessazioneVAM",cessazioneVAM);			
 		}
 		catch(SQLException e)
 		{
 			
 		}
 			finally{
 				super.freeConnection(context, db);
 			}
 		 context.getRequest().setAttribute("orgId", String.valueOf(org.getOrgId()));
 		    return executeCommandDetails(context);
 	}
   
  
  

} 

  
  
  
