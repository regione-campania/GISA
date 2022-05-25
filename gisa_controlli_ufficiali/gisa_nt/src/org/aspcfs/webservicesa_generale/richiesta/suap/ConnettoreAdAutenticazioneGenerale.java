/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class ConnettoreAdAutenticazioneGenerale {

	private URL urlServ;
	
	public ConnettoreAdAutenticazioneGenerale(URL baseUrl)
	{
		this.urlServ = baseUrl;
	}
	
	
	//ritorna booleano che indica autenticazione , e riempie iduser se autenticato
	public boolean autentica(String suapIp, String encryptedToken, String debugServizioRest,String[] idUserToFill) throws Exception
	{
		
		OutputStream os = null;
		//mandiamo la richiesta alla action per l'autenticazione
		 
		HttpURLConnection con = (HttpURLConnection) urlServ.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		//se e settato il parametro di debug, lo indichiamo alla login no pw (/eliminare dopo sviluppo)
		
		String parametriPost = "SuapIP="+suapIp+"&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8")+"&categoriaQuattro=true" + (debugServizioRest != null ? "&debugServizioRest="+debugServizioRest : "");
		os = con.getOutputStream();
		os.write(parametriPost.getBytes());
		os.flush();
		os.close();
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String t = null;
		while((t = br.readLine())!=null)
		{
			sb.append(t);
		}
		
		
		String[] tokens = sb.toString().split("/");
		String risultato = tokens[0];
		idUserToFill[0] =  tokens[6];
		
		try{os.close(); } catch(Exception ex){}
		
		
		return risultato.equalsIgnoreCase("riuscito") ? true : false;
		
		
		
	}
	
	
	
	
}
