/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.lookup;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.util.Md5;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupAlimentazioniDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupAlimentazioniDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupAlimentazioni getAlimentazione( int id, Connection connection ) throws SQLException
	{
		LookupAlimentazioni alimentazioni = new LookupAlimentazioni();
		String sql = "select id,description,level,enabled from lookup_tipi_richiedente where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			alimentazioni.setId(rs.getInt("id"));
			alimentazioni.setDescription(rs.getString("description"));
			alimentazioni.setEnabled(rs.getBoolean("enabled"));
			alimentazioni.setLevel(rs.getInt("level"));
		}
		return alimentazioni;
	}
	
	
	
	public static LookupAlimentazioni getAlimentazione( ResultSet rs ) throws SQLException
	{
		LookupAlimentazioni alim = new LookupAlimentazioni();
		alim.setId(rs.getInt("id"));
		alim.setDescription(rs.getString("description"));
		alim.setEnabled(rs.getBoolean("enabled"));
		alim.setLevel(rs.getInt("level"));
		return alim;
	}
	
	
	
	public static ArrayList<LookupAlimentazioni> getAlimentazioni(Connection connection) throws SQLException
	{
		ArrayList<LookupAlimentazioni> alimentazioni = new ArrayList<LookupAlimentazioni>();
		String sql = "select id,description,level,enabled from lookup_alimentazioni where enabled order by level asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			alimentazioni.add(getAlimentazione(rs));
		}
		return alimentazioni;
	}
	
	
}
