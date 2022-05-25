/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;


public class GestoreAcque implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private int id;
	private String endpoint;
	private String nome;
	private HashMap<String,String> extOpt;
	
	private boolean selected = false;
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public boolean isQualifica() {
		return qualifica;
	}


	public void setQualifica(boolean qualifica) {
		this.qualifica = qualifica;
	}


	private boolean qualifica = false;
	
	
	public GestoreAcque() {
		setExtOpt(new HashMap<String,String>());
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public String toString(){
		return getEndpoint()+";;;"+getId();
	}


	public HashMap<String,String> getExtOpt() {
		return extOpt;
	}


	public void setExtOpt(HashMap<String,String> extOpt) {
		this.extOpt = extOpt;
	} 
	
	public void buildFromRequest(HttpServletRequest req, int indice){
		selected = (req.getParameter("ruolo_"+indice)!=null && req.getParameter("ruolo_"+indice).equals("on")) ? true : false;
		nome = req.getParameter("nome");
		id = Integer.parseInt( req.getParameter("id") );
		qualifica = (req.getParameter("isQualifica_"+indice)!=null && req.getParameter("isQualifica_"+indice).equals("on")) ? true : false;
		
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
