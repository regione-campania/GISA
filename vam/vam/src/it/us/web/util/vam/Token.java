/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import it.us.web.util.properties.Application;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import crypto.nuova.gestione.ClientSCAAesServlet;
import sun.misc.BASE64Encoder;

public class Token
{
	
	
	  public static  String NEWencrypt(String input, String string){
			byte[] crypted = null;
			try{
				//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
				SecretKeySpec skey = new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
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
			KeyGenerator	kgen			= null;
			
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


	
	public static String generate( String username )
	{
		String ret = null;

		String			originalToken	= System.currentTimeMillis() + "@" + username;
		
		String encryptedToken = null ;
		/*vecchia gestione
		encryptedToken = NEWencrypt(originalToken,"");//this.getClass().getResource("aes_key"));
		*/
		
		try
		{
			ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			encryptedToken = cclient.crypt(originalToken);
			ret = "&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ATTENZIONE GENERAZIONE TOKEN FALLITA.");
			
		}
		/*
		byte[]			encryptedToken	= null;
		KeyGenerator	kgen			= null;
		
		try
		{
			kgen						= KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKeySpec	skeySpec	= new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
			Cipher 			cipher		= Cipher.getInstance("AES");
			cipher.init( Cipher.ENCRYPT_MODE, skeySpec );
			BASE64Encoder enc = new BASE64Encoder();
			encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes() ;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
  */

		return ret;
	}
	
	
	public static String generateOnlyToken( String username )
	{
		String ret = null;

		String			originalToken	= System.currentTimeMillis() + "@" + username;
		
		String encryptedToken = null ;
		/*vecchia gestione
		encryptedToken = NEWencrypt(originalToken,"");//this.getClass().getResource("aes_key"));
		*/
		
		try
		{
			ClientSCAAesServlet cclient = new ClientSCAAesServlet();
			encryptedToken = cclient.crypt(originalToken);
			ret = URLEncoder.encode(encryptedToken,"UTF-8");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("ATTENZIONE GENERAZIONE TOKEN FALLITA.");
			
		}
		/*
		byte[]			encryptedToken	= null;
		KeyGenerator	kgen			= null;
		
		try
		{
			kgen						= KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKeySpec	skeySpec	= new SecretKeySpec(asBytes( Application.get( "KEY" ) ), "AES");
			Cipher 			cipher		= Cipher.getInstance("AES");
			cipher.init( Cipher.ENCRYPT_MODE, skeySpec );
			BASE64Encoder enc = new BASE64Encoder();
			encryptedToken = enc.encode(cipher.doFinal(originalToken.getBytes())).getBytes() ;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
  */

		return ret;
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

	public static void main(String[] args)
	{
		String tokenChiaro = System.currentTimeMillis() + "@" + "s.squitieri";
		System.out.println( "TOKEN chiaro: " + tokenChiaro );
		System.out.println( "TOKEN: " + generate( tokenChiaro ) );
	}
	
}
