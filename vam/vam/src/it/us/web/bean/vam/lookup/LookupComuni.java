/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.vam.Clinica;

import java.util.HashSet;
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

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_comuni", schema = "public")
@Where( clause = "enabled" )
public class LookupComuni implements java.io.Serializable
{
	private static final long serialVersionUID = 1414679919315065002L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	
	private Boolean bn;
	private Boolean av;
	private Boolean ce;
	private Boolean sa;
	private Boolean na;
	
	private String cap;
	private String codiceIstat;
	
	private Set<Clinica> clinicas = new HashSet<Clinica>(0);
	private Set<LookupDetentori> detentoris = new HashSet<LookupDetentori>(0);

	@Transient
	public String getNomeEsame()
	{
		return "Comune";
	}
	
	public LookupComuni()
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupComuni")
	@Where( clause = "trashed_date is null" )
	public Set<Clinica> getClinicas() {
		return this.clinicas;
	}

	public void setClinicas(Set<Clinica> clinicas) {
		this.clinicas = clinicas;
	}

	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupComuni")
	@Where( clause = "trashed_date is null" )
	public Set<LookupDetentori> getDetentoris() {
		return detentoris;
	}

	public void setDetentoris(Set<LookupDetentori> detentoris) {
		this.detentoris = detentoris;
	}

	@Column(name = "bn")
	public Boolean getBn() {
		return bn;
	}

	public void setBn(Boolean bn) {
		this.bn = bn;
	}

	@Column(name = "av")
	public Boolean getAv() {
		return av;
	}

	public void setAv(Boolean av) {
		this.av = av;
	}

	@Column(name = "ce")
	public Boolean getCe() {
		return ce;
	}

	public void setCe(Boolean ce) {
		this.ce = ce;
	}

	@Column(name = "sa")
	public Boolean getSa() {
		return sa;
	}

	public void setSa(Boolean sa) {
		this.sa = sa;
	}

	@Column(name = "na")
	public Boolean getNa() {
		return na;
	}

	public void setNa(Boolean na) {
		this.na = na;
	}

	@Column(name = "cap")
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	@Column(name = "codice_istat")
	public String getCodiceIstat() {
		return codiceIstat;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}
	
	@Override
	public String toString()
	{
		return description;
	}
	
	
	

}
