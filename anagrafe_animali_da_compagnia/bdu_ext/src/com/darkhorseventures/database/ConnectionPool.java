/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.darkhorseventures.database;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SessionManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.MethodsUtils;

import com.darkhorseventures.framework.actions.ActionContext;

import java.sql.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * A class for recycling, and managing JDBC connections. <P>
 * <p/>
 * Taken from Core Servlets and JavaServer Pages http://www.coreservlets.com/.
 * &copy; 2000 Marty Hall; may be freely used or adapted. <P>
 * <p/>
 * The ConnectionPool class requires a ConnectionElement to be supplied for
 * each request to getConnection(connectionElement). This allows the
 * ConnectionPool to work in an ASP setting in which multiple databases are
 * cached by the pool.<P>
 * <p/>
 * Busy connections are stored with a connection key and a connection element
 * value.<br>
 * .free(connection) removes the connection from busy and then puts it on the
 * available list with a new connection element. <P>
 * <p/>
 * Available connections are stored with a connection element and connection
 * value.<br>
 * .getConnection(connectionElement) returns the connection, removes it from
 * the available connections, and then puts it on the busy list with a new
 * connection element.
 *
 * @author Matt Rajkowski
 * @version $Id: ConnectionPool.java,v 1.11 2003/01/13 14:42:24 mrajkowski Exp
 *          $
 * @created December 12, 2000
 */


public class ConnectionPool {

  private final static Logger log = Logger.getLogger(com.darkhorseventures.database.ConnectionPool.class);
  
  
  private DataSource datasource = null;
  
  
  public DataSource getDatasource() {
	return datasource;
}
  
  
  
  
  public ConnectionPool()  {
		super();
		
	}



public ConnectionPool(String datasourceName) throws NamingException {
	super();
	InitialContext cxt = new InitialContext();
	datasource = (DataSource) cxt.lookup(datasourceName);
	//System.out.println("AAAAA  "+ datasource.getName());
	
	
}









  
//private void getInfo(int i){
//	//i = 0 apertura
//	//i = 1 chiusura
//try{	
//	
//	System.out.println("CP  " + i +"   " + this.getDatasource().getUrl() +" **INITIAL SIZE:  "+this.getDatasource().getInitialSize() + " **ACTIVE SIZE:  "+this.getDatasource().getActive() + "" +
//			" **POOL SIZE:  "+this.getDatasource().getPoolSize() +" **IDLE SIZE:  "+this.getDatasource().getIdle() );
//
//}catch (Exception e) {
//	// TODO: handle exception
//}
//}


public void setDatasource(DataSource datasource) {
	this.datasource = datasource;
}


public String getDbName(){
	  
	 String urlToReturn = getUrl();
	 int lastSlash = urlToReturn.lastIndexOf("/");
	 
	 urlToReturn = urlToReturn.substring(lastSlash+1);
	
	 return urlToReturn;
}



public String getUrl(){
	 
	  String urlToReturn = "";
	  Connection con = null;
	 try {
		 
//		con =  getConnection("");
//	//	ConnectionPool pool = new ConnectionPool("java:comp/env/jdbc/bdu");
//		 urlToReturn = con.getMetaData().getURL();
	urlToReturn = this.getDatasource().getUrl();
//			con =  getConnection("");
//			urlToReturn = con.getMetaData().getURL();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
	//	this.free(con);
	}
	 
	 return urlToReturn;
  }
  
  
  
