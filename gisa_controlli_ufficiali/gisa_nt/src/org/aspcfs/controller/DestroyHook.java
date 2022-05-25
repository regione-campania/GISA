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
import com.darkhorseventures.framework.servlets.ControllerDestroyHook;
import org.aspcfs.apps.workFlowManager.WorkflowManager;

import javax.servlet.ServletConfig;

/**
 * Last chance to do something before the servlet is shut down
 *
 * @author mrajkowski
 * @version $Id: DestroyHook.java 24280 2007-12-09 10:34:18Z srinivasar@cybage.com $
 * @created July 30, 2001
 */
public class DestroyHook implements ControllerDestroyHook {

  ServletConfig config = null;


  /**
   * When the ServletController is initialized, this code creates a reference
   * to the servlet config.
   *
   * @param config The new Config value
   * @return Description of the Return Value
   * @since 1.0
   */
  public String executeControllerDestroyInit(ServletConfig config) {
    this.config = config;
    return null;
  }


  /**
   * Closes and removes the attributes that will need to be reloaded when the
   * framework starts back up, working backwards from the InitHook.
   *
   * @return Description of the Returned Value
   */
  public String executeControllerDestroy() {
    if (config != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Shutting down");
      }
      //Remove the workflow manager
      WorkflowManager wfManager =
          (WorkflowManager) config.getServletContext().getAttribute(
              "WorkflowManager");
      if (wfManager != null) {
        config.getServletContext().removeAttribute("WorkflowManager");
      }

      config.getServletContext().removeAttribute("SystemStatus");
      config.getServletContext().removeAttribute("DynamicFormList");
      config.getServletContext().removeAttribute("DynamicFormConfig");
      config.getServletContext().removeAttribute("ContainerMenuConfig");

      //Shutdown the ConnectionPool
      ConnectionPool cp =
          (ConnectionPool) config.getServletContext().getAttribute(
              "ConnectionPool");
      if (cp != null) {
        cp.closeAllConnections();
        cp.destroy();
        config.getServletContext().removeAttribute("ConnectionPool");
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Shutdown complete");
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DestroyHook-> Could not execute");
      }
    }
    return null;
  }

}

