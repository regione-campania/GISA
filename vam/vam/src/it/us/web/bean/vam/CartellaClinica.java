/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupEventoAperturaCc;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.util.vam.ComparatorTrasferimenti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cartella_clinica", schema = "public")
@Where(clause = "trashed_date is null")

//@SecondaryTables({
//    @SecondaryTable(name="utenti_", pkJoinColumns={
//        @PrimaryKeyJoinColumn(name="entered_by", referencedColumnName="id"),
//        @PrimaryKeyJoinColumn(name="modified_by", referencedColumnName="id")}
//    )
//})

public class CartellaClinica implements java.io.Serializable {
	private static final long serialVersionUID = -2430940309640415238L;

	private int id;
	private Integer progressivo;
	private String numero;
	private FascicoloSanitario fascicoloSanitario;
	private Accettazione accettazione;
	private StrutturaClinica strutturaClinica;
	private boolean dayHospital;

	private boolean anamnesiRecenteConosciuta;
	private String anamnesiRecenteDescrizione;

	private Boolean adozioneFuoriAsl = false;
	private Boolean adozioneVersoAssocCanili 	 = false;
	private Date dataApertura;
	private Date dataChiusura;
	private Date dataDecesso;
	private String ricoveroMotivo;
	private Date ricoveroData;
	private String ricoveroSintomatologia;
	private String ricoveroNote;
	private String ricoveroBox;
	private String catturaLuogo;
	private Date catturaData;
	private String catturaPersonaleIntervenuto;
	private String reimmissioneLuogo;
	private Date reimmissione;
	private String diagnosiIngresso;
	private String diagnosiDefinitiva;
	private String diagnosiUscita;
	private String terapiaPrescritta;
	private Date terapiaPrescrittaData;
	private String terapiaPrescrittaNote;
	private String note;
	private Boolean ccPostTrasferimento = false;
	private Boolean ccRiconsegna = false;
	private Boolean ccPostTrasferimentoMorto = false;
	private Boolean ccMorto = false;
	private String peso;
	private Set<AttivitaBdr> attivitaBdrs = new HashSet<AttivitaBdr>(0);

	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;
	private BUtenteAll deletedBy;
	private String ruoloEnteredBy;
	
	private Date dimissioniEntered;
	private BUtenteAll dimissioniEnteredBy;


	private Set<EsameCoprologico> esameCoprologicos = new HashSet<EsameCoprologico>(
			0);
	private Set<Sterilizzazione> sterilizzaziones = new HashSet<Sterilizzazione>(
			0);
	private Set<TerapiaDegenza> tarapiaDegenzas = new HashSet<TerapiaDegenza>(0);
	private Set<EsameSangue> esameSangues = new HashSet<EsameSangue>(0);
	private Set<EcoAddome> ecoAddomes = new HashSet<EcoAddome>(0);
	private Set<EcoCuore> ecoCuores = new HashSet<EcoCuore>(0);
	
	private Set<Tac> tacs = new HashSet<Tac>(0);
	private Set<Rx> rxes = new HashSet<Rx>(0);
	private Set<TipoIntervento> tipoInterventi = new HashSet<TipoIntervento>(0);
	
	private Set<OperazioneChirurgica> operazioneChirurgicas = new HashSet<OperazioneChirurgica>(
			0);
	private Set<Ecg> ecgs = new HashSet<Ecg>(0);
	private Set<EsameUrine> esamiUrine = new HashSet<EsameUrine>(0);
	private Set<EsameObiettivo> esameObiettivos = new HashSet<EsameObiettivo>(0);
	private Set<Diagnosi> diagnosis = new HashSet<Diagnosi>(0);
	private Set<Febbre> febbres = new HashSet<Febbre>(0);
	private Set<LookupAlimentazioni> lookupAlimentazionis = new HashSet<LookupAlimentazioni>(
			0);
	private Set<LookupHabitat> lookupHabitats = new HashSet<LookupHabitat>(0);
	private Set<LookupFerite> lookupFerite = new HashSet<LookupFerite>(0);
	private Set<EsameIstopatologico> esameIstopatologicos = new HashSet<EsameIstopatologico>(
			0);
	private Set<DiarioClinico> diarioClinico = new HashSet<DiarioClinico>(0);
	private Set<Trasferimento> trasferimenti = new HashSet<Trasferimento>(0);
	private Set<Trasferimento> trasferimentiByCcPostTrasf = new HashSet<Trasferimento>(
			0);
	private Set<Trasferimento> trasferimentiByCcMortoPostTrasf = new HashSet<Trasferimento>(
			0);
	private Set<Trasferimento> trasferimentiByCcPostRiconsegna = new HashSet<Trasferimento>(
			0);

