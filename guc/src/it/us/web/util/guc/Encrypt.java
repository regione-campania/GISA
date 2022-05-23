/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class Encrypt {
	
	public static String encrypted(String input,byte[] sharedKey)
	{
		 byte[] crypted = null;
		  try{
		    //SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			  SecretKeySpec skey = getKeySpecByString(sharedKey);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		      cipher.init(Cipher.ENCRYPT_MODE, skey);
		      crypted = cipher.doFinal(input.getBytes());
		    }catch(Exception e){
		    	System.out.println(e.toString());
		    }
		  String crypted_token= new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));
		  return crypted_token ;
	}
	
	public static  String decrypt(String input, byte[] preSharedKey){
		byte[] output = null;
	    try{
	      //SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	    	SecretKeySpec skey = getKeySpecByString(preSharedKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      output = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(input.replaceAll(" ","+" ).getBytes()));
	    }catch(Exception e){
	      System.out.println(e.toString());
	    }  
	    return new String(output);
	    }
	
	
	public static SecretKeySpec getKeySpecByString(byte[] preSharedKey) throws IOException, NoSuchAlgorithmException {
		

		SecretKeySpec spec = null;
		spec = new SecretKeySpec(preSharedKey, "AES");
		return spec;
	}
	
	public static boolean validaSharedKey(String parametro,String sharedKey)
	{
		
		if (sharedKey.length()==16 || sharedKey.length()==32)
		
		
		try
		{
			
			String crypt ="" ; // encrypted(parametro,sharedKey.getBytes() );
		
			if (sharedKey.length()==32) // caso esadecimale
				crypt = encrypted(parametro,lenientHexToBytes(sharedKey) );
			else
				crypt = encrypted(parametro,sharedKey.getBytes());
			
			System.out.println("Valore cruptato : "+crypt);
			String cedrypted = "" ;
			if (sharedKey.length()==32) // caso esadecimale
				cedrypted = decrypt(crypt,lenientHexToBytes(sharedKey) );
			else
				cedrypted = decrypt(crypt,sharedKey.getBytes());
			System.out.println("Valore decruptato : "+cedrypted);
			
			if (parametro.equalsIgnoreCase(cedrypted))
				return true ;
			return false;
		}
		catch(Exception e)
		{
			return false ;
		}
		return false ;
		
	}
	
	public static byte[] lenientHexToBytes(String hex) {
		byte[] result = null;
		if (hex != null) {
			result = new byte[hex.length() / 2];
			for (int i = 0; i < result.length; i++) {
				result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
			}
		}

		return result;
	}
	
	
	

	public static void main(String[] args)
	{
		
		
		
		validaSharedKey("ciaooooooooo", "pippopippopippop");
		
	}
	


}
