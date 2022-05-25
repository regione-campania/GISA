/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class DpatAttivitaCompetenze extends GenericBean {

	private static final long serialVersionUID = 8172469522264990994L;
	
	private int id;
	private int id_piano;
	private String description;
	private Boolean enabled;
	private DpatAttribuzioneCompetenzeIndicatoriList elencoIndicatori = new DpatAttribuzioneCompetenzeIndicatoriList();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_piano() {
		return id_piano;
	}
	public void setId_piano(int id_piano) {
		this.id_piano = id_piano;
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
	public DpatAttribuzioneCompetenzeIndicatoriList getElencoIndicatori() {
		return elencoIndicatori;
	}
	public void setElencoIndicatori(DpatAttribuzioneCompetenzeIndicatoriList elencoIndicatori) {
		this.elencoIndicatori = elencoIndicatori;
	}
	
	public DpatAttivitaCompetenze(ResultSet rs) throws SQLException
	{
		this.buildRecord(rs);
		
	}
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		description = rs.getString("description");
		id_piano = rs.getInt("id_piano");
	}
	
	
	
	


	

}
