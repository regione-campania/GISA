/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SintesisMercatoIttico {


	
	private int id = -1;
	private String ragioneSociale = null;
	private int numBox = -1;
	private int idComune = -1;
	private int idRelazione = -1;
	private int enteredBy;
	private Timestamp entered;
	
	public SintesisMercatoIttico() {
		// TODO Auto-generated constructor stub
	}
	
	public SintesisMercatoIttico(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public SintesisMercatoIttico(Connection db, String id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sintesis_mercato_ittico where id = ? and trashed_date is null");
		pst.setInt(1, Integer.parseInt(id));
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			buildRecord(rs);
		}
	}

	public void buildRecord(ResultSet rs) throws SQLException{
		id = rs.getInt("id");
		numBox = rs.getInt("num_box");
		ragioneSociale = rs.getString("ragione_sociale");
		idComune = rs.getInt("id_comune");
		idRelazione = rs.getInt("id_sintesis_rel_stab_lp");
		enteredBy = rs.getInt("entered_by");
		entered = rs.getTimestamp("entered");
	}
	
	
	public static ArrayList<SintesisMercatoIttico> getElencoOperatori(Connection db, int idRelazione) throws SQLException {
			ArrayList<SintesisMercatoIttico> lista = new  ArrayList<SintesisMercatoIttico>();
		 		
				PreparedStatement pst = db.prepareStatement("select * from sintesis_mercato_ittico where id_sintesis_rel_stab_lp = ? and trashed_date is null order by num_box asc");
				pst.setInt(1, idRelazione);
				ResultSet rs = pst.executeQuery();
				while (rs.next()){
					SintesisMercatoIttico mercato = new SintesisMercatoIttico(rs);
					lista.add(mercato);		
				}
			
			return lista;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public int getNumBox() {
		return numBox;
	}

	public void setNumBox(int numBox) {
		this.numBox = numBox;
	}
	
	public void setNumBox(String numBox) {
		 try { this.numBox = Integer.parseInt(numBox); } catch (Exception e) {}
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}
	
	public void setIdComune(String idComune) {
		try { this.idComune = Integer.parseInt(idComune); } catch (Exception e) {}
	}

	public int getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(int idRelazione) {
		this.idRelazione = idRelazione;
	}
	
	public void setIdRelazione(String idRelazione) {
		this.idRelazione = Integer.parseInt(idRelazione);
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public void insert(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into sintesis_mercato_ittico (id_sintesis_rel_stab_lp, num_box, ragione_sociale, id_comune, entered_by) values (?, ?, ?, ?, ?)");
		pst.setInt(1, idRelazione);
		pst.setInt(2, numBox);
		pst.setString(3, ragioneSociale);
		pst.setInt(4, idComune);
		pst.setInt(5, idUtente);
		pst.executeUpdate();			
	}

	public void update(Connection db, int idUtente) throws SQLException {
		delete(db, idUtente);
		insert(db, idUtente);
	}

	public void delete(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update sintesis_mercato_ittico set trashed_date = now(), trashed_by = ? where id = ?");
		pst.setInt(1, idUtente);
		pst.setInt(2, id);
		pst.executeUpdate();		
	}

}
