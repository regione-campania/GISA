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


import org.aspcf.modules.controlliufficiali.action.ProvvedimentiNoNc;
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
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GenericControlliUfficiali;
import java.util.*;


import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.provvedimentinc.actions.ProvvedimentiNoNcAction;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.soa.base.*;
import org.aspcfs.modules.soa.base.*;
import org.aspcfs.modules.soa.base.*;
import org.aspcfs.modules.troubletickets.base.*;

import org.aspcfs.modules.prvvedimentinc.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

/**
 * Maintains Tickets related to an Account
 * 
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:newTic.upda53:03 mrajkowski Exp $
 * @created August 15, 2001
 */
public final class AccountProvvedimentiNoNc extends CFSModule {

	/**
	 * Sample action for prototyping, by including a specified page
	 * 
	 * @param context
	 *            Description of Parameter
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
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		
		
		Integer resultCount = -1 ;
		try {
			
			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			
			c.executeCommandReopenTicket(context);
			
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
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
	 * Load the ticket details tab
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "soa-soa-campioni-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		// Parameters
				
		String retPag = null;
		try {
			db = this.getConnection(context);
			Integer orgId = -1 ;
			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			
			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			c.executeCommandTicketDetails(context,db);
			orgId = (Integer) context.getRequest().getAttribute("orgId");			
			Organization thisOrganization = new Organization(db,orgId);
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, orgId)) {
				return ("PermissionError");
			}
					retPag = "DettaglioOK";

			

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return getReturn(context, "TicketDetails");
		return retPag;
	}


	/**
	 * Confirm the delete operation showing dependencies
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "soa-soa-campioni-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		// Parameters
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
			// Prepare the dialog based on the dependencies
			HtmlDialog htmlDialog = new HtmlDialog();
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			htmlDialog.addMessage(systemStatus
					.getLabel("confirmdelete.caution")
					+ "\n" + dependencies.getHtmlString());
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			htmlDialog.addButton(systemStatus.getLabel("global.button.delete"),
					"javascript:window.location.href='"+ticket.getURlDettaglio()+"Provvedimenti.do?command=DeleteTicket&id="
							+ id
							+ "&orgId="
							+ orgId
							+ "&forceDelete=true"
							+ RequestUtils.addLinkParams(context.getRequest(),
									"popup|popupType|actionId") + "'");
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"),
					"javascript:parent.window.close()");
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
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDeleteTicket(ActionContext context) {
	
		boolean recordDeleted = false;
		Connection db = null;
		// Parameters
		int orgId = Integer
				.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			Organization newOrg = new Organization(db, orgId);
			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
				return ("PermissionError");
			}
			
			 String id_controllo=thisTic.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
			
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			if (recordDeleted) {
				processDeleteHook(context, thisTic);
				//del
				String inline = context.getRequest().getParameter("popupType");
				context.getRequest().setAttribute("OrgDetails", newOrg);
				context
						.getRequest()
						.setAttribute(
								"refreshUrl",
								thisTic.getURlDettaglio()+"Vigilanza.do?command=TicketDetails&id="+id_controllo+"&orgId="
										+ orgId
										+ (inline != null
												&& "inline".equals(inline
														.trim()) ? "&popup=true"
												: ""));
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

	
	
	
	
	
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "soa-soa-campioni-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Integer resultCount = 0;
		Boolean isValid = true;

	

		try {
			db = this.getConnection(context);

			Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));

			context.getRequest().setAttribute("OrgDetails", orgDetails);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, orgDetails.getOrgId())) {
				return ("PermissionError");
			}
			
			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			c.executeCommandUpdateTicket(context,db);
			
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");
			isValid 	= (Boolean)context.getRequest().getAttribute("isValid");
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			// return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandDettaglio(context));
	}

	// aggiunto da d.dauria
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "soa-soa-campioni-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			// aggiunte queste istruzioni
			String temporgId = context.getRequest().getParameter("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
		
			ProvvedimentiNoNc c = new ProvvedimentiNoNc ();
			c.executeCommandAdd(context,db);
			context.getRequest().setAttribute("OrgDetails", newOrg);

		} catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource",
					context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "soa-soa-campioni-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		Ticket newTic = (Ticket) context.getFormBean();
		
		
		try
		{
			db = this.getConnection(context);
			
			
			
			Organization thisOrg = new Organization(db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", thisOrg);
			context.getRequest().setAttribute("ragione_sociale", thisOrg.getName());
			
			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			c.executeCommandInsert(context,db);
			
			recordInserted = (Boolean) context.getRequest().getAttribute("recordInserted");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			
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
				context.getRequest().setAttribute("actionSource",
						context.getRequest().getParameter("actionSource"));
				return "InsertTicketOK";
			}
			String retPage = "DettaglioOK";
			//String tipo_richiesta = newTic.getTipo_richiesta();
			//tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "InsertOK";
		
			return (retPage);// ("InsertOK");
		}
		return (executeCommandAdd(context));
	}

	/**
	 * Loads the ticket for modification
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "soa-soa-campioni-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		
		try {
			db = this.getConnection(context);
			

			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			c.executeCommandModifyTicket(context,db);
			
			Integer orgId = (Integer) context.getRequest().getAttribute("orgId");
			Organization orgDetails = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", orgDetails);
			
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return "ModifyautorizzazionetrasportoanimaliviviOK";
		return "ModifyOK";
		// return getReturn(context, "ModifyTicket");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "soa-soa-edit")) {
			return ("PermissionError");
		}
		String retPage = "Modify";
		Connection db = null;
		Ticket newTic = null;
		// Parameters
		String ticketId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
		
			ProvvedimentiNoNcAction c = new ProvvedimentiNoNcAction();
			c.executeCommandModifyTicket(context,db);
			
			Organization orgDetails = new Organization(db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", orgDetails);

		
		
		}
		
		catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}

		return getReturn(context, retPage);
	}

	
	public String executeCommandDettaglio(ActionContext context) {
		if (!hasPermission(context, "soa-soa-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		String ticketId = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			String fromDefectDetails = context.getRequest().getParameter(
					"defectCheck");
			if (fromDefectDetails == null
					|| "".equals(fromDefectDetails.trim())) {
				fromDefectDetails = (String) context.getRequest().getAttribute(
						"defectCheck");
			}

			// Parameters
			ticketId = context.getRequest().getParameter("id");
			// Reset the pagedLists since this could be a new visit to this
			// ticket
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

			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				return ("PermissionError");
			}

			Organization orgDetails = new Organization(db, newTic.getOrgId());
		

			

		

			LookupList TipoCampione = new LookupList(db, "lookup_provvedimenti");
			TipoCampione.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db,
					"lookup_sanzioni_amministrative");
			EsitoCampione.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

			
	LookupList AnimaliNonAlimentari = new LookupList(db,
	"lookup_animali_non_alimentari");
AnimaliNonAlimentari.addItem(-1,
"-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AnimaliNonAlimentari",
	AnimaliNonAlimentari);
		      
		      
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList SanzioniPenali = new LookupList(db,
					"lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
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
		

		retPage = "DettaglioOK";


		return (retPage);
	}


	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "soa-soa-campioni-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
					.getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.chiudi(db);
			if (resultCount == 0)
			{
				context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito");
			}
			thisTicket.getIdControlloUfficiale();
			String padd="000000";
			 String id_controllo=thisTicket.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
			
			/*
			 * Inifio Giuseppe
			 * 
			 */
			
			
			org.aspcfs.modules.vigilanza.base.Ticket thisTicketV = new org.aspcfs.modules.vigilanza.base.Ticket(
			          db, Integer.parseInt(id_controllo));
			   
			
			String ticketId = context.getRequest().getParameter("id");
		      
		     
			 
