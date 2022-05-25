/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.base;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DocumentaleAllegato  extends GenericBean {
	
	private static final long serialVersionUID = 4320567602597719160L;
	
	private int orgId = -1; 
	private int ticketId = -1; 
	private String oggetto = null; 
	private String filename = null; 
	private String fileDimension = null; 
	private int folderId = -1; 
	private int parentId = -1; 
	private int grandparentId = -1; 
	private int idNodo = -1;
	private String tipoAllegato = null;
	private int userId = -1;
	private String userIp = null;
	private String idHeader = null;
	private int idDocumento = -1;
	private String dataCreazione = null;
	private String nomeDocumento = null;
	private boolean isFolder = false;
	private String nomeClient = null;
	private int contaFile = 0;
	private String estensione = null;
	private int idCartella = -1;
	
	private Boolean P7MValid = null;
	
	
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public void setFolder(String isFolder) {
		if (isFolder!=null && !isFolder.equals("") && !isFolder.equals("f"))
			this.isFolder = true;
	}
	public String getNomeClient() {
		return nomeClient;
	}
	public void setNomeClient(String nomeClient) {
		this.nomeClient = nomeClient;
	}
	public int getContaFile() {
		return contaFile;
	}
	public void setContaFile(int contaFile) {
		this.contaFile = contaFile;
	}
	public void setContaFile(String contaFile) {
		if (contaFile!=null && !contaFile.equals("null") && !contaFile.equals(""))
		this.contaFile = Integer.parseInt(contaFile);
	}
	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public void setOrgId(String orgId) {
		if (orgId!=null && !orgId.equals("null") && !orgId.equals(""))
			this.orgId = Integer.parseInt(orgId);
	}
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public void setTicketId(String ticketId) {
		if (ticketId!=null && !ticketId.equals("null") && !ticketId.equals(""))
			this.ticketId = Integer.parseInt(ticketId);
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserId(String userId) {
		if (userId!=null && !userId.equals("null") && !userId.equals(""))
			this.userId = Integer.parseInt(userId);
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getIdHeader() {
		return idHeader;
	}
	public void setIdHeader(String idHeader) {
		this.idHeader = idHeader;
	}
	public int getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		if (idDocumento!=null && !idDocumento.equals("null") && !idDocumento.equals(""))
			this.idDocumento = Integer.parseInt(idDocumento);
	}
	public int getIdCartella() {
		return idCartella;
	}
	public void setIdCartella(int idCartella) {
		this.idCartella = idCartella;
	}
	public void setIdCartella(String idCartella) {
		if (idCartella!=null && !idCartella.equals("null") && !idCartella.equals(""))
			this.idCartella = Integer.parseInt(idCartella);
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
public DocumentaleAllegato() {
		
		}
	
	public DocumentaleAllegato(String riga) {
		
		String[] split;
		split = riga.split(";;");
		
		this.setDataCreazione(split[0]);
		this.setNomeDocumento(split[1]);
		this.setUserId(split[2]);
		this.setIdHeader(split[3]);
		this.setIdDocumento(split[4]);
		this.setOggetto(split[5]);
		this.setFolder(split[6]);
		this.setIdCartella(split[7]);
		this.setEstensione(split[8]);
		this.setNomeClient(split[9]);
		this.setContaFile(split[10]);
		this.setTipoAllegato(split[11]);
		this.setP7MValid(split[12]);
		
		}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}
	public Boolean getP7MValid() {
		return P7MValid;
	}
	public void setP7MValid(Boolean p7mValid) {
		P7MValid = p7mValid;
	}
	public void setP7MValid(String p7mValid) {
		if (p7mValid!=null && p7mValid.equals("true"))
			P7MValid = true;
		else if (p7mValid!=null && p7mValid.equals("false"))
			P7MValid = false;
	}

}
