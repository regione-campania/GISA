/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Timestamp;

public class DocumentoPrelievoCampione {
	
	private int idDocumento;
	private int idCanile;
	private String nomeCanile;
	private Timestamp dataDocumento;
	private int numeroMC;
	public int getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	public int getIdCanile() {
		return idCanile;
	}
	public void setIdCanile(int idCanile) {
		this.idCanile = idCanile;
	}
	public String getNomeCanile() {
		return nomeCanile;
	}
	public void setNomeCanile(String nomeCanile) {
		this.nomeCanile = nomeCanile;
	}
	public Timestamp getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Timestamp dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public int getNumeroMC() {
		return numeroMC;
	}
	public void setNumeroMC(int numeroMC) {
		this.numeroMC = numeroMC;
	}
	
	

}
