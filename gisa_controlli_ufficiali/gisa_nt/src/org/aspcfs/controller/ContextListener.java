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
package org.aspcfs.controller;

import com.darkhorseventures.database.ConnectionPool;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.aspcfs.apps.workFlowManager.WorkflowManager;
import org.aspcfs.utils.ApplicationPropertiesStart;
import org.aspcfs.utils.GestoreConnessioni;




import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Responsible for initialization and cleanup when the web-app is
 * loaded/reloaded
 *
 * @author matt rajkowski
 * @version $Id: ContextListener.java,v 1.4 2002/12/23 19:59:31 mrajkowski Exp
 *          $
 * @created November 11, 2002
 */
public class ContextListener implements ServletContextListener {
  
  private final static Logger log = Logger.getLogger(org.aspcfs.controller.ContextListener.class);

  public final static String fs = System.getProperty("file.separator");


  /**
   * Constructor for the ContextListener object
   */
  public ContextListener() {
  }
		


  /**
   * Code initialization for global objects like ConnectionPools, the
   * initParameters from the servlet context are NOT available for use here.
   *
   * @param event Description of the Parameter
   */
  public void contextInitialized(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    log.info("Initializing");
    //Start the ConnectionPool with default params, these can be adjusted
    //in the InitHook

    //All virtual hosts will have an entry in SystemStatus, so this needs
    //to be reset when the context is reset
    Hashtable systemStatus = new Hashtable();
    context.setAttribute("SystemStatus", systemStatus);
    //The work horse for all objects that must go through a designed process,
    //reload here as well
   
    // Setup scheduler
    context.setAttribute("contesto", 	context.getInitParameter("context_starting"));
    
    try
    {
    ApplicationPropertiesStart properties = new ApplicationPropertiesStart("starting_"+event.getServletContext().getAttribute("contesto") +".properties");

//	  ConnectionPool cpStorico = new ConnectionPool();
//      cpStorico.setTestConnections(false);
//      cpStorico.setAllowShrinking(true);
//      cpStorico.setMaxConnections(10);
//      cpStorico.setMaxIdleTime(60000);
//      cpStorico.setMaxDeadTime(300000);
//      context.setAttribute("ConnectionPoolStorico", cpStorico);
	  
	  Iterator<Object> itKey= properties.getKeySet();
	  while (itKey.hasNext())
	  {
		  
		  String pool = (String) itKey.next();
		  if (pool.startsWith("ConnectionPool"))
		  {
			  ConnectionPool cp = null;
			  try {
				  cp = new ConnectionPool(properties.getProperty(pool));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  context.setAttribute(pool, cp);  
			  

			  switch(pool)
			  {
			  case "ConnectionPoolBdu" :
			  {
				  GestoreConnessioni.setCpBdu(cp);
				  break ;
			  }
			  case "ConnectionPoolVam" :
			  {
				  GestoreConnessioni.setCpVam(cp);
				  break ;
			  }
			  case "ConnectionPoolGuc" :
			  {
				  GestoreConnessioni.setCpGuc(cp);
				  break ;
			  }
			  case "ConnectionPoolStorico" :
			  {
				  GestoreConnessioni.setCpStorico(cp);
				  break ;
			  }
			  case "ConnectionPool" :
			  {
				  GestoreConnessioni.setCp(cp);
				  break ;
			  }
			  }
			  
			 
		  }
		  else
			  context.setAttribute(pool, properties.getProperty(pool)); 
		  
	  }
	  
	  if (properties.getProperty("MainPool") != null && !("").equals(properties.getProperty("MainPool"))){
          ConnectionPool mainPool = null;
		try {
			mainPool = new ConnectionPool(properties.getProperty("MainPool"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
         
     	
  }
    }
    catch(Exception e)
    {
    	  log.error("Context not Initialized : "+e.getMessage());
    }
    
    log.info("Initialized");
  }


  /**
   * All objects that should not be persistent can be removed from the context
   * before the next reload
   *
   * @param event Description of the Parameter
   */
  public void contextDestroyed(ServletContextEvent event) {
    ServletContext context = event.getServletContext();
    log.debug("Shutting down");
    //Stop the cron first so that nothing new gets executed
    
//    // Remove scheduler
//    try {
//      Scheduler scheduler = (Scheduler) context.getAttribute("Scheduler");
//      if (scheduler != null) {
//        scheduler.shutdown(true);
//        // TODO: check to see if db connections need to be closed here if
//        // scheduler is using its own ConnectionPool
//        context.removeAttribute("Scheduler");
//        log.info("Scheduler stopped");
//      }
//    } catch (Exception e) {
//      log.error(e.toString());
//    }
    //Stop the work flow manager
    WorkflowManager wfManager = (WorkflowManager) context.getAttribute(
        "WorkflowManager");
    if (wfManager != null) {
      context.removeAttribute("WorkflowManager");
    }
    //Remove the SystemStatus -> this will force a rebuild of any systems that
    //may have been cached
    Hashtable systemStatusList = (Hashtable) context.getAttribute(
        "SystemStatus");
    if (systemStatusList != null) {
      Iterator i = systemStatusList.values().iterator();
      while (i.hasNext()) {
        SystemStatus thisSystem = (SystemStatus) i.next();
        thisSystem.stopServers();
      }
      systemStatusList.clear();
    }
    context.removeAttribute("SystemStatus");
    //Remove the dynamic items, forcing them to rebuild
    context.removeAttribute("DynamicFormList");
    context.removeAttribute("DynamicFormConfig");
    context.removeAttribute("ContainerMenuConfig");

    //Unload the connection pool
    ConnectionPool cp = (ConnectionPool) context.getAttribute(
        "ConnectionPool");
    if (cp != null) {
      cp.closeAllConnections();
      cp.destroy();
      context.removeAttribute("ConnectionPool");
    }
    log.debug("Shutdown complete");
  }
}

