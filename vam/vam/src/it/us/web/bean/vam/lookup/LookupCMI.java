/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Autopsia;

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
@Table(name = "lookup_cause_morte_iniziali", schema = "public")
@Where( clause = "enabled" )
public class LookupCMI {

	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Boolean causaNaturale;
	private Set<Autopsia> 	autopsias 	= new HashSet<Autopsia>(0);
	private Set<Sinantropo> sinantropis = new HashSet<Sinantropo>(0);

	@Transient
	public String getNomeEsame()
	{
		return "Causa morte iniziale";
	}
	
	public LookupCMI()
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
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "autopsia_causemorteiniziali", schema = "public", joinColumns = { @JoinColumn(name = "causa_morte", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) })
	public Set<Autopsia> getAutopsias() {
		return autopsias;
	}

	public void setAutopsias(Set<Autopsia> autopsias) {
		this.autopsias = autopsias;
	}

	@Column(name = "causa_naturale")
	public Boolean getCausaNaturale() {
		return causaNaturale;
	}

	public void setCausaNaturale(Boolean causaNaturale) {
		this.causaNaturale = causaNaturale;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupCMI")	
	public Set<Sinantropo> getSinantropis() {
		return sinantropis;
	}

	public void setSinantropis(Set<Sinantropo> sinantropis) {
		this.sinantropis = sinantropis;
	}

	@Override
	public String toString()
	{
		return description;
	}
	
	
	
	
}



	


