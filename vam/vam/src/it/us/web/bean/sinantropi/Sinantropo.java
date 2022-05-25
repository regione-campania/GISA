/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.sinantropi;

import it.us.web.bean.BUtente;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupTaglie;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "sinantropo", schema = "public")
@Where( clause = "trashed_date is null" )
public class Sinantropo implements java.io.Serializable {

	private int id;	
	private boolean numeroAssegnato;
	private String numeroUfficiale;
	private String numeroAutomatico;
	private String codiceIspra;
	private String mc;
	private String statoAttuale;
	private String mantello;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private Set<Catture> cattureis = new HashSet<Catture>(0);
	
	private Date dataNascitaPresunta;	
	private Date dataDecesso;
	private boolean dataDecessoPresunta;
	private LookupCMI lookupCMI;
	private boolean maschio;
	private LookupSpecieSinantropi lookupSpecieSinantropi;
	private String razza;
	private String sesso;
	private String note;
	private LookupTaglie taglia;
	private LookupSinantropiEta eta;
	private boolean sinantropo;
	private boolean marini;
	private boolean zoo;
	
	//Questo attributo permettere di discriminare le registrazioni possibili
	private String lastOperation;
	
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
	public BUtente getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")	
	public BUtente getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nascita_presunta", length = 29)
	public Date getDataNascitaPresunta() {
		return dataNascitaPresunta;
	}

	public void setDataNascitaPresunta(Date dataNascitaPresunta) {
		this.dataNascitaPresunta = dataNascitaPresunta;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_decesso", length = 29)
	public Date getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}
	
	
	@Column(name = "data_decesso_presunta")
	public boolean isDataDecessoPresunta() {
		return dataDecessoPresunta;
	}

	public void setDataDecessoPresunta(boolean dataDecessoPresunta) {
		this.dataDecessoPresunta = dataDecessoPresunta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "causa_decesso")	
	public LookupCMI getLookupCMI() {
		return lookupCMI;
	}

	public void setLookupCMI(LookupCMI lookupCMI) {
		this.lookupCMI = lookupCMI;
	}

	@Column(name = "sinantropo")	
	public boolean isSinantropo() {
		return sinantropo;
	}

	public void setSinantropo(boolean sinantropo) {
		this.sinantropo = sinantropo;
	}
	
	@Column(name = "zoo")	
	public boolean isZoo() {
		return zoo;
	}

	public void setZoo(boolean zoo) {
		this.zoo = zoo;
	}
	
	@Column(name = "marini")	
	public boolean getMarini() {
		return marini;
	}

	public void setMarini(boolean marini) {
		this.marini = marini;
	}
	
	@Column(name = "maschio")	
	public boolean getMaschio() {
		return maschio;
	}

	public void setMaschio(boolean maschio) {
		this.maschio = maschio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "specie")
	@NotNull
	public LookupSpecieSinantropi getLookupSpecieSinantropi() {
		return lookupSpecieSinantropi;
	}

	public void setLookupSpecieSinantropi(
			LookupSpecieSinantropi lookupSpecieSinantropi) {
		this.lookupSpecieSinantropi = lookupSpecieSinantropi;
	}

	@Column(name = "note")
	@Type(type = "text")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "razza", length = 64)
	@Length(max = 64)	
	public String getRazza() {
		return razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}
	
	
	@Column(name = "sesso", length = 2)
	@Length(max = 2)
	@NotNull
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	@Column(name ="numero_automatico")
	public String getNumeroAutomatico() {
		return numeroAutomatico;
	}

	public void setNumeroAutomatico(String numeroAutomatico) {
		this.numeroAutomatico = numeroAutomatico;
	}
	
	@Column(name ="codice_ispra")
	public String getCodiceIspra() {
		return codiceIspra;
	}

	public void setCodiceIspra(String codiceIspra) {
		this.codiceIspra = codiceIspra;
	}
	
	@Column(name ="mc")
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	@Column(name = "stato_attuale")
	public String getStatoAttuale() {
		return statoAttuale;
	}

	public void setStatoAttuale(String statoAttuale) {
		if(this.dataDecesso!=null)
			this.statoAttuale = "Decesso";
		else
			this.statoAttuale = "Registrato";
	}

	@Column(name = "mantello")
	public String getMantello() {
		return mantello;
	}

	public void setMantello(String mantello) {
		this.mantello = mantello;
	}

	@Column(name = "numero_assegnato")
	@NotNull
	public boolean isNumeroAssegnato() {
		return numeroAssegnato;
	}

	public void setNumeroAssegnato(boolean numeroAssegnato) {
		this.numeroAssegnato = numeroAssegnato;
	}

	@Column(name ="numero_ufficiale")
	public String getNumeroUfficiale() {
		return numeroUfficiale;
	}

	public void setNumeroUfficiale(String numeroUfficiale) {
		this.numeroUfficiale = numeroUfficiale;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sinantropo")
	public Set<Catture> getCattureis() {
		return cattureis;
	}

	public void setCattureis(Set<Catture> cattureis) {
		this.cattureis = cattureis;
	}

	@Column(name ="last_operation")
	public String getLastOperation() {
		return lastOperation;
	}

	public void setLastOperation(String lastOperation) {
		this.lastOperation = lastOperation;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taglia")
	public LookupTaglie getTaglia() {
		return this.taglia;
	}

	public void setTaglia(LookupTaglie taglia) {
		this.taglia = taglia;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eta")
	public LookupSinantropiEta getEta() {
		return eta;
	}

	public void setEta(LookupSinantropiEta eta) {
		this.eta = eta;
	}
	
}
