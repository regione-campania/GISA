/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import it.us.web.bean.BUtente;
import it.us.web.bean.vam.lookup.LookupLeishmaniosiEsiti;

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
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

public class PrelievoCampioniLeishmania implements java.io.Serializable
{
	private static final long serialVersionUID = -8831477707572244920L;
	
	private int id;
	
	private Date dataPrelievoLeishmaniosi;
	
	private String veterinario;
	
	@Override
	public String toString()
	{
		return id+"";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDataPrelievoLeishmaniosi() {
		return dataPrelievoLeishmaniosi;
	}
	public void setDataPrelievoLeishmaniosi(Date dataPrelievoLeishmaniosi) {
		this.dataPrelievoLeishmaniosi = dataPrelievoLeishmaniosi;
	}
	
	public String getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(String veterinario) {
		this.veterinario = veterinario;
	}
	
	
	
	
	
}
