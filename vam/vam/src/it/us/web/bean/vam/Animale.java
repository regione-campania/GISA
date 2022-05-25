/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdRichiesteVarie;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import org.hibernate.criterion.Order;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "animale", schema = "public")
@Where( clause = "trashed_date is null" )
public class Animale implements java.io.Serializable
{
	private static final long serialVersionUID = 3764880202719490784L;
	
	private int id;
	private LookupSpecie lookupSpecie;
	private Integer taglia;
	private String identificativo;
	private String tatuaggio;
	private Integer razza;
	private Integer mantello;
	private String razzaSinantropo;
	private String mantelloSinantropo;
	private String specieSinantropo;
	private String sesso;
	private Boolean decedutoNonAnagrafe = false;
	private Date dataMorte;
	private Boolean dataMortePresunta;
	private LookupCMI causaMorte;
	private Clinica clinicaChippatura;
	private LookupSinantropiEta eta;
	
	private Date dataSmaltimentoCarogna;
	private String ddt;
	private String dittaAutorizzata;
	
	private String noteRitrovamento;
	private LookupComuni comuneRitrovamento;
	private String provinciaRitrovamento;
	private String indirizzoRitrovamento;
	
	private Date dataNascita;
	private Boolean dataNascitaPresunta;
	private String descrizione;
	private String note;
	private Boolean inColonia;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer enteredBy;
	private Integer modifiedBy;
	private Boolean necroscopiaNonEffettuabile = false;
	private Set<Accettazione> accettaziones = new HashSet<Accettazione>(0);

	private Set<EsameIstopatologico> esameIstopatologicos = new HashSet<EsameIstopatologico>(0);
	
	
	public Animale()
	{
		
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Animale";
	}

