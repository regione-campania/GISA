/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: PagedListAlphabeticalLinksHandler.java 18421 2007-01-11 19:05:45Z matt $
 * @created September 16, 2004
 */
public class PagedListAlphabeticalLinksHandler extends TagSupport implements TryCatchFinally {
  private String name = "alphabeticalLinksProperties";
  private String object = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    name = "alphabeticalLinksProperties";
    object = null;
  }

  /**
   * Sets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @param tmp The new object value
   */
  public void setObject(String tmp) {
    this.object = tmp;
  }


  /**
   * Sets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the name attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the object attribute of the PagedListAlphabeticalLinksHandler object
   *
   * @return The object value
   */
  public String getObject() {
    return object;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      PagedListInfo pagedListInfo = (PagedListInfo) pageContext.getSession().getAttribute(
          object);
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      if (ce == null) {
        System.out.println(
            "PagedListStatusHandler-> ConnectionElement is null");
      }
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      if (systemStatus == null) {
        System.out.println("PagedListStatusHandler-> SystemStatus is null");
      }
      if (systemStatus != null) {
        pagedListInfo.setLettersArray(
            systemStatus.getLettersArray("language.alphabet"));
      }

      JspWriter out = this.pageContext.getOut();
      out.write(pagedListInfo.getAlphabeticalPageLinks());
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }
}

