/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.vam.Accettazione;

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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_accettazione_attivita_esterna", schema = "public")
@Where( clause = "enabled" )
public class LookupAccettazioneAttivitaEsterna implements java.io.Serializable
{
	private static final long serialVersionUID = 4228259336170709625L;
	
	private int id;
	private String description;
	private Boolean enabled;
	private Boolean effettuabileDaMorto;
	private Set<Accettazione> accettaziones = new HashSet<Accettazione>(0);

	public LookupAccettazioneAttivitaEsterna()
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

	@Column(name = "description")
	@Type(type="text")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name = "effettuabile_da_morto")
	public Boolean getEffettuabileDaMorto() {
		return this.effettuabileDaMorto;
	}

	public void setEffettuabileDaMorto(Boolean effettuabileDaMorto) {
		this.effettuabileDaMorto = effettuabileDaMorto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoTrasferimento")
	@Where( clause = "trashed_date is null" )
	public Set<Accettazione> getAccettaziones() {
		return this.accettaziones;
	}

	public void setAccettaziones(Set<Accettazione> accettaziones) {
		this.accettaziones = accettaziones;
	}

	@Override
	public String toString()
	{
		return description;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Attivit� esterna";
	}
	
}
