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
import org.aspcfs.modules.accounts.utils.ApplicationProperties;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.MethodsUtils;

import com.darkhorseventures.framework.actions.ActionContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Timer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

public class ConnectionPool  {

  private final static Logger log = Logger.getLogger(com.darkhorseventures.database.ConnectionPool.class);

  //Internal Constants
  public final static int BUSY_CONNECTION = 1;
  public final static int AVAILABLE_CONNECTION = 2;
  //Thread properties for creating a new connection
  private String url = null;
  private String username = null;
  private String password = null;
  private String driver = null;
  //Connection Pool Properties
  private java.util.Date startDate = new java.util.Date();
  private Hashtable availableConnections;
  private Hashtable busyConnections;
  private boolean connectionPending = false;
  //Connection Pool Settings
  private boolean debug = false;
  private int maxConnections = 10;
  private boolean waitIfBusy = true;
  private boolean allowShrinking = false;
  private boolean testConnections = false;
  private boolean forceClose = false;
  private int maxIdleTime = 60000;
  private int maxDeadTime = 300000;
  private Timer cleanupTimer = null;


  /**
   * Constructor for the ConnectionPool object. <p>
   * <p/>
   * Instantiates the pool and background timer with default settings.
   *
   * @throws SQLException Description of Exception
 * @throws NamingException 
   * @since 1.2
   */
  public ConnectionPool(String dataSourceIn) throws SQLException, NamingException {
	
	  try {
			Context ctx = new InitialContext();
			log.info("INIZIALIZZAZIONE DATASOURCE "+dataSourceIn);
		dataSource = (DataSource)ctx.lookup(dataSourceIn);
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			log.error(dataSourceIn + "NON DEFINITO NEL CONTEXT");
		}
    
    
  }
  
  public ConnectionPool()  {
	
    
  }


  /**
   * Sets whether connection pool statistics and connection information are
   * output
   *
   * @param tmp The new debug value
   */
  public void setDebug(boolean tmp) {
    this.debug = tmp;
  }


  /**
   * Sets the debug attribute of the ConnectionPool object
   *
   * @param tmp The new debug value
   */
  public void setDebug(String tmp) {
    this.debug = "true".equals(tmp);
  }


  /**
   * Sets the behavior of connection requests. If all connections are busy,
   * then the thread requesting the connection will either wait or throw an
   * exception if waitIfBusy is false
   *
   * @param tmp The new waitIfBusy value
   */
  public void setWaitIfBusy(boolean tmp) {
    this.waitIfBusy = tmp;
  }


  /**
   * Sets whether a background process is allowed to close unused connections
   * after the maxIdleTime has been reached
   *
   * @param tmp The new allowShrinking value
   */
  public void setAllowShrinking(boolean tmp) {
    this.allowShrinking = tmp;
  }


  /**
   * Sets the allowShrinking attribute of the ConnectionPool object
   *
   * @param tmp The new allowShrinking value
   */
  public void setAllowShrinking(String tmp) {
    this.allowShrinking = "true".equals(tmp);
  }


  /**
   * Sets whether connections will be tested, by executing a simple query,
   * before being handed out. If the test fails, then a new connection is
   * attempted without throwing an exception.
   *
   * @param tmp The new testConnections value
   */
  public void setTestConnections(boolean tmp) {
    this.testConnections = tmp;
  }


  /**
   * Sets the testConnections attribute of the ConnectionPool object
   *
   * @param tmp The new testConnections value
   */
  public void setTestConnections(String tmp) {
    this.testConnections = "true".equals(tmp);
  }


  /**
   * Sets whether connections are immediately closed, instead of pooled for
   * later use
   *
   * @param tmp The new ForceClose value
   * @since 1.1
   */
  public void setForceClose(boolean tmp) {
    this.forceClose = tmp;
  }


  /**
   * Sets the maximum number of connections that can be open at once, if the
   * max is reached, then behavior is determined by the waitIfBusy property
   *
   * @param tmp The new maxConnections value
   */
  public void setMaxConnections(int tmp) {
    this.maxConnections = tmp;
  }


  /**
   * Sets the maxConnections attribute of the ConnectionPool object
   *
   * @param tmp The new maxConnections value
   */
  public void setMaxConnections(String tmp) {
    this.maxConnections = Integer.parseInt(tmp);
  }


  /**
   * Sets the maximum number of milliseconds a connection can remain idle for
   * until shrinking occurs.
   *
   * @param tmp The new maxIdleTime value
   */
  public void setMaxIdleTime(int tmp) {
    this.maxIdleTime = tmp;
  }


