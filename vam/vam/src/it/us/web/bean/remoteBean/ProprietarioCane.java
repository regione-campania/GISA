/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import it.us.web.bean.vam.lookup.LookupAsl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "sync_proprietario_cane", schema = "public")
@Where( clause = "trashed_date is null" )
public class ProprietarioCane extends Proprietario implements Serializable
{
	private static final long serialVersionUID = 8921544993066724951L;
	
	private int id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String documentoIdentita;
	private String citta;
	private String provincia;
	private String nazione;
	private String via;
	private String cap;
	private String tipo;
	private String numeroTelefono;
	private Date   trashedDate;
	private Integer asl;
	private String errorDescription;
	private int errorCode;

	@Column(name="numerotelefono")
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	
	@Column(name="provincia")
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	@Column(name="citta")
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	@Column(name="nazione")
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	@Column(name="via")
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}

	@Column(name="cap")
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	@Column(name="tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
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
	
	@Column(name = "codicefiscale")	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	@Column(name = "nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cognome")
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Column(name = "documentoidentita")
	public String getDocumentoIdentita() {
		return documentoIdentita;
	}
	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}
	
	@Column(name = "trasheddate")
	public Date getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
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
	
	@Column(name = "id_asl")
	public Integer getAsl() {
		return this.asl;
	}

	public void setAsl(Integer asl) {
		this.asl = asl;
	}
	
}
