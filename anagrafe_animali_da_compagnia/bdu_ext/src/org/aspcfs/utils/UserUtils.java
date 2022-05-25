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
package org.aspcfs.utils;

import org.aspcfs.modules.login.beans.UserBean;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Hashtable;

/**
 * Methods for working with the User object
 *
 * @author matt rajkowski
 * @version $Id: UserUtils.java 24321 2007-12-09 13:44:43Z srinivasar@cybage.com $
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

	/**
	 * Method to get user's contact id
	 * @param request
	 * @param userId
	 * @return contactId
	 */
	public static int getUserContactId(HttpServletRequest request, int userId) {
//		ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute(
//		"ConnectionElement");
		
    	ConnectionPool ce = (ConnectionPool) request.getSession().getServletContext().getAttribute(
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

	public static int getRole(HttpServletRequest request) {
		return ((UserBean) request.getSession().getAttribute("User")).getRoleId();
	}

	public static int getUserAslRif(HttpServletRequest request) {
		return ((UserBean) request.getSession().getAttribute("User")).getSiteId();
	}
	
	public static String getUserUsername(HttpServletRequest request) {
		return ((UserBean) request.getSession().getAttribute("User")).getUsername();
	}
}

