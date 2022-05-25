/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.OperazioneChirurgica;
import it.us.web.bean.vam.Rabbia;
import it.us.web.bean.vam.Sterilizzazione;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.dao.SuperUtenteDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.permessi.Permessi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "utenti_", schema = "public")
// @Where(clause = "trashed_date is null")
public class BUtenteAll implements java.io.Serializable, Comparable<BUtente> {
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
	private Clinica clinica;
	private boolean enabled;
	private Date enabledDate;
	private Date dataScadenza;
	private SuperUtenteAll superutente;
	private LookupAsl aslReferenza;
	private Set<Sterilizzazione> sterilizzaziones = new HashSet<Sterilizzazione>(0);
	private Set<OperazioneChirurgica> operazioneChirurgicas = new HashSet<OperazioneChirurgica>(0);

	/**
	 * GESTIONE GEOLOCALIZZAZIONE
	 */
	private Double accessPositionLat;
	private Double accessPositionLon;
	private String accessPositionErr;

	private String note;

	public BUtenteAll() {

	}

	@Transient
	public String getNomeEsame() {
		return "Utente";
	}

	@Override
	public String toString() {
		return (cognome == null ? "" : cognome) + " " + (nome == null ? "" : nome);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinica")
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.ALL)
	public Clinica getClinica() {
		return clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "cap")
	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	@Column(name = "codice_fiscale")
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@Column(name = "cognome")
	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Column(name = "comune")
	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	@Column(name = "comune_nascita")
	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nascita", length = 29)
	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Column(name = "domanda_segreta")
	public String getDomandaSegreta() {
		return this.domandaSegreta;
	}

	public void setDomandaSegreta(String domandaSegreta) {
		this.domandaSegreta = domandaSegreta;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "fax")
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "indirizzo")
	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Column(name = "nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "provincia")
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "risposta_segreta")
	public String getRispostaSegreta() {
		return this.rispostaSegreta;
	}

	public void setRispostaSegreta(String rispostaSegreta) {
		this.rispostaSegreta = rispostaSegreta;
	}

	@Column(name = "ruolo")
	public String getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	@Column(name = "stato")
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "telefono1")
	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	@Column(name = "telefono2")
	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Column(name = "entered_by")
	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utenti")
	public Set<Sterilizzazione> getSterilizzaziones() {
		return this.sterilizzaziones;
	}

	public void setSterilizzaziones(Set<Sterilizzazione> sterilizzaziones) {
		this.sterilizzaziones = sterilizzaziones;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "operatori_operazioni", schema = "public", joinColumns = { @JoinColumn(name = "operatore", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operazione", nullable = false, updatable = false) })
	public Set<OperazioneChirurgica> getOperazioneChirurgicas() {
		return this.operazioneChirurgicas;
	}

	public void setOperazioneChirurgicas(Set<OperazioneChirurgica> operazioneChirurgicas) {
		this.operazioneChirurgicas = operazioneChirurgicas;
	}

	@Override
	public int compareTo(BUtente o) {
		return this.toString().compareTo(o.toString());
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "enabled")
	@NotNull
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "enabled_date", length = 29)
	public Date getEnabledDate() {
		return this.enabledDate;
	}

	public void setEnabledDate(Date enabledDate) {
		this.enabledDate = enabledDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_scadenza", length = 29)
	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "superutente", nullable = true)
	@Cascade(CascadeType.ALL)
	public SuperUtenteAll getSuperutente() {
		return this.superutente;
	}

	public void setSuperutente(SuperUtente superutente) {
		this.superutente = SuperUtenteDAO.getSuperutenteAll(superutente.getId());
	}
	
	
	public void setSuperutente(SuperUtenteAll superutente) {
		this.superutente = superutente;
	}
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asl_referenza", nullable = true)
	@Cascade(CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	public LookupAsl getAslReferenza() {
		return this.aslReferenza;
	}

	public void setAslReferenza(LookupAsl aslReferenza) {
		this.aslReferenza = aslReferenza;
	}

	@Transient
	public String getRuoloByTalos() {
		// System.out.println("Sto recuperando il ruolo dell'utente " +
		// this.getUsername() + ". ");
		return Permessi.getRuolo(this);
	}

	@Column(name = "access_position_lat")
	public Double getAccessPositionLat() {
		return accessPositionLat;
	}

	public void setAccessPositionLat(Double accessPositionLat) {
		this.accessPositionLat = accessPositionLat;
	}

	public void setAccessPositionLat(String lat) {
		if (!lat.equals("")) {
			accessPositionLat = Double.parseDouble(lat);
		}
	}

	@Column(name = "access_position_lon")
	public Double getAccessPositionLon() {
		return accessPositionLon;
	}

	public void setAccessPositionLon(Double accessPositionLon) {
		this.accessPositionLon = accessPositionLon;
	}

	public void setAccessPositionLon(String lon) {
		if (!lon.equals("")) {
			accessPositionLon = Double.parseDouble(lon);
		}
	}

	@Column(name = "access_position_err")
	public String getAccessPositionErr() {
		return accessPositionErr;
	}

	public void setAccessPositionErr(String accessPositionErr) {
		this.accessPositionErr = accessPositionErr;
	}

}
