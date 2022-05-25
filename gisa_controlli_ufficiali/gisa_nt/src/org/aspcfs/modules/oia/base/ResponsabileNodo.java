/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.oia.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsabileNodo implements Serializable {

	private int id_utente 				;
	private String nome_responsabile 	;
	private String cognome_responsabile ;
	private String ruolo_responsabile ;
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public String getNome_responsabile() {
		return nome_responsabile;
	}
	public void setNome_responsabile(String nome_responsabile) {
		this.nome_responsabile = nome_responsabile;
	}
	public String getCognome_responsabile() {
		return cognome_responsabile;
	}
	public void setCognome_responsabile(String cognome_responsabile) {
		this.cognome_responsabile = cognome_responsabile;
	}
	public String getRuolo_responsabile() {
		return ruolo_responsabile;
	}
	public void setRuolo_responsabile(String ruolo_responsabile) {
		this.ruolo_responsabile = ruolo_responsabile;
	}
	
	public ResponsabileNodo(ResultSet rs) throws SQLException
	{
		
		id_utente = rs.getInt("id_utente");
		nome_responsabile = rs.getString("nome_responsabile");
		cognome_responsabile= rs.getString("cognome_responsabile");
		ruolo_responsabile = rs.getString("ruolo_responsabile");
	}
	
	public void insert (Connection db,int idNodo) throws SQLException
	{
		
		
		String insert = "insert into oia_nodo_responsabili (id_utente , id_oia_nodo) values (?,?)" ;
		PreparedStatement pst = db.prepareStatement(insert);
		pst.setInt(1, id_utente);
		pst.setInt(2, idNodo);
		pst.execute() ;
	}
	
	public ResponsabileNodo() 
	{
		
	}
	
	
	
}
