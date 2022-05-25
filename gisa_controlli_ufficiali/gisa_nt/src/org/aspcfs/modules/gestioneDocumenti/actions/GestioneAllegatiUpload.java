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
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
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

public class GestioneAllegatiUpload extends CFSModule {

	private byte[] ba = null;
	private int orgId = -1;
	private int stabId = -1;
	private int altId = -1;
	private int ticketId = -1;
	private String oggetto = null;
	private String filename = null;
	private String fileDimension = "";
	private int parentId = -1;
	private int folderId = -1;
	private int grandparentId = -1;
	private String f1 = "";
	private String actionOrigine = "";
	private int idNodo = -1;
	private String tipoAllegato = "";

	public int getIdNodo() {
		return idNodo;
	}

	public void setIdNodo(int idNodo) {
		this.idNodo = idNodo;
	}

	public void setIdNodo(String idNodo) {
		if (idNodo != null && !idNodo.equals("null"))
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

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public void setOrgId(String orgId) {
		if (orgId != null && !orgId.equals("null"))
			this.orgId = Integer.parseInt(orgId);
	}

	public int getStabId() {
		return stabId;
	}

	public void setStabId(int stabId) {
		this.stabId = stabId;
	}

	public void setStabId(String stabId) {
		if (stabId != null && !stabId.equals("null"))
			this.stabId = Integer.parseInt(stabId);
	}

	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}

