/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.vam.Clinica;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_province", schema = "public")
@Where( clause = "enabled" )
public class LookupProvince implements java.io.Serializable
{
	private static final long serialVersionUID = 1414679919315065002L;
	
	private int code;
	private String description;
	private Boolean defaultItem;
	private Integer level;
	private Boolean enabled;
	private Integer idRegione;
	private Date entered;
	private Date modified;
	
	public LookupProvince()
	{
		
	}

	@Transient
	public String getNomeEsame()
	{
		return "Provincia";
	}
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "code", unique = true, nullable = false)
	@NotNull
	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
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

	@Column(name = "default_item")
	public Boolean getDefaultItem() {
		return this.defaultItem;
	}

	public void setDefaultItem(Boolean defaultItem) {
		this.defaultItem = defaultItem;
	}
	
	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "id_regione")
	public Integer getIdRegione() {
		return this.idRegione;
	}

	public void setIdRegione(Integer idRegione) {
		this.idRegione = idRegione;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "entered", length = 13)
	@NotNull
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	
	@Temporal(TemporalType.DATE)
	@Column(name = "modified", length = 13)
	@NotNull
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	@Override
	public String toString()
	{
		return description;
	}

}
