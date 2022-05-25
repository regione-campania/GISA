/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoPeriodoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoSintomi;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "esame_obiettivo_sintopatologia", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameObiettivoSintoPatologia implements java.io.Serializable
{
	private static final long serialVersionUID = 5528525804289839766L;
	
	private int id;
	private LookupEsameObiettivoApparati apparato;
	private CartellaClinica cartellaClinica;
	private Date dataEsameObiettivo;
	private String terapiePrecedenti;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private Set<LookupEsameObiettivoSintomi> esameObiettivoSintomis = new HashSet<LookupEsameObiettivoSintomi>(0);
	private LookupEsameObiettivoInsorgenzaSintomi insorgenzaSintomi;
	private LookupEsameObiettivoPeriodoInsorgenzaSintomi periodoInsorgenzaSintomi;

	public EsameObiettivoSintoPatologia()
	{
		
	}

	@Transient
	public String getNomeEsame()
	{
		return "Sintopatologia";
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
	@JoinColumn(name = "insorgenza_sintomi")
	public LookupEsameObiettivoInsorgenzaSintomi getInsorgenzaSintomi() {
		return this.insorgenzaSintomi;
	}

	public void setInsorgenzaSintomi(
			LookupEsameObiettivoInsorgenzaSintomi insorgenzaSintomi) {
		this.insorgenzaSintomi = insorgenzaSintomi;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "periodo_insorgenza_sintomi")
	public LookupEsameObiettivoPeriodoInsorgenzaSintomi getPeriodoInsorgenzaSintomi() {
		return this.periodoInsorgenzaSintomi;
	}

	public void setPeriodoInsorgenzaSintomi(
			LookupEsameObiettivoPeriodoInsorgenzaSintomi periodoInsorgenzaSintomi) {
		this.periodoInsorgenzaSintomi = periodoInsorgenzaSintomi;
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

	@Column(name = "terapie_precedenti")
	@Type(type="text")
	public String getTerapiePrecedenti() {
		return this.terapiePrecedenti;
	}

	public void setTerapiePrecedenti(String terapiePrecedenti) {
		this.terapiePrecedenti = terapiePrecedenti;
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "esame_obiettivo_sintopatologia_sintomi", 
			joinColumns =        { @JoinColumn(name = "id_sintopatologia", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_sintomi", nullable = false, updatable = false) })
	public Set<LookupEsameObiettivoSintomi> getEsameObiettivoSintomis() {
		return this.esameObiettivoSintomis;
	}

	public void setEsameObiettivoSintomis(
			Set<LookupEsameObiettivoSintomi> esameObiettivoSintomis) {
		this.esameObiettivoSintomis = esameObiettivoSintomis;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apparato")
	public LookupEsameObiettivoApparati getApparato() {
		return this.apparato;
	}

	public void setApparato(LookupEsameObiettivoApparati apparato){
		this.apparato = apparato;
	}

}
