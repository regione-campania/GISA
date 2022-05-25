/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.operatoriprivati.base;

import com.darkhorseventures.framework.actions.ActionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.ArrayList;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OrganizationList extends Vector implements SyncableList {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.operatoriprivati.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  private static final long serialVersionUID = 2268314721560915731L;
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected int includeEnabled = TRUE;

  public final static String tableName = "organization";
  public final static String uniqueField = "org_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;

  protected Boolean minerOnly = null;
  protected int enteredBy = -1;
  protected String name = null;
  protected int ownerId = -1;
  protected int orgId = -1;
  protected String HtmlJsEvent = "";
  protected boolean showMyCompany = false;
  protected String ownerIdRange = null;
  protected String excludeIds = null;
  protected boolean hasAlertDate = false;
  protected boolean hasExpireDate = false;
  protected String accountNumber = null;
  protected int orgSiteId = -1;
  protected boolean includeOrganizationWithoutSite = false;
  protected int projectId = -1;
  private String city = null;
  protected String specie_allev = null;
  protected String tipo_stab = null;
  private String state = null;
  private String country = null;
  protected String postalCode = null;
  protected String assetSerialNumber = null;
  private String codiceAllerta = "";
  protected int revenueType = 0;
  protected int revenueYear = -1;
  protected int revenueOwnerId = -1;
  protected boolean buildRevenueYTD = false;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;

  protected java.sql.Timestamp enteredSince = null;
  protected java.sql.Timestamp enteredTo = null;

  protected int typeId = 0;
  protected String types = null;
  protected String accountSegment = null;
  
  protected int stageId = -1;

  //import filters
  private int importId = -1;
  private int statusId = -1;
  private boolean excludeUnapprovedAccounts = true;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;

  //Contact filters
  private String firstName = null;
  private String lastName = null;
  private String contactPhoneNumber = null;
  private String contactCity = null;
  private String contactState = null;
  private int tipologia = -1;
  protected String banca = null;
//  private String contactOtherState = null;
  private String contactCountry = null;

  private boolean includeAllSites = false;
  
  /**
   * Gets the includeAllSites attribute of the OrganizationList object
   *
   * @return includeAllSites The includeAllSites value
   */
  public boolean isIncludeAllSites() {
    return this.includeAllSites;
  }

  public String getCodiceAllerta() {
		return codiceAllerta;
	}

	public void setCodiceAllerta(String codiceAllerta) {
		this.codiceAllerta = codiceAllerta;
	}
  /**
   * Sets the includeAllSites attribute of the OrganizationList object
   *
   * @param includeAllSites The new includeAllSites value
   */
  public void setIncludeAllSites(boolean includeAllSites) {
    this.includeAllSites = includeAllSites;
  }


  /**
   *  Constructor for the OrganizationList object, creates an empty list. After
   *  setting parameters, call the build method.
   *
   * @since    1.1
   */
  public OrganizationList() { }


  /**
   *  Gets the enteredSince attribute of the OrganizationList object
   *
   * @return    The enteredSince value
   */
  public java.sql.Timestamp getEnteredSince() {
    return enteredSince;
  }


  /**
   *  Sets the enteredSince attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredSince value
   */
  public void setEnteredSince(java.sql.Timestamp tmp) {
    this.enteredSince = tmp;
  }


  /**
   *  Sets the enteredSince attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredSince value
   */
  public void setEnteredSince(String tmp) {
    this.enteredSince = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the enteredTo attribute of the OrganizationList object
   *
   * @return    The enteredTo value
   */
  public java.sql.Timestamp getEnteredTo() {
    return enteredTo;
  }


  /**
   *  Sets the enteredTo attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredTo value
   */
  public void setEnteredTo(java.sql.Timestamp tmp) {
    this.enteredTo = tmp;
  }


  /**
   *  Sets the enteredTo attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredTo value
   */
  public void setEnteredTo(String tmp) {
    this.enteredTo = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the excludeIds attribute of the OrganizationList object
   *
   * @return    The excludeIds value
   */
  public String getExcludeIds() {
    return excludeIds;
  }


  /**
   *  Sets the excludeIds attribute of the OrganizationList object
   *
   * @param  tmp  The new excludeIds value
   */
  public void setExcludeIds(String tmp) {
    this.excludeIds = tmp;
  }


  /**
   *  Gets the types attribute of the OrganizationList object
   *
   * @return    The types value
   */
  public String getTypes() {
    return types;
  }


  /**
   *  Sets the types attribute of the OrganizationList object
   *
   * @param  tmp  The new types value
   */
  public void setTypes(String tmp) {
    this.types = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Gets the typeId attribute of the OrganizationList object
   *
   * @return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   * @param  typeId  The new typeId value
   */
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   * @param  typeId  The new typeId value
   */
  public void setTypeId(String typeId) {
    this.typeId = Integer.parseInt(typeId);
  }


  /**
   *  Sets the accountSegment attribute of the OrganizationList object
   *
   * @param  tmp  The new accountSegment value
   */
  public void setAccountSegment(String tmp) {
    this.accountSegment = tmp;
  }


  /**
   *  Sets the accountSegment attribute of the OrganizationList object
   *
   * @return    The accountSegment value
   */
  public String getAccountSegment() {
    return accountSegment;
  }
  /**
   *  Sets the stageId attribute of the OrganizationList object
   *
   * @param  tmp  The new stageId  value
   */
  public void setStageId(int tmp) {
    this.stageId = tmp;
  }
  
  /**
   *  Sets the stageId attribute of the OrganizationList object
   *
   * @param  tmp  The new stageId  value
   */
  public void setStageId(String tmp) {
	  if (tmp!=null){
    this.stageId = Integer.parseInt(tmp);
	  }else{this.stageId = -1;}
  }


  /**
   *  Sets the stageId  attribute of the OrganizationList object
   *
   * @return    The stageId  value
   */
  public int getStageId () {
    return stageId;
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
   *
   *  <p/>
   *
   *  The query results will be constrained to the PagedListInfo parameters.
   *
   * @param  tmp  The new PagedListInfo value
   * @since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the OrganizationList object to limit the
   *  results to miner only, or non-miner only.
   *
   * @param  tmp  The new MinerOnly value
   * @since       1.1
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = new Boolean(tmp);
  }


  /**
   *  Sets the revenueType attribute of the OrganizationList object
   *
   * @param  tmp  The new revenueType value
   */
  public void setRevenueType(int tmp) {
    this.revenueType = tmp;
  }


  /**
   *  Sets the revenueYear attribute of the OrganizationList object
   *
   * @param  tmp  The new revenueYear value
   */
  public void setRevenueYear(int tmp) {
    this.revenueYear = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the OrganizationList object
   *
   * @param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the alertRangeEnd attribute of the OrganizationList object
   *
   * @param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   *  Sets the importId attribute of the OrganizationList object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new excludeUnapprovedAccounts value
   */
  public void setExcludeUnapprovedAccounts(boolean tmp) {
    this.excludeUnapprovedAccounts = tmp;
  }


  /**
   *  Sets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new excludeUnapprovedAccounts value
   */
  public void setExcludeUnapprovedAccounts(String tmp) {
    this.excludeUnapprovedAccounts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @return    The excludeUnapprovedAccounts value
   */
  public boolean getExcludeUnapprovedAccounts() {
    return excludeUnapprovedAccounts;
  }


  /**
   *  Sets the trashedDate attribute of the OrganizationList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the OrganizationList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the OrganizationList object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @return    The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   *  Sets the importId attribute of the OrganizationList object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrganizationList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrganizationList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the firstName attribute of the OrganizationList object
   *
   * @param  tmp  The new firstName value
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   *  Sets the lastName attribute of the OrganizationList object
   *
   * @param  tmp  The new lastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   *  Sets the contactPhoneNumber attribute of the OrganizationList object
   *
   * @param  tmp  The new contactPhoneNumber value
   */
  public void setContactPhoneNumber(String tmp) {
    this.contactPhoneNumber = tmp;
  }


  /**
   *  Sets the contactCity attribute of the OrganizationList object
   *
   * @param  tmp  The new contactCity value
   */
  public void setContactCity(String tmp) {
    this.contactCity = tmp;
  }


  /**
   *  Sets the contactState attribute of the OrganizationList object
   *
   * @param  tmp  The new contactState value
   */
  public void setContactState(String tmp) {
    this.contactState = tmp;
  }


  /**
   *  Gets the contactCountry attribute of the OrganizationList object
   *
   * @return    The contactCountry value
   */
  public String getContactCountry() {
    return contactCountry;
  }


  /**
   *  Sets the contactCountry attribute of the OrganizationList object
   *
   * @param  tmp  The new contactCountry value
   */
  public void setContactCountry(String tmp) {
    this.contactCountry = tmp;
  }


  /**
   *  Gets the contactOtherState attribute of the OrganizationList object
   *
   * @return    The contactOtherState value
   */
  public String getContactOtherState() {
    return contactState;
  }


  /**
   *  Sets the contactOtherState attribute of the OrganizationList object
   *
   * @param  tmp  The new contactOtherState value
   */
  public void setContactOtherState(String tmp) {
    this.contactState = tmp;
  }


  /**
   *  Gets the importId attribute of the OrganizationList object
   *
   * @return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the statusId attribute of the OrganizationList object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }

@Override
public String getTableName() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getUniqueField() {
	// TODO Auto-generated method stub
	return null;
}


  /**
   *  Gets the firstName attribute of the OrganizationList object
   *
   * @return    The firstName value
   */
  
}

