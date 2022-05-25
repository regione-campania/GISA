/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
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
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "esame_obiettivo_esito", schema = "public")
@Where( clause = "trashed_date is null" )
public class EsameObiettivoEsito implements java.io.Serializable
{
	private static final long serialVersionUID = -2271003577908569371L;
	
	private int id;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello3;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello4;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello5;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello6;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello7;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello8;
	private EsameObiettivo esameObiettivo;
//	private LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello2;
	private LookupEsameObiettivoEsito lookupEsameObiettivoEsito;
	private String note;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtenteAll enteredBy;
	private BUtenteAll modifiedBy;
	private Date dataEsameObiettivo;

	public EsameObiettivoEsito()
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

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_3")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello3() {
//		return this.lookupEsameObiettivoEsitoByLivello3;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello3(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello3) {
//		this.lookupEsameObiettivoEsitoByLivello3 = lookupEsameObiettivoEsitoByLivello3;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_4")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello4() {
//		return this.lookupEsameObiettivoEsitoByLivello4;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello4(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello4) {
//		this.lookupEsameObiettivoEsitoByLivello4 = lookupEsameObiettivoEsitoByLivello4;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_5")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello5() {
//		return this.lookupEsameObiettivoEsitoByLivello5;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello5(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello5) {
//		this.lookupEsameObiettivoEsitoByLivello5 = lookupEsameObiettivoEsitoByLivello5;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_6")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello6() {
//		return this.lookupEsameObiettivoEsitoByLivello6;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello6(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello6) {
//		this.lookupEsameObiettivoEsitoByLivello6 = lookupEsameObiettivoEsitoByLivello6;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_7")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello7() {
//		return this.lookupEsameObiettivoEsitoByLivello7;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello7(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello7) {
//		this.lookupEsameObiettivoEsitoByLivello7 = lookupEsameObiettivoEsitoByLivello7;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_8")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello8() {
//		return this.lookupEsameObiettivoEsitoByLivello8;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello8(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello8) {
//		this.lookupEsameObiettivoEsitoByLivello8 = lookupEsameObiettivoEsitoByLivello8;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esame_obiettivo")
	public EsameObiettivo getEsameObiettivo() {
		return this.esameObiettivo;
	}

	public void setEsameObiettivo(EsameObiettivo esameObiettivo) {
		this.esameObiettivo = esameObiettivo;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "livello_2")
//	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsitoByLivello2() {
//		return this.lookupEsameObiettivoEsitoByLivello2;
//	}
//
//	public void setLookupEsameObiettivoEsitoByLivello2(
//			LookupEsameObiettivoEsito lookupEsameObiettivoEsitoByLivello2) {
//		this.lookupEsameObiettivoEsitoByLivello2 = lookupEsameObiettivoEsitoByLivello2;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "esito")
	public LookupEsameObiettivoEsito getLookupEsameObiettivoEsito() {
		return this.lookupEsameObiettivoEsito;
	}

	public void setLookupEsameObiettivoEsito(
			LookupEsameObiettivoEsito lookupEsameObiettivoEsito) {
		this.lookupEsameObiettivoEsito = lookupEsameObiettivoEsito;
	}

	@Column(name = "note", length = 64)
	@Length(max = 64)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	@NotNull

	public BUtenteAll getEnteredBy() {
		return this.enteredBy;
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
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_esame", length = 13)
	public Date getDataEsameObiettivo() {
		return this.dataEsameObiettivo;
	}

	public void setDataEsameObiettivo(Date dataEsameObiettivo) {
		this.dataEsameObiettivo = dataEsameObiettivo;
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Esame obiettivo esito";
	}
}
