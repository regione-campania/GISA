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
package org.aspcfs.modules.aziendeagricole.base;

import com.darkhorseventures.framework.actions.ActionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.global_search.base.OrganizationView;
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
import java.sql.Timestamp;
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

	private static Logger log = Logger.getLogger(org.aspcfs.modules.laboratorihaccp.base.OrganizationList.class);

	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MainLogger");


	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}
	private static final int TIPOLOGIA_AZIENDA_AGRICOLA = 7 ;
	private static final long serialVersionUID = 2268314721560915731L;
	private String accountCity = null;
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
	private String accountName = null;
	private double latitudine = 0;
	private double longitudine = 0;
	protected int siteId = -1;
	protected int ownerId = -1;
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
	private String state = null;
	private String country = null;
	protected String postalCode = null;
	protected String assetSerialNumber = null;
	
	private String partitaIva ;

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
	private String codiceFiscale ;
	//import filters
	private int importId = -1;
	private int statusId = -1;
	private boolean excludeUnapprovedAccounts = true;
	private java.sql.Timestamp trashedDate = null;
	private boolean includeOnlyTrashed = false;

	//Contact filters
	private String firstName = null;
	private String lastName = null;
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	private String name = null;
	private String contactPhoneNumber = null;
	private String contactCity = null;
	private String contactState = null;
	//  private String contactOtherState = null;
	private String contactCountry = null;
	private String stato ;
	private boolean includeAllSites = false;
	private String numAutorizzazzione ;
	private Integer addressType=-1;

	

	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public void setAddressType(String addressType) {
		this.addressType = Integer.parseInt(addressType);
	}
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

	public String getNumAutorizzazzione() {
		return numAutorizzazzione;
	}


	public void setNumAutorizzazzione(String numAutorizzazzione) {
		this.numAutorizzazzione = numAutorizzazzione;
	}



	/**
	 * Gets the includeAllSites attribute of the OrganizationList object
	 *
	 * @return includeAllSites The includeAllSites value
	 */
	public boolean isIncludeAllSites() {
		return this.includeAllSites;
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

	public void setSiteId(int idTip) {
		this.siteId = idTip;
	}


	public String getStato() {
		return stato;
	}


	public void setStato(String stato) {
		this.stato = stato;
	}


	/**
	 *  Sets the siteId attribute of the Organization object
	 *
	 * @param  tmp  The new siteId value
	 */
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}
	public int getSiteId() {
		return siteId;
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

	public void setAccountName(String tmp) {
		this.accountName = tmp;
	}

	public void setName(String tmp) {
		this.name = tmp;
	}

	public String getName() {
		return name;
	}




	public void setLatitudine(double tmp) {
		this.latitudine = tmp;
	}

	public void setLongitudine(double tmp) {
		this.longitudine = tmp;
	}


	public String getAccountName() {
		return accountName;
	}

	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String tmp) {
		this.setLatitudine(Integer.parseInt(tmp));
	}
	public void setLongitudine(String tmp) {
		this.setLongitudine(Integer.parseInt(tmp));
	}

	public double getLongitudine() {
		return longitudine;
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


	/**
	 *  Gets the firstName attribute of the OrganizationList object
	 *
	 * @return    The firstName value
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 *  Gets the lastName attribute of the OrganizationList object
	 *
	 * @return    The lastName value
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 *  Gets the contactPhoneNumber attribute of the OrganizationList object
	 *
	 * @return    The contactPhoneNumber value
	 */
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}


	/**
	 *  Gets the contactCity attribute of the OrganizationList object
	 *
	 * @return    The contactCity value
	 */
	public String getContactCity() {
		return contactCity;
	}


	/**
	 *  Gets the contactState attribute of the OrganizationList object
	 *
	 * @return    The contactState value
	 */
	public String getContactState() {
		return contactState;
	}


	/**
	 *  Gets the revenueType attribute of the OrganizationList object
	 *
	 * @return    The revenueType value
	 */
	public int getRevenueType() {
		return revenueType;
	}


	/**
	 *  Gets the revenueYear attribute of the OrganizationList object
	 *
	 * @return    The revenueYear value
	 */
	public int getRevenueYear() {
		return revenueYear;
	}


	/**
	 *  Gets the includeEnabled attribute of the OrganizationList object
	 *
	 * @return    The includeEnabled value
	 */
	public int getIncludeEnabled() {
		return includeEnabled;
	}


	/**
	 *  Sets the includeEnabled attribute of the OrganizationList object
	 *
	 * @param  includeEnabled  The new includeEnabled value
	 */
	public void setIncludeEnabled(int includeEnabled) {
		this.includeEnabled = includeEnabled;
	}


	/**
	 *  Sets the ShowMyCompany attribute of the OrganizationList object
	 *
	 * @param  showMyCompany  The new ShowMyCompany value
	 */
	public void setShowMyCompany(boolean showMyCompany) {
		this.showMyCompany = showMyCompany;
	}


	/**
	 *  Sets the hasAlertDate attribute of the OrganizationList object
	 *
	 * @param  hasAlertDate  The new hasAlertDate value
	 */
	public void setHasAlertDate(boolean hasAlertDate) {
		this.hasAlertDate = hasAlertDate;
	}


	/**
	 *  Sets the HtmlJsEvent attribute of the OrganizationList object
	 *
	 * @param  HtmlJsEvent  The new HtmlJsEvent value
	 */
	public void setHtmlJsEvent(String HtmlJsEvent) {
		this.HtmlJsEvent = HtmlJsEvent;
	}


	/**
	 *  Gets the accountNumber attribute of the OrganizationList object
	 *
	 * @return    The accountNumber value
	 */
	public String getAccountNumber() {
		return accountNumber;
	}


	/**
	 *  Sets the accountNumber attribute of the OrganizationList object
	 *
	 * @param  accountNumber  The new accountNumber value
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	/**
	 *  Gets the projectId attribute of the OrganizationList object
	 *
	 * @return    The projectId value
	 */
	public int getProjectId() {
		return projectId;
	}


	/**
	 *  Sets the projectId attribute of the OrganizationList object
	 *
	 * @param  projectId  The new projectId value
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}


	/**
	 *  Sets the projectId attribute of the OrganizationList object
	 *
	 * @param  projectId  The new projectId value
	 */
	public void setProjectId(String projectId) {
		this.projectId = Integer.parseInt(projectId);
	}


	/**
	 *  Sets the EnteredBy attribute of the OrganizationList object to limit
	 *  results to those records entered by that user.
	 *
	 * @param  tmp  The new EnteredBy value
	 * @since       1.1
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the orgSiteId attribute of the OrganizationList object
	 *
	 * @param  orgSiteId  The new orgSiteId value
	 */
	public void setOrgSiteId(int orgSiteId) {
		this.orgSiteId = orgSiteId;
	}


	/**
	 *  Sets the orgSiteId attribute of the OrganizationList object
	 *
	 * @param  orgSiteId  The new orgSiteId value
	 */
	public void setOrgSiteId(String orgSiteId) {
		this.orgSiteId = Integer.parseInt(orgSiteId);
	}


	/**
	 *  
	 *
	 * @return    The orgSiteId value
	 */
	public int getOrgSiteId() {
		return orgSiteId;
	}


	/**
	 *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
	 *  object
	 *
	 * @param  tmp  The new includeOrganizationWithoutSite value
	 */
	public void setIncludeOrganizationWithoutSite(boolean tmp) {
		this.includeOrganizationWithoutSite = tmp;
	}


	/**
	 *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
	 *  object
	 *
	 * @param  tmp  The new includeOrganizationWithoutSite value
	 */
	public void setIncludeOrganizationWithoutSite(String tmp) {
		this.includeOrganizationWithoutSite = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Gets the includeOrganizationWithoutSite attribute of the OrganizationList
	 *  object
	 *
	 * @return    The includeOrganizationWithoutSite value
	 */
	public boolean getIncludeOrganizationWithoutSite() {
		return includeOrganizationWithoutSite;
	}


	/**
	 *  Gets the buildRevenueYTD attribute of the OrganizationList object
	 *
	 * @return    The buildRevenueYTD value
	 */
	public boolean getBuildRevenueYTD() {
		return buildRevenueYTD;
	}


	/**
	 *  Sets the buildRevenueYTD attribute of the OrganizationList object
	 *
	 * @param  buildRevenueYTD  The new buildRevenueYTD value
	 */
	public void setBuildRevenueYTD(boolean buildRevenueYTD) {
		this.buildRevenueYTD = buildRevenueYTD;
	}


	/**
	 *  Sets the ownerIdRange attribute of the OrganizationList object
	 *
	 * @param  tmp  The new ownerIdRange value
	 */
	public void setOwnerIdRange(String tmp) {
		this.ownerIdRange = tmp;
	}
	/**
	 *  Gets the revenueOwnerId attribute of the OrganizationList object
	 *
	 * @return    The revenueOwnerId value
	 */
	public int getRevenueOwnerId() {
		return revenueOwnerId;
	}


	/**
	 *  Building Alert Counts Sets the revenueOwnerId attribute of the
	 *  OrganizationList object
	 *
	 * @param  revenueOwnerId  The new revenueOwnerId value
	 */
	public void setRevenueOwnerId(int revenueOwnerId) {
		this.revenueOwnerId = revenueOwnerId;
	}


	/**
	 *  Sets the hasExpireDate attribute of the OrganizationList object
	 *
	 * @param  hasExpireDate  The new hasExpireDate value
	 */
	public void setHasExpireDate(boolean hasExpireDate) {
		this.hasExpireDate = hasExpireDate;
	}


	/**
	 *  Sets the OwnerId attribute of the OrganizationList object
	 *
	 * @param  ownerId  The new OwnerId value
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}


	/**
	 *  Sets the ownerId attribute of the OrganizationList object
	 *
	 * @param  tmp  The new ownerId value
	 */
	public void setOwnerId(String tmp) {
		this.ownerId = Integer.parseInt(tmp);
	}





	/**
	 *  Gets the tableName attribute of the OrganizationList object
	 *
	 * @return    The tableName value
	 */
	public String getTableName() {
		return tableName;
	}


	/**
	 *  Gets the uniqueField attribute of the OrganizationList object
	 *
	 * @return    The uniqueField value
	 */
	public String getUniqueField() {
		return uniqueField;
	}


	/**
	 *  Gets the hasAlertDate attribute of the OrganizationList object
	 *
	 * @return    The hasAlertDate value
	 */
	public boolean getHasAlertDate() {
		return hasAlertDate;
	}


	/**
	 *  Gets the ownerIdRange attribute of the OrganizationList object
	 *
	 * @return    The ownerIdRange value
	 */
	public String getOwnerIdRange() {
		return ownerIdRange;
	}


	/**
	 *  Gets the hasExpireDate attribute of the OrganizationList object
	 *
	 * @return    The hasExpireDate value
	 */
	public boolean getHasExpireDate() {
		return hasExpireDate;
	}


	/**
	 *  Gets the ShowMyCompany attribute of the OrganizationList object
	 *
	 * @return    The ShowMyCompany value
	 */
	public boolean getShowMyCompany() {
		return showMyCompany;
	}


	/**
	 *  Gets the HtmlJsEvent attribute of the OrganizationList object
	 *
	 * @return    The HtmlJsEvent value
	 */
	public String getHtmlJsEvent() {
		return HtmlJsEvent;
	}


	/**
	 *  Gets the OwnerId attribute of the OrganizationList object
	 *
	 * @return    The OwnerId value
	 */
	public int getOwnerId() {
		return ownerId;
	}




	/**
	 *  Gets the postalCode attribute of the OrganizationList object
	 *
	 * @return    The postalCode value
	 */
	public String getPostalCode() {
		return postalCode;
	}


	/**
	 *  Sets the postalCode attribute of the OrganizationList object
	 *
	 * @param  tmp  The new postalCode value
	 */
	public void setPostalCode(String tmp) {
		this.postalCode = tmp;
	}


	/**
	 *  Gets the accountPostalCode attribute of the OrganizationList object
	 *
	 * @return    The accountPostalCode value
	 */
	public String getAccountPostalCode() {
		return postalCode;
	}


	/**
	 *  Sets the accountPostalCode attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountPostalCode value
	 */
	public void setAccountPostalCode(String tmp) {
		this.postalCode = tmp;
	}

	/**
	 *  Gets the accountCity attribute of the OrganizationList object
	 *
	 * @return    The accountCity value
	 */
	public String getCity() {
		return city;
	}

	/**
	 *  Sets the accountCity attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountCity value
	 */
	public void setCity(String tmp) {
		this.city = tmp;
	}

	/**
	 *  Gets the accountCity attribute of the OrganizationList object
	 *
	 * @return    The accountCity value
	 */
	public String getAccountCity() {
		return city;
	}

	/**
	 *  Sets the contactCity attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountCity value
	 */
	public void setAccountCity(String tmp) {
		this.city = tmp;
	}

	/**
	 *  Gets the accountState attribute of the OrganizationList object
	 *
	 * @return    The accountState value
	 */
	public String getState() {
		return state;
	}

	/**
	 *  Sets the accountState attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountState value
	 */
	public void setState(String tmp) {
		this.state = tmp;
	}

	/**
	 *  Gets the accountState attribute of the OrganizationList object
	 *
	 * @return    The accountState value
	 */
	public String getAccountState() {
		return state;
	}

	/**
	 *  Sets the contactState attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountState value
	 */
	public void setAccountOtherState(String tmp) {
		this.state = tmp;
	}

	/**
	 *  Gets the accountCountry attribute of the OrganizationList object
	 *
	 * @return    The accountCountry value
	 */
	public String getCountry() {
		return country;
	}

	/**
	 *  Sets the accountCountry attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountCountry value
	 */
	public void setCountry(String tmp) {
		this.country = tmp;
	}

	/**
	 *  Gets the accountCountry attribute of the OrganizationList object
	 *
	 * @return    The accountCountry value
	 */
	public String getAccountCountry() {
		return country;
	}

	/**
	 *  Sets the accountCountry attribute of the OrganizationList object
	 *
	 * @param  tmp  The new accountCountry value
	 */
	public void setAccountCountry(String tmp) {
		this.country = tmp;
	}

	/**
	 *  Gets the assetSerialNumber attribute of the OrganizationList object
	 *
	 * @return    The assetSerialNumber value
	 */
	public String getAssetSerialNumber() {
		return assetSerialNumber;
	}


	/**
	 *  Sets the assetSerialNumber attribute of the OrganizationList object
	 *
	 * @param  tmp  The new assetSerialNumber value
	 */
	public void setAssetSerialNumber(String tmp) {
		this.assetSerialNumber = tmp;
	}

	/**
	 *  
	 *
	 * @param  selectName  Description of Parameter
	 * @return             The HtmlSelect value
	 * @since              1.8
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}


	/**
	 *  
	 *
	 * @param  selectName  Description of Parameter
	 * @param  defaultKey  Description of Parameter
	 * @return             The HtmlSelect value
	 * @since              1.8
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect orgListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			orgListSelect.addItem(
					thisOrg.getId(),
					thisOrg.getAccountName());
		}

		if (!(this.getHtmlJsEvent().equals(""))) {
			orgListSelect.setJsEvent(this.getHtmlJsEvent());
		}

		return orgListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
	 *
	 * @param  selectName  Description of Parameter
	 * @param  thisSystem  Description of the Parameter
	 * @return             The HtmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName) {
		return getHtmlSelectDefaultNone(thisSystem, selectName, -1);
	}


	/**
	 *  Gets the htmlSelectDefaultNone attribute of the OrganizationList object
	 *
	 * @param  selectName  Description of the Parameter
	 * @param  defaultKey  Description of the Parameter
	 * @param  thisSystem  Description of the Parameter
	 * @return             The htmlSelectDefaultNone value
	 */
	public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName, int defaultKey) {
		HtmlSelect orgListSelect = new HtmlSelect();
		orgListSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));

		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			orgListSelect.addItem(
					thisOrg.getId(),
					thisOrg.getAccountName());
		}

		if (!(this.getHtmlJsEvent().equals(""))) {
			orgListSelect.setJsEvent(this.getHtmlJsEvent());
		}

		return orgListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @throws  SQLException  Description of the Exception
	 */
	public void select(Connection db) throws SQLException {
		buildList(db);
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  timeZone       Description of the Parameter
	 * @param  events         Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public HashMap queryRecordCount(Connection db, TimeZone timeZone, HashMap events) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlTail = new StringBuffer();

		String sqlId = ("id_farmacia");

		createFilter(db, sqlFilter);

		sqlSelect.append(
				"SELECT count(*) AS nocols " +
						"FROM farmacie f " +
				"WHERE f.id_farmacia >= 0 ");
		sqlTail.append("GROUP BY " + sqlId);
		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
		prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			String alertDate = DateUtils.getServerToUserDateString(
					timeZone, DateFormat.SHORT, rs.getTimestamp("date"));
			int thisCount = rs.getInt("nocols");
			if (events.containsKey(alertDate)) {
				int tmpCount = ((Integer) events.get(alertDate)).intValue();
				thisCount += tmpCount;
			}
			events.put(alertDate, new Integer(thisCount));
		}
		rs.close();
		pst.close();
		return events;
	}


	/**
	 *  Queries the database, using any of the filters, to retrieve a list of
	 *  organizations. The organizations are appended, so build can be run any
	 *  number of times to generate a larger list for a report.
	 *
	 * @param  db             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 * @since                 1.1
	 */ 
	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			Organization thisOrganization = new Organization();
			thisOrganization.buildRecordSearch(rs);
			this.add(thisOrganization);
			/*Organization thisOrg = new Organization();
	 thisOrg.setOrgId(rs.getInt("org_id"));
	 thisOrg.setAccountName(rs.getString("name"));
	 thisOrg.setName(rs.getString("name"));
	 thisOrg.setSiteId(rs.getInt("site_id"));
	 thisOrg.setAsl(rs.getString("asl"));
	 thisOrg.getAddressList().setOrgId(thisOrg.getOrgId());
	 thisOrg.getAddressList().buildList(db);
	 this.add(thisOrg);*/
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
		buildResources(db);
	}

	public void buildList(Connection db, PreparedStatement pst) throws SQLException {
		//PreparedStatement pst = null;
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			Organization thisOrganization = new Organization () ;
			thisOrganization.buildRecordSearch(rs);

			this.add(thisOrganization);

		}
		rs.close();
		if (pst != null) {
			pst.close();
		}
		
	}

	
	

	/**
	 *  Gets the object attribute of the OrganizationList object
	 *
	 * @param  rs             Description of the Parameter
	 * @return                The object value
	 * @throws  SQLException  Description of the Exception
	 */
	public Organization getObject(ResultSet rs) throws SQLException {
		Organization thisOrganization = new Organization(rs);
		return thisOrganization;
	}


	/*Ricerca aziende agricole*/
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = 0;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append(
				"SELECT  COUNT( org_id) AS recordcount " +
				"FROM opu_operatori_aziende_agricole " );
