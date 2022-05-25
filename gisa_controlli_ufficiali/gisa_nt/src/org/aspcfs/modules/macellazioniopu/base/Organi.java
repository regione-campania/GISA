/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioniopu.base;

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
import java.util.Vector;

import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.beans.GenericBean;

public class Organi extends GenericBean
{

	private static final long serialVersionUID = -906599187340806298L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;

	private int id						= -1;
	private int id_capo					= -1;
	private int lcso_patologia			= -1;
	private String lcso_patologia_altro = "";
	private int lcso_stadio				= -1;
	private int lcso_organo				= -1;
	
	private String istopatologico_topografia = "";
	private String istopatologico_interessamento_altri_organi = "";
	private int istopatologico_id_richiesta = -1;
	private String identificativo_campione_organo = "";
	private int id_esito_istopatologico = -1;
	private int id_classificazione_istopatologico = -1;
	private int id_valore_classificazione_istopatologico = -1;
	
	//informazioni di sistema
	private Timestamp entered;
	private Timestamp modified;
	private Timestamp trashed_date;
	private int entered_by;
	private int modified_by;
	private int trashed_by;
	private boolean enabled = true;

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}

	public int getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}

	public int getTrashed_by() {
		return trashed_by;
	}

	public void setTrashed_by(int trashed_by) {
		this.trashed_by = trashed_by;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId_capo() {
		return id_capo;
	}

	public void setId_capo(int id_capo) {
		this.id_capo = id_capo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLcso_patologia() {
		return lcso_patologia;
	}

	public void setLcso_patologia(int lcso_patologia) {
		this.lcso_patologia = lcso_patologia;
	}

	public String getLcso_patologia_altro() {
		return lcso_patologia_altro;
	}

	public void setLcso_patologia_altro(String lcso_patologia_altro) {
		this.lcso_patologia_altro = lcso_patologia_altro;
	}

	public int getLcso_stadio() {
		return lcso_stadio;
	}

	public void setLcso_stadio(int lcso_stadio) {
		this.lcso_stadio = lcso_stadio;
	}

	public int getLcso_organo() {
		return lcso_organo;
	}

	public void setLcso_organo(int lcso_organo) {
		this.lcso_organo = lcso_organo;
	}
	
	
	
	public String getIstopatologico_topografia() {
		return istopatologico_topografia;
	}

	public void setIstopatologico_topografia(String istopatologico_topografia) {
		this.istopatologico_topografia = istopatologico_topografia;
	}

	public String getIstopatologico_interessamento_altri_organi() {
		return istopatologico_interessamento_altri_organi;
	}

	public void setIstopatologico_interessamento_altri_organi(String istopatologico_interessamento_altri_organi) {
		this.istopatologico_interessamento_altri_organi = istopatologico_interessamento_altri_organi;
	}
	

	
	public int getIstopatologico_id_richiesta() {
		return istopatologico_id_richiesta;
	}

	public void setIstopatologico_id_richiesta(int istopatologico_id_richiesta) {
		this.istopatologico_id_richiesta = istopatologico_id_richiesta;
	}
	
	

	public String getIdentificativo_campione_organo() {
		return identificativo_campione_organo;
	}

	public void setIdentificativo_campione_organo(String identificativo_campione_organo) {
		this.identificativo_campione_organo = identificativo_campione_organo;
	}
	
	
	public int getId_classificazione_istopatologico() {
		return id_classificazione_istopatologico;
	}

	public void setId_classificazione_istopatologico(int id_classificazione_istopatologico) {
		this.id_classificazione_istopatologico = id_classificazione_istopatologico;
	}

	public int getId_valore_classificazione_istopatologico() {
		return id_valore_classificazione_istopatologico;
	}

	public void setId_valore_classificazione_istopatologico(int id_valore_classificazione_istopatologico) {
		this.id_valore_classificazione_istopatologico = id_valore_classificazione_istopatologico;
	}

	public void setId_classificazione_istopatologico(String id_classificazione) {
		if (id_classificazione != null)
		this.id_classificazione_istopatologico = Integer.valueOf(id_classificazione);
	}

	
	public void setId_valore_classificazione_istopatologico(String id_valore_classificazione) {
		if (id_valore_classificazione != null)
			this.id_valore_classificazione_istopatologico = Integer.valueOf(id_valore_classificazione);
	}
	
	

	public int getId_esito_istopatologico() {
		return id_esito_istopatologico;
	}

	public void setId_esito_istopatologico(int id_esito_istopatologico) {
		this.id_esito_istopatologico = id_esito_istopatologico;
	}
	
	public void setId_esito_istopatologico(String id_esito_istopatologico) {
		this.id_esito_istopatologico = Integer.valueOf(id_esito_istopatologico);
	}

	public Organi() {
		super();
	}

	public static Organi loadOrgano(int idOrgano, Connection db){
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		Organi organo = new Organi();
		
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM m_lcso_organi WHERE trashed_date IS NULL AND enabled AND id =  ? ORDER BY id ASC" );
			stat.setInt( 1, idOrgano );
			res		= stat.executeQuery();
			while( res.next() )
			{
			organo = loadResultSet( res ) ;
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
		
		return organo;
	
		
	}

	public static ArrayList<Organi> loadByOrgani( int id_capo, Connection db )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM m_lcso_organi WHERE trashed_date IS NULL AND enabled AND id_capo = ? ORDER BY id ASC" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public static ArrayList<Organi> loadByOrgani( int id_capo )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		Connection db = null;
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			db = GestoreConnessioni.getConnection();
			stat	= db.prepareStatement( 
					"SELECT * FROM m_lcso_organi WHERE trashed_date IS NULL AND enabled AND id_capo = ? ORDER BY id ASC" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
				if( db != null )
				{
					GestoreConnessioni.freeConnection(db);
					db = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static ArrayList<Organi> loadOrganiByIdistopatologico( int id_istopatologico, Connection db )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM m_lcso_organi WHERE trashed_date IS NULL AND enabled AND istopatologico_id_richiesta = ?" );
			stat.setInt( 1, id_istopatologico );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public static ArrayList<Organi> loadOrganiTBC( int id_capo, Connection db )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"select o.* from m_lcso_organi o " +
					"right join m_lookup_patologie_organi po on po.code = o.lcso_patologia " +
					"where o.trashed_date is null and o.enabled = true " +
					"and po.description ilike '%tbc:%' and po.enabled = true " +
					"AND o.id_capo = ? " +
					"ORDER BY id ASC" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public static ArrayList<Organi> loadOrganiBRC( int id_capo, Connection db )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"select o.* from m_lcso_organi o " +
					"right join m_lookup_patologie_organi po on po.code = o.lcso_patologia " +
					"where o.trashed_date is null and o.enabled = true " +
					"and po.description ilike '%brucellosi%' and po.enabled = true " +
					"AND o.id_capo = ? " +
					"ORDER BY id ASC" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
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
	
	
	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "INSERT INTO m_lcso_organi( ";
		
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
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
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

	private static Organi loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Organi ret = new Organi();
	
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
	            	 if (!(( parseType( campo.getType() ) == INT)   && m[j].getParameterTypes() [0] == String.class))
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
	
	public ArrayList<Organi> getOrganiCapi(Connection db,int id_capo)
	{
		Organi		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		ArrayList<Organi> lista=new ArrayList<Organi>();
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM stabilimenti_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_capo = ?" );
			stat.setInt( 1, id_capo );
		
			
			res		= stat.executeQuery();
			if( res.next() )
			{
				ret = loadResultSet( res );
				lista.add(ret);
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
		
		return lista;
	}
	
	public void update(Connection db)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE m_lcso_organi SET ";
		
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
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
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
					"UPDATE m_lcso_organi SET modified = CURRENT_TIMESTAMP, trashed_date = CURRENT_TIMESTAMP, modified_by = ? " +
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
	
}
