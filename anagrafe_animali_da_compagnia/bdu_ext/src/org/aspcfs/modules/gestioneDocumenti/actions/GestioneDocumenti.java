/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleDocumentoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.richiesteerratacorrige.actions.GestioneRichiesteErrataCorrige;
import org.aspcfs.utils.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneDocumenti extends CFSModule {
	
	public GestioneDocumenti(){
		
	}
	
	public String executeCommandDefault(ActionContext context) {

		try {
			return executeCommandServerDocumentaleHome(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

	
	public String executeCommandGeneraPDF(ActionContext context) throws SQLException, IOException, NoSuchAlgorithmException {
		
		
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();
		
		String ignoraLista = context.getRequest().getParameter("generaNonLista");
		String tipo = context.getRequest().getParameter("tipo"); //tipo certificato
		
		//CONTROLLO IL RUOLO DELL'UTENTE E REINDIRIZZO ALLA LISTA DEI DOCUMENTI SE E' HELPDESK
		//IGNORALISTA: NEL CASO DALLA MASCHERA DI LISTA DOCUMENTI ABBIA FATTO GENERA, IGNORA QUESTO PASSAGGIO PER NON TORNARE DI NUOVO ALLA LISTA
		if ( (ignoraLista==null || !ignoraLista.equals("ok")) && (user.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || user.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2")) || tipo.equals("PrintSchedaAdozioneCani") || tipo.equals("PrintSchedaMorsicatura")))
			return(executeCommandListaDocumentiByTipo(context));
						  
		String url = null;
		String test = ""; //conterrà l'html recuperato
		String actionName =""; //nome action da chiamare per recuperare la pagina
		String htmlcode = context.getRequest().getParameter("html"); //lo passa solo barcode cani in canile
		test = htmlcode;
		
		//Catturo parametri per il documento
		String idSpecieString = context.getRequest().getParameter("IdSpecie");
		String idAnimaleString = context.getRequest().getParameter("IdAnimale");
		String id_microchip= "";
			if (context.getRequest().getParameter("idMicrochip")!=null && !context.getRequest().getParameter("idMicrochip").equals("null"))
				id_microchip = context.getRequest().getParameter("idMicrochip");
		String idLineaString = context.getRequest().getParameter("idLinea");
		String idEventoString = context.getRequest().getParameter("idEvento");
		String glifo =  context.getRequest().getParameter("glifo");
		String generazionePulita =  context.getRequest().getParameter("generazionePulita");
		boolean statico = false;
		boolean sendMail = false;
		String subjectMail = "";
		String bodyMail = "";
		String toMail = "";
		String tabellaTipiName = ApplicationProperties.getProperty("TABELLA_LISTA_TIPI_DOCUMENTI");
		
		String tipo_cmd = ""; //comando relativo al tipo di certificato
		String tipoTimbro = "AnagrafeAnimali";
		
	//	ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
	//	String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + "/" + prefs.get("APPLICATION.NAME")+"/";
		String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
		
		//System.out.println("[DOCUMENTALE BDU] urlChiamante: "+urlChiamante);
				
		Connection dbBdu = this.getConnection(context);
		//Catturo info sul campo statico
		
		StringBuffer sql = new StringBuffer();
 		sql.append("SELECT * from "+tabellaTipiName+" where tipo= ? ");
		PreparedStatement pst = dbBdu.prepareStatement(sql.toString());
		pst.setString(1, tipo);
		ResultSet rs = pst.executeQuery();
	    
		if(rs.next()){
		    		statico = rs.getBoolean("unico");
		    		sendMail = rs.getBoolean("send_mail_on_generate");
		    		toMail = rs.getString("mail_to");
		    		subjectMail = rs.getString("mail_subject");
		    		bodyMail = rs.getString("mail_body");
	 		    	}
		
	  		
		int idSpecie = -1, idAnimale = -1, idLinea = -1, idEvento=-1;
		
		//Se ho ricevuto parametri, cast in interi
		if (idSpecieString!=null && !idSpecieString.equals("null"))
			idSpecie=Integer.parseInt(idSpecieString);
		if (idAnimaleString!=null && !idAnimaleString.equals("null"))
			idAnimale=Integer.parseInt(idAnimaleString);
		if (idLineaString!=null && !idLineaString.equals("null"))
			idLinea=Integer.parseInt(idLineaString);
		if (idEventoString!=null && !idEventoString.equals("null"))
			idEvento=Integer.parseInt(idEventoString);
		
		//Recupero l'identificativo dell'animale
	
		Animale thisAnimale = null;
		
		if (idAnimale>0)
			{thisAnimale= new Animale(dbBdu, idAnimale);
		
		dbBdu.close();
		
		if (thisAnimale.getMicrochip()!=null && !thisAnimale.getMicrochip().equals(""))
			id_microchip = thisAnimale.getMicrochip();
		else if (thisAnimale.getTatuaggio()!=null && !thisAnimale.getTatuaggio().equals(""))
			id_microchip = thisAnimale.getTatuaggio();
		else
			id_microchip="N.D.";}
		this.freeConnection(context, dbBdu);
		
		//Recupero la action a seconda del tipo di certificato ricevuto
		if (tipo.equals("PrintRichiestaCampioniRabbia") || tipo.equals("PrintCertificatoVaccinazioneAntiRabbia"))
			actionName="ProfilassiRabbia.do";
		else if (tipo.equals("PrintDocumentoListaCani")) {
			actionName="ElencoCaniInCanile.do";
			tipoTimbro ="ElencoCaniInCanile";
			}
		else
			actionName="AnimaleAction.do";
		
		try {
			// cattura tipo di documento
			tipo_cmd = tipo;

			if (actionName.equals("ElencoCaniInCanile.do"))
				url =urlChiamante+actionName+"?command="
						+ tipo_cmd + "&id=" + idLinea+"&invioMail="+sendMail; //PAGINA DA LEGGERE E CODIFICARE
			else if (tipo.equals("PrintRichiestaAdozioneColonia") || tipo.equals("PrintRichiestaAdozione") || tipo.equals("PrintRichiestaAdozioneVersoAssocCanili")){
				url = urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idAnimale=" + idAnimale	+ "&idEvento=" + idEvento; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintRichiestaCampioniRabbia")){
				url =	urlChiamante+actionName+"?command="
						+ tipo_cmd + "&microchip=" + id_microchip; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintSchedaAdozioneCani")){
				url =	urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idSpecie=" + idSpecie+ "&idAnimale=" + idAnimale+ "&microchip=" + id_microchip; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintSchedaMorsicatura")){
				url =	urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idSpecie=" + idSpecie+ "&idAnimale=" + idAnimale+ "&microchip=" + id_microchip + "&idScheda=" + idEvento; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintCertificatoVaccinazioneAntiRabbia")){
				url =		urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idAnimale=" + idAnimale+ "&idSpecie=" + idSpecie; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintDocumentoMutilazione")){
				url =		urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idAnimale=" + idAnimale+ "&idSpecie=" + idSpecie+ "&idEvento=" + idEvento; //PAGINA DA LEGGERE E CODIFICARE
			}
			else if (tipo.equals("PrintDocumentoAllontanamento")){
				url =		urlChiamante+actionName+"?command="
						+ tipo_cmd + "&idAnimale=" + idAnimale+ "&idSpecie=" + idSpecie+ "&idEvento=" + idEvento; //PAGINA DA LEGGERE E CODIFICARE
			}
			else
				url =	urlChiamante+actionName+"?command="
					+ tipo_cmd + "&idAnimale=" + idAnimale	+ "&idSpecie=" + idSpecie + "&idEvento=" + idEvento + "&microchip="+id_microchip+"&idLinea="+idLineaString; //PAGINA DA LEGGERE E CODIFICARE
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//STAMPE
		//System.out.println("[DOCUMENTALE BDU] Dati catturati:\n Tipo: "+tipo+"\nTipoCertificato: "+tipo_cmd+"\nSpecie: "+idSpecie+"\nAnimale: "+idAnimale+"\nMicrochip: "+id_microchip+"\n [DOCUMENTALE BDU] Url generato(documento da catturare): "+url);
	
		HttpURLConnection conn=null;
		try {
		
		context.getRequest().setAttribute("html", test);
		
		//CONTROLLO IL RUOLO DELL'UTENTE E DESELEZIONO GENERAZIONE PULITA SE NON E' HELPDESK
		if (user.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) && user.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))) 
			generazionePulita="no";
		
		String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");

		String urlOriginale = url;
		//STAMPE
		System.out.println("\n [DOCUMENTALE] Url generato(chiamata a servlet): "+urlDocumentale);
		if (test==null)
			test = "";
		
		URL obj;
		obj = new URL(urlDocumentale);
		conn = null;
		
			conn = (HttpURLConnection) obj.openConnection();
		
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		//Passo alla servlet i parametri: SORGENTE HTML, ID IP UTENTE, TIPO DOCUMENTO, NOME APPLICATIVO (BDU), GLIFO, TIPO TIMBRO (SOTTOCARTELLA), ID ANIMALE E MICROCHIP
		StringBuffer requestParams = new StringBuffer();
		requestParams.append(URLEncoder.encode("html", "ISO-8859-1"));
		requestParams.append("=").append(URLEncoder.encode(test, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(user_id);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
		requestParams.append("&");
		requestParams.append("aggiungiGlifo");
		requestParams.append("=").append(glifo);
		requestParams.append("&");
		requestParams.append("tipoTimbro");
		requestParams.append("=").append(tipoTimbro);
		requestParams.append("&");
		requestParams.append("idAnimale");
		requestParams.append("=").append(idAnimale);
		requestParams.append("&");
		requestParams.append("microchip");
		requestParams.append("=").append(id_microchip);
		requestParams.append("&");
		requestParams.append("idLinea");
		requestParams.append("=").append(idLinea);
		requestParams.append("&");
		requestParams.append("idEvento");
		requestParams.append("=").append(idEvento);
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
		requestParams.append("invioMail");
		requestParams.append("=").append(sendMail);
		requestParams.append("&");
		requestParams.append("toMail");
		requestParams.append("=").append(toMail);
		requestParams.append("&");
		requestParams.append("subjectMail");
		requestParams.append("=").append(subjectMail);
		requestParams.append("&");
		requestParams.append("bodyMail");
		requestParams.append("=").append(bodyMail);
		requestParams.append("&");
		requestParams.append("idSessione");
		requestParams.append("=").append(context.getSession().getId()); 
		requestParams.append("&");
		requestParams.append("caller");
		requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));


		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE BDU] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
				
		String codDocumento="", titolo="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer result = new StringBuffer();
		
		//Leggo l'output: l'header del documento generato e il nome assegnatogli
		//Se il documento è già stato generato da meno di 2 minuti, viene restituito il vecchio
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto); }
			in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
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
		return executeCommandDownloadPDF(context);
			
	
		}

	public String executeCommandListaDocumenti(ActionContext context) throws SQLException, UnknownHostException {
		//MOSTRA LISTA DOCUMENTI TIMBRATI PER L'ANIMALE
		
		
		int idAnimale = -1, idLinea = -1;
		
		if (context.getRequest().getParameter("animaleId")!=null)
			idAnimale = Integer.parseInt(context.getRequest().getParameter("animaleId"));
		if (context.getRequest().getParameter("idLinea")!=null)
			idLinea = Integer.parseInt(context.getRequest().getParameter("idLinea"));
		
		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

		URL obj;
		
			try {
				obj = new URL(url);
			
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("idAnimale");
		requestParams.append("=").append(idAnimale);
		requestParams.append("&");
		requestParams.append("idLinea");
		requestParams.append("=").append(idLinea);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
		

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE BDU] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
				
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
//			 ArrayList<String> listaDocs = new ArrayList<String>();
//			 for(int i = 0 ; i < jo.length(); i++){
//				    String riga = jo.get(i).toString();
//				       listaDocs.add(riga);
//				}
//			context.getRequest().setAttribute("listaDocumenti", listaDocs);
			
			DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
			 docList.creaElenco(jo);
			 context.getRequest().setAttribute("listaDocumenti", docList);

			 
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
			
			Connection db = this.getConnection(context);
			Animale thisAnimale = null;
				
			thisAnimale = (Animale) context.getRequest().getAttribute("animale");
			if (thisAnimale == null) {
				String tempanimaleId = context.getRequest().getParameter(
						"animaleId");
				if (tempanimaleId == null) {
					tempanimaleId = (String) context.getRequest().getAttribute(
							"animaleId");
				}

				Integer tempid = null;

				if (tempanimaleId != null) {
					tempid = Integer.parseInt(tempanimaleId);

					thisAnimale = new Animale(db, tempid);
				}
			}
			this.freeConnection(context, db);
			context.getRequest().setAttribute("Animale", thisAnimale);
		return "listaDocOK";
	}
	
	public String executeCommandListaDocumentiByTipo(ActionContext context) throws SQLException, UnknownHostException {
		
		//Catturo tutti i possibili campi che possono arrivare per i diversi tipi di certificato
		String idSpecieString = context.getRequest().getParameter("IdSpecie");
		String idAnimaleString = context.getRequest().getParameter("IdAnimale");
		String idMicrochip = context.getRequest().getParameter("id_microchip");
		String idTipo = context.getRequest().getParameter("tipo");
		String idLineaString = context.getRequest().getParameter("idLinea");
		String idEventoString = context.getRequest().getParameter("idEvento");
		
		int idSpecie =-1, idAnimale=-1, idLinea=-1, idEvento=-1;
		
		//Se sono arrivati i campi, convertili in interi
		if (idSpecieString!=null && !idSpecieString.equals("null"))
			idSpecie=Integer.parseInt(idSpecieString);
		if (idAnimaleString!=null && !idAnimaleString.equals("null"))
			idAnimale=Integer.parseInt(idAnimaleString);
		if (idLineaString!=null && !idLineaString.equals("null"))
			idLinea=Integer.parseInt(idLineaString);
		if (idEventoString!=null && !idEventoString.equals("null"))
			idEvento=Integer.parseInt(idEventoString);
		
		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_DOCUMENTI");

		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

		URL obj;
		try {
			obj = new URL(url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(idTipo);
		requestParams.append("&");
		requestParams.append("idAnimale");
		requestParams.append("=").append(idAnimale);
		requestParams.append("&");
		requestParams.append("idLinea");
		requestParams.append("=").append(idLinea);
		requestParams.append("&");
		requestParams.append("idEvento");
		requestParams.append("=").append(idEvento);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
		

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE BDU] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
		conn.getContentLength();
				
		String codDocumento="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
//			 ArrayList<String> listaDocs = new ArrayList<String>();
//			 for(int i = 0 ; i < jo.length(); i++){
//				    String riga = jo.get(i).toString();
//				       listaDocs.add(riga);
//				}
//			context.getRequest().setAttribute("listaDocumenti", listaDocs);
			
			DocumentaleDocumentoList docList = new DocumentaleDocumentoList();
			 docList.creaElenco(jo);
			 context.getRequest().setAttribute("listaDocumenti", docList);
			
			Connection db = this.getConnection(context);
			Animale thisAnimale = (Animale) context.getRequest().getAttribute("animale");
			if (thisAnimale == null) {
				String tempanimaleId = context.getRequest().getParameter(
						"IdAnimale");
				if (tempanimaleId == null) {
					tempanimaleId = (String) context.getRequest().getAttribute(
							"IdAnimale");
				}
				Integer tempid = null;
	
				if (tempanimaleId != null && !tempanimaleId.equals("null")) {
					tempid = Integer.parseInt(tempanimaleId);
					if (tempid>0)
						thisAnimale = new Animale(db, tempid);
				}
			}
			context.getRequest().setAttribute("Animale", thisAnimale);
			context.getRequest().setAttribute("Microchip", idMicrochip);
			context.getRequest().setAttribute("tipo", idTipo);
			this.freeConnection(context, db);
		
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
		return "listaDocTipoOK";
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
			String provenienza = ApplicationProperties.getProperty("APP_NAME_BDU");
		
			String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
			
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

	
	public String executeCommandServerDocumentaleHome(ActionContext context) throws SQLException, IOException {
		if (!hasPermission(context, "server_documentale-view")) {
			return ("PermissionError");
		}
		return "documentaleHomeOK";
	}
	
	private String gestioneCaratteriSpeciali(String test){
		
		test=test.replaceAll("à", "###agrave###");
		test=test.replaceAll("è", "###egrave###");
		test=test.replaceAll("ì", "###igrave###");
		test=test.replaceAll("ò", "###ograve###");
		test=test.replaceAll("ù", "###ugrave###");
		
		test=test.replaceAll("á", "###aacute###");
		test=test.replaceAll("é", "###eacute###");
		test=test.replaceAll("í", "###iacute###");
		test=test.replaceAll("ó", "###oacute###");
		test=test.replaceAll("ú", "###uacute###");
		
		test=test.replaceAll("À", "###amaiuscgrave###");
		test=test.replaceAll("È", "###emaiuscgrave###");
		test=test.replaceAll("Ì", "###imaiuscgrave###");
		test=test.replaceAll("Ò", "###omaiuscgrave###");
		test=test.replaceAll("Ù", "###umaiuscgrave###");
		
		test=test.replaceAll("Á", "###amaiuscacute###");
		test=test.replaceAll("É", "###emaiuscacute###");
		test=test.replaceAll("í", "###imaiuscacute###");
		test=test.replaceAll("Ó", "###omaiuscacute###");
		test=test.replaceAll("Ú", "###umaiuscacute###");
			
		test=test.replaceAll("°", "###grado###");
		
		
		return test;
	}
	
	public boolean aggiornaIdAnimaleMicrochip(int idAnimale, String microchip,ActionContext context) throws IOException 
	{
		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_DOCUMENTI");
		// STAMPE

		URL obj;
		try {
			obj = new URL(url);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
			requestParams.append("&");
			requestParams.append("idAnimale");
			requestParams.append("=").append(idAnimale);
			requestParams.append("&");
			requestParams.append("microchip");
			requestParams.append("=").append(microchip);
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("aggiornaMicrochipIdAnimale");
			

			System.out.println("AGGIORNAMENTO ID ANIMALE MICROCHIP - URL : "+url+"&"+requestParams.toString());
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE BDU] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
			conn.getContentLength();


			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto);
			}
			in.close();



		} catch (ConnectException e1) {
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			throw e1 ;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NELL'AGGIORNAMENTO");
			context.getRequest().setAttribute("label", "documents");
			throw e ;
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NELL'AGGIORNAMENTO");
			context.getRequest().setAttribute("label", "documents");
			throw e ;
		}

		finally {
			conn.disconnect();
		}


		return true ;



	}
	
	public String executeCommandGeneraPDFRichiestaErrataCorrige(ActionContext context) throws SQLException, IOException, NumberFormatException, IndirizzoNotFoundException{ 
	 	
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}
		
			//URL oracle = null;
			String test = ""; //conterra' l'html recuperato
			String id = context.getRequest().getParameter("id");
	    	String idAnimale = context.getRequest().getParameter("idAnimale");
			String actionName = "GestioneRichiesteErrataCorrige.do"; //nome action da cchiamare
			String urlOriginale = "";
			String extra = context.getRequest().getParameter("extra");
			
			String riferimentoId = context.getRequest().getParameter("riferimentoId");
			String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");
			
			idAnimale = riferimentoId;
		
			String generazionePulita = "";
		
			String idSessione = context.getRequest().getSession().getId();
			
			String tipoCertificato ="RichiestaErrataCorrige";
			
			String nomeFile = "RichiestaErrataCorrige";
			String tipoTimbro = "AnagrafeAnimali";

		
			String urlChiamante = "http://"+context.getRequest().getLocalAddr()+":"+context.getRequest().getLocalPort() + context.getRequest().getContextPath() + "/";
			
			System.out.println("[DOCUMENTALE BDU] urlChiamante: "+urlChiamante);
			
			extra ="ID Errata Corrige: "+id;
			
			HttpURLConnection conn=null;
				
				String url          	= urlChiamante;
				String metodo="";
				metodo = "ModuloRichiestaErrataCorrige";
				
				url+=actionName+
							"?command="+ metodo + "&id=" + id ; //PAGINA DA LEGGERE E CODIFICARE
				
				//PAGINA DA LEGGERE E CODIFICARE
				urlOriginale = url;
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = context.getIpAddress();
			int user_id = user.getUserRecord().getId();

			String urlDocumentale = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_CODIFICA");
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
				requestParams.append("tipoCertificato");
				requestParams.append("=").append(tipoCertificato);
				requestParams.append("&");
				requestParams.append("idAnimale");
				requestParams.append("=").append(idAnimale);
				requestParams.append("&");
				requestParams.append("extra");
				requestParams.append("=").append(extra);
				requestParams.append("&");
				requestParams.append("app_name");
				requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
				requestParams.append("&");
				requestParams.append("tipoTimbro");
				requestParams.append("=").append(tipoTimbro);
				requestParams.append("&");
				requestParams.append("idUtente");
				requestParams.append("=").append(user_id);
				requestParams.append("&");
				requestParams.append("ipUtente");
				requestParams.append("=").append(ip);
				requestParams.append("&");
				requestParams.append("urlOriginale");
				requestParams.append("=").append(URLEncoder.encode(urlOriginale, "ISO-8859-1"));
				requestParams.append("&");
				requestParams.append("caller");
				requestParams.append("=").append(URLEncoder.encode(urlChiamante, "ISO-8859-1"));
				requestParams.append("&");
				requestParams.append("idSessione");
				requestParams.append("=").append(context.getSession().getId());
				requestParams.append("&");
				requestParams.append("generazionePulita");
				requestParams.append("=").append(generazionePulita);
				
				OutputStreamWriter wr = new OutputStreamWriter(conn
							.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE BDU] CONNESSIONE: "+conn.toString()+"?"+requestParams.toString());
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
				
				
				if (codDocumento!=null){
					String file = scaricaFileInLocale(context);
					
					Connection db = null;
					try {
						db = this.getConnection(context);
						GestioneRichiesteErrataCorrige.associaHeaderEInviaMail(context, db, Integer.parseInt(id), codDocumento, file);
						} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
						this.freeConnection(context, db);
					}
				}
				
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
	

public String scaricaFileInLocale(ActionContext context) throws SQLException, IOException {
	
	//recupero l'id timbro
		String codDocumento 		=  null;
			codDocumento = context.getRequest().getParameter("codDocumento");
		if (codDocumento==null)
			codDocumento = (String)context.getRequest().getAttribute("codDocumento");
		String idDocumento 				= null;
		idDocumento = context.getRequest().getParameter("idDocumento");
		
		String titolo="";
		String provenienza = ApplicationProperties.getProperty("APP_NAME_BDU");
	
		String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
		
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
 
     return inputFile.getAbsolutePath();	
}
     
     catch (ConnectException e1){
        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			return "documentaleError";
        }
        
	}
}
