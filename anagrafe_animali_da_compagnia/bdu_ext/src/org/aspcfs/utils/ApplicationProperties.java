/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import configurazione.centralizzata.nuova.gestione.ClientServizioCentralizzato;

public class ApplicationProperties {
	
	private static String ambiente = null;
	private static String fileProperties = null;
	public static InputStream is = null;
	
	
	private static Properties applicationProperties = null;
	private transient static Logger logger = Logger.getLogger("MainLogger");
	//costruttore
	public ApplicationProperties() { }
	
	public static String getAmbiente() {
		return ambiente;
	}
	 
	static {
		
		is = ApplicationProperties.class.getResourceAsStream( "application.properties" );
		applicationProperties = new Properties();
		try{
			applicationProperties.load( is );
		}catch(IOException e) {
			  logger.severe("[CANINA] - EXCEPTION nella classe ApplicationProperties");
			applicationProperties = null;
		}
	}
	
	public static String getProperty ( String property ){
		return ( applicationProperties != null) ? applicationProperties.getProperty( property ) : null;
	}
	
	public static ArrayList<String> getListProperty ( String property ){
		ArrayList<String> toReturn = new ArrayList<String>();
		if ( applicationProperties != null){
			int i = 1;
			while (applicationProperties.getProperty( property+i ) != null){
				toReturn.add(getProperty(property+i));
				i++;
			}
		}
		return toReturn;
	}
	
	 
	
	public static void setAmbiente(String string) {
		// TODO Auto-generated method stub
		ambiente = string;
		
		/*
		if(ambiente.contains("localhost")){
				 fileProperties= "application.propertiesSVILUPPO";
			}else if(ambiente.contains("col") || ambiente.contains("demo")){
				 fileProperties= "application.propertiesDEMO";
			}else if(ambiente.contains("srv")){
				 fileProperties= "application.propertiesUFFICIALE";
			}
			else {
				 fileProperties= "application.properties";
			}
			
		*/
		try {
			// Recupero il IP pubblico del server
			/*vecchia gestione
			URL connection = new URL("http://checkip.amazonaws.com/");
			java.net.URLConnection con = connection.openConnection();
			String str = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			str = reader.readLine();
			ambiente=str;
			
			
			JSONObject json = readJsonFromUrl("http://mon.gisacampania.it/configuratoreAmbiente.php?ip="+ambiente+"&service=ambiente");
			System.out.println("***CARICATO AMBIENTE: "+json.get("ambiente"));
            */
            ClientServizioCentralizzato sclient = new ClientServizioCentralizzato();
            ambiente=(String) sclient.getAmbiente().getString("ambiente");
            
            fileProperties= "application.properties"+ambiente;
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("----> VADO AVANTI UGUALMENTE CARICANDO application.propertiesUFFICIALE");
			fileProperties = "application.propertiesUFFICIALE";
		}
		
			is = ApplicationProperties.class.getResourceAsStream(fileProperties);
			applicationProperties = new Properties();
			try {
				applicationProperties.load( is );
			} catch (IOException e) {
				applicationProperties = null;
			}
		//}
			Logger logger = Logger.getLogger("MainLogger");
			logger.info("[ApplicationProperties] Ambiente rilevato: "+ambiente+ "; application.properties caricato: "+fileProperties);

}
	
	
	
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
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

}