  private void setDataSource(String url) {
	  
	  try {
	      // Look up the JNDI data source only once at init time
		  InitialContext cxt = new InitialContext();
		  
		  datasource = (DataSource) cxt.lookup("java:comp/env/jdbc/bduM");
		  
		  
		 // datasource.getPoolProperties().setRemoveAbandoned(false);
//		  Context envContext  = (Context)cxt.lookup("java:/comp/env/");
//		  datasource = (DataSource)envContext.lookup(url);
	      
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	  
	  
	  
  }



  
  public  Connection getConnection(ActionContext context) throws SQLException {
	  
	  Connection con = null;
		
		try{
			
			
			
			
		//	this.setDataSource(url);
			con = this.datasource.getConnection();	
			Thread t = Thread.currentThread();
//			getInfo(con,0,  MethodsUtils.getNomeMetodo(t), context);
			getInfo(con,0,datasource,MethodsUtils.getNomeMetodo(t),context);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore getConnection()");
		}
		
		
		return con;
	  
  }
  
  
  public void free (Connection con, ActionContext context){
	  closeConnection(con, context);
  }
  
  
  public void getInfo(Connection db,int tipo,DataSource ds,String action,ActionContext ctx){
	  try{	

		  
		  
		  /**
		   * 	QUESTA PARTE E' STATA COMMENTATA IN QUANTO IL CONTEXT IN INPUT PER LE CHIAMATE DWR (GESTORE CONNESSIONI) 
		   *  	VIENE SOVRASCRITTO OGNI VOLTA. IL CONTEXT è UTILIZZATO SOLO PER IL RECUPERO DELLA USERNAME
		   */
		  /*
		  if (ctx!= null && ctx.getSession()!=null)
	  	  {
			  UserBean u = (UserBean)ctx.getSession().getAttribute("User");
	  	
	  		log.info("[CONTESTO : BDU - USER : "+u.getUsername()+"]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );	
	  	}
	  	else
	  	{
	  		log.info("[CONTESTO : BDU - USER : ctx is null or session expires]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );	
	  	}
		  */
	  		log.info("[CONTESTO : BDU]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );	

		  
	 

	  }catch (Exception e) {
	  	// TODO: handle exception
	  }
	  }
  
  public void getInfo(Connection db,int tipo,DataSource ds,String action,UserBean u){
	  try{	

		  
		  if (u!=null)
	  	{
			 
	  	
	  		log.info("[CONTESTO : BDU - USER : "+u.getUsername()+"]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );	
	  	}
	  	else
	  	{
	  		//log.info("[CONTESTO : BDU - ] ctx is null or session expires");
	  		log.info("[CONTESTO : BDU - USER : ctx is null or session expires]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );	
	  	}
	 

	  }catch (Exception e) {
	  	// TODO: handle exception
	  }
	  }
  
  
  
  
  public void closeConnection (Connection con, ActionContext context, PreparedStatement st, ResultSet rs){
		try {

			if (rs != null)
				rs.close();

			closeConnection(con, context, st);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Return to connection pool
	 
  }
  
  
  public void closeConnection (Connection con, ActionContext context, PreparedStatement st){
		try {

			if (st != null)
				st.close();

			closeConnection(con, context);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Return to connection pool
	 
  }
  
  
  public void closeConnection (Connection con, ActionContext context){
	  try {
		

		Thread t = Thread.currentThread();
	if (con != null){
		con.close();
		
	}else{
		System.out.println("### CONNESSIONE ARRIVATA CHIUSA");
	}
		getInfo(con,1,datasource,  MethodsUtils.getNomeMetodo(t), context);
		con = null;  // Make sure we don't close it twice
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} // Return to connection pool
	  
	 
  }
  

  /**
   * When a connection is needed, ask the class for the next available, if the
   * max connections has been reached then a thread will be spawned to wait for
   * the next available connection.
   *
   * @param requestElement Description of the Parameter
   * @return The connection value
   * @throws SQLException Description of the Exception
   */
//  public synchronized Connection getConnection(ConnectionElement requestElement) throws SQLException {
//    //Get an available matching connection
//	if (System.getProperty("DEBUG") != null) {
//	  System.out.println("Avilab size: " +availableConnections.size());
//	}
//    if (!availableConnections.isEmpty()) {
//      Enumeration e = availableConnections.keys();
//      while (e.hasMoreElements()) {
//        ConnectionElement thisElement = (ConnectionElement) e.nextElement();
//        if (thisElement.getUrl().equals(requestElement.getUrl())) {
//          try {
//            //Try to get the connection from the pool, it may have been
//            //recycled before it could be retrieved
//            Connection existingConnection = this.getAvailableConnection(
//                thisElement);
//            if (existingConnection == null) {
//              notifyAll();
//              return (getConnection(requestElement));
//            }
//            //See if connection is open, else recycle it
//            if (existingConnection.isClosed()) {
//              existingConnection = null;
//              notifyAll();
//              return (getConnection(requestElement));
//            } else if (testConnections) {
//              //See if connection is good, else recycle it
//              try {
//                String testString = "SELECT 1";
//                if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.DB2) {
//                  testString = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
//                } else if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
//                  testString = "SELECT 1 FROM DUAL";
//                }
//                PreparedStatement pst = existingConnection.prepareStatement(
//                    testString);
//                ResultSet rs = pst.executeQuery();
//                rs.next();
//                rs.close();
//                pst.close();
//              } catch (SQLException sqe) {
//                existingConnection = null;
//                notifyAll();
//                return (getConnection(requestElement));
//              }
//            }
//            //Go with the connection
//            requestElement.renew();
//            busyConnections.put(existingConnection, requestElement);
//            // Default to case insensitive order by clauses
//            if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
//              //PreparedStatement pst = existingConnection.prepareStatement(
//              //      "ALTER SESSION SET nls_comp=ansi");
//              //pst.execute();
//              //pst.close();
//              PreparedStatement pst = existingConnection.prepareStatement(
//                    "ALTER SESSION SET nls_sort=binary_ci");
//              pst.execute();
//              pst.close();
//            }
//            return (existingConnection);
//          } catch (NullPointerException npe) {
//            return (getConnection(requestElement));
//          } catch (java.lang.ClassCastException cce) {
//            throw new SQLException("Database connection error");
//          }
//        }
//      }
//      // A matching connection was not found so make a new connection, or
//      // remove an idle available connection then try again...
//      if (!connectionPending && (totalConnections() == maxConnections)) {
//        e = availableConnections.keys();
//        if (e.hasMoreElements()) {
//          ConnectionElement thisElement = (ConnectionElement) e.nextElement();
//          try {
//            Connection oldConnection = this.getAvailableConnection(
//                thisElement);
//            if (oldConnection != null) {
//              oldConnection.close();
//            }
//            oldConnection = null;
//          } catch (SQLException se) {
//            //just don't want to return an error to the app just yet
//          }
//        }
//        e = null;
//        return (getConnection(requestElement));
//      }
//    }
//    // Three possible cases a connection wasn't found:
//    // 1) You haven't reached maxConnections limit. So
//    //    establish one in the background if there isn't
//    //    already one pending, then wait for
//    //    the next available connection (whether or not
//    //    it was the newly established one).
//    // 2) You reached maxConnections limit and waitIfBusy
//    //    flag is false. Throw SQLException in such a case.
//    // 3) You reached maxConnections limit and waitIfBusy
//    //    flag is true. Then do the same thing as in second
//    //    part of step 1: wait for next available connection.
//    if ((totalConnections() < maxConnections) && !connectionPending) {
//      makeBackgroundConnection(requestElement);
//    } else if (!waitIfBusy) {
//      throw new SQLException("Connection limit reached");
//    }
//    // Wait for either a new connection to be established
//    // (if you called makeBackgroundConnection) or for
//    // an existing connection to be freed up.
//    try {
//      wait();
//    } catch (InterruptedException ie) {
//    }
//    // Someone freed up a connection, so try again.
//    return (getConnection(requestElement));
//  }

  
  
 //VAM 
  
//  public synchronized Connection getConnectionVam(ConnectionElement requestElement) throws SQLException {
//	  
//	  this.setSecondaryConnection(true); // MI SERVE PER SAPERE IN RUN SE DEVO RIEMPIRE availableConnectionsVam o availableConnections
//	    //Get an available matching connection
//		//  System.out.println("Avilab size VAM: " +availableConnectionsVam.size());
//	    if (!availableConnectionsVam.isEmpty()) {
//	      Enumeration e = availableConnectionsVam.keys();
//	      while (e.hasMoreElements()) {
//	        ConnectionElement thisElement = (ConnectionElement) e.nextElement();
//	        if (thisElement.getUrl().equals(requestElement.getUrl())) {
//	          try {
//	            //Try to get the connection from the pool, it may have been
//	            //recycled before it could be retrieved
//	            Connection existingConnection = this.getAvailableConnectionVam(
//	                thisElement);
//	            if (existingConnection == null) {
//	              notifyAll();
//	              return (getConnectionVam(requestElement));
//	            }
//	            //See if connection is open, else recycle it
//	            if (existingConnection.isClosed()) {
//	              existingConnection = null;
//	              notifyAll();
//	              return (getConnectionVam(requestElement));
//	            } else if (testConnections) {
//	              //See if connection is good, else recycle it
//	              try {
//	                String testString = "SELECT 1";
//	                if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.DB2) {
//	                  testString = "SELECT 1 FROM SYSIBM.SYSDUMMY1";
//	                } else if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
//	                  testString = "SELECT 1 FROM DUAL";
//	                }
//	                PreparedStatement pst = existingConnection.prepareStatement(
//	                    testString);
//	                ResultSet rs = pst.executeQuery();
//	                rs.next();
//	                rs.close();
//	                pst.close();
//	              } catch (SQLException sqe) {
//	                existingConnection = null;
//	                notifyAll();
//	                return (getConnectionVam(requestElement));
//	              }
//	            }
//	            //Go with the connection
//	            requestElement.renew();
//	            busyConnectionsVam.put(existingConnection, requestElement);
//	            // Default to case insensitive order by clauses
//	            if (DatabaseUtils.getType(existingConnection) == DatabaseUtils.ORACLE) {
//	              //PreparedStatement pst = existingConnection.prepareStatement(
//	              //      "ALTER SESSION SET nls_comp=ansi");
//	              //pst.execute();
//	              //pst.close();
//	              PreparedStatement pst = existingConnection.prepareStatement(
//	                    "ALTER SESSION SET nls_sort=binary_ci");
//	              pst.execute();
//	              pst.close();
//	            }
//	            return (existingConnection);
//	          } catch (NullPointerException npe) {
//	            return (getConnectionVam(requestElement));
//	          } catch (java.lang.ClassCastException cce) {
//	            throw new SQLException("Database connection error");
//	          }
//	        }
//	      }
//	      // A matching connection was not found so make a new connection, or
//	      // remove an idle available connection then try again...
//	      if (!connectionPending && (totalConnectionsVam() == maxConnections)) {
//	        e = availableConnectionsVam.keys();
//	        if (e.hasMoreElements()) {
//	          ConnectionElement thisElement = (ConnectionElement) e.nextElement();
//	          try {
//	            Connection oldConnection = this.getAvailableConnectionVam(
//	                thisElement);
//	            if (oldConnection != null) {
//	              oldConnection.close();
//	            }
//	            oldConnection = null;
//	          } catch (SQLException se) {
//	            //just don't want to return an error to the app just yet
//	          }
//	        }
//	        e = null;
//	        return (getConnectionVam(requestElement));
//	      }
//	    }
//	    // Three possible cases a connection wasn't found:
//	    // 1) You haven't reached maxConnections limit. So
//	    //    establish one in the background if there isn't
//	    //    already one pending, then wait for
//	    //    the next available connection (whether or not
//	    //    it was the newly established one).
//	    // 2) You reached maxConnections limit and waitIfBusy
//	    //    flag is false. Throw SQLException in such a case.
//	    // 3) You reached maxConnections limit and waitIfBusy
//	    //    flag is true. Then do the same thing as in second
//	    //    part of step 1: wait for next available connection.
//	    if ((totalConnectionsVam() < maxConnections) && !connectionPending) {
//	      makeBackgroundConnection(requestElement);
//	    } else if (!waitIfBusy) {
//	      throw new SQLException("Connection limit reached");
//	    }
//	    // Wait for either a new connection to be established
//	    // (if you called makeBackgroundConnection) or for
//	    // an existing connection to be freed up.
//	    try {
//	      wait();
//	    } catch (InterruptedException ie) {
//	    }
//	    // Someone freed up a connection, so try again.
//	    return (getConnectionVam(requestElement));
//	  }
//
//  
  
  
//  
//
//  /**
//   * Prevents multiple methods from getting the same connection
//   *
//   * @param thisElement Description of the Parameter
//   * @return The availableConnection value
//   * @throws SQLException Description of the Exception
//   */
//  private synchronized Connection getAvailableConnection(ConnectionElement thisElement) throws SQLException {
//    Connection existingConnection =
//        (Connection) availableConnections.get(thisElement);
//    if (existingConnection != null) {
//      availableConnections.remove(thisElement);
//    }
//    return existingConnection;
//  }
//  
//  
//  /**
//   * Prevents multiple methods from getting the same connection
//   *
//   * @param thisElement Description of the Parameter
//   * @return The availableConnection value
//   * @throws SQLException Description of the Exception
//   */
////  private synchronized Connection getAvailableConnectionVam(ConnectionElement thisElement) throws SQLException {
////    Connection existingConnection =
////        (Connection) availableConnectionsVam.get(thisElement);
////    if (existingConnection != null) {
////      availableConnectionsVam.remove(thisElement);
////    }
////    return existingConnection;
////  }
//
//
//  /**
//   * Returns whether max connections has been reached for debugging
//   *
//   * @return The MaxStatus value
//   * @since 1.5
//   */
//  public boolean getMaxStatus() {
//    if (busyConnections.size() == maxConnections) {
//      return (true);
//    } else {
//      return (false);
//    }
//  }
//
//
//  /**
//   * Displays the date/time when the ConnectionPool was created
//   *
//   * @return The StartDate value
//   * @since 1.2
//   */
//  public String getStartDate() {
//    return (startDate.toString());
//  }
//
//
//  /**
//   * Main processing method for the ConnectionPool object, only 1 thread runs
//   * at a time since a connection can take a few seconds. While the user waits,
//   * if a connection is freed by another task first, then they will get that
//   * connection instead.
//   *
//   * @since 1.0
//   */
//  public void run() {
//    // Make the connection
//  //  try {
////      ConnectionElement thisElement = new ConnectionElement();
////      thisElement.setUrl(this.url);
////      thisElement.setUsername(this.username);
////      thisElement.setPassword(this.password);
////      thisElement.setDriver(this.driver);
/////*      ConnectionElement thisElementVam = new ConnectionElement();
////      thisElementVam.setUrl("jdbc:postgresql://172.16.3.250:5432/vam");
////      thisElementVam.setUsername(this.username);
////      thisElementVam.setPassword(this.password);
////      thisElementVam.setDriver(this.driver);*/
////      Connection connection = makeNewConnection(thisElement);
////      /*Connection connectionVam = makeNewConnection(thisElementVam);*/
////      synchronized (this) {
////        if (connection != null) {
////        	
////        	availableConnections.put(thisElement, connection);
//////        	if (this.isSecondaryConnection()){
//////        	//	availableConnectionsVam.put(thisElement, connection);
//////        	}else{
//////        		availableConnections.put(thisElement, connection);
//////        	}
////          log.debug("New: " + thisElement.getUrl() + " " + this.toString());
////        } else {
////          log.debug("Database connection could not be created: " + thisElement.getUrl() + " " + this.toString());
////          //NOTE: This is currently here because the getConnection() would be broken
////          //without it.  When getConnection() grabs this it will just recycle it right
////          //away.  Needs to be fixed since a NullPointer exception would be raised.
////          
////          availableConnections.put(thisElement, "Database Error");
//////          if (this.isSecondaryConnection()){
//////        	  availableConnections.put(thisElement, "Database Error");
//////      	}else{
//////      		 availableConnections.put(thisElement, "Database Error");
//////      	}
////          
////          
////         
////        }
////        
////        
////   /*     if (connectionVam != null) {
////            availableConnectionsVam.put(thisElementVam, connectionVam);
////            log.debug("New: " + thisElement.getUrl() + " " + this.toString());
////          } else {
////            log.debug("Database connection could not be created: " + thisElement.getUrl() + " " + this.toString());
////            //NOTE: This is currently here because the getConnection() would be broken
////            //without it.  When getConnection() grabs this it will just recycle it right
////            //away.  Needs to be fixed since a NullPointer exception would be raised.
////            availableConnections.put(thisElement, "Database Error");
////          }
////        */
////        
////        connectionPending = false;
////      //  secondaryConnection = false;
////        notifyAll();
////      }
////    } catch (Exception e) {
////      log.error("Thread Error: " + e.toString());
////      // SQLException or OutOfMemory
////      // Give up on new connection and wait for existing one
////      // to free up.
////    }
//  }
//
//
//  /**
//   * When finished with the connection, don't close it, free the connection and
//   * it will be reused by another request. If it's closed then remove the
//   * reference to it and another will just have to be opened.
//   *
//   * @param connection Description of Parameter
//   * @since 1.0
//   */
//  public synchronized void free(Connection connection) {
//    if (connection != null) {
//      ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
//          connection);
//      if (thisElement == null) {
//        log.info("Connection has already been returned to pool");
//      } else {
//        busyConnections.remove(connection);
//        try {
//          if (forceClose) {
//            if (!connection.isClosed()) {
//              connection.close();
//              if (!forceClose) {
//                log.info("Removed a possibly dead busy connection");
//              }
//            }
//          }
//          if (connection.isClosed()) {
//            connection = null;
//          } else {
//            availableConnections.put(
//                new ConnectionElement(
//                    thisElement.getUrl(),
//                    thisElement.getUsername(), thisElement.getPassword()), connection);
//          }
//        } catch (SQLException e) {
//          connection = null;
//        }
//        // Wake up threads that are waiting for a connection
//        notifyAll();
//      }
//    }
//  }
//  
////  public synchronized void freeVam(Connection connection) {
////	    if (connection != null) {
////	      ConnectionElement thisElement = (ConnectionElement) busyConnectionsVam.get(
////	          connection);
////	      if (thisElement == null) {
////	        log.info("Connection has already been returned to pool");
////	      } else {
////	        busyConnectionsVam.remove(connection);
////	        try {
////	          if (forceClose) {
////	            if (!connection.isClosed()) {
////	              connection.close();
////	              if (!forceClose) {
////	                log.info("Removed a possibly dead busy connection");
////	              }
////	            }
////	          }
////	          if (connection.isClosed()) {
////	            connection = null;
////	          } else {
////	            availableConnectionsVam.put(
////	                new ConnectionElement(
////	                    thisElement.getUrl(),
////	                    thisElement.getUsername(), thisElement.getPassword()), connection);
////	          }
////	        } catch (SQLException e) {
////	          connection = null;
////	        }
////	        // Wake up threads that are waiting for a connection
////	        notifyAll();
////	      }
////	    }
////	  }
//
//
//  /**
//   * Description of the Method
//   *
//   * @param connection Description of the Parameter
//   */
//  public void renew(Connection connection) {
//    if (connection != null) {
//      ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
//          connection);
//      thisElement.renew();
//    }
//  }
//
//
//  /**
//   * Returns total connections allocated
//   *
//   * @return Description of the Returned Value
//   * @since 1.0
//   */
//  public int totalConnections() {
//    return (availableConnections.size() + busyConnections.size());
//  }
//  
////  public int totalConnectionsVam() {
////	    return (availableConnectionsVam.size() + busyConnectionsVam.size());
////	  }
//
//
//  /**
//   * Close all the connections. Use with caution: be sure no connections are in
//   * use before calling. Note that you are not <I>required</I> to call this
//   * when done with a ConnectionPool, since connections are guaranteed to be
//   * closed when garbage collected. But this method gives more control
//   * regarding when the connections are closed.
//   *
//   * @since 1.1
//   */
//  public synchronized void closeAllConnections() {
//    log.debug("Status: " + this.toString());
//    log.debug("Closing available connections");
//    closeConnections(AVAILABLE_CONNECTION, availableConnections);
//    availableConnections.clear();
//    log.debug("Closing busy connections");
//    closeConnections(BUSY_CONNECTION, busyConnections);
//    busyConnections.clear();
//    
//    
//   // closeConnections(AVAILABLE_CONNECTION, availableConnectionsVam);
//  //  availableConnectionsVam.clear();
//   // log.debug("Closing busy connections");
//  //  closeConnections(BUSY_CONNECTION, busyConnectionsVam);
// //   busyConnectionsVam.clear();
//  }
//
//
//  /**
//   * Simple way to close just the busy connections
//   *
//   * @since 1.1
//   */
//  public synchronized void closeBusyConnections() {
//    closeConnections(1, busyConnections);
//    busyConnections = new Hashtable();
//    
//   // closeConnections(1, busyConnectionsVam);
//   // busyConnectionsVam = new Hashtable();
//  }
//
//
//  /**
//   * More debugging information... displays the current state of the
//   * ConnectionPool
//   *
//   * @return Description of the Returned Value
//   * @since 1.1
//   */
//  public String toString() {
//    String info =
//        "(avail=" + availableConnections.size() +
//        ", busy=" + busyConnections.size() +
//        ", max=" + maxConnections + ")";
//    return (info);
//  }
//
//
//  /**
//   * Cleans up the connection pool when destroyed, it's important to remove any
//   * open timers -- this does not appear to be automatically called so the
//   * application should call this to make sure the timer is stopped.
//   *
//   * @since 1.1
//   */
//  public void destroy() {
//    if (cleanupTimer != null) {
//      cleanupTimer.cancel();
//      cleanupTimer = null;
//      if (debug) {
//        log.debug("Timer shut down");
//        try {
//          Thread.sleep(2000);
//        } catch (InterruptedException e) {
//        }
//      }
//    }
//    log.debug("Stopped");
//  }
//
//
//  /**
//   * A timer to check and close idle connections
//   *
//   * @since 1.1
//   */
//  private void initializeCleanupTimer() {
//    // start checking after 5 seconds
//    int initialDelay = 5000;
//    // repeat every 5 seconds
//    int period = 5000;
//    if (cleanupTimer == null) {
//      cleanupTimer = new Timer();
//      TimerTask cleanupTask =
//          new TimerTask() {
//            /**
//             * Main processing method for the cleanupTask object
//             *
//             * @since 1.1
//             */
//            public void run() {
//              //Gets rid of idle connections
//              if (allowShrinking) {
//                cleanupAvailableConnections();
//              }
//              //Gets rid of deadlocked connections
//              cleanupBusyConnections();
//            }
//          };
//      cleanupTimer.scheduleAtFixedRate(cleanupTask, initialDelay, period);
//    }
//  }
//
//
//  /**
//   * Looks through the available connections and checks for anything that has
//   * been open longer than the time allowed
//   *
//   * @since 1.2
//   */
//  private synchronized void cleanupAvailableConnections() {
//    java.util.Date currentDate = new java.util.Date();
//    Enumeration e = availableConnections.keys();
//    while (e.hasMoreElements()) {
//      ConnectionElement thisElement = null;
//      try {
//        thisElement = (ConnectionElement) e.nextElement();
//        java.util.Date testDate = thisElement.getActiveDate();
//        Connection connection = (Connection) availableConnections.get(
//            thisElement);
//        if (connection.isClosed() || (testDate.getTime() < (currentDate.getTime() - maxIdleTime))) {
//          Connection expiredConnection = this.getAvailableConnection(
//              thisElement);
//          if (expiredConnection != null) {
//            expiredConnection.close();
//            log.debug("Removed: " + thisElement.getUrl() + " " + this.toString());
//            notify();
//          }
//          expiredConnection = null;
//        }
//        connection = null;
//      } catch (Exception sqle) {
//        if (thisElement != null) {
//          availableConnections.remove(thisElement);
//          //log.debug("Removed a null available connection");
//        }
//        notify();
//        //Ignore errors; garbage collect anyhow
//      }
//
//    }
//    e = null;
//  }
//
//
//  /**
//   * Looks through the busy connections and checks for anything that has been
//   * open longer than the time allowed
//   *
//   * @since 1.2
//   */
//  private synchronized void cleanupBusyConnections() {
//    try {
//      java.util.Date currentDate = new java.util.Date();
//      Enumeration e = busyConnections.keys();
//      while (e.hasMoreElements()) {
//        Connection connection = (Connection) e.nextElement();
//        ConnectionElement thisElement = (ConnectionElement) busyConnections.get(
//            connection);
//        if (thisElement != null) {
//          java.util.Date testDate = thisElement.getActiveDate();
//          if (connection.isClosed() ||
//              (thisElement.getAllowCloseOnIdle() &&
//              testDate.getTime() < (currentDate.getTime() - maxDeadTime))) {
//            //TODO: This is not synchronized and could error if this isn't really a closed connection
//            busyConnections.remove(connection);
//            connection.close();
//            connection = null;
//          }
//        }
//      }
//      e = null;
//    } catch (Exception sqle) {
//      // Ignore errors; garbage collect anyhow
//    }
//  }
//
//
//  /**
//   * Makes a connection to the database in a background thread.<p>
//   * <p/>
//   * You can't just make a new connection in the foreground when none are
//   * available, since this can take several seconds with a slow network
//   * connection. Instead, start a thread that establishes a new connection,
//   * then wait. You get woken up either when the new connection is established
//   * or if someone finishes with an existing connection.
//   *
//   * @param thisElement Description of the Parameter
//   * @since 1.0
//   */
//  private void makeBackgroundConnection(ConnectionElement thisElement) {
//    connectionPending = true;
//    try {
//      Thread connectThread = new Thread(this);
//      //NOTE: Currently thread safe due to connectionPending property
//      this.url = thisElement.getUrl();
//      this.username = thisElement.getUsername();
//      this.password = thisElement.getPassword();
//      this.driver = thisElement.getDriver();
//      
//      connectThread.start();
//    } catch (OutOfMemoryError oome) {
//      // Give up on new connection
//    }
//  }
//
//
//  /**
//   * This explicitly makes a new connection. Called in the foreground when
//   * initializing the ConnectionPool, and called in the background when
//   * running.
//   *
//   * @param thisElement Description of the Parameter
//   * @return Description of the Returned Value
//   * @since 1.0
//   */
//  private Connection makeNewConnection(ConnectionElement thisElement) {
//    try {
//      // Load database driver if not already loaded
//      Class.forName(thisElement.getDriver());
//      // Establish network connection to database
//      Connection connection =
//          DatabaseUtils.getConnection(
//              thisElement.getUrl(), thisElement.getUsername(), thisElement.getPassword());
//      return (connection);
//    } catch (Exception cnfe) {
//      // Simplify try/catch blocks of people using this by
//      // throwing only one exception type.
//      //throw new SQLException("Can't find class for driver: " + driver);
//      cnfe.printStackTrace(System.out);
//      return null;
//    }
//  }
//
//
//  /**
//   * Close connections -- useful for debugging
//   *
//   * @param connections    Description of Parameter
//   * @param connectionType Description of Parameter
//   * @since 1.1
//   */
//  private void closeConnections(int connectionType, Hashtable connections) {
//    try {
//      Enumeration e = connections.elements();
//      while (e.hasMoreElements()) {
//        //For each connection, attempt to close
//        try {
//          Connection connection = null;
//          if (connectionType == AVAILABLE_CONNECTION) {
//            connection = (Connection) e.nextElement();
//          } else {
//            ConnectionElement ce = (ConnectionElement) e.nextElement();
//            connection = (Connection) connections.get(ce);
//          }
//          connection.close();
//        } catch (SQLException sqle) {
//          //Ignore close error
//        }
//      }
//    } catch (Exception sqle) {
//      // Ignore errors; garbage collect anyhow
//    }
//  }
  
  public Object clone() {
	    try {
	      return super.clone();
	    } catch (java.lang.CloneNotSupportedException e) {
	    }
	    return null;
	  }

}

