/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.util.DateUtils;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "fascicolo_sanitario", schema = "public")
@Where( clause = "trashed_date is null" )
public class FascicoloSanitario implements java.io.Serializable
{
	private static final long serialVersionUID = 7046648521134189879L;

	private int id;
	private Date dataApertura;
	private Date dataChiusura;
	private String numero;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	private Set<CartellaClinica> cartellaClinicas = new HashSet<CartellaClinica>(0);

	public FascicoloSanitario()
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

	@Temporal(TemporalType.DATE)
	@Column(name = "data_apertura", length = 13)
	@NotNull
	public Date getDataApertura() {
		return this.dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_chiusura", length = 13)
	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}
	
	@Column(name = "numero")
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
	@NotNull
	public BUtente getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fascicoloSanitario")
	@Where( clause = "trashed_date is null" )
	@OrderBy(value="dataApertura asc, id asc")
	public Set<CartellaClinica> getCartellaClinicas() {
		return this.cartellaClinicas;
	}

	public void setCartellaClinicas(Set<CartellaClinica> cartellaClinicas) {
		this.cartellaClinicas = cartellaClinicas;
	}
	
	
	@Transient
	public Animale getAnimale() {
		return cartellaClinicas.iterator().next().getAccettazione().getAnimale();
	}
	
	@Transient
	public String getProprietarioCognome() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioCognome();
	}
	
	@Transient
	public String getProprietarioCap() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioCap();
	}
	
	@Transient
	public String getProprietarioCodiceFiscale() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioCodiceFiscale();
	}
	
	@Transient
	public String getProprietarioComune() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioComune();
	}
	
	@Transient
	public String getProprietarioDocumento() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioDocumento();
	}

	@Transient
	public String getProprietarioIndirizzo() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioIndirizzo();
	}
	
	@Transient
	public String getProprietarioNome() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioNome();
	}
	
	@Transient
	public String getProprietarioProvincia() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioProvincia();
	}
	
	@Transient
	public String getProprietarioTelefono() {
		return cartellaClinicas.iterator().next().getAccettazione().getProprietarioTelefono();
	}
	
	@Transient
	public int getUltimaCC()
	{
		
		List<Object> listCC = (List<Object>) Arrays.asList(cartellaClinicas.toArray());
		CartellaClinica last = (CartellaClinica) listCC.get(listCC.size()-1);
		return last.getId();
	}
	
}
