/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.DatabaseUtils;

public class MacroareaAnomalieValutazione implements Serializable {

	private int id ;
	private String descrizione ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void insert(Connection db,int idScheda)
	{
		try
		{


			String insert = "insert into iuv_campioni_valutazione_comportamentale_anomalie (id_iuv_valutazione,id_anomalia) values (?,?)";
			PreparedStatement pst = db.prepareStatement(insert);
			int i = 0 ;
			pst.setInt(++i, idScheda);
			pst.setInt(++i, id);
			pst.execute();


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void update(Connection db,int idScheda)
	{
			
			insert(db, idScheda);


		
		
	}

	public MacroareaAnomalieValutazione(){}
	public MacroareaAnomalieValutazione(int idAnomalie){ id = idAnomalie;}
	public MacroareaAnomalieValutazione(ResultSet rs) throws SQLException{

		this.buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException
	{

		id = rs.getInt("code");
		descrizione = rs.getString("description");


	}
}
