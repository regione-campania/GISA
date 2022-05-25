/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;

import java.io.Serializable;
import java.sql.Timestamp;

public class InformazioniSanitarie implements Serializable {

	private String codiceMalattia;
	private String qualifica 	;
	private String descrizioneCodiceMalattia;
	private String descrizioneQualifica 	;
	private Timestamp dataRilevazione;
	
	
	public Timestamp getDataRilevazione() {
		return dataRilevazione;
	}
	public void setDataRilevazione(Timestamp dataRilevazione) {
		this.dataRilevazione = dataRilevazione;
	}
	public String getCodiceMalattia() {
		return codiceMalattia;
	}
	public void setCodiceMalattia(String codiceMalattia) {
		this.codiceMalattia = codiceMalattia;
	}
	public String getQualifica() {
		return qualifica;
	}
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}
	public String getDescrizioneCodiceMalattia() {
		return descrizioneCodiceMalattia;
	}
	public void setDescrizioneCodiceMalattia(String descrizioneCodiceMalattia) {
		this.descrizioneCodiceMalattia = descrizioneCodiceMalattia;
	}
	public String getDescrizioneQualifica() {
		return descrizioneQualifica;
	}
	public void setDescrizioneQualifica(String descrizioneQualifica) {
		this.descrizioneQualifica = descrizioneQualifica;
	}
	
	
}
