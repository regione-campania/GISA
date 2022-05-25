/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoElminti;
import it.us.web.bean.vam.lookup.LookupEsameCoprologicoProtozoi;
import it.us.web.dao.UtenteDAO;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "esame_citologico", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameCitologico implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = -7105270997715736712L;

	private int id;
	private LookupEsameCitologicoTipoPrelievo tipoPrelievo = new LookupEsameCitologicoTipoPrelievo();
	private String tipoPrelievoAltro;
	private String aspettoLesione;
	private LookupEsameCitologicoDiagnosi diagnosi = new LookupEsameCitologicoDiagnosi();
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


	public EsameCitologico()
	{
		
	}

	@Override
	public String toString()
	{
		return id+"";
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
	@JoinColumn(name = "tipo_prelievo")
	@NotNull(message="Selezionare il tipo di prelievo")
	public LookupEsameCitologicoTipoPrelievo getTipoPrelievo() {
		return tipoPrelievo;
	}

	public void setTipoPrelievo(LookupEsameCitologicoTipoPrelievo tipoPrelievo) {
		this.tipoPrelievo = tipoPrelievo;
	}

	@Column(name = "tipo_prelievo_altro")
	@Type(type="text")
	public String getTipoPrelievoAltro() {
		return tipoPrelievoAltro;
	}

	public void setTipoPrelievoAltro(String tipoPrelievoAltro) {
		this.tipoPrelievoAltro = tipoPrelievoAltro;
	}

	@Column(name = "aspetto_lesione")
	@Type(type="text")
	public String getAspettoLesione() {
		return aspettoLesione;
	}

	public void setAspettoLesione(String aspettoLesione) {
		this.aspettoLesione = aspettoLesione;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diagnosi")
	@NotNull(message="Selezionare una diagnosi")
	public LookupEsameCitologicoDiagnosi getDiagnosi() {
		return diagnosi;
	}

	public void setDiagnosi(LookupEsameCitologicoDiagnosi diagnosi) {
		this.diagnosi = diagnosi;
	}

	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Citologico";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "<b>bla bla bla citologico</b>";
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
