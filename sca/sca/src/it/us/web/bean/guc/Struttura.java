/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

public class Struttura {

	private int idAsl;
	private int idStruttura;
	private String descrizioneStruttura;
	private int idPadre;
	private int n_livello;  //2 struttura complessa e dipartimentale; 3 struttura semplice
	
	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public int getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}
	public int getIdStruttura() {
		return idStruttura;
	}
	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}
	public String getDescrizioneStruttura() {
		return descrizioneStruttura;
	}
	public void setDescrizioneStruttura(String descrizioneStruttura) {
		this.descrizioneStruttura = descrizioneStruttura;
	}
	public int getN_livello() {
		return n_livello;
	}
	public void setN_livello(int n_livello) {
		this.n_livello = n_livello;
	}	
	@Override
	public String toString(){
		return getIdStruttura()+"";
	}

}

