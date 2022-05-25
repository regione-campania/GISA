/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.documents.base;

import java.sql.Connection;
import java.sql.SQLException;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;

public class FileItem  extends com.zeroio.iteam.base.FileItem{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileItem (Connection db , int id , int type, int val) throws SQLException
	{
		super(db,id,type,val);
	}
	
	public void delete(Connection db,int modifiedby)
	{
		try
		{
			java.sql.PreparedStatement pst = db.prepareStatement("update project_files set trashed_date = current_date ,modifiedby = ? where item_id = ?");
			pst.setInt(1, modifiedby);
			pst.setInt(2, super.getId());
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

}
