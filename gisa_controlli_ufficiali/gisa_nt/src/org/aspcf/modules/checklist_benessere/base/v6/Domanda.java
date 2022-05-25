/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.darkhorseventures.framework.beans.GenericBean;

public class Domanda extends GenericBean {
										 	

  private static final long serialVersionUID = 1L;
 
  private int id;
  private int idmod;
  private int level;
  private int irrId;
  private int dettIrrId;
  private String descrizione;
  private String titolo;
  private String sottotitolo;
  private String quesito;
  private String esito;
  private String evidenze;
  private ArrayList<Esito> esiti = new ArrayList<Esito>();
  private Risposta risposta = new Risposta();
  
public Domanda(Connection db, ResultSet rs, int idChkBnsModIst) throws SQLException {
	buildRecord(rs);
	setEsiti(db);
	if (idChkBnsModIst>0)
		setRisposta(db, idChkBnsModIst);
}

private void setRisposta(Connection db, int idChkBnsModIst) throws SQLException {
	Risposta r = new Risposta(db, this.id, idChkBnsModIst);
	this.risposta = r;
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.idmod = rs.getInt("idmod");
	this.level = rs.getInt("level");
	this.irrId = rs.getInt("irr_id");
	this.dettIrrId = rs.getInt("dettirrid");
	this.descrizione = rs.getString("descrizione");
	this.titolo = rs.getString("titolo");
	this.sottotitolo = rs.getString("sottotitolo");
	this.quesito = rs.getString("quesito");
	this.evidenze = rs.getString("evidenze");
	this.esito = rs.getString("esito");
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdmod() {
	return idmod;
}
public void setIdmod(int idmod) {
	this.idmod = idmod;
}
public int getLevel() {
	return level;
}
public void setLevel(int level) {
	this.level = level;
}
public int getIrrId() {
	return irrId;
}
public void setIrrId(int irrId) {
	this.irrId = irrId;
}
public int getDettIrrId() {
	return dettIrrId;
}
public void setDettIrrId(int dettIrrId) {
	this.dettIrrId = dettIrrId;
}
public String getDescrizione() {
	return descrizione;
}
public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}
public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}
public String getQuesito() {
	return quesito;
}
public void setQuesito(String quesito) {
	this.quesito = quesito;
}
public String getEsito() {
	return esito;
}
public void setEsito(String esito) {
	this.esito = esito;
}
  
public void setEsiti(Connection db) throws SQLException{
	ArrayList<Esito> esitiTemp = new ArrayList<Esito>();
	if (esito != null){
		String[] esitoArray = esito.replaceAll(" ", "").split(",");
		for (int i = 0; i<esitoArray.length; i++){
			Esito es = new Esito(db, esitoArray[i]);
			esitiTemp.add(es);
		}
	}
	this.esiti = esitiTemp; 
}
public String getSottotitolo() {
	return sottotitolo;
}
public void setSottotitolo(String sottotitolo) {
	this.sottotitolo = sottotitolo;
}
public String getEvidenze() {
	return evidenze;
}
public void setEvidenze(String evidenze) {
	this.evidenze = evidenze;
}

public ArrayList<Esito> getEsiti() {
	return esiti;
}

public void setEsiti(ArrayList<Esito> esiti) {
	this.esiti = esiti;
}

public Risposta getRisposta() {
	return risposta;
}

public void setRisposta(Risposta risposta) {
	this.risposta = risposta;
}
  
  
  
} 
