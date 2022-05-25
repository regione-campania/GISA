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

import com.darkhorseventures.framework.actions.ActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.Suap;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Handles all sessions running on the web server
 *
 * @author Mathur
 * @version $Id: SessionManager.java,v 1.1.2.1 2002/11/26 19:51:17 akhi_m Exp
 *          $
 * @created November 22, 2002
 */

public class SessionManager {

  public final static int ADD = 1;
  public final static int REMOVE = 2;
  HashMap sessions = new HashMap();
  
  HashMap sessionssuap = new HashMap();

  HashMap dbconnection = new HashMap();

  
  Logger logger = Logger.getLogger(SessionManager.class);
  /**
   * Constructor for the SessionManager object
   */
  public SessionManager() {
  }

  
  





public HashMap getDbconnection() {
	return dbconnection;
}








public void setDbconnection(HashMap dbconnection) {
	this.dbconnection = dbconnection;
}








/**
   * Sets the sessions attribute of the SessionManager object
   *
   * @param sessions The new sessions value
   */
  public void setSessions(HashMap sessions) {
    this.sessions = sessions;
  }


  /**
   * Gets all sessions running on this web server
   *
   * @return The sessions value
   */
  public synchronized HashMap getSessions() {
    return sessions;
  }
  public synchronized HashMap getSessionsSuap() {
	    return sessionssuap;
	  }
  


  /**
   * Adds a user to the HashMap in synch mode
   *
   * @param userId  The feature to be added to the User attribute
   * @param context The feature to be added to the User attribute
   */
  public void addUser(ActionContext context, int userId,Suap suap) {
    this.addUser(context.getRequest(), userId,suap);
  }
  


  
 

  /**
   * Adds a feature to the User attribute of the SessionManager object, for
   * classes that do not have access to an ActionContext, like the SecurityHook
   *
   * @param request The feature to be added to the User attribute
   * @param userId  The feature to be added to the User attribute
   */
  public void addUser(HttpServletRequest request, int userId,Suap suap) {
	  
	  
	  
	  
    HttpSession session = request.getSession();
    UserSession thisSession = new UserSession();
    
//    Iterator<Integer> itKey = this.getSessions().keySet().iterator();
//    while (itKey.hasNext())
//    {
//    	int usId =itKey.next();
//    	UserSession userSes = (UserSession) this.getSessions().get(usId);
//    	if (userSes.getSessionUser().getId()==request.getSession().getId())
//    	{
//    		
//    		this.getSessions().remove(usId);
//    	}
//    }
    
    
    thisSession.setId(session.getId());
    thisSession.setIpAddress(request.getRemoteAddr());
    thisSession.setCreationTime(session.getCreationTime());
    thisSession.setUserId(userId);
    thisSession.setSessionUser(session);
    thisSession.setSuap(suap);
    
    if(request.getParameter("access_position_lat")!=null)
    {
    	thisSession.setAccess_position_lat(request.getParameter("access_position_lat"));
    }
    else
    {
    	if(request.getAttribute("access_position_lat")!=null)
        {
        	thisSession.setAccess_position_lat(""+request.getAttribute("access_position_lat"));
        }
    	
    }
    
    if(request.getParameter("access_position_lon")!=null)
    {
    	thisSession.setAccess_position_lon(request.getParameter("access_position_lon"));
    }
    else
    {
    	if(request.getAttribute("access_position_lon")!=null)
        {
        	thisSession.setAccess_position_lon(""+request.getAttribute("access_position_lon"));
        }
    }
    if(request.getParameter("access_position_err")!=null)
    {
    	thisSession.setAccess_position_err(request.getParameter("access_position_err"));
    }
    else
    {
    	if(request.getAttribute("access_position_err")!=null)
        {
        	thisSession.setAccess_position_err(""+request.getAttribute("access_position_err"));
        }
    }
    
    
    
    if(!suap.getContesto().equalsIgnoreCase("suap"))
    {
    	
    if (sessions.get(new Integer(userId)) == null) {
      synchUpdate(thisSession, userId, ADD,suap);
    } else {
	       

    }
    }
    else
    {
    	 if (sessionssuap.get(suap.getCodiceFiscaleRichiedente()) == null) {
    	      synchUpdate(thisSession, userId, ADD,suap);
    	    } else {
    	      
  	       

    	      }
    	    
    }
  }