	@Transient
	public int getAccettazioneSize()
	{
		return (accettaziones == null) ? (0) : (accettaziones.size());
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
	@JoinColumn(name = "specie")
	public LookupSpecie getLookupSpecie() {
		return this.lookupSpecie;
	}

	public void setLookupSpecie(LookupSpecie lookupSpecie) {
		this.lookupSpecie = lookupSpecie;
	}
	
	@Column(name = "taglia")
	public Integer getTaglia() {
		return this.taglia;
	}

	public void setTaglia(Integer taglia) {
		this.taglia = taglia;
	}

	@Column(name = "identificativo", length = 64)
	@Length(max = 64)
	public String getIdentificativo() {
		return this.identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	
	@Column(name = "tatuaggio", length = 64)
	@Length(max = 64)
	public String getTatuaggio() {
		return this.tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	@Column(name = "razza")
	public Integer getRazza() {
		return razza;
	}
	public void setRazza(Integer razza) {
		this.razza = razza;
	}
	
	@Column(name = "mantello")
	public Integer getMantello() {
		return mantello;
	}
	public void setMantello(Integer mantello) {
		this.mantello = mantello;
	}

	@Column(name = "sesso", length = 2)
	@Length(max = 2)
	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public void setInColonia(Boolean inColonia) {
		this.inColonia = inColonia;
	}
	
	@Column(name = "in_colonia")
	public Boolean getInColonia() {
		return this.inColonia;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascita", length = 13)
	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	

	@Temporal(TemporalType.DATE)
	@Column(name = "deceduto_non_anagrafe_data_morte", length = 13)
	public Date getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	@Column(name = "deceduto_non_anagrafe_data_morte_presunta")
	public Boolean getDataMortePresunta() {
		return dataMortePresunta;
	}

	public void setDataMortePresunta(Boolean dataMortePresunta) {
		this.dataMortePresunta = dataMortePresunta;
	}

	@Column(name = "descrizione", length = 256)
	@Length(max = 256)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "note")
	@Type(type="text")
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

	@Column(name = "entered_by")
	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "deceduto_non_anagrafe")
	public Boolean getDecedutoNonAnagrafe() {
		return decedutoNonAnagrafe;
	}

	public void setDecedutoNonAnagrafe(Boolean decedutoNonAnagrafe) {
		this.decedutoNonAnagrafe = decedutoNonAnagrafe;
	}

	@Column(name = "data_nascita_presunta")
	public Boolean getDataNascitaPresunta() {
		return dataNascitaPresunta;
	}

	public void setDataNascitaPresunta(Boolean dataNascitaPresunta) {
		this.dataNascitaPresunta = dataNascitaPresunta;
	}
	
	@Column(name = "necroscopia_non_effettuabile")
	public Boolean getNecroscopiaNonEffettuabile() {
		return necroscopiaNonEffettuabile;
	}

	public void setNecroscopiaNonEffettuabile(Boolean necroscopiaNonEffettuabile) {
		this.necroscopiaNonEffettuabile = necroscopiaNonEffettuabile;
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
	
	@Column(name = "specie_sinantropo")
	@Type(type="text")
	public String getSpecieSinantropo() {
		return this.specieSinantropo;
	}

	public void setSpecieSinantropo(String specieSinantropo) {
		this.specieSinantropo = specieSinantropo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "animale")
	@Where( clause = "trashed_date is null" )
	@OrderBy(clause = "data desc, id desc")
	public Set<Accettazione> getAccettaziones() {
		return this.accettaziones;
	}

	public void setAccettaziones(Set<Accettazione> accettaziones) {
		this.accettaziones = accettaziones;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "animale")
	public Set<EsameIstopatologico> getEsameIstopatologicos() {
		return esameIstopatologicos;
	}

	public void setEsameIstopatologicos(
			Set<EsameIstopatologico> esameIstopatologicos) {
		this.esameIstopatologicos = esameIstopatologicos;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deceduto_non_anagrafe_causa_morte")
	public LookupCMI getCausaMorte() {
		return causaMorte;
	}

	public void setCausaMorte(LookupCMI causaMorte) {
		this.causaMorte = causaMorte;
	}
	
	@Transient
	public String getSessoDescrizione() 
	{
		String ret = "";
		
		if( sesso != null )
		{
			if( sesso.equalsIgnoreCase("M") )
			{
				ret = "Maschile";
			}
			else if( sesso.equalsIgnoreCase("F") )
			{
				ret = "Femminile";
			}
			else
			{
				ret = sesso;
			}
		}

		return ret;
	}
	
	@Transient
	public String getDataMorteCertezza() 
	{
		if(dataMortePresunta!=null)
		{
			if(dataMortePresunta)
				return "Presunta";
			else
				return "Certa";
		}
		else
			return "";
	}
	@Transient
	public String getDataNascitaCertezza() 
	{
		if(dataNascitaPresunta!=null)
		{
			if(dataNascitaPresunta)
				return "Presunta";
			else
				return "Certa";
		}
		else
			return "";
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinica_chippatura")
	public Clinica getClinicaChippatura() {
		return clinicaChippatura;
	}

	public void setClinicaChippatura(Clinica clinicaChippatura) {
		this.clinicaChippatura = clinicaChippatura;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_smaltimento_carogna", length = 13)
	public Date getDataSmaltimentoCarogna() {
		return this.dataSmaltimentoCarogna;
	}

	public void setDataSmaltimentoCarogna(Date dataSmaltimentoCarogna) {
		this.dataSmaltimentoCarogna = dataSmaltimentoCarogna;
	}
	
	@Column(name = "ddt")
	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}
	
	@Column(name = "ditta_autorizzata")
	public String getDittaAutorizzata() {
		return dittaAutorizzata;
	}

	public void setDittaAutorizzata(String dittaAutorizzata) {
		this.dittaAutorizzata = dittaAutorizzata;
	}
	
	@Transient
	public String getSpecieSinantropoString()
	{
		if(this.getSpecieSinantropo()!=null)
		{
			if(this.getSpecieSinantropo().equals("1"))
				return "Uccello";
			else if(this.getSpecieSinantropo().equals("2"))
				return "Mammifero";
			else
				return "Rettile/Anfibio";
		}
		else
			return "";
	}
	
	//Ritorna l'accettazione, se esiste, per cui si devono ancora eseguire delle operazioni richieste in bdr
	@Transient
	public Accettazione getAccettazioneConOpDaCompletare()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			if(!accettazione.getOperazioniRichiesteBdrNonEseguite().isEmpty())
				return accettazione;
		}
		return null;
	}
	
	@Transient
	public Accettazione getAccettazionePiuRecenteByClinica(int idClinica)
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		Accettazione ret = null;
		
		while( accettazioni.hasNext())
		{
			Accettazione acc = accettazioni.next();
			if(ret==null && acc.getEnteredBy().getClinica().getId()==idClinica)
				ret = acc;
			else if(  ret!=null && acc.getEnteredBy().getClinica().getId()==idClinica && acc.getData().after(ret.getData()) )
				ret = acc;
		}
		return ret;
	}
	
	//Ritorna l'accettazione, se esiste, per cui si devono ancora eseguire delle operazioni richieste in bdr
	@Transient
	public Accettazione getAccettazionePerSmaltimento()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			Iterator<LookupOperazioniAccettazione> operazioni = accettazione.getOperazioniRichieste().iterator();
			while(operazioni.hasNext())
			{
				LookupOperazioniAccettazione operazione = operazioni.next();
				if(operazione.getId()==IdRichiesteVarie.smaltimentoCarogna)
					return accettazione;
			}
		}
		return null;
	}
	
