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
import java.text.SimpleDateFormat;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;

/**
 * 
 * @author Veronica Evento di trasferimento: l'asl di destinazione del
 *         proprietario non varia
 * 
 */

public class EventoTrasferimento extends Evento {

	public static final int idTipologiaDB = 16;
	public static final int idTipologiaDBOperatoreCommerciale = 33;

	public EventoTrasferimento() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataTrasferimento;
	private int idProprietario = -1;
	private int idDetentore = -1;
	private int idComuneProprietario = -1;

	private int idVecchioProprietario = -1;
	private int idVecchioDetentore = -1;

	private int idAslVecchioProprietario = -1;
	private int idAslNuovoProprietario = -1;

	private String luogo = "";

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

	public java.sql.Timestamp getDataTrasferimento() {
		return dataTrasferimento;
	}

	public void setDataTrasferimento(java.sql.Timestamp dataTrasferimento) {
		this.dataTrasferimento = dataTrasferimento;
	}

	public void setDataTrasferimento(String dataTrasferimento) {
		this.dataTrasferimento = DateUtils.parseDateStringNew(dataTrasferimento, "dd/MM/yyyy");
	}

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}

	public void setIdProprietario(String idProprietario) {
		this.idProprietario = new Integer(idProprietario).intValue();
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public int getIdComuneProprietario() {
		return idComuneProprietario;
	}

	public void setIdComuneProprietario(int idComuneProprietario) {
		this.idComuneProprietario = idComuneProprietario;
	}

	public int getIdVecchioProprietario() {
		return idVecchioProprietario;
	}

	public void setIdVecchioProprietario(int idVecchioProprietario) {
		this.idVecchioProprietario = idVecchioProprietario;
	}

	public int getIdVecchioDetentore() {
		return idVecchioDetentore;
	}

	public void setIdVecchioDetentore(int idVecchioDetentore) {
		this.idVecchioDetentore = idVecchioDetentore;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public int getIdAslVecchioProprietario() {
		return idAslVecchioProprietario;
	}

	public void setIdAslVecchioProprietario(int idAslVecchioProprietario) {
		this.idAslVecchioProprietario = idAslVecchioProprietario;
	}

	public int getIdAslNuovoProprietario() {
		return idAslNuovoProprietario;
	}

	public void setIdAslNuovoProprietario(int idAslNuovoProprietario) {
		this.idAslNuovoProprietario = idAslNuovoProprietario;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_trasferimento_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_trasferimento("
					+ "id_evento, data_trasferimento, id_nuovo_proprietario, id_vecchio_proprietario  ");

			if (idDetentore > -1) {
				sql.append(",id_nuovo_detentore ");
			}

			if (idVecchioDetentore > -1) {
				sql.append(",id_vecchio_detentore ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",id_asl_vecchio_proprietario ");
			}

			if (idAslNuovoProprietario > -1) {
				sql.append(",id_asl_nuovo_proprietario ");
			}

			sql.append(")VALUES(?,?,?,?");

			if (idDetentore > -1) {
				sql.append(",? ");
			}

			if (idVecchioDetentore > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",? ");
			}

			if (idAslNuovoProprietario > -1) {
				sql.append(",? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataTrasferimento);

			pst.setInt(++i, idProprietario);

			pst.setInt(++i, idVecchioProprietario);

			if (idDetentore > -1) {
				pst.setInt(++i, idDetentore);
			}

			if (idVecchioDetentore > -1) {
				pst.setInt(++i, idVecchioDetentore);
			}

			if (idAslVecchioProprietario > -1) {
				pst.setInt(++i, idAslVecchioProprietario);
			}

			if (idAslNuovoProprietario > -1) {
				pst.setInt(++i, idAslNuovoProprietario);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_trasferimento_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoTrasferimento(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataTrasferimento = rs.getTimestamp("data_trasferimento");
		this.idProprietario = rs.getInt("id_nuovo_proprietario");
		this.idVecchioProprietario = rs.getInt("id_vecchio_proprietario");
		this.idDetentore = rs.getInt("id_nuovo_detentore");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore");
		this.idAslNuovoProprietario = rs.getInt("id_asl_nuovo_proprietario");
		this.idAslVecchioProprietario = rs.getInt("id_asl_vecchio_proprietario");

	}

	public EventoTrasferimento(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_trasferimento f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getVecchioProprietario() throws UnknownHostException {

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

			idOperatore = this.getIdVecchioProprietario();

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

	public Operatore getProprietario() throws UnknownHostException {

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

			idOperatore = this.getIdProprietario();

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

	public Operatore getVecchioDetentore() throws UnknownHostException {

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

	public EventoTrasferimento salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
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

			this.setIdVecchioProprietario(oldAnimale.getIdProprietario());

			Operatore soggettoAdded = new Operatore();
			soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdProprietario());

			// Detentore
			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
				int idDetentore = ((LineaProduttiva) (((Stabilimento) oldAnimale.getDetentore().getListaStabilimenti()
						.get(0)).getListaLineeProduttive()).get(0)).getId();
				this.setIdVecchioDetentore(idDetentore);

			}
			thisAnimale.setIdDetentore(this.getIdProprietario());
			thisAnimale.setIdProprietario(this.getIdProprietario());

			// Controllo se viene dal circuito commerciale
			if (oldAnimale.isFlagCircuitoCommerciale()) {
				Stabilimento stab = (Stabilimento) soggettoAdded.getListaStabilimenti().get(0);
				LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				if (lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale) {
					thisAnimale.setFlagCircuitoCommerciale(false);
					this.setIdTipologiaEvento(EventoTrasferimento.idTipologiaDB);
				} else {
					this.setIdTipologiaEvento(EventoTrasferimento.idTipologiaDBOperatoreCommerciale);
					// tipologiaRegistrazione =
					// EventoTrasferimento.idTipologiaDBOperatoreCommerciale;
				}
			} else {
				this.setIdTipologiaEvento(EventoTrasferimento.idTipologiaDB);
			}

			this.setIdAslVecchioProprietario(((Stabilimento) oldAnimale.getProprietario().getListaStabilimenti().get(0))
					.getIdAsl());

			this.setIdAslNuovoProprietario(((Stabilimento) soggettoAdded.getListaStabilimenti().get(0)).getIdAsl());

			// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
			// DETENTORE;
			// PER ORA LO IMPOSTO AL PROPRIETARIO
			this.setIdDetentore(this.getIdProprietario());
			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoTrasferimento build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
