/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.base_beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Utilities {
	
	
	
	/*metodo che evita l'eccezione di quando si accede ad un JSONObject per
	 * una chiave inesistente
	 */
	public static String getNullableFieldAsString(String key,JSONObject ob)
	{
		String s = null;
		if(ob.has(key))
			s = ob.getString(key);
		return s;
	}
	
	
	
	
	
	/*metodo che se una chiave in un oggetto non esiste, ritorna null per quel campo
	 * se invece esiste ma ha valore stringa vuota, ritorna comunque null
	 */
	public static String getAsNullIfEmpty(String key, JSONObject ob)
	{
		String value = getNullableFieldAsString(key, ob);
		
		if(value != null && value.trim().length( )== 0)
			value = null;
		
		return value;
	}
	
	
	
	/*metodo che evita l'eccezione quando si chiama sul pst un setX con un valore null
	 * e in tal caso lo setta a null con setNull */
	public static <T> void setNullable(int tipo, int index, Object value, PreparedStatement pst) throws Exception
	{
		if(tipo == java.sql.Types.VARCHAR )
		{
			if (value == null)
			{
				pst.setNull(index, java.sql.Types.VARCHAR);
			}
			else
			{
				pst.setString(index, (String)value);
			}
		}
		else if(tipo == java.sql.Types.INTEGER)
		{
			if(value == null)
			{
				pst.setNull(index, java.sql.Types.INTEGER);
			}
			else
			{
				pst.setInt(index, (Integer)value);
			}
		}
		else if(tipo == java.sql.Types.DOUBLE)
		{
			if(value == null)
			{
				pst.setNull(index, java.sql.Types.DOUBLE);
			}
			else
			{
				pst.setDouble(index, (Double)value);
			}
		}
		else if(tipo == java.sql.Types.TIMESTAMP)
		{
			if(value == null)
			{
				pst.setNull(index, java.sql.Types.TIMESTAMP);
			}
			else
			{
				pst.setTimestamp(index, (java.sql.Timestamp)value);
			}
		}
	}
	
	

	 
	
}