	//Ritorna l'accettazione, se esiste, per cui deve essere ancora aperta la cartella clinica
	@Transient
	public Accettazione getAccettazioneConCcDaAprire()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			if(accettazione.getAprireCartellaClinica())
				return accettazione;
		}
		return null;
	}
	
	//Ritorna l'accettazione, se esiste, per cui deve essere ancora aperta la cartella clinica
	@Transient
	public Autopsia getEsameNecroscopico()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			Iterator<CartellaClinica> cc = accettazione.getCartellaClinicas().iterator();
			while(cc.hasNext())
			{
				CartellaClinica ccTemp = cc.next();
				if(ccTemp.getAutopsia()!=null)
					return ccTemp.getAutopsia();
			}
		}
		return null;
	}

	@Column(name = "note_ritrovamento")
	@Type(type = "text")
	public String getNoteRitrovamento() {
		return noteRitrovamento;
	}
	public void setNoteRitrovamento(String noteRitrovamento) {
		this.noteRitrovamento = noteRitrovamento;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune_ritrovamento")
	public LookupComuni getComuneRitrovamento() {
		return comuneRitrovamento;
	}
	public void setComuneRitrovamento(LookupComuni comuneRitrovamento) {
		this.comuneRitrovamento = comuneRitrovamento;
	}
	
	@Column(name = "provincia_ritrovamento")
	public String getProvinciaRitrovamento() {
		return provinciaRitrovamento;
	}
	public void setProvinciaRitrovamento(String provinciaRitrovamento) {
		this.provinciaRitrovamento = provinciaRitrovamento;
	}
	
	@Transient
	public String getComuneProvinciaRitrovamento() {
		if(comuneRitrovamento!=null)
			return comuneRitrovamento.getDescription() + " (" + provinciaRitrovamento + ")";
		else
			return "";
	}
	
	@Column(name = "indirizzo_ritrovamento")
	public String getIndirizzoRitrovamento() {
		return indirizzoRitrovamento;
	}
	public void setIndirizzoRitrovamento(String indirizzoRitrovamento) {
		this.indirizzoRitrovamento = indirizzoRitrovamento;
	}
	
	@Transient
	public boolean getRandagio()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			if(accettazione.getRandagio()!=null && accettazione.getRandagio())
				return true;
		}
		return false;
		
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eta")
	public LookupSinantropiEta getEta() {
		return eta;
	}

	public void setEta(LookupSinantropiEta eta) {
		this.eta = eta;
	}
	
	//Verbale n.67
	//Qualora vengano richiesti uno o più esami istopatologici insieme alla necroscopia, 
	//il “Numero riferimento mittente” inserito la prima volta viene riportato uguale nelle maschere successive
	//Questo metodo restituisce il numero riferimento mittente da visualizzare nella maschera di aggiunta istopatologico e necroscopico
	@Transient
	public String getNumAccNecroscopicoIstoPrecedente()
	{
		Iterator<Accettazione> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			Accettazione accettazione = accettazioni.next();
			Iterator<CartellaClinica> cartelle = accettazione.getCartellaClinicas().iterator();
			while(cartelle.hasNext())
			{
				CartellaClinica cc = cartelle.next();
				Autopsia autopsia = cc.getAutopsia();
				if(autopsia!=null && autopsia.getNumeroAccettazioneSigla()!=null && !autopsia.getNumeroAccettazioneSigla().equals(""))
				{
					return autopsia.getNumeroAccettazioneSigla();
				}
				Iterator<EsameIstopatologico> istopatologici = cc.getEsameIstopatologicos().iterator();
				while(istopatologici.hasNext())
				{
					EsameIstopatologico istopatologico = istopatologici.next();
					if(istopatologico.getNumeroAccettazioneSigla()!=null && !istopatologico.getNumeroAccettazioneSigla().equals(""))
					{
						return istopatologico.getNumeroAccettazioneSigla();
					}
				}
			}
		}
		return "";
		
	}
	
	@Override
	public String toString()
	{
		return identificativo + " " + tatuaggio;
	}
	
}
