/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DwrGUC { 

	
	public static boolean verificaEsistenzaStabGiava(String numRegistrazione) throws SQLException
	{
		boolean esistente = false;
		Connection db = null ;
		try
		{
			
			db = it.us.web.db.DbUtil.getConnection();
			PreparedStatement pst = db.prepareStatement("select * from public.dbi_check_esistenza_utente_by_numreg(?)");
			pst.setString(1, numRegistrazione);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				esistente = rs.getBoolean(1);
			
			it.us.web.db.DbUtil.close(db);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			it.us.web.db.DbUtil.close(db);
		}
		return esistente;
	}
	
}


