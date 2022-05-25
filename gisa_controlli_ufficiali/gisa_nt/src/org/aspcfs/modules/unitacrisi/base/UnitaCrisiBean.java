/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.unitacrisi.base;

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
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import com.darkhorseventures.framework.beans.GenericBean;

public class UnitaCrisiBean extends GenericBean {
	private static final long serialVersionUID = -2134115954405464724L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int 	id;
    private int 	id_ambito;
	private String 	responsabile;
	private String 	mail;
	private String 	telefono;
	private String 	fax;

	// Campi relativi al record (data memorizzazione, modifica, etc.)
	private Timestamp	entered;
	private int			entered_by;
	private Timestamp	modified;
	private int			modified_by;
	private Timestamp	trashed_date;
	private int idAsl;
	// Eventuali campi utili ma non presenti nella tabella "unita_crisi"
	private String 		ambito_stringa;									// Rel. alla id_ambito
	private int level = -1;
	
	
	/* ************************************************** Metodi utility (loadResultSet, parseType) ************************************************ */
	private static UnitaCrisiBean loadResultSet( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		UnitaCrisiBean ret = new UnitaCrisiBean();
	
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
	/* **********************************************Fine metodi utility (loadResultSet, parseType) ************************************************ */
	
	/* *************************************************** Metodi Load, Store, Update, Delete ****************************************************** */
	public static ArrayList<UnitaCrisiBean> load_tutte( Connection db )
	{
		ArrayList<UnitaCrisiBean>	ret	= new ArrayList<UnitaCrisiBean>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		
		
		String sql ="SELECT uc.*, " +
						   "uc_ambito.description as ambito_stringa,uc_ambito.level as idAsl " +
					"FROM unita_crisi uc " +
					"LEFT JOIN lookup_ambito_unita_di_crisi uc_ambito ON uc.id_ambito = uc_ambito.code " +
					"where uc.trashed_date is null " +
					"ORDER BY uc_ambito.code, uc.level asc, uc.modified desc ";
		
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
	
	
	public static ArrayList<UnitaCrisiBean> load_tutte()
	{
		Connection db = null;
		ArrayList<UnitaCrisiBean>	ret	= new ArrayList<UnitaCrisiBean>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		
		
		String sql ="SELECT uc.*, " +
						   "uc_ambito.description as ambito_stringa,uc_ambito.level as idAsl " +
					"FROM unita_crisi uc " +
					"LEFT JOIN lookup_ambito_unita_di_crisi uc_ambito ON uc.id_ambito = uc_ambito.code " +
					"where uc.trashed_date is null " +
					"ORDER BY uc_ambito.code, uc.responsabile ";
		
		try
		{
			db = GestoreConnessioni.getConnection();
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
				GestoreConnessioni.freeConnection(db);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static UnitaCrisiBean load(String id, Connection db )
	{

		UnitaCrisiBean			ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				
				String sql ="SELECT uc.*, " +
				   					"uc_ambito.description as ambito_stringa,uc_ambito.level as idAsl " +
				   			"FROM unita_crisi uc " +
				   			"LEFT JOIN lookup_ambito_unita_di_crisi uc_ambito ON uc.id_ambito = uc_ambito.code " + 
							"WHERE uc.id=? " +
								"AND uc.trashed_date is null ";
				
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
	
	public static UnitaCrisiBean load( String id )
	{
		Connection 			db		= null;
		UnitaCrisiBean			ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				db = GestoreConnessioni.getConnection();
				int iid = Integer.parseInt( id );
				
				String sql ="SELECT uc.*, " +
									"uc_ambito.description as ambito_stringa,uc_ambito.level as idAsl " +
							"FROM unita_crisi uc " +
							"LEFT JOIN lookup_ambito_unita_di_crisi uc_ambito ON uc.id_ambito = uc_ambito.code " + 
							"WHERE uc.id=? " +
								"AND uc.trashed_date is null ";
				
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
					GestoreConnessioni.freeConnection(db);
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
	
	public static void delete( int id, int user_id, Connection db )
	{
		
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
						"UPDATE unita_crisi " +
						"SET modified = CURRENT_TIMESTAMP, " +
							"trashed_date = CURRENT_TIMESTAMP, " +
							"modified_by = ? " +
						"WHERE id = ? AND trashed_date IS NULL");

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
	
	public void update(Connection db) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE unita_crisi SET ";
		
		this.modified = new Timestamp( System.currentTimeMillis() );
		
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
	            if( 
	            	!field.equalsIgnoreCase( "id" ) &&	
	            	!field.equalsIgnoreCase( "idAsl" ) &&
	            	!field.equalsIgnoreCase( "ambito_stringa" ) &&					// Inserire qui i campi del bean non presenti nella tabella...
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
	
	public UnitaCrisiBean store( Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
				
		this.entered	= new Timestamp( System.currentTimeMillis() );
		this.modified	= this.entered;
		
		String sql = "INSERT INTO unita_crisi( ";
		
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
	            if( 
	            	!field.equalsIgnoreCase( "id" ) &&	
	            	!field.equalsIgnoreCase( "idAsl" ) &&	
	            	!field.equalsIgnoreCase( "ambito_stringa" ) &&			// Inserire qui i campi del bean non presenti nella tabella...
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
	    return UnitaCrisiBean.load( "" + DatabaseUtils.getCurrVal( db, "unita_crisi_id_seq", -1 ), db );
	    
	}
	
	/* *************************************************** Metodi Load, Store, Update, Delete ****************************************************** */
	
	/* *********************************************************** getter & setter bean ************************************************************ */
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId_ambito() {
		return id_ambito;
	}

	public void setId_ambito(int idAmbito) {
		id_ambito = idAmbito;
	}

	public String getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	// GETTER e SETTER PER ATTRIBUTI NON IN TABELLA.....
	public String getAmbito_stringa() {
		return ambito_stringa;
	}

	public void setAmbito_stringa(String ambitoStringa) {
		ambito_stringa = ambitoStringa;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	

	// FINE GETTER e SETTER PER ATTRIBUTI NON IN TABELLA.....
	/* ************************************Fine getter & setter bean **************************************** */


}
