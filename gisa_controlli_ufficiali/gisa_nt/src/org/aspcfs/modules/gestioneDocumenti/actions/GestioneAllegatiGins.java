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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;









import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;


import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoGins;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoGinsList;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.gestionepratiche.base.Pratica;
import org.aspcfs.modules.gestionepratiche.base.Richiesta;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;












import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneAllegatiGins extends CFSModule {
	
	private byte[] ba = null;
	private String idAggiuntaPratica = null;
	private String numeroPratica = null;
	private String codiceAllegato = null;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension="";
	private String f1="";
	private String actionOrigine="";
	private String tipoAllegato="";
	
	public String getActionOrigine() {
		return actionOrigine;
	}

	public void setActionOrigine(String actionOrigine) {
		this.actionOrigine = actionOrigine;
	}
	
	public byte[] getBa() {
		return ba;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setBa(byte[] ba) {
		this.ba = ba;
	}
		
	
	
	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getFileDimension() {
		return fileDimension;
	}

	public void setFileDimension(String fileDimension) {
		this.fileDimension = fileDimension;
	}

	public String executeCommandAllegaFile(ActionContext context) throws IOException, SQLException, IllegalStateException, ServletException, FileUploadException, ParseException{
		if (!hasPermission(context, "documentale_documents-add")) {
		return ("PermissionError");
	}

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			return "documentaleError";
		}
		
	String op ="";
	String filePath = this.getPath(context, "accounts");
	HttpMultiPartParser multiPart = new HttpMultiPartParser();
   	
	HashMap parts = multiPart.parseData(context.getRequest(), filePath);
    String idAggiuntaPratica = (String) parts.get("idAggiuntaPratica");
    String numeroPratica = (String) parts.get("numeroPratica");
    String codiceAllegato = (String) parts.get("codiceAllegato");
    String subject = (String) parts.get("subject");
    String tipoAllegato = (String) parts.get("tipoAllegato");
    actionOrigine = (String) parts.get("actionOrigine");
    
    setIdAggiuntaPratica(idAggiuntaPratica);
    setNumeroPratica(numeroPratica);
    setCodiceAllegato(codiceAllegato);
    setOggetto(subject);
    setTipoAllegato(tipoAllegato);
    
    context.getRequest().setAttribute("idAggiuntaPratica", getIdAggiuntaPratica());
    context.getRequest().setAttribute("numeroPratica", getNumeroPratica()); 
	context.getRequest().setAttribute("codiceAllegato",getCodiceAllegato());
    
    int fileSize = -1;
      
      if ((Object)  parts.get("file1") instanceof FileInfo) {
          //Update the database with the resulting file
          FileInfo newFileInfo = (FileInfo) parts.get("file1");
          //Insert a file description record into the database
          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
          thisItem.setLinkModuleId(Constants.ACCOUNTS);
          thisItem.setEnteredBy(getUserId(context));
          thisItem.setModifiedBy(getUserId(context));
          thisItem.setSubject(subject);
          thisItem.setClientFilename(newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename());
          setFilename(newFileInfo.getRealFilename());
          thisItem.setVersion(1.0);
          thisItem.setSize(newFileInfo.getSize());
          fileSize = thisItem.getSize();
          setFileDimension(String.valueOf(fileSize));
         }
      
      int maxFileSize=-1;
      int mb1size = 1048576;
      if (ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI")!=null)
    	  maxFileSize=Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
      
      if (fileSize > maxFileSize){ //2 mb
       	  String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
    	  context.getRequest().setAttribute("messaggioPost", "Errore! Selezionare un file con dimensione inferiore a "+maxSizeString +" MB.");
    	  context.getRequest().setAttribute("tipo", tipoAllegato);
			return "documentaleAllegatiError";
      }
      
      f1 = filePath + filename;
      
      
	  File file = new File(f1);
	  byte []buffer = new byte[(int) file.length()];
	    InputStream ios = null;
	    try {
	        ios = new FileInputStream(file);
	        if ( ios.read(buffer) == -1 ) {
	            throw new IOException("EOF reached while trying to read the whole file");
	        }        
	    } finally { 
	        try {
	             if ( ios != null ) 
	                  ios.close();
	        } catch ( IOException e) {
	      }
	    }

	ba = buffer;
	String ip = context.getIpAddress();
	
	String baString="";
	baString = byteArrayToString();
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GINS_CARICA");
	//STAMPE
	
	URL obj;
	try {
		obj = new URL(url);
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("baString");
		requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GINS"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipoAllegato);
		requestParams.append("&");
		requestParams.append("idAggiuntaPratica");
		requestParams.append("=").append(getIdAggiuntaPratica());
		requestParams.append("&");
		requestParams.append("numeroPratica");
		requestParams.append("=").append(getNumeroPratica());
		requestParams.append("&");
		requestParams.append("codiceAllegato");
		requestParams.append("=").append(getCodiceAllegato());
		requestParams.append("&");
		requestParams.append("oggetto");
		requestParams.append("=").append(getOggetto());
		requestParams.append("&");
		requestParams.append("filename");
		requestParams.append("=").append(getFilename());
		requestParams.append("&");
		requestParams.append("fileDimension");
		requestParams.append("=").append(getFileDimension());
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(getUserId(context));
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(ip);
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		
		conn.getContentLength();
		
		String messaggioPost = "OK! Caricamento completato con successo.";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		
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
		context.getRequest().setAttribute("codiceAllegato", codiceAllegato);

		} 
	 catch (ConnectException e1){
        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
        	context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
        }
	catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
	        	context.getRequest().setAttribute("label", "documents");
				return "documentaleAllegatiError";
			} catch (IOException e) {
				e.printStackTrace();
				context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
	        	context.getRequest().setAttribute("label", "documents");
				return "documentaleAllegatiError";
			} 
	
	finally{
		conn.disconnect();
	}
	
		return "allegaAllegatoOk";

}
		
	
public String executeCommandDownloadPDF(ActionContext context) throws SQLException, IOException {
		
		//recupero l'id timbro
			String codDocumento 		=  null;
				codDocumento = context.getRequest().getParameter("codDocumento");
			if (codDocumento==null)
				codDocumento = (String)context.getRequest().getAttribute("codDocumento");
			String idDocumento 				= null;
			
			String tipoDocumento 		=  null;
			tipoDocumento = context.getRequest().getParameter("tipoDocumento");
		if (tipoDocumento==null)
			tipoDocumento = (String)context.getRequest().getAttribute("tipoDocumento");
		
		String nomeDocumento 		=  null;
		nomeDocumento = context.getRequest().getParameter("nomeDocumento");
	if (nomeDocumento==null)
		nomeDocumento = (String)context.getRequest().getAttribute("nomeDocumento");
	
		String estensione = "."+tipoDocumento;
		
			idDocumento = context.getRequest().getParameter("idDocumento");
			
			String titolo="";
			String provenienza = ApplicationProperties.getProperty("APP_NAME_GINS");
		
			String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
			
			if (codDocumento!=null && !codDocumento.equals("null")){
				download_url+="?codDocumento="+codDocumento;
				titolo=codDocumento+estensione;
			}
			else {
				
				download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
				titolo=provenienza+"_"+idDocumento+estensione;
			}
			
			if (context.getRequest().getAttribute("titolo")!=null)
				titolo= (String)context.getRequest().getAttribute("titolo");
			
			if (nomeDocumento!=null && !nomeDocumento.equals("null"))
				titolo=nomeDocumento;
				//titolo=codDocumento+"_"+nomeDocumento;
				
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
             try {  
            	 out.write(buffer, 0, length);
            	 }  
             	catch (Exception e1){
             			in.close();
             			System.out.println("[DOCUMENTALE GISA] Sessione invalidata");
             			return ("-none-");	
             	}
         }
         in.close();
         out.flush();
         return ("-none-");	
    }
         
         catch (ConnectException e1){
	        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
	        	context.getRequest().setAttribute("label", "documents");
				return "documentaleAllegatiError";
	        }
	        
		
		
		}


