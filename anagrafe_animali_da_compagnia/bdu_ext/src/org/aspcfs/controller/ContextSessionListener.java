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

import com.zeroio.controller.Tracker;

import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Listener for monitoring session changes
 *
 * @author matt rajkowski
 * @version $Id: ContextSessionListener.java,v 1.2 2002/11/14 13:32:16
 *          mrajkowski Exp $
 * @created November 11, 2002
 */
public class ContextSessionListener implements HttpSessionAttributeListener, HttpSessionListener {

  /**
   * This method is invoked when a session is created
   *
   * @param event Description of the Parameter
   */
  public void sessionCreated(HttpSessionEvent event) {
    /*if (System.getProperty("DEBUG") != null) {
      System.out.println("ContextSessionListener-> sessionCreated");
    }*/
  }


  /**
   * This method is invoked when a session is destroyed
   *
   * @param event Description of the Parameter
   */
  public void sessionDestroyed(HttpSessionEvent event) {
   if (System.getProperty("DEBUG") != null) {
      System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId());
    }
	HttpSession s =  event.getSession();
	
	//STORICO OPERAZIONI UTENTI
	if (s.getAttribute("operazioni")!=null){
		 		    
		ArrayList<UserOperation> op=(ArrayList<UserOperation>) s.getAttribute("operazioni");
		String query = "";
		if (s.getAttribute("AccessUpdate")!=null)
			query = (String) s.getAttribute("AccessUpdate"); 
		MiddleServlet.writeStorico(op, query,false,(String)event.getSession().getServletContext().getInitParameter("context_starting"));
	}		  
		  ServletContext context = event.getSession().getServletContext();
		    try {
		     
		        UserBean thisUser = (UserBean) event.getSession().getAttribute("User");
		        if (thisUser != null) {
		        	System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId() + ". thisUser != null");

		          // Internal SessionManager
		          int userId = thisUser.getActualUserId();
		          if (userId > -2) {
		        	  System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId() + ". userId > -2");

		            // If context reloaded, then SystemStatus is null, but user is valid
		            Hashtable systems = (Hashtable) context.getAttribute("SystemStatus");
		            if (systems != null) {
		            	System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId() + ". systems != null");
		                SystemStatus systemStatus = (SystemStatus) systems.get(thisUser.getConnectionPoll().getUrl());
		              if (systemStatus != null) {
		            	  System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId() + ". systemStatus != null");

		                // Remove the user from the session if it already is there
		                SessionManager thisManager = systemStatus.getSessionManager();
		                if (thisManager != null) {
		                	System.out.println("ContextSessionListener-> sessionDestroyed for user Id: " + ((UserBean) event.getSession().getAttribute("User")).getActualUserId() + ". thisManager != null");
		                  UserSession thisSession = thisManager.getUserSession(userId);
		                  if (thisSession != null && thisSession.getId().equals(thisUser.getSessionId()))
		                  {
		                      System.out.println(
		                         "["+new Timestamp(System.currentTimeMillis()) +"]ContextSessionListener-> Session Invalidate  UserId " + userId + "");
		                    
		                    thisManager.removeUser(userId);
		                    if (System.getProperty("DEBUG") != null) {
		                      System.out.println(
		                          "ContextSessionListener-> User removed from valid user list");
		                    }
		                  }
		                }
		                // Website Tracker
		                Tracker tracker = systemStatus.getTracker();
		                tracker.remove(thisUser.getSessionId());
		              }
		            }
		          }
		        
		      }
		        else
		        {
		        	System.out.println("ContextSessionListener-> sessionDestroyed for user Id: null");
		        }
		    } catch (Exception e) {
		     
		      System.out.println("ContextSessionListener-> attributeRemoved Error: " + e.toString());
		    }
	  
	  }


  /**
   * This method is invoked when an attribute is added to the ServletContext
   * object
   *
   * @param event Description of the Parameter
   */
  public void attributeAdded(HttpSessionBindingEvent event) {
    ServletContext context = event.getSession().getServletContext();
    if ("User".equals(event.getName())) {
      // A user session has been created, can be a portal user or system user
      UserBean thisUser = (UserBean) event.getValue();
      thisUser.setSessionId(event.getSession().getId());
      // Track website users
      Hashtable systems = (Hashtable) context.getAttribute("SystemStatus");
      if (systems != null && thisUser.getConnectionPoll() != null) {
        SystemStatus systemStatus = (SystemStatus) systems.get(thisUser.getConnectionPoll().getUrl());
        if (systemStatus != null) {      
          Tracker tracker = ((SystemStatus) ((Hashtable) context.getAttribute(
            "SystemStatus")).get(thisUser.getConnectionPoll().getUrl())).getTracker();
          tracker.add(thisUser.getSessionId(), thisUser);
        }
      }
    }
  }


  /**
   * This method is invoked when an attribute is removed from the
   * ServletContext object
   *
   * @param se Description of the Parameter
   */
  public void attributeRemoved(HttpSessionBindingEvent se) {
    ServletContext context = se.getSession().getServletContext();
    try {
      if (se.getName().equals("User")) {
        UserBean thisUser = (UserBean) se.getValue();
        if (thisUser != null) {
          // Internal SessionManager
          int userId = thisUser.getActualUserId();
          if (userId > -2) {
            // If context reloaded, then SystemStatus is null, but user is valid
            Hashtable systems = (Hashtable) context.getAttribute("SystemStatus");
            if (systems != null) {
              SystemStatus systemStatus = (SystemStatus) systems.get(thisUser.getConnectionPoll().getUrl());
              if (systemStatus != null) {
                // Remove the user from the session if it already is there
                SessionManager thisManager = systemStatus.getSessionManager();
                if (thisManager != null) {
                  UserSession thisSession = thisManager.getUserSession(userId);
                  if (thisSession != null && thisSession.getId().equals(thisUser.getSessionId()))
                  {
                    if (System.getProperty("DEBUG") != null) {
                      System.out.println(
                          "ContextSessionListener-> Session for user " + userId + " ended");
                    }
                    thisManager.removeUser(userId);
                    if (System.getProperty("DEBUG") != null) {
                      System.out.println(
                          "ContextSessionListener-> User removed from valid user list");
                    }
                  }
                }
                // Website Tracker
                Tracker tracker = systemStatus.getTracker();
                tracker.remove(thisUser.getSessionId());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      System.out.println("ContextSessionListener-> attributeRemoved Error: " + e.toString());
    }
  }


  /**
   * This method is invoked when an attribute is replaced in the ServletContext
   * object
   *
   * @param event Description of the Parameter
   */
  public void attributeReplaced(HttpSessionBindingEvent event) {
    // This event has a handle to the old User object
    ServletContext context = event.getSession().getServletContext();
    // The user has logged in
    if ("User".equals(event.getName())) {
      UserBean thisUser = (UserBean) event.getValue();
      thisUser.setSessionId(event.getSession().getId());
      
    if (thisUser.getConnectionPoll()!=null)
    {
    
      Tracker tracker = ((SystemStatus) ((Hashtable) context.getAttribute(
          "SystemStatus")).get(thisUser.getConnectionPoll().getUrl())).getTracker();
      tracker.remove(event.getSession().getId());
      tracker.add(thisUser.getSessionId(), thisUser);
    }
      }
    
  }
  
}

