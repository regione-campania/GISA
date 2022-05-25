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
package org.aspcfs.modules.contacts.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import org.apache.log4j.Level;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionItemWorkList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.ExcludedRecipient;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.communications.base.SurveyResponseList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.text.DateFormat;
import java.util.*;

/**
 * Represents a Contact
 *
 * @author mrajkowski
 * @version $Id: Contact.java,v 1.106.4.3.2.3 2005/01/10 17:58:55 kbhoopal
 *          Exp $
 * @created August 29, 2001
 */
public class Contact extends GenericBean {

	private static final long serialVersionUID = -177466129659625417L;

	/**
	 * HTTP API User Mode checks to see if the user has the required permission
	 * to execute the corresponding method on the object. If the object can exist
	 * in various forms and each form has a unique permission then override
	 * method 'getPermission()' eg: Contact can be an Account Contact or General
	 * Contact
	 */
	protected String permission = "contacts-external_contacts";

	/**
	 * Type 1 in the database refers to an Employee
	 */
	public final static int EMPLOYEE_TYPE = 1;
	public final static int LEAD_TYPE = 2;
	public final static int LEAD_UNREAD = 0;
	public final static int LEAD_UNPROCESSED = 1;
	public final static int LEAD_ASSIGNED = 2;
	public final static int LEAD_TRASHED = 3;
	public final static int LEAD_WORKED = 4;
	  private String state = null;

	private int id = -1;
	private int orgId = -1;
	private String orgName = "";
	private String company = "";
	private String accountNumber = "";
	private String title = "";
	private int department = -1;
	private String nameSalutation = "";
	private int listSalutation = -1;
	private int orgSiteId = -1;
	private boolean prospectClient = false;
	private boolean noEmail = false;
	private boolean noMail = false;
	private boolean noPhone = false;
	private boolean noTextMessage = false;
	private boolean noInstantMessage = false;
	private boolean noFax = false;
	private String nameFirst = "";
	private String nameMiddle = "";
	private String nameLast = "";
	private String nameSuffix = "";
	private int assistant = -1;
	private String additionalNames = "";
	private String nickname = "";
	private String role = "";
	private java.sql.Timestamp birthDate = null;
	private String site = "";
	private String notes = "";
	private int locale = -1;
	private String employeeId = null;
	private int employmentType = -1;
	private String startOfDay = "";
	private String endOfDay = "";
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private int enteredBy = -1;
	private int modifiedBy = -1;
	private boolean enabled = true;
	private boolean hasAccount = false;
	private boolean excludedFromCampaign = false;
	private int owner = -1;
	private int custom1 = -1;
	private String url = null;
	private int userId = -1;
	private int accessType = -1;
	private int clientId = -1;
	private int statusId = -1;
	private int importId = -1;
	private boolean isLead = false;
	private int leadStatus = -1;
	private int source = -1;
	private int rating = -1;
	private int stage = -1;
	private String comments = null;
	private java.sql.Timestamp conversionDate = null;
	private java.sql.Timestamp assignedDate = null;
	private java.sql.Timestamp leadTrashedDate = null;
	private String secretWord = null;
	private double revenue = 0;
	private double potential = 0;
	private int industryTempCode = -1;
	private String city = null;
	private String postalcode = null;
	private int siteId = -1;
	private String siteName = null;
	private String importName = null;

	private ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
	private ContactPhoneNumberList phoneNumberList = new ContactPhoneNumberList();
	private ContactAddressList addressList = new ContactAddressList();
	private ContactTextMessageAddressList textMessageAddressList = new ContactTextMessageAddressList();
	private ContactInstantMessageAddressList instantMessageAddressList = new ContactInstantMessageAddressList();
	private String departmentName = "";
	private String ownerName = "";
	private String enteredByName = "";
	private String modifiedByName = "";
	private String industryName = "";
	private String sourceName = "";
	private String ratingName = "";
	private String stageName = "";
	
	private String  visibilitaDelega ;

	private boolean orgEnabled = true;
	private java.sql.Timestamp orgTrashedDate = null;
	private boolean hasEnabledOwnerAccount = true;
	private boolean hasEnabledAccount = true;

	private boolean primaryContact = false;
	private boolean employee = false;
	private boolean hasOpportunities = false;
	private LookupList types = new LookupList();
	private ArrayList typeList = null;

	private boolean buildDetails = true;
	private boolean buildTypes = true;
	private boolean hasAccess = false;
	private boolean isEnabled = false;

	private java.sql.Timestamp trashedDate = null;
	private boolean forceDelete = false;
	private boolean checkRevertingBackToLead = false;

	private int employees = -1;
	private String dunsType = null;
	private String dunsNumber = null;
	private String businessNameTwo = null;
	private int sicCode = -1;
	private int yearStarted = -1;
	private String sicDescription = null;

	private String codiceFiscale ;
	
	
	/*PER LA GESTIONE DEL PORTALE A CUI ACCEDONO I TRASPORTATORI/DISTRIBUTORI*/
	private String numRegistrazioneStabilimentoAssociato;
	//Logger
	private static long milies = -1;
	//private static Logger logger = Logger.getLogger(Contact.class);

	
	static {
		if (System.getProperty("DEBUG") != null) {
			//  logger.setLevel(Level.DEBUG);
		}
	}

	//filter used by xml api to retrieve the contact info for a particular user
	private String username = null;


	

	public String getNumRegistrazioneStabilimentoAssociato() {
		return numRegistrazioneStabilimentoAssociato;
	}

	public void setNumRegistrazioneStabilimentoAssociato(String numRegistrazioneStabilimentoAssociato) {
		this.numRegistrazioneStabilimentoAssociato = numRegistrazioneStabilimentoAssociato;
	}

	public String getVisibilitaDelega() {
		return visibilitaDelega;
	}

	public void setVisibilitaDelega(String visibilitaDelega) {
		this.visibilitaDelega = visibilitaDelega;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCodiceFiscale() {
		if (codiceFiscale!=null)
		return codiceFiscale;
		return "";
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setUsername(String tmp) {
		this.username = tmp;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * Gets the permission attribute of the Contact object
	 *
	 * @return The permission value
	 */
	public String getPermission() {
		if (orgId != -1) {
			permission = "accounts-accounts-contacts";
		} else if (employee) {
			permission = "contacts-internal_contacts";
		}
		return permission;
	}


	/**
	 * Gets the industryName attribute of the Contact object
	 *
	 * @return The industryName value
	 */
	public String getIndustryName() {
		return industryName;
	}


	/**
	 * Gets the sourceName attribute of the Contact object
	 *
	 * @return The sourceName value
	 */
	public String getSourceName() {
		return sourceName;
	}


	/**
	 * Gets the ratingName attribute of the Contact object
	 *
	 * @return The ratingName value
	 */
	public String getRatingName() {
		return ratingName;
	}


	/**
	 * Gets the importName attribute of the Contact object
	 *
	 * @return The importName value
	 */
	public String getImportName() {
		return importName;
	}


	/**
	 * Gets the stageName attribute of the Contact object
	 *
	 * @return The stageName value
	 */
	public String getStageName() {
		return stageName;
	}


	/**
	 * Gets the instantMessageAddressList attribute of the Contact object
	 *
	 * @return The instantMessageAddressList value
	 */
	public ContactInstantMessageAddressList getInstantMessageAddressList() {
		return instantMessageAddressList;
	}


	/**
	 * Sets the instantMessageAddressList attribute of the Contact object
	 *
	 * @param tmp The new instantMessageAddressList value
	 */
	public void setInstantMessageAddressList(ContactInstantMessageAddressList tmp) {
		this.instantMessageAddressList = tmp;
	}


	/**
	 * Gets the city attribute of the Contact object
	 *
	 * @return The city value
	 */
	public String getCity() {
		return city;
	}


	/**
	 * Sets the city attribute of the Contact object
	 *
	 * @param tmp The new city value
	 */
	public void setCity(String tmp) {
		this.city = tmp;
	}


	/**
	 * Gets the postalcode attribute of the Contact object
	 *
	 * @return The postalcode value
	 */
	public String getPostalcode() {
		return postalcode;
	}


	/**
	 * Sets the postalcode attribute of the Contact object
	 *
	 * @param tmp The new postalcode value
	 */
	public void setPostalcode(String tmp) {
		this.postalcode = tmp;
	}


	/**
	 * Sets the siteId attribute of the Contact object
	 *
	 * @param tmp The new siteId value
	 */
	public void setSiteId(int tmp) {
		this.siteId = tmp;
	}


	/**
	 * Sets the siteId attribute of the Contact object
	 *
	 * @param tmp The new siteId value
	 */
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}


	/**
	 * Gets the siteId attribute of the Contact object
	 *
	 * @return The siteId value
	 */
	public int getSiteId() {
		return siteId;
	}


	/**
	 * Constructor for the Contact object
	 *
	 * @since 1.1
	 */
	public Contact() {
	}
	
	
	public Contact(Connection db , int contactId) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from contact_ where contact_id = "+contactId);
		ResultSet rs =  pst.executeQuery();
		if (rs.next())
		{
			this.buildRecord(rs);
			
			
		}
		
	}

public Contact(Connection db , int contactId, boolean ext) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from contact_ext_ where contact_id = "+contactId);
		ResultSet rs =  pst.executeQuery();
		if (rs.next())
		{
			this.buildRecord(rs);
			
			
		}
		
	}

	/**
	 * Constructor for the Contact object
	 *
	 * @param rs Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @since 1.1
	 */
	public Contact(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}



	

	/**
	 * Description of the Method
	 *
	 * @param obj Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean equals(Object obj) {
		if (this.getId() == ((Contact) obj).getId()) {
			return true;
		}

		return false;
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	

	/**
	 * Description of the Method
	 *
	 * @param db        Description of the Parameter
	 * @param contactId Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */ 



	/**
	 * Gets the additionalNames attribute of the Contact object
	 *
	 * @return The additionalNames value
	 */
	public String getAdditionalNames() {
		return additionalNames;
	}


	/**
	 * Sets the additionalNames attribute of the Contact object
	 *
	 * @param tmp The new additionalNames value
	 */
	public void setAdditionalNames(String tmp) {
		this.additionalNames = tmp;
	}


	/**
	 * Gets the nickname attribute of the Contact object
	 *
	 * @return The nickname value
	 */
	public String getNickname() {
		return nickname;
	}


	/**
	 * Sets the nickname attribute of the Contact object
	 *
	 * @param tmp The new nickname value
	 */
	public void setNickname(String tmp) {
		this.nickname = tmp;
	}


