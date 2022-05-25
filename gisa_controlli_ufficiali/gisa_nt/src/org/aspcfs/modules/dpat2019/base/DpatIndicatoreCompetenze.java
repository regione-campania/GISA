/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.beans.GenericBean;

public class DpatIndicatoreCompetenze extends GenericBean {

	private static final long serialVersionUID = -3712067931478757670L;
	private int id;
	private int id_attivita;
	private String description;
	private Boolean enabled;
	private String note="";
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_attivita() {
		return id_attivita;
	}
	public void setId_attivita(int id_attivita) {
		this.id_attivita = id_attivita;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public DpatIndicatoreCompetenze(ResultSet rs) throws SQLException
	{
		this.buildRecord(rs);
		
	}
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		description = rs.getString("description");
		id_attivita = rs.getInt("id_attivita");
		note =  rs.getString("note");
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
