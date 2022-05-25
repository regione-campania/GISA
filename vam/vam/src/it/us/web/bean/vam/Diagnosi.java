/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "diagnosi", schema = "public")
public class Diagnosi implements java.io.Serializable{

	private int id;
	private String note;
	private String diagnosi;
	private String tipoDiagnosi;
	private Date dataDiagnosi;
	private Date entered;
	private Date modified;	
	private BUtente enteredBy;
	private BUtente modifiedBy;	
	private Set<LookupDiagnosi> lookupDiagnosis = new HashSet<LookupDiagnosi>(0);
	private CartellaClinica cartellaClinica;
	private Set<DiagnosiEffettuate> diagnosiEffettuate = new HashSet<DiagnosiEffettuate>(0);
	
	@Transient
	public String getNomeEsame()
	{
		return "Diagnosi";
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
	
	@Column(name = "note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "diagnosi")
	@Type(type="text")
	public String getDiagnosi() {
		return diagnosi;
	}
	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}
	
	@Column(name = "tipo_diagnosi")
	@Type(type="text")
	public String getTipoDiagnosi() {
		return tipoDiagnosi;
	}
	public void setTipoDiagnosi(String tipoDiagnosi) {
		this.tipoDiagnosi = tipoDiagnosi;
	}
	
	@Transient
	public String getDiagnosiChirurgiche() {
		return diagnosi + ((diagnosi!=null && !diagnosi.equals(""))?(" - " + tipoDiagnosi):(""));
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_diagnosi", length = 13)
	@NotNull
	public Date getDataDiagnosi() {
		return dataDiagnosi;
	}
	public void setDataDiagnosi(Date dataDiagnosi) {
		this.dataDiagnosi = dataDiagnosi;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 13)
	@NotNull
	public Date getEntered() {
		return entered;
	}
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 13)
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull	
	public BUtente getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	@NotNull	
	public BUtente getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "diagnosi_diagnosieffettuate", schema = "public", joinColumns = { @JoinColumn(name = "diagnosi", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "diagnosi_effettuata", nullable = false, updatable = false) })
//	public Set<LookupDiagnosi> getLookupDiagnosis() {
//		return lookupDiagnosis;
//	}
//	public void setLookupDiagnosis(Set<LookupDiagnosi> lookupDiagnosis) {
//		this.lookupDiagnosis = lookupDiagnosis;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "diagnosi")	
	public Set<DiagnosiEffettuate> getDiagnosiEffettuate() {
		return this.diagnosiEffettuate;
	}

	public void setDiagnosiEffettuate(Set<DiagnosiEffettuate> diagnosiEffettuate) {
		this.diagnosiEffettuate = diagnosiEffettuate;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}

}
