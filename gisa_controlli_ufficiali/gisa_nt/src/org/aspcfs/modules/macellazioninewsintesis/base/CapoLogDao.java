/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class CapoLogDao {
	
	private static CapoLogDao instance;
	private CapoLogDao(){}
	
	public static CapoLogDao getInstance(){
		if(instance == null){
			instance = new CapoLogDao();
		}
		return instance;
	}
	
	public CapoLog select(Connection db, String matricola) throws Exception{
		
		String select = "SELECT * " +
						"FROM m_capi_log " +
						"WHERE matricola ilike ?";
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		CapoLog capoLog = new CapoLog();
		
		try{
			stat = db.prepareStatement(select);
			stat.setString 		(1, matricola);
			rs = stat.executeQuery();
			
			if(rs.next()){
				capoLog.setAslSpeditoreFromBdn(rs.getInt("asl_speditore_from_bdn"));
				capoLog.setCodiceAziendaNascitaFromBdn(rs.getString("codice_azienda_nascita_from_bdn"));
				capoLog.setComuneSpeditoreFromBdn(rs.getString("comune_speditore_from_bdn"));
				capoLog.setDataNascitaFromBdn(rs.getTimestamp("data_nascita_from_bdn"));
				capoLog.setInBdn(rs.getBoolean("in_bdn"));
				capoLog.setMatricola(rs.getString("matricola"));
				capoLog.setRazzaFromBdn(rs.getInt("razza_from_bdn"));
				capoLog.setSessoFromBdn(rs.getString("sesso_from_bdn"));
				capoLog.setSpecieFromBdn(rs.getInt("specie_from_bdn"));
			}
			
			return capoLog;
			
		}
		finally{
			stat.close();
		}
		
	}
		
	public void insert(Connection db, CapoLog capoLog) throws Exception{
		
		String insert = "INSERT INTO m_capi_log" +
						"(id_macello, matricola, " +
						"codice_azienda_nascita, codice_azienda_nascita_from_bdn, " +
						"comune_speditore, comune_speditore_from_bdn, asl_speditore, asl_speditore_from_bdn, " +
						"data_nascita, data_nascita_from_bdn, sesso, sesso_from_bdn, specie, specie_from_bdn, razza, razza_from_bdn, " +
						"in_bdn, entered_by, modified_by, entered, modified) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now() )";
		
		PreparedStatement stat = null;
		int i = 0;
		
		try{
			stat = db.prepareStatement(insert);
			stat.setInt 		(++i, capoLog.getIdMacello());
			stat.setString 		(++i, capoLog.getMatricola());
			stat.setString 		(++i, capoLog.getCodiceAziendaNascita());
			stat.setString 		(++i, capoLog.getCodiceAziendaNascitaFromBdn());
			stat.setString 		(++i, capoLog.getComuneSpeditore());
			stat.setString 		(++i, capoLog.getComuneSpeditoreFromBdn());
			stat.setInt 		(++i, capoLog.getAslSpeditore());
			stat.setInt 		(++i, capoLog.getAslSpeditoreFromBdn());
			stat.setTimestamp 	(++i, capoLog.getDataNascita());
			stat.setTimestamp 	(++i, capoLog.getDataNascitaFromBdn());
			stat.setString 		(++i, capoLog.getSesso());
			stat.setString 		(++i, capoLog.getSessoFromBdn());
			stat.setInt 		(++i, capoLog.getSpecie());
			stat.setInt 		(++i, capoLog.getSpecieFromBdn());
			stat.setInt 		(++i, capoLog.getRazza());
			stat.setInt 		(++i, capoLog.getRazzaFromBdn());
			stat.setBoolean 	(++i, capoLog.isInBdn());
			stat.setInt 		(++i, capoLog.getEnteredBy());
			stat.setInt 		(++i, capoLog.getModifiedBy());
			stat.executeUpdate();
		}
		finally{
			stat.close();
		}
		
	}
	
	
	public void update(Connection db, CapoLog capoLog) throws Exception{
		
		String update = "UPDATE m_capi_log " +
						"SET id_macello=?, " +
						"codice_azienda_nascita=?, codice_azienda_nascita_from_bdn=?, " +
						"comune_speditore=?, comune_speditore_from_bdn=?, asl_speditore=?, asl_speditore_from_bdn=?, " +
						"data_nascita=?, data_nascita_from_bdn=?, sesso=?, sesso_from_bdn=?, " +
						"specie=?, specie_from_bdn=?, razza=?, razza_from_bdn=?, " +
						"in_bdn=?, modified_by=?, modified=now(), trashed_date=? " +
						"WHERE matricola=?";

		PreparedStatement stat = null;
		int i = 0;
		
		try{
			stat = db.prepareStatement(update);
			stat.setInt 		(++i, capoLog.getIdMacello());
			stat.setString 		(++i, capoLog.getCodiceAziendaNascita());
			stat.setString 		(++i, capoLog.getCodiceAziendaNascitaFromBdn());
			stat.setString 		(++i, capoLog.getComuneSpeditore());
			stat.setString 		(++i, capoLog.getComuneSpeditoreFromBdn());
			stat.setInt 		(++i, capoLog.getAslSpeditore());
			stat.setInt 		(++i, capoLog.getAslSpeditoreFromBdn());
			stat.setTimestamp 	(++i, capoLog.getDataNascita());
			stat.setTimestamp 	(++i, capoLog.getDataNascitaFromBdn());
			stat.setString 		(++i, capoLog.getSesso());
			stat.setString 		(++i, capoLog.getSessoFromBdn());
			stat.setInt 		(++i, capoLog.getSpecie());
			stat.setInt 		(++i, capoLog.getSpecieFromBdn());
			stat.setInt 		(++i, capoLog.getRazza());
			stat.setInt 		(++i, capoLog.getRazzaFromBdn());
			stat.setBoolean 	(++i, capoLog.isInBdn());
			stat.setInt 		(++i, capoLog.getModifiedBy());
			stat.setTimestamp 	(++i, capoLog.getTrashedDate());
			stat.setString 		(++i, capoLog.getMatricola());
			stat.executeUpdate();
		}
		finally{
			stat.close();
		}
		
	}
	
	
	public boolean isCapoLogged(Connection db, CapoLog capoLog) throws Exception{
		
		boolean isCapoLogged = false;
		
		String count = "SELECT count(matricola) " +
					   "FROM m_capi_log " +
					   "WHERE matricola ilike ? and trashed_date is null";

		PreparedStatement stat = null;
		ResultSet rs = null;
		
		try{
			stat = db.prepareStatement(count);
			stat.setString 		(1, capoLog.getMatricola());
			rs = stat.executeQuery();
			if(rs.next()){
				isCapoLogged = rs.getInt(1) > 0;
			}
			return isCapoLogged;
		}
		finally{
			rs.close();
			stat.close();
		}
		
	}
	
	
	
	public void log(Connection db, CapoLog capoLog) throws Exception{
		
		boolean isCapoInBdn = capoLog.isInBdn();
		
		boolean isCapoModified = capoLog.getAslSpeditore() != capoLog.getAslSpeditoreFromBdn() ||
								 !capoLog.getCodiceAziendaNascita().equals(capoLog.getCodiceAziendaNascitaFromBdn()) ||
								 !capoLog.getComuneSpeditore().equals(capoLog.getComuneSpeditoreFromBdn()) ||
								 capoLog.getDataNascita()!=null && !capoLog.getDataNascita().equals(capoLog.getDataNascitaFromBdn()) ||
								 capoLog.getRazza() != capoLog.getRazzaFromBdn() ||
								 !capoLog.getSesso().equals(capoLog.getSessoFromBdn()) ||
								 capoLog.getSpecie() != capoLog.getSpecieFromBdn();
		
		boolean isCapoToBeLogged = !isCapoInBdn || isCapoModified;
		
		if(isCapoToBeLogged){
			
			if(this.isCapoLogged(db, capoLog)){
				this.update(db, capoLog);
			}
			else{
				this.insert(db, capoLog);
			}
			
		}
		
	}
	
	

}
