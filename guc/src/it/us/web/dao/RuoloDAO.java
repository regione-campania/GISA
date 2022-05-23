/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Ruolo;
import it.us.web.constants.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuoloDAO extends GenericDAO
{
	public static final Logger logger = LoggerFactory.getLogger( RuoloDAO.class );
	
	public static Vector<BRuolo> getRuoli()
	{
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		Vector<BRuolo>		ret		= new Vector<BRuolo>();
		
		try
		{
			conn	= retrieveConnection();
			stat	= conn.prepareStatement( Sql.GET_RUOLI );
			rs		= stat.executeQuery();

			while( rs.next() )
			{
				BRuolo ruolo = new BRuolo();
				ruolo.setId			( rs.getInt		("ID") );
				ruolo.setRuolo		( rs.getString	("NOME") );
				ruolo.setDescrizione( rs.getString	("DESCRIZIONE") );
				
				ret.addElement( ruolo );
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

		return ret;
	}
	
	
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
				ruolo.setRuoloInteger( rs.getInt		("ruolo_Integer") );
				
				ruolo.setRuoloString( rs.getString	("ruolo_String") );
				
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
	
	public static BRuolo getRuoloByName( String nomeruolo ) 
	{
		BRuolo				ruolo	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_RUOLO_BY_NOME );
			
			stat.setString( 1 ,  nomeruolo );
			
			rs = stat.executeQuery();

			
			if( rs.next() )
			{
				ruolo = new BRuolo();
				ruolo.setId			( rs.getInt		("ID") );
				ruolo.setRuolo		( rs.getString	("NOME") );
				ruolo.setDescrizione( rs.getString	("DESCRIZIONE") );
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

		return ruolo;
		
	}
	
	public static BRuolo getRuoloById( int idRuolo ) 
	{
		BRuolo				ruolo	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_RUOLO_BY_ID );
			
			stat.setInt( 1 ,  idRuolo );
			
			rs = stat.executeQuery();

			
			if( rs.next() )
			{
				ruolo = new BRuolo();
				ruolo.setId			( rs.getInt		("ID") );
				ruolo.setRuolo		( rs.getString	("NOME") );
				ruolo.setDescrizione( rs.getString	("DESCRIZIONE") );
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

		return ruolo;
		
	}
	
	public static void insertRuolo( String nomeruolo, String descrizione ) throws SQLException
	{
		Connection			conn = null;
		PreparedStatement	stat = null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.INSERT_RUOLO );
			stat.setString( 1 ,  nomeruolo );
			stat.setString( 2 ,  descrizione );
			stat.execute();
			conn.commit();
		}
		catch(SQLException e)
		{
			logger.error( "", e );
			throw e;
		}
		finally
		{
			close( stat, conn );
		}
	}
	
	public static String modificaDescrizioneRuolo( String nuovaDescrizione, String nomeRuolo )
	{
		String				errore	= "";
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		if( (nuovaDescrizione == null) || (nuovaDescrizione.equals("")) )
		{
			return "Campo descrizione obbligatorio.";
		}
		else
		{
			try
			{
				conn = retrieveConnection();
				
				stat = conn.prepareStatement( "UPDATE permessi_RUOLI SET DESCRIZIONE = ? WHERE NOME = ? " );
				stat.setString( 1 ,  nuovaDescrizione );
				stat.setString( 2 ,  nomeRuolo );
				stat.execute();
				conn.commit();
			}
			catch(SQLException e)
			{
				logger.error( "", e );
			}
			finally
			{
				close( stat, conn );
			}
		}
		
		return errore;
	}
	
	public static void setCodiceRaggruppamentoUtenti( Connection db, String utenti )
	{
		PreparedStatement	stat	= null;
		try
		{
			 String codice = "select nextval('codice_raggruppamento_id_seq')";
			 int codice_raggruppamento = 0;
			 PreparedStatement pst = db.prepareStatement(codice);
		     ResultSet rs = pst.executeQuery();
		     if (rs.next())
			 {
		    	 codice_raggruppamento=rs.getInt(1);
			 } 
				
			stat = db.prepareStatement( "UPDATE guc_ruoli SET codice_raggruppamento = ? WHERE id_utente in ("+utenti+")");
			stat.setInt(1, codice_raggruppamento);
			stat.executeUpdate();
					
		}
		catch(SQLException e)
		{
				logger.error( "", e );
		}
		finally
		{
				close( stat );
		}		
	}
	

	public static void delete(String nome_ruolo, BUtente utente, HttpServletRequest req)
	{
		Connection conn = null;
		PreparedStatement stat = null;
		try
		{			
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.DELETE_RUOLO );
			stat.setString( 1 ,  nome_ruolo );
			stat.execute();
			conn.commit();
		}
		catch(SQLException e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( stat, conn );
		}
	}
}
