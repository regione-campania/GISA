/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.microchip.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class MicrochipList extends ArrayList {
	
	protected PagedListInfo pagedListInfo = null;
	private int aslRif = -1;
	private String microchip = null;
	private String codiceFiscale = null;
	private boolean checkOnlyEnabled = false;
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getMicrochip() {
		return microchip;
	}
	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
	public int getAslRif() {
		return aslRif;
	}
	public void setAslRif(int aslRif) {
		this.aslRif = aslRif;
	}
	
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	
	
	public MicrochipList(Connection db) throws SQLException {
	}
	
	public MicrochipList() {
	}
		  

    public Microchip getObject(ResultSet rs) throws SQLException {
	  Microchip thisMC = new Microchip(rs);
	  return thisMC;
	}
	
	
	public void buildList(Connection db) throws SQLException {
	  PreparedStatement pst = null;
	  ResultSet rs = queryList(db, pst);
	  while (rs.next()) {
	   Microchip thisMC = this.getObject(rs);
	   if (thisMC.getIdImport()>0 && thisMC.getDataCaricamento()==null)
		   thisMC.setInformazioniImport(db);
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

    sqlCount.append( "SELECT COUNT(*) as recordcount FROM microchips m WHERE 1=1 and trashed_date is null " );
    
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
      pagedListInfo.setDefaultSort("m.microchip", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);

      //Optimize SQL Server Paging
      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
    } else {
      sqlOrder.append("ORDER BY m.id, m.microchip ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append( " *  from  microchips m " +
    		"WHERE m.microchip IS NOT NULL AND m.trashed_date IS NULL  ");
    
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
    	sqlFilter.append( "AND m.enabled");
    }
    
    if (codiceFiscale != null) {
    		 sqlFilter.append("AND m.asl = ? "); 
    }
    
    if (aslRif > -1) {
        //sqlFilter.append("AND a.asl_rif = ? ");
        sqlFilter.append("AND m.asl = ? ");
    }
    
    if (microchip != null && !"".equals(microchip)) {
        sqlFilter.append("AND m.microchip = ? ");
    }

  }
  
  protected int prepareFilter(PreparedStatement pst) throws SQLException {  
	int i = 0;
	if (codiceFiscale != null) {
      pst.setString(++i, codiceFiscale.replaceAll("%", ""));
    }
    
    if (aslRif > -1) {
	  //pst.setInt(++i, aslRif);
      pst.setString(++i, getAsl(aslRif));
    }
    if (microchip != null && !"".equals(microchip)) {
  	  pst.setString(++i, microchip);
  	}
  		
	return i;
  }
  
  //utility
 /* protected String getAsl(int aslRifId){
    if (aslRifId == 1){
	  return "AV1";
    }
    if (aslRifId == 2){
	  return "AV2";
    }
    if (aslRifId == 3){
      return "NA1";
    }
    if (aslRifId == 4){
  	  return "NA2";
    }
    if (aslRifId == 5){
  	  return "NA3";
    }
    if (aslRifId == 6){
      return "NA4";
    }
    if (aslRifId == 7){
      return "NA5";
    }
    if (aslRifId == 8){
      return "BN1";
    }
    if (aslRifId == 9){
      return "CE1";
    }
    if (aslRifId == 10){
      return "CE2";
    }   
    if (aslRifId == 11){
      return "SA1";
    }
    if (aslRifId == 12){
      return "SA2";
    }
    if (aslRifId == 13){
      return "SA3";
    }
    
    return "";
  }
*/
	
	 protected String getAsl(int aslRifId){
    if (aslRifId == 201){
	  return "AV";
    }
    if (aslRifId == 202){
	  return "BN";
    }
    if (aslRifId == 203){
      return "CE";
    }
    if (aslRifId == 204){
  	  return "NA1C";
    }
    if (aslRifId == 205){
  	  return "NA2N";
    }
    if (aslRifId == 206){
      return "NA3S";
    }
    if (aslRifId == 207){
      return "SA";
    }
    return "";
  }
	
}