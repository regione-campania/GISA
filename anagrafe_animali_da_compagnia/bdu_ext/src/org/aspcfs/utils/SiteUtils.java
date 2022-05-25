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
package org.aspcfs.utils;


import com.darkhorseventures.database.ConnectionPool;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;

import java.sql.Connection;
import java.util.Map;

/**
 * Utilities to work with the sites that have been installed on the system
 *
 * @author matt rajkowski
 * @version $Id: SiteUtils.java 24321 2007-12-09 13:44:43Z srinivasar@cybage.com $
 * @created October 3, 2003
 */
public class SiteUtils {

  /**
   * Generates a list of sites depending on the configuration parameters
   * specified, generally a build.properties file config
   *
   * @return The siteList value
   */
  public static SiteList getSiteList(ApplicationPrefs prefs, ConnectionPool cp) {
    return SiteUtils.getSiteList(prefs.getPrefs(), cp);
  }

  public static SiteList getSiteList(ApplicationPrefs prefs, ConnectionPool cp, String vhost) {
    return SiteUtils.getSiteList(prefs.getPrefs(), cp, vhost);
  }

  /**
   * Generates a list of sites depending on the configuration parameters
   * specified, generally a build.properties file config
   *
   * @param config Description of the Parameter
   * @return The siteList value
   */
  public static SiteList getSiteList(Map config) {
    return getSiteList(config, null);
  }

//  public static SiteList getSiteList(Map config, ConnectionPool cp) {
//    return getSiteList(config, cp, (String) null);
//  }

  public static SiteList getSiteList(Map config, ConnectionPool cp, String vhost) {
    Connection dbSites = null;
    SiteList siteList = new SiteList();
    String appCode = (String) config.get("GATEKEEPER.APPCODE");
   // String baseName = (String) config.get("GATEKEEPER.URL");
  //  String baseNameVam = (String) config.get("GATEKEEPER.URLVAM");
//    String dbUser = (String) config.get("GATEKEEPER.USER");
//    String dbPass = (String) config.get("GATEKEEPER.PASSWORD");
//    String driver = (String) config.get("GATEKEEPER.DRIVER");
    try {
      if ("true".equals((String) config.get("WEBSERVER.ASPMODE"))) {
    //    if (cp != null) {
//          ConnectionElement ce = new ConnectionElement(
//              baseName, dbUser, dbPass);
         // ce.setDriver(driver);
        //  dbSites = cp.getConnection(ce);
    //    } else {
          //Build list of sites to process
        //  Class.forName(driver);
       //   dbSites = DatabaseUtils.getConnection(baseName, dbUser, dbPass);
  //      }
        siteList.setSiteCode(appCode);
        siteList.setEnabled(Constants.TRUE);
        siteList.setVirtualHost(vhost);
        siteList.buildList(dbSites);
//        if (cp == null) {
//          dbSites.close();
//        }
      } else {
        //This setup only allows one site so process it
        Site thisSite = new Site();
//        thisSite.setDatabaseDriver((String) config.get("GATEKEEPER.DRIVER"));
//        thisSite.setDatabaseUrl((String) config.get("GATEKEEPER.URL"));
//        thisSite.setDatabaseName((String) config.get("GATEKEEPER.DATABASE"));
//        thisSite.setDatabaseUsername((String) config.get("GATEKEEPER.USER"));
//        thisSite.setDatabasePassword(
//            (String) config.get("GATEKEEPER.PASSWORD"));
        
//        thisSite.setDatabaseNameVam((String) config.get("GATEKEEPER.DATABASEVAM"));
//        thisSite.setDatabaseUrlVam((String) config.get("GATEKEEPER.URLVAM"));
//        thisSite.setDatabaseUsernameVam((String) config.get("GATEKEEPER.USERVAM"));
//        thisSite.setDatabasePasswordVam(
//            (String) config.get("GATEKEEPER.PASSWORDVAM"));
        
        
        thisSite.setSiteCode((String) config.get("GATEKEEPER.APPCODE"));
        thisSite.setVirtualHost((String) config.get("WEBSERVER.URL"));
        thisSite.setLanguage((String) config.get("SYSTEM.LANGUAGE"));
        siteList.add(thisSite);
      }
    } catch (Exception e) {
      return null;
    } finally {
//      if (cp != null) {
//        cp.free(dbSites);
//      }
    }
    return siteList;
  }


//  public static SiteList getSiteList(ApplicationPrefs prefs, ConnectionPool cp) {
//    return SiteUtils.getSiteList(prefs.getPrefs(), cp);
//  }


  public static SiteList getSiteList(Map config, ConnectionPool cp) {
    Connection dbSites = null;
    SiteList siteList = new SiteList();
    String appCode = (String) config.get("GATEKEEPER.APPCODE");
    String baseName = (String) config.get("GATEKEEPER.URL");
//    String dbUser = (String) config.get("GATEKEEPER.USER");
//    String dbPass = (String) config.get("GATEKEEPER.PASSWORD");
//    String driver = (String) config.get("GATEKEEPER.DRIVER");
    try {
      if ("true".equals((String) config.get("WEBSERVER.ASPMODE"))) {
        if (cp != null) {
//          ConnectionElement ce = new ConnectionElement(
//              baseName, dbUser, dbPass);
//          ce.setDriver(driver);
//          dbSites = cp.getConnection(ce);
        } else {
          //Build list of sites to process
//          Class.forName(driver);
//          dbSites = DatabaseUtils.getConnection(baseName, dbUser, dbPass);
        }
        siteList.setSiteCode(appCode);
       // siteList.setDbHost(ceToCompare.getUrl());
        siteList.setEnabled(Constants.TRUE);
        siteList.buildList(dbSites);
        if (cp == null) {
          dbSites.close();
        }
      } else {
        //This setup only allows one site so process it
        Site thisSite = new Site();
        thisSite.setDatabaseDriver((String) config.get("GATEKEEPER.DRIVER"));
        thisSite.setDatabaseUrl((String) config.get("GATEKEEPER.URL"));
//        thisSite.setDatabaseName((String) config.get("GATEKEEPER.DATABASE"));
//        thisSite.setDatabaseUsername((String) config.get("GATEKEEPER.USER"));
//        thisSite.setDatabasePassword(
//            (String) config.get("GATEKEEPER.PASSWORD"));
        thisSite.setSiteCode((String) config.get("GATEKEEPER.APPCODE"));
        thisSite.setVirtualHost((String) config.get("WEBSERVER.URL"));
        thisSite.setLanguage((String) config.get("SYSTEM.LANGUAGE"));
        siteList.add(thisSite);
      }
    } catch (Exception e) {
      return null;
    } finally {
      if (cp != null) {
        cp.free(dbSites, null);
      }
    }
    return siteList;
  }

}
