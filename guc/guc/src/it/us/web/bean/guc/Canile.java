/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.guc;

public class Canile {
	
	private int idAsl;
	private int idCanile;
	private String descrizioneCanile;
	
	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public int getIdCanile() {
		return idCanile;
	}
	public void setIdCanile(int idCanile) {
		this.idCanile = idCanile;
	}
	public String getDescrizioneCanile() {
		return descrizioneCanile;
	}
	public void setDescrizioneCanile(String descrizioneCanile) {
		this.descrizioneCanile = descrizioneCanile;
	}
	
	@Override
	public String toString(){
		return getIdCanile()+"";
	} 

}
