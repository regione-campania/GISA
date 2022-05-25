/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupModalitaSomministrazioneFarmaci;
import it.us.web.bean.vam.lookup.LookupVieSomministrazioneFarmaci;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "terapia_assegnata", schema = "public")
@Where( clause = "trashed_date is null" )
public class TerapiaAssegnata implements java.io.Serializable {

	private static final long serialVersionUID = 5661336423622596083L;
	
	private int id;		
	private TerapiaDegenza terapiaDegenza;
	private Date data;	
	
	private Date entered;
	private BUtente enteredBy;
	private Date modified;
	private BUtente modifiedBy;
	private Date trashedDate;
	private BUtente stoppedBy;
	private Date stoppedDate;
	
	private Date dataUltimaEffettuazione;
	
	private float quantita;
	private String unitaMisura;
	private int tempi;
	private String note;
	
	private boolean praticable = true;
	private boolean modifiable = true;
	private boolean stopped    = false;
	private boolean erasable   = true;
	
	
	private LookupModalitaSomministrazioneFarmaci lmsf;	
	private LookupVieSomministrazioneFarmaci lvsf;
	
	
	private Set<TerapiaEffettuata> tarapiaEffettuates = new HashSet<TerapiaEffettuata>(0);
	
	private MagazzinoFarmaci magazzinoFarmaci;		
	
	public TerapiaAssegnata() {
		
	}
	
	@Transient
	public String getNomeEsame()
	{
		return "Terapia Assegnata";
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
	@JoinColumn(name = "terapia_degenza")
	public TerapiaDegenza getTerapiaDegenza() {
		return terapiaDegenza;
	}

	public void setTerapiaDegenza(TerapiaDegenza terapiaDegenza) {
		this.terapiaDegenza = terapiaDegenza;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", length = 13)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered")
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "trashed_date")
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
		return this.enteredBy;
	}

	public void setEnteredBy(BUtente enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	@NotNull
	public BUtente getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(BUtente modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_ultima_effettuazione")
	public Date getDataUltimaEffettuazione() {
		return dataUltimaEffettuazione;
	}

	public void setDataUltimaEffettuazione(Date dataUltimaEffettuazione) {
		this.dataUltimaEffettuazione = dataUltimaEffettuazione;
	}
		
	@Column(name = "effettuabile")
	public boolean isPraticable() {
		return praticable;
	}

	public void setPraticable(boolean praticable) {
		this.praticable = praticable;
	}

	@Column(name = "modificabile")
	public boolean isModifiable() {
		return modifiable;
	}

	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	@Column(name = "stoppata")
	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	@Column(name = "cancellabile")
	public boolean isErasable() {
		return erasable;
	}

	public void setErasable(boolean erasable) {
		this.erasable = erasable;
	}

	@Column(name = "quantita")
	public float getQuantita() {
		return quantita;
	}

	public void setQuantita(float quantita) {
		this.quantita = quantita;
	}

	@Column(name = "unita_misura")
	public String getUnitaMisura() {
		return unitaMisura;
	}

	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	@Column(name = "tempi")
	public int getTempi() {
		return tempi;
	}

	public void setTempi(int tempi) {
		this.tempi = tempi;
	}

	@Column(name = "note")
	@Type(type = "text")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modalita_somministrazione")
	public LookupModalitaSomministrazioneFarmaci getLmsf() {
		return lmsf;
	}

	public void setLmsf(LookupModalitaSomministrazioneFarmaci lmsf) {
		this.lmsf = lmsf;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "via_somministrazione")
	public LookupVieSomministrazioneFarmaci getLvsf() {
		return lvsf;
	}

	public void setLvsf(LookupVieSomministrazioneFarmaci lvsf) {
		this.lvsf = lvsf;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmaco")
	public MagazzinoFarmaci getMagazzinoFarmaci() {
		return magazzinoFarmaci;
	}

	public void setMagazzinoFarmaci(MagazzinoFarmaci magazzinoFarmaci) {
		this.magazzinoFarmaci = magazzinoFarmaci;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terapiaAssegnata")
	@Where( clause = "trashed_date is null" )
	public Set<TerapiaEffettuata> getTarapiaEffettuates() {
		return tarapiaEffettuates;
	}

	public void setTarapiaEffettuates(Set<TerapiaEffettuata> tarapiaEffettuates) {
		this.tarapiaEffettuates = tarapiaEffettuates;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stopped_by")
	public BUtente getStoppedBy() {
		return this.stoppedBy;
	}

	public void setStoppedBy(BUtente stoppedBy) {
		this.stoppedBy = stoppedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stopped_date")
	public Date getStoppedDate() {
		return this.stoppedDate;
	}

	public void setStoppedDate(Date stoppedDate) {
		this.stoppedDate = stoppedDate;
	}
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
	
	
	
	
}
