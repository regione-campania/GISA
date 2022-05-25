/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.util.ArrayList;
import java.util.HashMap;

public class BeanPerXmlRichiesta {
	String campo1;
	String campo2;
	ArrayList<HashMap<String,String>> listaEntries;
	
	public String getCampo1() {
		return campo1;
	}
	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}
	public String getCampo2() {
		return campo2;
	}
	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}
	public void setListaEntries(ArrayList<HashMap<String, String>> entries) {
		listaEntries = entries;
		
	}
	
	public ArrayList<HashMap<String,String>> getListaEntries()
	{
		return listaEntries;
	}
	
	
	
}
