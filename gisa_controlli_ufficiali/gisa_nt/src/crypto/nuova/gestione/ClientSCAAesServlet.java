/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package crypto.nuova.gestione;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONObject;

public class ClientSCAAesServlet {
	
	
	URL urlServletCryptDecrypt ;
	String key;
	
	public ClientSCAAesServlet() throws IOException{
		 
		Properties p = new Properties();
		p.load(this.getClass().getResourceAsStream("client.properties"));
		String key = p.getProperty("key");
		String url = p.getProperty("url");
		
		this.urlServletCryptDecrypt = new URL(url);
		this.key = key;
		
	}
	public ClientSCAAesServlet(String uri, String key) throws MalformedURLException
	{
		
		try
		{
			this.urlServletCryptDecrypt = new URL(uri);
			this.key = key;
			System.out.println(">>>INIZIALIZZO CLIENT SCA AES SERVLET VERSO URL  ->"+this.urlServletCryptDecrypt.toString());
		}
		catch(Exception ex)
		{
			System.out.println("IMPOSSIBILE LEGGERE PROPRIETA \"URL_AESSERVLET\" DA FILE APPLICATION PROPERTIES PER L'AMBIENTE IN USO");
			throw ex;
		}
		
		
		
		
	}
	
	
	
	public boolean checkKey( ) throws IOException, ParseException
	{
		
		
		HashMap<String,String> pars = new HashMap<String,String>();
		pars.put("operation", "checkValiditaChiave");
		pars.put("key", key);
		
		String t = sendPOSTRequest(pars);
		JSONObject jsn = new JSONObject(t);
		System.out.println("-------------------CLIENT CRYPT/DECRYPT ------------> \ninviata richiesta di test chiave: "+key);
		debugPrint(jsn);
		
		
		return jsn.getString("output").split(" ")[0].equalsIgnoreCase("invalid") ? false : true;
		
	}
	
	public String crypt( String msg) throws IOException, ParseException
	{
		String toRet = null;
		
		HashMap<String,String> pars = new HashMap<String,String>();
		pars.put("operation", "encrypt");
		pars.put("key", key);
		pars.put("toEncode", msg);
		
		String t = sendPOSTRequest(pars);
		JSONObject jsn = new JSONObject(t);
		System.out.println("-------------------CLIENT ENCRYPT/DECRYPT ------------> \ninviata richiesta di encrypt con chiave: "+key+", msg: "+msg);
		debugPrint(jsn);
		toRet = jsn.getString("output");
		
		return toRet;
	}
	
	
	
	public String decrypt(  String msg) throws IOException, ParseException
	{
		String toRet = null;
		
		HashMap<String,String> pars = new HashMap<String,String>();
		pars.put("operation", "decrypt");
		pars.put("key", key);
		pars.put("toEncode", msg);
		
		
		String t = sendPOSTRequest(pars);
		JSONObject jsn = new JSONObject(t);
		System.out.println("-------------------CLIENT ENCRYPT/DECRYPT ------------> \ninviata richiesta di decrypt con chiave: "+key+", msg: "+msg);
		debugPrint(jsn);
		toRet = jsn.getString("output");
		
		return toRet;
	}
	
	
	private void debugPrint(JSONObject jsn)
	{
		
		System.out.println("status: "+jsn.getString("status"));
		System.out.println("status msg: "+jsn.getString("statusMsg"));
		System.out.println("output: "+jsn.getString("output"));
		 
	}
	private String sendPOSTRequest(HashMap<String,String> pars) throws IOException
	{
		String toRet = null;
		int r = -1;
		byte[] buff = new byte[1024];
		StringBuffer sb = new StringBuffer("");
		
		System.out.println(">>>>>>>>>>>>CLIENT SCA AES SERVLET VADO VERSO URL  "+urlServletCryptDecrypt.toString()+" >>>>\n\n");
		
		HttpURLConnection conn = (HttpURLConnection)this.urlServletCryptDecrypt.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		
		for(String key : pars.keySet())
		{
			sb.append( URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(pars.get(key),"UTF-8")+"&"  );
		}
		
		sb.deleteCharAt(sb.length()-1);
		
		os.write(sb.toString().getBytes());
		
		InputStream is = conn.getInputStream();
		sb = new StringBuffer("");
		
		while( (r = is.read(buff)) >0)
		{
			sb.append(new String(buff,0,r));
		}
		
		toRet = sb.toString();
		
		try{ os.close();} catch(Exception ex){}
		try{ is.close();} catch(Exception ex){}
		
		return toRet;
	}
	
	 
	public URL getUrlServletCryptDecrypt() {
		return urlServletCryptDecrypt;
	}
	public void setUrlServletCryptDecrypt(URL urlServletCryptDecrypt) {
		this.urlServletCryptDecrypt = urlServletCryptDecrypt;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
