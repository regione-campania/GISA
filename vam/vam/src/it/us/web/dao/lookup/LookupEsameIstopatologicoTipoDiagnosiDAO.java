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
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupEsameIstopatologicoTipoDiagnosiDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupEsameIstopatologicoTipoDiagnosiDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupEsameIstopatologicoTipoDiagnosi get( int id, Connection connection ) throws SQLException
	{
		LookupEsameIstopatologicoTipoDiagnosi s = new LookupEsameIstopatologicoTipoDiagnosi();
		String sql = " select id, description, level, enabled from lookup_esame_istopatologico_tipo_diagnosi "
				+    " where enabled and id = ? " ;

		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			s.setId(rs.getInt("id"));
			s.setDescription(rs.getString("description"));
			s.setEnabled(rs.getBoolean("enabled"));
			s.setLevel(rs.getInt("level"));
		}
		return s;
	}
	
	
	
	public static LookupEsameIstopatologicoTipoDiagnosi get( ResultSet rs ) throws SQLException
	{
		LookupEsameIstopatologicoTipoDiagnosi s = new LookupEsameIstopatologicoTipoDiagnosi();
		s.setId(rs.getInt("id"));
		s.setDescription(rs.getString("description"));
		s.setEnabled(rs.getBoolean("enabled"));
		s.setLevel(rs.getInt("level"));
		return s;
	}
	
	
	
	public static ArrayList<LookupEsameIstopatologicoTipoDiagnosi> getTipi(Connection connection) throws SQLException
	{
		ArrayList<LookupEsameIstopatologicoTipoDiagnosi> s = new ArrayList<LookupEsameIstopatologicoTipoDiagnosi>();
		String sql = " select id, description, codice, level, enabled from lookup_esame_istopatologico_tipo_diagnosi "
				+    " where enabled  " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			s.add(get(rs));
		}
		return s;
	}
	
	
}
