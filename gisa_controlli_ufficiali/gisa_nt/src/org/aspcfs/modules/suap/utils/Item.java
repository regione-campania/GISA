/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.utils;

public class Item
{
	private int id ;
	private String descizione ;
	private boolean previstoCodiceNazionale;
	private boolean flagBdu ;
	public Item(){}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescizione() {
		return descizione;
	}
	public void setDescizione(String descizione) {
		this.descizione = descizione;
	}
	public boolean isPrevistoCodiceNazionale() {
		return previstoCodiceNazionale;
	}
	public void setPrevistoCodiceNazionale(boolean previstoCodiceNazionale) {
		this.previstoCodiceNazionale = previstoCodiceNazionale;
	}
	public boolean isFlagBdu() {
		return flagBdu;
	}
	public void setFlagBdu(boolean flagBdu) {
		this.flagBdu = flagBdu;
	}
	
}