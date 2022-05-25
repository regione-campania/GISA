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
package org.aspcfs.modules.trasportoanimali.actions;
 
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldCategoryList;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.trasportoanimali.base.Organization;
import org.aspcfs.modules.trasportoanimali.base.OrganizationList;
import org.aspcfs.modules.trasportoanimali.base.SchedaAllegato;
import org.aspcfs.modules.trasportoanimali.base.Stato;
import org.aspcfs.modules.trasportoanimali.base.Veicolo;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.TipoAnimaliRendicontazione;
import org.aspcfs.utils.TipoCategoriaRendicontazione;
import org.aspcfs.utils.TipoIspezioniRendicontazione;
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
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;


/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {
	
	private static final String squote = "<tr><td align=center>";
	private static final String equote = "</td></tr>";
	private static final String dquote = "</td><td align=center>";
	private static final String initialBold="<b>";
	private static final String endBold="</b>";
	private static final String blueFont = "<font color='blue'>";
	private static final String redFont = "<font color='red'>";
	private static final String endFont  = "</font>";
/**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
	
	
	public String executeCommandGeneraCodiceOsa(ActionContext context) {
		  if (!hasPermission(context, "trasportoanimali-trasportoanimali-genera-view")) {
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
		      if(thisOrganization.getAccountNumber()==null || thisOrganization.getAccountNumber().equals("")){
		      generaCodice(db, thisOrganization);
		      }
		      LookupList siteList = new LookupList(db, "lookup_site_id");
		        siteList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        context.getRequest().setAttribute("SiteList", siteList);
		        
				
			   
		    } catch (Exception e) {
		    	  e.printStackTrace();
			      context.getRequest().setAttribute("Error", e);
			      return ("SystemError");
			      //return ("DetailsOK");
			 } finally {
			      this.freeConnection(context, db);
			    }
			 /*String retPage = "";
	         String tipo_richiesta = thisOrganization.getDunsType();
	         tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
	         
	         retPage = "Details_" + tipo_richiesta + "OK";
	 
		         		          
		          return ( retPage );//("InsertOK");*/ 
		  return executeCommandDetails(context);
	  }
	
	public String executeCommandAdd_Tipo1(ActionContext context){
		if (!hasPermission(context, "trasportoanimali-trasportoanimali-add")) {
		      return ("PermissionError");
		    }
		    SystemStatus systemStatus = this.getSystemStatus(context);
		    Connection db = null;
		    try {
		      db = this.getConnection(context);
		      
		      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
		      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		      context.getRequest().setAttribute("TipoLocale", TipoLocale);
		    
		      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
			    llist.addItem(-1, "-- SELEZIONA VOCE --");
			    llist.setMultiple(true);
			    llist.setSelectSize(5);
			    context.getRequest().setAttribute("SpecieA", llist);
			       
		      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
		      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
		      
		      LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
		      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		      CategoriaTrasportata.setMultiple(true);
		      CategoriaTrasportata.setSelectSize(5);
		      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);

		      LookupList siteList = new LookupList(db, "lookup_site_id");
		      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteList", siteList);
		      
		            
		      Organization newOrg = (Organization) context.getFormBean();      
		      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
		      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
		      context.getRequest().setAttribute("StateSelect", stateSelect);
		      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
		      //newOrg.getProvince();
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
		    context.getRequest().setAttribute(
		        "systemStatus", this.getSystemStatus(context));
		    //if a different module reuses this action then do a explicit return
		    if (context.getRequest().getParameter("actionSource") != null) {
		        return getReturn(context, "AddAccount");
		    }
		   
		    return getReturn(context, "Add_Tipo1");
	}
	
	
	 public String executeCommandViewVigilanza(ActionContext context) {
		    if (!hasPermission(context, "trasporti-trasporti-vigilanza-view")) {
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
		        "TrasportoAnimali.do?command=ViewVigilanza&orgId=" + passedId);
		    ticList.setPagedListInfo(ticketListInfo);
		    try {
		      db = this.getConnection(context);
		      SystemStatus systemStatus = this.getSystemStatus(context);
		      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
		      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("TipoCampione", TipoCampione);
		      
		      LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
		      TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("TipoAudit", TipoAudit);
		      
		      LookupList TipoIspezione = new LookupList(db, "lookup_ispezione_trasporto");
		      TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
		      
		      
		      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
		      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
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

	  public String executeCommandScegliRichiesta( ActionContext context )
	  { 
		  
		  if (!hasPermission(context, "trasportoanimali-trasportoanimali-add"))
		  {
			  return ("PermissionError");
		  }
		  
		  return "ScegliRichiestaOK";
	  }
	  
	  public String executeCommandRendicontazione(ActionContext context){
			Connection db = null;

			try {
				db = getConnection(context);
			
				LookupList llist=new LookupList(db,"lookup_site_id");
			    llist.addItem(0, "-tutte-");
			    context.getRequest().setAttribute("ASL", llist);
			    
			    LookupList list=new LookupList(db,"lookup_anno");
			    context.getRequest().setAttribute("Anno", list);
				
				
				 /*UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			      if (thisUser.getRoleId()==21){ //UTENTE COMUNE
			    	  context.getRequest().setAttribute("UserComune", thisUser);
			      }
			     
			      if (thisUser.getRoleId()==23){ //UTENTE PROVINCIA
			    	  context.getRequest().setAttribute("UserProvincia", thisUser);
			      }		     */
					
							
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("TrasportoAnimaliRendicontazioneOK");
			
		}
	  
	public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
      
      LookupList animaliPropri = new LookupList(db, "lookup_specie_trasportata_tipo4");
      animaliPropri.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      animaliPropri.setMultiple(true);
      animaliPropri.setSelectSize(5);
      context.getRequest().setAttribute("AnimaliPropri", animaliPropri);

      LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      CategoriaTrasportata.setMultiple(true);
      CategoriaTrasportata.setSelectSize(5);
      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);
      
      
      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
	  llist.addItem(-1, "-- SELEZIONA VOCE --");
//	  llist.removeElementByLevel(1);
	    llist.setMultiple(true);
	    llist.setSelectSize(5);
	    context.getRequest().setAttribute("SpecieA", llist);
	       
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      Organization newOrg = (Organization) context.getFormBean();    
      
      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
      StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
      stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
      context.getRequest().setAttribute("StateSelect", stateSelect);
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      //newOrg.getProvince();
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
    context.getRequest().setAttribute(
        "systemStatus", this.getSystemStatus(context));
    //if a different module reuses this action then do a explicit return
    if (context.getRequest().getParameter("actionSource") != null) {
        return getReturn(context, "AddAccount");
    }
    String retPage = "SystemError";
    String tipo_richiesta = context.getRequest().getParameter( "tipo_richiesta" );
    tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
    
    retPage = "Add_" + tipo_richiesta + "OK";
    return ( retPage );
    //return getReturn(context, "Add");
  }
  
 
 
  
 /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandInsert(ActionContext context) {
		  	if (!hasPermission(context, "trasportoanimali-trasportoanimali-add")) {
		      return ("PermissionError");
		  	}
		  	
		    Connection db = null;
		    boolean recordInserted = false;
		    boolean isValid = false;
		    Organization insertedOrg = null;
		    String checkSaveClone = null;
		    Integer orgId = null;
		    /*Istanzio e popolo l'oggetto Organization*/
		    Organization newOrg = (Organization) context.getFormBean();
		    
		  //COMMENTATO IN QUANTO POSSIBILE BUG SU AGGIUNGI (TIPO 3) - e' commentata anche una parentesi graffa prima del return finale
/*		    if(context.getSession().getAttribute("orgIdf5" )!=null){   
			    orgId=(Integer) context.getSession().getAttribute( "orgIdf5" );
			    
			    if(orgId >0){
			    	String retPage = "Details_" + context.getRequest().getParameter( "tipo_richiesta" ) + "OK";
			          context.getSession().removeAttribute("orgIdf5");
			          return ( retPage );//("InsertOK"); 
			    }
		    }else{ */
		    
		    if(context.getRequest().getParameter( "tipo_richiesta" ).equals("tipo4")){
		    newOrg.setName(context.getRequest().getParameter( "cognomeRappresentante" )+" "+context.getRequest().getParameter( "nomeRappresentante" ));}
		    newOrg.setDunsType(context.getRequest().getParameter( "tipo_richiesta" ));
		    context.getRequest().setAttribute("tipo_richiesta", context.getRequest().getAttribute( "tipo_richiesta" ));
		    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
		    //newOrg.setDunsType(context.getRequest().getParameter( "tipoDest" ));
		    newOrg.setEnteredBy(getUserId(context));
		    newOrg.setModifiedBy(getUserId(context));
		    newOrg.setOwner(getUserId(context));
		    UserBean user = (UserBean) context.getSession().getAttribute("User");
		    String ip = user.getUserRecord().getIp();
		    newOrg.setIp_entered(ip);
		    newOrg.setIp_modified(ip);
		    
		    /*if(!context.getRequest().getParameter("TipoLocale").equals("")){
		    	
		    	newOrg.setTipoLocale(Integer.parseInt(context.getRequest().getParameter("TipoLocale")));
		    	
		    }
		    
		    if(!context.getRequest().getParameter("TipoLocale2").equals("")){
		    	
		    	newOrg.setTipoLocale2(Integer.parseInt(context.getRequest().getParameter("TipoLocale2")));
		    	
		    }
		    
		    if(!context.getRequest().getParameter("TipoLocale3").equals("")){
		    	
		    	newOrg.setTipoLocale3(Integer.parseInt(context.getRequest().getParameter("TipoLocale3")));
		    	
		    }*/
		    
		    
		    checkSaveClone = context.getParameter("saveandclone");
		    try {
		    	db = this.getConnection(context);
		    	
		    	LookupList siteList = new LookupList(db, "lookup_site_id");
		        siteList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        context.getRequest().setAttribute("SiteList", siteList);
		        
		        LookupList llist = new LookupList(db,"lookup_specie_trasportata");
			    llist.addItem(-1, "-- SELEZIONA VOCE --");
//			    llist.removeElementByLevel(1);
			    llist.setMultiple(true);
			    context.getRequest().setAttribute("SpecieA", llist);
		        
			    LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
			      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			      CategoriaTrasportata.setMultiple(true);
			      CategoriaTrasportata.setSelectSize(5);
			      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);
			      
			      SystemStatus systemStatus = this.getSystemStatus(context);
			      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("statoLab", statoLab);
			      
			    
		        LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
		        TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        context.getRequest().setAttribute("TipoLocale", TipoLocale);
		        
		         LookupList IstatList = new LookupList(db, "lookup_codistat");
		        IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        context.getRequest().setAttribute("IstatList", IstatList);
		        //aggiunto da d.dauria
		        
		        LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
		        TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
		        
		        LookupList animaliPropri = new LookupList(db, "lookup_specie_trasportata_tipo4");
		        animaliPropri.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		        animaliPropri.setMultiple(true);
		        animaliPropri.setSelectSize(5);
		        context.getRequest().setAttribute("AnimaliPropri", animaliPropri);
		        
		        context.getRequest().setAttribute("numeroPersonale", 1);
				context.getRequest().setAttribute("numeroAutoveicoli", 1);
				context.getRequest().setAttribute("numeroSedi", 1);
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
		            
		              //don't want to populate the addresses, etc. if this is an individual account
		              newOrg.setIsIndividual(false);
		              newOrg.setRequestItems(context);
		            //}
		            if (this.getUserSiteId(context) != -1) {
		              // Set the site Id of the account to be equal to the site Id of the user
		              // for a new account
		              if (newOrg.getId() == -1) {
		                newOrg.setSiteId(this.getUserSiteId(context));
		              } else {
		                // Check whether the user has access to update the organization
		             
		              }
		            }

		            //Si costruisce la FORM dagli elementi	
		    	//Si costruisce la FORM dagli elementi	
		           // newOrg.setSiteId(context.getRequest().getParameter("siteId"));
		            //newOrg.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
		        isValid = this.validateObject(context, db, newOrg);
		        
		        //modifica coordinate
		        Iterator it = newOrg.getAddressList().iterator();
		        while(it.hasNext())
		        {
		      	  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress thisAddress = (org.aspcfs.modules.trasportoanimali.base.OrganizationAddress) it.next();
		      	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
		      	  String[] coords = null;
		      	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
		      		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
		      		 thisAddress.setLatitude(coords[1]);
		        	  thisAddress.setLongitude(coords[0]);
		      	  }
		        }//Fine aggiunta
		          
		        
		        
		        if (isValid){
		        	 
		            String numDistributori=context.getRequest().getParameter(("numElementi"));
		           
		            
		          
		            SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
		            
		            ArrayList<Veicolo> listaV=new ArrayList<Veicolo>();
		           
		        	String[] spa=context.getRequest().getParameterValues("specieA");
		        	String[] ca=context.getRequest().getParameterValues("categoriaTrasportata");
		        	
          		  recordInserted = newOrg.insert(db,spa,ca,context);
          		 newOrg.insertVeicoli(db);
		        }
		        if (recordInserted)
		        {
		          insertedOrg = new Organization(db, newOrg.getOrgId());
		          Integer tempid = newOrg.getOrgId();
		          
		             
		          if(newOrg.getDunsType().equals("tipo1")){
		      	    RowSetDynaClass	rsdc			= null;
		      	    
		      		
		      			//db = getConnection(context);
		      			
		      			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
		      			ResultSet			rs		= stat.executeQuery();
		      							
		      								
		      			TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
		      			tf.setEditable(true);
		      			
		      			tf.setItems( newOrg.getListaV(rs));
		      			//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
		      			tf.setColumnProperties("descrizione", "targa","accepted");
		      			
		      			tf.setStateAttr("restore");
		      			
		      			HtmlRow row = (HtmlRow) tf.getTable().getRow();
		      	        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
		      	        
		      	        
		      	        

		      	       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

		      			tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
		      			tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );

		      			//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
		      			tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );

		      						
		      			Limit limit = tf.getLimit();
		      			if(! limit.isExported() )
		      			{

		      				HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
		      				chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
		      				chkbox.setTitle("Check List");
		      				chkbox.setFilterable(false);
		      				chkbox.setSortable(false);
		      				
		      				
		      				
		      				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
		      			
		      				

		      				
		      				
		      				
		      				cg.setFilterable( false );
		      							

		      				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
		      				//cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
		      				
		      				/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
		      				cg.setEditable(false);
		      				cg.getCellRenderer().setCellEditor( 
		      		        		new CellEditor()
		      		        		{	
		      		        			public Object getValue(Object item, String property, int rowCount)
		      		        			{
		      		        				//String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		      		        				String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
		      		        				String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		      		        				//temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

		      		        				String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&file=allegato_B1.xml&targa="+targa+"\">CheckList </a>";

		      		        				return ret;
		      		        			}
		      		        		});*/
		      				
		      			
		      				cg.setFilterable( false );
		      				
		      				
		      				
		      			}
		      			
		      			String tabella = tf.render();
		      			context.getRequest().setAttribute( "tabella", tabella );
		      			
		      			//tipo 2
		            }else if(newOrg.getDunsType().equals("tipo2")){
		          	  RowSetDynaClass	rsdc			= null;
		        	    
		        		
		      			//db = getConnection(context);
		      			
		      			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa,lunghi_viaggi" );
		      			ResultSet			rs		= stat.executeQuery();
		      							
		      								
		      			TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
		      			tf.setEditable(true);
		      			
		      			tf.setItems( newOrg.getListaV(rs));
		      			//tf.setColumnProperties("descrizione", "targa","checklist","omologazione","accepted");
		      			tf.setColumnProperties("descrizione", "targa","lunghi_Viaggi","accepted");

		      			tf.setStateAttr("restore");
		      			
		      			HtmlRow row = (HtmlRow) tf.getTable().getRow();
		      	        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
		      	        
		      	        
		      	        

		      	        //tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

		      			tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
		      			tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
		      			//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
		      			//tf.getTable().getRow().getColumn( "omologazione" ).setTitle( "Omologazione" );
		      			tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
		      			tf.getTable().getRow().getColumn( "lunghi_Viaggi" ).setTitle( "Omologazione" );


		      						
		      			Limit limit = tf.getLimit();
		      			if(! limit.isExported() )
		      			{
		      				
		      				HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
		      				chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
		      				chkbox.setTitle("Check List");
		      				chkbox.setFilterable(false);
		      				chkbox.setSortable(false);
		      				
		      				
		      				
		      				chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("lunghi_Viaggi");
		      				chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
		      				chkbox.setTitle("Omologazione");
		      				chkbox.setFilterable(false);
		      				chkbox.setSortable(false);
		      							
		      				
		      				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
		      				

		      				cg.setFilterable( false );
		      							

		      				
		      				//cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
		      				
		      				/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
		      				
		      				cg.getCellRenderer().setCellEditor( 
		      		        		new CellEditor()
		      		        		{	
		      		        			public Object getValue(Object item, String property, int rowCount)
		      		        			{
		      		        				//String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		      		        				String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
		      		        				String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		      		        				//temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

		      		        				String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&file=allegato_B1.xml&targa="+targa+"\">CheckList </a>";

		      		        				return ret;
		      		        			}
		      		        		});*/
		      				
		      			
		      				/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("omologazione");
		      				cg.setFilterable(false);
		      				cg.setEditable(false);
		      				cg.getCellRenderer().setCellEditor( 
		      		        		new CellEditor()
		      		        		{	
		      		        			public Object getValue(Object item, String property, int rowCount)
		      		        			{
		      		        				
		      		        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		      		        				String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
		      		        				String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		      		        				temp = (temp == null || "".equals(temp.trim())) ? ("Omologazione") : (temp);
		      		 
		      		        				String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&targa="+targa+"&file=Allegato_E.xml\">" + temp + "</a>";
		      		        				return ret;
		      		        			}
		      		        		});*/
		      				
		      				
		      			}
		      			
		      			String tabella = tf.render();
		      			context.getRequest().setAttribute( "tabella", tabella );
		          //tipo 3
		            }else if((newOrg.getDunsType().equals("tipo3"))||(newOrg.getDunsType().equals("tipo4"))){
		          	    RowSetDynaClass	rsdc			= null;
		          	    
		          		
		      			//db = getConnection(context);
		      			
		      			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
		      			ResultSet			rs		= stat.executeQuery();
		      							
		      								
		      			TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
		      			tf.setEditable(true);
		      			
		      			tf.setItems( newOrg.getListaV(rs));
		      			//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
		      			tf.setColumnProperties("descrizione", "targa");
		      			
		      			tf.setStateAttr("restore");
		      			
		      			HtmlRow row = (HtmlRow) tf.getTable().getRow();
		      	        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
		      	        
		      	        
		      	        

		      	       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

		      			tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
		      			tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );

		      			Limit limit = tf.getLimit();
		      			if(! limit.isExported() )
		      			{

		      						
		      				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
		      			
		      				

		      				
		      				
		      				
		      				cg.setFilterable( false );
		      							

		      				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
		      							
		      				cg.setFilterable( false );
		      				
		      				
		      				
		      			}
		      			
		      			String tabella = tf.render();
		      			context.getRequest().setAttribute( "tabella", tabella );
		            }
		      			 RowSetDynaClass	rsdc2			= null;
		      				
		      				PreparedStatement	stat2	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid);
		      				ResultSet			rs2		= stat2.executeQuery();
		      								
		      									
		      				TableFacade tf2 = TableFacadeFactory.createTableFacade( "sedi", context.getRequest() );
		      				tf2.setEditable(true);
		      				
		      				tf2.setItems( newOrg.getListaS(rs2));
		      				tf2.setColumnProperties( 
		      						"comune", "indirizzo", "provincia", "cap", "stato");
		      				tf2.setStateAttr("restore");
		      				
		      				HtmlRow row2 = (HtmlRow) tf2.getTable().getRow();
		      		        row2.setUniqueProperty("id"); // l'id e' creato da noi quando importiamo la prima volta un file
		      		        
		      			
		      				
		      				
		      				
		      				
		      		        
		      		        
		      		        //tf2.getTable().getRow().getColumn( "id" ).setTitle( "ID" );
		      				
		      				tf2.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
		      				tf2.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
		      				tf2.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
		      				tf2.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
		      				tf2.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );	
		      				
		      				Limit limit2 = tf2.getLimit();
		      				if(! limit2.isExported() )
		      				{
		      					
		      					
		      					
		      					HtmlColumn cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("comune");
		      					//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
		      					cg2.setFilterable( true );
		      					cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("indirizzo");
		      					//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("id");
		      					//cg2.setEditable(false);
		      					//cg2.setFilterable(false);
		      					cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("provincia");
		      					
		      					cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("cap");
		      					cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("stato");
		      					cg2.setFilterable( false );
		      					
		      					
		      				}
		      				
		      				String tabella2 = tf2.render();
		      				context.getRequest().setAttribute( "tabella2", tabella2 );
		      				
		      				RowSetDynaClass	rsdc3			= null;

		      				PreparedStatement	stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid);
		      				ResultSet			rs3		= stat3.executeQuery();
		      								
		      									
		      				TableFacade tf3 = TableFacadeFactory.createTableFacade( "personale", context.getRequest() );
		      				tf3.setEditable(true);
		      				
		      				tf3.setItems( newOrg.getListaP(rs3));
		      				tf3.setColumnProperties( 
		      						"nome", "cognome", "mansione","cf");
		      				tf3.setStateAttr("restore");
		      				
		      				
		      				HtmlRow row3 = (HtmlRow) tf3.getTable().getRow();
		      		        row3.setUniqueProperty("cf"); // the unique worksheet properties to identify the row
		      		       
		      				tf3.getTable().getRow().getColumn( "nome" ).setTitle( "Nome" );
		      				tf3.getTable().getRow().getColumn( "cognome" ).setTitle( "Cognome" );
		      				tf3.getTable().getRow().getColumn( "mansione" ).setTitle( "Mansione" );
		      				tf3.getTable().getRow().getColumn( "cf" ).setTitle( "Codice Fiscale" );
		      				
		      				Limit limit3 = tf3.getLimit();
		      				if(! limit3.isExported() )
		      				{
		      					
		      					
		      					
		      					HtmlColumn cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
		      					//cg3.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
		      					//cg3.setFilterable( true );
		      					//cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
		      					cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("cognome");
		      					cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("mansione");
		      					
		      					cg3.setFilterable( false );
		      					
		      					
		      				}
		      				
		      				String tabella3 = tf3.render();
		      				context.getRequest().setAttribute( "tabella3", tabella3 );
		      				Iterator it1 = insertedOrg.getAddressList().iterator();
		      			   	  while(it1.hasNext())
		      			   	  {
		      			   		  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress add=(org.aspcfs.modules.trasportoanimali.base.OrganizationAddress)it1.next();
		      			   		  if(add.getLatitude()!=0 && add.getLongitude()!=0){
		      			    		  String spatial_coords [] = null;
		      			    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(add.getLatitude()), Double.toString(add.getLongitude()),db);
//		      			    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//		      			    		  {
//		      			    			 AjaxCalls ajaxCall = new AjaxCalls();
//		      			    			 String[] coordinate= ajaxCall.getCoordinate(add.getStreetAddressLine1(), add.getCity(), add.getState(), add.getZip(), ""+add.getLatitude(), ""+add.getLongitude(), "");
//		      			    			 add.setLatitude(coordinate[1]);
//		      			    			 add.setLongitude(coordinate[0]);
//		      			    		  }
//		      			    		  else
//		      			    		  {
		      			    			  add.setLatitude(spatial_coords[0]);
		      			    			  add.setLongitude(spatial_coords[1]);
		      			    		  //}  
		      			    	  }
		      			   		  
		      			   	  }			
		      	
		      				
		      				
		      	  context.getSession().setAttribute("orgIdf5", insertedOrg.getOrgId());
		          context.getRequest().setAttribute("OrgDetails", insertedOrg);
		          addRecentItem(context, newOrg);
		          
