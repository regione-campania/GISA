/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.framework.beans.GenericBean;

public class StabilimentoVariazioneUbicazione extends GenericBean{


	private Timestamp dataVariazione ;
	private int idIndirizzo ;
	
	private Timestamp dataInizio ;
	private Timestamp dataFine ;
	private Indirizzo indirizzo ;
	
	
	public Timestamp getDataVariazione() {
		return dataVariazione;
	}

	public void setDataVariazione(Timestamp dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	
	public int getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Timestamp getDataFine() {
		return dataFine;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void buildRecord(Connection db , ResultSet rs) throws SQLException
	{
		idIndirizzo = rs.getInt("id_opu_indirizzo");
		dataVariazione = rs.getTimestamp("data_assegnazione_ubicazione");
		dataInizio = rs.getTimestamp("data_inizio");
		dataFine = rs.getTimestamp("data_fine");
		indirizzo=new Indirizzo(db,idIndirizzo);
	}
	



}
