/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.web;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.ApplicationPrefs;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id: RequestUtils.java 12404 2005-08-05 17:37:07Z mrajkowski $
 * @created Apr 11, 2005
 */

public class RequestUtils {
  /**
   * Adds a feature to the LinkParams attribute of the HTTPUtils class
   *
   * @param request The feature to be added to the LinkParams attribute
   * @param tmp     The feature to be added to the LinkParams attribute
   * @return Description of the Return Value
   */
  public static String addLinkParams(HttpServletRequest request, String tmp) {
    String params = "";
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()) {
      String param = tokens.nextToken();
      if (request.getParameter(param) != null) {
        params += ("&" + param + "=" + request.getParameter(param));
      }
    }
    return params;
  }

  public static String getLink(ActionContext context, String url) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    boolean sslEnabled = "true".equals(prefs.get("ForceSSL"));
    if (sslEnabled) {
      return ("https://" + RequestUtils.getServerUrl(context.getRequest()) + "/" + url);
    } else {
      return ("http://" + RequestUtils.getServerUrl(context.getRequest()) + "/" + url);
    }
  }

  /**
   * Returns the server's url that was specified in the request, excluding the
   * scheme
   *
   * @param request Description of the Parameter
   * @return The serverURL value
   */
  public static String getServerUrl(HttpServletRequest request) {
    int port = request.getServerPort();
    return (request.getServerName() + (port != 80 && port != 443 ? ":" + String.valueOf(
        port) : "") + request.getContextPath());
  }
}
