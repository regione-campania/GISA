/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_esame_coprologico_elminti", schema = "public")
@Where( clause = "enabled" )
public class LookupEsameCoprologicoElminti implements java.io.Serializable
{
	private static final long serialVersionUID = -3156233441365137861L;
	
	private int id;
	private String description;
	private boolean canina;
	private boolean felina;
	private boolean sinantropi;
	private Integer level;
	private Boolean enabled;

	@Transient
	public String getNomeEsame()
	{
		return "Elminti";
	}
	
	public LookupEsameCoprologicoElminti()
	{
		
	}
	
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

	@Column(name = "canina", nullable = false)
	@NotNull
	public boolean isCanina() {
		return this.canina;
	}

	public void setCanina(boolean canina) {
		this.canina = canina;
	}

	@Column(name = "felina", nullable = false)
	@NotNull
	public boolean isFelina() {
		return this.felina;
	}

	public void setFelina(boolean felina) {
		this.felina = felina;
	}

	@Column(name = "sinantropi", nullable = false)
	@NotNull
	public boolean isSinantropi() {
		return this.sinantropi;
	}

	public void setSinantropi(boolean sinantropi) {
		this.sinantropi = sinantropi;
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

}
