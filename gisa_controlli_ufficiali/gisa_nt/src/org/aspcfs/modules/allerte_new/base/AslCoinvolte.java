/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.base;

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
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class AslCoinvolte extends GenericBean
{
	
	
	private static final long serialVersionUID = 7620936339377012629L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	private int numCuEseguiti ;
	private String motivo_chiusura;
	private int numCuEseguiti_aperti;
	private boolean stato_allegatof = false;
	private Timestamp data_invio_allegato ;
	
	
	
	public Timestamp getData_invio_allegato() {
		return data_invio_allegato;
	}
	public void setData_invio_allegato(Timestamp data_invio_allegato) {
		this.data_invio_allegato = data_invio_allegato;
	}
	public boolean isStato_allegatof() {
		return stato_allegatof;
	}
	public void setStato_allegatof(boolean stato_allegatof) {
		this.stato_allegatof = stato_allegatof;
	}
	public int getNumCuEseguiti_aperti() {
		return numCuEseguiti_aperti;
	}
	public void setNumCuEseguiti_aperti(int numCuEseguiti_aperti) {
		this.numCuEseguiti_aperti = numCuEseguiti_aperti;
	}
	public String getMotivo_chiusura() {
		return motivo_chiusura;
	}
	public void setMotivo_chiusura(String motivo_chiusura) {
		this.motivo_chiusura = motivo_chiusura;
	}
	public int getNumCuEseguiti() {
		return numCuEseguiti;
	}
	public void setNumCuEseguiti(int numCuEseguiti) {
		this.numCuEseguiti = numCuEseguiti;
	}
	private String noteFuoriRegione = "";
	
	public String getNoteFuoriRegione() {
		return noteFuoriRegione;
	}
	public void setNoteFuoriRegione(String noteFuoriRegione) {
		this.noteFuoriRegione = noteFuoriRegione;
	}
	private int controlliUfficialiRegionaliPianificati=-1;
	public int getControlliUfficialiRegionaliPianificati() {
		return controlliUfficialiRegionaliPianificati;
	}
	public void setControlliUfficialiRegionaliPianificati(
			int controlliUfficialiRegionaliPianificati) {
		this.controlliUfficialiRegionaliPianificati = controlliUfficialiRegionaliPianificati;
	}
	private String motivazione="";
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	private int		id;
	private int		id_allerta;
	private int		id_ldd;
	private int		id_asl;
	private int		cu_pianificati    = -1;
	private int		cu_pianificati_da = -1;
	private int		cu_eseguiti       = 0;
	private String	note;
	private boolean	enabled		= true;
	private int		chiusa_da	= -1;
	private Timestamp data_chiusura;
	private boolean view_allegato_asl ;
	private boolean flag_ripianificazione ;
	
	
	
	
	public boolean getFlag_ripianificazione() {
		return flag_ripianificazione;
	}
	public void setFlag_ripianificazione(boolean flag_ripianificazione) {
		this.flag_ripianificazione = flag_ripianificazione;
	}
	public boolean isView_allegato_asl() {
		return view_allegato_asl;
	}
	public void setView_allegato_asl(boolean view_allegato_asl) {
		this.view_allegato_asl = view_allegato_asl;
	}
	public int getChiusa_da() {
		return chiusa_da;
	}
	public void setChiusa_da(int chiusa_da) {
		this.chiusa_da = chiusa_da;
	}
	public Timestamp getData_chiusura() {
		return data_chiusura;
	}
	public void setData_chiusura(Timestamp data_chiusura) {
		this.data_chiusura = data_chiusura;
	}
	public int getCu_pianificati_da() {
		return cu_pianificati_da;
	}
	public void setCu_pianificati_da(int cu_pianificati_da) {
		this.cu_pianificati_da = cu_pianificati_da;
	}
	public int getCu_eseguiti() {
		return cu_eseguiti;
	}
	public void setCu_eseguiti(int cu_eseguiti) {
		this.cu_eseguiti = cu_eseguiti;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_allerta() {
		return id_allerta;
	}
	public void setId_allerta(int id_allerta) {
		this.id_allerta = id_allerta;
	}
	public int getId_ldd() {
		return id_ldd;
	}
	public void setId_ldd(int id_ldd) {
		this.id_ldd = id_ldd;
	}
	public int getId_asl() {
		return id_asl;
	}
	public void setId_asl(int id_asl) {
		this.id_asl = id_asl;
	}
	public int getCu_pianificati() {
		return cu_pianificati;
	}
	public void setCu_pianificati(int cu_pianificati) {
		this.cu_pianificati = cu_pianificati;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "INSERT INTO allerte_asl_coinvolte( ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        if ( ! field.equals("view_allegato_asl") && ! field.equals("numCuEseguiti") &&!field.equalsIgnoreCase( "numCuEseguiti_aperti" ) )
	        {
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

	/*public static AslCoinvolte load(String id, Connection db,boolean contaCu )
	{
		
		
		AslCoinvolte		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM allerte_asl_coinvolte WHERE id = ? and enabled" );
				stat.setInt( 1, iid );
				
				res		= stat.executeQuery();
				
				if( res.next() )
				{
					ret = loadResultSet( res );
					
					if (contaCu==true)
					{
						ret.setCu_eseguiti(getNumeroCuEseguitiAllerta(ret.id_allerta,ret.id_asl,db));
						ret.setNumCuEseguiti_aperti(getNumeroCuEseguitiApertiAllerta(ret.id_allerta,ret.id_asl,db));
					}
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
	*/
	/**
	 * 			RITORNA IL NUMERO DI CONTROLLI UFFICIALI CHIUSI E NON CANCELLATI ESEGUITI PER L'ALLERTA IN INPUT 
	 * 			@return
	 */
	
	public static int getNumeroCuEseguitiAllerta(String codiceAllerta,int idAsl,Connection db)
	{
		
	
		int numeroCU = 0;
		try
		{
			PreparedStatement pst = db.prepareStatement("SELECT COUNT (ticketid) from ticket cu where cu.tipologia = 3 and cu.trashed_date is null and cu.closed is not null and cu.site_id = ? and cu.codice_allerta =? ");
			pst.setInt(1, idAsl)					;
			pst.setString(2, codiceAllerta)				;
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				numeroCU = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numeroCU;
		
		
		
	}
	
	public static int getNumeroCuEseguitiAllertaByIdLdd(String codiceAllerta, int idLdd, int idAsl,Connection db)
	{
		
	
		int numeroCU = 0;
		try
		{
			String sql = "SELECT COUNT (ticketid) from ticket cu where cu.tipologia = 3 and cu.trashed_date is null and cu.closed is not null and cu.site_id = ? and cu.codice_allerta =? ";
			if (idLdd>0)
			{
				sql+= " and cu.id_ldd = ? "; 
			}
			PreparedStatement pst = db.prepareStatement(sql);
			
			pst.setInt(1, idAsl)					;
			pst.setString(2, codiceAllerta)				;
			if (idLdd>0)
			{
				pst.setInt(3, idLdd)				; 
			}
			
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				numeroCU = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numeroCU;
		
		
		
	}
	
	public static int getNumeroCuEseguitiApertiAllerta(String codicEallerta,int idAsl,Connection db)
	{
		
		int numeroCU = 0;
		try
		{
			PreparedStatement pst = db.prepareStatement("SELECT COUNT (ticketid) from ticket cu where cu.tipologia = 3 and cu.trashed_date is null and cu.closed is null and cu.site_id = ? and cu.codice_allerta =?");
			pst.setInt(1, idAsl)					;
			pst.setString(2, codicEallerta)				;
			ResultSet rs =  pst.executeQuery();
			if(rs.next())
			{
				numeroCU = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numeroCU;
		
		
		
	}
	
	public static int getNumeroCuEseguitiApertiAllertaByIdLdd(String codicEallerta,int idLdd, int idAsl,Connection db)
	{
		
		int numeroCU = 0;
		try
		{
			String sql = "SELECT COUNT (ticketid) from ticket cu where cu.tipologia = 3 and cu.trashed_date is null and cu.closed is null and cu.site_id = ? and cu.codice_allerta =? ";
			
			if (idLdd>0)
			{
				sql+= " and cu.id_ldd = ? "; 
			}
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl)					;
			pst.setString(2, codicEallerta)				;
			if (idLdd>0)
			{
				pst.setInt(3, idLdd)					;
			}
			ResultSet rs =  pst.executeQuery();
			if(rs.next())
			{
				numeroCU = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numeroCU;
		
		
		
	}
	
	

	private static AslCoinvolte loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		AslCoinvolte ret = new AslCoinvolte();
	
		Field[]	f = ret.getClass().getDeclaredFields();
		Method[]	m = ret.getClass().getMethods();
		for( int i = 0; i < f.length; i++ )
		{
			Method getter	= null;
	    	 Method setter	= null;
	    	 Field	campo	= f[i];
	         String field = f[i].getName();
	         if(!field.equals("view_allegato_asl") && !field.equals("numCuEseguiti")  &&!field.equalsIgnoreCase( "numCuEseguiti_aperti" ))
	         {  
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
	         }
	
		return ret;
	}

	public static void delete( String id, int user_id, Timestamp now, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			int iid = Integer.parseInt( id );
	
			stat	= db.prepareStatement( "UPDATE allerte_asl_coinvolte SET enabled = FALSE WHERE id = ? " );
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
		String sql = "UPDATE allerte_asl_coinvolte SET ";
		
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
	            if( (!field.equalsIgnoreCase( "numcueseguiti" ))&&!field.equalsIgnoreCase( "numCuEseguiti_aperti" )  && (!field.equalsIgnoreCase( "view_allegato_asl" )) && !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field + " = ?";
	            }
	        }
	        
	    }
	    
	    sql += " WHERE id = ? and enabled = true ";
	
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
	
	/*public static Hashtable<String, AslCoinvolte> getAslConvolte( int id_allerta,boolean contaCu, Connection db )
	{
		Hashtable<String, AslCoinvolte>	ret		= new Hashtable<String, AslCoinvolte>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{

			LookupList list_asl = new LookupList(db,"lookup_site_id");
			
			
			stat = db.prepareStatement("SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? and enabled");
			
			
			stat.setInt( 1, id_allerta );
			res		= stat.executeQuery();
			while( res.next() )
			{
				
				AslCoinvolte asl =  loadResultSet( res );
				
				if (contaCu==true)
				{
					asl.setCu_eseguiti(getNumeroCuEseguitiAllerta(asl.getId_allerta(),asl.getId_asl(),db));
					asl.setNumCuEseguiti_aperti(getNumeroCuEseguitiApertiAllerta(asl.getId_allerta(),asl.getId_asl(),db));
				}
				//AslCoinvolte asl = AslCoinvolte.load( res.getInt( "id" ) + "", db,contaCu ) ;
				ret.put( list_asl.getSelectedValue(asl.getId_asl()) ,asl );
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
	}*/
	
	
	public static Hashtable<String, AslCoinvolte> getAslConvolte( int id,String codice_allerta,boolean contaCu, Connection db )
	{
		Hashtable<String, AslCoinvolte>	ret		= new Hashtable<String, AslCoinvolte>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{

			LookupList list_asl = new LookupList(db,"lookup_site_id");
			/*stat	= db.prepareStatement( 
					"SELECT ac.id, ac.id_asl, lsi.description FROM allerte_asl_coinvolte ac, lookup_site_id lsi " +
					"WHERE ac.id_asl = lsi.code AND id_allerta = ? and ac.enabled" );*/
			
			stat = db.prepareStatement("SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? and enabled");
			
			
			stat.setInt( 1, id );
			res		= stat.executeQuery();
			while( res.next() )
			{
				
				AslCoinvolte asl =  loadResultSet( res );
				
				if (contaCu==true)
				{
					asl.setCu_eseguiti(getNumeroCuEseguitiAllerta(codice_allerta,asl.getId_asl(),db));
					asl.setNumCuEseguiti_aperti(getNumeroCuEseguitiApertiAllerta(codice_allerta,asl.getId_asl(),db));
				}
				//AslCoinvolte asl = AslCoinvolte.load( res.getInt( "id" ) + "", db,contaCu ) ;
				ret.put( list_asl.getSelectedValue(asl.getId_asl()) ,asl );
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
	
	public static Hashtable<String, AslCoinvolte> getAslConvolteByIdLdd( int id_ldd,String codice_allerta,int idAllerta, boolean contaCu, Connection db )
	{
		Hashtable<String, AslCoinvolte>	ret		= new Hashtable<String, AslCoinvolte>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{

			LookupList list_asl = new LookupList(db,"lookup_site_id");
			/*stat	= db.prepareStatement( 
					"SELECT ac.id, ac.id_asl, lsi.description FROM allerte_asl_coinvolte ac, lookup_site_id lsi " +
					"WHERE ac.id_asl = lsi.code AND id_allerta = ? and ac.enabled" );*/
			
			stat = db.prepareStatement("SELECT * FROM allerte_asl_coinvolte WHERE id_ldd = ? and id_allerta = ? and enabled");
			
			
			stat.setInt( 1, id_ldd );
			stat.setInt( 2, idAllerta );
			res		= stat.executeQuery();
			while( res.next() )
			{
				
				AslCoinvolte asl =  loadResultSet( res );
				
				if (contaCu==true)
				{
					asl.setCu_eseguiti(getNumeroCuEseguitiAllertaByIdLdd(codice_allerta,id_ldd,asl.getId_asl(),db));
					asl.setNumCuEseguiti_aperti(getNumeroCuEseguitiApertiAllertaByIdLdd(codice_allerta,id_ldd, asl.getId_asl(),db));
				}
				//AslCoinvolte asl = AslCoinvolte.load( res.getInt( "id" ) + "", db,contaCu ) ;
				ret.put( list_asl.getSelectedValue(asl.getId_asl()) ,asl );
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
	
	public static void chiusura_pregresso( String id_allerta, int idUser,Timestamp time,  Connection db )
	{
		AslCoinvolte		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? AND enabled" );
			stat.setInt(1,Integer.parseInt( id_allerta));
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				
				AslCoinvolte ac = loadResultSet(rs);
				ac.setData_chiusura( time );
				ac.setChiusa_da( idUser );
				ac.update( db );
				
			}
			
			
			
		}catch (Exception e)
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
	
	public static void chiusura_ufficio( String id_allerta, int idUser,  Connection db,int id_history_allerta,Ticket allerta )
	{
		AslCoinvolte		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? AND enabled" );
			stat.setInt(1,Integer.parseInt( id_allerta));
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				
				AslCoinvolte ac = loadResultSet(rs);
				ac.setData_chiusura( new Timestamp( System.currentTimeMillis() ) );
				ac.setChiusa_da( idUser );
				ac.update( db );
				
				AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
				historyAslAllerta.setAsl(ac.getId_asl());
				historyAslAllerta.setCu_pianificati_regione(ac.getControlliUfficialiRegionaliPianificati());
				historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				historyAslAllerta.setId_allerte_history(id_history_allerta);
				historyAslAllerta.setTipo_operazione("CHIUSURA REGIONE-F");
				historyAslAllerta.setStato(ac.getId_asl(),allerta.getId(),db);
				historyAslAllerta.store(db);
				
			}
			
			
			
		}catch (Exception e)
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
	public static AslCoinvolte load( int id_allerta, int id_asl, Connection db )
	{
		AslCoinvolte		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? AND id_asl = ? AND enabled" );
			stat.setInt( 1, id_allerta );
			stat.setInt( 2, id_asl );
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
	
	public static AslCoinvolte loadByIdLdd( int id_allerta, int id_ldd, int id_asl, Connection db )
	{
		AslCoinvolte		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM allerte_asl_coinvolte WHERE id_allerta = ? AND id_asl = ? AND id_ldd = ? AND enabled" );
			stat.setInt( 1, id_allerta );
			stat.setInt( 2, id_asl );
			stat.setInt( 3, id_ldd );
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

	public String getCUResidui()
	{
		//return ( (cu_pianificati > 0) ? (((cu_pianificati > cu_eseguiti) ? (cu_pianificati - cu_eseguiti)+ "" : ( 0 + "")  ) )   : ((controlliUfficialiRegionaliPianificati > 0) ? ( (controlliUfficialiRegionaliPianificati> cu_eseguiti) ? (controlliUfficialiRegionaliPianificati - cu_eseguiti ) + "" : (0 + "")  ): ("Non Disponibile"))  );
		return (cu_pianificati > cu_eseguiti) ? (cu_pianificati - cu_eseguiti)+ "" : ( 0 + "");
	}
	
	public static void addCU( String id_allerta, int id_asl, Connection db )
	{
		AslCoinvolte		temp	= null;
		
		try
		{
			temp = AslCoinvolte.load( Integer.parseInt(id_allerta), id_asl, db );
			if(temp!=null)
			{
			temp.setCu_eseguiti( temp.getCu_eseguiti() + 1 );
			temp.update( db );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public static void updateAslCoinvolteAllerta(int id_allerta, String[] asl, int user_id, Connection db,HttpServletRequest request,Ticket allerta,int id_history_allerta,boolean flag_sian)
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
{
		
		
		
	
	
	//cancellazione asl non piu' coinvolte
	String asl_id_list = "-1";
	for( int i=0;i<asl.length;i++ )
	{
		int val=Integer.parseInt(asl[i]);
		
			asl_id_list += ( ", " + val );
		
	}
	
	
	
	
	String sql = "UPDATE allerte_asl_coinvolte SET enabled = FALSE " +
			"WHERE   id_allerta = ? ";
	
	
	PreparedStatement stat = db.prepareStatement( sql );
	stat.setInt( 1, id_allerta );
	stat.execute();
	
	//inserimento nuove asl coinvolte
	AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
	request.getSession().setAttribute("problemaInvioMail", "no");
	ArrayList<AslCoinvolte> lista_asl = new ArrayList<AslCoinvolte>();
	for( int i=0;i<asl.length;i++ )
	{
	
		int idasl=Integer.parseInt(asl[i]);
		
		AslCoinvolte ac = AslCoinvolte.load( id_allerta, idasl, db );
			String cuPianificatiRegione=request.getParameter("cu_"+idasl);
			int cuRegione=0;
			if(cuPianificatiRegione!=null && !cuPianificatiRegione.equals("")){
				cuRegione=Integer.parseInt(cuPianificatiRegione);
			}
			if(ac!=null)
			{
				if (ac.getControlliUfficialiRegionaliPianificati()!=cuRegione)
				{
					
					
					
					String sql1 = "UPDATE allerte_asl_coinvolte SET controlliufficialiregionalipianificati =? ,data_invio_allegato = null ,stato_allegatof=false, data_chiusura = null,chiusa_da=null, cu_pianificati = ?,flag_ripianificazione=true where id_allerta = ? and id_asl =? ";
					PreparedStatement stat1 = db.prepareStatement( sql1 );
					stat1.setInt( 1, cuRegione );
					stat1.setInt( 2, cuRegione );
					stat1.setInt(3, id_allerta);
					stat1.setInt(4, idasl);
					stat1.execute();
					historyAslAllerta.setAsl(idasl);
					historyAslAllerta.setCu_pianificati_regione(cuRegione);
					historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
					historyAslAllerta.setId_allerte_history(id_history_allerta);
					historyAslAllerta.setTipo_operazione("MODIFICA CU")	;
					historyAslAllerta.setStato_allegatof("Non Inviato") ;
					historyAslAllerta.setStato(idasl,allerta.getId(),db)	;
					historyAslAllerta.store(db);
					lista_asl.add(ac);
					}
				
			}
			else
			{
			
			if( ac == null )
			{
				
				
				ac = new AslCoinvolte();
			}
			
				if(idasl == 16){
					ac.setNoteFuoriRegione(request.getParameter("noteFuoriRegione"));
					
				}
				else
				{
					ac.setNoteFuoriRegione("");
				}
				ac.setId_allerta( id_allerta );
				ac.setId_asl( idasl );
				ac.setCu_pianificati_da( user_id );
				ac.setFlag_ripianificazione(true) ;
				ac.setControlliUfficialiRegionaliPianificati(cuRegione);
				ac.setCu_pianificati(cuRegione);
				ac.store( db );
				historyAslAllerta.setAsl(idasl);
				historyAslAllerta.setCu_pianificati_regione(cuRegione);
				historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
				historyAslAllerta.setId_allerte_history(id_history_allerta);
				historyAslAllerta.setTipo_operazione("AGGIUNTA ASL");
				historyAslAllerta.setStato(idasl,allerta.getId(),db);
				historyAslAllerta.setStato_allegatof("Non Inviato") ;
				historyAslAllerta.store(db);
				lista_asl.add(ac);
				
			}
			
		}
	
	
//	for (AslCoinvolte asl_coinvolta : lista_asl)
//	{
//		int idasl=asl_coinvolta.getId_asl();
//		String cuPianificatiRegione=request.getParameter("cu_"+idasl);
//		int cuRegione=0;
//		if(cuPianificatiRegione!=null && !cuPianificatiRegione.equals("")){
//			cuRegione=Integer.parseInt(cuPianificatiRegione);
//		}
//		
//		try
//		{
//			
//			GestionePEC.preparaSpedisciPECripianificazioneallerta(""+idasl, allerta,asl_coinvolta,cuRegione,flag_sian)	;
//		}
//		catch(Exception em)
//		{
//			em.printStackTrace();
//
//			
//			request.getSession().setAttribute("problemaInvioMail", "si");
//			break;
//		}
//		
//	}
	
	
}	
	
	public static void updateAslCoinvolteAllertaByIdLdd(int id_allerta, int id_ldd, String[] asl, int user_id, Connection db,HttpServletRequest request,Ticket allerta,int id_history_allerta,boolean flag_sian)
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
				
			//cancellazione asl non piu' coinvolte
			String asl_id_list = "-1";
			for( int i=0;i<asl.length;i++ )
			{
				int val=Integer.parseInt(asl[i]);
				
					asl_id_list += ( ", " + val );
				
			}
			
			String sql = "UPDATE allerte_asl_coinvolte SET enabled = FALSE " +
					"WHERE   id_allerta = ? and id_ldd = ? ";
			
			
			PreparedStatement stat = db.prepareStatement( sql );
			stat.setInt( 1, id_allerta );
			stat.setInt( 2, id_ldd );
			stat.execute();
			
			//inserimento nuove asl coinvolte
			AllerteAslHistory historyAslAllerta = new AllerteAslHistory();
			request.getSession().setAttribute("problemaInvioMail", "no");
			ArrayList<AslCoinvolte> lista_asl = new ArrayList<AslCoinvolte>();
			for( int i=0;i<asl.length;i++ )
			{
			
				int idasl=Integer.parseInt(asl[i]);
				
				AslCoinvolte ac = AslCoinvolte.loadByIdLdd( id_allerta, id_ldd, idasl, db );
					String cuPianificatiRegione=request.getParameter("cu_"+idasl);
					int cuRegione=0;
					if(cuPianificatiRegione!=null && !cuPianificatiRegione.equals("")){
						cuRegione=Integer.parseInt(cuPianificatiRegione);
					}
					if(ac!=null)
					{
						if (ac.getControlliUfficialiRegionaliPianificati()!=cuRegione)
						{
							
							
							
							String sql1 = "UPDATE allerte_asl_coinvolte SET controlliufficialiregionalipianificati =? ,data_invio_allegato = null ,stato_allegatof=false, data_chiusura = null,chiusa_da=null, cu_pianificati = ?,flag_ripianificazione=true where id_allerta = ? and id_asl =? and id_ldd = ? ";
							PreparedStatement stat1 = db.prepareStatement( sql1 );
							stat1.setInt( 1, cuRegione );
							stat1.setInt( 2, cuRegione );
							stat1.setInt(3, id_allerta);
							stat1.setInt(4, idasl);
							stat1.setInt(5, id_ldd);
							stat1.execute();
							historyAslAllerta.setAsl(idasl);
							historyAslAllerta.setCu_pianificati_regione(cuRegione);
							historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
							historyAslAllerta.setId_allerte_history(id_history_allerta);
							historyAslAllerta.setTipo_operazione("MODIFICA CU")	;
							historyAslAllerta.setStato_allegatof("Non Inviato") ;
							historyAslAllerta.setStato(idasl,allerta.getId(),db)	;
							historyAslAllerta.store(db);
							lista_asl.add(ac);
							}
						
					}
					else
					{
					
					if( ac == null )
					{
						
						
						ac = new AslCoinvolte();
					}
					
						if(idasl == 16){
							ac.setNoteFuoriRegione(request.getParameter("noteFuoriRegione"));
							
						}
						else
						{
							ac.setNoteFuoriRegione("");
						}
						ac.setId_allerta( id_allerta );
						ac.setId_asl( idasl );
						ac.setCu_pianificati_da( user_id );
						ac.setFlag_ripianificazione(true) ;
						ac.setControlliUfficialiRegionaliPianificati(cuRegione);
						ac.setCu_pianificati(cuRegione);
						ac.setId_ldd(id_ldd);
						ac.store( db );
						historyAslAllerta.setAsl(idasl);
						historyAslAllerta.setCu_pianificati_regione(cuRegione);
						historyAslAllerta.setData(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
						historyAslAllerta.setId_allerte_history(id_history_allerta);
						historyAslAllerta.setTipo_operazione("AGGIUNTA ASL");
						historyAslAllerta.setStato(idasl,allerta.getId(),db);
						historyAslAllerta.setStato_allegatof("Non Inviato") ;
						historyAslAllerta.store(db);
						lista_asl.add(ac);
						
					}
					
				}
			
		}
	
		
	
	public int chiudiPerAsl(Connection db, String data, int idAsl, int idUtenteModifica) throws SQLException, ParseException {
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsedDate;
			Timestamp timestamp; 
			parsedDate = dateFormat.parse(data);
			timestamp = new java.sql.Timestamp(parsedDate.getTime()); 
			String sql =
				"UPDATE allerte_asl_coinvolte " +
				"SET data_chiusura = ?,  chiusa_da = ? " +
						"WHERE id = ? and data_chiusura is null ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setTimestamp( ++i, timestamp);
			pst.setInt(++i, idUtenteModifica);
			pst.setInt(++i, this.getId());
			
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}
	
	
	public int ripianificaPerAsl(Connection db, int newPianificati, int idAsl, String motivazione, int idUtenteModifica) throws SQLException, ParseException {
		
		int numCuEseguiti = getNumCuEseguiti() + getNumCuEseguiti_aperti();
		
		if ( newPianificati < numCuEseguiti){
			return 7;
		}
		
		int resultCount = 0;
		try {
			db.setAutoCommit(false);
			PreparedStatement pst = null;
			
			String sql =
				"UPDATE allerte_asl_coinvolte " +
						"SET cu_pianificati = ?,  utente_modifica = ?, data_modifica = now(), motivazione = concat_ws(?, motivazione,?), note = ? " +
						"WHERE id = ? and data_chiusura is null ";
			int i = 0;
			pst = db.prepareStatement(sql);
			pst.setInt( ++i, newPianificati);
			pst.setInt(++i, idUtenteModifica);
			pst.setString(++i, ";");
			pst.setString(++i, motivazione);
			pst.setString(++i, "Ripianificati CU da ASL");
			pst.setInt(++i, this.getId());
			
			resultCount = pst.executeUpdate();
			pst.close();
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		} finally {
			db.setAutoCommit(true);
		}
		return resultCount;
	}
	
	
	
	
	
	
	
	
	
	
}


	
	
	/*public static void updateAslCoinvolteAllerta(int id_allerta, HttpServletRequest req, int user_id, Connection db)
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
{
		
		String[] asl =req.getParameterValues("asl_coinvolte");
		
	//ArrayList<Parameter> al = ParameterUtils.list( req, "asl_c_" );
	
	//cancellazione asl non piu' coinvolte
	String asl_id_list = "-1";
	for( int i=0;i<asl.length;i++ )
	{
		int val=Integer.parseInt(asl[i]);
		
			asl_id_list += ( ", " + val );
		
	}
	
	String sql = "UPDATE allerte_asl_coinvolte SET enabled = FALSE " +
			"WHERE cu_eseguiti < 1 AND id_allerta = ? AND id_asl NOT IN ( " + asl_id_list + " )";
	
	PreparedStatement stat = db.prepareStatement( sql );
	stat.setInt( 1, id_allerta );
	stat.execute();
	
	//inserimento nuove asl coinvolte
	for( int i=0;i<asl.length;i++ )
	{
		int idasl=Integer.parseInt(asl[i]);
			AslCoinvolte ac = AslCoinvolte.load( id_allerta, idasl, db );
			if( ac == null )
			{
				ac = new AslCoinvolte();
				ac.setId_allerta( id_allerta );
				ac.setId_asl( idasl );
				ac.setCu_pianificati_da( user_id );
				ac.store( db );
			}
		
	}
	
}

}*/

	
/*	public static void updateAslCoinvolteAllerta(int id_allerta, HttpServletRequest req, int user_id, Connection db)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		ArrayList<Parameter> al = ParameterUtils.list( req, "asl_c_" );
		
		//cancellazione asl non piu' coinvolte
		String asl_id_list = "-1";
		for( Parameter a: al )
		{
			if( a.getValore().length() > 1 )
			{
				asl_id_list += ( ", " + a.getId() );
			}
		}
		
		String sql = "UPDATE allerte_asl_coinvolte SET enabled = FALSE " +
				"WHERE cu_eseguiti < 1 AND id_allerta = ? AND id_asl NOT IN ( " + asl_id_list + " )";
		
		PreparedStatement stat = db.prepareStatement( sql );
		stat.setInt( 1, id_allerta );
		stat.execute();
		
		//inserimento nuove asl coinvolte
		for( Parameter a: al )
		{
			if( a.getValore().length() > 1 )
			{
				AslCoinvolte ac = AslCoinvolte.load( id_allerta, a.getId(), db );
				if( ac == null )
				{
					ac = new AslCoinvolte();
					ac.setId_allerta( id_allerta );
					ac.setId_asl( a.getId() );
					ac.setCu_pianificati_da( user_id );
					ac.store( db );
				}
			}
		}
		
	}
	
}*/


  
