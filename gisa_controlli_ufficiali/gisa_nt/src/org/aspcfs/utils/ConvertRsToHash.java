/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ConvertRsToHash {
    public static List<Object> resultSetToHashMapLookup(ResultSet rs) throws SQLException
    	{
    		ResultSetMetaData md = rs.getMetaData();
    		int columns = md.getColumnCount();//int size =rs.size(); 
    	  
    		ArrayList listRecord = new ArrayList<>();
    			
    			  while (rs.next())
    			  {
    				  HashMap row = new LinkedHashMap(columns);
    				  	for(int i=1; i<=columns; ++i)
    				  	{           
    				  		row.put(md.getColumnName(i),rs.getObject(i));
    				
    				  	}	
    				  	listRecord.add(row);
    			  }
    			    
    		  		
    		return listRecord;
    	}
    
}

