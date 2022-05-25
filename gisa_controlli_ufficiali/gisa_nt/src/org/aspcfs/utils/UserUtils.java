/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import org.aspcfs.modules.login.beans.UserBean;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Hashtable;

/**
 * Methods for working with the User object
 *
 * @author matt rajkowski
 * @version $Id: UserUtils.java 16219 2006-10-09 19:33:21Z matt $
 * @created October 13, 2003
 */
public class UserUtils {

  /**
   * Gets the userId attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userId value
   */
  public static int getUserId(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getUserId();
  }


  /**
   * Gets the userRangeId attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userRangeId value
   */
  public static String getUserIdRange(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getIdRange();
  }


  /**
   * Gets the userRoleType attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userRoleType value
   */
  public static int getUserRoleType(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getRoleType();
  }


  /**
   * Gets the userOrganization attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userOrganization value
   */
  public static int getUserOrganization(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getOrgId();
  }


  /**
   * Gets the userTimeZone attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userTimeZone value
   */
  public static String getUserTimeZone(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getTimeZone();
  }


  /**
   * Gets the userLocale attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userLocale value
   */
  public static Locale getUserLocale(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getLocale();
  }


  /**
   * Gets the userCurrency attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userCurrency value
   */
  public static String getUserCurrency(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getCurrency();
  }


  /**
   * Gets the userSiteId attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userSiteId value
   */
  public static int getUserSiteId(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getSiteId();
  }


  /**
   * Gets the userContactName attribute of the UserUtils class
   *
   * @param request Description of the Parameter
   * @return The userContactName value
   */
  public static String getUserContactName(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getContactName();
  }


