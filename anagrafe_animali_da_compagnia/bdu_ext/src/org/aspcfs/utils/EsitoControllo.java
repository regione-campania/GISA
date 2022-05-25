/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Timestamp;

public class EsitoControllo {

	private int idEsito ; 
	private String descrizione ;
	private String dataRegistrazioneAnimale = null;
	private String comune ;
	private int comuneId;
	private String sesso ;
	
	public int getIdEsito() {
		return idEsito;
	}
	public void setIdEsito(int idEsito) {
		this.idEsito = idEsito;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public int getComuneId() {
		return comuneId;
	}
	public void setComuneId(int comuneId) {
		this.comuneId = comuneId;
	}
	public String getDataRegistrazioneAnimale() {
		return dataRegistrazioneAnimale;
	}
	public void setDataRegistrazioneAnimale(String dataRegistrazioneAnimale) {
		this.dataRegistrazioneAnimale = dataRegistrazioneAnimale;
	}

	
	
}
