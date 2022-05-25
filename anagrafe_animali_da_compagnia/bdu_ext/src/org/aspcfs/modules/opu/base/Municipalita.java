/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;


public class Municipalita extends GenericBean {

	private int id = -1;
	private int idComune = -1;
	private String nomeMunicipalita = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdComune() {
		return idComune;
	}
	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}

	public String getNomeMunicipalita() {
		return nomeMunicipalita;
	}
	public void setNomeMunicipalita(String nomeMunicipalita) {
		this.nomeMunicipalita = nomeMunicipalita;
	}
	public Municipalita() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<Municipalita> getListaByIdComune(Connection db, int idComune) throws SQLException {
		ArrayList<Municipalita> ret = new ArrayList<Municipalita>();
		
		StringBuffer query = new StringBuffer("");
		
		query.append("select * from municipalita");
		
		if (idComune > 0)
			query.append(" where id_comune = ?");
		
		PreparedStatement cs = db.prepareStatement(query.toString());
		if (idComune > 0)
			cs.setInt(1,idComune);
		
		ResultSet rs = cs.executeQuery();
		while (rs.next())
		{
			Municipalita circ = new Municipalita();
			circ.setId(rs.getInt("id"));
			circ.setNomeMunicipalita(rs.getString("nome_municipalita"));
			ret.add(circ);
		}
//		
		return ret;
	}
	
	
	
	
	
	
	
	
	
}
