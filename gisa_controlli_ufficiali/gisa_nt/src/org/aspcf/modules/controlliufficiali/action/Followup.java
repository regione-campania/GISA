/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

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
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.followup.base.Ticket;
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

public class Followup  extends CFSModule {
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-followup-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);
			String tipo_nc = context.getRequest().getParameter("tipoNc");
			String tipologia_nc = context.getRequest().getParameter("tipologiaNc");

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",id);

			LookupList Followup = new LookupList(db, "lookup_followup");
			Followup.setMultiple(true);
			Followup.setSelectSize(7);


			if ("1".equals(tipo_nc)) /*FORMALI*/
			{
				Followup.removeElementByLevel(1);
				Followup.removeElementByLevel(2);
				Followup.removeElementByLevel(3);
				Followup.removeElementByLevel(5);
				Followup.addItem(-1, "-- SELEZIONA VOCE --");
			}
			else
			{
				if ("2".equals(tipo_nc))/*SIGNIFICATIVE*/
				{
					Followup.removeElementByLevel(0);
					Followup.removeElementByLevel(2);
					Followup.removeElementByLevel(4);
					Followup.removeElementByLevel(5);

					Followup.addItem(-1, "-- SELEZIONA VOCE --");
				}
				else
				{
					if ("3".equals(tipo_nc))/*GRAVI*/
					{
						Followup.removeElementByLevel(0);
						Followup.removeElementByLevel(1);
						Followup.removeElementByLevel(3);
						Followup.addItem(-1, "-- SELEZIONA VOCE --");


					}
				}

			}
			
			if (!"126".equals(tipologia_nc) && !"65".equals(tipologia_nc)) /*NON BENESSERE*/
			{
				Followup.removeElementByLevel(11);
			}

			context.getRequest().setAttribute("Followup", Followup);

			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());

			LookupList FollowupPenali = new LookupList(db, "lookup_followup");
			FollowupPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupPenali", FollowupPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));
			org.aspcf.modules.controlliufficiali.base.Organization organization = new org.aspcf.modules.controlliufficiali.base.Organization(db,Integer.parseInt(id));
			
			if (context.getParameter("orgId")==null && context.getParameter("stabId")!=null)
			{
				organization.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
				organization.setIdApiario(Integer.parseInt(context.getParameter("stabId")));
				organization.setOrgId(-1);
			
			}
			context.getRequest().setAttribute("OrgDetails", organization);
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		} catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-followup-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Ticket newTicket = null;


		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		Ticket newTic = (Ticket) context.getFormBean();
		newTic.setTipo_nc(Integer.parseInt(context.getRequest().getParameter("tipoNc")));
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
		}/*else if(site.equals("8")){
				site = "NA3";
			}else if(site.equals("9")){
				site = "NA4";
			}else if(site.equals("10")){
				site = "NA5";
			}else if(site.equals("11")){
				site = "SA1";
			}else if(site.equals("12")){
				site = "SA2";
			}else if(site.equals("13")){
				site = "SA3";
			}*/
		else{
			if(site.equals(16))
			{
				site ="FuoriRegione";
			}
		}
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String valutazione = context.getRequest().getParameter("nonConformitaGraviValutazione");
		String idC = context.getRequest().getParameter("idC");
		String idCampione = site+idControllo;
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));
		newTic.setValutazione(valutazione);
		
		if (context.getParameter("altId") != null && !context.getParameter("altId").equals("null"))
			newTic.setAltId(Integer.parseInt(context.getParameter("altId")));
		
		if (context.getParameter("stabId") != null && !context.getParameter("stabId").equals("null")){
			newTic.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
		}
		else if(newTic.getIdStabilimento() > 0 && newTic.getIdApiario() > 0){
			newTic.setIdStabilimento(0);
		}
		
		try {
			db = this.getConnection(context);
			
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
	cu.controlloBloccoCu(db,cu.getId());
			
			cu.setFlagBloccoNonConformita(db, cu.getId());
			if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==true)
			{
				newTic.setFlag_posticipato(true);
				newTic.setFlag_campione_non_conforme(false);
			}
			else
			{
				if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==false)
				{
					newTic.setFlag_posticipato(true);
					newTic.setFlag_campione_non_conforme(true);
				}
			}
			
			if (cu.getAltId()>0)
			{
				newTic.setAltId(cu.getAltId());
			}
