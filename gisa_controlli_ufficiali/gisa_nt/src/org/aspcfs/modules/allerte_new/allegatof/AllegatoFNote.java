/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.allegatof;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class AllegatoFNote extends GenericBean {
	
	
	private int id = -1;	
	private String note =null;
	private int idAsl =-1;
	private int idAllerta = -1;
	private int idUtenteInserimento =-1;
	private Timestamp dataInserimento = null;
	private Timestamp  dataCancellazione =null;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public int getIdAsl() {
		return idAsl;
	}


	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}


	public int getIdAllerta() {
		return idAllerta;
	}


	public void setIdAllerta(int idAllerta) {
		this.idAllerta = idAllerta;
	}


	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}


	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}


	public Timestamp getDataInserimento() {
		return dataInserimento;
	}


	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}


	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}


	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}


	public AllegatoFNote(Connection db, int idAllerta, int idAsl) {
		// TODO Auto-generated constructor stub
		this.idAllerta = idAllerta;
		this.idAsl = idAsl;
		
		String sql = "select * from allerte_f_note(?, ?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAllerta);
			pst.setInt(2, idAsl);
			ResultSet rs= pst.executeQuery();
		
			if (rs.next()){
				buildRecord(rs);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public AllegatoFNote() {
		// TODO Auto-generated constructor stub
	}


	private void buildRecord(ResultSet rs) throws SQLException{
	note = rs.getString(1);
	}
	
public void store(Connection db) throws SQLException{
	
	PreparedStatement pstDisabilita;
	String sqlDisabilita = "update allerte_asl_note set data_cancellazione = ? where data_cancellazione is null and id_asl = ?";
	pstDisabilita = db.prepareStatement(sqlDisabilita);
	pstDisabilita.setTimestamp(1, dataCancellazione);
	pstDisabilita.setInt(2, idAsl);
	pstDisabilita.executeUpdate();

	PreparedStatement pstInserisci;
	String sqlInserisci = "insert into allerte_asl_note(id_asl, id_allerta, id_utente_inserimento, data_inserimento, note) values (?,?, ?, ?, ?)";
	pstInserisci = db.prepareStatement(sqlInserisci);
	pstInserisci.setInt(1, idAsl);
	pstInserisci.setInt(2, idAllerta);
	pstInserisci.setInt(3, idUtenteInserimento);
	pstInserisci.setTimestamp(4, dataCancellazione);
	pstInserisci.setString(5, note);
	pstInserisci.executeUpdate();
	
}
	
	
}
