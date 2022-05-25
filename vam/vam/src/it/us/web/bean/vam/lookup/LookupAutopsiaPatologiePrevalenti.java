/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_autopsia_patologie_prevalenti", schema = "public")
@Where( clause = "enabled" )
public class LookupAutopsiaPatologiePrevalenti {

	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Boolean definitiva;
	private Boolean esclusivo;
	
	private Set<LookupAutopsiaOrgani> lookupOrganiAutopsias = new HashSet<LookupAutopsiaOrgani>(0);
	private Set<AutopsiaOrganoPatologie> autopsiaOrganiPatologies = new HashSet<AutopsiaOrganoPatologie>(0);


	@Transient
	public String getNomeEsame()
	{
		return "Quadro Patologico prevalente";
	}
	
	public LookupAutopsiaPatologiePrevalenti()
	{
		
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
	
	@Column(name = "definitiva")
	public Boolean getDefinitiva() {
		return this.definitiva;
	}

	public void setDefinitiva(Boolean definitiva) {
		this.definitiva = definitiva;
	}
	
	@Column(name = "esclusivo")
	public Boolean getEsclusivo() {
		return this.esclusivo;
	}

	public void setEsclusivo(Boolean esclusivo) {
		this.esclusivo = esclusivo;
	}


	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "organi_patologieprevalenti", schema = "public", joinColumns = { @JoinColumn(name = "patologia_prevalente", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "organo", nullable = false, updatable = false) })
	public Set<LookupAutopsiaOrgani> getLookupOrganiAutopsias() {
		return lookupOrganiAutopsias;
	}

	public void setLookupOrganiAutopsias(
			Set<LookupAutopsiaOrgani> lookupOrganiAutopsias) {
		this.lookupOrganiAutopsias = lookupOrganiAutopsias;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "esame_organopatologie", schema = "public", joinColumns = { @JoinColumn(name = "patologia", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) })
	public Set<AutopsiaOrganoPatologie> getAutopsiaOrganiPatologies() {
		return autopsiaOrganiPatologies;
	}

	public void setAutopsiaOrganiPatologies(
			Set<AutopsiaOrganoPatologie> autopsiaOrganiPatologies) {
		this.autopsiaOrganiPatologies = autopsiaOrganiPatologies;
	}

	@Override
	public String toString()
	{
		return description;
	}	
	
	
	
}


