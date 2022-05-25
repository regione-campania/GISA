/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DoubleConverter;

public class MyDoubleConverter implements Converter
{
	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Class arg0, Object arg1) 
	{
		if(  arg1 == null || arg1.equals(""))
		{
			return null;
		}
		else
		{ 
			arg1 = (arg1.toString()).replaceAll( ",", "." );
			DoubleConverter dc = new DoubleConverter();
			return dc.convert(arg0, arg1);
		}
	}
}



