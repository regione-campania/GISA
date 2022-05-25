/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets.query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SessionManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.UserSession;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.OperazioniMonitoraggioUtenti;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class MonitoraggioUtentiServlet
 */
public class MonitoraggioUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String operazione;
	private HashMap<Integer, String> hashAsl;
	private TreeMap<Integer, ArrayList<User>> listaUtentiPerAsl;
       
    public MonitoraggioUtentiServlet() {
        super();
        hashAsl = new HashMap<Integer, String>();
        hashAsl.put(-1, "Nessuna ASL");
        hashAsl.put(201, "AV");
        hashAsl.put(202, "BN");
        hashAsl.put(203, "CE");
        hashAsl.put(204, "NA1C");
        hashAsl.put(205, "NA2N");
        hashAsl.put(206, "NA3S");
        hashAsl.put(207, "SA");
    }

protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
			
			ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		    ce.setDriver(ceDriver);
			
		    SystemStatus thisSystem = null;
		    SessionManager sessionManager = null;
		    HashMap sessions = null;
		    
			thisSystem = (SystemStatus) ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
			if(thisSystem != null){
				sessionManager = thisSystem.getSessionManager();
			}
			if(sessionManager != null){
				sessions = sessionManager.getSessions();
			}
			
			operazione = request.getParameter("op");
			OperazioniMonitoraggioUtenti omu = OperazioniMonitoraggioUtenti.valueOf(operazione);
			String rispostaCSV = "";
		
			switch (omu) {
			case TotaleUtenti:
				
				if(sessions != null){
					rispostaCSV = "1||" + sessions.keySet().size() +"||";
				}
				else{
					rispostaCSV = "1||0||";
				}
				break;
				
			case TotaleUtentiPerAsl:
				listaUtentiPerAsl = new TreeMap<Integer, ArrayList<User>>();
				listaUtentiPerAsl.put(201, new ArrayList<User>());
				listaUtentiPerAsl.put(202, new ArrayList<User>());
				listaUtentiPerAsl.put(203, new ArrayList<User>());
				listaUtentiPerAsl.put(204, new ArrayList<User>());
				listaUtentiPerAsl.put(205, new ArrayList<User>());
				listaUtentiPerAsl.put(206, new ArrayList<User>());
				listaUtentiPerAsl.put(207, new ArrayList<User>());

				User u = null;
				
				if(sessions != null){
					for(Object o : sessions.keySet()){
						u = thisSystem.getUser(Integer.parseInt(o.toString()));
						if(u.getSiteId() != 0 && u.getSiteId() != -1 && u.getSiteId() != 16 ){
							listaUtentiPerAsl.get(u.getSiteId()).add(u);
						}
					}
				}
				rispostaCSV = listaUtentiPerAsl.size()+ "||";
				
				for(int asl : listaUtentiPerAsl.keySet()){
					rispostaCSV = rispostaCSV + hashAsl.get(asl) + "|" + listaUtentiPerAsl.get(asl).size() + "||";
				}
				
				break;
			default:
				break;
			}
		
		
			request.setAttribute("rispostaCSV", rispostaCSV);
			RequestDispatcher rd = request.getRequestDispatcher("risultatoQuery.jsp");
			rd.forward(request, response);
			
		}
		catch(Exception e){
			System.out.println("Errore durante l'invocazione di MonitoraggioUtentiServlet.");
			e.printStackTrace();
		}
		
		
	}

}
