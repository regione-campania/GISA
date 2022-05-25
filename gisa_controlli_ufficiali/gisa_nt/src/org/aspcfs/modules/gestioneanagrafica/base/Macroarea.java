/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.aspcfs.utils.Bean;

public class Macroarea 
{
	private Integer id;
	private String codice_sezione;
	private String macroarea;
	
	public Macroarea(){}
	
	public Macroarea(Map<String, String[]> properties) throws SQLException
	{
		Bean.populate(this, properties);
	}
	
	
	public Macroarea(ResultSet rs) throws SQLException
	{
		Bean.populate(this, rs);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodice_sezione() {
		return codice_sezione;
	}
	public void setCodice_sezione(String codice_sezione) {
		this.codice_sezione = codice_sezione;
	}
	public String getMacroarea() {
		return macroarea;
	}
	public void setMacroarea(String macroarea) {
		this.macroarea = macroarea;
	}

}
