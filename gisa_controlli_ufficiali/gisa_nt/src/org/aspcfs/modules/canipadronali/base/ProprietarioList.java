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
import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.IndirizzoProprietario;
import org.aspcfs.utils.web.PagedListInfo;

public class ProprietarioList extends Vector implements SyncableList {
	  private static Logger log = Logger.getLogger(ProprietarioList.class);

	  protected PagedListInfo pagedListInfo = null;
	  protected int siteId 				;
	  protected String cfProprietario 	;
	  
	  
	  
	  
	  

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getCfProprietario() {
		return cfProprietario;
	}

	public void setCfProprietario(String cfProprietario) {
		this.cfProprietario = cfProprietario;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

	
	  
	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		  //andAudit( sqlFilter );
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    if (! "".equals(cfProprietario))
	    {
	    	sqlFilter.append(" and o.codice_fiscale_rappresentante ilike ? ");
	    }
	   
	}
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
	    int i = 0;
	    
	    if (! "".equals(cfProprietario))
	    {
	    	pst.setString(++i, "%"+cfProprietario+"%");
	    }
	   
	    return i ;
	    }
	   
	public ResultSet queryListCalcoloPunteggio(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	   String query_sel =  	" select sum(t.punteggio)as punteggio,o.name,o.codice_fiscale_rappresentante, o.nome_rappresentante ,o.cognome_rappresentante," +
	   						" o.data_nascita_rappresentante,o.luogo_nascita_rappresentante " +
		   					" from ticket t  " +
		   					" join organization o on ( t.org_id = o.org_id) " +
		   					" where t.trashed_date is null and t.tipologia = 3 and o.tipologia =255 " ;
	  String group = " group by o.codice_fiscale_rappresentante, o.name, o.nome_rappresentante ,o.cognome_rappresentante,o.data_nascita_rappresentante,o.luogo_nascita_rappresentante " ;
	   
	
	  
	  if (! "".equals(cfProprietario))
	    {
		  query_sel+= " and o.codice_fiscale_rappresentante ilike ? ";
	    }
	   
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	            "SELECT COUNT(*) AS recordcount " +
	            "FROM ("+query_sel+ group +")a where 1 = 1 " );
	    
	    
	    
	    
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
	      pagedListInfo.setColumnToSortBy(" o.name ");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append(" ORDER BY o.name ");
	    }

	     sqlSelect.append(query_sel + group);
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
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	   
	    ResultSet rs = queryListCalcoloPunteggio(db, pst);
	    while (rs.next()) {
	      Proprietario thisOrganization = this.getObject(rs);
	    
	        this.add(thisOrganization);
	      
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    
	  }
	  
	
	
	  public Proprietario getObject(ResultSet rs) throws SQLException {
		    Proprietario thisOrganization = new Proprietario();
		    thisOrganization.buildRecord(rs, thisOrganization);
		    return thisOrganization;
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

	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}

	  
	  

		
}