private String byteArrayToString () throws UnsupportedEncodingException{
	String s = new String(ba, "ISO-8859-1");
	return s;
	
	
}

public String getTipoAllegato() {
	return tipoAllegato;
}

public void setTipoAllegato(String tipoAllegato) {
	if (tipoAllegato==null || tipoAllegato.equals(""))
		tipoAllegato="Allegato";
	else
		this.tipoAllegato = tipoAllegato;
}

public String getNumeroPratica() {
	return numeroPratica;
}

public void setNumeroPratica(String numeroPratica) {
	this.numeroPratica = numeroPratica;
}

public String getCodiceAllegato() {
	return codiceAllegato;
}

public void setCodiceAllegato(String codiceAllegato) {
	this.codiceAllegato = codiceAllegato;
}

public String executeCommandPrepareUploadAllegato(ActionContext context) throws SQLException, IOException {
	String idAggiuntaPratica = context.getRequest().getParameter("idAggiuntaPratica");
	context.getRequest().setAttribute("idAggiuntaPratica", idAggiuntaPratica);
	String numeroPratica = context.getRequest().getParameter("numeroPratica");
	context.getRequest().setAttribute("numeroPratica", numeroPratica);
	String codiceAllegato = context.getRequest().getParameter("codiceAllegato");
	context.getRequest().setAttribute("codiceAllegato", codiceAllegato);
	String messaggioPost = (String) context.getRequest().getAttribute("messaggioPost");
		if (messaggioPost!=null && !messaggioPost.equals("null"))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
		String tipo = (String) context.getRequest().getAttribute("tipo");
		if (tipo!=null && !tipo.equals("null"))
			context.getRequest().setAttribute("tipo", tipo);
	
	return "prepareUploadAllegatoOk";
	}

