/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;




public class DbUtil
{
	//private static DataSource datasource = null;
	/*
	public static void createDataSourceJDBC(String nomeDb,String userName,String password,String ipAddress)
	{
		DataSource temp = new DataSource();
		
		/*temp.setUser( userName);
		temp.setPassword(password);
		temp.setServerName(ipAddress);
		temp.setDatabaseName(nomeDb );
		
		temp.setMaxConnections(400);
		
		datasource = temp;
	}
	
	public static void destroyDataSource()
	{
		if( datasource != null )
		{
			datasource.close();
		}
	}
	*/
//	public static Connection ottieniConnessioneJDBC(String dbUser, String dbPwd, String dbHost, String dbName) throws SQLException{
		public static Connection ottieniConnessioneJDBC(String dbDatasource) throws SQLException{
		/*if( datasource == null )
		{
			createDataSourceJDBC(dbName, dbUser, dbPwd, dbHost);
		}
	
		System.out.println("CONNESSIONE GUC: " + dbName + " - " + dbUser+ " - " + dbPwd);
		
		return (datasource == null) ? (null) : (datasource.getConnection());
		*/
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javax.sql.DataSource ds = null;
		try {
			ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/"+dbDatasource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds.getConnection();

	}
	
	public static void chiudiConnessioneJDBC(ResultSet rs, PreparedStatement pst, Connection conn) throws SQLException{
		if(rs != null){
			rs.close();
		}
		chiudiConnessioneJDBC(pst, conn);
	}
	
	public static void chiudiConnessioneJDBC(PreparedStatement pst, Connection conn) throws SQLException{
		if(pst != null){
			pst.close();
		}
		if(conn != null){
			conn.close();
		}
	//	if (datasource!=null)
	//	datasource.close();
	//	datasource = null ;
	}
	
	
	
}
