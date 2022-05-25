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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class SottoAttivita extends GenericBean
{

	private static final long serialVersionUID = -906599187340806298L;
	private Timestamp data_inizio_sospensione;

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;

	
	private int idAsl ;
	private String ragioneSociale = "" ;
	private Timestamp dataScadenza ;
	private String approvalNumber = "" ;
	
	
	private int id					= -1;
	private int id_soa		= -1;
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
	private int id_classificazione ;
	private ArrayList<Integer> listaProdotti = new ArrayList<Integer>();
	private int non_imballata=-1;
	
	
	public int getNon_imballata() {
		return non_imballata;
	}

	public void setNon_imballata(int non_imballata) {
		this.non_imballata = non_imballata;
	}

	public int getId_classificazione() {
		return id_classificazione;
	}

	public void setId_classificazione(int id_classificazione) {
		this.id_classificazione = id_classificazione;
	}

	public ArrayList<Integer> getListaProdotti() {
		if(listaProdotti==null)
			listaProdotti = new ArrayList<Integer>();
		return listaProdotti;
	}

	public void setListaProdotti(ArrayList<Integer> listaProdotti) {
		this.listaProdotti = listaProdotti;
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

	public int getId_soa() {
		return id_soa;
	}

	public void setId_soa(int id_soa) {
		this.id_soa = id_soa;
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
	
	
	
	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Timestamp getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public static ArrayList<SottoAttivita> loadBySoa( int id_soa, Connection db )
	{
		ArrayList<SottoAttivita>	ret		= new ArrayList<SottoAttivita>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM soa_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_soa = ? and not_used is null " );
			stat.setInt( 1, id_soa );
			res		= stat.executeQuery();
			while( res.next() )
			{
				 SottoAttivita tmp = loadResultSet( res ) ;
				String listaProdotti = "select id_prodotto from lista_prodotti_soa where id_soa = ? and id_linea_produttiva = ?" ;
				PreparedStatement psd_sel_prod = db.prepareStatement(listaProdotti);
				psd_sel_prod.setInt(1, id_soa);
				psd_sel_prod.setInt(2, tmp.getId());
				ResultSet rs_sel  = psd_sel_prod.executeQuery();
				while (rs_sel.next())
				{
					tmp.getListaProdotti().add(rs_sel.getInt(1));
				}
				
				ret.add( tmp );
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
	
	public void store( Connection db ,ActionContext context)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		this.id = DatabaseUtils.getNextSeqInt(db, context , "soa_sottoattivita","id");
		String sql = "INSERT INTO soa_sottoattivita( ";
		
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
	            
	            if (!field.equalsIgnoreCase("approvalNumber") && !field.equalsIgnoreCase("dataScadenza") &&  !field.equalsIgnoreCase("ragionesociale")  && !field.equalsIgnoreCase("idasl") && !field.equalsIgnoreCase("partitaiva") &&
	            ! field.equalsIgnoreCase("listaProdotti")  && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
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
	    
	    String insert_lista_prodotti = "insert into lista_prodotti_soa values (?,?,?)";
	    PreparedStatement pst =db.prepareStatement(insert_lista_prodotti);
	    for (Integer idProdotto : this.getListaProdotti())
	    {
	    	pst.setInt(1, this.getId_soa());
	    	pst.setInt(2, idProdotto);
	    	pst.setInt(3, this.getId());
	    	
	    	pst.execute();
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
	            
	             if (!field.equalsIgnoreCase("approvalNumber") && !field.equalsIgnoreCase("dataScadenza") && !field.equalsIgnoreCase("ragionesociale")  && !field.equalsIgnoreCase("idasl") && !field.equalsIgnoreCase("partitaiva"))
	             {
	             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
	             {
	                  getter = m[j];
	             }
	             else if( met.equalsIgnoreCase( "SET" + field ) )
	             {
	                 setter = m[j];
	             }
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
					"SELECT * FROM soa_sottoattivita WHERE trashed_date IS NULL AND enabled AND id_soa = ? AND codice_impianto = ? AND attivita = ? and codice_sezione=? " );
			stat.setInt( 1, id_soa );
			stat.setInt( 2, codice_impianto );
			stat.setString( 3, attivita );
			stat.setInt(4, codice_sezione);
			
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
	
	public void update(Connection db)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "UPDATE soa_sottoattivita SET ";
		
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
	            
	            if (!field.equalsIgnoreCase("approvalNumber") && !field.equalsIgnoreCase("dataScadenza") &&  !field.equalsIgnoreCase("ragionesociale")  && !field.equalsIgnoreCase("idasl") && !field.equalsIgnoreCase("partitaiva") && 
	            ! field.equalsIgnoreCase("listaProdotti") && !field.equalsIgnoreCase( "id_soa" ) & !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
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
	public void updateAutorizzazzione(Connection db)
	{
		String sql = "update soa_sottoattivita set tipo_autorizzazione = "+this.getTipo_autorizzazione()+",data_inizio_attivita = ? , stato_attivita =? , descrizione_stato_attivita = (select description from lookup_stato_lab where code = ?) where id = ? ";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setTimestamp(1, this.data_inizio_attivita);
			pst.setInt(2,this.stato_attivita);
			pst.setInt(3, this.stato_attivita);
			pst.setInt(4, this.id);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String getDataScadenzaAsString() {
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataScadenza!=null)
		{
			data = sdf.format(new Date(dataScadenza.getTime()));
		}
		return data;
	}
	
	public String getData_fine_attivitaAsString() {
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_fine_attivita!=null)
		{
			data = sdf.format(new Date(data_fine_attivita.getTime()));
		}
		return data;
	}
	public String getData_inizio_attivitaAsString() {
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_inizio_attivita!=null)
		{
			data = sdf.format(new Date(data_inizio_attivita.getTime()));
		}
		return data;
	}
	
	public void revocaSottoAttivitaStabilimento(Connection db) throws SQLException
	{
		try
		{
			db.prepareStatement("update organization set stato_lab = 1 where org_id = "+this.getId_soa()).execute();
		}
		catch(SQLException e )
		{
			throw e ;
		}
	}
	
	public static ArrayList<SottoAttivita> getSottoAttivitaCondizionateinScadenza(Connection db,int idAsl)
	{
		SottoAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		ArrayList<SottoAttivita> lista=new ArrayList<SottoAttivita>();
		try
		{
			
			String query = "SELECT *," +
			"case when ss.stato_attivita=5 then ((ss.data_inizio_attivita+ interval '3 month')- interval '10 day')" +
			"when ss.stato_attivita=9 and ss.tipo_autorizzazione =2 then ((ss.data_inizio_attivita+ interval '6 month')- interval '10 day') end as data_scadenza" +
			" FROM soa_sottoattivita ss join organization o on ss.id_soa = o.org_id and o.tipologia = 97 and o.trashed_date is null " +
			" WHERE  ss.enabled AND " +
			"((current_timestamp>=((ss.data_inizio_attivita+ interval '3 month')- interval '10 day')  and ss.stato_attivita=5) or " +
			"(current_timestamp>=((ss.data_inizio_attivita+ interval '6 month')- interval '10 day') and ss.stato_attivita=9) )" ;
			
			if (idAsl>0)
				query += " and o.site_id = "+idAsl ;
			
			query += " order by " +
			"case when ss.stato_attivita=5 then ((ss.data_inizio_attivita+ interval '3 month')- interval '10 day')" +
			"when ss.stato_attivita=9 then ((ss.data_inizio_attivita+ interval '6 month')- interval '10 day') end " ;
			
			stat	= db.prepareStatement( query
					);
		
		
		
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret = loadResultSet( res );
				ret.setRagioneSociale(res.getString("name"));
				ret.setIdAsl(res.getInt("site_id"));
				ret.setApprovalNumber(res.getString("numaut"));
				ret.setDataScadenza(res.getTimestamp("data_scadenza"));
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
	
	
	
	public static ArrayList<SottoAttivita> getSottoAttivitaSospeseinScadenza(Connection db,int idAsl)
	{
		SottoAttivita		ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		ArrayList<SottoAttivita> lista=new ArrayList<SottoAttivita>();
		try
		{
			
			
			String query = "SELECT *," +
			"((ss.data_inizio_sospensione+ interval '12 month')- interval '1 month') as data_scadenza " +
			" FROM soa_sottoattivita ss join organization o on ss.id_soa = o.org_id and o.tipologia = 97 and o.trashed_date is null " +
			" WHERE  ss.enabled AND " +
			"  o.stato_lab in (0,1,2,5,3) and ss.stato_attivita =2 and " +
			"(current_timestamp>=((ss.data_inizio_sospensione+ interval '12 month')- interval '1 month')) " ;
			
		
			if (idAsl>0)
				query+= " and o.site_id = "+idAsl ;
			
			query+= " order by " +
			"((ss.data_inizio_sospensione+ interval '12 month')- interval '1 month') ";
			
			stat	= db.prepareStatement( query );
		
			
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret = loadResultSet( res );
				ret.setRagioneSociale(res.getString("name"));
				ret.setIdAsl(res.getInt("site_id"));
				ret.setApprovalNumber(res.getString("numaut"));
				ret.setDataScadenza(res.getTimestamp("data_scadenza"));
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
	
	public String getData_inizio_sospensioneAsString() {
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data_inizio_sospensione!=null)
		{
			data = sdf.format(new Date(data_inizio_sospensione.getTime()));
		}
		return data;
	}
	
	public Timestamp getData_inizio_sospensione() {
		return data_inizio_sospensione;
	}

	public void setData_inizio_sospensione(Timestamp data_inizio_sospensione) {
		this.data_inizio_sospensione = data_inizio_sospensione;
	}
	
		
	
}
