/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.constants.Sql;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.vam.ClinicaDAO;
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

public class SuperUtenteDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( SuperUtenteDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	
	public static SuperUtente getSuperUtente( int id, Connection connection ) throws SQLException
	{
		SuperUtente su = new SuperUtente();
		String sql = "select  su.luogo,su.num_iscrizione_albo as numIscrizioneAlbo,su.sigla_provincia as siglaProvincia, su.id,su.data_scadenza as dataScadenza, su.enabled,su.enabled_date as enabledDate, su.entered, su.entered_by as enteredBy, "
				+ " su.modified, su.modified_by as modifiedBy, su.note,su.password, su.trashed_date as trashedDate, su.username, "
				+ " su.access_position_lat as accessPositionLat,su.access_position_lon as accessPositionLon, "
				+ " su.access_position_err as accessPositionErr "
				+ " from utenti_super su "
				+ " where su.trashed_date is null and su.id = ? and su.enabled " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			su = (SuperUtente)Bean.populate(su, rs);
			su.setUtenti(getUtenti(connection, su.getId()));
			su.setEnabled(true);
			su.setDataScadenza(rs.getDate("dataScadenza"));
			su.setNumIscrizioneAlbo(rs.getString("numIscrizioneAlbo"));
			su.setSiglaProvincia(rs.getString("siglaProvincia"));
		}
		return su;
	}
	
	public static ArrayList<BUtente> getUtenti(Connection connection, int idSu) throws SQLException
	{
		
		ArrayList<BUtente> sus = new ArrayList<BUtente>();
		String sql = "select id,nome,cognome,enabled from utenti where trashed_date is null and superutente = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idSu);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			BUtente u = new BUtente();
			u.setId(rs.getInt("id"));
			u.setNome(rs.getString("nome"));
			u.setCognome(rs.getString("cognome"));
			u.setEnabled(rs.getBoolean("enabled"));
			sus.add(u);
		}
		return sus;
	}
	
	public static ArrayList<BUtenteAll> getUtentiAll(Connection connection, int idSu) throws SQLException
	{
		
		ArrayList<BUtenteAll> sus = new ArrayList<BUtenteAll>();
		String sql = "select id,nome,cognome,enabled from utenti_ where trashed_date is null and superutente = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idSu);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			BUtenteAll u = new BUtenteAll();
			u.setId(rs.getInt("id"));
			u.setNome(rs.getString("nome"));
			u.setCognome(rs.getString("cognome"));
			u.setEnabled(rs.getBoolean("enabled"));
			sus.add(u);
		}
		return sus;
	}
	
	
	public static SuperUtenteAll getSuperutenteAll( int id )
	{
		SuperUtenteAll				su	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "SELECT * FROM utenti_super_  WHERE ID = ? " );
			stat.setInt( 1 ,  id );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				su = createBeanSuperutenteAll(rs);
			}
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return su;
	}
	
	
	public static SuperUtenteAll createBeanSuperutenteAll(ResultSet rs)
	{
		SuperUtenteAll su = new SuperUtenteAll();
		
		try 
		{
			su.setEntered(rs.getTimestamp("entered"));
			su.setEnteredBy(rs.getInt("entered_By"));
			su.setId( rs.getInt("ID") );
			su.setModified(rs.getTimestamp("modified"));
			su.setModifiedBy(rs.getInt("modified_By"));
			su.setPassword(rs.getString("password"));
			su.setTrashedDate(rs.getTimestamp("trashed_Date"));
			su.setUsername				( rs.getString("USERNAME") );
			su.setDataScadenza(rs.getTimestamp("data_scadenza"));
			su.setEnabled(rs.getBoolean("enabled"));
			su.setEnabledDate(rs.getTimestamp("enabled_date"));
			su.setLastLogin(rs.getTimestamp("last_login"));
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return su;
	}
	
	
}
