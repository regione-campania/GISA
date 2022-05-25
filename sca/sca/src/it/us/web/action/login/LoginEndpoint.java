/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import configurazione.centralizzata.nuova.gestione.ClientServizioCentralizzato;
import crypto.nuova.gestione.ClientSCAAesServlet;
import crypto.nuova.gestione.SCAAesServlet;
import sun.misc.BASE64Encoder;
import it.us.web.action.GenericAction;


import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;
import it.us.web.util.LoginUtil;
import it.us.web.util.guc.DbUtil;

public class LoginEndpoint extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	@SuppressWarnings("unchecked") 
	@Override
	public void execute() throws Exception
	{
		System.out.println("LOG LOGINENDPOINT:  = ");
		System.out.println("utenteGuc = " + ((utenteGuc==null)?(""):(utenteGuc)));
		
		//if(false)
		if( utenteGuc == null)
		{
			setErrore("Sessione non piu' valida");
			//gotoPage("/jsp/sca/index.jsp" );
			if(Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
				gotoPage("/indexSpidVersion.jsp" );
			else
				gotoPage("/index.jsp" );
		}
		else{
			
			/*VECCHIA GESTIONE */
//			 URL in = new URL("http://localhost/sysinfo.json"); /* TODO RIMETTI */
////			URL in = new URL("http://sca.gisacampania.it/sysinfo.json"); /*TODO COMMENTA */
//			  System.out.println("******STAMPO LA URL DEL FILE SYSINFO********: "+in);
//		      BufferedReader reader = new BufferedReader(
//		      new InputStreamReader(in.openStream()));
//		      StringBuilder out = new StringBuilder();
//	          String line;
//	          while ((line = reader.readLine()) != null) 
//	            out.append(line);
//		        reader.close();
//		    
//			JSONObject jsonObject = new JSONObject(out.toString());
//		    /*String gisa = (String) jsonObject.get("gisa");
//		    String bdu = (String) jsonObject.get("bdu");
//		    String gisa_ext = (String) jsonObject.get("gisa_ext");
//		    String bdu_ext = (String) jsonObject.get("bdu_ext");
//		    String vam = (String) jsonObject.get("vam");
//		    String digemon = (String) jsonObject.get("digemon");
//		    String sca = (String) jsonObject.get("sca");
//		    String open = (String) jsonObject.get("open");*/
		    
			/*NUOVA GESTIONE */
			ClientServizioCentralizzato sclient = new ClientServizioCentralizzato();
			JSONObject mappaEndPoints = sclient.getMappaEndpointsSca();
			
			/*System.out.println("(SCA) MAPPA ENDPOINTS TARGET RICEVUTA DAL SERVIZIO CENTRALIZZATO CONFIGURAZIONE-----------------------");
			Iterator it =mappaEndPoints.keys();
			while(it.hasNext())
			{
				String key = (String)it.next();
				System.out.println("endpoint :"+key+" -> "+mappaEndPoints.getString(key));
			}
			System.out.println("----------------------------------------------");
			*/
			
			//Modifica del 04/03
//			String indirizzoDiRitorno = (String)session.getAttribute("urlInvocatoDaUtente");
	 		//String originalToken = System.currentTimeMillis() + "@"+utenteGuc.getUsername()+"@"+ mappaEndPoints.getString("sca");
			String endpointSca = mappaEndPoints.getString("sca");
			System.out.println("endpointSca = " + endpointSca);
			if(false)
				endpointSca = req.getRequestURL().toString();
			
			String[] endpointScaArray = endpointSca.split("/");
			System.out.println("endpointScaArray = " + endpointScaArray);
			String ultimaParte = endpointScaArray[endpointScaArray.length-1];
			System.out.println("ultimaParte = " + ultimaParte);
			if(false)
				endpointSca = endpointSca.replace("/"+ultimaParte, "");
			else if(req.getRequestURL().toString().contains("/login/"))
			{
				//endpointSca = endpointSca.replace("/"+ultimaParte, "/login/");
				endpointSca = endpointSca.substring(0, endpointSca.length()-4) + "/login/";
				System.out.println("endpointSca = " + endpointSca);
			}
			//endpointSca = endpointSca.replaceAll("https://", "");
			endpointSca = endpointSca.replaceAll("http://", "");
			endpointSca = endpointSca.replaceAll("//", "/");
			System.out.println("endpointSca dopo i replaceAll = " + endpointSca);
			String originalToken = System.currentTimeMillis() + "@"+ stringaFromRequest("username") +"@"+endpointSca;
			System.out.println("originalToken = " + originalToken);
	 		//String originalToken = System.currentTimeMillis() + "@"+utenteGuc.getUsername()+"@sirv";
			String id = stringaFromRequest("endpoint").toLowerCase();
			String goTo = mappaEndPoints.getString(id);
			
			String encryptedToken = null ;
	
			//encryptedToken =  encrypt(originalToken,this.getClass().getResource("aes_key"));
			
			
			
			
			
			
			
			/*Modifica 23/03/2017 test nuova versione */
			//encryptedToken = NEWencrypt(originalToken,this.getClass().getResource("aes_key"));
			  
			 /********************************************/
			 /*String path = this.getClass().getResource("aes_key2").getPath().replaceAll("%20", " ");
			 FileInputStream fis = new FileInputStream(new File(path));
			 byte[] buff = new byte[1024];
			int r = fis.read(buff);
			 String key = new String(buff,0,r);*/
			  try
			  {
				  /*ClientCryptDecryptPerJava cclient = new ClientCryptDecryptPerJava("http://localhost:8090/gisa_nt/cryptoServlet");
				  encryptedToken = cclient.crypt(key, originalToken); */
				  String key = new ClientSCAAesServlet().getKey(); /*la leggo da file*/
				  /*faccio encrypt usando direttamente il metodo, senza richiamare servlet */
				  ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				  encryptedToken = cclient.crypt(originalToken);
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			  //fis.close();
			  /********************************************/
			
			System.out.println("token "+encryptedToken);
			
			/*nuova gestione senza lo switch */
			String goToCompleteUrl =
					/*"http://"+*/goTo+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8");
			
			System.out.println("TEST LOGIN END POINT DA SCA >>>>>>>>>>>>>>>>>>> "+goToCompleteUrl);
			/*attenzione che se si va verso VAM, non esiste realmente una Login.do (poiche' e' struts) ma c'e' una servlet che mappa li nel 
			 * web.xml
			 */
			
			try {
				System.out.println("scrivo log login");
				LoginUtil.gestioneLogLogin(db, utenteGuc.getUsername(), utenteGuc.getCodiceFiscale(), Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")), id);
				System.out.println("scritto log login");
			}catch(Exception e){
				e.printStackTrace();
			}
			finally {
				DbUtil.chiudiConnessioneJDBC(null, db);
			}
			
			//redirectTo(goToCompleteUrl);
			req.setAttribute("url", goToCompleteUrl);
			gotoPage("/jsp/sca/redirect.jsp" ); 
			 
			/* vecchia gestione degli url negli end points 
			 
			if(id.equals("Gisa")) {
				System.out.println("redirect to "+"http://"+gisa+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_GISA")+"/gisa_nt/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://srv.gisacampania.it/gisa_nt/Login.do?command=LoginNoPassword&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+gisa+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
				}
			else if(id.equals("Gisa_ext")) {
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_GISA_EXT")+"/gisa_ext/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://srv.gisacampania.it/gisa_ext/Login.do?command=LoginNoPassword&encryptedToken="+(encryptedToken));
				
				//redirectTo( "http://"+gisa_ext+"/Login.do?command=LoginNoPassword&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+gisa_ext+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
				}
			//Se vam
			else if(id.equals("Vam")){
				//redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_BDU")+"/vam/login.LoginNoPassword.us?system=vam&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://"+InetAddress.getByName("srvVAMW").getHostAddress()+"/vam/login.LoginNoPassword.us?system=vam&encryptedToken="+(encryptedToken));
				//redirectTo( "http://vam.anagrafecaninacampania.it/vam/login.LoginNoPassword.us?system=vam&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+vam+"/login.LoginNoPassword.us?system=vam&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));

			}//Se gisaReport
			else if(id.equals("bdu")){
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_BDU")+"/bdu/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://srv.anagrafecaninacampania.it/bdu/Login.do?command=LoginNoPassword&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+bdu+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));

				}//Se gisaReport
			else if(id.equals("Digemon") ){
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_REPORT")+"/DiGeMon/Login.do?command=LoginNoPassword&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+digemon+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(asHex(encrypt(originalToken,this.getClass().getResource("aes_key"))),"UTF-8"));

				}//Se caninaReport
			else if(id.equals("Importatori")){
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_BDU")+"/bdu_ext/login.LoginNoPassword.us?system=vam&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://"+InetAddress.getByName("srvBDUW").getHostAddress()+"/bdu_ext/login.LoginNoPassword.us?system=vam&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+bdu_ext+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
				}//Se gisaReport
			
			else if(id.equalsIgnoreCase("open")){
				  //redirectTo( "http://"+ApplicationProperties.getProperty("AMBIENTE_SCA_BDU")+"/bdu_ext/login.LoginNoPassword.us?system=vam&encryptedToken="+asHex(encryptedToken));
				//redirectTo( "http://"+InetAddress.getByName("srvBDUW").getHostAddress()+"/bdu_ext/login.LoginNoPassword.us?system=vam&encryptedToken="+(encryptedToken));
				redirectTo( "http://"+open+"/Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));
				}//Se gisaReport
			*/
			
		}
		  
		
	  }
		
	
	public static String asHex(byte buf[]) {
        StringBuffer sb = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                sb.append("0");
            }

            sb.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return sb.toString();
    }

  public static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
	    byte[] bytes = new byte[16];
	    
	    File f = new File(path.replaceAll("%20", " "));
	    
	    SecretKeySpec spec = null;
	    if (f.exists()) 
	    {
	      new FileInputStream(f).read(bytes);
	      
	    } else {
	      /* KeyGenerator kgen = KeyGenerator.getInstance("AES");
	       kgen.init(128);
	       key = kgen.generateKey();
	       bytes = key.getEncoded();
	       new FileOutputStream(f).write(bytes);*/
	    	throw new FileAesKeyException("File aes_key not found");
	    	
	    }
	    spec = new SecretKeySpec(bytes,"AES");
	    return spec;
	  }
	  public static byte[] encrypt(String text,URL url) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		  
		  if(url ==null)
		  {
			  throw new FileAesKeyException("File aes_key not found");
		  }
		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.ENCRYPT_MODE, spec);
		  BASE64Encoder enc = new BASE64Encoder();
	    
	    return enc.encode(cipher.doFinal(text.getBytes())).getBytes() ;
	  }
	
		public static  String NEWencrypt(String input, URL url){
			byte[] crypted = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = getKeySpec(url.getPath());
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, skey);
				crypted = cipher.doFinal(input.getBytes());
			}catch(Exception e){
				System.out.println(e.toString());
			}
			return new String(Base64.encodeBase64(crypted));
		}

		
		
}
