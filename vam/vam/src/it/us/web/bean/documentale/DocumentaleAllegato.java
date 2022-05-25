/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.documentale;



public class DocumentaleAllegato  {
	
	private static final long serialVersionUID = 4320567602597719160L;
	
	private int idAutopsia = -1; 
	private int idAccettazione = -1;
	private int idIstopatologico = -1;
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
	private boolean isFolder = false;
	private String nomeClient = null;
	private String estensione = null;

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

	public String getEstensione() {
		return estensione;
	}
	public void setEstensione(String estensione) {
		this.estensione = estensione;
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
		this.setEstensione(split[8]);
		this.setNomeClient(split[9]);
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
