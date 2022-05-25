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


public class Operazione {

	private static final long serialVersionUID = 2L;
	
	private int id;
	private String nome;
	
	public static final int GETRUOLIUTENTE= 1;
	public static final int INSERTUTENTE= 2;
	public static final int MODIFYPROFILOUTENTE= 3;
	public static final int ROLLBACKPROFILOUTENTE= 4;
	public static final int MODIFYANAGRAFICAUTENTE= 5;
	public static final int DISABLEUTENTE= 6;
	public static final int ENABLEUTENTE= 7;
	public static final int CHECKENABLEUTENTE= 8;
	public static final int CHECKESISTENZAUTENTE= 9;
	public static final int CHECKESISTENZAUTENTEBYSTRUTTURA= 10;
	public static final int GETCANILIUTENTEBDU= 11;
	public static final int GETLISTAPROVINCE= 12;
	public static final int VERIFICAUTENTEMODIFICABILE= 13;
	public static final int ACCREDITASUAP= 14;
	public static final int MODIFYUTENTE= 15;
	public static final int GETIMPORTATORIUTENTE= 16;
	public static final int GETCLINICHEUTENTE= 17;
	public static final int VERIFICAPASSWORDPRECEDENTE = 18;
	public static final int CAMBIOPASSWORD = 19;
	public static final int MODIFICACODICEFISCALE= 20;
	public static final int MODIFICAEMAIL= 25;
	public static final int INSERTRUOLO= 21;
	public static final int GET_GESTORI_ACQUE= 23;
	public static final int GET_COMUNI= 24;

	
	public Operazione() {
		 
	}
	
	public Operazione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public Operazione(Connection db, int id) {
		 String sql = "select * from guc_operazioni where id = ?";
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
	
	private void buildRecord(ResultSet rs) throws SQLException {

		this.id =  rs.getInt("id");
		this.nome = rs.getString("nome");
		}
	

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



}
