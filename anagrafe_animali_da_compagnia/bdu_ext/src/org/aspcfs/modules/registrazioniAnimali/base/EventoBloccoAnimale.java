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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;


public class EventoBloccoAnimale extends Evento {
	

	
	public static final int idTipologiaDB = 57;

	public EventoBloccoAnimale () {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private int id = -1;
	private int idEvento = -1;
	
	private java.sql.Timestamp dataBlocco;
	private String noteBlocco = "";
	private int idProprietario = -1;
	private int idDetentore = -1;
	private int idStato = -1;
	

    
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
	
	
	
	

	public java.sql.Timestamp getDataBlocco() {
		return dataBlocco;
	}

	public void setDataBlocco(java.sql.Timestamp dataBlocco) {
		this.dataBlocco = dataBlocco;
	}

	
	public void setDataBlocco(String dataBlocco) {
		this.dataBlocco = DateUtils.parseDateStringNew(dataBlocco,
				"dd/MM/yyyy");
	}
	public String getNoteBlocco() {
		return noteBlocco;
	}

	public void setNoteBlocco(String noteBlocco) {
		this.noteBlocco = noteBlocco;
	}
	
	
	

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

		//	id = DatabaseUtils.getNextSeq(db, "evento_blocco_animale_id_seq");

			sql.append("INSERT INTO evento_blocco_animale(" + "id_evento, data_blocco, note_blocco  ");

			sql.append(")VALUES(?,?,?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);
			pst.setTimestamp(++i, dataBlocco);
			pst.setString(++i, noteBlocco);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_blocco_animale_id_seq", id);

		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
		}
		return true;

	}
	
	

	public EventoBloccoAnimale(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
	  
	protected void buildRecord(ResultSet rs) throws SQLException {
		  
		
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.id = rs.getInt("id");
		  this.dataBlocco = rs.getTimestamp("data_blocco");
		  this.noteBlocco = rs.getString("note_blocco");
		  this.setIdDetentore(rs.getInt("id_detentore"));
		  this.setIdProprietario(rs.getInt("id_proprietario"));
		  this.setIdStato(rs.getInt("id_stato"));
		



		  
		  
	  }
	
	  
	public EventoBloccoAnimale(Connection db, int idEventoPadre) throws SQLException {

		//super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_blocco_animale f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	
	

	
	public EventoBloccoAnimale salvaRegistrazione(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
				

			this.setIdProprietario(this.getIdProprietarioCorrente());
			this.setIdDetentore(this.getIdDetentoreCorrente());
			this.setIdStato(this.getIdStatoOriginale());

			this.insert(db);

			thisAnimale.setFlagBloccato(true);
			thisAnimale.setIdUltimaRegistrazioneBlocco(this.getIdEvento());
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoBloccoAnimale build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
	
	
	public void getUltimoBlocco(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_blocco_animale f on (e.id_evento = f.id_evento) where e.id_animale = ? and e.id_evento = " +
						"(select max (id_evento) from evento where id_animale = ? and id_tipologia_evento = ?)  ");
		pst.setInt(1, idAnimale);
		pst.setInt(2, idAnimale);
		pst.setInt(3, EventoBloccoAnimale.idTipologiaDB);
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
	
	
	

}