  /**
   * Gets the temporaryUser attribute of the UserUtils class
   *
   * @param context Description of the Parameter
   * @return The temporaryUser value
   */
  public static UserBean getTemporaryUserSession(ActionContext context) {
    User userRecord = new User();
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
    userRecord.setCurrency(applicationPrefs.get("SYSTEM.CURRENCY"));
    userRecord.setLanguage(applicationPrefs.get("SYSTEM.LANGUAGE"));
    //userRecord.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));

    UserBean userBean = new UserBean();
    userBean.setUserRecord(userRecord);
    context.getSession().setAttribute("User", userBean);

    return userBean;
  }
  
  
  public static User getUserFormId(HttpServletRequest request,int userid)
  {
	  
	  ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
	  String ceDriver = prefs.get("GATEKEEPER.DRIVER");
	  String ceHost = prefs.get("GATEKEEPER.URL");
	  String ceUser = prefs.get("GATEKEEPER.USER");
	  String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

	  ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	  SystemStatus thisSystem = null; 
	  User user = null;
	  thisSystem = (SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
	  if (thisSystem!=null)
	  {
		  user = (User)thisSystem.getUserList().get(userid);
		  
		  if (user == null)
		  {
			  Connection db = null ; 
			  try
			  {
				  db = GestoreConnessioni.getConnection();
				  user = new User(db,userid);
			  }
			  catch(SQLException e)
			  {
				  
			  }
			  finally
			  {
				  GestoreConnessioni.freeConnection(db);
			  }
		  }
	  }
	  
	  
	  return user ;
  }
  
  
  public static ArrayList<User> getUserFromIdAsl(HttpServletRequest request,int siteId)
  {
	  
	  ArrayList<User> listaUtentiPerAsl = new ArrayList<User>();
	  ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
	  String ceDriver = prefs.get("GATEKEEPER.DRIVER");
	  String ceHost = prefs.get("GATEKEEPER.URL");
	  String ceUser = prefs.get("GATEKEEPER.USER");
	  String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

	  ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	  SystemStatus thisSystem = null; 
	  User user = null;
	  thisSystem = (SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
	  if (thisSystem!=null)
	  {
		  Iterator<Integer> itKey = thisSystem.getUserList().keySet().iterator();
		  while (itKey.hasNext())
		  {
			  User u  = (User)thisSystem.getUserList().get(itKey.next());
			  if (siteId >0)
			  {
			  if (u.getSiteId()==siteId )
			  {
				  listaUtentiPerAsl.add(u);
			  }
			  }else
			  {
				  listaUtentiPerAsl.add(u);
			  }
		  }
	  }
	  return listaUtentiPerAsl ;
  }
  
  
  public static ArrayList<User> getUserFromDpat(HttpServletRequest request,int siteId)
  {
	  
	  ArrayList<User> listaUtentiPerAsl = getUserFromIdAsl(request, siteId);
	  
	  ArrayList<User> listaUtentiPerDpat = new ArrayList<User>();

		  Iterator<User> itKey = listaUtentiPerAsl.iterator();
		  while (itKey.hasNext())
		  {
			  User u  = itKey.next();
			  if (u.isInDpat()==true && u.getEnabledAsQualifica()==true)
			  {
				  listaUtentiPerDpat.add(u);
			  }
		  
	  }
	  return listaUtentiPerDpat ;
  }
  
  
  public static ArrayList<User> getUserFromRole(HttpServletRequest request,int siteId,int idRuolo)
  {
	  
	  ArrayList<User> listaUtentiPerAsl = getUserFromIdAsl(request, siteId);
	  
	  ArrayList<User> listaUtentiPerDpat = new ArrayList<User>();

		  Iterator<User> itKey = listaUtentiPerAsl.iterator();
		  while (itKey.hasNext())
		  {
			  User u  = itKey.next();
			  if (u.getRoleId()==idRuolo)
			  {
				  listaUtentiPerDpat.add(u);
			  }
		  
	  }
	  return listaUtentiPerDpat ;
  }
  
  
  public static ArrayList<User> getUserFromRoleAltreAutorita(HttpServletRequest request,int siteId,int idRuolo)
  {
	  
	  ArrayList<User> listaUtentiPerRole = new ArrayList<User>();
	  ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
	  String ceDriver = prefs.get("GATEKEEPER.DRIVER");
	  String ceHost = prefs.get("GATEKEEPER.URL");
	  String ceUser = prefs.get("GATEKEEPER.USER");
	  String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

	  ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	  SystemStatus thisSystem = null; 
	  User user = null;
	  thisSystem = (SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
	  if (thisSystem!=null)
	  {
		  Iterator<Integer> itKey = thisSystem.getUserList().keySet().iterator();

		  while (itKey.hasNext())
		  {
			  User u  = (User)thisSystem.getUserList().get(itKey.next());
			  if (u.getRoleId()==idRuolo)
			  {
				  listaUtentiPerRole.add(u);
			  }
		  
	  }
	  }
	  return listaUtentiPerRole ;
  }

  /**
   * Method to get user's contact id
   * @param request
   * @param userId
   * @return contactId
   */
  public static int getUserContactId(HttpServletRequest request, int userId) {
    ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute(
        "ConnectionElement");
    if (ce != null) {
      SystemStatus thisSystem = (SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
      if (thisSystem != null) {
        return thisSystem.getUser(userId).getContactId();
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("UserUtils-> getUserContactid: ** UserId is null for " + userId + " **");
    }
    return -1;
  }


public static int getUgpIdGruppoFromRole(int roleId) {
				int idGruppo = -1;
			  Connection db = null ; 
			  try
			  {
				  db = GestoreConnessioni.getConnection();
				  PreparedStatement pst = db.prepareStatement("select id_gruppo from ugp_gruppi_ruoli where id_ruolo = ?");
				  pst.setInt(1, roleId);
				  ResultSet rs = pst.executeQuery();
				  if (rs.next())
					  idGruppo = rs.getInt("id_gruppo");
			      System.out.println("UserUtils-> getUgpIdGruppoFromRole: ** "+pst.toString()+" : "+ idGruppo+" **");

			  }
			  catch(SQLException e)
			  {
				  
			  }
			  finally
			  {
				  GestoreConnessioni.freeConnection(db);
			  }
			  return idGruppo ;
}
}

