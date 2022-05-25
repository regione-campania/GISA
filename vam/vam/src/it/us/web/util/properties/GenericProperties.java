/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GenericProperties
{
	protected static Properties load( String fileName )
	{
		InputStream	is = GenericProperties.class.getResourceAsStream( fileName );
		Properties applicationProperties = new Properties();
		try
		{
			applicationProperties.load( is );
		} 
		catch (IOException e)
		{
			applicationProperties = null;
		}
		return applicationProperties;
	}
	
	protected static String get( String property, Properties properties )
	{
		return (properties != null) ? properties.getProperty( property ) : null;
	}
	
	/**
	 * @param property, properties
	 * @return il valore della chiave se questa è presente nel file message.properties, 
	 * la chiave stessa filtrata dal carattere _ (underscore) altrimenti
	 */
	protected static String getSmart( String property, Properties properties )
	{
		return ( get(property, properties) == null ) ? cleanPropertyName(property) : get(property, properties);
	}
	
	protected static String cleanPropertyName( String propertyName )
	{
		String ret = null;
		
		if( propertyName != null )
		{
			ret = propertyName.replaceAll( "_", " " ).trim();
		}
		
		return ret;
	}
	
}