  /**
   * Adds/Removes an entry from HashMap in synch mode
   *
   * @param thisSession Description of the Parameter
   * @param userId      Description of the Parameter
   * @param action      Description of the Parameter
   */
  public void synchUpdate(UserSession thisSession, int userId, int action,Suap suap) {
    synchronized (this) {
      
    	
    		
    	if (action == ADD) {
    	  
    		 if(!suap.getContesto().equalsIgnoreCase("suap"))
    		    {
    			 sessions.put(new Integer(userId), thisSession);
    			 logger.info("##"+suap.getContesto()+"## - AGGIUNTO UTENTE IN SESSIONMANAGER - USERID "+userId);
        
    		}
    		else
    		{
    			
    	        sessionssuap.put(suap.getCodiceFiscaleRichiedente(), thisSession);
    	        logger.info("##"+suap.getContesto()+"## - AGGIUNTO UTENTE IN SESSIONMANAGER - CF "+suap.getCodiceFiscaleRichiedente());

    		}
      } 
    	else if (action == REMOVE) {
    		if(!suap.getContesto().equalsIgnoreCase("suap"))
		    {
    				sessions.remove(new Integer(userId));
    				logger.info("##"+suap.getContesto()+"## - RIMOSSO UTENTE IN SESSIONMANAGER - USERID "+userId);

        
        
  		}
    	  else
    	  {
    		  	sessionssuap.remove(suap.getCodiceFiscaleRichiedente());
  	        	logger.info("##"+suap.getContesto()+"## - RIMOSSO UTENTE IN SESSIONMANAGER - CF "+suap.getCodiceFiscaleRichiedente());

    		  
    	      
    	  }
      }
    }
  }
  
  
 


  /**
   * Remove a user from HashMap in synch mode
   *
   * @param userId Description of the Parameter
   */
  public void removeUser(int userId,Suap suap) {
    
	  if(!suap.getContesto().equalsIgnoreCase("suap"))
	    {
	  UserSession thisSession = (UserSession) sessions.get(new Integer(userId));
    if (thisSession != null) {
      synchUpdate(thisSession, userId, REMOVE,suap);
      }
	  }
	  else
	  {
		  UserSession thisSession = (UserSession) sessionssuap.get(suap.getCodiceFiscaleRichiedente());
		    if (thisSession != null) {
		      synchUpdate(thisSession, userId, REMOVE,suap);
		      }
	  }
  }


  /**
   * checks to see if the user is logged in
   *
   * @param userId Description of the Parameter
   * @return The userLoggedIn value
   */
  public boolean isUserLoggedIn(int userId) {
    return sessions.containsKey(new Integer(userId));
  }
  
  public boolean isUserLoggedIn(String codiceFiscale) {
	    return sessionssuap.containsKey(codiceFiscale);
	  }


  /**
   * Gets the userSession attribute of the SessionManager object
   *
   * @param userId Description of the Parameter
   * @return The userSession value
   */
  public UserSession getUserSession(int userId) {
    if (sessions.get(new Integer(userId)) != null) {
      return (UserSession) sessions.get(new Integer(userId));
    }
    return null;
  }
  
  public UserSession getUserSession(String cf) {
	    if (sessionssuap.get(cf) != null) {
	      return (UserSession) sessionssuap.get(cf);
	    }
	    return null;
	  }
  



  /**
   * Replace a user's session<br>
   * Is called when user logs from more than one machine
   *
   * @param context Description of the Parameter
   * @param userId  Description of the Parameter
   * @return Description of the Return Value
   */
  public UserSession replaceUserSession(ActionContext context, int userId,Suap suap) {
    

	  removeUser(userId, suap);
	  addUser(context, userId,suap);
    return getUserSession(userId);
  }


  /**
   * Returns the number of active sessions<br>
   * NOTE: Not sure if expired sessions are removed
   *
   * @return Description of the Return Value
   */
  public int size() {
    return sessions.size();
  }
}

