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
package org.aspcfs.modules.soa.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.soa.base.Organization;
import org.aspcfs.modules.soa.base.OrganizationList;
import org.aspcfs.modules.soa.base.SottoAttivita;
import org.aspcfs.modules.stabilimenti.base.ControlloDocumentale;
import org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico;
import org.aspcfs.modules.stabilimenti.base.PermessoVisibilitaStabilimenti;
import org.aspcfs.modules.stabilimenti.base.StatiStabilimenti;
import org.aspcfs.modules.soa.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

    return executeCommandDashboard(context);
  }
  
  

  
  public String executeCommandModifyStabilimento(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-view")) {
	      return ("PermissionError");
	    }
	    Connection   db           = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg       = null;
	    ArrayList    elencoAttivita = null;
	    try {
	    
	      db = this.getConnection(context);
	      newOrg = new Organization();
	      
	      newOrg = newOrg.load(context.getRequest().getParameter("account_number"), db);
	      
	      
	      LookupList categoriaList =Organization.getCategorieSoa(db, newOrg.getTipoSoa());
	      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("categoriaList", categoriaList);
	      
	      
	      ControlloDocumentale cd = new ControlloDocumentale();
	      cd.buildControlloDocumentale(db, newOrg.getOrgId());
	      context.getRequest().setAttribute("ControlloDocumentale", cd);
	      
	      /**
	       * se lo stato e' riconosciuto condizionato
	       */
	       
	    //Dopo l'inserimento riconverti
	      Iterator it_coords = newOrg.getAddressList().iterator();
	      while(it_coords.hasNext()){
	    	  
	    	  org.aspcfs.modules.soa.base.OrganizationAddress thisAddress = (org.aspcfs.modules.soa.base.OrganizationAddress) it_coords.next();
	    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
	    		  
	    		  String spatial_coords [] = null;
	        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
	        	
	      			  thisAddress.setLatitude(spatial_coords[0]);
	        		  thisAddress.setLongitude(spatial_coords[1]);
	      		 
	    	  }
	      }
	    	  
	    
	    
	      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
	      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
	      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("statoLab", statoLab);
	      
	      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
	      
	      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
	      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("impianto", impianto);
	      
	      LookupList IstatList = new LookupList(db, "lookup_codistat");
	      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("IstatList", IstatList);

	      LookupList lookup_prodotti = new LookupList(db, "lookup_prodotti_soa");
	      lookup_prodotti.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("LookupProdotti", lookup_prodotti);
	      
	      LookupList lookup_classificazione = new LookupList(db, "lookup_classificazione_stabilimenti");
	      lookup_classificazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("LookupClassificazione", lookup_classificazione);
	      
	    
	      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata");
	      imballataList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("imballataList", imballataList);
	      
	      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
	      tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
	      
	      elencoAttivita =  SottoAttivita.loadBySoa(newOrg.getOrgId(), db);
	      
	      /*org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
	      int AuditOrgId = newOrg.getOrgId();
	      audit.setOrgId(AuditOrgId);
	     
	      //audit.buildList(db);
	      */
	      
	      /*if( (audit.size() - 1)>=0){
	      
	    	  context.getRequest().setAttribute("Audit",audit.get(0) );
	      }*/
	      context.getRequest().setAttribute("elencoSottoAttivita", elencoAttivita);
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	  
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return "ModifyIstruttoriaOK";
	    
	  }
  
  
  
  

	public String executeCommandUpdatePregresso(ActionContext context) {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		try {
			db = this.getConnection(context);

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


			if(context.getParameter("addressId")!=null && ! "-1" .equalsIgnoreCase(context.getParameter("addressId")))
			{
				org.aspcfs.modules.stabilimenti.base.OrganizationAddress oa = new org.aspcfs.modules.stabilimenti.base.OrganizationAddress(db,Integer.parseInt(context.getParameter("addressId")));

				oa.setCity(context.getParameter("comune_sede_legale"));
				oa.setStreetAddressLine1(context.getParameter("indirizzo_sede_legale"));
				oa.setState(context.getParameter("provincia_sede_legale"));
				oa.setZip(context.getParameter("cap_sede_legale"));
				oa.setId(context.getParameter("addressId"));
				oa.setType(1);
				oa.setOrgId(newOrg.getOrgId());
				oa.update(db, user.getUserId());
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
	      Organization newOrg = new Organization (db,Integer.parseInt(context.getRequest().getParameter("orgId")));
	     
	      newOrg.setSottoattivitaList(SottoAttivita.loadBySoa(newOrg.getOrgId(), db));
	      
	    
	      
	      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
	      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("statoLab", statoLab);
	      
	      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
	      
	      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
	      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("impianto", impianto);
	      
	     LookupList IstatList = new LookupList(db, "lookup_codistat");
	      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("IstatList", IstatList);
	      
	   
	      //newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
	      newOrg.setSottoattivitaItem(context,db);
	    
	     
	     if(newOrg.getTipoSoa()==2)
	     {
	      PreparedStatement pst = db.prepareStatement("update organization set stato_istruttoria = 2 where org_id = ?;delete from soa_sottoattivita where id_soa = ?");
	      pst.setInt(1, newOrg.getOrgId());
	      pst.setInt(2, newOrg.getOrgId());
	      pst.execute();
	     }
	     else
	     {
	    	 PreparedStatement pst = db.prepareStatement("update organization set stato_istruttoria = 12 where org_id = ?;delete from soa_sottoattivita where id_soa = ?");
		      pst.setInt(1, newOrg.getOrgId());
		      pst.setInt(2, newOrg.getOrgId());
		      pst.execute();
	     }
	      
	  	Iterator saiterator = newOrg.getSottoattivitaList().iterator();
		while (saiterator.hasNext()) {
			SottoAttivita thisSa = (SottoAttivita) saiterator.next();
			thisSa.setId_soa(newOrg.getOrgId());
			//thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
			thisSa.store(db,context);
		}
		
		
		

		PreparedStatement pst2 = db.prepareStatement("select * from quesiti_controllo_documentale_stabilimenti where id_stabilimento= ?");
		pst2.setInt(1, newOrg.getOrgId());
		ResultSet rs2 = pst2.executeQuery();
		
		if (!rs2.next())
		{
		
		int id = DatabaseUtils.getNextSeqInt(db, context,"quesiti_controllo_documentale_stabilimenti","id");

		PreparedStatement pst_controllo_ins = db.prepareStatement("insert into quesiti_controllo_documentale_stabilimenti (id,id_stabilimento) values  (?,?)");
		pst_controllo_ins.setInt(1,id);
		pst_controllo_ins.setInt(2,newOrg.getOrgId());

		pst_controllo_ins.execute();

		PreparedStatement pst_controllo_doc = db.prepareStatement("insert into quesiti_risposte_controllo_documentale (id_quesito,id_quesiti_controllo_documentale_stabilimenti,risposta_asl,risposta_stap)  (" +
				"select id,?," +
				"case when competenza_asl = true then false "+
				"else " +
				"true " +
				"end as risp" +
				"" +
		",false from quesiti_controllo_documentale where enabled = true )");
		pst_controllo_doc.setInt(1,id);
		pst_controllo_doc.execute();
		}
		else
		{
			PreparedStatement pst_controllo_ins = db.prepareStatement("update  quesiti_controllo_documentale_stabilimenti set stato_asl = 0 , stato_stap = 0 where id_stabilimento = ?");
			
			pst_controllo_ins.setInt(1,newOrg.getOrgId());

			pst_controllo_ins.execute();
			
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
  
  public String executeCommandCondizionatiInScadenza(ActionContext context) {
		
	    if (!hasPermission(context, "soa-soa-view")) {
	      return ("PermissionError");
	    }
	    
	    int i = 0;
	 

	    //String sottoAttivita = getImpiantoSearch(categoria_id,context);

	    OrganizationList organizationList = new OrganizationList();
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    
	    
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfo");
	    searchListInfo.setLink("Soa.do?command=Search");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);

	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	    	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(-2, "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      
	      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
	    
	      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
	     
	      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
	      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("impianto", impianto);
	      
	      //inserito da Carmela
	      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
	      
	      
	      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
	      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("statoLab", statoLab);
	      
	      
	     
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        
	        UserBean user = (UserBean)context.getSession().getAttribute("User");
		    int idAsl = user.getSiteId();
	        ArrayList<SottoAttivita> listaSottoattivita = SottoAttivita.getSottoAttivitaCondizionateinScadenza( db,idAsl);
	        
	        
	        context.getRequest().setAttribute("OrgList", listaSottoattivita);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
	        tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
	        context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
	       
	       
	        //fetching criterea for account status (active, disabled or any)
	        
	      
	    } catch (Exception e) {
	      //Go through the SystemError process
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
	 

	    //String sottoAttivita = getImpiantoSearch(categoria_id,context);

	    OrganizationList organizationList = new OrganizationList();
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    
	    
	    PagedListInfo searchListInfo = this.getPagedListInfo(
	        context, "SearchOrgListInfo");
	    searchListInfo.setLink("Stabilimenti.do?command=Search");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);

	      //For portal usr set source as 'searchForm' explicitly since
	      //the search form is bypassed.
	      //temporary solution for page redirection for portal user.
	    	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      siteList.addItem(-2, "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      
	      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
	    
	      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
	     
	    
	      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
	      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("impianto", impianto);
	      
	      //inserito da Carmela
	      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
	      
	      
	      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
	      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("statoLab", statoLab);
	      
	      
	     
	        //Build the organization list
	        organizationList.setPagedListInfo(searchListInfo);
	        
	        UserBean user = (UserBean)context.getSession().getAttribute("User");
		    int idAsl = user.getSiteId();
	        ArrayList<SottoAttivita> listaSottoattivita = SottoAttivita.getSottoAttivitaSospeseinScadenza(db,idAsl);
	        
	        
	        context.getRequest().setAttribute("OrgList", listaSottoattivita);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	        
	        LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
	        tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
	        context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
	       
	       
	        //fetching criterea for account status (active, disabled or any)
	        
	      
	    } catch (Exception e) {
	      //Go through the SystemError process
	    	e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
		return "ScadenzarioSospesiOK";
	  }
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "soa-soa-vigilanza-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.assigned_date", "desc");
	    ticketListInfo.setLink(
	        "Soa.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit_soa");
	      
	      context.getRequest().setAttribute("TipoAudit", TipoAudit);
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione_soa");
	      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }
	      newOrg = new Organization(db, passedId);
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
	    if (!hasPermission(context, "soa-soa-cessazionevariazione-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "Soa.do?command=ViewCessazionevariazione&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
	      TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
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
  

  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "soa-soa-view"))) {
      return ("PermissionError");
    }
    
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      
  	org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	UserBean user = (UserBean) context.getSession()
			.getAttribute("User");
	ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c
			.buildList(db, user.getSiteId());

	LookupList comuniList = new LookupList(listaComuni);

	comuniList.addItem(-1, "");
	context.getRequest().setAttribute("ComuniList", comuniList);

      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
      context.getRequest().setAttribute("StatiStabilimenti", statiStabilimenti);
      
      PermessoVisibilitaStabilimenti permessi = new PermessoVisibilitaStabilimenti() ;
      PermessoVisibilitaStabilimenti permessiRuolo = permessi.getPermessiRuolo(db, user.getRoleId(),super.getSuffiso(context));
      
      context.getRequest().setAttribute("PermessiRuolo", permessiRuolo);
      LookupList categoriaList = new LookupList(db, "lookup_categoria_soa");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CategoriaList", categoriaList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      //tabella modificata
      

      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo orgListInfo = this.getPagedListInfo(
          context, "SearchOrgListInfo");
      orgListInfo.setCurrentLetter("");
      orgListInfo.setCurrentOffset(0);
      if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
        if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
          HashMap map = new HashMap();
          map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
          stateSelect.setPreviousStates(map);
        }
        context.getRequest().setAttribute("ContactStateSelect", stateSelect);
      }
      if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
        StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
        if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
          HashMap map = new HashMap();
          map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
          stateSelect.setPreviousStates(map);
        }
        context.getRequest().setAttribute("AccountStateSelect", stateSelect);
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


  /**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "soa-soa-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
    	if(context.getRequest().getAttribute("Inserito")!=null)
    		context.getRequest().setAttribute("Inserito","no");
      db = this.getConnection(context);
      
      
      /*
       * 1 : riconosciuti
       * 2 : registrati 
       * */
      String tipoSoa = context.getRequest().getParameter("tipoSoa");
      
      
      LookupList categoriaList =Organization.getCategorieSoa(db, Integer.parseInt(tipoSoa));
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CategoriaList", categoriaList);
      
      context.getRequest().setAttribute("tipoSoa", Integer.parseInt(tipoSoa));
    	  
      
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
     
      LookupList lookup_impianto = new LookupList(db,"lookup_impianto_soa");
      lookup_impianto.addItem(-1, "-SELEZIONA -");
      context.getRequest().setAttribute("impianto", lookup_impianto);
      
      LookupList categoria =Organization.getCategorieSoa(db, Integer.parseInt(tipoSoa));
      categoria.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("categoria", categoria);
      
      LookupList statoLab = new LookupList(db,"lookup_stato_lab");
      statoLab.addItem(-1, "-SELEZIONA -");

      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList tipoAutorizzazzione = new LookupList(db,"lookup_sottoattivita_tipoautorizzazione");
      tipoAutorizzazzione.addItem(-1, "-SELEZIONA -");
      context.getRequest().setAttribute("tipoAutorizzazzione", tipoAutorizzazzione);
      
     
      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList lookup_prodotti = new LookupList(db, "lookup_prodotti_soa");
      lookup_prodotti.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("LookupProdotti", lookup_prodotti);
      
      LookupList lookup_classificazione = new LookupList(db, "lookup_classificazione_stabilimenti");
      lookup_classificazione.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("LookupClassificazione", lookup_classificazione);
      
       
      
      //LOOKUP CATEGORIA AGGIUNTA DA GIUSEPPE
    

      Organization newOrg = (Organization) context.getFormBean();
      
  		newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());
      
  	  newOrg.setTipoSoa(Integer.parseInt(tipoSoa));
  		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
   
        
      
        context.getRequest().setAttribute("OrgDetails", newOrg);
        
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Add Account", "Accounts Add");
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
        return getReturn(context, "AddAccount");
    }

    return getReturn(context, "Add");
  }


  /**
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "soa-soa-view")) {
      return ("PermissionError");
    }
    Connection   db           = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg       = null;
    ArrayList    elencoAttivita = null;
    try {
    
      String temporgId = context.getRequest().getParameter("orgId");
      if (temporgId == null) {
        temporgId = (String) context.getRequest().getAttribute("orgId");
      }
      int tempid = Integer.parseInt(temporgId);
      db = this.getConnection(context);
      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
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
      //Dopo l'inserimento riconverti
      Iterator it_coords = newOrg.getAddressList().iterator();
      while(it_coords.hasNext()){
    	  
    	  org.aspcfs.modules.soa.base.OrganizationAddress thisAddress = (org.aspcfs.modules.soa.base.OrganizationAddress) it_coords.next();
    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
    		  
    		  String spatial_coords [] = null;
        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
        	
      			  thisAddress.setLatitude(spatial_coords[0]);
        		  thisAddress.setLongitude(spatial_coords[1]);
      		 
    	  }
      }
      
      //check whether or not the owner is an active User
      newOrg.setOwner(this.getUserId(context));
      newOrg.checkPregresso(db);
      LookupList statoLabImpianti = new LookupList(db, "lookup_stato_lab_impianti");
		
		statoLabImpianti.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("statoLabImpianti", statoLabImpianti);

      LookupList lookup_prodotti = new LookupList(db, "lookup_prodotti_soa");
      lookup_prodotti.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("LookupProdotti", lookup_prodotti); 
      
      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList categoriaR = new LookupList(db, "lookup_categoriarischio_soa");
      categoriaR.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("CategoriaRischioSoa", categoriaR);
           
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
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
      
      if(newOrg.getCodice1()!=null){
      codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
      context.getRequest().setAttribute("codice1", codice1);
      }else if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("codice2", codice2);
      }else if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("codice3", codice3);
      }else if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("codice4", codice4);
      }else if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("codice5", codice5);
      }else if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("codice6", codice6);
      }else if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("codice7", codice7);
      }else if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("codice8", codice8);
      }else if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("codice9", codice9);
      }else if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("codice10", codice10);
      }     
      
       
      LookupList categoriaList = new LookupList(db, "lookup_categoria_soa");
      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("categoriaList", categoriaList);
      
      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata_soa");
      imballataList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("imballataList", imballataList);
      
      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
      tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
      
      elencoAttivita =  SottoAttivita.loadBySoa(tempid, db);
      int AuditOrgId = newOrg.getOrgId();
     /* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      
      audit.setOrgId(AuditOrgId);
     
     // audit.buildList(db);
      
      if( (audit.size() - 1)>=0){
      
      context.getRequest().setAttribute("Audit",audit.get( audit.size() - 1) );
      }
      */
      org.aspcfs.modules.vigilanza.base.TicketList controllo = new org.aspcfs.modules.vigilanza.base.TicketList();
      int OrgId = newOrg.getOrgId();
      controllo.setOrgId(AuditOrgId);
     
      controllo.buildList(db);
      
      if( (controllo.size() - 1)>=0){
      
      context.getRequest().setAttribute("VigilanzaDetails",controllo.get( controllo.size() - 1) );
      }
      context.getRequest().setAttribute("elencoSottoAttivita", elencoAttivita);
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addRecentItem(context, newOrg);
    String action = context.getRequest().getParameter("action");
    if (action != null && action.equals("modify")) {
      //If user is going to the modify form
      addModuleBean(context, "Accounts", "Modify Account Details");
      return ("DetailsOK");
    } else {
      //If user is going to the detail screen
      addModuleBean(context, "View Accounts", "View Account Details");
      context.getRequest().setAttribute("OrgDetails", newOrg);
      return getReturn(context, "Details");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "soa-dashboard-view")) {
      if (!hasPermission(context, "soa-soa-view")) {
        return ("PermissionError");
      }
      //Bypass dashboard and search form for portal users
      if (isPortalUser(context)) {
        return (executeCommandSearch(context));
      }
      return (executeCommandSearchForm(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");
    CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
        "AccountsCalendarInfo");
    if (calendarInfo == null) {
      calendarInfo = new CalendarBean(
          this.getUser(context, this.getUserId(context)).getLocale());
      calendarInfo.addAlertType(
          "Accounts", "org.aspcfs.modules.soa.base.AccountsListScheduledActions", "Accounts");
      calendarInfo.setCalendarDetailsView("Accounts");
      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
    }

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.
    User thisRec = thisUser.getUserRecord();

    UserList shortChildList = thisRec.getShortChildList();
    UserList newUserList = thisRec.getFullChildList(
        shortChildList, new UserList());

    newUserList.setMyId(getUserId(context));
    newUserList.setMyValue(
        thisUser.getUserRecord().getContact().getNameLastFirst());
    newUserList.setIncludeMe(true);

    newUserList.setJsEvent(
        "onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
    HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
        "userId", getUserId(context));
    userListSelect.addAttribute("id", "userId");
    context.getRequest().setAttribute("Return", "Accounts");
    context.getRequest().setAttribute("NewUserList", userListSelect);
    return ("DashboardOK");
  }


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "soa-soa-view")) {
      return ("PermissionError");
    }
    
    String source = (String) context.getRequest().getParameter("source");
    String stato = (String) context.getRequest().getParameter("statoLab");
    String categoria = (String)context.getRequest().getParameter("searchcodeCodiceSezione");

    OrganizationList organizationList = new OrganizationList();
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("Soa.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
    	
      db = this.getConnection(context);

      if (isPortalUser(context)) {
        organizationList.setOrgSiteId(this.getUserSiteId(context));
        source = "searchForm";
      }
      organizationList.setTipologia(97);
//      organizationList.setListaStatiIstruttoria(context.getRequest().getParameterValues("searchgrouplistaStatiIstruttoria"));
     
      
      if(!"".equals(stato) && stato!=null && !stato.equals("-1")) {
      	organizationList.setStatoLab(stato);
      	 }

    
      if((context.getParameter("searchNumAut"))!=null){
          organizationList.setNumAut(context.getParameter("searchNumAut"));
          }
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
     
       Comuni c = new Comuni();
      ArrayList<Comuni> listaComuni =  c.buildList(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());
      LookupList comuniList = new LookupList(listaComuni);
     
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
      //inserito da Carmela
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
      
      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
      
      PermessoVisibilitaStabilimenti permessi = new PermessoVisibilitaStabilimenti();

      ArrayList<PermessoVisibilitaStabilimenti> lista_permessi = permessi.getListaRuoliPermessi(db,super.getSuffiso(context) );
      
      context.getRequest().setAttribute("lista_permessi", lista_permessi);
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      
      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
      	System.out.println( "soa: search -> searchContacts" );
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("Soa.do?command=Search");
        }
        //Build the organization list
        organizationList.setPagedListInfo(searchListInfo);
        organizationList.setMinerOnly(false);
        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
        
      
        if((context.getParameter("searchNumAut"))!=null){
        organizationList.setNumAut(context.getParameter("searchNumAut"));
        }
        organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
        
        searchListInfo.setSearchCriteria(organizationList, context);
        //fetching criterea for account source (my accounts or all accounts)
        if ("my".equals(searchListInfo.getListView())) {
          organizationList.setOwnerId(this.getUserId(context));
        }
        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
        } else if (organizationList.getOrgSiteId() == -1) {
          organizationList.setIncludeOrganizationWithoutSite(true);
        }
        //fetching criterea for account status (active, disabled or any)
        int enabled = searchListInfo.getFilterKey("listFilter2");
        organizationList.setIncludeEnabled(enabled);
        if (isPortalUser(context)) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
        }
        
        organizationList.buildList(db);
        
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
        if (organizationList.size() == 1 && organizationList.getAssetSerialNumber() != null) {
          AssetList assets = new AssetList();
          assets.setOrgId(((Organization) organizationList.get(0)).getId());
          assets.setSerialNumber(organizationList.getAssetSerialNumber());
          assets.setSkipParentIdRequirement(true);
          assets.buildList(db);
          if (assets.size() == 1) {
            Asset asset = (Asset) assets.get(0);
            context.getRequest().setAttribute(
                "id", String.valueOf(asset.getId()));
            return "AssetDetailsOK";
          }
        }
        return "ListOK";
      } else {}
    } catch (Exception e) {
      //Go through the SystemError process
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "" ;
  }


  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
    if (!hasPermission(context, "soa-soa-add")) {
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

    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
	newOrg.setDate1(context.getParameter("date1"));
	newOrg.setDate2(context.getParameter("date2"));
	newOrg.setNameMiddle(context.getParameter("namemiddle"));
    newOrg.setOwner(getUserId(context));
    newOrg.setTpologia(97);
    newOrg.setTipoSoa(Integer.parseInt(context.getParameter("tipoSoa")));
    newOrg.setCodiceFiscaleRappresentante(context.getParameter("codiceFiscaleRappresentante"));
    newOrg.setNomeRappresentante(context.getParameter("nomeRappresentante"));
    newOrg.setCognomeRappresentante(context.getParameter("cognomeRappresentante"));
    newOrg.setLuogoNascitaRappresentante(context.getParameter("luogoNascitaRappresentante"));
    newOrg.setCity_legale_rapp(context.getParameter("city_legale_rapp"));
    newOrg.setProv_legale_rapp(context.getParameter("prov_legale_rapp"));
    newOrg.setAddress_legale_rapp(context.getParameter("address_legale_rapp"));
    newOrg.setEmailRappresentante(context.getParameter("emailRappresentante"));
    newOrg.setTelefonoRappresentante(context.getParameter("telefonoRappresentante"));
    newOrg.setDataNascitaRappresentante(context.getParameter("dataNascitaRappresentante"));
    newOrg.setFax(context.getParameter("fax"));
    
    
    
    int statoIstruttoriaPreliminare = 1;
    if(newOrg.getTipoSoa()==2  )
    {
    	if (context.getParameter("doc_controllata").equalsIgnoreCase("on"))
    	{
    		newOrg.setContolloDocumentale(true);
    		statoIstruttoriaPreliminare = 2 ;
    	}
    	
    }
    
    newOrg.setStatoIstruttoria(statoIstruttoriaPreliminare);
    try {
      db = this.getConnection(context);

   
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("impianto", impianto);
      
        
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
     
    
      
      
      newOrg.setIsIndividual(false);
      newOrg.setRequestItems(context);
      newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
      newOrg.setSottoattivitaItem(context,db);
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
      
        recordInserted = newOrg.insert_stabilimento(db,context,Integer.parseInt(context.getParameter("tipoSoa")));
      if (recordInserted) {
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);

         
      }
      else
      {
    	  context.getRequest().setAttribute("Inserito", "no");
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
     
     
    	  return ("InsertOK");
     
    }
    return (executeCommandAdd(context));
  }



  public String executeCommandUpdate(ActionContext context) throws SQLException, ParseException {
	   
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    Organization newOrg = (Organization) context.getFormBean();
	    Organization oldOrg = null;
	   
	    
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    try {
	        db = this.getConnection(context);
	    newOrg.setModifiedBy(getUserId(context));
	    newOrg.setEnteredBy(getUserId(context));
	    newOrg.setModifiedBy(getUserId(context));
		newOrg.setDate1(context.getParameter("date1"));
		newOrg.setDate2(context.getParameter("date2"));
		newOrg.setTipoSoa(Integer.parseInt(context.getParameter("tipoSoa")));
		newOrg.setNameMiddle(context.getParameter("namemiddle"));
	    newOrg.setOwner(getUserId(context));
	    newOrg.setCodiceFiscaleRappresentante(context.getParameter("codiceFiscaleRappresentante"));
	    newOrg.setNomeRappresentante(context.getParameter("nomeRappresentante"));
	    newOrg.setCognomeRappresentante(context.getParameter("cognomeRappresentante"));
	    newOrg.setLuogoNascitaRappresentante(context.getParameter("luogoNascitaRappresentante"));
	    newOrg.setCity_legale_rapp(context.getParameter("city_legale_rapp"));
	    newOrg.setProv_legale_rapp(context.getParameter("prov_legale_rapp"));
	    newOrg.setAddress_legale_rapp(context.getParameter("address_legale_rapp"));
	    newOrg.setEmailRappresentante(context.getParameter("emailRappresentante"));
	    newOrg.setTelefonoRappresentante(context.getParameter("telefonoRappresentante"));
	    newOrg.setDataNascitaRappresentante(context.getParameter("dataNascitaRappresentante"));
	    newOrg.setFax(context.getParameter("fax"));
	    newOrg.setIsIndividual(false);
	    newOrg.setRequestItems(context);
	    newOrg.setStatoLab(Integer.parseInt(context.getParameter("statoLab")));
	    try {
			newOrg.setSottoattivitaItem(context,db);
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
	    newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
	    newOrg.setDate2(context.getParameter("dateI"));
	    newOrg.setCodiceFiscale(context.getParameter("cf_stabilimento"));
	    newOrg.setTpologia(97);
	    
	    
	     
	    
	        String tipoRichiesta = context.getParameter("tipoModifica");
	        
	        oldOrg = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
	    	newOrg.setOrgId(Integer.parseInt(context.getParameter("orgId")));
	    	
	    	//int tipo_autorizzazzione_definitiva = 1 ;
	     
	    	newOrg.setData_assegnazione_approval_number(context.getParameter("data_assegnazione_approval_number"));
	        
	    	if (newOrg.getTipoSoa()==1)
	    	{
	    		gestioneAggiornamentoRiconosciuti(newOrg,oldOrg ,context,db);
	    	}
	    	else
	    	{
	    		if (newOrg.getTipoSoa()==2)
	    		{
	    			gestioneAggiornamentoRegistrati(newOrg,oldOrg ,context,db);
	    		}
	    	}
	    	
	    	
	        
	      

	 
	      
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }

	    context.getRequest().setAttribute("orgId", newOrg.getOrgId());
	    return executeCommandDetails(context);
	  }
  
  
  private void gestioneAggiornamentoRiconosciuti(Organization newOrg ,Organization oldOrg,ActionContext context,Connection db) throws SQLException, ParseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
	  if(context.getParameter("idImpianto")!=null){
      	int id_nuovo_stato = Integer.parseInt(context.getParameter("nuovoStato"));

  		newOrg.cambiaStatoImpianto(id_nuovo_stato, Integer.parseInt(context.getParameter("idImpianto")), db);
  		
  		
  		boolean impiantirevocati = true ;
      	boolean impiantisospesi = false ;
      	boolean impiantiautorizzati = false ;
  		oldOrg.setSottoattivitaItem(context, db);
  		ArrayList<SottoAttivita> lista_sa =   SottoAttivita.loadBySoa(oldOrg.getId(), db);
  		for (SottoAttivita sa : lista_sa)
  		{
  			if (sa.getStato_attivita()!=1)
  			{
  				impiantirevocati = false ;
  			}
  			
  			if (sa.getStato_attivita()==0)
  			{
  				impiantiautorizzati = true ;
  			}
  			if (sa.getStato_attivita()==2)
  			{
  				impiantisospesi = true ;
  			}
  			
  			
  			
  			sa.update(db);
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
  			
  			oldOrg.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA   ||  
  			oldOrg.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO  )
      {
      	
      	int id_nuovo_stato = Integer.parseInt(context.getParameter("nuovoStato"));
      	if (id_nuovo_stato == StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA || 
					id_nuovo_stato == StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO || 
					id_nuovo_stato == StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE  )
				
			{
      	newOrg.setStatoIstruttoria(id_nuovo_stato);
      	newOrg.setOrgId(Integer.parseInt(context.getParameter("orgId")));
      	newOrg.cambiaStatoIstruttoria(id_nuovo_stato, db);
      	
      	
      	if (id_nuovo_stato==5)
      	{
      		ArrayList<SottoAttivita> lista_sa =   SottoAttivita.loadBySoa(oldOrg.getId(), db);
      		for (SottoAttivita sa : lista_sa)
      		{
      			//sa.setTipo_autorizzazione(tipo_autorizzazzione_definitiva);
      			sa.update(db);
      		}
      	}
      	
      	if (id_nuovo_stato==4)
      	{
      		oldOrg.setSottoattivitaItem(context, db);
      		ArrayList<SottoAttivita> lista_sa =   oldOrg.getSottoattivitaList();
      		for (SottoAttivita sa : lista_sa)
      		{
      			
      			sa.update(db);
      		}
      	}
			}
      }
     
  	else
      if(oldOrg.getStatoIstruttoria()==StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE )
      {
      	
      	int id_nuovo_stato = Integer.parseInt(context.getParameter("nuovoStato"));
      	if(id_nuovo_stato == StatiStabilimenti.RICONOSCIUTO_CONDIZIONATO && oldOrg.getStatoLab()!=0)
      	{
      		newOrg.updateStatoLab(5, db) ;
      	}
      	newOrg.setNumAut(context.getParameter("numAut"));
      	newOrg.setStatoIstruttoria(id_nuovo_stato);
      	newOrg.setOrgId(Integer.parseInt(context.getParameter("orgId")));
      	newOrg.attribuisciApprovalNumbrer(db);
      	newOrg.setSottoattivitaItem(context, db);
      	//ArrayList<SottoAttivita> lista_sa =   SottoAttivita.loadByStabilimento(oldOrg.getId(), db);
  		for (SottoAttivita sa : newOrg.getSottoattivitaList())
  		{	
  			
  			sa.updateAutorizzazzione(db);
  		}
      }
      else
      if (oldOrg.getStatoIstruttoria()==StatiStabilimenti.COMPLETATO)
  	{
      	boolean impiantirevocati = true ;
      	boolean impiantisospesi = false ;
      	boolean impiantiautorizzati = false ;
  		oldOrg.setSottoattivitaItem(context, db);
  		if(oldOrg.getSottoattivitaList().isEmpty())
  		{
  			newOrg.setSottoattivitaList(SottoAttivita.loadBySoa(oldOrg.getId(), db));
  		}
  		
  		ArrayList<SottoAttivita> lista_sa =   newOrg.getSottoattivitaList();
  		for (SottoAttivita sa : lista_sa)
  		{
  			if (sa.getStato_attivita()!=1)
  			{
  				impiantirevocati = false ;
  			}
  			
  			if (sa.getStato_attivita()==0)
  			{
  				impiantiautorizzati = true ;
  			}
  			if (sa.getStato_attivita()==2)
  			{
  				impiantisospesi = true ;
  			}
  			
  			
  			
  			sa.update(db);
  		}
  		if(impiantirevocati==true)
  			newOrg.revocaStabilimento(db);
  		else
  			if(impiantiautorizzati==true)
  				newOrg.updateStatoLab(0,db);
  			else
  				if(impiantisospesi==true)
      				newOrg.updateStatoLab(2,db);
  				
  	}
    
      if (oldOrg.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_PRELIMINARE || newOrg.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA)
  	{
  		newOrg.update(db,context);
  	}
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  private void gestioneAggiornamentoRegistrati(Organization newOrg ,Organization oldOrg,ActionContext context,Connection db) throws SQLException, ParseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
	  
  	if(oldOrg.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_ESISTENTE  || oldOrg.getStatoIstruttoria()==StatiStabilimenti.COMPLETATO  || oldOrg.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA   ||  oldOrg.getStatoIstruttoria()==StatiStabilimenti.INOLTRO_DEFINITIVO_REGIONE  )
      {
      	
      	int id_nuovo_stato = Integer.parseInt(context.getParameter("nuovoStato"));
    	boolean impiantirevocati = true ;
      	boolean impiantisospesi = false ;
      	boolean impiantiautorizzati = false ;
      	newOrg.setStatoIstruttoria(id_nuovo_stato);
      	newOrg.setOrgId(Integer.parseInt(context.getParameter("orgId")));
      	if(context.getParameter("numAut")!=null)
      		newOrg.setNumAut(context.getParameter("numAut"));
     	newOrg.attribuisciApprovalNumbrer(db);
      	newOrg.cambiaStatoIstruttoria(id_nuovo_stato, db);
      	
      	UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
      	if(user.getSiteId()==-1)// se e' utente nurecu
      	{
      		int tipo_autorizzazzione_definitiva = 1 ;
      		newOrg.setSottoattivitaItem(context,db);
      		
      		ArrayList<SottoAttivita> lista_sa =   newOrg.getSottoattivitaList();
      		for (SottoAttivita sa : lista_sa)
      		{
      			sa.setTipo_autorizzazione(tipo_autorizzazzione_definitiva);
      			sa.update(db);
      			
      			if (sa.getStato_attivita()!=1)
      			{
      				impiantirevocati = false ;
      			}
      			
      			if (sa.getStato_attivita()==0)
      			{
      				impiantiautorizzati = true ;
      			}
      			if (sa.getStato_attivita()==2)
      			{
      				impiantisospesi = true ;
      			}
      		}
      		
      		if(impiantirevocati==true)
      			newOrg.revocaStabilimento(db);
      		else
      			if(impiantiautorizzati==true)
      				newOrg.updateStatoLab(0,db);
      			else
      				if(impiantisospesi==true)
          				newOrg.updateStatoLab(2,db);
      	}	
      	
			
      }
     
  	  else
      if (oldOrg.getStatoIstruttoria()==StatiStabilimenti.COMPLETATO)
  	  {
      	boolean impiantirevocati = true ;
      	boolean impiantisospesi = false ;
      	boolean impiantiautorizzati = false ;
  		oldOrg.setSottoattivitaItem(context, db);
  		if(oldOrg.getSottoattivitaList().isEmpty())
  		{
  			newOrg.setSottoattivitaList(SottoAttivita.loadBySoa(oldOrg.getId(), db));
  		}
  		
  		ArrayList<SottoAttivita> lista_sa =   newOrg.getSottoattivitaList();
  		for (SottoAttivita sa : lista_sa)
  		{
  			if (sa.getStato_attivita()!=1)
  			{
  				impiantirevocati = false ;
  			}
  			
  			if (sa.getStato_attivita()==0)
  			{
  				impiantiautorizzati = true ;
  			}
  			if (sa.getStato_attivita()==2)
  			{
  				impiantisospesi = true ;
  			}
  			
  			
  			
  			sa.update(db);
  		}
  		if(impiantirevocati==true)
  			newOrg.revocaStabilimento(db);
  		else
  			if(impiantiautorizzati==true)
  				newOrg.updateStatoLab(0,db);
  			else
  				if(impiantisospesi==true)
      				newOrg.updateStatoLab(2,db);
  				
  	}
    
      if (oldOrg.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_PRELIMINARE || newOrg.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA)
  	{
  		newOrg.update(db,context);
  	}
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateCatRischio(ActionContext context) {
    if (!(hasPermission(context, "soa-tipochecklist-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    String org_id = context.getRequest().getParameter( "orgId" );
    String account_size = context.getRequest().getParameter( "accountSize" );
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual

      Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
      newOrg.setAccountSize( account_size );
      newOrg.setTypeList(
          context.getRequest().getParameterValues("selectedList"));
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
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "soa-soa-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Exception errorMessage = null;
    boolean recordDeleted = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
        return ("PermissionError");
      }
      if (context.getRequest().getParameter("action") != null) {
        if (((String) context.getRequest().getParameter("action")).equals(
            "delete")) {
          // NOTE: these may have different options later
          thisOrganization.setContactDelete(true);
          thisOrganization.setRevenueDelete(true);
          thisOrganization.setDocumentDelete(true);
          OpportunityHeaderList opportunityList = new OpportunityHeaderList();
          opportunityList.setOrgId(thisOrganization.getOrgId());
          opportunityList.buildList(db);
          opportunityList.invalidateUserData(context, db);
          thisOrganization.setForceDelete(
              context.getRequest().getParameter("forceDelete"));
          recordDeleted = thisOrganization.delete(
              db, context, getDbNamePath(context));
        } else if (((String) context.getRequest().getParameter("action")).equals(
            "disable")) {
          recordDeleted = thisOrganization.disable(db);
        }
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        
        context.getRequest().setAttribute(
            "refreshUrl", "Soa.do?command=Search");
        if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
            context.getRequest().getParameter("return"))) {
          return executeCommandSearch(context);
        }
        return "DeleteOK";
      } else {
        processErrors(context, thisOrganization.getErrors());
        return (executeCommandSearch(context));
      }
    } else {
      System.out.println(errorMessage);
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
          "object.validation.actionError.accountDeletion"));
      context.getRequest().setAttribute(
          "refreshUrl", "Soa.do?command=Search");
      return ("DeleteError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "soa-soa-delete")) {
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
      
      // NOTE: these may have different options later
      recordUpdated = thisOrganization.updateStatus(
          db, context, true, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
      this.invalidateUserData(context, thisOrganization.getOwner());
      
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "Soa.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Soa.do?command=Search");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearch(context);
      }
      return "DeleteOK";
    } else {
      return (executeCommandSearch(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandRestore(ActionContext context) {
    if (!hasPermission(context, "soa-soa-delete")) {
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
      recordUpdated = thisOrganization.updateStatus(
          db, context, false, this.getUserId(context));
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
      context.getRequest().setAttribute(
          "refreshUrl", "Soa.do?command=Search");
      if ("list".equals(context.getRequest().getParameter("return"))) {
        return executeCommandSearch(context);
      }
      return this.executeCommandDetails(context);
    } else {
      return (executeCommandSearch(context));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandEnable(ActionContext context) {
    if (!hasPermission(context, "soa-soa-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
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
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "soa-soa-delete")) {
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
        if (!AccountsUtil.isCancellabile(db, thisOrg.getOrgId())){
      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
            htmlDialog.addMessage("<br/>");
            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
        }
        else {
        htmlDialog.addMessage("<form action=\"TrasportoAnimali.do?command=Trash&auto-populate=true\" method=\"post\">");
        htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
        htmlDialog.addMessage("<br/>");
        htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
        htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
        htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
        htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+thisOrg.getOrgId()+"\"/>");
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
   *  Modify: Displays the form used for modifying the information of the
   *  currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
	   
	    Connection   db           = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg       = null;
	    try {
	    	ArrayList    elencoAttivita = null;
	      String temporgId = context.getRequest().getParameter("orgId");
	     
	      int tempid = Integer.parseInt(temporgId);
	           
	      db = this.getConnection(context);
	   
	      newOrg = new Organization(db, tempid);
	      newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());
	      elencoAttivita =  SottoAttivita.loadBySoa(tempid, db);
	      context.getRequest().setAttribute("elencoSottoAttivita", elencoAttivita);

	   //context.getRequest().setAttribute("tipoModifica",Integer.parseInt(context.getRequest().getParameter("tipoModifica")));
	      Iterator it_coords = newOrg.getAddressList().iterator();
	      while(it_coords.hasNext()){
	    	  
	    	  org.aspcfs.modules.soa.base.OrganizationAddress thisAddress = (org.aspcfs.modules.soa.base.OrganizationAddress) it_coords.next();
	    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
	    		  
	    		  String spatial_coords [] = null;
	        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
	        	
	      			  thisAddress.setLatitude(spatial_coords[0]);
	        		  thisAddress.setLongitude(spatial_coords[1]);
	      		 
	    	  }
	      }
	      //check whether or not the owner is an active User
	      newOrg.setOwner(this.getUserId(context));
	    
	      LookupList statiStabilimenti = new LookupList(db, "lookup_stati_stabilimenti");
	      context.getRequest().setAttribute("statiStabilimenti", statiStabilimenti);
	      context.getRequest().setAttribute("Condizionato", context.getParameter("condizionato"));
	      context.getRequest().setAttribute("Revoca", context.getParameter("revoca"));

	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
	      statoLab.removeElementByLevel(3);
	      statoLab.removeElementByLevel(4);
	      statoLab.removeElementByLevel(5);
	      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("statoLab", statoLab);
	      
	      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
	      
	      
			LookupList statoLabImpianti = new LookupList(db, "lookup_stato_lab_impianti");
			
			statoLabImpianti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLabImpianti", statoLabImpianti);

	      LookupList impianto = new LookupList(db, "lookup_impianto_soa");
	      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("impianto", impianto);
	      
	      LookupList lookup_prodotti = new LookupList(db, "lookup_prodotti_stabilimenti");
	      lookup_prodotti.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("LookupProdotti", lookup_prodotti);
	      
	      LookupList lookup_classificazione = new LookupList(db, "lookup_classificazione_stabilimenti");
	      lookup_classificazione.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("LookupClassificazione", lookup_classificazione);
	      
	      LookupList IstatList = new LookupList(db, "lookup_codistat");
	      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("IstatList", IstatList);
	      
	      
	      LookupList categoriaList = new LookupList(db, "lookup_categoria_soa");
	      categoriaList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("categoriaList", categoriaList);
	      
	      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata");
	      imballataList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("imballataList", imballataList);
	      
	      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
	      tipoAutorizzazioneList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
	     
	      
	      addRecentItem(context, newOrg);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      if(context.getParameter("mode")==null)
	    	  return"ModifyOK";
	      else
	    	  return"ModifyPraticaOK";
	    
	  }

  
//inserito carmela
  public String executeCommandModificaCatRischio(ActionContext context) {
	    if ((!hasPermission(context, "soa-tipochecklist-add"))) {
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
	      if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }
	      newOrg = new Organization(db, tempid);
	      //check whether or not the owner is an active User
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
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
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("ModificaCatRischioOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "ModificaCatRischio");
	    }
	  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "soa-soa-folders-view"))) {
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
      //Show a list of the different folders available in Accounts
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
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
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


 
  /*inserito da d.dauria */
  public String executeCommandPrintReport(ActionContext context) {
	    if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		try {
		  db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("id");
		  HashMap map = new HashMap();
		  map.put("orgid", new Integer(id));
		  map.put("path", getWebInfPath(context, "reports"));
		  //provide the dictionary as a parameter to the quote report
		  map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
		  String filename = (String) context.getRequest().getParameter("file");
		  //provide a seperate database connection for the subreports
		  Connection scriptdb = this.getConnection(context);
		  map.put("SCRIPT_DB_CONNECTION", scriptdb);

		  //Replace the font based on the system language to support i18n chars
		  String fontPath = getWebInfPath(context, "fonts");
		  String reportDir = getWebInfPath(context, "reports");
		  JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
		  String language = getPref(context, "SYSTEM.LANGUAGE");

		  JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);
		  
		  byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

		  if (bytes != null) {
		    FileDownload fileDownload = new FileDownload();
		    	fileDownload.setDisplayName("DettaglioSoa_" + id + ".pdf");
		    fileDownload.sendFile(context, bytes, "application/pdf");
		  } else {
		    return ("SystemError");
		  }
		} catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
		} finally {
		  this.freeConnection(context, db);
		}
		return ("-none-");
	  }
  
  public String executeCommandUpdateControlloDocumentale(ActionContext context)
  {
	 
	  Connection db = null ;
	  int idStabilimento 	= Integer.parseInt(context.getParameter("idStabilimento"));
	  int numQuesiti		= Integer.parseInt(context.getParameter("numQuesiti"));
	  int idCD		= Integer.parseInt(context.getParameter("idControlloDocumentale"));
	 
	  
	  try
	  {
		  db = this.getConnection(context);
		
		  PreparedStatement pst_1 = null;
		  PreparedStatement pst_2 = null;
		  boolean updateStato = false ;
		  String statoControlloDocumentaleAsl = context.getParameter("stato_asl");
		  String statoControlloDocumentaleStap = context.getParameter("stato_stap");
		  UserBean user = (UserBean)context.getSession().getAttribute("User");
		  for (int i = 0 ; i <numQuesiti; i++)
		  {
			  boolean risposta = false ;
			 
			  String sql_1 = "" ;
			  String sql_2 = "" ;

			  int idQuesito = Integer.parseInt(context.getParameter("id_quesito_"+i));
			  String risposta_asl = context.getParameter("risposta_asl_"+i);
			  String risposta_stap = context.getParameter("risposta_stap_"+i);
			  String noteStap = context.getParameter("note_stap_"+i);
			  String statoDontrolloDocumentale = "" ;
			  String competenzaAsl = context.getParameter("competenzaAsl_"+i);
			  /*SE e' UNO STAP*/
			  if(user.getRoleId()==40)
			  {
				  statoDontrolloDocumentale = statoControlloDocumentaleStap;
				  if (risposta_stap != null )
				  {
					  risposta = true ;
				  }
				  sql_1 = "update quesiti_controllo_documentale_stabilimenti set stato_stap = ?, user_id_stap = ?,modified_stap=current_timestamp where id_stabilimento = ? " ; 
				  sql_2 = "update quesiti_risposte_controllo_documentale set risposta_stap = ?,note_stap = ? where id_quesito = ? and id_quesiti_controllo_documentale_stabilimenti = ? " ; 

			  }
			  else
			  {
				  if(user.getRoleId()==40 || user.getRoleId()==27)
				  {
					  statoDontrolloDocumentale = statoControlloDocumentaleStap;
					  if (risposta_stap != null )
					  {
						  risposta = true ;
					  }
					  sql_1 = "update quesiti_controllo_documentale_stabilimenti set stato_stap = ?, user_id_stap = ?,modified_stap=current_timestamp where id_stabilimento = ? " ; 
					  sql_2 = "update quesiti_risposte_controllo_documentale set risposta_stap = ?,note_stap = ? where id_quesito = ? and id_quesiti_controllo_documentale_stabilimenti = ? " ; 

				  }
				  else
				  {
				 
				  statoDontrolloDocumentale = statoControlloDocumentaleAsl;
				  if(statoControlloDocumentaleAsl.equals("1")) // se lo stato e' definitvo
					  updateStato = true ;
				  if (risposta_asl != null )
				  {
					  risposta = true ;
				  }
				  
				  sql_1 = "update quesiti_controllo_documentale_stabilimenti set stato_asl = ?, user_id_asl = ?,modified_asl=current_timestamp where id_stabilimento = ? " ; 
				 
				  if (competenzaAsl.equalsIgnoreCase("true"))
				  {
					  sql_2 = "update quesiti_risposte_controllo_documentale set risposta_asl = ? where id_quesito = ? and id_quesiti_controllo_documentale_stabilimenti = ? " ; 
				  }
				  else
				  {
					  sql_2 = "" ;
				 }
				  }
			  }
			  
			  pst_1 = db.prepareStatement(sql_1) ;
			  pst_1.setInt(1, Integer.parseInt(statoDontrolloDocumentale))  ; 
			  pst_1.setInt(2, user.getUserId())  ; 
			  pst_1.setInt(3, idStabilimento)  	 ;

			  
			  int j = 0 ;
			 if (!sql_2.equals(""))
			 {
			  pst_2 = db.prepareStatement(sql_2) 	;
			  pst_2.setBoolean(++j, risposta)			;
			  if(user.getRoleId()==39 || (user.getRoleId()==40 || user.getRoleId()==27))
			  {
				  pst_2.setString(++j, noteStap) ;
			  }
			  
			  pst_2.setInt(++j, idQuesito)			;
			  pst_2.setInt(++j, idCD)					;

			  pst_2.execute();
			 
			 }
			 pst_1.execute();

			  
		  }
		  
		  /*SALVATO IL CONTROLLO DOCUMENTALE LA PRATICA PASSA NELLO STATO 2 (ISTRUTTORIA DOCUMENTAZIONE CONTROLLATA)*/
		  
		  ControlloDocumentale cd = new ControlloDocumentale();
		  cd.buildControlloDocumentale(db, idStabilimento);
		  
		  
		  /*se lo stap ha salvato definitivamente e ha riscontrato problemi riapro la pratica per l'asl*/
		  if (statoControlloDocumentaleStap.equals("1") && (user.getRoleId()==39 || user.getRoleId()==40)&&  cd.getEsitoControlloFavorevole()==false)
		  {
			  String sql = "update quesiti_controllo_documentale_stabilimenti set stato_asl = 0 where id_stabilimento = ? " ; 
			  PreparedStatement pst_up = db.prepareStatement(sql);
			  pst_up.setInt(1,idStabilimento);
			  pst_up.execute();
			  
			  PreparedStatement pst = db.prepareStatement("update organization set stato_istruttoria = 1 where org_id = ? ");
			  pst.setInt(1,idStabilimento);
			  pst.execute();
			  
		  }
		  else
		  {
			  if (statoControlloDocumentaleAsl.equals("1") && user.getRoleId()!=40)
			  {

				  String sql = "update quesiti_controllo_documentale_stabilimenti set stato_stap = 0 where id_stabilimento = ? " ; 
				 
				  PreparedStatement pst_up = db.prepareStatement(sql);
				  pst_up.setInt(1,idStabilimento);
				  pst_up.execute();
				  
				 
			  }
		  }
		  
		  
		  if (updateStato ==true)
		  {
			  PreparedStatement pst_up = db.prepareStatement("update organization set stato_istruttoria = 2 where org_id = ? ");
			  pst_up.setInt(1,idStabilimento);
			  pst_up.execute();
		  }
		  Organization o = new Organization(db,idStabilimento);
		  context.getRequest().setAttribute("OrgDetails", o);
	  }
	  catch(SQLException e )
	  {
		  context.getRequest().setAttribute("EsitoUpdate", "KO");
		  e.printStackTrace();
		  context.getRequest().setAttribute("orgId", idStabilimento);
		  return executeCommandViewControlloDocumentale(context) ;
	  }
	  finally
	  {
		  this.freeConnection(context, db) ;
	  }
	  context.getRequest().setAttribute("EsitoUpdate", "OK");
	  context.getRequest().setAttribute("orgId", idStabilimento);
	  return "UpdateControlloDocumentaleOK" ;
  }
  public String executeCommandViewControlloDocumentale(ActionContext context) {
	 
	    Connection db = null;
	    try {
	      db = getConnection(context);
	      int idStabilimento = -1 ;
	      if (context.getParameter("orgId") != null)
	      {
	    	  idStabilimento = Integer.parseInt(context.getParameter("orgId"));
	      }
	      else
	      {
	    	  idStabilimento = (Integer ) context.getRequest().getAttribute("orgId") ;
	      }
	      ControlloDocumentale cd = new ControlloDocumentale();
	      cd.buildControlloDocumentale(db, idStabilimento);
	      context.getRequest().setAttribute("ListaQuesiti", cd);
	      
	      Organization newOrg = new Organization(db, idStabilimento);
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      if (context.getRequest().getAttribute("EsitoUpdate")!=null)
	      {
	    	  if (((String)context.getRequest().getAttribute("EsitoUpdate")).equals("KO"))
	    	  {
	    		  context.getRequest().setAttribute("EsitoUpdate","Errore nel Salvataggio delle Informazioni");
	    	  }
	    	  else
	    	  {
	    		  context.getRequest().setAttribute("EsitoUpdate","Salvataggio Avvenuto con successo");
	    	  }
	      }
	      
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	this.freeConnection(context, db);
	    }
	    
	    return "ViewControlloDocOK";
  }
  
  /*
  private String[] converToWgs84UTM33NInverter(String latitudine , String longitudine, Connection db) throws SQLException
  {
          String lat ="";
          String lon = "";
          String [] coord = new String [2];
          String sql1 = 
                  "select         y \n"        + 
                  "( \n"                                + 
                  "        transform \n"        + 
                  "        (  \n"                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+longitudine+" "+latitudine+")', 32633 \n"        + 
                  "         \n"                                        + 
                  "                ), 4326 \n"                        + 
                  "        ) \n"                                        + 
                  ") AS y, \n"                                + 
                  "x \n"                                                + 
                  "( \n"                                                + 
                  "        transform \n"                        + 
                  "        (  \n"                                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+longitudine+" "+latitudine+")', 32633 \n"        + 
                  "         \n"                        + 
                  "                ), 4326 \n"        + 
                  "        ) \n"                        + 
                  ") AS x \n";
          
        try {
        	
          PreparedStatement stat1 = db.prepareStatement( sql1 );
          ResultSet res1 = stat1.executeQuery();
          if( res1.next() )
          {
                  lat = res1.getString( "y") ;
                  lon=res1.getString( "x" );
                  coord [0] =lat;
                  coord [1] =lon;
          
          }
          res1.close();
          stat1.close();
          
        }catch (Exception e){
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

}