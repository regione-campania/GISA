/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONObject;
import it.us.web.util.properties.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadNewFile extends GenericAction {

	
	private int idAutopsia = -1;
	private int idAccettazione = -1;
	private int idIstopatologico = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension = "";
	private String f1 = "";
	private String tipoAllegato = "";

	

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


	public int getIdAutopsia() {
		return idAutopsia;
	}

	public void setIdAutopsia(int idAutopsia) {
		this.idAutopsia = idAutopsia;
	}

	public void setIdAutopsia(String idAutopsia) {
		if (idAutopsia != null && !idAutopsia.equals("null"))
			this.idAutopsia = Integer.parseInt(idAutopsia);
	}

	public int getIdAccettazione() {
		return idAccettazione;
	}

	public void setIdAccettazione(int idAccettazione) {
		this.idAccettazione = idAccettazione;
	}

	public void setIdAccettazione(String idAccettazione) {
		if (idAccettazione != null && !idAccettazione.equals("null"))
			this.idAccettazione = Integer.parseInt(idAccettazione);
	}

	public int getIdIstopatologico() {
		return idIstopatologico;
	}

	public void setIdIstopatologico(int idIstopatologico) {
		this.idIstopatologico = idIstopatologico;
	}

	public void setIdIstopatologico(String idIstopatologico) {
		if (idIstopatologico != null && !idIstopatologico.equals("null"))
			this.idIstopatologico = Integer.parseInt(idIstopatologico);
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
	
	private String byteArrayToString(byte[] ba) throws UnsupportedEncodingException {
		String s = new String(ba, "ISO-8859-1");
		return s;

	}

	public String getTipoAllegato() {
		return tipoAllegato;
	}

	public void setTipoAllegato(String tipoAllegato) {
		if (tipoAllegato == null || tipoAllegato.equals(""))
			tipoAllegato = "Allegato";
		else
			this.tipoAllegato = tipoAllegato;
	}
	
	public void can() throws AuthorizationException {
	}

	@Override
	public void setSegnalibroDocumentazione() {
	}

	public void execute() throws Exception {
		Context ctx = new InitialContext();

		String ip = req.getRemoteAddr();
		int idUser = -1;
		if (utente != null)
			idUser = utente.getId();
		
		
		Action action = null;

		Boolean documentaleDisponibile = Boolean.valueOf(Application.get("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = Application.get("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			setErrore(documentaleNonDisponibileMessaggio);
			gotoPage("jsp/documentale/error.jsp");
		}

		else {


			String op = "";
			String filePath = this.getPath();

			ServletFileUpload uploadHandler = new ServletFileUpload( new DiskFileItemFactory() );
			int fileSize = -1;
			 List<FileItem> items = uploadHandler.parseRequest( req );
			 for(FileItem item : items)
	            {
	                if(!item.isFormField())
	                {
	                	String idAutopsia = (String)  getMultipartParameter(items, "idAutopsia");
						String idAccettazione = (String)  getMultipartParameter(items, "idAccettazione");
						String idIstopatologico = (String)  getMultipartParameter(items, "idIstopatologico");
						String subject = (String)  getMultipartParameter(items, "subject");
						String filename = item.getName();
						byte[] ba =  item.get(); 

						setIdAutopsia(idAutopsia);
						setIdAccettazione(idAccettazione);
						setIdIstopatologico(idIstopatologico);
						setTipoAllegato(tipoAllegato);
						
						int maxFileSize = -1;
						int mb1size = 1048576;
						if (Application.get("MAX_SIZE_ALLEGATI") != null)
							maxFileSize = Integer.parseInt(Application.get("MAX_SIZE_ALLEGATI"));

						if (fileSize > maxFileSize) { // 2 mb
							String maxSizeString = String.format("%.2f", (double) maxFileSize / (double) mb1size);
							req.setAttribute("messaggioPost", "Errore! Selezionare un file con dimensione inferiore a " + maxSizeString + " MB.");
							goToAction(new ListaAllegati());
						}

						
						String baString = "";
						baString = byteArrayToString(ba);
						
						chiamaServerDocumentale(context, idUser, ip, baString,  subject, filename, String.valueOf(item.getSize()));
						
	                }
	            }
			 
				req.setAttribute("idAutopsia", String.valueOf(idAutopsia));
				req.setAttribute("idAccettazione", String.valueOf(idAccettazione));
				req.setAttribute("idIstopatologico", String.valueOf(idIstopatologico));

			
				String readonly 	 = stringaFromRequest("readonly");
				req.setAttribute("readonly", 	readonly);
				
				goToAction(new ListaAllegati());
			

			
			}
	}

	private void chiamaServerDocumentale(ServletContext context, int userId, String userIp, String baString, String oggetto, String fileName, String fileDimension) throws Exception {
		
	
		String url = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/UploadFile";
		System.out.println("[DOCUMENTALE VAM] Connessione a: " + url);

		URL obj = null;
		HttpURLConnection conn = null;
		
		try {
		obj = new URL(url);

		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");

		StringBuffer requestParams = new StringBuffer();
		requestParams.append("baString");
		requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
		requestParams.append("&");
		requestParams.append("provenienza");
		requestParams.append("=").append("vam");
		requestParams.append("&");
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipoAllegato);
		requestParams.append("&");
		requestParams.append("idAutopsia");
		requestParams.append("=").append(getIdAutopsia());
		requestParams.append("&");
		requestParams.append("idAccettazione");
		requestParams.append("=").append(getIdAccettazione());
		requestParams.append("&");
		requestParams.append("idIstopatologico");
		requestParams.append("=").append(getIdIstopatologico());
		requestParams.append("&");
		requestParams.append("oggetto");
		requestParams.append("=").append(fileName);
		requestParams.append("&");
		requestParams.append("filename");
		requestParams.append("=").append(fileName);
		requestParams.append("&");
		requestParams.append("fileDimension");
		requestParams.append("=").append(fileDimension);
		requestParams.append("&");
		requestParams.append("idUtente");
		requestParams.append("=").append(userId);
		requestParams.append("&");
		requestParams.append("ipUtente");
		requestParams.append("=").append(userIp);
		
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

		String codDocumento = null;

		try {
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
		} catch (Exception e) {
		}
		if (codDocumento == null || codDocumento.equals("null") || codDocumento.equals(""))
			messaggioPost = "Possibile errore nel caricamento del file. Controllarne la presenza nella lista sottostante.";
		else
			messaggioPost = "OK! Caricamento completato con successo.";

		req.setAttribute("messaggioPost", messaggioPost);
		
		
	} catch (ConnectException e1) {
		setErrore("SERVER DOCUMENTALE OFFLINE");
		gotoPage("jsp/documentale/error.jsp");
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		setErrore("ERRORE NEL CARICAMENTO DEL FILE");
		gotoPage("jsp/documentale/error.jsp");
	} catch (IOException e) {
		e.printStackTrace();
		setErrore("ERRORE NEL CARICAMENTO DEL FILE");
		gotoPage("jsp/documentale/error.jsp");
	}

	finally {
		conn.disconnect();
	}
	
	}

	

	
	  private String getPath() {
		  
		String	destinationDirPath	= Application.get( "UPLOAD_ROOT_FOLDER" );
		return destinationDirPath;
		  
		  }
		private String getMultipartParameter(List<FileItem> items, String parameter) throws FileUploadException
		{
			String ret = null;
			
	        for( FileItem item : items )
	        {
	        	if( item.isFormField() )
	        	{
	        		if( parameter.equals( item.getFieldName() ) )
	        		{
	        			ret = item.getString();
	        			break;
	        		}
	        	}
	        }
			
			return ret;
		}
}
