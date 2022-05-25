/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package configurazione.centralizzata.nuova.gestione;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Properties;

import org.json.JSONObject;

import sun.net.www.protocol.http.HttpURLConnection;

public class ClientServizioCentralizzato {
	private URL urlServizioConfigurazione;
	//private URL urlMyIp;
	
	public ClientServizioCentralizzato() throws IOException{
		
		InputStream is = this.getClass().getResourceAsStream("client.properties");
		Properties prop = new Properties();
		prop.load(is);
		String urlS =prop.getProperty("urlServizioConfigurazione");
		this.urlServizioConfigurazione = new URL(urlS); 
		/*urlS = prop.getProperty("urlMyIp");
		this.urlMyIp = new URL(urlS);*/
		System.out.println("---> CREATO CLIENT SERVIZIO CONFIGURAZIONE CENTRALIZZATO VERSO URL "+urlS);
	}
	
	public ClientServizioCentralizzato(URL urlServizio, URL urlMyIp){this.urlServizioConfigurazione = urlServizio; /*this.urlMyIp = urlMyIp;*/}
	
	
	
	 
	
	/*
	 * contatta il servizio centralizzato per face il check del browser
	 */
	public JSONObject checkBrowser(String userAgent) throws Exception 
	{
		String urlS = this.urlServizioConfigurazione+"?service=checkBrowser&userAgent="+encodeUTF8(userAgent);
		String t = sendGETRequest(new URL(urlS));
		
		return new JSONObject(t);
	}
	
	
	/*
	 * contatta il servizio centralizzato per ottenere la mappa degli urls degli endpoints, per ambiente
	 */
	public JSONObject getMappaEndpointsSca() throws Exception
	{
		 
		/*ottengo prima l'ambiente */
		//String ambiente = this.getAmbiente().getString("ambiente");
		String urlS = this.urlServizioConfigurazione+"?service=endpointsSca";//&ambiente="+encodeUTF8(ambiente);
		return getJsonObj(new URL(urlS));
	}
	 
	
	/*
	 * contatta il servizio centralizzato per ottenere l'ambiente 
	 */
	public JSONObject getAmbiente() throws Exception 
	{
		String urlS = this.urlServizioConfigurazione+"?service=ambiente";//&ip="+encodeUTF8(getMyIp());
		
		JSONObject toRet =getJsonObj(new URL(urlS));
		String status = toRet.getString("status");
		if(Integer.parseInt(status) < 0 )
		{
			throw new Exception(" ------------------ AMBIENTE NON RICONOSCIUTO CORRETTAMENTE DAL SERVIZIO CONFIGURATORE CENTRALIZZATO--------------------"
					+ "----------------------------------------------------------------------------------------------------------------------------------"
					+ "----------------------------------------------------------------------------------------------------------------------------------");
		}
		
		String ambS = toRet.getString("ambiente");
		System.out.println(">>CLIENT CONFIGUZ CENTRALIZZATA -> RICHIESTO AMBIENTE DA URL "+urlS+"   , RISPOSTA "+ambS);
		return toRet;
	}
	
	
	
	
	private JSONObject getJsonObj(URL url)throws Exception
	{
		String t = sendGETRequest( url);
		return new JSONObject(t);
	}
	
	/*public String getMyIp() throws IOException
	{
		String toRet = sendGETRequest(urlMyIp).trim();
		System.out.println(">>>CLIENT CONF CENTRALIZZATO, CHIEDO MIO IP DA "+urlMyIp+"  , RISPOSTA "+toRet);
		return toRet;
	}
	*/
	
	private String encodeUTF8(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode,"UTF-8");
	}
	
	private String sendGETRequest(URL url) throws IOException{
		String toRet = null;
		
		 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		byte[] buff = new byte[1024];
		int r = -1;
		StringBuffer sb = new StringBuffer("");
		while((r = is.read(buff))>0)
		{
			sb.append(new String(buff,0,r));
		}
		toRet = sb.toString();
		
		return toRet;
	}
}
