/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import java.io.Serializable;

public class BGuiView implements Serializable
{
	private static final long serialVersionUID = -2883927938048815689L;
	
	private int id_funzione;
	private String nome_funzione;
	private String descrizione_funzione;
	
	private int id_subfunzione;
	private String nome_subfunzione;
	private String descrizione_subfunzione;
	
	private int id_gui;
	private String nome_gui;
	private String descrizione_gui;
	
	private String diritti;
	
	private String key = null;
	
	public String getDescrizione_funzione() {
		return descrizione_funzione;
	}
	public void setDescrizione_funzione(String descrizione_funzione) {
		this.descrizione_funzione = descrizione_funzione;
	}
	public String getDescrizione_gui() {
		return descrizione_gui;
	}
	public void setDescrizione_gui(String descrizione_gui) {
		this.descrizione_gui = descrizione_gui;
	}
	public String getDescrizione_subfunzione() {
		return descrizione_subfunzione;
	}
	public void setDescrizione_subfunzione(String descrizione_subfunzione) {
		this.descrizione_subfunzione = descrizione_subfunzione;
	}
	public int getId_funzione() {
		return id_funzione;
	}
	public void setId_funzione(int id_funzione) {
		this.id_funzione = id_funzione;
	}
	public int getId_gui() {
		return id_gui;
	}
	public void setId_gui(int id_gui) {
		this.id_gui = id_gui;
	}
	public int getId_subfunzione() {
		return id_subfunzione;
	}
	public void setId_subfunzione(int id_subfunzione) {
		this.id_subfunzione = id_subfunzione;
	}
	public String getNome_funzione() {
		return nome_funzione;
	}
	public void setNome_funzione(String nome_funzione) {
		this.nome_funzione = nome_funzione;
	}
	public String getNome_gui() {
		return nome_gui;
	}
	public void setNome_gui(String nome_gui) {
		this.nome_gui = nome_gui;
	}
	public String getNome_subfunzione() {
		return nome_subfunzione;
	}
	public void setNome_subfunzione(String nome_subfunzione) {
		this.nome_subfunzione = nome_subfunzione;
	}
	public String getKey() {
		if( key == null )
		{
			key = nome_funzione + "->" + nome_subfunzione + "->" + nome_gui;
		}
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDiritti() {
		return diritti;
	}
	public void setDiritti(String diritti) {
		this.diritti = diritti;
	}
}
