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
package org.aspcfs.modules.osmregistrati.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.Storico;
import org.aspcfs.modules.osmregistrati.base.Organization;
import org.aspcfs.modules.osmregistrati.base.OrganizationList;
import org.aspcfs.modules.osmregistrati.base.SottoAttivita;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
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
import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.requestor.base.Comuni;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.trasportoanimali.base.Veicolo;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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
import java.util.Map;

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
  
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "osmregistrati-osmregistrati-vigilanza-view")) {
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
	        "OsmRegistrati.do?command=ViewVigilanza&orgId=" + passedId);
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
	    if (!hasPermission(context, "osmregistrati-osmregistrati-cessazionevariazione-view")) {
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
	        "OsmRegistrati.do?command=ViewCessazionevariazione&orgId=" + passedId);
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
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "osmregistrati-osmregistrati-reports-delete"))) {
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
          db, this.getPath(context, "osmregistrati-reports"));
      String filePath1 = this.getPath(context, "osmregistrati-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath1);
      }
      String filePath2 = this.getPath(context, "osmregistrati-reports") + getDatePath(
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
    if (!(hasPermission(context, "osmregistrati-osmregistrati-view"))) {
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
      
      
      
/*    LookupList llist=new LookupList(db,"lookup_osm_types");
      llist.addItem(-1, "-nessuno-");
      context.getRequest().setAttribute("llist", llist);
*/
      LookupList impiantoZ = new LookupList(db, "lookup_attivita_osm_reg");
      impiantoZ.addItem(-1,  "-- SELEZIONA VOCE --");

      context.getRequest().setAttribute("impiantoZ", impiantoZ);
           
      LookupList categoriaList = new LookupList(db, "lookup_categoria");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CategoriaList", categoriaList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      impianto.setSelectSize(10);
      impianto.setMultiple(true);
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
    
    // associazione OSM - azienda zootecnica
    String ricercaOsmAssociabiliParameter = (String)context.getRequest().getParameter("ricercaOsmAssociabiliParameter");
    if (ricercaOsmAssociabiliParameter != null && ricercaOsmAssociabiliParameter.equals("1")) {
    	context.getRequest().setAttribute("ricercaOsmAssociabiliAttribute", true);
    }
    
    return ("SearchOK");
  }


  /**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "osmregistrati-osmregistrati-add")) {
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
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      impianto.setSelectSize(10);
      impianto.setMultiple(true);
      
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
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      if (newOrg.getEnteredBy() != -1) {
        newOrg.setTypeListToTypes(db);
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-view")) {
      return ("PermissionError");
    }
    
	context.getRequest().setAttribute("Messaggio", (String)context.getRequest().getAttribute("Messaggio"));

    Connection   db           = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg       = null;
    ArrayList    elencoAttivita = null;
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
     
      Iterator it_coords = newOrg.getAddressList().iterator();
	  while (it_coords.hasNext()) {

			org.aspcfs.modules.osmregistrati.base.OrganizationAddress thisAddress = (org.aspcfs.modules.osmregistrati.base.OrganizationAddress) it_coords.next();
			if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
	    		  
	    		  String spatial_coords [] = null;
	    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()),db);
//	    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//	    		  {
//	    			 AjaxCalls ajaxCall = new AjaxCalls();
//	    			 String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//	    			 thisAddress.setLatitude(coordinate[1]);
//	    			 thisAddress.setLongitude(coordinate[0]);
//	    		  }
//	    		  else
//	    		  {
	    			  thisAddress.setLatitude(spatial_coords[0]);
	    			  thisAddress.setLongitude(spatial_coords[1]);
	    		  //}
	        	  
	    	  }
	}
      
      //check whether or not the owner is an active User
      newOrg.setOwner(this.getUserId(context));
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      impianto.setSelectSize(10);
      impianto.setMultiple(true);
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

      String codice1 = null;
      String codice2 = null;
      String codice3 = null;
      String codice4 = null;
      String codice5 = null;
      String codice6 = null;
      String codice7 = null;
      String codice8 = null;
      String codice9 = null;
      String codice10 = null;
      
      if(newOrg.getCodice1()!=null){
      codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
      context.getRequest().setAttribute("codice1", codice1);
      }else if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("codice2", codice2);
      }else if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("codice3", codice3);
      }else if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("codice4", codice4);
      }else if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("codice5", codice5);
      }else if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("codice6", codice6);
      }else if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("codice7", codice7);
      }else if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("codice8", codice8);
      }else if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("codice9", codice9);
      }else if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("codice10", codice10);
      }     
      
      LookupList categoriaList = new LookupList(db, "lookup_categoria");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("categoriaList", categoriaList);
      
      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata");
      imballataList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("imballataList", imballataList);
      
      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
      tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
      
      elencoAttivita =  SottoAttivita.loadByStabilimento(tempid, db);
      
      /*org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      int AuditOrgId = newOrg.getOrgId();
      audit.setOrgId(AuditOrgId);
     
     // audit.buildList(db);
      
      if( (audit.size() - 1)>=0){
      
    	  context.getRequest().setAttribute("Audit",audit.get(0) );
      }*/
      context.getRequest().setAttribute("elencoSottoAttivita", elencoAttivita);
      
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
      
      // associazione OSM - azienda zootecnica
      String ricercaOsmAssociabiliParameter = (String)context.getRequest().getParameter("ricercaOsmAssociabiliParameter");
      if (ricercaOsmAssociabiliParameter != null && ricercaOsmAssociabiliParameter.equals("1")) {
      	context.getRequest().setAttribute("ricercaOsmAssociabiliAttribute", true);
      }
      
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
    if (!hasPermission(context, "osmregistrati-home-view")) {
      if (!hasPermission(context, "osmregistrati-osmregistrati-view")) {
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
    if (!hasPermission(context, "osmregistrati-dashboard-view")) {
      if (!hasPermission(context, "osmregistrati-osmregistrati-view")) {
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
          "Accounts", "org.aspcfs.modules.osmregistrati.base.AccountsListScheduledActions", "Accounts");
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-view")) {
      return ("PermissionError");
    }
    String categoriaR = (String) context.getRequest().getParameter("searchcodecategoriaRischio");
    String attivita = (String) context.getRequest().getParameter("searchcodeattivita");

    String source = (String) context.getRequest().getParameter("source");
    String stato = (String) context.getRequest().getParameter("searchstatoLab");
    String impianto = (String) context.getRequest().getParameter("searchimpianto");
    String categoria_id = (String)context.getRequest().getParameter("searchcodeCodiceSezione");
    String categoria_R = (String)context.getRequest().getParameter("searchcodecategoriaRischio");
    String codiceAllerta = context.getRequest().getParameter("searchcodiceAllerta");
    
    OrganizationList organizationList = new OrganizationList();
    addModuleBean(context, "View Accounts", "Search Results");

    // associazione OSM - azienda zootecnica
    String visualizzaOsmAssociato = (String) context.getRequest().getParameter("visualizzaOsmAssociato");
    if (visualizzaOsmAssociato != null) {
    	context.getRequest().getSession().removeAttribute("SearchOrgListInfo");
    }
    
    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("OsmRegistrati.do?command=Search");
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
      organizationList.setCategoria(categoria_R);
      organizationList.setTipologia(800);
      
      if(!"".equals(impianto) && impianto!=null && !impianto.equals("-1")) {
      	organizationList.setImpianto(impianto);
      	 }
      if(!"".equals(stato) && stato!=null && !stato.equals("-1")) {
        	organizationList.setStatoLab(stato);
        	 }
      if((context.getParameter("searchNumAut"))!=null){
          organizationList.setNumAut(context.getParameter("searchNumAut"));
          }
      
      if(attivita!=null)
          organizationList.setAttivita(Integer.parseInt(attivita));
      
      if(categoriaR!=null)
      organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));
     /* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
     // audit.buildList(db);
      context.getRequest().setAttribute("AuditList",audit );
      */
      if(!"".equals(codiceAllerta) && codiceAllerta != null)
      	organizationList.setCodiceAllerta(codiceAllerta);
      
      //return if no criteria is selected
      /*if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }*/
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
     

      LookupList impiantoZ = new LookupList(db, "lookup_attivita_osm_reg");
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
          searchListInfo.setLink("OsmRegistrati.do?command=Search");
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
        
        // associazione OSM - azienda zootecnica
        if (visualizzaOsmAssociato != null) {
        	// la richiesta arriva dalla scheda "Allevamenti" e l'associazione e' gia' presente
	        String numRegOsmAsString = (String) context.getRequest().getAttribute("numRegOsm");
	        String idAssociazione = (String) context.getRequest().getAttribute("idAssociazione");
	        if (numRegOsmAsString != null && !"".equals(numRegOsmAsString.trim())) {	        	
	        	// numero di registrazione OSM != null e non vuoto (esiste un OSM associato all'allevamento)
	        	// ricerchiamo l'OSM con numero registrazione associato all'allevamento da cui parte la richiesta
	        	searchListInfo.getSavedCriteria().put("searchcodeAccountNumber", numRegOsmAsString);
		        searchListInfo.setSearchCriteria(organizationList, context);
		        organizationList.setPagedListInfo(searchListInfo);
	        	context.getRequest().setAttribute("idAssociazione", idAssociazione);
	        }
	        else {
        		throw new Exception("Si sta tentando di associare un OSM con numero di registrazione non valido.");
        	}
        }
	    
	    // associazione OSM - azienda zootecnica
        String ricercaOsmAssociabiliParameter = (String)context.getRequest().getParameter("ricercaOsmAssociabiliParameter");
        if (ricercaOsmAssociabiliParameter != null && ricercaOsmAssociabiliParameter.equals("1")) {
        	// la richiesta arriva dalla scheda "Allevamenti" e l'associazione non e' presente
        	// ricerchiamo gli OSM associabili
        	organizationList.setFlagRicercaOsmAssociabiliAdAllevamento(true);
        	organizationList.setPagedListInfo(null);
			/*
	        // ricerca OSM appartenenti alla stessa ASL dell'allevamento da cui parte la richiesta
	        org.aspcfs.modules.osmregistrati.base.Organization osmACuiAssociareAllevamento = (org.aspcfs.modules.osmregistrati.base.Organization)context.getSession().getAttribute("osmACuiAssociareAllevamento");
	        int siteIdAllevamento = osmACuiAssociareAllevamento.getSiteId();
	        searchListInfo.getSavedCriteria().put("searchcodeOrgSiteId", siteIdAllevamento);
	        searchListInfo.setSearchCriteria(organizationList, context);
	        organizationList.setPagedListInfo(searchListInfo);
	        */	    
        }

        organizationList.buildList(db);
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
        
        // associazione OSM - azienda zootecnica
        if (ricercaOsmAssociabiliParameter != null && ricercaOsmAssociabiliParameter.equals("1")) {
          	context.getRequest().setAttribute("ricercaOsmAssociabiliAttribute", true);
        }
        
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
      //Go through the SystemError process
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "" ;
  }


  public static void generaCodice(Connection db, org.aspcfs.modules.osmregistrati.base.Organization thisOrganization)
		throws SQLException
 {
	  Integer aslid= thisOrganization.getSiteId();
	  String asl = null;
	  
	  if(aslid>-1){
		  if(aslid==1 || aslid==2)
			  asl=" AV";
		  else if(aslid==3)
			  asl=" BN";
		  else if(aslid==4 || aslid==5)
			  asl=" CE";
		  else if(aslid==6 || aslid==7 || aslid==8 || aslid==9 || aslid==10)
					  asl=" NA";
		  else if(aslid==11 || aslid==12 ||aslid==13)
			  asl=" SA";  
		  else
			  asl = " FUORIREGIONE";
	  }
		  
	  thisOrganization.generaCodice(db, asl);
	
 }
  
  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "osmregistrati-osmregistrati-add")) {
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
    newOrg.setContractEndDate(new Timestamp(System.currentTimeMillis()));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(getUserId(context));
    
    newOrg.setStatoLab(context.getRequest().getParameter("statoLab"));
    newOrg.setCategoriaRischio(3);
    
    try {
      db = this.getConnection(context);

      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      impianto.setSelectSize(10);
      impianto.setMultiple(true);
      context.getRequest().setAttribute("impianto", impianto);
      
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

      String codice1 = null;
      String codice2 = null;
      String codice3 = null;
      String codice4 = null;
      String codice5 = null;
      String codice6 = null;
      String codice7 = null;
      String codice8 = null;
      String codice9 = null;
      String codice10 = null;
      
      if(newOrg.getCodice1()!=null){
      codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
      context.getRequest().setAttribute("codice1", codice1);
      }else if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("codice2", codice2);
      }else if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("codice3", codice3);
      }else if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("codice4", codice4);
      }else if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("codice5", codice5);
      }else if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("codice6", codice6);
      }else if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("codice7", codice7);
      }else if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("codice8", codice8);
      }else if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("codice9", codice9);
      }else if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("codice10", codice10);
      }
      
      
      

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
      if (isValid) {
    	    Iterator it = newOrg.getAddressList().iterator();
    		while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it.next();
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
    	
    	 String[] codeAtt=context.getRequest().getParameterValues("impianto");
    	 String descrizione = context.getRequest().getParameter("noteAttivita");
    	 recordInserted = newOrg.insert(db, codeAtt, descrizione,context);
    	 
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
    if (!(hasPermission(context, "osmregistrati-osmregistrati-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean();
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String[] code=context.getRequest().getParameterValues("impianto");
    
    
    String descrizione=null;
    descrizione = context.getRequest().getParameter("noteAttivita");

    newOrg.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
    

        //don't want to populate the addresses, etc. if this is an individual account
        newOrg.setIsIndividual(false);
                
        newOrg.setRequestItems(context);
      
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      
      Iterator it = newOrg.getAddressList().iterator();
		while (it.hasNext()) {
			org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress) it
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
		}// Fine aggiunta
       
      if (isValid) {
        resultCount = newOrg.update(db, code, descrizione,context);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
       
        //update all contacts which are associated with this organization
 
        
        LookupList IstatList = new LookupList(db, "lookup_codistat");
        IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("IstatList", IstatList);
          
        String codice1 = null;
        String codice2 = null;
        String codice3 = null;
        String codice4 = null;
        String codice5 = null;
        String codice6 = null;
        String codice7 = null;
        String codice8 = null;
        String codice9 = null;
        String codice10 = null;
        
        if(newOrg.getCodice1()!=null){
        codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
        context.getRequest().setAttribute("codice1", codice1);
        }else if(newOrg.getCodice2()!=null){
        codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
        context.getRequest().setAttribute("codice2", codice2);
        }else if(newOrg.getCodice3()!=null){
        codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
        context.getRequest().setAttribute("codice3", codice3);
        }else if(newOrg.getCodice4()!=null){
        codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
        context.getRequest().setAttribute("codice4", codice4);
        }else if(newOrg.getCodice5()!=null){
        codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
        context.getRequest().setAttribute("codice5", codice5);
        }else if(newOrg.getCodice6()!=null){
        codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
        context.getRequest().setAttribute("codice6", codice6);
        }else if(newOrg.getCodice7()!=null){
        codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
        context.getRequest().setAttribute("codice7", codice7);
        }else if(newOrg.getCodice8()!=null){
        codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
        context.getRequest().setAttribute("codice8", codice8);
        }else if(newOrg.getCodice9()!=null){
        codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
        context.getRequest().setAttribute("codice9", codice9);
        }else if(newOrg.getCodice10()!=null){
        codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
        context.getRequest().setAttribute("codice10", codice10);
        }

           
        LookupList statoLab = new LookupList(db, "lookup_stato_lab");
        statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("statoLab", statoLab);
        
        LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
        OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
        
        LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
        impianto.addItem(-1,  "-- SELEZIONA VOCE --");
        impianto.setSelectSize(10);
        impianto.setMultiple(true);

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
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateCatRischio(ActionContext context) {
    if (!(hasPermission(context, "osmregistrati-tipochecklist-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    String org_id = context.getRequest().getParameter( "orgId" );
    String account_size = context.getRequest().getParameter( "accountSize" );
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    String[] code=context.getRequest().getParameterValues("impianto");
    String descrizione=context.getRequest().getParameter("noteAttivita");
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual

      Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
      newOrg.setAccountSize( account_size );
      newOrg.setTypeList(
          context.getRequest().getParameterValues("selectedList"));
      newOrg.setModifiedBy(getUserId(context));
      newOrg.setEnteredBy(getUserId(context));
      
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        resultCount = newOrg.update(db, code, descrizione,context);
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-delete")) {
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
      
          recordDeleted = thisOrganization.delete(
              db, context, getDbNamePath(context));
       
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        
        context.getRequest().setAttribute(
            "refreshUrl", "OsmRegistrati.do?command=Search");
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
          "refreshUrl", "OsmRegistrati.do?command=Search");
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-delete")) {
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
      recordUpdated = thisOrganization.updateStatus(
          db, context, true, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisOrganization.getOwner());
     
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "OsmRegistrati.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "OsmRegistrati.do?command=Search");
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-delete")) {
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
          "refreshUrl", "OsmRegistrati.do?command=Search");
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-edit")) {
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-delete")) {
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
    if (!hasPermission(context, "osmregistrati-osmregistrati-edit")) {
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
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      if (newOrg.getId() == -1) {
        newOrg = new Organization(db, tempid);
        
     // In fase di modifica
		Iterator it_coords = newOrg.getAddressList().iterator();
		while (it_coords.hasNext()) {

			org.aspcfs.modules.osmregistrati.base.OrganizationAddress thisAddress = (org.aspcfs.modules.osmregistrati.base.OrganizationAddress) it_coords.next();
	    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
	    		  
	    		  String spatial_coords [] = null;
	        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLongitude()),Double.toString(thisAddress.getLatitude()),db);
//	    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//	    		  {
//	    			 AjaxCalls ajaxCall = new AjaxCalls();
//	    			 String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//	    			 thisAddress.setLatitude(coordinate[1]);
//	    			 thisAddress.setLongitude(coordinate[0]);
//	    		  }
//	    		  else
//	    		  {
	    			  thisAddress.setLatitude(spatial_coords[0]);
	    			  thisAddress.setLongitude(spatial_coords[1]);
	    		  //}
			}
		}
        
     
      } else {
        newOrg.setTypeListToTypes(db);
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
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm_reg");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      impianto.setSelectSize(10);
      impianto.setMultiple(true);

      context.getRequest().setAttribute("impianto", impianto);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      

      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
      String codice1 = null;
      String codice2 = null;
      String codice3 = null;
      String codice4 = null;
      String codice5 = null;
      String codice6 = null;
      String codice7 = null;
      String codice8 = null;
      String codice9 = null;
      String codice10 = null;
      
      if(newOrg.getCodice1()!=null){
      codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
      context.getRequest().setAttribute("cod1", codice1);
      }else if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("cod2", codice2);
      }else if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("cod3", codice3);
      }else if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("cod4", codice4);
      }else if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("cod5", codice5);
      }else if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("cod6", codice6);
      }else if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("cod7", codice7);
      }else if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("cod8", codice8);
      }else if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("cod9", codice9);
      }else if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("cod10", codice10);
      }     
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
    
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

  
//inserito carmela
  public String executeCommandModificaCatRischio(ActionContext context) {
	    if ((!hasPermission(context, "osmregistrati-tipochecklist-add"))) {
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
   * @return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "osmregistrati-osmregistrati-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      //check permission to record
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }
      thisOrganization = new Organization(db, Integer.parseInt(orgId));
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Show a list of the different folders available in Accounts
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.ACCOUNTS);
      thisList.setLinkItemId(thisOrganization.getId());
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.setBuildResources(false);
      thisList.setBuildTotalNumOfRecords(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Custom Fields Details");
    return this.getReturn(context, "FolderList");
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
   * Upload file stabilimenti Giuseppe
   * @param context
   * @return
   */
  public String executeCommandUploadDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;
		
		try {
			
			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}
			
			/*LookupList llist = new LookupList(db,"lookup_specie_allevata");
		    llist.addItem(-1, "-nessuno-");
		    context.getRequest().setAttribute("SpecieA", llist);*/
		    
		    String specieA = multi.getParameter("SpecieA");
		    
			File myFileT = multi.getFile("file1"); 
			FileInputStream fiStream=new FileInputStream(myFileT);
			//BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logOsm");
			
			String pathDownloadLog = LeggiFile.leggiCampiOsm(context, db,myFileT,logPath, getUserId(context));
			
			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insertStabilimento(db);
			
			context.getRequest().setAttribute("pathLog", pathDownloadLog);
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("OsmUploadSaveOK");
		
	}
  
  public String executeCommandOsmUpload(ActionContext context){
		Connection db = null;

		try {
			db = getConnection(context);
		
			
			
			 UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		      if (thisUser.getRoleId()==21){ //UTENTE COMUNE
		    	  context.getRequest().setAttribute("UserComune", thisUser);
		      }
		     
		      if (thisUser.getRoleId()==23){ //UTENTE PROVINCIA
		    	  context.getRequest().setAttribute("UserProvincia", thisUser);
		      }		     
				
						
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("OsmUploadAddOK");
		
	}
  	
  /*
    private String[] convert2Wgs84UTM33N(String latitude, String longitude,
			Connection conn) throws SQLException {

		String[] ret = null;
		String sql = "select 	X \n" + "( \n" + "	transform \n" + "	(  \n"
				+ "		geomfromtext \n" + "		(  \n" + "			'POINT( "
				+ longitude
				+ " "
				+ latitude
				+ " )', 4326 \n"
				+ "	 \n"
				+ "		), 32633 \n"
				+ "	) \n"
				+ ") AS x, \n"
				+ "Y \n"
				+ "( \n"
				+ "	transform \n"
				+ "	(  \n"
				+ "		geomfromtext \n"
				+ "		(  \n"
				+ "			'POINT( "
				+ longitude
				+ " "
				+ latitude
				+ " )', 4326 \n"
				+ "	 \n" + "		), 32633 \n" + "	) \n" + ") AS y \n";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			// stat.setString( 1, wgs84[0] );
			// stat.setString( 2, wgs84[1] );
			// stat.setString( 3, wgs84[0] );
			// stat.setString( 4, wgs84[1] );

			ResultSet res = stat.executeQuery();
			if (res.next()) {
				ret = new String[2];
				ret[0] = res.getString("x");
				ret[1] = res.getString("y");

			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

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
  
  public String executeCommandPrepareUpdateLineePregresse(ActionContext context) {
		Connection db = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			Integer tempid = null;
			if (temporgId != null) {
				tempid = Integer.parseInt(temporgId);
			} else {
				tempid = (Integer) context.getSession().getAttribute("orgIdf5");
				if (tempid > 0) {
					context.getSession().removeAttribute("orgIdf5");
				}
			}    
			db = this.getConnection(context);
			
			// 1 indica il tipo di operatore 852
			context=PrepareUpdateLineePregresse(context,db,temporgId,1);
			
			return getReturn(context, "PrepareUpdateLineePregresse");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	
	public String executeCommandUpdateLineePregresse(ActionContext context) {
		Connection db = null;
		try {
			db = this.getConnection(context);	
			String idImpresa = context.getRequest().getParameter("id_impresa");
			
			int tipoImpresa = -1;
			PreparedStatement pst = db.prepareStatement("select tipologia from organization where org_id = "+idImpresa);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				tipoImpresa = rs.getInt("tipologia");
			}
			if (tipoImpresa == 801)
				tipoImpresa = 3;
			else
				tipoImpresa = 1;
			
			return UpdateLineePregresse(context,db,idImpresa,tipoImpresa);
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public static ActionContext PrepareUpdateLineePregresse(ActionContext context, Connection db, String id_impresa,
			int tipo_impresa) {
		try {
			if (tipo_impresa == 1) {
				ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
						.load_linee_attivita_secondarie_per_org_id(id_impresa, db);
				context.getRequest().setAttribute("linee_attivita_secondarie", linee_attivita_secondarie);

				ArrayList<String> linee_nuove = new ArrayList<String>();
				ArrayList<LineeAttivita> linee_attivita = new ArrayList<LineeAttivita>();

				// Questa stringa contiene la concatenazioni dei vari cod
				// macroarea, cod aggregazione ecc ecc con separatore '&&&'
				String id_linea = "";
				// id dell'ultimo livello per controllare se e' una foglia o meno
				int id_ultimo_livello = 0;
				// Descrizione linea di attivita' nuova nella parte sinistra
				// della form
				String desc_linea = "";
				// Descrizione linea di attivita' nuova nella parte destra della
				// form
				String desc_linea_2 = "";
				// indice i = 0 corrisponde alla linea primaria
				for (int i = 0; i < linee_attivita_secondarie.size(); i++) {
					id_ultimo_livello = 0;
					id_linea = "";
					desc_linea = "";
					desc_linea_2 = "";
					LineeAttivita l = linee_attivita_secondarie.get(i);
					if (!l.isMappato()) {
						PreparedStatement stat = db
								.prepareStatement("select * from mapping_codice_ateco_master_list_2015_2016 where id = "
										+ l.getId_attivita_masterlist());
						ResultSet rs = stat.executeQuery();
						if (rs.next()) {
							if (rs.getString("macroarea") != null && !rs.getString("macroarea").equals("")) {
								id_ultimo_livello = rs.getInt("id_macroarea");
								desc_linea_2 = "<b>MACROAREA: </b>" + rs.getString("macroarea") + "</br></br>";
								id_linea = "" + rs.getInt("id_macroarea") + "&&&";
								desc_linea += "<tr><th colspan='2'>Corrsispondente nuovo</th></tr><tr><td nowrap class='formLabel'>MACROAREA</td><td>"
										+ rs.getString("macroarea") + "</td></tr>";
							}
							if (rs.getString("aggregazione") != null && !rs.getString("aggregazione").equals("")) {
								id_ultimo_livello = rs.getInt("id_aggregazione");
								desc_linea_2 += "<b>AGGREGAZIONE: </b>" + rs.getString("aggregazione") + "</br></br>";
								id_linea += "" + rs.getInt("id_aggregazione") + "&&&";
								desc_linea += "<tr><td nowrap class='formLabel'>AGGREGAZIONE</td><td>"
										+ rs.getString("aggregazione") + "</td></tr>";
							}
							if (rs.getString("attivita") != null && !rs.getString("attivita").equals("")) {
								id_ultimo_livello = rs.getInt("id_attivita");
								desc_linea_2 += "<b>ATTIVITA: </b>" + rs.getString("attivita") + "</br></br>";
								id_linea += "" + rs.getInt("id_attivita") + "&&&";
								desc_linea += "<tr><td nowrap class='formLabel'>ATTIVITA</td><td>"
										+ rs.getString("attivita") + "</td></tr>";
							}
							if (rs.getString("descrizione") != null && !rs.getString("descrizione").equals("")) {
								id_ultimo_livello = rs.getInt("id_nuova_linea");
								desc_linea_2 += "<b>DESCRIZIONE: </b>" + rs.getString("descrizione") + "</br>";
								id_linea += "" + rs.getInt("id_nuova_linea") + "&&&";
								desc_linea += "<tr><td nowrap class='formLabel'>DESCRIZIONE</td><td>"
										+ rs.getString("descrizione") + "</td></tr>";
								// context.getRequest().setAttribute("nuova_linea",
								// linee_nuove);
							}
						}
						// Se c'e' la vecchia linea nella nuova gestione,
						// controllo se l'ultimo livello corrisponde ad una
						// foglia
						if (id_ultimo_livello != 0) {
							stat = db.prepareStatement("select * from ml8_linee_attivita_nuove_materializzata  where id_padre = "
									+ id_ultimo_livello);
							rs = stat.executeQuery();
							if (rs.next()) {
								// Non e' livello foglia
								id_linea = "false&&&" + id_linea;
							} else {
								// Livello foglia
								id_linea = "true&&&" + id_linea;

							}
						}
						if (!desc_linea.equals(""))
							desc_linea += "---" + id_linea + "---" + desc_linea_2;
						linee_attivita.add(l);
						linee_nuove.add(desc_linea);

						stat.close();
						rs.close();
					}
				}
				context.getRequest().setAttribute("linee_attivita", linee_attivita);
				context.getRequest().setAttribute("nuove_linee", linee_nuove);

				LookupList LookupTipoAttivita = new LookupList(db, "opu_lookup_tipologia_attivita");
				context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

				LineaProduttivaList lpList = new LineaProduttivaList();
				context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			}
			if (tipo_impresa==2){
				LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
				context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

				Stabilimento newStabilimento = null;
				newStabilimento = new Stabilimento(db,  Integer.parseInt(id_impresa));
				newStabilimento.getPrefissoAction(context.getAction().getActionName());
				context.getRequest().setAttribute("StabilimentoDettaglio",
						newStabilimento);
				
				
				newStabilimento.buildListLineeProduttivePregresse(db);
				newStabilimento.setListaLineeProduttive(newStabilimento.getListaLineeProduttivePregresse()); 
				LineaProduttivaList lpl = newStabilimento.getListaLineeProduttive();
				
				for(Object lpt : lpl )
				{
					LineaProduttiva lp0= (LineaProduttiva)lpt;
					
					//questa è la linea nuova associata fittizia
					String[] temp = lp0.getDescrizione_linea_attivita().split("->");
					
					int idLineaVecchia = -1;
					
					try
					{
						String macroarea = temp[0].replace("'","");
						String aggregazione = temp[1].replace("'","");
						String attivita = temp[2].replace("'","");
						idLineaVecchia = LineeAttivita.ottieniLineaVecchiaDaNuovaFittizia(db, macroarea, aggregazione, attivita);
					} 
					catch(Exception ex)
					{
						//niente
						
					}
					
					if(idLineaVecchia != -1)
					{
						lp0.setCandidatiNuoveLineeRanking(LineeAttivita.load_candidati_per_linea_fittiziaVersioneKnowledgeBased(idLineaVecchia,db)) ;
					}
					else //non sono riuscito a trovare la corrispondenza tra linea nuova fittizia (non presente in opu) e quella vecchia in lista linee attivita vecchia ana
					{
						System.out.println("trovata linea non mappabile");
					}
					
					
				}
				

				Operatore operatore = new Operatore () ;
				org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
				org.setName(operatore.getRagioneSociale());
				operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
				operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
				context.getRequest().setAttribute("Operatore", operatore);

				LineaProduttivaList lpList = new LineaProduttivaList();
				context.getRequest().setAttribute("ListaLineaProduttiva", lpList);

			}
			return context;

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return context;
		}
	}
	 

	

	// tipo_impresa:
	// 1 - 852
	// 2 - OPU
	
	
	
	
	
	// tipo_impresa:
	// 1 - 852
	// 2 - OPU
	public static String UpdateLineePregresse(ActionContext context, Connection db, String id_impresa, int tipo_impresa) {
		String ret = "";

		try {
			
			if (1==1) { //OSM REGISTRATI

				if (context.getRequest().getParameterValues("idLineaProduttiva") != null
						&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {
					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
					String id_nuovo = "";
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						id_nuovo = "";
						if (!lineeProduttiveSelezionate[i].equals("")) {
							id_nuovo = lineeProduttiveSelezionate[i];
						} else {
							id_nuovo = context.getRequest().getParameter("idLineaProduttiva_" + i);
							if (id_nuovo == null) {
								id_nuovo = (String) context.getRequest().getAttribute("idLineaProduttiva_" + i);
							}
						}
						int idVecchia = -1;
						if (context.getRequest().getParameter("vecchiaLineaId" + i) != null
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals("null")
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals(""))
							idVecchia = Integer.parseInt(context.getRequest().getParameter("vecchiaLineaId" + i));
						PreparedStatement pst = db
								.prepareStatement("UPDATE linee_attivita_ml8_temp c SET map_completo = true, id_norma = (select id_norma from ml8_linee_attivita_nuove_materializzata where id_attivita = " + id_nuovo + "), codice_univoco_ml = (select codice_attivita from ml8_linee_attivita_nuove_materializzata where id_attivita = " + id_nuovo + ") where org_id = " + id_impresa +" and map_completo is not true AND ctid = (SELECT ctid FROM linee_attivita_ml8_temp c2 where org_id = " + id_impresa +" and map_completo is not true ORDER BY ctid LIMIT 1);");
						pst.execute();
					}
				}

				/*REFRESH TAB MATERIALIZZATA*/
				try {
				RicercheAnagraficheTab.insertAtriStabilimentiOrganization(db, Integer.parseInt(id_impresa));
				} catch (Exception e) {}
				
				ret = "UpdateLineePregresseOK";
			}

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
	}
	
}




  


