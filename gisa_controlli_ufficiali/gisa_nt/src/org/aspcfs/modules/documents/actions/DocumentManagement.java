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
package org.aspcfs.modules.documents.actions;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileFolder;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.ProjectList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.documents.base.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Docuement Management module
 *
 * @author
 * @version $Id: DocumentManagement.java,v 1.2 2005/04/13 20:04:34 mrajkowski
 *          Exp $
 * @created November 6, 2001
 */
public final class DocumentManagement extends CFSModule {

  /**
   * Show the Document Store List by default
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    //return "DocumentsOK";
    return executeCommandEnterpriseView(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  
  public String executeCommandPopupSelectorAllegatiInBacheca(ActionContext context) {
	    Connection db = null;
	    DocumentStoreList contactList = null;
	    boolean listDone = false;
	    String selectedIds = context.getRequest().getParameter("selectedIds");
	    String listType = context.getRequest().getParameter("listType");
	    String type = context.getRequest().getParameter("type");
	    if (type != null && !"".equals(type) && !"null".equals(type)) {
	      context.getRequest().setAttribute("type", type);
	    }
	    String displayType = context.getRequest().getParameter("displayType");
	    HashMap selectedList = new HashMap();
	    //initialize from page, if list...
	    //put in session
	    if (context.getRequest().getParameter("previousSelection") != null) {
	      StringTokenizer st = new StringTokenizer(
	          context.getRequest().getParameter("previousSelection"), "|");
	      StringTokenizer st1 = new StringTokenizer(
	          context.getRequest().getParameter("previousSelectionDisplay"), "|");
	      while (st.hasMoreTokens()) {
	        selectedList.put(new Integer(st.nextToken()), st1.nextToken());
	      }
	    } else {
	      //get selected list from the session
	      selectedList = (HashMap) context.getSession().getAttribute(
	          "selectedContacts");
	    }
	    //Flush the selectedList if its a new selection
	    if ("true".equals(
	        ((String) context.getRequest().getParameter("flushtemplist")))) {
	      if (context.getSession().getAttribute("finalContacts") != null && context.getRequest().getParameter(
	          "previousSelection") == null) {
	        selectedList = (HashMap) ((HashMap) context.getSession().getAttribute(
	            "finalContacts")).clone();
	      }
	    }
	 
	    try {
	      db = this.getConnection(context);
	      SystemStatus thisSystem = this.getSystemStatus(context);
	      //Build Department List if empty
	     
	      
	      ArrayList<Role> listaRuoli = new ArrayList<Role>();
	      java.sql.PreparedStatement pst1 = db.prepareStatement("select role_id , role from role where enabled = true ORDER BY role;");
	      ResultSet rs = pst1.executeQuery();
	      while(rs.next())
	      {
	    	  int ruoloId = rs.getInt(1);
	    	  String descrizione = rs.getString(2);
	    	  Role ruolo = new Role();
	    	  ruolo.setDescription(descrizione);
	    	  ruolo.setId(ruoloId);
	    	  listaRuoli.add(ruolo);
	      }
	      context.getRequest().setAttribute("listaRuoli", listaRuoli);
	      LookupList listaAsl = new LookupList(db, "lookup_site_id");
	      listaAsl.addItem(
	          -1, "--Nessuna Asl--"); //All Departments
	      context.getRequest().setAttribute("listaAsl", listaAsl);
	      
	      
	      //Build Project List if empty
	      if (context.getSession().getAttribute("ProjectListSelect") == null) {
	        ProjectList projects = new ProjectList();
	        projects.setUserRange(this.getUserRange(context));
	        projects.setBuildAssignments(false);
	        projects.setBuildIssues(false);
	        projects.setGroupId(-1);
	        projects.buildList(db);
	        HtmlSelect htmlSelect = projects.getHtmlSelect();
	        htmlSelect.addItem(
	            -1, "--" + thisSystem.getLabel("contact.allProjects") + "--", 0); //All Projects
	        context.getSession().setAttribute("ProjectListSelect", htmlSelect);
	      }
	      contactList = new DocumentStoreList();
	    
	      /*
	       *  Collect the selected entries in the contactList & store it in the session's HashMap i.e checkcontact
	       *  checkcontact+rowCount is the checkbox name (value is  the contact_id)
	       *  Single Email   : email as a hidden value contactemail+rowCount
	       *  Multiple Emails: email as a value of selected entry from comboBox i.e contactemail_rowCount
	       */
	      int rowCount = 1;
	      //list
	      if (listType.equalsIgnoreCase("list")) {
	        while (context.getRequest().getParameter("hiddencontactid" + rowCount) != null)
	        {
	          int contactId = 0;
	          String emailAddress = "";
	          contactId = Integer.parseInt(
	              context.getRequest().getParameter("hiddencontactid" + rowCount));

	          if (context.getRequest().getParameter("checkcontact" + rowCount) != null)
	          {
	            if (context.getRequest().getParameter("contactemail" + rowCount) != null)
	            {

	              //we want this "emailAddress" variable to be the email only if we are not in Campaign Mgr.
	              if (context.getRequest().getParameter("campaign") == null) {
	                emailAddress = context.getRequest().getParameter(
	                    "contactemail" + rowCount);
	              } else if (!(((String) context.getRequest().getParameter(
	                  "campaign")).equalsIgnoreCase("true"))) {
	                emailAddress = context.getRequest().getParameter(
	                    "contactemail" + rowCount);
	              }
	            }

	            //If User does not have a emailAddress or if it is not a message use Name(LastFirst)
	            if (emailAddress.equals("") || "single".equals(listType) || "name".equals(
	                displayType)) {
	              if (context.getRequest().getParameter("hiddenname" + rowCount) != null)
	              {
	                emailAddress = "P:" + context.getRequest().getParameter(
	                    "hiddenname" + rowCount);
	              }
	            }

	            if (selectedList.get(new Integer(contactId)) == null) {
	              selectedList.put(new Integer(contactId), emailAddress);
	            } else {
	              selectedList.remove(new Integer(contactId));
	              selectedList.put(new Integer(contactId), emailAddress);
	            }
	          } else {
	            selectedList.remove(new Integer(contactId));
	          }
	          rowCount++;
	        }
	      } else {
	        if (selectedIds != null && !"".equals(selectedIds)) {
	          if (selectedList == null) {
	            selectedList = new HashMap();
	          }
	          selectedList.clear();
	          selectedList.put(new Integer(Integer.parseInt(selectedIds)), "");
	        }
	      }
	      if ("true".equals(
	          (String) context.getRequest().getParameter("finalsubmit"))) {
	        //Handle single selection case
	        if ("single".equals(listType)) {
	          rowCount = Integer.parseInt(
	              context.getRequest().getParameter("rowcount"));
	          String emailAddress = context.getRequest().getParameter(
	              "hiddenname" + rowCount);
	          int contactId = Integer.parseInt(
	              context.getRequest().getParameter("hiddencontactid" + rowCount));
	          selectedList.clear();
	          selectedList.put(new Integer(contactId), emailAddress);
	        }
	        listDone = true;
	        
	      }
	      
