/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import org.apache.commons.beanutils.Converter;

public class FloatConverter implements Converter
{
	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Class clazz, Object value)
	{
		float ret = 0;

		if( value != null && !value.equals("") )
		{
			value = ((String)value).replaceAll( ",", "." );
			try
			{
				ret = Float.parseFloat( (String) value );
			}
			catch ( Exception e )
			{
				try
				{
					ret = Float.parseFloat( (String) value );
				}
				catch ( Exception e1 )
				{
					e1.printStackTrace();
				}
			}
		}
		else if( value.equals(""))
		{
			return null;
		}
		
		return ret;
	}	
}
