/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.endpointconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class EndPoint {

	private int id = -1;
	private String nome ="";
	private String dataSourceMaster="";
	private String dataSourceSlave="";
	
	public static final int BDU= 1;
	public static final int GISA= 2;
	public static final int GISA_EXT= 3;
	public static final int VAM= 4;
	public static final int IMPORTATORI= 5;
	public static final int GUC= 6;
	public static final int DIGEMON= 7;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataSourceMaster() {
		return dataSourceMaster;
	}

	public void setDataSourceMaster(String dataSourceMaster) {
		this.dataSourceMaster = dataSourceMaster;
	}

	public String getDataSourceSlave() {
		return dataSourceSlave;
	}

	public void setDataSourceSlave(String dataSourceSlave) {
		this.dataSourceSlave = dataSourceSlave;
	}

	public EndPoint() {

	}
	public EndPoint(Connection db, int id) {
		 String sql = "select * from guc_endpoint where id = ?";
		 PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				buildRecord(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}

	public EndPoint(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	
	private void buildRecord(ResultSet rs) throws SQLException {
		this.id =  rs.getInt("id");
		this.nome = rs.getString("nome");
		this.dataSourceMaster = rs.getString("ds_master");
		this.dataSourceSlave = rs.getString("ds_slave");
	}
}
	







	

