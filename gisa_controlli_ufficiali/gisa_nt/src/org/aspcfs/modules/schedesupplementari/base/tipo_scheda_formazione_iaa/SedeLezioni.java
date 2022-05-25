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

public class SedeLezioni {

	private int idIstanza = -1;
	private int indice = -1;
	
	private String comune = null;
	private String via = null;
	private String presso = null;
	private String dateLezioni = null;
	private String orari = null;
	
	
	public SedeLezioni() {
		// TODO Auto-generated constructor stub
	}

	public SedeLezioni(ResultSet rs) throws SQLException {
		this.idIstanza = rs.getInt("id_istanza");
		this.indice = rs.getInt("indice");
		this.comune = rs.getString("comune");
		this.via = rs.getString("via");
		this.presso = rs.getString("presso");
		this.dateLezioni = rs.getString("datelezioni");
		this.orari = rs.getString("orari");
		}


	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}


	public String getVia() {
		return via;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getPresso() {
		return presso;
	}


	public void setPresso(String presso) {
		this.presso = presso;
	}


	public String getDateLezioni() {
		return dateLezioni;
	}


	public void setDateLezioni(String dateLezioni) {
		this.dateLezioni = dateLezioni;
	}


	public String getOrari() {
		return orari;
	}


	public void setOrari(String orari) {
		this.orari = orari;
	}

	
	public static ArrayList<SedeLezioni> buildDaRequest(ActionContext context) {
		ArrayList<SedeLezioni> lista = new  ArrayList<SedeLezioni>();
		for (int i = 1; i<=14; i++){
			SedeLezioni elem = new SedeLezioni();
			elem.setIndice(i);
			elem.setComune(context.getRequest().getParameter("lezioniComune_"+i));
			elem.setVia(context.getRequest().getParameter("lezioniVia_"+i));
			elem.setPresso(context.getRequest().getParameter("lezioniPresso_"+i));
			elem.setDateLezioni(context.getRequest().getParameter("lezioniDate_"+i));
			elem.setOrari(context.getRequest().getParameter("lezioniOrari_"+i));
			lista.add(elem);
		}
		return lista;
	}

	public void insert(Connection db, int idIstanza) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.insert_sede_lezioni(?, ?, ?, ?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, indice);
		pst.setString(++i, comune);
		pst.setString(++i, via);
		pst.setString(++i, presso);
		pst.setString(++i, dateLezioni);
		pst.setString(++i, orari);
		pst.executeQuery();				
	}


	public static ArrayList<SedeLezioni> queryRecord(Connection db, int idIstanza) throws SQLException {
		ArrayList<SedeLezioni> lista = new  ArrayList<SedeLezioni>();
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_sede_lezioni(?)");
		pst.setInt(1, idIstanza);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SedeLezioni sede = new SedeLezioni(rs);
			lista.add(sede);
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
