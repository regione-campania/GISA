/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import javax.persistence.Column;

public interface RegistrazioniInterface
{
	public Boolean getAdozione();
	public Boolean getAdozioneFuoriAsl();
	public Boolean getAdozioneVersoAssocCanili();
	public void setAdozione(Boolean adozione);
	public void setAdozioneFuoriAsl(Boolean adozioneFuoriAsl);
	public void setAdozioneVersoAssocCanili(Boolean adozioneVersoAssocCanili);
	public Boolean getRitrovamento();
	public void setRitrovamento(Boolean ritrovamento);
	public Boolean getRitrovamentoSmarrNonDenunciato();
	public void setRitrovamentoSmarrNonDenunciato(Boolean ritrovamentoSmarrNonDenunciato);
	public Boolean getSmarrimento();
	public void setSmarrimento(Boolean smarrimento);
	public Boolean getTrasfCanile();
	public void setTrasfCanile(Boolean trasfCanile);
	public Boolean getRitornoProprietario();
	public void setRitornoProprietario(Boolean ritornoProprietario);
	public Boolean getTrasferimento();
	public void setTrasferimento(Boolean trasferimento);
	public Boolean getPassaporto();
	public void setPassaporto(Boolean passaporto);
	public Boolean getFurto();
	public void setFurto(Boolean furto);
	public Boolean getDecesso();
	public void setDecesso(Boolean decesso);
	public Boolean getSterilizzazione();
	public void setSterilizzazione(Boolean sterilizzazione);
	public void setPrelievoDna(Boolean prelievoDna);
	public Boolean getPrelievoDna();
	public void setPrelievoLeishmania(Boolean prelievoLeishmania);
	public Boolean getPrelievoLeishmania();
	public void setRinnovoPassaporto(Boolean rinnovoPassaporto);
	public Boolean getRinnovoPassaporto();
	public Boolean getReimmissione();
	public Boolean getRitornoAslOrigine();
	public Boolean getCessione();
	public void setCessione(Boolean cessione);
	public Boolean getTrasfRegione();
	public void setTrasfRegione(Boolean trasfRegione);
	public Boolean getTrasferimentoResidenzaProp();
	public void setTrasferimentoResidenzaProp(Boolean trasferimentoResidenzaProp);
	public Boolean getRicattura();
	public void setRicattura(Boolean ricattura);
	public Boolean getRitornoCanileOrigine();
	public void setRitornoCanileOrigine(Boolean ritornoCanileOrigine);
}
