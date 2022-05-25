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

public class VisitaPMSemplificata extends GenericBean {
	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";
	
	
	
	@Column(columnName = "data_visita_pm", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataVisitaPm = null;
	@Column(columnName = "id_esito_pm", columnType = INT, table = nome_tabella)
	int idEsitoPm = -1;
	@Column(columnName = "progressivo_macellazione_pm", columnType = STRING, table = nome_tabella)
	private String progressivoMacellazionePm = "";
	@Column(columnName = "id_tipo_macellazione_pm", columnType = INT, table = nome_tabella)
	private int idTipoMacellazionePm = -1;
	@Column(columnName = "id_veterinario1_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario1Pm = -1;
	@Column(columnName = "id_veterinario2_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario2Pm = -1;
	@Column(columnName = "id_veterinario3_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario3Pm = -1;
	
	
	
	


	public Timestamp getDataVisitaPm() {
		return dataVisitaPm;
	}

	public void setDataVisitaPm(Timestamp dataVisitaPm) {
		this.dataVisitaPm = dataVisitaPm;
	}

	public int getIdEsitoPm() {
		return idEsitoPm;
	}

	public void setIdEsitoPm(int idEsitoPm) {
		this.idEsitoPm = idEsitoPm;
	}

	public void setIdEsitoPm(String idEsito) {
		this.idEsitoPm = Integer.valueOf(idEsito);
	}
	
	public void setDataVisitaPm(String dataVisita) {
		this.dataVisitaPm =   DateUtils.parseDateStringNew(dataVisita, "dd/MM/yyyy");
	}

	public String getProgressivoMacellazionePm() {
		return progressivoMacellazionePm;
	}

	public void setProgressivoMacellazionePm(String progressivoMacellazionePm) {
		this.progressivoMacellazionePm = progressivoMacellazionePm;
	}

	public int getIdTipoMacellazionePm() {
		return idTipoMacellazionePm;
	}

	public void setIdTipoMacellazionePm(int idTipoMacellazionePm) {
		this.idTipoMacellazionePm = idTipoMacellazionePm;
	}
	
	public void setIdTipoMacellazionePm(String idTipoMacellazionePm) {
		this.idTipoMacellazionePm = Integer.parseInt(idTipoMacellazionePm);
	}

	public int getIdVeterinario1Pm() {
		return idVeterinario1Pm;
	}

	public void setIdVeterinario1Pm(int idVeterinario1Pm) {
		this.idVeterinario1Pm = idVeterinario1Pm;
	}
	
	public void setIdVeterinario1Pm(String idVeterinario1Pm) {
		if (idVeterinario1Pm != null && !("").equals(idVeterinario1Pm))
			this.idVeterinario1Pm = Integer.parseInt(idVeterinario1Pm);
	}

	public int getIdVeterinario2Pm() {
		return idVeterinario2Pm;
	}

	public void setIdVeterinario2Pm(int idVeterinario2Pm) {
		this.idVeterinario2Pm = idVeterinario2Pm;
	}
	
	public void setIdVeterinario2Pm(String idVeterinario2Pm) {
		if (idVeterinario2Pm != null && !("").equals(idVeterinario2Pm))
			this.idVeterinario2Pm = Integer.parseInt(idVeterinario2Pm);
	}

	public int getIdVeterinario3Pm() {
		return idVeterinario3Pm;
	}

	public void setIdVeterinario3Pm(int idVeterinario3Pm) {
		this.idVeterinario3Pm = idVeterinario3Pm;
	}
	
	public void setIdVeterinario3Pm(String idVeterinario3Pm) {
		if (idVeterinario3Pm != null && !("").equals(idVeterinario3Pm))
			this.idVeterinario3Pm = Integer.parseInt(idVeterinario3Pm);
	}
	
	
	

}
