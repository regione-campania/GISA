/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class HistoryClassificazioneList  extends Vector {
	private static Logger log = Logger.getLogger(org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazioneList.class);

	protected PagedListInfo pagedListInfo = null;
	protected int idZona ;
	
	
	public int getIdZona() {
		return idZona;
	}


	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}


	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}


	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) 
	    {
	    	HistoryClassificazione thisOrganization = new HistoryClassificazione (rs);
	    	thisOrganization.getListaProvvedimenti().setIdZona(thisOrganization.getIdZona());
	    	thisOrganization.getListaProvvedimenti().buildListProvvedimenti(db, thisOrganization.getNumDecreto());
	    	thisOrganization.setListaMolluschi(this.buildMolluschiHistory(db, thisOrganization.getId()));
	    	//thisOrganization.setListaMotivi(this.buildMotiviHistory(db,  thisOrganization.getMotivi()));
	        this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   
	  }
	
	
	
	
	
	public void buildListProvvedimenti(Connection db,String numDecreto) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryListProvvedimenti(db, pst,numDecreto);
	    while (rs.next()) 
	    {
	    	HistoryClassificazione thisOrganization = new HistoryClassificazione (rs);
	    	thisOrganization.setListaMolluschi(this.buildMolluschiHistory(db, thisOrganization.getId()));
	    	thisOrganization.setListaMotivi(this.buildMotiviHistory(db,  thisOrganization.getMotivi()));
	    	this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   
	  }
	
	
	
	public ResultSet queryListProvvedimenti(Connection db, PreparedStatement pst,String numDecreto) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		sqlCount.append("SELECT  COUNT(distinct o.*) AS recordcount " +
						"FROM decreto_classificazione_molluschi o " +
						"join organization moll on (moll.org_id = o.id_zona) " );
		sqlCount.append(" WHERE o.id_zona =? and o.tipo_provvedimento ilike 'provvedimento' and o.num_decreto ilike ? ");
		//createFilter(db, sqlFilter);
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
			pst.setInt(1, idZona);
			pst.setString(2, numDecreto);

			//items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.entered");
			pagedListInfo.appendSqlTail(db, sqlOrder);
		} else 
		{
			sqlOrder.append(" ORDER BY o.data_provvedimento desc ");
		}

		if (pagedListInfo != null) 
		{
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else 
		{
			sqlSelect.append("SELECT ");
		}
		sqlSelect.append(
		        "distinct o.* " +
		        "FROM decreto_classificazione_molluschi o "  + 
		        "LEFT JOIN organization moll ON (moll.org_id = o.id_zona) where tipologia = 201 and moll.org_id = ? and o.tipo_provvedimento ilike 'provvedimento' and o.num_decreto ilike ? ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		pst.setInt(1, idZona) ;
		pst.setString(2, numDecreto);
		//items = prepareFilter(pst);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}
		rs = DatabaseUtils.executeQuery(db, pst, log);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		sqlCount.append("SELECT  COUNT(distinct o.num_decreto, o.data_classificazione, o.data_fine_classificazione, o.classe) AS recordcount " +
						"FROM decreto_classificazione_molluschi o " +
						"join organization moll on (moll.org_id = o.id_zona) " );
		sqlCount.append(" WHERE o.id_zona =? and o.tipo_provvedimento is null ");
		//createFilter(db, sqlFilter);
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
			pst.setInt(1, idZona);
			//items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("o.entered");
			pagedListInfo.appendSqlTail(db, sqlOrder);
		} else 
		{
			sqlOrder.append(" ORDER BY o.data_classificazione desc ");
		}

		if (pagedListInfo != null) 
		{
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else 
		{
			sqlSelect.append("SELECT ");
		}
		sqlSelect.append(
		        "distinct o.num_decreto, o.data_classificazione, o.data_fine_classificazione, o.classe  " +
		        "FROM decreto_classificazione_molluschi o "  + 
		        "LEFT JOIN organization moll ON (moll.org_id = o.id_zona) where tipologia = 201 and moll.org_id = ?  and o.tipo_provvedimento is null ");
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		pst.setInt(1, idZona) ;
		//items = prepareFilter(pst);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}
		rs = DatabaseUtils.executeQuery(db, pst, log);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}
	
	protected HashMap<Integer, String> buildMolluschiHistory(Connection db , int idHistoryDecreto)
	{
		HashMap<Integer, String> listaHistoryMoll = new HashMap<Integer, String>() ;
	
		try
		{
			
		PreparedStatement pst = db.prepareStatement("select * from tipo_molluschi_decreti_classificazione where id_decreto_classificazione_molluschi = ? ");
		pst.setInt(1,idHistoryDecreto );
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			listaHistoryMoll.put(rs.getInt("id_matrice"), rs.getString("cammino"));
		}
	}
	catch(SQLException e)
	{
		e.printStackTrace() ;
	}
	return listaHistoryMoll ;
	}
	
	protected HashMap<Integer, String> buildMotiviHistory(Connection db , String motivi)
	{
		HashMap<Integer, String> listaHistoryMotivi = new HashMap<Integer, String>() ;
		if (motivi!=null){
			if (motivi.endsWith(","))
				motivi = motivi.substring(0, motivi.length()-1);
			try
			{
			
				PreparedStatement pst = db.prepareStatement("select * from lookup_classi_acque where code in ("+motivi+") ");
		
				ResultSet rs = pst.executeQuery();
				while (rs.next())
				{
					listaHistoryMotivi.put(rs.getInt("code"), rs.getString("description"));
				}
			}
	catch(SQLException e)
	{
		e.printStackTrace() ;
	}
		}
	return listaHistoryMotivi ;
	}

	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		
		
	}


	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
	
		return i;
	}
	
}
