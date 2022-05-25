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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.actions.AccountVigilanza;
import org.aspcfs.modules.oia.base.Organization;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneAllegatiRiunione extends CFSModule {
	
	private byte[] ba = null;
	private int idRiunione = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension="";
	private String f1="";
	private String tipoAllegato="";
	private boolean principale=false;
	private boolean revisione = false;
	
	private int numeroRevisione = -1;
	private String headerRevisionato = null;
	
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
		
	public int getIdRiunione() {
		return idRiunione;
	}

	public void setIdRiunione(int idRiunione) {
		this.idRiunione = idRiunione;
	}

	public void setIdRiunione(String idRiunione) {
		if (idRiunione!=null & !idRiunione.equals("null"))
		this.idRiunione = Integer.parseInt(idRiunione);
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
		this.setNumeroRevisione(revDoc.substring(4));
		return revDoc;
		
	}
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException{
		
		String ip = context.getIpAddress();
		
		String baString="";
		baString = byteArrayToString();
		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_RIUNIONI_CARICA");
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
			requestParams.append("idRiunione");
			requestParams.append("=").append(getIdRiunione());
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
			requestParams.append("principale");
			requestParams.append("=").append(isPrincipale());
			requestParams.append("&");
			requestParams.append("revisione");
			requestParams.append("=").append(isRevisione());
			requestParams.append("&");
			requestParams.append("headerRevisionato");
			requestParams.append("=").append(getHeaderRevisionato());
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
	
	
	public DocumentaleAllegatoRiunioneList listaAllegati(ActionContext context) throws SQLException, IOException {
		
	
	DocumentaleAllegatoRiunioneList docList = null;
		
	HttpURLConnection conn = null;
	String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_RIUNIONI");
	//STAMPE
	

	URL obj;
	try {
		obj = new URL(lista_url);
	
	conn = (HttpURLConnection) obj.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("GET");
	StringBuffer requestParams = new StringBuffer();
	requestParams.append("idRiunione");
	requestParams.append("=").append(getIdRiunione());
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
	  
		docList = new DocumentaleAllegatoRiunioneList();
		 docList.creaElenco(jo);
		 docList = documentalePaginazione(context, docList);
			
		context.getRequest().setAttribute("listaAllegati", docList);
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
		context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
		context.getRequest().setAttribute("label", "documents");
		return null;
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally{
		conn.disconnect();
	}
	return docList;
	}
	
	
	public DocumentaleAllegatoRiunioneList listaRevisioni(ActionContext context) throws SQLException, IOException {
		
		
		DocumentaleAllegatoRiunioneList docList = null;
			
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI_RIUNIONI_REVISIONI");
		//STAMPE
		

		URL obj;
		try {
			obj = new URL(lista_url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("headerRevisionato");
		requestParams.append("=").append(getHeaderRevisionato());
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
		  
			docList = new DocumentaleAllegatoRiunioneList();
			 docList.creaElenco(jo);
			 docList = documentalePaginazione(context, docList);
				
			context.getRequest().setAttribute("listaAllegati", docList);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return null;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


public DocumentaleAllegatoRiunioneList documentalePaginazione(ActionContext context, DocumentaleAllegatoRiunioneList listaDocs){
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

public File getFileDaCaricare() {
	return fileDaCaricare;
}

public void setFileDaCaricare(File file) {
	this.fileDaCaricare = file;
}

public boolean isPrincipale() {
	return principale;
}

public void setPrincipale(boolean principale) {
	this.principale = principale;
}
public boolean isRevisione() {
	return revisione;
}

public void setRevisione(boolean revisione) {
	this.revisione = revisione;
}

public int getNumeroRevisione() {
	return numeroRevisione;
}

public void setNumeroRevisione(int numeroRevisione) {
	this.numeroRevisione = numeroRevisione;
}

public void setNumeroRevisione(String numeroRevisione) {
	if (numeroRevisione!=null && !numeroRevisione.equals("null") && !numeroRevisione.equals(""))
	this.numeroRevisione =Integer.parseInt(numeroRevisione);
}


public String getHeaderRevisionato() {
	return headerRevisionato;
}

public void setHeaderRevisionato(String headerRevisionato) {
	this.headerRevisionato = headerRevisionato;
}
}
