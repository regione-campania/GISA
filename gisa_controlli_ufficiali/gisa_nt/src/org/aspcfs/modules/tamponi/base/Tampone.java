/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.tamponi.base;

import java.io.Serializable;
import java.util.HashMap;

public class Tampone implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6842719494559486778L;
	
	private int idTampone=0;
	private int idTicket=0;
	private HashMap<Integer, String> ricerca=new HashMap<Integer, String>();
	private int superfice=-1;
	private String superficeStringa="";
	private HashMap<Integer, String> esiti=new HashMap<Integer, String>();
	private int tipo=0;
	private String desrizioneSuperficeIntero="";
	
	public String getDesrizioneSuperficeIntero() {
		return desrizioneSuperficeIntero;
	}
	public void setDesrizioneSuperficeIntero(String desrizioneSuperficeIntero) {
		this.desrizioneSuperficeIntero = desrizioneSuperficeIntero;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public void addEsito(int k,String v){
		esiti.put(k, v);
	}
	public void addRicerca(int k,String v){
		ricerca.put(k, v);
	}
	public int getIdTampone() {
		return idTampone;
	}


	public void setIdTampone(int idTampone) {
		this.idTampone = idTampone;
	}


	public int getIdTicket() {
		return idTicket;
	}


	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}


	public HashMap<Integer, String> getRicerca() {
		return ricerca;
	}


	public void setRicerca(HashMap<Integer, String> ricerca) {
		this.ricerca = ricerca;
	}


	public int getSuperfice() {
		return superfice;
	}


	public void setSuperfice(int superfice) {
		this.superfice = superfice;
	}


	public String getSuperficeStringa() {
		return superficeStringa;
	}


	public void setSuperficeStringa(String superficeStringa) {
		this.superficeStringa = superficeStringa;
	}


	public HashMap<Integer, String> getEsiti() {
		return esiti;
	}


	public void setEsiti(HashMap<Integer, String> esiti) {
		this.esiti = esiti;
	}


	public Tampone() {
		// TODO Auto-generated constructor stub
	}

}