	public void setAltId(String altId) {
		if (altId != null && !altId.equals("null"))
			this.altId = Integer.parseInt(altId);
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setTicketId(String ticketId) {
		if (ticketId != null && !ticketId.equals("null"))
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

		String op = "";
		//String filePath = this.getPath(context, "allegati");
		String filePath = getWebInfPath(context,"tmp_allegati");
		HttpMultiPartParser multiPart = new HttpMultiPartParser();
		
		HashMap parts = multiPart.parseData(context.getRequest(), filePath);
		String id = (String) parts.get("id");
		String sId = (String) parts.get("stabId");
		String aId = (String) parts.get("altId");
		String tId = (String) parts.get("ticketId");
		String subject = (String) parts.get("subject");
		String folderId = (String) parts.get("folderId");
		String parentId = (String) parts.get("parentId");
		String grandparentId = (String) parts.get("grandparentId");
		op = (String) parts.get("op");
		String tipoAllegato = (String) parts.get("tipoAllegato");
		actionOrigine = (String) parts.get("actionOrigine");
		String idN = (String) parts.get("idNodo");

		setParentId(parentId);
		setGrandparentId(grandparentId);
		setFolderId(folderId);
		setOrgId(id);
		setStabId(sId);
		setAltId(aId);
		setTicketId(tId);
		setOggetto(subject);
		setIdNodo(idN);
		setTipoAllegato(tipoAllegato);

		context.getRequest().setAttribute("op", op);
		context.getRequest().setAttribute("folderId", String.valueOf(getFolderId()));
		context.getRequest().setAttribute("parentId", String.valueOf(getParentId()));
		context.getRequest().setAttribute("grandparentId", String.valueOf(getGrandparentId()));
		context.getRequest().setAttribute("orgId", String.valueOf(getOrgId()));
		context.getRequest().setAttribute("stabId", String.valueOf(getStabId()));
		context.getRequest().setAttribute("altId", String.valueOf(getAltId()));
		context.getRequest().setAttribute("ticketId", String.valueOf(getTicketId()));

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


			if (tipoAllegato != null && tipoAllegato.equals("ChecklistMacelli")) // CHECKLIST MACELLI
			{
				context.getRequest().setAttribute("msg", "<font color=\"red\">Errore! Selezionare un file con dimensione inferiore a " + maxSizeString + " MB.</font>");
				return "uploadChecklistMacelliOK";
		}
			
			if (actionOrigine != null && actionOrigine.equals("OiaVigilanza")) // CHIUSURA
			{
				org.aspcfs.modules.oia.actions.AccountVigilanza oia = new org.aspcfs.modules.oia.actions.AccountVigilanza();
				context.getRequest().setAttribute("messaggioPost",
						"Errore! Selezionare un file con dimensione inferiore a " + maxSizeString + " MB.");
				context.getRequest().setAttribute("id", String.valueOf(ticketId));
				context.getRequest().setAttribute("idNodo", String.valueOf(idNodo));
				return oia.executeCommandTicketDetails(context);
			} else
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

	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException {

		String ip = context.getIpAddress();

		String baString = "";
		baString = byteArrayToString();
		String codDocumento = "";
		
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

		if (tipoAllegato != null && tipoAllegato.equals("ChecklistMacelli")) // CHECKLIST MACELLI
		{
		
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			 org.aspcfs.modules.vigilanza.base.Ticket tic = new org.aspcfs.modules.vigilanza.base.Ticket(db, ticketId);
			tic.setChecklistMacelli(codDocumento);
			tic.aggiornaChecklistMacelli(db, getUserId(context));
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		String msg = "<font color=\"green\">OK! Caricamento completato con successo.</font>";
		context.getRequest().setAttribute("msg", msg);
		return "uploadChecklistMacelliOK";

	}
	
		if (actionOrigine != null && actionOrigine.equals("OiaVigilanza")) { // CHIUSURA
																				// AUTORITA
																				// COMPETENTI:
																				// ALLEGA
																				// FILE
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
				// Load the organization
				context.getRequest().setAttribute("OrgDetails", newOrg);
				return AV.executeCommandChiudi(context);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				this.freeConnection(context, db);
			}
		}

		if (actionOrigine != null && actionOrigine.equals("GenerazioneExcel"))
			return "-none-";
		else
			return executeCommandListaAllegati(context);

	}

	public String executeCommandListaAllegati(ActionContext context) throws SQLException, IOException {

		if (!hasPermission(context, "documentale_documents-view")) {
			return ("PermissionError");
		}

		int folderId = -1;
		int parentId = -1;
		int grandparentId = -1;
		int orgId = -1;
		int stabId = -1;
		int altId = -1;
		int ticketId = -1;
		String folderIdString = "";
		String parentIdString = "";
		String orgIdString = "";
		String stabIdString = "";
		String altIdString = "";
		String ticketIdString = "";
		String messaggioPost = "";

		messaggioPost = context.getRequest().getParameter("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null") && !messaggioPost.equals(""))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		String op = "";

		String uploadOk = (String) context.getRequest().getAttribute("uploadOk");
		folderIdString = (String) context.getRequest().getAttribute("folderId");
		parentIdString = (String) context.getRequest().getAttribute("parentId");
		orgIdString = (String) context.getRequest().getAttribute("orgId");
		stabIdString = (String) context.getRequest().getAttribute("stabId");
		altIdString = (String) context.getRequest().getAttribute("altId");
		ticketIdString = (String) context.getRequest().getAttribute("ticketId");
		op = (String) context.getRequest().getAttribute("op");

		if (orgIdString == null)
			orgIdString = context.getRequest().getParameter("orgId");
		if (stabIdString == null)
			stabIdString = context.getRequest().getParameter("stabId");
		if (altIdString == null)
			altIdString = context.getRequest().getParameter("altId");
		if (ticketIdString == null)
			ticketIdString = context.getRequest().getParameter("ticketId");
		if (parentIdString == null)
			parentIdString = context.getRequest().getParameter("parentId");
		if (folderIdString == null)
			folderIdString = context.getRequest().getParameter("folderId");
		if (op == null)
			op = context.getRequest().getParameter("op");

		if (orgIdString != null && !orgIdString.equals("null") && !orgIdString.equals(""))
			orgId = Integer.parseInt(orgIdString);
		if (stabIdString != null && !stabIdString.equals("null") && !stabIdString.equals(""))
			stabId = Integer.parseInt(stabIdString);
		if (altIdString != null && !altIdString.equals("null") && !altIdString.equals(""))
			altId = Integer.parseInt(altIdString);
		if (ticketIdString != null && !ticketIdString.equals("null") && !ticketIdString.equals(""))
			ticketId = Integer.parseInt(ticketIdString);
		if (parentIdString != null && !parentIdString.equals("null") && !parentIdString.equals(""))
			parentId = Integer.parseInt(parentIdString);
		if (folderIdString != null && !folderIdString.equals("null") && !folderIdString.equals(""))
			folderId = Integer.parseInt(folderIdString);

		Connection db = null;
		try {
			db = this.getConnection(context);

			if (orgId > 0) {
				org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
				org.getOnlyRagioneSociale(db, orgId);
				context.getRequest().setAttribute("OrgDetails", org);
			}

			else if (stabId > 0) {
				Stabilimento stab = new Stabilimento(db, stabId);
				context.getRequest().setAttribute("StabilimentoDettaglio", stab);

				Operatore operatore = new Operatore();
				operatore.queryRecordOperatore(db, stab.getIdOperatore());
				context.getRequest().setAttribute("Operatore", operatore);
			} else if (altId > 0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU_RICHIESTE) {
				org.aspcfs.modules.suap.base.Stabilimento stab = new org.aspcfs.modules.suap.base.Stabilimento(db,
						altId, true);
				context.getRequest().setAttribute("StabilimentoRichiestaDettaglio", stab);

				org.aspcfs.modules.suap.base.Operatore operatore = new org.aspcfs.modules.suap.base.Operatore();
				operatore.queryRecordOperatore(db, stab.getIdOperatore());
				context.getRequest().setAttribute("OperatoreRichiesta", operatore);
			}
		 else if (altId > 0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS) {
			org.aspcfs.modules.sintesis.base.SintesisStabilimento stab = new org.aspcfs.modules.sintesis.base.SintesisStabilimento(db,
					altId, true);
			context.getRequest().setAttribute("StabilimentoSintesisDettaglio", stab);

			org.aspcfs.modules.sintesis.base.SintesisOperatore operatore = new org.aspcfs.modules.sintesis.base.SintesisOperatore();
			operatore.queryRecordOperatore(db, stab.getIdOperatore());
			context.getRequest().setAttribute("OperatoreSintesis", operatore);
		}
		 else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_ANAGRAFICA_STABILIMENTI){
				org.aspcfs.modules.gestioneanagrafica.base.Stabilimento stab = new org.aspcfs.modules.gestioneanagrafica.base.Stabilimento (db, altId, true);
				context.getRequest().setAttribute("StabilimentoAnagraficaDettaglio", stab);
				context.getRequest().setAttribute("OperatoreAnagrafica", stab.getImpresa());
			}	
		 else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU){ 
				org.aspcfs.modules.opu.base.Stabilimento stab = new org.aspcfs.modules.opu.base.Stabilimento (db, altId, true);
				context.getRequest().setAttribute("StabilimentoDettaglio", stab);
				context.getRequest().setAttribute("Operatore", stab.getOperatore());
			}		

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
		context.getRequest().setAttribute("folderId", String.valueOf(folderId));
		context.getRequest().setAttribute("parentId", String.valueOf(parentId));
		context.getRequest().setAttribute("uploadOk", uploadOk);
		context.getRequest().setAttribute("op", op);

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
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(stabId);
			
