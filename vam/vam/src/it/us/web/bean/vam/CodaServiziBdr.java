/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "coda_servizi_bdr", schema = "public")
@Where( clause = "trashed_date is null" )

public class CodaServiziBdr implements java.io.Serializable
{
	private static final long serialVersionUID = 310529298751894357L;
	private int id;
	private String operazione;
	private Animale animale;
	private String dataSmarrimento;
	private String luogoSmarrimento;
	private String noteSmarrimento;
	private Boolean sanzione;
	private String importoSanzione;
	private BUtente utente;
	private String dataRitrovamento;
	private String luogoRitrovamento;
	private String noteRitrovamento;
	private String dataPassaporto;
	private String numeroPassaporto;
	private String notePassaporto;
	private String sesso;
	private LookupRazze razza;
	private LookupMantelli mantello;
	private LookupTaglie taglia;
	private int decessoCode;
	private String dataMorte;
	private Boolean dataDecessoPresunta;
	private Date entered;
	private BUtente enteredBy;
	private Date modified;
	private BUtente modifiedBy;
	private Date trashedDate;
	private Set<CodaServiziBdrTentativi> tentativi = new HashSet<CodaServiziBdrTentativi>(0);
	private AttivitaBdr attivitaBdr;
	

	public CodaServiziBdr()
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

	@Column(name = "operazione")
	public String getOperazione() {
		return this.operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="animale")
	public Animale getAnimale() {
		return animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
	}

	@Column(name = "data_smarrimento")
	public String getDataSmarrimento() {
		return this.dataSmarrimento;
	}

	public void setDataSmarrimento(String dataSmarrimento) {
		this.dataSmarrimento = dataSmarrimento;
	}

	@Column(name = "luogo_smarrimento")
	public String getLuogoSmarrimento() {
		return luogoSmarrimento;
	}

	public void setLuogoSmarrimento(String luogoSmarrimento) {
		this.luogoSmarrimento = luogoSmarrimento;
	}

	@Column(name = "note_smarrimento")
	public String getNoteSmarrimento() {
		return noteSmarrimento;
	}

	public void setNoteSmarrimento(String noteSmarrimento) {
		this.noteSmarrimento = noteSmarrimento;
	}

	@Column(name = "sanzione")
	public Boolean getSanzione() {
		return sanzione;
	}

	public void setSanzione(Boolean sanzione) {
		this.sanzione = sanzione;
	}

	@Column(name = "importo_sanzione")
	public String getImportoSanzione() {
		return importoSanzione;
	}

	public void setImportoSanzione(String importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "utente")
	public BUtente getUtente() {
		return utente;
	}

	public void setUtente(BUtente utente) {
		this.utente = utente;
	}

	@Column(name = "data_ritrovamento")
	public String getDataRitrovamento() {
		return dataRitrovamento;
	}

	public void setDataRitrovamento(String dataRitrovamento) {
		this.dataRitrovamento = dataRitrovamento;
	}

	@Column(name = "luogo_ritrovamento")
	public String getLuogoRitrovamento() {
		return luogoRitrovamento;
	}

	public void setLuogoRitrovamento(String luogoRitrovamento) {
		this.luogoRitrovamento = luogoRitrovamento;
	}

	@Column(name = "note_ritrovamento")
	public String getNoteRitrovamento() {
		return noteRitrovamento;
	}

	public void setNoteRitrovamento(String noteRitrovamento) {
		this.noteRitrovamento = noteRitrovamento;
	}

	@Column(name = "data_passaporto")
	public String getDataPassaporto() {
		return dataPassaporto;
	}

	public void setDataPassaporto(String dataPassaporto) {
		this.dataPassaporto = dataPassaporto;
	}

	@Column(name = "numero_passaporto")
	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}

	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}

	@Column(name = "note_passaporto")
	public String getNotePassaporto() {
		return notePassaporto;
	}

	public void setNotePassaporto(String notePassaporto) {
		this.notePassaporto = notePassaporto;
	}

	@Column(name = "decesso_code")
	public int getDecessoCode() {
		return decessoCode;
	}

	public void setDecessoCode(int decessoCode) {
		this.decessoCode = decessoCode;
	}

	@Column(name = "data_morte")
	public String getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(String dataMorte) {
		this.dataMorte = dataMorte;
	}

	@Column(name = "data_decesso_presunta")
	public Boolean getDataDecessoPresunta() {
		return dataDecessoPresunta;
	}

	public void setDataDecessoPresunta(Boolean dataDecessoPresunta) {
		this.dataDecessoPresunta = dataDecessoPresunta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered")
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date")
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull
	public BUtente getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtente getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servizioInCoda")
	@Where( clause = "trashed_date is null" )
	public Set<CodaServiziBdrTentativi> getTentativi() {
		return this.tentativi;
	}

	public void setTentativi(Set<CodaServiziBdrTentativi> tentativi) {
		this.tentativi = tentativi;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attivita_bdr")
	public AttivitaBdr getAttivitaBdr() {
		return this.attivitaBdr;
	}

	public void setAttivitaBdr(AttivitaBdr attivitaBdr) {
		this.attivitaBdr = attivitaBdr;
	}

	@Column(name = "sesso")
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "razza")
	public LookupRazze getRazza() {
		return razza;
	}
	public void setRazza(LookupRazze razza) {
		this.razza = razza;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mantello")
	public LookupMantelli getMantello() {
		return mantello;
	}
	public void setMantello(LookupMantelli mantello) {
		this.mantello = mantello;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taglia")
	public LookupTaglie getTaglia() {
		return taglia;
	}

	public void setTaglia(LookupTaglie taglia) {
		this.taglia = taglia;
	}
	
}
