/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.gestioneanagrafica.base.Indirizzo;
import org.aspcfs.utils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndirizzoDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( IndirizzoDAO.class );
	
	
	//Campi che servono per l'inserimento
	public Indirizzo indirizzo;
	
	public IndirizzoDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	
		
	public IndirizzoDAO(Indirizzo indirizzo) 
	{
		this.indirizzo=indirizzo;
	}

	public ArrayList<Indirizzo> getItems(Connection conn) throws SQLException 
	{

	  	String sql = " select * from public.get_indirizzo(?, ?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, indirizzo.getIdComune());
		st.setObject(2, indirizzo.getIdToponimo());
		st.setString(3, indirizzo.getVia());
		
		ResultSet rs = st.executeQuery();
		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		
		while(rs.next())
		{
			indirizzi.add(new Indirizzo(rs));
		}
		
		return indirizzi;
	}
	
	
	public ArrayList<Indirizzo> getIndirizzoNapoli(Connection conn) throws SQLException 
	{

	  	String sql = " select * from public.get_indirizzo_napoli_new(?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, indirizzo.getIdToponimo());
		st.setString(2, indirizzo.getVia());
		
		ResultSet rs = st.executeQuery();
		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		
		while(rs.next())
		{
			indirizzi.add(new Indirizzo(rs));
		}
		
		return indirizzi;
	}
	

	public boolean exist(Connection conn) throws SQLException 
	{
		return !getItems(conn).isEmpty();
	}
	
}
