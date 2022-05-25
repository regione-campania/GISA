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
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public class GestioneAllegatiUpload extends CFSModule {

	private byte[] ba = null;
	private int idAnimale = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension = "";
	private int parentId = -1;
	private int folderId = -1;
	private int grandparentId = -1;
	private String f1 = "";
	private String tipoAllegato = "";

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public void setIdAnimale(String idAnimale) {
		if (idAnimale != null && !idAnimale.equals("null"))
			this.idAnimale = Integer.parseInt(idAnimale);
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
		if (grandparentId != null && !grandparentId.equals("null"))
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
		if (parentId != null && !parentId.equals("null"))
			this.parentId = Integer.parseInt(parentId);
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public void setFolderId(String folderId) {
		if (folderId != null && !folderId.equals("null"))
			this.folderId = Integer.parseInt(folderId);
	}

	public String getFileDimension() {
		return fileDimension;
	}

	public void setFileDimension(String fileDimension) {
		this.fileDimension = fileDimension;
	}

	public String executeCommandAllegaFile(ActionContext context) throws IOException, SQLException,
			IllegalStateException, ServletException, FileUploadException {

		if (!hasPermission(context, "documentale_documents-add")) {
			return ("PermissionError");
		}

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		String filePath = this.getPath(context, "allegati");
		HttpMultiPartParser multiPart = new HttpMultiPartParser();
		
		HashMap parts = multiPart.parseData(context.getRequest(), filePath);
		String idAnimale = (String) parts.get("idAnimale");
		String subject = (String) parts.get("subject");
		String folderId = (String) parts.get("folderId");
		String parentId = (String) parts.get("parentId");
		String grandparentId = (String) parts.get("grandparentId");
		String tipoAllegato = (String) parts.get("tipoAllegato");
		
		setParentId(parentId);
		setGrandparentId(grandparentId);
		setFolderId(folderId);
		setIdAnimale(idAnimale);
		setOggetto(subject);
		setTipoAllegato(tipoAllegato);

		context.getRequest().setAttribute("folderId", String.valueOf(getFolderId()));
		context.getRequest().setAttribute("parentId", String.valueOf(getParentId()));
		context.getRequest().setAttribute("grandparentId", String.valueOf(getGrandparentId()));
		context.getRequest().setAttribute("idAnimale", String.valueOf(getIdAnimale()));
		
		int fileSize = -1;

		if ((Object) parts.get("file1") instanceof FileInfo) {
			// Update the database with the resulting file
			FileInfo newFileInfo = (FileInfo) parts.get("file1");
			// Insert a file description record into the database
			com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
			thisItem.setLinkModuleId(Constants.ACCOUNTS);
			thisItem.setEnteredBy(getUserId(context));
			thisItem.setModifiedBy(getUserId(context));
			thisItem.setFolderId(getFolderId());
			thisItem.setSubject(subject);
			thisItem.setClientFilename(newFileInfo.getClientFileName());
			thisItem.setFilename(newFileInfo.getRealFilename());
			setFilename(newFileInfo.getRealFilename());
			thisItem.setVersion(1.0);
			thisItem.setSize(newFileInfo.getSize());
			fileSize = thisItem.getSize();
			setFileDimension(String.valueOf(fileSize));
		}

		int maxFileSize = -1;
		int mb1size = 1048576;
		if (ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI") != null)
			maxFileSize = Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));

		if (fileSize > maxFileSize) { // 2 mb
			String maxSizeString = String.format("%.2f", (double) maxFileSize / (double) mb1size);
			context.getRequest().setAttribute("messaggioPost",
					"Errore! Selezionare un file con dimensione inferiore a " + maxSizeString + " MB.");

			return executeCommandListaAllegati(context);
		}

		f1 = filePath + filename;

		File file = new File(f1);
		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}

		ba = buffer;
		return chiamaServerDocumentale(context);

	}

	
	public String executeCommandAllegaFileCommissione(ActionContext context) throws IOException, SQLException,
	IllegalStateException, ServletException, FileUploadException {

if (!hasPermission(context, "file_commissione_diritti_animali-view")) {
	return ("PermissionError");
}

Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
String documentaleNonDisponibileMessaggio = ApplicationProperties
		.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
if (!documentaleDisponibile) {
	context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
	context.getRequest().setAttribute("label", "documents");
	return "documentaleAllegatiError";
}

String filePath = this.getPath(context, "file_commissione");
HttpMultiPartParser multiPart = new HttpMultiPartParser();

HashMap parts = multiPart.parseData(context.getRequest(), filePath);
String idAnimale = (String) parts.get("idAnimale");
String subject = (String) parts.get("subject");
String folderId = (String) parts.get("folderId");
String parentId = (String) parts.get("parentId");
String grandparentId = (String) parts.get("grandparentId");
String tipoAllegato = (String) parts.get("tipoAllegato");

setParentId(parentId);
setGrandparentId(grandparentId);
setFolderId(folderId);
setIdAnimale(idAnimale);
setOggetto(subject);
setTipoAllegato(tipoAllegato);

context.getRequest().setAttribute("folderId", String.valueOf(getFolderId()));
context.getRequest().setAttribute("parentId", String.valueOf(getParentId()));
context.getRequest().setAttribute("grandparentId", String.valueOf(getGrandparentId()));
context.getRequest().setAttribute("idAnimale", String.valueOf(getIdAnimale()));

int fileSize = -1;

if ((Object) parts.get("file") instanceof FileInfo) {
	// Update the database with the resulting file
	FileInfo newFileInfo = (FileInfo) parts.get("file");
	// Insert a file description record into the database
	com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
	thisItem.setLinkModuleId(Constants.ACCOUNTS);
	thisItem.setEnteredBy(getUserId(context));
	thisItem.setModifiedBy(getUserId(context));
	thisItem.setFolderId(getFolderId());
	thisItem.setSubject(subject);
	thisItem.setClientFilename(newFileInfo.getClientFileName());
	thisItem.setFilename(newFileInfo.getRealFilename());
	setFilename(newFileInfo.getRealFilename());
	thisItem.setVersion(1.0);
	thisItem.setSize(newFileInfo.getSize());
	fileSize = thisItem.getSize();
	setFileDimension(String.valueOf(fileSize));
}

int maxFileSize = -1;
int mb1size = 1048576;
if (ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI") != null)
	maxFileSize = Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));

