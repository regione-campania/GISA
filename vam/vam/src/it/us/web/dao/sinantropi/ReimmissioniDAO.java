/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.sinantropi;

import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Reimmissioni;
import it.us.web.dao.GenericDAO;

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

public class ReimmissioniDAO extends GenericDAO 
{
	public static final Logger logger = LoggerFactory.getLogger(ReimmissioniDAO.class);

	
	
	
	
	public static Reimmissioni getReimmissione( Catture c, Connection connection) throws SQLException
	{
		Reimmissioni r = null;
		String sql = " select * "
				+ " from sinantropo_reimmissioni "
				+ " where catture = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, c.getId());
		
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			r = new Reimmissioni();
			r.setId( rs.getInt("id"));
			r.setDataReimmissione(rs.getDate("data_reimmissione"));
			r.setCatture(c);
		}
		return r;
	}
	
	
}
