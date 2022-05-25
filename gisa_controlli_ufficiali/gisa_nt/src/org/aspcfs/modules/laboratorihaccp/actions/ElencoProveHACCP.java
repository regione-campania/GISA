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
package org.aspcfs.modules.laboratorihaccp.actions;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.modules.laboratorihaccp.base.Organization;
import org.aspcfs.modules.laboratorihaccp.base.OrganizationList;
import org.aspcfs.modules.laboratorihaccp.base.Prova;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.laboratorihaccp.base.*;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
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
public final class ElencoProveHACCP extends CFSModule {
	
	 Logger logg = Logger.getLogger("MainLogger");

  
  
  private String validateColumn(WorksheetColumn colonna, String changedValue) {
	  	String nomeColonna=colonna.getProperty();
		String errore="";
		String changedValue1 = colonna.getChangedValue();    

			try
			{
				Integer.parseInt(changedValue1);
			
			}catch(Exception e)
			{
				colonna.setError("Tipo Dato Non Valido"); 
				errore="Inserire un numero";
			}
			if(errore.equals(""))
				colonna.removeError();
	return errore;
	

}
   
  

  /**
   *  DeleteReport: Deletes previously generated report files (HTML and CSV)
   *  from server and all related file information from the project_files table.
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDeleteReport(ActionContext context) {
    if (!(hasPermission(context, "laboratori-laboratori-reports-delete"))) {
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
          db, this.getPath(context, "account-reports"));
      String filePath1 = this.getPath(context, "account-reports") + getDatePath(
          thisItem.getEntered()) + thisItem.getFilename() + ".csv";
      java.io.File fileToDelete1 = new java.io.File(filePath1);
      if (!fileToDelete1.delete()) {
        System.err.println("FileItem-> Tried to delete file: " + filePath1);
      }
      String filePath2 = this.getPath(context, "account-reports") + getDatePath(
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


   public String executeCommandAdd(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-prove-add")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      denom.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	      
	      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	      ente.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ente", ente);
	      
	      Prova newOrg = (Prova) context.getFormBean();
	      ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	      if(newOrg == null)
	      {
	    	  newOrg = new Prova();
	      }
	      context.getRequest().setAttribute("Prova", newOrg);
	     
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
  
  
 



  public String executeCommandDetails(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-view")) {
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
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	       
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addRecentItem(context, newOrg);
	    String action = context.getRequest().getParameter("action");
	   /* if (action != null && action.equals("modify")) {
	      //If user is going to the modify form
	      addModuleBean(context, "Accounts", "Modify Account Details");
	      return ("DetailsOK");
	    } else {
	      //If user is going to the detail screen
	      addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return getReturn(context, "Details");
	    }*/
	    addModuleBean(context, "View Accounts", "View Account Details");
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      return ("DetailsOK");
	  }
  
 
  
    
  public String executeCommandInsert(ActionContext context) throws SQLException {
	    if (!hasPermission(context, "laboratori-laboratori-prove-add")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Prova insertedOrg = null;	    
	    Prova newOrg = (Prova) context.getFormBean();
	   
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    
	      try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);
	      
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	       
	      newOrg.setOrgId(context.getRequest().getParameter("orgId"));
	      newOrg.setCodiceMatrice(context.getRequest().getParameter("codiceMatrice"));
	      newOrg.setCodiceDenominazione(context.getRequest().getParameter("codiceDenominazione"));
	      newOrg.setCodiceEnte(context.getRequest().getParameter("codiceEnte"));
	      newOrg.setNorma(context.getRequest().getParameter("norma"));
	      String accreditamento = context.getRequest().getParameter("accreditata");
	      if (accreditamento.equals("true")){
	      newOrg.setAccreditata(true);
	      }else{
	    	  newOrg.setAccreditata(false);
	      }
	      newOrg.setEntered(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));
	      newOrg.setEnteredBy(user.getUserId());
	      newOrg.setModifiedBy(user.getUserId());

	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        recordInserted = newOrg.insert(db,context);
	      }
	      if (recordInserted) {
	        insertedOrg = new Prova(db, newOrg.getId());
	        context.getRequest().setAttribute("Prova", insertedOrg);
	        context.getRequest().setAttribute("orgId", insertedOrg.getOrgId());
	        addRecentItem(context, newOrg);
	        context.getRequest().setAttribute("inserito", "OK");
	        
	      }
	    } catch (Exception errorMessage) {
	      String forward = "";
	      logg.severe("Errore di forwarding nella MiddleServlet: " + forward);
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "View Accounts", "Prova Insert ok");
	    
	   return "AddOK";
	    
	  }

  
  public String executeCommandUpdate(ActionContext context) {
	    if (!(hasPermission(context, "laboratori-laboratori-edit"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    int resultCount = -1;
	    boolean isValid = false;
	    String orgId = context.getRequest().getParameter("orgId");
	    String idProva = context.getRequest().getParameter("id");
	    Prova newOrg = (Prova) context.getFormBean();
	    Prova oldOrg = null;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    newOrg.setModifiedBy(getUserId(context));
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newOrg.setOrgId(Integer.parseInt(orgId));
	    newOrg.setId(Integer.parseInt(idProva));
	    try {
	      db = this.getConnection(context);
	      //set the name to namelastfirstmiddle if individual
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteList", siteList);

	    
	      oldOrg = new Prova(db, newOrg.getId());		
	      //Inserisco la riga precedente nella tabella dello storico verificando che non sia gia' presente
	      int selectedRow = this.selectProva(db, oldOrg.getId());
	      
	      if(selectedRow == 0)	{
	    	  oldOrg.insertStorico(db);
	      }
	      
	      //Stessa cosa per il laboratorio che inserisco nella tabella dello storico
	      //Inserisco la riga precedente nella tabella dello storico verificando che non sia gia' presente
	      Organization org = new Organization(db,oldOrg.getOrgId());
	      int selectedRowLab = org.selectLab(db, oldOrg.getOrgId());	
	      
	      if(selectedRowLab == 0)	{
	    	  org.insertStorico(db,context);
	      }
	      
	      newOrg.setModified(new Timestamp (new Date(System.currentTimeMillis()).getTime()));	      
	      newOrg.setModifiedBy(user.getUserId());
	      newOrg.setCodiceDenominazione(Integer.parseInt(context.getRequest().getParameter("codiceDenominazione")));
	      newOrg.setCodiceMatrice(Integer.parseInt(context.getRequest().getParameter("codiceMatrice")));
	      newOrg.setAccreditata(Boolean.parseBoolean(context.getRequest().getParameter("accreditata")));
	      newOrg.setDecreto(context.getRequest().getParameter("decreto"));
	     
	      int result = this.compare_and_updateStorico(oldOrg,newOrg, db); 
	      
	      isValid = this.validateObject(context, db, newOrg);
	      if (isValid) {
	        resultCount = newOrg.update(db);
	        context.getSession().setAttribute("ListaCampi", newOrg.getCampiModificati());
	        
	      }
	      if (resultCount == 1) {
	        processUpdateHook(context, oldOrg, newOrg);
	        //if this is an individual account, populate and update the primary contact
	        
	        //update all contacts which are associated with this organization
	      
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
	    	context.getRequest().setAttribute("inserito", "true");
	    	context.getRequest().setAttribute("orgId", newOrg.getOrgId());
	    	return executeCommandDetails(context);
	    } else {
	      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	      return ("UserError");
	    }
	  }
  
  private int compare_and_updateStorico(Prova oldOrg, Prova newOrg, Connection db) throws SQLException {
	
	  int idMatrice = oldOrg.getCodiceMatrice();
	  int idProva = oldOrg.getCodiceDenominazione();
	  int idEnte = oldOrg.getCodiceEnte();
	  String norma = oldOrg.getNorma();
	  String decreto = oldOrg.getDecreto();
	  boolean accreditata = oldOrg.getAccreditata();
	  PreparedStatement pst = null;
	  StringBuffer sql = new StringBuffer();
	  LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	  LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	  LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	  //Vector <Object> campi =  new Vector<Object>();
	  HashMap<Integer, Object> campi =  new HashMap <Integer, Object>();
	  HashMap<Integer, Object> lista_campi_aggiornata =  new HashMap <Integer, Object>();
	  int numCampiAggiornati =0;
	    //Richiamo l'insert
		  sql.append("INSERT INTO laboratorihaccp_storico_elenco_prove ( ");
		  if(idMatrice != newOrg.getCodiceMatrice()){
			  ++numCampiAggiornati;
			  sql.append("codice_matrice, ");
			  campi.put(0,newOrg.getCodiceMatrice());
		  }
		  if(idProva != newOrg.getCodiceDenominazione()){
			  ++numCampiAggiornati;
			  sql.append("codice_denominazione, ");
			  campi.put(1,newOrg.getCodiceDenominazione());
			  
		  }
		  if(idEnte != newOrg.getCodiceEnte()){
			  ++numCampiAggiornati;
			  sql.append("codice_ente, ");
			  campi.put(2,newOrg.getCodiceEnte());
		  }
		  if(norma !=null && !norma.equals(newOrg.getNorma())){
			  ++numCampiAggiornati;
			  sql.append("norma, ");
			  campi.put(3,newOrg.getNorma());
		  }
		  if(decreto!= null && !decreto.equals(newOrg.getDecreto())){
			  ++numCampiAggiornati;
			  sql.append("decreto, ");
			  campi.put(4,newOrg.getDecreto());
		  }
		  if(accreditata != newOrg.getAccreditata()){
			  ++numCampiAggiornati;
			  sql.append("accreditata, ");
			  campi.put(5,newOrg.getAccreditata());
		  }
		  
		  int i = 0,k = 0;
		  if(numCampiAggiornati != 0){
		      sql.append("modified, modified_by, id_prova, org_id, prova_originaria ) VALUES (?, ?, ?, ? , ? " );
		      for(int j=0; j<campi.size(); j++){
		    	  sql.append(", ? ");
		      }
		      sql.append(" )");
			  pst = db.prepareStatement(sql.toString());
			  logg.info("stat insert prima: "+pst.toString());
			  
			  while(k<6){
				  if(campi.containsKey(k)){
					  pst.setObject(++i, campi.get(k));
					  lista_campi_aggiornata.put(k, campi.get(k));
				  }
				  k++;
			  }
			  
			  /*Iterator iterator = campi.entrySet().iterator();
		      while(iterator. hasNext()){        
		      }*/
		       
			  newOrg.setCampiModificati(lista_campi_aggiornata);
			  pst.setTimestamp(++i, newOrg.getModified());  
			  pst.setInt(++i, newOrg.getModifiedBy());
			  pst.setInt(++i, newOrg.getId());
			  pst.setInt(++i,newOrg.getOrgId());
			  pst.setBoolean(++i, false);
			  
			  logg.info("stat insert dopo: "+pst.toString());
			  pst.execute();
			 
		  }
	  //}
	 
	  
	  return numCampiAggiornati;
  }

 
   private int selectProva(Connection db, int id_prova) {
	// TODO Auto-generated method stub
	   PreparedStatement count = null;
	   StringBuffer sql = new StringBuffer();
	   sql.append("SELECT COUNT(*) as count_prova FROM laboratorihaccp_storico_elenco_prove WHERE id_prova = ? and prova_originaria = ? ");
	   ResultSet rs = null;
	   int result = 0;
	   try {
		   count = db.prepareStatement(sql.toString());
		   count.setInt(1, id_prova);
		   count.setBoolean(2, true);
		   rs = count.executeQuery();
		   if(rs.next()){
				result = rs.getInt("count_prova");
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	return result;
   }

   private void updateStoricoProva(Connection db) {
	   
	   
   }
   

public String executeCommandDeleteProve(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-prove-delete")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    boolean recordDeleted = false;
	    Prova thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Prova(
	          db, Integer.parseInt(context.getRequest().getParameter("id")));
	             
	         recordDeleted = thisOrganization.delete(db);
	        
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	    addModuleBean(context, "Accounts", "Delete Account");
	   
	      if (recordDeleted) {
	        context.getRequest().setAttribute("orgId", thisOrganization.getOrgId());
	        return ("DeleteOK");
	      } else {
	        processErrors(context, thisOrganization.getErrors());
	        context.getRequest().setAttribute("orgId", thisOrganization.getOrgId());
	        return ("DeleteOK");
	      }
	    } 

  
    
  public String executeCommandConfirmDelete(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-prove-edit")) {
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
	      
	      
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("button.delete"), "javascript:window.location.href='LaboratoriHACCP.do?command=Trash&action=delete&orgId=" + thisOrg.getOrgId() + "&forceDelete=true" + "'");
	      
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

  
  /*public String executeCommandModify(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-edit")) {
	      return ("PermissionError");
	    }
	    
	    String orgid = context.getRequest().getParameter("orgId");
	    String id = context.getRequest().getParameter("id");
	    
	    context.getRequest().setAttribute("orgId", orgid);
	    Connection db = null;
	    int tempid = Integer.parseInt(orgid);
	    Organization  newOrg = null;
		try 
		{
			db = this.getConnection(context);
			newOrg = new Organization(db,tempid);
			if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
		        return ("PermissionError");
		    }
			SystemStatus systemStatus = this.getSystemStatus(context);
		    context.getRequest().setAttribute("systemStatus", systemStatus);
		    //if this is an individual account
		    LookupList siteList = new LookupList(db, "lookup_site_id");
		      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteList", siteList);
		      
		      LookupList stageList = new LookupList(db, "lookup_account_stage");
		      stageList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("StageList", stageList);
		      
		      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
		      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("MatriciHaccp", matrici);
		      
		      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
		      denom.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("DenominazioniHaccp", denom);
		      
		      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
		      ente.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Ente", ente);
		      
		    CountrySelect countrySelect = new CountrySelect(systemStatus);
		    context.getRequest().setAttribute("CountrySelect", countrySelect);
		    context.getRequest().setAttribute("systemStatus", systemStatus);
			
				
		}  catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      e.printStackTrace();
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    addModuleBean(context, "View Accounts", "Account Modify");
		    context.getRequest().setAttribute("OrgDetails", newOrg);
		    context.getRequest().setAttribute("idProva", id);
		    if (context.getRequest().getParameter("popup") != null) {
		      return ("PopupModifyOK");
		    } else {
		      return ("ModifyOK");
		    }
	    
	  }*/
  
  public String executeCommandModify(ActionContext context) {
	    if (!hasPermission(context, "laboratori-laboratori-prove-edit")) {
	      return ("PermissionError");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Exception errorMessage = null;
	    int recordUpdated = 0;
	    Prova thisOrganization = null;
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      thisOrganization = new Prova(
	          db, Integer.parseInt(context.getRequest().getParameter("id")));
	             
	     // recordUpdated = thisOrganization.update(db);
	      LookupList matrici = new LookupList(db, "lookup_matrici_labhaccp");
	      matrici.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("MatriciHaccp", matrici);
	      
	      LookupList denom = new LookupList(db, "lookup_denominazioni_labhaccp");
	      denom.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("DenominazioniHaccp", denom);
	      
	      LookupList ente = new LookupList(db, "lookup_ente_labhaccp");
	      ente.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ente", ente); 
	      
	      
	        
	    } catch (Exception e) {
	      errorMessage = e;
	    } finally {
	      this.freeConnection(context, db);
	    }
	   	
	    addModuleBean(context, "View Accounts", "Account Modify");
	    context.getRequest().setAttribute("Prova", thisOrganization);
	    context.getRequest().setAttribute("orgId", thisOrganization.getOrgId());
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
	
  
}

  
  
  
