/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.base;

import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.json.JSONArray;

public class DocumentaleDocumentoList extends Vector  {
	
	
	
	public void creaElenco(JSONArray jo){
		
		 for(int i = 0 ; i < jo.length(); i++){
			    String riga = jo.get(i).toString();{
			    DocumentaleDocumento doc = new DocumentaleDocumento(riga);
			    this.add(doc);
			    }
			}
		
	}
	

}
