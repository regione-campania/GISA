/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;


public class Ruolo implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private int id;
	private Utente utente;
	private String endpoint;
	private Integer ruoloInteger;
	private String ruoloString;
	private String note;
	
	
	public Ruolo() {
		setRuoloInteger(-1);
		setRuoloString("N.D.");
		setNote("");
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public Integer getRuoloInteger() {
		return ruoloInteger;
	}
	public void setRuoloInteger(Integer ruoloInteger) {
		this.ruoloInteger = ruoloInteger;
	}
	
	
	public String getRuoloString() {
		return ruoloString;
	}
	public void setRuoloString(String ruoloString) {
		this.ruoloString = ruoloString;
	}

	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString(){
		return getEndpoint()+";;;"+getRuoloInteger();
	} 
}
