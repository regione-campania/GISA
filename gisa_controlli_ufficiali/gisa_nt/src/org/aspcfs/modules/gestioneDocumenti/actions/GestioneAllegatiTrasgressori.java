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
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneAllegatiTrasgressori extends CFSModule {
	
	private byte[] ba = null;
	private int trasgrId = -1;
	private int ticketId = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension="";
	private String f1="";
	private String actionOrigine="";
	private String tipoAllegato="";
	
	private int idUtente = -1;
	
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
		
	public int getTrasgrId() {
		return trasgrId;
	}

	public void setTrasgrId(int trasgrId) {
		this.trasgrId = trasgrId;
	}

	public void setTrasgrId(String trasgrId) {
		if (trasgrId!=null && !trasgrId.equals("null"))
		this.trasgrId = Integer.parseInt(trasgrId);
	}
		
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setTicketId(String ticketId) {
		if (ticketId!=null && !ticketId.equals("null"))
		this.ticketId = Integer.parseInt(ticketId);
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
    String id = (String) parts.get("id");
    String tId = (String) parts.get("ticketId");
    String riga = (String) parts.get("idRiga");
    String subject = (String) parts.get("subject");
    String tipoAllegato = (String) parts.get("tipoAllegato");
    actionOrigine = (String) parts.get("actionOrigine");
    
    setTrasgrId(id);
    setTicketId(tId);
    setOggetto(subject);
    setTipoAllegato(tipoAllegato);
    
    context.getRequest().setAttribute("trasgrId", String.valueOf(getTrasgrId()));
	context.getRequest().setAttribute("ticketId", String.valueOf(getTicketId()));
	context.getRequest().setAttribute("idRiga", riga);
    
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
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_TRASGRESSORI_CARICA");
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
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipoAllegato);
		requestParams.append("&");
		requestParams.append("trasgrId");
		requestParams.append("=").append(getTrasgrId());
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(getTicketId());
		requestParams.append("&");
		requestParams.append("oggetto");
		requestParams.append("=").append(getOggetto());
		requestParams.append("&");
		requestParams.append("filename");
		requestParams.append("=").append(getFilename().replaceAll("\'", ""));
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
		
		String codDocumento="", titolo="", oggetto="", nomeClient="";
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
		oggetto = jo.get("oggetto").toString();
		nomeClient = jo.get("nomeClient").toString();

		context.getRequest().setAttribute("codDocumento", codDocumento);
		context.getRequest().setAttribute("titolo", titolo);
		context.getRequest().setAttribute("oggetto", oggetto);
		context.getRequest().setAttribute("nomeClient", nomeClient);

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
			String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		
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
					titolo=codDocumento+"_"+nomeDocumento;
				
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

public int getIdUtente() {
	return idUtente;
}

public void setIdUtente(int idUtente) {
	this.idUtente = idUtente;
}


public String executeCommandPrepareUploadAllegato(ActionContext context) throws SQLException, IOException {
	String trasgrId = context.getRequest().getParameter("trasgrId");
	context.getRequest().setAttribute("trasgrId", trasgrId);
	String ticketId = context.getRequest().getParameter("ticketId");
	context.getRequest().setAttribute("ticketId", ticketId);
	String idRiga = context.getRequest().getParameter("idRiga");
	context.getRequest().setAttribute("idRiga", idRiga);
	String messaggioPost = (String) context.getRequest().getAttribute("messaggioPost");
		if (messaggioPost!=null && !messaggioPost.equals("null"))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
		String tipo = (String) context.getRequest().getAttribute("tipo");
		if (tipo!=null && !tipo.equals("null"))
			context.getRequest().setAttribute("tipo", tipo);
	
	return "prepareUploadAllegatoOk";
	}

public String executeCommandPrepareUploadAllegatoSanzione(ActionContext context) throws SQLException, IOException {
	String ticketId = context.getRequest().getParameter("ticketId");
	context.getRequest().setAttribute("ticketId", ticketId);
	String messaggioPost = (String) context.getRequest().getAttribute("messaggioPost");
		if (messaggioPost!=null && !messaggioPost.equals("null"))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
		String tipo = (String) context.getRequest().getAttribute("tipo");
		if (tipo!=null && !tipo.equals("null"))
			context.getRequest().setAttribute("tipo", tipo);
	
	return "prepareUploadAllegatoSanzioneOk";
	}

public String executeCommandAllegaFileSanzione(ActionContext context) throws IOException, SQLException, IllegalStateException, ServletException, FileUploadException, ParseException{
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
String tId = (String) parts.get("ticketId");
String subject = (String) parts.get("subject");
String tipoAllegato = (String) parts.get("tipoAllegato");
actionOrigine = (String) parts.get("actionOrigine");

setTicketId(tId);
setOggetto(subject);
setTipoAllegato(tipoAllegato);

context.getRequest().setAttribute("ticketId", String.valueOf(getTicketId()));

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
String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_TRASGRESSORI_CARICA");
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
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	requestParams.append("&");
	requestParams.append("tipoCertificato");
	requestParams.append("=").append(tipoAllegato);
	requestParams.append("&");
	requestParams.append("ticketId");
	requestParams.append("=").append(getTicketId());
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
	
	String codDocumento="", titolo="", oggetto="", nomeClient="";
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
	oggetto = jo.get("oggetto").toString();
	nomeClient = jo.get("nomeClient").toString();

	context.getRequest().setAttribute("codDocumento", codDocumento);
	context.getRequest().setAttribute("titolo", titolo);
	
	if (codDocumento!= null && !codDocumento.equals("")){
		Connection db = null;
		try {
			db = this.getConnection(context);
			PreparedStatement pst = db.prepareStatement("insert into sanzioni_allegati(id_sanzione, id_utente, tipo_allegato, header_allegato, oggetto, nome_client) values (?, ?, ?, ?, ?, ?)");
			pst.setInt(1, getTicketId());
			pst.setInt(2, getUserId(context));
			pst.setString(3, tipoAllegato);
			pst.setString(4, codDocumento);
			pst.setString(5, oggetto);
			pst.setString(6, nomeClient);
			pst.execute();
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}

	}
	
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

	return "allegaAllegatoSanzioneOk";

}


public String chiamaServerDocumentale() throws SQLException, IOException {

	String baString = "";
	baString = byteArrayToString();
	String codDocumento = "";
	
	HttpURLConnection conn = null;
	String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
			+ ApplicationProperties.getProperty("APP_DOCUMENTALE_TRASGRESSORI_CARICA");
	// STAMPE

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
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipoAllegato);
		requestParams.append("&");
		requestParams.append("trasgrId");
		requestParams.append("=").append(getTrasgrId());
		requestParams.append("&");
		requestParams.append("ticketId");
		requestParams.append("=").append(getTicketId());
		requestParams.append("&");
		requestParams.append("oggetto");
		requestParams.append("=").append(getOggetto());
		requestParams.append("&");
		requestParams.append("filename");
		requestParams.append("=").append(getFilename().replaceAll("\'", ""));
		requestParams.append("&");
		requestParams.append("fileDimension");
		requestParams.append("=").append(getFileDimension());
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(getIdUtente());
		
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();

		conn.getContentLength();

		String messaggioPost = "";

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer result = new StringBuffer();
		if (in != null) {
			String ricevuto = in.readLine();
			result.append(ricevuto);
		}
		in.close();

		try {
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
		} catch (Exception e) {
		}
		if (codDocumento == null || codDocumento.equals("null") || codDocumento.equals(""))
			messaggioPost = "Possibile errore nel caricamento del file. Controllarne la presenza nella lista sottostante.";
		else
			messaggioPost = "OK! Caricamento completato con successo.";


	} catch (ConnectException e1) {
		return "documentaleAllegatiError";
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "documentaleAllegatiError";
	} catch (IOException e) {
		e.printStackTrace();
		return "documentaleAllegatiError";
	}

	finally {
		conn.disconnect();
	}

	return "allegaAllegatoOk";


}

}
