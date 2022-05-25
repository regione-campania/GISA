/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

public class RispostaDwrCodicePiano {

	private String codiceInterno ;
	private String flagCondizionalita ;
	private String code ;
	
	private String descrizione ;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getFlagCondizionalita() {
		return flagCondizionalita;
	}
	public void setFlagCondizionalita(String flagCondizionalita) {
		this.flagCondizionalita = flagCondizionalita;
	}
	
	
}
