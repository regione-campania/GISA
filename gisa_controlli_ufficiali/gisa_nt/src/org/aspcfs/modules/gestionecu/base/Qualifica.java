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

public class Qualifica {
	
	private int id = -1;
	private String nome = "";
	private boolean viewListaComponenti = false;
	private boolean gruppo = false;
	private ArrayList<Componente> listaComponenti = new ArrayList<Componente>();
	
	
	public Qualifica() {
		// TODO Auto-generated constructor stub
	}


	public Qualifica(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.nome = rs.getString("nome");
		this.viewListaComponenti = rs.getBoolean("view_lista_componenti");
		this.gruppo = rs.getBoolean("gruppo");  
	}

	public Qualifica(Connection db, int idQualifica) throws SQLException {
		String select = "select * from public.get_nucleo_qualifiche(-1) where id = ? and gruppo is false;";  
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setInt(1, idQualifica);
		rs = pst.executeQuery();
		while (rs.next()){
			buildRecord(rs); 
			}
	}

	public static ArrayList<Qualifica> buildList(Connection db, int userId) {
		ArrayList<Qualifica> lista = new ArrayList<Qualifica>();
		try
		{
			String select = "select * from public.get_nucleo_qualifiche(?);"; 
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()){
				Qualifica qual = new Qualifica(rs); 
				lista.add(qual);
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


	public boolean isViewListaComponenti() {
		return viewListaComponenti;
	}


	public void setViewListaComponenti(boolean viewListaComponenti) {
		this.viewListaComponenti = viewListaComponenti;
	}


	public boolean isGruppo() {
		return gruppo;
	}


	public void setGruppo(boolean gruppo) {
		this.gruppo = gruppo;
	}

	public ArrayList<Componente> getListaComponenti() {
		return listaComponenti;
	}


	public void setListaComponenti(ArrayList<Componente> listaComponenti) {
		this.listaComponenti = listaComponenti;
	}

}
