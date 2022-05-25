/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlliUfficialiThread  {
  

    public void refreshNucleo(int idControllo,Connection db) {
    	
    	try
    	{
    	PreparedStatement pst =  db.prepareStatement("select * from public.refresh_nucleo_ispettivo(?)");
    	pst.setInt(1, idControllo);
    	pst.execute();
    	}
    	catch(SQLException e)
    	{
    		
    	}
    	
    }
}