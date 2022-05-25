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
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoPresaCessioneImport extends Evento {
	
	public static final int idTipologiaDB = 52;

	public EventoPresaCessioneImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataPresaCessioneImport;
	private int idProprietario = -1;
	private int idAsl = -1;

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


	public java.sql.Timestamp getDataPresaCessioneImport() {
		return dataPresaCessioneImport;
	}

	public void setDataPresaCessioneImport(
			java.sql.Timestamp dataPresaCessioneImport) {
		this.dataPresaCessioneImport = dataPresaCessioneImport;
	}

	public void setDataPresaCessioneImport(String dataPresaCessione) {
		this.dataPresaCessioneImport = DateUtils.parseDateStringNew(
				dataPresaCessione, "dd/MM/yyyy");
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

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db,
					"evento_presa_in_carico_cessione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_presa_in_carico_cessione_import("
							+ "id_evento, data_presa_in_carico_import, id_nuovo_proprietario_presa_cessione_import, id_asl_import  ");

			sql.append(")VALUES(?,?,?,?" );
					


			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			
				pst.setTimestamp(++i, dataPresaCessioneImport);
			

			pst.setInt(++i, idProprietario);

			pst.setInt(++i, idAsl);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_presa_in_carico_cessione_import_id_seq", id);

		
		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoPresaCessioneImport(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataPresaCessioneImport = rs.getTimestamp("data_presa_in_carico_import");
		this.idProprietario = rs.getInt("id_nuovo_proprietario_presa_cessione_import");
		this.idAsl = rs.getInt("id_asl_import");

	}

	public EventoPresaCessioneImport(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_presa_in_carico_cessione_import f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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



	
	public Operatore getNuovoProprietario() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			
			conn = GestoreConnessioni.getConnection();

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//finally{
//			if (conn != null)
//				DbUtil.chiudiConnessioneJDBC(null, null, conn);
//		}
		finally{
		GestoreConnessioni.freeConnection(conn);
	}

		return operatore;
	}

	
	public EventoPresaCessioneImport salvaRegistrazione(int userId,
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


			

			thisAnimale.setIdProprietario(this.getIdProprietario());
			thisAnimale.setIdDetentore(this.getIdProprietario());

			EventoCessioneImport cessioneInCorso = new EventoCessioneImport();
			cessioneInCorso.GetCessioneApertaByIdAnimale(db,
					thisAnimale.getIdAnimale());

			this.setIdAsl(cessioneInCorso
					.getIdAslNuovoProprietarioCessioneImport());

			this.insert(db);
		
			// Chiudo l'evento di cessione corrispondente a questa presa
			// in
			// carico

			cessioneInCorso.setFlagAccettato(true);
			cessioneInCorso.updateAccettazione(db);			

			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoPresaCessioneImport build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}



}
