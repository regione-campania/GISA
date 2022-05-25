/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.bean.remoteBean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
//@Table(name = "sync_registrazioni_canina", schema = "public")
//@NamedNativeQuery(name = "GetRegistrazioniCanina", query = "select * from sync_registrazioni_canina where identificativo = :identificativo", resultClass = Registrazioni.class)

public class Registrazioni implements Serializable
{
	private static final long serialVersionUID = -7289087085008520718L;
	
	private int id;
	private int idTipoReg;
	private Boolean cambioDetentore;
	private Integer origineRegistrazione;
	
	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="id_tipo_reg")
	public int getIdTipoReg() {
		return this.idTipoReg;
	}

	public void setIdTipoReg(int idTipoReg) {
		this.idTipoReg = idTipoReg;
	}
	
	@Column(name="cambio_detentore")
	public Boolean getCambioDetentore() {
		return cambioDetentore;
	}
	public void setCambioDetentore(Boolean cambioDetentore) {
		this.cambioDetentore = cambioDetentore;
	}
	
	@Column(name="origine_registrazione")
	public Integer getOrigineRegistrazione() {
		return this.origineRegistrazione;
	}

	public void setOrigineRegistrazione(Integer origineRegistrazione) {
		this.origineRegistrazione = origineRegistrazione;
	}
	
}
