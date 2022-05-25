/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.guc;

import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.GenericDAO;
import it.us.web.db.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtenteDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LogUtenteDAO.class );
	
	public static void loggaUtente(Connection db,Utente u, String operazione) throws SQLException{
		
		int id = DbUtil.getNextSeqTipo(db, "log_utenti_id_seq");
		
		String sql = "INSERT INTO log_utenti(id, id_utente, username, nome, cognome, password, password_encrypted, operazione, data) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = null;
		
		try{
			pst =  db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getUsername());
			pst.setString(4, u.getNome());
			pst.setString(5, u.getCognome());
			pst.setString(6, u.getPassword());
			pst.setString(7, u.getPasswordEncrypted());
			pst.setString(8, operazione);
			pst.setTimestamp(9, new Timestamp (new Date().getTime()));
			
			pst.executeUpdate();
			
			sql = "INSERT INTO log_ruoli_utenti(id_log_utente, endpoint, ruolo) VALUES (?, ?, ?);";
			pst =  db.prepareStatement(sql);
			
			for (Ruolo r : u.getRuoli()){
				pst.setInt(1, id);
				pst.setString(2, r.getEndpoint());
				pst.setString(3, r.getRuoloString());
				pst.executeUpdate();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void loggaUtente(Connection db,BUtente u, String operazione) throws SQLException{	
		int id = DbUtil.getNextSeqTipo(db, "log_utenti_id_seq");
		
		String sql = "INSERT INTO log_utenti(id, id_utente, username, nome, cognome, password, operazione, data) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = null;
		
		try{
			pst =  db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, u.getId());
			pst.setString(3, u.getUsername());
			pst.setString(4, u.getNome());
			pst.setString(5, u.getCognome());
			pst.setString(6, u.getPassword());
			pst.setString(7, operazione);
			pst.setTimestamp(8, new Timestamp (new Date().getTime()));
			
			pst.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
}
