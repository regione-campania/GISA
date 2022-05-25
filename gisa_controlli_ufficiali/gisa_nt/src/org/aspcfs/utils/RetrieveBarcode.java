/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class RetrieveBarcode {
		
	public static int getGeneratedBarcode (String orgId, String ticketId){
		
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int value = -1;
		String select = "";
				
		try
		{
			db = GestoreConnessioni.getConnection()	;
			select = " select count(*) as recordcount from etichette_verbali_prelievo where org_id = ? and ticket_id = ? ";
			pst = db.prepareStatement(select);
			int i=0;
			pst.setInt(++i,Integer.parseInt(orgId));
			pst.setInt(++i,Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			while ( rs.next() )
			{
				value	= rs.getInt("recordcount")	;
									
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
			
		}
		
		return value;
		
	}
	
	
}
	
	   
