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
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
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

public class LookupPersonaleInternoDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupPersonaleInternoDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupPersonaleInterno getPersonale( int id, Connection connection ) throws SQLException
	{
		LookupPersonaleInterno pers = new LookupPersonaleInterno();
		String sql = "select pers.id,pers.nominativo,pers.enabled, asl.description as descriptionAsl, asl.id as idAsl from lookup_personale_interno pers, lookup_asl asl "
				+ " where pers.enabled and pers.id = ? and pers.asl = asl.id " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			pers.setId(rs.getInt("id"));
			pers.setNominativo(rs.getString("nominativo"));
			pers.setAsl(getAsl(rs));
			pers.setEnabled(rs.getBoolean("enabled"));
		}
		return pers;
	}
	
	public static LookupPersonaleInterno getPersonale( ResultSet rs ) throws SQLException
	{
		LookupPersonaleInterno pers = new LookupPersonaleInterno();
		pers.setId(rs.getInt("id"));
		pers.setNominativo(rs.getString("nominativo"));
		pers.setAsl(getAsl(rs));
		pers.setEnabled(rs.getBoolean("enabled"));
		return pers;
	}
	
	
	public static LookupAsl getAsl( ResultSet rs ) throws SQLException
	{
		LookupAsl asl = new LookupAsl();
		asl.setId(rs.getInt("idAsl"));
		asl.setDescription(rs.getString("descriptionAsl"));
		return asl;
	}
	
	
	public static ArrayList<LookupPersonaleInterno> getPersonali(Connection connection) throws SQLException
	{
		ArrayList<LookupPersonaleInterno> pers = new ArrayList<LookupPersonaleInterno>();
		String sql = "select pers.id,pers.nominativo,pers.enabled, asl.description as descriptionAsl, asl.id as idAsl from lookup_personale_interno pers, lookup_asl asl "
				+ " where enabled and pers.asl = asl.id" ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			pers.add(getPersonale(rs));
		}
		return pers;
	}
	
	
}
