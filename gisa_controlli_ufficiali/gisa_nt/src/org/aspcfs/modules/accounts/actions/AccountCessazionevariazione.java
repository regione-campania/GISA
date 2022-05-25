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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
import org.aspcfs.modules.troubletickets.base.TicketCategoryAssignment;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.modules.cessazionevariazione.base.Ticket;
import org.aspcfs.utils.DataVolturaException;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountCessazionevariazione extends CFSModule {

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
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-edit")) {
      return ("PermissionError");
    }
    int resultCount = -1;
    Connection db = null;
    Ticket thisTicket = null;
    Ticket oldTicket = null;
    try {
      db = this.getConnection(context);
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      oldTicket = new Ticket(db, thisTicket.getId());
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
        return ("PermissionError");
      }
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      thisTicket.queryRecord(db, thisTicket.getId());
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
   * Load the ticket details tab
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandTicketDetails(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    String retPag = null;
    try {
      db = this.getConnection(context);
      // Load the ticket
      Ticket newTic = new Ticket();
      SystemStatus systemStatus = this.getSystemStatus(context);
      newTic.setSystemStatus(systemStatus);
      newTic.queryRecord(db, Integer.parseInt(ticketId));

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
 
      
    LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);

     
      LookupList countryList = new LookupList(db, "lookup_nazioni");
	countryList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("CountryList", countryList);
    
      context.getRequest().setAttribute("TicketDetails", newTic);
      
      retPag = "Details_" + newTic.getTipo_richiesta() + "OK";
      
      addRecentItem(context, newTic);
      // Load the organization for the header
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      addModuleBean(context, "View Accounts", "View Tickets");
      // Reset any pagedLists since this could be a new visit to this ticket
      deletePagedListInfo(context, "AccountTicketsFolderInfo");
      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
      deletePagedListInfo(context, "AccountTicketTaskListInfo");
      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
    
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //return getReturn(context, "TicketDetails");
    return retPag;
  }

  public String executeCommandCessazionevariazioneDetails(ActionContext context) {
	    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    //Parameters
	    String ticketId = context.getRequest().getParameter("id");
	    try {
	      db = this.getConnection(context);
	      // Load the ticket
	      org.aspcfs.modules.cessazionevariazione.base.Ticket newTic = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      newTic.setSystemStatus(systemStatus);
	      newTic.queryRecord(db, Integer.parseInt(ticketId));

	      //find record permissions for portal users
	      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
	        return ("PermissionError");
	      }
	     

	     LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
	      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoLocale", TipoLocale);
	      
	      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
	      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
	      
	     
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	     
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      addRecentItem(context, newTic);
	      // Load the organization for the header
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      addModuleBean(context, "View Accounts", "View Tickets");
	      // Reset any pagedLists since this could be a new visit to this ticket
	      deletePagedListInfo(context, "AccountTicketsFolderInfo");
	      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
	      deletePagedListInfo(context, "AccountTicketTaskListInfo");
	      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
	    
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "TicketDetails");
	  }

  /**
   * Confirm the delete operation showing dependencies
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-delete")) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Ticket ticket = new Ticket(db, Integer.parseInt(id));
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
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountCessazionevariazione.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-delete")) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.delete(db, getDbNamePath(context));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute(
            "refreshUrl", "Accounts.do?command=ViewCessazionevariazione&orgId=" + orgId +
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTrashTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Connection db = null;
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      Organization newOrg = new Organization(db, orgId);
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordUpdated = thisTic.updateStatus(db, true, this.getUserId(context));
      if (recordUpdated) {
        processDeleteHook(context, thisTic);
        context.getRequest().setAttribute("OrgDetails", newOrg);
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute(
            "refreshUrl", "Accounts.do?command=ViewTickets&orgId=" + orgId +
            (inline != null && "inline".equals(inline) ? "&popup=true" : ""));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated) {
      return ("DeleteTicketOK");
    } else {
      return (executeCommandTicketDetails(context));
    }
  }


 


  /**
   * Loads the ticket for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-edit")) {
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
        newTic = new Ticket(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
      }
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
      //Load the organization
      Organization thisOrganization = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", thisOrganization);
      //Load the departmentList for assigning
     
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      LookupList countryList = new LookupList(db, "lookup_nazioni");
    		// SOLO PAESI EUROPEI
    		countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
    		// ITALIA NON SELEZIONABILE
    		countryList.removeElementByValue("Italia");
    		countryList.addItem(-1, "-- SELEZIONA VOCE --");
    		context.getRequest().setAttribute("CountryList", countryList);
  
    

      //Put the ticket in the request
      addRecentItem(context, newTic);
      context.getRequest().setAttribute("TicketDetails", newTic);
      addModuleBean(context, "View Accounts", "View Tickets");

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
      thisOrganization.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //return "ModifyautorizzazionetrasportoanimaliviviOK";
    return "Modifica_" + newTic.getTipo_richiesta() + "OK";
    //return getReturn(context, "ModifyTicket");
  }
 
 public String executeCommandAddTicket(ActionContext context) {
   if (!hasPermission(context, "accounts-accounts-cessazionevariazione-add")) {
     return ("PermissionError");
   }
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
     
     SystemStatus systemStatus = this.getSystemStatus(context);
     
     LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
     TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
     context.getRequest().setAttribute("TipoLocale", TipoLocale);
     
     LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
     TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
     context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
     
     LookupList countryList = new LookupList(db, "lookup_nazioni");
		// SOLO PAESI EUROPEI
		countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
		// ITALIA NON SELEZIONABILE
		countryList.removeElementByValue("Italia");
		countryList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("CountryList", countryList);
		
     LookupList SiteIdList = new LookupList(db, "lookup_site_id");
     SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
     context.getRequest().setAttribute("SiteIdList", SiteIdList);
     
     if (!isRecordAccessPermitted(context, db, tempid)) {
       return ("PermissionError");
     }
     //Organization for header
     newOrg = new Organization(db, tempid);
     //Ticket
     newTic = (Ticket) context.getFormBean();
     if (context.getRequest().getParameter("refresh") != null ||
         (context.getRequest().getParameter("contact") != null &&
             context.getRequest().getParameter("contact").equals("on"))) {
     } else {
       newTic.setOrgId(tempid);
     }
     newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
     addModuleBean(context, "View Accounts", "Add a Ticket");
     context.getRequest().setAttribute("OrgDetails", newOrg);

     //getting current date in mm/dd/yyyy format
     String currentDate = getCurrentDateAsString(context);
     context.getRequest().setAttribute("currentDate", currentDate);
     context.getRequest().setAttribute(
         "systemStatus", this.getSystemStatus(context));
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

 
 public String executeCommandInsertTicket(ActionContext context) {
	    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-add")) {
	      return ("PermissionError");
	    }
	    context.getRequest().setAttribute("dataVolturaError", null);
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean contactRecordInserted = false;
	    boolean isValid = true;
	    
	    Ticket newTicket = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Parameters
	    String newContact = context.getRequest().getParameter("contact");
	    //Process the submitted ticket
	    Ticket newTic = (Ticket) context.getFormBean();
	    newTic.setOperazione("Voltura");
	    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);

	    newTic.setEnteredBy(getUserId(context));
	    newTic.setModifiedBy(getUserId(context));
	    //Insert a new contact if specified
	    
	    try {
	    	
	    	if(newTic.getAssignedDate() == null){
	    		throw new DataVolturaException("Data voltura mancante");
	    	}
	    	
	      db = this.getConnection(context);

	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
	      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoLocale", TipoLocale);
	      
	      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
	      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
	      
	  
	      //Display account name in the header
	      String temporgId = context.getRequest().getParameter("orgId");
	      int tempid = Integer.parseInt(temporgId);

	  

	      //Check if portal user can insert this record
	      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
	        return ("PermissionError");
	      }

	      Organization newOrg = new Organization(db, tempid);
	      
	      
	      org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
	      String idV= newOrg.getIdVoltura(db, newOrg.getOrgId());
	      if(idV==null){
	         
	     Ticket datiIniziali = new Ticket();
//	     datiIniziali.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
	     datiIniziali.setOperazione("Avvio Attivita");
	     datiIniziali.setOrgId(newOrg.getOrgId());
	     datiIniziali.setModifiedBy(getUserId(context));
	     datiIniziali.setEnteredBy(getUserId(context));
	     datiIniziali.setName(newOrg.getName());
	     datiIniziali.setBanca(newOrg.getBanca());
	     datiIniziali.setPartitaIva(newOrg.getPartitaIva());
	     datiIniziali.setTipo_richiesta("autorizzazione_trasporto_animali_vivi");
	     datiIniziali.setAssignedDate((newOrg.getDataPresentazione() != null) ? newOrg.getDataPresentazione() : newOrg.getDate1());
	     datiIniziali.setClosed(new Timestamp(System.currentTimeMillis()));
	     datiIniziali.setIpEntered(ip);
	     datiIniziali.setIpModified(ip);
	     if(context.getRequest().getParameter("cfPrecedente")!=null && !context.getRequest().getParameter("cfPrecedente").equals("")){
	    	 datiIniziali.setCodiceFiscaleRappresentante(context.getRequest().getParameter("cfPrecedente"));
	         }
	     else{
	    	 datiIniziali.setCodiceFiscaleRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("nomePrecedente")!=null && !context.getRequest().getParameter("nomePrecedente").equals("")){
	    	 datiIniziali.setNomeRappresentante(context.getRequest().getParameter("nomePrecedente"));
	     }else{
	    	 datiIniziali.setNomeRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("cognomePrecedente")!=null && !context.getRequest().getParameter("cognomePrecedente").equals("")){
	    	 datiIniziali.setCognomeRappresentante(context.getRequest().getParameter("cognomePrecedente"));
	     }else{
	    	 datiIniziali.setCognomeRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("dnPrecedente")!=null && !context.getRequest().getParameter("dnPrecedente").equals("null")){
	    	 datiIniziali.setDataNascitaRappresentante(context.getRequest().getParameter("dnPrecedente"));
	     }else{
	    	 datiIniziali.setDataNascitaRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("lnPrecedente")!=null && !context.getRequest().getParameter("lnPrecedente").equals("null")){
	    	 datiIniziali.setLuogoNascitaRappresentante(context.getRequest().getParameter("lnPrecedente"));
	     }else{
	    	 datiIniziali.setLuogoNascitaRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("ePrecedente")!=null && !context.getRequest().getParameter("ePrecedente").equals("null")){
	    	 datiIniziali.setEmailRappresentante(context.getRequest().getParameter("ePrecedente"));
	         }
	     else{
	    	 datiIniziali.setEmailRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("tPrecedente")!=null && !context.getRequest().getParameter("tPrecedente").equals("null")){
	    	 datiIniziali.setTelefonoRappresentante(context.getRequest().getParameter("tPrecedente"));
	     }else{
	    	 datiIniziali.setTelefonoRappresentante(" ");
	     }
	     if(context.getRequest().getParameter("faxPrecedente")!=null && !context.getRequest().getParameter("faxPrecedente").equals("null")){
	    	 datiIniziali.setFax(context.getRequest().getParameter("faxPrecedente"));
	     }else{
	    	 datiIniziali.setFax(" ");
	     }
	     datiIniziali.insert(db,context);
	      }

	      newOrg.setName(newTic.getName());
	      newOrg.setBanca(newTic.getBanca());
	      newOrg.setPartitaIva(newTic.getPartitaIva());
	      newOrg.setNomeRappresentante(newTic.getNomeRappresentante());
	      newOrg.setCognomeRappresentante(newTic.getCognomeRappresentante());
	      newOrg.setCodiceFiscaleRappresentante(newTic.getCodiceFiscaleRappresentante());
	      newOrg.setDataNascitaRappresentante(newTic.getDataNascitaRappresentante());
	      newOrg.setLuogoNascitaRappresentante(newTic.getLuogoNascitaRappresentante());
	      String fax = newTic.getFax();
	      String telefono = newTic.getTelefonoRappresentante();
	      newOrg.setFax(fax.toString());
	      newOrg.setDataPresentazione(newTic.getAssignedDate());
	      newOrg.setTelefonoRappresentante(telefono.toString());
	      newOrg.setEmailRappresentante(newTic.getEmailRappresentante());
	      newOrg.setCodiceFiscale(context.getParameter("codiceFiscale"));
	      
	      //PAESE DI PROVENIENZA
	  		String provenienza = context.getRequest().getParameter("provenienza");
	  		if (provenienza != null && provenienza.equals("ESTERO"))
	  			newOrg.setIdNazione( context.getRequest().getParameter("country"));
	  		else
	  			newOrg.setIdNazione(106); //ITALIA
	  		
	  		if(context.getParameter("no_piva")!= null && context.getParameter("no_piva").equalsIgnoreCase("on"))
		    	newOrg.setNo_piva(true);
			else
				newOrg.setNo_piva(false);
	      
	      newOrg.updateVoltura(db, tempid);
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      
	      
	        if (newTic.getOrgId() > 0) {
	          newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
	        }
	        isValid = this.validateObject(context, db, newTic) && isValid;
	        if (isValid) {
	          recordInserted = newTic.insert(db,context);
	        }
	      
	      if (recordInserted) {
	        //Reload the ticket for the details page... redundant to do here
	        newTicket = new Ticket(db, newTic.getId());
	        context.getRequest().setAttribute("TicketDetails", newTicket);

	     


	      }
	      addModuleBean(context, "View Accounts", "Ticket Insert ok");
	      Integer idtampone = newTic.getId();
		
	    } 
	    catch (DataVolturaException dve) {
	    	context.getRequest().setAttribute("dataVolturaError", dve);
	    	return (executeCommandAddTicket(context));
	    }
	    catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    if (recordInserted) {
	      //return getReturn(context, "InsertTicket");
	    	return "Details_" + newTicket.getTipo_richiesta() + "OK";
	    }
	    return (executeCommandAddTicket(context));
	  }
  /**
   * Update the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateTicket(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-cessazionevariazione-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;

    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;

    Ticket newTic = (Ticket) context.getFormBean();
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newTic.setIpEntered(ip);
    newTic.setIpModified(ip);
    

    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      
      /*LookupList titoloList = new LookupList(db, "lookup_titolo_list");
      titoloList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TitoloList", titoloList);*/
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      LookupList countryList = new LookupList(db, "lookup_nazioni");
      countryList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CountryList", countryList);
		
      
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      orgDetails.setName(newTic.getName());
      orgDetails.setBanca(newTic.getBanca());
      orgDetails.setNomeRappresentante(newTic.getNomeRappresentante());
      orgDetails.setCognomeRappresentante(newTic.getCognomeRappresentante());
      orgDetails.setCodiceFiscaleRappresentante(newTic.getCodiceFiscaleRappresentante());
      orgDetails.setDataNascitaRappresentante(newTic.getDataNascitaRappresentante());
      orgDetails.setLuogoNascitaRappresentante(newTic.getLuogoNascitaRappresentante());
      String fax = newTic.getFax();
      String telefono = newTic.getTelefonoRappresentante();
      orgDetails.setPartitaIva(newTic.getPartitaIva());
      orgDetails.setFax(fax.toString());
      orgDetails.setTelefonoRappresentante(telefono.toString());
      orgDetails.setEmailRappresentante(newTic.getEmailRappresentante());
      orgDetails.setCodiceFiscale(context.getParameter("codiceFiscale"));
      //PAESE DI PROVENIENZA
		String provenienza = context.getRequest().getParameter("provenienza");
		if (provenienza != null && provenienza.equals("ESTERO"))
			orgDetails.setIdNazione( context.getRequest().getParameter("country"));
		else
			orgDetails.setIdNazione(106); //ITALIA
		
		if(context.getParameter("no_piva")!= null && context.getParameter("no_piva").equalsIgnoreCase("on"))
			orgDetails.setNo_piva(true);
		else
			orgDetails.setNo_piva(false);
      
      orgDetails.updateVoltura(db, newTic.getOrgId());

      context.getRequest().setAttribute("OrgDetails", orgDetails);
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }
     
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
      newTic.setModifiedBy(getUserId(context));
      newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      newTic.setPartitaIva(context.getRequest().getParameter("partitaIva"));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
        resultCount = newTic.update(db);
      }
     
      Integer idtampone = newTic.getId();
		
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

  
    	return "Details_" + context.getRequest().getParameter("tipo_richiesta") + "OK";
   
  }


  public String executeCommandChiudi(ActionContext context)
  {
	  	    if (!(hasPermission(context, "accounts-accounts-cessazionevariazione-edit"))){
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
	        return ( executeCommandTicketDetails(context));
	      } else if (resultCount == 1) {
	        thisTicket.queryRecord(db, thisTicket.getId());
	        this.processUpdateHook(context, oldTicket, thisTicket);
	        return (executeCommandTicketDetails(context));
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

}
