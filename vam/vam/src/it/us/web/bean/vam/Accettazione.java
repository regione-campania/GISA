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

@Entity
@Table(name = "accettazione", schema = "public")
@Where( clause = "trashed_date is null" )
//@ScriptAssert(lang = "javascript", script = "_this.nome.equals(_this.cognome)", message="attenzioneeeeee!!!!" )
public class Accettazione implements java.io.Serializable
{
	private String prova4 = "###prova";
	private static final long serialVersionUID = 7046648521134189879L;
	private static final DecimalFormat decimalFormat = new DecimalFormat( "00000" );
	
	private int id;
	private Animale animale;
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
	
	private Set<AttivitaBdr> attivitaBdrs = new HashSet<AttivitaBdr>(0);
	private Set<CartellaClinica> cartellaClinicas = new HashSet<CartellaClinica>(0);
	private Set<LookupOperazioniAccettazione> operazioniRichieste = new HashSet<LookupOperazioniAccettazione>(0);
	private Set<LookupPersonaleInterno> personaleInterno = new HashSet<LookupPersonaleInterno>(0);
	private Set<SuperUtenteAll> personaleAsl     = new HashSet<SuperUtenteAll>(0);
	private String noteRicoveroInCanile;
	private String noteIncompatibilitaAmbientale;
	private String noteAltro;
	private String idAccMultipla;

	public Accettazione()
	{
		
	}

	@Transient
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
	
	@Transient
	public boolean isCovid()
	{
		String ohyes;
		boolean ret = false;
		
		Iterator<LookupOperazioniAccettazione> it = operazioniRichieste.iterator();
		
		while( it.hasNext() && !ret )
		{
			LookupOperazioniAccettazione loa = it.next();
			if(loa.getCovid())
				ret = true;
		}
		
		return ret;
	}
	
	public boolean contain(Integer idOperazione)
	{
		boolean ret = false;
		
		Iterator<LookupOperazioniAccettazione> it = operazioniRichieste.iterator();
		
		while( it.hasNext() && !ret )
		{
			LookupOperazioniAccettazione loa = it.next();
			if(loa.getId()==idOperazione)
				ret = true;
		}
		
		return ret;
	}
	
	@Transient
	public boolean getAprireCartellaNecroscopica()
	{
		boolean ret = false;
		
		if( cartellaClinicas.size() <= 0 )
		{
			ret = true;
		}
		
		return ret;
	}
	
	@Transient
	public Accettazione getAccettazionePiuRecente()
	{
		Accettazione ret = null;
		
		Iterator<Accettazione> it = this.getAnimale().getAccettaziones().iterator();
			
		while( it.hasNext() && ret==null )
		{
			Accettazione acc = it.next();
			if(  acc.getData().after(this.getData()) )
				ret = acc;
		}
		
		return ret;
	}
	
	@Transient
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
	
	@Transient
	public boolean getCancellabile()
	{
		if(getAnimale().getDataSmaltimentoCarogna()!=null)
			return false;
		if(getCartellaClinicas().size() > 0)
			return false;
		for( AttivitaBdr abdr: attivitaBdrs ) {
			if (abdr.getOperazioneBdr().getId() != IdOperazioniBdr.iscrizione){
				if(abdr.getIdRegistrazioneBdr()!=null)
					return false;
			}
		}
		return true;
	}
	
	@Transient
	public boolean getModificabile()
	{
		if(getAnimale().getDataSmaltimentoCarogna()!=null)
			return false;
		if(getCartellaClinicas().size() > 0)
			return false;
		for( AttivitaBdr abdr: attivitaBdrs )
		{
			if(abdr.getIdRegistrazioneBdr()!=null)
				return false;
		}
		return true;
	}
	
	@Transient
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
	
	@Transient
	public boolean getEseguireOperazioniBdr()
	{
		if (!this.getCartellaClinicas().isEmpty()){
				if(this.getCartellaClinicas().iterator().next().getCcRiconsegna())
					return false;
		}
		
		if(getOperazioniRichiesteBdrNonEseguite().isEmpty())
			return false;
		else
			return true;
	}
	
