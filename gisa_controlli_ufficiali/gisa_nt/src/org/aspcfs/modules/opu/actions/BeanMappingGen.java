/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BeanMappingGen {

	public HashMap<String,String> campi = new HashMap<String,String>();
	
	public BeanMappingGen(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd =rs.getMetaData();
		for(int i=1;i<=rsmd.getColumnCount();i++)
		{
			campi.put(rsmd.getColumnName(i),rs.getString(rsmd.getColumnName(i)));
		}
		 
		 
	}
	
	
	public static  ArrayList<BeanMappingGen> buildList(ResultSet rs) throws SQLException
	{
		ArrayList<BeanMappingGen> toRet = new ArrayList<BeanMappingGen>();
		int j = 0;
		
		while(rs.next())
		{
			
			BeanMappingGen toAdd = new BeanMappingGen(rs);
			toRet.add(toAdd);
			
			
		}
		
		
		return toRet;
	}
	
	
	
}
