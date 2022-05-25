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
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;

public class EventoTrasferimentoFuoriRegioneSoloProprietario extends Evento {

	public static final int idTipologiaDB = 40;

	public EventoTrasferimentoFuoriRegioneSoloProprietario() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataTrasferimentoFuoriRegioneProprietario;
	private int idVecchioProprietario = -1;
	private int idVecchioDetentore = -1;
	private int idAslVecchioProprietario = -1;
	private int idAslVecchioDetentore = -1;
	private int idRegioneFr = -1;
	private int idProvinciaFr = -1;
	private int idComuneFr = -1;

	private int idProprietario = -1;

	public java.sql.Timestamp getDataTrasferimentoFuoriRegioneProprietario() {
		return dataTrasferimentoFuoriRegioneProprietario;
	}

	public void setDataTrasferimentoFuoriRegioneProprietario(
			java.sql.Timestamp dataTrasferimentoFuoriRegioneProprietario) {
		this.dataTrasferimentoFuoriRegioneProprietario = dataTrasferimentoFuoriRegioneProprietario;
	}

	public void setDataTrasferimentoFuoriRegioneProprietario(String dataTrasferimentoFuoriRegioneProprietario) {
		this.dataTrasferimentoFuoriRegioneProprietario = DateUtils.parseDateStringNew(
				dataTrasferimentoFuoriRegioneProprietario, "dd/MM/yyyy");
	}

