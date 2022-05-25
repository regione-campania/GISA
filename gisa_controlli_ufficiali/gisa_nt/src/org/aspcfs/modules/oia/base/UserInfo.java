/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.oia.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;

import com.darkhorseventures.framework.beans.GenericBean;

public class UserInfo extends GenericBean {

	private static final long serialVersionUID = -669786521496027459L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;

	private String id_utente      ;
	private String id_asl         ;
	private String asl            ;
	private String nome_utente    ;
	private String cognome_utente ;
	private String id_ruolo       ;
	private String ruolo          ;
	
	public String getId_utente() {
		return id_utente;
	}
	public void setId_utente(String idUtente) {
		id_utente = idUtente;
	}
	public String getId_asl() {
		return id_asl;
	}
	public void setId_asl(String idAsl) {
		id_asl = idAsl;
	}
	public String getAsl() {
		return asl;
	}
	public void setAsl(String asl) {
		this.asl = asl;
	}
	public String getNome_utente() {
		return nome_utente;
	}
	public void setNome_utente(String nomeUtente) {
		nome_utente = nomeUtente;
	}
	public String getCognome_utente() {
		return cognome_utente;
	}
	public void setCognome_utente(String cognomeUtente) {
		cognome_utente = cognomeUtente;
	}
	public String getId_ruolo() {
		return id_ruolo;
	}
	public void setId_ruolo(String idRuolo) {
		id_ruolo = idRuolo;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public static Integer[] get_id_asl_by_id_user(String id, Connection db )
	{
		Integer[]				ret	 = new Integer [2];
		ret[0]  = -1;
		ret[1]  = 1;
		
		PreparedStatement	stat = null;
		ResultSet			res	 = null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			int iid = Integer.parseInt( id );
			try
			{
				String query_discriminante = "SELECT id_asl,n_livello " +
										 	 "FROM oia_nodo " +
										     "WHERE id_utente = ? ";
	
				PreparedStatement ps = db.prepareStatement(query_discriminante);
				int indice = 1;
				ps.setInt(indice++, iid);
				res = ps.executeQuery();
				
				while( res.next() )
				{
					ret[0]  = res.getInt("id_asl");
					ret[1]  = res.getInt("n_livello");
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}
					
					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		return ret;
	}
	
	public static ArrayList<UserInfo> load_UserInfo_per_id_asl( String id_asl, Connection db )
	{
		ArrayList<UserInfo>	ret		= new ArrayList<UserInfo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		id_asl						= (id_asl == null)	? ("")	:	(id_asl.trim());
		
		String sql = "SELECT * " +
					 "FROM v_user_info " +
					 "WHERE id_asl=? ";
		
		try
		{
			
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( id_asl  ) );
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res, db ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static ArrayList<UserInfo> load_UserInfo_per_id_asl_e_ruolo( String id_asl, String id_ruolo, Connection db )
	{
		ArrayList<UserInfo>	ret		= new ArrayList<UserInfo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		id_asl						= (id_asl == null)		? ("")	:	(id_asl.trim());
		id_ruolo					= (id_ruolo == null)	? ("")	:	(id_ruolo.trim());
		
		String sql = "SELECT * " +
					 "FROM v_user_info " +
					 "WHERE id_asl=? and id_ruolo=? ";
		
		try
		{
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( id_asl ) );
			stat.setInt( 2, Integer.parseInt( id_ruolo ) );
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res, db ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static ArrayList<UserInfo> load_UserInfos( Connection db )
	{
		ArrayList<UserInfo>	ret		= new ArrayList<UserInfo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		
		String sql = "SELECT * " +
					 "FROM v_user_info ";
		
		try
		{
			stat = db.prepareStatement( sql );
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res, db ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static ArrayList<UserInfo> load_UserInfo_per_id_asl_e_ruoli( String id_asl,String tipoRespOrDelegato,  Connection db )
	{
		ArrayList<UserInfo>	ret		= new ArrayList<UserInfo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		id_asl						= (id_asl == null)	? ("")	:	(id_asl.trim());
		
		String sql = "SELECT * " +
					 "FROM v_user_info " +
					 "WHERE 1=1 " ;
		
		if (id_asl!= null && ! id_asl.equals("-1") )
		{
			sql += " and id_asl=? " ;
		}
		
		if (tipoRespOrDelegato.equals("1"))
		{
			sql += " and id_ruolo in (16,21,43,19,42) order by id_ruolo, cognome_utente,nome_utente ";
		}
		else
		{
			sql += " and id_ruolo= 59 order by cognome_utente"; // da cambiare
		}
		
		
		try
		{
			
			stat = db.prepareStatement( sql );
			
			if (id_asl!= null && ! id_asl.equals("-1") )
			{
				stat.setInt( 1, Integer.parseInt( id_asl ) );
			}
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res, db ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static ArrayList<OiaNodo> load_unita_operative( String id_asl, Connection db,SystemStatus system )
	{
		ArrayList<OiaNodo>	ret		= new ArrayList<OiaNodo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		id_asl						= (id_asl == null)	? ("")	:	(id_asl.trim());
		
		String sql = "SELECT oia_nodo.id from oia_nodo   WHERE id_asl=? and n_livello = 3 order by n_livello,id_padre ";
		
		try
		{
			
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( id_asl ) );

			res		= stat.executeQuery();
			while( res.next() )
			{
				OiaNodo nodo = new OiaNodo(db, res.getInt(1), system);
				ret.add(nodo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	
	private static UserInfo loadResultSet( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		UserInfo ret = new UserInfo();
	
		Field[]	f = ret.getClass().getDeclaredFields();
		Method[]	m = ret.getClass().getMethods();
		for( int i = 0; i < f.length; i++ )
		{
			 Method getter	= null;
	    	 Method setter	= null;
	    	 Field	campo	= f[i];
	         String field = f[i].getName();
	         for( int j = 0; j < m.length; j++ )
	         {
	             String met = m[j].getName();
	             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
	             {
	                  getter = m[j];
	             }
	             else if( met.equalsIgnoreCase( "SET" + field ) )
	             {
	                 setter = m[j];
	             }
	         }	
	     
	         if( (getter != null) && (setter != null) && (campo != null) )
	         {
	        	 Object o = null;
	             
	             switch ( parseType( campo.getType() ) )
	             {
	             case INT:
	                 o = res.getInt( field );
	                 break;
	             case STRING:
	                 o = res.getString( field );
	                 break;
	             case BOOLEAN:
	                 o = res.getBoolean( field );
	                 break;
	             case TIMESTAMP:
	                 o = res.getTimestamp( field );
	                 break;
	             case DATE:
	                 o = res.getDate( field );
	                 break;
	             case FLOAT:
	                 o = res.getFloat( field );
	                 break;
	             case DOUBLE:
	                 o = res.getDouble( field );
	                 break;
	             }
	             
	             setter.invoke( ret, o );
	         
	         }
		}
	
		return ret;
	}
	
	protected static int parseType(Class<?> type)
    {
        int ret = -1;
        
        String name = type.getSimpleName();
        
        if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
        {
            ret = INT;
        }
        else if( name.equalsIgnoreCase( "string" ) )
        {
            ret = STRING;
        }
        else if( name.equalsIgnoreCase( "double" ) )
        {
            ret = DOUBLE;
        }
        else if( name.equalsIgnoreCase( "float" ) )
        {
            ret = FLOAT;
        }
        else if( name.equalsIgnoreCase( "timestamp" ) )
        {
            ret = TIMESTAMP;
        }
        else if( name.equalsIgnoreCase( "date" ) )
        {
            ret = DATE;
        }
        else if( name.equalsIgnoreCase( "boolean" ) )
        {
            ret = BOOLEAN;
        }
        
        return ret;
    }
	
	

}
