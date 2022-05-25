/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class EventoList extends Vector implements SyncableList {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.registrazioniAnimali.base.EventoList.class);

	public final static String tableName = "evento";
	public final static String uniqueField = "id";
	protected java.sql.Timestamp lastAnchor = null;
	protected java.sql.Timestamp nextAnchor = null;
	protected int syncType = Constants.NO_SYNC;
	protected PagedListInfo pagedListInfo = null;

	protected Boolean minerOnly = null;
	protected int typeId = 0;
	protected int stageId = -1;

	private int id = -1;
	private int idTipologiaEvento = -1;
	private String idTipologiaEventoString;
	private int id_animale = -1;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;
	private int id_utente_inserimento = -1;
	private int id_utente_modifica = -1;
	private java.sql.Timestamp data_evento;
	private int idSpecieAnimale = -1;
	private int idAslInserimentoEventoBDU = -1;
	private java.sql.Timestamp BDUa;
	private java.sql.Timestamp BDUda;
	private java.sql.Timestamp eventoda;
	private java.sql.Timestamp eventoa;
	private int id_proprietario_corrente = -1;
	private int id_detentore_corrente = -1;
	private ArrayList<Integer> tipologieToSet = new ArrayList<Integer>();

	// Evento registrazione in BDU
	private java.sql.Timestamp data_registrazione;
	private int id_asl_registrazione = -1;

	// Dati evento inserimento microchip
	private java.sql.Timestamp data_inserimento_microchip;
	private String numero_microchip_assegnato;
	private int id_veterinario_privato_inserimento_microchip = -1;

	// Dati evento inserimento secondo microchip
	private java.sql.Timestamp data_inserimento_secondo_microchip;
	private String numero_secondo_microchip_assegnato;

	// Dati evento sterilizzazione
	private java.sql.Timestamp data_sterilizzazione;
	private boolean flag_richiesta_contributo_regionale;
	private boolean checkFlagRichiestaContributo = false;
	private int id_progetto_di_sterilizzazione_richiesto = -1;
	private int tipologia_soggetto_sterilizzante = -1;
	/**
	 * creare lookup tipologia_soggento sterilizzante con valori possibili asl o
	 * llp
	 */
	private int id_soggetto_sterilizzante = -1; // id asl o id llp

	// Dati evento cattura
	private java.sql.Timestamp data_cattura;
	private int id_comune_cattura = -1;
	private String indirizzo_cattura;
	private String verbale_cattura;
	private String luogo_cattura;

	// Dati evento rilascio passaporto
	private java.sql.Timestamp data_rilascio_passaporto;
	private String numero_passaporto;

	// Dati evento di decesso
	private java.sql.Timestamp data_decesso;
	private int id_causa_decesso = -1; // Creare lookup possibili cause
	private boolean flag_decesso_presunto;
	private String luogo_decesso;

	// Dati evento smarrimento
	private java.sql.Timestamp data_smarrimento;
	private boolean flag_presenza_importo_smarrimento;
	private double importo_smarrimento = 0;
	private String luogo_smarrimento;

	// Dati evento ritrovamento
	private java.sql.Timestamp data_ritrovamento;
	private int id_comune_ritrovamento = -1;
	private String luogo_ritrovamento;
	private int id_nuovo_proprietario = -1;
	private int id_detentore = -1;
	
	// Dati evento ritrovamento non denunciato
	private java.sql.Timestamp data_ritrovamento_nd;
	private int id_comune_ritrovamento_nd = -1;
	private String luogo_ritrovamento_nd;
	private int id_nuovo_proprietario_nd = -1;
	private int id_detentore_nd = -1;

	// Dati evento cessione
	private java.sql.Timestamp data_cessione;
	private int nuovo_proprietario = -1;
	/**
	 * In questo evento si tratta di un campo testuale perchè il soggetto di
	 * altra asl non è censito
	 */
	private int id_vecchio_proprietario = -1;
	private int id_asl_vecchio_proprietario = -1;
	private int id_asl_nuovo_proprietario = -1;
	private boolean get_only_opened = false;

	// Dati evento presa in carico da cessione
	private java.sql.Timestamp data_presa_in_carico_da_cessione;
	private int id_nuovo_proprietario_cessione = -1;
	// private int id_vecchio_proprietario_cessione = -1;
	// private int id_asl_vecchio_proprietario_cessione = -1;
	private int id_asl_nuovo_proprietario_cessione = -1;

	// Dati evento trasferimento
	private java.sql.Timestamp data_trasferimento;
	private int id_nuovo_proprietario_trasferimento = -1;
	private int id_vecchio_proprietario_trasferimento = -1;
	private String luogo_trasferimento;
	private int id_comune_nuovo_proprietario_trasferimento = -1;
	private int id_nuovo_detentore_trasferimento = -1;
	private int id_vecchio_detentore_trasferimento = -1;

	// Dati trasferimento fuori regione
	private java.sql.Timestamp data_trasferimento_fuori_regione;
	private int id_nuovo_proprietario_trasf_fuori_regione = -1;
	private int id_vecchio_proprietario_trasf_fuori_regione = -1;
	private int id_asl_vecchio_proprietario_trasf_fuori_regione = -1;
	private int id_asl_nuovo_proprietario_trasf_fuori_regione = -1;
	private int id_regione_trasferimento_a = -1;
	private int id_vecchio_detentore = -1;
	private String luogo_trasf_fuori_regione;

	// Dati evento rientro da fuori regione
	private java.sql.Timestamp data_rientro_da_fuori_regione;
	private int id_nuovo_proprietario_rientro_f_regione = -1;
	private String luogo_rientro_f_regione;
	private int id_nuovo_detentore_rientro_f_regione = -1;
	private int id_regione_rientro_da = -1;
	private int id_vecchio_proprietario_rientro_f_regione = -1;

	// Dati evento rientro da fuori stato
	private java.sql.Timestamp data_rientro_da_fuori_stato;
	private int id_nuovo_proprietario_rientro_f_stato = -1;
	private String luogo_rientro_f_stato;
	private int id_nuovo_detentore_rientro_f_stato = -1;
	private int id_continente_rientro_da = -1;
	private int id_asl_nuovo_proprietario_rientro_fuori_stato = -1;

	// DATI EVENTO TRASFERIMENTO F REGIONE SOLO PROP

	private java.sql.Timestamp data_trasferimento_fuori_regione_solo_proprietario;

	// Dati evento furto

	private java.sql.Timestamp data_furto;
	private String luogo_furto;
	private String dati_denuncia;

	// Dati evento adozione da colonia
	private java.sql.Timestamp data_adozione_colonia;
	private int id_colonia;
	private int id_comune_destinatario_adozione_colonia;
	private String luogo_adozione_colonia;
	private int id_nuovo_proprietario_adozione_colonia;
	private int id_asl_destinazione_adozione_colonia;
	private int id_colonia_provenienza;

	// Dati evento adozione da canile
	private java.sql.Timestamp data_adozione_canile;
	private int id_comune_destinatario_adozione_canile = -1;
	private int id_asl_destinazione_adozione = -1;
	private int id_nuovo_proprietario_adozione_canile = -1;
	private int id_canile_provenienza = -1; // detentore
	private String luogo_adozione;
	private int id_proprietario_prima_adozione = -1; // Canile o sindaco

	// Dati evento restituzione a canile
	private java.sql.Timestamp data_restituzione_canile;
	private int id_canile = -1;
	private int id_proprietario_da_restituzione = -1;

	// Dati evento adozione fuori asl
	private java.sql.Timestamp data_adozione_fuori_asl;
	private int id_comune_destinatario_adozione_fuori_asl = -1;
	private int id_comune_cedente = -1;
	private int id_asl_cedente = -1;
	private int id_canile_provenienza_fuori_asl;
	private int id_nuovo_proprietario_adozione_fuori_asl;

	// Dati evento vaccino anti rabbico
	private java.sql.Timestamp data_vaccino_antirabbico;
	private String lotto_vaccino;

	// Dati evento ricattura
	private java.sql.Timestamp data_cattura_2;
	private int id_comune_cattura_2;
	private String indirizzo_cattura_2;
	private String verbale_cattura_2;
	private String luogo_cattura_2;

	// Dati evento cambio detentore

	private java.sql.Timestamp data_cambio_detentore;
	private int id_detentore_cambio = -1;
	private int id_vecchio_detentore_cambio = -1;

	// Dati evento restituzione a proprietario

	private java.sql.Timestamp data_restituzione;
	
	// Dati evento morsicatura

	private java.sql.Timestamp data_morso;
	
	private java.sql.Timestamp data_aggressione;

	// Dati evento esitoControlli

	private java.sql.Timestamp data_esito;
	private boolean flag_ehrlichiosi = false;
	private java.sql.Timestamp data_ehrlichiosi;
	private int esito_ehrlichiosi = -1;

	private boolean flag_rickettiosi = false;
	private java.sql.Timestamp data_rickettiosi;
	private int esito_rickettiosi = -1;

	// Dati reimmissione
	private java.sql.Timestamp data_reimmissione;
	private int id_comune_reimmissione = -1;
	private String luogo_reimmissione;

	// Dati ricattura
	private java.sql.Timestamp data_ricattura;
	private int id_comune_ricattura;

	// Dati trasferimento canile
	private java.sql.Timestamp data_trasferimento_canile;

	// dati adozione distanza
	private java.sql.Timestamp data_adozione_distanza;
	
	
	private java.sql.Timestamp data_prelievo_leish;

	// Dati animale

	private int flag_circuito_commerciale = -1;
	
	//DATI TIPO VACCINAZIONE
	private int tipoVaccinazione = 0; //1: rabbia 2: leishmaniosi
	
	public int getTipoVaccinazione() {
		return tipoVaccinazione;
	}
	
	public void setTipoVaccinazione(int tipoVaccinazione) {
		this.tipoVaccinazione=tipoVaccinazione;
	}

	public int getFlag_circuito_commerciale() {
		return flag_circuito_commerciale;
	}

	public void setFlag_circuito_commerciale(int flag_circuito_commerciale) {
		this.flag_circuito_commerciale = flag_circuito_commerciale;
	}

	
	
	public java.sql.Timestamp getData_prelievo_leish() {
		return data_prelievo_leish;
	}

	public void setData_prelievo_leish(java.sql.Timestamp data_prelievo_leish) {
		this.data_prelievo_leish = data_prelievo_leish;
	}

	public int getId_proprietario_corrente() {
		return id_proprietario_corrente;
	}

	public void setId_proprietario_corrente(int id_proprietario_corrente) {
		this.id_proprietario_corrente = id_proprietario_corrente;
	}

	public int getId_detentore_corrente() {
		return id_detentore_corrente;
	}

	public void setId_detentore_corrente(int id_detentore_corrente) {
		this.id_detentore_corrente = id_detentore_corrente;
	}

	public int getIdAslInserimentoEventoBDU() {
		return idAslInserimentoEventoBDU;
	}

	public void setIdAslInserimentoEventoBDU(int idAslInserimentoEventoBDU) {
		this.idAslInserimentoEventoBDU = idAslInserimentoEventoBDU;
	}
	
	public void setIdAslInserimentoEventoBDU(String idAslInserimentoEventoBDU) {
		this.idAslInserimentoEventoBDU = new Integer (idAslInserimentoEventoBDU);
	}

	protected void createFilterEvento(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (id > -1) {
			sqlFilter.append("AND e.id_evento = ? ");
		}

		if (id_animale != -1) {
			sqlFilter.append("AND e.id_animale = ? ");
		}

		if (idSpecieAnimale > -1) {
			sqlFilter.append("AND e.id_specie_animale = ? ");
		}

		if (idTipologiaEventoString != null) {
			sqlFilter.append("AND e.id_tipologia_evento in ("+ idTipologiaEventoString+") ");
		} else if (idTipologiaEvento != -1) {
			sqlFilter.append("AND e.id_tipologia_evento = ? ");
		}
		else 

		if (entered != null) {
			sqlFilter.append("AND e.entered = ? ");
		}

		if (modified != null) {
			sqlFilter.append("AND e.modified = ? ");
		}

		if (id_utente_inserimento != -1) {
			sqlFilter.append("AND e.id_utente_inserimento = ? ");
		}

		if (id_utente_modifica != -1) {
			sqlFilter.append("AND e.id_utente_modifica = ? ");
		}

		
		if (id_proprietario_corrente > 0){
			sqlFilter.append("AND e.id_proprietario_corrente = ? ");
		}
		
		if (id_detentore_corrente > 0){
			sqlFilter.append("AND e.id_detentore_corrente = ? ");
		}
		if (BDUa != null && BDUda != null) {
			sqlFilter.append("AND e.entered >= ? and e.entered <= ? ");
		}
		
		if (idAslInserimentoEventoBDU > 0){
			sqlFilter.append("AND e.id_asl = ?");
		}
		
		sqlFilter.append("AND e.trashed_date is null "); //si può cancellare? uso l'altro campo
		sqlFilter.append("AND e.data_cancellazione is null ");
	}

	protected void createFilterAnimale(Connection db, StringBuffer sqlFilter) {

		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (flag_circuito_commerciale > -1) {
			if (flag_circuito_commerciale == 1)
				sqlFilter.append("AND a.flag_circuito_commerciale = true ");
			else if (flag_circuito_commerciale == 0)
				sqlFilter.append("AND a.flag_circuito_commerciale = false ");
		}

	}

	protected StringBuffer createFilterRegistrazione(Connection db) {
		StringBuffer registrazioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, registrazioneStringBuffer);

		if (data_registrazione != null) {
			registrazioneStringBuffer.append("AND reg.data_registrazione = ? ");
		}

		if (id_asl_registrazione != -1) {
			registrazioneStringBuffer
					.append("AND reg.id_asl_registrazione = ? ");
		}

		if (eventoda != null && eventoa != null) {
			registrazioneStringBuffer
					.append("AND reg.data_registrazione >= ? and reg.data_registrazione <= ? ");
		}

		return registrazioneStringBuffer;
	}

	protected int prepareFilterRegistrazione(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);
		if (data_registrazione != null) {
			pst.setTimestamp(++i, data_registrazione);
		}

		if (id_asl_registrazione != -1) {
			pst.setInt(++i, id_asl_registrazione);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterDecesso(Connection db) {

		StringBuffer decessoStringBuffer = new StringBuffer();

		if (data_decesso != null) {
			decessoStringBuffer.append("AND decess.data_decesso = ? ");
		}

		if (id_causa_decesso > -1) {
			decessoStringBuffer.append("AND decess.id_causa_decesso = ? ");
		}

		// decessoStringBuffer.append("AND decess.flag_decesso_presunto = ? ");

		if (luogo_decesso != null) {
			decessoStringBuffer.append("AND decess.luogo_decesso = ? ");
		}
		if (eventoda != null && eventoa != null) {
			decessoStringBuffer
					.append("AND decess.data_decesso >= ? and decess.data_decesso <= ? ");
		}

		return decessoStringBuffer;
	}

	protected int prepareFilterDecesso(PreparedStatement pst, int i)
			throws SQLException {

		StringBuffer decessoStringBuffer = new StringBuffer();

		if (data_decesso != null) {
			pst.setTimestamp(++i, data_decesso);
		}

		if (id_causa_decesso > -1) {
			pst.setInt(++i, id_causa_decesso);
		}

		// pst.setBoolean(++i, flag_decesso_presunto);

		if (luogo_decesso != null) {
			pst.setString(++i, luogo_decesso);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	
	protected StringBuffer createFilterPrelievoLeish(Connection db) {

		StringBuffer prelievoStringBuffer = new StringBuffer();

		if (data_prelievo_leish != null) {
			prelievoStringBuffer.append("AND prelievo_leish.data_prelievo_leish = ? ");
		}

		if (eventoda != null && eventoa != null) {
			prelievoStringBuffer
					.append("AND decess.data_decesso >= ? and decess.data_decesso <= ? ");
		}

		return prelievoStringBuffer;
	}

	protected int prepareFilterPrelievoLeish(PreparedStatement pst, int i)
			throws SQLException {


		if (data_prelievo_leish != null) {
			pst.setTimestamp(++i, data_prelievo_leish);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterRestituzioneAslOrigine(Connection db) {

		StringBuffer restituzioneStringBuffer = new StringBuffer();


		if (eventoda != null && eventoa != null) {
			restituzioneStringBuffer
					.append("AND restituzione_asl_origine.data_restituzione_asl >= ? and restituzione_asl_origine.data_restituzione_asl <= ? ");
		}

		return restituzioneStringBuffer;
	}

	protected int prepareFilterRestituzioneAslOrigine(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	

	
	
	protected int prepareFilterBloccoAnimale(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterBloccoAnimale(Connection db) {

		StringBuffer bloccoAnimale = new StringBuffer();


		if (eventoda != null && eventoa != null) {
			bloccoAnimale
					.append("AND blocco.data_blocco >= ? and blocco.data_blocco <= ? ");
		}

		return bloccoAnimale;
	}
	
	
	
	protected int prepareFilterSbloccoAnimale(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterSbloccoAnimale(Connection db) {

		StringBuffer sbloccoAnimale = new StringBuffer();


		if (eventoda != null && eventoa != null) {
			sbloccoAnimale
					.append("AND sblocco.data_sblocco >= ? and sblocco.data_sblocco <= ? ");
		}

		return sbloccoAnimale;
	}
	
	
	
	protected int prepareFilterMutilazione(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterMutilazione(Connection db) {

		StringBuffer mutilazione = new StringBuffer();


		if (eventoda != null && eventoa != null) {
			mutilazione
					.append("AND mutilazione.data_mutilazione >= ? and mutilazione.data_mutilazione <= ? ");
		}

		return mutilazione;
	}
	
	protected int prepareFilterAllontanamento(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterAllontanamento(Connection db) {

		StringBuffer allontanamento = new StringBuffer();


		if (eventoda != null && eventoa != null) {
			allontanamento
					.append("AND allontanamento.data_allontanamento >= ? and allontanamento.data_allontanamento <= ? ");
		}

		return allontanamento;
	}
	
	

	protected StringBuffer createFilterSterilizzazione(Connection db) {

		StringBuffer sterilizzazioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, sterilizzazioneStringBuffer);

		if (data_sterilizzazione != null) {
			sterilizzazioneStringBuffer
					.append("AND ster.data_sterilizzazione = ? ");
		}

		if (checkFlagRichiestaContributo) {
			sterilizzazioneStringBuffer
					.append("AND ster.flag_richiesta_contributo_regionale = ? ");
		}
		if (id_progetto_di_sterilizzazione_richiesto != -1) {
			sterilizzazioneStringBuffer
					.append("AND ster.progetto_di_sterilizzazione_richiesto = ? ");
		}
		if (tipologia_soggetto_sterilizzante != -1) {
			sterilizzazioneStringBuffer
					.append("AND ster.tipologia_soggetto_sterilizzante = ? ");
		}
		if (id_soggetto_sterilizzante != -1) {
			sterilizzazioneStringBuffer
					.append("AND ster.id_soggetto_sterilizzante = ? ");
		}
		if (eventoda != null && eventoa != null) {
			sterilizzazioneStringBuffer
					.append("AND ster.data_sterilizzazione >= ? and ster.data_sterilizzazione <= ? ");
		}
		return sterilizzazioneStringBuffer;
	}

	protected int prepareFilterSterilizzazione(PreparedStatement pst, int i)
			throws SQLException {

		// int i = 0;

		// i = prepareFilterEvento (pst, i);

		if (data_sterilizzazione != null) {
			pst.setTimestamp(++i, data_sterilizzazione);
		}

		if (checkFlagRichiestaContributo) {
			pst.setBoolean(++i, flag_richiesta_contributo_regionale);
		}

		if (id_progetto_di_sterilizzazione_richiesto != -1) {
			pst.setInt(++i, id_progetto_di_sterilizzazione_richiesto);
		}
		if (tipologia_soggetto_sterilizzante != -1) {
			pst.setInt(++i, tipologia_soggetto_sterilizzante);
		}
		if (id_soggetto_sterilizzante != -1) {
			pst.setInt(++i, id_soggetto_sterilizzante);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}

	protected StringBuffer createFilterFurto(Connection db) {
		StringBuffer furtoStringBuffer = new StringBuffer();

		// createFilterEvento(db, furtoStringBuffer);

		if (data_furto != null) {
			furtoStringBuffer.append("AND furto.data_furto = ? ");
		}

		if (luogo_furto != null && !"".equals(luogo_furto)) {
			furtoStringBuffer.append("AND furto.luogo_furto = ? ");
		}

		if (dati_denuncia != null && !"".equals(dati_denuncia)) {
			furtoStringBuffer.append("AND furto.dati_denuncia = ? ");
		}

		if (eventoda != null && eventoa != null) {
			furtoStringBuffer.append("AND furto.data_furto >= ? and furto.data_furto <= ? ");
		}

		return furtoStringBuffer;
	}

	protected int prepareFilterFurto(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		if (data_furto != null) {
			pst.setTimestamp(++i, data_furto);
		}

		if (luogo_furto != null && !"".equals(luogo_furto)) {
			pst.setString(++i, luogo_furto);
		}

		if (dati_denuncia != null && !"".equals(dati_denuncia)) {
			pst.setString(++i, dati_denuncia);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}

	protected StringBuffer createFilterMicrochip(Connection db) {
		StringBuffer microchipStringBuffer = new StringBuffer();

		// createFilterEvento(db, microchipStringBuffer);

		if (data_inserimento_microchip != null) {
			microchipStringBuffer
					.append("AND microchip.data_inserimento_microchip = ? ");
		}

		if (numero_microchip_assegnato != null
				&& !"".equals(numero_microchip_assegnato)) {
			microchipStringBuffer
					.append("AND microchip.numero_microchip_assegnato = ? ");
		}

		if (id_veterinario_privato_inserimento_microchip != -1) {
			microchipStringBuffer
					.append("AND microchip.id_veterinario_privato_inserimento_microchip = ? ");
		}

		if (eventoda != null && eventoa != null) {
			microchipStringBuffer
					.append("AND microchip.data_inserimento_microchip <= ? and microchip.data_inserimento_microchip >= ? ");
		}

		return microchipStringBuffer;
	}

	protected int prepareFilterMicrochip(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		if (data_inserimento_microchip != null) {
			pst.setTimestamp(++i, data_inserimento_microchip);
		}

		if (numero_microchip_assegnato != null
				&& !"".equals(numero_microchip_assegnato)) {
			pst.setString(++i, numero_microchip_assegnato);
		}

		if (id_veterinario_privato_inserimento_microchip != -1) {
			pst.setInt(++i, id_veterinario_privato_inserimento_microchip);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterMicrochipSecondo(Connection db) {
		StringBuffer microchipStringBuffer = new StringBuffer();

		// createFilterEvento(db, microchipStringBuffer);

		microchipStringBuffer
				.append("AND microchip2.id_tipologia_microchip = ? ");

		if (data_inserimento_microchip != null) {
			microchipStringBuffer
					.append("AND microchip2.data_inserimento_microchip = ? ");
		}

		if (numero_microchip_assegnato != null
				&& !"".equals(numero_microchip_assegnato)) {
			microchipStringBuffer
					.append("AND microchip2.numero_microchip_assegnato = ? ");
		}

		if (id_veterinario_privato_inserimento_microchip != -1) {
			microchipStringBuffer
					.append("AND microchip2.id_veterinario_privato_inserimento_microchip = ? ");
		}

		if (eventoda != null && eventoa != null) {
			microchipStringBuffer
					.append("AND microchip2.data_inserimento_microchip >= ? and microchip2.data_inserimento_microchip <= ? ");
		}

		return microchipStringBuffer;
	}

	protected int prepareFilterMicrochipSecondo(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		pst.setInt(++i, 2);

		if (data_inserimento_microchip != null) {
			pst.setTimestamp(++i, data_inserimento_microchip);
		}

		if (numero_microchip_assegnato != null
				&& !"".equals(numero_microchip_assegnato)) {
			pst.setString(++i, numero_microchip_assegnato);
		}

		if (id_veterinario_privato_inserimento_microchip != -1) {
			pst.setInt(++i, id_veterinario_privato_inserimento_microchip);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterVaccinazioni(Connection db) {
		StringBuffer vacciniStringBuffer = new StringBuffer();

		// createFilterEvento(db, microchipStringBuffer);

		if (eventoda != null && eventoa != null) {
			vacciniStringBuffer
					.append(" AND vaccini.data_inserimento_vaccinazione >= ? and vaccini.data_inserimento_vaccinazione<= ? ");
		}
		if (tipoVaccinazione != 0 ) {
			vacciniStringBuffer
					.append(" AND vaccini.id_tipo_vaccino = ?");
		}
		return vacciniStringBuffer;
	}

	protected int prepareFilterVaccinazioni(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);


		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		if (tipoVaccinazione != 0 ) {
			pst.setInt(++i, tipoVaccinazione);
		}

		return i;
	}

	protected StringBuffer createFilterMorsicatura(Connection db) {
		StringBuffer morsicaturaStringBuffer = new StringBuffer();

		// createFilterEvento(db, registrazioneStringBuffer);

		if (data_morso != null) {
			morsicaturaStringBuffer.append(" AND morsicatura.data_morso = ? ");
		}

		if (eventoda != null && eventoa != null) {
			morsicaturaStringBuffer
					.append(" AND morsicatura.data_morso >= ? and morsicatura.data_morso <= ? ");
		}

		return morsicaturaStringBuffer;
	}
	
	protected StringBuffer createFilterAggressione(Connection db) {
		StringBuffer aggrStringBuffer = new StringBuffer();

		// createFilterEvento(db, registrazioneStringBuffer);

		if (data_aggressione != null) {
			aggrStringBuffer.append(" AND aggr.data_aggressione = ? ");
		}

		if (eventoda != null && eventoa != null) {
			aggrStringBuffer
					.append(" AND aggr.data_aggressione >= ? and aggr.data_aggressione <= ? ");
		}

		return aggrStringBuffer;
	}

	protected int prepareFilterMorsicatura(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);
		if (data_morso != null) {
			pst.setTimestamp(++i, data_morso);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	protected int prepareFilterAggressione(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);
		if (data_aggressione != null) {
			pst.setTimestamp(++i, data_aggressione);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterEsitoControlli(Connection db) {
		StringBuffer controlliStringBuffer = new StringBuffer();

		// createFilterEvento(db, registrazioneStringBuffer);

		if (data_esito != null) {
			controlliStringBuffer.append(" AND controlli.data_controllo = ? ");
		}

		if (flag_ehrlichiosi) {
			controlliStringBuffer
					.append(" AND controlli.controllo_ehrlichiosi = ? ");
		}
		if (data_ehrlichiosi != null) {
			controlliStringBuffer
					.append(" AND controlli.data_controllo_ehrlichiosi = ? ");
		}
		if (esito_ehrlichiosi > -1) {
			controlliStringBuffer
					.append(" AND controlli.esito_controllo_ehrlichiosi = ? ");
		}

		if (flag_rickettiosi) {
			controlliStringBuffer
					.append(" AND controlli.controllo_rickettiosi = ? ");
		}
		if (data_rickettiosi != null) {
			controlliStringBuffer
					.append(" AND controlli.data_controllo_rickettiosi = ? ");
		}
		if (esito_rickettiosi > -1) {
			controlliStringBuffer
					.append(" AND controlli.esito_controllo_rickettiosi = ? ");
		}

		if (eventoda != null && eventoa != null) {
			controlliStringBuffer
					.append(" AND controlli.data_controllo >= ? and controlli.data_controllo <= ?");
		}

		return controlliStringBuffer;
	}

	protected int prepareFilterEsitoControlli(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		if (data_esito != null) {
			pst.setTimestamp(++i, data_esito);
		}

		if (flag_ehrlichiosi) {
			pst.setBoolean(++i, flag_ehrlichiosi);
		}
		if (data_ehrlichiosi != null) {
			pst.setTimestamp(++i, data_ehrlichiosi);
		}
		if (esito_ehrlichiosi > -1) {
			pst.setInt(++i, esito_ehrlichiosi);
		}

		if (flag_rickettiosi) {
			pst.setBoolean(++i, flag_rickettiosi);
		}
		if (data_rickettiosi != null) {
			pst.setTimestamp(++i, data_rickettiosi);
		}
		if (esito_rickettiosi > -1) {
			pst.setInt(++i, esito_rickettiosi);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}

	protected StringBuffer createFilterReimmissione(Connection db) {
		StringBuffer reimmissioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, registrazioneStringBuffer);

		if (data_reimmissione != null) {
			reimmissioneStringBuffer
					.append("AND reimmissione.data_reimmissione = ? ");
		}

		if (id_comune_reimmissione > -1) {
			reimmissioneStringBuffer
					.append("AND reimmissione.id_comune_reimmissione = ? ");
		}
		if (luogo_reimmissione != null) {
			reimmissioneStringBuffer.append("AND reimmissione.luogo = ? ");
		}

		if (eventoda != null && eventoa != null) {
			reimmissioneStringBuffer
					.append("AND reimmissione.data_reimmissione >= ? and reimmissione.data_reimmissione <= ? ");
		}

		return reimmissioneStringBuffer;
	}

	protected int prepareFilterReimmissione(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		if (data_reimmissione != null) {
			pst.setTimestamp(++i, data_reimmissione);
		}

		if (id_comune_reimmissione > -1) {
			pst.setInt(++i, id_comune_reimmissione);
		}

		if (luogo_reimmissione != null) {
			pst.setString(++i, luogo_reimmissione);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterCommerciali(Connection db) {
		StringBuffer commercialiStringBuffer = new StringBuffer();

		// TODO

		if (eventoda != null && eventoa != null) {
			commercialiStringBuffer
					.append("AND commerciali.data_registrazione_esiti >= ? and commerciali.data_registrazione_esiti <=? ");
		}

		return commercialiStringBuffer;
	}

	protected int prepareFilterCommerciali(PreparedStatement pst, int i)
			throws SQLException {
		// i = prepareFilterEvento(pst, i);

		// TODO

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}

	protected StringBuffer createFilterCattura(Connection db) {
		StringBuffer catturaStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_cattura != null) {
			catturaStringBuffer.append("AND cattura.data_cattura = ? ");
		}

		if (verbale_cattura != null && !"".equals(verbale_cattura)) {
			catturaStringBuffer.append("AND cattura.verbale_cattura = ? ");
		}

		if (indirizzo_cattura != null && !"".equals(indirizzo_cattura)) {
			catturaStringBuffer.append("AND cattura.indirizzo_cattura = ? ");
		}
		if (id_comune_cattura != -1) {
			catturaStringBuffer.append("AND cattura.id_comune_cattura = ? ");
		}

		if (luogo_cattura != null && !"".equals(luogo_cattura)) {
			catturaStringBuffer.append("AND cattura.luogo_cattura = ? ");
		}

		if (eventoda != null && eventoa != null) {
			catturaStringBuffer
					.append("AND cattura.data_cattura >= ? and cattura.data_cattura <= ? ");
		}

		return catturaStringBuffer;
	}

	protected int prepareFilterCattura(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_cattura != null) {
			pst.setTimestamp(++i, data_cattura);
		}

		if (verbale_cattura != null && !"".equals(verbale_cattura)) {
			pst.setString(++i, verbale_cattura);
		}

		if (indirizzo_cattura != null && !"".equals(indirizzo_cattura)) {
			pst.setString(++i, indirizzo_cattura);
		}
		if (id_comune_cattura != -1) {
			pst.setInt(++i, id_comune_cattura);
		}

		if (luogo_cattura != null && !"".equals(luogo_cattura)) {
			pst.setString(++i, luogo_cattura);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterRitrovamento(Connection db) {
		StringBuffer ritrovamentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_ritrovamento != null) {
			ritrovamentoStringBuffer.append("AND rit.data_ritrovamento = ? ");
		}

		if (luogo_ritrovamento != null && !"".equals(luogo_ritrovamento)) {
			ritrovamentoStringBuffer.append("AND rit.luogo_ritrovamento = ? ");
		}

		if (id_comune_ritrovamento != -1) {
			ritrovamentoStringBuffer.append("AND rit.comune_ritrovamento = ? ");
		}

		if (eventoda != null && eventoa != null) {
			ritrovamentoStringBuffer
					.append("AND rit.data_ritrovamento >= ? and rit.data_ritrovamento <= ? ");
		}

		return ritrovamentoStringBuffer;
	}

	protected int prepareFilterRitrovamento(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_ritrovamento != null) {
			pst.setTimestamp(++i, data_ritrovamento);
		}

		if (luogo_ritrovamento != null && !"".equals(luogo_ritrovamento)) {
			pst.setString(++i, luogo_ritrovamento);
		}

		if (id_comune_ritrovamento != -1) {
			pst.setInt(++i, id_comune_ritrovamento);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}
	
	protected StringBuffer createFilterRitrovamentoNonDenunciato(Connection db) {
		StringBuffer ritrovamentoNonDenunciatoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_ritrovamento_nd != null) {
			ritrovamentoNonDenunciatoStringBuffer.append("AND rit_nd.data_ritrovamento_nd = ? ");
		}

		if (luogo_ritrovamento_nd != null && !"".equals(luogo_ritrovamento_nd)) {
			ritrovamentoNonDenunciatoStringBuffer.append("AND rit_nd.luogo_ritrovamento_nd = ? ");
		}

		if (id_comune_ritrovamento_nd != -1) {
			ritrovamentoNonDenunciatoStringBuffer.append("AND rit_nd.comune_ritrovamento_nd = ? ");
		}

		if (eventoda != null && eventoa != null) {
			ritrovamentoNonDenunciatoStringBuffer
					.append("AND rit_nd.data_ritrovamento_nd >= ? and rit_nd.data_ritrovamento_nd <= ? ");
		}

		return ritrovamentoNonDenunciatoStringBuffer;
	}

	protected int prepareFilterRitrovamentoNonDenunciato(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_ritrovamento_nd != null) {
			pst.setTimestamp(++i, data_ritrovamento_nd);
		}

		if (luogo_ritrovamento_nd != null && !"".equals(luogo_ritrovamento_nd)) {
			pst.setString(++i, luogo_ritrovamento_nd);
		}

		if (id_comune_ritrovamento_nd != -1) {
			pst.setInt(++i, id_comune_ritrovamento_nd);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		return i;
	}

	protected StringBuffer createFilterAdozione(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_adozione_canile != null) {
			adozioneStringBuffer.append("AND ado.data_adozione = ? ");
		}

		if (luogo_adozione != null && !"".equals(luogo_adozione)) {
			adozioneStringBuffer.append("AND ado.luogo = ? ");
		}

		if (id_comune_destinatario_adozione_canile != -1) {
			adozioneStringBuffer.append("AND ado.id_comune_destinazione = ? ");
		}

		if (id_asl_destinazione_adozione != -1) {
			adozioneStringBuffer
					.append("AND ado.id_asl_destinataria_adozione = ? ");
		}

		if (id_nuovo_proprietario_adozione_canile != -1) {
			adozioneStringBuffer.append("AND ado.id_proprietario = ? ");
		}

		if (id_canile_provenienza != -1) {
			adozioneStringBuffer.append("AND ado.id_vecchio_detentore = ? ");
		}
		if (id_proprietario_prima_adozione != -1) {
			adozioneStringBuffer.append("AND ado.id_vecchio_proprietario = ? ");
		}
		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND ado.data_adozione >= ? and ado.data_adozione <= ? ");
		}

		return adozioneStringBuffer;
	}
	
	
	protected StringBuffer createFilterAdozioneAssociazioni(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_adozione_canile != null) {
			adozioneStringBuffer.append("AND ado_associazioni.data_adozione = ? ");
		}

		if (luogo_adozione != null && !"".equals(luogo_adozione)) {
			adozioneStringBuffer.append("AND ado_associazioni.luogo = ? ");
		}

		if (id_comune_destinatario_adozione_canile != -1) {
			adozioneStringBuffer.append("AND ado_associazioni.id_comune_destinazione = ? ");
		}

		if (id_asl_destinazione_adozione != -1) {
			adozioneStringBuffer
					.append("AND ado_associazioni.id_asl_destinataria_adozione = ? ");
		}

		if (id_nuovo_proprietario_adozione_canile != -1) {
			adozioneStringBuffer.append("AND ado_associazioni.id_proprietario = ? ");
		}

		if (id_canile_provenienza != -1) {
			adozioneStringBuffer.append("AND ado_associazioni.id_vecchio_detentore = ? ");
		}
		if (id_proprietario_prima_adozione != -1) {
			adozioneStringBuffer.append("AND ado_associazioni.id_vecchio_proprietario = ? ");
		}
		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND ado_associazioni.data_adozione >= ? and ado_associazioni.data_adozione <= ? ");
		}

		return adozioneStringBuffer;
	}


	protected int prepareFilterAdozione(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_adozione_canile != null) {
			pst.setTimestamp(++i, data_adozione_canile);
		}

		if (luogo_adozione != null && !"".equals(luogo_adozione)) {
			pst.setString(++i, luogo_adozione);
		}

		if (id_comune_destinatario_adozione_canile != -1) {
			pst.setInt(++i, id_comune_destinatario_adozione_canile);
		}

		if (id_asl_destinazione_adozione != -1) {
			pst.setInt(++i, id_asl_destinazione_adozione);
		}

		if (id_nuovo_proprietario_adozione_canile != -1) {
			pst.setInt(++i, id_nuovo_proprietario_adozione_canile);
		}

		if (id_canile_provenienza != -1) {
			pst.setInt(++i, id_canile_provenienza);
		}
		if (id_proprietario_prima_adozione != -1) {
			pst.setInt(++i, id_proprietario_prima_adozione);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterAdozioneColonia(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_adozione_colonia != null) {
			adozioneStringBuffer
					.append("AND adoColonia.data_adozione_colonia = ? ");
		}

		if (luogo_adozione_colonia != null
				&& !"".equals(luogo_adozione_colonia)) {
			adozioneStringBuffer
					.append("AND adoColonia.luogo_adozione_colonia = ? ");
		}

		if (id_comune_destinatario_adozione_colonia > 0) {
			adozioneStringBuffer
					.append("AND adoColonia.id_comune_destinazione_adozione_colonia = ? ");
		}

		if (id_asl_destinazione_adozione_colonia > 0) {
			adozioneStringBuffer
					.append("AND adoColonia.id_asl_destinataria_adozione_colonia = ? ");
		}

		if (id_nuovo_proprietario_adozione_colonia > 0) {
			adozioneStringBuffer
					.append("AND adoColonia.id_proprietario_adozione_colonia = ? ");
		}

		if (id_colonia_provenienza > 0) {
			adozioneStringBuffer
					.append("AND adoColonia.id_vecchio_detentore_adozione_colonia = ? ");
		}
		if (id_proprietario_prima_adozione > 0) {
			adozioneStringBuffer
					.append("AND adoColonia.id_vecchio_proprietario_adozione_colonia = ? ");
		}
		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND ado.data_adozione >= ? and ado.data_adozione <= ? ");
		}

		return adozioneStringBuffer;
	}

	protected int prepareFilterAdozioneColonia(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_adozione_colonia != null) {
			pst.setTimestamp(++i, data_adozione_colonia);
		}

		if (luogo_adozione_colonia != null
				&& !"".equals(luogo_adozione_colonia)) {
			pst.setString(++i, luogo_adozione_colonia);
		}

		if (id_comune_destinatario_adozione_colonia > 0) {
			pst.setInt(++i, id_comune_destinatario_adozione_colonia);
		}

		if (id_asl_destinazione_adozione_colonia > 0) {
			pst.setInt(++i, id_asl_destinazione_adozione_colonia);
		}

		if (id_nuovo_proprietario_adozione_colonia > 0) {
			pst.setInt(++i, id_nuovo_proprietario_adozione_colonia);
		}

		if (id_colonia_provenienza > 0) {
			pst.setInt(++i, id_colonia_provenienza);
		}
		if (id_proprietario_prima_adozione > 0) {
			pst.setInt(++i, id_proprietario_prima_adozione);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterAdozioneDistanza(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_adozione_distanza != null) {
			adozioneStringBuffer.append("AND adoD.data_adozione_distanza = ? ");
		}

		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND adoD.data_adozione >= ? and adoD.data_adozione <= ? ");
		}

		return adozioneStringBuffer;
	}

	protected int prepareFilterAdozioneDistanza(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_adozione_distanza != null) {
			pst.setTimestamp(++i, data_adozione_canile);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected int prepareFilterInserimentoVaccino(PreparedStatement pst, int i)
			throws SQLException {

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}
		if (tipoVaccinazione != 0) {
			pst.setInt(++i, tipoVaccinazione);
		}

		return i;
	}

	protected StringBuffer createFilterVaccino(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND vaccino.data_inserimento_vaccinazione >= ? and vaccino.data_inserimento_vaccinazione <= ? ");
		}
		if (tipoVaccinazione!=0) {
			adozioneStringBuffer
					.append("AND vaccino.id_tipo_vaccino=?");
		}

		return adozioneStringBuffer;
	}
	
	
	protected StringBuffer createFilterPrelievoDNA(Connection db) {
		StringBuffer prelievoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (eventoda != null && eventoa != null) {
			prelievoStringBuffer
					.append("AND prelievo_dna.data_prelievo >= ? and prelievo_dna.data_prelievo <= ? ");
		}
		
		return prelievoStringBuffer;
	}
	
	protected StringBuffer createFilterCessioneImport(Connection db) {
		StringBuffer cessioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (eventoda != null && eventoa != null) {
			cessioneStringBuffer
					.append("AND cessione_import.data_cessione_import >= ? and cessione_import.data_cessione_import <= ? ");
		}
		
		return cessioneStringBuffer;
	}
	
	
	protected StringBuffer createFilterPresaCessioneImport(Connection db) {
		StringBuffer cessioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (eventoda != null && eventoa != null) {
			cessioneStringBuffer
					.append("AND presa_cessione_import.data_presa_in_carico_import >= ? and presa_cessione_import.data_presa_in_carico_import <= ? ");
		}
		
		return cessioneStringBuffer;
	}
	
	
	

	protected StringBuffer createFilterRestituzione(Connection db) {
		StringBuffer adozioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_restituzione_canile != null) {
			adozioneStringBuffer
					.append("AND rest.data_restituzione_canile = ? ");
		}

		if (id_canile != -1) {
			adozioneStringBuffer.append("AND rest.id_canile = ? ");
		}

		if (id_proprietario_da_restituzione != -1) {
			adozioneStringBuffer
					.append("AND rest.id_proprietario_da_restituzione = ? ");
		}

		if (eventoda != null && eventoa != null) {
			adozioneStringBuffer
					.append("AND rest.data_restituzione_canile >= ? and  rest.data_restituzione_canile <= ? ");
		}

		return adozioneStringBuffer;
	}

	protected int prepareFilterRestituzione(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_restituzione_canile != null) {
			pst.setTimestamp(++i, data_restituzione_canile);
		}

		if (id_canile != -1) {
			pst.setInt(++i, id_canile);
		}

		if (id_proprietario_da_restituzione != -1) {
			pst.setInt(++i, id_proprietario_da_restituzione);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterRilascioPassaporto(Connection db) {
		StringBuffer passaportoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_rilascio_passaporto != null) {
			passaportoStringBuffer
					.append("AND passaporto.data_rilascio_passaporto = ? ");
		}

		if (numero_passaporto != null && !("").equals(numero_passaporto)) {
			passaportoStringBuffer
					.append("AND passaporto.numero_passaporto = ? ");
		}

		if (eventoda != null && eventoa != null) {
			passaportoStringBuffer
					.append("AND passaporto.data_rilascio_passaporto >= ? and passaporto.data_rilascio_passaporto <= ? ");
		}

		return passaportoStringBuffer;
	}

	protected int prepareFilterRilascioPassaporto(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_rilascio_passaporto != null) {
			pst.setTimestamp(++i, data_rilascio_passaporto);
		}

		if (numero_passaporto != null && !("").equals(numero_passaporto)) {
			pst.setString(++i, numero_passaporto);
		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterCessione(Connection db) {
		StringBuffer cessioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_cessione != null) {
			cessioneStringBuffer.append("AND cessione.data_cessione = ? ");
		}

		if (nuovo_proprietario > -1) {
			cessioneStringBuffer
					.append("and cessione.id_nuovo_proprietario = ?");

		}

		if (id_vecchio_proprietario > -1) {
			cessioneStringBuffer
					.append("and cessione.id_vecchio_proprietario = ?");

		}

		if (id_asl_vecchio_proprietario > -1) {
			cessioneStringBuffer
					.append(" and cessione.id_asl_vecchio_proprietario_cessione = ?");

		}
		if (id_asl_nuovo_proprietario > -1) {
			cessioneStringBuffer
					.append(" and cessione.id_asl_nuovo_proprietario_cessione = ?");

		}

		if (get_only_opened) {

			cessioneStringBuffer
					.append(" and cessione.flag_accettato = false ");
		}
		if (eventoda != null && eventoa != null) {
			cessioneStringBuffer
					.append("AND cessione.data_cessione >= ? and cessione.data_cessione <= ? ");
		}

		return cessioneStringBuffer;
	}

	public boolean isGet_only_opened() {
		return get_only_opened;
	}

	public void setGet_only_opened(boolean get_only_opened) {
		this.get_only_opened = get_only_opened;
	}

	protected int prepareFilterCessione(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_cessione != null) {
			pst.setTimestamp(++i, data_cessione);
		}

		if (nuovo_proprietario > -1) {
			pst.setInt(++i, nuovo_proprietario);

		}

		if (id_vecchio_proprietario > -1) {
			pst.setInt(++i, id_vecchio_proprietario);

		}

		if (id_asl_vecchio_proprietario > -1) {
			pst.setInt(++i, id_asl_vecchio_proprietario);

		}
		if (id_asl_nuovo_proprietario > -1) {
			pst.setInt(++i, id_asl_nuovo_proprietario);

		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterPresaCessione(Connection db) {
		StringBuffer presaCessioneStringBuffer = new StringBuffer();

		if (data_presa_in_carico_da_cessione != null) {
			presaCessioneStringBuffer
					.append("AND presa_cessione.data_presa_in_carico = ? ");
		}

		if (id_nuovo_proprietario_cessione > -1) {
			presaCessioneStringBuffer
					.append("and presa_cessione.id_nuovo_proprietario = ?");

		}

		if (id_asl_nuovo_proprietario_cessione > -1) {
			presaCessioneStringBuffer.append("and presa_cessione.id_asl = ?");

		}

		if (eventoda != null && eventoa != null) {
			presaCessioneStringBuffer
					.append("AND presa_cessione.data_presa_in_carico >= ? and presa_cessione.data_presa_in_carico <= ? ");
		}

		return presaCessioneStringBuffer;
	}

	protected int prepareFilterPresaCessione(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_presa_in_carico_da_cessione != null) {
			pst.setTimestamp(++i, data_presa_in_carico_da_cessione);
		}

		if (id_nuovo_proprietario_cessione > -1) {
			pst.setInt(++i, id_nuovo_proprietario_cessione);

		}

		if (id_asl_nuovo_proprietario_cessione > -1) {
			pst.setInt(++i, id_asl_nuovo_proprietario_cessione);

		}
		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	
	
	protected StringBuffer createFilterAdozioneFuoriAsl(Connection db) {
		StringBuffer cessioneStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

	

		return cessioneStringBuffer;
	}



	protected int prepareFilterAdozioneFuoriAsl(PreparedStatement pst, int i)
			throws SQLException {

		
		return i;
	}

	protected StringBuffer createFilterPresaAdozioneFuoriAsl(Connection db) {
		StringBuffer presaCessioneStringBuffer = new StringBuffer();


		return presaCessioneStringBuffer;
	}

	protected int prepareFilterPresaAdozioneFuoriAsl(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		

		return i;
	}
	
	
	protected StringBuffer createFilterRientroFuoriRegione(Connection db) {
		StringBuffer rientroStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_rientro_da_fuori_regione != null) {
			rientroStringBuffer.append("AND rientro.data_rientro_fr = ? ");
		}

		if (id_nuovo_proprietario_rientro_f_regione > -1) {
			rientroStringBuffer.append("and rientro.id_proprietario = ?");

		}

		if (id_nuovo_detentore_rientro_f_regione > -1) {
			rientroStringBuffer.append("and rientro.id_detentore = ?");

		}

		if (id_asl_nuovo_proprietario_trasf_fuori_regione > -1) {
			rientroStringBuffer.append("and rientro.id_asl = ?");
		}
		if (id_regione_rientro_da > -1) {
			rientroStringBuffer.append("and rientro.id_regione_rientro_da = ?");
		}

		if (luogo_rientro_f_regione != null
				&& !("").equals(luogo_rientro_f_regione)) {
			rientroStringBuffer
					.append("and rientro.luogo_rientro_f_regione = ?");
		}

		if (eventoda != null && eventoa != null) {
			rientroStringBuffer
					.append("AND rientro.data_rientro_fr >= ? and rientro.data_rientro_fr <= ? ");
		}

		return rientroStringBuffer;
	}

	protected int prepareFilterRientroFuoriRegione(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_rientro_da_fuori_regione != null) {
			pst.setTimestamp(++i, data_rientro_da_fuori_regione);
		}

		if (id_nuovo_proprietario_rientro_f_regione > -1) {
			pst.setInt(++i, id_nuovo_proprietario_rientro_f_regione);

		}

		if (id_nuovo_detentore_rientro_f_regione > -1) {
			pst.setInt(++i, id_nuovo_detentore_rientro_f_regione);

		}

		if (id_asl_nuovo_proprietario_trasf_fuori_regione > -1) {
			pst.setInt(++i, id_asl_nuovo_proprietario_trasf_fuori_regione);
		}
		if (id_regione_rientro_da > -1) {
			pst.setInt(++i, id_regione_rientro_da);
		}

		if (luogo_rientro_f_regione != null
				&& !("").equals(luogo_rientro_f_regione)) {
			pst.setString(++i, luogo_rientro_f_regione);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	// inizio fuori stato
	protected StringBuffer createFilterRientroFuoriStato(Connection db) {
		StringBuffer rientroFSStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_rientro_da_fuori_stato != null) {
			rientroFSStringBuffer
					.append("AND rientroFS.data_rientro_fuori_stato = ? ");
		}

		if (id_nuovo_proprietario_rientro_f_stato > 0) {
			rientroFSStringBuffer
					.append("and rientroFS.id_proprietario_rientro_fuori_stato = ?");

		}

		if (id_nuovo_detentore_rientro_f_stato > 0) {
			rientroFSStringBuffer
					.append("and rientroFS.id_detentore_fuori_stato = ?");

		}

		if (id_asl_nuovo_proprietario_rientro_fuori_stato > 0) {
			rientroFSStringBuffer
					.append("and rientroFS.id_asl_fuori_stato = ?");
		}
		if (id_continente_rientro_da > 0) {
			rientroFSStringBuffer.append("and rientroFS.id_continente_da = ?");
		}

		if (luogo_rientro_f_stato != null
				&& !("").equals(luogo_rientro_f_regione)) {
			rientroFSStringBuffer.append("and rientroFS.luogo_fuori_stato = ?");
		}

		if (eventoda != null && eventoa != null) {
			rientroFSStringBuffer
					.append("AND rientroFS.data_rientro_fuori_stato >= ? and rientroFS.data_rientro_fuori_stato <= ? ");
		}

		return rientroFSStringBuffer;
	}

	protected int prepareFilterRientroFuoriStato(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_rientro_da_fuori_stato != null) {
			pst.setTimestamp(++i, data_rientro_da_fuori_stato);
		}

		if (id_nuovo_proprietario_rientro_f_stato > -1) {
			pst.setInt(++i, id_nuovo_proprietario_rientro_f_stato);

		}

		if (id_nuovo_detentore_rientro_f_stato > -1) {
			pst.setInt(++i, id_nuovo_detentore_rientro_f_stato);

		}

		if (id_asl_nuovo_proprietario_rientro_fuori_stato > 0) {
			pst.setInt(++i, id_asl_nuovo_proprietario_rientro_fuori_stato);
		}
		if (id_continente_rientro_da > -1) {
			pst.setInt(++i, id_continente_rientro_da);
		}

		if (luogo_rientro_f_stato != null
				&& !("").equals(luogo_rientro_f_stato)) {
			pst.setString(++i, luogo_rientro_f_stato);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	// fine fuori stato
	protected StringBuffer createFilterCambioDetentore(Connection db) {
		StringBuffer detentoreStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_cambio_detentore != null) {
			detentoreStringBuffer
					.append("AND detentore.data_cambio_detentore = ? ");
		}

		if (id_detentore_cambio > -1) {
			detentoreStringBuffer.append("and detentore.id_detentore = ?");

		}

		if (id_vecchio_detentore_cambio > -1) {
			detentoreStringBuffer
					.append("and detentore.id_vecchio_detentore = ?");
		}

		if (eventoda != null && eventoa != null) {
			detentoreStringBuffer
					.append("AND detentore.data_cambio_detentore >= ? and detentore.data_cambio_detentore <= ?");
		}

		return detentoreStringBuffer;
	}

	protected int prepareFilterCambioDetentore(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_cambio_detentore != null) {
			pst.setTimestamp(++i, data_cambio_detentore);
		}

		if (id_detentore_cambio > -1) {
			pst.setInt(++i, id_detentore_cambio);

		}

		if (id_vecchio_detentore_cambio > -1) {
			pst.setInt(++i, id_vecchio_detentore_cambio);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterRestituzioneAProprietario(Connection db) {
		StringBuffer restituzioneAProprietarioStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_restituzione != null) {
			restituzioneAProprietarioStringBuffer
					.append("AND restituzione.data_restituzione = ? ");
		}


		if (eventoda != null && eventoa != null) {
			restituzioneAProprietarioStringBuffer
					.append("AND restituzione.data_restituzione >= ? and restituzione.data_restituzione <= ? ");
		}

		return restituzioneAProprietarioStringBuffer;
	}

	protected int prepareFilterRestituzioneAProprietario(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_restituzione!= null) {
			pst.setTimestamp(++i, data_restituzione);
		}


		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterTrasferimento(Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento.data_trasferimento = ? ");
		}

		if (id_nuovo_proprietario_trasferimento > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento.id_nuovo_proprietario = ?");

		}

		if (id_vecchio_proprietario_trasferimento > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento.id_vecchio_proprietario = ?");

		}

		if (id_comune_nuovo_proprietario_trasferimento > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento.id_comune_proprietario = ?");
		}
		if (id_nuovo_detentore_trasferimento > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento.id_nuovo_detentore = ?");
		}
		if (id_vecchio_detentore_trasferimento > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento.id_vecchio_detentore = ?");
		}

		if (luogo_trasferimento != null && !("").equals(luogo_trasferimento)) {
			trasferimentoStringBuffer.append("and trasferimento.luogo = ?");
		}
		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento.data_trasferimento >= ? and trasferimento.data_trasferimento <= ? ");
		}

		return trasferimentoStringBuffer;
	}

	protected StringBuffer createFilterTrasferimentoCanile(Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento_canile != null) {
			trasferimentoStringBuffer
					.append("AND trasferimentoC.data_trasferimento_canile = ? ");
		}

		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento.data_trasferimento >= ? and trasferimento.data_trasferimento <= ? ");
		}

		return trasferimentoStringBuffer;
	}
	
	protected StringBuffer createFilterTrasferimentoSindaco(Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento_canile != null) {
			trasferimentoStringBuffer
					.append("AND trasferimentoSin.data_trasferimento = ? ");
		}

		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimentoSin.data_trasferimento >= ? and trasferimentoSin.data_trasferimento <= ? ");
		}

		return trasferimentoStringBuffer;
	}

	protected int prepareFilterTrasferimentoCanile(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento_canile != null) {
			pst.setTimestamp(++i, data_trasferimento_canile);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	
	protected int prepareFilterTrasferimentoSindaco(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento_canile != null) {
			pst.setTimestamp(++i, data_trasferimento_canile);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	protected int prepareFilterTrasferimento(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento != null) {
			pst.setTimestamp(++i, data_trasferimento);
		}

		if (id_nuovo_proprietario_trasferimento > -1) {
			pst.setInt(++i, id_nuovo_proprietario_trasferimento);

		}

		if (id_vecchio_proprietario_trasferimento > -1) {
			pst.setInt(++i, id_vecchio_proprietario_trasferimento);

		}

		if (id_comune_nuovo_proprietario_trasferimento > -1) {
			pst.setInt(++i, id_comune_nuovo_proprietario_trasferimento);
		}
		if (id_nuovo_detentore_trasferimento > -1) {
			pst.setInt(++i, id_nuovo_detentore_trasferimento);
		}
		if (id_vecchio_detentore_trasferimento > -1) {
			pst.setInt(++i, id_vecchio_detentore_trasferimento);
		}

		if (luogo_trasferimento != null && !("").equals(luogo_trasferimento)) {
			pst.setString(++i, luogo_trasferimento);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterSmarrimento(Connection db) {
		StringBuffer smarrimentoStringBuffer = new StringBuffer();

		if (eventoda != null && eventoa != null) {
			smarrimentoStringBuffer
					.append("AND smarrimento.data_smarrimento >= ? and smarrimento.data_smarrimento <= ? ");
		}

		return smarrimentoStringBuffer;
	}

	protected int prepareFilterSmarrimento(PreparedStatement pst, int i)
			throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterTrasferimentoFuoriRegione(Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento_fuori_regione != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_fr.data_trasferimento_fuori_regione = ? ");
		}

		if (id_vecchio_proprietario_trasf_fuori_regione > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento_fr.id_vecchio_proprietario = ?");

		}

		if (id_vecchio_detentore > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento_fr.id_vecchio_detentore = ?");

		}

		if (id_asl_vecchio_proprietario_trasf_fuori_regione > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento_fr.id_asl_vecchio_proprietario = ?");
		}
		if (id_regione_trasferimento_a > -1) {
			trasferimentoStringBuffer
					.append("and trasferimento_fr.id_regione_a = ?");
		}

		if (luogo_trasf_fuori_regione != null
				&& !("").equals(luogo_trasf_fuori_regione)) {
			trasferimentoStringBuffer
					.append("and trasferimento_fr.luogo_trasf_fuori_regione = ?");
		}
		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_fr.data_trasferimento_fuori_regione >= ? and trasferimento_fr.data_trasferimento_fuori_regione <= ? ");
		}

		return trasferimentoStringBuffer;
	}

	protected int prepareFilterTrasferimentoFuoriRegione(PreparedStatement pst,
			int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento_fuori_regione != null) {
			pst.setTimestamp(++i, data_trasferimento_fuori_regione);
		}

		if (id_vecchio_proprietario_trasf_fuori_regione > -1) {
			pst.setInt(++i, id_vecchio_proprietario_trasf_fuori_regione);

		}

		if (id_vecchio_detentore > -1) {
			pst.setInt(++i, id_vecchio_detentore);

		}

		if (id_asl_vecchio_proprietario_trasf_fuori_regione > -1) {
			pst.setInt(++i, id_asl_vecchio_proprietario_trasf_fuori_regione);
		}
		if (id_regione_trasferimento_a > -1) {
			pst.setInt(++i, id_regione_trasferimento_a);
		}

		if (luogo_trasf_fuori_regione != null
				&& !("").equals(luogo_trasf_fuori_regione)) {
			pst.setString(++i, luogo_trasf_fuori_regione);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterTrasferimentoFuoriStato(Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento_fuori_regione != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_fs.data_trasferimento_fuori_stato = ? ");
		}

		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_fs.data_trasferimento_fuori_stato >= ? and trasferimento_fs.data_trasferimento_fuori_stato <= ? ");
		}

		return trasferimentoStringBuffer;
	}

	protected int prepareFilterTrasferimentoFuoriStato(PreparedStatement pst,
			int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento_fuori_regione != null) {
			pst.setTimestamp(++i, data_trasferimento_fuori_regione);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterTrasferimentoFuoriRegioneSoloProp(
			Connection db) {
		StringBuffer trasferimentoStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (data_trasferimento_fuori_regione_solo_proprietario != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_frp.data_trasferimento_fuori_regione_solo_proprietario = ? ");
		}

		if (eventoda != null && eventoa != null) {
			trasferimentoStringBuffer
					.append("AND trasferimento_frp.data_trasferimento_fuori_regione_solo_proprietario >= ? and trasferimento_frp.data_trasferimento_fuori_regione_solo_proprietario <= ? ");
		}

		return trasferimentoStringBuffer;
	}

	protected int prepareFilterTrasferimentoFuoriRegioneSoloProp(
			PreparedStatement pst, int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (data_trasferimento_fuori_regione_solo_proprietario != null) {
			pst.setTimestamp(++i,
					data_trasferimento_fuori_regione_solo_proprietario);
		}

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}

	protected StringBuffer createFilterModificaResidenza(
			Connection db) {
		StringBuffer residenzaStringBuffer = new StringBuffer();

		// createFilterEvento(db, catturaStringBuffer);

		if (eventoda != null && eventoa != null) {
			residenzaStringBuffer
					.append("AND residenza.data_modifica_residenza >= ? and residenza.data_modifica_residenza <= ? ");
		}

		return residenzaStringBuffer;
	}

	protected int prepareFilterModificaResidenza(
			PreparedStatement pst, int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	protected int prepareFilterPrelievoDNA(
			PreparedStatement pst, int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	
	protected int prepareFilterCessioneImport(
			PreparedStatement pst, int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	protected int prepareFilterPresaCessioneImport(
			PreparedStatement pst, int i) throws SQLException {

		// i = prepareFilterEvento(pst, i);

		if (eventoda != null && eventoa != null) {
			pst.setTimestamp(++i, eventoda);
			pst.setTimestamp(++i, eventoa);
		}

		return i;
	}
	
	
	
	
	protected int prepareFilterEvento(PreparedStatement pst, int i)
			throws SQLException {
		// int i = 0;

		if (id > -1) {
			pst.setInt(++i, id);
		}
		if (id_animale != -1) {
			pst.setInt(++i, id_animale);
		}

		if (idSpecieAnimale > -1) {
			pst.setInt(++i, idSpecieAnimale);
		}

		 if (idTipologiaEventoString == null && idTipologiaEvento != -1) {
			pst.setInt(++i, idTipologiaEvento);
		}
		else 

		if (entered != null) {
			pst.setTimestamp(++i, entered);
		}

		if (modified != null) {
			pst.setTimestamp(++i, modified);
		}

		if (id_utente_inserimento != -1) {
			pst.setInt(++i, id_utente_inserimento);
		}

		if (id_utente_modifica != -1) {
			pst.setInt(++i, id_utente_modifica);
		}
		
		if (id_proprietario_corrente > 0){
			pst.setInt(++i, id_proprietario_corrente);
		}
		
		if (id_detentore_corrente > 0){
			pst.setInt(++i, id_detentore_corrente);
		}

		if (BDUa != null && BDUda != null) {
			pst.setTimestamp(++i, BDUda);
			pst.setTimestamp(++i, BDUa);
		}
		
		if (idAslInserimentoEventoBDU > 0){
			pst.setInt(++i, idAslInserimentoEventoBDU);
		}

		return i;
	}

	protected int prepareFilterAnimale(PreparedStatement pst, int i) 
			throws SQLException {
		// int i = 0;

		return i;
	}

	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		switch (idTipologiaEvento) {
		case -1:
			i = prepareFilterRegistrazione(pst, i); 
			i = prepareFilterSterilizzazione(pst, i);

			break;
		case 1:
			i = prepareFilterRegistrazione(pst, i);
			break;
		case 2:
			i = prepareFilterSterilizzazione(pst, i);
			break;
		default:
			break;
		}

		return i;
	}

	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;
		
		if(id_animale>0)
			getTipologieRegistrazioniAnimale(db,id_animale); 
		ResultSet rs = queryList(db, pst);
		while (rs.next()) {				
			
			
			try 
			{
				//Se la registrazione è "Trasferimento clinica extra asl VAM" o "Riconsegna clinica extra asl VAM"
				//Queste non devono comparire nella lista registrazioni poichè sono fittizie e servono solo per modificare l'asl in carico dell'animale ma l'utente non deve vederle
				if(rs.getInt("id_tipologia_evento")<62 || rs.getInt("id_tipologia_evento")>63)
				{
					Evento thisEvento = (Evento) Class.forName("org.aspcfs.modules.registrazioniAnimali.base."+ApplicationProperties.getProperty(String.valueOf(rs.getInt("id_tipologia_evento")))).newInstance();
					thisEvento = thisEvento.build(rs);
					this.add(thisEvento);
				}
			} 
			catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			
//			switch (rs.getInt("id_tipologia_evento")) {
//			
//
//			case EventoRegistrazioneBDU.idTipologiaDB:
//
//			{
//				EventoRegistrazioneBDU evento = new EventoRegistrazioneBDU(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoInserimentoMicrochip.idTipologiaDB:
//
//			{
//				EventoInserimentoMicrochip evento = new EventoInserimentoMicrochip(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip:
//
//			{
//				EventoInserimentoMicrochip evento = new EventoInserimentoMicrochip(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoSterilizzazione.idTipologiaDB:
//
//			{
//				EventoSterilizzazione evento = new EventoSterilizzazione(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoFurto.idTipologiaDB:
//
//			{
//				EventoFurto evento = new EventoFurto(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoDecesso.idTipologiaDB:
//
//			{
//				EventoDecesso evento = new EventoDecesso(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoRitrovamento.idTipologiaDB:
//
//			{
//				EventoRitrovamento evento = new EventoRitrovamento(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoRitrovamentoNonDenunciato.idTipologiaDB:
//
//			{
//				EventoRitrovamentoNonDenunciato evento = new EventoRitrovamentoNonDenunciato(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoSmarrimento.idTipologiaDB: {
//				EventoSmarrimento evento = new EventoSmarrimento(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoCattura.idTipologiaDB:
//
//			{
//				EventoCattura evento = new EventoCattura(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoCattura.idTipologiaDBRicattura:
//
//			{
//				EventoCattura evento = new EventoCattura(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoRilascioPassaporto.idTipologiaDB: {
//				EventoRilascioPassaporto evento = new EventoRilascioPassaporto(
//						rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoRilascioPassaporto.idTipologiaRinnovoDB: {
//				EventoRilascioPassaporto evento = new EventoRilascioPassaporto(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoAdozioneDaCanile.idTipologiaDB: {
//				EventoAdozioneDaCanile evento = new EventoAdozioneDaCanile(rs);
//				this.add(evento);
//				break;
//
//			}
//
//			case EventoRestituzioneACanile.idTipologiaDB: {
//				EventoRestituzioneACanile evento = new EventoRestituzioneACanile(
//						rs);
//				this.add(evento);
//				break;
//
//			}
//
//			case EventoAdozioneDaColonia.idTipologiaDB: {
//				EventoAdozioneDaColonia evento = new EventoAdozioneDaColonia(rs);
//				this.add(evento);
//				break;
//
//			}
//
//			case EventoCessione.idTipologiaDB: {
//				EventoCessione evento = new EventoCessione(rs, db);
//				this.add(evento);
//				break;
//
//			}
//			case EventoPresaInCaricoDaCessione.idTipologiaDB: {
//				EventoPresaInCaricoDaCessione evento = new EventoPresaInCaricoDaCessione(
//						rs);
//				this.add(evento);
//				break;
//
//			}
//			
//			case EventoAdozioneFuoriAsl.idTipologiaDB: {
//				EventoAdozioneFuoriAsl evento = new EventoAdozioneFuoriAsl(rs);
//				this.add(evento);
//				break;
//
//			}
//			case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
//				EventoPresaInCaricoDaAdozioneFuoriAsl evento = new EventoPresaInCaricoDaAdozioneFuoriAsl(
//						rs);
//				this.add(evento);
//				break;
//
//			}
//			
//			
//			case EventoTrasferimento.idTipologiaDB: {
//				EventoTrasferimento evento = new EventoTrasferimento(rs);
//				this.add(evento);
//				break;
//			}
//			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
//				EventoTrasferimentoFuoriRegione evento = new EventoTrasferimentoFuoriRegione(
//						rs);
//				this.add(evento);
//				break;
//			}
//			case EventoRientroFuoriRegione.idTipologiaDB: {
//				EventoRientroFuoriRegione evento = new EventoRientroFuoriRegione(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoTrasferimentoFuoriStato.idTipologiaDB: {
//				EventoTrasferimentoFuoriStato evento = new EventoTrasferimentoFuoriStato(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB: {
//				EventoTrasferimentoFuoriRegioneSoloProprietario evento = new EventoTrasferimentoFuoriRegioneSoloProprietario(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoCambioDetentore.idTipologiaDB: {
//				EventoCambioDetentore evento = new EventoCambioDetentore(rs);
//				this.add(evento);
//				break;
//			}
//			case EventoRestituzioneAProprietario.idTipologiaDB: {
//				EventoRestituzioneAProprietario evento = new EventoRestituzioneAProprietario(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoRestituzioneAProprietario.idTipologiaRestituzioneACanileDB: {
//				EventoRestituzioneAProprietario evento = new EventoRestituzioneAProprietario(rs);
//				this.add(evento);
//				break;
//			}
//			
//			
//			case EventoMorsicatura.idTipologiaDB: {
//				EventoMorsicatura evento = new EventoMorsicatura(rs);
//				this.add(evento);
//				break;
//			}
//			case EventoEsitoControlli.idTipologiaDB: {
//				EventoEsitoControlli evento = new EventoEsitoControlli(rs);
//				this.add(evento);
//				break;
//			}
//			case EventoReimmissione.idTipologiaDB: {
//				EventoReimmissione evento = new EventoReimmissione(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB: {
//				EventoRegistrazioneEsitoControlliCommerciali evento = new EventoRegistrazioneEsitoControlliCommerciali(
//						rs);
//				this.add(evento);
//				break;
//			}
//			case EventoTrasferimentoCanile.idTipologiaDB: {
//				EventoTrasferimentoCanile evento = new EventoTrasferimentoCanile(
//						rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoAdozioneDistanza.idTipologiaDB: {
//				EventoAdozioneDistanza evento = new EventoAdozioneDistanza(rs);
//				this.add(evento);
//				break;
//			}
//
//			case EventoInserimentoVaccinazioni.idTipologiaDB: {
//				EventoInserimentoVaccinazioni evento = new EventoInserimentoVaccinazioni(
//						rs);
//				this.add(evento);
//				break;
//			}
//			case EventoRientroFuoriStato.idTipologiaDB:
//
//			{
//				EventoRientroFuoriStato evento = new EventoRientroFuoriStato(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoModificaResidenza.idTipologiaDB:
//
//			{
//				EventoModificaResidenza evento = new EventoModificaResidenza(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoPrelievoDNA.idTipologiaDB:
//
//			{
//				EventoPrelievoDNA evento = new EventoPrelievoDNA(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoCessioneImport.idTipologiaDB:
//
//			{
//				EventoCessioneImport evento = new EventoCessioneImport(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoPresaCessioneImport.idTipologiaDB:
//
//			{
//				EventoPresaCessioneImport evento = new EventoPresaCessioneImport(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoPrelievoLeishmania.idTipologiaDB:
//
//			{
//				EventoPrelievoLeishmania evento = new EventoPrelievoLeishmania(rs);
//				this.add(evento);
//				break;
//			}
//			
//			case EventoRestituzioneAslOrigine.idTipologiaDB:
//
//			{
//				EventoRestituzioneAslOrigine evento = new EventoRestituzioneAslOrigine(rs);
//				this.add(evento);
//				break;
//			}
//			
//			}

		}
		rs.close();
		if (pst != null) {
			pst.close();
		}

	}

	public ResultSet queryList(Connection db, PreparedStatement pst)
			throws SQLException {
		
		
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		StringBuffer sqlFileterRegistrazione = new StringBuffer();
		StringBuffer sqlFileterSterilizzazione = new StringBuffer();
		StringBuffer sqlFilterFurto = new StringBuffer();
		StringBuffer sqlFilterMicrochip = new StringBuffer();
		StringBuffer sqlFilterCattura = new StringBuffer();
		StringBuffer sqlFilterRitrovamento = new StringBuffer();
		StringBuffer sqlFilterRitrovamentoNonDenunciato = new StringBuffer();
		StringBuffer sqlFilterAdozioneCanile = new StringBuffer();
		StringBuffer sqlFilterAdozioneColonia = new StringBuffer();
		StringBuffer sqlFilterAdozioneVersoAssociazioni = new StringBuffer();
		StringBuffer sqlFilterRestituzioneCanile = new StringBuffer();
		StringBuffer sqlFilterRilascioPassaporto = new StringBuffer();
		StringBuffer sqlFilterCessione = new StringBuffer();
		StringBuffer sqlFilterPresaCessione = new StringBuffer();
		StringBuffer sqlFilterTrasferimento = new StringBuffer();
		StringBuffer sqlFilterAdozioneFuoriAsl = new StringBuffer();
		StringBuffer sqlFilterPresaAdozioneFuoriAsl = new StringBuffer();
		StringBuffer sqlFilterTrasferimentoFuoriRegione = new StringBuffer();
		StringBuffer sqlFilterRientroFuoriRegione = new StringBuffer();
		StringBuffer sqlFilterDetentore = new StringBuffer();
		StringBuffer sqlFilterRestituzioneAProprietario = new StringBuffer();
		StringBuffer sqlFilterMorsicatura = new StringBuffer();
		StringBuffer sqlFilterAggressione = new StringBuffer();
		StringBuffer sqlFilterControlli = new StringBuffer();
		StringBuffer sqlFilterReimmissione = new StringBuffer();
		StringBuffer sqlFilterDecesso = new StringBuffer();
		StringBuffer sqlFilterInserimentoEsitoCommerciale = new StringBuffer();
		StringBuffer sqlFilterSmarrimento = new StringBuffer();
		StringBuffer sqlFilterTrasferimentoCanile = new StringBuffer();
		StringBuffer sqlFilterTrasferimentoSindaco = new StringBuffer();
		StringBuffer sqlFilterAdozioneDistanza = new StringBuffer();
		StringBuffer sqlFilterVaccino = new StringBuffer();
		StringBuffer sqlFilterTrasferimentoFuoriStato = new StringBuffer();
		StringBuffer sqlFilterTrasferimentoFuoriRegioneSoloProp = new StringBuffer();
		StringBuffer sqlFilterRientroFuoriStato = new StringBuffer();
		StringBuffer sqlFilterVaccinazioni = new StringBuffer();
		StringBuffer sqlFilterRestituzioneAColonia = new StringBuffer();
		StringBuffer sqlFilterModificaResidenza = new StringBuffer();
		StringBuffer sqlFilterPrelievoDNA = new StringBuffer();
		StringBuffer sqlFilterCessioneImport = new StringBuffer();
		StringBuffer sqlFilterPresaInCaricoCessioneImport = new StringBuffer();
		StringBuffer sqlFilterPrelievoLeish = new StringBuffer();
		StringBuffer sqlFilterRestituzioneAslOrigine = new StringBuffer();
		StringBuffer sqlFilterBloccoAnimale = new StringBuffer();
		StringBuffer sqlFilterSbloccoAnimale = new StringBuffer();
		StringBuffer sqlFilterMutilazione = new StringBuffer();
		StringBuffer sqlFilterAllontanamento = new StringBuffer();

		// Need to build a base SQL statement for counting records

		switch (idTipologiaEvento) {
		case -1: {// Tutti gli eventi:

			sqlCount.append("SELECT COUNT(*) AS recordcount "
					+ "FROM evento e "

			// + "WHERE e.id >= 0 "
					);

			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRegistrazioneBDU.idTipologiaDB))
			{
				sqlCount.append("left join evento_registrazione_bdu reg on (e.id_evento = reg.id_evento ");
				sqlFileterRegistrazione = createFilterRegistrazione(db);
				sqlCount.append(sqlFileterRegistrazione);
				sqlCount.append(")");
			}
				
			// Sterilizzazione
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSterilizzazione.idTipologiaDB))
			{
			sqlCount
					.append(" left join evento_sterilizzazione ster on (e.id_evento = ster.id_evento ");
			sqlFileterSterilizzazione = createFilterSterilizzazione(db);
			sqlCount.append(sqlFileterSterilizzazione);
			sqlCount.append(")");
			}
			
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoDecesso.idTipologiaDB))
			{
			// Decesso
			sqlCount
					.append(" left join evento_decesso decess on (e.id_evento = decess.id_evento ");
			sqlFilterDecesso = createFilterDecesso(db);
			sqlCount.append(sqlFilterDecesso);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoFurto.idTipologiaDB))
			{
			// Furto
			sqlCount
					.append(" left join evento_furto furto on (e.id_evento = furto.id_evento  ");
			sqlFilterFurto = createFilterFurto(db);
			sqlCount.append(sqlFilterFurto);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip) || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDB))
			{
			// Inserimento microchip
			sqlCount
					.append(" left join evento_inserimento_microchip microchip on (e.id_evento = microchip.id_evento  ");
			sqlFilterMicrochip = createFilterMicrochip(db);
			sqlCount.append(sqlFilterMicrochip);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCattura.idTipologiaDB))
			{
			// Inserimento cattura
			sqlCount
					.append(" left join evento_cattura cattura on (e.id_evento = cattura.id_evento  ");
			sqlFilterCattura = createFilterCattura(db);
			sqlCount.append(sqlFilterCattura);
			sqlCount.append(") ");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
			{
			// ritrovamento
			sqlCount
					.append("left join evento_ritrovamento rit on (e.id_evento = rit.id_evento ");
			sqlFilterRitrovamento = createFilterRitrovamento(db);
			sqlCount.append(sqlFilterRitrovamento);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamentoNonDenunciato.idTipologiaDB))
			{
			// ritrovamento non denunciato
			sqlCount
					.append("left join evento_ritrovamento_non_denunciato rit_nd on (e.id_evento = rit_nd.id_evento ");
			sqlFilterRitrovamentoNonDenunciato = createFilterRitrovamentoNonDenunciato(db);
			sqlCount.append(sqlFilterRitrovamentoNonDenunciato);
			sqlCount.append(")");

			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaDB))
			{
			// adozioneDaCanile

			sqlCount
					.append("left join evento_adozione_da_canile ado on (e.id_evento = ado.id_evento ");
			sqlFilterAdozioneCanile = createFilterAdozione(db);
			sqlCount.append(sqlFilterAdozioneCanile);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB))
			{
			// adozioneVersoAssociazioni

			sqlCount.append("left join evento_adozione_da_canile ado_associazioni on (e.id_evento = ado_associazioni.id_evento ");
			sqlFilterAdozioneVersoAssociazioni = createFilterAdozioneAssociazioni(db);
			sqlCount.append(sqlFilterAdozioneCanile);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneACanile.idTipologiaDB))
			{
			// restituzione a canile
			sqlCount
					.append("left join evento_restituzione_a_canile rest on (e.id_evento = rest.id_evento ");
			sqlFilterRestituzioneCanile = createFilterRestituzione(db);
			sqlCount.append(sqlFilterRestituzioneCanile);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaColonia.idTipologiaDB))
			{
			sqlCount
					.append("left join evento_adozione_da_colonia adoColonia on (e.id_evento = adoColonia.id_evento ");
			sqlFilterAdozioneColonia = createFilterAdozioneColonia(db);
			sqlCount.append(sqlFilterAdozioneColonia);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaDB) || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaRinnovoDB))
			{
			// rilascio passaporto
			sqlCount
					.append("left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento ");
			sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			sqlCount.append(sqlFilterRilascioPassaporto);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessione.idTipologiaDB) || tipologieToSet.contains(EventoCessione.idTipologiaDB_obsoleto))
			{
			// cessione
			sqlCount
					.append("left join evento_cessione cessione on (e.id_evento = cessione.id_evento ");
			sqlFilterCessione = createFilterCessione(db);
			sqlCount.append(sqlFilterCessione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaCessione.idTipologiaDB))
			{
			// presa in carico da cessione
			sqlCount
					.append("left join evento_presa_in_carico_cessione presa_cessione on (e.id_evento = presa_cessione.id_evento ");
			sqlFilterPresaCessione = createFilterPresaCessione(db);
			sqlCount.append(sqlFilterPresaCessione);
			sqlCount.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB) || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto))
			{
			// adozione fuori asl
			sqlCount
					.append("left join evento_adozione_fuori_asl adozione_fa on (e.id_evento = adozione_fa.id_evento ");
			sqlFilterCessione = createFilterAdozioneFuoriAsl(db);
			sqlCount.append(sqlFilterCessione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB))
			{
			// presa in carico da adozione
			sqlCount
					.append("left join evento_presa_in_carico_adozione_fuori_asl presa_adozione on (e.id_evento = presa_adozione.id_evento ");
			sqlFilterPresaCessione = createFilterPresaAdozioneFuoriAsl(db);
			sqlCount.append(sqlFilterPresaAdozioneFuoriAsl);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimento.idTipologiaDB) || tipologieToSet.contains(EventoTrasferimento.idTipologiaDBOperatoreCommerciale))
			{
			// trasferimento

			sqlCount
					.append("left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento ");
			sqlFilterTrasferimento = createFilterTrasferimento(db);
			sqlCount.append(sqlFilterTrasferimento);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoCanile.idTipologiaDB))
			{
			// trasferimento canile

			sqlCount
					.append("left join evento_trasferimento_canile trasferimentoC on (e.id_evento = trasferimentoC.id_evento ");
			sqlFilterTrasferimentoCanile = createFilterTrasferimentoCanile(db);
			sqlCount.append(sqlFilterTrasferimentoCanile);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoSindaco.idTipologiaDB))
			{
			// trasferimento canile

			sqlCount
					.append("left join evento_trasferimento_sindaco trasferimentoSin on (e.id_evento = trasferimentoSin.id_evento ");
			sqlFilterTrasferimentoSindaco = createFilterTrasferimentoSindaco(db);
			sqlCount.append(sqlFilterTrasferimentoSindaco);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
			{
			// trasferimento fuori regione
			sqlCount
					.append("left join evento_trasferimento_fuori_regione trasferimento_fr on (e.id_evento = trasferimento_fr.id_evento ");
			sqlFilterTrasferimentoFuoriRegione = createFilterTrasferimentoFuoriRegione(db);
			sqlCount.append(sqlFilterTrasferimentoFuoriRegione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriStato.idTipologiaDB))
			{
			// Trasferimento fuori stato
			sqlCount
					.append("left join evento_trasferimento_fuori_stato trasferimento_fs on (e.id_evento = trasferimento_fs.id_evento ");
			sqlFilterTrasferimentoFuoriStato = createFilterTrasferimentoFuoriStato(db);
			sqlCount.append(sqlFilterTrasferimentoFuoriStato);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
			{
			// Trasferimento fuori regione solo prop
			sqlCount
					.append("left join evento_trasferimento_fuori_regione_solo_proprietario trasferimento_frp on (e.id_evento = trasferimento_frp.id_evento ");
			sqlFilterTrasferimentoFuoriRegioneSoloProp = createFilterTrasferimentoFuoriRegioneSoloProp(db);
			sqlCount.append(sqlFilterTrasferimentoFuoriRegioneSoloProp);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDB) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBRandagio) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale))
			{
			// rientro da fuori regione
			sqlCount
					.append("left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento ");
			sqlFilterRientroFuoriRegione = createFilterRientroFuoriRegione(db);
			sqlCount.append(sqlFilterRientroFuoriRegione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriStato.idTipologiaDB))
			{
			// rientro da fuori stato
			sqlCount
					.append("left join evento_rientro_da_fuori_stato rientroFS on (e.id_evento = rientroFS.id_evento ");
			sqlFilterRientroFuoriStato = createFilterRientroFuoriStato(db);
			sqlCount.append(sqlFilterRientroFuoriStato);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCambioDetentore.idTipologiaDB))
			{
			// cambio detentore
			sqlCount
					.append("left join evento_cambio_detentore detentore on (e.id_evento = detentore.id_evento ");
			sqlFilterDetentore = createFilterCambioDetentore(db);
			sqlCount.append(sqlFilterDetentore);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneAProprietario.idTipologiaDB))
			{
			// restituzione a proprietario
			sqlCount
					.append("left join evento_restituzione_a_proprietario restituzione on (e.id_evento = restituzione.id_evento ");
			sqlFilterRestituzioneAProprietario = createFilterRestituzioneAProprietario(db);
			sqlCount.append(sqlFilterRestituzioneAProprietario);
			sqlCount.append(")");

			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMorsicatura.idTipologiaDB))
			{
			// morsicatura
			sqlCount
					.append("left join evento_morsicatura morsicatura on (e.id_evento = morsicatura.id_evento ");
			sqlFilterMorsicatura = createFilterMorsicatura(db);
			sqlCount.append(sqlFilterMorsicatura);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAggressione.idTipologiaDB))
			{
			sqlCount
					.append("left join evento_aggressione aggr on (e.id_evento = aggr.id_evento ");
			sqlFilterAggressione = createFilterAggressione(db);
			sqlCount.append(sqlFilterAggressione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoEsitoControlli.idTipologiaDB))
			{
			// controlli
			sqlCount
					.append("left join evento_esito_controlli controlli on (e.id_evento = controlli.id_evento ");
			sqlFilterControlli = createFilterEsitoControlli(db);
			sqlCount.append(sqlFilterControlli);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoReimmissione.idTipologiaDB))
			{
			// reimmissione
			sqlCount
					.append("left join evento_reimmissione reimmissione on (e.id_evento = reimmissione.id_evento ");
			sqlFilterReimmissione = createFilterReimmissione(db);
			sqlCount.append(sqlFilterReimmissione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(26))
			{
			// Esito controlli commerciali
			sqlCount
					.append("left join evento_inserimento_esiti_controlli_commerciali commerciali on (e.id_evento = commerciali.id_evento ");
			sqlFilterInserimentoEsitoCommerciale = createFilterCommerciali(db);
			sqlCount.append(sqlFilterInserimentoEsitoCommerciale);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSmarrimento.idTipologiaDB))
			{
			sqlCount
					.append("left join evento_smarrimento smarrimento on (e.id_evento = smarrimento.id_evento ");
			sqlFilterSmarrimento = createFilterSmarrimento(db);
			sqlCount.append(sqlFilterSmarrimento);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDistanza.idTipologiaDB))
			{
			sqlCount
					.append("left join evento_adozione_distanza adoD on (e.id_evento = adoD.id_evento ");
			sqlFilterAdozioneDistanza = createFilterAdozioneDistanza(db);
			sqlCount.append(sqlFilterAdozioneDistanza);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoVaccinazioni.idTipologiaDB))
			{
			sqlCount
					.append("left join evento_inserimento_vaccino vaccino on (e.id_evento = vaccino.id_evento ");
			sqlFilterVaccino = createFilterVaccino(db);
			sqlCount.append(sqlFilterVaccino);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoModificaResidenza.idTipologiaDB))
			{
			sqlCount
				.append("left join evento_modifica_residenza residenza on (e.id_evento = residenza.id_evento ");
			sqlFilterVaccino = createFilterVaccino(db);
			sqlCount.append(sqlFilterVaccino);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoDNA.idTipologiaDB))
			{
			sqlCount
			.append("left join evento_prelievo_dna prelievo_dna on (e.id_evento = prelievo_dna.id_evento ");
			sqlFilterPrelievoDNA = createFilterPrelievoDNA(db);
			sqlCount.append(sqlFilterPrelievoDNA);
			sqlCount.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessioneImport.idTipologiaDB))
			{
			sqlCount
			.append("left join evento_cessione_import cessione_import on (e.id_evento = cessione_import.id_evento ");
			sqlFilterCessioneImport = createFilterCessioneImport(db);
			sqlCount.append(sqlFilterCessioneImport);
			sqlCount.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaCessioneImport.idTipologiaDB))
			{
			sqlCount
			.append("left join evento_presa_in_carico_cessione_import presa_cessione_import on (e.id_evento = presa_cessione_import.id_evento ");
			sqlFilterPresaInCaricoCessioneImport = createFilterPresaCessioneImport(db);
			sqlCount.append(sqlFilterPresaInCaricoCessioneImport);
			sqlCount.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoLeishmania.idTipologiaDB))
			{
			sqlCount
			.append("left join evento_prelievo_leish prelievo_leish on (e.id_evento = prelievo_leish.id_evento ");
			sqlFilterPrelievoLeish = createFilterPrelievoLeish(db);
			sqlCount.append(sqlFilterPrelievoLeish);
			sqlCount.append(")");
			}
			
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoBloccoAnimale.idTipologiaDB))
			{
			//Evento blocco animale
			
			sqlCount
			.append("left join evento_blocco_animale blocco on (e.id_evento = blocco.id_evento ");
			sqlFilterBloccoAnimale = createFilterBloccoAnimale(db);
			sqlCount.append(sqlFilterBloccoAnimale);
			sqlCount.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSbloccoAnimale.idTipologiaDB))
			{
			//Evento sblocco animale
			
			sqlCount
			.append("left join evento_sblocco_animale sblocco on (e.id_evento = sblocco.id_evento ");
			sqlFilterSbloccoAnimale = createFilterSbloccoAnimale(db);
			sqlCount.append(sqlFilterSbloccoAnimale);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMutilazione.idTipologiaDB))
			{
			//Evento mutilazione
			
			sqlCount
			.append("left join evento_mutilazione mutilazione on (e.id_evento = mutilazione.id_evento ");
			sqlFilterMutilazione = createFilterMutilazione(db);
			sqlCount.append(sqlFilterMutilazione);
			sqlCount.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAllontanamento.idTipologiaDB))
			{
			//Evento allontanamento
			
			sqlCount
			.append("left join evento_allontanamento allontanamento on (e.id_evento = allontanamento.id_evento ");
			sqlFilterAllontanamento = createFilterAllontanamento(db);
			sqlCount.append(sqlFilterAllontanamento);
			sqlCount.append(")");
			}
			// sqlCount.append(" where 1 = 1");

			break;

		}

		case EventoRegistrazioneBDU.idTipologiaDB: { // registrazione
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_registrazione_bdu reg on (e.id_evento = reg.id_evento)  ");
			sqlFileterRegistrazione = createFilterRegistrazione(db);
			break;
		}

		case EventoSterilizzazione.idTipologiaDB: { // Sterilizzazione
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_sterilizzazione ster on (e.id_evento = ster.id_evento) ");
			sqlFileterSterilizzazione = createFilterSterilizzazione(db);
			break;
		}

		case EventoDecesso.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_decesso decess on (e.id_evento = decess.id_evento) ");
			sqlFilterDecesso = createFilterDecesso(db);
			break;
		}
		case EventoFurto.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_furto furto on (e.id_evento = furto.id_evento)  ");
			sqlFilterFurto = createFilterFurto(db);
			break;
		}

		case EventoInserimentoMicrochip.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_inserimento_microchip microchip on (e.id_evento = microchip.id_evento)  ");
			sqlFilterMicrochip = createFilterMicrochip(db);
			break;
		}

		case EventoCattura.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cattura cattura on (e.id_evento = cattura.id_evento)   ");
			sqlFilterCattura = createFilterCattura(db);
			break;
		}

		case EventoCattura.idTipologiaDBRicattura: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cattura cattura on (e.id_evento = cattura.id_evento)   ");
			sqlFilterCattura = createFilterCattura(db);
			break;
		}

		case EventoRitrovamento.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_ritrovamento rit on (e.id_evento = rit.id_evento)  ");
			sqlFilterRitrovamento = createFilterRitrovamento(db);
			break;
		}
		case EventoRitrovamentoNonDenunciato.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_ritrovamento_non_denunciato rit_nd on (e.id_evento = rit_nd.id_evento)  ");
			sqlFilterRitrovamentoNonDenunciato = createFilterRitrovamentoNonDenunciato(db);
			break;
		}
		case EventoAdozioneDaCanile.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_da_canile ado on (e.id_evento = ado.id_evento)  ");
			sqlFilterAdozioneCanile = createFilterAdozione(db);
			break;
		}
		case EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_da_canile ado_associazioni on (e.id_evento = ado_associazioni.id_evento)  ");
			sqlFilterAdozioneVersoAssociazioni = createFilterAdozioneAssociazioni(db);
			break;
		}
		case EventoRestituzioneACanile.idTipologiaDB: {
			// restituzione a canile
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_restituzione_a_canile rest on (e.id_evento = rest.id_evento) ");
			sqlFilterRestituzioneCanile = createFilterRestituzione(db);
			break;
		}

		case EventoAdozioneDaColonia.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_da_colonia adoColonia on (e.id_evento = adoColonia.id_evento)  ");
			sqlFilterAdozioneColonia = createFilterAdozioneColonia(db);
			break;
		}

		case EventoRilascioPassaporto.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento)   ");
			sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			break;

		}
		
		case EventoRilascioPassaporto.idTipologiaRinnovoDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento)   ");
			sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			break;

		}

		case EventoCessione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cessione cessione on (e.id_evento = cessione.id_evento)   ");
			sqlFilterCessione = createFilterCessione(db);
			break;
		}
		
		case EventoCessione.idTipologiaDB_obsoleto: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cessione cessione on (e.id_evento = cessione.id_evento)   ");
			sqlFilterCessione = createFilterCessione(db);
			break;
		}

		case EventoPresaInCaricoDaCessione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_presa_in_carico_cessione presa_cessione on (e.id_evento = presa_cessione.id_evento)  ");
			sqlFilterPresaCessione = createFilterPresaCessione(db);
			break;
		}
		
		
		case EventoAdozioneFuoriAsl.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_fuori_asl adozione on (e.id_evento = adozione.id_evento)   ");
			sqlFilterAdozioneFuoriAsl = createFilterAdozioneFuoriAsl(db);
			break;
		}
		
		case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_fuori_asl adozione on (e.id_evento = adozione.id_evento)   ");
			sqlFilterAdozioneFuoriAsl = createFilterAdozioneFuoriAsl(db);
			break;
		}

		case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_presa_in_carico_adozione_fuori_asl presa_adozione on (e.id_evento = presa_adozione.id_evento)  ");
			sqlFilterPresaCessione = createFilterPresaCessione(db);
			break;
		}

		case EventoTrasferimento.idTipologiaDB: {

			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento)  ");
			sqlFilterTrasferimento = createFilterTrasferimento(db);
			break;

		}
		
		case EventoTrasferimento.idTipologiaDBOperatoreCommerciale: {

			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento)  ");
			sqlFilterTrasferimento = createFilterTrasferimento(db);
			break;

		}

		case EventoTrasferimentoCanile.idTipologiaDB: {

			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento_canile trasferimentoC on (e.id_evento = trasferimentoC.id_evento)  ");
			sqlFilterTrasferimentoCanile = createFilterTrasferimentoCanile(db);
			break;

		}
		case EventoTrasferimentoSindaco.idTipologiaDB: {

			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento_sindaco trasferimentoSin on (e.id_evento = trasferimentoSin.id_evento)  ");
			sqlFilterTrasferimentoSindaco = createFilterTrasferimentoSindaco(db);
			break;

		}
		case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento_fuori_regione trasferimento_fr on (e.id_evento = trasferimento_fr.id_evento)  ");
			sqlFilterTrasferimentoFuoriRegione = createFilterTrasferimentoFuoriRegione(db);
			break;
		}

		case EventoTrasferimentoFuoriStato.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento_fuori_stato trasferimento_fs on (e.id_evento = trasferimento_fs.id_evento)  ");
			sqlFilterTrasferimentoFuoriStato = createFilterTrasferimentoFuoriStato(db);
			break;
		}

		case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_trasferimento_fuori_regione_solo_proprietario trasferimento_frp on (e.id_evento = trasferimento_frp.id_evento)  ");
			sqlFilterTrasferimentoFuoriRegioneSoloProp = createFilterTrasferimentoFuoriRegioneSoloProp(db);
			break;
		}

		case EventoRientroFuoriRegione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			sqlFilterRientroFuoriRegione = createFilterRientroFuoriRegione(db);
			break;
		}
		
		case EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			sqlFilterRientroFuoriRegione = createFilterRientroFuoriRegione(db);
			break;
		}
		case EventoRientroFuoriRegione.idTipologiaDBRandagio: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			sqlFilterRientroFuoriRegione = createFilterRientroFuoriRegione(db);
			break;
		}

		case EventoRientroFuoriStato.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_rientro_da_fuori_stato rientroFS on (e.id_evento = rientroFS.id_evento)  ");
			sqlFilterRientroFuoriStato = createFilterRientroFuoriStato(db);
			break;
		}

		case EventoCambioDetentore.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cambio_detentore detentore on (e.id_evento = detentore.id_evento)   ");
			sqlFilterDetentore = createFilterCambioDetentore(db);
			break;
		}
		
		case EventoRestituzioneAProprietario.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_restituzione_a_proprietario restituzione on (e.id_evento = restituzione.id_evento)   ");
			sqlFilterRestituzioneAProprietario = createFilterRestituzioneAProprietario(db);
			break;
		}

		case EventoMorsicatura.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_morsicatura morsicatura on (e.id_evento = morsicatura.id_evento)  ");
			sqlFilterMorsicatura = createFilterMorsicatura(db);
			break;
		}
		
		case EventoAggressione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_aggressione aggr on (e.id_evento = aggr.id_evento)  ");
			sqlFilterAggressione = createFilterAggressione(db);
			break;
		}


		case EventoEsitoControlli.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_esito_controlli controlli on (e.id_evento = controlli.id_evento)   ");
			sqlFilterControlli = createFilterEsitoControlli(db);
			break;
		}

		case EventoReimmissione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_reimmissione reimmissione on (e.id_evento = reimmissione.id_evento)  ");
			sqlFilterReimmissione = createFilterReimmissione(db);
			break;
		}

		case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_inserimento_esiti_controlli_commerciali commerciali on (e.id_evento = commerciali.id_evento)  ");
			sqlFilterInserimentoEsitoCommerciale = createFilterCommerciali(db);
			break;
		}

		case EventoSmarrimento.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_smarrimento smarrimento on (e.id_evento = smarrimento.id_evento) ");
			sqlFilterSmarrimento = createFilterSmarrimento(db);
			break;
		}

		case EventoAdozioneDistanza.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_adozione_distanza adoD on (e.id_evento = adoD.id_evento)   ");
			sqlFilterAdozioneDistanza = createFilterAdozioneDistanza(db);
			break;
		}

		case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_inserimento_microchip microchip2 on (e.id_evento = microchip2.id_evento) ");
			sqlFilterMicrochip = createFilterMicrochipSecondo(db);
			break;
		}

		case EventoInserimentoVaccinazioni.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_inserimento_vaccino vaccini on (e.id_evento = vaccini.id_evento)   ");
			sqlFilterVaccinazioni = createFilterVaccinazioni(db);
			break;
		}
		
		case EventoModificaResidenza.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_modifica_residenza residenza on (e.id_evento = residenza.id_evento)   ");
			sqlFilterModificaResidenza = createFilterModificaResidenza(db);
			break;
		}
		
		case EventoPrelievoDNA.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_prelievo_dna prelievo_dna on (e.id_evento = prelievo_dna.id_evento)   ");
			sqlFilterPrelievoDNA = createFilterPrelievoDNA(db);
			break;
		}
		
		case EventoCessioneImport.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_cessione_import cessione_import on (e.id_evento = cessione_import.id_evento)   ");
			sqlFilterCessioneImport = createFilterCessioneImport(db);
			break;
		}
		
		case EventoPresaCessioneImport.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_presa_in_carico_cessione_import presa_cessione_import on (e.id_evento = presa_cessione_import.id_evento)   ");
			sqlFilterPresaInCaricoCessioneImport = createFilterPresaCessioneImport(db);
			break;
		}

		
		case EventoPrelievoLeishmania.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_prelievo_leish prelievo_leish on (e.id_evento = prelievo_leish.id_evento)   ");
			sqlFilterPrelievoLeish = createFilterPrelievoLeish(db);
			break;
		}
		
		case EventoBloccoAnimale.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_blocco_animale blocco on (e.id_evento = blocco.id_evento)   ");
			sqlFilterBloccoAnimale = createFilterBloccoAnimale(db);
			break;
		}
		
		case EventoSbloccoAnimale.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_sblocco_animale sblocco on (e.id_evento = sblocco.id_evento)   ");
			sqlFilterSbloccoAnimale = createFilterSbloccoAnimale(db);
			break;
		}
		
		case EventoMutilazione.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_mutilazione mutilazione on (e.id_evento = mutilazione.id_evento)   ");
			sqlFilterMutilazione = createFilterMutilazione(db);
			break;
		}
		
		case EventoAllontanamento.idTipologiaDB: {
			sqlCount
					.append("SELECT COUNT(*) AS recordcount "
							+ "FROM evento e left join evento_allontanamento allontanamento on (e.id_evento = allontanamento.id_evento)   ");
			sqlFilterAllontanamento = createFilterAllontanamento(db);
			break;
		}
		
		default:
			
			break;

		}

		sqlCount
				.append(" left join animale a on (e.id_animale = a.id) WHERE e.id_evento >= 0 ");

		// createFilterEvento(db, sqlFilter);

		switch (idTipologiaEvento) {
		case -1: { /*
					 * sqlFilter.append(sqlFileterRegistrazione);
					 * sqlFilter.append(sqlFileterSterilizzazione);
					 * sqlFilter.append(sqlFilterFurto);
					 * sqlFilter.append(sqlFilterMicrochip);
					 * sqlFilter.append(sqlFilterCattura);
					 */
			
			
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRegistrazioneBDU.idTipologiaDB))
			{
				sqlFilter.append(sqlFileterRegistrazione);
			}
				
			// Sterilizzazione
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSterilizzazione.idTipologiaDB))
			{
			sqlFilter.append(sqlFileterSterilizzazione);
			}
			
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoDecesso.idTipologiaDB))
			{
			// Decesso
			sqlFilter.append(sqlFilterDecesso);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoFurto.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterFurto);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDB) || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip))
			{
			sqlFilter.append(sqlFilterMicrochip);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCattura.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterCattura);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterRitrovamento);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamentoNonDenunciato.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterRitrovamentoNonDenunciato);

			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterAdozioneCanile);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB))
			{
			sqlFilter.append(sqlFilterAdozioneCanile);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneACanile.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterRestituzioneCanile);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaColonia.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterAdozioneColonia);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaDB) || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaRinnovoDB))
			{
			sqlFilter.append(sqlFilterRilascioPassaporto);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessione.idTipologiaDB) || tipologieToSet.contains(EventoCessione.idTipologiaDB_obsoleto))
			{
			sqlFilter.append(sqlFilterCessione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaCessione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterPresaCessione);
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB)|| tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto))
			{
			sqlFilter.append(sqlFilterCessione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterPresaAdozioneFuoriAsl);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimento.idTipologiaDB) || tipologieToSet.contains(EventoTrasferimento.idTipologiaDBOperatoreCommerciale))
			{
			sqlFilter.append(sqlFilterTrasferimento);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoCanile.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterTrasferimentoCanile);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoSindaco.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterTrasferimentoSindaco);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterTrasferimentoFuoriRegione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriStato.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterTrasferimentoFuoriStato);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterTrasferimentoFuoriRegioneSoloProp);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDB) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBRandagio) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale))
			{
			sqlFilter.append(sqlFilterRientroFuoriRegione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriStato.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterRientroFuoriStato);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCambioDetentore.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterDetentore);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneAProprietario.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterRestituzioneAProprietario);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMorsicatura.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterMorsicatura);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAggressione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterAggressione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoEsitoControlli.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterControlli);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoReimmissione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterReimmissione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(26))
			{
			sqlFilter.append(sqlFilterInserimentoEsitoCommerciale);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSmarrimento.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterSmarrimento);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDistanza.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterAdozioneDistanza);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoVaccinazioni.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterVaccino);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoModificaResidenza.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterVaccino);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoDNA.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterPrelievoDNA);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessioneImport.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterCessioneImport);
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaCessioneImport.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterPresaInCaricoCessioneImport);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoLeishmania.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterPrelievoLeish);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoBloccoAnimale.idTipologiaDB))
			{
			sqlFilterBloccoAnimale = createFilterBloccoAnimale(db);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSbloccoAnimale.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterSbloccoAnimale);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMutilazione.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterMutilazione);
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAllontanamento.idTipologiaDB))
			{
			sqlFilter.append(sqlFilterAllontanamento);
			}
			createFilterEvento(db, sqlFilter);
			break;
		}
		case EventoRegistrazioneBDU.idTipologiaDB:
			sqlFilter.append(sqlFileterRegistrazione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoSterilizzazione.idTipologiaDB:
			sqlFilter.append(sqlFileterSterilizzazione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoDecesso.idTipologiaDB:
			sqlFilter.append(sqlFilterDecesso);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoFurto.idTipologiaDB:
			sqlFilter.append(sqlFilterFurto);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoInserimentoMicrochip.idTipologiaDB:
			sqlFilter.append(sqlFilterMicrochip);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoCattura.idTipologiaDB:
			sqlFilter.append(sqlFilterCattura);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoCattura.idTipologiaDBRicattura:
			sqlFilter.append(sqlFilterCattura);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRitrovamento.idTipologiaDB:
			sqlFilter.append(sqlFilterRitrovamento);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRitrovamentoNonDenunciato.idTipologiaDB:
			sqlFilter.append(sqlFilterRitrovamentoNonDenunciato);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAdozioneDaCanile.idTipologiaDB:
			sqlFilter.append(sqlFilterAdozioneCanile);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB:
			sqlFilter.append(sqlFilterAdozioneVersoAssociazioni);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRestituzioneACanile.idTipologiaDB:
			sqlFilter.append(sqlFilterRestituzioneCanile);
			createFilterEvento(db, sqlFilter);
			break;

		case EventoAdozioneDaColonia.idTipologiaDB:
			sqlFilter.append(sqlFilterAdozioneColonia);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRilascioPassaporto.idTipologiaDB:
			sqlFilter.append(sqlFilterRilascioPassaporto);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRilascioPassaporto.idTipologiaRinnovoDB:
			sqlFilter.append(sqlFilterRilascioPassaporto);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoCessione.idTipologiaDB:
			sqlFilter.append(sqlFilterCessione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoCessione.idTipologiaDB_obsoleto:
			sqlFilter.append(sqlFilterCessione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoPresaInCaricoDaCessione.idTipologiaDB:
			sqlFilter.append(sqlFilterPresaCessione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAdozioneFuoriAsl.idTipologiaDB:
			sqlFilter.append(sqlFilterAdozioneFuoriAsl);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto:
			sqlFilter.append(sqlFilterAdozioneFuoriAsl);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB:
			sqlFilter.append(sqlFilterPresaAdozioneFuoriAsl);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimento.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimento);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimento.idTipologiaDBOperatoreCommerciale:
			sqlFilter.append(sqlFilterTrasferimento);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimentoCanile.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimentoCanile);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimentoSindaco.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimentoSindaco);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimentoFuoriRegione.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimentoFuoriRegione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimentoFuoriStato.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimentoFuoriStato);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB:
			sqlFilter.append(sqlFilterTrasferimentoFuoriRegioneSoloProp);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRientroFuoriRegione.idTipologiaDB:
			sqlFilter.append(sqlFilterRientroFuoriRegione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRientroFuoriRegione.idTipologiaDBRandagio:
			sqlFilter.append(sqlFilterRientroFuoriRegione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale:
			sqlFilter.append(sqlFilterRientroFuoriRegione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRientroFuoriStato.idTipologiaDB:
			sqlFilter.append(sqlFilterRientroFuoriStato);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoCambioDetentore.idTipologiaDB:
			sqlFilter.append(sqlFilterDetentore);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRestituzioneAProprietario.idTipologiaDB:
			sqlFilter.append(sqlFilterRestituzioneAProprietario);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoMorsicatura.idTipologiaDB:
			sqlFilter.append(sqlFilterMorsicatura);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAggressione.idTipologiaDB:
			sqlFilter.append(sqlFilterAggressione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoEsitoControlli.idTipologiaDB:
			sqlFilter.append(sqlFilterControlli);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoReimmissione.idTipologiaDB:
			sqlFilter.append(sqlFilterReimmissione);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB:
			sqlFilter.append(sqlFilterInserimentoEsitoCommerciale);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoSmarrimento.idTipologiaDB:
			sqlFilter.append(sqlFilterSmarrimento);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoAdozioneDistanza.idTipologiaDB:
			sqlFilter.append(sqlFilterAdozioneDistanza);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip:
			sqlFilter.append(sqlFilterMicrochip);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoInserimentoVaccinazioni.idTipologiaDB: 
			sqlFilter.append(sqlFilterVaccinazioni);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoModificaResidenza.idTipologiaDB: 
			sqlFilter.append(sqlFilterModificaResidenza);
			createFilterEvento(db, sqlFilter);
			break;
			
		case EventoPrelievoDNA.idTipologiaDB: 
			sqlFilter.append(sqlFilterPrelievoDNA);
			createFilterEvento(db, sqlFilter);
			break;
			
			
		case EventoCessioneImport.idTipologiaDB: 
			sqlFilter.append(sqlFilterCessioneImport);
			createFilterEvento(db, sqlFilter);
			break;
			
		case EventoPresaCessioneImport.idTipologiaDB: 
			sqlFilter.append(sqlFilterPresaInCaricoCessioneImport);
			createFilterEvento(db, sqlFilter);
			break;
		case EventoPrelievoLeishmania.idTipologiaDB:
			sqlFilter.append(sqlFilterPrelievoLeish);
			createFilterEvento(db, sqlFilter);
			break;
		
			
		case EventoBloccoAnimale.idTipologiaDB:
			sqlFilter.append(sqlFilterBloccoAnimale);
			createFilterEvento(db, sqlFilter);
			break;
			
		case EventoSbloccoAnimale.idTipologiaDB:
			sqlFilter.append(sqlFilterSbloccoAnimale);
			createFilterEvento(db, sqlFilter);
			break;
			
		case EventoMutilazione.idTipologiaDB:
			sqlFilter.append(sqlFilterMutilazione);
			createFilterEvento(db, sqlFilter);
			break;
		
		case EventoAllontanamento.idTipologiaDB:
			sqlFilter.append(sqlFilterAllontanamento);
			createFilterEvento(db, sqlFilter);
			break;
			}
		
		
		
		createFilterAnimale(db, sqlFilter); 

		if (pagedListInfo != null) {
			// Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString()
					+ sqlFilter.toString());
			// items = prepareFilter(pst);
			int i = 0;
			switch (idTipologiaEvento) {
			case -1: {
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRegistrazioneBDU.idTipologiaDB))
					i = prepareFilterRegistrazione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSterilizzazione.idTipologiaDB))
					i = prepareFilterSterilizzazione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoDecesso.idTipologiaDB))
					i = prepareFilterDecesso(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoFurto.idTipologiaDB))
					i = prepareFilterFurto(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDB) || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip))
					i = prepareFilterMicrochip(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCattura.idTipologiaDB))
					i = prepareFilterCattura(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
					i = prepareFilterRitrovamento(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamentoNonDenunciato.idTipologiaDB))
					i = prepareFilterRitrovamentoNonDenunciato(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaDB))
					i = prepareFilterAdozione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneACanile.idTipologiaDB))
					i = prepareFilterRestituzione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaColonia.idTipologiaDB))
					i = prepareFilterAdozioneColonia(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaDB) || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaRinnovoDB))
					i = prepareFilterRilascioPassaporto(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessione.idTipologiaDB) || tipologieToSet.contains(EventoCessione.idTipologiaDB_obsoleto))
					i = prepareFilterCessione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaCessione.idTipologiaDB))
					i = prepareFilterPresaCessione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB) || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto))
					i = prepareFilterAdozioneFuoriAsl(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB))
					i = prepareFilterPresaAdozioneFuoriAsl(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimento.idTipologiaDB) || tipologieToSet.contains(EventoTrasferimento.idTipologiaDBOperatoreCommerciale))
					i = prepareFilterTrasferimento(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoCanile.idTipologiaDB))
					i = prepareFilterTrasferimentoCanile(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoSindaco.idTipologiaDB))
					i = prepareFilterTrasferimentoSindaco(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
					i = prepareFilterTrasferimentoFuoriRegione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriStato.idTipologiaDB))
					i = prepareFilterTrasferimentoFuoriStato(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB))
					i = prepareFilterTrasferimentoFuoriRegioneSoloProp(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDB) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBRandagio) || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale))
					i = prepareFilterRientroFuoriRegione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriStato.idTipologiaDB))
					i = prepareFilterRientroFuoriStato(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCambioDetentore.idTipologiaDB))
					i = prepareFilterCambioDetentore(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneAProprietario.idTipologiaDB))
					i = prepareFilterRestituzioneAProprietario(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMorsicatura.idTipologiaDB))
					i = prepareFilterMorsicatura(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAggressione.idTipologiaDB))
					i = prepareFilterAggressione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoEsitoControlli.idTipologiaDB))
					i = prepareFilterEsitoControlli(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoReimmissione.idTipologiaDB))
					i = prepareFilterReimmissione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(26))
					i = prepareFilterCommerciali(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSmarrimento.idTipologiaDB))
					i = prepareFilterSmarrimento(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDistanza.idTipologiaDB))
					i = prepareFilterAdozioneDistanza(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoModificaResidenza.idTipologiaDB))
					i = prepareFilterModificaResidenza(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoDNA.idTipologiaDB))
					i = prepareFilterPrelievoDNA(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessioneImport.idTipologiaDB))
					i = prepareFilterCessioneImport(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaCessioneImport.idTipologiaDB))
					i = prepareFilterPresaCessioneImport(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoLeishmania.idTipologiaDB))
					i = prepareFilterPrelievoLeish(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoBloccoAnimale.idTipologiaDB))
					i = prepareFilterBloccoAnimale(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSbloccoAnimale.idTipologiaDB))
					i = prepareFilterSbloccoAnimale(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMutilazione.idTipologiaDB))
					i = prepareFilterMutilazione(pst, i);
				if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAllontanamento.idTipologiaDB))
					i = prepareFilterAllontanamento(pst, i);
				i = prepareFilterEvento(pst, i);
				break;
			}

			case EventoRegistrazioneBDU.idTipologiaDB:
				i = prepareFilterRegistrazione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoSterilizzazione.idTipologiaDB:
				i = prepareFilterSterilizzazione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoDecesso.idTipologiaDB:
				i = prepareFilterDecesso(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoFurto.idTipologiaDB:
				i = prepareFilterFurto(pst, i);
				i = prepareFilterEvento(pst, i);
				break;
			case EventoInserimentoMicrochip.idTipologiaDB:
				i = prepareFilterMicrochip(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoCattura.idTipologiaDB:
				i = prepareFilterCattura(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoCattura.idTipologiaDBRicattura:
				i = prepareFilterCattura(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRitrovamento.idTipologiaDB:
				i = prepareFilterRitrovamento(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRitrovamentoNonDenunciato.idTipologiaDB:
				i = prepareFilterRitrovamentoNonDenunciato(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAdozioneDaCanile.idTipologiaDB:
				i = prepareFilterAdozione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB:
				i = prepareFilterAdozione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRestituzioneACanile.idTipologiaDB:
				i = prepareFilterRestituzione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAdozioneDaColonia.idTipologiaDB:
				i = prepareFilterAdozioneColonia(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRilascioPassaporto.idTipologiaDB:
				i = prepareFilterRilascioPassaporto(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRilascioPassaporto.idTipologiaRinnovoDB:
				i = prepareFilterRilascioPassaporto(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoCessione.idTipologiaDB:
				i = prepareFilterCessione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoCessione.idTipologiaDB_obsoleto:
				i = prepareFilterCessione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoPresaInCaricoDaCessione.idTipologiaDB:
				i = prepareFilterPresaCessione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimento.idTipologiaDB:
				i = prepareFilterTrasferimento(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimento.idTipologiaDBOperatoreCommerciale:
				i = prepareFilterTrasferimento(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimentoCanile.idTipologiaDB:
				i = prepareFilterTrasferimentoCanile(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimentoSindaco.idTipologiaDB:
				i = prepareFilterTrasferimentoSindaco(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimentoFuoriRegione.idTipologiaDB:
				i = prepareFilterTrasferimentoFuoriRegione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimentoFuoriStato.idTipologiaDB:
				i = prepareFilterTrasferimentoFuoriStato(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB:
				i = prepareFilterTrasferimentoFuoriRegioneSoloProp(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRientroFuoriRegione.idTipologiaDB:
				i = prepareFilterRientroFuoriRegione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRientroFuoriRegione.idTipologiaDBRandagio:
				i = prepareFilterRientroFuoriRegione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale:
				i = prepareFilterRientroFuoriRegione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRientroFuoriStato.idTipologiaDB:
				i = prepareFilterRientroFuoriStato(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoCambioDetentore.idTipologiaDB:
				i = prepareFilterCambioDetentore(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRestituzioneAProprietario.idTipologiaDB:
				i = prepareFilterRestituzioneAProprietario(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoMorsicatura.idTipologiaDB:
				i = prepareFilterMorsicatura(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAggressione.idTipologiaDB:
				i = prepareFilterAggressione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoEsitoControlli.idTipologiaDB:
				i = prepareFilterEsitoControlli(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoReimmissione.idTipologiaDB:
				i = prepareFilterReimmissione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB:
				i = prepareFilterCommerciali(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoSmarrimento.idTipologiaDB:
				i = prepareFilterSmarrimento(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip:
				i = prepareFilterMicrochipSecondo(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoInserimentoVaccinazioni.idTipologiaDB:
				i = prepareFilterVaccinazioni(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoModificaResidenza.idTipologiaDB:
				i = prepareFilterModificaResidenza(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAdozioneFuoriAsl.idTipologiaDB:
				i = prepareFilterAdozioneFuoriAsl(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto:
				i = prepareFilterAdozioneFuoriAsl(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB:
				i = prepareFilterPresaCessione(pst, i);
				prepareFilterEvento(pst, i);
				break;
			case EventoPrelievoDNA.idTipologiaDB:
				i = prepareFilterPrelievoDNA(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoCessioneImport.idTipologiaDB:
				i = prepareFilterCessioneImport(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoPresaCessioneImport.idTipologiaDB:
				i = prepareFilterPresaCessioneImport(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoPrelievoLeishmania.idTipologiaDB:
				i = prepareFilterPrelievoLeish(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoBloccoAnimale.idTipologiaDB:
				i = prepareFilterBloccoAnimale(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoSbloccoAnimale.idTipologiaDB:
				i = prepareFilterSbloccoAnimale(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoMutilazione.idTipologiaDB:
				i = prepareFilterMutilazione(pst, i);
				prepareFilterEvento(pst, i);
				break;
				
			case EventoAllontanamento.idTipologiaDB:
				i = prepareFilterAllontanamento(pst, i);
				prepareFilterEvento(pst, i);
				break;
			
			}

			i = prepareFilterAnimale(pst, i);

			System.out.println("ESECUZIONE QUERY LISTA REGISTRAZIONI OTTIMIZZATA: " + pst.toString());
			
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			pagedListInfo.setColumnToSortBy("e.entered");
			pagedListInfo.setSortOrder("DESC");
			pagedListInfo.appendSqlTail(db, sqlOrder);
			// Determine column to sort by

			// Optimize SQL Server Paging
			// sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization "
			// + sqlOrder.toString());
		} else {
			sqlOrder.append("ORDER BY e.entered DESC");
		}

		// Need to build a base SQL statement for returning records
		if (pagedListInfo != null) {
			pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		} else {
			sqlSelect.append("SELECT ");
		}

		switch (idTipologiaEvento) {
		case -1: {// Tutti gli eventi: CAMBIARE TABELLA IN EVENTOREGISTRAZIONE

			sqlSelect.append(" *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento ");
			
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
				sqlSelect.append(" , rit.data_ritrovamento as data_ritrovamento_specifico ");
			
			sqlSelect.append(" from evento e ");

			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRegistrazioneBDU.idTipologiaDB))
			{
			// registrazione in BDU
			sqlSelect
					.append("left join evento_registrazione_bdu reg on (e.id_evento = reg.id_evento ");
			sqlFileterRegistrazione = createFilterRegistrazione(db);
			sqlSelect.append(sqlFileterRegistrazione);
			sqlSelect.append(")");

			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSterilizzazione.idTipologiaDB))
			{
			// Sterilizzazione
			sqlSelect
					.append(" left join evento_sterilizzazione ster on (e.id_evento = ster.id_evento ");
			sqlFileterSterilizzazione = createFilterSterilizzazione(db);
			sqlSelect.append(sqlFileterSterilizzazione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoDecesso.idTipologiaDB))
			{
			// Decesso
			sqlSelect
					.append(" left join evento_decesso decess on (e.id_evento = decess.id_evento ");
			sqlFilterDecesso = createFilterDecesso(db);
			sqlSelect.append(sqlFilterDecesso);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoFurto.idTipologiaDB))
			{
			// Furto
			sqlSelect
					.append("left join evento_furto furto on (e.id_evento = furto.id_evento ");
			sqlFilterFurto = createFilterFurto(db);
			sqlSelect.append(sqlFilterFurto);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDB) || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip))
			{
			// Inserimento microchip
			sqlSelect
					.append("left join evento_inserimento_microchip microchip on (e.id_evento = microchip.id_evento ");
			sqlFilterMicrochip = createFilterMicrochip(db);
			sqlSelect.append(sqlFilterMicrochip);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCattura.idTipologiaDB))
			{
			// Inserimento cattura
			sqlSelect
					.append(" left join evento_cattura cattura on (e.id_evento = cattura.id_evento  ");
			sqlFilterCattura = createFilterCattura(db);
			sqlSelect.append(sqlFilterCattura);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
			{
			// ritrovamento
			sqlSelect
					.append(" left join evento_ritrovamento rit on (e.id_evento = rit.id_evento ");
			sqlFilterRitrovamento = createFilterRitrovamento(db);
			sqlSelect.append(sqlFilterRitrovamento);
			sqlSelect.append(") ");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamentoNonDenunciato.idTipologiaDB))
			{
			// ritrovamento non denunciato
			sqlSelect
					.append(" left join evento_ritrovamento_non_denunciato rit_nd on (e.id_evento = rit_nd.id_evento ");
			sqlFilterRitrovamento = createFilterRitrovamentoNonDenunciato(db);
			sqlSelect.append(sqlFilterRitrovamentoNonDenunciato);
			sqlSelect.append(") ");

			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaDB))
			{
			// adozione da canile
			sqlSelect
					.append("left join evento_adozione_da_canile ado on (e.id_evento = ado.id_evento ");
			sqlFilterAdozioneCanile = createFilterAdozione(db);
			sqlSelect.append(sqlFilterAdozioneCanile);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB))
			{
			// adozione da canile verso associazioni
			sqlSelect
			.append("left join evento_adozione_da_canile ado_associazioni on (e.id_evento = ado_associazioni.id_evento ");
				sqlFilterAdozioneVersoAssociazioni = createFilterAdozioneAssociazioni(db);
				sqlSelect.append(sqlFilterAdozioneVersoAssociazioni);
				sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneACanile.idTipologiaDB))
			{
			// restituzione a canile
			sqlSelect
					.append("left join evento_restituzione_a_canile rest on (e.id_evento = rest.id_evento ");
			sqlFilterRestituzioneCanile = createFilterRestituzione(db);
			sqlSelect.append(sqlFilterRestituzioneCanile);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaColonia.idTipologiaDB))
			{
			// adozione da colonia
			sqlSelect
					.append("left join evento_adozione_da_colonia adoColonia on (e.id_evento = adoColonia.id_evento ");
			sqlFilterAdozioneColonia = createFilterAdozioneColonia(db);
			sqlSelect.append(sqlFilterAdozioneColonia);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaDB) || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaRinnovoDB))
			{
			// rilascio passaporto
			sqlSelect
					.append("left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento ");
			sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			sqlSelect.append(sqlFilterRilascioPassaporto);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessione.idTipologiaDB) || tipologieToSet.contains(EventoCessione.idTipologiaDB_obsoleto))
			{
			// cessione

			sqlSelect
					.append("left join evento_cessione cessione on (e.id_evento = cessione.id_evento ");
			sqlFilterCessione = createFilterCessione(db);
			sqlSelect.append(sqlFilterCessione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaCessione.idTipologiaDB))
			{
			// presa in carico da cessione
			sqlSelect
					.append("left join evento_presa_in_carico_cessione presa_cessione on (e.id_evento = presa_cessione.id_evento ");
			sqlFilterPresaCessione = createFilterPresaCessione(db);
			sqlSelect.append(sqlFilterPresaCessione);
			sqlSelect.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB)|| tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto))
			{
			// cessione

			sqlSelect
					.append("left join evento_adozione_fuori_asl adozione_fa on (e.id_evento = adozione_fa.id_evento ");
			sqlFilterAdozioneFuoriAsl = createFilterAdozioneFuoriAsl(db);
			sqlSelect.append(sqlFilterAdozioneFuoriAsl);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB))
			{
			// presa in carico da cessione
			sqlSelect
					.append("left join evento_presa_in_carico_adozione_fuori_asl presa_adozione on (e.id_evento = presa_adozione.id_evento ");
			sqlFilterPresaAdozioneFuoriAsl = createFilterPresaAdozioneFuoriAsl(db);
			sqlSelect.append(sqlFilterPresaAdozioneFuoriAsl);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimento.idTipologiaDB) || tipologieToSet.contains(EventoTrasferimento.idTipologiaDBOperatoreCommerciale))
			{
			// trasferimento

			sqlSelect
					.append("left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento ");
			sqlFilterTrasferimento = createFilterTrasferimento(db);
			sqlSelect.append(sqlFilterTrasferimento);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoCanile.idTipologiaDB))
			{
			sqlSelect
					.append("left join evento_trasferimento_canile trasferimentoC on (e.id_evento = trasferimentoC.id_evento ");
			sqlFilterTrasferimentoCanile = createFilterTrasferimentoCanile(db);
			sqlSelect.append(sqlFilterTrasferimentoCanile);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoSindaco.idTipologiaDB))
			{
			sqlSelect
					.append("left join evento_trasferimento_sindaco trasferimentoSin on (e.id_evento = trasferimentoSin.id_evento ");
			sqlFilterTrasferimentoSindaco = createFilterTrasferimentoSindaco(db);
			sqlSelect.append(sqlFilterTrasferimentoSindaco);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
			{
			// trasferimento fuori regione
			sqlSelect
					.append("left join evento_trasferimento_fuori_regione trasferimento_fr on (e.id_evento = trasferimento_fr.id_evento ");
			sqlFilterTrasferimentoFuoriRegione = createFilterTrasferimentoFuoriRegione(db);
			sqlSelect.append(sqlFilterTrasferimentoFuoriRegione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriStato.idTipologiaDB))
			{
			// trasferimento fuori stato
			sqlSelect
					.append("left join evento_trasferimento_fuori_stato trasferimento_fs on (e.id_evento = trasferimento_fs.id_evento ");
			sqlFilterTrasferimentoFuoriStato = createFilterTrasferimentoFuoriStato(db);
			sqlSelect.append(sqlFilterTrasferimentoFuoriStato);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB))
			{
			// trasferimento fuori regione solo prop
			sqlSelect
					.append("left join evento_trasferimento_fuori_regione_solo_proprietario trasferimento_frp on (e.id_evento = trasferimento_frp.id_evento ");
			sqlFilterTrasferimentoFuoriRegioneSoloProp = createFilterTrasferimentoFuoriRegioneSoloProp(db);
			sqlSelect.append(sqlFilterTrasferimentoFuoriRegioneSoloProp);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDB)|| tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBRandagio)|| tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale))
			{
			// rientro fuori regione
			sqlSelect
					.append("left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento ");
			sqlFilterRientroFuoriRegione = createFilterRientroFuoriRegione(db);
			sqlSelect.append(sqlFilterRientroFuoriRegione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriStato.idTipologiaDB))
			{
			// rientro fuori stato
			sqlSelect
					.append("left join evento_rientro_da_fuori_stato rientroFS on (e.id_evento = rientroFS.id_evento ");
			sqlFilterRientroFuoriStato = createFilterRientroFuoriStato(db);
			sqlSelect.append(sqlFilterRientroFuoriStato);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCambioDetentore.idTipologiaDB))
			{
			// cambio detentore
			sqlSelect
					.append("left join evento_cambio_detentore detentore on (e.id_evento = detentore.id_evento ");
			sqlFilterDetentore = createFilterCambioDetentore(db);
			sqlSelect.append(sqlFilterDetentore);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneAProprietario.idTipologiaDB))
			{
			// restituzione a proprietario
			sqlSelect
					.append("left join evento_restituzione_a_proprietario restituzione on (e.id_evento = restituzione.id_evento ");
			sqlFilterDetentore = createFilterCambioDetentore(db);
			sqlSelect.append(sqlFilterDetentore);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMorsicatura.idTipologiaDB))
			{
			// morsicatura
			sqlSelect
					.append("left join evento_morsicatura morsicatura on (e.id_evento = morsicatura.id_evento ");
			sqlFilterMorsicatura = createFilterMorsicatura(db);
			sqlSelect.append(sqlFilterMorsicatura);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAggressione.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_aggressione aggr on (e.id_evento = aggr.id_evento ");
			sqlFilterAggressione = createFilterAggressione(db);
			sqlSelect.append(sqlFilterAggressione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoEsitoControlli.idTipologiaDB))
			{
			// esito controlli
			// controlli
			sqlSelect
					.append("left join evento_esito_controlli controlli on (e.id_evento = controlli.id_evento ");
			sqlFilterControlli = createFilterEsitoControlli(db);
			sqlSelect.append(sqlFilterControlli);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoReimmissione.idTipologiaDB))
			{
			// reimmissione
			sqlSelect
					.append("left join evento_reimmissione reimmissione on (e.id_evento = reimmissione.id_evento ");
			sqlFilterReimmissione = createFilterReimmissione(db);
			sqlSelect.append(sqlFilterReimmissione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(26))
			{
			// controlli commerciali
			sqlSelect
					.append("left join evento_inserimento_esiti_controlli_commerciali commerciali on (e.id_evento = commerciali.id_evento ");
			sqlFilterInserimentoEsitoCommerciale = createFilterCommerciali(db);
			sqlSelect.append(sqlFilterInserimentoEsitoCommerciale);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSmarrimento.idTipologiaDB))
			{
			// smarrimento
			sqlSelect
					.append("left join evento_smarrimento smarrimento on (e.id_evento = smarrimento.id_evento ");
			sqlFilterSmarrimento = createFilterSmarrimento(db);
			sqlSelect.append(sqlFilterSmarrimento);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDistanza.idTipologiaDB))
			{
			// Adozione distanza
			sqlSelect
					.append("left join evento_adozione_distanza adoD on (e.id_evento = adoD.id_evento ");
			sqlFilterAdozioneDistanza = createFilterAdozioneDistanza(db);
			sqlSelect.append(sqlFilterAdozioneDistanza);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoVaccinazioni.idTipologiaDB))
			{
			// Evento inserimento vaccino
			sqlSelect
					.append("left join evento_inserimento_vaccino vaccino on (e.id_evento = vaccino.id_evento ");
			sqlFilterVaccino = createFilterVaccino(db);
			sqlSelect.append(sqlFilterVaccino);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoModificaResidenza.idTipologiaDB))
			{
			// Evento modifica residenza
			sqlSelect
					.append("left join evento_modifica_residenza residenza on (e.id_evento = residenza.id_evento ");
			sqlFilterModificaResidenza = createFilterModificaResidenza(db);
			sqlSelect.append(sqlFilterModificaResidenza);
			sqlSelect.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoDNA.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_prelievo_dna prelievo_dna on (e.id_evento = prelievo_dna.id_evento ");
			sqlFilterPrelievoDNA = createFilterPrelievoDNA(db);
			sqlSelect.append(sqlFilterPrelievoDNA);
			sqlSelect.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessioneImport.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_cessione_import cessione_import on (e.id_evento = cessione_import.id_evento ");
			sqlFilterCessioneImport = createFilterCessioneImport(db);
			sqlSelect.append(sqlFilterCessioneImport);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaCessioneImport.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_presa_in_carico_cessione_import presa_cessione_import on (e.id_evento = presa_cessione_import.id_evento ");
			sqlFilterPresaInCaricoCessioneImport = createFilterPresaCessioneImport(db);
			sqlSelect.append(sqlFilterPresaInCaricoCessioneImport);
			sqlSelect.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoLeishmania.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_prelievo_leish prelievo_leish on (e.id_evento = prelievo_leish.id_evento ");
			sqlFilterPrelievoLeish = createFilterPrelievoLeish(db);
			sqlSelect.append(sqlFilterPrelievoLeish);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoBloccoAnimale.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_blocco_animale blocco on (e.id_evento = blocco.id_evento ");
			sqlFilterBloccoAnimale = createFilterBloccoAnimale(db);
			sqlSelect.append(sqlFilterBloccoAnimale);
			sqlSelect.append(")");
			
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSbloccoAnimale.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_sblocco_animale sblocco on (e.id_evento = sblocco.id_evento ");
			sqlFilterSbloccoAnimale = createFilterSbloccoAnimale(db);
			sqlSelect.append(sqlFilterSbloccoAnimale);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMutilazione.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_mutilazione mutilazione on (e.id_evento = mutilazione.id_evento ");
			sqlFilterMutilazione = createFilterMutilazione(db);
			sqlSelect.append(sqlFilterMutilazione);
			sqlSelect.append(")");
			}
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAllontanamento.idTipologiaDB))
			{
			sqlSelect
			.append("left join evento_allontanamento allontanamento on (e.id_evento = allontanamento.id_evento ");
			sqlFilterAllontanamento = createFilterAllontanamento(db);
			sqlSelect.append(sqlFilterAllontanamento);
			sqlSelect.append(")");
			}
			break;
		}

		case EventoRegistrazioneBDU.idTipologiaDB: { // registrazione
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, reg.*   "
							+ "FROM evento e left join evento_registrazione_bdu reg on (e.id_evento = reg.id_evento) ");
			/* sqlFileterRegistrazione = createFilterRegistrazione(db); */
			// sqlSelect.append(sqlFileterRegistrazione);
			break;
		}
		case EventoSterilizzazione.idTipologiaDB: {// Sterilizzazione
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, ster.*  "
							+ "FROM evento e left join evento_sterilizzazione ster on (e.id_evento = ster.id_evento)  ");
			/* sqlFileterSterilizzazione = createFilterSterilizzazione(db); */
			// sqlSelect.append(sqlFileterSterilizzazione);
			break;
		}

		case EventoDecesso.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, decess.*  "
							+ "FROM evento e left join evento_decesso decess on (e.id_evento = decess.id_evento)   ");
			// sqlFilterDecesso = createFilterDecesso(db);
			// sqlSelect.append(sqlFilterDecesso);
			break;
		}
		case EventoFurto.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, furto.*  "
							+ "FROM evento e left join evento_furto furto on (e.id_evento = furto.id_evento )  ");
			// sqlFilterFurto = createFilterFurto(db);
			// sqlSelect.append(sqlFilterFurto);
			break;
		}

		case EventoInserimentoMicrochip.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, microchip.*  "
							+ "FROM evento e left join evento_inserimento_microchip microchip on (e.id_evento = microchip.id_evento)   ");
			// sqlFilterMicrochip = createFilterMicrochip(db);
			// sqlSelect.append(sqlFilterMicrochip);
			break;
		}

		case EventoCattura.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, cattura.*  "
							+ "FROM evento e left join evento_cattura cattura on (e.id_evento = cattura.id_evento)    ");
			// sqlFilterCattura = createFilterCattura(db);
			// sqlSelect.append(sqlFilterCattura);
			break;
		}

		case EventoCattura.idTipologiaDBRicattura: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, cattura.*  "
							+ "FROM evento e left join evento_cattura cattura on (e.id_evento = cattura.id_evento)    ");
			// sqlFilterCattura = createFilterCattura(db);
			// sqlSelect.append(sqlFilterCattura);
			break;
		}

		case EventoRitrovamento.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rit.*  "
							+ "FROM evento e left join evento_ritrovamento rit on (e.id_evento = rit.id_evento)   ");
			// sqlFilterRitrovamento = createFilterRitrovamento(db);
			// sqlSelect.append(sqlFilterRitrovamento);
			break;
		}
		
		case EventoRitrovamentoNonDenunciato.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rit_nd.*  "
							+ "FROM evento e left join evento_ritrovamento_non_denunciato rit_nd on (e.id_evento = rit_nd.id_evento)   ");
			// sqlFilterRitrovamento = createFilterRitrovamento(db);
			// sqlSelect.append(sqlFilterRitrovamento);
			break;
		}
		case EventoAdozioneDaCanile.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, ado.*  "
							+ "FROM evento e left join evento_adozione_da_canile ado on (e.id_evento = ado.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		case EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, ado_associazioni.*  "
							+ "FROM evento e left join evento_adozione_da_canile ado_associazioni on (e.id_evento = ado_associazioni.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		case EventoRestituzioneACanile.idTipologiaDB: {
			// restituzione a canile
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rest.*  "
							+ "FROM evento e left join evento_restituzione_a_canile rest on (e.id_evento = rest.id_evento)  ");
			// sqlFilterRestituzioneCanile = createFilterRestituzione(db);
			// sqlSelect.append(sqlFilterRestituzioneCanile);
			break;
		}

		case EventoAdozioneDaColonia.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, adoColonia.*  "
							+ "FROM evento e left join evento_adozione_da_colonia adoColonia on (e.id_evento = adoColonia.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}

		case EventoRilascioPassaporto.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, passaporto.*  "
							+ "FROM evento e left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento)  ");
			// sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			// sqlSelect.append(sqlFilterRilascioPassaporto);
			break;

		}
		
		case EventoRilascioPassaporto.idTipologiaRinnovoDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, passaporto.*  "
							+ "FROM evento e left join evento_rilascio_passaporto passaporto on (e.id_evento = passaporto.id_evento)  ");
			// sqlFilterRilascioPassaporto = createFilterRilascioPassaporto(db);
			// sqlSelect.append(sqlFilterRilascioPassaporto);
			break;

		}

		case EventoCessione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, cessione.*  "
							+ "FROM evento e left join evento_cessione cessione on (e.id_evento = cessione.id_evento)  ");
			// sqlFilterCessione = createFilterCessione(db);
			// sqlSelect.append(sqlFilterCessione);
			break;
		}
		
		case EventoCessione.idTipologiaDB_obsoleto: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, cessione.*  "
							+ "FROM evento e left join evento_cessione cessione on (e.id_evento = cessione.id_evento)  ");
			// sqlFilterCessione = createFilterCessione(db);
			// sqlSelect.append(sqlFilterCessione);
			break;
		}

		case EventoPresaInCaricoDaCessione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, presa_cessione.*  "
							+ "FROM evento e left join evento_presa_in_carico_cessione presa_cessione on (e.id_evento = presa_cessione.id_evento)   ");
			// sqlFilterPresaCessione = createFilterPresaCessione(db);
			// sqlSelect.append(sqlFilterPresaCessione);
			break;
		}

		case EventoTrasferimento.idTipologiaDB: {

			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimento.*  "
							+ "FROM evento e left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento)   ");
			// sqlFilterTrasferimento = createFilterTrasferimento(db);
			// sqlSelect.append(sqlFilterTrasferimento);
			break;

		}
		
		case EventoTrasferimento.idTipologiaDBOperatoreCommerciale: {

			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimento.*  "
							+ "FROM evento e left join evento_trasferimento trasferimento on (e.id_evento = trasferimento.id_evento)   ");
			// sqlFilterTrasferimento = createFilterTrasferimento(db);
			// sqlSelect.append(sqlFilterTrasferimento);
			break;

		}

		case EventoTrasferimentoCanile.idTipologiaDB: {

			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimentoC.*  "
							+ "FROM evento e left join evento_trasferimento_canile trasferimentoC on (e.id_evento = trasferimentoC.id_evento)  ");
			// sqlFilterTrasferimento = createFilterTrasferimento(db);
			// sqlSelect.append(sqlFilterTrasferimento);
			break;

		}
		
		case EventoTrasferimentoSindaco.idTipologiaDB: {

			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimentoSin.*  "
							+ "FROM evento e left join evento_trasferimento_sindaco trasferimentoSin on (e.id_evento = trasferimentoSin.id_evento)  ");
			// sqlFilterTrasferimento = createFilterTrasferimento(db);
			// sqlSelect.append(sqlFilterTrasferimento);
			break;

		}

		case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimento_fr.*  "
							+ "FROM evento e left join evento_trasferimento_fuori_regione trasferimento_fr on (e.id_evento = trasferimento_fr.id_evento)  ");
			// sqlFilterTrasferimentoFuoriRegione =
			// createFilterTrasferimentoFuoriRegione(db);
			// sqlSelect.append(sqlFilterTrasferimentoFuoriRegione);
			break;
		}

		case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimento_frp.*  "
							+ "FROM evento e left join evento_trasferimento_fuori_regione_solo_proprietario trasferimento_frp on (e.id_evento = trasferimento_frp.id_evento)  ");
			// sqlFilterTrasferimentoFuoriRegione =
			// createFilterTrasferimentoFuoriRegione(db);
			// sqlSelect.append(sqlFilterTrasferimentoFuoriRegione);
			break;
		}

		case EventoTrasferimentoFuoriStato.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, trasferimento_fs.*  "
							+ "FROM evento e left join evento_trasferimento_fuori_stato trasferimento_fs on (e.id_evento = trasferimento_fs.id_evento)  ");
			// sqlFilterTrasferimentoFuoriRegione =
			// createFilterTrasferimentoFuoriRegione(db);
			// sqlSelect.append(sqlFilterTrasferimentoFuoriRegione);
			break;
		}

		case EventoRientroFuoriRegione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rientro.*  "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			// sqlFilterRientroFuoriRegione =
			// createFilterRientroFuoriRegione(db);
			// sqlSelect.append(sqlFilterRientroFuoriRegione);
			break;
		}
		case EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rientro.*  "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			// sqlFilterRientroFuoriRegione =
			// createFilterRientroFuoriRegione(db);
			// sqlSelect.append(sqlFilterRientroFuoriRegione);
			break;
		}
		case EventoRientroFuoriRegione.idTipologiaDBRandagio: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rientro.*  "
							+ "FROM evento e left join evento_rientro_da_fuori_regione rientro on (e.id_evento = rientro.id_evento)  ");
			// sqlFilterRientroFuoriRegione =
			// createFilterRientroFuoriRegione(db);
			// sqlSelect.append(sqlFilterRientroFuoriRegione);
			break;
		}
		case EventoRientroFuoriStato.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, rientroFS.*  "
							+ "FROM evento e left join evento_rientro_da_fuori_stato rientroFS on (e.id_evento = rientroFS.id_evento)  ");
			// sqlFilterRientroFuoriRegione =
			// createFilterRientroFuoriRegione(db);
			// sqlSelect.append(sqlFilterRientroFuoriRegione);
			break;
		}

		case EventoCambioDetentore.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, detentore.*  "
							+ "FROM evento e left join evento_cambio_detentore detentore on (e.id_evento = detentore.id_evento)   ");
			// sqlFilterDetentore = createFilterCambioDetentore(db);
			// sqlSelect.append(sqlFilterDetentore);
			break;
		}
		
		case EventoRestituzioneAProprietario.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, restituzione.*  "
							+ "FROM evento e left join evento_restituzione_a_proprietario restituzione on (e.id_evento = restituzione.id_evento)   ");
			// sqlFilterDetentore = createFilterCambioDetentore(db);
			// sqlSelect.append(sqlFilterDetentore);
			break;
		}
		
		case EventoMorsicatura.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, morsicatura.*  "
							+ "FROM evento e left join evento_morsicatura morsicatura on (e.id_evento = morsicatura.id_evento)   ");
			// sqlFilterMorsicatura = createFilterMorsicatura(db);
			// sqlSelect.append(sqlFilterMorsicatura);
			break;
		}
		
		case EventoAggressione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, aggr.*  "
							+ "FROM evento e left join evento_aggressione aggr on (e.id_evento = aggr.id_evento)   ");
			// sqlFilterMorsicatura = createFilterMorsicatura(db);
			// sqlSelect.append(sqlFilterMorsicatura);
			break;
		}

		case EventoEsitoControlli.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, controlli.*  "
							+ "FROM evento e left join evento_esito_controlli controlli on (e.id_evento = controlli.id_evento)  ");
			// sqlFilterControlli = createFilterEsitoControlli(db);
			// sqlSelect.append(sqlFilterControlli);
			break;
		}

		case EventoReimmissione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, reimmissione.*  "
							+ "FROM evento e left join evento_reimmissione reimmissione on (e.id_evento = reimmissione.id_evento )  ");
			// sqlFilterReimmissione = createFilterReimmissione(db);
			// sqlSelect.append(sqlFilterReimmissione);
			break;
		}

		case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, commerciali.*  "
							+ "FROM evento e left join evento_inserimento_esiti_controlli_commerciali commerciali on (e.id_evento = commerciali.id_evento)  ");
			// sqlFilterInserimentoEsitoCommerciale =
			// createFilterCommerciali(db);
			// sqlSelect.append(sqlFilterInserimentoEsitoCommerciale);
			break;
		}

		case EventoSmarrimento.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, smarrimento.*  "
							+ "FROM evento e left join evento_smarrimento smarrimento on (e.id_evento = smarrimento.id_evento)  ");
			// sqlFilterSmarrimento = createFilterSmarrimento(db);
			// sqlSelect.append(sqlFilterSmarrimento);
			break;
		}

		case EventoAdozioneDistanza.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, adoD.*  "
							+ "FROM evento e left join evento_adozione_canile adoD on (e.id_evento = adoD.id_evento)  ");
			// sqlFilterSmarrimento = createFilterSmarrimento(db);
			// sqlSelect.append(sqlFilterSmarrimento);
			break;
		}
		case EventoInserimentoVaccinazioni.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, vaccini.*  "
							+ "FROM evento e left join evento_inserimento_vaccino vaccini on (e.id_evento = vaccini.id_evento)  ");
			break;
		}

		case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, microchip2.*  "
							+ "FROM evento e left join evento_inserimento_microchip microchip2 on (e.id_evento = microchip2.id_evento)   ");

			break;
		}
		case EventoModificaResidenza.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, residenza.*  "
							+ "FROM evento e left join evento_modifica_residenza residenza on (e.id_evento = residenza.id_evento)   ");

			break;
		}
		case EventoAdozioneFuoriAsl.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, adozione.*  "
							+ "FROM evento e left join evento_adozione_fuori_asl adozione on (e.id_evento = adozione.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, adozione.*  "
							+ "FROM evento e left join evento_adozione_fuori_asl adozione on (e.id_evento = adozione.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, presa_adozione.*  "
							+ "FROM evento e left join evento_presa_in_carico_adozione_fuori_asl presa_adozione on (e.id_evento = presa_adozione.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		case EventoPrelievoDNA.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, prelievo_dna.*  "
							+ "FROM evento e left join evento_prelievo_dna prelievo_dna on (e.id_evento = prelievo_dna.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}

		
		case EventoCessioneImport.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, cessione_import.*  "
							+ "FROM evento e left join evento_cessione_import cessione_import on (e.id_evento = cessione_import.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		case EventoPresaCessioneImport.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, presa_cessione_import.*  "
							+ "FROM evento e left join evento_presa_in_carico_cessione_import presa_cessione_import on (e.id_evento = presa_cessione_import.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		case EventoPrelievoLeishmania.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, prelievo_leish.*  "
							+ "FROM evento e left join evento_prelievo_leish prelievo_leish on (e.id_evento = prelievo_leish.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		
		case EventoBloccoAnimale.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, blocco.*  "
							+ "FROM evento e left join evento_blocco_animale blocco on (e.id_evento = blocco.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		
		case EventoSbloccoAnimale.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, sblocco.*  "
							+ "FROM evento e left join evento_sblocco_animale sblocco on (e.id_evento = sblocco.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		case EventoMutilazione.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, mutilazione.*  "
							+ "FROM evento e left join evento_mutilazione mutilazione on (e.id_evento = mutilazione.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		case EventoAllontanamento.idTipologiaDB: {
			sqlSelect
					.append(" e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento, allontanamento.*  "
							+ "FROM evento e left join evento_allontanamento allontanamento on (e.id_evento = allontanamento.id_evento)  ");
			// sqlFilterAdozioneCanile = createFilterAdozione(db);
			// sqlSelect.append(sqlFilterAdozioneCanile);
			break;
		}
		
		default:
			
			break;
		}

		sqlSelect
				.append(" left join animale a on (e.id_animale = a.id) WHERE e.id_evento >= 0 ");
		// createFilterEvento(db, sqlFilter);

		pst = db.prepareStatement(sqlSelect.toString() + " "
				+ sqlFilter.toString() + " " + sqlOrder.toString());

		int i = 0;
		switch (idTipologiaEvento) {
		case -1: {

			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRegistrazioneBDU.idTipologiaDB))
				i = prepareFilterRegistrazione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSterilizzazione.idTipologiaDB))
				i = prepareFilterSterilizzazione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoDecesso.idTipologiaDB))
				i = prepareFilterDecesso(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoFurto.idTipologiaDB))
				i = prepareFilterFurto(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDB) || tipologieToSet.contains(EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip))
				i = prepareFilterMicrochip(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCattura.idTipologiaDB))
				i = prepareFilterCattura(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamento.idTipologiaDB))
				i = prepareFilterRitrovamento(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRitrovamentoNonDenunciato.idTipologiaDB))
				i = prepareFilterRitrovamentoNonDenunciato(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaCanile.idTipologiaDB))
				i = prepareFilterAdozione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneACanile.idTipologiaDB))
				i = prepareFilterRestituzione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDaColonia.idTipologiaDB))
				i = prepareFilterAdozioneColonia(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaDB) || tipologieToSet.contains(EventoRilascioPassaporto.idTipologiaRinnovoDB))
				i = prepareFilterRilascioPassaporto(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessione.idTipologiaDB) || tipologieToSet.contains(EventoCessione.idTipologiaDB_obsoleto))
				i = prepareFilterCessione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaCessione.idTipologiaDB))
				i = prepareFilterPresaCessione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB)|| tipologieToSet.contains(EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto))
				i = prepareFilterAdozioneFuoriAsl(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB))
				i = prepareFilterPresaAdozioneFuoriAsl(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimento.idTipologiaDB) || tipologieToSet.contains(EventoTrasferimento.idTipologiaDBOperatoreCommerciale))
				i = prepareFilterTrasferimento(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoCanile.idTipologiaDB))
				i = prepareFilterTrasferimentoCanile(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoSindaco.idTipologiaDB))
				i = prepareFilterTrasferimentoSindaco(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegione.idTipologiaDB))
				i = prepareFilterTrasferimentoFuoriRegione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriStato.idTipologiaDB))
				i = prepareFilterTrasferimentoFuoriStato(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB))
				i = prepareFilterTrasferimentoFuoriRegioneSoloProp(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDB)|| tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBRandagio)|| tipologieToSet.contains(EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale))
				i = prepareFilterRientroFuoriRegione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRientroFuoriStato.idTipologiaDB))
				i = prepareFilterRientroFuoriStato(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCambioDetentore.idTipologiaDB))
				i = prepareFilterCambioDetentore(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoRestituzioneAProprietario.idTipologiaDB))
				i = prepareFilterRestituzioneAProprietario(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMorsicatura.idTipologiaDB))
				i = prepareFilterMorsicatura(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAggressione.idTipologiaDB))
				i = prepareFilterAggressione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoEsitoControlli.idTipologiaDB))
				i = prepareFilterEsitoControlli(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoReimmissione.idTipologiaDB))
				i = prepareFilterReimmissione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(26))
				i = prepareFilterCommerciali(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSmarrimento.idTipologiaDB))
				i = prepareFilterSmarrimento(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAdozioneDistanza.idTipologiaDB))
				i = prepareFilterAdozioneDistanza(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoModificaResidenza.idTipologiaDB))
				i = prepareFilterModificaResidenza(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoDNA.idTipologiaDB))
				i = prepareFilterPrelievoDNA(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoCessioneImport.idTipologiaDB))
				i = prepareFilterCessioneImport(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPresaCessioneImport.idTipologiaDB))
				i = prepareFilterPresaCessioneImport(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoPrelievoLeishmania.idTipologiaDB))
				i = prepareFilterPrelievoLeish(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoBloccoAnimale.idTipologiaDB))
				i = prepareFilterBloccoAnimale(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoSbloccoAnimale.idTipologiaDB))
				i = prepareFilterSbloccoAnimale(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoMutilazione.idTipologiaDB))
				i = prepareFilterMutilazione(pst, i);
			if(tipologieToSet==null || tipologieToSet.isEmpty() || tipologieToSet.contains(EventoAllontanamento.idTipologiaDB))
				i = prepareFilterAllontanamento(pst, i);
			i = prepareFilterEvento(pst, i);
			break;
		}

		case EventoRegistrazioneBDU.idTipologiaDB:
			i = prepareFilterRegistrazione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoSterilizzazione.idTipologiaDB:
			i = prepareFilterSterilizzazione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoDecesso.idTipologiaDB:
			i = prepareFilterDecesso(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoFurto.idTipologiaDB:
			i = prepareFilterFurto(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoInserimentoMicrochip.idTipologiaDB:
			i = prepareFilterMicrochip(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoCattura.idTipologiaDB:
			i = prepareFilterCattura(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoCattura.idTipologiaDBRicattura:
			i = prepareFilterCattura(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRitrovamento.idTipologiaDB:
			i = prepareFilterRitrovamento(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRitrovamentoNonDenunciato.idTipologiaDB:
			i = prepareFilterRitrovamentoNonDenunciato(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoAdozioneDaCanile.idTipologiaDB:
			i = prepareFilterAdozione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB:
			i = prepareFilterAdozione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRestituzioneACanile.idTipologiaDB:
			i = prepareFilterRestituzione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoAdozioneDaColonia.idTipologiaDB:
			i = prepareFilterAdozioneColonia(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRilascioPassaporto.idTipologiaDB:
			i = prepareFilterRilascioPassaporto(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRilascioPassaporto.idTipologiaRinnovoDB:
			i = prepareFilterRilascioPassaporto(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoCessione.idTipologiaDB:
			i = prepareFilterCessione(pst, i);
			i = prepareFilterEvento(pst, i);
			break;
		case EventoCessione.idTipologiaDB_obsoleto:
			i = prepareFilterCessione(pst, i);
			i = prepareFilterEvento(pst, i);
			break;
		case EventoPresaInCaricoDaCessione.idTipologiaDB:
			i = prepareFilterPresaCessione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimento.idTipologiaDB:
			i = prepareFilterTrasferimento(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimento.idTipologiaDBOperatoreCommerciale:
			i = prepareFilterTrasferimento(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimentoCanile.idTipologiaDB:
			i = prepareFilterTrasferimentoCanile(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimentoSindaco.idTipologiaDB:
			i = prepareFilterTrasferimentoSindaco(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimentoFuoriRegione.idTipologiaDB:
			i = prepareFilterTrasferimentoFuoriRegione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB:
			i = prepareFilterTrasferimentoFuoriRegioneSoloProp(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoTrasferimentoFuoriStato.idTipologiaDB:
			i = prepareFilterTrasferimentoFuoriStato(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRientroFuoriRegione.idTipologiaDB:
			i = prepareFilterRientroFuoriRegione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRientroFuoriRegione.idTipologiaDBOperatoreCommerciale:
			i = prepareFilterRientroFuoriRegione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRientroFuoriRegione.idTipologiaDBRandagio:
			i = prepareFilterRientroFuoriRegione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRientroFuoriStato.idTipologiaDB:
			i = prepareFilterRientroFuoriStato(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoCambioDetentore.idTipologiaDB:
			i = prepareFilterCambioDetentore(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRestituzioneAProprietario.idTipologiaDB:
			i = prepareFilterRestituzioneAProprietario(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoMorsicatura.idTipologiaDB:
			i = prepareFilterMorsicatura(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoAggressione.idTipologiaDB:
			i = prepareFilterAggressione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoEsitoControlli.idTipologiaDB:
			i = prepareFilterEsitoControlli(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoReimmissione.idTipologiaDB:
			i = prepareFilterReimmissione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB:
			i = prepareFilterCommerciali(pst, i);
			i = prepareFilterEvento(pst, i);
			break;
		case EventoSmarrimento.idTipologiaDB:
			i = prepareFilterSmarrimento(pst, i);
			prepareFilterEvento(pst, i);
			break;
		case EventoAdozioneDistanza.idTipologiaDB:
			i = prepareFilterAdozioneDistanza(pst, i);
			prepareFilterEvento(pst, i);
			break;

		case EventoInserimentoVaccinazioni.idTipologiaDB: {
			i = prepareFilterInserimentoVaccino(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}

		case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip: {
			i = prepareFilterMicrochipSecondo(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		case EventoModificaResidenza.idTipologiaDB: {
			i = prepareFilterModificaResidenza(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		case EventoAdozioneFuoriAsl.idTipologiaDB: {
			i = prepareFilterAdozioneFuoriAsl(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
			i = prepareFilterAdozioneFuoriAsl(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
			i = prepareFilterPresaAdozioneFuoriAsl(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		case EventoPrelievoDNA.idTipologiaDB: {
			i = prepareFilterPrelievoDNA(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoCessioneImport.idTipologiaDB: {
			i = prepareFilterCessioneImport(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoPresaCessioneImport.idTipologiaDB: {
			i = prepareFilterPresaCessioneImport(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoPrelievoLeishmania.idTipologiaDB: {
			i = prepareFilterPrelievoLeish(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
			
		case EventoBloccoAnimale.idTipologiaDB: {
			i = prepareFilterBloccoAnimale(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoSbloccoAnimale.idTipologiaDB: {
			i = prepareFilterSbloccoAnimale(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoMutilazione.idTipologiaDB: {
			i = prepareFilterMutilazione(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		
		case EventoAllontanamento.idTipologiaDB: {
			i = prepareFilterAllontanamento(pst, i);
			prepareFilterEvento(pst, i);
			break;
		}
		}
		i = prepareFilterAnimale(pst, i);

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, pst);
		}
		
		System.out.println("ESECUZIONE QUERY LISTA REGISTRAZIONI OTTIMIZZATA: " + pst.toString());
		rs = pst.executeQuery();

		if (pagedListInfo != null) {
			pagedListInfo.doManualOffset(db, rs);
		}
		return rs;
	}

	/**
	 * Sets the lastAnchor attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new lastAnchor value
	 */
	public void setLastAnchor(java.sql.Timestamp tmp) {
		this.lastAnchor = tmp;
	}

	/**
	 * Sets the lastAnchor attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new lastAnchor value
	 */
	public void setLastAnchor(String tmp) {
		this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
	}

	/**
	 * Sets the nextAnchor attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new nextAnchor value
	 */
	public void setNextAnchor(java.sql.Timestamp tmp) {
		this.nextAnchor = tmp;
	}

	/**
	 * Sets the nextAnchor attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new nextAnchor value
	 */
	public void setNextAnchor(String tmp) {
		this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
	}

	/**
	 * Sets the syncType attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new syncType value
	 */
	public void setSyncType(int tmp) {
		this.syncType = tmp;
	}

	/**
	 * Sets the syncType attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new syncType value
	 */
	public void setSyncType(String tmp) {
		this.syncType = Integer.parseInt(tmp);
	}

	/**
	 * Sets the pagedListInfo attribute of the NewsArticleList object
	 * 
	 * @param tmp
	 *            The new pagedListInfo value
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public String getUniqueField() {
		return uniqueField;
	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public static void setLog(Logger log) {
		EventoList.log = log;
	}

	public int getIdTipologiaEvento() {
		return idTipologiaEvento;
	}

	public void setIdTipologiaEvento(int idTipologiaEvento) {
		this.idTipologiaEvento = idTipologiaEvento;
	}
	
	public String getIdTipologiaEventoString() {
		return idTipologiaEventoString;
	}

	public void setIdTipologiaEventoString(String idTipologiaEventoString) {
		this.idTipologiaEventoString = idTipologiaEventoString;
	}

	public void setIdTipologiaEvento(String idTipologiaEvento) {
		this.idTipologiaEvento = new Integer(idTipologiaEvento).intValue();
		if(this.idTipologiaEvento == EventoAdozioneFuoriAsl.idTipologiaDB)
			this.idTipologiaEventoString = EventoAdozioneFuoriAsl.idTipologiaDB + "," + EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto;
		else if(this.idTipologiaEvento == EventoCessione.idTipologiaDB) 
			this.idTipologiaEventoString = EventoCessione.idTipologiaDB + "," + EventoCessione.idTipologiaDB_obsoleto;
	}

	public EventoList() {
	
	}

	public int getIdSpecieAnimale() {
		return idSpecieAnimale;
	}

	public void setIdSpecieAnimale(int idSpecieAnimale) {
		this.idSpecieAnimale = idSpecieAnimale;
	}

	public void setIdSpecieAnimale(String idSpecieAnimale) {
		this.idSpecieAnimale = new Integer(idSpecieAnimale).intValue();
	}

	public java.sql.Timestamp getData_cambio_detentore() {
		return data_cambio_detentore;
	}

	public void setData_cambio_detentore(
			java.sql.Timestamp data_cambio_detentore) {
		this.data_cambio_detentore = data_cambio_detentore;
	}

	public int getId_detentore_cambio() {
		return id_detentore_cambio;
	}

	public void setId_detentore_cambio(int id_detentore_cambio) {
		this.id_detentore_cambio = id_detentore_cambio;
	}

	public int getId_vecchio_detentore_cambio() {
		return id_vecchio_detentore_cambio;
	}

	public void setId_vecchio_detentore_cambio(int id_vecchio_detentore_cambio) {
		this.id_vecchio_detentore_cambio = id_vecchio_detentore_cambio;
	}

	public java.sql.Timestamp getData_morso() {
		return data_morso;
	}

	public void setData_morso(java.sql.Timestamp data_morso) {
		this.data_morso = data_morso;
	}

	public java.sql.Timestamp getData_esito() {
		return data_esito;
	}

	public void setData_esito(java.sql.Timestamp data_esito) {
		this.data_esito = data_esito;
	}

	public boolean isFlag_ehrlichiosi() {
		return flag_ehrlichiosi;
	}

	public void setFlag_ehrlichiosi(boolean flag_ehrlichiosi) {
		this.flag_ehrlichiosi = flag_ehrlichiosi;
	}

	public java.sql.Timestamp getData_ehrlichiosi() {
		return data_ehrlichiosi;
	}

	public void setData_ehrlichiosi(java.sql.Timestamp data_ehrlichiosi) {
		this.data_ehrlichiosi = data_ehrlichiosi;
	}

	public int getEsito_ehrlichiosi() {
		return esito_ehrlichiosi;
	}

	public void setEsito_ehrlichiosi(int esito_ehrlichiosi) {
		this.esito_ehrlichiosi = esito_ehrlichiosi;
	}

	public boolean isFlag_rickettiosi() {
		return flag_rickettiosi;
	}

	public void setFlag_rickettiosi(boolean flag_rickettiosi) {
		this.flag_rickettiosi = flag_rickettiosi;
	}

	public java.sql.Timestamp getData_rickettiosi() {
		return data_rickettiosi;
	}

	public void setData_rickettiosi(java.sql.Timestamp data_rickettiosi) {
		this.data_rickettiosi = data_rickettiosi;
	}

	public int getEsito_rickettiosi() {
		return esito_rickettiosi;
	}

	public void setEsito_rickettiosi(int esito_rickettiosi) {
		this.esito_rickettiosi = esito_rickettiosi;
	}

	public java.sql.Timestamp getData_reimmissione() {
		return data_reimmissione;
	}

	public void setData_reimmissione(java.sql.Timestamp data_reimmissione) {
		this.data_reimmissione = data_reimmissione;
	}

	public int getId_comune_reimmissione() {
		return id_comune_reimmissione;
	}

	public void setId_comune_reimmissione(int id_comune_reimmissione) {
		this.id_comune_reimmissione = id_comune_reimmissione;
	}

	public String getLuogo_reimmissione() {
		return luogo_reimmissione;
	}

	public void setLuogo_reimmissione(String luogo_reimmissione) {
		this.luogo_reimmissione = luogo_reimmissione;
	}

	public java.sql.Timestamp getData_ricattura() {
		return data_ricattura;
	}

	public void setData_ricattura(java.sql.Timestamp data_ricattura) {
		this.data_ricattura = data_ricattura;
	}

	public int getId_comune_ricattura() {
		return id_comune_ricattura;
	}

	public void setId_comune_ricattura(int id_comune_ricattura) {
		this.id_comune_ricattura = id_comune_ricattura;
	}

	public int getId_animale() {
		return id_animale;
	}

	public void setId_animale(int id_animale) {
		this.id_animale = id_animale;
	}

	public java.sql.Timestamp getEntered() {
		return entered;
	}

	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public java.sql.Timestamp getModified() {
		return modified;
	}

	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public int getId_utente_inserimento() {
		return id_utente_inserimento;
	}

	public void setId_utente_inserimento(int id_utente_inserimento) {
		this.id_utente_inserimento = id_utente_inserimento;
	}

	public int getId_utente_modifica() {
		return id_utente_modifica;
	}

	public void setId_utente_modifica(int id_utente_modifica) {
		this.id_utente_modifica = id_utente_modifica;
	}

	public java.sql.Timestamp getData_evento() {
		return data_evento;
	}

	public void setData_evento(java.sql.Timestamp data_evento) {
		this.data_evento = data_evento;
	}

	public java.sql.Timestamp getData_registrazione() {
		return data_registrazione;
	}

	public void setData_registrazione(java.sql.Timestamp data_registrazione) {
		this.data_registrazione = data_registrazione;
	}

	public int getId_asl_registrazione() {
		return id_asl_registrazione;
	}

	public void setId_asl_registrazione(int id_asl_registrazione) {
		this.id_asl_registrazione = id_asl_registrazione;
	}

	public java.sql.Timestamp getData_inserimento_microchip() {
		return data_inserimento_microchip;
	}

	public void setData_inserimento_microchip(
			java.sql.Timestamp data_inserimento_microchip) {
		this.data_inserimento_microchip = data_inserimento_microchip;
	}

	public String getNumero_microchip_assegnato() {
		return numero_microchip_assegnato;
	}

	public void setNumero_microchip_assegnato(String numero_microchip_assegnato) {
		this.numero_microchip_assegnato = numero_microchip_assegnato;
	}

	public int getId_veterinario_privato_inserimento_microchip() {
		return id_veterinario_privato_inserimento_microchip;
	}

	public void setId_veterinario_privato_inserimento_microchip(
			int id_veterinario_privato_inserimento_microchip) {
		this.id_veterinario_privato_inserimento_microchip = id_veterinario_privato_inserimento_microchip;
	}

	public java.sql.Timestamp getData_inserimento_secondo_microchip() {
		return data_inserimento_secondo_microchip;
	}

	public void setData_inserimento_secondo_microchip(
			java.sql.Timestamp data_inserimento_secondo_microchip) {
		this.data_inserimento_secondo_microchip = data_inserimento_secondo_microchip;
	}

	public String getNumero_secondo_microchip_assegnato() {
		return numero_secondo_microchip_assegnato;
	}

	public void setNumero_secondo_microchip_assegnato(
			String numero_secondo_microchip_assegnato) {
		this.numero_secondo_microchip_assegnato = numero_secondo_microchip_assegnato;
	}

	public java.sql.Timestamp getData_sterilizzazione() {
		return data_sterilizzazione;
	}

	public void setData_sterilizzazione(java.sql.Timestamp data_sterilizzazione) {
		this.data_sterilizzazione = data_sterilizzazione;
	}

	public boolean isFlag_richiesta_contributo_regionale() {
		return flag_richiesta_contributo_regionale;
	}

	public void setFlag_richiesta_contributo_regionale(
			boolean flag_richiesta_contributo_regionale) {
		this.flag_richiesta_contributo_regionale = flag_richiesta_contributo_regionale;
	}

	public int getId_progetto_di_sterilizzazione_richiesto() {
		return id_progetto_di_sterilizzazione_richiesto;
	}

	public void setId_progetto_di_sterilizzazione_richiesto(
			int id_progetto_di_sterilizzazione_richiesto) {
		this.id_progetto_di_sterilizzazione_richiesto = id_progetto_di_sterilizzazione_richiesto;
	}

	public int getTipologia_soggetto_sterilizzante() {
		return tipologia_soggetto_sterilizzante;
	}

	public void setTipologia_soggetto_sterilizzante(
			int tipologia_soggetto_sterilizzante) {
		this.tipologia_soggetto_sterilizzante = tipologia_soggetto_sterilizzante;
	}

	public int getId_soggetto_sterilizzante() {
		return id_soggetto_sterilizzante;
	}

	public void setId_soggetto_sterilizzante(int id_soggetto_sterilizzante) {
		this.id_soggetto_sterilizzante = id_soggetto_sterilizzante;
	}

	public java.sql.Timestamp getData_cattura() {
		return data_cattura;
	}

	public void setData_cattura(java.sql.Timestamp data_cattura) {
		this.data_cattura = data_cattura;
	}

	public int getId_comune_cattura() {
		return id_comune_cattura;
	}

	public void setId_comune_cattura(int id_comune_cattura) {
		this.id_comune_cattura = id_comune_cattura;
	}

	public String getIndirizzo_cattura() {
		return indirizzo_cattura;
	}

	public void setIndirizzo_cattura(String indirizzo_cattura) {
		this.indirizzo_cattura = indirizzo_cattura;
	}

	public String getVerbale_cattura() {
		return verbale_cattura;
	}

	public void setVerbale_cattura(String verbale_cattura) {
		this.verbale_cattura = verbale_cattura;
	}

	public String getLuogo_cattura() {
		return luogo_cattura;
	}

	public void setLuogo_cattura(String luogo_cattura) {
		this.luogo_cattura = luogo_cattura;
	}

	public java.sql.Timestamp getData_rilascio_passaporto() {
		return data_rilascio_passaporto;
	}

	public void setData_rilascio_passaporto(
			java.sql.Timestamp data_rilascio_passaporto) {
		this.data_rilascio_passaporto = data_rilascio_passaporto;
	}

	public String getNumero_passaporto() {
		return numero_passaporto;
	}

	public void setNumero_passaporto(String numero_passaporto) {
		this.numero_passaporto = numero_passaporto;
	}

	public java.sql.Timestamp getData_decesso() {
		return data_decesso;
	}

	public void setData_decesso(java.sql.Timestamp data_decesso) {
		this.data_decesso = data_decesso;
	}

	public int getId_causa_decesso() {
		return id_causa_decesso;
	}

	public void setId_causa_decesso(int id_causa_decesso) {
		this.id_causa_decesso = id_causa_decesso;
	}

	public boolean isFlag_decesso_presunto() {
		return flag_decesso_presunto;
	}

	public void setFlag_decesso_presunto(boolean flag_decesso_presunto) {
		this.flag_decesso_presunto = flag_decesso_presunto;
	}

	public String getLuogo_decesso() {
		return luogo_decesso;
	}

	public void setLuogo_decesso(String luogo_decesso) {
		this.luogo_decesso = luogo_decesso;
	}

	public java.sql.Timestamp getData_smarrimento() {
		return data_smarrimento;
	}

	public void setData_smarrimento(java.sql.Timestamp data_smarrimento) {
		this.data_smarrimento = data_smarrimento;
	}

	public boolean isFlag_presenza_importo_smarrimento() {
		return flag_presenza_importo_smarrimento;
	}

	public void setFlag_presenza_importo_smarrimento(
			boolean flag_presenza_importo_smarrimento) {
		this.flag_presenza_importo_smarrimento = flag_presenza_importo_smarrimento;
	}

	public double getImporto_smarrimento() {
		return importo_smarrimento;
	}

	public void setImporto_smarrimento(double importo_smarrimento) {
		this.importo_smarrimento = importo_smarrimento;
	}

	public String getLuogo_smarrimento() {
		return luogo_smarrimento;
	}

	public void setLuogo_smarrimento(String luogo_smarrimento) {
		this.luogo_smarrimento = luogo_smarrimento;
	}

	public java.sql.Timestamp getData_ritrovamento() {
		return data_ritrovamento;
	}

	public void setData_ritrovamento(java.sql.Timestamp data_ritrovamento) {
		this.data_ritrovamento = data_ritrovamento;
	}

	public int getId_comune_ritrovamento() {
		return id_comune_ritrovamento;
	}

	public void setId_comune_ritrovamento(int id_comune_ritrovamento) {
		this.id_comune_ritrovamento = id_comune_ritrovamento;
	}

	public String getLuogo_ritrovamento() {
		return luogo_ritrovamento;
	}

	public void setLuogo_ritrovamento(String luogo_ritrovamento) {
		this.luogo_ritrovamento = luogo_ritrovamento;
	}

	public int getId_nuovo_proprietario() {
		return id_nuovo_proprietario;
	}

	public void setId_nuovo_proprietario(int id_nuovo_proprietario) {
		this.id_nuovo_proprietario = id_nuovo_proprietario;
	}

	public int getId_detentore() {
		return id_detentore;
	}

	public void setId_detentore(int id_detentore) {
		this.id_detentore = id_detentore;
	}

	public java.sql.Timestamp getData_cessione() {
		return data_cessione;
	}

	public void setData_cessione(java.sql.Timestamp data_cessione) {
		this.data_cessione = data_cessione;
	}

	public int getNuovo_proprietario() {
		return nuovo_proprietario;
	}

	public void setNuovo_proprietario(int nuovo_proprietario) {
		this.nuovo_proprietario = nuovo_proprietario;
	}

	public String getLuogo_adozione() {
		return luogo_adozione;
	}

	public void setLuogo_adozione(String luogo_adozione) {
		this.luogo_adozione = luogo_adozione;
	}

	public int getId_proprietario_prima_adozione() {
		return id_proprietario_prima_adozione;
	}

	public void setId_proprietario_prima_adozione(
			int id_proprietario_prima_adozione) {
		this.id_proprietario_prima_adozione = id_proprietario_prima_adozione;
	}

	public java.sql.Timestamp getData_restituzione_canile() {
		return data_restituzione_canile;
	}

	public void setData_restituzione_canile(
			java.sql.Timestamp data_restituzione_canile) {
		this.data_restituzione_canile = data_restituzione_canile;
	}

	public int getId_canile() {
		return id_canile;
	}

	public void setId_canile(int id_canile) {
		this.id_canile = id_canile;
	}

	public int getId_proprietario_da_restituzione() {
		return id_proprietario_da_restituzione;
	}

	public void setId_proprietario_da_restituzione(
			int id_proprietario_da_restituzione) {
		this.id_proprietario_da_restituzione = id_proprietario_da_restituzione;
	}

	public int getId_vecchio_proprietario() {
		return id_vecchio_proprietario;
	}

	public void setId_vecchio_proprietario(int id_vecchio_proprietario) {
		this.id_vecchio_proprietario = id_vecchio_proprietario;
	}

	public int getId_asl_vecchio_proprietario() {
		return id_asl_vecchio_proprietario;
	}

	public void setId_asl_vecchio_proprietario(int id_asl_vecchio_proprietario) {
		this.id_asl_vecchio_proprietario = id_asl_vecchio_proprietario;
	}

	public int getId_asl_nuovo_proprietario() {
		return id_asl_nuovo_proprietario;
	}

	public void setId_asl_nuovo_proprietario(int id_asl_nuovo_proprietario) {
		this.id_asl_nuovo_proprietario = id_asl_nuovo_proprietario;
	}

	public java.sql.Timestamp getData_presa_in_carico_da_cessione() {
		return data_presa_in_carico_da_cessione;
	}

	public void setData_presa_in_carico_da_cessione(
			java.sql.Timestamp data_presa_in_carico_da_cessione) {
		this.data_presa_in_carico_da_cessione = data_presa_in_carico_da_cessione;
	}

	public int getId_nuovo_proprietario_cessione() {
		return id_nuovo_proprietario_cessione;
	}

	public void setId_nuovo_proprietario_cessione(
			int id_nuovo_proprietario_cessione) {
		this.id_nuovo_proprietario_cessione = id_nuovo_proprietario_cessione;
	}

	/*
	 * public int getId_vecchio_proprietario_cessione() { return
	 * id_vecchio_proprietario_cessione; }
	 * 
	 * 
	 * public void setId_vecchio_proprietario_cessione( int
	 * id_vecchio_proprietario_cessione) { this.id_vecchio_proprietario_cessione
	 * = id_vecchio_proprietario_cessione; }
	 * 
	 * 
	 * public int getId_asl_vecchio_proprietario_cessione() { return
	 * id_asl_vecchio_proprietario_cessione; }
	 * 
	 * 
	 * public void setId_asl_vecchio_proprietario_cessione( int
	 * id_asl_vecchio_proprietario_cessione) {
	 * this.id_asl_vecchio_proprietario_cessione =
	 * id_asl_vecchio_proprietario_cessione; }
	 */

	public int getId_asl_nuovo_proprietario_cessione() {
		return id_asl_nuovo_proprietario_cessione;
	}

	public void setId_asl_nuovo_proprietario_cessione(
			int id_asl_nuovo_proprietario_cessione) {
		this.id_asl_nuovo_proprietario_cessione = id_asl_nuovo_proprietario_cessione;
	}

	public java.sql.Timestamp getData_trasferimento() {
		return data_trasferimento;
	}

	public void setData_trasferimento(java.sql.Timestamp data_trasferimento) {
		this.data_trasferimento = data_trasferimento;
	}

	public int getId_nuovo_proprietario_trasferimento() {
		return id_nuovo_proprietario_trasferimento;
	}

	public void setId_nuovo_proprietario_trasferimento(
			int id_nuovo_proprietario_trasferimento) {
		this.id_nuovo_proprietario_trasferimento = id_nuovo_proprietario_trasferimento;
	}

	public int getId_vecchio_proprietario_trasferimento() {
		return id_vecchio_proprietario_trasferimento;
	}

	public void setId_vecchio_proprietario_trasferimento(
			int id_vecchio_proprietario_trasferimento) {
		this.id_vecchio_proprietario_trasferimento = id_vecchio_proprietario_trasferimento;
	}

	public String getLuogo_trasferimento() {
		return luogo_trasferimento;
	}

	public void setLuogo_trasferimento(String luogo_trasferimento) {
		this.luogo_trasferimento = luogo_trasferimento;
	}

	public int getId_comune_nuovo_proprietario_trasferimento() {
		return id_comune_nuovo_proprietario_trasferimento;
	}

	public void setId_comune_nuovo_proprietario_trasferimento(
			int id_comune_nuovo_proprietario_trasferimento) {
		this.id_comune_nuovo_proprietario_trasferimento = id_comune_nuovo_proprietario_trasferimento;
	}

	public int getId_nuovo_detentore_trasferimento() {
		return id_nuovo_detentore_trasferimento;
	}

	public void setId_nuovo_detentore_trasferimento(
			int id_nuovo_detentore_trasferimento) {
		this.id_nuovo_detentore_trasferimento = id_nuovo_detentore_trasferimento;
	}

	public int getId_vecchio_detentore_trasferimento() {
		return id_vecchio_detentore_trasferimento;
	}

	public void setId_vecchio_detentore_trasferimento(
			int id_vecchio_detentore_trasferimento) {
		this.id_vecchio_detentore_trasferimento = id_vecchio_detentore_trasferimento;
	}

	public java.sql.Timestamp getData_trasferimento_fuori_regione() {
		return data_trasferimento_fuori_regione;
	}

	public void setData_trasferimento_fuori_regione(
			java.sql.Timestamp data_trasferimento_fuori_regione) {
		this.data_trasferimento_fuori_regione = data_trasferimento_fuori_regione;
	}

	public int getId_nuovo_proprietario_trasf_fuori_regione() {
		return id_nuovo_proprietario_trasf_fuori_regione;
	}

	public void setId_nuovo_proprietario_trasf_fuori_regione(
			int id_nuovo_proprietario_trasf_fuori_regione) {
		this.id_nuovo_proprietario_trasf_fuori_regione = id_nuovo_proprietario_trasf_fuori_regione;
	}

	public int getId_vecchio_proprietario_trasf_fuori_regione() {
		return id_vecchio_proprietario_trasf_fuori_regione;
	}

	public void setId_vecchio_proprietario_trasf_fuori_regione(
			int id_vecchio_proprietario_trasf_fuori_regione) {
		this.id_vecchio_proprietario_trasf_fuori_regione = id_vecchio_proprietario_trasf_fuori_regione;
	}

	public int getId_asl_vecchio_proprietario_trasf_fuori_regione() {
		return id_asl_vecchio_proprietario_trasf_fuori_regione;
	}

	public void setId_asl_vecchio_proprietario_trasf_fuori_regione(
			int id_asl_vecchio_proprietario_trasf_fuori_regione) {
		this.id_asl_vecchio_proprietario_trasf_fuori_regione = id_asl_vecchio_proprietario_trasf_fuori_regione;
	}

	public int getId_asl_nuovo_proprietario_trasf_fuori_regione() {
		return id_asl_nuovo_proprietario_trasf_fuori_regione;
	}

	public void setId_asl_nuovo_proprietario_trasf_fuori_regione(
			int id_asl_nuovo_proprietario_trasf_fuori_regione) {
		this.id_asl_nuovo_proprietario_trasf_fuori_regione = id_asl_nuovo_proprietario_trasf_fuori_regione;
	}

	public int getId_regione_trasferimento_a() {
		return id_regione_trasferimento_a;
	}

	public void setId_regione_trasferimento_a(int id_regione_trasferimento_a) {
		this.id_regione_trasferimento_a = id_regione_trasferimento_a;
	}

	public int getId_vecchio_detentore() {
		return id_vecchio_detentore;
	}

	public void setId_vecchio_detentore(int id_vecchio_detentore) {
		this.id_vecchio_detentore = id_vecchio_detentore;
	}

	public String getLuogo_trasf_fuori_regione() {
		return luogo_trasf_fuori_regione;
	}

	public void setLuogo_trasf_fuori_regione(String luogo_trasf_fuori_regione) {
		this.luogo_trasf_fuori_regione = luogo_trasf_fuori_regione;
	}

	public java.sql.Timestamp getData_rientro_da_fuori_regione() {
		return data_rientro_da_fuori_regione;
	}

	public void setData_rientro_da_fuori_regione(
			java.sql.Timestamp data_rientro_da_fuori_regione) {
		this.data_rientro_da_fuori_regione = data_rientro_da_fuori_regione;
	}

	public int getId_nuovo_proprietario_rientro_f_regione() {
		return id_nuovo_proprietario_rientro_f_regione;
	}

	public void setId_nuovo_proprietario_rientro_f_regione(
			int id_nuovo_proprietario_rientro_f_regione) {
		this.id_nuovo_proprietario_rientro_f_regione = id_nuovo_proprietario_rientro_f_regione;
	}

	public String getLuogo_rientro_f_regione() {
		return luogo_rientro_f_regione;
	}

	public void setLuogo_rientro_f_regione(String luogo_rientro_f_regione) {
		this.luogo_rientro_f_regione = luogo_rientro_f_regione;
	}

	public int getId_nuovo_detentore_rientro_f_regione() {
		return id_nuovo_detentore_rientro_f_regione;
	}

	public void setId_nuovo_detentore_rientro_f_regione(
			int id_nuovo_detentore_rientro_f_regione) {
		this.id_nuovo_detentore_rientro_f_regione = id_nuovo_detentore_rientro_f_regione;
	}

	public int getId_regione_rientro_da() {
		return id_regione_rientro_da;
	}

	public void setId_regione_rientro_da(int id_regione_rientro_da) {
		this.id_regione_rientro_da = id_regione_rientro_da;
	}

	public int getId_vecchio_proprietario_rientro_f_regione() {
		return id_vecchio_proprietario_rientro_f_regione;
	}

	public void setId_vecchio_proprietario_rientro_f_regione(
			int id_vecchio_proprietario_rientro_f_regione) {
		this.id_vecchio_proprietario_rientro_f_regione = id_vecchio_proprietario_rientro_f_regione;
	}

	public java.sql.Timestamp getData_furto() {
		return data_furto;
	}

	public void setData_furto(java.sql.Timestamp data_furto) {
		this.data_furto = data_furto;
	}

	public String getLuogo_furto() {
		return luogo_furto;
	}

	public void setLuogo_furto(String luogo_furto) {
		this.luogo_furto = luogo_furto;
	}

	public String getDati_denuncia() {
		return dati_denuncia;
	}

	public void setDati_denuncia(String dati_denuncia) {
		this.dati_denuncia = dati_denuncia;
	}

	public java.sql.Timestamp getData_adozione_colonia() {
		return data_adozione_colonia;
	}

	public void setData_adozione_colonia(
			java.sql.Timestamp data_adozione_colonia) {
		this.data_adozione_colonia = data_adozione_colonia;
	}

	public int getId_colonia() {
		return id_colonia;
	}

	public void setId_colonia(int id_colonia) {
		this.id_colonia = id_colonia;
	}

	public int getId_comune_destinatario_adozione_colonia() {
		return id_comune_destinatario_adozione_colonia;
	}

	public void setId_comune_destinatario_adozione_colonia(
			int id_comune_destinatario_adozione_colonia) {
		this.id_comune_destinatario_adozione_colonia = id_comune_destinatario_adozione_colonia;
	}

	public String getLuogo_adozione_colonia() {
		return luogo_adozione_colonia;
	}

	public void setLuogo_adozione_colonia(String luogo_adozione_colonia) {
		this.luogo_adozione_colonia = luogo_adozione_colonia;
	}

	public int getId_nuovo_proprietario_adozione_colonia() {
		return id_nuovo_proprietario_adozione_colonia;
	}

	public void setId_nuovo_proprietario_adozione_colonia(
			int id_nuovo_proprietario_adozione_colonia) {
		this.id_nuovo_proprietario_adozione_colonia = id_nuovo_proprietario_adozione_colonia;
	}

	public int getId_asl_destinazione_adozione_colonia() {
		return id_asl_destinazione_adozione_colonia;
	}

	public void setId_asl_destinazione_adozione_colonia(
			int id_asl_destinazione_adozione_colonia) {
		this.id_asl_destinazione_adozione_colonia = id_asl_destinazione_adozione_colonia;
	}

	public int getId_colonia_provenienza() {
		return id_colonia_provenienza;
	}

	public void setId_colonia_provenienza(int id_colonia_provenienza) {
		this.id_colonia_provenienza = id_colonia_provenienza;
	}

	public java.sql.Timestamp getData_trasferimento_canile() {
		return data_trasferimento_canile;
	}

	public void setData_trasferimento_canile(
			java.sql.Timestamp data_trasferimento_canile) {
		this.data_trasferimento_canile = data_trasferimento_canile;
	}

	public java.sql.Timestamp getData_adozione_distanza() {
		return data_adozione_distanza;
	}

	public void setData_adozione_distanza(
			java.sql.Timestamp data_adozione_distanza) {
		this.data_adozione_distanza = data_adozione_distanza;
	}

	public java.sql.Timestamp getData_adozione_canile() {
		return data_adozione_canile;
	}

	public void setData_adozione_canile(java.sql.Timestamp data_adozione_canile) {
		this.data_adozione_canile = data_adozione_canile;
	}

	public int getId_comune_destinatario_adozione_canile() {
		return id_comune_destinatario_adozione_canile;
	}

	public void setId_comune_destinatario_adozione_canile(
			int id_comune_destinatario_adozione_canile) {
		this.id_comune_destinatario_adozione_canile = id_comune_destinatario_adozione_canile;
	}

	public int getId_asl_destinazione_adozione() {
		return id_asl_destinazione_adozione;
	}

	public void setId_asl_destinazione_adozione(int id_asl_destinazione_adozione) {
		this.id_asl_destinazione_adozione = id_asl_destinazione_adozione;
	}

	public int getId_nuovo_proprietario_adozione_canile() {
		return id_nuovo_proprietario_adozione_canile;
	}

	public void setId_nuovo_proprietario_adozione_canile(
			int id_nuovo_proprietario_adozione_canile) {
		this.id_nuovo_proprietario_adozione_canile = id_nuovo_proprietario_adozione_canile;
	}

	public int getId_canile_provenienza() {
		return id_canile_provenienza;
	}

	public void setId_canile_provenienza(int id_canile_provenienza) {
		this.id_canile_provenienza = id_canile_provenienza;
	}

	public java.sql.Timestamp getData_adozione_fuori_asl() {
		return data_adozione_fuori_asl;
	}

	public void setData_adozione_fuori_asl(
			java.sql.Timestamp data_adozione_fuori_asl) {
		this.data_adozione_fuori_asl = data_adozione_fuori_asl;
	}

	public int getId_comune_destinatario_adozione_fuori_asl() {
		return id_comune_destinatario_adozione_fuori_asl;
	}

	public void setId_comune_destinatario_adozione_fuori_asl(
			int id_comune_destinatario_adozione_fuori_asl) {
		this.id_comune_destinatario_adozione_fuori_asl = id_comune_destinatario_adozione_fuori_asl;
	}

	public int getId_comune_cedente() {
		return id_comune_cedente;
	}

	public void setId_comune_cedente(int id_comune_cedente) {
		this.id_comune_cedente = id_comune_cedente;
	}

	public int getId_asl_cedente() {
		return id_asl_cedente;
	}

	public void setId_asl_cedente(int id_asl_cedente) {
		this.id_asl_cedente = id_asl_cedente;
	}

	public int getId_canile_provenienza_fuori_asl() {
		return id_canile_provenienza_fuori_asl;
	}

	public void setId_canile_provenienza_fuori_asl(
			int id_canile_provenienza_fuori_asl) {
		this.id_canile_provenienza_fuori_asl = id_canile_provenienza_fuori_asl;
	}

	public int getId_nuovo_proprietario_adozione_fuori_asl() {
		return id_nuovo_proprietario_adozione_fuori_asl;
	}

	public void setId_nuovo_proprietario_adozione_fuori_asl(
			int id_nuovo_proprietario_adozione_fuori_asl) {
		this.id_nuovo_proprietario_adozione_fuori_asl = id_nuovo_proprietario_adozione_fuori_asl;
	}

	public java.sql.Timestamp getData_vaccino_antirabbico() {
		return data_vaccino_antirabbico;
	}

	public void setData_vaccino_antirabbico(
			java.sql.Timestamp data_vaccino_antirabbico) {
		this.data_vaccino_antirabbico = data_vaccino_antirabbico;
	}

	public String getLotto_vaccino() {
		return lotto_vaccino;
	}

	public void setLotto_vaccino(String lotto_vaccino) {
		this.lotto_vaccino = lotto_vaccino;
	}

	public java.sql.Timestamp getData_cattura_2() {
		return data_cattura_2;
	}

	public void setData_cattura_2(java.sql.Timestamp data_cattura_2) {
		this.data_cattura_2 = data_cattura_2;
	}

	public int getId_comune_cattura_2() {
		return id_comune_cattura_2;
	}

	public void setId_comune_cattura_2(int id_comune_cattura_2) {
		this.id_comune_cattura_2 = id_comune_cattura_2;
	}

	public String getIndirizzo_cattura_2() {
		return indirizzo_cattura_2;
	}

	public void setIndirizzo_cattura_2(String indirizzo_cattura_2) {
		this.indirizzo_cattura_2 = indirizzo_cattura_2;
	}

	public String getVerbale_cattura_2() {
		return verbale_cattura_2;
	}

	public void setVerbale_cattura_2(String verbale_cattura_2) {
		this.verbale_cattura_2 = verbale_cattura_2;
	}

	public String getLuogo_cattura_2() {
		return luogo_cattura_2;
	}

	public void setLuogo_cattura_2(String luogo_cattura_2) {
		this.luogo_cattura_2 = luogo_cattura_2;
	}

	/**
	 * @return the tablename
	 */
	public static String getTablename() {
		return tableName;
	}

	/**
	 * @return the uniquefield
	 */
	public static String getUniquefield() {
		return uniqueField;
	}

	/**
	 * @return the lastAnchor
	 */
	public java.sql.Timestamp getLastAnchor() {
		return lastAnchor;
	}

	/**
	 * @return the nextAnchor
	 */
	public java.sql.Timestamp getNextAnchor() {
		return nextAnchor;
	}

	/**
	 * @return the syncType
	 */
	public int getSyncType() {
		return syncType;
	}

	/**
	 * @return the pagedListInfo
	 */
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = new Integer(id).intValue();
	}

	public java.sql.Timestamp getBDUa() {
		return BDUa;
	}

	public void setBDUa(java.sql.Timestamp ua) {
		BDUa = ua;
	}

	public java.sql.Timestamp getBDUda() {
		return BDUda;
	}

	public void setBDUda(java.sql.Timestamp uda) {
		BDUda = uda;
	}

	public void setBDUda(String uda) {
		BDUda = DateUtils.parseDateStringNew(uda, "dd/MM/yyyy");
	}

	public java.sql.Timestamp getEventoda() {
		return eventoda;
	}

	public void setEventoda(java.sql.Timestamp eventoda) {
		this.eventoda = eventoda;
	}

	public void setEventoda(String eventoda) {
		this.eventoda = DateUtils.parseDateStringNew(eventoda, "dd/MM/yyyy");
	}

	public java.sql.Timestamp getEventoa() {
		return eventoa;
	}

	public void setEventoa(java.sql.Timestamp eventoa) {
		this.eventoa = eventoa;
	}

	public void setEventoa(String eventoa) {
		this.eventoa = DateUtils.parseDateStringNew(eventoa, "dd/MM/yyyy");
	}

	public Boolean getMinerOnly() {
		return minerOnly;
	}

	public void setMinerOnly(Boolean minerOnly) {
		this.minerOnly = minerOnly;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public boolean isCheckFlagRichiestaContributo() {
		return checkFlagRichiestaContributo;
	}

	public void setCheckFlagRichiestaContributo(
			boolean checkFlagRichiestaContributo) {
		this.checkFlagRichiestaContributo = checkFlagRichiestaContributo;
	}

	public void setStageId(String tmp) {
		if (tmp != null) {
			this.stageId = Integer.parseInt(tmp);
		} else {
			this.stageId = -1;
		}
	}

	public void setId_nuovo_proprietario_nd(int id_nuovo_proprietario_nd) {
		this.id_nuovo_proprietario_nd = id_nuovo_proprietario_nd;
	}
	public void setId_nuovo_proprietario_nd(String id_nuovo_proprietario_nd) {
		this.id_nuovo_proprietario_nd = Integer.parseInt(id_nuovo_proprietario_nd);
	}

	public int getId_nuovo_proprietario_nd() {
		return id_nuovo_proprietario_nd;
	}

	public void setId_detentore_nd(int id_detentore_nd) {
		this.id_detentore_nd = id_detentore_nd;
	}

	public void setId_detentore_nd(String id_detentore_nd) {
		this.id_detentore_nd = Integer.parseInt(id_detentore_nd);
	}
	public int getId_detentore_nd() {
		return id_detentore_nd;
	}
	public java.sql.Timestamp getData_restituzione() {
		return data_restituzione;
	}

	public void setData_restituzione(
			java.sql.Timestamp data_restituzione) {
		this.data_restituzione = data_restituzione;
	}
	
	
	public void getTipologieRegistrazioniAnimale(Connection db, int idAnimale) throws SQLException 
	{

		PreparedStatement pst = db.prepareStatement("select distinct id_tipologia_evento from evento where trashed_date is null and data_cancellazione is null and id_animale = ? ");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while(rs.next()) 
			tipologieToSet.add(rs.getInt("id_tipologia_evento"));

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}
	
	
	
	
}
