/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.AutopsiaOrganiTipiEsamiEsiti;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_autopsia_organi", schema = "public")
@Where( clause = "enabled or enabled_sde" )
public class LookupAutopsiaOrgani {

	private static final long serialVersionUID = -6362067633554376948L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Boolean tessuto;
	private Boolean enabledSde;
	private Integer levelSde;
	private Boolean uccelli;
	private Boolean cani;
	private Boolean gatti;
	private Boolean mammiferi;
	private Boolean rettili;
	
	private Set<LookupAutopsiaPatologiePrevalenti> lookupPatologiePrevalentiAutopsias = new HashSet<LookupAutopsiaPatologiePrevalenti>(0);

	
	public LookupAutopsiaOrgani()
	{
		
	}
	
	@Override
	public String toString()
	{
		return description+"";
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
	
	@Column(name = "level_sde")
	public Integer getLevelSde() {
		return this.levelSde;
	}

	public void setLevelSde(Integer levelSde) {
		this.levelSde = levelSde;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
	@Column(name = "tessuto")
	public Boolean getTessuto() {
		return tessuto;
	}

	public void setTessuto(Boolean tessuto) {
		this.tessuto = tessuto;
	}
	
	@Column(name = "enabled_sde")
	public Boolean getEnabledSde() {
		return enabledSde;
	}

	public void setEnabledSde(Boolean enabledSde) {
		this.enabledSde = enabledSde;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy(value="level")
	@JoinTable(name = "organi_patologieprevalenti", schema = "public", joinColumns = { @JoinColumn(name = "organo", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "patologia_prevalente", nullable = false, updatable = false) })
	public Set<LookupAutopsiaPatologiePrevalenti> getLookupPatologiePrevalentiAutopsias() {
		return lookupPatologiePrevalentiAutopsias;
	}

	public void setLookupPatologiePrevalentiAutopsias(
			Set<LookupAutopsiaPatologiePrevalenti> lookupPatologiePrevalentiAutopsias) {
		this.lookupPatologiePrevalentiAutopsias = lookupPatologiePrevalentiAutopsias;
	}
	
	@Column(name = "cani")
	public Boolean getCani() {
		return cani;
	}

	public void setCani(Boolean cani) {
		this.cani = cani;
	}
	
	@Column(name = "gatti")
	public Boolean getGatti() {
		return gatti;
	}

	public void setGatti(Boolean gatti) {
		this.gatti = gatti;
	}
	
	@Column(name = "uccelli")
	public Boolean getUccelli() {
		return uccelli;
	}

	public void setUccelli(Boolean uccelli) {
		this.uccelli = uccelli;
	}
	
	@Column(name = "mammiferi")
	public Boolean getMammiferi() {
		return mammiferi;
	}

	public void setMammiferi(Boolean mammiferi) {
		this.mammiferi = mammiferi;
	}
	
	@Column(name = "rettili")
	public Boolean getRettili() {
		return rettili;
	}

	public void setRettili(Boolean rettili) {
		this.rettili = rettili;
	}
	
	
	@Transient
	public String getNomeEsame()
	{
		return "Organi";
	}

	
}

