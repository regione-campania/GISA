/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Logger;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONObject;

import com.google.gson.Gson;

public class GeoCoder 
{
	Logger logger = Logger.getLogger("MainLogger");
	
	final public double LAT_CAMPANIA_MIN  = 40.750956;
	final public double LAT_CAMPANIA_MAX  = 41.241021;
	final public double LONG_CAMPANIA_MIN = 14.66765;
	final public double LONG_CAMPANIA_MAX = 15.336227;

	private static Hashtable<String, String> acronimi = new Hashtable<String, String>();

	static //ELENCO ACRONIMI SOSTITUITI
	{
		acronimi.put("loc.",  "localita");
		acronimi.put("c.da",  "contrada");
		acronimi.put("c/da",  "contrada");		
		acronimi.put("c.so",  "corso");		
		acronimi.put("c/so",  "corso");
		acronimi.put("trav.", "traversa");
		acronimi.put("s.s.",  "strada statale");

		acronimi.put("XXV",   "25");
		acronimi.put("XXIV",  "24");
		acronimi.put("XXX",   "30");
		acronimi.put("XXXI",  "31");
	}


	//chiave google map
	private String key="AIzaSyA_XTLGPhbMx-cUxUW1H-FEqatg1naFEyk";
	
	private String jsonCoord(String address) throws IOException {
		//URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true&language=it");
		                           URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?key="+key+"&address="+address+"&sensor=false&language=it");
		System.out.println("NEW-GOOGLE CORDS CHIAMATA: "+"https://maps.googleapis.com/maps/api/geocode/json?key="+key+"&address="+address+"&sensor=false");
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		String jsonResult = "";
		while ((inputLine = in.readLine()) != null) {
		    jsonResult += inputLine;
		}
		in.close();
		return jsonResult; 
		}
	
	private String jsonCoord (String lat, String lon) throws IOException {
		//URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true&language=it");
		
		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=true&language=it");
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		String jsonResult = "";
		
		while ((inputLine = in.readLine()) != null) {
		    jsonResult += inputLine;
		}
		in.close();
		
		return jsonResult; 
	}

	public String[] getLatLongCity(String indirizzo, String paese, String provincia, String latitudine, String longitudine) throws ParseException 
	{	
		String [] dati_coordinate = new String [3] ;
		
		String json = "";
		
		String enabledGoogleServ = ApplicationProperties.getProperty("ENABLEDGOOGLESERVICE");
		String IND="";

		if(enabledGoogleServ.equalsIgnoreCase("si"))
		{
		
			try
			{
		
				Gson gson = new Gson();
				if(indirizzo.equals(""))
					IND = paese + "+" + provincia;
				else
					IND=indirizzo +","+paese +" "+provincia+",italia";
			
			 GoogleGeoCodeResponse result = gson.fromJson(jsonCoord(URLEncoder.encode(latitudine, "UTF-8"),URLEncoder.encode(longitudine, "UTF-8")),GoogleGeoCodeResponse.class);
	
			 double lat = 0;
			 double lng = 0;
			 String esito = "ko";
			 
		 for (int i = 0 ; i <result.results.length;i++ )
		 {
			 
			 for (int  j=0; j<result.results[i].address_components.length;j++)
			 {
				 System.out.println("stampa paese:"+paese);
				 if(result.results[i].address_components[j].long_name.equalsIgnoreCase(paese))
				 {
					  System.out.println("stampa_longname:"+result.results[i].address_components[j].long_name);
					  lat = Double.parseDouble(result.results[0].geometry.location.lat);
					  lng = Double.parseDouble(result.results[0].geometry.location.lng);
					  esito="ok";
				 }
			 }
		 }
		 if(lat==0 && lng ==0){

				 return null;		
		 }
	
			 dati_coordinate [0] = lng+"" ;
			 dati_coordinate [1] = lat+"" ;
			 dati_coordinate [2] = esito;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			logger.warning("Calcolo Coordinate non riuscito");
		}
		}
		else
		{
			logger.warning("Calcolo Coordinate Disabilitato");
			json="N.D,N.D";
		}
		return dati_coordinate;
	}	

	
	
