/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.global_search.actions;

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
import java.util.ArrayList;
import java.util.HashMap;
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

import org.aspcfs.controller.SystemStatus;
//import org.aspcfs.modules.accounts.base.Organization;
//import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.global_search.base.OrganizationListViewMinimale;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;
//import com.lowagie.text.Cell;

public final class GlobalSearch extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}
	
	public String executeCommandDashboard(ActionContext context) {
		 if (!hasPermission(context, "accounts-dashboard-view")) {
			 if (!hasPermission(context, "global-search-view")) {
		        return ("PermissionError");
		     }
			 this.deletePagedListInfo(context, "SearchOrgListInfo");
			 //Bypass dashboard and search form for portal users
		     if (isPortalUser(context)) {
		        return (executeCommandSearch(context));
		     }
		  
		 }   return (executeCommandSearchForm(context));
		  
	}
	
	public String executeCommandSearchForm(ActionContext context) {
	    if (!(hasPermission(context, "global-search-view"))) {
	      return ("PermissionError");
	    }
	    
	    //Bypass search form for portal users
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	     // siteList.addItem(-1, "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      this.deletePagedListInfo(context, "searchListInfo");
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      UserBean user = (UserBean) context.getSession().getAttribute("User");
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
	      
	      LookupList comuniList = new LookupList(listaComuni);
	      comuniList.addItem(-1, "");
	      context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {	
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchOK");
	    
	  }
	
	
	
	public String executeCommandSearchFormImprese(ActionContext context) {
//	    if (!(hasPermission(context, "global-search-view"))) {
//	      return ("PermissionError");
//	    }
	    
		
		Connection db = null;
		try {
			db = this.getConnection(context);	      
		
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	     // siteList.addItem(-1, "-- SELEZIONA VOCE --");
	      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      this.deletePagedListInfo(context, "searchListInfo");
	      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
	      UserBean user = (UserBean) context.getSession().getAttribute("User");
	      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
	      
	      
	      LookupList comuniList = new LookupList(listaComuni);
	      comuniList.addItem(-1, "");
	      context.getRequest().setAttribute("ComuniList", comuniList);
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {	
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchImpreseOK");
	    
	  }

	
	public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "global-search-view") && !hasPermission(context, "global-search-acquacoltura-view")) {
      return ("PermissionError");
    }
    
    context.getRequest().setAttribute("redirect", context.getRequest().getParameter("redirect"));
    context.getRequest().setAttribute("redirectAcquacoltura", context.getRequest().getParameter("redirectAcquacoltura"));
    
    boolean reportExcelRichiesto = false;

	UserBean user = (UserBean) context.getSession().getAttribute("User");
	OrganizationListViewMinimale organizationList = new OrganizationListViewMinimale();
	
	String source = (String) context.getRequest().getParameter("source");
	String tipoRicerca = (String) context.getRequest().getParameter("tipoRicerca");
	
	String OpCancellati = (String) context.getRequest().getParameter("searchOpCancellati");
	String AttCancellati = (String) context.getRequest().getParameter("searchAttCancellati");
	
    String asl = (String) context.getRequest().getParameter("searchcodeOrgSiteId");
    String tipo = (String) context.getRequest().getParameter("searchcodeTipologia");
    String comune = (String) context.getRequest().getParameter("searchAccountCity");
    String provincia = (String) context.getRequest().getParameter("searchAccountOtherState");
    String name = (String) context.getRequest().getParameter("searchAccountName");
    String tipologiaAttivita = (String) context.getRequest().getParameter("searchcodeTipologiaAttivita");
    String num_verbale = (String) context.getRequest().getParameter("searchAccountNumVerbale");
	String esito = (String) context.getRequest().getParameter("searchEsito");
    String idC = (String) context.getRequest().getParameter("searchAccountIdentificativo");
    String categoria = (String) context.getRequest().getParameter("searchcodeCategoriaRischio");
    String numero = (String) context.getRequest().getParameter("searchcodeNumero");
    String qualificatore = (String) context.getRequest().getParameter("searchcodeQualificatore");
    String inizio = (String) context.getRequest().getParameter("searchtimestampInizio");
    String fine = (String) context.getRequest().getParameter("searchtimestampFine");
        
    if( asl != null )
    	organizationList.setOrgSiteId(asl);
    
    if(tipo != null && tipo !="-1" ){
    	switch ( Integer.parseInt(tipo)) {
        	case 1: organizationList.setTipologia(1); break;
        	case 2: organizationList.setTipologia(2); break;
        	case 3: organizationList.setTipologia(3); break;
        	case 4: organizationList.setTipologia(3);
        			organizationList.setDirectBill(true); break;
        	case 9: organizationList.setTipologia(9);  break;
        	case 97: organizationList.setTipologia(97);  break;
        	case 800: organizationList.setTipologia(800); break;
        	case 801: organizationList.setTipologia(801); break;
        	case 201: organizationList.setTipologia(201); break;
        	default: logger.info("Switch tipologia operatore"); break;
    	}
   }
  
   
   if(tipologiaAttivita != null && tipologiaAttivita !="-1" ){
   	switch ( Integer.parseInt(tipologiaAttivita)) {
       	case 1:  organizationList.setTipologiaAttivita(1); break;
       	case 2:  organizationList.setTipologiaAttivita(2); break;
       	case 3:  organizationList.setTipologiaAttivita(3); break;
       	case 6:  organizationList.setTipologiaAttivita(6); break;
       	case 7:  organizationList.setTipologiaAttivita(7); break;
       	case 9:  organizationList.setTipologiaAttivita(9);  break;
       	case 15: organizationList.setTipologiaAttivita(15);  break;
       	default: logger.info("Switch tipologia attivita'"); break;
   	}
  }
   
   if(name != null)
	   organizationList.setAccountName(name);
   
   if(idC != null){
   	organizationList.setAccountIdentificativo(idC);
   }
   
   if(num_verbale != null){
   	organizationList.setAccountNumVerbale(num_verbale);
   }
   
      
   if(qualificatore != null && qualificatore !="-1" ){
	   	switch ( Integer.parseInt(qualificatore)) {
	       	case 1: organizationList.setQualificatore(1); break;
	       	case 2: organizationList.setQualificatore(2); break;
	       	case 3: organizationList.setQualificatore(3); break;
	       	case 4: organizationList.setQualificatore(4); break;
	       	default: logger.info("Switch qualificatore"); break;
	   	}
	}
  
   if(numero != null && numero !="-1" ){
	   	switch ( Integer.parseInt(numero)) {
	       	case 1: organizationList.setNumero(1); break;
	       	case 10: organizationList.setNumero(10); break;
	       	case 50: organizationList.setNumero(50); break;
	       	case 100: organizationList.setNumero(100); break;
	       	default: logger.info("Switch numero"); break;
	   	}
	}
  
   
   if( inizio!= null){
	   	organizationList.setInizio(inizio);
   }
  
   if( fine!= null){
	   	organizationList.setFine(fine);
   }
   
   //Anche il filtro sulla tipologia della ricerca deve essere gestito
   if(tipoRicerca!=null){
	   organizationList.setTipoRicerca(tipoRicerca);
   }
   
   if(OpCancellati != null){
	   organizationList.setOpCancellati(OpCancellati);
   }
   //Set check operatore e attivita'
   
   if(AttCancellati != null){
	   organizationList.setAttCancellati(AttCancellati);
   }
  
   if(esito != null){
	   organizationList.setEsito(esito);
   }
  
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    
    searchListInfo.setLink("GlobalSearch.do?command=Search&tipoRicerca="+tipoRicerca);
    searchListInfo.setListView("all");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
    	
      db = this.getConnection(context); 
      
      if ((searchListInfo.getListView() == null || "".equals(
              searchListInfo.getListView())) && !"searchForm".equals(source)) {
            return "ListOK";
          }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
      LookupList comuniList = new LookupList(listaComuni);
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
    

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("GlobalSearch.do?command=Search");
        }
        organizationList.setPagedListInfo(searchListInfo);
        organizationList.setMinerOnly(false);
        searchListInfo.setSearchCriteria(organizationList, context);
        if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
        } else if (organizationList.getOrgSiteId() == -1) {
          organizationList.setIncludeOrganizationWithoutSite(true);
        }
        //fetching criterea for account status (active, disabled or any)
        int enabled = searchListInfo.getFilterKey("listFilter2");
        //organizationList.setCessato(enabled);
        organizationList.setIncludeEnabled(enabled);
        //If the user is a portal user, fetching only the
        //the organization that he access to
        //(i.e., the organization for which he is an account contact
        if (isPortalUser(context)) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
        }
        
        reportExcelRichiesto = context.getRequest().getParameter("excel")!=null && 
        							   context.getRequest().getParameter("excel").equalsIgnoreCase("ok");
        
        if(!tipoRicerca.equals("det")){
        	organizationList.buildListView(db,tipoRicerca, reportExcelRichiesto);
        }
        else{
        	organizationList.buildListViewCount(db, reportExcelRichiesto);
        }
        context.getSession().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
      } 
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    
    if(tipoRicerca.equals("op")){
    	if(reportExcelRichiesto){
    		return this.executeCommandStampaExportExcelOperatori(context);
    	}
    	return "ListOK";
    }    else if(tipoRicerca.equals("cu"))
    {
    	if(reportExcelRichiesto){
    		return this.executeCommandStampaExportExcelAttivita(context);
    	}
    	return "ListTipologiaOK";
    }
    else if(tipoRicerca.equals("cam"))
    {
    	if(reportExcelRichiesto){
    		return this.executeCommandStampaExportExcelCampioni(context);
    	}
    	return "ListCampioniOK";
    }
    else{
    	if(reportExcelRichiesto){
    		return this.executeCommandStampaExportExcelDettaglioAttivita(context);
    	}
    	return "ListControlliOK";
    }
   
  }
	
	
	
	
	
	
	
	public String executeCommandSearchImprese(ActionContext context) {
	   
	    
	    boolean reportExcelRichiesto = false;

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
	
	   
	    addModuleBean(context, "View Accounts", "Search Results");

	    //Prepare pagedListInfo
	    RicercaList organizationList = new RicercaList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");

		searchListInfo.setLink("GlobalSearch.do?command=SearchImprese");
	    
	    searchListInfo.setLink("GlobalSearch.do?command=SearchImprese");
	    searchListInfo.setListView("all");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    //Need to reset any sub PagedListInfos since this is a new account
	    this.resetPagedListInfo(context);
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
//		organizationList.setTarga(context.getRequest().getParameter("targa"));
		
		
		
		
		organizationList.buildList(db);
	       
	        context.getRequest().setAttribute("OrgList", organizationList);
	        context.getSession().setAttribute("previousSearchType", "accounts");
	       
	    } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    
	  
	    	return "ListImpreseOK";
	   
	  }
	
		
	public String executeCommandToExportExcelOperatori(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    return "ToExportExcelOperatori";
	}
	
	/*
	public String executeCommandStampaExportExcelOperatori(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListOperatori = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    
	    String fileName = "Report_ricerca_globale_operatori.xls";
	    context.getResponse().setContentType( "application/xls" );
	    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	

	    HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("Operatori");
	    HSSFRow rowHeader = sheet.createRow(0);
	   
	    HSSFFont font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle cellStyle = workbook.createCellStyle();
	    cellStyle.setFont(font);
	    
	    
	    HSSFCell cellHeader1 = rowHeader.createCell((short)0);
	    cellHeader1.setCellValue("Ragione Sociale");
	    cellHeader1.setCellStyle(cellStyle);
	    HSSFCell cellHeader2 = rowHeader.createCell((short)1);
	    cellHeader2.setCellValue("ASL");
	    cellHeader2.setCellStyle(cellStyle);
	    HSSFCell cellHeader3 = rowHeader.createCell((short)2);
	    cellHeader3.setCellValue("Tipologia Operatore");
	    cellHeader3.setCellStyle(cellStyle);
	    HSSFCell cellHeader4 = rowHeader.createCell((short)3);
	    cellHeader4.setCellValue("Descrizione");
	    cellHeader4.setCellStyle(cellStyle);
	    HSSFCell cellHeader5 = rowHeader.createCell((short)4);
	    cellHeader5.setCellValue("Titolare");
	    cellHeader5.setCellStyle(cellStyle);
	    HSSFCell cellHeader6 = rowHeader.createCell((short)5);
	    cellHeader6.setCellValue("C.F Titolare");
	    cellHeader6.setCellStyle(cellStyle);
	    HSSFCell cellHeader7 = rowHeader.createCell((short)6);
	    cellHeader7.setCellValue("N.Registrazione");
	    cellHeader7.setCellStyle(cellStyle);
	    HSSFCell cellHeader8 = rowHeader.createCell((short)7);
	    cellHeader8.setCellValue("P.Iva");
	    cellHeader8.setCellStyle(cellStyle);
	    HSSFCell cellHeader9 = rowHeader.createCell((short)8);
	    cellHeader9.setCellValue("Stato");
	    cellHeader9.setCellStyle(cellStyle);
	    HSSFCell cellHeader10 = rowHeader.createCell((short)9);
	    cellHeader10.setCellValue("Targa");
	    cellHeader10.setCellStyle(cellStyle);
	    HSSFCell cellHeader11 = rowHeader.createCell((short)10);
	    cellHeader11.setCellValue("Cat. Rischio");
	    cellHeader11.setCellStyle(cellStyle);
	    HSSFCell cellHeader12 = rowHeader.createCell((short)11);
	    cellHeader12.setCellValue("Comune");
	    cellHeader12.setCellStyle(cellStyle);
	    HSSFCell cellHeader13 = rowHeader.createCell((short)12);
	    cellHeader13.setCellValue("Provincia");
	    cellHeader13.setCellStyle(cellStyle);
	    
	    
	    int numRiga=1;
	    HSSFRow row = null;
	    HSSFCell cell1 = null;
	    HSSFCell cell2 = null;
	    HSSFCell cell3 = null;
	    HSSFCell cell4 = null;
	    HSSFCell cell5 = null;
	    HSSFCell cell6 = null;
	    HSSFCell cell7 = null;
	    HSSFCell cell8 = null;
	    HSSFCell cell9 = null;
	    HSSFCell cell10 = null;
	    HSSFCell cell11 = null;
	    HSSFCell cell12 = null;
	    HSSFCell cell13 = null;
	    
	    for(OrganizationView o : orgListOperatori){
	    	row = sheet.createRow(numRiga);
	    	
	    	cell1 = row.createCell((short)0);
		    cell1.setCellValue(o.getName().toUpperCase());
		    cell2 = row.createCell((short)1);
		    cell2.setCellValue(o.getAsl());
		    cell3 = row.createCell((short)2);
		    cell3.setCellValue(o.getTipologia_operatore());
		    cell4 = row.createCell((short)3);
		    cell4.setCellValue(o.getAlertText());
		    cell5 = row.createCell((short)4);
		    cell5.setCellValue(o.getTitolare());
		    cell6 = row.createCell((short)5);
		    cell6.setCellValue(o.getCodiceFiscaleRappresentante());
		    cell7 = row.createCell((short)6);
		    if(o.getTipologia_operatore().equals("Stabilimento") || o.getTipologia_operatore().equals("SOA")){
		    	cell7.setCellValue(o.getNum_aut());
		    }
		    else{
		    	cell7.setCellValue(o.getN_reg());
		    }
		    cell8 = row.createCell((short)7);
		    cell8.setCellValue(o.getPartitaIva());
		    cell9 = row.createCell((short)8);
		    if(o.getTipologia_operatore().equals("Impresa")){
		    	cell9.setCellValue(o.getStato_impresa());
		    }
		    else if(o.getTipologia_operatore().equals("Allevamento")){
		    	cell9.setCellValue(o.getStato_allevamento());
		    }
		    else{
		    	cell9.setCellValue(o.getStato());
		    }
		    cell10 = row.createCell((short)9);
		    cell10.setCellValue(o.getTarga());
		    cell11 = row.createCell((short)10);
		    cell11.setCellValue(o.getCategoriaRischio());
		    cell12 = row.createCell((short)11);
		    cell12.setCellValue(o.getCity());
		    cell13 = row.createCell((short)12);
		    cell13.setCellValue(o.getState());
		    
		    numRiga++;
		    
	    }
	    
	    try {
			workbook.write(context.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return "-none-";
	}
	*/
	
	
	/*
	public String executeCommandStampaExportExcelOperatori(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListOperatori = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    
	    String fileName = "Report_ricerca_globale_operatori.xls";
	    context.getResponse().setContentType( "application/xls" );
	    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	
	
	    HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("Operatori");
	    HSSFRow rowHeader = sheet.createRow(0);
	
	    HSSFFont font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle cellStyle = workbook.createCellStyle();
	    cellStyle.setFont(font);
	    
	    
	    HSSFCell cellHeader1 = rowHeader.createCell((short)0);
	    cellHeader1.setCellValue("Ragione Sociale");
	    cellHeader1.setCellStyle(cellStyle);
	    HSSFCell cellHeader2 = rowHeader.createCell((short)1);
	    cellHeader2.setCellValue("ASL");
	    cellHeader2.setCellStyle(cellStyle);
	    HSSFCell cellHeader3 = rowHeader.createCell((short)2);
	    cellHeader3.setCellValue("Tipologia Operatore");
	    cellHeader3.setCellStyle(cellStyle);
	    HSSFCell cellHeader4 = rowHeader.createCell((short)3);
	    cellHeader4.setCellValue("Descrizione");
	    cellHeader4.setCellStyle(cellStyle);
	    HSSFCell cellHeader5 = rowHeader.createCell((short)4);
	    cellHeader5.setCellValue("Titolare");
	    cellHeader5.setCellStyle(cellStyle);
	    HSSFCell cellHeader6 = rowHeader.createCell((short)5);
	    cellHeader6.setCellValue("C.F Titolare");
	    cellHeader6.setCellStyle(cellStyle);
	    HSSFCell cellHeader7 = rowHeader.createCell((short)6);
	    cellHeader7.setCellValue("N.Registrazione");
	    cellHeader7.setCellStyle(cellStyle);
	    HSSFCell cellHeader8 = rowHeader.createCell((short)7);
	    cellHeader8.setCellValue("P.Iva");
	    cellHeader8.setCellStyle(cellStyle);
	    HSSFCell cellHeader9 = rowHeader.createCell((short)8);
	    cellHeader9.setCellValue("Stato");
	    cellHeader9.setCellStyle(cellStyle);
	    HSSFCell cellHeader10 = rowHeader.createCell((short)9);
	    cellHeader10.setCellValue("Targa");
	    cellHeader10.setCellStyle(cellStyle);
	    HSSFCell cellHeader11 = rowHeader.createCell((short)10);
	    cellHeader11.setCellValue("Cat. Rischio");
	    cellHeader11.setCellStyle(cellStyle);
	    HSSFCell cellHeader12 = rowHeader.createCell((short)11);
	    cellHeader12.setCellValue("Comune");
	    cellHeader12.setCellStyle(cellStyle);
	    HSSFCell cellHeader13 = rowHeader.createCell((short)12);
	    cellHeader13.setCellValue("Provincia");
	    cellHeader13.setCellStyle(cellStyle);
	    
	    
	    int numRiga=1;
	    HSSFRow row = null;
	    HSSFCell cell1 = null;
	    HSSFCell cell2 = null;
	    HSSFCell cell3 = null;
	    HSSFCell cell4 = null;
	    HSSFCell cell5 = null;
	    HSSFCell cell6 = null;
	    HSSFCell cell7 = null;
	    HSSFCell cell8 = null;
	    HSSFCell cell9 = null;
	    HSSFCell cell10 = null;
	    HSSFCell cell11 = null;
	    HSSFCell cell12 = null;
	    HSSFCell cell13 = null;
	    
	    for(OrganizationView o : orgListOperatori){
	    	row = sheet.createRow(numRiga);
	    	
	    	cell1 = row.createCell((short)0);
		    cell1.setCellValue(o.getName().toUpperCase());
		    cell2 = row.createCell((short)1);
		    cell2.setCellValue(o.getAsl());
		    cell3 = row.createCell((short)2);
		    cell3.setCellValue(o.getTipologia_operatore());
		    cell4 = row.createCell((short)3);
		    cell4.setCellValue(o.getAlertText());
		    cell5 = row.createCell((short)4);
		    cell5.setCellValue(o.getTitolare());
		    cell6 = row.createCell((short)5);
		    cell6.setCellValue(o.getCodiceFiscaleRappresentante());
		    cell7 = row.createCell((short)6);
		    if(o.getTipologia_operatore().equals("Stabilimento") || o.getTipologia_operatore().equals("SOA")){
		    	cell7.setCellValue(o.getNum_aut());
		    }
		    else{
		    	cell7.setCellValue(o.getN_reg());
		    }
		    cell8 = row.createCell((short)7);
		    cell8.setCellValue(o.getPartitaIva());
		    cell9 = row.createCell((short)8);
		    if(o.getTipologia_operatore().equals("Impresa")){
		    	cell9.setCellValue(o.getStato_impresa());
		    }
		    else if(o.getTipologia_operatore().equals("Allevamento")){
		    	cell9.setCellValue(o.getStato_allevamento());
		    }
		    else{
		    	cell9.setCellValue(o.getStato());
		    }
		    cell10 = row.createCell((short)9);
		    cell10.setCellValue(o.getTarga());
		    cell11 = row.createCell((short)10);
		    cell11.setCellValue(o.getCategoriaRischio());
		    cell12 = row.createCell((short)11);
		    cell12.setCellValue(o.getCity());
		    cell13 = row.createCell((short)12);
		    cell13.setCellValue(o.getState());
		    
		    numRiga++;
		    
	    }
	    
	    try {
			workbook.write(context.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return "-none-";
	}
	*/
	
	
	public String executeCommandStampaExportExcelOperatori(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListOperatori = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    ArrayList<String> lista_controlli = orgListOperatori.getControlli_sanitari();
	    /*
	    String fileName = "Report_ricerca_globale_operatori.xls";
	    context.getResponse().setContentType( "application/xls" );
	    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
	    */	
	    
	    String fileName = "Report_ricerca_globale_operatori.xls";
	    //context.getResponse().setContentType( "application/zip" );
	    //context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
	   
	    Connection db = null ;
	    try {
	    	 db = this.getConnection(context); 
	    
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
			WritableWorkbook wb = Workbook.createWorkbook(bos);
			WritableSheet sheet = wb.createSheet("Operatori", 0);
			
			//Font
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setBackground(Colour.YELLOW);
			
			//Header
			sheet.addCell( new Label(0,0,"Ragione Sociale",headerFormat) );
			sheet.addCell( new Label(1,0,"ASL",headerFormat) );
			sheet.addCell( new Label(2,0,"Tipologia Operatore",headerFormat) );
			sheet.addCell( new Label(3,0,"Descrizione",headerFormat) );
			sheet.addCell( new Label(4,0,"Titolare",headerFormat) );
			sheet.addCell( new Label(5,0,"C.F Titolare",headerFormat) );
			sheet.addCell( new Label(6,0,"Detentore",headerFormat) );
			sheet.addCell( new Label(7,0,"C.F Detentore",headerFormat) );
			sheet.addCell( new Label(8,0,"N.Registrazione",headerFormat) );
			sheet.addCell( new Label(9,0,"P.Iva",headerFormat) );
			sheet.addCell( new Label(10,0,"Stato",headerFormat) );
			sheet.addCell( new Label(11,0,"Targa",headerFormat) );
			sheet.addCell( new Label(12,0,"Cat. Rischio",headerFormat) );
			sheet.addCell( new Label(13,0,"Comune",headerFormat) );
			sheet.addCell( new Label(14,0,"Provincia",headerFormat) );
			sheet.addCell( new Label(15,0,"Tipologia Struttura",headerFormat) );
			sheet.addCell( new Label(16,0,"Orientamento Produttivo",headerFormat) );
			sheet.addCell( new Label(17,0,"Specie Allevata",headerFormat) );
			sheet.addCell( new Label(18,0,"Tot. Capi",headerFormat) );
			sheet.addCell( new Label(19,0,"N. Capi > 6 settimane",headerFormat) );
			sheet.addCell( new Label(20,0,"N. Capi > 1 anno",headerFormat) );
			int prossima_colonna = 21 ;
			
			for (String controllo_malattia : lista_controlli)
			{
				sheet.addCell( new Label(prossima_colonna,0,"(Controllo "+controllo_malattia+" - Qualifica - Data Rilevazione  )",headerFormat) );
				
				prossima_colonna++ ;
			}
			
			
			//Content
			int numFoglio = 1;
			int numRiga = 1;
			for(OrganizationView o : orgListOperatori){
				
				//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
				if(numRiga % 60001 == 0){
					
					numRiga = 1;
					numFoglio++;
					sheet = wb.createSheet("Operatori-" + numFoglio, numFoglio);
					
					//Riscrivo l'Header
					sheet.addCell( new Label(0,0,"Ragione Sociale",headerFormat) );
					sheet.addCell( new Label(1,0,"ASL",headerFormat) );
					sheet.addCell( new Label(2,0,"Tipologia Operatore",headerFormat) );
					sheet.addCell( new Label(3,0,"Descrizione",headerFormat) );
					sheet.addCell( new Label(4,0,"Titolare",headerFormat) );
					sheet.addCell( new Label(5,0,"C.F Titolare",headerFormat) );
					sheet.addCell( new Label(6,0,"Detentore",headerFormat) );
					sheet.addCell( new Label(7,0,"C.F Detentore",headerFormat) );
					sheet.addCell( new Label(8,0,"N.Registrazione",headerFormat) );
					sheet.addCell( new Label(9,0,"P.Iva",headerFormat) );
					sheet.addCell( new Label(10,0,"Stato",headerFormat) );
					sheet.addCell( new Label(11,0,"Targa",headerFormat) );
					sheet.addCell( new Label(12,0,"Cat. Rischio",headerFormat) );
					sheet.addCell( new Label(13,0,"Comune",headerFormat) );
					sheet.addCell( new Label(14,0,"Provincia",headerFormat) );
					sheet.addCell( new Label(15,0,"Tipologia Struttura",headerFormat) );
					sheet.addCell( new Label(16,0,"Orientamento Produttivo",headerFormat) );
					sheet.addCell( new Label(17,0,"Specie Allevata",headerFormat) );
					sheet.addCell( new Label(18,0,"Tot. Capi",headerFormat) );
					sheet.addCell( new Label(19,0,"N. Capi > 6 settimane",headerFormat) );
					sheet.addCell( new Label(20,0,"N. Capi > 1 anno",headerFormat) );
					prossima_colonna = 21 ;
					for (String controllo_malattia : lista_controlli)
					{
						sheet.addCell( new Label(prossima_colonna,0,controllo_malattia,headerFormat) );
						prossima_colonna++ ;
					}
				}
				
				sheet.addCell( new Label(0,numRiga,o.getName().toUpperCase()) );
				sheet.addCell( new Label(1,numRiga,o.getAsl()) );
				sheet.addCell( new Label(2,numRiga,o.getTipologia_operatore()) );
				sheet.addCell( new Label(3,numRiga,o.getAlertText()) );
				sheet.addCell( new Label(4,numRiga,o.getTitolare()) );
				sheet.addCell( new Label(5,numRiga,o.getCodiceFiscaleRappresentante()) );
				sheet.addCell( new Label(6,numRiga,o.getNome_detentore()) );
				sheet.addCell( new Label(7,numRiga,o.getCf_detentore()) );
				if(o.getTipologia_operatore().toLowerCase().contains("853") || o.getTipologia_operatore().equals("SOA")){
					sheet.addCell( new Label(8,numRiga,o.getNum_aut()) );
			    }
			    else{
			    	sheet.addCell( new Label(8,numRiga,o.getN_reg()) );
			    }
				sheet.addCell( new Label(9,numRiga,o.getPartitaIva()) );
				if(o.getTipologia_operatore().contains("852")){
					sheet.addCell( new Label(10,numRiga,o.getStato_impresa()) );
			    }
			    else if(o.getTipologia_operatore().toLowerCase().contains("zootecnic")){
			    	sheet.addCell( new Label(10,numRiga,o.getStato_allevamento()) );
			    }
			    else{
			    	sheet.addCell( new Label(10,numRiga,o.getStato()) );
			    }
				sheet.addCell( new Label(11,numRiga,o.getTarga()) );
				sheet.addCell( new Label(12,numRiga,o.getCategoriaRischio()) );
				sheet.addCell( new Label(13,numRiga,o.getCity()) );
				sheet.addCell( new Label(14,numRiga,o.getState()) );
				sheet.addCell( new Label(15,numRiga,o.getTipologia_struttura()) );
				sheet.addCell( new Label(16,numRiga,o.getOrientamento_produttivo()) );
				sheet.addCell( new Label(17,numRiga,o.getSpecie_allevata()) );
				sheet.addCell( new Label(18,numRiga,o.getNum_capi()) );
				sheet.addCell( new Label(19,numRiga,o.getNum_capi_sei_settimane()) );
				sheet.addCell( new Label(20,numRiga,o.getNum_capi_un_anno()) );
				
				String stato_sanitario = o.getStato_sanitario();
				if (o.getTipologia_operatore().toLowerCase().contains("zootecnic"))
				{
				if(stato_sanitario!=null && !stato_sanitario.equals(""))
				{
					String[] controlli_sato_san = stato_sanitario.split("@@");
					for (int indice = 0 ; indice<controlli_sato_san.length ; indice ++)
					{
						if (!controlli_sato_san.equals(""))
						{
							String descrizione_malattia = controlli_sato_san[indice].split("-")[0];
							if (!descrizione_malattia.equals(""))
							{
								int j = 0 ;
								for (String controllo_malattia : lista_controlli)
								{
									if (descrizione_malattia.equals(controllo_malattia))
									{
										sheet.addCell( new Label(21+j,numRiga,controlli_sato_san[indice].replaceAll("-", " - ")) );
										break;
									}
									j ++ ;
								}
							}

						}
					}
				}
				}
				else
				{
					for (int j=0;j< lista_controlli.size();j++)
					{
						sheet.addCell( new Label(21+j,numRiga,"N.D.") );
					}
				}
				
				numRiga++;
			}
			
			//Write & Close
			wb.write();
			wb.close();
			
			if(bos.size() > (1024 * 1024 * 10) ){
				
				fileName += ".zip";
			    context.getResponse().setContentType( "application/zip" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				
				//Inizio Zip
				GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
				
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				
				int lunghezza;
				byte[] buffer = new byte[1024];
			    while ((lunghezza = bis.read(buffer)) > 0){
			    	zipOutputStream.write(buffer, 0, lunghezza);
			    }
			    
			    bos.close();
			    bis.close();
			    zipOutputStream.finish();
			    zipOutputStream.close();
			      
				//Fine Zip
				
			}
			else{
				context.getResponse().setContentType( "application/xls" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				context.getResponse().getOutputStream().write(bos.toByteArray());
			}
			
			
		} 
	    catch (IOException ioe) {
			ioe.printStackTrace();
		}
	    catch(WriteException we){
	    	we.printStackTrace();
	    }
	    catch(SQLException e){
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	this.freeConnection(context, db);
	    }
	    
	    return "-none-";
	}

	public String executeCommandToExportExcelAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    return "ToExportExcelAttivita";
	}
	
	/*
	public String executeCommandStampaExportExcelAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListAttivita = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    
	    String fileName = "Report_ricerca_globale_attivita.xls";
	    context.getResponse().setContentType( "application/xls" );
	    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	

	    HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("Attivita'");
	    HSSFRow rowHeader = sheet.createRow(0);
	    HSSFCellStyle cellStyle = workbook.createCellStyle();
	    HSSFFont font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    cellStyle.setFont(font);
	    
	    
	    HSSFCell cellHeader1 = rowHeader.createCell((short)0);
	    cellHeader1.setCellValue("Identificativo");
	    cellHeader1.setCellStyle(cellStyle);
	    HSSFCell cellHeader2 = rowHeader.createCell((short)1);
	    cellHeader2.setCellValue("Numero Verbale");
	    cellHeader2.setCellStyle(cellStyle);
	    HSSFCell cellHeader3 = rowHeader.createCell((short)2);
	    cellHeader3.setCellValue("Tipologia Attivita'");
	    cellHeader3.setCellStyle(cellStyle);
	    HSSFCell cellHeader4 = rowHeader.createCell((short)3);
	    cellHeader4.setCellValue("ASL");
	    cellHeader4.setCellStyle(cellStyle);
	    HSSFCell cellHeader5 = rowHeader.createCell((short)4);
	    cellHeader5.setCellValue("Ragione Sociale");
	    cellHeader5.setCellStyle(cellStyle);
	    HSSFCell cellHeader6 = rowHeader.createCell((short)5);
	    cellHeader6.setCellValue("Tipologia Operatore");
	    cellHeader6.setCellStyle(cellStyle);
	    HSSFCell cellHeader7 = rowHeader.createCell((short)6);
	    cellHeader7.setCellValue("Data Controllo");
	    cellHeader7.setCellStyle(cellStyle);
	    
	    
	    int numRiga=1;
	    for(OrganizationView o : orgListAttivita){
	    	HSSFRow row = sheet.createRow(numRiga);
	    	HSSFCell cell1 = row.createCell((short)0);
		    cell1.setCellValue(o.getIdControllo());
		    HSSFCell cell2 = row.createCell((short)1);
		    cell2.setCellValue(o.getNum_verbale());
		    HSSFCell cell3 = row.createCell((short)2);
		    cell3.setCellValue(o.getTipologia_campioni());
		    HSSFCell cell4 = row.createCell((short)3);
		    cell4.setCellValue(o.getAsl());
		    HSSFCell cell5 = row.createCell((short)4);
		    cell5.setCellValue(o.getName());
		    HSSFCell cell6 = row.createCell((short)5);
		    cell6.setCellValue(o.getTipologia_operatore());
		    HSSFCell cell7 = row.createCell((short)6);
		    cell7.setCellValue(o.getDataControllo());
		    
		    numRiga++;
	    }
	    
	    try {
			workbook.write(context.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return "-none-";
	}
	*/
	
	
	public String executeCommandStampaExportExcelAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListAttivita = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    String fileName = "Report_ricerca_globale_attivita.xls";
	    
	    try {
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
			WritableWorkbook wb = Workbook.createWorkbook(bos);
			WritableSheet sheet = wb.createSheet("Attivita'", 0);
			
			//Font
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setBackground(Colour.YELLOW);
			
			//Header
			sheet.addCell( new Label(0,0,"Identificativo",headerFormat) );
			sheet.addCell( new Label(1,0,"Numero Verbale",headerFormat) );
			sheet.addCell( new Label(2,0,"Tipologia Attivita'",headerFormat) );
			sheet.addCell( new Label(3,0,"ASL",headerFormat) );
			sheet.addCell( new Label(4,0,"Ragione Sociale",headerFormat) );
			sheet.addCell( new Label(5,0,"Tipologia Operatore",headerFormat) );
			sheet.addCell( new Label(6,0,"Data Controllo",headerFormat) );
			
			//Content
			int numFoglio = 1;
			int numRiga = 1;
			for(OrganizationView o : orgListAttivita){
				
				//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
				if(numRiga % 60001 == 0){
					
					numRiga = 1;
					numFoglio++;
					sheet = wb.createSheet("Attivita'-" + numFoglio, numFoglio);
					
					//Riscrivo l'Header
					sheet.addCell( new Label(0,0,"Identificativo",headerFormat) );
					sheet.addCell( new Label(1,0,"Numero Verbale",headerFormat) );
					sheet.addCell( new Label(2,0,"Tipologia Attivita'",headerFormat) );
					sheet.addCell( new Label(3,0,"ASL",headerFormat) );
					sheet.addCell( new Label(4,0,"Ragione Sociale",headerFormat) );
					sheet.addCell( new Label(5,0,"Tipologia Operatore",headerFormat) );
					sheet.addCell( new Label(6,0,"Data Controllo",headerFormat) );
				}
				
				sheet.addCell( new Label(0,numRiga,o.getIdControllo()) );
				sheet.addCell( new Label(1,numRiga,o.getNum_verbale()) );
				sheet.addCell( new Label(2,numRiga,o.getTipologia_campioni()) );
				sheet.addCell( new Label(3,numRiga,o.getAsl()) );
				sheet.addCell( new Label(4,numRiga,o.getName()) );
				sheet.addCell( new Label(5,numRiga,o.getTipologia_operatore()) );
				sheet.addCell( new Label(6,numRiga,o.getDataControllo()) );
				
				numRiga++;
			}
			
			//Write & Close
			wb.write();
			wb.close();
			
			if(bos.size() > (1024 * 1024 * 10) ){
				
				fileName += ".zip";
			    context.getResponse().setContentType( "application/zip" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				
				//Inizio Zip
				GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
				
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				
				int lunghezza;
				byte[] buffer = new byte[1024];
			    while ((lunghezza = bis.read(buffer)) > 0){
			    	zipOutputStream.write(buffer, 0, lunghezza);
			    }
			    
			    bos.close();
			    bis.close();
			    zipOutputStream.finish();
			    zipOutputStream.close();
				//Fine Zip
				
			}
			else{
				context.getResponse().setContentType( "application/xls" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				context.getResponse().getOutputStream().write(bos.toByteArray());
			}
			
		} 
	    catch (IOException ioe) {
			ioe.printStackTrace();
		}
	    catch(WriteException we){
	    	we.printStackTrace();
	    }
	    
	    return "-none-";
	    
	}
	
	public String executeCommandStampaExportExcelCampioni(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListAttivita = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    String fileName = "Report_ricerca_globale_campioni.xls";
	    
	    try {
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
			WritableWorkbook wb = Workbook.createWorkbook(bos);
			WritableSheet sheet = wb.createSheet("Campioni", 0);
			
			//Font
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setBackground(Colour.YELLOW);
			
			//Header
			sheet.addCell( new Label(0,0,"Identificativo Campione",headerFormat) );
			sheet.addCell( new Label(1,0,"Numero Verbale/Barcode",headerFormat) );
			sheet.addCell( new Label(2,0,"Motivazione",headerFormat) );
			sheet.addCell( new Label(3,0,"Matrice",headerFormat) );
			sheet.addCell( new Label(4,0,"Analita",headerFormat) );
			sheet.addCell( new Label(5,0,"Esito/Stato",headerFormat) );
			sheet.addCell( new Label(6,0,"ASL",headerFormat) );
			sheet.addCell( new Label(7,0,"Ragione Sociale",headerFormat) );
			sheet.addCell( new Label(8,0,"Tipologia Operatore",headerFormat) );
			sheet.addCell( new Label(9,0,"Data Prelievo",headerFormat) );
		
			
			//Content
			int numFoglio = 1;
			int numRiga = 1;
			for(OrganizationView o : orgListAttivita){
				
				//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
				if(numRiga % 60001 == 0){
					
					numRiga = 1;
					numFoglio++;
					sheet = wb.createSheet("Campioni-" + numFoglio, numFoglio);
					
					//Riscrivo l'Header
					sheet.addCell( new Label(0,0,"Identificativo Campione",headerFormat) );
					sheet.addCell( new Label(1,0,"Numero Verbale/Barcode",headerFormat) );
					sheet.addCell( new Label(2,0,"Motivazione",headerFormat) );
					sheet.addCell( new Label(3,0,"Matrice",headerFormat) );
					sheet.addCell( new Label(4,0,"Analita",headerFormat) );
					sheet.addCell( new Label(5,0,"Esito/Stato",headerFormat) );
					sheet.addCell( new Label(6,0,"ASL",headerFormat) );
					sheet.addCell( new Label(7,0,"Ragione Sociale",headerFormat) );
					sheet.addCell( new Label(8,0,"Tipologia Operatore",headerFormat) );
					sheet.addCell( new Label(9,0,"Data Prelievo",headerFormat) );
				}
				
				sheet.addCell( new Label(0,numRiga,o.getTicketId()) );
				sheet.addCell( new Label(1,numRiga,o.getNum_verbale()) );
				sheet.addCell( new Label(2,numRiga,o.getMotivazioneCampione()));
				sheet.addCell( new Label(3,numRiga,o.getMatrice()));
				sheet.addCell( new Label(4,numRiga,o.getAnalita()));
				sheet.addCell( new Label(5,numRiga,o.getEsito()));
				sheet.addCell( new Label(6,numRiga,o.getAsl()) );
				sheet.addCell( new Label(7,numRiga,o.getName()) );
				sheet.addCell( new Label(8,numRiga,o.getTipologia_operatore()) );
				sheet.addCell( new Label(9,numRiga,o.getDataControllo()) );
				
				numRiga++;
			}
			
			//Write & Close
			wb.write();
			wb.close();
			
			if(bos.size() > (1024 * 1024 * 10) ){
				
				fileName += ".zip";
			    context.getResponse().setContentType( "application/zip" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				
				//Inizio Zip
				GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
				
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				
				int lunghezza;
				byte[] buffer = new byte[1024];
			    while ((lunghezza = bis.read(buffer)) > 0){
			    	zipOutputStream.write(buffer, 0, lunghezza);
			    }
			    
			    bos.close();
			    bis.close();
			    zipOutputStream.finish();
			    zipOutputStream.close();
				//Fine Zip
				
			}
			else{
				context.getResponse().setContentType( "application/xls" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				context.getResponse().getOutputStream().write(bos.toByteArray());
			}
			
		} 
	    catch (IOException ioe) {
			ioe.printStackTrace();
		}
	    catch(WriteException we){
	    	we.printStackTrace();
	    }
	    
	    return "-none-";
	    
	}
	
	public String executeCommandToExportExcelDettaglioAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    return "ToExportExcelDettaglioAttivita";
	}
	
	/*
	public String executeCommandStampaExportExcelDettaglioAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListDettaglio = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    
	    String fileName = "Report_ricerca_globale_dettaglio_attivita.xls";
	    context.getResponse().setContentType( "application/xls" );
	    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 	

	    HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("Dettaglio Attivita'");
	    HSSFRow rowHeader = sheet.createRow(0);
	    HSSFCellStyle cellStyle = workbook.createCellStyle();
	    HSSFFont font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    cellStyle.setFont(font);
	    
	    
	    HSSFCell cellHeader1 = rowHeader.createCell((short)0);
	    cellHeader1.setCellValue("Ragione Sociale");
	    cellHeader1.setCellStyle(cellStyle);
	    HSSFCell cellHeader2 = rowHeader.createCell((short)1);
	    cellHeader2.setCellValue("ASL");
	    cellHeader2.setCellStyle(cellStyle);
	    HSSFCell cellHeader3 = rowHeader.createCell((short)2);
	    cellHeader3.setCellValue("Tipologia Operatore");
	    cellHeader3.setCellStyle(cellStyle);
	    HSSFCell cellHeader4 = rowHeader.createCell((short)3);
	    cellHeader4.setCellValue("Numero Controlli/Sottoattivita'");
	    cellHeader4.setCellStyle(cellStyle);
	    HSSFCell cellHeader5 = rowHeader.createCell((short)4);
	    cellHeader5.setCellValue("Tipologia Attivita'");
	    cellHeader5.setCellStyle(cellStyle);
	    HSSFCell cellHeader6 = rowHeader.createCell((short)5);
	    cellHeader6.setCellValue("Cat. Rischio");
	    cellHeader6.setCellStyle(cellStyle);
	    
	    
	    int numRiga=1;
	    for(OrganizationView o : orgListDettaglio){
	    	HSSFRow row = sheet.createRow(numRiga);
	    	
	    	HSSFCell cell1 = row.createCell((short)0);
		    cell1.setCellValue(o.getName());
		    HSSFCell cell2 = row.createCell((short)1);
		    cell2.setCellValue(o.getAsl());
		    HSSFCell cell3 = row.createCell((short)2);
		    cell3.setCellValue(o.getTipologia_operatore());
		    HSSFCell cell4 = row.createCell((short)3);
		    cell4.setCellValue(o.getCount());
		    HSSFCell cell5 = row.createCell((short)4);
		    cell5.setCellValue(o.getTipologia_campioni());
		    HSSFCell cell6 = row.createCell((short)5);
		    cell6.setCellValue(o.getCategoriaRischio());
		    
		    numRiga++;
	    }
	    
	    try {
			workbook.write(context.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return "-none-";
		
	}
	*/
	
	public String executeCommandStampaExportExcelDettaglioAttivita(ActionContext context) {
	    if (!hasPermission(context, "global-search-view")) {
	      return ("PermissionError");
	    }
	    
	    OrganizationListViewMinimale orgListDettagli = (OrganizationListViewMinimale)context.getSession().getAttribute("OrgList");
	    String fileName = "Report_ricerca_globale_dettagli_attivita.xls";
	    
	    try {
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	
			WritableWorkbook wb = Workbook.createWorkbook(bos);
			WritableSheet sheet = wb.createSheet("Dettagli Attivita'", 0);
			
			//Font
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
			headerFormat.setBackground(Colour.YELLOW);
			
			//Header
			sheet.addCell( new Label(0,0,"Ragione Sociale",headerFormat) );
			sheet.addCell( new Label(1,0,"ASL",headerFormat) );
			sheet.addCell( new Label(2,0,"Tipologia Operatore",headerFormat) );
			sheet.addCell( new Label(3,0,"Numero Controlli/Sottoattivita'",headerFormat) );
			sheet.addCell( new Label(4,0,"Tipologia Attivita'",headerFormat) );
			sheet.addCell( new Label(5,0,"Cat. Rischio",headerFormat) );
			
			//Content
			int numFoglio = 1;
			int numRiga = 1;
			for(OrganizationView o : orgListDettagli){
				
				//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
				if(numRiga % 60001 == 0){
					
					numRiga = 1;
					numFoglio++;
					sheet = wb.createSheet("Dettagli Attivita'-" + numFoglio, numFoglio);
					
					//Riscrivo l'Header
					sheet.addCell( new Label(0,0,"Ragione Sociale",headerFormat) );
					sheet.addCell( new Label(1,0,"ASL",headerFormat) );
					sheet.addCell( new Label(2,0,"Tipologia Operatore",headerFormat) );
					sheet.addCell( new Label(3,0,"Numero Controlli/Sottoattivita'",headerFormat) );
					sheet.addCell( new Label(4,0,"Tipologia Attivita'",headerFormat) );
					sheet.addCell( new Label(5,0,"Cat. Rischio",headerFormat) );
				}
				
				sheet.addCell( new Label(0,numRiga,o.getName()) );
				sheet.addCell( new Label(1,numRiga,o.getAsl()) );
				sheet.addCell( new Label(2,numRiga,o.getTipologia_operatore()) );
				sheet.addCell( new Label(3,numRiga,"" + o.getCount()) );
				sheet.addCell( new Label(4,numRiga,o.getTipologia_campioni()) );
				sheet.addCell( new Label(5,numRiga,o.getCategoriaRischio()) );
				
				numRiga++;
			}
			
			//Write & Close
			wb.write();
			wb.close();
			
			if(bos.size() > (1024 * 1024 * 10) ){
				
				fileName += ".zip";
			    context.getResponse().setContentType( "application/zip" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				
				//Inizio Zip
				GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
				
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				
				int lunghezza;
				byte[] buffer = new byte[1024];
			    while ((lunghezza = bis.read(buffer)) > 0){
			    	zipOutputStream.write(buffer, 0, lunghezza);
			    }
			    
			    bos.close();
			    bis.close();
			    zipOutputStream.finish();
			    zipOutputStream.close();
				//Fine Zip
				
			}
			else{
				context.getResponse().setContentType( "application/xls" );
			    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
				context.getResponse().getOutputStream().write(bos.toByteArray());
			}
			
		} 
	    catch (IOException ioe) {
			ioe.printStackTrace();
		}
	    catch(WriteException we){
	    	we.printStackTrace();
	    }
	    
	    return "-none-";
	    
	    
	}



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
	
	public String executeCommandSearchFormAMR(ActionContext context) {
	    if (!(hasPermission(context, "global-search-amr-view"))) {
	      return ("PermissionError");
	    }
	    
	    //Bypass search form for portal users
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    
	    try {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	     // siteList.addItem(-1, "-- SELEZIONA VOCE --");
	      siteList.addItem(-1,  "-- TUTTE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      this.deletePagedListInfo(context, "searchListInfo");
	      
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {	
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchAMROK");
	    
	  }

	
	public String executeCommandSearchFormAcquacoltura(ActionContext context) 
	{
	    if (!(hasPermission(context, "global-search-acquacoltura-view"))) 
	    {
	      return ("PermissionError");
	    }
	    
	    //Bypass search form for portal users
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    
	    try 
	    {
	      db = getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	     // siteList.addItem(-1, "-- SELEZIONA VOCE --");
	      siteList.addItem(-1,  "-- TUTTE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      this.deletePagedListInfo(context, "searchListInfo");
	      
	      
	    } 
	    catch (Exception e) 
	    {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } 
	    finally 
	    {	
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Search Accounts", "Accounts Search");
	    return ("SearchAcquacolturaOK");
	    
	  }
	
	
	public String executeCommandSearchAMR(ActionContext context) {
	    if (!hasPermission(context, "global-search-amr-view")) {
	      return ("PermissionError");
	    }
	    
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
	    String asl = (String) context.getRequest().getParameter("searchcodeOrgSiteId");
	    String num_verbale = (String) context.getRequest().getParameter("searchAccountNumVerbale");
		String idC = (String) context.getRequest().getParameter("searchAccountIdentificativo");
	    String inizio = (String) context.getRequest().getParameter("searchtimestampInizio");
	    String fine = (String) context.getRequest().getParameter("searchtimestampFine");
	    
	    int idAsl = -1;
	    int idControllo = -1;
	    
	    try {idControllo = Integer.parseInt(idC);} catch(Exception e) {}
	    try {idAsl = Integer.parseInt(asl);} catch(Exception e) {}

	    ArrayList<String> listaControlli = new ArrayList<String>();
	    Connection db = null;
	    try {
	    	
	      db = this.getConnection(context); 
	      
	      LookupList SiteList = new LookupList(db, "lookup_site_id");
		  context.getRequest().setAttribute("SiteList", SiteList);
	      
	      PreparedStatement pst = db.prepareStatement("select * from lista_controlli_amr(?, ?, ?, ?, ?)");
	      int i = 0;
	      pst.setInt(++i, idControllo);
	      pst.setInt(++i, idAsl);
	      pst.setString(++i, num_verbale);
	      pst.setString(++i, inizio);
	      pst.setString(++i, fine);	      
	      
	      ResultSet rs = pst.executeQuery();
	      while (rs.next()){
	    	  String res =  rs.getString("id_controllo") + ";;" +   rs.getString("id_asl") + ";;" + rs.getString("piano_monitoraggio") + ";;" +  rs.getString("num_verbale_amr") + ";;" +  rs.getString("data_controllo");
	    	  listaControlli.add(res);
	      }
	        } catch (Exception e) {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute("listaControlli", listaControlli);
	    return "ListControlliAMROK";
	    
	   
	  }
	
	public String executeCommandSearchAcquacoltura(ActionContext context) 
	{
	    if (!hasPermission(context, "global-search-acquacoltura-view")) 
	    {
	      return ("PermissionError");
	    }
	    
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
	    String asl = (String) context.getRequest().getParameter("searchcodeOrgSiteId");
		String idC = (String) context.getRequest().getParameter("searchAccountIdentificativo");
	    String inizio = (String) context.getRequest().getParameter("searchtimestampInizio");
	    String fine = (String) context.getRequest().getParameter("searchtimestampFine");
	    
	    int idAsl = -1;
	    int idControllo = -1;
	    
	    try {idControllo = Integer.parseInt(idC);} catch(Exception e) {}
	    try {idAsl = Integer.parseInt(asl);} catch(Exception e) {}

	    ArrayList<String> listaControlli = new ArrayList<String>();
	    Connection db = null;
	    try {
	    	
	      db = this.getConnection(context); 
	      
	      LookupList SiteList = new LookupList(db, "lookup_site_id");
		  context.getRequest().setAttribute("SiteList", SiteList);
	      
	      PreparedStatement pst = db.prepareStatement("select * from lista_controlli_acquacoltura(?, ?, ?, ?)");
	      int i = 0;
	      pst.setInt(++i, idControllo);
	      pst.setInt(++i, idAsl);
	      pst.setString(++i, inizio);
	      pst.setString(++i, fine);	      
	      
	      ResultSet rs = pst.executeQuery();
	      while (rs.next())
	      {
	    	  String res =  rs.getString("id_controllo") + ";;" +   rs.getString("id_asl") + ";;" + rs.getString("piano_monitoraggio") + ";;" +  rs.getString("data_controllo");
	    	  listaControlli.add(res);
	      }
	        
	    } 
	    catch (Exception e) 
	    {
	      //Go through the SystemError process
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } 
	    finally 
	    {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute("listaControlli", listaControlli);
	    return "ListControlliAcquacolturaOK";
	    
	   
	  }
		
	
}