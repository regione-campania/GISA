/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupTipiStruttura;

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
@Table(name = "struttura_clinica", schema = "public")
@Where( clause = "trashed_date is null" )
public class StrutturaClinica implements java.io.Serializable
{
	private static final long serialVersionUID = -5652826872549822712L;
	
	private int id;
	private LookupTipiStruttura lookupTipiStruttura;
	private Clinica clinica;
	private String denominazione;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer enteredBy;
	private Integer modifiedBy;
	private boolean cane;
	private boolean gatto;
	private boolean sinantropo;
	
	private Set<CartellaClinica> cartellaClinicas = new HashSet<CartellaClinica>(0);
	private Set<BookingClinica> booking = new HashSet<BookingClinica>(0);

	public StrutturaClinica()
	{
		
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Strututra clinica";
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
	public LookupTipiStruttura getLookupTipiStruttura() {
		return this.lookupTipiStruttura;
	}

	public void setLookupTipiStruttura(LookupTipiStruttura lookupTipiStruttura) {
		this.lookupTipiStruttura = lookupTipiStruttura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinica")
	public Clinica getClinica() {
		return this.clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	@Column(name = "denominazione", length = 64)
	@Length(max = 64)
	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "strutturaClinica")
	@Where( clause = "trashed_date is null" )
	public Set<CartellaClinica> getCartellaClinicas() {
		return this.cartellaClinicas;
	}

	public void setCartellaClinicas(Set<CartellaClinica> cartellaClinicas) {
		this.cartellaClinicas = cartellaClinicas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "strutturaClinica")
	@Where( clause = "trashed_date is null" )
	public Set<BookingClinica> getBooking() {
		return booking;
	}

	public void setBooking(Set<BookingClinica> booking) {
		this.booking = booking;
	}
	
	@Column(name = "cane")
	@Type(type="boolean")
	public boolean getCane() {
		return this.cane;
	}

	public void setCane(boolean cane) {
		this.cane = cane;
	}
	
	@Column(name = "gatto")
	@Type(type="boolean")
	public boolean getGatto() {
		return this.gatto;
	}

	public void setGatto(boolean gatto) {
		this.gatto = gatto;
	}
	
	@Column(name = "sinantropo")
	@Type(type="boolean")
	public boolean getSinantropo() {
		return this.sinantropo;
	}

	public void setSinantropo(boolean sinantropo) {
		this.sinantropo = sinantropo;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}

}
