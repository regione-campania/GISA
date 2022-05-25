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
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;

public class EventoRientroFuoriRegione extends Evento {

	public static final int idTipologiaDB = 17; // Ritorno come privato
	public static final int idTipologiaDBOperatoreCommerciale = 35; // ritorno a
																	// operatore
																	// commerciale
																	// ??
	public static final int idTipologiaDBRandagio = 37;
	public static final int idRegioneCampania = -1;

	private java.sql.Timestamp dataRientroFR;
	private int idAsl = -1;
	private int idProprietario = -1;
	private int idDetentore = -1;
	private int idRegioneDa = -1;
	private String luogo = "";

	private int id = -1;
	private int idEvento = -1;

	public EventoRientroFuoriRegione() {
		super();
		this.setIdTipologiaEvento(idTipologiaDB);
		// TODO Auto-generated constructor stub
	}

	public java.sql.Timestamp getDataRientroFR() {
		return dataRientroFR;
	}

	public void setDataRientroFR(java.sql.Timestamp dataRientroFR) {
		this.dataRientroFR = dataRientroFR;
	}

	public void setDataRientroFR(String data) {
		this.dataRientroFR = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public void setIdAsl(String idAsl) {
		this.idAsl = new Integer(idAsl).intValue();
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

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}

	public int getIdRegioneDa() {
		return idRegioneDa;
	}

	public void setIdRegioneDa(int idRegioneDa) {
		this.idRegioneDa = idRegioneDa;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
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

			id = DatabaseUtils.getNextSeq(db, "evento_rientro_da_fuori_regione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_rientro_da_fuori_regione(" + "id_evento, data_rientro_fr, id_asl  ");

			if (idProprietario > -1) {
				sql.append(",id_proprietario_rientro ");
			}

			if (idDetentore > -1) {
				sql.append(",id_detentore_rientro ");
			}

			if (idRegioneDa > -1) {
				sql.append(",id_regione_da ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", luogo ");
			}

			sql.append(")VALUES(?,?,?");

			if (idProprietario > -1) {
				sql.append(",? ");
			}

			if (idDetentore > -1) {
				sql.append(",? ");
			}

			if (idRegioneDa > -1) {
				sql.append(",? ");
			}

			if (luogo != null && !("").equals(luogo)) {
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataRientroFR);

			pst.setInt(++i, idAsl);

			if (idProprietario > -1) {
				pst.setInt(++i, idProprietario);
			}

			if (idDetentore > -1) {
				pst.setInt(++i, idDetentore);
			}

			if (idRegioneDa > -1) {
				pst.setInt(++i, idRegioneDa);
			}

			if (luogo != null && !("").equals(luogo)) {
				pst.setString(++i, luogo);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_rientro_da_fuori_regione_id_seq", id);

		

		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoRientroFuoriRegione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);

		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataRientroFR = rs.getTimestamp("data_rientro_fr");
		this.idAsl = rs.getInt("id_asl");
		this.idProprietario = rs.getInt("id_proprietario_rientro");
		this.idDetentore = rs.getInt("id_detentore_rientro");
		this.idRegioneDa = rs.getInt("id_regione_da");
		this.luogo = rs.getString("luogo");

	}

	public EventoRientroFuoriRegione(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_rientro_da_fuori_regione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public EventoRientroFuoriRegione salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
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

			
			//Se lo stato dell'animale è 'Privato/Fuori Regione' allora la regione di provenienza va presa dalla residenza dell'attuale proprietario,
			//Se invece è stata fatta la registrazione di Trasferimento Fuori Regione allora questa informazione è stata memorizzata nella tabella animale
			if(thisAnimale.getStato()==6)
				this.setIdRegioneDa(thisAnimale.getProprietario().getSedeLegale().getIdRegione());
			else
				this.setIdRegioneDa(thisAnimale.getIdRegione());

			// ////////////////////////

			Operatore soggettoAdded = new Operatore();
			// Detentore
			// thisAnimale = new Cane(db, thisAnimale.getIdAnimale());
			if (this.getIdProprietario() > -1) {
				thisAnimale.setIdProprietario(this.getIdProprietario());

				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdProprietario());
			} else {
				thisAnimale.setIdProprietario(thisAnimale.getIdProprietarioUltimoTrasferimentoFRegione());
				this.setIdProprietario(thisAnimale.getIdProprietarioUltimoTrasferimentoFRegione());
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db,
						thisAnimale.getIdProprietarioUltimoTrasferimentoFRegione());
			}

			// Controllo se viene dal circuito commerciale

			Stabilimento stab = (Stabilimento) soggettoAdded.getListaStabilimenti().get(0);
			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale) {
				thisAnimale.setFlagCircuitoCommerciale(false);
				if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco) {
					this.setIdTipologiaEvento(EventoRientroFuoriRegione.idTipologiaDB);
				} else {
					this.setIdTipologiaEvento(EventoRientroFuoriRegione.idTipologiaDBRandagio);
				}
			} else {
				this.setIdTipologiaEvento(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale);
			}

			int idAsl = stab.getIdAsl();

			thisAnimale.setIdAslRiferimento(idAsl);

			if (this.getIdDetentore() != -1) {
				thisAnimale.setIdDetentore(this.getIdDetentore());
			} else {

				/**
				 * Vecchia gestione
				 */
				// thisAnimale.setIdDetentore(thisAnimale
				// .getIdDetentoreUltimoTrasferimentoFRegione());
				// rientroFR.setIdDetentore(thisAnimale
				// .getIdDetentoreUltimoTrasferimentoFRegione());
				/**
				 * Nuova Gestione
				 */
				if (this.getIdProprietario() > 0) {
					thisAnimale.setIdDetentore(this.getIdProprietario());
					this.setIdDetentore(this.getIdProprietario());
				} else {
					thisAnimale.setIdDetentore(thisAnimale.getIdDetentoreUltimoTrasferimentoFRegione());
					this.setIdDetentore(thisAnimale.getIdDetentoreUltimoTrasferimentoFRegione());
				}
			}

			thisAnimale.setIdRegione(-1);

			// //////////////////////////////

			// thisAnimale.setIdRegione(-1);
			// thisAnimale.setIdProprietario(rientroFR.getIdProprietario());
			if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
			} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
			} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
			}

			this.insert(db);
			// tipologiaRegistrazione =
			// EventoRientroFuoriRegione.idTipologiaDB;

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoRientroFuoriRegione build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