	      PagedListInfo documentStoreListInfo = this.getPagedListInfo(
	  	        context, "documentStoreListInfo");
	  	    documentStoreListInfo.setLink(
	  	        "DocumentManagement.do?command=EnterpriseViewAllegati");
	  	    if (documentStoreListInfo.getListView() == null) {
	  	      //My Open Document Stores
	  	      documentStoreListInfo.setListView("Open");
	  	    }
	  	    if (documentStoreListInfo.getListView().equals("Open")) {
	  	    	contactList.setOpenDocumentStoresOnly(true);
	  	    } else if (documentStoreListInfo.getListView().equals("Archived")) {
	  	    	contactList.setClosedDocumentStoresOnly(true);
	  	    } else if (documentStoreListInfo.getListView().equals("Trashed")) {
	  	    	contactList.setIncludeOnlyTrashed(true);
	  	    }
	  	  int tmpUserId = this.getUserId(context);
	      User tmpUser = getUser(context, tmpUserId);
	  	  int tmpUserRoleId = tmpUser.getRoleId();
	     
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
	      context.getRequest().setAttribute("SiteIdList", siteList);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    if ("true".equals((String) context.getRequest().getParameter("campaign"))) {
	      context.getRequest().setAttribute(
	          "Campaign", (String) context.getRequest().getParameter("campaign"));
	    }

