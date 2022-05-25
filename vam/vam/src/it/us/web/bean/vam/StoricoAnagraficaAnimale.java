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
@Table(name = "storico_anagrafica_animale", schema = "public")
@Where( clause = "trashed_date is null" )
public class StoricoAnagraficaAnimale implements java.io.Serializable
{
	private static final long serialVersionUID = -5683301989406129454L;
	
	private int id;
	private Animale animale;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	private Integer razza;
	private String sesso;
	private String razzaSinantropo;
	private String mantelloSinantropo;
	private Integer taglia;
	private Integer mantello;
	
	public StoricoAnagraficaAnimale()
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
	@JoinColumn(name = "animale")
	public Animale getAnimale() {
		return this.animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
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
	
	@Column(name = "razza")
	public Integer getRazza() {
		return razza;
	}
	public void setRazza(Integer razza) {
		this.razza = razza;
	}
	
	@Column(name = "sesso")
	public String getSesso(){
		return this.sesso;
	}
	
	public void setSesso(String sesso){
		this.sesso=sesso;
	}
	
	@Column(name = "taglia")
	public Integer getTaglia(){
		return this.taglia;
	}
	
	public void setTaglia(Integer taglia){
		this.taglia=taglia;
	}
	
	@Column(name = "mantello")
	public Integer getMantello() {
		return mantello;
	}
	public void setMantello(Integer mantello) {
		this.mantello = mantello;
	}
	
	@Column(name = "razza_sinantropo")
	@Type(type="text")
	public String getRazzaSinantropo() {
		return this.razzaSinantropo;
	}

	public void setRazzaSinantropo(String razzaSinantropo) {
		this.razzaSinantropo = razzaSinantropo;
	}
	
	@Column(name = "mantello_sinantropo")
	@Type(type="text")
	public String getMantelloSinantropo() {
		return this.mantelloSinantropo;
	}

	public void setMantelloSinantropo(String mantelloSinantropo) {
		this.mantelloSinantropo = mantelloSinantropo;
	}
	

}

