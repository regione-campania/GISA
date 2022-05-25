/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricercaunica.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
//import org.aspcfs.modules.accounts.base.Organization;
//import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.global_search.base.OrganizationListView;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.ricercaunica.base.RicercaArchiviatiList;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;

import com.darkhorseventures.framework.actions.ActionContext;
//import com.lowagie.text.Cell;

public final class RicercaArchiviati extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}

	public String executeCommandDashboard(ActionContext context) {

		return (executeCommandSearchForm(context));

	}

	public String executeCommandSearchForm(ActionContext context) {
		//	    if (!(hasPermission(context, "global-search-view"))) {
		//	      return ("PermissionError");
		//	    }
		//	    
		//Bypass search form for portal users

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		try {
			db = getConnection(context);
			
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);
			
			this.deletePagedListInfo(context, "searchListInfo");
			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			//verificare la lista comuni
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());

			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			String tipoOp = context.getParameter("tipoOperazione");
			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));

			if(tipoOp!=null)
			{
				switch (Integer.parseInt(tipoOp)) {
				case 1:
					/*Spostamento controlli*/
					if (context.getParameter("rifId")!=null)
					{
						int rifId = Integer.parseInt(context.getParameter("rifId"));
						String rifIdNome = context.getParameter("rifIdNome");
						int tipoRicerca = Integer.parseInt(context.getParameter("tipoRicerca"));
						
						RicercaList ricerca = new RicercaList();
						ricerca.setRiferimentoIdnomeCol(rifIdNome);
						ricerca.setRiferimentoId(rifId);
						ricerca.setTipoRicerca(tipoRicerca);
						ricerca.buildList(db);
						
						RicercaOpu anagraficaPartenza =  (RicercaOpu) ricerca.get(0);
						anagraficaPartenza.setTipoRicerca(tipoRicerca);
						context.getRequest().setAttribute("AnagraficaPartenza", anagraficaPartenza);
						
					}
					break;

				default:
					break;
				}
				
			}
			
			
			
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {	
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		if (context.getParameter("Popup")!=null)
		{
			context.getRequest().setAttribute("Popup", context.getParameter("Popup"));
			return "SearchPopupOK";
		}
		return ("SearchOK");

	}









	public String executeCommandSearch(ActionContext context) {
		//		if (!hasPermission(context, "ricercaunica-view")) {
		//			return ("PermissionError");
		//		}



		RicercaArchiviatiList organizationList = new RicercaArchiviatiList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");

		searchListInfo.setLink("RicercaArchiviati.do?command=Search");
			
		Connection db = null;
		try {
			db = this.getConnection(context);	      

			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			//	organizationList.setEscludiInDomanda(true);
			//	organizationList.setEscludiRespinti(true);

			//			String idAsl = context.getRequest().getParameter("searchaslSedeProduttiva");
			//			organizationList.setIdAsl(idAsl);
			//			
			//			String tipoRicerca = context.getRequest().getParameter("tipoRicerca");
			//			organizationList.setTipoRicerca(tipoRicerca);

			organizationList.buildList(db);
			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);

			

			LookupList tipoOperatore = new LookupList(db, "lookup_tipologia_operatore");
			context.getRequest().setAttribute("tipoOperatore", tipoOperatore);

			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));

			
			String tipoOp = context.getParameter("tipoOperazione");
			String pageOperazionePregfisso = "";
			if(tipoOp!=null)
			{
				switch (Integer.parseInt(tipoOp)) {
				case 1:
					/*Spostamento controlli*/
					
					if (context.getParameter("rifId")!=null)
					{
						int rifId = Integer.parseInt(context.getParameter("rifId"));
						String rifIdNome = context.getParameter("rifIdNome");
						
						int tipoRicerca = Integer.parseInt(context.getParameter("tipoRicerca"));
						RicercaList partenza = new RicercaList();
						partenza.setRiferimentoIdnomeCol(rifIdNome);
						partenza.setRiferimentoId(rifId);
						partenza.setTipoRicerca(tipoRicerca);
						partenza.buildList(db);
						
						if (partenza.size()>0)
						{
						 ((RicercaOpu) partenza.get(0)).setNumeroControlli(db);
						}
							
						
						
						if (organizationList.size()==1)
						{
							RicercaOpu anagraficaPartenza = (RicercaOpu) partenza.get(0);
						context.getRequest().setAttribute("AnagraficaPartenza", anagraficaPartenza);
						}
					}
					
					
					pageOperazionePregfisso="SpostaControlli";
					break;

				default:
					break;
				}
				
			}
			context.getRequest().setAttribute("StabilimentiList", organizationList);
			if(context.getParameter("modalita")!=null && context.getParameter("modalita").equals("json"))
				return pageOperazionePregfisso+"ListPopupJsonOK";
			
			if (context.getParameter("Popup")!=null)
				return pageOperazionePregfisso+"ListPopupOK";

			return pageOperazionePregfisso+"ListOK";



		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}

	
	
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db  = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		RicercaOpu opu = null;
		try {

			//R.M aggiunto controllo sul container
			String container = "archiviati";
			context.getRequest().setAttribute("container",
					container);
			
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			Integer tempid = null;
			if (temporgId != null) {
				tempid = Integer.parseInt(temporgId);
			} else {

				tempid = (Integer) context.getSession().getAttribute("orgIdf5");
				if (tempid > 0) {
					context.getSession().removeAttribute("orgIdf5");

				}
			}

			
		db = this.getConnection(context);
		
		if (!isRecordAccessPermitted(context, db, tempid)) {
				return ("PermissionError");
		}
		opu = new RicercaOpu(db, tempid,"org_id","arch");


		} catch (SQLException e) {
			logger.warning("Sie'verificato un Errore nel dettaglio Imprese : "+e.getMessage());
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		// If user is going to the detail screen
		addModuleBean(context, "View Accounts", "View Account Details");
		context.getRequest().setAttribute("OrgDetails", opu);

		return "DetailsOK";
	
	}
	
	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		//R.M aggiunto controllo sul container
		String container = context.getRequest().getParameter("container");
		context.getRequest().setAttribute("container",
				container);
		
		
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink("RicercaArchiviati.do?command=ViewVigilanza&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {

			db = this.getConnection(context);
			newOrg = new Organization(db, passedId);
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
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(passedId);
//			controlliList.setAltId(newOrg.getAltId());
			/*
			 * int punteggioAccumulato =
			 * controlliList.buildListControlliUltimiAnni(db, passedId);
			 * context.getRequest().setAttribute("punteggioUltimiAnni",
			 * punteggioAccumulato);
			 */
			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			
			ticList.setOrgId(passedId);
//			ticList.setAltId(newOrg.getAltId());
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
			ticList.buildList(db);

			
			Boolean flag=false;
			ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
					.load_linee_attivita_secondarie_per_org_id(String.valueOf(passedId), db);
			for(int i=0;i<linee_attivita_secondarie.size(); i++ ){
				LineeAttivita l = linee_attivita_secondarie.get(i);
				if (l!=null && l.getCodice_istat().equals("00.00.00")){
					flag = true;
				}
			}
			context.getRequest().setAttribute("flag", flag);
			
			
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}
	
	public String executeCommandCessazioneAttivita(ActionContext context) throws ParseException
	{
	 
	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
		String dataFineString = context.getParameter("dataCessazioneAttivita");
		Timestamp dataFine = null;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
		String note = context.getParameter("noteCessazione");
		int userId = getUserId(context);
	  
		Connection db = null;
		try
		{
			db=super.getConnection(context);
			Organization org = new Organization(db,orgId);
			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
			context.getRequest().setAttribute("OrgDetails", org);
		}
		catch(SQLException e)
		{
			
		}
			finally{
				super.freeConnection(context, db);
			}
		return "InsertOK";
	}
	
	
}