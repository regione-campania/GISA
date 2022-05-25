/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.lookup;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupComuni;
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

public class LookupComuniDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupComuniDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupComuni getComune( int id, Connection connection ) throws SQLException
	{
		LookupComuni c = new LookupComuni();
		String sql = "select * from lookup_comuni where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			c = (LookupComuni)Bean.populate(c, rs);
			c.setAv(rs.getBoolean("av"));
			c.setBn(rs.getBoolean("bn"));
			c.setCe(rs.getBoolean("ce"));
			c.setSa(rs.getBoolean("sa"));
			c.setNa(rs.getBoolean("na"));
			c.setCap(rs.getString("cap"));
			c.setCodiceIstat(rs.getString("codice_istat"));
			c.setDescription(rs.getString("description"));
			c.setEnabled(rs.getBoolean("enabled"));
			c.setId(rs.getInt("id"));
			c.setLevel(rs.getInt("level"));
		}
		return c;
	}
	
	
}
