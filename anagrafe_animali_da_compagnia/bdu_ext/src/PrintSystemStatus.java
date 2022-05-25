/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/


import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.SystemStatus;


import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class PrintSystemStatus
 */
public class PrintSystemStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintSystemStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		SystemStatus system = null;
		
		 ConnectionPool ce = (ConnectionPool) request.getSession().getAttribute(
	        "ConnectionPool");
		 
		 
		 if (ce != null) {
			 system = (SystemStatus) ((Hashtable) request.getSession().getAttribute("SystemStatus")).get(ce.getUrl());
		    } else {
		      if (System.getProperty("DEBUG") != null) {
		        System.out.println("CFSModule-> ** System status is null **");
		      }
		    }
		      
		      
		HashMap map =  system.getProperties();
		
		Iterator it= map.keySet().iterator();
		while (it.hasNext())
		{
			System.out.println("System Status -> "+it.next());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
