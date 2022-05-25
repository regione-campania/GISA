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
import com.darkhorseventures.framework.servlets.ControllerServlet;

import org.apache.log4j.Logger;
import org.aspcfs.apps.workFlowManager.WorkflowManager;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.ApplicationPropertiesStart;
import org.aspcfs.utils.GestoreConnessioni;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    final ServletContext context = event.getServletContext();
    ServletContext cc = null;
    
//    cc = (ServletContext) Proxy.newProxyInstance(  
//            getClass().getClassLoader(),  
//            new Class[]{ServletContext.class},  
//            new InvocationHandler() {  
//                public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {  
//                    if ("getServletContextName".equals(method.getName())) {  
//                        return ContextListener.getInitParametersFromProperties((String) method.invoke(context, args), context);  
//                    }  
//                    else {  
//                        return method.invoke(context, args);  
//                    }  
//                }  
//            }  
//    );
//    System.out.println("Caricando contesto  " + cc.getContextPath());
//    System.out.println("Setting connection pools for  " +cc.getServletContextName());
    
  
   
    log.info("Initializing");
    //Start the ConnectionPool with default params, these can be adjusted
    //in the InitHook
    try {
    	
    	context.setAttribute("contesto", context.getInitParameter("context_starting"));
    	ApplicationPropertiesStart properties = new ApplicationPropertiesStart("starting_"+ context.getInitParameter("context_starting") +".properties");
    	
    	
    /*xx
     * Ogni contest deve avere il suo MainPool	
     */
    	
        ConnectionPool cpMain = new ConnectionPool(properties.getProperty("MainPool"));
        //    ConnectionPool cpSecondario = new ConnectionPool(properties.getProperty("SecondaryPool"));
            GestoreConnessioni.setCp(cpMain);
            context.setAttribute("ConnectionPool", cpMain);
    
        if (properties.getProperty("BduPool") != null && !("").equals(properties.getProperty("BduPool"))){
      	  ConnectionPool BduPool = new ConnectionPool(properties.getProperty("BduPool"));
            GestoreConnessioni.setCpBdu(BduPool);
            context.setAttribute("ConnectionPoolBdu", BduPool);  
        }

      if  (properties.getProperty("VamPool") != null && !("").equals(properties.getProperty("VamPool"))){
    	  ConnectionPool cpVam = new ConnectionPool(properties.getProperty("VamPool"));
          GestoreConnessioni.setCpVam(cpVam);
          context.setAttribute("ConnectionPoolVam", cpVam);  
      }
      
      if (properties.getProperty("GisaPool") != null && !("").equals(properties.getProperty("GisaPool"))){
    	  ConnectionPool GisaPool = new ConnectionPool(properties.getProperty("GisaPool"));
          GestoreConnessioni.setCpGisa(GisaPool);
          context.setAttribute("ConnectionPoolGisa", GisaPool);  
      }
      
      
      if  (properties.getProperty("StoricoPool") != null && !("").equals(properties.getProperty("StoricoPool"))){
    	  ConnectionPool StoricoPool = new ConnectionPool(properties.getProperty("StoricoPool"));
          GestoreConnessioni.setCpStorico(StoricoPool);
          context.setAttribute("ConnectionPoolStorico", StoricoPool);  
      }
      
      /*if (properties.getProperty("ImportatoriPool") != null && !("").equals(properties.getProperty("ImportatoriPool"))){
    	  ConnectionPool cpImportatori = new ConnectionPool(properties.getProperty("ImportatoriPool"));
          GestoreConnessioni.setCpImportatori(cpImportatori);
          context.setAttribute("ConnectionPoolImportatori", cpImportatori);  
      }*/
      
      
  //    GestoreConnessioni.setCpSecondario(cpSecondario);
      
      
    //  context.setAttribute("ConnectionPoolSecondario", cpSecondario );
      
    } catch (Exception e) {
      log.error(e.toString());
    }
    //All virtual hosts will have an entry in SystemStatus, so this needs
    //to be reset when the context is reset
    Hashtable systemStatus = new Hashtable();
    context.setAttribute("SystemStatus", systemStatus);
    //The work horse for all objects that must go through a designed process,
    //reload here as well
   
    // Setup scheduler
 
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
    
    // Remove scheduler
    try {
      Scheduler scheduler = (Scheduler) context.getAttribute("Scheduler");
      if (scheduler != null) {
        scheduler.shutdown(true);
        // TODO: check to see if db connections need to be closed here if
        // scheduler is using its own ConnectionPool
        context.removeAttribute("Scheduler");
        log.info("Scheduler stopped");
      }
    } catch (Exception e) {
      log.error(e.toString());
    }
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
//      cp.closeAllConnections();
//      cp.destroy();
      context.removeAttribute("ConnectionPool");
    }
    
    //Unload the connection pool
    ConnectionPool cpVam = (ConnectionPool) context.getAttribute(
        "ConnectionPoolVam");
    if (cpVam != null) {
//      cp.closeAllConnections();
//      cp.destroy();
      context.removeAttribute("ConnectionPoolVam");
    }
    log.debug("Shutdown complete");
  }
  
  
 public static String getInitParametersFromProperties(String value, ServletContext cc) {  
	  
	  
	  ApplicationPropertiesStart properties = new ApplicationPropertiesStart("starting_"+cc.getInitParameter("context_starting") +".properties");
      if (value == null) {  
          return null;  
      }  
      String initialValue = null;  
      final Pattern p = Pattern.compile("\\$\\{([\\p{Alnum}|\\.|\\-]*)\\}");  
      while (!value.equals(initialValue)) {  
          initialValue = value;  
          final Matcher m = p.matcher(value);  
          while (m.find()) {  
              final String propName = m.group(1);  
              final String propValue = properties.getProperty(propName) ; 
              if (propValue != null) {  
                  value = value.replaceAll("\\$\\{" + propName + "\\}", Matcher.quoteReplacement(propValue));  
              }  
          }  
      }  
      return value;  
  }  

}

