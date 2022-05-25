/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.BUtente;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Clinica;
import it.us.web.util.vam.ComparatorUtenti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

public class LookupAslNoH implements java.io.Serializable
{
	private static final long serialVersionUID = 4228259336170709625L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Set<Accettazione> accettaziones = new HashSet<Accettazione>(0);
	private Set<Clinica> clinicas = new HashSet<Clinica>(0);
	private Set<ProprietarioCane>  proprietariCane  = new HashSet<ProprietarioCane>(0);
	private Set<ProprietarioGatto> proprietariGatto = new HashSet<ProprietarioGatto>(0);
	private Set<LookupPersonaleInterno> personaleInterno = new HashSet<LookupPersonaleInterno>(0);
	private Set<BUtente> referentes = new HashSet<BUtente>(0);
	
	public LookupAslNoH()
	{
		
	}

	public ArrayList<BUtente> getUtenti()
	{
		ArrayList<BUtente> ret = new ArrayList<BUtente>();
		Iterator<Clinica> cliniche = this.getClinicas().iterator();

		while( cliniche.hasNext() )
		{
			Clinica clinicaTemp = cliniche.next();
			Iterator<BUtente> utentiTemp = clinicaTemp.getUtentis().iterator();
			while( utentiTemp.hasNext() )
			{
				BUtente utenteTemp = utentiTemp.next();
				if(utenteTemp.getEnabled())
					ret.add(utenteTemp);
			}
		}
		
		return ret;
	}
	
	public ArrayList<BUtente> getUtentiDistinct()
	{
		ArrayList<BUtente> ret = new ArrayList<BUtente>();
		
		Hashtable<Integer, BUtente> temp = new Hashtable<Integer, BUtente>();
		Hashtable<String, String> nominativi = new Hashtable<String, String>();
		for( Clinica clinica: clinicas )
		{
			for( BUtente utente: clinica.getUtentis() )
			{
				if(utente.getEnabled() && temp.get(utente.getSuperutente().getId())==null && nominativi.get(utente.toString().toUpperCase())==null)
				{
					temp.put( new Integer( utente.getSuperutente().getId() ), utente );
					nominativi.put(utente.toString().toUpperCase(), utente.toString().toUpperCase());
				}
			}
		}
		Enumeration<BUtente> e = temp.elements();
		
		while( e.hasMoreElements() )
		{
			ret.add( e.nextElement() );
		}
		
		Collections.sort(ret, new ComparatorUtenti());
		
		return ret;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Accettazione> getAccettaziones() {
		return this.accettaziones;
	}

	public void setAccettaziones(Set<Accettazione> accettaziones) {
		this.accettaziones = accettaziones;
	}

	public Set<Clinica> getClinicas() {
		return this.clinicas;
	}

	public void setClinicas(Set<Clinica> clinicas) {
		this.clinicas = clinicas;
	}
	
	public String toString()
	{
		return description;
	}
	
	public Set<ProprietarioCane> getProprietariCane() {
		return this.proprietariCane;
	}

	public void setProprietariCane(Set<ProprietarioCane> proprietariCane) {
		this.proprietariCane = proprietariCane;
	}
	
	public Set<ProprietarioGatto> getProprietariGatto() {
		return this.proprietariGatto;
	}

	public void setProprietariGatto(Set<ProprietarioGatto> proprietariGatto) {
		this.proprietariGatto = proprietariGatto;
	}
	
	
	public Set<LookupPersonaleInterno> getPersonaleInterno() {
		return personaleInterno;
	}

	public void setPersonaleInterno(Set<LookupPersonaleInterno> personaleInterno) {
		this.personaleInterno = personaleInterno;
	}
	
	public Set<BUtente> getReferentes() {
		return referentes;
	}

	public void setReferentes(Set<BUtente> referentes) {
		this.referentes = referentes;
	}
	
	public String getNomeEsame()
	{
		return "Alimentazione";
	}
}
