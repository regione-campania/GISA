/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.lineeattivita.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class RelAtecoLineeAttivita extends GenericBean
{

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_linee_attivita;
	private int id_lookup_codistat;
	
	
	// Campi non presenti nella tabella "la_rel_ateco_attivita"
	private String codice_istat;
	private String categoria;
	private String linea_attivita;
	
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
    
//	public static RelAtecoLineeAttivita load_principale_per_org_id(String org_id, Connection db )
//	{
//
//		RelAtecoLineeAttivita		ret		= null;
//		PreparedStatement	stat	= null;
//		ResultSet			res		= null;
//		
//		if( (org_id != null) && (org_id.trim().length() > 0) )
//		{
//			try
//			{
//				String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat " +
//							 "FROM la_rel_ateco_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
//							 "WHERE i.trashed_date IS NULL " +
//									"AND i.org_id=? " +
//									"AND i.id_rel_ateco_attivita=rel.id " +
//									"AND rel.id_linee_attivita=la.id " +
//									"AND rel.id_lookup_codistat=cod.code";
//				
//				stat	= db.prepareStatement( sql );
//				
//				stat.setInt( 1, Integer.parseInt(org_id) );
//				res		= stat.executeQuery();
//				if( res.next() )
//				{
//					ret = loadResultSet( res, db );
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			finally
//			{
//				try
//				{
//					if( res != null )
//					{
//						res.close();
//						res = null;
//					}
//					
//					if( stat != null )
//					{
//						stat.close();
//						stat = null;
//					}
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		return ret;
//	
//	}
	
	private static RelAtecoLineeAttivita loadResultSet( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		RelAtecoLineeAttivita ret = new RelAtecoLineeAttivita();
	
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

	public static ArrayList<RelAtecoLineeAttivita> load_rel_ateco_linee_attivita_per_codice_istat( String codice_istat, Connection db )
	{
		ArrayList<RelAtecoLineeAttivita>		ret		= new ArrayList<RelAtecoLineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		codice_istat						= (codice_istat == null)						? ("") : (codice_istat.trim());
		
		String sql = "SELECT rel.*, la.categoria, la.linea_attivita, cod.description as codice_istat " +
					 "FROM la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE cod.description=? " +
					       "AND rel.id_linee_attivita=la.id " +
					       "AND rel.id_lookup_codistat=cod.code "; 
					       //+ "AND rel.id_linee_attivita!=999";
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setString( 1, codice_istat );
			
			
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
	
	public static ArrayList<RelAtecoLineeAttivita> load_rel_ateco_linee_attivita_per_orgId( String orgId, Connection db )
	{
		ArrayList<RelAtecoLineeAttivita>		ret		= new ArrayList<RelAtecoLineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		orgId						= (orgId == null)						? ("") : (orgId.trim());
		
		String sql = "SELECT rel.*, la.categoria, la.linea_attivita, cod.description as codice_istat " +
					 "FROM la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod, la_imprese_linee_attivita ila " +
					 "WHERE ila.org_id=? " +
					 	"AND ila.id_rel_ateco_attivita = rel.id " +
					 	"AND ila.trashed_date is null " +
					 	"AND rel.id_linee_attivita=la.id " +
					 	"AND rel.id_lookup_codistat=cod.code "; 
					       //+ "AND rel.id_linee_attivita!=999";
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt(orgId) );
			
			
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
	
	public static ArrayList<RelAtecoLineeAttivita> load_all_rel_ateco_linee_attivita( Connection db )
	{
		ArrayList<RelAtecoLineeAttivita>		ret		= new ArrayList<RelAtecoLineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		String sql = "SELECT rel.*, la.categoria, la.linea_attivita, cod.description as codice_istat " +
					 "FROM la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE " +
					 		"rel.id_linee_attivita=la.id " +
					       "AND rel.id_lookup_codistat=cod.code";
		
		try
		{
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
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
	
	public static boolean controlla_linea_attivita_definita_per_orgId(int orgId, Connection db){
		boolean ret=false;
		
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		String sql = "SELECT count (la_imprese_linee_attivita.id) as num " +
					 "FROM la_imprese_linee_attivita, la_rel_ateco_attivita " +
					 "WHERE la_imprese_linee_attivita.id_rel_ateco_attivita = la_rel_ateco_attivita.id " +
					 	"AND la_rel_ateco_attivita.id_linee_attivita = 999" +
					 	"AND la_imprese_linee_attivita.org_id = ? " +
					 	"AND la_imprese_linee_attivita.trashed_date is null";
		try
		{
			
			stat = db.prepareStatement(sql);
			stat.setInt(1, orgId);
			res = stat.executeQuery();
			
			if (res.next()){
				if (res.getInt(1)==0)
					ret=false;
				else
					ret=true;
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
	
	/* **************************************** Getter & setter bean **************************************** */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_linee_attivita() {
		return id_linee_attivita;
	}

	public void setId_linee_attivita(int idLineeAttivita) {
		id_linee_attivita = idLineeAttivita;
	}

	public int getId_lookup_codistat() {
		return id_lookup_codistat;
	}

	public void setId_lookup_codistat(int idLookupCodistat) {
		id_lookup_codistat = idLookupCodistat;
	}

	public String getCodice_istat() {
		return codice_istat;
	}

	public void setCodice_istat(String codiceIstat) {
		codice_istat = codiceIstat;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getLinea_attivita() {
		return linea_attivita;
	}

	public void setLinea_attivita(String lineaAttivita) {
		linea_attivita = lineaAttivita;
	}
	
	

}
