/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.dao.UtenteDAO;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "trasferimento", schema = "public")
@Where( clause = "trashed_date is null" )
public class Trasferimento implements java.io.Serializable
{
	private static final long serialVersionUID = -1656622444261812507L;
	
	private int id;
	private CartellaClinica cartellaClinica;
	private CartellaClinica cartellaClinicaDestinatario;
	private CartellaClinica cartellaClinicaMortoDestinatario;
	private CartellaClinica cartellaClinicaMittenteRiconsegna;
	private Date dataRichiesta;
	public Date dataAccettazioneCriuv;
	private Date dataRifiutoCriuv;
	private Date dataAccettazioneDestinatario;
	private Date dataRiconsegna;
	private Date dataApprovazioneRiconsegna;
	private Date dataRifiutoRiconsegna;
	
	private Clinica clinicaOrigine;
	private Clinica clinicaDestinazione;
	
	private Boolean urgenza = false;
	private Boolean automaticoPerNecroscopia = false;
	
	private String notaRichiesta;
	private String notaCriuv;
	private String notaDestinatario;
	private String notaRiconsegna;
	private String notaApprovazioneRiconsegna;

	private Set<LookupOperazioniAccettazione> operazioniRichieste = new HashSet<LookupOperazioniAccettazione>(0);
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;

