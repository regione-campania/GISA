/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.partitecommerciali.base;

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

public class PartiteCommercialiList extends Vector implements SyncableList {

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


	private int idPartitaCommerciale = -1;
	private int idAslRiferimento = -1;
	private int idTipoPartita = -1;
	private int idImportatore = -1;
	private String nrCertificato = "";
	private int idNazioneProvenienza = -1;
	private int nrAnimaliPartita = -1;
	private java.sql.Timestamp dataArrivoPrevista = null;
	private java.sql.Timestamp dataArrivoEffettiva = null;
	private Operatore operatoreCommerciale = null;

	private boolean flagPresenzaVincoloSanitario = false;
	private boolean flagControlloDocumentaleRichiesto = false;
	private boolean flagControlloIdentitaRichiesto = false;
	private boolean flagControlloFisicoRichiesto = false;
	private boolean flagControlloLaboratorioRichiesto = false;
	
	
	private int idStatoImportatore = -1;

	public int getIdStatoImportatore() {
		return idStatoImportatore;
	}

	public void setIdStatoImportatore(int idStatoImportatore) {
		this.idStatoImportatore = idStatoImportatore;
	}


	protected PagedListInfo pagedListInfo = null;

	public int getIdPartitaCommerciale() {
		return idPartitaCommerciale;
	}

	public void setIdPartitaCommerciale(int idPartitaCommerciale) {
		this.idPartitaCommerciale = idPartitaCommerciale;
	}

	public int getIdAslRiferimento() {
		return idAslRiferimento;
	}

	public void setIdAslRiferimento(int idAslRiferimento) {
		this.idAslRiferimento = idAslRiferimento;
	}

	public int getIdTipoPartita() {
		return idTipoPartita;
	}

	public void setIdTipoPartita(int idTipoPartita) {
		this.idTipoPartita = idTipoPartita;
	}

	public int getIdImportatore() {
		return idImportatore;
	}

	public void setIdImportatore(int idImportatore) {
		this.idImportatore = idImportatore;
	}

	public String getNrCertificato() {
		return nrCertificato;
	}

	public void setNrCertificato(String nrCertificato) {
		this.nrCertificato = nrCertificato;
	}

	public int getIdNazioneProvenienza() {
		return idNazioneProvenienza;
	}

	public void setIdNazioneProvenienza(int idNazioneProvenienza) {
		this.idNazioneProvenienza = idNazioneProvenienza;
	}

	public int getNrAnimaliPartita() {
		return nrAnimaliPartita;
	}

	public void setNrAnimaliPartita(int nrAnimaliPartita) {
		this.nrAnimaliPartita = nrAnimaliPartita;
	}

	public java.sql.Timestamp getDataArrivoPrevista() {
		return dataArrivoPrevista;
	}

	public void setDataArrivoPrevista(java.sql.Timestamp dataArrivoPrevista) {
		this.dataArrivoPrevista = dataArrivoPrevista;
	}

	public java.sql.Timestamp getDataArrivoEffettiva() {
		return dataArrivoEffettiva;
	}

	public void setDataArrivoEffettiva(java.sql.Timestamp dataArrivoEffettiva) {
		this.dataArrivoEffettiva = dataArrivoEffettiva;
	}

	public Operatore getOperatoreCommerciale() {
		return operatoreCommerciale;
	}

	public void setOperatoreCommerciale(Operatore operatoreCommerciale) {
		this.operatoreCommerciale = operatoreCommerciale;
	}

	public boolean isFlagPresenzaVincoloSanitario() {
		return flagPresenzaVincoloSanitario;
	}

	public void setFlagPresenzaVincoloSanitario(boolean flagPresenzaVincoloSanitario) {
		this.flagPresenzaVincoloSanitario = flagPresenzaVincoloSanitario;
	}

	public boolean isFlagControlloDocumentaleRichiesto() {
		return flagControlloDocumentaleRichiesto;
	}

	public void setFlagControlloDocumentaleRichiesto(
			boolean flagControlloDocumentaleRichiesto) {
		this.flagControlloDocumentaleRichiesto = flagControlloDocumentaleRichiesto;
	}

	public boolean isFlagControlloIdentitaRichiesto() {
		return flagControlloIdentitaRichiesto;
	}

	public void setFlagControlloIdentitaRichiesto(
			boolean flagControlloIdentitaRichiesto) {
		this.flagControlloIdentitaRichiesto = flagControlloIdentitaRichiesto;
	}

	public boolean isFlagControlloFisicoRichiesto() {
		return flagControlloFisicoRichiesto;
	}

	public void setFlagControlloFisicoRichiesto(boolean flagControlloFisicoRichiesto) {
		this.flagControlloFisicoRichiesto = flagControlloFisicoRichiesto;
	}

	public boolean isFlagControlloLaboratorioRichiesto() {
		return flagControlloLaboratorioRichiesto;
	}

