/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.util.vam.ComparatorTrasferimenti;

import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "clinica", schema = "public")
@Where( clause = "trashed_date is null" )
public class Clinica implements java.io.Serializable
{
	private static final long serialVersionUID = 8752614958052634681L;
	
	private int id;
	private LookupComuni lookupComuni;
	private LookupAsl lookupAsl;
	private String nome;
	private String nomeBreve;
	private String indirizzo;
	private String telefono;
	private String fax;
	private String email;
	private String note;
	private String remoteCaninaUsername;
	private String remoteCaninaPassword;
	private String remoteFelinaUsername;
	private String remoteFelinaPassword;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Date dataCessazione;
	private Integer enteredBy;
	private Integer modifiedBy;
	private Integer canileBdu;
	private Integer idStabilimentoGisa;
	
	private Set<StrutturaClinica> 	strutturaClinicas 		= new HashSet<StrutturaClinica>(0);	
	private Set<BUtente> 			utentis 				= new HashSet<BUtente>(0);
	private Set<Trasferimento> 		trasferimentiUscita 	= new HashSet<Trasferimento>(0);
	private Set<Trasferimento> 		trasferimentiIngresso 	= new HashSet<Trasferimento>(0);	
	private Set<MagazzinoFarmaci> 	magazzinoFarmaci 		= new HashSet<MagazzinoFarmaci>(0);
	private Set<MagazzinoArticoliSanitari> 	magazzinoArticoliSanitari 		= new HashSet<MagazzinoArticoliSanitari>(0);
	private Set<MagazzinoMangimi> 	magazzinoMangimi 		= new HashSet<MagazzinoMangimi>(0);
	private Set<BookingClinica> 	booking 				= new HashSet<BookingClinica>(0);

	public Clinica()
	{
		
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "clinica")
	@Where(clause="enabled")
	public Set<BUtente> getUtentis() {
		return utentis;
	}

