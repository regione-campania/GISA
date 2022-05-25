/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "immagini", schema = "public")
@Where( clause = "trashed_date is null" )
public class Immagine implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idRefClass;
	private String refClass;
	private Accettazione accettazione;
	
	private String originalName;
	private String displayName;
	private String pathName;
	private String description;
	private String contentType;
	private long size;
	
	private BUtente enteredBy;
	private BUtente modifiedBy;
	private Date entered;
	private Date modified;
	private Date trashedDate;
	
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
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "id_ref_class")
	@NotNull
	public int getIdRefClass() {
		return idRefClass;
	}
	public void setIdRefClass(int idRefClass) {
		this.idRefClass = idRefClass;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accettazione")
	@NotNull
	public Accettazione getAccettazione() {
		return this.accettazione;
	}

	public void setAccettazione(Accettazione accettazione) {
		this.accettazione = accettazione;
	}
	
	@Column(name = "ref_class", length = 256)
	public String getRefClass() {
		return this.refClass;
	}

	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}
	
	@Column(name = "original_name", length = 256)
	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	@Column(name = "displayName", length = 256)
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Column(name = "path_name", length = 256)
	public String getPathName() {
		return this.pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 13)
	@NotNull
	public Date getEntered() {
		return entered;
	}
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", length = 13)
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
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
	@NotNull	
	public BUtente getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(BUtente modifiedBy) {
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
	
	@Column(name = "description")	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column( name = "size")
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
	
	@Column(name = "content_type")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