			if (stabId<=0){
				requestParams.append("&");
				requestParams.append("altId");
				requestParams.append("=").append(altId);
			}
			
			requestParams.append("&");
			requestParams.append("ticketId");
			requestParams.append("=").append(ticketId);
			requestParams.append("&");
			requestParams.append("folderId");
			requestParams.append("=").append(folderId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
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

		String op = "";
		op = context.getRequest().getParameter("op");
		context.getRequest().setAttribute("op", op);

		setOrgId(context.getRequest().getParameter("orgId"));
		setStabId(context.getRequest().getParameter("stabId"));
		setAltId(context.getRequest().getParameter("altId"));
		setTicketId(context.getRequest().getParameter("ticketId"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
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
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(orgId);
			requestParams.append("&");
			requestParams.append("stabId");
			requestParams.append("=").append(stabId);
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("ticketId");
			requestParams.append("=").append(ticketId);
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
		String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");

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

		if (nomeDocumento != null && !nomeDocumento.equals("null"))
			titolo = codDocumento + "_" + nomeDocumento;

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
					System.out.println("[DOCUMENTALE GISA] Sessione invalidata");
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

	public String executeCommandGestisciCartella(ActionContext context) throws SQLException, IOException {

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		String op = "";
		op = context.getRequest().getParameter("op");
		context.getRequest().setAttribute("op", op);

		// Completare
		setOrgId(context.getRequest().getParameter("orgId"));
		setStabId(context.getRequest().getParameter("stabId"));
		setAltId(context.getRequest().getParameter("altId"));

		setTicketId(context.getRequest().getParameter("ticketId"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		setGrandparentId(context.getRequest().getParameter("grandparentId"));
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		context.getRequest().setAttribute("altId", String.valueOf(altId));

		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
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
			System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
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
				requestParams.append("orgId");
				requestParams.append("=").append(orgId);
				requestParams.append("&");
				requestParams.append("stabId");
				requestParams.append("=").append(stabId);
				requestParams.append("&");
				requestParams.append("altId");
				requestParams.append("=").append(altId);
				requestParams.append("&");
				requestParams.append("ticketId");
				requestParams.append("=").append(ticketId);
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
				System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
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
			System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
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
				requestParams.append("orgId");
				requestParams.append("=").append(orgId);
				requestParams.append("&");
				requestParams.append("stabId");
				requestParams.append("=").append(stabId);
				requestParams.append("&");
				requestParams.append("altId");
				requestParams.append("=").append(altId);
				requestParams.append("&");
				requestParams.append("ticketId");
				requestParams.append("=").append(ticketId);
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
				System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
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

		String op = "";
		op = context.getRequest().getParameter("op");
		context.getRequest().setAttribute("op", op);

		// Completare
		setOrgId(context.getRequest().getParameter("orgId"));
		setStabId(context.getRequest().getParameter("stabId"));
		setAltId(context.getRequest().getParameter("altId"));
		setTicketId(context.getRequest().getParameter("ticketId"));
		setFolderId(context.getRequest().getParameter("folderId"));
		setParentId(context.getRequest().getParameter("parentId"));
		setGrandparentId(context.getRequest().getParameter("grandparentId"));
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		context.getRequest().setAttribute("altId", String.valueOf(altId));

		context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));
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
			System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
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
				requestParams.append("orgId");
				requestParams.append("=").append(orgId);
				requestParams.append("&");
				requestParams.append("stabId");
				requestParams.append("=").append(stabId);
				requestParams.append("&");
				requestParams.append("altId");
				requestParams.append("=").append(altId);
				requestParams.append("&");
				requestParams.append("ticketId");
				requestParams.append("=").append(ticketId);
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
				System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
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

	public String executeCommandDownloadByTipo(ActionContext context) throws SQLException, IOException {

		// recupero l'id timbro

		String tipoAllegato = context.getRequest().getParameter("tipoAllegato");
		String idNodo = null;
		idNodo = context.getRequest().getParameter("idNodo");
		if (idNodo == null)
			idNodo = (String) context.getRequest().getAttribute("idNodo");

		String orgId = null;
		orgId = context.getRequest().getParameter("orgId");
		if (orgId == null)
			orgId = (String) context.getRequest().getAttribute("orgId");

		String id = null;
		id = context.getRequest().getParameter("id");
		if (id == null)
			id = (String) context.getRequest().getAttribute("id");

		String nomeDocumento = null;
		nomeDocumento = context.getRequest().getParameter("nomeDocumento");
		if (nomeDocumento == null)
			nomeDocumento = (String) context.getRequest().getAttribute("nomeDocumento");

		String titolo = "";
		String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");

		String download_url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")
				+ "/" + ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE_TIPO");

		download_url += "?orgId=" + orgId + "&ticketId=" + id + "&idNodo=" + idNodo + "&provenienza=" + provenienza
				+ "&tipoAllegato=" + tipoAllegato;
		;
		titolo = tipoAllegato + "_" + idNodo;

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
				out.write(buffer, 0, length);
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

	public String executeCommandPrepareUploadLista(ActionContext context) throws SQLException, IOException {
		String orgId = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgId);
		String messaggioPost = (String) context.getRequest().getAttribute("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null"))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);
		String tipo = (String) context.getRequest().getAttribute("tipo");
		if (tipo != null && !tipo.equals("null"))
			context.getRequest().setAttribute("tipo", tipo);

		return "prepareUploadListaOk";
	}

	public String executeCommandAllegaLista(ActionContext context) throws IOException, SQLException,
			IllegalStateException, ServletException, FileUploadException, ParseException {

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
		String op = "";
		String filePath = this.getPath(context, "accounts");
		HttpMultiPartParser multiPart = new HttpMultiPartParser();
		HashMap parts = multiPart.parseData(context.getRequest(), filePath);
		String id = (String) parts.get("id");
		String tId = (String) parts.get("ticketId");
		String subject = (String) parts.get("subject");
		String folderId = (String) parts.get("folderId");
		String parentId = (String) parts.get("parentId");
		String grandparentId = (String) parts.get("grandparentId");
		op = (String) parts.get("op");
		String tipoAllegato = (String) parts.get("tipoAllegato");
		actionOrigine = (String) parts.get("actionOrigine");
		String idN = (String) parts.get("idNodo");

		setParentId(parentId);
		setGrandparentId(grandparentId);
		setFolderId(folderId);
		setOrgId(id);
		setTicketId(tId);
		setOggetto(subject);
		setIdNodo(idN);
		setTipoAllegato(tipoAllegato);

		context.getRequest().setAttribute("op", op);
		context.getRequest().setAttribute("folderId", String.valueOf(getFolderId()));
		context.getRequest().setAttribute("parentId", String.valueOf(getParentId()));
		context.getRequest().setAttribute("grandparentId", String.valueOf(getGrandparentId()));
		context.getRequest().setAttribute("orgId", String.valueOf(getOrgId()));
		context.getRequest().setAttribute("ticketId", String.valueOf(getTicketId()));

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
			context.getRequest().setAttribute("tipo", tipoAllegato);
			return executeCommandPrepareUploadLista(context);
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
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
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

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
			conn.getContentLength();

			String messaggioPost = "OK! Caricamento completato con successo.";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);

			String codDocumento = "", titolo = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();

			// Leggo l'output: l'header del documento generato e il nome
			// assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto);
			}
			in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
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

		if (tipoAllegato.equals("ListaDistribuzione"))
			return "allegaListaOk";
		else if (tipoAllegato.equals("VerbaleSupervisione"))
			return "allegaVerbaleOk";
		else
			return "-null-";

	}