	public int getIdRegioneFr() {
		return idRegioneFr;
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

	public void setIdRegioneFr(int idRegioneFr) {
		this.idRegioneFr = idRegioneFr;
	}

	public void setIdRegioneFr(String idRegioneFr) {
		this.idRegioneFr = new Integer(idRegioneFr).intValue();
	}

	public int getIdProvinciaFr() {
		return idProvinciaFr;
	}

	public void setIdProvinciaFr(int idProvinciaFr) {
		this.idProvinciaFr = idProvinciaFr;
	}

	public void setIdProvinciaFr(String idProvinciaFr) {
		this.idProvinciaFr = new Integer(idProvinciaFr).intValue();
	}

	public int getIdComuneFr() {
		return idComuneFr;
	}

	public void setIdComuneFr(int idComuneFr) {
		this.idComuneFr = idComuneFr;
	}

	public void setIdComuneFr(String idComuneFr) {
		this.idComuneFr = new Integer(idComuneFr).intValue();
	}

	public String getDatiProprietarioFuoriRegione() {
		return datiProprietarioFuoriRegione;
	}

	public void setDatiProprietarioFuoriRegione(String datiProprietarioFuoriRegione) {
		this.datiProprietarioFuoriRegione = datiProprietarioFuoriRegione;
	}

	private String luogo = "";
	private String datiProprietarioFuoriRegione = "";

	public int getIdTipologiaDB() {
		return idTipologiaDB;
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

			id = DatabaseUtils.getNextSeq(db, "evento_trasferimento_fuori_regione_solo_proprietario_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_trasferimento_fuori_regione_solo_proprietario("
					+ "id_evento, data_trasferimento_fuori_regione_solo_proprietario, id_vecchio_proprietario_fuori_regione_solo_prop  ");

			if (idVecchioDetentore > -1) {
				sql.append(",id_vecchio_detentore_fuori_regione_solo_prop ");
			}

			if (idRegioneFr > -1) {
				sql.append(",id_regione_solo_prop ");
			}

			if (idProvinciaFr > -1) {
				sql.append(",id_provincia_solo_prop ");
			}

			if (idComuneFr > -1) {
				sql.append(",id_comune_solo_prop ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",id_asl_vecchio_proprietario_fuori_regione_solo_prop ");
			}

			if (idAslVecchioDetentore > -1) {
				sql.append(", id_asl_vecchio_detentore_fuori_regione_solo_prop ");
			}

			if (idProprietario > -1) {
				sql.append(", id_proprietario_fuori_regione_solo_prop ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", luogo ");
			}

			sql.append(")VALUES(?,?,?");

			if (idVecchioDetentore > -1) {
				sql.append(",? ");
			}

			if (idRegioneFr > -1) {
				sql.append(",? ");
			}

			if (idProvinciaFr > -1) {
				sql.append(",? ");
			}

			if (idComuneFr > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioDetentore > -1) {
				sql.append(", ? ");
			}

			if (idProprietario > -1) {
				sql.append(", ? ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataTrasferimentoFuoriRegioneProprietario);

			pst.setInt(++i, idVecchioProprietario);

			if (idVecchioDetentore > -1) {
				pst.setInt(++i, idVecchioDetentore);
			}

			if (idRegioneFr > -1) {
				pst.setInt(++i, idRegioneFr);
			}

			if (idProvinciaFr > -1) {
				pst.setInt(++i, idProvinciaFr);
			}

			if (idComuneFr > -1) {
				pst.setInt(++i, idComuneFr);
			}

			if (idAslVecchioProprietario > -1) {
				pst.setInt(++i, idAslVecchioProprietario);
			}

			if (idAslVecchioDetentore > -1) {
				pst.setInt(++i, idAslVecchioDetentore);
			}

			if (idProprietario > -1) {
				pst.setInt(++i, idProprietario);
			}

			if (luogo != null && !("").equals(luogo)) {
				pst.setString(++i, luogo);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_trasferimento_fuori_regione_solo_proprietario_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoTrasferimentoFuoriRegioneSoloProprietario(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataTrasferimentoFuoriRegioneProprietario = rs
				.getTimestamp("data_trasferimento_fuori_regione_solo_proprietario");
		this.idVecchioProprietario = rs.getInt("id_vecchio_proprietario_fuori_regione_solo_prop");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore_fuori_regione_solo_prop");
		this.idRegioneFr = rs.getInt("id_regione_solo_prop");
		this.idProvinciaFr = rs.getInt("id_provincia_solo_prop");
		this.idComuneFr = rs.getInt("id_comune_solo_prop");
		this.idProprietario = rs.getInt("id_proprietario_fuori_regione_solo_prop");
		// this.luogo = rs.getString("luogo");
		this.idAslVecchioProprietario = rs.getInt("id_asl_vecchio_proprietario_fuori_regione_solo_prop");
		this.idAslVecchioDetentore = rs.getInt("id_asl_vecchio_detentore_fuori_regione_solo_prop");

	}

	public EventoTrasferimentoFuoriRegioneSoloProprietario(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_trasferimento_fuori_regione_solo_proprietario f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public Operatore getProprietarioFuoriRegione() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			conn = GestoreConnessioni.getConnection();
			//
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}
		return operatore;

	}

	public EventoTrasferimentoFuoriRegioneSoloProprietario salvaRegistrazione(int userId, int userRole, int userAsl,
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

			this.setIdVecchioProprietario(oldAnimale.getIdProprietario());
			Stabilimento stab = (Stabilimento) oldAnimale.getProprietario().getListaStabilimenti().get(0);
			this.setIdAslVecchioProprietario(stab.getIdAsl());

			// Recupero dal nuovo proprietario il comune, per poter
			// recuperare regione e provincia
			Operatore newprop = new Operatore();
			newprop.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdProprietario());

			Stabilimento stabNewOp = (Stabilimento) newprop.getListaStabilimenti().get(0);

			if (stabNewOp != null && stabNewOp.getSedeOperativa() != null) {
				int idComune = stabNewOp.getSedeOperativa().getComune();
				if (idComune > 0) {
					ComuniAnagrafica comuni = new ComuniAnagrafica();
					HashMap map = comuni.getProvinciaRegioneDaIdComune(db, idComune);
					this.setIdRegioneFr((Integer) map.get("codiceRegione"));
					this.setIdProvinciaFr((Integer) map.get("codiceProvincia"));
				}
			}

			Stabilimento stabDet = new Stabilimento();

			// Detentore
			// thisCane = new Cane(db, oldAnimale.getIdAnimale());
			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
				this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
				stabDet = (Stabilimento) oldAnimale.getDetentore().getListaStabilimenti().get(0);
				this.setIdAslVecchioDetentore(stabDet.getIdAsl());

			}

			thisAnimale.setIdDetentoreUltimoTrasferimentoFRegione(thisAnimale.getIdDetentore());
			thisAnimale.setIdProprietarioUltimoTrasferimentoFRegione(thisAnimale.getIdProprietario());
			// thisCane.setIdDetentore(-1);
			thisAnimale.setIdProprietario(this.getIdProprietario());
			thisAnimale.setIdRegione(this.getIdRegioneFr());
			thisAnimale.setIdAslRiferimento(stabDet.getIdAsl());

			if (oldAnimale.getIdSpecie() == Cane.idSpecie) {
			} else if (oldAnimale.getIdSpecie() == Gatto.idSpecie) {
			}

			else if (oldAnimale.getIdSpecie() == Furetto.idSpecie) {
			}

			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoTrasferimentoFuoriRegioneSoloProprietario build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
