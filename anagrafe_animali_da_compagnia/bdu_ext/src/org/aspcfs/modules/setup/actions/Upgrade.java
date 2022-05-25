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
package org.aspcfs.modules.setup.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.system.base.ApplicationVersion;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Actions that facilitate upgrading an installation of Concourse Suite Community Edition
 *
 * @author matt rajkowski
 * @version $Id: Upgrade.java 24313 2007-12-09 12:42:47Z srinivasar@cybage.com $
 * @created June 16, 2004
 */
public class Upgrade extends CFSModule {

  /**
   * Prepares instruction page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    addModuleBean(context, null, "Upgrade");
    return "NeedUpgradeOK";
  }


  /**
   * Checks to see what version is installed and what the version should be
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCheck(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    // Check version info
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Something needs updating
      context.getRequest().setAttribute("status", "1");
    } else {
      // All ok
      context.getRequest().setAttribute("status", "0");
    }
    context.getRequest().setAttribute(
        "installedVersion", ApplicationVersion.getInstalledVersion(prefs));
    context.getRequest().setAttribute(
        "newVersion", ApplicationVersion.VERSION);
    return "CheckOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public synchronized String executeCommandPerformUpgrade(ActionContext context) {
    if (!isAdministrator(context)) {
      return "NeedUpgradeOK";
    }
    addModuleBean(context, null, "Upgrade");
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs.isUpgradeable()) {
      // Create a log of upgrade events
      ArrayList installLog = new ArrayList();
      context.getRequest().setAttribute("installLog", installLog);
      // Display upgrade information
      context.getRequest().setAttribute(
          "installedVersion", ApplicationVersion.getInstalledVersion(prefs));
      context.getRequest().setAttribute(
          "newVersion", ApplicationVersion.VERSION);
      Connection db = null;
      try {
        // Get a connection from the connection pool for this user
        db = this.getConnection(context);
        CustomHook.verifyDatabase(context, db, prefs, installLog, getDbNamePath(context));
        // Upgrade might be in a new place
        prefs.add(
          "WEB-INF", context.getServletContext().getRealPath("/") + "WEB-INF" + fs);
        if (!prefs.save()) {
          context.getRequest().setAttribute(
              "errorMessage", "No write permission on file library, build.properties");
          return "UpgradeERROR";
        }
      } catch (Exception e) {
        context.getRequest().setAttribute("errorMessage", e.getMessage());
        return "UpgradeERROR";
      } finally {
        //Always free the database connection
        this.freeConnection(context, db);
      }
    }
    return "UpgradeOK";
  }


  /**
   * Gets the administrator attribute of the Upgrade object
   *
   * @param context Description of the Parameter
   * @return The administrator value
   */
  private boolean isAdministrator(ActionContext context) {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    if (thisUser != null) {
      String status = (String) context.getSession().getAttribute("UPGRADEOK");
      return "UPGRADEOK".equals(status);
    }
    return false;
  }

}