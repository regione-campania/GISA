/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.documents.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;

import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.modules.documents.base.DocumentStorePermissionCategoryLookupList;
import org.aspcfs.modules.documents.base.DocumentStorePermissionList;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileFolderList;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.ProjectList;

public class DocumentManagementAsl extends CFSModule {
	
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
	  
	  
	  public String executeCommandEnterpriseView(ActionContext context) {
	    if (getUserId(context) < 0) {
	      return "PermissionError";
	    }
	    if (!(hasPermission(context, "documents-asl-view"))) {
	      return ("PermissionError");
	    }
	    Connection db = null;
	    DocumentStoreList documentStoreList = new DocumentStoreList();
	    PagedListInfo documentStoreListInfo = this.getPagedListInfo(
	        context, "documentStoreListInfo");
	    documentStoreListInfo.setLink(
	        "DocumentManagementAsl.do?command=EnterpriseView");
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
	      documentStoreList.buildListAsl(db);
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
		    if (!(hasPermission(context, "documents-asl-view"))) {
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
	    if (!(hasPermission(context, "documents_documentstore-asl-add"))) {
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
	    if (!(hasPermission(context, "documents_documentstore-asl-add"))) {
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
	    if (!(hasPermission(context, "documents_documentstore-asl-view"))) {
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
	          db, Integer.parseInt(documentStoreId),true);
	      //thisDocumentStore.buildPermissionList(db);
	      
	      context.getRequest().setAttribute("idMessaggio", context.getRequest().getParameter("idMessaggio"));
	      if (section == null || "".equals(section) || "File_Library".equals(
	          section)) {
	        section = "File_Library";
	        
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
	        PagedListInfo documentStoreUserTeamInfo = this.getPagedListInfo(
		            context, "documentStoreUserTeamInfo");
		        documentStoreUserTeamInfo.setLink(
		            "DocumentManagementAsl.do?command=DocumentStoreCenter&documentStoreId=" + thisDocumentStore.getId());
		        documentStoreUserTeamInfo.setItemsPerPage(PagedListInfo.DEFAULT_ITEMS_PER_PAGE);
		        documentStoreUserTeamInfo.setColumnToSortBy("entered");
		        documentStoreUserTeamInfo.setSortOrder("desc");
	      
		    org.aspcfs.modules.documents.base.FileItemList files = new org.aspcfs.modules.documents.base.FileItemList();
	        files.setPagedListInfo(documentStoreUserTeamInfo);
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
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
	        documentStoreUserTeamInfo.setItemsPerPage(0);

	        PagedListInfo documentStoreEmployeeTeamInfo = this.getPagedListInfo(
	            context, "documentStoreEmployeeTeamInfo");
	        documentStoreEmployeeTeamInfo.setLink(
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
	        documentStoreEmployeeTeamInfo.setItemsPerPage(0);

	        PagedListInfo documentStoreAccountContactTeamInfo = this.getPagedListInfo(
	            context, "documentStoreAccountContactTeamInfo");
	        documentStoreAccountContactTeamInfo.setLink(
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
	        documentStoreAccountContactTeamInfo.setItemsPerPage(0);

	        PagedListInfo documentStoreRoleTeamInfo = this.getPagedListInfo(
	            context, "documentStoreRoleTeamInfo");
	        documentStoreRoleTeamInfo.setLink(
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
	        documentStoreRoleTeamInfo.setItemsPerPage(0);

	        PagedListInfo documentStoreDepartmentTeamInfo = this.getPagedListInfo(
	            context, "documentStoreDepartmentTeamInfo");
	        documentStoreDepartmentTeamInfo.setLink(
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
	        documentStoreDepartmentTeamInfo.setItemsPerPage(0);

	        PagedListInfo documentStorePortalUserTeamInfo = this.getPagedListInfo(
	            context, "documentStorePortalUserTeamInfo");
	        documentStorePortalUserTeamInfo.setLink(
	            "DocumentManagementAsl.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + thisDocumentStore.getId());
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
	          LookupList departmentList = new LookupList(db, "lookup_department");
	          thisMember.setUser(
	              departmentList.getValueFromId(thisMember.getItemId()));
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
