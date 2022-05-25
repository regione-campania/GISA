/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioniopu.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.aspcfs.modules.macellazioniopu.base.Art17AltreSpecie;

public class Art17AltreSpecieDao {
	
	Logger logger = Logger.getLogger("MainLogger");
	private static Art17AltreSpecieDao instance;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Art17AltreSpecieDao(){}
	
	public static Art17AltreSpecieDao getInstance(){
		if(instance == null){
			instance = new Art17AltreSpecieDao();
		}
		return instance;
	}
	
	public boolean insert(Art17AltreSpecie art17AltreSpecie, Connection db){
		
		boolean ok = false;
		
		String query = "INSERT INTO art17_altre_specie(id_macello, id_esercente, nome_esercente, data_macellazione," +  
					   "auricolare, mezzene, data_nascita, specie, categoria, sesso, modello4, esito_visita) " +
					   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pst = db.prepareStatement(query);
			pst.setInt(1, art17AltreSpecie.getIdMacello());
			pst.setInt(2, art17AltreSpecie.getIdEsercente());
			pst.setString(3, art17AltreSpecie.getNomeEsercente());
			pst.setString(4, art17AltreSpecie.getDataMacellazione());
			pst.setString(5, art17AltreSpecie.getAuricolare());
			pst.setString(6, art17AltreSpecie.getMezzene());
			pst.setString(7, art17AltreSpecie.getDataNascita());
			pst.setString(8, art17AltreSpecie.getSpecie());
			pst.setString(9, art17AltreSpecie.getCategoria());
			pst.setString(10, art17AltreSpecie.getSesso());
			pst.setString(11, art17AltreSpecie.getModello4());
			pst.setString(12, art17AltreSpecie.getEsitoVisita());
			int n = pst.executeUpdate();
			if(n > 0){
				ok = true;
			}
		} 
		catch (SQLException sqle) {
			logger.severe("Errore durante l'inserimento di una tupla nella tabella <art17_altre_specie>.");
			sqle.printStackTrace();
		}
		finally{
			try {
				pst.close();
			} 
			catch (SQLException sqle) {
				logger.severe("Errore durante la chiusura del PreparedStatement.");
				sqle.printStackTrace();
			}
		}
		
		return ok;
	}
	
	
	public ArrayList<HashMap<Integer, String>> select(int idMacello, int idEsercente, String nomeEsercente, String dataMacellazione, Connection db){
		
		ArrayList<HashMap<Integer, String>> art17AltreSpecieList = new ArrayList<HashMap<Integer, String>>();
		
		String query = "SELECT auricolare, mezzene, data_nascita, specie, categoria, sesso, modello4, esito_visita " +
					   "FROM art17_altre_specie " +
					   "WHERE id_macello = ? and id_esercente = ? and nome_esercente = ? and data_macellazione = ?";
		
		try {
			pst = db.prepareStatement(query);
			pst.setInt(1, idMacello);
			pst.setInt(2, idEsercente);
			pst.setString(3, nomeEsercente);
			pst.setString(4, dataMacellazione);
			rs = pst.executeQuery();
			
			HashMap<Integer, String> art17AltreSpecieH = null;
			while(rs.next()){
				art17AltreSpecieH = new HashMap<Integer, String>();
				art17AltreSpecieH.put(0,rs.getString("auricolare"));
				art17AltreSpecieH.put(1,rs.getString("mezzene"));
				art17AltreSpecieH.put(2,rs.getString("data_nascita"));
				art17AltreSpecieH.put(3,rs.getString("specie"));
				art17AltreSpecieH.put(4,rs.getString("categoria"));
				art17AltreSpecieH.put(5,rs.getString("sesso"));
				art17AltreSpecieH.put(6,rs.getString("modello4"));
				art17AltreSpecieH.put(7,rs.getString("esito_visita"));
				art17AltreSpecieList.add(art17AltreSpecieH);
			}
			
		}
		catch (SQLException sqle) {
			logger.severe("Errore durante la selezione di tuple dalla tabella <art17_altre_specie>.");
			sqle.printStackTrace();
		}
		finally{
			try {
				rs.close();
				pst.close();
			} 
			catch (SQLException sqle) {
				logger.severe("Errore durante la chiusura del PreparedStatement.");
				sqle.printStackTrace();
			}
		}
		
		return art17AltreSpecieList;
		
	}
	
	
	public void delete(int idMacello, int idEsercente, String nomeEsercente, String dataMacellazione, Connection db){
		
		String query ="DELETE FROM art17_altre_specie WHERE id_macello = ? and id_esercente = ? and nome_esercente = ? and data_macellazione = ? ";
		
		try {
			
			//vengono preventivamente eliminate le tuple relative alla quaterna (id_macello,id_esercente,nome_esercente,data_macellazione)
			pst = db.prepareStatement(query);
			pst.setInt(1, idMacello);
			pst.setInt(2, idEsercente);
			pst.setString(3, nomeEsercente);
			pst.setString(4, dataMacellazione);
			pst.executeUpdate();
		} 
		catch (SQLException sqle) {
			logger.severe("Errore durante la cancellazione di tuple dalla tabella <art17_altre_specie>.");
			sqle.printStackTrace();
		}
		finally{
			try {
				pst.close();
			} 
			catch (SQLException sqle) {
				logger.severe("Errore durante la chiusura del PreparedStatement.");
				sqle.printStackTrace();
			}
		}
		
	}
	
	

}
