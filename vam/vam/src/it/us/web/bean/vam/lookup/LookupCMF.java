/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaCMF;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_cause_morte_finali", schema = "public")
@Where( clause = "enabled" )
public class LookupCMF {

	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	
//	private Set<Autopsia> autopsias = new HashSet<Autopsia>(0);
	private Set<AutopsiaCMF> autopsiaCMF = new HashSet<AutopsiaCMF>(0);
	
	@Transient
	public String getNomeEsame()
	{
		return "Causa morte finale";
	}
	
	public LookupCMF()
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

	@Column(name = "description", length = 128)
	@Length(max = 128)
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

	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "autopsia_causemortefinali", schema = "public", joinColumns = { @JoinColumn(name = "causa_morte", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) })
//	public Set<Autopsia> getAutopsias() {
//		return autopsias;
//	}
//
//	public void setAutopsias(Set<Autopsia> autopsias) {
//		this.autopsias = autopsias;
//	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupCMF")	
	public Set<AutopsiaCMF> getAutopsiaCMF() {
		return autopsiaCMF;
	}

	public void setAutopsiaCMF(Set<AutopsiaCMF> autopsiaCMF) {
		this.autopsiaCMF = autopsiaCMF;
	}
	
	@Override
	public String toString()
	{
		return description;
	}
	

	
}



	

