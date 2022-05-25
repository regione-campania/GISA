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
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoInviiList;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.actions.AccountVigilanza;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.oia.base.Organization;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.suap.base.LineaProduttivaList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.oreilly.servlet.MultipartRequest;

public class GestioneAllegatiUploadDelegaApicoltore extends CFSModule {

	private byte[] ba = null;
	private int orgId = -1;
	private int stabId = -1;
	private int altId = -1;
	private int ticketId = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension="";
	private int parentId = -1;
	private int folderId = -1;
	private int grandparentId = -1;
	private String f1="";
	private String actionOrigine="";
	private int idNodo=-1;
	private String tipoAllegato="";
	private int idDelega ;
	private File fileDaCaricare = null;
	

	
	
	public File getFileDaCaricare() {
		return fileDaCaricare;
	}

	public void setFileDaCaricare(File fileDaCaricare) {
		this.fileDaCaricare = fileDaCaricare;
	}

	public int getIdDelega() {
		return idDelega;
	}

	public void setIdDelega(int idDelega) {
		this.idDelega = idDelega;
	}

	public int getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(int idNodo) {
		this.idNodo = idNodo;
	}

	public void setIdNodo(String idNodo) {
		if (idNodo!=null && !idNodo.equals("null"))
			this.idNodo = Integer.parseInt(idNodo);
	}

	public String getActionOrigine() {
		return actionOrigine;
	}

	public void setActionOrigine(String actionOrigine) {
		this.actionOrigine = actionOrigine;
	}

	public byte[] getBa() {
		return ba;
	}

	public int getGrandparentId() {
		return grandparentId;
	}

	public void setGrandparentId(int grandparentId) {
		this.grandparentId = grandparentId;
	}

