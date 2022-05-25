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

public class DocumentaleAllegatoRiunione  extends GenericBean {
	
	private static final long serialVersionUID = 4320567602597719160L;
	
	private int altId = -1; 
	
	private String oggetto = null; 
	private String filename = null; 
	private String fileDimension = null; 

	private String tipoAllegato = null;
	private int userId = -1;
	private String userIp = null;
	private String idHeader = null;
	private int idDocumento = -1;
	private String dataCreazione = null;
	private String nomeDocumento = null;
	private boolean principale = false;
	private boolean revisione = false;
	private String nomeClient = null;

	private String estensione = null;

	private int numeroRevisione = -1;
	private String headerRevisionato = null;
	
	public boolean isPrincipale() {
		return principale;
	}
	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	public void setPrincipale(String principale) {
		if (principale!=null && !principale.equals("") && !principale.equals("f"))
			this.principale = true;
	}
	
	public boolean isRevisione() {
		return revisione;
	}
	public void setRevisione(boolean revisione) {
		this.revisione = revisione;
	}
	public void setRevisione(String revisione) {
		if (revisione!=null && !revisione.equals("") && !revisione.equals("f"))
			this.revisione = true;
	}
	
	public String getNomeClient() {
		return nomeClient;
	}
	public void setNomeClient(String nomeClient) {
		this.nomeClient = nomeClient;
	}
	
	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}
	public int getAltId() {
		return altId;
	}
	public void setAltId(int altId) {
		this.altId = altId;
	}
	public void setAltId(String altId) {
		if (altId!=null && !altId.equals("null") && !altId.equals(""))
			this.altId = Integer.parseInt(altId);
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
public DocumentaleAllegatoRiunione() {
		
		}
	
	public DocumentaleAllegatoRiunione(String riga) {
		
		String[] split;
		split = riga.split(";;");
		
		this.setDataCreazione(split[0]);
		this.setNomeDocumento(split[1]);
		this.setUserId(split[2]);
		this.setIdHeader(split[3]);
		this.setIdDocumento(split[4]);
		this.setOggetto(split[5]);
		this.setPrincipale(split[6]);
		this.setEstensione(split[7]);
		this.setNomeClient(split[8]);
		this.setRevisione(split[9]);
		this.setNumeroRevisione(split[10]);
		this.setHeaderRevisionato(split[11]);
	
		  
		
		}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}
	public String getHeaderRevisionato() {
		return headerRevisionato;
	}
	public void setHeaderRevisionato(String headerRevisionato) {
		this.headerRevisionato = headerRevisionato;
	}
	public int getNumeroRevisione() {
		return numeroRevisione;
	}
	public void setNumeroRevisione(int numeroRevisione) {
		this.numeroRevisione = numeroRevisione;
	}
	public void setNumeroRevisione(String numeroRevisione) {
		if (numeroRevisione!=null && !numeroRevisione.equals("") && !numeroRevisione.equals("null"))
		this.numeroRevisione = Integer.parseInt(numeroRevisione);
	}

}
