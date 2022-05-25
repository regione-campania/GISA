/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

public class EsitoOperazione {

	private int esito =-1;
	private String messaggio = "";
	private int idStabilimento = -1;

	public int getEsito() {
		return esito;
	}
	public void setEsito(int esito) {
		this.esito = esito;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public int getIdStabilimento() {
		return idStabilimento;
	}
	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	
	public EsitoOperazione (String ret){
		if (ret!=null){
			String[] retArray = ret.split(";;");
			try {
				this.esito = Integer.parseInt(retArray[0]);
				this.messaggio = retArray[1];
				this.idStabilimento = Integer.parseInt(retArray[2]);
			}
			catch (Exception e) {}
		}
		
		
		
	}
}
