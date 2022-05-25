/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.sinantropi;

import it.us.web.bean.sinantropi.Catture;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.lookup.LookupComuniDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CattureDAO extends GenericDAO 
{
	public static final Logger logger = LoggerFactory.getLogger(CattureDAO.class);

	
	
	
	
	public static HashSet<Catture> getCatture( int idSinantropo, Connection connection) throws SQLException
	{
		HashSet<Catture> catture = new HashSet<Catture>();
		String sql = " select * "
				+ " from sinantropo_catture "
				+ " where sinantropo = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idSinantropo);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Catture c = new Catture();
			c.setId( rs.getInt("id"));
			c.setComuneCattura(LookupComuniDAO.getComune( rs.getInt("comune_cattura"), connection));
			c.setDataCattura(rs.getDate("data_cattura"));
			c.setReimmissioni(ReimmissioniDAO.getReimmissione(c, connection));
			catture.add(c);
		}
		return catture;
	}
	
	
}
