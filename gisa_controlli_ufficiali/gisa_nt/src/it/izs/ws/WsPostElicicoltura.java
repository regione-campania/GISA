/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WsPostElicicoltura 
{
				  
		  private String url = "";
		  private String wsRequest = "";
		  private String xmlns;
		  private String username;
		  private String password;
		  private String ruolo;
		  private String ruoloCodice;
		  private String ruoloValoreCodice;
		  private String nomeServizio;
		  private String tipoAutorizzazione;
		  private String nomeOggetto;
		  private String ambiente;
		  private int idEndpoint;
		  private HashMap<String,Object> campiOggetto = new HashMap<>();
		  private HashMap<String,Object> campiInput = new HashMap<>();
		  
		  public WsPostElicicoltura()
		  {
			  
		  }
		  
		 
		
		  public String getWsRequest() 
		  {
		  	return wsRequest;
		  }
		
		  public void setWsRequest(String wsRequest) 
		  {
		  	this.wsRequest = wsRequest;
		  }
		
		  public HashMap<String, Object> getCampiInput() 
		  {
		  	return campiInput;
		  }
		
		  public void setCampiInput(HashMap<String, Object> campiInput) 
		  {
		  	this.campiInput = campiInput;
		  }
		
		  public String getXmlns() 
		  {
		  	return xmlns;
		  }
		
		  public void setXmlns(String xmlns) 
		  {
		  	this.xmlns = xmlns;
		  }
		  
		  public String getWsUrl() 
		  {
		  	return url;
		  }
		
		  public void setWsUrl(String url) 
		  {
		  	this.url = url;
		  }
		
		  public String getNomeServizio() 
		  {
		  	return nomeServizio;
		  }
		
		  public void setNomeServizio(String nomeServizio) 
		  {
		  	this.nomeServizio = nomeServizio;
		  }
		  
		  public String getTipoAutorizzazione() 
		  {
		  	return tipoAutorizzazione;
		  }
		
		  public void setTipoAutorizzazione(String tipoAutorizzazione) 
		  {
		  	this.tipoAutorizzazione = tipoAutorizzazione;
		  }
		
		  public String getNomeOggetto() 
		  {
		  	return nomeOggetto;
		  }
		
		  public void setNomeOggetto(String nomeOggetto) 
		  {
		  	this.nomeOggetto = nomeOggetto;
		  }
		  
		  public int getIdEndpoint() 
		  {
		  	return idEndpoint;
		  }
		
		  public void setIdEndpoint(int idEndpoint) 
		  {
		  	this.idEndpoint = idEndpoint;
		  }
		
		  public String getUsername() 
		  {
		    return username;
		  }
		
		  public void setUsername(String username) 
		  {
			this.username = username;
		  }
	
		  public String getPassword() 
		  {
		    return password;
		  }
	
	  	  public void setPassword(String password) 
	  	  {
	  		this.password = password;
	  	  }
	
	  	  public String getRuolo() 
	  	  {
	  		return ruolo;
	  	  }
	
	  	  public void setRuolo(String ruolo) 
	  	  {
	  		this.ruolo = ruolo;
	  	  }
	
	  	  public String getRuoloCodice() 
	  	  {
	  		return ruoloCodice;
	  	  }
	
	  	  public void setRuoloCodice(String ruoloCodice) 
	  	  {
	  		this.ruoloCodice = ruoloCodice;
	  	  }
	
	  	  public String getRuoloValoreCodice() 
	  	  {
	  		return ruoloValoreCodice;
	  	  }
	
	  	  public void setRuoloValoreCodice(String ruoloValoreCodice) 
	  	  {
	  		this.ruoloValoreCodice = ruoloValoreCodice;
	  	  }

		  
		
		  public String getToken(Connection db, int userId, String username, String password) 
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);
			  
			  OkHttpClient client = new OkHttpClient();

			  MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
			  RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"grant_type\"\r\n\r\npassword\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\n"+username+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n"+password+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
			  Request request = new Request.Builder()
			    .url(url)
			    .post(body)
			    .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
			    .addHeader("Content-Type", "application/json")
			    .addHeader("Authorization", "Basic MlJTZGcwRlR4eDo2NzU1YjUwNS1iMzdjLTQxYzEtOTc3Yy0yMDc5MzU1MzBlYjM=")
			    .addHeader("Cache-Control", "no-cache")
			    .addHeader("Postman-Token", "c5239c9d-2f0b-40ac-b13e-1e571acf8f5d")
			    .build();

			  Response response;
			try {
				response = client.newCall(request).execute();
			    wsResponse = response.body().string();
				System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				salvaStorico(db, userId, wsResponse);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			}
			  return wsResponse;
		  }
		  
		  public String getAttivitaPerAzienda(Connection db, int userId, String token) throws UnsupportedEncodingException 
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);

			  OkHttpClient client = new OkHttpClient();
			  Request request = new Request.Builder()
			    .url(url+"?q="+URLEncoder.encode(wsRequest, StandardCharsets.UTF_8.toString()))
			    .get()
			    .addHeader("Izs-Profile-Role", "REGWS")
			    .addHeader("Izs-Profile-Code", "150")
			    .addHeader("Izs-Profile-App", "ELI")
			    .addHeader("Authorization", "Bearer "+token)
			    .addHeader("Cache-Control", "no-cache")
			    .addHeader("Postman-Token", "15ab1f7a-f951-48cb-b3f6-8e11b9b27447")
			    .build();

			  
			  
			  Response response;
			try {
				 response = client.newCall(request).execute();
				 wsResponse = response.body().string();
				System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				salvaStorico(db, userId, wsResponse);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			}
			  return wsResponse;
		  }
		  

		 
		  
		  private void salvaStorico(Connection db, int userId, String wsResponse)  
			{
				PreparedStatement pst;
				try 
				{
			        System.out.println("\n\n [*** wsPost ***] INSERISCO STORICO ");
			
					pst = db.prepareStatement("insert into ws_storico_chiamate(url, request, response, id_utente, data) values (?, ?, ?, ?, now());");
					
					pst.setString(1, url);
					pst.setString(2, wsRequest);
					pst.setString(3, wsResponse);
					pst.setInt(4, userId);
					pst.execute();
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
}
