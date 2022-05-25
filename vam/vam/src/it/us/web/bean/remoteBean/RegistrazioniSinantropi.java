/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;

import javax.persistence.Column;

public class RegistrazioniSinantropi implements RegistrazioniInterface, Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private int idSinantropo;
	private String identificativo;
	
	private Boolean cattura;	
	private Boolean decesso;
	private Boolean detentore;	
	private Boolean reimmissione;
	private Boolean ritornoAslOrigine;
	private Boolean sterilizzazione;
	private Boolean furto;
	
	public int getIdSinantropo() {
		return idSinantropo;
	}
	public void setIdSinantropo(int idSinantropo) {
		this.idSinantropo = idSinantropo;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public boolean isCattura() {
		return cattura;
	}
	public void setCattura(boolean cattura) {
		this.cattura = cattura;
	}
	public Boolean getDecesso() {
		return decesso;
	}
	public void setDecesso(Boolean decesso) {
		this.decesso = decesso;
	}
	public boolean isDetentore() {
		return detentore;
	}
	public void setDetentore(boolean detentore) {
		this.detentore = detentore;
	}
	public boolean isReimmissione() {
		return reimmissione;
	}
	public void setReimmissione(boolean reimmissione) {
		this.reimmissione = reimmissione;
	}
	
	@Column(name="ritornoAslOrigine")
	public Boolean getRitornoAslOrigine() {
		return ritornoAslOrigine;
	}
	public void setRitornoAslOrigine(Boolean ritornoAslOrigine) {
		this.ritornoAslOrigine = ritornoAslOrigine;
	}
	public Boolean getSterilizzazione() {
		return sterilizzazione;
	}
	public void setSterilizzazione(Boolean sterilizzazione) {
		this.sterilizzazione = sterilizzazione;
	}
	
	
	@Override
	public Boolean getAdozione() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean getFurto() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean getPassaporto() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean getRitrovamento() {
		// TODO Auto-generated method stub
		return false;
	}
	public Boolean getRitrovamentoSmarrNonDenunciato() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean getSmarrimento() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean getTrasferimento() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setAdozione(Boolean adozione) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFurto(Boolean furto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPassaporto(Boolean passaporto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRitrovamento(Boolean ritrovamento) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRitrovamentoSmarrNonDenunciato(Boolean ritrovamentoSmarrNonDenunciato) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSmarrimento(Boolean smarrimento) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTrasferimento(Boolean trasferimento) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getAdozioneFuoriAsl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAdozioneVersoAssocCanili(Boolean adozioneVersoAssocCanili) {
		// TODO Auto-generated method stub
		
	}
	
	public Boolean getAdozioneVersoAssocCanili() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAdozioneFuoriAsl(Boolean adozioneFuoriAsl) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setPrelievoDna(Boolean prelievoDna) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getPrelievoDna() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setPrelievoLeishmania(Boolean prelievoLeishmania) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getPrelievoLeishmania() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean getReimmissione() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean getCessione() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCessione(Boolean cessione) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getTrasfRegione() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setTrasfRegione(Boolean trasfRegione) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getTrasferimentoResidenzaProp() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setTrasferimentoResidenzaProp(Boolean trasferimentoResidenzaProp) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRinnovoPassaporto(Boolean rinnovoPassaporto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getRinnovoPassaporto() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean getRicattura() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRicattura(Boolean ricattura) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getTrasfCanile() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setTrasfCanile(Boolean trasfCanile) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getRitornoProprietario() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRitornoProprietario(Boolean ritornoProprietario) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Boolean getRitornoCanileOrigine() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRitornoCanileOrigine(Boolean ritornoCanileOrigine) {
		// TODO Auto-generated method stub
		
	}
	
		
	
}