if (fileSize > maxFileSize) { // 2 mb
	String maxSizeString = String.format("%.2f", (double) maxFileSize / (double) mb1size);
	context.getRequest().setAttribute("messaggioPost",
			"Errore! Selezionare un file con dimensione inferiore a " + maxSizeString + " MB.");

	return executeCommandListaAllegati(context);
}

f1 = filePath + filename;

File file = new File(f1);
byte[] buffer = new byte[(int) file.length()];
InputStream ios = null;
try {
	ios = new FileInputStream(file);
	if (ios.read(buffer) == -1) {
		throw new IOException("EOF reached while trying to read the whole file");
	}
} finally {
	try {
		if (ios != null)
			ios.close();
	} catch (IOException e) {
	}
}

ba = buffer;





String ip = context.getIpAddress();

String baString = "";
baString = byteArrayToString();

HttpURLConnection conn = null;
String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
		+ ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI");
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
	requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
	requestParams.append("&");
	requestParams.append("tipoCertificato");
	requestParams.append("=").append(tipoAllegato);
	requestParams.append("&");
	requestParams.append("idAnimale");
	requestParams.append("=").append(getIdAnimale());
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

	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(requestParams.toString());
	wr.flush();

	conn.getContentLength();

	String messaggioPost = "";
	
	System.out.println("Connessione al documentale: " + conn.toString());

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

	context.getRequest().setAttribute("messaggioPost", messaggioPost);

} catch (ConnectException e1) {
	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
	context.getRequest().setAttribute("label", "documents");
	return "documentaleAllegatiError";
} catch (MalformedURLException e) {
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

finally {
	conn.disconnect();
}


	return executeCommandListaFileCommissione(context);



}
	
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException {

		String ip = context.getIpAddress();

		String baString = "";
		baString = byteArrayToString();

		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI");
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
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoAllegato);
			requestParams.append("&");
			requestParams.append("idAnimale");
			requestParams.append("=").append(getIdAnimale());
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

			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		} catch (ConnectException e1) {
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		} catch (MalformedURLException e) {
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

		finally {
			conn.disconnect();
		}

		
			return executeCommandListaAllegati(context);

	}

	public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {

		if (!hasPermission(context, "documentale_documents-view")) {
			return ("PermissionError");
		}

		int folderId = -1;
		int parentId = -1;
		int grandparentId = -1;
		int idAnimale = -1;
		String folderIdString = "";
		String parentIdString = "";
		String idAnimaleString = "";
		String messaggioPost = "";

		messaggioPost = context.getRequest().getParameter("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null") && !messaggioPost.equals(""))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);


		String uploadOk = (String) context.getRequest().getAttribute("uploadOk");
		folderIdString = (String) context.getRequest().getAttribute("folderId");
		parentIdString = (String) context.getRequest().getAttribute("parentId");
		idAnimaleString = (String) context.getRequest().getAttribute("idAnimale");
		

		if (idAnimaleString == null)
			idAnimaleString = context.getRequest().getParameter("idAnimale");
		if (parentIdString == null)
			parentIdString = context.getRequest().getParameter("parentId");
		if (folderIdString == null)
			folderIdString = context.getRequest().getParameter("folderId");
		
		if (idAnimaleString != null && !idAnimaleString.equals("null") && !idAnimaleString.equals(""))
			idAnimale = Integer.parseInt(idAnimaleString);
		if (parentIdString != null && !parentIdString.equals("null") && !parentIdString.equals(""))
			parentId = Integer.parseInt(parentIdString);
		if (folderIdString != null && !folderIdString.equals("null") && !folderIdString.equals(""))
			folderId = Integer.parseInt(folderIdString);

		Connection db = null;
		try {
			db = this.getConnection(context);
			Animale thisAnimale = new Animale(db, idAnimale);
			context.getRequest().setAttribute("Animale", thisAnimale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("uploadOk", uploadOk);
		

		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI");
		// STAMPE

		URL obj;
		try {
			obj = new URL(lista_url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("idAnimale");
			requestParams.append("=").append(idAnimale);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

			conn.getContentLength();

			String codDocumento = "";
			String nomeCartella = "";
			String grandparentIdString = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String inputLine;
			StringBuffer html = new StringBuffer();
			if (in != null) {
				html.append(in.readLine());
			}
			in.close();
			JSONArray jo = new JSONArray(html.toString());

			nomeCartella = jo.get(0).toString();
			grandparentIdString = jo.get(1).toString();

			DocumentaleAllegatoList docList = new DocumentaleAllegatoList();
			docList.creaElenco(jo);

			context.getRequest().setAttribute("nomeCartella", nomeCartella);
			context.getRequest().setAttribute("grandparentId", grandparentIdString);

			docList = documentalePaginazione(context, docList);

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
		} finally {
			conn.disconnect();
		}

		return "listaAllegatiOk";
	}
	
	public String executeCommandListaFileCommissione(ActionContext context) throws SQLException, IOException {

		if (!hasPermission(context, "file_commissione_diritti_animali-view")) {
			return ("PermissionError");
		}

		int folderId = -1;
		int parentId = -1;
		int grandparentId = -1;
		int idAnimale = 42;
		String folderIdString = "";
		String parentIdString = "";
		String messaggioPost = "";
		

		messaggioPost = context.getRequest().getParameter("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null") && !messaggioPost.equals(""))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);


		String uploadOk = (String) context.getRequest().getAttribute("uploadOk");
		folderIdString = (String) context.getRequest().getAttribute("folderId");
		parentIdString = (String) context.getRequest().getAttribute("parentId");
		

		if (parentIdString == null)
			parentIdString = context.getRequest().getParameter("parentId");
		if (folderIdString == null)
			folderIdString = context.getRequest().getParameter("folderId");
		
		if (parentIdString != null && !parentIdString.equals("null") && !parentIdString.equals(""))
			parentId = Integer.parseInt(parentIdString);
		if (folderIdString != null && !folderIdString.equals("null") && !folderIdString.equals(""))
			folderId = Integer.parseInt(folderIdString);

		Connection db = null;
		PreparedStatement pst = null;
		try {
			db = this.getConnection(context);
			Animale thisAnimale = new Animale(db, idAnimale);
			context.getRequest().setAttribute("Animale", thisAnimale);
			
			HashMap<Integer,String> stati = new HashMap<Integer,String>();
			pst = db.prepareStatement(" select * from stati_file_commissione ");
			ResultSet result = pst.executeQuery();
			while(result.next())
				stati.put(result.getInt("id"), result.getString("stato"));
			context.getRequest().setAttribute("stati", stati);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("uploadOk", uploadOk);
		

		HttpURLConnection conn = null;
		String lista_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI");
		// STAMPE

		URL obj;
		try {
			obj = new URL(lista_url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("idAnimale");
			requestParams.append("=").append(idAnimale);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
			requestParams.append("&");
			requestParams.append("parentId");
			requestParams.append("=").append(parentId);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

			conn.getContentLength();

			String codDocumento = "";
			String nomeCartella = "";
			String grandparentIdString = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String inputLine;
			StringBuffer html = new StringBuffer();
			if (in != null) {
				html.append(in.readLine());
			}
			in.close();
			JSONArray jo = new JSONArray(html.toString());

			nomeCartella = jo.get(0).toString();
			grandparentIdString = jo.get(1).toString();

			DocumentaleAllegatoList docList = new DocumentaleAllegatoList();
			docList.creaElenco(jo);

			context.getRequest().setAttribute("nomeCartella", nomeCartella);
			context.getRequest().setAttribute("grandparentId", grandparentIdString);

			docList = documentalePaginazione(context, docList);

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
		} finally {
			conn.disconnect();
		}

		return "fileCommissioneListaOk";
	}

	public String executeCommandCreaNuovaCartella(ActionContext context) throws SQLException, IOException {

		if (!hasPermission(context, "documentale_documents-add")) {
			return ("PermissionError");
		}
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		
		setIdAnimale(context.getRequest().getParameter("idAnimale"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));

		if (context.getRequest().getParameter("new") != null)
			return "newCartellaOk";

		String nome = context.getRequest().getParameter("nomeCartella");

		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI");
		// STAMPE

		URL obj;

		try {
			obj = new URL(url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
			requestParams.append("&");
			requestParams.append("idAnimale");
			requestParams.append("=").append(idAnimale);
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

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

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

	public String executeCommandDownloadPDF(ActionContext context) throws SQLException, IOException {

		// recupero l'id timbro
		String codDocumento = null;
		codDocumento = context.getRequest().getParameter("codDocumento");
		if (codDocumento == null)
			codDocumento = (String) context.getRequest().getAttribute("codDocumento");
		String idDocumento = null;

		String tipoDocumento = null;
		tipoDocumento = context.getRequest().getParameter("tipoDocumento");
		if (tipoDocumento == null)
			tipoDocumento = (String) context.getRequest().getAttribute("tipoDocumento");

		String nomeDocumento = null;
		nomeDocumento = context.getRequest().getParameter("nomeDocumento");
		if (nomeDocumento == null)
			nomeDocumento = (String) context.getRequest().getAttribute("nomeDocumento");

		String estensione = "." + tipoDocumento;

		idDocumento = context.getRequest().getParameter("idDocumento");

		String titolo = "";
		String provenienza = ApplicationProperties.getProperty("APP_NAME_BDU");

		String download_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")
				+ "/" + ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");

		if (codDocumento != null && !codDocumento.equals("null")) {
			download_url += "?codDocumento=" + codDocumento;
			titolo = codDocumento + estensione;
		} else {

			download_url += "?idDocumento=" + idDocumento + "&provenienza=" + provenienza;
			titolo = provenienza + "_" + idDocumento + estensione;
		}

		if (context.getRequest().getAttribute("titolo") != null)
			titolo = (String) context.getRequest().getAttribute("titolo");

		boolean fileCommissione = context.getRequest().getParameter("fileCommissione")!=null;
		if (nomeDocumento != null && !nomeDocumento.equals("null") && !fileCommissione)
			titolo = codDocumento + "_" + nomeDocumento;
		if (nomeDocumento != null && !nomeDocumento.equals("null") && fileCommissione)
			titolo = nomeDocumento;

		// Cartella temporanea sull'APP
		String path_doc = getWebInfPath(context, "tmp_documentale");
		// Creare il file ...(ispirarsi a GestoreGlifo servlet)

		File theDir = new File(path_doc);
		theDir.mkdirs();

		File inputFile = new File(path_doc + titolo);
		if (!inputFile.exists())
			inputFile.createNewFile();
		URL copyurl;
		InputStream outputFile = null;
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

			// if (new File(fileName).exists()){
			// Find this file id in database to get file name, and file type

			// You must tell the browser the file type you are going to send
			// for example application/pdf, text/plain, text/html, image/jpg
			context.getResponse().setContentType(fileType);

			// Make sure to show the download dialog
			context.getResponse().setHeader("Content-disposition", "attachment; filename=" + titolo);

			// Assume file name is retrieved from database
			// For example D:\\file\\test.pdf

			File my_file = new File(inputFile.getAbsolutePath());

			// This should send the file to browser
			OutputStream out = context.getResponse().getOutputStream();
			FileInputStream in = new FileInputStream(my_file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
				try {
					out.write(buffer, 0, length);
				} catch (Exception e1) {
					in.close();
					System.out.println("[DOCUMENTALE BDU] Sessione invalidata");
					return ("-none-");
				}
			}
			in.close();
			out.flush();
			return ("-none-");
		}

		catch (ConnectException e1) {
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

	}
	
	
	
	public String executeCommandDownloadPDFFileCommissione(ActionContext context) throws SQLException, IOException 
	{
		return executeCommandDownloadPDF(context);
	}

	public String executeCommandGestisciCartella(ActionContext context) throws SQLException, IOException {

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		
		// Completare
		setIdAnimale(context.getRequest().getParameter("idAnimale"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		setGrandparentId(context.getRequest().getParameter("grandparentId"));
		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("grandparentId", String.valueOf(grandparentId));

		int idCartella = -1;
		if (context.getRequest().getParameter("idCartella") != null)
			idCartella = Integer.parseInt(context.getRequest().getParameter("idCartella"));

		if (context.getRequest().getParameter("operazione").equals("rinomina")) { // RINOMINA
																					// CARTELLA

			if (!hasPermission(context, "documentale_documents-edit")) {
				return ("PermissionError");
			}

			String nomeCartella = context.getRequest().getParameter("nomeCartella");
			if (context.getRequest().getParameter("rinominata") == null) { // SE
																			// DEVO
																			// ANCORA
																			// SCEGLIERE
																			// IL
																			// NOME
				context.getRequest().setAttribute("nomeCartellaOld", nomeCartella);
				return "rinominaCartellaOk";
			}

			HttpURLConnection conn = null;
			String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
					+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
			// STAMPE
			System.out.println("\n[DOCUMENTALE BDU] Url generato(chiamata a servlet): " + url.toString());
			URL obj;

			try {
				obj = new URL(url);

				conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");

				StringBuffer requestParams = new StringBuffer();
				requestParams.append("provenienza");
				requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
				requestParams.append("&");
				requestParams.append("idAnimale");
				requestParams.append("=").append(idAnimale);
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

				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE BDU] Conn: " + conn.toString());
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

		else if (context.getRequest().getParameter("operazione").equals("cancella")) { // CANCELLA
																						// CARTELLA
																						// CARTELLA

			if (!hasPermission(context, "documentale_documents-delete")) {
				return ("PermissionError");
			}

			HttpURLConnection conn = null;
			String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
					+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
			// STAMPE
			System.out.println("\n[DOCUMENTALE BDU] Url generato(chiamata a servlet): " + url.toString());
			URL obj;

			try {
				obj = new URL(url);

				conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");

				StringBuffer requestParams = new StringBuffer();
				requestParams.append("provenienza");
				requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
				requestParams.append("&");
				requestParams.append("idAnimale");
				requestParams.append("=").append(idAnimale);
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

				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE BDU] Conn: " + conn.toString());
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

	public String executeCommandGestisciFile(ActionContext context) throws SQLException, IOException {

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		// Completare
		setIdAnimale(context.getRequest().getParameter("idAnimale"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		setGrandparentId(context.getRequest().getParameter("grandparentId"));
		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("grandparentId", String.valueOf(grandparentId));

		int idFile = -1;
		if (context.getRequest().getParameter("idFile") != null)
			idFile = Integer.parseInt(context.getRequest().getParameter("idFile"));

		if (context.getRequest().getParameter("operazione").equals("cancella")) { // RINOMINA
																					// CARTELLA

			if (!hasPermission(context, "documentale_documents-delete")) {
				return ("PermissionError");
			}

			HttpURLConnection conn = null;
			String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
					+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
			// STAMPE
			System.out.println("\n[DOCUMENTALE BDU] Url generato(chiamata a servlet): " + url.toString());
			URL obj;

			try {
				obj = new URL(url);

				conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");

				StringBuffer requestParams = new StringBuffer();
				requestParams.append("provenienza");
				requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
				requestParams.append("&");
				requestParams.append("idAnimale");
				requestParams.append("=").append(idAnimale);
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

				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE BDU] Conn: " + conn.toString());
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
	
	
	
	public String executeCommandGestisciFileCommissione(ActionContext context) throws SQLException, IOException 
	{

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		// Completare
		setIdAnimale(context.getRequest().getParameter("idAnimale"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		setGrandparentId(context.getRequest().getParameter("grandparentId"));
		context.getRequest().setAttribute("idAnimale", String.valueOf(idAnimale));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("grandparentId", String.valueOf(grandparentId));

		int idFile = -1;
		if (context.getRequest().getParameter("idFile") != null)
			idFile = Integer.parseInt(context.getRequest().getParameter("idFile"));

		if (context.getRequest().getParameter("operazione").equals("cancella")) { // RINOMINA
																					// CARTELLA

			if (!hasPermission(context, "documentale_documents-delete")) {
				return ("PermissionError");
			}

			HttpURLConnection conn = null;
			String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
					+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
			// STAMPE
			System.out.println("\n[DOCUMENTALE BDU] Url generato(chiamata a servlet): " + url.toString());
			URL obj;

			try {
				obj = new URL(url);

				conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");

				StringBuffer requestParams = new StringBuffer();
				requestParams.append("provenienza");
				requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_BDU"));
				requestParams.append("&");
				requestParams.append("idAnimale");
				requestParams.append("=").append(idAnimale);
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

				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE BDU] Conn: " + conn.toString());
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
		return executeCommandListaFileCommissione(context);

	}

	private String byteArrayToString() throws UnsupportedEncodingException {
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

//	



}
