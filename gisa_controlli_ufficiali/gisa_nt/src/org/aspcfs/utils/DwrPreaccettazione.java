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

import org.directwebremoting.extend.LoginRequiredException;
import org.json.JSONArray;
import org.json.JSONObject;

public class DwrPreaccettazione {
	
public String ChiamaServizioConJSON(String urlService, JSONObject json) {
		
		String output = "{}";
		String amb = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente");
		String abilitazione_chiamata_microservices = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("abilitazione_chiamata_microservices");
		if(abilitazione_chiamata_microservices.equals("false")){
		//if (amb.equalsIgnoreCase("ufficiale") || amb.equalsIgnoreCase("demo")){
			JSONObject  jsonout = new JSONObject();
			jsonout.put("codice_preaccettazione", "" );
			jsonout.put("messaggio", "");
			jsonout.put("esito_cancellazione", "0");
			jsonout.put("errore_cancellazione", "");
			return output = jsonout.toString();
		}else{
			
			URL url = null;
			HttpURLConnection connection;
			int random = (int )(Math.random() * 50 + 1);
			
	        System.out.println("[PREACCETTAZIONE] "+random+" INPUT URL: "+urlService);
	        System.out.println("[PREACCETTAZIONE] "+random+" INPUT JSON: "+json.toString());
			
	        try {
				url = new URL(urlService); //Creating the URL.
		        connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/json");
		        //connection.setRequestProperty("Accept", "application/json");
		        connection.setUseCaches(false);
		        connection.setDoInput(true);
		        connection.setDoOutput(true);
		        connection.connect(); //New line
	
		        //Send request
		        OutputStream os = connection.getOutputStream();
		        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		        System.out.println("[PREACCETTAZIONE] "+random+" SERVICE URL: "+url.toString());
		        osw.write(json.toString());
		        osw.flush();
		        osw.close();
		        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            System.out.println("[PREACCETTAZIONE] "+random+" SERVICE Ok response!");
		        } else {
		            System.out.println("[PREACCETTAZIONE] "+random+" SERVICE ******* Bad response *******");
		        }
			
		        BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
				StringBuffer result = new StringBuffer();
		
				//Leggo l'output: l'header del documento generato e il nome assegnatogli
				if (in != null) {
					String ricevuto = in.readLine();
					result.append(ricevuto); }
					in.close();
				output = result.toString();
		        System.out.println("[PREACCETTAZIONE] "+random+" OUTPUT JSON: "+output);
		        
		        } catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        	return output;
		}
	
}
	
	public String Preaccettazione_GetElencoDaLinea(String identificativo_linea, String id_ente, String id_laboratorio) {
		JSONObject json = new JSONObject();
		json.put("identificativo_linea", identificativo_linea);
		json.put("id_laboratorio", id_laboratorio);
		json.put("id_ente", id_ente);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+ org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Report/Getelencodalinea";
		return ChiamaServizioConJSON(url, json);
	}
	
	public String Preaccettazione_GetPreaccettazione(String userId, String idEnte, String idLaboratorio) {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("id_ente", idEnte);
		json.put("id_laboratorio", idLaboratorio);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Prenotazioni/Getpreaccettazione";
		return ChiamaServizioConJSON(url, json);
	}
	
	public String Preaccettazione_SetPreaccettazione(String id, String riferimento_id, String riferimento_id_nome, String riferimento_id_nome_tab, String id_linea_materializzata, String tipologia_operatore, String userId) {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("riferimento_id", riferimento_id);
		json.put("riferimento_id_nome", riferimento_id_nome);
		json.put("riferimento_id_nome_tab", riferimento_id_nome_tab);
		json.put("id_linea_materializzata", id_linea_materializzata);
		json.put("tipologia_operatore", tipologia_operatore);
		json.put("userId", userId);
		json.put("quesito_diagnostico", "");
		json.put("matrice_campione", "");
		json.put("id_quesito_diagnostico", "");
		json.put("id_matrice_campione", "");
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Prenotazioni/Setpreaccettazione";
		return ChiamaServizioConJSON(url, json);
	}

	public String Preaccettazione_RecuperaCodPreaccettazione(String idCampione) { 
		JSONObject json = new JSONObject();
		json.put("idCampione", idCampione);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Report/Getcodicepreaccettazionedacampione";
		return ChiamaServizioConJSON(url, json);
}
	
	public String Preaccettazione_GetListaLineeGisa(String riferimentoId, String riferimentoIdNome, String riferimentoIdNomeTab) { 
		JSONObject json = new JSONObject();
		json.put("riferimento_id", riferimentoId);
		json.put("riferimento_id_nome", riferimentoIdNome);
		json.put("riferimento_id_nome_tab", riferimentoIdNomeTab);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Osa/Listalineegisa";
		return ChiamaServizioConJSON(url, json);
}
	public String Preaccettazione_CancellazionePreaccettazione(String idCampione, String cancella, String userId) { 
		JSONObject json = new JSONObject();
		json.put("idCampione", idCampione);
		json.put("cancella", cancella);
		json.put("userId", userId);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Prenotazioni/Cancellazionepreaccettazione";
		return ChiamaServizioConJSON(url, json);
}
	
	public String Preaccettazione_Associacampione(String id_campione, String userId, String codice_preaccettazione){
		JSONObject  json = new JSONObject();
		json.put("id_campione", id_campione );
		json.put("userId", userId);
		json.put("codice_preaccettazione",codice_preaccettazione);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Prenotazioni/Associacampione";
		return ChiamaServizioConJSON(url, json);
	}
	
	
	public String Preaccettazione_Ritorno_Da_Laboratorio(String codice_preaccettazione, int user_id, String id_laboratorio) throws ParseException{
		
		if (id_laboratorio!=null && id_laboratorio.equals("2")) //sigla
			return Preaccettazione_Ritorno_Da_Sigla(codice_preaccettazione, user_id);
		else if (id_laboratorio!=null && id_laboratorio.equals("1")) //arpac
			return Preaccettazione_Ritorno_Da_Arpac(codice_preaccettazione, user_id);
		return "";
	}
	
	public String Preaccettazione_Ritorno_Da_Sigla(String codice_preaccettazione, int user_id) throws ParseException{
		
		//ritorno della dwr contenente descrizione esito esame
	 	String output_esito = "";
	 	
	 	//recupero note_esito_esame dal campione associato alla preaccettazione
	 	String note_esito_esame_campione = recupera_note_esito_esame_da_campione(codice_preaccettazione);

		//recupera stato preaccettazione
		int stato_preaccettazione = 0;
		stato_preaccettazione = recupera_stato_preaccettazione(codice_preaccettazione);
		
		//se stato = 5 recupera campo note_esito_esame dalla tabella ticket
		if(stato_preaccettazione == 5){
			output_esito = note_esito_esame_campione; 
			System.out.println("recupero esito da ticket con preaccettazione gia ricevuta da ws sigla: " + output_esito);
			
		}
		
		if(!note_esito_esame_campione.equalsIgnoreCase("")){
	 		output_esito = note_esito_esame_campione;
	 		System.out.println("note recupero esito da ticket: " + output_esito);
	 	}
		
		//se stato diverso da 5 chiama WS sigla e salvare chiamata nel log
		if(stato_preaccettazione != 5) 
		{
			String host_sigla = "vuoto";
			try {host_sigla = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_SIGLA_HOST");} catch (Exception e){}
			String url_login_rit_sigla = "http://" + host_sigla + "/api/v1/rest/login";
			String output_login_sigla = "";
			//String username = "GISA_WS";
			//String password = "g1s4t0SiG4!2020";
			
			String username = "";
			try {username = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_SIGLA_LOGIN_USERNAME");} catch (Exception e){}
			if(username == null){
				username = "";
			}
			String password = "";
			try {password = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_SIGLA_LOGIN_PASSWORD");} catch (Exception e){}
			if(password == null){
				password = "";
			}
			
			//chiamata per il ws sigla che restituisce il token
	        output_login_sigla = ChiamaServizioPostRitornoSiglaLogin(url_login_rit_sigla, username, password, user_id);
	        System.out.println("login servizio sigla: " + output_login_sigla);
	        
	        JSONObject json_login = new JSONObject("{}");
	        json_login = new JSONObject(output_login_sigla.toString());
			
	        String token = "";
	        try {token = json_login.getJSONObject("utente").getString("token");} catch (Exception e){}

	        if(!token.equalsIgnoreCase("")){
	        	String url_servizio_esito = "";
	        	url_servizio_esito = "http://" + host_sigla + "/api/v1/rest/esito?";
	            output_esito = ChiamaServizioPostRitornoSiglaEsito(url_servizio_esito, codice_preaccettazione, token, user_id);
	        } 
	        
	        System.out.println("recupero esito da ws sigla: " + output_esito);
		}
		
		System.out.println("esito chiamata dwr: " + output_esito);

		return output_esito;
	}
	
 	private String ChiamaServizioPostRitornoSiglaLogin(String urlService, String username, String password, int user_id){
		
		String output = "{}";
		String parameters = "{\"username\": \"" + username + "\",\"password\": \"" + password + "\"}";
		System.out.println("[PREACCETTAZIONE] login ws sigla parameters JSON: " + parameters);
		try {
			System.out.println("[PREACCETTAZIONE] url ws sigla login: " + urlService);
			
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
			  .addFormDataPart("username", username)
			  .addFormDataPart("password", password)
			  .build();
			Request request = new Request.Builder().url(urlService).method("POST", body).build();
			salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
			Response response = client.newCall(request).execute();
			output = response.body().string();
			System.out.println("[PREACCETTAZIONE] login ws sigla OUTPUT JSON: " + output);
			salvaStorico(user_id, urlService, parameters, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			output = "{}";
			e.printStackTrace();
			salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
		}
		finally{
			if(output == null || output.equalsIgnoreCase("")){
				output = "{}";
			}
			System.out.println("[PREACCETTAZIONE] finally login ws sigla OUTPUT JSON: " + output);
		}
		
		return output;
	}
	
	private String ChiamaServizioPostRitornoSiglaEsito(String url, String codice_preaccettazione, String token, int user_id){
		
		String output = "";
		String parametri_chiamata = "codice_preaccettazione_gisa=" + codice_preaccettazione + "&token=" + token;
		System.out.println("[PREACCETTAZIONE] esito ws sigla url + parametri: " + url + parametri_chiamata);
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(url+parametri_chiamata).method("GET", null).build();
		
		salvaStorico(user_id, url, parametri_chiamata, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
		
		try {
			
			Response response = client.newCall(request).execute();
			output = response.body().string();
			System.out.println("[PREACCETTAZIONE] ws sigla http_status_code: " + response.networkResponse().code());
			System.out.println("[PREACCETTAZIONE] esito ws sigla OUTPUT JSON: " + output);
			salvaStorico(user_id, url, parametri_chiamata, output);
			//se WS risponde pieno chiamare dbi dbi_ins_res_sigla e restituire la descrizione risultato esame alla jsp
			if(!output.equalsIgnoreCase("[]") && !output.equalsIgnoreCase("")) {

				//formatto la descrizione risultato esame da passare alla dbi e da restituire alla jsp
				JSONArray jsonarray = new JSONArray(output);
				String lista_esiti = "<div><table class='table details' width='100%' cellpadding='2' style='border-collapse: collapse' border='1'>";
				for(int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonobject = jsonarray.getJSONObject(i);
					lista_esiti += "<tr>"
							+ "<td align='center' style='width:25%; text-transform: none;'>"+jsonobject.getString("descrizione_esame").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:60%; text-transform: none;'>"+jsonobject.getString("risultato").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:10%; text-transform: none;'>"+jsonobject.getString("descrizione_esito").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:5%; text-transform: none;'>"+jsonobject.getString("esito").replace("<", "< ")+"</td>"
							+ "</tr>";
				}
				lista_esiti = lista_esiti + "</table></div>";
				//chiama dbi dbi_ins_res_sigla
				chiama_dbi_ins_res_sigla(codice_preaccettazione, lista_esiti, user_id);
				output = lista_esiti;
				
			} else {
				output = "";
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			output = "";
			e.printStackTrace();
			salvaStorico(user_id, url, parametri_chiamata, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
		} finally {
			if(output == null){
				output = "";
			}
			System.out.println("[PREACCETTAZIONE] finally esito ws sigla OUTPUT: " + output);
		}
		
		return output;
	}
	
	
	public String Preaccettazione_Ritorno_Da_Arpac(String codice_preaccettazione, int user_id) throws ParseException{
		
		//ritorno della dwr contenente descrizione esito esame
	 	String output_esito = "";
	 	
	 	//recupero note_esito_esame dal campione associato alla preaccettazione
	 	String note_esito_esame_campione = recupera_note_esito_esame_da_campione(codice_preaccettazione);

		//recupera stato preaccettazione
		int stato_preaccettazione = 0;
		stato_preaccettazione = recupera_stato_preaccettazione(codice_preaccettazione);
		
		//se stato = 5 recupera campo note_esito_esame dalla tabella ticket
		if(stato_preaccettazione == 5){
			output_esito = note_esito_esame_campione; 
			System.out.println("recupero esito da ticket con preaccettazione gia ricevuta da ws arpac: " + output_esito);
			
		}
		
		if(!note_esito_esame_campione.equalsIgnoreCase("")){
	 		output_esito = note_esito_esame_campione;
	 		System.out.println("note recupero esito da ticket: " + output_esito);
	 	}
		
		//se stato diverso da 5 chiama WS arpac e salvare chiamata nel log
		if(stato_preaccettazione != 5) 
		{
			String host_arpac = "vuoto";
			try {host_arpac = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_ARPAC_HOST");} catch (Exception e){}
			String url_login_rit_arpac = "https://" + host_arpac + "/MobileService.svc/Logon";
			String output_login_arpac = "";
			
			String username = "";
			try {username = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_ARPAC_LOGIN_USERNAME");} catch (Exception e){}
			if(username == null){
				username = "";
			}
			String password = "";
			try {password = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WS_ARPAC_LOGIN_PASSWORD");} catch (Exception e){}
			if(password == null){
				password = "";
			}
			String idTenant ="00000000-0000-0000-0000-000000000000";
			
			//chiamata per il ws arpac che restituisce il token
	        output_login_arpac = ChiamaServizioPostRitornoArpacLogin(url_login_rit_arpac, username, password, idTenant, user_id);
	        System.out.println("login servizio arpac: " + output_login_arpac); 
	        
	        JSONObject json_login = new JSONObject("{}");
	        json_login = new JSONObject(output_login_arpac.toString());
			
	        String idUser = "";
	        String idSession = "";
	        String anno = "2021";
	        String alreadyLogged = "";
	        
	        try {alreadyLogged = json_login.getString("AlreadyLogged");} catch (Exception e){}
	        try {idUser = json_login.getString("IdUser");} catch (Exception e){}
	        try {idSession = json_login.getString("IdSession");} catch (Exception e){}
	        
	        if("true".equalsIgnoreCase(alreadyLogged)){
				String url_logout_rit_arpac = "https://" + host_arpac + "/MobileService.svc/Logoff";
		        String output_logout_arpac = ChiamaServizioPostRitornoArpacLogout(url_logout_rit_arpac, idSession, idTenant, user_id);
		        output_login_arpac = ChiamaServizioPostRitornoArpacLogin(url_login_rit_arpac, username, password, idTenant, user_id);
		        json_login = new JSONObject(output_login_arpac.toString());
		        try {idUser = json_login.getString("IdUser");} catch (Exception e){}
		        try {idSession = json_login.getString("IdSession");} catch (Exception e){}
	        }

	        if(!idSession.equalsIgnoreCase("")){
	        	String url_servizio_esito = "";
	        	url_servizio_esito = "https://" + host_arpac + "/MobileService.svc/GetSamplesTestResult";
	            output_esito = ChiamaServizioPostRitornoArpacEsito(url_servizio_esito, codice_preaccettazione, idSession, idUser, anno, user_id);
	            
	            String url_logout_rit_arpac = "https://" + host_arpac + "/MobileService.svc/Logoff";
		        String output_logout_arpac = ChiamaServizioPostRitornoArpacLogout(url_logout_rit_arpac, idSession, idTenant, user_id);
	        } 
	        
	        System.out.println("recupero esito da ws arpac: " + output_esito);
		}
		
		System.out.println("esito chiamata dwr: " + output_esito);

		return output_esito;
	}
	
	
		private String ChiamaServizioPostRitornoArpacLogin(String urlService, String username, String password, String idTenant, int user_id){
		
		String output = "{}";
		String parameters = "{\"username\": \"" + username + "\",\"password\": \"" + password + "\",\"idTenant\": \"" + idTenant+ "\"}";
		System.out.println("[PREACCETTAZIONE] login ws arpac parameters JSON: " + parameters);
		try {
			System.out.println("[PREACCETTAZIONE] url ws arpac login: " + urlService);
			
		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			OkHttpClient client = new OkHttpClient().newBuilder().build();
		    RequestBody body = RequestBody.create(JSON, parameters);
		    
			Request request = new Request.Builder().url(urlService).method("POST", body).build();
			salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
			Response response = client.newCall(request).execute();
			output = response.body().string();
			System.out.println("[PREACCETTAZIONE] login ws arpac OUTPUT JSON: " + output);
			salvaStorico(user_id, urlService, parameters, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			output = "{}";
			e.printStackTrace();
			salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
		}
		finally{
			if(output == null || output.equalsIgnoreCase("")){
				output = "{}";
			}
			System.out.println("[PREACCETTAZIONE] finally login ws arpac OUTPUT JSON: " + output);
		}
		
		return output;
	}
		
		private String ChiamaServizioPostRitornoArpacLogout(String urlService, String idSession, String idTenant, int user_id){
			
			String output = "{}";
			String parameters = "{\"idSession\": \"" + idSession + "\",\"idTenant\": \"" + idTenant+ "\"}";
			System.out.println("[PREACCETTAZIONE] logout ws arpac parameters JSON: " + parameters);
			try {
				System.out.println("[PREACCETTAZIONE] url ws arpac logout: " + urlService);
				
			    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
				OkHttpClient client = new OkHttpClient().newBuilder().build();
			    RequestBody body = RequestBody.create(JSON, parameters);
			    
				Request request = new Request.Builder().url(urlService).method("POST", body).build();
				salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
				Response response = client.newCall(request).execute();
				output = response.body().string();
				System.out.println("[PREACCETTAZIONE] logout ws arpac OUTPUT JSON: " + output);
				salvaStorico(user_id, urlService, parameters, output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				output = "{}";
				e.printStackTrace();
				salvaStorico(user_id, urlService, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			}
			finally{
				if(output == null || output.equalsIgnoreCase("")){
					output = "{}";
				}
				System.out.println("[PREACCETTAZIONE] finally logout ws arpac OUTPUT JSON: " + output);
			}
			
			return output;
		}
	
	private String ChiamaServizioPostRitornoArpacEsito(String url, String codice_preaccettazione, String idSession, String idUser, String anno, int user_id){
		
		String output = "";
		String parameters = "{\"idSession\": \"" + idSession + "\",\"idUser\": \"" + idUser + "\",\"SamplesCodeGISA\": \"" + codice_preaccettazione + "\",\"samplesCodeYear\": \"" + anno+ "\"}";
		System.out.println("[PREACCETTAZIONE] esito ws arpac url + parametri: " + url + parameters);
		
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		RequestBody body = RequestBody.create(JSON, parameters);
			
		Request request = new Request.Builder().url(url).method("POST", body).build();
		
		salvaStorico(user_id, url, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
		
		try {
			
			Response response = client.newCall(request).execute();
			output = response.body().string();
			System.out.println("[PREACCETTAZIONE] ws arpac http_status_code: " + response.networkResponse().code());
			System.out.println("[PREACCETTAZIONE] esito ws arpac OUTPUT JSON: " + output);
			salvaStorico(user_id, url, parameters, output);
			//se WS risponde pieno chiamare dbi dbi_ins_res_arpac e restituire la descrizione risultato esame alla jsp
			if(!output.equalsIgnoreCase("[]") && !output.equalsIgnoreCase("")) {
				
				JSONObject output_json = new JSONObject("{}");
				output_json = new JSONObject(output.toString());
						
				JSONArray jsonarray = new JSONArray();
				
				try { jsonarray = new JSONArray(output_json.getJSONArray("Data").toString());} catch (Exception e){}

				//formatto la descrizione risultato esame da passare alla dbi e da restituire alla jsp
				
				String lista_esiti = "<div><table class='table details' width='100%' cellpadding='2' style='border-collapse: collapse' border='1'>"
						+ "<tr><th>Descrizione esame</th><th>Risultato</th><th>Descrizione esito</th><th>Esito</th></tr>";
				for(int i = 0; i < jsonarray.length(); i++) {
					JSONObject jsonobject = jsonarray.getJSONObject(i);
					lista_esiti += "<tr>"
							+ "<td align='center' style='width:25%; text-transform: none;'>"+jsonobject.getString("descrizione_esame").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:60%; text-transform: none;'>"+jsonobject.getString("risultato").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:10%; text-transform: none;'>"+jsonobject.getString("descrizione_esito").replace("<", "< ")+"</td>"
							+ "<td align='center' style='width:5%; text-transform: none;'>"+jsonobject.getString("esito").replace("<", "< ")+"</td>"
							+ "</tr>";
				}
				lista_esiti = lista_esiti + "</table></div>";
				//chiama dbi dbi_ins_res_arpac
				chiama_dbi_ins_res_arpac(codice_preaccettazione, lista_esiti, user_id);
				output = lista_esiti;
				
			} else {
				output = "";
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			output = "";
			e.printStackTrace();
			salvaStorico(user_id, url, parameters, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
		} finally {
			if(output == null){
				output = "";
			}
			System.out.println("[PREACCETTAZIONE] finally esito ws arpac OUTPUT: " + output);
		}
		
		return output;
	}
	
	
	private int recupera_stato_preaccettazione(String codice_preaccettazione){
		
		String select = "";
		int ret	= 0; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select id_stato "
					+ "from preaccettazione.vw_ultimo_stato "
					+ "where concat(prefix,anno,progres) ilike ?";
			pst = db.prepareStatement(select);
			pst.setString(1, codice_preaccettazione);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				ret = rs.getInt("id_stato");
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
	
	private String recupera_note_esito_esame_da_campione(String codice_preaccettazione){
		
		String select = "";
		String ret	= ""; 
		int id_cmp = 0;
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select id_cmp::integer from preaccettazione.get_id_cmp_da_codice_preaccettazione(?);";
			pst = db.prepareStatement(select);
			pst.setString(1, codice_preaccettazione);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				id_cmp = rs.getInt("id_cmp");
			}
			
			//select note_esito_esame, * from ticket where ticketid =  1346069
			select = "select coalesce(trim(note_esito_esame),'') as note_esito_esame from ticket where ticketid =  ?;";
			pst = db.prepareStatement(select);
			pst.setInt(1, id_cmp);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				ret = rs.getString("note_esito_esame");
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}

	private void chiama_dbi_ins_res_sigla(String codice_preaccettazione, String descr_ris_esame, int user_id){
		String select = "";
		int ret	= 0; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select * from preaccettazione.dbi_ins_res_sigla(?,?,?)";
			
			pst = db.prepareStatement(select);
			pst.setString(1, codice_preaccettazione);
			pst.setString(2, descr_ris_esame);
			pst.setInt(3, user_id);
			System.out.println("chiamata dbi dbi_ins_res_sigla : " + pst);
			rs = pst.executeQuery();
			

			if ( rs.next() )
			{
				ret = rs.getInt("_idout");
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

	}
	
	private void chiama_dbi_ins_res_arpac(String codice_preaccettazione, String descr_ris_esame, int user_id){
		String select = "";
		int ret	= 0; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select * from preaccettazione.dbi_ins_res_arpac(?,?,?)";
			
			pst = db.prepareStatement(select);
			pst.setString(1, codice_preaccettazione);
			pst.setString(2, descr_ris_esame);
			pst.setInt(3, user_id);
			System.out.println("chiamata dbi dbi_ins_res_arpac : " + pst);
			rs = pst.executeQuery();
			

			if ( rs.next() )
			{
				ret = rs.getInt("_idout");
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

	}
	
	private void salvaStorico(int userId, String url, String wsRequest, String wsResponse)  
	{
		Connection db = null;
		PreparedStatement pst;
		try 
		{
			db = GestoreConnessioni.getConnection()	;
			
	        System.out.println("\n\n [*** wsPost ***] INSERISCO STORICO ");
	
			//pst = db.prepareStatement("insert into ws_storico_chiamate(url, request, response, id_utente, data) values (?, ?, ?, ?, now());");
	        pst = db.prepareStatement("select * from inserisci_nel_log(?,?,?,?);");
	        
			pst.setString(1, url);
			pst.setString(2, wsRequest);
			pst.setString(3, wsResponse);
			pst.setInt(4, userId);
			pst.execute();
		} 
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
	}
	
	public String Preaccettazione_GetElencoLaboratoriDaEnte(String idEnte) { 
		JSONObject json = new JSONObject();
		json.put("id_ente", idEnte);
		String url = "http://"+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_HOST")+org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("PREACCETTAZIONE_PORT")+"/preaccettazione/All/Report/Getelencolaboratoridaente";
		return ChiamaServizioConJSON(url, json);
	}
	
	public static String verificaPresenzaCampioniPreaccettazioneSuCu(int idControllo){ 
		
		String select = "";
		String ret	= ""; 
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			db = GestoreConnessioni.getConnection()	;
			
			select = "select * FROM preaccettazione.verifica_presenza_campioni_preaccettazione_su_cu(?)";
			pst = db.prepareStatement(select);
			pst.setInt(1, idControllo);
			rs = pst.executeQuery();

			if ( rs.next() )
			{
				ret = rs.getString(1);
			}

		}catch(LoginRequiredException e)
		{

			throw e ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		return ret;
	}
}
