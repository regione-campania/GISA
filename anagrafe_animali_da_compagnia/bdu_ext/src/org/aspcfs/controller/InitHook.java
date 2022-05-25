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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.servlets.ControllerInitHook;
import org.aspcfs.utils.web.CustomFormList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Code that is initialized when the ServletController starts.
 *
 * @author mrajkowski
 * @version $Id: InitHook.java,v 1.24.18.1 2003/08/12 21:12:56 mrajkowski Exp
 *          $
 * @created July 10, 2001
 */
public class InitHook implements ControllerInitHook {
  public final static String fs = System.getProperty("file.separator");


  /**
   * When the ServletController is initialized, this code maps init-params to
   * the Context or Application scope.
   *
   * @param config Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeControllerInit(ServletConfig config) {
	System.out.println("InitHook-> Executing");
    ServletContext context = config.getServletContext();
    //Determine the file library path and load the prefs if found
    ApplicationPrefs prefs = new ApplicationPrefs(context);
    context.setAttribute("applicationPrefs", prefs);
    //Define the keystore, to be used by tasks that require SSL certificates
    addAttribute(config, context, "ClientSSLKeystore", "ClientSSLKeystore");
    addAttribute(
        config, context, "ClientSSLKeystorePassword", "ClientSSLKeystorePassword");
    //Read in the default module settings
    addAttribute(
        config, context, "ContainerMenuConfig", "ContainerMenuConfig");
    
    //ELIMINATO IN QUANTO NON CI SERVE E NEL WEB.XML E' STATO ELIMINATO
//    if (config.getInitParameter("DynamicFormConfig") != null) {
//      context.setAttribute(
//          "DynamicFormConfig", config.getInitParameter("DynamicFormConfig"));
//      CustomFormList forms = new CustomFormList(
//          context, config.getInitParameter("DynamicFormConfig"));
//      context.setAttribute("DynamicFormList", forms);
//    }
    
  //Verifica se il db collegato è MASTER O SLAVE
	String mode = "";
	Connection db = null;
//	String DBURL = ApplicationPrefs.getPref(context, "GATEKEEPER.URL");
//	String DBUSER =  ApplicationPrefs.getPref(context, "GATEKEEPER.USER");
//	String DBPASSWORD =  ApplicationPrefs.getPref(context, "GATEKEEPER.PASSWORD");
	try {
		//Class.forName("org.postgresql.Driver");
		//Connection db = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
		db = ((ConnectionPool) config.getServletContext().getAttribute("ConnectionPool")).getConnection(null);
		PreparedStatement pst = db.prepareStatement("show transaction_read_only");
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			mode = rs.getString(1);
		}
		if (mode.equals("on")){
			System.out.println("DB SLAVE");
			context.setAttribute("ambiente","SLAVE");
		}
		else{
			System.out.println("DB MASTER");
			context.setAttribute("ambiente","MASTER");
		}
		pst.close();
		rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} //catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
  
    finally{
    	((ConnectionPool) config.getServletContext().getAttribute("ConnectionPool")).closeConnection(db, null);
    }
	

	BufferedReader br=null;
	try {
		br = new BufferedReader(new FileReader(new File(config.getServletContext().getRealPath("templates/avviso_messaggio_urgente.txt"))));
	
	String mes = "" ;
	String messaggioFinale = "";
	mes = br.readLine();
	while (mes != null)
	{
		messaggioFinale +=mes ;
		mes = br.readLine();
	}
	br.close();
	Timestamp dataUltimaModifica = new Timestamp(System.currentTimeMillis());
	config.getServletContext().setAttribute("MessaggioHome", dataUltimaModifica+"&&"+messaggioFinale);
    
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
    SyncHook.initSync(context);
    return null;
  }


  /**
   * Adds a feature to the Attribute attribute of the InitHook class
   *
   * @param config        The feature to be added to the Attribute attribute
   * @param context       The feature to be added to the Attribute attribute
   * @param attributeName The feature to be added to the Attribute attribute
   * @param paramName     The feature to be added to the Attribute attribute
   */
  private static void addAttribute(ServletConfig config, ServletContext context, String attributeName, String paramName) {
    if (config.getInitParameter(paramName) != null) {
      context.setAttribute(attributeName, config.getInitParameter(paramName));
    }
  }
}

