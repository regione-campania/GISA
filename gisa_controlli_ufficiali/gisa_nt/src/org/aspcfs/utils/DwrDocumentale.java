/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.directwebremoting.extend.LoginRequiredException;
import org.json.JSONArray;
import org.json.JSONObject;

public class DwrDocumentale {
	
public String ChiamaServizio(String urlService) {
		
		String output = "{}";
		
			URL url = null;
			HttpURLConnection connection;
			int random = (int )(Math.random() * 50 + 1);
			
	        System.out.println("[SERVER DOCUMENTALE] "+random+" INPUT URL: "+urlService);
			
	        try {
				url = new URL(urlService); //Creating the URL.
		        connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		       // connection.setRequestProperty("Content-Type", "application/json");
		        //connection.setRequestProperty("Accept", "application/json");
		        connection.setUseCaches(false);
		        connection.setDoInput(true);
		        connection.setDoOutput(true);
		        connection.connect(); //New line
	
		        //Send request
		        OutputStream os = connection.getOutputStream();
		        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		        System.out.println("[SERVER DOCUMENTALE] "+random+" SERVICE URL: "+url.toString());
		        osw.flush();
		        osw.close();
		        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            System.out.println("[SERVER DOCUMENTALE] "+random+" SERVICE Ok response!");
		        } else {
		            System.out.println("[SERVER DOCUMENTALE] "+random+" SERVICE ******* Bad response *******");
		        }
			
		        BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
				StringBuffer result = new StringBuffer();
		
				//Leggo l'output: l'header del documento generato e il nome assegnatogli
				if (in != null) {
					String ricevuto = in.readLine();
					result.append(ricevuto); }
					in.close();
				output = result.toString();
		        System.out.println("[SERVER DOCUMENTALE] "+random+" OUTPUT JSON: "+output);
		        
		        } catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        	return output;
		}
	
	public String Documentale_InfoService(String header) { 
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_INFO_SERVICE")+"?output=json&codDocumento="+header;
		return ChiamaServizio(url);
}
	
}
