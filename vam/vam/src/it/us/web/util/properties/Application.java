/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import configurazione.centralizzata.nuova.gestione.ClientServizioCentralizzato;
import sun.net.www.URLConnection;

public class Application extends GenericProperties
{
	protected static Properties properties = null;
	
	static
	{
		properties = load( "application.properties" );
	}
	
	public static String get(String property)
	{
		return get( property, properties );
	}

	/**
	 * @param property
	 * @return il valore della chiave se questa è presente nel file message.properties, 
	 * la chiave stessa filtrata dal carattere _ (underscore) altrimenti
	 */
	public static String getSmart(String property)
	{
		return getSmart( property, properties );
	}
	
	public static void reloadProperties(String ambiente) throws IOException{
		//properties = load("application_"+ambiente+".properties");
		
		// Recupero il IP pubblico del server
		URL connection = new URL("http://checkip.amazonaws.com/");
	    java.net.URLConnection con = connection.openConnection();
	    String str = null;
	    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    str = reader.readLine();
	    ambiente=str;
	    
	    //if (ambiente.equalsIgnoreCase("62.101.81.68"))
	    //	ambiente="localhost";
		
		
		String amb="";
		try {
			//JSONObject json = readJsonFromUrl("http://"+ambiente+"/vam/configuratoreAmbiente.jsp?richiesta=ambiente");;
			JSONObject json = readJsonFromUrl("http://mon.gisacampania.it/configuratoreAmbiente.php?ip="+ambiente+"&service=ambiente");
            //JSONObject obj = (JSONObject)json.get(0);
            System.out.println("***CARICATO AMBIENTE: "+json.get("ambiente"));
            
            amb= "application.properties"+json.get("ambiente");
            
            
            ClientServizioCentralizzato sclient = new ClientServizioCentralizzato();
            ambiente=(String) sclient.getAmbiente().getString("ambiente");
            
            
        } 
		catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("----> VADO AVANTI UGUALMENTE CARICANDO application.propertiesUFFICIALE");
			amb = "application.propertiesUFFICIALE";
		}
		properties = load(amb);
		
	}
	
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, ParseException {
        // String s = URLEncoder.encode(url, "UTF-8");
        // URL url = new URL(s);
        InputStream is = new URL(url).openStream();
        JSONObject json = null;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);
        } finally {
            is.close();
        }
        return json;
    }

    
    
    public static JSONObject  checkBrowser(String userAgent)
	{
		
		 
		try {
			// Recupero il IP pubblico del server
			
			/*vecchia gestione
			JSONObject json = readJsonFromUrl("http://mon.gisacampania.it/configuratoreAmbiente.php?service=checkBrowser&userAgent="+userAgent);
			*/
			/*nuova gestione*/
			ClientServizioCentralizzato sclient = new ClientServizioCentralizzato();
			JSONObject json = sclient.checkBrowser(userAgent); 
            return json;
            
           
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
		
	}
	
	
}
