/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.aspcfs.modules.vigilanza.base.Ticket;


public class OggettoControllo {
	
	String altro_motivo_ispezione;
	String altro_settore_consumo_umano;
	String altro_settore_zootecnici;
	String altro_settore_benessere_animale;
	String altro_settore_sanita_animale;
	String altro_settore_impianti;
	String altro_settore_rifiuti;
	String altro_altro;
	String altro_settore_trasporto;
	
	private HashMap<Integer, HashMap<Integer, String>> listaElementi;
	
	public OggettoControllo(){}
	
	public OggettoControllo(Connection db, int idControllo) {
		
		try {
			
			Ticket cu = new Ticket(db, idControllo);
			HashMap<Integer, HashMap<Integer, String>> listaElementi = cu.getLisaElementi_Ispezioni();
			this.setElementiIspezioni(listaElementi);	
			this.setAltro_motivo_ispezione(cu.getIspezioneAltro());
			this.setAltro_settore_consumo_umano(cu.getIspezioni_desc1());
			this.setAltro_settore_zootecnici(cu.getIspezioni_desc2());
			this.setAltro_settore_benessere_animale(cu.getIspezioni_desc3());
			this.setAltro_settore_sanita_animale(cu.getIspezioni_desc4());
			this.setAltro_settore_impianti(cu.getIspezioni_desc5());
			this.setAltro_settore_rifiuti(cu.getIspezioni_desc6());
			this.setAltro_altro(cu.getIspezioni_desc7());
			this.setAltro_settore_trasporto(cu.getIspezioni_desc8());
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
	}
	
	private void setElementiIspezioni(
			HashMap<Integer, HashMap<Integer, String>> listaElementi) {
			// TODO Auto-generated method stub
		this.listaElementi = listaElementi;
	}

	public String getAltro_motivo_ispezione() {
		return altro_motivo_ispezione;
	}
	public void setAltro_motivo_ispezione(String altro_motivo_ispezione) {
		this.altro_motivo_ispezione = altro_motivo_ispezione;
	}
	public String getAltro_settore_consumo_umano() {
		return altro_settore_consumo_umano;
	}
	public void setAltro_settore_consumo_umano(String altro_settore_consumo_umano) {
		this.altro_settore_consumo_umano = altro_settore_consumo_umano;
	}
	public String getAltro_settore_zootecnici() {
		return altro_settore_zootecnici;
	}
	public void setAltro_settore_zootecnici(String altro_settore_zootecnici) {
		this.altro_settore_zootecnici = altro_settore_zootecnici;
	}
	public String getAltro_settore_benessere_animale() {
		return altro_settore_benessere_animale;
	}
	public void setAltro_settore_benessere_animale(
			String altro_settore_benessere_animale) {
		this.altro_settore_benessere_animale = altro_settore_benessere_animale;
	}
	public String getAltro_settore_sanita_animale() {
		return altro_settore_sanita_animale;
	}
	public void setAltro_settore_sanita_animale(String altro_setore_sanita_animale) {
		this.altro_settore_sanita_animale = altro_setore_sanita_animale;
	}
	public String getAltro_settore_impianti() {
		return altro_settore_impianti;
	}
	public void setAltro_settore_impianti(String altro_settore_impianti) {
		this.altro_settore_impianti = altro_settore_impianti;
	}
	public String getAltro_settore_rifiuti() {
		return altro_settore_rifiuti;
	}
	public void setAltro_settore_rifiuti(String altro_settore_rifiuti) {
		this.altro_settore_rifiuti = altro_settore_rifiuti;
	}
	public String getAltro_altro() {
		return altro_altro;
	}
	public void setAltro_altro(String altro_altro) {
		this.altro_altro = altro_altro;
	}
	public String getAltro_settore_trasporto() {
		return altro_settore_trasporto;
	}
	public void setAltro_settore_trasporto(String altro_settore_trasporto) {
		this.altro_settore_trasporto = altro_settore_trasporto;
	}
	
	public boolean isChecked(int code)
	{
		boolean checked = false;
			
		//String settore_benessere_trasporto = "";
	    for ( int key : listaElementi.keySet() ){
			HashMap<Integer, String> hash_oggetto_controllo =  listaElementi.get(key);
			for ( int internalKey : hash_oggetto_controllo.keySet()) {
				if(internalKey == code){
					checked = true;
					break;
				}						
			}
		}
		
		return checked;
		
	}
	
	
	
	
}
