/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stabilimenti.actions;

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


import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.stabilimenti.base.ControlloDocumentale;
import org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.stabilimenti.base.OrganizationAddressList;
import org.aspcfs.modules.stabilimenti.base.OrganizationList;
import org.aspcfs.modules.stabilimenti.base.PermessoVisibilitaStabilimenti;
import org.aspcfs.modules.stabilimenti.base.SottoAttivita;
import org.aspcfs.modules.stabilimenti.base.StatiStabilimenti;
import org.aspcfs.modules.stabilimenti.storico.CampoModificato;
import org.aspcfs.modules.stabilimenti.storico.StoricoModifica;
import org.aspcfs.modules.stabilimenti.storico.StoricoModificaList;


import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;
import org.eclipse.jdt.internal.compiler.ast.OperatorExpression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.beanutils.RowSetDynaClass;


import org.aspcfs.utils.web.LookupList;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Action for the Account module
 * 
 * @author chris
 * @created August 15, 2001
 * @version $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

	/**
	 * Default: not used
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDefault(ActionContext context) {

		return executeCommandDashboard(context);
	}

	public String executeCommandUpdatePregresso(ActionContext context) {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		try {
			db = this.getConnection(context);

			Organization oldOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			Organization newOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			newOrg.setName(context.getParameter("ragione_sociale"));
			newOrg.setPartitaIva(context.getParameter("partita_iva"));
			newOrg.setCodiceFiscale(context.getParameter("codice_fiscale"));

			newOrg.setNomeRappresentante(context.getParameter("nome_rappresentante"));
			newOrg.setCognomeRappresentante(context.getParameter("cognome_rappresentante"));
			newOrg.setCodiceFiscaleRappresentante(context.getParameter("codice_fiscale_rappresentante"));
			newOrg.setDataNascitaRappresentante(context.getParameter("data_nascita_rappresentante"));
			newOrg.updatePregresso(db);

			//Log rispetto alle modifiche in Organization
			ArrayList<CampoModificato> campi_modificati = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati = newOrg.checkModifiche(db, oldOrg);

			// Registro le modifiche
			StoricoModifica storicoModifica = new StoricoModifica();
			storicoModifica.setIdStabilimento(newOrg.getId());
			storicoModifica.setIdUtente(getUserId(context));
			storicoModifica.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica.setNrCampiModificati(campi_modificati.size());
			storicoModifica.setListaCampiModificati(campi_modificati);
			storicoModifica.setMotivazioneModifica("");
			if(campi_modificati.size() > 0){
				storicoModifica.insert(db, "Organization",context);
			}



			if(context.getParameter("addressId")!=null && ! "-1" .equalsIgnoreCase(context.getParameter("addressId")))
			{
				//Per lo storico
				org.aspcfs.modules.stabilimenti.base.OrganizationAddress oa_old = new org.aspcfs.modules.stabilimenti.base.OrganizationAddress(db,Integer.parseInt(context.getParameter("addressId")));

				org.aspcfs.modules.stabilimenti.base.OrganizationAddress oa = new org.aspcfs.modules.stabilimenti.base.OrganizationAddress(db,Integer.parseInt(context.getParameter("addressId")));

				oa.setCity(context.getParameter("comune_sede_legale"));
				oa.setStreetAddressLine1(context.getParameter("indirizzo_sede_legale"));
				oa.setState(context.getParameter("provincia_sede_legale"));
				oa.setZip(context.getParameter("cap_sede_legale"));
				oa.setId(context.getParameter("addressId"));
				oa.setType(1);
				oa.setOrgId(newOrg.getOrgId());
				oa.update(db, user.getUserId());

				//Log rispetto alle modifiche in Organization
				ArrayList<CampoModificato> campi_modificati_oa = new ArrayList<CampoModificato>();

				// Recupero i campi modificati per poterli salvare nel db per
				// tenere traccia delle modifiche
				campi_modificati_oa = oa.checkModifiche(db, oa_old);

				// Registro le modifiche
				StoricoModifica storicoModifica_oa = new StoricoModifica();
				storicoModifica_oa.setIdStabilimento(newOrg.getId());
				storicoModifica_oa.setIdUtente(getUserId(context));
				storicoModifica_oa.setDataModifica(new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime()));
				storicoModifica_oa.setNrCampiModificati(campi_modificati_oa.size());
				storicoModifica_oa.setListaCampiModificati(campi_modificati_oa);
				storicoModifica_oa.setMotivazioneModifica("");
				if(campi_modificati_oa.size() > 0){
					storicoModifica_oa.insert(db, "OrganizationAddress",context);
				}
			}
			else
			{
				org.aspcfs.modules.stabilimenti.base.OrganizationAddress oa = new org.aspcfs.modules.stabilimenti.base.OrganizationAddress();

				oa.setCity(context.getParameter("comune_sede_legale"));
				oa.setStreetAddressLine1(context.getParameter("indirizzo_sede_legale"));
				oa.setState(context.getParameter("provincia_sede_legale"));
				oa.setZip(context.getParameter("cap_sede_legale"));
				oa.setEnteredBy(user.getUserId());
				oa.setModifiedBy(user.getUserId());
				oa.setType(1);
				oa.setOrgId(newOrg.getOrgId());
				oa.insert(db,context);

			}
			context.getRequest().setAttribute("orgId", newOrg.getId());

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		return (executeCommandCompilaPregresso( context));


	}



	public String executeCommandCompilaPregresso(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-tamponi-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {
			db = this.getConnection(context);
			// find record permissions for portal users
			int passedId = -1 ;
			if(context.getRequest().getParameter("orgId")!=null )
				passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));
			else
			{
				passedId = (Integer)context.getRequest().getAttribute("orgId");

			}
			HashMap<String, String> lista_campi = new HashMap<String, String>();

			String ql = "select * from stabilimenti_dati_mancanti where org_id = "+passedId ;
			ResultSet rs = db.prepareStatement(ql).executeQuery();
			if (rs.next())
			{

				lista_campi.put("ragione_sociale", rs.getString("ragione_sociale"));
				lista_campi.put("partita_iva", rs.getString("partita_iva"));
				lista_campi.put("codice_fiscale", rs.getString("codice_fiscale"));

				lista_campi.put("cognome_rappresentante", rs.getString("cognome_rappresentante"));
				lista_campi.put("nome_rappresentante", rs.getString("nome_rappresentante"));
				lista_campi.put("codice_fiscale_rappresentante", rs.getString("codice_fiscale_rappresentante"));
				lista_campi.put("data_nascita_rappresentante", rs.getString("data_nascita_rappresentante"));


				lista_campi.put("comune_sede_legale", rs.getString("comune_sede_legale"));
				lista_campi.put("indirizzo_sede_legale", rs.getString("indirizzo_sede_legale"));
				lista_campi.put("provincia_sede_legale", rs.getString("provincia_sede_legale"));
				lista_campi.put("cap_sede_legale", rs.getString("cap_sede_legale"));



			}
			Organization org = new Organization(db,passedId);
			context.getRequest().setAttribute("ListaCampiPregressi", lista_campi);
			context.getRequest().setAttribute("OrgDetails", org);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ModificaPregresso");
	}

	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"StabilimentiTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink("Stabilimenti.do?command=ViewVigilanza&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			ArrayList elencoAttivita = SottoAttivita.loadByStabilimento(
					passedId, db);
			context.getRequest().setAttribute("elencoSottoAttivita",
					elencoAttivita);

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db,
					"lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db,
					"lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			TipoIspezione.setDefaultValue(-1);
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(passedId);

			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			/*if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}*/
			newOrg = new Organization(db, passedId);
			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, newOrg.getId());
			context.getRequest().setAttribute(
					"stabilimentiAssociateMercatoIttico",
					stabilimentiAssociateMercatoIttico);

			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			String statusId = context.getRequest().getParameter("statusId");
			ticList.setStatusId(statusId);
			
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

	public String executeCommandViewCessazionevariazione(ActionContext context) {
		if (!hasPermission(context,
				"stabilimenti-stabilimenti-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo
		.setLink("Stabilimenti.do?command=ViewCessazionevariazione&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			

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
		return getReturn(context, "ViewCessazionevariazione");
	}

	public String executeCommandViewControlloDocumentale(ActionContext context) {

		Connection db = null;
		try {
			db = getConnection(context);
			int idStabilimento = -1;
			if (context.getParameter("orgId") != null) {
				idStabilimento = Integer
						.parseInt(context.getParameter("orgId"));
			} else {
				idStabilimento = (Integer) context.getRequest().getAttribute(
						"orgId");
			}
			ControlloDocumentale cd = new ControlloDocumentale();
			cd.buildControlloDocumentale(db, idStabilimento);
			context.getRequest().setAttribute("ListaQuesiti", cd);

			Organization newOrg = new Organization(db, idStabilimento);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			if (context.getRequest().getAttribute("EsitoUpdate") != null) {
				if (((String) context.getRequest().getAttribute("EsitoUpdate"))
						.equals("KO")) {
					context.getRequest().setAttribute("EsitoUpdate",
							"Errore nel Salvataggio delle Informazioni");
				} else {
					context.getRequest().setAttribute("EsitoUpdate",
							"Salvataggio Avvenuto con successo");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "ViewControlloDocOK";
	}

	/**
	 * Search: Displays the Account search form
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "stabilimenti-stabilimenti-view"))) {
			return ("PermissionError");
		}

		/*
		 * if (!(hasPermission(context, "request-view"))) { return
		 * ("PermissionError"); }
		 */
		// Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			this.deletePagedListInfo(context, "SearchOrgListInfo");
			/*
			 * LookupList llist=new LookupList(db,"lookup_stabilimenti_types");
			 * llist.addItem(-1, "-nessuno-");
			 * context.getRequest().setAttribute("llist", llist);
			 */

			Comuni c = new Comuni();
			ArrayList<Comuni> listaComuni = c.buildList(db, ((UserBean) 
					context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList categoriaList = new LookupList(db, "lookup_categoria");
			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaList", categoriaList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("StatiStabilimenti",
					statiStabilimenti);
			UserBean user = (UserBean) context.getRequest().getSession()
					.getAttribute("User");

			PermessoVisibilitaStabilimenti permessi = new PermessoVisibilitaStabilimenti();
			PermessoVisibilitaStabilimenti permessiRuolo = permessi
					.getPermessiRuolo(db, user.getRoleId(),super.getSuffiso(context));

			context.getRequest().setAttribute("PermessiRuolo", permessiRuolo);

			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context,
					"SearchOrgListInfo");
			orgListInfo.setCurrentLetter(context.getRequest().getParameter(
					"letter"));
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
						.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map
					.put(
							(String) orgListInfo
							.getSearchOptionValue("searchcodeContactCountry"),
							(String) orgListInfo
							.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect",
						stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null
					&& !"-1".equals(orgListInfo
							.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(
						systemStatus,
						orgListInfo
						.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map
					.put(
							(String) orgListInfo
							.getSearchOptionValue("searchcodeAccountCountry"),
							(String) orgListInfo
							.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect",
						stateSelect);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}

	public String executeCommandSavePermessiVisibilita(ActionContext context)
			throws SQLException {

		context.getRequest().setAttribute("Message", "si");
		PermessoVisibilitaStabilimenti permessi = new PermessoVisibilitaStabilimenti();
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			int numRuoli = Integer.parseInt(context.getParameter("numRuoli"));
			int numStati = Integer.parseInt(context.getParameter("numStati"));
			for (int i = 0; i < numRuoli; i++) {
				int idRuolo = Integer.parseInt(context.getParameter("ruolo_"
						+ i));
				permessi.deletePermessiVisibilita(db, idRuolo);
				for (int j = 0; j < numStati; j++) {
					if (context.getParameter("permesso_" + i + "_" + j) != null) {
						int idStato = Integer.parseInt(context
								.getParameter("permesso_" + i + "_" + j));
						permessi.savePermessiVisibilita(db, idRuolo, idStato);
					}

				}

			}
		} catch (SQLException e) {
			throw e;
		}
		return executeCommandViewPermessiVisibilita(context);
	}

	public String executeCommandViewPermessiVisibilita(ActionContext context) {
		// if (!hasPermission(context,
		// "stabilimenti-stabilimenti-visibilita-stati")) {
		// return ("PermissionError");
		// }
		Connection db = null;
		try {
			if (context.getRequest().getAttribute("Inserito") != null)
				context.getRequest().setAttribute("Inserito", "no");
			db = this.getConnection(context);

			LookupList stati_stabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");
			context.getRequest().setAttribute("StatiStabilimenti",
					stati_stabilimenti);
			PermessoVisibilitaStabilimenti permessi = new PermessoVisibilitaStabilimenti();

			ArrayList<PermessoVisibilitaStabilimenti> lista_permessi = permessi
					.getListaRuoliPermessi(db,super.getSuffiso(context) );

			context.getRequest().setAttribute("lista_permessi", lista_permessi);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "ViewVisibilitaStati";
	}

	/**
	 * Add: Displays the form used for adding a new Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			if (context.getRequest().getAttribute("Inserito") != null)
				context.getRequest().setAttribute("Inserito", "no");
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			// aggiunto da d.dauria TitoloList e' il nome che poi dichiaro come
			// usebean nella jsp


			LookupList lookup_impianto = new LookupList(db, "lookup_impianto");
			lookup_impianto.addItem(-1, "-SELEZIONA -");
			context.getRequest().setAttribute("impianto", lookup_impianto);

			LookupList lookup_categoria = new LookupList(db, "lookup_categoria");
			lookup_categoria.addItem(-1, "-SELEZIONA -");
			context.getRequest().setAttribute("categoria", lookup_categoria);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-SELEZIONA -");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList statoLabImp = new LookupList(db,
					"lookup_stato_lab_impianti");
			statoLabImp.addItem(-1, "-SELEZIONA -");
			context.getRequest().setAttribute("statoLabImp", statoLabImp);

			LookupList tipoAutorizzazzione = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			tipoAutorizzazzione.addItem(-1, "-SELEZIONA -");
			context.getRequest().setAttribute("tipoAutorizzazzione",
					tipoAutorizzazzione);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList lookup_prodotti = new LookupList(db,
					"lookup_prodotti_stabilimenti");
			lookup_prodotti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest()
			.setAttribute("LookupProdotti", lookup_prodotti);

			LookupList lookup_classificazione = new LookupList(db,
					"lookup_classificazione_stabilimenti");
			lookup_classificazione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("LookupClassificazione",
					lookup_classificazione);


			// LOOKUP CATEGORIA AGGIUNTA DA GIUSEPPE
			LookupList categoriaList = new LookupList(db, "lookup_categoria");
			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CategoriaList", categoriaList);

			Organization newOrg = (Organization) context.getFormBean();

			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());

			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			StateSelect stateSelect = new StateSelect(systemStatus, (newOrg
					.getAddressList() != null ? newOrg.getAddressList()
							.getCountries() : "")
							+ prefs.get("SYSTEM.COUNTRY"));
			stateSelect
			.setPreviousStates((newOrg.getAddressList() != null ? newOrg
					.getAddressList().getSelectedStatesHashMap()
					: new HashMap()));
			context.getRequest().setAttribute("StateSelect", stateSelect);
			if (newOrg.getEnteredBy() != -1) {
				newOrg.setTypeListToTypes(db);
				context.getRequest().setAttribute("OrgDetails", newOrg);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		if ((context.getRequest().getParameter("tipoIstruttoria") != null && context
				.getRequest().getParameter("tipoIstruttoria").equals("1"))
				|| (context.getRequest().getAttribute("tipoIstruttoria") != null && ((String) context
						.getRequest().getAttribute("tipoIstruttoria"))
						.equals("1")))
			return getReturn(context, "Add");
		else
			return "AddIstruttoriaOK";
	}

	public String executeCommandexport(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}

		String source = (String) context.getRequest().getParameter("source");
		OrganizationList organizationList = new OrganizationList();
		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("Stabilimenti.do?command=Search");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			// For portal usr set source as 'searchForm' explicitly since
			// the search form is bypassed.
			// temporary solution for page redirection for portal user.
			if (isPortalUser(context)) {
				organizationList.setOrgSiteId(this.getUserSiteId(context));
				source = "searchForm";
			}
			organizationList.setTipologia(3);
			if ((context.getParameter("searchNumAut")) != null) {
				organizationList
				.setNumAut(context.getParameter("searchNumAut"));
			}
			// return if no criteria is selected
			if ((searchListInfo.getListView() == null || ""
					.equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);


			// inserito da Carmela
			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo
					.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					searchListInfo.setLink("Stabilimenti.do?command=Search");
				}
				// Build the organization list
				// organizationList.setPagedListInfo(searchListInfo);
				organizationList.setMinerOnly(false);
				organizationList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));
				if ((context.getParameter("searchNumAut")) != null) {
					organizationList.setNumAut(context
							.getParameter("searchNumAut"));
				}
				organizationList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(organizationList, context);
				// fetching criterea for account source (my accounts or all
				// accounts)
				if ("my".equals(searchListInfo.getListView())) {
					organizationList.setOwnerId(this.getUserId(context));
				}
				if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (organizationList.getOrgSiteId() == -1) {
					organizationList.setIncludeOrganizationWithoutSite(true);
				}
				// fetching criterea for account status (active, disabled or
				// any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				organizationList.setIncludeEnabled(enabled);
				// If the user is a portal user, fetching only the
				// the organization that he access to
				// (i.e., the organization for which he is an account contact
				if (isPortalUser(context)) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
					organizationList
					.setOrgId(getPortalUserPermittedOrgId(context));
				}
				// System.out.println(
				// "stabilimenti: search -> build list (prima) " +
				// organizationList.toString() );
				organizationList.buildList(db);
				// System.out.println(
				// "stabilimenti: search -> build list (dopo) " +
				// organizationList.toString() );
				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType",
						"accounts");
				if (organizationList.size() == 1
						&& organizationList.getAssetSerialNumber() != null) {
					AssetList assets = new AssetList();
					assets.setOrgId(((Organization) organizationList.get(0))
							.getId());
					assets.setSerialNumber(organizationList
							.getAssetSerialNumber());
					assets.setSkipParentIdRequirement(true);
					assets.buildList(db);
					if (assets.size() == 1) {
						Asset asset = (Asset) assets.get(0);
						context.getRequest().setAttribute("id",
								String.valueOf(asset.getId()));
						return "esportaOK";
					}
				}
				return "esportaOK";
			} else {}
		} catch (Exception e) {
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}return "" ;

	}

//	public String executeCommandModifyStabilimento(ActionContext context) {
//		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
//			return ("PermissionError");
//		}
//		Connection db = null;
//		SystemStatus systemStatus = this.getSystemStatus(context);
//		Organization newOrg = null;
//		ArrayList elencoAttivita = null;
//		try {
//
//			db = this.getConnection(context);
//			newOrg = new Organization();
//
//			newOrg = newOrg.load(context.getRequest().getParameter(
//					"account_number"), db);
//
//			ControlloDocumentale cd = new ControlloDocumentale();
//			cd.buildControlloDocumentale(db, newOrg.getOrgId());
//			context.getRequest().setAttribute("ControlloDocumentale", cd);
//
//			/**
//			 * se lo stato e' riconosciuto condizionato
//			 */
//
//			// Dopo l'inserimento riconverti
//			Iterator it_coords = newOrg.getAddressList().iterator();
//			while (it_coords.hasNext()) {
//
//				org.aspcfs.modules.stabilimenti.base.OrganizationAddress thisAddress = (org.aspcfs.modules.stabilimenti.base.OrganizationAddress) it_coords
//						.next();
//				if (thisAddress.getLatitude() != 0
//						&& thisAddress.getLongitude() != 0) {
//
//					String spatial_coords[] = null;
//					spatial_coords = this.converToWgs84UTM33NInverter(Double
//							.toString(thisAddress.getLatitude()), Double
//							.toString(thisAddress.getLongitude()), db);
//
//					thisAddress.setLatitude(spatial_coords[0]);
//					thisAddress.setLongitude(spatial_coords[1]);
//
//				}
//			}
//
//			LookupList lookup_categoria = new LookupList(db, "lookup_categoria");
//			lookup_categoria.addItem(-1, "-SELEZIONA -");
//			context.getRequest().setAttribute("categoria", lookup_categoria);
//
//			LookupList statiStabilimenti = new LookupList(db,
//					"lookup_stati_stabilimenti");
//			context.getRequest().setAttribute("statiStabilimenti",
//					statiStabilimenti);
//
//			LookupList siteList = new LookupList(db, "lookup_site_id");
//			siteList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("SiteList", siteList);
//
//			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
//			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("statoLab", statoLab);
//
//			LookupList categoriaRischioList = new LookupList(db,
//					"lookup_org_catrischio");
//			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("OrgCategoriaRischioList",
//					categoriaRischioList);
//
//			LookupList impianto = new LookupList(db, "lookup_impianto");
//			impianto.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("impianto", impianto);
//
//			LookupList IstatList = new LookupList(db, "lookup_codistat");
//			IstatList.addItem(-1, getSystemStatus(context).getLabel(
//					"calendar.none.4dashes"));
//			context.getRequest().setAttribute("IstatList", IstatList);
//
//			LookupList lookup_prodotti = new LookupList(db,
//					"lookup_prodotti_stabilimenti");
//			lookup_prodotti.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest()
//			.setAttribute("LookupProdotti", lookup_prodotti);
//
//			LookupList lookup_classificazione = new LookupList(db,
//					"lookup_classificazione_stabilimenti");
//			lookup_classificazione.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("LookupClassificazione",
//					lookup_classificazione);
//
//
//			LookupList categoriaList = new LookupList(db, "lookup_categoria");
//			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("categoriaList", categoriaList);
//
//			LookupList imballataList = new LookupList(db,
//					"lookup_sottoattivita_imballata");
//			imballataList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("imballataList", imballataList);
//
//			LookupList tipoAutorizzazioneList = new LookupList(db,
//					"lookup_sottoattivita_tipoautorizzazione");
//			tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("tipoAutorizzazioneList",
//					tipoAutorizzazioneList);
//
//			elencoAttivita = SottoAttivita.loadByStabilimento(
//					newOrg.getOrgId(), db);
//
//			/*
//			 * org.aspcfs.modules.audit.base.AuditList audit = new
//			 * org.aspcfs.modules.audit.base.AuditList(); int AuditOrgId =
//			 * newOrg.getOrgId(); audit.setOrgId(AuditOrgId);
//			 * 
//			 * //audit.buildList(db);
//			 */
//
//			/*
//			 * if( (audit.size() - 1)>=0){
//			 * 
//			 * context.getRequest().setAttribute("Audit",audit.get(0) ); }
//			 */
//			context.getRequest().setAttribute("elencoSottoAttivita",
//					elencoAttivita);
//
//		} catch (Exception e) {
//			context.getRequest().setAttribute("Error", e);
//			return ("SystemError");
//		} finally {
//			this.freeConnection(context, db);
//		}
//
//		// If user is going to the detail screen
//		addModuleBean(context, "View Accounts", "View Account Details");
//		context.getRequest().setAttribute("OrgDetails", newOrg);
//		return "ModifyIstruttoriaOK";
//
//	}

	
	
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		ArrayList elencoAttivita = null;
		try {

			

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);

			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer
					.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);

			 //Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	       
			// check whether or not the owner is an active User
			newOrg.setOwner(this.getUserId(context));
			newOrg.checkPregresso(db);
			int nCategoriaMax = maxCategoriaRischioMercatoIttico(context,
					tempid,db);

			if (nCategoriaMax != newOrg.getCategoriaPrecedente())
				aggiornaCategoriaRischioGlobaleMercatoIttico(context, tempid,db);

			
			context.getRequest().setAttribute("eliminazioneKo",context.getRequest().getParameter("eliminazioneKo"));
			elencoAttivita = SottoAttivita.loadByStabilimento(tempid, db);
			
			/******* CONTROLLO LINEE PREGRESSE *********
			PreparedStatement pst = db	.prepareStatement("select * from stab_org_id_da_mappare where mappato = false and org_id = ? ");
			pst.setInt(1, tempid);
			ResultSet rs = pst.executeQuery();
			boolean linee_pregresse=false;
			if (rs.next()) {
				linee_pregresse=true;
			}
			context.getRequest().setAttribute("linee_pregresse",""+linee_pregresse);
			rs.close(); pst.close();
			*******************************************/

			/*
			 * org.aspcfs.modules.audit.base.AuditList audit = new
			 * org.aspcfs.modules.audit.base.AuditList(); int AuditOrgId =
			 * newOrg.getOrgId(); audit.setOrgId(AuditOrgId);
			 * 
			 * //audit.buildList(db);
			 */

			/*
			 * if( (audit.size() - 1)>=0){
			 * 
			 * context.getRequest().setAttribute("Audit",audit.get(0) ); }
			 */
			context.getRequest().setAttribute("elencoSottoAttivita",
					elencoAttivita);

			ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriImpreseById(db, newOrg.getId());
			context.getRequest().setAttribute("impreseAssociateMercatoIttico",
					impreseAssociateMercatoIttico);

			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, newOrg.getId());
			context.getRequest().setAttribute(
					"stabilimentiAssociateMercatoIttico",
					stabilimentiAssociateMercatoIttico);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			// If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			// If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "Details");
		}
	}
	
	
	/**
	 * Details: Displays all details relating to the selected Account. The user
	 * can also goto a modify page from this form or delete the Account entirely
	 * from the database
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDetailsOld(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		ArrayList elencoAttivita = null;
		try {

			executeCommandListaOperatoriMercatoIttico(context);

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);

			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer
					.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);

			 //Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	       
			
			ControlloDocumentale cd = new ControlloDocumentale();
			cd.buildControlloDocumentale(db, tempid);
			context.getRequest().setAttribute("ControlloDocumentale", cd);

			/**
			 * se lo stato e' riconosciuto condizionato
			 */

			// Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.stabilimenti.base.OrganizationAddress thisAddress = (org.aspcfs.modules.stabilimenti.base.OrganizationAddress) it_coords
						.next();
				if (thisAddress.getLatitude() != 0
						&& thisAddress.getLongitude() != 0) {

					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double
							.toString(thisAddress.getLatitude()), Double
							.toString(thisAddress.getLongitude()), db);

					thisAddress.setLatitude(spatial_coords[0]);
					thisAddress.setLongitude(spatial_coords[1]);

				}
			}

			// check whether or not the owner is an active User
			newOrg.setOwner(this.getUserId(context));
			newOrg.checkPregresso(db);
			int nCategoriaMax = maxCategoriaRischioMercatoIttico(context,
					tempid,db);

			if (nCategoriaMax != newOrg.getCategoriaPrecedente())
				aggiornaCategoriaRischioGlobaleMercatoIttico(context, tempid,db);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");
			context.getRequest().setAttribute("statiStabilimenti",
					statiStabilimenti);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList statoLabImpianti = new LookupList(db, "lookup_stato_lab_impianti");

			statoLabImpianti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLabImpianti", statoLabImpianti);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			LookupList lookup_prodotti = new LookupList(db,
					"lookup_prodotti_stabilimenti");
			lookup_prodotti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest()
			.setAttribute("LookupProdotti", lookup_prodotti);

			LookupList lookup_classificazione = new LookupList(db,
					"lookup_classificazione_stabilimenti");
			lookup_classificazione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("LookupClassificazione",
					lookup_classificazione);

			LookupList categoriaList = new LookupList(db, "lookup_categoria");
			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("categoriaList", categoriaList);

			LookupList imballataList = new LookupList(db,
					"lookup_sottoattivita_imballata");
			imballataList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("imballataList", imballataList);

			LookupList tipoAutorizzazioneList = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			context.getRequest().setAttribute("tipoAutorizzazioneList",
					tipoAutorizzazioneList);

			elencoAttivita = SottoAttivita.loadByStabilimento(tempid, db);

			/*
			 * org.aspcfs.modules.audit.base.AuditList audit = new
			 * org.aspcfs.modules.audit.base.AuditList(); int AuditOrgId =
			 * newOrg.getOrgId(); audit.setOrgId(AuditOrgId);
			 * 
			 * //audit.buildList(db);
			 */

			/*
			 * if( (audit.size() - 1)>=0){
			 * 
			 * context.getRequest().setAttribute("Audit",audit.get(0) ); }
			 */
			context.getRequest().setAttribute("elencoSottoAttivita",
					elencoAttivita);

			ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriImpreseById(db, newOrg.getId());
			context.getRequest().setAttribute("impreseAssociateMercatoIttico",
					impreseAssociateMercatoIttico);

			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, newOrg.getId());
			context.getRequest().setAttribute(
					"stabilimentiAssociateMercatoIttico",
					stabilimentiAssociateMercatoIttico);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			// If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			// If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "Details");
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-dashboard-view")) {
			if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
				return ("PermissionError");
			}
			// Bypass dashboard and search form for portal users
			if (isPortalUser(context)) {
				return (executeCommandSearch(context));
			}
			return (executeCommandSearchForm(context));
		}
		addModuleBean(context, "Dashboard", "Dashboard");


		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");



		context.getRequest().setAttribute("Return", "Accounts");
		return ("DashboardOK");
	}

	private String getImpiantoSearch(String categoria_id, ActionContext context) {
		String toRet = "-1";
		if (categoria_id != null && !categoria_id.equals("-1")) {
			if (categoria_id.equals("0")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita0");
			}
			if (categoria_id.equals("1")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita1");
			}
			if (categoria_id.equals("2")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita2");
			}
			if (categoria_id.equals("3")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita3");
			}
			if (categoria_id.equals("4")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita4");
			}
			if (categoria_id.equals("5")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita5");
			}
			if (categoria_id.equals("6")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita6");
			}
			if (categoria_id.equals("7")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita7");
			}
			if (categoria_id.equals("8")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita8");
			}
			if (categoria_id.equals("9")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita9");
			}
			if (categoria_id.equals("15")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita15");
			}
			if (categoria_id.equals("10")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita10");
			}
			if (categoria_id.equals("11")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita11");
			}
			if (categoria_id.equals("12")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita12");
			}
			if (categoria_id.equals("13")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita13");
			}
			if (categoria_id.equals("14")) {
				toRet = (String) context.getRequest().getParameter(
						"searchcodeSottoAttivita14");
			}

		}
		return toRet;
	}

	public String executeCommandCondizionatiInScadenza(ActionContext context) {

		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}

		int i = 0;

		// String sottoAttivita = getImpiantoSearch(categoria_id,context);

		OrganizationList organizationList = new OrganizationList();
		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo

		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("Stabilimenti.do?command=CondizionatiInScadenza");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			// For portal usr set source as 'searchForm' explicitly since
			// the search form is bypassed.
			// temporary solution for page redirection for portal user.

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");

			context.getRequest().setAttribute("statiStabilimenti",
					statiStabilimenti);


			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			// inserito da Carmela
			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			// Build the organization list
			organizationList.setPagedListInfo(searchListInfo);

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			int idAsl = user.getSiteId();
			ArrayList<SottoAttivita> listaSottoattivita = SottoAttivita
					.getSottoAttivitaCondizionateinScadenza(db, idAsl);

			context.getRequest().setAttribute("OrgList", listaSottoattivita);
			context.getSession().setAttribute("previousSearchType", "accounts");

			LookupList tipoAutorizzazioneList = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoAutorizzazioneList",
					tipoAutorizzazioneList);

			// fetching criterea for account status (active, disabled or any)

		} catch (Exception e) {
			// Go through the SystemError process
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ScadenzarioCondizionatiOK";
	}

	public String executeCommandSospesoInScadenza(ActionContext context) {

		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}

		int i = 0;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int idAsl = user.getSiteId();

		// String sottoAttivita = getImpiantoSearch(categoria_id,context);

		OrganizationList organizationList = new OrganizationList();
		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo

		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("Stabilimenti.do?command=Search");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			// For portal usr set source as 'searchForm' explicitly since
			// the search form is bypassed.
			// temporary solution for page redirection for portal user.

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");

			context.getRequest().setAttribute("statiStabilimenti",
					statiStabilimenti);


			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			// inserito da Carmela
			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			// Build the organization list
			organizationList.setPagedListInfo(searchListInfo);

			ArrayList<SottoAttivita> listaSottoattivita = SottoAttivita
					.getSottoAttivitaSospeseinScadenza(db, idAsl);

			context.getRequest().setAttribute("OrgList", listaSottoattivita);
			context.getSession().setAttribute("previousSearchType", "accounts");

			LookupList tipoAutorizzazioneList = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoAutorizzazioneList",
					tipoAutorizzazioneList);

			// fetching criterea for account status (active, disabled or any)

		} catch (Exception e) {
			// Go through the SystemError process
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ScadenzarioSospesiOK";
	}

	/**
	 * Search Accounts
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandSearch(ActionContext context) {

		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}
		String categoriaR = (String) context.getRequest().getParameter(
				"searchcodecategoriaRischio");

		String source = (String) context.getRequest().getParameter("source");
		String stato = (String) context.getRequest().getParameter("statoLab");
		String categoria_id = (String) context.getRequest().getParameter(
				"searchcodeCodiceSezione");
		String categoria_R = (String) context.getRequest().getParameter(
				"searchcodeCategoria");
		String codiceAllerta = context.getRequest().getParameter(
				"searchcodiceAllerta");
		String statoCu = context.getRequest().getParameter("searchstatoCu");
		// String sottoAttivita =
		// (String)context.getRequest().getParameter("searchSottoAttivita");

		int i = 0;
		boolean ok = false;

		// String sottoAttivita = getImpiantoSearch(categoria_id,context);

		OrganizationList organizationList = new OrganizationList();
		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo

		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("Stabilimenti.do?command=Search");
		searchListInfo.setColumnToSortBy("o.name");
		// Need to reset any sub PagedListInfos since this is a new account

		Connection db = null;
		try {
			db = this.getConnection(context);

			// For portal usr set source as 'searchForm' explicitly since
			// the search form is bypassed.
			// temporary solution for page redirection for portal user.
			if (isPortalUser(context)) {
				organizationList.setOrgSiteId(this.getUserSiteId(context));
				source = "searchForm";
			}
			// if(!"".equals(sottoAttivita) && sottoAttivita!=null &&
			// !sottoAttivita.equals("-1"))
			// organizationList.setSottoAttivita(sottoAttivita);
			if (!"".equals(categoria_id) && categoria_id != null
					&& !categoria_id.equals("-1"))
				organizationList.setCodiceSezione(Integer
						.parseInt(categoria_id));
			organizationList.setTipologia(3);

			if (!"".equals(stato) && stato != null && !stato.equals("-1")) {
				organizationList.setStatoLab(stato);
			}
			if ((context.getParameter("searchNumAut")) != null) {
				organizationList
				.setNumAut(context.getParameter("searchNumAut"));
			}
			if (!"".equals(statoCu) && statoCu != null && !statoCu.equals("-1")) {
				organizationList.setStatoCu(statoCu);
			}

			if (categoriaR != null)
				organizationList.setCategoriaRischio(Integer
						.parseInt(categoriaR));

			/*
			 * org.aspcfs.modules.audit.base.AuditList audit = new
			 * org.aspcfs.modules.audit.base.AuditList(); //audit.buildList(db);
			 * context.getRequest().setAttribute("AuditList",audit );
			 */




			// return if no criteria is selected



			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");

			context.getRequest().setAttribute("statiStabilimenti",
					statiStabilimenti);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			// inserito da Carmela
			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo
					.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
						"previousSearchType"))) {

					searchListInfo = this.getPagedListInfo(context,
							"SearchOrgListInfo");
					
				}
				// Build the organization list
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.setMinerOnly(false);
				organizationList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));
				// organizationList.setStatoIstruttoria(Integer.parseInt(
				// context.getParameter("searchcodestatoIstruttoria")));
				organizationList.setListaStatiIstruttoria(context.getRequest()
						.getParameterValues("searchgrouplistaStatiIstruttoria"));

				if ((context.getParameter("searchNumAut")) != null) {
					organizationList.setNumAut(context
							.getParameter("searchNumAut"));
				}
				organizationList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(organizationList, context);

				if (!"".equals(codiceAllerta) && codiceAllerta != null && context.getParameter("flagAllerte")!=null)
					organizationList.setCodiceAllerta(codiceAllerta);
				else
					organizationList.setCodiceAllerta(null);
				// fetching criterea for account source (my accounts or all
				// accounts)
				if ("my".equals(searchListInfo.getListView())) {
					organizationList.setOwnerId(this.getUserId(context));
				}
				if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (organizationList.getOrgSiteId() == -1) {
					organizationList.setIncludeOrganizationWithoutSite(true);
				}
				// fetching criterea for account status (active, disabled or
				// any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				organizationList.setIncludeEnabled(enabled);
				// If the user is a portal user, fetching only the
				// the organization that he access to
				// (i.e., the organization for which he is an account contact
				if (isPortalUser(context)) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
					organizationList
					.setOrgId(getPortalUserPermittedOrgId(context));
				}

				organizationList.buildList(db);
				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType",
						"accounts");

				return "ListOK";
			} 
		} catch (Exception e) {
			// Go through the SystemError process
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "";
	}

	/**
	 * ViewTickets: Displays Ticket history (open and closed) for a particular
	 * Account.
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandViewTickets(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-tickets-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		TicketList ticList = new TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink("Stabilimenti.do?command=ViewTickets&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
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
		return getReturn(context, "ViewTickets");
	}

	public String executeCommandUpdateControlloDocumentale(ActionContext context) {

		Connection db = null;
		int idStabilimento = Integer.parseInt(context
				.getParameter("idStabilimento"));
		int numQuesiti = Integer.parseInt(context.getParameter("numQuesiti"));
		int idCD = Integer.parseInt(context
				.getParameter("idControlloDocumentale"));





		try {
			db = this.getConnection(context);

			ControlloDocumentale cd_old = new ControlloDocumentale();
			cd_old.buildControlloDocumentale(db, idStabilimento);

			Organization oldOrg = new Organization(db, idStabilimento);

			PreparedStatement pst_1 = null;
			PreparedStatement pst_2 = null;
			boolean updateStato = false;
			String statoControlloDocumentaleAsl = context
					.getParameter("stato_asl");
			String statoControlloDocumentaleStap = context
					.getParameter("stato_stap");
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			for (int i = 0; i < numQuesiti; i++) {
				boolean risposta = false;

				String sql_1 = "";
				String sql_2 = "";

				int idQuesito = Integer.parseInt(context
						.getParameter("id_quesito_" + i));
				String risposta_asl = context.getParameter("risposta_asl_" + i);
				String risposta_stap = context.getParameter("risposta_stap_"
						+ i);
				String noteStap = context.getParameter("note_stap_" + i);
				String statoDontrolloDocumentale = "";
				String competenzaAsl = context.getParameter("competenzaAsl_"
						+ i);
				/* SE e' UNO STAP */
				if (user.getRoleId() == 40) {
					statoDontrolloDocumentale = statoControlloDocumentaleStap;
					if (risposta_stap != null) {
						risposta = true;
					}
					sql_1 = "update quesiti_controllo_documentale_stabilimenti set stato_stap = ?, user_id_stap = ?,modified_stap=current_timestamp where id_stabilimento = ? ";
					sql_2 = "update quesiti_risposte_controllo_documentale set risposta_stap = ?,note_stap = ? where id_quesito = ? and id_quesiti_controllo_documentale_stabilimenti = ? ";

				} else {
					statoDontrolloDocumentale = statoControlloDocumentaleAsl;
					if (statoControlloDocumentaleAsl.equals("1")) // se lo stato
						// e'
						// definitvo
						updateStato = true;
					if (risposta_asl != null) {
						risposta = true;
					}

					sql_1 = "update quesiti_controllo_documentale_stabilimenti set stato_asl = ?, user_id_asl = ?,modified_asl=current_timestamp where id_stabilimento = ? ";

					if (competenzaAsl.equalsIgnoreCase("true")) {
						sql_2 = "update quesiti_risposte_controllo_documentale set risposta_asl = ? where id_quesito = ? and id_quesiti_controllo_documentale_stabilimenti = ? ";
					} else {
						sql_2 = "";
					}


				}

				pst_1 = db.prepareStatement(sql_1);
				pst_1.setInt(1, Integer.parseInt(statoDontrolloDocumentale));
				pst_1.setInt(2, user.getUserId());
				pst_1.setInt(3, idStabilimento);

				int j = 0;
				if (!sql_2.equals("")) {
					pst_2 = db.prepareStatement(sql_2);
					pst_2.setBoolean(++j, risposta);
					if (user.getRoleId() == 40) {
						pst_2.setString(++j, noteStap);
					}

					pst_2.setInt(++j, idQuesito);
					pst_2.setInt(++j, idCD);

					pst_2.execute();

				}
				pst_1.execute();



			}

			/*
			 * SALVATO IL CONTROLLO DOCUMENTALE LA PRATICA PASSA NELLO STATO 2
			 * (ISTRUTTORIA DOCUMENTAZIONE CONTROLLATA)
			 */

			ControlloDocumentale cd = new ControlloDocumentale();
			cd.buildControlloDocumentale(db, idStabilimento);

			//Log rispetto alle modifiche in ControlloDocumentale
			ArrayList<CampoModificato> campi_modificati_cd = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati_cd = cd.checkModifiche(db,cd_old);

			// Registro le modifiche
			StoricoModifica storicoModifica_cd = new StoricoModifica();
			storicoModifica_cd.setIdStabilimento(idStabilimento);
			storicoModifica_cd.setIdUtente(getUserId(context));
			storicoModifica_cd.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica_cd.setNrCampiModificati(campi_modificati_cd.size());
			storicoModifica_cd.setListaCampiModificati(campi_modificati_cd);
			storicoModifica_cd.setMotivazioneModifica("");
			if(campi_modificati_cd.size() > 0) {
				storicoModifica_cd.insert(db, "ControlloDocumentale",context);
			}



			/*
			 * se lo stap ha salvato definitivamente e ha riscontrato problemi
			 * riapro la pratica per l'asl
			 */
			if (statoControlloDocumentaleStap.equals("1")
					&& user.getRoleId() == 40
					&& cd.getEsitoControlloFavorevole() == false) {
				String sql = "update quesiti_controllo_documentale_stabilimenti set stato_asl = 0 where id_stabilimento = ? ";
				PreparedStatement pst_up = db.prepareStatement(sql);
				pst_up.setInt(1, idStabilimento);
				pst_up.execute();

				PreparedStatement pst = db
						.prepareStatement("update organization set stato_istruttoria = 1 where org_id = ? ");
				pst.setInt(1, idStabilimento);
				pst.execute();

			} else {
				if (statoControlloDocumentaleAsl.equals("1")
						&& user.getRoleId() != 40) {

					String sql = "update quesiti_controllo_documentale_stabilimenti set stato_stap = 0 where id_stabilimento = ? ";

					PreparedStatement pst_up = db.prepareStatement(sql);
					pst_up.setInt(1, idStabilimento);
					pst_up.execute();

				}
			}

			if (updateStato == true) {
				PreparedStatement pst_up = db
						.prepareStatement("update organization set stato_istruttoria = 2 where org_id = ? ");
				pst_up.setInt(1, idStabilimento);
				pst_up.execute();
			}

			ControlloDocumentale cd_2 = new ControlloDocumentale();
			cd_2.buildControlloDocumentale(db, idStabilimento);
			//Log rispetto alle modifiche in ControlloDocumentale
			ArrayList<CampoModificato> campi_modificati_cd_2 = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati_cd_2 = cd.checkModifiche(db,cd);

			// Registro le modifiche
			StoricoModifica storicoModifica_cd_2 = new StoricoModifica();
			storicoModifica_cd_2.setIdStabilimento(idStabilimento);
			storicoModifica_cd_2.setIdUtente(getUserId(context));
			storicoModifica_cd_2.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica_cd_2.setNrCampiModificati(campi_modificati_cd_2.size());
			storicoModifica_cd_2.setListaCampiModificati(campi_modificati_cd_2);
			storicoModifica_cd_2.setMotivazioneModifica("");
			if(campi_modificati_cd_2.size() > 0) {
				storicoModifica_cd_2.insert(db, "ControlloDocumentale",context);
			}


			Organization o = new Organization(db, idStabilimento);

			//Log rispetto alle modifiche in Organization
			ArrayList<CampoModificato> campi_modificati = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati = o.checkModifiche(db, oldOrg);

			// Registro le modifiche
			StoricoModifica storicoModifica = new StoricoModifica();
			storicoModifica.setIdStabilimento(o.getId());
			storicoModifica.setIdUtente(getUserId(context));
			storicoModifica.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica.setNrCampiModificati(campi_modificati.size());
			storicoModifica.setListaCampiModificati(campi_modificati);
			storicoModifica.setMotivazioneModifica("");
			if(campi_modificati.size() > 0) {
				storicoModifica.insert(db, "Organization",context);
			}

			context.getRequest().setAttribute("OrgDetails", o);
		} catch (SQLException e) {
			context.getRequest().setAttribute("EsitoUpdate", "KO");
			e.printStackTrace();
			context.getRequest().setAttribute("orgId", idStabilimento);
			return executeCommandViewControlloDocumentale(context);
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("EsitoUpdate", "OK");
		context.getRequest().setAttribute("orgId", idStabilimento);
		return "UpdateControlloDocumentaleOK";
	}

	/**
	 * Insert: Inserts a new Account into the database.
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandInsert(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = (Organization) context.getFormBean();
		Audit audit = new Audit();
		audit.setLivelloRischioFinale(-1);
		newOrg.setDomicilioDigitale(context.getParameter("domicilio_digitale"));
		newOrg.setTypeList(context.getRequest().getParameterValues(
				"selectedList"));
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setDate1(context.getParameter("date1"));
		newOrg.setDate2(context.getParameter("date2"));
		newOrg.setNameMiddle(context.getParameter("namemiddle"));
		newOrg.setOwner(getUserId(context));

		newOrg.setCodiceFiscaleRappresentante(context
				.getParameter("codiceFiscaleRappresentante"));
		newOrg
		.setNomeRappresentante(context
				.getParameter("nomeRappresentante"));
		newOrg.setCognomeRappresentante(context
				.getParameter("cognomeRappresentante"));
		newOrg.setLuogoNascitaRappresentante(context
				.getParameter("luogoNascitaRappresentante"));
		newOrg.setCity_legale_rapp(context.getParameter("city_legale_rapp"));
		newOrg.setProv_legale_rapp(context.getParameter("prov_legale_rapp"));
		newOrg.setAddress_legale_rapp(context
				.getParameter("address_legale_rapp"));
		newOrg.setEmailRappresentante(context
				.getParameter("emailRappresentante"));
		newOrg.setTelefonoRappresentante(context
				.getParameter("telefonoRappresentante"));
		newOrg.setDataNascitaRappresentante(context
				.getParameter("dataNascitaRappresentante"));
		newOrg.setFax(context.getParameter("fax"));

		int statoIstruttoriaPreliminare = 1;
		newOrg.setStatoIstruttoria(statoIstruttoriaPreliminare);
		try {
			db = this.getConnection(context);


			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);


			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			newOrg.setIsIndividual(false);
			newOrg.setRequestItems(context);
			newOrg.setStatoLab(Integer.parseInt(context
					.getParameter("statoLab")));
			newOrg.setSottoattivitaItem(context, db);
			OrganizationAddress so = null;
			OrganizationAddress sedeLegale = null;
			OrganizationAddress sedeMobile = null;
			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it
						.next();
				if (thisAddress.getType() == 5) {
					so = thisAddress;
				}
				if (thisAddress.getType() == 7) {
					sedeMobile = thisAddress;
				}
				if (thisAddress.getType() == 1) {
					sedeLegale = thisAddress;
				}

				// RICHIAMO METODO PER CONVERSIONE COORDINATE
				String[] coords = null;
				if (thisAddress.getLatitude() != 0
						&& thisAddress.getLongitude() != 0) {
					coords = this.convert2Wgs84UTM33N(Double
							.toString(thisAddress.getLatitude()), Double
							.toString(thisAddress.getLongitude()), db);
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}

			}
			newOrg.setName(context.getParameter("name"));
			newOrg.setPartitaIva(context.getParameter("piva"));
			newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
			newOrg.setDate2(context.getParameter("dateI"));
			newOrg.setCodiceFiscale(context.getParameter("cf_stabilimento"));
			newOrg.setTpologia(3);
			recordInserted = newOrg.insert_stabilimento(db,context);
			if (recordInserted) {
				insertedOrg = new Organization(db, newOrg.getOrgId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);
				addRecentItem(context, newOrg);


			} else {
				context.getRequest().setAttribute("Inserito", "no");
				context.getRequest().setAttribute("tipoIstruttoria",
						context.getRequest().getParameter("tipo_istruttoria"));
				return executeCommandAdd(context);
			}
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");
			} else {
				return ("InsertOK");
			}
		}
		return (executeCommandAdd(context));
	}

	public String executeCommandUpdateIstruttoria(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		try {
			db = this.getConnection(context);

			Organization oldOrg = new Organization(db, Integer.parseInt(context
					.getRequest().getParameter("orgId")));

			ControlloDocumentale cd_old = new ControlloDocumentale();
			cd_old.buildControlloDocumentale(db, Integer.parseInt(context
					.getRequest().getParameter("orgId")));

			Organization newOrg = new Organization(db, Integer.parseInt(context
					.getRequest().getParameter("orgId")));

			newOrg.setSottoattivitaList(SottoAttivita.loadByStabilimento(newOrg
					.getOrgId(), db));


			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList OrgCategoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					OrgCategoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);


			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			newOrg.setDomicilioDigitale(context
					.getParameter("domicilio_digitale"));
			// newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
			newOrg.setSottoattivitaItem(context, db);

			PreparedStatement pst = db
					.prepareStatement("update organization set stato_istruttoria = 12 where org_id = ?;delete from stabilimenti_sottoattivita where id_stabilimento = ?");
			pst.setInt(1, newOrg.getOrgId());
			pst.setInt(2, newOrg.getOrgId());
			pst.execute();

			//Log rispetto alle modifiche in Organization [rispetto al campo stato_istruttoria]
			ArrayList<CampoModificato> campi_modificati = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati = newOrg.checkModifiche(db,oldOrg,"StatoIstruttoria");

			// Registro le modifiche
			StoricoModifica storicoModifica = new StoricoModifica();
			storicoModifica.setIdStabilimento(newOrg.getId());
			storicoModifica.setIdUtente(getUserId(context));
			storicoModifica.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica.setNrCampiModificati(campi_modificati.size());
			storicoModifica.setListaCampiModificati(campi_modificati);
			storicoModifica.setMotivazioneModifica("");
			if(campi_modificati.size() > 0) {
				storicoModifica.insert(db, "Organization",context);
			}

			Iterator saiterator = newOrg.getSottoattivitaList().iterator();
			while (saiterator.hasNext()) {
				SottoAttivita thisSa = (SottoAttivita) saiterator.next();
				thisSa.setId_stabilimento(newOrg.getOrgId());
				// thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
				thisSa.store(db,context);
			}

			PreparedStatement pst2 = db
					.prepareStatement("select * from quesiti_controllo_documentale_stabilimenti where id_stabilimento= ?");
			pst2.setInt(1, newOrg.getOrgId());
			ResultSet rs2 = pst2.executeQuery();

			if (!rs2.next()) {


				
			    int id  = DatabaseUtils.getNextSeqInt(db,context,"quesiti_controllo_documentale_stabilimenti","id");


				PreparedStatement pst_controllo_ins = db
						.prepareStatement("insert into quesiti_controllo_documentale_stabilimenti (id,id_stabilimento) values  (?,?)");
				pst_controllo_ins.setInt(1, id);
				pst_controllo_ins.setInt(2, newOrg.getOrgId());

				pst_controllo_ins.execute();

				PreparedStatement pst_controllo_doc = db
						.prepareStatement("insert into quesiti_risposte_controllo_documentale (id_quesito,id_quesiti_controllo_documentale_stabilimenti,risposta_asl,risposta_stap)  ("
								+ "select id,?,"
								+ "case when competenza_asl = true then false "
								+ "else "
								+ "true "
								+ "end as risp"
								+ ""
								+ ",false from quesiti_controllo_documentale where enabled = true )");
				pst_controllo_doc.setInt(1, id);
				pst_controllo_doc.execute();
			} else {
				PreparedStatement pst_controllo_ins = db
						.prepareStatement("update  quesiti_controllo_documentale_stabilimenti set stato_asl = 0 , stato_stap = 0 where id_stabilimento = ?");

				pst_controllo_ins.setInt(1, newOrg.getOrgId());

				pst_controllo_ins.execute();

				//Log rispetto alle modifiche in ControlloDocumentale
				ControlloDocumentale cd = new ControlloDocumentale();
				cd.buildControlloDocumentale(db, newOrg.getOrgId());
				ArrayList<CampoModificato> campi_modificati_cd = new ArrayList<CampoModificato>();

				// Recupero i campi modificati per poterli salvare nel db per
				// tenere traccia delle modifiche
				campi_modificati_cd = cd.checkModifiche(db,cd_old);

				// Registro le modifiche
				StoricoModifica storicoModifica_cd = new StoricoModifica();
				storicoModifica_cd.setIdStabilimento(newOrg.getId());
				storicoModifica_cd.setIdUtente(getUserId(context));
				storicoModifica_cd.setDataModifica(new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime()));
				storicoModifica_cd.setNrCampiModificati(campi_modificati_cd.size());
				storicoModifica_cd.setListaCampiModificati(campi_modificati_cd);
				storicoModifica_cd.setMotivazioneModifica("");
				if(campi_modificati_cd.size() > 0 ) {
					storicoModifica_cd.insert(db, "ControlloDocumentale",context);
				}
			}

			insertedOrg = new Organization(db, newOrg.getOrgId());
			context.getRequest().setAttribute("OrgDetails", insertedOrg);
			addRecentItem(context, newOrg);

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		String target = context.getRequest().getParameter("target");
		if (context.getRequest().getParameter("popup") != null) {
			return ("ClosePopupOK");
		}
		if (target != null && "add_contact".equals(target)) {
			return ("InsertAndAddContactOK");
		} else {
			return ("InsertOK");
		}

	}




	/**
	 * Update: Updates the Organization table to reflect user-entered
	 * changes/modifications to the currently selected Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws ParseException
	 * @throws SQLException
	 */
	public String executeCommandUpdate(ActionContext context)
			throws SQLException, ParseException {

		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getFormBean();
		Organization oldOrg = null;

		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);

			oldOrg = new Organization(db, Integer.parseInt(context
					.getParameter("orgId")));

			oldOrg.setSottoattivitaList(SottoAttivita
					.loadByStabilimento(oldOrg.getId(), db));
			//Mi conservo le vecchie sottoattivita
			ArrayList<SottoAttivita> lista_sa_old = oldOrg.getSottoattivitaList();

			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));
			newOrg.setModifiedBy(getUserId(context));
			newOrg.setDate1(context.getParameter("date1"));
			newOrg.setDate2(context.getParameter("date2"));
			newOrg.setNameMiddle(context.getParameter("namemiddle"));
			newOrg.setOwner(getUserId(context));
			newOrg.setDomicilioDigitale(context
					.getParameter("domicilio_digitale"));
			newOrg.setCodiceFiscaleRappresentante(context
					.getParameter("codiceFiscaleRappresentante"));
			newOrg.setNomeRappresentante(context
					.getParameter("nomeRappresentante"));
			newOrg.setCognomeRappresentante(context
					.getParameter("cognomeRappresentante"));
			newOrg.setLuogoNascitaRappresentante(context
					.getParameter("luogoNascitaRappresentante"));
			newOrg
			.setCity_legale_rapp(context
					.getParameter("city_legale_rapp"));
			newOrg
			.setProv_legale_rapp(context
					.getParameter("prov_legale_rapp"));
			newOrg.setAddress_legale_rapp(context
					.getParameter("address_legale_rapp"));
			newOrg.setEmailRappresentante(context
					.getParameter("emailRappresentante"));
			newOrg.setTelefonoRappresentante(context
					.getParameter("telefonoRappresentante"));
			newOrg.setDataNascitaRappresentante(context
					.getParameter("dataNascitaRappresentante"));
			newOrg.setFax(context.getParameter("fax"));
			newOrg.setIsIndividual(false);
			newOrg.setRequestItems(context);
			if(context.getParameter("statoLab")!=null)
				newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
			try {
				newOrg.setSottoattivitaItem(context, db);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			OrganizationAddress so = null;
			OrganizationAddress sedeLegale = null;
			OrganizationAddress sedeMobile = null;
			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it
						.next();
				if (thisAddress.getType() == 5) {
					so = thisAddress;
				}
				if (thisAddress.getType() == 7) {
					sedeMobile = thisAddress;
				}
				if (thisAddress.getType() == 1) {
					sedeLegale = thisAddress;
				}

				// RICHIAMO METODO PER CONVERSIONE COORDINATE
				String[] coords = null;
				if (thisAddress.getLatitude() != 0
						&& thisAddress.getLongitude() != 0) {
					try {
						coords = this.convert2Wgs84UTM33N(Double
								.toString(thisAddress.getLatitude()), Double
								.toString(thisAddress.getLongitude()), db);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}

			}
			newOrg.setName(context.getParameter("name"));
			newOrg.setPartitaIva(context.getParameter("piva"));

			if(context.getParameter("siteId")!=null)
				newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
			newOrg.setDate2(context.getParameter("dateI"));
			newOrg.setCodiceFiscale(context.getParameter("cf_stabilimento"));
			newOrg.setTpologia(3);

			String tipoRichiesta = context.getParameter("tipoModifica");


			newOrg.setOrgId(Integer.parseInt(context.getParameter("orgId")));
			// int tipo_autorizzazzione_definitiva = 1 ;

			newOrg.setData_assegnazione_approval_number(context.getParameter("data_assegnazione_approval_number"));
			newOrg.setDataApprovalNumber(db);
			if (context.getParameter("idImpianto") != null) {
				int id_nuovo_stato = Integer.parseInt(context
						.getParameter("nuovoStato"));



				newOrg.cambiaStatoImpianto(id_nuovo_stato, Integer
						.parseInt(context.getParameter("idImpianto")), db);

				boolean impiantirevocati = true;
				boolean impiantisospesi = false;
				boolean impiantiautorizzati = false;
				oldOrg.setSottoattivitaItem(context, db);
				oldOrg.setSottoattivitaList(SottoAttivita
						.loadByStabilimento(oldOrg.getId(), db));

				ArrayList<SottoAttivita> lista_sa =oldOrg.getSottoattivitaList();// SottoAttivita.loadByStabilimento(oldOrg.getId(), db);
				for (SottoAttivita sa : lista_sa) {
					if (sa.getStato_attivita() != 1) {
						impiantirevocati = false;
					}

					if (sa.getStato_attivita() == 0) {
						impiantiautorizzati = true;
					}
					if (sa.getStato_attivita() == 2) {
						impiantisospesi = true;
					}

					//sa.update(db);
				}
				UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
				if(user.getSiteId()==-1)
				{
					if (impiantirevocati == true) {
						newOrg.revocaStabilimento(db);

						newOrg.completaPratica(db);
					} else if (impiantiautorizzati == true) {
						newOrg.updateStatoLab(0, db);
						newOrg.completaPratica(db);

					} else if (impiantisospesi == true) {
						newOrg.updateStatoLab(2, db);
						newOrg.completaPratica(db);

					}}

			}
			else
				if(oldOrg.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_ESISTENTE  || 
				//	oldOrg.getStatoIstruttoria()==StatiStabilimenti.COMPLETATO  
				oldOrg.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA   ||  
				oldOrg.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO  )
				{

					int id_nuovo_stato = Integer.parseInt(context
							.getParameter("nuovoStato"));



					ArrayList<SottoAttivita> lista_sa = newOrg.getSottoattivitaList();
					for (SottoAttivita sa : lista_sa) {

						sa.update(db);

					}

					if (id_nuovo_stato == StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA || 
							id_nuovo_stato == StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO || 
							id_nuovo_stato == StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE  )

					{
						newOrg.setStatoIstruttoria(id_nuovo_stato);
						newOrg
						.setOrgId(Integer.parseInt(context
								.getParameter("orgId")));


						newOrg.cambiaStatoIstruttoria(id_nuovo_stato, db);

						if (id_nuovo_stato == 5) {
							lista_sa = SottoAttivita
									.loadByStabilimento(oldOrg.getId(), db);
							for (SottoAttivita sa : lista_sa) {
								// sa.setTipo_autorizzazione(tipo_autorizzazzione_definitiva);

								sa.update(db);
							}
						}

						if (id_nuovo_stato == 4) {
							oldOrg.setSottoattivitaItem(context, db);
							lista_sa = oldOrg
									.getSottoattivitaList();
							for (SottoAttivita sa : lista_sa) {

								sa.update(db);
							}
						}
					}
				}
				else

					if (oldOrg.getStatoIstruttoria() == StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE) {

						int id_nuovo_stato = Integer.parseInt(context
								.getParameter("nuovoStato"));
						if(id_nuovo_stato == StatiStabilimenti.RICONOSCIUTO_CONDIZIONATO && oldOrg.getStatoLab()!=0)
						{

							newOrg.updateStatoLab(5, db);
						}
						newOrg.setNumAut(context.getParameter("numAut"));
						newOrg.setStatoIstruttoria(id_nuovo_stato);
						newOrg
						.setOrgId(Integer.parseInt(context
								.getParameter("orgId")));

						newOrg.attribuisciApprovalNumbrer(db);
						newOrg.setSottoattivitaItem(context, db);
						// ArrayList<SottoAttivita> lista_sa =
						// SottoAttivita.loadByStabilimento(oldOrg.getId(), db);
						for (SottoAttivita sa : newOrg.getSottoattivitaList()) {


							sa.updateAutorizzazzione(db);
						}		

					}

					else
						if (oldOrg.getStatoIstruttoria() == StatiStabilimenti.COMPLETATO) {
							boolean impiantirevocati = true;
							boolean impiantisospesi = false;
							boolean impiantiautorizzati = false;



							oldOrg.setSottoattivitaItem(context, db);
							if (oldOrg.getSottoattivitaList().isEmpty()) {
								newOrg.setSottoattivitaList(SottoAttivita
										.loadByStabilimento(oldOrg.getId(), db));
							}

							ArrayList<SottoAttivita> lista_sa = newOrg
									.getSottoattivitaList();



							for (SottoAttivita sa : lista_sa) {
								if (sa.getStato_attivita() != 1) {
									impiantirevocati = false;
								}

								if (sa.getStato_attivita() == 0) {
									impiantiautorizzati = true;
								}
								if (sa.getStato_attivita() == 2) {
									impiantisospesi = true;
								}

								sa.update(db);
							}
							if (impiantirevocati == true){

								newOrg.revocaStabilimento(db);
							}
							else if (impiantiautorizzati == true){

								newOrg.updateStatoLab(0, db);
							}
							else if (impiantisospesi == true) {


								newOrg.updateStatoLab(2, db);
							}

						}

			if (oldOrg.getStatoIstruttoria() == StatiStabilimenti.ISTRUTTORIA_PRELIMINARE
					|| newOrg.getStatoIstruttoria() == StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA) {


				//Lancio l'update classico su organization
				newOrg.update(db,context);
			}

			//Log rispetto alle modifiche in Organization
			ArrayList<CampoModificato> campi_modificati = new ArrayList<CampoModificato>();

			// Recupero i campi modificati per poterli salvare nel db per
			// tenere traccia delle modifiche
			campi_modificati = newOrg.checkModifiche(db, oldOrg);

			// Registro le modifiche
			StoricoModifica storicoModifica = new StoricoModifica();
			storicoModifica.setIdStabilimento(newOrg.getId());
			storicoModifica.setIdUtente(getUserId(context));
			storicoModifica.setDataModifica(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			storicoModifica.setNrCampiModificati(campi_modificati.size());
			storicoModifica.setListaCampiModificati(campi_modificati);
			storicoModifica.setMotivazioneModifica("");
			if(campi_modificati.size() > 0){
				storicoModifica.insert(db, "Organization",context);
			}

			ArrayList<SottoAttivita> lista_sa = newOrg.getSottoattivitaList();

			for (SottoAttivita sa : lista_sa) {
				//R.M per la gestione dello storico modifiche
				for (SottoAttivita sa_old : lista_sa_old) {

					//Log rispetto alle modifiche in Organization
					ArrayList<CampoModificato> campi_modificati_sa = new ArrayList<CampoModificato>();

					// Recupero i campi modificati per poterli salvare nel db per
					// tenere traccia delle modifiche
					campi_modificati_sa = sa.checkModifiche(db, sa_old);

					// Registro le modifiche
					StoricoModifica storicoModifica_sa = new StoricoModifica();
					storicoModifica_sa.setIdStabilimento(newOrg.getId());
					storicoModifica_sa.setIdUtente(getUserId(context));
					storicoModifica_sa.setDataModifica(new java.sql.Timestamp(Calendar
							.getInstance().getTime().getTime()));
					storicoModifica_sa.setNrCampiModificati(campi_modificati_sa.size());
					storicoModifica_sa.setListaCampiModificati(campi_modificati_sa);
					storicoModifica_sa.setMotivazioneModifica("");
					if(campi_modificati_sa.size() > 0){
						storicoModifica_sa.insert(db,"SottoAttivita",context);
					}

				}
				sa.update(db);
			}
			
			newOrg.setStatoIstruttoria(Integer.parseInt(context.getParameter("statoIstruttoria")));
			newOrg.setNumAut(context.getParameter("numAut"));
			newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
			newOrg.attribuisciApprovalNumbrer(db);


		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("orgId", newOrg.getOrgId());
		return executeCommandDetails(context);
	}

	/**
	 * Update: Updates the Organization table to reflect user-entered
	 * changes/modifications to the currently selected Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateCatRischio(ActionContext context) {
		if (!(hasPermission(context, "stabilimenti-tipochecklist-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		String org_id = context.getRequest().getParameter("orgId");
		String account_size = context.getRequest().getParameter("accountSize");
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			// set the name to namelastfirstmiddle if individual

			Organization newOrg = new Organization(db, Integer.parseInt(org_id));
			newOrg.setAccountSize(account_size);
			newOrg.setTypeList(context.getRequest().getParameterValues(
					"selectedList"));
			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);
			if (isValid) { 
				resultCount = newOrg.update(db,context);
			} 

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");

		return ("UpdateCatRischioOK");
	}

	/**
	 * Delete: Deletes an Account from the Organization table
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDelete(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-delete")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Exception errorMessage = null;
		boolean recordDeleted = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(db, Integer.parseInt(context
					.getRequest().getParameter("orgId")));
			//check permission to record
			
			
			
			if(thisOrganization.verificaEsistenzaCu(db)>0 || thisOrganization.verificaEsistenzaMacellazioni(db)>0)
			{
				context.getRequest().setAttribute("eliminazioneKo", "La scheda non puo essere cancellata per esistenza di controlli ufficiali e/o sedute di macellazione");
				context.getRequest().setAttribute("orgId", thisOrganization.getOrgId());
				return executeCommandDetails(context);
			
			}
			
					// NOTE: these may have different options later
			thisOrganization.setModifiedBy(getUserId(context));
			thisOrganization.setNotes(context.getParameter("note"));
					
					recordDeleted = thisOrganization.delete(db, context,
							getDbNamePath(context));
			
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (errorMessage == null) {
			if (recordDeleted) {
				
				context.getRequest().setAttribute("refreshUrl",
						"Stabilimenti.do?command=Search");
				if ("disable".equals(context.getRequest()
						.getParameter("action"))
						&& "list".equals(context.getRequest().getParameter(
								"return"))) {
					return executeCommandSearch(context);
				}
				return "DeleteOK";
			} else {
				processErrors(context, thisOrganization.getErrors());
				return (executeCommandSearch(context));
			}
		} else {
			context
			.getRequest()
			.setAttribute(
					"actionError",
					systemStatus
					.getLabel("object.validation.actionError.accountDeletion"));
			context.getRequest().setAttribute("refreshUrl",
					"Stabilimenti.do?command=Search");
			return ("DeleteError");
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandTrash(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Organization thisOrganization = null;
		Organization oldOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}

			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			oldOrganization = new Organization(db, Integer.parseInt(orgId));

			if(thisOrganization.verificaEsistenzaCu(db)>0 || thisOrganization.verificaEsistenzaMacellazioni(db)>0)
			{
				context.getRequest().setAttribute("eliminazioneKo", "La scheda non puo essere cancellata per esistenza di controlli ufficiali e/o sedute di macellazione");
				context.getRequest().setAttribute("orgId", thisOrganization.getOrgId());
				context.getRequest().setAttribute("refreshUrl",
						"Stabilimenti.do?command=Details&orgId="+thisOrganization.getOrgId()+"&eliminazioneKo=La scheda non puo essere cancellata per esistenza di controlli ufficiali e/o sedute di macellazione");
				
				return "DeleteOK";
			
			}
			
					// NOTE: these may have different options later
			thisOrganization.setModifiedBy(getUserId(context));
			thisOrganization.setNotes(context.getParameter("note"));
					
					
			
			
			// NOTE: these may have different options later
			recordUpdated = thisOrganization.updateStatus(db, context, true,
					this.getUserId(context));
			this.invalidateUserData(context, this.getUserId(context));
			this.invalidateUserData(context, thisOrganization.getOwner());
			
		} catch (Exception e) {
			context.getRequest().setAttribute("refreshUrl",
					"Stabilimenti.do?command=Search");
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute("refreshUrl",
					"Stabilimenti.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return "DeleteOK";
		} else {
			return (executeCommandSearch(context));
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandRestore(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			// NOTE: these may have different options later
			recordUpdated = thisOrganization.updateStatus(db, context, false,
					this.getUserId(context));
			this.invalidateUserData(context, this.getUserId(context));
			this.invalidateUserData(context, thisOrganization.getOwner());
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute("refreshUrl",
					"Stabilimenti.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return this.executeCommandDetails(context);
		} else {
			return (executeCommandSearch(context));
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandEnable(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-edit")) {
			return ("PermissionError");
		}
		boolean recordEnabled = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(db, Integer.parseInt(context
					.getRequest().getParameter("orgId")));
			recordEnabled = thisOrganization.enable(db);
			if (!recordEnabled) {
				this.validateObject(context, db, thisOrganization);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		return (executeCommandSearch(context));
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrg = null;
		HtmlDialog htmlDialog = new HtmlDialog();
		String id = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		if (context.getRequest().getParameter("id") != null) {
			id = context.getRequest().getParameter("id");
		}
		try {
			db = this.getConnection(context);
			thisOrg = new Organization(db, Integer.parseInt(id));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisOrg.getId())) {
				return ("PermissionError");
			}
			htmlDialog.addMessage(systemStatus
					.getLabel("confirmdelete.caution")
					+ "\n" );
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			/*
			 * htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header")
			 * );
			 */
			
			htmlDialog.setDeleteUrl("Stabilimenti.do?command=Trash&action=delete&orgId="
							+ thisOrg.getOrgId());
			htmlDialog
			.addButton(
					systemStatus.getLabel("button.delete"),
					"javascript:window.location.href='Stabilimenti.do?command=Trash&action=delete&orgId="
							+ thisOrg.getOrgId()
							+ "&forceDelete=true"
							+ "'");
			
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"),
					"javascript:parent.window.close()");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getSession().setAttribute("Dialog", htmlDialog);
		return ("ConfirmDeleteOK");
	}

	/**
	 * Modify: Displays the form used for modifying the information of the
	 * currently selected Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			ArrayList elencoAttivita = null;
			String temporgId = context.getRequest().getParameter("orgId");

			int tempid = Integer.parseInt(temporgId);

			db = this.getConnection(context);

			
		
			
			
			newOrg = new Organization(db, tempid);
			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());
			elencoAttivita = SottoAttivita.loadByStabilimento(tempid, db);
			context.getRequest().setAttribute("elencoSottoAttivita",
					elencoAttivita);

			// context.getRequest().setAttribute("tipoModifica",Integer.parseInt(context.getRequest().getParameter("tipoModifica")));
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.stabilimenti.base.OrganizationAddress thisAddress = (org.aspcfs.modules.stabilimenti.base.OrganizationAddress) it_coords
						.next();
				if (thisAddress.getLatitude() != 0
						&& thisAddress.getLongitude() != 0) {

					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double
							.toString(thisAddress.getLatitude()), Double
							.toString(thisAddress.getLongitude()), db);

					thisAddress.setLatitude(spatial_coords[0]);
					thisAddress.setLongitude(spatial_coords[1]);

				}
			}
			// check whether or not the owner is an active User
			newOrg.setOwner(this.getUserId(context));
			int nCategoriaMax = maxCategoriaRischioMercatoIttico(context,
					tempid,db);
			if (nCategoriaMax != newOrg.getCategoriaPrecedente())
				aggiornaCategoriaRischioGlobaleMercatoIttico(context, tempid,db);

			LookupList statiStabilimenti = new LookupList(db,
					"lookup_stati_stabilimenti");
			context.getRequest().setAttribute("statiStabilimenti",
					statiStabilimenti);
			context.getRequest().setAttribute("Condizionato",
					context.getParameter("condizionato"));
			context.getRequest().setAttribute("Revoca",
					context.getParameter("revoca"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.removeElementByLevel(3);
			statoLab.removeElementByLevel(4);
			statoLab.removeElementByLevel(5);
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList statoLabImpianti = new LookupList(db, "lookup_stato_lab_impianti");

			statoLabImpianti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLabImpianti", statoLabImpianti);

			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList lookup_prodotti = new LookupList(db,
					"lookup_prodotti_stabilimenti");
			lookup_prodotti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest()
			.setAttribute("LookupProdotti", lookup_prodotti);

			LookupList lookup_classificazione = new LookupList(db,
					"lookup_classificazione_stabilimenti");
			lookup_classificazione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("LookupClassificazione",
					lookup_classificazione);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			LookupList categoriaList = new LookupList(db, "lookup_categoria");
			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("categoriaList", categoriaList);

			LookupList imballataList = new LookupList(db,
					"lookup_sottoattivita_imballata");
			imballataList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("imballataList", imballataList);

			LookupList tipoAutorizzazioneList = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoAutorizzazioneList",
					tipoAutorizzazioneList);

			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, newOrg.getId());
			context.getRequest().setAttribute(
					"stabilimentiAssociateMercatoIttico",
					stabilimentiAssociateMercatoIttico);
			addRecentItem(context, newOrg);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getParameter("mode") == null)
			return "ModifyOK";
		else
			return "ModifyPraticaOK";

	}

	// inserito carmela
	public String executeCommandModificaCatRischio(ActionContext context) {
		if ((!hasPermission(context, "stabilimenti-tipochecklist-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer
					.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			// check whether or not the owner is an active User
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			// If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("ModificaCatRischioOK");
		} else {
			// If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "ModificaCatRischio");
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandFolderList(ActionContext context) {
		if (!(hasPermission(context, "stabilimenti-stabilimenti-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			// Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setLinkItemId(thisOrganization.getId());
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.setBuildTotalNumOfRecords(true);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Custom Fields Details");
		return this.getReturn(context, "FolderList");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 */
	private void resetPagedListInfo(ActionContext context) {
		
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @param organization
	 *            Description of the Parameter
	 * @param db
	 *            Description of the Parameter
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	

	public String executeCommandEliminaOperatoreMercatoIttico(
			ActionContext context) {
		Connection db = null;
		int org_id = Integer.parseInt(context.getRequest()
				.getParameter("orgId"));

		try {
			db = getConnection(context);

			int id = Integer.parseInt(context.getRequest().getParameter("id"));
			OperatoriAssociatiMercatoIttico.delete(db, id);

			aggiornaCategoriaRischioGlobaleMercatoIttico(context, org_id,db);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandDetails(context);

	}

	public int aggiornaCategoriaRischioGlobaleMercatoIttico(
			ActionContext context, int id,Connection db) throws SQLException {
		int ret = -1;
		int cat_attuale = -564;

			ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriImpreseById(db, id);
			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, id);

			for (OperatoriAssociatiMercatoIttico impresa : (ArrayList<OperatoriAssociatiMercatoIttico>) impreseAssociateMercatoIttico) {
				if (impresa.getImpresa().getCategoriaRischio() > 0)
					cat_attuale = impresa.getImpresa().getCategoriaRischio();
				else
					cat_attuale = 3;

				if (cat_attuale > ret)
					ret = cat_attuale;
			}

			for (OperatoriAssociatiMercatoIttico stabilimento : (ArrayList<OperatoriAssociatiMercatoIttico>) stabilimentiAssociateMercatoIttico) {
				if (stabilimento.getStabilimento().getCategoriaRischio() > 0)
					cat_attuale = stabilimento.getStabilimento()
					.getCategoriaRischio();
				else
					cat_attuale = 3;

				if (cat_attuale > ret)
					ret = cat_attuale;
			}

			Organization newOrg = new Organization(db, id);

			if (newOrg.getCategoriaRischio() > ret) {
				ret = newOrg.getCategoriaRischio();
			}

			newOrg.setCategoriaPrecedente(ret);

			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));

			newOrg.updateCategoriaPrecedente(db, ret, id);

		

		return ret;

	}

	public String executeCommandListaOperatoriMercatoIttico(
			ActionContext context) {
		String ret = "ListaOperatoriMercatoItticoOK";
		
		
		int ORG_ID=0;
		
		
		
		
		if (context.getRequest().getParameter("orgId")==null){
			ORG_ID=Integer.parseInt((String) context.getRequest().getAttribute(
					"orgId"));
			
		}else{
			ORG_ID=Integer.parseInt(context.getRequest().getParameter(
					"orgId"));
		}
		
		final int org_id = ORG_ID;
		
		

		if (!hasPermission(context, "stabilimenti-operatori-ittici-view")) {
			return "PermissionError";
		}

		Connection db = null;
		RowSetDynaClass rsdc = null;

		try {
			db = getConnection(context);

			// PreparedStatement stat = db.prepareStatement(
			// "SELECT DISTINCT description as asl," +
			// " tipologia, " +
			// "name, " +
			// "partita_iva," +
			// "codice_fiscale," +
			// "CASE WHEN tipologia=1 THEN 'IMPRESA' ELSE 'STABILIMENTO' END as tipo,"
			// +
			// "org_id," +
			// "'' as edit" +
			// " FROM organization, lookup_site_id "+
			// " WHERE lookup_site_id.code=site_id AND ( (tipologia=1  AND cessato=0) OR (tipologia=3) ) AND (trashed_date is null) "+
			// " AND org_id not in (select id_operatore as org_id from operatori_associati_mercato_ittico WHERE id_mercato_ittico="+
			// org_id +" ) " +
			// " ORDER BY asl,tipo,name" );

			PreparedStatement stat = db
					.prepareStatement("SELECT DISTINCT description as asl,"
							+ " tipologia, "
							+ "name, "
							+ "partita_iva,"
							+ "codice_fiscale,"
							+ "CASE WHEN tipologia=3 THEN 'IMPRESA' ELSE 'STABILIMENTO' END as tipo,"
							+ "org_id,"
							+ "'' as edit"
							+ " FROM organization, lookup_site_id "
							+ " WHERE lookup_site_id.code=site_id AND ( tipologia=3 AND direct_bill=true) AND (trashed_date is null) "
							+ " AND org_id not in (select id_operatore as org_id from operatori_associati_mercato_ittico where importato_in_anagrafica =false ) "
							+ "and site_id=(select site_id from organization where org_id ="+org_id+") "
							+ "ORDER BY asl,tipo,name");

			ResultSet rs = stat.executeQuery();
			rsdc = new RowSetDynaClass(rs);

			TableFacade tf = null;

			tf = TableFacadeFactory
					.createTableFacade("5", context.getRequest());
			tf.setItems(rsdc.getRows());
			tf.setColumnProperties("asl", "tipo", "name", "partita_iva",
					"codice_fiscale", "edit");

			tf.setStateAttr("restore");

			Limit limit = tf.getLimit();

			if (limit.isExported()) {
				tf.render();
				return "ListOK";
			} else {

				tf.getTable().getRow().getColumn("name").setTitle(
						"Ragione Sociale");
				tf.getTable().getRow().getColumn("partita_iva").setTitle(
						"Partita Iva");
				tf.getTable().getRow().getColumn("codice_fiscale").setTitle(
						"Codice Fiscale");
				tf.getTable().getRow().getColumn("tipo").setTitle(
						"Tipo Operatore");

				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn(
						"tipo");
				cg.getFilterRenderer().setFilterEditor(
						new DroplistFilterEditor());

				tf.getTable().getRow().getColumn("edit").setTitle("Azione");
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("edit");

				tf.getTable().getRow().getColumn("asl").setTitle("A.S.L.");
				HtmlColumn cg2 = (HtmlColumn) tf.getTable().getRow().getColumn(
						"asl");
				cg2.getFilterRenderer().setFilterEditor(
						new DroplistFilterEditor());

				cg.getCellRenderer().setCellEditor(new CellEditor() {
					public Object getValue(Object item, String property,
							int rowCount) {

						String idOperatore = (String) (new HtmlCellEditor())
								.getValue(item, "org_id", rowCount);

						String tipoOperatore = (String) (new HtmlCellEditor())
								.getValue(item, "tipologia", rowCount);

						return "<a href=\"Stabilimenti.do?command=AggiungiOperatoreMercatoIttico&orgId="
						+ org_id
						+ "&idOperatore="
						+ idOperatore
						+ "&tipoOperatore="
						+ tipoOperatore
						+ "\" onclick=\"return confirm('Sei sicuro di voler aggiungere l\'operatore selezionato?');\">Aggiungi</a>";
					}
				}

						);

			}

			String tabella = tf.render();
			context.getRequest().setAttribute("tabella", tabella);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(0, "Regione");
			context.getRequest().setAttribute("SiteList", siteList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return ret;
	}

	public String executeCommandAggiungiOperatoreMercatoIttico(
			ActionContext context) {
		Connection db = null;
		int org_id = Integer.parseInt(context.getRequest()
				.getParameter("orgId"));
		int idOperatore = Integer.parseInt(context.getRequest().getParameter(
				"idOperatore"));
		int tipoOperatore = Integer.parseInt(context.getRequest().getParameter(
				"tipoOperatore"));

		try {
			db = getConnection(context);

			OperatoriAssociatiMercatoIttico nuovoOperatore = new OperatoriAssociatiMercatoIttico();

			nuovoOperatore.setEntered_by(getUserId(context));
			nuovoOperatore.setIdMercatoIttico(org_id);
			nuovoOperatore.setIdOperatore(idOperatore);
			nuovoOperatore.setTipo(tipoOperatore);
			nuovoOperatore.setContenitoreMercatoIttico("organization");
			nuovoOperatore.store(db);

			Organization mercato_ittico = new Organization(db, org_id);
			Organization operatore_ittico = new Organization(db, idOperatore);
			operatore_ittico.setApprovalNumber(mercato_ittico
					.getApprovalNumber());
			operatore_ittico.update(db,context);

			aggiornaCategoriaRischioGlobaleMercatoIttico(context, org_id,db);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandDetails(context);

	}

	public int maxCategoriaRischioMercatoIttico(ActionContext context, int id,Connection db) throws SQLException {
		int maxCategoria = -1;

		
		
			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico.getOperatoriStabilimentiById(db, id);

			

			for (OperatoriAssociatiMercatoIttico stabilimento : (ArrayList<OperatoriAssociatiMercatoIttico>) stabilimentiAssociateMercatoIttico) {
				if (stabilimento.getStabilimento().getCategoriaRischio() > -1) {
					if (stabilimento.getStabilimento().getCategoriaRischio() > maxCategoria)
						maxCategoria = stabilimento.getStabilimento()
						.getCategoriaRischio();
				} else {
					if (maxCategoria < 3)
						maxCategoria = stabilimento.getStabilimento()
						.getCategoriaRischio();
				}
			}

		

		return maxCategoria;

	}

	public String executeCommandViewRappresentantiLegali(ActionContext context) {
		if (!hasPermission(context,
				"stabilimenti-stabilimenti-rappresentantelegale-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		org.aspcfs.modules.rappresentantestabilimento.base.TicketList ticList = new org.aspcfs.modules.rappresentantestabilimento.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo
		.setLink("Stabilimenti.do?command=ViewRappresentantiLegali&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
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
		return getReturn(context, "ViewRappresentantiLegali");
	}

	public String executeCommandDetailsOperatoriMercatiIttici(
			ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		ArrayList elencoAttivita = null;
		try {

			executeCommandListaOperatoriMercatoIttico(context);

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);

			db = this.getConnection(context);
			/*if (!isRecordAccessPermitted(context, db, Integer
					.parseInt(temporgId))) {
				return ("PermissionError");
			}*/
			newOrg = new Organization(db, tempid,"organization");
			// check whether or not the owner is an active User
			newOrg.setOwner(this.getUserId(context));

			// Aggiornamento categoria mercato ittico in caso di discrepanza
			if (hasPermission(context, "stabilimenti-operatori-ittici-view")) {
				int nCategoriaMax = maxCategoriaRischioMercatoIttico(context,
						tempid,db);

				if (nCategoriaMax != newOrg.getCategoriaPrecedente())
					aggiornaCategoriaRischioGlobaleMercatoIttico(context,
							tempid,db);
			}

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			String codice1 = null;
			String codice2 = null;
			String codice3 = null;
			String codice4 = null;
			String codice5 = null;
			String codice6 = null;
			String codice7 = null;
			String codice8 = null;
			String codice9 = null;
			String codice10 = null;

			if (newOrg.getCodice1() != null) {
				codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(),
						db);
				context.getRequest().setAttribute("codice1", codice1);
			} else if (newOrg.getCodice2() != null) {
				codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(),
						db);
				context.getRequest().setAttribute("codice2", codice2);
			} else if (newOrg.getCodice3() != null) {
				codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(),
						db);
				context.getRequest().setAttribute("codice3", codice3);
			} else if (newOrg.getCodice4() != null) {
				codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(),
						db);
				context.getRequest().setAttribute("codice4", codice4);
			} else if (newOrg.getCodice5() != null) {
				codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(),
						db);
				context.getRequest().setAttribute("codice5", codice5);
			} else if (newOrg.getCodice6() != null) {
				codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(),
						db);
				context.getRequest().setAttribute("codice6", codice6);
			} else if (newOrg.getCodice7() != null) {
				codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(),
						db);
				context.getRequest().setAttribute("codice7", codice7);
			} else if (newOrg.getCodice8() != null) {
				codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(),
						db);
				context.getRequest().setAttribute("codice8", codice8);
			} else if (newOrg.getCodice9() != null) {
				codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(),
						db);
				context.getRequest().setAttribute("codice9", codice9);
			} else if (newOrg.getCodice10() != null) {
				codice10 = IstatList.getSelectedValueShort(
						newOrg.getCodice10(), db);
				context.getRequest().setAttribute("codice10", codice10);
			}


			LookupList categoriaList = new LookupList(db, "lookup_categoria");
			categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("categoriaList", categoriaList);

			LookupList imballataList = new LookupList(db,
					"lookup_sottoattivita_imballata");
			imballataList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("imballataList", imballataList);

			LookupList tipoAutorizzazioneList = new LookupList(db,
					"lookup_sottoattivita_tipoautorizzazione");
			tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("tipoAutorizzazioneList",
					tipoAutorizzazioneList);

			elencoAttivita = SottoAttivita.loadByStabilimento(tempid, db);

			/*
			 * org.aspcfs.modules.audit.base.AuditList audit = new
			 * org.aspcfs.modules.audit.base.AuditList(); int AuditOrgId =
			 * newOrg.getOrgId(); audit.setOrgId(AuditOrgId);
			 * 
			 * //audit.buildList(db);
			 * 
			 * if( (audit.size() - 1)>=0){
			 * 
			 * context.getRequest().setAttribute("Audit",audit.get(0) ); }
			 */
			context.getRequest().setAttribute("elencoSottoAttivita",
					elencoAttivita);

			ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriImpreseById(db, newOrg.getId());
			context.getRequest().setAttribute("impreseAssociateMercatoIttico",
					impreseAssociateMercatoIttico);

			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
					.getOperatoriStabilimentiById(db, newOrg.getId());
			context.getRequest().setAttribute(
					"stabilimentiAssociateMercatoIttico",
					stabilimentiAssociateMercatoIttico);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			// If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOperatoriMercatiItticiOK");
		} else {
			// If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "DetailsOperatoriMercatiIttici");
		}
	}

	public int getIdMercatoIttico(ActionContext context, int idOperatore,String contenitoreMercatoIttico) {
		int ret = -1;

		Connection db = null;

		try {
			db = getConnection(context);

			ret = OperatoriAssociatiMercatoIttico
					.getIdMercatoItticoDaOperatore(db, idOperatore,contenitoreMercatoIttico);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return -1;
		} finally {
			this.freeConnection(context, db);
		}

		return ret;

	}

	public String executeCommandListaStoricoModifiche(ActionContext context) {

		Connection db = null;

		try {

			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context,
					"modificheListInfo");
			searchListInfo
			.setLink("Stabilimenti.do?command=ListaStoricoModifiche");
			searchListInfo.setListView("all");

			db = this.getConnection(context);

			String orgId = context.getRequest().getParameter("orgId");
			if (orgId == null) {
				orgId = (String) context.getRequest().getAttribute(
						"orgId");
			}

			Integer tempid = null;

			if (orgId != null) {
				tempid = Integer.parseInt(orgId);
			}

			StoricoModificaList listaModifiche = new StoricoModificaList();
			listaModifiche.setIdStabilimento(tempid);
			listaModifiche.buildList(db);

			listaModifiche.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(listaModifiche, context);
			context.getRequest().setAttribute("listaModifiche", listaModifiche);

			Organization thisOrg = new Organization(db, tempid);
			context.getRequest().setAttribute("orgDetails", thisOrg);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ListaModificheOK");
	}

	public String executeCommandDetailsModifica(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);

		try {

			String idModifica = context.getRequest().getParameter("idModifica");
			if (idModifica == null) {
				idModifica = (String) context.getRequest().getAttribute(
						"idModifica");
			}

			Integer tempid = null;

			if (idModifica != null) {
				tempid = Integer.parseInt(idModifica);

				db = this.getConnection(context);

				StoricoModifica thisModifica = new StoricoModifica(db, tempid);
				context.getRequest().setAttribute("modifica", thisModifica);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "DetailsModifica");
	}

	/*
	private String[] converToWgs84UTM33NInverter(String latitudine,
			String longitudine, Connection db) throws SQLException {
		String lat = "";
		String lon = "";
		String[] coord = new String[2];
		String sql1 = "select         y \n" + "( \n" + "        transform \n"
		+ "        (  \n" + "                geomfromtext \n"
		+ "                (  \n" + "                        'POINT("
		+ longitudine
		+ " "
		+ latitudine
		+ ")', 32633 \n"
		+ "         \n"
		+ "                ), 4326 \n"
		+ "        ) \n"
		+ ") AS y, \n"
		+ "x \n"
		+ "( \n"
		+ "        transform \n"
		+ "        (  \n"
		+ "                geomfromtext \n"
		+ "                (  \n"
		+ "                        'POINT("
		+ longitudine
		+ " "
		+ latitudine
		+ ")', 32633 \n"
		+ "         \n"
		+ "                ), 4326 \n"
		+ "        ) \n"
		+ ") AS x \n";

		try {

			PreparedStatement stat1 = db.prepareStatement(sql1);
			ResultSet res1 = stat1.executeQuery();
			if (res1.next()) {
				lat = res1.getString("y");
				lon = res1.getString("x");
				coord[0] = lat;
				coord[1] = lon;

			}
			res1.close();
			stat1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return coord;

	}

	private String[] convert2Wgs84UTM33N(String latitude, String longitude,
			Connection conn) throws SQLException {

		String[] ret = null;
		String sql = "select 	X \n" + "( \n" + "	transform \n" + "	(  \n"
		+ "		geomfromtext \n" + "		(  \n" + "			'POINT( "
		+ longitude
		+ " "
		+ latitude
		+ " )', 4326 \n"
		+ "	 \n"
		+ "		), 32633 \n"
		+ "	) \n"
		+ ") AS x, \n"
		+ "Y \n"
		+ "( \n"
		+ "	transform \n"
		+ "	(  \n"
		+ "		geomfromtext \n"
		+ "		(  \n"
		+ "			'POINT( "
		+ longitude
		+ " "
		+ latitude
		+ " )', 4326 \n"
		+ "	 \n" + "		), 32633 \n" + "	) \n" + ") AS y \n";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			// stat.setString( 1, wgs84[0] );
			// stat.setString( 2, wgs84[1] );
			// stat.setString( 3, wgs84[0] );
			// stat.setString( 4, wgs84[1] );

			ResultSet res = stat.executeQuery();
			if (res.next()) {
				ret = new String[2];
				ret[0] = res.getString("x");
				ret[1] = res.getString("y");

			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	} */

	public String executeCommandPrepareModifyStabilimento(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-modifica-view")) {
			return ("PermissionError");
		}
		
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Organization();
			newOrg = newOrg.load(context.getRequest().getParameter("account_number"), db,"organization");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("OrgDetails", newOrg);
		return "PrepareModifyStabilimentoOK";

	}
	
	public String executeCommandModifyStabilimento2(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-modifica-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Organization();
			newOrg = newOrg.load(context.getRequest().getParameter("account_number"), db,"organization");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		
		org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = null;
		try {
			listaComuni = c.buildList(db, -1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.freeConnection(context, db);
		}
	      LookupList comuniList = new LookupList(listaComuni);
	      
	      comuniList.addItem(-1, "");
	      context.getRequest().setAttribute("ComuniList", comuniList);
		

		context.getRequest().setAttribute("OrgDetails", newOrg);
		return "ModifyStabilimento2OK";

	}
	
	public String executeCommandAggiornaInfoStabilimento(ActionContext context) {
		if (!hasPermission(context, "stabilimenti-stabilimenti-modifica-edit")) {
			return ("PermissionError");
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Organization();
			newOrg = newOrg.load(context.getRequest().getParameter("approval_number"), db,"organization");

			
			
			// AGGIUNTA PARAMETRI INFORMAZIONE PRIMARIA
			newOrg.setName(context.getRequest().getParameter("organization_name"));
			newOrg.setCodiceFiscale(context.getRequest().getParameter("codice_fiscale_impresa"));
			newOrg.setPartitaIva(context.getRequest().getParameter("partita_iva"));
			newOrg.setDomicilioDigitale(context.getRequest().getParameter("domicilio_digitale_impresa"));
			
			// AGGIUNTA PARAMETRI TITOLARE O LEGALE RAPPRESENTANTE
			newOrg.setCodiceFiscaleRappresentante(context.getRequest().getParameter("codice_fiscale_rappr"));
			newOrg.setNomeRappresentante(context.getRequest().getParameter("nome"));
			newOrg.setCognomeRappresentante(context.getRequest().getParameter("cognome"));
			// GESTIONE COMUNE DI NASCITA A CAUSA DEL CARATTERE "SPAZIO" FINALE
			String comune_nascita=null;
			if (context.getRequest().getParameter("comune_nascita")!=null && context.getRequest().getParameter("comune_nascita")!="" && context.getRequest().getParameter("comune_nascita")!=" "){
				comune_nascita=context.getRequest().getParameter("comune_nascita");
				comune_nascita=comune_nascita.substring(0, comune_nascita.length()-1);
			}
			newOrg.setLuogoNascitaRappresentante(comune_nascita);
			newOrg.setCity_legale_rapp(context.getRequest().getParameter("comune_di_residenza"));
			newOrg.setProv_legale_rapp(context.getRequest().getParameter("provincia"));
			newOrg.setAddress_legale_rapp(context.getRequest().getParameter("indirizzo"));
			newOrg.setEmailRappresentante(context.getRequest().getParameter("domicilio_digitale_rappr"));
			newOrg.setTelefonoRappresentante(context.getRequest().getParameter("telefono"));
			newOrg.setFax(context.getRequest().getParameter("fax"));

			OrganizationAddressList listAddress = new OrganizationAddressList();
			// Controllo se esiste almeno 1 elemento che identifichi un indirizzo legale
			if (context.getRequest().getParameter("indirizzo_legale")!=null || context.getRequest().getParameter("indirizzo_legale")!="" ||
			context.getRequest().getParameter("comune_legale")!=null || context.getRequest().getParameter("comune_legale")!="" ||
			context.getRequest().getParameter("provincia_legale")!=null || context.getRequest().getParameter("provincia_legale")!="" ||
			context.getRequest().getParameter("cap_legale")!=null || context.getRequest().getParameter("cap_legale")!=""){
				org.aspcfs.modules.stabilimenti.base.OrganizationAddress addressLegale=new org.aspcfs.modules.stabilimenti.base.OrganizationAddress();
				addressLegale.setType(1);
				addressLegale.setStreetAddressLine1(context.getRequest().getParameter("indirizzo_legale"));
				addressLegale.setCity(context.getRequest().getParameter("comune_legale"));
				addressLegale.setState(context.getRequest().getParameter("provincia_legale"));
				addressLegale.setZip(context.getRequest().getParameter("cap_legale"));
				addressLegale.setEnteredBy(user.getUserId());
				listAddress.add(addressLegale);
			}
			// Controllo se esiste almeno 1 elemento che identifichi un indirizzo operativa
			if (context.getRequest().getParameter("indirizzo_operativa")!=null ||
			context.getRequest().getParameter("comune_operativa")!=null || 
			context.getRequest().getParameter("provincia_operativa")!=null ||
			context.getRequest().getParameter("cap_operativa")!=null){
				org.aspcfs.modules.stabilimenti.base.OrganizationAddress addressOperativa=new org.aspcfs.modules.stabilimenti.base.OrganizationAddress();
				addressOperativa.setType(5);
				addressOperativa.setStreetAddressLine1(context.getRequest().getParameter("indirizzo_operativa"));
				addressOperativa.setCity(context.getRequest().getParameter("comune_operativa"));
				addressOperativa.setState(context.getRequest().getParameter("provincia_operativa"));
				addressOperativa.setZip(context.getRequest().getParameter("cap_operativa"));
				addressOperativa.setEnteredBy(user.getUserId());
				listAddress.add(addressOperativa);
			}
			
			
			newOrg.setAddressList(listAddress);
			

			newOrg.updateInfoStabilimento(db,context);
			
			
			
			
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		
		//context.getRequest().setAttribute("orgId",context.getRequest().getParameter("orgId"));
		
		context.getRequest().setAttribute("OrgDetails", newOrg);
		return (executeCommandDetails( context));
		//return "ModifyStabilimento2OK";
	}
}
