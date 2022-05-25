/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.distributori.actions;

import com.darkhorseventures.framework.actions.ActionContext;
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
import org.aspcfs.modules.distributori.base.Organization;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tamponi.actions.Tamponi;
import org.aspcfs.modules.tamponi.base.Tampone;
import org.aspcfs.modules.tamponi.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * Maintains Tickets related
 * 
 * @author chris
 * @version $Id: Tickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp $
 * @created August 15, 2001
 */
public final class AccountTamponi extends CFSModule {

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
		if (!hasPermission(context, "distributori-distributori-tamponi-edit")) {
			return ("PermissionError");
		}
	Integer resultCount = -1 ; 
		try
		{
			Tamponi t = new Tamponi();
			t.executeCommandReopenTicket(context);
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");

			
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
	 * Prepares a ticket form
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandAddTicket(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-tamponi-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		Organization newOrg = null;
		String retPag = "";
		// Parameters
		String temporgId = context.getRequest().getParameter("orgId");
		int tempid = Integer.parseInt(temporgId);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, tempid)) {
				return ("PermissionError");
			}
			// Organization for header
			newOrg = new Organization(db, tempid);
			// Ticket
			newTic = (Ticket) context.getFormBean();
			if (context.getRequest().getParameter("refresh") != null
					|| (context.getRequest().getParameter("contact") != null && context
							.getRequest().getParameter("contact").equals("on"))) {
			} else {
				newTic.setOrgId(tempid);
			}
			addModuleBean(context, "View Accounts", "Add a Ticket");
			context.getRequest().setAttribute("OrgDetails", newOrg);

			// getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute("systemStatus",
					this.getSystemStatus(context));
			String tipo_richiesta = context.getRequest().getParameter(
					"tipo_richiesta");
			retPag = "Add_" + tipo_richiesta + "OK";
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return getReturn(context, "AddTicket");
		return retPag;
	}

	

	/**
	 * Load the ticket details tab
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-tamponi-view")) {
			return ("PermissionError");
		}
		String retPag  	= "";
		Connection db 	= null;
		try
		{
		
			db = this.getConnection(context);
			Tamponi t = new Tamponi();
			t.executeCommandTicketDetails(context,db);
			
			Integer orgId = (Integer) context.getRequest().getAttribute("orgId");
			Organization thisOrganization = new Organization(db,orgId);
				// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, orgId)) {
				return ("PermissionError");
			}
			retPag = "DettaglioOK";
			// Load the organization for the header
		
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			
		} 
		catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally 
		{
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
		if (!hasPermission(context, "distributori-distributori-tamponi-delete")) {
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
					"javascript:window.location.href='DistributoriTamponi.do?command=DeleteTicket&id="
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
		if (!hasPermission(context, "distributori-distributori-tamponi-delete")) {
			return ("PermissionError");
		}
		Boolean recordDeleted = false;
		Connection db = null;
		// Parameters
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		try {
			db = this.getConnection(context);
			Organization newOrg = new Organization(db, orgId);
			
			if (!isRecordAccessPermitted(context, db, orgId)) {
				return ("PermissionError");
			}
			Tamponi t = new Tamponi ();
			t.executeCommandDeleteTicket(context,db);
			
			
			
			String id_controllo = (String)context.getRequest().getAttribute("idControllo");
			recordDeleted = (Boolean) context.getRequest().getAttribute("recordDeleted");			
			
			if (recordDeleted) {
				
				String inline = context.getRequest().getParameter("popupType");
				context.getRequest().setAttribute("OrgDetails", newOrg);
				context.getRequest().setAttribute("refreshUrl","DistributoriVigilanza.do?command=TicketDetails&id="+id_controllo+"&orgId="+ orgId+ (inline != null&& "inline".equals(inline.trim()) ? "&popup=true": ""));
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

	private static void aggiornaPunteggio(Connection db, int idNononformita) throws SQLException
		{
		  
		  String selselectIdCu = "select id_controllo_ufficiale from ticket where ticketid = ?";
			String update = "update ticket set punteggio = (select sum (punteggio) from ticket where id_controllo_ufficiale= ? and tipologia not in (3)) where ticketid = ?";
			PreparedStatement pst = db.prepareStatement(selselectIdCu);
			pst.setInt(1, idNononformita);
			ResultSet rs = pst.executeQuery();
			String idCU = "";
			if(rs.next())
			{
				idCU = rs.getString(1);
			}
			
			int id_cu = -1;
			String padd = idCU;
			while(idCU.startsWith("0"))
			{
				idCU = idCU.substring(1);
			}
			id_cu = Integer.parseInt(padd);
			pst =db.prepareStatement(update);
			
			pst.setString(1, padd);
			pst.setInt(2, id_cu);
			pst.execute();
		}
	/**
	 * Update the specified ticket
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	
	
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-tamponi-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;


		try {
			db = this.getConnection(context);

			Tamponi t = new Tamponi () ;
			t.executeCommandUpdateTicket(context,db); 
			
			resultCount = (Integer) context.getRequest().getAttribute("resultCount")	;
			isValid		= (Boolean) context.getRequest().getAttribute("isValid")		;
			Integer orgId = (Integer)context.getRequest().getAttribute("OrgId")			;
			
			Organization orgDetails = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", orgDetails);
			//check permission to record
			if (!isRecordAccessPermitted(context, db,orgId)) {
				return ("PermissionError");
			}
			
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
		if (!hasPermission(context, "distributori-distributori-tamponi-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandAdd(context,db);
			

			// Load the organization
			Organization thisOrganization = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "AddTicket", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource",
					context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute("systemStatus",this.getSystemStatus(context));
		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "distributori-distributori-tamponi-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		
		try {
			db = this.getConnection(context);

			
			Ticket newTic = (Ticket) context.getFormBean();
			Organization thisOrg = new Organization(db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", thisOrg);
			
			context.getRequest().setAttribute("ragione_sociale", thisOrg.getName());
			
			Tamponi t = new Tamponi () ;
			t.executeCommandInsert(context,db);
			isValid = (Boolean)  context.getRequest().getAttribute("isValid");
			recordInserted = (Boolean)  context.getRequest().getAttribute("recordInserted");
	          
		} catch (Exception e) 
		{
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
		if (!hasPermission(context, "distributori-distributori-tamponi-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandModifyTicket(context,db);
			 
			Integer orgId = (Integer) context.getRequest().getAttribute("OrgId");
			Organization thisOrganization = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			
			//check permission to record
			if (!isRecordAccessPermitted(context, db, orgId)) {
				return ("PermissionError");
			}
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ModifyOK";
		
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		// Parameters
		String ticketId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			User user = this.getUser(context, this.getUserId(context));
			// Load the ticket
			if (context.getRequest().getParameter("companyName") == null) {
				newTic = new Ticket();
				newTic.queryRecord(db, Integer.parseInt(ticketId));
			} else {
				newTic = (Ticket) context.getFormBean();
				if (newTic.getOrgId() != -1) {
					
				}
			}

			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				return ("PermissionError");
			}

			

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

	

			LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
			TipoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoTampone", TipoTampone);

			LookupList EsitoTampone = new LookupList(db,
					"lookup_esito_tampone");
			EsitoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoTampone", EsitoTampone);

			LookupList DestinatarioTampone = new LookupList(db,
					"lookup_destinazione_tampone");
			DestinatarioTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("DestinatarioTampone",
					DestinatarioTampone);

			Organization orgDetails = new Organization(db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", orgDetails);

			context.getRequest().setAttribute("TicketDetails", newTic);
			addRecentItem(context, newTic);

			// getting current date in mm/dd/yyyy format
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
		

		return getReturn(context, retPage);
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
			// check wether or not the product id exists
			
			// Load the ticket state

			LookupList TipoTampone = new LookupList(db, "lookup_provvedimenti");
			TipoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoTampone", TipoTampone);

			LookupList EsitoTampone = new LookupList(db,
					"lookup_sanzioni_amministrative");
			EsitoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoTampone", EsitoTampone);

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
		String tipo_richiesta = newTic.getTipo_richiesta();
		tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

		retPage = "DettaglioOK";

		

		return (retPage);
	}

	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-tamponi-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
	
		try {
			db = this.getConnection(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandChiudi(context,db);
			
			resultCount 	= (Integer) context.getRequest().getAttribute("resultCount");
		    Integer OrgId	= (Integer) context.getRequest().getAttribute("OrgId");
			
			//check permission to record
			if (!isRecordAccessPermitted(context, db,OrgId)) {
				return ("PermissionError");
			}
			
		
			

				      if (resultCount == -1) {
				    	  return (executeCommandTicketDetails(context));
				      } else if (resultCount == 1) {
				    	 
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
