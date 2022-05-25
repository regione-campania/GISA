/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoSbloccoAnimale extends Evento {

	public static final int idTipologiaDB = 58;

	public EventoSbloccoAnimale() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;

	private java.sql.Timestamp dataSblocco;
	private String noteSblocco = "";
	private boolean flagRipristinaStatoPrecendente = false;

	int idProprietario = -1;
	int idDetentore = -1;
	int idStato = -1;

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

	public java.sql.Timestamp getDataSblocco() {
		return dataSblocco;
	}

	public void setDataSblocco(java.sql.Timestamp dataSblocco) {
		this.dataSblocco = dataSblocco;
	}

	public String getNoteSblocco() {
		return noteSblocco;
	}

	public void setNoteSblocco(String noteSblocco) {
		this.noteSblocco = noteSblocco;
	}

	public void setDataSblocco(String dataSblocco) {
		this.dataSblocco = DateUtils.parseDateStringNew(dataSblocco, "dd/MM/yyyy");
	}

	public boolean isFlagRipristinaStatoPrecendente() {
		return flagRipristinaStatoPrecendente;
	}

	public void setFlagRipristinaStatoPrecendente(boolean flagRipristinaStatoPrecendente) {
		this.flagRipristinaStatoPrecendente = flagRipristinaStatoPrecendente;
	}

	public void setFlagRipristinaStatoPrecendente(String flagRipristinaStatoPrecendente) {
		this.flagRipristinaStatoPrecendente = DatabaseUtils.parseBoolean(flagRipristinaStatoPrecendente);
	}

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}

	public void setIdProprietario(String idProprietario) {
		this.idProprietario = Integer.valueOf(idProprietario);
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = Integer.valueOf(idDetentore);
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public void setIdStato(String idStato) {
		this.idStato = Integer.valueOf(idStato);
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			
			
			
			super.insert(db);
			idEvento = super.getIdEvento();

			// id = DatabaseUtils.getNextSeq(db,
			// "evento_blocco_animale_id_seq");

			sql.append("INSERT INTO evento_sblocco_animale("
					+ "id_evento, data_sblocco, note_sblocco, flag_ripristino_stato_precedente, id_proprietario, id_detentore, id_stato  ");

			sql.append(")VALUES(?,?,?,?,?,?,?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);
			pst.setTimestamp(++i, dataSblocco);
			pst.setString(++i, noteSblocco);
			pst.setBoolean(++i, flagRipristinaStatoPrecendente);
			pst.setInt(++i, idProprietario);
			pst.setInt(++i, idDetentore);
			pst.setInt(++i, idStato);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_sblocco_animale_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoSbloccoAnimale(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataSblocco = rs.getTimestamp("data_sblocco");
		this.noteSblocco = rs.getString("note_sblocco");
		this.flagRipristinaStatoPrecendente = rs.getBoolean("flag_ripristino_stato_precedente");
		this.idProprietario = rs.getInt("id_proprietario");
		this.idDetentore = rs.getInt("id_detentore");
		this.idStato = rs.getInt("id_stato");

	}

	public EventoSbloccoAnimale(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_sblocco_animale f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public EventoSbloccoAnimale salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
		try {
			
			if (!flagRipristinaStatoPrecendente){
				if (this.getIdStato() != checkStato(db, thisAnimale)) {
			
				throw new Exception("Stato non congruente");
				}
			}
			

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			

			this.insert(db);

			// COME AGGIORNO? NON MI SERVE IL WKF
			// aggiornaStatoAnimale(db, thisAnimale);

			if (flagRipristinaStatoPrecendente) {
				// Recupero lo stato precedente al blocco dalla registrazione di
				// blocco??
				EventoBloccoAnimale blocco = new EventoBloccoAnimale(db, thisAnimale.getIdUltimaRegistrazioneBlocco());
				thisAnimale.setStato(blocco.getIdStatoOriginale());
				this.setIdStato(blocco.getIdStatoOriginale());
				thisAnimale.updateStato(db);
			}else{
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, this.idProprietario);			
				Stabilimento stabProp = (Stabilimento) proprietario.getListaStabilimenti().get(0);
				
				thisAnimale.setIdAslRiferimento(stabProp.getIdAsl());
				thisAnimale.setStato(this.getIdStato());
				thisAnimale.setIdProprietario(this.getIdProprietario());
				thisAnimale.setIdDetentore(this.getIdDetentore());
				thisAnimale.updateStato(db);
			}
			
			
		

		} catch (Exception e) {
			throw e;
		}
		
		return this;

	}

	public EventoSbloccoAnimale build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

	private int checkStato(Connection db, Animale thisAnimale) {
		int idStato = -1;

		Operatore proprietario = new Operatore();
		Operatore detentore = new Operatore();
		int tipologiaRegistrazione = -1;
		try {
			proprietario.queryRecordOperatorebyIdLineaProduttiva(db, this.idProprietario);			
			detentore.queryRecordOperatorebyIdLineaProduttiva(db, this.idDetentore);
			Stabilimento stabProp = (Stabilimento) proprietario.getListaStabilimenti().get(0);
			Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);
			LineaProduttiva lpProp = (LineaProduttiva) stabProp.getListaLineeProduttive().get(0);
			LineaProduttiva lpDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);
			
			tipologiaRegistrazione = EventoRegistrazioneBDU.idTipologiaDB;
			
			if (lpProp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco){
				tipologiaRegistrazione = EventoCattura.idTipologiaDB;
			}
			
			
			RegistrazioniWKF r_wkf = new RegistrazioniWKF();
			r_wkf.setIdStato(1);
			r_wkf.setIdRegistrazione(tipologiaRegistrazione);
			idStato = (r_wkf
					.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
					.getIdProssimoStato();
			
			if (thisAnimale.isFlagSterilizzazione()){
				tipologiaRegistrazione = EventoSterilizzazione.idTipologiaDB;
				r_wkf.setIdStato(idStato);
				r_wkf.setIdRegistrazione(tipologiaRegistrazione);
				idStato = (r_wkf
						.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
						.getIdProssimoStato();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idStato;
	}
	
	
	public Operatore getNuovoProprietario() {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			Thread t = Thread.currentThread(); 
			conn = GestoreConnessioni.getConnection();




			idOperatore = this.getIdProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}
	
	public Operatore getNuovoDetentore() {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			Thread t = Thread.currentThread(); 
			conn = GestoreConnessioni.getConnection();




			idOperatore = this.getIdDetentore();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}



}
