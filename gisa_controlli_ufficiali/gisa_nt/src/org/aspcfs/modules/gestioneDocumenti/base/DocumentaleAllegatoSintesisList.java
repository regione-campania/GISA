/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.json.JSONArray;

public class DocumentaleAllegatoSintesisList extends Vector  {
	
	
	
	public void creaElenco(JSONArray jo){
		
		 for(int i = 0 ; i < jo.length(); i++){
			    String riga = jo.get(i).toString();{
			    DocumentaleAllegatoSintesis doc = new DocumentaleAllegatoSintesis(riga);
			    this.add(doc);
			    }
			}
		
	}
	
	public DocumentaleAllegatoSintesisList dividiPagine(int iniz, int fine){
		DocumentaleAllegatoSintesisList docList = new DocumentaleAllegatoSintesisList(); 
		
		for (int i = iniz; i < fine; i++){
			docList.add(this.get(i));
		}
		
		return docList;
	}
	

}
