/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.login.beans;

import com.darkhorseventures.framework.beans.GenericBean;

public class LoginBeanSuap extends LoginBean {
	
	private String urlProvenienza ;
	private String denominazione ;
	private String indirizzo ;
	private String codiceSuap ;
	private String codiceFiscaleRichiedente ;
	private String message ;
	private String partitaIva ;
	private String urlDispatcher ; 
	private String username ;
	private String password ;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrlDispatcher() {
		return urlDispatcher;
	}
	public void setUrlDispatcher(String urlDispatcher) {
		this.urlDispatcher = urlDispatcher;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrlProvenienza() {
		return urlProvenienza;
	}
	public void setUrlProvenienza(String urlProvenienza) {
		this.urlProvenienza = urlProvenienza;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCodiceSuap() {
		return codiceSuap;
	}
	public void setCodiceSuap(String codiceSuap) {
		this.codiceSuap = codiceSuap;
	}
	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}
	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}
	
	

}