	public void aggiornaTicketId(int ticketId, String headerAllegato) throws SQLException, IOException {

		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_GESTIONE_ALLEGATI");
		// STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
		URL obj;

		try {
			obj = new URL(url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("ticketId");
			requestParams.append("=").append(ticketId);
			requestParams.append("&");
			requestParams.append("idHeader");
			requestParams.append("=").append(headerAllegato);
			requestParams.append("&");
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("operazione");
			requestParams.append("=").append("AggiornaTicketId");

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



	public String executeCommandListaAllegatiDpat(ActionContext context) throws SQLException, IOException {

		if (!hasPermission(context, "server_documentale-dpat-view")) {
			return ("PermissionError");
		}

		String messaggioPost = "";

		messaggioPost = context.getRequest().getParameter("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null") && !messaggioPost.equals(""))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		String op = "";
		String idStruttura = "";
		String anno = "";
		String tipoCertificato = "";

		String uploadOk = (String) context.getRequest().getAttribute("uploadOk");
		op = (String) context.getRequest().getAttribute("op");
		idStruttura = (String) context.getRequest().getAttribute("idStruttura");
		anno = (String) context.getRequest().getAttribute("anno");

		if (op == null)
			op = context.getRequest().getParameter("op");

		if (idStruttura == null)
			idStruttura = context.getRequest().getParameter("combo_area");

		if (anno == null)
			anno = context.getRequest().getParameter("anno");

		if (context.getRequest().getParameter("tipoAllegato") != null)
			tipoCertificato = context.getRequest().getParameter("tipoAllegato");

		context.getRequest().setAttribute("idStruttura", idStruttura);
		context.getRequest().setAttribute("anno", anno);
		context.getRequest().setAttribute("uploadOk", uploadOk);
		context.getRequest().setAttribute("op", op);

		Connection db = null;
		try {
			db = this.getConnection(context);
			OiaNodo struttura = new OiaNodo(db, Integer.parseInt(idStruttura));
			context.getRequest().setAttribute("StrutturaAmbito", struttura);
		} catch (SQLException e) {

		} finally {
			this.freeConnection(context, db);
		}
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
			requestParams.append("dpatidStruttura");
			requestParams.append("=").append(idStruttura);
			requestParams.append("&");
			requestParams.append("dpatAnno");
			requestParams.append("=").append(anno);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoCertificato);

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

		return "listaAllegatiDpatOk";
	}

	public String executeCommandGestisciFileDpat(ActionContext context) throws SQLException, IOException {

		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties
				.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) {
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		context.getRequest().setAttribute("idStruttura", context.getParameter("combo_area"));
		context.getRequest().setAttribute("anno", context.getParameter("anno"));

		String op = "";
		op = context.getRequest().getParameter("op");
		context.getRequest().setAttribute("op", op);

		// Completare

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
			System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): " + url.toString());
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
				requestParams.append("idStruttura");
				requestParams.append("=").append(context.getParameter("idStruttura"));
				requestParams.append("&");
				requestParams.append("anno");
				requestParams.append("=").append(context.getParameter("anno"));

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
				System.out.println("[DOCUMENTALE GISA] Conn: " + conn.toString());
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
		return executeCommandListaAllegatiDpat(context);
	}

