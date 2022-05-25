/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.constants;

import java.util.ArrayList;

public class ExtendedOptions {
	//GISA
	public ArrayList<String> Gisa = new ArrayList<String>(){{add("access");add("dpat");add("nucleo_ispettivo");}};
	
	//GISA_EXT
	public ArrayList<String> Gisa_ext = new ArrayList<String>(){{add("access");add("nucleo_ispettivo");}};
	
	//BDU
	public ArrayList<String> bdu = new ArrayList<String>();
	
	//VAM
	public ArrayList<String> Vam = new ArrayList<String>();
	
	//IMPORTATORI
	public ArrayList<String> Importatori = new ArrayList<String>();
	
	//DIGEMON
	public ArrayList<String> Digemon = new ArrayList<String>();

	
	public ArrayList<String> getListOptions(String endpoint){
		ArrayList<String> ret = null;
		switch (endpoint){
			case "Gisa" : ret = Gisa; break;
			case "Gisa_ext" : ret = Gisa_ext; break;
			case "bdu" : ret = bdu; break;
			case "Vam" : ret = Vam; break;
			case "Importatori" : ret = Importatori; break;
			case "Digemon" : ret = Digemon; break;

		}
		return ret;
	}
}
