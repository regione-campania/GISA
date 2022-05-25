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

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.AccessLogList;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.PermissionCategoryList;
import org.aspcfs.modules.admin.base.UsageList;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactType;
import org.aspcfs.modules.contacts.base.ContactTypeList;
import org.aspcfs.modules.system.base.ApplicationVersion;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListElement;
import org.aspcfs.utils.web.LookupListList;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemVersionList;

/**
 * Admin actions
 *
 * @author chris
 * @version $Id: Admin.java 24287 2007-12-09 11:28:24Z srinivasar@cybage.com $
 * @created January 7, 2002
 */
public final class Admin extends CFSModule {

  /**
   * Default action, calls home action.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    return executeCommandHome(context);
  }


  /**
   * Home.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Admin", "Admin");
    return ("HomeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandManage(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Management");
    return "ManagementOK";
  }


  /**
   * Action that prepares system usage data
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUsage(ActionContext context) {
    if (!hasPermission(context, "admin-usage-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Usage");
    Connection db = null;
    ArrayList usageList = new ArrayList();
    ArrayList usageList2 = new ArrayList();
    String rangeSelect = context.getRequest().getParameter("rangeSelect");
    java.sql.Timestamp dateStart = null;
    String dateStartParam = context.getRequest().getParameter("dateStart");
    if (dateStartParam != null) {
      dateStart = DatabaseUtils.parseDateToTimestamp(dateStartParam);
      if (dateStart != null) {
        rangeSelect = "custom";
      }
    }
    java.sql.Timestamp dateEnd = null;
    String dateEndParam = context.getRequest().getParameter("dateEnd");
    if (dateEndParam != null) {
      dateEnd = DatabaseUtils.parseDateToTimestamp(dateEndParam);
      if (dateEnd != null) {
        rangeSelect = "custom";
      }
    }
    context.getRequest().setAttribute("rangeSelect", rangeSelect);
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      Calendar cal = Calendar.getInstance();
      //Date Start Range
      if (dateStart != null) {
        cal.setTimeInMillis(dateStart.getTime());
      }
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      long startRange = cal.getTimeInMillis();
      context.getRequest().setAttribute(
          "dateStart", new java.sql.Timestamp(cal.getTimeInMillis()));
      //Date End Range
      if (dateStart != null && dateEnd != null && dateEnd.after(dateStart)) {
        cal.setTimeInMillis(dateEnd.getTime());
      }
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      cal.set(Calendar.MILLISECOND, 999);
      long endRange = cal.getTimeInMillis();
      context.getRequest().setAttribute(
          "dateEnd", new java.sql.Timestamp(cal.getTimeInMillis()));

      NumberFormat nf = NumberFormat.getInstance();
      db = this.getConnection(context);

      //Users enabled as of date range
      UserList userList = new UserList();
      userList.setEnabled(Constants.TRUE);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      int userListCount = userList.queryRecordCount(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (userListCount == 1) {
          logEntry = systemStatus.getLabel("admin.usage.userEnabled.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.usersEnabled.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(userListCount));
          usageList.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList.add(
              nf.format(userListCount) + " enabled user" + StringUtils.addS(
                  userListCount));
        }
      } else {
        usageList.add(
            nf.format(userListCount) + " enabled user" + StringUtils.addS(
                userListCount));
      }

      //File count: x size: x
      FileItemVersionList fileList = new FileItemVersionList();
      int fileCount = fileList.queryRecordCount(db);
      long fileSize = fileList.queryFileSize(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (fileCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.oneFileStoredDocumentLibraryWithSize.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesStoredDocumentLibraryWithSize.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileCount));
          map.put("${size}", nf.format(fileSize));
          usageList.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList.add(
              nf.format(fileCount) + " file" + StringUtils.addS(fileCount) + " stored in document library using " + nf.format(
                  fileSize) + " megabyte" + StringUtils.addS(fileSize) + " of storage");
        }
      } else {
        usageList.add(
            nf.format(fileCount) + " file" + StringUtils.addS(fileCount) + " stored in document library using " + nf.format(
                fileSize) + " megabyte" + StringUtils.addS(fileSize) + " of storage");
      }

      //Logins
      AccessLogList accessLog = new AccessLogList();
      accessLog.setEnteredRangeStart(new java.sql.Timestamp(startRange));
      accessLog.setEnteredRangeEnd(new java.sql.Timestamp(endRange));
      int logins = accessLog.queryRecordCount(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (logins == 1) {
          logEntry = systemStatus.getLabel("admin.usage.login.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.logins.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(logins));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(logins) + " login" + StringUtils.addS(logins));
        }
      } else {
        usageList2.add(
            nf.format(logins) + " login" + StringUtils.addS(logins));
      }

      //Prepare to get usage for several different actions
      UsageList usage = new UsageList();
      usage.setEnteredRangeStart(new java.sql.Timestamp(startRange));
      usage.setEnteredRangeEnd(new java.sql.Timestamp(endRange));

      //Upstream bw: x, the specific files uploaded on date x size
      usage.setAction(Constants.USAGE_FILE_UPLOAD);
      usage.buildUsage(db);
      long fileUploadCount = usage.getCount();
      long fileUploadSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (fileUploadCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.fileUploadedBandwidth.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesUploadedBandwidth.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileUploadCount));
          map.put("${size}", nf.format(fileUploadSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(fileUploadCount) + " file" + StringUtils.addS(
                  fileUploadCount) + " uploaded, using " + nf.format(
                      fileUploadSize) + " byte" + StringUtils.addS(
                          fileUploadSize) + " of bandwidth");
        }
      } else {
        usageList2.add(
            nf.format(fileUploadCount) + " file" + StringUtils.addS(
                fileUploadCount) + " uploaded, using " + nf.format(
                    fileUploadSize) + " byte" + StringUtils.addS(
                        fileUploadSize) + " of bandwidth");
      }

      //Downstream bw: x
      usage.setAction(Constants.USAGE_FILE_DOWNLOAD);
      usage.buildUsage(db);
      long fileDownloadCount = usage.getCount();
      long fileDownloadSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (fileDownloadCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.fileDownloadedBandwidth.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesDownloadedBandwidth.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileDownloadCount));
          map.put("${size}", nf.format(fileDownloadSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(fileDownloadCount) + " file" + StringUtils.addS(
                  fileDownloadCount) + " downloaded, using " + nf.format(
                      fileDownloadSize) + " byte" + StringUtils.addS(
                          fileDownloadSize) + " of bandwidth");
        }
      } else {
        usageList2.add(
            nf.format(fileDownloadCount) + " file" + StringUtils.addS(
                fileDownloadCount) + " downloaded, using " + nf.format(
                    fileDownloadSize) + " byte" + StringUtils.addS(
                        fileDownloadSize) + " of bandwidth");
      }

      //Communications Manager emails
      usage.setAction(Constants.USAGE_COMMUNICATIONS_EMAIL);
      usage.buildUsage(db);
      long emailRecipientCount = usage.getCount();
      long emailSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (emailRecipientCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.communicationManagerOneEmail.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.communicationManagerEmails.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(emailRecipientCount));
          map.put("${size}", nf.format(emailSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(emailRecipientCount) + " email" + StringUtils.addS(
                  emailRecipientCount) + " sent, consisting of " + nf.format(
                      emailSize) + " byte" + StringUtils.addS(emailSize));
        }
      } else {
        usageList2.add(
            nf.format(emailRecipientCount) + " email" + StringUtils.addS(
                emailRecipientCount) + " sent, consisting of " + nf.format(
                    emailSize) + " byte" + StringUtils.addS(emailSize));
      }

      //Communications Manager faxes
      usage.setAction(Constants.USAGE_COMMUNICATIONS_FAX);
      usage.buildUsage(db);
      long faxRecipientCount = usage.getCount();
      long faxSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (faxRecipientCount == 1) {
          logEntry = systemStatus.getLabel("admin.usage.oneFaxSent.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.faxsSent.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(faxRecipientCount));
          map.put("${size}", nf.format(faxSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(faxRecipientCount) + " fax" + StringUtils.addES(
                  faxRecipientCount) + " sent, consisting of  " + nf.format(
                      faxSize) + " byte" + StringUtils.addS(faxSize));
        }
      } else {
        usageList2.add(
            nf.format(faxRecipientCount) + " fax" + StringUtils.addES(
                faxRecipientCount) + " sent, consisting of  " + nf.format(
                    faxSize) + " byte" + StringUtils.addS(faxSize));
      }

      context.getRequest().setAttribute("usageList", usageList);
      context.getRequest().setAttribute("usageList2", usageList2);

      //Application Version
      String appVersion = ApplicationVersion.VERSION;
      context.getRequest().setAttribute("applicationVersion", appVersion);

      //Database Version
      String dbVersion = DatabaseVersion.getLatestVersion(db);
      context.getRequest().setAttribute("databaseVersion", dbVersion);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "UsageOK";
  }


  /**
   * Action that prepares a list of modules that can be configured
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandConfig(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    PermissionCategoryList thisPermCatList = new PermissionCategoryList();

    try {
      db = this.getConnection(context);
      thisPermCatList.setEnabledState(Constants.TRUE);
      thisPermCatList.setCustomizableModulesOnly(true);
      thisPermCatList.buildList(db);
      context.getRequest().setAttribute(
          "permissionCategoryList", thisPermCatList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ConfigurationOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   * Action that prepares a list of specific module items that can be
   * configured.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigDetails(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      return ("ConfigDetailsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Action that prepares a list of the configurable module lookup lists
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandEditLists(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-lists-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    String table = "" ;
    	if (context.getRequest().getParameter("table")!=null)
    		table = context.getRequest().getParameter("table") ;
    	else 
    		table = (String)context.getRequest().getAttribute("table");
    	
    	context.getRequest().setAttribute("table",table);
    try {
      db = this.getConnection(context);
     
      buildFormElements(context, db);
      addModuleBean(context, "Configuration", "Configuration");
      return ("EditListsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }


 


  /**
   * Updates the Contact Types list .<br>
   * Note : Treated as a special case of Lookup List as it has category &
   * userId associated.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateContactList(ActionContext context) {
    Connection db = null;
    String category = context.getRequest().getParameter("category");
    String[] params = context.getRequest().getParameterValues("selectedList");
    String[] names = new String[params.length];
    int j = 0;
    StringTokenizer st = new StringTokenizer(
        context.getRequest().getParameter("selectNames"), "^");
    while (st.hasMoreTokens()) {
      names[j] = (String) st.nextToken();
      j++;
    }
    try {
      db = this.getConnection(context);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Admin -- > Updating Contact Types ");
      }
      ContactTypeList compareList = new ContactTypeList();
      compareList.setCategory(category);
      compareList.setIncludeDefinedByUser(this.getUserId(context));
      compareList.buildList(db);
      ContactTypeList newList = new ContactTypeList(params, names);
      Iterator i = compareList.iterator();
      while (i.hasNext()) {
        ContactType thisType = (ContactType) i.next();
        //not there, disable it, leave it
        if (newList.getElement(thisType.getId()) == null) {
          thisType.setEnabled(db, false);
        }
      }
      Iterator k = newList.iterator();
      while (k.hasNext()) {
        ContactType thisType = (ContactType) k.next();
        thisType.setCategory(category);
        if (thisType.getId() == 0) {
          thisType.insert(db);
        } else {
          thisType.setNewOrder(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "moduleId", context.getRequest().getParameter("module"));
    addModuleBean(context, "Configuration", "Configuration");
    return (executeCommandEditLists(context));
  }


  public String executeCommandUpdateList(ActionContext context) {
	    if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
	      return ("PermissionError");
	    }
	    String tblName = context.getRequest().getParameter("tableName");
	  
	    String[] params = context.getRequest().getParameterValues("selectedList");
	    String[] names = new String[params.length];
	    int j = 0;
	    StringTokenizer st = new StringTokenizer(
	        context.getRequest().getParameter("selectNames"), "^");
	    while (st.hasMoreTokens()) {
	      names[j] = (String) st.nextToken();
	      j++;
	    }
	    Connection db = null;
	    try {
	      db = this.getConnection(context);
	      //begin for all lookup lists
	      LookupList compareList = new LookupList(db, tblName);
	      LookupList newList = new LookupList(params, names);
	      Iterator i = compareList.iterator();
	      while (i.hasNext()) {
	        LookupElement thisElement = (LookupElement) i.next();
	        //still there, stay enabled, don't re-insert it
	        if (System.getProperty("DEBUG") != null) {
	          System.out.println(
	              "Here: " + thisElement.getCode() + " " + newList.getSelectedValue(
	                  thisElement.getCode()));
	        }
	        //not there, disable it, leave it
	        if (newList.getSelectedValue(thisElement.getCode()).equals("") ||
	            newList.getSelectedValue(thisElement.getCode()) == null) {
	          thisElement.disableElement(db, tblName);
	        }
	      }
	      Iterator k = newList.iterator();
	      while (k.hasNext()) {
	        LookupElement thisElement = (LookupElement) k.next();
	        if (thisElement.getCode() == 0) {
	          int thisCode = -1;
	          if ((thisCode = thisElement.isDisabled(db, tblName)) != -1) {
	            thisElement.setCode(thisCode);
	            thisElement.enableElement(db, tblName);
	            thisElement.setNewOrder(db, tblName);
	          } else {
	            thisElement.insertElement(db, tblName);
	          }
	        } else {
	          thisElement.setNewOrder(db, tblName);
	          if (!thisElement.getDescription().equals(
	              compareList.getValueFromId(thisElement.getCode()))) {
	            thisElement.setNewDescription(db, tblName);
	          }
	        }
	      }
	      //invalidate the cache for this list
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      systemStatus.removeLookup(tblName);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute(
	        "moduleId", context.getRequest().getParameter("module"));
	    addModuleBean(context, "Configuration", "Configuration");
	    context.getRequest().setAttribute(
	            "table", context.getRequest().getParameter("tableName"));
	    return (executeCommandEditLists(context));
	  }


/**
 * Action that prepares the selected lookup list for modification
 *
 * @param context Description of Parameter
 * @return Description of the Returned Value
 */
public String executeCommandModifyList(ActionContext context) {
  if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
    return ("PermissionError");
  }
  Connection db = null;
  int moduleId = -1;
  int lookupId = -1;
  LookupList selectedList = null;
  try {
    db = this.getConnection(context);
  String table =context.getParameter("table");
    
    
    LookupList thisList = new LookupList(
        db,table);
   
    selectedList = thisList;
//    SWITCH (THISLIST.GETCATEGORYID()) {
//      CASE PERMISSIONCATEGORY.PERMISSION_CAT_CONTACTS:
//        IF (LOOKUPID == PERMISSIONCATEGORY.LOOKUP_CONTACTS_TYPE) {
//          CONTEXT.GETREQUEST().SETATTRIBUTE(
//              "CATEGORY", STRING.VALUEOF(CONTACTTYPE.GENERAL));
//        }
//        BREAK;
//      CASE PERMISSIONCATEGORY.PERMISSION_CAT_ACCOUNTS:
//        IF (LOOKUPID == PERMISSIONCATEGORY.LOOKUP_ACCOUNTS_CONTACTS_TYPE) {
//          CONTEXT.GETREQUEST().SETATTRIBUTE(
//              "CATEGORY", STRING.VALUEOF(CONTACTTYPE.ACCOUNT));
//        }
//        BREAK;
//      DEFAULT:
//        BREAK;
//    }
    context.getRequest().setAttribute("moduleId", String.valueOf(moduleId));
    context.getRequest().setAttribute("SelectedList", selectedList);
//    context.getRequest().setAttribute("SubTitle", thisList.getDescription());
  } catch (Exception e) {
    context.getRequest().setAttribute("Error", e);
    return ("SystemError");
  } finally {
    this.freeConnection(context, db);
  }
  if (selectedList != null) {
    selectedList.setSelectSize(8);
    selectedList.setMultiple(true);
  }
  return ("ModifyListOK");
}


  /**
   * Build all the necessarry form elements (lists)
   *
   * @param context Description of Parameter
   * @param db      Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
   
	  
	  
	  String table = "" ;
	  if (context.getRequest().getParameter("table")!=null)
  		table = context.getRequest().getParameter("table") ;
  	else 
  		table = (String)context.getRequest().getAttribute("table"); 
	  SystemStatus systemStatus = this.getSystemStatus(context);
    LookupListList thisList = new LookupListList();
   LookupListElement elem = new LookupListElement();
   elem.setTableName(table);
   elem.buildLookupList(db);
   
   thisList.add(elem);
    
    context.getRequest().setAttribute("LookupLists", thisList);
  }

  public String getLabel(SystemStatus systemStatus, HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }
}

