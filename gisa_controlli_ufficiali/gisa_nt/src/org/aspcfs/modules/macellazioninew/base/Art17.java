/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninew.base;

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
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.darkhorseventures.framework.beans.GenericBean;

public class Art17 extends GenericBean
{
	private static final long serialVersionUID = -8366550979572747502L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_macello;
	private int id_esercente	= -1;
	private String nome_esercente = "";
	private int anno;
	private int progressivo;
	private Timestamp data_modello;

	private Timestamp data_prima_generazione;
	private Timestamp data_ultima_generazione;
	
	private int utente_prima_generazione;
	private int utente_ultima_generazione;
	private int num_capi_prima_generazione;
	private int num_capi_ultima_generazione;
	private int num_generazioni = 0;

	private	int			trashed_by;
	private Timestamp	trashed_date;
	
	
	public int getTrashed_by() {
		return trashed_by;
	}

	public void setTrashed_by(int trashed_by) {
		this.trashed_by = trashed_by;
	}

	public int getId_macello() {
		return id_macello;
	}

	public void setId_macello(int id_macello) {
		this.id_macello = id_macello;
	}

	public int getId_esercente() {
		return id_esercente;
	}

	public void setId_esercente(int id_esercente) {
		this.id_esercente = id_esercente;
	}

	public String getNome_esercente() {
		return nome_esercente;
	}

	public void setNome_esercente(String nome_esercente) {
		this.nome_esercente = nome_esercente;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public Timestamp getData_modello() {
		return data_modello;
	}

	public void setData_modello(Timestamp data_modello) {
		this.data_modello = data_modello;
	}

	public Timestamp getData_prima_generazione() {
		return data_prima_generazione;
	}

	public void setData_prima_generazione(Timestamp data_prima_generazione) {
		this.data_prima_generazione = data_prima_generazione;
	}

	public Timestamp getData_ultima_generazione() {
		return data_ultima_generazione;
	}

	public void setData_ultima_generazione(Timestamp data_ultima_generazione) {
		this.data_ultima_generazione = data_ultima_generazione;
	}

	public int getUtente_prima_generazione() {
		return utente_prima_generazione;
	}

	public void setUtente_prima_generazione(int utente_prima_generazione) {
		this.utente_prima_generazione = utente_prima_generazione;
	}

	public int getUtente_ultima_generazione() {
		return utente_ultima_generazione;
	}

	public void setUtente_ultima_generazione(int utente_ultima_generazione) {
		this.utente_ultima_generazione = utente_ultima_generazione;
	}

	public int getNum_capi_prima_generazione() {
		return num_capi_prima_generazione;
	}

	public void setNum_capi_prima_generazione(int num_capi_prima_generazione) {
		this.num_capi_prima_generazione = num_capi_prima_generazione;
	}

	public int getNum_capi_ultima_generazione() {
		return num_capi_ultima_generazione;
	}

	public void setNum_capi_ultima_generazione(int num_capi_ultima_generazione) {
		this.num_capi_ultima_generazione = num_capi_ultima_generazione;
	}

	public int getNum_generazioni() {
		return num_generazioni;
	}

	public void setNum_generazioni(int num_generazioni) {
		this.num_generazioni = num_generazioni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}

	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		calcolaCodiceIdentificativo( db );
		
		String sql = "INSERT INTO m_art17( ";
		
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
    
    /**
     * Controlla se per il dato macello, il dato esercente o la data impresa, per una specifica data e' stato gia' generato un modello 17.
     * @param id_macello
     * @param id_esercent -1 se si fa riferimento ad un'impresa
     * @param data_modello
     * @param db
     * @return L'oggetto corrispondente o null se non presente
     */
	public static Art17 find( int id_macello, int id_esercente, String nomeEsercente, Timestamp data_modello,  Connection db )
	{

		Art17				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM m_art17 WHERE " +
					"id_macello = ? and " +
					"id_esercente = ? and " + (id_esercente == -999 ? "nome_esercente = ? and " : "") +
					"data_modello = ? and " +
					"trashed_date IS NULL" );
			
			int i = 1;
			stat.setInt( i++, id_macello );
			stat.setInt( i++, id_esercente );
			if(id_esercente == -999){
				stat.setString( i++, nomeEsercente );
			}
			stat.setTimestamp( i++, data_modello );
			res = stat.executeQuery();
			
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
		
		return ret;
	
	}
	
	/**
	 * Per i nuovi modelli Art17 calcola ed associa al bean i valori "anno" e "progressivo"
	 * @throws SQLException 
	 */
	private void calcolaCodiceIdentificativo( Connection db ) throws SQLException
	{
		int anno_corrente = Integer.parseInt( (new SimpleDateFormat("yyyy")).format( new java.util.Date() ) );
		String sql = "select coalesce( max( progressivo ), 0 ) + 1 as num from m_art17 where " +
				"id_macello = ? and anno = ?";
		PreparedStatement stat = db.prepareStatement( sql );
		stat.setInt( 1, id_macello );
		stat.setInt( 2, anno_corrente );
		ResultSet res = stat.executeQuery();
		
		if( res.next() )
		{
			this.progressivo = res.getInt( "num" );
			this.anno = anno_corrente;
		}
		
		stat.close();
	}
    
	public static Art17 load( String id, Connection db )
	{

		Art17				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM m_art17 WHERE id = ? and trashed_date IS NULL" );
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
	
	private static Art17 loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Art17 ret = new Art17();
	
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

	public void update(Connection db)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE m_art17 SET ";
		
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
					"UPDATE m_art17 SET trashed_date = CURRENT_TIMESTAMP, trashed_by = ? " +
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
	
	@Override
	public String toString()
	{
		return anno + "/" + progressivo;
	}

}
