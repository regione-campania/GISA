/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.abusivismi.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import ext.aspcfs.modules.apicolture.actions.Movimentazioni;

public class VariazioneUbicazioneList extends  Vector implements SyncableList {
	
	private int idAzienda;
	private int idApiario;
	  protected PagedListInfo pagedListInfo = null;

	   
	   public void setPagedListInfo(PagedListInfo tmp) {
	     this.pagedListInfo = tmp;
	   }
	
	
	public int getIdAzienda() {
		return idAzienda;
	}


	public void setIdAzienda(int idAzienda) {
		this.idAzienda = idAzienda;
	}
	
	
	public void setIdAzienda(String idAzienda) {
		if(idAzienda!=null && !"".equals(idAzienda))
		this.idAzienda = Integer.parseInt(idAzienda);
	}

	
	public void setIdApiario(String idApiario) {

		if(idApiario!=null && !"".equals(idApiario))
			this.idApiario = Integer.parseInt(idApiario);
	}
	
	 public int getIdApiario() {
		return idApiario;
	}


	public void setIdApiario(int idApiario) {
		this.idApiario = idApiario;
	}


	public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) {
		      StabilimentoVariazioneUbicazione thisMov = new StabilimentoVariazioneUbicazione();
		      thisMov.buildRecord(db,rs);
		       this.add(thisMov);
		      
		    }
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		   
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
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM apicoltura_apiari_variazioni_ubicazione " +
	        "WHERE 1=1  ");

	    createFilter(db, sqlFilter);

	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	  

	      //Determine column to sort by
	      sqlOrder.append("ORDER BY data_assegnazione_ubicazione ");
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY data_assegnazione_ubicazione ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" * FROM apicoltura_apiari_variazioni_ubicazione where 1=1 ");
	       
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }


	  /**
	   *  Builds a base SQL where statement for filtering records to be used by
	   *  sqlSelect and sqlCount
	   *
	   * @param  sqlFilter  Description of Parameter
	   * @param  db         Description of the Parameter
	   * @since             1.2
	   */
	  protected void createFilter(Connection db, StringBuffer sqlFilter) {
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    
	    if (idAzienda>0)
	    	sqlFilter.append(" and id_apicoltura_imprese =? ");
	    
	    if (idApiario>0)
	    	sqlFilter.append(" and id_apicoltura_apiario =? ");
	  
	  }
	  
	  protected int prepareFilter(PreparedStatement pst) throws SQLException {
		    int i = 0;
		    
		    if (idAzienda>0)
		    	pst.setInt(++i, idAzienda);
		    if (idApiario>0)
		    	pst.setInt(++i, idApiario);
		    
		    return i;
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
	

}
