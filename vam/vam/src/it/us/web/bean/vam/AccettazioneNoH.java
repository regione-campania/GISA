/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAssociazioni;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.util.DateUtils;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.Restrictions;
import org.hibernate.validator.constraints.Length;

import com.cacheonix.util.array.HashMap;

public class AccettazioneNoH implements java.io.Serializable
{
	private static final long serialVersionUID = 7046648521134189879L;
	private static final DecimalFormat decimalFormat = new DecimalFormat( "00000" );
	
	private int id;
	private AnimaleNoH animale;
	private String aslAnimale;
	private LookupTipiRichiedente lookupTipiRichiedente;
	private Integer progressivo;
	private Date data;
	private String richiedenteNome;
	private String richiedenteCognome;
	private String richiedenteCodiceFiscale;
	private String richiedenteDocumento;
	
	private String richiedenteTelefono;
	private String richiedenteResidenza;
	private String richiedenteTipoProprietario;
	
	private boolean richiedenteProprietario = false;
	private Boolean randagio 	 = false;
	private Boolean adozioneFuoriAsl 	 = false;
	private Boolean adozioneVersoAssocCanili 	 = false;
	private Boolean sterilizzato = false;
	private LookupAsl richiedenteAsl;
	private BUtente richiedenteAslUser;
	private String richiedenteAltro;
	private LookupAssociazioni richiedenteAssociazione;
	private LookupAsl aslRitrovamento;
	private LookupTipoTrasferimento tipoTrasferimento;
	
	private CartellaClinica ccVivo;
	
	private String richiedenteForzaPubblicaComune;
	private String richiedenteForzaPubblicaProvincia;
	private String richiedenteForzaPubblicaComando;
	
	private LookupAccettazioneAttivitaEsterna attivitaEsterna;
	private LookupComuni comuneAttivitaEsterna;
	private String indirizzoAttivitaEsterna;
	
	/*
	 * i dati sul proprietario (e l'asl del cane) sono in "accettazione" invece che in "animale"
	 * perchè nel tempo un animale puo' cambiare proprietario 
	 */
	private String proprietarioNome;
	private String proprietarioCognome;
	private String proprietarioTipo;
	private String proprietarioCodiceFiscale;
	private String proprietarioDocumento;
	private String proprietarioCap;
	private String proprietarioProvincia;
	private String proprietarioComune;
	private String proprietarioIndirizzo;
	private String proprietarioTelefono;
	
	private String descrizione;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
//	private BUtente enteredBy;
//	private BUtente modifiedBy;
	
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;
	
	private Set<AttivitaBdrNoH> attivitaBdrs = new HashSet<AttivitaBdrNoH>(0);
	private Set<CartellaClinicaNoH> cartellaClinicas = new HashSet<CartellaClinicaNoH>(0);
	private Set<LookupOperazioniAccettazione> operazioniRichieste = new HashSet<LookupOperazioniAccettazione>(0);
	private Set<LookupPersonaleInterno> personaleInterno = new HashSet<LookupPersonaleInterno>(0);
	private Set<SuperUtenteAll> personaleAsl     = new HashSet<SuperUtenteAll>(0);
	private String noteRicoveroInCanile;
	private String noteIncompatibilitaAmbientale;
	private String noteAltro;
	private String idAccMultipla;

	public AccettazioneNoH()
	{
		
	}

