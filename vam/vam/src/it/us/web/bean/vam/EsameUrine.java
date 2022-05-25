/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupEsameUrineColore;
import it.us.web.bean.vam.lookup.LookupEsameUrinePresenze;

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
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "esame_urine", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameUrine implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = -259373535024094667L;
	
	private int id;
	private CartellaClinica cartellaClinica;
	private String note;
	private Date dataRichiesta;
	private Date dataEsito;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	//esame fisico/chimico
	private Float volume;
	private Float pesoSpecifico;
	private Float ph;
	private Float proteine;
	private Float urobilinogeno;
	private LookupEsameUrinePresenze glucosio;
	private LookupEsameUrinePresenze bilirubina;
	private LookupEsameUrinePresenze corpiChetonici;
	private LookupEsameUrinePresenze emoglobina;
	private LookupEsameUrinePresenze nitriti;
	private LookupEsameUrinePresenze sangue;
	private LookupEsameUrineColore colore;
	
	//esame microscopico
	private LookupEsameUrinePresenze batteri;
	private LookupEsameUrinePresenze cilindri;
	private LookupEsameUrinePresenze celluleEpiteliali;
	private LookupEsameUrinePresenze cristalli;
	private LookupEsameUrinePresenze eritrociti;
	private LookupEsameUrinePresenze leucociti;
	

	public EsameUrine()
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
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_richiesta", length = 29)
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito", length = 29)
	public Date getDataEsito() {
		return this.dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}

	@Column(name = "volume")
	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	@Column(name = "peso_specifico")
	public Float getPesoSpecifico() {
		return pesoSpecifico;
	}

	public void setPesoSpecifico(Float pesoSpecifico) {
		this.pesoSpecifico = pesoSpecifico;
	}

	@Column(name = "ph")
	public Float getPh() {
		return ph;
	}

	public void setPh(Float ph) {
		this.ph = ph;
	}

	@Column(name = "proteine")
	public Float getProteine() {
		return proteine;
	}

	public void setProteine(Float proteine) {
		this.proteine = proteine;
	}

	@Column(name = "urobilinogeno")
	public Float getUrobilinogeno() {
		return urobilinogeno;
	}

	public void setUrobilinogeno(Float urobilinogeno) {
		this.urobilinogeno = urobilinogeno;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "glucosio")
	public LookupEsameUrinePresenze getGlucosio() {
		return glucosio;
	}

	public void setGlucosio(LookupEsameUrinePresenze glucosio) {
		this.glucosio = glucosio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bilirubina")
	public LookupEsameUrinePresenze getBilirubina() {
		return bilirubina;
	}

	public void setBilirubina(LookupEsameUrinePresenze bilirubina) {
		this.bilirubina = bilirubina;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "corpi_chetonici")
	public LookupEsameUrinePresenze getCorpiChetonici() {
		return corpiChetonici;
	}

	public void setCorpiChetonici(LookupEsameUrinePresenze corpiChetonici) {
		this.corpiChetonici = corpiChetonici;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emoglobina")
	public LookupEsameUrinePresenze getEmoglobina() {
		return emoglobina;
	}

	public void setEmoglobina(LookupEsameUrinePresenze emoglobina) {
		this.emoglobina = emoglobina;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nitriti")
	public LookupEsameUrinePresenze getNitriti() {
		return nitriti;
	}

	public void setNitriti(LookupEsameUrinePresenze nitriti) {
		this.nitriti = nitriti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sangue")
	public LookupEsameUrinePresenze getSangue() {
		return sangue;
	}

	public void setSangue(LookupEsameUrinePresenze sangue) {
		this.sangue = sangue;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colore")
	public LookupEsameUrineColore getColore() {
		return colore;
	}

	public void setColore(LookupEsameUrineColore colore) {
		this.colore = colore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batteri")
	public LookupEsameUrinePresenze getBatteri() {
		return batteri;
	}

	public void setBatteri(LookupEsameUrinePresenze batteri) {
		this.batteri = batteri;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cilindri")
	public LookupEsameUrinePresenze getCilindri() {
		return cilindri;
	}

	public void setCilindri(LookupEsameUrinePresenze cilindri) {
		this.cilindri = cilindri;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cellule_epiteliali")
	public LookupEsameUrinePresenze getCelluleEpiteliali() {
		return celluleEpiteliali;
	}

	public void setCelluleEpiteliali(LookupEsameUrinePresenze celluleEpiteliali) {
		this.celluleEpiteliali = celluleEpiteliali;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cristalli")
	public LookupEsameUrinePresenze getCristalli() {
		return cristalli;
	}

	public void setCristalli(LookupEsameUrinePresenze cristalli) {
		this.cristalli = cristalli;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eritrociti")
	public LookupEsameUrinePresenze getEritrociti() {
		return eritrociti;
	}

	public void setEritrociti(LookupEsameUrinePresenze eritrociti) {
		this.eritrociti = eritrociti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leucociti")
	public LookupEsameUrinePresenze getLeucociti() {
		return leucociti;
	}

	public void setLeucociti(LookupEsameUrinePresenze leucociti) {
		this.leucociti = leucociti;
	}
	
	
	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Urine";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "<b>urine</b>";
	}

}
