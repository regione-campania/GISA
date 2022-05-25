/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.web.PagedListInfo;

public class ReferenteList extends Vector implements SyncableList {


	protected PagedListInfo pagedListInfo = null;





	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

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
	
	
	public ResultSet queryList(Connection db,PreparedStatement pst)
	{
		ResultSet rs = null ;
		
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		
		sqlCount.append("select count(r.*) from referenti_riunioni r  "+
				"where r.trashed_Date is null ");
		

		try
		{
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
			// UnionAudit(sqlFilter,db);
			


			rs = pst.executeQuery(); 
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();


		
			pagedListInfo.appendSqlTail(db, sqlOrder);

		} else {
			sqlOrder.append("");
		}	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select * from referenti_riunioni  ");
		
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		}
		catch(SQLException e)
		{
			
		}
		
		return rs ;
		
		
	}
	
	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			Referente referente = this.getObject(rs);
			
			
			
			this.add(referente);		    
		}

		
	}
	
	
	public Referente getObject(ResultSet rs) throws SQLException {
		  
		Referente st = new Referente() ;
		st.setId(rs.getInt("id"));
		st.setNominativo(rs.getString("nominativo"));
		st.setUserId(rs.getInt("user_id_access"));
		return st ;
	}

}