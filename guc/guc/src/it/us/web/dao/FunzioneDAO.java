/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BFunzione;
import it.us.web.constants.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunzioneDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( FunzioneDAO.class );
	
	public static Vector<BFunzione> getFunzioni()
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BFunzione			bf		= null;
		Vector<BFunzione>	ret		= new Vector<BFunzione>();
		
		try
		{
			conn	= retrieveConnection();
			stat	= conn.prepareStatement( Sql.GET_FUNZIONI );
			rs		= stat.executeQuery();
			
			while( rs.next() )
			{
				bf = loadResultSet(rs);
				ret.addElement		( bf );
			}
		}
		
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return ret;
	}
	
	public static BFunzione getFunzione( String f )
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BFunzione			bf		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_FUNZIONE );
			stat.setString( 1, f );
			
			rs = stat.executeQuery();

			if( rs.next() )
			{
				bf = loadResultSet(rs);
			}
		}
		
		catch (SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return bf;
	}

	private static BFunzione loadResultSet(ResultSet rs) throws SQLException
	{
		BFunzione bf;
		bf = new BFunzione();
		bf.setId			( rs.getInt("ID_FUNZIONE") );
		bf.setNome			( rs.getString("NOME") );
		bf.setDescrizione	( rs.getString("DESCRIZIONE") );
		return bf;
	}
}