  /**
   * Sets the maxIdleTime attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTime value
   */
  public void setMaxIdleTime(String tmp) {
    this.maxIdleTime = Integer.parseInt(tmp);
  }


  /**
   * Sets the maxIdleTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTimeSeconds value
   */
  public void setMaxIdleTimeSeconds(String tmp) {
    this.maxIdleTime = 1000 * Integer.parseInt(tmp);
  }


  /**
   * Sets the maxIdleTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxIdleTimeSeconds value
   */
  public void setMaxIdleTimeSeconds(int tmp) {
    this.maxIdleTime = 1000 * tmp;
  }


  /**
   * Sets the maximum number of milliseconds a connection can checked out and
   * remain busy for. If the connection is not returned, or not renewed, then
   * it will be closed. The connection might be in use, or some process may
   * have forget to return it. This prevents an application from completely
   * being unusable if the connection is not returned.
   *
   * @param tmp The new maxDeadTime value
   */
  public void setMaxDeadTime(int tmp) {
    this.maxDeadTime = tmp;
  }


  /**
   * Sets the maxDeadTime attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTime value
   */
  public void setMaxDeadTime(String tmp) {
    this.maxDeadTime = Integer.parseInt(tmp);
  }


  /**
   * Sets the maxDeadTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTimeSeconds value
   */
  public void setMaxDeadTimeSeconds(String tmp) {
    this.maxDeadTime = 1000 * Integer.parseInt(tmp);
  }


  /**
   * Sets the maxDeadTimeSeconds attribute of the ConnectionPool object
   *
   * @param tmp The new maxDeadTimeSeconds value
   */
  public void setMaxDeadTimeSeconds(int tmp) {
    this.maxDeadTime = 1000 * tmp;
  }

  public boolean getDebug() {
    return debug;
  }

  public boolean getAllowShrinking() {
    return allowShrinking;
  }

  public boolean getTestConnections() {
    return testConnections;
  }

  /**
   * Gets the username attribute of the ConnectionPool object
   *
   * @return The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   * Gets the password attribute of the ConnectionPool object
   *
   * @return The password value
   */
  public String getPassword() {
    return password;
  }


  /**
   * Gets the driver attribute of the ConnectionPool object
   *
   * @return The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   * Gets the maxConnections attribute of the ConnectionPool object
   *
   * @return The maxConnections value
   */
  public int getMaxConnections() {
    return maxConnections;
  }


  /**
   * Gets the maxIdleTime attribute of the ConnectionPool object
   *
   * @return The maxIdleTime value
   */
  public int getMaxIdleTime() {
    return maxIdleTime;
  }


  /**
   * Gets the maxDeadTime attribute of the ConnectionPool object
   *
   * @return The maxDeadTime value
   */
  public int getMaxDeadTime() {
    return maxDeadTime;
  }

public  DataSource dataSource = null ;