	public String executeCommandAllegaFileDpat(ActionContext context) throws IOException, SQLException,
			IllegalStateException, ServletException, FileUploadException {

		if (!hasPermission(context, "server_documentale-dpat-add")) {
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

		// setOggetto(context.getParameter("subject"));
		String op = "";
		//String filePath = this.getPath(context, "allegati");
		String filePath = getWebInfPath(context,"tmp_allegati");
		HttpMultiPartParser multiPart = new HttpMultiPartParser();

		HashMap parts = multiPart.parseData(context.getRequest(), filePath);
		String idStruttura = (String) parts.get("idStruttura");
		String anno = (String) parts.get("anno");

		String subject = (String) parts.get("subject");

		op = (String) parts.get("op");
		String tipoAllegato = (String) parts.get("tipoAllegato");
		actionOrigine = (String) parts.get("actionOrigine");

		context.getRequest().setAttribute("op", op);
		context.getRequest().setAttribute("idStruttura", String.valueOf(idStruttura));
		context.getRequest().setAttribute("anno", String.valueOf(anno));
		context.getRequest().setAttribute("tipoAllegato", String.valueOf(tipoAllegato));

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

			return executeCommandListaAllegatiDpat(context);
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

		/**
		 * INIZIO METODO CHIAMA SERVER DOCUMENTALE
		 */

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
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoAllegato);
			requestParams.append("&");
			requestParams.append("dpatidStruttura");
			requestParams.append("=").append(idStruttura);
			requestParams.append("&");
			requestParams.append("dpatAnno");
			requestParams.append("=").append(anno);
			requestParams.append("&");
			requestParams.append("oggetto");
			requestParams.append("=").append(tipoAllegato + "_" + subject + "_" + anno);
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
		return executeCommandListaAllegatiDpat(context);

	}

