/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.login.beans.UserBean;

public class IndirizzoProprietario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id				;
	private int orgId 			;
	private String 	citta 		;
	private String 	provincia	;
	private String 	nazione		;
	private String 	via			;
	private String 	cap			;
	private int inserito_da 	;
	private int modificato_da 	;
	
	
	public int getInserito_da() {
		return inserito_da;
	}
	public void setInserito_da(int inserito_da) {
		this.inserito_da = inserito_da;
	}
	public int getModificato_da() {
		return modificato_da;
	}
	public void setModificato_da(int modificato_da) {
		this.modificato_da = modificato_da;
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
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public void insert(Connection db,UserBean user) throws SQLException
	{
		String insert = "insert into organization_address (org_id , city,addrline1,state ,postalcode,entered,modified,enteredby,modifiedby,address_id) values (?,?,?,?,?,current_date,current_date,?,?,?)" ;
		PreparedStatement pst = db.prepareStatement(insert);
		
		
		int livello=1 ;
		
		if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
			livello=2;
		int address_id =  DatabaseUtils.getNextInt( db, "organization_address","address_id",livello);
		pst.setInt(1, orgId) ;
		pst.setString(2,citta);
		pst.setString(3, via);
		pst.setString(4, provincia);
		pst.setString(5,cap);
		pst.setInt(6, inserito_da);
		pst.setInt(7, modificato_da);
		pst.setInt(8, address_id);
		pst.execute();
		
	}
	
	public void update(Connection db) throws SQLException
	{
		String insert = "update organization_address set city = ? , postalcode = ? , addrline1=?,state = ?,modified=current_date,modifiedby=? where address_id = ? " ;
		PreparedStatement pst = db.prepareStatement(insert);
		
		pst.setString(1,citta);
		pst.setString(2,cap);
		pst.setString(3, via);
		pst.setString(4, provincia);
		pst.setInt(5, modificato_da) ;
		pst.setInt(6, id) ;
		pst.execute();
		
	}
	
}
