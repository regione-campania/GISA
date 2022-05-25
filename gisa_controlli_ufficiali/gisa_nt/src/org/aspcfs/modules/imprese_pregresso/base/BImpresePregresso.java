/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.imprese_pregresso.base;

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

public class BImpresePregresso extends GenericBean
{
	
	private static final long serialVersionUID = 7127566929908851546L;

	private static final int INT = Types.INTEGER;

	private static final int STRING = Types.VARCHAR;

	private static final int DOUBLE = Types.DOUBLE;

	private static final int FLOAT = Types.FLOAT;

	private static final int TIMESTAMP = Types.TIMESTAMP;

	private static final int DATE = Types.DATE;

	private static final int BOOLEAN = Types.BOOLEAN;
	
	private Timestamp data_importazione_osa;
	public Timestamp getData_importazione_osa() {
		return data_importazione_osa;
	}

	public void setData_importazione_osa(Timestamp data_importazione_osa) {
		this.data_importazione_osa = data_importazione_osa;
	}
	
	private Timestamp data_import;
	public Timestamp getData_import() {
		return data_import;
	}

	public void setData_import(Timestamp data_import) {
		this.data_import = data_import;
	}
	
	private int org_id;
	
	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	private int id = -1;
	
	private Timestamp data_registrazione_asl;
	public Timestamp getData_registrazione_asl() {
		return data_registrazione_asl;
	}

	public void setData_registrazione_asl(Timestamp data_registrazione_asl) {
		this.data_registrazione_asl = data_registrazione_asl;
	}
	private String n_prot_comune;
	public String getN_prot_comune() {
		return n_prot_comune;
	}

	public void setN_prot_comune(String n_prot_comune) {
		this.n_prot_comune = n_prot_comune;
	}
	private Timestamp data_prot_comune;	
	public Timestamp getData_prot_comune() {
		return data_prot_comune;
	}

	public void setData_prot_comune(Timestamp data_prot_comune) {
		this.data_prot_comune = data_prot_comune;
	}
	private String n_prot_asl;
	public String getN_prot_asl() {
		return n_prot_asl;
	}

	public void setN_prot_asl(String n_prot_asl) {
		this.n_prot_asl = n_prot_asl;
	}
	private Timestamp data_prot_asl;	
	public Timestamp getData_prot_asl() {
		return data_prot_asl;
	}

	public void setData_prot_asl(Timestamp data_prot_asl) {
		this.data_prot_asl = data_prot_asl;
	}
	private String tipologia_attivita;
	public String getTipologia_attivita() {
		return tipologia_attivita;
	}

	public void setTipologia_attivita(String tipologia_attivita) {
		this.tipologia_attivita = tipologia_attivita;
	}
	private String codice_ateco;
	public String getCodice_ateco() {
		return codice_ateco;
	}

	public void setCodice_ateco(String codice_ateco) {
		this.codice_ateco = codice_ateco;
	}
	private String denominazione;
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	private String ragione_sociale;
	public String getRagione_sociale() {
		return ragione_sociale;
	}

	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
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
	private String codice_registrazione_sian;
	public String getCodice_registrazione_sian() {
		return codice_registrazione_sian;
	}

	public void setCodice_registrazione_sian(String codice_registrazione_sian) {
		this.codice_registrazione_sian = codice_registrazione_sian;
	}
	private String rappresentante_legale;
	public String getRappresentante_legale() {
		return rappresentante_legale;
	}

	public void setRappresentante_legale(String rappresentante_legale) {
		this.rappresentante_legale = rappresentante_legale;
	}
	private String indirizzo_sede_operativa;
	public String getIndirizzo_sede_operativa() {
		return indirizzo_sede_operativa;
	}

	public void setIndirizzo_sede_operativa(String indirizzo_sede_operativa) {
		this.indirizzo_sede_operativa = indirizzo_sede_operativa;
	}
	private String comune_sede_operativa;
	public String getComune_sede_operativa() {
		return comune_sede_operativa;
	}

	public void setComune_sede_operativa(String comune_sede_operativa) {
		this.comune_sede_operativa = comune_sede_operativa;
	}
	private String codice_istat;
	public String getCodice_istat() {
		return codice_istat;
	}

	public void setCodice_istat(String codice_istat) {
		this.codice_istat = codice_istat;
	}
	private String via_sede_legale;
	public String getVia_sede_legale() {
		return via_sede_legale;
	}

