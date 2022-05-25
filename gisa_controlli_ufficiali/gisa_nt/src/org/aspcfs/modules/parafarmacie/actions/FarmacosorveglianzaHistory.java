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
package org.aspcfs.modules.parafarmacie.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.farmacosorveglianza.base.Prescrizioni;
import org.aspcfs.modules.farmacosorveglianza.base.PrescrizioniHistory;
import org.aspcfs.modules.farmacosorveglianza.base.PrescrizioniHistoryList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: AccountsHistory.java 15627 2006-08-08 19:08:07Z matt $
 * @created May 27, 2005
 */
public final class FarmacosorveglianzaHistory extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandView(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "parafarmacie-parafarmacie-history-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "AccountsHistory", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Prescrizioni newOrg = null;
    String orgid = context.getRequest().getParameter("orgId");
    if (orgid == null) {
      orgid = (String) context.getRequest().getAttribute("orgId");
    }
    String isPopup = context.getRequest().getParameter("popup");
    PagedListInfo historyListInfo = this.getPagedListInfo(
        context, "orgHistoryListInfo");
    historyListInfo.setLink("AccountsHistory.do?command=View&orgId=" + orgid+
      (isPopup != null && "true".equals(isPopup) ? "&popup=true":""));

    PrescrizioniHistoryList historyList = new PrescrizioniHistoryList();
    historyList.setPagedListInfo(historyListInfo);
    historyListInfo.setSearchCriteria(historyList, context);


    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Prescrizioni(db, Integer.parseInt(orgid));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      //Build the list of contacts
//      contacts.setOrgId(orgid);
//      contacts.setShowTrashedAndNormal(true);
//      contacts.setIncludeEnabled(Constants.UNDEFINED);
      //contacts.setShowTrashedAndNormal(true);
      if (historyListInfo.getSavedCriteria().size() == 0) {
        setDefaultFilters(context, historyList);
      }
//      contacts.buildList(db);
//      context.getRequest().setAttribute(
//          "contacts", contacts.getHashMapOfContacts());
      //Build the history for the organization
      historyList.setOrgId(newOrg.getOrgId());
      historyList.buildList(db);
      context.getRequest().setAttribute("historyList", historyList);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "List");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandAddNote(ActionContext context) {
    if (!hasPermission(context, "parafarmacie-parafarmacie-history-add")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Account History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Prescrizioni newOrg = null;
    PrescrizioniHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Prescrizioni(db, Integer.parseInt(orgId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new PrescrizioniHistory();
      context.getRequest().setAttribute("history", history);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("AddNoteOK");
  }
  
  
  
  
//aggiunto da d.dauria
  public String executeCommandAddNote2(ActionContext context) {
	    if (!hasPermission(context, "parafarmacie-parafarmacie-history-add")) {
	      return ("PermissionError");
	    }
	    addModuleBean(context, "Account History", "View History");
	    String source = (String) context.getRequest().getParameter("source");
	    Prescrizioni newOrg = null;
	    PrescrizioniHistory history = null;
	    String orgId = context.getRequest().getParameter("orgId");
	    if (orgId == null) {
	      orgId = (String) context.getRequest().getAttribute("orgId");
	    }
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      newOrg = new Prescrizioni(db, Integer.parseInt(orgId));
	      //check permission to record
	      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
	        return ("PermissionError");
	      }
	      context.getRequest().setAttribute("OrgDetails", newOrg);
	      history = new PrescrizioniHistory();
	      context.getRequest().setAttribute("history", history);
	    } catch (Exception e) {
	      e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return ("AddNote2OK");
	  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSaveNote(ActionContext context) {
    String source = (String) context.getRequest().getParameter("source");
    Prescrizioni newOrg = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    PrescrizioniHistory history = (PrescrizioniHistory) context.getFormBean();
    if (history.getId() != -1) {
      if (!hasPermission(context, "parafarmacie-parafarmacie-history-edit")) {
        return ("PermissionError");
      }
    } else {
      if (!hasPermission(context, "parafarmacie-parafarmacie-history-add")) {
        return ("PermissionError");
      }
    }
    boolean isValid = false;
    boolean recordInserted = false;
    int resultCount = -1;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Prescrizioni(db, Integer.parseInt(orgId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      history.setEnteredBy(this.getUserId(context));
      history.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, history);
      if (isValid) {
        if (history.getId() != -1) {
          resultCount = history.update(db);
        } else {
          recordInserted = history.insert(db,context);
        }
      }
      if (!recordInserted && resultCount == -1) {
        context.getRequest().setAttribute("OrgDetails", newOrg);
        context.getRequest().setAttribute("history", history);
        return "AddNoteOK";
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "SaveNoteOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyNote(ActionContext context) {
    if (!hasPermission(context, "parafarmacie-parafarmacie-history-edit")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Organization History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Prescrizioni newOrg = null;
    PrescrizioniHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Prescrizioni(db, Integer.parseInt(orgId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new PrescrizioniHistory(db, Integer.parseInt(id));
      context.getRequest().setAttribute("history", history);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "AddNoteOK";
  }

  public String executeCommandDeleteNote(ActionContext context) {
    if (!hasPermission(context, "parafarmacie-parafarmacie-history-delete")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Organization History", "View History");
    String source = (String) context.getRequest().getParameter("source");
    Prescrizioni newOrg = null;
    PrescrizioniHistory history = null;
    String orgId = context.getRequest().getParameter("orgId");
    if (orgId == null) {
      orgId = (String) context.getRequest().getAttribute("orgId");
    }
    String id = context.getRequest().getParameter("id");
    if (id == null) {
      id = (String) context.getRequest().getAttribute("id");
    }
    boolean result = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      newOrg = new Prescrizioni(db, Integer.parseInt(orgId));
      //check permission to record
      if (!isRecordAccessPermitted(context, db, newOrg.getOrgId())) {
        return ("PermissionError");
      }
      context.getRequest().setAttribute("OrgDetails", newOrg);
      history = new PrescrizioniHistory(db, Integer.parseInt(id));
      result = history.delete(db);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return executeCommandView(context);
  }

  private void setDefaultFilters(ActionContext context, PrescrizioniHistoryList list) {
    if (hasPermission(context, "parafarmacie-parafarmacie-view")) {
      list.setNotes(true);
    }
    if (hasPermission(context, "parafarmacie-parafarmacie-contacts-calls-edit") || hasPermission(
        context, "parafarmacie-parafarmacie-contacts-calls-view")) {
      list.setActivities(true);
    }
    if (hasPermission(context, "myhomepage-inbox-view") || hasPermission(
        context, "parafarmacie-parafarmacie-contacts-messages-view")) {
      list.setEmail(true);
    }
    if (hasPermission(context, "parafarmacie-parafarmacie-documents-view") || hasPermission(
        context, "parafarmacie-parafarmacie-documents-edit")) {
      list.setDocuments(true);
    }
    if (hasPermission(context, "accounts-quotes-view") || hasPermission(
        context, "accounts-quotes-edit")) {
      list.setQuotes(true);
    }
    if (hasPermission(context, "parafarmacie-parafarmacie-opportunities-view") || hasPermission(
        context, "parafarmacie-parafarmacie-opportunities-edit")) {
      list.setOpportunities(true);
    }
    if (hasPermission(context, "accounts-service-contracts-view") || hasPermission(
        context, "accounts-service-contracts-edit")) {
      list.setServiceContracts(true);
    }
    if (hasPermission(context, "parafarmacie-parafarmacie-tickets-edit") || hasPermission(
        context, "parafarmacie-parafarmacie-tickets-view")) {
      list.setTickets(true);
    }
    if (hasPermission(context, "myhomepage-tasks-view") || hasPermission(
        context, "myhomepage-tasks-edit")) {
      list.setTasks(true);
    }
    if (hasPermission(context, "parafarmacie-parafarmacie-relationships-view")) {
      list.setRelationships(true);
    }
    if (hasPermission(context, "accounts-assets-edit") || hasPermission(
        context, "accounts-assets-view")) {
      list.setAssets(true);
    }
  }
}

