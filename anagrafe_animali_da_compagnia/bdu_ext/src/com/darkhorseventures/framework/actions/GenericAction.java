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

/**
 * This class is provided as a means to descend from, so that common generic
 * methods and fields can be placed here and all other action classes (that
 * descend from this one), inherit the common functionality. You may want to
 * provide a simple logging mechanism, helper methods that action classes might
 * find useful, and so on. note: If an action class you define will not be
 * descended by other classes, you should make it final, for the performance
 * gains a final class achieves.
 *
 * @author kevin duffey
 * @version $Id: GenericAction.java 24274 2007-12-09 09:49:18Z srinivasar@cybage.com $
 * @created July 9, 2001
 */

public class GenericAction implements java.io.Serializable {

  final static long serialVersionUID = -1388329390981035172L;


  /**
   * 0-arg constructor
   */
  public GenericAction() {
  }


  /**
   * This is the default call by all actions if a command= paramter is not
   * passed along with the request. Descendant classes should override this
   * method to carry out default behavior that should occure BEFORE the initial
   * JSP page is displayed. This is most useful when the initial page needs to
   * show a list of items that are populated from the database, such as a
   * drop-down list of activity for a client, etc.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return "DefaultOK";
  }

}

