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
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;

public class DpatAttribuzioneCompetenzeAttivitaList extends Vector implements SyncableList {

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSyncType(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void buildList(Connection db,int id_piano)
	{
		PreparedStatement pst = null;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_attivita where enabled and id_piano = ? order by ordinamento " ;
			pst=db.prepareStatement(sql);
			pst.setInt(1, id_piano);
			rs = pst.executeQuery();
			while (rs.next()) 
			{
			
				DpatAttivitaCompetenze attivita = new DpatAttivitaCompetenze(rs);
				attivita.getElencoIndicatori().buildList(db, attivita.getId());
				
				this.add(attivita);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	

	
	
}
