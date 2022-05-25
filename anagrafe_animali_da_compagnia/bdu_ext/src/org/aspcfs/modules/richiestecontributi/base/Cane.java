/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Cane {

	public enum TipoAnimale {CANE,GATTO};
	private String microchip;
	private int id_cane;
	private String proprietario;
	private boolean pagato;
	private Timestamp approvato;
	private String tipologia;
	//comune di cattura per i cani catturati o comune del proprietario per i padronali
	private String comune_cattura;
	//data di sterilizzazione
	private Timestamp data_ster;
	private String numero_protocollo;
	private int asl;
	private String descrizioneAsl;
	private String comune_proprietario;
	private int id_pratica;
	private String comune_colonia;
	
	private TipoAnimale tipo_animale;
	
	
	public String getMicrochip() {
		return microchip;
	}
	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}
	public int getId_cane() {
		return id_cane;
	}
	public void setId_cane(int id_cane) {
		this.id_cane = id_cane;
	}
	public String getProprietario() {
		return proprietario;
	}
	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	
	public boolean isPagato() {
		return pagato;
	}
	public void setPagato(boolean pagato) {
		this.pagato = pagato;
	}
	
	public void setDataApprovazione(java.sql.Timestamp approvato) {
		this.approvato = approvato;
	}
	public java.sql.Timestamp getDataApprovazione() {
		return approvato;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipo) {
		this.tipologia=tipo;
	}
	
	public String getComuneCattura() {
		return comune_cattura;
	}
	public void setComuneCattura(String comune) {
		this.comune_cattura=comune;
	}
	
	public void setDataSterilizzazione(java.sql.Timestamp data_ster) {
		this.data_ster = data_ster;
	}
	
	public java.sql.Timestamp getDataSterilizzazione() {
		return data_ster;
	}
	
	public String getNumeroProtocollo() {
		return numero_protocollo;
	}
	
	public void setNumeroProtocollo(String num_prot) {
		this.numero_protocollo=num_prot;
	}
	public void setAsl(int asl2) {
		this.asl = asl2;
	}
	public int getAsl() {
		return asl;
	}
	
	public static String formatData(Timestamp timestamp)
	{
		return (timestamp == null) ? ("") : (sdf.format( timestamp ));
	}

	static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
		
	
	public String getDescrizioneAsl() {
		return descrizioneAsl;
	}
	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}
	public void setComune_proprietario(String comune_proprietario) {
		this.comune_proprietario = comune_proprietario;
	}
	public String getComune_proprietario() {
		return comune_proprietario;
	}
	public void setId_pratica(int id_pratica) {
		this.id_pratica = id_pratica;
	}
	public int getId_pratica() {
		return id_pratica;
	}
	
	
	public void setComune_colonia(String comune_colonia) {
		this.comune_colonia = comune_colonia;
	}
	public String getComune_colonia() {
		return comune_colonia;
	}
	public void setTipo_animale(TipoAnimale tipo_animale) {
		this.tipo_animale = tipo_animale;
	}
	public TipoAnimale getTipo_animale() {
		return tipo_animale;
	}
}
