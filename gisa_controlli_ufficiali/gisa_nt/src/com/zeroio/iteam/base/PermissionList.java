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
package com.zeroio.iteam.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
 
/**
 * Represents a list of permissions for a project
 *
 * @author matt rajkowski
 * @version $Id: PermissionList.java,v 1.1.2.2 2004/04/08 14:55:53 rvasista
 *          Exp $
 * @created August 10, 2003
 */
public class PermissionList extends HashMap implements SyncableList{

  public final static String tableName = "project_permissions";
  public final static String uniqueField = "id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int projectId = -1;


  /**
   * Constructor for the PermissionList object
   */
  public PermissionList() {  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }
 

  /**
   * Sets the projectId attribute of the PermissionList object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT lp.permission, p.userlevel " +
            "FROM project_permissions p, lookup_project_permission lp " +
            "WHERE p.permission_id = lp.code ");
    createFilter(sql);
    PreparedStatement pst = db.prepareStatement(sql.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      this.put(
          rs.getString("permission"), new Integer(rs.getInt("userlevel")));
    }
    rs.close();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (projectId > -1) {
      sqlFilter.append("AND project_id = ? ");
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND p.entered > ? ");
      }
      sqlFilter.append("AND p.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND p.modified > ? ");
      sqlFilter.append("AND p.entered < ? ");
      sqlFilter.append("AND p.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (projectId > -1) {
      pst.setInt(++i, projectId);
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param permissionName Description of the Parameter
   * @param userLevel      Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasPermission(String permissionName, int userLevel) {
    Integer value = (Integer) this.get(permissionName);

    return true;
  }


  /**
   * Gets the accessLevel attribute of the PermissionList object
   *
   * @param permissionName Description of the Parameter
   * @return The accessLevel value
   */
  public int getAccessLevel(String permissionName) {
    Integer value = (Integer) this.get(permissionName);
    if (value == null) {
      return 1;
    } else {
      return value.intValue();
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param request   Description of the Parameter
   * @param projectId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void updateProjectPermissions(Connection db, HttpServletRequest request, int projectId) throws SQLException {
    //Look through the request and put the permissions in buckets
   
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void delete(Connection db, int projectId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_permissions " +
            "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    pst.execute();
    pst.close();
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param projectId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void insertDefaultPermissions(Connection db, int projectId) throws SQLException {
    //Make sure no permissions exist, then insert
    PreparedStatement pst = db.prepareStatement(
        "SELECT count(*) AS perm_count " +
            "FROM project_permissions " +
            "WHERE project_id = ? ");
    pst.setInt(1, projectId);
    ResultSet rs = pst.executeQuery();
    rs.next();
    int count = rs.getInt("perm_count");
    rs.close();
    pst.close();
    //Insert the permissions
    
  }
}

