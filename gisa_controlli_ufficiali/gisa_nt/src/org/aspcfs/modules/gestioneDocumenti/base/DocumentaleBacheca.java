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

public class DocumentaleBacheca  extends GenericBean {
	
	private static final long serialVersionUID = 4320567602597719160L;
	
	private int storeId = -1; 
	private String filename = null; 
	private String fileDimension = null; 
	private String fileDescrizione = null;
	private String fileTitolo = null;
	private String oggetto = null;
	private int folderId = -1; 
	private int parentId = -1; 
	private int grandparentId = -1; 
	private String idHeader = null;
	private int idDocumento = -1;
	private String dataCreazione = null;
	private String nomeDocumento = null;
	private boolean isFolder = false;
	private String nomeClient = null;
	private int contaFile = 0;
	private String estensione = null;
	private int idCartella = -1;
	private String dataInizio= null;
	private String dataApprovazione= null;
	private String dataModifica= null;
	private String dataArchiviazione= null;
	private int utenteInserimento=-1;
	private int utenteModifica=-1;
	private int utenteCancellazione=-1;
	private boolean archivioApprovato = false;
	private boolean archiviatoArchivio = false;
	private boolean elencoAsl = false;
	private String tipoBacheca=null;
	private int priorita = -1;
	
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
	
	public String getTipoBacheca() {
		return tipoBacheca;
	}

	public void setTipoBacheca(String tipoBacheca) {
		this.tipoBacheca = tipoBacheca;
	}
	
	public String getFileTitolo() {
		return fileTitolo;
	}

	public void setFileTitolo(String fileTitolo) {
		this.fileTitolo = fileTitolo;
	}

	public String getFileName() {
		return filename;
	}

	public void setFileName(String fileName) {
		this.filename = fileName;
	}

	public String getFileEstensione() {
		return estensione;
	}

	public void setFileEstensione(String fileEstensione) {
		this.estensione = fileEstensione;
	}

	public String getFileDimensione() {
		return fileDimension;
	}

	public void setFileDimensione(String fileDimensione) {
		this.fileDimension = fileDimensione;
	}

	public String getFileDescrizione() {
		return fileDescrizione;
	}

	public void setFileDescrizione(String fileDescrizione) {
		this.fileDescrizione = fileDescrizione;
	}
	
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public void setUtenteInserimento(String utenteInserimento) {
		if (utenteInserimento!= null && !utenteInserimento.equals("null") && !utenteInserimento.equals(""))
		this.utenteInserimento = Integer.parseInt(utenteInserimento);
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
public DocumentaleBacheca() {
		
		}
	
	public DocumentaleBacheca(String riga, int tipo) {
		
		String[] split;
		split = riga.split(";;");
		
		if (tipo==-1) { //ARCHIVIO
	
		this.setFileTitolo(split[0]);
		this.setFileDescrizione(split[1]);
		this.setDataInizio(split[2]);
		this.setDataApprovazione(split[3]);
		this.setUtenteInserimento(split[4]);
		this.setDataCreazione(split[5]);
		this.setDataModifica(split[6]);
		this.setDataArchiviazione(split[7]);
		this.setStoreId(split[8]);
		this.setContaFile(split[9]);
		this.setPriorita(split[10]);

			}
		else  { //ALLEGATO
			
			this.setDataCreazione(split[0]);
			this.setNomeDocumento(split[1]);
			this.setUtenteInserimento(split[2]);
			this.setIdHeader(split[3]);
			this.setIdDocumento(split[4]);
			this.setOggetto(split[5]);
			this.setFolder(split[6]);
			this.setIdCartella(split[7]);
			this.setEstensione(split[8]);
			this.setNomeClient(split[9]);
			this.setContaFile(split[10]);
			
		}
			
		  
		
		}

public DocumentaleBacheca(String riga, String tipo) {
		
		String[] split;
		split = riga.split(";;");
		
		if (tipo.equals("risultatiRicerca")) { 
			
			if (split[9].equals("archivio")){
				this.setFileTitolo(split[0]);
				this.setFileDescrizione(split[1]);
				this.setDataInizio(split[2]);
				this.setDataApprovazione(split[3]);
				this.setUtenteInserimento(split[4]);
				this.setDataCreazione(split[5]);
				this.setDataModifica(split[6]);
				this.setDataArchiviazione(split[7]);
				this.setStoreId(split[8]);
				this.setTipoBacheca(split[9]);
				this.setPriorita(split[10]);

			}
			else if (split[9].equals("documento")){
				this.setOggetto(split[0]);
				this.setIdHeader(split[1]);
				this.setEstensione(split[2]);
				this.setFileDimensione(split[3]);
				this.setUtenteInserimento(split[4]);
				this.setDataCreazione(split[5]);
				this.setFolder(split[6]);
				this.setNomeClient(split[7]);
				this.setIdDocumento(split[8]);
				this.setTipoBacheca(split[9]);
				
				
			}
	
	
			
		}
		else if (tipo.equals("comunicazioniInterne")){
			this.setDataCreazione(split[0]);
			this.setNomeDocumento(split[1]);
			this.setUtenteInserimento(split[2]);
			this.setIdHeader(split[3]);
			this.setIdDocumento(split[4]);
			this.setOggetto(split[5]);
			this.setFolder(split[6]);
			this.setIdCartella(split[7]);
			this.setEstensione(split[8]);
			this.setNomeClient(split[9]);
		
		}
		  
		
		}

	public String getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getDataArchiviazione() {
		return dataArchiviazione;
	}

	public void setDataArchiviazione(String dataArchiviazione) {
		this.dataArchiviazione = dataArchiviazione;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public int getPriorita() {
		return priorita;
	}

	public void setPriorita(int priorita) {
		this.priorita = priorita;
	}
	public void setPriorita(String priorita) {
		if (priorita!= null && !priorita.equals("null") && !priorita.equals(""))
		this.priorita = Integer.parseInt(priorita);
	}

}
