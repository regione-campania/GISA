/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SessionManager;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.UserSession;

import com.darkhorseventures.database.ConnectionElement;

/**
 * Servlet implementation class InvalidateSessionUser
 */
@WebServlet("/InvalidateSessionUser")
public class InvalidateSessionUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvalidateSessionUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
	
		
		
		
		

ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
String ceDriver = prefs.get("GATEKEEPER.DRIVER");
String ceHost = prefs.get("GATEKEEPER.URL");
String ceUser = prefs.get("GATEKEEPER.USER");
String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
SystemStatus thisSystem = null; 
SessionManager sessionManager = null;
HashMap sessions = null;
thisSystem = (SystemStatus) ((Hashtable) context.getAttribute("SystemStatus")).get(ce.getUrl());
if(thisSystem != null){
	sessionManager = thisSystem.getSessionManager();
	
}
if(sessionManager != null){
	sessions = sessionManager.getSessions();
}

UserSession s = null;
 
int userId = Integer.parseInt(request.getParameter("userId"));

if(sessions != null && sessions.size() > 0){
	
	try{
		for(Object o : sessions.keySet()){
			s = (UserSession)sessions.get(Integer.parseInt(o.toString()));
			if ( s.getUserId() ==  userId){
				
				System.out.println(s.getId());
				
				HttpSession sessione = (HttpSession) context.getAttribute(s.getId());
				sessione.invalidate();
				
			}
		}
	}
	catch(Exception e){
		e.printStackTrace();
		
	}
	
}

response.sendRedirect("checkUtenti.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
