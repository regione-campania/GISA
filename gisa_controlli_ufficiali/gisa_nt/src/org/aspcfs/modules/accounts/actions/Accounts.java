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
package org.aspcfs.modules.accounts.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcf.modules.controlliufficiali.action.Diffida;
import org.aspcfs.modules.accounts.utils.AccountsUtil;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.OrganizationReport;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.base.CustomFieldRecord;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.cessazionevariazione.base.Ticket;


import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.lineeattivita.base.RelAtecoLineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.Storico;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
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
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

/**
 * Action for the Account module
 * 
 * @author chris
 * @created August 15, 2001
 * @version $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");

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

	/**
	 * Search: Displays the Account search form
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "accounts-accounts-view"))) {
			return ("PermissionError");
		}

		// Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
		
			context.getRequest().setAttribute("SiteList", siteList);

			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c
					.buildList(db, user.getSiteId());

			LookupList comuniList = new LookupList(listaComuni);
 
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList normeList = new LookupList(db,
					"opu_lookup_norme_master_list");
			normeList.addItem(-1, "NON SINVSA");
			context.getRequest().setAttribute("normeList",
					normeList);

			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			

			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);
			
			this.deletePagedListInfo(context, "SearchOrgListInfo");
			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context,
					"SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
		
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}

	/**
	 * Add: Displays the form used for adding a new Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {

			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
			
			LookupList countryList = new LookupList(db, "lookup_nazioni");
			// SOLO PAESI EUROPEI
			countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
			// ITALIA NON SELEZIONABILE
			countryList.removeElementByValue("Italia");
			countryList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CountryList", countryList);
 

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			

			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);

			// Carico nel context la lookup che unisce codici ateco e linee di
			// attivita'...
			LookupList rel_ateco_linea_attivita_List = costruisci_lookup_rel_ateco_linea_attivita(context,db);
			context.getRequest().setAttribute("rel_ateco_linea_attivita_List",
					rel_ateco_linea_attivita_List);

			ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita
					.load_all_rel_ateco_linee_attivita(db);
			context.getRequest().setAttribute("all_rel_ateco_linee_attivita",
					all_rel_ateco_linee_attivita);

			
			if (context.getRequest().getSession().getAttribute("OrgAdded")!=null)
			{
				context.getRequest().setAttribute("OrgDetails", context.getRequest().getSession().getAttribute("OrgAdded"));
				context.getRequest().getSession().removeAttribute("OrgAdded");
			}
			else
			{
			Organization newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
			   
	
			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());
			if (newOrg.getEnteredBy() != -1) {
			
				context.getRequest().setAttribute("OrgDetails", newOrg);
			}
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

		return getReturn(context, "Add");
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

	public String executeCommandUploadDoc(ActionContext context) {
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;

		try {

			db = getConnection(context);
			MultipartRequest multi = null;
			if (action == null) {
				int maxUploadSize = 50000000;
				multi = new MultipartRequest(req, ".", maxUploadSize,"UTF-8");
			}

			String orgId = multi.getParameter("orgId");
			int org_Id = Integer.parseInt(orgId);

			File myFileT = multi.getFile("file1");
			FileInputStream fiStream = new FileInputStream(myFileT);
			// BufferedReader input = new BufferedReader(new
			// FileReader(myFileT));

			BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logdistributori");

			String pathDownloadLog = LeggiFile.leggiCampiDistributoriCSV(
					context, db, myFileT, logPath, getUserId(context), org_Id);

			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim() + "/" + data[1].trim() + "/"
					+ data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport(dataOperazione,
					pathDownloadLog);
			rImport.insertDistributore(db, org_Id);
			
			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			

			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);

			context.getRequest().setAttribute("pathLog", pathDownloadLog);
			context.getRequest().setAttribute("orgId", orgId);

		} catch (Exception e) {
			context.getRequest().setAttribute("NoFile", "true");
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();

			return ("ErrorFile");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DistributoriUploadSaveOK");

	}

	public String executeCommandInsertDistributori(ActionContext context) {

		/*
		 * if (!hasPermission(context, "distributori-distributori-upload")) {
		 * return ("PermissionError"); }
		 */
		context.getSession().setAttribute("orgIdDistributore",
				context.getRequest().getParameter("id"));

		return ("Upload");

	}

	public String executeCommandAllImportRecords(ActionContext context) {
		Connection db = null;

		try {

			db = getConnection(context);
			String idorg = context.getRequest().getParameter("orgid");

			int orgid = Integer.parseInt(idorg);
			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListDistributori(db, orgid);

			context.getRequest().setAttribute("allRecords",
					rImport.getAllRecord());

			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DistributoriUploadListOK");

	}

	List<String> uniquePropertyValues = null;
	int k = -1;

	Integer[] array = null;


	
	public String executeCommandPrepareUpdateLineePregresse(ActionContext context) {
		Connection db = null;
		try {
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
			
			// 1 indica il tipo di operatore 852
			context=PrepareUpdateLineePregresse(context,db,temporgId,1);
			
			return getReturn(context, "PrepareUpdateLineePregresse");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	
	public String executeCommandUpdateLineePregresse(ActionContext context) {
		Connection db = null;
		try {
			db = this.getConnection(context);	
			String idImpresa = context.getRequest().getParameter("id_impresa");
			
			int tipoImpresa = -1;
			PreparedStatement pst = db.prepareStatement("select tipologia from organization where org_id = "+idImpresa);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				tipoImpresa = rs.getInt("tipologia");
			}
			if (tipoImpresa == 801)
				tipoImpresa = 3;
			else
				tipoImpresa = 1;
			
			return UpdateLineePregresse(context,db,idImpresa,tipoImpresa,getUserId(context));
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public static ActionContext PrepareUpdateLineePregresse(ActionContext context, Connection db, String id_impresa,
			int tipo_impresa) {
		try {
			if (tipo_impresa == 1) {
				ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
						.load_linee_attivita_secondarie_per_org_id(id_impresa, db);
				context.getRequest().setAttribute("linee_attivita_secondarie", linee_attivita_secondarie);

				ArrayList<String> linee_nuove = new ArrayList<String>();
				ArrayList<LineeAttivita> linee_attivita = new ArrayList<LineeAttivita>();

				// Questa stringa contiene la concatenazioni dei vari cod
				// macroarea, cod aggregazione ecc ecc con separatore '&&&'
				String id_linea = "";
				// id dell'ultimo livello per controllare se e' una foglia o meno
				int id_ultimo_livello = 0;
				// Descrizione linea di attivita' nuova nella parte sinistra
				// della form
				String desc_linea = "";
				// Descrizione linea di attivita' nuova nella parte destra della
				// form
				String desc_linea_2 = "";
				// indice i = 0 corrisponde alla linea primaria
				for (int i = 0; i < linee_attivita_secondarie.size(); i++) {
					id_ultimo_livello = 0;
					id_linea = "";
					desc_linea = "";
					desc_linea_2 = "";
					LineeAttivita l = linee_attivita_secondarie.get(i);
					if (!l.isMappato()) {
						
						PreparedStatement stat = db.prepareStatement("select * from ml8_linee_attivita_nuove_materializzata  where id_nuova_linea_attivita =  " + l.getId_attivita_masterlist());
						ResultSet rs = stat.executeQuery();
						boolean procedi = true;
						if(!rs.next())
						{
							stat = db.prepareStatement("select * from mapping_codice_ateco_master_list_2015_2016 where id = " + l.getId_attivita_masterlist());
							rs = stat.executeQuery();
							if(!rs.next())
								procedi = false;
						}
						
						if (procedi) 
						{
							if (rs.getString("macroarea") != null && !rs.getString("macroarea").equals("")) {
								id_ultimo_livello = rs.getInt("id_macroarea");
								desc_linea_2 = "<b>MACROAREA: </b>" + rs.getString("macroarea") + "</br></br>";
								id_linea = "" + rs.getInt("id_macroarea") + "&&&";
								desc_linea += "<tr><th colspan='2'>Corrsispondente nuovo</th></tr><tr><td nowrap class='formLabel'>MACROAREA</td><td>"
										+ rs.getString("macroarea") + "</td></tr>";
								l.setMacroarea(rs.getString("macroarea"));
							}
							if (rs.getString("aggregazione") != null && !rs.getString("aggregazione").equals("")) {
								id_ultimo_livello = rs.getInt("id_aggregazione");
								desc_linea_2 += "<b>AGGREGAZIONE: </b>" + rs.getString("aggregazione") + "</br></br>";
								id_linea += "" + rs.getInt("id_aggregazione") + "&&&";
								desc_linea += "<tr><td nowrap class='formLabel'>AGGREGAZIONE</td><td>"
										+ rs.getString("aggregazione") + "</td></tr>";
								l.setAggregazione(rs.getString("aggregazione"));
							}
							if (rs.getString("attivita") != null && !rs.getString("attivita").equals("")) {
								id_ultimo_livello = rs.getInt("id_attivita");
								desc_linea_2 += "<b>ATTIVITA: </b>" + rs.getString("attivita") + "</br></br>";
								id_linea += "" + rs.getInt("id_attivita") + "&&&";
								desc_linea += "<tr><td nowrap class='formLabel'>ATTIVITA</td><td>"
										+ rs.getString("attivita") + "</td></tr>";
								l.setAttivita(rs.getString("attivita"));
							}
							if (rs.getString("descrizione") != null && !rs.getString("descrizione").equals("")) 
							{
								try
								{
									id_ultimo_livello = rs.getInt("id_nuova_linea");
									desc_linea_2 += "<b>DESCRIZIONE: </b>" + rs.getString("descrizione") + "</br>";
									id_linea += "" + rs.getInt("id_nuova_linea") + "&&&";
									desc_linea += "<tr><td nowrap class='formLabel'>DESCRIZIONE</td><td>"
											+ rs.getString("descrizione") + "</td></tr>";
								}
								catch(SQLException e)
								{
									
								}
								
								// context.getRequest().setAttribute("nuova_linea",
								// linee_nuove);
							}
						}
						// Se c'e' la vecchia linea nella nuova gestione,
						// controllo se l'ultimo livello corrisponde ad una
						// foglia
						if (id_ultimo_livello != 0) {
							stat = db.prepareStatement("select * from ml8_linee_attivita_nuove_materializzata  where id_padre = "
									+ id_ultimo_livello);
							rs = stat.executeQuery();
							if (rs.next()) {
								// Non e' livello foglia
								id_linea = "false&&&" + id_linea;
							} else {
								// Livello foglia
								id_linea = "true&&&" + id_linea;

							}
						}
						if (!desc_linea.equals(""))
							desc_linea += "---" + id_linea + "---" + desc_linea_2;
						linee_attivita.add(l);
						linee_nuove.add(desc_linea);

						stat.close();
						rs.close();
					}
				}
				context.getRequest().setAttribute("linee_attivita", linee_attivita);
				context.getRequest().setAttribute("nuove_linee", linee_nuove);

				LookupList LookupTipoAttivita = new LookupList(db, "opu_lookup_tipologia_attivita");
				context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

				LineaProduttivaList lpList = new LineaProduttivaList();
				context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
				
				Organization org = new Organization(db, Integer.parseInt(id_impresa));
				context.getRequest().setAttribute("org", org);
			}
			if (tipo_impresa==2){
				LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
				context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

				Stabilimento newStabilimento = null;
				newStabilimento = new Stabilimento(db,  Integer.parseInt(id_impresa));
				newStabilimento.getPrefissoAction(context.getAction().getActionName());
				context.getRequest().setAttribute("StabilimentoDettaglio",
						newStabilimento);
				
				
				newStabilimento.buildListLineeProduttivePregresse(db);
				newStabilimento.setListaLineeProduttive(newStabilimento.getListaLineeProduttivePregresse()); 
				LineaProduttivaList lpl = newStabilimento.getListaLineeProduttive();
				
				for(Object lpt : lpl )
				{
					LineaProduttiva lp0= (LineaProduttiva)lpt;
					
					//questa è la linea nuova associata fittizia
					String[] temp = lp0.getDescrizione_linea_attivita().split("->");
					
					int idLineaVecchia = -1;
					
					try
					{
						String macroarea = temp[0].replace("'","");
						String aggregazione = temp[1].replace("'","");
						String attivita = temp[2].replace("'","");
						idLineaVecchia = LineeAttivita.ottieniLineaVecchiaDaNuovaFittizia(db, macroarea, aggregazione, attivita);
					} 
					catch(Exception ex)
					{
						//niente
						
					}
					
					if(idLineaVecchia != -1)
					{
						lp0.setCandidatiNuoveLineeRanking(LineeAttivita.load_candidati_per_linea_fittiziaVersioneKnowledgeBased(idLineaVecchia,db)) ;
					}
					else //non sono riuscito a trovare la corrispondenza tra linea nuova fittizia (non presente in opu) e quella vecchia in lista linee attivita vecchia ana
					{
						System.out.println("trovata linea non mappabile");
					}
					
					
				}
				

				Operatore operatore = new Operatore () ;
				org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
				org.setName(operatore.getRagioneSociale());
				operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
				operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
				context.getRequest().setAttribute("Operatore", operatore);

				LineaProduttivaList lpList = new LineaProduttivaList();
				context.getRequest().setAttribute("ListaLineaProduttiva", lpList);

			}
			return context;

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return context;
		}
	}
	 

	

	// tipo_impresa:
	// 1 - 852
	// 2 - OPU
	
	
	
	
	
	// tipo_impresa:
	// 1 - 852
	// 2 - OPU
	public static String UpdateLineePregresse(ActionContext context, Connection db, String id_impresa, int tipo_impresa, int userId) {
		String ret = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			if (tipo_impresa == 1) {

				if (context.getRequest().getParameterValues("idLineaProduttiva") != null
						&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {
					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
					String id_nuovo = "";
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						id_nuovo = "";
						if (!lineeProduttiveSelezionate[i].equals("")) {
							id_nuovo = lineeProduttiveSelezionate[i];
						} else {
							id_nuovo = context.getRequest().getParameter("idLineaProduttiva_" + i);
							if (id_nuovo == null) {
								id_nuovo = (String) context.getRequest().getAttribute("idLineaProduttiva_" + i);
							}
						}
						int idVecchia = -1;
						if (context.getRequest().getParameter("vecchiaLineaId" + i) != null
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals("null")
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals(""))
							idVecchia = Integer.parseInt(context.getRequest().getParameter("vecchiaLineaId" + i));
						PreparedStatement pst = db
								.prepareStatement("update la_imprese_linee_attivita set note_internal_use_only = concat(note_internal_use_only, current_timestamp, '. Aggiornata linea da ', id_attivita_masterlist,  ' a "+id_nuovo+" da utente "+userId+"' ), modified_by = " + userId + ", modified = current_timestamp, mappato = true, id_attivita_masterlist_old = id_attivita_masterlist, id_attivita_masterlist = "
										+ id_nuovo + " where id = " + idVecchia);
						pst.execute();
					}
				}

				/*REFRESH TAB MATERIALIZZATA*/
				try {
				RicercheAnagraficheTab.inserOrganization(db, Integer.parseInt(id_impresa));
				} catch (Exception e) {}
				
				ret = "UpdateLineePregresseOK";
			}
			if (tipo_impresa == 2) {
				Stabilimento newStabilimento = null;
				newStabilimento = new Stabilimento(db, Integer.parseInt(id_impresa));
				newStabilimento.getPrefissoAction(context.getAction().getActionName());
				// context.getRequest().setAttribute("StabilimentoDettaglio",
				// newStabilimento);
				// context.getRequest().setAttribute("idStab", id_impresa);
				
				
				//metto da parte tutti i parametri che contengono il mapping usando candidato ranked
				//controllo se esiste almeno una linea scelta usando il candidato rank
				//NB: MODIFICA---> L'ID CHE VA EFFETTIVAMENTE REGISTRATO COME ID LINEA NUOVA NEL KNOWLEDGE BASED MAPPING E' QUELLO DEL TERZO LIVELLO
				//(POICHE' POTREBBE ESSERE ARRIVATO UN ID LINEA RELATIVO A LIVELLI SUCCESSIVI)
				Map<String,String[]> tuttiParametriRequest = context.getRequest().getParameterMap();
				HashMap<Integer,Integer> fromVecchiaLineaToNuovaRankedSoloPerScelteRanked = new HashMap<Integer,Integer>();  
				//me li metto da parte

				for(String nomePar : tuttiParametriRequest.keySet())
				{
					if(nomePar.contains("candidato-"))
					{
						String[] t = nomePar.split("-");
						Integer idLineaVecchia = Integer.parseInt(t[1]); //id linea vecchia fittizia 
						String valoreParametro = context.getRequest().getParameter(nomePar); //il valore e' l'id della nuova linea associata
						Integer idLineaNuova = Integer.parseInt(valoreParametro);
						fromVecchiaLineaToNuovaRankedSoloPerScelteRanked.put(idLineaVecchia, idLineaNuova);
					}
				}
				
				
				//voglio ottenere corrispondenza tra tutti gli id della linea fittizia, e quella vecchia a cui fanno riferimento
				//poichè quella è quella usata per il knowledge based mapping
				HashMap<Integer,Integer> fromLineaFittiziaToVecchia = new HashMap<Integer,Integer>();
				
				for(int i=0; i< newStabilimento.getListaLineeProduttive().size();i++)
				{
					LineaProduttiva lpt = (LineaProduttiva)newStabilimento.getListaLineeProduttive().get(i);
					int idLineaFittizia = lpt.getId();
					String[] t = lpt.getDescrizione_linea_attivita().split("->");
					try
					{
						String macroarea = t[0].replace("'", "");
						String aggregazione = t[1].replace("'","");
						String attivita = t[2].replace("'","");
						
						int idLineaVecchia = LineeAttivita.ottieniLineaVecchiaDaNuovaFittizia(db, macroarea,aggregazione,attivita);
						//se non la trova, idLineaVecchia (cioè quella in lista linee vecchia ana) è -1
						fromLineaFittiziaToVecchia.put(idLineaFittizia,idLineaVecchia);
					}
					catch(Exception ex) //alcune linee non hanno descrizione fino al terzo livello
					{
						System.out.println("linea fittizia non mappabile all'indietro, la salto.");
						continue;
					}
					
				}
				
				
				
				for (int i = 0; i < newStabilimento.getListaLineeProduttive().size(); i++) {
					newStabilimento.getListaLineeProduttive().removeAllElements();
				}
				
				if (context.getRequest().getParameterValues("idLineaProduttiva") != null
						&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {

					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");

					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						if (!lineeProduttiveSelezionate[i].equals("")) {
							
							 
							
							
							String numero = "";
							if (context.getRequest().getParameter("vecchiaLineaNumero" + i) != null
									&& !context.getRequest().getParameter("vecchiaLineaNumero" + i).equals("null"))
								numero = context.getRequest().getParameter("vecchiaLineaNumero" + i);

							String codice = "";
							if (context.getRequest().getParameter("vecchiaLineaCodice" + i) != null
									&& !context.getRequest().getParameter("vecchiaLineaCodice" + i).equals("null"))
								codice = context.getRequest().getParameter("vecchiaLineaCodice" + i);

							Timestamp dataInizio = null;
							if (context.getRequest().getParameter("vecchiaLineaDataInizio" + i) != null && !context.getRequest().getParameter("vecchiaLineaDataInizio" + i).equals("")
									&& !context.getRequest().getParameter("vecchiaLineaDataInizio" + i).equals("null"))
								dataInizio = new Timestamp(sdf.parse(context.getRequest().getParameter(
										"vecchiaLineaDataInizio" + i)).getTime());

							Timestamp dataFine = null;
							if (context.getRequest().getParameter("vecchiaLineaDataFine" + i) != null && !context.getRequest().getParameter("vecchiaLineaDataFine" + i).equals("")
									&& !context.getRequest().getParameter("vecchiaLineaDataFine" + i).equals("null"))
								dataFine =  new Timestamp(sdf.parse(context.getRequest().getParameter(
										"vecchiaLineaDataFine" + i)).getTime());

							int stato = -1;
							if (context.getRequest().getParameter("vecchiaLineaStato" + i) != null
									&& !context.getRequest().getParameter("vecchiaLineaStato" + i).equals("null"))
								stato = Integer.parseInt(context.getRequest().getParameter("vecchiaLineaStato" + i));

							int idLineaInOpuFittizia = -1; //questo è in realtà id della fittizia vecchia
							if (context.getRequest().getParameter("vecchiaLineaId" + i) != null
									&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals("null")
									&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals(""))
								idLineaInOpuFittizia = Integer.parseInt(context.getRequest().getParameter("vecchiaLineaId" + i));

							int idRelInOpuFittizia = -1;
							if (context.getRequest().getParameter("vecchiaLineaIdRel" + i) != null
									&& !context.getRequest().getParameter("vecchiaLineaIdRel" + i).equals("null")
									&& !context.getRequest().getParameter("vecchiaLineaIdRel" + i).equals(""))
								idRelInOpuFittizia = Integer.parseInt(context.getRequest().getParameter(
										"vecchiaLineaIdRel" + i));

							LineaProduttiva lp = new LineaProduttiva();
							String idNuovaLinea = "";
							if(!lineeProduttiveSelezionate[i].equals("-1")) 
							{
								idNuovaLinea =  lineeProduttiveSelezionate[i] ;
							}
							else
							{ //QUESTE SONO QUELLE CHE ARRIVANO COL RANKED
								idNuovaLinea = fromVecchiaLineaToNuovaRankedSoloPerScelteRanked.get(idLineaInOpuFittizia)+"";
								//gestisco il ranking
							}
							
							lp.setIdRelazioneAttivita(idNuovaLinea);
							
							lp.setCodice_ufficiale_esistente(codice);
							lp.setDataInizio(dataInizio);
							lp.setDataFine(dataFine);
							lp.setStato(stato);
							lp.setNumeroRegistrazione(numero);
							
							lp.setIdVecchiaLinea(idLineaInOpuFittizia);
							
							if (i == 0)
								lp.setPrincipale(true);

							org.aspcfs.modules.opu.actions.StabilimentoAction.gestisciMappingLineePregresse(idLineaInOpuFittizia,
									lp.getIdRelazioneAttivita(), idRelInOpuFittizia, newStabilimento.getIdStabilimento(),
									((UserBean) context.getSession().getAttribute("User")).getUserId(), db);

							
							int idLineaVecchia = fromLineaFittiziaToVecchia.containsKey(idLineaInOpuFittizia) ?  fromLineaFittiziaToVecchia.get(idLineaInOpuFittizia) : -1;
							
							if(idLineaVecchia != -1)
								lp.setMessage(idLineaVecchia+""); 
							
							
							
							HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> campiEst = StabilimentoAction.costruisciValoriCampiEstesi(context, db, Integer.parseInt(idNuovaLinea));
							lp.addCampiEstesi(campiEst);
							
							newStabilimento.getListaLineeProduttive().add(lp); 
							//in ogni caso gescisco l'aggiornamento del mapping  
							//NB: l'id vecchia linea per il mapping, deve essere quello della linea vecchia a cui era stata
							//associata la linea fittizia  
							//quindi estraggo idlinea vecchia della fittizia 
							
							
							
							if( idLineaVecchia != -1) //se è -1 non sono riuscito a trovare la rappresentazione, in lista_linee_vecchia_anagrafica, della linea fittizia in opu
							{//quindi non aggiorno il ranking
								int idCandidatoTerzoLivelloML = LineeAttivita.ottieniIdPadreAlTerzoLivelloDataLinea(db,Integer.parseInt(idNuovaLinea) );
								//PRENDERE TIPOLOGIA e ORG ID dell'operatore originario
								int tipologia = -1;
								int orgId = -1;
								int idstab = -1;
								int idNorma = -1;
								
								int[] t = LineeAttivita.ottieniInfoOrgDaIdOpu(db,Integer.parseInt(id_impresa));
								orgId = t[0];
								tipologia = t[1];
								idstab = t[2];
								idNorma = LineeAttivita.getIdNormaDaLineaMS(db, Integer.parseInt(idNuovaLinea));
								
								
								LineeAttivita.gestisciRankingMapping(db,orgId,idstab,tipologia,idLineaVecchia,idCandidatoTerzoLivelloML,idNorma,true);
							}
						}
						 
					}
					
					
					 
					
					UserBean user = (UserBean) context.getSession().getAttribute("User");
					String ip = context.getIpAddress();
					int user_id = user.getUserRecord().getId();
					newStabilimento.setModifiedBy(user_id);
					
					
					
//					newStabilimento.gestisciLineeProduttivePregresse(db);
					newStabilimento.gestisciLineeProduttivePregresseVersioneNuova(db,userId);
					
					

					String[] infoUtente = { "-1", "", "" };
					infoUtente[0] = String.valueOf(((UserBean) context.getSession().getAttribute("User")).getUserId());
					infoUtente[1] = ((UserBean) context.getRequest().getSession().getAttribute("User")).getUserRecord()
							.getSuap().getCodiceFiscaleRichiedente();
					infoUtente[2] = ((UserBean) context.getRequest().getSession().getAttribute("User")).getUserRecord()
							.getSuap().getCodiceFiscaleDelegato();
					org.aspcfs.modules.opu.actions.StabilimentoAction.gestioneStorico(newStabilimento, Storico.IMPORT,
							infoUtente, "", db);
					org.aspcfs.modules.opu.actions.StabilimentoAction.gestioneStorico(newStabilimento,
							Storico.AGGIORNAMENTO_LINEE, infoUtente, "", db);

				}

				

				/*REFRESH TAB MATERIALIZZATA*/
				try {
				RicercheAnagraficheTab.inserOpu(db, Integer.parseInt(id_impresa));
			} catch (Exception e) {}

				ret = "UpdateLineePregresseOK";

			}
			else if (tipo_impresa == 3) {

				if (context.getRequest().getParameterValues("idLineaProduttiva") != null
						&& context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {
					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
					String id_nuovo = "";
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						id_nuovo = "";
						if (!lineeProduttiveSelezionate[i].equals("")) {
							id_nuovo = lineeProduttiveSelezionate[i];
						} else {
							id_nuovo = context.getRequest().getParameter("idLineaProduttiva_" + i);
							if (id_nuovo == null) {
								id_nuovo = (String) context.getRequest().getAttribute("idLineaProduttiva_" + i);
							}
						}
						int idVecchia = -1;
						if (context.getRequest().getParameter("vecchiaLineaId" + i) != null
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals("null")
								&& !context.getRequest().getParameter("vecchiaLineaId" + i).equals(""))
							idVecchia = Integer.parseInt(context.getRequest().getParameter("vecchiaLineaId" + i));
						PreparedStatement pst = db.prepareStatement("INSERT INTO public.la_imprese_linee_attivita( org_id, entered, entered_by, modified, modified_by, id_attivita_masterlist, mappato,note_internal_use_only) " +
							    " VALUES (?, now(), ?, now(), ?, ?,  true, 'Aggiornamento linea da utente "+userId+"')");
						pst.setInt(1, Integer.parseInt(id_impresa));
						pst.setInt(2, userId);
						pst.setInt(3, userId);
						pst.setInt(4, Integer.parseInt(id_nuovo));
						pst.execute();
					}
				}

				/*REFRESH TAB MATERIALIZZATA*/
				try {
				RicercheAnagraficheTab.inserOrganization(db, Integer.parseInt(id_impresa));
				} catch (Exception e) {}
				
				ret = "UpdateLineePregresseOK";
			}

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
	}
	
	
	
	
	
	

	
	
	

	public String executeCommandDetails(ActionContext context) throws IndirizzoNotFoundException {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db  = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {

			//R.M aggiunto controllo sul container
		
			
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
			PreparedStatement stat_veicoli = db
					.prepareStatement("select count(*) as numero from distributori_automatici where org_id="
							+ tempid);
			ResultSet rs_veicoli = stat_veicoli.executeQuery();
			int numeroDistributori = 0;
			if (rs_veicoli.next())
				numeroDistributori = rs_veicoli.getInt(1);

			numeroDistributori = (numeroDistributori / 15) + 1;
			context.getRequest().setAttribute("numeroDistributori",
					numeroDistributori);

			
			newOrg = new Organization(db, tempid);
			
			
			ArrayList <String> lineeStoriche = new ArrayList<String>();
			PreparedStatement pstStoriche = db.prepareStatement("select case when lcd.description is not null then lcd.description else '' end || ' ' || case when lcd.short_description is not null then lcd.short_description else '' end || ' ' || case when la.categoria is not null then la.categoria else '' end || ' - ' || case when lda.mappato is true then case when la.linea_attivita is not null then la.linea_attivita else '' end || ' ' || case when opu.macroarea is not null then ' Macroarea: ' || opu.macroarea else '' end || ' ' || case when opu.aggregazione is not null then ' Aggregazione: ' || opu.aggregazione else '' end || ' ' || case when opu.attivita is not null then ' Attivita: ' || opu.attivita else '' end || ' ' || case when opu.descrizione is not null then ' Descrizione: ' || opu.descrizione else '' end else '' end as linee from la_imprese_linee_attivita lda left join la_rel_ateco_attivita rat on (lda.id_rel_ateco_attivita = rat.id) left join lookup_codistat lcd on (rat.id_lookup_codistat = lcd.code) left join opu_linee_attivita_nuove opu on opu.id_nuova_linea_attivita = lda.id_attivita_masterlist, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod where lda.trashed_date is null and lda.org_id= ? and lda.id_rel_ateco_attivita=rel.id and rel.id_linee_attivita=la.id and rel.id_lookup_codistat=cod.code and lda.trashed_date is null");
			pstStoriche.setInt(1, newOrg.getOrgId());
			ResultSet rsStoriche = pstStoriche.executeQuery();
			while (rsStoriche.next()){
				lineeStoriche.add(rsStoriche.getString("linee"));
			}
			context.getRequest().setAttribute("lineeStoriche", lineeStoriche);
	
			RicercaOpu AnagraficaStabilimento = new RicercaOpu(db, tempid, "org_id");
			context.getRequest().setAttribute("AnagraficaStabilimento", AnagraficaStabilimento);
			
			
			
			if(newOrg.getTrashedDate()!=null)
			{
			String container = "archiviati";
			context.getRequest().setAttribute("container",
					container);
			}
			
			//Caricamento Diffide
			TicketList diffList = new TicketList();
		    diffList.setOrgId(newOrg.getOrgId());
		    diffList.buildListDiffideOsaCentralizzato(db);
		    context.getRequest().setAttribute("DiffideList", diffList);
			
			
			
			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			

			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);
			
			// Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress) it_coords
						.next();
				if (thisAddress.getLatitude() != 0
						&& thisAddress.getLongitude() != 0) {

					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
					
//					if (spatial_coords[0]!=null && (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(
//									',', '.')) > 41.503754) ){
//						AjaxCalls ajaxCall = new AjaxCalls();
//						String[] coordinate = ajaxCall.getCoordinate(
//								thisAddress.getStreetAddressLine1(),
//								thisAddress.getCity(), thisAddress.getState(),
//								thisAddress.getZip(), ""
//										+ thisAddress.getLatitude(), ""
//										+ thisAddress.getLongitude(), "");
//						thisAddress.setLatitude(coordinate[1]);
//						thisAddress.setLongitude(coordinate[0]);
//					} else {
						thisAddress.setLatitude(spatial_coords[0]);
						thisAddress.setLongitude(spatial_coords[1]);
					//}
				}

				// context.getSession().setAttribute("lat",
				// Double.toString(thisAddress.getLatitude()));
				// context.getSession().setAttribute("lon",
				// Double.toString(thisAddress.getLongitude()));

			}

			if (context.getRequest().getParameter("generaCodice") != null) {
				this.generaCodice(db, tempid, false);
			}
			// check whether or not the owner is an active User
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
			
			LookupList countryList = new LookupList(db, "lookup_nazioni");
			countryList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CountryList", countryList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);
		// Carico nel context le linee di attivita'
			
			ArrayList<LineeAttivita> linee_attivita = LineeAttivita
					.load_linee_attivita_per_org_id(temporgId, db);
			context.getRequest().setAttribute("linee_attivita", linee_attivita);
			
			
			boolean presenteIstatAmbulante = false ;
			
			if(newOrg.getTipoDest()!= null && newOrg.getTipoDest().equalsIgnoreCase("Es. Commerciale"))
			{
				if(linee_attivita.size()>1)
				{
					for(LineeAttivita la : linee_attivita)
					{
						if(la.isCommercioAmbulante())
						{
							context.getRequest().setAttribute("BloccaImport", "SI");
						}
					}
				}
				
			}
			
			
			
			
			LineeAttivita linea_attivita_principale = LineeAttivita
					.load_linea_attivita_principale_per_org_id(temporgId, db);
			context.getRequest().setAttribute("linea_attivita_principale",
					linea_attivita_principale);
			
			ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
					.load_linee_attivita_secondarie_per_org_id(temporgId, db);
			context.getRequest().setAttribute("linee_attivita_secondarie",
					linee_attivita_secondarie);
			
			/*
			boolean linee_pregresse=false;
			if (!linea_attivita_principale.isMappato())
				linee_pregresse=true;
			
			for(int i=0; i< linee_attivita_secondarie.size();i++){
				if (!linee_attivita_secondarie.get(i).isMappato()){
					linee_pregresse=true;
					break;	
				}
			}
			
			context.getRequest().setAttribute("linee_pregresse",""+linee_pregresse);
			*/
			context.getRequest().setAttribute("linee_pregresse","false");
			
			
			if (newOrg.getTipoDest() != null) {
				if (newOrg.getTipoDest().equalsIgnoreCase("distributori")) {

					RowSetDynaClass rsdc = null;

					

					PreparedStatement stat = db
							.prepareStatement("select * from distributori_automatici ,lookup_tipo_distributore where alimenti_distribuiti=code and org_id="
									+ newOrg.getId());
					ResultSet rs = stat.executeQuery();
					rsdc = new RowSetDynaClass(rs);

					TableFacade tf = TableFacadeFactory.createTableFacade("15",
							context.getRequest());
					tf.setEditable(true);

					Collection<Distrubutore> coll = newOrg
							.getListaDistributori();

					tf.setItems(coll);
					tf.setColumnProperties("matricola", "data", "comune",
							"indirizzo", "provincia", "cap",
							"descrizioneTipoAlimenti", "note", "ubicazione",
							"elimina");
					tf.setStateAttr("restore");

					HtmlRow row = (HtmlRow) tf.getTable().getRow();
					row.setUniqueProperty("matricola"); // the unique worksheet
														// properties to
														// identify the row

					tf.getTable().getRow().getColumn("matricola").setTitle(
							"matricola");
					tf.getTable().getRow().getColumn("data").setTitle(
							"data Installazione");
					tf.getTable().getRow().getColumn("comune").setTitle(
							"comune");
					tf.getTable().getRow().getColumn("provincia").setTitle(
							"Indirizzo");
					tf.getTable().getRow().getColumn("indirizzo").setTitle(
							"Provincia");
					tf.getTable().getRow().getColumn("cap").setTitle("cap");

					tf.getTable().getRow().getColumn("descrizioneTipoAlimenti")
							.setTitle("Alimento Distribuito");
					tf.getTable().getRow().getColumn("note").setTitle("note");
					tf.getTable().getRow().getColumn("ubicazione").setTitle(
							"ubicazione");
					tf.getTable().getRow().getColumn("elimina").setTitle(
							"Elimina");

					Iterator<Distrubutore> it = coll.iterator();

					array = new Integer[coll.size()];
					int i = 0;
					while (it.hasNext()) {
						Distrubutore dd = it.next();
						array[i] = dd.getAlimentoDistribuito();
						i++;

					}

					HtmlColumn cg1 = (HtmlColumn) tf.getTable().getRow()
							.getColumn("descrizioneTipoAlimenti");
					cg1.getCellRenderer().setCellEditor(new CellEditor() {
						public Object getValue(Object item, String property,
								int rowCount) {
							LookupList alimenti = null;
//							try {
////								alimenti = new LookupList(db,
////										"lookup_tipo_distributore");
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}

							String temp = (String) (new HtmlCellEditor())
									.getValue(item, property, rowCount);
							String id = (String) (new HtmlCellEditor())
									.getValue(item, "alimentoDistribuito",
											rowCount);
							String matricola = (String) (new HtmlCellEditor())
									.getValue(item, "matricola", rowCount);
							temp = (temp == null || "".equals(temp.trim())) ? ("-")
									: (temp);
							k++;

							return alimenti.getHtmlSelect(
									"alimentiDistribuiti_" + matricola, id);

						}
					}

					);

					Limit limit = tf.getLimit();
					if (!limit.isExported()) {

						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow()
								.getColumn("matricola");
						cg.getFilterRenderer().setFilterEditor(
								new DroplistFilterEditor());
						// cg.setFilterable( false );

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"data");
						cg.setFilterable(true);

						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,
									String property, int rowCount) {

								String temp = (String) (new HtmlCellEditor())
										.getValue(item, property, rowCount);
								String data = (String) (new HtmlCellEditor())
										.getValue(item, "data", rowCount);
								String dataForm = "";
								if (data != null && !data.equals("")) {
									dataForm = data.substring(8, 10) + "-"
											+ data.substring(5, 7) + "-"
											+ data.substring(0, 4);
								}

								return "" + dataForm;

							}
						}

						);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"comune");
						cg.setFilterable(true);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);
						
						
						

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"provincia");
						cg.setFilterable(false);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "provincia", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"indirizzo");
						cg.setFilterable(true);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "indirizzo", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"cap");
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "cap", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);

						cg.setFilterable(false);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"descrizioneTipoAlimenti");
						cg.setFilterable(false);
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "descrizioneTipoAlimenti", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);


						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"note");
						
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "note", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);
						cg.setFilterable(false);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"ubicazione");
						cg.setFilterable(false);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,String property, int rowCount) {
								String val = (String) (new HtmlCellEditor()).getValue(item, "ubicazione", rowCount);

								if (val!= null)
									return val ;
								return "" ;

								
							}}
							);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn(
								"elimina");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor(new CellEditor() {
							public Object getValue(Object item,
									String property, int rowCount) {
								String iddef = (String) (new HtmlCellEditor())
										.getValue(item, "matricola", rowCount);

								String orgid = (String) (new HtmlCellEditor())
										.getValue(item, "org_id", rowCount);
								Comuni comune = new Comuni();

								return "<a href='DistributoriListImprese.do?orgId="
										+ orgid
										+ "&oggetto=distributore&id="
										+ iddef + "'>Elimina </a>";
							}
						}

						);
						cg.setFilterable(false);

					}

					// tf.getWorksheet().addRow()
					// tf.getWorksheet().addRow(new WorksheetRow)

					ToolbarItem item7 = (new ToolbarItemFactoryImpl(tf
							.getWebContext(), tf.getCoreContext()))
							.createFilterItem();
					item7.setTooltip("Filtra");
					tf.getToolbar().addToolbarItem(item7);

					ToolbarItem item8 = (new ToolbarItemFactoryImpl(tf
							.getWebContext(), tf.getCoreContext()))
							.createClearItem();
					item8.setTooltip("Resetta Filtro");
					tf.getToolbar().addToolbarItem(item8);

					ToolbarItem item2 = (new ToolbarItemFactoryImpl(tf
							.getWebContext(), tf.getCoreContext()))
							.createSaveWorksheetItem();
					item2.setTooltip("Salva");
					tf.getToolbar().addToolbarItem(item2);

					ToolbarItem item18 = (new ToolbarItemFactoryImpl(tf
							.getWebContext(), tf.getCoreContext()))
							.createPrevPageItem();
					item18.setTooltip("Scorri pagina indietro");
					tf.getToolbar().addToolbarItem(item18);

					ToolbarItem item17 = (new ToolbarItemFactoryImpl(tf
							.getWebContext(), tf.getCoreContext()))
							.createNextPageItem();
					item17.setTooltip("Scorri pagina in avanti");
					tf.getToolbar().addToolbarItem(item17);

					String tabella = tf.render();
					context.getRequest().setAttribute("tabella", tabella);

					context.getRequest().setAttribute("tf", tf);

				}
			}

		

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

		
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);

		
			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

			/*org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
			int AuditOrgId = newOrg.getOrgId();
			audit.setOrgId(AuditOrgId);

		/*	if ((audit.size() - 1) >= 0) {

				context.getRequest().setAttribute("Audit", audit.get(0));

			}*/
			
			PreparedStatement stat_trasporti = db.prepareStatement("select get_852_tipo_trasporto as tipo_trasporto from get_852_tipo_trasporto("+tempid+")");
			ResultSet rs_trasporti = stat_trasporti.executeQuery();
			int tipoTrasporto = -1;
			while (rs_trasporti.next())
				tipoTrasporto = rs_trasporti.getInt(1);
			context.getRequest().setAttribute("tipoTrasporto",	String.valueOf(tipoTrasporto));
			
			PreparedStatement stat_is_farmacia = db.prepareStatement("select * from is_farmacia("+tempid +");");
			ResultSet rs_is_farmacia = stat_is_farmacia.executeQuery();
			Boolean is_farmacia = false;
			while (rs_is_farmacia.next())
				is_farmacia = rs_is_farmacia.getBoolean(1);
			context.getRequest().setAttribute("is_farmacia", is_farmacia+"");
			
			if (newOrg.getVoltura()) {
				org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
				String idVoltura = newOrg.getIdVoltura(db, newOrg.getOrgId());

				if (idVoltura != null) {
					voltura.setId(Integer.parseInt(idVoltura));
					voltura.queryRecord(db, Integer.parseInt(newOrg
							.getIdVoltura(db, newOrg.getOrgId())));
					if ((voltura != null)) {

						context.getRequest().setAttribute("Voltura", voltura);
					}
				}
			}
		} catch (SQLException e) {
			logger.warning("Si e' verificato un Errore nel dettaglio Imprese : "+e.getMessage());
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
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

			if (context.getRequest().getParameter("generaCodice") != null)
				return "GoToDetailsOk";
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
		if (!hasPermission(context, "accounts-dashboard-view")) {
			if (!hasPermission(context, "accounts-accounts-view")) {
				return ("PermissionError");
			}

			// Bypass dashboard and search form for portal users
			if (isPortalUser(context)) {
				return (executeCommandSearch(context));
			}
			return (executeCommandSearchForm(context));
		}

		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession()
				.getAttribute("AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(this.getUser(context,
					this.getUserId(context)).getLocale());
			calendarInfo
					.addAlertType(
							"Accounts",
							"org.aspcfs.modules.accounts.base.AccountsListScheduledActions",
							"Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo",
					calendarInfo);
		}

		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");


		
		return ("DashboardOK");
	}

	
	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");

		String source = (String) context.getRequest().getParameter("source");
		String partitaIva = (String) context.getRequest().getParameter(
				"searchPartitaIva");
		
		
		
		
		String codiceF = (String) context.getRequest().getParameter(
				"searchCodiceFiscale");
		String stato = (String) context.getRequest().getParameter(
				"searchcodeCessato");
		String cognomeR = (String) context.getRequest().getParameter(
				"searchCognomeRappresentante");
		String nomeR = (String) context.getRequest().getParameter(
				"searchNomeRappresentante");
		String codIstat = (String) context.getRequest().getParameter(
				"searchCodiceFiscaleCorrentista");
		String categoriaR = (String) context.getRequest().getParameter(
				"searchcodecategoriaRischio");
		String codiceAllerta = context.getRequest().getParameter(
				"searchcodiceAllerta");
		String tipoDest = context.getRequest().getParameter("searchTipoDest");
		String addressType = context.getRequest().getParameter(
				"searchcodeAddressType");
		String statoCu = context.getRequest().getParameter("searchstatoCu");
		OrganizationList organizationList = new OrganizationList();
		organizationList.setTipologia(1);
		if (addressType != null && !addressType.equals("")) {
			organizationList.setTipoDest(tipoDest);
			organizationList.setAddressType(addressType);
		}
		if (categoriaR != null)
			organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));
		if (!"".equals(codIstat) && codIstat != null) {
			organizationList.setCodiceFiscaleCorrentista(codIstat);
		}
		if (!"".equals(partitaIva) && partitaIva != null) {
			organizationList.setPartitaIva(partitaIva);
		}
		if (!"".equals(codiceF) && codiceF != null) {
			organizationList.setCodiceFiscale(codiceF);
	     	}
		if (!"".equals(cognomeR) && cognomeR != null) {
			organizationList.setCognomeRappresentante(cognomeR);
		}
		if (!"".equals(nomeR) && nomeR != null) {
			organizationList.setNomeRappresentante(nomeR);
		}
		if (!"".equals(stato) && stato != null && !stato.equals("-1")) {
			organizationList.setCessato(stato);
		}
		if (!"".equals(statoCu) && statoCu != null && !statoCu.equals("-1")) {
			organizationList.setStatoCu(statoCu);
		}
		if (categoriaR != null)
			organizationList.setCategoriaRischio(Integer.parseInt(categoriaR));

		if (!"".equals(codiceAllerta) && codiceAllerta != null)
			organizationList.setCodiceAllerta(codiceAllerta);

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchOrgListInfo");
		searchListInfo.setLink("Accounts.do?command=Search");
		searchListInfo.setListView("all");
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

					LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);
			
			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			
			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);

			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());

			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			LookupList categoriaRischioList = new LookupList(db,"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",categoriaRischioList);
			
			
			LookupList normeList = new LookupList(db,"opu_lookup_norme_master_list");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("normeList",normeList);

				organizationList.setPagedListInfo(searchListInfo);
				
				searchListInfo.setSearchCriteria(organizationList, context);
				organizationList.buildList(db);
				
				context.getRequest().setAttribute("OrgList", organizationList);
				
				return "ListOK";
			
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	/**
	 * Search Accounts
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	

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
		ticketListInfo.setLink("Accounts.do?command=ViewVigilanza&orgId="
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
			//if (!isRecordAccessPermitted(context, db, passedId)) {
			//	return ("PermissionError");
			//}
			
			ticList.setOrgId(passedId);
//			ticList.setAltId(newOrg.getAltId());
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			String statusId = context.getRequest().getParameter("statusId");
			ticList.setStatusId(statusId);
			
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


	public String executeCommandViewCessazionevariazione(ActionContext context) {
		if (!hasPermission(context,
				"accounts-accounts-cessazionevariazione-view")) {
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
				.setLink("Accounts.do?command=ViewCessazionevariazione&orgId="
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
		return getReturn(context, "ViewCessazionevariazione");
	}

	/**
	 * Insert: Inserts a new Account into the database.
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 */
	public String executeCommandInsert(ActionContext context)
			throws SQLException {
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		Organization newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
		Audit audit = new Audit();
		audit.setLivelloRischioFinale(-1);

		newOrg.setTypeList(context.getRequest().getParameterValues(
				"selectedList"));
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setOwner(getUserId(context));
		newOrg.setCategoriaRischio(-1);
		newOrg.setCessato(context.getRequest().getParameter("cessato"));
		newOrg.setCessato(Integer.parseInt(context.getRequest().getParameter("cessato")));
		if(newOrg.getCessazione()){
			newOrg.setCessato(context.getRequest().getParameter("cessato"));
			newOrg.setCessato(Integer.parseInt(context.getRequest().getParameter("cessato")));		
		}
		
		//PAESE DI PROVENIENZA
		String provenienza = context.getRequest().getParameter("provenienza");
		if (provenienza != null && provenienza.equals("ESTERO"))
			newOrg.setIdNazione( context.getRequest().getParameter("country"));
		
		newOrg.setCity_legale_rapp(context.getRequest().getParameter(
				"city_legale_rapp"));
		newOrg.setProv_legale_rapp(context.getRequest().getParameter(
				"prov_legale_rapp"));
		newOrg.setAddress_legale_rapp(context.getRequest().getParameter(
				"address_legale_rapp"));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIp_entered(ip);
		if(context.getParameter("flag_vendita")!= null && context.getParameter("flag_vendita").equalsIgnoreCase("on"))
	    	newOrg.setFlagVenditaCanali(true);
		else
			newOrg.setFlagVenditaCanali(false);
		
		if(context.getParameter("no_piva")!= null && context.getParameter("no_piva").equalsIgnoreCase("on"))
	    	newOrg.setNo_piva(true);
		else
			newOrg.setNo_piva(false);
		
		
		newOrg.setDomicilioDigitale(context.getRequest().getParameter("domicilioDigitale"));
		newOrg.setIp_modified(ip);
		try {
			db = this.getConnection(context);

			String[] id_codici_ateco_1 = context.getRequest()
					.getParameterValues("codici_sel");
			
			String[] id_masterlist = context.getRequest()
					.getParameterValues("codici_sel");
			
			int[] id_codici_ateco = new int[11];
			//R.M
			int[] id_attivita_masterlist = new int[11];
			if (id_codici_ateco_1 == null) {
				id_codici_ateco[0] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_principale"));
				id_codici_ateco[1] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_1"));
				id_codici_ateco[2] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_2"));
				id_codici_ateco[3] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_3"));
				id_codici_ateco[4] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_4"));
				id_codici_ateco[5] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_5"));
				id_codici_ateco[6] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_6"));
				id_codici_ateco[7] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_7"));
				id_codici_ateco[8] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_8"));
				id_codici_ateco[9] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_9"));
				id_codici_ateco[10] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_10"));
				context.getRequest().setAttribute("id_codici_ateco",
						id_codici_ateco);
				//R.M
				if(context.getRequest().getParameter("id_attivita_masterlist") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist").equals("")) {
					
					id_attivita_masterlist[0] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_1") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_1").equals("")) {
					
					id_attivita_masterlist[1] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_1"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_2") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_2").equals("")) {
					
					id_attivita_masterlist[2] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_2"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_3") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_3").equals("")) {
					
					id_attivita_masterlist[3] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_3"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_4") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_4").equals("")) {
					
					id_attivita_masterlist[4] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_4"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_5") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_5").equals("")) {
					
					id_attivita_masterlist[5] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_5"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_6") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_6").equals("")) {
					
					id_attivita_masterlist[6] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_6"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_7") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_7").equals("")) {
					
					id_attivita_masterlist[7] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_7"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_8") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_8").equals("")) {
					
					id_attivita_masterlist[8] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_8"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_9") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_9").equals("")) {
					
					id_attivita_masterlist[9] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_9"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_10") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_10").equals("")) {
					
					id_attivita_masterlist[10] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_10"));
				}
				
				
				
				context.getRequest().setAttribute("id_attivita_masterlist",
						id_attivita_masterlist);
				
			} else {
				int indice = 0;
				int i=0;
				for (String a : id_codici_ateco_1) {
					id_codici_ateco[indice] = Integer.parseInt(a);
					indice++;
				}
				
				for (String b : id_masterlist) {
					id_attivita_masterlist[i] = Integer.parseInt(b);
					i++;
				}
			}

			// aggiunto da Stany
			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SiteList", siteList);

			

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
			//     

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			

			// set the name to namelastfirstmiddle if individual
			
				// don't want to populate the addresses, etc. if this is an
				// individual account
				newOrg.setIsIndividual(false);
				newOrg.setRequestItems(context);



			if (this.getUserSiteId(context) != -1) {
				// Set the site Id of the account to be equal to the site Id of
				// the user
				// for a new account
				if (newOrg.getId() == -1) {
					newOrg.setSiteId(this.getUserSiteId(context));
				} else {
					// Check whether the user has access to update the
					// organization
					if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
						return ("PermissionError");
					}
				}
			}

			isValid = this.validateObject(context, db, newOrg);

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

			if (context.getRequest().getParameter("TipoLocale1") != null)
				newOrg.setTipoLocale(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale1")));
			if (context.getRequest().getParameter("TipoLocale2") != null)
				newOrg.setTipoLocale2(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale2")));

			if (context.getRequest().getParameter("TipoLocale3") != null)
				newOrg.setTipoLocale3(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale3")));

			if (isValid) {
				recordInserted = newOrg.insert(db,context);

			}
			if (recordInserted) {
				Organization temp = new Organization(db, newOrg.getOrgId());

				if (memorizza_codici_ateco(context, newOrg.getOrgId(),
						id_codici_ateco, id_attivita_masterlist,db) == null)

					temp.setModifiedBy(getUserId(context));
				org.aspcfs.modules.accounts.actions.Accounts.generaCodice(db,
						temp.getId(), false);
				insertedOrg = new Organization(db, newOrg.getOrgId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);

				PreparedStatement stat_veicoli = db
						.prepareStatement("select count(*) as numero from distributori_automatici where org_id="
								+ newOrg.getOrgId());
				ResultSet rs_veicoli = stat_veicoli.executeQuery();
				int numeroDistributori = 0;
				if (rs_veicoli.next())
					numeroDistributori = rs_veicoli.getInt(1);

				numeroDistributori = (numeroDistributori / 15) + 1;
				context.getRequest().setAttribute("numeroDistributori",
						numeroDistributori);
				context.getSession().setAttribute("orgIdf5",
						insertedOrg.getOrgId());
				addRecentItem(context, newOrg);

			}

			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD",
					"Accounts.do?command=Details");
			context.getRequest().setAttribute("org_cod",
					"&orgId=" + newOrg.getOrgId());

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			context.getRequest().setAttribute("OrgAdd", newOrg);
			
			 /*PrintWriter out=null;
			try {
				out = context.getResponse().getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    out.println("<html><body>");
			    out.println("<script type=\"text/javascript\">");
			    out.println("var popwin = window.open(\"/gisa_nt/errors/error_system.jsp\")");
			    out.println("window.parent.location.href=\"Account.do?command=Add\"");
			    out.println("</script>");
			    out.println("</body></html>");
			  
			    return "-none-";*/
			
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

	/**
	 * Update: Updates the Organization table to reflect user-entered
	 * changes/modifications to the currently selected Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 */
	public String executeCommandUpdate(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "accounts-accounts-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setTypeList(context.getRequest().getParameterValues(
				"selectedList"));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setVoltura(context.getRequest().getParameter("voltura"));
		newOrg.setDataPresentazione(context.getRequest().getParameter(
				"dataPresentazione"));
		newOrg.setCity_legale_rapp(context.getRequest().getParameter(
				"city_legale_rapp"));
		newOrg.setProv_legale_rapp(context.getRequest().getParameter(
				"prov_legale_rapp"));
		newOrg.setDomicilioDigitale(context.getRequest().getParameter("domicilioDigitale"));
		newOrg.setAddress_legale_rapp(context.getRequest().getParameter(
				"address_legale_rapp"));
		
		//PAESE DI PROVENIENZA
		String provenienza = context.getRequest().getParameter("provenienza");
		if (provenienza != null && provenienza.equals("ESTERO"))
			newOrg.setIdNazione( context.getRequest().getParameter("country"));
		else 
			newOrg.setIdNazione(106); //ITALIA
		
		if(context.getParameter("flag_vendita")!= null && context.getParameter("flag_vendita").equalsIgnoreCase("on"))
	    	newOrg.setFlagVenditaCanali(true);
		else
			newOrg.setFlagVenditaCanali(false);
		
		if(context.getParameter("no_piva")!= null && context.getParameter("no_piva").equalsIgnoreCase("on"))
	    	newOrg.setNo_piva(true);
		else
			newOrg.setNo_piva(false);

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIp_entered(ip);
		newOrg.setIp_modified(ip);
		try {
			db = this.getConnection(context);
			// set the name to namelastfirstmiddle if individual

			if (context.getRequest().getParameter("aggiorna_voltura") != null) {
				PreparedStatement pst = db
						.prepareStatement(" select ticketid "
								+ " from ticket where org_id = ? and tipologia = 4 and entered in (select min(entered) "
								+ " from ticket where  org_id = ? and tipologia = 4) and assigned_date is null"
								+ " group by assigned_Date , entered,modified,ticketid,operazione   order by entered desc");
				pst.setInt(1, newOrg.getOrgId());
				pst.setInt(2, newOrg.getOrgId());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					Ticket voltura_iniziale = new Ticket(db, rs.getInt(1));
					voltura_iniziale.setAssignedDate(context.getRequest()
							.getParameter("dataPresentazione"));
					voltura_iniziale.update_data_voltura_iniziale(db);
				}

			}
			String[] id_codici_ateco_1 = context.getRequest()
					.getParameterValues("codici_sel");
			int[] id_codici_ateco = new int[11];
			if (id_codici_ateco_1 == null) {
				id_codici_ateco[0] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_principale"));
				id_codici_ateco[1] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_1"));
				id_codici_ateco[2] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_2"));
				id_codici_ateco[3] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_3"));
				id_codici_ateco[4] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_4"));
				id_codici_ateco[5] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_5"));
				id_codici_ateco[6] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_6"));
				id_codici_ateco[7] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_7"));
				id_codici_ateco[8] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_8"));
				id_codici_ateco[9] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_9"));
				id_codici_ateco[10] = Integer.parseInt(context.getRequest()
						.getParameter("id_rel_10"));
				context.getRequest().setAttribute("id_codici_ateco",
						id_codici_ateco);
			} else {
				int indice = 0;
				for (String a : id_codici_ateco_1) {
					id_codici_ateco[indice] = Integer.parseInt(a);
					indice++;
				}
			}
			
			//R.M
			String[] id_masterlist = context.getRequest()
					.getParameterValues("codici_sel");
			int[] id_attivita_masterlist= new int[11];
			if (id_codici_ateco_1 == null) {
				if(context.getRequest().getParameter("id_attivita_masterlist") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist").equals("")) {
					
					id_attivita_masterlist[0] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_1") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_1").equals("")) {
					
					id_attivita_masterlist[1] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_1"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_2") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_2").equals("")) {
					
					id_attivita_masterlist[2] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_2"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_3") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_3").equals("")) {
					
					id_attivita_masterlist[3] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_3"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_4") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_4").equals("")) {
					
					id_attivita_masterlist[4] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_4"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_5") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_5").equals("")) {
					
					id_attivita_masterlist[5] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_5"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_6") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_6").equals("")) {
					
					id_attivita_masterlist[6] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_6"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_7") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_7").equals("")) {
					
					id_attivita_masterlist[7] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_7"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_8") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_8").equals("")) {
					
					id_attivita_masterlist[8] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_8"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_9") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_9").equals("")) {
					
					id_attivita_masterlist[9] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_9"));
				}
				if(context.getRequest().getParameter("id_attivita_masterlist_10") != null && 
						!context.getRequest().getParameter("id_attivita_masterlist_10").equals("")) {
					
					id_attivita_masterlist[10] = Integer.parseInt(context.getRequest()
							.getParameter("id_attivita_masterlist_10"));
				}
			
				
				
			
				context.getRequest().setAttribute("id_attivita_masterlist",
						id_attivita_masterlist);
			} else {
				int b = 0;
				for (String c : id_masterlist) {
					id_attivita_masterlist[b] = Integer.parseInt(c);
					b++;
				}
			}
			
			
			if (aggiorna_codici_ateco(context, newOrg.getId(), id_codici_ateco, id_attivita_masterlist, db) == null)
				logger.info("ATTENZIONE! Si e' verificata una eccezione nella memorizzazione dei codici ateco....");

		
		
				// don't want to populate the addresses, etc. if this is an
				// individual account
				newOrg.setIsIndividual(false);
				newOrg.setRequestItems(context);
				
			
			if (context.getRequest().getParameter("TipoLocale") != null)
				newOrg.setTipoLocale(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale")));
			if (context.getRequest().getParameter("TipoLocale2") != null)
				newOrg.setTipoLocale2(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale2")));

			if (context.getRequest().getParameter("TipoLocale3") != null)
				newOrg.setTipoLocale3(Integer.parseInt(context.getRequest()
						.getParameter("TipoLocale3")));

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);

			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) {
				org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress) it
						.next();
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
			}// Fine aggiunta

			if (isValid) {
				if (newOrg.getVoltura()) {
					org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
					voltura.setId(Integer.parseInt(newOrg.getIdVoltura(db,
							newOrg.getOrgId())));
					voltura.setModifiedBy(getUserId(context));
					voltura.setEnteredBy(getUserId(context));
					voltura.setName(newOrg.getName());
					voltura.setBanca(newOrg.getBanca());
					if (newOrg.getTitoloRappresentante() > -1) {
						voltura.setTitoloRappresentante(newOrg
								.getTitoloRappresentante());
					}
					if (newOrg.getCodiceFiscaleRappresentante() != null
							&& !newOrg.getCodiceFiscaleRappresentante().equals(
									"")) {
						voltura.setCodiceFiscaleRappresentante(newOrg
								.getCodiceFiscaleRappresentante());
					}
					if (newOrg.getNomeRappresentante() != null
							&& !newOrg.getNomeRappresentante().equals("")) {
						voltura.setNomeRappresentante(newOrg
								.getNomeRappresentante());
					}
					if (newOrg.getCognomeRappresentante() != null) {
						voltura.setCognomeRappresentante(newOrg
								.getCognomeRappresentante());
					}
					if (newOrg.getDataNascitaRappresentante() != null) {
						voltura.setDataNascitaRappresentante(newOrg
								.getDataNascitaRappresentante());
					}
					if (newOrg.getLuogoNascitaRappresentante() != null) {
						voltura.setLuogoNascitaRappresentante(newOrg
								.getLuogoNascitaRappresentante());
					}
					if (newOrg.getEmailRappresentante() != null
							&& !newOrg.getEmailRappresentante().equals("")) {
						voltura.setEmailRappresentante(newOrg
								.getEmailRappresentante());
					}
					if (newOrg.getTelefonoRappresentante() != null
							&& !newOrg.getTelefonoRappresentante().equals("")) {
						voltura.setTelefonoRappresentante(newOrg
								.getTelefonoRappresentante());
					}
					if (newOrg.getFax() != null && !newOrg.getFax().equals("")) {
						voltura.setFax(newOrg.getFax());
					}
					voltura.setAssignedDate(newOrg.getDataPresentazione());
					
					voltura.update(db);
					resultCount = newOrg.update(db,context);
					voltura.queryRecord(db, Integer.parseInt(newOrg
							.getIdVoltura(db, newOrg.getOrgId())));
					context.getRequest().setAttribute("Voltura", voltura);

				} else {
					resultCount = newOrg.update(db,context);
					// String prova =
					// context.getRequest().getParameter("address1state");
					// newOrg.setState(prova);
				}
			}
			if (resultCount == 1) {
				processUpdateHook(context, oldOrg, newOrg);
				// if this is an individual account, populate and update the
				// primary contact
				
			

				LookupList IstatList = new LookupList(db, "lookup_codistat");
				IstatList.addItem(-1, getSystemStatus(context).getLabel(
						"calendar.none.4dashes"));
				context.getRequest().setAttribute("IstatList", IstatList);

			

			}
		} catch (Exception errorMessage) {

			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");
		if (resultCount == -1 || !isValid) {
			return (executeCommandModify(context));
		} else if (resultCount == 1) {
			if (context.getRequest().getParameter("return") != null
					&& context.getRequest().getParameter("return").equals(
							"list")) {
				return (executeCommandSearch(context));
			} else if (context.getRequest().getParameter("return") != null
					&& context.getRequest().getParameter("return").equals(
							"dashboard")) {
				return (executeCommandDashboard(context));
			} else if (context.getRequest().getParameter("return") != null
					&& context.getRequest().getParameter("return").equals(
							"Calendar")) {
				if (context.getRequest().getParameter("popup") != null) {
					return ("PopupCloseOK");
				}
			} else {
				return ("UpdateOK");
			}
		} else {

			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
		return ("UpdateOK");
	}

	
	/**
	 * Update: Updates the Organization table to reflect user-entered
	 * changes/modifications to the currently selected Account
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 */
	public String executeCommandUpdateCatRischio(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "account-tipochecklist-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		String org_id = context.getRequest().getParameter("orgId");
		String idC = context.getRequest().getParameter("idC");

		String account_size = context.getRequest().getParameter("accountSize");
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			// set the name to namelastfirstmiddle if individual

			newOrg = new Organization(db, Integer.parseInt(org_id));
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
		context.getRequest().setAttribute("idControllo", idC);

		addModuleBean(context, "View Accounts", "Modify Account");

		return ("UpdateCatRischioOK");
	}

	/**
	 * Delete: Deletes an Account from the Organization table
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 */
	public String executeCommandDelete(ActionContext context)
			throws SQLException {
		if (!hasPermission(context, "accounts-accounts-delete")) {
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
			if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
				return ("PermissionError");
			}
			
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
						"Accounts.do?command=Search");
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
			logger.severe("Eccezione" + errorMessage);

			context
					.getRequest()
					.setAttribute(
							"actionError",
							systemStatus
									.getLabel("object.validation.actionError.accountDeletion"));
			context.getRequest().setAttribute("refreshUrl",
					"Accounts.do?command=Search");
			return ("DeleteError");
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException
	 */
	public String executeCommandTrash(ActionContext context)
			throws SQLException {
		if (!hasPermission(context, "accounts-accounts-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
	   Connection db = null;
		try {
			

		      db = this.getConnection(context);
		      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		      String note = context.getRequest().getParameter("note");

		      //check permission to record
		      if (!isRecordAccessPermitted(context, db, orgId)) {
		        return ("PermissionError");
		      }
		    
		      // NOTE: these may have different options later
		      recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
		  
		    if (recordUpdated) {
			
				LineeAttivita.delete_by_orgId(orgId, this.getUserId(context), db);

						}
		} catch (Exception e) {

			context.getRequest().setAttribute("refreshUrl",
					"Accounts.do?command=Search");
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute("refreshUrl",
					"Accounts.do?command=Search");
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
	 * @throws IndirizzoNotFoundException 
	 */
	public String executeCommandRestore(ActionContext context) throws IndirizzoNotFoundException {
		if (!hasPermission(context, "accounts-accounts-delete")) {
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
					"Accounts.do?command=Search");
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
		if (!hasPermission(context, "accounts-accounts-edit")) {
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
		if (!hasPermission(context, "accounts-accounts-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		HtmlDialog htmlDialog = new HtmlDialog();
		int orgId = -1;
		SystemStatus systemStatus = this.getSystemStatus(context);
		if (context.getRequest().getParameter("id") != null) {
			 orgId = Integer.parseInt(context.getRequest().getParameter("id"));
		}
		try {
		      db = this.getConnection(context);
		      //check permission to record
		      if (!AccountsUtil.isCancellabile(db, orgId)){
		    	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
		          htmlDialog.addMessage("<br/>");
		          htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
		      }
		      else {
		      htmlDialog.addMessage("<form action=\"Accounts.do?command=Trash&auto-populate=true\" method=\"post\">");
		      htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
		      htmlDialog.addMessage("<br/>");
		      htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
		      htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
		      htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+orgId+"\"/>");
		      htmlDialog.addMessage("</form>");
		      }
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
		if (!hasPermission(context, "accounts-accounts-edit")) {
			return ("PermissionError");
		}
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
			ticList.setOrgId(tempid);
			ticList.buildList(db);
			if (ticList.size() != 0) {
				context.getRequest().setAttribute("Volture", "si");
			} else {
				context.getRequest().setAttribute("Volture", "no");
			}
			 newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
			if (newOrg.getId() == -1) {
				newOrg = new Organization(db, tempid);

				// In fase di modifica
				Iterator it_coords = newOrg.getAddressList().iterator();
				while (it_coords.hasNext()) {

					org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress) it_coords
							.next();
					if (thisAddress.getLatitude() != 0
							&& thisAddress.getLongitude() != 0) {
						String spatial_coords[] = null;
						spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()), db);
//						if (Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
//						{
//							AjaxCalls ajaxCall = new AjaxCalls();
//							String[] coordinate = ajaxCall.getCoordinate(
//									thisAddress.getStreetAddressLine1(),
//									thisAddress.getCity(), thisAddress
//											.getState(), thisAddress.getZip(),
//									"" + thisAddress.getLatitude(), ""
//											+ thisAddress.getLongitude(), "");
//							thisAddress.setLatitude(coordinate[1]);
//							thisAddress.setLongitude(coordinate[0]);
//						} else {
							thisAddress.setLatitude(spatial_coords[0]);
							thisAddress.setLongitude(spatial_coords[1]);
						//}

					}
				}

				
			} else {
				newOrg.setTypeListToTypes(db);
			}
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);
			
			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			
			

			LookupList stageList = new LookupList(db, "lookup_account_stage");
			stageList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("StageList", stageList);

			
			
			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);
		
			LookupList countryList = new LookupList(db, "lookup_nazioni");
			// SOLO PAESI EUROPEI
			countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
			// ITALIA NON SELEZIONABILE
			countryList.removeElementByValue("Italia");
			countryList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("CountryList", countryList);
			
			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			// Carico nel context la lookup che unisce codici ateco e linee di
			// attivita'...
			carica_lookup_ateco_nel_context(context, orgid, newOrg
					.getCodiceFiscaleCorrentista(), db);


		
			// inserito da Francesco
			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

		

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

		
			// if this is an individual account
		

			// Make the StateSelect and CountrySelect drop down menus available
			// in the request.
			// This needs to be done here to provide the SystemStatus to the
			// constructors, otherwise translation is not possible
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			
			CountrySelect countrySelect = new CountrySelect(systemStatus);
			context.getRequest().setAttribute("CountrySelect", countrySelect);
			context.getRequest().setAttribute("systemStatus", systemStatus);

			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());
			if (newOrg.getVoltura()) {
				org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
				voltura.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg
						.getId())));
				voltura.queryRecord(db, Integer.parseInt(newOrg.getIdVoltura(
						db, newOrg.getId())));
				if ((voltura != null)) {

					context.getRequest().setAttribute("Voltura", voltura);
				}
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			logger.warning("Si e verificato un Errore nel dettaglio imprese "+e.getMessage());
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getRequest().getParameter("popup") != null) {
			return ("PopupModifyOK");
		} else {
			return ("ModifyOK");
		}
	}
	
	
	
	
	public String executeCommandClona(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
			ticList.setOrgId(tempid);
			ticList.buildList(db);
			if (ticList.size() != 0) {
				context.getRequest().setAttribute("Volture", "si");
			} else {
				context.getRequest().setAttribute("Volture", "no");
			}

			 newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
			
			
			
			if (newOrg.getId() == -1) {
				newOrg = new Organization(db, tempid);

				// In fase di modifica
				Iterator it_coords = newOrg.getAddressList().iterator();
				while (it_coords.hasNext()) {

					org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress) it_coords
							.next();
					if (thisAddress.getLatitude() != 0
							&& thisAddress.getLongitude() != 0) {
						String spatial_coords[] = null;
						spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()), db);
						if (1==2 && Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
						{
							AjaxCalls ajaxCall = new AjaxCalls();
							String[] coordinate = ajaxCall.getCoordinate(
									thisAddress.getStreetAddressLine1(),
									thisAddress.getCity(), thisAddress
											.getState(), thisAddress.getZip(),
									"" + thisAddress.getLatitude(), ""
											+ thisAddress.getLongitude(), "");
							thisAddress.setLatitude(coordinate[1]);
							thisAddress.setLongitude(coordinate[0]);
						} else {
							thisAddress.setLatitude(spatial_coords[0]);
							thisAddress.setLongitude(spatial_coords[1]);
						}

					}
				}

				
			} else {
				newOrg.setTypeListToTypes(db);
			}
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}
		
			
			
			
			OrganizationAddressList lista =  newOrg.getAddressList();
			Iterator<OrganizationAddress> it = lista.iterator();
			int numLoc = 0 ;
			while (it.hasNext())
			{
				
				OrganizationAddress temp = it.next();
				if (temp.getType()==1)
					context.getRequest().setAttribute("AddressSedeLegale", temp);
				if (temp.getType()==5)
					context.getRequest().setAttribute("AddressSedeOperativa", temp);
				if (temp.getType()==6 && numLoc==0)
				{
					numLoc ++ ;
					context.getRequest().setAttribute("AddressLocale1", temp);
				}
				if (temp.getType()==6 && numLoc==1)
				{
					numLoc ++ ;
					context.getRequest().setAttribute("AddressLocale2", temp);
				}
				if (temp.getType()==6 && numLoc==2)
				{
					numLoc ++ ;
					context.getRequest().setAttribute("AddressLocale3", temp);
				}
				
				
				if (temp.getType()==7 )
				{
					context.getRequest().setAttribute("AddressSedeMobile", temp);
				}
			}
			
			
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);
			

			
			LookupList sourceList = new LookupList(db, "lookup_contact_source");
			sourceList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("SourceList", sourceList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			// Carico nel context la lookup che unisce codici ateco e linee di
			// attivita'...
			carica_lookup_ateco_nel_context(context, orgid, newOrg
					.getCodiceFiscaleCorrentista(), db);

			

			
		

			// inserito da Francesco
			LookupList categoriaRischioList = new LookupList(db,
					"lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList",
					categoriaRischioList);

		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

	

			

			// Make the StateSelect and CountrySelect drop down menus available
			// in the request.
			// This needs to be done here to provide the SystemStatus to the
			// constructors, otherwise translation is not possible
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			
			CountrySelect countrySelect = new CountrySelect(systemStatus);
			context.getRequest().setAttribute("CountrySelect", countrySelect);
			context.getRequest().setAttribute("systemStatus", systemStatus);

			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute(
					"User")).getSiteId());
			if (newOrg.getVoltura()) {
				org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
				voltura.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg
						.getOrgId())));
				voltura.queryRecord(db, Integer.parseInt(newOrg.getIdVoltura(
						db, newOrg.getOrgId())));
				if ((voltura != null)) {

					context.getRequest().setAttribute("Voltura", voltura);
				}
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			logger.warning("Si e verificato un Errore nel dettaglio imprese "+e.getMessage());
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getRequest().getParameter("popup") != null) {
			return "ClonaOK";
		} else {
			return  "ClonaOK";
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
		if (!(hasPermission(context, "accounts-accounts-folders-view"))) {
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
	 * Gestione delle stampe nel modulo Imprese 
	 *
	 * @param context
	 * 
	 */

	public static void generaCodice(Connection db, int org_id,
			boolean tramiteDIA) throws SQLException {

		Organization thisOrganization = new Organization(db, org_id);
		if (thisOrganization.getTipoDest().equalsIgnoreCase("Es. Commerciale")) {
//			String city2 = thisOrganization.getCity2(db, thisOrganization.getOrgId());
//			city2 = (city2 == null) ? (null) : (city2.toUpperCase());
			Comuni comune = new Comuni(db, org_id, 5);
			if (comune.getCodice()!= null)  
				thisOrganization.generaCodice(db, comune.getCodice(), tramiteDIA);
		} else {
			if (thisOrganization.getTipoDest().equalsIgnoreCase("distributori")) {
//				String city2 = thisOrganization.getCity();
//				city2 = (city2 == null) ? (null) : (city2.toUpperCase());
				
				Comuni comune = new Comuni(db, org_id, 1);
				if (comune.getCodice()!= null)
				thisOrganization.generaCodice(db, comune.getCodice(),tramiteDIA);

			} else {
//				String city3 = thisOrganization.getCity3(db, thisOrganization.getOrgId());
//				city3 = (city3 == null) ? (null) : (city3.toUpperCase());
				Comuni comune = new Comuni(db, org_id, 7);
				if (comune.getCodice()!= null)
					thisOrganization.generaCodice(db, comune.getCodice(),tramiteDIA);

			}

		}

	}


	public LookupList costruisci_lookup_rel_ateco_linea_attivita(
			ActionContext context,Connection db) {
		LookupList ret = new LookupList();
//		Connection db = null;

		try {
//			db = getConnection(context);
			ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita
					.load_all_rel_ateco_linee_attivita(db);

			for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
				if (!rel_ateco_linea.getLinea_attivita().isEmpty())
					ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea
							.getCodice_istat()
							+ " : "
							+ rel_ateco_linea.getCategoria()
							+ " - "
							+ rel_ateco_linea.getLinea_attivita());
				else
					ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea
							.getCodice_istat()
							+ " : " + rel_ateco_linea.getCategoria());
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} //finally {
//			this.freeConnection(context, db);
//		}
		return ret;
	}

	public LookupList costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
			ActionContext context, String cod_ateco,Connection db) {
		LookupList ret = new LookupList();
//		Connection db = null;

		try {
//			db = getConnection(context);
			ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita
					.load_rel_ateco_linee_attivita_per_codice_istat(cod_ateco,
							db);

			for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
				if (!rel_ateco_linea.getLinea_attivita().isEmpty())
					ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea
							.getCategoria()
							+ " - " + rel_ateco_linea.getLinea_attivita());
				else
					ret.addItem(rel_ateco_linea.getId(), rel_ateco_linea
							.getCategoria());
			}

			if (ret.size() == 0) {
				ret.addItem(-1, "-- Selezionare prima il codice Ateco --");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} //finally {
