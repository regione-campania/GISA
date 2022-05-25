/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.gestioneml.actions.GestioneMasterList;

public class SintesisStoricoRelazioneLineaProduttiva {
	
	private int id =-1;
	private int idUtente = -1;
	private Timestamp dataModifica;
	
		private int idRelazione = -1;
		
		private int statoOld = -1;
		private Timestamp dataInizioOld = null;
		private Timestamp  dataFineOld = null;
		private String tipoAutorizzazioneOld = null;
		private String imballaggioOld = null;
		private String paesiAbilitatiExportOld = null;
		private String remarkOld = null;
		private String speciesOld = null;
	
		private int statoNew = -1;
		private Timestamp dataInizioNew = null;
		private Timestamp  dataFineNew = null;
		private String tipoAutorizzazioneNew = null;
		private String imballaggioNew = null;
		private String paesiAbilitatiExportNew = null;
		private String remarkNew = null;
		private String speciesNew = null;
		
		
		private String pathCompleto = null;
		
		private int idRecord;
		private int idImport;
		

		public SintesisStoricoRelazioneLineaProduttiva(ResultSet rs, Connection db) throws SQLException {
				buildRecord(rs);
				setPathCompleto(db);
				
		}
		
		public void buildRecord(ResultSet rs) throws SQLException {
			id = rs.getInt("id");
			idUtente = rs.getInt("id_utente");
			dataModifica = rs.getTimestamp("data_modifica");
			statoOld = rs.getInt("stato_old");
			statoNew = rs.getInt("stato_new");
			idRelazione = rs.getInt("id_relazione");
			 
			 dataInizioOld = rs.getTimestamp("data_inizio_old");
			dataFineOld = rs.getTimestamp("data_fine_old");
			  tipoAutorizzazioneOld = rs.getString("tipo_autorizzazione_old");
			  imballaggioOld = rs.getString("imballaggio_old");
			  paesiAbilitatiExportOld = rs.getString("paesi_abilitati_export_old");
			  remarkOld = rs.getString("remark_old");
			  speciesOld = rs.getString("species_old");
			  
			  dataInizioNew = rs.getTimestamp("data_inizio_new");
				dataFineNew = rs.getTimestamp("data_fine_new");
				  tipoAutorizzazioneNew = rs.getString("tipo_autorizzazione_new");
				  imballaggioNew = rs.getString("imballaggio_new");
				  paesiAbilitatiExportNew = rs.getString("paesi_abilitati_export_new");
				  remarkNew = rs.getString("remark_new");
				  speciesNew = rs.getString("species_new");
				  
				  idRecord = rs.getInt("id_sintesis_stabilimenti_import");
					idImport = rs.getInt("id_import");
		}
		
	public String getPathCompleto() {
		return pathCompleto;
	}
	public void setPathCompleto(String pathCompleto) {
		this.pathCompleto = pathCompleto;
	}

	public void setPathCompleto(Connection db) throws SQLException {
		int idLineaMasterList = -1;
		
		PreparedStatement pst = db.prepareStatement("select * from sintesis_relazione_stabilimento_linee_produttive where id = ?");
		pst.setInt(1, idRelazione);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			idLineaMasterList = rs.getInt("id_linea_produttiva");
		
		
		String lineaAttivitaMasterList = GestioneMasterList.getPathCompleto(db, idLineaMasterList);
		this.pathCompleto = lineaAttivitaMasterList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(int idRelazione) {
		this.idRelazione = idRelazione;
	}

	public int getStatoOld() {
		return statoOld;
	}

	public void setStatoOld(int statoOld) {
		this.statoOld = statoOld;
	}

	public Timestamp getDataInizioOld() {
		return dataInizioOld;
	}

	public void setDataInizioOld(Timestamp dataInizioOld) {
		this.dataInizioOld = dataInizioOld;
	}

	public Timestamp getDataFineOld() {
		return dataFineOld;
	}

	public void setDataFineOld(Timestamp dataFineOld) {
		this.dataFineOld = dataFineOld;
	}

	public String getTipoAutorizzazioneOld() {
		return tipoAutorizzazioneOld;
	}

	public void setTipoAutorizzazioneOld(String tipoAutorizzazioneOld) {
		this.tipoAutorizzazioneOld = tipoAutorizzazioneOld;
	}

	public String getImballaggioOld() {
		return imballaggioOld;
	}

	public void setImballaggioOld(String imballaggioOld) {
		this.imballaggioOld = imballaggioOld;
	}

	public String getPaesiAbilitatiExportOld() {
		return paesiAbilitatiExportOld;
	}

	public void setPaesiAbilitatiExportOld(String paesiAbilitatiExportOld) {
		this.paesiAbilitatiExportOld = paesiAbilitatiExportOld;
	}

	public String getRemarkOld() {
		return remarkOld;
	}

	public void setRemarkOld(String remarkOld) {
		this.remarkOld = remarkOld;
	}

	public String getSpeciesOld() {
		return speciesOld;
	}

	public void setSpeciesOld(String speciesOld) {
		this.speciesOld = speciesOld;
	}

	public int getStatoNew() {
		return statoNew;
	}

	public void setStatoNew(int statoNew) {
		this.statoNew = statoNew;
	}

	public Timestamp getDataInizioNew() {
		return dataInizioNew;
	}

	public void setDataInizioNew(Timestamp dataInizioNew) {
		this.dataInizioNew = dataInizioNew;
	}

	public Timestamp getDataFineNew() {
		return dataFineNew;
	}

	public void setDataFineNew(Timestamp dataFineNew) {
		this.dataFineNew = dataFineNew;
	}

	public String getTipoAutorizzazioneNew() {
		return tipoAutorizzazioneNew;
	}

	public void setTipoAutorizzazioneNew(String tipoAutorizzazioneNew) {
		this.tipoAutorizzazioneNew = tipoAutorizzazioneNew;
	}

	public String getImballaggioNew() {
		return imballaggioNew;
	}

	public void setImballaggioNew(String imballaggioNew) {
		this.imballaggioNew = imballaggioNew;
	}

	public String getPaesiAbilitatiExportNew() {
		return paesiAbilitatiExportNew;
	}

	public void setPaesiAbilitatiExportNew(String paesiAbilitatiExportNew) {
		this.paesiAbilitatiExportNew = paesiAbilitatiExportNew;
	}

	public String getRemarkNew() {
		return remarkNew;
	}

	public void setRemarkNew(String remarkNew) {
		this.remarkNew = remarkNew;
	}

	public String getSpeciesNew() {
		return speciesNew;
	}

	public void setSpeciesNew(String speciesNew) {
		this.speciesNew = speciesNew;
	}

	public int getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(int idRecord) {
		this.idRecord = idRecord;
	}

	public int getIdImport() {
		return idImport;
	}

	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}


	

}
