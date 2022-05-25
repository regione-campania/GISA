/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mycfs.base;

public class Allegato {

	private String descrizione;
	private int idAllegato;
	private int folderid;
	private String versione;
	private String estensione;
	private String codAllegato;
	
	public String getCodAllegato() {
		return codAllegato;
	}
	public void setCodAllegato(String codAllegato) {
		this.codAllegato = codAllegato;
	}
	
	public int getFolderid() {
		return folderid;
	}
	public void setFolderid(int folderid) {
		this.folderid = folderid;
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(int idAllegato) {
		this.idAllegato = idAllegato;
	}
	
}