	/**
	 * Gets the role attribute of the Contact object
	 *
	 * @return The role value
	 */
	public String getRole() {
		return role;
	}


	/**
	 * Sets the role attribute of the Contact object
	 *
	 * @param tmp The new role value
	 */
	public void setRole(String tmp) {
		this.role = tmp;
	}


	/**
	 * Gets the birthDate attribute of the Contact object
	 *
	 * @return The birthDate value
	 */
	public java.sql.Timestamp getBirthDate() {
		return birthDate;
	}


	/**
	 * Gets the secretWord attribute of the Contact object
	 *
	 * @return The secretWord value
	 */
	public String getSecretWord() {
		return secretWord;
	}


	/**
	 * Sets the secretWord attribute of the Contact object
	 *
	 * @param tmp The new secretWord value
	 */
	public void setSecretWord(String tmp) {
		this.secretWord = tmp;
	}


	/**
	 * Sets the revenue attribute of the Contact object
	 *
	 * @param tmp The new revenue value
	 */
	public void setRevenue(double tmp) {
		this.revenue = tmp;
	}

	/**
	 * Sets the revenue attribute of the Contact object
	 *
	 * @param tmp The new revenue value
	 */
	public void setRevenue(String tmp) {
		try{
			this.revenue = Double.parseDouble(tmp);
		}catch (Exception e) {
			this.revenue = 0;
		}
	}

	/**
	 * Sets the potential attribute of the Contact object
	 *
	 * @param tmp The new potential value
	 */
	public void setPotential(double tmp) {
		this.potential = tmp;
	}


	/**
	 * Sets the potential attribute of the Contact object
	 *
	 * @param tmp The new potential value
	 */
	public void setPotential(String tmp) {
		this.potential = Double.parseDouble(tmp);
	}


	/**
	 * Sets the industryTempCode attribute of the Contact object
	 *
	 * @param tmp The new industryTempCode value
	 */
	public void setIndustryTempCode(int tmp) {
		this.industryTempCode = tmp;
	}


	/**
	 * Sets the industryTempCode attribute of the Contact object
	 *
	 * @param tmp The new industryTempCode value
	 */
	public void setIndustryTempCode(String tmp) {
		this.industryTempCode = Integer.parseInt(tmp);
	}


	/**
	 * Gets the revenue attribute of the Contact object
	 *
	 * @return The revenue value
	 */
	public double getRevenue() {
		return revenue;
	}


	/**
	 * Gets the potential attribute of the Contact object
	 *
	 * @return The potential value
	 */
	public double getPotential() {
		return potential;
	}


	/**
	 * Gets the grossPotential attribute of the Contact object
	 *
	 * @param divisor Description of the Parameter
	 * @return The grossPotential value
	 */
	public double getGrossPotential(int divisor) {
		return (java.lang.Math.round(potential) / (double) divisor);
	}


	/**
	 * Gets the industryTempCode attribute of the Contact object
	 *
	 * @return The industryTempCode value
	 */
	public int getIndustryTempCode() {
		return industryTempCode;
	}


	/**
	 * Sets the birthDate attribute of the Contact object
	 *
	 * @param tmp The new birthDate value
	 */
	public void setBirthDate(java.sql.Timestamp tmp) {
		this.birthDate = tmp;
	}


	/**
	 * Sets the birthDate attribute of the Contact object
	 *
	 * @param tmp The new birthDate value
	 */
	public void setBirthDate(String tmp) {
		this.birthDate = DatabaseUtils.parseTimestamp(tmp, Locale.getDefault(), true);
	}


