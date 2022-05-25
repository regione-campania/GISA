/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.Ecg;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_autopsia_fenomeni_cadaverici", schema = "public")
@Where( clause = "enabled" )
public class LookupAutopsiaFenomeniCadaverici implements Serializable{
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	private Boolean sceltaSingola;
	private Set<Autopsia> autopsias = new HashSet<Autopsia>(0);
	private LookupAutopsiaFenomeniCadaverici padre;
	private Set<LookupAutopsiaFenomeniCadaverici> figli = new HashSet<LookupAutopsiaFenomeniCadaverici>(0);
	
	public LookupAutopsiaFenomeniCadaverici()
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
	
	@Column(name = "scelta_singola")
	public Boolean getSceltaSingola() {
		return this.sceltaSingola;
	}

	public void setSceltaSingola(Boolean sceltaSingola) {
		this.sceltaSingola = sceltaSingola;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "autopsia_fenomeni_cadaverici", schema = "public", joinColumns = { @JoinColumn(name = "fenomeno_cadaverico", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "autopsia", nullable = false, updatable = false) })
	public Set<Autopsia> getAutopsias() {
		return autopsias;
	}

	public void setAutopsias(Set<Autopsia> autopsias) {
		this.autopsias = autopsias;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "padre")
	public LookupAutopsiaFenomeniCadaverici getPadre() {
		return padre;
	}
	public void setPadre(LookupAutopsiaFenomeniCadaverici padre) {
		this.padre = padre;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre")
	@OrderBy(clause="level asc")
	public Set<LookupAutopsiaFenomeniCadaverici> getFigli() {
		return this.figli;
	}

	public void setFigli(Set<LookupAutopsiaFenomeniCadaverici> figli) {
		this.figli = figli;
	}
	
	@Override
	public String toString()
	{
		return description;
	}
	
	@Transient
	public String getAlberoGenealogicoString()
	{
		String toReturn = this.getDescription();
		LookupAutopsiaFenomeniCadaverici temp = this;
		while(temp.getPadre()!=null)
		{
			temp = temp.getPadre();
			toReturn = temp.getDescription() + " -> " + toReturn;
		}
		return toReturn;
	}
	
	@Transient
	public ArrayList<LookupAutopsiaFenomeniCadaverici> getAlberoGenealogicoArray()
	{
		ArrayList<LookupAutopsiaFenomeniCadaverici> toReturn = new ArrayList<LookupAutopsiaFenomeniCadaverici>();
		toReturn.add(this);
		
		LookupAutopsiaFenomeniCadaverici temp = this;
		while(temp.getPadre()!=null)
		{
			temp = temp.getPadre();
			toReturn.add(temp);
		}
		return toReturn;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Fenomeni Cadaverici";
	}
	
}



	


