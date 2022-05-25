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
package org.aspcfs.modules.soa.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;

import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.soa.base.*;
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
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;

import java.text.ParseException;
import java.util.*;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.soa.base.Organization;
import org.aspcfs.modules.soa.base.OrganizationHistory;
import org.aspcfs.modules.soa.base.OrganizationHistoryList;
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
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return getReturn(context, "Include");
  }
  public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "soa-soa-vigilanza-edit"))){
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
	  if (!hasPermission(context, "soa-soa-vigilanza-edit")) {
	      return ("PermissionError");
	    }
	    int resultCount = -1;
	    
	    Connection db =null;
		try 
		{	db=this.getConnection(context);
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
    if (!hasPermission(context, "soa-soa-vigilanza-add")) {
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
  

  /**
   * Load the ticket details tab
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  
  public String executeCommandTicketDetails(ActionContext context) {
	  if (!hasPermission(context, "soa-soa-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    //Parameters
	    String ticketId = context.getRequest().getParameter("id");
	    String retPag = null;
	    PreparedStatement pst = null;
		String sql = null;
		String flagMod5 = null;
		ResultSet rs = null;
	    try {
	      db = this.getConnection(context);
	      // Load the ticket
	      int i=0;
	      Organization orgDetail =null;
	      
	      if (context.getRequest().getParameter("orgId")!=null)
	      {
	    	  orgDetail =  new Organization(db,Integer.parseInt(context.getRequest().getParameter("orgId")));
	      }
	      else
	      {
	    	  orgDetail =  new Organization(db,Integer.parseInt(context.getRequest().getParameter("id")));
	      }
	    	  context.getRequest().setAttribute("OrgDetails", orgDetail);      
	      
	    	//Read flag for mod5 from ticket table
				sql = "select flag_mod5 as flag from ticket where ticketid = ? ";
				pst=db.prepareStatement(sql);
				pst.setInt(++i,Integer.parseInt(ticketId));
				rs = pst.executeQuery();
				if(rs.next()){
					 flagMod5 = rs.getString("flag");
				}
				
				LookupList OrgCategoriaSOA = new LookupList(db, "lookup_categoriarischio_soa");
				//OrgCategoriaSOA.buildList(db);
				OrgCategoriaSOA.addItem(-1, "-- SELEZIONA UNA CATEGORIA DI RISCHIO --");
				context.getRequest().setAttribute("OrgCategoriaSOA", OrgCategoriaSOA);		
	    	  
	      context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia",97);
	      context.getRequest().setAttribute("bozza", flagMod5);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandTicketDetails(context,db);
	      context.getRequest().setAttribute("ViewLdASoa","si");
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
  public String executeCommandConfirmDelete(ActionContext context) {
	  if (!hasPermission(context, "soa-soa-vigilanza-delete")) {
	      return ("PermissionError");
	    }
	  Connection db = null;
	    //Parameters
	    String id = context.getRequest().getParameter("id");
	    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	    try 
	    {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      Ticket ticket = new Ticket(db, Integer.parseInt(id));
	      String codiceAllerta = context.getRequest().getParameter("codiceallerta");
	      DependencyList dependencies = ticket.processDependencies(db);
	      //Prepare the dialog based on the dependencies
	      HtmlDialog htmlDialog = new HtmlDialog();
	      dependencies.setSystemStatus(systemStatus);
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      htmlDialog.addMessage(
	      systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='SoaVigilanza.do?command=DeleteTicket&codiceallerta=" +codiceAllerta + "&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
	      htmlDialog.addButton(
	      systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
	      context.getSession().setAttribute("Dialog", htmlDialog);
	     
	    } 
	    catch (Exception errorMessage) 
	    {
	    	errorMessage.printStackTrace();
	    	context.getRequest().setAttribute("Error", errorMessage);
	    	return ("SystemError");
	    } 
	    finally 
	    {
	      this.freeConnection(context, db);
	    }
	    return ("ConfirmDeleteOK");
	    
	  }

  


  /**
   * Delete the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteTicket(ActionContext context) {
	  if (!hasPermission(context, "soa-soa-vigilanza-delete")) {
	      return ("PermissionError");
	    }
	    boolean recordDeleted = false;
	    Connection db = null;
	   
	    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	    String passedId = context.getRequest().getParameter("id");
	  
	    try {
	      db = this.getConnection(context);
	      Organization newOrg = new Organization(db, orgId);
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	   
	    
	        Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
	        recordDeleted = thisTic.delete(db, getDbNamePath(context));
	        if (recordDeleted) 
	        {
	          //del
	          String inline = context.getRequest().getParameter("popupType");
	          context.getRequest().setAttribute("refreshUrl", "SoaVigilanza.do?command=ViewVigilanza&orgId=" + orgId + (inline != null && "inline".equals(inline.trim()) ? "&popup=true" : ""));
	          String codiceAllerta=(String)context.getRequest().getParameter("codiceallerta");
	  	  	if(thisTic.getTipoIspezioneCodiceInterno().contains("7a"))
	  	    {
	  	  	  if(thisTic.getIdFileAllegato()!=-1)
	  	      {
	  	  			FileItem thisItem = new FileItem(db, thisTic.getIdFileAllegato(), thisTic.getOrgId(), Constants.ACCOUNTS);
	  	    		recordDeleted = thisItem.delete(db, this.getPath(context, "accounts"));
	  	      }
	  	    }
	  	  }
	        
	        context.getRequest().setAttribute("recordDeleted", recordDeleted);
	        
	            
	      } 
	      catch (Exception errorMessage) 
	      {
	        context.getRequest().setAttribute("Error", errorMessage);
	        errorMessage.printStackTrace();
	        return ("SystemError");
	      } 
	      finally 
	      {
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
	    if (!hasPermission(context, "soa-soa-vigilanza-edit")) {
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
	   

	    if(("1").equals(context.getRequest().getParameter("ncrilevate")))
	  	  newTic.setNcrilevate(true);
	    else
	  	  newTic.setNcrilevate(false);
	    
	    
	    newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
	    newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili")) ;
	    newTic.setDescrizioneCodiceAteco(context.getRequest().getParameter("alertText")) ;
	    try {


	      db = this.getConnection(context);
	      Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      
	      context.getRequest().setAttribute("siteId",orgDetails.getSiteId());
	      context.getRequest().setAttribute("tipologia",97);
	     context.getRequest().setAttribute("tipoDest","");
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandUpdateTicket(context,db);
	      context.getRequest().setAttribute("OrgDetails", orgDetails);
	      resultCount = (Integer) context.getRequest().getAttribute("resultCount");
	      context.getRequest().setAttribute("ViewLdASoa","si");
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
  
  public String executeCommandDeleteListaDistribuzione(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;
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

	      String gotoInsert = (String)parts.get("gotoPage");
	      String temporgId = (String)parts.get("orgId");
	      int tempid = Integer.parseInt(temporgId);
	      Organization newOrg = new Organization(db, tempid);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      context.getRequest().setAttribute("siteId", newOrg.getSiteId());
	      context.getRequest().setAttribute("tipologia",97);
	      context.getRequest().setAttribute("tipoDest", "");
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandDeleteListaDistribuzione(context,db);
	      context.getRequest().setAttribute("OrgDetails", newOrg);

	    if(!"insert".equals(gotoInsert))
	    {
	    	return "ModifyOK";
	    }
	    else
	    {
	    	
	    	if (((String)parts.get("actionSource")) != null) {
	    		context.getRequest().setAttribute("actionSource", (String)parts.get("actionSource"));
	    		return "AddTicketOK";
	    	}
	    	context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
	    	return ("AddOK");
	    }
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	  
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	  }

  
  public String executeCommandUploadListaDistribuzione(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-vigilanza-add")) {
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
	      context.getRequest().setAttribute("tipologia", 97);
	      context.getRequest().setAttribute("tipoDest", "");
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric =new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandUploadListaDistribuzione(context,db);
	      //aggiunte queste istruzioni
	     
	      
	     
	      //Load the organization
	      Organization thisOrganization = new Organization(db, Integer.parseInt(temporgId));
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	     
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
  


  public String executeCommandAdd(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;
	    try {
	      db = this.getConnection(context);
	      

	      
	      //aggiunte queste istruzioni
	      String temporgId = context.getRequest().getParameter("orgId");
	      int tempid = Integer.parseInt(temporgId);
	      Organization newOrg = new Organization(db, tempid);
	      
	      context.getRequest().setAttribute("siteId", newOrg.getSiteId());
	      context.getRequest().setAttribute("tipologia", 97);
	      context.getRequest().setAttribute("tipoDest", "");
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      c.executeCommandAdd(context,db);
	      
	      //Load the organization
	      Organization thisOrganization = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      context.getRequest().setAttribute("ViewLdASoa","si");
	     
	     
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
	    UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "AddTipo2");
			return getReturn(context, "Add");	  }
  
  public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
	    if (!(hasPermission(context, "soa-soa-vigilanza-add"))) 
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
	   try
	   {
	    db = this.getConnection(context);
	    String newContact = context.getRequest().getParameter("contact");
	    String save = context.getRequest().getParameter("Save");
	    Ticket newTic = (Ticket) context.getFormBean();
	   
	   
	    Organization thisOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	    newTic.setCompanyName(thisOrg.getName());
	    newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili")) ;
	    newTic.setDescrizioneCodiceAteco(context.getRequest().getParameter("alertText")) ;
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
	    else return executeCommandAdd(context);
	   }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	this.freeConnection(context, db);
	    }
	      return ( retPage );//("InsertOK"); 
	    }
  
  
  public String executeCommandViewVigilanza(ActionContext context) {
  
	    if (!hasPermission(context, "soa-soa-vigilanza-view")) {
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
	        "SoaVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
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
  
  /**
   * Loads the ticket for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyTicket(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-vigilanza-edit")) {
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
		
		if (!isModificabile){
			context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
			return(executeCommandTicketDetails(context));
		}
		//gestione della modifica del controllo ufficiale
	    
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("siteId", thisOrganization.getSiteId());
	      context.getRequest().setAttribute("tipologia", 97);
	      context.getRequest().setAttribute("tipoDest", "");
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      context.getRequest().setAttribute("ViewLdASoa","si");
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandModifyTicket(context,db);

	    } catch (Exception errorMessage) {
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
	  
  
  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "soa-soa-edit")) {
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
        if (newTic.getOrgId() != -1) {
        }
      }

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
        return ("PermissionError");
      }



      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      

      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
           
      Organization orgDetails = new Organization(db, newTic.getOrgId());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

     
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
    } catch (Exception errorMessage) {
    	errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("TicketDetails", newTic);
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
    if (!hasPermission(context, "soa-soa-edit")) {
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
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      
      newTic.setModifiedBy(getUserId(context));
      //Get the previousTicket, update the ticket, then send both to a hook
      Ticket previousTicket = new Ticket(db, newTic.getId());
     
      newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      if (isValid) {
    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria //aggiunto da d.dauria  
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
	    if (!hasPermission(context, "soa-soa-vigilanza-view")) {
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
		if (!(hasPermission(context, "soa-soa-vigilanza-edit"))) {
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
	  	    if (!(hasPermission(context, "soa-soa-vigilanza-edit"))){
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
	    	  
	    	  org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
	          int AuditOrgId = thisTicket.getOrgId();
	          String idTi = thisTicket.getPaddedId();
	          audit.setOrgId(AuditOrgId);
	         
	          audit.buildListControlli(db, AuditOrgId, idTi);
	          
	    	  Iterator itAudit=audit.iterator();
	    	  
     // while(itAudit.hasNext()){
		    	  
		    	  //org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit.next();
		    	  
	    	  		//if(itAudit.hasNext()==true)
	    	  		/*{
		    	  if(thisTicket.isCategoriaisAggiornata()==false){
		    		  flag=2;
		    		  
		    		//  break;
		    		  
		    	  }}*/
		    		  
		      //}
	    	  
	    	  
	    	  if(!itAudit.hasNext()){
	    		  
	  		    	flag=2;
	  	    			  		    
	    	  }else{
	    	  
    while(itAudit.hasNext()){
		    	  
		    	  org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit.next();
		    	  Organization orgDetails = new Organization(db,thisTicket.getOrgId());
		    	  
		    	  if(thisTicket.isCategoriaisAggiornata()==false){
		    		  flag=2;
		    		  
		    		  break;
		    		  
		    	  }
		    		  
		    }}
  
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
