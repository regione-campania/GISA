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
package org.aspcfs.modules.imbarcazioni.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.itextpdf.text.log.SysoLogger;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.imbarcazioni.base.Organization;
import org.aspcfs.modules.imbarcazioni.base.OrganizationList;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
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
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.Storico;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;

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
			
			context=PrepareUpdateLineePregresse(context,db,temporgId);
			
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
  
  	public static ActionContext PrepareUpdateLineePregresse(ActionContext context, Connection db, String id_impresa) {
		try {

				context.getRequest().setAttribute("idImpresa", id_impresa);
			
			return context;

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return context;
		}
	}
  	
  	public String  executeCommandUpdateLineePregresse(ActionContext context) {
		String ret = "";

		try {
			Connection db = null;
		    db = this.getConnection(context);
			String codiceLinea = context.getRequest().getParameter("codiceLinea");
			String orgId = context.getRequest().getParameter("orgId");
			System.out.println("codiceLinea "+codiceLinea);
			PreparedStatement pst = db.prepareStatement("select * from aggiorna_linea_imbarcazioni(?,?)");
			pst.setInt(1, Integer.parseInt(orgId));
			pst.setString(2, codiceLinea);
			System.out.println("pst: "+pst.toString());
			pst.execute();
				
			ret = "UpdateLineePregresseOK";
			}
			catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		return ret;
	}
  	
  	
  	
  	
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "imbarcazioni-imbarcazioni-vigilanza-view")) {
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
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "Imbarcazioni.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
	        controlliList.setOrgId(passedId);
	    	
	      
	      LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
	      AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("AuditTipo", AuditTipo);
	      
	      LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
	      TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoAudit", TipoAudit);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

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
  

  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "imbarcazioni-imbarcazioni-view"))) {
      return ("PermissionError");
    }
    
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("stabilimenti.allSites"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      UserBean user = (UserBean) context.getSession().getAttribute("User");
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, -1 );
      LookupList comuniList = new LookupList(listaComuni);
      
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);

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
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList tipoPesca = new LookupList(db, "lookup_tipo_pesca");
      context.getRequest().setAttribute("TipoPesca", tipoPesca);
      
      LookupList sistemaPesca = new LookupList(db, "lookup_sistema_pesca");
      context.getRequest().setAttribute("SistemaPesca", sistemaPesca);
      
      Organization newOrg = (Organization) context.getFormBean();
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
            
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
      String provincia = thisUser.getContact().getState();
      ArrayList<Integer> asl_id = new ArrayList<Integer>();
      newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());
      if(provincia!=null){
      if(provincia.equals("AV")){
    	  asl_id.add(1);
    	  asl_id.add(2);
      }else if(provincia.equals("BN")){
    	  asl_id.add(3);
      }else if(provincia.equals("CE")){
    	  asl_id.add(4);
    	  asl_id.add(5);
      }else if(provincia.equals("NA")){
    	  asl_id.add(6);
    	  asl_id.add(7);
    	  asl_id.add(8);
    	  asl_id.add(9);
    	  asl_id.add(10);
    	  
      }else if(provincia.equals("SA")){
    	  asl_id.add(11);
    	  asl_id.add(12);
    	  asl_id.add(13);
    	  
      }}
    
      
      if (newOrg.getEnteredBy() != -1) {
     
        context.getRequest().setAttribute("OrgDetails", newOrg);
      }
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
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-view")) {
      return ("PermissionError");
    }
    Connection   db           = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg       = null;
   
    try {
    	
    	 
    	 /**
    	  * 	COSTRUZIONE DEL BEAN A PARTIRE DALL'ID IN INPUT
    	  */
    		
    	 String temporgId = context.getRequest().getParameter("orgId");
         if (temporgId == null) {
           temporgId = (String) context.getRequest().getAttribute("orgId");
         }
         int tempid = Integer.parseInt(temporgId);
         db = this.getConnection(context);
//         if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
//           return ("PermissionError");
//         }
         newOrg = new Organization(db, tempid);
         //Caricamento Diffide
         Ticket t = new Ticket();
 		context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
 		context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
    
         
         
 		Iterator it_coords = newOrg.getAddressList().iterator();
		while (it_coords.hasNext()) {

			org.aspcfs.modules.imbarcazioni.base.OrganizationAddress thisAddress = (org.aspcfs.modules.imbarcazioni.base.OrganizationAddress) it_coords
					.next();
			if (thisAddress.getLatitude() != 0
					&& thisAddress.getLongitude() != 0) {

				String spatial_coords[] = null;
				spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
				
//				if (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(
//								',', '.')) > 41.503754) {
//					AjaxCalls ajaxCall = new AjaxCalls();
//					String[] coordinate = ajaxCall.getCoordinate(
//							thisAddress.getStreetAddressLine1(),
//							thisAddress.getCity(), thisAddress.getState(),
//							thisAddress.getZip(), ""
//									+ thisAddress.getLatitude(), ""
//									+ thisAddress.getLongitude(), "");
//					thisAddress.setLatitude(coordinate[1]);
//					thisAddress.setLongitude(coordinate[0]);
//				} else {
					thisAddress.setLatitude(spatial_coords[0]);
					thisAddress.setLongitude(spatial_coords[1]);
				//}
			}

			// context.getSession().setAttribute("lat",
			// Double.toString(thisAddress.getLatitude()));
			// context.getSession().setAttribute("lon",
			// Double.toString(thisAddress.getLongitude()));

		}

         
        
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

    
      
      LookupList categoriaList = new LookupList(db, "lookup_categoria");
      categoriaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("categoriaList", categoriaList);
      
      LookupList imballataList = new LookupList(db, "lookup_sottoattivita_imballata");
      imballataList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("imballataList", imballataList);
      
      LookupList tipoAutorizzazioneList = new LookupList(db, "lookup_sottoattivita_tipoautorizzazione");
      tipoAutorizzazioneList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("tipoAutorizzazioneList", tipoAutorizzazioneList);
      
      
     /* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      int AuditOrgId = newOrg.getOrgId();
      audit.setOrgId(AuditOrgId);
     
      audit.buildList(db);
      
      if( (audit.size() - 1)>=0){
      
    	  context.getRequest().setAttribute("Audit",audit.get(0) );
      }*/
     
      
    } catch (Exception e) {
    	e.printStackTrace();
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
      if(context.getRequest().getAttribute("generato")!=null)
    	  return "generazioneCodiceOk";
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
    if (!hasPermission(context, "imbarcazioni-dashboard-view")) {
      if (!hasPermission(context, "imbarcazioni-imbarcazioni-view")) {
        return ("PermissionError");
      }
      //Bypass dashboard and search form for portal users
      if (isPortalUser(context)) {
        return (executeCommandSearch(context));
      }
      return (executeCommandSearchForm(context));
    }
    addModuleBean(context, "Dashboard", "Dashboard");
    return ("DashboardOK");
  }


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-view")) {
      return ("PermissionError");
    }

    
    String name =context.getRequest().getParameter("searchAccountName"); 
    OrganizationList organizationList = new OrganizationList();
    organizationList.setNomeRappresentante(context.getParameter("searchNomeRappresentante"));
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("Imbarcazioni.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    Connection db = null;
    try {
      db = this.getConnection(context);

      //For portal usr set source as 'searchForm' explicitly since
      //the search form is bypassed.
      //temporary solution for page redirection for portal user.
     
      if((context.getParameter("searchNumAut"))!=null){
          organizationList.setNumAut(context.getParameter("searchNumAut"));
          }
     
      if(!"".equals(name) && name != null)
        	organizationList.setName(name);
      
      if((context.getParameter("searchAccountCity"))!=null)
      {
          organizationList.setAccountCity(context.getParameter("searchAccountCity").trim());
      }
      
      UserBean user = (UserBean) context.getSession().getAttribute("User");
      org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
      ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
      LookupList comuniList = new LookupList(listaComuni);
      
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      siteList.addItem(Constants.INVALID_SITE, systemStatus.getLabel("stabilimenti.allSites"));
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("Imbarcazioni.do?command=Search");
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
        
        if((context.getParameter("searchAccountCity"))!=null)
        {
        	HashMap criteria = searchListInfo.getSavedCriteria();
        	criteria.remove("searchAccountCity");
            criteria.put("searchAccountCity", context.getParameter("searchAccountCity").trim());
            searchListInfo.setSavedCriteria(criteria);
        	organizationList.setAccountCity(context.getParameter("searchAccountCity").trim());
        }
        
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
    	e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "ListOK";
  }


  /**
   *  ViewTickets: Displays Ticket history (open and closed) for a particular
   *  Account.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandViewTickets(ActionContext context) {
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-tickets-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    TicketList ticList = new TicketList();
    Organization newOrg = null;
    //Process request parameters
    int passedId = Integer.parseInt(
        context.getRequest().getParameter("orgId"));

    //Prepare PagedListInfo
    PagedListInfo ticketListInfo = this.getPagedListInfo(
        context, "AccountTicketInfo", "t.entered", "desc");
    ticketListInfo.setLink(
        "Imbarcazioni.do?command=ViewTickets&orgId=" + passedId);
    ticList.setPagedListInfo(ticketListInfo);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
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

  
  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws ParseException 
   */
  public String executeCommandInsert(ActionContext context) throws ParseException {
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-add")) {
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
    
   /* newOrg.setNomeRappresentante(context.getParameter("nomeRappresentante"));
    newOrg.setCognomeRappresentante(context.getParameter("cognomeRappresentante"));
    newOrg.setDataNascitaRappresentante(context.getParameter("dataNascitaRappresentante"));
    newOrg.setLuogoNascitaRappresentante(context.getParameter("luogoNascitaRappresentante"));
    newOrg.setTelefonoRappresentante(context.getParameter("telefonoRappresentante"));
    newOrg.setFax(context.getParameter("fax"));*/
    
    newOrg.setAccountNumber(context.getParameter("accountNumber"));
    if(context.getParameter("capacita_max")!= null && !context.getParameter("capacita_max").equals("")){
    	newOrg.setCapacita_max(context.getParameter("capacita_max"));
    }
    newOrg.setDuns_type(context.getParameter("duns_type"));
    newOrg.setTipo_struttura(context.getParameter("tipologia_strutt"));
    newOrg.setPartitaIva(context.getParameter("partita_iva"));
    newOrg.setNumaut(context.getParameter("numaut"));
    newOrg.setNamefirst(context.getParameter("namefirst"));
    newOrg.setCategoriaRischio(3);
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setContractEndDate(new Timestamp(System.currentTimeMillis()));
    newOrg.setModifiedBy(getUserId(context));
  
    String flag = context.getParameter("flag_selezione"); 
    if(flag != null){
    	if(flag.equals("on")){
    		newOrg.setFlag_selezione(true);
    	}
    	else {
    		newOrg.setFlag_selezione(false);
    	}
    }
    
    String fuori_regione = context.getParameter("fuori_regione");
    if(fuori_regione != null){
    	if(fuori_regione.equals("true"))
    		newOrg.setFuori_regione(true);
    	else 
    		newOrg.setFuori_regione(false);
    }
    
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    newOrg.setDataPresentazione(context.getParameter("dataPresentazione"));
    
    try {
      db = this.getConnection(context);

      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
        
     
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("statoLab", statoLab);
      
      LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      OrgCategoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
      
      LookupList impianto = new LookupList(db, "lookup_attivita_osm");
      impianto.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("impianto", impianto);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

     
        newOrg.setRequestItems(context);
        //newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
     
      isValid = this.validateObject(context, db, newOrg);
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.imbarcazioni.base.OrganizationAddress thisAddress = (org.aspcfs.modules.imbarcazioni.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
    	  int idAsl = 16 ; 
    	  if(thisAddress.getType()==1)
    	  {
    		  String sql = "select asl.code from lookup_site_id asl join comuni1 c  on c.codiceistatasl::int=asl.code where c.nome ilike ?";
    		  PreparedStatement pst = db.prepareStatement(sql);
    		  pst.setString(1, thisAddress.getCity());
    		  ResultSet rs = pst.executeQuery();
    		  if(rs.next())
    		  {
    			  idAsl = rs.getInt(1);
    		  }
    		  newOrg.setSiteId(idAsl);
    		  
    	  }
      }//Fine aggiunta
        
     
    
      if (isValid) {
    	  
        recordInserted = newOrg.insert(db,context);
      }
      if (recordInserted) {
        insertedOrg = new Organization(db, newOrg.getOrgId());
        if(context.getRequest().getParameterValues("tipoPesca") != null){
        	insertedOrg.insertTipoPesca(db, context.getRequest().getParameterValues("tipoPesca"));
      	}
        
      	if(context.getRequest().getParameterValues("sistemaPesca") != null){
      		insertedOrg.insertSistemaPesca(db, context.getRequest().getParameterValues("sistemaPesca"));
    	}
        
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        addRecentItem(context, newOrg);

       }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
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
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "imbarcazioni-imbarcazioni-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean(); 
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
   
    newOrg.setModifiedBy(getUserId(context));
   
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
   
   newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
   
   
      /*	if(!context.getParameter("siteId").equals("-1"))
      		newOrg.setSiteId(Integer.parseInt(context.getParameter("siteId")));
      */
        newOrg.setRequestItems(context);
        newOrg.setName(context.getParameter("name"));
        
        /*newOrg.setNomeRappresentante(context.getParameter("nomeRappresentante"));
        newOrg.setCognomeRappresentante(context.getParameter("cognomeRappresentante"));
        newOrg.setDataNascitaRappresentante(context.getParameter("dataNascitaRappresentante"));
        newOrg.setLuogoNascitaRappresentante(context.getParameter("luogoNascitaRappresentante"));
        newOrg.setTelefonoRappresentante(context.getParameter("telefonoRappresentante"));
        newOrg.setFax(context.getParameter("fax"));
        newOrg.setDuns_type(context.getParameter("duns_type"));
        newOrg.setTipo_struttura(context.getParameter("tipologia_strutt"));
       
        */
        newOrg.setAccountNumber(context.getParameter("accountNumber"));
        if(context.getParameter("capacita_max")!= null && !context.getRequest().getParameter("capacita_max").equals("")){
        	newOrg.setCapacita_max(context.getParameter("capacita_max"));
        }
        if(context.getParameter("partita_iva") != null && !context.getRequest().getParameter("partita_iva").equals("") ){
        	newOrg.setPartitaIva(context.getParameter("partita_iva"));
        }
        newOrg.setNumaut(context.getParameter("numaut"));
        newOrg.setNamefirst(context.getParameter("namefirst"));
        String flag = context.getParameter("flag_selezione"); 
        if(flag != null){
        	if(flag.equals("on")){
        		newOrg.setFlag_selezione(true);
        	}
        	else {
        		newOrg.setFlag_selezione(false);
        	}
        }
        
        String fuori_regione = context.getParameter("fuori_regione");
        if(fuori_regione != null){
        	if(fuori_regione.equals("true"))
        		newOrg.setFuori_regione(true);
        	else 
        		newOrg.setFuori_regione(false);
        }
        
        if(context.getRequest().getParameterValues("tipoPesca") != null){
        	newOrg.updateTipoPesca(db, context.getRequest().getParameterValues("tipoPesca"));
      	}
        
        if(context.getRequest().getParameterValues("sistemaPesca") != null){
        	newOrg.updateSistemaPesca(db, context.getRequest().getParameterValues("sistemaPesca"));
      	}
        
        
         oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      context.getRequest().setAttribute("orgId",newOrg.getOrgId() );
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.imbarcazioni.base.OrganizationAddress thisAddress = (org.aspcfs.modules.imbarcazioni.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      
    	 newOrg.update(db,context);
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
        if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
            "individual")) {
         
        }
        LookupList IstatList = new LookupList(db, "lookup_codistat");
        IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("IstatList", IstatList);
       

   
        
        LookupList statoLab = new LookupList(db, "lookup_stato_lab");
        statoLab.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("statoLab", statoLab);
        
        LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
        OrgCategoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
        context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
        
      
       

      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    
    
        return executeCommandDetails(context);
      
    
  }

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateCatRischio(ActionContext context) {
    
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
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandTrash(ActionContext context) {
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-delete")) {
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
      
   
      } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "Imbarcazioni.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Imbarcazioni.do?command=Search");
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
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-delete")) {
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
//      recordUpdated = thisOrganization.updateStatus(
//          db, context, false, this.getUserId(context));
      this.invalidateUserData(context, this.getUserId(context));
 
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "Imbarcazioni.do?command=Search");
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
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-edit")) {
      return ("PermissionError");
    }
    boolean recordEnabled = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisOrganization = new Organization(
          db, Integer.parseInt(context.getRequest().getParameter("orgId")));
   
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
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-delete")) {
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
        htmlDialog.addMessage("<form action=\"Imbarcazioni.do?command=Trash&auto-populate=true\" method=\"post\">");
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
   *  Modify: Displays the form used for modifying the information of the
   *  currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "imbarcazioni-imbarcazioni-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    SystemStatus systemStatus = this.getSystemStatus(context);
    
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      org.aspcfs.modules.imbarcazioni.base.OrganizationAddress soperativa = null;
      newOrg = new Organization(db,tempid);
      //newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
            
      Iterator it1 = newOrg.getAddressList().iterator();
   	  int nlocali =0;
   	  while(it1.hasNext())
   	  {
   		  org.aspcfs.modules.imbarcazioni.base.OrganizationAddress add=(org.aspcfs.modules.imbarcazioni.base.OrganizationAddress)it1.next();
   		  if(add.getType()==5)
   		  {
       		  soperativa=add;
   		  } 
   	  }
    
   	 context.getRequest().setAttribute("AddressSedeOperativa",soperativa);
      
      context.getRequest().setAttribute("systemStatus", systemStatus);

      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList tipoPesca = new LookupList(db, "lookup_tipo_pesca");
      context.getRequest().setAttribute("TipoPesca", tipoPesca);
      
      LookupList sistemaPesca = new LookupList(db, "lookup_sistema_pesca");
      context.getRequest().setAttribute("SistemaPesca", sistemaPesca);
     
      Iterator it = newOrg.getAddressList().iterator();
   	  while(it.hasNext())
   	  {
   		  org.aspcfs.modules.imbarcazioni.base.OrganizationAddress add=(org.aspcfs.modules.imbarcazioni.base.OrganizationAddress)it.next();
   		  if(add.getLatitude()!=0 && add.getLongitude()!=0){
    		  String spatial_coords [] = null;
    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(add.getLatitude()), Double.toString(add.getLongitude()),db);
//    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//    		  {
//    			 AjaxCalls ajaxCall = new AjaxCalls();
//    			 String[] coordinate= ajaxCall.getCoordinate(add.getStreetAddressLine1(), add.getCity(), add.getState(), add.getZip(), ""+add.getLatitude(), ""+add.getLongitude(), "");
//    			 add.setLatitude(coordinate[1]);
//    			 add.setLongitude(coordinate[0]);
//    		  }
//    		  else
//    		  {
    			  add.setLatitude(spatial_coords[0]);
    			  add.setLongitude(spatial_coords[1]);
    		  //}

    	  }
   		  
   	  }//Fine modifica
      
   	
      //newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      
      //if this is an individual account
   
   	  newOrg.setComuni2(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());
      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      //ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      context.getRequest().setAttribute("StateSelect", stateSelect);
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("CountrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);


    } catch (Exception e) {
    	e.printStackTrace();
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

  
//inserito carmela
  public String executeCommandModificaCatRischio(ActionContext context) {
	    
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
	      siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
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
		  //String filename = "modB.xml";
		  String filename = (String) context.getRequest().getParameter("file");
		  //System.out.println("file:" + filename);
		  
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
		    	fileDownload.setDisplayName("DettaglioStabilimento_" + id + ".pdf");
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
  
  public String executeCommandAllImportRecords(ActionContext context){
		Connection db = null;
		
		try {
			
			db = getConnection(context);
			
			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListOsm(db);
			
			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("OsmUploadListOK");
		
	}
  
  /**
   * Upload file stabilimenti Giuseppe
   * @param context
   * @return
   */
  public String executeCommandUploadDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;
		
		try {
			
			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}
			
			/*LookupList llist = new LookupList(db,"lookup_specie_allevata");
		    llist.addItem(-1, "-nessuno-");
		    context.getRequest().setAttribute("SpecieA", llist);*/
		    
		    String specieA = multi.getParameter("SpecieA");
		    
			File myFileT = multi.getFile("file1"); 
			FileInputStream fiStream=new FileInputStream(myFileT);
			//BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logOsm");
			
			String pathDownloadLog = LeggiFile.leggiCampiOsm(context, db,myFileT,logPath, getUserId(context));
			
			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insertStabilimento(db);
			
			context.getRequest().setAttribute("pathLog", pathDownloadLog);
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("OsmUploadSaveOK");
		
	}
  
  public String executeCommandOsmUpload(ActionContext context){
		Connection db = null;

		try {
			db = getConnection(context);
		
			
			
			 UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
		      if (thisUser.getRoleId()==21){ //UTENTE COMUNE
		    	  context.getRequest().setAttribute("UserComune", thisUser);
		      }
		     
		      if (thisUser.getRoleId()==23){ //UTENTE PROVINCIA
		    	  context.getRequest().setAttribute("UserProvincia", thisUser);
		      }		     
				
			
						
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("OsmUploadAddOK");
		
	}
  
  /*
  private String[] convert2Wgs84UTM33N( String latitude, String longitude, Connection conn  ) throws SQLException
  {
		
		String[] ret = null;
		String sql = 
		"select 	X \n"	+ 
		"( \n"				+ 
		"	transform \n"	+ 
		"	(  \n"			+ 
		"		geomfromtext \n"	+ 
		"		(  \n"				+ 
		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
		"	 \n"					+ 
		"		), 32633 \n"			+ 
		"	) \n"					+ 
		") AS x, \n"				+ 
		"Y \n"						+ 
		"( \n"						+ 
		"	transform \n"			+ 
		"	(  \n"					+ 
		"		geomfromtext \n"	+ 
		"		(  \n"				+ 
		"			'POINT( " + longitude + " " + latitude + " )', 4326 \n"	+ 
		"	 \n"			+ 
		"		), 32633 \n"	+ 
		"	) \n"			+ 
		") AS y \n";
		
		try
		{
			
			PreparedStatement stat = conn.prepareStatement( sql );
			//stat.setString( 1, wgs84[0] );
			//stat.setString( 2, wgs84[1] );
			//stat.setString( 3, wgs84[0] );
			//stat.setString( 4, wgs84[1] );
			//System.out.println("Query: "+stat.toString());
			
			ResultSet res = stat.executeQuery();
			if( res.next() )
			{
				ret = new String[2];
				ret[0] = res.getString( "x" );
				ret[1] = res.getString( "y" );
				//System.out.println("latitudine wgs84 utm 33n : "+ret[0]);
				//System.out.println("longitudine wgs84 utm 33n : "+ret[1]);
				
			}
			res.close();
			stat.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
  
  private String[] converToWgs84UTM33NInverter(String latitudine , String longitudine, Connection db) throws SQLException
  {
          String lat ="";
          String lon = "";
          String [] coord = new String [2];
          String sql1 = 
                  "select         X \n"        + 
                  "( \n"                                + 
                  "        transform \n"        + 
                  "        (  \n"                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
                  "         \n"                                        + 
                  "                ), 4326 \n"                        + 
                  "        ) \n"                                        + 
                  ") AS x, \n"                                + 
                  "Y \n"                                                + 
                  "( \n"                                                + 
                  "        transform \n"                        + 
                  "        (  \n"                                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+latitudine+" "+longitudine+")', 32633 \n"        + 
                  "         \n"                        + 
                  "                ), 4326 \n"        + 
                  "        ) \n"                        + 
                  ") AS y \n";
          
        try {
        	
          PreparedStatement stat1 = db.prepareStatement( sql1 );
          //System.out.println("Query di conversione:" +stat1.toString());
          ResultSet res1 = stat1.executeQuery();
          if( res1.next() )
          {
                  lat = res1.getString( "y") ;
                  lon=res1.getString( "x" );
                  coord [0] =lat;
                  coord [1] =lon;
                  //System.out.println("latitudine input : "+ lat);
                  //System.out.println("longitudine input : "+ lon);
          
          }
          res1.close();
          stat1.close();
          
        }catch (Exception e){
        	e.printStackTrace();
        }
         
        return coord;
          
  }
  */
  
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




  


