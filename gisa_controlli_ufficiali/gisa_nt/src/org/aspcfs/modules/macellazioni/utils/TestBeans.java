/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import org.aspcfs.modules.macellazioni.base.StoriaCapi;

public class TestBeans
{

	public static void main(String[] args)
	{
		Connection db = null;
		
		try
		{
			Class.forName("org.postgresql.Driver");
			 db = DriverManager.getConnection("jdbc:postgresql:"+"gisa",
                     "postgres",
                     "postgres");

			Vector<StoriaCapi> sc = StoriaCapi.load( 2, db );
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( db != null )
			{
				try
				{
					db.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}

}
