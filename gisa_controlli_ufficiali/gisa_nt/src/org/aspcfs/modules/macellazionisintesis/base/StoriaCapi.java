/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionisintesis.base;

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
import java.util.Vector;

import com.darkhorseventures.framework.beans.GenericBean;

public class StoriaCapi extends GenericBean
{
	private static final long serialVersionUID = 8313006891554941893L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_capo;
	private String description;
	private String operazione;
	private String note;
	private int entered_by;
	private Timestamp entered;
	
	public int getId_capo() {
		return id_capo;
	}
	public void setId_capo(int id_capo) {
		this.id_capo = id_capo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOperazione() {
		return operazione;
	}
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	
	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		String sql = "INSERT INTO m_capi_storia( ";
		
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
    
	public static StoriaCapi load(String id, Connection db )
	{

		StoriaCapi			ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM m_capi_storia WHERE id = ? " );
				stat.setInt( 1, iid );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res );
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
	
	private static StoriaCapi loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		StoriaCapi ret = new StoriaCapi();
	
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

	public static Vector<StoriaCapi> load( int id_capo, Connection db )
	{
		Vector<StoriaCapi>		ret		= new Vector<StoriaCapi>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_capi_storia WHERE id_capo = ? ORDER BY entered LIMIT 100 ";
		
		try
		{
			stat = db.prepareStatement( sql );
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

}
