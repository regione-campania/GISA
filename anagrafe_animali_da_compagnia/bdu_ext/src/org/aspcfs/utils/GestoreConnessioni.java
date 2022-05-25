/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class GestoreConnessioni {

	static Logger logger = Logger.getLogger("MainLogger");
	private static ConnectionPool cp = null;
	private static ConnectionPool cpVam = null;
	private static ConnectionPool cpImportatori = null;
	private static ConnectionPool cpGisa = null;
	private static ConnectionPool cpStorico = null;
	private static ConnectionPool cpBdu = null;

	// private static ConnectionElement ce = null;
	private static ActionContext context;
	
	public static ConnectionPool getCp() {
		return cp;
	}

	public static void setCp(ConnectionPool cp) {
		GestoreConnessioni.cp = cp;
		logger.info("Settato il CP PRINCIPALE");
	}

	public static ConnectionPool getCpBdu() {
		return cpBdu;
	}

	public static void setCpBdu(ConnectionPool cp) {
		GestoreConnessioni.cpBdu = cp;
		logger.info("Settato il CP BDU");
	}

	public static ConnectionPool getCpVam() {
		return cp;
	}

	public static void setCpVam(ConnectionPool cp) {
		GestoreConnessioni.cpVam = cp;
		logger.info("Settato il CP VAM");
	}

	public static ConnectionPool getCpImportatori() {
		return cpImportatori;
	}

	public static void setCpImportatori(ConnectionPool cpImportatori) {
		GestoreConnessioni.cpImportatori = cpImportatori;
		logger.info("Settato il CP IMPORTATORI");
	}
	
	public static ConnectionPool getCpGisa() {
		return cpGisa;
	}

	public static void setCpGisa(ConnectionPool cpGisa) {
		GestoreConnessioni.cpGisa = cpGisa;
		logger.info("Settato il CP GISA");
	}

	public static ConnectionPool getCpStorico() {
		return cpStorico;
	}

	public static void setCpStorico(ConnectionPool cpStorico) {
		GestoreConnessioni.cpStorico = cpStorico;
		logger.info("Settato il CP STORICO");
	}

	public static ActionContext getContext() {
		return context;
	}

	public static void setContext(ActionContext context) {
		GestoreConnessioni.context = context;
	}
	
	public static Connection getConnection() throws SQLException {
		return getConnection(context);
	}

	public static Connection getConnection(ActionContext context)
			throws SQLException {
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext()
				.getAttribute("ConnectionPool");
		Connection db = sqlDriver.getConnection(context);
		// logger.info("[PRELEVATA CONNESSIONE CUSTOM] - " + "{" +
		// context.getRequest().getServletPath() + ".executeCommand" +
		// context.getRequest().getParameter("command") + "} " + " " +
		// sqlDriver);
		return db;
	}

	public static Connection getConnectionBdu() throws SQLException {
		return getConnectionBdu(context);
	}

	public static Connection getConnectionBdu(ActionContext context)
			throws SQLException {
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext()
				.getAttribute("ConnectionPoolBdu");
		Connection db = sqlDriver.getConnection(context);
		// logger.info("[PRELEVATA CONNESSIONE CUSTOM] - " + "{" +
		// context.getRequest().getServletPath() + ".executeCommand" +
		// context.getRequest().getParameter("command") + "} " + " " +
		// sqlDriver);
		return db;
	}

	public static Connection getConnectionVam(String nomeClasse) throws SQLException {
		// ConnectionElement ce = (ConnectionElement)
		// context.getSession().getAttribute(
		// "ConnectionElement");
		return getConnectionVam(context);
	}

	public static Connection getConnectionVam(ActionContext context)
			throws SQLException {
		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext()
				.getAttribute("ConnectionPoolVam");
		Connection db = sqlDriver.getConnection(context);
		// logger.info("[PRELEVATA CONNESSIONE CUSTOM] - " + "{" +
		// context.getRequest().getServletPath() + ".executeCommand" +
		// context.getRequest().getParameter("command") + "} " + " " +
		// sqlDriver);
		return db;
	}
	
	public static Connection getConnectionImportatori() throws SQLException {
		// ConnectionElement ce = (ConnectionElement)
		// context.getSession().getAttribute(
		// "ConnectionElement");
		return getConnectionImportatori(context);
	}
	
	public static Connection getConnectionImportatori(ActionContext context)
			throws SQLException {
//		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext()
//				.getAttribute("ConnectionPoolVam");
		Connection db = cpImportatori.getConnection(context);
		// logger.info("[PRELEVATA CONNESSIONE CUSTOM] - " + "{" +
		// context.getRequest().getServletPath() + ".executeCommand" +
		// context.getRequest().getParameter("command") + "} " + " " +
		// sqlDriver);
		return db;
	}
	
	public static Connection getConnectionGisa() throws SQLException {
		// ConnectionElement ce = (ConnectionElement)
		// context.getSession().getAttribute(
		// "ConnectionElement");
		return getConnectionGisa(context);
	}
	
	public static Connection getConnectionGisa(ActionContext context)
			throws SQLException {
//		ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext()
//				.getAttribute("ConnectionPoolVam");
		Connection db = cpGisa.getConnection(context);
		// logger.info("[PRELEVATA CONNESSIONE CUSTOM] - " + "{" +
		// context.getRequest().getServletPath() + ".executeCommand" +
		// context.getRequest().getParameter("command") + "} " + " " +
		// sqlDriver);
		return db;
	}

	public static Connection getConnectionStorico() throws SQLException {
		return getConnectionStorico(context);
	}
	
	public static Connection getConnectionStorico(ActionContext context)
			throws SQLException {
		Connection db = cpStorico.getConnection(context);
		return db;
	}
	
	public static void freeConnection(Connection db) {

		cp.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}

	public static void freeConnectionBdu(Connection db) {

		cpBdu.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}

	public static void freeConnectionVam(Connection db) {

		cpVam.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}
	
	public static void freeConnectionImportatori(Connection db) {

		cpImportatori.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}
	
	public static void freeConnectionGisa(Connection db) {

		cpGisa.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}

	public static void freeConnectionStorico(Connection db) {

		cpStorico.free(db, context);
		// logger.info("CHIUSURA CONNESSIONE MEDIANTE GESTORE - CP: " + cp);

	}
	
	
	public static Connection getConnectionNotLogged(ServletContext cxt) {

		Connection con = null;
		try {
			InitialContext ss = new InitialContext();
			ApplicationPropertiesStart properties = new ApplicationPropertiesStart("starting_"
					+ cxt.getInitParameter("context_starting") + ".properties");
			ConnectionPool cpMain = new ConnectionPool(properties.getProperty("MainPool"));
			
			DataSource datasource = (DataSource) ss.lookup(properties.getProperty("MainPool"));
			con =  datasource.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return con;
	}
	
	
	public static void closeConnectionNotLogged(Connection con) {

	//	Connection con = null;
		try {
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
