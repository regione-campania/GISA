/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupEsitoRabbia;
import it.us.web.dao.UtenteDAO;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "rabbia", schema = "public")
@Where( clause = "trashed_date is null" )
public class Rabbia implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = -5683301989406129454L;
	
	private int id;

	private CartellaClinica cartellaClinica;
	
	private Date dataRichiesta;
	private Date dataEsito;
	private Date entered;
	private Date modified;
	private Date trashedDate;
//	private BUtente enteredBy;
//	private BUtente modifiedBy;
	
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;
	
	boolean prelievoSangue = false;
	boolean prelievoEncefalo = false;
	private LookupEsitoRabbia esitoSangue;
	private LookupEsitoRabbia esitoEncefalo;


	public Rabbia()
	{
		
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id", unique = true, nullable = false)
	@NotNull
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_richiesta", length = 29)
	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esito", length = 29)
	public Date getDataEsito() {
		return dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 29)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 29)
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date", length = 29)
	public Date getTrashedDate() {
		return this.trashedDate;
	}

	public void setTrashedDate(Date trashedDate) {
		this.trashedDate = trashedDate;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "entered_by")
//	public BUtente getEnteredBy() {
//		return this.enteredBy;
//	}
//
//	public void setEnteredBy(BUtente enteredBy) {
//		this.enteredBy = enteredBy;
//	}
//
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "modified_by")
//	public BUtente getModifiedBy() {
//		return this.modifiedBy;
//	}
//
//	public void setModifiedBy(BUtente modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esito_sangue")
	public LookupEsitoRabbia getEsitoSangue() {
		return esitoSangue;
	}
	
	public void setEsitoSangue(LookupEsitoRabbia esitoSangue) {
		this.esitoSangue = esitoSangue;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esito_encefalo")
	public LookupEsitoRabbia getEsitoEncefalo() {
		return esitoEncefalo;
	}
	
	public void setEsitoEncefalo(LookupEsitoRabbia esitoEncefalo) {
		this.esitoEncefalo = esitoEncefalo;
	}
	
	public boolean getPrelievoSangue() {
		return prelievoSangue;
	}

	public void setPrelievoSangue(boolean prelievoSangue) {
		this.prelievoSangue = prelievoSangue;
	}

	public boolean getPrelievoEncefalo() {
		return prelievoEncefalo;
	}

	public void setPrelievoEncefalo(boolean prelievoEncefalo) {
		this.prelievoEncefalo = prelievoEncefalo;
	}
	
	@Transient
	public String getEsitoString()
	{
		String ret = "";
		
		if( prelievoSangue && esitoSangue != null )
		{
			ret = "Sangue: " + esitoSangue.getDescription();
		}
		
		if( prelievoEncefalo && esitoEncefalo != null )
		{
			ret += ( ( (ret.length() > 0) ? (",\n") : ("") ) + "Encefalo: " + esitoEncefalo.getDescription() );
		}
		
		return ret;
	}
	
	@Transient
	public String getTipoPrelievoString()
	{
		String ret = "";
		if( prelievoSangue )
		{
			ret = "Sangue";
		}
		
		if( prelievoEncefalo )
		{
			ret += ( ( (prelievoSangue) ? (", ") : ("") ) + "Encefalo" );
		}
		return ret;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Rabbia";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare rabbia";
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull

//	public BUtenteAll getEnteredBy() {
//		return this.enteredBy;
//	}
	
	public BUtente getEnteredBy() {
		return UtenteDAO.getUtente(enteredBy.getId());
	}

	public void setEnteredBy(BUtente enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = UtenteDAO.getUtenteAll(enteredBy.getId());
	}
	
	
	public void setEnteredBy(BUtenteAll enteredBy) {
		//this.enteredBy = enteredBy;
		this.enteredBy = enteredBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtenteAll getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	UtenteDAO.getUtenteAll(modifiedBy.getId());
	}
	
	
	public void setModifiedBy(BUtenteAll modifiedBy) {
	//	this.modifiedBy = modifiedBy;
	this.modifiedBy =	modifiedBy;
	}
}
