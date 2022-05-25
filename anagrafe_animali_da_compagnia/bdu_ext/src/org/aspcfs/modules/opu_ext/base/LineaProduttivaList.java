/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class LineaProduttivaList extends Vector implements SyncableList {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu_ext.base.StabilimentoList.class);
	protected PagedListInfo pagedListInfo = null;
	private int idCategoria ;
	private int idMacrocategoria ;
	private int idStabilimento ;
	private String tipoSelezione ;
	private Integer[] idLineaProduttiva ;
	private int id ;
	
	
	
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoSelezione() {
		return tipoSelezione;
	}

	public void setTipoSelezione(String tipoSelezione) {
		this.tipoSelezione = tipoSelezione;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}


	public void setIdCategoria(String idCategoria) {
		if(idCategoria != null && !idCategoria.equals(""))
			setIdCategoria(Integer.parseInt(idCategoria));
	}

	public void setIdMacrocategoria(String idMacrocategoria) {
		if(idMacrocategoria != null && !idMacrocategoria.equals(""))
			setIdMacrocategoria(Integer.parseInt(idMacrocategoria));
	}


	public int getIdMacrocategoria() {
		return idMacrocategoria;
	}

	public void setIdMacrocategoria(int idMacrocategoria) {
		this.idMacrocategoria = idMacrocategoria;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	

	public Integer[] getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(Integer[] idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
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

	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		String sqlSele = 	"select  distinct opu_relazione_attivita_produttive_aggregazioni.id, opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria,"+
		"opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,"+ 
		"opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita  " ;

		String sqlConta = "select count (*) as recordcount " ;

		String sqlFrom = " from opu_lookup_attivita_linee_produttive_aggregazioni " +
		"join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione "+
		"join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione "+
		"join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive " +
		"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id " +
		"where 1=1 and rslp.trashed_date is null ";

		sqlCount.append(sqlConta + sqlFrom);
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
				pagedListInfo.setItemsPerPage(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("opu_lookup_attivita_linee_produttive_aggregazioni.description, opu_lookup_macrocategorie_linee_produttive.code,opu_lookup_aggregazioni_linee_produttive.code,opu_lookup_attivita_linee_produttive_aggregazioni.code");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY opu_lookup_macrocategorie_linee_produttive.code,opu_lookup_aggregazioni_linee_produttive.code,opu_lookup_attivita_linee_produttive_aggregazioni.code");
		}

		//Need to build a base SQL statement for returning records

		sqlSelect.append(sqlSele);
		sqlSelect.append(sqlFrom);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}


	public ResultSet queryListStabilimento(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		String sqlSele = 	"select  distinct rslp.autorizzazione,rslp.id, rslp.id_linea_produttiva as id_relazione_attivita,rslp.data_fine,rslp.stato, rslp.data_inizio,opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria,"+
		"opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,"+ 
		"opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita  " ;

		String sqlConta = "select count (*) as recordcount " ;

		String sqlFrom = " from opu_lookup_attivita_linee_produttive_aggregazioni " +
		"join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione "+
		"join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione "+
		"join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive " +
		"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id " +
		"where 1=1 and rslp.trashed_date is null ";

		sqlCount.append(sqlConta + sqlFrom);
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
				pagedListInfo.setItemsPerPage(maxRecords);
			}
			rs.close();
			pst.close();

			//Determine column to sort by
			pagedListInfo.setColumnToSortBy("opu_lookup_macrocategorie_linee_produttive.code,opu_lookup_aggregazioni_linee_produttive.code,opu_lookup_attivita_linee_produttive_aggregazioni.code");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY opu_lookup_macrocategorie_linee_produttive.code,opu_lookup_aggregazioni_linee_produttive.code,opu_lookup_attivita_linee_produttive_aggregazioni.code");
		}

		//Need to build a base SQL statement for returning records

		sqlSelect.append(sqlSele);
		sqlSelect.append(sqlFrom);
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);

		rs = pst.executeQuery();
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	protected void createFilter(Connection db, StringBuffer sqlFilter) 
	{
		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		if (idCategoria>0)
			sqlFilter.append(" and opu_relazione_attivita_produttive_aggregazioni.id_aggregazione = ? ");
		if (idMacrocategoria >0)
			sqlFilter.append(" and opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive = ? ");
		if(idStabilimento>0)
			sqlFilter.append(" and id_stabilimento = ? ");
		if (id>0)
			sqlFilter.append(" and rslp.id = ? ");
		
	    if (idLineaProduttiva != null && idLineaProduttiva.length>0)
	    {
	    	sqlFilter.append("AND  rslp.id_linea_produttiva in ( ");
	    	for (int i = 0 ; i<idLineaProduttiva.length-1 ; i++)
	    	{
	    		if(! idLineaProduttiva[i].equals("-1"))
	    			sqlFilter.append(idLineaProduttiva[i]+",");
	    	}
	    	if(! idLineaProduttiva[idLineaProduttiva.length-1].equals("-1"))
	    		sqlFilter.append(idLineaProduttiva[idLineaProduttiva.length-1]+") ");
	    	else
	    		sqlFilter.append(") ");
	    	
	    	
	    }
			

	}
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		if (idCategoria>0)
			pst.setInt(++i, idCategoria) ;
		if (idMacrocategoria>0)
			pst.setInt(++i, idMacrocategoria) ;
		if(idStabilimento>0)
			pst.setInt(++i, idStabilimento) ;
		if (id>0)
			pst.setInt(++i, id) ;
		return i;
	}


	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {

			LineaProduttiva thisIter = this.getObject(rs);
			this.add(thisIter);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//  buildResources(db);
	}

	public void buildListStabilimento(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListStabilimento(db, pst);
		while (rs.next()) {

			LineaProduttiva thisIter = this.getObjectStabilimento(rs);
			switch (thisIter.getIdRelazioneAttivita() ) {
			
			case  LineaProduttiva.idAggregazioneImportatore:
 				thisIter = ImportatoreInformazioni.getInfoAddizionali(thisIter, db);
				break;


			default:
				break;
			}
			this.add(thisIter);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//  buildResources(db);
	}


	public LineaProduttiva getObject(ResultSet rs) throws SQLException {

		LineaProduttiva lp = new LineaProduttiva(rs) ;
		return lp ;

	}

	public LineaProduttiva getObjectStabilimento(ResultSet rs) throws SQLException {

		LineaProduttiva lp = new LineaProduttiva(rs,true) ;
		return lp ;

	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}




}
