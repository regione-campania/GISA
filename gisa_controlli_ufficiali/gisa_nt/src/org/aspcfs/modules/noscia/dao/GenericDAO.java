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
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericDAO implements DAO
{
	private static final Logger logger = LoggerFactory.getLogger( GenericDAO.class );
	
	public Object getItem( Connection connection ) throws SQLException 
	{
		ArrayList<?> items = getItems( connection );
		return (items.isEmpty())?(null):(items.get(0));
	}
	
	
	public Object getItem( Connection connection, int total ) throws SQLException 
	{
		ArrayList<?> items = getItems( connection );
		return (items.isEmpty())?(null):(items.get(0));
	}

	
	public static void close( ResultSet rs, Statement st )
	{
		close( rs );
		close( st );
	}
	
	public static void close( Statement st, Connection conn )
	{
		close( st );
		close( conn );
	}
	
	public static void close( ResultSet rs, Statement st, Connection conn )
	{
		close( rs );
		close( st );
		close( conn );
	}

	public static void close( ResultSet rs )
	{
		if( rs != null )
		{
			try
			{
				rs.clearWarnings();
				rs.close();
			}
			catch (Exception e)
			{
				logger.error( "", e );
			}
		}
	}

	public static void close( Statement st )
	{
		if( st != null )
		{
			try
			{
				st.clearWarnings();
				st.close();
			}
			catch (SQLException e)
			{
				logger.error( "", e );
			}
		}
	}
	
	public static void close( Connection conn )
	{
		if( conn != null )
		{
			try
			{
				conn.clearWarnings();
				conn.close();
			}
			catch (SQLException e)
			{
				logger.error( "", e );
			}
		}
	}
}
