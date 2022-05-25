/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.aspcfs.utils.GestoreConnessioni;



public class DbUtil
{
	private static Logger logger = Logger.getLogger("MainLogger");
	private static DataSource datasource = null;
	private static int numConn = 0;
	
/*	public static void createDataSource()
	{
		Jdbc3PoolingDataSource temp = new Jdbc3PoolingDataSource();
		
		temp.setUser( ApplicationProperties.getProperty( "USERNAME" ) );
		temp.setPassword( ApplicationProperties.getProperty( "PASSWORD" ) );
		temp.setServerName( "dbserver"  );
		temp.setDatabaseName( ApplicationProperties.getProperty( "DATABASE" ) );
		
		//System.out.println("[DbUtil.getConnection] - Prelievo connessione dal DB " + ApplicationProperties.getProperty( "DATABASE" ) +  
						   //" della macchina " + ApplicationProperties.getProperty( "dbserver" ) );
		
		datasource = temp;
	}*/
	

	
	public static void createDataSource(String nomeDb,String userName,String password,String ipAddress)
	{
		DataSource temp = new DataSource();
		
		/*temp.setUser( userName);
		temp.setPassword(password);
		temp.setServerName(ipAddress);
		temp.setDatabaseName(nomeDb );
		*/
		datasource = temp;
	}
	
/*	public static void createDataSourceReports(String nomeDb,String userName,String password,String ipAddress)
	{
		Jdbc3PoolingDataSource temp = new Jdbc3PoolingDataSource();
		
		temp.setUser( userName);
		temp.setPassword(password);
		temp.setServerName(ipAddress);
		temp.setDatabaseName(nomeDb );
		
		datasource = temp;
	}*/
	
	public static void createDataSourceJDBC(String nomeDb,String userName,String password,String ipAddress)
	{
		logger.info("Inizio createDataSourceJDBC");
		
		DataSource temp = new DataSource();
		
		/*temp.setUser( userName);
		temp.setPassword(password);
		temp.setServerName(ipAddress);
		temp.setDatabaseName(nomeDb );
		*/
		datasource = temp;
		
		logger.info("Fine createDataSourceJDBC");
	}
	
	public static void destroyDataSource()
	{
		if( datasource != null )
		{
			datasource.close();
		}
	}
/*	public static Connection getConnectionCanina() throws SQLException
	{
		
		
		if( datasource == null )
		{
			createDataSource();
		}
		
		return (datasource == null) ? (null) : (datasource.getConnection());
		
		
		
	}*/
	
	/*public static Connection getConnectionReports() throws SQLException
	{
		
		
		if( datasource == null )
		{
			createDataSourceReports();
		}
		
		return (datasource == null) ? (null) : (datasource.getConnection());
		
		
		
	}*/
	
	public static Connection getConnection() throws SQLException
	{
		
		/*
		if( datasource == null )
		{
			createDataSource();
		}
		
		return (datasource == null) ? (null) : (datasource.getConnection());
		*/
		
		return GestoreConnessioni.getConnection();
	}
	
	public static Connection getConnection(String nomeDb,String userName,String password,String ipAddress) throws SQLException
	{
		if( datasource == null )
		{
			createDataSource(nomeDb, userName, password, ipAddress);
		}
		numConn++;
		System.out.println("## Aperta conn -- CONN TOTALI: " +numConn);
		return (datasource == null) ? (null) : (datasource.getConnection());
	}
	
	
	public static Connection ottieniConnessioneJDBC(String dbUser, String dbPwd, String dbHost, String dbName) throws SQLException{
		if( datasource == null )
		{
			createDataSourceJDBC(dbName, dbUser, dbPwd, dbHost);
		}
		
		return (datasource == null) ? (null) : (datasource.getConnection());
		
		
	}

