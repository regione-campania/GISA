/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import com.darkhorseventures.framework.beans.GenericBean;

public class Tipo extends GenericBean
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
	private String description;
	private Boolean enabled;
	private int level;
	private Timestamp trashedDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Timestamp getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}

	public static ArrayList<Tipo> loadAll(Connection db)
	{
		ArrayList<Tipo>		ret		= new ArrayList<Tipo>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = " SELECT m.id, m.description, m.enabled, m.level, " +
					 " m.trashed_date as trashedDate " +
					 " FROM m_lookup_tipi m " +
					 " WHERE m.trashed_date IS NULL " +
					 " ORDER BY m.level ASC ";
		try
		{	
			stat = db.prepareStatement( sql );
			
			res = stat.executeQuery();
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
	
	private static Tipo loadResultSet( ResultSet res, Connection db ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Tipo ret = new Tipo();
		
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
