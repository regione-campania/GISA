/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base.oia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.daffodilwoods.daffodildb.server.serversystem.PreparedStatement;

public class FileItem  extends com.zeroio.iteam.base.FileItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idControllo ;


	public int getIdControllo() {
		return idControllo;
	}

	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}
	public FileItem ()
	{
		
	}

	public FileItem (Connection db, int idControllo)
	{

		String sel = "select * from project_files where id_controllo = ? and trashed_date is null  ";
		try
		{
			java.sql.PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, idControllo);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				super.setId(rs.getInt("item_id"));
				super.setLinkItemId(rs.getInt("link_item_id"));
				super.setFolderId(rs.getInt("folder_id"));
				super.setClientFilename(rs.getString("client_filename"));
				super.setFilename(rs.getString("filename"));
				super.setSubject(rs.getString("subject"));
				super.setSize(rs.getInt("size"));
				super.setVersion(rs.getDouble("version"));
				super.setEnabled(rs.getBoolean("enabled"));
				super.setDownloads(rs.getInt("downloads"));
				super.setModified(rs.getTimestamp("modified"));
				super.setEntered(rs.getTimestamp("entered"));
				super.setEnteredBy(rs.getInt("enteredby"));
				super.setModifiedBy(rs.getInt("modifiedby"));
				super.setDefaultFile(rs.getBoolean("default_file"));
				super.setAllowPortalAccess(rs.getBoolean("allow_portal_access"));
				this.setIdControllo(rs.getInt("id_controllo"));
				
			}


		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
	}
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

	public boolean insert(Connection db)
	{
		try
		{

			String insert = "INSERT INTO project_files(" +
			"link_module_id, link_item_id,client_filename," + 
			"filename, subject, size, version, enabled, downloads, entered," + 
			"enteredby, modified, modifiedby, default_file, allow_portal_access," + 
			"id_controllo)" +
			"VALUES ( ?, ?,  ?," + 
			"?, ?, ?, ?, ?, ?, current_timestamp," +
			"?, current_timestamp, ?, ?, ?, " +
			" ?)";

			java.sql.PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(1, super.getLinkItemId());
			pst.setInt(2, super.getId());
			
			pst.setString(3, this.getClientFilename());
			pst.setString(4, this.getFilename());
			pst.setString(5, this.getSubject());
			pst.setInt(6, this.getSize());
			pst.setDouble(7, this.getVersion());
			pst.setBoolean(8, this.getEnabled());
			pst.setInt(9, this.getDownloads());
			pst.setInt(10, this.getEnteredBy());
			pst.setInt(11, this.getModifiedBy());
			pst.setBoolean(12, this.getDefaultFile());
			pst.setBoolean(13, this.getAllowPortalAccess());
			pst.setInt(14, this.getIdControllo());
			pst.execute();


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true ; 

	}


}