  /**
   * When a connection is needed, ask the class for the next available, if the
   * max connections has been reached then a thread will be spawned to wait for
   * the next available connection.
   *
   * @param requestElement Description of the Parameter
   * @return The connection value
   * @throws SQLException Description of the Exception
   */
  public  Connection getConnection(ConnectionElement requestElement, ActionContext ctx) throws SQLException { 	 
		try {
			Connection db =  dataSource.getConnection();
			Thread t = Thread.currentThread();
			  
				getInfo(db,0,dataSource,MethodsUtils.getNomeMetodo(t),ctx);
				return db ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
    }
  
  
  
  public  Connection getConnection() throws SQLException { 	 
		try {
			Connection db =  dataSource.getConnection();
				return db ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
  }
  
  
  
public Timestamp getTime()
{
	return new Timestamp(System.currentTimeMillis());
}
public void getInfo(Connection db,int tipo,DataSource ds,String action,ActionContext ctx){
try{	
	UserBean user = null ;
	if (ctx!= null && ctx.getRequest().getSession()!=null)
		user = (UserBean) ctx.getRequest().getSession().getAttribute("User");
	
	
	ApplicationPrefs prefs = (ApplicationPrefs) ctx.getServletContext().getAttribute("applicationPrefs");
	String ceDriver = prefs.get("GATEKEEPER.DRIVER");
	String ceHost = prefs.get("GATEKEEPER.URL");
	String ceUser = prefs.get("GATEKEEPER.USER");
	String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
	
	if ((ctx!=null && ctx.getBrowser()!=null &&  !ctx.getBrowser().contains("Firefox")) ){
		ceUser = "usr_ro";
	} else if (ctx!=null && ctx.getBrowser()==null) {
		UserBean u = (UserBean)ctx.getSession().getAttribute("User");
		if (!u.getBrowserId().equalsIgnoreCase("moz"))
			ceUser = "usr_ro";
	}
	ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	SystemStatus thisSystem = null; 
	SessionManager sessionManager = null;
	HashMap sessions = null;
	thisSystem = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
	if(thisSystem != null){
		sessionManager = thisSystem.getSessionManager();
		
	}
	
	
	HashMap dbaction = null;
	

	if(thisSystem != null){
		sessionManager = thisSystem.getSessionManager();
		
	}
	if(sessionManager != null){
		dbaction = sessionManager.getDbconnection();
	}
	
	
	
	
	if (tipo==0)
	{
		ActionDb actiondb = new ActionDb();
		actiondb.setActionName(ctx.getAction().getActionName());
		actiondb.setActionppathname(ctx.getAction().getActionClassName());
		actiondb.setIpChiamante(ctx.getRequest().getRemoteAddr());
		actiondb.setDataApertura(new Timestamp(System.currentTimeMillis()));
		actiondb.setCommand(ctx.getCommand());
		if (dbaction.containsKey(user.getUserId()))
		{
			HashMap lista = (HashMap)dbaction.get(user.getUserId());
			lista.put(db, actiondb);
			
			dbaction.put(user.getUserId(), lista);
			
			
			
			
		}
		else
		{
			HashMap lista = new HashMap();
			lista.put(db, actiondb);
			
			dbaction.put(user.getUserId(), lista);
		}
	}
	else
	{
		HashMap lista = (HashMap)dbaction.get(user.getUserId());
		lista.remove(db);
		
	}
		
	ctx.getSession().setAttribute("User", user);
	log.info("[CONTESTO : "+user.getUserRecord().getSuap().getContesto()+" USER : "+user.getUsername()+"]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );


}catch (Exception e) {
	// TODO: handle exception
}
}

//		
//public  Connection getConnectionStorico(ConnectionElement requestElement, ActionContext ctx) throws SQLException {		
//	Context context;
//	DataSource dataSource = null;
//	try {
//		context = new InitialContext();
//		dataSource = (DataSource)context.lookup("java:comp/env/jdbc/StoricoDataSource");
//	} catch (NamingException e1) {
//		e1.printStackTrace();
//	}
//	try {		
//		Connection db =  dataSource.getConnection();
//		Thread t = Thread.currentThread();  
//
//		getInfo(db,0,dataSource,MethodsUtils.getNomeMetodo(t),ctx);
//		return db ;
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return null ;
//}

  


  /**
   * Returns whether max connections has been reached for debugging
   *
   * @return The MaxStatus value
   * @since 1.5
   */
  public boolean getMaxStatus() {
    if (busyConnections.size() == maxConnections) {
      return (true);
    } else {
      return (false);
    }
  }



  

  /**
   * When finished with the connection, don't close it, free the connection and
   * it will be reused by another request. If it's closed then remove the
   * reference to it and another will just have to be opened.
   *
   * @param connection Description of Parameter
   * @since 1.0
   */
  public  void free(Connection connection,ActionContext context) {
   try {
	   if (connection != null)
	   {
		    connection.close();
		    
		   Thread t = Thread.currentThread();
			getInfo(connection,1,dataSource,MethodsUtils.getNomeMetodo(t),context);
	   }
	 
	

	   
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  }


  public  void free(Connection connection) {
	 
		  free(connection,null);

		   
	
	  }
  /**
   * Description of the Method
   *
   * @param connection Description of the Parameter
   */
  public void renew(Connection connection) {
  
  }


  /**
   * Returns total connections allocated
   *
   * @return Description of the Returned Value
   * @since 1.0
   */
  public int totalConnections() {
    return (availableConnections.size() + busyConnections.size());
  }


  /**
   * Close all the connections. Use with caution: be sure no connections are in
   * use before calling. Note that you are not <I>required</I> to call this
   * when done with a ConnectionPool, since connections are guaranteed to be
   * closed when garbage collected. But this method gives more control
   * regarding when the connections are closed.
   *
   * @since 1.1
   */
  public synchronized void closeAllConnections() {
    log.debug("Status: " + this.toString());
    log.debug("Closing available connections");
    availableConnections.clear();
    log.debug("Closing busy connections");
    busyConnections.clear();
  }




  /**
   * More debugging information... displays the current state of the
   * ConnectionPool
   *
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String toString() {
    String info =
        "(avail=" + availableConnections.size() +
        ", busy=" + busyConnections.size() +
        ", max=" + maxConnections + ")";
    return (info);
  }


  /**
   * Cleans up the connection pool when destroyed, it's important to remove any
   * open timers -- this does not appear to be automatically called so the
   * application should call this to make sure the timer is stopped.
   *
   * @since 1.1
   */
  public void destroy() {
    if (cleanupTimer != null) {
      cleanupTimer.cancel();
      cleanupTimer = null;
      if (debug) {
        log.debug("Timer shut down");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
      }
    }
    log.debug("Stopped");
  }


 
  
  
  public  Connection getConnection(ConnectionElement requestElement, HttpSession session, UserBean userBean, ApplicationPrefs prefs, ActionContext context, String browser, Object systemStatus, String command, String actionName, String actionClassName, String remoteAddr) throws SQLException 
  { 	 
		try 
		{
			Connection db =  dataSource.getConnection();
			Thread t = Thread.currentThread();
			  
				getInfo(db,0,dataSource,MethodsUtils.getNomeMetodo(t), session, userBean, prefs, context, browser, systemStatus, command, actionName, actionClassName, remoteAddr);
				return db ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
    }






public void getInfo(Connection db,int tipo,DataSource ds,String action,HttpSession session, UserBean userBean, ApplicationPrefs prefs,ActionContext context, String browser, Object systemStatus,String command, String actionName, String actionClassName, String remoteAddr){
try{	
	UserBean user = null ;
	if (context!= null && session!=null)
		user = userBean;
	
	
	String ceDriver = prefs.get("GATEKEEPER.DRIVER");
	String ceHost = prefs.get("GATEKEEPER.URL");
	String ceUser = prefs.get("GATEKEEPER.USER");
	String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
	
	if ((context!=null && browser!=null &&  !browser.contains("Firefox")) ){
		ceUser = "usr_ro";
	} else if (context!=null && browser==null) {
		UserBean u = (UserBean)userBean;
		if (!u.getBrowserId().equalsIgnoreCase("moz"))
			ceUser = "usr_ro";
	}
	ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	SystemStatus thisSystem = null; 
	SessionManager sessionManager = null;
	HashMap sessions = null;
	thisSystem = (SystemStatus) ((Hashtable) systemStatus).get(ce.getUrl());
	if(thisSystem != null){
		sessionManager = thisSystem.getSessionManager();
		
	}
	
	
	HashMap dbaction = null;
	

	if(thisSystem != null){
		sessionManager = thisSystem.getSessionManager();
		
	}
	if(sessionManager != null){
		dbaction = sessionManager.getDbconnection();
	}
	
	
	
	
	if (tipo==0)
	{
		ActionDb actiondb = new ActionDb();
		actiondb.setActionName(actionName);
		actiondb.setActionppathname(actionClassName);
		actiondb.setIpChiamante(remoteAddr);
		actiondb.setDataApertura(new Timestamp(System.currentTimeMillis()));
		actiondb.setCommand(command);
		if (dbaction.containsKey(user.getUserId()))
		{
			HashMap lista = (HashMap)dbaction.get(user.getUserId());
			lista.put(db, actiondb);
			
			dbaction.put(user.getUserId(), lista);
			
			
			
			
		}
		else
		{
			HashMap lista = new HashMap();
			lista.put(db, actiondb);
			
			dbaction.put(user.getUserId(), lista);
		}
	}
	else
	{
		HashMap lista = (HashMap)dbaction.get(user.getUserId());
		lista.remove(db);
		
	}
		
	//ctx.getSession().setAttribute("User", user);
	log.info("[CONTESTO : "+user.getUserRecord().getSuap().getContesto()+" USER : "+user.getUsername()+"]"+((tipo==0)? ">" : "<")+"  CP " +" **ACTIVE SIZE:  "+ds.getActive() + " **IDLE SIZE:  "+ds.getIdle()+" "+action  );


}catch (Exception e) {
	// TODO: handle exception
}
}


public  void free(Connection connection, ConnectionElement requestElement, HttpSession session, UserBean userBean, ApplicationPrefs prefs, ActionContext context, String browser, Object systemStatus, String command, String actionName, String actionClassName, String remoteAddr) 
{
	   try {
		   if (connection != null)
		   {
			    connection.close();
			    
			   Thread t = Thread.currentThread();
				getInfo(connection,1,dataSource,MethodsUtils.getNomeMetodo(t), session, userBean, prefs, context, browser, systemStatus, command, actionName, actionClassName, remoteAddr);
		   }
		 
		

		   
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  }


  




}

