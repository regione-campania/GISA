/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Rilascio extends GenericBean{
	
	
	private int id ;
	
	private String oggetto ;
	
	private String noteNote ;
	private String noteModulo ;
	private String noteFunzione ;
	private int noteIdContesto ;
	
	private Timestamp data ;
	private ArrayList<Riunione> listaRiunioni = new ArrayList<Riunione>();
	
	private int enteredby ; 
	private  Timestamp entered;
	

	public String getNoteNote() {
		return noteNote;
	}
	public void setNoteNote(String noteNote) {
		this.noteNote = noteNote;
	}
	public String getNoteModulo() {
		return noteModulo;
	}
	public void setNoteModulo(String noteModulo) {
		this.noteModulo = noteModulo;
	}
	public String getNoteFunzione() {
		return noteFunzione;
	}
	public void setNoteFunzione(String noteFunzione) {
		this.noteFunzione = noteFunzione;
	}
	public int getNoteIdContesto() {
		return noteIdContesto;
	}
	public void setNoteIdContesto(int noteIdContesto) {
		this.noteIdContesto = noteIdContesto;
	}
	public void setNoteIdContesto(String noteIdContesto) {
		try {this.noteIdContesto = Integer.parseInt(noteIdContesto);} catch(Exception e) {};
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public void setData(String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data!=null && !"".equals(data))
		{
			this.data = new Timestamp(sdf.parse(data).getTime());
		}
		
	}
	


	public void insert(Connection db)
	{

		
		 
		String insert = "INSERT INTO rilasci (id,oggetto,note_note, note_modulo, note_funzione, note_id_contesto,data,entered,enteredby) values ( ?,?,?, ?, ?, ?,?,CURRENT_TIMESTAMP,?)";
		PreparedStatement pst = null ;
		try
		{
			this.id = DatabaseUtils.getNextSeq(db, "rilasci_id_seq");
			
			int i = 0 ;
			pst = db.prepareStatement(insert);
			
			pst.setInt(++i, id);
			pst.setString(++i, oggetto);
			pst.setString(++i, noteNote);
			pst.setString(++i, noteModulo);
			pst.setString(++i, noteFunzione);
			pst.setInt(++i, noteIdContesto);
			pst.setTimestamp(++i, data);
			pst.setInt(++i, enteredby);
			pst.execute();
	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

		
		for (int j = 0; j < listaRiunioni.size(); j++){
		String insertRiunioni = "INSERT INTO rilasci_riunioni (id_rilascio, id_riunione) values (?, ?)";
		PreparedStatement pstRiunioni = null ;
		try
		{
			int i = 0 ;
			pstRiunioni = db.prepareStatement(insertRiunioni);
			pstRiunioni.setInt(++i, id);
			pstRiunioni.setInt(++i, listaRiunioni.get(j).getId());
			pstRiunioni.execute();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}

		}	
	}
	
	
	public void queryRecord(Connection db,int id) 
	{
		String select ="select * from rilasci where id =?" ;
		
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				loadResultSet(rs);
				
				buildListaRiunioni(db);
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
			oggetto = rs.getString("oggetto");
			noteNote =rs.getString("note_note");
			noteModulo=rs.getString("note_modulo");
			noteFunzione=rs.getString("note_funzione");
			noteIdContesto = rs.getInt("note_id_contesto");
			data=rs.getTimestamp("data");
			entered = rs.getTimestamp("entered");
			enteredby=rs.getInt("enteredby");
			
		}
		catch(SQLException e)
		{
			throw e ;
		}
	}
	
	public ArrayList<Riunione> getListaRiunioni() {
		return listaRiunioni;
	}
	public void setListaRiunioni(ArrayList<Riunione> listaRiunioni) {
		this.listaRiunioni = listaRiunioni;
	}
	
	private void buildListaRiunioni(Connection db){
		
		String select ="select * from rilasci_riunioni where id_rilascio =? and trashed_date is null order by id asc" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				Riunione riunione = new Riunione();
				riunione.queryRecord(db, rs.getInt("id_riunione"));
				listaRiunioni.add(riunione);
			}
				
		}
		catch(SQLException e)
		{
			
		}
		
	}

	
public ArrayList<Rilascio> getListaRilasciHomePage(Connection db){
	
	ArrayList<Rilascio> lista = new ArrayList<Rilascio>();
	
	String select ="select id from rilasci where note_id_contesto = 1 and trashed_date is null order by data desc" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(select);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				Rilascio rilascio = new Rilascio();
				rilascio.queryRecord(db, rs.getInt("id"));
				lista.add(rilascio);
			}
				
		}
		catch(SQLException e)
		{
			
		}
		return lista;
		
	}

}
