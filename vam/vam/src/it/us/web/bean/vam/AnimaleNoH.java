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

public class AnimaleNoH implements java.io.Serializable
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
	private Set<AccettazioneNoH> accettaziones = new HashSet<AccettazioneNoH>(0);

	private Set<EsameIstopatologico> esameIstopatologicos = new HashSet<EsameIstopatologico>(0);
	
	
	public AnimaleNoH()
	{
		
	}
	
	public String getNomeEsame()
	{
		return "Animale";
	}

	public int getAccettazioneSize()
	{
		return (accettaziones == null) ? (0) : (accettaziones.size());
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LookupSpecie getLookupSpecie() {
		return this.lookupSpecie;
	}

	public void setLookupSpecie(LookupSpecie lookupSpecie) {
		this.lookupSpecie = lookupSpecie;
	}
	
	public Integer getTaglia() {
		return this.taglia;
	}

	public void setTaglia(Integer taglia) {
		this.taglia = taglia;
	}

	public String getIdentificativo() {
		return this.identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	
	public String getTatuaggio() {
		return this.tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public Integer getRazza() {
		return razza;
	}
	public void setRazza(Integer razza) {
		this.razza = razza;
	}
	
	public Integer getMantello() {
		return mantello;
	}
	public void setMantello(Integer mantello) {
		this.mantello = mantello;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public void setInColonia(Boolean inColonia) {
		this.inColonia = inColonia;
	}
	
	public Boolean getInColonia() {
		return this.inColonia;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	

	public Date getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Boolean getDataMortePresunta() {
		return dataMortePresunta;
	}

	public void setDataMortePresunta(Boolean dataMortePresunta) {
		this.dataMortePresunta = dataMortePresunta;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	public Integer getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getDecedutoNonAnagrafe() {
		return decedutoNonAnagrafe;
	}

	public void setDecedutoNonAnagrafe(Boolean decedutoNonAnagrafe) {
		this.decedutoNonAnagrafe = decedutoNonAnagrafe;
	}

	public Boolean getDataNascitaPresunta() {
		return dataNascitaPresunta;
	}

	public void setDataNascitaPresunta(Boolean dataNascitaPresunta) {
		this.dataNascitaPresunta = dataNascitaPresunta;
	}
	
	public Boolean getNecroscopiaNonEffettuabile() {
		return necroscopiaNonEffettuabile;
	}

	public void setNecroscopiaNonEffettuabile(Boolean necroscopiaNonEffettuabile) {
		this.necroscopiaNonEffettuabile = necroscopiaNonEffettuabile;
	}
	
	public String getRazzaSinantropo() {
		return this.razzaSinantropo;
	}

	public void setRazzaSinantropo(String razzaSinantropo) {
		this.razzaSinantropo = razzaSinantropo;
	}
	
	public String getMantelloSinantropo() {
		return this.mantelloSinantropo;
	}

	public void setMantelloSinantropo(String mantelloSinantropo) {
		this.mantelloSinantropo = mantelloSinantropo;
	}
	
	public String getSpecieSinantropo() {
		return this.specieSinantropo;
	}

	public void setSpecieSinantropo(String specieSinantropo) {
		this.specieSinantropo = specieSinantropo;
	}

	public Set<AccettazioneNoH> getAccettaziones() {
		return this.accettaziones;
	}

	public void setAccettaziones(Set<AccettazioneNoH> accettaziones) {
		this.accettaziones = accettaziones;
	}
	
	public Set<EsameIstopatologico> getEsameIstopatologicos() {
		return esameIstopatologicos;
	}

	public void setEsameIstopatologicos(
			Set<EsameIstopatologico> esameIstopatologicos) {
		this.esameIstopatologicos = esameIstopatologicos;
	}
	
	public LookupCMI getCausaMorte() {
		return causaMorte;
	}

	public void setCausaMorte(LookupCMI causaMorte) {
		this.causaMorte = causaMorte;
	}
	
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
	
	public Clinica getClinicaChippatura() {
		return clinicaChippatura;
	}

	public void setClinicaChippatura(Clinica clinicaChippatura) {
		this.clinicaChippatura = clinicaChippatura;
	}
	
	public Date getDataSmaltimentoCarogna() {
		return this.dataSmaltimentoCarogna;
	}

	public void setDataSmaltimentoCarogna(Date dataSmaltimentoCarogna) {
		this.dataSmaltimentoCarogna = dataSmaltimentoCarogna;
	}
	
	public String getDdt() {
		return ddt;
	}

	public void setDdt(String ddt) {
		this.ddt = ddt;
	}
	
	public String getDittaAutorizzata() {
		return dittaAutorizzata;
	}

	public void setDittaAutorizzata(String dittaAutorizzata) {
		this.dittaAutorizzata = dittaAutorizzata;
	}
	
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
	public AccettazioneNoH getAccettazioneConOpDaCompletare()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
			if(!accettazione.getOperazioniRichiesteBdrNonEseguite().isEmpty())
				return accettazione;
		}
		return null;
	}
	
	public AccettazioneNoH getAccettazionePiuRecenteByClinica(int idClinica)
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		AccettazioneNoH ret = null;
		
		while( accettazioni.hasNext())
		{
			AccettazioneNoH acc = accettazioni.next();
			if(ret==null && acc.getEnteredBy().getClinica().getId()==idClinica)
				ret = acc;
			else if(  ret!=null && acc.getEnteredBy().getClinica().getId()==idClinica && acc.getData().after(ret.getData()) )
				ret = acc;
		}
		return ret;
	}
	
	//Ritorna l'accettazione, se esiste, per cui si devono ancora eseguire delle operazioni richieste in bdr
	public AccettazioneNoH getAccettazionePerSmaltimento()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
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
	public AccettazioneNoH getAccettazioneConCcDaAprire()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
			if(accettazione.getAprireCartellaClinica())
				return accettazione;
		}
		return null;
	}
	
	//Ritorna l'accettazione, se esiste, per cui deve essere ancora aperta la cartella clinica
	public Autopsia getEsameNecroscopico()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
			Iterator<CartellaClinicaNoH> cc = accettazione.getCartellaClinicas().iterator();
			while(cc.hasNext())
			{
				CartellaClinicaNoH ccTemp = cc.next();
				if(ccTemp.getAutopsia()!=null)
					return ccTemp.getAutopsia();
			}
		}
		return null;
	}

	public String getNoteRitrovamento() {
		return noteRitrovamento;
	}
	public void setNoteRitrovamento(String noteRitrovamento) {
		this.noteRitrovamento = noteRitrovamento;
	}
	
	public LookupComuni getComuneRitrovamento() {
		return comuneRitrovamento;
	}
	public void setComuneRitrovamento(LookupComuni comuneRitrovamento) {
		this.comuneRitrovamento = comuneRitrovamento;
	}
	
	public String getProvinciaRitrovamento() {
		return provinciaRitrovamento;
	}
	public void setProvinciaRitrovamento(String provinciaRitrovamento) {
		this.provinciaRitrovamento = provinciaRitrovamento;
	}
	
	public String getComuneProvinciaRitrovamento() {
		if(comuneRitrovamento!=null)
			return comuneRitrovamento.getDescription() + " (" + provinciaRitrovamento + ")";
		else
			return "";
	}
	
	public String getIndirizzoRitrovamento() {
		return indirizzoRitrovamento;
	}
	public void setIndirizzoRitrovamento(String indirizzoRitrovamento) {
		this.indirizzoRitrovamento = indirizzoRitrovamento;
	}
	
	public boolean getRandagio()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
			if(accettazione.getRandagio()!=null && accettazione.getRandagio())
				return true;
		}
		return false;
		
	}
	
	
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
	public String getNumAccNecroscopicoIstoPrecedente()
	{
		Iterator<AccettazioneNoH> accettazioni = getAccettaziones().iterator();
		while(accettazioni.hasNext())
		{
			AccettazioneNoH accettazione = accettazioni.next();
			Iterator<CartellaClinicaNoH> cartelle = accettazione.getCartellaClinicas().iterator();
			while(cartelle.hasNext())
			{
				CartellaClinicaNoH cc = cartelle.next();
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
	
	public String toString()
	{
		return identificativo + " " + tatuaggio;
	}
	
}
