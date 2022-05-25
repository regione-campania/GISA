/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.darkhorseventures.framework.beans.GenericBean;

public class Canile extends GenericBean  implements Serializable 
{
	private static final long serialVersionUID = -8622138631789926823L;
	
	private Timestamp dataFine;
	private Timestamp dataRiattivazioneBlocco;
	private Timestamp dataOperazioneBlocco;
	private Timestamp dataSospensioneBlocco;
	private boolean bloccato;
	private int mqDisponibili;
	private float occupazioneAttuale;
	private int numeroCaniVivi;
	
	public Timestamp getDataFine() {
		return dataFine;
	}
	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	
	public Timestamp getDataRiattivazioneBlocco() {
		return dataRiattivazioneBlocco;
	}
	public void setDataRiattivazioneBlocco(Timestamp dataRiattivazioneBlocco) {
		this.dataRiattivazioneBlocco = dataRiattivazioneBlocco;
	}
	
	public Timestamp getDataOperazioneBlocco() {
		return dataOperazioneBlocco;
	}
	public void setDataOperazioneBlocco(Timestamp dataOperazioneBlocco) {
		this.dataOperazioneBlocco = dataOperazioneBlocco;
	}
	
	public Timestamp getDataSospensioneBlocco() {
		return dataSospensioneBlocco;
	}
	public void setDataSospensioneBlocco(Timestamp dataSospensioneBlocco) {
		this.dataSospensioneBlocco = dataSospensioneBlocco;
	}
	
	public boolean isBloccato() {
		return bloccato;
	}
	public void setBloccato(boolean bloccato) {
		this.bloccato = bloccato;
	}
	
	public int getMqDisponibili() {
		return mqDisponibili;
	}
	public void setMqDisponibili(int mqDisponibili) {
		this.mqDisponibili = mqDisponibili;
	}
	
	public float getOccupazioneAttuale() {
		return occupazioneAttuale;
	}
	public void setOccupazioneAttuale(float occupazioneAttuale) {
		this.occupazioneAttuale = occupazioneAttuale;
	}
	
	public int getNumeroCaniVivi() {
		return numeroCaniVivi;
	}
	public void setNumeroCaniVivi(int numeroCaniVivi) {
		this.numeroCaniVivi = numeroCaniVivi;
	}
	
	
}
