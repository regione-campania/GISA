/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.vam.Autopsia;
import it.us.web.dao.GenericDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutopsiaDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( 

			AutopsiaDAO.class );

	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */

	public static Integer getProgressivoNumRifMittente( Connection connection ) throws SQLException
	{
		int progressivo = 1;
		String sql = " select max(progressivo)+1 as progressivo from autopsia "
				+    " where trashed_date is null and date_part('year', data_autopsia) =  date_part('year', current_timestamp)  " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			if(rs.getInt("progressivo")>0)
			{
				progressivo = rs.getInt("progressivo");
				while(existNumRifMittente(connection, progressivo))
				{
					progressivo++;
				}
			}	
		}
		return progressivo;
	}
	
	public static boolean existNumRifMittente( Connection connection, String numRifMittente ) throws SQLException
	{
		String sql = " select count(*) as count from autopsia "
				+    " where trashed_date is null and trim(numero_accettazione_sigla) =  ?  " ;
		PreparedStatement st = connection.prepareStatement(sql);
		
		st.setString(1, numRifMittente.trim());
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			if(rs.getInt("count")>0)
				return true;
		}
		return false;
	}
	
	public static boolean existNumRifMittente( Connection connection, int progressivo ) throws SQLException
	{
		DecimalFormat decimalFormat = new DecimalFormat( "000" );
		String progressivoFormattato = decimalFormat.format(progressivo);
		
		String sql = " select count(*) as count from autopsia "
				+    " where trashed_date is null and (trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ "                                    trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or"
				+ "                                    trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or"
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp) or "
				+ " trim(numero_accettazione_sigla) = ?|| date_part('year', current_timestamp)   ) " ;
		PreparedStatement st = connection.prepareStatement(sql);
		
		
		
		st.setString(1, progressivoFormattato+ "CR");
		st.setString(2, progressivoFormattato+ "UN");
		st.setString(3, progressivoFormattato+ "IZ");
		st.setString(4, progressivoFormattato+ "N1");
		st.setString(5, progressivoFormattato+ "N2");
		st.setString(6, progressivoFormattato+ "N3");
		st.setString(7, progressivoFormattato+ "AV");
		st.setString(8, progressivoFormattato+ "BN");
		st.setString(9, progressivoFormattato+ "CE");
		st.setString(10,progressivoFormattato+ "SA");
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			if(rs.getInt("count")>0)
				return true;
		}
		return false;
	}
	
	public static boolean existNumRifMittente( Connection connection, String numRifMittente, int idAutopsia ) throws SQLException
	{
		String sql = " select count(*) as count from autopsia "
				+    " where trashed_date is null and trim(numero_accettazione_sigla) = ? and id <> ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		
		st.setString(1, numRifMittente.trim());
		st.setInt(2, idAutopsia);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			if(rs.getInt("count")>0)
			{
				return true;
			}
		}
		return false;
	}


	
	


}
