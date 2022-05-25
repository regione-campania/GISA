/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.operatorifuoriregione.base;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.aspcfs.utils.CsvLunghezzaFissa;

import com.darkhorseventures.framework.beans.GenericBean;

public class BOperatori extends GenericBean
{
	
	private static final long serialVersionUID = 7127566929908851546L;

	private static final int INT = Types.INTEGER;

	private static final int STRING = Types.VARCHAR;

	private static final int DOUBLE = Types.DOUBLE;

	private static final int FLOAT = Types.FLOAT;

	private static final int TIMESTAMP = Types.TIMESTAMP;

	private static final int DATE = Types.DATE;

	private static final int BOOLEAN = Types.BOOLEAN;
	
	private int org_id;
	
	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	
	
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	private String codice_fiscale;
	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	private String partita_iva;
	public String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}
	private String nome_correntista;	
	public String getNome_correntista() {
		return nome_correntista;
	}

	public void setNome_correntista(String nome_correntista) {
		this.nome_correntista = nome_correntista;
	}
	
	public static Vector<BOperatori> load( String targa, Connection db )
	{
		Vector<BOperatori>	ret		= new Vector<BOperatori>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		targa	= (targa == null)	? ("") : (targa.trim());
		
		String sql = "SELECT * FROM organization ";
		
		try
		{
			boolean where = false;
			
			if( targa.length() > 0 )
			{
				sql += ( " WHERE tipologia = 1 and tipo_dest = 'Autoveicolo' and nome_correntista ILIKE '%" + targa.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			
			
			
				sql += " ORDER BY nome_correntista LIMIT 100 ";
			
			stat = db.prepareStatement( sql );
			
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

	public static Vector<BOperatori> loadT( String targa, Connection db )
	{
		Vector<BOperatori>	ret		= new Vector<BOperatori>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		targa	= (targa == null)	? ("") : (targa.trim());
		
		String sql = "SELECT * FROM organization ";
		
		try
		{
			boolean where = false;
			
			if( targa.length() > 0 )
			{
				sql += ( " WHERE tipologia = 1 and tipo_dest = 'Distributori' and name ILIKE '%" + targa.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			
			
			
				sql += " ORDER BY name LIMIT 100 ";
			
			stat = db.prepareStatement( sql );
			
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

	private static BOperatori loadResultSet( ResultSet res ) 
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		BOperatori ret = new BOperatori();

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

	
	
	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "INSERT INTO organization( ";
		
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
	
	public void carica( CsvLunghezzaFissa csv  ) 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Field[] f = this.getClass().getDeclaredFields();
        Method[] m = this.getClass().getMethods();
		
		for( int i = 0; i < f.length; i++ )
        {
			
			 for( int j = 0; j < m.length; j++ )
             {
                String met = m[j].getName();
                String field = f[i].getName();
	            String value = csv.get( field );
	            Object o = null;
                if( met.equalsIgnoreCase( "SET" + field ) && (value != null) && (value.trim().length() > 0) )
                {
                	value = value.trim();
		            switch ( parseType( f[i].getType() ) )
		            {
		            case INT:
		                o = Integer.parseInt( value );
		                break;
		            case STRING:
		                o = value;
		                break;
		            case BOOLEAN:
		                o = ( ("0".equals(value)) ? (false) : (true) );
		                break;
		            case TIMESTAMP:
		                o = parseDate( value );
		                break;
		            }
                    m[j].invoke( this, o );
                 }
             }
			
        }
        
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

     private static Timestamp parseDate( String date )
     {
    	 Timestamp ret = null;
    	 
    	 SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
    	 try 
    	 {
			ret = new Timestamp( sdf.parse( date ).getTime() );
    	 }
    	 catch (ParseException e)
    	 {
			e.printStackTrace();
    	 }
    	 
    	 return ret;
     }



	
}

