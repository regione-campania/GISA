/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.EcoCuoreEsito;
import it.us.web.bean.vam.lookup.LookupEcoCuoreDiagnosi;
import it.us.web.bean.vam.lookup.LookupTipologiaAltroInterventoChirurgico;
import it.us.web.util.bean.NotNullDependencies;
import it.us.web.util.bean.MinLengthDependencies;

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
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tipo_intervento", schema = "public")
@Where( clause = "trashed_date is null" )

public class TipoIntervento implements java.io.Serializable, EsameInterface
{
	private static final long serialVersionUID = 5528525804289839766L;
	
	private int id;
	private CartellaClinica cartellaClinica;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;	
	
	private Set<SuperUtente> operatori = new HashSet<SuperUtente>(0);	
	private Set<LookupTipologiaAltroInterventoChirurgico> tipologie = new HashSet<LookupTipologiaAltroInterventoChirurgico>(0);	
	
	private String note;
	private Date dataEsito;
	private Date dataRichiesta;
	
	public TipoIntervento()
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
	@JoinColumn(name = "id_cartella_clinica")
	public CartellaClinica getCartellaClinica() {
		return this.cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_richiesta", length = 13)
	@NotNull(message = "Inserire un valore")
	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date data) {
		this.dataRichiesta = data;
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
	public BUtente getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public BUtente getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	@Transient
	public String getNomeEsame()
	{
		return "Tipo Intervento";
	}

	@Override
	@Transient
	public String getHtml()
	{
		return "implementare Tipo Intervento";
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_esito", length = 13)
	public Date getDataEsito() {
		// TODO Auto-generated method stub
		return dataEsito;
	}

	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tipo_intervento_operatori", schema = "public", joinColumns = { @JoinColumn(name = "tipo_intervento", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operatore", nullable = false, updatable = false) })
	public Set<SuperUtente> getOperatori() {
		return operatori;
	}
	public void setOperatori(Set<SuperUtente> operatori) {
		this.operatori = operatori;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tipo_intervento_tipologie", schema = "public", joinColumns = { @JoinColumn(name = "tipo_intervento", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "tipologia", nullable = false, updatable = false) })
	public Set<LookupTipologiaAltroInterventoChirurgico> getTipologie() {
		return tipologie;
	}
	public void setTipologie(Set<LookupTipologiaAltroInterventoChirurgico> tipologie) {
		this.tipologie = tipologie;
	}
	
	
	@Transient
	public Set<String> getOperatoriId() 
	{
		Set<String> operatoriId     = new HashSet<String>(0);
		for(SuperUtente temp:this.operatori)
		{
			operatoriId.add(temp.getId()+"");
		}
		return operatoriId;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
}
