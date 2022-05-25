/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.vam.lookup.LookupCMF;
import it.us.web.bean.vam.lookup.LookupDiagnosi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "autopsia_cmf", schema = "public")
public class AutopsiaCMF implements java.io.Serializable {
	
	private int id;
	private Autopsia autopsia;
	private LookupCMF lookupCMF;	
	private boolean provata;
	private String description;
	
	public AutopsiaCMF() {
		
	}
	
	
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
	
	@Override
	public String toString()
	{
		description = lookupCMF.getDescription();
		if(provata)
			description += " - Provata";
		else
			description += " - Sospetta";
		return description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autopsia")
	public Autopsia getAutopsia() {
		return autopsia;
	}


	public void setAutopsia(Autopsia autopsia) {
		this.autopsia = autopsia;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lookup_cmf")
	public LookupCMF getLookupCMF() {
		return lookupCMF;
	}


	public void setLookupCMF(LookupCMF lookupCMF) {
		this.lookupCMF = lookupCMF;
	}


	@Column(name = "provata", nullable = false)
	@NotNull
	public boolean isProvata() {
		return provata;
	}


	public void setProvata(boolean provata) {
		this.provata = provata;
	}
	

	@Transient
	public String getProvataReferto() 
	{
		if(provata)
			return "Provata";
		else
			return "Sospetta";
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Causa Morte Finale";
	}
	
}
