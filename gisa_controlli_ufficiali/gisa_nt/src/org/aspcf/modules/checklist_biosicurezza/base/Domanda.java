/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_biosicurezza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.aspcf.modules.checklist_farmacosorveglianza.base.Giudizio;

import com.darkhorseventures.framework.beans.GenericBean;

public class Domanda extends GenericBean {
										 	

  private static final long serialVersionUID = 1L;
 
  private int id;
  private int ordine =-1;
  private String sezione ="";
  private String domanda = "";
  
  //risposta
  private ArrayList<Risposta> listaRisposte = new ArrayList<Risposta>();

 
  
public Domanda(Connection db, ResultSet rs) throws SQLException {
	buildRecord(rs);
}


public Domanda() {
	// TODO Auto-generated constructor stub
}


private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.ordine = rs.getInt("ordine");
	this.sezione = rs.getString("sezione");
	this.domanda = rs.getString("domanda");

}


public ArrayList<Domanda> getListaDomande (Connection db, int idIstanza, int codiceSpecie) throws SQLException {
	ArrayList<Domanda> listaDomande = new ArrayList<Domanda>();
	PreparedStatement pst = null;
	
	pst = db.prepareStatement("select sez.sezione, dom.domanda, dom.id, dom.ordine from biosicurezza_sezioni sez left join biosicurezza_domande dom on sez.id = dom.id_sezione where sez.trashed_date is null and dom.trashed_date is null and sez.cod_specie::integer = ? order by sez.ordine, dom.ordine");
	pst.setInt(1, codiceSpecie);
	
	ResultSet rs = pst.executeQuery();
	
	while (rs.next()){
		Domanda domanda = new Domanda();
		domanda.buildRecord(rs);
		domanda.setListaRisposte(Risposta.getListaRisposte(db, domanda.getId(), idIstanza));
				
		listaDomande.add(domanda);
	}
	return listaDomande;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getOrdine() {
	return ordine;
}


public void setOrdine(int ordine) {
	this.ordine = ordine;
}


public String getSezione() {
	return sezione;
}


public void setSezione(String sezione) {
	this.sezione = sezione;
}


public String getDomanda() {
	return domanda;
}


public void setDomanda(String domanda) {
	this.domanda = domanda;
}


public ArrayList<Risposta> getListaRisposte() {
	return listaRisposte;
}


public void setListaRisposte(ArrayList<Risposta> listaRisposte) {
	this.listaRisposte = listaRisposte;
}
  
  
} 
