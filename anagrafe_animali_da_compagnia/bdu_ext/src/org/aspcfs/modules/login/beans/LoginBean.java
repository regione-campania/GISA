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
package org.aspcfs.modules.login.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webdav.WebdavServlet;

import org.aspcfs.utils.PasswordHash;

/**
 * Used by the Login module for passing formatted data to the Login JSP
 *
 * @author chris
 * @version $Id: LoginBean.java 24298 2007-12-09 12:06:52Z srinivasar@cybage.com $
 * @created July 2, 2001
 */
public class LoginBean extends GenericBean {
	  private String message = "";
	  private String username = "";
	  private String password = "";
	  private String webdavPassword = "";
	  private String ldapPassword = "";
	  private String redirectTo = null;
	  
	  private double access_position_lat ;
	  private double access_position_lon ;
	  private String access_position_err ;

	  /** Field names of this bean, used in place of hard-coded strings in ObjectValidator and /setup/configure_authentication_validate.jsp
	   They are set in static {} block, so that their string values are not compiled into class files of jasper-processed JSP.
	   Otherwise if these field names were changed in future, Tomcat's work subdirectory would need to be purged, so that JSPs got recompiled.
	   */
	  //public static final String USERNAME, PASSWORD, UNENCRYPTED_PASSWORD;
	  //static {
	  //  USERNAME= "username";
	  //  PASSWORD="password";
	  //  UNENCRYPTED_PASSWORD="unencryptedPassword";
	  //}

	  /**
	   * Default constructor.
	   *
	   * @since 1.0
	   */
	  public LoginBean() { }


	  /**
	   * Sets the Username attribute of the object.
	   *
	   * @param tmp The new Username value
	   * @since 1.0
	   */
	  public void setUsername(String tmp) {
	    this.username = tmp;
	  }


	  /**
	   * Sets the Password attribute of the object to the encrypted value of the
	   * given value.
	   *
	   * @param tmp The new Password value
	   * @since 1.0
	   */
	  public void setPassword(String tmp) {
	    this.password = PasswordHash.encrypt(tmp);
	    //Enrypt the webdav password. 'username' should be already set for a valid webdav password
	    this.webdavPassword = PasswordHash.encrypt(
	        username + ":" +
	            WebdavServlet.CFS_USER_REALM + ":" + tmp);
	    this.ldapPassword = tmp;
	  }

	  /**
	   * Sets the Password Hash
	   *
	   * @param passwordHash The new Password Hash value
	   */
	  public void setPasswordHash(String passwordHash) {
	    this.password = passwordHash;
	  }

	  /**
	   * Sets the Message attribute of the LoginBean object
	   *
	   * @param tmp The new Message value
	   * @since 1.1
	   */
	  public void setMessage(String tmp) {
	    this.message = tmp;
	  }


	  /**
	   * Gets the Username attribute of the object.
	   *
	   * @return The Username value
	   * @since 1.0
	   */
	  public String getUsername() {
	    return username;
	  }


	  /**
	   * Gets the password attribute of the object. The value stored in this object
	   * is encrypted -- this method returns the encrypted value. The original
	   * value is not stored.
	   *
	   * @return The Password value
	   * @since 1.0
	   */
	  public String getPassword() {
	    return password;
	  }


	  /**
	   * Gets the Message attribute of the LoginBean object
	   *
	   * @return The Message value
	   * @since 1.1
	   */
	  public String getMessage() {
	    return message;
	  }


	  /**
	   * Gets the webdavPassword attribute of the LoginBean object
	   *
	   * @return The webdavPassword value
	   */
	  public String getWebdavPassword() {
	    return webdavPassword;
	  }

	  public String getLdapPassword() {
	    return ldapPassword;
	  }

	  public String getRedirectTo() {
	    return redirectTo;
	  }

	  public void setRedirectTo(String redirectTo) {
	    this.redirectTo = redirectTo;
	  }

	  public void checkURL(ActionContext context) {
	    if (redirectTo == null) {
	        String requestedURL = (String) context.getRequest().getAttribute("requestedURL");
	        if (requestedURL != null) {
	          redirectTo = requestedURL;
	        }
	      }

	  }


	public double getAccess_position_lat() {
		return access_position_lat;
	}


	public void setAccess_position_lat(double access_position_lat) {
		this.access_position_lat = access_position_lat;
	}


	public double getAccess_position_lon() {
		return access_position_lon;
	}


	public void setAccess_position_lon(double access_position_lon) {
		this.access_position_lon = access_position_lon;
	}


	public String getAccess_position_err() {
		return access_position_err;
	}


	public void setAccess_position_err(String access_position_err) {
		this.access_position_err = access_position_err;
	}

	public void setAccess_position_lat(String lat)
	{
		  if (!lat.equals(""))
		  {
			  access_position_lat = Double.parseDouble(lat);
		  }
	}


	public void setAccess_position_lon(String lon)
	{
		  if (!lon.equals(""))
		  {
			  access_position_lon = Double.parseDouble(lon);
		  }
	}

	  
	  
	  
}