	/**
	 * Sets the OwnerName attribute of the Contact object
	 *
	 * @param ownerName The new OwnerName value
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	/**
	 * Sets the hasEnabledOwnerAccount attribute of the Contact object
	 *
	 * @param hasEnabledOwnerAccount The new hasEnabledOwnerAccount value
	 */
	public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
		this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
	}


	/**
	 * Sets the noEmail attribute of the Contact object
	 *
	 * @param tmp The new noEmail value
	 */
	public void setNoEmail(boolean tmp) {
		this.noEmail = tmp;
	}


	/**
	 * Sets the employee attribute of the Contact object
	 *
	 * @param tmp The new employee value
	 */
	public void setEmployee(boolean tmp) {
		this.employee = tmp;
	}


	/**
	 * Sets the employee attribute of the Contact object
	 *
	 * @param tmp The new employee value
	 */
	public void setEmployee(String tmp) {
		this.employee = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the noMail attribute of the Contact object
	 *
	 * @param tmp The new noMail value
	 */
	public void setNoMail(boolean tmp) {
		this.noMail = tmp;
	}


	/**
	 * Sets the noPhone attribute of the Contact object
	 *
	 * @param tmp The new noPhone value
	 */
	public void setNoPhone(boolean tmp) {
		this.noPhone = tmp;
	}


	/**
	 * Sets the noPhone attribute of the Contact object
	 *
	 * @param tmp The new noPhone value
	 */
	public void setNoPhone(String tmp) {
		this.noPhone = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the noTextMessage attribute of the Contact object
	 *
	 * @param tmp The new noTextMessage value
	 */
	public void setNoTextMessage(boolean tmp) {
		this.noTextMessage = tmp;
	}


	/**
	 * Sets the noTextMessage attribute of the Contact object
	 *
	 * @param tmp The new noTextMessage value
	 */
	public void setNoTextMessage(String tmp) {
		this.noTextMessage = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the noInstantMessage attribute of the Contact object
	 *
	 * @param tmp The new noInstantMessage value
	 */
	public void setNoInstantMessage(boolean tmp) {
		this.noInstantMessage = tmp;
	}


	/**
	 * Sets the noInstantMessage attribute of the Contact object
	 *
	 * @param tmp The new noInstantMessage value
	 */
	public void setNoInstantMessage(String tmp) {
		this.noInstantMessage = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the noFax attribute of the Contact object
	 *
	 * @param tmp The new noFax value
	 */
	public void setNoFax(boolean tmp) {
		this.noFax = tmp;
	}


	/**
	 * Sets the noFax attribute of the Contact object
	 *
	 * @param tmp The new noFax value
	 */
	public void setNoFax(String tmp) {
		this.noFax = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the accessType attribute of the Contact object
	 *
	 * @param accessType The new accessType value
	 */
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}


	/**
	 * Sets the accessType attribute of the Contact object
	 *
	 * @param accessType The new accessType value
	 */
	public void setAccessType(String accessType) {
		this.accessType = Integer.parseInt(accessType);
	}


	/**
	 * Sets the clientId attribute of the Contact object
	 *
	 * @param clientId The new clientId value
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}


	/**
	 * Sets the clientId attribute of the Contact object
	 *
	 * @param clientId The new clientId value
	 */
	public void setClientId(String clientId) {
		this.clientId = Integer.parseInt(clientId);
	}


	/**
	 * Gets the clientId attribute of the Contact object
	 *
	 * @return The clientId value
	 */
	public int getClientId() {
		return clientId;
	}


	/**
	 * Sets the statusId attribute of the Contact object
	 *
	 * @param tmp The new statusId value
	 */
	public void setStatusId(int tmp) {
		this.statusId = tmp;
	}


	/**
	 * Sets the statusId attribute of the Contact object
	 *
	 * @param tmp The new statusId value
	 */
	public void setStatusId(String tmp) {
		this.statusId = Integer.parseInt(tmp);
	}


	/**
	 * Sets the importId attribute of the Contact object
	 *
	 * @param tmp The new importId value
	 */
	public void setImportId(int tmp) {
		this.importId = tmp;
	}


	/**
	 * Sets the importId attribute of the Contact object
	 *
	 * @param tmp The new importId value
	 */
	public void setImportId(String tmp) {
		this.importId = Integer.parseInt(tmp);
	}


	/**
	 * Sets the hasAccess attribute of the Contact object
	 *
	 * @param tmp The new hasAccess value
	 */
	public void setHasAccess(boolean tmp) {
		this.hasAccess = tmp;
	}


	/**
	 * Sets the hasAccess attribute of the Contact object
	 *
	 * @param tmp The new hasAccess value
	 */
	public void setHasAccess(String tmp) {
		this.hasAccess = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the isEnabled attribute of the Contact object
	 *
	 * @param tmp The new isEnabled value
	 */
	public void setIsEnabled(boolean tmp) {
		this.isEnabled = tmp;
	}


	/**
	 * Sets the isEnabled attribute of the Contact object
	 *
	 * @param tmp The new isEnabled value
	 */
	public void setIsEnabled(String tmp) {
		this.isEnabled = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the forceDelete attribute of the Contact object
	 *
	 * @param tmp The new forceDelete value
	 */
	public void setForceDelete(boolean tmp) {
		this.forceDelete = tmp;
	}


	/**
	 * Sets the forceDelete attribute of the Contact object
	 *
	 * @param tmp The new forceDelete value
	 */
	public void setForceDelete(String tmp) {
		this.forceDelete = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Gets the hasAccess attribute of the Contact object
	 *
	 * @return The hasAccess value
	 */
	public boolean getHasAccess() {
		return hasAccess;
	}


	/**
	 * Gets the isEnabled attribute of the Contact object
	 *
	 * @return The isEnabled value
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}


	/**
	 * Sets the trashedDate attribute of the Contact object
	 *
	 * @param tmp The new trashedDate value
	 */
	public void setTrashedDate(java.sql.Timestamp tmp) {
		this.trashedDate = tmp;
	}


	/**
	 * Sets the trashedDate attribute of the Contact object
	 *
	 * @param tmp The new trashedDate value
	 */
	public void setTrashedDate(String tmp) {
		this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 * Gets the trashedDate attribute of the Contact object
	 *
	 * @return The trashedDate value
	 */
	public java.sql.Timestamp getTrashedDate() {
		return trashedDate;
	}


	/**
	 * Gets the trashed attribute of the Contact object
	 *
	 * @return The trashed value
	 */
	public boolean isTrashed() {
		return (trashedDate != null);
	}


	/**
	 * Gets the forceDelete attribute of the Contact object
	 *
	 * @return The forceDelete value
	 */
	public boolean getForceDelete() {
		return forceDelete;
	}


	/**
	 * Gets the statusId attribute of the Contact object
	 *
	 * @return The statusId value
	 */
	public int getStatusId() {
		return statusId;
	}


	/**
	 * Gets the importId attribute of the Contact object
	 *
	 * @return The importId value
	 */
	public int getImportId() {
		return importId;
	}


	/**
	 * Gets the accessType attribute of the Contact object
	 *
	 * @return The accessType value
	 */
	public int getAccessType() {
		return accessType;
	}


	/**
	 * Gets the accessType attribute of the Contact object
	 *
	 * @return The accessType value
	 */
	public String getAccessTypeString() {
		return String.valueOf(accessType);
	}


	/**
	 * Gets the employee attribute of the Contact object
	 *
	 * @return The employee value
	 */
	public boolean getEmployee() {
		return employee;
	}


	/**
	 * Gets the hasEnabledOwnerAccount attribute of the Contact object
	 *
	 * @return The hasEnabledOwnerAccount value
	 */
	public boolean getHasEnabledOwnerAccount() {
		return hasEnabledOwnerAccount;
	}


	/**
	 * Gets the url attribute of the Contact object
	 *
	 * @return The url value
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * Gets the hasOpportunities attribute of the Contact object
	 *
	 * @return The hasOpportunities value
	 */
	public boolean getHasOpportunities() {
		return hasOpportunities;
	}


	/**
	 * Sets the hasOpportunities attribute of the Contact object
	 *
	 * @param hasOpportunities The new hasOpportunities value
	 */
	public void setHasOpportunities(boolean hasOpportunities) {
		this.hasOpportunities = hasOpportunities;
	}


	/**
	 * Sets the url attribute of the Contact object
	 *
	 * @param url The new url value
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * Sets the primaryContact attribute of the Contact object
	 *
	 * @param primaryContact The new primaryContact value
	 */
	public void setPrimaryContact(boolean primaryContact) {
		this.primaryContact = primaryContact;
	}


	/**
	 * Sets the primaryContact attribute of the Contact object
	 *
	 * @param tmp The new primaryContact value
	 */
	public void setPrimaryContact(String tmp) {
		this.primaryContact = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the typeList attribute of the Contact object
	 *
	 * @param typeList The new typeList value
	 */
	public void setTypeList(ArrayList typeList) {
		this.typeList = typeList;
	}


	/**
	 * Gets the typeList attribute of the Contact object
	 *
	 * @return The typeList value
	 */
	public ArrayList getTypeList() {
		return typeList;
	}


	/**
	 * Gets the primaryContact attribute of the Contact object
	 *
	 * @return The primaryContact value
	 */
	public boolean getPrimaryContact() {
		return primaryContact;
	}


	/**
	 * Returns a name for the contact checking (last,first) name and company name
	 * in that order
	 *
	 * @return The validName value
	 */
	public String getValidName() {
		String validName = StringUtils.toString(getNameLastFirst());
		if ("".equals(validName) && !"".equals(StringUtils.toString(company))) {
			validName = company;
		}
		return validName;
	}


	/**
	 * Gets the fullNameAbbr attribute of the Contact object
	 *
	 * @return The fullNameAbbr value
	 */
	public String getFullNameAbbr() {
		StringBuffer out = new StringBuffer();
		if (this.getNameFirst() != null && this.getNameFirst().length() > 0) {
			out.append(this.getNameFirst().charAt(0) + ". ");
		}
		if (this.getNameLast() != null && this.getNameLast().length() > 0) {
			out.append(this.getNameLast());
		}
		return out.toString();
	}


	/**
	 * Gets the custom1 attribute of the Contact object
	 *
	 * @return The custom1 value
	 */
	public int getCustom1() {
		return custom1;
	}


	/**
	 * Sets the custom1 attribute of the Contact object
	 *
	 * @param custom1 The new custom1 value
	 */
	public void setCustom1(int custom1) {
		this.custom1 = custom1;
	}


	/**
	 * Description of the Method
	 *
	 * @return Description of the Return Value
	 */
	public boolean hasEnabledAccount() {
		return hasEnabledAccount;
	}


	/**
	 * Sets the hasEnabledAccount attribute of the Contact object
	 *
	 * @param hasEnabledAccount The new hasEnabledAccount value
	 */
	public void setHasEnabledAccount(boolean hasEnabledAccount) {
		this.hasEnabledAccount = hasEnabledAccount;
	}


	/**
	 * Sets the custom1 attribute of the Contact object
	 *
	 * @param custom1 The new custom1 value
	 */
	public void setCustom1(String custom1) {
		this.custom1 = Integer.parseInt(custom1);
	}


	/**
	 * Sets the buildDetails attribute of the Contact object
	 *
	 * @param tmp The new buildDetails value
	 */
	public void setBuildDetails(boolean tmp) {
		this.buildDetails = tmp;
	}


	/**
	 * Sets the buildTypes attribute of the Contact object
	 *
	 * @param tmp The new buildTypes value
	 */
	public void setBuildTypes(boolean tmp) {
		this.buildTypes = tmp;
	}


	/**
	 * Sets the buildTypes attribute of the Contact object
	 *
	 * @param tmp The new buildTypes value
	 */
	public void setBuildTypes(String tmp) {
		this.buildTypes = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * @return the businessNameTwo
	 */
	public String getBusinessNameTwo() {
		return businessNameTwo;
	}


	/**
	 * @return the dunsNumber
	 */
	public String getDunsNumber() {
		return dunsNumber;
	}


	/**
	 * @return the dunsType
	 */
	public String getDunsType() {
		return dunsType;
	}


	/**
	 * Gets the Employees attribute of the Organization object
	 *
	 * @return The Employees value
	 */
	public int getEmployees() {
		return employees;
	}


	/**
	 * @return the sicCode
	 */
	public int getSicCode() {
		return sicCode;
	}


	/**
	 * @return the yearStarted
	 */
	public int getYearStarted() {
		return yearStarted;
	}

	public void setSicDescription(String tmp) {
		this.sicDescription = tmp;
	}

	public String getSicDescription() {
		return sicDescription;
	}

	/**
	 * @param businessNameTwo the businessNameTwo to set
	 */
	public void setBusinessNameTwo(String businessNameTwo) {
		this.businessNameTwo = businessNameTwo;
	}


	/**
	 * @param dunsNumber the dunsNumber to set
	 */
	public void setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
	}


	/**
	 * @param dunsType the dunsType to set
	 */
	public void setDunsType(String dunsType) {
		this.dunsType = dunsType;
	}


	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(int employees) {
		this.employees = employees;
	}


	/**
	 * Sets the Employees attribute of the Organization object
	 *
	 * @param employees The new Employees value
	 */
	public void setEmployees(String employees) {
		try {
			this.employees = Integer.parseInt(employees);
		} catch (Exception e) {
			this.employees = 0;
		}
	}


	/**
	 * @param sicCode the sicCode to set
	 */
	public void setSicCode(int sicCode) {
		this.sicCode = sicCode;
	}


	/**
	 * @param sicCode the sicCode to set
	 */
	public void setSicCode(String sicCode) {
		this.sicCode = Integer.parseInt(sicCode);
	}


	/**
	 * @param yearStarted the yearStarted to set
	 */
	public void setYearStarted(int yearStarted) {
		this.yearStarted = yearStarted;
	}


	/**
	 * @param yearStarted the yearStarted to set
	 */
	public void setYearStarted(String yearStarted) {
		if (!"".equals(yearStarted) && yearStarted != null) {
			this.yearStarted = Integer.parseInt(yearStarted);
		}
	}


	


	/**
	 * Sets the Id attribute of the Contact object
	 *
	 * @param tmp The new Id value
	 * @since 1.1
	 */
	public void setId(int tmp) {
		this.id = tmp;
		addressList.setContactId(tmp);
		phoneNumberList.setContactId(tmp);
		emailAddressList.setContactId(tmp);
		textMessageAddressList.setContactId(tmp);
		instantMessageAddressList.setContactId(tmp);
	}


	/**
	 * Sets the entered attribute of the Contact object
	 *
	 * @param tmp The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 * Sets the modified attribute of the Contact object
	 *
	 * @param tmp The new modified value
	 */
	public void setModified(java.sql.Timestamp tmp) {
		this.modified = tmp;
	}


	/**
	 * Sets the entered attribute of the Contact object
	 *
	 * @param tmp The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DateUtils.parseTimestampString(tmp);
	}


	/**
	 * Sets the modified attribute of the Contact object
	 *
	 * @param tmp The new modified value
	 */
	public void setModified(String tmp) {
		this.modified = DateUtils.parseTimestampString(tmp);
	}


	/**
	 * Sets the Owner attribute of the Opportunity object
	 *
	 * @param owner The new Owner value
	 */
	public void setOwner(String owner) {
		this.owner = Integer.parseInt(owner);
	}


	/**
	 * Sets the Owner attribute of the Opportunity object
	 *
	 * @param owner The new Owner value
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}


	/**
	 * Sets the Id attribute of the Contact object
	 *
	 * @param tmp The new Id value
	 * @since 1.1
	 */
	public void setId(String tmp) {
		this.setId(Integer.parseInt(tmp));
	}


	/**
	 * Sets the OrgId attribute of the Contact object
	 *
	 * @param tmp The new OrgId value
	 * @since 1.1
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 * Sets the OrgId attribute of the Contact object
	 *
	 * @param tmp The new OrgId value
	 * @since 1.2
	 */
	public void setOrgId(String tmp) {
		if (tmp != null) {
			this.orgId = Integer.parseInt(tmp);
		}
	}


	/**
	 * Sets the prospectClient attribute of the Contact object
	 *
	 * @param prospectClient The new prospectClient value
	 */
	public void setProspectClient(boolean prospectClient) {
		this.prospectClient = prospectClient;
	}


	/**
	 * Gets the prospectClient attribute of the Contact object
	 *
	 * @return The prospectClient value
	 */
	public boolean getProspectClient() {
		return prospectClient;
	}


	/**
	 * Sets the listSalutation attribute of the Contact object
	 *
	 * @param tmp The new listSalutation value
	 * @since 1.2
	 */
	public void setListSalutation(String tmp) {
		if (tmp != null) {
			this.listSalutation = Integer.parseInt(tmp);
		}
	}


	/**
	 * Sets the listSalutation attribute of the Contact object
	 *
	 * @param tmp The new listSalutation value
	 */
	public void setListSalutation(int tmp) {
		this.listSalutation = tmp;
	}


	/**
	 * Sets the orgSiteId attribute of the Contact object
	 *
	 * @param orgSiteId The new orgSiteId value
	 */
	public void setOrgSiteId(int orgSiteId) {
		this.orgSiteId = orgSiteId;
	}


	/**
	 * Sets the orgSiteId attribute of the Contact object
	 *
	 * @param tmp The new orgSiteId value
	 */
	public void setOrgSiteId(String tmp) {
		this.orgSiteId = Integer.parseInt(tmp);
	}


	/**
	 * Gets the orgSiteId attribute of the Organization object
	 *
	 * @return The orgSiteId value
	 */
	public int getOrgSiteId() {
		return orgSiteId;
	}


	/**
	 * Sets the ModifiedByName attribute of the Contact object
	 *
	 * @param modifiedByName The new ModifiedByName value
	 */
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}


	/**
	 * Sets the NameSalutation attribute of the Contact object
	 *
	 * @param tmp The new NameSalutation value
	 * @since 1.1
	 */
	public void setNameSalutation(String tmp) {
		this.nameSalutation = tmp;
	}


	/**
	 * Sets the NameFirst attribute of the Contact object
	 *
	 * @param tmp The new NameFirst value
	 * @since 1.1
	 */
	public void setNameFirst(String tmp) {
		this.nameFirst = tmp;
	}


	/**
	 * Sets the EnteredByName attribute of the Contact object
	 *
	 * @param enteredByName The new EnteredByName value
	 */
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}


	/**
	 * Sets the NameMiddle attribute of the Contact object
	 *
	 * @param tmp The new NameMiddle value
	 * @since 1.1
	 */
	public void setNameMiddle(String tmp) {
		this.nameMiddle = tmp;
	}


	/**
	 * Sets the NameLast attribute of the Contact object
	 *
	 * @param tmp The new NameLast value
	 * @since 1.1
	 */
	public void setNameLast(String tmp) {
		this.nameLast = tmp;
	}


	/**
	 * Sets the NameSuffix attribute of the Contact object
	 *
	 * @param tmp The new NameSuffix value
	 * @since 1.1
	 */
	public void setNameSuffix(String tmp) {
		this.nameSuffix = tmp;
	}


	/**
	 * Gets the assistant attribute of the Contact object
	 *
	 * @return The assistant value
	 */
	public int getAssistant() {
		return assistant;
	}


	/**
	 * Sets the assistant attribute of the Contact object
	 *
	 * @param tmp The new assistant value
	 */
	public void setAssistant(int tmp) {
		this.assistant = tmp;
	}


	/**
	 * Sets the assistant attribute of the Contact object
	 *
	 * @param tmp The new assistant value
	 */
	public void setAssistant(String tmp) {
		this.assistant = Integer.parseInt(tmp);
	}


	/**
	 * Sets the Company attribute of the Contact object
	 *
	 * @param tmp The new Company value
	 * @since 1.34
	 */
	public void setCompany(String tmp) {
		this.company = tmp;
	}


	/**
	 * Sets the AccountNumber attribute of the Contact object
	 *
	 * @param tmp The new AccountNumber value
	 */
	public void setAccountNumber(String tmp) {
		this.accountNumber = tmp;
	}


	/**
	 * Sets the Title attribute of the Contact object
	 *
	 * @param tmp The new Title value
	 * @since 1.1
	 */
	public void setTitle(String tmp) {
		if (tmp != null && tmp.length() > 80) {
			tmp = tmp.substring(0, 79);
		}
		this.title = tmp;
	}


	/**
	 * Sets the DepartmentName attribute of the Contact object
	 *
	 * @param tmp The new DepartmentName value
	 * @since 1.1
	 */
	public void setDepartmentName(String tmp) {
		this.departmentName = tmp;
	}


	/**
	 * Sets the Department attribute of the Contact object
	 *
	 * @param tmp The new Department value
	 * @since 1.1
	 */
	public void setDepartment(int tmp) {
		this.department = tmp;
	}


	/**
	 * Sets the Department attribute of the Contact object
	 *
	 * @param tmp The new Department value
	 * @since 1.1
	 */
	public void setDepartment(String tmp) {
		this.department = Integer.parseInt(tmp);
	}


	/**
	 * Sets the EmailAddresses attribute of the Contact object
	 *
	 * @param tmp The new EmailAddresses value
	 * @since 1.1
	 */
	public void setEmailAddressList(ContactEmailAddressList tmp) {
		this.emailAddressList = tmp;
	}


	/**
	 * Sets the ContactPhoneNumberList attribute of the Contact object
	 *
	 * @param tmp The new ContactPhoneNumberList value
	 * @since 1.13
	 */
	public void setPhoneNumberList(ContactPhoneNumberList tmp) {
		this.phoneNumberList = tmp;
	}


	/**
	 * Sets the textMessageAddressList attribute of the Contact object
	 *
	 * @param tmp The new textMessageAddressList value
	 */
	public void setTextMessageAddressList(ContactTextMessageAddressList tmp) {
		this.textMessageAddressList = tmp;
	}


	/**
	 * Gets the orgEnabled attribute of the Contact object
	 *
	 * @return The orgEnabled value
	 */
	public boolean getOrgEnabled() {
		return orgEnabled;
	}


	/**
	 * Gets the orgTrashedDate attribute of the Contact object
	 *
	 * @return The orgTrashedDate value
	 */
	public java.sql.Timestamp getOrgTrashedDate() {
		return orgTrashedDate;
	}
	 
	  public String getBirthDateString() {
		    String tmp = "";
		    try {
		      return DateFormat.getDateInstance(3).format(birthDate);
		    } catch (NullPointerException e) {
		    }
		    return tmp;
	  }


	/**
	 * Gets the noEmail attribute of the Contact object
	 *
	 * @return The getNoEmail value
	 */
	public boolean getNoEmail() {
		return noEmail;
	}


	/**
	 * Sets the NoEmail and noMail attribute of the Organization object
	 *
	 * @param tmp The new NoEmail value
	 */
	public void setNoEmail(String tmp) {
		this.noEmail = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the noMail attribute of the Contact object
	 *
	 * @param tmp The new noMail value
	 */
	public void setNoMail(String tmp) {
		this.noMail = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Gets the noMail attribute of the Contact object
	 *
	 * @return The noMail value
	 */
	public boolean getNoMail() {
		return noMail;
	}


	/**
	 * Gets the noPhone attribute of the Contact object
	 *
	 * @return The noPhone value
	 */
	public boolean getNoPhone() {
		return noPhone;
	}


	/**
	 * Gets the noTextMessage attribute of the Contact object
	 *
	 * @return The noTextMessage value
	 */
	public boolean getNoTextMessage() {
		return noTextMessage;
	}


	/**
	 * Gets the noInstantMessage attribute of the Contact object
	 *
	 * @return The noInstantMessage value
	 */
	public boolean getNoInstantMessage() {
		return noInstantMessage;
	}


	/**
	 * Gets the noFax attribute of the Contact object
	 *
	 * @return The noFax value
	 */
	public boolean getNoFax() {
		return noFax;
	}


	/**
	 * Sets the orgEnabled attribute of the Contact object
	 *
	 * @param orgEnabled The new orgEnabled value
	 */
	public void setOrgEnabled(boolean orgEnabled) {
		this.orgEnabled = orgEnabled;
	}


	/**
	 * Sets the orgTrashedDate attribute of the Contact object
	 *
	 * @param tmp The new orgTrashedDate value
	 */
	public void setOrgTrashedDate(java.sql.Timestamp tmp) {
		this.orgTrashedDate = tmp;
	}


	/**
	 * Sets the orgTrashedDate attribute of the Contact object
	 *
	 * @param tmp The new orgTrashedDate value
	 */
	public void setOrgTrashedDate(String tmp) {
		this.orgTrashedDate = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 * Sets the excludedFromCampaign attribute of the Contact object
	 *
	 * @param excludedFromCampaign The new excludedFromCampaign value
	 */
	public void setExcludedFromCampaign(boolean excludedFromCampaign) {
		this.excludedFromCampaign = excludedFromCampaign;
	}


	/**
	 * Sets the AddressList attribute of the Contact object
	 *
	 * @param tmp The new AddressList value
	 * @since 1.1
	 */
	public void setAddressList(ContactAddressList tmp) {
		this.addressList = tmp;
	}


	/**
	 * Sets the Notes attribute of the Contact object
	 *
	 * @param tmp The new Notes value
	 * @since 1.1
	 */
	public void setNotes(String tmp) {
		this.notes = tmp;
	}


	/**
	 * Sets the Site attribute of the Contact object
	 *
	 * @param tmp The new Site value
	 * @since 1.1
	 */
	public void setSite(String tmp) {
		this.site = tmp;
	}


	/**
	 * Sets the EmploymentType attribute of the Contact object
	 *
	 * @param tmp The new EmploymentType value
	 * @since 1.1
	 */
	public void setEmploymentType(int tmp) {
		this.employmentType = tmp;
	}


	/**
	 * Sets the EmploymentType attribute of the Contact object
	 *
	 * @param tmp The new EmploymentType value
	 * @since 1.2
	 */
	public void setEmploymentType(String tmp) {
		this.employmentType = Integer.parseInt(tmp);
	}


	/**
	 * Sets the Locale attribute of the Contact object
	 *
	 * @param tmp The new Locale value
	 * @since 1.1
	 */
	public void setLocale(int tmp) {
		this.locale = tmp;
	}


	/**
	 * Sets the Locale attribute of the Contact object
	 *
	 * @param tmp The new Locale value
	 * @since 1.2
	 */
	public void setLocale(String tmp) {
		this.locale = Integer.parseInt(tmp);
	}


	/**
	 * Sets the EmployeeId attribute of the Contact object
	 *
	 * @param tmp The new EmployeeId value
	 * @since 1.1
	 */
	public void setEmployeeId(String tmp) {
		this.employeeId = tmp;
	}


	/**
	 * Sets the StartOfDay attribute of the Contact object
	 *
	 * @param tmp The new StartOfDay value
	 * @since 1.1
	 */
	public void setStartOfDay(String tmp) {
		this.startOfDay = tmp;
	}


	/**
	 * Sets the EndOfDay attribute of the Contact object
	 *
	 * @param tmp The new EndOfDay value
	 * @since 1.1
	 */
	public void setEndOfDay(String tmp) {
		this.endOfDay = tmp;
	}


	/**
	 * Sets the Enabled attribute of the Contact object
	 *
	 * @param tmp The new Enabled value
	 * @since 1.2
	 */
	public void setEnabled(boolean tmp) {
		this.enabled = tmp;
	}


	/**
	 * Sets the Enabled attribute of the Contact object
	 *
	 * @param tmp The new Enabled value
	 * @since 1.2
	 */
	public void setEnabled(String tmp) {
		enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
	}


	/**
	 * Sets the EnteredBy attribute of the Contact object
	 *
	 * @param tmp The new EnteredBy value
	 * @since 1.12
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 * Sets the enteredBy attribute of the Contact object
	 *
	 * @param tmp The new enteredBy value
	 */
	public void setEnteredBy(String tmp) {
		this.enteredBy = Integer.parseInt(tmp);
	}


	/**
	 * Sets the ModifiedBy attribute of the Contact object
	 *
	 * @param tmp The new ModifiedBy value
	 * @since 1.12
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}


	/**
	 * Sets the modifiedBy attribute of the Contact object
	 *
	 * @param tmp The new modifiedBy value
	 */
	public void setModifiedBy(String tmp) {
		this.modifiedBy = Integer.parseInt(tmp);
	}


	/**
	 * Sets the HasAccount attribute of the Contact object
	 *
	 * @param tmp The new HasAccount value
	 * @since 1.20
	 */
	public void setHasAccount(boolean tmp) {
		this.hasAccount = tmp;
	}


	/**
	 * Gets the approved attribute of the Contact object
	 *
	 * @return The approved value
	 */
	public boolean isApproved() {
		return (statusId != Import.PROCESSED_UNAPPROVED);
	}


	/**
	 * Since dynamic fields cannot be auto-populated, passing the request to this
	 * method will populate the indicated fields.
	 *
	 * @param context The new requestItems value
	 * @since 1.15
	 */
	public void setRequestItems(ActionContext context) {
		phoneNumberList = new ContactPhoneNumberList(context);
		addressList = new ContactAddressList(context.getRequest());
		emailAddressList = new ContactEmailAddressList(context.getRequest());
		textMessageAddressList = new ContactTextMessageAddressList(
				context.getRequest());
		instantMessageAddressList = new ContactInstantMessageAddressList(
				context.getRequest());
	}


	/**
	 * Sets the typeList attribute of the Contact object
	 *
	 * @param criteriaString The new typeList value
	 */
	public void setTypeList(String[] criteriaString) {
		if (criteriaString != null) {
			String[] params = criteriaString;
			typeList = new ArrayList(Arrays.asList(params));
		} else {
			typeList = new ArrayList();
		}
	}


	/**
	 * Adds a feature to the Type attribute of the Contact object
	 *
	 * @param typeId The feature to be added to the Type attribute
	 */
	public void addType(int typeId) {
		if (typeList == null) {
			typeList = new ArrayList();
		}
		typeList.add(String.valueOf(typeId));
	}


	/**
	 * Adds a feature to the Type attribute of the Contact object
	 *
	 * @param typeId The feature to be added to the Type attribute
	 */
	public void addType(String typeId) {
		if (typeList == null) {
			typeList = new ArrayList();
		}
		typeList.add(typeId);
	}


	/**
	 * Gets the typesNameString attribute of the Contact object
	 *
	 * @return The typesNameString value
	 */
	public String getTypesNameString() {
		StringBuffer types = new StringBuffer();
		Iterator i = getTypes().iterator();
		while (i.hasNext()) {
			LookupElement thisElt = (LookupElement) i.next();
			types.append(thisElt.getDescription());
			if (i.hasNext()) {
				types.append(", ");
			}
		}
		return types.toString();
	}


	/**
	 * Description of the Method
	 *
	 * @param type Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean hasType(int type) {
		boolean gotType = false;
		Iterator i = getTypes().iterator();
		while (i.hasNext()) {
			LookupElement thisElt = (LookupElement) i.next();
			if (thisElt.getCode() == type) {
				gotType = true;
				break;
			}
		}
		return gotType;
	}


	/**
	 * Gets the entered attribute of the Contact object
	 *
	 * @return The entered value
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}


	/**
	 * Gets the modified attribute of the Contact object
	 *
	 * @return The modified value
	 */
	public java.sql.Timestamp getModified() {
		return modified;
	}


	/**
	 * Gets the user_id attribute of the Contact object
	 *
	 * @return The user_id value
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * Sets the userId attribute of the Contact object
	 *
	 * @param tmp The new userId value
	 */
	public void setUserId(int tmp) {
		userId = tmp;
	}


	/**
	 * Sets the userId attribute of the Contact object
	 *
	 * @param tmp The new userId value
	 */
	public void setUserId(String tmp) {
		userId = Integer.parseInt(tmp);
	}


	/**
	 * Sets the types attribute of the Contact object
	 *
	 * @param types The new types value
	 */
	public void setTypes(LookupList types) {
		this.types = types;
	}


	/**
	 * Gets the types attribute of the Contact object
	 *
	 * @return The types value
	 */
	public LookupList getTypes() {
		return types;
	}


	/**
	 * Gets the modifiedString attribute of the Contact object
	 *
	 * @return The modifiedString value
	 */
	public String getModifiedString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
					modified);
		} catch (NullPointerException e) {
		}
		return tmp;
	}


	/**
	 * Gets the enteredString attribute of the Contact object
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
	 * Gets the ModifiedByName attribute of the Contact object
	 *
	 * @return The ModifiedByName value
	 */
	public String getModifiedByName() {
		return modifiedByName;
	}


	/**
	 * Gets the EnteredByName attribute of the Contact object
	 *
	 * @return The EnteredByName value
	 */
	public String getEnteredByName() {
		return enteredByName;
	}


	/**
	 * Gets the OwnerName attribute of the Contact object
	 *
	 * @return The OwnerName value
	 */
	public String getOwnerName() {
		return ownerName;
	}


	/**
	 * Gets the Owner attribute of the Opportunity object
	 *
	 * @return The Owner value
	 */
	public int getOwner() {
		return owner;
	}


	/**
	 * Gets the ownerString attribute of the Contact object
	 *
	 * @return The ownerString value
	 */
	public String getOwnerString() {
		return String.valueOf(owner);
	}


	/**
	 * Gets the Id attribute of the Contact object
	 *
	 * @return The Id value
	 * @since 1.1
	 */
	public int getId() {
		return id;
	}


	/**
	 * Gets the OrgId attribute of the Contact object
	 *
	 * @return The OrgId value
	 * @since 1.1
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 * Gets the listSalutation attribute of the Contact object
	 *
	 * @return The OrgId value
	 * @since 1.1
	 */
	public int getListSalutation() {
		return listSalutation;
	}


	/**
	 * Sets the orgName attribute of the Contact object
	 *
	 * @param orgName The new orgName value
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	/**
	 * Gets the OrgName attribute of the Contact object
	 *
	 * @return The OrgName value
	 * @since 1.28
	 */
	public String getOrgName() {
		return orgName;
	}


	/**
	 * Gets the NameSalutation attribute of the Contact object
	 *
	 * @return The NameSalutation value
	 * @since 1.1
	 */
	public String getNameSalutation() {
		return nameSalutation;
	}


	/**
	 * Gets the NameFirst attribute of the Contact object
	 *
	 * @return The NameFirst value
	 * @since 1.1
	 */
	public String getNameFirst() {
		if (nameFirst!=null)
		return nameFirst;
		return "";
	}


	/**
	 * Gets the NameMiddle attribute of the Contact object
	 *
	 * @return The NameMiddle value
	 * @since 1.1
	 */
	public String getNameMiddle() {
		return nameMiddle;
	}


	/**
	 * Gets the NameLast attribute of the Contact object
	 *
	 * @return The NameLast value
	 * @since 1.1
	 */
	public String getNameLast() {
		if (nameLast != null)
		return nameLast;
		return "";
	}


	/**
	 * Gets the NameSuffix attribute of the Contact object
	 *
	 * @return The NameSuffix value
	 * @since 1.1
	 */
	public String getNameSuffix() {
		return nameSuffix;
	}


	/**
	 * Gets the NameFull attribute of the Contact object
	 *
	 * @return The NameFull value
	 * @since 1.33
	 */
	public String getNameFull() {
		StringBuffer out = new StringBuffer();

		if (nameSalutation != null && nameSalutation.length() > 0) {
			out.append(nameSalutation + " ");
		}

		if (nameFirst != null && nameFirst.length() > 0) {
			out.append(nameFirst + " ");
		}

		if (nameMiddle != null && nameMiddle.length() > 0) {
			out.append(nameMiddle + " ");
		}

		if (nameLast != null && nameLast.length() > 0) {
			out.append(nameLast + " ");
		}

		if (nameSuffix != null && nameSuffix.length() > 0) {
			out.append(nameSuffix + " ");
		}

		if (out.toString().length() == 0) {
			return company;
		}

		return out.toString().trim();
	}


	/**
	 * Gets the NameFirstLast attribute of the Contact object
	 *
	 * @return The NameFirstLast value
	 */
	public String getNameFirstLast() {
		StringBuffer out = new StringBuffer();

		if (nameFirst != null && nameFirst.length() > 0) {
			out.append(nameFirst + " ");
		}

		if (nameLast != null && nameLast.length() > 0) {
			out.append(nameLast);
		}

		if (out.toString().length() == 0) {
			return company;
		}

		return out.toString().trim();
	}


	/**
	 * Gets the userIdParams attribute of the Contact class
	 *
	 * @return The userIdParams value
	 */
	public static ArrayList getUserIdParams() {
		ArrayList thisList = new ArrayList();
		thisList.add("enteredBy");
		thisList.add("modifiedBy");
		thisList.add("owner");
		return thisList;
	}


	/**
	 * Gets the timeZoneParams attribute of the Contact class
	 *
	 * @return The timeZoneParams value
	 */
	public static ArrayList getTimeZoneParams() {
		ArrayList thisList = new ArrayList();
		thisList.add("birthDate");
		return thisList;
	}


	/**
	 * Gets the numberParams attribute of the Organization class
	 *
	 * @return The numberParams value
	 */
	public static ArrayList getNumberParams() {
		ArrayList thisList = new ArrayList();
		thisList.add("revenue");
		thisList.add("potential");
		return thisList;
	}


	/**
	 * Gets the nameFirstInitialLast attribute of the Contact object
	 *
	 * @return The nameFirstInitialLast value
	 */
	public String getNameFirstInitialLast() {
		StringBuffer out = new StringBuffer();
		if (nameFirst != null && nameFirst.trim().length() > 0) {
			out.append(String.valueOf(nameFirst.charAt(0)) + ".");
		}
		if (nameLast != null && nameLast.trim().length() > 0) {
			if (out.length() > 0) {
				out.append(" ");
			}
			out.append(nameLast);
		}
		if (out.toString().length() == 0) {
			return null;
		}
		return out.toString().trim();
	}


	/**
	 * Gets the NameLastFirst attribute of the Contact object
	 *
	 * @return The NameLastFirst value
	 */
	public String getNameLastFirst() {
		return Contact.getNameLastFirst(nameLast, nameFirst);
	}


	/**
	 * Gets the Company attribute of the Contact object
	 *
	 * @return The Company value
	 * @since 1.34
	 */

	public String getCompany() {
		if (company == null || company.trim().equals("")) {
			return orgName;
		} else {
			return company;
		}
	}


	/**
	 * Gets the accountNumber attribute of the Contact object
	 *
	 * @return The accountNumber value
	 */
	public String getAccountNumber() {
		return accountNumber;
	}


	/**
	 * Gets the affiliation attribute of the Contact object
	 *
	 * @return The affiliation value
	 */
	public String getAffiliation() {
		if (orgId > -1) {
			return orgName;
		} else {
			return company;
		}
	}


	/**
	 * Gets the companyOnly attribute of the Contact object
	 *
	 * @return The companyOnly value
	 */
	public String getCompanyOnly() {
		return company;
	}


	/**
	 * Gets the Title attribute of the Contact object
	 *
	 * @return The Title value
	 * @since 1.1
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Gets the DepartmentName attribute of the Contact object
	 *
	 * @return The DepartmentName value
	 * @since 1.1
	 */
	public String getDepartmentName() {
		return departmentName;
	}


	/**
	 * Gets the Department attribute of the Contact object
	 *
	 * @return The Department value
	 * @since 1.1
	 */
	public int getDepartment() {
		return department;
	}


	/**
	 * Gets the EmailAddresses attribute of the Contact object
	 *
	 * @return The EmailAddresses value
	 * @since 1.1
	 */
	public ContactEmailAddressList getEmailAddressList() {
		return emailAddressList;
	}


	/**
	 * Gets the ContactPhoneNumberList attribute of the Contact object
	 *
	 * @param thisType Description of Parameter
	 * @return The PhoneNumber value
	 * @since 1.13
	 */
	public String getPhoneNumber(String thisType) {
		return phoneNumberList.getPhoneNumber(thisType);
	}


	/**
	 * Description of the Method
	 */
	public void resetBaseInfo() {
		this.nameFirst = null;
		this.nameLast = null;
		this.nameMiddle = null;
		this.nameSalutation = null;
		this.nameSuffix = null;
		this.id = -1;
		this.notes = null;
		this.title = null;

		Iterator i = emailAddressList.iterator();
		while (i.hasNext()) {
			ContactEmailAddress thisAddress = (ContactEmailAddress) i.next();
			thisAddress.setId(-1);
		}

		Iterator j = phoneNumberList.iterator();
		while (j.hasNext()) {
			ContactPhoneNumber thisNumber = (ContactPhoneNumber) j.next();
			thisNumber.setId(-1);
		}

		Iterator k = addressList.iterator();
		while (k.hasNext()) {
			ContactAddress thisAddress = (ContactAddress) k.next();
			thisAddress.setId(-1);
		}

		Iterator l = textMessageAddressList.iterator();
		while (l.hasNext()) {
			ContactTextMessageAddress thisAddress = (ContactTextMessageAddress) l.next();
			thisAddress.setId(-1);
		}

		Iterator m = instantMessageAddressList.iterator();
		while (m.hasNext()) {
			ContactInstantMessageAddress thisAddress = (ContactInstantMessageAddress) m.next();
			thisAddress.setId(-1);
		}
	}


	/**
	 * Gets the phoneNumber attribute of the Contact object
	 *
	 * @param position Description of the Parameter
	 * @return The phoneNumber value
	 */
	public String getPhoneNumber(int position) {
		return phoneNumberList.getPhoneNumber(position);
	}


	/**
	 * Gets the EmailAddress attribute of the Contact object
	 *
	 * @param thisType Description of Parameter
	 * @return The EmailAddress value
	 * @since 1.10
	 */
	public String getEmailAddress(String thisType) {
		return emailAddressList.getEmailAddress(thisType);
	}


	/**
	 * Gets the EmailAddressTag attribute of the Contact object
	 *
	 * @param thisType   Description of Parameter
	 * @param linkText   Description of Parameter
	 * @param noLinkText Description of Parameter
	 * @return The EmailAddressTag value
	 * @since 1.50
	 */
	public String getEmailAddressTag(String thisType, String linkText, String noLinkText) {
		String tmpAddress = emailAddressList.getEmailAddress(thisType);
		if ("".equals(thisType) && !("".equals(linkText) || "&nbsp;".equals(
				linkText) || (linkText == null))) {
			return "<a href=\"mailto:" + this.getPrimaryEmailAddress() + "\">" + linkText + "</a>";
		} else if (tmpAddress != null && !"".equals(tmpAddress)) {
			return "<a href=\"mailto:" + this.getEmailAddress(thisType) + "\">" + linkText + "</a>";
		} else {
			return noLinkText;
		}
	}


	/**
	 * Gets the EmailAddress attribute of the Contact object using the specified
	 * position in the emailAddressList
	 *
	 * @param thisPosition Description of Parameter
	 * @return The EmailAddress value
	 * @since 1.24
	 */
	public String getEmailAddress(int thisPosition) {
		return emailAddressList.getEmailAddress(thisPosition);
	}


	/**
	 * Gets the EmailAddressTypeId attribute of the Contact object
	 *
	 * @param thisPosition Description of Parameter
	 * @return The EmailAddressTypeId value
	 * @since 1.24
	 */
	public int getEmailAddressTypeId(int thisPosition) {
		return emailAddressList.getEmailAddressTypeId(thisPosition);
	}


	/**
	 * Gets the Address attribute of the Contact object
	 *
	 * @param thisType Description of Parameter
	 * @return The Address value
	 * @since 1.1
	 */
	public Address getAddress(String thisType) {
		return addressList.getAddress(thisType);
	}


	/**
	 * Gets the primaryEmailAddress attribute of the Contact object
	 *
	 * @return The primaryEmailAddress value
	 */
	public String getPrimaryEmailAddress() {
		return emailAddressList.getPrimaryEmailAddress();
	}


	/**
	 * Gets the primaryPhoneNumber attribute of the Contact object
	 *
	 * @return The primaryPhoneNumber value
	 */
	public String getPrimaryPhoneNumber() {
		return phoneNumberList.getPrimaryPhoneNumber();
	}


	/**
	 * Gets the primaryTextMessageAddress attribute of the Contact object
	 *
	 * @return The primaryTextMessageAddress value
	 */
	public String getPrimaryTextMessageAddress() {
		return textMessageAddressList.getPrimaryTextMessageAddress();
	}


	/**
	 * Gets the primaryInstantMessageAddress attribute of the Contact object
	 *
	 * @return The primaryInstantMessageAddress value
	 */
	public String getPrimaryInstantMessageAddress() {
		return instantMessageAddressList.getPrimaryInstantMessageAddress();
	}


	/**
	 * Gets the primaryAddress attribute of the Contact object
	 *
	 * @return The primaryAddress value
	 */
	public Address getPrimaryAddress() {
		return addressList.getPrimaryAddress();
	}


	/**
	 * Gets the Addresses attribute of the Contact object
	 *
	 * @return The Addresses value
	 * @since 1.1
	 */
	public ContactAddressList getAddressList() {
		return addressList;
	}


	/**
	 * Gets the Notes attribute of the Contact object
	 *
	 * @return The Notes value
	 * @since 1.1
	 */
	public String getNotes() {
		return notes;
	}


	/**
	 * Gets the Site attribute of the Contact object
	 *
	 * @return The Site value
	 * @since 1.1
	 */
	public String getSite() {
		return site;
	}


	/**
	 * Gets the EmploymentType attribute of the Contact object
	 *
	 * @return The EmploymentType value
	 * @since 1.1
	 */
	public int getEmploymentType() {
		return employmentType;
	}


	/**
	 * Gets the Locale attribute of the Contact object
	 *
	 * @return The Locale value
	 * @since 1.1
	 */
	public int getLocale() {
		return locale;
	}


	/**
	 * Gets the EmployeeId attribute of the Contact object
	 *
	 * @return The EmployeeId value
	 * @since 1.1
	 */
	public String getEmployeeId() {
		return employeeId;
	}


	/**
	 * Gets the StartOfDay attribute of the Contact object
	 *
	 * @return The StartOfDay value
	 * @since 1.esn'
	 */
	public String getStartOfDay() {
		return startOfDay;
	}


	/**
	 * Gets the EndOfDay attribute of the Contact object
	 *
	 * @return The EndOfDay value
	 * @since 1.1
	 */
	public String getEndOfDay() {
		return endOfDay;
	}


	/**
	 * Gets the Enabled attribute of the Contact object
	 *
	 * @return The Enabled value
	 * @since 1.2
	 */
	public boolean getEnabled() {
		return enabled;
	}


	/**
	 * Gets the PhoneNumberList attribute of the Contact object
	 *
	 * @return The PhoneNumberList value
	 * @since 1.13
	 */
	public ContactPhoneNumberList getPhoneNumberList() {
		return phoneNumberList;
	}


	/**
	 * Gets the textMessageAddressList attribute of the Contact object
	 *
	 * @return The textMessageAddressList value
	 */
	public ContactTextMessageAddressList getTextMessageAddressList() {
		return textMessageAddressList;
	}


	/**
	 * Gets the textMessageAddressTypeId attribute of the Contact object
	 *
	 * @param thisPosition Description of the Parameter
	 * @return The textMessageAddressTypeId value
	 */
	public int getTextMessageAddressTypeId(int thisPosition) {
		return textMessageAddressList.getTextMessageAddressTypeId(thisPosition);
	}


	/**
	 * Gets the instantMessageAddressTypeId attribute of the Contact object
	 *
	 * @param thisPosition Description of the Parameter
	 * @return The instantMessageAddressTypeId value
	 */
	public int getInstantMessageAddressTypeId(int thisPosition) {
		return instantMessageAddressList.getInstantMessageAddressTypeId(
				thisPosition);
	}


	/**
	 * Gets the instantMessageAddressServiceId attribute of the Contact object
	 *
	 * @param thisPosition Description of the Parameter
	 * @return The instantMessageAddressServiceId value
	 */
	public int getInstantMessageAddressServiceId(int thisPosition) {
		return instantMessageAddressList.getInstantMessageAddressServiceId(
				thisPosition);
	}


	/**
	 * Gets the EnteredBy attribute of the Contact object
	 *
	 * @return The EnteredBy value
	 * @since 1.1
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 * Gets the ModifiedBy attribute of the Contact object
	 *
	 * @return The ModifiedBy value
	 * @since 1.1
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}


	/**
	 * Description of the Method
	 *
	 * @return Description of the Returned Value
	 */
	public boolean excludedFromCampaign() {
		return getExcludedFromCampaign();
	}


	/**
	 * Gets the excludedFromCampaign attribute of the Contact object
	 *
	 * @return The excludedFromCampaign value
	 */
	public boolean getExcludedFromCampaign() {
		return excludedFromCampaign;
	}


	/**
	 * Gets the isLead attribute of the Contact object
	 *
	 * @return The isLead value
	 */
	public boolean getIsLead() {
		return isLead;
	}


	/**
	 * Sets the isLead attribute of the Contact object
	 *
	 * @param tmp The new isLead value
	 */
	public void setIsLead(boolean tmp) {
		this.isLead = tmp;
	}


	/**
	 * Sets the isLead attribute of the Contact object
	 *
	 * @param tmp The new isLead value
	 */
	public void setIsLead(String tmp) {
		this.isLead = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Gets the leadStatus attribute of the Contact object
	 *
	 * @return The leadStatus value
	 */
	public int getLeadStatus() {
		return leadStatus;
	}


	/**
	 * Sets the leadStatus attribute of the Contact object
	 *
	 * @param tmp The new leadStatus value
	 */
	public void setLeadStatus(int tmp) {
		this.leadStatus = tmp;
	}


	/**
	 * Sets the leadStatus attribute of the Contact object
	 *
	 * @param tmp The new leadStatus value
	 */
	public void setLeadStatus(String tmp) {
		this.leadStatus = Integer.parseInt(tmp);
	}


	/**
	 * Gets the leadStatusString attribute of the Contact object
	 *
	 * @return The leadStatusString value
	 */
	public String getLeadStatusString() {
		if (leadStatus == LEAD_ASSIGNED) {
			return "Assigned";
		} else if (leadStatus == LEAD_TRASHED) {
			return "Archived";
		} else if (leadStatus == LEAD_UNPROCESSED) {
			return "Unprocessed";
		}
		return "Error";
	}


	/**
	 * Gets the source attribute of the Contact object
	 *
	 * @return The source value
	 */
	public int getSource() {
		return source;
	}


	/**
	 * Sets the source attribute of the Contact object
	 *
	 * @param tmp The new source value
	 */
	public void setSource(int tmp) {
		this.source = tmp;
	}


	/**
	 * Sets the source attribute of the Contact object
	 *
	 * @param tmp The new source value
	 */
	public void setSource(String tmp) {
		this.source = Integer.parseInt(tmp);
	}


	/**
	 * Gets the stage attribute of the Contact object
	 *
	 * @return The stage value
	 */
	public int getStage() {
		return stage;
	}


	/**
	 * Sets the stage attribute of the Contact object
	 *
	 * @param tmp The new stage value
	 */
	public void setStage(int tmp) {
		this.stage = tmp;
	}


	/**
	 * Sets the stage attribute of the Contact object
	 *
	 * @param tmp The new stage value
	 */
	public void setStage(String tmp) {
		this.stage = Integer.parseInt(tmp);
	}


	/**
	 * Gets the rating attribute of the Contact object
	 *
	 * @return The rating value
	 */
	public int getRating() {
		return rating;
	}


	/**
	 * Sets the rating attribute of the Contact object
	 *
	 * @param tmp The new rating value
	 */
	public void setRating(int tmp) {
		this.rating = tmp;
	}


	/**
	 * Sets the rating attribute of the Contact object
	 *
	 * @param tmp The new rating value
	 */
	public void setRating(String tmp) {
		this.rating = Integer.parseInt(tmp);
	}


	/**
	 * Gets the comments attribute of the Contact object
	 *
	 * @return The comments value
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * Sets the comments attribute of the Contact object
	 *
	 * @param tmp The new comments value
	 */
	public void setComments(String tmp) {
		this.comments = tmp;
	}


	/**
	 * Gets the conversionDate attribute of the Contact object
	 *
	 * @return The conversionDate value
	 */
	public java.sql.Timestamp getConversionDate() {
		return conversionDate;
	}


	/**
	 * Sets the conversionDate attribute of the Contact object
	 *
	 * @param tmp The new conversionDate value
	 */
	public void setConversionDate(java.sql.Timestamp tmp) {
		this.conversionDate = tmp;
	}


	/**
	 * Sets the conversionDate attribute of the Contact object
	 *
	 * @param tmp The new conversionDate value
	 */
	public void setConversionDate(String tmp) {
		this.conversionDate = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 * Gets the assignedDate attribute of the Contact object
	 *
	 * @return The assignedDate value
	 */
	public java.sql.Timestamp getAssignedDate() {
		return assignedDate;
	}


	/**
	 * Sets the assignedDate attribute of the Contact object
	 *
	 * @param tmp The new assignedDate value
	 */
	public void setAssignedDate(java.sql.Timestamp tmp) {
		this.assignedDate = tmp;
	}


	/**
	 * Sets the assignedDate attribute of the Contact object
	 *
	 * @param tmp The new assignedDate value
	 */
	public void setAssignedDate(String tmp) {
		this.assignedDate = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 * Gets the leadTrashedDate attribute of the Contact object
	 *
	 * @return The leadTrashedDate value
	 */
	public java.sql.Timestamp getLeadTrashedDate() {
		return leadTrashedDate;
	}


	/**
	 * Sets the leadTrashedDate attribute of the Contact object
	 *
	 * @param tmp The new leadTrashedDate value
	 */
	public void setLeadTrashedDate(java.sql.Timestamp tmp) {
		this.leadTrashedDate = tmp;
	}


	/**
	 * Sets the leadTrashedDate attribute of the Contact object
	 *
	 * @param tmp The new leadTrashedDate value
	 */
	public void setLeadTrashedDate(String tmp) {
		this.leadTrashedDate = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 * Gets the siteName attribute of the Contact object
	 *
	 * @return The siteName value
	 */
	public String getSiteName() {
		return siteName;
	}


	/**
	 * Sets the siteName attribute of the Contact object
	 *
	 * @param tmp The new siteName value
	 */
	public void setSiteName(String tmp) {
		this.siteName = tmp;
	}


	/**
	 * Gets the checkRevertingBackToLead attribute of the Contact object
	 *
	 * @return The checkRevertingBackToLead value
	 */
	public boolean getCheckRevertingBackToLead() {
		return checkRevertingBackToLead;
	}


	/**
	 * Sets the checkRevertingBackToLead attribute of the Contact object
	 *
	 * @param tmp The new checkRevertingBackToLead value
	 */
	public void setCheckRevertingBackToLead(boolean tmp) {
		this.checkRevertingBackToLead = tmp;
	}


	/**
	 * Sets the checkRevertingBackToLead attribute of the Contact object
	 *
	 * @param tmp The new checkRevertingBackToLead value
	 */
	public void setCheckRevertingBackToLead(String tmp) {
		this.checkRevertingBackToLead = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Description of the Method
	 *
	 * @param db         Description of Parameter
	 * @param campaignId Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 */
	public boolean toggleExcluded(Connection db, int campaignId) throws SQLException {
		if (id == -1) {
			return false;
		}

		ExcludedRecipient thisRecipient = new ExcludedRecipient();
		thisRecipient.setCampaignId(campaignId);
		thisRecipient.setContactId(this.getId());
		if (this.excludedFromCampaign()) {
			thisRecipient.delete(db);
		} else {
			thisRecipient.insert(db);
		}

		this.excludedFromCampaign = !excludedFromCampaign;
		return true;
	}


	/**
	 * Returns whether or not this Contact has a User Account in the system
	 *
	 * @return Description of the Returned Value
	 * @since 1.10
	 */
	public boolean hasAccount() {
		return hasAccount;
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	public void buildPhoneNumberList(Connection db) throws SQLException {
		phoneNumberList.setContactId(this.getId());
		phoneNumberList.buildList(db);
	}


	/**
	 * Inserts this object into the database, and populates this Id. For
	 * maintenance, only the required fields are inserted, then an update is
	 * executed to finish the record.
	 *
	 * @param db Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	

	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	void processPhoneNumbers(Connection db) throws SQLException {
		Iterator iphone = phoneNumberList.iterator();
		while (iphone.hasNext()) {
			ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) iphone.next();
			thisPhoneNumber.process(
					db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	void processAddress(Connection db) throws SQLException {
		Iterator iaddress = addressList.iterator();
		while (iaddress.hasNext()) {
			ContactAddress thisAddress = (ContactAddress) iaddress.next();
			thisAddress.process(
					db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	void processEmailAddress(Connection db) throws SQLException {
		Iterator iemail = emailAddressList.iterator();
		while (iemail.hasNext()) {
			ContactEmailAddress thisEmailAddress = (ContactEmailAddress) iemail.next();
			thisEmailAddress.process(
					db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	void processTextMessageAddress(Connection db) throws SQLException {
		Iterator itextMessageAddress = textMessageAddressList.iterator();
		while (itextMessageAddress.hasNext()) {
			ContactTextMessageAddress thistextMessageAddress = (ContactTextMessageAddress) itextMessageAddress.next();
			thistextMessageAddress.process(
					db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	void processInstantMessageAddress(Connection db) throws SQLException {
		Iterator instantMessageAddress = instantMessageAddressList.iterator();
		while (instantMessageAddress.hasNext()) {
			ContactInstantMessageAddress thisInstantMessageAddress = (ContactInstantMessageAddress) instantMessageAddress.next();
			thisInstantMessageAddress.process(
					db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
		}
	}


	


	

	/**
	 * Populates this object from a shortened result set
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	protected void buildShortRecord(ResultSet rs) throws SQLException{
		// c.user_id, c.contact_id, c.namelast, c.namefirst, o.name, c.owner, c.status_id, c.entered
		this.setId(rs.getInt("contact_id"));
		userId = DatabaseUtils.getInt(rs, "user_id");      
		nameLast = rs.getString("namelast");
		nameFirst = rs.getString("namefirst");
		company = rs.getString("company");
		orgName = rs.getString("name");
		statusId = DatabaseUtils.getInt(rs, "status_id");
		owner = DatabaseUtils.getInt(rs, "owner");
		entered = rs.getTimestamp("entered");
		isLead = rs.getBoolean("lead");
		leadStatus = DatabaseUtils.getInt(rs, "lead_status");
		orgId = DatabaseUtils.getInt(rs, "org_id");
		siteId = DatabaseUtils.getInt(rs, "site_id");
	}

	/**
	 * Populates this object from a result set
	 *
	 * @param rs Description of Parameter
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		//contact table
		this.setId(rs.getInt("contact_id"));
		
	//	orgId = DatabaseUtils.getInt(rs, "org_id");
		
		//TODO: Determine why department was made to default to 0? department = DatabaseUtils.getInt(rs, "department", 0);
		//department = DatabaseUtils.getInt(rs, "department");
		
		try
		{
			visibilitaDelega = rs.getString("visibilita_delega");
		}
		catch(SQLException e)
		{
			
		}
		
		
		try
		{
			numRegistrazioneStabilimentoAssociato = rs.getString("num_registrazione_stab");
		}
		catch(SQLException e)
		{
			
		}
		
		try
		{
		state = rs.getString("state");
		}
		catch(SQLException e)
		{
			
		}
		nameLast = rs.getString("namelast");
		nameFirst = rs.getString("namefirst");
		nameMiddle = rs.getString("namemiddle");
		nameSuffix = rs.getString("namesuffix");
		assistant = DatabaseUtils.getInt(rs, "assistant");
		notes = rs.getString("notes");
		site = rs.getString("site");
		locale = rs.getInt("locale");
		employeeId = rs.getString("employee_id");
		employmentType = rs.getInt("employmenttype");
		startOfDay = rs.getString("startofday");
		endOfDay = rs.getString("endofday");
		codiceFiscale = rs.getString("codice_fiscale");
		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		enabled = rs.getBoolean("enabled");
		owner = DatabaseUtils.getInt(rs, "owner");
		custom1 = rs.getInt("custom1");
		url = rs.getString("url");
		orgName = rs.getString("org_name");
		city = rs.getString("luogo");

		
		//		stage = DatabaseUtils.getInt(rs, "stage");
//		rating = DatabaseUtils.getInt(rs, "rating");
//		comments = rs.getString("comments");
//		conversionDate = rs.getTimestamp("conversion_date");
//		additionalNames = rs.getString("additional_names");
//		nickname = rs.getString("nickname");
//		birthDate = rs.getTimestamp("birthdate");
//		role = rs.getString("role");
//		trashedDate = rs.getTimestamp("trashed_date");
//		secretWord = rs.getString("secret_word");
//		accountNumber = rs.getString("account_number");
//		revenue = rs.getDouble("revenue");
//		industryTempCode = DatabaseUtils.getInt(rs, "industry_temp_code");
//		potential = rs.getDouble("potential");
//		noEmail = rs.getBoolean("no_email");
//		noMail = rs.getBoolean("no_mail");
//		noPhone = rs.getBoolean("no_phone");
//		noTextMessage = rs.getBoolean("no_textmessage");
//		noInstantMessage = rs.getBoolean("no_im");
//		noFax = rs.getBoolean("no_fax");
		siteId = DatabaseUtils.getInt(rs, "site_id");
//		assignedDate = rs.getTimestamp("assigned_date");
//		leadTrashedDate = rs.getTimestamp("lead_trashed_date");
//		employees = DatabaseUtils.getInt(rs, "employees");
//		dunsType = rs.getString("duns_type");
//		dunsNumber = rs.getString("duns_number");
//		businessNameTwo = rs.getString("business_name_two");
//		sicCode = DatabaseUtils.getInt(rs, "sic_code");
//		yearStarted = rs.getInt("year_started");
//		sicDescription = rs.getString("sic_description");
//
//		//organization table
//		orgEnabled = rs.getBoolean("orgenabled");
//		orgTrashedDate = rs.getTimestamp("orgtrasheddate");
//
//		//lookup_department table
//		departmentName = rs.getString("departmentname");

		//contact_address table
//		city = rs.getString("city");
//		postalcode = rs.getString("postalcode");
		//lookup_site_id table
//		siteName = rs.getString("site_id_name");
//
//		//lead descriptions
//		industryName = rs.getString("industry_name");
//		sourceName = rs.getString("source_name");
//		stageName = rs.getString("stage_name");
//		ratingName = rs.getString("rating_name");
//
//		// Get the import name
//		importName = rs.getString("import_name");
	}



	/**
	 * Combines the first and last name of a contact, depending on the length of
	 * the strings
	 *
	 * @param nameLast  Description of the Parameter
	 * @param nameFirst Description of the Parameter
	 * @return The nameLastFirst value
	 */
	public static String getNameLastFirst(String nameLast, String nameFirst) {
		StringBuffer out = new StringBuffer();
		if (nameLast != null && nameLast.trim().length() > 0) {
			out.append(nameLast);
		}
		if (nameFirst != null && nameFirst.trim().length() > 0) {
			if (out.length() > 0) {
				out.append(", ");
			}
			out.append(nameFirst);
		}
		if (out.toString().length() == 0) {
			return null;
		}
		return out.toString().trim();
	}

	/**
	 * Combines the first and last name of a contact and the title, depending on the length of
	 * the strings
	 *
	 * @param nameLast  Description of the Parameter
	 * @param nameFirst Description of the Parameter
	 * @title nameFirst Description of the Parameter
	 * @return The nameLastFirst value
	 */
	public static String getNameLastFirstAndTitle(String nameLast, String nameFirst, String title) {
		if (title != null && !"".equals(title)) {
			return Contact.getNameLastFirst(nameLast, nameFirst) + " - " + title;
		}

		return Contact.getNameLastFirst(nameLast, nameFirst);
	}
	
	
	
	public void updateCodiceFiscale(Connection db){
		
		try {
			PreparedStatement pst= db.prepareStatement("update contact set codice_fiscale=? where user_id =?");
			pst.setString(1, codiceFiscale);
			pst.setInt(2, userId);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the nameFirstLast attribute of the Contact class
	 *
	 * @param nameFirst Description of the Parameter
	 * @param nameLast  Description of the Parameter
	 * @return The nameFirstLast value
	 */
	public static String getNameFirstLast(String nameFirst, String nameLast) {
		StringBuffer out = new StringBuffer();
		if (nameFirst != null && nameFirst.trim().length() > 0) {
			out.append(nameFirst);
		}
		if (nameLast != null && nameLast.trim().length() > 0) {
			if (out.length() > 0) {
				out.append(" ");
			}
			out.append(nameLast);
		}
		if (out.toString().length() == 0) {
			return null;
		}
		return out.toString().trim();
	}


	/**
	 * Gets the nameFirstLastOrCompany attribute of the Contact class
	 *
	 * @param tmpNameFirst Description of the Parameter
	 * @param tmpNameLast  Description of the Parameter
	 * @param tmpCompany   Description of the Parameter
	 * @return The nameFirstLastOrCompany value
	 */
	public static String getNameFirstLastOrCompany(String tmpNameFirst, String tmpNameLast, String tmpCompany) {
		String firstLastOrCompany = Contact.getNameFirstLast(tmpNameFirst, tmpNameLast);

		if ((firstLastOrCompany == null) || "".equals(firstLastOrCompany)) {
			firstLastOrCompany = tmpCompany;
		}

		return firstLastOrCompany;
	}


	/**
	 * Description of the Method
	 *
	 * @param db      Description of the Parameter
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException Description of the Exception
	 */
	


	


	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 */
	public void invalidateUserData(ActionContext context) {
		invalidateUserData(context, owner);
	}


	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @param userId  Description of the Parameter
	 */
	public void invalidateUserData(ActionContext context, int userId) {
		if (userId != -1) {
			ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
					"ConnectionElement");
			SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
			"SystemStatus")).get(ce.getUrl());
			systemStatus.getHierarchyList().getUser(userId).setIsValidLead(
					false, true);
		}
	}


	/**
	 * Gets the htmlString attribute of the Contact object
	 *
	 * @param dependencies Description of the Parameter
	 * @param systemStatus Description of the Parameter
	 * @return The htmlString value
	 */
	public String getHtmlString(DependencyList dependencies, SystemStatus systemStatus) {
		boolean canMove = true;
		Iterator i = dependencies.iterator();
		StringBuffer html = new StringBuffer();
		html.append("<br />");
		int count = 0;
		while (i.hasNext()) {
			Dependency thisDependency = (Dependency) i.next();
			if (thisDependency.getCount() > 0) {
				++count;
				html.append("&nbsp;&nbsp;");
				if (thisDependency.getCanDelete()) {
					html.append("- ");
				} else {
					if ((thisDependency.getName().equals("opportunities") && this.getOrgId() == -1) || thisDependency.getName().equals(
					"activities") || thisDependency.getName().equals("folders")) {
						html.append("- ");
					} else {
						html.append("* ");
						canMove = false;
					}
				}
				if (systemStatus != null) {
					html.append(
							systemStatus.getLabel("dependency." + thisDependency.getName()) + " (" + thisDependency.getCount() + ")");
				}
				html.append("<br />");
			}
		}
		if (count == 0) {
			if (systemStatus != null) {
				html.append(
						"&nbsp;&nbsp;" + systemStatus.getLabel(
						"dependency.noDependencyForAction") + "<br />");
			}
		}
		if (!canMove) {
			if (systemStatus != null) {
				html.append(
						"<br />(*) " + systemStatus.getLabel(
						"dependency.preventingContactMove"));
				html.append(
						"<br />" + systemStatus.getLabel("dependency.note") + "<br />");
			}
		}
		return html.toString();
	}


	/**
	 * Description of the Method
	 *
	 * @param dependencies Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean canMoveContact(DependencyList dependencies) {
		Iterator thisList = dependencies.iterator();
		boolean canMove = true;
		boolean othersPresent = false;
		while (thisList.hasNext()) {
			Dependency thisDependency = (Dependency) thisList.next();
			if (!thisDependency.getCanDelete() && thisDependency.getCount() > 0) {
				if (thisDependency.getName().equals("opportunities") || thisDependency.getName().equals(
				"activities") || thisDependency.getName().equals("folders")) {
					canMove = false;
				} else {
					othersPresent = true;
				}
			}
		}
		return (!othersPresent && canMove);
	}


	/**
	 * Description of the Method
	 *
	 * @return Description of the Return Value
	 */
	public String toString() {
		return this.getNameFull();
	}


	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	public void updatePrimaryContactInformation(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement(
				"UPDATE contact SET primary_contact = ?, " +
				"modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
		"WHERE contact_id = ? ");
		pst.setBoolean(1, this.getPrimaryContact());
		pst.setInt(2, this.getId());
		pst.executeUpdate();
		pst.close();
	}


}

