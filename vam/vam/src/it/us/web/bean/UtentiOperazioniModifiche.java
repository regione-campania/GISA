/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean;

import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.OperazioneChirurgica;
import it.us.web.bean.vam.Sterilizzazione;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.permessi.Permessi;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "utenti_operazioni_modifiche", schema = "public")
public class UtentiOperazioniModifiche implements java.io.Serializable
{
	private static final long serialVersionUID = -3763688846976643462L;
	
	private int id;
	private String modifiche;
	private String bean;
	private Integer operazione;
	private Boolean nuovagestione; 
	private Integer userid;
	private Date entered;
	private Integer idcc ;
	private String descrizioneoperazione;
	private String urloperazione;

	
	public UtentiOperazioniModifiche()
	{
		
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Type(type="text")
	@Column(name = "modifiche")
	public String getModifiche() {
		return this.modifiche;
	}

	public void setModifiche(String modifiche) {
		this.modifiche = modifiche;
	}
	
	@Type(type="text")
	@Column(name = "bean")
	public String getBean() {
		return this.bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}
	
	@Column(name = "operazione")
	public Integer getOperazione() {
		return this.operazione;
	}

	public void setOperazione(Integer operazione) {
		this.operazione = operazione;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Column(name = "nuovagestione")
	public Boolean getNuovagestione() {
		return nuovagestione;
	}

	public void setNuovagestione(Boolean nuovagestione) {
		this.nuovagestione = nuovagestione;
	}
	
	@Column(name = "userid")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "idcc")
	public Integer getIdcc() {
		return idcc;
	}

	public void setIdcc(Integer idcc) {
		this.idcc = idcc;
	}

	@Type(type="text")
	@Column(name = "descrizioneoperazione")
	public String getDescrizioneoperazione() {
		return descrizioneoperazione;
	}

	public void setDescrizioneoperazione(String descrizioneoperazione) {
		this.descrizioneoperazione = descrizioneoperazione;
	}

	@Type(type="text")
	@Column(name = "urloperazione")
	public String getUrloperazione() {
		return urloperazione;
	}

	public void setUrloperazione(String urloperazione) {
		this.urloperazione = urloperazione;
	}
	
}
