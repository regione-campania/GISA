/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletServiziScheda extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String cssScreen = "<style type=\"text/css\"> .dettaglioTabella table{ font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .dettaglioTabella td{	font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .grey {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue {	 background-color:#e6f3ff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";  
	String cssPrint = "<style type=\"text/css\"> .dettaglioTabella table{ font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .dettaglioTabella td{	font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .grey {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue {	 background-color:#e6f3ff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";
	
	private static String CAPITOLO = "capitolo";
	private static String BARCODE = "barcode";
	private static String INDIRIZZO = "indirizzo";
	private static String JSON = "json";

	
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletServiziScheda() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");

		String objectId = request.getParameter("object_id");
		String objectIdName = request.getParameter("object_id_name");
		String tipoDettaglio = request.getParameter("tipo_dettaglio");
		String outputType = request.getParameter("output_type");
		String visualizzazione = request.getParameter("visualizzazione");
		
		if (outputType==null || outputType.equals("null") || outputType.equals(""))
			outputType = "html";
		if (visualizzazione==null || visualizzazione.equals("null") || visualizzazione.equals(""))
			visualizzazione = "tutto";
	
	
		Connection db = null;
		ConnectionPool cp = null ;
		try
		{
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) getServletContext().getAttribute(
		"applicationPrefs");
		
		UserBean user = (UserBean) request.getSession().getAttribute("User");

		ApplicationPrefs prefs = (ApplicationPrefs) getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		ce.setDriver(ceDriver);

		cp = (ConnectionPool)getServletContext().getAttribute("ConnectionPool");
		db = cp.getConnection(ce,null);
		
		if (tipoDettaglio!=null && !tipoDettaglio.equals(""))
			gestisciDettaglio(request,response, db, objectId, objectIdName, tipoDettaglio, outputType, visualizzazione);
		else
			gestisciDettaglioVuoto(request,response, db);
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			cp.free(db,null);
		}
		

	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	public static String generaBarcode(HttpServletRequest request, String code) {
		String barcode = "";
		String urlServlet = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/"+request.getContextPath()+"/"+"ServletBarcode";
		System.out.println("Servlet barcode: "+urlServlet);
		URL obj;
		HttpURLConnection conn = null;
		try {
			obj = new URL(urlServlet);
			
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("barcode");
			requestParams.append("=").append(code);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			conn.getContentLength();
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
			
			while (in != null) {
				String ricevuto = in.readLine();
				if (ricevuto==null)
					break;
				result.append(ricevuto); }
			//in.close();
			barcode = result.toString();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		finally {try{conn.disconnect();} catch(Exception ex){}}
		
		return barcode;
	
}
		
		private String generaRigaJson(String nome, String valore, String attributo){
			if (nome==null)
				nome= "";
			else
				nome = nome.replaceAll("'", "");
			
			if (valore==null)
				valore="";
			else
				valore = valore.replaceAll("'", "");
			
			if (attributo==null)
				attributo = "";
			String output ="{nome:'"+nome+"', valore:'"+valore+"', attributo:'"+attributo.replaceAll("'", "\'")+"'}";
			return output;
		}
		
		
		protected void gestisciDettaglio(HttpServletRequest request, HttpServletResponse response, Connection db, String objectId, String objectIdName, String tipoDettaglio, String outputType, String visualizzazione) throws ServletException, IOException, ParseException {
			// TODO Auto-generated method stub
			SchedaCentralizzata scheda = new SchedaCentralizzata();
			
			if (objectIdName!=null && objectIdName.equals("stab_id"))
				scheda.setStabId(objectId);
			else if (objectIdName!=null && objectIdName.equals("alt_id"))
				scheda.setAltId(objectId);
			else	
				scheda.setOrgId(objectId);
			
		//	scheda.setStabId(stabId);
//			scheda.setAddressId1(add1);
//			scheda.setAddressId2(add2);
//			scheda.setAddressId3(add3);
			scheda.setTipo(tipoDettaglio);
			scheda.setDestinazione(visualizzazione);
			scheda.popolaScheda(db);
			
			if(outputType.equals("html"))
			{
				
			//String css = "<style type=\"text/css\"> table{ font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;}table, td{	font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .grey {	 background-color:#ededed;	 border: 1px solid black;	 }.blue {	 background-color:#bdcfff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";
				
			String objectCss = request.getParameter("object_css");
			if (objectCss==null || objectCss.equals("null") || objectCss.equals("")){
				if (scheda.getDestinazione().equals("print"))
					objectCss = cssPrint;
				else
					objectCss =cssScreen;
			}
			String output = "";
			
			output = "<table class=\"dettaglioTabella\" cellpadding=\"5\" style=\"border-collapse: collapse\" width=\"100%\"> <col width=\"33%\">";
			for(Map.Entry<String, String[]> elemento : scheda.getListaElementi().entrySet()){
				if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equals(CAPITOLO)) {
				output = output + "<tr><td colspan=\"4\" class=\"blue\"><b>"+elemento.getKey().toUpperCase() +"</b></td></tr>";
				} 
				else if ( elemento.getValue()[0]!=null && !elemento.getValue()[0].replaceAll(" ", "").equals("") && !elemento.getValue()[0].equals("null")) {
			output = output + "<tr><td class=\"grey\" >"+elemento.getKey() +"</td>";
			if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equalsIgnoreCase(BARCODE)) {
			output = output + "<td class=\"layout\">"+generaImmagine(generaBarcode(request, elemento.getValue()[0]))+"</td></tr>";
			} 
			else if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equalsIgnoreCase(INDIRIZZO)) {
				output = output + "<td class=\"layout\">"+ elemento.getValue()[0] + visualizzaSuMappa(elemento.getValue()[0])+ "</td></tr>";
				}
			else if ( elemento.getValue()[1]!=null && elemento.getValue()[1].equalsIgnoreCase(JSON)) {
				output = output + "<td class=\"layout\"><center>"+generaTabellaJson(elemento.getValue()[0])+"</center></td></tr>";
				} 
			else {
			output = output + "<td class=\"layout\">"+ elemento.getValue()[0] + "</td></tr>";
			}
			
			}
			}
			output = output + "</table>";
			output = objectCss + output;
			
			response.getWriter().println(output.toString());
			}
			else if (outputType.equals("xml"))
			{
				String output="";
				
				output = output + "<Dettaglio>";
				output= output + "\n";
						;			
					for(Map.Entry<String, String[]> elemento : scheda.getListaElementi().entrySet()){
					output = output + "<nome>"+elemento.getKey()+"</nome>";
					output= output + "\n";
					output = output + "<valore>"+elemento.getValue()[0]+"</valore>";
					output= output + "\n";
					output = output + "<attributo>"+elemento.getValue()[1]+"</attributo>";
					output= output + "\n";
				}
					output = output + "</Dettaglio>";
					response.getWriter().println(output.toString());
			}
			else if (outputType.equals("json"))
			{
				JSONArray json_arr = new JSONArray();
				for(Map.Entry<String, String[]> elemento : scheda.getListaElementi().entrySet()){
					JSONObject json_obj = null ;
					String output = generaRigaJson(elemento.getKey(), elemento.getValue()[0], elemento.getValue()[1]);
					json_obj=new JSONObject(output);
					json_arr.put(json_obj);
				}
				
				response.setContentType("Application/JSON");
				response.getWriter().println(json_arr.toString().replaceAll(",}", "}"));
	            
			}
			
		} 
		
		protected void gestisciDettaglioVuoto(HttpServletRequest request, HttpServletResponse response, Connection db) throws ServletException, IOException, SQLException {
			// TODO Auto-generated method stub
			
			//String css = "<style type=\"text/css\"> table{ font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;}table, td{	font-family: Calibri, sans-serif;	font-size:14px;	color:#000000;} .grey {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue {	 background-color:#e6f3ff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";
			
			String objectCss = request.getParameter("object_css");
			if (objectCss==null || objectCss.equals("null") || objectCss.equals(""))
				objectCss = cssScreen;
			
			LookupList tipoList = new LookupList(db, "lookup_tipo_scheda_operatore");
				
			String output = "";
			output = "<table class=\"dettaglioTabella\" cellpadding=\"5\" style=\"border-collapse: collapse\"> <col width=\"66%\">";
			output = output + "<tr><th colspan=\"2\">Lista dei tipi selezionabili</th></tr>";
			output = output + "<tr><th>Operatore</th> <th>Id</th></tr>";
			
			for (int i =0; i<tipoList.size(); i++){
				LookupElement elemento = (LookupElement) tipoList.get(i);
				output = output + "<tr><td class=\"layout\">"+elemento.getDescription()+"</td> <td class=\"layout\">"+ elemento.getCode() + "</td></tr>";
			}
			output = output + "</table>";
			
			output = objectCss + output;
			response.getWriter().println(output.toString());
			
		}
		private String visualizzaSuMappa(String indirizzo){
			String visualizzaSuMappa = " <a target=\"_blank\" href=\"http://www.google.it/maps?q="+indirizzo+"\">Visualizza su mappa</a>"; 
			return visualizzaSuMappa;
			
		}
		private String generaImmagine(String img){
			String immagine = "<img src=\""+img+"\"/>"; 
			return immagine;
			
		}
		
		public String generaTabellaJson(String json) throws ParseException{
			String output = "";
			String jsonString = "["+json.replaceAll("<br/><br/>", ",")+"]";
			ArrayList<String> ordiniCampi = new ArrayList<String>(); /*campi in ordine di arrivo */
			
			/*
			HashMap<Integer, String> hash = new HashMap<Integer, String>();
			Map<Integer, String> treeMap = null; */
			JSONArray topArray = new JSONArray(jsonString); 
			
			 
			/*nuova versione per evitare bug di linee con campi diversi*/
			ArrayList<String[]> triple = new ArrayList<String[]>(); /*descr linea - nome campo- valore */
			
			HashMap<String,ArrayList<String[]>> lineeToCampoVal = new HashMap<String,ArrayList<String[]>>(); /*per descrizione linea -> lista entries*/
//			TreeSet<String> allCampos = new TreeSet<String>();/* tutti i campi possiili incontrati */
			/*+++++++++++++*/
			 
			
			
			for(int i = 0; i < topArray.length(); i++){
				JSONObject c = topArray.getJSONObject(i);
				
				/*
				if (treeMap==null){
				for (int j = 0; j<c.names().length();j++){
					int index = jsonString.indexOf(c.names().getString(j));
					hash.put(index, c.names().getString(j));
					
				
					
				}
				treeMap = new TreeMap<Integer, String>(hash);
				}
				*/
				
				
				/*nuova versione */
				/*+++++++++++++*/
				String[] tripla = new String[3];
				
				for (int j = 0; j<c.names().length();j++)
				{
					String key = c.names().getString(j);
					String value = c.getString(key);
					if(key.matches(".*linea.*"))
					{
						 
						/*String descrLinea = c.getString(key);
						if(!lineeToCampoVal.containsKey(descrLinea))
						{
							HashMap<String,String> toAdd = new HashMap<String,String>();
							lineeToCampoVal.put(descrLinea, toAdd);
						}*/
						tripla[0] = value;
					}
					else if(c.names().getString(j).toLowerCase().matches(".*info.*"))
					{
						/*allCampos.add(c.names().getString(j));*/
						tripla[1] = value;
						 
					}
					else if(c.names().getString(j).toLowerCase().matches("^valore$"))
					{
						/*allCampos.add(c.names().getString(j));*/
						tripla[2] = value;
					}
					 
				}
				
				triple.add(tripla);
				
				
				
				
				
				/*+++++++++++*/
				/*
				for(int k = 0; k<treeMap.size(); k++){
					int key = (int) treeMap.keySet().toArray()[k];
					String nome = treeMap.get(key); 
					String value = (String) c.get(nome);
					if (nome.equals("info")){
						if (!headers.contains(value))
							headers.add(value);
					}
					else if (nome.equals("valore"))
						body.add(value);
				} 
				*/
			}
			
			for(String[] temp : triple)
			{
				//temp[0]; e' descr linea
				// temp[1]; e' nome campo (info)
				temp[2] = temp[2] == null ? "" : temp[2]; //temp2 e' valore
				
				if(temp[0] == null || temp[0].trim().length() == 0 
						|| temp[1] == null || temp[1].trim().length() == 0 ) continue;
				
				
				
				
				/*aggiungo info linea */
				if(!lineeToCampoVal.containsKey(temp[0]))
				{
					 
					lineeToCampoVal.put(temp[0], new ArrayList<String[]>());
				} 
				
				ArrayList<String[]> infoLinee = lineeToCampoVal.get(temp[0]);
				/*per hp un campo puo' avere +  valori */
				 
				infoLinee.add( temp );
				
			}
			
			/*1 tr per ogni linea, nel primo tr la descr linea, e nel secondo una tabella dove formatto le entries */
			for(String descrLinea : lineeToCampoVal.keySet())
			{
				ArrayList<String[]> valoriPerCampiLinea = lineeToCampoVal.get(descrLinea);
				output += "<center><br><b>LINEA DI ATTIVITa\'</b>: "+descrLinea+"</br><br>";
				output+="<table align=\"center\" cellpadding=\"5\" style=\"border-collapse: collapse\" border=\"1px solid black\" >"; /*la tabella dei campi */
				
				/*per creare gli headers devo prendere tutti i nomi dei campi (mantengo ordine con linkedhashmap)*/
				LinkedHashMap<String,Boolean> hash0 = new LinkedHashMap<String,Boolean>();
				for(String[] tripla : valoriPerCampiLinea)
				{
					if(hash0.containsKey(tripla[1]))
						continue;
					
					hash0.put(tripla[1], true);
				}
				
				int num_righe_info_aggiu =valoriPerCampiLinea.size()/hash0.keySet().size();
				
				if(hash0.keySet().size() > 6 && num_righe_info_aggiu < 2 ){
					//gestione verticale dei campi
					String[] tripla = null;
					Iterator<String[]> valoriVerticali = valoriPerCampiLinea.iterator();
					for(String nomeCampo : hash0.keySet())
					{
						output += "<tr>";
						//intestazione riga
						output+= ("<th>"+nomeCampo+"</th>");
						//valore/i riga
						tripla = valoriVerticali.next();
						output+=("<td>"+tripla[2]+"</td>");
						output+="</tr>";
					}
										
				} else {
					//gestione orizontale dei campi
					output += "<tr>";
					for(String nomeCampo : hash0.keySet())
					{
						output+= ("<th>"+nomeCampo+"</th>");
					}
					output+="</tr>";
					
					/*inizio le tr delle entries */
					int iCol = 0;
					output+="<tr>";
					for(String[] tripla : valoriPerCampiLinea)
					{
						if(iCol == hash0.keySet().size())
						{
							iCol = 0;
							output+="</tr>";
							output+="<tr>";
						}
						output+=("<td>"+tripla[2]+"</td>");
						iCol++;
					}
					output += "</tr>"; 
				}

				
				output+="</table></center><hr>";
				
			}
			
			return output;
		}
}
