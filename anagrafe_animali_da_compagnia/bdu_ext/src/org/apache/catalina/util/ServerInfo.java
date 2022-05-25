/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina.util;


import java.io.InputStream;
import java.util.Properties;


/**
 * Simple utility module to make it easy to plug in the server identifier
 * when integrating Tomcat.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 12404 $ $Date: 2005-08-05 19:37:07 +0200 (Fri, 05 Aug 2005) $
 */

public class ServerInfo {


  // ------------------------------------------------------- Static Variables


  /**
   * The server information String with which we identify ourselves.
   */
  private static String serverInfo = null;

  static {

    try {
      InputStream is = ServerInfo.class.getResourceAsStream
          ("/org/apache/catalina/util/ServerInfo.properties");
      Properties props = new Properties();
      props.load(is);
      is.close();
      serverInfo = props.getProperty("server.info");
    } catch (Throwable t) {
      ;
    }
    if (serverInfo == null) {
      serverInfo = "Apache Tomcat";
    }

  }


  // --------------------------------------------------------- Public Methods


  /**
   * Return the server identification for this version of Tomcat.
   */
  public static String getServerInfo() {

    return (serverInfo);

  }


}
