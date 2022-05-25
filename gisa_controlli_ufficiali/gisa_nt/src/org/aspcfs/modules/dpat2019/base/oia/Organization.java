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
package org.aspcfs.modules.dpat2019.base.oia;

import java.sql.*;

import com.darkhorseventures.framework.beans.GenericBean;


public class Organization extends GenericBean {

	
	private static final long serialVersionUID = 4320567602597719160L;

	private int orgId = -1;
	private String name = "";
	private int siteId = -1;
	private int enteredBy = -1;
	protected int modifiedBy = -1;
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private int tipologia = 6 ;

	
	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public int getTipologia() {
		return tipologia;
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}

	public Organization() { }

	public Organization(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}






	public Organization(Connection db, int org_id) throws SQLException {


		if (org_id == -1) {
			throw new SQLException("Invalid Account");
		} 
		PreparedStatement pst = db.prepareStatement(
				"SELECT o.name,o.site_id,o.entered,o.modified,o.enteredby,o.modifiedby,o.org_id,o.tipologia " +
				"FROM organization o " +
				"WHERE o.org_id = ? " );
			
		pst.setInt(1, org_id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
		}
		rs.close();
		pst.close();
		if (orgId == -1) {
			throw new SQLException("Non Trovato Record");
		}

	}


	/**
	 *  Description of the Method
	 *
	 * @param  rs             Description of Parameter
	 * @throws  SQLException  Description of Exception
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {

		entered = rs.getTimestamp("entered");
		enteredBy = rs.getInt("enteredby");
		modified = rs.getTimestamp("modified");
		modifiedBy = rs.getInt("modifiedby");
		name = rs.getString("name");
		orgId = rs.getInt("org_id");
		tipologia = rs.getInt("tipologia");
		siteId = rs.getInt("site_id");

	}




}

