/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioniopu.base;

import java.sql.Timestamp;

public class StampeModuli {
	
	private int id;
	private int tipoModulo;
	private Timestamp dataModulo;
	private int aslMacello;
	private int idMacello;
	private int progressivo;
	private int oldProgressivo;
	private int hashCode;
	private String matricolaCapo;
	private int idSpeditore;
	private String malattiaCapo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipoModulo() {
		return tipoModulo;
	}
	public void setTipoModulo(int tipoModulo) {
		this.tipoModulo = tipoModulo;
	}
	public Timestamp getDataModulo() {
		return dataModulo;
	}
	public void setDataModulo(Timestamp dataModulo) {
		this.dataModulo = dataModulo;
	}
	public int getAslMacello() {
		return aslMacello;
	}
	public void setAslMacello(int aslMacello) {
		this.aslMacello = aslMacello;
	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public int getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}
	public String getMatricolaCapo() {
		return matricolaCapo;
	}
	public void setMatricolaCapo(String matricolaCapo) {
		this.matricolaCapo = matricolaCapo;
	}
	public int getIdSpeditore() {
		return idSpeditore;
	}
	public void setIdSpeditore(int idSpeditore) {
		this.idSpeditore = idSpeditore;
	}
	public String getMalattiaCapo() {
		return malattiaCapo;
	}
	public void setMalattiaCapo(String malattiaCapo) {
		this.malattiaCapo = malattiaCapo;
	}
	public int getOldProgressivo() {
		return oldProgressivo;
	}
	public void setOldProgressivo(int oldProgressivo) {
		this.oldProgressivo = oldProgressivo;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
	
	
}
