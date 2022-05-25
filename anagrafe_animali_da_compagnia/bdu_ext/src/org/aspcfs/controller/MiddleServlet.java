/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;


/**
 * Servlet implementation class MiddleServlet
 */
public class MiddleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MiddleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (System.getProperty("DEBUG") != null) 
			System.out.println("In middleservlet");
		//Gestione del logging
		Logger logger = Logger.getLogger("MainLogger");
		
		if(ApplicationProperties.getAmbiente()==null){
			ApplicationProperties.setAmbiente(request.getServerName().toString());
		}
		
		
		ApplicationProperties.getProperty("livelloLOG");
		try{
			logger.setLevel( Level.parse( ApplicationProperties.getProperty("livelloLOG").toUpperCase() ) );
		}
		catch(Exception e){
			logger.setLevel(Level.WARNING);
		}
		
		//Gestione dell'encoding
		try {
			request.setCharacterEncoding("UTF-8");
		} 
		catch (UnsupportedEncodingException uee) {
			logger.severe("Character Encoding non supportato.");
			uee.printStackTrace();
		}
		
	//	logger.info("Invocata la MiddleServlet");
		String servletPath = request.getServletPath();
		final String forward = servletPath.replace(".do", ".doController");
		
		if( ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null 
			&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") 
			//&& !request.getServletContext().getAttribute("ambiente").equals("SLAVE")
			){
		
			Connection db = null;
			UserBean user = null;
			try{
				if(request.getSession().getAttribute("User") != null){
					user = (UserBean) request.getSession().getAttribute("User");
					if(user.getUserRecord() != null){
					//	logger.info("Utente loggato");
						
						//RECUPERO TUTTI I PARAMETRI 
						Enumeration requestParameters = ((HttpServletRequest)request).getParameterNames();
						HashMap<String, String> mapParameters = new HashMap<String, String>();
					    while (requestParameters.hasMoreElements()) {
					    	String value = null;
					        String element = (String) requestParameters.nextElement();
					        if (element != null && !("password").equals(element)){
					        	value = request.getParameter(element);
					        	mapParameters.put(element, value);
					        }
					        
					        if (element != null && value != null) {
					          //  logger.info("param Name : " + element + " value: " + value);
					        }
					    }
					    
					    
					    int userId = user.getUserId();
						String userIp = user.getUserRecord().getIp();
						String parameters = mapParameters.toString();
						String userAction = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
						String userCommand = request.getParameter("command");
						if(userCommand == null || userCommand.equals("")){
							userCommand = "Default";
						}
						String idToOperateOn = null;
						if (request.getParameter("animaleId") != null)
							idToOperateOn = request.getParameter("animaleId");
						else if (request.getParameter("idAnimale") != null)
							idToOperateOn = request.getParameter("idAnimale");
						else if (request.getParameter("opId") != null)
							idToOperateOn = request.getParameter("opId");
								
						int objectId = -1;
						String tableName = "";
						if (idToOperateOn != null){
							objectId = Integer.parseInt(idToOperateOn);
							if(request.getParameter("animaleId") != null && !("").equals(request.getParameter("animaleId")) )
								tableName = "Animale";
							else if (request.getParameter("opId") != null && !("").equals(request.getParameter("opId")))
									tableName = "Operatore";
						}
						
						//Update di access
						if( ApplicationProperties.getProperty("abilitaUpdateAccess") != null 
								&& ApplicationProperties.getProperty("abilitaUpdateAccess").equalsIgnoreCase("si") ){
						
							String query = "UPDATE access SET last_ip = '"+userIp+"' , " +
									" last_interaction_time = now() ," +
									" action = '"+userAction+"' , " +
									" command = '"+userCommand+"' , " +
									" object_id = "+objectId+" , " +
									" table_name = '"+tableName+"' WHERE user_id = "+userId+" and trashed_date is null";
															
							if (request.getSession().getAttribute("AccessUpdate")!=null){
								request.getSession().removeAttribute("AccessUpdate");
								request.getSession().setAttribute("AccessUpdate", query);
							} else {
								request.getSession().setAttribute("AccessUpdate", query);
							}
						}
								
						//NUOVO STORICO OPERAZIONI UTENTE						
						ArrayList<UserOperation> user_operazioni = (ArrayList<UserOperation>) request.getSession().getAttribute("operazioni");			
						UserOperation uo = new UserOperation();
						uo.setUser_id(user.getUserId());
						uo.setUsername(user.getUsername());
						uo.setIp(user.getUserRecord().getIp());
						uo.setData(new Timestamp(new Date().getTime()));
						uo.setUrl(request.getRequestURL().toString()+(request.getQueryString()!=null ? "?"+request.getQueryString() : ""));
						uo.setParameter(parameters);
						uo.setUserBrowser(request.getHeader("user-agent"));
						uo.setUser_id_session((Integer)request.getSession().getAttribute("UserID"));
					
						if (!userCommand.equalsIgnoreCase("Logout")){ 							
							if (user_operazioni!=null){
								user_operazioni.add(uo);
							} else {
								user_operazioni = new ArrayList<UserOperation>();
								user_operazioni.add(uo);
							}
							request.getSession().setAttribute("operazioni", user_operazioni);
						} else {
							if (user_operazioni==null) {
								user_operazioni = new ArrayList<UserOperation>();
							}
							user_operazioni.add(uo);
							request.getSession().setAttribute("operazioni", user_operazioni);
						}			
						
						UserBean userLoggato = (UserBean) request.getSession().getAttribute("User");
						Object thisSystem = null; 
						SessionManager sessionManager = null;
						HashMap  sessions = null;
						thisSystem =  ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(userLoggato.getConnectionPoll().getUrl());
						if(thisSystem != null){
							sessionManager = ((SystemStatus) thisSystem).getSessionManager();							
						}
						if(sessionManager != null){
							sessions = sessionManager.getSessions();
						}
						UserSession thisUser= (UserSession) sessions.get(userLoggato.getUserId());
						thisUser.setLastOperation((userAction + "->"+userCommand).toUpperCase() );
						sessions.put(userLoggato.getUserId(), thisUser);
						sessionManager.setSessions(sessions);	
					}
					else{
						logger.info("UserRecord null");
					}
				}
				else{
					logger.info("User null");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				if(db!=null){
					GestoreConnessioni.freeConnectionStorico(db);
				}
			} 
		} else {
			logger.info("[DB LOG OPERAZIONI UTENTE ed ACCESS] - info non aggiornate\nTIPS : Ambiente=SLAVE(REPLICA) oppure verifica parametro 'abilitaStoricoOperazioniUtente' nel properties");
			}		
	
		try{
			logger.info("[MIDDLE SERVLET] - Forwarding:  " + forward);
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
		}
		catch(Exception e){
			logger.severe("Errore di forwarding nella MiddleServlet: " + forward);
			e.printStackTrace();
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (System.getProperty("DEBUG") != null) 
			System.out.println("In middleservlet");
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (System.getProperty("DEBUG") != null) 
			System.out.println("In middleservlet");
		
	}
	
	
	public static void writeStorico(ArrayList<UserOperation> op, String queryAccessUpdate, Boolean automatico, String suff){
		Connection db = null;
		//UPDATE DI ACCESS
		
		if (queryAccessUpdate!=null && !queryAccessUpdate.equals("")){
			try {
				db = GestoreConnessioni.getConnection();
				if (db!=null){
					if (queryAccessUpdate!=null){
						PreparedStatement pst = db.prepareStatement(queryAccessUpdate);
						pst.executeUpdate();
						pst.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				if(db!=null){
					GestoreConnessioni.freeConnection(db);
				}
			}
		}
		
		//OPERAZIONI UTENTE
		if (op!=null && op.size()>0){
			try {
				db = GestoreConnessioni.getConnectionStorico();
				if (db!=null){
					for (int i=0; i<op.size();i++){
						op.get(i).insert(db, automatico,suff);
					}
				}
			} catch (Exception e) {
				System.out.println("Errore nella scrittura sul db storico. I dati sono stati inseriti sul db locale");
				Connection conn = null;
				try {
					conn = GestoreConnessioni.getConnection();
					for (int i=0; i<op.size();i++){
						op.get(i).insert(conn, automatico, suff);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (conn!=null){
						GestoreConnessioni.freeConnection(conn);
					}
				} 
				
			} finally {
				if(db!=null){
					GestoreConnessioni.freeConnectionStorico(db);
				}
			} 
		}
	}
	
	public static void writeStorico(ArrayList<UserOperation> op, String queryAccessUpdate, Boolean automatico){}
	
	public static void writeLoginFault(String username, String ip, String error, ActionContext context){
		if( ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null 
				&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
			Connection conn = null;
			try {
				conn = GestoreConnessioni.getConnectionStorico(context);
				String suff = "";
				if (context.getServletContext().getInitParameter("context_starting")!=null){
					suff=(String)context.getServletContext().getInitParameter("context_starting");
				}
				if (conn!=null){
					PreparedStatement pst = conn.prepareStatement("insert into login_fallite (endpoint,ip,username,data,error_message) values ('"+suff+"',?,?,now(),?);");
					pst.setString(1, ip);
					pst.setString(2, username);
					pst.setString(3, error);
					pst.executeUpdate();
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(conn!=null ){
					GestoreConnessioni.freeConnectionStorico(conn);
				}
			} 
		}
	}
}
