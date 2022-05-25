/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;

public class Iscritto {

	private int idIstanza = -1;
	private int indice = -1;

	private String nome = null;
	private String cognome = null;
	private String codiceFiscale = null;
	
	public Iscritto() {
		// TODO Auto-generated constructor stub
	}
	
	public Iscritto(ResultSet rs) throws SQLException {
		this.idIstanza = rs.getInt("id_istanza");
		this.indice = rs.getInt("indice");
		this.nome = rs.getString("nome");
		this.cognome = rs.getString("cognome");
		this.codiceFiscale = rs.getString("codice_fiscale");
	}

	
	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public static ArrayList<Iscritto> buildDaRequest(ActionContext context) {
		 ArrayList<Iscritto> lista = new  ArrayList<Iscritto>();
		for (int i = 1; i<=32; i++){
			Iscritto elem = new Iscritto();
			elem.setIndice(i);
			elem.setNome(context.getRequest().getParameter("iscrittoNome_"+i));
			elem.setCognome(context.getRequest().getParameter("iscrittoCognome_"+i));
			elem.setCodiceFiscale(context.getRequest().getParameter("iscrittoCf_"+i));
			lista.add(elem);
		}
		return lista;
	}

	public void insert(Connection db, int idIstanza) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.insert_iscritti(?, ?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, indice);
		pst.setString(++i, nome);
		pst.setString(++i, cognome);
		pst.setString(++i, codiceFiscale);
		pst.executeQuery();				
	}
	
	public static ArrayList<Iscritto> queryRecord(Connection db, int idIstanza) throws SQLException {
		ArrayList<Iscritto> lista = new  ArrayList<Iscritto>();
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_iscritti(?)");
		pst.setInt(1, idIstanza);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Iscritto isc = new Iscritto(rs);
			lista.add(isc);
		}
		return lista;
	}

	public int getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}
}