	public void setVia_sede_legale(String via_sede_legale) {
		this.via_sede_legale = via_sede_legale;
	}
	private String comune_sede_legale;
	public String getComune_sede_legale() {
		return comune_sede_legale;
	}

	public void setComune_sede_legale(String comune_sede_legale) {
		this.comune_sede_legale = comune_sede_legale;
	}
	private Timestamp data_inizio_attivita;	
	public Timestamp getData_inizio_attivita() {
		return data_inizio_attivita;
	}

	public void setData_inizio_attivita(Timestamp data_inizio_attivita) {
		this.data_inizio_attivita = data_inizio_attivita;
	}
	private Timestamp data_fine_attivita;
	public Timestamp getData_fine_attivita() {
		return data_fine_attivita;
	}

	public void setData_fine_attivita(Timestamp data_fine_attivita) {
		this.data_fine_attivita = data_fine_attivita;
	}
	private String	codice_istat_regione;
	public String getCodice_istat_regione() {
		return codice_istat_regione;
	}

	public void setCodice_istat_regione(String codice_istat_regione) {
		this.codice_istat_regione = codice_istat_regione;
	}
	private String codice_istat_asl;	
	public String getCodice_istat_asl() {
		return codice_istat_asl;
	}

	public void setCodice_istat_asl(String codice_istat_asl) {
		this.codice_istat_asl = codice_istat_asl;
	}

	public Vector<BImpresePregresso> load( Connection db )
	{
		Vector<BImpresePregresso>	ret		= new Vector<BImpresePregresso>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM imprese_pregresso LIMIT 100" );
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
	
	private static BImpresePregresso loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		BImpresePregresso ret = new BImpresePregresso();
		
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
		String sql = "INSERT INTO imprese_pregresso( ";
		
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

	public static Vector<BImpresePregresso> load( String ragione_sociale, String partita_iva,
			String codice_fiscale, String duplicati, Connection db )
	{
		Vector<BImpresePregresso>	ret		= new Vector<BImpresePregresso>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		ragione_sociale	= (ragione_sociale == null)	? ("") : (ragione_sociale.trim());
		partita_iva		= (partita_iva == null)		? ("") : (partita_iva.trim());
		codice_fiscale	= (codice_fiscale == null)	? ("") : (codice_fiscale.trim());
		
		String sql = "SELECT * FROM imprese_pregresso ";
		if( duplicati != null )
		{
			sql = "SELECT " +
					"distinct on ( ragione_sociale, denominazione, partita_iva, codice_fiscale,  " +
					"indirizzo_sede_operativa, comune_sede_operativa ) " +
					"* FROM imprese_pregresso ";
		}
		
		try
		{
			boolean where = false;
			
			if( ragione_sociale.length() > 0 )
			{
				sql += ( " WHERE denominazione ILIKE '%" + ragione_sociale.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			if( partita_iva.length() > 0 )
			{
				if(!where)
				{
					sql += " WHERE ";
				}
				else
				{
					sql += " AND ";
				}
				sql += ( " partita_iva ILIKE '%" + partita_iva.replaceAll( "'", "''" ) + "%' " );
			}
			if( codice_fiscale.length() > 0 )
			{
				if(!where)
				{
					sql += " WHERE ";
				}
				else
				{
					sql += " AND ";
				}
				sql += ( " codice_fiscale ILIKE '%" + codice_fiscale.replaceAll( "'", "''" ) + "%' " );
			}
			
			if( duplicati != null )
			{
				sql += "ORDER BY ragione_sociale, denominazione, partita_iva, codice_fiscale,  " +
						"indirizzo_sede_operativa, comune_sede_operativa LIMIT 100 ";
			}
			else
			{
				sql += " ORDER BY ragione_sociale LIMIT 100 ";
			}
			
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static BImpresePregresso load(String id, Connection db )
	{

		BImpresePregresso	ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM imprese_pregresso WHERE id = ? " );
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

	public static void importato(String id_cc, int orgId, Connection db)
	{
		PreparedStatement	stat	= null;
		String				sql		= "UPDATE imprese_pregresso SET data_importazione_osa = ?, org_id = ? WHERE id = ? ";
		
		if( (id_cc != null) && (id_cc.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id_cc );
				stat	= db.prepareStatement( sql );
				stat.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
				stat.setInt( 2, orgId );
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
		
	}
	
}

