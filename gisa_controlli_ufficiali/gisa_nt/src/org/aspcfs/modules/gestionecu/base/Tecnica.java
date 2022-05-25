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

public class Tecnica {
	
	private int id = -1;
	private String nome = "";
	
	public Tecnica() {
		// TODO Auto-generated constructor stub
	}


	public Tecnica(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.nome = rs.getString("nome");
	}

	public Tecnica(Connection db, int idTecnica) throws SQLException {}

	public static ArrayList<Tecnica> buildLista(Connection db, int riferimentoId, String riferimentoIdNomeTab) {
		ArrayList<Tecnica> lista = new ArrayList<Tecnica>();
		try
		{
			String select = "select * from public.get_tecniche_by_id_anagrafica(?, ?);"; 
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, riferimentoId);
			pst.setString(2, riferimentoIdNomeTab);
			rs = pst.executeQuery();
			while (rs.next()){
				Tecnica tec = new Tecnica(rs); 
				lista.add(tec);
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


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


}
