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

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.useridentifier;
import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
//import com.itextpdf.text.log.SysoLogger;
import com.zeroio.webdav.WebdavServlet;

import org.apache.log4j.Logger;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.GraphSummaryList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.PasswordHash;
import org.aspcfs.utils.StringUtils;
import org.json.JSONObject;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.util.*;

/**
 * Represents a user access record <p>
 * <p/>
 * <p/>
 * <p/>
 * Password control is implemented with three fields: password is the database
 * (stored) field, containing the encrypted string; password1 is the field that
 * the user sees; password2 is the field used for verification.<p>
 * <p/>
 * <p/>
 * <p/>
 * For an existing record, the password field is only included in the SQL
 * command if the password1 field is left blank. Otherwise, it is compared to
 * the password2 field (also filled in by the user), and if valid, is encrypted
 * and included in the update.<p>
 * <p/>
 * <p/>
 * <p/>
 * For a new record, the password 1 and 2 fields are required. <p/>
 * <p/>
 * Password control is implemented with three fields: password is the database
 * (stored) field, containing the encrypted string; password1 is the field that
 * the user sees; password2 is the field used for verification.<p>
 * <p/>
 * <p/>
 * <p/>
 * For an existing record, the password field is only included in the SQL
 * command if the password1 field is left blank. Otherwise, it is compared to
 * the password2 field (also filled in by the user), and if valid, is encrypted
 * and included in the update.<p>
 * <p/>
 * <p/>
 * <p/>
 * For a new record, the password 1 and 2 fields are required.
 *
 * @author matt rajkowski
 * @version $Id: User.java 24287 2007-12-09 11:28:24Z srinivasar@cybage.com $
 * @created September 17, 2001
 */

public class User extends GenericBean {

	private static final long serialVersionUID = -8407265772708150808L;

	private static final Logger log = Logger.getLogger(org.aspcfs.modules.admin.base.User.class);

	protected String errmsg = "";

	protected int id = -1;

	protected String username = null;

	protected String encryptedPassword = null;

	protected String password = null;

	protected String password1 = null;

	protected String password2 = null;

	protected String webdavPassword = null;

	protected int contactId = -1;

	protected int roleId = -1;

	protected String role = null;

	private int roleType = -1;

	protected int managerId = -1;


	protected String ip = null;

	protected String timeZone = null;

	private String currency = null;

	private String language = null;

	private Locale locale = null;

	protected int startOfDay = -1;

	protected int endOfDay = -1;

	protected int enteredBy = -1;

	protected int modifiedBy = -1;

	protected boolean enabled = true;
	
	protected boolean enabledAsQualifica = true;

	protected boolean hasWebdavAccess = false;

	protected boolean hasHttpApiAccess = false;

	private int siteId = -1;

	private String siteIdName = null;

	private int gruppo_ruolo ;
	private String  descrizione_gruppo_ruolo ;

	protected java.sql.Timestamp entered = null;

	protected java.sql.Timestamp modified = null;

	protected java.sql.Timestamp lastLogin = null;

	protected java.sql.Timestamp expires = null;

	protected String previousUsername = null;

	private int assistant = -1;

	private int alias = -1;

	protected boolean hidden = false;

	// Related objects
	protected Contact contact = new Contact();

	protected UserList childUsers = null;

	// Lazy loading properties
	protected boolean buildContact = false;

	protected boolean buildContactDetails = false;

	protected boolean buildHierarchy = false;

	protected boolean hideHiddenChildren = false;

	// check to see if manager user is enabled
	private boolean managerUserEnabled = true;

	// Cached data
	// TODO: Remove cache data from this object
	protected boolean opportunityLock = false;

	protected boolean revenueLock = false;

	protected double YTD = 0;

	protected double pipelineValue = 0;

	protected boolean pipelineValueIsValid = false;


	// Leads related data
	protected boolean leadsLock = false;

	protected double leadsNumber = 0;

	protected boolean leadsNumberIsValid = false;

	protected GraphSummaryList lccr = new GraphSummaryList();
	//XML API helper properties
	protected boolean addContact = true;
	private double access_position_lat ;
	private double access_position_lon ;
	private String access_position_err ;

	private boolean inDpat = false ;
	private boolean inAccess;
	private boolean inNucleoIspettivo ;
	protected String strutturaAppartenenza ;
	protected int idStrutturaAppartenenza ;
	
	private Suap suap = new Suap() ;

	private int caricolo_lavoro_annuale;
	//------
	protected java.sql.Timestamp trashed_date = null;

	private Date dataScadenza ;
	private int idStrutturaAslComplessa;

	private int id_tipo_gruppo_ruolo;

	private String descrizione_tipo_gruppo_ruolo;
	
	
  

	public int getIdStrutturaAslComplessa() {
		return idStrutturaAslComplessa;
	}
	public void setIdStrutturaAslComplessa(int idStrutturaAslComplessa) {
		this.idStrutturaAslComplessa = idStrutturaAslComplessa;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public int getIdStrutturaAppartenenza() {
		return idStrutturaAppartenenza;
	}
	public void setIdStrutturaAppartenenza(int idStrutturaAppartenenza) {
		this.idStrutturaAppartenenza = idStrutturaAppartenenza;
	}
	public String getStrutturaAppartenenza() {
		return strutturaAppartenenza;
	}
	public void setStrutturaAppartenenza(String strutturaAppartenenza) {
		this.strutturaAppartenenza = strutturaAppartenenza;
	}
	public int getCaricolo_lavoro_annuale() {
		return caricolo_lavoro_annuale;
	}
	public void setCaricolo_lavoro_annuale(int caricolo_lavoro_annuale) {
		this.caricolo_lavoro_annuale = caricolo_lavoro_annuale;
	}
	public int getGruppo_ruolo() {
		return gruppo_ruolo;
	}
	public void setGruppo_ruolo(int gruppo_ruolo) {
		this.gruppo_ruolo = gruppo_ruolo;
	}
	public String getDescrizione_gruppo_ruolo() {
		return descrizione_gruppo_ruolo;
	}
	public void setDescrizione_gruppo_ruolo(String descrizione_gruppo_ruolo) {
		this.descrizione_gruppo_ruolo = descrizione_gruppo_ruolo;
	}
	public Suap getSuap() {
		return suap;
	}
	public void setSuap(Suap suap) {
		this.suap = suap;
	}
	public java.sql.Timestamp getTrashed_date() {
		return trashed_date;
	}
	public void setTrashed_date(java.sql.Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}
	//------


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
	 * Constructor for the User object
	 *
	 * @since 1.1
	 */
	public User() {
	}

	/**
	 * Constructor for the User object
	 *
	 * @param rs Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	public User(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	/**
	 * Constructor for the User object
	 *
	 * @param db     Description of Parameter
	 * @param userId Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	

	/**
	 * Constructor for the User object
	 *
	 * @param db          Description of Parameter
	 * @param userId      Description of Parameter
	 * @param doHierarchy Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @throws SQLException Description of Exception
	 * @since 1.26
	 */
	public User(Connection db, String userId, boolean doHierarchy)
			throws SQLException {
		this.buildHierarchy = doHierarchy;
	}

	/**
	 * Constructor for the User object
	 *
	 * @param db     Description of Parameter
	 * @param userId Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @throws SQLException Description of Exception
	 * @since 1.20
	 */
	public User(Connection db, int userId) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from lista_utenti_centralizzata_storico where user_id = "+userId);
		ResultSet rs =  pst.executeQuery();
		if (rs.next())
		{
			this.buildRecord(rs);
			contact = new Contact(db,this.contactId);
			
		}
		
		
		

	}
	
