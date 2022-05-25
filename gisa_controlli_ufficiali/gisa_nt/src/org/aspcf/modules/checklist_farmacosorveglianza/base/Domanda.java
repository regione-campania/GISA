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

import com.darkhorseventures.framework.beans.GenericBean;

public class Domanda extends GenericBean {
										 	

  private static final long serialVersionUID = 1L;
 
  private int id;
  private int ordine =-1;
  private int idSezione = -1;
  private String domanda = "";
  private String note = "";
 
  private ArrayList<Giudizio> listaGiudizi = new ArrayList<Giudizio>();
  private String evidenze = "";
  
public Domanda(Connection db, ResultSet rs) throws SQLException {
	buildRecord(rs);
}


public Domanda() {
	// TODO Auto-generated constructor stub
}



private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.ordine = rs.getInt("ordine");
	this.idSezione = rs.getInt("id_sezione");
	this.domanda = rs.getString("domanda");
	this.note = rs.getString("note");

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


public int getIdSezione() {
	return idSezione;
}


public void setIdSezione(int idSezione) {
	this.idSezione = idSezione;
}


public String getDomanda() {
	return domanda;
}


public void setDomanda(String domanda) {
	this.domanda = domanda;
}


public String getNote() {
	return note;
}


public void setNote(String note) {
	this.note = note;
}


public static ArrayList<Domanda> getListaDomande(Connection db, int idSezione, int idIstanza) throws SQLException {
	ArrayList<Domanda> listaDomande = new ArrayList<Domanda>();
	PreparedStatement pst = null;
	
	pst = db.prepareStatement("select * from farmacosorveglianza_domande where trashed_date is null and id_sezione = ? order by ordine asc");
	pst.setInt(1, idSezione);
	
	ResultSet rs = pst.executeQuery();
	
	while (rs.next()){
		Domanda domanda = new Domanda();
		domanda.buildRecord(rs);
		domanda.setListaGiudizi(Giudizio.getListaGiudizi(db, domanda.getId(), idIstanza));
		
		if (idIstanza > 0)
			domanda.setEvidenze(Domanda.getEvidenze(db, domanda.getId(), idIstanza));
		
		listaDomande.add(domanda);
	}
	return listaDomande;
}


private static String getEvidenze(Connection db, int idDomanda, int idIstanza) throws SQLException {
	String evidenze = "";
	PreparedStatement pst = null;
	
	pst = db.prepareStatement("select * from farmacosorveglianza_risposte_evidenze where trashed_date is null and id_domanda = ? and id_istanza = ?");
	pst.setInt(1, idDomanda);
	pst.setInt(2, idIstanza);

	ResultSet rs = pst.executeQuery();
	
	if (rs.next()){
		evidenze = rs.getString("evidenze");
	}
	
	return evidenze;
}


public ArrayList<Giudizio> getListaGiudizi() {
	return listaGiudizi;
}


public void setListaGiudizi(ArrayList<Giudizio> listaGiudizi) {
	this.listaGiudizi = listaGiudizi;
}


public String getEvidenze() {
	return evidenze;
}


public void setEvidenze(String evidenze) {
	this.evidenze = evidenze;
}

public void insertEvidenza(Connection db, int idIstanza, int enteredBy) throws SQLException {
	int i = 0;
	PreparedStatement pst = null;
	pst = db.prepareStatement("insert into farmacosorveglianza_risposte_evidenze (id_istanza, id_domanda, evidenze) values (?, ?, ?)");
	pst.setInt(++i, idIstanza);
	pst.setInt(++i, this.id);
	pst.setString(++i, this.evidenze);
	pst.executeUpdate();		
}

  
  
} 
