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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoCessioneImport extends Evento {


	public static final int idTipologiaDB = 51;

	public EventoCessioneImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataCessioneImport;
	
	private int idVecchioProprietario = -1;
	private int idVecchioDetentore = -1;
	private int idAslVecchioProprietario = -1;
	private int idAslVecchioDetentore = -1;

	private int idAslNuovoProprietarioCessioneImport = -1;
	private boolean flagAccettato = false;

	
	


	

	public boolean isFlagAccettato() {
		return flagAccettato;
	}

	public void setFlagAccettato(boolean flagAccettato) {
		this.flagAccettato = flagAccettato;
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


	public java.sql.Timestamp getDataCessioneImport() {
		return dataCessioneImport;
	}

	public void setDataCessioneImport(java.sql.Timestamp dataCessioneImport) {
		this.dataCessioneImport = dataCessioneImport;
	}
	
	public void setDataCessioneImport(String dataCessioneImport) {
		this.dataCessioneImport = 	DateUtils.parseDateStringNew(
				dataCessioneImport, "dd/MM/yyyy");;
	}

	public int getIdAslNuovoProprietarioCessioneImport() {
		return idAslNuovoProprietarioCessioneImport;
	}

	public void setIdAslNuovoProprietarioCessioneImport(
			int idAslNuovoProprietarioCessioneImport) {
		this.idAslNuovoProprietarioCessioneImport = idAslNuovoProprietarioCessioneImport;
	}
	
	public void setIdAslNuovoProprietarioCessioneImport(
			String idAslNuovoProprietarioCessioneImport) {
		this.idAslNuovoProprietarioCessioneImport = Integer.parseInt(idAslNuovoProprietarioCessioneImport);
	}
	
	


	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_cessione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_cessione_import("
							+ "id_evento, data_cessione_import, id_asl_nuovo_proprietario_cessione_import, flag_accettato ");

			

			sql.append(") values(?, ?, ?, ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataCessioneImport);
			pst.setInt(++i, idAslNuovoProprietarioCessioneImport);
			pst.setBoolean(++i, flagAccettato);

			

			pst.execute();
			pst.close();

			this.id = DatabaseUtils
					.getCurrVal(db, "evento_cessione_import_id_seq", id);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public EventoCessioneImport(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.dataCessioneImport = rs.getTimestamp("data_cessione_import");
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.flagAccettato = rs.getBoolean("flag_accettato");
		this.idAslNuovoProprietarioCessioneImport = rs.getInt("id_asl_nuovo_proprietario_cessione_import");
	
		
		

	}

	public EventoCessioneImport(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cessione_import f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	
	public void GetCessioneApertaByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cessione_import f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = false and e.data_cancellazione is null and e.trashed_date is null");
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
	
	//usata per recuperare la cessione da riaprire a seguito della cancellazione di una presa in carico
	public void GetUltimaCessioneChiusaByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cessione_import f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = true and e.data_cancellazione is null and e.trashed_date is null");
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

	

	
	
	

	public int updateAccettazione(Connection conn) {
		int result = -1;

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("update evento_cessione_import set flag_accettato = ? where id_evento=?");
			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setBoolean(++i, this.flagAccettato);
			pst.setInt(++i, this.getIdEvento());
			
			result = pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	
	public EventoCessioneImport salvaRegistrazione(int userId,
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


			this.setIdVecchioProprietario(oldAnimale
					.getIdProprietario());
			this.setIdAslVecchioProprietario(oldAnimale
					.getIdAslRiferimento());

	

			this.setIdVecchioDetentore(oldAnimale
					.getIdDetentore());


			thisAnimale.setIdDetentore(-1);
			thisAnimale.setIdProprietario(-1);



			// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
			// DETENTORE;
			// PER ORA LO IMPOSTO AL PROPRIETARIO

			this.insert(db);
			
			thisAnimale.setIdAslRiferimento(this
					.getIdAslNuovoProprietarioCessioneImport());

			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}

	public EventoCessioneImport build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
}
