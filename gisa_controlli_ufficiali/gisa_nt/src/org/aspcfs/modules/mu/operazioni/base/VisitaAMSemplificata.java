/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.operazioni.base;

import java.sql.Timestamp;
import java.sql.Types;

import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class VisitaAMSemplificata extends GenericBean {
	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";
	

	@Column(columnName = "data_visita_am", columnType = TIMESTAMP, table = nome_tabella)
	Timestamp dataVisitaAm = null;
	@Column(columnName = "id_esito_am", columnType = INT, table = nome_tabella)
	int idEsitoAm = -1;
	
	
	
	
	public Timestamp getDataVisitaAm() {
		return dataVisitaAm;
	}

	public void setDataVisitaAm(Timestamp dataVisitaAm) {
		this.dataVisitaAm = dataVisitaAm;
	}

	public int getIdEsitoAm() {
		return idEsitoAm;
	}

	public void setIdEsitoAm(int idEsitoAm) {
		this.idEsitoAm = idEsitoAm;
	}

	public void setDataVisitaAm(String dataVisita) {
		this.dataVisitaAm =   DateUtils.parseDateStringNew(dataVisita, "dd/MM/yyyy");
	}
	
	
	public void setIdEsitoAm(String idEsito) {
		this.idEsitoAm = Integer.valueOf(idEsito);
	}
	
	
	

}
