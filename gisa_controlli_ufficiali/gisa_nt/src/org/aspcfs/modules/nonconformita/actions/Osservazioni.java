/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nonconformita.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcf.modules.controlliufficiali.action.Followup;
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


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public final class Osservazioni extends CFSModule {
	public String executeCommandAdd(ActionContext context,Connection db){

		org.aspcfs.modules.osservazioni.base.Osservazioni newTic = null;
		try
		{
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			
			
			LookupList Provvedimenti = new LookupList(db, "lookup_oggetto_audit");
			Provvedimenti.addItem(-1, "-- SELEZIONA UNA TIPOLOGIA DI NC --");
			Provvedimenti.removeElementByLevel(9);
			context.getRequest().setAttribute("Osservazioni", Provvedimenti);
			
			
			LookupList macrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
			context.getRequest().setAttribute("Macrocategorie", macrocategorie);
			
			context.getRequest().setAttribute("idIspezione", context.getRequest().getParameter("idIspezione"));
			context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			

			
			LookupList osservazioni = new LookupList(db, "lookup_osservazioni");
			osservazioni.addItem(-1, "-- SELEZIONA OSSERVAZIONE --");
			osservazioni.setMultiple(true);
			osservazioni.setSelectSize(6);
			context.getRequest().setAttribute("Osservazioni", osservazioni);

			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);

			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(idC));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());
			
			LookupList SiteIdList = new LookupList();
			SiteIdList.setTableName("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			newTic = new org.aspcfs.modules.osservazioni.base.Osservazioni();
			


			String temporgId = context.getRequest().getParameter("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);

			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return ("SystemError");
		} 
		
		addModuleBean(context, "AddTicket", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource", context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context,Connection db) throws SQLException 
	{
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		org.aspcfs.modules.osservazioni.base.Osservazioni newTicket = null;
		org.aspcfs.modules.osservazioni.base.Osservazioni newTic = (org.aspcfs.modules.osservazioni.base.Osservazioni) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		String site =  context.getRequest().getParameter("siteId");
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String idC = context.getRequest().getParameter("idC");
		String idCU = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idIspezione", context.getRequest().getParameter("idIspezione"));
		context.getRequest().setAttribute("idC",idCU);
		context.getRequest().setAttribute("idIspezione", context.getRequest().getAttribute("idIspezione"));
		newTic.setNcFormaliValutazioni(context.getRequest().getParameter("nonConformitaFormali_valutazione"));
		newTic.setNcSignificativeValutazioni(context.getRequest().getParameter("nonConformitaSignificativeValuazione"));
		newTic.setNcGraviValutazioni(context.getRequest().getParameter("nonConformitaGraviValutazione"));
		
		   
		newTic.setResolutionDate(context.getRequest().getParameter("resolutionDate"));
		
		
		if(context.getRequest().getParameter("resolvable") != null && !context.getRequest().getParameter("resolvable").equals("") ){
			if(context.getRequest().getParameter("resolvable").equals("NO")){
				newTic.setResolvable(false);
			}
			if(context.getRequest().getParameter("resolvable").equals("SI")){
				newTic.setResolvable(true);	
			}
		}
		if(context.getRequest().getParameter("note_altro") != null && !context.getRequest().getParameter("note_altro").equals("")){
			newTic.setNote_altro(context.getRequest().getParameter("note_altro"));
		}
		
		//newTic.setNoteAltro(context.getRequest().getParameter("note_altro"));
		//newTic.setResolvable(context.getRequest().getParameter("resolvable"));
		
		if (context.getRequest().getParameter("idMacchinetta")!=null)
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		}
		else
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
		}

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

		newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
		newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));

		try {

			LookupList SiteIdList = new LookupList();
			SiteIdList.setTableName("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
	
			LookupList Provvedimenti = new LookupList(db, "lookup_oggetto_audit");
			Provvedimenti.addItem(-1, "-- SELEZIONA UNA TIPOLOGIA DI NC --");
			Provvedimenti.removeElementByLevel(9);
			context.getRequest().setAttribute("Osservazioni", Provvedimenti);
			if (newTic.getOrgId() > 0) 
			{
				newTic.setSiteId(Integer.parseInt(context.getParameter("siteId")));
			}
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) 
			{
				newTic.setTestoAppoggio("Inserimento"); 
				recordInserted = newTic.insert(db,context);
				}


			if (recordInserted) {
				
				boolean cuaggiornato = newTic.updateControllo(db, Integer.parseInt(idControllo));
				newTic.setPunteggio(context.getRequest().getParameter("punteggio"));
				org.aspcfs.modules.vigilanza.base.Ticket controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));
				int puntiV = controlloUff.getPunteggio();
				int puntiN = puntiV + newTic.getPunteggio();
				if(controlloUff.getTipoCampione()!=5)
				{
				controlloUff.setPunteggio(puntiN);
				controlloUff.update(db);
				}
				//Prepare the ticket for the response
				newTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, newTic.getId());
				String elementi_formali 	= context.getRequest().getParameter("elementi_nc_formali");
				int num_elementi_formali 	= Integer.parseInt(elementi_formali) 	;
				
				String oggettiAudit[] = context.getRequest().getParameterValues("Provvedimenti1_1");
				String listaOsservazioni[] = context.getRequest().getParameterValues("Osservazioni_Formali");

				int progressivo = 1;
				
					
					
					if ((oggettiAudit!=null && oggettiAudit.length>0 ) )
					{
						newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,1,context.getParameter("note_formali"));
						progressivo++ ;
					}
					
				
				
				
				
				
				progressivo = 1;
				oggettiAudit = context.getRequest().getParameterValues("Provvedimenti2_1");
				listaOsservazioni = context.getRequest().getParameterValues("Osservazioni_Significative");
				if ((oggettiAudit!=null && oggettiAudit.length>0  ) )
				{
					newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,2,context.getParameter("note_significative"));
					progressivo++ ;
				}
				
				
				
				progressivo = 1;
				oggettiAudit = context.getRequest().getParameterValues("Provvedimenti3_1");
				listaOsservazioni = context.getRequest().getParameterValues("Osservazioni_Gravi");
				 
				 
				if ((oggettiAudit!=null && oggettiAudit.length>0  ) )
				{
					newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,3,context.getParameter("note_gravi"));
					progressivo++ ;
				}
				
				context.getRequest().setAttribute("TicketDetails", newTicket);
				context.getRequest().setAttribute("orgId", newTicket.getOrgId());
				addRecentItem(context, newTicket);

				processInsertHook(context, newTicket);
			} else {
				if (newTic.getOrgId() != -1) {

					newTic.setCompanyName((String)context.getRequest().getAttribute("companyname"));
				}
			}
			

			context.getRequest().setAttribute("recordInserted", recordInserted);
			context.getRequest().setAttribute("isValid", isValid);



		} catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		return "-none-";
	}



	public String executeCommandTicketDetails(ActionContext context,Connection db){ 

		if(context.getRequest().getAttribute("Messaggio2")!=null)
		{

			context.getRequest().setAttribute("Messaggio2", context.getRequest().getAttribute("Messaggio2"));
		}
		if (context.getRequest().getParameter("idMacchinetta")!=null)
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		}
		else
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
		}
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		String tickId = context.getRequest().getParameter("ticketId");

		String idControlloUfficiale = context.getRequest().getParameter("idControllo");
		String idC = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idControllo",
				idControlloUfficiale);
		context.getRequest().setAttribute("idC",
				idC);

		try {
			// Load the ticket
			org.aspcfs.modules.osservazioni.base.Osservazioni newTic = new org.aspcfs.modules.osservazioni.base.Osservazioni();
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			
			if(tickId == null)
				newTic.queryRecord(db, Integer.parseInt(ticketId));
			else
				newTic.queryRecord(db, Integer.parseInt(tickId));

			if (context.getRequest().getParameter("idIspezione")!=null)
			{
				context.getRequest().setAttribute("idIspezione", context.getRequest().getParameter("idIspezione"));
			}
			else
			{
				context.getRequest().setAttribute("idIspezione", context.getRequest().getAttribute("idIspezione"));
			}
			int passedId = newTic.getOrgId();
			org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
			int passId = newTic.getOrgId();
			sanzioniList.setOrgId(passedId);
			sanzioniList.buildListControlli(db, passId, ticketId,8);
			context.getRequest().setAttribute("SanzioniList", sanzioniList);

			
			LookupList SiteIdList = new LookupList();
			SiteIdList.setTableName("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_oggetto_audit");
			Provvedimenti.addItem(-1, "-- SELEZIONA UNA TIPOLOGIA DI NC --");
			Provvedimenti.removeElementByLevel(9);
			context.getRequest().setAttribute("Osservazioni", Provvedimenti);

		

			LookupList NonConformita = new LookupList(db, "lookup_nonconformita");
			NonConformita.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("NonConformita", NonConformita);

			org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
			context.getRequest().setAttribute("CU",CU);
			String id_controllo=newTic.getIdControlloUfficiale();

			while(id_controllo.startsWith("0")){

				id_controllo=id_controllo.substring(1);
			}

			context.getRequest().setAttribute("idC", id_controllo);
			newTic.setPermission();
			context.getRequest().setAttribute("TicketDetails", newTic);

			addRecentItem(context, newTic);
			addModuleBean(context, "View Accounts", "View Tickets");
			// Reset any pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "AccountTicketsFolderInfo");
			deletePagedListInfo(context, "AccountTicketDocumentListInfo");
			deletePagedListInfo(context, "AccountTicketTaskListInfo");
			deletePagedListInfo(context, "accountTicketPlanWorkListInfo");

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
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
	public String executeCommandModifyTicket(ActionContext context,Connection db){

		org.aspcfs.modules.osservazioni.base.Osservazioni newTic = new org.aspcfs.modules.osservazioni.base.Osservazioni ();
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		try 
		{
			newTic.queryRecord(db, Integer.parseInt(ticketId));
			User user = this.getUser(context, this.getUserId(context));
			//Load the ticket
			
			if (context.getRequest().getParameter("idMacchinetta")!=null)
			{
				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
			}
			else
			{
				context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
			}
LookupList macrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
			
			context.getRequest().setAttribute("Macrocategorie", macrocategorie);
			
			
			

			
			LookupList osservazioni = new LookupList(db, "lookup_osservazioni");
			osservazioni.addItem(-1, "-- SELEZIONA OSSERVAZIONE --");
			osservazioni.setMultiple(true);
			osservazioni.setSelectSize(6);
			context.getRequest().setAttribute("Osservazioni", osservazioni);

		
			
			String id =newTic.getIdControlloUfficiale();
			while(id.startsWith("0"))
			{
				id=id.substring(1);
			}
			String id_controllo = context.getRequest().getParameter("idC");
			while(id_controllo.startsWith("0"))
			{

				id_controllo=id_controllo.substring(1);
			}
		
			
		
			org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
			context.getRequest().setAttribute("idIspezione",""+CU.getTipoIspezione());

			context.getRequest().setAttribute("idC", id_controllo);
		
			
			LookupList SiteIdList = new LookupList();
			SiteIdList.setTableName("lookup_site_id");
			SiteIdList.buildListWithEnabled(db);
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			//Put the ticket in the request
			addRecentItem(context, newTic);
			context.getRequest().setAttribute("TicketDetails", newTic);
			addModuleBean(context, "View Accounts", "View Tickets");

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id_controllo));
			context.getRequest().setAttribute("CU", cu);

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} 
		return "";

	}

	public String executeCommandUpdateTicket(ActionContext context,Connection db){

		int resultCount = 0;

		int catCount = 0;
		
		boolean catInserted = false;
		boolean isValid = true;
		if (context.getRequest().getParameter("idMacchinetta")!=null)
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getParameter("idMacchinetta"));
		}
		else
		{
			context.getRequest().setAttribute("idMacchinetta", context.getRequest().getAttribute("idMacchinetta"));
		}
		org.aspcfs.modules.osservazioni.base.Osservazioni newTic = (org.aspcfs.modules.osservazioni.base.Osservazioni) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		newTic.setNcFormaliValutazioni(context.getRequest().getParameter("nonConformitaFormali_valutazione"));
		newTic.setNcSignificativeValutazioni(context.getRequest().getParameter("nonConformitaSignificativeValuazione"));
		newTic.setIdentificativo(context.getRequest().getParameter("identificativoNC"));
		newTic.setNcGraviValutazioni(context.getRequest().getParameter("nonConformitaGraviValutazione"));
		
		if(context.getRequest().getParameter("note_altro") != null && !context.getRequest().getParameter("note_altro").equals(""))
			newTic.setNote_altro(context.getRequest().getParameter("note_altro"));


		if(context.getRequest().getParameter("resolvable") != null && !context.getRequest().getParameter("resolvable").equals("") ){
			if(context.getRequest().getParameter("resolvable").equals("NO")){
				newTic.setResolvable(false);
			}
			if(context.getRequest().getParameter("resolvable").equals("SI")){
				newTic.setResolvable(true);	
			}
		}
		
		

		try {

			org.aspcfs.modules.osservazioni.base.Osservazioni oldTic = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, newTic.getId());
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("idIspezione", context.getRequest().getParameter("idIspezione"));
			//Get the previousTicket, update the ticket, then send both to a hook
			org.aspcfs.modules.osservazioni.base.Osservazioni previousTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, Integer.parseInt(context.getParameter("id")));
			newTic.setModifiedBy(getUserId(context));
			
			newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
				resultCount = newTic.update(db);
				newTic.delTipiOsservazione(db);
				String elementi_formali 	= context.getRequest().getParameter("elementi_nc_formali");
				int num_elementi_formali 	= Integer.parseInt(elementi_formali) 	;
				int progressivo = 1;

				String oggettiAudit[] = context.getRequest().getParameterValues("Provvedimenti1_1");
				String listaOsservazioni[] = context.getRequest().getParameterValues("Osservazioni_Formali");

				
					newTic.getAnimaliNonAlimentariCombo();
					
					if ((oggettiAudit!=null ) )
					{
						newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,1,context.getParameter("note_formali"));
						progressivo++ ;
					}
					
				
				
				
				
				
				progressivo = 1;
				oggettiAudit = context.getRequest().getParameterValues("Provvedimenti2_1");
				listaOsservazioni = context.getRequest().getParameterValues("Osservazioni_Significative");
				if ((oggettiAudit!=null ) )
				{
					newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,2,context.getParameter("note_significative"));
					progressivo++ ;
				}
				
				
				
				progressivo = 1;
				oggettiAudit = context.getRequest().getParameterValues("Provvedimenti3_1");
				listaOsservazioni = context.getRequest().getParameterValues("Osservazioni_Gravi");
				 
				 
				if ((oggettiAudit!=null ) )
				{
					newTic.insertTipiNonConformita(context,db, oggettiAudit,listaOsservazioni,3,context.getParameter("note_gravi"));
					progressivo++ ;
				}
				

			if (resultCount == 1) {
				newTic.queryRecord(db, newTic.getId());
				processUpdateHook(context, previousTicket, newTic);
			
			}

			}
		

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}


		return "";
	}

	public String executeCommandChiudi(ActionContext context,Connection db)
	{
		

		int resultCount = -1;
		org.aspcfs.modules.osservazioni.base.Osservazioni thisTicket = null;
		try {
			thisTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(
					db, Integer.parseInt(context.getRequest().getParameter("id")));
			org.aspcfs.modules.osservazioni.base.Osservazioni oldTicket = new org.aspcfs.modules.osservazioni.base.Osservazioni(db, thisTicket.getId());
						String ticketId = context.getRequest().getParameter("id");

			int passedId = thisTicket.getOrgId();
			org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
			int passId = thisTicket.getOrgId();
			sanzioniList.setOrgId(passedId);
			sanzioniList.buildListControlli(db, passId, ticketId,8);
			context.getRequest().setAttribute("SanzioniList", sanzioniList);

			org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
			int passIdR = thisTicket.getOrgId();
			reatiList.setOrgId(passedId);
			reatiList.buildListControlli(db, passIdR, ticketId,8);
			context.getRequest().setAttribute("ReatiList", reatiList);


			org.aspcfs.modules.followup.base.TicketList followupList = new org.aspcfs.modules.followup.base.TicketList();
			int passIdF = thisTicket.getOrgId();
			followupList.setOrgId(passedId);
			followupList.buildListControlli(db, passIdF, ticketId,8);
			context.getRequest().setAttribute("FollowupList", followupList);

			org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
			int passIdS = thisTicket.getOrgId();
			seqList.setOrgId(passedId);
			seqList.buildListControlli(db, passIdS, ticketId,8);
			context.getRequest().setAttribute("SequestriList", seqList);
			
			Iterator sanzionilIterator=sanzioniList.iterator();
			Iterator seqlIterator=seqList.iterator();
			Iterator reatilIterator=reatiList.iterator();
			Iterator followupIterator=followupList.iterator();
			

			int flag=0;
			int numSottoAttivita = 0;
			if(! thisTicket.getNon_conformita_gravi().isEmpty())
			{
			
			while(sanzionilIterator.hasNext()){

				org.aspcfs.modules.sanzioni.base.Ticket tic = (org.aspcfs.modules.sanzioni.base.Ticket) sanzionilIterator.next();
				numSottoAttivita ++ ;
				if(tic.getClosed()==null){
					flag=1;

					break;

				}

			}

			while(reatilIterator.hasNext()){
				numSottoAttivita ++ ;
				org.aspcfs.modules.reati.base.Ticket tic = (org.aspcfs.modules.reati.base.Ticket) reatilIterator.next();

				if(tic.getClosed()==null){
					flag=1;
					break;

				}


			}

			while(seqlIterator.hasNext()){
				numSottoAttivita ++ ;
				org.aspcfs.modules.sequestri.base.Ticket tic = (org.aspcfs.modules.sequestri.base.Ticket) seqlIterator.next();
				if(tic.getClosed()==null){
					flag=1;
					break;

				}

			}

			while(followupIterator.hasNext()){
				numSottoAttivita ++ ;
				org.aspcfs.modules.followup.base.Ticket tic = (org.aspcfs.modules.followup.base.Ticket) followupIterator.next();
				if(tic.getClosed()==null){
					flag=1;

					break;
				}
			}
		}


	



			context.getRequest().setAttribute("flag", flag);
			if(flag==1  ){
				context.getRequest().setAttribute("Chiudi", "1");
				return "";

			}

			if(numSottoAttivita == 0 ){

				context.getRequest().setAttribute("numSottoAttivita", 0);
				return "";

			}
			else
			{
				context.getRequest().setAttribute("numSottoAttivita", numSottoAttivita);
			}


			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.chiudi(db);
			context.getRequest().setAttribute("resultCount",resultCount);

			String id_controllo=thisTicket.getIdControlloUfficiale();
			while(id_controllo.startsWith("0")){

				id_controllo=id_controllo.substring(1);
			}

			context.getRequest().setAttribute("idC", id_controllo);


			org.aspcfs.modules.vigilanza.base.Ticket thisTicketV = new org.aspcfs.modules.vigilanza.base.Ticket(
					db, Integer.parseInt(id_controllo));

			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId1 = thisTicketV.getOrgId();
			ticList.setOrgId(passedId1);
			ticList.buildListControlli(db, passedId, id_controllo);


			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = thisTicketV.getOrgId();
			tamponiList.setOrgId(passedId1);
			tamponiList.buildListControlli(db, pasId, id_controllo);




			Iterator campioniIterator=ticList.iterator();
			Iterator tamponiIterator=tamponiList.iterator();



			int flag1=0;
			while(campioniIterator.hasNext()){

				org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();

				if(tic.getClosed()==null){
					flag1=1;

					break;

				}

			}


			while(tamponiIterator.hasNext()){

				org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();

				if(tic.getClosed()==null){
					flag1=1;

					break;
				}

			}


			if(thisTicketV.isNcrilevate()==true){
				org.aspcfs.modules.osservazioni.base.OsservazioniList nonCList = new org.aspcfs.modules.osservazioni.base.OsservazioniList();
				int passIdN = thisTicketV.getOrgId();
				nonCList.setOrgId(passedId);
				nonCList.buildListControlli(db, passIdN, id_controllo);

				Iterator ncIterator=nonCList.iterator();

				while(ncIterator.hasNext()){

					org.aspcfs.modules.osservazioni.base.Osservazioni tic = (org.aspcfs.modules.osservazioni.base.Osservazioni) ncIterator.next();

					if(tic.getClosed()==null){
						flag1=1;

						break;

					}

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

						if(thisTicketV.isCategoriaisAggiornata()==false){
							flag=2;

							break;

						}

					}}
			}



			String attivitaCollegate=context.getRequest().getParameter("altro");
			context.getRequest().setAttribute("attivitaCollegate", attivitaCollegate);
			if(attivitaCollegate==null){
				if(flag==1 || flag==2){
					context.getRequest().setAttribute("Chiudi", ""+flag);
					return "";

				}
			}



			String chiudiCu = context.getRequest().getParameter("chiudiCu");
			if(flag1==0 ){
				if(chiudiCu !=null)
				{
					thisTicketV.setModifiedBy(getUserId(context));
					resultCount = thisTicketV.chiudi(db);
				}else
				{
					context.getRequest().setAttribute("Messaggio2", "Attivita collegate al cu chiuse");
				}
			}

			context.getRequest().setAttribute("resultCount", resultCount);


			 if (resultCount == -1) {
				 return "";
			 } else if (resultCount == 1) {
				 thisTicket.queryRecord(db, thisTicket.getId());
				 this.processUpdateHook(context, oldTicket, thisTicket);
				 return "";
			 }
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}
}