//		          BeanSaver.save( null, insertedOrg, insertedOrg.getOrgId(),
//		          		"organization", context, db, null, "Trasporto animali: Inserimento", insertedOrg.getName() );
		          
		          
		        }
		      } catch (Exception errorMessage) {
		    	  errorMessage.printStackTrace(); 
		        context.getRequest().setAttribute("Error", errorMessage);
		        return ("SystemError");
		      } finally {
		        this.freeConnection(context, db);
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
		        	  String retPage = "DetailsOK";
			          String tipo_richiesta = newOrg.getDunsType();
			          tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
			          
			          retPage = "Details_" + tipo_richiesta + "OK";
			         		          
			          return ( retPage );//("InsertOK"); 
		          }
		  	}else if ( recordInserted && checkSaveClone !=null ){
		  		//SALVA E CLONA
		  		if ( checkSaveClone.equals("true") ){
		  			context.getRequest().setAttribute("esitoInsert", "Trasporto inserito:"+insertedOrg.getName()+" inserito con successo!");
		  			//insertedOrg.getName();
		  			context.getRequest().setAttribute("trasportoAnimali", insertedOrg);
		  			
		  			return getReturn(context, "Add");
		  		}
		  	}
		      if (recordInserted) {
		       String retPage = "DetailsOK";
			          String tipo_richiesta = newOrg.getDunsType();
			          tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
			          
			          retPage = "Details_" + tipo_richiesta + "OK";
			         		          
			          return ( retPage );//("InsertOK"); 
		      }
	//	    }
		      return (executeCommandAdd(context));
	  }

  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

    return executeCommandDashboard(context);
  }
  /*
  public String executeCommandGeneraProvince(ActionContext context) {
	  if (!hasPermission(context, "trasportoanimali-trasportoanimali-edit")) {
	      return ("PermissionError");
	    }
	  Connection db = null;
	  Organization thisOrganization = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Organization();
		      
	      
	      
	      thisOrganization.getProvince(db,3);
	     
	      
	  
	    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		      //return ("DetailsOK");
		    } finally {
		      this.freeConnection(context, db);
		    }
	  return ("-none-");
  }*/
  
  public String executeCommandAllImportRecords(ActionContext context){
		Connection db = null;
		
		try {
			
			db = getConnection(context);
		String idorg=	context.getRequest().getParameter("orgid");
			
		int orgid=Integer.parseInt(idorg);
			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListDistributori(db,orgid);
			
			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("VeicoliUploadListOK");
		
	}
  
  public String executeCommandUploadDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;
		String carica = req.getParameter("carica");
		try {
			
			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}
			
	
		    
		   String orgId=multi.getParameter("orgId");
		   int org_Id=Integer.parseInt(orgId);
		   
			File myFileT = multi.getFile("file1"); 
			FileInputStream fiStream=new FileInputStream(myFileT);
			//BufferedReader input = new BufferedReader(new FileReader(myFileT));

			
			BufferedReader input = new BufferedReader(new FileReader(myFileT));
			
			String logPath = getWebContentPath(context, "logtrasporto");
			String pathDownloadLog = null;
			if(carica.equals("veicoli")){
				pathDownloadLog = LeggiFile.leggiCampiVeicoli(context, db,myFileT,logPath, getUserId(context),org_Id);
			}else if(carica.equals("sedi")){
				pathDownloadLog = LeggiFile.leggiCampiSedi(context, db,myFileT,logPath, getUserId(context),org_Id);
			}else{
				pathDownloadLog = LeggiFile.leggiCampiPersonale(context, db,myFileT,logPath, getUserId(context),org_Id);
			}
			if(!pathDownloadLog.equals("errore")){
			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();
			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insertVeicoli(db,org_Id);
			
			
			
			context.getRequest().setAttribute("pathLog", pathDownloadLog);
				
			}else{
				context.getRequest().setAttribute("isError", "Il File non rispetta le specifiche ");
		
			}
			context.getRequest().setAttribute("orgId", orgId);
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if(carica.equals("veicoli")){
			
			return ("VeicoliUploadSaveOK"); 
			
		}else if(carica.equals("sedi")){
			return ("SediUploadSaveOK"); 
		}else{
			return ("PersonaleUploadSaveOK"); 
		}
		
	}

  
  public String executeCommandInsertVeicoli(ActionContext context) {
	  
	
	 context.getSession().setAttribute("orgIdVeicolo",context.getRequest().getParameter("orgId")) ;
	  
	  return ("Upload");
	  
  }
  
  public String executeCommandInsertPersonale(ActionContext context) {
	  
		
		 context.getSession().setAttribute("orgIdPersonale",context.getRequest().getParameter("orgId")) ;
		  
		  return ("UploadPersonale");
		  
	  }
  
  public String executeCommandInsertSedi(ActionContext context) {
	  
		
		 context.getSession().setAttribute("orgIdSedi",context.getRequest().getParameter("orgId")) ;
		  
		  return ("UploadSedi");
		  
	  }
  
  public static void generaCodice(Connection db, org.aspcfs.modules.trasportoanimali.base.Organization thisOrganization)
		throws SQLException
 {
	
		  
	  thisOrganization.generaCodice(db);
	
 }
  
  
  
  public String executeCommandPrintReportV(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-report-view")) {
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
		  
		  map.put("targa", context.getRequest().getParameter("targa"));
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
		    if(filename.equals("modelloB.xml"))
		    	fileDownload.setDisplayName("ModelloB_DIA_" + id + ".pdf");
		    else if (filename.equals("modelloC.xml"))
		    	fileDownload.setDisplayName("ModelloC_DIA_" + id + ".pdf");
		    else if (filename.equals("allegato_B.xml"))
		    	fileDownload.setDisplayName("Checklist_" + id + ".pdf");
		    else
		    	fileDownload.setDisplayName("DettaglioOSA_" + id + ".pdf");
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
	
  
 /* public String executeCommandPrintReport(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		try {
		  db = this.getConnection(context);
		  String id = (String) context.getRequest().getParameter("id");
		  HashMap map = new HashMap();
		  map.put("orgId", new Integer(id));
		  map.put("path", getWebInfPath(context, "reports"));
		  //provide the dictionary as a parameter to the quote report
		  map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
		  //String filename = "modB.xml";
		  
		  map.put("targa", context.getRequest().getParameter("targa"));
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
		    if(filename.equals("allegato_B1.xml")){
		    	fileDownload.setDisplayName("ALLEGATO_B" + id + ".pdf");
		   
		  } else if(filename.equals("Allegato_E.xml")){
			  fileDownload.setDisplayName("ALLEGATO_E" + id + ".pdf");
		  }else if(filename.equals("Allegato_C.xml")){
			  fileDownload.setDisplayName("ALLEGATO_C" + id + ".pdf");
		  }else if(filename.equals("Allegato_D.xml")){
			  fileDownload.setDisplayName("ALLEGATO_D" + id + ".pdf");
		  } else if(filename.equals("Allegato_G.xml")){
			  fileDownload.setDisplayName("ALLEGATO_G" + id + ".pdf");
		  }else if(filename.equals("Allegato_H.xml")){
			  fileDownload.setDisplayName("ALLEGATO_H" + id + ".pdf");
		  }
		  fileDownload.sendFile(context, bytes, "application/pdf");
		  }
		} catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
		} finally {
		  this.freeConnection(context, db);
		}
		return ("-none-");
	  }
  */
  
  	 public String executeCommandPrintReport(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		try {
			
			HttpServletResponse res = context.getResponse();
			db = this.getConnection(context);
			Organization org = new Organization();
			String id = (String) context.getRequest().getParameter("id");
			org.setOrgId(id);
			String reportDir = getWebInfPath(context, "reports");
			String filename = (String) context.getRequest().getParameter("file");
			PdfReader reader = new PdfReader( reportDir + filename);
			ByteArrayOutputStream out = new ByteArrayOutputStream()	;
			PdfStamper stamper = new PdfStamper(reader, out )		;
			AcroFields form = stamper.getAcroFields()				;
			
			Image instanceImg = Image.getInstance(reportDir + "/images/regionecampania.jpg");

			float[] photograph = form.getFieldPositions("logo");
			Rectangle rect = new Rectangle(photograph[1], photograph[2], photograph[3], photograph[4]);
			instanceImg.scaleToFit(rect.getWidth(), rect.getHeight());
			instanceImg.setAbsolutePosition(277.5F,760.35F);
			//instanceImg.setAbsolutePosition(31.2F,768.35F);
			//instanceImg.setAbsolutePosition(28.0F,780.35F);
			PdfContentByte cb = stamper.getOverContent(1);
			cb.addImage (instanceImg);
			ResultSet rs = null;
			ResultSet rs1 = null;
			String tipoAllegato = null;
			if(filename.equals("AllegatoB1.pdf")){
				rs = org.queryRecord_allegatoB(db);
				tipoAllegato = "B";
			} else if(filename.equals("AllegatoE.pdf")){
				rs = org.queryRecord_allegatoE(db);
				tipoAllegato = "E";
			}else if(filename.equals("AllegatoC.pdf")){
				rs = org.queryRecord_allegatoC(db);
				rs1 = org.queryRecord_specie(db);
				tipoAllegato = "C";
			}else if(filename.equals("AllegatoD.pdf")){
				rs = org.queryRecord_allegatoD(db);
				rs1 = org.queryRecord_specie(db);
				tipoAllegato = "D";
			} else if(filename.equals("AllegatoG.pdf")){
				rs = org.queryRecord_allegatoG(db);
				rs1 = org.queryRecord_specie(db);
				tipoAllegato = "G";
			}else if(filename.equals("AllegatoH.pdf")){
				rs = org.queryRecord_allegatoH(db);
				tipoAllegato = "H";
			}
			
			
			org.setFields(rs, rs1, form, tipoAllegato);

			stamper.setFormFlattening( true );
			stamper.close();
			
			FileDownload fileDownload = new FileDownload();
			if(filename.equals("AllegatoB1.pdf")){
		    	fileDownload.setDisplayName("ALLEGATO_B" + id + ".pdf");
			} else if(filename.equals("AllegatoE.pdf")){
				fileDownload.setDisplayName("ALLEGATO_E" + id + ".pdf");
			}else if(filename.equals("AllegatoC.pdf")){
				fileDownload.setDisplayName("ALLEGATO_C" + id + ".pdf");
			}else if(filename.equals("AllegatoD.pdf")){
				fileDownload.setDisplayName("ALLEGATO_D" + id + ".pdf");
			} else if(filename.equals("AllegatoG.pdf")){
				fileDownload.setDisplayName("ALLEGATO_G" + id + ".pdf");
			}else if(filename.equals("AllegatoH.pdf")){
				fileDownload.setDisplayName("ALLEGATO_H" + id + ".pdf");
			}

			fileDownload.sendFile(context, out.toByteArray(), "application/pdf");
			out.close();

		} catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
		} finally {
		  this.freeConnection(context, db);
		}
		
		return "-none-";
		
	  }
  
  
  	 public String executeCommandStampaAllegato(ActionContext context) {
 	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
 		  return ("PermissionError");
 		}
 		Connection db = null;
 		try {
 			
 			db = this.getConnection(context);
 			Organization org = new Organization();
 			String id = (String) context.getRequest().getParameter("id");
 			org.setOrgId(id);
 			String filename = (String) context.getRequest().getParameter("file");
 			ResultSet rs = null;
 			ResultSet rs1 = null;
 			String tipoAllegato = null;
 			if(filename.equals("AllegatoB1.pdf")){
 				rs = org.queryRecord_allegatoB(db);
 				tipoAllegato = "B";
 			} else if(filename.equals("AllegatoE.pdf")){
 				rs = org.queryRecord_allegatoE(db);
 				tipoAllegato = "E";
 			}else if(filename.equals("AllegatoC.pdf")){
 				rs = org.queryRecord_allegatoC(db);
 				rs1 = org.queryRecord_specie(db);
 				tipoAllegato = "C";
 			}else if(filename.equals("AllegatoD.pdf")){
 				rs = org.queryRecord_allegatoD(db);
 				rs1 = org.queryRecord_specie(db);
 				tipoAllegato = "D";
 			} else if(filename.equals("AllegatoG.pdf")){
 				rs = org.queryRecord_allegatoG(db);
 				rs1 = org.queryRecord_specie(db);
 				tipoAllegato = "G";
 			}else if(filename.equals("AllegatoH.pdf")){
 				rs = org.queryRecord_allegatoH(db);
 				tipoAllegato = "H";
 			}
 			SchedaAllegato allegatoDetails = new SchedaAllegato();
 			if (rs.next()) {
 				allegatoDetails.buildRecord(rs);
 			}
 			rs.close();
 			
 			String specie="";
 			String desc = "";
 			if (rs1!=null){
 			while (rs1.next()){
 				try {
 					desc = rs1.getString("codice10");
 				} catch (SQLException sqlex) {
 					// System.out.println("not found");
 				}
 			specie=specie+rs1.getString("d1") + "-";	
 			}
 			rs1.close();
 			
 			if (desc!=null && !desc.equals("null") && !desc.equals("")){
 				desc = " Descrizione: "+desc;
 				specie = specie + desc;
 			}
 			
 			context.getRequest().setAttribute("specieList", specie);
 			}
 			context.getRequest().setAttribute("allegatoDetails", allegatoDetails);
 			  context.getRequest().setAttribute("tipoAllegato", tipoAllegato);
 		} catch (Exception errorMessage) {
 		  context.getRequest().setAttribute("Error", errorMessage);
 		  return ("SystemError");
 		} finally {
 		  this.freeConnection(context, db);
 		}
 		 
 		return "StampaAllegatoOk";
 		
 	  }
  
  
  public boolean replaceVista(Connection db, Timestamp dataInizio, Timestamp dataFine) throws SQLException {
	  
	    String sql =
	        "CREATE or REPLACE VIEW rendicontazione_annuale AS select(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=1 and assigned_date<=? and assigned_date>=?}) as n_bovini_ispezionati_macello, /*Bovini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=1 and assigned_date<=? and assigned_date>=?}) as n_bovini_ispezionati_esportazione, /*Bovini da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=1 and assigned_date<=? and assigned_date>=?}) as n_bovini_ispezionati_importati, /*Bovini importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=1 and assigned_date<=? and assigned_date>=?}) as n_bovini_ispezionati_altro, /*Bovini da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=2 and assigned_date<=? and assigned_date>=?}) as n_suini_ispezionati_macello, /*Suini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=2 and assigned_date<=? and assigned_date>=?}) as n_suini_ispezionati_esportazione, /*Suini da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=2 and assigned_date<=? and assigned_date>=?}) as n_suini_ispezionati_importati, /*Suini importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=2 and assigned_date<=? and assigned_date>=?}) as n_suini_ispezionati_altro, /*Suini da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=3 and assigned_date<=? and assigned_date>=?}) as n_piccoli_ispezionati_macello, /*Piccoli Ruminanti da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=3 and assigned_date<=? and assigned_date>=?}) as n_piccoli_ispezionati_esportazione, /*Piccoli Ruminanti da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=3 and assigned_date<=? and assigned_date>=?}) as n_piccoli_ispezionati_importati, /*Piccoli Ruminanti importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=3 and assigned_date<=? and assigned_date>=?}) as n_piccoli_ispezionati_altro, /*Piccoli Ruminanti da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=4 and assigned_date<=? and assigned_date>=?}) as n_equidi_ispezionati_macello, /*Equidi da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=4 and assigned_date<=? and assigned_date>=?}) as n_equidi_ispezionati_esportazione, /*Equidi da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=4 and assigned_date<=? and assigned_date>=?}) as n_equidi_ispezionati_importati, /*Equidi importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=4 and assigned_date<=? and assigned_date>=?}) as n_equidi_ispezionati_altro, /*Equidi da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=5 and assigned_date<=? and assigned_date>=?}) as n_volatili_ispezionati_macello, /*Volatili da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=5 and assigned_date<=? and assigned_date>=?}) as n_volatili_ispezionati_esportazione, /*Volatili da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=5 and assigned_date<=? and assigned_date>=?}) as n_volatili_ispezionati_importati, /*Volatili importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=5 and assigned_date<=? and assigned_date>=?}) as n_volatili_ispezionati_altro, /*Volatili da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=6 and assigned_date<=? and assigned_date>=?}) as n_altre_ispezionati_macello, /*Altre da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=6 and assigned_date<=? and assigned_date>=?}) as n_altre_ispezionati_esportazione, /*Altre da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=6 and assigned_date<=? and assigned_date>=?}) as n_altre_ispezionati_importati, /*Altre importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=6 and assigned_date<=? and assigned_date>=?}) as n_altre_ispezionati_altro, /*Altre da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 1 and animalitrasportati=10 and assigned_date<=? and assigned_date>=?}) as n_buf_ispezionati_macello, /*Buf da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 2 and animalitrasportati=10 and assigned_date<=? and assigned_date>=?}) as n_buf_ispezionati_esportazione, /*Buf da esportazione CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 3 and animalitrasportati=10 and assigned_date<=? and assigned_date>=?}) as n_buf_ispezionati_importati, /*Buf importati CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and specietrasportata = 4 and animalitrasportati=10 and assigned_date<=? and assigned_date>=?}) as n_buf_ispezionati_altro, /*Buf da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as bovini_ispezionati_cd, /*Bovini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as suini_ispezionati_cd, /*Suini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as piccoli_ispezionati_cd, /*Piccoli Ruminanti da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as equidi_ispezionati_cd, /*Equidi da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as volatili_ispezionati_cd, /*Volatili da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as altre_ispezionati_cd, /*Altre da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?}) as buf_ispezionati_cd, /*Buf da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t1 on(ticket.ticketid = t1.idcontrollo and t1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_trasp_isp, /*Bovini da macello ISP*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t2 on(ticket.ticketid = t2.idcontrollo and t2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_arrivo_isp, /*Bovini da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t3 on(ticket.ticketid = t3.idcontrollo and t3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_mercato_isp, /*Bovini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t4 on(ticket.ticketid = t4.idcontrollo and t4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_partenza_isp, /*Bovini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t5 on(ticket.ticketid = t5.idcontrollo and t5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_sosta_isp, /*Bovini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t6 on(ticket.ticketid = t6.idcontrollo and t6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as bovini_trasf_isp, /*Bovini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c1 on(ticket.ticketid = c1.idcontrollo and c1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_trasp_isp, /*Suini da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c2 on(ticket.ticketid = c2.idcontrollo and c2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_arrivo_isp, /*Suini da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c3 on(ticket.ticketid = c3.idcontrollo and c3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_mercato_isp, /*Suini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c4 on(ticket.ticketid = c4.idcontrollo and c4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_partenza_isp, /*Suini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c5 on(ticket.ticketid = c5.idcontrollo and c5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_sosta_isp, /*Suini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c6 on(ticket.ticketid = c6.idcontrollo and c6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as suini_traf_isp, /*Suini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d1 on(ticket.ticketid = d1.idcontrollo and d1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_trasp_isp, /*Piccoli Ruminanti da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d2 on(ticket.ticketid = d2.idcontrollo and d2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_arrivo_isp, /*Piccoli Ruminanti da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d3 on(ticket.ticketid = d3.idcontrollo and d3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_mercato_isp, /*Piccoli Ruminanti importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d4 on(ticket.ticketid = d4.idcontrollo and d4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_partenza_isp, /*Piccoli Ruminanti da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d5 on(ticket.ticketid = d5.idcontrollo and d5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_sosta_isp, /*Piccoli Ruminanti importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d6 on(ticket.ticketid = d6.idcontrollo and d6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as piccoli_trasf_isp, /*Piccoli Ruminanti da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s1 on(ticket.ticketid = s1.idcontrollo and s1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_trasp_isp, /*Equidi da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s2 on(ticket.ticketid = s2.idcontrollo and s2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_arrivo_isp, /*Equidi da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s3 on(ticket.ticketid = s3.idcontrollo and s3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_mercato_isp, /*Equidi importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s4 on(ticket.ticketid = s4.idcontrollo and s4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_partenza_isp, /*Equidi da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s5 on(ticket.ticketid = s5.idcontrollo and s5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_sosta_isp, /*Equidi importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s6 on(ticket.ticketid = s6.idcontrollo and s6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as equidi_trasf_isp, /*Equidi da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x1 on(ticket.ticketid = x1.idcontrollo and x1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_trasp_isp, /*Volatili da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x2 on(ticket.ticketid = x2.idcontrollo and x2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_arrivo_isp, /*Volatili da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x3 on(ticket.ticketid = x3.idcontrollo and x3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_mercato_isp, /*Volatili importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x4 on(ticket.ticketid = x4.idcontrollo and x4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_partenza_isp, /*Volatili da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x5 on(ticket.ticketid = x5.idcontrollo and x5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_sosta_isp, /*Volatili importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x6 on(ticket.ticketid = x6.idcontrollo and x6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as volatili_trasf_isp, /*Volatili da altro CD*/" +
	         "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r1 on(ticket.ticketid = r1.idcontrollo and r1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_trasp_isp, /*Altre da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r2 on(ticket.ticketid = r2.idcontrollo and r2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_arrivo_isp, /*Altre da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r3 on(ticket.ticketid = r3.idcontrollo and r3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_mercato_isp, /*Altre importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r4 on(ticket.ticketid = r4.idcontrollo and r4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_partenza_isp, /*Altre da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r5 on(ticket.ticketid = r5.idcontrollo and r5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_sosta_isp, /*Altre importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r6 on(ticket.ticketid = r6.idcontrollo and r6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as altre_trasf_isp, /*Altre da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y1 on(ticket.ticketid = y1.idcontrollo and y1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_trasp_isp, /*Buf da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y2 on(ticket.ticketid = y2.idcontrollo and y2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_arrivo_isp, /*Buf da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y3 on(ticket.ticketid = y3.idcontrollo and y3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_mercato_isp, /*Buf importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y4 on(ticket.ticketid = y4.idcontrollo and y4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_partenza_isp, /*Buf da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y5 on(ticket.ticketid = y5.idcontrollo and y5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_sosta_isp, /*Buf importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y6 on(ticket.ticketid = y6.idcontrollo and y6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as buf_trasf_isp, /*Buf da altro CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p1 on(ticket.ticketid = p1.idcontrollo and p1.tipoispezione = 1) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as trasp_mezzi, /*Bovini da macello CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p2 on(ticket.ticketid = p2.idcontrollo and p2.tipoispezione = 2) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as arrivo_mezzi, /*Bovini da esportazione CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p3 on(ticket.ticketid = p3.idcontrollo and p3.tipoispezione = 3) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as mercato_mezzi, /*Bovini importati CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p4 on(ticket.ticketid = p4.idcontrollo and p4.tipoispezione = 4) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as partenza_mezzi, /*Bovini da altro CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p5 on(ticket.ticketid = p5.idcontrollo and p5.tipoispezione = 5) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as sosta_mezzi, /*Suini da macello CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p6 on(ticket.ticketid = p6.idcontrollo and p6.tipoispezione = 6) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as trasf_mezzi, /*Suini da esportazione CD*/" +
	        "(select SUM(mezziispezionati) from ticket where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?}) as cd_mezzi_isp, /*Buf da altro CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_viol_cd, /*Bovini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_viol_cd, /*Suini da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_viol_cd, /*Piccoli Ruminanti da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_viol_cd, /*Equidi da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_viol_cd, /*Volatili da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_viol_cd, /*Altre da macello CD*/" +
	        "(select SUM(animali) from ticket where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 3 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_viol_cd, /*Buf da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t1 on(ticket.ticketid = t1.idcontrollo and t1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_trasp_viol, /*Bovini da macello ISP*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t2 on(ticket.ticketid = t2.idcontrollo and t2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_arrivo_viol, /*Bovini da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t3 on(ticket.ticketid = t3.idcontrollo and t3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_mercato_viol, /*Bovini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t4 on(ticket.ticketid = t4.idcontrollo and t4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_partenza_viol, /*Bovini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t5 on(ticket.ticketid = t5.idcontrollo and t5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_sosta_viol, /*Bovini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese t6 on(ticket.ticketid = t6.idcontrollo and t6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=1 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as bovini_trasf_viol, /*Bovini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c1 on(ticket.ticketid = c1.idcontrollo and c1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_trasp_viol, /*Suini da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c2 on(ticket.ticketid = c2.idcontrollo and c2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_arrivo_viol, /*Suini da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c3 on(ticket.ticketid = c3.idcontrollo and c3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_mercato_viol, /*Suini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c4 on(ticket.ticketid = c4.idcontrollo and c4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_partenza_viol, /*Suini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c5 on(ticket.ticketid = c5.idcontrollo and c5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_sosta_viol, /*Suini importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese c6 on(ticket.ticketid = c6.idcontrollo and c6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=2 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as suini_traf_viol, /*Suini da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d1 on(ticket.ticketid = d1.idcontrollo and d1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_trasp_viol, /*Piccoli Ruminanti da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d2 on(ticket.ticketid = d2.idcontrollo and d2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_arrivo_viol, /*Piccoli Ruminanti da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d3 on(ticket.ticketid = d3.idcontrollo and d3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_mercato_viol, /*Piccoli Ruminanti importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d4 on(ticket.ticketid = d4.idcontrollo and d4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_partenza_viol, /*Piccoli Ruminanti da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d5 on(ticket.ticketid = d5.idcontrollo and d5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_sosta_viol, /*Piccoli Ruminanti importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese d6 on(ticket.ticketid = d6.idcontrollo and d6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as piccoli_trasf_viol, /*Piccoli Ruminanti da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s1 on(ticket.ticketid = s1.idcontrollo and s1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_trasp_viol, /*Equidi da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s2 on(ticket.ticketid = s2.idcontrollo and s2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_arrivo_viol, /*Equidi da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s3 on(ticket.ticketid = s3.idcontrollo and s3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_mercato_viol, /*Equidi importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s4 on(ticket.ticketid = s4.idcontrollo and s4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_partenza_viol, /*Equidi da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s5 on(ticket.ticketid = s5.idcontrollo and s5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_sosta_viol, /*Equidi importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese s6 on(ticket.ticketid = s6.idcontrollo and s6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=4 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as equidi_trasf_viol, /*Equidi da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x1 on(ticket.ticketid = x1.idcontrollo and x1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_trasp_viol, /*Volatili da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x2 on(ticket.ticketid = x2.idcontrollo and x2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_arrivo_viol, /*Volatili da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x3 on(ticket.ticketid = x3.idcontrollo and x3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_mercato_viol, /*Volatili importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x4 on(ticket.ticketid = x4.idcontrollo and x4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_partenza_viol, /*Volatili da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x5 on(ticket.ticketid = x5.idcontrollo and x5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_sosta_viol, /*Volatili importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese x6 on(ticket.ticketid = x6.idcontrollo and x6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=5 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as volatili_trasf_viol, /*Volatili da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r1 on(ticket.ticketid = r1.idcontrollo and r1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_trasp_viol, /*Altre da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r2 on(ticket.ticketid = r2.idcontrollo and r2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_arrivo_viol, /*Altre da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r3 on(ticket.ticketid = r3.idcontrollo and r3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_mercato_viol, /*Altre importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r4 on(ticket.ticketid = r4.idcontrollo and r4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_partenza_viol, /*Altre da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r5 on(ticket.ticketid = r5.idcontrollo and r5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_sosta_viol, /*Altre importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese r6 on(ticket.ticketid = r6.idcontrollo and r6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=6 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as altre_trasf_viol, /*Altre da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y1 on(ticket.ticketid = y1.idcontrollo and y1.tipoispezione = 1) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_trasp_viol, /*Buf da macello CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y2 on(ticket.ticketid = y2.idcontrollo and y2.tipoispezione = 2) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_arrivo_viol, /*Buf da esportazione CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y3 on(ticket.ticketid = y3.idcontrollo and y3.tipoispezione = 3) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_mercato_viol, /*Buf importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y4 on(ticket.ticketid = y4.idcontrollo and y4.tipoispezione = 4) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_partenza_viol, /*Buf da altro CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y5 on(ticket.ticketid = y5.idcontrollo and y5.tipoispezione = 5) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_sosta_viol, /*Buf importati CD*/" +
	        "(select SUM(animali) from ticket left join tipocontrolloufficialeimprese y6 on(ticket.ticketid = y6.idcontrollo and y6.tipoispezione = 6) where tipologia = 3 and animalitrasportati=10 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as buf_trasf_viol, /*Buf da altro CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p1 on(ticket.ticketid = p1.idcontrollo and p1.tipoispezione = 1) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as trasp_mezzi_viol, /*Bovini da macello CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p2 on(ticket.ticketid = p2.idcontrollo and p2.tipoispezione = 2) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as arrivo_mezzi_viol, /*Bovini da esportazione CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p3 on(ticket.ticketid = p3.idcontrollo and p3.tipoispezione = 3) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as mercato_mezzi_viol, /*Bovini importati CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p4 on(ticket.ticketid = p4.idcontrollo and p4.tipoispezione = 4) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as partenza_mezzi_viol, /*Bovini da altro CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p5 on(ticket.ticketid = p5.idcontrollo and p5.tipoispezione = 5) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as sosta_mezzi_viol, /*Suini da macello CD*/" +
	        "(select SUM(mezziispezionati) from ticket left join tipocontrolloufficialeimprese p6 on(ticket.ticketid = p6.idcontrollo and p6.tipoispezione = 6) where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as trasf_mezzi_viol, /*Suini da esportazione CD*/" +
	        "(select SUM(mezziispezionati) from ticket where tipologia = 3 and provvedimenti_prescrittivi = 4 and assigned_date<=? and assigned_date>=?} and ncrilevate = TRUE) as cd_mezzi_viol;";
	    int i = 0;
	    PreparedStatement pst = db.prepareStatement(sql);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.setTimestamp(++i, dataInizio);
	    pst.setTimestamp(++i, dataFine);
	    pst.execute();
	    pst.close();
	    return true;
	  }
  
  public String executeCommandPrintReportRendicontazione2(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		try {
		  db = this.getConnection(context);
		  String anno = context.getRequest().getParameter("anno");
		  Double anno_vero=null;
		  
		  if(anno.equals("1")){
			  anno_vero = Double.parseDouble("2008");
			 
		  }else  if(anno.equals("2")){
			  anno_vero=Double.parseDouble("2009");
			 
		  }else  if(anno.equals("3")){
			  anno_vero = Double.parseDouble("2010");
			  
		  }else  if(anno.equals("4")){
			  anno_vero = Double.parseDouble("2011");
			 
		  }else  if(anno.equals("5")){
			  anno_vero = Double.parseDouble("2012");
			  
		  }else  if(anno.equals("6")){
			  anno_vero = Double.parseDouble("2013");
			  
		  }else  if(anno.equals("7")){
			  anno_vero = Double.parseDouble("2014");
			  
		  }else  if(anno.equals("8")){
			  anno_vero = Double.parseDouble("2015");
		  }
		  HashMap map = new HashMap();
		  map.put("anno", anno_vero);
		  if(user.getSiteId()!=-1)
			  map.put("idAsl",user.getSiteId());
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		/* Date dataInizio = (Date)formatter.parse(anno_inizio);
		 Date dataFine = (Date)formatter.parse(anno_fine);
		  //replaceVista(db, new Timestamp(dataInizio.getTime()), new Timestamp(dataFine.getTime()));
		  map.put("dataInizioControllo", new Timestamp(dataInizio.getTime()));
		  map.put("dataFineControllo", new Timestamp(dataFine.getTime()));*/
		 
		  map.put("path", getWebInfPath(context, "reports"));
		  //provide the dictionary as a parameter to the quote report
		  map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
		  //String filename = "modB.xml";
		  
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
		   
			  fileDownload.setDisplayName("Rendicontazione_" + anno_vero + ".pdf");
		  
		  fileDownload.sendFile(context, bytes, "application/pdf");
		  }
		} catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
		} finally {
		  this.freeConnection(context, db);
		}
		return ("-none-");
	  }
  
   /*Lato jsp e' da includere il file rendicontazione.pdf al posto di rendiconazione_asl.xml*/
  	public String executeCommandPrintReportRendicontazione(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view") && !hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
		  return ("PermissionError");
		}
		Connection db = null;
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		try {
		  db = this.getConnection(context);
		  String anno = context.getRequest().getParameter("anno");
		  Double anno_vero=null;
		  if(anno.equals("1")){
			  anno_vero = Double.parseDouble("2008");
			 
		  }else  if(anno.equals("2")){
			  anno_vero=Double.parseDouble("2009");
			 
		  }else  if(anno.equals("3")){
			  anno_vero = Double.parseDouble("2010");
			  
		  }else  if(anno.equals("4")){
			  anno_vero = Double.parseDouble("2011");
			 
		  }else  if(anno.equals("5")){
			  anno_vero = Double.parseDouble("2012");
			  
		  }else  if(anno.equals("6")){
			  anno_vero = Double.parseDouble("2013");
			  
		  }else  if(anno.equals("7")){
			  anno_vero = Double.parseDouble("2014");
			  
		  }else  if(anno.equals("8")){
			  anno_vero = Double.parseDouble("2015");
		  }
		  
		  db = this.getConnection(context);
		  Organization org = new Organization();
		  String reportDir = getWebInfPath(context, "reports");
		  HashMap<TipoAnimaliRendicontazione, HashMap<Integer, Integer>> hashAnimaliTrasportati = new HashMap<TipoAnimaliRendicontazione, HashMap<Integer,Integer>> ();
		  
		  int somma_specie = 0;
		  String filename = (String) context.getRequest().getParameter("file");
		  PdfReader reader = new PdfReader( reportDir + filename);
		  ByteArrayOutputStream out = new ByteArrayOutputStream()	;
		  PdfStamper stamper = new PdfStamper(reader, out )		;
		  AcroFields form = stamper.getAcroFields()				;
		  ResultSet rs_animali = null;
		  
		  
		  if(user.getSiteId()!=-1){
			rs_animali = org.queryRecord_rendicontazione_asl(db,anno_vero, user.getSiteId()); 
		  }
		  else {
			 rs_animali = org.queryRecord_rendicontazione_animali(db,anno_vero);
		  }
		  
		  //Initialize hashMap
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  hashAnimaliTrasportati.put(t, new HashMap<Integer, Integer>());
			  /*Initialize  tipo categoria trasportata*/
			  for (TipoCategoriaRendicontazione ct : TipoCategoriaRendicontazione.values()){
				  hashAnimaliTrasportati.get(t).put(ct.getCategoria(), 0);
			  }
			 
		  }
		  
		  
		  while(rs_animali.next()){
			  if(rs_animali.getString("tipo_animali") != null && !(rs_animali.getString("tipo_animali").equals("")) ) {
				  hashAnimaliTrasportati.get(TipoAnimaliRendicontazione.valueOf(rs_animali.getString("tipo_animali"))).put(rs_animali.getInt("categoria"),rs_animali.getInt("somma_animali_specie"));
			  }
		  }
		  
		  
		  
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  
			  for (TipoCategoriaRendicontazione ct : TipoCategoriaRendicontazione.values()){
				 somma_specie += hashAnimaliTrasportati.get(t).get(ct.getCategoria());
				 form.setField(t+""+ct.getCategoria(), ""+hashAnimaliTrasportati.get(t).get(ct.getCategoria())); 
			  }
			  form.setField("SOMMA"+t, ""+somma_specie);
			  somma_specie = 0;
			
			  
		  }
		  
		  
		  int anno_int = anno_vero.intValue(); 
		  form.setField("anno", Integer.toString(anno_int));
		  
		  this.setFieldIspezioniRendicontazione(form, anno_vero,db);
		  this.setFieldViolazioniRendicontazione(form,anno_vero,db);
		  this.setFieldMezziRendicontazione(form,anno_vero,db);
		  this.setFieldMezziViolazioneRendicontazione(form,anno_vero,db);
		  /*while(rs.next()){
			  setFormFieldTot(rs, form);
			  
			  if(user.getSiteId()!=-1){
				  form.setField("asl", rs.getString("nomeasl"));
			  }
			  else {
				  form.setField("asl", "Tutte");
			  }
			  String nomeC = "";
			  for(int i=1;i<numberOfColumns;i++){
				  nomeC = rsMetaData.getColumnName(i);
				  if(form.getField(nomeC) != null && (rs.getInt(nomeC)) !=0 ){
					  form.setField(nomeC, Integer.toString(rs.getInt(nomeC)));
				  }  
			  }
		  }*/
		
		 rs_animali.close();
		 stamper.setFormFlattening( true );
		 stamper.close();
		
		 
		 FileDownload fileDownload = new FileDownload();
		 fileDownload.setDisplayName("Rendicontazione"+anno_vero+".pdf");
		 fileDownload.sendFile(context, out.toByteArray(), "application/pdf");
		 out.close();
		 
		} catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
		} finally {
		  this.freeConnection(context, db);
		}
		return ("-none-");
	  }

  
  
  
  private void setFormFieldTot(ResultSet rs, AcroFields form) throws SQLException, IOException, DocumentException {
		// TODO Auto-generated method stub
	  
	  int b_macello = rs.getInt("n_bovini_ispezionati_macello")+rs.getInt("n_buf_ispezionati_macello");
	  int b_esportazione = rs.getInt("n_bovini_ispezionati_esportazione")+rs.getInt("n_buf_ispezionati_esportazione");
	  int b_importati = rs.getInt("n_bovini_ispezionati_importati")+rs.getInt("n_buf_ispezionati_importati");
	  int b_altro = rs.getInt("n_bovini_ispezionati_altro")+rs.getInt("n_buf_ispezionati_altro");
	  
	  int tot_bovini_ispezionati = b_macello + b_esportazione + b_importati + b_altro;
	  
	  int b_trasp_isp =  rs.getInt("bovini_trasp_isp")+ rs.getInt("buf_trasp_isp");
	  int b_arrivo_isp =  rs.getInt("bovini_arrivo_isp") + rs.getInt("buf_arrivo_isp");
	  int b_mercato_isp = rs.getInt("bovini_mercato_isp") + rs.getInt("buf_mercato_isp");
	  int b_partenza_isp =  rs.getInt("bovini_partenza_isp") + rs.getInt("buf_partenza_isp");
	  int b_sosta_isp = rs.getInt("bovini_sosta_isp") +rs.getInt("buf_sosta_isp");
	  int b_traf_isp = rs.getInt("bovini_trasf_isp") + rs.getInt("buf_trasf_isp");
	  
	  int tot_bovini_isp = b_trasp_isp + b_arrivo_isp + b_mercato_isp + b_partenza_isp + b_sosta_isp + b_traf_isp;
	  
	  int b_trasp_viol = rs.getInt("bovini_trasp_viol")+ rs.getInt("buf_trasf_viol");
	  int b_arrivo_viol = rs.getInt("bovini_arrivo_viol") + rs.getInt("buf_arrivo_viol");
	  int b_mercato_viol = rs.getInt("bovini_mercato_viol") + rs.getInt("buf_mercato_viol");
	  int b_partenza_viol =  rs.getInt("bovini_partenza_viol") + rs.getInt("buf_partenza_viol");
	  int b_sosta_viol = rs.getInt("bovini_sosta_viol") + rs.getInt("buf_sosta_viol") ;
	  int b_traf_viol = rs.getInt("bovini_trasf_viol")+ rs.getInt("buf_trasf_viol");
	  
	  int tot_bovini_viol = b_trasp_viol + b_arrivo_viol + b_mercato_viol + b_partenza_viol + b_sosta_viol + b_traf_viol;
	  
	  int tot_suini_ispezionati = rs.getInt("n_suini_ispezionati_macello") + rs.getInt("n_suini_ispezionati_esportazione") +
	  							  rs.getInt("n_suini_ispezionati_importati") + rs.getInt("n_bovini_ispezionati_altro");
	  int tot_piccoli_ispezionati = rs.getInt("n_piccoli_ispezionati_macello") + rs.getInt("n_piccoli_ispezionati_esportazione") +
	  								rs.getInt("n_piccoli_ispezionati_importati") + rs.getInt("n_piccoli_ispezionati_altro");
	  int tot_equidi_ispezionati = rs.getInt("n_equidi_ispezionati_macello") + rs.getInt("n_equidi_ispezionati_esportazione") +
		  						   rs.getInt("n_equidi_ispezionati_importati") + rs.getInt("n_equidi_ispezionati_altro");;
	  int tot_volatili_ispezionati = rs.getInt("n_volatili_ispezionati_macello") + rs.getInt("n_volatili_ispezionati_esportazione") +
		  							 rs.getInt("n_volatili_ispezionati_importati") + rs.getInt("n_volatili_ispezionati_altro"); ;
	  int tot_altre_ispezionati = rs.getInt("n_altre_ispezionati_macello") + rs.getInt("n_altre_ispezionati_esportazione") +
		  						  rs.getInt("n_altre_ispezionati_importati") + rs.getInt("n_altre_ispezionati_altro");
	  
	  int tot_mezzi = rs.getInt("trasp_mezzi") + rs.getInt("arrivo_mezzi") + rs.getInt("mercato_mezzi") + rs.getInt("partenza_mezzi") +
		  			  rs.getInt("sosta_mezzi") + rs.getInt("trasf_mezzi");
	  
	  int tot_suini_isp = rs.getInt("suini_trasp_isp") + rs.getInt("suini_arrivo_isp") + rs.getInt("suini_mercato_isp") + rs.getInt("suini_partenza_isp") +
		  			      rs.getInt("suini_sosta_isp") + rs.getInt("suini_traf_isp");

	  int tot_piccoli_isp = rs.getInt("piccoli_trasp_isp") + rs.getInt("piccoli_arrivo_isp") + rs.getInt("piccoli_mercato_isp") + rs.getInt("piccoli_partenza_isp") +
	      rs.getInt("piccoli_sosta_isp") + rs.getInt("piccoli_trasf_isp");
	  
	  int tot_equidi_isp = rs.getInt("equidi_trasp_isp") + rs.getInt("equidi_arrivo_isp") + rs.getInt("equidi_mercato_isp") + rs.getInt("equidi_partenza_isp") +
	      rs.getInt("equidi_sosta_isp") + rs.getInt("equidi_trasf_isp");
	  
	  int tot_volatili_isp = rs.getInt("volatili_trasp_isp") + rs.getInt("volatili_arrivo_isp") + rs.getInt("volatili_mercato_isp") + rs.getInt("volatili_partenza_isp") +
	      rs.getInt("volatili_sosta_isp") + rs.getInt("volatili_trasf_isp");
		
	  int tot_altre_isp = rs.getInt("altre_trasp_isp") + rs.getInt("altre_arrivo_isp") + rs.getInt("altre_mercato_isp") + rs.getInt("altre_partenza_isp") +
      rs.getInt("altre_sosta_isp") + rs.getInt("altre_trasf_isp");
  
	  form.setField("b_ispezionati_macello",Integer.toString(b_macello));
	  form.setField("b_ispezionati_esportazione",Integer.toString(b_esportazione));
	  form.setField("b_ispezionati_importati",Integer.toString(b_importati));
	  form.setField("b_ispezionati_altro",Integer.toString(b_altro));
	  form.setField("tot_bovini_ispezionati", Integer.toString(tot_bovini_ispezionati));
	  form.setField("tot_suini_ispezionati", Integer.toString(tot_suini_ispezionati));
	  form.setField("tot_equidi_ispezionati", Integer.toString(tot_equidi_ispezionati));
	  form.setField("tot_piccoli_ispezionati", Integer.toString(tot_piccoli_ispezionati));
	  form.setField("tot_volatili_ispezionati", Integer.toString(tot_volatili_ispezionati));
	  form.setField("tot_altre_ispezionati", Integer.toString(tot_altre_ispezionati));
	  form.setField("b_trasp_isp", Integer.toString(b_trasp_isp));
	  form.setField("b_arrivo_isp", Integer.toString(b_arrivo_isp));
	  form.setField("b_mercato_isp", Integer.toString(b_mercato_isp));
	  form.setField("b_partenza_isp", Integer.toString(b_partenza_isp));
	  form.setField("b_sosta_isp", Integer.toString(b_sosta_isp));
	  form.setField("b_traf_isp", Integer.toString(b_traf_isp));
	  form.setField("tot_bovini_isp", Integer.toString(tot_bovini_isp));
	  form.setField("tot_suini_isp", Integer.toString(tot_suini_isp));
	  form.setField("tot_equidi_isp", Integer.toString(tot_equidi_isp));
	  form.setField("tot_volatili_isp", Integer.toString(tot_volatili_isp));
	  form.setField("tot_piccoli_isp", Integer.toString(tot_piccoli_isp));
	  form.setField("tot_altre_isp", Integer.toString(tot_altre_isp));
	  form.setField("b_trasp_viol", Integer.toString(b_trasp_viol));
	  form.setField("b_arrivo_viol", Integer.toString(b_arrivo_viol));
	  form.setField("b_mercato_viol", Integer.toString(b_mercato_viol));
	  form.setField("b_partenza_viol", Integer.toString(b_partenza_viol));
	  form.setField("b_sosta_viol", Integer.toString(b_sosta_viol));
	  form.setField("b_traf_viol", Integer.toString(b_traf_viol));
	  form.setField("tot_bovini_viol", Integer.toString(tot_bovini_viol));
	  form.setField("tot_mezzi",Integer.toString(tot_mezzi));
	  
	}


