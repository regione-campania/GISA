/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.sinantropi;

import it.us.web.bean.BUtente;
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
import org.hibernate.annotations.Where;


@Entity
@Table(name = "sinantropo_reimmissioni", schema = "public")
public class Reimmissioni implements java.io.Serializable {

	private int id;	
	private Date dataReimmissione;
	private LookupComuni comuneReimmissione;
	private String luogoReimmissione;
	private String codiceIspra;
	
	private Date entered;
	private Date modified;
	private Date trashedDate;
	private BUtente enteredBy;
	private BUtente modifiedBy;
		
	private Catture catture;
	
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
	@Column(name = "data_reimmissione", length = 29)
	public Date getDataReimmissione() {
		return dataReimmissione;
	}

	public void setDataReimmissione(Date dataReimmissione) {
		this.dataReimmissione = dataReimmissione;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comune_reimmissione")
	public LookupComuni getComuneReimmissione() {
		return comuneReimmissione;
	}

	public void setComuneReimmissione(LookupComuni comuneReimmissione) {
		this.comuneReimmissione = comuneReimmissione;
	}

	@Column(name ="luogo_reimmissione")
	public String getLuogoReimmissione() {
		return luogoReimmissione;
	}

	public void setLuogoReimmissione(String luogoReimmissione) {
		this.luogoReimmissione = luogoReimmissione;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catture")
	public Catture getCatture() {
		return catture;
	}

	public void setCatture(Catture catture) {
		this.catture = catture;
	}
	
	@Column(name ="codice_ispra")
	public String getCodiceIspra() {
		return codiceIspra;
	}

	public void setCodiceIspra(String codiceIspra) {
		this.codiceIspra = codiceIspra;
	}
	
	

}

