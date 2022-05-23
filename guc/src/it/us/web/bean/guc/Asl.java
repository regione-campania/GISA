/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



public class Asl implements Serializable {

	private static final long serialVersionUID = 3L;
	
	private int id;
	private String nome;
	public int idVam ;
	private Set<Utente> utenti = new HashSet<Utente>(0);

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public Set<Utente> getUtenti() {
		return utenti;
	}
	public void setUtenti(Set<Utente> utenti) {
		this.utenti = utenti;
	}
	public int getIdVam() {
		return idVam;
	}
	public void setIdVam(int idVam) {
		this.idVam = idVam;
	}
	
	@Override
	public String toString(){
		return getId()+"";
	} 
	 
	
	

}
