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
package org.aspcfs.modules.allevamenti.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;

import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.campioni.base.ModuliCampioniHtmlFields;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.allevamenti.base.*;
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
import java.util.logging.Logger;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.schedeadeguamenti.base.SchedaBiogas;
import org.aspcfs.modules.schedeadeguamenti.base.SchedaPiccioni;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.PopolaCombo;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp $
 * @created August 15, 2001
 */
public final class AccountVigilanza extends CFSModule {

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
	 * Update the specified ticket
	 *
	 * @param context
	 *            Description of Parameter
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
			// return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandTicketDetails(context));
	}

	public String executeCommandChiudiTemp(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-vigilanza-edit"))) {
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

	/**
	 * Re-opens a ticket
	 *
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-edit")) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;

		try {
			db = this.getConnection(context);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandReopenTicket(context, db);
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
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

	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		// Parameters
		String ticketId = context.getRequest().getParameter("id");
		Ticket thisTicket = null;
		String orgIdString = context.getRequest().getParameter("orgId");
		int orgId = -1;

		String retPag = null;
		PreparedStatement pst = null;
		String sql = null;
		String flagMod5 = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket

			thisTicket = new Ticket(db, Integer.parseInt(ticketId));

			int i = 0;

			if (orgIdString != null && !orgIdString.equals("null") && !orgIdString.equals(""))
				orgId = Integer.parseInt(orgIdString);
			else
				orgId = thisTicket.getOrgId();
			context.getRequest().setAttribute("orgId", orgId);

			Organization orgDetail = new Organization(db, orgId);

			// Read flag for mod5 from ticket table
			sql = "select flag_mod5 as flag from ticket where ticketid = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(++i, Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			if (rs.next()) {
				flagMod5 = rs.getString("flag");
	
			}
			
			boolean checkScheda = false;
	
			checkScheda = thisTicket.isControlloChiudibileBaSa(db,false);
			context.getRequest().setAttribute("hasChecklistBenessere", checkScheda);	
			checkScheda = thisTicket.isControlloChiudibileBaSa(db,true);
			context.getRequest().setAttribute("hasChecklistCondizionalita", checkScheda);
			
			if (orgDetail.getSpecieA()== 122) {
				checkScheda = thisTicket.isControlloChiudibileBiosicurezza(db);
				context.getRequest().setAttribute("hasChecklistBiosicurezza", checkScheda);
			}
			
			checkScheda = thisTicket.isControlloChiudibileFarmacosorveglianza(db);
			context.getRequest().setAttribute("hasChecklistFarmacosorveglianza", checkScheda);
			
			context.getRequest().setAttribute("OrgDetails", orgDetail);
			context.getRequest().setAttribute("siteId", orgDetail.getSiteId());
			context.getRequest().setAttribute("tipologia", 2);
			context.getRequest().setAttribute("tipoDest", null);
			context.getRequest().setAttribute("bozza", flagMod5);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandTicketDetails(context, db);
			if (context.getRequest().getAttribute("Chiudi") != null) {
				context.getRequest().setAttribute("Chiudi", "" + context.getRequest().getAttribute("Chiudi"));
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
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-delete")) {
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
			// check permission to record
			if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
				return ("PermissionError");
			}
			DependencyList dependencies = ticket.processDependencies(db);
			// Prepare the dialog based on the dependencies
			HtmlDialog htmlDialog = new HtmlDialog();
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			htmlDialog.addMessage(systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
			/*
			 * htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header")
			 * );
			 */
			htmlDialog.addButton(
					systemStatus.getLabel("global.button.delete"),
					"javascript:window.location.href='AllevamentiVigilanza.do?command=DeleteTicket&id=" + id
							+ "&orgId=" + orgId + "&forceDelete=true"
							+ RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		boolean istDeleted = false;
		Connection db = null;
		// Parameters
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			Organization newOrg = new Organization(db, orgId);
			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			// check permission to record
			if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
				return ("PermissionError");
			}
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			// AGGIUNTA di cancellazione delle istanze per le liste di
			// riscontro.
			istDeleted = thisTic.logicDeleteCheckListIstanza(db);

			if (recordDeleted) {
				processDeleteHook(context, thisTic);
				// del
				String inline = context.getRequest().getParameter("popupType");
				context.getRequest().setAttribute("OrgDetails", newOrg);
				context.getRequest().setAttribute(
						"refreshUrl",
						"Allevamenti.do?command=ViewVigilanza&orgId=" + orgId
								+ (inline != null && "inline".equals(inline.trim()) ? "&popup=true" : ""));
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

	public String executeCommandViewVigilanza(ActionContext context) {

		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink("AllevamentiVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
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
			// find record permissions for portal users
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

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);

			// aggiunte queste istruzioni
			String temporgId = context.getRequest().getParameter("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);

			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 2);
			context.getRequest().setAttribute("tipoDest", null);

			SystemStatus systemStatus = this.getSystemStatus(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			c.executeCommandAdd(context, db);

			// Load the organization
			Organization thisOrganization = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			if (context.getRequest().getParameter("refresh") != null
					|| (context.getRequest().getParameter("contact") != null && context.getRequest()
							.getParameter("contact").equals("on"))) {
				newTic = (Ticket) context.getFormBean();

			} else {
				newTic = new Ticket();
			}

			if (context.getRequest().getParameter("refresh") != null
					|| (context.getRequest().getParameter("contact") != null && context.getRequest()
							.getParameter("contact").equals("on"))) {
				newTic = (Ticket) context.getFormBean();

			} else {
				newTic = new Ticket();
			}

			// getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
			
			//GESTIONE PICCIONI
			//SE SI TRATTA DI UN'AZIENDA BOVINA O BUFALINA, CERCO LA SCHEDA PICCIONI
			if (newOrg.getSpecieA()==121 || newOrg.getSpecieA()==129){
				SchedaPiccioni dettaglioPiccioni = new SchedaPiccioni();
				dettaglioPiccioni.loadByAnagrafica(db, newOrg.getOrgId());
				if (!hasPermission(context, "allevamenti-allevamenti-schedapiccioni-ignora-view") && dettaglioPiccioni.getId()<=0){ //SE SI TRATTA DI UTENTE ASL E NON C'E' LA SCHEDA PICCIONI, REINDIRIZZALO
					Accounts allevamentiAction = new Accounts();
					context.getRequest().setAttribute("orgId", newOrg.getOrgId());
					context.getRequest().setAttribute("MessaggioSchedaPiccioni", "ATTENZIONE! Per proseguire con l'aggiunta dei controlli ufficiali su questa anagrafica e' necessario compilare la scheda adeguamento denominata:  STIMA DEL PATRIMONIO DEL COLOMBO DI CITTA' NELLE AZIENDE AGRICOLE E/O ZOOTECNICHE. Dopo la compilazione sara' possibile continuare a lavorare sull'anagrafica.");
					return allevamentiAction.executeCommandDetails(context);
				}
				}
			
			//GESTIONE BIOGAS
			//CERCO LA SCHEDA BIOGAS
				SchedaBiogas dettaglioBiogas = new SchedaBiogas();
				dettaglioBiogas.loadByAnagrafica(db, newOrg.getOrgId(), -1);
//				if (!hasPermission(context, "adeguamento-schedabiogas-ignora-view") && dettaglioBiogas.getId()<=0){ //SE SI TRATTA DI UTENTE ASL E NON C'E' LA SCHEDA BIOGAS, REINDIRIZZALO
//					Accounts allevamentiAction = new Accounts();
//					context.getRequest().setAttribute("orgId", newOrg.getOrgId());
//					context.getRequest().setAttribute("MessaggioSchedaBiogas", "ATTENZIONE! Per proseguire con l'aggiunta dei controlli ufficiali su questa anagrafica e' necessario compilare la scheda adeguamento denominata: INFORMAZIONI IMPIANTO BIOGAS. Dopo la compilazione sara' possibile continuare a lavorare sull'anagrafica.");
//					return allevamentiAction.executeCommandDetails(context);
//				}
				
				
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);

			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "AddTicket", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource", context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String nameContext = context.getRequest().getServletContext().getServletContextName();
		if (user.getUserRecord().getGruppo_ruolo() == Role.GRUPPO_ALTRE_AUTORITA)
			return getReturn(context, "AddTipo2");
		return getReturn(context, "Add");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
		if (!(hasPermission(context, "allevamenti-allevamenti-vigilanza-add"))) {
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
		try {
			db = this.getConnection(context);
			String newContact = context.getRequest().getParameter("contact");
			String save = context.getRequest().getParameter("Save");
			Ticket newTic = (Ticket) context.getFormBean();

			Organization thisOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));

			context.getRequest().setAttribute("OrgDetails", thisOrg);
			context.getRequest().setAttribute("name", thisOrg.getName());
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandInsert(context, db);

			recordInserted = (Boolean) context.getRequest().getAttribute("inserted");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			if (recordInserted && isValid) {

				String tipo_richiesta = newTic.getTipo_richiesta();
				tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

				retPage = "InsertOK";
			} else {
				return executeCommandAdd(context);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return (retPage);// ("InsertOK");
	}

	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;

		try {
			db = this.getConnection(context);
			String ticketId = context.getRequest().getParameter("id");

			if (context.getRequest().getParameter("companyName") == null) {
				newTic = new Ticket(db, Integer.parseInt(ticketId));

			} else {
				newTic = (Ticket) context.getFormBean();
			}
			
			boolean isModificabileBenessere = true;
			//gestione della modifica del controllo ufficale
			PreparedStatement pstBA = db.prepareStatement("select count(*) as num_istanze from chk_bns_mod_ist  where idcu = ? and trashed_date is null");
			pstBA.setInt(1, newTic.getId());
			ResultSet rsBA = pstBA.executeQuery();
			while (rsBA.next()){
				    int numIst= rsBA.getInt("num_istanze");
				    if(numIst > 0)
				    	isModificabileBenessere = false;
			}
		
			if (!isModificabileBenessere){
				context.getRequest().setAttribute("Error", "Controllo non modificabile perche' ci sono checklist compilate relative al benessere e/o alla sicurezza animale.");
				return(executeCommandTicketDetails(context));
			}
			
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
			
//			if (!isModificabile){
//				context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
//				return(executeCommandTicketDetails(context));
//			}
			//gestione della modifica del controllo ufficiale
			
			Organization thisOrganization = new Organization(db, newTic.getOrgId());
			context.getRequest().setAttribute("siteId", thisOrganization.getSiteId());

			/* Costruzione campi gestiti */
			AziendeZootFields fields = new AziendeZootFields();
			fields.queryRecord(db, Integer.parseInt(ticketId));

			context.getRequest().setAttribute("AzDetails", fields);

			context.getRequest().setAttribute("tipologia", 2);
			context.getRequest().setAttribute("tipoDest", null);
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			context.getRequest().setAttribute("TicketDetails", newTic);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandModifyTicket(context, db);

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return "ModifyautorizzazionetrasportoanimaliviviOK";
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String nameContext = context.getRequest().getServletContext().getServletContextName();
		if (user.getUserRecord().getGruppo_ruolo() == Role.GRUPPO_ALTRE_AUTORITA)
			return getReturn(context, "ModifyTipo2");
		return getReturn(context, "Modify");
		// return getReturn(context, "ModifyTicket");
	}

	/**
	 * Update the specified ticket
	 *
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;

		Ticket newTic = (Ticket) context.getFormBean();

		if (context.getRequest().getParameter("codici_selezionabili") != null) {
			newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
		}

		newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));

		if (context.getRequest().getParameter("ncrilevate").equals("1"))
			newTic.setNcrilevate(true);
		else
			newTic.setNcrilevate(false);

		newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));

		try {

			db = this.getConnection(context);
			Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));

			context.getRequest().setAttribute("siteId", orgDetails.getSiteId());
			context.getRequest().setAttribute("tipologia", 2);
			context.getRequest().setAttribute("tipoDest", null);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUpdateTicket(context, db);
			context.getRequest().setAttribute("OrgDetails", orgDetails);
			
			boolean checkScheda = false;
			
			checkScheda = newTic.isControlloChiudibileBaSa(db,false);
			context.getRequest().setAttribute("hasChecklistBenessere", checkScheda);	
			checkScheda = newTic.isControlloChiudibileBaSa(db,true);
			context.getRequest().setAttribute("hasChecklistCondizionalita", checkScheda);
			checkScheda = newTic.isControlloChiudibileFarmacosorveglianza(db);
			context.getRequest().setAttribute("hasChecklistFarmacosorveglianza", checkScheda);

			/* Costruzione campi gestiti */
			AziendeZootFields fields = new AziendeZootFields();
			fields.queryRecord(db, newTic.getId());

			context.getRequest().setAttribute("AzDetails", fields);
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			if (!isValid) {
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
			// return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandDettaglio(context));
	}

	public String executeCommandUploadListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {

			String filePath = this.getPath(context, "allevamenti");
			// Process the form data
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			multiPart.setUsePathParam(false);
			multiPart.setUseUniqueName(true);
			multiPart.setUseDateForFolder(true);
			multiPart.setExtensionId(getUserId(context));
			HashMap parts = multiPart.parseData(context.getRequest(), filePath);
			context.getRequest().setAttribute("parts", parts);
			db = this.getConnection(context);
			String temporgId = (String) parts.get("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
			SystemStatus systemStatus = this.getSystemStatus(context);

			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 2);
			context.getRequest().setAttribute("tipoDest", null);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUploadListaDistribuzione(context, db);
			// aggiunte queste istruzioni

			// Load the organization
			context.getRequest().setAttribute("OrgDetails", newOrg);

			String gotoInsert = (String) parts.get("gotoPage");
			if ("insert".equals(gotoInsert)) {
				if (((String) parts.get("actionSource")) != null) {
					context.getRequest().setAttribute("actionSource", (String) parts.get("actionSource"));
					return "AddTicketOK";
				}
				context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
				return ("AddOK");
			} else {
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

	public String executeCommandDettaglio(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-view")) {
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

		return (retPage);
	}

	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-vigilanza-edit"))) {
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

			// check permission to record
			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
				return ("PermissionError");
			}

			String ticketId = context.getRequest().getParameter("id");

			String chiudiSenzaLista = (String) context.getRequest().getAttribute("ChiudiSenzaLista");
			
			
			if(!oldTicket.isControlloChiudibileBaSa(db, false) || !oldTicket.isControlloChiudibileBaSa(db, true)){
				//context.getRequest().setAttribute("Messaggio1", "Il controllo non chiudibile");
				context.getRequest().setAttribute("Chiudi", "" + 1); //chiusura per benessere
				return (executeCommandTicketDetails(context));

			}
			
			if(!oldTicket.isControlloChiudibileFarmacosorveglianza(db)){
				//context.getRequest().setAttribute("Messaggio1", "Il controllo non chiudibile");
				context.getRequest().setAttribute("Chiudi", "" + 1); //chiusura per benessere/farmacosorveglianza
				return (executeCommandTicketDetails(context));

			}

			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId = thisTicket.getOrgId();
			ticList.setOrgId(passedId);
			ticList.buildListControlli(db, passedId, ticketId);

			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = thisTicket.getOrgId();
			tamponiList.setOrgId(passedId);
			tamponiList.buildListControlli(db, pasId, ticketId);

			Iterator campioniIterator = ticList.iterator();
			Iterator tamponiIterator = tamponiList.iterator();

			int flag = 0;
			while (campioniIterator.hasNext()) {

				org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator
						.next();

				if (tic.getClosed() == null) {
					flag = 1;

					break;

				}

			}

			org.aspcfs.modules.prvvedimentinc.base.TicketList ticListProvvedimenti = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
			int passId = thisTicket.getOrgId();
			ticListProvvedimenti.setOrgId(passId);
			ticListProvvedimenti.buildListControlli(db, passId, ticketId);
			Iterator provvedimentiIterator = ticListProvvedimenti.iterator();

			while (provvedimentiIterator.hasNext()) {

				org.aspcfs.modules.prvvedimentinc.base.Ticket tic = (org.aspcfs.modules.prvvedimentinc.base.Ticket) provvedimentiIterator
						.next();

				if (tic.getClosed() == null) {
					flag = 1;

					break;
				}

			}

			while (tamponiIterator.hasNext()) {

				org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator
						.next();

				if (tic.getClosed() == null) {
					flag = 1;

					break;
				}

			}

			if (thisTicket.isNcrilevate() == true) {
				org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = thisTicket.getOrgId();
				nonCList.setOrgId(passedId);
				nonCList.buildListControlli(db, passIdN, ticketId);

				Iterator ncIterator = nonCList.iterator();

				while (ncIterator.hasNext()) {

					org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator
							.next();

					if (tic.getClosed() == null) {
						flag = 1;

						break;

					}

				}

			}

			// NC di terzi
			org.aspcfs.modules.altriprovvedimenti.base.TicketList nonCList = new org.aspcfs.modules.altriprovvedimenti.base.TicketList();
			int passIdN = thisTicket.getOrgId();
			nonCList.setOrgId(passedId);
			nonCList.buildListControlli(db, passIdN, ticketId);
			Iterator ncIterator = nonCList.iterator();
			while (ncIterator.hasNext()) {
				org.aspcfs.modules.altriprovvedimenti.base.Ticket tic = (org.aspcfs.modules.altriprovvedimenti.base.Ticket) ncIterator
						.next();
				if (tic.getClosed() == null) {
					flag = 1;

					break;

				}

			}
			// FIne nc di terzi

			if (thisTicket.getTipoCampione() == 5) {

				if (thisTicket.isCategoriaisAggiornata() == false) {
					flag = 2;
				}

			}

			String attivitaCollegate = context.getRequest().getParameter("altro");

			if (attivitaCollegate == null) {
				if (flag == 1 || flag == 2) {
					context.getRequest().setAttribute("Chiudi", "" + flag);
					return (executeCommandTicketDetails(context));

				}
			}

			if (flag == 0) {
				thisTicket.setModifiedBy(getUserId(context));

				// Per nuova chiusura del controllo senza lista di riscontro
				thisTicket.setClosed_nolista(chiudiSenzaLista);

				resultCount = thisTicket.chiudi(db);
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

	public String executeCommandRicompilaSchedaB11(ActionContext context) {

		Connection db = null;

		try {

			db = getConnection(context);
			UserBean userBean = (UserBean) context.getSession().getAttribute("User");
			User user = userBean.getUserRecord();
			String idControllo = context.getParameter("id");

			Ticket cu = new Ticket(db, Integer.parseInt(idControllo));
			int aggiorna = cu.ricompilaB11(db, idControllo, user.getId());
			if (aggiorna > 0)
				context.getRequest().setAttribute("AggiornamentoOK",
						"Si puo procedere alla ricompilazione della scheda B11.");
			else
				context.getRequest().setAttribute("AggiornamentoOK",
						"Si e' verificato un problema durante l'aggiornamento. Contattare l'HD.");

			context.getRequest().setAttribute("TicketDetails", cu);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return executeCommandTicketDetails(context);

	}

	

	public String executeCommandChiudiInAssenzaDiNc(ActionContext context) {
		Connection db = null;
		Ticket thisTicket = null;

		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));

			// // Controllo se esiste un'istanza
			// String queryIstanza =
			// "select * from chk_bns_mod_ist where idcu = ? and trashed_date is null";
			// PreparedStatement ps1 = db.prepareStatement(queryIstanza);
			// ps1.setInt(1, thisTicket.getId());
			// ResultSet rs1 = ps1.executeQuery();
			// while (rs1.next()){
			// // Controllo se la lista ha punteggio maggiore di zero
			// int idIstanza = rs1.getInt("id");
			// }

			String query = "select * from get_punteggio_totale_chk_bns_animale(?)";
			PreparedStatement ps1 = db.prepareStatement(query);
			ps1.setInt(1, thisTicket.getId());
			ResultSet rs1 = ps1.executeQuery();
			int tot = -1, totA = 0, totB = 0, totC = 0;
			if (rs1.next()) {
				totA = rs1.getInt("totalea");
				totB = rs1.getInt("totaleb");
				totC = rs1.getInt("totalec");
				tot = totA + totB + totC;
			}

			String query2 = "select * from get_non_conformita_b11(?)";
			PreparedStatement ps2 = db.prepareStatement(query2);
			ps2.setInt(1, thisTicket.getId());
			ResultSet rs2 = ps2.executeQuery();
			int totb11 = -1;
			if (rs2.next()) {
				totb11 = rs2.getInt("totale");
			}

			if (tot > 0) {
				context.getRequest()
						.setAttribute(
								"ErroreAccountVigilanza",
								"Impossibile chiudere il controllo ufficiale. Risultano inserite delle liste di riscontro per il benessere animale con irregolarita' rilevate.");
				return executeCommandTicketDetails(context);
			} else if (totb11 > 0) {
				context.getRequest()
						.setAttribute(
								"ErroreAccountVigilanza",
								"Impossibile chiudere il controllo ufficiale. Risulta compilata la checklist condizionalita' b11 con non conformita' rilevate.");
				return executeCommandTicketDetails(context);
			} else {
				context.getRequest().setAttribute("ChiudiSenzaLista", "true");
				return executeCommandChiudiTutto(context);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "null";

	}

	public String executeCommandChiudiTutto(ActionContext context) {
		// public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-vigilanza-edit"))) {
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

}