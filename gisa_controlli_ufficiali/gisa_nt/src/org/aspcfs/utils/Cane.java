/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.darkhorseventures.framework.beans.GenericBean;

public class Cane extends GenericBean  implements Serializable 
{
	private static final long serialVersionUID = -8622138631789926823L;
	
	private int id;
	private String mc;
	private String razza;
	private String sesso;
	private String dettagliAddizionali;
	private String segniParticolari;
	private Date dataNascita;
	private int idTaglia;
	
	private String nominativoProprietario;
	private String nominativoDetentore;
	private Date dataMicrochip;
	
	private String aslRiferimentoStringa;
	private boolean isVivo;
	private boolean isCatturato;
	
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRazza() {
		return razza;
	}
	public void setRazza(String razza) {
		this.razza = razza;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDettagliAddizionali() {
		return dettagliAddizionali;
	}
	public void setDettagliAddizionali(String dettagliAddizionali) {
		this.dettagliAddizionali = dettagliAddizionali;
	}
	public String getSegniParticolari() {
		return segniParticolari;
	}
	public void setSegniParticolari(String segniParticolari) {
		this.segniParticolari = segniParticolari;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public int getIdTaglia() {
		return idTaglia;
	}
	public void setIdTaglia(int idTaglia) {
		this.idTaglia = idTaglia;
	}
//	public String getDataNascitaString() {
//		return new SimpleDateFormat("dd/MM/yyyy").format(this.getDataNascita());
//	}
	
	public String getNominativoProprietario() {
		return nominativoProprietario;
	}
	public void setNominativoProprietario(String nominativoProprietario) {
		this.nominativoProprietario = nominativoProprietario;
	}
	public String getNominativoDetentore() {
		return nominativoDetentore;
	}
	public void setNominativoDetentore(String nominativoDetentore) {
		this.nominativoDetentore = nominativoDetentore;
	}
	public Date getDataMicrochip() {
		return dataMicrochip;
	}
	public void setDataMicrochip(Date dataMicrochip) {
		this.dataMicrochip = dataMicrochip;
	}
//	public String getDataMicrochipString() {
//		return new SimpleDateFormat("dd/MM/yyyy").format(this.getDataMicrochip());
//	}
	
	public String getAslRiferimentoStringa() {
		return aslRiferimentoStringa;
	}
	public void setAslRiferimentoStringa(String aslRiferimentoStringa) {
		this.aslRiferimentoStringa = aslRiferimentoStringa;
	}
	public boolean isVivo() {
		return isVivo;
	}
	public void setVivo(boolean isVivo) {
		this.isVivo = isVivo;
	}
	public boolean isCatturato() {
		return isCatturato;
	}
	public void setCatturato(boolean isCatturato) {
		this.isCatturato = isCatturato;
	}
	
	
	
}
