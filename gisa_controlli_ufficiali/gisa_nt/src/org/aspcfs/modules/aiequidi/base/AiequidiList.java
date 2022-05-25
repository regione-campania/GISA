/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.aiequidi.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.aiequidi.actions.AIEquidi;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class AiequidiList extends Vector  {

	 protected PagedListInfo pagedListInfo = null;
	 private int anno ;
	 private String idCapo ;
	 private String numAccertamento ;
	 private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);

	
	 public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	

	public int getAnno() {
		return anno;
	}
	

	public String getNumAccertamento() {
		return numAccertamento;
	}

	public void setNumAccertamento(String numAccertamento) {
		this.numAccertamento = numAccertamento;
	}

	public void setAnno(String anno) {
		this.anno = Integer.parseInt(anno);
	}

	public String getIdCapo() {
		return idCapo;
	}

	public void setIdCapo(String idCapo) {
		this.idCapo = idCapo;
	}

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		    ResultSet rs = null;
		    int items = -1;

		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();

		    //Need to build a base SQL statement for counting records

		    sqlCount.append(
		            "SELECT  COUNT(o.*) AS recordcount " +
		            "FROM a_i_equidi o where 1=1 " );
		   
		    createFilter(db, sqlFilter);
		    
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString() +
		          sqlFilter.toString());
		     // UnionAudit(sqlFilter,db);
		      
		      items = prepareFilter(pst);
		      
		      
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();


		      //Determine column to sort by
		      pagedListInfo.setColumnToSortBy("o.anno desc,data_prelievo desc,data_accettazione desc");
		      
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		            
		      //Optimize SQL Server Paging
		      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		    } else {
		      sqlOrder.append("ORDER BY o.anno desc ");
		    }

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		     
		    	 sqlSelect.append(
		    		        " num_rapporto,num_accettazione,citta,ragione_sociale,codazie,nomin_utente,anno,num_capi_prelevati,num_acc_progressivo_campione as num_acc_progressivo_camp,data_fine_prova,to_char(data_fine_prova,'dd/MM/yyyy') as data_fine_prova_string,data_prelievo,to_char(data_prelievo,'dd/MM/yyyy') as data_prelievo_string,data_accettazione,to_char(data_accettazione,'dd/MM/yyyy') as data_accettazione_string,esito,risultato,id_capo  " +
		    		        "from a_i_equidi o where 1=1 " );
		    		       
		  
		    pst = db.prepareStatement(
		        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		    items = prepareFilter(pst);
		    
		    
		   
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, pst);
		    }
		    
		    
		    rs = DatabaseUtils.executeQuery(db, pst, log);
		    if (pagedListInfo != null) {
		      pagedListInfo.doManualOffset(db, rs);
		    }
		    return rs;
		  }
	 
	public void buildList(Connection db) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	    	Aiequidi  thisOrganization = new Aiequidi();
	    	thisOrganization.buildRecord(rs);
	        this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	   
	  }
	
	
	 protected int prepareFilter(PreparedStatement pst) throws SQLException {
		 int i = 0 ;

			if (idCapo != null && ! "".equalsIgnoreCase(idCapo)){
				
				pst.setString(++i, idCapo);
			}
			
			if (anno >0){
				
				pst.setInt(++i, anno);
			}
			
			if (numAccertamento != null && ! "".equalsIgnoreCase(numAccertamento.replace("%", "").trim())){
				
				pst.setInt(++i, Integer.parseInt((numAccertamento.replaceAll("%","").trim())));
			}
		 return i ;
	 
	 }
	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		
		if (idCapo != null && ! "".equalsIgnoreCase(idCapo.replace("%", "").trim())){
			
			sqlFilter.append(" and id_capo ilike ?");
		}
		
		if (anno >0){
			
			sqlFilter.append(" and anno = ?");
		}
		
		if (numAccertamento != null && ! "".equalsIgnoreCase(numAccertamento.replace("%", "").trim())){
			
			sqlFilter.append(" and num_accettazione = ?");
		}
	}

}

	

