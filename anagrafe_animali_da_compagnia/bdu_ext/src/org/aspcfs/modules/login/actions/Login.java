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
package org.aspcfs.modules.login.actions;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;

import crypto.nuova.gestione.ClientSCAAesServlet;

import org.aspcfs.controller.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.RolePermission;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserEmail;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.login.utils.CheckLock;
import org.aspcfs.modules.mycfs.actions.MyCFS;
import org.aspcfs.modules.ricerca_microchip.actions.GeneraBarCode;
import org.aspcfs.modules.sync.utils.SyncUtils;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.LookupList;
import org.directwebremoting.WebContext;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * The Login module.
 *
 * @author mrajkowski
 * @version $Id: Login.java 24298 2007-12-09 12:06:52Z srinivasar@cybage.com $
 * @created July 9, 2001
 */
public final class Login extends CFSModule {

  public final static String fs = System.getProperty("file.separator");
  SimpleDateFormat sdfLog = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
  public static Logger logger = Logger.getLogger("MainLogger");
  
  private long ultimoConteggioCani = 0L;

  public String executeCommandDefault(ActionContext context) {
	    // Will need to use the following objects in some way...
	    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	    Connection db = null;
	    //Connection dbvam = null;
	    try {
	    	
	      if (context.getSession().getAttribute("User") != null){
	    	 MyCFS cfs = new MyCFS();
	    	 return cfs.executeCommandHome(context);
	      }
	      
	      
	      
	      Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
	      // Store a ConnectionElement in session for the LabelHandler to find the corresponding language
	    //  ConnectionElement ce = thisSite.getConnectionElement();
	     // ConnectionElement ceVam = thisSite.getConnectionElementVam();
	    //  context.getSession().setAttribute("ConnectionElement", ce);
	    //  context.getSession().setAttribute("ConnectionElementVam", ceVam);
	   //   GestoreConnessioni.setCe(ce);

	      db = getConnection(context);
	     // dbvam = getConnectionVam(context, ceVam);
	      // Load the system status for the corresponding site w/specified language
	      //SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce, thisSite.getLanguage());
	    } catch (Exception e) {
	      System.out.println("Login-> Default error: " + e.getMessage());
	    } finally {
	      freeConnection(context, db);
	     // freeConnectionVam(context, dbvam);
	    }
	    // Determine entry page
	    String scheme = context.getRequest().getScheme();
	    // If SSL is configured, but this user isn't using SSL, then go to the welcome page
	    if ("true".equals((String) context.getServletContext().getAttribute("ForceSSL")) &&
	        scheme.equals("http")) {
	      context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.WELCOME"));
	    } else {
	      context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.LOGIN"));
	    }
	    return "IndexPageOK";
	  }


