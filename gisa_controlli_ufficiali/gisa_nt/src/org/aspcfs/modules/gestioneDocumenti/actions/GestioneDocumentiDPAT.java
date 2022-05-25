/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneDocumentiDPAT extends CFSModule {

//	private String encoded = "";
//	private String tabellaName=ApplicationProperties.getProperty("DOCUMENTALE_GISA_NT_TABELLA_NAME");
//	private int idDocumento=-1; //creazione documenti timbrati, id dell'entry nel db timbro_storage
	//private String driverName = "org.postgresql.Driver"; //dati per connessione a DB
	//String databaseURL = ApplicationPrefs.getPref(context.getServletContext(), "GATEKEEPER.URL");
	//private String databaseURL = "jdbc:postgresql://"+ApplicationProperties.getProperty("GESTOREGLIFODBSERVER")+":"+ApplicationProperties.getProperty("GESTOREGLIFODBPORT")+"/"+ApplicationProperties.getProperty("GESTOREGLIFODBNAME");
	//private String DB_USER= ApplicationProperties.getProperty("DOCUMENTALEDBUSER");
	//private String DB_PASSWORD=ApplicationProperties.getProperty("DOCUMENTALEDBPWD");
	public GestioneDocumentiDPAT(){
		
	}
	
	
	public String generaPdfDpat(String tipo,String idAsl,String anno,String comboArea,int idStoricoSDC,ActionContext context) throws SQLException, IOException{

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}

		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato

		//		String htmlcode = context.getRequest().getParameter("code");

		int idArea = -1 ;
		if (comboArea!= null && !"".equals(comboArea))
			idArea = Integer.parseInt(comboArea);
		String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
		String urlOriginale = "";

		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);

		String asl = "";

		if (idAsl.equals("201"))
			asl = "AVELLINO";
		else if (idAsl.equals("202"))
			asl = "BENEVENTO";
		else if (idAsl.equals("203"))
			asl = "CASERTA";
		else if (idAsl.equals("204"))
			asl = "NAPOLI 1 CENTRO";
		else if (idAsl.equals("205"))
			asl = "NAPOLI 2 NORD";
		else if (idAsl.equals("206"))
			asl = "NAPOLI 3 SUD";
		else if (idAsl.equals("207"))
			asl = "SALERNO";


		//inizializzo la variabile glifo nel caso sia null
		//glifo valorizzata: aggiungi timbro

		HttpURLConnection conn=null;



		URL oracle = null;
		String idSessione = context.getRequest().getSession().getId();

		String url          	= urlChiamante;
		String metodo="";

		if (tipo.equals("DPAT_SDC")){
			actionName="DpatSDC.do";
			metodo="Details";
		}
		else if (tipo.equals("DPAT_Carichi")){
			actionName="Dpat.do";
			metodo="DpatDetailGenerale";
		}
		else if (tipo.equals("DPAT_All2")){
			actionName="DpatRS.do";
			metodo="AddModify";
		}
		else if (tipo.equals("DPAT_All6")){
			actionName="Dpat.do";
			metodo="DpatDetailGeneraleCompetenze";
		}



		if (actionName.equals("DpatSDC.do") || actionName.equals("Dpat.do"))
			url+=actionName+
			"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl + "&anno=" + anno+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
		else if (actionName.equals("DpatRS.do"))
			url+=actionName+
			"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl +"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
		else if (actionName.equals("Dpat.do"))
			url+=actionName+
			"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl +"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE


		//PAGINA DA LEGGERE E CODIFICARE

		//	test = getHtml(url, idSessione);
		urlOriginale = url;


		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();
		String codDocumento="", titolo="";
		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA_DPAT");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
			//			requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
			//			requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
			//			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipo);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(idAsl);
			requestParams.append("&");
			requestParams.append("idCU");
			requestParams.append("=").append(anno);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(anno+"-"+asl);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("DPAT");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(idSessione);

			requestParams.append("&");
			requestParams.append("idStoricoSDC");
			requestParams.append("=").append(idStoricoSDC);

			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));

			OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("Conn: "+conn.toString());
			conn.getContentLength();


			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();

			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
			in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}

		return codDocumento ;
	}
	
	public String executeCommandGeneraPDF(ActionContext context) throws SQLException, IOException{
		
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}
		
		//URL oracle = null;
		String test = ""; //conterra' l'html recuperato
		
		String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
		String idAsl = context.getRequest().getParameter("idAsl"); 
		String anno = context.getRequest().getParameter("anno"); 
		String htmlcode = context.getRequest().getParameter("code");
		String comboArea = context.getRequest().getParameter("combo_area");
		int idArea = -1 ;
		if (comboArea!= null && !"".equals(comboArea))
			idArea = Integer.parseInt(comboArea);
		String actionName = "PrintModulesHTML.do"; //nome action da cchiamare
		String urlOriginale = "";
		
		String generazionePulita =  context.getRequest().getParameter("generazionePulita");
		boolean statico = false;
		String tabellaTipiName = ApplicationProperties.getProperty("TABELLA_LISTA_TIPI_DOCUMENTI");
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		System.out.println("[DOCUMENTALE GISA] urlChiamante: "+urlChiamante);
		
		String asl = "";
		
		if (idAsl.equals("201"))
			asl = "AVELLINO";
		else if (idAsl.equals("202"))
			asl = "BENEVENTO";
		else if (idAsl.equals("203"))
			asl = "CASERTA";
		else if (idAsl.equals("204"))
			asl = "NAPOLI 1 CENTRO";
		else if (idAsl.equals("205"))
			asl = "NAPOLI 2 NORD";
		else if (idAsl.equals("206"))
			asl = "NAPOLI 3 SUD";
		else if (idAsl.equals("207"))
			asl = "SALERNO";
		
		
		//inizializzo la variabile glifo nel caso sia null
		//glifo valorizzata: aggiungi timbro
				
		HttpURLConnection conn=null;
		
		test = htmlcode; //test contiene il sorgente html della pagina
		
		URL oracle = null;
		String idSessione = context.getRequest().getSession().getId();
		
		String url          	= urlChiamante;
		String metodo="";
			
			if (tipo.equals("DPAT_All5")){
				actionName="DpatSDC.do";
				metodo="Details";
				}
			else if (tipo.equals("Organigramma")){
				actionName="DpatSDC2019.do";
				metodo="Details";
				}
			else if (tipo.equals("DPAT_Carichi")){
				actionName="Dpat.do";
				metodo="DpatDetailGenerale";
				}
			else if (tipo.equals("DPAT_All2")){
				actionName="DpatRS.do";
				metodo="AddModify";
				}
			else if (tipo.equals("DPAT_All6")){
				actionName="Dpat.do";
				metodo="DpatDetailGeneraleCompetenze";
				}
			
			
			if (actionName.equals("DpatSDC2019.do") || actionName.equals("DpatSDC.do") || actionName.equals("Dpat.do"))
				url+=actionName+
						"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl + "&anno=" + anno+"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
			else if (actionName.equals("DpatRS.do"))
				url+=actionName+
						"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl +"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
			else if (actionName.equals("Dpat.do"))
				url+=actionName+
						"?command="+ metodo + "&combo_area="+idArea+"&idAsl=" + idAsl +"&layout=style"; //PAGINA DA LEGGERE E CODIFICARE
			
			
			//PAGINA DA LEGGERE E CODIFICARE
	
		//	test = getHtml(url, idSessione);
			urlOriginale = url;
			
			/* test: compressione  
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        GZIPOutputStream gzip = new GZIPOutputStream(out);
	        gzip.write(test.getBytes());
	        gzip.close();
	        String outStr = out.toString("ISO-8859-1");
	        test = outStr;
	    	 test: compressione */ 
	        
	
		//Per qualche motivo demo non riesce a collegarsi al proprio indirizzo pubblico, pertanto
		//enecessario sostituire i link dei CSS per farglieli leggere
		
