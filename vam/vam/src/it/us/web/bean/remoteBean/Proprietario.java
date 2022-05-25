/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public abstract class Proprietario implements Serializable
{
	private int id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String documentoIdentita;
	private String aslString;
	private String citta;
	private String provincia;
	private String nazione;
	private String tipo;
	private String via;
	private Integer tipologiaIndirizzo;
	private String cap;
	private String numeroTelefono;
	private Integer tipologiaTelefono;
	private Date   trashedDate;
	private String errorDescription;
	private int errorCode;

	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	
	public Integer getTipologiaTelefono() {
		return tipologiaTelefono;
	}
	public void setTipologiaTelefono(Integer tipologiaTelefono) {
		this.tipologiaTelefono = tipologiaTelefono;
	}
	
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public Integer getTipologiaIndirizzo() {
		return tipologiaIndirizzo;
	}
	public void setTipologiaIndirizzo(Integer tipologiaIndirizzo) {
		this.tipologiaIndirizzo = tipologiaIndirizzo;
	}

	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getAslString() {
		return aslString;
	}
	public void setAslString(String aslString) {
		this.aslString = aslString;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getDocumentoIdentita() {
		return documentoIdentita;
	}
	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}
	
	public Date getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
