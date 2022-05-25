/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.base;

import java.sql.Timestamp;

public class Art17AltreSpecie {
	
	private int idMacello;
	private int idEsercente;
	private String nomeEsercente;
	private String dataMacellazione;
	private String auricolare;
	private String mezzene;
	private String dataNascita;
	private String specie;
	private String categoria;
	private String sesso;
	private String modello4;
	private String esitoVisita;
	
	
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public int getIdEsercente() {
		return idEsercente;
	}
	public void setIdEsercente(int idEsercente) {
		this.idEsercente = idEsercente;
	}
	public String getNomeEsercente() {
		return nomeEsercente;
	}
	public void setNomeEsercente(String nomeEsercente) {
		this.nomeEsercente = nomeEsercente;
	}
	public String getDataMacellazione() {
		return dataMacellazione;
	}
	public void setDataMacellazione(String dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}
	public String getAuricolare() {
		return auricolare;
	}
	public void setAuricolare(String auricolare) {
		this.auricolare = auricolare;
	}
	public String getMezzene() {
		return mezzene;
	}
	public void setMezzene(String mezzene) {
		this.mezzene = mezzene;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getModello4() {
		return modello4;
	}
	public void setModello4(String modello4) {
		this.modello4 = modello4;
	}
	public String getEsitoVisita() {
		return esitoVisita;
	}
	public void setEsitoVisita(String esitoVisita) {
		this.esitoVisita = esitoVisita;
	}
	
	
}
