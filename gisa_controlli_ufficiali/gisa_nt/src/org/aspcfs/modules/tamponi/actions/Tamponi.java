/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.tamponi.actions;


import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.accounts.base.*;
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
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.troubletickets.base.*;
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
 * Maintains Tickets related to an Account
 * 
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp $
 * @created August 15, 2001
 */
public final class Tamponi extends CFSModule {

	



	

	/**
	 * Re-opens a ticket
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		Ticket oldTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.reopen(db);
			thisTicket.queryRecord(db, thisTicket.getId());
			context.getRequest().setAttribute("resultCount",resultCount);
					
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == 1) {
			this.processUpdateHook(context, oldTicket, thisTicket);
		}
		return "" ;
	}

	
	/**
	 * Load the ticket details tab
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context,Connection db){
		
	
		// Parameters
		String ticketId = context.getRequest().getParameter("id");
		String retPag = null;
		try {
			
			if(context.getRequest().getAttribute("Messaggio")!=null)
			{
			
				context.getRequest().setAttribute("Messaggio", context.getRequest().getAttribute("Messaggio"));
			}
			
			if(context.getRequest().getAttribute("Messaggio2")!=null)
			{
				
				context.getRequest().setAttribute("Messaggio2", context.getRequest().getAttribute("Messaggio2"));
			}
			
			
			// Load the ticket
			Ticket newTic = new org.aspcfs.modules.tamponi.base.Ticket();
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			newTic.queryRecord(db, Integer.parseInt(ticketId));
			context.getRequest().setAttribute("orgId",newTic.getOrgId());
			context.getRequest().setAttribute("stabId",newTic.getIdStabilimento());
			context.getRequest().setAttribute("idApiario", newTic.getIdApiario());

			  if (context.getRequest().getAttribute("ActionString")!=null)
              	newTic.setAction(""+context.getRequest().getAttribute("ActionName"));

			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
			context.getRequest().setAttribute("CU", cu);

			LookupList DestinatarioTampone = new LookupList(db,
			"lookup_destinazione_tampone");
	DestinatarioTampone.addItem(-1, systemStatus
			.getLabel("calendar.none.4dashes"));
	context.getRequest().setAttribute("DestinatarioTampone",
			DestinatarioTampone);

			LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
			TipoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoTampone", TipoTampone);

			LookupList EsitoTampone = new LookupList(db,
					"lookup_esito_tampone");
			EsitoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoTampone", EsitoTampone);
			
	
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			SiteIdList.addItem(-2, "-- TUTTI --"
					);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			
			 String id_controllo=newTic.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
			
			context.getRequest().setAttribute("TicketDetails", newTic);
			addRecentItem(context, newTic);
			// Load the organization for the header
			addModuleBean(context, "View Accounts", "View Tickets");
			// Reset any pagedLists since this could be a new visit to this
			// ticket
			deletePagedListInfo(context, "AccountTicketsFolderInfo");
			deletePagedListInfo(context, "AccountTicketDocumentListInfo");
			deletePagedListInfo(context, "AccountTicketTaskListInfo");
			deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			
		} 
		// return getReturn(context, "TicketDetails");
		return "";
	}

	
	/**
	 * Delete the specified ticket
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDeleteTicket(ActionContext context,Connection db){
		
		boolean recordDeleted = false;
		
		// Parameters

		String passedId = context.getRequest().getParameter("id");
		try {
			
			
			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
				return ("PermissionError");
			}
			
			 String id_controllo=thisTic.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			
			
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			context.getRequest().setAttribute("idControllo", id_controllo);
			
			if (recordDeleted) 
			{
				processDeleteHook(context, thisTic);
				//del
			}
			

				
			context.getRequest().setAttribute("idC", id_controllo);
			context.getRequest().setAttribute("recordDeleted", recordDeleted);
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			
		} 
		return "" ;
	}

	
	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandRestoreTicket(ActionContext context) {

		if (!(hasPermission(context, "accounts-accounts-tamponi-edit"))) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Connection db = null;
		Ticket thisTicket = null;
		try 
		{
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			thisTicket.setModifiedBy(getUserId(context));
			recordUpdated = thisTicket.updateStatus(db, false, this.getUserId(context));
			context.getRequest().setAttribute("recordUpdated", recordUpdated);
			context.getRequest().setAttribute("OrgId", thisTicket.getOrgId());
			
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		return "" ;
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
	
	
	public String executeCommandUpdateTicket(ActionContext context,Connection db){
		
		int resultCount = 0;

		int catCount = 0;
		
		boolean catInserted = false;
		boolean isValid = true;

		Ticket newTic = (Ticket) context.getFormBean();
		newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		 String id_controllo=newTic.getIdControlloUfficiale();
			while(id_controllo.startsWith("0")){
				
				id_controllo=id_controllo.substring(1);
			}

		context.getRequest().setAttribute("idC", id_controllo);
	
		try {
			

			Ticket oldTic = new Ticket(db, newTic.getId());
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
			TipoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoTampone", TipoTampone);

			LookupList EsitoTampone = new LookupList(db,
					"lookup_esito_tampone");
			EsitoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoTampone", EsitoTampone);
            
			//aggiunto da d.dauria
			
	         
		   

			
			LookupList DestinatarioTampone = new LookupList(db,
					"lookup_destinazione_tampone");
			DestinatarioTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("DestinatarioTampone",
					DestinatarioTampone);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			SiteIdList.addItem(-2, "-- TUTTI --"
					);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			// Get the previousTicket, update the ticket, then send both to a
			// hook
			Ticket previousTicket = new Ticket(db, Integer.parseInt(context
					.getParameter("id")));
			newTic.setModifiedBy(getUserId(context));
			newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  			      
				resultCount = newTic.update(db);
				
				newTic.aggiornaRicerca_EsitoTampone(db);
				newTic.aggiornaTamponi(db);
for(int i=0;i<10;i++){
					
					
					int a=i+1;
					String tipoTampone=context.getRequest().getParameter("check1_"+a);
					if(tipoTampone!=null){
						if(tipoTampone.equals("1")){
							
							
							String s=context.getRequest().getParameter("Tamponi"+a);
							int idTampone=newTic.insertTamponi(db, "1", "",Integer.parseInt(s));
							String [] ss=context.getRequest().getParameterValues("RicercaTamponi_"+a);
							
						
							
							for(int j=0;j<ss.length;j++){
								int b=j+1;
								String esito=context.getRequest().getParameter("esitoTampone"+a+"_"+ss[j]);
								newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
								
								
								
							}
												
						}
						else{
							
							tipoTampone=context.getRequest().getParameter("check2_"+a);
							if(tipoTampone!=null){
							if(tipoTampone.equals("2")){
								String s=context.getRequest().getParameter("superfice"+a);
								int idTampone=newTic.insertTamponi(db, "2", s,-1);
								String [] ss=context.getRequest().getParameterValues("RicercaTamponi2_"+a);
								
								for(int j=0;j<ss.length;j++){
									
									String esito=context.getRequest().getParameter("esito_"+ss[j]+"_tampone"+a);
									newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
									
								}
								
								
							}
							
							}
							
						}
					}else{
						
						tipoTampone=context.getRequest().getParameter("check2_"+a);
						if(tipoTampone!=null){
						if(tipoTampone.equals("2")){
							String s=context.getRequest().getParameter("superfice"+a);
							int idTampone=newTic.insertTamponi(db, "2", s,-1);
							String [] ss=context.getRequest().getParameterValues("RicercaTamponi2_"+a);
							
							
							for(int j=0;j<ss.length;j++){
								
								String esito=context.getRequest().getParameter("esito_"+ss[j]+"_tampone"+a);
								newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
								
							}
							
						}
						
						}
						
					}
				
				}
				
				
			}
			
			aggiornaPunteggio(db,newTic.getId());
			if (resultCount == 1) {
				newTic.queryRecord(db, newTic.getId());
				processUpdateHook(context, previousTicket, newTic);
				
			}
				
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 

		context.getRequest().setAttribute("resultCount", resultCount);
		context.getRequest().setAttribute("isValid", isValid);
		context.getRequest().setAttribute("OrgId", newTic.getOrgId());
		context.getRequest().setAttribute("stabId", newTic.getIdStabilimento());
		context.getRequest().setAttribute("idApiario", newTic.getIdApiario());

		
		return "";
	}

	// aggiunto da d.dauria
	public String executeCommandAdd(ActionContext context,Connection db){
		
		
		Ticket newTic = null;
		try {
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",idC);

			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(idC));
			context.getRequest().setAttribute("CU", cu);
			LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
			TipoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoTampone", TipoTampone);

			LookupList Tamponi = new LookupList(db, "lookup_tamponi");
			Tamponi.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Tamponi", Tamponi);
			
			context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
		
			
			LookupList RicercaTamponi_1 = new LookupList(db, "lookup_ricerca_tamponi");
			RicercaTamponi_1.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_1.setMultiple(true);
			RicercaTamponi_1.setSelectSize(7);
			RicercaTamponi_1.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_1", RicercaTamponi_1);
			
			LookupList RicercaTamponi_2 = new LookupList(db, "lookup_ricerca_tamponi");
			RicercaTamponi_2.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_2.setMultiple(true);
			RicercaTamponi_2.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_2", RicercaTamponi_2);
			
LookupList RicercaTamponi_3 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_3.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_3.setMultiple(true);
			RicercaTamponi_3.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_3", RicercaTamponi_3);
			
LookupList RicercaTamponi_4 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_4.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_4.setMultiple(true);
			RicercaTamponi_4.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_4", RicercaTamponi_4);
			
LookupList RicercaTamponi_5 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_5.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_5.setMultiple(true);
			RicercaTamponi_5.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_5", RicercaTamponi_5);
			
LookupList RicercaTamponi_6 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_6.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_6.setMultiple(true);
			RicercaTamponi_6.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_6", RicercaTamponi_6);
			
LookupList RicercaTamponi_7 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_7.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_7.setMultiple(true);
			RicercaTamponi_7.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_7", RicercaTamponi_7);
			
LookupList RicercaTamponi_8 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_8.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_8.setMultiple(true);
			RicercaTamponi_8.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_8", RicercaTamponi_8);
			
LookupList RicercaTamponi_9 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_9.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_9.setMultiple(true);
			RicercaTamponi_9.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_9", RicercaTamponi_9);
			
LookupList RicercaTamponi_10 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi_10.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi_10.setMultiple(true);
			RicercaTamponi_10.setSelectSize(7);
			//RicercaTamponi.setJsEvent("onSelect=javascript:showEsiti()");
			context.getRequest().setAttribute("RicercaTamponi_10", RicercaTamponi_10);
			
			
			LookupList RicercaTamponi2_1 = new LookupList(db, "lookup_ricerca_tamponi");
			RicercaTamponi2_1.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_1.setMultiple(true);
			RicercaTamponi2_1.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_1", RicercaTamponi2_1);
			
LookupList RicercaTamponi2_2 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_2.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_2.setMultiple(true);
			RicercaTamponi2_2.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_2", RicercaTamponi2_2);
			
LookupList RicercaTamponi2_3 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_3.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_3.setMultiple(true);
			RicercaTamponi2_3.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_3", RicercaTamponi2_3);
			
LookupList RicercaTamponi2_4 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_4.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_4.setMultiple(true);
			RicercaTamponi2_4.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_4", RicercaTamponi2_4);
			
LookupList RicercaTamponi2_5 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_5.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_5.setMultiple(true);
			RicercaTamponi2_5.setSelectSize(7);
		
			context.getRequest().setAttribute("RicercaTamponi2_5", RicercaTamponi2_5);
			
LookupList RicercaTamponi2_6 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_6.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_6.setMultiple(true);
			RicercaTamponi2_6.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_6", RicercaTamponi2_6);
			
LookupList RicercaTamponi2_7 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_7.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_7.setMultiple(true);
			RicercaTamponi2_7.setSelectSize(7);
			
		context.getRequest().setAttribute("RicercaTamponi2_7", RicercaTamponi2_7);
			
LookupList RicercaTamponi2_8 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_8.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_8.setMultiple(true);
			RicercaTamponi2_8.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_8", RicercaTamponi2_8);
			
LookupList RicercaTamponi2_9 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_9.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_9.setMultiple(true);
			RicercaTamponi2_9.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_9", RicercaTamponi2_9);
			
LookupList RicercaTamponi2_10 = new LookupList(db, "lookup_ricerca_tamponi");
RicercaTamponi2_10.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			RicercaTamponi2_10.setMultiple(true);
			RicercaTamponi2_10.setSelectSize(7);
			
			context.getRequest().setAttribute("RicercaTamponi2_10", RicercaTamponi2_10);
			
			
			

			
			LookupList DestinatarioTampone = new LookupList(db,
					"lookup_destinazione_tampone");
			DestinatarioTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("DestinatarioTampone",
					DestinatarioTampone);

			LookupList EsitoTampone = new LookupList(db,
					"lookup_esito_tampone");
			EsitoTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("EsitoTampone", EsitoTampone);
			
			//aggiunto da d.dauria
			LookupList titoloNucleo = new LookupList(db,
			"lookup_nucleo_ispettivo");
	         titoloNucleo.addItem(-1, systemStatus
						.getLabel("calendar.none.4dashes"));
          	context.getRequest().setAttribute("TitoloNucleo",
			titoloNucleo);
	
          	String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			context.getRequest().setAttribute("idControllo",
					idControlloUfficiale);
			context.getRequest().setAttribute("idC",
					idC);

			
			
			

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			SiteIdList.addItem(-2, "-- TUTTI --"
					);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			
				newTic = new Ticket();
			

			// getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute("systemStatus",
					this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
		
		
		} 
		addModuleBean(context, "AddTicket", "Ticket Add");
		return "" ;
	}

	public String executeCommandInsert(ActionContext context,Connection db)
			throws SQLException {
		
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		Ticket newTicket = null;

		

		String idCU = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",
				idCU);
		
		Ticket newTic = (Ticket) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		String site =  context.getRequest().getParameter("siteId");
		newTic.setDestinatarioTampone(context.getRequest().getParameter("DestinatarioTampone"));
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
		newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
		
		String idCampione = site+idControllo;
		newTic.setTipo_richiesta(context.getRequest().getParameter(
				"tipo_richiesta"));
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));

		

		try {
		
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
	
			LookupList DestinatarioTampone = new LookupList(db,
					"lookup_destinazione_tampone");
			DestinatarioTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("DestinatarioTampone",
					DestinatarioTampone);

			 
			
				if (newTic.getOrgId() > 0) {
					newTic.setSiteId(context.getRequest().getParameter("siteId"));
				}
				isValid = this.validateObject(context, db, newTic) && isValid;
				if (isValid) {
					recordInserted = newTic.insert(db,context);
					
for(int i=0;i<10;i++){
						
						
						int a=i+1;
						String tipoTampone=context.getRequest().getParameter("check1_"+a);
						if(tipoTampone!=null){
							if(tipoTampone.equals("1")){
								
								
								String s=context.getRequest().getParameter("Tamponi"+a);
								int idTampone=newTic.insertTamponi(db, "1", "",Integer.parseInt(s));
								String [] ss=context.getRequest().getParameterValues("RicercaTamponi_"+a);
								
								
								for(int j=0;j<ss.length;j++){
									int b=j+1;
									String esito=context.getRequest().getParameter("esitoTampone"+a+"_"+ss[j]);
									newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
									
									
								}
								/*
								 * 
								 *  
  
								 * 
								 * 
								 */
								
								
								
								
							}
							else{
								
								tipoTampone=context.getRequest().getParameter("check2_"+a);
								if(tipoTampone!=null){
								if(tipoTampone.equals("2")){
									
									
									String s=context.getRequest().getParameter("superfice"+a);
									int idTampone=newTic.insertTamponi(db, "2", s,-1);
									String [] ss=context.getRequest().getParameterValues("RicercaTamponi2_"+a);
									
									
									for(int j=0;j<ss.length;j++){
										
										String esito=context.getRequest().getParameter("esito_"+ss[j]+"_tampone"+a);
										newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
										
									}
									
								}
								
								}
								
							}
						}else{
							
							tipoTampone=context.getRequest().getParameter("check2_"+a);
							if(tipoTampone!=null){
							if(tipoTampone.equals("2")){
								String s=context.getRequest().getParameter("superfice"+a);
								int idTampone=newTic.insertTamponi(db, "2", s,-1);
								String [] ss=context.getRequest().getParameterValues("RicercaTamponi2_"+a);
								
								
								for(int j=0;j<ss.length;j++){
									
									String esito=context.getRequest().getParameter("esito_"+ss[j]+"_tampone"+a);
									newTic.insertRicerca_EsitoTampone(db, ss[j], esito, idTampone);
									
								}
							}
							
							}
							
						}
					
					}
					
				}
			

			if (recordInserted) {
				newTic.setPunteggio(context.getRequest().getParameter("punteggio"));
				org.aspcfs.modules.vigilanza.base.Ticket controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));
				int puntiV = controlloUff.getPunteggio();
				int puntiN = puntiV + newTic.getPunteggio();
				
				controlloUff.setPunteggio(puntiN);
				controlloUff.update(db);
				// Prepare the ticket for the response
				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);


				addRecentItem(context, newTicket);

				processInsertHook(context, newTicket);
			} else {
				if (newTic.getOrgId() != -1) {
					Organization thisOrg = new Organization(db, newTic
							.getOrgId());
					newTic.setCompanyName(thisOrg.getName());
				}
			}
	
	    			
	          context.getRequest().setAttribute("isValid",isValid);
	          context.getRequest().setAttribute("recordInserted", recordInserted);
	          
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			
		} 
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		return "" ;
	}

	/**
	 * Loads the ticket for modification
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context,Connection db){
		
		Ticket newTic = null;
		// Parameters
		String ticketId = context.getRequest().getParameter("id");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			User user = this.getUser(context, this.getUserId(context));
			// Load the ticket
			if (context.getRequest().getParameter("companyName") == null) {
				newTic = new Ticket(db, Integer.parseInt(ticketId));
			} else {
				newTic = (Ticket) context.getFormBean();
			}
			//check permission to record
			
			LookupList Tamponi = new LookupList(db, "lookup_tamponi");
			Tamponi.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Tamponi", Tamponi);
			
		
		
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			SiteIdList.addItem(-2, "-- TUTTI --"
					);
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
		
	         
	
			
			// aggiunto da d.dauria
			LookupList conseguenzepositivita = new LookupList(db,
					"lookup_conseguenze_positivita");
			conseguenzepositivita.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ConseguenzePositivita",
					conseguenzepositivita);
			
			
			// aggiunto da d.dauria
			LookupList responsabilitapositivita = new LookupList(db,
					"lookup_responsabilita_positivita");
			responsabilitapositivita.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ResponsabilitaPositivita",
					responsabilitapositivita);
			
			LookupList DestinatarioTampone = new LookupList(db,
					"lookup_destinazione_tampone");
			DestinatarioTampone.addItem(-1, systemStatus
					.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("DestinatarioTampone",
					DestinatarioTampone);

		
		

			// Put the ticket in the request
			addRecentItem(context, newTic);
			context.getRequest().setAttribute("TicketDetails", newTic);
			addModuleBean(context, "View Accounts", "View Tickets");

			// getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);

			 String id_controllo = context.getRequest().getParameter("idC");
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
				
			HashMap<Integer, Tampone> listaTamponi=newTic.getListaTamponi();
			
			LookupList RicercaTamponi_1 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_1.setMultiple(true);
			RicercaTamponi_1.setSelectSize(7);
			RicercaTamponi_1.setJsEvent("onSelect=javascript:showEsiti()");
			
				LookupList multipleSelects=new LookupList();
		      
		      
		     if(listaTamponi.get(1)!=null){
		      HashMap<Integer, String> ListaRicercaTampone1=listaTamponi.get(1).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone1= ListaRicercaTampone1.keySet().iterator();
		      while(iteraKiaviRicercaTampone1.hasNext()){
		      int kiave=iteraKiaviRicercaTampone1.next();
		      String valore=ListaRicercaTampone1.get(kiave);
		    	  
		    	  multipleSelects.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_1.setMultipleSelects(multipleSelects);
		     }
			
			context.getRequest().setAttribute("RicercaTamponi_1", RicercaTamponi_1);
			
			LookupList RicercaTamponi_2 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_2.setMultiple(true);
			RicercaTamponi_2.setSelectSize(7);
		
			
			LookupList multipleSelects2=new LookupList();
		      
		      
		     if(listaTamponi.get(2)!=null){
		      HashMap<Integer, String> ListaRicercaTampone2=listaTamponi.get(2).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone2= ListaRicercaTampone2.keySet().iterator();
		      while(iteraKiaviRicercaTampone2.hasNext()){
		      int kiave=iteraKiaviRicercaTampone2.next();
		      String valore=ListaRicercaTampone2.get(kiave);
		    	  
		    	  multipleSelects2.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_2.setMultipleSelects(multipleSelects2);
		     }
			
			context.getRequest().setAttribute("RicercaTamponi_2", RicercaTamponi_2);
			
LookupList RicercaTamponi_3 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_3.setMultiple(true);
			RicercaTamponi_3.setSelectSize(7);
			
			
			LookupList multipleSelects3=new LookupList();
		      
		      
		     if(listaTamponi.get(3)!=null){
		      HashMap<Integer, String> ListaRicercaTampone3=listaTamponi.get(3).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone3= ListaRicercaTampone3.keySet().iterator();
		      while(iteraKiaviRicercaTampone3.hasNext()){
		      int kiave=iteraKiaviRicercaTampone3.next();
		      String valore=ListaRicercaTampone3.get(kiave);
		    	  
		    	  multipleSelects3.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_3.setMultipleSelects(multipleSelects3);
			
		     }
			context.getRequest().setAttribute("RicercaTamponi_3", RicercaTamponi_3);
			
LookupList RicercaTamponi_4 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_4.setMultiple(true);
			RicercaTamponi_4.setSelectSize(7);
			
			LookupList multipleSelects4=new LookupList();
		      
		      
			  if(listaTamponi.get(4)!=null){
		      HashMap<Integer, String> ListaRicercaTampone4=listaTamponi.get(4).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone4= ListaRicercaTampone4.keySet().iterator();
		      while(iteraKiaviRicercaTampone4.hasNext()){
		      int kiave=iteraKiaviRicercaTampone4.next();
		      String valore=ListaRicercaTampone4.get(kiave);
		    	  
		    	  multipleSelects4.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_4.setMultipleSelects(multipleSelects4);
			  }
			
			
			context.getRequest().setAttribute("RicercaTamponi_4", RicercaTamponi_4);
			
LookupList RicercaTamponi_5 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_5.setMultiple(true);
			RicercaTamponi_5.setSelectSize(7);
			
			LookupList multipleSelects5=new LookupList();
		      
		      
			  if(listaTamponi.get(5)!=null){
		      HashMap<Integer, String> ListaRicercaTampone5=listaTamponi.get(5).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone5= ListaRicercaTampone5.keySet().iterator();
		      while(iteraKiaviRicercaTampone5.hasNext()){
		      int kiave=iteraKiaviRicercaTampone5.next();
		      String valore=ListaRicercaTampone5.get(kiave);
		    	  
		    	  multipleSelects5.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_5.setMultipleSelects(multipleSelects5);
			  }
			
			
			context.getRequest().setAttribute("RicercaTamponi_5", RicercaTamponi_5);
			
LookupList RicercaTamponi_6 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_6.setMultiple(true);
			RicercaTamponi_6.setSelectSize(7);
			
			
			LookupList multipleSelects6=new LookupList();
		      
		      
			  if(listaTamponi.get(6)!=null){
		      HashMap<Integer, String> ListaRicercaTampone6=listaTamponi.get(6).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone6= ListaRicercaTampone6.keySet().iterator();
		      while(iteraKiaviRicercaTampone6.hasNext()){
		      int kiave=iteraKiaviRicercaTampone6.next();
		      String valore=ListaRicercaTampone6.get(kiave);
		    	  
		    	  multipleSelects6.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_6.setMultipleSelects(multipleSelects6);
			  }
			
			context.getRequest().setAttribute("RicercaTamponi_6", RicercaTamponi_6);
			
			
			
LookupList RicercaTamponi_7 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_7.setMultiple(true);
			RicercaTamponi_7.setSelectSize(7);
			
			LookupList multipleSelects7=new LookupList();
		      
		      
			  if(listaTamponi.get(7)!=null){
		      HashMap<Integer, String> ListaRicercaTampone7=listaTamponi.get(7).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone7= ListaRicercaTampone7.keySet().iterator();
		      while(iteraKiaviRicercaTampone7.hasNext()){
		      int kiave=iteraKiaviRicercaTampone7.next();
		      String valore=ListaRicercaTampone7.get(kiave);
		    	  
		    	  multipleSelects7.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_7.setMultipleSelects(multipleSelects7);
			  }
			
			context.getRequest().setAttribute("RicercaTamponi_7", RicercaTamponi_7);
			
LookupList RicercaTamponi_8 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_8.setMultiple(true);
			RicercaTamponi_8.setSelectSize(7);
			
			LookupList multipleSelects8=new LookupList();
		      
		      
			  if(listaTamponi.get(8)!=null){ 
		      HashMap<Integer, String> ListaRicercaTampone8=listaTamponi.get(8).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone8= ListaRicercaTampone8.keySet().iterator();
		      while(iteraKiaviRicercaTampone8.hasNext()){
		      int kiave=iteraKiaviRicercaTampone8.next();
		      String valore=ListaRicercaTampone8.get(kiave);
		    	  
		    	  multipleSelects8.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_8.setMultipleSelects(multipleSelects8);
			  }
			
			context.getRequest().setAttribute("RicercaTamponi_8", RicercaTamponi_8);
			
LookupList RicercaTamponi_9 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_9.setMultiple(true);
			RicercaTamponi_9.setSelectSize(7);
			LookupList multipleSelects9=new LookupList();
		      
		      
			  if(listaTamponi.get(9)!=null){
		      HashMap<Integer, String> ListaRicercaTampone9=listaTamponi.get(9).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone9= ListaRicercaTampone9.keySet().iterator();
		      while(iteraKiaviRicercaTampone9.hasNext()){
		      int kiave=iteraKiaviRicercaTampone9.next();
		      String valore=ListaRicercaTampone9.get(kiave);
		    	  
		    	  multipleSelects9.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_9.setMultipleSelects(multipleSelects9);
			  }
			context.getRequest().setAttribute("RicercaTamponi_9", RicercaTamponi_9);
			
LookupList RicercaTamponi_10 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi_10.setMultiple(true);
			RicercaTamponi_10.setSelectSize(7);
			
			LookupList multipleSelects10=new LookupList();
		      
		      
			  if(listaTamponi.get(10)!=null){
		      HashMap<Integer, String> ListaRicercaTampone10=listaTamponi.get(10).getRicerca();
		      Iterator<Integer> iteraKiaviRicercaTampone10= ListaRicercaTampone10.keySet().iterator();
		      while(iteraKiaviRicercaTampone10.hasNext()){
		      int kiave=iteraKiaviRicercaTampone10.next();
		      String valore=ListaRicercaTampone10.get(kiave);
		    	  
		    	  multipleSelects10.addItem(kiave,valore);
		    	  
		      }
		      
		      RicercaTamponi_10.setMultipleSelects(multipleSelects10);
			
			  }
			
			context.getRequest().setAttribute("RicercaTamponi_10", RicercaTamponi_10);
			
			
			LookupList RicercaTamponi2_1 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_1.setMultiple(true);
			RicercaTamponi2_1.setSelectSize(7);
			
			
			RicercaTamponi2_1.setMultipleSelects(multipleSelects);
			
			context.getRequest().setAttribute("RicercaTamponi2_1", RicercaTamponi2_1);
			
LookupList RicercaTamponi2_2 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_2.setMultiple(true);
			RicercaTamponi2_2.setSelectSize(7);
			RicercaTamponi2_2.setMultipleSelects(multipleSelects2);
			
			
			context.getRequest().setAttribute("RicercaTamponi2_2", RicercaTamponi2_2);
			
LookupList RicercaTamponi2_3 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_3.setMultiple(true);
			RicercaTamponi2_3.setSelectSize(7);
			RicercaTamponi2_3.setMultipleSelects(multipleSelects3);
			
			context.getRequest().setAttribute("RicercaTamponi2_3", RicercaTamponi2_3);
			
LookupList RicercaTamponi2_4 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_4.setMultiple(true);
			RicercaTamponi2_4.setSelectSize(7);
			RicercaTamponi2_4.setMultipleSelects(multipleSelects4);
			
			context.getRequest().setAttribute("RicercaTamponi2_4", RicercaTamponi2_4);
			
LookupList RicercaTamponi2_5 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_5.setMultiple(true);
			RicercaTamponi2_5.setSelectSize(7);
			RicercaTamponi2_5.setMultipleSelects(multipleSelects5);
			
			context.getRequest().setAttribute("RicercaTamponi2_5", RicercaTamponi2_5);
			
LookupList RicercaTamponi2_6 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_6.setMultiple(true);
			RicercaTamponi2_6.setSelectSize(7);
			RicercaTamponi2_6.setMultipleSelects(multipleSelects6);
			
			context.getRequest().setAttribute("RicercaTamponi2_6", RicercaTamponi2_6);
			
LookupList RicercaTamponi2_7 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_7.setMultiple(true);
			RicercaTamponi2_7.setSelectSize(7);
			RicercaTamponi2_7.setMultipleSelects(multipleSelects7);
			
		context.getRequest().setAttribute("RicercaTamponi2_7", RicercaTamponi2_7);
			
LookupList RicercaTamponi2_8 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_8.setMultiple(true);
			RicercaTamponi2_8.setSelectSize(7);
			RicercaTamponi2_8.setMultipleSelects(multipleSelects8);
			
			context.getRequest().setAttribute("RicercaTamponi2_8", RicercaTamponi2_8);
			
LookupList RicercaTamponi2_9 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_9.setMultiple(true);
			RicercaTamponi2_9.setSelectSize(7);
			RicercaTamponi2_9.setMultipleSelects(multipleSelects9);
			
			context.getRequest().setAttribute("RicercaTamponi2_9", RicercaTamponi2_9);
			
LookupList RicercaTamponi2_10 = new LookupList(db, "lookup_ricerca_tamponi");
			
			RicercaTamponi2_10.setMultiple(true);
			RicercaTamponi2_10.setSelectSize(7);
			RicercaTamponi2_10.setMultipleSelects(multipleSelects10);
			
			context.getRequest().setAttribute("RicercaTamponi2_10", RicercaTamponi2_10);
			context.getRequest().setAttribute("idApiario",newTic.getIdApiario());
			context.getRequest().setAttribute("OrgId",newTic.getOrgId());
		
			
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		// return "ModifyautorizzazionetrasportoanimaliviviOK";
		return "";
		// return getReturn(context, "ModifyTicket");
	}

	


	public String executeCommandChiudi(ActionContext context,Connection db){
		
		Integer resultCount = -1;
		Ticket thisTicket = null;
		try {
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
					.getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			
			
		
			boolean esitiInseriti = true;
			HashMap<Integer, Tampone> listaTamponi = thisTicket.getListaTamponi();
			Iterator<Integer> keys = listaTamponi.keySet().iterator();
			while(keys.hasNext()){
				int kiave = keys.next();
				Tampone t = listaTamponi.get(kiave);
				Iterator<Integer> keyEsiti = t.getEsiti().keySet().iterator();
				while(keyEsiti.hasNext())
				{
					int kiaveEsito = keyEsiti.next();
					String esito = t.getEsiti().get(kiaveEsito);
					if(esito==null || esito.equals(""))
					{
						esitiInseriti = false;
						break;
					}
					
				}
				
			}
			if(esitiInseriti == true)
			{
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			else
			{
				context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito per ogni Tampone");
			}
			
			
			 String id_controllo=thisTicket.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
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
				      
				      org.aspcfs.modules.altriprovvedimenti.base.TicketList nonCList = new org.aspcfs.modules.altriprovvedimenti.base.TicketList();
			          int passIdN = thisTicketV.getOrgId();
			          nonCList.setOrgId(passedId);
			          nonCList.buildListControlli(db, passIdN, id_controllo);
			    	   Iterator ncIterator=nonCList.iterator();
			          while(ncIterator.hasNext()){
				    	  org.aspcfs.modules.altriprovvedimenti.base.Ticket tic = (org.aspcfs.modules.altriprovvedimenti.base.Ticket) ncIterator.next();
				    	  if(tic.getClosed()==null){
				    		  flag=1;
				    		  break;
				    	  }
				      }
				     

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

				      context.getRequest().setAttribute("resultCount", resultCount);
				      context.getRequest().setAttribute("OrgId", thisTicket.getOrgId());
				      context.getRequest().setAttribute("stabId", thisTicket.getIdStabilimento());
				      context.getRequest().setAttribute("idApiario", thisTicket.getIdApiario());
				      context.getRequest().setAttribute("flag", flag);
				      context.getRequest().setAttribute("attivitaCollegate", attivitaCollegate);
				      
				      if(attivitaCollegate==null){
				    	  if(flag==1 || flag==2){
				    		return "" ;

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
				      
				     
				      if (resultCount == 1) {
				    	  thisTicket.queryRecord(db, thisTicket.getId());
				    	  this.processUpdateHook(context, oldTicket, thisTicket);
				    	
				      }


		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			
		} 
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return "";

	}

}
