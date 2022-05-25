/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.Charsets;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleBachecaList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneBacheca extends CFSModule {
	
	//file
	private byte[] ba = null;
	private String fileTitolo = null;
	private String fileName = null;
	private String fileEstensione = null;
	private String fileDimensione="";
	private String fileDescrizione= "";
	private String f1="";
		
	//id cartella principale
	private int storeId = -1;
	private int parentId = -1;
	private int folderId = -1;
	
	private String dataInizio= null;
	private String dataApprovazione= null;
	private int utenteInserimento=-1;
	private int utenteModifica=-1;
	private int utenteCancellazione=-1;
	
	private boolean archivioApprovato = false;
	private boolean archiviatoArchivio = false;
	
	private boolean storeArchiviato = true;
	
	private int priorita = -1;

	
	private boolean elencoAsl = false;
	private boolean apiari = false;
	
	public boolean isApiari() {
		return apiari;
	}

	public void setApiari(boolean apiari) {
		this.apiari = apiari;
	}
	
	public void setApiari(String apiari) {
		if (apiari!=null && !apiari.equals("null"))
			this.apiari = true;
		else
			this.apiari = false;
	}
	
	
	public boolean isElencoAsl() {
		return elencoAsl;
	}

	public void setElencoAsl(boolean elencoAsl) {
		this.elencoAsl = elencoAsl;
	}
	
	public void setElencoAsl(String elencoAsl) {
		if (elencoAsl!=null && !elencoAsl.equals("null"))
			this.elencoAsl = true;
		else
			this.elencoAsl = false;
	}
	
	public boolean isArchivioApprovato() {
		return archivioApprovato;
	}

	public void setArchivioApprovato(boolean archivioApprovato) {
		this.archivioApprovato = archivioApprovato;
	}

	public void setArchivioApprovato(String archivioApprovato) {
		if (archivioApprovato!=null && !archivioApprovato.equals("null"))
			this.archivioApprovato = true;
		else
			this.archivioApprovato = false;
	}
	
	public boolean isArchiviatoArchivio() {
		return archiviatoArchivio;
	}

	public void setArchiviatoArchivio(boolean archiviatoArchivio) {
		this.archiviatoArchivio = archiviatoArchivio;
	}

	public void setArchiviatoArchivio(String archiviatoArchivio) {
		if (archiviatoArchivio!=null && !archiviatoArchivio.equals("null"))
			this.archiviatoArchivio = true;
		else
			this.archiviatoArchivio = false;
	}
	
	public boolean isStoreArchiviato() {
		return storeArchiviato;
	}

	public void setStoreArchiviato(boolean storeArchiviato) {
		this.storeArchiviato = storeArchiviato;
	}

	public void setStoreArchiviato(String storeArchiviato) {
		if (storeArchiviato!=null && !storeArchiviato.equals("null"))
			this.storeArchiviato = true;
		else
			this.storeArchiviato = false;
	}
	
	public byte[] getBa() {
		return ba;
	}

	public void setBa(byte[] ba) {
		this.ba = ba;
	}

	public String getFileTitolo() {
		return fileTitolo;
	}

	public void setFileTitolo(String fileTitolo) {
		this.fileTitolo = fileTitolo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileEstensione() {
		return fileEstensione;
	}

	public void setFileEstensione(String fileEstensione) {
		this.fileEstensione = fileEstensione;
	}

	public String getFileDimensione() {
		return fileDimensione;
	}

	public void setFileDimensione(String fileDimensione) {
		this.fileDimensione = fileDimensione;
	}

	public String getFileDescrizione() {
		return fileDescrizione;
	}

	public void setFileDescrizione(String fileDescrizione) {
		this.fileDescrizione = fileDescrizione;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void setStoreId(String storeId) {
		if (storeId!=null && !storeId.equals("null"))
		this.storeId = Integer.parseInt(storeId);
	}
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public void setParentId(String parentId) {
		if (parentId!=null && !parentId.equals("null")  && !"".equals(parentId))
		this.parentId = Integer.parseInt(parentId);
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	
	public void setFolderId(String folderId) {
		if (folderId!=null && !folderId.equals("null") && !"".equals(folderId))
			this.folderId = Integer.parseInt(folderId);
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(String dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public int getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(int utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public int getUtenteModifica() {
		return utenteModifica;
	}

	public void setUtenteModifica(int utenteModifica) {
		this.utenteModifica = utenteModifica;
	}

	public int getUtenteCancellazione() {
		return utenteCancellazione;
	}

	public void setUtenteCancellazione(int utenteCancellazione) {
		this.utenteCancellazione = utenteCancellazione;
	}
	
	public int getPriorita() {
		return priorita;
	}

	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}
	public void setPriorita(String priorita) {
		if (priorita!=null && !priorita.equals("null") && !"".equals(priorita))
				this.priorita = Integer.parseInt(priorita);
	}

	public String executeCommandDefault(ActionContext context) {

		try {
			return executeCommandListaAllegati(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-none-";
	}


	public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {
		
		 if (!(hasPermission(context, "documentale_bacheca-view"))) {
		      return ("PermissionError");
		    }
		
	int folderId = -1;
	int parentId = -1;
	int storeId = -1;
	String folderIdString = "";
	String parentIdString = "";
	String storeIdString = "";
	String messaggioPost = "";
		
	messaggioPost = context.getRequest().getParameter("messaggioPost");
	if (messaggioPost!=null && !messaggioPost.equals("null") && !messaggioPost.equals("")) 
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
	
	String archiviati ="";
		archiviati = context.getRequest().getParameter("archiviati");
	
		
	String uploadOk= (String) context.getRequest().getAttribute("uploadOk");
	folderIdString = (String) context.getRequest().getAttribute("folderId");
	parentIdString = (String) context.getRequest().getAttribute("parentId");
	storeIdString = (String) context.getRequest().getAttribute("storeId");

	
	if (storeIdString==null){
		storeIdString = context.getRequest().getParameter("storeId");
		parentIdString = context.getRequest().getParameter("parentId");
		folderIdString = context.getRequest().getParameter("folderId");
		
	}
	
	if (storeIdString!=null && !storeIdString.equals("null"))
		storeId = Integer.parseInt(storeIdString);
	if (parentIdString!=null && !parentIdString.equals("null"))
		parentId = Integer.parseInt(parentIdString);
	if (folderIdString!=null && !folderIdString.equals("null"))
		folderId = Integer.parseInt(folderIdString);
		
	setStoreArchiviato(context.getRequest().getParameter("storeArchiviato"));
	context.getRequest().setAttribute("storeArchiviato", String.valueOf(storeArchiviato));
	
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
	context.getRequest().setAttribute("uploadOk", uploadOk);
				
	HttpURLConnection conn = null;
	String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+lista_url.toString());

	URL obj;
	try {
		obj = new URL(lista_url);
	
	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("POST");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("storeId");
	requestParams.append("=").append(storeId);
	requestParams.append("&");
	requestParams.append("folderId");
	requestParams.append("=").append(folderId);
	requestParams.append("&");
	requestParams.append("app_name");
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
	requestParams.append("&");
	requestParams.append("parentId");
	requestParams.append("=").append(parentId);
	requestParams.append("&");
	requestParams.append("archiviati");
	requestParams.append("=").append(archiviati);
	
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();
	System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
	conn.getContentLength();
			
	String codDocumento="";
	String nomeCartella="";
	String nomeArchivio="";
	String grandparentIdString="";
	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
	//String inputLine; 
	StringBuffer html = new StringBuffer();
	if (in != null) {
		html.append(in.readLine()); }
		in.close();
		JSONArray jo = new JSONArray(html.toString());
		
		nomeCartella= jo.get(0).toString();
		nomeArchivio= jo.get(1).toString();
		grandparentIdString= jo.get(2).toString();
		  
		DocumentaleBachecaList docList = new DocumentaleBachecaList();
		docList.creaElenco(jo, storeId);
		 docList = documentalePaginazione(context, docList);
		 
		 context.getRequest().setAttribute("nomeCartella", nomeCartella);
		 context.getRequest().setAttribute("nomeArchivio", nomeArchivio);
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
	
	if (storeId==-1)
		return "listaArchiviOk";
	if (storeArchiviato==true)
		return "listaAllegatiArchiviatiOk";
	return "listaAllegatiOk";
	}
	
	
public String executeCommandAllegaFile(ActionContext context) throws IOException, SQLException, IllegalStateException, ServletException, FileUploadException{
		 
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	
	//String filePath = this.getPath(context, "bacheca");
    String filePath = getWebInfPath(context,"tmp_bacheca");

	HttpMultiPartParser multiPart = new HttpMultiPartParser();
   	
	HashMap parts = multiPart.parseData(context.getRequest(), filePath);
    String id = (String) parts.get("id");
    String subject = (String) parts.get("subject");
    String folderId = (String) parts.get("folderId");
    String parentId = (String) parts.get("parentId");
    String elencoAsl = (String) parts.get("elencoAsl");
    String apiari = (String) parts.get("apiari");
  	      
    setElencoAsl(elencoAsl);
    setApiari(apiari);
    setParentId(parentId);
    setFolderId(folderId);
    setStoreId(id);
    setFileTitolo(subject);
    
    if (isApiari()) {
    	if (!hasPermission(context, "documentale_bacheca_apiari_files-add")) {
			return ("PermissionError");
		}
    }
    else
    {
    	if (!hasPermission(context, "documentale_bacheca_files-add") && !hasPermission(context, "documentale_bacheca_asl-add")) {
			return ("PermissionError");
		}
    }
		
	    
	    context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	    
	    int fileSize = -1;
	      
	      if ((Object)  parts.get("file1") instanceof FileInfo) {
	          //Update the database with the resulting file
	          FileInfo newFileInfo = (FileInfo) parts.get("file1");
	          //Insert a file description record into the database
	          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
	          thisItem.setLinkModuleId(Constants.ACCOUNTS);
	          thisItem.setEnteredBy(getUserId(context));
	          thisItem.setModifiedBy(getUserId(context));
	          thisItem.setFolderId(getFolderId());
	          thisItem.setSubject(getFileTitolo());
	          thisItem.setClientFilename(newFileInfo.getClientFileName());
	          thisItem.setFilename(newFileInfo.getRealFilename());
	          setFileName(newFileInfo.getRealFilename());
	          thisItem.setVersion(1.0);
	          thisItem.setSize(newFileInfo.getSize());
	          fileSize = thisItem.getSize();
	          setFileDimensione(String.valueOf(fileSize));
	         }
	      
	      int maxFileSize=-1;
	      int mb1size = 1048576;
	      if (ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI")!=null)
	    	  maxFileSize=Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	      
	      if (fileSize > maxFileSize){ //2 mb
	       	  String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
	    	  context.getRequest().setAttribute("messaggioPost", "Errore! Selezionare un file con dimensione inferiore a "+maxSizeString +" MB.");
				return executeCommandListaAllegati(context);
	      }
	      
	      f1 = filePath + fileName;
	      
	      
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
		return chiamaServerDocumentale(context);
		
	}
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException{
		
		String ip = context.getIpAddress();
		
		String baString="";
		baString = byteArrayToString();
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_BACHECA_CARICA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
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
			requestParams.append("=").append("Bacheca");
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("oggetto");
			requestParams.append("=").append(fileTitolo);
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("filename");
			requestParams.append("=").append(fileName);
			requestParams.append("&");
			requestParams.append("fileDimension");
			requestParams.append("=").append(fileDimensione);
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);
				
			System.out.println("ListaParametri "+requestParams.toString());
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
		
			String messaggioPost = "";
			
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer(); 
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
				
			String esito = null; 
			
			try {
				JSONObject jo = new JSONObject(result.toString());
				esito = jo.get("esito").toString();
			}
			catch (Exception e) {
			}
			
			System.out.println("[DOCUMENTALE GISA] Esito UploadBacheca: "+esito);
			if (esito==null || !esito.equals("OK"))
				messaggioPost = "Possibile errore nel caricamento del file. Controllarne la presenza nella lista sottostante.";
			else
				messaggioPost = "OK! Caricamento completato con successo.";
			
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
			if (elencoAsl)
				return executeCommandListaReperibilitaAsl(context);
			if (apiari)
				return executeCommandListaApiari(context);
			return executeCommandListaAllegati(context);
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
		
	
	}
	
	private String byteArrayToString () throws UnsupportedEncodingException{
		String s = new String(ba, "ISO-8859-1");
		return s;
		
		
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

public String executeCommandCreaNuovoArchivio(ActionContext context) throws SQLException, IOException{
	
	 if (!(hasPermission(context, "documentale_bacheca-add"))) {
	      return ("PermissionError");
	    }
	
	 Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}
		
	setStoreId("-1");
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	setFileDescrizione(context.getRequest().getParameter("descrizioneArchivio"));
	setFileName(context.getRequest().getParameter("nomeArchivio"));
	setDataInizio(context.getRequest().getParameter("dataInizio"));
	setArchivioApprovato(context.getRequest().getParameter("statoArchivio"));
	
	setUtenteInserimento(getUserId(context));
	setPriorita(context.getRequest().getParameter("prioritaArchivio"));
	
	
	if (context.getRequest().getParameter("new")!=null)
		return "newArchivioOk";
	
	String nome = context.getRequest().getParameter("nomeArchivio");
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_BACHECA_CARICA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nome);
		requestParams.append("&");
		requestParams.append("fileDescrizione");
		requestParams.append("=").append(fileDescrizione);
		requestParams.append("&");
		requestParams.append("dataInizio");
		requestParams.append("=").append(dataInizio);
		requestParams.append("&");
		requestParams.append("priorita");
		requestParams.append("=").append(priorita);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("CreaArchivio");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(utenteInserimento);
		requestParams.append("&");
		requestParams.append("archivioApprovato");
		requestParams.append("=").append(archivioApprovato);
		
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		String messaggioPost = "OK! Archivio creato con successo.";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return executeCommandListaAllegati(context);
}

public String executeCommandCreaNuovoArchivioApiari(ActionContext context) throws SQLException, IOException{
	
	 if (!(hasPermission(context, "documentale_bacheca_apiari-add"))) {
	      return ("PermissionError");
	    }
	 Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}
		
	setStoreId("-1");
	setApiari(true);
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	setFileDescrizione(context.getRequest().getParameter("descrizioneArchivio"));
	setFileName(context.getRequest().getParameter("nomeArchivio"));
	setDataInizio(context.getRequest().getParameter("dataInizio"));
	setArchivioApprovato(context.getRequest().getParameter("statoArchivio"));
	
	setUtenteInserimento(getUserId(context));
	
	
	
	if (context.getRequest().getParameter("new")!=null)
		return "newArchivioApiariOk";
	
	String nome = context.getRequest().getParameter("nomeArchivio");
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_BACHECA_CARICA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nome);
		requestParams.append("&");
		requestParams.append("fileDescrizione");
		requestParams.append("=").append(fileDescrizione);
		requestParams.append("&");
		requestParams.append("dataInizio");
		requestParams.append("=").append(dataInizio);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("CreaArchivio");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(utenteInserimento);
		requestParams.append("&");
		requestParams.append("archivioApprovato");
		requestParams.append("=").append(archivioApprovato);
		requestParams.append("&");
		requestParams.append("apiari");
		requestParams.append("=").append(apiari);
		
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		String messaggioPost = "OK! Archivio creato con successo.";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return executeCommandListaApiari(context);
}


public String executeCommandAddArchivio(ActionContext context) throws SQLException, IOException{
	
	 if (!(hasPermission(context, "documentale_bacheca-add"))) {
	      return ("PermissionError");
	    }
	
		
	setStoreId(context.getRequest().getParameter("storeId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	return "addArchivioOk";
}

public String executeCommandGestisciArchivio(ActionContext context) throws SQLException, IOException {
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	setFileDescrizione(context.getRequest().getParameter("descrizioneArchivio"));
	context.getRequest().setAttribute("descrizioneArchivio", fileDescrizione);
	setFileName(context.getRequest().getParameter("nomeArchivio"));
	context.getRequest().setAttribute("nomeArchivio", fileName);
	setDataInizio(context.getRequest().getParameter("dataInizio"));
	context.getRequest().setAttribute("dataInizio", dataInizio);
	setArchivioApprovato(context.getRequest().getParameter("statoArchivio"));
	context.getRequest().setAttribute("statoArchivio", String.valueOf(archivioApprovato));
	setArchiviatoArchivio(context.getRequest().getParameter("archiviatoArchivio"));
	context.getRequest().setAttribute("archiviatoArchivio", String.valueOf(archiviatoArchivio));
	setPriorita(context.getRequest().getParameter("prioritaArchivio"));
	context.getRequest().setAttribute("prioritaArchivio", String.valueOf(priorita));
	
	
	
	if (context.getRequest().getParameter("operazione").equals("modificaArchivio")){ //MODIFICA ARCHIVIO
		setUtenteModifica(getUserId(context));
		
		  if (!(hasPermission(context, "documentale_bacheca-edit"))) {
		      return ("PermissionError");
		    }
		if (context.getRequest().getParameter("modificato")==null){
			context.getRequest().setAttribute("statoArchivio", context.getRequest().getParameter("statoArchivio"));
			return "modificaArchivioOk";
		}
		
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(fileName);
		requestParams.append("&");
		requestParams.append("fileDescrizione");
		requestParams.append("=").append(fileDescrizione);
		requestParams.append("&");
		requestParams.append("dataInizio");
		requestParams.append("=").append(dataInizio);
		requestParams.append("&");
		requestParams.append("idUtenteModifica");
		requestParams.append("=").append(utenteModifica);
		requestParams.append("&");
		requestParams.append("archivioApprovato");
		requestParams.append("=").append(archivioApprovato);
		requestParams.append("&");
		requestParams.append("archiviatoArchivio");
		requestParams.append("=").append(archiviatoArchivio);
		requestParams.append("&");
		requestParams.append("priorita");
		requestParams.append("=").append(priorita);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("ModificaArchivio");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String messaggioPost = "Archivio modificato con successo!";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		//context.getRequest().setAttribute("storeId", "-1"); //deve tornare alla lista degli archivi
		return executeCommandDettaglioArchivio(context);
	}
	
	else if (context.getRequest().getParameter("operazione").equals("cancellaArchivio")){ //CANCELLA ARCHIVIO
		
		if (!(hasPermission(context, "documentale_bacheca-delete"))) {
		      return ("PermissionError");
		    }
		
		setUtenteCancellazione(getUserId(context));
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("idUtenteCancellazione");
			requestParams.append("=").append(utenteCancellazione);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaArchivio");
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String messaggioPost = "Archivio cancellato con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			context.getRequest().setAttribute("storeId", "-1"); //deve tornare alla lista degli archivi
		
			
	}
else if (context.getRequest().getParameter("operazione").equals("dettaglioArchivio")){ //DETTAGLIO ARCHIVIO
	
	return executeCommandDettaglioArchivio(context);
			
	}
	return executeCommandListaAllegati(context);
	
}

public String executeCommandGestisciArchivioApiari(ActionContext context) throws SQLException, IOException {
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	
	setApiari(true);	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	setFileDescrizione(context.getRequest().getParameter("descrizioneArchivio"));
	context.getRequest().setAttribute("descrizioneArchivio", fileDescrizione);
	setFileName(context.getRequest().getParameter("nomeArchivio"));
	context.getRequest().setAttribute("nomeArchivio", fileName);
	setDataInizio(context.getRequest().getParameter("dataInizio"));
	context.getRequest().setAttribute("dataInizio", dataInizio);
	setArchivioApprovato(context.getRequest().getParameter("statoArchivio"));
	context.getRequest().setAttribute("statoArchivio", String.valueOf(archivioApprovato));
	setArchiviatoArchivio(context.getRequest().getParameter("archiviatoArchivio"));
	context.getRequest().setAttribute("archiviatoArchivio", String.valueOf(archiviatoArchivio));

	if (context.getRequest().getParameter("operazione").equals("modificaArchivio")){ //MODIFICA ARCHIVIO
		setUtenteModifica(getUserId(context));
		
		  if (!(hasPermission(context, "documentale_bacheca_apiari-edit"))) {
		      return ("PermissionError");
		    }
		if (context.getRequest().getParameter("modificato")==null){
			context.getRequest().setAttribute("statoArchivio", context.getRequest().getParameter("statoArchivio"));
			return "modificaArchivioApiariOk";
		}
		
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(fileName);
		requestParams.append("&");
		requestParams.append("fileDescrizione");
		requestParams.append("=").append(fileDescrizione);
		requestParams.append("&");
		requestParams.append("dataInizio");
		requestParams.append("=").append(dataInizio);
		requestParams.append("&");
		requestParams.append("idUtenteModifica");
		requestParams.append("=").append(utenteModifica);
		requestParams.append("&");
		requestParams.append("archivioApprovato");
		requestParams.append("=").append(archivioApprovato);
		requestParams.append("&");
		requestParams.append("archiviatoArchivio");
		requestParams.append("=").append(archiviatoArchivio);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("ModificaArchivio");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String messaggioPost = "Archivio modificato con successo!";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		//context.getRequest().setAttribute("storeId", "-1"); //deve tornare alla lista degli archivi
		return executeCommandDettaglioArchivioApiari(context);
	}
	
	else if (context.getRequest().getParameter("operazione").equals("cancellaArchivio")){ //CANCELLA ARCHIVIO
		
		if (!(hasPermission(context, "documentale_bacheca_apiari-delete"))) {
		      return ("PermissionError");
		    }
		
		setUtenteCancellazione(getUserId(context));
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("idUtenteCancellazione");
			requestParams.append("=").append(utenteCancellazione);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaArchivio");
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String messaggioPost = "Archivio cancellato con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			context.getRequest().setAttribute("storeId", "-1"); //deve tornare alla lista degli archivi
		
			
	}
else if (context.getRequest().getParameter("operazione").equals("dettaglioArchivio")){ //DETTAGLIO ARCHIVIO
	
	return executeCommandDettaglioArchivioApiari(context);
			
	}
	return executeCommandListaApiari(context);
	
}

public String executeCommandCreaNuovaCartella(ActionContext context) throws SQLException, IOException{
	
	  if (!(hasPermission(context, "documentale_bacheca_cartelle-add"))) {
	      return ("PermissionError");
	    }
	  Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}
		
	  setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		
	if (context.getRequest().getParameter("new")!=null)
		return "newCartellaOk";
	
	String nome = context.getRequest().getParameter("nomeCartella");
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_BACHECA_CARICA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("parentId");
		requestParams.append("=").append(parentId);
		requestParams.append("&");
		requestParams.append("folderId");
		requestParams.append("=").append(folderId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nome);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("CreaCartella");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(getUserId(context));
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
return executeCommandListaAllegati(context);
}

public String executeCommandCreaNuovaCartellaApiari(ActionContext context) throws SQLException, IOException{
	
	  if (!(hasPermission(context, "documentale_bacheca_apiari_cartelle-add"))) {
	      return ("PermissionError");
	    }
	
	  Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}
		
	  setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		
	if (context.getRequest().getParameter("new")!=null)
		return "newCartellaOk";
	
	String nome = context.getRequest().getParameter("nomeCartella");
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_BACHECA_CARICA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("parentId");
		requestParams.append("=").append(parentId);
		requestParams.append("&");
		requestParams.append("folderId");
		requestParams.append("=").append(folderId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nome);
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append("CreaCartella");
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(getUserId(context));
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
return executeCommandListaApiari(context);
}


public String executeCommandGestisciCartella(ActionContext context) throws SQLException, IOException {
		
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
	
	int idCartella = -1;
	if (context.getRequest().getParameter("idCartella")!=null)
		idCartella = Integer.parseInt(context.getRequest().getParameter("idCartella"));
	
	if (context.getRequest().getParameter("operazione").equals("rinomina")){ //RINOMINA CARTELLA
	
		if (!hasPermission(context, "documentale_bacheca_cartelle-edit")) {
			return ("PermissionError");
		}
		
		String nomeCartella = context.getRequest().getParameter("nomeCartella");
	if (context.getRequest().getParameter("rinominata")==null){ //SE DEVO ANCORA SCEGLIERE IL NOME
		context.getRequest().setAttribute("nomeCartellaOld", nomeCartella);
		return "rinominaCartellaOk";
	}
		
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("parentId");
		requestParams.append("=").append(parentId);
		requestParams.append("&");
		requestParams.append("folderId");
		requestParams.append("=").append(folderId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nomeCartella);
		requestParams.append("&");
		requestParams.append("idElemento");
		requestParams.append("=").append(idCartella);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("RinominaCartella");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String messaggioPost = "Cartella rinominata con successo!";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		
	}
	
	else if (context.getRequest().getParameter("operazione").equals("cancella")){ //CANCELLA CARTELLA CARTELLA
		
		if (!hasPermission(context, "documentale_bacheca_cartelle-delete")) {
			return ("PermissionError");
		}
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("idElemento");
			requestParams.append("=").append(idCartella);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaCartella");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String messaggioPost = "Cartella cancellata con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
	}
	return executeCommandListaAllegati(context);
	
}

public String executeCommandGestisciCartellaApiari(ActionContext context) throws SQLException, IOException {
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
	
	int idCartella = -1;
	if (context.getRequest().getParameter("idCartella")!=null)
		idCartella = Integer.parseInt(context.getRequest().getParameter("idCartella"));
	
	if (context.getRequest().getParameter("operazione").equals("rinomina")){ //RINOMINA CARTELLA
	
		if (!hasPermission(context, "documentale_bacheca_apiari_cartelle-edit")) {
			return ("PermissionError");
		}
		
		String nomeCartella = context.getRequest().getParameter("nomeCartella");
	if (context.getRequest().getParameter("rinominata")==null){ //SE DEVO ANCORA SCEGLIERE IL NOME
		context.getRequest().setAttribute("nomeCartellaOld", nomeCartella);
		return "rinominaCartellaApiariOk";
	}
		
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("parentId");
		requestParams.append("=").append(parentId);
		requestParams.append("&");
		requestParams.append("folderId");
		requestParams.append("=").append(folderId);
		requestParams.append("&");
		requestParams.append("nome");
		requestParams.append("=").append(nomeCartella);
		requestParams.append("&");
		requestParams.append("idElemento");
		requestParams.append("=").append(idCartella);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("RinominaCartella");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String messaggioPost = "Cartella rinominata con successo!";
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
		
	}
	
	else if (context.getRequest().getParameter("operazione").equals("cancella")){ //CANCELLA CARTELLA CARTELLA
		
		if (!hasPermission(context, "documentale_bacheca_apiari_cartelle-delete")) {
			return ("PermissionError");
		}
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("idElemento");
			requestParams.append("=").append(idCartella);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaCartella");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String messaggioPost = "Cartella cancellata con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
	}
	return executeCommandListaApiari(context);
	
}

public String executeCommandGestisciFile(ActionContext context) throws SQLException, IOException {
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	String op ="";
	op = context.getRequest().getParameter("op");
	context.getRequest().setAttribute("op", op);
	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		
	int idFile = -1;
	if (context.getRequest().getParameter("idFile")!=null)
		idFile = Integer.parseInt(context.getRequest().getParameter("idFile"));
	
	if (context.getRequest().getParameter("operazione").equals("cancella")){ //CANCELLA FILE
	
		if (!hasPermission(context, "documentale_bacheca_files-delete")) {
			return ("PermissionError");
		}
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("idElemento");
			requestParams.append("=").append(idFile);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaFile");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String messaggioPost = "File cancellato con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
	}
	return executeCommandListaAllegati(context);
	
}

public String executeCommandGestisciFileApiari(ActionContext context) throws SQLException, IOException {
	
	Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
	String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
	if (!documentaleDisponibile){
		context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
		context.getRequest().setAttribute("label", "documents");
		return "documentaleAllegatiError";
	}
	String op ="";
	op = context.getRequest().getParameter("op");
	context.getRequest().setAttribute("op", op);
	
	//Completare
	setStoreId(context.getRequest().getParameter("storeId"));
	setFolderId(context.getRequest().getParameter("folderId"));
	setParentId(context.getRequest().getParameter("parentId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	context.getRequest().setAttribute("folderId", String.valueOf(folderId));
	context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		
	int idFile = -1;
	if (context.getRequest().getParameter("idFile")!=null)
		idFile = Integer.parseInt(context.getRequest().getParameter("idFile"));
	
	if (context.getRequest().getParameter("operazione").equals("cancella")){ //CANCELLA FILE
	
		if (!hasPermission(context, "documentale_bacheca_apiari_files-delete")) {
			return ("PermissionError");
		}
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
		URL obj;
		
			try {
				obj = new URL(url);
			
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("storeId");
			requestParams.append("=").append(storeId);
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("idElemento");
			requestParams.append("=").append(idFile);
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("CancellaFile");
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
				
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String messaggioPost = "File cancellato con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
			
	}
	return executeCommandListaApiari(context);
	
}

public String executeCommandRicercaInArchivio(ActionContext context) throws SQLException, IOException {
	
	String ricerca = context.getRequest().getParameter("ricerca");
	
	if (ricerca==null)
		return "ricercaInArchivioOk";
	
	String ricercaStringa = context.getRequest().getParameter("ricercaStringa");
		
		
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_BACHECA_FILTRI");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("ricerca");
		requestParams.append("=").append(ricerca);
		requestParams.append("&");
		requestParams.append("ricercaStringa");
		requestParams.append("=").append(ricercaStringa);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("Ricerca");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		String codDocumento="";
		String nomeCartella="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			  
			DocumentaleBachecaList docList = new DocumentaleBachecaList();
			docList.creaElencoGlobale(jo, "risultatiRicerca", 0);
			 docList = documentalePaginazione(context, docList);
			 
		context.getRequest().setAttribute("listaAllegati", docList);
		
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	return "risultatiRicercaOk";
	
}

public String executeCommandListaReperibilitaAsl(ActionContext context) throws SQLException, IOException {
	
	 if (!(hasPermission(context, "documentale_bacheca_asl-view"))) {
	      return ("PermissionError");
	    }
	
	// UserBean user = (UserBean) context.getSession().getAttribute("User");
	//	context.getRequest().setAttribute("User", user);
		
	setStoreId(context.getRequest().getParameter("storeId"));
	context.getRequest().setAttribute("storeId", String.valueOf(storeId));
	
	String messaggioPost = "";
	
	messaggioPost = context.getRequest().getParameter("messaggioPost");
	if (messaggioPost!=null && !messaggioPost.equals("null") && !messaggioPost.equals("")) 
		context.getRequest().setAttribute("messaggioPost", messaggioPost);
	
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		setStoreId(context.getRequest().getParameter("storeId"));
		try {
			obj = new URL(url);
		
		 conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("app_name");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("elencoAsl");
		requestParams.append("=").append("true");
					
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		String codDocumento="";
		String nomeCartella="";
		String nomeArchivio="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			nomeCartella= jo.get(0).toString();
			nomeArchivio= jo.get(1).toString();
			  
			DocumentaleBachecaList docList = new DocumentaleBachecaList();
			docList.creaElenco(jo, storeId);
			 docList = documentalePaginazione(context, docList);
			
		context.getRequest().setAttribute("listaAllegati", docList);
		context.getRequest().setAttribute("nomeArchivio", nomeArchivio);
		
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (storeId==-1)
			return "listaReperibilitaAslOk";
		
		return "listaAllegatiReperibilitaAslOk";
	
	
}

public String executeCommandListaApiari(ActionContext context) throws SQLException, IOException {
	
	 if (!(hasPermission(context, "documentale_bacheca_apiari-view"))) {
	      return ("PermissionError");
	    }
	setApiari(true);
int folderId = -1;
int parentId = -1;
int storeId = -1;
String folderIdString = "";
String parentIdString = "";
String storeIdString = "";
String messaggioPost = "";
	
messaggioPost = context.getRequest().getParameter("messaggioPost");
if (messaggioPost!=null && !messaggioPost.equals("null") && !messaggioPost.equals("")) 
	context.getRequest().setAttribute("messaggioPost", messaggioPost);

String archiviati ="";
	archiviati = context.getRequest().getParameter("archiviati");

	
String uploadOk= (String) context.getRequest().getAttribute("uploadOk");
folderIdString = (String) context.getRequest().getAttribute("folderId");
parentIdString = (String) context.getRequest().getAttribute("parentId");
storeIdString = (String) context.getRequest().getAttribute("storeId");


if (storeIdString==null){
	storeIdString = context.getRequest().getParameter("storeId");
	parentIdString = context.getRequest().getParameter("parentId");
	folderIdString = context.getRequest().getParameter("folderId");
	
}

if (storeIdString!=null && !storeIdString.equals("null"))
	storeId = Integer.parseInt(storeIdString);
if (parentIdString!=null && !parentIdString.equals("null"))
	parentId = Integer.parseInt(parentIdString);
if (folderIdString!=null && !folderIdString.equals("null"))
	folderId = Integer.parseInt(folderIdString);
	
setStoreArchiviato(context.getRequest().getParameter("storeArchiviato"));
context.getRequest().setAttribute("storeArchiviato", String.valueOf(storeArchiviato));

context.getRequest().setAttribute("storeId", String.valueOf(storeId));
context.getRequest().setAttribute("folderId", String.valueOf(folderId));
context.getRequest().setAttribute("parentId", String.valueOf(parentId));
context.getRequest().setAttribute("uploadOk", uploadOk);
			
HttpURLConnection conn = null;
String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_BACHECA");
//STAMPE
System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+lista_url.toString());

URL obj;
try {
	obj = new URL(lista_url);

conn = (HttpURLConnection) obj.openConnection();
conn.setDoOutput(true);
conn.setRequestMethod("POST");
StringBuffer requestParams = new StringBuffer();
requestParams.append("storeId");
requestParams.append("=").append(storeId);
requestParams.append("&");
requestParams.append("folderId");
requestParams.append("=").append(folderId);
requestParams.append("&");
requestParams.append("app_name");
requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
requestParams.append("&");
requestParams.append("parentId");
requestParams.append("=").append(parentId);
requestParams.append("&");
requestParams.append("archiviati");
requestParams.append("=").append(archiviati);
requestParams.append("&");
requestParams.append("apiari");
requestParams.append("=").append(apiari);

OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
wr.write(requestParams.toString());
wr.flush();
System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
conn.getContentLength();
		
String codDocumento="";
String nomeCartella="";
String nomeArchivio="";
String grandparentIdString="";
BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
//String inputLine; 
StringBuffer html = new StringBuffer();
if (in != null) {
	html.append(in.readLine()); }
	in.close();
	JSONArray jo = new JSONArray(html.toString());
	
	nomeCartella= jo.get(0).toString();
	nomeArchivio= jo.get(1).toString();
	grandparentIdString= jo.get(2).toString();
	  
	DocumentaleBachecaList docList = new DocumentaleBachecaList();
	docList.creaElenco(jo, storeId);
	 docList = documentalePaginazione(context, docList);
	 
	 context.getRequest().setAttribute("nomeCartella", nomeCartella);
	 context.getRequest().setAttribute("nomeArchivio", nomeArchivio);
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

if (storeId==-1)
	return "listaApiariOk";
if (storeArchiviato==true)
	return "listaAllegatiApiariArchiviatiOk";
return "listaAllegatiApiariOk";
}

public DocumentaleBachecaList documentalePaginazione(ActionContext context, DocumentaleBachecaList listaDocs){
	String pag = context.getRequest().getParameter("pag");
	String pagine = context.getRequest().getParameter("pagine");
	
	int elementiPerPagina = 10;
	if (pagine!=null && pagine.equals("no"))
		elementiPerPagina = listaDocs.size();
	
	int paginaIniziale = 1;
	if (pag!=null && !pag.equals("null") && !pag.equals(""))
		paginaIniziale = Integer.parseInt(pag);
	long pagTot=1;
	
	int i_iniz = (paginaIniziale-1) * elementiPerPagina;
	int i_fin = (paginaIniziale*elementiPerPagina);
	if (i_fin > listaDocs.size())
		i_fin = listaDocs.size();
	
	try {
	pagTot = new BigDecimal(listaDocs.size()).divide(new BigDecimal(elementiPerPagina), RoundingMode.UP).longValue();
	}
	catch(ArithmeticException ae){
		pagTot = 1;
	}
	
	listaDocs = listaDocs.dividiPagine(i_iniz, i_fin);
	context.getRequest().setAttribute("pag", String.valueOf(pag));
	context.getRequest().setAttribute("pagTot", String.valueOf(pagTot));
	context.getRequest().setAttribute("pagine", pagine);
	return listaDocs;
		
}

public String executeCommandDettaglioArchivio(ActionContext context){
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("DettaglioArchivio");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
							  
			 ArrayList<String> ArchivioDetail = new ArrayList<String>();
			 for(int i = 1 ; i < jo.length(); i++){
				    String riga = jo.get(i).toString();
				    ArchivioDetail.add(riga);
				}
			
			context.getRequest().setAttribute("ArchivioDetail", ArchivioDetail);
			return "dettaglioArchivioOk";
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

return "-none-";
}


public String executeCommandDettaglioArchivioApiari(ActionContext context){
	HttpURLConnection conn =null;
	String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_BACHECA");
	//STAMPE
	System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+url.toString());
	URL obj;
	
		try {
			obj = new URL(url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("provenienza");
		requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
		requestParams.append("&");
		requestParams.append("storeId");
		requestParams.append("=").append(storeId);
		requestParams.append("&");
		requestParams.append("operazione");
		requestParams.append("=").append("DettaglioArchivio");
			
		OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
		conn.getContentLength();
		
		
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
							  
			 ArrayList<String> ArchivioDetail = new ArrayList<String>();
			 for(int i = 1 ; i < jo.length(); i++){
				    String riga = jo.get(i).toString();
				    ArchivioDetail.add(riga);
				}
			
			context.getRequest().setAttribute("ArchivioDetail", ArchivioDetail);
			return "dettaglioArchivioApiariOk";
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

return "-none-";
}

public String executeCommandListaAllegatiComunicazioniInterne(ActionContext context) throws SQLException, IOException {
	
	 if (!(hasPermission(context, "documentale_bacheca-view"))) {
	      return ("PermissionError");
	    }

			
HttpURLConnection conn = null;
String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_BACHECA");
//STAMPE
System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+lista_url.toString());

URL obj;
try {
	obj = new URL(lista_url);

conn = (HttpURLConnection) obj.openConnection();
conn.setDoOutput(true);
conn.setRequestMethod("POST");
StringBuffer requestParams = new StringBuffer();
requestParams.append("app_name");
requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
requestParams.append("&");
requestParams.append("ComunicazioniInterne");
requestParams.append("=").append("si");

OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
wr.write(requestParams.toString());
wr.flush();
System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
conn.getContentLength();
		
String codDocumento="";
String nomeCartella="";
String nomeArchivio="";
String grandparentIdString="";
BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
//String inputLine; 
StringBuffer html = new StringBuffer();
if (in != null) {
	html.append(in.readLine()); }
	in.close();
	JSONArray jo = new JSONArray(html.toString());
	
	nomeCartella= jo.get(0).toString();
	nomeArchivio= jo.get(1).toString();
	grandparentIdString= jo.get(2).toString();
	  
	DocumentaleBachecaList docList = new DocumentaleBachecaList();
	docList.creaElencoGlobale(jo, "comunicazioniInterne", 3);
	// docList = documentalePaginazione(context, docList);
	 
	 context.getRequest().setAttribute("nomeCartella", nomeCartella);
	 context.getRequest().setAttribute("nomeArchivio", nomeArchivio);
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

return "listaAllegatiComunicazioniInterneOk";
}

}
