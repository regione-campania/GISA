/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONObject;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletDocumentaleWrapper extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDocumentaleWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String parametri = "";
		String nomeServlet = "";
		String parametriDecode = "";
		
		JSONObject jo = new JSONObject();
		
		HashMap<String, String> map = new HashMap<String, String>();

		String contentType = request.getContentType();
		if (contentType!=null && contentType.toLowerCase().indexOf("multipart/form-data") > -1){} else { //classico
			
			parametri = request.getParameter("parametri");
			parametriDecode = URLDecoder.decode(parametri, "ISO-8859-1");
			
			 String[] params = parametriDecode.split("&");  
			   
			    for (String param : params) {  
			        String name = param.split("=")[0];  
			        String value = param.split("=")[1];  
			        map.put(name, value);  
			    }  
			    
			nomeServlet = map.get("nomeServlet");

			String path = map.get("path");
			long fileDimension = -1;
			String baString = "";
			byte[] ba = null;
			
			//SE HO RICEVUTO PATH, ISTANZIO IL FILE E LO RECUPERO
			if (path!=null && !path.equals("")){
				File file = new File(path);
				
				String fileName = file.getName();
				
		        Path path2 = Paths.get(path);
				fileDimension = Files.size(path2);
				
				byte[] buffer = new byte[(int) file.length()];
				InputStream ios = null;
				try {
					ios = new FileInputStream(file);
					if (ios.read(buffer) == -1) {
						throw new IOException("EOF reached while trying to read the whole file");
					}
				} finally {
					try {
						if (ios != null)
							ios.close();
					} catch (IOException e) {
					}
				}

				ba = buffer;
				baString = new String(ba, "ISO-8859-1");
				
				map.put("baString", baString);
				map.put("fileDimension", String.valueOf(fileDimension));
				map.put("filename", fileName);
				
			}
				
		}
		
		HttpURLConnection conn=null;
		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/serverDocumentale/"+nomeServlet;
		
		if (nomeServlet.equalsIgnoreCase("DownloadService")){
			response.sendRedirect(urlDocumentale+"?codDocumento="+map.get("codDocumento"));
			return ;
		}
		
		//STAMPE
		URL obj;
		
		try{
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			
			for (Entry<String, String> entry : map.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    requestParams.append(key);
				requestParams.append("=").append(URLEncoder.encode(value, "ISO-8859-1"));
			    requestParams.append("&");
			}
			
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			conn.getContentLength();
			

			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			jo = new JSONObject(result.toString());
		
		}
			catch (IOException e) {
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		finally {
			conn.disconnect();
			response.getWriter().println(jo.toString());
			}
	} 
}