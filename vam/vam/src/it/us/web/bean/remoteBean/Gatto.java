/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;


import it.us.web.bean.remoteBean.Colonia;
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
@Table(name = "sync_gatto", schema = "public")
@Where( clause = "trashed_date is null" )
public class Gatto implements Serializable
{
	private static final long serialVersionUID = -8622138631789926823L;
	
	private int id;
	private String mc;
	private Integer razza;
	private String descrizioneRazza;
	private String descrizioneMantello;
	private String descrizioneTaglia;
	private String sesso;
	private Integer mantello;
	private Date dataNascita;
	private Integer idTaglia;
	private String noteRitrovamento;
	private Date dataDecesso;
	private String decessoValue;
	private Integer idTipoDecesso;
	private String errorDescription;
	private Integer errorCode;
	private Boolean isDataDecessoPresunta;
	private Boolean isDataNascitaPresunta;
	private Date trashedDate;
	private Boolean reimmesso;
	private String statoAttuale;
	private Boolean sterilizzato;
	private Date dataSterilizzazione;
	private Date dataRegistrazione;
	private String operatoreSterilizzazione;
	private String tatuaggio;
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
	
	@Column(name = "mc")
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	
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
	
	@Column(name = "descrizione_taglia")
	public String getDescrizioneTaglia() {
		return descrizioneTaglia;
	}
	public void setDescrizioneTaglia(String descrizioneTaglia) {
		this.descrizioneTaglia = descrizioneTaglia;
	}
	
	@Column(name = "sesso")
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	@Column(name = "mantello")
	public Integer getMantello() {
		return mantello;
	}
	public void setMantello(Integer mantello) {
		this.mantello = mantello;
	}
	
	@Column(name = "data_nascita")
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	@Column(name = "id_taglia")
	public Integer getIdTaglia() {
		return idTaglia;
	}
	public void setIdTaglia(Integer idTaglia) {
		this.idTaglia = idTaglia;
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
	
	@Column(name="data_sterilizzazione")
	public Date getDataSterilizzazione() {
		return dataSterilizzazione;
	}
	public void setDataSterilizzazione(Date dataSterilizzazione) {
		this.dataSterilizzazione = dataSterilizzazione;
	}
	
	@Column(name="data_registrazione")
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	@Column(name="decesso_value")
	public String getDecessoValue() {
		return decessoValue;
	}
	public void setDecessoValue(String decessoValue) {
		this.decessoValue = decessoValue;
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
	
	@Column(name="is_data_decesso_presunta")
	public Boolean getIsDataDecessoPresunta() {
		return isDataDecessoPresunta;
	}
	public void setIsDataDecessoPresunta(Boolean isDataDecessoPresunta) {
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
	public Boolean getIsDataNascitaPresunta() {
		return isDataNascitaPresunta;
	}
	public void setIsDataNascitaPresunta(Boolean isDataNascitaPresunta) {
		this.isDataNascitaPresunta = isDataNascitaPresunta;
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
	
	@Column(name="soggetto_sterilizzante")
	public String getOperatoreSterilizzazione() {
		return operatoreSterilizzazione;
	}
	public void setOperatoreSterilizzazione(String operatoreSterilizzazione) {
		this.operatoreSterilizzazione = operatoreSterilizzazione;
	}
	
	@Column(name = "tatuaggio")
	public String getTatuaggio() {
		return tatuaggio;
	}
	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}
	
	@Column(name="id_proprietario")
	public Integer getIdProprietario() {
		return idProprietario;
	}
	public void setIdProprietario(Integer idProprietario) {
		this.idProprietario = idProprietario;
	}
	
	@Column(name="data_chippatura")
	public Date getDataChippatura() {
		return dataChippatura;
	}
	public void setDataChippatura(Date dataChippatura) {
		this.dataChippatura = dataChippatura;
	}
	
	@Column(name="id_detentore")
	public Integer getIdDetentore() {
		return idDetentore;
	}
	public void setIdDetentore(Integer idDetentore) {
		this.idDetentore = idDetentore;
	}
	
}
