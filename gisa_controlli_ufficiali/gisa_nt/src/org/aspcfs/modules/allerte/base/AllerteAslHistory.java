/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Vector;

public class AllerteAslHistory {
	
	/**
	 * 1
	 */
	public static final String ATTIVA				= "Attiva";
	/**
	 * 2
	 */
	public static final String CONTROLLI_IN_CORSO	= "Controlli in Corso";
	/**
	 * 3
	 */
	public static final String CONTROLLI_COMPLETATI	= "Controlli Completati";
	/**
	 * 4
	 */
	public static final String CHIUSA				= "Chiusa";
	/**
	 * 5
	 */
	public static final String RIPIANIFICAZIONE		= "Controlli in Corso - R";

	
	public static final String CONTROLLI_COMPLETATI_R	= "Controlli Completati - R";


	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id_allerte_history ;
	private String  tipo_operazione ;
	private int  asl ;
	private int  cu_pianificati_regione ;
	private String  stato ;
	private Timestamp  data ;
	private String stato_allegatof ;
	
	
	
	
	public String getStato_allegatof() {
		return stato_allegatof;
	}

	public void setStato_allegatof(String stato_allegatof) {
		this.stato_allegatof = stato_allegatof;
	}

	public int getId_allerte_history() {
		return id_allerte_history;
	}

	public void setId_allerte_history(int id_allerte_history) {
		this.id_allerte_history = id_allerte_history;
	}

	public String getTipo_operazione() {
		return tipo_operazione;
	}

	public void setTipo_operazione(String tipo_operazione) {
		this.tipo_operazione = tipo_operazione;
	}

	public int getAsl() {
		return asl;
	}

	public void setAsl(int asl) {
		this.asl = asl;
	}

	public int getCu_pianificati_regione() {
		return cu_pianificati_regione;
	}

	public void setCu_pianificati_regione(int cu_pianificati_regione) {
		this.cu_pianificati_regione = cu_pianificati_regione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(int id_asl,int id_allerta,Connection db) {
		
		AslCoinvolte asl = AslCoinvolte.load(id_allerta, id_asl, db);
		
		this.stato = parseStato(asl);
	}
	
public void setStato(String stato) {
		
		
		
		this.stato = stato;
	}
	public void setStato(AslCoinvolte asl) {
		
		this.stato = parseStato(asl);
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public void store( Connection db )
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
{
	String sql = "INSERT INTO allerte_asl_coinvolte_history( ";
	
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
	
	private String parseStato(AslCoinvolte ac)
	{
		String ret = "";

		if( ac.getData_chiusura() != null )
		{
			ret = CHIUSA;
		}
		else if( ac.getCu_pianificati() <= 0 )
		{
			ret = ATTIVA;
		}
		else if( ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0 && (ac.getFlag_ripianificazione()==false ) )
		{
			ret = CONTROLLI_IN_CORSO;
		}
		else if( ac.getFlag_ripianificazione()==true && ( ac.getCu_pianificati() - ac.getCu_eseguiti() ) > 0)
		{
			ret = RIPIANIFICAZIONE;
		}
		else
		{
			if (ac.getFlag_ripianificazione()==true)
			{
				ret = CONTROLLI_COMPLETATI_R ;
			}
			else ret = CONTROLLI_COMPLETATI;
		}
		
		return ret;
	}

}
