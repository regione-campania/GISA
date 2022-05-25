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

public class MorteANM {

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";

	@Column(columnName = "data_morte_am", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataMorteAm = null;

	@Column(columnName = "id_luogo_verifica_morte_am", columnType = INT, table = nome_tabella)
	private int idLuogoVerificaMorteAm = -1;

	@Column(columnName = "descrizione_luogo_verifica_morte_am", columnType = STRING, table = nome_tabella)
	private String descrizioneLuogoVerificaMorteAm = "";

	@Column(columnName = "causa_morte_am", columnType = STRING, table = nome_tabella)
	private String causaMorteAm = "";

	@Column(columnName = "impianto_termodistruzione_morte_am", columnType = STRING, table = nome_tabella)
	private String impiantoTermodistruzioneMorteAm = "";

	@Column(columnName = "destinazione_carcassa_morte_am", columnType = STRING, table = nome_tabella)
	private String destinazioneCarcassaMorteAm = "";

	@Column(columnName = "comunicazione_asl_origine_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAslOrigineMorteAm = false;

	@Column(columnName = "comunicazione_proprietario_animale_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioAnimaleMorteAm = false;

	@Column(columnName = "comunicazione_azienda_origine_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAziendaOrigineMorteAm = false;

	@Column(columnName = "comunicazione_proprietario_macello_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioMacelloMorteAm = false;

	@Column(columnName = "comunicazione_pif_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazionePifMorteAm = false;

	@Column(columnName = "comunicazione_uvac_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneUvacMorteAm = false;

	@Column(columnName = "comunicazione_regione_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneRegioneMorteAm = false;

	@Column(columnName = "comunicazione_altro_morte_am", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAltroMorteAm = false;

	@Column(columnName = "comunicazione_altro_testo_morte_am", columnType = STRING, table = nome_tabella)
	private String comunicazioneAltroTestoMorteAm = "";

	@Column(columnName = "note_morte_am", columnType = STRING, table = nome_tabella)
	private String noteMorteAm = "";

	@Column(columnName = "data_sblocco_morte_am", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataSbloccoMorteAm = null;

	@Column(columnName = "data_blocco_morte_am", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataBloccoMorteAm = null;

	@Column(columnName = "id_destinazione_sblocco_morte_am", columnType = INT, table = nome_tabella)
	private int idDestinazioneSbloccoMorteAm = -1;

	public Timestamp getDataMorteAm() {
		return dataMorteAm;
	}

	public void setDataMorteAm(Timestamp dataMorteAm) {
		this.dataMorteAm = dataMorteAm;
	}

	public void setDataMorteAm(String data) {
		this.dataMorteAm = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdLuogoVerificaMorteAm() {
		return idLuogoVerificaMorteAm;
	}

	public void setIdLuogoVerificaMorteAm(int idLuogoVerificaMorteAm) {
		this.idLuogoVerificaMorteAm = idLuogoVerificaMorteAm;
	}

	public void setIdLuogoVerificaMorteAm(String idLuogoVerificaMorteAm) {

		if (idLuogoVerificaMorteAm != null && !("").equals(idLuogoVerificaMorteAm))
			this.idLuogoVerificaMorteAm = Integer.parseInt(idLuogoVerificaMorteAm);
	}

	public String getCausaMorteAm() {
		return causaMorteAm;
	}

	public void setCausaMorteAm(String causaMorteAm) {
		this.causaMorteAm = causaMorteAm;
	}

	public String getImpiantoTermodistruzioneMorteAm() {
		return impiantoTermodistruzioneMorteAm;
	}

	public void setImpiantoTermodistruzioneMorteAm(String impiantoTermodistruzioneMorteAm) {
		this.impiantoTermodistruzioneMorteAm = impiantoTermodistruzioneMorteAm;
	}

	public String getDestinazioneCarcassaMorteAm() {
		return destinazioneCarcassaMorteAm;
	}

	public void setDestinazioneCarcassaMorteAm(String destinazioneCarcassaMorteAm) {
		this.destinazioneCarcassaMorteAm = destinazioneCarcassaMorteAm;
	}

	public boolean isComunicazioneAslOrigineMorteAm() {
		return comunicazioneAslOrigineMorteAm;
	}

	public void setComunicazioneAslOrigineMorteAm(boolean comunicazioneAslOrigineMorteAm) {
		this.comunicazioneAslOrigineMorteAm = comunicazioneAslOrigineMorteAm;
	}

	public void setComunicazioneAslOrigineMorteAm(String flag) {
		this.comunicazioneAslOrigineMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneProprietarioAnimaleMorteAm() {
		return comunicazioneProprietarioAnimaleMorteAm;
	}

	public void setComunicazioneProprietarioAnimaleMorteAm(boolean comunicazioneProprietarioAnimaleMorteAm) {
		this.comunicazioneProprietarioAnimaleMorteAm = comunicazioneProprietarioAnimaleMorteAm;
	}

	public void setComunicazioneProprietarioAnimaleMorteAm(String flag) {
		this.comunicazioneProprietarioAnimaleMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneProprietarioMacelloMorteAm() {
		return comunicazioneProprietarioMacelloMorteAm;
	}

	public void setComunicazioneProprietarioMacelloMorteAm(boolean comunicazioneProprietarioMacelloMorteAm) {
		this.comunicazioneProprietarioMacelloMorteAm = comunicazioneProprietarioMacelloMorteAm;
	}

	public void setComunicazioneProprietarioMacelloMorteAm(String flag) {
		this.comunicazioneProprietarioMacelloMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazionePifMorteAm() {
		return comunicazionePifMorteAm;
	}

	public void setComunicazionePifMorteAm(boolean comunicazionePifMorteAm) {
		this.comunicazionePifMorteAm = comunicazionePifMorteAm;
	}

	public void setComunicazionePifMorteAm(String flag) {
		this.comunicazionePifMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneUvacMorteAm() {
		return comunicazioneUvacMorteAm;
	}

	public void setComunicazioneUvacMorteAm(boolean comunicazioneUvacMorteAm) {
		this.comunicazioneUvacMorteAm = comunicazioneUvacMorteAm;
	}

	public void setComunicazioneUvacMorteAm(String flag) {
		this.comunicazioneUvacMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneRegioneMorteAm() {
		return comunicazioneRegioneMorteAm;
	}

	public void setComunicazioneRegioneMorteAm(boolean comunicazioneRegioneMorteAm) {
		this.comunicazioneRegioneMorteAm = comunicazioneRegioneMorteAm;
	}

	public void setComunicazioneRegioneMorteAm(String flag) {
		this.comunicazioneRegioneMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public boolean isComunicazioneAltroMorteAm() {
		return comunicazioneAltroMorteAm;
	}

	public void setComunicazioneAltroMorteAm(boolean comunicazioneAltroMorteAm) {
		this.comunicazioneAltroMorteAm = comunicazioneAltroMorteAm;
	}

	public void setComunicazioneAltroMorteAm(String flag) {
		this.comunicazioneAltroMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public String getNoteMorteAm() {
		return noteMorteAm;
	}

	public void setNoteMorteAm(String noteMorteAm) {
		this.noteMorteAm = noteMorteAm;
	}

	public boolean isComunicazioneAziendaOrigineMorteAm() {
		return comunicazioneAziendaOrigineMorteAm;
	}

	public void setComunicazioneAziendaOrigineMorteAm(boolean comunicazioneAziendaOrigineMorteAm) {
		this.comunicazioneAziendaOrigineMorteAm = comunicazioneAziendaOrigineMorteAm;
	}

	public void setComunicazioneAziendaOrigineMorteAm(String flag) {
		this.comunicazioneAziendaOrigineMorteAm = DatabaseUtils.parseBoolean(flag);
	}

	public String getComunicazioneAltroTestoMorteAm() {
		return comunicazioneAltroTestoMorteAm;
	}

	public void setComunicazioneAltroTestoMorteAm(String comunicazioneAltroTestoMorteAm) {
		this.comunicazioneAltroTestoMorteAm = comunicazioneAltroTestoMorteAm;
	}

	public String getDescrizioneLuogoVerificaMorteAm() {
		return descrizioneLuogoVerificaMorteAm;
	}

	public void setDescrizioneLuogoVerificaMorteAm(String descrizioneLuogoVerificaMorteAm) {
		this.descrizioneLuogoVerificaMorteAm = descrizioneLuogoVerificaMorteAm;
	}

	public Timestamp getDataSbloccoMorteAm() {
		return dataSbloccoMorteAm;
	}

	public void setDataSbloccoMorteAm(Timestamp dataSbloccoMorteAm) {
		this.dataSbloccoMorteAm = dataSbloccoMorteAm;
	}

	public void setDataSbloccoMorteAm(String data) {
		this.dataSbloccoMorteAm = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public Timestamp getDataBloccoMorteAm() {
		return dataBloccoMorteAm;
	}

	public void setDataBloccoMorteAm(Timestamp dataBloccoMorteAm) {
		this.dataBloccoMorteAm = dataBloccoMorteAm;
	}

	public void setDataBloccoMorteAm(String data) {
		this.dataBloccoMorteAm = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdDestinazioneSbloccoMorteAm() {
		return idDestinazioneSbloccoMorteAm;
	}

	public void setIdDestinazioneSbloccoMorteAm(int idDestinazioneSbloccoMorteAm) {
		this.idDestinazioneSbloccoMorteAm = idDestinazioneSbloccoMorteAm;
	}
	
	public void setIdDestinazioneSbloccoMorteAm(String idDestinazioneSbloccoMorteAm) {
		this.idDestinazioneSbloccoMorteAm = Integer.valueOf(idDestinazioneSbloccoMorteAm);
	}

}