//			this.freeConnection(context, db);
//		}
		return ret;
	}

	public String aggiorna_codici_ateco(ActionContext context, int orgId,
			int[] id_codici_ateco,  int[] id_attivita_masterlist, Connection db) throws SQLException {
//		Connection db = null;
		String ret = "memorizza_codici_atecoOK";

		try {
//			db = getConnection(context);

			if (orgId != -1)
				LineeAttivita.delete_by_orgId_aggiorna(orgId, this.getUserId(context), db);

			boolean primario = false ;
			for (int i = 0; i <= (id_codici_ateco.length - 1); i++)
			{
				if(i==0)
					primario = true ;
				else 
					primario = false ;
				LineeAttivita linea_corrente = LineeAttivita.load_linee_attivita_per_org_id_id_rel_ateco(""+orgId, ""+id_codici_ateco[i], id_attivita_masterlist[i], db,primario);
				if (linea_corrente != null)
				{
					LineeAttivita.ripristina(linea_corrente.getId(), id_attivita_masterlist[i], db);
				}
				else
				{
				if (id_codici_ateco[i] != -1) {
					LineeAttivita linea = new LineeAttivita();
					if (i == 0)
						linea.setPrimario(true);
					else
						linea.setPrimario(false);
					linea.setId_rel_ateco_attivita(id_codici_ateco[i]);
					linea.setOrg_id(orgId);
					linea.setId_attivita_masterlist(id_attivita_masterlist[i]);
					linea.setEntered(new Timestamp(System.currentTimeMillis()));
					linea.setEntered_by(this.getUserId(context));
					linea.setModified(new Timestamp(System.currentTimeMillis()));
					linea.setModified_by(this.getUserId(context));
					linea.store(db);
				}
				}
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} 
//		finally {
//			this.freeConnection(context, db);
//		}
		return ret;
	}

	public String memorizza_codici_ateco(ActionContext context, int orgId,
			int[] id_codici_ateco, int[] id_attivita_masterlist, Connection  db) throws SQLException {
//		Connection db = null;
		String ret = "memorizza_codici_atecoOK";

		try {
//			db = getConnection(context);

			if (orgId != -1)
				LineeAttivita.delete_by_orgId(orgId, this.getUserId(context),
						db);

			for (int i = 0; i <= (id_codici_ateco.length - 1); i++)
				if (id_codici_ateco[i] != -1) {
					LineeAttivita linea = new LineeAttivita();
					if (i == 0)
						linea.setPrimario(true);
					else
						linea.setPrimario(false);
					linea.setId_rel_ateco_attivita(id_codici_ateco[i]);
					linea.setId_attivita_masterlist(id_attivita_masterlist[i]);
					linea.setOrg_id(orgId);
					linea.setEntered(new Timestamp(System.currentTimeMillis()));
					linea.setEntered_by(this.getUserId(context));
					linea
							.setModified(new Timestamp(System
									.currentTimeMillis()));
					linea.setModified_by(this.getUserId(context));
					linea.store(db);
				}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} 
//		finally {
//			this.freeConnection(context, db);
//		}
		return ret;
	}


	public void carica_lookup_ateco_nel_context(ActionContext context,
			String orgid, String codice_istat_da_impresa, Connection db) {
		LookupList rel_ateco_linea_attivita_List = costruisci_lookup_rel_ateco_linea_attivita(context,db);
		context.getRequest().setAttribute("rel_ateco_linea_attivita_List",
				rel_ateco_linea_attivita_List);
		ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita
				.load_all_rel_ateco_linee_attivita(db);
		context.getRequest().setAttribute("all_rel_ateco_linee_attivita",
				all_rel_ateco_linee_attivita);
		LineeAttivita linea_attivita_principale = LineeAttivita
				.load_linea_attivita_principale_per_org_id(orgid, db);
		context.getRequest().setAttribute("linea_attivita_principale",
				linea_attivita_principale);
		ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita
				.load_linee_attivita_secondarie_per_org_id(orgid, db);
		context.getRequest().setAttribute("linee_attivita_secondarie",
				linee_attivita_secondarie);
		LookupList List_id_rel_principale = null;

		if (linea_attivita_principale != null) {
			List_id_rel_principale = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linea_attivita_principale.getCodice_istat(),db);
		} else {
			List_id_rel_principale = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, codice_istat_da_impresa,db);

		}

		LookupList List_id_rel_1 = new LookupList();
		LookupList List_id_rel_2 = new LookupList();
		LookupList List_id_rel_3 = new LookupList();
		LookupList List_id_rel_4 = new LookupList();
		LookupList List_id_rel_5 = new LookupList();
		LookupList List_id_rel_6 = new LookupList();
		LookupList List_id_rel_7 = new LookupList();
		LookupList List_id_rel_8 = new LookupList();
		LookupList List_id_rel_9 = new LookupList();
		LookupList List_id_rel_10 = new LookupList();

		if (linee_attivita_secondarie.size() > 0)
			List_id_rel_1 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(0).getCodice_istat(),db);
		else
			List_id_rel_1
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 1)
			List_id_rel_2 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(1).getCodice_istat(),db);
		else
			List_id_rel_2
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 2)
			List_id_rel_3 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(2).getCodice_istat(),db);
		else
			List_id_rel_3
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 3)
			List_id_rel_4 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(3).getCodice_istat(),db);
		else
			List_id_rel_4
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 4)
			List_id_rel_5 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(4).getCodice_istat(),db);
		else
			List_id_rel_5
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 5)
			List_id_rel_6 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(5).getCodice_istat(),db);
		else
			List_id_rel_6
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 6)
			List_id_rel_7 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(6).getCodice_istat(),db);
		else
			List_id_rel_7
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 7)
			List_id_rel_8 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(7).getCodice_istat(),db);
		else
			List_id_rel_8
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 8)
			List_id_rel_9 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(8).getCodice_istat(),db);
		else
			List_id_rel_9
					.addItem(-1, "-- Selezionare prima il codice Ateco --");

		if (linee_attivita_secondarie.size() > 9)
			List_id_rel_10 = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(
					context, linee_attivita_secondarie.get(9).getCodice_istat(),db);
		else
			List_id_rel_10.addItem(-1,
					"-- Selezionare prima il codice Ateco --");

		context.getRequest().setAttribute("List_id_rel_principale",
				List_id_rel_principale);
		context.getRequest().setAttribute("List_id_rel_1 ", List_id_rel_1);
		context.getRequest().setAttribute("List_id_rel_2 ", List_id_rel_2);
		context.getRequest().setAttribute("List_id_rel_3 ", List_id_rel_3);
		context.getRequest().setAttribute("List_id_rel_4 ", List_id_rel_4);
		context.getRequest().setAttribute("List_id_rel_5 ", List_id_rel_5);
		context.getRequest().setAttribute("List_id_rel_6 ", List_id_rel_6);
		context.getRequest().setAttribute("List_id_rel_7 ", List_id_rel_7);
		context.getRequest().setAttribute("List_id_rel_8 ", List_id_rel_8);
		context.getRequest().setAttribute("List_id_rel_9 ", List_id_rel_9);
		context.getRequest().setAttribute("List_id_rel_10", List_id_rel_10);

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
