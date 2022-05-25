/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.util.imports.ApplicationProperties;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletTestDocumentale extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletTestDocumentale() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String esito="";
		HttpURLConnection conn=null;
		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");
		//STAMPE
		URL obj;
		
		try{
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("tipoCertificato");
			requestParams.append("=").append("-1");
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			conn.getContentLength();
			esito ="<font color=\"green\">Online</font>";
			}
			catch (IOException e) {
				esito ="<font color=\"red\">OFFLINE</font>";
			} 
		finally {
			conn.disconnect();
			response.getWriter().println(esito);
			}
	} 
}