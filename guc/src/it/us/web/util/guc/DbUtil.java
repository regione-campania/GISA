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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.postgresql.util.PSQLException;


public class DbUtil
{
	
	private final static Logger log = Logger.getLogger(DbUtil.class);
	
	public static void createDataSourceJDBC(String nomeDb,String userName,String password,String ipAddress)
	{
		/*Jdbc3PoolingDataSource temp = new Jdbc3PoolingDataSource();
		
		temp.setUser( userName);
		temp.setPassword(password);
		temp.setServerName(ipAddress);
		temp.setDatabaseName(nomeDb );
		
		temp.setMaxConnections(400);
		
		datasource = temp; */
	}
	
	public static void destroyDataSource()
	{
		/*if( datasource != null )
		{
			datasource.close();
		}*/
	}
	
//	public static Connection ottieniConnessioneJDBC(String dbUser, String dbPwd, String dbHost, String dbName) throws SQLException{
	public static Connection ottieniConnessioneJDBC(String dbDatasource) throws SQLException, NamingException{
		/*	if( datasource == null )
		{
			createDataSourceJDBC(dbName, dbUser, dbPwd, dbHost);
		}
	
		System.out.println("MAX CONNECTIONS: " + datasource.getMaxConnections());
		
		try
		{
			return (datasource == null) ? (null) : (datasource.getConnection());
		}
		catch(PSQLException e)
		{
			e.printStackTrace();
			datasource = null;
			return null;
		}*/
		
		System.out.println("GUC - Arrivata richiesta per datasource: "+ dbDatasource);
		
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataSource ds = null;
		try {
			ds = (DataSource) ctx.lookup("java:comp/env/"+dbDatasource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		Connection conn = ds.getConnection();
		
		getInfo(conn,0,ds);
		return conn;
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
	
	public static void chiudiConnessioneJDBC(PreparedStatement pst, Connection conn, String dbDatasource) throws SQLException{
		System.out.println("GUC - Arrivata richiesta per chiusura connessione: "+ dbDatasource);
		chiudiConnessioneJDBC(null, conn);
		System.out.println("GUC - Chiusa connessione: "+ dbDatasource);

	}
	
	
	
	public static void getInfo(Connection db,int tipo,DataSource ds){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		  try{	
		  		System.out.println("[GUC] "+ sdf.format(new Date()) + " " + ((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()  + " **POOL SIZE:  "+ds.getPoolSize()  );	
		  }catch (Exception e) {
		  	// TODO: handle exception
		  }
		  }
	
	
}
