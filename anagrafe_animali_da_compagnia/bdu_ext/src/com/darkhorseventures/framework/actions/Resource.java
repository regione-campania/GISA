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
package com.darkhorseventures.framework.actions;

import java.io.Serializable;

/**
 * This class represents a single resource that is stored in the <code>Map
 * </code>for resources resource table. Each resource is the name of a resource
 * to forward to, and possibly the name of an XSL style-sheet to use to
 * transform the resource into a specific format, such as HTML, or XML
 *
 * @author kevin
 * @version $Id: Resource.java 24274 2007-12-09 09:49:18Z srinivasar@cybage.com $
 * @created October 8, 2000
 */
public class Resource
    implements Serializable {
  private String resourceName = "";
  private String resourceXSL = "";
  private String resourceContext = "";
  private boolean resourceRedirect = false;
//  private String resourcePermission = "";
  private String layoutType = null;
  final static long serialVersionUID = 7269086432553620255L;


  public Resource(String name, String xsl, String context, boolean redirect, String layout) {
    resourceName = name;
    resourceXSL = xsl;
    resourceContext = context;
    resourceRedirect = redirect;
    layoutType = layout;
 //   resourcePermission = resourcePerm;
  }


  /**
   * Return the name of the resource (usually the path/*.jsp JSP page name)
   *
   * @return The Name value
   */
  public String getName() {
    return resourceName;
  }


  /**
   * Returns the name of the XSL to use (usually the path/*.xsl name)
   *
   * @return The XSL value
   */
  public String getXSL() {
    return resourceXSL;
  }

  public String getContext() {
    return resourceContext;
  }


  /**
   * Returns whether or not the ControllerServlet should forward to this resource or send a
   * redirect response to the server indicating to redirect the user to this resource.
   *
   * @return boolean  true if this resource requires a redirection to get to, or false if forwarding is to be used
   */
  public boolean getRedirect() {
    return resourceRedirect;
  }

  /**
   * Returns the name of the layout to use
   *
   * @return The layout value
   */
  public String getLayout() {
    return layoutType;
  }


//public String getResourcePermission() {
//	return resourcePermission;
//}
//
//
//public void setResourcePermission(String resourcePermission) {
//	this.resourcePermission = resourcePermission;
//}
//  
  
  
}

