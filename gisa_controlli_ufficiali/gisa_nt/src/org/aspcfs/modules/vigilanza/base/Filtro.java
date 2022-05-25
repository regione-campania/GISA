/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.vigilanza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Filtro {
	
	public Filtro(){}
	
	
	
	private int tipologiaOperatore ;
	private String sql_join ="";
	private String sql_campi_variabili ="" ;
	
public Filtro(Connection db,int orgId){
		
		try
		{
			
			ResultSet rs = null ;
			PreparedStatement pst = null;
			pst = db.prepareStatement("select * from lista_controlli_ufficiali where tipologia_operatore =(select tipologia from organization where org_id =?)");
			pst.setInt(1, orgId);
			rs=pst.executeQuery();
			if(rs.next()){
				tipologiaOperatore = rs.getInt("tipologia_operatore");
				sql_join = rs.getString("sql_join");
				sql_campi_variabili = rs.getString("sql_campi_variabili");
			}
			if(sql_join == null)
				sql_join= "" ;
			
			if(sql_campi_variabili == null)
				sql_campi_variabili= "" ;
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

public void queryRecord(Connection db,int tipologiaOperatore){
	
	try
	{
		
		ResultSet rs = null ;
		PreparedStatement pst = null;
		pst = db.prepareStatement("select * from lista_controlli_ufficiali where tipologia_operatore =?");
		pst.setInt(1, tipologiaOperatore);
		rs=pst.executeQuery();
		if(rs.next()){
			tipologiaOperatore = rs.getInt("tipologia_operatore");
			sql_join = rs.getString("sql_join");
			sql_campi_variabili = rs.getString("sql_campi_variabili");
		}
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	
}


	public int getTipologiaOperatore() {
		return tipologiaOperatore;
	}
	public void setTipologiaOperatore(int tipologiaOperatore) {
		this.tipologiaOperatore = tipologiaOperatore;
	}
	public String getSql_join() {
		if (sql_join!=null)
		return sql_join;
		return "" ;
	}
	public void setSql_join(String sql_join) {
		this.sql_join = sql_join;
	}
	public String getSql_campi_variabili() {
		if (sql_campi_variabili!=null)
		return sql_campi_variabili;
		return "" ;
	}
	public void setSql_campi_variabili(String sql_campi_variabili) {
		this.sql_campi_variabili = sql_campi_variabili;
	}
	
	

}
