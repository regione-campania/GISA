/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessLogList;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.UserEmail;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.BatchInfo;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Methods for managing users
 *
 * @author     mrajkowski
 * @created    September 17, 2001
 * @version    $Id: Users.java 15115 2006-05-31 16:47:51 +0000 (Wed, 31 May
 *      2006) matt $
 */
public final class Users extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandListUsers(context);
  }


  /**
   *  Lists the users in the system that have a corresponding contact record
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
   */
  public String executeCommandListUsers(ActionContext context) {
    if (!hasPermission(context, "admin-users-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    Connection db = null;
    UserList list = new UserList();
    RoleList roleList = new RoleList();
    //Configure the pagedList
    PagedListInfo listInfo = getPagedListInfo(context, "UserListInfo");
    listInfo.setLink("Users.do?command=ListUsers");
    //Configure batch feature
    BatchInfo batchInfo = new BatchInfo("batchUsers");
    batchInfo.setAction("Users.do?command=ProcessBatch");

    try {
      db = getConnection(context);
      if ("disabled".equals(listInfo.getListView())) {
        list.setEnabled(UserList.FALSE);
        list.setIncludeAliases(Constants.UNDEFINED);
      } else if ("aliases".equals(listInfo.getListView())) {
        list.setEnabled(UserList.TRUE);
        list.setIncludeAliases(Constants.TRUE);
      } else {
        list.setEnabled(UserList.TRUE);
        list.setIncludeAliases(Constants.UNDEFINED);
      }
      list.setPagedListInfo(listInfo);
      list.setBuildContact(false);
      list.setBuildContactDetails(false);
      list.setBuildHierarchy(false);
      list.setRoleType(Constants.ROLETYPE_REGULAR);
      //fetch only regular users
      list.setRoleId(listInfo.getFilterKey("listFilter1"));
      list.setSiteId(this.getUserSiteId(context));
      list.buildList(db,super.getSuffiso(context));

      //build a list of roles
      roleList.setBuildUsers(false);
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.setEmptyHtmlSelectRecord(this.getSystemStatus(context).getLabel("accounts.any"));
      roleList.setJsEvent("onChange=\"javascript:document.userForm.submit();\"");
      roleList.buildList(db,super.getSuffiso(context));
      context.getRequest().setAttribute(
          "roleList", roleList);

      batchInfo.setSize(list.size());
      context.getRequest().setAttribute(
          "userListBatchInfo", batchInfo);

      context.getRequest().setAttribute(
          "systemStatus", this.getSystemStatus(context));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "Users", "User List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("UserList", list);
      return ("ListUsersOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandViewLog(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo listInfo = getPagedListInfo(context, "AccessLogInfo");
    listInfo.setLink(
        "Users.do?command=ViewLog&id=" + context.getRequest().getParameter(
        "id"));

    Connection db = null;
    AccessLogList list = new AccessLogList();
    int userId = -1;
    User newUser = null;

    if (context.getRequest().getParameter("id") != null) {
      userId = Integer.parseInt(context.getRequest().getParameter("id"));
    }

    try {
      db = getConnection(context);
      newUser = new User();
      newUser.setBuildContact(true);

      list.setUserId(userId);
      list.setPagedListInfo(listInfo);
      list.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "View User Details");
    if (errorMessage == null) {
      context.getRequest().setAttribute("UserRecord", newUser);
      context.getRequest().setAttribute("AccessLog", list);
      return ("ViewLogOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Action that loads a user for display
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
   */
  public String executeCommandUserDetails(ActionContext context) {

    if (!(hasPermission(context, "admin-users-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    String id = context.getRequest().getParameter("id");
    String action = context.getRequest().getParameter("action");

    Connection db = null;
    User thisUser = new User();

    try {
      db = this.getConnection(context);
      thisUser.setBuildContact(true);
      context.getRequest().setAttribute("UserRecord", thisUser);
      addRecentItem(context, thisUser);

      if (action != null && action.equals("modify")) {
        //RoleList roleList = new RoleList(db);
        //context.getRequest().setAttribute("RoleList", roleList);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (action != null && action.equals("modify")) {
        addModuleBean(context, "Users", "Modify User Details");
        return ("UserDetailsModifyOK");
      } else {
        addModuleBean(context, "Users", "View User Details");
        return ("UserDetailsOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   *  Action that generates the form data for inserting a new user
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.6
   */
  public String executeCommandInsertUserForm(ActionContext context) {
    if (!hasPermission(context, "admin-users-add")) {
      return ("PermissionError");
    }
    Connection db = null;
    addModuleBean(context, "Add User", "Add New User");
    try {
      db = this.getConnection(context);
      String value = CustomHook.populateUser(db, getPref(context, "FILELIBRARY"));
      if (value != null) {
        return value;
      }
      // Set some defaults if first time showing form
      User user = (User) context.getFormBean();
      if (user.getUsername() == null) {
        user.setHasHttpApiAccess(true);
        user.setHasWebdavAccess(true);
      }
      //Prepare the role drop-down
      RoleList roleList = new RoleList();
      roleList.setBuildUsers(false);
      roleList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("admin.users.pleaseSelect"));
      roleList.setRoleType(Constants.ROLETYPE_REGULAR);
      roleList.setEnabledState(Constants.TRUE);
      roleList.buildList(db,super.getSuffiso(context));
      context.getRequest().setAttribute("RoleList", roleList);
      //Prepare the user drop-down
      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      userList.setBuildContact(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setBuildContactDetails(false);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      //fetch users who have access to all sites.
      //A user assigned to a site may report to
      //another user who has access to the same site or to one
      //who has accesss to all sites
      userList.setSiteId(this.getUserSiteId(context));
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db,super.getSuffiso(context));
      context.getRequest().setAttribute("UserList", userList);
      LookupList siteid = new LookupList(db, "lookup_site_id");
      siteid.addItem(-1, this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("SiteIdList", siteid);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("UserInsertFormOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDisableUserConfirm(ActionContext context) {
    if (!hasPermission(context, "admin-users-delete")) {
      return ("PermissionError");
    }
    User thisUser = null;
    User managerUser = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisUser = new User();
      thisUser.setBuildContact(true);
     
      if (thisUser.getManagerId() > -1) {
        managerUser = new User();
        managerUser.setBuildContact(true);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Users", "Disable User");
    context.getRequest().setAttribute("User", thisUser);
    context.getRequest().setAttribute("ManagerUser", managerUser);
    return ("UserDisableConfirmOK");
  }


  /**
   *  fetches the user list based on site id
   *
   * @param  context  Description of Parameter
   * @return          Description of the Returned Value
   * @since           1.12
   */
  public String executeCommandReportsToJSList(ActionContext context) {

    String siteIdString = context.getRequest().getParameter("siteId");
    int siteId = -1;
    if ((siteIdString != null) || !"".equals(siteIdString)) {
      siteId = Integer.parseInt(siteIdString);
    }
    if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
      return ("PermissionError");
    }
    /*
     *  User user = this.getUser(context, this.getUserId(context));
     *  if (user.getSiteId() != -1 && user.getSiteId() != siteId) {
     *  return ("PermissionError");
     *  }
     */
    Connection db = null;
    try {
      db = this.getConnection(context);

      UserList userList = new UserList();
      userList.setEmptyHtmlSelectRecord(
          this.getSystemStatus(context).getLabel("calendar.none.4dashes"));
      userList.setBuildContact(false);
      userList.setBuildContactDetails(false);
      userList.setExcludeDisabledIfUnselected(true);
      userList.setExcludeExpiredIfUnselected(true);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);

      //fetch users from the specified site id and users who have
      //access to all sites. A user assigned to as site may report to
      //another user who has access to the same site or to one
      //who has accesss to all sites
      userList.setSiteId(siteId);
      userList.setIncludeUsersWithAccessToAllSites(true);
      userList.buildList(db,super.getSuffiso(context));
      HtmlSelect userListSelect = userList.getHtmlSelectObj("userId", getUserId(context));
      context.getRequest().setAttribute("UserListSelect", userListSelect);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return ("SiteJSUserListOK");
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandProcessBatch(ActionContext context) {
    if (!hasPermission(context, "admin-users-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      String batchName = context.getRequest().getParameter("batchId");
      String batchSize = context.getRequest().getParameter("batchSize");
      String action = context.getRequest().getParameter("action");
      String status = context.getRequest().getParameter("status");

      ArrayList selection = new ArrayList();

      for (int rowCount = 1; rowCount <= Integer.parseInt(batchSize); ++rowCount) {
        if (context.getRequest().getParameter(batchName + rowCount) != null) {
          String id = context.getRequest().
              getParameter(batchName + rowCount);

          if (!selection.contains(String.valueOf(id))) {
            selection.add(String.valueOf(id));
          }
          //Additional hidden parameters can be passed using the batch input handler
          //Process them based on requirement.
        }
      }

      for (int i = 0; i < selection.size(); i++) {
        int id = Integer.parseInt(
            (String) selection.get(i));
        User thisUser = new User(db, id);
        
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return (executeCommandListUsers(context));
  }


  /**
   *  Description of the Method
   *
   * @param  context         Description of the Parameter
   * @param  thisUser        Description of the Parameter
   * @param  modifiedByUser  Description of the Parameter
   * @param  template        Description of the Parameter
   * @param  password        Description of the Parameter
   * @return                 Description of the Return Value
   * @exception  Exception   Description of the Exception
   */
  private boolean sendEmail(ActionContext context, User thisUser, User modifiedByUser, String template, String password) throws Exception {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    SystemStatus systemStatus = this.getSystemStatus(context);
    String modifiedByUserName = (modifiedByUser.getContact() != null && modifiedByUser.getContact().getNameFull() != null? modifiedByUser.getContact().getNameFull():systemStatus.getLabel("campaign.superUser","Super User"));
    UserEmail userEmail = new UserEmail(context, thisUser, modifiedByUserName, password, systemStatus.getUrl(), template);
    // Prepare the email
    SMTPMessage mail = new SMTPMessage();
    mail.setHost(prefs.get("MAILSERVER"));
    mail.setFrom(prefs.get("EMAILADDRESS"));
    mail.addReplyTo(prefs.get("EMAILADDRESS"));
    mail.setType("text/html");
    mail.setSubject(userEmail.getSubject());
    mail.setBody(userEmail.getBody());
    if (thisUser.getContact().getPrimaryEmailAddress() != null && !"".equals(thisUser.getContact().getPrimaryEmailAddress())) {
      mail.addTo(thisUser.getContact().getPrimaryEmailAddress());
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ADDING: " + thisUser.getContact().getPrimaryEmailAddress());
      }
    }
    int result = mail.send();
    if (result == 2) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Users-> Send error: " + mail.getErrorMsg() + "\n");
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Users-> Sending message...");
      }
    }
    return true;
  }
}

