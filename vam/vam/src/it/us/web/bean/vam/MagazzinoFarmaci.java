/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "magazzino_farmaci", schema = "public")
@Where( clause = "trashed_date is null" )
public class MagazzinoFarmaci implements java.io.Serializable
{
	private static final long serialVersionUID = -1921248398217301209L;
	
	private int id;
	private Clinica clinica;
	private LookupFarmaci 		lookupFarmaci;
	private LookupTipiFarmaco 	lookupTipiFarmaco;
	
	private float quantita;
	private String unitaMisura;
	private String descrizione;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private Set<CaricoFarmaco>  caricoFarmaci  = new HashSet<CaricoFarmaco>(0);
	private Set<ScaricoFarmaco> scaricoFarmaci = new HashSet<ScaricoFarmaco>(0);
	private Set<TerapiaAssegnata> terapiaAssegnatas = new HashSet<TerapiaAssegnata>(0);
	
	public MagazzinoFarmaci()
	{
		
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Magazzino Farmaci";
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
	@JoinColumn(name = "clinica")
	public Clinica getClinica() {
		return this.clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmaco")
	@NotNull		
	public LookupFarmaci getLookupFarmaci() {
		return lookupFarmaci;
	}

	public void setLookupFarmaci(LookupFarmaci lookupFarmaci) {
		this.lookupFarmaci = lookupFarmaci;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_farmaco")
	@NotNull	
	public LookupTipiFarmaco getLookupTipiFarmaco() {
		return lookupTipiFarmaco;
	}

	public void setLookupTipiFarmaco(LookupTipiFarmaco lookupTipiFarmaco) {
		this.lookupTipiFarmaco = lookupTipiFarmaco;
	}

	@Column(name = "quantita")		
	@Min(value=0, message = "La quantità residua deve essere > 0")
	public float getQuantita() {
		return quantita;
	}
	
	public void setQuantita(float quantita) {
		this.quantita = quantita;
	}

	@Column(name = "unita_misura", length = 64)
	@Length(max = 64)
	public String getUnitaMisura() {
		return this.unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	@Column(name = "descrizione")
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "note", length = 64)
	@Length(max = 64)
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

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mf")	
	public Set<CaricoFarmaco> getCaricoFarmaci() {
		return caricoFarmaci;
	}

	public void setCaricoFarmaci(Set<CaricoFarmaco> caricoFarmaci) {
		this.caricoFarmaci = caricoFarmaci;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mf")	
	public Set<ScaricoFarmaco> getScaricoFarmaci() {
		return scaricoFarmaci;
	}

	public void setScaricoFarmaci(Set<ScaricoFarmaco> scaricoFarmaci) {
		this.scaricoFarmaci = scaricoFarmaci;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "magazzinoFarmaci")	
	public Set<TerapiaAssegnata> getTerapiaAssegnatas() {
		return terapiaAssegnatas;
	}

	public void setTerapiaAssegnatas(Set<TerapiaAssegnata> terapiaAssegnatas) {
		this.terapiaAssegnatas = terapiaAssegnatas;
	}
	
	
	
	
}