//			newTic.setAssignedDate(cu.getAssignedDate());
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList FollowupAmministrative = new LookupList(db, "lookup_followup");
			FollowupAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupAmministrative", FollowupAmministrative);

			LookupList FollowupPenali = new LookupList(db, "lookup_followup");
			FollowupPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupPenali", FollowupPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);
			newTic.setNoteFollowup(context.getRequest().getParameter("notefollowup"));
			newTic.setIdApiario(cu.getIdApiario());
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				String[] limitazioni=context.getRequest().getParameterValues("limitazioniFollowup");

				recordInserted = newTic.insert(db,context);
				newTic.insertLimitazioniFollowup(db, limitazioni);
				
				
				context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));
			}


			if (recordInserted) {

				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);
				if (!"null".equals(context.getRequest().getParameter("inseriti")) && context.getRequest().getParameter("inseriti")!=null)
				{
					if (! "".equals(context.getRequest().getParameter("inseriti")))
					{
						context.getRequest().setAttribute("inserito",context.getRequest().getParameter("inseriti")+";"+newTicket.getId());
					}
					else
					{
						context.getRequest().setAttribute("inserito",newTicket.getId());
					}

					String tipo_nc = context.getRequest().getParameter("tipoNc");
					if (tipo_nc.equals("1"))
					{
						if(! "".equals(context.getRequest().getParameter("followup_formali_inseriti")))
						{
							context.getRequest().setAttribute("followup_formali_inseriti",context.getRequest().getParameter("followup_formali_inseriti")+ ";"+newTicket.getId());

						}
						else
						{
							context.getRequest().setAttribute("followup_formali_inseriti",newTicket.getId());

						}
					}
					else
					{
						if (tipo_nc.equals("2"))
						{
							if(! "".equals(context.getRequest().getParameter("followup_significativi_inseriti")))
							{
								context.getRequest().setAttribute("followup_significativi_inseriti",context.getRequest().getParameter("followup_significativi_inseriti")+ ";"+newTicket.getId());

							}
							else
							{
								context.getRequest().setAttribute("followup_significativi_inseriti",newTicket.getId());

							}
						}
						else
						{
							if (tipo_nc.equals("3"))
							{
								if(! "".equals(context.getRequest().getParameter("followup_gravi_inseriti")))
								{
									context.getRequest().setAttribute("followup_gravi_inseriti",context.getRequest().getParameter("followup_gravi_inseriti")+ ";"+newTicket.getId());

								}
								else
								{
									context.getRequest().setAttribute("followup_gravi_inseriti",newTicket.getId());

								}
							}


						}


					}


				}
				else
				{
					context.getRequest().setAttribute("inserito", ""+newTicket.getId()+";");

				}
				


			} else {
				if (newTic.getOrgId()>0) {
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
		}




	


		return "AddOK"; 
	}


	/**
	 * Load the ticket details tab
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		
		Connection db = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		String tickId = context.getRequest().getParameter("ticketId");
		String retPag = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			Ticket newTic = new Ticket();
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			if(tickId == null)
				newTic.queryRecord(db, Integer.parseInt(ticketId));
			else
				newTic.queryRecord(db, Integer.parseInt(tickId));

			//find record permissions for portal users
			
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
			context.getRequest().setAttribute("CU", cu);

			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",
					id);

			String id2 = context.getRequest().getParameter("idNC");
			context.getRequest().setAttribute("idNC",
					id2);


			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList FollowupAmministrative = new LookupList(db, "lookup_followup");
			FollowupAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupAmministrative", FollowupAmministrative);

			LookupList FollowupPenali = new LookupList(db, "lookup_followup");
			FollowupPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupPenali", FollowupPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			
			LookupList Followup = new LookupList(db, "lookup_followup");
			Followup.setMultiple(true);
			Followup.setSelectSize(7);
//			Followup.addItem(-1, "-- SELEZIONA VOCE --");
			
			

			if ("1".equals(newTic.getTipo_nc()+"")) /*FORMALI*/
			{
				Followup.removeElementByLevel(1);
				Followup.removeElementByLevel(2);
				Followup.removeElementByLevel(3);
				Followup.removeElementByLevel(5);
				Followup.addItem(-1, "-- SELEZIONA VOCE --");
			}
			else
			{
				if ("2".equals(newTic.getTipo_nc()+""))/*SIGNIFICATIVE*/
				{
					Followup.removeElementByLevel(0);
					Followup.removeElementByLevel(2);
					Followup.removeElementByLevel(4);
					Followup.addItem(-1, "-- SELEZIONA VOCE --");
				}
				else
				{
					if ("3".equals(newTic.getTipo_nc()+""))/*GRAVI*/
					{
						Followup.removeElementByLevel(0);
						Followup.removeElementByLevel(1);
						Followup.removeElementByLevel(3);
						Followup.addItem(-1, "-- SELEZIONA VOCE --");


					}
				}

			}

			
			

			LookupList multipleSelects=new LookupList();

			HashMap<Integer, String> listaLimitazioni=newTic.getListaLimitazioniFollowup();
			Iterator<Integer> iterakiavi=listaLimitazioni.keySet().iterator();
			while(iterakiavi.hasNext()){
				int kiave=iterakiavi.next();
				String v=listaLimitazioni.get(kiave);
				multipleSelects.addItem(kiave, v);



			}


			if(multipleSelects.size()!=0)
				Followup.setMultipleSelects(multipleSelects);


			context.getRequest().setAttribute("Followup", Followup);
			
			 UserBean user = (UserBean)context.getSession().getAttribute("User");
			 String nameContext=context.getRequest().getServletContext().getServletContextName();
			 if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				 context.getRequest().setAttribute("View", "AutoritaNonCompetenti");

			context.getRequest().setAttribute("TicketDetails", newTic);

			retPag = "DettaglioOK";

			addRecentItem(context, newTic);
			// Load the organization for the header
			
			addModuleBean(context, "View Accounts", "View Tickets");
			// Reset any pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "AccountTicketsFolderInfo");
			deletePagedListInfo(context, "AccountTicketDocumentListInfo");
			deletePagedListInfo(context, "AccountTicketTaskListInfo");
			deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
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
	public String executeCommandModifyTicket(ActionContext context) {

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
			
			String tipo_nc = context.getRequest().getParameter("tipoNc");
			//Load the organization

		
			






			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList FollowupAmministrative = new LookupList(db, "lookup_followup");
			FollowupAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupAmministrative", FollowupAmministrative);

			LookupList FollowupPenali = new LookupList(db, "lookup_followup");
			FollowupPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("FollowupPenali", FollowupPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

		
		
			LookupList Followup = new LookupList(db, "lookup_followup");
			Followup.setMultiple(true);
			Followup.setSelectSize(7);

			if ("1".equals(tipo_nc)) /*FORMALI*/
			{
				Followup.removeElementByLevel(1);
				Followup.removeElementByLevel(2);
				Followup.removeElementByLevel(3);
				Followup.removeElementByLevel(5);
				Followup.addItem(-1, "-- SELEZIONA VOCE --");
			}
			else
			{
				if ("2".equals(tipo_nc))/*SIGNIFICATIVE*/
				{
					Followup.removeElementByLevel(0);
					Followup.removeElementByLevel(2);
					Followup.removeElementByLevel(4);
					Followup.addItem(-1, "-- SELEZIONA VOCE --");
				}
				else
				{
					if ("3".equals(tipo_nc))/*GRAVI*/
					{
						Followup.removeElementByLevel(0);
						Followup.removeElementByLevel(1);
						Followup.removeElementByLevel(3);
						Followup.addItem(-1, "-- SELEZIONA VOCE --");


					}
				}

			}

			context.getRequest().setAttribute("Followup", Followup);

			//
			


			//Put the ticket in the request
			addRecentItem(context, newTic);
			context.getRequest().setAttribute("TicketDetails", newTic);
			addModuleBean(context, "View Accounts", "View Tickets");

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
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


	public String executeCommandChiudi(ActionContext context)
	{
		if (!(hasPermission(context, "accounts-accounts-followup-edit"))){
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
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.chiudi(db);
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
	 * Confirm the delete operation showing dependencies
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-followup-delete")) {
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
					systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='AccountFollowup.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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





	public String executeCommandUpdateTicket(ActionContext context) {
		
		Connection db = null;
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
			db = this.getConnection(context);

			Ticket oldTic = new Ticket(db, newTic.getId());


			org.aspcfs.modules.vigilanza.base.Ticket cu =new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
			newTic.setAssignedDate(context.getParameter("assignedDate"));
			//Get the previousTicket, update the ticket, then send both to a hook
			Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
			newTic.setModifiedBy(getUserId(context));
			newTic.setSiteId(context.getRequest().getParameter("siteId"));
			newTic.setIdControlloUfficiale(id);
			
			isValid = this.validateObject(context, db, newTic) && isValid;
			newTic.setTipo_richiesta(context.getRequest().getParameter("tipo_richiesta"));
			newTic.setNoteFollowup(context.getRequest().getParameter("notefollowup"));
			String valutazione = context.getRequest().getParameter("nonConformitaGraviValutazione");
			newTic.setValutazione(valutazione);
			if (isValid) {
				newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
				resultCount = newTic.update(db);
				String[] limitazioni=context.getRequest().getParameterValues("limitazioniFollowup");
				newTic.updateLimitazioneFollowup(db, limitazioni);

			}
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
		} finally {
			this.freeConnection(context, db);
		}


		return "";
	}




}
