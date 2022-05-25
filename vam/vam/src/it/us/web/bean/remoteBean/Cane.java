/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;


import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;

import java.io.Serializable;
import java.util.Date;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "sync_cane", schema = "public")
@Where( clause = "trashed_date is null" )
public class Cane implements Serializable
{
	private static final long serialVersionUID = -8622138631789926823L;
	
	private int id;
	private String mc;
	private String sesso;
	private Integer razza;
	private Integer mantello;
	private String descrizioneRazza;
	private String descrizioneMantello;
	private Integer idTaglia;
	private String descrizioneTaglia;
	private Date dataNascita;
	private Date dataDecesso;
	private Integer idTipoDecesso;
	private String decessoValue;
	private Float importoSanzioneSmarrimento;
	private String noteRitrovamento;
	private Boolean ricatturato;
	private Boolean circuitoCommerciale;
	private String statoAttuale;
	private Boolean sterilizzato;
	private String errorDescription;
	private Integer errorCode;
	private Boolean reimmesso;
	private Boolean isDataDecessoPresunta;
	private Boolean isDataNascitaPresunta;
	private Date trashedDate;
	private Date dataSterilizzazione;
	private String operatoreSterilizzazione;
	private String tatuaggio;
	private Date dataRegistrazione;
	private Integer idProprietario;
	private Integer idDetentore;
	private Date dataChippatura;
	
	@Column(name="id_tipo_decesso")
	public Integer getIdTipoDecesso() {
		return idTipoDecesso;
	}
	public void setIdTipoDecesso(Integer idTipoDecesso) {
		this.idTipoDecesso = idTipoDecesso;
	}
	
	/*@Column(name="tipo_detentore_originario")
	public Integer getDetentoreTipoOriginario() {
		return detentoreTipoOriginario;
	}
	public void setDetentoreTipoOriginario(Integer detentoreTipoOriginario) {
		this.detentoreTipoOriginario = detentoreTipoOriginario;
	}*/
	
	/*@Column(name="tipo_proprietario_originario")
	public Integer getProprietarioTipoOriginario() {
		return proprietarioTipoOriginario;
	}
	public void setProprietarioTipoOriginario(Integer proprietarioTipoOriginario) {
		this.proprietarioTipoOriginario = proprietarioTipoOriginario;
	}*/
	
	@Column(name = "mc")
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	
	@Column(name="decesso_value")
	public String getDecessoValue() {
		return decessoValue;
	}
	public void setDecessoValue(String decessoValue) {
		this.decessoValue = decessoValue;
	}
	
	@Column(name="importo_sanzione_smarrimento")
	public Float getImportoSanzioneSmarrimento() {
		return importoSanzioneSmarrimento;
	}
	public void setImportoSanzioneSmarrimento(Float importoSanzioneSmarrimento) {
		this.importoSanzioneSmarrimento = importoSanzioneSmarrimento;
	}
	
	@Column(name="stato_attuale")
	public String getStatoAttuale() {
		if(statoAttuale==null)
			return "";
		else
			return statoAttuale;
	}
	public void setStatoAttuale(String statoAttuale) {
		this.statoAttuale = statoAttuale;
	}
	
	@Column(name="ricatturato")
	public Boolean getRicatturato() {
		return ricatturato;
	}
	public void setRicatturato(Boolean ricatturato) {
		this.ricatturato = ricatturato;
	}
	
	@Column(name="sterilizzato")
	public Boolean getSterilizzato() {
		if(sterilizzato!=null)
			return sterilizzato;
		else
			return false;
	}
	public void setSterilizzato(Boolean sterilizzato) {
		this.sterilizzato = sterilizzato;
	}
	
	/*@Column(name="detentore_tipo")
	public Integer getDetentoreTipo() {
		return detentoreTipo;
	}
	public void setDetentoreTipo(Integer detentoreTipo) {
		this.detentoreTipo = detentoreTipo;
	}*/
	
	/*@Column(name="proprietario_tipo")
	public Integer getProprietarioTipo() {
		return proprietarioTipo;
	}
	public void setProprietarioTipo(Integer proprietarioTipo) {
		this.proprietarioTipo = proprietarioTipo;
	}*/
	
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
	
	@Column(name = "razza")
	public Integer getRazza() {
		return razza;
	}
	public void setRazza(Integer razza) {
		this.razza = razza;
	}
	
