/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.io.Serializable;

import org.aspcfs.modules.vigilanza.base.ComponenteNucleoIspettivoList;

public class Piano implements Serializable{

	/**
	 * 
	 */
	private ComponenteNucleoIspettivoList listaComponentiNucleoIspettivo = new ComponenteNucleoIspettivoList();
	private static final long serialVersionUID = 1L;
	private int id ;
	private String descrizione ;
	private int id_uo ;
	private String desc_uo ;
	private String codice_interno;
	private boolean flagCondizionalita ;
	
	
	
	public boolean isFlagCondizionalita() {
		return flagCondizionalita;
	}
	public void setFlagCondizionalita(boolean flagCondizionalita) {
		this.flagCondizionalita = flagCondizionalita;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getId_uo() {
		return id_uo;
	}
	public void setId_uo(int id_uo) {
		this.id_uo = id_uo;
	}
	public String getDesc_uo() {
		return desc_uo;
	}
	public void setDesc_uo(String desc_uo) {
		this.desc_uo = desc_uo;
	}
	
	public ComponenteNucleoIspettivoList getListaComponentiNucleoIspettivo() {
		
		return listaComponentiNucleoIspettivo;
	}

	public void setListaComponentiNucleoIspettivo(
			ComponenteNucleoIspettivoList listaComponentiNucleoIspettivo) {
		this.listaComponentiNucleoIspettivo = listaComponentiNucleoIspettivo;
	}
	public String getCodice_interno() {
		return codice_interno;
	}
	public void setCodice_interno(String codice_interno) {
		this.codice_interno = codice_interno;
	}
	
	
}
