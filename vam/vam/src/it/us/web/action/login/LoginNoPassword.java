/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import crypto.nuova.gestione.ClientSCAAesServlet;
import sun.misc.BASE64Decoder;
import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;
import it.us.web.util.properties.Application;
import it.us.web.util.properties.GenericProperties;

public class LoginNoPassword extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("login");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		String system = (String) session.getAttribute( "system" );
		
		double lat = 0.0;
		double lon = 0.0;
		String errore_geo = "";
		
		if( utente != null )
		{
			Enumeration<String> e = session.getAttributeNames();
			while( e.hasMoreElements() )
			{
				session.removeAttribute( e.nextElement() );
			}
			utente = null;
		}

		session.setAttribute( "system", (system == null) ? ("vam") : (system) );
		
		//TOKEN....
		String[] params = null ;
		String decrypteToken = "" ;
		String encryptedToken = req.getParameter("encryptedToken");
		  
		long loginTime =0;
		try 
		{
			//decrypteToken = decrypt(new String(lenientHexToBytes(encryptedToken)), this.getClass().getResource("aes_key"));
//			 decrypteToken = NEWdecrypt( new String((encryptedToken)), getKeySpecByte(this.getClass().getResource("aes_key").getPath()));
			ClientSCAAesServlet cclient = new ClientSCAAesServlet( );	 
			decrypteToken = cclient.decrypt(  new String((encryptedToken)));
			
			params = decrypteToken.split("@");
			loginTime = Long.parseLong(params[0]);
		} 
		 
		catch(Exception e)
		{
			setErrore("Si è verificato un problema nella decriptazione del token ");
			e.printStackTrace();
			//redirectTo( "Index.us" );
		}

		
		long currTime = System.currentTimeMillis();
			
		if(currTime-loginTime>15*1000*60)
		{
			setErrore("Token non più valido");
			//redirectTo( "Index.us" );
		}
			
		String username = params[1];
		
		session.setAttribute("usernameDaSirv", username);
		
		String ambiente = null;
		if (params.length>2){
			ambiente = params[2];
			System.out.println("ambienteSirv"+ambiente);
			session.setAttribute("ambienteSirv", ambiente);
		}
		//_END TOKEN
		
		//Si è scelto di scollegare l'utente già presente in context
		boolean scollegareUtenteContext = req.getParameter("scollegareUtenteContext")!=null;
		SuperUtente su = null;
		if(scollegareUtenteContext && (BUtente)session.getAttribute("utenteVecchioContext")!=null)
		{
			su = ((BUtente)session.getAttribute("utenteVecchioContext")).getSuperutente();
		}
		else
		{
			lat = doubleFromRequest("access_position_lat");
			lon =  doubleFromRequest("access_position_lon");
			errore_geo = stringaFromRequest("access_position_err");
			su = UtenteDAO.superAuthenticateNoPassword(username, connection);
		}
		
		if(!scollegareUtenteContext)
		{
			if( su == null || su.getUtenti() == null || su.getUtenti().size() == 0 )
			{
				setErrore("Autenticazione fallita");
				String ip = req.getRemoteAddr();
				GenericAction.writeLoginFault(username, ip, "Autenticazione fallita");
				
				redirectTo( "Index.us?entrypointSinantropi=urlDiretto" );
			}
		
			else if( !esisteUtenteContext(su.getUsername()))
			{	
				
				
				/**
				 * GESTIONE GEOLOCALIZZAZIONE
				 */
				su.setAccessPositionErr(errore_geo);
				
				if (lat > 0.0 && lon > 0.0 ){
					su.setAccessPositionLat(lat);
					su.setAccessPositionLon(lon);
					if (errore_geo == null || ("").equals(errore_geo))
						su.setAccessPositionErr("Comunicazione RealTime");
					
				}
				
				//su.setAccessPositionErr(errore_geo);
				getPersistence().update(su);

				
				
				if( su.getUtenti().size() == 1 )
				{	
					BUtente utente = su.getUtenti().get( 0 );
					
					
					
					/**
					 * GESTIONE GEOLOCALIZZAZIONE
					 */
					utente.setAccessPositionErr(errore_geo);
					if (lat > 0.0 && lon > 0.0 ){
						utente.setAccessPositionLat(lat);
						utente.setAccessPositionLon(lon);
						if (errore_geo == null || ("").equals(errore_geo))
							utente.setAccessPositionErr("Comunicazione RealTime");
					}
					
					
					getPersistence().update(utente);
					inserisciUtenteContext(su, session, ambiente); 
					session.setAttribute( "utente", utente );
					//settaSessionFunzioniConcesse(su.getUtenti().get( 0 ) , session);
					redirectTo( "Index.us?entrypointSinantropi=urlDiretto" );
				}
				else
				{
					session.setAttribute( "su", su );
					session.setAttribute("password", stringaFromRequest( "password" ));
					gotoPage( "public", "/jsp/ballot-screen.jsp" );
				}
			}
			else
			{
				try
				{
					session.setAttribute( "utenteVecchioContext", getSessioneUtenteContext(su.getUsername()).getAttribute("utente"));
				}
				catch(IllegalStateException e)
				{
					session.setAttribute( "utenteVecchioContext", null);
				}
				session.setAttribute( "su", su );
				req.setAttribute("utente", username);
				req.setAttribute("password", stringaFromRequest("password"));
				if (su.getUtenti().size()==1){
					gotoPage( "public", "/jsp/gestioneUtenteContext.jsp" );
				} else if (su.getUtenti().size()>1) {
					gotoPage( "public", "/jsp/ballot-screen.jsp" );
				}
			}
		}
		else
		{	
			session.setAttribute( "utenteVecchioContext", null);
			HttpSession sessioneVecchia = (HttpSession)getSessioneUtenteContext(username);
			try
			{
				sessioneVecchia.setAttribute( "utente", null );
				sessioneVecchia.setAttribute("funzioniConcesse", null);
				if(sessioneVecchia.getId()!=session.getId())
					sessioneVecchia.invalidate();
			}
			catch (IllegalStateException e) 
			{
				
			}
			eliminaUtenteContext(username);
			//Devo ricostruire il bean SuperUtente perchè quello in session che si chiama utenteVecchioContext ha la sessione scaduta
			SuperUtente superUtente = UtenteDAO.superAuthenticateNoPassword(username, connection);
			
			if( superUtente.getUtenti().size() == 1 )
			{	
				inserisciUtenteContext(superUtente, session, ambiente); 
				session.setAttribute( "utente", superUtente.getUtenti().get( 0 ) );
				//settaSessionFunzioniConcesse(superUtente.getUtenti().get( 0 ) , session);
				redirectTo( "Index.us?entrypointSinantropi=urlDiretto" );
			}
			else
			{  if (!scollegareUtenteContext) {
					session.setAttribute( "su", superUtente );
					gotoPage( "public", "/jsp/ballot-screen.jsp" );
				} else {
					session.setAttribute( "su", superUtente ); 
					redirectTo( "login.Ballot.us?id="+req.getParameter("id"));
				}
			}
		}
		
		
	}
	
	public static byte[] lenientHexToBytes(String hex) {
	      byte[] result = null;
	      if (hex != null) {
	          // remove all non alphanumeric chars like colons, whitespace, slashes
	          //hex = hex.replaceAll("[^a-zA-Z0-9]", "");
	          // from http://forums.sun.com/thread.jspa?threadID=546486
	          // (using BigInteger to convert to byte array messes up by adding extra 0 if first byte > 7F and this method
	          //  will not rid of leading zeroes like the flawed method 
	          
	          //byte[] bts = new BigInteger(hex, 16).toByteArray();)
	          result = new byte[hex.length() / 2];
	          for (int i = 0; i < result.length; i++) {
	              result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	          }
	      }
	   
	      return result;
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
	  
	  
	  public static String decrypt(String text,URL url) throws Exception {
		  if(url == null)
			  throw new FileAesKeyException("File aes_key not found");

		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.DECRYPT_MODE, spec);
		  BASE64Decoder dec = new BASE64Decoder();
		return (new String(cipher.doFinal(dec.decodeBuffer(text))));
	  }
	
	
		public  String NEWdecrypt(String input, byte[] preSharedKey){
			byte[] output = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = getKeySpecByString(preSharedKey);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, skey);
				output = cipher.doFinal(Base64.decodeBase64(input.replaceAll(" ","+" ).getBytes()));
			}catch(Exception e){
				System.out.println(e.toString());
			}  
			return new String(output);
		}
		
		public static SecretKeySpec getKeySpecByString(byte[] preSharedKey) throws IOException, NoSuchAlgorithmException,
		FileAesKeyException {


			SecretKeySpec spec = null;

			spec = new SecretKeySpec(preSharedKey, "AES");
			return spec;
		}

		public static byte[] getKeySpecByte(String path) throws IOException, NoSuchAlgorithmException,
		FileAesKeyException {
			byte[] bytes = new byte[16];
			File f = new File(path.replaceAll("%20", " "));

			SecretKeySpec spec = null;
			if (f.exists()) {
				new FileInputStream(f).read(bytes);

			} else {
				throw new FileAesKeyException("File aes_key not found");

			}
			
			return bytes;
		}

	
	
	
}
