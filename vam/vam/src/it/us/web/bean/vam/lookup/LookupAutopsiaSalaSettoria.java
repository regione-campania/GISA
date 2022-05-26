/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.Animale;

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
@Table(name = "lookup_autopsia_sala_settoria", schema = "public")
@Where( clause = "enabled" )
public class LookupAutopsiaSalaSettoria implements java.io.Serializable
{
	private static final long serialVersionUID = -6362067633554376948L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private String esameRiferimento;
	private Boolean esterna;
	
	private Set<Animale> animales = new HashSet<Animale>(0);

	public LookupAutopsiaSalaSettoria()
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
	
	@Column(name = "esterna")
	public Boolean getEsterna() {
		return this.esterna;
	}

	public void setEsterna(Boolean esterna) {
		this.esterna = esterna;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@Where( clause = "trashed_date is null" )
	public Set<Animale> getAnimales() {
		return this.animales;
	}

	public void setAnimales(Set<Animale> animales) {
		this.animales = animales;
	}
	
	@Override
	public String toString()
	{
		return description;
	}
	
	@Column(name = "esame_riferimento")
	public String getEsameRiferimento() {
		return this.esameRiferimento;
	}

	public void setEsameRiferimento(String esameRiferimento) {
		this.esameRiferimento = esameRiferimento;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Sala settoria destinazione";
	}
	

}