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

public class DpatSezioneCompetenze extends GenericBean implements Cloneable {

	private static final long serialVersionUID = -9092380054888506648L;
	
	private int id;
	private String description;
	private Boolean enabled;
	private DpatAttribuzioneCompetenzePianiList elencoPiani = new DpatAttribuzioneCompetenzePianiList();
	
	//Parametri di configurazione/visualizzazione
	private String bgColor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public DpatAttribuzioneCompetenzePianiList getElencoPiani() {
		return elencoPiani;
	}
	public void setElencoPiani(DpatAttribuzioneCompetenzePianiList elencoPiani) {
		this.elencoPiani = elencoPiani;
	}
	
	
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	
	public DpatSezioneCompetenze(ResultSet rs) throws SQLException
	{
		this.buildRecord(rs);
		
	}
	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		description = rs.getString("description");
		bgColor = rs.getString("color");
	}
	
	
	public Object clone(){
		try{
			return super.clone();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
