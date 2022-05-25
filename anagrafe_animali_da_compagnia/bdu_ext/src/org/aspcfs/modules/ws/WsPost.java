/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.aspcfs.utils.ApplicationProperties;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;

import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.Headers.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WsPost 
{
		  public static final MediaType hdr  = MediaType.parse("text/xml;charset=UTF-8");
		  public static final MediaType JSON  = MediaType.parse("application/json;charset=UTF-8");

		  
		  public static final int ENDPOINT_API = 1 ;
		  public static final int ENDPOINT_API_REGINE	= 2 ;
		  public static final int ENDPOINT_ACQUACOLTURA	= 3 ;
		  public static final int ENDPOINT_API_MOVIMENTAZIONI	= 4 ;
		  public static final int ENDPOINT_API_MOVIMENTAZIONI_INGRESSO	= 5 ;
		  public static final int ENDPOINT_API_MOVIMENTAZIONI_DETTAGLIO_MODELLO	= 6 ;
		  public static final int ENDPOINT_API_ATTIVITA = 7 ;
		  public static final int ENDPOINT_PRELIEVO_MOLLUSCHI = 8 ;
		  public static final int ENDPOINT_ALLEVAMENTI = 9 ;
		  public static final int ENDPOINT_ALLEVAMENTI_AZIENDA = 10 ;
		  public static final int ENDPOINT_ALLEVAMENTI_PERSONA = 11 ;
		  
		  public static final int AZIONE_GET = 1 ;
		  public static final int AZIONE_GETBYPK = 2 ;
		  public static final int AZIONE_INSERT = 3 ;
		  public static final int AZIONE_UPDATE = 4 ;
		  public static final int AZIONE_DELETE = 5 ;
		  
		  public static final String AMBIENTE_UFFICIALE = "UFFICIALE" ;
		  public static final String AMBIENTE_DEMO = "DEMO" ;
		  
		  public String url = "";
		  private String wsRequest = "";
		  private String xmlns;
		  private String suffissoAutenticazione;
		  private String prefissoUsernamePassword;
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
		  private HashMap<String,Boolean> obbligatorioCampo = new HashMap<>();
		  private HashMap<String,Object> campiInput = new HashMap<>();
		  public boolean propagazioneSinaaf;
		  public String presenteInGisa;
		  public boolean sincronizzato;
		  public String nomeWs;
		  public String dbiGetEnvelope;
		  public String idTabella;
		  public String tabella;
		  public String dipendenze;
		  public String nomeCampoIdSinaaf;
		  public String idSinaaf;
		  public WsPost wsPostSecondario = null;
		  
		  public WsPost()
		  {
			  
		  }
		  
		  public WsPost(Connection db,String idEntita,String entita,String url)
		  {
			  setInfoWs(db,idEntita, entita);
			  this.url=url+nomeWs;
			  if(wsPostSecondario!=null)
				  wsPostSecondario.url=url+wsPostSecondario.nomeWs;
		  }
		  
		  
		  
		  public WsPost(Connection db, int idEndpoint, int idAzione)
		  {
			  if(ApplicationProperties.getAmbiente()!=null)
				  ambiente = (ApplicationProperties.getAmbiente().equalsIgnoreCase("UFFICIALE")?(AMBIENTE_UFFICIALE):(AMBIENTE_DEMO));
			  setEndpointInfo(db, idEndpoint);
			  setServizioInfo(db, idAzione);
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
		  
		  public String getPrefissoUsernamePassword() 
		  {
		  	return prefissoUsernamePassword;
		  }
		
		  public void setPrefissoUsernamePassword(String prefissoUsernamePassword) 
		  {
		  	this.prefissoUsernamePassword = prefissoUsernamePassword;
		  }
		  
		  public String getSuffissoAutenticazione() 
		  {
		  	return suffissoAutenticazione;
		  }
		
		  public void setSuffissoAutenticazione(String suffissoAutenticazione) 
		  {
		  	this.suffissoAutenticazione = suffissoAutenticazione;
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

		  public String post(Connection db, int userId)  
		  {
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
		
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest); 
			  
			  RequestBody body = RequestBody.create(hdr, wsRequest);
			  Request request = new Request.Builder().url(url).post(body).build();
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
			  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  wsResponse = response.body().string();
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			  }
			  return wsResponse;
		  }
		  
		  
		  public String post(Connection db, Integer userId,MediaType mediaType,String token, String idTabella, String tabella,String method)  
		  {
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
			  
			  String suffissoWs="";
			  if(method.equals("put"))
				  suffissoWs="/"+idSinaaf;
		
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url+suffissoWs);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest); 
			  
			  RequestBody body = RequestBody.create(mediaType, wsRequest);
			  Request request = null;
			  okhttp3.Request.Builder builder ;
			  if(token!=null)
				  builder = new Request.Builder().addHeader("Authorization", "Bearer " + token).addHeader("Izs-Profile-Role", "WSREG").addHeader("Izs-Profile-Code", "150").url(url+suffissoWs);
			  else
				  builder = new Request.Builder().url(url+suffissoWs);
				  
			  if(method.equals("post"))
				  request = builder.post(body).build();
			  else
				  request = builder.put(body).build();
				  
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
			  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  String esito = "OK";
				  wsResponse = response.body().string();
				  JsonParser parser = new JsonParser(); 
				  JsonObject json = (JsonObject) parser.parse(wsResponse);
				  String status = (json.get("status")==null)?(null):(json.get("status").toString());
				  String error = (json.get("error")==null)?(null):(json.get("error").toString());
				  if((status!=null && (status.equals("422") || status.equals("500") || status.equals("403"))) || error!=null)
					  esito="KO";
				  
				  
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse,idTabella,tabella,esito);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString(),idTabella,tabella,"KO");
			  }
			  return wsResponse;
		  }
		  
		  
		  public String get(Connection db, Integer userId,MediaType mediaType,String token, String idTabella, String tabella)  
		  {
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");
		
			  String wsResponse = "";
			  System.out.println("\n [*** wsGet ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsGet ***] REQUEST:\n" + wsRequest); 
			  
			  RequestBody body = RequestBody.create(mediaType, wsRequest);
			  Request request = null;
			  if(token!=null)
				  request = new Request.Builder().addHeader("Authorization", "Bearer " + token)
						  .addHeader("Izs-Profile-Role", "WSREG")
						  .addHeader("Izs-Profile-Code", "150").url(url).get().build();
			  else
				  request = new Request.Builder().url(url).get().build();
				  
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
			  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  String esito = "OK";
				  wsResponse = response.body().string();
				  JsonParser parser = new JsonParser(); 
				  JsonObject json = (JsonObject) parser.parse(wsResponse);
				  String status = (json.get("status")==null)?(null):(json.get("status").toString());
				  String error = (json.get("error")==null)?(null):(json.get("error").toString());
				  if((status!=null && (status.equals("422") || status.equals("500") || status.equals("403"))) || error!=null)
					  esito="KO";
				  
				  
				  System.out.println("\n\n [*** wsGet ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse,idTabella,tabella,esito);
			  } 
			  catch (IOException e) 
			  {
				  e.printStackTrace();
				  if(db!=null)
					  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString(),idTabella,tabella,"KO");
			  }
			  return wsResponse;
		  }
		  
		  public String postJSONWithAuthentication(Connection db, int userId, String username, String password, String token)  
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");

			  RequestBody body = RequestBody.create(JSON, wsRequest);
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);
			  
    		  Request request =  null;
    		  
    		  if (token!=null && !token.equals("")){
    			  System.out.println("\n\n [*** wsPost ***] USO TOKEN:\n" + token);
        		  request = new Request.Builder().url(url).addHeader("Authorization: Bearer ", token).post(body).build();
    		  }
    		  else {
    			  System.out.println("\n\n [*** wsPost ***] USO BASIC:\n" + username + " " + password);
    			  request =  new Request.Builder().url(url).addHeader("Authorization", Credentials.basic(username, password)).post(body).build();
    		  }
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  wsResponse = response.body().string();
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			  }
			  return wsResponse;
		  }
		  
		  public String postJSONWithApiKeyToken(Connection db, int userId, String token)  
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");

			  RequestBody body = RequestBody.create(JSON, wsRequest);
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);
			  
    		  Request request =  null;
    		  
    			  System.out.println("\n\n [*** wsPost ***] USO TOKEN:\n" + token);
        		  request = new Request.Builder().url(url).addHeader("X-API-KEY", token).post(body).build();
    		  
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  wsResponse = response.body().string();
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			  }
			  return wsResponse;
		  }
		  
		  public String postJSON(Connection db, int userId)  
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");

			  RequestBody body = RequestBody.create(JSON, wsRequest);
			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);
			  
			  Request request = new Request.Builder().url(url).post(body).build();
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
		
			  try (Response response = client.newCall(request).execute()) 
			  {
				  wsResponse = response.body().string();
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			  }
			  return wsResponse;
		  }
		
		  public String getWithAuthentication(Connection db, int userId, String username, String password, String token)  
		  {
			  
			  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI ### ");

			  String wsResponse = "";
			  System.out.println("\n [*** wsPost ***] URL :\n" + url);
			  System.out.println("\n\n [*** wsPost ***] REQUEST:\n" + wsRequest);
			  
    		  Request request =  null;
    		  
    		  if (token!=null && !token.equals("")){
    			  System.out.println("\n\n [*** wsPost ***] USO TOKEN:\n" + token);
        		  request = new Request.Builder().url(url+wsRequest).addHeader("Authorization: Bearer ", token).get().build();
    		  }
    		  else {
    			  System.out.println("\n\n [*** wsPost ***] USO BASIC:\n" + username + " " + password);
    			  request =  new Request.Builder().url(url+wsRequest).addHeader("Authorization", Credentials.basic(username, password)).get().build();
    		  }
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
  
			  try (Response response = client.newCall(request).execute()) 
			  {
				  wsResponse = response.body().string();
				  System.out.println("\n\n [*** wsPost ***] RESPONSE:\n" + wsResponse);
				  salvaStorico(db, userId, wsResponse);
			  } 
			  catch (IOException e) 
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
				  salvaStorico(db, userId, "### TENTATIVO DI CHIAMATA AI SERVIZI FALLITO ###: "+e.getStackTrace().toString());
			  }
			  return wsResponse;
		  }
		  
			private void salvaStorico(Connection db, int userId, String wsResponse, String idTabella, String tabella, String esito)  
			{
				PreparedStatement pst;
				try 
				{
			        System.out.println("\n\n [*** wsPost ***] INSERISCO STORICO ");
			
					pst = db.prepareStatement("insert into ws_storico_chiamate(url, request, response, id_utente, data, id_tabella, tabella, esito) values (?, ?, ?, ?, now(), ?,?,?);");
					
					pst.setString(1, url);
					pst.setString(2, wsRequest);
					pst.setString(3, wsResponse);
					pst.setInt(4, userId);
					pst.setString(5, idTabella);
					pst.setString(6, tabella);
					pst.setString(7, esito);
					pst.execute();
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			
			public static boolean getSincronizzato(Connection db, String tabella,String nomeIdTabella, String nomeCampoModified, String idTabella)  
			{
				PreparedStatement pst;
				try 
				{
					pst = db.prepareStatement("select * from sinaaf_is_sincronizzato(?,?,?)");

					pst.setString(1, idTabella);
					pst.setString(2, tabella);
					pst.setString(3, nomeIdTabella);
					
					ResultSet rs = pst.executeQuery();
					if(rs.next() )
						return rs.getBoolean(1);
					else
						return false;
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			
			
			public void setIdSinaaf(Connection db, String idSinaaf, String idEntita) throws SQLException  
			{
				setIdSinaaf( db,  idSinaaf,  idEntita, "");
			}
			
			public void setIdSinaaf(Connection db, String idSinaaf, String idEntita, String suffisso) throws SQLException  
			{
				PreparedStatement pst;
				pst = db.prepareStatement("update "+ tabella + " set id_sinaaf" + suffisso  + " = ?::integer where " + idTabella + "::text = ?::text ");
				pst.setString(1, idSinaaf);
				pst.setString(2, idEntita);
				pst.execute();
			}
			
			public String getIdSinaaf(Connection db, String idEntita) throws SQLException  
			{
				PreparedStatement pst;
				pst = db.prepareStatement("select coalesce(id_sinaaf,codice_sinaaf) as id_sinaaf from "+ tabella + " where " + idTabella + "::text = ? ");
				pst.setString(1, idEntita);
				ResultSet rs = pst.executeQuery();
				if(rs.next())
				{
					return rs.getString(1);
				}
				return null;
			}
			
			public void setInfoWs(Connection db, String idEntita, String entita)  
			{
				PreparedStatement pst;
				try 
				{
					pst = db.prepareStatement("select * from sinaaf_get_info_ws(?,?)");
					
					pst.setString(1, idEntita);
					pst.setString(2, entita);
					ResultSet rs = pst.executeQuery();
					if(rs.next() )
					{
							propagazioneSinaaf=rs.getBoolean(1);
							nomeWs=(rs.getString(2)==null)?(""):(rs.getString(2).split(";")[0]);
							dbiGetEnvelope=(rs.getString(3)==null)?(""):(rs.getString(3).split(";")[0]);
							idTabella=rs.getString(4);
							tabella=rs.getString(5); 
							dipendenze=rs.getString(6); 
							nomeCampoIdSinaaf=(rs.getString(7)==null)?(""):(rs.getString(7).split(";")[0]);
							presenteInGisa=rs.getString(8);
							sincronizzato=rs.getBoolean(9);
							idSinaaf = rs.getString(10);
							
							if(rs.getString(2)!=null && rs.getString(2).split(";").length>1)
							{
								wsPostSecondario = new WsPost();
								wsPostSecondario.propagazioneSinaaf=propagazioneSinaaf;
								wsPostSecondario.nomeWs=(rs.getString(2)==null)?(""):(rs.getString(2).split(";")[1]);
								wsPostSecondario.dbiGetEnvelope=(rs.getString(3)==null)?(""):(rs.getString(3).split(";")[1]);
								wsPostSecondario.idTabella=idTabella;
								wsPostSecondario.tabella=tabella; 
								wsPostSecondario.dipendenze=dipendenze; 
								wsPostSecondario.nomeCampoIdSinaaf=(rs.getString(7)==null)?(""):(rs.getString(7).split(";")[1]);
								wsPostSecondario.presenteInGisa=presenteInGisa;
								wsPostSecondario.sincronizzato=sincronizzato;
								wsPostSecondario.idSinaaf = idSinaaf;
							}
					}
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			private void setEndpointInfo(Connection db, int idEndpoint)  
			{
				PreparedStatement pst;
				java.sql.ResultSet rs;
				try 
				{
					pst = db.prepareStatement("select * from ws_get_endpoint_info(?,?);");
					
					pst.setInt(1, idEndpoint);
					pst.setString(2, ambiente);
					rs = pst.executeQuery();
					if(rs.next())
					{
						url = rs.getString("url");
						username = rs.getString("username");
						password = rs.getString("password");
						ruolo = rs.getString("ruolo");
						ruoloValoreCodice = rs.getString("ruolo_valore_codice");
						ruoloCodice = rs.getString("ruolo_codice");
						xmlns = rs.getString("xmlns");
						prefissoUsernamePassword = rs.getString("prefisso_username_password");
						suffissoAutenticazione = rs.getString("suffisso_autenticazione");
						this.idEndpoint = rs.getInt("id_endpoint");
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			private void setServizioInfo(Connection db,int idAzione)  
			{
				PreparedStatement pst;
				java.sql.ResultSet rs;
				try 
				{
					pst = db.prepareStatement("select * from ws_get_servizio_info(?,?);");
					
					pst.setInt(1, idEndpoint);
					pst.setInt(2, idAzione);
					rs = pst.executeQuery();
					while(rs.next())
					{
						nomeServizio = rs.getString("nome_servizio");
						nomeOggetto = rs.getString("nome_oggetto");
						campiOggetto.put(rs.getString("nome_campo"), rs.getString("nome_campo"));
						obbligatorioCampo.put(rs.getString("nome_campo"), rs.getBoolean("obbligatorio_campo"));
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			
			//tipoAutorizzazione serve a costruire l'header di autorizzazione e autenticazione nell'header dell'envelope a seconda del ws da chiamare
			//Vedi dbi ws_get_envelope per i dettagli
			public void costruisciEnvelope(Connection db,String tipoAutorizzazione)
			{
				setTipoAutorizzazione(tipoAutorizzazione);
				costruisciEnvelope(db);
			}
			
			
			public void costruisciEnvelope(Connection db)
			{
				Iterator<String> campi = campiOggetto.keySet().iterator();
				
				String campiEnvelope = "";
				while(campi.hasNext())
				{
					String nomeCampo = campi.next();
					Object valoreCampo = campiInput.get(nomeCampo);
					Boolean obbligatorietaCampo = obbligatorioCampo.get(nomeCampo);
					if((valoreCampo!=null && !valoreCampo.equals(""))  || obbligatorietaCampo)
						campiEnvelope += "<" + nomeCampo + ">" + ((valoreCampo!=null)?(valoreCampo):("")) + "</" + nomeCampo + ">";
				}
				

				PreparedStatement pst;
				java.sql.ResultSet rs;
				try 
				{
					pst = db.prepareStatement("select * from ws_get_envelope(?,?,?,?,?,?,?,?,?,?,?)");
					
					
					pst.setString(1, xmlns);
					pst.setString(2, (ruolo!=null)?(ruolo):(""));
					pst.setString(3, username);
					pst.setString(4, password);
					pst.setString(5, ruoloCodice);
					pst.setString(6, ruoloValoreCodice);
					pst.setString(7, nomeServizio);
					pst.setString(8, nomeOggetto);
					pst.setString(9, campiEnvelope);
					pst.setString(10, suffissoAutenticazione);
					pst.setString(11, prefissoUsernamePassword);
					rs = pst.executeQuery();
					while(rs.next())
					{
						wsRequest = rs.getString(1);
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			
			}
			
			
			
			
			public String getToken() throws SQLException, IOException 
			{
				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
				
				String username = ApplicationProperties.getProperty("USERNAME_WS_SINAAF");
				String password = ApplicationProperties.getProperty("PASSWORD_WS_SINAAF");
				String url = ApplicationProperties.getProperty("END_POINT_SINAAF_TOKEN");
				String authorization = ApplicationProperties.getProperty("AUTHORIZATION_METHOD_SINAAF");
				
				RequestBody body = RequestBody.create(mediaType, "grant_type=password&username="+username+"&password="+password);
				Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("authorization", "Basic " + authorization)
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("cache-control", "no-cache")
				.build();
				Response response = client.newCall(request).execute();
				
				int code = response.code();
				String token = null;
				if(code==200)
				{
					String result = response.body().string();
				    JsonParser parser = new JsonParser();  
				    JsonObject json = (JsonObject) parser.parse(result);  
				    token = json.get("access_token").toString();
				    token=token.replaceAll("\"", "");
				    System.out.println("Token: " + token);
				}
				else
				{
					String result = response.body().string();
				    JsonParser parser = new JsonParser();  
				    JsonObject json = (JsonObject) parser.parse(result);  
				    String errore = json.get("error").toString();
				    String erroreDescrizione = json.get("error_description").toString();
				    System.out.println("Errore: " + errore);
				    System.out.println("ErroreDescrizione: " + erroreDescrizione);
					//throw new Exception("Errore nella richiesta del token. Errore: " + errore + ". ErroreDescrizione: " + erroreDescrizione);
				}
				
				
				return token;
			}
			
			
			
}
