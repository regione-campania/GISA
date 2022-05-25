/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_eco_cuore_anomalia", schema = "public")
@Where( clause = "enabled" )
public class LookupEcoCuoreAnomalia implements java.io.Serializable
{
	private static final long serialVersionUID = -8091002289851989785L;
	
	private int id;
	private LookupEcoCuoreAnomalia lookupEcoCuoreAnomalia;
	private LookupEcoCuoreTipo lookupEcoCuoreTipo;
	private String descrizione;
	private Boolean enabled;
	private Set<LookupEcoCuoreAnomalia> anomalieMutuamenteEsclusive;

	@Transient
	public String getNomeEsame()
	{
		return "Anomalia";
	}
	
	public LookupEcoCuoreAnomalia()
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
	@JoinColumn(name = "superesito")
	public LookupEcoCuoreAnomalia getLookupEcoCuoreAnomalia() {
		return this.lookupEcoCuoreAnomalia;
	}

	public void setLookupEcoCuoreAnomalia(
			LookupEcoCuoreAnomalia lookupEcoCuoreAnomalia) {
		this.lookupEcoCuoreAnomalia = lookupEcoCuoreAnomalia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_esame")
	public LookupEcoCuoreTipo getLookupEcoCuoreTipo() {
		return this.lookupEcoCuoreTipo;
	}

	public void setLookupEcoCuoreTipo(
			LookupEcoCuoreTipo lookupEcoCuoreTipo) {
		this.lookupEcoCuoreTipo = lookupEcoCuoreTipo;
	}

	@Column(name = "descrizione", length = 64)
	@Length(max = 64)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "lookup_eco_cuore_anomalie_mutuamente_esclusivi", 
			   joinColumns = 
			   { 
				 	@JoinColumn(
				 				name = "id_anomalia", 
				 				nullable = false, 
				 				updatable = false
						    	) 
			   }, 
			   inverseJoinColumns = 
			   { 
					@JoinColumn(
								name = "id_anomalia_esclusiva", 
								nullable = false, 
								updatable = false
						    	) 
			   }
			  )
	public Set<LookupEcoCuoreAnomalia> getAnomalieMutuamenteEsclusive() {
		return this.anomalieMutuamenteEsclusive;
	}

	public void setAnomalieMutuamenteEsclusive(Set<LookupEcoCuoreAnomalia> anomalieMutuamenteEsclusive) {
		this.anomalieMutuamenteEsclusive = anomalieMutuamenteEsclusive;
	}

	
	
	
}
