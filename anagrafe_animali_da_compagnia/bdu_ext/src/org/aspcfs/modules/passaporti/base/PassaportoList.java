/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.passaporti.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.passaporti.base.Passaporto;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class PassaportoList extends ArrayList {
	

	
	protected PagedListInfo pagedListInfo = null;
	private int idAsl = -1;
	private String nrPassaporto = null;
	private Boolean assegnato;
	private Boolean abilitato;
	private Integer idUos;
	private boolean checkOnlyEnabled = false;
	

	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public String getNrPassaporto() {
		return nrPassaporto;
	}
	public void setNrPassaporto(String nrPassaporto) {
		this.nrPassaporto = nrPassaporto;
	}
	public Boolean isAssegnato() {
		return assegnato;
	}
	public void setAssegnato(Boolean assegnato) {
		this.assegnato = assegnato;
	}
	public Boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	public Integer getIdUos() {
		return idUos;
	}
	public void setIdUos(Integer idUos) {
		this.idUos = idUos;
	}
	public boolean isCheckOnlyEnabled() {
		return checkOnlyEnabled;
	}
	public void setCheckOnlyEnabled(boolean checkOnlyEnabled) {
		this.checkOnlyEnabled = checkOnlyEnabled;
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	
	

		  

    public Passaporto getObject(ResultSet rs) throws SQLException {
    	Passaporto thisP = null;
		try {
			thisP = Passaporto.loadResultSet(rs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return thisP;
	}
	
	
	public void buildList(Connection db) throws SQLException {
	  PreparedStatement pst = null;
	  ResultSet rs = queryList(db, pst);
	  while (rs.next()) {
		  Passaporto thisMC = this.getObject(rs);
	   this.add(thisMC);
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

    sqlCount.append( "SELECT COUNT(*) as recordcount FROM passaporto p WHERE 1=1 and data_cancellazione is null " );
    
    createFilter(db, sqlFilter);

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString() + "AND " + DatabaseUtils.toLowerCase(db) + "(a.serial_number) < ? ");
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
      pagedListInfo.setDefaultSort("p.nr_passaporto", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);

      //Optimize SQL Server Paging
      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
    } else {
      sqlOrder.append("ORDER BY p.nr_passaporto ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append( " *  from  passaporto p " +
    		"WHERE p.nr_passaporto IS NOT NULL AND p.data_cancellazione IS NULL  ");
    
/*   "a.asset_id as id, a.serial_number as assegnato, m.microchip as mc, m.asl as asl, m.assegnato_felina, a.trashed_date " +
    		"FROM asset a " +
    		"RIGHT OUTER JOIN microchips m ON (a.serial_number = m.microchip or a.po_number = m.microchip) " +
    "WHERE m.microchip IS NOT NULL AND m.enabled AND m.trashed_date IS NULL  ");
  */    

    sqlFilter.append("  ");  
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
	
  
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
    if (checkOnlyEnabled){
    	sqlFilter.append( "AND p.flag_abilitato");
    }
    
    
    
    if (idAsl > 0) {
        //sqlFilter.append("AND a.asl_rif = ? ");
        sqlFilter.append("AND p.id_asl_appartenenza = ? ");
    }
    
    if (nrPassaporto != null && !"".equals(nrPassaporto)) {
        sqlFilter.append("AND p.nr_passaporto ilike ? ");
    }
    
    if (assegnato != null && !assegnato) {
        sqlFilter.append("AND ( p.id_animale is null or p.id_animale <=0 ) ");
    }
    if (idUos != null && idUos>0) {
        sqlFilter.append("AND (id_uos is null or id_uos <=0) ");
    }

  }
  
  protected int prepareFilter(PreparedStatement pst) throws SQLException {  
	int i = 0;
	
    if (checkOnlyEnabled){
    	pst.setBoolean(++i, checkOnlyEnabled);
    }
    
    
    
    if (idAsl > 0) {
        //sqlFilter.append("AND a.asl_rif = ? ");
        pst.setInt(++i, idAsl);
    }
    
    if (nrPassaporto != null && !"".equals(nrPassaporto)) {
        pst.setString(++i, nrPassaporto);
    }
	return i;
  }
  
 


}
