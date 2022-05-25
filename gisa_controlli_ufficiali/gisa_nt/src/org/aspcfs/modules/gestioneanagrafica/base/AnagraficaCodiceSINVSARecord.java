/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.Date;

public class AnagraficaCodiceSINVSARecord {
	
	private int id;
	private int riferimentoId;
	private String riferimentoIdNomeTab;
	private String codiceSINVSA;
	private Date dataCodiceSINVSA;
	private Date entered;
	private int enteredBy;
	private Date trashedDate;
	private String noteHd;
	
	public AnagraficaCodiceSINVSARecord(int id, int riferimentoId, String riferimentoIdNomeTab, String codiceSINVSA,
			Date dataCodiceSINVSA, Date entered, int enteredBy, Date trashedDate, String noteHd) {
		this.id = id;
		this.riferimentoId = riferimentoId;
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
		this.codiceSINVSA = codiceSINVSA;
		this.dataCodiceSINVSA = dataCodiceSINVSA;
		this.entered = entered;
		this.enteredBy = enteredBy;
		this.trashedDate = trashedDate;
		this.noteHd = noteHd;
	}
	
	public int getId() {
		return id;
	}
	public int getRiferimentoId() {
		return riferimentoId;
	}
	public String getRiferimentoIdNomeTab() {
		return riferimentoIdNomeTab;
	}
	public String getCodiceSINVSA() {
		return codiceSINVSA;
	}
	public Date getDataCodiceSINVSA() {
		return dataCodiceSINVSA;
	}
	public Date getEntered() {
		return entered;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public Date getTrashedDate() {
		return trashedDate;
	}
	public String getNoteHd() {
		return noteHd;
	}
}
