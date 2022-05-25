/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.webgis.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.FileAesKeyException;

import com.darkhorseventures.framework.actions.ActionContext;

import crypto.nuova.gestione.ClientSCAAesServlet;

public class Webgis extends CFSModule{
	public String executeCommandLoginWebgis(ActionContext context) throws IOException 
	  {
		  UserBean user = (UserBean)context.getSession().getAttribute("User");
		  /**COSTRUZIONE DEL TOKEN**/		  
		  
		  String destinazione = (context.getRequest().getParameter("destinazione")!=null ? context.getRequest().getParameter("destinazione") : "");
		  
		  String originalToken = System.currentTimeMillis() + "@"+user.getUsername();//+"@"+context.getServletContext().getServerInfo()+"@gisa";
		  
		  System.out.println("#### TOKEN COLLEGAMENTO A WEBGIS: "+originalToken);
		  

		  try {
			  
			  String porta = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("WEBGIS_PORT"); //":8080";
			  System.out.println("REDIRECT A WEBGIS SU PORTA: "+porta);

			  
			  ClientSCAAesServlet cclient = new ClientSCAAesServlet();			 
			  String encryptedToken = cclient.crypt(originalToken);
			  System.out.println("###encryptedToken: "+encryptedToken);
			  
			  //String redirectTo =  "http://srvgisal:8081"
			  String redirectTo =  "http://"+InetAddress.getByName("srvWEBGISW").getHostAddress() + porta
			  		+ "/webgisdesktop/LoginNoPassword.us?encryptedToken=" 
			  		+ URLEncoder.encode(encryptedToken,"UTF-8");
			  context.getResponse().sendRedirect( redirectTo);
//			  context.getResponse().sendRedirect( "http://172.16.3.194:8080/CanAgr_Priv/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			  
		  }  catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		
		  return "-none-" ;
	  }
	
	public static byte[] encrypt(String text,URL url) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		  
		  if(url ==null)
		  {
			  throw new FileAesKeyException("File aes_key not found");
		  }
		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.ENCRYPT_MODE, spec);
		  sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	    
	    return enc.encode(cipher.doFinal(text.getBytes())).getBytes() ;
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


	
}