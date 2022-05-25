/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "lookup_operazioni_accettazione_condizionate", schema = "public")
@Where( clause = "enabled" )
public class LookupOperazioniAccettazioneCondizionate implements java.io.Serializable
{
	private static final long serialVersionUID = -7225928276157167124L;
	
	private int id;
	private String operazioneDaFare;
	private Boolean enabled;
	private LookupOperazioniAccettazione operazioneCondizionata  = new LookupOperazioniAccettazione();
	private LookupOperazioniAccettazione operazioneCondizionante = new LookupOperazioniAccettazione();

	public LookupOperazioniAccettazioneCondizionate()
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

	@Column(name = "operazione_da_fare", length = 64)
	@Length(max = 64)
	public String getOperazioneDaFare() {
		return this.operazioneDaFare;
	}

	public void setOperazioneDaFare(String operazioneDaFare) {
		this.operazioneDaFare = operazioneDaFare;
	}


	@Column(name = "enabled")
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operazione_condizionante")
	@NotNull
	public LookupOperazioniAccettazione getOperazioneCondizionante() {
		return this.operazioneCondizionante;
	}
	
	public void setOperazioneCondizionante(LookupOperazioniAccettazione operazioneCondizionante) {
		this.operazioneCondizionante = operazioneCondizionante;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operazione_condizionata")
	@NotNull
	public LookupOperazioniAccettazione getOperazioneCondizionata() {
		return this.operazioneCondizionata;
	}
	
	public void setOperazioneCondizionata(LookupOperazioniAccettazione operazioneCondizionata) {
		this.operazioneCondizionata = operazioneCondizionata;
	}
	
}
