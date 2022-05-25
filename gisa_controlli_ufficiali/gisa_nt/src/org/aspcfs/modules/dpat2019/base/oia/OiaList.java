/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base.oia;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.oia.actions.Oia;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class OiaList extends Vector implements SyncableList {

	protected PagedListInfo pagedListInfo = null;
	private String 	descrizione_lunga;
	private int 	id_asl;			
	private int tipologia_struttura;
	
	
	
	public String getDescrizione_lunga() {
		return descrizione_lunga;
	}



	public void setDescrizione_lunga(String descrizione_lunga) {
		this.descrizione_lunga = descrizione_lunga;
	}



	public int getId_asl() {
		return id_asl;
	}



	public void setId_asl(int id_asl) {
		this.id_asl = id_asl;
	}
	
	public void setId_asl(String id_asl) {
		if(id_asl != null && ! "".equals(id_asl))
			this.id_asl = Integer.parseInt(id_asl);
	}




	public int getTipologia_struttura() {
		return tipologia_struttura;
	}

	public void setTipologia_struttura(String tipologia_struttura) {
		if(tipologia_struttura != null && ! "".equals(tipologia_struttura))
			this.tipologia_struttura = Integer.parseInt(tipologia_struttura);
	}

	
	public void setTipologia_struttura(int tipologia_struttura) {
		this.tipologia_struttura = tipologia_struttura;
	}

	private static Logger log = Logger.getLogger(org.aspcfs.modules.oia.base.OiaList.class);

	/**
	 *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
	 *
	 *  <p/>
	 *
	 *  The query results will be constrained to the PagedListInfo parameters.
	 *
	 * @param  tmp  The new PagedListInfo value
	 * @since       1.1
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}



	public void buildList(Connection db) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			OiaNodo thisNodo = this.getObject(rs);

			this.add(thisNodo);

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
				"SELECT  COUNT( distinct oia_n.id) AS recordcount " +
				"FROM oia_nodo oia_n   " +
				"left join oia_nodo_responsabili responsabili on oia_n.id = responsabili.id_oia_nodo " +
		"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) where  oia_n.n_livello<3 and oia_n.trashed_date is null and o.trashed_date is null "
			);




	
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

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(
						sqlCount.toString() +
						sqlFilter.toString() 
					);
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
			pagedListInfo.setColumnToSortBy("oia_n.id_asl");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		sqlSelect.append(
				" distinct oia_n.*,o.org_id,tipooia.description as descrizione_tipologia_struttura,asl.description as asl_stringa,cmni.comune as descrizione_comune " +
				"FROM oia_nodo oia_n   " +
				"LEFT JOIN comuni cmni on cmni.codiceistatcomune = oia_n.comune::text " +
				"left join oia_nodo_responsabili responsabili on oia_n.id = responsabili.id_oia_nodo " +
		"LEFT JOIN organization o on (oia_n.id_asl = o.site_id and o.tipologia =6) " +
		"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_n.tipologia_struttura = tipooia.code " +
		"LEFT JOIN lookup_site_id asl ON oia_n.id_asl = asl.code " +
		"where oia_n.trashed_date is null and oia_n.n_livello<3 and o.trashed_date is null "
		);

		



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

	protected void createFilter(Connection db, StringBuffer sqlFilter) {
		//andAudit( sqlFilter );
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}
		if(tipologia_struttura>0)
		{
			sqlFilter.append(" and oia_n.id = ? ");
		}
		
		
		/*if(descrizione_lunga != null && !descrizione_lunga.equals(""))
		{
			sqlFilter.append(" and descrizione_lunga = ? ");
		}*/

	}

	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if(tipologia_struttura>0)
		{
			pst.setInt(++i, tipologia_struttura);
		}
		
		return i ;

	}
	
	 public OiaNodo getObject(ResultSet rs) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		 OiaNodo thisNodo = new OiaNodo();
		 thisNodo.loadResultSet(rs);
		 
		    return thisNodo;
		  }



	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}
}
