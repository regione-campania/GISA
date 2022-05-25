/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import it.us.web.permessi.Permessi;

import java.util.Date;


import javax.validation.constraints.NotNull;



public class BUtente implements java.io.Serializable, Comparable<BUtente>
{
	private static final long serialVersionUID = -3763688846976643462L;
	
	private int id;
	private String cap;
	private String codiceFiscale;
	private String cognome;
	private String comune;
	private String comuneNascita;
	private Date dataNascita;
	private String domandaSegreta;
	private String email;
	private String fax;
	private String indirizzo;
	private String nome;
	private String password;
	private String provincia;
	private String rispostaSegreta;
	private String ruolo;
	private String stato;
	private String telefono1;
	private String telefono2;
	private String username;
	private Date entered;
	private Integer enteredBy;
	private Date modified;
	private Integer modifiedBy;
	private Date trashedDate;
	
	private boolean enabled;
	private Date enabledDate;
	private Date dataScadenza;
	
	private String note;
	
	private boolean SuperAdmin = false;
    private boolean newPassword;

	
	
	public BUtente()
	{
		
	}

	@Override
	public String toString()
	{
		return (cognome == null ? "" : cognome) + " " + (nome == null ? "" : nome);
	}

	
	public String getNominativo()
	{
		return (cognome == null ? "" : cognome) + " " + (nome == null ? "" : nome);
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	
	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDomandaSegreta() {
		return this.domandaSegreta;
	}

	public void setDomandaSegreta(String domandaSegreta) {
		this.domandaSegreta = domandaSegreta;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRispostaSegreta() {
		return this.rispostaSegreta;
	}

	public void setRispostaSegreta(String rispostaSegreta) {
		this.rispostaSegreta = rispostaSegreta;
	}

	public String getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	

	@Override
	public int compareTo(BUtente o)
	{
		return this.toString().compareTo( o.toString() );
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Date getEnabledDate() {
		return this.enabledDate;
	}

	public void setEnabledDate(Date enabledDate) {
		this.enabledDate = enabledDate;
	}
	
	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public boolean isSuperAdmin() {
		return SuperAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		SuperAdmin = superAdmin;
	}

	public boolean isNewPassword() {
		return newPassword;
	}

	public void setNewPassword(boolean newPassword) {
		this.newPassword = newPassword;
	}

}
