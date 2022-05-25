/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupSpecie;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "animale_deceduto", schema = "public")
@Where( clause = "trashed_date is null" )
public class AnimaleDeceduto implements java.io.Serializable
{
	private static final long serialVersionUID = 3764880202719490784L;
	
	private int id;
	private LookupSpecie lookupSpecie;
	private String identificativo;
	private String razza;
	private String sesso;
	private Date dataNascita;
	private boolean dataNascitaCerta;
	
	private String descrizione;
	private String note;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer enteredBy;
	private Integer modifiedBy;
	
	private Autopsia autopsia;
	private Set<Accettazione> accettaziones = new HashSet<Accettazione>(0);

	public AnimaleDeceduto()
	{
		
	}

	@Transient
	public int getAccettazioneSize()
	{
		return (accettaziones == null) ? (0) : (accettaziones.size());
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
	@JoinColumn(name = "specie")
	public LookupSpecie getLookupSpecie() {
		return this.lookupSpecie;
	}

	public void setLookupSpecie(LookupSpecie lookupSpecie) {
		this.lookupSpecie = lookupSpecie;
	}

	@Column(name = "identificativo", length = 64, unique = true)
	@Length(max = 64)
	public String getIdentificativo() {
		return this.identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	@Column(name = "razza", length = 64)
	@Length(max = 64)
	public String getRazza() {
		return this.razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}

	@Column(name = "sesso", length = 2)
	@Length(max = 2)
	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascita", length = 13)
	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
	@Column(name = "data_nascita_certa", nullable = false)	
	public boolean isDataNascitaCerta() {
		return this.dataNascitaCerta;
	}

	public void setDataNascitaCerta(boolean dataNascitaCerta) {
		this.dataNascitaCerta = dataNascitaCerta;
	}
	
	

	@Column(name = "descrizione", length = 256)
	@Length(max = 256)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "note", length = 256)
	@Length(max = 256)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "animale")
	@Where( clause = "trashed_date is null" )
	public Set<Accettazione> getAccettaziones() {
		return this.accettaziones;
	}

	public void setAccettaziones(Set<Accettazione> accettaziones) {
		this.accettaziones = accettaziones;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autopsia")
	public Autopsia getAutopsia() {
		return autopsia;
	}

	public void setAutopsia(Autopsia autopsia) {
		this.autopsia = autopsia;
	}
	
	
	
	
	

}

