/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class EventoRitrovamentoNonDenunciato extends Evento {

	public static final int idTipologiaDB = 41;

	private int id = -1;
	private java.sql.Timestamp dataRitrovamentoNd;
	private int idComuneRitrovamentoNd;
	private String luogoRitrovamentoNd;
	private int idDetentore = -1;
	private int idDetentoreOldNd = -1;
	private int idEvento;
	private boolean flagAperto;

	public final static int detentoreOld = 3;
	public final static int detentoreNew = 4;

	public EventoRitrovamentoNonDenunciato() {
		super();
		// TODO Auto-generated constructor stub
	}

	public java.sql.Timestamp getDataRitrovamentoNd() {
		return dataRitrovamentoNd;
	}

	public void setDataRitrovamentoNd(java.sql.Timestamp dataRitrovamento) {
		this.dataRitrovamentoNd = dataRitrovamento;
	}

	public void setDataRitrovamentoNd(String dataRitrovamento) {
		this.dataRitrovamentoNd = DateUtils.parseDateStringNew(dataRitrovamento, "dd/MM/yyyy");
	}

	public String getLuogoRitrovamentoNd() {
		return luogoRitrovamentoNd;
	}

	public void setLuogoRitrovamentoNd(String luogoRitrovamento) {
		this.luogoRitrovamentoNd = luogoRitrovamento;
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}

	public int getIdDetentoreOldNd() {
		return idDetentoreOldNd;
	}

	public void setIdDetentoreOldNd(int idDetentoreOld) {
		this.idDetentoreOldNd = idDetentoreOld;
	}

	public void setIdDetentoreOldNd(String idDetentoreOld) {
		this.idDetentoreOldNd = new Integer(idDetentoreOld).intValue();
	}

	public int getIdComuneRitrovamentoNd() {
		return idComuneRitrovamentoNd;
	}

	public void setIdComuneRitrovamentoNd(int idComuneRitrovamento) {
		this.idComuneRitrovamentoNd = idComuneRitrovamento;
	}

	public void setIdComuneRitrovamentoNd(String idComuneRitrovamento) {
		this.idComuneRitrovamentoNd = new Integer(idComuneRitrovamento).intValue();
	}

	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {
			
			
			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_ritrovamento_non_denunciato_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_ritrovamento_non_denunciato(id_evento, data_ritrovamento_nd, flag_ritrovamento_aperto ");

			if (luogoRitrovamentoNd != null && !"".equals(luogoRitrovamentoNd)) {
				sql.append(", luogo_ritrovamento_nd");
			}

			if (idComuneRitrovamentoNd > -1) {
				sql.append(", comune_ritrovamento_nd");
			}

			if (idDetentore > -1) {
				sql.append(",id_detentore_dopo_ritrovamento_nd ");
			}

			if (idDetentoreOldNd > -1) {
				sql.append(",id_detentore_old_nd ");
			}

			sql.append(")");

			sql.append("VALUES(?,?,?");

			if (luogoRitrovamentoNd != null && !"".equals(luogoRitrovamentoNd)) {
				sql.append(",?");
			}

			if (idComuneRitrovamentoNd > -1) {
				sql.append(",?");
			}

			if (idDetentore > -1) {
				sql.append(",?");
			}

			if (idDetentoreOldNd > -1) {
				sql.append(",?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataRitrovamentoNd);

			pst.setBoolean(++i, flagAperto);

			if (luogoRitrovamentoNd != null && !"".equals(luogoRitrovamentoNd)) {
				pst.setString(++i, luogoRitrovamentoNd);
			}

			if (idComuneRitrovamentoNd > -1) {
				pst.setInt(++i, idComuneRitrovamentoNd);
			}

			if (idDetentore > -1) {
				pst.setInt(++i, idDetentore);
			}

			if (idDetentoreOldNd > -1) {
				pst.setInt(++i, idDetentoreOldNd);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_ritrovamento_non_denunciato_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoRitrovamentoNonDenunciato(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataRitrovamentoNd = rs.getTimestamp("data_ritrovamento_nd");
		this.idComuneRitrovamentoNd = rs.getInt("comune_ritrovamento_nd");
		this.luogoRitrovamentoNd = rs.getString("luogo_ritrovamento_nd");
		this.idDetentore = rs.getInt("id_detentore_dopo_ritrovamento_nd");

		this.idDetentoreOldNd = rs.getInt("id_detentore_old_nd");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public EventoRitrovamentoNonDenunciato(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_ritrovamento_non_denunciato f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getIdProprietario(int tipoSoggetto) throws UnknownHostException {
		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {

			conn = GestoreConnessioni.getConnection();

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			switch (tipoSoggetto) {

			case detentoreNew: {
				idOperatore = this.getIdDetentore();
				break;

			}
			case detentoreOld: {
				idOperatore = this.getIdDetentoreOldNd();
				break;

			}

			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public void updateFlagAperto(Connection con) throws SQLException {
		
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("Update evento_ritrovamento_non_denunciato set flag_ritrovamento_aperto = "
					+ flagAperto + " where id_evento = " + this.getIdEvento());

			int resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		}
	}

	public void GetRitrovamentoNonDenunciatoApertoByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("SELECT *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento FROM evento_ritrovamento_non_denunciato rit_nd left join evento e on e.id_evento = rit_nd.id_evento where e.id_animale = ? AND rit_nd.flag_ritrovamento_aperto=true AND e.data_cancellazione is null ");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	}

	public void setFlagAperto(boolean flagAperto) {
		this.flagAperto = flagAperto;
	}

	public boolean isFlagAperto() {
		return flagAperto;
	}

	public EventoRitrovamentoNonDenunciato salvaRegistrazione(int userId, int userRole, int userAsl,
			Animale thisAnimale, Connection db) throws Exception {
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

			EventoRestituzioneAProprietario restituzione = null;

			// recupero vecchio animale per prendere detentore vecchio

			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0)
				this.setIdDetentoreOldNd(oldAnimale.getIdDetentore());

			// Detentore
			if (this.getIdDetentore() > -1) { // Ho scelto
				// unnuovo
				// detentore
				thisAnimale.setIdDetentore(this.getIdDetentore());
				this.setFlagAperto(true);
			} else {
				this.setIdDetentore(oldAnimale.getIdDetentore());
				thisAnimale.setIdDetentore(oldAnimale.getIdDetentore());
				this.setFlagAperto(false);

				restituzione = new EventoRestituzioneAProprietario();
				restituzione.setEnteredby(userId);
				restituzione.setModifiedby(userId);
				restituzione.setIdAnimale(this.getIdAnimale());
				restituzione.setMicrochip(this.getMicrochip());
				restituzione.setIdProprietarioCorrente(this.getIdProprietarioCorrente());
				restituzione.setIdDetentoreCorrente(this.getIdDetentoreCorrente());
				restituzione.setSpecieAnimaleId(this.getSpecieAnimaleId());
				restituzione.setIdAslRiferimento(this.getIdAslRiferimento());
				restituzione.setIdStatoOriginale(this.getIdStatoOriginale());

				restituzione.setIdTipologiaEvento(EventoRestituzioneAProprietario.idTipologiaDB);
				restituzione.setIdDetentore(oldAnimale.getIdDetentore());
				restituzione.setDataRestituzione(this.getDataRitrovamentoNd());
				restituzione.setIdVecchioDetentore(oldAnimale.getIdDetentore());

				this.setFlagFuoriDominioAsl(false); // ELIMINO FUORI DOMINIO ASL
													// IN QUANTO SI TRATTA DI
													// RESTITUZIONE CONTESTUALE
			}

			this.insert(db);

			if (restituzione != null) {
				restituzione.setIdEventoRitrovamento(this.getIdEvento());
				restituzione.setIdTipologiaEventoRitrovamento(this.idTipologiaDB);
				restituzione.setFlagContestualeRitrovamento(true);
				restituzione.insert(db);
			}

			thisAnimale.setFlagSmarrimento(false);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoRitrovamentoNonDenunciato build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
