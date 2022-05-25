/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.devdoc.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class ModuloNote extends GenericBean {
	
	
	private int id = -1;	
	private String note =null;
	private int idModulo = -1;
	private int idUtente =-1;
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

	public int getIdModulo() {
		return idModulo;
	}


	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}

	public void setIdModulo(String idModulo) {
		try {this.idModulo = Integer.parseInt(idModulo);} catch (Exception e){};
	}
	
	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public void setIdUtente(String idUtente) {
		try {this.idUtente = Integer.parseInt(idUtente);} catch (Exception e){};
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


	public ArrayList<ModuloNote> estraiNote (Connection db, int idModulo) {
		// TODO Auto-generated constructor stub
		this.idModulo = idModulo;
		ArrayList<ModuloNote> note = new ArrayList<ModuloNote>();
		
		String sql = "select * from sviluppo_moduli_note where id_modulo = ? and data_cancellazione is null";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idModulo);
			ResultSet rs= pst.executeQuery();
		
			while (rs.next()){
				ModuloNote nota = new ModuloNote();
				nota.buildRecord(rs);
				note.add(nota);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}
	
	
	public ModuloNote() {
		// TODO Auto-generated constructor stub
	}


	private void buildRecord(ResultSet rs) throws SQLException{
	id = rs.getInt("id");
	note = rs.getString("note");
	idUtente = rs.getInt("id_utente");
	idModulo = rs.getInt("id_modulo");
	dataInserimento = rs.getTimestamp("data_inserimento");
	
	}
	
public void store(Connection db) throws SQLException{
	
	PreparedStatement pstDisabilita;
	String sqlDisabilita = "update sviluppo_moduli_note set data_cancellazione = ? where data_cancellazione is null and id_modulo = ? and id_utente = ?";
	pstDisabilita = db.prepareStatement(sqlDisabilita);
	pstDisabilita.setTimestamp(1, dataCancellazione);
	pstDisabilita.setInt(2, idModulo);
	pstDisabilita.setInt(3, idUtente);
	pstDisabilita.executeUpdate();

	PreparedStatement pstInserisci;
	String sqlInserisci = "insert into sviluppo_moduli_note(id_modulo, id_utente, data_inserimento, note) values (?,?, ?, ?)";
	pstInserisci = db.prepareStatement(sqlInserisci);
	pstInserisci.setInt(1, idModulo);
	pstInserisci.setInt(2, idUtente);
	pstInserisci.setTimestamp(3, dataCancellazione);
	pstInserisci.setString(4, note);
	pstInserisci.executeUpdate();
	
}
	
	
}

