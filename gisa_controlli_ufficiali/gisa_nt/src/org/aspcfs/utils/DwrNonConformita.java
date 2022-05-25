/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.directwebremoting.extend.LoginRequiredException;

public class DwrNonConformita {
	
	
	public PunteggiNonConformita get_punteggio_non_conformita(int tipoControllo,String dataControllo)
	{
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		String sql="select * from get_punteggio_non_conformita(?,?)";
		Connection db = null;
		PunteggiNonConformita punti = new PunteggiNonConformita();
		try
		{
			db = GestoreConnessioni.getConnection();
			Timestamp dataControlloTime = new Timestamp(sdf.parse(dataControllo).getTime());
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, tipoControllo);
			pst.setTimestamp(2, dataControlloTime);
			ResultSet rs= pst.executeQuery();
			if(rs.next())
			{
				punti.setPuntiformali( rs.getInt("puntiformali"));
				punti.setPuntisignificativi(rs.getInt("puntisignificativi"));
				punti.setPuntigravi( rs.getInt("puntigravi"));
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return punti;
 
	}

}