  /**
   * Processes the user login
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public String executeCommandLogin(ActionContext context) {
	    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
	            "applicationPrefs");
	        //Process the login request
	        LoginBean loginBean = (LoginBean) context.getFormBean();
	        loginBean.checkURL(context);
	        
	        //POSITION
//	        if (context.getParameter("access_position_lat") != null && !"".equals(context.getParameter("access_position_lat")))
//	     	   loginBean.setAccess_position_lat(context.getParameter("access_position_lat"));
//	        
//	        if (context.getParameter("access_position_lon") != null && !"".equals(context.getParameter("access_position_lon")))
//	     	   loginBean.setAccess_position_lon(context.getParameter("access_position_lon"));
//	        
//	        if (context.getParameter("access_position_err")!=null)
//	     	   loginBean.setAccess_position_err(context.getParameter("access_position_err"));
	        
	        
	        String username = loginBean.getUsername();
	        String password = loginBean.getPassword();
	        String serverName = context.getRequest().getServerName();
	        // Throw out empty passwords
	        if (password == null || "".equals(password.trim())) {
	          return "LoginRetry";
	        }
	        //Prepare the gatekeeper
//	        String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
//	        String gkHost = getPref(context, "GATEKEEPER.URL");
//	        String gkUser = getPref(context, "GATEKEEPER.USER");
//	        String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
	        String siteCode = getPref(context, "GATEKEEPER.APPCODE");
	     //   ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
	     //   gk.setDriver(gkDriver);
	        //Prepare the database connection
	        ConnectionPool sqlDriver =
	            (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
	        if (sqlDriver == null) {
	          loginBean.setMessage("Connection pool missing!");
	          return "LoginRetry";
	        }
	        
	        

			JSONObject jsonObj = ApplicationProperties.checkBrowser(context.getRequest().getHeader("User-Agent"));
			String msg = "" ;
			if (jsonObj!=null)
			{
				if (jsonObj.getString("esito").equals("1"))
				{
					msg = jsonObj.getString("msg");
					loginBean.setMessage(msg);
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetry";
				}
				
				if (jsonObj.getString("esito").equals("2"))
				{
					 msg = jsonObj.getString("msg");
				}
				

				
			}
	        
	        
	        Connection db = null;
	      //  ConnectionElement ce = null;
	        //Connect to the gatekeeper, validate this host and get new connection info
	        try {
	          if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE")))
	          {
	            //Scan for the virtual host
	          //  db = sqlDriver.getConnection(gk);
	            SiteList siteList = new SiteList();
	            siteList.setSiteCode(siteCode);
	            siteList.setVirtualHost(serverName);
	            siteList.buildList(db);
	            if (siteList.size() > 0) {
	              Site thisSite = (Site) siteList.get(0);
//	              ce = new ConnectionElement(
//	                  thisSite.getDatabaseUrl(),
//	                  thisSite.getDatabaseUsername(),
//	                  thisSite.getDatabasePassword());
	           //   ce.setDbName(thisSite.getDatabaseName());
	            //  ce.setDriver(thisSite.getDatabaseDriver());
	            } else {
	              loginBean.setMessage(
	                  "* Access denied: Host does not exist (" +
	                      serverName + ")");
	            }
	          } else {
	            //A single database is configured, so use it only regardless of ip/domain name
	         //   ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
	          //  ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
	        //    ce.setDriver(gkDriver);
	          }
	        } catch (Exception e) {
	          loginBean.setMessage("* Gatekeeper: " + e.getMessage());
	        } finally {
	          if (db != null) {
	            sqlDriver.free(db, null);
	          }
	        }
//	        if (ce == null) {
//	          return "LoginRetry";
//	        }
	        // Connect to the customer database and validate user
	        UserBean thisUser = null;
	        int userId = -1;
	        int aliasId = -1;
	        int roleId = -1;
	        String role = null;
	        String userId2 = null;
	        java.util.Date now = new java.util.Date();
	        boolean continueId = false;
	        ArrayList<String> coordinate = new ArrayList<String>();
	        try {
	          SystemStatus thisSystem = null;

	          db = sqlDriver.getConnection(null);
	          
	        //CERCO DI ENTRARE
				
				//CHECK LOCK
					if (CheckLock.checkLocked(db, context.getIpAddress(), username)){
						context.getRequest().setAttribute("messaggio", "Accesso bloccato a causa dei troppi tentativi falliti. Attendere circa 3 minuti per riprovare.");
						return "LoginRetry";
					}
				//FINE CHECK LOCK
					
	          // If system is not upgraded, perform lightweight validation to ensure backwards compatibility
	          if (applicationPrefs.isUpgradeable()) {
	            continueId = true;
	          } else {
	            //A good place to initialize this SystemStatus, must be done before getting a user
	            Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
	            thisSystem = SecurityHook.retrieveSystemStatus(
	                context.getServletContext(), db, thisSite.getLanguage());
	            if (System.getProperty("DEBUG") != null) {
	              System.out.println("Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
	            }
	            continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
	          }
	          //Query the user record
	          int id = 0;
	          String pw = null;
	          String tpw = null;
	          java.sql.Date expDate = null;
	          int tmpUserId = -1;
	          int roleType = -1;
	          if (continueId) {
	            // NOTE: The following is the simplest valid SQL that works
	            // on all versions of Concourse Suite Community Edition.  It must not be
	            // modified with new fields because .war users need to
	            // be able to login first, before the upgrade has happened
	            PreparedStatement pst = db.prepareStatement(
	                "SELECT a.user_id, a." + DatabaseUtils.addQuotes(db, "password") + ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role") + ", a.expires, a.alias, a.user_id, r.role_type " +
	                    "FROM " + DatabaseUtils.addQuotes(db, "access") + " a, " + DatabaseUtils.addQuotes(db, "role") + " r " +
	                    "WHERE a.role_id = r.role_id " +
	                    "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
	                    "AND a.enabled = ? and a.trashed_date is null ");
	            pst.setString(1, username.toLowerCase());
	            pst.setBoolean(2, true);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	              id = rs.getInt("user_id");
	              pw = rs.getString("password");
	              roleId = rs.getInt("role_id");
	              role = rs.getString("role");
	              expDate = rs.getDate("expires");
	              aliasId = rs.getInt("alias");
	              tmpUserId = rs.getInt("user_id");
	              roleType = rs.getInt("role_type");
	            }
	            rs.close();
	            pst.close();
	            if (tmpUserId == -1) {
	            	
	            	//CHECK LOCK
					CheckLock.incLock(db, context.getIpAddress(), username);
					//FINE CHECK LOCK
					
	              // NOTE: This could be modified so that LDAP records get inserted into CRM on the fly if they do not exist yet

	              // User record not found in database
	              loginBean.setMessage(
	                  "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
	              if (System.getProperty("DEBUG") != null) {
	                System.out.println("Login-> User record not found in database for: " + username.toLowerCase());
	              }
	              MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(),context);
	            } else if (expDate != null && now.after(expDate)) {
	              // Login expired
	              loginBean.setMessage(
	                  "* " + thisSystem.getLabel("login.msg.accountExpired"));
	              MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(),context);
	            } else {
	              // User exists, now verify password
	              boolean ldapEnabled = "true".equals(applicationPrefs.get("LDAP.ENABLED"));
	              if (ldapEnabled && roleType == Constants.ROLETYPE_REGULAR) {
	                // See if the CRM username and password matches in LDAP
	                int ldapResult = LDAPUtils.authenticateUser(applicationPrefs, db, loginBean);
	                if (ldapResult == LDAPUtils.RESULT_VALID) {
	                  userId = tmpUserId;
	                }
	              } else {
	                // Validate against Concourse Suite Community Edition for PortalRole users
	            			
	                if ((pw == null || pw.trim().equals("") ||
	                   (!pw.equals(password) && !context.getServletContext().getAttribute("GlobalPWInfo").equals(password)))){
	                	
	                	//LOGIN ERRATA
						
						//CHECK LOCK
						CheckLock.incLock(db, context.getIpAddress(), username);
						//FINE CHECK LOCK
						
						
	                  PreparedStatement ps = db.prepareStatement(
	                    	"SELECT  a.temp_password " +
	                    	      "FROM " + DatabaseUtils.addQuotes(db, "access") + " a, " + DatabaseUtils.addQuotes(db, "role") + " r " +
	                    	       "WHERE a.role_id = r.role_id " +
	                    	       "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
	                    	       "AND a.enabled = ? and a.trashed_date is null ");
	                  ps.setString(1, username.toLowerCase());
	                  ps.setBoolean(2, true);
	                  ResultSet r = ps.executeQuery();
	                  if (r.next()) {
	                  	tpw = r.getString("temp_password");
	                  }
	                  r.close();
	                  ps.close();            
	                	if ((tpw == null || tpw.trim().equals("") ||
	                	(!tpw.equals(password) && !context.getServletContext().getAttribute("GlobalPWInfo").equals(password)))) {
	                	   loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
	                	   MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(),context);
	                	} else {
	                		if (tpw != null){
	                		   User user = new User();
	                		   user.overwritePassword(db, password, username, id);
	                		}
	                		userId = tmpUserId;
	                	}
	                } else {
	                	userId = tmpUserId;
	                	
	                	//LOGIN CORRETTA
						
						//CHECK LOCK
						CheckLock.resetLock(db, context.getIpAddress(), username);
						//FINE CHECK LOCK
	                }
	              }
	            }
	          }
	          //Perform rest of user initialization if a valid user
	          if (userId > -1) {
	            thisUser = new UserBean();
	            thisUser.setSessionId(context.getSession().getId());
	            thisUser.setUserId(aliasId > 0 ? aliasId : userId);
	            thisUser.setActualUserId(userId);
	          //  thisUser.setConnectionElement(ce);
	            thisUser.setConnectionPoll(sqlDriver);
	            thisUser.setClientType(context.getRequest());
	            if (thisSystem != null) {
	              //The user record must be in user cache to proceed
	              User userRecord = thisSystem.getUser(thisUser.getUserId());
	              
	              //COSTRUISCO I DETTAGLI DEL CONTATTO PER AVER NUM TELEFONICO E EMAIL DI VET PRIVATI IN HPAGE
	              userRecord.setBuildContact(true);
	              userRecord.setBuildContactDetails(true);
	              System.out.println(db.isClosed());
	              userRecord.buildResources(db);
	              System.out.println(db.isClosed());
	              if (userRecord != null) {
	            	  if (System.getProperty("DEBUG") != null) {
		                  System.out.println("Login-> Retrieved user from memory: " + userRecord.getUsername());
		                } 
	            	  	//CHECK LAST LOGIN
	            	  	if(userRecord.getLastLogin()!=null && !userRecord.getLastLogin().equals("")){
							String s = new SimpleDateFormat("dd/MM/yyyy").format(userRecord.getLastLogin());
							String timeout = ApplicationProperties.getProperty("timeout");
							int time = Integer.parseInt(timeout);
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.MONTH, -time);
							Timestamp calTime = new Timestamp(cal.getTimeInMillis());
							//Se la last login è antecedente al timeout
							if(userRecord.getLastLogin().before(calTime)){
								logger.info(" - [bdu] - Login Fallito: ip=" + context.getIpAddress() + " username="
										+ username);
								context.getRequest().setAttribute("messaggio", "ATTENZIONE! NON PUOI ACCEDERE AL SISTEMA IN QUANTO IL TUO ACCOUNT RISULTA DISATTIVATO. "
										+ "IL TUO ULTIMO ACCESSO RISALE AL GIORNO "+s+". PER ESSERE RIATTIVATO, SI PREGA DI CONTATTARE IL SERVIZIO DI HD I LIVELLO.");
								return "LoginRetry";
							} else {
								
								thisUser.setIdRange(userRecord.getIdRange());
								thisUser.setUserRecord(userRecord);
				                //Log that the user attempted login (does not necessarily mean logged in
				                //anymore due to the single-session manager below
				                userRecord.setIp(context.getIpAddress());
				                //POSITION
			//	                userRecord.setAccess_position_lat(loginBean.getAccess_position_lat());
			//	                userRecord.setAccess_position_lon(loginBean.getAccess_position_lon());
			//	                userRecord.setAccess_position_err(loginBean.getAccess_position_err());
				                System.out.println(db.isClosed());
				                if (db.isClosed())
				                	  db = sqlDriver.getConnection(null);
				                else {
				                	db.close();
				                	db = sqlDriver.getConnection(null);
				                }
				                userRecord.updateLogin(db);
				                System.out.println(db.isClosed());
				                
				            //    coordinate = userRecord.getCoordinateUltimoAccesso(db);
				                
				                
				                if(!CFSModule.isOfflineMode(context)){
				                  userRecord.checkWebdavAccess(
				                      db, context.getRequest().getParameter("password"));
				                }
							}   
	            	  	}//fine last login
	              }//fine userRecord
	              if (!thisSystem.hasPermissions()) {
	                System.out.println("Login-> This system does not have any permissions loaded!");
	              }
	            } else {
	              if (System.getProperty("DEBUG") != null) {
	                System.out.println("Login-> Fatal: User not found in this System!");
	              }
	            }
	          } else {
	            if (System.getProperty("DEBUG") != null) {
	              System.out.println("Login-> Fatal: User does not have an Id!");
	            }
	          }
	        } catch (Exception e) {
	          loginBean.setMessage("* Access: " + e.getMessage());
	        //  if (System.getProperty("DEBUG") != null) {
	            e.printStackTrace(System.out);
	        //  }
	          thisUser = null;
	        } finally {
	          if (db != null) {
	            sqlDriver.free(db, null);
	          }
	        }

	        try{
	          if (SyncUtils.isOfflineMode(applicationPrefs)){
	            //Check state of Offline application
	            SyncUtils.checkOfflineState(context.getServletContext());
	            if(SyncUtils.isSyncConflict(applicationPrefs)){
	              RolePermission.setReadOnlyOfflinePermissionsForAll(db, (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(sqlDriver.getUrl()));
	            }
	          }
	        } catch (SQLException e) {
	          loginBean.setMessage("* Access: " + e.getMessage());
	          thisUser = null;
	        } finally {
	          if (db != null) {
	            sqlDriver.free(db, null);
	          }
	        }

	        //If user record is not found, ask them to login again
	        if (thisUser == null) {
	          if (isOfflineMode(context)) {
	            //Offline Database Corrupt
	            return "OfflineLoginERROR";
	          }
	          return "LoginRetry";
	        }

	        //A valid user must have this information in their session, or the
	        //security manager will not let them access any secure pages
	        context.getSession().setAttribute("User", thisUser);
	        /**
	         * AGGIUNGO IN SESSIONE ANCHE USER_ID PER VERIFICARE CORRETTEZZA UTENTE LOGGATO
	         */
	        context.getSession().setAttribute("UserID", thisUser.getUserId());
	        if (System.getProperty("DEBUG") != null) 
	        	System.out.println("EMAIL SETTATA: "  +thisUser.getContact().getPrimaryEmailAddress());
	       // context.getSession().setAttribute("ConnectionElement", ce);
	       // GestoreConnessioni.setCe(ce);
	       
