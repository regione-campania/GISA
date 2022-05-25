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
package org.aspcfs.modules.distributori.actions;

import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcf.modules.controlliufficiali.action.Sequestri;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.distributori.base.*;
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



import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.sequestri.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountSequestri extends CFSModule {

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
    if (!hasPermission(context, "distributori-distributori-sequestri-edit")) {
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
 context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
      
      Distrubutore d=new Distrubutore();
      Distrubutore dd=d.loadDistributore(thisTicket.getOrgId(), Integer.parseInt(context.getRequest().getParameter("idMacchinetta")), db);
      
      if(dd!=null){
    	 
    	  context.getRequest().setAttribute("aslMacchinetta",""+dd.getAslMacchinetta());
    	  
      }
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
	  if (!hasPermission(context, "distributori-distributori-sequestri-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    
	    String retPag = "" ;

	    try {
	      db = this.getConnection(context);
	      // Load the ticket
	      Sequestri generic_action = new Sequestri();
	      generic_action.executeCommandModifyTicket(context);
	      
	      retPag = "DettaglioOK";
	      Ticket newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");
	      // Load the organization for the header
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	     
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    //return getReturn(context, "TicketDetails");
	    return retPag;
	  }
  
  /**
   * Confirm the delete operation showing dependencies
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "sequestri-sequestri-delete")) {
      return ("PermissionError");
    }
    if(context.getRequest().getParameter("idMacchinetta")!=null)
		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		else
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    //int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      Ticket ticket = new Ticket(db, Integer.parseInt(id));
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
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountSequestri.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
    if (!hasPermission(context, "distributori-distributori-sequestri-delete")) {
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
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
        return ("PermissionError");
      }
      recordDeleted = thisTic.delete(db, getDbNamePath(context));
      if (recordDeleted) {
        processDeleteHook(context, thisTic);
        //del
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute(
            "refreshUrl", "Distributori.do?command=ViewRichieste&orgId=" + orgId +
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
	  if (!hasPermission(context, "distributori-distributori-sequestri-edit")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    

	    try {
	      db = this.getConnection(context);
	      
	      Sequestri generic_action = new Sequestri();
	      generic_action.executeCommandUpdateTicket(context);
	      
	      Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("OrgDetails", orgDetails);
	      //check permission to record
	      

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
	    if (!hasPermission(context, "distributori-distributori-sequestri-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;
	    try {
	    	
	    	 context.getRequest().setAttribute("aslCU", context.getRequest().getParameter("aslMacchinetta"));
	    	 if(context.getRequest().getParameter("idMacchinetta")!=null)
	    			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
	    			else
	    				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      String idControlloUfficiale = context.getRequest().getParameter("idControllo");
		  
			context.getRequest().setAttribute("idControllo",
					idControlloUfficiale);
				
			context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			
			String id = context.getRequest().getParameter("idC");
	    	context.getRequest().setAttribute("idC",
	    			id);
	    	
	    	String id2 = context.getRequest().getParameter("idNC");
	    	context.getRequest().setAttribute("idNC",
	    			id2);
			String identificativoNC = context.getRequest().getParameter("identificativo");
			context.getRequest().setAttribute("identificativoNC",
					identificativoNC);
			
			
	      LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
	     

	      SequestroDi.addItem(-1, "-- SELEZIONA VOCE --");

	      context.getRequest().setAttribute("SequestroDi", SequestroDi);
	      
	      
	      
	      
	      LookupList EsitiSequestri = new LookupList(db, "lookup_esiti_sequestri");
		     

	      EsitiSequestri.addItem(-1, "-- SELEZIONA VOCE --");

	      context.getRequest().setAttribute("EsitiSequestri", EsitiSequestri);
	      
	      
	      
	      
	      
	      LookupList SequestroDiStabilimento = new LookupList(db, "lookup_sequestri_amministrative_stabilimento");
	      SequestroDiStabilimento.setMultiple(true);
	      SequestroDiStabilimento.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiStabilimento", SequestroDiStabilimento);
	      
	      
	      LookupList SequestroDiLocali = new LookupList(db, "lookup_sequestri_amministrative_locali");
	      SequestroDiLocali.setMultiple(true);
	      SequestroDiLocali.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiLocali", SequestroDiLocali);
	      
	      
	      LookupList SequestroDiAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_attrezzature");
	      SequestroDiAttrezzature.setMultiple(true);
	      SequestroDiAttrezzature.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiAttrezzature", SequestroDiAttrezzature);
	      
	      
	      LookupList SequestroDiAlimentiorigineAnimale = new LookupList(db, "lookup_sequestri_amministrative_alimentiorigineanimale");
	      SequestroDiAlimentiorigineAnimale.setMultiple(true);
	      SequestroDiAlimentiorigineAnimale.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiAlimentiorigineAnimale", SequestroDiAlimentiorigineAnimale);
	      
	      
	      LookupList SequestroDiAlimentiorigineVegetale = new LookupList(db, "lookup_sequestri_amministrative_alimentioriginevegetale");
	      SequestroDiAlimentiorigineVegetale.setMultiple(true);
	      SequestroDiAlimentiorigineVegetale.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiAlimentiorigineVegetale", SequestroDiAlimentiorigineVegetale);
	      
	      
	      LookupList SequestroDiAnimali = new LookupList(db, "lookup_sequestri_amministrative_animali");
	      SequestroDiAnimali.setMultiple(true);
	      SequestroDiAnimali.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiAnimali", SequestroDiAnimali);
	      
	      
	      LookupList SequestroDiLocalieAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_locali_attrezzature");
	      SequestroDiLocalieAttrezzature.setMultiple(true);
	      SequestroDiLocalieAttrezzature.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDiLocalieAttrezzature", SequestroDiLocalieAttrezzature);
	      
	      
	      LookupList SequestroDivegetaleEanimale = new LookupList(db, "lookup_sequestri_amministrative_vegetale_animale");
	      SequestroDivegetaleEanimale.setMultiple(true);
	      SequestroDivegetaleEanimale.setSelectSize(5);
	      
	      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestroDivegetaleEanimale", SequestroDivegetaleEanimale);
	      
	      
	      
	      
	      
	      
	      
	      LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
	    AzioneNonConforme.setMultiple(true);
	    AzioneNonConforme.setSelectSize(7);
	      context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);
	      
	      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
	      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Sequestri", Sequestri);

	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      SiteIdList.addItem(-2, "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      //Load the organization
	      Organization thisOrganization = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      
	      if (context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter(
	          "contact") != null && context.getRequest().getParameter("contact").equals(
	          "on"))) {
	        newTic = (Ticket) context.getFormBean();
	      
	      } else {
	        newTic = new Ticket();
	      }
	      
	      //aggiunte queste istruzioni
	      String temporgId = context.getRequest().getParameter("orgId");
	      int tempid = Integer.parseInt(temporgId);
	      Organization newOrg = new Organization(db, tempid);
    //istruzioni aggiunte
	     
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
	    return ("AddOK");
	  }

  
  public String executeCommandInsert(ActionContext context) throws SQLException {
	    if (!(hasPermission(context, "distributori-distributori-sequestri-add"))) {
	      return ("PermissionError");
	    }
	    int idMacc=-1;
	    if(context.getRequest().getParameter("idMacchinetta")!=null){
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
			idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta"));
	    }else
				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean contactRecordInserted = false;
	    boolean isValid = true;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Ticket newTicket = null;
	    String id = context.getRequest().getParameter("idC");
    	context.getRequest().setAttribute("idC",
    			id);
    	
    	String id2 = context.getRequest().getParameter("idNC");
    	context.getRequest().setAttribute("idNC",
    			id2);
	    String newContact = context.getRequest().getParameter("contact");

	    Ticket newTic = (Ticket) context.getFormBean();
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
	    newTic.setPunteggio(25);
	    newTic.setEsitoSequestro(Integer.parseInt( context.getRequest().getParameter("esitoSequestro")));  
	    newTic.setDescrizione(context.getRequest().getParameter("descrizione"));
	    String site =  context.getRequest().getParameter("siteId");
	    if(site.equals("201")){
            site = "AV";        
    }else if(site.equals("202")){
            site = "BN";
    }else if(site.equals("203")){
            site = "CE";
    }else if(site.equals("204")){
            site = "NA1";
    }else if(site.equals("205")){
            site = "NA2N";
    }else if(site.equals("206")){
            site = "NA3S";
    }else if(site.equals("207")){
            site = "SA";
    }else{
             if(site.equals("16")){
                            site = "FuoriRegione";
                    }
    }
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String idC = context.getRequest().getParameter("idC");
		//newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
		newTic.setId_nonconformita(Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")));
		
		String idCampione = site+idControllo;
	    newTic.setNoteSequestrodi(context.getRequest().getParameter("notesequestridi"));
		newTic.setIdentificativonc(context.getRequest().getParameter("identificativoNC"));
	    newTic.setEnteredBy(getUserId(context));
	    newTic.setModifiedBy(getUserId(context));
	    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
	    newTic.setCodiceArticolo(Integer.parseInt(context.getRequest().getParameter("articolo")));
	    String oggettiSequestrati=  context.getRequest().getParameter("SequestroDi");
        newTic.setSequestroDi(Integer.parseInt(oggettiSequestrati));
	    
	

	    try {
	      db = this.getConnection(context);
	      Distrubutore d=new Distrubutore();
	      Distrubutore dd=d.loadDistributore(newTic.getOrgId(), idMacc, db);
	      
	      if(dd!=null){
	    	  context.getRequest().setAttribute("aslMacchinetta",""+dd.getAslMacchinetta());
	    	  
	      }
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      
	      LookupList SequestriAmministrative = new LookupList(db, "lookup_sequestri_amministrative");
	      SequestriAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestriAmministrative", SequestriAmministrative);
	      
	      LookupList SequestriPenali = new LookupList(db, "lookup_sequestri_penali");
	      SequestriPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestriPenali", SequestriPenali);
	      
	      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
	      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Sequestri", Sequestri);
	     
	        if (newTic.getOrgId() > 0) {
	          //newTic.setsiteid(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
	        }
	        isValid = this.validateObject(context, db, newTic) && isValid;
	        if (isValid) {
	          newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
	          recordInserted = newTic.insert(db,context);
	          String[] oggettiSequestratiLista=null;
              if(newTic.getSequestroDi()==1){
            	     
           	      
           	   oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiStabilimento");
            	  
            	  
              }else{
            	  if(newTic.getSequestroDi()==2){
            		  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAttrezzature");
	            	  
	            	  
	              }else{
	            	  if(newTic.getSequestroDi()==3){
		            	  
	            		  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiLocali");
		            	  
		              }
	            	  else{
	            		  if(newTic.getSequestroDi()==4){
	    	            	  
	            			  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiLocalieAttrezzature");
	    	            	  
	    	              }
	            		  else{
	            			  if(newTic.getSequestroDi()==5){
	        	            	  
	            				  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAnimali");
	        	            	  
	        	              }else{
	        	            	  if(newTic.getSequestroDi()==6){
	        		            	  
	        	            		  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAlimentiorigineAnimale");
	        		            	  
	        		              }else{
	        		            	  if(newTic.getSequestroDi()==7){
	        		            		  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDiAlimentiorigineVegetale");
	        			            	  
	        		            		  
	        			              }else{
	        			            	  if(newTic.getSequestroDi()==8){
	        				            	  
	        			            		  oggettiSequestratiLista=context.getRequest().getParameterValues("SequestroDivegetaleEanimale");
	        				            	  
	        				              }
	        			              }
	        		              }
	        	              }
	            		  }
	            	  }
	              }
            	  
              }
              newTic.insertOggettiSequestratiOAzionenonConforme(db, oggettiSequestratiLista);
	          
	        }
	      

	      if (recordInserted) {

	    	  newTic.setPunteggio("25");
				
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
	  if (!hasPermission(context, "distributori-distributori-sequestri-edit")) {
	      return ("PermissionError");
	    }			
	    Connection db = null;
	   
	    try {
	      db = this.getConnection(context);
	     
	      Sequestri generic_action = new Sequestri();
	      generic_action.executeCommandModifyTicket(context);
	      
	      Ticket newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");
	      
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      //Load the departmentList for assigning
	    
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
	  if (!hasPermission(context, "distributori-distributori-sequestri-edit")) {
	      return ("PermissionError");
	    }	
	    Connection db = null;
	   
	    try {
	      db = this.getConnection(context);
	      Sequestri generic_action = new Sequestri();
	      generic_action.executeCommandModifyTicket(context);
	      
	      Ticket newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");
	      Organization orgDetails = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("OrgDetails", orgDetails);

	      
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	 
	    addModuleBean(context, "ViewTickets", "View Tickets");

	    String retPage = "Modify";
	  
	    
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
    int resultCount = 0;
    int catCount = 0;
    TicketCategory thisCat = null;
    boolean catInserted = false;
    boolean isValid = true;
    Ticket newTic = (Ticket) context.getFormBean();
    Connection db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
    	int idMacc=-1;
	    if(context.getRequest().getParameter("idMacchinetta")!=null){
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
			idMacc=Integer.parseInt(context.getRequest().getParameter("idMacchinetta"));
	    }	else
    				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
      db = this.getConnection(context);
      //check permission to record
      Distrubutore d=new Distrubutore();
      Distrubutore dd=d.loadDistributore(newTic.getOrgId(), idMacc, db);
      
      if(dd!=null){
    	  context.getRequest().setAttribute("aslMacchinetta",""+dd.getAslMacchinetta());
    	  
      }
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      
      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
      
      LookupList SequestriAmministrative = new LookupList(db, "lookup_sequestri_amministrative");
      SequestriAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SequestriAmministrative", SequestriAmministrative);
      
      LookupList SequestriPenali = new LookupList(db, "lookup_sequestri_penali");
      SequestriPenali.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SequestriPenali", SequestriPenali);
      
      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Sequestri", Sequestri);
     
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
      
      //newTic.setsiteid(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
    	  newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria //aggiunto da d.dauria
    	//newTic.setTestoAppoggio("Modifica");  
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
        return (executeCommandDettaglio(context));
      }
      return getReturn(context, "Update");
    }
    return (executeCommandModify(context));
  }
  
  
  
  public String executeCommandDettaglio(ActionContext context) {
	    if (!hasPermission(context, "distributori-distributori-view")) {
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
	     
	      //Load the ticket state
	      
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      
	      LookupList SequestriAmministrative = new LookupList(db, "lookup_sequestri_amministrative");
	      SequestriAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestriAmministrative", SequestriAmministrative);
	      
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList SequestriPenali = new LookupList(db, "lookup_sequestri_penali");
	      SequestriPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SequestriPenali", SequestriPenali);
	      
	      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
	      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Sequestri", Sequestri);
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
	  	    if (!(hasPermission(context, "distributori-distributori-sequestri-edit"))){
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
	      if(context.getRequest().getParameter("idMacchinetta")!=null)
	  		context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
	  		else
	  			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
	      thisTicket.setModifiedBy(getUserId(context));
	      resultCount = thisTicket.chiudi(db);
	      if (resultCount == 0)
			{
				context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito");
			}
	      if (resultCount == -1 || resultCount == 0) {
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
