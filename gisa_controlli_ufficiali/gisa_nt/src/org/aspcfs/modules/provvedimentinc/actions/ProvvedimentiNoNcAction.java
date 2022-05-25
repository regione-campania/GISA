/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.provvedimentinc.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
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

import org.aspcfs.modules.prvvedimentinc.base.Ticket ;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class ProvvedimentiNoNcAction  extends CFSModule {
	public String executeCommandAdd(ActionContext context,Connection db) {
		if (!hasPermission(context, "accounts-accounts-followup-add")) {
			return ("PermissionError");
		}
		Ticket newTic = null;
		try {
			String tipo_nc = context.getRequest().getParameter("tipoNc");
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti_no_nc");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",id);

			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			String temporgId = context.getRequest().getParameter("orgId");
			
			int tempid = Integer.parseInt(temporgId);
			org.aspcf.modules.controlliufficiali.base.Organization organization = new org.aspcf.modules.controlliufficiali.base.Organization(db,Integer.parseInt(id));
			context.getRequest().setAttribute("OrgDetails", organization);
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		} catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} 


		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context,Connection db) throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-followup-add"))) {
			return ("PermissionError");
		}
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		Ticket newTicket = null;

		String newContact = context.getRequest().getParameter("contact");

		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		Ticket newTic = (Ticket) context.getFormBean();
		
		String idControlloUfficiale = (String.valueOf(Integer.parseInt(id)));
		while (idControlloUfficiale.length() < 6) {
			idControlloUfficiale = "0" + idControlloUfficiale;
		}
		newTic.setIdControlloUfficiale(idControlloUfficiale);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newTic.setIpEntered(ip);
		newTic.setIpModified(ip);
		String site =  context.getRequest().getParameter("siteId");
		if(site.equals("201")){
			site = "AV";	
		}else if(site.equals("202")){
			site = "BN";
		}else if(site.equals("203")){
			site = "CE";
		}else if(site.equals("204")){
			site = "NA1C";
		}else if(site.equals("205")){
			site = "NA2N";
		}else if(site.equals("206")){
			site = "NA3S";
		}else if(site.equals("207")){
			site = "SA";
		}
		else{
			if(site.equals(16))
			{
				site ="FuoriRegione";
			}
		}
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String idC = context.getRequest().getParameter("idC");
		
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));
		try {

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti_no_nc");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			Provvedimenti.setMultiple(true);
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			

			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				
				String[] limitazioni=context.getRequest().getParameterValues("provvedimenti");

				recordInserted = newTic.insert(db,context);
				newTic.insertLimitazioniFollowup(db, limitazioni);
			}
				context.getRequest().setAttribute("recordInserted", recordInserted);
				context.getRequest().setAttribute("isValid", isValid);

			if (recordInserted) {

				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);
				


			} 

	


		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		if (recordInserted && isValid) {
		
			String retPage = "DettaglioOK";
			
			
		}


		return "AddOK"; 
	}


	/**
	 * Load the ticket details tab
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context,Connection db)
	{
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		String tickId = context.getRequest().getParameter("ticketId");
		String retPag = null;
		try {
			// Load the ticket
			Ticket newTic = new Ticket();
			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			if(tickId == null)
				newTic.queryRecord(db, Integer.parseInt(ticketId));
			else
				newTic.queryRecord(db, Integer.parseInt(tickId));
			context.getRequest().setAttribute("stabId", newTic.getIdStabilimento());
			//find record permissions for portal users
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
			context.getRequest().setAttribute("CU", cu);

			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",
					id);

			String id2 = context.getRequest().getParameter("idNC");
			context.getRequest().setAttribute("idNC",
					id2);


			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti_no_nc");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			Provvedimenti.setMultiple(true);
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList multipleSelects=new LookupList();

			HashMap<Integer, String> listaLimitazioni=newTic.getListaLimitazioniFollowup();
			Iterator<Integer> iterakiavi=listaLimitazioni.keySet().iterator();
			while(iterakiavi.hasNext()){
				int kiave=iterakiavi.next();
				String v=listaLimitazioni.get(kiave);
				multipleSelects.addItem(kiave, v);



			}

			context.getRequest().setAttribute("orgId", newTic.getOrgId());
			
		
			context.getRequest().setAttribute("TicketDetails", newTic);

			retPag = "DettaglioOK";

			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		//return getReturn(context, "TicketDetails");
		return "";
	}



	/**
	 * Loads the ticket for modification
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context,Connection db) {

		Ticket newTic = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			User user = this.getUser(context, this.getUserId(context));
			//Load the ticket
			if (context.getRequest().getParameter("companyName") == null) {
				newTic = new Ticket(db, Integer.parseInt(ticketId));
			} else {
				newTic = (Ticket) context.getFormBean();
				
			}
			//check permission to record

			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",
					id);

			String id2 = context.getRequest().getParameter("idNC");
			context.getRequest().setAttribute("idNC",
					id2);
			//Load the organization

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			
			context.getRequest().setAttribute("orgId", newTic.getOrgId());


			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti_no_nc");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			Provvedimenti.setMultiple(true);
			Provvedimenti.setSelectSize(7);
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);



			LookupList multipleSelects=new LookupList();

			HashMap<Integer, String> listaLimitazioni=newTic.getListaLimitazioniFollowup();
			Iterator<Integer> iterakiavi=listaLimitazioni.keySet().iterator();
			while(iterakiavi.hasNext()){
				int kiave=iterakiavi.next();
				String v=listaLimitazioni.get(kiave);
				multipleSelects.addItem(kiave, v);



			}
			Provvedimenti.setMultipleSelects(multipleSelects);


			context.getRequest().setAttribute("TicketDetails", newTic);

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		//return "ModifyautorizzazionetrasportoanimaliviviOK";
		return "";
		//return getReturn(context, "ModifyTicket");
	}



	/**
	 * Re-opens a ticket
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-followup-edit")) {
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

	
			context.getRequest().setAttribute("resultCount", resultCount);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == -1) {
			return (executeCommandTicketDetails(context,db));
		} else if (resultCount == 1) {
			this.processUpdateHook(context, oldTicket, thisTicket);
			return (executeCommandTicketDetails(context,db));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}


	public String executeCommandChiudi(ActionContext context,Connection db)
	{
		if (!(hasPermission(context, "accounts-accounts-followup-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		
		Ticket thisTicket = null;
		try {
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
				return ( executeCommandTicketDetails(context,db));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);

			
				return (executeCommandTicketDetails(context,db));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}



	/**
	 * Confirm the delete operation showing dependencies
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context,Connection db){
		if (!hasPermission(context, "accounts-accounts-followup-delete")) {
			return ("PermissionError");
		}
	
		//Parameters
		String id = context.getRequest().getParameter("id");
		//int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		try {
			
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
					systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountProvvedimenti.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
							context.getRequest(), "popup|popupType|actionId") + "'");
			htmlDialog.addButton(
					systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
			context.getSession().setAttribute("Dialog", htmlDialog);
			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
	}





	public String executeCommandUpdateTicket(ActionContext context,Connection db){
		
		int resultCount = 0;

		int catCount = 0;
		TicketCategory thisCat = null;
		boolean catInserted = false;
		boolean isValid = true;

		Ticket  newTic = (Ticket) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newTic.setIpEntered(ip);
		newTic.setIpModified(ip);
		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",
				id);

		String id2 = context.getRequest().getParameter("idNC");
		context.getRequest().setAttribute("idNC",
				id2);

	

		try {

			Ticket oldTic = new Ticket(db, newTic.getId());

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			//Get the previousTicket, update the ticket, then send both to a hook
			Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
			newTic.setModifiedBy(getUserId(context));
			newTic.setSiteId(context.getRequest().getParameter("siteId"));
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				resultCount = newTic.update(db);
				String[] limitazioni=context.getRequest().getParameterValues("limitazioniFollowup");
				newTic.updateLimitazioneFollowup(db, limitazioni);

			}
			context.getRequest().setAttribute("resultCount", resultCount);
			context.getRequest().setAttribute("isValid", isValid);
			if (resultCount == 1) {
				newTic.queryRecord(db, newTic.getId());
				processUpdateHook(context, previousTicket, newTic);
//				TicketCategoryList ticketCategoryList = new TicketCategoryList();
//				ticketCategoryList.setEnabledState(Constants.TRUE);
//				ticketCategoryList.setSiteId(newTic.getSiteId());
//				ticketCategoryList.setExclusiveToSite(true);
//				ticketCategoryList.buildList(db);
//				context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
			}
			Integer idtampone = newTic.getId();
			//				BeanSaver.save( null, newTic, newTic.getId(),
			//			      		"ticket", context, db, null, "O.S.A: Modifica Follow", idtampone.toString() );

	
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 


		return "";
	}




}
