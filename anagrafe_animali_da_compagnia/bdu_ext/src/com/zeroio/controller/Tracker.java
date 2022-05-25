/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.zeroio.controller;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: Tracker.java,v 2.4 2006/02/22 14:25:35 matt Exp $
 * @created September 30, 2004
 */
public class Tracker {

  private final static int ADD = 1;
  private final static int REMOVE = -1;
  private int guestCount = 0;
  private int userCount = 0;
  private LinkedHashMap users = new LinkedHashMap();
  private ArrayList guests = new ArrayList();


  /**
   * Description of the Method
   */
  public Tracker() {
  }


  /**
   * Gets the guestCount attribute of the Tracker object
   *
   * @return The guestCount value
   */
  public int getGuestCount() {
    return guestCount;
  }


  /**
   * Gets the userCount attribute of the Tracker object
   *
   * @return The userCount value
   */
  public int getUserCount() {
    return userCount;
  }


  /**
   * Description of the Method
   *
   * @param user      Description of the Parameter
   * @param sessionId Description of the Parameter
   */
  public void add(String sessionId, UserBean user) {
    adjustCount(sessionId, user, ADD);

  }


  /**
   * Description of the Method
   *
   * @param sessionId Description of the Parameter
   */
  public void remove(String sessionId) {
    adjustCount(sessionId, null, REMOVE);
  }


  /**
   * Description of the Method
   *
   * @param user       Description of the Parameter
   * @param adjustment Description of the Parameter
   * @param sessionId  Description of the Parameter
   */
  private synchronized void adjustCount(String sessionId, UserBean user, int adjustment) {
    if (user != null) {
      if (user.getUserId() > 0) {
        TrackerElement element = new TrackerElement(user.getUserId());
        users.put(sessionId, element);
        userCount += adjustment;
        if (userCount < 0) {
          userCount = 0;
        }
      } else {
        guests.add(sessionId);
        guestCount += adjustment;
        if (guestCount < 0) {
          guestCount = 0;
        }
      }
    } else {
      if (users.containsKey(sessionId)) {
        userCount += adjustment;
        if (userCount < 0) {
          userCount = 0;
        }
        users.remove(sessionId);
      }
      if (guests.contains(sessionId)) {
        guestCount += adjustment;
        if (guestCount < 0) {
          guestCount = 0;
        }
        guests.remove(sessionId);
      }
    }
  }


  /**
   * Gets the totalMembers attribute of the Tracker object
   *
   * @param request Description of the Parameter
   * @return The totalMembers value
   */
  public int getTotalMembers(HttpServletRequest request) {
    try {
      UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
      return ((SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute(
          "SystemStatus")).get(thisUser.getConnectionPoll().getUrl())).getUserList().size();
    } catch (Exception e) {
    }
    return -1;
  }

  public Collection getUserList() {
    return users.values();
  }
}

