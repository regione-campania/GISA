/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.beans.GenericBean;

public class Referente extends GenericBean {
	
	private int id ;
	private String nominativo ;
	private int userId ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Referente(){}
	public Referente(Connection db,int id){ queryRecord(db, id); }
	
	public void queryRecord(Connection db , int id)
	{
		try
		{
			String select = "select id as id_referente ,nominativo as nominativo_referente,user_id_access from referenti_riunioni where id = ?";
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				loadResultSet(rs);
			}
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	public void loadResultSet(ResultSet rs) throws SQLException {
		  
		this.setId(rs.getInt("id_referente"));
		this.setNominativo(rs.getString("nominativo_referente"));
		this.setUserId(rs.getInt("user_id_access"));
		
	}
	
}
