/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.test;

import java.io.Serializable;
import java.util.Date;

public class Organization implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String idAsl;
	private Date dataNascita;
	private Indirizzo[] listaIndirizzi;
	
	public Indirizzo[] getListaIndirizzi() {
		return listaIndirizzi;
	}
	public void setListaIndirizzi(Indirizzo[] listaIndirizzi) {
		this.listaIndirizzi = listaIndirizzi;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(String idAsl) {
		this.idAsl = idAsl;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
}
