/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUtil {
	public static void gestioneLogLogin ( Connection conn, String username, String codiceFiscale, boolean accessoSpid, String endPoint) throws SQLException{
		
		PreparedStatement pst = conn.prepareStatement("select * from spid.insert_sca_storico_login(?, ?, ?, ?)");
		pst.setString(1, username);
		pst.setString(2, codiceFiscale);
		pst.setBoolean(3, accessoSpid);
		pst.setString(4, endPoint);
		pst.executeQuery();
		  
	  }
	
public static String gestioneLastLogin ( Connection conn, String username, String codiceFiscale, boolean accessoSpid, String endPoint) throws SQLException{
		
		String esito = "";
		PreparedStatement pst = conn.prepareStatement("select * from spid.check_sca_last_login(?, ?, ?, ?)");
		pst.setString(1, username);
		pst.setString(2, codiceFiscale);
		pst.setBoolean(3, accessoSpid);
		pst.setString(4, endPoint);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			esito = rs.getString(1);
		
		return esito;
		  
	  }
}
