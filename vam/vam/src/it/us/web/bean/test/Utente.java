/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "utenteeee")
@Where( clause = "trashed_date is null" )
public class Utente implements Serializable
{
	private static final long serialVersionUID = -8565252002872673022L;
	
	private int			id;
	private String		username;
	private String		nome;
	private String		cognome;
    private Timestamp	data_inizio_validita;
    private Timestamp	data_fine_validita;
    private String		email;
    private String		telefono;
    private Timestamp	entered;
    private int			entered_by;
    private Timestamp	modified;
    private int			modified_by;
    private Timestamp	deleted;
    private String		domanda;
    private String		risposta;
	private Timestamp	trashed_date;
    
    private Set<Password> passwords = new HashSet<Password>(0);
    
    protected Timestamp getTrashed_date() {
		return trashed_date;
	}
	protected void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utente")
	@Where( clause = "trashed_date is null" )
	public Set<Password> getPasswords() {
		return passwords;
	}
    public void setPasswords(Set<Password> passwords) {
		this.passwords = passwords;
	}
    
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Timestamp getData_inizio_validita() {
		return data_inizio_validita;
	}
	public void setData_inizio_validita(Timestamp data_inizio_validita) {
		this.data_inizio_validita = data_inizio_validita;
	}
	public Timestamp getData_fine_validita() {
		return data_fine_validita;
	}
	public void setData_fine_validita(Timestamp data_fine_validita) {
		this.data_fine_validita = data_fine_validita;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public int getEntered_by() {
		return entered_by;
	}
	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Timestamp getDeleted() {
		return deleted;
	}
	public void setDeleted(Timestamp deleted) {
		this.deleted = deleted;
	}
	public String getDomanda() {
		return domanda;
	}
	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
    
}