	public void setGrandparentId(String grandparentId) {
		if (grandparentId!=null && !grandparentId.equals("null"))
			this.grandparentId = Integer.parseInt(grandparentId);
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void setOrgId(String orgId) {
		if (orgId!=null && !orgId.equals("null"))
			this.orgId = Integer.parseInt(orgId);
	}

	public int getStabId() {
		return stabId;
	}

	public void setStabId(int stabId) {
		this.stabId = stabId;
	}

	public void setStabId(String stabId) {
		if (stabId!=null && !stabId.equals("null"))
			this.stabId = Integer.parseInt(stabId);
	}

	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

	public void setAltId(String altId) {
		if (altId!=null && !altId.equals("null"))
			this.altId = Integer.parseInt(altId);
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setParentId(String parentId) {
		if (parentId!=null && !parentId.equals("null"))
			this.parentId = Integer.parseInt(parentId);
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public void setFolderId(String folderId) {
		if (folderId!=null && !folderId.equals("null"))
			this.folderId = Integer.parseInt(folderId);
	}

	public String getFileDimension() {
		return fileDimension;
	}

	public void setFileDimension(String fileDimension) {
		this.fileDimension = fileDimension;
	}
	
	public void setTipoAllegato(String tipoAllegato) {
		if (tipoAllegato==null || tipoAllegato.equals(""))
			tipoAllegato="Allegato";
		else
			this.tipoAllegato = tipoAllegato;
	}
	
	private String byteArrayToString () throws UnsupportedEncodingException{
		String s = new String(ba, "ISO-8859-1");
		return s;


	}

	public String allegaFileDelega(MultipartRequest multi,ActionContext context) throws SQLException, IOException
	{
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio =ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile){
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		String op ="";
		//String filePath = this.getPath(context, "allegati");
		String filePath = getWebInfPath(context,"tmp_allegati");

		String id = (String) multi.getParameter("id");
		String sId = (String) multi.getParameter("stabId");
		String aId = (String) multi.getParameter("altId");
		String tId = (String) multi.getParameter("ticketId");
		String subject = (String) multi.getParameter("subject");
		String folderId = (String) multi.getParameter("folderId");
		String parentId = (String) multi.getParameter("parentId");
		String grandparentId = (String) multi.getParameter("grandparentId");
		op = (String) multi.getParameter("op");
		String tipoAllegato = (String) multi.getParameter("tipoAllegato");
		actionOrigine = (String) multi.getParameter("actionOrigine");
		String idN = (String) multi.getParameter("idNodo");

		setParentId(parentId);
		setGrandparentId(grandparentId);
		setFolderId(folderId);
		setOrgId(id);
		setStabId(sId);
		setAltId(aId);
		setTicketId(tId);
		setOggetto(subject);
		setIdNodo(idN);
		setTipoAllegato("AllegatoDelega");

		context.getRequest().setAttribute("op", op);
		context.getRequest().setAttribute("folderId", String.valueOf(getFolderId()));
		context.getRequest().setAttribute("parentId", String.valueOf(getParentId()));
		context.getRequest().setAttribute("grandparentId", String.valueOf(getGrandparentId()));
		context.getRequest().setAttribute("orgId", String.valueOf(getOrgId()));
		context.getRequest().setAttribute("stabId", String.valueOf(getStabId()));
		context.getRequest().setAttribute("altId", String.valueOf(getAltId()));
		context.getRequest().setAttribute("ticketId", String.valueOf(getTicketId()));

		int fileSize = -1;

		 if (fileDaCaricare instanceof File ) {
	          //Update the database with the resulting file
	          File newFileInfo = fileDaCaricare;
	          //Insert a file description record into the database
	          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
	          thisItem.setLinkModuleId(Constants.ACCOUNTS);
	          thisItem.setEnteredBy(getUserId(context));
	          thisItem.setModifiedBy(getUserId(context));
	          thisItem.setFolderId(getFolderId());
	          thisItem.setSubject(subject);
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
	    	    
	    		  if (op.equalsIgnoreCase("suap"))
	    		  {
	    			  
	    			 context.getRequest().setAttribute("FileCaricatoSuap", this.getOggetto());
	    			 context.getRequest().setAttribute("indice", context.getParameter("indice"));

	    			  return "SuapAllegatiOK";
	    		  }
	    		  else if (op.equalsIgnoreCase("suapmobile"))
   		  {
   			  
   			 context.getRequest().setAttribute("FileCaricatoSuap", this.getOggetto());
   			 context.getRequest().setAttribute("indice", context.getParameter("indice"));

   			  return "SuapAllegatiOK";
   		  }
	    			  else
				return "";
	      }
	      
	     
	      
//		  File file = new File(fileDaCaricare);
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
	
			
			return chiamaServerDocumentale(context);
		
	}
	
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException{

		String ip = context.getIpAddress();

		String baString="";
		baString = byteArrayToString();

		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI");
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
			requestParams.append("orgId");
			requestParams.append("=").append(getOrgId());
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(getStabId());
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(getAltId());
			requestParams.append("&");
			requestParams.append("ticketId");
			requestParams.append("=").append(getTicketId());
			requestParams.append("&");
			requestParams.append("idNodo");
			requestParams.append("=").append(getIdNodo());
			requestParams.append("&");
			requestParams.append("oggetto");
			requestParams.append("=").append(getOggetto());
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(getParentId());
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(getFolderId());
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
			requestParams.append("&");
			requestParams.append("apiarioDelega");
			requestParams.append("=").append(idDelega);

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

		if (actionOrigine!=null && actionOrigine.equals("OiaVigilanza")){ //CHIUSURA AUTORITA COMPETENTI: ALLEGA FILE 
			AccountVigilanza AV = new AccountVigilanza();
			String tempIdControllo = String.valueOf(ticketId);
			int tempid = orgId;
			Connection db = null;
			try {
				db = this.getConnection(context);
				Organization newOrg = new Organization(db, tempid);
				context.getRequest().setAttribute("siteId", newOrg.getSiteId());
				context.getRequest().setAttribute("tipologia", 1);
				context.getRequest().setAttribute("id", tempIdControllo);
				context.getRequest().setAttribute("idNodo", String.valueOf(idNodo));
				//Load the organization
				context.getRequest().setAttribute("OrgDetails", newOrg);
				return AV.executeCommandChiudi(context);
			}
			catch(SQLException e1){
				e1.printStackTrace();
			}
			finally{
				this.freeConnection(context, db);
			}
		}

		return "" ;

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






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public DocumentaleAllegato listaAllegati(ActionContext context) throws SQLException, IOException, ParseException {
		
		
		DocumentaleAllegatoList docList = null;
			
		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI");
		//STAMPE
		
	//System.out.println("URLLLLLLL : "+lista_url);
		URL obj;
		try {
			obj = new URL(lista_url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("apiarioDelega");
		requestParams.append("=").append(idDelega);
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
		  
			docList = new DocumentaleAllegatoList();
			 docList.creaElenco(jo);
			 
				
			context.getRequest().setAttribute("listaAllegati", docList);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			throw e;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		finally{
			conn.disconnect();
		}
		if(docList.size()>0)
			return (DocumentaleAllegato)docList.get(0);
		return null ;
		}
	
}
