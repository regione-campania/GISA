/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import java.sql.Timestamp;
import java.util.ArrayList;

/*****************************
 * BEAN PER IL SINGOLO RUOLO *
******************************/

public class RoleBean {

	//Attributi Ruolo
	private int idr;
	private String role;
	private String descr;
	private int enteredby;
	private Timestamp entered;
	private int modifiedby;
	private Timestamp modified;
	private String enabled;
	private String role_type="0";
	private String note="";
	
	//Permessi Ruolo
	private ArrayList<String> perm = new ArrayList<String>();
	
	//MESSAGGIO
	private String msg="val";
	
	public int getIdr() {
		return idr;
	}
	public void setIdr(int idr) {
		this.idr = idr;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public ArrayList<String> getPerm() {
		return perm;
	}
	public void setPerm(ArrayList<String> perm) {
		this.perm = perm;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return note;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}
	public String getRole_type() {
		return role_type;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public Timestamp getModified() {
		return modified;
	}
}