	public String[] callGoogleService(String indirizzo, String paese, String provincia) throws ParseException 
	{	
		String [] latlng = new String [3] ;
		
		String json = "";
		
		String enabledGoogleServ = ApplicationProperties.getProperty("ENABLEDGOOGLESERVICE");
		
		if(enabledGoogleServ.equalsIgnoreCase("si"))
		{
		String query = formatAddress( indirizzo, paese, provincia );
		//String urlLine = "http://maps.google.com/maps/geo?q=" + query + "&output=json&key=" + key;
		String urlLine = "";
		
		try
		{		
		
			Gson gson = new Gson();
			String IND="";
			if(indirizzo.equals(""))
				IND = paese + "+" + provincia;
			else
				IND=indirizzo +","+paese +" "+provincia+",italia";
			
			 GoogleGeoCodeResponse result = gson.fromJson(jsonCoord(URLEncoder.encode(IND, "UTF-8")),GoogleGeoCodeResponse.class);
	
			 double lat = 0;
			 double lng = 0;
			 String esito = "ko";
		 
		 for (int i = 0 ; i <result.results.length;i++ )
		 {
			 
			 for (int  j=0; j<result.results[i].address_components.length;j++)
			 {
				 if(result.results[i].address_components[j].long_name.equalsIgnoreCase(paese))
				 {
					  lat = Double.parseDouble(result.results[0].geometry.location.lat);
					  lng = Double.parseDouble(result.results[0].geometry.location.lng);
					  esito="ok";
				 }
			 }
		 }
		 
		 
		 if(!indirizzo.equals("") && lat==0 && lng ==0){
			 //if(!indirizzo.equals("")) {
				  //azzera indirizzo
				  //GoogleGeoCodeResponse result1 = gson.fromJson(jsonCoord(URLEncoder.encode("" +","+paese +" "+provincia+",italia", "UTF-8")),GoogleGeoCodeResponse.class);
				  //lat = Double.parseDouble(result1.results[0].geometry.location.lat);
				  //lng = Double.parseDouble(result1.results[0].geometry.location.lng);
				  //esito="ok";

				 return callGoogleService("",  paese,  provincia) ;
			 //}
		 }

		   
		
		
		//String lat = (String)j.getLong("")
		//String lng = (String)j.get("lng");
		
		
		
		latlng [0] = lng+"" ;
		latlng [1] = lat+"" ;
		latlng [2] = esito;
//		if( index == -1 ) {return null;}
//
//		json = json.substring(index);
//		json = json.substring(json.indexOf("[") + 1, json.indexOf("]"));
//		
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			logger.warning("Calcolo Coordinate non riuscito");
		}
		}
		else
		{
			logger.warning("Calcolo Coordinate Disabilitato");
			json="N.D,N.D";
		}
		return latlng;
	}	

	/**
	 * Metodo che formatta la query per il servizio di geocodifica
	 * @param indirizzo - l'indirizzo 
	 * @param comune - il comune
	 * @param provincia - la provincia (sigla o per esteso : NAPOLI, SALERNO, CASERTA, ecc)
	 * @return
	 */
	private String formatAddress( String indirizzo, String comune, String provincia) 
	{ 
		String formatted_address = null;

		try {

			if( (indirizzo == null) || (indirizzo.trim().length() == 0) )  
				return URLEncoder.encode(comune+","+provincia+",italia","UTF-8");	            
			else 
				return URLEncoder.encode(indirizzo.replace("'", " ").replace("\n", " ").trim() + ","+comune+","+provincia+",italia", "UTF-8");

		} catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return formatted_address;
	}

	/**
	 * Restituisce un array con le coordinate in LAT / LON ed un flag di validita
	 * @param indirizzo
	 * @param comune
	 * @param provincia
	 * @return
	 */
	public String[] getCoords(String indirizzo, String comune, String provincia)
	{
		if (indirizzo!=null) 
		{
			indirizzo = indirizzo.toLowerCase();
			indirizzo = parseAddress(indirizzo);//normalizzazione
		}

		String[] coords = {"0","0","E"};

		String enabledGoogleServ = ApplicationProperties.getProperty("ENABLEDGOOGLESERVICE");
		
		if(enabledGoogleServ.equalsIgnoreCase("si"))
		{
		try 
		{
			coords =  callGoogleService(indirizzo, comune, provincia);
			if (coords==null || coords.length<3 )
			{
				coords =  callGoogleService(null, comune, provincia);
				if (coords==null || coords.length<3) 
				{
					coords = new String[3]; //Errato
					coords[0] = "";
					coords[1] = "";
					coords[2] = "E";
				}
				else coords[2]="A";//Approssimato (solo comune)
			}	
			else coords[2]="V";//Valido
		}	
		catch(Exception e)
		{
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} //1 sec di attesa 
		}
		}
		else
		{
			coords[0] = "Servizio non Disponibile";
			coords[1] = "Servizio non Disponibile";
			coords[2] = "Er";
		}
		return coords;
	}
	
	

	/**
	 * Metodo che ripulisce gli indirizzi da acronimi, ecc.
	 */
	private String parseAddress(String address)
	{

		for (Enumeration e = acronimi.keys(); e.hasMoreElements();)
		{
			String key = (String) e.nextElement();
			String val = acronimi.get(key);
			if (address.contains(new StringBuffer(key)))
			{
				address = address.replace(key, val);
			}
		}	
		return address;
	}
	public static void main(String[] args)
	{
		
		GeoCoder g = new GeoCoder();
		g.getCoords("via marconi 58","torre annunziata","napoli");
	}
	
	
	public String getCityByCoords(String lat, String lon) throws IOException {
		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+"&sensor=true&language=it");
		
		
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		String jsonResult = "";
		while ((inputLine = in.readLine()) != null) {
		    jsonResult += inputLine;
		}
		in.close();
		
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonResult);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JSONObject newJSON = jsonObject.getJSONObject("address_components");
	    System.out.println(newJSON.toString());
	     
	    System.out.println(jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location"));
		return newJSON.toString();
		//String lat = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");


		}

}//Fine classe