	    context.getSession().setAttribute("selectedContacts", selectedList);
	 
	    
	    String ret = "EnterpriseViewOKPopUp";
	    
	    return ret;
	  }
  
  
  /*public String executeCommandPopupSelectorAllegatiInBacheca(ActionContext context) {
	    //return "DocumentsOK";
	  if (getUserId(context) < 0) {
	      return "PermissionError";
	    }
	    if (!(hasPermission(context, "documents-view"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    DocumentStoreList documentStoreList = new DocumentStoreList();
	    PagedListInfo documentStoreListInfo = this.getPagedListInfo(
	        context, "documentStoreListInfo");
	    documentStoreListInfo.setLink(
	        "DocumentManagement.do?command=EnterpriseViewAllegati");
	    if (documentStoreListInfo.getListView() == null) {
	      //My Open Document Stores
	      documentStoreListInfo.setListView("Open");
	    }
	    if (documentStoreListInfo.getListView().equals("Open")) {
	      documentStoreList.setOpenDocumentStoresOnly(true);
	    } else if (documentStoreListInfo.getListView().equals("Archived")) {
	      documentStoreList.setClosedDocumentStoresOnly(true);
	    } else if (documentStoreListInfo.getListView().equals("Trashed")) {
	      documentStoreList.setIncludeOnlyTrashed(true);
	    }
	    try {
	      db = getConnection(context);
	      int tmpUserId = this.getUserId(context);
	      User tmpUser = getUser(context, tmpUserId);
	      int tmpUserRoleId = tmpUser.getRoleId();
	      Contact tmpContact = new Contact(db, tmpUser.getContactId());
	      int tmpDepartmentId = tmpContact.getDepartment();
	      documentStoreList.setDocumentStoresForUser(getUserId(context));
	      documentStoreList.setUserRole(tmpUserRoleId);
	      documentStoreList.setDepartmentId(tmpDepartmentId);
	      documentStoreList.setSiteId(tmpUser.getSiteId());
	      documentStoreList.setPagedListInfo(documentStoreListInfo);
	      documentStoreList.buildList(db);
	      context.getRequest().setAttribute(
	          "documentStoreList", documentStoreList);
	      //cache permissions
	      getDocumentStoreUserLevel(context, db, DocumentStoreTeamMember.GUEST);
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return ("EnterpriseViewOKPopUp");
	  }
	  */
  
  public String executeCommandEnterpriseView(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    DocumentStoreList documentStoreList = new DocumentStoreList();
    PagedListInfo documentStoreListInfo = this.getPagedListInfo(
        context, "documentStoreListInfo");
    documentStoreListInfo.setLink(
        "DocumentManagement.do?command=EnterpriseView");
    if (documentStoreListInfo.getListView() == null) {
      //My Open Document Stores
      documentStoreListInfo.setListView("Open");
    }
    if (documentStoreListInfo.getListView().equals("Open")) {
      documentStoreList.setOpenDocumentStoresOnly(true);
    } else if (documentStoreListInfo.getListView().equals("Archived")) {
      documentStoreList.setClosedDocumentStoresOnly(true);
    } else if (documentStoreListInfo.getListView().equals("Trashed")) {
      documentStoreList.setIncludeOnlyTrashed(true);
    }
    try {
      db = getConnection(context);
      int tmpUserId = this.getUserId(context);
      User tmpUser = getUser(context, tmpUserId);
      int tmpUserRoleId = tmpUser.getRoleId();
      documentStoreList.setDocumentStoresForUser(getUserId(context));
      documentStoreList.setUserRole(tmpUserRoleId);
      documentStoreList.setSiteId(tmpUser.getSiteId());
      documentStoreListInfo.setColumnToSortBy("entered");
      documentStoreListInfo.setSortOrder("desc");
      documentStoreList.setPagedListInfo(documentStoreListInfo);
      documentStoreList.buildList(db);
      context.getRequest().setAttribute(
          "documentStoreList", documentStoreList);
      //cache permissions
      getDocumentStoreUserLevel(context, db, DocumentStoreTeamMember.GUEST);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("EnterpriseViewOK");
  }

  
  public String executeCommandEnterpriseViewAllegati(ActionContext context) {
	    if (getUserId(context) < 0) {
	      return "PermissionError";
	    }
	    if (!(hasPermission(context, "documents-view"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    DocumentStoreList documentStoreList = new DocumentStoreList();
	    PagedListInfo documentStoreListInfo = this.getPagedListInfo(
	        context, "documentStoreListInfo");
	    documentStoreListInfo.setLink(
	        "DocumentManagement.do?command=EnterpriseViewAllegati");
	    if (documentStoreListInfo.getListView() == null) {
	      //My Open Document Stores
	      documentStoreListInfo.setListView("Open");
	    }
	    if (documentStoreListInfo.getListView().equals("Open")) {
	      documentStoreList.setOpenDocumentStoresOnly(true);
	    } else if (documentStoreListInfo.getListView().equals("Archived")) {
	      documentStoreList.setClosedDocumentStoresOnly(true);
	    } else if (documentStoreListInfo.getListView().equals("Trashed")) {
	      documentStoreList.setIncludeOnlyTrashed(true);
	    }
	    try {
	      db = getConnection(context);
	      int tmpUserId = this.getUserId(context);
	      User tmpUser = getUser(context, tmpUserId);
	      int tmpUserRoleId = tmpUser.getRoleId();
	     
	      documentStoreList.setDocumentStoresForUser(getUserId(context));
	      documentStoreList.setUserRole(tmpUserRoleId);
	      documentStoreList.setSiteId(tmpUser.getSiteId());
	      documentStoreList.setPagedListInfo(documentStoreListInfo);
	      documentStoreList.buildList(db);
	      context.getRequest().setAttribute(
	          "documentStoreList", documentStoreList);
	      //cache permissions
	      getDocumentStoreUserLevel(context, db, DocumentStoreTeamMember.GUEST);
	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    return ("EnterpriseViewAllegatiOK");
	  }

  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandAddDocumentStore(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents_documentstore-add"))) {
      return ("PermissionError");
    }
    try {
      DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();
      if (thisDocumentStore.getRequestDate() == null) {
        thisDocumentStore.setRequestDate(
            DateUtils.roundUpToNextFive(System.currentTimeMillis()));
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
    }
    return ("AddDocumentStoreOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandInsertDocumentStore(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    if (!(hasPermission(context, "documents_documentstore-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    boolean isValid = false;
    User user = this.getUser(context, this.getUserId(context));
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();

      thisDocumentStore.setEnteredBy(this.getUserId(context));
      thisDocumentStore.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, thisDocumentStore);

      if (isValid) {
        thisDocumentStore.insert(db,context);
        updateDocumentStoreCache(
            context, thisDocumentStore.getId(), thisDocumentStore.getTitle());
        indexAddItem(context, thisDocumentStore);

        //Add the current user to the team TODO: Put in a transaction
        DocumentStoreTeamMember thisMember = new DocumentStoreTeamMember();
        thisMember.setDocumentStoreId(thisDocumentStore.getId());
        thisMember.setStatus(DocumentStoreTeamMember.STATUS_ADDED);
        thisMember.setItemId(this.getUserId(context));
        thisMember.setUserLevel(
            getDocumentStoreUserLevel(
                context, db, DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER));
        thisMember.setSiteId(user.getSiteId());
        thisMember.setEnteredBy(this.getUserId(context));
        thisMember.setModifiedBy(this.getUserId(context));
        thisMember.insert(db, DocumentStoreTeamMemberList.USER);
        //Go to the document store
        context.getRequest().setAttribute(
            "documentStoreId", String.valueOf(thisDocumentStore.getId()));
        return (executeCommandDefault(context));
      } else {
        return (executeCommandAddDocumentStore(context));
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandModifyDocumentStore(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("modify_document_store").toLowerCase());
      //Category List
      //LookupList categoryList = new LookupList(db, "lookup_document_store_category");
      //categoryList.addItem(-1,  "-- SELEZIONA VOCE --");
      //context.getRequest().setAttribute("categoryList", categoryList);
      return ("DocumentStoreCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandConfigurePermissions(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("setup_permissions").toLowerCase());
      //Load the possible permission categories and permissions
      DocumentStorePermissionCategoryLookupList categories = new DocumentStorePermissionCategoryLookupList();
      categories.setIncludeEnabled(Constants.TRUE);
      categories.buildList(db);
      categories.buildResources(db);
      context.getRequest().setAttribute("categories", categories);
      return ("DocumentStoreCenterOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandUpdatePermissions(ActionContext context) {
    Connection db = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      //Make sure user can modify permissions
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
        return "PermissionError";
      }
      DocumentStorePermissionList.updateDocumentStorePermissions(
          db, context.getRequest(), Integer.parseInt(documentStoreId),context);
      return "UpdatePermissionsOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateDocumentStore(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-edit"))) {
      return ("PermissionError");
    }
    boolean isValid = false;
    DocumentStore thisDocumentStore = (DocumentStore) context.getFormBean();
    //thisDocumentStore.setRequestItems(context.getRequest());
    Connection db = null;
    int resultCount = 0;
    try {
      db = this.getConnection(context);
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-edit")) {
        return "PermissionError";
      }
      thisDocumentStore.setModifiedBy(this.getUserId(context));
      isValid = this.validateObject(context, db, thisDocumentStore);
      if (isValid) {
        resultCount = thisDocumentStore.update(db);
      }
      if (resultCount == 1) {
        updateDocumentStoreCache(
            context, thisDocumentStore.getId(), thisDocumentStore.getTitle());
        indexAddItem(context, thisDocumentStore);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    //Results
    if (resultCount == -1 || !isValid) {
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("modify_document_store").toLowerCase());
      return ("DocumentStoreCenterOK");
    } else if (resultCount == 1) {
      context.getRequest().setAttribute(
          "documentStoreId", "" + thisDocumentStore.getId());
      return ("UpdateDocumentStoreOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDocumentStoreCenter(ActionContext context) {
    if (!(hasPermission(context, "documents_documentstore-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    DocumentStore thisDocumentStore = null;
    //Parameters
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    if (documentStoreId == null) {
      documentStoreId = (String) context.getRequest().getAttribute(
          "documentStoreId");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String section = (String) context.getRequest().getParameter("section");
    //Determine the section to display
    try {
      db = getConnection(context);
      LookupList siteList = new LookupList(db, "lookup_site_id");
      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", siteList);
      thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      
      context.getRequest().setAttribute("idMessaggio", context.getRequest().getParameter("idMessaggio"));
      if (section == null || "".equals(section) || "File_Library".equals(
          section)) {
        section = "File_Library";
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-documents-view")) {
          return "PermissionError";
        }
        String folderId = context.getRequest().getParameter("folderId");
        if (folderId == null) {
          folderId = (String) context.getRequest().getAttribute("folderId");
        }
        //Build the folder list
        FileFolderList folders = new FileFolderList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          folders.setTopLevelOnly(true);
        } else {
          folders.setParentId(Integer.parseInt(folderId));
          //Build array of folder trails
          DocumentStoreManagementFileFolders.buildHierarchy(db, context);
        }
        folders.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        folders.setLinkItemId(thisDocumentStore.getId());
        folders.setBuildItemCount(true);
        folders.buildList(db);
        context.getRequest().setAttribute("fileFolderList", folders);
        //Build the file item list
        org.aspcfs.modules.documents.base.FileItemList files = new org.aspcfs.modules.documents.base.FileItemList();
        if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
          files.setTopLevelOnly(true);
          //Reset the pagedListInfo
          deletePagedListInfo(context, "documentStoreDocumentsGalleryInfo");
        } else {
          files.setFolderId(Integer.parseInt(folderId));
        }
        files.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
        files.setLinkItemId(thisDocumentStore.getId());
        files.buildList(db);
        thisDocumentStore.setFiles(files);
      } else if ("Team".equals(section)) {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-team-view")) {
          return "PermissionError";
        }
        //Check the pagedList filter
        PagedListInfo documentStoreUserTeamInfo = this.getPagedListInfo(
            context, "documentStoreUserTeamInfo");
        documentStoreUserTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreUserTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreEmployeeTeamInfo = this.getPagedListInfo(
            context, "documentStoreEmployeeTeamInfo");
        documentStoreEmployeeTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreEmployeeTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreAccountContactTeamInfo = this.getPagedListInfo(
            context, "documentStoreAccountContactTeamInfo");
        documentStoreAccountContactTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreAccountContactTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreRoleTeamInfo = this.getPagedListInfo(
            context, "documentStoreRoleTeamInfo");
        documentStoreRoleTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreRoleTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStoreDepartmentTeamInfo = this.getPagedListInfo(
            context, "documentStoreDepartmentTeamInfo");
        documentStoreDepartmentTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStoreDepartmentTeamInfo.setItemsPerPage(0);

        PagedListInfo documentStorePortalUserTeamInfo = this.getPagedListInfo(
            context, "documentStorePortalUserTeamInfo");
        documentStorePortalUserTeamInfo.setLink(
            "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
        documentStorePortalUserTeamInfo.setItemsPerPage(0);
        
        //Generate the list
        thisDocumentStore.getUserTeam().setPagedListInfo(
            documentStoreUserTeamInfo);
        thisDocumentStore.getEmployeeTeam().setPagedListInfo(
            documentStoreEmployeeTeamInfo);
        thisDocumentStore.getAccountContactTeam().setPagedListInfo(
            documentStoreAccountContactTeamInfo);
        thisDocumentStore.getRoleTeam().setPagedListInfo(
            documentStoreRoleTeamInfo);
        thisDocumentStore.getDepartmentTeam().setPagedListInfo(
            documentStoreDepartmentTeamInfo);
        thisDocumentStore.getPortalUserTeam().setPagedListInfo(
            documentStorePortalUserTeamInfo);
        thisDocumentStore.buildTeamMemberList(db);

        Iterator i = thisDocumentStore.getUserTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getEmployeeTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getAccountContactTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisMember.setUser(thisUser);
        }
        i = thisDocumentStore.getRoleTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
        }
        i = thisDocumentStore.getDepartmentTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
         
        }
        i = thisDocumentStore.getPortalUserTeam().iterator();
        while (i.hasNext()) {
          DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
          User thisUser = new User();
          thisUser.setBuildContact(true);
          thisUser.setBuildContactDetails(true);
          thisMember.setUser(thisUser);
        }
  
      } else if ("Details".equals(section)) {
        //Just looking at the details
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-details-view")) {
          return "PermissionError";
        }
      } else if ("Setup".equals(section)) {
        if (!hasDocumentStoreAccess(
            context, db, thisDocumentStore, "documentcenter-setup-permissions")) {
          return "PermissionError";
        }
      }
      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", section.toLowerCase());
      //The user has access, so show that they accessed the Document Store
      User user = this.getUser(context, this.getUserId(context));
      DocumentStoreTeamMember.updateLastAccessed(
          db, thisDocumentStore.getId(), user.getId(), user.getSiteId());
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("DocumentStoreCenterOK");
  }
  
  
  
  
  public String executeCommandDocumentStoreCenterBacheca(ActionContext context) {
	    if (!(hasPermission(context, "documents_documentstore-view"))) {
	      return ("PermissionError");
	    }
	    
	    String sql = "select document_store_id from document_store where trashed_date is null";
	   
	    String nomeFile = context.getRequest().getParameter("nomeFile");
	    Connection db = null;
	    DocumentStore thisDocumentStore = null;
	    //Parameters
	    String documentStoreId = (String) context.getRequest().getParameter(
	        "documentStoreId");
	    if (documentStoreId == null) {
	      documentStoreId = (String) context.getRequest().getAttribute(
	          "documentStoreId");
	    }
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    String section = (String) context.getRequest().getParameter("section");
	    //Determine the section to display
	    try {
	      db = getConnection(context);
	      java.sql.PreparedStatement pst = db.prepareStatement(sql);
	      ResultSet rs = pst.executeQuery();
	     ArrayList<FileItem> listaFile = new ArrayList<FileItem>();
	     ArrayList<FileItem> listaFileFiltrati = new ArrayList<FileItem>();
	      while(rs.next())
	      {
	    	Integer id_cod_store = rs.getInt(1);
	    	
	    	thisDocumentStore = new DocumentStore( db, (id_cod_store));
	    	 FileItemList files = new FileItemList();
	        
	         files.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
	         files.setLinkItemId(thisDocumentStore.getId());
	         files.buildList(db);
	         thisDocumentStore.setFiles(files);
	    	 
	    	 Integer folderId = thisDocumentStore.getId();
	    	 FileFolderList folders = new FileFolderList();
	         if (folderId == null || "-1".equals(folderId) || "0".equals(folderId)) {
	           folders.setTopLevelOnly(true);
	         } else {
	           folders.setParentId(folderId);
	           //Build array of folder trails
	           DocumentStoreManagementFileFolders.buildHierarchy(db, context);
	         }
	         folders.setLinkModuleId(Constants.DOCUMENTS_DOCUMENTS);
	         folders.setLinkItemId(thisDocumentStore.getId());
	         folders.setBuildItemCount(true);
	         folders.buildList(db);
	    
	    	FileItemList folds = thisDocumentStore.getFiles();
	    	Iterator i = folds.iterator();
	    	  while (i.hasNext()) {
	    	    
	    	    FileItem thisFile = (FileItem)i.next();
	    	    listaFile.add(thisFile);
	    	    
	    	    if(nomeFile!=null && !nomeFile.equalsIgnoreCase("") && thisFile.getSubject().toLowerCase().startsWith(nomeFile.toLowerCase()))
	    	    {
	    	    	listaFileFiltrati.add(thisFile);
	    	    }
	    	   
	    	}
	        
	      }
	      
	      if(nomeFile == null || nomeFile.equals("") )
	      {	  
	    	  context.getRequest().setAttribute("listafile", listaFile);
	      }
	      else
	      {
	    	  context.getRequest().setAttribute("listafile", listaFileFiltrati);
	      }
	      
	      
	      LookupList siteList = new LookupList(db, "lookup_site_id");
	      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", siteList);
	      
	      
	      
	    }catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
	    finally
	    {
	    	this.freeConnection(context, db);
	    }
	    return "EnterpriseViewOKPopUp";
	  }

  
  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDeleteDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
    	  	context.getRequest().setAttribute("PermissionErrorBacheca", "si");
        return "PermissionError";
      }
      thisDocumentStore.delete(db, getDbNamePath(context));
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);

      indexDeleteItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandTrashDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
        return "PermissionError";
      }
      thisDocumentStore.updateStatus(db, true, this.getUserId(context));
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);
      indexDeleteItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
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
  public String executeCommandRestoreDocumentStore(ActionContext context) {
    Connection db = null;
    //Params
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    try {
      db = this.getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
      thisDocumentStore.buildPermissionList(db);
      if (!hasDocumentStoreAccess(
          context, db, thisDocumentStore, "documentcenter-details-delete")) {
        return "PermissionError";
      }
      thisDocumentStore.updateStatus(db, false, this.getUserId(context));
      thisDocumentStore.setTrashedDate((java.sql.Timestamp) null);
      updateDocumentStoreCache(context, thisDocumentStore.getId(), null);
      indexAddItem(context, thisDocumentStore);

      return "DeleteDocumentStoreOK";
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }

}

