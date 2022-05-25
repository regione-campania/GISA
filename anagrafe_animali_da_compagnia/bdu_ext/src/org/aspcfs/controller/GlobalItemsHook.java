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
package org.aspcfs.controller;


import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.servlets.ControllerGlobalItemsHook;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.actionplans.base.ActionStep;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Configures globally available items for CFS.
 *
 * @author mrajkowski
 * @version $Id: GlobalItemsHook.java,v 1.15 2002/12/23 16:12:28 mrajkowski
 *          Exp $
 * @created July 9, 2001
 */
public class GlobalItemsHook implements ControllerGlobalItemsHook {

  /**
   * Generates all of the HTML for the permissable items.
   *
   * @param request Description of Parameter
   * @param servlet Description of the Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String generateItems(Servlet servlet, HttpServletRequest request) {
    ConnectionPool ce = (ConnectionPool) request.getSession().getServletContext().getAttribute(
            "ConnectionPool");
    if (ce == null) {
      return null;
    }
    Hashtable systems = (Hashtable) servlet.getServletConfig().getServletContext().getAttribute(
            "SystemStatus");
    if (systems == null) {
      return null;
    }
    SystemStatus systemStatus = (SystemStatus) (systems).get(ce.getUrl());
    if (systemStatus == null) {
      return null;
    }
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    if (thisUser == null) {
      return null;
    }
    TimeZone timeZone = TimeZone.getTimeZone(
            thisUser.getUserRecord().getTimeZone());
//    int userId = thisUser.getUserId();
    int roleId = thisUser.getRoleId();
    int departmentId = thisUser.getUserRecord().getContact().getDepartment();
    int contactId = thisUser.getUserRecord().getContact().getId();

    //get today
    Calendar today = Calendar.getInstance(timeZone);
    today.set(Calendar.HOUR, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    //get tomorrow
    Calendar tomorrow = Calendar.getInstance(timeZone);
    tomorrow.set(Calendar.HOUR, 0);
    tomorrow.set(Calendar.MINUTE, 0);
    tomorrow.set(Calendar.SECOND, 0);
    tomorrow.set(Calendar.MILLISECOND, 0);
    tomorrow.add(Calendar.DAY_OF_MONTH, 1);

    StringBuffer items = new StringBuffer();

    //Site Search
    boolean isOfflineMode = Boolean.parseBoolean(ApplicationPrefs.getPref(servlet.getServletConfig().getServletContext(), "OFFLINE_MODE"));
    String om = isOfflineMode ? "-offline" : "";
  

    //My Items
   // if (systemStatus.hasPermission(userId, "globalitems-myitems-view" + om)) {
      ConnectionPool sqlDriver = (ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute(
              "ConnectionPool");
      Connection db = null;

      //Output
      items.append(
              "<!-- My Items -->" +
                      "<table class=\"globalItem\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                      "<tr><th>" + systemStatus.getLabel("myitems.header") + "</th></tr>" +
                      "<tr>" +
                      "<td nowrap>");



    //Recent Items
//    if (systemStatus.hasPermission(userId, "globalitems-recentitems-view" + om)) {
      if (systemStatus.hasPermission(roleId, "globalitems-recentitems-view" + om)) {
      items.append(
              "<!-- Recent Items -->" +
                      "<table class=\"globalItem\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
                      "<tr><th>" + systemStatus.getLabel("myitems.recentItems") + "</th></tr>" +
                      "<tr>" +
                      "<td>");

      ArrayList recentItems = (ArrayList) request.getSession().getAttribute(
              "RecentItems");
      if (recentItems != null) {
        Iterator i = recentItems.iterator();
        while (i.hasNext()) {
          RecentItem thisItem = (RecentItem) i.next();
          items.append(thisItem.getHtml());
          if (i.hasNext()) {
            items.append("<br>");
            //items.append("<hr color=\"#BFBFBB\" noshade>");
          }
        }
      } else {
        items.append(
                systemStatus.getLabel("myitems.noRecentItems") + "<br>&nbsp;<br>");
      }

      items.append(
              "</td>" +
                      "</tr>" +
                      "</table>");
    }

    if (items.length() > 0) {
      //If they have any modules, then create a cell to hold them...
      return (items.toString());
    } else {
      //No global items
      return "";
    }
  }


  /**
   * Description of the Method
   *
   * @param count Description of the Parameter
   * @return Description of the Return Value
   */
  private static String paint(int count) {
    if (count > 0) {
      return "<font color=\"red\">" + count + "</font>";
    } else {
      return "" + count;
    }
  }

}


