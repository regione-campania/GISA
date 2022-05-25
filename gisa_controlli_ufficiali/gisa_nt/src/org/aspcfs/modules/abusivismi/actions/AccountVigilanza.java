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
package org.aspcfs.modules.abusivismi.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;

import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.abusivismi.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.allerte.base.AslCoinvolte;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;

import java.text.ParseException;
import java.util.*;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.abusivismi.base.Organization;
import org.aspcfs.modules.abusivismi.base.OrganizationHistory;
import org.aspcfs.modules.abusivismi.base.OrganizationHistoryList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;


/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountVigilanza extends CFSModule {

  /**
   * Sample action for prototyping, by including a specified page
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
	
	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSupervisiona(ActionContext context) {
		
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;



		


		try {


			db = this.getConnection(context);

			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandSupervisiona(context, db);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandTicketDetails(context));
	}
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return getReturn(context, "Include");
  }
  public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "abusivismi-abusivismi-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

		
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
			
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
  /**
   * Re-opens a ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-edit")) {
      return ("PermissionError");
    }
    int resultCount = -1;
    Connection db = null;
    Ticket thisTicket = null;
    Ticket oldTicket = null;
    try {
		db = this.getConnection(context);
		org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
		controlliGeneric.executeCommandReopenTicket(context,db);
		resultCount = (Integer)context.getRequest().getAttribute("resultCount");

	} catch (Exception errorMessage) {
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	if (resultCount == -1) {
		return (executeCommandTicketDetails(context));
	} else if (resultCount == 1) {
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
    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-add")) {
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



  public String executeCommandTicketDetails(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    //Parameters
	    String ticketId = context.getRequest().getParameter("id");
	    String retPag = null;
	    try {
	      db = this.getConnection(context);
	      // Load the ticket

	      Organization orgDetail = new Organization(db,Integer.parseInt(context.getRequest().getParameter("orgId")));
	      context.getRequest().setAttribute("OrgDetails", orgDetail);      
	      
	      context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia",4);
	     context.getRequest().setAttribute("tipoDest",null);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandTicketDetails(context,db);

	      retPag = "DettaglioOK";
	      
	      
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
    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-delete")) {
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
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AbusivismiVigilanza.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-delete")) {
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
            "refreshUrl", "Abusivismi.do?command=ViewVigilanza&orgId=" + orgId +
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
  /**
   * Update the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateTicket(ActionContext context) {
    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = 0;
    boolean isValid = true;

    
    Ticket newTic = (Ticket) context.getFormBean();
    
    if(context.getRequest().getParameter("codici_selezionabili")!=null){
    	newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
    }
    
    newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));

    if(context.getRequest().getParameter("ncrilevate").equals("1"))
  	  newTic.setNcrilevate(true);
    else
  	  newTic.setNcrilevate(false);
    
    
    newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
    
    try {


      db = this.getConnection(context);
      Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
      
      context.getRequest().setAttribute("siteId",orgDetails.getSiteId());
      context.getRequest().setAttribute("tipologia",4);
     context.getRequest().setAttribute("tipoDest",null);
      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
      controlliGeneric.executeCommandUpdateTicket(context,db);
      context.getRequest().setAttribute("OrgDetails", orgDetails);
      resultCount = (Integer) context.getRequest().getAttribute("resultCount");
      isValid = (Boolean) context.getRequest().getAttribute("isValid");
		if (!isValid)
		{
			return executeCommandModifyTicket(context);
		}
      
    } catch (Exception e) {
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    if (resultCount == 1 && isValid) {
      //return "UpdateOK";
    	return "DettaglioOK";
    }
    return (executeCommandDettaglio(context));
  }

  public String executeCommandAdd(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;
	    String popup = "";
	    try {
	      db = this.getConnection(context);
	      
	      //aggiunte queste istruzioni
	      String temporgId = context.getRequest().getParameter("orgId");
	      int tempid = Integer.parseInt(temporgId);
	      Organization newOrg = new Organization(db, tempid);
	      
	      context.getRequest().setAttribute("siteId", newOrg.getSiteId());
	      context.getRequest().setAttribute("tipologia", 4);
	      context.getRequest().setAttribute("tipoDest", null);
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      c.executeCommandAdd(context,db);
	      
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
	      
	     

	      if (	context.getRequest().getParameter("refresh") != null || (context.getRequest().getParameter("contact") != null && 
	    		context.getRequest().getParameter("contact").equals(
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
	    	e.printStackTrace();
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
	    
	    context.getRequest().setAttribute("popup", popup);
	    if(context.getRequest().getParameter("popup") != null && context.getRequest().getParameter("popup") != ""){
	    	return ("PopupAddOK");
	    }
	    else {
	    	 UserBean user = (UserBean)context.getSession().getAttribute("User");
			 String nameContext=context.getRequest().getServletContext().getServletContextName();
				
					if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
					return getReturn(context, "AddTipo2");
				return getReturn(context, "Add");
	    }
	    
	  }

  public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
	    if (!(hasPermission(context, "abusivismi-abusivismi-vigilanza-add"))) 
	    {
	      return ("PermissionError");
	    }
	    context.getRequest().setAttribute("reload", "false");
	    String retPage = "InsertOK";
	   
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean contactRecordInserted = false;
	    boolean isValid = true;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    
	    Ticket newTicket = null;
	    String popup = "";
	   try
	   {
	    db = this.getConnection(context);
	    String newContact = context.getRequest().getParameter("contact");
	    String save = context.getRequest().getParameter("Save");
	    Ticket newTic = (Ticket) context.getFormBean();
	   
	   
	      Organization thisOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      newTic.setCompanyName(thisOrg.getName());
	      
        context.getRequest().setAttribute("OrgDetails", thisOrg);
        context.getRequest().setAttribute("name", thisOrg.getName());
        org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
        controlliGeneric.executeCommandInsert(context,db);
       
        
       
        recordInserted = (Boolean) context.getRequest().getAttribute("inserted");
        isValid = (Boolean) context.getRequest().getAttribute("isValid");
	    if (recordInserted && isValid) {
	      
	     
	      String tipo_richiesta = newTic.getTipo_richiesta();
	      tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
	     
	      retPage = "InsertOK";
	    }
	    else
	    {
	    	return executeCommandAdd(context);
	    }
	    
	   }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	this.freeConnection(context, db);
	    }
	    
	    if(context.getRequest().getParameter("popup") != null && context.getRequest().getParameter("popup") != ""){
	    	return ("PopupCloseOK");
	    }
	    
	    
	      return ( retPage );//("InsertOK"); 
	    }
  
  
  public String executeCommandUploadListaDistribuzione(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    
	    try {
	    	
	    String filePath = this.getPath(context, "accounts");
	    //Process the form data
	     HttpMultiPartParser multiPart = new HttpMultiPartParser();
	     multiPart.setUsePathParam(false);
	     multiPart.setUseUniqueName(true);
	     multiPart.setUseDateForFolder(true);
	     multiPart.setExtensionId(getUserId(context));
	     HashMap parts = multiPart.parseData(context.getRequest(), filePath);
	     context.getRequest().setAttribute("parts", parts);
	      db = this.getConnection(context);
	      String temporgId = (String)parts.get("orgId");
	      int tempid = Integer.parseInt(temporgId);
	      Organization newOrg = new Organization(db, tempid);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      
	      context.getRequest().setAttribute("siteId", newOrg.getSiteId());
	      context.getRequest().setAttribute("tipologia", 4);
	      context.getRequest().setAttribute("tipoDest", null);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric =new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandUploadListaDistribuzione(context,db);
	      //aggiunte queste istruzioni
	     
	      
	     
	      //Load the organization
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	     
	      String gotoInsert 		=(String)parts.get("gotoPage");
	    if("insert".equals(gotoInsert))
	    {
	    	if (((String)parts.get("actionSource")) != null) {
	    		context.getRequest().setAttribute(
	    				"actionSource", (String)parts.get("actionSource"));
	    		return "AddTicketOK";
	    	}
	    	context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
	    	return ("AddOK");
	    }
	    else
	    {
	    	return "ModifyOK";
	    }
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	  }
  
  public String executeCommandModifyTicket(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-edit")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;

	   
	    try 
	    {
	      db = this.getConnection(context);
	      String ticketId = context.getRequest().getParameter("id");
	      
	    if (context.getRequest().getParameter("companyName") == null) 
	    {
	    	newTic = new Ticket(db, Integer.parseInt(ticketId));
	    	        
	    } else 
	    {
	    	newTic = (Ticket) context.getFormBean();
	    }
	    
	  //gestione della modifica del controllo ufficale
		boolean isModificabile = true;
		PreparedStatement pst = db.prepareStatement("select * from get_lista_sottoattivita(?, ?, ?)");
		pst.setInt(1, newTic.getId());
		pst.setInt(2, 2);
		pst.setBoolean(3, false);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			int idCampione = rs.getInt("ticketid");
			//Preaccettazione
			DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
			String result = dwrPreacc.Preaccettazione_RecuperaCodPreaccettazione(String.valueOf(idCampione));
			JSONObject jsonObj;
			jsonObj = new JSONObject(result);
			String codicePreaccettazione = jsonObj.getString("codice_preaccettazione");
			if (!codicePreaccettazione.equalsIgnoreCase("")){
				isModificabile = false;
			}
			
		}
		
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		if (user.getUserRecord().getRoleId()!=Role.RUOLO_ORSA && user.getUserRecord().getRoleId()!=Role.HD_1LIVELLO && user.getUserRecord().getRoleId()!=Role.HD_2LIVELLO ){
			if (!isModificabile){
				context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
				return(executeCommandTicketDetails(context));
			}
		}
//		
//		if (!isModificabile){
//			context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
//			return(executeCommandTicketDetails(context));
//		}
		//gestione della modifica del controllo ufficiale
	    
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("siteId", thisOrganization.getSiteId());
	      context.getRequest().setAttribute("tipologia", 4);
	      context.getRequest().setAttribute("tipoDest", null);
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandModifyTicket(context,db);

	    } catch (Exception errorMessage) {
	    	errorMessage.printStackTrace();
	    	errorMessage.printStackTrace();
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    //return "ModifyautorizzazionetrasportoanimaliviviOK";
	    UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
		 if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "ModifyTipo2");
			return getReturn(context, "Modify");	    //return getReturn(context, "ModifyTicket");
	  }
  
  public String executeCommandViewVigilanza(ActionContext context) {
  
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-view")) {
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
	        "AccountVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
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
	      addModuleBean(context, "View Accounts", "Accounts View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "ViewVigilanza");
	  }
  
  
  
  
  
  
  public String executeCommandDettaglio(ActionContext context) {
	    if (!hasPermission(context, "abusivismi-abusivismi-vigilanza-view")) {
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
	    

	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_sanzioni_amministrative");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      
	      
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
	      SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);
	      
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
  
  
  
  
	public String executeCommandChiudiTutto(ActionContext context) {
		// public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "abusivismi-abusivismi-vigilanza-edit"))) {
			return ("PermissionError");
		}

		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		Ticket oldTicket = null;
		try {
			db = this.getConnection(context);

//			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
//			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			
			thisTicket =  new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			oldTicket =  thisTicket;

			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
				return ("PermissionError");
			}

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			int flag=controlliGeneric.executeCommandChiudiTutto(context,db, thisTicket, oldTicket);

			if (flag == 4) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
				// return (executeCommandTicketDetails(context));
			}

			context.getRequest().setAttribute("Chiudi", "" + flag); if (flag == 0 || flag == 1 || flag == 3) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			if (resultCount == -1) {
				return (executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				context.getRequest().setAttribute("Chiudi", "" + flag);
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

  
  public String executeCommandChiudi(ActionContext context)
  {
	  	    if (!(hasPermission(context, "abusivismi-abusivismi-vigilanza-edit"))){
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
	      
	      Integer idT = thisTicket.getId();
	      String idCU = idT.toString(); 
	      
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
	        return ("PermissionError");
	      }
	      
	 
	      
 String ticketId = context.getRequest().getParameter("id");
	      
	     
 
 org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
 int passedId = thisTicket.getOrgId();
 ticList.setOrgId(passedId);
 ticList.buildListControlli(db, passedId, ticketId);

 
 org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
 int pasId = thisTicket.getOrgId();
 tamponiList.setOrgId(passedId);
 tamponiList.buildListControlli(db, pasId, ticketId);

	  	    
	      
	      
	      Iterator campioniIterator=ticList.iterator();
	      Iterator tamponiIterator=tamponiList.iterator();
	      
	      
	      
	      int flag=0;
	      while(campioniIterator.hasNext()){
	    	  
	    	  org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();
	    	  
	    	  if(tic.getClosed()==null){
	    		  flag=1;
	    		  
	    		  break;
	    		  
	    	  }
	    		  
	      }
	      
	      org.aspcfs.modules.prvvedimentinc.base.TicketList ticListProvvedimenti = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
			int passId = thisTicket.getOrgId();
			ticListProvvedimenti.setOrgId(passId);
			ticListProvvedimenti.buildListControlli(db, passId, ticketId);
			Iterator provvedimentiIterator=ticListProvvedimenti.iterator();


			while(provvedimentiIterator.hasNext()){

				org.aspcfs.modules.prvvedimentinc.base.Ticket tic = (org.aspcfs.modules.prvvedimentinc.base.Ticket) provvedimentiIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;
				}

			}

	     
	      while(tamponiIterator.hasNext()){
	    	  
	    	  org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();
	    	  
	    	  if(tic.getClosed()==null){
	    		  flag=1;
	    		  
	    		  break;
	  	  }
	    	 	  
	      }
	      
	      
	      if(thisTicket.isNcrilevate()==true){
	    	  org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
	          int passIdN = thisTicket.getOrgId();
	          nonCList.setOrgId(passedId);
	          nonCList.buildListControlli(db, passIdN, ticketId);
	    	  
	          Iterator ncIterator=nonCList.iterator();
	          
	          while(ncIterator.hasNext()){
		    	  
		    	  org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();
		    	  
		    	  if(tic.getClosed()==null){
		    		  flag=1;
		    		  
		    		  break;
		    		  
		    	  }
		    		  
		      }
	  
	      }
	      
	    //NC di terzi
			org.aspcfs.modules.altriprovvedimenti.base.TicketList nonCList = new org.aspcfs.modules.altriprovvedimenti.base.TicketList();
			int passIdN = thisTicket.getOrgId();
			nonCList.setOrgId(passedId);
			nonCList.buildListControlli(db, passIdN, ticketId);
			Iterator ncIterator=nonCList.iterator();
			while(ncIterator.hasNext()){
				org.aspcfs.modules.altriprovvedimenti.base.Ticket tic = (org.aspcfs.modules.altriprovvedimenti.base.Ticket) ncIterator.next();
				if(tic.getClosed()==null){
					flag=1;

					break;

				}

			}
			//FIne nc di terzi
	      
	      
	      if(thisTicket.getTipoCampione()==5){
	    	  
	    	  org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
	          int AuditOrgId = thisTicket.getOrgId();
	          String idTi = thisTicket.getPaddedId();
	          audit.setOrgId(AuditOrgId);
	         
	          audit.buildListControlli(db, AuditOrgId, idTi);
	          
	    	  Iterator itAudit=audit.iterator();
	    	  
      while(itAudit.hasNext()){
		    	  
		    	  org.aspcfs.modules.audit.base.Audit auditTemp = (org.aspcfs.modules.audit.base.Audit) itAudit.next();
		    	  
		    	  if(auditTemp.getLivelloRischioFinale()==-1){
		    		  flag=2;
		    		  
		    		  break;
		    		  
		    	  }
		    		  
		      }
	    	  
	    	  
	    	  
	    	  
	    	  
	          
	          
	    	  
	    	  
	      }
	      
	      String attivitaCollegate=context.getRequest().getParameter("altro");
	   
	      if(attivitaCollegate==null){
	      if(flag==1 || flag==2){
	    	  context.getRequest().setAttribute("Chiudi", ""+flag);
	    	  return (executeCommandTicketDetails(context));
	    	  
	      }
	      }
	      
	  
	   
	  if(flag==0){
	      thisTicket.setModifiedBy(getUserId(context));
	      resultCount = thisTicket.chiudi(db);
	  }
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