/**
   *  Reports: Displays a list of previously generated reports with
   *  view/delete/download options.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandReports(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-reports-view")) {
      return ("PermissionError");
    }
    //Set the menu: the user is in the Reports module
    addModuleBean(context, "Reports", "ViewReports");
    //Retrieve the paged list that will be used for paging through reports
    PagedListInfo rptListInfo = this.getPagedListInfo(context, "RptListInfo");
    rptListInfo.setLink("trasportoanimali.do?command=Reports");
    //Prepare the file list for accounts
    FileItemList files = new FileItemList();
    files.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
    files.setPagedListInfo(rptListInfo);
    //Check the combo box value from the report list for filtering reports
    if ("all".equals(rptListInfo.getListView())) {
      //Show only the reports that this user or someone below this user created
      files.setOwnerIdRange(this.getUserRange(context));
    } else {
      //Show only this user's reports
      files.setOwner(this.getUserId(context));
    }
    Connection db = null;
    try {
      //Get a connection from the connection pool for this user
      db = this.getConnection(context);
      //Generate the list of files based on the above criteria
      files.buildList(db);
      context.getRequest().setAttribute("FileList", files);
    } catch (Exception errorMessage) {
      //An error occurred, go to generic error message page
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      //Always free the database connection
      this.freeConnection(context, db);
    }
    return ("ReportsOK");
  }


  /**
   *  DownloadCSVReport: Sends a copy of the CSV report to the user's local
   *  machine
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDownloadCSVReport(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-reports-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      FileItem itemToDownload = null;
      itemToDownload = thisItem;
      //itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "trasportoanimali-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println(
            "Accounts-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  ShowReportHtml: Displays a preview of the selected report in HTML format
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandShowReportHtml(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-reports-view"))) {
      return ("PermissionError");
    }
    FileItem thisItem = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
      String filePath = this.getPath(context, "trasportoanimali-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      String textToShow = includeFile(filePath);
      context.getRequest().setAttribute("ReportText", textToShow);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("ReportHtmlOK");
  }


  /**
   *  GenerateForm: Displays the form that allows the user to select criteria
   *  and specify information for a new Accounts report
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandGenerateForm(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-reports-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    CustomFieldCategoryList thisList = new CustomFieldCategoryList();
    thisList.setLinkModuleId(Constants.ACCOUNTS);
    thisList.setIncludeEnabled(Constants.TRUE);
    thisList.setIncludeScheduled(Constants.TRUE);
    thisList.setAllSelectOption(true);
    thisList.setBuildResources(false);
    try {
      db = getConnection(context);
      thisList.buildList(db);
      context.getRequest().setAttribute("CategoryList", thisList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Generate new");
    return ("GenerateFormOK");
  }


  /**
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-reports-delete"))) {
      return ("PermissionError");
    }
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
      recordDeleted = thisItem.delete(
          db, this.getPath(context, "trasportoanimali-reports"));
      String filePath1 = this.getPath(context, "trasportoanimali-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath1);
      }
      String filePath2 = this.getPath(context, "trasportoanimali-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".html";
      java.io.File fileToDelete2 = new java.io.File(filePath2);
      if (!fileToDelete2.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath2);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Reports", "Reports del");
    if (recordDeleted) {
      return ("DeleteReportOK");
    } else {
      return ("DeleteReportERROR");
    }
  }


  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-view"))) {
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
      
      
      
/*    LookupList llist=new LookupList(db,"lookup_trasportoanimali_types");
      llist.addItem(-1, "-nessuno-");
      context.getRequest().setAttribute("llist", llist);
*/
      
      LookupList comuniList = new LookupList(db, "lookup_comuni");
      comuniList.addItem(-1, "");
      context.getRequest().setAttribute("ComuniList", comuniList); 
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      //tabella modificata

      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
	    llist.addItem(-1, "-- SELEZIONA VOCE --");
