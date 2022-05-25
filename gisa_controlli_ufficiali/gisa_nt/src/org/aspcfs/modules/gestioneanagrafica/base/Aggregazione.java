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


public class Aggregazione 
{
	private String aggregazione;
	private Integer id;
	private Integer id_flusso_originale;
	private Macroarea macroarea = new Macroarea();
	private String codice_attivita;
	
	public Aggregazione()
	{
		
	}
	
	public Aggregazione(Map<String, String[]> properties) throws SQLException
	{
		Bean.populate(this, properties);
	}
	
	public Aggregazione(ResultSet rs) throws SQLException
	{
		Bean.populate(this, rs);
	}
	
	public String getAggregazione() {
		return aggregazione;
	}
	public void setAggregazione(String aggregazione) {
		this.aggregazione = aggregazione;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_flusso_originale() {
		return id_flusso_originale;
	}
	public void setId_flusso_originale(Integer id_flusso_originale) {
		this.id_flusso_originale = id_flusso_originale;
	}
	
	public Macroarea getMacroarea()
	{
		return this.macroarea;
	}
	public void setMacroarea(Macroarea macroarea)
	{
		this.macroarea = macroarea;
	}
	
	public String getCodice_attivita() {
		return codice_attivita;
	}

	public void setCodice_attivita(String codice_attivita) {
		this.codice_attivita = codice_attivita;
	}
}
