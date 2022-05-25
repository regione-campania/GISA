/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "lookup_esame_istopatologico_who_umana", schema = "public")
@Where( clause = "enabled" )
public class LookupEsameIstopatologicoWhoUmana implements java.io.Serializable {

	private static final long serialVersionUID = 4924979223182833401L;

	private int id;
	private String description;
	private String codice;
	private Integer level;
	private Boolean enabled;
	private LookupEsameIstopatologicoWhoUmana padre;
	private Set<LookupEsameIstopatologicoWhoUmana> figli = new HashSet<LookupEsameIstopatologicoWhoUmana>(0);
	
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
	
	@Transient
	public String getNomeEsame()
	{
		return "Who Umana";
	}
	
	
	@Column(name = "description", length = 128)
	@Length(max = 128)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "codice", length = 128)
	@Length(max = 128)
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "padre")
	public LookupEsameIstopatologicoWhoUmana getPadre() {
		return padre;
	}
	
	public void setPadre(LookupEsameIstopatologicoWhoUmana padre) {
		this.padre = padre;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre")
	@OrderBy(clause="level asc")
	public Set<LookupEsameIstopatologicoWhoUmana> getFigli() {
		return figli;
	}
	
	public void setFigli(Set<LookupEsameIstopatologicoWhoUmana> figli) {
		this.figli = figli;
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
	
	@Override
	public String toString()
	{
		return (padre == null) ? (description + " [codice ICD-0: " + codice + "]") : (padre.toString() + " -> " + description + " [codice ICD-0: " + codice + "]");
	}
	
}
