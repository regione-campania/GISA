/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class Oggetto extends GenericBean {

	private String oggetto;
	private int code;
	private String capitolo;
	private String codiceEvento;

	public Oggetto() { 

	}

	public Oggetto(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public Oggetto(Connection db, int code) throws SQLException {
		String select = "select * from public.get_oggetto_del_controllo(?);";
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setInt(1, code);
		rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
		}
		}

	private void buildRecord(ResultSet rs) throws SQLException{

		this.code = rs.getInt("code");
		this.oggetto = rs.getString("oggetto");
		this.capitolo = rs.getString("capitolo");
		this.codiceEvento = rs.getString("codice_evento");
	}

	public static ArrayList<Oggetto> buildLista(Connection db) {
		ArrayList<Oggetto> lista = new ArrayList<Oggetto>();
		try
		{
			String select = "select * from public.get_oggetto_del_controllo(-1);"; 
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			rs = pst.executeQuery();
			while (rs.next()){
				Oggetto oggetto = new Oggetto(rs);
				lista.add(oggetto);
			}
		}
		catch(SQLException e)
		{	e.printStackTrace();
		}
		return lista;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getCodiceEvento() { 
		return codiceEvento;
	}

	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

	

}
