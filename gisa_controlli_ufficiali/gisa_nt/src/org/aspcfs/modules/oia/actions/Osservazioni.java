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
package org.aspcfs.modules.oia.actions;

import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.oia.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;

import java.util.*;

import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.osservazioni.base.OsservazioniList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class Osservazioni extends CFSModule {

  /**
   * Sample action for prototyping, by including a specified page
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return getReturn(context, "Include");
  }
  
  /**
   * Re-opens a ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {
   
    int resultCount = -1;
    Connection db = null;
    org.aspcfs.modules.osservazioni.base.Osservazioni thisTicket = null;
    org.aspcfs.modules.osservazioni.base.Osservazioni oldTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      oldTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, thisTicket.getId());
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      thisTicket.queryRecord(db, thisTicket.getId());
 context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
      
     
     
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount == -1) {
      return (executeCommandTicketDetails(context));
    } else if (resultCount == 1) {
      this.processUpdateHook(context, oldTicket, thisTicket);
      return (executeCommandTicketDetails(context));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Prepares a ticket form
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddTicket(ActionContext context) {
   
    
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    Connection db = null;
    Ticket newTic = null;
    Organization newOrg = null;
    String retPag = "";
    //Parameters
    String temporgId = context.getRequest().getParameter("orgId");
    int tempid = Integer.parseInt(temporgId);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, tempid)) {
        return ("PermissionError");
      }
      //Organization for header
	    newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));

      //Ticket
      newTic = (Ticket) context.getFormBean();
      if (context.getRequest().getParameter("refresh") != null ||
          (context.getRequest().getParameter("contact") != null &&
              context.getRequest().getParameter("contact").equals("on"))) {
      } else {
        newTic.setOrgId(tempid);
      }
      
      addModuleBean(context, "View Accounts", "Add a Ticket");
      context.getRequest().setAttribute("OrgDetails", newOrg);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
 
      String tipo_richiesta = context.getRequest().getParameter( "tipo_richiesta" );
      retPag = "Add_" + tipo_richiesta + "OK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //return getReturn(context, "AddTicket");
    return retPag;
  }


  /**
   * Inserts a submitted ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandInsertTicket(ActionContext context) throws SQLException {
	  if (!hasPermission(context, "oia-oia-osservazioni-add")) {
		  return ("PermissionError");
	  }
	  int idMacc=-1;
	  if(context.getRequest().getParameter("idMacchinetta")!=null){
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		  idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta"));
	  }	else
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	  Connection db = null ;
	  boolean recordInserted = false ;
	  try {db = this.getConnection(context);
		  org.aspcfs.modules.nonconformita.actions.Osservazioni nonConformita = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
		  
		  nonConformita.executeCommandInsert(context,db);
		

		  String temporgId = context.getRequest().getParameter("orgId");
		  int tempid = Integer.parseInt(temporgId);
		  
		  
		  Organization newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
		  context.getRequest().setAttribute("OrgDetails", newOrg);
		  
		  context.getRequest().setAttribute("idNodo", newOrg.getOrgId());

	  } catch (Exception e) {
		  context.getRequest().setAttribute("Error", e);
		  return ("SystemError");
	  } finally {
		  this.freeConnection(context, db);
	  }
	  if (recordInserted) {
		  String tipoRichiesta = (String) context.getRequest().getAttribute("TipoRichiesta");
		  return "Details_" + tipoRichiesta + "OK";
	  }
	  return (executeCommandAddTicket(context));
  }


  /**
   * Load the ticket details tab
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandTicketDetails(ActionContext context) {
	  
	  int idMacc=-1;
	  if(context.getRequest().getParameter("idMacchinetta")!=null && ! "null".equals(context.getRequest().getParameter("idMacchinetta"))){
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		  idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta") );

	  }	else
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	  Connection db = null;

	  String retPag = null;
	  try {
		  db = this.getConnection(context);
		  org.aspcfs.modules.nonconformita.actions.Osservazioni nonconformita = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
		  nonconformita.executeCommandTicketDetails(context,db);
		  Ticket newTic = (Ticket)context.getRequest().getAttribute("TicketDetails");
		
		  retPag = "DettaglioOK";


		  // Load the organization for the header
		  Organization newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
		  context.getRequest().setAttribute("OrgDetails", newOrg);

	  } catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
	  } finally {
		  this.freeConnection(context, db);
	  }
	  //return getReturn(context, "TicketDetails");
	  return retPag;
  }


  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "nonconformita-nonconformita-delete")) {
      return ("PermissionError");
    }
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    
    if (context.getRequest().getParameter("idNodo")!=null)
    {
		context.getRequest().setAttribute("idNodo", context.getRequest().getParameter("idNodo"));

    }
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    //int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      org.aspcfs.modules.osservazioni.base.Osservazioni ticket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, Integer.parseInt(id));
      int orgId = ticket.getOrgId();
      //check permission to record
      if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
        return ("PermissionError");
      }
      DependencyList dependencies = ticket.processDependencies(db);
      //Prepare the dialog based on the dependencies
      HtmlDialog htmlDialog = new HtmlDialog();
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='OiaOsservazioni.do?command=DeleteTicket&idNodo="+context.getRequest().getParameter("idNodo")+"&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
          context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Delete the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteTicket(ActionContext context) {
    if (!hasPermission(context, "oia-oia-osservazioni-delete")) {
      return ("PermissionError");
    }
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    boolean recordDeleted = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      org.aspcfs.modules.osservazioni.base.Osservazioni thisTic = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, Integer.parseInt(passedId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.logicdelete(db, getDbNamePath(context));
      
      PreparedStatement pst = db.prepareStatement("UPDATE ticket SET punteggio = (select sum(punteggio) " +
  			"from ticket where trashed_date is null and tipologia in (2,8,7,16) and id_controllo_ufficiale = " +
  			"(select id_controllo_ufficiale from ticket where ticketid = ?)) " +
  			"WHERE ticketid = ("+thisTic.getIdControlloUfficiale()+");");
  	
  	pst.setInt(1, Integer.parseInt(thisTic.getIdControlloUfficiale()));
  	
  	
  	pst.execute();
      
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        //del
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute(
            "refreshUrl", "OiaVigilanza.do?command=TicketDetails&idNodo="+context.getRequest().getParameter("idNodo")+"&id="+thisTic.getIdControlloUfficiale()+"&orgId=" + orgId +
            (inline != null && "inline".equals(inline.trim()) ? "&popup=true" : ""));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      return ("DeleteTicketOK");
    } else {
      return (executeCommandTicketDetails(context));
    }
  }


  /**
   * Update the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateTicket(ActionContext context) {
    
    int idMacc=-1;
    if(context.getRequest().getParameter("idMacchinetta")!=null){
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta"));
    }	else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    Connection db = null;
    try {db = this.getConnection(context);
    	org.aspcfs.modules.nonconformita.actions.Osservazioni nonConformita = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
		nonConformita.executeCommandUpdateTicket(context,db);
		
		  Organization newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")) );
		  
		  context.getRequest().setAttribute("OrgDetails", newOrg);
		  
		  context.getRequest().setAttribute("idNodo", newOrg.getOrgId());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

   
    return (executeCommandTicketDetails(context));
  }


  //aggiunto da d.dauria
  public String executeCommandAdd(ActionContext context) {

	  

	  context.getRequest().setAttribute("aslCU", context.getRequest().getParameter("aslMacchinetta"));
	  if(context.getRequest().getParameter("idMacchinetta")!=null)
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
	  else
		  context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	  Connection db = null;
	 
	  try 
	  {
		  db = this.getConnection(context);
		  	String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			org.aspcfs.modules.nonconformita.actions.Osservazioni nonConformita = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
			nonConformita.executeCommandAdd(context,db);
			
			Organization newOrg = new Organization(db,Integer.parseInt(context.getParameter("orgId")) );			
			context.getRequest().setAttribute("OrgDetails", newOrg);
	  } 
	  catch (Exception e) 
	  {
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
	  return ("AddOK");
  }

  
  public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-nonconformita-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.osservazioni.base.Osservazioni newTicket = null;
		org.aspcfs.modules.osservazioni.base.Osservazioni newTic = (org.aspcfs.modules.osservazioni.base.Osservazioni) context.getFormBean();
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		try {
			db = this.getConnection(context);
			
			org.aspcfs.modules.nonconformita.actions.Osservazioni nc = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
			nc.executeCommandInsert(context,db);
			  
			Organization newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));		
			context.getRequest().setAttribute("OrgDetails", newOrg);
			  
			  context.getRequest().setAttribute("idNodo", newOrg.getOrgId());

		} catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally 
		{
			this.freeConnection(context, db);
		}
		recordInserted 	= (Boolean) context.getRequest().getAttribute("recordInserted");
		isValid 		= (Boolean) context.getRequest().getAttribute("isValid");
		
		if (recordInserted && isValid) 
		{
			String retPage = "DettaglioOK";
			String tipo_richiesta = newTic.getTipo_richiesta();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "InsertOK";



			return ( retPage );//("InsertOK"); 
		}
		return (executeCommandAdd(context));
	}
  
  

  /**
   * Loads the ticket for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyTicket(ActionContext context) {
   			
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    Connection db = null;
    
	try {
		db = this.getConnection(context);
		org.aspcfs.modules.nonconformita.actions.Osservazioni nonConformita = new org.aspcfs.modules.nonconformita.actions.Osservazioni();
		nonConformita.executeCommandModifyTicket(context,db);
		Ticket newTic = (Ticket)context.getRequest().getAttribute("TicketDetails");
		
		Organization newOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")) );		  context.getRequest().setAttribute("OrgDetails", newOrg);
		  
		  context.getRequest().setAttribute("idNodo", newOrg.getOrgId());


    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //return "ModifyautorizzazionetrasportoanimaliviviOK";
    return "ModifyOK";
    //return getReturn(context, "ModifyTicket");
  }
  
  
  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "distributori-distributori-edit")) {
      return ("PermissionError");
    }
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    Connection db = null;
    org.aspcfs.modules.osservazioni.base.Osservazioni newTic = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      User user = this.getUser(context, this.getUserId(context));
      //Load the ticket
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new org.aspcfs.modules.osservazioni.base.Osservazioni();
        newTic.queryRecord(db, Integer.parseInt(ticketId));
      } else {
        newTic = (org.aspcfs.modules.osservazioni.base.Osservazioni) context.getFormBean();
       
      }

      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      
      LookupList Provvedimenti = new LookupList(db, "lookup_osservazioni");
      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
      Provvedimenti.removeElementByLevel(9);
      context.getRequest().setAttribute("Osservazioni", Provvedimenti);
      
      LookupList NonConformitaAmministrative = new LookupList(db, "lookup_nonconformita_amministrative");
      NonConformitaAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformitaAmministrative", NonConformitaAmministrative);
      
      LookupList NonConformitaPenali = new LookupList(db, "lookup_nonconformita_penali");
      NonConformitaPenali.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformitaPenali", NonConformitaPenali);
      
      LookupList NonConformita = new LookupList(db, "lookup_nonconformita");
      NonConformita.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformita", NonConformita);
      
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
  public String executeCommandUpdate(ActionContext context) {
    if (!hasPermission(context, "distributori-distributori-edit")) {
      return ("PermissionError");
    }
    int idMacc=-1;
    if(context.getRequest().getParameter("idMacchinetta")!=null){
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta"));
    }	else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    int resultCount = 0;
    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;
    org.aspcfs.modules.osservazioni.base.Osservazioni newTic = (org.aspcfs.modules.osservazioni.base.Osservazioni) context.getFormBean();
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //check permission to record
     
      LookupList Provvedimenti = new LookupList(db, "lookup_osservazioni");
      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
      Provvedimenti.removeElementByLevel(9);
      context.getRequest().setAttribute("Osservazioni", Provvedimenti);
      
      LookupList NonConformitaAmministrative = new LookupList(db, "lookup_nonconformita_amministrative");
      NonConformitaAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformitaAmministrative", NonConformitaAmministrative);
      
      LookupList NonConformitaPenali = new LookupList(db, "lookup_nonconformita_penali");
      NonConformitaPenali.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformitaPenali", NonConformitaPenali);
      
      LookupList NonConformita = new LookupList(db, "lookup_nonconformita");
      NonConformita.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("NonConformita", NonConformita);
     
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      org.aspcfs.modules.osservazioni.base.Osservazioni previousTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, newTic.getId());
     
      //newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
    	  newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria //aggiunto da d.dauria
    	//newTic.setTestoAppoggio("Modifica");  
        resultCount = newTic.update(db);
        String[] nc_formali=context.getRequest().getParameterValues("Provvedimenti1");
        String[] nc_significative=context.getRequest().getParameterValues("Provvedimenti2");
        String[] nc_gravi=context.getRequest().getParameterValues("Provvedimenti3");
        newTic.updateNonConformita(db, nc_formali, nc_significative, nc_gravi);
        
        if (resultCount == 1) {
          newTic.queryRecord(db, newTic.getId());
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
        return (executeCommandDettaglio(context));
      }
      return getReturn(context, "Update");
    }
    return (executeCommandModify(context));
  }
  
  
  
  public String executeCommandDettaglio(ActionContext context) {
	   
	    Connection db = null;
	    org.aspcfs.modules.osservazioni.base.Osservazioni newTic = null;
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
	      newTic = new org.aspcfs.modules.osservazioni.base.Osservazioni();
	      newTic.queryRecord(db, Integer.parseInt(ticketId));

	      //find record permissions for portal users
	      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
	        return ("PermissionError");
	      }

	     
	      Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")) );
	      LookupList Provvedimenti = new LookupList(db, "lookup_osservazioni");
	      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
	      Provvedimenti.removeElementByLevel(9);
	      context.getRequest().setAttribute("Osservazioni", Provvedimenti);
	      
	      LookupList NonConformitaAmministrative = new LookupList(db, "lookup_nonconformita_amministrative");
	      NonConformitaAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("NonConformitaAmministrative", NonConformitaAmministrative);
	      
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList NonConformitaPenali = new LookupList(db, "lookup_nonconformita_penali");
	      NonConformitaPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("NonConformitaPenali", NonConformitaPenali);
	      
	      LookupList NonConformita = new LookupList(db, "lookup_nonconformita");
	      NonConformita.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("NonConformita", NonConformita);
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute("TicketDetails", newTic);
	    addRecentItem(context, newTic);
	    addModuleBean(context, "ViewTickets", "View Tickets");
	    
	    String retPage = "DettaglioOK";
	    String tipo_richiesta = newTic.getTipo_richiesta();
	    tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
	    
	    retPage = "DettaglioOK";
	    
	  
	    return ( retPage );
	  }
  
  
  
  
  
  
  public String executeCommandChiudi(ActionContext context)
  {
	  	   
	  	    
	  	 
	  	  if(context.getRequest().getParameter("idMacchinetta")!=null)
				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
				else
					context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));   
	  	    
	    int resultCount = -1;
	    Connection db = null;
	    org.aspcfs.modules.osservazioni.base.Osservazioni thisTicket = null;
	    try {
	      db = this.getConnection(context);
	      thisTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(
	          db, Integer.parseInt(context.getRequest().getParameter("id")));
	      org.aspcfs.modules.osservazioni.base.Osservazioni oldTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, thisTicket.getId());
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
	        return ("PermissionError");
	      }
	
	      
	      String ticketId = context.getRequest().getParameter("id");
	      
	      int passedId = thisTicket.getOrgId();

	  	    
	    
	      resultCount = thisTicket.chiudi(db);
	      
	      
	      /*
			 * Inifio Giuseppe
			 * 
			 */
			

				   
				
			context.getRequest().setAttribute("chiusura", "OK");
			
			
			/**
			 * 
			 * Fine Giuseppe
			 */
	      org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(thisTicket.getIdControlloUfficiale()));
	      context.getRequest().setAttribute("TicketDetails", cu);
	      context.getRequest().setAttribute("idNodo", cu.getIdMacchinetta());
	      return "InsertOK";
	      
//	      if (resultCount == -1) {
//	        return ( executeCommandTicketDetails(context));
//	      } else if (resultCount == 1) {
//	        thisTicket.queryRecord(db, thisTicket.getId());
//	        this.processUpdateHook(context, oldTicket, thisTicket);
//	        return (executeCommandTicketDetails(context));
//	      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
//	    context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
//	    return ("UserError");
	  
	  
	  }

}
