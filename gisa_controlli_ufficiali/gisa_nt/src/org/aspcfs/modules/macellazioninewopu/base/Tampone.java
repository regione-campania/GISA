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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo;
import org.aspcfs.utils.DatabaseUtils;

import sun.security.action.GetLongAction;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Tampone extends GenericBean
{
	
	private static final long serialVersionUID = 1L;
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;


	
	private int			entered_by;
	private int			modified_by;
	private Timestamp	entered;
	private Timestamp	modified;
	private Timestamp	trashed_date;
	
	private ArrayList<TipoRicerca> tipo_ricerca = new ArrayList<TipoRicerca>() ;
	private int id_tipo_carcassa ;
	private boolean distruttivo ;

	
	
	private int id_macello  ;
	private Timestamp data_macellazione	; 
	private int sessione_macellazione ;
	private int piano_monitoraggio ;
	
	
	
	
	
	
	public int getPiano_monitoraggio() {
		return piano_monitoraggio;
	}

	public void setPiano_monitoraggio(int piano_monitoraggio) {
		this.piano_monitoraggio = piano_monitoraggio;
	}

	
	public ArrayList<TipoRicerca> getTipo_ricerca() {
		return tipo_ricerca;
	}

	public void setTipo_ricerca(ArrayList<TipoRicerca> tipo_ricerca) {
		this.tipo_ricerca = tipo_ricerca;
	}

	public int getId_tipo_carcassa() {
		return id_tipo_carcassa;
	}

	public void setId_tipo_carcassa(int id_tipo_carcassa) {
		this.id_tipo_carcassa = id_tipo_carcassa;
	}

	public boolean isDistruttivo() {
		return distruttivo;
	}

	public void setDistruttivo(boolean distruttivo) {
		this.distruttivo = distruttivo;
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

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
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

	
	public int getId_macello() {
		return id_macello;
	}

	public void setId_macello(int id_macello) {
		this.id_macello = id_macello;
	}

	public Timestamp getData_macellazione() {
		return data_macellazione;
	}

	public void setData_macellazione(Timestamp data_macellazione) {
		this.data_macellazione = data_macellazione;
	}

	public int getSessione_macellazione() {
		return sessione_macellazione;
	}

	public void setSessione_macellazione(int sessione_macellazione) {
		this.sessione_macellazione = sessione_macellazione;
	}

	
	public void updateTampone(Connection db) throws SQLException
	{
		String sql = "update m_vpm_tamponi  set id_tipo_carcassa = ? , distruttivo = ? where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);

		pst.setInt(1, this.getId_tipo_carcassa());
		pst.setBoolean(2, this.isDistruttivo());
		pst.setInt(3, this.getId());
		pst.execute();
		insertTipoRicercaTampone(db);
		
	}
	

	public void insertTipoRicercaTampone(Connection db) throws SQLException
	{
		
		db.prepareStatement("delete from m_vpm_tamponi_analiti where id_tampone="+this.getId()).execute();
		String sql = "insert into m_vpm_tamponi_analiti (id_tampone,id_tipo_ricerca) values (?,?)";
		PreparedStatement pst = db.prepareStatement(sql);
		
		for(TipoRicerca ricerca : tipo_ricerca)
		{
		pst.setInt(1, this.getId());
		pst.setInt(2, ricerca.getId());
		pst.execute();
		}
	}
	
	
	
	
	
	public void cancella_tampone_capo(org.aspcfs.modules.macellazioninewopu.base.GenericBean b, Connection db, ConfigTipo configTipo) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("update m_vpm_capi_tamponi set trashed_date = current_date,trashed_by =? where "+configTipo.getNomeCampoRifTabellaTamponi()+" = ?");
		pst.setInt(1, b.getModified_by());
		pst.setInt(2, b.getId());
		pst.execute();
	}
	
	public void cancella_tampone_seduta(org.aspcfs.modules.macellazioninewopu.base.PartitaSeduta p, Connection db, ConfigTipo configTipo) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("update m_vpm_capi_tamponi set trashed_date = current_date,trashed_by =? where id_m_seduta = ?");
		pst.setInt(1, p.getModified_by());
		pst.setInt(2, p.getId());
		pst.execute();
	}
	
	public void associa_tampone_capo(org.aspcfs.modules.macellazioninewopu.base.GenericBean c, Connection db,ConfigTipo configTipo) throws SQLException
	{
		PreparedStatement verifica = db.prepareStatement("select id from m_vpm_capi_tamponi where " + configTipo.getNomeCampoRifTabellaTamponi() + " = ? and trashed_Date is null");
		verifica.setInt(1, c.getId());
		ResultSet rs1 = verifica.executeQuery();
		
		if (!rs1.next())
		{
			PreparedStatement pst = db.prepareStatement("insert into m_vpm_capi_tamponi (id_m_vpm_tamponi, " + configTipo.getNomeCampoRifTabellaTamponi() + " ,entered,enteredby) values (?,?,current_timestamp,?)");
			pst.setInt(1, this.getId());
			pst.setInt(2, c.getId());
			pst.setInt(3, c.getModified_by());
			pst.execute();
		}
	}
	
	public void associa_tampone_seduta(org.aspcfs.modules.macellazioninewopu.base.PartitaSeduta p, Connection db,ConfigTipo configTipo) throws SQLException
	{
		PreparedStatement verifica = db.prepareStatement("select id from m_vpm_capi_tamponi where id_m_seduta = ? and trashed_Date is null");
		verifica.setInt(1, p.getId());
		ResultSet rs1 = verifica.executeQuery();
		
		if (!rs1.next())
		{
			PreparedStatement pst = db.prepareStatement("insert into m_vpm_capi_tamponi (id_m_vpm_tamponi, id_m_seduta ,entered,enteredby) values (?,?,current_timestamp,?)");
			pst.setInt(1, this.getId());
			pst.setInt(2, p.getId());
			pst.setInt(3, p.getModified_by());
			pst.execute();
		}
	}
	
	public void associa_tampone_seduta(PartitaSeduta p, Connection db) throws SQLException
	{
		
		PreparedStatement verifica = db.prepareStatement("select id from m_vpm_capi_tamponi where id_m_seduta= ? and trashed_Date is null");
		verifica.setInt(1, p.getId());
		ResultSet rs1 = verifica.executeQuery();
		
		if (!rs1.next())
		{
			PreparedStatement pst = db.prepareStatement("insert into m_vpm_capi_tamponi (id_m_vpm_tamponi,id_m_seduta,entered,enteredby) values (?,?,current_timestamp,?)");
			pst.setInt(1, this.getId());
			pst.setInt(2, p.getId());
			pst.setInt(3, p.getModified_by());
			pst.execute();
		}
		
	}
	
	public void store( Connection db,ActionContext context )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		String sql = "INSERT INTO m_vpm_tamponi( ";
		
		setId(DatabaseUtils.getNextSeq(db,context, "m_vpm_tamponi","id"));
		
		

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
	            if( (!field.equalsIgnoreCase("tipo_ricerca") && (met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )) )
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
	    insertTipoRicercaTampone(db);
	   
	    
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
    
	public static Tampone load( Integer id, ConfigTipo configTipo, Connection db )
	{

		Tampone	ret		= new Tampone();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT m_vpm_tamponi.* " +
					"FROM m_vpm_tamponi left join m_vpm_capi_tamponi on m_vpm_tamponi.id = m_vpm_capi_tamponi.id_m_vpm_tamponi " +
					"WHERE " + configTipo.getNomeCampoRifTabellaTamponi() + " = ? and m_vpm_tamponi.trashed_date IS NULL and m_vpm_capi_tamponi.trashed_date is null " );
			stat.setInt( 1, id );
			res		= stat.executeQuery();
			
			if (res.next())
				 ret =loadResultSet( res ) ;
			loadTipoRicerca(db,ret);
			
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
	
	public static  void loadTipoRicerca (Connection db ,Tampone t) throws SQLException
	{
		ResultSet rs = null ;
		PreparedStatement pst = db.prepareStatement("select an.*,l.description as descrizione_ricerca from m_vpm_tamponi_analiti an join lookup_ricerca_tamponi_macelli l on l.code =an.id_tipo_ricerca where id_tampone = ?");
		pst.setInt(1, t.getId());
		rs = pst.executeQuery() ;
		while (rs.next())
		{
			TipoRicerca rr = new TipoRicerca();
			rr.setId(rs.getInt("id_tipo_ricerca"));
			rr.setDescrizione(rs.getString("descrizione_ricerca"));
			t.getTipo_ricerca().add(rr);
		}
		
	}
	
	public static Tampone load( int idMacello ,String dataMacellazione,int sedutaMacellazione, Connection db )
	{

		Tampone	ret		= new Tampone();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT m_vpm_tamponi.* " +
					"FROM m_vpm_tamponi left join m_vpm_capi_tamponi on m_vpm_tamponi.id = m_vpm_capi_tamponi.id_m_vpm_tamponi " +
					"WHERE id_macello = ? and  to_char(data_macellazione, 'dd/MM/yyyy') = ?  and sessione_macellazione = ? " +
					"and m_vpm_tamponi.trashed_date IS NULL  " );
			stat.setInt( 1, idMacello );
			stat.setString( 2,dataMacellazione );
			stat.setInt( 3, sedutaMacellazione );
			res		= stat.executeQuery();
			
			if (res.next())
				 ret =loadResultSet( res ) ;
			
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
	
	
	
    
	public static Tampone load(String id, Connection db )
	{

		Tampone				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM m_vpm_tamponi WHERE id = ? and trashed_date IS NULL" );
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
	
	private static Tampone loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Tampone ret = new Tampone();
	
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
	             if(! field.equals("tipo_ricerca") && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ))
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

	

	

}