	public static void close(ResultSet rs, Statement st)
	{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception e) { }
		}
		
		if (st != null) {
			try {
				st.clearWarnings();
				st.close();
			}
			catch (Exception e) { }
		}
	}

	public static void close(ResultSet rs)
	{
		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception e) { }
		}
	}

	public static void close(Statement st)
	{
		if (st != null) {
			try {
				st.clearWarnings();
				st.close();
			}
			catch (SQLException e) { }
		}
	}

	public static void close(PreparedStatement stat, Connection conn) {
		if (stat != null) {
			try {
				stat.close();
				stat.clearWarnings();
			}
			catch (Exception e) { }
		}
		
		if (conn != null) {
			GestoreConnessioni.freeConnection(conn);
		}
	}

	public static void close(ResultSet res, PreparedStatement stat, Connection conn)
	{
		close( res );
		close( stat, conn );
	}
	
	public static void chiudiConnessioneJDBC(ResultSet rs, PreparedStatement pst, Connection conn) {
		try{
		if(rs != null){
			rs.close();
		}
		chiudiConnessioneJDBC(pst, conn);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void chiudiConnessioneJDBC(PreparedStatement pst, Connection conn) {
		try{
		if(pst != null){
			pst.close();
		}
		if(conn != null){
			conn.close();
		}
		datasource = null;
		numConn--;
		System.out.println("** CHIUSA conn -- CONN TOTALI: " +numConn);
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public static void chiudiConnessioneJDBC(ResultSet rs, CallableStatement pst, Connection conn) {

		try{
			if(pst != null){
		
			pst.close();
			}
		
		if(conn != null){
			conn.close();
		}
		if(rs != null){
			rs.close();
		}
		datasource = null;
		numConn--;
		System.out.println("** CHIUSA conn -- CONN TOTALI: " +numConn);
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	
public static ArrayList  <HashMap<String, String>> getTabelleCampiFk (String nomeTabella, String nomeCampo, String tabellaIlikeDaEscludere) throws Exception{
		
		
		ArrayList <HashMap<String, String>> toReturn = new ArrayList <HashMap<String, String>>();
		Connection db = GestoreConnessioni.getConnection();
		
		StringBuffer sql = new StringBuffer();
				
				
				sql.append("SELECT pc1.relname as tabella_da_aggiornare, pga1.attname as colonna_padre, pga2.attname as colonna_da_aggiornare "+
				"FROM pg_class pc1, pg_class pc2, pg_constraint, pg_attribute pga1, pg_attribute pga2 "+
				"WHERE pc1.relname in"+
				"  ("+
        "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema','pg_catalog')"+
    ")"+
    	"AND pg_constraint.conrelid = pc1.oid "+
    	"AND pc2.relkind = 'r' AND pc2.oid = pg_constraint.confrelid "+
    	"AND pga1.attnum = pg_constraint.confkey[1] " +
    	"AND pga1.attrelid = pc2.oid "+
    	"AND pga2.attnum = pg_constraint.conkey[1] "+
    	"AND pga2.attrelid = pc1.oid "+
    	"AND pga1.attname = ? "+
    	"AND pc2.relname = ? ");
				
			if (tabellaIlikeDaEscludere != null && !("").equals(tabellaIlikeDaEscludere))	
				sql.append("AND pc1.relname not ilike ? ");
				
				PreparedStatement pst = db.prepareStatement(sql.toString());
		
		int i = 0;
		
		pst.setString(++i, nomeCampo);
		pst.setString(++i, nomeTabella);
		if (tabellaIlikeDaEscludere != null && !("").equals(tabellaIlikeDaEscludere))	
			pst.setString(++i, tabellaIlikeDaEscludere+"%");
		
		ResultSet rsResultSet = pst.executeQuery();
		
		
		while (rsResultSet.next()){
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(rsResultSet.getString("tabella_da_aggiornare"), rsResultSet.getString("colonna_da_aggiornare"));
			
			toReturn.add(map);
			
		}
		
		GestoreConnessioni.freeConnection(db);
		
		return toReturn;
		
	}
}
