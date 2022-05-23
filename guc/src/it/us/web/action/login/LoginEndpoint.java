/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;



import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.action.IndexGUC;
import it.us.web.bean.guc.Utente;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;


import sun.misc.BASE64Encoder;


public class LoginEndpoint extends GenericAction {

	@Override
	public void can() throws AuthorizationException
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		//System.out.println("Sono entrata nel LoginEndpoint");
		
		String originalToken = System.currentTimeMillis() + "@"+utenteGuc.getUsername();
		String id = stringaFromRequest("endpoint");
		byte[] encryptedToken = null ;

		try {
			encryptedToken =  encrypt(originalToken,this.getClass().getResource("aes_key"));
			//context.getResponse.sendRedirect( "http://srv.anagrafecaninacampania.it/canina2/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			
			session.setAttribute( "utenteGuc", null );
			session.invalidate();			
			utenteGuc = null;
			
			//Se l'endpoint scelto e' GISA in locale mettere 8080
			if(id.equals("Gisa")) {
				  redirectTo( "http://"+ApplicationProperties.getProperty("GISA")+"/centric_osa/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			}
			//Se Canina
			else if(id.equals("Canina") ){
				  redirectTo( "http://"+ApplicationProperties.getProperty("CANINA")+"/canina2/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			}//Se felina
			else if(id.equals("Felina") ){
				  redirectTo( "http://"+ApplicationProperties.getProperty("FELINA")+"/felina/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			}//Se vam
			else if(id.equals("Vam")){
				  redirectTo( "http://"+ApplicationProperties.getProperty("VAM")+"/vam/login.LoginNoPassword.us?system=vam&encryptedToken="+asHex(encryptedToken));
			}//Se gisaReport
			else if(id.equals("Digemon") ){
				  redirectTo( "http://"+ApplicationProperties.getProperty("GISA_REPORT")+"/DiGeMon/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			}
			//Se caninaReport
			else if(id.equals("CaninaReport")){
				  redirectTo( "http://"+ApplicationProperties.getProperty("CANINA_REPORT")+"/canina_report/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));
			}
				
		  
		  } catch (NoSuchAlgorithmException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (IllegalBlockSizeException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (BadPaddingException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (InvalidKeyException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (NoSuchPaddingException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  catch (FileAesKeyException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
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
	
		
		
}
