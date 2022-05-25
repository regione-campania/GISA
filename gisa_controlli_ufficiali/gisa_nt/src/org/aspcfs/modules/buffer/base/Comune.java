/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.buffer.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Comune {
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
	public ArrayList<Comune> getComuni (Connection db)
	{
		ArrayList<Comune> lista = new ArrayList<Comune>();
		String sql = "select * from comuni1 where cod_regione='15' and notused is null order by nome ";
		try
		{	
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				Comune c = new Comune ();
				c.setId(rs.getInt("id"));
				c.setDescrizione(rs.getString("nome"));
				lista.add(c);
				
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return lista ;
		
	}
	

}
