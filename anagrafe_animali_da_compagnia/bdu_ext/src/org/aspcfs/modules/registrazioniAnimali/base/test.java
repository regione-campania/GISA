/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.SQLException;

public class test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventoList listaEventi = new EventoList();
		
		EventoRegistrazioneBDU e1 = new EventoRegistrazioneBDU();
		
		listaEventi.add(e1);
		
		EventoSterilizzazione e2 = new EventoSterilizzazione();
		
		listaEventi.add(e2);
		
		GestoreEventi.setListaEventi(listaEventi);

		for (int i = 0; i < listaEventi.size(); i++){
			Evento e = (Evento) listaEventi.get(i);
			try {
				e.insert(null);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			
		}
		
	//	GestoreEventi.aggiungiEventi(null);
		
/*		listaEventi.setFlag_richiesta_contributo_regionale(true);
		listaEventi.setId_asl_registrazione(0);
		listaEventi.setId_utente_inserimento(12);*/
		
/*		try {
			//listaEventi.buildList(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
	}
}