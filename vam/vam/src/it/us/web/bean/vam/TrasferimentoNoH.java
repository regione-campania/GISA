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

public class TrasferimentoNoH implements java.io.Serializable
{
	private static final long serialVersionUID = -1656622444261812507L;
	
	private int id;
	private CartellaClinicaNoH cartellaClinica;
	private CartellaClinicaNoH cartellaClinicaDestinatario;
	private CartellaClinicaNoH cartellaClinicaMortoDestinatario;
	private CartellaClinicaNoH cartellaClinicaMittenteRiconsegna;
	private Date dataRichiesta;
	public Date dataAccettazioneCriuv;
	private Date dataRifiutoCriuv;
	private Date dataAccettazioneDestinatario;
	private Date dataRiconsegna;
	private Date dataApprovazioneRiconsegna;
	private Date dataRifiutoRiconsegna;
	
	private ClinicaNoH clinicaOrigine;
	private ClinicaNoH clinicaDestinazione;
	
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

	public TrasferimentoNoH()
	{
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CartellaClinicaNoH getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinicaNoH cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
	
	public CartellaClinicaNoH getCartellaClinicaDestinatario() {
		return this.cartellaClinicaDestinatario;
	}

	public void setCartellaClinicaDestinatario(CartellaClinicaNoH cartellaClinicaDestinatario) {
		this.cartellaClinicaDestinatario = cartellaClinicaDestinatario;
	}
	
	public CartellaClinicaNoH getCartellaClinicaMortoDestinatario() {
		return this.cartellaClinicaMortoDestinatario;
	}

	public void setCartellaClinicaMortoDestinatario(CartellaClinicaNoH cartellaClinicaMortoDestinatario) {
		this.cartellaClinicaMortoDestinatario = cartellaClinicaMortoDestinatario;
	}
	
	public CartellaClinicaNoH getCartellaClinicaMittenteRiconsegna() {
		return this.cartellaClinicaMittenteRiconsegna;
	}

	public void setCartellaClinicaMittenteRiconsegna(CartellaClinicaNoH cartellaClinicaMittenteRiconsegna) {
		this.cartellaClinicaMittenteRiconsegna = cartellaClinicaMittenteRiconsegna;
	}

	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	public Date getDataAccettazioneCriuv() {
		return this.dataAccettazioneCriuv;
	}

	public void setDataAccettazioneCriuv(Date dataAccettazioneCriuv) {
		this.dataAccettazioneCriuv = dataAccettazioneCriuv;
	}
	
	public Date getDataRifiutoCriuv() {
		return this.dataRifiutoCriuv;
	}

	public void setDataRifiutoCriuv(Date dataRifiutoCriuv) {
		this.dataRifiutoCriuv = dataRifiutoCriuv;
	}
	
	public Date getDataAccettazioneDestinatario() {
		return this.dataAccettazioneDestinatario;
	}

	public void setDataAccettazioneDestinatario(Date dataAccettazioneDestinatario) {
		this.dataAccettazioneDestinatario = dataAccettazioneDestinatario;
	}
	
	public Date getDataRiconsegna() {
		return this.dataRiconsegna;
	}

	public void setDataRiconsegna(Date dataRiconsegna) {
		this.dataRiconsegna = dataRiconsegna;
	}
	
	public Date getDataApprovazioneRiconsegna() {
		return this.dataApprovazioneRiconsegna;
	}

	public void setDataApprovazioneRiconsegna(Date dataApprovazioneRiconsegna) {
		this.dataApprovazioneRiconsegna = dataApprovazioneRiconsegna;
	}
	
	public Date getDataRifiutoRiconsegna() {
		return this.dataRifiutoRiconsegna;
	}

	public void setDataRifiutoRiconsegna(Date dataRifiutoRiconsegna) {
		this.dataRifiutoRiconsegna = dataRifiutoRiconsegna;
	}

	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

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
	
	public ClinicaNoH getClinicaOrigine() {
		return clinicaOrigine;
	}
	
	public void setClinicaOrigine(ClinicaNoH clinicaOrigine) {
		this.clinicaOrigine = clinicaOrigine;
	}
	
	public ClinicaNoH getClinicaDestinazione() {
		return clinicaDestinazione;
	}
	
	public void setClinicaDestinazione(ClinicaNoH clinicaDestinazione) {
		this.clinicaDestinazione = clinicaDestinazione;
	}
	
	public String getNotaRichiesta() {
		return this.notaRichiesta;
	}

	public void setNotaRichiesta(String notaRichiesta) {
		this.notaRichiesta = notaRichiesta;
	}
	
	public String getNotaCriuv() {
		return this.notaCriuv;
	}

	public void setNotaCriuv(String notaCriuv) {
		this.notaCriuv = notaCriuv;
	}
	
	public String getNotaDestinatario() {
		return this.notaDestinatario;
	}

	public void setNotaDestinatario(String notaDestinatario) {
		this.notaDestinatario = notaDestinatario;
	}
	
	public String getNotaRiconsegna() {
		return this.notaRiconsegna;
	}

	public void setNotaRiconsegna(String notaRiconsegna) {
		this.notaRiconsegna = notaRiconsegna;
	}
	
	public String getNotaApprovazioneRiconsegna() {
		return this.notaApprovazioneRiconsegna;
	}

	public void setNotaApprovazioneRiconsegna(String notaApprovazioneRiconsegna) {
		this.notaApprovazioneRiconsegna = notaApprovazioneRiconsegna;
	}
	
	public boolean getUrgenza() {
		return this.urgenza;
	}

	public void setUrgenza(boolean urgenza) {
		this.urgenza = urgenza;
	}
	
	public boolean getAutomaticoPerNecroscopia() {
		return this.automaticoPerNecroscopia;
	}

	public void setAutomaticoPerNecroscopia(boolean automaticoPerNecroscopia) {
		this.automaticoPerNecroscopia = automaticoPerNecroscopia;
	}
	
	public StatoTrasferimento getStato()
	{
		StatoTrasferimento ret = new StatoTrasferimento();
		
		ret.setStato( this );

		return ret;
	}
	
	public boolean getDaApprovare()
	{
		boolean ret = true;
		
		ret = dataAccettazioneCriuv == null && dataRifiutoCriuv == null && !urgenza 
			&& (clinicaDestinazione == null || !clinicaDestinazione.getAsl().equals(clinicaOrigine.getAsl()));
		
		return ret;
	}
	
	public boolean getDaApprovareRiconsegna()
	{
		boolean ret = true;
		
		ret = dataRiconsegna != null && dataApprovazioneRiconsegna == null && dataRifiutoRiconsegna == null 
			&& (!clinicaDestinazione.getAsl().equals(clinicaOrigine.getAsl()));
		
		return ret;
	}
	
	public Set<LookupOperazioniAccettazione> getOperazioniRichieste() {
		return this.operazioniRichieste;
	}

	public void setOperazioniRichieste(
			Set<LookupOperazioniAccettazione> operazioniRichieste) {
		this.operazioniRichieste = operazioniRichieste;
	}
	
	public String toString()
	{
		return id+"";
	}
	
	public String getNomeEsame()
	{
		return "Trasferimento";
	}
	
}