	public DocumentaleAllegatoList getListaAllegatiDpat(ActionContext context, Connection db) {

		String messaggioPost = "";
		DocumentaleAllegatoList docList = new DocumentaleAllegatoList();
		messaggioPost = context.getRequest().getParameter("messaggioPost");
		if (messaggioPost != null && !messaggioPost.equals("null") && !messaggioPost.equals(""))
			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		String op = "";
		String idStruttura = "";
		String anno = "";
		String tipoCertificato = "";

		String uploadOk = (String) context.getRequest().getAttribute("uploadOk");
		op = (String) context.getRequest().getAttribute("op");
		idStruttura = (String) context.getRequest().getAttribute("idStruttura");
		anno = (String) context.getRequest().getAttribute("anno");

		if (op == null)
			op = context.getRequest().getParameter("op");

		if (idStruttura == null)
			idStruttura = context.getRequest().getParameter("combo_area");

		if (anno == null)
			anno = context.getRequest().getParameter("anno");

		if (context.getRequest().getParameter("tipoAllegato") != null)
			tipoCertificato = context.getRequest().getParameter("tipoAllegato");

		context.getRequest().setAttribute("idStruttura", idStruttura);
		context.getRequest().setAttribute("anno", anno);
		context.getRequest().setAttribute("uploadOk", uploadOk);
		context.getRequest().setAttribute("op", op);

		try {

			OiaNodo struttura = new OiaNodo(db, Integer.parseInt(idStruttura));
			context.getRequest().setAttribute("StrutturaAmbito", struttura);
		} catch (SQLException e) {

		}

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
			requestParams.append("dpatidStruttura");
			requestParams.append("=").append(idStruttura);
			requestParams.append("&");
			requestParams.append("dpatAnno");
			requestParams.append("=").append(anno);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoAllegato);

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

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}

		return docList;
	}

	public static DocumentaleAllegatoList getListaAllegatiScia(int altId) throws SQLException, IOException {

		DocumentaleAllegatoList docList = new DocumentaleAllegatoList();

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
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}

		return docList;
	}
	
	public String executeCommandPrepareAllegaChecklistMacelli(ActionContext context) throws IOException, SQLException,
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

String ticketId = context.getRequest().getParameter("ticketId");
String altId = context.getRequest().getParameter("altId");
String stabId = context.getRequest().getParameter("stabId");

context.getRequest().setAttribute("stabId", String.valueOf(stabId));
context.getRequest().setAttribute("altId", String.valueOf(altId));
context.getRequest().setAttribute("ticketId", String.valueOf(ticketId));

return "allegaChecklistMacelliOK";

}

}
