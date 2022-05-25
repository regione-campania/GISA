/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.aiequidi.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.aspcfs.modules.macellazioni.base.Capo;

public class Aiequidi {


	
	private int anno ;
	private String id_capo ;
	private String esito ; 
	private String risultato;
	private Timestamp data_prelievo ;
	private int num_rapporto ;
	private String num_accettazione;
	private int num_capi_prelevati ;
	private String num_acc_progressivo_camp;
	private Timestamp  data_accettazione ;
	private String nomin_utente  ;
	private String codazie ;
	private String ragione_sociale ;
	private String citta ;
	private Timestamp data_fine_prova ;
	
	private String  data_accettazione_string ;
	private String data_fine_prova_string ;
	private String data_prelievo_string ;

	
	public Timestamp getData_prelievo() {
		return data_prelievo;
	}
	public void setData_prelievo(Timestamp data_prelievo) {
		this.data_prelievo = data_prelievo;
	}
	public String getData_accettazione_string() {
		return data_accettazione_string;
	}
	public void setData_accettazione_string(String data_accettazione_string) {
		this.data_accettazione_string = data_accettazione_string;
	}
	public String getData_fine_prova_string() {
		return data_fine_prova_string;
	}
	public void setData_fine_prova_string(String data_fine_prova_string) {
		this.data_fine_prova_string = data_fine_prova_string;
	}
	public String getData_prelievo_string() {
		return data_prelievo_string;
	}
	public void setData_prelievo_string(String data_prelievo_string) {
		this.data_prelievo_string = data_prelievo_string;
	}
	public int getNum_rapporto() {
		return num_rapporto;
	}
	public void setNum_rapporto(int num_rapporto) {
		this.num_rapporto = num_rapporto;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String getId_capo() {
		return id_capo;
	}
	public void setId_capo(String id_capo) {
		this.id_capo = id_capo;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getRisultato() {
		return risultato;
	}
	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}
	
	public String getNum_accettazione() {
		return num_accettazione;
	}
	public void setNum_accettazione(String num_accettazione) {
		this.num_accettazione = num_accettazione;
	}
	public int getNum_capi_prelevati() {
		return num_capi_prelevati;
	}
	public void setNum_capi_prelevati(int num_capi_prelevati) {
		this.num_capi_prelevati = num_capi_prelevati;
	}
	public String getNum_acc_progressivo_camp() {
		return num_acc_progressivo_camp;
	}
	public void setNum_acc_progressivo_camp(String num_acc_progressivo_camp) {
		this.num_acc_progressivo_camp = num_acc_progressivo_camp;
	}
	public Timestamp getData_accettazione() {
		return data_accettazione;
	}
	public void setData_accettazione(Timestamp data_accettazione) {
		this.data_accettazione = data_accettazione;
	}
	public String getNomin_utente() {
		return nomin_utente;
	}
	public void setNomin_utente(String nomin_utente) {
		this.nomin_utente = nomin_utente;
	}
	public String getCodazie() {
		return codazie;
	}
	public void setCodazie(String codazie) {
		this.codazie = codazie;
	}
	public String getRagione_sociale() {
		return ragione_sociale;
	}
	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public Timestamp getData_fine_prova() {
		return data_fine_prova;
	}
	public void setData_fine_prova(Timestamp data_fine_prova) {
		this.data_fine_prova = data_fine_prova;
	}
	
	 protected static int parseType(Class<?> type)
	    {
	        int ret = -1;
	        
	        String name = type.getSimpleName();
	        
	        if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
	        {
	            ret = Types.INTEGER;
	        }
	        else if( name.equalsIgnoreCase( "string" ) )
	        {
	            ret = Types.VARCHAR;
	        }
	        else if( name.equalsIgnoreCase( "double" ) )
	        {
	            ret = Types.DOUBLE;
	        }
	        else if( name.equalsIgnoreCase( "float" ) )
	        {
	            ret = Types.FLOAT;
	        }
	        else if( name.equalsIgnoreCase( "timestamp" ) )
	        {
	            ret = Types.TIMESTAMP;
	        }
	        else if( name.equalsIgnoreCase( "date" ) )
	        {
	            ret = Types.DATE;
	        }
	        else if( name.equalsIgnoreCase( "boolean" ) )
	        {
	            ret = Types.BOOLEAN;
	        }
	        
	        return ret;
	    }
	 public Aiequidi()
	 {
		 
	 }
	 
	public void buildRecord(ResultSet res) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		
		Field[]	f = this.getClass().getDeclaredFields();
		Method[]	m = this.getClass().getMethods();
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
	             case Types.INTEGER:
	                 o = res.getInt( field );
	                 break;
	             case Types.VARCHAR:
	                 o = res.getString( field );
	                 break;
	             case Types.BOOLEAN:
	                 o = res.getBoolean( field );
	                 break;
	             case Types.TIMESTAMP:
	                 o = res.getTimestamp( field );
	                 break;
	             case Types.DATE:
	                 o = res.getDate( field );
	                 break;
	             case Types.FLOAT:
	                 o = res.getFloat( field );
	                 break;
	             case Types.DOUBLE:
	                 o = res.getDouble( field );
	                 break;
	             }
	             
	             setter.invoke( this, o );
	         
	         }
		}


	}

}
