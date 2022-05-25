/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcqueReteMotiviNc {


	
	private int id = -1;
	private String description = null;
	private boolean capitolo;
		
	public AcqueReteMotiviNc() {
		// TODO Auto-generated constructor stub
	}
	
	public AcqueReteMotiviNc(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public AcqueReteMotiviNc(Connection db, String id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from acquedirete_campioni_nonconformita where id = ?");
		pst.setInt(1, Integer.parseInt(id));
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			buildRecord(rs);
		}
	}

	public void buildRecord(ResultSet rs) throws SQLException{
		id = rs.getInt("id");
		description = rs.getString("description");
		capitolo = rs.getBoolean("capitolo");
	}
	
	
	public static ArrayList<AcqueReteMotiviNc> getElencoMotiviNc(Connection db) throws SQLException {
			ArrayList<AcqueReteMotiviNc> lista = new  ArrayList<AcqueReteMotiviNc>();
		 		
				PreparedStatement pst = db.prepareStatement("select * from acquedirete_campioni_nonconformita order by level asc");
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					AcqueReteMotiviNc motivo = new AcqueReteMotiviNc(rs);
					lista.add(motivo);		
				}
			
			return lista;
	}

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

	public boolean isCapitolo() {
		return capitolo;
	}

	public void setCapitolo(boolean capitolo) {
		this.capitolo = capitolo;
	}
	

}