	@Transient
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
				for( AttivitaBdr abdr: attivitaBdrs )
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
				for( AttivitaBdr abdr: attivitaBdrs )
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
	
	@Transient
	public String getProgressivoFormattato()
	{
		return "ACC-" + enteredBy.getClinica().getNomeBreve() + "-" + DateUtils.annoCorrenteString( data ) + "-" 
			+ decimalFormat.format(progressivo);
	}
	
	@Column(name = "richiedente_forza_pubblica_comune", length = 64)
	@Length(max = 64)
	public String getRichiedenteForzaPubblicaComune() {
		return richiedenteForzaPubblicaComune;
	}

	public void setRichiedenteForzaPubblicaComune(
			String richiedenteForzaPubblicaComune) {
		this.richiedenteForzaPubblicaComune = richiedenteForzaPubblicaComune;
	}

	@Column(name = "richiedente_forza_pubblica_provincia", length = 64)
	@Length(max = 64)
	public String getRichiedenteForzaPubblicaProvincia() {
		return richiedenteForzaPubblicaProvincia;
	}

	public void setRichiedenteForzaPubblicaProvincia(
			String richiedenteForzaPubblicaProvincia) {
		this.richiedenteForzaPubblicaProvincia = richiedenteForzaPubblicaProvincia;
	}

	@Column(name = "richiedente_forza_pubblica_comando", length = 64)
	@Length(max = 64)
	public String getRichiedenteForzaPubblicaComando() {
		return richiedenteForzaPubblicaComando;
	}

	public void setRichiedenteForzaPubblicaComando(
			String richiedenteForzaPubblicaComando) {
		this.richiedenteForzaPubblicaComando = richiedenteForzaPubblicaComando;
	}

	@Column(name = "proprietario_nome")
	@Type(type="text")
	public String getProprietarioNome() {
		return proprietarioNome;
	}


	public void setProprietarioNome(String proprietarioNome) {
		this.proprietarioNome = proprietarioNome;
	}

	@Column(name = "proprietario_cognome")
	@Type(type="text")
	public String getProprietarioCognome() {
		return proprietarioCognome;
	}


	public void setProprietarioCognome(String proprietarioCognome) {
		this.proprietarioCognome = proprietarioCognome;
	}
	
	@Column(name = "proprietario_tipo")
	@Type(type="text")
	public String getProprietarioTipo() {
		return proprietarioTipo;
	}


	public void setProprietarioTipo(String proprietarioTipo) {
		this.proprietarioTipo = proprietarioTipo;
	}


	@Column(name = "proprietario_codice_fiscale")
	public String getProprietarioCodiceFiscale() {
		return proprietarioCodiceFiscale;
	}


	public void setProprietarioCodiceFiscale(String proprietarioCodiceFiscale) {
		this.proprietarioCodiceFiscale = proprietarioCodiceFiscale;
	}

	@Column(name = "proprietario_documento")
	@Type(type = "text")
	public String getProprietarioDocumento() {
		return proprietarioDocumento;
	}


	public void setProprietarioDocumento(String proprietarioDocumento) {
		this.proprietarioDocumento = proprietarioDocumento;
	}

	@Column(name = "proprietario_cap")
	public String getProprietarioCap() {
		return proprietarioCap;
	}


	public void setProprietarioCap(String proprietarioCap) {
		this.proprietarioCap = proprietarioCap;
	}

	@Column(name = "proprietario_provincia")
	public String getProprietarioProvincia() {
		return proprietarioProvincia;
	}


	public void setProprietarioProvincia(String proprietarioProvincia) {
		this.proprietarioProvincia = proprietarioProvincia;
	}

	@Column(name = "proprietario_comune")
	@Type(type="text")
	public String getProprietarioComune() {
		return proprietarioComune;
	}


	public void setProprietarioComune(String proprietarioComune) {
		this.proprietarioComune = proprietarioComune;
	}

	@Column(name = "proprietario_indirizzo")
	@Type(type="text")
	public String getProprietarioIndirizzo() {
		return proprietarioIndirizzo;
	}


