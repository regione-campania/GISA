/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedNativeQuery;

@Entity

public class Documenti implements Serializable
{
	private static final long serialVersionUID = -7289087085008520718L;
	
	private int id;
	private Integer specie;
	private Integer animale;
	private String microchip;
	private String tipo;
	private Integer version;
	private String nomeDocumento;
	private String path;
	private Date dataCreazione;
	private String utenteCreazione;
	private Integer utenteCreazioneId;
	private String utenteCreazioneIp;
	private String pathServer;
	private String md5;
	private Integer letto;
	private Date dataUltimaLettura;
	private Integer cc;
	
	
	@Id
	@Column(name = "id")
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "specie")
	public Integer getSpecie() {
		return specie;
	}

	public void setSpecie(Integer specie) {
		this.specie = specie;
	}

	@Column(name = "animale")
	public Integer getAnimale() {
		return animale;
	}

	public void setAnimale(Integer animale) {
		this.animale = animale;
	}

	@Column(name = "microchip")
	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	@Column(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "nome_documento")
	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	@Column(name = "path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "data_creazione")
	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Column(name = "utente_creazione")
	public String getUtenteCreazione() {
		return utenteCreazione;
	}

	public void setUtenteCreazione(String utenteCreazione) {
		this.utenteCreazione = utenteCreazione;
	}

	@Column(name = "utente_creazione_id")
	public Integer getUtenteCreazioneId() {
		return utenteCreazioneId;
	}

	public void setUtenteCreazioneId(Integer utenteCreazioneId) {
		this.utenteCreazioneId = utenteCreazioneId;
	}

	@Column(name = "utente_creazione_ip")
	public String getUtenteCreazioneIp() {
		return utenteCreazioneIp;
	}

	public void setUtenteCreazioneIp(String utenteCreazioneIp) {
		this.utenteCreazioneIp = utenteCreazioneIp;
	}

	@Column(name = "path_server")
	public String getPathServer() {
		return pathServer;
	}

	public void setPathServer(String pathServer) {
		this.pathServer = pathServer;
	}

	@Column(name = "md5")
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "letto")
	public Integer getLetto() {
		return letto;
	}

	public void setLetto(Integer letto) {
		this.letto = letto;
	}

	@Column(name = "data_ultima_lettura")
	public Date getDataUltimaLettura() {
		return dataUltimaLettura;
	}

	public void setDataUltimaLettura(Date dataUltimaLettura) {
		this.dataUltimaLettura = dataUltimaLettura;
	}

	@Column(name = "cc")
	public Integer getCc() {
		return cc;
	}

	public void setCc(Integer cc) {
		this.cc = cc;
	}
	
	
	
	
}
