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

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class DpatCoefficiente extends GenericBean {

	private static final long serialVersionUID = -7913502548654198988L;
	
	private int id;
	private int id_indicatore;
	private double coefficiente;
	
	private int codiceInterno ; 
	private int codiceIndicatore ;
	
	
	
	public int getCodiceIndicatore() {
		return codiceIndicatore;
	}
	public void setCodiceIndicatore(int codiceIndicatore) {
		this.codiceIndicatore = codiceIndicatore;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_indicatore() {
		return id_indicatore;
	}
	public void setId_indicatore(int id_indicatore) {
		this.id_indicatore = id_indicatore;
	}
	public double getCoefficiente() {
		return coefficiente;
	}
	public void setCoefficiente(double coefficiente) {
		this.coefficiente = coefficiente;
	}
	
	
	public DpatCoefficiente (){}
	
	
	public DpatCoefficiente (Connection db ,int idIndicatore ){
		
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		try
		{
			pst = db.prepareStatement("select * from dpat_coefficiente where id_indicatore =? ");
			pst.setInt(1, idIndicatore);
			rs=pst.executeQuery();
			if (rs.next())
			{
				id_indicatore = rs.getInt("id_indicatore");
				coefficiente =rs.getDouble("coefficiente");
				codiceInterno=rs.getInt("codice_interno");
				codiceIndicatore = rs.getInt("codice_indicatore");
			}
		}
		catch(SQLException e)
		{
			
		}
				
		
	}
	
	
	public void insert(Connection db)
	
	{
		PreparedStatement pst = null ;
		String sql = "insert into dpat_coefficiente (id,id_indicatore,coefficiente,codice_interno,codice_indicatore)values(?,?,?,?,?)";
		try
		{
			
			id =DatabaseUtils.getNextInt(db, "dpat_coefficiente_", "id", 1);
			 pst = db.prepareStatement(sql);
			 pst.setInt(1, id);
			 pst.setInt(2, this.getId_indicatore());
			 pst.setDouble(3, coefficiente);
			 
			 
			 if (codiceInterno>0)
				 pst.setInt(4, codiceInterno);
			 else
			 {
				 codiceInterno=id ;
				 pst.setInt(4, codiceInterno);
			 }
				 
			 pst.setInt(5, codiceIndicatore);
			 
			 pst.execute();
		}
		catch(SQLException e)
		{
			
		}
				
		
	}
	public int getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	
	
	
	
}
