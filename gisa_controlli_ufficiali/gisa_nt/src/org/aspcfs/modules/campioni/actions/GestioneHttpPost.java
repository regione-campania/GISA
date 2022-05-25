/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.actions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

//classe per la gestione di una chiamata http di tipo POST
public class GestioneHttpPost{

	 public static JSONObject getJsonObject(String urlInput, JSONObject datiDaInviare) throws Exception {
		 
		  String url = urlInput;
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
		        // Setting basic post request
		  con.setRequestMethod("POST");
		  con.setRequestProperty("Content-Type","application/json");
		 
		  String postJsonData = datiDaInviare.toString();
		  
		  // Send post request
		  con.setDoOutput(true);
		  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		  wr.writeBytes(postJsonData);
		  wr.flush();
		  wr.close();
		 
		  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		  JSONObject json = new JSONObject(response.toString());
		  
		  return json;
		 }
	 
	 
	 public static JSONArray getJsonArray(String urlInput, JSONObject datiDaInviare) throws Exception {
		 
		  String url = urlInput;
		  URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
		  // Setting basic post request
		  con.setRequestMethod("POST");
		  //con.setRequestProperty("User-Agent", "Mozilla/5.0");
		  //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		  con.setRequestProperty("Content-Type","application/json");
		 
		  String postJsonData = datiDaInviare.toString();
		  
		  // Send post request
		  con.setDoOutput(true);
		  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		  wr.writeBytes(postJsonData);
		  wr.flush();
		  wr.close();
		 
		  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		  JSONArray json = new JSONArray(response.toString());
		  
		  return json;
		 }
}
