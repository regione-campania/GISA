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

import sun.misc.BASE64Decoder;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;

public class CambioUtente extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "AMMINISTRAZIONE", "CAMBIO UTENTE", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		//setSegnalibroDocumentazione("login");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		String system = (String) session.getAttribute( "system" );
		
				session.setAttribute( "system", (system == null) ? ("vam") : (system) );
		
		String username = stringaFromRequest("username");
		    
		SuperUtente su = UtenteDAO.superAuthenticateNoPassword( username, persistence );
		
		if( su == null || su.getUtenti() == null || su.getUtenti().size() == 0 )
		{
			setErrore("Autenticazione fallita");
			redirectTo( "Index.us" );
		}
		else if( !esisteUtenteContext(su.getUsername()) )
		{	
			utenti.remove(utente.getUsername());
			if( su.getUtenti().size() == 1 )
			{	
				session.setAttribute( "utente", su.getUtenti().get( 0 ) );
				inserisciUtenteContext(su, session,(String)session.getAttribute("ambienteSirv")); 
				//settaSessionFunzioniConcesse(su.getUtenti().get( 0 ) , session);
				redirectTo( "Index.us?entrypointSinantropi=urlDiretto" );
			}
			else
			{
				session.setAttribute( "su", su );
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
			if(username==null || username.equalsIgnoreCase("")|| username.equalsIgnoreCase("null"))
				req.setAttribute("utente", stringaFromRequest("utente"));
			else
				req.setAttribute("utente", username);
			req.setAttribute("password", stringaFromRequest("password"));
			gotoPage( "public", "/jsp/gestioneUtenteContext.jsp" );
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
	
	
	
	
	
	
}
