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

import com.darkhorseventures.framework.beans.GenericBean;

public class ReferenteApprovazione extends GenericBean{
	
	private Referente referente ;
	private int stato ;
	private String note ;
	private Timestamp dataApprovazione ;
	private String revVerbale;
	public Referente getReferente() {
		return referente;
	}
	public void setReferente(Referente referente) {
		this.referente = referente;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public void setStato(String stato) {
		if (stato != null && !"".equals(stato))
		{
			this.stato = Integer.parseInt(stato);
		}
		
	}
	
	
	
	public String getRevVerbale() {
		return revVerbale;
	}
	public void setRevVerbale(String revVerbale) {
		this.revVerbale = revVerbale;
	}
	public String getNote() {
		if(note!=null)
		return note;
		return "" ;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Timestamp getDataApprovazione() {
		return dataApprovazione;
	}
	public void setDataApprovazione(Timestamp dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	
	public void setDataApprovazione(String dataApprovazione) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataApprovazione!=null && !"".equals(dataApprovazione))
		{
			this.dataApprovazione = new Timestamp(sdf.parse(dataApprovazione).getTime());
		}
		
	}
	
	
	public void loadResultSet(ResultSet rs) throws SQLException {
		  
		this.setDataApprovazione(rs.getTimestamp("data"));
		this.setStato(rs.getInt("stato"));
		this.setNote(rs.getString("note"));
		this.setRevVerbale(rs.getString("rev_verbale"));
		
		
	}
	
	
	
	public void updateStato(Connection db , int idRiunione,int modifiedby)
	{
		String update = "UPDATE referenti_approvazione_riunioni set note=?, rev_verbale = ? , stato = ?,data=current_timestamp,modified=current_timestamp,modifiedby=? where id_riunione = ? and id_referente = ? ";
		try
		{
			
			PreparedStatement pst = db.prepareStatement(update);
			pst.setString(1, note);
			pst.setString(2, revVerbale);
			pst.setInt(3, stato);
			pst.setInt(4, modifiedby);
			pst.setInt(5, idRiunione);
			pst.setInt(6, this.getReferente().getId());
			pst.execute();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	

	
}
