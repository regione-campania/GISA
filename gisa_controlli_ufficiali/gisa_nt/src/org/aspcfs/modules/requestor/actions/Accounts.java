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
package org.aspcfs.modules.requestor.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.requestor.base.Comuni;
import org.aspcfs.modules.requestor.base.Organization;
import org.aspcfs.modules.requestor.base.OrganizationAddress;
import org.aspcfs.modules.requestor.base.OrganizationAddressList;
import org.aspcfs.modules.requestor.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.actions.ExternalContacts;


import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.lineeattivita.base.RelAtecoLineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.actions.OperatoreAction;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.InformazioniStabilimento;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.*;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

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
 
  
  public String executeCommandGeneraCodiceOsa(ActionContext context) throws SQLException {
	  if (!hasPermission(context, "requestor-requestor-generaosa-view")) {
	      return ("PermissionError");
	    }
	  Connection db = null;
	  Organization thisOrganization = null;
	  Organization oldOrg = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Organization(
		          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      oldOrg = new Organization(
		          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
		      
	      /*
	      Comuni comune = new Comuni(db, thisOrganization.getCity().toUpperCase());
	      comune.queryRecord(db, thisOrganization.getCity().toUpperCase());*/
	      thisOrganization.setModifiedBy( getUserId(context) );
	      //if(thisOrganization.getAccountNumber()==null || thisOrganization.getAccountNumber().equals("")){
	      boolean cod = generaCodice(db, thisOrganization, true);
	      context.getRequest().setAttribute("codiceEsistente", cod);
	     // }
				 
	  
	    } catch (Exception e) {
	    	
	    	  e.printStackTrace();
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		      //return ("DetailsOK");
		    } finally {
		      this.freeConnection(context, db);
		    }
	  return executeCommandDetails(context);
  }

  public static boolean generaCodice(Connection db, org.aspcfs.modules.requestor.base.Organization thisOrganization, boolean tramiteDIA)
		throws SQLException
 {
	  boolean codice = false;
	if(thisOrganization.getTipoDest().equalsIgnoreCase("Es. Commerciale"))
	{
//		  String city2 = thisOrganization.getCity2(db, thisOrganization.getOrgId());
//		  city2 = (city2 == null) ? (null) : (city2.toUpperCase());
//		  Comuni comune = new Comuni( db, city2 );
		
		  Comuni comune = new Comuni(db, thisOrganization.getId(), 5);
		  codice = thisOrganization.generaCodice(db, comune.getCodice(), tramiteDIA);
	}
	else{
		//if(thisOrganization.getTipoDest().equalsIgnoreCase("distributori")){
//			String city2 = thisOrganization.getCity();
//			  city2 = (city2 == null) ? (null) : (city2.toUpperCase());
//			  Comuni comune = new Comuni( db, city2 );
			  Comuni comune = new Comuni(db, thisOrganization.getId(), 1);
			  codice = thisOrganization.generaCodice(db, comune.getCodice(), tramiteDIA);
			
		/*}else{
			String city3 = thisOrganization.getCity();
			  city3 = (city3 == null) ? (null) : (city3.toUpperCase());
			  Comuni comune = new Comuni( db, city3 );
			  codice = thisOrganization.generaCodice(db, comune.getCodice(), tramiteDIA);
			
		}*/
		
	}
return codice;
 }
  
  
  
  public String executeCommandCambiaInOsa(ActionContext context) throws SQLException {
	  if (!hasPermission(context, "requestor-requestor-cambiaosa-view")) {
	      return ("PermissionError");
	    }
	  
	  boolean recordEnabled = false;
	    Organization thisOrganization = null;
	    Organization oldOrg = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Organization(
	          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      
	      oldOrg = new Organization(
		          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      
	      thisOrganization.setModifiedBy( getUserId(context) );
	      recordEnabled = thisOrganization.cambiaInOsa(db);
	     
	      
	     
	      
	    } catch (Exception e) {
	      
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	      //return ("DetailsOK");
	    } finally {
	      this.freeConnection(context, db);
	    }
	
		//return ("-none-");
		return ("gotoRequestor");
  }
  


  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "requestor-requestor-view"))) {
      return ("PermissionError");
    }
    
   /* if (!(hasPermission(context, "request-view"))) {
        return ("PermissionError");
      }
*/
    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      
      
      