			 org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			 int passedId = thisTicketV.getOrgId();
			 ticList.setOrgId(passedId);
			 ticList.buildListControlli(db, passedId, id_controllo);

			 
			 org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			 int pasId = thisTicketV.getOrgId();
			 tamponiList.setOrgId(passedId);
			 tamponiList.buildListControlli(db, pasId, id_controllo);

				  	    
				      
				      
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
				      
				     
				      while(tamponiIterator.hasNext()){
				    	  
				    	  org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();
				    	  
				    	  if(tic.getClosed()==null){
				    		  flag=1;
				    		  
				    		  break;
				  	  }
				    	 	  
				      }
				      
				      
				      if (flag == 0)
				      {
				    	  thisTicketV.apriTemp(db);
				      }
				     
				      
				      if(thisTicketV.isNcrilevate()==true){
				    	  org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				          int passIdN = thisTicketV.getOrgId();
				          nonCList.setOrgId(passedId);
				          nonCList.buildListControlli(db, passIdN, id_controllo);
				    	  
				          Iterator ncIterator=nonCList.iterator();
				          
				          while(ncIterator.hasNext()){
					    	  
					    	  org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();
					    	  
					    	  if(tic.getClosed()==null){
					    		  flag=1;
					    		  
					    		  break;
					    		  
					    	  }
					    		  
					      }
				  
				      }
				      
				      Organization org = new org.aspcfs.modules.soa.base.Organization(db , thisTicketV.getOrgId());
				      
				    
				      
				      
				      
				      if(thisTicketV.getTipoCampione()==5){
				    	  
				    	  org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
				          int AuditOrgId = thisTicketV.getOrgId();
				          String idTi = thisTicketV.getPaddedId();
				          audit.setOrgId(AuditOrgId);
				         
				          audit.buildListControlli(db, AuditOrgId, idTi);
				          
				    	  Iterator itAudit=audit.iterator();
				    	  
				    	  if(!itAudit.hasNext()){
				    		  
				  		    	flag=2;
				  	    			  		    
				    	  }else{
				    	  
			      while(itAudit.hasNext()){
					    	  
					    	  org.aspcfs.modules.audit.base.Audit auditTemp = (org.aspcfs.modules.audit.base.Audit) itAudit.next();
					    	  Organization orgDetails = new Organization(db,thisTicketV.getOrgId());
					    	  
					    	  if(thisTicketV.isCategoriaisAggiornata()==false){
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
				      
				  
				      String chiudiCu = context.getRequest().getParameter("chiudiCu");
					  if(flag==0 ){
						  if(chiudiCu !=null)
						  {
					      thisTicketV.setModifiedBy(getUserId(context));
					      resultCount = thisTicketV.chiudi(db);
						  }else
						  {
							  context.getRequest().setAttribute("Messaggio2", "Attivita collegate al cu chiuse");
						  }
					  }
			
			if (resultCount == -1) {
				return (executeCommandTicketDetails(context));
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
