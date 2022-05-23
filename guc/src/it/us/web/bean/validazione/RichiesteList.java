/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.validazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class RichiesteList extends Vector  {
	
	public static ArrayList<Richiesta> creaLista(Connection db){
		
		ArrayList<Richiesta> lista = new ArrayList<Richiesta>();
		
		String sql = "select * from spid.get_lista_richieste(null)"; 
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				Richiesta ric = new Richiesta(rs);
				lista.add(ric);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return lista;
		}
		
}
