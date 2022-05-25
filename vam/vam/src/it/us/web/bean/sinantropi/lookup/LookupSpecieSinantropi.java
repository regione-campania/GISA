/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.sinantropi.lookup;

import it.us.web.bean.sinantropi.Sinantropo;
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
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_specie_sinantropi", schema = "public")
@Where( clause = "enabled" )
public class LookupSpecieSinantropi implements java.io.Serializable {
	
	private static final long serialVersionUID = 1414679919315065002L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Set<Sinantropo> sinantropis = new HashSet<Sinantropo>(0);
	
	private Boolean uccello;
	private Boolean mammifero;
	private Boolean rettileAnfibio;
	
	private Boolean uccelloZ;
	private Boolean mammiferoZ;
	private Boolean rettileAnfibioZ;
	
	private Boolean selaci;
	private Boolean mammiferoCetaceo;
	private Boolean rettileTestuggine;
	
	
	public LookupSpecieSinantropi() {
		
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lookupSpecieSinantropi")
	@Where( clause = "trashed_date is null" )
	public Set<Sinantropo> getSinantropis() {
		return sinantropis;
	}


	public void setSinantropis(Set<Sinantropo> sinantropis) {
		this.sinantropis = sinantropis;
	}

	@Column(name = "uccello")
	public Boolean getUccello() {
		return uccello;
	}

	public void setUccello(Boolean uccello) {
		this.uccello = uccello;
	}

	@Column(name = "mammifero_z")
	public Boolean getMammiferoZ() {
		return mammiferoZ;
	}

	public void setMammiferoZ(Boolean mammiferoZ) {
		this.mammiferoZ = mammiferoZ;
	}

	@Column(name = "rettile_anfibio_z")
	public Boolean getRettileAnfibioZ() {
		return rettileAnfibioZ;
	}

	public void setRettileAnfibioZ(Boolean rettileAnfibioZ) {
		this.rettileAnfibioZ = rettileAnfibioZ;
	}
	
	
	@Column(name = "uccello_z")
	public Boolean getUccelloZ() {
		return uccelloZ;
	}

	public void setUccelloZ(Boolean uccelloZ) {
		this.uccelloZ = uccelloZ;
	}

	@Column(name = "mammifero")
	public Boolean getMammifero() {
		return mammifero;
	}

	public void setMammifero(Boolean mammifero) {
		this.mammifero = mammifero;
	}

	@Column(name = "rettile_anfibio")
	public Boolean getRettileAnfibio() {
		return rettileAnfibio;
	}

	public void setRettileAnfibio(Boolean rettileAnfibio) {
		this.rettileAnfibio = rettileAnfibio;
	}
	
	
	
	
	@Column(name = "selaci")
	public Boolean getSelaci() {
		return selaci;
	}

	public void setSelaci(Boolean selaci) {
		this.selaci = selaci;
	}

	@Column(name = "mammifero_cetaceo")
	public Boolean getMammiferoCetaceo() {
		return mammiferoCetaceo;
	}

	public void setMammiferoCetaceo(Boolean mammiferoCetaceo) {
		this.mammiferoCetaceo = mammiferoCetaceo;
	}

	@Column(name = "rettile_testuggine")
	public Boolean getRettileTestuggine() {
		return rettileTestuggine;
	}

	public void setRettileTestuggine(Boolean rettileTestuggine) {
		this.rettileTestuggine = rettileTestuggine;
	}
		
	
	

	
}
