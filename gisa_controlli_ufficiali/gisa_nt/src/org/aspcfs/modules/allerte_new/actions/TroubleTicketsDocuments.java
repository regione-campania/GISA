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
package org.aspcfs.modules.allerte_new.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.actions.ProjectManagementFileFolders;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.allerte_new.base.AslCoinvolte;
import org.aspcfs.modules.allerte_new.base.Ticket;
import org.aspcfs.modules.allerte_new.base.TicketLog;
import org.aspcfs.utils.web.LookupList;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: TroubleTicketsDocuments.java,v 1.11 2004/08/05 20:37:41
 *          mrajkowski Exp $
 * @created January 15, 2003
 */
public final class TroubleTicketsDocuments extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {

    if (!(hasPermission(context, "allerte-allerte-documents-view"))) {
      return ("PermissionError");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    Exception errorMessage = null;
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      //Build the folder list
      FileFolderList folders = new FileFolderList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId) || "".equals(
          folderId)) {
        folders.setTopLevelOnly(true);
      } else {
        folders.setParentId(Integer.parseInt(folderId));
        //Build array of folder trails
        ProjectManagementFileFolders.buildHierarchy(db, context);
      }
      folders.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
      folders.setLinkItemId(ticketId);
      folders.setBuildItemCount(true);
      folders.buildList(db);

      FileItemList documents = new FileItemList();
      if (folderId == null || "-1".equals(folderId) || "0".equals(folderId) || "".equals(
          folderId)) {
        documents.setTopLevelOnly(true);
      } else {
        documents.setFolderId(Integer.parseInt(folderId));
      }
      documents.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
      documents.setLinkItemId(ticketId);
      documents.buildList(db);
      context.getRequest().setAttribute("fileItemList", documents);
      context.getRequest().setAttribute("fileFolderList", folders);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ViewTickets", "View Documents");
    if (errorMessage == null) {
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {

    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    try {
      db = getConnection(context);
      if(context.getRequest().getParameter("documentiAllerta") != null)
      {
    	  context.getRequest().setAttribute("header", "true");
    	  if(context.getRequest().getParameter("chiusuraUfficio") != null)
          {
    	  
    	  context.getRequest().setAttribute("chiusuraUfficio", "1");
          
          }
      }
      if (context.getRequest().getParameter("tipoAlimenti")!=null)
      {
      context.getRequest().setAttribute("tipoAlimenti", context.getRequest().getParameter("tipoAlimenti"));
      context.getRequest().setAttribute("specie_alimenti", context.getRequest().getParameter("specie_alimenti"));
      }
      if (context.getRequest().getParameter("motivazioni")!=null)
    	  context.getRequest().setAttribute("motivazioni", "true");
      //context.getRequest().setAttribute("header", "true");
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      String folderId = context.getRequest().getParameter("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "ViewTickets", "Upload Document");
    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUploadEInserisci(ActionContext context) {

    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }

    Connection db = null;

    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "tickets");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      if(parts.get("motivazioni")!=null)
    	  context.getRequest().setAttribute("motivazioni", "true");
      db = getConnection(context);
      String id = (String) parts.get("id");
      String subject = (String) parts.get("subject");
      String folderId = (String) parts.get("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db, id);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
        thisItem.setLinkItemId(ticketId);
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());

        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
          
	      db.commit();
          
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        if (subject != null && "".equals(subject.trim())) {
          errors.put(
              "subjectError", systemStatus.getLabel(
                  "object.validation.required"));
        }
        processErrors(context, errors);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    
    if(context.getRequest().getParameter("allegato")!=null){
    context.getRequest().setAttribute("isAllegato", "1");
    return (executeCommandAdd(context));
    	
    }else{

    if (recordInserted) {
      return ("UploadOK");
    }
    return (executeCommandAdd(context));
  }}
  
  
  
  public String executeCommandUpload(ActionContext context) {

	    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
	      return ("PermissionError");
	    }

	    Connection db = null;

	    boolean recordInserted = false;
	    boolean isValid = false;
	    try {
	      String filePath = this.getPath(context, "tickets");

	      //Process the form data
	      HttpMultiPartParser multiPart = new HttpMultiPartParser();
	      multiPart.setUsePathParam(false);
	      multiPart.setUseUniqueName(true);
	      multiPart.setUseDateForFolder(true);
	      multiPart.setExtensionId(getUserId(context));
	      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
	      db = getConnection(context);
	      String id = (String) parts.get("id");
	      String subject = (String) parts.get("subject");
	      String folderId = (String) parts.get("folderId");
	      if (folderId != null) {
	        context.getRequest().setAttribute("folderId", folderId);
	      }
	      if (subject != null) {
	        context.getRequest().setAttribute("subject", subject);
	      }
	      //Load the ticket and the organization
	      Ticket thisTicket = addTicket(context, db, id);
	      int ticketId = thisTicket.getId();
	      //check permission to record
//	      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//	        return ("PermissionError");
//	      }

	      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

	        //Update the database with the resulting file
	        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

	        FileItem thisItem = new FileItem();
	        thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
	        thisItem.setLinkItemId(ticketId);
	        thisItem.setEnteredBy(getUserId(context));
	        thisItem.setModifiedBy(getUserId(context));
	        thisItem.setFolderId(Integer.parseInt(folderId));
	        thisItem.setSubject(subject);
	        thisItem.setClientFilename(newFileInfo.getClientFileName());
	        thisItem.setFilename(newFileInfo.getRealFilename());
	        thisItem.setVersion(1.0);
	        thisItem.setSize(newFileInfo.getSize());

	        isValid = this.validateObject(context, db, thisItem);
	        if (isValid) {
	          recordInserted = thisItem.insert(db);
	           
	          
	        }
	      } else {
	        recordInserted = false;
	        HashMap errors = new HashMap();
	        SystemStatus systemStatus = this.getSystemStatus(context);
	        errors.put(
	            "actionError", systemStatus.getLabel(
	                "object.validation.incorrectFileName"));
	        if (subject != null && "".equals(subject.trim())) {
	          errors.put(
	              "subjectError", systemStatus.getLabel(
	                  "object.validation.required"));
	        }
	        processErrors(context, errors);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      freeConnection(context, db);
	    }
	    
	    if(context.getRequest().getParameter("allegato")!=null){
	    context.getRequest().setAttribute("isAllegato", "1");
	    return (executeCommandAdd(context));
	    	
	    }else{

	    if (recordInserted) {
	      return ("UploadOK");
	    }
	    return (executeCommandAdd(context));
	  }}
  
 public String executeCommandAllegaEInserisci(ActionContext context)
{	


Connection db = null;
boolean isValid = false;
boolean recordInserted = false;
try{
	String filePath = this.getPath(context, "tickets");
//Process the form data
HttpMultiPartParser multiPart = new HttpMultiPartParser();
multiPart.setUsePathParam(false);
multiPart.setUseUniqueName(true);
multiPart.setUseDateForFolder(true);
multiPart.setExtensionId(getUserId(context));
HashMap parts = multiPart.parseData(context.getRequest(), filePath);
if(parts.get("motivazioni")!=null)
	  context.getRequest().setAttribute("motivazioni", "true");
String id_allerta	= "";

if(parts.get("header")!= null)
{
	context.getRequest().setAttribute("header", parts.get("header"));
}



if(parts.get("chiusura")!=null)
{
	context.getRequest().setAttribute("chiusuraUfficio", parts.get("chiusura"));
}
db = getConnection(context);
id_allerta	= (String) parts.get( "id" );
Ticket newTic = new Ticket (db,Integer.parseInt(id_allerta));
String id = ""+newTic.getId();

String idd = (String) parts.get("id");
String subject = (String) parts.get("subject");
String folderId = (String) parts.get("folderId");
if (folderId != null) {
  context.getRequest().setAttribute("folderId", folderId);
}
if (subject != null) {
  context.getRequest().setAttribute("subject", subject);
}
//Load the ticket and the organization
Ticket thisTicket = addTicket(context, db, id);
int ticketId = thisTicket.getId();
//check permission to record
//if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//  return ("PermissionError");
//}

if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {

  //Update the database with the resulting file
  FileInfo newFileInfo = (FileInfo) parts.get("id" + id);

  FileItem thisItem = new FileItem();
  thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
  thisItem.setLinkItemId(ticketId);
  thisItem.setEnteredBy(getUserId(context));
  thisItem.setModifiedBy(getUserId(context));
  thisItem.setFolderId(Integer.parseInt(folderId));
  thisItem.setSubject(subject);
  thisItem.setClientFilename(newFileInfo.getClientFileName());
  thisItem.setFilename(newFileInfo.getRealFilename());
  thisItem.setVersion(1.0);
  thisItem.setSize(newFileInfo.getSize());

  isValid = this.validateObject(context, db, thisItem);
  if (isValid) {
    recordInserted = thisItem.insert(db);
   
      
     
  }
} else {
  recordInserted = false;
  HashMap errors = new HashMap();
  SystemStatus systemStatus = this.getSystemStatus(context);
  errors.put(
      "actionError", systemStatus.getLabel(
          "object.validation.incorrectFileName"));
  if (subject != null && "".equals(subject.trim())) {
    errors.put(
        "subjectError", systemStatus.getLabel(
            "object.validation.required"));
  }
  processErrors(context, errors);
}


int id_asl			= getUser( context, getUserId(context) ).getSiteId();

String chiusuraUfficio = (String) parts.get("chiusuraUfficio");



try{
SystemStatus systemStatus = this.getSystemStatus(context);

LookupList alimentinontrasformati = new LookupList(db,
		"lookup_alimenti_origine_animale_non_trasformati");
alimentinontrasformati.addItem(-1,"-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AlimentiNonTrasformati",
		alimentinontrasformati);

LookupList altrialimenti = new LookupList(db, "lookup_altrialimenti_nonanimale");
altrialimenti.addItem(-1, systemStatus
		.getLabel("calendar.none.4dashes"));
context.getRequest().setAttribute("AltriAlimenti", altrialimenti);

// aggiunto da d.dauria
LookupList alimentinontrasformativalori = new LookupList(db,
"lookup_alimenti_origine_animale_non_trasformati_valori");
alimentinontrasformativalori.addItem(-1, "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AlimentiNonTrasformatiValori",
		alimentinontrasformativalori);

// aggiunto da d.dauria
LookupList alimentitrasformati = new LookupList(db,
"lookup_alimenti_origine_animale_trasformati");
alimentitrasformati.addItem(-1, "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AlimentiTrasformati",
		alimentitrasformati);

// aggiunto da d.dauria
LookupList alimentivegetali = new LookupList(db,
"lookup_alimenti_origine_vegetale_macrocategorie");
alimentivegetali.addItem(-1, "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AlimentiVegetali",
		alimentivegetali);

LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("TipoCampione", TipoCampione);

String fromDefectDetails = context.getRequest().getParameter("defectCheck");
if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
	fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
}

// Parameters


newTic.queryRecord(db, newTic.getId());

// Reset the pagedLists since this could be a new visit to this ticket
deletePagedListInfo(context, "TicketDocumentListInfo");
deletePagedListInfo(context, "SunListInfo");
deletePagedListInfo(context, "TMListInfo");
deletePagedListInfo(context, "CSSListInfo");
deletePagedListInfo(context, "TicketsFolderInfo");
deletePagedListInfo(context, "TicketTaskListInfo");
deletePagedListInfo(context, "ticketPlanWorkListInfo");



Organization orgDetails = new Organization();//new Organization(db, newTic.getOrgId());
// check wether or not the product id exists


LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
Provvedimenti.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
SanzioniAmministrative.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

LookupList SiteIdList = new LookupList(db, "lookup_site_id");

SiteIdList.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("SiteIdList", SiteIdList);

LookupList regioni = new LookupList(db, "lookup_regioni");
regioni.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("Regioni", regioni);



LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
SanzioniPenali.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

LookupList Sequestri = new LookupList(db, "lookup_sequestri");
Sequestri.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("Sequestri", Sequestri);

LookupList TipoAlimento = new LookupList(db, "lookup_tipo_alimento");
TipoAlimento.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("TipoAlimento", TipoAlimento);

LookupList Origine = new LookupList(db, "lookup_origine");
Origine.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("Origine", Origine);

LookupList AlimentoInteressato = new LookupList(db, "lookup_alimento_interessato");
AlimentoInteressato.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("AlimentoInteressato", AlimentoInteressato);

LookupList NonConformita = new LookupList(db, "lookup_non_conformita");
NonConformita.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("NonConformita", NonConformita);

LookupList ListaCommercializzazione = new LookupList(db, "lookup_lista_commercializzazione");
ListaCommercializzazione.addItem(-1,  "-- SELEZIONA VOCE --");
context.getRequest().setAttribute("ListaCommercializzazione", ListaCommercializzazione);

} catch (Exception e) {
context.getRequest().setAttribute("Error", e);
return ("SystemError");
} finally {
this.freeConnection(context, db);
}
context.getRequest().setAttribute("TicketDetails", newTic);
addRecentItem(context, newTic);
addModuleBean(context, "ViewTickets", "View Tickets");

String retPage = "DetailsOK";
String tipo_richiesta = newTic.getTipo_richiesta();
tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

return "AllegaInserisciOk";

	
  



} catch (Exception e) {
context.getRequest().setAttribute("Error", e);
return ("SystemError");
} finally {
freeConnection(context, db);
}

}

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddVersion(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    if (itemId == null) {
      itemId = (String) context.getRequest().getAttribute("fid");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId == null) {
      folderId = (String) context.getRequest().getAttribute("folderId");
    }
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      FileItem thisFile = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      context.getRequest().setAttribute("FileItem", thisFile);

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Upload New Document Version");
    if (errorMessage == null) {
      return ("AddVersionOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUploadVersion(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean recordInserted = false;
    boolean isValid = false;
    try {
      String filePath = this.getPath(context, "tickets");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String id = (String) parts.get("id");
      String itemId = (String) parts.get("fid");
      String subject = (String) parts.get("subject");
      String versionId = (String) parts.get("versionId");
      String folderId = (String) parts.get("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db, id);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS);
        thisItem.setLinkItemId(ticketId);
        thisItem.setId(Integer.parseInt(itemId));
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(Double.parseDouble(versionId));
        thisItem.setSize(newFileInfo.getSize());
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insertVersion(db);
          
	    
	      db.commit();
          
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        SystemStatus systemStatus = this.getSystemStatus(context);
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        if (subject != null && "".equals(subject.trim())) {
          errors.put(
              "subjectError", systemStatus.getLabel(
                  "object.validation.required"));
        }
        processErrors(context, errors);
      }
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      return ("UploadOK");
    }
    return (executeCommandAddVersion(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-view"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      thisItem.buildVersionList(db);
      if (folderId != null && !"-1".equals(folderId) && !"0".equals(folderId) && !"".equals(
          folderId) && !" ".equals(folderId)) {
        //Build array of folder trails
        ProjectManagementFileFolders.buildHierarchy(db, context);
      }
      context.getRequest().setAttribute("FileItem", thisItem);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Document Details");
    if (errorMessage == null) {
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    
       if (!(hasPermission(context, "allerte-allerte-documents-view"))) {
       return ("PermissionError");
       }
     
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    String stream = (String) context.getRequest().getParameter("stream");
    SystemStatus systemStatus = this.getSystemStatus(context);
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
      if (version == null) {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "tickets") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (stream != null && "true".equals(stream)) {
            fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "TroubleTicketsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "tickets") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (stream != null && "true".equals(stream)) {
            fileDownload.setFileTimestamp(itemToDownload.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } 
        
        else {
          db = null;
          System.err.println(
              "TroubleTicketsDocuments-> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", systemStatus.getLabel(
                  "object.validation.actionError.downloadDoesNotExist"));
          return (executeCommandView(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    
      
    } catch (Exception e) {
     
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      addModuleBean(context, "ViewTickets", "");
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Exception errorMessage = null;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Modify Document Information");
    if (errorMessage == null) {
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    boolean recordInserted = false;
    boolean isValid = false;
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    String itemId = (String) context.getRequest().getParameter("fid");
    String subject = (String) context.getRequest().getParameter("subject");
    String filename = (String) context.getRequest().getParameter(
        "clientFilename");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      isValid = this.validateObject(context, db, thisItem);
      if (isValid) {
        recordInserted = thisItem.update(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "");
    if (recordInserted && isValid) {
      return ("UpdateOK");
    } else {
      context.getRequest().setAttribute("fid", itemId);
      return (executeCommandModify(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-delete"))) {
      return ("PermissionError");
    }
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null) {
      context.getRequest().setAttribute("folderId", folderId);
    }
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      recordDeleted = thisItem.delete(db, this.getPath(context, "tickets"));
      

      db.commit();
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "ViewTickets", "Delete Document");
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        return ("DeleteERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Adds a feature to the Ticket attribute of the TroubleTicketsDocuments
   * object
   *
   * @param context The feature to be added to the Ticket attribute
   * @param db      The feature to be added to the Ticket attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Ticket addTicket(ActionContext context, Connection db) throws SQLException {
    String ticketId = (String) context.getRequest().getParameter("tId");
    if (ticketId == null) {
      ticketId = (String) context.getRequest().getAttribute("tId");
    }
    if (ticketId == null) {
        ticketId = context.getRequest().getParameter("id");
      }
    return addTicket(context, db, ticketId);
  }


  /**
   * Adds a feature to the Ticket attribute of the TroubleTicketsDocuments
   * object
   *
   * @param context  The feature to be added to the Ticket attribute
   * @param db       The feature to be added to the Ticket attribute
   * @param ticketId The feature to be added to the Ticket attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Ticket addTicket(ActionContext context, Connection db, String ticketId) throws SQLException {
    context.getRequest().setAttribute("tId", ticketId);
    Ticket thisTicket = new Ticket(db, Integer.parseInt(ticketId));
    context.getRequest().setAttribute("TicketDetails", thisTicket);
    return thisTicket;
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);
    
      return "MoveOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveMove(ActionContext context) {
    if (!(hasPermission(context, "allerte-allerte-documents-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Parameters
    String newFolderId = (String) context.getRequest().getParameter(
        "folderId");
    String itemId = (String) context.getRequest().getParameter("fid");
    try {
      db = getConnection(context);
      //Load the ticket and the organization
      Ticket thisTicket = addTicket(context, db);
      int ticketId = thisTicket.getId();
      //check permission to record
//      if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
//        return ("PermissionError");
//      }

      //Load the file
      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), ticketId, Constants.DOCUMENTS_TICKETS);
      thisItem.buildVersionList(db);
      thisItem.updateFolderId(db, Integer.parseInt(newFolderId));
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }

}

