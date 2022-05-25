/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionepratiche.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.gestionepratiche.base.Richiesta;

public class Richiesta {
	
	private int code;
	private String long_description;
	ArrayList<Richiesta> richieste = new ArrayList<Richiesta>();
	
	
	public ArrayList<Richiesta> getRichieste() {
		return richieste;
	}

	public void setRichieste(ArrayList<Richiesta> richieste) {
		this.richieste = richieste;
	}
	public Richiesta(){};
	
	public Richiesta(int code, String long_description){
		this.setCode(code);
		this.setLong_description(long_description);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getLong_description() {
		return long_description;
	}

	public void setLong_description(String long_description) {
		this.long_description = long_description;
	};
	

	public void getTipoRichiesta(Connection db)  throws SQLException {
		
		// TODO Auto-generated method stub
		String sql = "select code, long_description from suap_lookup_tipo_richiesta where gins order by code";
    	PreparedStatement st = db.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
		while(rs.next()){
       	 Richiesta richiesta = new Richiesta(rs.getInt("code"), rs.getString("long_description"));
         richieste.add(richiesta);    
        }
	}

	public void insertPratica(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		
	};
}
