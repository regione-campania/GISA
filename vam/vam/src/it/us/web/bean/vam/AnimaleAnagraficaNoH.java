/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import java.util.Date;

import javax.persistence.Column;

public class AnimaleAnagraficaNoH implements java.io.Serializable
{
	private static final long serialVersionUID = -5683301989406129454L;
	
	private String  taglia;
	private String  sesso;
	private String  mantello;
	private String  razza;
	private Integer idTaglia;
	private Integer idMantello;
	private Integer idRazza;
	private String  statoAttuale;
	private String  sterilizzato;
	private Date    dataSterilizzazione;
	private String  operatoreSterilizzazione;
	private AnimaleNoH animale;
	
	public Integer getIdTaglia() {
		return idTaglia;
	}

	public void setIdTaglia(Integer idTaglia) {
		this.idTaglia = idTaglia;
	}

	public Integer getIdMantello() {
		return idMantello;
	}

	public void setIdMantello(Integer idMantello) {
		this.idMantello = idMantello;
	}

	public Integer getIdRazza() {
		return idRazza;
	}

	public void setIdRazza(Integer idRazza) {
		this.idRazza = idRazza;
	}

	public AnimaleAnagraficaNoH()
	{
		
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getMantello() {
		return mantello;
	}

	public void setMantello(String mantello) {
		this.mantello = mantello;
	}

	public String getRazza() {
		return razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}
	
	public String getStatoAttuale() 
	{
		if(this.statoAttuale==null)
			return "";
		else
			return statoAttuale;
	}
	public void setStatoAttuale(String statoAttuale) 
	{
		this.statoAttuale = statoAttuale;
	}
	
	public Date getDataSterilizzazione() 
	{
		return this.dataSterilizzazione;
	}
	public void setDataSterilizzazione(Date dataSterilizzazione) 
	{
		this.dataSterilizzazione = dataSterilizzazione;
	}
	
	public String getOperatoreSterilizzazione() 
	{
		return this.operatoreSterilizzazione;
	}
	public void setOperatoreSterilizzazione(String operatoreSterilizzazione) 
	{
		this.operatoreSterilizzazione = operatoreSterilizzazione;
	}
	
	public String getSterilizzato() 
	{
		if(this.sterilizzato!=null)
			return this.sterilizzato;
		else
			return "";
	}
	public void setSterilizzato(String sterilizzato) 
	{
		this.sterilizzato = sterilizzato;
	}
	
	public AnimaleNoH getAnimale() {
		return animale;
	}

	public void setAnimale(AnimaleNoH animale) {
		this.animale = animale;
	}

}

