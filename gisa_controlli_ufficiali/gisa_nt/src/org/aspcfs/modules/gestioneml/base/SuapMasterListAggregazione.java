/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuapMasterListAggregazione {
	
	private int id ; 
	private int idMacroarea ; 
	private String codiceAttivita ; 
	private String aggregazione ;
	private int flussoOrig ;
	
	
	
	public SuapMasterListAggregazione(ResultSet rs)
	{
		try
		{
			this.id 				=	rs.getInt("id");
			this.idMacroarea 		=	rs.getInt("id_macroarea");
			this.codiceAttivita 	=	rs.getString("codice_attivita");
			this.aggregazione		=	rs.getString("aggregazione");
			this.flussoOrig		 	=	rs.getInt("id_flusso_originale");
		}
		catch(SQLException e)
		{
			System.out.println("##ERRORE COSTRUZIONE BEAN MACROAREA "+e.getMessage());
		}
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdMacroarea() {
		return idMacroarea;
	}



	public void setIdMacroarea(int idMacroarea) {
		this.idMacroarea = idMacroarea;
	}



	public String getCodiceAttivita() {
		return codiceAttivita;
	}



	public void setCodiceAttivita(String codiceAttivita) {
		this.codiceAttivita = codiceAttivita;
	}



	public String getAggregazione() {
		return aggregazione;
	}



	public void setAggregazione(String aggregazione) {
		this.aggregazione = aggregazione;
	}



	public int getFlussoOrig() {
		return flussoOrig;
	}



	public void setFlussoOrig(int flussoOrig) {
		this.flussoOrig = flussoOrig;
	}
	
	
	

}
