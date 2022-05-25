/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;
import java.util.Date;

public class RegistrazioniSinantropiResponse implements Serializable
{
	private static final long serialVersionUID = -1512473086466499197L;
	
	private int idRegistrazione;
	private int idSinantropo;
	private String mcSinantropo;
	private String tipoRegistrazione;
	private String note;
	private Date dataEvento;
	private int idTipoDecesso;
	private String decessoValue;
	private boolean isDataDecessoPresunta;
	
	public int getIdRegistrazione() {
		return idRegistrazione;
	}
	public void setIdRegistrazione(int idRegistrazione) {
		this.idRegistrazione = idRegistrazione;
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
	public void setMcSinantropo(String mcSinantropo) {
		this.mcSinantropo = mcSinantropo;
	}
	public String getMcSinantropo() {
		return mcSinantropo;
	}
	public void setIdSinantropo(int idSinantropo) {
		this.idSinantropo = idSinantropo;
	}
	public int getIdSinantropo() {
		return idSinantropo;
	}
	public String getDecessoValue() {
		return decessoValue;
	}
	public void setDecessoValue(String decessoValue) {
		this.decessoValue = decessoValue;
	}
	public boolean isDataDecessoPresunta() {
		return isDataDecessoPresunta;
	}
	public void setDataDecessoPresunta(boolean isDataDecessoPresunta) {
		this.isDataDecessoPresunta = isDataDecessoPresunta;
	}
	public String getDataMorteCertezza() 
	{
		if(isDataDecessoPresunta)
			return "Presunta";
		else
			return "Certa";
	}
}
