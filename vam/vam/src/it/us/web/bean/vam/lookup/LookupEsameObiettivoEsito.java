/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.EsameObiettivoEsito;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_esame_obiettivo_esito", schema = "public")
@Where( clause = "enabled" )
public class LookupEsameObiettivoEsito implements java.io.Serializable
{
	private static final long serialVersionUID = -8091002289851989785L;
	
	private int id;
	private LookupEsameObiettivoEsito lookupEsameObiettivoEsito;
	private LookupEsameObiettivoTipo lookupEsameObiettivoTipo;
	private String description;
	private Integer level;
	private Boolean enabled;
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello3 = new HashSet<EsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello4 = new HashSet<EsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello6 = new HashSet<EsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello7 = new HashSet<EsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello5 = new HashSet<EsameObiettivoEsito>(0);
	private Set<LookupEsameObiettivoEsito> lookupEsameObiettivoEsitos = new HashSet<LookupEsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello2 = new HashSet<EsameObiettivoEsito>(0);
//	private Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello8 = new HashSet<EsameObiettivoEsito>(0);
	private Set<EsameObiettivoEsito> esameObiettivoEsitos = new HashSet<EsameObiettivoEsito>(0);
	private Set<LookupEsameObiettivoEsito> esitiMutuamenteEsclusivi;

	public LookupEsameObiettivoEsito()
	{
		
	}
	
//	@Override
//	public String toString()
//	{
//		return id+"";
//	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Esito";
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
	@JoinColumn(name = "superesito")
	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsito() {
		return this.lookupEsameObiettivoEsito;
	}

	public void setLookupEsameObiettivoEsito(
			LookupEsameObiettivoEsito lookupEsameObiettivoEsito) {
		this.lookupEsameObiettivoEsito = lookupEsameObiettivoEsito;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_esame")
	public LookupEsameObiettivoTipo getLookupEsameObiettivoTipo() {
		return this.lookupEsameObiettivoTipo;
	}

	public void setLookupEsameObiettivoTipo(
			LookupEsameObiettivoTipo lookupEsameObiettivoTipo) {
		this.lookupEsameObiettivoTipo = lookupEsameObiettivoTipo;
	}

	@Column(name = "description", length = 64)
	@Length(max = 64)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello3")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello3() {
//		return this.esameObiettivoEsitosForLivello3;
//	}
//
//	public void setEsameObiettivoEsitosForLivello3(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello3) {
//		this.esameObiettivoEsitosForLivello3 = esameObiettivoEsitosForLivello3;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello4")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello4() {
//		return this.esameObiettivoEsitosForLivello4;
//	}
//
//	public void setEsameObiettivoEsitosForLivello4(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello4) {
//		this.esameObiettivoEsitosForLivello4 = esameObiettivoEsitosForLivello4;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello6")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello6() {
//		return this.esameObiettivoEsitosForLivello6;
//	}
//
//	public void setEsameObiettivoEsitosForLivello6(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello6) {
//		this.esameObiettivoEsitosForLivello6 = esameObiettivoEsitosForLivello6;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello7")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello7() {
//		return this.esameObiettivoEsitosForLivello7;
//	}
//
//	public void setEsameObiettivoEsitosForLivello7(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello7) {
//		this.esameObiettivoEsitosForLivello7 = esameObiettivoEsitosForLivello7;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello5")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello5() {
//		return this.esameObiettivoEsitosForLivello5;
//	}
//
//	public void setEsameObiettivoEsitosForLivello5(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello5) {
//		this.esameObiettivoEsitosForLivello5 = esameObiettivoEsitosForLivello5;
//	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsito")
	public Set<LookupEsameObiettivoEsito> getLookupEsameObiettivoEsitos() {
		return this.lookupEsameObiettivoEsitos;
	}

	public void setLookupEsameObiettivoEsitos(
			Set<LookupEsameObiettivoEsito> lookupEsameObiettivoEsitos) {
		this.lookupEsameObiettivoEsitos = lookupEsameObiettivoEsitos;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello2")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello2() {
//		return this.esameObiettivoEsitosForLivello2;
//	}
//
//	public void setEsameObiettivoEsitosForLivello2(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello2) {
//		this.esameObiettivoEsitosForLivello2 = esameObiettivoEsitosForLivello2;
//	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsitoByLivello8")
//	public Set<EsameObiettivoEsito> getEsameObiettivoEsitosForLivello8() {
//		return this.esameObiettivoEsitosForLivello8;
//	}
//
//	public void setEsameObiettivoEsitosForLivello8(
//			Set<EsameObiettivoEsito> esameObiettivoEsitosForLivello8) {
//		this.esameObiettivoEsitosForLivello8 = esameObiettivoEsitosForLivello8;
//	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupEsameObiettivoEsito")
	public Set<EsameObiettivoEsito> getEsameObiettivoEsitos() {
		return this.esameObiettivoEsitos;
	}

	public void setEsameObiettivoEsitos(
			Set<EsameObiettivoEsito> esameObiettivoEsitos) {
		this.esameObiettivoEsitos = esameObiettivoEsitos;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "lookup_esame_obiettivo_esiti_mutuamente_esclusivi", 
			   joinColumns = 
			   { 
				 	@JoinColumn(
				 				name = "id_esito", 
				 				nullable = false, 
				 				updatable = false
						    	) 
			   }, 
			   inverseJoinColumns = 
			   { 
					@JoinColumn(
								name = "id_esito_esclusivo", 
								nullable = false, 
								updatable = false
						    	) 
			   }
			  )
	public Set<LookupEsameObiettivoEsito> getEsitiMutuamenteEsclusivi() {
		return this.esitiMutuamenteEsclusivi;
	}

	public void setEsitiMutuamenteEsclusivi(Set<LookupEsameObiettivoEsito> esitiMutuamenteEsclusivi) {
		this.esitiMutuamenteEsclusivi = esitiMutuamenteEsclusivi;
	}
	
	@Override
	public String toString()
	{
		return (lookupEsameObiettivoEsito == null) ? (description) : (lookupEsameObiettivoEsito.toString() + " -> " + description);
	}
	
	@Transient
	public Set<Integer> getEsitiMutuamenteEsclusiviString() 
	{
		Iterator<LookupEsameObiettivoEsito> iter = this.esitiMutuamenteEsclusivi.iterator();
		Set<Integer> toReturn = new HashSet<Integer>();
		int i=0;
		while(iter.hasNext())
		{
			toReturn.add(iter.next().getId());
			i++;
		}
		return toReturn;
	}

}
