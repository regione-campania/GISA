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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.database.ConnectionPool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: RoleSelectHandler.java,v 1.1.2.2 2004/04/08 14:55:53
 *          rvasista Exp $
 * @created June 19, 2003
 */
public class RoleSelectHandler extends TagSupport implements TryCatchFinally {

  private String name = null;
  private int value = -1;
  private String onChange = null;
  private boolean isPortalUser = false;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = null;
    value = -1;
    onChange = null;
    isPortalUser = false;
  }


  /**
   * Sets the name attribute of the RoleSelectHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the value attribute of the RoleSelectHandler object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = Integer.parseInt(tmp);
  }


  /**
   * Sets the value attribute of the RoleSelectHandler object
   *
   * @param tmp The new value value
   */
  public void setValue(int tmp) {
    this.value = tmp;
  }


  /**
   * Sets the onChange attribute of the RoleSelectHandler object
   *
   * @param tmp The new onChange value
   */
  public void setOnChange(String tmp) {
    this.onChange = tmp;
  }


  /**
   * Gets the isPortalUser attribute of the RoleSelectHandler object
   *
   * @return The isPortalUser value
   */
  public boolean getIsPortalUser() {
    return isPortalUser;
  }


  /**
   * Sets the isPortalUser attribute of the RoleSelectHandler object
   *
   * @param tmp The new isPortalUser value
   */
  public void setIsPortalUser(boolean tmp) {
    this.isPortalUser = tmp;
  }


  /**
   * Sets the isPortalUser attribute of the RoleSelectHandler object
   *
   * @param tmp The new isPortalUser value
   */
  public void setIsPortalUser(String tmp) {
    this.isPortalUser = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Description of the Method
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
        System.out.println("RoleHandler-> ConnectionElement is null");
      }
      boolean output = false;
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      if (systemStatus != null) {
        LookupList roleList = (LookupList) systemStatus.getLookupList(
            null, "lookup_project_role");
        if (roleList != null && isPortalUser) {
          roleList = (LookupList) roleList.clone();
          //Remove the Project Lead that has a level 10.
          roleList.removeElementByLevel(10);
        }
        if (roleList != null) {
          if (onChange != null) {
            roleList.setJsEvent("onChange=\"" + onChange + "\"");
          }
          this.pageContext.getOut().write(roleList.getHtmlSelect(name, value));
          output = true;
        }
      }
      if (!output) {
        this.pageContext.getOut().write("&nbsp;");
      }
    } catch (Exception e) {
      throw new JspException("RoleHandler Error: " + e.getMessage());
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

