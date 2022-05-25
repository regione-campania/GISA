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
public class SchedaPiccioni extends GenericBean {

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
  private String stimaPopolazione = "";
  private String utilizzoSistemi = "";
 private String retiProtezione ="";
	private String cannonciniDissuasori ="";
	private String dissuasoriAghi ="";
	private String dissuasoriSonori ="";
	private String altro ="";
	private int enteredby = -1;
	private Timestamp entered = null;
	
	public SchedaPiccioni() {}
	
	public SchedaPiccioni(Connection db, int id) throws SQLException{
		PreparedStatement pst=db.prepareStatement("SELECT * FROM allevamenti_scheda_piccioni where id = ?");
		int i = 0;
		pst.setInt(++i, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public SchedaPiccioni(ActionContext context) {
		this.orgId = Integer.parseInt(context.getRequest().getParameter("orgIdAllevamento"));
		this.stimaPopolazione = context.getRequest().getParameter("stimaPopolazione");
		this.utilizzoSistemi = context.getRequest().getParameter("utilizzoSistemi");
		this.retiProtezione = context.getRequest().getParameter("retiProtezione");
		this.cannonciniDissuasori = context.getRequest().getParameter("cannonciniDissuasori");
		this.dissuasoriAghi = context.getRequest().getParameter("dissuasoriAghi");
		this.dissuasoriSonori = context.getRequest().getParameter("dissuasoriSonori");
		this.altro = context.getRequest().getParameter("altro");
}

	private void buildRecord(ResultSet rs) throws SQLException {
	this.id = rs.getInt("id");
	this.orgId = rs.getInt("org_id");
	this.stimaPopolazione= rs.getString("stima_popolazione");
	this.utilizzoSistemi= rs.getString("utilizzo_sistemi");
	this.retiProtezione= rs.getString("reti_protezione");
	this.cannonciniDissuasori= rs.getString("cannoncini_dissuasori");
	this.dissuasoriAghi= rs.getString("dissuasori_aghi");
	this.dissuasoriSonori= rs.getString("dissuasori_sonori");
	this.altro= rs.getString("altro");
	this.enteredby= rs.getInt("enteredby");
	this.entered= rs.getTimestamp("entered");

	}

	public void insert(Connection db, int idUtente) throws SQLException{
		
		PreparedStatement pst=db.prepareStatement("INSERT INTO allevamenti_scheda_piccioni(org_id, stima_popolazione, utilizzo_sistemi, reti_protezione, cannoncini_dissuasori, dissuasori_aghi, dissuasori_sonori, altro, enteredby) values (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
		int i = 0;
		pst.setInt(++i, orgId);
		pst.setString(++i, stimaPopolazione);
		pst.setString(++i, utilizzoSistemi);
		pst.setString(++i, retiProtezione);
		pst.setString(++i, cannonciniDissuasori);
		pst.setString(++i, dissuasoriAghi);
		pst.setString(++i, dissuasoriSonori);
		pst.setString(++i, altro);
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

	public String getStimaPopolazione() {
		return stimaPopolazione;
	}

	public void setStimaPopolazione(String stimaPopolazione) {
		this.stimaPopolazione = stimaPopolazione;
	}

	public String getUtilizzoSistemi() {
		return utilizzoSistemi;
	}

	public void setUtilizzoSistemi(String utilizzoSistemi) {
		this.utilizzoSistemi = utilizzoSistemi;
	}

	public String getRetiProtezione() {
		return retiProtezione;
	}

	public void setRetiProtezione(String retiProtezione) {
		this.retiProtezione = retiProtezione;
	}

	public String getCannonciniDissuasori() {
		return cannonciniDissuasori;
	}

	public void setCannonciniDissuasori(String cannonciniDissuasori) {
		this.cannonciniDissuasori = cannonciniDissuasori;
	}

	public String getDissuasoriAghi() {
		return dissuasoriAghi;
	}

	public void setDissuasoriAghi(String dissuasoriAghi) {
		this.dissuasoriAghi = dissuasoriAghi;
	}

	public String getDissuasoriSonori() {
		return dissuasoriSonori;
	}

	public void setDissuasoriSonori(String dissuasoriSonori) {
		this.dissuasoriSonori = dissuasoriSonori;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
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
	
	public void loadByAnagrafica(Connection db, int orgId) throws SQLException {
		PreparedStatement pst=db.prepareStatement("SELECT * FROM allevamenti_scheda_piccioni where org_id = ? and trashed_date is null");
		int i = 0;
		pst.setInt(++i, orgId);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	
}