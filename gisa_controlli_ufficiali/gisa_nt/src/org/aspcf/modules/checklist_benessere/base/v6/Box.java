/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Box {

	private int id;
	private int enteredBy;
	private Timestamp entered;
	private int idChkBnsModIst;
	private int indice;
	private String numero;
	private String larghezza;
	private String lunghezza;
	private String peso;
	private String travetti;
	private String regolare;
	private String animali;
	private String categoria;
	private String fessure;
	private String pavimento;
	
	public Box(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");
		this.enteredBy = rs.getInt("entered_by");
		this.setEntered(rs.getTimestamp("entered"));
		this.indice = rs.getInt("indice");
		this.numero = rs.getString("numero");
		this.larghezza = rs.getString("larghezza");
		this.lunghezza = rs.getString("lunghezza");
		this.peso = rs.getString("peso");
		this.travetti = rs.getString("travetti");
		this.regolare = rs.getString("regolare");
		this.animali = rs.getString("animali");
		this.categoria = rs.getString("categoria");
		this.fessure = rs.getString("fessure");
		this.pavimento = rs.getString("pavimento");
			
		
	}
	public Box() {
		// TODO Auto-generated constructor stub
	}
	public String getPavimento() {
		return pavimento;
	}
	public void setPavimento(String pavimento) {
		this.pavimento = pavimento;
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
	public String getLarghezza() {
		return larghezza;
	}
	public void setLarghezza(String larghezza) {
		this.larghezza = larghezza;
	}
	public String getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(String lunghezza) {
		this.lunghezza = lunghezza;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	public String getTravetti() {
		return travetti;
	}
	public void setTravetti(String travetti) {
		this.travetti = travetti;
	}
	public String getRegolare() {
		return regolare;
	}
	public void setRegolare(String regolare) {
		this.regolare = regolare;
	}
	public String getAnimali() {
		return animali;
	}
	public void setAnimali(String animali) {
		this.animali = animali;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFessure() {
		return fessure;
	}
	public void setFessure(String fessure) {
		this.fessure = fessure;
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
	public void insert(Connection db) throws SQLException {
		int i = 0;
		PreparedStatement pst = null;
		pst = db.prepareStatement("insert into chk_bns_boxes_v6 (id_chk_bns_mod_ist, entered_by, indice, numero, larghezza, lunghezza, peso, travetti, regolare, animali, categoria, fessure, pavimento) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setInt(++i, this.idChkBnsModIst);
		pst.setInt(++i, this.enteredBy);
		pst.setInt(++i, this.indice);
		pst.setString(++i, this.numero);
		pst.setString(++i, this.larghezza);
		pst.setString(++i, this.lunghezza);
		pst.setString(++i, this.peso);
		pst.setString(++i, this.travetti);
		pst.setString(++i, this.regolare);
		pst.setString(++i, this.animali);
		pst.setString(++i, this.categoria);
		pst.setString(++i, this.fessure);
		pst.setString(++i, this.pavimento);
		pst.executeUpdate();		
	}
	public static ArrayList<Box> queryListByIdChkBnsModIst(Connection db, int idChkBnsModIst) throws SQLException {
		ArrayList<Box> boxes = new ArrayList<Box>();
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_boxes_v6 where id_chk_bns_mod_ist = ? and trashed_date is null order by indice asc");
		pst.setInt(1, idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Box b = new Box(rs);
			boxes.add(b);
		}
		return boxes;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	
}
