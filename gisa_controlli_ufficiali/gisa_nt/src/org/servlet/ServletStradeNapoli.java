/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;


public class ServletStradeNapoli extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletStradeNapoli() {
		super();
		// TODO Auto-generated constructor stub
	}

 

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Connection db = null;
		String esito = "";
        String tipoOutput = request.getParameter("tipoOutput");
        System.out.println("tipo"+tipoOutput);

		try {
			db = GestoreConnessioni.getConnection();
			if (db != null) { 
				
   			    pst = db.prepareStatement("select * from get_strade_napoli(?)");
   			    pst.setString(1, tipoOutput);
   			    
				rs = pst.executeQuery();

				while (rs.next()) {
					esito = rs.getString(1);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {

			throw e;
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		
		response.getWriter().println(esito);
	}
}