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
package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: AccessLog.java 24287 2007-12-09 11:28:24Z srinivasar@cybage.com $
 * @created January 13, 2003
 */
public class AccessLog extends GenericBean {

  private int id = -1;
  private int userId = -1;
  private String username = "";
  private String ip = "";
  private String browser = "";
  private java.sql.Timestamp entered = null;

  private double access_position_lat ;
  private double access_position_lon ;
  private String access_position_err ;
  
  
  
  public void setAccess_position_lat(String lat)
  {
	  if (!lat.equals(""))
	  {
		  access_position_lat = Double.parseDouble(lat);
	  }
  }


  public void setAccess_position_lon(String lon)
  {
	  if (!lon.equals(""))
	  {
		  access_position_lon = Double.parseDouble(lon);
	  }
  }

  
  public double getAccess_position_lat() {
	return access_position_lat;
}


public void setAccess_position_lat(double access_position_lat) {
	this.access_position_lat = access_position_lat;
}


public double getAccess_position_lon() {
	return access_position_lon;
}


public void setAccess_position_lon(double access_position_lon) {
	this.access_position_lon = access_position_lon;
}


public String getAccess_position_err() {
	return access_position_err;
}


public void setAccess_position_err(String access_position_err) {
	this.access_position_err = access_position_err;
}



  /**
   * Constructor for the AccessLog object
   */
  public AccessLog() {
  }


  /**
   * Constructor for the AccessLog object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccessLog(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the AccessLog object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccessLog(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the AccessLog object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public AccessLog(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {

    if (id == -1) {
      throw new SQLException("Invalid Access Log ID");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT a.* " +
            "FROM access_log a " +
            "WHERE a.id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Access Log not found");
    }
  }


  /**
   * Gets the id attribute of the AccessLog object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the userId attribute of the AccessLog object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the username attribute of the AccessLog object
   *
   * @return The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   * Gets the ip attribute of the AccessLog object
   *
   * @return The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   * Gets the browser attribute of the AccessLog object
   *
   * @return The browser value
   */
  public String getBrowser() {
    return browser;
  }


  /**
   * Gets the entered attribute of the AccessLog object
   *
   * @return The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Sets the id attribute of the AccessLog object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the AccessLog object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the userId attribute of the AccessLog object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the AccessLog object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Sets the username attribute of the AccessLog object
   *
   * @param tmp The new username value
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   * Sets the ip attribute of the AccessLog object
   *
   * @param tmp The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   * Sets the browser attribute of the AccessLog object
   *
   * @param tmp The new browser value
   */
  public void setBrowser(String tmp) {
    this.browser = tmp;
  }


  /**
   * Sets the entered attribute of the AccessLog object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Gets the enteredString attribute of the AccessLog object
   *
   * @return The enteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db,ActionContext context) throws SQLException {
    if (userId == -1) {
      throw new SQLException(
          "Log Entry must be associated to a Concourse Suite Community Edition user");
    }
    StringBuffer sql = new StringBuffer();
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeqInt(db, "access_log_id_seq");
      sql.append("INSERT INTO access_log (user_id, username, ip,access_position_lat,access_position_lon,access_position_err,");
      if (id > -1) { 
        sql.append("id, ");
      }
      sql.append("entered, ");
      sql.append("browser ) ");
      sql.append("VALUES (?, ?, ?, ?,?,?,");
      if (id > -1) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      } else {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getUserId());
      pst.setString(++i, this.getUsername());
      pst.setString(++i, this.getIp());
      pst.setDouble(++i, this.getAccess_position_lat());
      pst.setDouble(++i, this.getAccess_position_lon());
      pst.setString(++i, this.getAccess_position_err());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      pst.setString(++i, this.getBrowser());
      pst.execute();
      pst.close();

       if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Access Log ID not specified.");
    }

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM access_log " +
            "WHERE id = ? ");
    pst.setInt(1, this.getId());
    pst.executeUpdate();
    pst.close();
    
    return true;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    userId = rs.getInt("user_id");
    username = rs.getString("username");
    ip = rs.getString("ip");
    browser = rs.getString("browser");
    entered = rs.getTimestamp("entered");
  }
}

