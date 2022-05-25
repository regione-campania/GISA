/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.soa.base;

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
import java.util.ArrayList;
import java.util.Vector;

public class MerceInOut
{
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_soa;
	private int id_specie_mollusco;
	private int id_destinatario;
	private int id_provenienza;
	
	private int entered_by;
	private int modified_by;
	private int trashed_by;
	
	private String tipo_provenienza;
	private String stato_regione_provenienza;
	private String identificativo_documento_trasporto;
	private String quantita;

	private Timestamp data_arrivo;
	private Timestamp data_invio;
	private Timestamp entered;
	private Timestamp modified;
	private Timestamp trashed_date;
	
	
	
	public int getId_soa() {
		return id_soa;
	}
	public void setId_soa(int id_soa) {
		this.id_soa = id_soa;
	}
	public int getId_provenienza() {
		return id_provenienza;
	}
	public void setId_provenienza(int id_provenienza) {
		this.id_provenienza = id_provenienza;
	}
	public String getTipo_provenienza() {
		return tipo_provenienza;
	}
	public void setTipo_provenienza(String tipo_provenienza) {
		this.tipo_provenienza = tipo_provenienza;
	}
	public String getStato_regione_provenienza() {
		return stato_regione_provenienza;
	}
	public void setStato_regione_provenienza(String stato_regione_provenienza) {
		this.stato_regione_provenienza = stato_regione_provenienza;
	}
	public Timestamp getData_arrivo() {
		return data_arrivo;
	}
	public void setData_arrivo(Timestamp data_arrivo) {
		this.data_arrivo = data_arrivo;
	}
	public int getId() {
		return id;
	}
	public int getTrashed_by() {
		return trashed_by;
	}
	public void setTrashed_by(int trashed_by) {
		this.trashed_by = trashed_by;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_specie_mollusco() {
		return id_specie_mollusco;
	}
	public void setId_specie_mollusco(int id_specie_mollusco) {
		this.id_specie_mollusco = id_specie_mollusco;
	}
	public int getId_destinatario() {
		return id_destinatario;
	}
	public void setId_destinatario(int id_destinatario) {
		this.id_destinatario = id_destinatario;
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
	public String getIdentificativo_documento_trasporto() {
		return identificativo_documento_trasporto;
	}
	public void setIdentificativo_documento_trasporto(
			String identificativo_documento_trasporto) {
		this.identificativo_documento_trasporto = identificativo_documento_trasporto;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public Timestamp getData_invio() {
		return data_invio;
	}
	public void setData_invio(Timestamp data_invio) {
		this.data_invio = data_invio;
	}
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
	
	private static MerceInOut loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		MerceInOut ret = new MerceInOut();
	
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
    
	public static MerceInOut load(String id, Connection db )
	{

		MerceInOut			ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM merce_in_out WHERE id = ? and trashed_date IS NULL " );
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

	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		String sql = "INSERT INTO merce_in_out( ";
		
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
	
	public static ArrayList<MerceInOut> loadList(String id, Connection db )
	{

		ArrayList<MerceInOut>	ret		= new ArrayList<MerceInOut>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM merce_in_out WHERE id_soa = ? and trashed_date IS NULL ORDER BY id DESC" );
				stat.setInt( 1, iid );
				res		= stat.executeQuery();
				while( res.next() )
				{
					MerceInOut temp = loadResultSet( res );
					ret.add( temp );
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
	public void setData_invioString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			try
			{
				data_invio = new Timestamp( 
						(new SimpleDateFormat("dd/MM/yyyy")).parse( parameter.trim() ).getTime() );
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
	}
	public void setId_destinatarioString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			id_destinatario = Integer.parseInt( parameter.trim() );
		}
	}
	public void setId_specie_molluscoString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			id_specie_mollusco = Integer.parseInt( parameter.trim() );
		}
	}
	public static void delete(String id, int user_id, Timestamp now,
			Connection db)
	{
		PreparedStatement	stat	= null;
		try
		{
			int iid = Integer.parseInt( id );

			stat	= db.prepareStatement( "UPDATE merce_in_out SET trashed_date = ?, trashed_by = ? WHERE id = ? AND trashed_date IS NULL" );
			stat.setTimestamp( 1, now );
			stat.setInt( 2, user_id );
			stat.setInt( 3, iid );
			
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
	public void update(Connection db) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE merce_in_out SET ";
		
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
	
	public void setData_arrivoString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			try
			{
				data_arrivo = new Timestamp( 
						(new SimpleDateFormat("dd/MM/yyyy")).parse( parameter.trim() ).getTime() );
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setId_provenienzaString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			id_provenienza = Integer.parseInt( parameter.trim() );
		}
	}
	
	public void setId_soaString(String parameter)
	{
		if( (parameter != null) && (parameter.trim().length() > 0) )
		{
			id_soa = Integer.parseInt( parameter.trim() );
		}
	}
	
}
