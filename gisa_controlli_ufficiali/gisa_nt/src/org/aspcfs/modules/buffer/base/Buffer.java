/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.buffer.base;

import java.util.ArrayList;
import java.util.Locale;

import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Buffer extends org.aspcfs.modules.troubletickets.base.Ticket
{
	
	private int id ;
	private String descrizioneBreve ;
	private String note ;
	private int stato ;
	private Timestamp dataStato ;
	private Timestamp dataEvento ;
	private ArrayList<Comune> listaComuni = new ArrayList<Comune>();
	
	private String descrStato ;
	
	private Timestamp entered ;
	private Timestamp modified ;
	private int enteredby ;
	private int modifiedby ;
	private String codiceBuffer ;
	
	

	public Buffer (){}
	
	
	public String getCodiceBuffer() {
		return codiceBuffer;
	}


	public void setCodiceBuffer(String codiceBuffer) {
		this.codiceBuffer = codiceBuffer;
	}


	public void setDataEvento(String dataEvento) {
		this.dataEvento = DatabaseUtils.parseDateToTimestamp(dataEvento, Locale.ITALIAN);;
	}
	
	public void setStato(String stato)
	{
		this.stato = Integer.parseInt(stato);
		
	}
	public void setDataStato(String dataStato) {
		this.dataStato = DatabaseUtils.parseDateToTimestamp(dataStato, Locale.ITALIAN);;;
	}
	
	public Timestamp getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Timestamp dataEvento) {
		this.dataEvento = dataEvento;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}
	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public Timestamp getDataStato() {
		return dataStato;
	}
	public void setDataStato(Timestamp dataStato) {
		this.dataStato = dataStato;
	}
	public ArrayList<Comune> getListaComuni() {
		return listaComuni;
	}
	public void setListaComuni(ArrayList<Comune> listaComuni) {
		this.listaComuni = listaComuni;
	}
	public String getDescrStato() {
		return descrStato;
	}
	public void setDescrStato(String descrStato) {
		this.descrStato = descrStato;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	
	public boolean insert(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		//boolean commit = db.getAutoCommit();
		try {
		
		//		db.setAutoCommit(false);
		
			
		     id =  DatabaseUtils.getNextSeq(db, context,"buffer","id");

			StringBuffer sb = new StringBuffer("");
			sb.append("insert into buffer (id , descrizione_breve , note ,stato , data_stato ,entered, modified,enteredby,modifiedby,data_evento,codice_buffer) " +
					"values (?,?,?,?,current_timestamp,current_timestamp,current_timestamp,?,?,?,?)") ;
			
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(sb.toString());
			pst.setInt(++i,this.id);
			pst.setString(++i,this.descrizioneBreve);
			pst.setString(++i,this.getNote());
			pst.setInt(++i,this.getStato());
			pst.setInt(++i,this.enteredby);
			pst.setInt(++i,this.modifiedby);
			pst.setTimestamp(++i,this.getDataEvento());
			pst.setString(++i, this.getCodiceBuffer());

			pst.execute() ;
			
			this.insertComuni(db) ;
			this.insertStorico(db,context);
			
			
			//db.commit();
			//db.setAutoCommit(true);
			
			
		}catch (SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			 
		}
			return true ;
		}
	
	
	public int update(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
		
		//boolean commit = db.getAutoCommit();
		try {
			/*if (commit) {
				db.setAutoCommit(false);
			}*/
			
			
			StringBuffer sb = new StringBuffer("");
			sb.append("update buffer set descrizione_breve= ? , data_evento = ?, note = ?  ,stato = ?  , data_stato = current_timestamp  , modified = current_date , modifiedby = ? where id = ? ") ;
			
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(sb.toString());
		
			pst.setString(++i,this.descrizioneBreve);
			pst.setTimestamp(++i,this.getDataEvento());
			pst.setString(++i,this.getNote());
			pst.setInt(++i,this.getStato());
			//pst.setInt(++i,this.enteredby);
			pst.setInt(++i,this.modifiedby);
			pst.setInt(++i,this.id);
			pst.execute() ;
			this.insertComuni(db) ;
			this.insertStorico(db,context);
			//db.setAutoCommit(true);
			//db.commit();
			
			
			
			return 0 ;
		}catch (SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			
		}
			return 0 ;
		}
	
	
	
	public boolean insertStorico(Connection db,ActionContext context) throws SQLException {
		StringBuffer sql = new StringBuffer();
	
		try {
			
			
		    // id =  DatabaseUtils.getNextSeq(db, context,"buffer_storico","id");

			StringBuffer sb = new StringBuffer("");
			sb.append("insert into buffer_storico (id , descrizione_breve , note ,stato , data_stato ,entered, modified,enteredby,modifiedby,id_buffer,data_evento,codice_buffer) " +
					"values (?,?,?,?,current_timestamp,current_timestamp,current_timestamp,?,?,?,?,?)") ;
			
			int i = 0 ;
			PreparedStatement pst = db.prepareStatement(sb.toString());
			pst.setInt(++i, DatabaseUtils.getNextSeq(db, context,"buffer_storico","id"));
			pst.setString(++i,this.descrizioneBreve);
			pst.setString(++i,this.getNote());
			pst.setInt(++i,this.getStato());
			pst.setInt(++i,this.enteredby);
			pst.setInt(++i,this.modifiedby);
			pst.setInt(++i,this.id);
			pst.setTimestamp(++i,this.dataEvento);
			pst.setString(++i, this.getCodiceBuffer());
			
			pst.execute() ;
			this.insertComuniStorico(db, id) ;
			
			
			
		}catch (SQLException e)
		{
		
			e.printStackTrace();
			
		}
			return true ;
		}
	
	public boolean insertComuni(Connection db) throws SQLException
	{
		
		StringBuffer sb = new StringBuffer("");
		sb.append("insert into buffer_comuni_coinvolti(id_buffer,id_comune) values (?,?)") ;
		
		db.prepareStatement("delete from buffer_comuni_coinvolti where id_buffer="+this.id).execute();
		PreparedStatement pst = db.prepareStatement(sb.toString());
		for (Comune comune : listaComuni)
		{
			pst.setInt(1, this.id);
			pst.setInt(2, comune.getId());
			pst.execute();
		}
		return true ;
		
		
	}
	
	
	public boolean insertComuniStorico(Connection db,int idBufferStorico) throws SQLException
	{
		
		StringBuffer sb = new StringBuffer("");
		sb.append("insert into buffer_comuni_coinvolti_storico(id_buffer_storico,id_comune) values (?,?)") ;
		PreparedStatement pst = db.prepareStatement(sb.toString());
		
		
		for (Comune comune : listaComuni)
		{
			pst.setInt(1, idBufferStorico);
			pst.setInt(2, comune.getId());
			pst.execute();
		}
		return true ;
		
		
	}
	
	public Buffer (Connection db, int idBuffer ) throws SQLException
	{
		queryRecord(db, idBuffer);
	}
	
	
	public Buffer (ResultSet rs ) throws SQLException
	{
		buildRecord( rs ) ;
	}
	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			throw new SQLException("Invalid Ticket Number");
		}
		PreparedStatement pst = db.prepareStatement(
				"select b.*,lbs.description as stato_desc from buffer b join lookup_buffer_stato lbs on lbs.code = b.stato where id = ?"); //modificata la tipologia. Messa = 700
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.buildRecord(rs);
			this.queryRecordComuni(db);
	
		}
	}
	
	public void queryRecordComuni(Connection db) throws SQLException
	{
		
		PreparedStatement pst = db.prepareStatement("select comuni1.id,comuni1.nome from buffer_comuni_coinvolti join comuni1 on comuni1.id = id_comune where comuni1.notused is null and id_buffer = ? and cod_regione= '15' "); //modificata la tipologia. Messa = 700
		pst.setInt(1, id);	
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			Comune c = new Comune();
			c.setId(rs.getInt("id"));
			c.setDescrizione(rs.getString("nome"));
			listaComuni.add(c);
			
		}
				
	}
	
	
	
	public void buildRecord(ResultSet rs ) throws SQLException
	{
		
		this.setId(rs.getInt("id"));
		this.setDescrizioneBreve(rs.getString("descrizione_breve"));
		this.setNote(rs.getString("note"));
		this.setStato(rs.getInt("stato"));
		this.setDescrStato(rs.getString("stato_desc"));
		this.setDataStato(rs.getTimestamp("data_stato"));
		this.setDataEvento(rs.getTimestamp("data_evento"));
		this.setCodiceBuffer(rs.getString("codice_buffer"));
		
		
		
		
		
	}

	
	
	
	
	
	
}
