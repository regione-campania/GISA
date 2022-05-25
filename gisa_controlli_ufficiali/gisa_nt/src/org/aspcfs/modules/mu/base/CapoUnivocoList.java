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

public class CapoUnivocoList extends Vector implements SyncableList {
	

	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_capi";
	
	
	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;
	@Column(columnName = "id_esercente_articolo_17", columnType = INT, table = nome_tabella)
	private int idEsercente = -1;
	@Column(columnName = "id_macello", columnType = INT, table = nome_tabella)
	private int idMacello = -1;
	@Column(columnName = "id_partita", columnType = INT, table = nome_tabella)
	private int idPartita = -1;
	@Column(columnName = "id_seduta", columnType = INT, table = nome_tabella)
	private int idSeduta = -1;
	@Column(columnName = "matricola", columnType = STRING, table = nome_tabella)
	private String matricola = "";
	@Column(columnName = "id_specie", columnType = INT, table = nome_tabella)
	private int idSpecie = -1;
	@Column(columnName = "id_stato", columnType = INT, table = nome_tabella)
	private int idStato = -1;
	
	
	boolean flagBuildDettagliPartita = false;
	boolean flagBuildDettagliSeduta = false;
	
	
	

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
	

	public int getIdMacello() {
		return idMacello;
	}

	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	
	
	public void setIdMacello(String idMacello) {
		
		if (idMacello != null && !("").equals(idMacello))
			this.idMacello = Integer.valueOf(idMacello);
	}
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEsercente() {
		return idEsercente;
	}

	public void setIdEsercente(int idEsercente) {
		this.idEsercente = idEsercente;
	}

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}
	
	public void setIdPartita(String idPartita) {
		if (idPartita != null && !("").equals(idPartita))
			this.idPartita = Integer.valueOf(idPartita);
	}

	public int getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(int idSeduta) {
		this.idSeduta = idSeduta;
	}
	

	
	
	public void setIdSeduta(String idSeduta) {
		if (idSeduta != null && !("").equals(idSeduta))
			this.idSeduta = Integer.valueOf(idSeduta);
	}



	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	
	

	public boolean isFlagBuildDettagliPartita() {
		return flagBuildDettagliPartita;
	}

	public void setFlagBuildDettagliPartita(boolean flagBuildDettagliPartita) {
		this.flagBuildDettagliPartita = flagBuildDettagliPartita;
	}

	public boolean isFlagBuildDettagliSeduta() {
		return flagBuildDettagliSeduta;
	}

	public void setFlagBuildDettagliSeduta(boolean flagBuildDettagliSeduta) {
		this.flagBuildDettagliSeduta = flagBuildDettagliSeduta;
	}

	
	
	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			CapoUnivoco thisCapo = this.getObject(rs);
			this.add(thisCapo);

			
		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		// buildResources(db);
	}

	
	
	public CapoUnivoco getObject(ResultSet rs) throws SQLException {
		CapoUnivoco thisCapo = new CapoUnivoco();
		try {
			thisCapo.buildRecord(rs, flagBuildDettagliPartita, flagBuildDettagliSeduta);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thisCapo;
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


		sqlCount.append(" COUNT(distinct a.* ) AS recordcount " + "from mu_capi a left join mu_partite p on (a.id_partita = p.id) ");
				

		sqlCount.append(" WHERE a.id >= 0 ");

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
				.append("distinct a.* ");




		sqlSelect.append("from mu_capi a left join mu_partite p on (a.id_partita = p.id)  ");
		

		sqlSelect.append("WHERE a.id >= 0 ");

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


			if (idMacello > -1) {
				sqlFilter.append("and p.id_macello = ? ");
			}
			
			

			if (idStato > -1) {
				sqlFilter.append("and a.id_stato = ? ");
			}
			
			
			if (idPartita > -1) {
				sqlFilter.append("and a.id_partita = ? ");
			}
			
			if (idSeduta > -1) {
				sqlFilter.append("and a.id_seduta = ? ");
			}
			
			
			if (idSpecie > 0){
				sqlFilter.append("and a.specie = ? ");
			}
			
			if (matricola != null && !("").equals(matricola)) {
				sqlFilter.append("and a.matricola = ? ");
			}
			
			

			sqlFilter.append(" AND a.data_cancellazione is NULL ");

	}
	
	
	
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		
		if (idMacello > -1) {
			pst.setInt(++i, idMacello);
		}
		
		

		if (idStato > -1) {
			pst.setInt(++i, idStato);
		}
		
		
		if (idPartita > -1) {
			pst.setInt(++i, idPartita);
		}
		
		if (idSeduta > -1) {
			pst.setInt(++i, idSeduta);
		}
		
		if (idSpecie > 0){
			pst.setInt(++i, idSpecie);
		}
		
		if (matricola != null && !("").equals(matricola)) {
			pst.setString(++i, matricola);
		}

		return i;
	}
	
	 public static HashMap<Integer, Integer> calcolaStatistiche(ArrayList <CapoUnivoco> listaCapi){
		 HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		 
		 Iterator i = listaCapi.iterator();
		 
		 while (i.hasNext()){
			CapoUnivoco thisCapo = (CapoUnivoco) i.next();
			if (map.containsKey(thisCapo.getIdStato()))
				map
						.put(thisCapo.getIdStato(), map.get(thisCapo.getIdStato()) + 1);
			else
				map.put(thisCapo.getIdStato(), 1);
		 }
		 
		 
		 return map;
	  }
	 
	 
	 public static HashMap<Integer, HashMap<Integer, Integer>> calcolaStatisticheSpecie(ArrayList <CapoUnivoco> listaCapi){
		 
		 
		 HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<Integer, HashMap<Integer, Integer>>();
		 
		 
		 
		 Iterator i = listaCapi.iterator();
		 
		 while (i.hasNext()){
			CapoUnivoco thisCapo = (CapoUnivoco) i.next();
			if (map.containsKey(thisCapo.getSpecieCapo())){
				HashMap<Integer, Integer> mappaConteggi = map.get(thisCapo.getSpecieCapo());
				
				if (mappaConteggi.containsKey(thisCapo.getIdStato())){
						mappaConteggi
						.put(thisCapo.getIdStato(), mappaConteggi.get(thisCapo.getIdStato()) + 1);
				}
			else{
				mappaConteggi.put(thisCapo.getIdStato(), 1);
				}
			}else{
				HashMap mappaConteggi = new HashMap<Integer, Integer>();
				mappaConteggi.put(thisCapo.getIdStato(), 1);
				map.put(thisCapo.getSpecieCapo(), mappaConteggi);
			}
		 }
		 
		 
		 return map;
	  }


}
