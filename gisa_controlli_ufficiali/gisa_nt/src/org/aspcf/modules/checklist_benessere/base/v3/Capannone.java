/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Capannone {

	private int id;
	private int enteredBy;
	private Timestamp entered;
	private int idChkBnsModIst;
	private int indice;
	private String numero;
	private String capacita;
	private String animali;
	private String numTotaleBox;
	private String numTotaleBoxAttivi;
	private String ispezionato;
	
	public Capannone(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");
		this.enteredBy = rs.getInt("entered_by");
		this.setEntered(rs.getTimestamp("entered"));
		this.indice = rs.getInt("indice");
		this.numero = rs.getString("numero");
		this.capacita = rs.getString("capacita");
		this.animali = rs.getString("animali");
		this.numTotaleBox = rs.getString("num_totale_box");
		this.numTotaleBoxAttivi = rs.getString("num_totale_box_attivi");
		this.ispezionato = rs.getString("ispezionato");
			
		
	}
	public Capannone() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	public String getAnimali() {
		return animali;
	}
	public void setAnimali(String animali) {
		this.animali = animali;
	}
	
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public int getIdChkBnsModIst() {
		return idChkBnsModIst;
	}
	public void setIdChkBnsModIst(int idChkBnsModIst) {
		this.idChkBnsModIst = idChkBnsModIst;
	}
	
	public String getCapacita() {
		return capacita;
	}
	public void setCapacita(String capacita) {
		this.capacita = capacita;
	}
	public String getNumTotaleBox() {
		return numTotaleBox;
	}
	public void setNumTotaleBox(String numTotaleBox) {
		this.numTotaleBox = numTotaleBox;
	}
	public String getNumTotaleBoxAttivi() {
		return numTotaleBoxAttivi;
	}
	public void setNumTotaleBoxAttivi(String numTotaleBoxAttivi) {
		this.numTotaleBoxAttivi = numTotaleBoxAttivi;
	}
	public String getIspezionato() {
		return ispezionato;
	}
	public void setIspezionato(String ispezionato) {
		this.ispezionato = ispezionato;
	}
	public void insert(Connection db) throws SQLException {
		int i = 0;
		PreparedStatement pst = null;
		pst = db.prepareStatement("insert into chk_bns_capannoni_v3 (id_chk_bns_mod_ist, entered_by, indice, numero, capacita, animali, num_totale_box, num_totale_box_attivi, ispezionato) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setInt(++i, this.idChkBnsModIst);
		pst.setInt(++i, this.enteredBy);
		pst.setInt(++i, this.indice);
		pst.setString(++i, this.numero);
		pst.setString(++i, this.capacita);
		pst.setString(++i, this.animali);
		pst.setString(++i, this.numTotaleBox);
		pst.setString(++i, this.numTotaleBoxAttivi);
		pst.setString(++i, this.ispezionato);
		if (this.numero!=null && !this.numero.equals("")) 
			pst.executeUpdate();		
	}
	public static ArrayList<Capannone> queryListByIdChkBnsModIst(Connection db, int idChkBnsModIst) throws SQLException {
		ArrayList<Capannone> capannoni = new ArrayList<Capannone>();
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_capannoni_v3 where id_chk_bns_mod_ist = ? and trashed_date is null order by indice asc");
		pst.setInt(1, idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Capannone cap = new Capannone(rs);
			capannoni.add(cap);
		}
		return capannoni;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	
}
