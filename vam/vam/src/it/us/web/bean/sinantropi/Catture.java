/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.sinantropi;

import it.us.web.bean.BUtente;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.lookup.LookupComuni;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "sinantropo_catture", schema = "public")
public class Catture implements java.io.Serializable {

	private int id;	
	private Date dataCattura;
	private LookupComuni comuneCattura;
	private String luogoCattura;
	private String noteCattura;
		
	private Sinantropo sinantropo;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
	
	private Set<Detenzioni> detenzionis = new HashSet<Detenzioni>(0);
	private Reimmissioni reimmissioni;
	
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
	@Column(name = "data_cattura", length = 29)
	public Date getDataCattura() {
		return dataCattura;
	}

	public void setDataCattura(Date dataCattura) {
		this.dataCattura = dataCattura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune_cattura")
	public LookupComuni getComuneCattura() {
		return comuneCattura;
	}

	public void setComuneCattura(LookupComuni comuneCattura) {
		this.comuneCattura = comuneCattura;
	}

	@Column(name ="luogo_cattura")
	public String getLuogoCattura() {
		return luogoCattura;
	}

	public void setLuogoCattura(String luogoCattura) {
		this.luogoCattura = luogoCattura;
	}
		
	@Column(name ="note_cattura")
	@Type(type = "text")
	public String getNoteCattura() {
		return noteCattura;
	}

	public void setNoteCattura(String noteCattura) {
		this.noteCattura = noteCattura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sinantropo")
	public Sinantropo getSinantropo() {
		return sinantropo;
	}

	public void setSinantropo(Sinantropo sinantropo) {
		this.sinantropo = sinantropo;
	}

	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catture")	
	public Set<Detenzioni> getDetenzionis() {
		return detenzionis;
	}

	public void setDetenzionis(Set<Detenzioni> detenzionis) {
		this.detenzionis = detenzionis;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reimmissioni")
	public Reimmissioni getReimmissioni() {
		return reimmissioni;
	}

	public void setReimmissioni(Reimmissioni reimmissioni) {
		this.reimmissioni = reimmissioni;
	}
	

}
