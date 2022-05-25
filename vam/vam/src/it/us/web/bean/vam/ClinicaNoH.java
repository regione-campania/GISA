/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAslNoH;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupComuniNOH;
import it.us.web.util.vam.ComparatorTrasferimenti;
import it.us.web.util.vam.ComparatorTrasferimentiNoH;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

public class ClinicaNoH implements java.io.Serializable
{
	private static final long serialVersionUID = 8752614958052634681L;
	
	private int id;
	private LookupComuni lookupComuni;
	private LookupComuniNOH lookupComuniNOH;
	private LookupAsl lookupAsl;
	private LookupAslNoH lookupAslNoH;
	private String nome;
	private String nomeBreve;
	private String indirizzo;
	private String telefono;
	private String fax;
	private String email;
	private String note;
	private String asl;
	private String remoteCaninaUsername;
	private String remoteCaninaPassword;
	private String remoteFelinaUsername;
	private String remoteFelinaPassword;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private Integer enteredBy;
	private Integer modifiedBy;
	private Integer canileBdu;
	private Integer idStabilimentoGisa;
	private Date dataCessazione;
	
	private Set<StrutturaClinica> 	strutturaClinicas 		= new HashSet<StrutturaClinica>(0);	
	private Set<BUtente> 			utentis 				= new HashSet<BUtente>(0);
	private Set<TrasferimentoNoH> 		trasferimentiUscita 	= new HashSet<TrasferimentoNoH>(0);
	private Set<TrasferimentoNoH> 		trasferimentiIngresso 	= new HashSet<TrasferimentoNoH>(0);	
	private Set<MagazzinoFarmaci> 	magazzinoFarmaci 		= new HashSet<MagazzinoFarmaci>(0);
	private Set<MagazzinoArticoliSanitari> 	magazzinoArticoliSanitari 		= new HashSet<MagazzinoArticoliSanitari>(0);
	private Set<MagazzinoMangimi> 	magazzinoMangimi 		= new HashSet<MagazzinoMangimi>(0);
	private Set<BookingClinica> 	booking 				= new HashSet<BookingClinica>(0);

	public ClinicaNoH()
	{
		
	}

	public Set<BUtente> getUtentis() {
		return utentis;
	}

	public void setUtentis(Set<BUtente> utentis) {
		this.utentis = utentis;
	}
	
	public Set<TrasferimentoNoH> getTrasferimentiUscita() {
		return trasferimentiUscita;
	}

	public void setTrasferimentiUscita(Set<TrasferimentoNoH> trasferimentiUscita) {
		this.trasferimentiUscita = trasferimentiUscita;
	}
	
	public Set<TrasferimentoNoH> getTrasferimentiIngresso() {
		return trasferimentiIngresso;
	}

