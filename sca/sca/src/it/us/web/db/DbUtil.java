/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



public class DbUtil
{
	private static Logger logger = Logger.getLogger("MainLogger");
	//private static DataSource datasource = null;
	 public final static int POSTGRESQL = 1;
	  public final static int MSSQL = 2;
	  public final static int ORACLE = 3;
	  public final static int FIREBIRD = 4;
	  public final static int DAFFODILDB = 5;
	  public final static int DB2 = 6;
	  public final static int MYSQL = 7;
	  public final static int DERBY = 8;
	  public final static int INTERBASE = 9;
	  
	  public static DataSource dsStorico=null;
	  
/*	public static void createDataSource()
	{
		DataSource temp = new DataSource();
		
		/*temp.setUser( ApplicationProperties.getProperty( "USERNAME" ) );
		temp.setPassword( ApplicationProperties.getProperty( "PASSWORD" ) );
		temp.setServerName( ApplicationProperties.getProperty( "IP" )  );
		
		temp.setDatabaseName( ApplicationProperties.getProperty( "DATABASE" ) );
		
		System.out.println("CONNESSIONE NORMALE [DbUtil.getConnection] - Prelievo connessione dal DB " + ApplicationProperties.getProperty( "DATABASE" ) +  
						   " della macchina " + ApplicationProperties.getProperty( "dbserver" ) );
		
		datasource = temp;
	}
	*/
	public static Connection getConnessioneStorico() throws SQLException
	{
		/*Jdbc3PoolingDataSource temp = new Jdbc3PoolingDataSource();
		
		temp.setUser( ApplicationProperties.getProperty( "USERNAME_STORICO" ) );
		//System.out.println("USERNAME_STORICO" + ApplicationProperties.getProperty( "USERNAME_STORICO" ));
		temp.setPassword( ApplicationProperties.getProperty( "PASSWORD_STORICO" ) );
		//System.out.println("PASSWORD_STORICO" + ApplicationProperties.getProperty( "PASSWORD_STORICO" ));
		temp.setServerName( ApplicationProperties.getProperty( "IP_STORICO" )  );
		//System.out.println("IP_STORICO" + ApplicationProperties.getProperty( "IP_STORICO" ));
		temp.setDatabaseName( ApplicationProperties.getProperty( "DATABASE_STORICO" ) );
		//System.out.println("IP_STORICO" + ApplicationProperties.getProperty( "DATABASE_STORICO" ));
		//System.out.println("connessione recuperata:" + temp.getConnection());
		return temp.getConnection();
		*/
				if (dsStorico == null) {

			Context ctx = null;
			try {
				ctx = new InitialContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				dsStorico = (DataSource) ctx.lookup("java:comp/env/jdbc/storico");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Connection cc= dsStorico.getConnection();
		getInfo(dsStorico,"APERTURA");
		return cc;
	}
	
	
	private static void getInfo(DataSource d, String motivo){
		try{        
		        
		        System.out.println("CP [STORICO SCA] - AZIONE = "+motivo+" - " + d.getUrl().substring(d.getUrl().lastIndexOf("/")+1)  +" **INITIAL SIZE:  "+d.getInitialSize() + " **ACTIVE SIZE:  "+d.getActive() + "" +
		                        " **POOL SIZE:  "+d.getPoolSize() +" **IDLE SIZE:  "+d.getIdle() );

		}catch (Exception e) {
		        // TODO: handle exception
		}
	}
	
	 public static String getSequenceName(Connection db, String sequenceName) {
		    int typeId = getType(db);
		    switch (typeId) {
		      case FIREBIRD:
		      case INTERBASE: 
		    	  // interbase actually allows 64 character names, but since we are using the same db scripts...
		        if (sequenceName.length() > 31) {
		          String seqPart1 = sequenceName.substring(0, 13);
		          String seqPart2 = sequenceName.substring(14);
		          sequenceName = seqPart1 + "_" + seqPart2.substring(seqPart2.length() - 17);
		        }
		        break;
		      case DAFFODILDB:
		        break;
		      case ORACLE:
		        if (sequenceName.length() > 30) {
		          String seqPart1 = sequenceName.substring(0, 13);
		          String seqPart2 = sequenceName.substring(14);
		          sequenceName = seqPart1 + "_" + seqPart2.substring(seqPart2.length() - 16);
		        }
		        break;
		      case DB2:
		        if (sequenceName.length() > 30) {
		          String seqPart1 = sequenceName.substring(0, 13);
		          String seqPart2 = sequenceName.substring(14);
		          sequenceName = seqPart1 + "_" + seqPart2.substring(seqPart2.length() - 16);
		        }
		        break;
		      default:
		        break;
		    }
		    return sequenceName;
		  }

	 public static int getNextSeqTipo(Connection db, String origSequenceName) throws SQLException {
		    
		    int id = -1;
		    Statement st = db.createStatement();
		    ResultSet rs = null;
		    String sequenceName = getSequenceName(db, origSequenceName);

		        rs = st.executeQuery(
		            "SELECT nextval ('" + sequenceName + "');");
		        
		    if (rs.next()) {
		      id = rs.getInt(1);
		    }
		    rs.close();
		    st.close();
		    return id;
		  }
	public static int getType(Connection db) {
	    String databaseName = db.getClass().getName();
	    if (databaseName.indexOf("postgresql") > -1 || databaseName.equalsIgnoreCase("org.aspcfs.utils.CustomConnection")) {
	      return POSTGRESQL;
	    } else if ("net.sourceforge.jtds.jdbc.ConnectionJDBC3".equals(
	        databaseName)) {
	      return MSSQL;
	    } else if ("net.sourceforge.jtds.jdbc.TdsConnectionJDBC3".equals(
	        databaseName)) {
	      return MSSQL;
	    } else if (databaseName.indexOf("sqlserver") > -1) {
	      return MSSQL;
	    } else if ("net.sourceforge.jtds.jdbc.TdsConnection".equals(databaseName)) {
	      return MSSQL;
	    } else if ("org.firebirdsql.jdbc.FBConnection".equals(databaseName)) {
	      return FIREBIRD;
	    } else if ("org.firebirdsql.jdbc.FBDriver".equals(databaseName)) {
	      return FIREBIRD;
	    } else if ("oracle.jdbc.driver.OracleConnection".equals(databaseName)) {
	      return ORACLE;
	    } else if (databaseName.indexOf("oracle") > -1) {
	      return ORACLE;
	    } else
	    if ("in.co.daffodil.db.jdbc.DaffodilDBConnection".equals(databaseName)) {
	      return DAFFODILDB;
	    } else if (databaseName.indexOf("db2") > -1) {
	      return DB2;
	    } else if (databaseName.indexOf("mysql") > -1) {
	      return MYSQL;
	    } else if (databaseName.indexOf("derby") > -1) {
	      return DERBY; 
	    } else if ( "interbase.interclient.Connection".equals( databaseName ) ) {
	      return INTERBASE;
	    } else {
	      System.out.println("DatabaseUtils-> Unkown Connection Class: " + databaseName);
	      return -1;
	    }
	  }
	
	/*
	public static void destroyDataSource()
	{
		if( datasource != null )
		{
			datasource.close();
		}
	}
*/
	
	public static Connection getConnection() throws SQLException
	{
		/*
		
		if( datasource == null )
		{
			createDataSource();
		}
		
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
			ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/guc");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ds.getConnection();
		
		
	}
	
	public static void close(Connection db) throws SQLException
	{
		if(db!=null)
		{
			db.close();
			getInfo(dsStorico,"CHIUSURA");
		}
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

	public static void close(PreparedStatement stat, Connection conn) throws SQLException {
		if (stat != null) {
			try {
				stat.close();
				stat.clearWarnings();
			}
			catch (Exception e) { }
		}
		
		if (conn != null) {
			conn.close();
		}
	}

	public static void close(ResultSet res, PreparedStatement stat, Connection conn) throws SQLException
	{
		close( res );
		close( stat, conn );
	}
	


	
	
	
}
