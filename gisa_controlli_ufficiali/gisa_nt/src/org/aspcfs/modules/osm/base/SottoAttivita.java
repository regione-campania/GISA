/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.osm.base;

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

import com.darkhorseventures.framework.beans.GenericBean;

public class SottoAttivita extends GenericBean
{

	private static final long serialVersionUID = -906599187340806298L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;

	private int id					= -1;
	private int id_stabilimento		= -1;
	private int codice_sezione		= -1;
	private int codice_impianto		= -1;
	private int stato_attivita		= -1;
	private int imballata			= -1;
	private int tipo_autorizzazione	= -1;
	
	private String attivita;
	private String descrizione_stato_attivita;
	
	private Timestamp data_inizio_attivita;
	private Timestamp data_fine_attivita;
	
	private boolean riti_religiosi = false;
	
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

	public int getId_stabilimento() {
		return id_stabilimento;
	}

	public void setId_stabilimento(int id_stabilimento) {
		this.id_stabilimento = id_stabilimento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodice_sezione() {
		return codice_sezione;
	}

	public void setCodice_sezione(int codice_sezione) {
		this.codice_sezione = codice_sezione;
	}

	public int getCodice_impianto() {
		return codice_impianto;
	}

	public void setCodice_impianto(int codice_impianto) {
		this.codice_impianto = codice_impianto;
	}

	public int getStato_attivita() {
		return stato_attivita;
	}

	public void setStato_attivita(int stato_attivita) {
		this.stato_attivita = stato_attivita;
	}

	public int getImballata() {
		return imballata;
	}

	public void setImballata(int imballata) {
		this.imballata = imballata;
	}

	public int getTipo_autorizzazione() {
		return tipo_autorizzazione;
	}

	public void setTipo_autorizzazione(int tipo_autorizzazione) {
		this.tipo_autorizzazione = tipo_autorizzazione;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	public String getDescrizione_stato_attivita() {
		return descrizione_stato_attivita;
	}

	public void setDescrizione_stato_attivita(String descrizione_stato_attivita) {
		this.descrizione_stato_attivita = descrizione_stato_attivita;
	}

	public Timestamp getData_inizio_attivita() {
		return data_inizio_attivita;
	}

	public void setData_inizio_attivita(Timestamp data_inizio_attivita) {
		this.data_inizio_attivita = data_inizio_attivita;
	}

	public Timestamp getData_fine_attivita() {
		return data_fine_attivita;
	}

	public void setData_fine_attivita(Timestamp data_fine_attivita) {
		this.data_fine_attivita = data_fine_attivita;
	}

	public boolean isRiti_religiosi() {
		return riti_religiosi;
	}

	public void setRiti_religiosi(boolean riti_religiosi) {
		this.riti_religiosi = riti_religiosi;
	}
	
	public static ArrayList<SottoAttivita> loadByStabilimento( int id_stabilimento, Connection db )
	{
		ArrayList<SottoAttivita>	ret		= new ArrayList<SottoAttivita>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM stabilimenti_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_stabilimento = ?" );
			stat.setInt( 1, id_stabilimento );
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
		String sql = "INSERT INTO stabilimenti_sottoattivita( ";
		
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

	private static SottoAttivita loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		SottoAttivita ret = new SottoAttivita();
	
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

	public void setData_inizio_attivitaString(String dataInizioAttivita)
	{
		
	}

	public void setData_fine_attivitaString(String dataFineAttivita)
	{
		
	}

	public SottoAttivita alreadyExist(Connection db)
	{
		SottoAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM stabilimenti_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_stabilimento = ? AND codice_impianto = ? AND attivita = ? " );
			stat.setInt( 1, id_stabilimento );
			stat.setInt( 2, codice_impianto );
			stat.setString( 3, attivita );
	
			
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
		
		return ret;
	}
	
	
	

	
	public ArrayList<SottoAttivita> getSottoAttivitaStabilimento(Connection db,int id_stabilimento)
	{
		SottoAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		ArrayList<SottoAttivita> lista=new ArrayList<SottoAttivita>();
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM stabilimenti_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_stabilimento = ?" );
			stat.setInt( 1, id_stabilimento );
		
			
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
		String sql = "UPDATE stabilimenti_sottoattivita SET ";
		
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

	
}