        public User(Connection db, String cf) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from lista_utenti_centralizzata_storico where visibilita_delega = '"+cf+"' limit 1");
		ResultSet rs =  pst.executeQuery();
		if (rs.next())
		{
			this.buildRecord(rs);
			contact = new Contact(db,this.contactId);
			
		}
		
		
		

	}


	/**
	 * Gets the addContact attribute of the User object
	 *
	 * @return The addContact value
	 */
	public boolean getAddContact() {
		return addContact;
	}


	/**
	 * Sets the addContact attribute of the User object
	 *
	 * @param tmp The new addContact value
	 */
	public void setAddContact(boolean tmp) {
		this.addContact = tmp;
	}


	/**
	 * Sets the addContact attribute of the User object
	 *
	 * @param tmp The new addContact value
	 */
	public void setAddContact(String tmp) {
		this.addContact = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 * Sets the webdavPassword attribute of the User object
	 *
	 * @param tmp The new webdavPassword value
	 */
	public void setWebdavPassword(String tmp) {
		this.webdavPassword = tmp;
	}

	/**
	 * Gets the webdavPassword attribute of the User object
	 *
	 * @return The webdavPassword value
	 */
	public String getWebdavPassword() {
		return webdavPassword;
	}

	/**
	 * Gets the revenue attribute of the User object
	 *
	 * @return The revenue value
	 */

	/**
	 * Gets the hasWebdavAccess attribute of the User object
	 *
	 * @return The hasWebdavAccess value
	 */
	public boolean getHasWebdavAccess() {
		return hasWebdavAccess;
	}

	/**
	 * Sets the hasWebdavAccess attribute of the User object
	 *
	 * @param tmp The new hasWebdavAccess value
	 */
	public void setHasWebdavAccess(boolean tmp) {
		this.hasWebdavAccess = tmp;
	}

	/**
	 * Sets the hasWebdavAccess attribute of the User object
	 *
	 * @param tmp The new hasWebdavAccess value
	 */
	public void setHasWebdavAccess(String tmp) {
		this.hasWebdavAccess = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Gets the hasHttpApiAccess attribute of the User object
	 *
	 * @return The hasHttpApiAccess value
	 */
	public boolean getHasHttpApiAccess() {
		return hasHttpApiAccess;
	}

	/**
	 * Sets the hasHttpApiAccess attribute of the User object
	 *
	 * @param tmp The new hasHttpApiAccess value
	 */
	public void setHasHttpApiAccess(boolean tmp) {
		this.hasHttpApiAccess = tmp;
	}

	/**
	 * Sets the hasHttpApiAccess attribute of the User object
	 *
	 * @param tmp The new hasHttpApiAccess value
	 */
	public void setHasHttpApiAccess(String tmp) {
		this.hasHttpApiAccess = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Sets the revenue attribute of the User object
	 *
	 * @param revenue The new revenue value
	 */

	/**
	 * Gets the yTD attribute of the User object
	 *
	 * @return The yTD value
	 */
	public double getYTD() {
		return YTD;
	}

	/**
	 * Sets the yTD attribute of the User object
	 *
	 * @param YTD The new yTD value
	 */
	public void setYTD(double YTD) {
		this.YTD = YTD;
	}

	/**
	 * Gets the yTDValue attribute of the User object
	 *
	 * @return The yTDValue value
	 */
	public String getYTDValue() {
		double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
		String toReturn = String.valueOf(value_2dp);
		if (toReturn.endsWith(".0")) {
			toReturn = toReturn.substring(0, toReturn.length() - 2);
		}

		if (Integer.parseInt(toReturn) == 0) {
			toReturn = "";
		}

		return toReturn;
	}



	public boolean isInDpat() {
		return inDpat;
	}
	public void setInDpat(boolean inDpat) {
		this.inDpat = inDpat;
	}
	public boolean isInAccess() {
		return inAccess;
	}
	public void setInAccess(boolean inAccess) {
		this.inAccess = inAccess;
	}
	public boolean isInNucleoIspettivo() {
		return inNucleoIspettivo;
	}
	public void setInNucleoIspettivo(boolean inNucleoIspettivo) {
		this.inNucleoIspettivo = inNucleoIspettivo;
	}
	/**
	 * Gets the hidden attribute of the User object
	 *
	 * @return The hidden value
	 */
	public boolean getHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden attribute of the User object
	 *
	 * @param tmp The new hidden value
	 */
	public void setHidden(boolean tmp) {
		this.hidden = tmp;
	}

	/**
	 * Sets the hidden attribute of the User object
	 *
	 * @param tmp The new hidden value
	 */
	public void setHidden(String tmp) {
		this.hidden = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Gets the hideHiddenChildren attribute of the User object
	 *
	 * @return The hideHiddenChildren value
	 */
	public boolean getHideHiddenChildren() {
		return hideHiddenChildren;
	}

	/**
	 * Sets the hideHiddenChildren attribute of the User object
	 *
	 * @param tmp The new hideHiddenChildren value
	 */
	public void setHideHiddenChildren(boolean tmp) {
		this.hideHiddenChildren = tmp;
	}

	/**
	 * Sets the hideHiddenChildren attribute of the User object
	 *
	 * @param tmp The new hideHiddenChildren value
	 */
	public void setHideHiddenChildren(String tmp) {
		this.hideHiddenChildren = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Gets the leadsLock attribute of the User object
	 *
	 * @return The leadsLock value
	 */
	public boolean getLeadsLock() {
		return leadsLock;
	}

	/**
	 * Sets the leadsLock attribute of the User object
	 *
	 * @param tmp The new leadsLock value
	 */
	public void setLeadsLock(boolean tmp) {
		this.leadsLock = tmp;
	}

	/**
	 * Sets the leadsLock attribute of the User object
	 *
	 * @param tmp The new leadsLock value
	 */
	public void setLeadsLock(String tmp) {
		this.leadsLock = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Gets the leadsNumber attribute of the User object
	 *
	 * @return The leadsNumber value
	 */
	public double getLeadsNumber() {
		return leadsNumber;
	}

	/**
	 * Sets the leadsNumber attribute of the User object
	 *
	 * @param tmp The new leadsNumber value
	 */
	public void setLeadsNumber(double tmp) {
		this.leadsNumber = tmp;
	}

	/**
	 * Sets the leadsNumber attribute of the User object
	 *
	 * @param tmp The new leadsNumber value
	 */
	public void setLeadsNumber(String tmp) {
		this.leadsNumber = Double.parseDouble(tmp);
	}

	/**
	 * Gets the leadsNumberIsValid attribute of the User object
	 *
	 * @return The leadsNumberIsValid value
	 */
	public boolean getLeadsNumberIsValid() {
		return leadsNumberIsValid;
	}

	/**
	 * Sets the leadsNumberIsValid attribute of the User object
	 *
	 * @param tmp The new leadsNumberIsValid value
	 */
	public void setLeadsNumberIsValid(boolean tmp) {
		this.leadsNumberIsValid = tmp;
	}

	/**
	 * Sets the leadsNumberIsValid attribute of the User object
	 *
	 * @param tmp The new leadsNumberIsValid value
	 */
	public void setLeadsNumberIsValid(String tmp) {
		this.leadsNumberIsValid = DatabaseUtils.parseBoolean(tmp);
	}

	/**
	 * Gets the lccr attribute of the User object
	 *
	 * @return The lccr value
	 */
	public GraphSummaryList getLccr() {
		return lccr;
	}

	/**
	 * Sets the lccr attribute of the User object
	 *
	 * @param tmp The new lccr value
	 */
	public void setLccr(GraphSummaryList tmp) {
		this.lccr = tmp;
	}

	/**
	 * Sets the graphValuesLeads attribute of the User object
	 *
	 * @param key   The new graphValuesLeads value
	 * @param value The new graphValuesLeads value
	 */
	public void setGraphValuesLeads(String key, Double value) {
		this.getLccr().setValue(key, value);
	}

	/**
	 * Gets the isValidLeads attribute of the User object
	 *
	 * @return The isValidLeads value
	 */
	public boolean getIsValidLead() {
		if (this.lccr.getIsValid() == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Description of the Method
	 */
	public void doLeadsLock() {
		while (leadsLock) {
		}
		synchronized (this) {
			while (leadsLock) {
			}
			this.leadsLock = true;
		}
	}

	/**
	 * Description of the Method
	 */
	public void doLeadsUnlock() {
		this.leadsLock = false;
	}

	/**
	 * Sets the isValidLead attribute of the User object
	 *
	 * @param isValid  The new isValidLead value
	 * @param dataAlso The new isValidLead value
	 */
	public void setIsValidLead(boolean isValid, boolean dataAlso) {
		if (dataAlso) {
			this.lccr.setIsValid(isValid);
		}

		if (!isValid) {
			this.lccr.setLastFileName(null);
		}

		
	}

	/**
	 * Constructor for the User object
	 *
	 * @param db          Description of Parameter
	 * @param userId      Description of Parameter
	 * @param doHierarchy Description of Parameter
	 * @throws SQLException Description of the Exception
	 * @throws SQLException Description of Exception
	 * @since 1.28
	 */
	public User(Connection db, int userId, boolean doHierarchy)
			throws SQLException {
		this.buildHierarchy = doHierarchy;

	}

	/**
	 * Sets the expires attribute of the User object
	 *
	 * @param expires The new expires value
	 */
	public void setExpires(java.sql.Timestamp expires) {
		this.expires = expires;
		checkHidden();
	}

	/**
	 * Sets the expires attribute of the User object
	 *
	 * @param tmp The new expires value
	 */
	public void setExpires(String tmp) {
		this.expires = DatabaseUtils.parseDateToTimestamp(tmp);
		checkHidden();
	}

	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException Description of the Exception
	 */
	public String generateRandomPassword(Connection db) throws SQLException {
		String newPassword = String.valueOf(StringUtils.rand(100000, 999999));
		this.setPassword1(newPassword);
		this.setPassword2(newPassword);
		return newPassword;
	}

	/**
	 * Sets the Assistant attribute of the User object
	 *
	 * @param tmp The new Assistant value
	 */
	public void setAssistant(int tmp) {
		this.assistant = tmp;
	}

	/**
	 * Gets the childUsers attribute of the User object
	 *
	 * @return The childUsers value
	 */
	public UserList getChildUsers() {
		return childUsers;
	}

	/**
	 * Sets the assistant attribute of the User object
	 *
	 * @param tmp The new assistant value
	 */
	public void setAssistant(String tmp) {
		this.assistant = Integer.parseInt(tmp);
	}

	/**
	 * Sets the Alias attribute of the User object
	 *
	 * @param tmp The new Alias value
	 */
	public void setAlias(int tmp) {
		alias = tmp;
	}

	/**
	 * Sets the alias attribute of the User object
	 *
	 * @param tmp The new alias value
	 */
	public void setAlias(String tmp) {
		alias = Integer.parseInt(tmp);
	}

	/**
	 * Sets the Gmr attribute of the User object
	 *
	 * @param tmp The new Gmr value
	 * @since 1.18
	 */


	/**
	 * Sets the managerUserEnabled attribute of the User object
	 *
	 * @param managerUserEnabled The new managerUserEnabled value
	 */
	public void setManagerUserEnabled(boolean managerUserEnabled) {
		this.managerUserEnabled = managerUserEnabled;
	}

	/**
	 * Gets the managerUserEnabled attribute of the User object
	 *
	 * @return The managerUserEnabled value
	 */
	public boolean getManagerUserEnabled() {
		return managerUserEnabled;
	}

	/**
	 * Gets the revenueLock attribute of the User object
	 *
	 * @return The revenueLock value
	 */
	public boolean getRevenueLock() {
		return revenueLock;
	}

	/**
	 * Sets the revenueLock attribute of the User object
	 *
	 * @param revenueLock The new revenueLock value
	 */
	public void setRevenueLock(boolean revenueLock) {
		this.revenueLock = revenueLock;
	}

	/**
	 * Sets the Ramr attribute of the User object
	 *
	 * @param tmp The new Ramr value
	 * @since 1.18
	 */


	/**
	 * Gets the pipelineValueIsValid attribute of the User object
	 *
	 * @return The pipelineValueIsValid value
	 */
	public boolean getPipelineValueIsValid() {
		return pipelineValueIsValid;
	}

	/**
	 * Sets the pipelineValueIsValid attribute of the User object
	 *
	 * @param pipelineValueIsValid The new pipelineValueIsValid value
	 */
	public void setPipelineValueIsValid(boolean pipelineValueIsValid) {
		this.pipelineValueIsValid = pipelineValueIsValid;
	}

	/**
	 * Sets the IsValid attribute of the User object
	 *
	 * @param isValid  The new IsValid value
	 * @param dataAlso The new IsValid value
	 */
	public void setIsValid(boolean isValid, boolean dataAlso) {
		if (dataAlso) {

		}

		if (!isValid) {

		}

	
	}

	/**
	 * Sets the revenueIsValid attribute of the User object
	 *
	 * @param isValid  The new revenueIsValid value
	 * @param dataAlso The new revenueIsValid value
	 */
	public void setRevenueIsValid(boolean isValid, boolean dataAlso) {

		
	}

	/**
	 * Sets the GraphValues attribute of the User object
	 *
	 * @param key The new GraphValues value
	 * @param v1  The new GraphValues value
	 * @param v2  The new GraphValues value
	 * @param v3  The new GraphValues value
	 * @param v4  The new GraphValues value
	 */
	public void setGraphValues(String key, Double v1, Double v2, Double v3,
			Double v4) {

	}

	/**
	 * Sets the revenueGraphValues attribute of the User object
	 *
	 * @param key The new revenueGraphValues value
	 * @param v1  The new revenueGraphValues value
	 */
	public void setRevenueGraphValues(String key, Double v1) {
	}

	/**
	 * Sets the Cgmr attribute of the User object
	 *
	 * @param tmp The new Cgmr value
	 * @since 1.18
	 */


	/**
	 * Sets the Cramr attribute of the User object
	 *
	 * @param tmp The new Cramr value
	 * @since 1.18
	 */
	public void setCramr(GraphSummaryList tmp) {

	}

	/**
	 * Sets the Id attribute of the User object
	 *
	 * @param tmp The new Id value
	 * @since 1.1
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}

	/**
	 * Sets the Id attribute of the User object
	 *
	 * @param tmp The new Id value
	 * @since 1.1
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
	}

	/**
	 * Sets the Username attribute of the User object
	 *
	 * @param tmp The new Username value
	 * @since 1.1
	 */
	public void setUsername(String tmp) {
		this.username = tmp;
	}

	/**
	 * Sets the PreviousUsername attribute of the User object
	 *
	 * @param tmp The new PreviousUsername value
	 * @since 1.28
	 */
	public void setPreviousUsername(String tmp) {
		this.previousUsername = tmp;
	}

	/**
	 * Sets the encryptedPassword attribute of the User object
	 *
	 * @param tmp The new encryptedPassword value
	 */
	public void setEncryptedPassword(String tmp) {
		this.encryptedPassword = tmp;
	}

	/**
	 * Sets the Password attribute of the User object
	 *
	 * @param tmp The new Password value
	 * @since 1.1
	 */
	public void setPassword(String tmp) {
		this.password = tmp;
	}

	/**
	 * Sets the Password1 attribute of the User object
	 *
	 * @param tmp The new Password1 value
	 * @since 1.1
	 */
	public void setPassword1(String tmp) {
		this.password1 = tmp;
	}

	/**
	 * Sets the Password2 attribute of the User object
	 *
	 * @param tmp The new Password2 value
	 * @since 1.1
	 */
	public void setPassword2(String tmp) {
		this.password2 = tmp;
	}

	/**
	 * Sets the entered attribute of the User object
	 *
	 * @param tmp The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}

	/**
	 * Sets the modified attribute of the User object
	 *
	 * @param tmp The new modified value
	 */
	public void setModified(java.sql.Timestamp tmp) {
		this.modified = tmp;
	}

	/**
	 * Sets the entered attribute of the User object
	 *
	 * @param tmp The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DateUtils.parseTimestampString(tmp);
	}

	/**
	 * Sets the modified attribute of the User object
	 *
	 * @param tmp The new modified value
	 */
	public void setModified(String tmp) {
		this.modified = DateUtils.parseTimestampString(tmp);
	}

	/**
	 * Sets the ContactId attribute of the User object
	 *
	 * @param tmp The new ContactId value
	 * @since 1.1
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
		this.contact.setId(tmp);
	}

	/**
	 * Sets the ContactId attribute of the User object
	 *
	 * @param tmp The new ContactId value
	 * @since 1.1
	 */
	public void setContactId(String tmp) {
		if (tmp != null) {
			setContactId(Integer.parseInt(tmp));
		}
	}

	/**
	 * Sets the RoleId attribute of the User object
	 *
	 * @param tmp The new RoleId value
	 * @since 1.1
	 */
	public void setRoleId(int tmp) {
		this.roleId = tmp;
	}

	/**
	 * Description of the Method
	 *
	 * @param db         Description of the Parameter
	 * @param newManager Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException Description of the Exception
	 */
	public boolean reassign(Connection db, int newManager) throws SQLException {
		int result = -1;
		this.setManagerId(newManager);
		result = this.update(db);

		if (result == -1) {
			return false;
		}

		return true;
	}

	/**
	 * Sets the RoleId attribute of the User object
	 *
	 * @param tmp The new RoleId value
	 * @since 1.1
	 */
	public void setRoleId(String tmp) {
		if (tmp != null) {
			this.roleId = Integer.parseInt(tmp);
		}
	}

	/**
	 * Sets the siteId attribute of the User object
	 *
	 * @param tmp The new siteId value
	 */
	public void setSiteId(int tmp) {
		siteId = tmp;
	}

	/**
	 * Sets the siteId attribute of the User object
	 *
	 * @param tmp The new siteId value
	 */
	public void setSiteId(String tmp) {
		this.siteId = Integer.parseInt(tmp);
	}

	/**
	 * Sets the pipelineValue attribute of the User object
	 *
	 * @param pipelineValue The new pipelineValue value
	 */
	public void setPipelineValue(double pipelineValue) {
		this.pipelineValue = pipelineValue;
	}

	/**
	 * Gets the pipelineValue attribute of the User object
	 *
	 * @return The pipelineValue value
	 */
	public double getPipelineValue() {
		return pipelineValue;
	}

	/**
	 * Sets the Role attribute of the User object
	 *
	 * @param tmp The new Role value
	 * @since 1.11
	 */
	public void setRole(String tmp) {
		this.role = tmp;
	}

	/**
	 * Sets the managerId attribute of the User object
	 *
	 * @param tmp The new managerId value
	 * @since 1.1
	 */
	public void setManagerId(int tmp) {
		this.managerId = (tmp);
	}

	/**
	 * Sets the managerId attribute of the User object DPM 8.14.03
	 *
	 * @param tmp The new managerId value
	 * @since 1.1
	 */
	public void setManagerId(String tmp) {
		if (tmp != null) {
			this.managerId = Integer.parseInt(tmp);
		}
	}

	/**
	 * Sets the EnteredBy attribute of the User object
	 *
	 * @param tmp The new EnteredBy value
	 * @since 1.1
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}

	/**
	 * Sets the EnteredBy attribute of the User object
	 *
	 * @param tmp The new EnteredBy value
	 * @since 1.1
	 */
	public void setEnteredBy(String tmp) {
		if (tmp != null) {
			this.enteredBy = Integer.parseInt(tmp);
		}
	}

	/**
	 * Sets the ModifiedBy attribute of the User object
	 *
	 * @param tmp The new ModifiedBy value
	 * @since 1.1
	 */
	public void setModifiedBy(int tmp) {
		this.modifiedBy = tmp;
	}

	/**
	 * Sets the ModifiedBy attribute of the User object
	 *
	 * @param tmp The new ModifiedBy value
	 * @since 1.1
	 */
	public void setModifiedBy(String tmp) {
		this.modifiedBy = Integer.parseInt(tmp);
	}

	/**
	 * Sets the Enabled attribute of the User object
	 *
	 * @param tmp The new Enabled value
	 * @since 1.1
	 */
	public void setEnabled(boolean tmp) {
		this.enabled = tmp;
		checkHidden();
	}

	/**
	 * Sets the Enabled attribute of the User object
	 *
	 * @param tmp The new Enabled value
	 * @since 1.1
	 */
	public void setEnabled(String tmp) {
		if (tmp.toLowerCase().equals("false")) {
			this.enabled = false;
		} else {
			this.enabled = true;
		}
		checkHidden();
	}

	/**
	 * Sets the lastLogin attribute of the User object
	 *
	 * @param tmp The new lastLogin value
	 */
	public void setLastLogin(java.sql.Timestamp tmp) {
		this.lastLogin = tmp;
	}

	/**
	 * Sets the lastLogin attribute of the User object
	 *
	 * @param tmp The new lastLogin value
	 */
	public void setLastLogin(String tmp) {
		this.lastLogin = DateUtils.parseTimestampString(tmp);
	}

	/**
	 * Sets the Ip attribute of the User object
	 *
	 * @param tmp The new Ip value
	 */
	public void setIp(String tmp) {
		this.ip = tmp;
	}

	/**
	 * Sets the TimeZone attribute of the User object
	 *
	 * @param tmp The new TimeZone value
	 */
	public void setTimeZone(String tmp) {
		this.timeZone = tmp;
	}

	/**
	 * Sets the BuildContact attribute of the User object
	 *
	 * @param tmp The new BuildContact value
	 * @since 1.29
	 */
	public void setBuildContact(boolean tmp) {
		this.buildContact = tmp;
	}

	/**
	 * Sets the buildContactDetails attribute of the User object
	 *
	 * @param tmp The new buildContactDetails value
	 */
	public void setBuildContactDetails(boolean tmp) {
		this.buildContactDetails = tmp;
	}

	/**
	 * Sets the BuildHierarchy attribute of the User object
	 *
	 * @param tmp The new BuildHierarchy value
	 * @since 1.23
	 */
	public void setBuildHierarchy(boolean tmp) {
		this.buildHierarchy = tmp;
	}

	/**
	 * Sets the Contact attribute of the User object
	 *
	 * @param tmp The new Contact value
	 * @since 1.1
	 */
	public void setContact(Contact tmp) {
		this.contact = tmp;
	}

	/**
	 * Sets the ManagerUser attribute of the User object
	 *
	 * @param tmp The new ManagerUser value
	 * @since 1.17
	 */
	public void setManagerUser(User tmp) {
		
	}

	/**
	 * Sets the ChildUsers attribute of the User object
	 *
	 * @param tmp The new ChildUsers value
	 */
	public void setChildUsers(UserList tmp) {
		this.childUsers = tmp;
	}

	/**
	 * Sets the roleType attribute of the User object
	 *
	 * @param tmp The new roleType value
	 */
	public void setRoleType(int tmp) {
		this.roleType = tmp;
	}

	/**
	 * Sets the roleType attribute of the User object
	 *
	 * @param tmp The new roleType value
	 */
	public void setRoleType(String tmp) {
		this.roleType = Integer.parseInt(tmp);
	}

	/**
	 * Gets the expires attribute of the User object
	 *
	 * @return The expires value
	 */
	public java.sql.Timestamp getExpires() {
		return expires;
	}

	/**
	 * Gets the expiresString attribute of the User object
	 *
	 * @return The expiresString value
	 */
	public String getExpiresString() {
		String tmp = "";
		try {
			return DateFormat.getDateInstance(3).format(expires);
		} catch (NullPointerException e) {
		}
		return tmp;
	}

	/**
	 * Gets the entered attribute of the User object
	 *
	 * @return The entered value
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}

	/**
	 * Gets the modified attribute of the User object
	 *
	 * @return The modified value
	 */
	public java.sql.Timestamp getModified() {
		return modified;
	}

	/**
	 * Gets the modifiedString attribute of the User object
	 *
	 * @return The modifiedString value
	 */
	public String getModifiedString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
					.format(modified);
		} catch (NullPointerException e) {
		}
		return tmp;
	}

	/**
	 * Gets the enteredString attribute of the User object
	 *
	 * @return The enteredString value
	 */
	public String getEnteredString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
					.format(entered);
		} catch (NullPointerException e) {
		}
		return tmp;
	}

	/**
	 * Gets the lastLogin attribute of the User object
	 *
	 * @return The lastLogin value
	 */
	public java.sql.Timestamp getLastLogin() {
		return lastLogin;
	}

	/**
	 * Gets the lastLoginString attribute of the User object
	 *
	 * @return The lastLoginString value
	 */
	public String getLastLoginString() {
		String tmp = "";
		try {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG)
					.format(lastLogin);
		} catch (NullPointerException e) {
		}
		return tmp;
	}

	/**
	 * Gets the Assistant attribute of the User object
	 *
	 * @return The Assistant value
	 */
	public int getAssistant() {
		return assistant;
	}

	/**
	 * Gets the siteId attribute of the User object
	 *
	 * @return The siteId value
	 */
	public int getSiteId() {
		return siteId;
	}

	/**
	 * Gets the siteIdName attribute of the User object
	 *
	 * @return The siteIdName value
	 */
	public String getSiteIdName() {
		return siteIdName;
	}
	
	public void setSiteIdName(String siteIdName) {
		this.siteIdName = siteIdName;
	}

	/**
	 * Gets the Alias attribute of the User object
	 *
	 * @return The Alias value
	 */
	public int getAlias() {
		return alias;
	}

	/**
	 * Gets the IsValid attribute of the User object
	 *
	 * @return The IsValid value
	 */


	/**
	 * Gets the revenueIsValid attribute of the User object
	 *
	 * @return The revenueIsValid value
	 */

	/**
	 * Gets the Id attribute of the User object
	 *
	 * @return The Id value
	 * @since 1.1
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the Username attribute of the User object
	 *
	 * @return The Username value
	 * @since 1.1
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the PreviousUsername attribute of the User object
	 *
	 * @return The PreviousUsername value
	 * @since 1.28
	 */
	public String getPreviousUsername() {
		return previousUsername;
	}

	/**
	 * Gets the Password attribute of the User object
	 *
	 * @return The Password value
	 * @since 1.1
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the EncryptedPassword attribute of the User object
	 *
	 * @return The EncryptedPassword value
	 */
	public String getEncryptedPassword() {
		if (encryptedPassword != null) {
			return encryptedPassword;
		} else {
			return this.encryptPassword(password);
		}
	}

	/**
	 * Gets the Password1 attribute of the User object
	 *
	 * @return The Password1 value
	 * @since 1.1
	 */
	public String getPassword1() {
		return password1;
	}

	/**
	 * Gets the Password2 attribute of the User object
	 *
	 * @return The Password2 value
	 * @since 1.1
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * Gets the ContactId attribute of the User object
	 *
	 * @return The ContactId value
	 * @since 1.1
	 */
	public int getContactId() {
		return contactId;
	}

	/**
	 * Gets the RoleId attribute of the User object
	 *
	 * @return The RoleId value
	 * @since 1.1
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * Gets the managerId attribute of the User object
	 *
	 * @return The manager value
	 * @since 1.1
	 */
	public int getManagerId() {
		return managerId;
	}

	/**
	 * Gets the Role attribute of the User object
	 *
	 * @return The Role value
	 * @since 1.11
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Gets the EnteredBy attribute of the User object
	 *
	 * @return The EnteredBy value
	 * @since 1.1
	 */
	public int getEnteredBy() {
		return enteredBy;
	}

	/**
	 * Gets the Ip attribute of the User object
	 *
	 * @return The Ip value
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Gets the TimeZone attribute of the User object
	 *
	 * @return The TimeZone value
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * Gets the timeZoneActual attribute of the User object
	 *
	 * @return The timeZoneActual value
	 */
	public TimeZone getTimeZoneActual() {
		TimeZone tZone = Calendar.getInstance().getTimeZone();
		if (timeZone != null && !"".equals(timeZone)) {
			tZone = TimeZone.getTimeZone(timeZone);
		}
		return tZone;
	}

	/**
	 * Gets the currency attribute of the User object
	 *
	 * @return The currency value
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency attribute of the User object
	 *
	 * @param tmp The new currency value
	 */
	public void setCurrency(String tmp) {
		this.currency = tmp;
	}

	/**
	 * Gets the language attribute of the User object
	 *
	 * @return The language value
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language and locale attributes of the User object
	 *
	 * @param tmp The new language value
	 */
	public void setLanguage(String tmp) {
		this.language = tmp;
		if (language == null) {
			locale = Locale.getDefault();
		} else {
			switch (language.length()) {
			case 2:
				locale = new Locale(language.substring(0, 2), "");
				break;
			case 5:
				locale = new Locale(language.substring(0, 2), language.substring(3, 5));
				break;
			case 10:
				// fr_FR_EURO
				locale = new Locale(language.substring(0, 2), language.substring(3, 5),
						language.substring(6));
				break;
			default:
				locale = Locale.getDefault();
				break;
			}
		}
	}

	/**
	 * Gets the locale attribute of the User object
	 *
	 * @return The locale value
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the locale attribute of the User object
	 *
	 * @param tmp The new locale value
	 */
	public void setLocale(Locale tmp) {
		this.locale = tmp;
	}

	/**
	 * Gets the FullChildList attribute of the User object
	 *
	 * @param inList      Description of Parameter
	 * @param currentList Description of Parameter
	 * @return The FullChildList value
	 * @since 1.23
	 */
	public UserList getFullChildList(UserList inList, UserList currentList) {
		if (inList != null) {
			Iterator j = inList.iterator();
			while (j.hasNext()) {
				User thisRec = (User) j.next();
				thisRec.setHideHiddenChildren(this.getHideHiddenChildren());
				currentList.addElement(thisRec);
				UserList countList = thisRec.getShortChildList();
				if (countList != null && countList.getListSize() > 0) {
					currentList = thisRec.getFullChildList(countList, currentList);
				}
			}
		}
		return currentList;
	}

	/**
	 * Gets the ShortChildList attribute of the User object
	 *
	 * @return The ShortChildList value
	 * @since 1.20
	 */
	public UserList getShortChildList() {
		return this.childUsers;
	}

	/**
	 * Gets the Child attribute of the User object by navigating through the
	 * children.
	 *
	 * @param childId Description of Parameter
	 * @return The Child value
	 * @since 1.30
	 */
	public User getChild(int childId) {
		UserList shortChildList = this.getShortChildList();
		if (shortChildList != null) {
			// System.out.println("User-> Child List Size: " + shortChildList.size());
			UserList fullChildList = this.getFullChildList(shortChildList,
					new UserList());
			Iterator i = fullChildList.iterator();
			while (i.hasNext()) {
				User childRecord = (User) i.next();
				if (childRecord.getId() == childId) {
					return childRecord;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the ModifiedBy attribute of the User object
	 *
	 * @return The ModifiedBy value
	 * @since 1.1
	 */
	public int getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Gets the Enabled attribute of the User object
	 *
	 * @return The Enabled value
	 * @since 1.1
	 */
	public boolean getEnabled() {
		return enabled;
	}
	
	public boolean getEnabledAsQualifica() {
		return enabledAsQualifica;
	}

	/**
	 * Geentuhe Contact attribute of the User object
	 *
	 * @return The Contact value
	 * @since 1.1
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * Gets the ManagerUser attribute of the User object
	 *
	 * @return The ManagerUser value
	 * @since 1.17
	 */
	public User getManagerUser() {
		return null;
	}

	/**
	 * Gets the roleType attribute of the User object
	 *
	 * @return The roleType value
	 */
	public int getRoleType() {
		return roleType;
	}

	/**
	 * @return the startOfDay
	 */
	public int getStartOfDay() {
		return startOfDay;
	}

	/**
	 * @param startOfDay the startOfDay to set
	 */
	public void setStartOfDay(int startOfDay) {
		this.startOfDay = startOfDay;
	}

	/**
	 * @param startOfDay the startOfDay to set
	 */
	public void setStartOfDay(String startOfDay) {
		try{
			this.startOfDay = Integer.parseInt(startOfDay);
		}catch (Exception e) {
			this.startOfDay = -1;
		}
	}

	/**
	 * @return the endOfDay
	 */
	public int getEndOfDay() {
		return endOfDay;
	}

	/**
	 * @param endOfDay the endOfDay to set
	 */
	public void setEndOfDay(int endOfDay) {
		this.endOfDay = endOfDay;
	}

	/**
	 * @param endOfDay the endOfDay to set
	 */
	public void setEndOfDay(String endOfDay) {
		try{
			this.endOfDay = Integer.parseInt(endOfDay);
		}catch (Exception e) {
			this.endOfDay = -1;
		}
	}

	/**
	 * Returns whether this user is above the specified userId. If the specified
	 * userId is a child of this user, returns true.
	 *
	 * @param userId Description of Parameter
	 * @return The managerOf value
	 */
	public boolean isManagerOf(int userId) {
		return (this.getChild(userId) != null);
	}

	/**
	 * Returns true if the current user is a portal user
	 *
	 * @return true if portal user.
	 */
	public boolean isPortalUser() {
		return (this.roleType > 0);
	}

	/**
	 * Sets the opportunityLock to true, causing any other requests to this
	 * method to block until released.
	 *
	 * @since 1.19
	 */
	public void doOpportunityLock() {
		while (opportunityLock == true) {
		}
		synchronized (this) {
			while (opportunityLock) {
			}
			this.opportunityLock = true;
		}
	}

	/**
	 * Description of the Method
	 */
	public void doRevenueLock() {
		while (revenueLock == true) {
		}
		synchronized (this) {
			while (revenueLock) {
			}
			this.revenueLock = true;
		}
	}

	/**
	 * Sets the opportunityLock to false, allowing any waiting requests to try
	 * and lock it.
	 *
	 * @since 1.19
	 */
	public void doOpportunityUnlock() {
		this.opportunityLock = false;
	}

	/**
	 * Description of the Method
	 */
	public void doRevenueUnlock() {
		this.revenueLock = false;
	}

	/**
	 * Description of the Method
	 *
	 * @param db       Description of Parameter
	 * @param context  Description of Parameter
	 * @param currPass Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 */
	public int updatePassword(Connection db, ActionContext context,
			String currPass) throws SQLException {
		if (!isValidChangePass(context, currPass)) {
			return -1;
		} else {
			int resultCount = -1;
			if (this.getId() == -1) {
				throw new SQLException("User ID was not specified");
			}
			checkHidden();
			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();
			sql.append(
					"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
							"SET " + DatabaseUtils.addQuotes(db, "password") + " = ?, "+
							" webdav_password = ?, " +
							" temp_password = ?, " +
							" temp_webdav_password = ?, " +
							" hidden = ?, " +
							" modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
							" modifiedBy = ? " +
					" WHERE user_id = ? ");
			int i = 0;
			pst = db.prepareStatement(sql.toString());
			pst.setString(++i, encryptPassword(password1));
			pst.setString(++i, this.encryptWebdavPassword(username, password1));
			pst.setString(++i, null);
			pst.setString(++i, null);
			pst.setBoolean(++i, this.getHidden());
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, getId());
			resultCount = pst.executeUpdate();
			pst.close();
			return resultCount;
		}
	}

	/**
	 * Checks to see if the user entered password is correct. If correct, then a
	 * webdav encrypted password is generated and stored
	 *
	 * @param db  Description of the Parameter
	 * @param pwd Description of the Parameter
	 * @throws SQLException Description of the Exception
	 */
	public void checkWebdavAccess(Connection db, String pwd) throws SQLException {
		String tmpPwd = encryptWebdavPassword(username, pwd);
		if (tmpPwd != null && !tmpPwd.equals(webdavPassword)) {
			// Existing webdav password is no longer valid. generate a new webdav
			// password and
			// update the user's webdav password
			if (System.getProperty("DEBUG") != null) {
				System.out.println("User-> Generating a new webdav password");
			}
			if (this.getId() == -1) {
				throw new SQLException("User ID was not specified");
			}
			checkHidden();
			PreparedStatement pst = null;
			StringBuffer sql = new StringBuffer();
			sql.append(
					"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
							"SET webdav_password = ?, hidden = ?, " +
							"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedBy = ? " +
					"WHERE user_id = ? ");
			int i = 0;
			pst = db.prepareStatement(sql.toString());
			pst.setString(++i, tmpPwd);
			pst.setBoolean(++i, this.getHidden());
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, getId());
			pst.executeUpdate();

			this.setWebdavPassword(tmpPwd);

			pst.close();
		}
		else
		{
			this.setWebdavPassword("");
		}
	}

	/**
	 * Description of the Method
	 *
	 * @param db Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 */
	public int select(Connection db) throws SQLException {

		if (expires != null && (new java.util.Date()).after(expires)) {
			// set error message
			return -1;
		} else {
			if (alias > 0) {
				return alias;
			} else {
				return this.id;
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
	public int updatePortalUser(Connection db) throws SQLException {

		int updated = -1;
		if (hasErrors()) {
			return updated;
		}
		if (System.getProperty("DEBUG") != null) {
			System.out.println("User-> Beginning update");
		}
		checkHidden();
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
				"SET expires = ?, ");

		if (password1 != null) {
			sql.append("" + DatabaseUtils.addQuotes(db, "password") + " = ?,");
		}

		sql.append(
				"role_id = ?, hidden = ?, " +
						"modifiedBy = ?, " +
						"modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
						"WHERE username = ? " +
						"AND modified " + ((this.getModified() == null) ? "IS NULL " : "= ? "));

		pst = db.prepareStatement(sql.toString());

		int i = 0;
		pst.setTimestamp(++i, this.expires);
		if (password1 != null) {
			pst.setString(++i, encryptPassword(password1));
		}
		pst.setInt(++i, this.roleId);
		pst.setBoolean(++i, this.getHidden());
		pst.setInt(++i, modifiedBy);
		pst.setString(++i, this.username);
		if (this.getModified() != null) {
			pst.setTimestamp(++i, this.modified);
		}

		// Update the user
		updated = pst.executeUpdate();
		pst.close();

		return updated;
	}

	/**
	 * Inserts the current user record into the database
	 *
	 * @param db      Description of Parameter
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 * @throws Exception Description of the Exception
	 * @since 1.1
	 */


	/**
	 * Deletes the current user record
	 *
	 * @param db Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	public boolean disable(Connection db) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("ID not specified.");
		}
		checkHidden(false);
		int resultCount = 0;
		PreparedStatement pst = db.prepareStatement(
				"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
						"SET enabled = ?, hidden = ?, " +
						"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedBy = ? " +
				"WHERE user_id = ? ");
		pst.setBoolean(1, false);
		pst.setBoolean(2, this.getHidden());
		pst.setInt(3, this.getModifiedBy());
		pst.setInt(4, this.getId());
		resultCount = pst.executeUpdate();
		pst.close();

		if (resultCount == 0) {
			// errors.put("actionError", "User could not be disabled because it no
			// longer exists.");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException Description of the Exception
	 */
	public boolean enable(Connection db) throws SQLException {
		if (this.getId() == -1) {
			throw new SQLException("ID not specified.");
		}
		checkHidden(true);
		int resultCount = 0;
		PreparedStatement pst = db.prepareStatement(
				"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
						"SET enabled = ? , hidden = ?, " +
						"modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", modifiedBy = ? " +
				"WHERE user_id = ? ");
		pst.setBoolean(1, true);
		pst.setBoolean(2, this.getHidden());
		pst.setInt(3, this.getModifiedBy());
		pst.setInt(4, this.getId());
		resultCount = pst.executeUpdate();
		pst.close();

		if (resultCount == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method builds are extra data for a user... Children Users, etc.
	 *
	 * @param db Description of Parameter
	 * @throws SQLException Description of Exception
	 * @since 1.12
	 */


	/**
	 * Description of the Method
	 *
	 * @param db Description of Parameter
	 * @throws SQLException Description of Exception
	 * @since 1.18
	 */
	public void updateLogin(Connection db,String suffisso,ActionContext context) {
		if (this.id > -1) {
			checkHidden();
			String sql =
					"UPDATE " + DatabaseUtils.addQuotes(db, "access")+suffisso + " " +
							"SET last_login = " + DatabaseUtils.getCurrentTimestamp(db) + ",access_position_lat =?,access_position_lon=?,access_position_err =? , " +
							"last_ip = ? " +
							" WHERE user_id = ? ";
			PreparedStatement pst;
			try {
				pst = db.prepareStatement(sql);
				pst.setDouble(1, this.getAccess_position_lat());
				pst.setDouble(2, this.getAccess_position_lon());
				pst.setString(3, this.getAccess_position_err());
				pst.setString(4, this.ip);
				pst.setInt(5, this.id);
				pst.executeUpdate();
				pst.close();

				insertLogRecord(db,context);
			} catch (SQLException e) {

				System.out.println("Eccezione catturata per l'update in access!");
			}

		}

	}

	public ArrayList<String> getCoordinateUltimoAccesso(Connection db) throws SQLException
	{
		ArrayList<String> getCoordinate = new ArrayList<String>();

		if (this.getAccess_position_lat()<=0 && this.getAccess_position_lon()<=0 )
		{
			PreparedStatement pst = db.prepareStatement("select access_position_lat,access_position_lon,access_position_err,max(entered) as entered "+
					"from access_log " +
					"where  ip ilike ? and access_position_lat>0 and access_position_lon>0 " +
					"group by access_position_lat,access_position_lon,access_position_err " +
					"order by entered desc limit 1");
			pst.setString(1,ip);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				double lat = rs.getDouble(1);
				double lon = rs.getDouble(2);
				String err = rs.getString(3);
				Timestamp entered = rs.getTimestamp(4);
				getCoordinate.add(0,lat+"");
				getCoordinate.add(1,lon+"");
				getCoordinate.add(2,err);
				getCoordinate.add(3,entered+"");

			}

		}


		return getCoordinate ;

	}



	/**
	 * Description of the Method
	 *
	 * @param db Description of Parameter
	 * @throws SQLException Description of Exception
	 */
	public void insertLogRecord(Connection db,ActionContext context) {
		AccessLog thisLog = new AccessLog();
		thisLog.setUserId(this.getId());
		thisLog.setUsername(this.getUsername());
		thisLog.setIp(this.getIp());
		thisLog.setAccess_position_err(this.getAccess_position_err());
		thisLog.setAccess_position_lat(this.getAccess_position_lat());
		thisLog.setAccess_position_lon(this.getAccess_position_lon());
		try {
			thisLog.insert(db,context);
		} catch (SQLException e) {
			System.out.println("Eccezione catturata per l'inserimento del log!");
		}

	}


	/**
	 * Gets the grossPipelineCurrency attribute of the User object
	 *
	 * @param divisor Description of the Parameter
	 * @return The grossPipelineCurrency value
	 */
	public double getGrossPipeline(int divisor) {
		return (java.lang.Math.round(pipelineValue) / (double) divisor);
	}



	/**
	 * Gets the Valid attribute of the User object
	 *
	 * @param db      Description of Parameter
	 * @param context Description of Parameter
	 * @return The Valid value
	 * @throws Exception Description of the Exception
	 * @since 1.12
	 */
	protected boolean isValid(Connection db, ActionContext context)
			throws Exception {
		isValidNoPass(db, context);

		if (contactId < 1
				&& (contact == null || !ObjectValidator.validate(null,null, db, contact))) {
			errors.put("contactIdError",
					"Contact needs to be selected or newly created first");
		}

		if (password1 == null || password1.trim().equals("")) {
			errors.put("password1Error", "Password cannot be left blank");
		}

		if (!password1.equals(password2)) {
			errors.put("password2Error", "Verification password does not match");
		}

		Timestamp currentTime = new Timestamp(Calendar.getInstance()
				.getTimeInMillis());
		if (hidden && expires != null && currentTime.before(expires) && enabled) {
			errors
			.put("expiresError",
					"The user cannot be hidden when the user is enabled and has not expired.");
			errors
			.put("enabledError",
					"The user cannot be hidden when the user is enabled and has not expired.");
		}

		if (hasErrors()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gets the ValidChangePass attribute of the User object
	 *
	 * @param context     Description of Parameter
	 * @param currentPass Description of Parameter
	 * @return The ValidChangePass value
	 */
	protected boolean isValidChangePass(ActionContext context, String currentPass) {

		if (!(this.getEncryptedPassword().equals(currentPass)) || password == null
				|| password.trim().equals("")) {
			errors.put("passwordError", "Incorrect value for current password");
		}

		if (password1 == null || password1.trim().equals("")) {
			errors.put("password1Error", "Password cannot be left blank");
		}

		if (!password1.equals(password2)) {
			errors.put("password2Error", "Verification password does not match");
		}

		if (hasErrors()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gets the ValidNoPass attribute of the User object
	 *
	 * @param db      Description of Parameter
	 * @param context Description of Parameter
	 * @return The ValidNoPass value
	 * @throws SQLException Description of Exception
	 * @since 1.27
	 */
	protected boolean isValidNoPass(Connection db, ActionContext context)
			throws SQLException {

		if (username == null || username.trim().equals("")) {
			errors.put("usernameError", "Username cannot be left blank");
		} else {
			if (isDuplicate(db)) {
				errors.put("usernameError", "Username is already in use");
			}
		}

		if (roleId < 0) {
			errors.put("roleError", "Role needs to be selected");
		}

		// Check hierarchy context for circular references
		if (managerId > 0 && id > -1 && alias == -1) {
			if (managerId == id) {
				// Check 1: User cannot report to self
				errors.put("managerIdError", "User cannot report to self");
			} else {
				// Check 2: User cannot report to someone already beneath them
				ConnectionElement ce = (ConnectionElement) context.getRequest()
						.getSession().getAttribute("ConnectionElement");
				SystemStatus systemStatus = (SystemStatus) ((Hashtable) context
						.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
				User updatedUser = systemStatus.getHierarchyList().getUser(id);
				User testChild = updatedUser.getChild(managerId);

				if (testChild != null) {
					// Since the new manager is a child of this user, display the
					// hierarchy for the user
					// Start at the testChild and work up to current user
					Stack names = new Stack();
					int currentId = testChild.getId();
					while (currentId != id) {
						String childName = testChild.getContact().getNameFirstLast();
						names.push(childName);
						testChild = testChild.getManagerUser();
						currentId = testChild.getId();
					}
					names.push(updatedUser.getContact().getNameFirstLast());
					// Now work back down and show the hierarchy
					StringBuffer sb = new StringBuffer();
					sb
					.append("Cannot create a circular hierarchy, review current hierarchy:\r\n");
					while (!names.empty()) {
						sb.append((String) names.pop());
						if (!names.empty()) {
							sb.append(" < ");
						}
					}
					errors.put("managerIdError", sb.toString());
				}
			}
		}

		if (hasErrors()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Populates the current user record from a ResultSet
	 *
	 * @param rs Description of Parameter
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		// access table
		this.setUsername(rs.getString("username"));
		String thisPassword = rs.getString("password");
		this.setPassword(thisPassword);
		this.setEncryptedPassword(thisPassword);
		this.dataScadenza = rs.getDate("data_scadenza");
		this.setRoleId(rs.getInt("role_id"));
		try
		{
			enabledAsQualifica = rs.getBoolean("enabled_as_qualifica");
		}
		catch(Exception e)
		{
			System.out.println("Eccezione gestita: enabled_as_qualifica non è presente nel ResultSet");
		}
		lastLogin = rs.getTimestamp("last_login");
		this.setManagerId(rs.getInt("manager_id"));
		this.setSiteId(DatabaseUtils.getInt(rs, "siteid"));
		ip = rs.getString("last_ip");
		timeZone = rs.getString("timezone");
		
		expires = rs.getTimestamp("expires");
		this.setAlias(rs.getInt("alias"));
		this.setContactId(rs.getInt("contact_id_link"));
		this.setId(rs.getInt("access_user_id"));
		enabled = rs.getBoolean("access_enabled");
		this.setAssistant(rs.getInt("access_assistant"));
		entered = rs.getTimestamp("access_entered");
		enteredBy = rs.getInt("access_enteredby");
		modified = rs.getTimestamp("access_modified");
		modifiedBy = rs.getInt("access_modifiedby");
		currency = rs.getString("currency");
		language = rs.getString("language");
		this.setWebdavPassword(rs.getString("webdav_password"));
		hidden = rs.getBoolean("hidden");
		hasWebdavAccess = rs.getBoolean("allow_webdav_access");
		hasHttpApiAccess = rs.getBoolean("allow_httpapi_access");
		// role table
		this.setRole(rs.getString("systemrole"));
		roleType = DatabaseUtils.getInt(rs, "role_type");
		// user table (manager)
		if (managerId > -1) {
			managerUserEnabled = rs.getBoolean("mgr_enabled");
		} else {
			managerUserEnabled = false;
		}
		
		try
		{
			idStrutturaAslComplessa = rs.getInt("id_struttura_asl_complessa");
		}
		catch(SQLException e)
		{
			
		}
		// lookup site_id table
		siteIdName = rs.getString("site_id_name");

		trashed_date = rs.getTimestamp("trashed_date");
		gruppo_ruolo = rs.getInt("super_ruolo");
		descrizione_gruppo_ruolo = rs.getString("descrizione_super_ruolo");
		this.getContact().setNameFirst(rs.getString("namefirst"));
		this.getContact().setNameLast(rs.getString("namelast"));
		inDpat = rs.getBoolean("in_dpat");
		inAccess = rs.getBoolean("in_access");
		inNucleoIspettivo = rs.getBoolean("in_nucleo_ispettivo");
		try
		{
			caricolo_lavoro_annuale=rs.getInt("carico_lavoro_annuale");
		}
		catch(SQLException e)
		{

		}
		
		
		suap.setCallbackSuap(rs.getString("callback_suap"));
		suap.setCallbackSuapKo(rs.getString("callback_suap_ko"));
		suap.setPec(rs.getString("pec_suap"));
		




	}
	
	
	
	public void buildRecord(User user) throws SQLException {
		// access table
		this.setUsername(user.getUsername());
		String thisPassword = user.getPassword();
		this.setPassword(thisPassword);
		this.dataScadenza = user.getDataScadenza();
		this.setRoleId(user.getRoleId());
		this.setSiteId(user.getSiteId());
		
		expires = user.getExpires();
		this.setContactId(user.getContactId());
		this.setId(user.getId());
		enabled = user.getEnabled();
		this.setRole(user.getRole());
		siteIdName = user.getSiteIdName();
		gruppo_ruolo = user.getGruppo_ruolo();
		descrizione_gruppo_ruolo =user.getDescrizione_gruppo_ruolo();
		this.getContact().setNameFirst(user.getContact().getNameFirst());
		this.getContact().setNameLast(user.getContact().getNameLast());
		inDpat = user.isInDpat();
		inAccess = user.isInAccess();
		inNucleoIspettivo = user.isInNucleoIspettivo();
		
		
		suap.setCallbackSuap(user.getSuap().getCallbackSuap());
		suap.setCallbackSuapKo(user.getSuap().getCallbackSuapKo());
		suap.setPec(user.getSuap().getPec());
		





	}

	/**
	 * Checks to see if the user is a duplicate in the database
	 *
	 * @param db Description of Parameter
	 * @return The Duplicate value
	 * @throws SQLException Description of Exception
	 * @since 1.11
	 */
	public boolean isDuplicate(Connection db) throws SQLException {
		boolean duplicate = false;
		if (previousUsername != null && previousUsername.equals(username)) {
			return false;
		}
		//finding if an enabled user exists
		PreparedStatement pst = db.prepareStatement(
				"SELECT * " +
						"FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
						"WHERE " + DatabaseUtils.toLowerCase(db) + "(username) = ? " +
				"AND enabled = ? ");
		pst.setString(1, getUsername().toLowerCase());
		pst.setBoolean(2, true);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			duplicate = true;
		}
		rs.close();
		pst.close();

		//finding if a disabled user exists whose contact is enabled
		//  return true if exists as such a user can be enabled
		pst = db.prepareStatement(
				"SELECT * " +
						"FROM " + DatabaseUtils.addQuotes(db, "access") + ", contact " +
						"WHERE " + DatabaseUtils.toLowerCase(db) + "(username) = ? " +
						"AND " + DatabaseUtils.addQuotes(db, "access") + ".enabled = ? " +
						"AND contact.user_id = " + DatabaseUtils.addQuotes(db, "access") + ".user_id " +
				"AND contact.enabled = ? ");
		int i = 0;
		pst.setString(++i, getUsername().toLowerCase());
		pst.setBoolean(++i, false);
		pst.setBoolean(++i, true);
		rs = pst.executeQuery();
		if (rs.next()) {
			duplicate = true;
		}
		rs.close();
		pst.close();

		return duplicate;
	}

	/**
	 * Gets the contactId attribute of the User class
	 *
	 * @param db     Description of the Parameter
	 * @param userId Description of the Parameter
	 * @return The contactId value
	 * @throws SQLException Description of the Exception
	 */
	public static int getContactId(Connection db, int userId) throws SQLException {
		int contactId = -1;
		if (userId == -1) {
			throw new SQLException("User Id not specified");
		}

		PreparedStatement pst = db.prepareStatement(
				"SELECT contact_id " +
						"FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
				"WHERE user_id = ? ");
		pst.setInt(1, userId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			contactId = rs.getInt("contact_id");
		}
		rs.close();
		pst.close();
		return contactId;
	}

	/**
	 * Updates the list of users that this user manages.
	 *
	 * @param db Description of Parameter
	 * @throws SQLException Description of Exception
	 * @since 1.28
	 */
	private void buildChildren(Connection db,String suffisso) throws SQLException {
		childUsers = new UserList();
		childUsers.setManagerId(this.getId());
		childUsers.setManagerUser(this);
		childUsers.setBuildHierarchy(true);
		if (hideHiddenChildren) {
			childUsers.setHidden(Constants.FALSE);
		}
		childUsers.buildList(db,suffisso);
	}

	/**
	 * Updates the current user record
	 *
	 * @param db      Description of Parameter
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	public int update(Connection db, ActionContext context,String suffisso) throws SQLException {
		int i = -1;
		i = this.update(db, context, false);
		return i;
	}

	/**
	 * Description of the Method
	 *
	 * @param db Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException Description of the Exception
	 */
	public int update(Connection db) throws SQLException {
		return update(db, null, true);
	}

	/**
	 * Updates the current user record, this method is used internally
	 *
	 * @param db       Description of Parameter
	 * @param override Description of Parameter
	 * @param context  Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException Description of Exception
	 * @since 1.1
	 */
	private int update(Connection db, ActionContext context, boolean override)
			throws SQLException {
		int resultCount = 0;
		int defaultManager = -1;
		if (context != null && !isValidNoPass(db, context)) {
			return -1;
		}
		if (this.getId() == -1) {
			throw new SQLException("User ID was not specified");
		}
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
						"SET username = ?, manager_id = ?, role_id = ?, expires = ?, site_id = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
		if (password1 != null) {
			sql.append("" + DatabaseUtils.addQuotes(db, "password") + " = ?, ");
			sql.append("webdav_password = ?, ");
		}
		if (enteredBy > -1) {
			sql.append("enteredby = ?, ");
		}
		if (modifiedBy > -1) {
			sql.append("modifiedby = ?, ");
		}
		if (contactId > -1) {
			sql.append("contact_id = ?, ");
		}
		if (assistant > -1) {
			sql.append("assistant = ?, ");
		}
		sql
		.append("alias = ?, hidden = ?, allow_webdav_access = ?, allow_httpapi_access = ? ");
		sql.append("WHERE user_id = ? ");

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, username);
		if (this.getAlias() > -1) {
			pst.setInt(++i, defaultManager);
		} else {
			pst.setInt(++i, this.getManagerId());
		}
		pst.setInt(++i, roleId);
		if (expires == null) {
			pst.setNull(++i, java.sql.Types.TIMESTAMP);
		} else {
			pst.setTimestamp(++i, this.getExpires());
		}
		DatabaseUtils.setInt(pst, ++i, getSiteId());
		if (password1 != null) {
			pst.setString(++i, encryptPassword(password1));
			pst.setString(++i, encryptWebdavPassword(username, password1));
		}
		if (enteredBy > -1) {
			pst.setInt(++i, enteredBy);
		}
		if (modifiedBy > -1) {
			pst.setInt(++i, modifiedBy);
		}
		if (contactId > -1) {
			pst.setInt(++i, contactId);
		}
		if (assistant > -1) {
			pst.setInt(++i, assistant);
		}
		pst.setInt(++i, alias);
		pst.setBoolean(++i, hidden);
		pst.setBoolean(++i, hasWebdavAccess);
		pst.setBoolean(++i, hasHttpApiAccess);
		pst.setInt(++i, getId());
		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}

	/**
	 * Hashes a password... the resulting encrypted password cannot be decrypted
	 * since this is one-way.
	 *
	 * @param tmp Description of Parameter
	 * @return Description of the Returned Value
	 * @since 1.1
	 */
	private String encryptPassword(String tmp) {
		return PasswordHash.encrypt(tmp);
	}

	/**
	 * In the Digest Authentication the client sends an encrypted request-digest
	 * to the server which is made of 3 components. The server will need to
	 * generate a similar digest and will need to compare it with the
	 * request-digest to see if there is a match so as to validate the request.
	 * The first component in the digest is an encrypted value computed by the
	 * following hash<p>
	 * <p/>
	 * <p/>
	 * <p/>
	 * First component = H [username + ":" + realm + ":" + password]<p>
	 * <p/>
	 * <p/>
	 * <p/>
	 * Since the password needs to be clear text, we determine the first
	 * component only when a user is added or updated and store this as the
	 * webdav_password field.
	 *
	 * @param username Description of the Parameter
	 * @param password Description of the Parameter
	 * @return Description of the Return Value
	 */
	private String encryptWebdavPassword(String username, String password) {
		if (username != null && password != null) {
			return PasswordHash.encrypt(username + ":" + WebdavServlet.CFS_USER_REALM
					+ ":" + password);
		}
		return null;
	}

	/**
	 * Returns a comma delimited string of all users in this user's hierarchy for
	 * querying records.
	 *
	 * @return The idRange value
	 */
	public String getIdRange() {
		UserList shortChildList = this.getShortChildList();
		UserList fullChildList = this.getFullChildList(shortChildList,
				new UserList());
		return (fullChildList.getUserListIds(id));
	}

	/**
	 * Gets the numberOfSimilarUsernames attribute of the User class
	 *
	 * @param db          Description of the Parameter
	 * @param tmpUsername Description of the Parameter
	 * @return The numberOfSimilarUsernames value
	 * @throws SQLException Description of the Exception
	 */
	public static int getNumberOfSimilarUsernames(Connection db,
			String tmpUsername) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pst = db.prepareStatement(
				"SELECT count(*) as recordcount " +
						"FROM " + DatabaseUtils.addQuotes(db, "access") + " " +
				"WHERE username LIKE (?)");
		int i = 0;
		pst.setString(++i, tmpUsername + "%");
		rs = pst.executeQuery();
		int no = 0;
		if (rs.next()) {
			no = rs.getInt("recordcount");
		}
		rs.close();
		pst.close();
		return no;
	}

	/**
	 * Returns the names of the fields that must be adjusted for time
	 *
	 * @return The timeZoneParams value
	 */
	public static ArrayList getTimeZoneParams() {
		ArrayList thisList = new ArrayList();
		thisList.add("expires");
		return thisList;
	}

	/**
	 * Gets the idByEmailAddress attribute of the User class
	 *
	 * @param db    Description of the Parameter
	 * @param email Description of the Parameter
	 * @return The idByEmailAddress value
	 * @throws SQLException Description of the Exception
	 */
	public static int getIdByEmailAddress(Connection db, String email)
			throws SQLException {
		int userId = -1;
		PreparedStatement pst = db.prepareStatement(
				"SELECT user_id " +
						"FROM contact c, contact_emailaddress e " +
						"WHERE " + DatabaseUtils.toLowerCase(db) + "(e.email) = ? " +
						"AND c.contact_id = e.contact_id " +
						"AND user_id IS NOT NULL " +
				"AND user_id > 0 ");
		pst.setString(1, email.toLowerCase());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			userId = rs.getInt("user_id");
		}
		rs.close();
		pst.close();
		return userId;
	}

	/**
	 * Gets the idByEmailAddress attribute of the User class
	 *
	 * @param db       Description of the Parameter
	 * @param username Description of the Parameter
	 * @return The exists value
	 * @throws SQLException Description of the Exception
	 */
	public boolean exists(Connection db, String username) throws SQLException {
		PreparedStatement pst = db.prepareStatement(
				"SELECT user_id "
						+ "FROM " + DatabaseUtils.addQuotes(db, "access") + " a " +
						"WHERE  username = ? "
						+ "AND user_id IS NOT NULL "
						+ "AND user_id > 0 " 
						+ " AND trashed_date is null ");
		pst.setString(1, username.toLowerCase());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			id = rs.getInt("user_id");
		}
		rs.close();
		pst.close();
		if (id > -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Description of the Method
	 */
	private void checkHidden() {
		Timestamp currentTime = new Timestamp(
				Calendar.getInstance().getTimeInMillis());
		if (this.getExpires() != null && currentTime.before(this.getExpires()) && this.getEnabled()) {
			this.setHidden(false);
		} else if (this.getExpires() == null && this.getEnabled()) {
			this.setHidden(false);
		}
	}

	/**
	 * Description of the Method
	 *
	 * @param enabledValue Description of the Parameter
	 */
	private void checkHidden(boolean enabledValue) {
		Timestamp currentTime = new Timestamp(
				Calendar.getInstance().getTimeInMillis());
		if (this.getExpires() != null && currentTime.before(this.getExpires()) && enabledValue) {
			this.setHidden(false);
		} else if (this.getExpires() == null && enabledValue) {
			this.setHidden(false);
		}
	}

	/**
	 * Description of the Method
	 *
	 * @return Description of the Return Value
	 */
	public String toString() {
		return this.getUsername() + "(" + this.getId() + ")";
	}

	public String toString2()
	{

		HashMap<String, Object> obj = new HashMap<>();
		String ret = "" ;
		ret += "{_id_:_"+id+"_," ;
		obj.put("_id_", id);

		if (this.contact !=null)
		{
			ret += "_nome_:_"+this.contact.getNameFirst().replaceAll("'", " ")+"_," ;
			obj.put("_nome_", this.contact.getNameFirst());

			ret += "_cognome_:_"+this.contact.getNameLast().replaceAll("'", " ")+"_," ;
			obj.put("_cognome_", this.contact.getNameLast().replaceAll("'", " "));

			ret += "_cf_:_"+ this.getContact().getCodiceFiscale()+"_," ;
			obj.put("_cf_", this.getContact().getCodiceFiscale());

		}
		else
		{
			ret += "_nome_:_UTENTE NON MAPPATO_," ;
			obj.put("_nome_", "UTENTE NON MAPPATO");

			ret += "_cognome_:_UTENTE NON MAPPATO_," ;
			obj.put("_cognome_","UTENTE NON MAPPATO");

			ret += "_cf_:_UTENTE NON MAPPATO_," ;
			obj.put("_cf_","UTENTE NON MAPPATO");

		}
		ret += "_idasl_:_"+this.getSiteId()+"_}" ;
		obj.put("_idasl_", this.getSiteId());





		return ret ;

	}

	public int insertNewPassword(Connection db, String newPassword) throws SQLException{
		int resultCount = 0;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pst = null;
		sql.append("UPDATE " + DatabaseUtils.addQuotes(db, "access") + " SET ");
		if (newPassword != null) {
			sql.append("temp_password = ?, ");
			sql.append("temp_webdav_password = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " ");
		}
		sql.append("WHERE user_id = ? ");
		int i = 0;
		pst = db.prepareStatement(sql.toString());
		if (newPassword != null) {
			pst.setString(++i, encryptPassword(newPassword));
			pst.setString(++i, encryptWebdavPassword(username, newPassword));
		}
		pst.setInt(++i, id);
		resultCount = pst.executeUpdate();
		pst.close();
		return resultCount;
	} 

	public void overwritePassword(Connection db, String password, String username, int id) throws SQLException{
		int resultCount = 0;
		String pw = null;
		String tempPassword = null;    
		String tempWebdavPassword = null;    
		PreparedStatement pst = db.prepareStatement(
				"SELECT a.temp_password, a.temp_webdav_password " +
						"FROM " + DatabaseUtils.addQuotes(db, "access") + " a " +
						"WHERE " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? ");
		pst.setString(1, username.toLowerCase());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			tempPassword = rs.getString("temp_password");
			tempWebdavPassword = rs.getString("temp_webdav_password");
		}
		rs.close();
		pst.close();
		if (tempPassword.equals(password)){
			int i =0;
			StringBuffer sql = new StringBuffer();
			PreparedStatement ps = null;
			// Set password and webdav password
			sql.append("UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
					"SET " +
					"password = ?, " +
					"webdav_password = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
					"WHERE user_id = ? ");
			ps = db.prepareStatement(sql.toString());
			ps.setString(++i, tempPassword);
			ps.setString(++i, tempWebdavPassword);
			ps.setInt(++i, id);
			ps.executeUpdate();
			ps.close();

			i = 0;
			sql = new StringBuffer();
			//reset temporary passwords
			sql.append("UPDATE " + DatabaseUtils.addQuotes(db, "access") + " " +
					"SET " +
					"temp_password = ?, " +
					"temp_webdav_password = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
					"WHERE user_id = ? ");
			ps = db.prepareStatement(sql.toString());
			ps.setString(++i, null);
			ps.setString(++i, null);
			ps.setInt(++i, id);
			ps.executeUpdate();
			ps.close();
		}
	}


	public User(Connection db, int userId,String suffisso) throws SQLException {
		buildRecord(db, userId,suffisso);
	}
	public void buildRecord(Connection db, int userId,String suffisso) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * from lista_utenti_centralizzata a where 1=1");
		if (userId > -1) {
			sql.append("AND a.access_user_id = ? ");
		} else {
			sql.append(
					"AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
							"AND a." + DatabaseUtils.addQuotes(db, "password") + " = ? " +
					"AND a.enabled = ? and a.trashed_date is null ");
		}
		pst = db.prepareStatement(sql.toString());
		int i = 0;
	
		if (userId > -1) {
			pst.setInt(++i, userId);
		} else {
			pst.setString(++i, username);
			pst.setString(++i, (password != null && !("").equals(password)) ?   encryptPassword(password) : "");
			pst.setBoolean(++i, true);
		}
		
		System.out.println("[GISA] User: buildRecord: "+pst.toString());

		rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			if (buildContact==true)
			{
				System.out.println("sto recuperando i dati di contact");
				this.contact = new Contact(rs);
				
			}
		}
		rs.close();
		pst.close();
		if (id == -1) {
			throw new SQLException("User record not found.");
		}
	}
	
	public int getId_tipo_gruppo_ruolo() {
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select id_gruppo_ruolo from get_gruppo_ruolo_utente(?)")	;
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				id_tipo_gruppo_ruolo = rs.getInt("id_gruppo_ruolo");
				}

		}
		catch(SQLException e) {e.printStackTrace();}
		finally {GestoreConnessioni.freeConnection(db);}
		return id_tipo_gruppo_ruolo;
	}
	public void setId_tipo_gruppo_ruolo(int id_tipo_gruppo_ruolo) {
		this.id_tipo_gruppo_ruolo = id_tipo_gruppo_ruolo;
	}
	public String getDescrizione_tipo_gruppo_ruolo() {
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement("select descrizione_gruppo_ruolo from get_gruppo_ruolo_utente(?)")	;
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				descrizione_tipo_gruppo_ruolo = rs.getString("descrizione_gruppo_ruolo");
				}

		}
		catch(SQLException e) {e.printStackTrace();}
		finally {GestoreConnessioni.freeConnection(db);}
		return descrizione_tipo_gruppo_ruolo;
	}
	public void setDescrizione_tipo_gruppo_ruolo(String descrizione_tipo_gruppo_ruolo) {
		this.descrizione_tipo_gruppo_ruolo = descrizione_tipo_gruppo_ruolo;
	}

}
