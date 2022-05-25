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
package org.aspcfs.modules.cessazionevariazione.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.ProjectList;
import com.zeroio.webutils.FileDownload;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.communications.base.CampaignList;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.cessazionevariazione.base.Ticket;
import org.aspcfs.modules.cessazionevariazione.base.TicketList;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: TroubleTickets.java,v 1.37 2002/12/20 14:07:55 mrajkowski Exp
 *          $
 * @created March 15, 2002
 */
public final class TroubleTickets extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-view"))) {
      return ("DefaultError");
    }
    return (this.executeCommandHome(context));
  }
  
  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "cessazionevariazione-cessazionevariazione-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    try {
      db = this.getConnection(context);
      
      
      SystemStatus systemStatus = this.getSystemStatus(context);
      
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
      if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter(
          "contact") != null && context.getRequest().getParameter("contact").equals(
          "on"))) {
        newTic = (Ticket) context.getFormBean();
      
      } else {
        newTic = new Ticket();
      }

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
  
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "AddTicket", "Ticket Add");
    if (context.getRequest().getParameter("actionSource") != null) {
      context.getRequest().setAttribute(
          "actionSource", context.getRequest().getParameter("actionSource"));
      return "AddTicketOK";
    }
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    
    String retPage = "SystemError";
    
    String tipo_richiesta = context.getRequest().getParameter( "tipo_richiesta" );
    tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
    
    retPage = "AddOK";
    
    
    return ( retPage );
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      //Load the ticket
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new Ticket();
        newTic.queryRecord(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
      }

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }

     
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
     
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
     
      
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

     
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");

    String retPage = "Modify";
   /* String tipo_richiesta = newTic.getTipo_richiesta();
    tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);*/
    
    
    /*
    if( tipo_richiesta.equalsIgnoreCase( "epidemologia_malattie_infettive" ) ){
    	retPage = "ModifyEpidemologia_malattie_infettive"; }else
    if( tipo_richiesta.equalsIgnoreCase( "autorizzazione_trasporto_animali_vivi" ) ){
    	retPage = "ModifyAutorizzazione_trasporto_animali_vivi"; }else
    if( tipo_richiesta.equalsIgnoreCase( "movimentazione_compravendita_animali" ) ){
    	retPage = "ModifyMovimentazione_compravendita_animali"; }else
    if( tipo_richiesta.equalsIgnoreCase( "macellazioni" ) ){
    	retPage = "ModifyMacellazioni"; }else
    if( tipo_richiesta.equalsIgnoreCase( "attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" ) ){
    	retPage = "ModifyAttivita_ispettiva_rilascioautorizzazioni_e_vigilanza"; }else
    if( tipo_richiesta.equalsIgnoreCase( "smaltimento_spoglie_animali" ) ){
    	retPage = "ModifySmaltimento_spoglie_animali"; }
    */
    
    return getReturn( context, retPage );
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "cessazionevariazione-cessazionevariazione-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Ticket newTic = null;
    String ticketId = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      String fromDefectDetails = context.getRequest().getParameter("defectCheck");
      if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
        fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
      }

      // Parameters
      ticketId = context.getRequest().getParameter("id");
      // Reset the pagedLists since this could be a new visit to this ticket
      deletePagedListInfo(context, "TicketDocumentListInfo");
      deletePagedListInfo(context, "SunListInfo");
      deletePagedListInfo(context, "TMListInfo");
      deletePagedListInfo(context, "CSSListInfo");
      deletePagedListInfo(context, "TicketsFolderInfo");
      deletePagedListInfo(context, "TicketTaskListInfo");
      deletePagedListInfo(context, "ticketPlanWorkListInfo");
      db = this.getConnection(context);
      // Load the ticket
      newTic = new Ticket();
      newTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }

      Organization orgDetails = new Organization(db, newTic.getOrgId());
      // check wether or not the product id exists
    


    
      
      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
      Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
    
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
    addRecentItem(context, newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");
    
    String retPage = "DetailsOK";
    String tipo_richiesta = newTic.getTipo_richiesta();
    tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
    
    retPage = "DetailsOK";
    
    /*
    if( tipo_richiesta.equalsIgnoreCase( "epidemologia_malattie_infettive" ) ){
    	retPage = "DetailsEpidemologia_malattie_infettiveOK"; }else
    if( tipo_richiesta.equalsIgnoreCase( "autorizzazione_trasporto_animali_vivi" ) ){
    	retPage = "DetailsAutorizzazione_trasporto_animali_viviOK"; }else
    if( tipo_richiesta.equalsIgnoreCase( "movimentazione_compravendita_animali" ) ){
    	retPage = "DetailsMovimentazione_compravendita_animaliOK"; }else
    if( tipo_richiesta.equalsIgnoreCase( "macellazioni" ) ){
    	retPage = "DetailsMacellazioniOK"; }else
    if( tipo_richiesta.equalsIgnoreCase( "attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" ) ){
    	retPage = "DetailsAttivita_ispettiva_rilascioautorizzazioni_e_vigilanzaOK"; }else
    if( tipo_richiesta.equalsIgnoreCase( "smaltimento_spoglie_animali" ) ){
    	retPage = "DetailsSmaltimento_spoglie_animaliOK"; }
    */
    
    return ( retPage );
  }


  /**
   * View Tickets History
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewHistory(ActionContext context) {

    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-history-view"))) {
      return ("PermissionError");
    }

    Connection db = null;
    Ticket thisTic = null;
    String ticketId = null;

    try {
      ticketId = context.getRequest().getParameter("id");
      db = this.getConnection(context);
      thisTic = new Ticket();
      SystemStatus systemStatus = this.getSystemStatus(context);
      thisTic.setSystemStatus(systemStatus);
      thisTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }

      
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Tickets", "Ticket Details");
    context.getRequest().setAttribute("TicketDetails", thisTic);
    addModuleBean(context, "ViewTickets", "View Tickets");
    addRecentItem(context, thisTic);
    return ("ViewHistoryOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    int MINIMIZED_ITEMS_PER_PAGE = 5;
    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-view"))) {
      return ("PermissionError");
    }
    context.getSession().removeAttribute("searchTickets");
    Connection db = null;
    
	
    TicketList assignedToMeList = new TicketList();
    TicketList openList = new TicketList();
    TicketList createdByMeList = new TicketList();
    TicketList allTicketsList = new TicketList();
    TicketList userGroupTicketList = new TicketList();
    User user = this.getUser(context, this.getUserId(context));
    String sectionId = null;
    if (context.getRequest().getParameter("pagedListSectionId") != null) {
      sectionId = context.getRequest().getParameter("pagedListSectionId");
    }
    //reset the paged lists
   
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    //Assigned To Me
    PagedListInfo assignedToMeInfo = this.getPagedListInfo(
        context, "AssignedToMeInfo", "t.entered", "desc");
    assignedToMeInfo.setLink("TroubleTicketsCessazionevariazione.do?command=Home");
    if (sectionId == null) {
      if (!assignedToMeInfo.getExpandedSelection()) {
        if (assignedToMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (assignedToMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          assignedToMeInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(assignedToMeInfo.getId())) {
      assignedToMeInfo.setExpandedSelection(true);
    }
    if (sectionId == null || assignedToMeInfo.getExpandedSelection() == true) {
      assignedToMeList.setPagedListInfo(assignedToMeInfo);
      assignedToMeList.setAssignedTo(user.getId());
      assignedToMeList.setIncludeAllSites(true);
      assignedToMeList.setOnlyOpen(true);
      if ("assignedToMe".equals(assignedToMeInfo.getListView())) {
        assignedToMeList.setAssignedTo(user.getId());
      }
    }
    //Other Tickets In My Department
    PagedListInfo openInfo = this.getPagedListInfo(
        context, "OpenInfo", "t.entered", "desc");
    openInfo.setLink("TroubleTicketsCessazionevariazione.do?command=Home");
    if (sectionId == null) {
      if (!openInfo.getExpandedSelection()) {
        if (openInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          openInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (openInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          openInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(openInfo.getId())) {
      openInfo.setExpandedSelection(true);
    }

    //Tickets Created By Me
    PagedListInfo createdByMeInfo = this.getPagedListInfo(
        context, "CreatedByMeInfo", "t.entered", "desc");
    createdByMeInfo.setLink("TroubleTicketsCessazionevariazione.do?command=Home");
    if (sectionId == null) {
      if (!createdByMeInfo.getExpandedSelection()) {
        if (createdByMeInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (createdByMeInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          createdByMeInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(createdByMeInfo.getId())) {
      createdByMeInfo.setExpandedSelection(true);
    }

    //Tickets in my User Group
    PagedListInfo userGroupTicketInfo = this.getPagedListInfo(
        context, "UserGroupTicketInfo", "t.entered", "desc");
    userGroupTicketInfo.setLink("TroubleTicketsCessazionevariazione.do?command=Home");
    if (sectionId == null) {
      if (!userGroupTicketInfo.getExpandedSelection()) {
        if (userGroupTicketInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          userGroupTicketInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (userGroupTicketInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          userGroupTicketInfo.setItemsPerPage(
              PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(userGroupTicketInfo.getId())) {
      userGroupTicketInfo.setExpandedSelection(true);
    }
    if (sectionId == null || userGroupTicketInfo.getExpandedSelection() == true) {
      userGroupTicketList.setPagedListInfo(userGroupTicketInfo);
      userGroupTicketList.setInMyUserGroups(user.getId());
			userGroupTicketList.setIncludeAllSites(true);
      userGroupTicketList.setOnlyOpen(true);
    }
    //All Tickets
    PagedListInfo allTicketsInfo = this.getPagedListInfo(
        context, "AllTicketsInfo", "t.entered", "desc");
    allTicketsInfo.setLink("TroubleTicketsCessazionevariazione.do?command=Home");
    if (sectionId == null) {
      if (!allTicketsInfo.getExpandedSelection()) {
        if (allTicketsInfo.getItemsPerPage() != MINIMIZED_ITEMS_PER_PAGE) {
          allTicketsInfo.setItemsPerPage(MINIMIZED_ITEMS_PER_PAGE);
        }
      } else {
        if (allTicketsInfo.getItemsPerPage() == MINIMIZED_ITEMS_PER_PAGE) {
          allTicketsInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
        }
      }
    } else if (sectionId.equals(allTicketsInfo.getId())) {
      allTicketsInfo.setExpandedSelection(true);
    }
    if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
      allTicketsList.setPagedListInfo(allTicketsInfo);
      allTicketsList.setSiteId(user.getSiteId());
      if (user.getSiteId() > -1) {
        allTicketsList.setExclusiveToSite(true);
        allTicketsList.setIncludeAllSites(false);
      }
      allTicketsList.setUnassignedToo(true);
      allTicketsList.setOnlyOpen(true);
    }
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
     
      	LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
          TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
          context.getRequest().setAttribute("TipoCampione", TipoCampione);
          
          
          LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
          EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
          context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
  
          LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
          DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
          context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
          
      
      if (sectionId == null || assignedToMeInfo.getExpandedSelection()) {
        assignedToMeList.buildList(db);
      }
      if (sectionId == null || openInfo.getExpandedSelection()) {
        openList.buildList(db);
      }
      if (sectionId == null || createdByMeInfo.getExpandedSelection()) {
        createdByMeList.buildList(db);
      }
      if (sectionId == null || allTicketsInfo.getExpandedSelection()) {
        allTicketsList.buildList(db);
      }
      if (sectionId == null || userGroupTicketInfo.getExpandedSelection()) {
        userGroupTicketList.buildList(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
    context.getRequest().setAttribute("CreatedByMeList", createdByMeList);
    context.getRequest().setAttribute("AssignedToMeList", assignedToMeList);
    context.getRequest().setAttribute("OpenList", openList);
    context.getRequest().setAttribute("AllTicketsList", allTicketsList);
    context.getRequest().setAttribute("UserGroupTicketList", userGroupTicketList);
    addModuleBean(context, "VisualizzaRichiesta", "Visualizza Richiesta");
    return ("HomeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSearchTickets(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    PagedListInfo ticListInfo = this.getPagedListInfo(context, "TicListInfo");

    Connection db = null;
    User user = this.getUser(context, this.getUserId(context));
    TicketList ticList = new TicketList();
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    ticListInfo.setLink("TroubleTicketsCessazionevariazione.do?command=SearchTickets");
    ticList.setPagedListInfo(ticListInfo);
    ticListInfo.setSearchCriteria(ticList, context);

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
      
      if (ticList.getSiteId() == Constants.INVALID_SITE) {
        ticList.setSiteId(user.getSiteId());
        ticList.setIncludeAllSites(true);
      } else if (ticList.getSiteId() == -1) {
        ticList.setExclusiveToSite(true);
        ticList.setIncludeAllSites(false);
      } else {
        ticList.setExclusiveToSite(true);
        ticList.setIncludeAllSites(false);
      }
      if ("unassigned".equals(ticListInfo.getListView())) {
        ticList.setUnassignedToo(true);
        ticList.setBuildDepartmentTickets(true);
        ticList.setDepartment(
            thisUser.getUserRecord().getContact().getDepartment());
      } else if ("assignedToMe".equals(ticListInfo.getListView())) {
        ticList.setAssignedTo(getUserId(context));
      } else {
        ticList.setUnassignedToo(true);
        if ("createdByMe".equals(ticListInfo.getListView())) {
          ticList.setEnteredBy(getUserId(context));
        }
      }
      //set the status
      if (ticListInfo.getFilterKey("listFilter1") == 1) {
        ticList.setOnlyOpen(true);
      } else if (ticListInfo.getFilterKey("listFilter1") == 2) {
        ticList.setOnlyClosed(true);
      }
      ticList.buildList(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "SearchTickets", "Search Tickets");
    context.getRequest().setAttribute("TicList", ticList);
    context.getSession().setAttribute("searchTickets", "yes");
    addModuleBean(context, "SearchTickets", "Search Tickets");
    return ("ResultsOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopen(ActionContext context) {

    if (!(hasPermission(context, "tickets-tickets-edit"))) {
      return ("PermissionError");
    }
    int resultCount = -1;
    Connection db = null;
    Ticket thisTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      Ticket oldTicket = new Ticket(db, thisTicket.getId());
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      if (resultCount == -1) {
        return (executeCommandDetails(context));
      } else if (resultCount == 1) {
        thisTicket.queryRecord(db, thisTicket.getId());
        this.processUpdateHook(context, oldTicket, thisTicket);
        return (executeCommandDetails(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    return ("UserError");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandInsert(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean contactRecordInserted = false;
    boolean isValid = true;
    SystemStatus systemStatus = this.getSystemStatus(context);
    
    Ticket newTicket = null;

    String newContact = context.getRequest().getParameter("contact");

    Ticket newTic = (Ticket) context.getFormBean();
    
    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));

   
   

    try {
      db = this.getConnection(context);

      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
      Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
     
        if (newTic.getOrgId() > 0) {
          newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
        }
        isValid = this.validateObject(context, db, newTic) && isValid;
        if (isValid) {
          recordInserted = newTic.insert(db,context);
        }
      

      if (recordInserted) {
        //Prepare the ticket for the response
        newTicket = new Ticket(db, newTic.getId());
        context.getRequest().setAttribute("TicketDetails", newTicket);
      

    

        addRecentItem(context, newTicket);

        processInsertHook(context, newTicket);
      } else {
        if (newTic.getOrgId() != -1) {
          Organization thisOrg = new Organization(db, newTic.getOrgId());
          newTic.setCompanyName(thisOrg.getName());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Ticket Insert ok");
    if (recordInserted && isValid) {
      if (context.getRequest().getParameter("actionSource") != null) {
        context.getRequest().setAttribute(
            "actionSource", context.getRequest().getParameter("actionSource"));
        return "InsertTicketOK";
      }
      String retPage = "DetailsOK";
      String tipo_richiesta = newTic.getTipo_richiesta();
      tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
      
      retPage = "InsertOK";
      
      
      
      if( tipo_richiesta.equalsIgnoreCase( "autorizzazione_trasporto_animali_vivi" ) ){
      	retPage = "DetailsAutorizzazione_trasporto_animali_viviOK"; }else{
      	retPage = "DetailsAttivita_ispettiva_rilascioautorizzazioni_e_vigilanzaOK"; 
      }
      
      
      return ( retPage );//("InsertOK"); 
    }
    return (executeCommandAdd(context));
  }


  /**
   * Prepares supplemental form data that a user can search by
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandSearchTicketsForm(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Prepare ticket state form data
  
      addModuleBean(context, "SearchTickets", "Tickets Search");
     
 try{

      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      //sites lookup
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);

      return ("SearchTicketsFormOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "tickets-tickets-edit")) {
      return ("PermissionError");
    }
    int resultCount = 0;
    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;
    Ticket newTic = (Ticket) context.getFormBean();
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      
      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
      Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
      
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());

      newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
        resultCount = newTic.update(db);
        if (resultCount == 1) {
          newTic.queryRecord(db, newTic.getId());
          processUpdateHook(context, previousTicket, newTic);
        }
      }
    
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandHome(context));
      }
      return getReturn(context, "Update");
    }
    return (executeCommandModify(context));
  }


  public String executeCommandChiudi(ActionContext context) {
	
	  


	    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-edit"))) {
	      return ("PermissionError");
	    }
	    int resultCount = -1;
	    Connection db = null;
	    Ticket thisTicket = null;
	    try {
	      db = this.getConnection(context);
	      thisTicket = new Ticket(
	          db, Integer.parseInt(context.getRequest().getParameter("id")));
	      Ticket oldTicket = new Ticket(db, thisTicket.getId());
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
	        return ("PermissionError");
	      }
	      thisTicket.setModifiedBy(getUserId(context));
	      resultCount = thisTicket.chiudi(db);
	      if (resultCount == -1) {
	        return (executeCommandDetails(context));
	      } else if (resultCount == 1) {
	        thisTicket.queryRecord(db, thisTicket.getId());
	        this.processUpdateHook(context, oldTicket, thisTicket);
	        return (executeCommandDetails(context));
	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	    return ("UserError");
	  
	  
	  }

  

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!(hasPermission(context, "cessazionevariazione-cessazionevariazione-delete"))) {
      return ("PermissionError");
    }
    HtmlDialog htmlDialog = new HtmlDialog();
    Ticket ticket = null;
    String id = context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      ticket = new Ticket(db, Integer.parseInt(id));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
        return ("PermissionError");
      }
      String returnType = (String) context.getRequest().getParameter("return");
      DependencyList dependencies = ticket.processDependencies(db);
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));

      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      if ("searchResults".equals(returnType)) {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTicketsCessazionevariazione.do?command=Trash&id=" + id + "&return=searchResults" + RequestUtils.addLinkParams(
            context.getRequest(), "popup|popupType|actionId") + "'");

      } else {
        htmlDialog.addButton(
            systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='TroubleTicketsCessazionevariazione.do?command=Trash&id=" + id + RequestUtils.addLinkParams(
            context.getRequest(), "popup|popupType|actionId") + "'");
      }
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


  /**
   * Deletes the specified ticket and triggers any hooks
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "cessazionevariazione-cessazionevariazione-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Ticket thisTic = null;
    Connection db = null;
    //Parameters
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      thisTic = new Ticket(db, Integer.parseInt(passedId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.delete(db, getDbNamePath(context));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        //del

        String returnType = (String) context.getRequest().getParameter(
            "return");
        if ("searchResults".equals(returnType)) {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTicketsCessazionevariazione.do?command=SearchTickets" + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId"));
        } else {
          context.getRequest().setAttribute(
              "refreshUrl", "TroubleTicketsCessazionevariazione.do?command=Home" + RequestUtils.addLinkParams(
              context.getRequest(), "popup|popupType|actionId"));
        }
        return ("DeleteOK");
      }
      return (executeCommandHome(context));
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}