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
package org.aspcfs.modules.login.actions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.MiddleServlet;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SessionManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.UserSession;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.Suap;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.LoginBeanSuap;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.login.utils.CheckLock;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.FileAesKeyException;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.LDAPUtils;
import org.aspcfs.utils.PrefissoWrapper;
import org.aspcfs.utils.UserUtils;
import org.directwebremoting.WebContext;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;
import com.google.gson.JsonObject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import crypto.nuova.gestione.ClientSCAAesServlet;
import ext.aspcfs.modules.apiari.base.DelegaList;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apicolture.actions.CfUtil;

/**
 * The Login module.F
 *
 * @author mrajkowski
 * @version $Id: Login.java 22322 2007-08-01 13:08:17Z matt $
 * @created July 9, 2001
 */
public final class Login extends CFSModule {

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Login.class);

	public final static String fs = System.getProperty("file.separator");
	public Image mappa = null;// inserito da carmela
	PdfPTable tabellaAnalisi = new PdfPTable(100);
	SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static final int CONTEXT_GISA = 1;
	public static final int CONTEXT_SUAP = 2;

	private String getContext(ActionContext context) {
		String contesto = (String) context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		if (contesto != null && contesto.equals("_ext"))
			return "Ext";
		return "Gisa";
	}

	// Metodo per logout da DWR - VERONICA
	public String executeCommandLogout(WebContext context) {
		HttpSession oldSession = context.getSession();
		if (oldSession != null) {
			oldSession.removeAttribute("User");
			oldSession.invalidate();
		}
		return "LoginRetry";
	}

	public String executeCommandDefault(ActionContext context) {
		// Will need to use the following objects in some way...
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		Connection db = null;
		try {

			HttpSession oldSession = context.getRequest().getSession(false);
			if (oldSession != null) {
				oldSession.removeAttribute("User");
			}

			Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
			// Store a ConnectionElement in session for the LabelHandler to find
			// the corresponding language

		} catch (Exception e) {

		}
		// Determine entry page
		String scheme = context.getRequest().getScheme();
		// If SSL is configured, but this user isn't using SSL, then go to the
		// welcome page
		if ("true".equals((String) context.getServletContext().getAttribute("ForceSSL")) && scheme.equals("http")) {
			context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.WELCOME"));
		} else {
			context.getRequest().setAttribute("LAYOUT.JSP", prefs.get("LAYOUT.JSP.LOGIN"));
		}

		return "IndexPageOK";
	}

	public static byte[] lenientHexToBytes(String hex) {
		byte[] result = null;
		if (hex != null) {
			result = new byte[hex.length() / 2];
			for (int i = 0; i < result.length; i++) {
				result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
			}
		}

		return result;
	}

	public static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,
			FileAesKeyException {
		byte[] bytes = new byte[16];
		File f = new File(path.replaceAll("%20", " "));

		SecretKeySpec spec = null;
		if (f.exists()) {
			new FileInputStream(f).read(bytes);

		} else {
			throw new FileAesKeyException("File aes_key not found");

		}
		spec = new SecretKeySpec(bytes, "AES");
		return spec;
	}

	public static byte[] getKeySpecByte(String path) throws IOException, NoSuchAlgorithmException, FileAesKeyException {
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

	public static SecretKeySpec getKeySpecByString(byte[] preSharedKey) throws IOException, NoSuchAlgorithmException,
			FileAesKeyException {

		SecretKeySpec spec = null;

		spec = new SecretKeySpec(preSharedKey, "AES");
		return spec;
	}

	public static String decrypt(String text, URL url) throws Exception {
		if (url == null)
			throw new FileAesKeyException("File aes_key not found");

		SecretKeySpec spec = getKeySpec(url.getPath());
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		return (new String(cipher.doFinal(dec.decodeBuffer(text))));
	}

	public static String encrypt(String text, URL url) throws IOException, NoSuchAlgorithmException,
			FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {

		if (url == null) {
			throw new FileAesKeyException("File aes_key not found");
		}
		SecretKeySpec spec = getKeySpec(url.getPath());
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();

		return enc.encode(cipher.doFinal(text.getBytes()));
	}

	public static String NEWencrypt(String input, URL url) {
		byte[] crypted = null;
		try {
			// SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			SecretKeySpec skey = getKeySpec(url.getPath());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));
	}

	public String NEWdecrypt(String input, byte[] preSharedKey) {
		byte[] output = null;
		try { 
			// SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			SecretKeySpec skey = getKeySpecByString(preSharedKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(input.replaceAll(" ", "+")
					.getBytes()));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}

	public String executeCommandLoginGisaExt(ActionContext context) throws IOException, NoSuchAlgorithmException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException,
			FileAesKeyException, Exception {

		if (!hasPermission(context, "system-access-view")) {
			return "LoginNoAccessKO";
		}
		
		String originalToken = "";/*TODO _> CHI INVOCA QUESTO FLUSSO ? */
		if (context.getParameter("encryptedToken") != null) {

			String decrypteToken = "";
			String encrypteToken = context.getParameter("encryptedToken");
			String username = "";
			try {
				String[] params = null;
				/*TODO _> CHI INVOCA QUESTO FLUSSO ? */
				decrypteToken = NEWdecrypt(new String((encrypteToken)),
						getKeySpecByte(this.getClass().getResource("aes_key").getPath()));
				params = decrypteToken.split("@");
				username = params[1];
				originalToken = System.currentTimeMillis() + "@" + username + "@gisa_ext";
				String encryptedToken = null;
				encryptedToken = NEWencrypt(originalToken, this.getClass().getResource("aes_key"));
				/*
				ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				cclient.decrypt(encrypteToken);
				String decryptToken = */ 
				context.getResponse().sendRedirect(
						"http://" + context.getRequest().getServerName()
								+ "/gisa_ext/Login.do?command=LoginNoPassword&encryptedToken=" + (encryptedToken));

			} catch (Exception e) {
				context.getRequest().setAttribute("dettagli_problema",
						"Si e' verificato un problema nella decriptazione del token ");
				e.printStackTrace();
			}

		} else {
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			/** COSTRUZIONE DEL TOKEN **/
			LoginBean loginBean = (LoginBean) context.getRequest().getAttribute("LoginBean");

			originalToken = System.currentTimeMillis() + "@" + loginBean.getUsername() + "@gisa_ext@"
					+ loginBean.getPassword();
			/* vecchia gestione
			String encryptedToken = null;
			encryptedToken = NEWencrypt(originalToken, this.getClass().getResource("aes_key"));
			*/
			ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			String encryptedToken = cclient.crypt(originalToken);
			
			context.getResponse().sendRedirect(
					"http://" + context.getRequest().getServerName()+":"+context.getRequest().getServerPort()
							+ "/gisa_ext/Login.do?command=LoginNoPassword&encryptedToken=" + URLEncoder.encode(encryptedToken,"UTF-8"));
			// context.getResponse().sendRedirect(
			// "http://172.16.3.194:8080/CanAgr_Priv/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));

		}
		return "-none-";
	}

	public String executeCommandLoginNoPassword(ActionContext context) throws SQLException, UnknownHostException,
			ParseException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, UnsupportedEncodingException {
		String prefisso = PrefissoWrapper.valutaPrefissoWrapper(context);

		context.getRequest().setCharacterEncoding("UTF-8");
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
				"applicationPrefs");
		// LogBean lb = new LogBean();
		// Process the login request
		
		String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
		String gkHost = getPref(context, "GATEKEEPER.URL");
		String gkUser = getPref(context, "GATEKEEPER.USER");
		String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
		String siteCode = getPref(context, "GATEKEEPER.APPCODE");
		ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
		gk.setDriver(gkDriver);
		
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
		if (sqlDriver == null) {
//			loginBean.setMessage("Connection pool missing!");
			return "LoginRetry";
		}
		
		Connection db = null;
		
		
		String[] params = null;
		String decrypteToken = "";
		String encrypteToken = context.getParameter("encryptedToken");

		long loginTime = 0;
		try {
			db = sqlDriver.getConnection(gk, context);
			/*modifica 23/03/2017 Test modifica nuova versione */
			//decrypteToken = NEWdecrypt(new String((encrypteToken)),
			//	getKeySpecByte(this.getClass().getResource("aes_key").getPath()));
			/********************************************/
			 
			/*String path = this.getClass().getResource("aes_key2").getPath().replaceAll("%20", " ");
			FileInputStream fis = new FileInputStream(new File(path));
			byte[] buff = new byte[1024];
			int r = fis.read(buff);
			String key = new String(buff,0,r);*/
			try
			{
				//decrypteToken = CryptEncryptServlet.decrypt(encrypteToken, key);
				ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				decrypteToken = cclient.decrypt(encrypteToken);//CryptEncryptServlet.encrypt(originalToken, key);
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			//fis.close();
			/********************************************/
			
			int index = decrypteToken.indexOf("@");
			System.out.println("Controllo login @: index " + index);
			params = new String[2];
			params[0] = decrypteToken.substring(0,index);
			System.out.println("Controllo login @: params[0] " + params[0]);
			
			//Calcolo numero di @
			int num = 0;
			String toCheck = decrypteToken.substring(index+1,decrypteToken.length());
			System.out.println("Controllo login @: toCheck " + toCheck);
			while(toCheck.indexOf("@")>=0)
			{
				toCheck = toCheck.substring(toCheck.indexOf("@")+1,toCheck.length());
				num=num+1;
			}
			System.out.println("Controllo login @: num " + num);
			// Fine //Calcolo numero di @
			
			if(num==0)
			{
				params[1] = decrypteToken.substring(index+1,decrypteToken.length());
				System.out.println("Controllo login @: params[1]  " + params[1] );
			}
			else if(num==1)
			{
				System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length())  " + decrypteToken.substring(index+1,decrypteToken.length()) );
				if(decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1].contains("gisa"))
				{
					params = new String[3];
					System.out.println("Controllo login @: decrypteToken.substring(0,index) " + decrypteToken.substring(0,index) );
					params[0] = decrypteToken.substring(0,index);
					System.out.println("Controllo login @: decrypteToken.length()).split(\"@\")[1]  " + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1]);
					params[2] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1];
					System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[0] " + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] );
					params[1] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0];
				}
				else
				{
					System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()) " + decrypteToken.substring(index+1,decrypteToken.length()) );
					params[1] = decrypteToken.substring(index+1,decrypteToken.length());
				}
					
			}
			else if(num==2)
			{
				params = new String[3];
				System.out.println("Controllo login @: decrypteToken.substring(0,index) " + decrypteToken.substring(0,index));
				params[0] = decrypteToken.substring(0,index);
				System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[2]" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[2] );
				params[2] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[2];
				System.out.println("Controllo login @:  decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[0] + \"@\" + decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[1] " +  decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] + "@" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1]);
				params[1] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] + "@" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1];
					
			}
			//params = decrypteToken.split("@");
			loginTime = Long.parseLong(params[0]);

		} catch (Exception e) {
			context.getRequest().setAttribute("dettagli_problema",
					"Si e' verificato un problema nella decriptazione del token ");
			e.printStackTrace();
			return prefisso + "LoginRetry";
		}
		finally
		{
			sqlDriver.free(db,context);
		}

		// long loginTime = Long.parseLong(params[0]);
		long currTime = System.currentTimeMillis();

		if (currTime - loginTime > 15 * 1000 * 60) {
			context.getRequest().setAttribute("dettagli_problema", "Token non piu valido");
			return prefisso + "LoginRetry";
		}

		String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));

		LoginBean loginBean = (LoginBean) context.getRequest().getAttribute("LoginBean");

		JSONObject jsonObj = ApplicationProperties.checkBrowser(context.getRequest().getHeader("User-Agent"));
		String msg = "";
		if (jsonObj != null) {
			if (jsonObj.getString("esito").equals("1")) {
				msg = jsonObj.getString("msg");
				loginBean.setMessage(msg);
				context.getRequest().setAttribute("LoginBean", loginBean);
				return prefisso + "LoginRetry";
			}

			if (jsonObj.getString("esito").equals("2")) {
				msg = jsonObj.getString("msg");
			}

		}

		loginBean.checkURL(context);
		String username = params[1];
		if (params.length > 2) {
			String ambiente = params[2];
			System.out.println("ambiente" + ambiente);
			context.getSession().setAttribute("ambiente", ambiente);
			if ("gisa_ext".equalsIgnoreCase(ambiente)) {
				context.getServletContext().setAttribute("SUFFISSO_TAB_ACCESSI", "_ext");
			}
		}
		String password = null;
		if (params.length > 3) {
			password = params[3];

		}


		

		// String password = loginBean.getPassword();
		logger.info("[CANINA Login No Password] - username = " + username + " data = " + timeToLog);
		String serverName = context.getRequest().getServerName();
		// Throw out empty passwords
		// if (password == null || "".equals(password.trim())) {
		// return "LoginRetry";
		// }
		// Prepare the gatekeeper

		// Prepare the database connection

		ConnectionElement ce = null;
		// Connect to the gatekeeper, validate this host and get new connection
		// info
		try {
			if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
				// Scan for the virtual host
				db = sqlDriver.getConnection(gk, context);
				SiteList siteList = new SiteList();
				siteList.setSiteCode(siteCode);
				siteList.setVirtualHost(serverName);
				siteList.buildList(db);
				if (siteList.size() > 0) {
					Site thisSite = (Site) siteList.get(0);
					ce = new ConnectionElement(thisSite.getDatabaseUrl(), thisSite.getDatabaseUsername(),
							thisSite.getDatabasePassword());
					ce.setDbName(thisSite.getDatabaseName());
					ce.setDriver(thisSite.getDatabaseDriver());
				} else {
					loginBean.setMessage("* Access denied: Host does not exist (" + serverName + ")");
				}
			} else {
				// A single database is configured, so use it only regardless of
				// ip/domain name
				ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
				ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
				ce.setDriver(gkDriver);
			}
		} catch (Exception e) {
			loginBean.setMessage("* Gatekeeper: " + e.getMessage());
		} finally {
			if (db != null) {
				sqlDriver.free(db, context);
			}
		}
		if (ce == null) {
			// lb.storeAccesso( -1, -1, 1,"Login Fallito", username, context, db
			// );
			logger.info(timeToLog + " - [CANINA Login No Password] - Login No Password Fallito: ip="
					+ context.getIpAddress() + " username=" + username);
			return "LoginRetry";
		}

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
		try {
			SystemStatus thisSystem = null;
			db = sqlDriver.getConnection(ce, context);
			// Recuper l'ora da postgres
			String sqlOra = "select " + DatabaseUtils.getCurrentTimestamp(db) + " as tempo";

			PreparedStatement pst2 = db.prepareStatement(sqlOra);
			ResultSet rs2 = pst2.executeQuery();
			if (rs2.next()) {
				time = rs2.getTimestamp("tempo");
			}

			// If system is not upgraded, perform lightweight validation to
			// ensure backwards compatibility
			if (applicationPrefs.isUpgradeable()) {
				continueId = true;
			} else {
				// A good place to initialize this SystemStatus, must be done
				// before getting a user
				Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
				thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce,
						thisSite.getLanguage());

				continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
			}
			// Query the user record
			String pw = null;
			java.sql.Date expDate = null;
			int tmpUserId = -1;
			int roleType = -1;
			if (continueId) {
				// NOTE: The following is the simplest valid SQL that works
				// on all versions of Centric CRM. It must not be
				// modified with new fields because .war users need to
				// be able to login first, before the upgrade has happened
				PreparedStatement pst = db.prepareStatement("SELECT a." + DatabaseUtils.addQuotes(db, "password")
						+ ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role")
						+ ", a.expires, a.alias, a.user_id, r.role_type " + "FROM "
						+ DatabaseUtils.addQuotes(db, "access") + super.getSuffiso(context) + " a, "
						+ DatabaseUtils.addQuotes(db, "role") + super.getSuffiso(context) + " r "
						+ "WHERE a.role_id = r.role_id " + "AND " + DatabaseUtils.toLowerCase(db) + "(a.username) = ? "
						+ "AND a.enabled = ? AND a.in_access is not false AND a.trashed_date is null");
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
					// NOTE: This could be modified so that LDAP records get
					// inserted into CRM on the fly if they do not exist yet

					// User record not found in database
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));

				} else if (expDate != null && now.after(expDate)) {
					// Login expired
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.accountExpired"));
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

						if (password != null
								&& (pw == null || pw.trim().equals("") || (!pw.equals(password) && !context
										.getServletContext().getAttribute("GlobalPWInfo").equals(password)))

						) {
							loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
						} else {

							userId = tmpUserId;
						}
					}
				}
			}
			// Perform rest of user initialization if a valid user
			if (userId > -1) {
				thisUser = new UserBean();
				thisUser.setSessionId(context.getSession().getId());
				thisUser.setUserId(aliasId > 0 ? aliasId : userId);
				thisUser.setActualUserId(userId);
				thisUser.setConnectionElement(ce);
				thisUser.setClientType(context.getRequest());
				if (thisSystem != null) {
					// The user record must be in user cache to proceed
					User userRecord = thisSystem.getUser(thisUser.getUserId());

					Suap ss = userRecord.getSuap();
					ss.setContesto(getContext(context));
					userRecord.setSuap(ss);

					if (userRecord != null) {
						// userRecord.setSuap(null);

						thisUser.setIdRange(userRecord.getIdRange());
						thisUser.setUserRecord(userRecord);

						// Log that the user attempted login (does not
						// necessarily mean logged in
						// anymore due to the single-session manager below
						userRecord.setIp(context.getRequest().getRemoteAddr());
						userRecord.updateLogin(db, super.getSuffiso(context), context);

					}
					if (!thisSystem.hasPermissions()) {
						logger.info(timeToLog + " [GISA] - Login-> This system does not have any permissions loaded!");
					}
				} else {
					if (System.getProperty("DEBUG") != null) {
						logger.info(timeToLog + " [GISA] - Login-> Fatal: User not found in this System!");

					}
				}

				// lb.storeAccesso( -1, -1, 1, "Login Success ",username,
				// context, db );
				logger.info(timeToLog + " - [GISA] - Login Success: ip=" + context.getIpAddress() + " username="
						+ username + " user_id=" + thisUser.getUserId());

				thisUser.setSoggetto(new SoggettoFisico(thisUser.getContact().getCodiceFiscale().trim(), db));
				context.getRequest().getSession().setAttribute("User", thisUser);

			} else {
				if (System.getProperty("DEBUG") != null) {
					logger.info(timeToLog + " [GISA] - Login-> Fatal: User does not have an Id!");
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
				sqlDriver.free(db, context);
			}
		}
		// If user record is not found, ask them to login again
		if (thisUser == null) {
			// lb.storeAccesso( -1, -1, 1, "Login Fallito ",username, context,
			// db );
			logger.info(timeToLog + " - [GISA] - Login Fallito: ip=" + context.getIpAddress() + " username=" + username);
			return "LoginRetry";
		}
		// A valid user must have this information in their session, or the
		// security manager will not let them access any secure pages

		context.getSession().setAttribute("User", thisUser);
		context.getSession().setAttribute("Time", time);

		context.getSession().setAttribute("ConnectionElement", ce);

		if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null
				&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si")) {
			if (thisUser.getUserId() > 0) {
				ArrayList<UserOperation> op = new ArrayList<UserOperation>();
				UserOperation uo = new UserOperation();
				uo.setUser_id(thisUser.getUserId());
				uo.setUsername(thisUser.getUsername());
				uo.setIp(thisUser.getUserRecord().getIp());
				uo.setData(new Timestamp(new Date().getTime()));
				uo.setUrl(context.getRequest().getRequestURL().toString()
						+ (context.getRequest().getQueryString() != null ? "?" + context.getRequest().getQueryString()
								: ""));
				uo.setParameter("");
				uo.setUserBrowser(context.getRequest().getHeader("user-agent"));
				op.add(uo);

				MiddleServlet.writeStorico(op, "", false, super.getSuffiso(context));
			}
		}

		if (applicationPrefs.isUpgradeable()) {
			if (roleId == 1 || "Administrator".equals(role)) {
				context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
				return "PerformUpgradeOK";
			} else {
				return "UpgradeCheck";
			}
		} else {
			// Check to see if user is already logged in.
			// If not then add them to the valid users list
			SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
					"SystemStatus")).get(ce.getUrl());
			SessionManager sessionManager = thisSystem.getSessionManager();
			if (sessionManager.isUserLoggedIn(userId)) {

				UserSession thisSession = sessionManager.getUserSession(userId);
				context.getSession().setMaxInactiveInterval(300);
				context.getRequest().setAttribute("Session", thisSession);
				
				 if ((String) context.getRequest().getParameter("destinazione") != null && !("").equals((String) context.getRequest().getParameter("destinazione")))
				 {
					 context.getSession().setAttribute("destinazione", (String) context.getRequest().getParameter("destinazione"));
					 return "LoginConfirmBypassed";
				 }
       	         else
       	    	   context.getSession().setAttribute("destinazione", null);
				 
				return "LoginVerifyOK";

			}

			if (System.getProperty("DEBUG") != null) {
				logger.info(timeToLog + " [GISA] - Login-> Session Size: " + sessionManager.size());
			}
			// context.getSession().setMaxInactiveInterval(
			// thisSystem.getSessionTimeout());
			sessionManager.addUser(context, userId, thisUser.getUserRecord().getSuap());
		}
		// TODO: Replace this so it does not need to be maintained
		// NOTE: Make sure to update this similar code in the following method
		String redirectTo = "MyCFS.do?command=Home&Message=" + msg;
		
		
		if (!hasPermission(context, "system-access-view")) {
			return "LoginNoAccessKO";
		}
		
		if (hasPermission(context, "myhomepage-dashboard-view")) {
			context.getRequest().setAttribute("to_url", "MyCFS.do?command=Home&Message=" + msg);
		} else {
			if (hasPermission(context, "apicoltura-view")) {
				context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home&Message=" + msg);
			}
		}
		
		if(thisUser.getRoleId()==Role.RUOLO_TRASPORTATORI_DISTRIBUTORI)
		{
			if (thisUser.getContact().getVisibilitaDelega() != null && !"".equals(thisUser.getContact().getVisibilitaDelega())) 
			{
				context.getRequest().setAttribute("to_url", "OpuStab.do?command=MyHomePage");

			} 
		}
		
		if (context.getParameter("destinazione")!=null){ 
			redirectTo = context.getParameter("destinazione");
			System.out.println("[LOGIN NO PASSWORD URL DESTINAZIONE]: "+redirectTo);
			context.getRequest().setAttribute("to_url", redirectTo);

		}

		if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
			if (redirectTo != null) {
				// context.getRequest().removeAttribute("PageLayout");
				return "RedirectURL";
			}

			return "LoginOK";
		} else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
			return "CustomerPortalLoginOK";
		} else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
			return "ProductsPortalLoginOK";
		}
		if (redirectTo != null) {
			// context.getRequest().removeAttribute("PageLayout");
			return "RedirectURL";
		}
		return "LoginOK";
	}

	public String executeCommandLoginCNS(ActionContext context) throws SQLException, UnknownHostException,
			ParseException {
		String prefisso = PrefissoWrapper.valutaPrefissoWrapper(context);

		String ambiente = (String) context.getRequest().getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
				"applicationPrefs");
		// LogBean lb = new LogBean();
		// Process the login request
		LoginBean loginBean = new LoginBean();
		String codiceFiscale = "";
		/**
		 * QUI IL RECUPERO DEL CODICE FISCALE DALLA CNS
		 */

		Object o = context.getRequest().getAttribute("javax.servlet.request.X509Certificate");
		if (o != null) {
			X509Certificate certs[] = (X509Certificate[]) o;
			X509Certificate cert = certs[0];

			System.out.println("ver: " + cert.getVersion());
			System.out.println("\nSerial: " + cert.getSerialNumber().toString(16));
			System.out.println("\n Subj: " + cert.getSubjectDN());
			System.out.println("\n issuer: " + cert.getIssuerDN());
			System.out.println("\n ini: " + cert.getNotBefore());
			System.out.println("\n end: " + cert.getNotAfter());
			System.out.println("\n Sig: " + cert.getSigAlgName());
			codiceFiscale = CfUtil.extractCodiceFiscale(cert.getSubjectDN().toString());
			System.out.println("CNS  codiceFiscale Letto" + codiceFiscale);
		} else {

			loginBean.setMessage("Accesso al sistema negato Certificato non Riconosciuto");
			return "LoginRetry";
		}

		/**
		 * FINE LETTURA DALLA CNS
		 */

		String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));

		String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
		String gkHost = getPref(context, "GATEKEEPER.URL");
		String gkUser = getPref(context, "GATEKEEPER.USER");
		String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
		String siteCode = getPref(context, "GATEKEEPER.APPCODE");
		ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
		gk.setDriver(gkDriver);

		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
		if (sqlDriver == null) {
			loginBean.setMessage("Connection pool missing!");
			return "LoginRetry";
		}
		Connection db = null;

		// String password = loginBean.getPassword();
		logger.info("[GISA Login CNS ] = ");
		String serverName = context.getRequest().getServerName();
		// Throw out empty passwords
		// if (password == null || "".equals(password.trim())) {
		// return "LoginRetry";
		// }
		// Prepare the gatekeeper

		// Prepare the database connection

		ConnectionElement ce = null;
		// Connect to the gatekeeper, validate this host and get new connection
		// info
		try {
			if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
				// Scan for the virtual host
				db = sqlDriver.getConnection(gk, context);
				SiteList siteList = new SiteList();
				siteList.setSiteCode(siteCode);
				siteList.setVirtualHost(serverName);
				siteList.buildList(db);
				if (siteList.size() > 0) {
					Site thisSite = (Site) siteList.get(0);
					ce = new ConnectionElement(thisSite.getDatabaseUrl(), thisSite.getDatabaseUsername(),
							thisSite.getDatabasePassword());
					ce.setDbName(thisSite.getDatabaseName());
					ce.setDriver(thisSite.getDatabaseDriver());
				} else {
					loginBean.setMessage("* Access denied: Host does not exist (" + serverName + ")");
				}
			} else {
				// A single database is configured, so use it only regardless of
				// ip/domain name
				ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
				ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
				ce.setDriver(gkDriver);
			}
		} catch (Exception e) {
			loginBean.setMessage("* Gatekeeper: " + e.getMessage());
		} finally {
			if (db != null) {
				sqlDriver.free(db, context);
			}
		}
		if (ce == null) {
			// lb.storeAccesso( -1, -1, 1,"Login Fallito", username, context, db
			// );

			return "LoginRetry";
		}

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
		try {
			SystemStatus thisSystem = null;
			db = sqlDriver.getConnection(ce, context);
			// Recuper l'ora da postgres
			String sqlOra = "select " + DatabaseUtils.getCurrentTimestamp(db) + " as tempo";

			PreparedStatement pst2 = db.prepareStatement(sqlOra);
			ResultSet rs2 = pst2.executeQuery();
			if (rs2.next()) {
				time = rs2.getTimestamp("tempo");
			}

			// If system is not upgraded, perform lightweight validation to
			// ensure backwards compatibility
			if (applicationPrefs.isUpgradeable()) {
				continueId = true;
			} else {
				// A good place to initialize this SystemStatus, must be done
				// before getting a user
				Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
				thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce,
						thisSite.getLanguage());

				continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
			}
			// Query the user record
			String pw = null;
			java.sql.Date expDate = null;
			int tmpUserId = -1;
			int roleType = -1;
			if (continueId) {
				// NOTE: The following is the simplest valid SQL that works
				// on all versions of Centric CRM. It must not be
				// modified with new fields because .war users need to
				// be able to login first, before the upgrade has happened
				PreparedStatement pst = db.prepareStatement("SELECT a." + DatabaseUtils.addQuotes(db, "password")
						+ ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role")
						+ ", a.expires, a.alias, a.user_id, r.role_type " + "FROM "
						+ DatabaseUtils.addQuotes(db, "access") + super.getSuffiso(context) + " a, "
						+ DatabaseUtils.addQuotes(db, "role") + super.getSuffiso(context) + " r, "
						+ DatabaseUtils.addQuotes(db, "contact") + super.getSuffiso(context) + " c "
						+ "WHERE a.role_id = r.role_id " + "AND a.user_id=c.user_id AND "
						+ DatabaseUtils.toLowerCase(db) + "(c.codice_fiscale) ilike ? "
						+ "AND a.enabled = ? AND a.in_access is not false AND a.trashed_date is null");
				pst.setString(1, codiceFiscale.toLowerCase());
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
					System.out.println("########CNSSSSS UTENTE TROVATO NEL DATABASE : " + roleId);
				}
				rs.close();
				pst.close();
				if (tmpUserId == -1) {
					System.out.println("########CNSSSSS UTENTE NOOOOOOON TROVATO NEL DATABASE : " + roleId);
					// NOTE: This could be modified so that LDAP records get
					// inserted into CRM on the fly if they do not exist yet

					// User record not found in database
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));

				} else if (expDate != null && now.after(expDate)) {
					// Login expired
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.accountExpired"));
					System.out.println("########CNSSSSS UTENTE DATA SCADUTA NEL DATABASE : " + roleId);

				} else {
					// User exists, now verify password

					// Validate against Centric CRM for PortalRole users

					System.out.println("########CNSSSSS UTENTE SETTATTO LO USER IDD  : " + roleId);

					userId = tmpUserId;

				}
			}
			// Perform rest of user initialization if a valid user
			if (userId > -1) {
				System.out.println("########CNSSSSS UTENTE SETTATTO LO USER IDD>>>>>0  : " + roleId);

				thisUser = new UserBean();
				thisUser.setSessionId(context.getSession().getId());
				thisUser.setUserId(aliasId > 0 ? aliasId : userId);
				thisUser.setActualUserId(userId);
				thisUser.setConnectionElement(ce);
				thisUser.setClientType(context.getRequest());
				if (thisSystem != null) {
					// The user record must be in user cache to proceed
					User userRecord = thisSystem.getUser(thisUser.getUserId());

					if (userRecord != null) {
						// userRecord.setSuap(null);

						thisUser.setIdRange(userRecord.getIdRange());
						thisUser.setUserRecord(userRecord);

						// Log that the user attempted login (does not
						// necessarily mean logged in
						// anymore due to the single-session manager below
						userRecord.setIp(context.getRequest().getRemoteAddr());
						userRecord.updateLogin(db, super.getSuffiso(context), context);
						System.out.println("########CNSSSSS UTENTE PRELEVATO DAL SYSTEM STATUSSS " + roleId);

						SoggettoFisico sf = new SoggettoFisico(userRecord.getContact().getCodiceFiscale().trim(), db);
						thisUser.setSoggetto(sf);

					}
					if (!thisSystem.hasPermissions()) {
						logger.info(timeToLog + " [GISA] - Login-> This system does not have any permissions loaded!");
					}
				} else {
					if (System.getProperty("DEBUG") != null) {
						logger.info(timeToLog + " [GISA] - Login-> Fatal: User not found in this System!");

					}
				}

				// lb.storeAccesso( -1, -1, 1, "Login Success ",username,
				// context, db );
				logger.info(timeToLog + " - [GISA] - Login Success: ip=" + context.getIpAddress() + " codiceFiscale="
						+ codiceFiscale + " user_id=" + thisUser.getUserId());

			} else {
				if (System.getProperty("DEBUG") != null) {
					logger.info(timeToLog + " [GISA] - Login-> Fatal: User does not have an Id!");
				}
			}
		} catch (Exception e) {
			loginBean.setMessage("* Access: " + e.getMessage());
			e.printStackTrace();
			thisUser = null;
		} finally {
			if (db != null) {
				sqlDriver.free(db, context);
			}
		}
		// If user record is not found, ask them to login again
		if (thisUser == null) {
			// lb.storeAccesso( -1, -1, 1, "Login Fallito ",username, context,
			// db );
			logger.info(timeToLog + " - [GISA] - Login Fallito: ip=" + context.getIpAddress() + " codiceFiscale="
					+ codiceFiscale);
			return "LoginRetry";
		}
		// A valid user must have this information in their session, or the
		// security manager will not let them access any secure pages
		context.getSession().setAttribute("User", thisUser);
		context.getSession().setAttribute("Time", time);

		context.getSession().setAttribute("ConnectionElement", ce);

		if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null
				&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si")) {
			if (thisUser.getUserId() > 0) {
				ArrayList<UserOperation> op = new ArrayList<UserOperation>();
				UserOperation uo = new UserOperation();
				uo.setUser_id(thisUser.getUserId());
				uo.setUsername(thisUser.getUsername());
				uo.setIp(thisUser.getUserRecord().getIp());
				uo.setData(new Timestamp(new Date().getTime()));
				uo.setUrl(context.getRequest().getRequestURL().toString()
						+ (context.getRequest().getQueryString() != null ? "?" + context.getRequest().getQueryString()
								: ""));
				uo.setParameter("");
				uo.setUserBrowser(context.getRequest().getHeader("user-agent"));
				op.add(uo);

				MiddleServlet.writeStorico(op, "", false, super.getSuffiso(context));
			}
		}

		if (applicationPrefs.isUpgradeable()) {
			if (roleId == 1 || "Administrator".equals(role)) {
				context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
				return "PerformUpgradeOK";
			} else {
				return "UpgradeCheck";
			}
		} else {
			// Check to see if user is already logged in.
			// If not then add them to the valid users list
			SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
					"SystemStatus")).get(ce.getUrl());
			SessionManager sessionManager = thisSystem.getSessionManager();
			if (sessionManager.isUserLoggedIn(userId)) {

				UserSession thisSession = sessionManager.getUserSession(userId);
				context.getSession().setMaxInactiveInterval(300);
				context.getRequest().setAttribute("Session", thisSession);

				return "LoginVerifyOK";

			}

			if (System.getProperty("DEBUG") != null) {
				logger.info(timeToLog + " [GISA] - Login-> Session Size: " + sessionManager.size());
			}
			// context.getSession().setMaxInactiveInterval(
			// thisSystem.getSessionTimeout());
			sessionManager.addUser(context, userId, thisUser.getUserRecord().getSuap());
		}
		// TODO: Replace this so it does not need to be maintained
		// NOTE: Make sure to update this similar code in the following method
		String redirectTo = "MyCFS.do?command=Home";
		if (hasPermission(context, "myhomepage-dashboard-view")) {
			context.getRequest().setAttribute("to_url", "MyCFS.do?command=Home");
		} else {
			if (hasPermission(context, "apicoltura-view")) {
				context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home");
			}
		}

		if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
			if (redirectTo != null) {
				// context.getRequest().removeAttribute("PageLayout");
				return "RedirectURL";
			}

			return "LoginOK";
		} else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
			return "CustomerPortalLoginOK";
		} else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
			return "ProductsPortalLoginOK";
		}
		if (redirectTo != null) {
			// context.getRequest().removeAttribute("PageLayout");
			return "RedirectURL";
		}
		return "LoginOK";
	}
	
	public String executeCommandValidate(ActionContext context) throws SQLException,
	NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchPaddingException 
	{
		String[] params = null;
		String decrypteToken = "";
		String encrypteToken = context.getParameter("encryptedToken");

		if (encrypteToken == null) 
		{
			System.out.println("executeCommandValidate - return null: " );
			context.getRequest().setAttribute("risultato", "KO: Token non passato");
			return "ValidateOK";
		} 
		else 
		{
			String msg = "" ;
	
			LoginBean loginBean = (LoginBean) context.getRequest().getAttribute("LoginBean");
			String username = context.getRequest().getParameter("username");
			String serverName = context.getRequest().getServerName();

	
			String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
			String gkHost = getPref(context, "GATEKEEPER.URL");
			String gkUser = getPref(context, "GATEKEEPER.USER");
			String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
			String siteCode = getPref(context, "GATEKEEPER.APPCODE");
			ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
			gk.setDriver(gkDriver);
			ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
			if (sqlDriver == null) 
			{
				context.getRequest().setAttribute("risultato", "KO: Connection missing");
				return "ValidateOK";
			}
			Connection db = null;
			ConnectionElement ce = null;

			String ip = context.getRequest().getRemoteAddr();

			try 
			{
				if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) 
				{
					// Scan for the virtual host
					db = sqlDriver.getConnection(gk, context);
					SiteList siteList = new SiteList();
					siteList.setSiteCode(siteCode);
					siteList.setVirtualHost(serverName);
					siteList.buildList(db);
					if (siteList.size() > 0) 
					{
						Site thisSite = (Site) siteList.get(0);
						ce = new ConnectionElement(thisSite.getDatabaseUrl(), thisSite.getDatabaseUsername(),
								thisSite.getDatabasePassword());
						ce.setDbName(thisSite.getDatabaseName());
						ce.setDriver(thisSite.getDatabaseDriver());
					} 
					else 
					{
						context.getRequest().setAttribute("risultato", "KO: Access denied, Host does not exist");
						return "ValidateOK";
					}
				} 
				else 
				{
					ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
					ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
					ce.setDriver(gkDriver);
				}
			} 
			catch (Exception e) 
			{
				loginBean.setMessage("* Gatekeeper: " + e.getMessage());
			} 
			finally 
			{
				if (db != null) 
				{
					sqlDriver.free(db, context);
				}
			}

			String ipAddressSuap2 = context.getParameter("SuapIP");

			if (!ip.equalsIgnoreCase(ipAddressSuap2)) 
			{
				if (context.getRequest().getParameter("debugServizioRest") != null) 
				{
					System.out.println("#SUAP#] [IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE ]" + ip
					+ " IP PASSATO : " + ipAddressSuap2 + "\nPROSEGUO LO STESSO POICHE' IN DEBUG");
				} 
				else 
				{
					System.out.println("#SUAP#] [ACCESSO FALLITO - IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE ]"
							+ ip + " IP PASSATO : " + ipAddressSuap2);
					
					loginBean.setMessage("ACCESSO FALLITO - IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE "+ ip + " IP PASSATO : " + ipAddressSuap2);
					context.getRequest().setAttribute("risultato", "KO: IP NON CORRISPONDENTE CON IL CHIAMANTE");
					return "ValidateOK";
				}
			}

			String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));

			context.getRequest().setAttribute("LoginBean", loginBean);
			if (ce == null) 
			{
				logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress() + " username="
				+ username);

				context.getRequest().setAttribute("risultato", "KO: Connection missing");
				return "ValidateOK";
			}

			UserBean thisUser = null;
			int userId = -1;
			int aliasId = -1;
			int roleId = -1;
			String role = null;
			String userId2 = null;
			java.util.Date now = new java.util.Date();

			try 
			{
				SystemStatus thisSystem = null;
				db = sqlDriver.getConnection(ce, context);

				System.out.println("#SUAP#] [RICHIESTA ACCESSO DA IP : ]" + ipAddressSuap2);
				if (!isInWhiteList(ipAddressSuap2, db)) 
				{
					loginBean.setMessage("INDIRIZZO DI PROVENIENZA NON RICONOSCIUTO");
					logger.info("TENTATIVO DI ACCESSO FALLITO PROVENIENTE DA IP : " + ipAddressSuap2
					+ ". INDIRIZZO NON PRESENTE IN WITHELIST");
					System.out.println("#SUAP#] [ACCESSO FALLITO - IP NON PRESENTE IN WHITE LIST : IP ]"
					+ ipAddressSuap2);

			
				
					loginBean.setMessage("TENTATIVO DI ACCESSO FALLITO PROVENIENTE DA IP : " + ipAddressSuap2+" INDIRIZZO NON RICONOSCIUTO");
					context.getRequest().setAttribute("risultato", "KO: IP NON PRESENTE IN WHITE LIST");
					return "ValidateOK";
				}

				long loginTime = 0;
				ArrayList<String> preSharedKeyList = getSharedKey(ipAddressSuap2, db);

				int numTentativo = 0;
				for (String preSharedKey : preSharedKeyList) 
				{
					numTentativo++;
					try 
					{

						if (preSharedKey.length() == 32) // caso esadecimale
							decrypteToken = NEWdecrypt(encrypteToken, lenientHexToBytes(preSharedKey));
						else
							decrypteToken = NEWdecrypt(encrypteToken, preSharedKey.getBytes());

						params = decrypteToken.split("@");

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						long currTime = System.currentTimeMillis();

						Date d = new Date(currTime);
						loginTime = sdf.parse(params[0]).getTime();

						if (currTime - loginTime > 15 * 1000 * 60) 
						{
							context.getRequest().setAttribute("risultato", "KO: Token non piu valido");

							System.out.println("#SUAP#] [ACCESSO FALLITO - TOKEN NON VALIDO PASSATO TROPPO TEMPO ] "
							+ ip + " DATA CORRENTE" + (d) + " DATA RICHIESTA " + sdf.parse(params[0]));

							loginBean.setMessage("ACCESSO FALLITO - TOKEN SCADUTO PER CUI NON VALIDO");
							context.getRequest().setAttribute("LoginBean", loginBean);
							return "ValidateOK";
						}

						break;

					} 
					catch (Exception e) 
					{
							System.out.println("#SUAP#] [ACCESSO FALLITO - TOKEN NON VALIDO SI E VERIFICATO IL SEGUENTE ERRORE IP:] "
								+ ip
								+ " ERRORE : "
								+ e.getMessage()
								+ "+TENTATIVO :"
								+ numTentativo
								+ " sharedKEy utilizzata " + preSharedKey);
							
							context.getRequest().setAttribute("risutato", "KO: " + e.getMessage());

							if (numTentativo == preSharedKeyList.size()) 
							{
								loginBean.setMessage("ACCESSO FALLITO - TOKEN NON VALIDO");
								context.getRequest().setAttribute("LoginBean", loginBean);
								context.getRequest().setAttribute("risutato", "KO: TOKEN NON VALIDO");
								return "ValidateOK";
							} 
							else
								continue;
					}

				}

				String istatComune = params[1];
				String ipPubblico = params[2];
				String cfRichiedente ="" ;
				String cfDelegato="";
				try
				{
			
					cfRichiedente = params[3];
		
					if(params.length==5)
						cfDelegato = params[4];
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					context.getRequest().setAttribute("risutato", "KO: NEL TOKEN MANCA IL CODICE FISCALE DELEGATO");
					System.out.println("###SUAP WARNING- NEL TOKEN MANCA IL CODICE FISCALE DELEGATO");
					return "ValidateOK";
				}

				java.sql.Date expDate = null;
				int tmpUserId = -1;
				int roleType = -1;

		
			} 
			catch (Exception e) 
			{
				loginBean.setMessage("* Access: " + e.getMessage());
				context.getRequest().setAttribute("risutato", "KO: " + e.getMessage());
				e.printStackTrace();
				if (System.getProperty("DEBUG") != null) {
					e.printStackTrace(System.out);
				}
				thisUser = null;
				return "ValidateOK";
			} 
			finally 
			{
				if (db != null) 
				{
					sqlDriver.free(db, context);
				}
			}
			
			context.getRequest().setAttribute("risultato", "OK");
			System.out.println("Sessione: " + context.getRequest().getSession().getId());
			System.out.println("Token messo in sessione: " + encrypteToken);
			context.getRequest().getSession().setAttribute("encryptedToken", encrypteToken);
			return "ValidateOK";

		}

	}
	
	
	
	

	public String executeCommandLoginSuapWithToken(ActionContext context) throws SQLException,
			NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, NoSuchPaddingException {

		// LogBean lb = new LogBean();

		
		String prefisso = PrefissoWrapper.valutaPrefissoWrapper(context);

		ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
				"applicationPrefs");
		// Process the login request

		String[] params = null;
		String decrypteToken = "";
		String encrypteToken = context.getParameter("encryptedToken");
		boolean isCategoriaQuattro = context.getParameter("categoriaQuattro") != null;

		// a seconda che sia login per cat4 o meno, cambiano le jsp ritornate

		if (encrypteToken == null) {
			System.out.println("executeCommandLoginSuapWithToken - return null: " );
			return null;
		} else {

			
			
			JSONObject jsonObj = ApplicationProperties.checkBrowser(context.getRequest().getHeader("User-Agent"));
			
			System.out.println("executeCommandLoginSuapWithToken - jsonObj: " + jsonObj);
			
			String msg = "" ;
			
			LoginBean loginBean = (LoginBean) context.getRequest().getAttribute("LoginBean");
			loginBean.setMessage("PROVA MESSAGGIO");
			String username = context.getRequest().getParameter("username");

			String serverName = context.getRequest().getServerName();

			
			if (jsonObj!=null)
			{
				if (jsonObj.getString("esito").equals("1"))
				{
					msg = jsonObj.getString("msg");
					loginBean.setMessage(msg);
					context.getRequest().setAttribute("LoginBean", loginBean);
					
				}
				
				if (jsonObj.getString("esito").equals("2"))
				{
					 msg = jsonObj.getString("msg");
				}
				

				
			}
			
			// Prepare the gatekeeper
			String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
			String gkHost = getPref(context, "GATEKEEPER.URL");
			String gkUser = getPref(context, "GATEKEEPER.USER");
			String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
			String siteCode = getPref(context, "GATEKEEPER.APPCODE");
			ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
			gk.setDriver(gkDriver);
			// Prepare the database connection
			ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
			if (sqlDriver == null) {
				loginBean.setMessage("Connection pool missing!");

				if (isCategoriaQuattro) {
					context.getRequest().setAttribute("risultato", "fallito");
					return "risultatoLoginPerCat4";
				} else
					return "LoginRetrySuap";
			}
			Connection db = null;
			ConnectionElement ce = null;

			// Controllo Ip - BlackList / WhiteList
			String ip = context.getRequest().getRemoteAddr();
			logger.info("IP loggato: " + ip);

			// ArrayList<String> coordinate = new ArrayList<String>();
			// Connect to the gatekeeper, validate this host and get new
			// connection
			// info
			try {
				if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
					// Scan for the virtual host
					db = sqlDriver.getConnection(gk, context);
					SiteList siteList = new SiteList();
					siteList.setSiteCode(siteCode);
					siteList.setVirtualHost(serverName);
					siteList.buildList(db);
					if (siteList.size() > 0) {

						Site thisSite = (Site) siteList.get(0);
						ce = new ConnectionElement(thisSite.getDatabaseUrl(), thisSite.getDatabaseUsername(),
								thisSite.getDatabasePassword());
						ce.setDbName(thisSite.getDatabaseName());
						ce.setDriver(thisSite.getDatabaseDriver());
					} else {
						loginBean.setMessage("* Access denied: Host does not exist (" + serverName + ")");
					}
				} else {
					// A single database is configured, so use it only
					// regardless of
					// ip/domain name
					ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
					ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
					ce.setDriver(gkDriver);
				}
			} catch (Exception e) {
				loginBean.setMessage("* Gatekeeper: " + e.getMessage());
			} finally {
				if (db != null) {
					sqlDriver.free(db, context);
				}
			}

			String ipAddressSuap2 = context.getParameter("SuapIP");

			//if (!ip.equalsIgnoreCase(ipAddressSuap2)) {
			if(false){

				if (context.getRequest().getParameter("debugServizioRest") != null) {
					System.out.println("#SUAP#] [IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE ]" + ip
							+ " IP PASSATO : " + ipAddressSuap2 + "\nPROSEGUO LO STESSO POICHE' IN DEBUG");
				} else {
					System.out
							.println("#SUAP#] [ACCESSO FALLITO - IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE ]"
									+ ip + " IP PASSATO : " + ipAddressSuap2);

					if (isCategoriaQuattro) {
						context.getRequest().setAttribute("risultato", "fallito");
						return "risultatoLoginPerCat4";
					}
					loginBean.setMessage("ACCESSO FALLITO - IP NON CORRISPONDENTE CON IL CHIAMANTE : IP CHIAMANTE "+ ip + " IP PASSATO : " + ipAddressSuap2);
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetrySuap";
				}
			}

			String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));

			context.getRequest().setAttribute("LoginBean", loginBean);
			if (ce == null) {
				// lb.store( -1, -1, 1, "Login Fallito ",username, context, db
				// );
				logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress() + " username="
						+ username);

				if (isCategoriaQuattro) {
					context.getRequest().setAttribute("risultato", "fallito");
					return "risultatoLoginPerCat4";
				} else
					return "LoginRetrySuap";
			}

			// Connect to the customer database and validate user
			UserBean thisUser = null;
			int userId = -1;
			int aliasId = -1;
			int roleId = -1;
			String role = null;
			String userId2 = null;
			java.util.Date now = new java.util.Date();

			boolean continueId = true;
			try {
				SystemStatus thisSystem = null;
				db = sqlDriver.getConnection(ce, context);

				System.out.println("#SUAP#] [RICHIESTA ACCESSO DA IP : ]" + ipAddressSuap2);
				//if (!isInWhiteList(ipAddressSuap2, db)) {
				if(false){
					loginBean.setMessage("INDIRIZZO DI PROVENIENZA NON RICONOSCIUTO");

					logger.info("TENTATIVO DI ACCESSO FALLITO PROVENIENTE DA IP : " + ipAddressSuap2
							+ ". INDIRIZZO NON PRESENTE IN WITHELIST");
					System.out.println("#SUAP#] [ACCESSO FALLITO - IP NON PRESENTE IN WHITE LIST : IP ]"
							+ ipAddressSuap2);

					if (isCategoriaQuattro) {
						context.getRequest().setAttribute("risultato", "fallito");
						return "risultatoLoginPerCat4";
					} else
					{
						
						loginBean.setMessage("TENTATIVO DI ACCESSO FALLITO PROVENIENTE DA IP : " + ipAddressSuap2+" INDIRIZZO NON RICONOSCIUTO");
						context.getRequest().setAttribute("LoginBean", loginBean);
						return "LoginRetrySuap";
						
					}

				}

				long loginTime = 0;
				
				
				
				try
				{
					/*nuova gestione */
					ClientSCAAesServlet cclient = new ClientSCAAesServlet();
					decrypteToken = cclient.decrypt(encrypteToken);
					params = decrypteToken.split("@");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					long currTime = System.currentTimeMillis();

					Date d = new Date(currTime);
					loginTime = sdf.parse(params[0]).getTime();

					if (currTime - loginTime > 15 * 1000 * 60) {
						context.getRequest().setAttribute("dettagli_problema", "Token non piu valido");

						System.out.println("#SUAP#] [ACCESSO FALLITO - TOKEN NON VALIDO PASSATO TROPPO TEMPO ] "
								+ ip + " DATA CORRENTE" + (d) + " DATA RICHIESTA " + sdf.parse(params[0]));

						if (isCategoriaQuattro) {
							context.getRequest().setAttribute("risultato", "fallito");
							return "risultatoLoginPerCat4";
						} else {
							
							loginBean.setMessage("ACCESSO FALLITO - TOKEN SCADUTO PER CUI NON VALIDO");
							context.getRequest().setAttribute("LoginBean", loginBean);
							return "LoginRetrySuap";
						}
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					loginBean.setMessage("ACCESSO FALLITO - TOKEN NON VALIDO");
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetrySuap";
				}
				
				
				/* VECCHIA GESTIONE TOKEN -----> NON PRENDO PIU' LE CHIAVI DAL DB IN TABELLA PER COMUNE
				ArrayList<String> preSharedKeyList = getSharedKey(ipAddressSuap2, db);

				int numTentativo = 0;
				for (String preSharedKey : preSharedKeyList) {
					numTentativo++;
					try {

						
						if (preSharedKey.length() == 32) // caso esadecimale
							decrypteToken = NEWdecrypt(encrypteToken, lenientHexToBytes(preSharedKey));
						else
							decrypteToken = NEWdecrypt(encrypteToken, preSharedKey.getBytes());
						
						 
						
						params = decrypteToken.split("@");

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						long currTime = System.currentTimeMillis();

						Date d = new Date(currTime);
						loginTime = sdf.parse(params[0]).getTime();

						if (currTime - loginTime > 15 * 1000 * 60) {
							context.getRequest().setAttribute("dettagli_problema", "Token non piu valido");

							System.out.println("#SUAP#] [ACCESSO FALLITO - TOKEN NON VALIDO PASSATO TROPPO TEMPO ] "
									+ ip + " DATA CORRENTE" + (d) + " DATA RICHIESTA " + sdf.parse(params[0]));

							if (isCategoriaQuattro) {
								context.getRequest().setAttribute("risultato", "fallito");
								return "risultatoLoginPerCat4";
							} else {
								
								loginBean.setMessage("ACCESSO FALLITO - TOKEN SCADUTO PER CUI NON VALIDO");
								context.getRequest().setAttribute("LoginBean", loginBean);
								return "LoginRetrySuap";
							}
						}

						break;

					} catch (Exception e) {
						System.out
								.println("#SUAP#] [ACCESSO FALLITO - TOKEN NON VALIDO SI E VERIFICATO IL SEGUENTE ERRORE IP:] "
										+ ip
										+ " ERRORE : "
										+ e.getMessage()
										+ "+TENTATIVO :"
										+ numTentativo
										+ " sharedKEy utilizzata " + preSharedKey);

						context.getRequest().setAttribute("dettagli_problema",
								"Si e' verificato un problema nella decriptazione del token ");

						if (numTentativo == preSharedKeyList.size()) {
							if (isCategoriaQuattro) {
								context.getRequest().setAttribute("risultato", "fallito");
								return "risultatoLoginPerCat4";
							} else {
								
								loginBean.setMessage("ACCESSO FALLITO - TOKEN NON VALIDO");
								context.getRequest().setAttribute("LoginBean", loginBean);
								return "LoginRetrySuap";
							}
						} else
							continue;
					}

				}
				*/
				
				
				if(context.getRequest().getSession().getAttribute("encryptedToken")==null ||
				   (!((String)context.getRequest().getSession().getAttribute("encryptedToken")).equals(encrypteToken)))
				{
					loginBean.setMessage("Nessun token in sessione o token in sessione diverso da quello passato");
					System.out.println("Sessione: " + context.getRequest().getSession().getId());
					System.out.println("Nessun token in sessione o token in sessione diverso da quello passato. Token passato: " + encrypteToken + ", token in sessione: " + ((context.getRequest().getSession().getAttribute("encryptedToken")!=null)?((String)context.getRequest().getSession().getAttribute("encryptedToken")):("null")) );
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetrySuap";
				}
					
				String istatComune = params[1];
				String ipPubblico = params[2];
				String cfRichiedente ="" ;
				String cfDelegato="";
				try
				{
					
				 cfRichiedente = params[3];
				
				if(params.length==5)
					cfDelegato = params[4];
				}catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("###SUAP WARNING- NEL TOKEN MANCA IL CODICE FISCALE DELEGATO");
				}
				// If system is not upgraded, perform lightweight validation to
				// ensure backwards compatibility
				if (applicationPrefs.isUpgradeable()) {
					continueId = true;
				} else {
					// A good place to initialize this SystemStatus, must be
					// done
					// before getting a user
					Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
					thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce,
							thisSite.getLanguage());
					if (System.getProperty("DEBUG") != null) {
						logger.info("Login-> Retrieved SystemStatus from memory : "
								+ ((thisSystem == null) ? "false" : "true"));
					}

				}

				// Query the user record

				java.sql.Date expDate = null;
				int tmpUserId = -1;
				int roleType = -1;
				Suap s = new Suap();
				if (continueId) {
					// NOTE: The following is the simplest valid SQL that works
					// on all versions of Centric CRM. It must not be
					// modified with new fields because .war users need to
					// be able to login first, before the upgrade has happened
					//
					PreparedStatement pst = db
							.prepareStatement("SELECT distinct lp.code as id_provincia,lp.description as descrizione_provincia, c.id as idComuneSuap,c.nome as comune,a.ip_address_suap, a.istat_comune,a."
									+ DatabaseUtils.addQuotes(db, "password")
									+ ",a.pec_suap,a.callback_suap,a.callback_suap_ko, a.role_id, r."
									+ DatabaseUtils.addQuotes(db, "role")
									+ ", a.expires, a.alias, a.user_id, r.role_type "
									+ "FROM "
									+ DatabaseUtils.addQuotes(db, "access")
									+ super.getSuffiso(context)
									+ " a,whitelist_ip iplist, "
									+ DatabaseUtils.addQuotes(db, "role")
									+ super.getSuffiso(context)
									+ " r,comuni1 c , lookup_province lp "
									+ " WHERE c.istat::int = a.istat_comune::int and lp.code=c.cod_provincia::int and a.role_id = r.role_id and iplist.istat_comune=a.istat_comune "
									+ "AND a.enabled = true and iplist.ip = ? and a.istat_comune = ? AND a.trashed_date is null ");

					// pst.setString(1, ipAddressSuap);
					pst.setString(1, ipPubblico);
					pst.setString(2, istatComune);

					ResultSet rs = pst.executeQuery();

					if (rs.next()) {

						roleId = rs.getInt("role_id");
						role = rs.getString("role");
						expDate = rs.getDate("expires");
						aliasId = rs.getInt("alias");
						tmpUserId = rs.getInt("user_id");
						roleType = rs.getInt("role_type");

						s.setIstaComune(rs.getString("istat_comune"));
						s.setIpAddressSuap(rs.getString("ip_address_suap"));
						s.setCallbackSuapKo(rs.getString("callback_suap_ko"));
						s.setDescrizioneComune(rs.getString("comune"));
						s.setIdComuneSuap(rs.getInt("idComuneSuap"));
						s.setDescrizioneProvincia(rs.getString("descrizione_provincia"));
						s.setIdProvinciaSuap(rs.getInt("id_provincia"));
						s.setCallbackSuap(rs.getString("callback_suap"));
						s.setPec(rs.getString("pec_suap"));
						s.setEncryptedToken(encrypteToken);

					}
					rs.close();
					pst.close();
					if (tmpUserId == -1) {
						// NOTE: This could be modified so that LDAP records get
						// inserted into CRM on the fly if they do not exist yet

						loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));

					} else if (expDate != null && now.after(expDate)) {
						// Login expired
						loginBean.setMessage("* " + thisSystem.getLabel("login.msg.accountExpired"));
					} else {
						userId = tmpUserId;

					}
				}

				// Perform rest of user initialization if a valid user
				if (userId > 0) {
					thisUser = new UserBean();
					thisUser.setSessionId(context.getSession().getId());

					thisUser.setUserId(aliasId > 0 ? aliasId : userId);
					thisUser.setActualUserId(userId);
					thisUser.setConnectionElement(ce);
					thisUser.setClientType(context.getRequest());

					User userRecordCopia = thisSystem.getUser(thisUser.getUserId());
					User userRecord = new User();
					userRecord.buildRecord(userRecordCopia);

					s.setCodiceFiscaleRichiedente(cfRichiedente);
					s.setCodiceFiscaleDelegato(cfDelegato);
					s.setContesto("Suap");
					s.setEncryptedToken(encrypteToken);
					userRecord.setSuap(s);

					if (userRecord != null) {
						if (System.getProperty("DEBUG") != null) {
							logger.info(timeToLog + " [GISA] - Login-> Retrieved user from memory: "
									+ userRecord.getUsername());
						}

					}

					thisUser.setIdRange(userRecord.getIdRange());
					userRecord.setIp(context.getRequest().getRemoteAddr());

					thisUser.setUserRecord(userRecord);

					userRecord.updateLogin(db, super.getSuffiso(context), context);

					logger.info(timeToLog + " - [gisa] - Login Success: ip=" + context.getIpAddress() + " username="
							+ username + " user_id=" + thisUser.getUserId());

				} else {
					// lb.store( -1, -1, 1, "Login Fallito ",username, context,
					// db
					// );
					logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress() + " username="
							+ username);
					if (System.getProperty("DEBUG") != null) {

						logger.info("Login-> Fatal: User does not have an Id!");
					}
				}
			} catch (Exception e) {
				loginBean.setMessage("* Access: " + e.getMessage());
				e.printStackTrace();
				if (System.getProperty("DEBUG") != null) {
					e.printStackTrace(System.out);
				}
				thisUser = null;
			} finally {
				if (db != null) {
					sqlDriver.free(db, context);
				}
			}
			// If user record is not found, ask them to login again
			if (thisUser == null) {

				if (isCategoriaQuattro) {
					context.getRequest().setAttribute("risultato", "fallito");
					return "risultatoLoginPerCat4";
				} else {
					loginBean.setMessage("ACCESSO FALLITO - SUAP NON ACCREDITATO");
					context.getRequest().setAttribute("LoginBean", loginBean);
					return "LoginRetrySuap";
				}
			}

			context.getRequest().getSession().setAttribute("User", thisUser);
			context.getRequest().setAttribute("to_url_suap", "Impresa.do?command=SearchForm");
			context.getRequest().getSession().setAttribute("User", thisUser);
			context.getSession().setAttribute("ConnectionElement", ce);

			// Check to see if user is already logged in.
			// If not then add them to the valid users list
			SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
					"SystemStatus")).get(ce.getUrl());
			SessionManager sessionManager = thisSystem.getSessionManager();
			UserSession session = sessionManager.getUserSession(thisUser.getUserId());

			if (session != null && session.getSuap() != null) {
				UserSession thisSession = sessionManager.getUserSession(userId);
				context.getSession().setMaxInactiveInterval(300);
				context.getRequest().setAttribute("Session", thisSession);

				if (isCategoriaQuattro) {
					context.getRequest().setAttribute("risultato", "riuscito");
					context.getRequest().setAttribute("session_id", context.getSession().getId());
					context.getRequest().setAttribute(
							"comune_suap",
							((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
									.getDescrizioneComune()
									+ "");
					context.getRequest().setAttribute(
							"id_comune_suap",
							((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
									.getIstaComune()
									+ "");
					context.getRequest().setAttribute(
							"provincia",
							((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
									.getDescrizioneProvincia()
									+ "");
					context.getRequest().setAttribute(
							"id_provincia",
							((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
									.getIdProvinciaSuap()
									+ "");
					context.getRequest().setAttribute("id_user",
							((UserBean) context.getSession().getAttribute("User")).getUserId() + "");

					return "risultatoLoginPerCat4";
				} else {
					return "RedirectURLSuap";
				}
			}

			// if (sessionManager.isUserLoggedIn(userId)) {
			// UserSession thisSession = sessionManager.getUserSession(userId);
			// context.getSession().setMaxInactiveInterval(300);
			// context.getRequest().setAttribute("Session", thisSession);
			//
			// if (context.getRequest().getParameter("mobile") != null) {
			// return "LoginVerifyOKMobile";
			// }
			//
			// return "LoginVerifyOK";
			// }

			System.out.println("################## UTENTE LOGGATO IN SUAP ####### USER_ID" + thisUser.getUserId()
					+ "  cfffff " + thisUser.getUserRecord().getSuap().getCodiceFiscaleRichiedente());
			sessionManager.addUser(context, thisUser.getUserId(), thisUser.getUserRecord().getSuap());

			context.getRequest().setAttribute("to_url_suap", "Impresa.do?command=SearchForm");

			if (isCategoriaQuattro) {

				context.getRequest().setAttribute("session_id", context.getSession().getId());
				context.getRequest().setAttribute("risultato", "riuscito");
				context.getRequest().setAttribute(
						"comune_suap",
						((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
								.getDescrizioneComune()
								+ "");
				context.getRequest().setAttribute(
						"id_comune_suap",
						((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
								.getIstaComune()
								+ "");
				context.getRequest().setAttribute(
						"provincia",
						((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
								.getDescrizioneProvincia()
								+ "");
				context.getRequest().setAttribute(
						"id_provincia",
						((UserBean) context.getSession().getAttribute("User")).getUserRecord().getSuap()
								.getIdProvinciaSuap()
								+ "");
				context.getRequest().setAttribute("id_user",
						((UserBean) context.getSession().getAttribute("User")).getUserId() + "");

				return "risultatoLoginPerCat4";
			} else // "LoginOK";
			{
				return "RedirectURLSuap";
			}

		}

	}

	public String executeCommandListaAllerte(ActionContext context) throws Exception {
		String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));
		logger.info(timeToLog + " [GISA] - [ Login ][ executeCommandContaCani ]");
		long now = new java.util.Date().getTime();

		long time_diff = 0L;
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		Connection db = null;
		String gkDriver = prefs.get("GATEKEEPER.DRIVER");
		String gkHost = prefs.get("GATEKEEPER.URL");
		String gkUser = prefs.get("GATEKEEPER.USER");
		String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");

		ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
		gk.setDriver(gkDriver);
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

		try {
			db = sqlDriver.getConnection(gk, context);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			sqlDriver.free(db, context);
		}

		return "ListaAllerteOK";
	}

	/**
	 * Processes the user login
	 *
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 * @throws IOException
	 * @throws IndirizzoNotFoundException
	 * @since 1.0
	 */
	public String executeCommandLogin(ActionContext context) throws SQLException, IOException,
			IndirizzoNotFoundException {
		
		// LogBean lb = new LogBean();

		String timeToLog = sdfLog.format(new Date(System.currentTimeMillis()));

		ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
				"applicationPrefs");
		// Process the login request
		LoginBean loginBean = (LoginBean) context.getRequest().getAttribute("LoginBean");
		loginBean.checkURL(context);

		if (context.getParameter("access_position_lat") != null
				&& !"".equals(context.getParameter("access_position_lat")))
			loginBean.setAccess_position_lat(context.getParameter("access_position_lat"));

		if (context.getParameter("access_position_lon") != null
				&& !"".equals(context.getParameter("access_position_lon")))
			loginBean.setAccess_position_lon(context.getParameter("access_position_lon"));

		if (context.getParameter("access_position_err") != null)
			loginBean.setAccess_position_err(context.getParameter("access_position_err"));

		String username = loginBean.getUsername(); 
		String password = loginBean.getPassword();
		String serverName = context.getRequest().getServerName();

		String loginSospeso = ApplicationProperties.getProperty("loginSospeso");

		if (loginSospeso != null && loginSospeso.equals("si")) {
			loginBean.setMessage("Login temporaneamente sospeso - Il portale tornera' disponibile a breve");
			if (context.getRequest().getParameter("mobile") != null) {
				return "LoginRetryMobile";
			}
			return "LoginRetry";
		}

		JSONObject jsonObj = ApplicationProperties.checkBrowser(context.getRequest().getHeader("User-Agent"));
		String msg = "";
		if (jsonObj != null) {
			if (jsonObj.getString("esito").equals("1")) {
				msg = jsonObj.getString("msg");
				loginBean.setMessage(msg);
				context.getRequest().setAttribute("LoginBean", loginBean);
				return "LoginRetry";
			}

			if (jsonObj.getString("esito").equals("2")) {
				msg = jsonObj.getString("msg");
			}

		}

		// Throw out empty passwords
		if (password == null || "".equals(password.trim())) {
			if (context.getRequest().getParameter("mobile") != null) {
				return "LoginRetryMobile";
			}
			return "LoginRetry";
		}
		// Prepare the gatekeeper
		String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
		String gkHost = getPref(context, "GATEKEEPER.URL");
		String gkUser = getPref(context, "GATEKEEPER.USER");
		String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
		String siteCode = getPref(context, "GATEKEEPER.APPCODE");
		ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
		gk.setDriver(gkDriver);
		// Prepare the database connection
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

		if (sqlDriver == null) {
			loginBean.setMessage("Connection pool missing!");
			if (context.getRequest().getParameter("mobile") != null) {
				return "LoginRetryMobile";
			}
			return "LoginRetry";
		}
		Connection db = null;
		ConnectionElement ce = null;

		// Controllo Ip - BlackList / WhiteList
		String ip = context.getRequest().getRemoteAddr();
		logger.info("IP loggato: " + ip);
		
		/**
		 * CONTROLLO SE L'UTENTE CHE SI STA LOGGANDO IN GISA E' ANAGRAFATO SU
		 * GISA_EXT
		 */

		String ambiente = (String) context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");

		if (ambiente == null || "".equalsIgnoreCase(ambiente)) {
			try {
				db = sqlDriver.getConnection(gk, context);
				
				//CERCO DI ENTRARE
				
				//CHECK LOCK
					if (CheckLock.checkLocked(db, context.getIpAddress(), username)){
						context.getRequest().setAttribute("messaggio", "Accesso bloccato a causa dei troppi tentativi falliti. Attendere circa 3 minuti per riprovare.");
						return "LoginRetry";
					}
				//FINE CHECK LOCK
				
				PreparedStatement pst2 = db.prepareStatement("SELECT a." + DatabaseUtils.addQuotes(db, "password")
						+ ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role")
						+ ", a.expires, a.alias, a.user_id, r.role_type " + "FROM "
						+ DatabaseUtils.addQuotes(db, "access_ext") + " a, " + DatabaseUtils.addQuotes(db, "role_ext")
						+ " r " + "WHERE a.role_id = r.role_id " + "AND " + DatabaseUtils.toLowerCase(db)
						+ "(a.username) = ? AND a.in_access is not false "
						+ "AND a.enabled = ? and a.trashed_date is null  and r.super_ruolo=2");
				pst2.setString(1, username.toLowerCase());
				pst2.setBoolean(2, true);
				ResultSet rs2 = pst2.executeQuery();
				if (rs2.next()) {

					try {
						return executeCommandLoginGisaExt(context);
					} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException
							| BadPaddingException | NoSuchPaddingException | FileAesKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch(Exception ex)
					{
						ex.printStackTrace();
					}
					/**
					 * se l'utente e' anagrafato su ext lo rimando alla login su
					 * applicativo ext
					 * 
					 * 
					 */
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				sqlDriver.free(db, context);
			}
		}

		if (!ip.equals("0:0:0:0:0:0:0:1")) { // se non e' localhost ha senso
			// controllare l'ip
			try {
				db = sqlDriver.getConnection(gk, context);

				if (!isInWhiteList(ip, db) && (isInBlackList(ip, db) || isInBlackListRange(ip, db))) {
					logger.info("Tentativo di accesso da parte dell'IP < " + ip + " >");
					loginBean.setMessage("Accesso al sistema negato per il seguente IP: " + ip);
					context.getRequest().setAttribute("isBannato", "si");

					MiddleServlet.writeLoginFault(username, ip, loginBean.getMessage(), context);

					if (context.getRequest().getParameter("mobile") != null) {
						return "LoginRetryMobile";
					}
					return "LoginRetry";
				}

			} catch (Exception e) {
				logger.error("Eccezione durante il controllo dell'IP.");
				e.printStackTrace();
			} finally {
				if (db != null) {
					sqlDriver.free(db, context);
				}
			}

		}
		// ArrayList<String> coordinate = new ArrayList<String>();
		// Connect to the gatekeeper, validate this host and get new connection
		// info
		try {
			if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
				// Scan for the virtual host
				db = sqlDriver.getConnection(gk, context);
				SiteList siteList = new SiteList();
				siteList.setSiteCode(siteCode);
				siteList.setVirtualHost(serverName);
				siteList.buildList(db);
				if (siteList.size() > 0) {

					Site thisSite = (Site) siteList.get(0);
					ce = new ConnectionElement(thisSite.getDatabaseUrl(), thisSite.getDatabaseUsername(),
							thisSite.getDatabasePassword());
					ce.setDbName(thisSite.getDatabaseName());
					ce.setDriver(thisSite.getDatabaseDriver());
				} else {
					loginBean.setMessage("* Access denied: Host does not exist (" + serverName + ")");
				}
			} else {
				// A single database is configured, so use it only regardless of
				// ip/domain name
				ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
				ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
				ce.setDriver(gkDriver);
			}
		} catch (Exception e) {
			loginBean.setMessage("* Gatekeeper: " + e.getMessage());
		} finally {
			if (db != null) {
				sqlDriver.free(db, context);
			}
		}
		if (ce == null) {
			// lb.store( -1, -1, 1, "Login Fallito ",username, context, db );
			logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress() + " username=" + username);

			MiddleServlet.writeLoginFault(username, context.getIpAddress(), "Connection Element null", context);

			if (context.getRequest().getParameter("mobile") != null) {
				return "LoginRetryMobile";
			}
			return "LoginRetry";
		}

		// Connect to the customer database and validate user
		UserBean thisUser = null;

		int userId = -1;
		int aliasId = -1;
		int roleId = -1;
		String role = null;
		String userId2 = null;
		java.util.Date now = new java.util.Date();
		boolean continueId = false;
		try {
			SystemStatus thisSystem = null;
			db = sqlDriver.getConnection(ce, context);
			// If system is not upgraded, perform lightweight validation to
			// ensure backwards compatibility
			if (applicationPrefs.isUpgradeable()) {
				continueId = true;
			} else {
				// A good place to initialize this SystemStatus, must be done
				// before getting a user
				Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
				thisSystem = SecurityHook.retrieveSystemStatus(context.getServletContext(), db, ce,
						thisSite.getLanguage());
				if (System.getProperty("DEBUG") != null) {
					logger.info("Login-> Retrieved SystemStatus from memory : "
							+ ((thisSystem == null) ? "false" : "true"));
				}
				continueId = CustomHook.populateLoginContext(context, db, thisSystem, loginBean);
			}

			// Query the user record
			String pw = null;
			java.sql.Date expDate = null;
			int tmpUserId = -1;
			int roleType = -1;
			if (continueId) {
				String endPoing = (String) context.getServletContext().getAttribute("END_POINT_ROLE_EXT");
				// NOTE: The following is the simplest valid SQL that works
				// on all versions of Centric CRM. It must not be
				// modified with new fields because .war users need to
				// be able to login first, before the upgrade has happened
				PreparedStatement pst = db.prepareStatement("SELECT a." + DatabaseUtils.addQuotes(db, "password")
						+ ", a.role_id, r." + DatabaseUtils.addQuotes(db, "role")
						+ ", a.expires, a.alias, a.user_id, r.role_type " + "FROM "
						+ DatabaseUtils.addQuotes(db, "access") + super.getSuffiso(context) + " a, "
						+ DatabaseUtils.addQuotes(db, "role") + super.getSuffiso(context) + " r "
						+ "WHERE a.role_id = r.role_id " + "AND " + DatabaseUtils.toLowerCase(db)
						+ "(a.username) = ? AND a.in_access is not false "
						+ "AND a.enabled = ? and a.trashed_date is null "
						+ ((endPoing != null && !"".equals(endPoing)) ? " and r.super_ruolo=" + endPoing : ""));
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
					
					//LOGIN ERRATA
					
					//CHECK LOCK
					CheckLock.incLock(db, context.getIpAddress(), username);
					//FINE CHECK LOCK
					
					// NOTE: This could be modified so that LDAP records get
					// inserted into CRM on the fly if they do not exist yet

					// User record not found in database
					// logger.info("#### ACCESSO NEGATO PER UTENTE "+username+" #####");
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
					if (System.getProperty("DEBUG") != null) {
						logger.info("Login-> User record not found in database for: " + username.toLowerCase());
					}
					MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(), context);

				} else if (expDate != null && now.after(expDate)) {
					// Login expired
					loginBean.setMessage("* " + thisSystem.getLabel("login.msg.accountExpired"));
					MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(), context);

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
						
						if (pw == null
								|| pw.trim().equals("")
								|| (!pw.equals(password) && !context.getServletContext().getAttribute("GlobalPWInfo")
										.equals(password))) {
							
							//LOGIN ERRATA
							
							//CHECK LOCK
							CheckLock.incLock(db, context.getIpAddress(), username);
							//FINE CHECK LOCK
							
							loginBean.setMessage("* " + thisSystem.getLabel("login.msg.invalidLoginInfo"));
							MiddleServlet.writeLoginFault(username, context.getIpAddress(), loginBean.getMessage(),
									context);
							
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

			// Perform rest of user initialization if a valid user
			if (userId > -1) {
				thisUser = new UserBean();
				thisUser.setSessionId(context.getSession().getId());
				thisUser.setUserId(aliasId > 0 ? aliasId : userId);
				thisUser.setActualUserId(userId);
				thisUser.setConnectionElement(ce);
				thisUser.setClientType(context.getRequest());

				User userRecord = null;
				if (thisSystem != null) {
					// The user record must be in user cache to proceed
					userRecord = thisSystem.getUser(thisUser.getUserId());

					Suap ss = userRecord.getSuap();
					ss.setContesto(getContext(context));
					userRecord.setSuap(ss);

					if (userRecord != null) {
						// userRecord.setSuap(null);
						if (System.getProperty("DEBUG") != null) {
							logger.info("Login-> Retrieved user from memory: " + userRecord.getUsername());
						}
						// CHECK LAST LOGIN
						if (userRecord.getLastLogin() != null && !userRecord.getLastLogin().equals("")) {
							String s = new SimpleDateFormat("dd/MM/yyyy").format(userRecord.getLastLogin());
							String timeout = "6";// ApplicationProperties.getProperty("timeout");
							int time = Integer.parseInt(timeout);
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.MONTH, -time);
							Timestamp calTime = new Timestamp(cal.getTimeInMillis());
							// Se la last login e' antecedente al timeout
							if (userRecord.getLastLogin() != null && userRecord.getLastLogin().before(calTime)) {
								logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress()
										+ " username=" + username);
								context.getRequest()
										.setAttribute(
												"messaggio",
												"ATTENZIONE! NON PUOI ACCEDERE AL SISTEMA IN QUANTO IL TUO ACCOUNT RISULTA DISATTIVATO. "
														+ "IL TUO ULTIMO ACCESSO RISALE AL GIORNO "
														+ s
														+ ". PER ESSERE RIATTIVATO, SI PREGA DI CONTATTARE IL SERVIZIO DI HD I LIVELLO.");
								return "LoginRetry";
							} else {

								thisUser.setIdRange(userRecord.getIdRange());
								thisUser.setUserRecord(userRecord);

								// Log that the user attempted login (does not
								// necessarily mean logged in
								// anymore due to the single-session manager
								// below
								userRecord.setIp(context.getRequest().getRemoteAddr());
								userRecord.setAccess_position_lat(loginBean.getAccess_position_lat());
								userRecord.setAccess_position_lon(loginBean.getAccess_position_lon());
								userRecord.setAccess_position_err(loginBean.getAccess_position_err());

								// userRecord.setBrowser(context.getRequest().getHeader("user-agent"));
								userRecord.updateLogin(db, super.getSuffiso(context), context);
								// coordinate =
								// userRecord.getCoordinateUltimoAccesso(db);

								userRecord.checkWebdavAccess(db, context.getRequest().getParameter("password"));

								// lb.store( thisUser.getSiteId(),
								// thisUser.getUserId(),
								// 2, "Login Success ",username, context, db );
								logger.info(timeToLog + " - [gisa] - Login Success: ip=" + context.getIpAddress()
										+ " username=" + username + " user_id=" + thisUser.getUserId());
							}
						}
					}// chiudi if user not null

					if (ambiente != null && !"".equals(ambiente)) {
						SoggettoFisico sf = new SoggettoFisico(userRecord.getContact().getCodiceFiscale().trim(), db);
						thisUser.setSoggetto(sf);
					}
					if (!thisSystem.hasPermissions()) {
						logger.info("Login-> This system does not have any permissions loaded!");
					}
				} else {
					if (System.getProperty("DEBUG") != null) {
						logger.info("Login-> Fatal: User not found in this System!");
					}
				}
			} else {
				// lb.store( -1, -1, 1, "Login Fallito ",username, context, db
				// );
				logger.info(timeToLog + " - [gisa] - Login Fallito: ip=" + context.getIpAddress() + " username="
						+ username);
				if (System.getProperty("DEBUG") != null) {

					logger.info("Login-> Fatal: User does not have an Id!");
				}

			}
		} catch (Exception e) {
			loginBean.setMessage("* Access: " + e.getMessage());
			e.printStackTrace();
			if (System.getProperty("DEBUG") != null) {
				e.printStackTrace(System.out);
			}
			thisUser = null;
		} finally {
			if (db != null) {
				sqlDriver.free(db, context);
			}
		}
		// If user record is not found, ask them to login again
		if (thisUser == null) {

			return "LoginRetry";
		}
		// A valid user must have this information in their session, or the
		// security manager will not let them access any secure pages
		// if(ip.startsWith(applicationPrefs.get("IP_MOBILE")))
		// {
		//
		// PreparedStatement pst = db.prepareStatement(
		// "SELECT (cognome || ' ' || nome) as assegnatario from monitoring_report where ip_portatile = ?");
		// pst.setString(1, ip);
		// ResultSet rs = pst.executeQuery();
		// if (rs.next()) {
		// thisUser.setAssegnatario(rs.getString("assegnatario"));
		// }
		// User user_record = thisUser.getUserRecord();
		// user_record.setTipoDispositivo(applicationPrefs.get("CONNECTION_MOBILE"));
		// thisUser.setUserRecord(user_record);
		// }
		context.getRequest().getSession().setAttribute("User", thisUser);
		context.getSession().setAttribute("ConnectionElement", ce);

		if (applicationPrefs.isUpgradeable()) {
			if (roleId == 1 || "Administrator".equals(role)) {
				context.getSession().setAttribute("UPGRADEOK", "UPGRADEOK");
				return "PerformUpgradeOK";
			} else {
				return "UpgradeCheck";
			}
		} else {
			// Check to see if user is already logged in.
			// If not then add them to the valid users list
			SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
					"SystemStatus")).get(ce.getUrl());
			SessionManager sessionManager = thisSystem.getSessionManager();
			if (sessionManager.isUserLoggedIn(userId)) {
				UserSession thisSession = sessionManager.getUserSession(userId);
				context.getSession().setMaxInactiveInterval(300);
				context.getRequest().setAttribute("Session", thisSession);

				context.getRequest().setAttribute("access_position_lat", loginBean.getAccess_position_lat() + "");
				context.getRequest().setAttribute("access_position_lon", loginBean.getAccess_position_lon() + "");
				context.getRequest().setAttribute("access_position_err", loginBean.getAccess_position_err() + "");

				if (context.getRequest().getParameter("mobile") != null) {
					return "LoginVerifyOKMobile";
				}

				return "LoginVerifyOK";
			}
			if (System.getProperty("DEBUG") != null) {
				logger.info("Login-> Session Size: " + sessionManager.size());
			}
			// context.getSession().setMaxInactiveInterval(
			// thisSystem.getSessionTimeout());

			sessionManager.addUser(context, userId, thisUser.getUserRecord().getSuap());

			// if (coordinate.size()>0)
			// {
			//
			// UserSession thisSession =
			// (UserSession)sessionManager.getSessions().get(userId);
			//
			// thisSession.setAccess_position_lat(coordinate.get(0));
			// thisSession.setAccess_position_lon(coordinate.get(1));
			// thisSession.setAccess_position_date(coordinate.get(3));
			// thisSession.setAccess_position_err("Ultime coordinate del"+coordinate.get(3))
			// ;
			//
			// sessionManager.getSessions().put(userId, thisSession);
			// }
		}
		// TODO: Replace this so it does not need to be maintained
		// NOTE: Make sure to update this similar code in the following method
		String redirectTo = "MyCFS.do?command=Home&Message=" + msg;
		
		if (!hasPermission(context, "system-access-view")) {
			return "LoginNoAccessKO";
		}

		switch (thisUser.getRoleId()) {
		case (Role.RUOLO_APICOLTORE): {
			try {
				db = this.getConnection(context);
				DelegaList listaDeleghe = new DelegaList();
				listaDeleghe.setCodice_fiscale_delegante(thisUser.getContact().getCodiceFiscale().trim());
				listaDeleghe.setEnabled(true);
				listaDeleghe.buildList(db, this.getSystemStatus(context), context);
				if (listaDeleghe.size() > 0) {
					return "RedirectURLApicoltoreConferma";
				}
			} finally {
				this.freeConnection(context, db);
			}
		
			if (hasPermission(context, "apicoltura-view")) {
				context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home");
			}
			break;
		}

		case (Role.RUOLO_DELEGATO): {

			if (hasPermission(context, "apicoltura-view")) {
				context.getRequest().setAttribute("to_url", "DelegaAction.do?command=List");
			}
			break;
		}
		case Role.RUOLO_COMUNE: {
			context.getRequest().setAttribute("to_url", "OpuStab.do?command=SearchForm");
			break;

		}
		case Role.RUOLO_TRASPORTATORI_DISTRIBUTORI: {
			if (thisUser.getContact().getVisibilitaDelega() != null
					&& !"".equals(thisUser.getContact().getVisibilitaDelega())) {

				context.getRequest().setAttribute("to_url", "OpuStab.do?command=MyHomePage");

			} else {
				return "LoginRetry"; // sull'utente non e' stato settato il
										// codice fiscale , quindi non riesco a
										// capire a quale impresa fa riferimento
			}
			break;

		}
		case Role.RUOLO_GESTORE_PRODOTTI_SOA: {
			if (thisUser.getContact().getVisibilitaDelega() != null
					&& !"".equals(thisUser.getContact().getVisibilitaDelega())) {

				context.getRequest().setAttribute("to_url", "OpuStab.do?command=MyHomePage");

			} else {
				return "LoginRetry"; // sull'utente non e' stato settato il
										// codice fiscale , quindi non riesco a
										// capire a quale impresa fa riferimento
			}
			break;

		}
		default: {
			context.getRequest().setAttribute("to_url", "MyCFS.do?command=Home&Message=" + msg);
			break;
		}

		}

		if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null
				&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si")) {
			if (userId > 0) {
				ArrayList<UserOperation> op = new ArrayList<UserOperation>();
				UserOperation uo = new UserOperation();
				uo.setUser_id(thisUser.getUserId());
				uo.setUsername(thisUser.getUsername());
				uo.setIp(thisUser.getUserRecord().getIp());
				uo.setData(new Timestamp(new Date().getTime()));
				uo.setUrl(context.getRequest().getRequestURL().toString()
						+ (context.getRequest().getQueryString() != null ? "?" + context.getRequest().getQueryString()
								: ""));
				uo.setParameter("");
				uo.setUserBrowser(context.getRequest().getHeader("user-agent"));
				op.add(uo);
				MiddleServlet.writeStorico(op, "", false, super.getSuffiso(context));
			}
		}

		if (redirectTo != null) {
			// context.getRequest().removeAttribute("PageLayout");

			return "RedirectURL";
		}

		if (thisUser.getRole().toLowerCase().contains("BDApi"))
			context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home");
		else if (thisUser.getRole().toLowerCase().equalsIgnoreCase("BDAPI-DELEGATO"))
			context.getRequest().setAttribute("to_url", "DelegaAction.do?command=List");
		else
			context.getRequest().setAttribute("to_url", "MyCFS.do?command=Home&Message=" + msg);
		return "RedirectURL";// "LoginOK";
	}

	/**
	 * Confirms if the user wants to ovreride previous session or not.<br>
	 * and informs Session Manager accordingly.
	 *
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @throws SQLException
	 * @throws IndirizzoNotFoundException
	 */

	public String executeCommandLoginConfirm(ActionContext context) throws SQLException, IndirizzoNotFoundException {
				
		if (!hasPermission(context, "system-access-view")) {
			return "LoginNoAccessKO";
		}
		
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		if (thisUser == null) {
			return executeCommandLogout(context);
		}
		Connection db = null;

		String action = context.getRequest().getParameter("override");
		if ("yes".equals(action)) {
			SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
					"SystemStatus")).get(thisUser.getConnectionElement().getUrl());
			// context.getSession().setMaxInactiveInterval(systemStatus.getSessionTimeout());
			// replace userSession in SessionManager HashMap & reset timeout

			SessionManager sessionManager = systemStatus.getSessionManager();
			sessionManager.replaceUserSession(context, thisUser.getActualUserId(), thisUser.getUserRecord().getSuap());
			// TODO: Replace this so it does not need to be maintained
			// NOTE: Make sure to update this similar code in the previous
			// method
			if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null
					&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si")) {
				if (thisUser.getUserId() > 0) {
					ArrayList<UserOperation> op = new ArrayList<UserOperation>();
					UserOperation uo = new UserOperation();
					uo.setUser_id(thisUser.getUserId());
					uo.setUsername(thisUser.getUsername());
					uo.setIp(thisUser.getUserRecord().getIp());
					uo.setData(new Timestamp(new Date().getTime()));
					uo.setUrl(context.getRequest().getRequestURL().toString()
							+ (context.getRequest().getQueryString() != null ? "?"
									+ context.getRequest().getQueryString() : ""));
					uo.setParameter("");
					uo.setUserBrowser(context.getRequest().getHeader("user-agent"));

					op.add(uo);
					MiddleServlet.writeStorico(op, "", false, super.getSuffiso(context));
				}
			}

			if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
				ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
						"applicationPrefs");
				if (applicationPrefs.isUpgradeable()) {
					if (thisUser.getRoleId() == 1 || "Administrator".equals(thisUser.getRole())) {
						return "PerformUpgradeOK";
					} else {
						return "UpgradeCheck";
					}
				}
				// String redirectTo =
				// context.getRequest().getParameter("redirectTo");
				String redirectTo = "MyCFS.do?command=Home";
				try {
					db = this.getConnection(context);
					thisUser.setSoggetto(new SoggettoFisico(thisUser.getContact().getCodiceFiscale().trim(), db));
					context.getRequest().getSession().setAttribute("User", thisUser);
				} catch (SQLException e) {
					this.freeConnection(context, db);
				} finally {
					this.freeConnection(context, db);
				}

				switch (thisUser.getRoleId()) {
				case (Role.RUOLO_APICOLTORE): {
					try {
						db = this.getConnection(context);
						DelegaList listaDeleghe = new DelegaList();
						listaDeleghe.setCodice_fiscale_delegante(thisUser.getContact().getCodiceFiscale().trim());
						listaDeleghe.setEnabled(true);
						listaDeleghe.buildList(db, this.getSystemStatus(context), context);
						if (listaDeleghe.size() > 0) {
							return "RedirectURLApicoltoreConferma";
						}
					} finally {
						this.freeConnection(context, db);
					}

					if (hasPermission(context, "apicoltura-view")) {
						context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home");
					}
					break;
				}
				case (Role.RUOLO_DELEGATO): {

					if (hasPermission(context, "apicoltura-view")) {
						context.getRequest().setAttribute("to_url", "DelegaAction.do?command=List");
					}
					break;
				}
				case Role.RUOLO_COMUNE: {
					context.getRequest().setAttribute("to_url", "OpuStab.do?command=SearchForm");
					break;

				}
				case Role.RUOLO_TRASPORTATORI_DISTRIBUTORI: {
					if (thisUser.getContact().getVisibilitaDelega() != null
							&& !thisUser.getContact().getVisibilitaDelega().equals("")) {

						context.getRequest().setAttribute("to_url", "OpuStab.do?command=MyHomePage");

					} else {
						return "LoginRetry"; // sull'utente non e' stato settato
												// il codice fiscale , quindi
												// non riesco a capire a quale
												// impresa fa riferimento
					}
					break;

				}
				default: {
					String destinazione = "MyCFS.do?command=Home";
					if((String) context.getSession().getAttribute("destinazione") != null && !("").equals((String) context.getSession().getAttribute("destinazione")))
					{
						destinazione = (String) context.getSession().getAttribute("destinazione");
						System.out.println("[LOGIN NO PASSWORD URL DESTINAZIONE]: "+destinazione);
					}
					context.getRequest().setAttribute("to_url", destinazione);
					break;
				}

				}

				if (redirectTo != null) {
					context.getRequest().removeAttribute("PageLayout");

					return "RedirectURL";
				}

				return "LoginOK";
			} else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
				return "CustomerPortalLoginOK";
			} else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
				return "ProductsPortalLoginOK";
			}
		} else {
			// logout user from current session
			return executeCommandLogout(context);
		}
		String redirectTo = context.getRequest().getParameter("redirectTo");

		if (hasPermission(context, "myhomepage-dashboard-view")) {
			context.getRequest().setAttribute("to_url", "MyCFS.do?command=Home");
		} else {
			if (hasPermission(context, "apicoltura-view")) {
				context.getRequest().setAttribute("to_url", "ApicolturaAttivita.do?command=Home");
			}
		}
		if (redirectTo != null) {
			context.getRequest().removeAttribute("PageLayout");
			if (context.getRequest().getParameter("mobile") != null)
				return "RedirectURLMobile";
			return "RedirectURL";
		}
		if (context.getRequest().getParameter("mobile") != null)
			return "LoginOKMobile";
		return "LoginOK";
	}

	// public String executeCommandLoginConfirm(ActionContext context) {
	// UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	// if (thisUser == null) {
	// try {
	// return executeCommandLogout(context);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// String action = context.getRequest().getParameter("override");
	// if ("yes".equals(action)) {
	// ConnectionPool sqlDriver =
	// (ConnectionPool)
	// context.getSession().getServletContext().getAttribute("ConnectionPool");
	//
	// SystemStatus systemStatus = (SystemStatus) ((Hashtable)
	// context.getServletContext().getAttribute("SystemStatus")).get(thisUser.getConnectionElement().getUrl());
	// context.getSession().setMaxInactiveInterval(systemStatus.getSessionTimeout());
	// //replace userSession in SessionManager HashMap & reset timeout
	// if (System.getProperty("DEBUG") != null) {
	// System.out.println("Login-> Invalidating old Session");
	// }
	// SessionManager sessionManager = systemStatus.getSessionManager();
	// sessionManager.replaceUserSession(context,
	// thisUser.getActualUserId(),thisUser.getUserRecord().getSuap());
	//
	// // TODO: Replace this so it does not need to be maintained
	// // NOTE: Make sure to update this similar code in the previous method
	// if (thisUser.getRoleType() == Constants.ROLETYPE_REGULAR) {
	// ApplicationPrefs applicationPrefs = (ApplicationPrefs)
	// context.getServletContext().getAttribute("applicationPrefs");
	// if (applicationPrefs.isUpgradeable()) {
	// if (thisUser.getRoleId() == 1 ||
	// "Administrator".equals(thisUser.getRole()))
	// {
	// return "PerformUpgradeOK";
	// } else {
	// return "UpgradeCheck";
	// }
	// }
	// String redirectTo = context.getRequest().getParameter("redirectTo");
	// if (redirectTo != null) {
	// context.getRequest().removeAttribute("PageLayout");
	// return "RedirectURL";
	// }
	//
	// if ((String) context.getParameter("microchip") != null &&
	// !("").equals((String) context.getParameter("microchip"))){
	// //RegistrazioniAnimale registrazioniAnimale = new RegistrazioniAnimale();
	// //registrazioniAnimale.executeCommandAdd(context);
	// context.getSession().setAttribute("loginNoPassword", "true");
	//
	// String idTipologiaRichiestaDaVam = (String)
	// context.getParameter("idTipologiaEvento");
	// String dataRegistrazione = (String)
	// context.getParameter("dataRegistrazione");
	// System.out.println(dataRegistrazione);
	// int idTipoEvento = -1;
	// if (idTipologiaRichiestaDaVam != null &&
	// !("").equals(idTipologiaRichiestaDaVam) )
	// idTipoEvento = new Integer(idTipologiaRichiestaDaVam);
	// if (dataRegistrazione != null && !("").equals(dataRegistrazione) )
	// context.getRequest().setAttribute("dataRegistrazione",
	// dataRegistrazione);
	//
	// if ((String) context.getRequest().getParameter("caller") != null &&
	// !("").equals((String) context.getRequest().getParameter("caller")))
	// context.getSession().setAttribute("caller", (String)
	// context.getRequest().getParameter("caller"));
	// //context.getRequest().setAttribute("loginNoPassword", "true");
	// if (idTipoEvento == 1)
	// return "AggiungiAnimaleOK";
	//
	// return "AggiungiRegistrazioneOK";
	// }else if ((String) context.getParameter("tipologiaEvento") != null &&
	// !("").equals((String) context.getParameter("tipologiaEvento"))){
	// context.getSession().setAttribute("loginNoPassword", "true");
	//
	// if ((String) context.getRequest().getParameter("caller") != null &&
	// !("").equals((String) context.getRequest().getParameter("caller")))
	// context.getSession().setAttribute("caller", (String)
	// context.getRequest().getParameter("caller"));
	//
	//
	// return "DettaglioRegistrazioneOK";
	// }
	// else{
	// return "LoginOK";
	// }
	// } else if (thisUser.getRoleType() == Constants.ROLETYPE_CUSTOMER) {
	// return "CustomerPortalLoginOK";
	// } else if (thisUser.getRoleType() == Constants.ROLETYPE_PRODUCTS) {
	// return "ProductsPortalLoginOK";
	// }
	// } else {
	// //logout user from current session
	// try {
	// return executeCommandLogout(context);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// String redirectTo = context.getRequest().getParameter("redirectTo");
	// if (redirectTo != null) {
	// context.getRequest().removeAttribute("PageLayout");
	// return "RedirectURL";
	// }
	//
	//
	//
	// return "LoginOK";
	// }

	/**
	 * Used for invalidating a user session
	 *
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException
	 * @since 1.1
	 */

	public String executeCommandLogout(ActionContext context) throws SQLException {

		String ambiente = (String) context.getSession().getAttribute("ambiente");
		System.out.println("ambiente in sessione: " + ambiente);

		if (ambiente != null /*
		/*
		 Il sistema setta l'ambiente solo se veniamo da sca o se siamo in gisa_ext.
		 Pero' questo e' un difetto di progettazione. Nella versione precedente si rimanda a sca (loginSirv)
		 se l'ambiente e' diverso da null ,ma questo lo rimanderebbe anche se simao in ext
		 Quindi risolvo con una patch mettendo che rimanda a sca se l'ambiente di provenienza contiene effettivamente la parola sca
		 e non si tratta INOLTRE di una logout
		deve essere sca e non cambio utente TODO >> questo e' un bug di progettazione nel settare ambiente solo se e' sca e d ext */
				&& ( ambiente.toLowerCase().contains("sca") ||  ambiente.contains("login") )
				&& context.getRequest().getAttribute("isCambioUtente") == null) 
		{ 
			return executeCommandLoginSirv(context);
		} else {

			HttpSession oldSession = context.getRequest().getSession(false);
			if (oldSession != null) {
				// oldSession.removeAttribute("User"); //Commentata per log
				// storico operazioni
				try
				{
					oldSession.invalidate();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					System.out.println("ECCEZIONE NON BLOCCANTE SULL'INVALIDATE OLD SESSION() PROSEGUO!");
				}
			}

			return "LoginRetry";
		}
	}

	public String executeCommandLoginSirv(ActionContext context) {

		try {

			// String SIRV_URL =
			// InetAddress.getByName("endpointAPPSirv").getHostAddress();
			// String SIRV_URL =
			// ApplicationProperties.getProperty("ambiente_sirv");
			String SIRV_URL = (String) context.getSession().getAttribute("ambiente");
			HttpSession oldSession = context.getRequest().getSession(false);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			// String username = generate(thisUser.getUsername());
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

			System.out.println("#LOGOUT ---> LOGINSIRV TO: " + SIRV_URL);

			// Se vieni dal sistema SIRV..

			try
			{
				oldSession.invalidate();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("ECCEZIONE NON BLOCCANTE SULL'INVALIDATE OLD SESSION() PROSEGUO!");
			}

			//context.getResponse().sendRedirect(/*"http://" + */SIRV_URL + "/login.LoginNoPassword.us?encryptedToken=" + URLEncoder.encode(encryptedToken,"UTF-8"));
			
			if(false)
			//if(cf_spid==null || cf_spid.equalsIgnoreCase("") || cf_spid.equalsIgnoreCase("null"))
			{
				context.getRequest().setAttribute("url",SIRV_URL + "/login.LoginNoPassword.us?encryptedToken=" + URLEncoder.encode(encryptedToken,"UTF-8"));
			}
			else
			{
				SIRV_URL = SIRV_URL.replace("/sca", "/login");
				SIRV_URL = SIRV_URL.replace("https://login", "https://sca");
				SIRV_URL = SIRV_URL.replace("https:/login", "https://sca");
				context.getRequest().setAttribute("url",SIRV_URL + "/login.Login.us?cf_spid=" + cf_spid + "&tk_spid=" +  URLEncoder.encode(encryptedToken,"UTF-8"));
			}
			return "RedirectSCA"; 

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "";
	}

	public String generate(String username) {
		String ret = null;

		String originalToken = System.currentTimeMillis() + "@" + username;
		byte[] encryptedToken = null;
		KeyGenerator kgen = null;

		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			// SecretKeySpec skeySpec = new SecretKeySpec(asBytes(
			// Application.get( "KEY" ) ), "AES");
			SecretKeySpec skeySpec = getKeySpec(this.getClass().getResource("aes_key").getPath());
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			BASE64Encoder enc = new BASE64Encoder();
			encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ret = asHex(encryptedToken);

		return ret;
	}

	public static String asHex(byte buf[]) {
		StringBuffer sb = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			sb.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return sb.toString();
	}

	public String executeCommandLogoutSuap(ActionContext context) throws SQLException {

		System.out.println("Remote Addr : " + context.getRequest().getRemoteAddr());

		// LogBean lb = new LogBean();
		HttpSession oldSession = context.getRequest().getSession(false);
		Connection db = null;
		if (oldSession != null) {

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			Suap ss = thisUser.getUserRecord().getSuap();
			System.out.println("#####CALBACK DI KOO : "+ss.getCallbackSuapKo());
			context.getRequest().setAttribute("SupLogout", ss);
			oldSession.removeAttribute("User");
			oldSession.invalidate();
		}
		return "LoginRetrySuapJson";
	}

	public String executeCommandReport(ActionContext context) {
		String retPage = "";
		logger.info("context=" + context.toString());
		try {
			/*
			 * String path_phtml = "orsa/query_report.phtml"; String path_image
			 * = "orsa/temp/";commentato da carmela
			 */

			String path_phtml = "orsa/query2.phtml";
			String path_image = "orsa/demodata/tmp/";
			LoginBean loginBean = (LoginBean) context.getFormBean();
			loginBean.checkURL(context);

			String server = context.getParameter("server");
			String cartografia = context.getParameter("cartografia");
			String tipo = context.getParameter("tipo");
			String provincia = context.getParameter("provincia");
			String comune = context.getParameter("comune");
			String categoria = context.getParameter("categoria");
			String da_g = context.getParameter("da_g");
			String da_m = context.getParameter("da_m");
			String da_a = context.getParameter("da_a");
			String a_g = context.getParameter("a_g");
			String a_m = context.getParameter("a_m");
			String a_a = context.getParameter("a_a");

			if ((comune == null) || (comune.equals(""))) {
				comune = "TUTTI";
			}

			String prima_parte = "";
			if ((categoria != null) && !categoria.equals("tutte")) {
				String[] categorie = categoria.split("-");
				categoria = categorie[1];
				prima_parte = "alla categoria " + categorie[0];
			} else {
				prima_parte = "a tutte le categorie";
			}
			String com = null;
			if ((comune != null) && !comune.equals("tutte")) {
				com = "nel comune di " + comune;
			} else {
				com = "in tutti i comuni";
			}
			// inizio inserito da carmela
			String urlQueryReport = "http://" + server + "/" + path_phtml;
			String tempDirImageOrsa = "http://" + server + "/" + path_image;
			// fine inserito da carmela

			String dataDa = (da_g + "/" + da_m + "/" + da_a);
			String dataA = (a_g + "/" + a_m + "/" + a_a);

			String data_da = (da_a + "-" + da_m + "-" + da_g);
			String data_a = (a_a + "-" + a_m + "-" + a_g);

			String filePath = context.getServletContext().getRealPath("/") + "ImmaginiPdf" + "\\";
			Image loghi = Image.getInstance(filePath + "loghi.bmp");
			loghi.scalePercent(60);
			Image operatori = Image.getInstance(filePath + "operatori.bmp");
			Image simbolo1 = Image.getInstance(filePath + "simbolo_1.jpg");
			Image simbolo2 = Image.getInstance(filePath + "simbolo_2.jpg");
			Image simbolo3 = Image.getInstance(filePath + "simbolo_3.jpg");
			Image simbolo4 = Image.getInstance(filePath + "simbolo_4.jpg");
			Image simbolo5 = Image.getInstance(filePath + "simbolo_5.jpg");
			Image simbolo6 = Image.getInstance(filePath + "simbolo_6.jpg");
			Image simbolo7 = Image.getInstance(filePath + "simbolo_7.jpg");
			Image simbolo8 = Image.getInstance(filePath + "simbolo_8.jpg");

			initImmagini(loghi, operatori, simbolo1, simbolo2, simbolo3, simbolo4, simbolo5, simbolo6, simbolo7,
					simbolo8, filePath);

			// Apertura documento e impostazione delle dimensioni
			Document doc = new Document();
			ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
			PdfWriter docWriter = null;
			docWriter = PdfWriter.getInstance(doc, baosPDF);
			doc.setPageSize(PageSize.A4.rotate());
			doc.setMargins(0, 0, 0, 0);
			doc.open();

			// Creazione delle due tabelle di cui e' composto il pdf: titolo e
			// corpo
			PdfPTable titolo = new PdfPTable(100);
			titolo.setWidthPercentage(94);
			PdfPTable corpo = new PdfPTable(100);
			corpo.setWidthPercentage(94);
			PdfPTable tit = new PdfPTable(100);
			tit.setWidthPercentage(94);

			// Creazione tabella titolo
			rigaVuota(titolo);

			Paragraph p = null;

			if ("si".equalsIgnoreCase(cartografia)) {
				p = new Paragraph(new Phrase("REPORT CARTOGRAFICO DI SINTESI", new Font(null, 12, Font.BOLD)));
			} else {
				p = new Paragraph(new Phrase("REPORT DI ANALISI", new Font(null, 12, Font.BOLD)));
			}

			PdfPCell cella = new PdfPCell(p);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			cella.setColspan(100);
			titolo.addCell(cella);

			rigaVuota(titolo);

			if ("no".equalsIgnoreCase(cartografia)) // mappa
			{

				p = new Paragraph(new Phrase("GISA - Gestione Imprese Settore Alimentare",
						new Font(null, 10, Font.BOLD)));
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_CENTER);
				tit.addCell(cella);

				cella = new PdfPCell(loghi);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_CENTER);
				tit.addCell(cella);

				rigaVuota(tit);

				if ("osa".equalsIgnoreCase(tipo)) {
					if ((categoria != null && !categoria.equals("tutte"))
							&& (comune != null && !comune.equals("TUTTI"))) {
						p = new Paragraph(new Phrase("OSA appartenenti alla categoria " + prima_parte
								+ "presenti nel comune di " + comune + "provincia di " + provincia, new Font(null, 10,
								Font.NORMAL)));
						cella = new PdfPCell(p);
						cella.setColspan(100);
						cella.setBorderWidth(0);
						cella.setHorizontalAlignment(cella.ALIGN_CENTER);
						tit.addCell(cella);

					} else {
						if (categoria != null && !categoria.equals("")) {
							p = new Paragraph(new Phrase("OSA appartenenti " + prima_parte, new Font(null, 10,
									Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tit.addCell(cella);
						} else if (comune != null && !comune.equals("TUTTI")) {
							p = new Paragraph(new Phrase("OSA " + com, new Font(null, 10, Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tit.addCell(cella);
						} else if (provincia != null && !provincia.equals("")) {
							String prov = null;
							if (provincia.equals("AV")) {
								prov = "Avellino";
							} else if (provincia.equals("BN")) {
								prov = "Benevento";
							} else if (provincia.equals("CE")) {
								prov = "Caserta";
							} else if (provincia.equals("NA")) {
								prov = "Napoli";
							} else if (provincia.equals("SA")) {
								prov = "Salerno";
							}
							p = new Paragraph(new Phrase("OSA nella provincia di " + prov, new Font(null, 10,
									Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tit.addCell(cella);
						}

					}
				} else if ("sorveglianza".equalsIgnoreCase(tipo)) {
					String prov2 = null;
					if (provincia.equals("tutte")) {
						prov2 = "in tutte le province";
					} else if (provincia.equals("AV")) {
						prov2 = "nella provincia di Avellino";
					} else if (provincia.equals("BN")) {
						prov2 = "nella provincia di Benevento";
					} else if (provincia.equals("CE")) {
						prov2 = "nella provincia di Caserta";
					} else if (provincia.equals("NA")) {
						prov2 = "nella provincia di Napoli";
					} else if (provincia.equals("SA")) {
						prov2 = "nella provincia di Salerno";
					}
					p = new Paragraph(new Phrase("Sorveglianze " + prov2, new Font(null, 10, Font.NORMAL)));
					cella = new PdfPCell(p);
					cella.setColspan(100);
					cella.setBorderWidth(0);
					cella.setHorizontalAlignment(cella.ALIGN_CENTER);
					tit.addCell(cella);

				} else if ("campioni".equalsIgnoreCase(tipo)) {
					p = new Paragraph(new Phrase("Campioni prelevati da " + dataDa + " a " + dataA, new Font(null, 10,
							Font.NORMAL)));
					cella = new PdfPCell(p);
					cella.setColspan(100);
					cella.setBorderWidth(0);
					cella.setHorizontalAlignment(cella.ALIGN_CENTER);
					tit.addCell(cella);

				} else {
					rigaVuota(tit);
				}

				rigaVuota(tit);

			}

			rigaVuota(titolo);

			if ("si".equalsIgnoreCase(cartografia)) // mappa
			{
				String url = (server + "" + "");

				p = new Paragraph("  ");
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidthBottom(0);
				corpo.addCell(cella);

				p = new Paragraph("  ");
				cella = new PdfPCell(p);
				cella.setColspan(5);
				cella.setBorderWidthRight(0);
				cella.setBorderWidthTop(0);
				cella.setBorderWidthBottom(0);
				corpo.addCell(cella);

				long nomeFile = 0;

				nomeFile = System.currentTimeMillis();
				// logger.info("TIME="+nomeFile);

				mappa = creaMappa(urlQueryReport, tempDirImageOrsa, tipo, cartografia, comune, provincia, categoria,
						data_a, data_da, nomeFile);

				cella = new PdfPCell(mappa);
				cella.setColspan(65);
				cella.setBorderWidth(1);
				cella.setPadding(5);
				corpo.addCell(cella);

				PdfPTable tabella = new PdfPTable(100);
				cella = new PdfPCell(loghi);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_CENTER);
				tabella.addCell(cella);

				p = new Paragraph(new Phrase("GISA", new Font(null, 14, Font.BOLD)));
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_CENTER);
				tabella.addCell(cella);

				p = new Paragraph(new Phrase("Gestione Imprese Settore Alimentare", new Font(null, 10, Font.NORMAL)));
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_CENTER);
				tabella.addCell(cella);

				rigaVuota(tabella);

				if ("osa".equalsIgnoreCase(tipo)) {
					if ((categoria != null && !categoria.equals("tutte"))
							&& (comune != null && !comune.equals("TUTTI"))) {
						p = new Paragraph(new Phrase("OSA appartenenti alla categoria " + prima_parte
								+ "presenti nel comune di " + comune + "provincia di " + provincia, new Font(null, 10,
								Font.NORMAL)));
						cella = new PdfPCell(p);
						cella.setColspan(100);
						cella.setBorderWidth(0);
						cella.setHorizontalAlignment(cella.ALIGN_CENTER);
						tabella.addCell(cella);

					} else {
						if (categoria != null && !categoria.equals("")) {
							p = new Paragraph(new Phrase("OSA appartenenti " + prima_parte, new Font(null, 10,
									Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tabella.addCell(cella);
						} else if (comune != null && !comune.equals("TUTTI")) {
							p = new Paragraph(new Phrase("OSA " + comune, new Font(null, 10, Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tabella.addCell(cella);
						} else if (provincia != null && !provincia.equals("")) {
							String prov = null;
							if (provincia.equals("AV")) {
								prov = "Avellino";
							} else if (provincia.equals("BN")) {
								prov = "Benevento";
							} else if (provincia.equals("CE")) {
								prov = "Caserta";
							} else if (provincia.equals("NA")) {
								prov = "Napoli";
							} else if (provincia.equals("SA")) {
								prov = "Salerno";
							}
							p = new Paragraph(new Phrase("OSA nella provincia di " + prov, new Font(null, 10,
									Font.NORMAL)));
							cella = new PdfPCell(p);
							cella.setColspan(100);
							cella.setBorderWidth(0);
							cella.setHorizontalAlignment(cella.ALIGN_CENTER);
							tabella.addCell(cella);
						}

						url += ("");
					}
				} else if ("sorveglianza".equalsIgnoreCase(tipo)) {
					String prov2 = null;
					if (provincia.equals("tutte")) {
						prov2 = "in tutte le province";
					} else if (provincia.equals("AV")) {
						prov2 = "nella provincia di Avellino";
					} else if (provincia.equals("BN")) {
						prov2 = "nella provincia di Benevento";
					} else if (provincia.equals("CE")) {
						prov2 = "nella provincia di Caserta";
					} else if (provincia.equals("NA")) {
						prov2 = "nella provincia di Napoli";
					} else if (provincia.equals("SA")) {
						prov2 = "nella provincia di Salerno";
					}
					p = new Paragraph(new Phrase("Sorveglianze " + prov2, new Font(null, 10, Font.NORMAL)));
					cella = new PdfPCell(p);
					cella.setColspan(100);
					cella.setBorderWidth(0);
					cella.setHorizontalAlignment(cella.ALIGN_CENTER);
					tabella.addCell(cella);

					url += ("");
				} else if ("campioni".equalsIgnoreCase(tipo)) {
					p = new Paragraph(new Phrase("Campioni prelevati da " + dataDa + " a " + dataA, new Font(null, 10,
							Font.NORMAL)));
					cella = new PdfPCell(p);
					cella.setColspan(100);
					cella.setBorderWidth(0);
					cella.setHorizontalAlignment(cella.ALIGN_CENTER);
					tabella.addCell(cella);

					url += ("");
				} else {
					rigaVuota(tabella);
				}

				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);
				rigaVuota(tabella);

				p = new Paragraph("  ");
				cella = new PdfPCell(p);
				cella.setColspan(5);
				cella.setBorderWidth(0);
				tabella.addCell(cella);

				cella = new PdfPCell(tabellaLegenda(tipo, operatori, simbolo1, simbolo2, simbolo3, simbolo4, simbolo5,
						simbolo6, simbolo7, simbolo8));
				// cella.setBackgroundColor(Color.YELLOW);
				cella.setColspan(90);
				cella.setBorderWidth(1);
				tabella.addCell(cella);

				p = new Paragraph("  ");
				cella = new PdfPCell(p);
				cella.setColspan(5);
				cella.setBorderWidth(0);
				tabella.addCell(cella);

				cella = new PdfPCell(tabella);
				cella.setColspan(30);
				cella.setBorderWidthLeft(0);
				cella.setBorderWidthTop(0);
				cella.setBorderWidthBottom(0);
				corpo.addCell(cella);

				p = new Paragraph("  ");
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidthTop(0);
				corpo.addCell(cella);

				rigaVuota(corpo);
				rigaVuota(corpo);

			} else // tabella
			{
				String url = (server + "" + "");

				long nomeFile = 0;

				nomeFile = System.currentTimeMillis();

				// Prepare the gatekeeper
				String gkDriver = getPref(context, "GATEKEEPER.DRIVER");
				String gkHost = getPref(context, "GATEKEEPER.URL");
				String gkUser = getPref(context, "GATEKEEPER.USER");
				String gkUserPw = getPref(context, "GATEKEEPER.PASSWORD");
				String siteCode = getPref(context, "GATEKEEPER.APPCODE");
				ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
				gk.setDriver(gkDriver);
				// Prepare the database connection
				ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
				if (sqlDriver == null) {
					loginBean.setMessage("Connection pool missing!");
					return "LoginRetry";
				}
				Connection db = null;
				ConnectionElement ce = null;
				try {
					if ("true".equals((String) context.getServletContext().getAttribute("WEBSERVER.ASPMODE"))) {
						// Scan for the virtual host
						db = sqlDriver.getConnection(gk, context);
						tabellaAnalisi = creaTabella(db, urlQueryReport, tempDirImageOrsa, tipo, cartografia, comune,
								provincia, categoria, data_a, data_da, nomeFile);

					} else {
						// A single database is configured, so use it only
						// regardless of ip/domain name
						ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
						ce.setDbName(getPref(context, "GATEKEEPER.DATABASE"));
						ce.setDriver(gkDriver);
						db = sqlDriver.getConnection(ce, context);
						tabellaAnalisi = creaTabella(db, urlQueryReport, tempDirImageOrsa, tipo, cartografia, comune,
								provincia, categoria, data_a, data_da, nomeFile);

					}

					// tabellaAnalisi = creaTabella(db, urlQueryReport,
					// tempDirImageOrsa, tipo, cartografia, comune, provincia,
					// categoria, data_a, data_da, nomeFile);
					/*
					 * cella = new PdfPCell(tabellaAnalisi);
					 * cella.setColspan(100); cella.setBorderWidth(1);
					 * cella.setPadding(5); corpo.addCell(cella);
					 */

				} catch (Exception e) {
					loginBean.setMessage("* Gatekeeper: " + e.getMessage());
				} finally {
					if (db != null) {
						sqlDriver.free(db, context);
					}
				}
				if (ce == null) {
					return "LoginRetry";
				}

			}

			// Aggiunta di ora e data creazione
			if ("si".equalsIgnoreCase(cartografia)) {
				Date newDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String data = sdf.format(newDate);

				p = new Paragraph(data);
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
				corpo.addCell(cella);
			} else {
				Date newDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String data = sdf.format(newDate);

				p = new Paragraph(data);
				cella = new PdfPCell(p);
				cella.setColspan(100);
				cella.setBorderWidth(0);
				cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
				tabellaAnalisi.addCell(cella);
			}

			try {
				HeaderFooter header = new HeaderFooter(new Phrase(" "), true);
				header.setBorderWidth(0);
				doc.setHeader(header);

				doc.add(titolo);
				if ("no".equalsIgnoreCase(cartografia)) {
					doc.add(tit);
					doc.add(tabellaAnalisi);
				} else {
					doc.add(corpo);
				}

				doc.setFooter(header);
			} catch (DocumentException e) {

				e.printStackTrace();
			}

			doc.close();
			docWriter.close();

			context.getResponse().setContentType("application/pdf");
			context.getResponse().setHeader("Content-Disposition", "attachment; filename=\"risultato_ricerca.pdf\"");

			try {
				OutputStream out = context.getResponse().getOutputStream();
				baosPDF.writeTo(out);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retPage;
	}

	public String executeCommandContaOsa(ActionContext context) {

		logger.info(" [GISA] - [ Login ][ executeCommandContaOsa ]");

		int numero = -1;

		ApplicationPrefs prefs = null;
		ConnectionElement ce = null;
		ConnectionPool cp = null;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			String gkDriver = prefs.get("GATEKEEPER.DRIVER");
			String gkHost = prefs.get("GATEKEEPER.URL");
			String gkUser = prefs.get("GATEKEEPER.USER");
			String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ce = new ConnectionElement(gkHost, gkUser, gkUserPw);
			ce.setDriver(gkDriver);
			cp = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

			db = cp.getConnection(ce, context);

			pst = db.prepareStatement("select count(*) as numero from organization where tipologia in (1,2,3) and trashed_date is null");
			rs = pst.executeQuery();

			if (rs.next()) {
				numero = rs.getInt("numero");
			}

			context.getServletContext().setAttribute("numero", numero);

		} catch (Exception e) {
			logger.error(" [GISA] - Errore durante executeCommandContaOsa");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				cp.free(db, context);
			} catch (Exception e) {
				logger.error("[GISA] - EXCEPTION nella action executeCommandContaOsa della classe Login");
				e.printStackTrace();
			}

		}

		// try {
		//
		// if(numero > 0){
		//
		// context.getResponse().getWriter().write(numero);
		// //context.getResponse().getOutputStream().write( ("" +
		// numero).getBytes() );
		// }
		// else{
		// context.getResponse().getWriter().write("Si e' verificato un problema temporaneo: Numero Osa = "
		// + numero);
		// //context.getResponse().getOutputStream().write(
		// "Si e' verificato un problema temporaneo".getBytes() );
		// }
		// } catch (IOException ioe) {
		// ioe.printStackTrace();
		// }

		return "ContaOsaOK";
	}

	private void initImmagini(Image loghi, Image operatori, Image sim1, Image sim2, Image sim3, Image sim4, Image sim5,
			Image sim6, Image sim7, Image sim8, String path) {
		try {
			loghi = Image.getInstance(path + "loghi.bmp");
			loghi.scalePercent(75);

			operatori = Image.getInstance(path + "operatori.bmp");
			operatori.scalePercent(50);

			sim1 = Image.getInstance(path + "simbolo_1.jpg");
			sim1.scalePercent(50);

			sim2 = Image.getInstance(path + "simbolo_2.jpg");
			sim2.scalePercent(50);

			sim3 = Image.getInstance(path + "simbolo_3.jpg");
			sim3.scalePercent(50);

			sim4 = Image.getInstance(path + "simbolo_4.jpg");
			sim4.scalePercent(50);

			sim5 = Image.getInstance(path + "simbolo_5.jpg");
			sim5.scalePercent(50);

			sim6 = Image.getInstance(path + "simbolo_6.jpg");
			sim6.scalePercent(50);

			sim7 = Image.getInstance(path + "simbolo_7.jpg");
			sim7.scalePercent(50);

			sim8 = Image.getInstance(path + "simbolo_8.jpg");
			sim8.scalePercent(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rigaVuota(PdfPTable tabella) {
		Paragraph p = new Paragraph("  ");
		PdfPCell cella = new PdfPCell(p);
		cella.setColspan(100);
		cella.setBorderWidth(0);
		tabella.addCell(cella);
	}

	// metodi creaMappa() e tabellaLeggenda() presenti in stampaPdf inseriti da
	// carmela
	public Image creaMappa(String urlQueryReport, String tempDirImageOrsa, String tipo, String cartografia,
			String comune, String provincia, String categoria, String data_a, String data_da, long nome_immagine)
			throws Exception {

		// Primo passo: chiamare la pagina su Orsa che genera l'immagine e
		// recuperare il nome dall'html in output
		String urlMappa = urlQueryReport + "?tipo=" + tipo + "&cartografia=" + cartografia + "&nome_immagine="
				+ nome_immagine + ".png";

		if (comune != null) {
			urlMappa += "&comune=" + comune;
		}
		if (provincia != null) {
			urlMappa += "&provincia=" + provincia;
		}
		if (categoria != null) {
			urlMappa += "&categoria=" + categoria;
		}
		if (data_da != null) {
			urlMappa += "&dataDa=" + data_da;
		}

		if (data_a != null) {
			urlMappa += "&dataA=" + data_a;
		}

		URL url = new URL(urlMappa);

		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		long nomeFile = 0;
		/*
		 * while((inputLine = in.readLine()) != null) { nomeFile += inputLine; }
		 */

		in.close();

		// nomeFile = nomeFile.split("IMG SRC=")[1];
		// nomeFile = nomeFile.split(" >")[0];
		// int dim = nomeFile.split("/").length;

		// nomeFile = System.currentTimeMillis();//nomeFile.split("/")[dim-1];
		// logger.info("Mappa="+nomeFile);
		// Secondo passo: creare l'oggetto Image dal file creato su Orsa
		url = new URL(tempDirImageOrsa + nome_immagine + ".png");

		connection = url.openConnection();
		InputStream is = connection.getInputStream();

		byte[] bytes = new byte[1000000];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		is.close();

		mappa = Image.getInstance(bytes);
		mappa.scalePercent(80);

		return mappa;

	}

	public PdfPTable creaTabella(Connection con, String urlQueryReport, String tempDirImageOrsa, String tipo,
			String cartografia, String comune, String provincia, String categoria, String data_a, String data_da,
			long nome_immagine) throws Exception {
		PdfPTable tabellaA = new PdfPTable(90);
		Organization org = new Organization();
		// ResultSet rs = org.osaReportAnalisi(con, urlQueryReport, tipo,
		// provincia, comune, categoria, data_da, data_a);
		String campo1 = null;
		String campo2 = null;
		String campo3 = null;
		String campo4 = null;
		String campo5 = null;
		String campo6 = null;
		String campo7 = null;
		String campo8 = null;
		String campo9 = null;
		String campo10 = null;

		Statement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		PreparedStatement pst = null;

		if (tipo.equals("osa")) {
			String selectOSA = "SELECT OSA.* FROM (SELECT o.*, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, audit.livello_rischio_finale"
					+ " FROM organization o"
					+ " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1"
					+ " JOIN lookup_site_id asl ON o.site_id = asl.code"
					+ " LEFT JOIN audit audit ON o.org_id = audit.org_id"
					+ " WHERE oa.latitude > 0 AND oa.longitude > 0) as OSA";
			if (comune.equals("TUTTI") && provincia.equals("tutte") && categoria.equals("tutte")) {
				selectOSA += ")";
			} else {
				selectOSA += " WHERE ";
				if (!provincia.equals("tutte")) {
					selectOSA += "OSA.state = '" + provincia + "' ";
				}
				if (!comune.equals("TUTTI")) {
					if (!provincia.equals("tutte")) {
						selectOSA += " AND ";
					}
					selectOSA += " OSA.city = '" + comune + "' ";
				}
			}
			if (!categoria.equals("tutte")) {
				if ((!provincia.equals("tutte")) || (!comune.equals("TUTTI"))) {
					selectOSA += " AND ";
				}
				selectOSA += " OSA.cf_correntista in (" + categoria + ")";
			}

			sql.append(selectOSA);
			logger.info("select=" + sql.toString());
		} else if (tipo.equals("sorveglianza")) {
			String selectVigilanza = "SELECT VIGILANZA.* FROM (SELECT c.*, o.alert, o.name, o.account_number, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, esito.description as esito"
					+ " FROM ticket c"
					+ " JOIN organization o ON o.org_id = c.org_id"
					+ " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 3 OR oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1 OR oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 2"
					+ " JOIN lookup_site_id asl ON o.site_id = asl.code "
					+ " LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code"
					+ " WHERE oa.latitude > 0 AND oa.longitude > 0 AND c.tipologia = 3) as VIGILANZA";

			if (provincia.equals("tutte")) {

			} else {
				selectVigilanza += " where VIGILANZA.state = '" + provincia + "'";
			}
			sql.append(selectVigilanza);
		} else if (tipo.equals("campioni")) {
			String selectCampioni = "SELECT CAMPIONI.* FROM (SELECT c.*, o.alert, o.name, o.account_number, oa.addrline1, oa.city, oa.state, oa.country, oa.postalcode, oa.latitude, oa.longitude, oa.address_type, asl.description AS asl, esito.description as esito"
					+ " FROM ticket c JOIN organization o ON o.org_id = c.org_id"
					+ " JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 3 OR oa.org_id = o.org_id AND oa.address_type = 5 AND o.tipologia = 1 OR oa.org_id = o.org_id AND oa.address_type = 1 AND o.tipologia = 2 JOIN lookup_site_id asl ON o.site_id = asl.code"
					+ " LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code"
					+ " WHERE oa.latitude > 0 AND oa.longitude > 0 AND c.tipologia = 2) as CAMPIONI WHERE CAMPIONI.assigned_date <= '"
					+ data_a + "' and CAMPIONI.assigned_date >= '" + data_da + "'";
			sql.append(selectCampioni);
		}

		pst = con.prepareStatement(sql.toString());
		rs = pst.executeQuery();

		if (tipo.equals("osa")) {
			Paragraph p = new Paragraph("  ");
			PdfPCell cella = new PdfPCell(p);
			tabellaA.setHeaderRows(1);

			p = new Paragraph(new Phrase("Ragione Sociale", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("ASL", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Descrizione Attivita'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Indirizzo", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Citta'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Provincia", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			while (rs.next()) {
				campo1 = rs.getString("name");
				campo2 = rs.getString("asl");
				campo3 = rs.getString("alert");
				campo4 = rs.getString("addrline1");
				campo5 = rs.getString("city");
				campo6 = rs.getString("state");

				p = new Paragraph(new Phrase(campo1));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo2));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo3));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo4));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo5));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo6));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);
			}
		} else if (tipo.equals("sorveglianza")) {
			Paragraph p = new Paragraph("  ");
			PdfPCell cella = new PdfPCell(p);
			tabellaA.setHeaderRows(1);

			p = new Paragraph(new Phrase("Ragione Sociale", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Descrizione Attivita'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("ASL", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Indirizzo", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Citta'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Provincia", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			while (rs.next()) {
				campo1 = rs.getString("name");
				campo2 = rs.getString("asl");
				campo4 = rs.getString("addrline1");
				campo5 = rs.getString("city");
				campo6 = rs.getString("state");
				campo7 = rs.getString("alert");

				p = new Paragraph(new Phrase(campo1));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo7));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo2));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo4));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo5));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo6));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

			}
		} else if (tipo.equals("campioni")) {
			Paragraph p = new Paragraph("  ");
			PdfPCell cella = new PdfPCell(p);
			tabellaA.setHeaderRows(1);

			p = new Paragraph(new Phrase("Ragione Sociale", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Descrizione Attivita'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Data Prelievo", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(10);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("ASL", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(10);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Indirizzo", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Citta'", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(15);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			p = new Paragraph(new Phrase("Provincia", new Font(null, 10, Font.BOLD)));
			cella = new PdfPCell(p);
			cella.setColspan(10);
			cella.setBackgroundColor(Color.GRAY);
			cella.setBorderWidth(1);
			cella.setHorizontalAlignment(cella.ALIGN_CENTER);
			tabellaA.addCell(cella);

			while (rs.next()) {
				campo1 = rs.getString("name");
				campo3 = rs.getString("asl");
				campo2 = rs.getString("alert");
				campo4 = rs.getString("addrline1");
				campo5 = rs.getString("city");
				campo6 = rs.getString("state");
				campo7 = rs.getString("assigned_date");

				p = new Paragraph(new Phrase(campo1));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo2));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo7));
				cella = new PdfPCell(p);
				cella.setColspan(10);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo3));
				cella = new PdfPCell(p);
				cella.setColspan(10);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo4));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo5));
				cella = new PdfPCell(p);
				cella.setColspan(15);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

				p = new Paragraph(new Phrase(campo6));
				cella = new PdfPCell(p);
				cella.setColspan(10);
				cella.setBorderWidth(1);
				cella.setHorizontalAlignment(cella.ALIGN_LEFT);
				cella.setPaddingTop(5);
				cella.setPaddingBottom(5);
				cella.setPaddingLeft(10);
				tabellaA.addCell(cella);

			}
		}
		pst.close();
		rs.close();
		return tabellaA;

	}

	public PdfPTable tabellaLegenda(String richiesta, Image operatori, Image sim1, Image sim2, Image sim3, Image sim4,
			Image sim5, Image sim6, Image sim7, Image sim8) {

		PdfPTable legenda = new PdfPTable(100);

		Paragraph p = new Paragraph("  ");
		PdfPCell cella = new PdfPCell(p);
		cella.setColspan(10);
		/*
		 * cella.setBorderWidthRight(0); cella.setBorderWidthLeft(0);
		 * cella.setBorderWidthTop(0); cella.setBorderWidthBottom(0);
		 */
		cella.setBorderWidth(0);
		legenda.addCell(cella);

		p = new Paragraph(new Phrase("Legenda", new Font(null, 10, Font.BOLD)));
		cella = new PdfPCell(p);
		cella.setColspan(90);
		cella.setBorderWidth(0);
		/*
		 * cella.setBorderWidthRight(0); cella.setBorderWidthTop(0); //
		 * cella.setBorderWidthBottom(0);
		 */
		cella.setHorizontalAlignment(cella.ALIGN_CENTER);
		legenda.addCell(cella);

		if (richiesta.equals("osa")) {

			cella = new PdfPCell();
			cella.setColspan(5);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setBorderWidth(0);
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			cella = new PdfPCell(sim1);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Categoria di Rischio 1", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPadding(5);
			legenda.addCell(cella);

			cella = new PdfPCell();
			cella.setColspan(5);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			/*
			 * cella = new PdfPCell(); cella.setBackgroundColor(Color.YELLOW);
			 * cella.setColspan(5); cella.setBorderWidth(0);
			 * cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			 * legenda.addCell(cella);
			 */

			cella = new PdfPCell(sim2);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Categoria di Rischio 2", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setBorderWidth(0);
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPaddingLeft(5);
			legenda.addCell(cella);

			cella = new PdfPCell();
			cella.setColspan(5);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setBorderWidth(0);
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			/*
			 * cella = new PdfPCell(); cella.setColspan(5);
			 * cella.setBackgroundColor(Color.GRAY); cella.setBorderWidth(0);
			 * cella.setVerticalAlignment(cella.ALIGN_CENTER);
			 * cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			 * legenda.addCell(cella);
			 */

			cella = new PdfPCell(sim3);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Categoria di Rischio 3", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPaddingLeft(5);
			legenda.addCell(cella);

			cella = new PdfPCell();
			cella.setColspan(5);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setBorderWidth(0);
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			/*
			 * cella = new PdfPCell(); cella.setColspan(5);
			 * cella.setBackgroundColor(Color.GREEN); cella.setBorderWidth(0);
			 * cella.setVerticalAlignment(cella.ALIGN_CENTER);
			 * cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			 * legenda.addCell(cella);
			 */

			cella = new PdfPCell(sim4);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Categoria di Rischio 4", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPaddingLeft(5);
			legenda.addCell(cella);

			cella = new PdfPCell();
			cella.setColspan(5);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			/*
			 * cella = new PdfPCell(); cella.setColspan(5);
			 * cella.setBackgroundColor(Color.BLUE); cella.setBorderWidth(0);
			 * cella.setVerticalAlignment(cella.ALIGN_CENTER);
			 * cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			 * legenda.addCell(cella);
			 */

			cella = new PdfPCell(sim5);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Categoria di Rischio 5", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPaddingLeft(5);
			legenda.addCell(cella);

			cella = new PdfPCell();
			cella.setColspan(5);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthBottom(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			legenda.addCell(cella);

			cella = new PdfPCell(sim6);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Non Classificati", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthTop(0); cella.setBorderWidthLeft(0);
			 * cella.setBorderWidthBottom(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPaddingLeft(5);
			legenda.addCell(cella);

			p = new Paragraph("  ");
			cella = new PdfPCell(p);
			cella.setColspan(100);
			cella.setBorderWidthTop(0);
			legenda.addCell(cella);

		} else if (richiesta.equals("campioni")) {// inserito da carmela

			cella = new PdfPCell(sim7);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Campioni", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPadding(5);
			legenda.addCell(cella);

		} else {

			cella = new PdfPCell(sim8);
			cella.setColspan(10);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthRight(0); cella.setBorderWidthBottom(0);
			 * cella.setBorderWidthTop(0);
			 */
			cella.setHorizontalAlignment(cella.ALIGN_RIGHT);
			cella.setPaddingTop(5);
			cella.setPaddingBottom(5);
			cella.setPaddingLeft(10);
			legenda.addCell(cella);

			p = new Paragraph(new Phrase("Sorveglianza", new Font(null, 10, Font.NORMAL)));
			cella = new PdfPCell(p);
			cella.setColspan(90);
			cella.setBorderWidth(0);
			/*
			 * cella.setBorderWidthBottom(0); cella.setBorderWidthTop(0);
			 * cella.setBorderWidthLeft(0);
			 */
			cella.setVerticalAlignment(cella.ALIGN_TOP);
			cella.setPadding(5);
			legenda.addCell(cella);

		}
		return legenda;
	}

	public String executeCommandViewCheckList(ActionContext context) {

		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

		Connection db = null;
		String gkDriver = prefs.get("GATEKEEPER.DRIVER");
		String gkHost = prefs.get("GATEKEEPER.URL");
		String gkUser = prefs.get("GATEKEEPER.USER");
		String gkUserPw = prefs.get("GATEKEEPER.PASSWORD");

		ConnectionElement gk = new ConnectionElement(gkHost, gkUser, gkUserPw);
		gk.setDriver(gkDriver);
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");

		try {
			db = sqlDriver.getConnection(gk, context);

			context.getRequest().getSession().setAttribute("ConnectionElement", gk);
			GestoreConnessioni.setCe(gk);
			context.getRequest().getSession().setAttribute("ConnectionPool", sqlDriver);

		} catch (Exception e) {

		} finally {
			sqlDriver.free(db, context);
		}
		String imei = context.getParameter("imei");

		try {

			PreparedStatement pst = db.prepareStatement("SELECT user_id FROM access+" + super.getSuffiso(context)
					+ " a where username in (select username from blackberry_devices WHERE imei = ?) AND a.enabled ");
			pst.setString(1, imei);
			ResultSet res = pst.executeQuery();
			UserBean oldUser = (UserBean) context.getSession().getAttribute("User");

			if (res.next()) {
				int userId = res.getInt("user_id");
				User newUser = new User();// getUser( context, userId );
				UserBean ub = new UserBean();
				ub.setSessionId(context.getSession().getId());
				ub.setUserId(userId);
				ub.setActualUserId(userId);
				// ub.setConnectionElement( oldUser.getConnectionElement() );
				// ub.setClientType( context.getRequest() );
				// ub.setIdRange( newUser.getIdRange() );
				// ub.setUserRecord( newUser );

				context.getSession().setAttribute("User", ub);

			} else {
				context.getRequest().setAttribute("errore", "Utente \"" + imei + "\" non trovato");

			}
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("errore", e.getMessage());

		} finally {
			freeConnection(context, db);
		}

		return executeCommandUpdateCatRischio(context);

	}

	public String executeCommandUpdateCatRischio(ActionContext context) {

		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		String org_id = context.getRequest().getParameter("orgId");
		String idC = context.getRequest().getParameter("idC");

		String account_size = context.getRequest().getParameter("accountSize");
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			// set the name to namelastfirstmiddle if individual

			Organization newOrg = new Organization(db, Integer.parseInt(org_id));
			newOrg.setAccountSize(account_size);
			newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {
				resultCount = newOrg.update(db, context);
			}

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idControllo", idC);

		addModuleBean(context, "View Accounts", "Modify Account");

		return ("UpdateCatRischioOK");
	}

	private boolean isInWhiteList(String ip, Connection db) throws SQLException {

		String query = "select ip from whitelist_ip where ip = ?";

		PreparedStatement ps = db.prepareStatement(query);
		ps.setString(1, ip);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}

	}

	private ArrayList<String> getSharedKey(String ip, Connection db) throws SQLException {

		ArrayList<String> listSharedKey = new ArrayList<String>();
		String query = "select shared_key_suap from whitelist_ip where ip = ?";
		String sharedKey = "";
		PreparedStatement ps = db.prepareStatement(query);
		ps.setString(1, ip);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			sharedKey = rs.getString(1);
			listSharedKey.add(sharedKey);
		}
		if (sharedKey == null || "".equals(sharedKey))
			System.out.println("#SUAP#] [ACCESSO FALLITO - NON PRESENTE VALORE DI SHARED KEY  IN WHITELIST PER IP ] "
					+ ip);
		return listSharedKey;

	}

	private boolean isInBlackList(String ip, Connection db) throws SQLException {

		String query = "select ip from blacklist_ip where ip = ?";

		PreparedStatement ps = db.prepareStatement(query);
		ps.setString(1, ip);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}

	}

	private boolean isInBlackListRange(String ip, Connection db) throws SQLException, UnknownHostException {

		String query = "select ip_from, ip_to from blacklist_range_ip ";

		PreparedStatement ps = db.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			if (isIpInRange(ip, rs.getString("ip_from"), rs.getString("ip_to"))) {
				return true;
			}
		}

		return false;
	}

	private boolean isIpInRange(String ip, String ipFrom, String ipTo) throws UnknownHostException {

		InetAddress ia = InetAddress.getByName(ip);
		InetAddress iaFrom = InetAddress.getByName(ipFrom);
		InetAddress iaTo = InetAddress.getByName(ipTo);

		long ipLong = 256L * 256L * 256L * (ia.getAddress()[0] & 0xFF) + 256L * 256L * (ia.getAddress()[1] & 0xFF)
				+ 256L * (ia.getAddress()[2] & 0xFF) + (ia.getAddress()[3] & 0xFF);
		long ipFromLong = 256L * 256L * 256L * (iaFrom.getAddress()[0] & 0xFF) + 256L * 256L
				* (iaFrom.getAddress()[1] & 0xFF) + 256L * (iaFrom.getAddress()[2] & 0xFF)
				+ (iaFrom.getAddress()[3] & 0xFF);
		long ipToLong = 256L * 256L * 256L * (iaTo.getAddress()[0] & 0xFF) + 256L * 256L
				* (iaTo.getAddress()[1] & 0xFF) + 256L * (iaTo.getAddress()[2] & 0xFF) + (iaTo.getAddress()[3] & 0xFF);

		if (ipFromLong <= ipLong && ipLong <= ipToLong) {
			return true;
		} else {
			return false;
		}
	}

}
