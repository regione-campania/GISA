/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.taglib;

//;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.StringUtils;

import com.darkhorseventures.database.ConnectionPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ProjectNameHandler.java,v 1.1.2.2 2004/04/08 14:55:53
 *          rvasista Exp $
 * @created January 21, 2004
 */
public class ProjectNameHandler extends TagSupport {

  private int projectId = -1;


  /**
   * Sets the id attribute of the ProjectNameHandler object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Sets the id attribute of the ProjectNameHandler object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Outputs the project name if found from the ProjectNameCache
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
//      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
//          "ConnectionElement");
    	
      ConnectionPool ce = (ConnectionPool) pageContext.getSession().getServletContext().getAttribute(
                "ConnectionPool");
      if (ce == null) {
        System.out.println("ProjectNameHandler-> ConnectionElement is null");
      }
      boolean output = false;
      Hashtable systemStatus = (Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus");
      if (systemStatus != null) {
        SystemStatus thisSystem = (SystemStatus) systemStatus.get(ce.getUrl());
        if (thisSystem != null) {
          HashMap projectCache = (HashMap) thisSystem.getObject(
              Constants.SYSTEM_PROJECT_NAME_LIST);
          if (projectCache != null) {
            String projectName = (String) projectCache.get(
                new Integer(projectId));
            if (projectName != null) {
              this.pageContext.getOut().write(StringUtils.toHtml(projectName));
              output = true;
            }
          }
        }
      }
      if (!output) {
        this.pageContext.getOut().write("&nbsp;");
      }
    } catch (Exception e) {
      throw new JspException("Username Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

