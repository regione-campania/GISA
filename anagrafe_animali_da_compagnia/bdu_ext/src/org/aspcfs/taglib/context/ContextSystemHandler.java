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
package org.aspcfs.taglib.context;

import java.io.IOException;

import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: BrowserHandler.java,v 1.4 2003/03/10 15:22:05 mrajkowski Exp
 *          $
 * @created January 15, 2003
 */
public class ContextSystemHandler extends TagSupport  {
  private String contextId = null;
  private double minVersion = -1;
  private double maxVersion = -1;
  private String os = null;
  private boolean include = true;

  
  /**
   * Sets the id attribute of the BrowserHandler object
   *
   * @param tmp The new id value
   */
  public final void setId(String tmp) {
	  contextId = tmp;
  }
  

  /**
   * Sets the minVersion attribute of the BrowserHandler object
   *
   * @param tmp The new minVersion value
   */
  public final void setMinVersion(String tmp) {
    minVersion = Double.parseDouble(tmp);
  }


  /**
   * Sets the maxVersion attribute of the BrowserHandler object
   *
   * @param tmp The new maxVersion value
   */
  public final void setMaxVersion(String tmp) {
    maxVersion = Double.parseDouble(tmp);
  }


  /**
   * Sets the os attribute of the BrowserHandler object
   *
   * @param tmp The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   * Sets the include attribute of the BrowserHandler object
   *
   * @param tmp The new include value
   */
  public final void setInclude(String tmp) {
    include = tmp.equalsIgnoreCase("true");
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
	
	  String[] contesti =contextId.split(";");
	  
	for(int i = 0 ; i < contesti.length; i++)
	{
		if (contesti[i].equalsIgnoreCase(pageContext.getServletContext().getServletContextName())) {
			 return EVAL_BODY_INCLUDE;
		}
	}
   
      
        return SKIP_BODY;
      
    
  }

  

}

