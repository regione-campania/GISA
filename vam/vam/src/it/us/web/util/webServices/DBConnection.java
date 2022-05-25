/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.webServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
			
	
	public static Connection getConnection() throws ClassNotFoundException {

		Connection con = null;
		
		Class.forName("org.postgresql.Driver");    
		
		try {
		
			con = DriverManager.getConnection ( "jdbc:postgresql://localhost/vam","postgres", "postgres"); 

		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void closeResources(Statement statement){
    	try
    	{
    		if(statement != null)
    			statement.close();
    	}catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
	
	public static void closeDBServer(Connection con){
    	try
    	{
    		if (con != null) 
    			con.close();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	
        }
    }
}
