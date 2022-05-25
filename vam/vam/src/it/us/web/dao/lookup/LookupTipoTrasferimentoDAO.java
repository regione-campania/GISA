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
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
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

public class LookupTipoTrasferimentoDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupTipoTrasferimentoDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupTipoTrasferimento getTipoTrasferimento( int id, Connection connection ) throws SQLException
	{
		LookupTipoTrasferimento tipo = new LookupTipoTrasferimento();
		String sql = "select id,description,enabled from lookup_tipo_trasferimento where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			tipo.setDescription(rs.getString("description"));
			tipo.setId(rs.getInt("id"));
			tipo.setEnabled(rs.getBoolean("enabled"));
		}
		return tipo;
	}
	
	public static LookupTipoTrasferimento getTipoTrasferimento( ResultSet rs ) throws SQLException
	{
		LookupTipoTrasferimento tipo = new LookupTipoTrasferimento();
		tipo.setDescription(rs.getString("description"));
		tipo.setId(rs.getInt("id"));
		tipo.setEnabled(rs.getBoolean("enabled"));
		return tipo;
	}
	
	
	
	public static ArrayList<LookupTipoTrasferimento> getTipiTrasferimenti(Connection connection) throws SQLException
	{
		ArrayList<LookupTipoTrasferimento> tipi = new ArrayList<LookupTipoTrasferimento>();
		String sql = "select id, description,enabled from lookup_tipo_trasferimento where enabled order by id asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			tipi.add(getTipoTrasferimento(rs));
		}
		return tipi;
	}
	
	
}
