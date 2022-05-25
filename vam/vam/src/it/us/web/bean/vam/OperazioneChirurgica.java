/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupOperazioniChirurgiche;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "operazione_chirurgica", schema = "public")
@Where( clause = "trashed_date is null" )
public class OperazioneChirurgica implements java.io.Serializable
{
	private static final long serialVersionUID = 4911200589443296465L;
	
	private int id;
	private LookupOperazioniChirurgiche lookupOperazioniChirurgiche;
	private CartellaClinica cartellaClinica;
	private Date data;
	private String intervento;
	private String anestesia;
	private String descrizione;
	private String easmeIstologico;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer enteredBy;
	private Integer modifiedBy;
	private Set<BUtente> utentis = new HashSet<BUtente>(0);

	public OperazioneChirurgica()
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
	@JoinColumn(name = "tipo")
	public LookupOperazioniChirurgiche getLookupOperazioniChirurgiche() {
		return this.lookupOperazioniChirurgiche;
	}

	public void setLookupOperazioniChirurgiche(
			LookupOperazioniChirurgiche lookupOperazioniChirurgiche) {
		this.lookupOperazioniChirurgiche = lookupOperazioniChirurgiche;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", length = 13)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "intervento")
	public String getIntervento() {
		return this.intervento;
	}

	public void setIntervento(String intervento) {
		this.intervento = intervento;
	}

	@Column(name = "anestesia")
	public String getAnestesia() {
		return this.anestesia;
	}

	public void setAnestesia(String anestesia) {
		this.anestesia = anestesia;
	}

	@Column(name = "descrizione")
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "easme_istologico")
	public String getEasmeIstologico() {
		return this.easmeIstologico;
	}

	public void setEasmeIstologico(String easmeIstologico) {
		this.easmeIstologico = easmeIstologico;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@Column(name = "entered_by")
	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "operatori_operazioni", schema = "public", joinColumns = { @JoinColumn(name = "operazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operatore", nullable = false, updatable = false) })
	@Where( clause = "trashed_date is null" )
	public Set<BUtente> getUtentis() {
		return this.utentis;
	}

	public void setUtentis(Set<BUtente> utentis) {
		this.utentis = utentis;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}

}
