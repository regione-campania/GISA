/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaModalitaConservazione;
import it.us.web.bean.vam.lookup.LookupComuni;


import it.us.web.dao.UtenteDAO;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "autopsia", schema = "public")
@Where( clause = "trashed_date is null" )
public class Autopsia implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Integer progressivo;
	private Date trashedDate;
	private CartellaClinica cartellaClinica;
	
	private String numeroScheda;
	private String numeroAccettazioneSigla;
	private String tipoAccettazione;

	private String esameEsterno;	
	private String cavitaAddominale;
	private String cavitaToracica;
	private String cavitaPelvica;
	private String cavitaOrale;
	
	private String diagnosiAnatomoPatologica;
	private String diagnosiDefinitiva;
	
	private String note;
	private Date dataAutopsia;
	private Date dataEsito;
	private Date dataMorte;
	private boolean dataMorteCerta;
	
	private Date entered;
	private Date modified;	
//	private BUtente enteredBy;
//	private BUtente modifiedBy;	
	
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;
	
	private Date enteredEsito;
	private Date modifiedEsito;	
	private BUtenteAll enteredByEsito;
	private BUtenteAll modifiedByEsito;
	
	private Set<SuperUtente> operatori = new HashSet<SuperUtente>(0);	
	
	private LookupAutopsiaModalitaConservazione lmc;
	private LookupAutopsiaModalitaConservazione lmcRichiesta;
	private LookupAutopsiaSalaSettoria lass;

	private Float temperaturaConservazione;
	private Float temperaturaConservazioneRichiesta;
	
	private LookupCMI motivoFinaleDecesso;
	private LookupAutopsiaPatologiePrevalenti patologiaDefinitiva;
	
	
	private Set<LookupAutopsiaFenomeniCadaverici> fenomeniCadaverici 		= new HashSet<LookupAutopsiaFenomeniCadaverici>(0);	
	private Set<AutopsiaOrganoPatologie> 		aops 			= new HashSet<AutopsiaOrganoPatologie>(0);	
	private Set<AutopsiaOrganiTipiEsamiEsiti>   dettaglioEsami  = new HashSet<AutopsiaOrganiTipiEsamiEsiti>(0);
	private Set<LookupCMI> 						lookupCMIs 		= new HashSet<LookupCMI>(0);
	
	//private Set<LookupCMF> lookupCMFs = new HashSet<LookupCMF>(0);
	private Set<AutopsiaCMF> 					cmf 			= new HashSet<AutopsiaCMF>(0);
	
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
	
	@Column(name = "progressivo", nullable = false)
	@NotNull
	public Integer getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}
	
	@Column(name = "numero")	
	public String getNumeroScheda() {
		return this.numeroScheda;
	}

	public void setNumeroScheda(String numeroScheda) {
		this.numeroScheda = numeroScheda;
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
	
	@Column(name = "esame_esterno")
	@Type(type = "text")
	public String getEsameEsterno() {
		return esameEsterno;
	}
	public void setEsameEsterno(String esameEsterno) {
		this.esameEsterno = esameEsterno;
	}
	
	@Column(name = "cavita_addominale")
	@Type(type = "text")
	public String getCavitaAddominale() {
		return cavitaAddominale;
	}
	
	public void setCavitaAddominale(String cavitaAddominale) {
		this.cavitaAddominale = cavitaAddominale;
	}
	
	@Column(name = "cavita_toracica")
	@Type(type = "text")
	public String getCavitaToracica() {
		return cavitaToracica;
	}
	
	public void setCavitaToracica(String cavitaToracica) {
		this.cavitaToracica = cavitaToracica;
	}
	
	@Column(name = "cavita_pelvica")
	@Type(type = "text")
	public String getCavitaPelvica() {
		return cavitaPelvica;
	}
	
	public void setCavitaPelvica(String cavitaPelvica) {
		this.cavitaPelvica = cavitaPelvica;
	}
	
	@Column(name = "cavita_orale")
	@Type(type = "text")
	public String getCavitaOrale() {
		return cavitaOrale;
	}
	public void setCavitaOrale(String cavitaOrale) {
		this.cavitaOrale = cavitaOrale;
	}
		
	@Column(name = "diagnosi_anatomo_patologica")
	@Type(type = "text")
	public String getDiagnosiAnatomoPatologica() {
		return diagnosiAnatomoPatologica;
	}
	public void setDiagnosiAnatomoPatologica(String diagnosiAnatomoPatologica) {
		this.diagnosiAnatomoPatologica = diagnosiAnatomoPatologica;
	}
	
	@Column(name = "diagnosi_definitva")
	@Type(type = "text")
	public String getDiagnosiDefinitiva() {
		return diagnosiDefinitiva;
	}
	public void setDiagnosiDefinitiva(String diagnosiDefinitiva) {
		this.diagnosiDefinitiva = diagnosiDefinitiva;
	}
	
	@Column(name = "note")
	@Type(type = "text")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
				
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_autopsia", length = 13)
	@NotNull
	public Date getDataAutopsia() {
		return dataAutopsia;
	}
	public void setDataAutopsia(Date dataAutopsia) {
		this.dataAutopsia = dataAutopsia;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito", length = 13)
	public Date getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_morte", length = 13)	
	public Date getDataMorte() {
		return dataMorte;
	}
	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}
	
	@Column(name = "data_morte_certa", nullable = false)	
	public boolean isDataMorteCerta() {
		return this.dataMorteCerta;
	}

	public void setDataMorteCerta(boolean dataMorteCerta) {
		this.dataMorteCerta = dataMorteCerta;
	}
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 13)
	@NotNull
	public Date getEntered() {
		return entered;
	}
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 13)
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_esito", length = 13)
	public Date getModifiedEsito() {
		return modifiedEsito;
	}
	public void setModifiedEsito(Date modifiedEsito) {
		this.modifiedEsito = modifiedEsito;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered_esito", length = 13)
	public Date getEnteredEsito() {
		return enteredEsito;
	}
	public void setEnteredEsito(Date enteredEsito) {
		this.enteredEsito = enteredEsito;
	}
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "entered_by")
//	@NotNull	
//	public BUtente getEnteredBy() {
//		return enteredBy;
//	}
//	public void setEnteredBy(BUtente enteredBy) {
//		this.enteredBy = enteredBy;
//	}
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "modified_by")
//	@NotNull	
//	public BUtente getModifiedBy() {
//		return modifiedBy;
//	}
//	public void setModifiedBy(BUtente modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "autopsia_operatori", schema = "public", joinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operatore", nullable = false, updatable = false) })
	public Set<SuperUtente> getOperatori() {
		return operatori;
	}
	public void setOperatori(Set<SuperUtente> operatori) {
		this.operatori = operatori;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	
	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}
		

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala_settoria")
	public LookupAutopsiaSalaSettoria getLass() {
		return lass;
	}
	public void setLass(LookupAutopsiaSalaSettoria lass) {
		this.lass = lass;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modalita_conservazione")
	public LookupAutopsiaModalitaConservazione getLmc() {
		return lmc;
	}
	public void setLmc(LookupAutopsiaModalitaConservazione lmc) {
		this.lmc = lmc;
	}
	
	
	@Column(name = "temperatura_conservazione")
	public Float getTemperaturaConservazione() {
		return temperaturaConservazione;
	}
	public void setTemperaturaConservazione(Float temperaturaConservazione) {
		this.temperaturaConservazione = temperaturaConservazione;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modalita_conservazione_richiesta")
	public LookupAutopsiaModalitaConservazione getLmcRichiesta() {
		return lmcRichiesta;
	}
	public void setLmcRichiesta(LookupAutopsiaModalitaConservazione lmcRichiesta) {
		this.lmcRichiesta = lmcRichiesta;
	}
	
	
	@Column(name = "temperatura_conservazione_richiesta")
	public Float getTemperaturaConservazioneRichiesta() {
		return temperaturaConservazioneRichiesta;
	}
	public void setTemperaturaConservazioneRichiesta(Float temperaturaConservazioneRichiesta) {
		this.temperaturaConservazioneRichiesta = temperaturaConservazioneRichiesta;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "autopsia_causemorteiniziali", schema = "public", joinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "causa_morte", nullable = false, updatable = false) })
	public Set<LookupCMI> getLookupCMIs() {
		return lookupCMIs;
	}
	public void setLookupCMIs(Set<LookupCMI> lookupCMIs) {
		this.lookupCMIs = lookupCMIs;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prevalente_motivo_decesso")
	public LookupCMI getMotivoFinaleDecesso() {
		return motivoFinaleDecesso;
	}
	public void setMotivoFinaleDecesso(LookupCMI motivoFinaleDecesso) {
		this.motivoFinaleDecesso = motivoFinaleDecesso;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patologia_definitiva")
	public LookupAutopsiaPatologiePrevalenti getPatologiaDefinitiva() {
		return patologiaDefinitiva;
	}
	public void setPatologiaDefinitiva(LookupAutopsiaPatologiePrevalenti patologiaDefinitiva) {
		this.patologiaDefinitiva = patologiaDefinitiva;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "autopsia_fenomeni_cadaverici", schema = "public", joinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "fenomeno_cadaverico", nullable = false, updatable = false) })
	public Set<LookupAutopsiaFenomeniCadaverici> getFenomeniCadaverici() {
		return fenomeniCadaverici;
	}
	public void setFenomeniCadaverici(Set<LookupAutopsiaFenomeniCadaverici> fenomeniCadaverici) {
		this.fenomeniCadaverici = fenomeniCadaverici;
	}
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "autopsia_causemortefinali", schema = "public", joinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "causa_morte", nullable = false, updatable = false) })
//	public Set<LookupCMF> getLookupCMFs() {
//		return lookupCMFs;
//	}
//	public void setLookupCMFs(Set<LookupCMF> lookupCMFs) {
//		this.lookupCMFs = lookupCMFs;
//	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "autopsia")
	@OrderBy(clause="lookupOrganiAutopsia")
	public Set<AutopsiaOrganoPatologie> getAops() {
		return aops;
	}
	public void setAops(Set<AutopsiaOrganoPatologie> aops) {
		this.aops = aops;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "autopsia")	
	public Set<AutopsiaOrganiTipiEsamiEsiti> getDettaglioEsami() {
		return dettaglioEsami;
	}
	public void setDettaglioEsami(Set<AutopsiaOrganiTipiEsamiEsiti> dettaglioEsami) {
		this.dettaglioEsami = dettaglioEsami;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "autopsia")	
	public Set<AutopsiaCMF> getCmf() {
		return cmf;
	}
	public void setCmf(Set<AutopsiaCMF> cmf) {
		this.cmf = cmf;
	}
	
	@Transient
	public HashMap<String, Set<LookupAutopsiaEsitiEsami>> getDettaglioEsamiForJsp() 
	{
		HashMap<String, Set<LookupAutopsiaEsitiEsami>>     dettaglioEsamiForJsp = new HashMap<String, Set<LookupAutopsiaEsitiEsami>>(0);
		Iterator<AutopsiaOrganiTipiEsamiEsiti> iter = dettaglioEsami.iterator();
		while(iter.hasNext())
		{
			AutopsiaOrganiTipiEsamiEsiti ote = iter.next();
			int idOrgano      = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getId();
			int idTipo        = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getId();
			String descOrgano = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getDescription();
			String descTipo   = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getDescription();
			String chiave     = idOrgano+"---"+descOrgano+";"+idTipo+"---"+descTipo;
			if(dettaglioEsamiForJsp.containsKey(chiave))
			{
				Set<LookupAutopsiaEsitiEsami> temp = dettaglioEsamiForJsp.get(chiave);
				dettaglioEsamiForJsp.remove(chiave);
				temp.add(ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito());
				dettaglioEsamiForJsp.put(chiave, temp);
			}
			else
			{
				Set<LookupAutopsiaEsitiEsami> toAdd = new HashSet<LookupAutopsiaEsitiEsami>();
				toAdd.add(ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito());
				dettaglioEsamiForJsp.put(chiave, toAdd);
			}
		}
		
		return dettaglioEsamiForJsp;
	}
	
	@Transient
	public HashMap<String, Set<LookupAutopsiaOrganiTipiEsamiEsiti>> getDettaglioEsamiForJspEdit() 
	{
		HashMap<String, Set<LookupAutopsiaOrganiTipiEsamiEsiti>>     dettaglioEsamiForJsp = new HashMap<String, Set<LookupAutopsiaOrganiTipiEsamiEsiti>>(0);
		Iterator<AutopsiaOrganiTipiEsamiEsiti> iter = dettaglioEsami.iterator();
		while(iter.hasNext())
		{
			AutopsiaOrganiTipiEsamiEsiti ote = iter.next();
			int idOrgano      = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getId();
			int idTipo        = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getId();
			String descOrgano = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getDescription();
			String descTipo   = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getDescription();
			String note       = ote.getNote();
			String chiave     = idOrgano+"---"+descOrgano+";"+idTipo+"---"+descTipo+";"+note;
			if(dettaglioEsamiForJsp.containsKey(chiave))
			{
				Set<LookupAutopsiaOrganiTipiEsamiEsiti> temp = dettaglioEsamiForJsp.get(chiave);
				dettaglioEsamiForJsp.remove(chiave);
				temp.add(ote.getLookupAutopsiaOrganiTipiEsamiEsiti());
				dettaglioEsamiForJsp.put(chiave, temp);
			}
			else
			{
				Set<LookupAutopsiaOrganiTipiEsamiEsiti> toAdd = new HashSet<LookupAutopsiaOrganiTipiEsamiEsiti>();
				toAdd.add(ote.getLookupAutopsiaOrganiTipiEsamiEsiti());
				dettaglioEsamiForJsp.put(chiave, toAdd);
			}
		}
		
		return dettaglioEsamiForJsp;
	}
	
	@Transient
	public HashMap<String, String> getValoriDettaglioEsamiForJspEdit() 
	{
		HashMap<String, String>     valoriDettaglioEsamiForJsp = new HashMap<String, String>(0);
		Iterator<AutopsiaOrganiTipiEsamiEsiti> iter = dettaglioEsami.iterator();
		while(iter.hasNext())
		{
			AutopsiaOrganiTipiEsamiEsiti ote = iter.next();
			int idOrgano      = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getId();
			int idTipo        = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getId();
			String descOrgano = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getDescription();
			String descTipo   = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getDescription();
			String note       = ote.getNote();
			String chiave     = idOrgano+"---"+descOrgano+";"+idTipo+"---"+descTipo+";"+note;
			if(valoriDettaglioEsamiForJsp.containsKey(chiave))
			{
				String temp = valoriDettaglioEsamiForJsp.get(chiave);
				valoriDettaglioEsamiForJsp.remove(chiave);
				temp += "&&&&&" + ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getId()+"###"+ote.getValore();
				valoriDettaglioEsamiForJsp.put(chiave, temp);
			}
			else
			{
				String toAdd = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getId()+"###"+ote.getValore();
				valoriDettaglioEsamiForJsp.put(chiave, toAdd);
			}
		}
		
		return valoriDettaglioEsamiForJsp;
	}
	
	
	
	@Transient
	public HashMap<String, Set<String>> getDettaglioEsamiForJspDetail() 
	{
		HashMap<String, Set<String>>     dettaglioEsamiForJsp = new HashMap<String, Set<String>>(0);
		Iterator<AutopsiaOrganiTipiEsamiEsiti> iter = dettaglioEsami.iterator();
		while(iter.hasNext())
		{
			AutopsiaOrganiTipiEsamiEsiti ote = iter.next();
			int idOrgano      = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getId();
			int idTipo        = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getId();
			String descOrgano = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupOrganiAutopsia().getDescription();
			String descTipo   = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getLookupAutopsiaTipiEsami().getDescription();
			String note       = ote.getNote();
			String chiave     = idOrgano+"---"+descOrgano+";"+idTipo+"---"+descTipo+";"+note;
			if(dettaglioEsamiForJsp.containsKey(chiave))
			{
				Set<String> temp = dettaglioEsamiForJsp.get(chiave);
				dettaglioEsamiForJsp.remove(chiave);
				if(ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito()!=null)
				{
					String toAddString = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito().getDescription();
					if(ote.getValore()!=null && !ote.getValore().equals(""))
					{
						toAddString+="("+ote.getValore()+")";
					}
					temp.add(toAddString);
				}
				dettaglioEsamiForJsp.put(chiave, temp);
			}
			else
			{
				Set<String> toAdd = new HashSet<String>();
				if(ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito()!=null)
				{
					String toAddString = ote.getLookupAutopsiaOrganiTipiEsamiEsiti().getEsito().getDescription();
					if(ote.getValore()!=null && !ote.getValore().equals(""))
					{
						toAddString+="("+ote.getValore()+")";
					}
					toAdd.add(toAddString);
				}
				dettaglioEsamiForJsp.put(chiave, toAdd);
			}
		}
		
		return dettaglioEsamiForJsp;
	}
	
	
	@Transient
	/**
	 * Converte la struttura tirata fuori da getDettaglioEsamiForJspDetail() in una utilizzabile nella stampa del referto, ovvero un ArrayList di oggetti
	 */
	public ArrayList<AutopsiaSezioneDettaglioEsami> getDettaglioEsamiReferto() 
	{
		ArrayList<AutopsiaSezioneDettaglioEsami> toReturn = new ArrayList<AutopsiaSezioneDettaglioEsami>();
		Iterator<Entry<String, Set<String>>> iter = this.getDettaglioEsamiForJspDetail().entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<String, Set<String>> e = iter.next();
			AutopsiaSezioneDettaglioEsami asde = new AutopsiaSezioneDettaglioEsami();
			asde.setOrgano(e.getKey().split(";")[0].split("---")[1]);
			asde.setTipo(e.getKey().split(";")[1].split("---")[1]);
			asde.setDettaglio(((e.getKey().split(";").length>2)?(e.getKey().split(";")[2]):("")));
			asde.setEsiti(e.getValue());
			toReturn.add(asde);
		}
		return toReturn;
	}
	
	
	@Transient
	public String getFenomeniCadavericiReferto()
	{
		String toReturn="";
		for (LookupAutopsiaFenomeniCadaverici var : this.fenomeniCadaverici)
		{
			//Scorro solo i padri
			if(var.getPadre()==null)
				toReturn+=ricorsione(var,0);
		}	
		return toReturn;
	}
	
	
	public String ricorsione(LookupAutopsiaFenomeniCadaverici lafc , int livello )
	{
		String toReturn="";
		
		if( lafc.getPadre()== null )
		{
			if(this.fenomeniCadaverici.contains(lafc))
			{
				for(int i=0;i<=livello;i++)
					toReturn+="  ";
				toReturn+=lafc.getDescription();
			}
			toReturn+="\n";
		}
		
		if(!lafc.getFigli().isEmpty())
		{	
			livello++;
			for(LookupAutopsiaFenomeniCadaverici figlio : lafc.getFigli())
				toReturn+=ricorsione(figlio , livello );
		}
		else
		{
			if(this.fenomeniCadaverici.contains(lafc))
			{
				for(int i=0;i<=livello;i++)
					toReturn+="  ";
				toReturn+=lafc.getDescription();
				toReturn+="\n";
			}
		}
		return toReturn;
	}
	
	
	
	@Transient
	public Set<String> getOperatoriId() 
	{
		Set<String> operatoriId     = new HashSet<String>(0);
		for(SuperUtente temp:this.operatori)
		{
			operatoriId.add(temp.getId()+"");
		}
		return operatoriId;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Necroscopico";
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull

	public BUtenteAll getEnteredBy() {
		return this.enteredBy;
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by_esito")
	public BUtenteAll getEnteredByEsito() {
		return this.enteredByEsito;
	}

	public void setEnteredByEsito(BUtente enteredByEsito) {
		//this.enteredBy = enteredBy;
		this.enteredByEsito = UtenteDAO.getUtenteAll(enteredByEsito.getId());
	}
	
	
	public void setEnteredByEsito(BUtenteAll enteredByEsito) {
		//this.enteredBy = enteredBy;
		this.enteredByEsito = enteredByEsito;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by_esito")
	public BUtenteAll getModifiedByEsito() {
		return this.modifiedByEsito;
	}

	public void setModifiedByEsito(BUtente modifiedByEsito) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedByEsito =	UtenteDAO.getUtenteAll(modifiedByEsito.getId());
	}
	
	
	public void setModifiedByEsito(BUtenteAll modifiedByEsito) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedByEsito =	modifiedByEsito;
	}
	
	
}
