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
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
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
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupAslDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupAslDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupAsl getAsl( int id, Connection connection ) throws SQLException
	{
		LookupAsl asl = new LookupAsl();
		String sql = " select id,description,level,enabled from lookup_asl "
				+    " where enabled and id = ? "
				+    " order by id " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			asl = (LookupAsl)Bean.populate(asl, rs);
			asl.setEnabled(rs.getBoolean("enabled"));
		}
		return asl;
	}
	
	
	
	public static LookupAsl getAsl( ResultSet rs ) throws SQLException
	{
		LookupAsl asl = new LookupAsl();
		asl.setId(rs.getInt("id"));
		asl.setDescription(rs.getString("description"));
		asl.setEnabled(rs.getBoolean("enabled"));
		asl.setLevel(rs.getInt("level"));
		return asl;
	}
	
	
	
	public static ArrayList<LookupAsl> getAsl(Connection connection) throws SQLException
	{
		ArrayList<LookupAsl> asl = new ArrayList<LookupAsl>();
		String sql = "select id,description,level,enabled from lookup_asl where enabled order by description asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			asl.add(getAsl(rs));
		}
		return asl;
	}
	
	public static ArrayList<LookupPersonaleInterno> getPersonaleInterno(Connection connection, LookupAsl asl) throws SQLException
	{
		ArrayList<LookupPersonaleInterno> pis = new ArrayList<LookupPersonaleInterno>();
		String sql = " select * from lookup_personale_interno where enabled and hidden_in_page and asl = ? order by nominativo ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, asl.getId());
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupPersonaleInterno pi = new LookupPersonaleInterno();
			pi.setEnabled(rs.getBoolean("enabled"));
			pi.setId(rs.getInt("id"));
			pi.setNominativo(rs.getString("nominativo"));
			pi.setAsl(asl);
			pis.add(pi);
		}
		return pis;
	}
	
	
	public static ArrayList<LookupAsl> getAltreAsl(Connection connection, int idAslDaEscludere) throws SQLException
	{
		ArrayList<LookupAsl> asl = new ArrayList<LookupAsl>();
		String sql = "select id,description,level,enabled from lookup_asl where enabled and id <> ? order by description asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAslDaEscludere);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			asl.add(getAsl(rs));
		}
		return asl;
	}
	
	
}
