/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.DatabaseUtils;

public class ImportLog {
	
	private int id =-1;
	private int idMacello = -1;
	private String  dataMacellazione;
	private int utenteImport = -1;
	private Timestamp dataImport;
	private boolean esitoImport = false;
	private String erroriImport;
	private String fileImport;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public void setIdMacello(String idMacello) {
		try {
		this.idMacello = Integer.parseInt(idMacello);} catch (Exception e){}
	}
	public String getDataMacellazione() {
		return dataMacellazione;
	}
	public void setDataMacellazione(String dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}
	public int getUtenteImport() {
		return utenteImport;
	}
	public void setUtenteImport(int utenteImport) {
		this.utenteImport = utenteImport;
	}
	public Timestamp getDataImport() {
		return dataImport;
	}
	public void setDataImport(Timestamp dataImport) {
		this.dataImport = dataImport;
	}
	public boolean isEsitoImport() {
		return esitoImport;
	}
	public void setEsitoImport(boolean esitoImport) {
		this.esitoImport = esitoImport;
	}
	public String getErroriImport() {
		return erroriImport;
	}
	public void setErroriImport(String erroriImport) {
		this.erroriImport = erroriImport;
	}
	public String getFileImport() {
		return fileImport;
	}
	public void setFileImport(String fileImport) {
		this.fileImport = fileImport;
	}
 
public ImportLog (ResultSet rs) throws SQLException{
	buildRecord(rs);
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.idMacello = rs.getInt("id_macello");
	this.dataMacellazione = rs.getString("data_macellazione");
	this.utenteImport = rs.getInt("utente_import");
	this.dataImport = rs.getTimestamp("data_import");
	this.esitoImport = rs.getBoolean("esito_import");
	this.erroriImport = rs.getString("errori_import");
	this.fileImport = rs.getString("file_import");
}

public ImportLog () throws SQLException{
	
}

public ImportLog (Connection db, int id) throws SQLException{
	StringBuffer sql = new StringBuffer();
	sql.append("select * from m_import_storico where id = ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	ResultSet rs = pst.executeQuery();
	while (rs.next())
		buildRecord(rs);
}


public void insert (Connection db){

	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO m_import_storico("
			+ "id_macello,  data_macellazione, utente_import,  data_import, file_import "
			+ ") values ("
			+ "?, ?, ?, now(), ? "
			+ "); ");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
		pst.setInt(++i, this.getIdMacello() );
		pst.setString(++i, this.getDataMacellazione());
		pst.setInt(++i, this.getUtenteImport());
		pst.setString(++i, this.getFileImport());
		pst.execute();
		this.id = DatabaseUtils.getCurrVal(db, "m_import_storico_id_seq",id);

		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}

public void updateEsito (Connection db){
	
	StringBuffer sql = new StringBuffer();
	sql.append("update m_import_storico "
			+ "set esito_import = ?, errori_import = ? where id = ? ");
			
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
		pst.setBoolean(++i,  this.isEsitoImport());
		pst.setString(++i, this.getErroriImport());
		pst.setInt(++i, this.getId() );
		pst.executeUpdate();

		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
