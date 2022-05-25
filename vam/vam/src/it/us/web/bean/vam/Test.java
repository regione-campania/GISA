/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
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
@Table(name = "test", schema = "public")
@Where( clause = "trashed_date is null" )
public class Test implements java.io.Serializable {
	
private static final long serialVersionUID = -1921248398217301209L;

private int id;


private Date start;
private Date end;
private String title;
private String body;
	
private Date entered;
private Date modified;
private Date trashedDate;
private BUtente enteredBy;
private BUtente modifiedBy;
private Clinica clinica;




public Test()
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



@Temporal(TemporalType.TIMESTAMP)
@Column(name = "start", length = 29)
public Date getStart() {
	return start;
}

public void setStart(Date start) {
	this.start = start;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "end", length = 29)
public Date getEnd() {
	return end;
}

public void setEnd(Date end) {
	this.end = end;
}

@Column(name = "title", length = 64)
@Length(max = 64)
public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

@Column(name = "body")
@Type(type = "text")
public String getBody() {
	return body;
}

public void setBody(String body) {
	this.body = body;
}

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "clinica")
public Clinica getClinica() {
	return this.clinica;
}

public void setClinica(Clinica clinica) {
	this.clinica = clinica;
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





}