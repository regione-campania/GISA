/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.operazioni.base;

import java.sql.Timestamp;
import java.sql.Types;

import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class VisitaAM extends VisitaAMSemplificata {

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";

	@Column(columnName = "id_provvedimento_adottato_visita_am", columnType = INT, table = nome_tabella)
	private int idProvvedimentoAdottatoVisitaAm = -1;

	@Column(columnName = "comunicazione_asl_origine_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAslOrigineVisitaAm = false;

	@Column(columnName = "comunicazione_proprietario_animale_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioAnimaleVisitaAm = false;

	@Column(columnName = "comunicazione_azienda_origine_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAziendaOrigineVisitaAm = false;

	@Column(columnName = "comunicazione_proprietario_macello_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioMacelloVisitaAm = false;

	@Column(columnName = "comunicazione_pif_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazionePifVisitaAm = false;

	@Column(columnName = "comunicazione_uvac_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneUvacVisitaAm = false;

	@Column(columnName = "comunicazione_regione_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneRegioneVisitaAm = false;

	@Column(columnName = "comunicazione_altro_visita_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAltroVisitaAm = false;

	@Column(columnName = "comunicazione_altro_testo_visita_am", columnType = STRING, table = nome_tabella)
	private String comunicazioneAltroTestoVisitaAm = "";

	@Column(columnName = "note_visita_am", columnType = STRING, table = nome_tabella)
	private String noteVisitaAm = "";

	public int getIdProvvedimentoAdottatoVisitaAm() {
		return idProvvedimentoAdottatoVisitaAm;
	}

	public void setIdProvvedimentoAdottatoVisitaAm(int idProvvedimentoAdottatoVisitaAm) {
		this.idProvvedimentoAdottatoVisitaAm = idProvvedimentoAdottatoVisitaAm;
	}
	
	public void setIdProvvedimentoAdottatoVisitaAm(String idProvvedimentoAdottatoVisitaAm) {
		if (idProvvedimentoAdottatoVisitaAm != null && !("").equals(idProvvedimentoAdottatoVisitaAm))
			this.idProvvedimentoAdottatoVisitaAm = Integer.parseInt(idProvvedimentoAdottatoVisitaAm);
	}

	public boolean isComunicazioneAslOrigineVisitaAm() {
		return comunicazioneAslOrigineVisitaAm;
	}

	public void setComunicazioneAslOrigineVisitaAm(boolean comunicazioneAslOrigineVisitaAm) {
		this.comunicazioneAslOrigineVisitaAm = comunicazioneAslOrigineVisitaAm;
	}

	public void setComunicazioneAslOrigineVisitaAm(String flag) {
		this.comunicazioneAslOrigineVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneProprietarioAnimaleVisitaAm() {
		return comunicazioneProprietarioAnimaleVisitaAm;
	}

	public void setComunicazioneProprietarioAnimaleVisitaAm(boolean comunicazioneProprietarioAnimaleVisitaAm) {
		this.comunicazioneProprietarioAnimaleVisitaAm = comunicazioneProprietarioAnimaleVisitaAm;
	}

	public void setComunicazioneProprietarioAnimaleVisitaAm(String flag) {
		this.comunicazioneProprietarioAnimaleVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneAziendaOrigineVisitaAm() {
		return comunicazioneAziendaOrigineVisitaAm;
	}

	public void setComunicazioneAziendaOrigineVisitaAm(boolean comunicazioneAziendaOrigineVisitaAm) {
		this.comunicazioneAziendaOrigineVisitaAm = comunicazioneAziendaOrigineVisitaAm;
	}

	public void setComunicazioneAziendaOrigineVisitaAm(String flag) {
		this.comunicazioneAziendaOrigineVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneProprietarioMacelloVisitaAm() {
		return comunicazioneProprietarioMacelloVisitaAm;
	}

	public void setComunicazioneProprietarioMacelloVisitaAm(boolean comunicazioneProprietarioMacelloVisitaAm) {
		this.comunicazioneProprietarioMacelloVisitaAm = comunicazioneProprietarioMacelloVisitaAm;
	}

	public void setComunicazioneProprietarioMacelloVisitaAm(String flag) {
		this.comunicazioneProprietarioMacelloVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazionePifVisitaAm() {
		return comunicazionePifVisitaAm;
	}

	public void setComunicazionePifVisitaAm(boolean comunicazionePifVisitaAm) {
		this.comunicazionePifVisitaAm = comunicazionePifVisitaAm;
	}

	public void setComunicazionePifVisitaAm(String flag) {
		this.comunicazionePifVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneUvacVisitaAm() {
		return comunicazioneUvacVisitaAm;
	}

	public void setComunicazioneUvacVisitaAm(boolean comunicazioneUvacVisitaAm) {
		this.comunicazioneUvacVisitaAm = comunicazioneUvacVisitaAm;
	}

	public void setComunicazioneUvacVisitaAm(String flag) {
		this.comunicazioneUvacVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneRegioneVisitaAm() {
		return comunicazioneRegioneVisitaAm;
	}

	public void setComunicazioneRegioneVisitaAm(boolean comunicazioneRegioneVisitaAm) {
		this.comunicazioneRegioneVisitaAm = comunicazioneRegioneVisitaAm;
	}

	public void setComunicazioneRegioneVisitaAm(String flag) {
		this.comunicazioneRegioneVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneAltroVisitaAm() {
		return comunicazioneAltroVisitaAm;
	}

	public void setComunicazioneAltroVisitaAm(boolean comunicazioneAltroVisitaAm) {
		this.comunicazioneAltroVisitaAm = comunicazioneAltroVisitaAm;
	}

	public void setComunicazioneAltroVisitaAm(String flag) {
		this.comunicazioneAltroVisitaAm = DatabaseUtils.parseBoolean(flag);
	}

	public String getComunicazioneAltroTestoVisitaAm() {
		return comunicazioneAltroTestoVisitaAm;
	}

	public void setComunicazioneAltroTestoVisitaAm(String comunicazioneAltroTestoVisitaAm) {
		this.comunicazioneAltroTestoVisitaAm = comunicazioneAltroTestoVisitaAm;
	}

	public String getNoteVisitaAm() {
		return noteVisitaAm;
	}

	public void setNoteVisitaAm(String noteVisitaAm) {
		this.noteVisitaAm = noteVisitaAm;
	}
	
	


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
