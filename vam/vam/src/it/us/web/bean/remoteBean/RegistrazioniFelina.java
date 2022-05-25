/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedNativeQuery;

@Entity
//@Table(name = "sync_registrazioni_felina", schema = "public")
@NamedNativeQuery(name = "GetRegistrazioniFelina", query = "select * from sync_registrazioni_felina where identificativo = :identificativo", resultClass = RegistrazioniFelina.class)

public class RegistrazioniFelina implements RegistrazioniInterface, Serializable
{
	private static final long serialVersionUID = -7289087085008520718L;
	
	private int idGatto;
	private Boolean adozione;
	private Boolean adozioneFuoriAsl;
	private Boolean adozioneVersoAssocCanili;
	private Boolean cessione;
	private Boolean decesso;
	private Boolean furto;
	private Boolean ricattura;
	private Boolean ritrovamento;
	private Boolean ritrovamentoSmarrNonDenunciato;
	private Boolean smarrimento;
	private Boolean trasfCanile;
	private Boolean ritornoProprietario;
	private Boolean trasferimento;
	private Boolean passaporto;
	private Boolean rinnovoPassaporto;
	private Boolean prelievoDna;
	private Boolean prelievoLeishmania;
	private Boolean reimmissione;
	private Boolean ritornoAslOrigine;
	private Boolean sterilizzazione;
	private Boolean trasfRegione;
	private Boolean trasferimentoResidenzaProp;
	private String errorDescription;
	private Boolean ritornoCanileOrigine;
	private int errorCode;
	
	
	@Id
	public int getIdGatto() {
		return this.idGatto;
	}

	public void setIdGatto(int idGatto) {
		this.idGatto = idGatto;
	}
	
	
//	@Id
//	@GeneratedValue( strategy = GenerationType.IDENTITY )
//	@Column(name = "id", unique = true, nullable = false)
//	@NotNull
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	
	@Column(name="adozione")
	public Boolean getAdozione() {
		return adozione;
	}
	public void setAdozione(Boolean adozione) {
		this.adozione = adozione;
	}
	
	@Column(name="adozione_verso_assoc_canili")
	public Boolean getAdozioneVersoAssocCanili() {
		return adozioneVersoAssocCanili;
	}
	public void setAdozioneVersoAssocCanili(Boolean adozioneVersoAssocCanili) {
		this.adozioneVersoAssocCanili = adozioneVersoAssocCanili;
	}
	
	@Column(name="adozionefa")
	public Boolean getAdozioneFuoriAsl() {
		return adozioneFuoriAsl;
	}
	public void setAdozioneFuoriAsl(Boolean adozioneFuoriAsl) {
		this.adozioneFuoriAsl = adozioneFuoriAsl;
	}
	
	@Column(name="decesso")
	public Boolean getDecesso() {
		return decesso;
	}
	public void setDecesso(Boolean decesso) {
		this.decesso = decesso;
	}
	
	@Column(name="furto")
	public Boolean getFurto() {
		return furto;
	}
	public void setFurto(Boolean furto) {
		this.furto = furto;
	}
	
	@Column(name="ritrovamento")
	public Boolean getRitrovamento() {
		return ritrovamento;
	}
	public void setRitrovamento(Boolean ritrovamento) {
		this.ritrovamento = ritrovamento;
	}
	
	
	public Boolean getRitrovamentoSmarrNonDenunciato() {
		return ritrovamentoSmarrNonDenunciato;
	}
	public void setRitrovamentoSmarrNonDenunciato(Boolean ritrovamentoSmarrNonDenunciato) {
		this.ritrovamentoSmarrNonDenunciato = ritrovamentoSmarrNonDenunciato;
	}
	
	@Column(name="smarrimento")
	public Boolean getSmarrimento() {
		return smarrimento;
	}
	public void setSmarrimento(Boolean smarrimento) {
		this.smarrimento = smarrimento;
	}
	
