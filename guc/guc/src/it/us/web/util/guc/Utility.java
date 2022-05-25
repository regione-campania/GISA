/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.guc;

import it.us.web.exceptions.FileAesKeyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Utility {
	
	public static Utility getInstance(){
		return new Utility();
	}
	
	public String encrypt(String text) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		URL url = this.getClass().getResource("aes_key");
		
		if(url ==null){
			throw new FileAesKeyException("File aes_key not found");
		}
		
		SecretKeySpec spec = getKeySpec(url.getPath());
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		BASE64Encoder enc = new BASE64Encoder();
	    
	    return asHex(enc.encode(cipher.doFinal(text.getBytes())).getBytes()) ;
	}
	
	private SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
	    byte[] bytes = new byte[16];
	    
	    File f = new File(path.replaceAll("%20", " "));
	    
	    SecretKeySpec spec = null;
	    if (f.exists()) {
	      new FileInputStream(f).read(bytes);
	    } 
	    else{
	    	throw new FileAesKeyException("File aes_key not found");
	    }
	    
	    spec = new SecretKeySpec(bytes,"AES");
	    return spec;
	}
	
	private String asHex(byte buf[]) {
        StringBuffer sb = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                sb.append("0");
            }

            sb.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return sb.toString();
    }
	
	
	public String decrypt(String text) throws Exception {
		
		URL url = this.getClass().getResource("aes_key");
		
		if(url == null){
			throw new FileAesKeyException("File aes_key not found");
		}
		  
		SecretKeySpec spec = getKeySpec(url.getPath());
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		BASE64Decoder dec = new BASE64Decoder();
		
		return (new String(cipher.doFinal(dec.decodeBuffer( new String(lenientHexToBytes(text)) ))));
	}
	
	private byte[] lenientHexToBytes(String hex) {
	      byte[] result = null;
	      if(hex != null){
	          result = new byte[hex.length() / 2];
	          for (int i = 0; i < result.length; i++) {
	              result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	          }
	      }
	   
	      return result;
	  }
	
	public static void main(String[] args) throws Exception {
		
		String testo = "323846586355314b58447a654f72317633752f7631413d3d";
		System.out.println( Utility.getInstance().decrypt(testo) );
		
	}

}
