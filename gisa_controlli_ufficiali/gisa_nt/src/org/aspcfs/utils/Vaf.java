/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.framework.actions.ActionContext;

import crypto.nuova.gestione.ClientSCAAesServlet;

public class Vaf extends CFSModule
{
	
	
	  public static  String NEWencrypt(String input, String string){
			byte[] crypted = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = new SecretKeySpec(asBytes(  ApplicationProperties.getProperty("key") ), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, skey);
				crypted = cipher.doFinal(input.getBytes());
			}catch(Exception e){
				System.out.println(e.toString());
			}
			return new String(Base64.encodeBase64(crypted));
		}
	  
	  
		public static String generateOLD( String username )
		{
			String ret = null;

			String			originalToken	= System.currentTimeMillis() + "@" + username;
//			byte[]			encryptedToken	= null;
			String encryptedToken = null; 
			
			try
			{
				/*vecchia gestione
				kgen						= KeyGenerator.getInstance("AES");
				kgen.init(128);
				SecretKeySpec	skeySpec	= new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
				Cipher 			cipher		= Cipher.getInstance("AES");
				cipher.init( Cipher.ENCRYPT_MODE, skeySpec );
				BASE64Encoder enc = new BASE64Encoder();
				encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes() ;
				*/
				ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				encryptedToken = URLEncoder.encode(cclient.crypt(originalToken),"UTF-8");
				
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
	  
			/*
			 * vecchia gestione
			ret = "&encryptedToken="+asHex(encryptedToken);
			*/
			ret = "&encryptedToken="+encryptedToken;
			
			return ret;
		}


	
	public static String executeCommandLoginVaf( ActionContext context )  throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, FileAesKeyException, Exception
	{
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		  /**COSTRUZIONE DEL TOKEN**/
		String	originalToken	= System.currentTimeMillis() + "@" + user.getUsername(); 
		String encryptedToken = null ;
		try
		{
			ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			encryptedToken =  cclient.crypt(originalToken);
			
			String action = context.getRequest().getParameter("action"); //modifica suggerita da s.squitieri: passare direttamente la action come parametro
			
			String url = "/vaf/login.LoginNoPassword.us?template=popup&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8")+"&action="+action;
			context.getRequest().setAttribute("urlVaf",url);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ATTENZIONE GENERAZIONE TOKEN FALLITA.");
			
		}
		
		return "embeddedVAF" ;
	}
	
	
	public static String executeCommandNosciaVaf( ActionContext context )  throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, FileAesKeyException, Exception
	{
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		  /**COSTRUZIONE DEL TOKEN**/
		String	originalToken	= System.currentTimeMillis() + "@" + user.getUsername(); 
		String encryptedToken = null ;
		try
		{
			ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			encryptedToken =  cclient.crypt(originalToken);
			
		    String tipoNoScia = context.getRequest().getParameter("tipoNoScia");  
			String url =	"/vaf/login.LoginNoPassword.us?template=popup&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8")+"&action=noscia.generic.ToAdd.us?CodAttivita="+tipoNoScia;
																																										
			context.getRequest().setAttribute("urlVaf",url);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ATTENZIONE GENERAZIONE TOKEN FALLITA.");
			
		}
		
		return "nosciaVAF" ;
	}
	
	public static String asHex( byte buf[] )
	{
		StringBuffer sb = new StringBuffer(buf.length * 2);
		for( int i = 0; i < buf.length; i++ )
		{
			if( ((int) buf[i] & 0xff) < 0x10 )
			{
				sb.append("0");
			}
			sb.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		
		return sb.toString();
	}
	
    public static byte[] asBytes (String s)
    {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++)
        {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }

}
