/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "esame_obiettivo", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameObiettivo implements java.io.Serializable
{
	private static final long serialVersionUID = 5528525804289839766L;
	
	private int id;
	private LookupEsameObiettivoTipo lookupEsameObiettivoTipo;	
	private CartellaClinica cartellaClinica;
	private Date dataEsameObiettivo;
	private Boolean normale;	
	private String note;
	private String patologieCongenite;
	private String altro;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;	
	private Set<EsameObiettivoEsito> esameObiettivoEsitos = new HashSet<EsameObiettivoEsito>(0);

	public EsameObiettivo()
	{
		
	}
	
	@Override
	public String toString()
	{
		return id+"";
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
	public LookupEsameObiettivoTipo getLookupEsameObiettivoTipo() {
		return this.lookupEsameObiettivoTipo;
	}

	public void setLookupEsameObiettivoTipo(
			LookupEsameObiettivoTipo lookupEsameObiettivoTipo) {
		this.lookupEsameObiettivoTipo = lookupEsameObiettivoTipo;
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
	@Column(name = "data_esame", length = 13)
	@NotNull
	public Date getDataEsameObiettivo() {
		return this.dataEsameObiettivo;
	}

	public void setDataEsameObiettivo(Date dataEsameObiettivo) {
		this.dataEsameObiettivo = dataEsameObiettivo;
	}

	@Column(name = "normale")
	@NotNull
	public Boolean getNormale() {
		return this.normale;
	}

	public void setNormale(Boolean normale) {
		this.normale = normale;
	}
	
	@Column(name = "note", length = 64)
	@Length(max = 64)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "patologie_congenite")
	@Type(type="text")
	public String getPatologieCongenite() {
		return this.patologieCongenite;
	}

	public void setPatologieCongenite(String patologieCongenite) {
		this.patologieCongenite = patologieCongenite;
	}
	
	@Column(name = "altro")
	public String getAltro() {
		return this.altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "esameObiettivo")
	@Where( clause = "trashed_date is null" )
	public Set<EsameObiettivoEsito> getEsameObiettivoEsitos() {
		return this.esameObiettivoEsitos;
	}

	public void setEsameObiettivoEsitos(
			Set<EsameObiettivoEsito> esameObiettivoEsitos) {
		this.esameObiettivoEsitos = esameObiettivoEsitos;
	}
	

	@Transient
	public String getNomeEsame()
	{
		return "Esame obiettivo";
	}

}
