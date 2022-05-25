/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.properties;

import java.util.Properties;

public class Message extends GenericProperties
{
	protected static Properties properties = null;
	
	static
	{
		properties = load( "message.properties" );
	}

	public static String get(String property)
	{
		return get( property, properties );
	}

	/**
	 * @param property
	 * @return il valore della chiave se questa e'' presente nel file message.properties, 
	 * la chiave stessa filtrata dal carattere _ (underscore) altrimenti
	 */
	public static String getSmart(String property)
	{
		return getSmart( property, properties );
	}
}
