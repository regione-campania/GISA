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
package org.aspcfs.modules.osa.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.osa.base.Organization;
import org.aspcfs.modules.osa.base.OrganizationList;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;

import java.io.File;
import java.io.FileInputStream;
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

import javax.servlet.http.HttpServletRequest;

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
  
  
  public String executeCommandViewTamponi(ActionContext context) {
	    if (!hasPermission(context, "osa-osa-tamponi-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.tamponi.base.TicketList ticList = new org.aspcfs.modules.tamponi.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OsAnimali.do?command=ViewTamponi&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
	      TipoTampone.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoTampone", TipoTampone);
	      
	      LookupList EsitoTampone = new LookupList(db, "lookup_esito_tampone");
	      EsitoTampone.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoTampone", EsitoTampone);
	      if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }
	      newOrg = new Organization(db, passedId);
	      ticList.setOrgId(passedId);
	      if (newOrg.isTrashed()) {
	        ticList.setIncludeOnlyTrashed(true);
	      }
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
	    return getReturn(context, "ViewTamponi");
	  }
  
 
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "osa-osa-vigilanza-view")) {
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
	        "OsAnimali.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
	        controlliList.setOrgId(passedId);
	    	
	      
	      LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
	      AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("AuditTipo", AuditTipo);
	      
	      LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
	      TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAudit", TipoAudit);
	      
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
  
  public String executeCommandViewCessazionevariazione(ActionContext context) {
	    if (!hasPermission(context, "osa-osa-cessazionevariazione-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OsAnimali.do?command=ViewCessazionevariazione&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
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
	    return getReturn(context, "ViewCessazionevariazione");
	  }
  

  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "osa-osa-view"))) {
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
      
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      UserBean user = (UserBean) context.getSession().getAttribute("User");
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
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
    if (!hasPermission(context, "osa-osa-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList specieAnimali = new LookupList(db, "lookup_specie_animali");
      specieAnimali.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      specieAnimali.setMultiple(true);
      specieAnimali.setSelectSize(5);
      context.getRequest().setAttribute("specieAnimali", specieAnimali);

      
      LookupList tipoStabulatorio = new LookupList(db, "lookup_tipo_stabulatorio");
      tipoStabulatorio.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      tipoStabulatorio.setMultiple(true);
      tipoStabulatorio.setSelectSize(5);
      context.getRequest().setAttribute("tipoStabulatorio", tipoStabulatorio);

      
    
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      
      
      //LOOKUP CATEGORIA AGGIUNTA DA GIUSEPPE
      LookupList categoriaList = new LookupList(db, "lookup_categoria");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CategoriaList", categoriaList);

      Organization newOrg = (Organization) context.getFormBean();
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
            
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      String provincia = thisUser.getContact().getState();
      ArrayList<Integer> asl_id = new ArrayList<Integer>();
      newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());
      if(provincia!=null){
      if(provincia.equals("AV")){
    	  asl_id.add(1);
    	  asl_id.add(2);
      }else if(provincia.equals("BN")){
    	  asl_id.add(3);
      }else if(provincia.equals("CE")){
    	  asl_id.add(4);
    	  asl_id.add(5);
      }else if(provincia.equals("NA")){
    	  asl_id.add(6);
    	  asl_id.add(7);
    	  asl_id.add(8);
    	  asl_id.add(9);
    	  asl_id.add(10);
    	  
      }else if(provincia.equals("SA")){
    	  asl_id.add(11);
    	  asl_id.add(12);
    	  asl_id.add(13);
    	  
      }}
    
      if (newOrg.getEnteredBy() != -1) {
     
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
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "osa-osa-view")) {
      return ("PermissionError");
    }
    Connection   db           = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg       = null;
   
    try {
    	
    	 
    	 /**
    	  * 	COSTRUZIONE DEL BEAN A PARTIRE DALL'ID IN INPUT
    	  */
    		
    	 String temporgId = context.getRequest().getParameter("orgId");
         if (temporgId == null) {
           temporgId = (String) context.getRequest().getAttribute("orgId");
         }
         int tempid = Integer.parseInt(temporgId);
         db = this.getConnection(context);
//         if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
//           return ("PermissionError");
//         }
         newOrg = new Organization(db, tempid);
       //Caricamento Diffide
         Ticket t = new Ticket();
 		context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
 		context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));

         
         LookupList statoAutorizzazzioni = new LookupList(db, "lookup_stato_autorizzazzioni_osa");
         statoAutorizzazzioni.addItem(-1,  "-- SELEZIONA VOCE --");
         context.getRequest().setAttribute("statoAutorizzazzioni", statoAutorizzazzioni);
         
      LookupList specieAniamli = new LookupList(db, "lookup_specie_animali");
      specieAniamli.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("specieAniamli", specieAniamli);
         
      LookupList tipoStabulatorio = new LookupList(db, "lookup_tipo_stabulatorio");
      tipoStabulatorio.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("tipoStabulatorio", tipoStabulatorio);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

    
      
      LookupList categoriaList = new LookupList(db, "lookup_categoria");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("categoriaList", categoriaList);
      
      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata");
      imballataList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("imballataList", imballataList);
      
      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
      tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
      
      
     /* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      int AuditOrgId = newOrg.getOrgId();
      audit.setOrgId(AuditOrgId);
     
     // audit.buildList(db);
      
      if( (audit.size() - 1)>=0){
      
    	  context.getRequest().setAttribute("Audit",audit.get(0) );
      }*/
     
    } catch (Exception e) {
    	e.printStackTrace();
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
      if(context.getRequest().getAttribute("generato")!=null)
    	  return "generazioneCodiceOk";
      return getReturn(context, "Details");
    }
  }
  
 
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboardScelta(ActionContext context) {

	// Controllo per i permessi dell'utente
    if (!hasPermission(context, "osa-home-view")) {
      if (!hasPermission(context, "osa-osa-view")) {
        return ("PermissionError");
      }
    }
    
    return ("DashboardSceltaOK");
  }
  

  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "osa-dashboard-view")) {
      if (!hasPermission(context, "osa-osa-view")) {
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
          "Accounts", "org.aspcfs.modules.osm.base.AccountsListScheduledActions", "Accounts");
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
    if (!hasPermission(context, "osa-osa-view")) {
      return ("PermissionError");
    }
    String categoriaR = (String) context.getRequest().getParameter("searchcodecategoriaRischio");

    
    String name =context.getRequest().getParameter("searchAccountName"); 
    OrganizationList organizationList = new OrganizationList();
    organizationList.setNomeRappresentante(context.getParameter("searchNomeRappresentante"));
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("OsAnimali.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
     
      if((context.getParameter("searchNumAut"))!=null){
          organizationList.setNumAut(context.getParameter("searchNumAut"));
          }
      if(categoriaR!=null)
      organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));
      /*org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
     // audit.buildList(db);
      context.getRequest().setAttribute("AuditList",audit );
      */
     
      if(!"".equals(name) && name != null)
        	organizationList.setName(name);
      
     
      UserBean user = (UserBean) context.getSession().getAttribute("User");
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
      LookupList comuniList = new LookupList(listaComuni);
      
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
     

      LookupList impiantoZ = new LookupList(db, "lookup_attivita_osm");
      impiantoZ.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impiantoZ);
      
      //inserito da Carmela
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      
      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("OsAnimali.do?command=Search");
        }
        //Build the organization list
        organizationList.setPagedListInfo(searchListInfo);
        organizationList.setMinerOnly(false);
        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
        
    
        if((context.getParameter("searchNumAut"))!=null){
        organizationList.setNumAut(context.getParameter("searchNumAut"));
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
          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
        }
        organizationList.buildList(db);
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
        
        return "ListOK";
      }
    } catch (Exception e) {
      //Go through the SystemError process
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   *  ViewTickets: Displays Ticket history (open and closed) for a particular
   *  Account.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandViewTickets(ActionContext context) {
    if (!hasPermission(context, "osa-osa-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    TicketList ticList = new TicketList();
    Organization newOrg = null;
    //Process request parameters
    int passedId = Integer.parseInt(
        context.getRequest().getParameter("orgId"));

    //Prepare PagedListInfo
    PagedListInfo ticketListInfo = this.getPagedListInfo(
        context, "AccountTicketInfo", "t.entered", "desc");
    ticketListInfo.setLink(
        "OsAnimali.do?command=ViewTickets&orgId=" + passedId);
    ticList.setPagedListInfo(ticketListInfo);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, passedId)) {
        return ("PermissionError");
      }
      newOrg = new Organization(db, passedId);
      ticList.setOrgId(passedId);
      if (newOrg.isTrashed()) {
        ticList.setIncludeOnlyTrashed(true);
      }
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
    return getReturn(context, "ViewTickets");
  }

  public String executeCommandGeneraCodiceOsa(ActionContext context) {
	  if (!hasPermission(context, "osa-osa-generaosa-view")) {
	      return ("PermissionError");
	    }
	  Connection db = null;
	  Organization thisOrganization = null;
	  Organization oldOrg = null;
	    try {
	      db = this.getConnection(context);
	     
	      thisOrganization = new Organization(
		          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      oldOrg = new Organization(
		          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
		      
	      thisOrganization.setModifiedBy( getUserId(context) );
	      
	      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	      String provincia = thisUser.getContact().getState();
	      	      
	     
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	        siteList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	        context.getRequest().setAttribute("SiteList", siteList);
	        
				/*addModuleBean(context, "View Accounts", "View Account Details");
		      context.getRequest().setAttribute("OrgDetails", thisOrganization);*/
		   context.getRequest().setAttribute("generato", "ok");
	    } catch (Exception e) {
	    	  e.printStackTrace();
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		      //return ("DetailsOK");
		 } finally {
		      this.freeConnection(context, db);
		    }
		 
	  return executeCommandDetails(context);
  }

  public static void generaCodice(Connection db, org.aspcfs.modules.osm.base.Organization thisOrganization, String provincia) throws SQLException
  {
	  try
	  {
		  thisOrganization.generaCodice(db, provincia);
	  }  
	  catch(Exception e)
	  {
		  thisOrganization.rollback_sequence(db, provincia);
		  e.printStackTrace();
		
	  }
	
 }
  
  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws ParseException 
   */
  public String executeCommandInsert(ActionContext context) throws ParseException {
    if (!hasPermission(context, "osa-osa-add")) {
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
    
    
    
    if(context.getParameter("dataStatoUtilizzo") != null && ! "".equals(context.getParameter("dataStatoUtilizzo")))
    {
    	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoUtilizzo"));
    }
    
    if(context.getParameter("dataStatoAllevamento") != null && ! "".equals(context.getParameter("dataStatoAllevamento")))
    {
    	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoAllevamento"));
    }
    
    if(context.getParameter("dataStatoFornitore") != null && ! "".equals(context.getParameter("dataStatoFornitore")))
    {
    	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoFornitore"));
    }
    if(context.getParameter("dataStatoDeroga8") != null && ! "".equals(context.getParameter("dataStatoDeroga8")))
    {
    	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoDeroga8"));
    }
    if(context.getParameter("dataStatoDeroga0") != null && ! "".equals(context.getParameter("dataStatoDeroga9")))
    {
    	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoDeroga9"));
    }
    
    
    if(context.getParameter("statoUtilizzo") != null && ! "".equals(context.getParameter("statoUtilizzo")))
    {
    	newOrg.setStatoUtilizzo(Integer.parseInt(context.getParameter("statoUtilizzo")));
    }
    else
    {
    	newOrg.setStatoUtilizzo(0);
    }
    	
    if(context.getParameter("statoAllevamento") != null && ! "".equals(context.getParameter("statoAllevamento")))
    {
    	newOrg.setStatoAllevamento(Integer.parseInt(context.getParameter("statoAllevamento")));
    }
    else
    {
    	newOrg.setStatoAllevamento(0);
    }
    
    if(context.getParameter("statoFornitore") != null && ! "".equals(context.getParameter("statoFornitore")))
    {
    	newOrg.setStatoFornitore(Integer.parseInt(context.getParameter("statoFornitore")));
    }
    else
    {
    	newOrg.setStatoFornitore(0);
    }
    
    if(context.getParameter("statoDeroga8") != null && ! "".equals(context.getParameter("statoDeroga8")))
    {
    	newOrg.setStatoDeroga8(Integer.parseInt(context.getParameter("statoDeroga8")));
    }
    else
    {
    	newOrg.setStatoDeroga8(0);
    }
    
    if(context.getParameter("statoDeroga9") != null && ! "".equals(context.getParameter("statoDeroga9")))
    {
    	newOrg.setStatoDeroga9(Integer.parseInt(context.getParameter("statoDeroga9")));
    }
    else
    {
    	newOrg.setStatoDeroga9(0);
    }
    
    if(context.getParameter("autUtilizzo") != null)
    	newOrg.setAutUtilizzo(context.getParameter("autUtilizzo"));
    if(context.getParameter("autFornitore") != null)
    	newOrg.setAutFornitore(context.getParameter("autFornitore"));
    if(context.getParameter("autDeroga8") != null)
    	newOrg.setAutDeroga8(context.getParameter("autDeroga8"));
    if(context.getParameter("autDeroga9") != null)
    	newOrg.setAutDeroga9(context.getParameter("autDeroga9"));
    
    
    
    
    newOrg.setCategoriaRischio(3);
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setContractEndDate(new Timestamp(System.currentTimeMillis()));
    newOrg.setModifiedBy(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    newOrg.setDataPresentazione(context.getParameter("dataPresentazione"));
    
    try {
      db = this.getConnection(context);

      
      newOrg.generaCodiceAutorizzazzione(db);
      
     
      
    
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

     
        newOrg.setRequestItems(context);
        newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
     

      isValid = this.validateObject(context, db, newOrg);
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.osa.base.OrganizationAddress thisAddress = (org.aspcfs.modules.osa.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
        
      newOrg.setNomeRappresentante(context.getParameter("legaleRappresentante"));
      newOrg.setResponsabileAnimale(context.getParameter("responsabileAnimale"));
      newOrg.setMedicoVeterinario(context.getParameter("medicoVeterinario"));
      newOrg.setTelefonoRappresentante(context.getParameter("telefono"));
      newOrg.setEmailRappresentante(context.getParameter("mail"));
      newOrg.setFax(context.getParameter("fax"));
     
      if(!"".equals(context.getParameter("capacitaMax")))
      {
    	  newOrg.setCapacitaMax(Integer.parseInt(context.getParameter("capacitaMax")));
      }
      if(!"".equals(context.getParameter("mediaAnimaliOspitabili")))
      {
    	  newOrg.setMediaAnimaliOspitabili(Integer.parseInt(context.getParameter("mediaAnimaliOspitabili")));
      }
      if (isValid) {
    	  
        recordInserted = newOrg.insert(db,context.getRequest().getParameterValues("specieAnimali"),context.getRequest().getParameterValues("tipoStabulatorio"),context);
      }
      if (recordInserted) {
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);

           
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
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
    if (!(hasPermission(context, "osa-osa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean(); 
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
   
    newOrg.setModifiedBy(getUserId(context));
   
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
   
   newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
   
      if(context.getParameter("dataStatoUtilizzo") != null && ! "".equals(context.getParameter("dataStatoUtilizzo")))
      {
      	newOrg.setDataStatoUtilizzo(context.getParameter("dataStatoUtilizzo"));
      }
      
      if(context.getParameter("dataStatoAllevamento") != null && ! "".equals(context.getParameter("dataStatoAllevamento")))
      {
      	newOrg.setDataStatoAllevamento(context.getParameter("dataStatoAllevamento"));
      }
      
      if(context.getParameter("dataStatoFornitore") != null && ! "".equals(context.getParameter("dataStatoFornitore")))
      {
      	newOrg.setDataStatoFornitore(context.getParameter("dataStatoFornitore"));
      }
      if(context.getParameter("dataStatoDeroga8") != null && ! "".equals(context.getParameter("dataStatoDeroga8")))
      {
      	newOrg.setDataStatoDeroga8(context.getParameter("dataStatoDeroga8"));
      }
      if(context.getParameter("dataStatoDeroga9") != null && ! "".equals(context.getParameter("dataStatoDeroga9")))
      {
      	newOrg.setDataStatoDeroga9(context.getParameter("dataStatoDeroga9"));
      }
      
      
      if(context.getParameter("statoUtilizzo") != null && ! "".equals(context.getParameter("statoUtilizzo")))
      {
      	newOrg.setStatoUtilizzo(Integer.parseInt(context.getParameter("statoUtilizzo")));
      }
      else
      {
      	newOrg.setStatoUtilizzo(0);
      }
      	
      if(context.getParameter("statoAllevamento") != null && ! "".equals(context.getParameter("statoAllevamento")))
      {
      	newOrg.setStatoAllevamento(Integer.parseInt(context.getParameter("statoAllevamento")));
      }
      else
      {
      	newOrg.setStatoAllevamento(0);
      }
      
      if(context.getParameter("statoFornitore") != null && ! "".equals(context.getParameter("statoFornitore")))
      {
      	newOrg.setStatoFornitore(Integer.parseInt(context.getParameter("statoFornitore")));
      }
      else
      {
      	newOrg.setStatoFornitore(0);
      }
      
      if(context.getParameter("statoDeroga8") != null && ! "".equals(context.getParameter("statoDeroga8")))
      {
      	newOrg.setStatoDeroga8(Integer.parseInt(context.getParameter("statoDeroga8")));
      }
      else
      {
      	newOrg.setStatoDeroga8(0);
      }
      
      if(context.getParameter("statoDeroga9") != null && ! "".equals(context.getParameter("statoDeroga9")))
      {
      	newOrg.setStatoDeroga9(Integer.parseInt(context.getParameter("statoDeroga9")));
      }
      else
      {
      	newOrg.setStatoDeroga9(0);
      }
      
      if(context.getParameter("autUtilizzo") != null)
      	newOrg.setAutUtilizzo(context.getParameter("autUtilizzo"));
      if(context.getParameter("autFornitore") != null)
      	newOrg.setAutFornitore(context.getParameter("autFornitore"));
      if(context.getParameter("autDeroga8") != null)
      	newOrg.setAutDeroga8(context.getParameter("autDeroga8"));
      if(context.getParameter("autDeroga9") != null)
      	newOrg.setAutDeroga9(context.getParameter("autDeroga9"));
      
      	if(!context.getParameter("siteId").equals("-1"))
      		newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
        newOrg.setRequestItems(context);
        newOrg.setName(context.getParameter("name"));
        newOrg.setDataPresentazione(context.getParameter("dataPresentazione"));
        newOrg.setNomeRappresentante(context.getParameter("legaleRappresentante"));
        newOrg.setResponsabileAnimale(context.getParameter("responsabileAnimale"));
        newOrg.setMedicoVeterinario(context.getParameter("medicoVeterinario"));
        newOrg.setTelefonoRappresentante(context.getParameter("telefono"));
        newOrg.setEmailRappresentante(context.getParameter("mail"));
        newOrg.setFax(context.getParameter("fax"));
        
        if(!"".equals(context.getParameter("capacitaMax")))
        {
      	  newOrg.setCapacitaMax(Integer.parseInt(context.getParameter("capacitaMax")));
        }
        if(!"".equals(context.getParameter("mediaAnimaliOspitabili")))
        {
      	  newOrg.setMediaAnimaliOspitabili(Integer.parseInt(context.getParameter("mediaAnimaliOspitabili")));
        }
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      context.getRequest().setAttribute("orgId",newOrg.getOrgId() );
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.osa.base.OrganizationAddress thisAddress = (org.aspcfs.modules.osa.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      
    	 newOrg.update(db,context.getRequest().getParameterValues("specieAnimali"),context.getRequest().getParameterValues("tipoStabulatorio"),context);
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
            "individual")) {
         
        }
        
        LookupList IstatList = new LookupList(db, "lookup_codistat");
        IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("IstatList", IstatList);
       

         
        LookupList statoLab = new LookupList(db, "lookup_stato_lab");
        statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("statoLab", statoLab);
        
        LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
        OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
        
      
       

      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    
    
        return executeCommandDetails(context);
      
    
  }

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateCatRischio(ActionContext context) {
    
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    String org_id = context.getRequest().getParameter( "orgId" );
    String account_size = context.getRequest().getParameter( "accountSize" );
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual

      Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
      newOrg.setAccountSize( account_size );
     
      newOrg.setModifiedBy(getUserId(context));
      newOrg.setEnteredBy(getUserId(context));
      
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        resultCount = newOrg.update(db,context);
      }

    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");

    return ("UpdateCatRischioOK");
  }
  
  /**
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "osa-osa-delete")) {
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
       
          OpportunityHeaderList opportunityList = new OpportunityHeaderList();
          opportunityList.setOrgId(thisOrganization.getOrgId());
          opportunityList.buildList(db);
          opportunityList.invalidateUserData(context, db);
       
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
            "refreshUrl", "OsAnimali.do?command=Search");
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
      System.out.println(errorMessage);
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.accountDeletion"));
      context.getRequest().setAttribute(
          "refreshUrl", "OsAnimali.do?command=Search");
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
    if (!hasPermission(context, "osa-osa-delete")) {
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
          "refreshUrl", "OsAnimali.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "OsAnimali.do?command=Search");
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
    if (!hasPermission(context, "osa-osa-delete")) {
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
 
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "OsAnimali.do?command=Search");
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
    if (!hasPermission(context, "osa-osa-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
   
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
    if (!hasPermission(context, "osa-osa-delete")) {
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
        htmlDialog.addMessage("<form action=\"OsAnimali.do?command=Trash&auto-populate=true\" method=\"post\">");
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
    if (!hasPermission(context, "osa-osa-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    SystemStatus systemStatus = this.getSystemStatus(context);
    
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      org.aspcfs.modules.osa.base.OrganizationAddress soperativa = null;
      newOrg = new Organization(db,tempid);
      //newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
            
      Iterator it1 = newOrg.getAddressList().iterator();
   	  int nlocali =0;
   	  while(it1.hasNext())
   	  {
   		  org.aspcfs.modules.osa.base.OrganizationAddress add=(org.aspcfs.modules.osa.base.OrganizationAddress)it1.next();
   		  if(add.getType()==5)
   		  {
       		  soperativa=add;
   		  } 
   	  }
    
   	 context.getRequest().setAttribute("AddressSedeOperativa",soperativa);
      
    
   	 LookupList specieAnimali = new LookupList(db, "lookup_specie_animali");
     specieAnimali.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
     specieAnimali.setMultiple(true);
     specieAnimali.setSelectSize(5);
     context.getRequest().setAttribute("specieAnimali", specieAnimali);

     
     LookupList tipoStabulatorio = new LookupList(db, "lookup_tipo_stabulatorio");
     tipoStabulatorio.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
     tipoStabulatorio.setMultiple(true);
     tipoStabulatorio.setSelectSize(5);
     context.getRequest().setAttribute("tipoStabulatorio", tipoStabulatorio);
      
      context.getRequest().setAttribute("systemStatus", systemStatus);
      
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
    

      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
     
     
      Iterator it = newOrg.getAddressList().iterator();
   	  while(it.hasNext())
   	  {
   		  org.aspcfs.modules.osa.base.OrganizationAddress add=(org.aspcfs.modules.osa.base.OrganizationAddress)it.next();
   		  if(add.getLatitude()!=0 && add.getLongitude()!=0){
    		  String spatial_coords [] = null;
    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(add.getLatitude()), Double.toString(add.getLongitude()),db);
//    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//    		  {
//    			 AjaxCalls ajaxCall = new AjaxCalls();
//    			 String[] coordinate= ajaxCall.getCoordinate(add.getStreetAddressLine1(), add.getCity(), add.getState(), add.getZip(), ""+add.getLatitude(), ""+add.getLongitude(), "");
//    			 add.setLatitude(coordinate[1]);
//    			 add.setLongitude(coordinate[0]);
//    		  }
//    		  else
//    		  {
    			  add.setLatitude(spatial_coords[0]);
    			  add.setLongitude(spatial_coords[1]);
//    		  }

    	  }
   		  
   	  }//Fine modifica
      
   	  LookupList l_specie_animali_sel = new LookupList();
   	  
   	  Iterator<Integer> itSpeci = newOrg.getSpecie_animali().keySet().iterator();
   	  while(itSpeci.hasNext())
   	  {
   		  int key =itSpeci.next();
   		LookupElement el = new LookupElement();
        el.setCode( key);
        el.setDescription( newOrg.getSpecie_animali().get(key));
        l_specie_animali_sel.add(el);
        
   	  }
   	  if(l_specie_animali_sel.size()==0)
   	  {
   		LookupElement el = new LookupElement();
        el.setCode( -1);
        el.setDescription( "-SELEZIONA VOCE-");
        l_specie_animali_sel.add(el);
   	  }
   	  context.getRequest().setAttribute("SpecieAnimaliSelezionati", l_specie_animali_sel);
     
   	  
  LookupList l_tipo_stabulatorio_sel = new LookupList();
   	  
   	  Iterator<Integer> itstab = newOrg.getTipo_stabulatorio().keySet().iterator();
   	  while(itstab.hasNext())
   	  {
   		  int key =itstab.next();
   		LookupElement el = new LookupElement();
        el.setCode( key);
        el.setDescription( newOrg.getTipo_stabulatorio().get(key));
        l_tipo_stabulatorio_sel.add(el);
        
   	  }
   	  if(l_tipo_stabulatorio_sel.size()==0)
   	  {
   		LookupElement el = new LookupElement();
        el.setCode( -1);
        el.setDescription( "-SELEZIONA VOCE-");
        l_tipo_stabulatorio_sel.add(el);
   	  }
   	  context.getRequest().setAttribute("TipoStabulatorioSelezionati", l_tipo_stabulatorio_sel);
     
   	  
      //newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      
      //if this is an individual account
   
   	  newOrg.setComuni2(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());
      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      //ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      context.getRequest().setAttribute("StateSelect", stateSelect);
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("CountrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);

    } catch (Exception e) {
    	e.printStackTrace();
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

  
//inserito carmela
  public String executeCommandModificaCatRischio(ActionContext context) {
	    
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
	      //check whether or not the owner is an active User
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
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
	      return ("ModificaCatRischioOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "ModificaCatRischio");
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
  
  
  
	

}




  


