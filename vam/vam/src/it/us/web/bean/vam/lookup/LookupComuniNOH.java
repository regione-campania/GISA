/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam.lookup;

import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.vam.Clinica;

import java.util.HashSet;
import java.util.Set;


public class LookupComuniNOH implements java.io.Serializable
{
	private static final long serialVersionUID = 1414679919315065002L;
	
	private int id;
	private String description;
	private Integer level;
	private Boolean enabled;
	
	private Boolean bn;
	private Boolean av;
	private Boolean ce;
	private Boolean sa;
	private Boolean na;
	
	private String cap;
	private String codiceIstat;
	
	private Set<Clinica> clinicas = new HashSet<Clinica>(0);
	private Set<LookupDetentori> detentoris = new HashSet<LookupDetentori>(0);

	public String getNomeEsame()
	{
		return "Comune";
	}
	
	public LookupComuniNOH()
	{
		
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Clinica> getClinicas() {
		return this.clinicas;
	}

	public void setClinicas(Set<Clinica> clinicas) {
		this.clinicas = clinicas;
	}

	
	
	public Set<LookupDetentori> getDetentoris() {
		return detentoris;
	}

	public void setDetentoris(Set<LookupDetentori> detentoris) {
		this.detentoris = detentoris;
	}

	public Boolean getBn() {
		return bn;
	}

	public void setBn(Boolean bn) {
		this.bn = bn;
	}

	public Boolean getAv() {
		return av;
	}

	public void setAv(Boolean av) {
		this.av = av;
	}

	public Boolean getCe() {
		return ce;
	}

	public void setCe(Boolean ce) {
		this.ce = ce;
	}

	public Boolean getSa() {
		return sa;
	}

	public void setSa(Boolean sa) {
		this.sa = sa;
	}

	public Boolean getNa() {
		return na;
	}

	public void setNa(Boolean na) {
		this.na = na;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getCodiceIstat() {
		return codiceIstat;
	}

	public void setCodiceIstat(String codiceIstat) {
		this.codiceIstat = codiceIstat;
	}
	
	@Override
	public String toString()
	{
		return description;
	}
	
	
	

}