	public void setProprietarioIndirizzo(String proprietarioIndirizzo) {
		this.proprietarioIndirizzo = proprietarioIndirizzo;
	}

	@Column(name = "proprietario_telefono")
	@Type(type="text")
	public String getProprietarioTelefono() {
		return proprietarioTelefono;
	}


	public void setProprietarioTelefono(String proprietarioTelefono) {
		this.proprietarioTelefono = proprietarioTelefono;
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
	@JoinColumn(name = "richiedente_associazione")
	public LookupAssociazioni getRichiedenteAssociazione() {
		return this.richiedenteAssociazione;
	}

	public void setRichiedenteAssociazione(LookupAssociazioni richiedenteAssociazione) {
		this.richiedenteAssociazione = richiedenteAssociazione;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "animale")
	public Animale getAnimale() {
		return this.animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
	}
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiedente_asl")
	public LookupAsl getRichiedenteAsl() {
		return this.richiedenteAsl;
	}

	public void setRichiedenteAsl(LookupAsl richiedenteAsl) {
		this.richiedenteAsl = richiedenteAsl;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiedente_tipo")
	public LookupTipiRichiedente getLookupTipiRichiedente() {
		return this.lookupTipiRichiedente;
	}

	public void setLookupTipiRichiedente(
			LookupTipiRichiedente lookupTipiRichiedente) {
		this.lookupTipiRichiedente = lookupTipiRichiedente;
	}

	@Column(name = "progressivo")
	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
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

	@Column(name = "richiedente_nome")
	@Type(type="text")
	public String getRichiedenteNome() {
		return this.richiedenteNome;
	}

	public void setRichiedenteNome(String richiedenteNome) {
		this.richiedenteNome = richiedenteNome;
	}

	@Column(name = "richiedente_cognome")
	@Type(type="text")
	public String getRichiedenteCognome() {
		return this.richiedenteCognome;
	}

	public void setRichiedenteCognome(String richiedenteCognome) {
		this.richiedenteCognome = richiedenteCognome;
	}

	@Column(name = "richiedente_codice_fiscale")
	public String getRichiedenteCodiceFiscale() {
		return this.richiedenteCodiceFiscale;
	}

	public void setRichiedenteCodiceFiscale(String richiedenteCodiceFiscale) {
		this.richiedenteCodiceFiscale = richiedenteCodiceFiscale;
	}

	@Column(name = "richiedente_documento")
	@Type(type = "text")
	public String getRichiedenteDocumento() {
		return this.richiedenteDocumento;
	}

	public void setRichiedenteDocumento(String richiedenteDocumento) {
		this.richiedenteDocumento = richiedenteDocumento;
	}
	
	@Column(name = "richiedente_tipo_proprietario")
	@Type(type = "text")
	public String getRichiedenteTipoProprietario() {
		return this.richiedenteTipoProprietario;
	}

	public void setRichiedenteTipoProprietario(String richiedenteTipoProprietario) {
		this.richiedenteTipoProprietario = richiedenteTipoProprietario;
	}

	@Column(name = "richiedente_proprietario")
	public boolean getRichiedenteProprietario() {
		return this.richiedenteProprietario;
	}

	public void setRichiedenteProprietario(boolean richiedenteProprietario) {
		this.richiedenteProprietario = richiedenteProprietario;
	}
	
	@Column(name = "randagio")
	public Boolean getRandagio() {
		return this.randagio;
	}

	public void setRandagio(Boolean randagio) {
		this.randagio = randagio;
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
	
	@Column(name = "sterilizzato")
	public Boolean getSterilizzato() {
		return this.sterilizzato;
	}

	public void setSterilizzato(Boolean sterilizzato) {
		this.sterilizzato = sterilizzato;
	}
	
	@Transient
	public String getSterilizzatoString() {
		return (sterilizzato !=null && sterilizzato)?("Si"):("No");
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiedente_asl_user")
	public BUtente getRichiedenteAslUser() {
		return this.richiedenteAslUser;
	}

	public void setRichiedenteAslUser(BUtente richiedenteAslUser) {
		this.richiedenteAslUser = richiedenteAslUser;
	}

	@Column(name = "richiedente_altro")
	@Type(type = "text")
	public String getRichiedenteAltro() {
		return this.richiedenteAltro;
	}

	public void setRichiedenteAltro(String richiedenteAltro) {
		this.richiedenteAltro = richiedenteAltro;
	}

	@Column(name = "descrizione", length = 64)
	@Length(max = 64)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Column(name = "asl_animale", length = 64)
	@Length(max = 64)
	public String getAslAnimale() {
		return aslAnimale;
	}

	public void setAslAnimale(String aslAnimale) {
		this.aslAnimale = aslAnimale;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accettazione")
	@Where( clause = "trashed_date is null" )
	public Set<AttivitaBdr> getAttivitaBdrs() {
		return this.attivitaBdrs;
	}

	public void setAttivitaBdrs(Set<AttivitaBdr> attivitaBdrs) {
		this.attivitaBdrs = attivitaBdrs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accettazione")
	@Where( clause = "trashed_date is null" )
	public Set<CartellaClinica> getCartellaClinicas() {
		return this.cartellaClinicas;
	}
	
	

	public void setCartellaClinicas(Set<CartellaClinica> cartellaClinicas) {
		this.cartellaClinicas = cartellaClinicas;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "accettazione_operazionirichieste", schema = "public", joinColumns = { @JoinColumn(name = "accettazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operazione_richiesta", nullable = false, updatable = false) })
	@Size(min = 1, message = "Selezionare almeno una operazione")
	public Set<LookupOperazioniAccettazione> getOperazioniRichieste() {
		return this.operazioniRichieste;
	}

	public void setOperazioniRichieste(
			Set<LookupOperazioniAccettazione> operazioniRichieste) {
		this.operazioniRichieste = operazioniRichieste;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asl_ritrovamento")
	public LookupAsl getAslRitrovamento() {
		return aslRitrovamento;
	}

	public void setAslRitrovamento(LookupAsl aslRitrovamento) {
		this.aslRitrovamento = aslRitrovamento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_trasferimento")
	public LookupTipoTrasferimento getTipoTrasferimento() {
		return tipoTrasferimento;
	}

	public void setTipoTrasferimento(LookupTipoTrasferimento tipoTrasferimento) {
		this.tipoTrasferimento = tipoTrasferimento;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attivita_esterna")
	public LookupAccettazioneAttivitaEsterna getAttivitaEsterna() {
		return attivitaEsterna;
	}

	public void setAttivitaEsterna(LookupAccettazioneAttivitaEsterna attivitaEsterna) {
		this.attivitaEsterna = attivitaEsterna;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune_attivita_esterna")
	public LookupComuni getComuneAttivitaEsterna() {
		return comuneAttivitaEsterna;
	}

	public void setComuneAttivitaEsterna(LookupComuni comuneAttivitaEsterna) {
		this.comuneAttivitaEsterna = comuneAttivitaEsterna;
	}
	
	@Column(name = "indirizzo_attivita_esterna")
	@Type(type = "text")
	public String getIndirizzoAttivitaEsterna() {
		return indirizzoAttivitaEsterna;
	}

	public void setIndirizzoAttivitaEsterna(String indirizzoAttivitaEsterna) {
		this.indirizzoAttivitaEsterna = indirizzoAttivitaEsterna;
	}

	@Column(name = "note_ricovero_in_canile")
	@Type(type = "text")
	public String getNoteRicoveroInCanile() {
		return noteRicoveroInCanile;
	}

	public void setNoteRicoveroInCanile(String noteRicoveroInCanile) {
		this.noteRicoveroInCanile = noteRicoveroInCanile;
	}
	
	@Column(name = "note_incompatibilita_ambientale")
	@Type(type = "text")
	public String getNoteIncompatibilitaAmbientale() {
		return noteIncompatibilitaAmbientale;
	}

	public void setNoteIncompatibilitaAmbientale(
			String noteIncompatibilitaAmbientale) {
		this.noteIncompatibilitaAmbientale = noteIncompatibilitaAmbientale;
	}

	@Column(name = "note_altro")
	@Type(type = "text")
	public String getNoteAltro() {
		return noteAltro;
	}

	public void setNoteAltro(String noteAltro) {
		this.noteAltro = noteAltro;
	}
	
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "accettazione_personaleasl", schema = "public", joinColumns = { @JoinColumn(name = "accettazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "personale_asl", nullable = false, updatable = false) })
	public Set<SuperUtenteAll> getPersonaleAsl() {
		return this.personaleAsl;
	}

	public void setPersonaleAsl(
			Set<SuperUtenteAll> personaleAsl) {
		this.personaleAsl = personaleAsl;
	}
	
	@Transient
	public Set<String> getPersonaleAslId() 
	{
		Set<String> personaleAslId     = new HashSet<String>(0);
		for(SuperUtenteAll temp:this.personaleAsl)
		{
			personaleAslId.add(temp.getId()+"");
		}
		return personaleAslId;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "accettazione_personaleinterno", schema = "public", joinColumns = { @JoinColumn(name = "accettazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "personale_interno", nullable = false, updatable = false) })
	public Set<LookupPersonaleInterno> getPersonaleInterno() {
		return this.personaleInterno;
	}

	public void setPersonaleInterno(Set<LookupPersonaleInterno> personaleInterno) {
		this.personaleInterno = personaleInterno;
	}
	
	
	@Transient
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
			if(temp.getVersoAssocCanili()!=null && temp.getVersoAssocCanili() && this.getAdozioneVersoAssocCanili()!=null && !this.getAdozioneVersoAssocCanili())
			{
				toReturn+=" verso Associazioni/Canili";
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
	
	
	@Transient
	public String getNomeColonia() 
	{
		if(proprietarioCognome!=null && !proprietarioCognome.equals(""))
			return proprietarioCognome.replace("<b>Colonia:</b> ", "");
		else
			return proprietarioCognome;
	
	}
	
	@Transient
	public String getRagSocialeOpCommerciale() 
	{
		if(proprietarioCognome!=null && !proprietarioCognome.equals(""))
			return proprietarioCognome.replace("<b>Operatore Commerciale:</b> ", "");
		else
			return proprietarioCognome;
	
	}
	
	@Column(name = "richiedente_telefono")
	@Type(type = "text")
	public String getRichiedenteTelefono() {
		return richiedenteTelefono;
	}

	public void setRichiedenteTelefono(String richiedenteTelefono) {
		this.richiedenteTelefono = richiedenteTelefono;
	}
	@Column(name = "richiedente_residenza")
	@Type(type = "text")
	public String getRichiedenteResidenza() {
		return richiedenteResidenza;
	}

	public void setRichiedenteResidenza(String richiedenteResidenza) {
		this.richiedenteResidenza = richiedenteResidenza;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cc_vivo")
	public CartellaClinica getCcVivo() {
		return this.ccVivo;
	}

	
	public void setCcVivo(CartellaClinica ccVivo) {
		this.ccVivo = ccVivo;
	}
	
	
	@Transient
	public Set<Integer> getIdTipoAttivitaBdrsCompletate()
	{
		Set<Integer> idTipoAttivitaBdrs = new HashSet<Integer>();
		Iterator<AttivitaBdr> iter = getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0)
				idTipoAttivitaBdrs.add(att.getOperazioneBdr().getId());
		}
		return idTipoAttivitaBdrs;
	}
	
	@Override
	public String toString()
	{
		if(enteredBy!=null)
			return getProgressivoFormattato();
		else
			return id+"";
	}
	
	
	
	//AGGIUNTI VERONICA
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
	
	
	@Column(name = "id_acc_multipla")
	public String getIdAccMultipla() {
		return this.idAccMultipla;
	}

	public void setIdAccMultipla(String idAccMultipla) {
		this.idAccMultipla = idAccMultipla;
	}
	
}
