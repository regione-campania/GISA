/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.DatabaseUtils;

public class LogImport {
	
	private int id =-1;
	private int utenteImport = -1;
	private Timestamp dataDocumentoSintesis;
	private String fileImport;
	private Timestamp entered;
	private String errore;
	private String md5;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUtenteImport() {
		return utenteImport;
	}
	public void setUtenteImport(int utenteImport) {
		this.utenteImport = utenteImport;
	}
	public Timestamp getDataDocumentoSintesis() {
		return dataDocumentoSintesis;
	}
	public void setDataDocumentoSintesis(Timestamp dataDocumentoSintesis) {
		this.dataDocumentoSintesis = dataDocumentoSintesis;
	}
	public void setDataDocumentoSintesis(String dataDocumentoSintesis) {
		this.dataDocumentoSintesis = DatabaseUtils.parseDateToTimestamp(dataDocumentoSintesis);
	}
	
	public String getFileImport() {
		return fileImport;
	}
	public void setFileImport(String fileImport) {
		this.fileImport = fileImport;
	}
 
public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
public LogImport (ResultSet rs) throws SQLException{
	buildRecord(rs);
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.utenteImport = rs.getInt("utente_import");
	this.dataDocumentoSintesis = rs.getTimestamp("data_sintesis");
	this.fileImport = rs.getString("file_import");
	this.setEntered(rs.getTimestamp("entered"));
	this.errore = rs.getString("errore");
}

public LogImport (){
	
}

public LogImport (Connection db, int id) throws SQLException{
	StringBuffer sql = new StringBuffer();
	sql.append("select * from sintesis_stabilimenti_import_log where id = ?");
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
	sql.append("INSERT INTO sintesis_stabilimenti_import_log("
			+ "data_sintesis,  entered, utente_import, file_import, md5 "
			+ ") values ("
			+ "?, now(), ?, ?, ? "
			+ "); ");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
		pst.setTimestamp(++i, this.getDataDocumentoSintesis() );
		pst.setInt(++i, this.getUtenteImport());
		pst.setString(++i, this.getFileImport());
		pst.setString(++i, this.getMd5());

		pst.execute();
		this.id = DatabaseUtils.getCurrVal(db, "sintesis_stabilimenti_import_log_id_seq",id);

		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}

public void update (Connection db){

	StringBuffer sql = new StringBuffer();
	sql.append("UPDATE sintesis_stabilimenti_import_log set errore = ? where id = ?");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
		pst.setString(++i, errore);
		pst.setInt(++i, id);
		pst.execute();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}

public String getErrore() {
	return errore;
}
public void setErrore(String errore) {
	this.errore = errore;
}


public void calcolaMd5() throws IOException{
	File f = new File(fileImport);
	String md5 = "";
	byte[] buffer = new byte[(int) f.length()];
	InputStream ios = null;
	try {
		ios = new FileInputStream(f);
		if (ios.read(buffer) == -1) {
			throw new IOException("EOF reached while trying to read the whole file");
		}
	} finally {
		try {
			if (ios != null)
				ios.close();
		} catch (IOException e) {
		}
	}


	MessageDigest md;
	try {
		md = MessageDigest.getInstance("MD5");
		md.update(buffer);
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		md5 = sb.toString();
	} catch (NoSuchAlgorithmException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	this.md5 = md5 ;
}
public String getMd5() {
	return md5;
}
public void setMd5(String md5) {
	this.md5 = md5;
}
public boolean md5Presente(Connection db) throws SQLException {
	boolean presente = false;
	
	PreparedStatement pst = db.prepareStatement("select * from sintesis_stabilimenti_import_log where md5 = ?");
	pst.setString(1, md5);
	ResultSet rs = pst.executeQuery();
	if (rs.next())
		presente = true;
	return presente;
}

}
