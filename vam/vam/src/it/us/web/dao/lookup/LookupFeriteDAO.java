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
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
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

public class LookupFeriteDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupFeriteDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupFerite getFerite( int id, Connection connection ) throws SQLException
	{
		LookupFerite f = new LookupFerite();
		String sql = "select id,description,level,enabled from lookup_ferite where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			f.setId(rs.getInt("id"));
			f.setDescription(rs.getString("description"));
			f.setEnabled(rs.getBoolean("enabled"));
			f.setLevel(rs.getInt("level"));
		}
		return f;
	}
	
	
	
	public static LookupFerite getFerita( ResultSet rs ) throws SQLException
	{
		LookupFerite f = new LookupFerite();
		f.setId(rs.getInt("id"));
		f.setDescription(rs.getString("description"));
		f.setEnabled(rs.getBoolean("enabled"));
		f.setLevel(rs.getInt("level"));
		return f;
	}
	
	
	
	public static ArrayList<LookupFerite> getFerite(Connection connection) throws SQLException
	{
		ArrayList<LookupFerite> f = new ArrayList<LookupFerite>();
		String sql = "select id,description,level,enabled from lookup_ferite where enabled order by level asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			f.add(getFerita(rs));
		}
		return f;
	}
	
	
}
