/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import java.util.Set;

public class AutopsiaSezioneDettaglioEsami implements java.io.Serializable{
	
	private String organo;
	private String tipo;
	private String dettaglio;
	private Set<String> esiti;
	
	public String getOrgano() {
		return organo;
	}
	public void setOrgano(String organo) {
		this.organo = organo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDettaglio() {
		return dettaglio;
	}
	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}
	public Set<String> getEsiti() {
		return esiti;
	}
	public void setEsiti(Set<String> esiti) {
		this.esiti = esiti;
	}
	
	@Override
	public String toString()
	{
		return organo + ", " + tipo + ", " + dettaglio;
	}
	

}