	public void setUtentis(Set<BUtente> utentis) {
		this.utentis = utentis;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinicaOrigine")
	@OrderBy(clause="dataRichiesta desc, id desc")
	@Where(clause="trashed_date is null")
	public Set<Trasferimento> getTrasferimentiUscita() {
		return trasferimentiUscita;
	}

	public void setTrasferimentiUscita(Set<Trasferimento> trasferimentiUscita) {
		this.trasferimentiUscita = trasferimentiUscita;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "clinicaDestinazione")
	@OrderBy(clause="dataRichiesta desc, id desc")
	@Fetch (FetchMode.SELECT) 
	@Where(clause="trashed_date is null")
	public Set<Trasferimento> getTrasferimentiIngresso() {
		return trasferimentiIngresso;
	}

	public void setTrasferimentiIngresso(Set<Trasferimento> trasferimentiIngresso) {
		this.trasferimentiIngresso = trasferimentiIngresso;
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
	@JoinColumn(name = "comune")
	public LookupComuni getLookupComuni() {
		return this.lookupComuni;
	}

	public void setLookupComuni(LookupComuni lookupComuni) {
		this.lookupComuni = lookupComuni;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "asl")
	@Fetch (FetchMode.SELECT) 
	public LookupAsl getLookupAsl() {
		return this.lookupAsl;
	}

	public void setLookupAsl(LookupAsl lookupAsl) {
		this.lookupAsl = lookupAsl;
	}

	@Column(name = "nome", length = 64)
	@Length(max = 64)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "nome_breve", length = 64)
	@Length(max = 64)
	public String getNomeBreve() {
		return nomeBreve;
	}

	public void setNomeBreve(String nomeBreve) {
		this.nomeBreve = nomeBreve;
	}

	@Column(name = "indirizzo", length = 64)
	@Length(max = 256)
	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Column(name = "telefono", length = 64)
	@Length(max = 64)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "fax", length = 64)
	@Length(max = 64)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "email", length = 64)
	@Length(max = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cessazione", length = 29)
	public Date getDataCessazione() {
		return this.dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
	@Where( clause = "trashed_date is null" )
	public Set<StrutturaClinica> getStrutturaClinicas() {
		return this.strutturaClinicas;
	}

	public void setStrutturaClinicas(Set<StrutturaClinica> strutturaClinicas) {
		this.strutturaClinicas = strutturaClinicas;
	}
	
	@Column(name = "remote_canina_username")
	public String getRemoteCaninaUsername() {
		return remoteCaninaUsername;
	}

	public void setRemoteCaninaUsername(String remoteCaninaUsername) {
		this.remoteCaninaUsername = remoteCaninaUsername;
	}

	@Column(name = "remote_canina_password")
	public String getRemoteCaninaPassword() {
		return remoteCaninaPassword;
	}

	public void setRemoteCaninaPassword(String remoteCaninaPassword) {
		this.remoteCaninaPassword = remoteCaninaPassword;
	}
	
	@Column(name = "remote_felina_username")
	public String getRemoteFelinaUsername() {
		return remoteFelinaUsername;
	}

	public void setRemoteFelinaUsername(String remoteFelinaUsername) {
		this.remoteFelinaUsername = remoteFelinaUsername;
	}

	@Column(name = "remote_felina_password")
	public String getRemoteFelinaPassword() {
		return remoteFelinaPassword;
	}

	public void setRemoteFelinaPassword(String remoteFelinaPassword) {
		this.remoteFelinaPassword = remoteFelinaPassword;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
	@Where( clause = "trashed_date is null" )
	public Set<MagazzinoFarmaci> getMagazzinoFarmaci() {
		return magazzinoFarmaci;
	}

	public void setMagazzinoFarmaci(Set<MagazzinoFarmaci> magazzinoFarmaci) {
		this.magazzinoFarmaci = magazzinoFarmaci;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
	@Where( clause = "trashed_date is null" )
	public Set<MagazzinoMangimi> getMagazzinoMangimi() {
		return magazzinoMangimi;
	}

	public void setMagazzinoMangimi(Set<MagazzinoMangimi> magazzinoMangimi) {
		this.magazzinoMangimi = magazzinoMangimi;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
	@Where( clause = "trashed_date is null" )
	public Set<MagazzinoArticoliSanitari> getMagazzinoArticoliSanitari() {
		return magazzinoArticoliSanitari;
	}

	public void setMagazzinoArticoliSanitari(Set<MagazzinoArticoliSanitari> magazzinoArticoliSanitari) {
		this.magazzinoArticoliSanitari = magazzinoArticoliSanitari;
	}
	
	@Column(name = "canile_bdu")
	public Integer getCanileBdu() {
		return this.canileBdu;
	}

	public void setCanileBdu(Integer canileBdu) {
		this.canileBdu = canileBdu;
	}
	
	@Column(name = "id_stabilimento_gisa")
	public Integer getIdStabilimentoGisa() {
		return this.idStabilimentoGisa;
	}

	public void setIdStabilimentoGisa(Integer idStabilimentoGisa) {
		this.idStabilimentoGisa = idStabilimentoGisa;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinica")
	@Where( clause = "trashed_date is null" )
	public Set<BookingClinica> getBooking() {
		return booking;
	}

	public void setBooking(Set<BookingClinica> booking) {
		this.booking = booking;
	}
	
	@Transient
	public Set<Trasferimento> getTrasferimentiIngressoNoAttesaRifiutatoCriuv() {
		
		Set<Trasferimento> trasf = new HashSet<Trasferimento>();
		for(Trasferimento t : getTrasferimentiIngresso())
		{
			if(t.getStato().getStato()!=1 && t.getStato().getStato()!=6)
				trasf.add(t);
				
		}
		return trasf;
	}
	
	@Transient
	public ArrayList<Trasferimento> getTrasferimentiIngressoNoAttesaRifiutatoCriuvOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimentiIngressoNoAttesaRifiutatoCriuv());
		Collections.sort(trasfList,comp);
		return trasfList;
	}
	
	@Transient
	public ArrayList<Trasferimento> getTrasferimentiUscitaOrderByStato() {
		ArrayList<Trasferimento> trasfList = new ArrayList<Trasferimento>();
		ComparatorTrasferimenti comp = new ComparatorTrasferimenti();
		trasfList.addAll(getTrasferimentiUscita());
		Collections.sort(trasfList,comp);
		return trasfList;
	}

	@Transient
	public Set<Trasferimento> getTrasferimentiIngressoDaAccettare() {
		
		Set<Trasferimento> trasf = new HashSet<Trasferimento>();
		for(Trasferimento t : getTrasferimentiIngresso())
		{
			if(t.getStato().getStato()!=1 && t.getStato().getStato()!=6 && t.getDataAccettazioneDestinatario()==null)
				trasf.add(t);
				
		}
		return trasf;
	}
	
	@Override
	public String toString()
	{
		return nome;
	}

}
