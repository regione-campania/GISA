/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.aziendeagricole.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.AddressList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.aziendeagricole.base.OrganizationAddress;
import org.aspcfs.utils.DatabaseUtils;

public class OrganizationAddressList extends AddressList {
	
	private static final long serialVersionUID = 1L;

  private static Logger log = Logger.getLogger(org.aspcfs.modules.aziendeagricole.base.OrganizationAddressList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }

  public final static String tableName = "organization_address";
  public final static String uniqueField = "address_id";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;

  /**
   *  Constructor for the OrganizationAddressList object
   *
   * @since    1.1
   */
  public OrganizationAddressList() {
  }

  /**
   *  Constructor for the OrganizationAddressList object
   *
   * @param  request  Description of the Parameter
   */
  public OrganizationAddressList(HttpServletRequest request) {
    int i = 0;
    int primaryAddress = -1;
    if (request.getParameter("primaryAddress") != null) {
      primaryAddress = Integer.parseInt(
          (String) request.getParameter("primaryAddress"));
    }
    while (request.getParameter("address" + (++i) + "type") != null) {
      OrganizationAddress thisAddress = new OrganizationAddress();
      thisAddress.buildRecord(request, i);
      if (primaryAddress == i) {
        thisAddress.setPrimaryAddress(true);
      }
      if (thisAddress.isValid()) {
        this.addElement(thisAddress);
      }
    }
  }


  /**
   *  Gets the tableName attribute of the OrganizationAddressList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OrganizationAddressList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the OrganizationAddressList object
   *
   * @return    The lastAnchor value
   */
  public java.sql.Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the OrganizationAddressList object
   *
   * @return    The nextAnchor value
   */
  public java.sql.Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the OrganizationAddressList object
   *
   * @return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationAddressList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationAddressList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the syncType attribute of the OrganizationAddressList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Builds a list of addresses based on several parameters. The parameters are
   *  set after this object is constructed, then the buildList method is called
   *  to generate the list.
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.1
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for returning records
    sqlSelect.append(
        "SELECT " +
        "a.address_id, a.org_id, a.address_type, a.addrline1, a.addrline2, a.addrline3, " +
        "a.city, a.state, a.country, a.postalcode, a.entered, a.enteredby, a.modified, a.modifiedby, " +
        "a.primary_address, a.addrline4, a.county, a.latitude, a.longitude, " +
        "l.code, l.description, l.default_item, l." + DatabaseUtils.addQuotes(db, "level") + ", l.enabled " +
        "FROM organization_address a, lookup_orgaddress_types l " +
        "WHERE a.address_type = l.code ");
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM organization_address a, lookup_orgaddress_types l " +
        "WHERE a.address_type = l.code ");

    createFilter(sqlFilter);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(
            sqlCount.toString() +
            sqlFilter.toString() +
            "AND " + DatabaseUtils.toLowerCase(db) + "(city) < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals(
          "")) {
        sqlOrder.append(
            "ORDER BY " + pagedListInfo.getColumnToSortBy() + ", city ");
        if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals(
            "")) {
          sqlOrder.append(pagedListInfo.getSortOrder() + " ");
        }
      } else {
        sqlOrder.append("ORDER BY city ");
      }

      //Determine items per page
      if (pagedListInfo.getItemsPerPage() > 0) {
        sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
      }

      sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
    }

    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = DatabaseUtils.executeQuery(db, pst, log);
    while (rs.next()) {
      OrganizationAddress thisAddress = new OrganizationAddress(rs);
      this.addElement(thisAddress);
    }
    rs.close();
    pst.close();
  }

  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }
}


