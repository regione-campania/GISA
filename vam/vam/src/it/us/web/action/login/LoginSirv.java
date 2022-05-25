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
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;









import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;
import it.us.web.util.properties.Application;
import it.us.web.util.vam.Token;

public class LoginSirv  extends GenericAction {

	private String system;


	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
  
		try {  
			
		  //String SIRV_URL = InetAddress.getByName("endpointAPPSirv").getHostAddress();
		  //String SIRV_URL =InetAddress.getByName("srvSCAW").getHostAddress();;// Application.get("ambiente_sirv");
		  String SIRV_URL =(String)session.getAttribute("ambienteSirv");
		  System.out.println("VAM: variabile ambiente vale: "+SIRV_URL);
		  String username = "";
		  
		  System.out.println("##### "+SIRV_URL+" - "+username);
		  
		  if(utente!=null)
			  username = Token.generateOnlyToken(utente.getUsername());
		  else
			  username = Token.generateOnlyToken((String)session.getAttribute("usernameDaSirv"));
		  
		  //Se vieni dal sistema SIRV..
	     
          //context.getResponse().sendRedirect( "http://"+SIRV_URL+"/login.LoginNoPassword.us?encryptedToken="+generate(thisUser.getUsername()));
		  session.invalidate();
		  //redirectTo( /*"http://"+*/SIRV_URL+"/login.LoginNoPassword.us?"+username);
		  
		  
		  String cf_spid = stringaFromRequest("cf_spid");
		  
		  if(false)
		  //if(cf_spid==null || cf_spid.equalsIgnoreCase("") || cf_spid.equalsIgnoreCase("null"))
			{
			  req.setAttribute("url",SIRV_URL+"/login.LoginNoPassword.us?"+username);
			}
			else
			{
				SIRV_URL = SIRV_URL.replace("/sca", "/login");
				SIRV_URL = SIRV_URL.replace("https://login", "https://sca");
				SIRV_URL = SIRV_URL.replace("https:/login", "https://sca");
				req.setAttribute("url",SIRV_URL + "/login.Login.us?cf_spid=" + cf_spid + "&tk_spid=" +  username);
			}
	      gotoPage("public" , "/redirectSca.jsp");
             
        } catch (Exception e) {
                 // TODO: handle exception
         }
         
	}
	
	public LoginSirv (String system) {
		this.system = system;
	}

	@Override
	public void can() throws AuthorizationException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}
	
	 public  String generate( String username )
	    {
	            String ret = null;

	            String                        originalToken        = System.currentTimeMillis() + "@" + username;
	            byte[]                        encryptedToken        = null;
	            KeyGenerator        kgen                        = null;
	            
	            try
	            {
	                    kgen                                                = KeyGenerator.getInstance("AES");
	                    kgen.init(128);
	            //        SecretKeySpec        skeySpec        = new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
	                    SecretKeySpec skeySpec = getKeySpec( this.getClass().getResource("aes_key").getPath());
	                    Cipher                         cipher                = Cipher.getInstance("AES");
	                    cipher.init( Cipher.ENCRYPT_MODE, skeySpec );
	                    BASE64Encoder enc = new BASE64Encoder();
	                    encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes() ;
	            }
	            catch ( Exception e )
	            {
	                    e.printStackTrace();
	            }

	            ret = asHex(encryptedToken);

	            return ret;
	    }
		
		  public static String asHex (byte buf[] )
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
		  
		  @Override
			public void canClinicaCessata() throws AuthorizationException
			{
			}
		
}