//		test = test.replaceAll(InetAddress.getByName("APP_HOST_GISA_PUBBLICA").getHostAddress(), InetAddress.getByName("APP_HOST_GISA").getHostAddress());
//	
//			
//		//gestisco lettere accentate non lette in conversione
	//	test = gestioneCaratteriSpeciali(test);
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();

		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA_DPAT");
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());
		URL obj;
		try {
			obj = new URL(urlDocumentale);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			// conn.connect();
			StringBuffer requestParams = new StringBuffer();
//			requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
//			requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
//			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipo);
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(idAsl);
			requestParams.append("&");
			requestParams.append("idCU");
			requestParams.append("=").append(anno);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(anno+"-"+asl);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("sostituisciCaller");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("DPAT");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(user_id);
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
			requestParams.append("&");
			requestParams.append("idSessione");
			requestParams.append("=").append(idSessione);
			requestParams.append("&");
			requestParams.append("generazionePulita");
			requestParams.append("=").append(generazionePulita);
			requestParams.append("&");
			requestParams.append("statico");
			requestParams.append("=").append(statico);
			requestParams.append("&");
			requestParams.append("urlOriginale");
			requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("caller");
			requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("Conn: "+conn.toString());
			conn.getContentLength();
		
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
			} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
					return "documentaleError";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					conn.disconnect();
				}
				return executeCommandDownloadPDF(context);
	}
	
	public String executeCommandDownloadPDF(ActionContext context) throws SQLException, IOException {
		
		//recupero l'id timbro
			String codDocumento 		=  null;
				codDocumento = context.getRequest().getParameter("codDocumento");
			if (codDocumento==null)
				codDocumento = (String)context.getRequest().getAttribute("codDocumento");
			String idDocumento 				= null;
			idDocumento = context.getRequest().getParameter("idDocumento");
			
			String titolo="";
			String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		
			String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE_DPAT");
			
			if (codDocumento!=null && !codDocumento.equals("null")){
				download_url+="?codDocumento="+codDocumento;
				titolo=codDocumento+".pdf";
			}
			else {
				
				download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
				titolo=provenienza+"_"+idDocumento+".pdf";
			}
			
				if (context.getRequest().getAttribute("titolo")!=null)
					titolo= (String)context.getRequest().getAttribute("titolo");
				
			//Cartella temporanea sull'APP
	        String path_doc = getWebInfPath(context,"tmp_documentale");
	        //Creare il file ...(ispirarsi a GestoreGlifo servlet)
	        
	        File theDir = new File(path_doc);
         	theDir.mkdirs();

         	File inputFile = new File(path_doc+titolo);
         	if (!inputFile.exists())
         		inputFile.createNewFile();
         	URL copyurl;
         	InputStream outputFile=null;
        copyurl = new URL(download_url);
        try {
        outputFile = copyurl.openStream();
        FileOutputStream out2 = new FileOutputStream(inputFile);
        int c;
        while ((c = outputFile.read()) != -1)
            out2.write(c);
        outputFile.close();
        out2.close();
	 
        String fileType = "";
         
      //  if (new File(fileName).exists()){
         // Find this file id in database to get file name, and file type

         // You must tell the browser the file type you are going to send
         // for example application/pdf, text/plain, text/html, image/jpg
         context.getResponse().setContentType(fileType);

         // Make sure to show the download dialog
         context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);

         // Assume file name is retrieved from database
         // For example D:\\file\\test.pdf

         File my_file = new File(inputFile.getAbsolutePath());

         // This should send the file to browser
         OutputStream out = context.getResponse().getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
  
         return ("-none-");	
}
         
         catch (ConnectException e1){
	        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
	        }
	        
		
		
		}

		
	
