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

public class DocumentaleAllegatoList extends Vector  {
	
	
	
	public void creaElenco(JSONArray jo){
		
		 for(int i = 2 ; i < jo.length(); i++){
			    String riga = jo.get(i).toString();{
			    DocumentaleAllegato doc = new DocumentaleAllegato(riga);
			    this.add(doc);
			    }
			}
		
	}
	
	public DocumentaleAllegatoList dividiPagine(int iniz, int fine){
		DocumentaleAllegatoList docList = new DocumentaleAllegatoList(); 
		
		for (int i = iniz; i < fine; i++){
			docList.add(this.get(i));
		}
		
		return docList;
	}
	
	public DocumentaleAllegato cercaNome(String nome){
		
		for (int i = 0; i < this.size(); i++){
			DocumentaleAllegato doc = (DocumentaleAllegato) this.get(i);
			if (doc.getOggetto().equalsIgnoreCase(nome))
				return doc;
		}
		return null;
	}
	
	
public DocumentaleAllegato cercTipoAllegato(String tipo){
		
		for (int i = 0; i < this.size(); i++){
			DocumentaleAllegato doc = (DocumentaleAllegato) this.get(i);
			if (doc.getTipoAllegato().equalsIgnoreCase(tipo))
				return doc;
		}
		return null;
	}
	
}