/*    LookupList llist=new LookupList(db,"lookup_requestor_types");
      llist.addItem(-1, "-nessuno-");
      context.getRequest().setAttribute("llist", llist);
*/
      LookupList comuniList = new LookupList(db, "lookup_comuni");
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
      
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-2,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);

		

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);
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
    if (!hasPermission(context, "requestor-requestor-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
     // carica_lookup_ateco_nel_context(context, "" + -1, db);   
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
        
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList countryList = new LookupList(db, "lookup_nazioni");
		// SOLO PAESI EUROPEI
		countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
		// ITALIA NON SELEZIONABILE
		countryList.removeElementByValue("Italia");
		countryList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("CountryList", countryList);
		
      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);

		

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);
      Organization newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");      
          
     // UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      
      //newOrg.getProvince();
      if (newOrg.getEnteredBy() != -1) {
         
        context.getRequest().setAttribute("OrgDetails", newOrg);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
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
    if (!hasPermission(context, "requestor-requestor-view")) {
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
      //Caricamento Diffide
      Ticket t = new Ticket();
		context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
		context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
     
      //Dopo l'inserimento riconverti
      Iterator it_coords = newOrg.getAddressList().iterator();
      while(it_coords.hasNext()){
    	  
    	  org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress) it_coords.next();
    	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
    		  
    		  String spatial_coords [] = null;
        	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//        	  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//      		  {
//      			 AjaxCalls ajaxCall = new AjaxCalls();
//      			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//      			thisAddress.setLatitude(coordinate[1]);
//      		  thisAddress.setLongitude(coordinate[0]);
//      		  }
//      		  else
//      		  {
      			  thisAddress.setLatitude(spatial_coords[0]);
        		  thisAddress.setLongitude(spatial_coords[1]);
      		  //}
    	  }
    	  
    	  //context.getSession().setAttribute("lat", Double.toString(thisAddress.getLatitude()));
          //context.getSession().setAttribute("lon", Double.toString(thisAddress.getLongitude()));
          
    	  
      }
      
      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);

		

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);
      
            
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
      // Carico nel context le linee di attivita'
      ArrayList<LineeAttivita> linee_attivita = LineeAttivita.load_linee_attivita_per_org_id(temporgId, db);
      context.getRequest().setAttribute("linee_attivita", linee_attivita);
      LineeAttivita linea_attivita_principale = LineeAttivita.load_linea_attivita_principale_per_org_id(temporgId, db);
      context.getRequest().setAttribute("linea_attivita_principale", linea_attivita_principale);
      ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita.load_linee_attivita_secondarie_per_org_id(temporgId, db);
      context.getRequest().setAttribute("linee_attivita_secondarie", linee_attivita_secondarie);

          
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList countryList = new LookupList(db, "lookup_nazioni");
		countryList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("CountryList", countryList);

      
	    Boolean flag=false;
		if (linea_attivita_principale!=null && linea_attivita_principale.getCodice_istat().equals("00.00.00")){
			flag = true;
		}         
		context.getRequest().setAttribute("istat_principale_non_valido", flag);
		
		
       
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("OrgDetails", newOrg);
    String action = context.getRequest().getParameter("action");    
    
    
    if (action != null && action.equals("modify")) {
      //If user is going to the modify form
      addModuleBean(context, "Accounts", "Modify Account Details");
      return ("DetailsOK");
    } else {
      //If user is going to the detail screen
      addModuleBean(context, "View Accounts", "View Account Details");
     
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
    if (!hasPermission(context, "requestor-dashboard-view")) {
      if (!hasPermission(context, "requestor-requestor-view")) {
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
          "Accounts", "org.aspcfs.modules.accounts.base.AccountsListScheduledActions", "Accounts");
      calendarInfo.setCalendarDetailsView("Accounts");
      context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
    }

    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

    //this is how we get the multiple-level heirarchy...recursive function.
 
    
    return ("DashboardOK");
  }


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "requestor-requestor-view")) {
      return ("PermissionError");
    }
    
    String source = (String) context.getRequest().getParameter("source");
    String partitaIva = (String) context.getRequest().getParameter("searchPartitaIva");
    String codiceF = (String) context.getRequest().getParameter("codiceFiscale");
    String cognomeR = (String) context.getRequest().getParameter("cognomeRappresentante");
    String nomeR = (String) context.getRequest().getParameter("nomeRappresentante");
    String codIstat = (String) context.getRequest().getParameter("searchCodiceFiscaleCorrentista");
    String tipoDest = context.getRequest().getParameter("searchTipoDest");
    String addressType = context.getRequest().getParameter("searchcodeAddressType");
    OrganizationList organizationList = new OrganizationList();
    organizationList.setTipologia(0);

    if(addressType!=null && !addressType.equals("")){
        organizationList.setTipoDest(tipoDest);
        organizationList.setAddressType(addressType);
      }
    
    if(!"".equals(codIstat) && codIstat!=null) {
    	organizationList.setCodiceFiscaleCorrentista(codIstat);
    	 }
    
    if(!"".equals(partitaIva) && partitaIva!=null) {
    	organizationList.setPartitaIva(partitaIva);
    	 }
    if(!"".equals(codiceF) && codiceF != null) {
    	organizationList.setCodiceFiscale(codiceF);
    	 }
    if(!"".equals(cognomeR) && cognomeR != null) {
    	organizationList.setCognomeRappresentante(cognomeR);
    	 }
    if(!"".equals(nomeR) && nomeR != null) {
    	organizationList.setNomeRappresentante(nomeR);
    	 }
    	 
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("Requestor.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
     
      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
      if (isPortalUser(context)) {
        organizationList.setOrgSiteId(this.getUserSiteId(context));
        source = "searchForm";
      }
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-2, "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      	
      LookupList stageList = new LookupList(db, "lookup_requestor_stage");
      stageList.addItem(-1, systemStatus.getLabel("requestor.any"));
      context.getRequest().setAttribute("StageList", stageList);

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("Requestor.do?command=Search");
        }
        //Build the organization list
        organizationList.setPagedListInfo(searchListInfo);
        organizationList.setMinerOnly(false);
        organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

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
        //If the user is a portal user, fetching only the
        //the organization that he access to
        //(i.e., the organization for which he is an account contact
        if (isPortalUser(context)) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
          organizationList.setOrgId(getPortalUserPermittedOrgId(context));
        }
        organizationList.buildList(db);
        context.getRequest().setAttribute("OrgList", organizationList);
        context.getSession().setAttribute("previousSearchType", "accounts");
   
        return "ListOK";
      } 
    } catch (Exception e) {
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "";
  }
  
  

