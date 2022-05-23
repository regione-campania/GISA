/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BUtente;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.Sql;
import it.us.web.util.Md5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtenteDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( UtenteDAO.class );
	
	public static BUtente authenticate( String un, String pw, Connection db) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.PASSWORD_CHECK );		
			stat.setString( 1, un );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				String pwd = rs.getString("PASSWORD");
				
				if( pwd.equals( Md5.encrypt(pw) ) )
				{
					utente = getUtenteBId(db,rs.getInt("id"));//(BUtente) persistence.find( BUtente.class, rs.getInt( "id" ) );//createBeanUtente( rs );
				}	
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	
	public static BUtente authenticate( String cf_spid, Connection db) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.CF_CHECK );		
			stat.setString( 1, cf_spid );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				utente = getUtenteBId(db,rs.getInt("id"));//(BUtente) persistence.find( BUtente.class, rs.getInt( "id" ) );//createBeanUtente( rs );
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	public static Utente authenticateUnifiedAccess( String un, String pw, Connection db) 
	{
		Utente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.PASSWORD_CHECK_ACCESSO_UNICO );		
			stat.setString( 1, un );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				String pwd = rs.getString("PASSWORD");
				
				if( pwd.equals( Md5.encrypt(pw) ) )
				{
					utente = getUtenteUnifiedBId(db,rs.getInt("id"));//(BUtente) persistence.find( BUtente.class, rs.getInt( "id" ) );//createBeanUtente( rs );
				}	
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	public static BUtente authenticateAdministratorAccessSpid(String cf_spid, String pw_spid, Connection db) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.PASSWORD_CHECK_ACCESSO_ADMIN_SPID );		
			stat.setString( 1, cf_spid );
			stat.setString( 2, pw_spid);
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
					utente = getUtenteBId(db,rs.getInt("id"));//(BUtente) persistence.find( BUtente.class, rs.getInt( "id" ) );//createBeanUtente( rs );
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	

	public static BUtente getUtenteBId( Connection db,int userId) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "select * from utenti where id = ?" );		
			stat.setInt( 1, userId );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				utente	= createBeanUtente(rs);
					
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	public static ArrayList<BUtente> getUtenteByUserame( Connection db,String username) 
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<BUtente> listautenti = new ArrayList<BUtente>();
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "select * from utenti where username = ?" );		
			stat.setString( 1, username );
			rs = stat.executeQuery();
			
			while( rs.next() )
			{
				utente	= createBeanUtente(rs);
				listautenti.add(utente);
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return listautenti;		
	}
	
	
	public static Utente getUtenteUnifiedBId( Connection db,int userId) 
	{
		Utente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "select * from guc_utenti where id = ?" );		
			stat.setInt( 1, userId );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{
				utente	= createGucUtente(rs);
					
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	
	public static ArrayList<BUtente> getUtentiEnabled (Connection db) 
	{
		ArrayList<BUtente> 				utente	= new ArrayList<BUtente>() ;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "select * from utenti where enabled = true " );		
			rs = stat.executeQuery();
			
			while( rs.next() )
			{
				utente.add(createBeanUtente(rs));
					
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return utente;		
	}
	
	public static ArrayList<BUtente> getUtenti (Connection db) 
	{
		ArrayList<BUtente> 				utente	= new ArrayList<BUtente>() ;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( "select * from utenti" );		
			rs = stat.executeQuery();
			
			while( rs.next() )
			{
				utente.add(createBeanUtente(rs));
					
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}	
		return utente;		
	}
	
	public static BUtente createBeanUtente(ResultSet rs)
	{
		BUtente utente = new BUtente();
		
		try 
		{
			utente.setCap( rs.getString("CAP") );
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") );
			utente.setCognome( rs.getString("COGNOME") );
			utente.setComune( rs.getString("COMUNE") );
			utente.setComuneNascita( rs.getString("COMUNE_NASCITA") );
			utente.setDataNascita(rs.getDate("data_nascita"));
			utente.setDomandaSegreta(rs.getString("domanda_segreta"));
			utente.setEmail( rs.getString("EMAIL") );
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setEnteredBy(rs.getInt("entered_By"));
			utente.setFax(rs.getString("fax"));
			utente.setId( rs.getInt("ID") );
			utente.setIndirizzo(rs.getString("indirizzo"));
			utente.setModified(rs.getTimestamp("modified"));
			utente.setModifiedBy(rs.getInt("modified_By"));
			utente.setNome( rs.getString("NOME") );
			utente.setPassword(rs.getString("password"));
			utente.setProvincia(rs.getString("provincia"));
			utente.setRispostaSegreta(rs.getString("risposta_segreta"));
			utente.setStato(rs.getString("stato"));
			utente.setTelefono1(rs.getString("telefono1"));
			utente.setTelefono2(rs.getString("telefono2"));
			utente.setTrashedDate(rs.getTimestamp("trashed_Date"));
			utente.setUsername( rs.getString("USERNAME") );
			utente.setEnabled(rs.getBoolean("enabled"));
			
			utente.setSuperAdmin(rs.getBoolean("super_admin"));
			utente.setRuolo(rs.getString("ruolo"));
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return utente;
	}
	
	public static Utente createGucUtente(ResultSet rs)
	{
		Utente utente = new Utente();
		
		try 
		{
			
			utente.setCodiceFiscale( rs.getString("CODICE_FISCALE") );
			utente.setCognome( rs.getString("COGNOME") );
			utente.setEmail( rs.getString("EMAIL") );
			utente.setEntered(rs.getTimestamp("entered"));
			utente.setEnteredBy(rs.getInt("enteredby"));
			utente.setId( rs.getInt("ID") );
			utente.setModified(rs.getTimestamp("modified"));
			utente.setModifiedBy(rs.getInt("modifiedby"));
			utente.setNome( rs.getString("NOME") );
			utente.setPassword(rs.getString("password"));
			utente.setUsername( rs.getString("USERNAME") );
			utente.setDataScadenza(rs.getDate("data_scadenza"));
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return utente;
	}

	public static BUtente getUtente(String username)
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_USER );
			stat.setString( 1 ,  username );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtente( rs );
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
		
		return utente;
	}
	
	public static BUtente getUtente( int id )
	{
		BUtente				utente	= null;
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_USER_FROM_ID );
			stat.setInt( 1 ,  id );
			rs = stat.executeQuery();
			
			if( rs.next() )
			{		
				utente = createBeanUtente( rs );
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
		
		return utente;
	}

	public static ArrayList<BUtente> getUtenti( String substring_nome, String substring_cognome,String substring_ruolo )
	{
		String nome		= (substring_nome == null) ? ("%") : ("%" + substring_nome.trim() + "%");
		String cognome	= (substring_cognome == null) ? ("%") : ("%" + substring_cognome.trim() + "%");
		String ruolo	= (substring_ruolo == "" || substring_ruolo == null) ? ("") : (substring_ruolo);
		
		Connection			conn	= null;
		PreparedStatement	stat	= null;
		ResultSet			rs		= null;
		ArrayList<BUtente>	v		= new ArrayList<BUtente>();
		
		try 
		{
			conn = retrieveConnection();
			stat = conn.prepareStatement( Sql.GET_UTENTI_FILTERED );
			stat.setString( 1, nome );
			stat.setString( 2, cognome );
			rs = stat.executeQuery();
			
			while( rs.next() )
			{		
				
				BUtente utente = null;
				utente = createBeanUtente( rs );
				if (utente.getRuolo() != null)
				{
					if( ruolo.equals("") || utente.getRuolo().equalsIgnoreCase(ruolo) )
					{
						v.add(utente);
					}	
				}
				else
				{
					if( ruolo.equals("<nessuno>") || ruolo.equals("") )
					{
						v.add(utente);
					}
				}
			}
						
		}
		catch(Exception e)
		{
			logger.error( "", e );
		}
		finally
		{
			close( rs, stat, conn );
		}
		
		return v;
	}
	
	public static void insert(Connection db,BUtente butente)
	{
		String insert = "INSERT INTO utenti(" +
            "cap, codice_fiscale, cognome, comune, comune_nascita, data_nascita, " +
            "data_scadenza, domanda_segreta, email, enabled, enabled_date, " +
            "entered, entered_by, fax, indirizzo, modified, modified_by, nome, " +
            "note, password, provincia, risposta_segreta, ruolo, stato, telefono1, " +
            "telefono2, trashed_date, username,super_admin,id_utente_guc_old)" +
			"VALUES (?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, " +
            "current_date, ?, ?, ?, current_date, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?,?,?)";
		
		try
		{
			Timestamp dataNascita = null;
			Timestamp datScadenza = null;
			Timestamp enabledDate = null;
			Timestamp trashed = null;

			if (butente.getDataNascita()!=null)
			{
				dataNascita = new Timestamp(butente.getDataNascita().getTime());
			}
			if (butente.getDataScadenza()!=null)
			{
				datScadenza = new Timestamp(butente.getDataScadenza().getTime());
			}
			if (butente.getEnabledDate()!=null)
			{
				enabledDate = new Timestamp(butente.getEnabledDate().getTime());
			}
			if (butente.getTrashedDate()!=null)
			{
				trashed = new Timestamp(butente.getTrashedDate().getTime());
			}
			
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setString(1, butente.getCap());
			pst.setString(2,butente.getCodiceFiscale());
			pst.setString(3,butente.getCognome());
			pst.setString(4,butente.getComune());
			pst.setString(5,butente.getComuneNascita());
			pst.setTimestamp(6,dataNascita);
			pst.setTimestamp(7,datScadenza);
			pst.setString(8,butente.getDomandaSegreta());
			pst.setString(9,butente.getEmail());
			pst.setBoolean(10,butente.getEnabled());
			pst.setTimestamp(11,enabledDate);
			pst.setInt(12,butente.getEnteredBy());
			pst.setString(13,butente.getFax());
			pst.setString(14,butente.getIndirizzo());
			pst.setInt(15,butente.getModifiedBy());
			pst.setString(16,butente.getNome());
			pst.setString(17,butente.getNote());
			pst.setString(18,butente.getPassword());
			pst.setString(19,butente.getProvincia());
			pst.setString(20,butente.getRispostaSegreta());
			pst.setString(21,butente.getRuolo());
			pst.setString(22,butente.getStato());
			pst.setString(23,butente.getTelefono1());
			pst.setString(24,butente.getTelefono2());
			pst.setTimestamp(25,trashed);
			pst.setString(26,butente.getUsername());
			pst.setBoolean(27,butente.isSuperAdmin());
			pst.setInt(28,butente.getId());

			pst.execute() ;

				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void update(Connection db,BUtente butente)
	{
		String insert = "update utenti set" +
            " cap = ?, codice_fiscale= ?, cognome= ?, comune= ?, comune_nascita= ?, data_nascita= ?, " +
            "data_scadenza= ?, domanda_segreta= ?, email= ?, enabled= ?, enabled_date= ?, entered=?, " +
            "fax= ?, indirizzo= ?, modified= current_date, modified_by= ?, nome= ?, " +
            "note= ?, password= ?, provincia= ?, risposta_segreta= ?, ruolo= ?, stato= ?, telefono1= ?, " +
            "telefono2= ?, trashed_date= ?, username= ? , super_admin=? where id = ?";
		
		try
		{
			Timestamp dataNascita = null;
			Timestamp datScadenza = null;
			Timestamp enabledDate = null;
			Timestamp trashed = null;
			Timestamp entered = null;

			if (butente.getDataNascita()!=null)
			{
				dataNascita = new Timestamp(butente.getDataNascita().getTime());
			}
			if (butente.getDataScadenza()!=null)
			{
				datScadenza = new Timestamp(butente.getDataScadenza().getTime());
			}
			if (butente.getEnabledDate()!=null)
			{
				enabledDate = new Timestamp(butente.getEnabledDate().getTime());
			}
			if (butente.getTrashedDate()!=null)
			{
				trashed = new Timestamp(butente.getTrashedDate().getTime());
			}
			if  (butente.getEntered()!=null){
				entered = new Timestamp(butente.getEntered().getTime());
			}
			
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setString(1, butente.getCap());
			pst.setString(2,butente.getCodiceFiscale());
			pst.setString(3,butente.getCognome());
			pst.setString(4,butente.getComune());
			pst.setString(5,butente.getComuneNascita());
			pst.setTimestamp(6,dataNascita);
			pst.setTimestamp(7,datScadenza);
			pst.setString(8,butente.getDomandaSegreta());
			pst.setString(9,butente.getEmail());
			pst.setBoolean(10,butente.getEnabled());
			pst.setTimestamp(11,enabledDate);
			pst.setTimestamp(12,entered);
			pst.setString(13,butente.getFax());
			pst.setString(14,butente.getIndirizzo());
			pst.setInt(15,butente.getModifiedBy());
			pst.setString(16,butente.getNome());
			pst.setString(17,butente.getNote());
			pst.setString(18,butente.getPassword());
			pst.setString(19,butente.getProvincia());
			pst.setString(20,butente.getRispostaSegreta());
			pst.setString(21,butente.getRuolo());
			pst.setString(22,butente.getStato());
			pst.setString(23,butente.getTelefono1());
			pst.setString(24,butente.getTelefono2());
			pst.setTimestamp(25,trashed);
			pst.setString(26,butente.getUsername());
			pst.setBoolean(27, butente.isSuperAdmin());
			pst.setInt(28,butente.getId());

			pst.execute() ;

				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
