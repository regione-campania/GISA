/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameIstopatologico;

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
@Table(name = "lookup_alimentazioni", schema = "public")
@Where( clause = "enabled" )
public class LookupAlimentazioni implements java.io.Serializable {

	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	
	private Set<CartellaClinica> cartellaClinicas = new HashSet<CartellaClinica>(0);

	private Set<EsameIstopatologico> esameIstopatologos = new HashSet<EsameIstopatologico>(0);
	
	@Override
	public String toString()
	{
		return description;
	}
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return id;
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_alimentazioni", schema = "public", joinColumns = { @JoinColumn(name = "alimentazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "cc", nullable = false, updatable = false) })
	public Set<CartellaClinica> getCartellaClinicas() {
		return cartellaClinicas;
	}
	public void setCartellaClinicas(Set<CartellaClinica> cartellaClinicas) {
		this.cartellaClinicas = cartellaClinicas;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "animali_alimentazioni_outside", schema = "public", joinColumns = { @JoinColumn(name = "alimentazione", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "esame", nullable = false, updatable = false) })
	public Set<EsameIstopatologico> getEsameIstopatologos() {
		return esameIstopatologos;
	}
	public void setEsameIstopatologos(Set<EsameIstopatologico> esameIstopatologos) {
		this.esameIstopatologos = esameIstopatologos;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Alimentazione";
	}

	
	


}

	