public String executeCommandGeneraNumero(ActionContext context) throws IndirizzoNotFoundException {
		
		String tempOpId = context.getRequest().getParameter("opId");
		if (tempOpId == null) {
			tempOpId = ""
				+ (Integer) context.getRequest().getAttribute("opId");
		}

		String tempStabId = context.getRequest().getParameter("stabId");
		context.getRequest().setAttribute("stabId", tempStabId);
		context.getRequest().setAttribute("opId", tempOpId);
		if (tempStabId == null) {
			tempStabId = ""
				+ (Integer) context.getRequest().getAttribute("idStab");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer stabid = null;

		if (tempOpId != null) {
			tempid = Integer.parseInt(tempOpId);
		}

		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		Connection db = null;
		try {
			db = this.getConnection(context);	
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			int idComune = newStabilimento.getSedeOperativa().getComune();
			ComuniAnagrafica comune = new ComuniAnagrafica(db, idComune);
			
		}
		catch(SQLException e)
		{
			
		}
		finally
		{
			super.freeConnection(context, db);
		}
		
		return executeCommandDetailsOpu(context);
		
	}
	
	public String executeCommandCambiainOsaOpu(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = ""
					+ (Integer) context.getRequest().getAttribute("opId");
			}

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
			}
			context.getRequest().setAttribute("idStab", tempStabId);
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, tempid);	
			context.getRequest().setAttribute("Operatore", operatore);

			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.setFlagDia(false);
			newStabilimento.cambiainOsa(db,((UserBean) context.getSession().getAttribute("User")).getUserId());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			LookupList TipoStruttura = new LookupList(db,
			"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
			"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			LookupList serviziocompetente = new LookupList(db, "lookup_account_stage");
			serviziocompetente.addItem(-1, getSystemStatus(context).getLabel(
			"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", serviziocompetente);

		
			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
			"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
					.getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			StabilimentoAction act = new StabilimentoAction();
			
		
			return "gotoRequestorOpu";

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
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandInsert(ActionContext context) throws SQLException {
    if (!hasPermission(context, "requestor-requestor-add")) {
      return ("PermissionError");
    }
    
    context.getSession().removeAttribute("lista");
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    org.aspcfs.modules.requestor.base.Organization insertedOrg = null;
    String checkSaveClone = null;
    
    org.aspcfs.modules.requestor.base.Organization newOrg = ( org.aspcfs.modules.requestor.base.Organization) context.getRequest().getAttribute("OrgDetails");
    Audit audit = new Audit();
    audit.setLivelloRischioFinale(-1);
    
    newOrg.setCity_legale_rapp(context.getRequest().getParameter("city_legale_rapp"));
    newOrg.setProv_legale_rapp(context.getRequest().getParameter("prov_legale_rapp"));
    newOrg.setAddress_legale_rapp(context.getRequest().getParameter("address_legale_rapp"));
    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
    
  //PAESE DI PROVENIENZA
  		String provenienza = context.getRequest().getParameter("provenienza");
  		if (provenienza != null && provenienza.equals("ESTERO"))
  			newOrg.setIdNazione( context.getRequest().getParameter("country"));
       
    if(context.getParameter("flag_vendita")!= null && context.getParameter("flag_vendita").equalsIgnoreCase("on"))
    	newOrg.setFlagVenditaCanali(true);
	else
		newOrg.setFlagVenditaCanali(false);
    
    if(context.getParameter("no_piva")!= null && context.getParameter("no_piva").equalsIgnoreCase("on"))
    	newOrg.setNo_piva(true);
	else
		newOrg.setNo_piva(false);
    
    
    newOrg.setDomicilioDigitale(context.getRequest().getParameter("domicilioDigitale"));
    newOrg.setOwner(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    newOrg.setDate1(context.getParameter("date1"));
    newOrg.setDate3(context.getParameter("date3"));
    
   if( context.getRequest().getParameter("tipoDest")!=null)
    if(!context.getRequest().getParameter("tipoDest").equalsIgnoreCase("distributori")){
    
    	if(context.getRequest().getParameter("TipoLocale")!= null && !context.getRequest().getParameter("TipoLocale").equals("")){
    	
    	newOrg.setTipoLocale(Integer.parseInt(context.getRequest().getParameter("TipoLocale")));
    	
    }
    
    if(context.getRequest().getParameter("TipoLocale2")!= null &&!context.getRequest().getParameter("TipoLocale2").equals("")){
    	
    	newOrg.setTipoLocale2(Integer.parseInt(context.getRequest().getParameter("TipoLocale2")));
    	
    }
    
    if(context.getRequest().getParameter("TipoLocale3")!= null &&!context.getRequest().getParameter("TipoLocale3").equals("")){
    	
    	newOrg.setTipoLocale3(Integer.parseInt(context.getRequest().getParameter("TipoLocale3")));
    	
    }
    }
    
    checkSaveClone = context.getParameter("saveandclone"); 
    
    try {
      db = this.getConnection(context);
      
      String[] id_codici_ateco_1 =  context.getRequest().getParameterValues("codici_sel");
		
		String[] id_masterlist = context.getRequest()
				.getParameterValues("codici_sel");
      
      int[] id_codici_ateco = new int[11];
	  int[] id_attivita_masterlist = new int[11];

      if (id_codici_ateco_1==null) {
    	  id_codici_ateco[0] =  Integer.parseInt(context.getRequest().getParameter("id_rel_principale"));
          id_codici_ateco[1] =  Integer.parseInt(context.getRequest().getParameter("id_rel_1"));
          id_codici_ateco[2] =  Integer.parseInt(context.getRequest().getParameter("id_rel_2"));
          id_codici_ateco[3] =  Integer.parseInt(context.getRequest().getParameter("id_rel_3"));
          id_codici_ateco[4] =  Integer.parseInt(context.getRequest().getParameter("id_rel_4"));
          id_codici_ateco[5] =  Integer.parseInt(context.getRequest().getParameter("id_rel_5"));
          id_codici_ateco[6] =  Integer.parseInt(context.getRequest().getParameter("id_rel_6"));
          id_codici_ateco[7] =  Integer.parseInt(context.getRequest().getParameter("id_rel_7"));
          id_codici_ateco[8] =  Integer.parseInt(context.getRequest().getParameter("id_rel_8"));
          id_codici_ateco[9] =  Integer.parseInt(context.getRequest().getParameter("id_rel_9"));
          id_codici_ateco[10]=  Integer.parseInt(context.getRequest().getParameter("id_rel_10"));
          
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
          context.getRequest().setAttribute("id_codici_ateco", id_codici_ateco);
      }else
      {
    	  int indice=0;
    	  int i=0;
    	  for(String a : id_codici_ateco_1) {
    		  id_codici_ateco[indice]=Integer.parseInt(a);
    		  indice++;
    	  }
    	  
    	  for (String b : id_masterlist) {
				id_attivita_masterlist[i] = Integer.parseInt(b);
				i++;
			}
      }
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
  
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);

		

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);


        newOrg.setRequestItems(context);
     
      if (this.getUserSiteId(context) != -1) {
        // Set the site Id of the account to be equal to the site Id of the user
        // for a new account
        if (newOrg.getId() == -1) {
          newOrg.setSiteId(this.getUserSiteId(context));
        } else {
          // Check whether the user has access to update the organization
          if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
            return ("PermissionError");
          }
        }
      }

      //Si costruisce la FORM dagli elementi	
      
      OrganizationAddress so = null;
      OrganizationAddress sedeLegale = null;
      OrganizationAddress sedeMobile = null;
      Iterator it = newOrg.getAddressList().iterator();
      
      
      while(it.hasNext())
      {
    	  OrganizationAddress thisAddress = (OrganizationAddress) it.next();
    	  if(thisAddress.getType()==5)
    	  {
    		  so=thisAddress;
    	  }
    	  if(thisAddress.getType()==7)
    	  {
    		  sedeMobile=thisAddress;
    	  }
    	  if(thisAddress.getType()==1)
    	  {
    		  sedeLegale=thisAddress;
    	  }
    	  
    	  //Richiamo il metodo per il calcolo delle coordinate
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
        	  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
        	  //Prova nel fare il passaggio inverso
        	  //this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
        	  
    	  }  
    	  
    	  
      }
      
      
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        recordInserted = newOrg.insert(db,context);
      }
      if (recordInserted)
      {
    	  if (memorizza_codici_ateco(context, newOrg.getOrgId(), id_codici_ateco,  id_attivita_masterlist, db ) == null)
    	  carica_lookup_ateco_nel_context(context, "" + newOrg.getId(), db);   
    	       
    	  
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);
        
        
        
      }
      
      addModuleBean(context, "View Accounts", "Accounts Insert ok");
      if (recordInserted && ( checkSaveClone == null  || "".equals(checkSaveClone) ) ) {
      	String target = context.getRequest().getParameter("target");
          if (context.getRequest().getParameter("popup") != null) {
            return ("ClosePopupOK");
          }
          if (target != null && "add_contact".equals(target)) {
           	  return ("InsertAndAddContactOK");  
          } else {
        	  return ("InsertOK");
          }
  	}else if ( recordInserted && checkSaveClone !=null ){
  		
  		it = insertedOrg.getAddressList().iterator();
        while(it.hasNext())
        {
      	  OrganizationAddress thisAddress = (OrganizationAddress) it.next();
  		 String spatial_coords [] = null;
   	  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//   	  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
// 		  {
// 			 AjaxCalls ajaxCall = new AjaxCalls();
// 			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
// 			thisAddress.setLatitude(coordinate[1]);
// 		  thisAddress.setLongitude(coordinate[0]);
// 		  }
//   	 else
//		  {
			  thisAddress.setLatitude(spatial_coords[0]);
		  thisAddress.setLongitude(spatial_coords[1]);
		  //}
        }
  		context.getRequest().setAttribute("clona", "true");
  		if ( checkSaveClone.equals("true") ){
  			carica_lookup_ateco_nel_context(context, "" + newOrg.getId(), db);   
  			context.getRequest().setAttribute("esitoInsert", "DIA inserito:"+insertedOrg.getName()+" inserito con successo!");
  			//insertedOrg.getName();
  			
  			insertedOrg.setComuni2(db,  ((UserBean) context.getSession().getAttribute("User")).getSiteId());
  			context.getRequest().setAttribute("OrgDetails", insertedOrg);
  			
  			return getReturn(context, "Add");
  		}
  	}
    } catch (Exception errorMessage) {
    	errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if(checkSaveClone!=null && checkSaveClone.equals("true") )
    {
    	context.getRequest().setAttribute("clona", "true");
    }
    return (executeCommandAdd(context));
  }


  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandUpdate(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "requestor-requestor-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    newOrg.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    
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
    
    newOrg.setCity_legale_rapp(context.getRequest().getParameter("city_legale_rapp"));
    newOrg.setDomicilioDigitale(context.getRequest().getParameter("domicilioDigitale"));
    newOrg.setProv_legale_rapp(context.getRequest().getParameter("prov_legale_rapp"));
    newOrg.setAddress_legale_rapp(context.getRequest().getParameter("address_legale_rapp"));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    newOrg.setDate1(context.getParameter("date1"));
    newOrg.setDate3(context.getParameter("date3"));
    try {
      db = this.getConnection(context);
      
      if (aggiorna_codici_ateco(context, newOrg.getId()) == null)
      
    	  newOrg.setIsIndividual(false);
        newOrg.setRequestItems(context);
        
      
      if(context.getRequest().getParameter("TipoLocale")!=null)
      newOrg.setTipoLocale(Integer.parseInt(context.getRequest().getParameter("TipoLocale")));
      if(context.getRequest().getParameter("TipoLocale2")!=null)
          newOrg.setTipoLocale2(Integer.parseInt(context.getRequest().getParameter("TipoLocale2")));
          
      if(context.getRequest().getParameter("TipoLocale3")!=null)
          newOrg.setTipoLocale3(Integer.parseInt(context.getRequest().getParameter("TipoLocale3")));
          
      
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
        	  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);

		

		LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);
      
      if (isValid) {
        resultCount = newOrg.update(db,context);
      }
      if (resultCount == 1) {
  
        LookupList IstatList = new LookupList(db, "lookup_codistat");
        IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("IstatList", IstatList);


        
         
      }
    } catch (Exception errorMessage) {
    
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    if (resultCount == -1 || !isValid) {
      return (executeCommandModify(context));
    } else if (resultCount == 1) {
      if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("list")) {
        return (executeCommandSearch(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("dashboard")) {
        return (executeCommandDashboard(context));
      } else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
          "return").equals("Calendar")) {
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
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandDelete(ActionContext context) throws SQLException {
    if (!hasPermission(context, "requestor-requestor-delete")) {
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
     
          recordDeleted = thisOrganization.delete(
              db, context, getDbNamePath(context));
   
   
    } catch (Exception e) {
    	
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (errorMessage == null) {
      if (recordDeleted) {
        
        context.getRequest().setAttribute(
            "refreshUrl", "Requestor.do?command=Search");
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
          "refreshUrl", "Requestor.do?command=Search");
      return ("DeleteError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
 * @throws SQLException 
   */
  public String executeCommandTrash(ActionContext context) throws SQLException {
    if (!hasPermission(context, "requestor-requestor-delete")) {
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
  
    } catch (Exception e) {
    	
      context.getRequest().setAttribute(
          "refreshUrl", "Requestor.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Requestor.do?command=Search");
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
    if (!hasPermission(context, "requestor-requestor-delete")) {
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
          "refreshUrl", "Requestor.do?command=Search");
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
  public String executeCommandConfirmDelete(ActionContext context) {
    if (!hasPermission(context, "requestor-requestor-delete")) {
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
      htmlDialog.addMessage(
          systemStatus.getLabel("confirmdelete.caution") + "\n" );
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      htmlDialog.addButton(
          systemStatus.getLabel("button.delete"), "javascript:window.location.href='Requestor.do?command=Delete&action=delete&orgId=" + thisOrg.getOrgId() + "&forceDelete=true" + "'");
      if (thisOrg.getEnabled()) {
        htmlDialog.addButton(
            systemStatus.getLabel("button.disableOnly"), "javascript:window.location.href='Requestor.do?command=Delete&orgId=" + thisOrg.getOrgId() + "&action=disable'");
      }
      htmlDialog.addButton(
          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
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
    if (!hasPermission(context, "requestor-requestor-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      newOrg = (Organization) context.getRequest().getAttribute("OrgDetails");
      
      if (newOrg.getId() == -1) {
        newOrg = new Organization(db, tempid);
        
        //In fase di modifica
        Iterator it_coords = newOrg.getAddressList().iterator();
        while(it_coords.hasNext()){
      	  
      	  org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress) it_coords.next();
      	  if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){
      		  String spatial_coords [] = null;
      		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
//      		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//      		  {
//      			 AjaxCalls ajaxCall = new AjaxCalls();
//      			String[] coordinate= ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(), thisAddress.getZip(), ""+thisAddress.getLatitude(), ""+thisAddress.getLongitude(), "");
//      			thisAddress.setLatitude(coordinate[1]);
//      		  thisAddress.setLongitude(coordinate[0]);
//      		  }
//      		  else
//      		  {
      			  thisAddress.setLatitude(spatial_coords[0]);
        		  thisAddress.setLongitude(spatial_coords[1]);
      		  //}

      		  
      		  
      	  }
        }
        
        
      
      } else {
        
      }
      if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
        return ("PermissionError");
      }
   
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute("systemStatus", systemStatus);


      
      LookupList stageList = new LookupList(db, "lookup_account_stage");
		stageList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("StageList", stageList);
		
          
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
      // Carico nel context la lookup che unisce codici ateco e linee di attivita'...
      carica_lookup_ateco_nel_context(context, orgid, db);     
 
      LookupList sourceList = new LookupList(db, "lookup_contact_source");
		sourceList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("SourceList", sourceList);
      
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);



      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
  	LookupList countryList = new LookupList(db, "lookup_nazioni");
	// SOLO PAESI EUROPEI
	countryList.removeItemfromLookup(db, "lookup_nazioni", "level <> 1");
	// ITALIA NON SELEZIONABILE
	countryList.removeElementByValue("Italia");
	countryList.addItem(-1, "-- SELEZIONA VOCE --");
	context.getRequest().setAttribute("CountryList", countryList);

      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      context.getRequest().setAttribute("systemStatus", systemStatus);

      
      UserBean user = (UserBean) context.getSession().getAttribute("User");
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
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
					linea.setOrg_id(orgId);
					linea.setId_attivita_masterlist(id_attivita_masterlist[i]);
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
  /**
   *  Description of the Method
   *
   * @param  context     Description of the Parameter
   * @param  organization  Description of the Parameter
   * @param  db  Description of the Parameter
   *
   * @exception  Exception  Description of the Exception
   */
  protected void deleteRecentItems(ActionContext context, Connection db, Organization organization) throws SQLException {
    //remove any recent contacts belonging to this account
 
  }

  public LookupList costruisci_lookup_rel_ateco_linea_attivita(ActionContext context){
		    LookupList ret = new LookupList();
			Connection db = null;
	
			try {
				db = getConnection(context);
				ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_all_rel_ateco_linee_attivita(db);
							
				for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
					if (!rel_ateco_linea.getLinea_attivita().isEmpty())
						ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCodice_istat() + " : " + rel_ateco_linea.getCategoria() + " - " + rel_ateco_linea.getLinea_attivita());
					else
						ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCodice_istat() + " : " + rel_ateco_linea.getCategoria() );
				}
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return (null);
			} finally {
				this.freeConnection(context, db);
			}
			return ret;
		}
	
	public LookupList costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(ActionContext context, String cod_ateco){
		    LookupList ret = new LookupList();
			Connection db = null;
	
			try {
				db = getConnection(context);
				ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_rel_ateco_linee_attivita_per_codice_istat(cod_ateco, db);
	
				for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
					if (!rel_ateco_linea.getLinea_attivita().isEmpty())
						ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCategoria() + " - " + rel_ateco_linea.getLinea_attivita());
					else
						ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCategoria() );
				}
				
				if (ret.size()==0) {
					ret.addItem(-1, "-- Selezionare prima il codice Ateco --" );
				}
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return (null);
			} finally {
				this.freeConnection(context, db);
			}
			return ret;
		}
	
	
	public String aggiorna_codici_ateco(ActionContext context, int orgId) throws SQLException {
		  Connection db = null;
		  String ret="memorizza_codici_atecoOK";
	
			try {
				db = getConnection(context);
				
				String del = "delete from la_imprese_linee_attivita where org_id = "+orgId ;
				db.prepareStatement(del).execute();
				
				int[] id_rel_codici_ateco = new int[11];
				int[] id_attivita_masterlist= new int[11];

				id_rel_codici_ateco[0] =  Integer.parseInt(context.getRequest().getParameter("id_rel_principale"));
				id_rel_codici_ateco[1] =  Integer.parseInt(context.getRequest().getParameter("id_rel_1"));
				id_rel_codici_ateco[2] =  Integer.parseInt(context.getRequest().getParameter("id_rel_2"));
				id_rel_codici_ateco[3] =  Integer.parseInt(context.getRequest().getParameter("id_rel_3"));
				id_rel_codici_ateco[4] =  Integer.parseInt(context.getRequest().getParameter("id_rel_4"));
				id_rel_codici_ateco[5] =  Integer.parseInt(context.getRequest().getParameter("id_rel_5"));
				id_rel_codici_ateco[6] =  Integer.parseInt(context.getRequest().getParameter("id_rel_6"));
				id_rel_codici_ateco[7] =  Integer.parseInt(context.getRequest().getParameter("id_rel_7"));
				id_rel_codici_ateco[8] =  Integer.parseInt(context.getRequest().getParameter("id_rel_8"));
				id_rel_codici_ateco[9] =  Integer.parseInt(context.getRequest().getParameter("id_rel_9"));
				id_rel_codici_ateco[10]=  Integer.parseInt(context.getRequest().getParameter("id_rel_10"));
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
				
				
				int[] id_old = new int[11];
				id_old[0]  =  Integer.parseInt(context.getRequest().getParameter("id_la_principale_OLD"));
				id_old[1]  =  Integer.parseInt(context.getRequest().getParameter("id_la_1_OLD"));
				id_old[2]  =  Integer.parseInt(context.getRequest().getParameter("id_la_2_OLD"));
				id_old[3]  =  Integer.parseInt(context.getRequest().getParameter("id_la_3_OLD"));
				id_old[4]  =  Integer.parseInt(context.getRequest().getParameter("id_la_4_OLD"));
				id_old[5]  =  Integer.parseInt(context.getRequest().getParameter("id_la_5_OLD"));
				id_old[6]  =  Integer.parseInt(context.getRequest().getParameter("id_la_6_OLD"));
				id_old[7]  =  Integer.parseInt(context.getRequest().getParameter("id_la_7_OLD"));
				id_old[8]  =  Integer.parseInt(context.getRequest().getParameter("id_la_8_OLD"));
				id_old[9]  =  Integer.parseInt(context.getRequest().getParameter("id_la_9_OLD"));
				id_old[10] =  Integer.parseInt(context.getRequest().getParameter("id_la_10_OLD"));
				
				
				for(int i=0; i<=10; i++)
					if (id_rel_codici_ateco[i]!=-1) {
						String id = ""+id_old[i];
						LineeAttivita linea = LineeAttivita.load_linea_attivita_per_id(id, db);
						
						// Se in modifica non e' stato inserita una nuova linea di attivita' ma e' stata effettuata una modifica....
						if (linea!=null) {
							if (linea.getId_rel_ateco_attivita()!=id_rel_codici_ateco[i]) {
								if (i==0)
									linea.setPrimario(true);
								else
									linea.setPrimario(false);
								linea.setId_rel_ateco_attivita(id_rel_codici_ateco[i]);
								linea.setOrg_id(orgId);
								linea.setId_attivita_masterlist(id_attivita_masterlist[i]);
								linea.setEntered(linea.getEntered());
								linea.setEntered_by( linea.getEntered_by() );
								linea.setModified( new Timestamp( System.currentTimeMillis() ) );
								linea.setModified_by( this.getUserId(context) );
								linea.update(db);
							}
						} else {	// Codice eseguito se e' stato inserito una nuova linea di attivita' in modifica
							linea = new LineeAttivita();
							if (i==0)
								linea.setPrimario(true);
							else
								linea.setPrimario(false);
							linea.setId_rel_ateco_attivita(id_rel_codici_ateco[i]);
							linea.setOrg_id(orgId);
							linea.setId_attivita_masterlist(id_attivita_masterlist[i]);
							linea.setEntered(linea.getEntered());
							linea.setEntered_by( linea.getEntered_by() );
							linea.setModified( new Timestamp( System.currentTimeMillis() ) );
							linea.setModified_by( this.getUserId(context) );
							linea.store(db);
						}
				}
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return (null);
			} finally {
				this.freeConnection(context, db);
			}
			return ret;
	}
	
	public void carica_lookup_ateco_nel_context(ActionContext context, String orgid, Connection db){
		  LookupList rel_ateco_linea_attivita_List = costruisci_lookup_rel_ateco_linea_attivita(context);
	    context.getRequest().setAttribute("rel_ateco_linea_attivita_List", rel_ateco_linea_attivita_List);
	    ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_all_rel_ateco_linee_attivita(db);
	    context.getRequest().setAttribute("all_rel_ateco_linee_attivita", all_rel_ateco_linee_attivita);
	    LineeAttivita linea_attivita_principale = LineeAttivita.load_linea_attivita_principale_per_org_id(orgid, db);
	    context.getRequest().setAttribute("linea_attivita_principale", linea_attivita_principale);
	    ArrayList<LineeAttivita> linee_attivita_secondarie = LineeAttivita.load_linee_attivita_secondarie_per_org_id(orgid, db);
	    context.getRequest().setAttribute("linee_attivita_secondarie", linee_attivita_secondarie);
	    
	    String cod_istat= "" ;
	    if(linea_attivita_principale != null )
	    {
	    	cod_istat = linea_attivita_principale.getCodice_istat() ;
	    }
	    
	    LookupList List_id_rel_principale= costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, cod_istat);
	    
	    LookupList List_id_rel_1 = new LookupList(); 
	    LookupList List_id_rel_2 = new LookupList(); 
	    LookupList List_id_rel_3 = new LookupList(); 
	    LookupList List_id_rel_4 = new LookupList(); 
	    LookupList List_id_rel_5 = new LookupList(); 
	    LookupList List_id_rel_6 = new LookupList(); 
	    LookupList List_id_rel_7 = new LookupList(); 
	    LookupList List_id_rel_8 = new LookupList(); 
	    LookupList List_id_rel_9 = new LookupList(); 
	    LookupList List_id_rel_10= new LookupList(); 
	    
	    if (linee_attivita_secondarie.size()>0)    	  
	  	  List_id_rel_1         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(0).getCodice_istat());
	    else    	  
	  	  List_id_rel_1.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>1)     	  
	  	  List_id_rel_2         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(1).getCodice_istat());
	    else     	  
	  	  List_id_rel_2.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>2)    	  
	  	  List_id_rel_3         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(2).getCodice_istat());
	    else    	  
	  	  List_id_rel_3.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>3)     	 
	  	  List_id_rel_4         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(3).getCodice_istat());
	    else    	  
	  	  List_id_rel_4.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>4)     	 
	  	  List_id_rel_5         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(4).getCodice_istat());
	    else
	  	  List_id_rel_5.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>5)     	 
	  	  List_id_rel_6         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(5).getCodice_istat());
	    else    	  
	  	  List_id_rel_6.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>6)     	 
	  	  List_id_rel_7         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(6).getCodice_istat());
	    else    	  
	  	  List_id_rel_7.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>7)      	 
	  	  List_id_rel_8         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(7).getCodice_istat());
	    else    	  
	  	  List_id_rel_8.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>8)     	 
	  	  List_id_rel_9         = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(8).getCodice_istat());
	    else    	  
	  	  List_id_rel_9.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    if (linee_attivita_secondarie.size()>9)     	 
	  	  List_id_rel_10        = costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(context, linee_attivita_secondarie.get(9).getCodice_istat());
	    else    	  
	  	  List_id_rel_10.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	    
	    context.getRequest().setAttribute("List_id_rel_principale", List_id_rel_principale);
	    context.getRequest().setAttribute("List_id_rel_1 ", List_id_rel_1 );
	    context.getRequest().setAttribute("List_id_rel_2 ", List_id_rel_2 );
	    context.getRequest().setAttribute("List_id_rel_3 ", List_id_rel_3 );
	    context.getRequest().setAttribute("List_id_rel_4 ", List_id_rel_4 );
	    context.getRequest().setAttribute("List_id_rel_5 ", List_id_rel_5 );
	    context.getRequest().setAttribute("List_id_rel_6 ", List_id_rel_6 );
	    context.getRequest().setAttribute("List_id_rel_7 ", List_id_rel_7 );
	    context.getRequest().setAttribute("List_id_rel_8 ", List_id_rel_8 );
	    context.getRequest().setAttribute("List_id_rel_9 ", List_id_rel_9 );
	    context.getRequest().setAttribute("List_id_rel_10", List_id_rel_10);
	
	}
 
	
	public String executeCommandAddOpu(ActionContext context) {


		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			ComuniAnagrafica c = new ComuniAnagrafica();
			context.getRequest().setAttribute("idNorma",  context.getRequest().getParameter("idNorma"));
			String flagDia = context.getParameter("flagDia");
			
				flagDia = "true" ;
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);
			
			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);
			

			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);
			

			Stabilimento newStabilimento = new Stabilimento();
			context.getRequest().setAttribute("idNorma", context.getParameter("idNorma"));
			

			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			
			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			int idOperatore = -1;
			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			if (context.getRequest().getAttribute("Operatore")!= null )
				context.getRequest().setAttribute("Operatore", context.getRequest().getAttribute("Operatore"));
			else
				context.getRequest().setAttribute("Operatore", operatore);

			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);

			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			//lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "AddStabilimento");
	}
	
	
	public String executeCommandInsertOpu(ActionContext context)
	throws SQLException, IndirizzoNotFoundException {

		if (!hasPermission(context, "accounts-accounts-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;
		
		String flagDia = context.getParameter("flagDia");
		if (flagDia==null)
			flagDia = "true" ;
		context.getRequest().setAttribute("idNorma", context.getParameter("idNorma"));
		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		try {
			SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");
			
			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);
			
			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);
			

			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);

			if (("false").equals((String) context.getParameter("doContinueStab"))) { 

				LineaProduttiva lp = null;
				newStabilimento.setFlagDia(true);

				LineaProduttivaList arrayListeProduttiveDaConservare = new LineaProduttivaList();
				arrayListeProduttiveDaConservare.clear();

				if (	context.getRequest().getParameterValues("idLineaProduttiva") != null && 
						context.getRequest().getParameterValues("idLineaProduttiva").length > 0
				) 
				{
  
					try {


						String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
						for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
							if (!lineeProduttiveSelezionate[i].equals("")) 
							{
								if (Integer.parseInt(lineeProduttiveSelezionate[i])>0)
								{
									lp = new LineaProduttiva(db, Integer.parseInt(lineeProduttiveSelezionate[i]));
									InformazioniStabilimento infoStab = new InformazioniStabilimento(context);
									lp.setInfoStab(infoStab);
									
									arrayListeProduttiveDaConservare.add(lp);
								}
							}
						}

						newStabilimento.setListaLineeProduttive(arrayListeProduttiveDaConservare);
					} catch (Exception e) {
						e.printStackTrace();
					} // regione

				}



				if (context.getRequest().getParameter("inregione") != null && ("no").equals(context.getRequest().getParameter("inregione")))
					newStabilimento.setFlagFuoriRegione(true);

				soggettoAdded = new SoggettoFisico(context.getRequest());
				Indirizzo add = soggettoAdded.getIndirizzo();

				add.setDescrizioneComune(comuniList.getSelectedValue(add.getComune()));
				add.setDescrizione_provincia(provinceList.getSelectedValue(add.getProvincia()));

				soggettoAdded.setIndirizzo(add);
				newStabilimento.setRappLegale(soggettoAdded);
			

				Indirizzo indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
						indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa

				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
						indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa

				}

				indirizzoAdded.setDescrizioneComune(comuniList.getSelectedValue(indirizzoAdded.getComune()));
				newStabilimento.setSedeOperativa(indirizzoAdded);
				context.getRequest().setAttribute("LineaProduttivaScelta", arrayListeProduttiveDaConservare);
				context.getRequest().setAttribute("newStabilimento",newStabilimento);

				Operatore operatore = (Operatore) context.getRequest().getAttribute("Operatore");
				operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
				Indirizzo indirizzoAddedSL = null;
				if (new Integer(context.getRequest().getParameter("viaSL")).intValue() > 0) 
				{

					indirizzoAddedSL = new Indirizzo(db, new Integer(context.getRequest().getParameter("viaSL")).intValue());
					indirizzoAddedSL.setTipologiaSede(1); // Legale
				} else 
				{
					indirizzoAddedSL = new Indirizzo(context.getRequest(), db,true,context);
					indirizzoAddedSL.setTipologiaSede(1);

				}
				operatore.getListaSediOperatore().add(indirizzoAddedSL);
				
				if (newStabilimento.getIdOperatore() >0)
					operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());
				
				context.getRequest().setAttribute("Operatore", operatore);

				return executeCommandAddOpu(context);
			}
			newStabilimento.setEnteredBy(getUserId(context));
			newStabilimento.setModifiedBy(getUserId(context));
			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			
			

			
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

			if(context.getRequest().getParameter("addressLegaleLine1") != null)
			{
				soggettoAdded = new SoggettoFisico(context.getRequest());
				SoggettoFisico soggettoEsistente = soggettoAdded.verificaSoggetto(db);

				/*se il soggetto non esiste lo aggiungo */
				if(soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <=0)
				{
					soggettoAdded.setEnteredBy(getUserId(context));
					soggettoAdded.setModifiedBy(getUserId(context));
					soggettoAdded.insert(db,context);
					newStabilimento.setRappLegale(soggettoAdded);
				}
				else
				{
					/*se esiste */

					if ( soggettoEsistente.getIdSoggetto() >0)
					{
						Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();
						Indirizzo indirizzoEsistente = soggettoEsistente.getIndirizzo() ;

						if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi")))
						{
							soggettoEsistente.setEnteredBy(getUserId(context));
							soggettoEsistente.setModifiedBy(getUserId(context));
							soggettoEsistente.setNome(soggettoAdded.getNome());
							soggettoEsistente.setCognome(soggettoAdded.getCognome());
							soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
							soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());


							soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
							soggettoEsistente.setFax(soggettoAdded.getFax());
							soggettoEsistente.setIndirizzo(indirizzoAdded);
							soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
							soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
							soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
							soggettoEsistente.setEmail(soggettoAdded.getEmail());
							soggettoEsistente.setModifiedBy(user.getUserId());
							soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());


							soggettoEsistente.update(db,context);

							newStabilimento.setRappLegale(soggettoEsistente);
							/*temporaneamente prendo quello che ho */

						}
						else
						{
							newStabilimento.setRappLegale(soggettoEsistente);
							
						}

					}

				}
			}
			
			
			Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");
			exist = newOperatore.checkEsistenzaOperatore(db);
			
			newOperatore.setRappLegale(newStabilimento.getRappLegale());
			if (exist == false) 
			{
			
				
				Provincia provinciaAsl = new Provincia();
				provinciaAsl.getProvinciaAsl(db, user.getSiteId());
				context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

				Indirizzo indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("viaSL")).intValue() > 0) 
				{

					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("viaSL")).intValue());
					indirizzoAdded.setTipologiaSede(1); // Legale
				} else 
				{
					indirizzoAdded = new Indirizzo(context.getRequest(), db,true,context);
					indirizzoAdded.setTipologiaSede(1);

				}

				newOperatore.getListaSediOperatore().add(indirizzoAdded);
				isValid = this.validateObject(context, db, newOperatore);

				if (isValid) 
				{
					recordInserted = newOperatore.insert(db,context);
					
				}
			}

			newStabilimento.setIdOperatore(newOperatore.getIdOperatore());
			



			Indirizzo indirizzoAdded = null;
			if (context.getRequest().getParameter("via") != null && new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
			
					indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa
			
			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
					indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa
			
			}
			newStabilimento.setSedeOperativa(indirizzoAdded);
			
			/**
			 * CONTROLLO DI ESISTENZA stabilimento per id indirizzo e id operatore
			 */
			exist = newStabilimento.checkEsistenzaStabilimento(db);

			if (exist) 
			{
				context.getRequest().setAttribute("Exist","Esiste Uno Stabilimento con la stessa sede Operativa ");
				context.getRequest().setAttribute("rappLegale", soggettoAdded);
				return executeCommandAddOpu(context);
			}
			for (int i = 0; i < newStabilimento.getListaLineeProduttive().size(); i++) {
				newStabilimento.getListaLineeProduttive().remove(i);
			}
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null && context.getRequest().getParameterValues("idLineaProduttiva").length ==1 && context.getRequest().getParameterValues("idLineaProduttiva")[0].equals("")) {
				Operatore operatore = new Operatore () ;
				operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());
				context.getRequest().setAttribute("Operatore", operatore);
				context.getRequest().setAttribute("Error", "Attenzione Selezionare almeno una linea produttiva");
				return executeCommandAddOpu(context);
			}
			else

			if (context.getRequest().getParameterValues("idLineaProduttiva") != null && context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {

				String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");

				for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
					if (!lineeProduttiveSelezionate[i].equals("")) {
						LineaProduttiva lp = new LineaProduttiva();

						lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
						if (context.getRequest().getParameter("dataInizio" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataInizio(context.getRequest().getParameter("dataInizio"+ lp.getIdRelazioneAttivita()));

						if (context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()) != null)
							lp.setDataFine(context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()));

						if (context.getRequest().getParameter("stato" + lp.getIdRelazioneAttivita()) != null)
							lp.setStato(Integer.parseInt(context.getRequest().getParameter("stato"+ lp.getIdRelazioneAttivita())));
						if (context.getRequest().getParameter("tipo_attivita_produttiva" + lp.getIdRelazioneAttivita()) != null)
							lp.setTipoAttivitaProduttiva(Integer.parseInt(context.getRequest().getParameter("tipo_attivita_produttiva"+ lp.getIdRelazioneAttivita())));
						if (context.getParameter("principale")!=null && Integer.parseInt(lineeProduttiveSelezionate[i])== (Integer.parseInt(context.getParameter("principale"))))
							lp.setPrincipale(true);
						InformazioniStabilimento infoStab = new InformazioniStabilimento(context);
						lp.setInfoStab(infoStab);
						newStabilimento.getListaLineeProduttive().add(lp);
					}
				}

			}
			
			String inRegione = (String) context.getRequest().getParameter("inregione");

			if (inRegione != null) {
				newStabilimento.setFlagFuoriRegione(inRegione);
			}
			Object[] asl;
			if (!newStabilimento.isFlagFuoriRegione()) 
				asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
			else
				asl = null;

			if (asl != null && asl.length > 0) {

				Object[] aslVal = (Object[]) asl[0];
				if (aslVal != null && aslVal.length > 0)
					newStabilimento.setIdAsl((Integer) aslVal[0]);

			} else {
				newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
			}
			isValid = this.validateObject(context, db, newStabilimento);

			if (isValid) {   
				recordInserted = newStabilimento.insert(db, true,context);
				if (newStabilimento.isFlagDia()==false)
				{
					int idComune = newStabilimento.getSedeOperativa().getComune();
					ComuniAnagrafica comune = new ComuniAnagrafica(db, idComune);
					//newStabilimento.generaCodice(db, comune.getCodice());
						   

				}
			}
			if (recordInserted) {

				Stabilimento stabilimentoInserito = new Stabilimento(db, newStabilimento.getIdStabilimento());
				context.getRequest().setAttribute("Stabilimento",stabilimentoInserito);
				context.getRequest().setAttribute("opId",stabilimentoInserito.getIdOperatore());
				context.getRequest().setAttribute("idStab",stabilimentoInserito.getIdStabilimento());
			}


		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);

		}

		return (executeCommandDetailsOpu(context));

	}
	
	
	
	
	
	public String executeCommandDetailsOpu(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {



			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;



			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	
			

			
			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);
			
			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);
			

			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);
			
			
			newStabilimento = new Stabilimento(db,  stabid);
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);
			 
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			
			if (newStabilimento.getListaLineeProduttive().size()==0)
			{
				context.getRequest().setAttribute("opId", newStabilimento.getIdOperatore());
				OperatoreAction opAction = new OperatoreAction();
				return opAction.executeCommandDetails(context);
				
			}
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			LookupList TipoStruttura = new LookupList(db,
			"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
			"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
			
			
			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
					context.getRequest().setAttribute("ListaStati", ListaStati);


			LookupList serviziocompetente = new LookupList(db, "lookup_account_stage");
			serviziocompetente.addItem(-1, getSystemStatus(context).getLabel(
			"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", serviziocompetente);

		
	 	
			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
					.getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			/*852 tipo Distributori*/
			


		
			String sel1 = "select * from opu_raggruppamento_operatori where id_stabilimento =?";
			PreparedStatement pst1 = db.prepareStatement(sel1);
			pst1.setInt(1,newStabilimento.getIdStabilimento());
			ResultSet rs =  pst1.executeQuery();
			if (!rs.next())
				context.getRequest().setAttribute("Raggruppamento", "Si");
			else
				context.getRequest().setAttribute("Raggruppamento", "No");
			
			return getReturn(context, "DetailsOpu");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}


	
	  
  
  
}

