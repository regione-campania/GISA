/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.dao.UtenteDAO;
import it.us.web.util.DateUtils;

import java.text.DecimalFormat;
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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "accettazione", schema = "public")
@Where( clause = "trashed_date is null" )
//@ScriptAssert(lang = "javascript", script = "_this.nome.equals(_this.cognome)", message="attenzioneeeeee!!!!" )
public class AccettazioneList implements java.io.Serializable
{
	private static final long serialVersionUID = 7046648521134189879L;
	private static final DecimalFormat decimalFormat = new DecimalFormat( "00000" );
	
	private int id;
	private Animale animale;
	private String aslAnimale;
	private LookupTipiRichiedente lookupTipiRichiedente;
	private Integer progressivo;
	private Date data;
	private Set<LookupOperazioniAccettazione> operazioniRichieste = new HashSet<LookupOperazioniAccettazione>(0);
	private Set<CartellaClinica> cartellaClinicas = new HashSet<CartellaClinica>(0);
	private Set<AttivitaBdr> attivitaBdrs = new HashSet<AttivitaBdr>(0);
	private BUtenteAll enteredBy;
	private LookupAsl aslRitrovamento;
	private LookupTipoTrasferimento tipoTrasferimento;
	private String noteAltro; 
	private String noteRicoveroInCanile;
	private String noteIncompatibilitaAmbientale;
	private LookupAccettazioneAttivitaEsterna attivitaEsterna;
	private Boolean adozioneFuoriAsl 	 = false;
	private Boolean adozioneVersoAssocCanili 	 = false;

	public AccettazioneList()
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
				//Includo ritrovamentoSmarrNonDenunciato nelle operazioni non bdr finchè non sarà implementato in bdr
				if( !loa.isInbdr() &&  loa.getId()!=IdOperazioniBdr.ritrovamentoSmarrNonDenunciato&&  loa.getId()!=IdOperazioniBdr.smaltimentoCarogna)
				{
					ret = true;
				}
			}
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
		if(attivitaBdrs.size() > 0)
			return false;
		return true;
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
	
	
	
	@Transient
	public boolean getModificabile()
	{
		if(getAnimale().getDataSmaltimentoCarogna()!=null)
			return false;
		if(getCartellaClinicas().size() > 0)
			return false;
		if(attivitaBdrs.size() > 0)
			return false;
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
		boolean ret = false;
		
		ret = getOperazioniRichiesteBdrNonEseguite().size() > 0;
		
		return ret;
	}
	
	@Transient
	public Set<LookupOperazioniAccettazione> getOperazioniRichiesteBdrNonEseguite()
	{
		Set<LookupOperazioniAccettazione> ret = new HashSet<LookupOperazioniAccettazione>();
		
		Set<LookupOperazioniAccettazione> opBdrRichieste	= getOperazioniRichiesteBdr();
		Set<LookupOperazioniAccettazione> opBdrEseguite		= new HashSet<LookupOperazioniAccettazione>();
		for( AttivitaBdr abdr: attivitaBdrs )
		{
			opBdrEseguite.add( abdr.getOperazioneBdr() );
		}
		
		opBdrRichieste.removeAll( opBdrEseguite );
		ret = opBdrRichieste;
		
		return ret;
	}
	
	@Transient
	public String getProgressivoFormattato()
	{
		return "ACC-" + enteredBy.getClinica().getNomeBreve() + "-" + DateUtils.annoCorrenteString( data ) + "-" 
			+ decimalFormat.format(progressivo);
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
	@JoinColumn(name = "animale")
	public Animale getAnimale() {
		return this.animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
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

	@Column(name = "asl_animale", length = 64)
	@Length(max = 64)
	public String getAslAnimale() {
		return aslAnimale;
	}

	public void setAslAnimale(String aslAnimale) {
		this.aslAnimale = aslAnimale;
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
			if(temp.getIntraFuoriAsl()!=null && temp.getIntraFuoriAsl() && this.getAdozioneFuoriAsl()!=null && !this.getAdozioneFuoriAsl().equals(""))
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
}

