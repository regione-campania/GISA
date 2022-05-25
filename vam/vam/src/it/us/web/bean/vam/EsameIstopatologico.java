/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoInteressamentoLinfonodale;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumore;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumoriPrecedenti;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.dao.UtenteDAO;

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
@Table(name = "esame_istopatologico", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameIstopatologico implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = -5683301989406129454L;
	
	private int id;
	
	private CartellaClinica cartellaClinica;
	private boolean outsideCC;
	private Animale animale;
	
	private String numero;
	private String numeroAccettazioneSigla;
	private String tipoAccettazione;
	
	private String peso;
	private Set<LookupAlimentazioni> lookupAlimentazionis = new HashSet<LookupAlimentazioni>(0);
	private Set<LookupHabitat> lookupHabitats = new HashSet<LookupHabitat>(0);
	private LookupAutopsiaSalaSettoria lass;
	
	private Date dataRichiesta;
	private Date dataEsito;
	private Integer t;
	private Integer n;
	private Integer m;
	private String tnm;
	private Integer dimensione;
	private String descrizioneMorfologica;
	private String diagnosiNonTumorale;
	private LookupEsameIstopatologicoTipoPrelievo tipoPrelievo;
	private LookupEsameIstopatologicoTipoDiagnosi tipoDiagnosi;
	private LookupEsameIstopatologicoTumore tumore;
	private LookupEsameIstopatologicoTumoriPrecedenti tumoriPrecedenti;
	private LookupEsameIstopatologicoInteressamentoLinfonodale interessamentoLinfonodale;
	private LookupEsameIstopatologicoSedeLesione sedeLesione;
	private LookupEsameIstopatologicoWhoUmana whoUmana;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
//	private BUtente enteredBy;
//	private BUtente modifiedBy;
	
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;

	public EsameIstopatologico()
	{
		
	}
	
	@Override
	public String toString()
	{
		return numero;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_settoria")
	public LookupAutopsiaSalaSettoria getLass() {
		return lass;
	}
	public void setLass(LookupAutopsiaSalaSettoria lass) {
		this.lass = lass;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
	
	
	@Column(name = "outsideCC") 
	public boolean getOutsideCC() {
		return outsideCC;
	}
	
	@Column(name = "outsideCC") 
	public boolean isOutsideCC() {
		return outsideCC;
	}

	public void setOutsideCC(boolean outsideCC) {
		this.outsideCC = outsideCC;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "animale")
	public Animale getAnimale() {
		return animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_richiesta", length = 29)
	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito", length = 29)
	public Date getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}
	
	@Column(name = "numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Column(name = "numero_accettazione_sigla")	
	@Type(type = "text")
	public String getNumeroAccettazioneSigla() {
		return this.numeroAccettazioneSigla;
	}

	public void setNumeroAccettazioneSigla(String numeroAccettazioneSigla) {
		this.numeroAccettazioneSigla = numeroAccettazioneSigla;
	}
	
	@Column(name = "tipo_accettazione")	
	@Type(type = "text")
	public String getTipoAccettazione() {
		return this.tipoAccettazione;
	}

	public void setTipoAccettazione(String tipoAccettazione) {
		this.tipoAccettazione = tipoAccettazione;
	}

	@Column(name = "t")
	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	@Column(name = "n")
	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	@Column(name = "m")
	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	@Column(name = "dimensione")
	public Integer getDimensione() {
		return dimensione;
	}

	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}

	@Column(name = "descrizione_morfologica")
	@Type(type = "text")
	public String getDescrizioneMorfologica() {
		return descrizioneMorfologica;
	}

	public void setDescrizioneMorfologica(String descrizioneMorfologica) {
		this.descrizioneMorfologica = descrizioneMorfologica;
	}
	
	@Column(name = "diagnosi_non_tumorale")
	@Type(type = "text")
	public String getDiagnosiNonTumorale() {
		return diagnosiNonTumorale;
	}

	public void setDiagnosiNonTumorale(String diagnosiNonTumorale) {
		this.diagnosiNonTumorale = diagnosiNonTumorale;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_prelievo")
	public LookupEsameIstopatologicoTipoPrelievo getTipoPrelievo() {
		return tipoPrelievo;
	}

	public void setTipoPrelievo(LookupEsameIstopatologicoTipoPrelievo tipoPrelievo) {
		this.tipoPrelievo = tipoPrelievo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_diagnosi")
	public LookupEsameIstopatologicoTipoDiagnosi getTipoDiagnosi() {
		return tipoDiagnosi;
	}

	public void setTipoDiagnosi(LookupEsameIstopatologicoTipoDiagnosi tipoDiagnosi) {
		this.tipoDiagnosi = tipoDiagnosi;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tumore")
	public LookupEsameIstopatologicoTumore getTumore() {
		return tumore;
	}

	public void setTumore(LookupEsameIstopatologicoTumore tumore) {
		this.tumore = tumore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tumori_precedenti")
	public LookupEsameIstopatologicoTumoriPrecedenti getTumoriPrecedenti() {
		return tumoriPrecedenti;
	}

	public void setTumoriPrecedenti(
			LookupEsameIstopatologicoTumoriPrecedenti tumoriPrecedenti) {
		this.tumoriPrecedenti = tumoriPrecedenti;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interessamento_linfonodale")
	public LookupEsameIstopatologicoInteressamentoLinfonodale getInteressamentoLinfonodale() {
		return interessamentoLinfonodale;
	}

	public void setInteressamentoLinfonodale(
			LookupEsameIstopatologicoInteressamentoLinfonodale interessamentoLinfonodale) {
		this.interessamentoLinfonodale = interessamentoLinfonodale;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sede_lesione")
	public LookupEsameIstopatologicoSedeLesione getSedeLesione() {
		return sedeLesione;
	}

	public void setSedeLesione(LookupEsameIstopatologicoSedeLesione sedeLesione) {
		this.sedeLesione = sedeLesione;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "who_umana")
	public LookupEsameIstopatologicoWhoUmana getWhoUmana() {
		return whoUmana;
	}

	public void setWhoUmana(LookupEsameIstopatologicoWhoUmana whoUmana) {
		this.whoUmana = whoUmana;
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

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "entered_by")
//	public BUtente getEnteredBy() {
//		return this.enteredBy;
//	}
//
//	public void setEnteredBy(BUtente enteredBy) {
//		this.enteredBy = enteredBy;
//	}
//
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "modified_by")
//	public BUtente getModifiedBy() {
//		return this.modifiedBy;
//	}
//
//	public void setModifiedBy(BUtente modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}
	
	
	
	@Column(name = "peso")
	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_alimentazioni_outside", schema = "public", joinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "alimentazione", nullable = false, updatable = false) })
	public Set<LookupAlimentazioni> getLookupAlimentazionis() {
		return lookupAlimentazionis;
	}

	public void setLookupAlimentazionis(
			Set<LookupAlimentazioni> lookupAlimentazionis) {
		this.lookupAlimentazionis = lookupAlimentazionis;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_habitat_outside", schema = "public", joinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "habitat", nullable = false, updatable = false) })
	public Set<LookupHabitat> getLookupHabitats() {
		return lookupHabitats;
	}

	public void setLookupHabitats(Set<LookupHabitat> lookupHabitats) {
		this.lookupHabitats = lookupHabitats;
	}
	
	
	@Transient
	public String getDiagnosiReferto() 
	{
		return ((whoUmana==null)?(""):(whoUmana)) + " " + ((diagnosiNonTumorale==null)?(""):(diagnosiNonTumorale)) ;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Istopatologico";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare istopatologico";
	}
	
	@Transient
	public String getTnm() 
	{
		tnm = "";
		
		String t = (this.t==null)?(""):("T: " + this.t);
		String n = (this.n==null)?(""):("N: " + this.n);
		String m = (this.m==null)?(""):("M: " + this.m);
		
		if(!t.equals(""))
		{
			tnm = t;
		}
		if(!n.equals(""))
		{
			if(tnm.equals(""))
				tnm = n;
			else
				tnm+=", " + n;
		}
		if(!m.equals(""))
		{
			if(tnm.equals(""))
				tnm = m;
			else
				tnm+=", " + m;
		}
			
		return tnm;
		
	}
	
	
	@Transient
	public String getIdentificativoAnimale() 
	{
		if(outsideCC)
		{
			if(animale!=null)
				return animale.getIdentificativo();
		}
		else
		{
			if(cartellaClinica!=null && cartellaClinica.getAccettazione()!=null && cartellaClinica.getAccettazione().getAnimale()!=null)
				return cartellaClinica.getAccettazione().getAnimale().getIdentificativo();
		}
		return "";
	
	}
	
	@Transient
	public String getNumeroRifMittente() 
	{
		return getTipoAccettazione()+"-"+getNumeroAccettazioneSigla();
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull

//	public BUtenteAll getEnteredBy() {
//		return this.enteredBy;
//	}
	
	public BUtente getEnteredBy() {
		return UtenteDAO.getUtente(enteredBy.getId());
	}

	public void setEnteredBy(BUtente enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = UtenteDAO.getUtenteAll(enteredBy.getId());
	}
	
	
	public void setEnteredBy(BUtenteAll enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = enteredBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtenteAll getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	UtenteDAO.getUtenteAll(modifiedBy.getId());
	}
	
	
	public void setModifiedBy(BUtenteAll modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	modifiedBy;
	}
}