	private Set<Leishmaniosi> leishmaniosis = new HashSet<Leishmaniosi>(0);
	private Set<Ehrlichiosi> ehrlichiosis = new HashSet<Ehrlichiosi>(0);
	private Set<Rickettsiosi> rickettsiosis = new HashSet<Rickettsiosi>(0);
	private Set<Toxoplasmosi> toxoplasmosis = new HashSet<Toxoplasmosi>(0);
	private Set<Rabbia> rabbias = new HashSet<Rabbia>(0);
	private Set<Fip> fips = new HashSet<Fip>(0);
	private Set<Fiv> fivs = new HashSet<Fiv>(0);
	private Set<Felv> felvs = new HashSet<Felv>(0);
	private Set<Covid> covids = new HashSet<Covid>(0);
	private Set<EsameCitologico> esameCitologicos = new HashSet<EsameCitologico>(
			0);

	private LookupDestinazioneAnimale destinazioneAnimale;
	private LookupEventoAperturaCc eventoApertura;

	private Autopsia autopsia;
	private Diagnosi lastDiagnosi = new Diagnosi();
	
	private String numMod5;
	private Date  dataMod5;

	public CartellaClinica() {

	}

	@Transient
	public String getNomeEsame()
	{
		return "Cartella Clinica";
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accettazione")
	@NotNull
	public Accettazione getAccettazione() {
		return this.accettazione;
	}
	
	public void setAccettazione(Accettazione accettazione) {
		this.accettazione = accettazione;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ricovero_struttura")
	public StrutturaClinica getStrutturaClinica() {
		return this.strutturaClinica;
	}

	public void setStrutturaClinica(StrutturaClinica strutturaClinica) {
		this.strutturaClinica = strutturaClinica;
	}

	@Column(name = "day_hospital", nullable = false)
	public boolean isDayHospital() {
		return this.dayHospital;
	}

	public void setDayHospital(boolean dayHospital) {
		this.dayHospital = dayHospital;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_apertura", length = 13)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "data_decesso", length = 13)
	public Date getDataDecesso() {
		return this.dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	@Column(name = "ricovero_motivo")
	@Type(type = "text")
	public String getRicoveroMotivo() {
		return this.ricoveroMotivo;
	}

	public void setRicoveroMotivo(String ricoveroMotivo) {
		this.ricoveroMotivo = ricoveroMotivo;
	}

	@Column(name = "num_mod5")
	@Type(type = "text")
	public String getNumMod5() {
		return this.numMod5;
	}

	public void setNumMod5(String numMod5) {
		this.numMod5 = numMod5;
	}

	@Column(name = "peso")
	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_mod5", length = 13)
	public Date getDataMod5() {
		return this.dataMod5;
	}

	public void setDataMod5(Date dataMod5) {
		this.dataMod5 = dataMod5;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ricovero_data", length = 13)
	public Date getRicoveroData() {
		return this.ricoveroData;
	}

	public void setRicoveroData(Date ricoveroData) {
		this.ricoveroData = ricoveroData;
	}

	@Column(name = "ricovero_sintomatologia")
	@Type(type = "text")
	public String getRicoveroSintomatologia() {
		return this.ricoveroSintomatologia;
	}

	public void setRicoveroSintomatologia(String ricoveroSintomatologia) {
		this.ricoveroSintomatologia = ricoveroSintomatologia;
	}

	@Column(name = "ricovero_note")
	@Type(type = "text")
	public String getRicoveroNote() {
		return this.ricoveroNote;
	}

	public void setRicoveroNote(String ricoveroNote) {
		this.ricoveroNote = ricoveroNote;
	}

	@Column(name = "ricovero_box", length = 64)
	@Length(max = 64)
	public String getRicoveroBox() {
		return this.ricoveroBox;
	}

	public void setRicoveroBox(String ricoveroBox) {
		this.ricoveroBox = ricoveroBox;
	}

	@Column(name = "cattura_luogo", length = 64)
	@Length(max = 64)
	public String getCatturaLuogo() {
		return this.catturaLuogo;
	}

	public void setCatturaLuogo(String catturaLuogo) {
		this.catturaLuogo = catturaLuogo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cattura_data", length = 13)
	public Date getCatturaData() {
		return this.catturaData;
	}

	public void setCatturaData(Date catturaData) {
		this.catturaData = catturaData;
	}

	@Column(name = "anamnesi_recente_conosciuta")
	public boolean isAnamnesiRecenteConosciuta() {
		return anamnesiRecenteConosciuta;
	}

	public void setAnamnesiRecenteConosciuta(boolean anamnesiRecenteConosciuta) {
		this.anamnesiRecenteConosciuta = anamnesiRecenteConosciuta;
	}

	@Column(name = "cc_riconsegna")
	public Boolean getCcRiconsegna() {
		return ccRiconsegna;
	}

	public void setCcRiconsegna(Boolean ccRiconsegna) {
		this.ccRiconsegna = ccRiconsegna;
	}

	@Column(name = "cc_post_trasferimento")
	public Boolean getCcPostTrasferimento() {
		return ccPostTrasferimento;
	}

	public void setCcPostTrasferimento(Boolean ccPostTrasferimento) {
		this.ccPostTrasferimento = ccPostTrasferimento;
	}

	@Column(name = "cc_post_trasferimento_morto")
	public Boolean getCcPostTrasferimentoMorto() {
		return ccPostTrasferimentoMorto;
	}

	public void setCcPostTrasferimentoMorto(Boolean ccPostTrasferimentoMorto) {
		this.ccPostTrasferimentoMorto = ccPostTrasferimentoMorto;
	}

	@Column(name = "anamnesi_recente_descrizione")
	@Type(type = "text")
	public String getAnamnesiRecenteDescrizione() {
		return anamnesiRecenteDescrizione;
	}

	public void setAnamnesiRecenteDescrizione(String anamnesiRecenteDescrizione) {
		this.anamnesiRecenteDescrizione = anamnesiRecenteDescrizione;
	}

	@Column(name = "cattura_personale_intervenuto", length = 64)
	@Length(max = 64)
	public String getCatturaPersonaleIntervenuto() {
		return this.catturaPersonaleIntervenuto;
	}

	public void setCatturaPersonaleIntervenuto(
			String catturaPersonaleIntervenuto) {
		this.catturaPersonaleIntervenuto = catturaPersonaleIntervenuto;
	}

	@Column(name = "reimmissione_luogo", length = 64)
	@Length(max = 64)
	public String getReimmissioneLuogo() {
		return this.reimmissioneLuogo;
	}

	public void setReimmissioneLuogo(String reimmissioneLuogo) {
		this.reimmissioneLuogo = reimmissioneLuogo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reimmissione", length = 13)
	public Date getReimmissione() {
		return this.reimmissione;
	}

	public void setReimmissione(Date reimmissione) {
		this.reimmissione = reimmissione;
	}

	@Column(name = "diagnosi_ingresso")
	public String getDiagnosiIngresso() {
		return this.diagnosiIngresso;
	}

	public void setDiagnosiIngresso(String diagnosiIngresso) {
		this.diagnosiIngresso = diagnosiIngresso;
	}

	@Column(name = "diagnosi_definitiva")
	public String getDiagnosiDefinitiva() {
		return this.diagnosiDefinitiva;
	}

	public void setDiagnosiDefinitiva(String diagnosiDefinitiva) {
		this.diagnosiDefinitiva = diagnosiDefinitiva;
	}

	@Column(name = "diagnosi_uscita")
	public String getDiagnosiUscita() {
		return this.diagnosiUscita;
	}

	public void setDiagnosiUscita(String diagnosiUscita) {
		this.diagnosiUscita = diagnosiUscita;
	}

	@Column(name = "terapia_prescritta")
	public String getTerapiaPrescritta() {
		return this.terapiaPrescritta;
	}

	public void setTerapiaPrescritta(String terapiaPrescritta) {
		this.terapiaPrescritta = terapiaPrescritta;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "terapia_prescritta_data", length = 13)
	public Date getTerapiaPrescrittaData() {
		return this.terapiaPrescrittaData;
	}

	public void setTerapiaPrescrittaData(Date terapiaPrescrittaData) {
		this.terapiaPrescrittaData = terapiaPrescrittaData;
	}

	@Column(name = "terapia_prescritta_note")
	public String getTerapiaPrescrittaNote() {
		return this.terapiaPrescrittaNote;
	}

	public void setTerapiaPrescrittaNote(String terapiaPrescrittaNote) {
		this.terapiaPrescrittaNote = terapiaPrescrittaNote;
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
	@JoinColumn(name = "deleted_by")
	public BUtenteAll getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(BUtente deletedBy) {
	//	this.deletedBy = deletedBy;
		this.deletedBy = UtenteDAO.getUtenteAll(deletedBy.getId());
	}
	
	
	public void setDeletedBy(BUtenteAll deletedBy) {
	//	this.deletedBy = deletedBy;
		this.deletedBy = deletedBy;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dimissioni_entered", length = 29)
	public Date getDimissioniEntered() {
		return this.dimissioniEntered;
	}

	public void setDimissioniEntered(Date dimissioniEntered) {
		this.dimissioniEntered = dimissioniEntered;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dimissioni_entered_by")
	public BUtenteAll getDimissioniEnteredBy() {
		return this.dimissioniEnteredBy;
	}

	public void setDimissioniEnteredBy(BUtente dimissioniEnteredBy) {
		//this.dimissioniEnteredBy = dimissioniEnteredBy;
		if(dimissioniEnteredBy!=null)
			this.dimissioniEnteredBy = UtenteDAO.getUtenteAll(dimissioniEnteredBy.getId());
		else
			this.dimissioniEnteredBy = null;
	}
	
	public void setDimissioniEnteredBy(BUtenteAll dimissioniEnteredBy) {
		this.dimissioniEnteredBy = dimissioniEnteredBy;
		
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<Leishmaniosi> getLeishmaniosis() {
		return leishmaniosis;
	}

	public void setLeishmaniosis(Set<Leishmaniosi> leishmaniosis) {
		this.leishmaniosis = leishmaniosis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<Ehrlichiosi> getEhrlichiosis() {
		return ehrlichiosis;
	}

	public void setEhrlichiosis(Set<Ehrlichiosi> ehrlichiosis) {
		this.ehrlichiosis = ehrlichiosis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<Rickettsiosi> getRickettsiosis() {
		return rickettsiosis;
	}

	public void setRickettsiosis(Set<Rickettsiosi> rickettsiosis) {
		this.rickettsiosis = rickettsiosis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<EsameCoprologico> getEsameCoprologicos() {
		return this.esameCoprologicos;
	}

	public void setEsameCoprologicos(Set<EsameCoprologico> esameCoprologicos) {
		this.esameCoprologicos = esameCoprologicos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Fip> getFips() {
		return fips;
	}

	public void setFips(Set<Fip> fips) {
		this.fips = fips;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Fiv> getFivs() {
		return fivs;
	}

	public void setFivs(Set<Fiv> fivs) {
		this.fivs = fivs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Felv> getFelvs() {
		return felvs;
	}

	public void setFelvs(Set<Felv> felvs) {
		this.felvs = felvs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Covid> getCovids() {
		return covids;
	}

	public void setCovids(Set<Covid> covids) {
		this.covids = covids;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Toxoplasmosi> getToxoplasmosis() {
		return toxoplasmosis;
	}

	public void setToxoplasmosis(Set<Toxoplasmosi> toxoplasmosis) {
		this.toxoplasmosis = toxoplasmosis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Rabbia> getRabbias() {
		return rabbias;
	}

	public void setRabbias(Set<Rabbia> rabbias) {
		this.rabbias = rabbias;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<EsameCitologico> getEsameCitologicos() {
		return esameCitologicos;
	}

	public void setEsameCitologicos(Set<EsameCitologico> esameCitologicos) {
		this.esameCitologicos = esameCitologicos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<Sterilizzazione> getSterilizzaziones() {
		return this.sterilizzaziones;
	}

	public void setSterilizzaziones(Set<Sterilizzazione> sterilizzaziones) {
		this.sterilizzaziones = sterilizzaziones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<TerapiaDegenza> getTarapiaDegenzas() {
		return this.tarapiaDegenzas;
	}

	public void setTarapiaDegenzas(Set<TerapiaDegenza> tarapiaDegenzas) {
		this.tarapiaDegenzas = tarapiaDegenzas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<EcoAddome> getEcoAddomes() {
		return this.ecoAddomes;
	}

	public void setEcoAddomes(Set<EcoAddome> ecoAddomes) {
		this.ecoAddomes = ecoAddomes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<EcoCuore> getEcoCuores() {
		return this.ecoCuores;
	}

	public void setEcoCuores(Set<EcoCuore> ecoCuores) {
		this.ecoCuores = ecoCuores;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Tac> getTacs() {
		return this.tacs;
	}

	public void setTacs(Set<Tac> tacs) {
		this.tacs = tacs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Rx> getRxes() {
		return this.rxes;
	}

	public void setRxes(Set<Rx> rxes) {
		this.rxes = rxes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<TipoIntervento> getTipoInterventi() {
		return this.tipoInterventi;
	}

	public void setTipoInterventi(Set<TipoIntervento> tipoInterventi) {
		this.tipoInterventi = tipoInterventi;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<EsameSangue> getEsameSangues() {
		return this.esameSangues;
	}

	public void setEsameSangues(Set<EsameSangue> esameSangues) {
		this.esameSangues = esameSangues;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<OperazioneChirurgica> getOperazioneChirurgicas() {
		return this.operazioneChirurgicas;
	}

	public void setOperazioneChirurgicas(
			Set<OperazioneChirurgica> operazioneChirurgicas) {
		this.operazioneChirurgicas = operazioneChirurgicas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Ecg> getEcgs() {
		return this.ecgs;
	}

	public void setEcgs(Set<Ecg> ecgs) {
		this.ecgs = ecgs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<EsameUrine> getEsamiUrine() {
		return this.esamiUrine;
	}

	public void setEsamiUrine(Set<EsameUrine> esamiUrine) {
		this.esamiUrine = esamiUrine;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause="dataRichiesta desc")
	public Set<EsameIstopatologico> getEsameIstopatologicos() {
		return esameIstopatologicos;
	}

	public void setEsameIstopatologicos(
			Set<EsameIstopatologico> esameIstopatologicos) {
		this.esameIstopatologicos = esameIstopatologicos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "data desc, id desc")
	public Set<DiarioClinico> getDiarioClinico() {
		return diarioClinico;
	}

	public void setDiarioClinico(Set<DiarioClinico> diarioClinico) {
		this.diarioClinico = diarioClinico;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Trasferimento> getTrasferimenti() {
		return trasferimenti;
	}

	@Transient
	public ArrayList<Trasferimento> getTrasferimentiOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimenti());
		Collections.sort(trasfList, comp);
		return trasfList;
	}

	public void setTrasferimenti(Set<Trasferimento> trasferimenti) {
		this.trasferimenti = trasferimenti;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinicaDestinatario")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Trasferimento> getTrasferimentiByCcPostTrasf() {
		return trasferimentiByCcPostTrasf;
	}

	@Transient
	public ArrayList<Trasferimento> getTrasferimentiByCcPostTrasfOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimentiByCcPostTrasf());
		Collections.sort(trasfList, comp);
		return trasfList;
	}

	public void setTrasferimentiByCcPostTrasf(
			Set<Trasferimento> trasferimentiByCcPostTrasf) {
		this.trasferimentiByCcPostTrasf = trasferimentiByCcPostTrasf;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinicaMortoDestinatario")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Trasferimento> getTrasferimentiByCcMortoPostTrasf() {
		return trasferimentiByCcMortoPostTrasf;
	}

	@Transient
	public ArrayList<Trasferimento> getTrasferimentiByCcMortoPostTrasfOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimentiByCcMortoPostTrasf());
		Collections.sort(trasfList, comp);
		return trasfList;
	}

	public void setTrasferimentiByCcMortoPostTrasf(
			Set<Trasferimento> trasferimentiByCcMortoPostTrasf) {
		this.trasferimentiByCcMortoPostTrasf = trasferimentiByCcMortoPostTrasf;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinicaMittenteRiconsegna")
	@Where(clause = "trashed_date is null")
	@OrderBy(clause = "dataRichiesta desc, id desc")
	public Set<Trasferimento> getTrasferimentiByCcPostRiconsegna() {
		return trasferimentiByCcPostRiconsegna;
	}

	@Transient
	public ArrayList<Trasferimento> getTrasferimentiByCcPostRiconsegnaOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimentiByCcPostRiconsegna());
		Collections.sort(trasfList, comp);
		return trasfList;
	}

	public void setTrasferimentiByCcPostRiconsegna(
			Set<Trasferimento> trasferimentiByCcPostRiconsegna) {
		this.trasferimentiByCcPostRiconsegna = trasferimentiByCcPostRiconsegna;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	@Where(clause = "trashed_date is null")
	public Set<EsameObiettivo> getEsameObiettivos() {
		return this.esameObiettivos;
	}

	public void setEsameObiettivos(Set<EsameObiettivo> esameObiettivos) {
		this.esameObiettivos = esameObiettivos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	public Set<Febbre> getFebbres() {
		return this.febbres;
	}

	public void setFebbres(Set<Febbre> febbres) {
		this.febbres = febbres;
	}

	@Column(name = "progressivo")
	@NotNull
	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	@Column(name = "numero")
	@NotNull
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartellaClinica")
	public Set<Diagnosi> getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(Set<Diagnosi> diagnosis) {
		this.diagnosis = diagnosis;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autopsia")
	public Autopsia getAutopsia() {
		return autopsia;
	}

	public void setAutopsia(Autopsia autopsia) {
		this.autopsia = autopsia;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_alimentazioni", schema = "public", joinColumns = { @JoinColumn(name = "cc", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "alimentazione", nullable = false, updatable = false) })
	public Set<LookupAlimentazioni> getLookupAlimentazionis() {
		return lookupAlimentazionis;
	}

	public void setLookupAlimentazionis(
			Set<LookupAlimentazioni> lookupAlimentazionis) {
		this.lookupAlimentazionis = lookupAlimentazionis;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_habitat", schema = "public", joinColumns = { @JoinColumn(name = "cc", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "habitat", nullable = false, updatable = false) })
	public Set<LookupHabitat> getLookupHabitats() {
		return lookupHabitats;
	}

	public void setLookupHabitats(Set<LookupHabitat> lookupHabitats) {
		this.lookupHabitats = lookupHabitats;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_ferite", schema = "public", joinColumns = { @JoinColumn(name = "cc", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ferite", nullable = false, updatable = false) })
	public Set<LookupFerite> getLookupFerite() {
		return lookupFerite;
	}

	public void setLookupFerite(Set<LookupFerite> lookupFerite) {
		this.lookupFerite = lookupFerite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destinazione_animale")
	public LookupDestinazioneAnimale getDestinazioneAnimale() {
		return destinazioneAnimale;
	}

	public void setDestinazioneAnimale(
			LookupDestinazioneAnimale destinazioneAnimale) {
		this.destinazioneAnimale = destinazioneAnimale;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento_apertura")
	public LookupEventoAperturaCc getEventoApertura() {
		return eventoApertura;
	}

	public void setEventoApertura(LookupEventoAperturaCc eventoApertura) {
		this.eventoApertura = eventoApertura;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public Diagnosi getLastDiagnosi() {
		Set<Diagnosi> setDiagnosi = this.diagnosis;

		Diagnosi currentDiagnosi;

		if (setDiagnosi.size() > 1) {

			Iterator listaDiagnosi = setDiagnosi.iterator();

			lastDiagnosi = (Diagnosi) listaDiagnosi.next();

			while (listaDiagnosi.hasNext()) {

				currentDiagnosi = (Diagnosi) listaDiagnosi.next();

				if (currentDiagnosi.getDataDiagnosi().compareTo(
						lastDiagnosi.getDataDiagnosi()) == 0
						&& currentDiagnosi.getId() > lastDiagnosi.getId()) {
					lastDiagnosi = currentDiagnosi;
				}
				if (currentDiagnosi.getDataDiagnosi().compareTo(
						lastDiagnosi.getDataDiagnosi()) > 0) {
					lastDiagnosi = currentDiagnosi;
				}

			}
			return lastDiagnosi;
		}

		else if (setDiagnosi.size() == 1) {

			Iterator listaDiagnosi = setDiagnosi.iterator();
			lastDiagnosi = (Diagnosi) listaDiagnosi.next();

			return lastDiagnosi;
		}

		return lastDiagnosi;

	}

	@Transient
	public Set<EsameObiettivo> getEsamiObiettivoApparato(
			LookupEsameObiettivoApparati apparato) {
		Set<EsameObiettivo> ret = new HashSet<EsameObiettivo>();

		for (EsameObiettivo eo : esameObiettivos) {
			if ((eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati()==null && apparato==null) || (eo.getLookupEsameObiettivoTipo()!=null && eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati()!=null && apparato!=null &&  eo.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati().getId() == apparato.getId())) {
				ret.add(eo);
			}
		}

		return ret;
	}

	@Transient
	public List<EsameInterface> getQuadroEsami() {
		List<EsameInterface> ret = new ArrayList<EsameInterface>();

		ret.addAll(getEsameCoprologicos());
		ret.addAll(getEcoCuores());
		ret.addAll(getEcoAddomes());
		ret.addAll(getEsameSangues());
		ret.addAll(getEsamiUrine());
		ret.addAll(getEcgs());
		ret.addAll(getEsameIstopatologicos());
		ret.addAll(getEhrlichiosis());
		ret.addAll(getLeishmaniosis());
		ret.addAll(getRickettsiosis());
		ret.addAll(getToxoplasmosis());
		ret.addAll(getRabbias());
		ret.addAll(getFips());
		ret.addAll(getFivs());
		ret.addAll(getFelvs());
		ret.addAll(getEsameCitologicos());
		ret.addAll(getTacs());
		ret.addAll(getRxes());
		ret.addAll(getTipoInterventi());
		//ADD NEW

		Collections.sort(ret, new EsamiComparator());

		return ret;
	}

	public class EsamiComparator implements Comparator<EsameInterface> {
		@Override
		public int compare(EsameInterface arg0, EsameInterface arg1) {
			int ret = 0;

			ret = arg1.getDataRichiesta().compareTo(arg0.getDataRichiesta());
			if (ret == 0) {
				ret = arg1.getEntered().compareTo(arg0.getEntered());
			}

			return ret;
		}
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fascicolo_sanitario")
	public FascicoloSanitario getFascicoloSanitario() {
		return this.fascicoloSanitario;
	}

	public void setFascicoloSanitario(FascicoloSanitario fascicoloSanitario) {
		this.fascicoloSanitario = fascicoloSanitario;
	}

	@Column(name = "cc_morto")
	public Boolean getCcMorto() {
		return (ccMorto == null) ? (false) : (ccMorto);
	}

	public void setCcMorto(Boolean ccMorto) {
		this.ccMorto = ccMorto;
	}

	@Transient
	public boolean getExistTrasferimentoPerNecroscopia() {
		Iterator<Trasferimento> iter = null;
		if (getCcPostTrasferimento())
			iter = getTrasferimentiByCcPostTrasf().iterator();
		else if (getCcPostTrasferimentoMorto())
			iter = getTrasferimentiByCcMortoPostTrasf().iterator();

		if (iter != null) {
			while (iter.hasNext()) {
				Trasferimento t = iter.next();
				Iterator<LookupOperazioniAccettazione> iter2 = null;
				iter2 = t.getOperazioniRichieste().iterator();
				while (iter2.hasNext()) {
					LookupOperazioniAccettazione op = iter2.next();
					if (op.getId() == IdRichiesteVarie.esameNecroscopico)
						return true;
				}
			}
		}
		return false;
	}
	
	@Transient
	public boolean getEsisteTrasfAutomaticoPerNecroscopia() 
	{
		Iterator<Trasferimento> iter = getTrasferimenti().iterator();
		if (iter!=null)
		{
			while (iter.hasNext())
			{
				Trasferimento t = iter.next();
				if(t.getAutomaticoPerNecroscopia())
					return true;
			}
		}
		return false;
	}
	
	@Transient
	public boolean getEsisteTrasfByCcPostTrasfAutomaticoPerNecroscopia() 
	{
		Iterator<Trasferimento> iter = getTrasferimentiByCcMortoPostTrasf().iterator();
		if (iter!=null)
		{
			while (iter.hasNext())
			{
				Trasferimento t = iter.next();
				if(t.getAutomaticoPerNecroscopia())
					return true;
			}
		}
		return false;
	}
	
	@Column(name = "ruolo_entered_by")
	@Type(type = "text")
	public String getRuoloEnteredBy() {
		return this.ruoloEnteredBy;
	}

	public void setRuoloEnteredBy(String ruoloEnteredBy) {
		this.ruoloEnteredBy = ruoloEnteredBy;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cc")
	@Where( clause = "trashed_date is null" )
	public Set<AttivitaBdr> getAttivitaBdrs() {
		return this.attivitaBdrs;
	}

	public void setAttivitaBdrs(Set<AttivitaBdr> attivitaBdrs) {
		this.attivitaBdrs = attivitaBdrs;
	}
	
	@Column(name = "adozione_fuori_asl")
	public Boolean getAdozioneFuoriAsl() {
		return this.adozioneFuoriAsl;
	}

	public void setAdozioneFuoriAsl(Boolean adozioneFuoriAsl) {
		this.adozioneFuoriAsl = adozioneFuoriAsl;
	}
	
	@Column(name = "adozione_verso_assoc_canili")
	public Boolean getAdozioneVersoAssocCanili() {
		return this.adozioneVersoAssocCanili;
	}

	public void setAdozioneVersoAssocCanili(Boolean adozioneVersoAssocCanili) {
		this.adozioneVersoAssocCanili = adozioneVersoAssocCanili;
	}
	
	@Override
	public String toString()
	{
		return getNumero();
	}
	
	@Transient
	public String getTipologia() 
	{
		if(getCcMorto()!=null && getCcMorto())
		{
			return "Necroscopica";
		}
		else
		{
			
			if(dayHospital)
			{
				return "Day Hospital";
			}
			else
			{
				return "Degenza";
			}
				
		}
	}
	
	
	@Transient
	public boolean getCancellabile()
	{
//		if(getAnimale().getDataSmaltimentoCarogna()!=null)
//			return false;
		if(attivitaBdrs.size() > 0)
			return false;
		
		return true;
	}
}