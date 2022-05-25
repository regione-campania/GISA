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
package org.aspcfs.modules.setup.utils;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.ServletContextUtils;

import javax.servlet.ServletContext;
import java.util.prefs.Preferences;

/**
 * A class for loading preferences, only called when the initHook does not
 * find any prefs in web.xml. Used for the initial setup code.
 *
 * @author matt rajkowski
 * @version $Id: Prefs.java 24313 2007-12-09 12:42:47Z srinivasar@cybage.com $
 * @created August 12, 2003
 */
public class Prefs {

  public static String retrieveContextPrefName(ServletContext context) {
    String dir = ApplicationPrefs.getRealPath(context);
    try {
      // Some containers return null so use the specified instance
      if (dir == null) {
        dir = ServletContextUtils.loadText(context, "/WEB-INF/instance.property");
      }
      // Apache Geronimo without a plan file uses a temporary store for a webapp
      // which would prevent a redeployed/upgraded Centric from finding itself
      if (dir != null && dir.indexOf("config-store") > -1) {
        dir = ServletContextUtils.loadText(context, "/WEB-INF/instance.property");
      }
      // Apache Geronimo with a plan file and a module artifactId can use the
      // artifactId to differentiate
      if (dir != null && dir.indexOf("repository/default") > -1) {
        int uniqueIdStart = dir.indexOf("repository/default") + 19;
        dir = dir.substring(uniqueIdStart, dir.indexOf("/", uniqueIdStart));
      }
      System.out.println("Prefs-> Instance name: " + dir);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return dir;
  }


  /**
   * Saves the specified preference name and value to the store
   *
   * @param name  Description of the Parameter
   * @param value Description of the Parameter
   * @return Description of the Return Value
   */
  public static boolean savePref(String name, String value) {
    try {
      Preferences prefs = Preferences.userNodeForPackage(Prefs.class);
      if (name.length() <= Preferences.MAX_KEY_LENGTH) {
        prefs.put(name, value);
      } else {
        prefs.put(name.substring(name.length() - Preferences.MAX_KEY_LENGTH), value);
      }
      prefs.flush();
      return true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }
}

