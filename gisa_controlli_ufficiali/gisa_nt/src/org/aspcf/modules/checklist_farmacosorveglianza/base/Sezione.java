/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_farmacosorveglianza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcf.modules.checklist_farmacosorveglianza.base.Domanda;

import com.darkhorseventures.framework.beans.GenericBean;

public class Sezione extends GenericBean {
										 	

  private static final long serialVersionUID = 1L;
 
  private int id;
  private int ordine =-1;
  private String sezione = "";
  private ArrayList<Domanda> listaDomande = new ArrayList<Domanda>();
  
  
public Sezione(Connection db, ResultSet rs) throws SQLException {
	buildRecord(rs);
}


public Sezione() {
	// TODO Auto-generated constructor stub
}



private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.ordine = rs.getInt("ordine");
	this.sezione = rs.getString("sezione");
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



public static ArrayList<Sezione> getListaSezioni (Connection db, int idIstanza) throws SQLException {
	ArrayList<Sezione> listaSezioni = new ArrayList<Sezione>();
	PreparedStatement pst = null;
	
	pst = db.prepareStatement("select * from farmacosorveglianza_sezioni where trashed_date is null order by ordine asc");
	
	ResultSet rs = pst.executeQuery();
	
	while (rs.next()){
		Sezione sezione = new Sezione();
		sezione.buildRecord(rs);
		sezione.setListaDomande(Domanda.getListaDomande(db, sezione.getId(), idIstanza));
		listaSezioni.add(sezione);
	}
	return listaSezioni;
}


public ArrayList<Domanda> getListaDomande() {
	return listaDomande;
}


public void setListaDomande(ArrayList<Domanda> listaDomande) {
	this.listaDomande = listaDomande;
}


  
  
} 
