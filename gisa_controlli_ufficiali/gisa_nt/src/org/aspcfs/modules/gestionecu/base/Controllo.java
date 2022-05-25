/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONObject;

public class Controllo {
	
	private int idControllo = -1;
	
	public Controllo() {
		// TODO Auto-generated constructor stub
	}


	public Controllo(ResultSet rs) throws SQLException {
	}

	private void buildRecord(ResultSet rs) throws SQLException{
		this.idControllo = rs.getInt("id_controllo");
	}

	public void insert (Connection db, JSONObject jsonControllo) throws SQLException {
		String select = "select * from public.cu_insert_cu_globale(to_json(?::json))";  
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setString(1, jsonControllo.toString());
		rs = pst.executeQuery();
		while (rs.next()){
			this.idControllo = rs.getInt(1);
			}
	}

	public static final JSONObject getJson(Connection db, int idControllo) throws SQLException, ParseException {
		JSONObject jsonControllo = new JSONObject();
		String select = "select * from public.cu_dettaglio_cu_globale(?)";  
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setInt(1, idControllo);
		rs = pst.executeQuery();
		while (rs.next()){
			jsonControllo = new JSONObject(rs.getString(1));
			}
		return jsonControllo;
	}

	public int getIdControllo() {
		return idControllo;
	}


	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}

}
