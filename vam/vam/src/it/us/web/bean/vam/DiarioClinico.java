/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;

import java.net.URLEncoder;
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

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.itextpdf.text.html.HtmlEncoder;

@Entity
@Table(name = "diario_clinico", schema = "public")
@Where( clause = "trashed_date is null" )
public class DiarioClinico implements java.io.Serializable
{
	private static final long serialVersionUID = -1656622444261812507L;
	
	private int id;
	private LookupEsameObiettivoApparati apparato;	
	private CartellaClinica cartellaClinica;
	private Date data;
	private String osservazioni;
	private String temperatura;
	private Date entered;
	private Date trashedDate;
	private BUtente enteredBy;
	private Set<DiarioClinicoTipoEO> tipiEO = new HashSet<DiarioClinicoTipoEO>(0);

	public DiarioClinico()
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
	@JoinColumn(name = "apparato")
	public LookupEsameObiettivoApparati getApparato() {
		return this.apparato;
	}

	public void setApparato(LookupEsameObiettivoApparati apparato){
		this.apparato = apparato;
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
	@NotNull
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "temperatura", length = 64)
	@Length(max = 64)
	public String getTemperatura() {
		return this.temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "diarioClinico")
	@OrderBy(clause="id asc")
	public Set<DiarioClinicoTipoEO> getTipiEO() {
		return this.tipiEO;
	}

	public void setTipiEO(Set<DiarioClinicoTipoEO> tipiEO) {
		this.tipiEO = tipiEO;
	}
	
	@Column(name = "osservazioni")
	@Type(type = "text")
	public String getOsservazioni() {
		return this.osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}
	
	@SuppressWarnings("deprecation")
	@Transient
	public String getOsservazioniUrlEncoded()
	{
		String ret = this.osservazioni;
		if( ret != null )
		{
			ret = URLEncoder.encode( osservazioni );
		}
		return ret;
	}
	
	@Transient
	public String getOsservazioniHtmlEncoded()
	{
		String ret = this.osservazioni;
		if( ret != null )
		{
			ret = HtmlEncoder.encode( osservazioni );
		}
		return ret;
	}
}