public String executeCommandListaDocumentiByTipo(ActionContext context){
		
		//Catturo tutti i possibili campi che possono arrivare per i diversi tipi di certificato
	String orgIdString = context.getRequest().getParameter("orgId");
	String ticketIdString = context.getRequest().getParameter("ticketId");
	String tipo = context.getRequest().getParameter("tipo");
	String idCUString = context.getRequest().getParameter("idCU");
	String url = context.getRequest().getParameter("url");
	String htmlcode = context.getRequest().getParameter("htmlcode");
	
	int orgId=-1, ticketId=-1, idCU=-1;
	//Se sono arrivati i campi, convertili in interi
		if (orgIdString!=null && !orgIdString.equals("null"))
			orgId=Integer.parseInt(orgIdString);
		if (ticketIdString!=null && !ticketIdString.equals("null"))
			ticketId=Integer.parseInt(ticketIdString);
		if (idCUString!=null && !idCUString.equals("null"))
			idCU=Integer.parseInt(idCUString);
		
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI_DPAT");

		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+lista_url.toString());

		URL obj;
		try {
			obj = new URL(lista_url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(ticketId);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("idCU");
		requestParams.append("=").append(idCU);
		requestParams.append("&");
		requestParams.append("orgId");
		requestParams.append("=").append(orgId);
		requestParams.append("&");
		requestParams.append("sostituisciCaller");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_DOCUMENTALE_SOSTITUISCI_CALLER"));
		requestParams.append("&");
		requestParams.append("url");
		requestParams.append("=").append(url);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("Conn: "+conn.toString());
		conn.getContentLength();
				
		String codDocumento="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			 ArrayList<String> listaDocs = new ArrayList<String>();
			 for(int i = 0 ; i < jo.length(); i++){
				    String riga = jo.get(i).toString();
				       listaDocs.add(riga);
				}
			context.getRequest().setAttribute("listaDocumenti", listaDocs);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
		context.getRequest().setAttribute("tipo", tipo);
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		context.getRequest().setAttribute("idCU", String.valueOf(idCU));
		context.getRequest().setAttribute("url", url);
		context.getRequest().setAttribute("htmlcode", htmlcode);
		return "listaDocTipoOK";
	}
	
public String executeCommandListaDocumenti(ActionContext context) throws SQLException, UnknownHostException {
	//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
	
	int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	
	Connection db = null;
	try {
		db = this.getConnection(context);
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
		org.getOnlyRagioneSociale(db, orgId);
		context.getRequest().setAttribute("OrgDetails", org);
	}
	catch(SQLException e1){
		e1.printStackTrace();
	}
	finally{
		this.freeConnection(context, db);
	}
	
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI_DPAT");

	//STAMPE
	System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

	URL obj;
	
		try {
			obj = new URL(url);
		
	
	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("orgId");
	requestParams.append("=").append(orgId);
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	

	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("Conn: "+conn.toString());
	conn.getContentLength();
			
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		 ArrayList<String> listaDocs = new ArrayList<String>();
		 for(int i = 0 ; i < jo.length(); i++){
			    String riga = jo.get(i).toString();
			       listaDocs.add(riga);
			}
		context.getRequest().setAttribute("listaDocumenti", listaDocs);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			conn.disconnect();
		}
	return "listaDocOK";
}

	
	

}