	public Trasferimento()
	{
		
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica_destinatario")
	public CartellaClinica getCartellaClinicaDestinatario() {
		return this.cartellaClinicaDestinatario;
	}

	public void setCartellaClinicaDestinatario(CartellaClinica cartellaClinicaDestinatario) {
		this.cartellaClinicaDestinatario = cartellaClinicaDestinatario;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica_morto_destinatario")
	public CartellaClinica getCartellaClinicaMortoDestinatario() {
		return this.cartellaClinicaMortoDestinatario;
	}

	public void setCartellaClinicaMortoDestinatario(CartellaClinica cartellaClinicaMortoDestinatario) {
		this.cartellaClinicaMortoDestinatario = cartellaClinicaMortoDestinatario;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica_mittente_riconsegna")
	public CartellaClinica getCartellaClinicaMittenteRiconsegna() {
		return this.cartellaClinicaMittenteRiconsegna;
	}

	public void setCartellaClinicaMittenteRiconsegna(CartellaClinica cartellaClinicaMittenteRiconsegna) {
		this.cartellaClinicaMittenteRiconsegna = cartellaClinicaMittenteRiconsegna;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_richiesta", length = 13)
	@NotNull
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_accettazione_criuv", length = 13)
	public Date getDataAccettazioneCriuv() {
		return this.dataAccettazioneCriuv;
	}

	public void setDataAccettazioneCriuv(Date dataAccettazioneCriuv) {
		this.dataAccettazioneCriuv = dataAccettazioneCriuv;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_rifiuto_criuv", length = 13)
	public Date getDataRifiutoCriuv() {
		return this.dataRifiutoCriuv;
	}

	public void setDataRifiutoCriuv(Date dataRifiutoCriuv) {
		this.dataRifiutoCriuv = dataRifiutoCriuv;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_accettazione_destinatario", length = 13)
	public Date getDataAccettazioneDestinatario() {
		return this.dataAccettazioneDestinatario;
	}

	public void setDataAccettazioneDestinatario(Date dataAccettazioneDestinatario) {
		this.dataAccettazioneDestinatario = dataAccettazioneDestinatario;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_riconsegna", length = 29)
	public Date getDataRiconsegna() {
		return this.dataRiconsegna;
	}

	public void setDataRiconsegna(Date dataRiconsegna) {
		this.dataRiconsegna = dataRiconsegna;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_approvazione_riconsegna", length = 29)
	public Date getDataApprovazioneRiconsegna() {
		return this.dataApprovazioneRiconsegna;
	}

	public void setDataApprovazioneRiconsegna(Date dataApprovazioneRiconsegna) {
		this.dataApprovazioneRiconsegna = dataApprovazioneRiconsegna;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_rifiuto_riconsegna", length = 29)
	public Date getDataRifiutoRiconsegna() {
		return this.dataRifiutoRiconsegna;
	}

	public void setDataRifiutoRiconsegna(Date dataRifiutoRiconsegna) {
		this.dataRifiutoRiconsegna = dataRifiutoRiconsegna;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull

	public BUtenteAll getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(BUtente enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = UtenteDAO.getUtenteAll(enteredBy.getId());
	}
	
	
	public void setEnteredBy(BUtenteAll enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = enteredBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtenteAll getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	UtenteDAO.getUtenteAll(modifiedBy.getId());
	}
	
	
	public void setModifiedBy(BUtenteAll modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	modifiedBy;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinica_origine")
	@Fetch (FetchMode.SELECT) 
	@NotNull
	public Clinica getClinicaOrigine() {
		return clinicaOrigine;
	}
	
	public void setClinicaOrigine(Clinica clinicaOrigine) {
		this.clinicaOrigine = clinicaOrigine;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinica_destinazione")
	@Fetch (FetchMode.SELECT) 
	public Clinica getClinicaDestinazione() {
		return clinicaDestinazione;
	}
	
	public void setClinicaDestinazione(Clinica clinicaDestinazione) {
		this.clinicaDestinazione = clinicaDestinazione;
	}
	
	@Column(name = "nota_richiesta")
	@Type(type = "text")
	public String getNotaRichiesta() {
		return this.notaRichiesta;
	}

	public void setNotaRichiesta(String notaRichiesta) {
		this.notaRichiesta = notaRichiesta;
	}
	
	@Column(name = "nota_criuv")
	@Type(type = "text")
	public String getNotaCriuv() {
		return this.notaCriuv;
	}

	public void setNotaCriuv(String notaCriuv) {
		this.notaCriuv = notaCriuv;
	}
	
	@Column(name = "nota_destinatario")
	@Type(type = "text")
	public String getNotaDestinatario() {
		return this.notaDestinatario;
	}

	public void setNotaDestinatario(String notaDestinatario) {
		this.notaDestinatario = notaDestinatario;
	}
	
	@Column(name = "nota_riconsegna")
	@Type(type = "text")
	public String getNotaRiconsegna() {
		return this.notaRiconsegna;
	}

	public void setNotaRiconsegna(String notaRiconsegna) {
		this.notaRiconsegna = notaRiconsegna;
	}
	
	@Column(name = "nota_approvazione_riconsegna")
	@Type(type = "text")
	public String getNotaApprovazioneRiconsegna() {
		return this.notaApprovazioneRiconsegna;
	}

	public void setNotaApprovazioneRiconsegna(String notaApprovazioneRiconsegna) {
		this.notaApprovazioneRiconsegna = notaApprovazioneRiconsegna;
	}
	
	@Column(name = "urgenza", nullable = false)	
	public boolean getUrgenza() {
		return this.urgenza;
	}

	public void setUrgenza(boolean urgenza) {
		this.urgenza = urgenza;
	}
	
	@Column(name = "automatico_necroscopia", nullable = true)
	public boolean getAutomaticoPerNecroscopia() {
		return this.automaticoPerNecroscopia;
	}

	public void setAutomaticoPerNecroscopia(boolean automaticoPerNecroscopia) {
		this.automaticoPerNecroscopia = automaticoPerNecroscopia;
	}
	
	@Transient
	public StatoTrasferimento getStato()
	{
		StatoTrasferimento ret = new StatoTrasferimento();
		
		ret.setStato( this );

		return ret;
	}
	
	@Transient
	public boolean getDaApprovare()
	{
		boolean ret = true;
		
		ret = dataAccettazioneCriuv == null && dataRifiutoCriuv == null && !urgenza 
			&& (clinicaDestinazione == null || clinicaDestinazione.getLookupAsl().getId() != clinicaOrigine.getLookupAsl().getId());
		
		return ret;
	}
	
	@Transient
	public boolean getDaApprovareRiconsegna()
	{
		boolean ret = true;
		
		ret = dataRiconsegna != null && dataApprovazioneRiconsegna == null && dataRifiutoRiconsegna == null 
			&& (clinicaDestinazione.getLookupAsl().getId() != clinicaOrigine.getLookupAsl().getId());
		
		return ret;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "trasferimento_operazionirichieste", schema = "public", joinColumns = { @JoinColumn(name = "trasferimento", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operazione_richiesta", nullable = false, updatable = false) })
	@Size(min = 1, message = "Selezionare almeno una operazione")
	public Set<LookupOperazioniAccettazione> getOperazioniRichieste() {
		return this.operazioniRichieste;
	}

	public void setOperazioniRichieste(
			Set<LookupOperazioniAccettazione> operazioniRichieste) {
		this.operazioniRichieste = operazioniRichieste;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Trasferimento";
	}
	
}
