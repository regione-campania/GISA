/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Risposta {

	private int id;
	private int enteredBy;
	private Timestamp entered;
	private int idChkBnsModIst;
	private int idDomanda;
	private String risposta;
	private String evidenze;
	private String numIrregolarita;
	private String numProvvA;
	private String numProvvB;
	private String numProvvC;
	
	
	public Risposta(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	private void buildRecord (ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");
		this.idDomanda = rs.getInt("id_domanda");
		this.risposta = rs.getString("risposta");
		this.evidenze = rs.getString("evidenze");
		this.enteredBy = rs.getInt("entered_by");
		this.setEntered(rs.getTimestamp("entered"));
		this.numIrregolarita = rs.getString("num_irregolarita");
		this.numProvvA = rs.getString("num_provv_a");
		this.numProvvB = rs.getString("num_provv_b");
		this.numProvvC = rs.getString("num_provv_c");
	}
	
	public Risposta() {
		// TODO Auto-generated constructor stub
	}
	
	public Risposta(Connection db, int idDomanda, int idChkBnsModIst) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_risposte_v4 where id_chk_bns_mod_ist = ? and id_domanda = ? and trashed_date is null");
		pst.setInt(1, idChkBnsModIst);
		pst.setInt(2, idDomanda);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(int idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
	public String getEvidenze() {
		return evidenze;
	}
	public void setEvidenze(String evidenze) {
		this.evidenze = evidenze;
	}
	public int getIdChkBnsModIst() {
		return idChkBnsModIst;
	}
	public void setIdChkBnsModIst(int idChkBnsModIst) {
		this.idChkBnsModIst = idChkBnsModIst;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public void insert(Connection db) throws SQLException {
		int i = 0;
		PreparedStatement pst = null;
		pst = db.prepareStatement("insert into chk_bns_risposte_v4 (id_chk_bns_mod_ist, id_domanda, risposta, evidenze, num_irregolarita, num_provv_a, num_provv_b, num_provv_c, entered_by) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setInt(++i, this.idChkBnsModIst);
		pst.setInt(++i, this.idDomanda);
		pst.setString(++i, this.risposta);
		pst.setString(++i, this.evidenze);
		pst.setString(++i, this.numIrregolarita);
		pst.setString(++i, this.numProvvA);
		pst.setString(++i, this.numProvvB);
		pst.setString(++i, this.numProvvC);
		pst.setInt(++i, this.enteredBy);
		pst.executeUpdate();		
	}
	public static ArrayList<Risposta> queryListByIdChkBnsModIst(Connection db, int idChkBnsModIst) throws SQLException {
		ArrayList<Risposta> risposte = new ArrayList<Risposta>();
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_risposte_v4 where id_chk_bns_mod_ist = ? and trashed_date is null order by id_domanda asc");
		pst.setInt(1, idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Risposta r = new Risposta(rs);
			risposte.add(r);
		}
		return risposte;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public String getNumIrregolarita() {
		return numIrregolarita;
	}

	public void setNumIrregolarita(String numIrregolarita) {
		this.numIrregolarita = numIrregolarita;
	}

	public String getNumProvvA() {
		return numProvvA;
	}

	public void setNumProvvA(String numProvvA) {
		this.numProvvA = numProvvA;
	}

	public String getNumProvvB() {
		return numProvvB;
	}

	public void setNumProvvB(String numProvvB) {
		this.numProvvB = numProvvB;
	}

	public String getNumProvvC() {
		return numProvvC;
	}

	public void setNumProvvC(String numProvvC) {
		this.numProvvC = numProvvC;
	}
	
	
	
}
