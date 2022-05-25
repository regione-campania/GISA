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
package org.aspcfs.modules.operatorifuoriregione.actions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.RowSetDynaClass;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl;
import org.aspcfs.modules.operatorifuoriregione.base.Organization;
import org.aspcfs.modules.operatorifuoriregione.base.BOperatori;
import org.aspcfs.modules.operatorifuoriregione.base.OrganizationList;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
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



import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.troubletickets.base.TicketReport;
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
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;

import com.darkhorseventures.framework.actions.ActionContext;
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

  /**
   *  Default: not used
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {

    return executeCommandDashboard(context);
  }

  public String executeCommandDefaultASL( ActionContext context )
  {
		String ret = null;
		
		String tipoD = (String) context.getRequest().getParameter("tipoD");
		if(("Distributori").equals(tipoD)){
			ret="OperatoriFuoriRegioneTOK";
		}else{
			ret="OperatoriFuoriRegioneOK";
		}
		
		if (!hasPermission(context, "operatoriregione-operatoriregione-add"))
		{
			ret = "PermissionError";
		}
		
		return ret;
	}
  
  public String executeCommandDettaglio( ActionContext context )
	{
		if (!hasPermission(context, "operatoriregione-operatoriregione-view"))
		{
			return "PermissionError";
		}
		
		String ret = "DettaglioOK";
		Connection db = null;
		
		String org_id = context.getParameter( "org_id" );
		
		try 
		{
			db = getConnection( context );
			context.getRequest().setAttribute( "dettaglio", BOperatori.load( org_id, db) );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection( context, db );
		}
		
		return ret;
	}
	
  
  public String executeCommandCerca( ActionContext context )
	{
	  String ret = null;
	  
	  String tipoD = (String) context.getRequest().getParameter("tipoD");
	  
	  if(("Distributori").equals(tipoD)){
			ret="CercaTOK";
		}else{
			ret="CercaOK";
		}
		
		
		if (!hasPermission(context, "operatoriregione-operatoriregione-add"))
		{
			return "PermissionError";
		}
		String targa = "";
		String name = "";
		if(("Distributori").equals(tipoD)){
			name = context.getParameter( "name" );
		}else{
			targa	= context.getParameter( "targa" );
		}	
		Connection db = null;
		
		try 
		{
			db = getConnection( context );
			if(("Distributori").equals(tipoD)){
				context.getRequest().setAttribute( "risultati", BOperatori.loadT( name, db ) );
			}else{
				
			context.getRequest().setAttribute( "risultati", BOperatori.load( targa, db ) );}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection( context, db );
		}
		
		return ret;
	}
  
  

  /**
   *  Search: Displays the Account search form
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandSearchForm(ActionContext context) {
    if (!(hasPermission(context, "operatoriregione-operatoriregione-view"))) {
      return ("PermissionError");
    }

    //Bypass search form for portal users
    if (isPortalUser(context)) {
      return (executeCommandSearch(context));
    }
    context.getSession().setAttribute("tipoD", context.getRequest().getParameter("tipoD"));

    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
     
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteList", siteList);
      
     
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
    if(context.getSession().getAttribute("tipoD")!=null){
    		if(context.getSession().getAttribute("tipoD").equals("Distributori")){
    	
    return ("SearchOK");
    		}
    }else{
    	
    	 return ("SearchTOK");
    	 
    }
	return "SearchOK";
  }


  /**
   *  Add: Displays the form used for adding a new Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "operatoriregione-operatoriregione-add")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList Site2 = new LookupList(db, "lookup_site_id");
      Site2.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("Site_2", Site2);
      
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      String orgId = context.getRequest().getParameter("org_id");
      
      if(orgId!=null){  
    	  org.aspcfs.modules.accounts.base.Organization newOrgImpresa =   new org.aspcfs.modules.accounts.base.Organization(db, Integer.parseInt(orgId));
    	  context.getRequest().setAttribute("OrgDetailsImpresa", newOrgImpresa);
    	  Iterator iaddress = newOrgImpresa.getAddressList().iterator();
    	  int i = 0;
    	  int locali=0;
    	  if (iaddress.hasNext()) {
    	    while (iaddress.hasNext()) {
    	      org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress)iaddress.next();
    	      if(thisAddress.getType() == 1 ){
    	    	  context.getRequest().setAttribute("indirizzoImpresa", thisAddress);
    	      }}}
    	  context.getRequest().setAttribute("fuori_regione", "false");
    	  
      }
    	  Organization newOrg = (Organization) context.getFormBean();
    	  ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
          StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
          stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
          context.getRequest().setAttribute("StateSelect", stateSelect);
          
      
      newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
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

    if("Distributori".equals(context.getRequest().getParameter("tipoD"))){
    	return ("AddTOK");
        }
    else if("Operatori".equals(context.getRequest().getParameter("tipoD"))){
    	if ("true".equals(context.getRequest().getParameter("popUp"))){
    		context.getRequest().setAttribute("popUp", "true");
    		return ("AddOpopupOK");
    	}
    	return ("AddOOK");
        
        }
    else{
        	
        	return getReturn(context, "Add");
        	 
        }
    
  }


  /**
   *  Details: Displays all details relating to the selected Account. The user
   *  can also goto a modify page from this form or delete the Account entirely
   *  from the database
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
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
			
	
		    
		   String orgId=multi.getParameter("orgId");
		   int org_Id=Integer.parseInt(orgId);
		   
			File myFileT = multi.getFile("file1"); 
			FileInputStream fiStream=new FileInputStream(myFileT);
			//BufferedReader input = new BufferedReader(new FileReader(myFileT));

			
			BufferedReader input = new BufferedReader(new FileReader(myFileT));
			
			String logPath = getWebContentPath(context, "logdistributori");
			
			String pathDownloadLog = LeggiFile.leggiCampiDistributori(context, db,myFileT,logPath, getUserId(context),org_Id);
			
			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insertDistributore(db,org_Id);
			
			context.getRequest().setAttribute("pathLog", pathDownloadLog);
			context.getRequest().setAttribute("orgId", orgId);
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DistributoriUploadSaveOK");
		
	}

  
  public String executeCommandInsertDistributori(ActionContext context) {
	  
		 /* if (!hasPermission(context, "distributori-distributori-upload")) {
		      return ("PermissionError");
		    }
		  */
	  context.getSession().setAttribute("orgIdDistributore",context.getRequest().getParameter("id")) ;
	  context.getSession().setAttribute("tipoD",context.getRequest().getParameter("tipoD")) ;
		  
		  return ("Upload");
		  
	  }
  

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
		return ("DistributoriUploadListOK");
		
	}
	List<String> uniquePropertyValues=null;
	int k=-1;

	Integer[] array=null;
  Connection db = null;
  public String executeCommandDetails(ActionContext context) {
    if (!hasPermission(context, "operatoriregione-operatoriregione-view")) {
      return ("PermissionError");
    }
     db = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    Organization newOrg = null;
    try {
      String temporgId = context.getRequest().getParameter("orgId");
      if (temporgId == null) {
        temporgId = (String) context.getRequest().getAttribute("orgId");
      }
      int tempid = Integer.parseInt(temporgId);
      db = this.getConnection(context);
     /* if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
        return ("PermissionError");
      }*/
      newOrg = new Organization(db, tempid);
      
      //Caricamento Diffide
      Ticket t = new Ticket();
		context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
		context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
      
      
      //check whether or not the owner is an active User
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList IstatList = new LookupList(db, "lookup_codistat");
      IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("IstatList", IstatList);

      AttoriOpfuoriasl insertedCond = new AttoriOpfuoriasl(db, tempid, 1);
      context.getRequest().setAttribute("Conducente", insertedCond);
      AttoriOpfuoriasl insertedMitt = new AttoriOpfuoriasl(db, tempid, 2);
      context.getRequest().setAttribute("Mittente", insertedMitt);
      
      
      if(newOrg.getTipoDest()!=null){
      if(newOrg.getTipoDest().equalsIgnoreCase("distributori")){

  	    RowSetDynaClass	rsdc			= null;
  	    	
  			PreparedStatement	stat	= db.prepareStatement( "select * from distributori_automatici ,lookup_tipo_distributore where alimenti_distribuiti=code and org_id="+newOrg.getId() );
  			ResultSet			rs		= stat.executeQuery();
  								rsdc	= new RowSetDynaClass(rs);
  								
  			TableFacade tf = TableFacadeFactory.createTableFacade( "15", context.getRequest() );
  			tf.setEditable(true);
  			
  			
  			PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numero from distributori_automatici where org_id="+tempid );
			ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
			int numeroDistributori=0;
			if(rs_veicoli.next())
				numeroDistributori=rs_veicoli.getInt(1);
			
			
			numeroDistributori=(numeroDistributori/15)+1;
			context.getRequest().setAttribute("numeroDistributori", numeroDistributori);
  			
  			
  		
  		 Collection<Distrubutore> coll=	newOrg.getListaDistributori();
  	
  		 
  			tf.setItems(coll );
  			tf.setColumnProperties(
  					"matricola", "data", "comune", "provincia", "indirizzo",
  					"cap", "descrizioneTipoAlimenti","note","ubicazione","elimina"
  			);
  			tf.setStateAttr("restore");
  			
  			HtmlRow row = (HtmlRow) tf.getTable().getRow();
  	        row.setUniqueProperty("matricola"); // the unique worksheet properties to identify the row
  	      
  		
  	        
  		
  			
  			tf.getTable().getRow().getColumn( "matricola" ).setTitle( "matricola" );
  			tf.getTable().getRow().getColumn( "data" ).setTitle( "data Installazione" );
  			tf.getTable().getRow().getColumn( "comune" ).setTitle( "comune" );
  			tf.getTable().getRow().getColumn( "provincia" ).setTitle( "provincia" );
  			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "indirizzo" );
  			tf.getTable().getRow().getColumn( "cap" ).setTitle( "cap" );
  			
  			tf.getTable().getRow().getColumn( "descrizioneTipoAlimenti" ).setTitle( "Alimento Distribuito" );
  			tf.getTable().getRow().getColumn( "note" ).setTitle( "note" );
  			tf.getTable().getRow().getColumn( "ubicazione" ).setTitle( "ubicazione" );
  			tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
  					
  		
  			

Iterator<Distrubutore> it=coll.iterator();

		
		array=new Integer[coll.size()];
		int i=0;
		while(it.hasNext()){
			Distrubutore dd=it.next();
			array[i]=dd.getAlimentoDistribuito();
			i++;

			
		}
	
						
  			
  			
  			HtmlColumn cg1 = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
  			cg1.getCellRenderer().setCellEditor( 
  	        		new CellEditor()
  	        		{	
  	        			public Object getValue(Object item, String property, int rowCount)
  	        			{
  	        				LookupList alimenti = null;
  							try {
  								alimenti = new LookupList(db,"lookup_tipo_distributore");
  							} catch (SQLException e) {
  								// TODO Auto-generated catch block
  								e.printStackTrace();
  							}
  	        	
  	        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
  	        				String id	= (String) (new HtmlCellEditor()).getValue(item, "alimentoDistribuito", rowCount);
  	        				String matricola	= (String) (new HtmlCellEditor()).getValue(item, "matricola", rowCount);
  	        				temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
  	        				k++;
  	        				
  	        		
  	        				
  	        				return alimenti.getHtmlSelect("alimentiDistribuiti_"+matricola, id);
  	        				
  	        			}
  	        		}
  	        
  	        	);
  			
  			
  			
  			
  			
  			
  			Limit limit = tf.getLimit();
  			if(! limit.isExported() )
  			{
  				
  				
  				
  				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("matricola");
  				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
  				//cg.setFilterable( false );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data");
  				cg.setFilterable( true );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
  				cg.setFilterable( true );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
  				cg.setFilterable( false );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
  				cg.setFilterable( true );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
  				cg.setFilterable( false );
  				
  			
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
  				cg.setFilterable( false);
  				cg.setEditable(false);
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("note");
  				cg.setFilterable( false );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("ubicazione");
  				cg.setFilterable( false );
  				
  				cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
				cg.setEditable(false);
				cg.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "matricola", rowCount);
		        				
		        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		        				Comuni comune = new Comuni();
		        				
		        				if(orgid.equals("-1") )
		        					return "Elimina";
		        				else
		        		
		        				return "<a href='DistributoriListFuoriRegione.do?orgId="+orgid+"&oggetto=distributore&id="+iddef+"'>Elimina </a>";
		        			}
		        		}
		        
		        	);
				cg.setFilterable(false);
  				
  			}
  		

  		       //tf.getWorksheet().addRow()
  			//tf.getWorksheet().addRow(new WorksheetRow)
  			
  			ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
			item7.setTooltip( "Filtra" );
			tf.getToolbar().addToolbarItem( item7 );
			
			ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
			item8.setTooltip( "Resetta Filtro" );
			tf.getToolbar().addToolbarItem( item8 );
												
			ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
			item2.setTooltip( "Salva" );
			tf.getToolbar().addToolbarItem( item2 );
			
			ToolbarItem item18 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
			item18.setTooltip( "Scorri pagina indietro" );
			tf.getToolbar().addToolbarItem( item18 );
			
			ToolbarItem item17 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
			item17.setTooltip( "Scorri pagina in avanti" );
			tf.getToolbar().addToolbarItem( item17 );
			
			String tabella = tf.render();
  			context.getRequest().setAttribute( "tabella", tabella );

  			context.getRequest().setAttribute( "tf", tf );
    	  
      }
    }
      
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
      }if(newOrg.getCodice2()!=null){
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
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      

      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);

      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      /*org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      int AuditOrgId = newOrg.getOrgId();
      audit.setOrgId(AuditOrgId);
     
     // audit.buildList(db);
      
      if( (audit.size() - 1)>=0){
      
      context.getRequest().setAttribute("Audit",audit.get( audit.size() - 1) );
      }*/
      if(newOrg.getVoltura()){
      org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
      voltura.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
      voltura.queryRecord(db, Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
      if( (voltura!=null)){
      
      context.getRequest().setAttribute("Voltura", voltura);
      }}
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
      if(newOrg.getTipoDest().equals("Distributori"))
    	  return ("DetailsTOK");
      else if (newOrg.getTipoDest().equals("Operatori"))
      	  return ("DetailsOOK");
      else
    	  return ("DetailsOK");
    } else {
      //If user is going to the detail screen
      addModuleBean(context, "View Accounts", "View Account Details");
      context.getRequest().setAttribute("OrgDetails", newOrg);
      if(newOrg.getTipoDest().equals("Distributori"))
    	  return ("DetailsTOK");
      else if (newOrg.getTipoDest().equals("Operatori"))
      	  return ("DetailsOOK");   
      else
    	  return getReturn(context, "Details");
          	 
          
     
    }
  }
