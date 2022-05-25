/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.lookup.LookupTickets;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ticket", schema = "public")
public class Ticket implements java.io.Serializable {

	private static final long serialVersionUID = 124L;
	
	private int id;
	private boolean aperto;
	private String description;
	private String closureDescription;
	private String email;
	private Date entered;
	private BUtenteAll enteredBy;	
	private Date closed;	
	private BUtenteAll closedBy;		
	private LookupTickets lookupTickets;
	
	
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
	
	@Column(name = "aperto")
	public boolean isAperto() {
		return aperto;
	}
	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}
	
	@Column(name = "description")
	@Type(type = "text")
	public String getDescription() {
		return description;
	}	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "closure_description")
	@Type(type = "text")
	public String getClosureDescription() {
		return closureDescription;
	}
	public void setClosureDescription(String closureDescription) {
		this.closureDescription = closureDescription;
	}
	
	@Column(name = "email")	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered", length = 13)
	public Date getEntered() {
		return entered;
	}	
	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entered_by")
	public BUtenteAll getEnteredBy() {
		return enteredBy;
	}		
	public void setEnteredBy(BUtenteAll enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closed", length = 13)
	public Date getClosed() {
		return closed;
	}
	public void setClosed(Date closed) {
		this.closed = closed;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "closed_by")
	public BUtenteAll getClosedBy() {
		return closedBy;
	}
	public void setClosedBy(BUtenteAll closedBy) {
		this.closedBy = closedBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipologia_ticket")
	public LookupTickets getLookupTickets() {
		return lookupTickets;
	}
	public void setLookupTickets(LookupTickets lookupTickets) {
		this.lookupTickets = lookupTickets;
	}
	
	@Transient
	public String getStato()
	{
		if(this.aperto)
			return "Aperta";
		else
			return "Chiusa";
	}
	
	@Override
	public String toString()
	{
		return description+"";
	}
	
	

}


