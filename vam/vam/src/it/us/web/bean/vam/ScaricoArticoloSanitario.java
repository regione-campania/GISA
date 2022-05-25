/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "scarico_articoli_sanitari", schema = "public")
@Where( clause = "trashed_date is null" )
public class ScaricoArticoloSanitario implements java.io.Serializable
{
	private static final long serialVersionUID = -1921248398217301209L;
	
	private int id;
		
	private float 	quantita;
	private Date 	dataScarico;
	
	private Date 	entered;
	private Date 	modified;
	private Date 	trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private MagazzinoArticoliSanitari magazzinoArticoliSanitari;
	
	@Transient
	public String getNomeEsame()
	{
		return "Scarico Articoli Sanitari";
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

	
	@Column(name = "quantita_scaricata")
	public float getQuantita() {
		return quantita;
	}

	public void setQuantita(float quantita) {
		this.quantita = quantita;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_scarico", length = 29)
	public Date getDataScarico() {
		return dataScarico;
	}

	public void setDataScarico(Date dataScarico) {
		this.dataScarico = dataScarico;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "magazzino")
	public MagazzinoArticoliSanitari getMagazzinoArticoliSanitari() {
		return magazzinoArticoliSanitari;
	}

	public void setMagazzinoArticoliSanitari(MagazzinoArticoliSanitari magazzinoArticoliSanitari) {
		this.magazzinoArticoliSanitari = magazzinoArticoliSanitari;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}

}

