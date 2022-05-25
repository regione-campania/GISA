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

public class Componente {
	
	private int id = -1;
	private String nominativo = "";
	private int idStruttura = -1;
	private String nomeStruttura = "";
	private int idQualifica = -1;
	private String nomeQualifica = "";

	
	public Componente() {
		// TODO Auto-generated constructor stub
	}


	public Componente(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.nominativo = rs.getString("nominativo");
		this.idStruttura = rs.getInt("id_struttura");
		this.nomeStruttura = rs.getString("nome_struttura");
		this.idQualifica = rs.getInt("id_qualifica");
		this.nomeQualifica = rs.getString("nome_qualifica");
	}


	public static ArrayList<Componente> buildList(Connection db, int idQualifica, int anno, String idStrutture) {
		ArrayList<Componente> lista = new ArrayList<Componente>();
		try
		{
			String select = "select * from public.get_nucleo_componenti(?, ?, ?);";  
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, anno);
			pst.setInt(2, idQualifica);
			pst.setString(3, idStrutture);
			rs = pst.executeQuery();
			while (rs.next()){
				Componente com = new Componente(rs);
				lista.add(com);
			}
		}
		catch(SQLException e)
		{	e.printStackTrace();
		}
		return lista;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNominativo() {
		return nominativo;
	}


	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}


	public int getIdStruttura() {
		return idStruttura;
	}


	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}


	public String getNomeStruttura() {
		return nomeStruttura;
	}


	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}


	public int getIdQualifica() {
		return idQualifica;
	}


	public void setIdQualifica(int idQualifica) {
		this.idQualifica = idQualifica;
	}


	public String getNomeQualifica() {
		return nomeQualifica;
	}


	public void setNomeQualifica(String nomeQualifica) {
		this.nomeQualifica = nomeQualifica;
	}


	

}
