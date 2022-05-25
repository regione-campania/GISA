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
 * Description of the Class
 *
 * @author kevin duffey
 * @author matt rajkowski
 * @version $Id: Beans.java 24274 2007-12-09 09:49:18Z srinivasar@cybage.com $
 * @created July 1, 2001
 */
public final class Beans {
  private String beanName = "";
  private String className = "";
  private int beanScope = 0;
  private boolean defaultBean = false;

  // true if default bean


  /**
   * Constructor for the Beans object
   *
   * @param beanName    Description of the Parameter
   * @param beanScope   Description of the Parameter
   * @param className   Description of the Parameter
   * @param defaultBean Description of the Parameter
   */
  public Beans(String beanName, int beanScope, String className, boolean defaultBean) {
    this.beanName = beanName;
    this.beanScope = beanScope;
    this.className = className;
    this.defaultBean = defaultBean;
  }


  /**
   * Gets the defaultBean attribute of the Beans object
   *
   * @return The defaultBean value
   */
  public boolean isDefaultBean() {
    return defaultBean;
  }


  /**
   * Gets the className attribute of the Beans object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the beanName attribute of the Beans object
   *
   * @return The beanName value
   */
  public String getBeanName() {
    return beanName;
  }


  /**
   * Gets the beanScope attribute of the Beans object
   *
   * @return The beanScope value
   */
  public int getBeanScope() {
    return beanScope;
  }
}

