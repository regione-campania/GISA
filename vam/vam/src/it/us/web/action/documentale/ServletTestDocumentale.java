/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;

public class ServletTestDocumentale extends GenericAction {

	
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	public void execute() throws Exception
	{
		
		String esito="";
		HttpURLConnection conn=null;
		
		String urlDocumentale = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/ListaDocs";

		
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
			res.getWriter().println(esito);
			}
	} 
}