//
//		sqlCount.append(" left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = o.org_id and o.tipo_operatore_old =2 " +  
//				" left join ml8_linee_attivita_nuove ln on rslp.id_linea_produttiva = ln.id_nuova_linea_attivita " );

		sqlCount.append(" WHERE 1=1 AND primario=true  ");

		createFilter(db, sqlFilter); 

		if (pagedListInfo != null) { 
			//Get the total number of records matching filter
			pst = db.prepareStatement(
					sqlCount.toString() +
					sqlFilter.toString());
			// UnionAudit(sqlFilter,db);
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine the offset, based on the filter, for the first record to show

			sqlOrder.append(" order by impresa ");
			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("");
		}

	
		sqlSelect.append(	
				"select org_id,impresa ,num_reg,targa_sede_operativa,attivita,id_asl,partita_iva,codice_fiscale"+

				" FROM opu_operatori_aziende_agricole " );

		sqlSelect.append("WHERE 1=1  ");




		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);



		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = DatabaseUtils.executeQuery(db, pst, log);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs; 
	}

	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		//Name
		if (accountName != null && (!accountName.equals("")) ) {
			if (accountName.indexOf("%") >= 0) {
				sqlFilter.append("AND impresa ILIKE ? ");
			} else {
				sqlFilter.append("AND impresa = ? ");
			}
		}

		//City legale
		if (city != null && (!city.equals("")) ) {
			if (city.indexOf("%") >= 0) {
				sqlFilter.append(" AND targa_sede_operativa ILIKE ? ");


			} else {
				sqlFilter.append(" AND targa_sede_operativa ILIKE ? ");

			}
		}

		if (codiceFiscale != null && ! codiceFiscale.equals(""))
		{
			sqlFilter.append(" AND codice_fiscale ilike ? ");
		}

		if (orgSiteId > -1) {
			sqlFilter.append(" AND id_asl = ? ");
		}
		if (partitaIva != null && ! partitaIva.equals(""))
		{
			sqlFilter.append(" AND partita_iva ilike ? ");
		}
		
		if (accountNumber != null && ! accountNumber.equals(""))
		{
			sqlFilter.append(" AND num_reg ilike ? ");
		}
		
		



	}



	/**
	 *  Convenience method to get a list of phone numbers for each contact
	 *
	 * @param  db             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 * @since                 1.5
	 */
	protected void buildResources(Connection db) throws SQLException {

	}


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  newOwner       Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public int reassignElements(Connection db, int newOwner) throws SQLException {
		int total = 0;
		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();

		}
		return total;
	} 


	/**
	 *  Description of the Method
	 *
	 * @param  db             Description of the Parameter
	 * @param  newOwner       Description of the Parameter
	 * @param  userId         Description of the Parameter
	 * @return                Description of the Return Value
	 * @throws  SQLException  Description of the Exception
	 */
	public int reassignElements(Connection db, int newOwner, int userId) throws SQLException {
		int total = 0;
		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			thisOrg.setModifiedBy(userId);

		}
		return total;
	}



	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		//Name
		if (accountName != null && (!accountName.equals("")) ) {
			pst.setString(++i, accountName.toLowerCase().replaceAll(" ", "%"));
		}

		//AccountCityLeg
		if (city != null && !(city.equals(""))) {
			pst.setString(++i, city);

		}
		if (codiceFiscale != null && ! codiceFiscale.equals(""))
		{
			pst.setString(++i, codiceFiscale);
		}        
		if (orgSiteId > -1) {
			pst.setInt(++i, orgSiteId);		}
		
		if (partitaIva != null && ! partitaIva.equals(""))
		{
			pst.setString(++i, partitaIva);
		}
		if (accountNumber != null && ! accountNumber.equals(""))
		{
			pst.setString(++i, accountNumber);
		}


		return i;
	}





	/**
	 *  Gets the parent and leaf Accounts in the application Parent accounts are
	 *  the list of root nodes in the organization hierarchies Leaf accounts are
	 *  the list of leaf nodes in the organization hierarchies
	 *
	 * @param  db                Description of the Parameter
	 * @param  typeId            Description of the Parameter
	 * @param  reciprocal        Description of the Parameter
	 * @return                   The parentAccounts value
	 * @exception  SQLException  Description of the Exception
	 */
	public static HashMap getParentAndLeafAccounts(Connection db, int typeId, boolean reciprocal) throws SQLException {
		HashMap allAccounts = new HashMap();
		HashMap leafAccounts = new HashMap();
		PreparedStatement pst = db.prepareStatement(
				"SELECT id_farmacia FROM farmacie WHERE trashed_date IS NULL ");
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Integer accountId = new Integer(rs.getInt("id_farmacia"));
			allAccounts.put(accountId, accountId);
			leafAccounts.put(accountId, accountId);
		}
		rs.close();
		pst.close();
		RelationshipList thisList = new RelationshipList();
		thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
		thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
		thisList.setTypeId(typeId);
		thisList.buildList(db);
		Iterator iter = (Iterator) thisList.keySet().iterator();
		while (iter.hasNext()) {
			String relType = (String) iter.next();
			ArrayList tmpList = (ArrayList) thisList.get(relType);
			Iterator j = tmpList.iterator();
			while (j.hasNext()) {
				Relationship rel = (Relationship) j.next();
				//check for parent accounts to be in the child category
				if (allAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && reciprocal) {
					allAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
				} else if (allAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && !reciprocal) {
					allAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
				}
				//check for child accounts to be in the parent category
				if (leafAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && reciprocal) {
					leafAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
				} else if (leafAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && !reciprocal) {
					leafAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
				}
			}
		}
		HashMap combinedAccounts = new HashMap();
		combinedAccounts.put("parentNodes", allAccounts);
		combinedAccounts.put("leafNodes", leafAccounts);
		return combinedAccounts;
	}


	/**
	 *  Description of the Method
	 *
	 * @param  db                Description of the Parameter
	 * @param  childId           Description of the Parameter
	 * @param  skipName          Description of the Parameter
	 * @param  existingAccounts  Description of the Parameter
	 * @return                   Description of the Return Value
	 * @exception  SQLException  Description of the Exception
	 */





	/**
	 *  Gets the orgById attribute of the OrganizationList object This method
	 *  assumes that the value of id is > 0
	 *
	 * @param  id  The value of id is always greater than 0.
	 * @return     returns the matched organization or returns null
	 */
	public Organization getOrgById(int id) {
		Organization result = null;
		Iterator iter = (Iterator) this.iterator();
		while (iter.hasNext()) {
			Organization org = (Organization) iter.next();
			if (org.getOrgId() == id) {
				result = org;
				break;
			}
		}
		return result;
	}

	public void buildListOperatori(Connection db, String ticketId)  throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = queryListOpControllati(db, pst, (Integer.parseInt(ticketId)));
		while (rs.next()) {
			Organization thisOrganization = new Organization();
			thisOrganization.setOrgId(rs.getInt("org_id"));
			thisOrganization.setName(rs.getString("name"));
			thisOrganization.setTipologia(rs.getInt("tipologia"));
			thisOrganization.setAccountNumber(rs.getString("account_number"));
			thisOrganization.setNomeCorrentista(rs.getString("nome_correntista")); 
//			thisOrganization.setConservato(rs.getBoolean("conservato"));
			this.add(thisOrganization);
		}
		rs.close();
		if (pst != null) {
			pst.close();
		}


	}


	private ResultSet queryListOpControllati(Connection db, PreparedStatement pst, int ticketId) throws SQLException {

		// TODO Auto-generated method stub
		StringBuffer sqlSelect = new StringBuffer();
		ResultSet rs = null;
		//sqlSelect.append("SELECT * FROM organization where org_id in (select org_id from punti_sbarco_operatori_controllati where ticket_id = ? )") ;
		sqlSelect.append("SELECT * FROM organization o LEFT JOIN punti_sbarco_operatori_controllati p on o.org_id = p.org_id where ticket_id = ?" );
		pst = db.prepareStatement(sqlSelect.toString());
		pst.setInt(1, ticketId);
		rs = DatabaseUtils.executeQuery(db, pst, log);

		return rs;
	}




	/*private void buildListProdotti(Connection db,String ticketId) {
		// TODO Auto-generated method stub
		  PreparedStatement pst = null;
		  ResultSet rs = queryListProdotti(db, pst, (Integer.parseInt(ticketId)));
	      while (rs.next()) {

	      }
		  rs.close();
		  if (pst != null) {
			  pst.close();
		  }


	}


	private ResultSet queryListProdotti(Connection db, PreparedStatement pst, int ticketId) {
		// TODO Auto-generated method stub

		  StringBuffer sqlSelect = new StringBuffer();
		  ResultSet rs = null;
		  sqlSelect.append("SELECT conservato FROM organization where org_id in (select org_id from punti_sbarco_operatori_controllati where ticket_id = ? )") ;
		  pst = db.prepareStatement(sqlSelect.toString());
		  pst.setInt(1, ticketId);
		  rs = DatabaseUtils.executeQuery(db, pst, log);

		  return rs;

	}*/


}


