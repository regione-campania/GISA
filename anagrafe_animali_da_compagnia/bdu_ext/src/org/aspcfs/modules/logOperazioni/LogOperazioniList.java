/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.logOperazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class LogOperazioniList extends Vector implements SyncableList {
	private static Logger log = Logger.getLogger(LogOperazioni.class);
	protected PagedListInfo pagedListInfo = null;
	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;
	protected String username;
	protected Timestamp datestart;
	protected Timestamp dateend;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Timestamp getDateend() {
		return dateend;
	}

	public void setDateend(Timestamp dateend) {
		this.dateend = dateend;
	}

	public Timestamp getDatestart() {
		return datestart;
	}

	public void setDatestart(Timestamp datestart) {
		this.datestart = datestart;
	}

	
	public void buildList(Connection db, String suff) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = queryListOperazioni(db, pst, suff);
		
	    while (rs.next()) {
	    	UserOperation o = this.getObject(rs);
	        this.add(o);
	    }
	
		rs.close();
		if (pst != null) {
			pst.close();
		}
		    
	}  
	
	public UserOperation getObject(ResultSet rs) throws SQLException {
		UserOperation o = new UserOperation();
		o.buildRecord(rs, o);
		return o;
	}
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) {
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    if (! "".equals(username)){
	    	sqlFilter.append(" and username ilike ? ");
	    }
	    if (datestart!=null){
	    	sqlFilter.append(" and data::date >= ? ");
	    }

	    if (dateend!=null){
	    	sqlFilter.append(" and data::date <= ? ");
	    }
	   
	}
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
	    int i = 0;
	    if (! "".equals(username)){
	    	pst.setString(++i, username.replaceAll("%", ""));
	    }
	    if (datestart!=null){
	    	pst.setTimestamp(++i, datestart);
	    }

	    if (dateend!=null){
	    	String time = "23:59:59";
	    	Timestamp timestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ").format(dateend).concat(time));
	    	pst.setTimestamp(++i, timestamp);
	    }
	    return i ;
	}
	
	public ResultSet queryListOperazioni(Connection db, PreparedStatement pst, String suff) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		// Need to build a base SQL statement for counting records

		sqlCount.append("SELECT COUNT(*) AS recordcount FROM "+suff+"_storico_operazioni_utenti_view where 1 = 1 ");
		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString()
						+ sqlFilter.toString());
				items = prepareFilter(pst);
				
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}
			pagedListInfo.setSortOrder(" DESC ");
			pagedListInfo.setColumnToSortBy(" data ");
			pagedListInfo.appendSqlTail(db, sqlOrder);

		} else {
			sqlOrder.append("ORDER BY data DESC");
		}

		sqlSelect.append("select * from "+suff+"_storico_operazioni_utenti_view where 1=1 ");
		
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		} 
		rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
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
	
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}
	
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public void setStageId(String tmp) {
		if (tmp != null) {
			this.stageId = Integer.parseInt(tmp);
		} else {
			this.stageId = -1;
		}
	}

	public Boolean getMinerOnly() {
		return minerOnly;
	}

	public void setMinerOnly(Boolean minerOnly) {
		this.minerOnly = minerOnly;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