	        GestoreConnessioni.setContext(context);
	        if (applicationPrefs.isUpgradeable()) {
	          if (roleId == 1 || "Administrator".equals(role)) {
	            context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
	            return "PerformUpgradeOK";
	          } else {
	            return "UpgradeCheck";
	          }
	        } else {
	          //Check to see if user is already logged in.
	          //If not then add them to the valid users list
	          SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(sqlDriver.getUrl());
	          SessionManager sessionManager = thisSystem.getSessionManager();
	          if (sessionManager.isUserLoggedIn(userId)) {
	            UserSession thisSession = sessionManager.getUserSession(userId);
	            //context.getSession().setMaxInactiveInterval(300);
	            context.getRequest().setAttribute("Session", thisSession);
	            
//	            context.getRequest().setAttribute("access_position_lat", loginBean.getAccess_position_lat()+"");
//	            context.getRequest().setAttribute("access_position_lon", loginBean.getAccess_position_lon()+"");
//	            context.getRequest().setAttribute("access_position_err", loginBean.getAccess_position_err()+"");
	            
	            
	            return "LoginVerifyOK";
	          }
	          if (System.getProperty("DEBUG") != null) {
	            System.out.println("Login-> Session Size: " + sessionManager.size());
	          }
	          //context.getSession().setMaxInactiveInterval(
	              //thisSystem.getSessionTimeout());
	          sessionManager.addUser(context, userId);
	          
	          if (coordinate.size()>0)
	          {  
	        	  
	        	  UserSession thisSession = (UserSession)sessionManager.getSessions().get(userId);
	        	    
//	        	  thisSession.setAccess_position_lat(coordinate.get(0));
//	        	  thisSession.setAccess_position_lon(coordinate.get(1));
//	        	  thisSession.setAccess_position_date(coordinate.get(3));
//	        	  thisSession.setAccess_position_err("Ultime coordinate del"+coordinate.get(3))  ;
	          
	          sessionManager.getSessions().put(userId, thisSession);
	          }
	        }
	        
	        if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente")!=null &&
	    			ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
	    	    if (userId>0){
	    		    ArrayList<UserOperation> op = new ArrayList<UserOperation>();
	    			UserOperation uo = new UserOperation();
	    			uo.setUser_id(thisUser.getUserId());
	    			uo.setUsername(thisUser.getUsername());
	    			uo.setIp(thisUser.getUserRecord().getIp());
	    			uo.setData(new Timestamp(new java.util.Date().getTime()));
	    			uo.setUrl(context.getRequest().getRequestURL().toString()+(context.getRequest().getQueryString()!=null ? "?"+context.getRequest().getQueryString() : ""));
	    			uo.setParameter("");
	    			uo.setUserBrowser(context.getRequest().getHeader("user-agent"));
	    			uo.setUser_id_session(thisUser.getUserId());
	    			op.add(uo);
  						
	    			MiddleServlet.writeStorico(op, "", false); 
	    	    }
	        }
	        
	        
	        // TODO: Replace this so it does not need to be maintained
	        // NOTE: Make sure to update this similar code in the following method
	        String redirectTo = "MyCFS.do?command=Home";
