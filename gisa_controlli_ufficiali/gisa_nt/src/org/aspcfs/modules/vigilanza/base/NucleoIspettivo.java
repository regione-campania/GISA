/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.vigilanza.base;

public class NucleoIspettivo {
	private int nucleo;
	private String componente;
	private int userId ;
	
	
	
	
	public NucleoIspettivo(int nucleo , String componente,int userId)
	{
		this.nucleo = nucleo;
		this.componente = componente;
		this.userId = userId;
	}
	
	
	
	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getNucleo() {
		return nucleo;
	}
	public void setNucleo(int nucleo) {
		this.nucleo = nucleo;
	}
	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	

}