	public boolean getAprireCartellaClinica()
	{
		boolean ret = false;
		
		if( cartellaClinicas.size() <= 0 )
		{
			Iterator<LookupOperazioniAccettazione> it = operazioniRichieste.iterator();
			
			while( it.hasNext() && !ret )
			{
				LookupOperazioniAccettazione loa = it.next();
				if( !loa.isInbdr() &&  loa.getId()!=IdOperazioniBdr.smaltimentoCarogna)
				{
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	public boolean getAprireCartellaNecroscopica()
	{
		boolean ret = false;
		
		if( cartellaClinicas.size() <= 0 )
		{
			ret = true;
		}
		
		return ret;
	}
	
	public AccettazioneNoH getAccettazionePiuRecente()
	{
		AccettazioneNoH ret = null;
		
		Iterator<AccettazioneNoH> it = this.getAnimale().getAccettaziones().iterator();
			
		while( it.hasNext() && ret==null )
		{
			AccettazioneNoH acc = it.next();
			if(  acc.getData().after(this.getData()) )
				ret = acc;
		}
		
		return ret;
	}
	
	public boolean getObbligoAprireCartellaClinica()
	{
		boolean ret = false;
		
		if( cartellaClinicas.size() <= 0 )
		{
			Iterator<LookupOperazioniAccettazione> it = operazioniRichieste.iterator();
			
			while( it.hasNext() && !ret )
			{
				LookupOperazioniAccettazione loa = it.next();
				if( loa.isObbligoCc() )
				{
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	public boolean getCancellabile()
	{
		if(getAnimale().getDataSmaltimentoCarogna()!=null)
			return false;
		if(getCartellaClinicas().size() > 0)
			return false;
		for( AttivitaBdrNoH abdr: attivitaBdrs )
		{
			if(abdr.getIdRegistrazioneBdr()!=null)
				return false;
		}
		return true;
	}
	
	public boolean getModificabile()
	{
		if(getAnimale().getDataSmaltimentoCarogna()!=null)
			return false;
		if(getCartellaClinicas().size() > 0)
			return false;
		for( AttivitaBdrNoH abdr: attivitaBdrs )
		{
			if(abdr.getIdRegistrazioneBdr()!=null)
				return false;
		}
		return true;
	}
	
	public Set<LookupOperazioniAccettazione> getOperazioniRichiesteBdr()
	{
		Set<LookupOperazioniAccettazione> ret = new HashSet<LookupOperazioniAccettazione>();
		
		for( LookupOperazioniAccettazione loa: operazioniRichieste )
		{
			//Escludo ritrovamentoSmarrNonDenunciato dalle operazioni in bdr finchè non sarà implementato in bdr
			if( loa.isInbdr()  || loa.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato)
			{
				ret.add( loa );
			}
		}
		
		return ret;
	}
	
	public boolean getEseguireOperazioniBdr()
	{
		if(getOperazioniRichiesteBdrNonEseguite().isEmpty())
			return false;
		else
			return true;
	}
	
	public Set<LookupOperazioniAccettazione> getOperazioniRichiesteBdrNonEseguite()
	{
		Set<LookupOperazioniAccettazione> ret = new HashSet<LookupOperazioniAccettazione>();
		
		if(animale.getLookupSpecie().getId()!=3)
		{
			Iterator<LookupOperazioniAccettazione> opBdrIter		= getOperazioniRichiesteBdr().iterator();
			java.util.HashMap<Integer, LookupOperazioniAccettazione> opBdrDaEseguire = new java.util.HashMap<Integer, LookupOperazioniAccettazione>();
			
			
			while(opBdrIter.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter.next();
				opBdrDaEseguire.put(temp.getId(), temp);
			}
			
			Iterator<LookupOperazioniAccettazione> opBdrIter2		= getOperazioniRichiesteBdr().iterator();
			
			while(opBdrIter2.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter2.next();
				for( AttivitaBdrNoH abdr: attivitaBdrs )
				{
					if(abdr.getIdRegistrazioneBdr()>0 && abdr.getOperazioneBdr().getId()==temp.getId())
						opBdrDaEseguire.remove(temp.getId());
				}
			}
			
			Iterator<LookupOperazioniAccettazione> opBdrIter3		= opBdrDaEseguire.values().iterator();
			
			while(opBdrIter3.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter3.next();
				ret.add(temp);
			}
		}
		
		return ret;
	}
	
	public String getProgressivoFormattato()
	{
		return "ACC-" + enteredBy.getClinica().getNomeBreve() + "-" + DateUtils.annoCorrenteString( data ) + "-" 
			+ decimalFormat.format(progressivo);
	}
	
	public String getRichiedenteForzaPubblicaComune() {
		return richiedenteForzaPubblicaComune;
	}

	public void setRichiedenteForzaPubblicaComune(
			String richiedenteForzaPubblicaComune) {
		this.richiedenteForzaPubblicaComune = richiedenteForzaPubblicaComune;
	}

	public String getRichiedenteForzaPubblicaProvincia() {
		return richiedenteForzaPubblicaProvincia;
	}

	public void setRichiedenteForzaPubblicaProvincia(
			String richiedenteForzaPubblicaProvincia) {
		this.richiedenteForzaPubblicaProvincia = richiedenteForzaPubblicaProvincia;
	}

	public String getRichiedenteForzaPubblicaComando() {
		return richiedenteForzaPubblicaComando;
	}

	public void setRichiedenteForzaPubblicaComando(
			String richiedenteForzaPubblicaComando) {
		this.richiedenteForzaPubblicaComando = richiedenteForzaPubblicaComando;
	}

	public String getProprietarioNome() {
		return proprietarioNome;
	}


	public void setProprietarioNome(String proprietarioNome) {
		this.proprietarioNome = proprietarioNome;
	}

	public String getProprietarioCognome() {
		return proprietarioCognome;
	}


	public void setProprietarioCognome(String proprietarioCognome) {
		this.proprietarioCognome = proprietarioCognome;
	}
	
	public String getProprietarioTipo() {
		return proprietarioTipo;
	}


	public void setProprietarioTipo(String proprietarioTipo) {
		this.proprietarioTipo = proprietarioTipo;
	}


	public String getProprietarioCodiceFiscale() {
		return proprietarioCodiceFiscale;
	}


	public void setProprietarioCodiceFiscale(String proprietarioCodiceFiscale) {
		this.proprietarioCodiceFiscale = proprietarioCodiceFiscale;
	}

	public String getProprietarioDocumento() {
		return proprietarioDocumento;
	}


	public void setProprietarioDocumento(String proprietarioDocumento) {
		this.proprietarioDocumento = proprietarioDocumento;
	}

	public String getProprietarioCap() {
		return proprietarioCap;
	}


	public void setProprietarioCap(String proprietarioCap) {
		this.proprietarioCap = proprietarioCap;
	}

	public String getProprietarioProvincia() {
		return proprietarioProvincia;
	}


	public void setProprietarioProvincia(String proprietarioProvincia) {
		this.proprietarioProvincia = proprietarioProvincia;
	}

	public String getProprietarioComune() {
		return proprietarioComune;
	}


	public void setProprietarioComune(String proprietarioComune) {
		this.proprietarioComune = proprietarioComune;
	}

	public String getProprietarioIndirizzo() {
		return proprietarioIndirizzo;
	}


	public void setProprietarioIndirizzo(String proprietarioIndirizzo) {
		this.proprietarioIndirizzo = proprietarioIndirizzo;
	}

	public String getProprietarioTelefono() {
		return proprietarioTelefono;
	}


	public void setProprietarioTelefono(String proprietarioTelefono) {
		this.proprietarioTelefono = proprietarioTelefono;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LookupAssociazioni getRichiedenteAssociazione() {
		return this.richiedenteAssociazione;
	}

	public void setRichiedenteAssociazione(LookupAssociazioni richiedenteAssociazione) {
		this.richiedenteAssociazione = richiedenteAssociazione;
	}

	public AnimaleNoH getAnimale() {
		return this.animale;
	}

	public void setAnimale(AnimaleNoH animale) {
		this.animale = animale;
	}
	
	

	public LookupAsl getRichiedenteAsl() {
		return this.richiedenteAsl;
	}

	public void setRichiedenteAsl(LookupAsl richiedenteAsl) {
		this.richiedenteAsl = richiedenteAsl;
	}

	public LookupTipiRichiedente getLookupTipiRichiedente() {
		return this.lookupTipiRichiedente;
	}

	public void setLookupTipiRichiedente(
			LookupTipiRichiedente lookupTipiRichiedente) {
		this.lookupTipiRichiedente = lookupTipiRichiedente;
	}

	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRichiedenteNome() {
		return this.richiedenteNome;
	}

	public void setRichiedenteNome(String richiedenteNome) {
		this.richiedenteNome = richiedenteNome;
	}

	public String getRichiedenteCognome() {
		return this.richiedenteCognome;
	}

	public void setRichiedenteCognome(String richiedenteCognome) {
		this.richiedenteCognome = richiedenteCognome;
	}

	public String getRichiedenteCodiceFiscale() {
		return this.richiedenteCodiceFiscale;
	}

	public void setRichiedenteCodiceFiscale(String richiedenteCodiceFiscale) {
		this.richiedenteCodiceFiscale = richiedenteCodiceFiscale;
	}

	public String getRichiedenteDocumento() {
		return this.richiedenteDocumento;
	}

	public void setRichiedenteDocumento(String richiedenteDocumento) {
		this.richiedenteDocumento = richiedenteDocumento;
	}
	
	public String getRichiedenteTipoProprietario() {
		return this.richiedenteTipoProprietario;
	}

	public void setRichiedenteTipoProprietario(String richiedenteTipoProprietario) {
		this.richiedenteTipoProprietario = richiedenteTipoProprietario;
	}

	public boolean getRichiedenteProprietario() {
		return this.richiedenteProprietario;
	}

	public void setRichiedenteProprietario(boolean richiedenteProprietario) {
		this.richiedenteProprietario = richiedenteProprietario;
	}
	
	public Boolean getRandagio() {
		return this.randagio;
	}

	public void setRandagio(Boolean randagio) {
		this.randagio = randagio;
	}
	
	public Boolean getAdozioneFuoriAsl() {
		return this.adozioneFuoriAsl;
	}

	public void setAdozioneFuoriAsl(Boolean adozioneFuoriAsl) {
		this.adozioneFuoriAsl = adozioneFuoriAsl;
	}
	
	public Boolean getAdozioneVersoAssocCanili() {
		return this.adozioneVersoAssocCanili;
	}

	public void setAdozioneVersoAssocCanili(Boolean adozioneVersoAssocCanili) {
		this.adozioneVersoAssocCanili = adozioneVersoAssocCanili;
	}
	
	public Boolean getSterilizzato() {
		return this.sterilizzato;
	}

	public void setSterilizzato(Boolean sterilizzato) {
		this.sterilizzato = sterilizzato;
	}
	
	public String getSterilizzatoString() {
		return (sterilizzato !=null && sterilizzato)?("Si"):("No");
	}

	public BUtente getRichiedenteAslUser() {
		return this.richiedenteAslUser;
	}

	public void setRichiedenteAslUser(BUtente richiedenteAslUser) {
		this.richiedenteAslUser = richiedenteAslUser;
	}

	public String getRichiedenteAltro() {
		return this.richiedenteAltro;
	}

	public void setRichiedenteAltro(String richiedenteAltro) {
		this.richiedenteAltro = richiedenteAltro;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getAslAnimale() {
		return aslAnimale;
	}

	public void setAslAnimale(String aslAnimale) {
		this.aslAnimale = aslAnimale;
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

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "entered_by")
//	@NotNull
//	public BUtente getEnteredBy() {
//		return this.enteredBy;
//	}
//	
//	public void setEnteredBy(BUtente enteredBy) {
//		this.enteredBy = enteredBy;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "modified_by")
//	@NotNull
//	public BUtente getModifiedBy() {
//		return this.modifiedBy;
//	}
//
//	public void setModifiedBy(BUtente modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}

	public Set<AttivitaBdrNoH> getAttivitaBdrs() {
		return this.attivitaBdrs;
	}

	public void setAttivitaBdrs(Set<AttivitaBdrNoH> attivitaBdrs) {
		this.attivitaBdrs = attivitaBdrs;
	}

	public Set<CartellaClinicaNoH> getCartellaClinicas() {
		return this.cartellaClinicas;
	}
	
	

	public void setCartellaClinicas(Set<CartellaClinicaNoH> cartellaClinicas) {
		this.cartellaClinicas = cartellaClinicas;
	}

	public Set<LookupOperazioniAccettazione> getOperazioniRichieste() {
		return this.operazioniRichieste;
	}

	public void setOperazioniRichieste(
			Set<LookupOperazioniAccettazione> operazioniRichieste) {
		this.operazioniRichieste = operazioniRichieste;
	}
	
	public LookupAsl getAslRitrovamento() {
		return aslRitrovamento;
	}

	public void setAslRitrovamento(LookupAsl aslRitrovamento) {
		this.aslRitrovamento = aslRitrovamento;
	}

	public LookupTipoTrasferimento getTipoTrasferimento() {
		return tipoTrasferimento;
	}

	public void setTipoTrasferimento(LookupTipoTrasferimento tipoTrasferimento) {
		this.tipoTrasferimento = tipoTrasferimento;
	}
	
	public LookupAccettazioneAttivitaEsterna getAttivitaEsterna() {
		return attivitaEsterna;
	}

	public void setAttivitaEsterna(LookupAccettazioneAttivitaEsterna attivitaEsterna) {
		this.attivitaEsterna = attivitaEsterna;
	}
	
	public LookupComuni getComuneAttivitaEsterna() {
		return comuneAttivitaEsterna;
	}

	public void setComuneAttivitaEsterna(LookupComuni comuneAttivitaEsterna) {
		this.comuneAttivitaEsterna = comuneAttivitaEsterna;
	}
	
	public String getIndirizzoAttivitaEsterna() {
		return indirizzoAttivitaEsterna;
	}

	public void setIndirizzoAttivitaEsterna(String indirizzoAttivitaEsterna) {
		this.indirizzoAttivitaEsterna = indirizzoAttivitaEsterna;
	}

	public String getNoteRicoveroInCanile() {
		return noteRicoveroInCanile;
	}

	public void setNoteRicoveroInCanile(String noteRicoveroInCanile) {
		this.noteRicoveroInCanile = noteRicoveroInCanile;
	}
	
	public String getNoteIncompatibilitaAmbientale() {
		return noteIncompatibilitaAmbientale;
	}

	public void setNoteIncompatibilitaAmbientale(
			String noteIncompatibilitaAmbientale) {
		this.noteIncompatibilitaAmbientale = noteIncompatibilitaAmbientale;
	}

	public String getNoteAltro() {
		return noteAltro;
	}

	public void setNoteAltro(String noteAltro) {
		this.noteAltro = noteAltro;
	}
	
	
	
	public Set<SuperUtenteAll> getPersonaleAsl() {
		return this.personaleAsl;
	}

	public void setPersonaleAsl(
			Set<SuperUtenteAll> personaleAsl) {
		this.personaleAsl = personaleAsl;
	}
	
	public Set<String> getPersonaleAslId() 
	{
		Set<String> personaleAslId     = new HashSet<String>(0);
		for(SuperUtenteAll temp:this.personaleAsl)
		{
			personaleAslId.add(temp.getId()+"");
		}
		return personaleAslId;
	}
	
	public Set<LookupPersonaleInterno> getPersonaleInterno() {
		return this.personaleInterno;
	}

	public void setPersonaleInterno(Set<LookupPersonaleInterno> personaleInterno) {
		this.personaleInterno = personaleInterno;
	}
	
	
	public String getOperazioniRichiesteDetail() 
	{
		Iterator<LookupOperazioniAccettazione> iter = this.operazioniRichieste.iterator();
		String toReturn = "";
		boolean scriviVirgola=false;
		while(iter.hasNext())
		{
			LookupOperazioniAccettazione temp = iter.next();
			if(scriviVirgola)
				toReturn+=", ";
			toReturn+=temp.getDescription();
			if(temp.getSceltaAsl()!=null && temp.getSceltaAsl() && this.getAslRitrovamento()!=null && !this.getAslRitrovamento().equals(""))
			{
				toReturn+="("+this.getAslRitrovamento()+")";
			}
			if(temp.getIntraFuoriAsl()!=null && temp.getIntraFuoriAsl() && this.getAdozioneFuoriAsl()!=null && !this.getAdozioneFuoriAsl())
			{
				toReturn+=" fuori asl";
			}
			if(temp.getId()==IdOperazioniBdr.trasferimento && this.getTipoTrasferimento()!=null && !this.getTipoTrasferimento().equals(""))
			{
				toReturn+="("+this.getTipoTrasferimento()+")";
			}
			if(temp.getId()==IdOperazioniBdr.altro && this.getNoteAltro()!=null && !this.getNoteAltro().equals(""))
			{
				toReturn+="("+this.getNoteAltro()+")";
			}
			if(temp.getId()==IdOperazioniBdr.ricoveroInCanile && this.getNoteRicoveroInCanile()!=null && !this.getNoteRicoveroInCanile().equals(""))
			{
				toReturn+="("+this.getNoteRicoveroInCanile()+")";
			}
			if(temp.getId()==IdOperazioniBdr.incompatibilitaAmbientale && this.getNoteIncompatibilitaAmbientale()!=null && !this.getNoteIncompatibilitaAmbientale().equals(""))
			{
				toReturn+="("+this.getNoteIncompatibilitaAmbientale()+")";
			}
			if(temp.getId()==IdRichiesteVarie.attivitaEsterne)
			{
				toReturn+="("+this.getAttivitaEsterna().getDescription()+")";
			}
			scriviVirgola=true;
		}
		
		if(!this.operazioniRichieste.isEmpty())
			toReturn+=";";
		
		return toReturn;
		
	
	}
	
	
	public String getNomeColonia() 
	{
		if(proprietarioCognome!=null && !proprietarioCognome.equals(""))
			return proprietarioCognome.replace("<b>Colonia:</b> ", "");
		else
			return proprietarioCognome;
	
	}
	
	public String getRagSocialeOpCommerciale() 
	{
		if(proprietarioCognome!=null && !proprietarioCognome.equals(""))
			return proprietarioCognome.replace("<b>Operatore Commerciale:</b> ", "");
		else
			return proprietarioCognome;
	
	}
	
	public String getRichiedenteTelefono() {
		return richiedenteTelefono;
	}

	public void setRichiedenteTelefono(String richiedenteTelefono) {
		this.richiedenteTelefono = richiedenteTelefono;
	}
	public String getRichiedenteResidenza() {
		return richiedenteResidenza;
	}

	public void setRichiedenteResidenza(String richiedenteResidenza) {
		this.richiedenteResidenza = richiedenteResidenza;
	}
	
	public CartellaClinica getCcVivo() {
		return this.ccVivo;
	}

	
	public void setCcVivo(CartellaClinica ccVivo) {
		this.ccVivo = ccVivo;
	}
	
	
	public Set<Integer> getIdTipoAttivitaBdrsCompletate()
	{
		Set<Integer> idTipoAttivitaBdrs = new HashSet<Integer>();
		Iterator<AttivitaBdrNoH> iter = getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdrNoH att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0)
				idTipoAttivitaBdrs.add(att.getOperazioneBdr().getId());
		}
		return idTipoAttivitaBdrs;
	}
	
	public String toString()
	{
		if(enteredBy!=null)
			return getProgressivoFormattato();
		else
			return id+"";
	}
	
	
	
	//AGGIUNTI VERONICA
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
	
	
	public String getIdAccMultipla() {
		return this.idAccMultipla;
	}

	public void setIdAccMultipla(String idAccMultipla) {
		this.idAccMultipla = idAccMultipla;
	}
	
	
	@Transient
	public Set<Integer> getOperazioniRichiesteBdrNonEseguiteId()
	{
		Set<Integer> ret = new HashSet<Integer>();
		
		if(animale.getLookupSpecie().getId()!=3)
		{
			Iterator<LookupOperazioniAccettazione> opBdrIter		= getOperazioniRichiesteBdr().iterator();
			java.util.HashMap<Integer, LookupOperazioniAccettazione> opBdrDaEseguire = new java.util.HashMap<Integer, LookupOperazioniAccettazione>();
			
			
			while(opBdrIter.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter.next();
				opBdrDaEseguire.put(temp.getId(), temp);
			}
			
			Iterator<LookupOperazioniAccettazione> opBdrIter2		= getOperazioniRichiesteBdr().iterator();
			
			while(opBdrIter2.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter2.next();
				for( AttivitaBdrNoH abdr: attivitaBdrs )
				{
					if(abdr.getIdRegistrazioneBdr()>0 && abdr.getOperazioneBdr().getId()==temp.getId())
						opBdrDaEseguire.remove(temp.getId());
				}
			}
			
			Iterator<LookupOperazioniAccettazione> opBdrIter3		= opBdrDaEseguire.values().iterator();
			
			while(opBdrIter3.hasNext())
			{
				LookupOperazioniAccettazione temp = opBdrIter3.next();
				ret.add(temp.getId());
			}
		}
		
		return ret;
	}
	
}