//	    llist.removeElementByLevel(1);
	    llist.setMultiple(true);
	    llist.setSelectSize(5);
	    context.getRequest().setAttribute("SpecieA", llist);
	    
	    LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
	      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      CategoriaTrasportata.setMultiple(true);
	      CategoriaTrasportata.setSelectSize(5);
	      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);

      //reset the offset and current letter of the paged list in order to make sure we search ALL accounts
      PagedListInfo orgListInfo = this.getPagedListInfo(
          context, "SearchOrgListInfo");
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
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  Connection db = null;
  public String executeCommandDetails(ActionContext context) {
	    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
	      return ("PermissionError");
	    }
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Organization newOrg = null;
	    try {
	      String temporgId = context.getRequest().getParameter("orgId");
	      
	      if (temporgId == null) {
	        temporgId = (String) context.getRequest().getAttribute("orgId");
	      }
	      int tempid = Integer.parseInt(temporgId);
	      db = this.getConnection(context);
	      /*if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }*/
	      newOrg = new Organization(db, tempid);
	      
	      //Caricamento Diffide
	      Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	       
	      Iterator it1 = newOrg.getAddressList().iterator();
	   	  while(it1.hasNext())
	   	  {
	   		  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress add=(org.aspcfs.modules.trasportoanimali.base.OrganizationAddress)it1.next();
	   		  if(add.getLatitude()!=0 && add.getLongitude()!=0){
	    		  String spatial_coords [] = null;
	    		  spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(add.getLatitude()), Double.toString(add.getLongitude()),db);
//	    		  if (Double.parseDouble(spatial_coords[0].replace(',', '.'))< 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.'))> 41.503754)
//	    		  {
//	    			 AjaxCalls ajaxCall = new AjaxCalls();
//	    			 String[] coordinate= ajaxCall.getCoordinate(add.getStreetAddressLine1(), add.getCity(), add.getState(), add.getZip(), ""+add.getLatitude(), ""+add.getLongitude(), "");
//	    			 add.setLatitude(coordinate[1]);
//	    			 add.setLongitude(coordinate[0]);
//	    		  }
//	    		  else
//	    		  {
	    			  add.setLatitude(spatial_coords[0]);
	    			  add.setLongitude(spatial_coords[1]);
	    		//  }  
	    	  }
	   		  
	   	  }
	      
	      
	      
	      //tipo 1
	      if(newOrg.getDunsType().equals("tipo1")){
		    RowSetDynaClass	rsdc			= null;
		    
			
				//db = getConnection(context);
		    
		    
		    
		  
			
				
				PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
				ResultSet			rs		= stat.executeQuery();
								
									
				TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
				tf.setEditable(true);
				
				tf.setItems( newOrg.getListaV(rs));
				//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
				tf.setColumnProperties("descrizione", "targa","accepted","elimina");
				
				tf.setStateAttr("restore");
				
				HtmlRow row = (HtmlRow) tf.getTable().getRow();
		        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
		        
		        
		        

		       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );

				//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "elimina" ).setTitle( "elimina" );

							
				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{

					HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Check List");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);
					
					
					
					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
				
					

					
					
					
					cg.setFilterable( true );
								

					cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
					
				
					cg.setFilterable( true );
					
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
			        				
			        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			        				Comuni comune = new Comuni();
			        				
			        				String option="";
									
			        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
			        			}
			        		}
			        
			        	);
					
					
					
					
					
					
					
					
					
					
					
					//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
					cg.setFilterable( false );
					
					
				}
						
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				
				ToolbarItem item26 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item26.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item26 );
				
				ToolbarItem item25 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item25.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item25 );
				
			
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
				
				//tipo 2
	      }else if(newOrg.getDunsType().equals("tipo2")){
	    	  RowSetDynaClass	rsdc			= null;
	  	    
	  		
				//db = getConnection(context);
				
				PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa,lunghi_viaggi" );
				ResultSet			rs		= stat.executeQuery();
								
									
				TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
				tf.setEditable(true);
				
				tf.setItems( newOrg.getListaV(rs));
				//tf.setColumnProperties("descrizione", "targa","checklist","omologazione","accepted");
				tf.setColumnProperties("descrizione", "targa","lunghi_Viaggi","accepted","elimina");

				tf.setStateAttr("restore");
				
				HtmlRow row = (HtmlRow) tf.getTable().getRow();
		        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
				//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
				//tf.getTable().getRow().getColumn( "omologazione" ).setTitle( "Omologazione" );
				tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "lunghi_Viaggi" ).setTitle( "Omologazione" );
				tf.getTable().getRow().getColumn( "elimina" ).setTitle( "elimina" );


							
				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{
					
					HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Check List");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);
					chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("lunghi_Viaggi");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Omologazione");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);
								
					
					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
					

					cg.setFilterable( true );
					
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
			        				
			        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			        				Comuni comune = new Comuni();
			        				
			        				String option="";
									
			        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
			        			}
			        		}
			        
			        	);
					
					
					cg.setFilterable( false );
					
					
				}
				
			
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				
				
				ToolbarItem item24 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item24.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item24 );
				
				ToolbarItem item23 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item23.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item23 );
				
				
				
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
	    //tipo 3
	      }else if((newOrg.getDunsType().equals("tipo3"))||(newOrg.getDunsType().equals("tipo4"))){
	    	    RowSetDynaClass	rsdc			= null;
	    	    
	    		
				//db = getConnection(context);
				
				PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
				ResultSet			rs		= stat.executeQuery();
								
									
				TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
				tf.setEditable(true);
				
				tf.setItems( newOrg.getListaV(rs));
				//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
				tf.setColumnProperties("descrizione", "targa","elimina");
				
				tf.setStateAttr("restore");
				
				HtmlRow row = (HtmlRow) tf.getTable().getRow();
		        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
		        
		        
		        

		       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
				tf.getTable().getRow().getColumn( "elimina" ).setTitle( "elimina" );
				//tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );

				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{

							
					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
				
					

					
					
					
					cg.setFilterable( true );
								

					cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
								
					cg.setFilterable( true );
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
			        				
			        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			        				Comuni comune = new Comuni();
			        				
			        				String option="";
									
			        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
			        			}
			        		}
			        
			        	);
					
					
					cg.setFilterable( false );
					
					
				}
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				
				ToolbarItem item22 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item22.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item22 );
				
				ToolbarItem item21 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item21.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item21 );
				
				
				
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
	      }
	      
	      
	      
	     
				
				
			
			
			
			
	      
				 RowSetDynaClass	rsdc2			= null;
				 
					PreparedStatement	stat2	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid+" and elimina is null");
					ResultSet			rs2		= stat2.executeQuery();
									
										
					TableFacade tf2 = TableFacadeFactory.createTableFacade( "sedi", context.getRequest() );
					tf2.setEditable(true);
					
							
					tf2.setItems( newOrg.getListaS(rs2));
					tf2.setColumnProperties( 
							"comune", "indirizzo", "provincia", "cap","elimina");
					tf2.setStateAttr("restore");
					
					HtmlRow row2 = (HtmlRow) tf2.getTable().getRow();
			        row2.setUniqueProperty("id"); // l'id e' creato da noi quando importiamo la prima volta un file
			        
			        //tf2.getTable().getRow().getColumn( "id" ).setTitle( "ID" );
					
					tf2.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
					tf2.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
					tf2.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
					tf2.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
					tf2.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
					
					
					
					//tf2.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );	
					
					Limit limit2 = tf2.getLimit();
					if(! limit2.isExported() )
					{
						
						
						
						HtmlColumn cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("comune");
						cg2.setEditable(false);
						cg2.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
				        				String comunedef=(String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);
				        				String comunedefault= "" ;
				        				if (comunedef != null)
				        				 comunedefault=comunedef.replaceAll("'", "_");
				        				Comuni comune = new Comuni();
				        				String select = "<SELECT name = 'comune_"+iddef+"'>";
				        				String option="";
										try {
											ArrayList<String> comuni = comune.queryRecord2(db);
											
											for(String c:comuni){
												String com=c.replace("'", "_");
												option = "<OPTION value='"+com+"'";
												if(com.equals(comunedef))
													option+="selected='selected'>"+c+"</OPTION>";
												else
													option+=">"+c+"</OPTION>";
												
												select += option; 
											}
											String select2 = "</SELECT>";
											select += select2;
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				        		
				        				return select;
				        			}
				        		}
				        
				        	);
						
												
						//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
						cg2.setFilterable( true );
						
						
						
						cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("elimina");
						cg2.setEditable(false);
						cg2.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
				        				String comunedef=(String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				String select = "<SELECT name = 'comune_"+iddef+"'>";
				        				String option="";
										try {
											ArrayList<String> comuni = comune.queryRecord2(db);
											
											for(String c:comuni){
												option = "<OPTION value='"+c+"'";
												if(c.equals(comunedef))
													option+="selected='selected'>"+c+"</OPTION>";
												else
													option+=">"+c+"</OPTION>";
												
												select += option; 
											}
											String select2 = "</SELECT>";
											select += select2;
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=sede&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						
						
						
						
						
						
						
						
						
						
						
						//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
						cg2.setFilterable( false );
						
						
						
						
						
						
						
						cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("indirizzo");
						//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("id");
						//cg2.setEditable(false);
						//cg2.setFilterable(false);
						cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("provincia");
						
						cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("cap");
						//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("stato");
						cg2.setFilterable( false );
						
						
					}
					
					
					
				
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf2.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					tf2.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf2.getToolbar().addToolbarItem( item2 );
					
					
					ToolbarItem item20 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createPrevPageItem();
					item20.setTooltip( "Scorri pagina indietro" );
					tf2.getToolbar().addToolbarItem( item20 );

					ToolbarItem item19 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createNextPageItem();
					item19.setTooltip( "Scorri pagina in avanti" );
					tf2.getToolbar().addToolbarItem( item19 );
				
					
					
					String tabella2 = tf2.render();
					context.getRequest().setAttribute( "tabella2", tabella2 );
					
					RowSetDynaClass	rsdc3			= null;
					
					
					
					
					 PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numeroveicoli from organization_autoveicoli where org_id="+tempid+" and elimina is null" );
						ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
						int numeroVeicoli=0;
						if(rs_veicoli.next())
						numeroVeicoli=rs_veicoli.getInt(1);
						
						
						
					      PreparedStatement	stat_sedi	= db.prepareStatement( "select count(*) as numerosedi from organization_sediveicoli where org_id="+tempid+" and elimina is null" );
							ResultSet			rs_sedi	= stat_sedi.executeQuery();
							int numeroSedi=0;
							if(rs_sedi.next())
								numeroSedi=rs_sedi.getInt(1);
							
					
					 PreparedStatement	stat_persona	= db.prepareStatement( "select count(*) as numerpersone from organization_personale where org_id="+tempid+" and elimina is null" );
						ResultSet			rs_persona	= stat_persona.executeQuery();
						int numeropersone=0;
						if(rs_persona.next())
							numeropersone=rs_persona.getInt(1);
						
						numeropersone=(numeropersone/15)+1;
						
						numeroVeicoli=(numeroVeicoli/15)+1;
						numeroSedi=(numeroSedi/15)+1;
						
						
						context.getRequest().setAttribute("numeroPersonale", numeropersone);
						context.getRequest().setAttribute("numeroAutoveicoli", numeroVeicoli);
						context.getRequest().setAttribute("numeroSedi", numeroSedi);
						

					PreparedStatement	stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid+" and elimina is null");
					ResultSet			rs3		= stat3.executeQuery();
									
										
					TableFacade tf3 = TableFacadeFactory.createTableFacade( "personale", context.getRequest() );
					
					tf3.setEditable(true);
					
					tf3.setItems( newOrg.getListaP(rs3));
					tf3.setColumnProperties( 
							"nome", "cognome", "mansione","cf","elimina");
					tf3.setStateAttr("restore");
					
					
					HtmlRow row3 = (HtmlRow) tf3.getTable().getRow();
			        row3.setUniqueProperty("cf"); // the unique worksheet properties to identify the row
			       
					tf3.getTable().getRow().getColumn( "nome" ).setTitle( "Nome" );
					tf3.getTable().getRow().getColumn( "cognome" ).setTitle( "Cognome" );
					tf3.getTable().getRow().getColumn( "mansione" ).setTitle( "Mansione" );
					tf3.getTable().getRow().getColumn( "cf" ).setTitle( "Codice Fiscale" );
					tf3.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
					
					Limit limit3 = tf3.getLimit();
					if(! limit3.isExported() )
					{
						
						
						
						HtmlColumn cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
						//cg3.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
						//cg3.setFilterable( true );
						//cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
						cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("cognome");
						cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("mansione");
						
						cg3.setFilterable( false );
						

						cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("elimina");
						cg3.setEditable(false);
						cg3.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "cf", rowCount);
				        				
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				String select = "<SELECT name = 'comune_"+iddef+"'>";
				        				String option="";
										
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=persona&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						
						
						
						
						
						
						
						
						
						
						
						//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
						cg3.setFilterable( false );
						
						
					}
				
					
					ToolbarItem item13 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createFilterItem();
					item13.setTooltip( "Filtra" );
					tf3.getToolbar().addToolbarItem( item13 );
					
					ToolbarItem item15 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createClearItem();
					item15.setTooltip( "Resetta Filtro" );
					tf3.getToolbar().addToolbarItem( item15 );
														
					ToolbarItem item16 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createSaveWorksheetItem();
					item16.setTooltip( "Salva" );
					tf3.getToolbar().addToolbarItem( item16 );
					
					ToolbarItem item29 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createPrevPageItem();
					item29.setTooltip( "Scorri pagina indietro" );
					tf3.getToolbar().addToolbarItem( item29 );
					
					ToolbarItem item30= ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createNextPageItem();
					item30.setTooltip( "Scorri pagina in avanti" );
					tf3.getToolbar().addToolbarItem( item30 );
					
					
					
					String tabella3 = tf3.render();
					context.getRequest().setAttribute( "tabella3", tabella3 );
					
	      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
	      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	    
	      LookupList animaliPropri = new LookupList(db, "lookup_specie_trasportata_tipo4");
	      animaliPropri.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      animaliPropri.setMultiple(true);
	      animaliPropri.setSelectSize(5);
		   
	      context.getRequest().setAttribute("AnimaliPropri", animaliPropri);

	      LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
	      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      CategoriaTrasportata.setMultiple(true);
	      CategoriaTrasportata.setSelectSize(5);
	      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);

	      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
		    llist.addItem(-1, "-- SELEZIONA VOCE --");
