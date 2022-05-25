/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

public class RegistrazioniCaninaResponse implements Serializable
{
	private static final long serialVersionUID = -1512473086466499197L;
	
	private int idRegistrazione;
	private int idCane;
	private int idProprietario;
	private String mcCane;
	private String tipoRegistrazione;
	private String note;
	private String proprietario_detentore;
	private String numeroPassaporto;
	private Date dataEvento;
	private int idTipoDecesso;
	private float importoSanzioneSmarrimento;
	private String decessoValue;
	private Boolean dataDecessoPresunta;
	private String urlChiamato;
	
	
	private String errorDescription;
	private int errorCode;
		
	
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getIdRegistrazione() {
		return idRegistrazione;
	}
	public void setIdRegistrazione(int idRegistrazione) {
		this.idRegistrazione = idRegistrazione;
	}
	public int getIdCane() {
		return idCane;
	}
	public void setIdCane(int idCane) {
		this.idCane = idCane;
	}
	public int getIdProprietario() {
		return idProprietario;
	}
	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
	public String getMcCane() {
		return mcCane;
	}
	public void setMcCane(String mcCane) {
		this.mcCane = mcCane;
	}
	public String getTipoRegistrazione() {
		return tipoRegistrazione;
	}
	public void setTipoRegistrazione(String tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProprietario_detentore() {
		return proprietario_detentore;
	}
	public void setProprietario_detentore(String proprietario_detentore) {
		this.proprietario_detentore = proprietario_detentore;
	}
	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}
	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	public int getIdTipoDecesso() {
		return idTipoDecesso;
	}
	public void setIdTipoDecesso(int idTipoDecesso) {
		this.idTipoDecesso = idTipoDecesso;
	}
	public float getImportoSanzioneSmarrimento() {
		return importoSanzioneSmarrimento;
	}
	public void setImportoSanzioneSmarrimento(float importoSanzioneSmarrimento) {
		this.importoSanzioneSmarrimento = importoSanzioneSmarrimento;
	}
	
	public String getDecessoValue() {
		return decessoValue;
	}
	public void setDecessoValue(String decessoValue) {
		this.decessoValue = decessoValue;
	}
	public Boolean getDataDecessoPresunta() {
		return dataDecessoPresunta;
	}
	public void setDataDecessoPresunta(Boolean dataDecessoPresunta) {
		this.dataDecessoPresunta = dataDecessoPresunta;
	}
	public void setUrlChiamato(String urlChiamato) {
		this.urlChiamato = urlChiamato;
	}
	public String getUrlChiamato() {
		return urlChiamato;
	}
	public String getDataMorteCertezza() 
	{
		if(dataDecessoPresunta!=null)
		{
			if(dataDecessoPresunta)
				return "Presunta";
			else
				return "Certa";
		}
		else
			return "";
	}
}
