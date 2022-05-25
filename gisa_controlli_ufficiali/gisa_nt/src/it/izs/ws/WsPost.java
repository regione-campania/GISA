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
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import okhttp3.Call;
import okhttp3.Credentials;
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
		  
		  private int TIMEOUT_SECONDS = 60 ;
		  
		  private String url = "";
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
		  
		  public WsPost()
		  {
			  
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
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).build();
			  
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
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).build();
  
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
    		  
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).build();
  
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
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).build();
		
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
		
			  OkHttpClient client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS).build();
  
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

			public int getTIMEOUT_SECONDS() {
				return TIMEOUT_SECONDS;
			}

			public void setTIMEOUT_SECONDS(int tIMEOUT_SECONDS) {
				TIMEOUT_SECONDS = tIMEOUT_SECONDS;
			}
			
			
			
}
