/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package crypto.nuova.gestione;

import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SCAAesServlet extends HttpServlet{

	
	/*
	 * metodo che risponde in json, e permette 3 operazioni : 
	 * CRYPT / DECRYPT/ CHECK VALIDITA CHIAVE, rispetto all'uso di AES con chiave da 16 byte
	 * che deve essere fornita come stringa di 32 chars esadecimali 
	 * 
	 * prende in input:
	 * 		operation : decrypt/encrypt/checkValiditaChiave
	 * 		key : 		chiave (deve essere formata da 32 chars esadecimali)
	 * 		toEncode :  cosa cryptare/decryptare
	 * 
	 * ritorna 
	 * 		status : 0 SE OK, -1 SE QUALCOSA NON E' ANDATO BENE
	 * 		statusMsg : DESCRIZIONE DELLO STATO
	 * 		output : OUTPUT DELL'OP RICHIESTA
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String operazione = req.getParameter("operation");
		String keyStr = req.getParameter("key");
		String dataStr = req.getParameter("toEncode");
		
		
		JSONObject respJson = new JSONObject();
		resp.setContentType("application/json");
		
		String statusCode = null;
		String output = null;
		String statusMsg = null;
		try
		{
			if(operazione.equalsIgnoreCase("encrypt"))
			{
				output = encrypt(dataStr,keyStr);
			}
			else if(operazione.equalsIgnoreCase("decrypt"))
			{
				output = decrypt(dataStr, keyStr);
			}
			else if(operazione.equalsIgnoreCase("checkValiditaChiave"))
			{
				try
				{
					generateKey(keyStr);
					output = "valid key";
				}
				catch(InvalidKey ex)
				{
					output = "invalid key";
				}
			}
			else
			{
				throw new Exception("Operazione non supportata");
			}
				
			
			statusCode = "0";
			statusMsg = "Tutto OK";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			statusCode = "-1";
			statusMsg = ex.getMessage();
			output = "";
		}
		 
		
		respJson.put("status", statusCode);
		respJson.put("output", output);
		respJson.put("statusMsg", statusMsg);
		resp.getWriter().println(respJson.toString());
		
	}
	
	
	
	public static String encrypt(String valueToEnc, String keyStr) throws Exception {
		Key key = generateKey(keyStr);
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encValue = c.doFinal(valueToEnc.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encValue);
		return encryptedValue;
	}

	public static String decrypt(String encryptedValue, String keyStr) throws Exception {
		Key key = generateKey(keyStr);
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}



	private static Key generateKey(String keyStr) throws InvalidKey {
		/*hex to decimal 
		 * stringa di 32 chars, interpretati a 2 a 2 come esadecimali, ciascuno diventa quindi un intero tra 0 e 255, salvato in un buff (16 in totale)
		 * */
		byte[] buff0 = new byte[16];
		if(keyStr.length() != 32)
		{
			throw new InvalidKey("Chiave invalida -> Deve essere di 32 cifre esadecimali");
		}
		for(int i = 0; i< buff0.length; i++)
		{
			String tHex = keyStr.substring(2 * i, 2*i+2);
			try
			{
				Integer tHexI = Integer.parseInt(tHex,16);
				buff0[i] = (byte)tHexI.intValue();
			}
			catch(Exception ex)
			{
				throw new InvalidKey(tHex+" non e' riconosciuto come esadecimale corretto");
			}

		}

		Key key = new SecretKeySpec(buff0, "AES");
		return key;
	}
	
}
