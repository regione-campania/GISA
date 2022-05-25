/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.base;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.utils.DatabaseUtils;

public class PartitaUnivocaList extends Vector implements SyncableList {
	

	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_capi";
	
	
	private String numeroPartita = "";
	private String matricola = "";
	private int orgId = -1;
	
	
	

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
	
	
	
	

	public String getNumeroPartita() {
		return numeroPartita;
	}

	public void setNumeroPartita(String numeroPartita) {
		this.numeroPartita = numeroPartita;
	}
	
	

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	
	
	

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			PartitaUnivoca partita = this.getObject(rs);
			this.add(partita);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
	}

	
	
	public PartitaUnivoca getObject(ResultSet rs) throws SQLException {
		PartitaUnivoca thisPartita = new PartitaUnivoca();
		try {
			thisPartita.buildRecord(rs);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisPartita;
	}
	
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		

		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		// Need to build a base SQL statement for counting records

		sqlCount.append("Select ");


		sqlCount.append(" COUNT(distinct p.* ) AS recordcount " + "from  mu_partite p ");
		
		if (matricola != null && !("").equals(matricola)){
			sqlCount.append("left join mu_capi c on (p.id = c.id_partita ) ");
		}
				

		sqlCount.append(" WHERE p.id >= 0 ");

		createFilter(db, sqlFilter);

	//	if (pagedListInfo != null) {
			// Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);

			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
			//	pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			// Determine the offset, based on the filter, for the first record
			// to show
//			if (!pagedListInfo.getCurrentLetter().equals("")) {
//				pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString() + "AND "
//						+ DatabaseUtils.toLowerCase(db) + "(a.nome) < ? ");
//				items = prepareFilter(pst);
//				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
//
//				rs = pst.executeQuery();
//				if (rs.next()) {
//					int offsetCount = rs.getInt("recordcount");
//					pagedListInfo.setCurrentOffset(offsetCount);
//				}
//				rs.close();
//				pst.close();
//			}
//
//			// Determine column to sort by
//			pagedListInfo.setColumnToSortBy("a.nome");
//			pagedListInfo.appendSqlTail(db, sqlOrder);

			// Optimize SQL Server Paging
			// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
			// + sqlOrder.toString());
//		} else {
//			sqlOrder.append("ORDER BY a.nome ");
//		}

		// Need to build a base SQL statement for returning records
//		if (pagedListInfo != null) {
//			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
//		} else {
			sqlSelect.append("SELECT ");

//		}

		sqlSelect
				.append("distinct p.* ");




		sqlSelect.append("from mu_partite p ");
		
		if (matricola != null && !("").equals(matricola)){
			sqlSelect.append("left join mu_capi c on (p.id = c.id_partita ) ");
		}
			
		

		sqlSelect.append("WHERE p.id >= 0 ");

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		
		items = prepareFilter(pst);

//		if (pagedListInfo != null) {
//			pagedListInfo.doManualOffset(db, pst);
//		}
//		if (System.getProperty("DEBUG") != null)
//			System.out.println("QUERY!!!:  " + pst.toString());
		rs = DatabaseUtils.executeQuery(db, pst);
//		if (pagedListInfo != null) {
//			pagedListInfo.doManualOffset(db, rs);
//		}
		return rs;
	
		
		
	}
	
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (numeroPartita != null && !("").equals(numeroPartita)) {
			sqlFilter.append(" and p.numero = ?");
		}
		
		if (matricola != null && !("").equals(matricola)){
			sqlFilter.append(" and c.matricola = ?");
		}
		
		if (orgId > 0){
			sqlFilter.append(" and p.id_macello = ?");
		}

	}
	
	
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		
		if (numeroPartita != null && !("").equals(numeroPartita)) {
			pst.setString(++i, numeroPartita);
		}
		
		if (matricola != null && !("").equals(matricola)){
			pst.setString(++i, matricola);
		}
		
		if (orgId > 0){
			pst.setInt(++i, orgId);
		}

		return i;
	}
	
	

}