	@Column(name="trasferimento")
	public Boolean getTrasferimento() {
		return trasferimento;
	}
	public void setTrasferimento(Boolean trasferimento) {
		this.trasferimento = trasferimento;
	}
	
	@Column(name="trasfregione")
	public Boolean getTrasfRegione() {
		return trasfRegione;
	}
	public void setTrasfRegione(Boolean trasfRegione) {
		this.trasfRegione = trasfRegione;
	}
	
	@Column(name="trasferimentoResidenzaProp")
	public Boolean getTrasferimentoResidenzaProp() {
		return trasferimentoResidenzaProp;
	}
	public void setTrasferimentoResidenzaProp(Boolean trasferimentoResidenzaProp) {
		this.trasferimentoResidenzaProp = trasferimentoResidenzaProp;
	}
	
	@Column(name="passaporto")
	public Boolean getPassaporto() {
		return (passaporto==null)?(false):(passaporto);
	}
	public void setPassaporto(Boolean passaporto) {
		this.passaporto = passaporto;
	}
	
	@Column(name="rinnovo_passaporto")
	public Boolean getRinnovoPassaporto() {
		return (rinnovoPassaporto==null)?(false):(rinnovoPassaporto);
	}
	public void setRinnovoPassaporto(Boolean rinnovoPassaporto) {
		this.rinnovoPassaporto = rinnovoPassaporto;
	}
	
	
	
	@Column(name="reimmissione")
	public Boolean getReimmissione() {
		return (reimmissione==null)?(false):(reimmissione);
	}
	public void setReimmissione(Boolean reimmissione) {
		this.reimmissione = reimmissione;
	}
	
	@Column(name="ritornoAslOrigine")
	public Boolean getRitornoAslOrigine() {
		return ritornoAslOrigine;
	}
	public void setRitornoAslOrigine(Boolean ritornoAslOrigine) {
		this.ritornoAslOrigine = ritornoAslOrigine;
	}
	
	@Column(name="cessione")
	public Boolean getCessione() {
		return cessione;
	}
	public void setCessione(Boolean cessione) {
		this.cessione = cessione;
	}
	
	@Column(name="prelievodna")
	public Boolean getPrelievoDna() {
		return (prelievoDna==null)?(false):(prelievoDna);
	}
	public void setPrelievoDna(Boolean prelievoDna) {
		this.prelievoDna = prelievoDna;
	}
	
	@Column(name="prelievoLeishmania")
	public Boolean getPrelievoLeishmania() {
		return prelievoLeishmania;
	}

	public void setPrelievoLeishmania(Boolean prelievoLeishmania) {
		this.prelievoLeishmania = prelievoLeishmania;
	}
	
	@Column(name="sterilizzazione")
	public Boolean getSterilizzazione() {
		return sterilizzazione;
	}
	public void setSterilizzazione(Boolean sterilizzazione) {
		this.sterilizzazione = sterilizzazione;
	}
	
	@Transient
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Transient
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Column(name="ricattura")
	public Boolean getRicattura() {
		return ricattura;
	}
	public void setRicattura(Boolean ricattura) {
		this.ricattura = ricattura;
	}
	
	@Column(name="trasfCanile")
	public Boolean getTrasfCanile() {
		return trasfCanile;
	}
	public void setTrasfCanile(Boolean trasfCanile) {
		this.trasfCanile = trasfCanile;
	}
	
	@Column(name="ritornoProprietario")
	public Boolean getRitornoProprietario() {
		return ritornoProprietario;
	}
	public void setRitornoProprietario(Boolean ritornoProprietario) {
		this.ritornoProprietario = ritornoProprietario;
	}
	
	@Column(name="restituzionecanileorigine")
	public Boolean getRitornoCanileOrigine() {
		return ritornoCanileOrigine;
	}

	public void setRitornoCanileOrigine(Boolean ritornoCanileOrigine) {
		this.ritornoCanileOrigine = ritornoCanileOrigine;
	}
	
}
