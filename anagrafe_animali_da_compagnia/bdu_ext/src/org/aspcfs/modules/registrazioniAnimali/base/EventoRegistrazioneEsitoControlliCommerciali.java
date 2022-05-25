/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoRegistrazioneEsitoControlliCommerciali extends Evento {

	public static final int idTipologiaDB = 26;

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataInserimentoControlli;

	private boolean flagPresenzaEsitoControlloIdentita = false;
	private java.sql.Timestamp dataEsitoControlloIdentita = null;
	private int idEsitoControlloIdentita = -1;

	private boolean flagPresenzaEsitoControlloDocumentale = false;
	private java.sql.Timestamp dataEsitoControlloDocumentale = null;
	private int idEsitoControlloDocumentale = -1;

	private boolean flagPresenzaEsitoControlloFisico = false;
	private java.sql.Timestamp dataEsitoControlloFisico = null;
	private int idEsitoControlloFisico = -1;

	private boolean flagPresenzaEsitoControlloLaboratorio = false;
	private java.sql.Timestamp dataEsitoControlloLaboratorio = null;
	private int idEsitoControlloLaboratorio = -1;

	private int idDecisioneFinale = -1;

	public EventoRegistrazioneEsitoControlliCommerciali() {

		super();
		super.setIdTipologiaEvento(idTipologiaDB);
		// TODO Auto-generated constructor stub
	}

	public java.sql.Timestamp getDataInserimentoControlli() {
		return dataInserimentoControlli;
	}

	public void setDataInserimentoControlli(
			java.sql.Timestamp dataInserimentoControlli) {
		this.dataInserimentoControlli = dataInserimentoControlli;
	}
	
	public void setDataInserimentoControlli(
			String dataEsitoControlli) {
		this.dataInserimentoControlli = DateUtils.parseDateStringNew(
				dataEsitoControlli, "dd/MM/yyyy");
	}

	public boolean isFlagPresenzaEsitoControlloIdentita() {
		return flagPresenzaEsitoControlloIdentita;
	}

	public void setFlagPresenzaEsitoControlloIdentita(
			boolean flagPresenzaEsitoControlloIdentita) {
		this.flagPresenzaEsitoControlloIdentita = flagPresenzaEsitoControlloIdentita;
	}

	public java.sql.Timestamp getDataEsitoControlloIdentita() {
		return dataEsitoControlloIdentita;
	}

	public void setDataEsitoControlloIdentita(
			java.sql.Timestamp dataEsitoControlloIdentita) {
		this.dataEsitoControlloIdentita = dataEsitoControlloIdentita;
	}

	public int getIdEsitoControlloIdentita() {
		return idEsitoControlloIdentita;
	}

	public void setIdEsitoControlloIdentita(int idEsitoControlloIdentita) {
		this.idEsitoControlloIdentita = idEsitoControlloIdentita;
	}

	public boolean isFlagPresenzaEsitoControlloDocumentale() {
		return flagPresenzaEsitoControlloDocumentale;
	}

	public void setFlagPresenzaEsitoControlloDocumentale(
			boolean flagPresenzaEsitoControlloDocumentale) {
		this.flagPresenzaEsitoControlloDocumentale = flagPresenzaEsitoControlloDocumentale;
	}

	public java.sql.Timestamp getDataEsitoControlloDocumentale() {
		return dataEsitoControlloDocumentale;
	}

	public void setDataEsitoControlloDocumentale(
			java.sql.Timestamp dataEsitoControlloDocumentale) {
		this.dataEsitoControlloDocumentale = dataEsitoControlloDocumentale;
	}

	public int getIdEsitoControlloDocumentale() {
		return idEsitoControlloDocumentale;
	}

	public void setIdEsitoControlloDocumentale(int idEsitoControlloDocumentale) {
		this.idEsitoControlloDocumentale = idEsitoControlloDocumentale;
	}

	public boolean isFlagPresenzaEsitoControlloFisico() {
		return flagPresenzaEsitoControlloFisico;
	}

	public void setFlagPresenzaEsitoControlloFisico(
			boolean flagPresenzaEsitoControlloFisico) {
		this.flagPresenzaEsitoControlloFisico = flagPresenzaEsitoControlloFisico;
	}

	public java.sql.Timestamp getDataEsitoControlloFisico() {
		return dataEsitoControlloFisico;
	}

	public void setDataEsitoControlloFisico(
			java.sql.Timestamp dataEsitoControlloFisico) {
		this.dataEsitoControlloFisico = dataEsitoControlloFisico;
	}

	public int getIdEsitoControlloFisico() {
		return idEsitoControlloFisico;
	}

	public void setIdEsitoControlloFisico(int idEsitoControlloFisico) {
		this.idEsitoControlloFisico = idEsitoControlloFisico;
	}

	public boolean isFlagPresenzaEsitoControlloLaboratorio() {
		return flagPresenzaEsitoControlloLaboratorio;
	}

	public void setFlagPresenzaEsitoControlloLaboratorio(
			boolean flagPresenzaEsitoControlloLaboratorio) {
		this.flagPresenzaEsitoControlloLaboratorio = flagPresenzaEsitoControlloLaboratorio;
	}

	public java.sql.Timestamp getDataEsitoControlloLaboratorio() {
		return dataEsitoControlloLaboratorio;
	}

	public void setDataEsitoControlloLaboratorio(
			java.sql.Timestamp dataEsitoControlloLaboratorio) {
		this.dataEsitoControlloLaboratorio = dataEsitoControlloLaboratorio;
	}

	public int getIdEsitoControlloLaboratorio() {
		return idEsitoControlloLaboratorio;
	}

	public void setIdEsitoControlloLaboratorio(int idEsitoControlloLaboratorio) {
		this.idEsitoControlloLaboratorio = idEsitoControlloLaboratorio;
	}

	public int getIdDecisioneFinale() {
		return idDecisioneFinale;
	}

	public void setIdDecisioneFinale(int idDecisioneFinale) {
		this.idDecisioneFinale = idDecisioneFinale;
	}

	public void setIdDecisioneFinale(String idDecisioneFinale) {
		this.idDecisioneFinale = new Integer(idDecisioneFinale).intValue();
	}

	public void setFlagPresenzaEsitoControlloIdentita(
			String flagPresenzaEsitoControlloIdentita) {
		this.flagPresenzaEsitoControlloIdentita = DatabaseUtils
				.parseBoolean(flagPresenzaEsitoControlloIdentita);
	}

	public void setDataEsitoControlloIdentita(String dataEsitoControlloIdentita) {
		this.dataEsitoControlloIdentita = DateUtils.parseDateStringNew(
				dataEsitoControlloIdentita, "dd/MM/yyyy");
	}

	public void setIdEsitoControlloIdentita(String idEsitoControlloIdentita) {
		this.idEsitoControlloIdentita = new Integer(idEsitoControlloIdentita)
				.intValue();
	}

	public void setFlagPresenzaEsitoControlloDocumentale(
			String flagPresenzaControlloDocumentale) {
		this.flagPresenzaEsitoControlloDocumentale = DatabaseUtils
				.parseBoolean(flagPresenzaControlloDocumentale);
	}

	public void setDataEsitoControlloDocumentale(
			String dataEsitoControlloDocumentale) {
		this.dataEsitoControlloDocumentale = DateUtils.parseDateStringNew(
				dataEsitoControlloDocumentale, "dd/MM/yyyy");
	}

	public void setIdEsitoControlloDocumentale(
			String idEsitoControlloDocumentale) {
		this.idEsitoControlloDocumentale = new Integer(
				idEsitoControlloDocumentale).intValue();
	}

	public void setFlagPresenzaEsitoControlloFisico(
			String flagPresenzaControlloFisico) {
		this.flagPresenzaEsitoControlloFisico = DatabaseUtils
				.parseBoolean(flagPresenzaControlloFisico);
	}

	public void setDataEsitoControlloFisico(String dataEsitoControlloFisico) {
		this.dataEsitoControlloFisico = DateUtils.parseDateStringNew(
				dataEsitoControlloFisico, "dd/MM/yyyy");
	}

	public void setIdEsitoControlloFisico(String idEsitoControlloFisico) {
		this.idEsitoControlloFisico = new Integer(idEsitoControlloFisico)
				.intValue();
	}

	public void setFlagPresenzaEsitoControlloLaboratorio(
			String flagPresenzaControlloLaboratorio) {
		this.flagPresenzaEsitoControlloLaboratorio = DatabaseUtils
				.parseBoolean(flagPresenzaControlloLaboratorio);
	}

	public void setDataEsitoControlloLaboratorio(
			String dataEsitoControlloLaboratorio) {
		this.dataEsitoControlloLaboratorio = DateUtils.parseDateStringNew(
				dataEsitoControlloLaboratorio, "dd/MM/yyyy");
	}

	public void setIdEsitoControlloLaboratorio(
			String idEsitoControlloLaboratorio) {
		this.idEsitoControlloLaboratorio = new Integer(
				idEsitoControlloLaboratorio).intValue();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			
		

			super.insert(db);
			idEvento = super.getIdEvento();

			

			id = DatabaseUtils.getNextSeq(db,
					"evento_inserimento_esiti_controlli_commerciali_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_inserimento_esiti_controlli_commerciali("
							+ "id_evento, data_registrazione_esiti");

			if (flagPresenzaEsitoControlloIdentita) {
				sql.append(", flag_presenza_esito_controllo_identita");
			}

			if (flagPresenzaEsitoControlloDocumentale) {
				sql.append(", flag_presenza_esito_controllo_documentale");
			}

			if (flagPresenzaEsitoControlloFisico) {
				sql.append(", flag_presenza_esito_controllo_fisico");
			}

			if (flagPresenzaEsitoControlloLaboratorio) {
				sql.append(", flag_presenza_esito_controllo_laboratorio");
			}

			if (dataEsitoControlloIdentita != null) {
				sql.append(", data_esito_controllo_identita");
			}

			if (dataEsitoControlloDocumentale != null) {
				sql.append(", data_esito_controllo_documentale");
			}
			if (dataEsitoControlloFisico != null) {
				sql.append(", data_esito_controllo_fisico");
			}
			if (dataEsitoControlloLaboratorio != null) {
				sql.append(", data_esito_controllo_laboratorio");
			}

			if (idEsitoControlloIdentita > -1) {
				sql.append(", id_esito_controllo_identita");
			}

			if (idEsitoControlloDocumentale > -1) {
				sql.append(", id_esito_controllo_documentale");
			}

			if (idEsitoControlloFisico > -1) {
				sql.append(", id_esito_controllo_fisico");
			}
			if (idEsitoControlloLaboratorio > -1) {
				sql.append(", id_esito_controllo_laboratorio");
			}
			if (idDecisioneFinale > -1) {
				sql.append(", id_decisione_finale");
			}

			sql.append(")VALUES(?,?");

			if (flagPresenzaEsitoControlloIdentita) {
				sql.append(", ?");
			}

			if (flagPresenzaEsitoControlloDocumentale) {
				sql.append(", ?");
			}

			if (flagPresenzaEsitoControlloFisico) {
				sql.append(", ?");
			}

			if (flagPresenzaEsitoControlloLaboratorio) {
				sql.append(", ?");
			}

			if (dataEsitoControlloIdentita != null) {
				sql.append(", ?");
			}

			if (dataEsitoControlloDocumentale != null) {
				sql.append(", ?");
			}
			if (dataEsitoControlloFisico != null) {
				sql.append(", ?");
			}
			if (dataEsitoControlloLaboratorio != null) {
				sql.append(", ?");
			}

			if (idEsitoControlloIdentita > -1) {
				sql.append(", ?");
			}

			if (idEsitoControlloDocumentale > -1) {
				sql.append(", ?");
			}

			if (idEsitoControlloFisico > -1) {
				sql.append(", ?");
			}
			if (idEsitoControlloLaboratorio > -1) {
				sql.append(", ?");
			}
			if (idDecisioneFinale > -1) {
				sql.append(", ?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataInserimentoControlli);

			if (flagPresenzaEsitoControlloIdentita) {
				pst.setBoolean(++i, flagPresenzaEsitoControlloIdentita);
			}

			if (flagPresenzaEsitoControlloDocumentale) {
				pst.setBoolean(++i, flagPresenzaEsitoControlloDocumentale);
			}

			if (flagPresenzaEsitoControlloFisico) {
				pst.setBoolean(++i, flagPresenzaEsitoControlloFisico);
			}

			if (flagPresenzaEsitoControlloLaboratorio) {
				pst.setBoolean(++i, flagPresenzaEsitoControlloLaboratorio);
			}

			if (dataEsitoControlloIdentita != null) {
				pst.setTimestamp(++i, dataEsitoControlloIdentita);
			}

			if (dataEsitoControlloDocumentale != null) {
				pst.setTimestamp(++i, dataEsitoControlloDocumentale);
			}
			if (dataEsitoControlloFisico != null) {
				pst.setTimestamp(++i, dataEsitoControlloFisico);
			}
			if (dataEsitoControlloLaboratorio != null) {
				pst.setTimestamp(++i, dataEsitoControlloLaboratorio);
			}

			if (idEsitoControlloIdentita > -1) {
				pst.setInt(++i, idEsitoControlloIdentita);
			}

			if (idEsitoControlloDocumentale > -1) {
				pst.setInt(++i, idEsitoControlloDocumentale);
			}

			if (idEsitoControlloFisico > -1) {
				pst.setInt(++i, idEsitoControlloFisico);
			}
			if (idEsitoControlloLaboratorio > -1) {
				pst.setInt(++i, idEsitoControlloLaboratorio);
			}
			if (idDecisioneFinale > -1) {
				pst.setInt(++i, idDecisioneFinale);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils
					.getCurrVal(
							db,
							"evento_inserimento_esiti_controlli_commerciali_id_seq",
							id);

		

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoRegistrazioneEsitoControlliCommerciali(ResultSet rs)
			throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");

		this.dataInserimentoControlli = rs
				.getTimestamp("data_registrazione_esiti");
		this.flagPresenzaEsitoControlloIdentita = rs
				.getBoolean("flag_presenza_esito_controllo_identita");
		this.flagPresenzaEsitoControlloDocumentale = rs
				.getBoolean("flag_presenza_esito_controllo_documentale");
		this.flagPresenzaEsitoControlloFisico = rs
				.getBoolean("flag_presenza_esito_controllo_fisico");
		this.flagPresenzaEsitoControlloLaboratorio = rs
				.getBoolean("flag_presenza_esito_controllo_laboratorio");

		this.dataEsitoControlloIdentita = rs
				.getTimestamp("data_esito_controllo_identita");
		this.dataEsitoControlloDocumentale = rs
				.getTimestamp("data_esito_controllo_documentale");
		this.dataEsitoControlloFisico = rs
				.getTimestamp("data_esito_controllo_fisico");
		this.dataEsitoControlloLaboratorio = rs
				.getTimestamp("data_esito_controllo_laboratorio");

		this.idEsitoControlloIdentita = rs
				.getInt("id_esito_controllo_identita");
		this.idEsitoControlloDocumentale = rs
				.getInt("id_esito_controllo_documentale");
		this.idEsitoControlloFisico = rs.getInt("id_esito_controllo_fisico");
		this.idEsitoControlloLaboratorio = rs
				.getInt("id_esito_controllo_laboratorio");

		this.idDecisioneFinale = rs.getInt("id_decisione_finale");

	}

	public int setIdTipologiaDbByDecisione() {

		switch (idDecisioneFinale) {
		case 1: { // Libera commercializzazione
			return 26;
		}
		case 2: { // Blocco
			return 28;
		}
		case 3: { // Respingimento
			return 29;
		}

		default: { // Default Libera Commercializzazione
			return 26;
		}
		}
	}

	public EventoRegistrazioneEsitoControlliCommerciali(Connection db,
			int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_esiti_controlli_commerciali f on (e.id_evento = f.id_evento) where e.id_evento = ?");
		pst.setInt(1, idEventoPadre);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}
	
	
	public EventoRegistrazioneEsitoControlliCommerciali salvaRegistrazione(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			
			Animale oldAnimale = new Animale(db, this.getIdAnimale());

			switch (this.getSpecieAnimaleId()) {
			case Cane.idSpecie:
				thisAnimale = new Cane(db, this.getIdAnimale());
				break;
			case Gatto.idSpecie:
				thisAnimale = new Gatto(db, this.getIdAnimale());
				break;
			case Furetto.idSpecie:
				thisAnimale = new Furetto(db, this.getIdAnimale());
				break;
			default:
				break;
			}



			this.insert(db);
			thisAnimale.updateControlliCommerciali(db, this,
					true);
			thisAnimale.isFlagPresenzaEsitoControlloDocumentale();
			this.setIdTipologiaEvento(this
					.setIdTipologiaDbByDecisione())  ;

			// Se liberalizzo devo togliere il flag vincolo commerciale
			// dalla tabella animale
			if (this.getIdDecisioneFinale() == 1) {
				thisAnimale.setFlagVincoloCommerciale(false);
				PartitaCommerciale partita = new PartitaCommerciale(db,
						thisAnimale.getIdPartitaCircuitoCommerciale());
				partita.rimuoviVincolo(db, thisAnimale.getMicrochip());
			}
		
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}

	
	
	public EventoRegistrazioneEsitoControlliCommerciali build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}
}
