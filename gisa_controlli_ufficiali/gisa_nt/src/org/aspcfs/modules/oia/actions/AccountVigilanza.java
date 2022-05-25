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
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;

import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.distributori.base.*;
import org.aspcfs.modules.oia.base.Organization;
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
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.ObjectUtils;
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
  
  
  public String executeCommandDownloadRapportoConclusivo(ActionContext context) {
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    String itemId = (String) context.getRequest().getParameter("id");
	    String version = (String) context.getRequest().getParameter("ver");
	    String view = (String) context.getRequest().getParameter("view");
	    FileItem thisItem = null;
	    Connection db = null;
	    Organization thisOrg = null;
	    try {
	      db = getConnection(context);
	      
	      //check permission to record
	     
	      thisItem = new org.aspcfs.modules.oia.base.FileItem(
	          db, Integer.parseInt(itemId));
	      if (version != null) {
	        thisItem.buildVersionList(db);
	      }
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	    //Start the download
	    try {
	      if (version == null) {
	        FileItem itemToDownload = thisItem;
	        itemToDownload.setEnteredBy(this.getUserId(context));
	        String filePath = this.getPath(context, "accounts") + getDatePath(
	            itemToDownload.getModified()) + itemToDownload.getFilename();
	        FileDownload fileDownload = new FileDownload();
	        fileDownload.setFullPath(filePath);
	        fileDownload.setDisplayName(itemToDownload.getClientFilename());
	        if (fileDownload.fileExists()) {
	          if (view != null && "true".equals(view)) {
	            fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
	            fileDownload.streamContent(context);
	          } else {
	            fileDownload.sendFile(context);
	          }
	          //Get a db connection now that the download is complete
	          db = getConnection(context);
	          itemToDownload.updateCounter(db);
	        } 
	      } else {
	        FileItemVersion itemToDownload = thisItem.getVersion(
	        Double.parseDouble(version));
	        itemToDownload.setEnteredBy(this.getUserId(context));
	        String filePath = this.getPath(context, "accounts") + getDatePath(
	        itemToDownload.getModified()) + itemToDownload.getFilename();
	        FileDownload fileDownload = new FileDownload();
	        fileDownload.setFullPath(filePath);
	        fileDownload.setDisplayName(itemToDownload.getClientFilename());
	        if (fileDownload.fileExists()) {
	          if (view != null && "true".equals(view)) {
	            fileDownload.setFileTimestamp(itemToDownload.getModificationDate().getTime());
	            fileDownload.streamContent(context);
	          } else {
	            fileDownload.sendFile(context);
	          }
	          //Get a db connection now that the download is complete
	          db = getConnection(context);
	          itemToDownload.updateCounter(db);
	        } 
	      }
	    } catch (java.net.SocketException se) {
	      //User either canceled the download or lost connection
	      if (System.getProperty("DEBUG") != null) {
	        System.out.println(se.toString());
	      }
	    } catch (Exception e) {
	      errorMessage = e;
	      System.out.println(e.toString());
	    } finally {
	      if (db != null) {
	        this.freeConnection(context, db);
	      }
	    }

	    if (errorMessage == null) {
	      return ("-none-");
	    } else {
	      context.getRequest().setAttribute("Error", errorMessage);
	      addModuleBean(context, "View Accounts", "");
	      return ("SystemError");
	    }
	  }

  
  
  public String executeCommandUploadRapporto(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		 boolean recordInserted = false;
		    boolean isValid = false;
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
			String tempIdControllo = (String)parts.get("id");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 1);
		
			try {
			    
			      String id = (String) parts.get("id");
			      String idNodo = (String) parts.get("idNodo");
			      context.getRequest().setAttribute("idNodo", idNodo);
			      String subject = (String) parts.get("subject");
			      String folderId = (String) parts.get("folderId");
			      String actionStepWork = (String) parts.get("actionStepWork");
			      String allowPortalAccess = (String) parts.get("allowPortalAccess");
			      if (folderId != null) {
			        context.getRequest().setAttribute("folderId", folderId);
			      }
			      if (subject != null) {
			        context.getRequest().setAttribute("subject", subject);
			      }
			      if (actionStepWork != null) {
			        context.getRequest().setAttribute("actionStepWork", actionStepWork);
			      }
			      db = getConnection(context);
			    
			     
			      if (((Object) parts.get("id" + (String) parts.get("orgId"))) instanceof FileInfo) {
			        //Update the database with the resulting file
			    	
			        FileInfo newFileInfo = (FileInfo) parts.get("id" + temporgId);
			        //Insert a file description record into the database
			        org.aspcfs.modules.oia.base.FileItem thisItem = new org.aspcfs.modules.oia.base.FileItem();
			        thisItem.setLinkModuleId(Constants.ACCOUNTS);
			        thisItem.setLinkItemId(Integer.parseInt(id));
			        thisItem.setEnteredBy(getUserId(context));
			        thisItem.setModifiedBy(getUserId(context));
			        thisItem.setIdControllo(Integer.parseInt(id));
			        thisItem.setFolderId(Integer.parseInt(folderId));
			        thisItem.setSubject(subject);
			        thisItem.setClientFilename(newFileInfo.getClientFileName());
			        thisItem.setFilename(newFileInfo.getRealFilename());
			        thisItem.setVersion(1.0);
			        thisItem.setSize(newFileInfo.getSize());
			        thisItem.setAllowPortalAccess(allowPortalAccess);
			        isValid = this.validateObject(context, db, thisItem);
			        if (isValid) {
			          recordInserted = thisItem.insert(db);
			        }
			        if (recordInserted) {
			          this.processInsertHook(context, thisItem);
			          context.getRequest().setAttribute("fileItem", thisItem);
			          context.getRequest().setAttribute("subject", "");
			       
			          context.getRequest().setAttribute("idFile", thisItem.getId());
			          context.getRequest().setAttribute("fileCaricato", "yes");
			        }
			      } else {
			        recordInserted = false;
			        HashMap errors = new HashMap();
			       
			        errors.put(
			            "actionError", systemStatus.getLabel(
			                "object.validation.incorrectFileName"));
			        if (subject != null && "".equals(subject.trim())) {
			          errors.put(
			              "subjectError", systemStatus.getLabel(
			                  "object.validation.required"));
			        }
			        processErrors(context, errors);
			      }
			    } catch (Exception e) {
			      context.getRequest().setAttribute("Error", e);
			      return ("SystemError");
			    } finally {
			      freeConnection(context, db);
			    }
			
			    context.getRequest().setAttribute("id", tempIdControllo);
			//Load the organization
			context.getRequest().setAttribute("OrgDetails", newOrg);

			return executeCommandChiudi(context);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);

			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
  public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "oia-oia-vigilanza-edit"))){
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
   * Re-opens a ticket
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandReopenTicket(ActionContext context) {
    if (!hasPermission(context, "oia-oia-vigilanza-edit")) {
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
      
      
      org.aspcfs.modules.oia.base.FileItem file = new org.aspcfs.modules.oia.base.FileItem(db,thisTicket.getId());
      file.delete(db, getUserId(context));
      thisTicket.setStatusId(thisTicket.STATO_RIAPERTO);//3 significa Riaperto ed e' il numevo pevrfetto CU
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      thisTicket.queryRecord(db, thisTicket.getId());
      /*context.getRequest().setAttribute("idNodo", context.getRequest().getParameter("idNodo"));
      
      Distrubutore d=new Distrubutore();
      Distrubutore dd=d.loadDistributore(thisTicket.getOrgId(), Integer.parseInt(context.getRequest().getParameter("idNodo")), db);
      
      if(dd!=null){
    	
    	  context.getRequest().setAttribute("aslMacchinetta",""+dd.getAslMacchinetta());
    	  
      }*/
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
	    if (!hasPermission(context, "oia-oia-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    
	    try {
			db = this.getConnection(context);
			//System.out.println("TICKET "+ticketId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //Parameters
	    int idnodo = -1 ;
	    String ticketId = context.getRequest().getParameter("id");
	   if (ticketId==null)
		   ticketId =  ""+(((String)context.getRequest().getAttribute("id"))+"");
	    	Ticket t = null;
			
	    	try {
				t = new Ticket(db,Integer.parseInt(ticketId));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		     idnodo= t.getIdMacchinetta();
	    	  //context.getRequest().setAttribute("idNodo",context.getRequest().getParameter("idNodo"));
	    	  //idnodo = Integer.parseInt(context.getRequest().getParameter("idNodo")) ;
	    
	    String retPag = null;
	    try {
	      
	      // Load the ticket


		  Organization orgDetail = new Organization(db, t.getOrgId());

	    
	      context.getRequest().setAttribute("OrgDetails", orgDetail);      
	      
	      context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia",6);
	     context.getRequest().setAttribute("tipoDest",null);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandTicketDetails(context,db);
	      if (context.getRequest().getAttribute("idNodo")!=null)
	  	    context.getRequest().setAttribute("idNodo",context.getRequest().getAttribute("idNodo"));
	  	    else
	  	    	  context.getRequest().setAttribute("idNodo",context.getRequest().getParameter("idNodo"));
	  	 
	      retPag = "DettaglioOK";
	      
	      
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    //return getReturn(context, "TicketDetails");
	    
	    String messaggioPost = (String) context.getRequest().getAttribute("messaggioPost");
	    context.getRequest().setAttribute("messaggioPost", messaggioPost);
	    return retPag;
	  }

  /**
   * Confirm the delete operation showing dependencies
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  
  public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-vigilanza-delete")) {
			return ("PermissionError");
		}

		Connection db = null ;
		try 
		{	
			db = this.getConnection(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url_confirm","OiaVigilanza.do?command=DeleteTicket");
			controlliGeneric.executeCommandConfirmDelete(context,db);

			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}

  
  /*public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "oia-oia-vigilanza-delete")) {
      return ("PermissionError");
    }

    if(context.getRequest().getAttribute("idNodo")!=null)
    	context.getRequest().setAttribute("idNodo", context.getRequest().getParameter("idNodo") );
      
    Connection db = null;
    //Parameters
    String id = context.getRequest().getParameter("id");
    String idNodo = context.getRequest().getParameter("idNodo");

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
     /* htmlDialog.addButton(
          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='OiaVigilanza.do?command=DeleteTicket&id=" + id + "&orgId=" + idNodo + "&forceDelete=true" + RequestUtils.addLinkParams(
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
  }*/


  /**
   * Delete the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteTicket(ActionContext context) {
    /*if (!hasPermission(context, "oia-oia-vigilanza-delete")) {
      return ("PermissionError");
    }*/

  
    boolean recordDeleted = false;
    Connection db = null;
    //Parameters
   
    String passedId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
	  
	  Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	  
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
        context.getRequest().setAttribute("OrgDetails", orgDetail);
        context.getRequest().setAttribute(
            "refreshUrl","OiaVigilanza.do?command=ViewVigilanza&orgId="+thisTic.getOrgId()+ 
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
	    if (!hasPermission(context, "oia-oia-vigilanza-edit")) {
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
		  Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      
	      context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia",6);
	     context.getRequest().setAttribute("tipoDest",null);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandUpdateTicket(context,db);
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	      context.getRequest().setAttribute("idNodo",  context.getRequest().getParameter("idNodo"));
	      resultCount = (Integer) context.getRequest().getAttribute("resultCount");
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
	    if (!hasPermission(context, "oia-oia-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;
	    try {
	      db = this.getConnection(context);
	      
	      //aggiunte queste istruzioni
	      context.getRequest().setAttribute("idNodo",context.getRequest().getParameter("id"));
	      String temporgId = context.getRequest().getParameter("orgId");
	      int tempid = Integer.parseInt(temporgId);
		  Organization orgDetail = new Organization(db, tempid);
		  
	      
	      context.getRequest().setAttribute("siteId", orgDetail.getIdAsl());
	      context.getRequest().setAttribute("tipologia", 6);
	      context.getRequest().setAttribute("tipoDest", null);
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      c.executeCommandAdd(context,db);
	      
	      OiaNodo nodoTemp = new OiaNodo();
		 ArrayList<OiaNodo> listaStrutture =	nodoTemp.getStruttureAutoritaCompetentiDaControllare(db,orgDetail.getOrgId());
		 
		 context.getRequest().setAttribute("ListStruttureAComp", listaStrutture);
	      
	      //Load the organization
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	   
	      
	     

	      LookupList TipoCampione = (LookupList)context.getRequest().getAttribute("TipoCampione");
	      if (TipoCampione.containsKey(22))
	      {
	    	  if (!hasPermission(context, "vigilanza-vigilanza-supervisiona-view"))
	    	  {
	    		  TipoCampione.removeElementByLevel(5);
	    		  
	    	  }
	      }
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      //getting current date in mm/dd/yyyy format
	      String currentDate = getCurrentDateAsString(context);
	      context.getRequest().setAttribute("currentDate", currentDate);
	      context.getRequest().setAttribute(
	          "systemStatus", this.getSystemStatus(context));
	    } catch (Exception e) {
	    	e.printStackTrace();
	     
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

  
  public String executeCommandViewVigilanza(ActionContext context) {
  
	    if (!hasPermission(context, "oia-oia-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));
	    
	    String idNodoString = context.getRequest().getParameter("idNodo");
	    int idNodo = -1;
	    if (idNodoString!=null && !idNodoString.equals("null") && !idNodoString.equals(""))
	    	idNodo = Integer.parseInt(idNodoString);
	    
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OiaVigilanza.do?command=ViewVigilanza&orgId=" + passedId + "&idNodo=" + idNodo);
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
	      /*if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }
	      */
	      LookupList AuditTipo = new LookupList();
	      AuditTipo.setTable("lookup_audit_tipo");
	      AuditTipo.buildListWithEnabled(db);
		    ticketListInfo.setSearchCriteria(ticList, context);

	      context.getRequest().setAttribute("AuditTipo", AuditTipo);
	      Organization orgDetail = new Organization(db, passedId);
//		  OiaNodo orgDetail = new OiaNodo(db, idNodo, this.getSystemStatus(context));
	      ticList.setOrgId(passedId);
//	      ticList.setIdMacchinetta(idNodo);
	    
	    	  ticList.setSiteId(orgDetail.getSiteId());
	      
	    	  UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
				if (thisUser.getRoleId()==Role.RUOLO_CRAS)
					ticList.setIdRuolo(thisUser.getRoleId());
	      
	      ticList.buildList(db);
	      context.getRequest().setAttribute("TicList", ticList);
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	      addModuleBean(context, "View Accounts", "Accounts View");
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return getReturn(context, "ViewVigilanza");
	  }
  
  public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
	    if (!(hasPermission(context, "oia-oia-vigilanza-add"))) 
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
	  
	   
		  Organization orgDetail = new Organization(db, newTic.getOrgId());
	     
	      
      context.getRequest().setAttribute("OrgDetails", orgDetail);

      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
      controlliGeneric.executeCommandInsert(context,db);
     
      context.getRequest().setAttribute("idNodo",context.getRequest().getParameter("idNodo"));
     
      recordInserted = (Boolean) context.getRequest().getAttribute("inserted");
      isValid = (Boolean) context.getRequest().getAttribute("isValid");
	    if (recordInserted && isValid) {
	      
	     
	      String tipo_richiesta = newTic.getTipo_richiesta();
	      tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
	     
	      retPage = "InsertOK";
	    }
	    else
	    {
		      int tempid = newTic.getOrgId();
			  
		      
		      context.getRequest().setAttribute("siteId", orgDetail.getIdAsl());
		      context.getRequest().setAttribute("tipologia", 6);
		      context.getRequest().setAttribute("tipoDest", null);
		      
		      org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
		      c.executeCommandAdd(context,db);
		      
		      OiaNodo nodoTemp = new OiaNodo();
			 ArrayList<OiaNodo> listaStrutture =	nodoTemp.getStruttureAutoritaCompetentiDaControllare(db,orgDetail.getOrgId());
			 
			 context.getRequest().setAttribute("ListStruttureAComp", listaStrutture);
		      
		      //Load the organization
		      context.getRequest().setAttribute("OrgDetails", orgDetail);
		   
		      
		     

		      LookupList TipoCampione = (LookupList)context.getRequest().getAttribute("TipoCampione");
		      if (TipoCampione.containsKey(22))
		      {
		    	  if (!hasPermission(context, "vigilanza-vigilanza-supervisiona-view"))
		    	  {
		    		  TipoCampione.removeElementByLevel(5);
		    		  
		    	  }
		      }
		      context.getRequest().setAttribute("TipoCampione", TipoCampione);
		      //getting current date in mm/dd/yyyy format
		      String currentDate = getCurrentDateAsString(context);
		      context.getRequest().setAttribute("currentDate", currentDate);
		      context.getRequest().setAttribute(
		          "systemStatus", this.getSystemStatus(context));
		      return "AddOK";
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
	      return ( retPage );//("InsertOK"); 
	    }
  

  public String executeCommandModifyTicket(ActionContext context) {
	    if (!hasPermission(context, "oia-oia-vigilanza-edit")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    Ticket newTic = null;

	   
	    try 
	    {
	      db = this.getConnection(context);
	      String ticketId = context.getRequest().getParameter("id");
	      newTic = new Ticket(db, Integer.parseInt(ticketId));
	      
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
	   
		  Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("siteId", orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia", 6);
	      context.getRequest().setAttribute("tipoDest", null);
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandModifyTicket(context,db);
	      OiaNodo nodoTemp = new OiaNodo();
			 ArrayList<OiaNodo> listaStrutture =	nodoTemp.getStruttureAutoritaCompetentiDaControllare(db,orgDetail.getOrgId());
			 
			 context.getRequest().setAttribute("ListStruttureAComp", listaStrutture);
		      
	      context.getRequest().setAttribute("idNodo",  context.getRequest().getParameter("idNodo"));
	      
	      LookupList TipoCampione = (LookupList)context.getRequest().getAttribute("TipoCampione");
	      if (TipoCampione.containsKey(22))
	      {
	    	  if (!hasPermission(context, "vigilanza-vigilanza-supervisiona-view"))
	    	  {
	    		  TipoCampione.removeElementByLevel(5);
	    		  
	    	  }
	      }
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);

	    } catch (Exception errorMessage) {
	    	errorMessage.printStackTrace();
	    	errorMessage.printStackTrace();
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
	  if (!hasPermission(context, "oia-oia-vigilanza-edit")) {
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
		  Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("siteId", orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia", 6);
	      context.getRequest().setAttribute("tipoDest", null);
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandModifyTicket(context,db);
	      
	      context.getRequest().setAttribute("idNodo",  context.getRequest().getParameter("idNodo"));
	      
	      

	    } catch (Exception errorMessage) {
	    	errorMessage.printStackTrace();
	    	errorMessage.printStackTrace();
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
  public String executeCommandUpdate(ActionContext context) {
	    if (!hasPermission(context, "oia-oia-vigilanza-edit")) {
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
		  Organization orgDetail = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	      context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
	      context.getRequest().setAttribute("tipologia",6);
	     context.getRequest().setAttribute("tipoDest",null);
	      org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
	      controlliGeneric.executeCommandUpdateTicket(context,db);
	      context.getRequest().setAttribute("OrgDetails", orgDetail);
	      context.getRequest().setAttribute("idNodo",  context.getRequest().getParameter("idNodo"));
	      resultCount = (Integer) context.getRequest().getAttribute("resultCount");
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

  
  
  
  public String executeCommandDettaglio(ActionContext context) {
	    if (!hasPermission(context, "oia-oia-vigilanza-view")) {
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
		if (!(hasPermission(context, "oia-oia-vigilanza-edit"))) {
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
	  	    if (!(hasPermission(context, "oia-oia-vigilanza-edit"))){
	      return ("PermissionError");
	    }
	    int resultCount = -1;
	    Connection db = null;
	    Ticket thisTicket = null;
	    try {
	    	
	      db = this.getConnection(context);
	      
	      String idC = context.getRequest().getParameter("id") ;
	      if (idC == null)
	      {
	    	  idC = (String) context.getRequest().getAttribute("id");
	      }
	      
	      thisTicket = new Ticket(
	          db, Integer.parseInt(idC));
	      Ticket oldTicket = new Ticket(db, thisTicket.getId());
	      
	      Integer idT = thisTicket.getId();
	      String idCU = idT.toString(); 
	      
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
	        return ("PermissionError");
	      }
	      
	      if(context.getRequest().getParameter("idNodo")!=null)
	    	  context.getRequest().setAttribute("idNodo", context.getRequest().getParameter("idNodo") );
	      else
	    	  context.getRequest().setAttribute("idNodo", context.getRequest().getAttribute("idNodo") );
	      
 String ticketId = context.getRequest().getParameter("id");
 
 if (ticketId == null)
 {
	 ticketId = (String) context.getRequest().getAttribute("id");
 }
	     

 
 org.aspcfs.modules.osservazioni.base.OsservazioniList osservazioniList = new org.aspcfs.modules.osservazioni.base.OsservazioniList ();
 int paId = thisTicket.getOrgId();
 osservazioniList.setOrgId(paId);
 osservazioniList.buildListControlli(db, paId, ticketId);

	 
	      
	      Iterator osservazioniIterator=osservazioniList.iterator();

	      
	      int flag=0;
	 

	      
	      while(osservazioniIterator.hasNext()){
	    	  
	    	  org.aspcfs.modules.osservazioni.base.Osservazioni tic = (org.aspcfs.modules.osservazioni.base.Osservazioni) osservazioniIterator.next();
	    	  tic.setModifiedBy(thisTicket.getModifiedBy());
	    	  tic.setModified(thisTicket.getModified());
	    	  if (tic.getClosed()==null)
	    		  tic.chiudi(db);
	    	  
//	    	  	if(tic.getClosed()==null){
//	    		  flag=1;
//	    		  
//	    		  	//break;
//	    	  	}
	    	 	  
	      }
	      
	      
	     
	      
	      String attivitaCollegate=context.getRequest().getParameter("altro");
	   
//	      if(attivitaCollegate==null){
//	      if(flag==1 || flag==2){
//	    	  context.getRequest().setAttribute("Chiudi", ""+flag);
//	    	   
//	    	  
//	    	  return (executeCommandTicketDetails(context));
//	    	  
//	      }
//	      }
	      
	  
	   
	  if(flag==0){
	      thisTicket.setModifiedBy(getUserId(context));
	      resultCount = thisTicket.chiudi(db);
	      
	      context.getRequest().setAttribute("id", ""+thisTicket.getId());
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
  
  private int CalcolaIdAslDaIdStruttura(String struttura, Connection db) throws SQLException{
		int idAsl = -1;
		
		PreparedStatement pst = null;
		String query = "select id_asl from oia_nodo where id = ? ";
		pst  = db.prepareStatement( query );
		pst.setInt(1,  Integer.parseInt(struttura));
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			idAsl = rs.getInt("id_asl");
		rs.close();
		pst.close();
		
		return idAsl;
	}

}