	public void setFlagControlloLaboratorioRichiesto(
			boolean flagControlloLaboratorioRichiesto) {
		this.flagControlloLaboratorioRichiesto = flagControlloLaboratorioRichiesto;
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
		while (rs.next()) {
			PartitaCommerciale thisPartita = this.getObject(rs);

			//Costruisco operatore commerciale
			Operatore operatoreCommerciale = null;


			if (rs.getInt("id_importatore") > -1)
			{
				operatoreCommerciale = new Operatore();
				operatoreCommerciale.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_importatore"));
			}


			thisPartita.setOperatoreCommerciale(operatoreCommerciale);
			//thisAnimale.setDetentore(detentore);
			this.add(thisPartita);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//   buildResources(db);
	}


	public void buildListPartiteImportatori(Connection dbImportatori,Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryListPartiteImportatori(dbImportatori, pst);
		while (rs.next()) {
			PartitaCommerciale thisPartita = this.getObjectImportatori(rs);

			//Costruisco operatore commerciale
			Operatore operatoreCommerciale = null;
			if (rs.getInt("id_importatore") > -1)
			{
				operatoreCommerciale = new Operatore();
				operatoreCommerciale.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_importatore"));
			}

			thisPartita.setOperatoreCommerciale(operatoreCommerciale);
			//thisAnimale.setDetentore(detentore);
			this.add(thisPartita);

		}

		rs.close();
		if (pst != null) {
			pst.close();
		}
		//   buildResources(db);
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
				"Select COUNT(distinct p.* ) AS recordcount " +
		"from partita_commerciale p");



		sqlCount.append(" WHERE p.id_partita_commerciale >= 0 and p.data_cancellazione is null ");


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
						sqlFilter.toString() +
						"AND " + DatabaseUtils.toLowerCase(db) + "(p.nr_certificato) < ? ");
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
			pagedListInfo.setColumnToSortBy("p.data_arrivo_prevista, p.data_inserimento");
			pagedListInfo.setSortOrder("DESC");
			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY p.data_inserimento DESC ");
		}

		//Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}


		sqlSelect.append(
				"distinct p.* " +
		"from partita_commerciale p "  );

		sqlSelect.append("WHERE p.id_partita_commerciale >= 0 and p.data_cancellazione is null ");



		pst = db.prepareStatement(
				sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);


		System.out.println("aaaaaa "+sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}


		rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	
	
	public ResultSet queryListPartiteImportatori(Connection db, PreparedStatement pst) throws SQLException {
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for counting records

		sqlCount.append("Select COUNT(* ) AS recordcount from get_lista_partite(?) ");
		pagedListInfo.setColumnToSortBy("id_asl_riferimento");
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString());
			// UnionAudit(sqlFilter,db);
			pst.setInt(1, idAslRiferimento);

			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();


			pagedListInfo.appendSqlTail(db, sqlOrder);

			//Optimize SQL Server Paging
			//sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY nr_certificato ");
		}

		


		sqlSelect.append("select distinct * from get_lista_partite(?) "  );

	
		pst = db.prepareStatement(sqlSelect.toString() + sqlOrder.toString());
		pst.setInt(1, idAslRiferimento);
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


		if (idPartitaCommerciale > -1){
			sqlFilter.append(" AND p.id_partita_commerciale = ? ");
		}

		if(idAslRiferimento > -1){
			sqlFilter.append(" AND p.id_asl_riferimento = ? ");

		}

		if(idTipoPartita > -1){
			sqlFilter.append(" AND p.id_tipo_partita = ? ");

		}

		if(idImportatore > -1){
			sqlFilter.append(" AND p.id_importatore = ? ");

		}

		if(!("").equals(nrCertificato)){
			sqlFilter.append(" AND p.nr_certificato = ? ");

		}

		if(idNazioneProvenienza > -1){
			sqlFilter.append(" AND p.id_nazione_provenienza = ? ");

		}
		if(nrAnimaliPartita > -1){
			sqlFilter.append(" AND p.nr_animali_partita = ? ");

		}

		if(dataArrivoPrevista != null){
			sqlFilter.append(" AND p.data_arrivo_prevista = ? ");
		}
		if(dataArrivoEffettiva != null){
			sqlFilter.append(" AND p.data_arrivo_effettiva = ? ");
		}
		
		if (idStatoImportatore > -1){
			sqlFilter.append(" AND p.id_stato_importatore = ? ");
		}



	}




	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;


		if (idPartitaCommerciale > -1){
			pst.setInt(++i, idPartitaCommerciale);
		}

		if(idAslRiferimento > -1){
			pst.setInt(++i, idAslRiferimento);

		}

		if(idTipoPartita > -1){
			pst.setInt(++i, idTipoPartita);

		}

		if(idImportatore > -1){
			pst.setInt(++i, idImportatore);

		}

		if(!("").equals(nrCertificato)){
			pst.setString(++i, nrCertificato);

		}

		if(idNazioneProvenienza > -1){
			pst.setInt(++i, idNazioneProvenienza);

		}
		if(nrAnimaliPartita > -1){
			pst.setInt(++i, nrAnimaliPartita);

		}

		if(dataArrivoPrevista != null){
			pst.setTimestamp(++i, dataArrivoPrevista);
		}
		if(dataArrivoEffettiva != null){
			pst.setTimestamp(++i, dataArrivoEffettiva);
		}
		
		if (idStatoImportatore > -1){
			pst.setInt(++i, idStatoImportatore);
		}




		return i;
	}

	public PartitaCommerciale getObject(ResultSet rs) throws SQLException {
		PartitaCommerciale thisPartita = new PartitaCommerciale(rs);
		return thisPartita;
	}
	
	public PartitaCommerciale getObjectImportatori(ResultSet rs) throws SQLException {
		PartitaCommerciale thisPartita = new PartitaCommerciale();
		thisPartita.buildFromImportatori(rs);
		return thisPartita;
	}

}
