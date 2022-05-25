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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class LineeAttivita extends GenericBean
{

	private static final long serialVersionUID = 282194104265092711L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int org_id;
	private int id_rel_ateco_attivita;
	private int id_attivita_masterlist;
	

	private boolean primario;
	private boolean mappato;
	
	// Campi non presenti nella tabella "la_imprese_linee_attivita"
	private String codice_istat;
	private String descrizione_codice_istat;
	private String categoria;
	private String linea_attivita;
	
	
	private String macroarea;
	private String aggregazione;
	private String attivita;
	
	private Timestamp	entered;
	private int			entered_by;
	private Timestamp	modified;
	private int			modified_by;
	private Timestamp	trashed_date;

	private int ranking;

	private int idAggregazione;

	private int idAttivita;

	private int idMacroarea;

	private int idLineaVecchiaOriginale;

	private boolean mappatoInListaLineeAttVecchiaAnag;
		
	public int getId_attivita_masterlist() {
		return id_attivita_masterlist;
	}

	public void setId_attivita_masterlist(int id_attivita_masterlist) {
		this.id_attivita_masterlist = id_attivita_masterlist;
	}
	public LineeAttivita store( Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
				
		this.entered	= new Timestamp( System.currentTimeMillis() );
		this.modified	= this.entered;
		
		String sql = "INSERT INTO la_imprese_linee_attivita( ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) &&
	            	!field.equalsIgnoreCase( "codice_istat" ) &&
	            	!field.equalsIgnoreCase( "descrizione_codice_istat" ) &&
	            	!field.equalsIgnoreCase( "categoria" ) &&
	            	!field.equalsIgnoreCase( "linea_attivita" ) &&
	            	!field.equalsIgnoreCase( "macroarea" ) &&
	            	!field.equalsIgnoreCase( "aggregazione" ) &&
	            	!field.equalsIgnoreCase( "attivita" ) &&
	            		( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) 
	            ) {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field;
	            }
	        }
	        
	    }
	    
	    sql += " ) VALUES (";
	    firstField = true;
	    
	    for( int i = 0; i < v.size(); i++ )
	    {
	        {
	            sql += (firstField) ? ("") : (",");
	            sql += " ?";
	            firstField = false;
	        }
	    }
	
	    sql += " )";
	    
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	                stat.setString( i, (String)o );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    stat.execute();
	    stat.close();
	    return LineeAttivita.load_linea_attivita_per_id( "" + DatabaseUtils.getCurrVal( db, "la_imprese_linee_attivita_id_seq", -1 ), db );
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
    
    
    
    /*public static  ArrayList<LineeAttivita> load_linea_attivita_per_cu(String idCu, Connection db )
	{

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		LineeAttivita temp = null ;
		if( (idCu != null) && (idCu.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select *, riferimento_id as org_id from get_linee_attivita(?, ?, ?, ?) ";
		
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				
				stat.setInt(1, -1);
				stat.setString(2, "organization");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSet( res, db );
					ret.add(temp);
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
	
	}*/
    
    public static  ArrayList<LineeAttivita> opu_load_linea_attivita_per_cu(String idCu, Connection db )
   	{

   		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
   		PreparedStatement	stat	= null;
   		ResultSet			res		= null;
   		LineeAttivita temp = null ;
   		if( (idCu != null) && (idCu.trim().length() > 0) )
   		{
   			try
   			{
   				int iid = Integer.parseInt( idCu );
   				
   				String sql = "select * from get_linee_attivita(?, ?, ?, ?) ";
   				
   				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
   				stat	= db.prepareStatement( sql );
   				
   				stat.setInt(1, -1);
   				stat.setString(2, "opu_stabilimento");
   				stat.setObject(3, null);
   				stat.setInt( 4, iid );
   				
   				res		= stat.executeQuery();
   				while( res.next() )
   				{
   					temp = loadResultSetOpu( res, db );
   					ret.add(temp);
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
    
    
    
    public static  ArrayList<LineeAttivita> opu_load_linea_attivita_per_cu(String idCu, Connection db, int idStabilimento )
	{

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		LineeAttivita temp = null ;
		if( (idCu != null) && (idCu.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select * from get_linee_attivita(?, ?, ?, ?) ";
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				stat.setInt(1, idStabilimento);
				stat.setString(2, "opu_stabilimento");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSetOpu( res, db );
					ret.add(temp);
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
    
    public static  ArrayList<LineeAttivita> opu_load_linea_attivita_per_cu_alt(String idCu, Connection db, int altId )
  	{

  		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();  
  		PreparedStatement	stat	= null;
  		ResultSet			res		= null;
  		LineeAttivita temp = null ;
  		if( (idCu != null) && (idCu.trim().length() > 0) )
  		{
  			try
  			{
  				int iid = Integer.parseInt( idCu );
  				
  				String sql = "select * from get_linee_attivita((select id from opu_stabilimento where alt_id = ?), ?, ?, ?) ";
  				
  				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
  				stat	= db.prepareStatement( sql );
  				
  				stat.setInt(1, altId);
  				stat.setString(2, "opu_stabilimento");
  				stat.setObject(3, null);
  				stat.setInt( 4, iid );
  				
  				res		= stat.executeQuery();
  				while( res.next() )
  				{
  					temp = loadResultSetOpu( res, db );
  					ret.add(temp);
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
    
    public static  ArrayList<LineeAttivita> ric_load_linea_attivita_per_cu(String idCu, Connection db )
	{

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		LineeAttivita temp = null ;
		if( (idCu != null) && (idCu.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select * from get_linee_attivita(?, ?, ?, ?) ";
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				stat.setInt(1, -1);
				stat.setString(2, "suap_ric_scia_stabilimento");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSetOpu( res, db );
					ret.add(temp);
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

    public static  ArrayList<LineeAttivita> sin_load_linea_attivita_per_cu(String idCu, Connection db )
   	{

   		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
   		PreparedStatement	stat	= null;
   		ResultSet			res		= null;
   		LineeAttivita temp = null ;
   		if( (idCu != null) && (idCu.trim().length() > 0) )
   		{
   			try
   			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select * from get_linee_attivita(?, ?, ?, ?) ";
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				stat.setInt(1, -1);
				stat.setString(2, "sintesis_stabilimento");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSetOpu( res, db );
					ret.add(temp);
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
    
    
    
    public static  ArrayList<LineeAttivita> anagrafica_load_linea_attivita_per_cu(String idCu, Connection db )
   	{

   		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
   		PreparedStatement	stat	= null;
   		ResultSet			res		= null;
   		LineeAttivita temp = null ;
   		if( (idCu != null) && (idCu.trim().length() > 0) )
   		{
   			try
			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select * from get_linee_attivita(?, ?, ?, ?) ";
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				stat.setInt(1, -1);
				stat.setString(2, "anagrafica.stabilimenti");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSetOpu( res, db );
					ret.add(temp);
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
    
	public static  ArrayList<LineeAttivita> load_linea_attivita_per_cu(String idCu, Connection db, int orgId )
	{

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		LineeAttivita temp = null ;
		if( (idCu != null) && (idCu.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( idCu );
				
				String sql = "select *, riferimento_id as org_id from get_linee_attivita(?, ?, ?, ?) ";
		
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				
				stat.setInt(1, orgId);
				stat.setString(2, "organization");
				stat.setObject(3, null);
				stat.setInt( 4, iid );
				
				res		= stat.executeQuery();
				while( res.next() )
				{
					temp = loadResultSet( res, db );
					ret.add(temp);
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
	public static LineeAttivita load_linea_attivita_per_id(String id, Connection db )
	{

		LineeAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				
				/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
							 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
							 "WHERE i.trashed_date IS NULL " +
									"AND i.id=? " +
									"AND i.id_rel_ateco_attivita=rel.id " +
									"AND rel.id_linee_attivita=la.id " +
									"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
				*/
				String sql = "SELECT i.*, opu.macroarea, opu.aggregazione, opu.attivita,la.categoria, la.categoria," +
						" la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat "+
						" FROM la_imprese_linee_attivita i "+
						" left join ml8_linee_attivita_nuove_materializzata opu on opu.id_attivita = i.id_attivita_masterlist, "+ 
						" la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod "+
						" WHERE i.trashed_date IS NULL "+
						" AND i.id= ? "+
						" AND i.id_rel_ateco_attivita=rel.id AND rel.id_linee_attivita=la.id "+ 	
						" AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
				
				//stat	= db.prepareStatement( "SELECT * FROM la_imprese_linee_attivita WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat	= db.prepareStatement( sql );
				
				stat.setInt( 1, iid );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
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
	
	public static LineeAttivita load_linea_attivita_per_codice_istat(String codice_istat, Connection db )
	{

		LineeAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (codice_istat != null) && (codice_istat.trim().length() > 0) )
		{
			try
			{
				String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
							 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
							 "WHERE i.trashed_date IS NULL " +
									"AND cod.description=? " +
									"AND i.id_rel_ateco_attivita=rel.id " +
									"AND rel.id_linee_attivita=la.id " +
									"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
				
				stat	= db.prepareStatement( sql );
				
				stat.setString(1, codice_istat);
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
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
	
	public static LineeAttivita load_linea_attivita_principale_per_org_id(String org_id, Connection db )
	{

		LineeAttivita		ret		= null;
		PreparedStatement	stat	= null;	
		ResultSet			res		= null;
		
		if( (org_id != null) && (org_id.trim().length() > 0) )
		{
			try
			{
				/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
							 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
							 "WHERE i.trashed_date IS NULL " +
									"AND i.org_id=? " +
									"AND i.id_rel_ateco_attivita=rel.id " +
									"AND rel.id_linee_attivita=la.id " +
									"AND rel.id_lookup_codistat=cod.code " +
									"AND i.primario = true and i.trashed_date is null";
									
				*/
				String sql = " SELECT riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";

				
				stat	= db.prepareStatement( sql );
				
				stat.setInt( 1, Integer.parseInt(org_id) );
				stat.setString( 2, "organization" );
				stat.setBoolean(3, true);
				stat.setInt(4, -1);
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
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
	
	
	
	private static LineeAttivita loadResultSetOpu( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		LineeAttivita ret = new LineeAttivita();
	
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
	         
	         
	         if (campo.getName().equalsIgnoreCase("id") || 
	        		 campo.getName().equalsIgnoreCase("orgId") || 	
	        		 campo.getName().equalsIgnoreCase("categoria") || 
	        		 campo.getName().equalsIgnoreCase("macroarea") || 
	        		 campo.getName().equalsIgnoreCase("linea_attivita") 
	         )
	         {
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
	         
	         }}
		}
	
		return ret;
	}
	
	private static LineeAttivita loadResultSet( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		LineeAttivita ret = new LineeAttivita();
	
		Field[]	f = ret.getClass().getDeclaredFields();
		Method[]	m = ret.getClass().getMethods();
		
		HashMap<String,Boolean> campiBeanNonPresentiSuDb = new HashMap<String,Boolean>();
		campiBeanNonPresentiSuDb.put("norma", true);
		campiBeanNonPresentiSuDb.put("ranking", true);
		campiBeanNonPresentiSuDb.put("tipologia", true);
		campiBeanNonPresentiSuDb.put("idmacroarea", true);
		campiBeanNonPresentiSuDb.put("idaggregazione", true);
		campiBeanNonPresentiSuDb.put("idattivita", true);
		campiBeanNonPresentiSuDb.put("candidatiperranking", true);
		campiBeanNonPresentiSuDb.put("idlineavecchiaoriginale", true);
		campiBeanNonPresentiSuDb.put("mappatoinlistalineeattvecchiaanag", true);
		
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
	             
	        	 boolean campoNonPresenteInDb = campiBeanNonPresentiSuDb.containsKey(field.toLowerCase());
	        	 
	             switch ( parseType( campo.getType() ) )
	             {
	             case INT:
	            	 o = campoNonPresenteInDb ? new Integer(0) : res.getInt(field);
	            	 break;
	             case STRING:
	            	o = campoNonPresenteInDb ? new String("") : res.getString( field ); 
	                 break;
	             case BOOLEAN:
	                 o = campoNonPresenteInDb ? false : res.getBoolean( field );
	                 break;
	             case TIMESTAMP:
	                 o = campoNonPresenteInDb ? new Timestamp( System.currentTimeMillis() ) : res.getTimestamp( field );
	                 break;
	             case DATE:
	                 o = campoNonPresenteInDb ? new Date(System.currentTimeMillis()) : res.getDate( field );
	                 break;
	             case FLOAT:
	                 o = campoNonPresenteInDb ? new Float(0.0) :  res.getFloat( field );
	                 break;
	             case DOUBLE:
	                 o = campoNonPresenteInDb ? new Double(0.0) : res.getDouble( field );
	                 break;
	             }
	             
	             setter.invoke( ret, o );
	         
	         }
		}
	
		return ret;
	}

	public static ArrayList<LineeAttivita> load_linee_attivita_per_org_id( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE i.trashed_date IS NULL " +
							"AND org_id=? " +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
		*/
		
		 String sql = "select riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			stat.setString( 2, "organization" );
			stat.setObject( 3, null);
			stat.setInt( 4, -1 );

			
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
	
	
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_org_id_per_import( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		try
		{
			String sql2 = "select riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";
			
			PreparedStatement stat2 = db.prepareStatement( sql2 );
			stat2.setInt( 1, Integer.parseInt( org_id  ) );
			stat2.setString( 2, "organization" );
			stat2.setObject( 3, null);
			stat2.setInt( 4, -1 );
	
			
			ResultSet res2		= stat2.executeQuery();
			while( res2.next() )
			{
				LineeAttivita linea = new LineeAttivita();
				linea.setId(res2.getInt("id"));
				linea.setCodice_istat(res2.getString("codice_linea"));
				linea.setMappato(res2.getBoolean("mappato"));
				linea.setIdAttivita(res2.getInt("id_attivita_masterlist"));
				ret.add(linea);
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
	
	
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_id_stabilimento_opu( String idStabilimento, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		idStabilimento						= (idStabilimento == null)	? ("") : (idStabilimento.trim());
//		
//		String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
//					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
//					 "WHERE i.trashed_date IS NULL " +
//							"AND org_id=? " +
//					        "AND i.id_rel_ateco_attivita=rel.id " +
//							"AND rel.id_linee_attivita=la.id " +
//							"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
		
		String sql = "select distinct on (id_attivita) id_attivita,riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";
		
		
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( idStabilimento  ) );
			stat.setString( 2, "opu_stabilimento" );
			stat.setObject(3, null);
			stat.setInt(4, -1);
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetOpu( res, db ) );
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
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_alt_id_stabilimento_opu( String altId, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		altId						= (altId == null)	? ("") : (altId.trim());
//		
//		String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
//					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
//					 "WHERE i.trashed_date IS NULL " +
//							"AND org_id=? " +
//					        "AND i.id_rel_ateco_attivita=rel.id " +
//							"AND rel.id_linee_attivita=la.id " +
//							"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
		
		String sql = "select distinct on (id_attivita) id_attivita,riferimento_id as org_id, * from get_linee_attivita((select id from opu_stabilimento where alt_id = ?), ?, ?, ?)";
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( altId  ) );
			stat.setString( 2, "opu_stabilimento" );
			stat.setObject(3, null);
			stat.setInt(4, -1);
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetOpu( res, db ) );
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
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_alt_id( String altId, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		altId						= (altId == null)	? ("") : (altId.trim());
//		
//		String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
//					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
//					 "WHERE i.trashed_date IS NULL " +
//							"AND org_id=? " +
//					        "AND i.id_rel_ateco_attivita=rel.id " +
//							"AND rel.id_linee_attivita=la.id " +
//							"AND rel.id_lookup_codistat=cod.code and i.trashed_date is null";
		
		
		String sql = "select * from get_linee_attivita(?, ?, ?, ?)";
				
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( altId  ) );
			stat.setString( 2, "suap_ric_scia_stabilimento" );
			stat.setObject(3, null);
			stat.setInt(4, -1);
			
			res		= stat.executeQuery();
		
			while( res.next() )
			{
				ret.add( loadResultSetOpu( res, db ) );
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
	
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_alt_id_sintesis( String altId, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		altId						= (altId == null)	? ("") : (altId.trim());
		
		String sql = "select riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( altId  ) );
			stat.setString( 2, "sintesis_stabilimento" );
			stat.setObject(3, null);
			stat.setInt(4, -1);
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetOpu( res, db ) );
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
	
	
	public static ArrayList<LineeAttivita> load_linee_attivita_per_alt_id_anagrafica( String altId, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		altId						= (altId == null)	? ("") : (altId.trim());
		
		//String sql = "select * from anagrafica.linee_attivita_stabilimenti_view where alt_id = ? and enabled ";
		String sql = "select riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";
		
		try
		{	
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( altId ) );
			stat.setString( 2, "anagrafica.stabilimenti" );
			stat.setObject(3, null);
			stat.setInt(4, -1);
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetOpu( res, db ) );
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
	
	
	public static ArrayList<LineeAttivita> load_linee_attivita_secondarie_per_org_id( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE i.trashed_date IS NULL " +
							"AND org_id=? " +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code " +
							"AND i.primario=false and i.trashed_date is null";
	  */
		String sql = " SELECT riferimento_id as org_id, * from get_linee_attivita(?, ?, ?, ?)";

		 //" and opu.id_norma = 1";

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );

			stat.setInt( 1, Integer.parseInt(org_id) );
			stat.setString( 2, "organization" );
			stat.setBoolean(3, false);			
			stat.setInt(4, -1);
			
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
	
	public static ArrayList<LineeAttivita> load_linee_attivita_secondarie_per_stab_id( String stab_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		stab_id						= (stab_id == null)						? ("") : (stab_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE i.trashed_date IS NULL " +
							"AND org_id=? " +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code " +
							"AND i.primario=false and i.trashed_date is null";
	  */
		String sql = "select  distinct l.macroarea, -1 as id, -1 as org_id, false as primario, -1 as id_rel_ateco_attivita, null::timestamp as entered, null::timestamp as modified, null::timestamp as trashed_date, -1 as entered_by, -1 as modified_by, -1 as id_attivita_masterlist, '' as categoria, l.attivita as attivita, aggregazione, l.attivita as linea_attivita ,'' as codice_istat , '' as descrizione_codice_istat "+
				 " from ml8_linee_attivita_nuove_materializzata l "+  
				 " join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita and rslp.enabled=true "+  
				" where rslp.id_stabilimento = ? "+
				" and rslp.primario is not true";

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( stab_id  ) );
			
			
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
	
	public static LineeAttivita load_linee_attivita_per_org_id_id_rel_ateco( String org_id,String id_rel_ateco_attivita, int id_attivita_ms ,Connection db,boolean primario )
	{
		LineeAttivita		ret		= null;
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE  " +
							" org_id=? " +
							"AND id_rel_ateco_attivita = ? and primario =  ?" +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code " ;
			*/				
		String sql = "SELECT i.*, opu.macroarea, opu.aggregazione, opu.attivita, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat "+  
		" FROM la_imprese_linee_attivita i "+
		" left join ml8_linee_attivita_nuove_materializzata opu on opu.id_attivita = i.id_attivita_masterlist,"+
		" la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod, attivita_852_ateco_masterlist att  "+
		" WHERE  org_id=?  "+
		" AND id_rel_ateco_attivita = ? and primario =  ? and i.id_attivita_masterlist = ? "+ 
		" AND i.id_rel_ateco_attivita=rel.id  "+
		" AND rel.id_linee_attivita=la.id  "+
		" AND rel.id_lookup_codistat=cod.code" +
		" AND cod.description = att.codice_ateco";

		
		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			stat.setInt( 2, Integer.parseInt( id_rel_ateco_attivita ) );
			stat.setBoolean(3,primario);
			stat.setInt(4,id_attivita_ms);
			
			res		= stat.executeQuery();
			if( res.next() )
			{
				ret = loadResultSet( res, db ) ;
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
	
	
	public void update(Connection db) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE la_imprese_linee_attivita SET ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) &&
	            	!field.equalsIgnoreCase( "codice_istat" ) &&
	            	!field.equalsIgnoreCase( "descrizione_codice_istat" ) &&
	            	!field.equalsIgnoreCase( "categoria" ) &&
	            	!field.equalsIgnoreCase( "linea_attivita" ) &&
	            		( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field + " = ?";
	            }
	        }
	        
	    }
	    
	    sql += " WHERE id = ?";
	
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	                stat.setString( i, (String)o );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    
	    stat.setInt( v.size() + 1, id );
	    
	    stat.execute();
	    stat.close();
			
	}

	public static void delete( int id, int user_id, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
						"UPDATE la_imprese_linee_attivita " +
						"SET modified = CURRENT_TIMESTAMP, " +
							"trashed_date = CURRENT_TIMESTAMP, " +
							"modified_by = ? " +
						"WHERE id = ? AND trashed_date IS NULL" );

			stat.setInt( 1, user_id );
			stat.setInt( 2, id );
			
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
	
	public static void ripristina( int id, int id_attivita, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
						"UPDATE la_imprese_linee_attivita " +
						"SET  trashed_date = null , id_attivita_masterlist = ? "+
						"WHERE id = ? " );

			stat.setInt( 1, id_attivita );
			stat.setInt( 2, id );
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
	
	public static void delete_by_orgId( int orgId, int user_id, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
						"UPDATE la_imprese_linee_attivita " +
						"SET modified = CURRENT_TIMESTAMP, " +
							"trashed_date = CURRENT_TIMESTAMP, " +
							"modified_by = ? " +
						"WHERE org_id = ? AND trashed_date IS NULL" );

			stat.setInt( 1, user_id );
			stat.setInt( 2, orgId );
			
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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

	public static void delete_by_orgId_aggiorna( int orgId, int user_id, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
						"update la_imprese_linee_attivita set trashed_date = current_date ,modified_by = ? " +
						"WHERE org_id = ? " );

			
			stat.setInt( 1,  user_id);
			stat.setInt( 2, orgId );
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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
	
	/* **************************************** Getter & setter bean **************************************** */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int orgId) {
		org_id = orgId;
	}

	public int getId_rel_ateco_attivita() {
		return id_rel_ateco_attivita;
	}

	public void setId_rel_ateco_attivita(int idRelAtecoAttivita) {
		id_rel_ateco_attivita = idRelAtecoAttivita;
	}


	public boolean isPrimario() {
		return primario;
	}

	public void setPrimario(boolean primario) {
		this.primario = primario;
	}

	
	public boolean isMappato() {
		return mappato;
	}

	public void setMappato(boolean mappato) {
		this.mappato = mappato;
	}
	
	public String getCodice_istat() {
		return codice_istat;
	}

	public void setCodice_istat(String codiceIstat) {
		codice_istat = codiceIstat;
	}
	
	public String getDescrizione_codice_istat() {
		return descrizione_codice_istat;
	}

	public void setDescrizione_codice_istat(String descrizioneCodiceIstat) {
		descrizione_codice_istat = descrizioneCodiceIstat;
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

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public int getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(int enteredBy) {
		entered_by = enteredBy;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modifiedBy) {
		modified_by = modifiedBy;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setTrashed_date(Timestamp trashedDate) {
		trashed_date = trashedDate;
	}

	public String getMacroarea() {
		return macroarea;
	}

	public void setMacroarea(String macroarea) {
		this.macroarea = macroarea;
	}

	public String getAggregazione() {
		return aggregazione;
	}

	public void setAggregazione(String aggregazione) {
		this.aggregazione = aggregazione;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	
	public static LineeAttivita load_sottoattivita_principale_soa_per_org_id(String org_id, Connection db )
	{

		LineeAttivita		ret		= null;
		PreparedStatement	stat	= null;	
		ResultSet			res		= null;
		
		if( (org_id != null) && (org_id.trim().length() > 0) )
		{
			try
			{
				/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
							 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
							 "WHERE i.trashed_date IS NULL " +
									"AND i.org_id=? " +
									"AND i.id_rel_ateco_attivita=rel.id " +
									"AND rel.id_linee_attivita=la.id " +
									"AND rel.id_lookup_codistat=cod.code " +
									"AND i.primario = true and i.trashed_date is null";
									
				*/
				String sql = " SELECT i.id, i.id_soa as org_id, -1 as id_rel_ateco_attivita, true as primario, i.entered, i.entered_by, i.modified, i.modified_by, i.trashed_date, -1 as id_attivita_masterlist, '' as macroarea, '' as aggregazione, i.attivita as attivita, '' as categoria, '' as linea_attivita, '' as codice_istat, '' as descrizione_codice_istat "+
				"FROM soa_sottoattivita i "+
				"  WHERE i.trashed_date IS NULL "+
				"  AND i.id_soa= ?"+
				"  and i.trashed_date is null" +
				"  limit 1";
			
				stat	= db.prepareStatement( sql );
				
				stat.setInt( 1, Integer.parseInt(org_id) );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
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
	
	public static ArrayList<LineeAttivita> load_sottoattivita_secondaria_soa_per_org_id( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE i.trashed_date IS NULL " +
							"AND org_id=? " +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code " +
							"AND i.primario=false and i.trashed_date is null";
	  */
		String sql = "SELECT i.id, i.id_soa as org_id, -1 as id_rel_ateco_attivita, false as primario, i.entered, i.entered_by, i.modified, i.modified_by, i.trashed_date, -1 as id_attivita_masterlist, '' as macroarea, '' as aggregazione, i.attivita as attivita, '' as categoria, '' as linea_attivita, '' as codice_istat, '' as descrizione_codice_istat "+
				"FROM soa_sottoattivita i "+
				"  WHERE i.trashed_date IS NULL "+
				"  AND i.id_soa= ?"+
				"  and i.trashed_date is null" +
				"  offset 1";

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			
			
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

	public static LineeAttivita load_sottoattivita_principale_853_per_org_id(String org_id, Connection db )
	{

		LineeAttivita		ret		= null;
		PreparedStatement	stat	= null;	
		ResultSet			res		= null;
		
		if( (org_id != null) && (org_id.trim().length() > 0) )
		{
			try
			{
				/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
							 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
							 "WHERE i.trashed_date IS NULL " +
									"AND i.org_id=? " +
									"AND i.id_rel_ateco_attivita=rel.id " +
									"AND rel.id_linee_attivita=la.id " +
									"AND rel.id_lookup_codistat=cod.code " +
									"AND i.primario = true and i.trashed_date is null";
									
				*/
				String sql = " SELECT i.id, i.id_stabilimento as org_id, -1 as id_rel_ateco_attivita, true as primario, i.entered, i.entered_by, i.modified, i.modified_by, i.trashed_date, -1 as id_attivita_masterlist, '' as macroarea, '' as aggregazione, i.attivita as attivita, '' as categoria, '' as linea_attivita, '' as codice_istat, '' as descrizione_codice_istat "+
				"FROM stabilimenti_sottoattivita i "+
				"  WHERE i.trashed_date IS NULL "+
				"  AND i.id_stabilimento= ?"+
				"  and i.trashed_date is null" +
				"  limit 1";
			
				stat	= db.prepareStatement( sql );
				
				stat.setInt( 1, Integer.parseInt(org_id) );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db );
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
	
	public static ArrayList<LineeAttivita> load_sottoattivita_secondaria_853_per_org_id( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		/*String sql = "SELECT i.*, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat " +
					 "FROM la_imprese_linee_attivita i, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod " +
					 "WHERE i.trashed_date IS NULL " +
							"AND org_id=? " +
					        "AND i.id_rel_ateco_attivita=rel.id " +
							"AND rel.id_linee_attivita=la.id " +
							"AND rel.id_lookup_codistat=cod.code " +
							"AND i.primario=false and i.trashed_date is null";
	  */
		String sql = "SELECT i.id, i.id_stabilimento as org_id, -1 as id_rel_ateco_attivita, false as primario, i.entered, i.entered_by, i.modified, i.modified_by, i.trashed_date, -1 as id_attivita_masterlist, '' as macroarea, '' as aggregazione, i.attivita as attivita, '' as categoria, '' as linea_attivita, '' as codice_istat, '' as descrizione_codice_istat "+
				"FROM stabilimenti_sottoattivita i "+
				"  WHERE i.trashed_date IS NULL "+
				"  AND i.id_stabilimento= ?"+
				"  and i.trashed_date is null" +
				"  offset 1";

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			
			
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
	
	
	
	
	
	
	public static LineeAttivita load_linea_principale_globale_per_org_id(String org_id, Connection db )
	{

		//LineeAttivita		ret		= null;
		LineeAttivita		ret		= new LineeAttivita();
		PreparedStatement	stat	= null;	
		ResultSet			res		= null;
		
		if( (org_id != null) && (org_id.trim().length() > 0) )
		{
			try
			{
			
				String sql = " SELECT id, org_id, id_rel_ateco_attivita, primario, entered,entered_by,modified, modified_by, trashed_date,  id_attivita_masterlist,  macroarea,  aggregazione, attivita,  categoria,  linea_attivita,  codice_istat,  descrizione_codice_istat "+
				" FROM get_linea_attivita(?) where primario is true";
			
				stat	= db.prepareStatement( sql );
				
				stat.setInt( 1, Integer.parseInt(org_id) );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret.setId(res.getInt(1));
					ret.setOrg_id(res.getInt(2));
					ret.setId_rel_ateco_attivita(res.getInt(3));
					ret.setPrimario(res.getBoolean(4));
					ret.setEntered(res.getTimestamp(5));
					ret.setEntered_by(res.getInt(6));
					ret.setModified(res.getTimestamp(7));
					ret.setModified_by(res.getInt(8));
					ret.setTrashed_date(res.getTimestamp(9));
					ret.setId_attivita_masterlist(res.getInt(10));
					ret.setMacroarea(res.getString(11));
					ret.setAggregazione(res.getString(12));
					ret.setAttivita(res.getString(13));
					ret.setCategoria(res.getString(14));
					ret.setLinea_attivita(res.getString(15));
					ret.setCodice_istat(res.getString(16));
					ret.setDescrizione_codice_istat(res.getString(17));
					
					
					//ret = loadResultSet( res, db );
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
	
	public static ArrayList<LineeAttivita> load_linee_secondarie_globali_per_org_id( String org_id, Connection db )
	{
		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		
		String sql = " SELECT id, org_id, id_rel_ateco_attivita, primario, entered,entered_by,modified, modified_by, trashed_date,  id_attivita_masterlist,  macroarea,  aggregazione, attivita,  categoria,  linea_attivita,  codice_istat,  descrizione_codice_istat "+
				"FROM get_linea_attivita(?) where primario is not true";
			

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			
			
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
	
	
	
	
	
	
	//-----------------------------------------------------------
	
	public static int getIdNormaDaLineaMS(Connection db, int idLineaMasterList)
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		try
		{
			pst = db.prepareStatement("select id_norma from master_list_suap where id = ?");
			pst.setInt(1,idLineaMasterList);
			rs = pst.executeQuery();
			if(rs.next())
				toRet =  rs.getInt(1) ;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return toRet;
	}
	
	//[orgid,tipologia,idstab]
	public static int[] ottieniInfoOrgDaIdOpu(Connection db, int idOpu)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		int[] toRet = null;
		try
		{
			pst = db.prepareStatement("select * from get_org_from_idstab(?)");
			pst.setInt(1,idOpu);
			rs = pst.executeQuery();
			rs.next();
			int orgId = -1,tipologia = -1,idStab = -1;
			try
			{
				orgId = rs.getInt(1);
			}catch(Exception ex){orgId = -1;}
			try
			{
				tipologia = rs.getInt(2);
			}catch(Exception ex){ tipologia = -1;}
			try
			{
				idStab = rs.getInt(3);
			}catch(Exception ex){ idStab = -1;}
			
			toRet = new int[]{orgId,tipologia,idStab};
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			toRet = new int[]{-1,-1,-1};
			
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return toRet;
	}

	
	public static int ottieniLineaVecchiaDaNuovaFittizia(Connection db, String macroarea,
			String aggregazione, String attivita) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		
		try
		{
			pst = db.prepareStatement("select * from vecchia_da_nuova_fittizia(?,?,?)");
			pst.setString(1,macroarea);
			pst.setString(2,aggregazione);
			pst.setString(3,attivita);
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = rs.getInt(1);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{pst.close();}catch(Exception ex){}
			
		}
		return toRet;
	}
	
	
	
	
	//prende in input una terna di desc macroarea-desc aggregazione-desc attivita che è attività non presente in masterlistsuap
	//e per questa prima ottiene la corrispondente "linea_vecchia" in lista_linee_attivita_vecchie etc...e poi per questa ottiene
	//i candidati col rank massimo della master list
	
	
	public static ArrayList<LineeAttivita> load_candidati_per_linea_vecchiaKnowledgeBased(int idLineaVecchia,Connection db)
	{
		ArrayList<LineeAttivita> candidati = new ArrayList<LineeAttivita>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		try
		{
			
				
				//ottengo tutti i candidati nella master list per questa linea vecchia
				pst = db.prepareStatement("select * from proponi_candidatimaxrankmapping_dalinea(?)");
				pst.setInt(1,idLineaVecchia);
				rs = pst.executeQuery();
				while(rs.next())
				{
					LineeAttivita candidato = new LineeAttivita();
					candidato.setAggregazione(rs.getString(6));
					candidato.setAttivita(rs.getString(8));
					candidato.setMacroarea(rs.getString(4));
					candidato.setId(rs.getInt(2)); //id linea nuova
					candidato.setIdAggregazione(rs.getInt(5));
					candidato.setIdAttivita(rs.getInt(7));
					candidato.setIdMacroarea(rs.getInt(3));
					candidato.setRanking(rs.getInt(10));
					candidato.setNorma(rs.getInt(9));
					
					candidati.add(candidato);
				}
				
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{pst.close();}catch(Exception ex){}
		}
		
		
		
		return candidati;
	}
	
	
	//dalla linea nuova fittizia (perchè non esiste in opu) a quella vecchia in lista_linee_vecchia_ana
	public static ArrayList<LineeAttivita> load_candidati_per_linea_fittiziaVersioneKnowledgeBased(int idLineaVecchia, Connection db )
	{
		
		return load_candidati_per_linea_vecchiaKnowledgeBased(idLineaVecchia,db);
				
	}
	
	
	
	//dato un org_id, ottiene tutte le linee "vecchie" associate a quello stab, e per ciascuna di queste ottiene dal knowledge based mapping i candidati col rank massimo
	//nella master list
	//ciascuna linea ha come id quello LLAVA (e se non esiste una rappresentazione usa come id l'ORIG) e mantiene anche l'informazione 
	//sull'id linea ORIG
	public static ArrayList<LineeAttivita> load_tutteLLAVAeORIG_perOrg( String org_id, Connection db)
	{

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		HashMap<Integer,ArrayList<LineeAttivita>> tuttiCandidatiPerLineaVecchia  = new HashMap<Integer,ArrayList<LineeAttivita>>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		
//		String sql = " SELECT id, org_id, id_rel_ateco_attivita, primario, entered,entered_by,modified, modified_by, trashed_date,  id_attivita_masterlist,  macroarea,  aggregazione, attivita,  categoria,  linea_attivita,  codice_istat,  descrizione_codice_istat "+
//				"FROM get_linea_attivita(?)";
			
		String sql = "SELECT * FROM lista_linee_attivita_vecchia_anagrafica a join (SELECT * FROM get_vecchie_linee_da_organization(?)) b on a.id = b.id_vecchia_linea";
		String sqlCandidati = "SELECT * FROM proponi_candidati_mapping(?)";
		HashMap<Integer,Integer> maxRanksPerMapping = new HashMap<Integer,Integer>();
		
		try
		{
			//parte ottenimento candidati 
			try
			{
				stat = db.prepareStatement(sqlCandidati); //ottengo tutti i candidati ordinati per ranking, e scelgo solo (per ogni linea vecchia) quello / i (piu' di uno possono essercene) col rank massimo
				stat.setInt(1, Integer.parseInt(org_id));
				res = stat.executeQuery();
				while(res.next())
				{
					int idLineaVecchiaInListaLineeAttVeccAnag = res.getInt(1);
					
					
					
					if(tuttiCandidatiPerLineaVecchia.get(new Integer(idLineaVecchiaInListaLineeAttVeccAnag)) == null)
					{
						tuttiCandidatiPerLineaVecchia.put(new Integer(idLineaVecchiaInListaLineeAttVeccAnag), new ArrayList<LineeAttivita>());
					}
					LineeAttivita candidato = new LineeAttivita();
					
					candidato.setAggregazione(res.getString(6));
					candidato.setAttivita(res.getString(8));
					candidato.setMacroarea(res.getString(4));
					candidato.setId(res.getInt(2)); //id linea nuova
					candidato.setIdAggregazione(res.getInt(5));
					candidato.setIdAttivita(res.getInt(7));
					candidato.setIdMacroarea(res.getInt(3));
					candidato.setRanking(res.getInt(10));
					candidato.setNorma(res.getInt(9));
					tuttiCandidatiPerLineaVecchia.get(new Integer(idLineaVecchiaInListaLineeAttVeccAnag)).add(candidato);
					
					
					//salvo e aggiorno il max rank eventualmente
					if(maxRanksPerMapping.get(idLineaVecchiaInListaLineeAttVeccAnag) == null)
					{
						maxRanksPerMapping.put(idLineaVecchiaInListaLineeAttVeccAnag, new Integer(0));
					}
					
					int rankCandidato = res.getInt(10);
					//aggiorno eventualmente il max rank per quella linea vecchia
					maxRanksPerMapping.put(idLineaVecchiaInListaLineeAttVeccAnag,Math.max( rankCandidato, maxRanksPerMapping.get(idLineaVecchiaInListaLineeAttVeccAnag) ));
					
				}
				
				//elimino dai candidati tutti quelli, che , presa la linea vecchia che mappano, non hanno rank = al massimo rank per quella linea vecchia
				for(Integer idLineaVecchia : tuttiCandidatiPerLineaVecchia.keySet())
				{
					int maxPerQuellaLineaVecchia = maxRanksPerMapping.get(idLineaVecchia);
					ArrayList<LineeAttivita> vincitoriPerQuelMapping = new ArrayList<LineeAttivita>();
					for(LineeAttivita toTest : tuttiCandidatiPerLineaVecchia.get(idLineaVecchia))
					{
						if(toTest.getRanking() < maxPerQuellaLineaVecchia)
						{
							continue;
						}

						vincitoriPerQuelMapping.add(toTest); //e' uno di quelli con rank massimo per quella linea mappata vecchia
					}
					//sostituisco i vincitori
					tuttiCandidatiPerLineaVecchia.put(idLineaVecchia, vincitoriPerQuelMapping);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				try{stat.close();}catch(Exception ex){}
				try{res.close();}catch(Exception ex){}
			}
			
			
			//ottengo tutte le linee associate all'org, secondo la vecchia gestione (le uso per integrare)
			//cioè le ritorno esclusivamente se non esiste in LLAVA quella stessa linea
			ArrayList<LineeAttivita> lineeORIG = LineeAttivita.load_linee_ORIG_per_org_id(String.valueOf(org_id), db);
			HashMap<Integer,LineeAttivita> mapLineeORIG = new HashMap<Integer,LineeAttivita>();
			for(LineeAttivita lineaT : lineeORIG)
			{
//				int idLineaVecchiaOriginale = res.getInt(10);
//				  LineeAttivita lineaOriginale = mapLineeORIG.get(idLineaVecchiaOriginale); //questa ha come id proprio quello vecchio originale
				lineaT.setMappatoInListaLineeAttVecchiaAnag(false);
				lineaT.setIdLineaVecchiaOriginale(lineaT.getId()); //in questo modo id ORIG e id LLAVA corrispondono (e sono entrambi id ORIG )
				
				mapLineeORIG.put(lineaT.getId(),lineaT);
				
				
			}
			
			//ora ottengo quelle rappresentabili in LLAVA
			
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			res		= stat.executeQuery();
			
			HashMap<Integer,LineeAttivita> mapLineeORIG_to_LLAVA = new HashMap<Integer,LineeAttivita>();
			while(res.next()) //ciclo su tutte quelle trovate in lista_linee_attivita_vecchia_anagrafica (quelle trovate, hanno sempre anche id ORIG )
			{
				LineeAttivita iesima = new LineeAttivita();
				int idLineaLLAVA = res.getInt(1);
				int idLineaORIG = res.getInt(10);
				iesima.setId(idLineaLLAVA);
				iesima.setIdLineaVecchiaOriginale(idLineaORIG);  
				iesima.setOrg_id( Integer.parseInt(org_id) );
				iesima.setMacroarea(res.getString(4));
				iesima.setAggregazione(res.getString(5));
				iesima.setAttivita(res.getString(6));
				iesima.setLinea_attivita(res.getString(6));
				//valori dummy
				iesima.setId_rel_ateco_attivita(res.getInt(3));
				iesima.setPrimario(res.getBoolean(4));
				iesima.setEntered(new Timestamp(new java.util.Date().getTime()));
				iesima.setEntered_by(-1);
				iesima.setModified(new Timestamp(new java.util.Date().getTime()));
				iesima.setModified_by(-1);
				iesima.setTrashed_date(new Timestamp(new java.util.Date().getTime()));
				iesima.setId_attivita_masterlist(-1);
				iesima.setCategoria("");
				iesima.setTipologia(res.getInt(2));
				 
				
				iesima.setCodice_istat("");
				iesima.setDescrizione_codice_istat("");
				
				iesima.setMappatoInListaLineeAttVecchiaAnag(true);
				iesima.setCandidatiPerRanking(tuttiCandidatiPerLineaVecchia.get(new Integer(iesima.getId())));
				mapLineeORIG_to_LLAVA.put(idLineaORIG,iesima);
			}
			
			
			//ciclo su tutte quelle della gestione originale, che rappresnetano sicuramente TUTTE quelle che devo presentare
			for(Integer idORIG : mapLineeORIG.keySet())
			{
				LineeAttivita lineaToAdd = null;
				if(mapLineeORIG_to_LLAVA.containsKey(idORIG))
				{ //allora ritorno quella trovata secondo la nuova gestione
					lineaToAdd = mapLineeORIG_to_LLAVA.get(idORIG);
				}
				else
				{//altrimenti quella della vecchia gestione
					lineaToAdd = mapLineeORIG.get(idORIG);
				}
				
				ret.add(lineaToAdd);
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
	
	
	public void setMappatoInListaLineeAttVecchiaAnag(boolean b) {

		this.mappatoInListaLineeAttVecchiaAnag = b;
	}
	public boolean getMappatoInListaLineeAttVecchiaAnag()
	{
		return this.mappatoInListaLineeAttVecchiaAnag;
	}
	

	public int getIdLineaVecchiaOriginale()
	{
		return this.idLineaVecchiaOriginale;
	}
	
	public void setIdLineaVecchiaOriginale(int int2) {
		this.idLineaVecchiaOriginale = int2;
		
	}

	public static ArrayList<LineeAttivita> load_linee_ORIG_per_org_id( String org_id, Connection db )
	{
		

		ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		org_id						= (org_id == null)						? ("") : (org_id.trim());
		
		
		String sql = " SELECT id, org_id, id_rel_ateco_attivita, primario, entered,entered_by,modified, modified_by, trashed_date,  id_attivita_masterlist,  macroarea,  aggregazione, attivita,  categoria,  linea_attivita,  codice_istat,  descrizione_codice_istat "+
				"FROM get_linea_attivita(?)";
			

		
		try
		{
			
			//sql += " ORDER BY asl, identificativo LIMIT 100 ";
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( org_id  ) );
			
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				LineeAttivita iesima = new LineeAttivita();
				
				iesima.setId(res.getInt(1));
				iesima.setOrg_id(res.getInt(2));
				iesima.setId_rel_ateco_attivita(res.getInt(3));
				iesima.setPrimario(res.getBoolean(4));
				iesima.setEntered(res.getTimestamp(5));
				iesima.setEntered_by(res.getInt(6));
				iesima.setModified(res.getTimestamp(7));
				iesima.setModified_by(res.getInt(8));
				iesima.setTrashed_date(res.getTimestamp(9));
				iesima.setId_attivita_masterlist(res.getInt(10));
				iesima.setMacroarea(res.getString(11));
				iesima.setAggregazione(res.getString(12));
				iesima.setAttivita(res.getString(13));
				iesima.setCategoria(res.getString(14));
				iesima.setLinea_attivita(res.getString(15));
				iesima.setCodice_istat(res.getString(16));
				iesima.setDescrizione_codice_istat(res.getString(17));

				
				ret.add(iesima);
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

	
	
	
	
	
	private int norma;
	
	public void setNorma(int norma) {
		this.norma = norma;
	}
	
	
	public int getNorma(){return this.norma;}

	private int tipologia;
	
	public void setTipologia(int tipologia) {
		 this.tipologia = tipologia;
		
	}
	
	public int getTipologia()
	{
		return this.tipologia;
	}

	public void setIdMacroarea(int i) {
		this.idMacroarea = i;
		
	}

	public void setIdAttivita(int i) {
		// TODO Auto-generated method stub
		this.idAttivita = i;
		
	}

	public void setIdAggregazione(int i) {
		// TODO Auto-generated method stub
		this.idAggregazione = i;
		
	}

	public void setRanking(int i) {
		// TODO Auto-generated method stub
		this.ranking = i;
	}
	
	public int getIdAttivita(){return this.idAttivita;}
	public int getIdMacroarea(){return this.idMacroarea;}
	public int getIdAggregazione(){return this.idAggregazione;}
	
	
	public int getRanking(){return this.ranking;}
	
	
	
	
	ArrayList<LineeAttivita>		candidatiPerRanking		= new ArrayList<LineeAttivita>();
	public void setCandidatiPerRanking(ArrayList<LineeAttivita> candidatiNuoveLinee){
		this.candidatiPerRanking = candidatiNuoveLinee;
	}
	
	public ArrayList<LineeAttivita> getCandidatiPerRanking () {return candidatiPerRanking;}
	
	
	
	public static void gestisciRankingMapping(Connection db, int orgId, int stabId, int tipologia, int idVecchiaLinea, int idNuovaLinea, int idNorma, boolean importato)
	{
		PreparedStatement pst = null;
		 try
		 {
			 pst = db.prepareStatement("select * from gestisci_mapping(?,?,?,?,?,?,?)");
			 int u = 0;
			 pst.setInt(++u, orgId);
			 pst.setInt(++u, stabId);
			 pst.setInt(++u, tipologia);
			 pst.setInt(++u, idVecchiaLinea);
			 pst.setInt(++u, idNuovaLinea);
			 pst.setInt(++u, idNorma);
			 pst.setBoolean(++u, importato); //se lo stabilimento non e' stato importato, allora viene salvato solo il mapping ma non viene aggiunta l'entry per stabid/orgid nella tabella agganciata a knowledge_based_mapping
			 pst.executeQuery();
			 
			 System.out.println("INSERITO MAPPING PER LINEA VECCHIA "+idVecchiaLinea+ " VERSO LINEA NUOVA "+idNuovaLinea+ " PER STABILIMENTO"
					 + stabId +" E ORG ID "+orgId);
			 
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 try{pst.close();}catch(Exception ex){}
		 }
		 
	}
	
public static Integer ottieniIdPadreAlTerzoLivelloDataLinea(Connection db,Integer idLinea) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			//se la linea e' già del terzo....
			pst = db.prepareStatement("select * from master_list_suap where id = ? and livello = 3");
			pst.setInt(1,idLinea.intValue());
			rs = pst.executeQuery();
			if(rs.next())
			{
				return idLinea; //...ritorno di nuovo la stessa linea perche' già era terzo livello
			}
			//altrimenti ottengo l'antenato al terzo
			pst.close();
			rs.close();
			pst = db.prepareStatement("select path_id from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita = ? ");
			
			pst.setInt(1,idLinea.intValue());
			rs = pst.executeQuery();
			rs.next();
			
			String pathT = rs.getString(1);
			pathT = pathT.split(";")[3]; //quarto elemento, poiche' inizia sempre con -1
			return Integer.parseInt(pathT);
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return -1;
		}
		finally
		{
			try {pst.close();}catch(Exception ex){}
			try {rs.close();}catch(Exception ex){}
		}
		
	}


public boolean isCommercioAmbulante()
{
	if(
			this.getCodice_istat().equalsIgnoreCase("56.10.42") || 
			this.getCodice_istat().equalsIgnoreCase("47.81.01") || 
			this.getCodice_istat().equalsIgnoreCase("47.89.09") || 
			this.getCodice_istat().equalsIgnoreCase("47.81.02") || 
			this.getCodice_istat().equalsIgnoreCase("47.81.03") || 
			this.getCodice_istat().equalsIgnoreCase("47.81.09") 
			
			)
		
		return true ;
	
	return false;
		
	
}

public static ArrayList<LineeAttivita> load_linee_globali_per_org_id( String org_id, Connection db )
{
	

	ArrayList<LineeAttivita>		ret		= new ArrayList<LineeAttivita>();
	PreparedStatement				stat	= null;
	ResultSet						res		= null;
	
	org_id						= (org_id == null)						? ("") : (org_id.trim());
	
	
	String sql = " SELECT id, org_id, id_rel_ateco_attivita, primario, entered,entered_by,modified, modified_by, trashed_date,  id_attivita_masterlist,  macroarea,  aggregazione, attivita,  categoria,  linea_attivita,  codice_istat,  descrizione_codice_istat "+
			"FROM get_linea_attivita(?)";
		

	
	try
	{
		
		//sql += " ORDER BY asl, identificativo LIMIT 100 ";
		stat = db.prepareStatement( sql );
		stat.setInt( 1, Integer.parseInt( org_id  ) );
		
		
		res		= stat.executeQuery();
		while( res.next() )
		{
			LineeAttivita iesima = new LineeAttivita();
			
			iesima.setId(res.getInt(1));
			iesima.setOrg_id(res.getInt(2));
			iesima.setId_rel_ateco_attivita(res.getInt(3));
			iesima.setPrimario(res.getBoolean(4));
			iesima.setEntered(res.getTimestamp(5));
			iesima.setEntered_by(res.getInt(6));
			iesima.setModified(res.getTimestamp(7));
			iesima.setModified_by(res.getInt(8));
			iesima.setTrashed_date(res.getTimestamp(9));
			iesima.setId_attivita_masterlist(res.getInt(10));
			iesima.setMacroarea(res.getString(11));
			iesima.setAggregazione(res.getString(12));
			iesima.setAttivita(res.getString(13));
			iesima.setCategoria(res.getString(14));
			iesima.setLinea_attivita(res.getString(15));
			iesima.setCodice_istat(res.getString(16));
			iesima.setDescrizione_codice_istat(res.getString(17));

			
			ret.add(iesima);
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

	
	
}
