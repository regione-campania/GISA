/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.zeroio.controller;

import java.sql.Timestamp;

/**
 * The following are kept track of
 *
 * @author matt rajkowski
 * @version $Id: TrackerElement.java,v 2.1 2005/12/29 13:38:47 matt Exp $
 * @created December 6, 2005
 */
public class TrackerElement {

  private int userId = -1;
  private Timestamp entered = null;


  public TrackerElement() {
  }

  public TrackerElement(int id) {
    userId = id;
    entered = new Timestamp(System.currentTimeMillis());
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Timestamp getEntered() {
    return entered;
  }

  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

}

