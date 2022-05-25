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
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoSintesisList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneAllegatiSintesis extends CFSModule {
	
	private byte[] ba = null;
	private int idInvioSintesis = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension="";
	private String f1="";
	private String tipoAllegato="";
	
	private File fileDaCaricare = null;

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
		
	public int getIdInvioSintesis() {
		return idInvioSintesis;
	}

	public void setIdInvioSintesis(int idInvioSintesis) {
		this.idInvioSintesis = idInvioSintesis;
	}

	public void setIdInvioSintesis(String idInvioSintesis) {
		if (idInvioSintesis!=null & !idInvioSintesis.equals("null"))
		this.idInvioSintesis = Integer.parseInt(idInvioSintesis);
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

	public String allegaFile(ActionContext context) throws IOException, SQLException, IllegalStateException, ServletException, FileUploadException{
		
		if (!hasPermission(context, "documentale_documents-add")) {
			return ("PermissionError");
		}
		
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}
	
	//	String filePath = this.getPath(context, "accounts");
	
	    int fileSize = -1;
	      
	      if (fileDaCaricare instanceof File) {
	          File newFileInfo = fileDaCaricare;
	          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
	          thisItem.setLinkModuleId(Constants.ACCOUNTS);
	          thisItem.setEnteredBy(getUserId(context));
	          thisItem.setModifiedBy(getUserId(context));
	          thisItem.setSubject(getOggetto());
	          thisItem.setClientFilename(newFileInfo.getName());
	          thisItem.setFilename(newFileInfo.getName());
	          setFilename(newFileInfo.getName());
	          thisItem.setVersion(1.0);
	          thisItem.setSize((int) (long) newFileInfo.length());
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
	    	 	return "error";
	      }
	      
	     // f1 = filePath + filename;
	      
	      
	      
		  byte []buffer = new byte[(int) fileDaCaricare.length()];
		    InputStream ios = null;
		    try {
		        ios = new FileInputStream(fileDaCaricare);
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
		
		
		String revDoc = chiamaServerDocumentale(context);
		return revDoc;
		
	}
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException{
		
		String ip = context.getIpAddress();
		
		String baString="";
		baString = byteArrayToString();
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_SINTESIS_CARICA");
		//STAMPE
		String revDoc = null ;
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
			requestParams.append("idInvioSintesis");
			requestParams.append("=").append(getIdInvioSintesis());
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
			
			String messaggioPost = "";
			
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer(); 
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
				
			String codDocumento = null;	
			
			try {
				JSONObject jo = new JSONObject(result.toString());
				codDocumento = jo.get("codDocumento").toString();
				 revDoc =  jo.get("revDoc").toString();
				context.getRequest().setAttribute("RevDoc", revDoc);
			}
			catch (Exception e) {
			}
			if (codDocumento==null || codDocumento.equals("null") || codDocumento.equals(""))
				messaggioPost = "Possibile errore nel caricamento del file. Controllarne la presenza nella lista sottostante.";
			else
				messaggioPost = "OK! Caricamento completato con successo.";
			
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
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
		
			return revDoc;
	
	}
	
	
	public DocumentaleAllegatoSintesisList listaAllegati(ActionContext context) throws SQLException, IOException, ParseException {
		
	
	DocumentaleAllegatoSintesisList docList = null;
		
	HttpURLConnection conn = null;
	String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_SINTESIS");
	//STAMPE
	
//System.out.println("URLLLLLLL : "+lista_url);
	URL obj;
	try {
		obj = new URL(lista_url);
	
	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("idInvioSintesis");
	requestParams.append("=").append(getIdInvioSintesis());
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	
	conn.getContentLength();
			
	String codDocumento="";
	String nomeCartella="";
	String grandparentIdString="";
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
	  
		docList = new DocumentaleAllegatoSintesisList();
		 docList.creaElenco(jo);
			
		context.getRequest().setAttribute("listaAllegati", docList);
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		throw e ;
	} catch (IOException e) {
		e.printStackTrace();
		context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
		context.getRequest().setAttribute("label", "documents");
		throw e ;
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		throw e ;

	}
	finally{
		conn.disconnect();
	}
	return docList;
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


public File getFileDaCaricare() {
	return fileDaCaricare;
}

public void setFileDaCaricare(File file) {
	this.fileDaCaricare = file;
}

public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {
	
	if (!hasPermission(context, "documentale_documents-view")) {
		return ("PermissionError");
	}

int idInvio = -1;
int idInvioMolluschi = -1;
String idInvioString = "";
String idInvioMollString = "";


String op ="";
	
idInvioString = (String) context.getRequest().getAttribute("idInvio");
idInvioMollString = (String) context.getRequest().getAttribute("idInvioMolluschi");


op = (String) context.getRequest().getAttribute("op");

if (idInvioMollString==null)
	idInvioMollString = context.getRequest().getParameter("idInvioMolluschi");

if (idInvioString==null)
	idInvioString = context.getRequest().getParameter("idInvio");

if (op==null)
	op = context.getRequest().getParameter("op");


if (idInvioString!=null && !idInvioString.equals("null") && !idInvioString.equals(""))
	idInvio = Integer.parseInt(idInvioString);

if (idInvioMollString!=null && !idInvioMollString.equals("null") && !idInvioMollString.equals(""))
	idInvioMolluschi = Integer.parseInt(idInvioMollString);





context.getRequest().setAttribute("idInvio", String.valueOf(idInvio));
context.getRequest().setAttribute("idInvioMolluschi", String.valueOf(idInvioMolluschi));

context.getRequest().setAttribute("op", op);

	
HttpURLConnection conn = null;
String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_SINTESIS");
//STAMPE


URL obj;
try {
	obj = new URL(lista_url);

conn = (HttpURLConnection) obj.openConnection();
conn.setDoOutput(true);
conn.setRequestMethod("GET");
StringBuffer requestParams = new StringBuffer();
requestParams.append("idInvio");
requestParams.append("=").append(idInvio);
requestParams.append("&");
requestParams.append("idInvioMolluschi");
requestParams.append("=").append(idInvioMolluschi);
requestParams.append("&");
requestParams.append("app_name");
requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));

OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
wr.write(requestParams.toString());
wr.flush();

conn.getContentLength();
		
String codDocumento="";
String nomeCartella="";
String grandparentIdString="";
BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
//String inputLine; 
StringBuffer html = new StringBuffer();
if (in != null) {
	html.append(in.readLine()); }
	in.close();
	JSONArray jo = new JSONArray(html.toString());
	
	nomeCartella= jo.get(0).toString();
	grandparentIdString = jo.get(1).toString();
	  
	 DocumentaleAllegatoSintesisList docList = new DocumentaleAllegatoSintesisList();
	 docList.creaElenco(jo);
	 
	 context.getRequest().setAttribute("nomeCartella", nomeCartella);
	 context.getRequest().setAttribute("grandparentId", grandparentIdString);
	 
		
	context.getRequest().setAttribute("listaAllegati", docList);
	
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
}
finally{
	conn.disconnect();
}


return "listaAllegatiIBROk";
}

}
