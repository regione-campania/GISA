/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "attivita_bdr", schema = "public")
@Where( clause = "trashed_date is null" )
public class AttivitaBdr implements java.io.Serializable
{
	private static final long serialVersionUID = 4218602302365729127L;
	
	private int id;
	private LookupOperazioniAccettazione operazioneBdr;
	private Accettazione accettazione;
	private CartellaClinica cc;
	private String descrizione;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer idRegistrazioneBdr;
	private Integer enteredBy;
	private Integer modifiedBy;

	public AttivitaBdr()
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = {javax.persistence.CascadeType.ALL})
	@JoinColumn(name = "tipo_operazione")
	public LookupOperazioniAccettazione getOperazioneBdr() {
		return this.operazioneBdr;
	}

	public void setOperazioneBdr(LookupOperazioniAccettazione operazioneBdr) {
		this.operazioneBdr = operazioneBdr;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accettazione")
	public Accettazione getAccettazione() {
		return this.accettazione;
	}

	public void setAccettazione(Accettazione accettazione) {
		this.accettazione = accettazione;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cc")
	public CartellaClinica getCc() {
		return this.cc;
	}

	public void setCc(CartellaClinica cc) {
		this.cc = cc;
	}

	@Column(name = "descrizione", length = 64)
	@Length(max = 64)
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
	
	@Column(name = "id_registrazione_bdr")
	public Integer getIdRegistrazioneBdr() {
		return idRegistrazioneBdr;
	}

	public void setIdRegistrazioneBdr(Integer idRegistrazioneBdr) {
		this.idRegistrazioneBdr = idRegistrazioneBdr;
	}

}