public String getIdAggiuntaPratica() {
	return idAggiuntaPratica;
}

public void setIdAggiuntaPratica(String idAggiuntaPratica) {
	this.idAggiuntaPratica = idAggiuntaPratica;
}

public static String aggiornaNumeroPratica(int idUtente, String idAggiuntaPratica, String numeroPratica, String codici_allegati, String idComunePratica) throws SQLException, IOException {

		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI_GINS");
		// STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
		URL obj;

		try {
			obj = new URL(url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GINS"));
			requestParams.append("&");
			requestParams.append("numeroPratica");
			requestParams.append("=").append(numeroPratica);
			requestParams.append("&");
			requestParams.append("idAggiuntaPratica");
			requestParams.append("=").append(idAggiuntaPratica);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("aggiornaNumeroPratica");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(idUtente);
			requestParams.append("&");
			requestParams.append("codiciAllegati");
			requestParams.append("=").append(codici_allegati);
			requestParams.append("&");
			requestParams.append("idComunePratica");
			requestParams.append("=").append(idComunePratica);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
			conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	return "";
}

	public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {
	
		if (!hasPermission(context, "documentale_documents-view")) {
			return ("PermissionError");
		}
		String numeroPraticaInput = "";
		String idComunePratica = "";
		String desc_operatore = "";
		String alt_id = null;
		String stab_id = null;
		
		try {numeroPraticaInput = context.getParameter("numeroPratica").toString();} catch (Exception e){}
		try {idComunePratica = context.getParameter("idComunePratica").toString();} catch (Exception e){}
		try {desc_operatore = context.getParameter("desc_operatore").toString();} catch (Exception e){}
		try {alt_id = context.getParameter("alt_id").toString();} catch (Exception e){}
		try {stab_id = context.getParameter("stab_id").toString();} catch (Exception e){}
		
		
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_GINS");
		// STAMPE
	
		URL obj;
		try {
			obj = new URL(lista_url);
	
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("numeroPratica");
			requestParams.append("=").append(numeroPraticaInput);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GINS"));
			requestParams.append("&");
			requestParams.append("idComunePratica");
			requestParams.append("=").append(idComunePratica);
	
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			
			System.out.println("url: " + obj + requestParams);
	
			conn.getContentLength();
	
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String inputLine;
			StringBuffer html = new StringBuffer();
			if (in != null) {
				html.append(in.readLine());
			}
			in.close();
			JSONArray jo = new JSONArray(html.toString());
	
			DocumentaleAllegatoGinsList docList = new DocumentaleAllegatoGinsList();
			docList.creaElenco(jo);
	
			context.getRequest().setAttribute("listaAllegati", docList);
			context.getRequest().setAttribute("numeroPraticaInput", numeroPraticaInput);
			context.getRequest().setAttribute("idComunePratica", idComunePratica);
			context.getRequest().setAttribute("desc_operatore", desc_operatore);
			context.getRequest().setAttribute("alt_id", alt_id);
			context.getRequest().setAttribute("stab_id", stab_id);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	
		return "listaAllegatiOk";
	}
	
	
	public String executeCommandSchedeSupplementariStab(ActionContext context) throws SQLException, IndirizzoNotFoundException {
		
		Connection db = null;
		
		int altId = -1;
		int stabId = -1;
		
		try {altId = (Integer) context.getRequest().getAttribute("altId");} catch (Exception e){}
		if (altId==-1)
			try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		
		try {stabId = (Integer) context.getRequest().getAttribute("stabId");} catch (Exception e){}
		if (stabId==-1)
			try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	  	
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.opu.base.Stabilimento stab = null;
			
			if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
			
		    context.getRequest().setAttribute("StabilimentoDettaglio", stab);
		     
			ArrayList<Pratica> listaPratiche = new ArrayList<Pratica>();
			listaPratiche = Pratica.getListaPratiche(db, stab.getAltId());
			
			DocumentaleAllegatoGinsList lista_schede_supplementari = new  DocumentaleAllegatoGinsList();
			
			String temp_numero_pratica = "";
			String temp_id_comune_pratica = "";
			
			for(int i=0; i< listaPratiche.size(); i++){
				temp_numero_pratica = listaPratiche.get(i).getNumeroPratica();
				temp_id_comune_pratica = String.valueOf(listaPratiche.get(i).getIdComuneRichiedente());
				
				DocumentaleAllegatoGinsList lista_allegati_temp = new DocumentaleAllegatoGinsList();
				lista_allegati_temp = (DocumentaleAllegatoGinsList) getAllegatiListaAllegatiPratica(temp_numero_pratica, temp_id_comune_pratica);
				
				for(int j=0;j<lista_allegati_temp.size(); j++){
					if( ((DocumentaleAllegatoGins) lista_allegati_temp.get(j)).getOggetto().contains("schedasup") ){
						lista_schede_supplementari.add(lista_allegati_temp.get(j));
					}
				}
			}
			
			context.getRequest().setAttribute("lista_schede_supplementari", lista_schede_supplementari);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
	
		return "listaSchedeSupplementariOk";
	}
	
	public String executeCommandListaAllegatiDettaglioPratica(ActionContext context) throws SQLException, IOException {
		
		if (!hasPermission(context, "documentale_documents-view")) {
			return ("PermissionError");
		}
		String numeroPraticaInput = "";
		String idComunePratica = "";
		String alt_id = null;
		String stab_id = null;
		
		try {numeroPraticaInput = context.getParameter("numeroPratica").toString();} catch (Exception e){}
		try {idComunePratica = context.getParameter("idComunePratica").toString();} catch (Exception e){}
		try {alt_id = context.getParameter("alt_id").toString();} catch (Exception e){}
		try {stab_id = context.getParameter("stab_id").toString();} catch (Exception e){}
		
		
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_GINS");
		// STAMPE
	
		URL obj;
		try {
			obj = new URL(lista_url);
	
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("numeroPratica");
			requestParams.append("=").append(numeroPraticaInput);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GINS"));
			requestParams.append("&");
			requestParams.append("idComunePratica");
			requestParams.append("=").append(idComunePratica);
	
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			
			System.out.println("url: " + obj + requestParams);
	
			conn.getContentLength();
	
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String inputLine;
			StringBuffer html = new StringBuffer();
			if (in != null) {
				html.append(in.readLine());
			}
			in.close();
			JSONArray jo = new JSONArray(html.toString());
	
			DocumentaleAllegatoGinsList docList = new DocumentaleAllegatoGinsList();
			docList.creaElenco(jo);
	
			context.getRequest().setAttribute("listaAllegati", docList);
			context.getRequest().setAttribute("numeroPraticaInput", numeroPraticaInput);
			context.getRequest().setAttribute("idComunePratica", idComunePratica);
			context.getRequest().setAttribute("alt_id", alt_id);
			context.getRequest().setAttribute("stab_id", stab_id);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	
		return "listaAllegatiDettaglioPraticaOK";
	}
	
	
	public DocumentaleAllegatoGinsList getAllegatiListaAllegatiPratica(String numeroPraticaInput, String idComunePratica) throws SQLException, IOException {		
		
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_GINS");
		// STAMPE
	
		URL obj;
		
		DocumentaleAllegatoGinsList docList = new DocumentaleAllegatoGinsList();
		
		try {
			obj = new URL(lista_url);
	
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("numeroPratica");
			requestParams.append("=").append(numeroPraticaInput);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GINS"));
			requestParams.append("&");
			requestParams.append("idComunePratica");
			requestParams.append("=").append(idComunePratica);
	
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			
			System.out.println("url: " + obj + requestParams);
	
			conn.getContentLength();
	
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String inputLine;
			StringBuffer html = new StringBuffer();
			if (in != null) {
				html.append(in.readLine());
			}
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			docList.creaElenco(jo);
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
	
		return docList;
	}
	
	
	
	public String executeCommandAggiungiAllegatiPraticaEsistente(ActionContext context) throws IndirizzoNotFoundException, IOException{
		
		String numeroPratica = "";
		int comune_richiedente = -1;
		String idAggiuntaPratica = "";
		String codici_allegati = "";
		String desc_operatore = "";
		int altId = -1;
		int stabId = -1;
		
		Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
		String chiave_campo;
		for (String key: parameterMap.keySet()){
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("header_"))
			{
				codici_allegati += chiave_campo.replaceFirst("header_", "") + ";;";
			}
			
		}
		System.out.println(codici_allegati);
		
		try {desc_operatore = context.getParameter("desc_operatore").toString();} catch (Exception e){}
		
		try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
		
		try {comune_richiedente = Integer.parseInt(context.getRequest().getParameter("comune_richiedente"));} catch (Exception e){}

		try {idAggiuntaPratica = context.getRequest().getParameter("idAggiuntaPratica");} catch (Exception e){}
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		
		try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
		
	   
		try {
			GestioneAllegatiGins.aggiornaNumeroPratica(getUserId(context), idAggiuntaPratica, numeroPratica, codici_allegati, String.valueOf(comune_richiedente));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		context.getRequest().setAttribute("numeroPratica", numeroPratica);
		context.getRequest().setAttribute("idComunePratica", String.valueOf(comune_richiedente));
		context.getRequest().setAttribute("desc_operatore", desc_operatore);
		context.getRequest().setAttribute("alt_id", String.valueOf(altId));
		context.getRequest().setAttribute("stab_id", String.valueOf(stabId));
		
		return "tempListaAllegatiOk";
	}
	
	public DocumentaleAllegatoList documentalePaginazione(ActionContext context, DocumentaleAllegatoList listaDocs) {
		String pag = context.getRequest().getParameter("pag");
		String pagine = context.getRequest().getParameter("pagine");

		int elementiPerPagina = 10;
		if (pagine != null && pagine.equals("no"))
			elementiPerPagina = listaDocs.size();

		int paginaIniziale = 1;
		if (pag != null && !pag.equals("null") && !pag.equals(""))
			paginaIniziale = Integer.parseInt(pag);
		long pagTot = 1;

		int i_iniz = (paginaIniziale - 1) * elementiPerPagina;
		int i_fin = (paginaIniziale * elementiPerPagina);
		if (i_fin > listaDocs.size())
			i_fin = listaDocs.size();

		try {
			pagTot = new BigDecimal(listaDocs.size()).divide(new BigDecimal(elementiPerPagina), RoundingMode.UP)
					.longValue();
		} catch (ArithmeticException ae) {
			pagTot = 1;
		}
		listaDocs = listaDocs.dividiPagine(i_iniz, i_fin);
		context.getRequest().setAttribute("pag", String.valueOf(pag));
		context.getRequest().setAttribute("pagTot", String.valueOf(pagTot));
		context.getRequest().setAttribute("pagine", pagine);
		return listaDocs;
	}

}
