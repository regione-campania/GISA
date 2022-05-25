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
package org.aspcfs.modules.opu.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.actions.CFSModule;

import org.aspcfs.modules.base.DependencyList;
import java.util.*;


import org.aspcfs.modules.altriprovvedimenti.base.Ticket;
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
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AltreNonConformita extends CFSModule {

	/**
	 * Sample action for prototyping, by including a specified page
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeDefault(ActionContext context) {
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
		if (!hasPermission(context, "accounts-accounts-nonconformita-edit")) {
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
		if (!hasPermission(context, "accounts-accounts-nonconformita-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		String retPag = null;
		try {
			
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nonconformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
			db = this.getConnection(context);
			nonconformita.executeCommandTicketDetails(context,db);
			Ticket newTic = (Ticket)context.getRequest().getAttribute("TicketDetails");
			
			retPag = "DettaglioOK";


			// Load the organization for the header
			Stabilimento thisOrganization = new Stabilimento(db, newTic.getIdStabilimento());
			
			thisOrganization.getPrefissoAction(context.getAction().getActionName());
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
		if (!hasPermission(context, "nonconformita-nonconformita-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		//Parameters
		String id = context.getRequest().getParameter("id");
		//int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			Ticket ticket = new Ticket(db, Integer.parseInt(id));
			int orgId = ticket.getIdStabilimento();
			//check permission to record
			
		      Stabilimento stab = new Stabilimento(db,ticket.getIdStabilimento());
		      stab.getPrefissoAction(context.getAction().getActionName());

			DependencyList dependencies = ticket.processDependencies(db);
			//Prepare the dialog based on the dependencies
			HtmlDialog htmlDialog = new HtmlDialog();
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			htmlDialog.addMessage(
					systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			htmlDialog.addButton(
					systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='"+stab.getAction()+"NonConformita.do?command=DeleteTicket&stabId="+orgId+"&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
		if (!hasPermission(context, "accounts-accounts-nonconformita-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;
		//Parameters
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			
			Stabilimento thisOrganization = new Stabilimento(db, orgId);
			thisOrganization.getPrefissoAction(context.getAction().getActionName());

			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
			
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
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
				context.getRequest().setAttribute("OrgDetails", thisOrganization);
				context
				.getRequest()
				.setAttribute(
						"refreshUrl",
						thisOrganization.getAction()+ "Vigilanza.do?command=TicketDetails&id="+id_controllo+"&orgId="
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


	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandRestoreTicket(ActionContext context) {

		if (!(hasPermission(context, "tickets-tickets-edit"))) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(
					db, Integer.parseInt(context.getRequest().getParameter("id")));
			//check permission to record
			
			thisTicket.setModifiedBy(getUserId(context));
			recordUpdated = thisTicket.updateStatus(
					db, false, this.getUserId(context));
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (recordUpdated) {
			return (executeCommandTicketDetails(context));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}



	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-nonconformita-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
			nonConformita.executeCommandUpdateTicket(context,db);
			
			Stabilimento orgDetails = new Stabilimento(db, Integer.parseInt(context.getParameter("stabId")));
			orgDetails.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", orgDetails);


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		return (executeCommandTicketDetails(context));
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

		String padded=idCU;
		int id_cu = -1;
		while(idCU.startsWith("0"))
		{
			idCU = idCU.substring(1);
		}
		id_cu = Integer.parseInt(idCU);
		pst =db.prepareStatement(update);

		pst.setString(1, padded);
		pst.setInt(2, id_cu);
		pst.execute();
	}

	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 * @deprecated Replaced combobox with a pop-up
	 */
	

	//aggiunto da d.dauria
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-nonconformita-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {

			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
			db = this.getConnection(context);
			nonConformita.executeCommandAdd(context,db);
			
			Stabilimento thisOrganization = new Stabilimento(db, Integer.parseInt(context.getParameter("stabId")));
			thisOrganization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", thisOrganization);



		} catch (Exception e) {
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("AddOK");
	}


	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-nonconformita-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTicket = null;
		Ticket newTic = (Ticket) context.getFormBean();
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		try {
			db = this.getConnection(context);
			//db.commit();
			Stabilimento thisOrg = new Stabilimento(db, newTic.getIdStabilimento());
			thisOrg.getPrefissoAction(context.getAction().getActionName());
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nc = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
			nc.executeCommandInsert(context,db);


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
		if (!hasPermission(context, "accounts-accounts-nonconformita-edit")) {
			return ("PermissionError");
		}				
		Connection db = null;

		try {

			db = this.getConnection(context);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
			nonConformita.executeCommandModifyTicket(context,db);
			Ticket newTic = (Ticket)context.getRequest().getAttribute("TicketDetails");
			
			Stabilimento thisOrganization = new Stabilimento(db, newTic.getIdStabilimento());
			thisOrganization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", thisOrganization);


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




	public String executeCommandDettaglio(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
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
			newTic =  new Ticket(db,Integer.parseInt(ticketId));
			newTic.queryRecord(db, Integer.parseInt(ticketId));

			

			Stabilimento orgDetails = new Stabilimento(db, newTic.getIdStabilimento());
		
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			Provvedimenti.removeElementByLevel(9);
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

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
		if (!(hasPermission(context, "accounts-accounts-nonconformita-edit"))){
			return ("PermissionError");
		}
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformitaOpu();
		nonConformita.executeCommandChiudi(context,db);
		
		Integer flag 				= (Integer)context.getRequest().getAttribute("flag");
		
		Integer numSottoAttivita 	= (Integer)context.getRequest().getAttribute("numSottoAttivita");
		if(flag==1  ){
			context.getRequest().setAttribute("Chiudi", "1");
			return (executeCommandTicketDetails(context));

		}

		if(numSottoAttivita != null && numSottoAttivita == 0 ){

			context.getRequest().setAttribute("numSottoAttivita", "0");
			return (executeCommandTicketDetails(context));

		}
		
		if(context.getRequest().getAttribute("attivitaCollegate")==null){
			if(flag==1 || flag==2){
				context.getRequest().setAttribute("Chiudi", ""+flag);
				return (executeCommandTicketDetails(context));

			}
		}
		Integer resultCount 	= (Integer)context.getRequest().getAttribute("resultCount");
		 if (resultCount!=null && resultCount == -1) {
			 return ( executeCommandTicketDetails(context));
		 } else if (resultCount!=null && resultCount == 1) {
			 return (executeCommandTicketDetails(context));
		 }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		 return ("UserError");
		
	}


		

}
