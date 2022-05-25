/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.aspcfs.modules.login.beans.UserBean;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class GestoreConnessioni {
	
	static Logger logger = Logger.getLogger("MainLogger");
	
	private static ConnectionPool cp = null;
	private static ConnectionPool cpStorico = null;
	private static ConnectionPool cpBdu = null;
	private static ConnectionPool cpGuc= null;
	private static ConnectionPool cpVam = null;


	private static ConnectionElement ce = null;
	
	
	
	public static ConnectionPool getCpBdu() {
		return cpBdu;
	}
	public static void setCpBdu(ConnectionPool cpBdu) {
		GestoreConnessioni.cpBdu = cpBdu;
	}
	public static ConnectionPool getCpGuc() {
		return cpGuc;
	}
	public static void setCpGuc(ConnectionPool cpGuc) {
		GestoreConnessioni.cpGuc = cpGuc;
	}
	public static ConnectionPool getCp() {
		return cp;
	}
	public static void setCp(ConnectionPool cp) {
		GestoreConnessioni.cp = cp;
		logger.info("Settato il CP");
	}
	
	public static ConnectionPool getCpStorico() {
		return cpStorico;
	}
	public static void setCpStorico(ConnectionPool cpStorico) {
		GestoreConnessioni.cpStorico = cpStorico;
	}
	
	public static ConnectionElement getCe() {
		return ce;
	}
	public static void setCe(ConnectionElement ce) {
		GestoreConnessioni.ce = ce;
		logger.info("Settato il CE");
	}
	
	
	
	
	
	public static ConnectionPool getCpVam() {
		return cpVam;
	}
	public static void setCpVam(ConnectionPool cpVam) {
		GestoreConnessioni.cpVam = cpVam;
	}
	public static Connection getConnection() throws SQLException,LoginRequiredException  {	
	
		
	
			return cp.getConnection();
	}
	
	public static void freeConnection(Connection db){
		cp.free(db,null);
}
	
	
	
	
//	public static Connection getConnection(ActionContext context, ConnectionElement ce) throws LoginRequiredException, SQLException {
//	    ConnectionPool sqlDriver = null;
//	    String browser = "";
//	    try
//	    {
//	    UserBean u = (UserBean)context.getSession().getAttribute("User");
//	    if (u.getBrowserId().equalsIgnoreCase("moz"))
//	    	browser = "Firefox";
//	    
//	    if (browser.contains("Firefox")){
//	    	sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
//	    } else {
//	    	sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPoolRO");
//	    }
//	    Connection db = sqlDriver.getConnection(ce,null);
//	    
//	    return db;
//	}catch(IllegalStateException e)
//	    {
//	    	
//	    	throw e;
//	    }
//	   
//	  }
	
	public static Connection getConnectionStorico(ActionContext context, ConnectionElement ce) throws SQLException {
	    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
	        "ConnectionPoolStorico");
	    Connection db = sqlDriver.getConnection(ce,null);
	    return db;
	  }
	
	public static Connection getConnectionBdu(ActionContext context, ConnectionElement ce) throws SQLException {
	    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
	        "ConnectionPoolBdu");
	    Connection db = sqlDriver.getConnection(ce,null);
	    return db;
	  }

	public static Connection getConnectionGuc(ActionContext context, ConnectionElement ce) throws SQLException {
	    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
	        "ConnectionPoolGuc");
	    Connection db = sqlDriver.getConnection(ce,null);
	    return db;
	  }

	public static Connection getConnectionVam(ActionContext context, ConnectionElement ce) throws SQLException {
	    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute(
	        "ConnectionPoolVam");
	    Connection db = sqlDriver.getConnection(ce,null);
	    return db;
	  }
	
	
	
	public static Connection getConnectionStorico() throws SQLException {
		
		try
		{
	   
	    return cpStorico.getConnection();
		}
		catch(IllegalStateException e )
		{  
			logger.severe(""+e.getMessage());
		}
		
			
			return null ;
	
}
	
	public static void freeConnectionStorico(Connection db){
		
		
			try
			{
				cpStorico.free(db);
			}
			catch(IllegalStateException e )
			{  
				logger.severe("La sessione e stata Invalidata.");
			}
	
		
	}
	
	
	

/**
 * RECUPERO CONNESSIONE PER  GUC	
 * @param db
 */
	
	public static Connection getConnectionGuc() throws SQLException {
			try
			{
		    
		    return cpGuc.getConnection();
			}
			catch(IllegalStateException e )
			{  
				logger.severe(""+e.getMessage());
			}
			
				return null ;
	
	}
	

public static void freeConnectionGuc(Connection db)
{
			try
			{
				
		     cpGuc.free(db);
			}
			catch(IllegalStateException e )
			{  
				logger.severe("La sessione e stata Invalidata.");
			}
		
	}
	
	
	
/**
 * RECUPERO CONNESSIONE PER  BDU	
 * @param db
 */
public static void freeConnectionBdu(Connection db){
		
		
		
			try
			{
				
		     cpBdu.free(db);
			}
			catch(IllegalStateException e )
			{  
				logger.severe("La sessione e stata Invalidata.");
			}
			
		
		
	}
	
	public static Connection getConnectionBdu() throws SQLException {
		
			try
			{
				return cpBdu.getConnection();
			}
			catch(IllegalStateException e )
			{  
				logger.severe(""+e.getMessage());
			}
			
				
				return null ;
	}
	

	
/**
 * RECUPERO CONNESSIONE PER  VAM	
 * @param db
 */
public static void freeConnectionVam(Connection db){
		
		
		
			try
			{
				
		     cpVam.free(db);
			}
			catch(IllegalStateException e )
			{  
				logger.severe("La sessione e stata Invalidata.");
			}
			
		
		
	}
	
	public static Connection getConnectionVam() throws SQLException {
		
			try
			{
				return cpVam.getConnection();
			}
			catch(IllegalStateException e )
			{  
				logger.severe(""+e.getMessage());
			}
			
				
				return null ;
	}
}
