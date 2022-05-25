/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.base;

import java.util.ArrayList;

public class Stats
{
	String				asl;
	int					idAsl;
	ArrayList<String>	results;
	int					currentIndex = -1;
	
	public void add( String value )
	{
		if( results == null )
		{
			results = new ArrayList<String>();
		}
		
		results.add( value );
	}
	
	public String getAsl()
	{
		return asl;
	}
	
	public void setAsl(String asl)
	{
		this.asl = asl;
	}
	
	public int getIdAsl()
	{
		return idAsl;
	}

	public void setIdAsl(int idAsl)
	{
		this.idAsl = idAsl;
	}
	
	public String get( int index )
	{
		return (getSize() <= index || index < 0) ? (null) : (results.get(index));
	}
	
	public String getNext()
	{
		return get( ++currentIndex );
	}
	
	public void resetIndex()
	{
		currentIndex = -1;
	}
	
	public int getSize()
	{
		return (results == null) ? (0) : (results.size());
	}
	
}
