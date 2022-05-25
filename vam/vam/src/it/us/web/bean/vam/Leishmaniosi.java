/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupLeishmaniosiEsiti;

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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "leishmaniosi", schema = "public")
@Where( clause = "trashed_date is null" )
public class Leishmaniosi implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = -8831477707572244920L;
	
	private int id;
	private Date trashedDate;
	private CartellaClinica cartellaClinica;
	
	private Date entered;
	private Date modified;	
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	
	private Date dataPrelievoLeishmaniosi;
	private Date dataEsitoLeishmaniosi;
	private LookupLeishmaniosiEsiti lle;
	
	private String ordinanzaSindaco;
	private Date   dataOrdinanzaSindaco;
	
	private String veterinario;
	
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
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_prelievo_leishmaniosi", length = 29)	
	public Date getDataPrelievoLeishmaniosi() {
		return dataPrelievoLeishmaniosi;
	}
	public void setDataPrelievoLeishmaniosi(Date dataPrelievoLeishmaniosi) {
		this.dataPrelievoLeishmaniosi = dataPrelievoLeishmaniosi;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito_leishmaniosi", length = 29)	
	public Date getDataEsitoLeishmaniosi() {
		return dataEsitoLeishmaniosi;
	}
	public void setDataEsitoLeishmaniosi(Date dataEsitoLeishmaniosi) {
		this.dataEsitoLeishmaniosi = dataEsitoLeishmaniosi;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esito_leishmaniosi")
	public LookupLeishmaniosiEsiti getLle() {
		return lle;
	}
	public void setLle(LookupLeishmaniosiEsiti lle) {
		this.lle = lle;
	}
	
	@Column(name = "ordinanza_sindazo", length = 12)
	@Length(max = 12)
	public String getOrdinanzaSindaco() {
		return ordinanzaSindaco;
	}
	public void setOrdinanzaSindaco(String ordinanzaSindaco) {
		this.ordinanzaSindaco = ordinanzaSindaco;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ordinanza_sindaco", length = 29)	
	public Date getDataOrdinanzaSindaco() {
		return dataOrdinanzaSindaco;
	}
	public void setDataOrdinanzaSindaco(Date dataOrdinanzaSindaco) {
		this.dataOrdinanzaSindaco = dataOrdinanzaSindaco;
	}
	
	@Override
	@Transient
	public Date getDataEsito()
	{
		return getDataEsitoLeishmaniosi();
	}
	
	@Override
	@Transient
	public Date getDataRichiesta()
	{
		return getDataPrelievoLeishmaniosi();
	}
	
	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Leishmaniosi";
	}
	
	@Override
	@Transient
	public String getHtml()
	{
		return "implementare leishmaniosi";
	}
	
	
	@Column(name = "veterinario")
	@Type(type = "text")
	public String getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(String veterinario) {
		this.veterinario = veterinario;
	}
	
	
	
	
	
}
