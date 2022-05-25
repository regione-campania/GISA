/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.darkhorseventures.database;

import java.sql.Connection;
import java.sql.Timestamp;

public class ActionDb {
	
	private String actionName;
	private String ipChiamante ;
	private Timestamp dataApertura ;
	private String actionppathname ;
	private String command ;
	private Connection db ;
	
	
	
	public Connection getDb() {
		return db;
	}
	public void setDb(Connection db) {
		this.db = db;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getActionppathname() {
		return actionppathname;
	}
	public void setActionppathname(String actionppathname) {
		this.actionppathname = actionppathname;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getIpChiamante() {
		return ipChiamante;
	}
	public void setIpChiamante(String ipChiamante) {
		this.ipChiamante = ipChiamante;
	}
	public Timestamp getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Timestamp dataApertura) {
		this.dataApertura = dataApertura;
	}
	
	
	

}