	public void setTrasferimentiIngresso(Set<TrasferimentoNoH> trasferimentiIngresso) {
		this.trasferimentiIngresso = trasferimentiIngresso;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LookupComuni getLookupComuni() {
		return this.lookupComuni;
	}

	public void setLookupComuni(LookupComuni lookupComuni) {
		this.lookupComuni = lookupComuni;
	}
	
	public LookupComuniNOH getLookupComuniNOH() {
		return this.lookupComuniNOH;
	}

	public void setLookupComuniNOH(LookupComuniNOH lookupComuniNOH) {
		this.lookupComuniNOH = lookupComuniNOH;
	}

	public LookupAslNoH getLookupAslNoH() {
		return this.lookupAslNoH;
	}

	public void setLookupAslNoH(LookupAslNoH lookupAslNoH) {
		this.lookupAslNoH = lookupAslNoH;
	}
	
	public LookupAsl getLookupAsl() {
		return this.lookupAsl;
	}

	public void setLookupAsl(LookupAsl lookupAsl) {
		this.lookupAsl = lookupAsl;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomeBreve() {
		return nomeBreve;
	}

	public void setNomeBreve(String nomeBreve) {
		this.nomeBreve = nomeBreve;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getAsl() {
		return this.asl;
	}

	public void setAsl(String asl) {
		this.asl = asl;
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

	public Set<StrutturaClinica> getStrutturaClinicas() {
		return this.strutturaClinicas;
	}

	public void setStrutturaClinicas(Set<StrutturaClinica> strutturaClinicas) {
		this.strutturaClinicas = strutturaClinicas;
	}
	
	public String getRemoteCaninaUsername() {
		return remoteCaninaUsername;
	}

	public void setRemoteCaninaUsername(String remoteCaninaUsername) {
		this.remoteCaninaUsername = remoteCaninaUsername;
	}

	public String getRemoteCaninaPassword() {
		return remoteCaninaPassword;
	}

	public void setRemoteCaninaPassword(String remoteCaninaPassword) {
		this.remoteCaninaPassword = remoteCaninaPassword;
	}
	
	public String getRemoteFelinaUsername() {
		return remoteFelinaUsername;
	}

	public void setRemoteFelinaUsername(String remoteFelinaUsername) {
		this.remoteFelinaUsername = remoteFelinaUsername;
	}

	public String getRemoteFelinaPassword() {
		return remoteFelinaPassword;
	}

	public void setRemoteFelinaPassword(String remoteFelinaPassword) {
		this.remoteFelinaPassword = remoteFelinaPassword;
	}

	public Set<MagazzinoFarmaci> getMagazzinoFarmaci() {
		return magazzinoFarmaci;
	}

	public void setMagazzinoFarmaci(Set<MagazzinoFarmaci> magazzinoFarmaci) {
		this.magazzinoFarmaci = magazzinoFarmaci;
	}
	
	public Set<MagazzinoMangimi> getMagazzinoMangimi() {
		return magazzinoMangimi;
	}

	public void setMagazzinoMangimi(Set<MagazzinoMangimi> magazzinoMangimi) {
		this.magazzinoMangimi = magazzinoMangimi;
	}
	
	public Set<MagazzinoArticoliSanitari> getMagazzinoArticoliSanitari() {
		return magazzinoArticoliSanitari;
	}

	public void setMagazzinoArticoliSanitari(Set<MagazzinoArticoliSanitari> magazzinoArticoliSanitari) {
		this.magazzinoArticoliSanitari = magazzinoArticoliSanitari;
	}
	
	public Integer getCanileBdu() {
		return this.canileBdu;
	}

	public void setCanileBdu(Integer canileBdu) {
		this.canileBdu = canileBdu;
	}
	
	public Integer getIdStabilimentoGisa() {
		return this.idStabilimentoGisa;
	}

	public void setIdStabilimentoGisa(Integer idStabilimentoGisa) {
		this.idStabilimentoGisa = idStabilimentoGisa;
	}

	public Set<BookingClinica> getBooking() {
		return booking;
	}

	public void setBooking(Set<BookingClinica> booking) {
		this.booking = booking;
	}
	
	public Set<TrasferimentoNoH> getTrasferimentiIngressoNoAttesaRifiutatoCriuv() {
		
		Set<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		for(TrasferimentoNoH t : getTrasferimentiIngresso())
		{
			if(t.getStato().getStato()!=1 && t.getStato().getStato()!=6)
				trasf.add(t);
				
		}
		return trasf;
	}
	
	public ArrayList<TrasferimentoNoH> getTrasferimentiIngressoNoAttesaRifiutatoCriuvOrderByStato() {
		ArrayList<TrasferimentoNoH> trasfList = new ArrayList<TrasferimentoNoH>();
		ComparatorTrasferimentiNoH comp = new ComparatorTrasferimentiNoH();
		trasfList.addAll(getTrasferimentiIngressoNoAttesaRifiutatoCriuv());
		Collections.sort(trasfList,comp);
		return trasfList;
	}
	
	public ArrayList<TrasferimentoNoH> getTrasferimentiUscitaOrderByStato() {
		ArrayList<TrasferimentoNoH> trasfList = new ArrayList<TrasferimentoNoH>();
		ComparatorTrasferimentiNoH comp = new ComparatorTrasferimentiNoH();
		trasfList.addAll(getTrasferimentiUscita());
		Collections.sort(trasfList,comp);
		return trasfList;
	}

	public Set<TrasferimentoNoH> getTrasferimentiIngressoDaAccettare() {
		
		Set<TrasferimentoNoH> trasf = new HashSet<TrasferimentoNoH>();
		for(TrasferimentoNoH t : getTrasferimentiIngresso())
		{
			if(t.getStato().getStato()!=1 && t.getStato().getStato()!=6 && t.getDataAccettazioneDestinatario()==null)
				trasf.add(t);
				
		}
		return trasf;
	}
	
	public String toString()
	{
		return nome;
	}
	
	public Date getDataCessazione() {
		return this.dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

}