//inserito carmela
  public String executeCommandModificaCatRischio(ActionContext context) {
	    if ((!hasPermission(context, "account-tipochecklist-view"))) {
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
	     /* if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
	        return ("PermissionError");
	      }*/
	      newOrg = new Organization(db, tempid);
	      //check whether or not the owner is an active User
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
  public String executeCommandDashboard(ActionContext context) {
    if (!hasPermission(context, "operatoriregione-dashboard-view")) {
      if (!hasPermission(context, "operatoriregione-operatoriregione-view")) {
        return ("PermissionError");
      }
      //Bypass dashboard and search form for portal users
      context.getSession().setAttribute("tipoD", context.getRequest().getParameter("tipoD"));
      if (isPortalUser(context)) {
        return (executeCommandSearch(context));
      }
      return (executeCommandSearchForm(context));
    }
   
    return ("DashboardOK");
  }


  /**
   *  Search Accounts
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSearch(ActionContext context) {
    if (!hasPermission(context, "operatoriregione-operatoriregione-view")) {
      return ("PermissionError");
    }

    String source = (String) context.getRequest().getParameter("source");
    String partitaIva = (String) context.getRequest().getParameter("searchPartitaIva");
    String codiceF = (String) context.getRequest().getParameter("searchCodiceFiscale");
    String stato = (String) context.getRequest().getParameter("searchCessato");
    String cognomeR = (String) context.getRequest().getParameter("searchCognomeRappresentante");
    String nomeR = (String) context.getRequest().getParameter("searchNomeRappresentante");
    String codIstat = (String) context.getRequest().getParameter("searchCodiceFiscaleCorrentista");
    String tipoD = (String) context.getRequest().getParameter("searchTipoD");
    OrganizationList organizationList = new OrganizationList();
    organizationList.setTipologia(13);
    
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
    if(!"".equals(stato) && stato!=null && !stato.equals("-1")) {
    	organizationList.setCessato(stato);
    	 }
    if(!"".equals(tipoD) && tipoD!=null && !tipoD.equals("-1")) {
    	organizationList.setTipoD(tipoD);
    	 }	 
    addModuleBean(context, "View Accounts", "Search Results");

    //Prepare pagedListInfo
    PagedListInfo searchListInfo = this.getPagedListInfo(
        context, "SearchOrgListInfo");
    searchListInfo.setLink("OperatoriFuoriRegione.do?command=Search");
    searchListInfo.setListView("all");
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
    	  if(context.getRequest().getParameter("searchcodeOrgSiteId") != null)
    		  organizationList.setOrgSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
        source = "searchForm";
      }
      //return if no criteria is selected
      if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      
      //inserito da Carmela
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      

      //Display list of accounts if user chooses not to list contacts
      if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
        if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
          this.deletePagedListInfo(context, "SearchOrgListInfo");
          searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
          searchListInfo.setLink("OperatoriFuoriRegione.do?command=Search");
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
        	if(context.getRequest().getParameter("searchcodeOrgSiteId") != null)
        		organizationList.setOrgSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
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
        	if(context.getRequest().getParameter("searchcodeOrgSiteId") != null)
          organizationList.setOrgSiteId(context.getRequest().getParameter("searchcodeOrgSiteId"));
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
        
        if (tipoD==null)
        	tipoD = organizationList.getTipoD();
        
        if(tipoD.equals("Autoveicolo")){
        	return "ListTOK";
        }else{
        return "ListOK";
        }
      } else {}
    } catch (Exception e) {
    
      //Go through the SystemError process
      context.getRequest().setAttribute("Error", e);
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-tickets-view")) {
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
        "OperatoriFuoriRegione.do?command=ViewTickets&orgId=" + passedId);
    ticList.setPagedListInfo(ticketListInfo);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      /*if (!isRecordAccessPermitted(context, db, passedId)) {
        return ("PermissionError");
      }*/
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
  
  public String executeCommandViewTamponi(ActionContext context) {
	    if (!hasPermission(context, "stabilimenti-stabilimenti-campioni-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.tamponi.base.TicketList ticList = new org.aspcfs.modules.tamponi.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "Stabilimenti.do?command=ViewTamponi&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoTampone = new LookupList(db, "lookup_tipo_tampone");
	      TipoTampone.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoTampone", TipoTampone);
	      
	      LookupList EsitoTampone = new LookupList(db, "lookup_esito_tampone");
	      EsitoTampone.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoTampone", EsitoTampone);
	      /*if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewTamponi");
	  }
  
  
  public String executeCommandViewCampioni(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-campioni-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));
	    String idControlloUfficiale = context.getRequest().getParameter("idControllo");
		String idC = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idControllo",
				idControlloUfficiale);
		context.getRequest().setAttribute("idC",
				idC);
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=ViewCampioni&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
	      /*if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewCampioni");
	  }
  
  public String executeCommandViewVigilanza(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-vigilanza-view")) {
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
	        "OperatoriFuoriRegione.do?command=ViewVigilanza&orgId=" + passedId);
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
	      
	      LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
	      TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
	      
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
	     /* if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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

  public String executeCommandViewCessazionevariazione(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-cessazionevariazione-view")) {
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
	        "OperatoriFuoriRegione.do?command=ViewCessazionevariazione&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      /*if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
  
  public String executeCommandViewSanzioni(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-sanzioni-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.sanzioni.base.TicketList ticList = new org.aspcfs.modules.sanzioni.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=ViewSanzioni&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	    /*  if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewSanzioni");
	  }
  
  
  
  public String executeCommandViewNonConformita(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-nonconformita-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.nonconformita.base.TicketList ticList = new org.aspcfs.modules.nonconformita.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));
	    String idControlloUfficiale = context.getRequest().getParameter("idControllo");
		String idC = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idControllo",
				idControlloUfficiale);
		context.getRequest().setAttribute("idC",
				idC);
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=Viewnonconformita&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	     /* if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewNonConformita");
	  }
  
  public String executeCommandViewSequestri(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-sequestri-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.sequestri.base.TicketList ticList = new org.aspcfs.modules.sequestri.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=ViewSequestri&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	     /* if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewSequestri");
	  }
  

  
  public String executeCommandViewReati(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-reati-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.reati.base.TicketList ticList = new org.aspcfs.modules.reati.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=ViewReati&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	      /*if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewReati");
	  }

  
  
  
  
  
  
  public String executeCommandViewFollowup(ActionContext context) {
	    if (!hasPermission(context, "operatoriregione-operatoriregione-followup-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    org.aspcfs.modules.followup.base.TicketList ticList = new org.aspcfs.modules.followup.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));

	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "OperatoriFuoriRegione.do?command=ViewReati&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      db = this.getConnection(context);
	      //find record permissions for portal users
	    /*  if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
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
	    return getReturn(context, "ViewFollowup");
	  }
  


  /**
   *  Insert: Inserts a new Account into the database.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandInsert(ActionContext context) throws SQLException {
    if (!hasPermission(context, "operatoriregione-operatoriregione-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    Organization insertedOrg = null;
    
    Organization newOrg = (Organization) context.getFormBean();
    Audit audit = new Audit();
    audit.setLivelloRischioFinale(-1);
    
    
    String tipoImpresa = context.getRequest().getParameter("tipo_impresa");
    if ("mobile".equalsIgnoreCase(tipoImpresa)){
    	newOrg.setFlagMobile(true);
    }
    
    
    newOrg.setFuori_regione(context.getRequest().getParameter("fuori_regione"));
 
    newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
    newOrg.setEnteredBy(getUserId(context));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setOwner(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteList", siteList);
      
      
      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));     
      
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
      
      
        newOrg.setRequestItems(context);
     
      /*if (this.getUserSiteId(context) != -1) {
        // Set the site Id of the account to be equal to the site Id of the user
        // for a new account
        if (newOrg.getId() == -1) {
          newOrg.setSiteId(this.getUserSiteId(context));
        } else {
          // Check whether the user has access to update the organization
       /*   if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
            return ("PermissionError");
          }*/
        /*}
      }*/
      if (newOrg.getFuori_regione()==false )
      {	
    	  if (! context.getRequest().getParameter("site2").equals("-1"))
    	  newOrg.setSiteId(Integer.parseInt(context.getRequest().getParameter("site2")));
      }
      else
      {
    	  /**
    	   * 	ASL FUORI REGIONE
    	   */
    	  newOrg.setSiteId(16);
      }

      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        recordInserted = newOrg.insert(db,context);
      }
      if (recordInserted) {
    	  Organization temp = new Organization(db, newOrg.getOrgId());
    	  temp.setModifiedBy( getUserId(context) );
    	  String ragione_sociale = context.getRequest().getParameter("ragione_sociale");
          String cognome = context.getRequest().getParameter("cognome");
          String nome = context.getRequest().getParameter("nome");
          String comune_1 = context.getRequest().getParameter("comune_1");
          String indirizzo_1 = context.getRequest().getParameter("indirizzo_1");
          String provincia_1 = context.getRequest().getParameter("provincia_1");
          String comune_2 = context.getRequest().getParameter("comune_2");
          String indirizzo_2 = context.getRequest().getParameter("indirizzo_2");
          String provincia_2 = context.getRequest().getParameter("provincia_2");
          String note = context.getRequest().getParameter("note");
          String luogo_nascita = context.getRequest().getParameter("luogo_nascita");
          String data_nascita = context.getRequest().getParameter("data_nascita");
          String documento = context.getRequest().getParameter("documento");
          String merce = context.getRequest().getParameter("merce");
          AttoriOpfuoriasl conducente = new AttoriOpfuoriasl();
          if(cognome!=null){
        	  conducente.setTipologia(1);
        	  conducente.setModified_by( getUserId(context) );
        	  conducente.setEntered_by( getUserId(context) );
        	  conducente.setCognome(cognome);
        	  if(nome!=null && !nome.equals(""))
        	  conducente.setNome(nome);
        	  if(data_nascita!=null && !data_nascita.equals(""))
        	  conducente.setData_nascita(data_nascita);
        	  if(luogo_nascita!=null  && !luogo_nascita.equals(""))
        	  conducente.setLuogo_nascita(luogo_nascita);
        	  if(comune_1!=null && !comune_1.equals(""))
        	  conducente.setComune(comune_1);
        	  if(indirizzo_1!=null && !indirizzo_1.equals(""))
        	  conducente.setIndirizzo(indirizzo_1);
        	  if(provincia_1!=null && !provincia_1.equals(""))
        	  conducente.setProvincia(provincia_1);
        	  if(documento!=null&& !documento.equals("") )
        	  conducente.setDocumento(documento);
        	  conducente.setOrg_id(newOrg.getOrgId());
        	  conducente.insert(db, newOrg.getOrgId(), 1);
        	  AttoriOpfuoriasl insertedCond = new AttoriOpfuoriasl(db, conducente.getOrg_id(), 1);
              context.getRequest().setAttribute("Conducente", insertedCond);
          }	
    			
    	 				
          AttoriOpfuoriasl mittente = new AttoriOpfuoriasl();
          if(ragione_sociale!=null){
        	  mittente.setTipologia(2);
        	  mittente.setModified_by( getUserId(context) );
        	  mittente.setEntered_by( getUserId(context) );
        	  mittente.setRagione_sociale(ragione_sociale);
        	  if(comune_2!=null && !comune_2.equals(""))
        		  mittente.setComune(comune_2);
            	  if(indirizzo_2!=null && !indirizzo_2.equals(""))
            		  mittente.setIndirizzo(indirizzo_2);
            	  if(provincia_2!=null && !provincia_2.equals(""))
            		  mittente.setProvincia(provincia_2);
            	  if(note!=null && !note.equals(""))
            		  mittente.setNote(note);
            	  if(merce!=null && !merce.equals(""))
            		  mittente.setMerce(merce);
            	  mittente.setOrg_id(newOrg.getOrgId());
            	  mittente.insert(db, newOrg.getOrgId(), 2);
            	  AttoriOpfuoriasl insertedMitt = new AttoriOpfuoriasl(db, mittente.getOrg_id(), 2);
                  context.getRequest().setAttribute("Mittente", insertedMitt);
          }
    	  //org.aspcfs.modules.requestor.actions.Accounts.generaCodice( db, temp );
        insertedOrg = new Organization(db, newOrg.getOrgId());
        context.getRequest().setAttribute("OrgDetails", insertedOrg);
        
        PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numero from distributori_automatici where org_id="+newOrg.getOrgId() );
		ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
		int numeroDistributori=0;
		if(rs_veicoli.next())
			numeroDistributori=rs_veicoli.getInt(1);
		
		
		numeroDistributori=(numeroDistributori/15)+1;
		context.getRequest().setAttribute("numeroDistributori", numeroDistributori);
        
        
        addRecentItem(context, newOrg);
         
        
       
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
    	  
    	  String tipologia = context.getRequest().getParameter("tipologia");
    	  String popUp = context.getRequest().getParameter("popUp");
    	  if ("Operatori".equals(tipologia) && "true".equals(popUp))
    		  return "InsertOperatoriOk";
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
 * @throws SQLException 
   */
  public String executeCommandUpdate(ActionContext context) throws SQLException {
    if (!(hasPermission(context, "operatoriregione-operatoriregione-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization newOrg = (Organization) context.getFormBean();
    Organization oldOrg = null;
    SystemStatus systemStatus = this.getSystemStatus(context);
    newOrg.setTypeList(
        context.getRequest().getParameterValues("selectedList"));
    newOrg.setModifiedBy(getUserId(context));
    newOrg.setEnteredBy(getUserId(context));
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newOrg.setIp_entered(ip);
    newOrg.setIp_modified(ip);
    try {
      db = this.getConnection(context);
      //set the name to namelastfirstmiddle if individual
      
      if(newOrg.getTipoDest()==null && ((String)context.getRequest().getParameter("tipoD")!=null))
    	  newOrg.setTipoDest((String)context.getRequest().getParameter("tipoD"));
    	    	  
      
      newOrg.setFuori_regione(context.getRequest().getParameter("fuori_regione"));
      //newOrg.setTipoDest(context.getRequest().getParameter("tipoD"));
       
 
        newOrg.setRequestItems(context);
       
      if (newOrg.getFuori_regione()==false )
      {	
    	  if (! context.getRequest().getParameter("site2").equals("-1"))
    	  newOrg.setSiteId(Integer.parseInt(context.getRequest().getParameter("site2")));
      }
      else
      {
    	  /**
    	   * 	ASL FUORI REGIONE
    	   */
    	  newOrg.setSiteId(16);
      }
      oldOrg = new Organization(db, newOrg.getOrgId());
      isValid = this.validateObject(context, db, newOrg);
      if (isValid) {
        resultCount = newOrg.update(db,context);
      //  String prova = context.getRequest().getParameter("address1state");
       // newOrg.setState(prova);
        String ragione_sociale = context.getRequest().getParameter("ragione_sociale");
        String cognome = context.getRequest().getParameter("cognome");
        String nome = context.getRequest().getParameter("nome");
        String comune_1 = context.getRequest().getParameter("comune_1");
        String indirizzo_1 = context.getRequest().getParameter("indirizzo_1");
        String provincia_1 = context.getRequest().getParameter("provincia_1");
        String comune_2 = context.getRequest().getParameter("comune_2");
        String indirizzo_2 = context.getRequest().getParameter("indirizzo_2");
        String provincia_2 = context.getRequest().getParameter("provincia_2");
        String note = context.getRequest().getParameter("note");
        String luogo_nascita = context.getRequest().getParameter("luogo_nascita");
        String data_nascita = context.getRequest().getParameter("data_nascita");
        String documento = context.getRequest().getParameter("documento");
        String merce = context.getRequest().getParameter("merce");
        AttoriOpfuoriasl conducente = new AttoriOpfuoriasl();
        if(cognome!=null){
        	conducente.setTipologia(1);
      	  conducente.setModified_by( getUserId(context) );
      	  conducente.setEntered_by( getUserId(context) );
      	  conducente.setCognome(cognome);
      	  if(nome!=null && !nome.equals(""))
      	  conducente.setNome(nome);
      	  if(data_nascita!=null && !data_nascita.equals(""))
      	  conducente.setData_nascita(data_nascita);
      	  if(luogo_nascita!=null  && !luogo_nascita.equals(""))
      	  conducente.setLuogo_nascita(luogo_nascita);
      	  if(comune_1!=null && !comune_1.equals(""))
      	  conducente.setComune(comune_1);
      	  if(indirizzo_1!=null && !indirizzo_1.equals(""))
      	  conducente.setIndirizzo(indirizzo_1);
      	  if(provincia_1!=null && !provincia_1.equals(""))
      	  conducente.setProvincia(provincia_1);
      	  if(documento!=null&& !documento.equals("") )
      	  conducente.setDocumento(documento);
      	  conducente.setOrg_id(newOrg.getOrgId());
      	  AttoriOpfuoriasl insertedCond = new AttoriOpfuoriasl(db, newOrg.getOrgId(), 1);
      	  conducente.updateAttoriopfuoriasl(db, insertedCond.getId(), newOrg.getOrgId());
      	  AttoriOpfuoriasl newCond = new AttoriOpfuoriasl(db, newOrg.getOrgId(), 1);
            context.getRequest().setAttribute("Conducente", newCond);
        }	
  			
  	 				
        AttoriOpfuoriasl mittente = new AttoriOpfuoriasl();
        if(ragione_sociale!=null){
        	mittente.setTipologia(2);
      	  mittente.setModified_by( getUserId(context) );
      	  mittente.setEntered_by( getUserId(context) );
      	  mittente.setRagione_sociale(ragione_sociale);
      	  if(comune_2!=null && !comune_2.equals(""))
      		  mittente.setComune(comune_2);
          if(indirizzo_2!=null && !indirizzo_2.equals(""))
          		  mittente.setIndirizzo(indirizzo_2);
          if(provincia_2!=null && !provincia_2.equals(""))
          		  mittente.setProvincia(provincia_2);
          if(note!=null && !note.equals(""))
          		  mittente.setNote(note);
          if(merce!=null && !merce.equals(""))
          		  mittente.setMerce(merce);
          mittente.setOrg_id(newOrg.getOrgId());
          AttoriOpfuoriasl insertedMitt = new AttoriOpfuoriasl(db, newOrg.getOrgId(), 2);
    	  mittente.updateAttoriopfuoriasl(db, insertedMitt.getId(), newOrg.getOrgId());
    	  AttoriOpfuoriasl newMitt = new AttoriOpfuoriasl(db, newOrg.getOrgId(), 2);
          context.getRequest().setAttribute("Mittente", newMitt);
        }
      }
      if (resultCount == 1) {
        processUpdateHook(context, oldOrg, newOrg);
        //if this is an individual account, populate and update the primary contact
      
        //update all contacts which are associated with this organization
        
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

  /**
   *  Update: Updates the Organization table to reflect user-entered
   *  changes/modifications to the currently selected Account
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandUpdateCatRischio(ActionContext context) {
    if (!(hasPermission(context, "account-tipochecklist-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    String org_id = context.getRequest().getParameter( "orgId" );
    String idC= context.getRequest().getParameter( "idC" );
    
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
    context.getRequest().setAttribute("idControllo", idC);
    
    addModuleBean(context, "View Accounts", "Modify Account");

    return ("UpdateCatRischioOK");
  }


  /**
   *  Delete: Deletes an Account from the Organization table
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
 * @throws SQLException 
   */
  public String executeCommandDelete(ActionContext context) throws SQLException {
    if (!hasPermission(context, "operatoriregione-operatoriregione-delete")) {
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
      /*if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
        return ("PermissionError");
      }*/
     
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
            "refreshUrl", "OperatoriFuoriRegione.do?command=Search");
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
          "refreshUrl", "Accounts.do?command=Search");
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-delete")) {
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
          "refreshUrl", "OperatoriFuoriRegione.do?command=Search");
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Accounts", "Delete Account");
    if (recordUpdated) {
      context.getRequest().setAttribute(
          "refreshUrl", "OperatoriFuoriRegione.do?command=Search");
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-delete")) {
      return ("PermissionError");
    }
    boolean recordUpdated = false;
    Organization thisOrganization = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      String orgId = context.getRequest().getParameter("orgId");
      //check permission to record
      /*if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
        return ("PermissionError");
      }*/
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
          "refreshUrl", "OperatoriFuoriRegione.do?command=Search");
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-edit")) {
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-delete")) {
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
        htmlDialog.addMessage("<form action=\"OperatoriFuoriRegione.do?command=Trash&auto-populate=true\" method=\"post\">");
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
    if (!hasPermission(context, "operatoriregione-operatoriregione-edit")) {
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
      }
   /*   if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
        return ("PermissionError");
      }*/
 
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute("systemStatus", systemStatus);
    

      LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
      TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoLocale", TipoLocale);
      
      LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
      TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("TipoStruttura", TipoStruttura);
      
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
      context.getRequest().setAttribute("cod1", codice1);
      } if(newOrg.getCodice2()!=null){
      codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
      context.getRequest().setAttribute("cod2", codice2);
      } if(newOrg.getCodice3()!=null){
      codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
      context.getRequest().setAttribute("cod3", codice3);
      } if(newOrg.getCodice4()!=null){
      codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
      context.getRequest().setAttribute("cod4", codice4);
      } if(newOrg.getCodice5()!=null){
      codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
      context.getRequest().setAttribute("cod5", codice5);
      } if(newOrg.getCodice6()!=null){
      codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
      context.getRequest().setAttribute("cod6", codice6);
      } if(newOrg.getCodice7()!=null){
      codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
      context.getRequest().setAttribute("cod7", codice7);
      } if(newOrg.getCodice8()!=null){
      codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
      context.getRequest().setAttribute("cod8", codice8);
      } if(newOrg.getCodice9()!=null){
      codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
      context.getRequest().setAttribute("cod9", codice9);
      } if(newOrg.getCodice10()!=null){
      codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
      context.getRequest().setAttribute("cod10", codice10);
      }     
      LookupList addrTypeList = systemStatus.getLookupList(db, "lookup_orgaddress_types");
      context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

      LookupList emailTypeList = systemStatus.getLookupList(db, "lookup_orgemail_types");
      context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

      //inserito da Francesco
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteList", siteList);
      


      AttoriOpfuoriasl insertedCond = new AttoriOpfuoriasl(db, tempid, 1);
      context.getRequest().setAttribute("Conducente", insertedCond);
      AttoriOpfuoriasl insertedMitt = new AttoriOpfuoriasl(db, tempid, 2);
      context.getRequest().setAttribute("Mittente", insertedMitt);
      //if this is an individual account
 

      //Make the StateSelect and CountrySelect drop down menus available in the request.
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
   
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("CountrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);

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
    	if(newOrg.getTipoDest().equals("Distributori")){
    		return ("ModifyTOK");
            
            }
    	else if(newOrg.getTipoDest().equals("Operatori")){
        	return ("ModifyOOK");
            }
    	else{
           	return ("ModifyOK");
            }
      
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


  /*Inserito da Antonio....gestisce anche gli altri due tipi
   * di report....*/
  
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
		    if(filename.equals("modelloB.xml"))
		    	fileDownload.setDisplayName("ModelloB_DIA_" + id + ".pdf");
		    else if (filename.equals("modelloC.xml"))
		    	fileDownload.setDisplayName("ModelloC_DIA_" + id + ".pdf");
		    else
		    	fileDownload.setDisplayName("DettaglioImpresa_" + id + ".pdf");
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

  
  
  
