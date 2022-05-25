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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class QueryProcessorServlet
 */
public class QueryProcessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryProcessorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("Invocata la QueryProcessorServlet");

		ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");
		
		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
	    ce.setDriver(ceDriver);
		
		ConnectionPool cp = (ConnectionPool) request.getSession().getServletContext().getAttribute("ConnectionPool");
		
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		String rispostaCSV = "";
		String colonna = "";
		
		//System.out.println("cp: " + cp);
		//System.out.println("ce: " + ce);
		
		try{
			db = cp.getConnection(ce,null);
			query = request.getParameter("queryPHP");
			//System.out.println("QUERY: " + query);
			ps = db.prepareStatement(query);
			rs = ps.executeQuery();
			int numColonne = rs.getMetaData().getColumnCount();
			
			while(rs.next()){
				for(int i = 1; i <= numColonne; i++ ){
					colonna = rs.getString(i);
					if(colonna == null || colonna.equals("")){
						colonna = " ";
					}
					
//					if(colonna.length() > 21){
//						System.out.println("CODICE " + colonna.charAt(22) + ":" + (int)colonna.charAt(22) );
//					}
					
					rispostaCSV = rispostaCSV + colonna + "|";
				}
				rispostaCSV = rispostaCSV + "|";
			}
			
			//Estraggo la query escludendo limit e offset per ricavarmi il numero totale di record
			
			String queryInterna = query;
			if(query.contains("limit")){
				queryInterna = query.substring(0,query.indexOf("limit"));
				//System.out.println("QUERY INTERNA passo 1: " + queryInterna);
			}
			if(queryInterna.contains("offset")){
				queryInterna = queryInterna.substring(0,queryInterna.indexOf("offset"));
				//System.out.println("QUERY INTERNA passo 2: " + queryInterna);
			}
			
			if(!queryInterna.equals("")){
				String queryCount = "select count(*) from ( " + queryInterna + ") q";
				//System.out.println("QUERY COUNT: " + queryCount);
				ps = db.prepareStatement(queryCount);
				rs = ps.executeQuery();
				if(rs.next()){
					int numRecord = rs.getInt(1);
					//System.out.println("NUM RECORD: " + numRecord);
					rispostaCSV = numRecord + "||" + rispostaCSV;
				}
			}
			
			//System.out.println("CONTIENE? " + rispostaCSV.contains( ""+(char)65533 ));
			//rispostaCSV = rispostaCSV.replace(""+(char)65533, "");
			
			//rispostaCSV = rispostaCSV + (char)65533;
			
			//System.out.println("RISPOSTA CSV: " + rispostaCSV);
			
			//response.setContentType("text/html;charset=8859-1");
			//response.setHeader("Content-Encoding", "UTF-32");
			//response.setContentType("text/html; charset=iso-8859-1");
			//response.getOutputStream().write(rispostaCSV.getBytes());
			
			request.setAttribute("rispostaCSV", rispostaCSV);
			RequestDispatcher rd = request.getRequestDispatcher("risultatoQuery.jsp");
			rd.forward(request, response);
			
			//response.getOutputStream().write( ("<html><head><meta content=\"text/html; charset=iso-8859-1\" http-equiv=\"Content-Type\"></head><body>"+rispostaCSV+"</body></html>").getBytes() );
			
		}
		catch(Exception e){
			System.out.println("Errore durante l'invocazione di QueryProcessorServlet.");
			e.printStackTrace();
		}
		finally{
			cp.free(db,null);
		}
		
	}

}
