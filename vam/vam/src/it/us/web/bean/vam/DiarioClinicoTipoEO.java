/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import java.util.HashSet;
import java.util.Set;

import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "diario_clinico_tipo_eo", schema = "public")
public class DiarioClinicoTipoEO implements java.io.Serializable
{
	private static final long serialVersionUID = 6844865422702275480L;
	
	private int id;
	private LookupEsameObiettivoTipo tipo;	
	private DiarioClinico diarioClinico;
	private String osservazioni;
	private Boolean normale;
	private Set<DiarioClinicoEsitoEO> esiti = new HashSet<DiarioClinicoEsitoEO>(0);

	public DiarioClinicoTipoEO()
	{
		
	}
	
	@Override
	public String toString()
	{
		return id+"";
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo")
	public LookupEsameObiettivoTipo getTipo() {
		return this.tipo;
	}

	public void setTipo(LookupEsameObiettivoTipo tipo) {
		this.tipo = tipo;
	}
		

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diario_clinico")
	public DiarioClinico getDiarioClinico() {
		return this.diarioClinico;
	}

	public void setDiarioClinico(DiarioClinico diarioClinico) {
		this.diarioClinico = diarioClinico;
	}

	@Column(name = "osservazioni")
	@Type(type = "text")
	public String getOsservazioni() {
		return this.osservazioni;
	}

	public void setOsservazioni(String osservazioni) {
		this.osservazioni = osservazioni;
	}
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "diarioClinicoTipoEO")
	@OrderBy(clause="id asc")
	public Set<DiarioClinicoEsitoEO> getEsiti() {
		return this.esiti;
	}

	public void setEsiti(Set<DiarioClinicoEsitoEO> esiti) {
		this.esiti = esiti;
	}
	
	@Column(name = "normale")
	@NotNull
	public Boolean getNormale() {
		return this.normale;
	}

	public void setNormale(Boolean normale) {
		this.normale = normale;
	}

}
