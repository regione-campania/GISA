/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BFunzione;
import it.us.web.bean.BSubfunzione;
import it.us.web.constants.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubfunzioneDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( SubfunzioneDAO.class );
	
	public static BSubfunzione ottieniSubfunzione( BFunzione f, String sf )
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		BSubfunzione		bsf		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_SUBFUNZIONE );
			stat.setInt( 1, f.getId() );
			stat.setString( 2, sf );
			
			rs = stat.executeQuery();

			if( rs.next() )
			{
				bsf = new BSubfunzione();
				bsf.setId((int)rs.getInt("ID_SUBFUNZIONE"));
				bsf.setId_function((int)rs.getInt("ID_FUNZIONE"));
				bsf.setNome(rs.getString("NOME"));
				bsf.setDescrizione(rs.getString("DESCRIZIONE"));
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
		
		return bsf;
	}
}