//	        redirectTo+="&Message="+msg;
	        
	        context.getRequest().removeAttribute("PageLayout");
	        if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
	          if (redirectTo != null) {
	        	  context.getRequest().setAttribute("Message", msg);

	        	  context.getRequest().setAttribute("redirectUrl", redirectTo);
	            //context.getRequest().removeAttribute("PageLayout");
	            return "RedirectURL";
	          }
	          return "LoginOK";
	        } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
	          return "CustomerPortalLoginOK";
	        } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
	          return "ProductsPortalLoginOK";
	        }
	        if (redirectTo != null) {
	        	context.getRequest().setAttribute("redirectUrl", redirectTo);
	          //context.getRequest().removeAttribute("PageLayout");
	          return "RedirectURL";
	        }
	        return "LoginOK";
	      }


  /**
   * Confirms if the user wants to ovreride previous session or not.<br>
   * and informs Session Manager accordingly.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandLoginConfirm(ActionContext context) {
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    if (thisUser == null) {
	      try {
			return executeCommandLogout(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    String action = context.getRequest().getParameter("override");
	    if ("yes".equals(action)) {
	        ConnectionPool sqlDriver =
	                (ConnectionPool) context.getSession().getServletContext().getAttribute("ConnectionPool");
	       
	      SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(sqlDriver.getUrl());
	      //context.getSession().setMaxInactiveInterval(systemStatus.getSessionTimeout());
	      //replace userSession in SessionManager HashMap & reset timeout
	      if (System.getProperty("DEBUG") != null) {
	        System.out.println("Login-> Invalidating old Session");
	      }
	      SessionManager sessionManager = systemStatus.getSessionManager();
	      sessionManager.replaceUserSession(context, thisUser.getActualUserId());
	           
	      // TODO: Replace this so it does not need to be maintained
	      // NOTE: Make sure to update this similar code in the previous method
	      if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
	        ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	        if (applicationPrefs.isUpgradeable()) {
	          if (thisUser.getRoleId() == 1 || "Administrator".equals(thisUser.getRole()))
	          {
	            return "PerformUpgradeOK";
	          } else {
	            return "UpgradeCheck";
	          }
	        }
	        String redirectTo = context.getRequest().getParameter("redirectTo");
	        if (redirectTo != null) {
	          context.getRequest().removeAttribute("PageLayout");
	          context.getRequest().setAttribute("redirectUrl", redirectTo);
	          return "RedirectURL";
	        }
	        
	        if ((String) context.getParameter("microchip") != null && !("").equals((String) context.getParameter("microchip"))){
	        	//RegistrazioniAnimale registrazioniAnimale = new RegistrazioniAnimale();
	        	//registrazioniAnimale.executeCommandAdd(context);
	        	context.getSession().setAttribute("loginNoPassword", "true");
	        	
	        	String idTipologiaRichiestaDaVam = (String) context.getParameter("idTipologiaEvento");
	        	String dataRegistrazione = (String) context.getParameter("dataRegistrazione");
	        	
	        	int idTipoEvento = -1;
	        	if (idTipologiaRichiestaDaVam != null && !("").equals(idTipologiaRichiestaDaVam) )
	        		 idTipoEvento = new Integer(idTipologiaRichiestaDaVam);
	        	if (dataRegistrazione != null && !("").equals(dataRegistrazione) )
	        		context.getRequest().setAttribute("dataRegistrazione", dataRegistrazione);
	        	
	 		   if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
				   context.getSession().setAttribute("caller", (String) context.getRequest().getParameter("caller"));
	 		   else	
	 			  context.getSession().setAttribute("caller", null);
	 		   //context.getRequest().setAttribute("loginNoPassword", "true");
	        	if (idTipoEvento == 1)
	        		return "AggiungiAnimaleOK";
	        	
	        	return "AggiungiRegistrazioneOK";
	        	}else if ((String) context.getParameter("tipologiaEvento") != null && !("").equals((String) context.getParameter("tipologiaEvento"))){
	        		context.getSession().setAttribute("loginNoPassword", "true");
	        		
	        		if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
	     			    context.getSession().setAttribute("caller", (String) context.getRequest().getParameter("caller"));
	        		else
	        			context.getSession().setAttribute("caller", null);
	        		
	        		return "DettaglioRegistrazioneOK";
	        }
	        else{
	        	return "LoginOK";
	        }
	      } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
	        return "CustomerPortalLoginOK";
	      } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
	        return "ProductsPortalLoginOK";
	      }
	    } else {
	      //logout user from current session
	      try {
			return executeCommandLogout(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    String redirectTo = context.getRequest().getParameter("redirectTo");
	    if (redirectTo != null) {
	      context.getRequest().removeAttribute("PageLayout");
	      return "RedirectURL";
	    }
	    
	    

	    return "LoginOK";
	  }


  /**
   * Used for invalidating a user session
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
 /* public String executeCommandLogout(ActionContext context) {
    HttpSession oldSession = context.getRequest().getSession(false);
    if (oldSession != null) {
      oldSession.removeAttribute("User");
      oldSession.invalidate();
    }
    return "LoginRetry";
  }
*/
  public String executeCommandLogout(ActionContext context) throws SQLException {

		String ambiente = (String) context.getSession().getAttribute("ambiente");
			
		System.out.println("######## AMBNIENTE:"+ambiente);
		
		if (ambiente != null && context.getRequest().getAttribute("isCambioUtente") == null){// && ambiente.equalsIgnoreCase("sirv")) {
			return executeCommandLoginSirv(context);
		} else {
			
			HttpSession oldSession = context.getRequest().getSession(false);
			if (oldSession != null) {
				oldSession.removeAttribute("User");
				oldSession.invalidate();
			}
			
			return "LoginRetry";
		}
	}
  
  public static  String NEWencrypt(String input, URL url){
		byte[] crypted = null;
		try{
			//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			SecretKeySpec skey = getKeySpec(url.getPath());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));
	}

  
  public String executeCommandLogout(WebContext context) {

			HttpSession oldSession = context.getHttpServletRequest().getSession(false);
			if (oldSession != null) {
				oldSession.removeAttribute("User");
				oldSession.invalidate();
			}
			
			return "LoginRetry";
	}
	
	 public String executeCommandLoginSirv(ActionContext context){
		 
     
		try {  
		
			
		  //String SIRV_URL = InetAddress.getByName("endpointAPPSirv").getHostAddress();
		  //String SIRV_URL = ApplicationProperties.getProperty("ambiente_sirv");
		  
		String SIRV_URL= (String) context.getSession().getAttribute("ambiente");
			
		 //String SIRV_URL = ((InetAddress)((HashMap<String, InetAddress>)context.getSession().getServletContext().getAttribute("hosts")).get("srvSCAW")).getHostAddress();
		  System.out.println("#LOGOUT ---> LOGINSIRV TO: "+SIRV_URL);
		  HttpSession oldSession = context.getRequest().getSession(false);
		  UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		  
		  String cf_spid = context.getParameter("cf_spid");
			String encryptedToken = null;
			if(true)
			//if(cf_spid==null || cf_spid.equalsIgnoreCase("") || cf_spid.equalsIgnoreCase("null"))
			{
				String originalToken = System.currentTimeMillis() + "@" + thisUser.getUsername();

				/*vecchia gestione
				 * encryptedToken = NEWencrypt(originalToken, this.getClass().getResource("aes_key"));
				 */
				ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				encryptedToken = cclient.crypt(originalToken);
			}
			
			//Se vieni dal sistema SIRV..
	     
      //context.getResponse().sendRedirect( "http://"+SIRV_URL+"/login.LoginNoPassword.us?encryptedToken="+generate(thisUser.getUsername()));
	      oldSession.removeAttribute("User");
		  oldSession.invalidate();
	      //context.getResponse().sendRedirect( /*"http://"+*/SIRV_URL+"/login.LoginNoPassword.us?encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
		  if(false)
	      //if(cf_spid==null || cf_spid.equalsIgnoreCase("") || cf_spid.equalsIgnoreCase("null"))
			{
	    	    context.getRequest().setAttribute("url", SIRV_URL+"/login.LoginNoPassword.us?encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
			}
			else
			{
				SIRV_URL = SIRV_URL.replace("/sca", "/login");
				SIRV_URL = SIRV_URL.replace("https://login", "https://sca");
				SIRV_URL = SIRV_URL.replace("https:/login", "https://sca");
				context.getRequest().setAttribute("url",SIRV_URL + "/login.Login.us?cf_spid=" + cf_spid + "&tk_spid=" +URLEncoder.encode(encryptedToken,"UTF-8"));
			}
	      
	      
		  return "RedirectSCA"; 
         
    } catch (Exception e) {
             // TODO: handle exception
     }
       
       return "";
}
	 

	 
	  public  String generate( String username )
  {
          String ret = null;

          String                        originalToken        = System.currentTimeMillis() + "@" + username;
          byte[]                        encryptedToken        = null;
          KeyGenerator        kgen                        = null;
          
          try
          {
                  kgen                                                = KeyGenerator.getInstance("AES");
                  kgen.init(128);
          //        SecretKeySpec        skeySpec        = new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
                  SecretKeySpec skeySpec = getKeySpec( this.getClass().getResource("aes_key").getPath());
                  Cipher                         cipher                = Cipher.getInstance("AES");
                  cipher.init( Cipher.ENCRYPT_MODE, skeySpec );
                  BASE64Encoder enc = new BASE64Encoder();
                  encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes() ;
          }
          catch ( Exception e )
          {
                  e.printStackTrace();
          }

          ret = asHex(encryptedToken);

          return ret;
  }
	
	  public static String asHex (byte buf[] )
  {
          StringBuffer sb = new StringBuffer(buf.length * 2);
          for( int i = 0; i < buf.length; i++ )
          {
                  if( ((int) buf[i] & 0xff) < 0x10 )
                  {
                          sb.append("0");
                  }
                  sb.append(Long.toString((int) buf[i] & 0xff, 16));
          }
          
          return sb.toString();
  }

  
  public String executeCommandForgotPassword(ActionContext context) {
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		if (applicationPrefs.isUpgradeable()) {
			String forg = "No";
			context.getRequest().setAttribute("forgo", forg);
			return "ForgotPasswordOK";
		}
		String forg = "Yes";
		context.getRequest().setAttribute("forgo", forg);
		return "ForgotPasswordOK";
	}

	public String executeCommandGenerateTemporaryPassword(ActionContext context) {
		Connection db = null;
		try {
			db = this.getConnection(context);
			// building Users List
			String username = (String) context.getRequest().getParameter("username");
			UserList userList = new UserList();
			userList.setUsername(username);
			userList.setBuildContact(true);
			userList.setBuildContactDetails(true);
			userList.buildList(db);
			if (userList.size() > 0) {
				User user = (User) userList.get(0);
				// Preparing the EMail
				String newPassword = String.valueOf(StringUtils.rand(100000, 999999));
				String templateFile = getDbNamePath(context) + "templates_en_US.xml";
				UserEmail userEmail = new UserEmail(context, user, username, newPassword, this.getSystemStatus(context).getUrl(), templateFile);
				// Sending Email
				SMTPMessage mail = new SMTPMessage();
				String email = user.getContact().getEmailAddress(1);
				mail.setHost(getPref(context, "MAILSERVER"));
				mail.setFrom(getPref(context, "EMAILADDRESS"));
				mail.addReplyTo(email, user.getContact().getNameFirst());
				mail.setType("text/html");
				mail.setSubject("New Password");
				mail.setBody(userEmail.getBody());
				mail.addTo(email);
				if (mail.send() == 2) {
					System.out.println("Forgot Password -> Send error: " + mail.getErrorMsg() + "\n");
				} else {
					user.insertNewPassword(db, newPassword);
				}
			} else {
				context.getRequest().setAttribute("retu", "No");
				return executeCommandForgotPassword(context);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			freeConnection(context, db);
		}
		return "PasswordGeneratedOK";
	}
	
	
	  
	  public static String decrypt(String text,URL url) throws Exception {
		  if(url == null)
			  throw new FileAesKeyException("File aes_key not found");

		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.DECRYPT_MODE, spec);
		  BASE64Decoder dec = new BASE64Decoder();
		return (new String(cipher.doFinal(dec.decodeBuffer(text))));
	  }
	  
	  public static byte[] lenientHexToBytes(String hex) {
	      byte[] result = null;
	      if (hex != null) {
	          // remove all non alphanumeric chars like colons, whitespace, slashes
	          //hex = hex.replaceAll("[^a-zA-Z0-9]", "");
	          // from http://forums.sun.com/thread.jspa?threadID=546486
	          // (using BigInteger to convert to byte array messes up by adding extra 0 if first byte > 7F and this method
	          //  will not rid of leading zeroes like the flawed method 
	          
	          //byte[] bts = new BigInteger(hex, 16).toByteArray();)
	          result = new byte[hex.length() / 2];
	          for (int i = 0; i < result.length; i++) {
	              result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	          }
	      }
	   
	      return result;
	  }
	  
	  public static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
		    byte[] bytes = new byte[16];
		    File f = new File(path.replaceAll("%20", " "));
		   
		    SecretKeySpec spec = null;
		    if (f.exists()) 
		    {
		      new FileInputStream(f).read(bytes);
		      
		    } else {
		      /* KeyGenerator kgen = KeyGenerator.getInstance("AES");
		       kgen.init(128);
		       key = kgen.generateKey();
		       bytes = key.getEncoded();
		       new FileOutputStream(f).write(bytes);*/
		    	throw new FileAesKeyException("File aes_key not found");
		    	
		    }
		    spec = new SecretKeySpec(bytes,"AES");
		    return spec;
		  }
	  
	  public String executeCommandLoginNoPassword(ActionContext context) throws SQLException, UnknownHostException, ParseException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException {
		//  String prefisso = PrefissoWrapper.valutaPrefissoWrapper(context);  
		  String prefisso = "";
		  ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		    //LogBean lb = new LogBean();
		    //Process the login request
		    String[] params = null ; 
		    String decrypteToken = "" ;
		    String encrypteToken = context.getParameter("encryptedToken");
		    if (System.getProperty("DEBUG") != null) {
		    	System.out.println( context.getRequest().getRemoteAddr());
		    	System.out.println("DD   "+context.getRequest().getRequestURL());
		    	System.out.println("REFERER:   " +context.getRequest().getHeader("referer"));
		    }
		   
		   String referer = (String)context.getRequest().getHeader("referer");
		   context.getSession().setAttribute("referer", referer); //PER EVITARE CONTROLLO RICOVERO IN AGGIUNTA REGISTRAZIONI
		   

		   
		   
		   
		   
			   
		 //   String vam_user=context.getParameter("vam_user");
		    
		/*	if (vam_user!=null){
			    context.getRequest().setAttribute("vam_user",vam_user);
			}*/   
		    long loginTime =0;
			  try {
				  
				  
				  //decrypteToken = NEWdecrypt(new String(lenientHexToBytes(encrypteToken)), this.getClass().getResource("aes_key"));
//				   decrypteToken = NEWdecrypt( new String((encrypteToken)), getKeySpecByte(this.getClass().getResource("aes_key").getPath()));
				   
				  ClientSCAAesServlet cclient = new ClientSCAAesServlet( );
				  decrypteToken = cclient.decrypt( encrypteToken);
				  params = decrypteToken.split("@");
				  loginTime = Long.parseLong(params[0]);
				  
			  }  catch(Exception e){
				  context.getRequest().setAttribute("dettagli_problema", "Si è verificato un problema nella decriptazione del token ");
				  e.printStackTrace();
				  return prefisso + "LoginRetry";
			  }
		     
		    
			// long loginTime = Long.parseLong(params[0]);
			 long currTime = System.currentTimeMillis();
			
			 if(currTime-loginTime>15*1000*60)
			 {
				 context.getRequest().setAttribute("dettagli_problema", "Token non più valido");
				 return prefisso + "LoginRetry";
			 }
			
			
			String timeToLog = sdfLog.format( new Date(System.currentTimeMillis()) );
		    String interactiveMode = context.getRequest().getParameter("interactiveModeAddCane");
		    context.getRequest().setAttribute("interactiveModeAddCane", interactiveMode);
		    LoginBean loginBean = (LoginBean) context.getFormBean();
		    
		    JSONObject jsonObj = ApplicationProperties.checkBrowser(context.getRequest().getHeader("User-Agent"));
			String msg = "" ;
			if (jsonObj!=null)
			{
				if (jsonObj.getString("esito").equals("1"))
				{
					msg = jsonObj.getString("msg");
					loginBean.setMessage(msg);
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetry";
				}
				
				if (jsonObj.getString("esito").equals("2"))
				{
					 msg = jsonObj.getString("msg");
				}
				

				
			}
			
		    loginBean.checkURL(context);
		    String username = params[1];
			if (params.length>2)
			{
				String ambiente = params[2];
				System.out.println("ambiente"+ambiente);
				context.getSession().setAttribute("ambiente", ambiente);
			}
				    
		    //String password = loginBean.getPassword();
		    logger.info( "[BDU Login No Password] - username = "+username + " data = "  + timeToLog );
		    String serverName = context.getRequest().getServerName();
		    // Throw out empty passwords
//		    if (password == null || "".equals(password.trim())) {
//		      return "LoginRetry";
//		    }
		    //Prepare the gatekeeper
//		    String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
//		    String gkHost = getPref(context, "GATEKEEPER.URL");
//		    String gkUser = getPref(context, "GATEKEEPER.USER");
//		    String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
		    String siteCode = getPref(context, "GATEKEEPER.APPCODE");
//		    ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
//		    gk.setDriver(gkDriver);
		    //Prepare the database connection
		    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
		    if (sqlDriver == null) {
		      loginBean.setMessage("Connection pool missing!");
		      return "LoginRetry";
		    }
		    Connection db = null;
		   // ConnectionElement ce = null;
		    //Connect to the gatekeeper, validate this host and get new connection info
		    try {
		      if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE")))
		      {
		        //Scan for the virtual host
		      //  db = sqlDriver.getConnection(gk);
		        SiteList siteList = new SiteList();
		        siteList.setSiteCode(siteCode);
		        siteList.setVirtualHost(serverName);
		        siteList.buildList(db);
		        if (siteList.size() > 0) {
		          Site thisSite = (Site) siteList.get(0);
//		          ce = new ConnectionElement(
//		              thisSite.getDatabaseUrl(),
//		              thisSite.getDatabaseUsername(),
//		              thisSite.getDatabasePassword());
		        //  ce.setDbName(thisSite.getDatabaseName());
		       //   ce.setDriver(thisSite.getDatabaseDriver());
		          
		       } else {
		          loginBean.setMessage( "* Access denied: Host does not exist (" + serverName + ")");
		        }
		      } else {
		        //A single database is configured, so use it only regardless of ip/domain name
//		        ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
//		        ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
//		        ce.setDriver(gkDriver);
		      }
		    } catch (Exception e) {
		      loginBean.setMessage("* Gatekeeper: " + e.getMessage());
		    } finally {
		      if (db != null) {
		        sqlDriver.free(db, null);
		      }
		    }
//		    if (ce == null) {
//		     // lb.storeAccesso( -1, -1, 1,"Login Fallito", username, context, db );
//		    	logger.warning( timeToLog + " - [BDU Login No Password] - Login No Password Fallito: ip=" + context.getIpAddress() + " username=" + username );
//		    	return "LoginRetry";
//		    }
		    
		  
		    // Connect to the customer database and validate user
		    UserBean thisUser = null;
		    int userId = -1;
		    int aliasId = -1;
		    int roleId = -1;
		    String role = null;
		    String userId2 = null;
		    java.util.Date now = new java.util.Date();
		    boolean continueId = false;
		    Timestamp time = null;
		    ArrayList<String> coordinate = new ArrayList<String>();
		    try {
		      SystemStatus thisSystem = null;
		      db = sqlDriver.getConnection(null);
		      // Recuper l'ora da postgres
		      String sqlOra = "select "+DatabaseUtils.getCurrentTimestamp(db)+" as tempo";
		      
		      PreparedStatement pst2 = db.prepareStatement(sqlOra);
		      ResultSet rs2 = pst2.executeQuery();
		      if(rs2.next())
		      {
		    	  time = rs2.getTimestamp("tempo");
		      }
		      
		      
		      // If system is not upgraded, perform lightweight validation to ensure backwards compatibility
		      if (applicationPrefs.isUpgradeable()) {
		        continueId = true;
		      } else {
		        //A good place to initialize this SystemStatus, must be done before getting a user
		        Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
		        thisSystem = SecurityHook.retrieveSystemStatus(
		            context.getServletContext(), db, thisSite.getLanguage());
		        if (System.getProperty("DEBUG") != null) {
		        	logger.config(  timeToLog + " [BDU] - Login-> Retrieved SystemStatus from memory : " + ((thisSystem == null) ? "false" : "true"));
		        }
		        continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
		      }
		      //Query the user record
		      String pw = null;
		      java.sql.Date expDate = null;
		      int tmpUserId = -1;
		      int roleType = -1;
		      if (continueId) {
		        // NOTE: The following is the simplest valid SQL that works
		        // on all versions of Centric CRM.  It must not be
		        // modified with new fields because .war users need to
		        // be able to login first, before the upgrade has happened
		        PreparedStatement pst = db.prepareStatement(
		            "SELECT a." + DatabaseUtils.addQuotes(db, "password") + ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role") + ", a.expires, a.alias, a.user_id, r.role_type " +
		                "FROM " + DatabaseUtils.addQuotes(db, "access") + " a, " + DatabaseUtils.addQuotes(db, "role") + " r " +
		                "WHERE a.role_id = r.role_id " +
		                "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? " +
		                "AND a.enabled = ? ");
		        pst.setString(1, username.toLowerCase());
		        pst.setBoolean(2, true);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next()) {
		          pw = rs.getString("password");
		          roleId = rs.getInt("role_id");
		          role = rs.getString("role");
		          expDate = rs.getDate("expires");
		          aliasId = rs.getInt("alias");
		          tmpUserId = rs.getInt("user_id");
		          roleType = rs.getInt("role_type");
		        }
		        rs.close();
		        pst.close();
		        if (tmpUserId == -1) {
		          // NOTE: This could be modified so that LDAP records get inserted into CRM on the fly if they do not exist yet

		          // User record not found in database
		          loginBean.setMessage(
		              "* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
		          if (System.getProperty("DEBUG") != null) {
		        	  logger.config(timeToLog +" [BDU] - Login-> User record not found in database for: " + username.toLowerCase());
		          }
		        } else if (expDate != null && now.after(expDate)) {
		          // Login expired
		          loginBean.setMessage(
		              "* " + thisSystem.getLabel("login.msg.accountExpired"));
		        } else {
		          // User exists, now verify password
		          boolean ldapEnabled = "true".equals(applicationPrefs.get("LDAP.ENABLED"));
		          if (ldapEnabled && roleType == Constants.ROLETYPE_REGULAR) {
		            // See if the CRM username and password matches in LDAP
		            int ldapResult = LDAPUtils.authenticateUser(applicationPrefs, db, loginBean);
		            if (ldapResult == LDAPUtils.RESULT_VALID) {
		              userId = tmpUserId;
		            }
		          } else {
		            // Validate against Centric CRM for PortalRole users
		            /*if (pw == null || pw.trim().equals("") ||
		                (!pw.equals(password) && !context.getServletContext().getAttribute("GlobalPWInfo").equals(password))) {
		              loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
		            } else {*/
		              userId = tmpUserId;
		            //}
		          }
		        }
		      }
		      //Perform rest of user initialization if a valid user
		      if (userId > -1) {
		        thisUser = new UserBean();
		        thisUser.setSessionId(context.getSession().getId());
		        thisUser.setUserId(aliasId > 0 ? aliasId : userId);
		        thisUser.setActualUserId(userId);
		       // thisUser.setConnectionElement(ce);
		        thisUser.setConnectionPoll(sqlDriver);
		        thisUser.setClientType(context.getRequest());
		        if (thisSystem != null) {
		          //The user record must be in user cache to proceed
		          User userRecord = thisSystem.getUser(thisUser.getUserId());
		          if (userRecord != null) {
		            if (System.getProperty("DEBUG") != null) {
		            	logger.config(timeToLog +" [BDU] - Login-> Retrieved user from memory: " + userRecord.getUsername());
		            }
		            thisUser.setIdRange(userRecord.getIdRange());
		            userRecord.setBuildContact(true);
		            userRecord.setBuildContactDetails(true);
		            userRecord.buildResources(db);
		            userRecord.setAccess_position_lat(loginBean.getAccess_position_lat());
		            userRecord.setAccess_position_lon(loginBean.getAccess_position_lon());
		            userRecord.setAccess_position_err(loginBean.getAccess_position_err());
		            thisUser.setUserRecord(userRecord);
		            
		            //Log that the user attempted login (does not necessarily mean logged in
		            //anymore due to the single-session manager below
		            userRecord.setIp(context.getRequest().getRemoteAddr());
		            userRecord.updateLogin(db);
		            coordinate = userRecord.getCoordinateUltimoAccesso(db);
//		            userRecord.checkWebdavAccess(
//		                db, context.getRequest().getParameter("password"));
		          }
		          if (!thisSystem.hasPermissions()) {
		        	  logger.warning(timeToLog + " [BDU] - Login-> This system does not have any permissions loaded!");
		          }
		        } else {
		          if (System.getProperty("DEBUG") != null) {
		        	  logger.config(timeToLog +" [BDU] - Login-> Fatal: User not found in this System!");
		            
		          }
		        }
		        
		    	//lb.storeAccesso( -1, -1, 1, "Login Success ",username, context, db );
		        logger.info( timeToLog + " - [BDU] - Login Success: ip=" + context.getIpAddress() + " username=" + username + " user_id=" + thisUser.getUserId() );
		      } else {
		        if (System.getProperty("DEBUG") != null) {
		        	logger.config(timeToLog +" [BDU] - Login-> Fatal: User does not have an Id!");
		        }
		      }
		    } catch (Exception e) {
		      loginBean.setMessage("* Access: " + e.getMessage());
		      if (System.getProperty("DEBUG") != null) {
		        e.printStackTrace();
		      }
		      thisUser = null;
		    } finally {
		      if (db != null) {
		        sqlDriver.free(db, null);
		      }
		    }
		    //If user record is not found, ask them to login again
		    if (thisUser == null) {
		    	//lb.storeAccesso( -1, -1, 1, "Login Fallito ",username, context, db );
		    	logger.warning(timeToLog + " - [BDU] - Login Fallito: ip=" + context.getIpAddress() + " username=" + username );
		      return "LoginRetry";
		    }
		    //A valid user must have this information in their session, or the
		    //security manager will not let them access any secure pages
		    context.getSession().setAttribute("User", thisUser);
		    context.getSession().setAttribute("Time", time);
		    
	        /**
	         * AGGIUNGO IN SESSIONE ANCHE USER_ID PER VERIFICARE CORRETTEZZA UTENTE LOGGATO
	         */
	        context.getSession().setAttribute("UserID", thisUser.getUserId());
		//    context.getSession().setAttribute("ConnectionElement", ce);
		  //  GestoreConnessioni.setCe(ce);
		    GestoreConnessioni.setContext(context);
		    
		    if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente")!=null &&
	    			ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
	    	    if (userId>0){
	    		    ArrayList<UserOperation> op = new ArrayList<UserOperation>();
	    			UserOperation uo = new UserOperation();
	    			uo.setUser_id(thisUser.getUserId());
	    			uo.setUsername(thisUser.getUsername());
	    			uo.setIp(thisUser.getUserRecord().getIp());
	    			uo.setData(new Timestamp(new java.util.Date().getTime()));
	    			uo.setUrl(context.getRequest().getRequestURL().toString()+(context.getRequest().getQueryString()!=null ? "?"+context.getRequest().getQueryString() : ""));
	    			uo.setParameter("");
	    			uo.setUserBrowser(context.getRequest().getHeader("user-agent"));
	    			uo.setUser_id_session(thisUser.getUserId());
	    			op.add(uo);
	    			
	    			MiddleServlet.writeStorico(op, "", false);
	    	    }
	        }
		    
		    //Flusso 238
		    if(true)
		    {
		    String id_canile = context.getRequest().getParameter("id_canile");
	        String id_stabilimento_gisa = context.getRequest().getParameter("id_stabilimento_gisa");
	        
	        if ( id_canile!=null && !id_canile.equals("") && !id_canile.equals("-1") && !id_canile.equals("0"))
	        	context.getSession().setAttribute("id_canile", id_canile);
	        else
	        	context.getSession().setAttribute("id_canile", null);
	        if ( id_stabilimento_gisa!=null && !id_stabilimento_gisa.equals("") && !id_stabilimento_gisa.equals("-1") && !id_stabilimento_gisa.equals("0"))
	        	context.getSession().setAttribute("id_stabilimento_gisa", id_stabilimento_gisa);
	        else
	        	context.getSession().setAttribute("id_stabilimento_gisa", null);
		    }
		    if (applicationPrefs.isUpgradeable()) {
		      if (roleId == 1 || "Administrator".equals(role)) {
		        context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
		        return "PerformUpgradeOK";
		      } else {
		        return "UpgradeCheck";
		      }
		    } else {
		      //Check to see if user is already logged in.
		      //If not then add them to the valid users list
		      SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(sqlDriver.getUrl());
		      SessionManager sessionManager = thisSystem.getSessionManager();
		      if (sessionManager.isUserLoggedIn(userId)) {
		    	 
		    	       if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
	 			          context.getSession().setAttribute("caller", (String) context.getRequest().getParameter("caller"));
	        	       else
		 			      context.getSession().setAttribute("caller", null);
		    	       
		    		  	UserSession thisSession = sessionManager.getUserSession(userId);
		    		  	//context.getSession().setMaxInactiveInterval(300);
		    		  	context.getRequest().setAttribute("Session", thisSession);
		    		  	
		    	        context.getRequest().setAttribute("access_position_lat", loginBean.getAccess_position_lat()+"");
		    	        context.getRequest().setAttribute("access_position_lon", loginBean.getAccess_position_lon()+"");
		    	        context.getRequest().setAttribute("access_position_err", loginBean.getAccess_position_err()+"");
		    	        
		    	        
		    	        String dataAperturaCC = (String) context.getParameter("dataAperturaCC");
			        	if (dataAperturaCC != null && !("").equals(dataAperturaCC) )
			        		context.getRequest().setAttribute("dataAperturaCC", dataAperturaCC);
			        	
			        	if (dataAperturaCC != null && !("").equals(dataAperturaCC) )
			        		context.getRequest().setAttribute("dataAperturaCC", dataAperturaCC);
		    	        
		    		  	
			        	
			        	if(interactiveMode != null)
		    		      {
		    		  		return "interactivemodeAddCaneOK";
		    		      }
			        	if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
			        	{       
			        		return "LoginConfirmBypassed";
		    		  	}
		    		  	return "LoginVerifyOK";
//		    	  }

		      }
		      
		      if (System.getProperty("DEBUG") != null) {
		    	  logger.info(timeToLog +" [BDU] - Login-> Session Size: " + sessionManager.size());
		      }
		      //context.getSession().setMaxInactiveInterval(
		          //thisSystem.getSessionTimeout());
		      sessionManager.addUser(context, userId);
		      
		      
		      if (coordinate.size()>0)
		      {  
		    	  
		    	  UserSession thisSession = (UserSession)sessionManager.getSessions().get(userId);

		    	  
		    	  thisSession.setAccess_position_lat(coordinate.get(0));
		    	  thisSession.setAccess_position_lon(coordinate.get(1));
		    	  thisSession.setAccess_position_date(coordinate.get(3));
		    	  thisSession.setAccess_position_err("Ultime coordinate del"+coordinate.get(3))  ;
		      
		      sessionManager.getSessions().put(userId, thisSession);
		      }
		      
		    }
		    // TODO: Replace this so it does not need to be maintained
		    // NOTE: Make sure to update this similar code in the following method
		    String redirectTo = context.getRequest().getParameter("redirectTo");
		     
		   
		    
		    if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
		      if (redirectTo != null) {
//		    	  redirectTo+="&Message="+msg;
		    	  context.getRequest().setAttribute("Message", msg);

		    	  context.getRequest().setAttribute("redirectUrl", redirectTo);
		        //context.getRequest().removeAttribute("PageLayout");
		        return "RedirectURL";
		      }
		      
			    
			  if(interactiveMode != null)
		      {
		      	return "interactivemodeAddCaneOK";
		      }
		      
		        if ((String) context.getParameter("microchip") != null && !("").equals((String) context.getParameter("microchip"))){
		 		   if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
					   context.getSession().setAttribute("caller", (String) context.getRequest().getParameter("caller"));
		 		   else
		 			  context.getSession().setAttribute("caller", null);
		        	//RegistrazioniAnimale registrazioniAnimale = new RegistrazioniAnimale();
		        	//registrazioniAnimale.executeCommandAdd(context);
		 		    boolean accMultipla = context.getParameter("accMultipla")!=null && context.getParameter("accMultipla").equals("true");
		        	String idTipologiaRichiestaDaVam = (String) context.getParameter("idTipologiaEvento");
		        	String opIdDetentore = (String) context.getParameter("opIdDetentore");
		        	String dataRegistrazione = (String) context.getParameter("dataRegistrazione");
		        	String idSpecie = (String) context.getParameter("idSpecie");
		        	String razza = (String) context.getParameter("razza");
		        	String sesso = (String) context.getParameter("sesso");
		        	String dataNascita = (String) context.getParameter("dataNascita");
		        	String mantello = (String) context.getParameter("mantello");
		        	String taglia = (String) context.getParameter("taglia");
		        	String dataChippatura = (String) context.getParameter("dataChippatura");

		        	String dataAperturaCC = (String) context.getParameter("dataAperturaCC");
		        	int idTipoEvento = -1;
		        	if (idTipologiaRichiestaDaVam != null && !("").equals(idTipologiaRichiestaDaVam) )
		        		 idTipoEvento = new Integer(idTipologiaRichiestaDaVam);
		        	if (dataRegistrazione != null && !("").equals(dataRegistrazione) )
		        		context.getRequest().setAttribute("dataRegistrazione", dataRegistrazione);
		        	
		        	if (dataAperturaCC != null && !("").equals(dataAperturaCC) )
		        		context.getRequest().setAttribute("dataAperturaCC", dataAperturaCC);
		        	
		        	context.getSession().setAttribute("loginNoPassword", "true");
		        	context.getSession().setAttribute("idTipologiaEvento", idTipologiaRichiestaDaVam);
		        	if (idTipoEvento == 1 && accMultipla)
		        	{
		        		context.getRequest().setAttribute("opId", context.getRequest().getParameter("opId"));
		        		context.getRequest().setAttribute("opIdDetentore", context.getRequest().getParameter("opIdDetentore"));
		        		context.getRequest().setAttribute("tipologiaSoggetto", context.getRequest().getParameter("tipologiaSoggetto"));
		        		context.getRequest().setAttribute("dataRegistrazioneAccMultipla", context.getRequest().getParameter("dataRegistrazione"));
		        		context.getRequest().setAttribute("mcAccMultipla", context.getRequest().getParameter("microchip"));
		        		if (idSpecie != null && !("").equals(idSpecie) )
			        		context.getRequest().setAttribute("idSpecieAccMultipla", idSpecie);
		        		if (razza != null && !("").equals(razza) )
			        		context.getRequest().setAttribute("razzaAccMultipla", razza);
			        	if (sesso != null && !("").equals(sesso) )
			        		context.getRequest().setAttribute("sessoAccMultipla", sesso);
			        	if (dataNascita != null && !("").equals(dataNascita) )
			        		context.getRequest().setAttribute("dataNascitaAccMultipla", dataNascita);
			        	if (mantello != null && !("").equals(mantello) )
			        		context.getRequest().setAttribute("mantelloAccMultipla", mantello);
			        	if (taglia != null && !("").equals(dataChippatura) )
			        		context.getRequest().setAttribute("tagliaAccMultipla", taglia);
			        	if (dataChippatura != null && !("").equals(dataChippatura) )
			        		context.getRequest().setAttribute("dataChippaturaAccMultipla", dataChippatura);
			        	if (opIdDetentore != null && !("").equals(opIdDetentore) )
			        		context.getRequest().setAttribute("opIdDetentore", opIdDetentore);
		        		return "AggiungiAnimaleAccMultiplaOK";
		        	
		        	}
		        	else if (idTipoEvento == 1 && !accMultipla)
		        		return "AggiungiAnimaleOK";
		        	
		        	return "AggiungiRegistrazioneOK";
		        }else if ((String) context.getParameter("tipologiaEvento") != null && !("").equals((String) context.getParameter("tipologiaEvento"))){
		        	context.getSession().setAttribute("loginNoPassword", "true");
		        	
		        	if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller")))
		 			      context.getSession().setAttribute("caller", (String) context.getRequest().getParameter("caller"));
		        	else
			 			  context.getSession().setAttribute("caller", null);
		        	
		        	
		        	return "DettaglioRegistrazioneOK";
		        }
		        else{
		        	return "LoginOK";
		        }
		    } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
		      return "CustomerPortalLoginOK";
		    } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
		      return "ProductsPortalLoginOK";
		    }
		    if (redirectTo != null) {
		      //context.getRequest().removeAttribute("PageLayout");
		      return "RedirectURL";
		    }
		    

		    return "LoginOK";
		  }
	  
	  
	  public String executeCommandLoginCanina(ActionContext context){
		try{  
		    tokenLoginUtil tokenUtil = new tokenLoginUtil();
			String CANINA_PORTALE_URL 		= "http://";
			String CANINA_URL 		  		= InetAddress.getByName("hostAppCaninaOldPublic").getHostAddress();
			String CANINA_PORT 		  		= ApplicationProperties.getProperty("CANINA_PORT");
			String CANINA_APPLICATION_NAME 	= ApplicationProperties.getProperty("CANINA_APPLICATION_NAME");
			CANINA_PORTALE_URL = CANINA_PORTALE_URL + CANINA_URL + ":" + CANINA_PORT + "/" + CANINA_APPLICATION_NAME;
			
			
			String ret = CANINA_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + tokenUtil.generate( getUserName(context) );
			HttpServletResponse res = context.getResponse();
			res.sendRedirect( res.encodeRedirectURL( ret ) );
		}catch (Exception e) {
			// TODO: handle exception
		}
		  
		  return "-none-";
	  }
	  
	  
	  public String executeCommandLoginDigemon(ActionContext context){
		try{  
		    tokenLoginUtil tokenUtil = new tokenLoginUtil();
			String DIGEMON_PORTALE_URL 		= "http://";
			String DIGEMON_URL 		  		= ApplicationProperties.getProperty("DIGEMON_URL_PUBLIC");
			String DIGEMON_PORT 		  		= ApplicationProperties.getProperty("DIGEMON_PORT");
			String DIGEMON_APPLICATION_NAME 	= ApplicationProperties.getProperty("DIGEMON_APPLICATION_NAME");
			DIGEMON_PORTALE_URL = DIGEMON_PORTALE_URL + DIGEMON_URL + ":" + DIGEMON_PORT + "/" + DIGEMON_APPLICATION_NAME;
			
			
			String ret = DIGEMON_PORTALE_URL + "/Login.do?command=LoginNoPassword" + tokenUtil.generate( getUserName(context) );
			HttpServletResponse res = context.getResponse();
			res.sendRedirect( res.encodeRedirectURL( ret ) );
		    
		    
		    //context.getResponse().sendRedirect( "http://report.gisacampania.it/DiGeMon/Login.do?command=LoginNoPassword&encryptedToken="+tokenUtil.generate( getUserName(context) ));
		    
		    
		}catch (Exception e) {
			// TODO: handle exception
		}
		  
		  return "";
	  }
	   
	  public String executeCommandLoginPortaleImportatori(ActionContext context){
			try{  
			    tokenLoginUtil tokenUtil = new tokenLoginUtil();
				String IMPORTATORI_PORTALE_URL 		= "http://";
				//String IMPORTATORI_URL 		  		= InetAddress.getByName("hostAppImportatoriPublic").getHostAddress();
				String IMPORTATORI_URL 		  		= ((InetAddress)((HashMap<String, InetAddress>)context.getSession().getServletContext().getAttribute("hosts")).get("srvIMPORTATORIW")).getHostAddress();
				String IMPORTATORI_PORT 		  		= ApplicationProperties.getProperty("IMPORTATORI_PORT");
				String IMPORTATORI_APPLICATION_NAME 	= ApplicationProperties.getProperty("IMPORTATORI_APPLICATION_NAME");
				IMPORTATORI_PORTALE_URL = IMPORTATORI_PORTALE_URL + IMPORTATORI_URL + ":" + IMPORTATORI_PORT + "/" + IMPORTATORI_APPLICATION_NAME;
				
				
				 //String username = generate(thisUser.getUsername());
				 String originalToken = System.currentTimeMillis() + "@"+getUserName(context);
				 String encryptedToken = null ;
				 encryptedToken = NEWencrypt(originalToken,this.getClass().getResource("aes_key"));

				
				//String ret = IMPORTATORI_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + tokenUtil.generate( getUserName(context) );
				 String ret = IMPORTATORI_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso&encryptedToken=" + encryptedToken;
					HttpServletResponse res = context.getResponse();
				res.sendRedirect( res.encodeRedirectURL( ret ) );
			}catch (Exception e) {
				e.printStackTrace();
			}
			  
			  return "-none-";
		  }
	  
	  
	  
	  public String executeCommandLoginFelina(ActionContext context){
			try{  
			    tokenLoginUtil tokenUtil = new tokenLoginUtil();
				String FELINA_PORTALE_URL 		= "http://";
				String FELINA_URL 		  		= InetAddress.getByName("hostAppFelinaOldPublic").getHostAddress();
				String FELINA_PORT 		  		= ApplicationProperties.getProperty("FELINA_PORT");
				String FELINA_APPLICATION_NAME 	= ApplicationProperties.getProperty("FELINA_APPLICATION_NAME");
				FELINA_PORTALE_URL = FELINA_PORTALE_URL + FELINA_URL + ":" + FELINA_PORT + "/" + FELINA_APPLICATION_NAME;
				
				
				String ret = FELINA_PORTALE_URL + "/Login.do?command=LoginNoPassword&action=Accesso" + tokenUtil.generate( getUserName(context) );
				HttpServletResponse res = context.getResponse();
				res.sendRedirect( res.encodeRedirectURL( ret ) );
			}catch (Exception e) {
				// TODO: handle exception
			}
			  
			  return "";
		  }
	  
	  public String executeCommandCercaCaneDalPortale(ActionContext context) {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			executeCommandDefault((context));
			SystemStatus thisSystem = this.getSystemStatus(context);
			
		      String microchip = context.getRequest().getParameter("microchip");
		      
		      if(microchip == null){
		    	  context.getRequest().setAttribute("microchip", "Valore del microchip inserito non valido.");
		    	  return getReturn(context, "DettagliAnimalePortale");
		      }
		    
		     

		      Connection db = null;
//		    	String gkDriver = prefs.get("GATEKEEPER.DRIVER");
//				String gkHost = prefs.get("GATEKEEPER.URL");
//				String gkUser = prefs.get("GATEKEEPER.USER");
//				String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");
//
//				ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
//			    gk.setDriver(gkDriver);
				
			    ConnectionPool sqlDriver =(ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
		      try {
					 
				db = sqlDriver.getConnection(null);		

		    	LookupList lookupMantello = new LookupList(db, "lookup_mantello");
		    //	lookupMantello.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
				context.getRequest().setAttribute("lookupMantello", lookupMantello);

				LookupList lookupRazza = new LookupList(db, "lookup_razza");
			//	lookupRazza.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
				context.getRequest().setAttribute("lookupRazza", lookupRazza);
				
				LookupList siteList = new LookupList(db, "lookup_asl_rif");
				
				context.getRequest().setAttribute("AslList", siteList);

				if (microchip.length()<=15) {
					
					 Animale newAnimale = new Animale(db, microchip);
					context.getRequest().setAttribute("animale", newAnimale);
				}else {
					context.getRequest().setAttribute("microchip", "Valore del microchip inserito non valido.");
				}
		      } catch (Exception e) {
		        context.getRequest().setAttribute("Error", e);
		        e.printStackTrace();
		        return ("SystemError");
		      } finally {
		        sqlDriver.free(db, null);
		        freeConnection(context, db);
		      }
		    // return getReturn(context, "contaCani");
		     return "DettagliAnimalePortaleOK";
		}
	   
	  
		public String executeCommandNewContaCani(ActionContext context)
		{
			String timeToLog = sdfLog.format( new Date(System.currentTimeMillis()) );
			int numero = -1;
			logger.info(timeToLog +" [CANINA] - [ Login ][ executeCommandNewContaCani ]");
			long now = new java.util.Date().getTime();
			long time_diff = 0L;
			try{
				time_diff = 1000 * 60 * Integer.parseInt(ApplicationProperties.getProperty("intervallo_conta_cani_in_minuti"));
			}
			catch(Exception e){
				e.printStackTrace();
				//Di default viene impostato a 5 minuti
				time_diff = 1000 * 60 * 5;
			}
			
			logger.info(timeToLog +" [CANINA] - DIFF TIME (ms): " + (now - ultimoConteggioCani) );
			if( now < ultimoConteggioCani + time_diff && numero > 0 ){
				logger.info(timeToLog +" [CANINA] - Non eseguo interrogazioni al DB");
				return "NewContaCaniOK";
			}
				
			ultimoConteggioCani = now;
			
			ApplicationPrefs prefs = null;
			//ConnectionElement ce = null;
			ConnectionPool cp = null;
			Connection db = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			
			
			try {
				prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
//				String gkDriver = prefs.get("GATEKEEPER.DRIVER");
//				String gkHost = prefs.get("GATEKEEPER.URL");
//				String gkUser = prefs.get("GATEKEEPER.USER");
//				String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");
//				 
//				ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
		//		ce.setDriver(gkDriver);
			    cp = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
			     
			    db = cp.getConnection(null);		      
			     
				pst = db.prepareStatement("SELECT COUNT(*) AS recordcount FROM animale where trashed_date is null and data_cancellazione is null");
				rs = pst.executeQuery();
				
				if (rs.next()) {
					numero = rs.getInt("recordcount");
				}
				if(numero > 0){
					context.getServletContext().setAttribute("numeroCani", numero);
					logger.info("CONTA CANI: Trovati " + numero + "cani");
				}
				else{
					logger.severe(timeToLog +" [CANINA] - Errore numero cani.");
					throw new Exception("Il numero di cani non risulta maggiore di zero");
				}
				
		    } 
			catch (Exception e) {
				logger.severe(timeToLog +" [CANINA] - Errore durante executeCommandNewContaCani");
		    	e.printStackTrace();
		    } 
			finally {
				try{
					rs.close();
					pst.close();
					cp.free(db, context);
					freeConnection(context, db);
				}
				catch(Exception e){
					logger.severe("[CANINA] - EXCEPTION nella action executeCommandNewContaCani della classe Login");
					e.printStackTrace();
				}
		    	
		    }
			
			
				try {
					
					if(numero > 0){
						
						context.getResponse().getWriter().write(numero);
						//context.getResponse().getOutputStream().write( ("" + numero).getBytes() );
					}
					else{
						context.getResponse().getWriter().write("Si è verificato un problema temporaneo: Numero cani = " + numero);
						//context.getResponse().getOutputStream().write( "Si è verificato un problema temporaneo".getBytes() );
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			
			    
			return "NewContaCaniOK";
		}
		
		public String executeCommandBarcodePortale(ActionContext context) throws NamingException{


			logger.info("Generazione pdf per barcode in corso...");

			String m_c 			= 	context.getRequest().getParameter("microchip");
			String nomeFile 	= 	context.getRequest().getParameter("file_name");
			String fieldImgName =  	context.getRequest().getParameter("fieldImg");
	 		String tipo         =  	context.getRequest().getParameter("tipo");

			try {
				
				GeneraBarCode barc = new GeneraBarCode();
				if (m_c != null &&  !("").equals(m_c))
					return barc.connessione_DB(m_c, nomeFile, fieldImgName,tipo, context);
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "TuttoOK";
		}	

		public  String NEWdecrypt(String input, byte[] preSharedKey){
			byte[] output = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = getKeySpecByString(preSharedKey);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, skey);
				output = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(input.replaceAll(" ","+" ).getBytes()));
			}catch(Exception e){
				System.out.println(e.toString());
			}  
			return new String(output);
		}
		
		public static SecretKeySpec getKeySpecByString(byte[] preSharedKey) throws IOException, NoSuchAlgorithmException,
		FileAesKeyException {


			SecretKeySpec spec = null;

			spec = new SecretKeySpec(preSharedKey, "AES");
			return spec;
		}

		public static byte[] getKeySpecByte(String path) throws IOException, NoSuchAlgorithmException,
		FileAesKeyException {
			byte[] bytes = new byte[16];
			File f = new File(path.replaceAll("%20", " "));

			SecretKeySpec spec = null;
			if (f.exists()) {
				new FileInputStream(f).read(bytes);

			} else {
				throw new FileAesKeyException("File aes_key not found");

			}
			
			return bytes;
		}


}
