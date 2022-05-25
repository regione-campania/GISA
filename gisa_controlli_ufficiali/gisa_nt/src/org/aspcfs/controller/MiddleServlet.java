/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;




import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.database.ConnectionElement;
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
		
		//Gestione del logging
		
		
		Logger logger = Logger.getLogger(MiddleServlet.class);
		if(ApplicationProperties.getAmbiente()==null){
			ApplicationProperties.setAmbiente(request.getServerName().toString());
		}
		
		ApplicationProperties.getProperty("livelloLOG");
		try{
			logger.setLevel( Level.toLevel(( ApplicationProperties.getProperty("livelloLOG").toUpperCase() ) ));
		}
		catch(Exception e){
			logger.warn("Errore nella configurazione del livello di LOG - Il livello sara' settato a WARNING");
			//logger.setLevel(Level.WARNING);
		}

		try {
			request.setCharacterEncoding("UTF-8");
		} 
		catch (UnsupportedEncodingException uee) {
			logger.error("Character Encoding non supportato.");
			uee.printStackTrace();
		}
		
		String servletPath = request.getServletPath();

		final String forward = servletPath.replace(".do", ".doController");
		String forwardLock = "";
		
		if( ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null 
			&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") 
		&& (request.getServletContext().getAttribute("ambiente")==null ||  !request.getServletContext().getAttribute("ambiente").equals("SLAVE")) ){
		
			UserBean user = null;
			ConnectionPool cp = null;
			Connection db = null;
			PreparedStatement ps = null;
			
			//Inserisci qui le operazioni prima di passare il controllo a Centric
			try{
				if(request.getSession().getAttribute("User") != null){
					user = (UserBean) request.getSession().getAttribute("User");
					if(user.getUserRecord() != null){
						cp = (ConnectionPool)request.getSession().getServletContext().getAttribute("ConnectionPool");
						ConnectionElement ce = (ConnectionElement)request.getSession().getAttribute("ConnectionElement");
						
						db = cp.getConnection(ce,null);
						int userId = user.getUserId();
						
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
					        
//					        if (element != null && value != null) {
//					            logger.info("param Name : " + element + " value: " + value);
//					        }
					    }   
					    
						String userIp = user.getUserRecord().getIp();
						String parameters = mapParameters.toString();
						String userAction = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
						String userCommand = request.getParameter("command");
						
						Boolean esito = false;
						String sqlAction = "select * from lock_action where upper(name_action) = ? and enabled";
						PreparedStatement pstAct = db.prepareStatement(sqlAction);
						pstAct.setString(1, userAction.toUpperCase().trim());
						
						ResultSet rsAct = pstAct.executeQuery();
						if(rsAct.next())
						{
							esito=true;
						}
						
						
						if(userCommand == null || userCommand.equals("")){
							userCommand = "Default";
						} 
						String orgId = request.getParameter("orgId");
					 	int objectId = -1;
						String tableName = "";
				 		 
						if(orgId != null && !orgId.equals("") && !orgId.equals("null")){				
							objectId = Integer.parseInt(orgId);
							tableName = "Organization"; 
		 				}
						 
						String param = "" ;
						if (request.getQueryString() != null && ! "".equals(request.getQueryString()) && request.getQueryString().length()>= request.getQueryString().indexOf(userCommand)+userCommand.length())
								param = request.getQueryString().substring(request.getQueryString().indexOf(userCommand)+userCommand.length());
						logger.info("[User: "+user.getUsername()+ "] [Action: "+userAction+"] [Command: "+userCommand+ "] [Params: "+param+"]");
						
						if (esito)
						{
								/*String sqlSelectOut = "select extract(EPOCH FROM lock_date) * 1000 as lock_date from  lock_state where upper(tipo)='RESTART' and   upper(direction)='OUT'";
								String sqlUpdateIn = "update lock_state set  lock_date=current_timestamp  where upper(tipo)='RESTART' and  upper(direction)='IN'";
								long now = Instant.now().toEpochMilli();
								long nowSrv = 0L;
								long threshold = Long.parseLong(ApplicationProperties.getProperty("threshold")); //secondi
								String testoAlert =  ApplicationProperties.getProperty("messageAlert");
								
								logger.info("thresold lock: "+threshold);
//								System.out.println("now Action:"+now);
								Statement pst = db.createStatement();
								ResultSet rs = pst.executeQuery(sqlSelectOut);
								
								if (rs.next())
								{
									nowSrv = rs.getLong("lock_date");
								}
											
								
								long diff =(now - nowSrv)/1000;
		
								if (diff < threshold)
								{
									logger.info("Attenzione!! Il sistema si sta riavviando riprova tra poco");
									request.setAttribute("alertMsg", testoAlert);
									forwardLock = "errors/error_lock_action.jsp";

								}
								else
								{
								
									 pst.executeUpdate(sqlUpdateIn);
								}
		
		
								pst.close();
							*/
						}	          	
						String suff = "";
						if (request.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")!=null){
							suff=(String)request.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
						}
						
						//Update di access
						if( ApplicationProperties.getProperty("abilitaUpdateAccess") != null 
	 							&& ApplicationProperties.getProperty("abilitaUpdateAccess").equalsIgnoreCase("si") ){
						
							String query = "UPDATE access"+suff+" SET last_ip = '"+userIp+"' , " +
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
						thisSystem =  ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
						if(thisSystem != null){
							sessionManager = ((SystemStatus) thisSystem).getSessionManager();
						}
						if(sessionManager != null){
							sessions = sessionManager.getSessions();
						}
						if (sessions !=null && sessions.get(userLoggato.getUserId()) != null)
						{
						UserSession thisUser= (UserSession) sessions.get(userLoggato.getUserId());
						thisUser.setLastOperation((userAction + "->"+userCommand).toUpperCase() );
						sessions.put(userLoggato.getUserId(), thisUser);
						sessionManager.setSessions(sessions);
						}
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
			} finally{
				if(cp!=null && db!=null){
					cp.free(db,null);
				}
			}
		} else {
			logger.info("[DB LOG OPERAZIONI UTENTE ed ACCESS] - info non aggiornate\nTIPS : Ambiente=SLAVE(REPLICA) oppure verifica parametro 'abilitaStoricoOperazioniUtente' nel properties");
		}
		
		try{
			
			
			RequestDispatcher rd = request.getRequestDispatcher(forwardLock!="" ?  forwardLock : forward);
			rd.forward(request, response);
		}
		catch(Exception e){
			logger.error("Errore di forwarding nella MiddleServlet: " + forward);
			e.printStackTrace();
		}
		
		
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
						op.get(i).insert(db, automatico, suff);
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
	
	public static void writeLoginFault(String username, String ip, String error, ActionContext context){
		if( ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente") != null 
				&& ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
			Connection conn = null;
			try {
				conn = GestoreConnessioni .getConnectionStorico(context, null);
				String suff = "";
				if (context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")!=null){
					suff=(String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
				}
				if (conn!=null){
					PreparedStatement pst = conn.prepareStatement("insert into login_fallite (endpoint,ip,username,data,error_message) values ('GISA"+suff+"',?,?,now(),?);");
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
