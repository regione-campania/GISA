/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.AutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;

import java.util.HashSet;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "lookup_autopsia_organi_tipi_esami_esiti", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"organo","tipo_esame","esito"})})
@Where( clause = "enabled" )
public class LookupAutopsiaOrganiTipiEsamiEsiti {

	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private LookupAutopsiaOrgani    lookupOrganiAutopsia;		
	private LookupAutopsiaTipiEsami lookupAutopsiaTipiEsami;
	private LookupAutopsiaEsitiEsami esito;
	private String valore;
	
	private Set<LookupAutopsiaOrgani> lookupOrganiAutopsias = new HashSet<LookupAutopsiaOrgani>(0);
	private Set<AutopsiaOrganiTipiEsamiEsiti> autopsiaOrganiTipiEsamiEsitis = new HashSet<AutopsiaOrganiTipiEsamiEsiti>(0);


	public LookupAutopsiaOrganiTipiEsamiEsiti()
	{
		
	}
	
	@Override
	public String toString()
	{
		return id+"";
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

	@Column(name = "description", length = 64)
	@Length(max = 64)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "valore")
	@Type(type="text")
	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
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

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "organi_esiti", schema = "public", joinColumns = { @JoinColumn(name = "esito", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "organo", nullable = false, updatable = false) })
	public Set<LookupAutopsiaOrgani> getLookupOrganiAutopsias() {
		return lookupOrganiAutopsias;
	}

	public void setLookupOrganiAutopsias(
			Set<LookupAutopsiaOrgani> lookupOrganiAutopsias) {
		this.lookupOrganiAutopsias = lookupOrganiAutopsias;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "esame_organoesiti", schema = "public", joinColumns = { @JoinColumn(name = "esito", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) })
	public Set<AutopsiaOrganiTipiEsamiEsiti> getAutopsiaOrganiTipiEsamiEsitis() {
		return autopsiaOrganiTipiEsamiEsitis;
	}

	public void setAutopsiaOrganiTipiEsamiEsitis(
			Set<AutopsiaOrganiTipiEsamiEsiti> autopsiaOrganiTipiEsamiEsitis) {
		this.autopsiaOrganiTipiEsamiEsitis = autopsiaOrganiTipiEsamiEsitis;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organo")
	public LookupAutopsiaOrgani getLookupOrganiAutopsia() {
		return lookupOrganiAutopsia;
	}

	public void setLookupOrganiAutopsia(LookupAutopsiaOrgani lookupOrganiAutopsia) {
		this.lookupOrganiAutopsia = lookupOrganiAutopsia;
	}
	
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_esame")
	public LookupAutopsiaTipiEsami getLookupAutopsiaTipiEsami() {
		return lookupAutopsiaTipiEsami;
	}

	public void setLookupAutopsiaTipiEsami(
			LookupAutopsiaTipiEsami lookupAutopsiaTipiEsami) {
		this.lookupAutopsiaTipiEsami = lookupAutopsiaTipiEsami;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esito")
	public LookupAutopsiaEsitiEsami getEsito() {
		return esito;
	}
	public void setEsito(LookupAutopsiaEsitiEsami esito) {
		this.esito = esito;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Sezione Dettaglio Esami";
	}
	
	
	
}



