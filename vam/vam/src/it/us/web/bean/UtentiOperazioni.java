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
@Table(name = "utenti_operazioni", schema = "public")
@Where( clause = "trashed_date is null" )
public class UtentiOperazioni implements java.io.Serializable
{
	private static final long serialVersionUID = -3763688846976643462L;
	
	private int id;
	private Date entered;
	private BUtenteAll enteredBy;
	private Date modified;
	private BUtenteAll modifiedBy;
	private Date trashedDate;
	private BUtenteAll utente;
	private String operazione;
	private String descrizioneOperazione;
	private String ip;
	private String username;
	private CartellaClinica cc;
	private Set<UtentiOperazioniModifiche> modifiche = new HashSet<UtentiOperazioniModifiche>(0);
	
	/**
	 * Gestione geolocalizzazione
	 */
	private Double accessPositionLat;
	private Double accessPositionLon;
	private String accessPositionErr;
	
	public UtentiOperazioni()
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	public BUtenteAll getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(BUtenteAll enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "operazione")
	public Set<UtentiOperazioniModifiche> getModifiche() {
		return this.modifiche;
	}

	public void setModifiche(Set<UtentiOperazioniModifiche> modifiche) {
		this.modifiche = modifiche;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtenteAll getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtenteAll modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente", nullable=true)
	public BUtenteAll getUtente() {
		return this.utente;
	}

	public void setUtente(BUtenteAll utente) {
		this.utente = utente;
	}
	
	@Type(type="text")
	@Column(name = "operazione")
	public String getOperazione() {
		return this.operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	
	@Type(type="text")
	@Column(name = "descrizione_operazione")
	public String getDescrizioneOperazione() {
		return this.descrizioneOperazione;
	}

	public void setDescrizioneOperazione(String descrizioneOperazione) {
		this.descrizioneOperazione = descrizioneOperazione;
	}
	
	@Type(type="text")
	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
	@Type(type="double")
	@Column(name = "access_position_lat")
	public Double getAccessPositionLat() {
		return accessPositionLat;
	}

	public void setAccessPositionLat(Double accessPositionLat) {
		this.accessPositionLat = accessPositionLat;
	}
	
	  public void setAccessPositionLat(String lat)
		{
			if (!lat.equals(""))
			{
				accessPositionLat = Double.parseDouble(lat);
			}
		}

	@Type(type="double")
	@Column(name = "access_position_lon")
	public Double getAccessPositionLon() {
		return accessPositionLon;
	}

	
	public void setAccessPositionLon(Double accessPositionLon) {
		this.accessPositionLon = accessPositionLon;
	}
	



		public void setAccessPositionLon(String lon)
		{
			if (!lon.equals(""))
			{
				accessPositionLon = Double.parseDouble(lon);
			}
		}

	@Type(type="text")
	@Column(name = "access_position_err")
	public String getAccessPositionErr() {
		return accessPositionErr;
	}

	public void setAccessPositionErr(String accessPositionErr) {
		this.accessPositionErr = accessPositionErr;
	}
	
	@Type(type="text")
	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cc")
	public CartellaClinica getCc() {
		return this.cc;
	}

	public void setCc(CartellaClinica cc) {
		this.cc = cc;
	}
	
}
