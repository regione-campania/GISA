/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OrganizeLEOE {
	
	
	public static void organize (ArrayList<LookupEsameObiettivoEsito> listEsameObiettivo) {
		
		 	HashMap<LookupEsameObiettivoEsito, ArrayList<LookupEsameObiettivoEsito>> hash = new HashMap<LookupEsameObiettivoEsito, ArrayList<LookupEsameObiettivoEsito>>();
			
		 	LookupEsameObiettivoEsito leoePadre;
			LookupEsameObiettivoEsito leoeFiglio;
			
			ArrayList<LookupEsameObiettivoEsito> listaFigli = null; 
		 	
		 	for (int i=0;i<listEsameObiettivo.size();i++) {
		 		leoePadre = (LookupEsameObiettivoEsito) listEsameObiettivo.get(i);
		 		//System.out.println("Padre" + leoePadre.getDescription());
		 		
		 		for(int j=0;j<listEsameObiettivo.size();j++) {
		 			
		 			listaFigli = new ArrayList();
		 			
		 			leoeFiglio = (LookupEsameObiettivoEsito)listEsameObiettivo.get(j);
		 			//System.out.println("Figlio" + leoeFiglio.getDescription());
		 			
		 			if(leoeFiglio.getLookupEsameObiettivoEsito()!=null){
						if(leoeFiglio.getLookupEsameObiettivoEsito().getId()==leoePadre.getId()) {
							System.out.println(leoeFiglio.getDescription() +"è figlio di"+ leoePadre.getDescription());
							listaFigli.add(leoeFiglio);
						}
					}
		 		
		 		}
		 		hash.put(leoePadre, listaFigli);
		 	}
		 	
		 	
			
			Set set = hash.entrySet();
		    Iterator i = set.iterator();

		    while(i.hasNext()){
		     
		    	System.out.println("Scorro HASH");		     
		    	Map.Entry me = (Map.Entry)i.next();     
		     
		    	LookupEsameObiettivoEsito leoeK = (LookupEsameObiettivoEsito)me.getKey();
		    	
		    	System.out.println("Padre" + leoeK.getDescription());
		      
		    	ArrayList<LookupEsameObiettivoEsito> leoeV = (ArrayList<LookupEsameObiettivoEsito>)me.getValue();;
		      
		      
		    	LookupEsameObiettivoEsito leoeValue;
		      
		     
		      for(int k=0;k<leoeV.size();k++) {
		    	  
		    	  leoeValue = (LookupEsameObiettivoEsito)leoeV.get(k);
		    	  
		    	  System.out.println(leoeK.getDescription() + " : " + leoeValue.getDescription() );
		      
		      }
		    }
		
	}

}
