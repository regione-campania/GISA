/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class CaneList extends Vector implements SyncableList {
	  private static Logger log = Logger.getLogger(org.aspcfs.modules.canipadronali.base.CaneList.class);

	  protected PagedListInfo pagedListInfo = null;
	  protected int siteId ;
	  protected String mc ;
	  
	  
	  

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub
		
	}

	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub
		
	}
	  
	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		  //andAudit( sqlFilter );
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    if (! "".equals(mc))
	    {
	    	sqlFilter.append(" and a.serial_number = ? ");
	    }
	    if (siteId != -1)
		{
	    	sqlFilter.append(" where o.site_id = ? ") ;
		}
	}
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
	    int i = 0;
	    
	    if (! "".equals(mc))
	    {
	    	pst.setString(++i, getMc());
	    }
	    if (siteId != -1)
		{
	    	pst.setInt(++i, getSiteId());
		}	
	    return i ;
	    }
	   
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	   String query_sel =  ApplicationProperties.getProperty("GET_CANE_BY_MC");
	  
	   if (! "".equals(mc))
	    {
		   query_sel+= " and a.serial_number = ? " ;
	    }
	    if (siteId != -1)
		{
	    	 query_sel+= " and o.site_id = ? " ;
		}	
	   
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	            "SELECT COUNT(*) AS recordcount " +
	            "FROM ("+query_sel+")a where 1 = 1 " );
	    
	    createFilter(db, sqlFilter);
	    
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() 
	         );
	     // UnionAudit(sqlFilter,db);
	      
	      items = prepareFilter(pst);
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() +
	            sqlFilter.toString() +
	            "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy("o.name");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	     sqlSelect.append(query_sel);
	      pst = db.prepareStatement(
	        sqlSelect.toString() +  sqlOrder.toString());
	   
	    
	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    prepareFilter(pst);
	    rs = DatabaseUtils.executeQuery(db, pst, log);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
	
	public void set_asset_cu(Connection db,int orgId ,int idControllo)
	{
		try
		{
			String update = "update asset set idControllo=? where account_id = ? " ;
			
			PreparedStatement pst = db.prepareStatement(update);
			pst.setInt(1, idControllo);
			pst.setInt(2, orgId);
			
			pst.executeUpdate();
		
		
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	   
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	      Cane thisOrganization = this.getObject(rs);
	    
	        this.add(thisOrganization);
	      
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	  }
	  
	
	
	  public Cane getObject(ResultSet rs) throws SQLException {
		    Cane thisOrganization = new Cane();
		    thisOrganization.buildRecord(rs, thisOrganization);
		    return thisOrganization;
		  }

	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
