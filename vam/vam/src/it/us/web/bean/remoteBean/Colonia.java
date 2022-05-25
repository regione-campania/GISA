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

import org.hibernate.annotations.Where;

@Entity
@Table(name = "sync_colonia", schema = "public")
@Where(clause = "trashed_date is null")
public class Colonia implements Serializable
{
	private static final long serialVersionUID = -8622138631789926823L;
	
	private int id;
	private String cittaColonia;
	private String provinciaColonia;
	private String nazione;
	private String indirizzoColonia;
	private String veterinario;
	private String cap;
	private String nomeReferente;
	private String cognomeReferente;
	private String telefonoReferente;
	private String codiceFiscaleReferente;
	private String numeroProtocollo;
	private Date dataCensimentoNumGatti;
	private String documentoIdentita;
	private Date dataRegistrazioneColonia;
	private Date trashedDate;
	private Integer asl;
	private Integer numeroGatti;
	private String errorDescription;
	private int errorCode;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="citta_colonia")
	public String getCittaColonia() {
		return cittaColonia;
	}
	public void setCittaColonia(String cittaColonia) {
		this.cittaColonia = cittaColonia;
	}
	
	@Column(name="provincia_colonia")
	public String getProvinciaColonia() {
		return provinciaColonia;
	}
	public void setProvinciaColonia(String provinciaColonia) {
		this.provinciaColonia = provinciaColonia;
	}
	
	@Column(name="nazione")
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	
	@Column(name="via_colonia")
	public String getIndirizzoColonia() {
		return indirizzoColonia;
	}
	public void setIndirizzoColonia(String indirizzoColonia) {
		this.indirizzoColonia = indirizzoColonia;
	}
	
	@Column(name="cap")
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	@Column(name = "nome_veterinario")
	public String getVeterinario() {
		return veterinario;
	}
	public void setVeterinario(String veterinario) {
		this.veterinario = veterinario;
	}
	
	@Column(name = "nome_referente")
	public String getNomeReferente() {
		return nomeReferente;
	}
	public void setNomeReferente(String nomeReferente) {
		this.nomeReferente = nomeReferente;
	}
	
	@Column(name = "cognome_referente")
	public String getCognomeReferente() {
		return cognomeReferente;
	}
	public void setCognomeReferente(String cognomeReferente) {
		this.cognomeReferente = cognomeReferente;
	}
	
	@Column(name = "documentoidentita")
	public String getDocumentoIdentita() {
		return documentoIdentita;
	}
	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}
	
	@Column(name = "numerotelefono_referente")
	public String getTelefonoReferente() {
		return telefonoReferente;
	}
	public void setTelefonoReferente(String telefonoReferente) {
		this.telefonoReferente = telefonoReferente;
	}
	
	@Column(name = "codicefiscale_referente")
	public String getCodiceFiscaleReferente() {
		return codiceFiscaleReferente;
	}
	public void setCodiceFiscaleReferente(String codiceFiscaleReferente) {
		this.codiceFiscaleReferente = codiceFiscaleReferente;
	}
	
	@Column(name = "data_censimento_gatti")
	public Date getDataCensimentoNumGatti() {
		return dataCensimentoNumGatti;
	}
	public void setDataCensimentoNumGatti(Date dataCensimentoNumGatti) {
		this.dataCensimentoNumGatti = dataCensimentoNumGatti;
	}
	
	@Column(name = "data_registrazione_colonia")
	public Date getDataRegistrazioneColonia() {
		return dataRegistrazioneColonia;
	}
	public void setDataRegistrazioneColonia(Date dataRegistrazioneColonia) {
		this.dataRegistrazioneColonia = dataRegistrazioneColonia;
	}
	
	@Column(name = "numero_protocollo")
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	
	@Transient
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Transient
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Column(name = "trasheddate")
	public Date getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}
	
	@Column(name = "id_asl")
	public Integer getAsl() {
		return this.asl;
	}

	public void setAsl(Integer asl) {
		this.asl = asl;
	}
	
	@Column(name = "numero_gatti")
	public Integer getNumeroGatti() {
		return this.numeroGatti;
	}

	public void setNumeroGatti(Integer numeroGatti) {
		this.numeroGatti = numeroGatti;
	}
	
	@Transient
	public String getNominativoReferente() {
		return ((getCognomeReferente()==null)?(""):(getCognomeReferente())) + " " + ((getNomeReferente()==null)?(""):(getNomeReferente()));
	}
	
}
