/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServiziRestJson {
	
	private static GsonBuilder gb;
	private static Gson gson;
	
	static
	{
		gb = new GsonBuilder();
		gb.setDateFormat( "yyyy-MM-dd HH:mm:ss" );
		gson = gb.create();
	}
	  
	  public static boolean verificaMicrochip(String mc){
		  
		  boolean ret=false;
		  
		  Cane c = null;
			
			String url = "http://wrappersrv/WrapperApplication/canina/InfoCane.do?mc=" + mc + "&responseType=JSON";
			try
			{
				c = gson.fromJson( UrlUtil.getUrlResponse( url ), Cane.class );
				
				if( c != null && c.getMc() != null )
				{
					ret = true;
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		  
		  return ret;
	  }
	  
	  
	  
	  public static String getInfoCaneCanile(String mc , int idCanile){	
		  
		  String ret = "";
			
			try
			{
				String url = "http://wrappersrv/WrapperApplication/canina/InfoCaneCanile.do?mc=" + mc + "&idCanile=" + idCanile + "&responseType=JSON";
				//String url = ApplicationProperties.getProperty("URL_SERVIZIO_INFOCANECANILE") + "?mc=" + mc + "&idCanile=" + idCanile + "&responseType=JSON";
				String urlResponse = UrlUtil.getUrlResponse( url );
				Cane c  = gson.fromJson( urlResponse, Cane.class );
				if(c != null && c.getMc() != null){
					ret = "MC " + c.getMc() + " presente in BDR per questo canile:\n" + 
						  "Razza: " + ( c.getRazza() != null && !c.getRazza().equals("") ? c.getRazza() : "N.D." ) + "\n" + 
						  "Sesso: " + ( c.getSesso() != null && !c.getSesso().equals("") ? c.getSesso() : "N.D." ) + "\n" +
						  "Asl: " + ( c.getAslRiferimentoStringa() != null && !c.getAslRiferimentoStringa().equals("") ? c.getAslRiferimentoStringa() : "N.D." ) + "\n" +
						  "Proprietario: " + ( c.getNominativoProprietario() != null && !c.getNominativoProprietario().equals("") ? c.getNominativoProprietario() : "N.D." ) + "\n" +
						  "Detentore: " + ( c.getNominativoDetentore() != null && !c.getNominativoDetentore().equals("") ? c.getNominativoDetentore() : "N.D." );
				}
				else{
					if(urlResponse.contains("Microchip")){
						ret = urlResponse.substring(urlResponse.indexOf("Microchip"), urlResponse.indexOf("\"}") );
					}
					
				}
			}
			catch (Exception e)
			{
				ret = "Si e verificato un problema nella verifica del microchip.";
				e.printStackTrace();
			}
			
		  return ret;
	  }
	  
	  
//	  public static ArrayList<Integer> getElencoDocumentiPerCanile(int idCanile){
//		  
//		  ArrayList<Integer> ret = null;
//		  
//			String url = ApplicationProperties.getProperty("URL_SERVIZIO_ELENCO_DOCUMENTI_PER_CANILE") + "?idCanile=" + idCanile + "&responseType=JSON";
//			try
//			{
//				Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
//				String strJson = UrlUtil.getUrlResponse( url );
//				System.out.println("STRJSON DOC: " + strJson);
//				ret = gson.fromJson(strJson.substring(strJson.indexOf("["), strJson.indexOf("]") + 1) , type );
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		  
//		  return ret;
//	  }
	  
	  
//	  public static HashMap<Integer, ArrayList<String>> getElencoDocumentiPerCanile(int idCanile){
//		  
//		  HashMap<Integer, ArrayList<String>> ret = null;
//		  
//			String url = ApplicationProperties.getProperty("URL_SERVIZIO_ELENCO_DOCUMENTI_PER_CANILE") + "?idCanile=" + idCanile + "&responseType=JSON";
//			try
//			{
//				
//				//prova
//				HashMap<Integer, ArrayList<String>> hashprova = new HashMap<Integer, ArrayList<String>>();
//				hashprova.put(1, new ArrayList<String>());
//				hashprova.get(1).add("ciao");
//				hashprova.get(1).add("mondo");
//				hashprova.get(1).add("tutto");
//				hashprova.get(1).add("ok");
//				hashprova.put(2, new ArrayList<String>());
//				hashprova.get(2).add("we");
//				hashprova.get(2).add("ciao");
//				hashprova.put(3, new ArrayList<String>());
//				hashprova.get(3).add("we");
//				System.out.println("JSON: " + gson.toJson(hashprova) );
//				//prova
//				
//				Type type = new TypeToken<HashMap<Integer, ArrayList<String>>>(){}.getType();
//				String strJson = UrlUtil.getUrlResponse( url );
//				System.out.println("STRJSON: " + strJson);
//				strJson = strJson.replaceAll("\"empty\":false,\"entry\":\\[\\{\"key\":", "");
//				strJson = strJson.replaceAll(",\"value\":\\{\"string\"", "");
//				strJson = strJson.replaceAll("\\}\\},\\{\"key\":", ",");
//				strJson = strJson.replaceAll("\\}\\}\\]", "");
//				strJson = strJson.replaceAll("\\\"", "");
//				strJson = strJson.replaceAll("\\\\", "\"");
//				System.out.println("STRJSON: " + strJson);
//				ret = gson.fromJson(strJson , type );
//				
//				
//				
//				
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		  
//		  return ret;
//	  }
	  
	  
	  public static ArrayList<DocumentoPrelievoCampione> getElencoDocumentiPerCanile(int idCanile, String username){
		  
		  ArrayList<DocumentoPrelievoCampione> ret = null;
		  
			String url = "http://wrappersrv/WrapperApplication/canina/ElencoDocumentiCanile.do?idCanile=" + idCanile + "&usernameUtente=" + username + "&responseType=JSON";
			try
			{
				Type typeArray = new TypeToken<ArrayList<DocumentoPrelievoCampione>>(){}.getType();
				String strJson = UrlUtil.getUrlResponse( url );
				
				if(strJson.contains("[") && strJson.contains("]")){
					ret = gson.fromJson(strJson.substring(strJson.indexOf("["), strJson.indexOf("]") + 1) , typeArray );
				}
				else{
					DocumentoPrelievoCampione doc = gson.fromJson(strJson.substring(strJson.indexOf(":{") + 1, strJson.indexOf("}") + 1) , DocumentoPrelievoCampione.class);
					if(doc != null){
						ret = new ArrayList<DocumentoPrelievoCampione>();
						ret.add(doc);
					}
					
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		  
		  return ret;
	  }
	  
	  
	  public static ArrayList<String> getElencoMCPerDocumento(int idDocumento){
		  
		  ArrayList<String> ret = null;
		  
			String url = "http://wrappersrv/WrapperApplication/canina/ElencoMicrochipDocumento.do?idNumeroProgressivo=" + idDocumento + "&responseType=JSON";
			try
			{
				Type type = new TypeToken<ArrayList<String>>(){}.getType();
				String strJson = UrlUtil.getUrlResponse( url );
				
				if(strJson.contains("[") && strJson.contains("]")){
					ret = gson.fromJson(strJson.substring(strJson.indexOf("["), strJson.indexOf("]") + 1) , type );
				}
				else if(strJson.contains(":") && strJson.contains("}")){
					strJson = strJson.substring(strJson.indexOf(":") + 1, strJson.indexOf("}"));
					ret = new ArrayList<String>();
					ret.add(strJson);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		  
		  return ret;
	  }
	  
	  
	  public static boolean abilitaFlagControlliPerDocumento(int idDocumento){
		  
		  boolean ret = false;
		  
			String url = "http://wrappersrv/WrapperApplication/canina/AbilitaFlagControlliPerDocumento.do?idNumeroProgressivo=" + idDocumento + "&responseType=JSON";
			try
			{
				String strJson = UrlUtil.getUrlResponse( url );
				
				if(strJson != null && strJson.contains("OK")){
					ret = true;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		  
		  return ret;
	  }
	  
	  
	 
}
