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
import java.util.ArrayList;
import java.util.Vector;

public class AllerteHistory {

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private String motivo ;
	private int id ;
	private int id_allerta ;
	private int id_ldd;
	private String tipo_operazione ;
	private Timestamp data_operazione ;
	private int user_id ;
	private String nominativo ;
	ArrayList<AllerteAslHistory> lista_dettaglio_storia = new ArrayList<AllerteAslHistory>();
	
	
	
	public ArrayList<AllerteAslHistory> getLista_dettaglio_storia() {
		return lista_dettaglio_storia;
	}
	public void setLista_dettaglio_storia(
			ArrayList<AllerteAslHistory> lista_dettaglio_storia) {
		this.lista_dettaglio_storia = lista_dettaglio_storia;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public int getId() {
		return id;
	}
	public void setId(Connection db) throws SQLException {
		ResultSet rs = db.prepareStatement("select nextval('seq_allerte_history')").executeQuery();
		if(rs.next())
			this.id = rs.getInt(1);
	}
	public void setId(int id) throws SQLException {
		
			this.id = id;
	}
	public int getId_allerta() {
		return id_allerta;
	}
	public void setId_allerta(int id_allerta) {
		this.id_allerta = id_allerta;
	}
	public String getTipo_operazione() {
		return tipo_operazione;
	}
	public void setTipo_operazione(String tipo_operazione) {
		this.tipo_operazione = tipo_operazione;
	}
	public Timestamp getData_operazione() {
		return data_operazione;
	}
	public void setData_operazione(Timestamp data_operazione) {
		this.data_operazione = data_operazione;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	public void loadHistory( Connection db ) throws SQLException
	{
		String select = "select asl,tipo_operazione,cu_pianificati_regione,stato,data ,stato_allegatof " +
						"from allerte_asl_coinvolte_history " +
						"where id_allerte_history = ? " ;
		PreparedStatement pst = db.prepareStatement(select);
		pst.setInt(1, this.getId()) ;
		ResultSet rs = pst.executeQuery() ;
		while (rs.next())
		{
			int asl = rs.getInt(1);
			String tipo_operazione = rs.getString(2)	;
			int cu_pianificati = rs.getInt(3)			;
			String stato = rs.getString(4)				;
			Timestamp data = rs.getTimestamp(5)			;
			String stato_allegato = rs.getString(6)				;
			AllerteAslHistory allerteAslHistory = new AllerteAslHistory();
			allerteAslHistory.setAsl(asl);
			allerteAslHistory.setTipo_operazione(tipo_operazione);
			allerteAslHistory.setCu_pianificati_regione(cu_pianificati);
			allerteAslHistory.setStato(stato);
			allerteAslHistory.setData(data);
			allerteAslHistory.setStato_allegatof(stato_allegato);
			lista_dettaglio_storia.add(allerteAslHistory);
			
		}
	}
	
	
	public void store( Connection db )
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
{
	String sql = "INSERT INTO allerte_history( ";
	
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
            if(! field.equals("nominativo") && ! "lista_dettaglio_storia".equals(field) &&( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
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
	public int getId_ldd() {
		return id_ldd;
	}
	public void setId_ldd(int id_ldd) {
		this.id_ldd = id_ldd;
	}
}
