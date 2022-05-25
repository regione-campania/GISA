/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.devdoc.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Modulo extends GenericBean{
	
	
	private int id ;
	private int idTipo ;
	private int idFlusso ;
	private Timestamp data;
	private int idUtente;
	private boolean nonDisponibile = false;
	
	private ArrayList<ModuloNote> note = new ArrayList<ModuloNote>();

	public Modulo(ResultSet rs) throws SQLException {
		// TODO Auto-generated constructor stub
		loadResultSet(rs);
	}


	public Modulo() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdTipo() {
		return idTipo;
	}


	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}


	public int getIdFlusso() {
		return idFlusso;
	}


	public void setIdFlusso(int idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Timestamp getData() {
		return data;
	}


	public void setData(Timestamp data) {
		this.data = data;
	}


	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}


	public void setIdTipo(String idTipo) {
		try {this.idTipo = Integer.parseInt(idTipo);} catch (Exception e) {}		
	}
	
	public void setIdFlusso(String idFlusso) {
		try {this.idFlusso = Integer.parseInt(idFlusso);} catch (Exception e) {}		
	}
	
	public ArrayList<ModuloNote> getNote() {
		return note;
	}


	public void setNote(ArrayList<ModuloNote> note) {
		this.note = note;
	}


	public boolean isNonDisponibile() {
		return nonDisponibile;
	}


	public void setNonDisponibile(boolean nonDisponibile) {
		this.nonDisponibile = nonDisponibile;
	}


	public void gestioneInserimento(Connection db){
		Modulo mod = new Modulo();
		mod.queryRecord(db, idFlusso, idTipo);
		if (mod.getId()>0)
			this.id = mod.getId();
		else
			insert(db);
	}

	public void insert(Connection db)
	{
	 	String insert = "INSERT INTO sviluppo_moduli (id, id_tipo, id_flusso, data, id_utente, non_disponibile) values ( ?,?, ?, now(), ?, ?)";
		PreparedStatement pst = null ;
		try
		{
			this.id = DatabaseUtils.getNextSeq(db, "sviluppo_moduli_id_seq");
			
			int i = 0 ;
			pst = db.prepareStatement(insert);
			
			pst.setInt(++i, id);
			pst.setInt(++i, idTipo);
			pst.setInt(++i, idFlusso);
			pst.setInt(++i, idUtente);
			pst.setBoolean(++i, nonDisponibile);
			pst.execute();
	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void queryRecord(Connection db,int id) 
	{
		String select ="select * from sviluppo_moduli where id =? and data_cancellazione is null" ;
		
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				loadResultSet(rs);
				ModuloNote note = new ModuloNote();
				ArrayList<ModuloNote> notes = note.estraiNote(db, id);
				this.setNote(notes);
			}
				
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	public void queryRecord(Connection db,int idFlusso, int idTipo) 
	{
		String select ="select * from sviluppo_moduli where id_flusso = ? and id_tipo = ? and data_cancellazione is null limit 1" ;
		
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, idFlusso);
			pst.setInt(2, idTipo);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				loadResultSet(rs);
				ModuloNote note = new ModuloNote();
				ArrayList<ModuloNote> notes = note.estraiNote(db, id);
				this.setNote(notes);
			}
				
		}
		catch(SQLException e)
		{
			
		}
		
	}
	
	
	public void loadResultSet (ResultSet rs) throws SQLException
	{
		
		try
		{
			id =rs.getInt("id");
			idTipo = rs.getInt("id_tipo");
			idFlusso =rs.getInt("id_flusso");
			data=rs.getTimestamp("data");
			idUtente=rs.getInt("id_utente");
			nonDisponibile = rs.getBoolean("non_disponibile");
			}
		catch(SQLException e)
		{
			throw e ;
		}
	}

	public ModuloList estraiModuli (Connection db, int idFlusso) {
		// TODO Auto-generated constructor stub
		this.idFlusso = idFlusso;
		ModuloList moduli = new ModuloList();
		
		String sql = "select * from sviluppo_moduli where id_flusso = ? and data_cancellazione is null";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idFlusso);
			ResultSet rs= pst.executeQuery();
		
			while (rs.next()){
				Modulo modulo = new Modulo();
				modulo.loadResultSet(rs);
				moduli.add(modulo);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moduli;
	}

	public boolean isModuloDisponibile(Connection db) throws SQLException{
		boolean disponibile = true;
		
		PreparedStatement pst = db.prepareStatement("select non_disponibile from sviluppo_moduli where id_tipo = "+idTipo+" and id_flusso = "+idFlusso+" order by data desc limit 1");
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			if (rs.getBoolean("non_disponibile")==true)
				disponibile = false;
		}
		return disponibile;
	}
}
