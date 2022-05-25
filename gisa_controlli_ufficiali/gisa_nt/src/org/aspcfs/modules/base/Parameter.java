/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.base;

import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class Parameter extends GenericBean implements Comparable<Parameter>
{

	private static final long serialVersionUID = -3842980586914000622L;
	String nome		= null;
	String prefisso	= null;
	String valore	= null;
	ArrayList<String> valori	= null;
	int id 			= -1;
	
	public Parameter() {
		valori = new ArrayList<String>();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPrefisso() {
		return prefisso;
	}
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public ArrayList<String> getValori() {
		return valori;
	}
	public void setValori(ArrayList<String> valori) {
		this.valori = valori;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int compareTo(Parameter o) {
		return (id > o.getId()) ? (1) : (-1);
	}
	

}
