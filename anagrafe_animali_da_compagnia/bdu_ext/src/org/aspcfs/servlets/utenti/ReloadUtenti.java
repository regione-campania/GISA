/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets.utenti;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.SystemStatus;

import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ReloadUtenti
 */
public class ReloadUtenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
	     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReloadUtenti() {
    
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ConnectionElement ce = null;
		SystemStatus systemStatus = null;
		Connection db = null;
		ConnectionPool sqlDriver =  null ;
		boolean esito = false ;

		try {
			String username = request.getParameter("username");	

//			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
//			String ceHost = prefs.get("GATEKEEPER.URL");
//			String ceUser = prefs.get("GATEKEEPER.USER");
//			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
			
			//ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			ConnectionPool ce = (ConnectionPool) request.getSession().getServletContext().getAttribute("ConnectionPool");
			Object o = ((Hashtable) request.getSession().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
			
			if(o != null){
				systemStatus = (SystemStatus) o;
				sqlDriver = (ConnectionPool) getServletContext().getAttribute("ConnectionPool");
				db = sqlDriver.getConnection(null);
				if (username != null)
					esito =systemStatus.buildHierarchyListbyUserId(db,username);
				else
				{
					 systemStatus.buildHierarchyList(db);
					 systemStatus.buildRolePermissions(db);
					 esito = true ; 	 
				}
			}
			if (esito==true)
				response.getWriter().write("OK");
			else
				response.getWriter().write("KO : USERNAME NON PRESENTE");
		} 
		catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("KO");
		}
		finally{
		
			if(sqlDriver!=null)
				sqlDriver.free(db, null);
			}

	}

}
