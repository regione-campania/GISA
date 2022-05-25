/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.FileAesKeyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import crypto.nuova.gestione.ClientSCAAesServlet;
import crypto.nuova.gestione.SCAAesServlet;
import sun.misc.BASE64Decoder;


public class LoginNoPassword extends GenericAction 
{

	@Override
	public void can() throws AuthorizationException
	{
		//BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "ADD", "MAIN" );
		//can( gui, "w" );
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void execute() throws Exception
	{
	
	    String[] params = null ;
	    String decrypteToken = "" ;
	    String encrypteToken = req.getParameter("encryptedToken");

	    long loginTime = 0;
		  try {
			  
			  //decrypteToken = decrypt(new String(lenientHexToBytes(encrypteToken)), this.getClass().getResource("aes_key"));
			  /*vecchia gestione 
			  decrypteToken = NEWdecrypt( new String((encrypteToken)), getKeySpecByte(this.getClass().getResource("aes_key").getPath()));
			  */
			  String key  = new ClientSCAAesServlet().getKey();
			  
			  ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			  decrypteToken = cclient.decrypt(new String(encrypteToken));
			  
			  int index = decrypteToken.indexOf("@");
				System.out.println("Controllo login @: index " + index);
				params = new String[2];
				params[0] = decrypteToken.substring(0,index);
				System.out.println("Controllo login @: params[0] " + params[0]);
				
				//Calcolo numero di @
				int num = 0;
				String toCheck = decrypteToken.substring(index+1,decrypteToken.length());
				System.out.println("Controllo login @: toCheck " + toCheck);
				while(toCheck.indexOf("@")>0)
				{
					toCheck = toCheck.substring(toCheck.indexOf("@")+1,toCheck.length());
					num=num+1;
				}
				System.out.println("Controllo login @: num " + num);
				// Fine //Calcolo numero di @
				
				if(num==0)
				{
					params[1] = decrypteToken.substring(index+1,decrypteToken.length());
					System.out.println("Controllo login @: params[1]  " + params[1] );
				}
				else if(num==1)
				{
					System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length())  " + decrypteToken.substring(index+1,decrypteToken.length()) );
					if(decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1].contains("gisa"))
					{
						params = new String[3];
						System.out.println("Controllo login @: decrypteToken.substring(0,index) " + decrypteToken.substring(0,index) );
						params[0] = decrypteToken.substring(0,index);
						System.out.println("Controllo login @: decrypteToken.length()).split(\"@\")[1]  " + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1]);
						params[2] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1];
						System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[0] " + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] );
						params[1] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0];
					}
					else
					{
						System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()) " + decrypteToken.substring(index+1,decrypteToken.length()) );
						params[1] = decrypteToken.substring(index+1,decrypteToken.length());
					}
						
				}
				else if(num==2)
				{
					params = new String[3];
					System.out.println("Controllo login @: decrypteToken.substring(0,index) " + decrypteToken.substring(0,index));
					params[0] = decrypteToken.substring(0,index);
					System.out.println("Controllo login @: decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[2]" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[2] );
					params[2] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[2];
					System.out.println("Controllo login @:  decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[0] + \"@\" + decrypteToken.substring(index+1,decrypteToken.length()).split(\"@\")[1] " +  decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] + "@" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1]);
					params[1] = decrypteToken.substring(index+1,decrypteToken.length()).split("@")[0] + "@" + decrypteToken.substring(index+1,decrypteToken.length()).split("@")[1];
						
				}
				
			  //params = decrypteToken.split("@");
			  loginTime = Long.parseLong(params[0]);
			
			  
		  }catch(Exception e){
			  req.setAttribute("dettagli_problema", "Si e verificato un problema nella decriptazione del token ");
			  e.printStackTrace();
		  }
		 String username = params[1];
	    
		// long loginTime = Long.parseLong(params[0]);
		 long currTime = System.currentTimeMillis();
		
		 if(currTime-loginTime>15*1000*60)
		 {
			 req.setAttribute("esitoLogin", "Token non piu valido");
			 res.sendRedirect("login.jsp");
		 }else
		 {  
			 
			String flag="access";
			HttpSession session = req.getSession();
			session.setAttribute("userid", username);
			session.setAttribute("flag_open", flag);

	  		RequestDispatcher dispatcher;
	  		//dispatcher = context.getRequestDispatcher("/OrsaServlet?action=report&id_rep="+req.getParameter("id_rep"));
	  		dispatcher = context.getRequestDispatcher("/EndpointSca.us?username="+username);
			dispatcher.forward(req,res);
		 }
	      
	  
	}
	
	
	private static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
        byte[] bytes = new byte[16];
        File f = new File(path.replaceAll("%20", " "));

        SecretKeySpec spec = null;
        if (f.exists())
        {
          new FileInputStream(f).read(bytes);

        } else {
            throw new FileAesKeyException("File aes_key not found");

        }
        spec = new SecretKeySpec(bytes,"AES");
        return spec;
      }


	private static byte[] lenientHexToBytes(String hex) {
	  byte[] result = null;
	  if (hex != null) {
		  result = new byte[hex.length() / 2];
	      for (int i = 0; i < result.length; i++) {
	          result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	      }
	  }

	  return result;
	}
	
	private static String decrypt(String text,URL url) throws Exception {
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

	public static SecretKeySpec getKeySpecByString(byte[] preSharedKey) throws IOException, NoSuchAlgorithmException,
	FileAesKeyException {


		SecretKeySpec spec = null;

		spec = new SecretKeySpec(preSharedKey, "AES");
		return spec;
	}



	
}