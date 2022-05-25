/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeadeguamenti.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *      Exp $
 */
public class SchedaBiogas extends GenericBean {

	private static final long serialVersionUID = -6527485205549561674L;

	private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.Organization.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
    }
  }	
  
  protected double YTD = 0;

  private int id = -1;
  private int orgId = -1;
  private String impiantoBiogas = "";
  private String tipologiaBiogas = "";
  private int enteredby = -1;
  private Timestamp entered = null;
	
	public SchedaBiogas() {}
	
	public SchedaBiogas(Connection db, int id) throws SQLException{
		PreparedStatement pst=db.prepareStatement("SELECT * FROM adeguamento_scheda_biogas where id = ?");
		int i = 0;
		pst.setInt(++i, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public SchedaBiogas(ActionContext context) {
		try { this.orgId = Integer.parseInt(context.getRequest().getParameter("orgIdBiogas")); } catch (Exception e) {}
		try { this.altId = Integer.parseInt(context.getRequest().getParameter("altIdBiogas")); } catch (Exception e) {}
		this.impiantoBiogas = context.getRequest().getParameter("impiantoBiogas");
		this.tipologiaBiogas = context.getRequest().getParameter("tipologiaBiogas");
}

	private void buildRecord(ResultSet rs) throws SQLException {
	this.id = rs.getInt("id");
	this.orgId = rs.getInt("org_id");
	this.altId = rs.getInt("alt_id");
	this.impiantoBiogas= rs.getString("impianto_biogas");
	this.tipologiaBiogas = rs.getString("tipologia_biogas");
	this.enteredby= rs.getInt("enteredby");
	this.entered= rs.getTimestamp("entered");

	}

	public void insert(Connection db, int idUtente) throws SQLException{
		
		PreparedStatement pst=db.prepareStatement("INSERT INTO adeguamento_scheda_biogas(org_id, alt_id, impianto_biogas, tipologia_biogas, enteredby) values (?, ?, ?, ?, ?) returning id as id_inserito");
		int i = 0;
		pst.setInt(++i, orgId);
		pst.setInt(++i, altId);
		pst.setString(++i, impiantoBiogas);
		pst.setString(++i, tipologiaBiogas);
		pst.setInt(++i, idUtente);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			this.id = rs.getInt("id_inserito");
		
	}

	public double getYTD() {
		return YTD;
	}

	public void setYTD(double yTD) {
		YTD = yTD;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getEnteredby() {
		return enteredby;
	}

	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public String getImpiantoBiogas() {
		return impiantoBiogas;
	}

	public void setImpiantoBiogas(String impiantoBiogas) {
		this.impiantoBiogas = impiantoBiogas;
	}

	public String getTipologiaBiogas() {
		return tipologiaBiogas;
	}

	public void setTipologiaBiogas(String tipologiaBiogas) {
		this.tipologiaBiogas = tipologiaBiogas;
	}

	public void loadByAnagrafica(Connection db, int orgId, int altId) throws SQLException {
		PreparedStatement pst=db.prepareStatement("SELECT * FROM adeguamento_scheda_biogas where ((org_id > 0 and org_id = ?) or (alt_id > 0 and alt_id = ?)) and trashed_date is null");
		int i = 0;
		pst.setInt(++i, orgId);
		pst.setInt(++i, altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
}