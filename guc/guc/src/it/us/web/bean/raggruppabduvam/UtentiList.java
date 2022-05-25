/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.raggruppabduvam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class UtentiList extends Vector  {
	
	public static ArrayList<Utente> creaLista(Connection db, String endpoint){
		
		ArrayList<Utente> lista = new ArrayList<Utente>();
		
		String sql = "select * from lista_utenti(?)"; 
		try {
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, endpoint);

			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				Utente u = new Utente(rs);
				lista.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return lista;
		}
		
}
