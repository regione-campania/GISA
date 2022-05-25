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
package org.aspcfs.modules.canili.actions;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.canili.base.Organization;
import org.aspcfs.modules.canili.base.OrganizationList;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.GestoreComunicazioniVam;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Canili extends CFSModule {

	
	
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
	    if (!(hasPermission(context, "canili-view"))) {
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
	      
	      LookupList stageList = new LookupList(db, "lookup_account_stage");
	      stageList.addItem(-1, systemStatus.getLabel("accounts.any"));
	      context.getRequest().setAttribute("StageList", stageList);

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
	    addModuleBean(context, "Search Canili", "Canili Search");
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
	      if (!hasPermission(context, "canili-view")) {
	        return ("PermissionError");
	      }
	      
	    
	      
	      //Bypass dashboard and search form for portal users
	      if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));
	    }
    
    addModuleBean(context, "Dashboard", "DashboardCanili");
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
    if (!hasPermission(context, "canili-view")) {
      return ("PermissionError");
    }
    
	  UserBean user = (UserBean) context.getSession().getAttribute("User");
	 

	  addModuleBean(context, "SearchCanili", "SearchCanili");
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
    organizationList.setTipologia(10);
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
   
    
    addModuleBean(context, "View Canili", "Search Resaults Canili");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("Canili.do?command=Search");
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
      
      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
      stageList.addItem(-1, systemStatus.getLabel("accounts.any"));
      context.getRequest().setAttribute("StageList", stageList);

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("Canili.do?command=Search");
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
      } 
    } catch (Exception e) {
    	e.printStackTrace();
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "";
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
		  
	    if (!hasPermission(context, "canili-view")) {
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
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	      

	      
	      //check whether or not the owner is an active User
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList stageList = new LookupList(db, "lookup_account_stage");
	      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("StageList", stageList);
	      
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
	      addModuleBean(context, "Canili", "Modify Canili Details");
	      return "DetailsOK";
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Canili", "View Canili Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "Details");
	    }
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
  
  
  
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "canili-vigilanza-view")) {
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
	        "Canili.do?command=ViewVigilanza&orgId=" + passedId);
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
	      addModuleBean(context, "View Canili", "Canili View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      errorMessage.printStackTrace();
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "ViewVigilanza");
	  }
  
  public String executeCommandSetCodiceSINVSA(ActionContext context) throws SQLException {
		String codice = context.getRequest().getParameter("codice-sinvsa"); 
		String dataCodice = context.getRequest().getParameter("data-codice-sinvsa");
		int riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimento-id")); 
		String riferimentoId_nomeTab = context.getRequest().getParameter("riferimento-id-nome-tab");
		int userId = Integer.parseInt(context.getRequest().getParameter("user-id"));
		
		context.getRequest().setAttribute("orgId", String.valueOf(riferimentoId));
		
		try {
			PopolaCombo.setCodiceSINVSA(codice, dataCodice, riferimentoId, riferimentoId_nomeTab, userId);
		} catch (Exception e) {
			throw e;
		}
		
		return this.executeCommandDetails(context);
	}
  
}

  
  
  
