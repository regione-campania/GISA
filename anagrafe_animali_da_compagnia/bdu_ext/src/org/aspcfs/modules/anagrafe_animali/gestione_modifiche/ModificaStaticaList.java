/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.gestione_modifiche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class ModificaStaticaList extends Vector implements SyncableList {

	private int idModifica = -1;
	private int idAnimale = -1;
	private int idSpecie = -1;
	private int idUtente = -1;
	private java.sql.Timestamp dataModifica = null;
	private String motivazioneModifica = "";
	private int nrCampiModificati = 0;

	protected PagedListInfo pagedListInfo = null;

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
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
	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSyncType(String tmp) {
		// TODO Auto-generated method stub

	}

	public int getIdModifica() {
		return idModifica;
	}

	public void setIdModifica(int idModifica) {
		this.idModifica = idModifica;
	}

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public java.sql.Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(java.sql.Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getMotivazioneModifica() {
		return motivazioneModifica;
	}

	public void setMotivazioneModifica(String motivazioneModifica) {
		this.motivazioneModifica = motivazioneModifica;
	}

	public int getNrCampiModificati() {
		return nrCampiModificati;
	}

	public void setNrCampiModificati(int nrCampiModificati) {
		this.nrCampiModificati = nrCampiModificati;
	}

	public void buildList(Connection db) throws SQLException,
			IndirizzoNotFoundException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			ModificaStatica thisModifica = this.buildModifica(rs);

			// Costruisco lista campi
			thisModifica.getListaCampiModificatiDb(db);
			// thisAnimale.setDetentore(detentore);
			this.add(thisModifica);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		// buildResources(db);
	}

	public ModificaStatica buildModifica(ResultSet rs) throws SQLException {
		ModificaStatica modifica = new ModificaStatica(rs);
		return modifica;
	}

	public ResultSet queryList(Connection db, PreparedStatement pst)
			throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		// Need to build a base SQL statement for counting records

		sqlCount.append("Select COUNT(distinct m.* ) AS recordcount "
				+ "from modifiche_statiche_animale m  ");

		sqlCount.append(" WHERE m.id >= 0 ");

		createFilter(db, sqlFilter);

		if (pagedListInfo != null) {
			// Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString()
					+ sqlFilter.toString());
			// UnionAudit(sqlFilter,db);

			items = prepareFilter(pst);

			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			// Determine the offset, based on the filter, for the first record
			// to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString()
						+ sqlFilter.toString() + "AND "
						+ DatabaseUtils.toLowerCase(db) + "(m.id) < ? ");
				items = prepareFilter(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter()
						.toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			// Determine column to sort by
			pagedListInfo.setColumnToSortBy("m.id");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			// Optimize SQL Server Paging
			// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
			// + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY m.id ");
		}

		// Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		sqlSelect
				.append("distinct m.* from modifiche_statiche_animale m ");

		sqlSelect.append("WHERE m.id >= 0 ");

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
				+ sqlOrder.toString());
		items = prepareFilter(pst);

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}

		rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	protected void createFilter(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (idModifica > -1) {
			sqlFilter.append("and m.id = ? ");
		}
		if (idSpecie > -1) {
			sqlFilter.append("and m.id_specie = ? ");
		}
		if (idAnimale > -1) {
			sqlFilter.append("and m.id_animale = ? ");
		}
		if (dataModifica != null) {
			sqlFilter.append("and m.data_modifica = ? ");
		}
		if (idUtente > -1) {
			sqlFilter.append("and m.id_utente = ? ");
		}
		if (nrCampiModificati > 0) {
			sqlFilter.append("and m.nr_campi_modificati = ? ");
		}

		
	}

	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		if (idModifica > -1) {
			pst.setInt(++i, idModifica);
		}
		if (idSpecie > -1) {
			pst.setInt(++i, idSpecie);
		}
		if (idAnimale > -1) {
			pst.setInt(++i, idAnimale);
		}
		if (dataModifica != null) {
			pst.setTimestamp(++i, dataModifica);
		}
		if (idUtente > -1) {
			pst.setInt(++i, idUtente);
		}
		if (nrCampiModificati > 0) {
			pst.setInt(++i, nrCampiModificati);
		}

		return i;
	}

}
