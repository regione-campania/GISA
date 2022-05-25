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
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;

public class EventoTrasferimentoFuoriStato extends Evento {

	public static final int idTipologiaDB = 39;

	public EventoTrasferimentoFuoriStato() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataTrasferimentoFuoriStato;
	private int idVecchioProprietario = -1;
	private int idVecchioDetentore = -1;
	private int idAslVecchioProprietario = -1;
	private int idAslVecchioDetentore = -1;
	private int idContinente = -1;
	private String luogo = "";
	private String datiProprietarioFuoriStato = "";

	public int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public java.sql.Timestamp getDataTrasferimentoFuoriStato() {
		return dataTrasferimentoFuoriStato;
	}

	public void setDataTrasferimentoFuoriStato(java.sql.Timestamp dataTrasferimentoFuoriStato) {
		this.dataTrasferimentoFuoriStato = dataTrasferimentoFuoriStato;
	}

	public int getIdContinente() {
		return idContinente;
	}

	public void setIdContinente(int idContinente) {
		this.idContinente = idContinente;
	}

	public void setIdContinente(String idContinente) {
		this.idContinente = new Integer(idContinente).intValue();
	}

	public String getDatiProprietarioFuoriStato() {
		return datiProprietarioFuoriStato;
	}

	public void setDatiProprietarioFuoriStato(String datiProprietarioFuoriStato) {
		this.datiProprietarioFuoriStato = datiProprietarioFuoriStato;
	}

	public void setDataTrasferimentoFuoriStato(String dataTrasferimento) {
		this.dataTrasferimentoFuoriStato = DateUtils.parseDateStringNew(dataTrasferimento, "dd/MM/yyyy");
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

	public int getIdAslVecchioProprietario() {
		return idAslVecchioProprietario;
	}

	public void setIdAslVecchioProprietario(int idAslVecchioProprietario) {
		this.idAslVecchioProprietario = idAslVecchioProprietario;
	}

	public int getIdAslVecchioDetentore() {
		return idAslVecchioDetentore;
	}

	public void setIdAslVecchioDetentore(int idAslVecchioDetentore) {
		this.idAslVecchioDetentore = idAslVecchioDetentore;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		
		try {
			
	    	  
			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_trasferimento_fuori_stato_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_trasferimento_fuori_stato("
					+ "id_evento, data_trasferimento_fuori_stato, id_vecchio_proprietario_fuori_stato  ");

			if (idVecchioDetentore > -1) {
				sql.append(",id_vecchio_detentore_fuori_stato ");
			}

			if (idContinente > -1) {
				sql.append(",id_continente ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",id_asl_vecchio_proprietario_fuori_stato ");
			}

			if (idAslVecchioDetentore > -1) {
				sql.append(", id_asl_vecchio_detentore_fuori_stato ");
			}

			if (datiProprietarioFuoriStato != null && !("").equals(datiProprietarioFuoriStato)) {
				sql.append(", dati_proprietario_fuori_stato ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", luogo ");
			}

			sql.append(")VALUES(?,?,?");

			if (idVecchioDetentore > -1) {
				sql.append(",? ");
			}

			if (idContinente > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioDetentore > -1) {
				sql.append(", ? ");
			}

			if (datiProprietarioFuoriStato != null && !("").equals(datiProprietarioFuoriStato)) {
				sql.append(", ? ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataTrasferimentoFuoriStato);

			pst.setInt(++i, idVecchioProprietario);

			if (idVecchioDetentore > -1) {
				pst.setInt(++i, idVecchioDetentore);
			}

			if (idContinente > -1) {
				pst.setInt(++i, idContinente);
			}

			if (idAslVecchioProprietario > -1) {
				pst.setInt(++i, idAslVecchioProprietario);
			}

			if (idAslVecchioDetentore > -1) {
				pst.setInt(++i, idAslVecchioDetentore);
			}

			if (datiProprietarioFuoriStato != null && !("").equals(datiProprietarioFuoriStato)) {
				pst.setString(++i, datiProprietarioFuoriStato);
			}

			if (luogo != null && !("").equals(luogo)) {
				pst.setString(++i, luogo);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_trasferimento_fuori_stato_id_seq", id);

			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoTrasferimentoFuoriStato(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataTrasferimentoFuoriStato = rs.getTimestamp("data_trasferimento_fuori_stato");
		this.idVecchioProprietario = rs.getInt("id_vecchio_proprietario_fuori_stato");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore_fuori_stato");
		this.idContinente = rs.getInt("id_continente");
		this.datiProprietarioFuoriStato = rs.getString("dati_proprietario_fuori_stato");
		// this.luogo = rs.getString("luogo");
		this.idAslVecchioProprietario = rs.getInt("id_asl_vecchio_proprietario_fuori_stato");
		this.idAslVecchioDetentore = rs.getInt("id_asl_vecchio_detentore_fuori_stato");

	}

	public EventoTrasferimentoFuoriStato(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_trasferimento_fuori_stato f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdVecchioProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
			//	
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);
			//
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
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdVecchioDetentore();

			if (idOperatore != -1 && idOperatore != 0) {
			//	
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

	public EventoTrasferimentoFuoriStato salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
		//	
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

			if (thisAnimale.getProprietario() != null && thisAnimale.getProprietario().getIdOperatore() > 0) {
				Stabilimento stab = (Stabilimento) oldAnimale.getProprietario().getListaStabilimenti().get(0);
				this.setIdAslVecchioProprietario(stab.getIdAsl());
			}

			this.setIdVecchioDetentore(thisAnimale.getIdDetentore());
			if (thisAnimale.getDetentore() != null && thisAnimale.getDetentore().getIdOperatore() > 0) {
				Stabilimento stabDet = (Stabilimento) thisAnimale.getDetentore().getListaStabilimenti().get(0);
				this.setIdAslVecchioDetentore(stabDet.getIdAsl());
			}

			thisAnimale.setIdDetentoreUltimoTrasferimentoFStato(thisAnimale.getIdDetentore());
			thisAnimale.setIdProprietarioUltimoTrasferimentoFStato(thisAnimale.getIdProprietario());
			thisAnimale.setIdDetentore(-1);
			thisAnimale.setIdProprietario(-1);
			thisAnimale.setIdContinente(this.getIdContinente());
			thisAnimale.setIdAslRiferimento(Constants.ID_ASL_FUORI_REGIONE);

			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoTrasferimentoFuoriStato build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
