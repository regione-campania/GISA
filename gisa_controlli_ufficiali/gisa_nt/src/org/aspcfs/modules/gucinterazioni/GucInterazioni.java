/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gucinterazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.GestoreConnessioni;


public class GucInterazioni{
	
	
	public Connection recuperaConnessioneGuc(){
		Connection dbGuc = null;
		try {
			dbGuc = GestoreConnessioni.getConnectionGuc();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbGuc;
	}
	
	
	public String getDbiInserimentoGuc(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select sql from guc_endpoint_connector_config where id_endpoint = 6 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	
	public String getDbiInserimentoGisa(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select sql from guc_endpoint_connector_config where id_endpoint = 2 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	
	public String getDbiDisableUtenteGuc(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select sql from guc_endpoint_connector_config where id_endpoint = 2 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}


	
	public String getDbiDisableGuc(Connection dbGuc, String username){
		String sql = "";
		PreparedStatement pst;
		try 
		{
			pst = dbGuc.prepareStatement(" UPDATE guc_utenti_ set data_scadenza = now(),cessato=true,modified=current_timestamp where username =? ");
			pst.setString(1, username);
			pst.executeQuery();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	
	
	
			
			
	
	
	public String getDbiInserimentoGisaExt(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select sql from guc_endpoint_connector_config where id_endpoint = 3 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	
	public String getUrlReloadUtentiGisa(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select url_reload_utenti from guc_endpoint_connector_config where id_endpoint = 2 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("url_reload_utenti");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	public String getUrlReloadUtentiGisaExt(Connection dbGuc){
		String sql = "";
		PreparedStatement pst;
		try {
			pst = dbGuc.prepareStatement("select url_reload_utenti from guc_endpoint_connector_config where id_endpoint = 3 and id_operazione = 2 and enabled");
	
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			sql = rs.getString("url_reload_utenti");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sql;
	}
	
public String eseguiInserimentoGuc(Connection dbGuc, String sql, String cf, String cognome, int idUtenteInserimento, String note, String nome, String username, int idQualificaGisa, String descrizioneQualificaGisa,  int idQualificaGisaExt, String descrizioneQualificaGisaExt){
	
	String res = "";
	PreparedStatement pst;
	try {
		pst = dbGuc.prepareStatement(sql);
	
	int i = 0;
	pst.setString(++i, cf);
	pst.setString(++i, cognome);
	pst.setString(++i, "");
	pst.setBoolean(++i, true);
	pst.setInt(++i, idUtenteInserimento);
	pst.setTimestamp(++i, null);
	pst.setInt(++i, idUtenteInserimento);
	pst.setString(++i, note);
	pst.setString(++i, nome);
	pst.setString(++i, "");
	pst.setString(++i, username);
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");

	//SOLO PER UNINA E LP
	pst.setInt(++i, -1);  //-1 no provincia
	pst.setString(++i, "");
	pst.setInt(++i, -1);  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");  //-1 no provincia
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");

	//Ruoli
	pst.setInt(++i, idQualificaGisa);
	pst.setString(++i, descrizioneQualificaGisa);
	pst.setInt(++i, idQualificaGisaExt);
	pst.setString(++i, descrizioneQualificaGisaExt);
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setInt(++i, -1);
	//52simo parametro
	pst.setString(++i,"");
	pst.setString(++i,"");
	pst.setInt(++i, -1);
	pst.setString(++i,"");
	pst.setString(++i,"");
	pst.setInt(++i, -1);
	pst.setString(++i,"");
	pst.setString(++i,"");

  
	System.out.println("[GISA] eseguo inserimento su GUC: "+pst.toString());
	ResultSet rs = pst.executeQuery();
	
	while (rs.next())
		res = rs.getString(1);	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return res;
	}
public String eseguiInserimentoGisa(Connection dbGisa, String sql, String username, int idQualifica, String nome, String cognome, String cf, String note){
	String res = "";
	PreparedStatement pst;
	try {
		pst = dbGisa.prepareStatement(sql);
	
	int i = 0;
	pst.setString(++i, username);
	pst.setString(++i, ""); //password
	pst.setInt(++i, idQualifica);
	pst.setInt(++i, 2);
	pst.setInt(++i, 2);
	pst.setBoolean(++i, true);
	pst.setInt(++i, -1);
	pst.setString(++i, nome);
	pst.setString(++i, cognome);
	pst.setString(++i, cf);
	pst.setString(++i, note);
	pst.setString(++i, "-1");
	pst.setObject(++i, null);
	pst.setString(++i, "");
	pst.setObject(++i, null);
	pst.setInt(++i, -1);
	pst.setString(++i, "false"); //access
	pst.setString(++i, "false"); //dpat
	pst.setString(++i, "true"); //nucleo ispettivo
	pst.setString(++i, "true"); //telefono
	
	System.out.println("[GISA] eseguo inserimento su GISA: "+pst.toString());
	ResultSet rs = pst.executeQuery();
	
	while (rs.next())
	 res = rs.getString(1);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return res;
	}


public String eseguiInserimentoGisaExt(Connection dbGisa, String sql, String username, int idQualifica, String nome, String cognome, String cf, String note){
	
	PreparedStatement pst;
	String res = "";
	try {
		pst = dbGisa.prepareStatement(sql);
	
	pst = dbGisa.prepareStatement(sql);
	int i = 0;
	pst.setString(++i, username);
	pst.setString(++i, ""); //password
	pst.setInt(++i, idQualifica);
	pst.setInt(++i, 2);
	pst.setInt(++i, 2);
	pst.setBoolean(++i, true);
	pst.setInt(++i, -1);
	pst.setString(++i, nome);
	pst.setString(++i, cognome);
	pst.setString(++i, cf);
	pst.setString(++i, note);
	pst.setString(++i, "-1");
	pst.setObject(++i, null);
	pst.setString(++i, "");
	pst.setObject(++i, null);
	pst.setString(++i, "false"); //access
	pst.setString(++i, "true"); //nucleo ispettivo
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setInt(++i, -1);
	pst.setString(++i, "");
	pst.setString(++i, "");
	pst.setString(++i, "");
	  
	
	System.out.println("[GISA] eseguo inserimento su GISA EXT: "+pst.toString());
	ResultSet rs = pst.executeQuery();
	while (rs.next())
	 res = rs.getString(1);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return res;
}

}