//		    llist.removeElementByLevel(1);
		    llist.setMultiple(true);
		    llist.setSelectSize(5);
		    
		    context.getRequest().setAttribute("SpecieA", llist);
	      
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
	      } if(newOrg.getCodice2()!=null){
	      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
	      context.getRequest().setAttribute("codice2", codice2);
	      } if(newOrg.getCodice3()!=null){
	      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
	      context.getRequest().setAttribute("codice3", codice3);
	      } if(newOrg.getCodice4()!=null){
	      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
	      context.getRequest().setAttribute("codice4", codice4);
	      } if(newOrg.getCodice5()!=null){
	      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
	      context.getRequest().setAttribute("codice5", codice5);
	      } if(newOrg.getCodice6()!=null){
	      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
	      context.getRequest().setAttribute("codice6", codice6);
	      } if(newOrg.getCodice7()!=null){
	      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
	      context.getRequest().setAttribute("codice7", codice7);
	      } if(newOrg.getCodice8()!=null){
	      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
	      context.getRequest().setAttribute("codice8", codice8);
	      } if(newOrg.getCodice9()!=null){
	      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
	      context.getRequest().setAttribute("codice9", codice9);
	      } if(newOrg.getCodice10()!=null){
	      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
	      context.getRequest().setAttribute("codice10", codice10);
	      }
	      
	      context.getRequest().setAttribute("codice1", codice1);
	      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
	      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("TipoLocale", TipoLocale);
	      
	      
	      
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
	      String retPage = "DetailsOK";
	      String tipo_richiesta = newOrg.getDunsType();
	      tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
	      
	      retPage = "Details_" + tipo_richiesta + "OK";
	     		          
	      return ( retPage );//("InsertOK");
	      //return getReturn(context, "Details");
	    }
	  }





  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-dashboard-view")) {
      if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
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
          "Accounts", "org.aspcfs.modules.trasportoanimali.base.AccountsListScheduledActions", "Accounts");
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
      return ("PermissionError");
    }
    String source = (String) context.getRequest().getParameter("source");
    String targa = (String) context.getRequest().getParameter("searchTarga");
    String id_autorizzazione = (String) context.getRequest().getParameter("id_autorizzazione");
    String denominazione = (String) context.getRequest().getParameter("searchAccountName");
    //boolean reportExcelRichiesto = false;
    OrganizationList organizationList = new OrganizationList();
    organizationList.setOrgSiteId(-1);
    organizationList.setTipologia(9);
    if(!"".equals(targa) && targa!=null) {
    	organizationList.setTarga(targa);
    }
    if(!"".equals(id_autorizzazione) && id_autorizzazione!=null) {
    	organizationList.setId_autorizzazione(id_autorizzazione);
    }
    if(!"".equals(denominazione) && denominazione!=null) {
    	organizationList.setDenominazione(denominazione);
    }
    
    
    /*Manca la lista degli animali*/
    
    
    
    /*
    
    String partitaIva = (String) context.getRequest().getParameter("searchPartitaIva");
    String codiceF = (String) context.getRequest().getParameter("codiceFiscale");
    String cognomeR = (String) context.getRequest().getParameter("cognomeRappresentante");
    String nomeR = (String) context.getRequest().getParameter("nomeRappresentante");
    String codIstat = (String) context.getRequest().getParameter("searchCodiceFiscaleCorrentista");
    
    
    
    OrganizationList organizationList = new OrganizationList();
    organizationList.setTipologia(9);
    
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
   	*/
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("TrasportoAnimali.do?command=Search");
    SystemStatus systemStatus = this.getSystemStatus(context);
    //Need to reset any sub PagedListInfos since this is a new account
    this.resetPagedListInfo(context);
    Connection db = null;
    try {
      db = this.getConnection(context);

      /*reportExcelRichiesto = context.getRequest().getParameter("excel")!=null && 
	   						 context.getRequest().getParameter("excel").equalsIgnoreCase("ok");

*/      
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
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", siteList);

      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("TrasportoAnimali.do?command=Search");
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
        /*if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
          organizationList.setOrgSiteId(this.getUserSiteId(context));
          organizationList.setIncludeOrganizationWithoutSite(false);
        } else */if (organizationList.getOrgSiteId() == -1) {
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
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }return "" ;
  }


  /**
   *  ViewTickets: Displays Ticket history (open and closed) for a particular
   *  Account.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandViewTickets(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-tickets-view")) {
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
        "TrasportoAnimali.do?command=ViewTickets&orgId=" + passedId);
    ticList.setPagedListInfo(ticketListInfo);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
    
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
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean();
    newOrg.setStatoImpresa(context.getRequest().getParameter("statoImpresa"));
    Organization oldOrg = null;
    String[] spa=context.getRequest().getParameterValues("specieA");
    String[] ca=context.getRequest().getParameterValues("categoriaTrasportata");
    SystemStatus systemStatus = this.getSystemStatus(context);
    newOrg.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    String tipo = context.getRequest().getParameter( "tipo_richiesta" );
    if(tipo != null && tipo.equals("tipo4")){
    newOrg.setName(context.getRequest().getParameter( "name" ));}
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual

        //don't want to populate the addresses, etc. if this is an individual account
        newOrg.setIsIndividual(false);
        newOrg.setRequestItems(context);
       
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      
      //Modifica per le coordinate
      Iterator it = newOrg.getAddressList().iterator();
      while(it.hasNext())
      {
    	  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress thisAddress = (org.aspcfs.modules.trasportoanimali.base.OrganizationAddress) it.next();
    	  //RICHIAMO METODO PER CONVERSIONE COORDINATE
    	  String[] coords = null;
    	  if(thisAddress.getLatitude()!= 0 && thisAddress.getLongitude() != 0) {
    		  coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);
    		  thisAddress.setLatitude(coords[1]);
        	  thisAddress.setLongitude(coords[0]);
    	  }
      }//Fine aggiunta
      
      
      
      
      if (isValid) {
        resultCount = newOrg.update(db,spa,ca,context);
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
     
        //update all contacts which are associated with this organization
        //BeanSaver.save( oldOrg, new Organization(db, newOrg.getOrgId()), oldOrg.getOrgId(),
        //		"organization", context, db, null, "Trasporto animali: Update", oldOrg.getName() );

        LookupList animaliPropri = new LookupList(db, "lookup_specie_trasportata_tipo4");
        animaliPropri.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
        animaliPropri.setMultiple(true);
        animaliPropri.setSelectSize(5);
        context.getRequest().setAttribute("AnimaliPropri", animaliPropri);

        
        
    	
		 PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numeroveicoli from organization_autoveicoli where org_id="+newOrg.getOrgId()+" and elimina is null" );
			ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
			int numeroVeicoli=0;
			if(rs_veicoli.next())
			numeroVeicoli=rs_veicoli.getInt(1);
			
			
			
		      PreparedStatement	stat_sedi	= db.prepareStatement( "select count(*) as numerosedi from organization_sediveicoli where org_id="+newOrg.getOrgId()+" and elimina is null" );
				ResultSet			rs_sedi	= stat_sedi.executeQuery();
				int numeroSedi=0;
				if(rs_sedi.next())
					numeroSedi=rs_sedi.getInt(1);
				
		
		 PreparedStatement	stat_persona	= db.prepareStatement( "select count(*) as numerpersone from organization_personale where org_id="+newOrg.getOrgId()+" and elimina is null" );
			ResultSet			rs_persona	= stat_persona.executeQuery();
			int numeropersone=0;
			if(rs_persona.next())
				numeropersone=rs_persona.getInt(1);
			
			numeropersone=(numeropersone/15)+1;
			
			numeroVeicoli=(numeroVeicoli/15)+1;
			numeroSedi=(numeroSedi/15)+1;
			
			
			context.getRequest().setAttribute("numeroPersonale", numeropersone);
			context.getRequest().setAttribute("numeroAutoveicoli", numeroVeicoli);
			context.getRequest().setAttribute("numeroSedi", numeroSedi);
        
        
        
        LookupList llist = new LookupList(db,"lookup_specie_trasportata");
  	  llist.addItem(-1, "-- SELEZIONA VOCE --");
//  	  llist.removeElementByLevel(1);
  	  llist.setMultiple(true);
  	  llist.setSelectSize(5);
  	  context.getRequest().setAttribute("SpecieA", llist);
        
    LookupList statoLab = new LookupList(db, "lookup_stato_lab");
    statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
    context.getRequest().setAttribute("statoLab", statoLab);
    
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

  public String executeCommandCambiaStato(ActionContext context) {
	    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-stato-view"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    //boolean resultCount = false;
	    int resultCount = 0;
	    boolean isValid = false;
	    
	    Organization oldOrg = null;
	    
	   
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      
	      oldOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
	      oldOrg.setModifiedBy(getUserId(context));
	      oldOrg.setEnteredBy(getUserId(context));
	      //oldOrg.setStatoImpresa(context.getRequest().getParameter("statoImpresa"));
	      isValid = this.validateObject(context, db, oldOrg);
	      String statoImpresa = context.getRequest().getParameter("stato_impresa");
	      String dataStato =  context.getRequest().getParameter("dataCambioStato");
	      if (isValid) {
	    	 
	         resultCount = oldOrg.updateStato(db, statoImpresa, dataStato, Integer.parseInt(context.getRequest().getParameter("orgId")));
	         Stato stato = new Stato();
	         stato.setStato(statoImpresa);
	         stato.setEnteredBy(getUserId(context));
	         stato.insert(db, oldOrg.getOrgId());
	        
	      }
	      if (resultCount > 0) {
	        processUpdateHook(context, oldOrg, oldOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	        String operazione = "";
	        if(statoImpresa.equalsIgnoreCase("sospeso")){
	        	operazione = "Sospensione";
	        }
	        else if(statoImpresa.equalsIgnoreCase("attivo")){
	        	operazione = "Riattivazione";
	        }
	        else if(statoImpresa.equalsIgnoreCase("cessato")){
	        	operazione = "Cessazione";
	        }
	        else if(statoImpresa.equalsIgnoreCase("revocato")){
	        	operazione = "Revoca";
	        }
	        else if(statoImpresa.equalsIgnoreCase("rinnovo")){
	        	operazione = "Rinnovo";
	        }
	        
	        //update all contacts which are associated with this organization
		      }
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Modify Account");
	 
	    return (executeCommandDetails(context));
	  }


  /**
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-delete")) {
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
            "refreshUrl", "TrasportoAnimali.do?command=Search");
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
          "refreshUrl", "TrasportoAnimali.do?command=Search");
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
      String note = context.getRequest().getParameter("note");

        // NOTE: these may have different options later
     recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
      /*if (recordUpdated) {
        
        deleteRecentItems(context, db, thisOrganization);
        
        }*/
    } catch (Exception e) {
      context.getRequest().setAttribute(
          "refreshUrl", "TrasportoAnimali.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "TrasportoAnimali.do?command=Search");
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String orgId = context.getRequest().getParameter("orgId");
      //check permission to record
     
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
          "refreshUrl", "TrasportoAnimali.do?command=Search");
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-edit")) {
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-delete")) {
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
      htmlDialog.addMessage("<form action=\"TrasportoAnimali.do?command=Trash&auto-populate=true\" method=\"post\">");
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
    if (!hasPermission(context, "trasportoanimali-trasportoanimali-edit")) {
      return ("PermissionError");
    }
    String orgid = context.getRequest().getParameter("orgId");
    context.getRequest().setAttribute("orgId", orgid);
    int tempid = Integer.parseInt(orgid);
    Connection db = null;
    Organization newOrg = null;
    try {
      db = this.getConnection(context);
      newOrg = (Organization) context.getFormBean();
      
      if (newOrg.getId() == -1) {
        newOrg = new Organization(db, tempid);
     
      } else {
        newOrg.setTypeListToTypes(db);
        
      }
     
     
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute("systemStatus", systemStatus);
     
      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("statoLab", statoLab);
      
    
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);
      
      LookupList animaliPropri = new LookupList(db, "lookup_specie_trasportata_tipo4");
      animaliPropri.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      animaliPropri.setMultiple(true);
      animaliPropri.setSelectSize(5);
      context.getRequest().setAttribute("AnimaliPropri", animaliPropri);

      LookupList CategoriaTrasportata = new LookupList(db, "lookup_categoria_trasportata");
      CategoriaTrasportata.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      CategoriaTrasportata.setMultiple(true);
      CategoriaTrasportata.setSelectSize(5);
      context.getRequest().setAttribute("CategoriaTrasportata", CategoriaTrasportata);
      
        
      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
	  llist.addItem(-1, "-- SELEZIONA VOCE --");
//	  llist.removeElementByLevel(1);
	  llist.setMultiple(true);
	  llist.setSelectSize(5);
	  context.getRequest().setAttribute("SpecieA", llist);
      
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
      context.getRequest().setAttribute("cod1", codice1);
      }else if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("cod2", codice2);
      }else if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("cod3", codice3);
      }else if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("cod4", codice4);
      }else if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("cod5", codice5);
      }else if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("cod6", codice6);
      }else if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("cod7", codice7);
      }else if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("cod8", codice8);
      }else if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("cod9", codice9);
      }else if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("cod10", codice10);
      }     
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);


      
      //inserito da Antonio
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
     
      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      
      context.getRequest().setAttribute("systemStatus", systemStatus);

      UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
      context.getRequest().setAttribute("UserList", userList);
      
      
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
      
      Iterator it = newOrg.getAddressList().iterator();
   	  while(it.hasNext())
   	  {
   		  org.aspcfs.modules.trasportoanimali.base.OrganizationAddress add=(org.aspcfs.modules.trasportoanimali.base.OrganizationAddress)it.next();
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
    	String retPage = "SystemError";
        String tipo_richiesta = newOrg.getDunsType();
        tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
        
        retPage = "Modify_" + tipo_richiesta + "OK";
        return ( retPage );
      //return ("ModifyOK");
    }
  }

    /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandFolderList(ActionContext context) {
    if (!(hasPermission(context, "trasportoanimali-trasportoanimali-folders-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    Organization thisOrganization = null;
    try {
      String orgId = context.getRequest().getParameter("orgId");
      db = this.getConnection(context);
      //check permission to record
    
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


  /**
   *  Description of the Method
   *
   * @param  context        Description of the Parameter
   * @param  siteId         Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  private UserList filterOwnerListForSite(ActionContext context, int siteId) throws Exception {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    //this is how we get the multiple-level heirarchy...recursive function.
    User thisRec = thisUser.getUserRecord();
    UserList shortChildList = thisRec.getShortChildList();
    UserList userList = thisRec.getFullChildList(
        shortChildList, new UserList());
    userList.setMyId(getUserId(context));
    userList.setMyValue(thisUser.getContact().getNameLastFirst());
    userList.setIncludeMe(true);
    userList.setExcludeDisabledIfUnselected(true);
    userList.setExcludeExpiredIfUnselected(true);

    // filter possible owners for accounts with site ids.

    // An account WITH a site id can be owned by a user with the same
    // site id or by one who has access to all sites
    // (i.e., siteId = -1 or site_id IS NULL)
    if (siteId != -1) {
      Iterator itr = userList.iterator();
      while (itr.hasNext()) {
        User tmpUser = (User) itr.next();
        if (tmpUser.getSiteId() == -1) {
          continue;
        }
        if (siteId != tmpUser.getSiteId()) {
          itr.remove();
        }
      }
    }

    // An account WITHOUT a site id can ONLY be owned by a user with access
    // to all sites
    // (i.e., siteId = -1 or site_id IS NULL)
    if (siteId == -1) {
      Iterator itr = userList.iterator();
      while (itr.hasNext()) {
        User tmpUser = (User) itr.next();
        if (siteId != tmpUser.getSiteId()) {
          itr.remove();
        }
      }
    }
    return userList;
  }
 

  public String executeCommandStates(ActionContext context) {
    String country = context.getRequest().getParameter("country");
    String form = context.getRequest().getParameter("form");
    String obj = context.getRequest().getParameter("obj");
    String stateObj = context.getRequest().getParameter("stateObj");
    String defaultValue = context.getRequest().getParameter("selected");
    SystemStatus systemStatus = this.getSystemStatus(context);
    StateSelect stateSelect = new StateSelect(systemStatus, country);
    context.getRequest().setAttribute("stateSelect", stateSelect.getHtmlSelectObj(country));
    context.getRequest().setAttribute("form", form);
    context.getRequest().setAttribute("obj", obj);
    context.getRequest().setAttribute("stateObj", stateObj);
    if (defaultValue != null) {
      context.getRequest().setAttribute("selected", defaultValue);
    }
    return "StatesOK";
  }
  
  public String executeCommandPopUpDownload(ActionContext context) {
	    
	    return "PopUpDownload";
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
			
			ResultSet res = stat.executeQuery();
			if( res.next() )
			{
				ret = new String[2];
				ret[0] = res.getString( "x" );
				ret[1] = res.getString( "y" );
				
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
           
  } */
  
  
  public String executeCommandElenco(ActionContext context) throws SQLException
  {
	if (!(hasPermission(context, "trasportoanimali-estrazione-view")))
	{
		return ("PermissionError");
	}

    Connection		db		= null;
    RowSetDynaClass	rsdc	= null;
    String			fileName=null;
    
    try
	{
		db = getConnection(context);
		
		int siteId=this.getUserSiteId(context);
	    
		String aslName="";
	    
		//Se siteId!=-1 allora e' una ASL
	    if (siteId!=-1)
	    	aslName=this.resolveAsl(db, siteId);
		
	    		
		PreparedStatement	stat = null;
		
		stat= db.prepareStatement( "select asl.description as asl, o.name as impresa, auto.targa, auto.descrizione, o.stato_impresa, "+
				"o.account_number as numero_registrazione, o.duns_type as tipo_trasporto, o.partita_iva as partita_iva, "+
				"o.codice_fiscale as codice_fiscale, o.codice_fiscale_rappresentante, "+
				"o.nome_rappresentante ||' '|| cognome_rappresentante as legale_rappresentante, ct.namefirst ||' '||ct.namelast as inserito_da, "+
				"oa5.city as comune, oa5.addrline1 as indirizzo, oa5.state as provincia, "+ 
				"o.entered as inserito_il "+
				"from organization o "+
				"left join organization_autoveicoli auto on (o.org_id = auto.org_id and auto.elimina is null) "+
				"left join organization_address oa5 on (o.org_id = oa5.org_id and oa5.address_type = 5) "+
				"left join contact ct on (o.enteredby = ct.user_id) "+
				"left join lookup_site_id asl on (o.site_id = asl.code) "+
				"where o.tipologia = 9 and o.trashed_date is null and asl.code > 0 "+
				"order by o.site_id, o.duns_type, o.org_id" );
				
			
			fileName="Trasportatori_" +System.currentTimeMillis()+ ".xls";
	
		
		
		ResultSet			rs		= stat.executeQuery();
							rsdc	= new RowSetDynaClass(rs);
				
		HttpServletResponse res = context.getResponse();
	 	res.setContentType( "application/xls" );
	 	//res.setHeader( "Content-Disposition","attachment; filename=\"osa_" + anno + ".xls\";" ); 
	 	
	 	res.setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 

	 	ServletOutputStream sout = res.getOutputStream();
	 	sout.write( "<table border=1>".getBytes() );
	 		 		 	
	 	List<DynaBean> l = rsdc.getRows();
	 	
	 	//stampa i nomi delle colonne
	 	dynamicHeader(rs, sout);
	 	
	 	for( int i = 0; i < l.size(); i++ )
	 	{
	 		//Stampa ogni riga sul file
	 		this.dynamicRow(l.get(i), rs, sout);
	 	}
	 	sout.write( "</table>".getBytes() );
	 	sout.flush();
		
	}
	catch ( Exception e )
	{
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
    }
	finally
	{
		this.freeConnection(context, db);
		System.gc();
	}
	
	return "-none-";
  }
  

	
	public String executeCommandSaveData(ActionContext context) {
		
	if (!(hasPermission(context, "trasportoanimali-trasportoanimali-edit"))) {
		return ("PermissionError");
	}

	int resultCount = -1;
	Connection db = null;
	 Organization newOrg = null;
	try {
		db = this.getConnection(context);
		String dataStato =  context.getRequest().getParameter("dataCambioStato");	
		newOrg = new Organization(
				db, Integer.parseInt(context.getRequest().getParameter("orgId")));
		Organization oldTicket = new Organization(db, newOrg.getOrgId());
		//check permission to recordnewOrg
		if (!isRecordAccessPermitted(context, db,  newOrg.getOrgId())) {
			return ("PermissionError");
		}
		newOrg.setModifiedBy(getUserId(context));
		//resultCount = newOrg.cambiaStato(db, dataStato);
		if (resultCount == -1) {
			return (executeCommandDetails(context));
		} else if (resultCount == 1) {
			//newOrg.queryRecord(db, newOrg.getOrgId());
			this.processUpdateHook(context, oldTicket, newOrg);	
			return executeCommandDetails(context);
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
  
  
  private String resolveAsl (Connection conn, int siteId) throws SQLException {
	  
	   
	  PreparedStatement stat =null;
	  
	  stat= conn.prepareStatement("select description from lookup_site_id where code= ? " );
	  stat.setInt(1,siteId);
	  	  
	  ResultSet			rs		= stat.executeQuery();
	  	
	  String aslName="";
	  
	  if (rs.next()) {
		  aslName = rs.getString(1);
	  }
	  
	  
	  return aslName;
	   
  }

  /* Questo metodo serve a stampare, sulla prima riga del file 
   * XLS, i nomi degli attrbuti della tabella di riferimento.*/
  private void dynamicHeader (ResultSet rs , ServletOutputStream sout) throws IOException, SQLException {
	  
	  //Lo String Buffer
	  StringBuffer sb = new StringBuffer(); 
	  
	  //Serve ad ottenere i meta-dati del ResultSet
	  ResultSetMetaData rsmd=rs.getMetaData();
	  
	  //Il numero di attributi
	  int columnNumber=rsmd.getColumnCount();
	  
	  sb.append(squote);
		 
	  for (int i=1;i<=columnNumber;i++) {
		sb.append(initialBold);
		sb.append(blueFont);
		sb.append( rsmd.getColumnName(i) );
		sb.append(endFont);
		sb.append(endBold);
		if( i < columnNumber )
		{
			sb.append( dquote );
		}			
	 }
	  		
		sb.append( equote );
		sb.append( "\r\n" );
		
		sout.write( sb.toString().getBytes() );
	  
  }
  
  /* Questo metodo serve a stampare ogni tupla sul file*/
  private void dynamicRow (DynaBean dynaBean, ResultSet rs, ServletOutputStream sout) throws IOException, SQLException {
	  
	  //Lo String Buffer
	  StringBuffer sb = new StringBuffer(); 
	  
	  //Serve ad ottenere i meta-dati del ResultSet
	  ResultSetMetaData rsmd=rs.getMetaData();
	  
	  //Il numero di attributi
	  int columnNumber=rsmd.getColumnCount();
	  	  
	  sb.append(squote);
		 
	  for (int i=1;i<=columnNumber;i++) {
			
		sb.append( nullCheck( dynaBean, rsmd.getColumnName(i) ) );
		if( i < columnNumber )
		{
			sb.append( dquote );
		}
				
		}
	  		
		sb.append( equote );
		sb.append( "\r\n" );
		
		sout.write( sb.toString().getBytes() );
	   
  }

  private Object nullCheck( DynaBean dynaBean, String string )
  {
  	return (dynaBean.get( string ) == null) ? ("") : (dynaBean.get( string ));
  }
  
  public void setFieldIspezioniRendicontazione(AcroFields form, double anno, Connection db) {
	  
	  HashMap<TipoAnimaliRendicontazione, HashMap<Integer, Integer>> hashAnimaliIspezionati = new HashMap<TipoAnimaliRendicontazione, HashMap<Integer,Integer>> ();
	  int somma_specie_ispezioni = 0;
	  ResultSet rs_ispezioni = null;
	  Organization org = new Organization();
	  
	  try {
		rs_ispezioni = org.queryRecord_rendicontazione_ispezioni(db,anno);
	
		  
		  /*Initialize tipo ispezione*/
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  hashAnimaliIspezionati.put(t, new HashMap<Integer, Integer>());
			  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  hashAnimaliIspezionati.get(t).put(tir.getIndiceTipoIspezione(), 0);
			  }
		  }
		  
		  while(rs_ispezioni.next()){
			  if(rs_ispezioni.getString("tipo_animali") != null && !rs_ispezioni.getString("tipo_animali").equals("") ) {
				  hashAnimaliIspezionati.get(TipoAnimaliRendicontazione.valueOf(rs_ispezioni.getString("tipo_animali"))).put(rs_ispezioni.getInt("ispezione"),rs_ispezioni.getInt("somma_animali_specie"));  
			  }
			  
		  }
		  
		  
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  
			  /*Gestione numero ispezioni*/
			  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  somma_specie_ispezioni += hashAnimaliIspezionati.get(t).get(tir.getIndiceTipoIspezione());
				  form.setField(t+""+tir.getIndiceTipoIspezione(), ""+hashAnimaliIspezionati.get(t).get(tir.getIndiceTipoIspezione()));
				  if(tir.getIndiceTipoIspezione() != 75){
					  form.setField("SOMMA"+t+"_ISPEZIONE", somma_specie_ispezioni+"");

				  }		 
			  }
			 
			  somma_specie_ispezioni = 0;
		  }
		  rs_ispezioni.close();
		  
	  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  
  }
  
public void setFieldViolazioniRendicontazione(AcroFields form, double anno, Connection db) {
	  
	  HashMap<TipoAnimaliRendicontazione, HashMap<Integer, Integer>> hashAnimaliIspezionati = new HashMap<TipoAnimaliRendicontazione, HashMap<Integer,Integer>> ();
	  int somma_specie_violazioni = 0;
	  ResultSet rs_violazioni = null;
	  Organization org = new Organization();
	  
	  try {
		  rs_violazioni = org.queryRecord_rendicontazione_violazioni(db,anno);
	
		  /*Initialize tipo ispezione*/
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  hashAnimaliIspezionati.put(t, new HashMap<Integer, Integer>());
			  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  hashAnimaliIspezionati.get(t).put(tir.getIndiceTipoIspezione(), 0);
			  }
		  }
		  
		  while(rs_violazioni.next()){
			  if(rs_violazioni.getString("tipo_animali") != null && !rs_violazioni.getString("tipo_animali").equals("") ) {
				  hashAnimaliIspezionati.get(TipoAnimaliRendicontazione.valueOf(rs_violazioni.getString("tipo_animali"))).put(rs_violazioni.getInt("ispezione"),rs_violazioni.getInt("somma_animali"));  
			  }
			  
		  }
		  
		  for (TipoAnimaliRendicontazione t : TipoAnimaliRendicontazione.values()){
			  
			  /*Gestione numero ispezioni*/
			  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  somma_specie_violazioni += hashAnimaliIspezionati.get(t).get(tir.getIndiceTipoIspezione());
				  form.setField(t+""+tir.getIndiceTipoIspezione()+"_BIS", ""+hashAnimaliIspezionati.get(t).get(tir.getIndiceTipoIspezione()));
				  if(tir.getIndiceTipoIspezione() != 75){
					  form.setField("SOMMA"+t+"_VIOLAZIONE_BIS", somma_specie_violazioni+"");

				  }		 
			  }
			 
			  somma_specie_violazioni = 0;
		  }
		  rs_violazioni.close();
		  
	  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  
  }
  
public void setFieldMezziRendicontazione(AcroFields form, double anno, Connection db) {
	  
	  HashMap<Integer, Integer> hashMezzi = new HashMap<Integer,Integer> ();
	  int somma_mezzi_ispezioni = 0;
	  ResultSet rs = null;
	  Organization org = new Organization();
	  
	  try {
		rs = org.queryRecord_rendicontazione_mezzi(db, anno);
	
		  
		  /*Initialize tipo ispezione*/
		 for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  hashMezzi.put(tir.getIndiceTipoIspezione(), 0);
		 }
		 
		  
		  while(rs.next()){
			  hashMezzi.put(rs.getInt("ispezione"),rs.getInt("tot_mezzi_ispezionati"));  
		  }
		  
		  /*Gestione numero ispezioni*/
		  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  somma_mezzi_ispezioni += hashMezzi.get(tir.getIndiceTipoIspezione());
				  form.setField(""+tir.getIndiceTipoIspezione(), ""+hashMezzi.get(tir.getIndiceTipoIspezione()));
				  if(tir.getIndiceTipoIspezione() != 75){
					  form.setField("SOMMAMEZZI", somma_mezzi_ispezioni+"");
				  }		 
		  }
			 
			 
		  
		  rs.close();
		  
	  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  
  }
  
public void setFieldMezziViolazioneRendicontazione(AcroFields form, double anno, Connection db) {
	  
	  HashMap<Integer, Integer> hashMezzi = new HashMap<Integer,Integer> ();
	  int somma_mezzi_violazioni = 0;
	  ResultSet rs = null;
	  Organization org = new Organization();
	  
	  try {
		rs = org.queryRecord_rendicontazione_mezzi_violazioni(db, anno);
	
		  
		  /*Initialize tipo ispezione*/
		 for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
				  hashMezzi.put(tir.getIndiceTipoIspezione(), 0);
		 }
		 
		  
		  while(rs.next()){
			  hashMezzi.put(rs.getInt("ispezione"),rs.getInt("tot_mezzi_ispezionati"));  
		  }
		  
		  /*Gestione numero ispezioni*/
		  for (TipoIspezioniRendicontazione tir : TipoIspezioniRendicontazione.values()){
			  somma_mezzi_violazioni += hashMezzi.get(tir.getIndiceTipoIspezione());
				  form.setField(""+tir.getIndiceTipoIspezione()+"_BIS", ""+hashMezzi.get(tir.getIndiceTipoIspezione()));
				  if(tir.getIndiceTipoIspezione() != 75){
					  form.setField("SOMMAMEZZI_BIS", somma_mezzi_violazioni+"");
				  }		 
		  }
			 
		  rs.close();
		  
	  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  
}

  
  
  

}

