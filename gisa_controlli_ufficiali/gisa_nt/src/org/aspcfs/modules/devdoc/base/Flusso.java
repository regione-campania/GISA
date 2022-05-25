/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.devdoc.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Flusso extends GenericBean {

	private int id;
	private int idFlusso;
	private int idPriorita;
	private Timestamp data;
	private Timestamp dataUltimaModifica;
	private String descrizione;
	private String tags;

	private Timestamp dataConsegna;
	private String noteConsegna;
	private int idUtenteConsegna;

	private Timestamp dataStandby;
	private String noteStandby;
	private int idUtenteStandby;

	private Timestamp dataAnnullamento;
	private String noteAnnullamento;
	private int idUtenteAnnullamento;

	private String noteAggiornamentiPriorita;

	private ModuloList moduli = new ModuloList();
	private ArrayList<FlussoNota> note = new ArrayList<FlussoNota>();

	public Flusso(ResultSet rs) throws SQLException {
		// TODO Auto-generated constructor stub
		loadResultSet(rs);
	}

	public Flusso(ResultSet rs, Connection db) throws SQLException {
		// TODO Auto-generated constructor stub
		loadResultSet(rs);
		ModuloList moduli = new ModuloList();
		moduli.buildList(db, idFlusso);
		this.setModuli(moduli);
	}

	public Flusso() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(int idFlusso) {
		this.id = idFlusso;
	}

	public int getIdPriorita() {
		return idPriorita;
	}

	public void setIdPriorita(int idPriorita) {
		this.idPriorita = idPriorita;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public Timestamp getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Timestamp dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public Timestamp getDataConsegna() {
		return dataConsegna;
	}

	public void setDataConsegna(Timestamp dataConsegna) {
		this.dataConsegna = dataConsegna;
	}

	public void setDataConsegna(String dataConsegna) {
		this.dataConsegna = DatabaseUtils.parseDateToTimestamp(dataConsegna);
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getNoteConsegna() {
		return noteConsegna;
	}

	public void setNoteConsegna(String noteConsegna) {
		this.noteConsegna = noteConsegna;
	}

	public ModuloList getModuli() {
		return moduli;
	}

	public void setModuli(ModuloList moduli) {
		this.moduli = moduli;
	}

	public void setIdFlusso(String idFlusso) {
		try {
			this.idFlusso = Integer.parseInt(idFlusso);
		} catch (Exception e) {
		}
	}

	public Timestamp getDataStandby() {
		return dataStandby;
	}

	public void setDataStandby(Timestamp dataStandby) {
		this.dataStandby = dataStandby;
	}

	public void setDataStandby(String dataStandby) {
		this.dataStandby = DatabaseUtils.parseDateToTimestamp(dataStandby);
	}

	public String getNoteStandby() {
		return noteStandby;
	}

	public void setNoteStandby(String noteStandby) {
		this.noteStandby = noteStandby;
	}

	public int getIdUtenteStandby() {
		return idUtenteStandby;
	}

	public void setIdUtenteStandby(int idUtenteStandby) {
		this.idUtenteStandby = idUtenteStandby;
	}

	public Timestamp getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Timestamp dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

	public void setDataAnnullamento(String dataAnnullamento) {
		this.dataAnnullamento = DatabaseUtils.parseDateToTimestamp(dataAnnullamento);
	}

	public String getNoteAnnullamento() {
		return noteAnnullamento;
	}

	public void setNoteAnnullamento(String noteAnnullamento) {
		this.noteAnnullamento = noteAnnullamento;
	}

	public int getIdUtenteAnnullamento() {
		return idUtenteAnnullamento;
	}

	public void setIdUtenteAnnullamento(int idUtenteAnnullamento) {
		this.idUtenteAnnullamento = idUtenteAnnullamento;
	}

	public String getNoteAggiornamentiPriorita() {
		return noteAggiornamentiPriorita;
	}

	public void setNoteAggiornamentiPriorita(String noteAggiornamentiPriorita) {
		this.noteAggiornamentiPriorita = noteAggiornamentiPriorita;
	}

	public ArrayList<FlussoNota> getNote() {
		return note;
	}

	public void setNote(ArrayList<FlussoNota> note) {
		this.note = note;
	}

	public void gestioneInserimento(Connection db) {
		Flusso flusso = new Flusso();
		flusso.queryRecord(db, idFlusso);
		if (flusso.getId() > 0) {
			this.id = flusso.getId();
			updateTags(db);
		} else
			insert(db);
	}

	public void insert(Connection db) {
		String insert = "INSERT INTO sviluppo_flussi (id,id_flusso, data, data_ultima_modifica, descrizione, tags) values ( ?, ?, now(), now(), ?,?)";
		PreparedStatement pst = null;
		try {
			this.id = DatabaseUtils.getNextSeq(db, "sviluppo_flussi_id_seq");

			int i = 0;
			pst = db.prepareStatement(insert);

			pst.setInt(++i, id);
			pst.setInt(++i, idFlusso);
			pst.setString(++i, descrizione);
			pst.setString(++i, tags);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void queryRecord(Connection db, int idFlusso) {
		String select = "select * from sviluppo_flussi where id_flusso =? and data_cancellazione is null";

		try {
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, idFlusso);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				loadResultSet(rs);
				ModuloList moduli = new ModuloList();
				moduli.buildList(db, idFlusso);
				this.setModuli(moduli);
				this.setNote(this.fetchNote(db));
			}
		} catch (SQLException e) {

		}

	}

	public void loadResultSet(ResultSet rs) throws SQLException {

		try {
			id = rs.getInt("id");
			idFlusso = rs.getInt("id_flusso");
			idPriorita = rs.getInt("id_priorita");
			data = rs.getTimestamp("data");
			dataUltimaModifica = rs.getTimestamp("data_ultima_modifica");
			descrizione = rs.getString("descrizione");
			tags = rs.getString("tags");
			dataConsegna = rs.getTimestamp("data_consegna");
			noteConsegna = rs.getString("note_consegna");
			idUtenteConsegna = rs.getInt("id_utente_consegna");
			dataStandby = rs.getTimestamp("data_standby");
			noteStandby = rs.getString("note_standby");
			idUtenteStandby = rs.getInt("id_utente_standby");
			dataAnnullamento = rs.getTimestamp("data_annullamento");
			noteAnnullamento = rs.getString("note_annullamento");
			idUtenteAnnullamento = rs.getInt("id_utente_annullamento");
			noteAggiornamentiPriorita = rs.getString("note_aggiornamenti_priorita");
		} catch (SQLException e) {
			throw e;
		}
	}

	public int getIdUtenteConsegna() {
		return idUtenteConsegna;
	}

	public void setIdUtenteConsegna(int idUtenteConsegna) {
		this.idUtenteConsegna = idUtenteConsegna;
	}

	public void updateTags(Connection db) {
		String update = "UPDATE sviluppo_flussi set tags = ?, data_ultima_modifica = now() where id = ? ";
		PreparedStatement pst = null;
		try {

			int i = 0;
			pst = db.prepareStatement(update);
			pst.setString(++i, tags);
			pst.setInt(++i, id);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isFlussoAperto(Connection db) throws SQLException {
		boolean aperto = true;

		PreparedStatement pst = db.prepareStatement("select data_consegna from sviluppo_flussi where id_flusso = "
				+ idFlusso);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			if (rs.getString("data_consegna") != null && !rs.getString("data_consegna").equals(""))
				aperto = false;
		}
		return aperto;
	}

	public void consegna(Connection db) {
		String update = "UPDATE sviluppo_flussi set data_consegna = ?, note_consegna = ?, id_utente_consegna = ?, data_ultima_modifica = now() where id = ? ";
		PreparedStatement pst = null;
		try {

			int i = 0;
			pst = db.prepareStatement(update);
			pst.setTimestamp(++i, dataConsegna);
			pst.setString(++i, noteConsegna);
			pst.setInt(++i, idUtenteConsegna);
			pst.setInt(++i, id);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void standby(Connection db) {
		String update = "UPDATE sviluppo_flussi set data_standby = ?, note_standby = ?, id_utente_standby = ?, data_ultima_modifica = now() where id = ? ";
		PreparedStatement pst = null;
		try {

			int i = 0;
			pst = db.prepareStatement(update);
			pst.setTimestamp(++i, dataStandby);
			pst.setString(++i, noteStandby);
			pst.setInt(++i, idUtenteStandby);
			pst.setInt(++i, id);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void annullamento(Connection db) {
		String update = "UPDATE sviluppo_flussi set data_annullamento = ?, note_annullamento = ?, id_utente_annullamento = ?, data_ultima_modifica = now() where id = ? ";
		PreparedStatement pst = null;
		try {

			int i = 0;
			pst = db.prepareStatement(update);
			pst.setTimestamp(++i, dataAnnullamento);
			pst.setString(++i, noteAnnullamento);
			pst.setInt(++i, idUtenteAnnullamento);
			pst.setInt(++i, id);
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePriorita(Connection db, int userId) throws SQLException {
		String query = "UPDATE sviluppo_flussi "
				     + "set id_priorita = ?,"
				     + "note_aggiornamenti_priorita = concat(note_aggiornamenti_priorita, 'user: ', ?, ' priorita: ', ?, ' data: ', now(), ';;')  "
				     + "where id_flusso = ?";
		PreparedStatement pst = null;
		try {
			pst = db.prepareStatement(query);
			pst.setInt(1, idPriorita);
			pst.setInt(2, userId);
			pst.setInt(3, idPriorita);
			pst.setInt(4, idFlusso);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasModulo(int tipoModulo) {
		ModuloList listaModuli = this.getModuli();
		for (int i = 0; i < listaModuli.size(); i++) {
			if (((Modulo) listaModuli.get(i)).getIdTipo() == tipoModulo)
				return true;
		}
		return false;
	}
	
	public boolean aggiungiNota(Connection db, int userId, String nota) throws SQLException {
		String query = "insert into sviluppo_note_flusso(nota, id_flusso, id_utente)"
					 + "values(?,?,?)";
		
		PreparedStatement pst = null;
		try {
			pst = db.prepareStatement(query);
			pst.setString(1, nota);
			pst.setInt(2, this.idFlusso);
			pst.setInt(3, userId);
			pst.execute();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public ArrayList<FlussoNota> fetchNote(Connection db) throws SQLException {
		String query = "select * from sviluppo_note_flusso where id_flusso = ? order by data_inserimento desc";
		PreparedStatement pst = null;
		ResultSet set = null;
		ArrayList<FlussoNota> note = new ArrayList<FlussoNota>();
		try {
			pst = db.prepareStatement(query);
			pst.setInt(1, this.idFlusso);
			set = pst.executeQuery();
			while(set.next()) {
				note.add(new FlussoNota(
							set.getInt("id"),
							set.getString("nota"),
							set.getInt("id_flusso"),
							set.getInt("id_utente"),
							set.getTimestamp("data_inserimento"),
							set.getTimestamp("data_cancellazione")
						)
				);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return note;
	}
}
