/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.oia.base;

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
import com.darkhorseventures.framework.beans.GenericBean;

public class OiaTipologiaCompetenzaNodo extends GenericBean {

	private static final long serialVersionUID = 6660496295178867740L;

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private static final int 	DISCRIMINANTE_COMUNI 					= 1;
	private static final int 	DISCRIMINANTE_MACROCATEGORIE			= 2;
	private static final String TABELLA_DISCRIMINANTE_COMUNI			= "v_comuni";
	private static final String TABELLA_DISCRIMINANTE_MACROCATEGORIE	= "nodo_macrocategorie";
	
    private int 	id;
	private int 	id_nodo_oia;
	private int 	id_lookup_tipologia_competenza;
	private int 	discriminante;

	// Campi relativi al record (data memorizzazione, modifica, etc.)
	private Timestamp	entered;
	private int			entered_by;
	private Timestamp	modified;
	private int			modified_by;
	private Timestamp	trashed_date;
	
	// Eventuali campi utili ma non presenti nella tabella "oia_nodo"
	private String		tipologia_competenza_stringa;
	
	/* ************************************************** Metodi utility (loadResultSet, parseType) ************************************************ */
	private static OiaTipologiaCompetenzaNodo loadResultSet( ResultSet res, Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		OiaTipologiaCompetenzaNodo ret = new OiaTipologiaCompetenzaNodo();
	
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
	public static int getDiscriminate_by_id_nodo_oia(String id_nodo_oia, Connection db )
	{
		int 				ret	 = -1;
		PreparedStatement	stat = null;
		ResultSet			res	 = null;
		
		if( (id_nodo_oia != null) && (id_nodo_oia.trim().length() > 0) )
		{
			int iid = Integer.parseInt( id_nodo_oia );
			try
			{
				String query_discriminante = "SELECT discriminante " +
										 	 "FROM oia_tipologia_competenza_nodo " +
										     "WHERE id_nodo_oia = ? ";

				PreparedStatement ps_discriminante = db.prepareStatement(query_discriminante);
				int indice = 1;
				ps_discriminante.setInt(indice++, iid);
				ResultSet res_discriminante = ps_discriminante.executeQuery();
				
				while( res_discriminante.next() )
				{
					ret  = res_discriminante.getInt("discriminante");  //	if (res_discriminante.getString("discriminante").equalsIgnoreCase("nodo_macrocategorie") ) {
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
	
	public static int getDiscriminate_by_id(String id, Connection db )
	{
		int 				ret	 = -1;
		PreparedStatement	stat = null;
		ResultSet			res	 = null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			int iid = Integer.parseInt( id );
			try
			{
				String query_discriminante = "SELECT discriminante " +
										 	 "FROM oia_tipologia_competenza_nodo " +
										     "WHERE id = ? ";
	
				PreparedStatement ps_discriminante = db.prepareStatement(query_discriminante);
				int indice = 1;
				ps_discriminante.setInt(indice++, iid);
				ResultSet res_discriminante = ps_discriminante.executeQuery();
				
				if (res_discriminante.next())
					ret  = res_discriminante.getInt("discriminante");  //	if (res_discriminante.getString("discriminante").equalsIgnoreCase("nodo_macrocategorie") ) {
				
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
	
	public static ArrayList<OiaTipologiaCompetenzaNodo> load_by_id_nodo_oia( String id_nodo_oia, Connection db )
	{
		ArrayList<OiaTipologiaCompetenzaNodo>	ret	= new ArrayList<OiaTipologiaCompetenzaNodo>();
		PreparedStatement						stat= null;
		ResultSet								res	= null;
		
		id_nodo_oia	= (id_nodo_oia == null)	? ("") : (id_nodo_oia.trim());
		int discriminante = getDiscriminate_by_id_nodo_oia( id_nodo_oia, db );
		
		String tabella_discriminante ="";
		String sql ="";
				
		switch (discriminante) {
			case DISCRIMINANTE_COMUNI:
				tabella_discriminante = TABELLA_DISCRIMINANTE_COMUNI;
				break;
			case DISCRIMINANTE_MACROCATEGORIE:
				tabella_discriminante = TABELLA_DISCRIMINANTE_MACROCATEGORIE;
				break;
		}
		
		sql ="SELECT oia_tcn.*, d.descrizione as tipologia_competenza_stringa " +
		 	 "FROM oia_tipologia_competenza_nodo oia_tcn " +
			 "JOIN " + tabella_discriminante + " d on oia_tcn.id_lookup_tipologia_competenza = d.id::integer " +
			 "WHERE oia_tcn.id_nodo_oia=? ";

		try
		{
			stat = db.prepareStatement( sql );
			stat.setInt( 1, Integer.parseInt( id_nodo_oia ) );
			
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
	
	
	public static OiaTipologiaCompetenzaNodo load(String id, Connection db )
	{

		OiaTipologiaCompetenzaNodo	ret		= null;
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				
				int discriminante = getDiscriminate_by_id( id, db );
				String tabella_discriminante ="";
				
				switch (discriminante) {
						case DISCRIMINANTE_COMUNI:
							tabella_discriminante = TABELLA_DISCRIMINANTE_COMUNI;
							break;
						case DISCRIMINANTE_MACROCATEGORIE:
							tabella_discriminante = TABELLA_DISCRIMINANTE_MACROCATEGORIE;
							break;
				}

				String sql ="SELECT oia_tcn.*, d.descrizione as tipologia_competenza_stringa " +
							"FROM oia_tipologia_competenza_nodo oia_tcn " +
							"JOIN " + tabella_discriminante + " d on oia_tcn.id_lookup_tipologia_competenza = d.id::integer " +
							"WHERE oia_tcn.id=? ";
				
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
	
	public static void delete( int id, Connection db )
	{
		PreparedStatement	stat	= null;
		String sql = "DELETE FROM oia_tipologia_competenza_nodo " +
					 "WHERE id=?";
		
		try
		{
			stat	= db.prepareStatement( sql );
			stat.setInt( 1, id );
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
	
	public static void delete_by_id_nodo_oia( int id_nodo_oia, Connection db )
	{
		PreparedStatement	stat	= null;
		String sql = "DELETE FROM oia_tipologia_competenza_nodo " +
					 "WHERE id_nodo_oia=?";
		
		try
		{
			stat	= db.prepareStatement( sql );
			stat.setInt( 1, id_nodo_oia );
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
	
	public static void delete_logic_by_id_nodo_oia( int id_nodo_oia, int user_id, Connection db)
	{
		PreparedStatement	stat	= null;
		String sql = "UPDATE oia_tipologia_competenza_nodo " +
					 "SET modified = CURRENT_TIMESTAMP, " +
						 "trashed_date = CURRENT_TIMESTAMP, " +
						 "modified_by = ? " +
					 "WHERE id_nodo_oia = ? AND trashed_date IS NULL" ;
		
		try
		{
			stat	= db.prepareStatement( sql );
			stat.setInt( 1, user_id );
			stat.setInt( 2, id_nodo_oia );
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
		String sql = "UPDATE oia_tipologia_competenza_nodo SET ";
		
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
	            	!field.equalsIgnoreCase( "tipologia_competenza_stringa" ) &&					// Inserire qui i campi del bean non presenti nella tabella...
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
	
	public OiaTipologiaCompetenzaNodo store( Connection db ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		this.entered	= new Timestamp( System.currentTimeMillis() );
		this.modified	= this.entered;
				
		String sql = "INSERT INTO oia_tipologia_competenza_nodo( ";
		
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
	            	!field.equalsIgnoreCase( "tipologia_competenza_stringa" ) &&			// Inserire qui i campi del bean non presenti nella tabella...
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
	    return OiaTipologiaCompetenzaNodo.load( "" + DatabaseUtils.getCurrVal( db, "oia_tipologia_competenza_nodo_id_seq", -1 ), db );
	    
	}
	
	/* ***********************************************Fine metodi Load, Store, Update, Delete ****************************************************** */
	
	
	/* *********************************************************** getter & setter bean ************************************************************ */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_nodo_oia() {
		return id_nodo_oia;
	}
	public void setId_nodo_oia(int idNodoOia) {
		id_nodo_oia = idNodoOia;
	}
	public int getId_lookup_tipologia_competenza() {
		return id_lookup_tipologia_competenza;
	}
	public void setId_lookup_tipologia_competenza(int idLookupTipologiaCompetenza) {
		id_lookup_tipologia_competenza = idLookupTipologiaCompetenza;
	}
	public int getDiscriminante() {
		return discriminante;
	}
	public void setDiscriminante(int discriminante) {
		this.discriminante = discriminante;
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
	public String getTipologia_competenza_stringa() {
		return tipologia_competenza_stringa;
	}

	public void setTipologia_competenza_stringa(String tipologiaCompetenzaStringa) {
		tipologia_competenza_stringa = tipologiaCompetenzaStringa;
	}
	// FINE GETTER e SETTER PER ATTRIBUTI NON IN TABELLA.....
	
	/* ************************************Fine getter & setter bean **************************************** */


}
