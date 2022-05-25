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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.orders.base.OrderAddress;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class EventoTrasferimentoCanile extends Evento {

	public static final int idTipologiaDB = 31;

	private int id;
	private int idEvento = -1;
	private java.sql.Timestamp dataTrasferimentoCanile;
	private int idProprietario = -1;
	private int idCanileOld = -1;
	private int idDetentore = -1;

	// public static final HashMap campi = new HashMap();

	public int getId() {
		return id;
	}

	public java.sql.Timestamp getDataTrasferimentoCanile() {
		return dataTrasferimentoCanile;
	}

	public void setDataTrasferimentoCanile(java.sql.Timestamp data) {
		this.dataTrasferimentoCanile = data;
	}

	public void setDataTrasferimentoCanile(String dataSter) {
		this.dataTrasferimentoCanile = DateUtils.parseDateStringNew(dataSter, "dd/MM/yyyy");
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EventoTrasferimentoCanile() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getIdCanileOld() {
		return idCanileOld;
	}

	public void setIdCanileOld(int idCanileOld) {
		this.idCanileOld = idCanileOld;
	}

	public void setIdCanileOld(String idCanileOld) {
		this.idCanileOld = new Integer(idCanileOld).intValue();
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

	public EventoTrasferimentoCanile(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataTrasferimentoCanile = rs.getTimestamp("data_trasferimento_canile");
		this.idProprietario = rs.getInt("id_proprietario_trasferimento_canile");
		this.idCanileOld = rs.getInt("id_canile_old_trasferimento_canile");
		this.idDetentore = rs.getInt("id_canile_detentore");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {

			
			
	    	  
			super.insert(db);
			idEvento = super.getIdEvento();

		

			id = DatabaseUtils.getNextSeq(db, "evento_trasferimento_canile_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append(" INSERT INTO evento_trasferimento_canile(id_evento, data_trasferimento_canile, id_proprietario_trasferimento_canile ");

			if (idCanileOld != -1) {
				sql.append(", id_canile_old_trasferimento_canile");
			}

			if (idDetentore != -1) {
				sql.append(",id_canile_detentore");
			}

			sql.append(")VALUES(?,?,?");

			if (idCanileOld != -1) {
				sql.append(",?");
			}

			if (idDetentore != -1) {
				sql.append(",?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataTrasferimentoCanile);
			pst.setInt(++i, idProprietario);

			if (idCanileOld != -1) {
				pst.setInt(++i, idCanileOld);
			}

			if (idDetentore != -1) {
				pst.setInt(++i, idDetentore);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_trasferimento_canile_id_seq", id);


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoTrasferimentoCanile(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_trasferimento_canile f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getProprietario() throws UnknownHostException { // Sindaco
																		// proprietario
																		// al
																		// momento
																		// del
																		// trasferimento

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
			// String host =InetAddress.getByName("hostDbBdu").getHostAddress();
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

	public Operatore getCanileOld() throws UnknownHostException { // Canile
																	// detentore
																	// al
																	// momento
																	// del
																	// trasferimento

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

			idOperatore = this.getIdCanileOld();

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

	public Operatore getCanileDetentore() throws UnknownHostException { // Canile
																		// detentore
																		// dopo
																		// il
																		// trasferimento

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

	public EventoTrasferimentoCanile salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
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

			this.setIdProprietario(oldAnimale.getIdProprietario());

			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
				int idDetentore = ((LineaProduttiva) (((Stabilimento) oldAnimale.getDetentore().getListaStabilimenti()
						.get(0)).getListaLineeProduttive()).get(0)).getId();
				this.setIdCanileOld(idDetentore);

			}
			thisAnimale.setIdDetentore(this.getIdDetentore()); // Nuovo
			// canile
			// selezionato
			// come
			// detentore

			// }

			
			this.insert(db);
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoTrasferimentoCanile salvaRegistrazioneSenzaControlloCanilePieno(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			
			Animale oldAnimale = thisAnimale;

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

			this.setIdProprietario(oldAnimale.getIdProprietario());

			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0) {
				int idDetentore = ((LineaProduttiva) (((Stabilimento) oldAnimale.getDetentore().getListaStabilimenti()
						.get(0)).getListaLineeProduttive()).get(0)).getId();
				this.setIdCanileOld(idDetentore);

			}
			thisAnimale.setIdDetentore(this.getIdDetentore()); // Nuovo
			// canile
			// selezionato
			// come
			// detentore

			// }

			
			this.insert(db);
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimaleSenzaControlloCanilePieno(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoTrasferimentoCanile build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