	@Column(name = "mantello")
	public Integer getMantello() {
		return mantello;
	}
	public void setMantello(Integer mantello) {
		this.mantello = mantello;
	}
	
	@Column(name = "descrizione_razza")
	public String getDescrizioneRazza() {
		return descrizioneRazza;
	}
	public void setDescrizioneRazza(String descrizioneRazza) {
		this.descrizioneRazza = descrizioneRazza;
	}
	
	@Column(name = "descrizione_mantello")
	public String getDescrizioneMantello() {
		return descrizioneMantello;
	}
	public void setDescrizioneMantello(String descrizioneMantello) {
		this.descrizioneMantello = descrizioneMantello;
	}
	
	@Column(name = "sesso")
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	@Column(name = "data_nascita")
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	@Column(name = "descrizione_taglia")
	public String getDescrizioneTaglia() {
		return descrizioneTaglia;
	}
	public void setDescrizioneTaglia(String descrizioneTaglia) {
		this.descrizioneTaglia = descrizioneTaglia;
	}
	
	@Column(name = "id_taglia")
	public Integer getIdTaglia() {
		return idTaglia;
	}
	public void setIdTaglia(Integer idTaglia) {
		this.idTaglia = idTaglia;
	}
	
	@Column(name = "circuito_commerciale")
	public Boolean getCircuitoCommerciale() {
		return circuitoCommerciale;
	}
	public void setCircuitoCommerciale(Boolean circuitoCommerciale) {
		this.circuitoCommerciale = circuitoCommerciale;
	}
	
	@Column(name = "note_ritrovamento")
	@Type(type="text")
	public String getNoteRitrovamento() {
		return noteRitrovamento;
	}
	public void setNoteRitrovamento(String noteRitrovamento) {
		this.noteRitrovamento = noteRitrovamento;
	}
	
	@Column(name="data_decesso")
	public Date getDataDecesso() {
		return dataDecesso;
	}
	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}
	
	@Transient
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@Transient
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	/*@Column(name="cessione_aperta")
	public Boolean getCessioneAperta() {
		return cessioneAperta;
	}
	public void setCessioneAperta(Boolean cessioneAperta) {
		this.cessioneAperta = cessioneAperta;
	}*/
	
	@Column(name="is_data_decesso_presunta")
	public Boolean isDataDecessoPresunta() {
		return isDataDecessoPresunta;
	}
	public void setDataDecessoPresunta(Boolean isDataDecessoPresunta) {
		this.isDataDecessoPresunta = isDataDecessoPresunta;
	}
	
	@Column(name = "trashed_date")
	public Date getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}
	
	@Column(name="is_data_nascita_presunta")
	public Boolean isDataNascitaPresunta() {
		return isDataNascitaPresunta;
	}
	public void setDataNascitaPresunta(Boolean isDataNascitaPresunta) {
		this.isDataNascitaPresunta = isDataNascitaPresunta;
	}
	
	@Column(name="data_sterilizzazione")
	public Date getDataSterilizzazione() {
		return dataSterilizzazione;
	}
	public void setDataSterilizzazione(Date dataSterilizzazione) {
		this.dataSterilizzazione = dataSterilizzazione;
	}
	
	@Column(name="soggetto_sterilizzante")
	public String getOperatoreSterilizzazione() {
		return operatoreSterilizzazione;
	}
	public void setOperatoreSterilizzazione(String operatoreSterilizzazione) {
		this.operatoreSterilizzazione = operatoreSterilizzazione;
	}
	
	@Column(name="mc2")
	public String getTatuaggio() {
		return tatuaggio;
	}
	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}
	
	@Column(name="data_registrazione")
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	@Column(name="data_chippatura")
	public Date getDataChippatura() {
		return dataChippatura;
	}
	public void setDataChippatura(Date dataChippatura) {
		this.dataChippatura = dataChippatura;
	}
	
	@Column(name="id_proprietario")
	public Integer getIdProprietario() {
		return idProprietario;
	}
	public void setIdProprietario(Integer idProprietario) {
		this.idProprietario = idProprietario;
	}
	
	@Column(name="id_detentore")
	public Integer getIdDetentore() {
		return idDetentore;
	}
	public void setIdDetentore(Integer idDetentore) {
		this.idDetentore = idDetentore;
	}
	
	
	
}
