/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.guc;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.db.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuoloDAO extends GenericDAO
{
	public static final Logger logger = LoggerFactory.getLogger( RuoloDAO.class );
	
	
	
	
	public static ArrayList<Ruolo> getRuoliByEndPoint(String endPoint,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<Ruolo>		ret		= new ArrayList<Ruolo>();
		
		try
		{
			
			stat	= db.prepareStatement( Sql.GET_RUOLI_BY_ENDPOINT );
			stat.setString(1, endPoint) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				Ruolo ruolo = new Ruolo();
				ruolo.setRuoloInteger( rs.getInt		("ruoloInteger") );
				
				ruolo.setRuoloString( rs.getString	("ruoloString") );
				
				ret.add( ruolo );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	

	public static Set<Ruolo> getRuoliByIdUtente(Utente utente,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		Set<Ruolo> ret = new HashSet<Ruolo>();
		
		try
		{
			 
			stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  =? and (trashed is null or trashed is false)" );
			stat.setInt(1, utente.getId()) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				Ruolo ruolo = createBeanUtente(rs);
				ruolo.setUtente(utente);
				ret.add( ruolo );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	
	
	public static void insert(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			//int idGucRuolo = DbUtil.getNextSeqTipo(db, "guc_ruoli_id_seq");
			stat	= db.prepareStatement( "INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (?,?,?,?,?) " );
			stat.setString(1, r.getEndpoint()) ;
			stat.setInt(2, r.getRuoloInteger()) ;
			stat.setString(3, r.getRuoloString()) ;
			stat.setInt(4, r.getUtente().getId()) ;
			stat.setString(5, r.getNote()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	
	
	public static void updateForImport(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			//int idGucRuolo = DbUtil.getNextSeqTipo(db, "guc_ruoli_id_seq");
			stat	= db.prepareStatement( "delete from guc_ruoli  where id_utente = ? ;INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (?,?,?,?,?) " );
			
			stat.setInt(1, r.getUtente().getId());
			stat.setString(2, r.getEndpoint()) ;
			stat.setInt(3, r.getRuoloInteger()) ;
			stat.setString(4, r.getRuoloString()) ;
			stat.setInt(5, r.getUtente().getId()) ;
			stat.setString(6, r.getNote()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	public static void update(Connection db ,Ruolo r)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			
			stat	= db.prepareStatement( "update guc_ruoli set endpoint=?,ruolo_integer=?,ruolo_string=?,note=? where id = ? " );
			stat.setString(1, r.getEndpoint()) ;
			stat.setInt(2, r.getRuoloInteger()) ;
			stat.setString(3, r.getRuoloString()) ;
			stat.setString(4, r.getNote()) ;
			stat.setInt(5, r.getId()) ;
			stat.executeUpdate();
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

	}
	
	
	
	public static Ruolo createBeanUtente(ResultSet rs)
	{
		Ruolo ruolo = new Ruolo();
		
		try 
		{
			ruolo.setId(rs.getInt("id"));
			ruolo.setNote(rs.getString("note"));
			ruolo.setRuoloInteger(rs.getInt("ruolo_integer"));
			ruolo.setRuoloString(rs.getString("ruolo_string"));
			ruolo.setEndpoint(rs.getString("endpoint"));
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return ruolo;
	}
	
	public static ArrayList<String> getEndpointByIdUtente(int idUtente,Connection db)
	{
		
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<String> ret = new ArrayList<String> ();
		
		try
		{
			
			stat	= db.prepareStatement( "select * from guc_ruoli where id_utente  = ?" );
			stat.setInt(1, idUtente) ;
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				//Ruolo ruolo = createBeanUtente(rs);
				//ruolo.setUtente(utente);
				
				ret.add(rs.getString("endpoint") );
			}
			
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat );
		}

		return ret;
	}
	
	
}
