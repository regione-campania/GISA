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

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;

public class EventoRestituzioneAProprietario extends Evento {

	public final static int idTipologiaDB = 45;
	public final static int idTipologiaRestituzioneACanileDB = 56; // Restituzione
																	// a canile
																	// origine
																	// per
																	// randagi
																	// ritrovati
																	// senza
																	// denuncia

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataRestituzione;
	private int idDetentore = -1;
	private int idVecchioDetentore = -1;

	private String denuncia;

	private boolean flagContestualeRitrovamento = false;
	private int idEventoRitrovamento = -1; // Evento ritrovamento o ritrovamento
											// non denunciato
	private int idTipologiaEventoRitrovamento = -1;
	private boolean flagRestituzioneCanileOrigine = false;

	public java.sql.Timestamp getDataRestituzione() {
		return dataRestituzione;
	}

	public void setDataRestituzione(java.sql.Timestamp dataRestituzione) {
		this.dataRestituzione = dataRestituzione;
	}

	public void setDataRestituzione(String data) {
		this.dataRestituzione = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
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

	public int getIdVecchioDetentore() {
		return idVecchioDetentore;
	}

	public void setIdVecchioDetentore(int idVecchioDetentore) {
		this.idVecchioDetentore = idVecchioDetentore;
	}

	public void setIdVecchioDetentore(String idVecchioDetentore) {
		this.idVecchioDetentore = new Integer(idVecchioDetentore).intValue();
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

	public boolean isFlagContestualeRitrovamento() {
		return flagContestualeRitrovamento;
	}

	public void setFlagContestualeRitrovamento(boolean flagContestualeRitrovamento) {
		this.flagContestualeRitrovamento = flagContestualeRitrovamento;
	}

	public int getIdEventoRitrovamento() {
		return idEventoRitrovamento;
	}

	public void setIdEventoRitrovamento(int idEventoRitrovamento) {
		this.idEventoRitrovamento = idEventoRitrovamento;
	}

	public int getIdTipologiaEventoRitrovamento() {
		return idTipologiaEventoRitrovamento;
	}

	public void setIdTipologiaEventoRitrovamento(int idTipologiaEventoRitrovamento) {
		this.idTipologiaEventoRitrovamento = idTipologiaEventoRitrovamento;
	}

	public boolean isFlagRestituzioneCanileOrigine() {
		return flagRestituzioneCanileOrigine;
	}

	public void setFlagRestituzioneCanileOrigine(boolean flagRestituzioneCanileOrigine) {
		this.flagRestituzioneCanileOrigine = flagRestituzioneCanileOrigine;
	}

	public void setFlagRestituzioneCanileOrigine(String flagRestituzioneCanileOrigine) {
		this.flagRestituzioneCanileOrigine = DatabaseUtils.parseBoolean(flagRestituzioneCanileOrigine);
	}

	public EventoRestituzioneAProprietario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			
			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_restituzione_a_proprietario_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_restituzione_a_proprietario("
					+ "id_evento, data_restituzione, id_detentore, denuncia, flag_contestuale_ritrovamento, id_evento_ritrovamento, id_tipologia_evento_ritrovamento, flag_restituzione_canile_origine ");

			if (idVecchioDetentore > -1) {
				sql.append(", id_detentore_old ");
			}

			sql.append(")VALUES(?,?,?,?,?,?,?,?");

			if (idVecchioDetentore > -1) {
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataRestituzione);

			pst.setInt(++i, idDetentore);
			pst.setString(++i, denuncia);
			pst.setBoolean(++i, flagContestualeRitrovamento);
			pst.setInt(++i, idEventoRitrovamento);
			pst.setInt(++i, idTipologiaEventoRitrovamento);
			pst.setBoolean(++i, flagRestituzioneCanileOrigine);

			if (idVecchioDetentore > -1) {
				pst.setInt(++i, idVecchioDetentore);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_restituzione_a_proprietario_id_seq", id);


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public EventoRestituzioneAProprietario(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataRestituzione = rs.getTimestamp("data_restituzione");
		this.idDetentore = rs.getInt("id_detentore");
		this.idVecchioDetentore = rs.getInt("id_detentore_old");
		this.denuncia = rs.getString("denuncia");
		this.flagContestualeRitrovamento = rs.getBoolean("flag_contestuale_ritrovamento");
		this.idTipologiaEventoRitrovamento = rs.getInt("id_tipologia_evento_ritrovamento");
		this.idEventoRitrovamento = rs.getInt("id_evento_ritrovamento");
		this.flagRestituzioneCanileOrigine = rs.getBoolean("flag_restituzione_canile_origine");

	}

	public EventoRestituzioneAProprietario(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_restituzione_a_proprietario f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getOldDetentore() throws UnknownHostException {

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

			idOperatore = this.getIdVecchioDetentore();

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
			// conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public Operatore getDetentore() throws UnknownHostException {

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

			idOperatore = this.getIdDetentore();

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
			// conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public void setDenuncia(String denuncia) {
		this.denuncia = denuncia;
	}

	public String getDenuncia() {
		return denuncia;
	}

	
	public void updateFlagAperto(Connection con, int idEvento, boolean flag) throws SQLException {

		PreparedStatement pst = con
				.prepareStatement("Update evento_ritrovamento_non_denunciato set flag_ritrovamento_aperto = " + flag
						+ " where id_evento = " + idEvento);
		int resultCount = pst.executeUpdate();
		pst.close();

	}

	public EventoRestituzioneAProprietario salvaRegistrazione(int userId, int userRole, int userAsl,
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

			if (this.getIdTipologiaEvento() == idTipologiaDB) {

				// Cerco se c'è un ritrovamento non denunciato aperto per
				// chiuderlo e per verificare che non si tratta di una
				// restituzione dopo un affidamento a canile in seguito a un
				// ritrovamento
				// (dopo denuncia di furto o smarrimento)
				EventoRitrovamentoNonDenunciato ritrovamento = new EventoRitrovamentoNonDenunciato();
				ritrovamento.GetRitrovamentoNonDenunciatoApertoByIdAnimale(db, thisAnimale.getIdAnimale());
				if (ritrovamento != null && ritrovamento.getIdEvento() > 0) {
					ritrovamento.setFlagAperto(false);
					ritrovamento.updateFlagAperto(db);

					if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
						this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
						// restituz.setIdDetentore(ritrovamento
						// .getIdDetentoreOldNd());
					} else {
						this.setIdVecchioDetentore(ritrovamento.getIdDetentore());

					}

					this.setIdDetentore(ritrovamento.getIdDetentoreOldNd());

					thisAnimale.setIdDetentore(ritrovamento.getIdDetentoreOldNd());
				} else {
					// Restituzione dopo ritrovamento con affidamento a
					// canile

					EventoRitrovamento ritrov = new EventoRitrovamento();
					ritrov.GetRitrovamentoApertoByIdAnimale(db, thisAnimale.getIdAnimale());
					if (ritrov != null && ritrov.getId() > 0) {
						ritrov.setFlagAperto(false);
						ritrov.updateFlagAperto(db);
						this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
						this.setIdDetentore(ritrov.getIdDetentoreOld());
						thisAnimale.setIdDetentore(ritrov.getIdDetentoreOld());
						thisAnimale.setFlagDetenutoInCanileDopoRitrovamento(false);
					}
				}
				// thisAnimale.update(db);
				this.insert(db);
			} else if (this.getIdTipologiaEvento() == idTipologiaRestituzioneACanileDB) {

				// Cerco se c'è un ritrovamento non denunciato aperto per
				// chiuderlo e per verificare che non si tratta di una
				// restituzione dopo un affidamento a canile in seguito a un
				// ritrovamento
				// (dopo denuncia di furto o smarrimento)
				EventoRitrovamentoNonDenunciato ritrovamento = new EventoRitrovamentoNonDenunciato();
				ritrovamento.GetRitrovamentoNonDenunciatoApertoByIdAnimale(db, thisAnimale.getIdAnimale());
				if (ritrovamento != null && ritrovamento.getIdEvento() > 0) {
					ritrovamento.setFlagAperto(false);
					ritrovamento.updateFlagAperto(db);

					if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
						this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
						// restituz.setIdDetentore(ritrovamento
						// .getIdDetentoreOldNd());
					} else {
						this.setIdVecchioDetentore(ritrovamento.getIdDetentore());

					}

					this.setIdDetentore(ritrovamento.getIdDetentoreOldNd());

					thisAnimale.setIdDetentore(ritrovamento.getIdDetentoreOldNd());
				} else {
					// Restituzione dopo ritrovamento con affidamento a
					// canile

					EventoRitrovamento ritrov = new EventoRitrovamento();
					ritrov.GetRitrovamentoApertoByIdAnimale(db, thisAnimale.getIdAnimale());
					if (ritrov != null && ritrov.getId() > 0) {
						ritrov.setFlagAperto(false);
						ritrov.updateFlagAperto(db);
						this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
						this.setIdDetentore(ritrov.getIdDetentoreOld());
						thisAnimale.setIdDetentore(ritrov.getIdDetentoreOld());
						thisAnimale.setFlagDetenutoInCanileDopoRitrovamento(false);
					}

				}
				// thisAnimale.update(db);
				this.insert(db);

			}

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoRestituzioneAProprietario build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
