/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.audit.base;

import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

public class AuditList extends ArrayList {
	
	//campi
	private PagedListInfo pagedListInfo = null;
	private int id = -1;
	private int orgId = -1;
	
	//metodi get e set
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
private Timestamp dataProssimoControllo=null;
	
	
	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}
	public void setDataProssimoControllo(String tmp) {
	    this.dataProssimoControllo = DatabaseUtils.parseDateToTimestamp(tmp);
	}
private Integer categoria=null;
	
	
	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}
	//costruttore
	public AuditList() { }
    
	public AuditList(Connection db) throws SQLException { }
	
	
	/*public void buildList(Connection db) throws SQLException {
      PreparedStatement pst = null;
	  ResultSet rs = queryList(db, pst);
	  while (rs.next()) {
		Audit thisAudit = this.getObject(rs);
		this.add(thisAudit);
	  }
      rs.close();
	  if (pst != null) {
		pst.close();
	  }
    }*/
	
	public void buildListControlli(Connection db, int orgIdAudit, String idTicket) throws SQLException {
	      PreparedStatement pst = null;
		  ResultSet rs = queryListControlli(db, pst, orgIdAudit, idTicket);
		  while (rs.next()) {
			Audit thisAudit = this.getObject(rs);
			this.add(thisAudit);
		  }
		  if(this.size()!=0)
		  {
		  Audit a =(Audit) this.remove(0);
		  a.setAggiornaCategoria(true);
		  this.add(0,a);
		  }
		  rs.close();
		  if (pst != null) {
			pst.close();
		  }
	    }

	public ResultSet queryListControlli(Connection db, PreparedStatement pst, int idAudit, String idTicket) throws SQLException {
		  ResultSet rs = null;
		  int items = -1;

		  StringBuffer sqlSelect = new StringBuffer();
		  StringBuffer sqlCount = new StringBuffer();
		  StringBuffer sqlFilter = new StringBuffer();
		  StringBuffer sqlOrder = new StringBuffer();
		  
		  //Need to build a base SQL statement for counting records
		  sqlCount.append("SELECT COUNT(*) AS recordcount FROM audit a WHERE id > -1 and a.org_id = "+idAudit+" and a.id_controllo = '"+idTicket+"' and a.trashed_date is null");
		  createFilter(sqlFilter, db);
		  if (pagedListInfo != null) {
		    //Get the total number of records matching filter
		    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
		    items = prepareFilter(pst);
		    rs = pst.executeQuery();
		    if (rs.next()) {
		      int maxRecords = rs.getInt("recordcount");
		      pagedListInfo.setMaxRecords(maxRecords);
		    }
		    
		    rs.close();
		    pst.close();
		    //Determine column to sort by
		    pagedListInfo.setDefaultSort("a.data_1", null);
		    //pagedListInfo.appendSqlTail(db, sqlOrder);
		    sqlOrder.append("ORDER BY numero_registrazione DESC LIMIT 10 OFFSET 0");
		    	    
		  } else {
		    sqlOrder.append("ORDER BY numero_registrazione DESC ");
		  }
		  //Need to build a base SQL statement for returning records
		  if (pagedListInfo != null) {
		    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		  } else {
		    sqlSelect.append("SELECT ");
		  }
		 
		  sqlSelect.append("a.* FROM audit a WHERE id > -1 and a.org_id = "+idAudit+" and a.id_controllo = '"+idTicket+"' and a.trashed_date is null ");
		  pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
	
	/*public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	  ResultSet rs = null;
	  int items = -1;

	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  
	  //Need to build a base SQL statement for counting records
	  sqlCount.append("SELECT COUNT(*) AS recordcount FROM audit a WHERE id > -1 ");
	  createFilter(sqlFilter, db);
	  if (pagedListInfo != null) {
	    //Get the total number of records matching filter
	    pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	      int maxRecords = rs.getInt("recordcount");
	      pagedListInfo.setMaxRecords(maxRecords);
	    }
	    
	    rs.close();
	    pst.close();
	    //Determine column to sort by
	    pagedListInfo.setDefaultSort("a.data_1", null);
	    //pagedListInfo.appendSqlTail(db, sqlOrder);
	    sqlOrder.append("ORDER BY numero_registrazione DESC LIMIT 10 OFFSET 0");
	    	    
	  } else {
	    sqlOrder.append("ORDER BY numero_registrazione DESC ");
	  }
	  //Need to build a base SQL statement for returning records
	  if (pagedListInfo != null) {
	    pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	  } else {
	    sqlSelect.append("SELECT ");
	  }
	 
	  sqlSelect.append("a.* FROM audit a WHERE id > -1 ");
	  pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
	*/
	private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
	  if (sqlFilter == null) {
		sqlFilter = new StringBuffer();
	  }
      if (id > -1) {
	    sqlFilter.append("AND a.id = ? ");
	  }
	  if (orgId > -1) {
		sqlFilter.append("AND a.org_id = ? ");
	  }
	}

	private int prepareFilter(PreparedStatement pst) throws SQLException {
	  int i = 0;
      if (id > -1) {
	    pst.setInt(++i, id);
	  }
	  if (orgId > -1) {
	    pst.setInt(++i, orgId);
	  }
	  return i;
	}
	
	public Audit getObject(ResultSet rs) throws SQLException {
	  Audit thisAsset = buildRecord( rs );
	  return thisAsset;
	}
	
	private Audit buildRecord(ResultSet rs) throws SQLException
	{
		Audit a = new Audit();
		a.setComponentiGruppo( rs.getString("componenti_gruppo") );
		a.setData1( rs.getTimestamp("data_1") );
		a.setData2( rs.getTimestamp("data_2") );
		a.setId( rs.getInt("id") );
		a.setNote( rs.getString("note") );
		a.setOrgId( rs.getInt("org_id") );
		a.setNumeroRegistrazione( rs.getString("numero_registrazione") );
		a.setLivelloRischio( rs.getInt("livello_rischio") );
		a.setLivelloRischioFinale( a.getLivelloRischio() );
		a.setTipoChecklist(rs.getInt("tipo_check"));
		a.setCategoria(rs.getInt("categoria"));
		a.setDataProssimoControllo(rs.getTimestamp("data_prossimo_controllo"));
		return a;
	}
}
