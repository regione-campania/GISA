/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.aspcfs.modules.macellazioninewopu.actions.Macellazioni;
import org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninewopu.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninewopu.utils.ReflectionUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

public class GenericBean {
	private static final long serialVersionUID = 8313006891554941893L;

	// campi usati per immagazzinare i dati delle tabelel in join al fine di
	// contenere
	// tutte le informazioni del capo macellato nello storico (campioni, organi,
	// non conformita' etc..
	// NB. campi pubblici senza metodi get e set
	public ArrayList<NonConformita> storico_vam_non_conformita;
	public ArrayList<Organi> storico_lcso_organi;
	public ArrayList<PatologiaRilevata> storico_vpm_patologie_rilevate;
	public ArrayList<Campione> storico_vpm_campioni;

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;

	private int id;
	private int id_macello;
	private String stato_macellazione;
	private int cd_seduta_macellazione;
	// -->> CONTROLLO DOCUMENTALE <<--
	// provenienza
	private int cd_provenienza_stato;
	private boolean cd_prov_stato_comunitario;
	private int cd_provenienza_regione;
	private String cd_provenienza_comune;
	private String cd_speditore;
	private String cd_codice_speditore;
	private int cd_id_speditore;
	private String cd_veterinario_1;
	private String cd_veterinario_2;
	private String cd_veterinario_3;
	//
	private String mavam_descrizione_luogo_verifica;
	private boolean casl_to_asl_origine;
	private boolean casl_to_proprietario_animale;
	private boolean casl_to_azienda_origine;
	private boolean casl_to_proprietario_macello;
	private boolean casl_to_pif;
	private boolean casl_to_uvac;
	private boolean casl_to_regione;
	private boolean casl_to_altro;
	private String casl_to_altro_testo;
	private String casl_note_prevvedimento;
	private Timestamp casl_data;
	private String casl_info_richiesta;

	private String cd_codice_azienda_provenienza;
	private String cd_info_azienda_provenienza;

	// proprietario
	private String cd_codice_azienda;
	// animale
	private int cd_asl;
	private boolean cd_vincolo_sanitario;
	private String cd_vincolo_sanitario_motivo;
	private int cd_macellazione_differita;
	private boolean cd_info_catena_alimentare;
	private String cd_mod4;
	private Timestamp cd_data_mod4;
	private Timestamp cd_data_arrivo_macello;
	// FLAG DICHIARATA DAL GESTORE
	private boolean cd_data_arrivo_macello_flag_dichiarata;

	private String cd_tipo_mezzo_trasporto;
	private String cd_targa_mezzo_trasporto;
	private boolean cd_trasporto_superiore8ore;

	// note
	private String cd_note;
	private boolean solo_cd = true;
	private boolean manca_BSE_Nmesi = false;
	private boolean articolo17 = false;

	private boolean modello10 = false;

	// -->> TEST BSE <<--
	private Timestamp bse_data_prelievo;
	private Timestamp bse_data_ricezione_esito;
	private String bse_esito;
	private String bse_note;

	// -->> VISITA ANTE MORTEM <<--
	private Timestamp vam_data;
	// private boolean vam_favorevole;
	// private boolean vam_macellazione_con_osservazione;
	private int vam_provvedimenti;
	private String vam_provvedimenti_note;
	private String vam_esito;
	private boolean vam_to_asl_origine;
	private boolean vam_to_proprietario_animale;
	private boolean vam_to_azienda_origine;
	private boolean vam_to_proprietario_macello;
	private boolean vam_to_pif;
	private boolean vam_to_uvac;
	private boolean vam_to_regione;
	private boolean vam_to_altro;
	private String vam_to_altro_testo;
	private String mvam_destinazione_carcassa;
	private String mavam_motivo;
	private String mavam_impianto_termodistruzione;
	private String mavam_note;
	private boolean mavam_to_asl_origine;
	private boolean mavam_to_proprietario_animale;
	private boolean mavam_to_azienda_origine;
	private boolean mavam_to_proprietario_macello;
	private boolean mavam_to_pif;
	private boolean mavam_to_uvac;
	private boolean mavam_to_regione;
	private boolean mavam_to_altro;
	private String mavam_to_altro_testo;
	// -->> MACELLAZIONE <<--
	private int mac_progressivo;
	private int progressivo_macellazione;
	private int mac_tipo;

	// -->> VISITA POST MORTEM <<--
	private Timestamp vpm_data;
	private Timestamp vpm_data_esito;
	private String vpm_veterinario;
	private int vpm_esito;
	private String vpm_note;
	private String vpm_causa_patologia;
	private String vpm_veterinario_2;
	private String vpm_veterinario_3;

	// -->> MORTE ANTECEDENTE VISITA AM <<--
	private Timestamp mavam_data;

	// -->> RICEZIONE COMUNICAZIONE ALL'ASL <<--
	private Timestamp rca_data;
	private String rca_note;

	// -->> SEQUESTRO ANIMALE <<--
	private Timestamp seqa_data;
	private Timestamp seqa_data_sblocco;
	private int seqa_destinazione_allo_sblocco;

	// -->> ABBATTIMENTO <<--
	private Timestamp abb_data;
	private String abb_veterinario;
	private String abb_motivo;
	private boolean abb_dist_carcassa;
	private String abb_veterinario_2;
	private String abb_veterinario_3;

	// -->> LIBERO CONSUMO PREVIO RISANAMENTO <<--
	private Timestamp lcpr_data_prevista_liber;
	private Timestamp lcpr_data_effettiva_liber;

	// -->> LIBERO CONSUMO PREVIO RISANAMENTO <<--
	private Timestamp lcso_data;

	private int entered_by;
	private int modified_by;
	private Timestamp entered;
	private Timestamp modified;
	private Timestamp trashed_date;

	private int cd_categoria_rischio = -1;
	/*
	 * 
	 * alter table m_capi add destinatario_3_id integer; alter table m_capi add
	 * destinatario_3_in_regione boolean; alter table m_capi add
	 * destinatario_3_nome text;
	 * 
	 * 
	 * alter table m_capi add destinatario_4_id integer; alter table m_capi add
	 * destinatario_4_in_regione boolean; alter table m_capi add
	 * destinatario_4_nome text;
	 */

	// -->> DESTINAZIONE CARNI <<--
	// private int org_id;
	// private boolean in_regione;
	// private int tipologia_destinazione;
	// private String ragione_sociale;
	private int destinatario_1_id = -1;
	private boolean destinatario_1_in_regione;
	private String destinatario_1_nome;
	private int destinatario_2_id = -1;
	private boolean destinatario_2_in_regione;
	private String destinatario_2_nome;

	// AGGIUNTA DI ALTRI 2 DESTINATARI
	private int destinatario_3_id = -1;
	private boolean destinatario_3_in_regione;
	private String destinatario_3_nome;
	private int destinatario_4_id = -1;
	private boolean destinatario_4_in_regione;
	private String destinatario_4_nome;

	// RICHIESTA ISTOPATOLOGICO
	private boolean istopatologico_richiesta = false;
	private Timestamp istopatologico_data_richiesta;
	private int istopatologico_id = -1;

	private int mavam_luogo;

	public String color = "#FFA500";

	public int getCd_seduta_macellazione() {
		return cd_seduta_macellazione;
	}

	public void setCd_seduta_macellazione(int cd_seduta_macellazione) {
		this.cd_seduta_macellazione = cd_seduta_macellazione;
	}

	public int getMavam_luogo() {
		return mavam_luogo;
	}

	public void setMavam_luogo(int mavam_luogo) {
		this.mavam_luogo = mavam_luogo;
	}

	public String getMvam_destinazione_carcassa() {
		return mvam_destinazione_carcassa;
	}

	public void setMvam_destinazione_carcassa(String mvam_destinazione_carcassa) {
		this.mvam_destinazione_carcassa = mvam_destinazione_carcassa;
	}

	public int getCd_categoria_rischio() {
		return cd_categoria_rischio;
	}

	public void setCd_categoria_rischio(int cd_categoria_rischio) {
		this.cd_categoria_rischio = cd_categoria_rischio;
	}

	public Timestamp getDataSessioneMacellazione() {

		return this.getCd_data_arrivo_macello();

	}

	public int getDestinatario_1_id() {
		return destinatario_1_id;
	}

	public void setDestinatario_1_id(int destinatario_1_id) {
		this.destinatario_1_id = destinatario_1_id;
	}

	/**
	 * @return true se e' in regione false altrimenti (impresa se in regione,
	 *         esercente se fuori regione)
	 */
	public boolean isDestinatario_1_in_regione() {
		return destinatario_1_in_regione;
	}

	public void setDestinatario_1_in_regione(boolean destinatario_1_in_regione) {
		this.destinatario_1_in_regione = destinatario_1_in_regione;
	}

	public String getDestinatario_1_nome() {
		return destinatario_1_nome;
	}

	public void setDestinatario_1_nome(String destinatario_1_nome) {
		this.destinatario_1_nome = destinatario_1_nome;
	}

	public int getDestinatario_2_id() {
		return destinatario_2_id;
	}

	public void setDestinatario_2_id(int destinatario_2_id) {
		this.destinatario_2_id = destinatario_2_id;
	}

	/**
	 * @return true se e' in regione false altrimenti (impresa se in regione,
	 *         esercente se fuori regione)
	 */
	public boolean isDestinatario_2_in_regione() {
		return destinatario_2_in_regione;
	}

	public void setDestinatario_2_in_regione(boolean destinatario_2_in_regione) {
		this.destinatario_2_in_regione = destinatario_2_in_regione;
	}

	public String getDestinatario_2_nome() {
		return destinatario_2_nome;
	}

	public void setDestinatario_2_nome(String destinatario_2_nome) {
		this.destinatario_2_nome = destinatario_2_nome;
	}

	public String getCd_codice_azienda_provenienza() {
		return cd_codice_azienda_provenienza;
	}

	public void setCd_codice_azienda_provenienza(String cd_codice_azienda_provenienza) {
		this.cd_codice_azienda_provenienza = cd_codice_azienda_provenienza;
	}

	public String getCd_info_azienda_provenienza() {
		return cd_info_azienda_provenienza;
	}

	public void setCd_info_azienda_provenienza(String cd_info_azienda_provenienza) {
		this.cd_info_azienda_provenienza = cd_info_azienda_provenienza;
	}

	public int getDestinatario_3_id() {
		return destinatario_3_id;
	}

	public void setDestinatario_3_id(int destinatario_3_id) {
		this.destinatario_3_id = destinatario_3_id;
	}

	public boolean isDestinatario_3_in_regione() {
		return destinatario_3_in_regione;
	}

	public void setDestinatario_3_in_regione(boolean destinatario_3_in_regione) {
		this.destinatario_3_in_regione = destinatario_3_in_regione;
	}

	public String getDestinatario_3_nome() {
		return destinatario_3_nome;
	}

	public void setDestinatario_3_nome(String destinatario_3_nome) {
		this.destinatario_3_nome = destinatario_3_nome;
	}

	public int getDestinatario_4_id() {
		return destinatario_4_id;
	}

	public void setDestinatario_4_id(int destinatario_4_id) {
		this.destinatario_4_id = destinatario_4_id;
	}

	public boolean isDestinatario_4_in_regione() {
		return destinatario_4_in_regione;
	}

	public void setDestinatario_4_in_regione(boolean destinatario_4_in_regione) {
		this.destinatario_4_in_regione = destinatario_4_in_regione;
	}

	public String getDestinatario_4_nome() {
		return destinatario_4_nome;
	}

	public void setDestinatario_4_nome(String destinatario_4_nome) {
		this.destinatario_4_nome = destinatario_4_nome;
	}

	public int getSeqa_destinazione_allo_sblocco() {
		return seqa_destinazione_allo_sblocco;
	}

	public void setSeqa_destinazione_allo_sblocco(int seqa_destinazione_allo_sblocco) {
		this.seqa_destinazione_allo_sblocco = seqa_destinazione_allo_sblocco;
	}

	public Timestamp getCd_data_arrivo_macello() {
		return cd_data_arrivo_macello;
	}

	public void setCd_data_arrivo_macello(Timestamp cd_data_arrivo_macello) {
		this.cd_data_arrivo_macello = cd_data_arrivo_macello;
	}

	public boolean isCd_data_arrivo_macello_flag_dichiarata() {
		return cd_data_arrivo_macello_flag_dichiarata;
	}

	public void setCd_data_arrivo_macello_flag_dichiarata(boolean cd_data_arrivo_macello_flag_dichiarata) {
		this.cd_data_arrivo_macello_flag_dichiarata = cd_data_arrivo_macello_flag_dichiarata;
	}

	public Timestamp getSeqa_data_sblocco() {
		return seqa_data_sblocco;
	}

	public void setSeqa_data_sblocco(Timestamp seqa_data_sblocco) {
		this.seqa_data_sblocco = seqa_data_sblocco;
	}

	public int getCd_asl() {
		return cd_asl;
	}

	public void setCd_asl(int cdAsl) {
		cd_asl = cdAsl;
	}

	public String getCd_note() {
		return cd_note;
	}

	public void setCd_note(String cd_note) {
		this.cd_note = cd_note;
	}

	public boolean isSolo_cd() {
		return solo_cd;
	}

	public void setSolo_cd(boolean solo_cd) {
		this.solo_cd = solo_cd;
	}

	public boolean isManca_BSE_Nmesi() {
		return manca_BSE_Nmesi;
	}

	public void setManca_BSE_Nmesi(boolean manca_BSE_Nmesi) {
		this.manca_BSE_Nmesi = manca_BSE_Nmesi;
	}

	public boolean isArticolo17() {
		return articolo17;
	}

	public void setArticolo17(boolean articolo17) {
		this.articolo17 = articolo17;
	}

	public boolean isModello10() {
		return modello10;
	}

	public void setModello10(boolean modello10) {
		this.modello10 = modello10;
	}

	public Timestamp getCd_data_mod4() {
		return cd_data_mod4;
	}

	public void setCd_data_mod4(Timestamp cd_data_mod4) {
		this.cd_data_mod4 = cd_data_mod4;
	}

	public String getCd_mod4() {
		return cd_mod4;
	}

	public void setCd_mod4(String cd_mod4) {
		this.cd_mod4 = cd_mod4;
	}

	public Timestamp getBse_data_prelievo() {
		return bse_data_prelievo;
	}

	public void setBse_data_prelievo(Timestamp bse_data_prelievo) {
		this.bse_data_prelievo = bse_data_prelievo;
	}

	public Timestamp getBse_data_ricezione_esito() {
		return bse_data_ricezione_esito;
	}

	public void setBse_data_ricezione_esito(Timestamp bse_data_ricezione_esito) {
		this.bse_data_ricezione_esito = bse_data_ricezione_esito;
	}

	public String getBse_esito() {
		return bse_esito;
	}

	public void setBse_esito(String bse_esito) {
		this.bse_esito = bse_esito;
	}

	public String getBse_note() {
		return bse_note;
	}

	public void setBse_note(String bse_note) {
		this.bse_note = bse_note;
	}

	public int getVam_provvedimenti() {
		return vam_provvedimenti;
	}

	public void setVam_provvedimenti(int vam_provvedimenti) {
		this.vam_provvedimenti = vam_provvedimenti;
	}

	public String getVam_esito() {
		return vam_esito;
	}

	public void setVam_esito(String vam_esito) {
		this.vam_esito = vam_esito;
	}

	public String getCd_tipo_mezzo_trasporto() {
		return cd_tipo_mezzo_trasporto;
	}

	public void setCd_tipo_mezzo_trasporto(String cd_tipo_mezzo_trasporto) {
		this.cd_tipo_mezzo_trasporto = cd_tipo_mezzo_trasporto;
	}

	public String getCd_targa_mezzo_trasporto() {
		return cd_targa_mezzo_trasporto;
	}

	public void setCd_targa_mezzo_trasporto(String cd_targa_mezzo_trasporto) {
		this.cd_targa_mezzo_trasporto = cd_targa_mezzo_trasporto;
	}

	public boolean isCd_trasporto_superiore8ore() {
		return cd_trasporto_superiore8ore;
	}

	public void setCd_trasporto_superiore8ore(boolean cd_trasporto_superiore8ore) {
		this.cd_trasporto_superiore8ore = cd_trasporto_superiore8ore;
	}

	public String getVam_provvedimenti_note() {
		return vam_provvedimenti_note;
	}

	public void setVam_provvedimenti_note(String vam_provvedimenti_note) {
		this.vam_provvedimenti_note = vam_provvedimenti_note;
	}

	public String getCd_speditore() {
		return cd_speditore;
	}

	public void setCd_speditore(String cd_speditore) {
		this.cd_speditore = cd_speditore;
	}

	public String getCd_codice_speditore() {
		return cd_codice_speditore;
	}

	public void setCd_codice_speditore(String cd_codice_speditore) {
		this.cd_codice_speditore = cd_codice_speditore;
	}

	public int getCd_id_speditore() {
		return cd_id_speditore;
	}

	public void setCd_id_speditore(int cd_id_speditore) {
		this.cd_id_speditore = cd_id_speditore;
	}

	public int getCd_provenienza_stato() {
		return cd_provenienza_stato;
	}

	public void setCd_provenienza_stato(int cd_provenienza_stato) {
		this.cd_provenienza_stato = cd_provenienza_stato;
	}

	public boolean isCd_prov_stato_comunitario() {
		return cd_prov_stato_comunitario;
	}

	public void setCd_prov_stato_comunitario(boolean cd_prov_stato_comunitario) {
		this.cd_prov_stato_comunitario = cd_prov_stato_comunitario;
	}

	public int getCd_provenienza_regione() {
		return cd_provenienza_regione;
	}

	public void setCd_provenienza_regione(int cd_provenienza_regione) {
		this.cd_provenienza_regione = cd_provenienza_regione;
	}

	public String getCd_provenienza_comune() {
		return cd_provenienza_comune;
	}

	public void setCd_provenienza_comune(String cd_provenienza_comune) {
		this.cd_provenienza_comune = cd_provenienza_comune;
	}

	public String getCd_codice_azienda() {
		return cd_codice_azienda;
	}

	public void setCd_codice_azienda(String cd_codice_azienda) {
		this.cd_codice_azienda = cd_codice_azienda;
	}

	public boolean isCd_vincolo_sanitario() {
		return cd_vincolo_sanitario;
	}

	public void setCd_vincolo_sanitario(boolean cd_vincolo_sanitario) {
		this.cd_vincolo_sanitario = cd_vincolo_sanitario;
	}

	public String getCd_vincolo_sanitario_motivo() {
		return cd_vincolo_sanitario_motivo;
	}

	public void setCd_vincolo_sanitario_motivo(String cd_vincolo_sanitario_motivo) {
		this.cd_vincolo_sanitario_motivo = cd_vincolo_sanitario_motivo;
	}

	public int getCd_macellazione_differita() {
		return cd_macellazione_differita;
	}

	public void setCd_macellazione_differita(int cd_macellazione_differita) {
		this.cd_macellazione_differita = cd_macellazione_differita;
	}

	public boolean isCd_info_catena_alimentare() {
		return cd_info_catena_alimentare;
	}

	public void setCd_info_catena_alimentare(boolean cd_info_catena_alimentare) {
		this.cd_info_catena_alimentare = cd_info_catena_alimentare;
	}

	public String getCd_veterinario_1() {
		return cd_veterinario_1;
	}

	public void setCd_veterinario_1(String cd_veterinario_1) {
		this.cd_veterinario_1 = cd_veterinario_1;
	}

	public String getCd_veterinario_2() {
		return cd_veterinario_2;
	}

	public void setCd_veterinario_2(String cd_veterinario_2) {
		this.cd_veterinario_2 = cd_veterinario_2;
	}

	public String getCd_veterinario_3() {
		return cd_veterinario_3;
	}

	public void setCd_veterinario_3(String cd_veterinario_3) {
		this.cd_veterinario_3 = cd_veterinario_3;
	}

	public Timestamp getVam_data() {
		return vam_data;
	}

	public void setVam_data(Timestamp vam_data) {
		this.vam_data = vam_data;
	}

	// public boolean isVam_favorevole() {
	// return vam_favorevole;
	// }
	// public void setVam_favorevole(boolean vam_favorevole) {
	// this.vam_favorevole = vam_favorevole;
	// }

	public int getMac_progressivo() {
		return mac_progressivo;
	}

	public void setMac_progressivo(int mac_progressivo) {
		this.mac_progressivo = mac_progressivo;
	}

	public int getProgressivo_macellazione() {
		return progressivo_macellazione;
	}

	public void setProgressivo_macellazione(int progressivo_macellazione) {
		this.progressivo_macellazione = progressivo_macellazione;
	}

	public int getMac_tipo() {
		return mac_tipo;
	}

	public void setMac_tipo(int mac_tipo) {
		this.mac_tipo = mac_tipo;
	}

	public Timestamp getVpm_data() {
		return vpm_data;
	}

	public void setVpm_data(Timestamp vpm_data) {
		this.vpm_data = vpm_data;
	}

	public Timestamp getVpm_data_esito() {
		return vpm_data_esito;
	}

	public void setVpm_data_esito(Timestamp vpmDataEsito) {
		vpm_data_esito = vpmDataEsito;
	}

	public String getVpm_veterinario() {
		return vpm_veterinario;
	}

	public void setVpm_veterinario(String vpm_veterinario) {
		this.vpm_veterinario = vpm_veterinario;
	}

	public String getVpm_veterinario_2() {
		return vpm_veterinario_2;
	}

	public void setVpm_veterinario_2(String vpm_veterinario_2) {
		this.vpm_veterinario_2 = vpm_veterinario_2;
	}

	public String getVpm_veterinario_3() {
		return vpm_veterinario_3;
	}

	public void setVpm_veterinario_3(String vpm_veterinario_3) {
		this.vpm_veterinario_3 = vpm_veterinario_3;
	}

	public int getVpm_esito() {
		return vpm_esito;
	}

	public void setVpm_esito(int vpm_esito) {
		this.vpm_esito = vpm_esito;
	}

	public String getVpm_note() {
		return vpm_note;
	}

	public void setVpm_note(String vpm_note) {
		this.vpm_note = vpm_note;
	}

	public String getVpm_causa_patologia() {
		return vpm_causa_patologia;
	}

	public void setVpm_causa_patologia(String vpm_causa_patologia) {
		this.vpm_causa_patologia = vpm_causa_patologia;
	}

	public Timestamp getMavam_data() {
		return mavam_data;
	}

	public void setMavam_data(Timestamp mavam_data) {
		this.mavam_data = mavam_data;
	}

	public boolean isVam_to_asl_origine() {
		return vam_to_asl_origine;
	}

	public void setVam_to_asl_origine(boolean vam_to_asl_origine) {
		this.vam_to_asl_origine = vam_to_asl_origine;
	}

	public boolean isVam_to_proprietario_animale() {
		return vam_to_proprietario_animale;
	}

	public void setVam_to_proprietario_animale(boolean vam_to_proprietario_animale) {
		this.vam_to_proprietario_animale = vam_to_proprietario_animale;
	}

	public boolean isVam_to_azienda_origine() {
		return vam_to_azienda_origine;
	}

	public void setVam_to_azienda_origine(boolean vam_to_azienda_origine) {
		this.vam_to_azienda_origine = vam_to_azienda_origine;
	}

	public boolean isVam_to_proprietario_macello() {
		return vam_to_proprietario_macello;
	}

	public void setVam_to_proprietario_macello(boolean vam_to_proprietario_macello) {
		this.vam_to_proprietario_macello = vam_to_proprietario_macello;
	}

	public boolean isVam_to_pif() {
		return vam_to_pif;
	}

	public void setVam_to_pif(boolean vam_to_pif) {
		this.vam_to_pif = vam_to_pif;
	}

	public boolean isVam_to_uvac() {
		return vam_to_uvac;
	}

	public void setVam_to_uvac(boolean vam_to_uvac) {
		this.vam_to_uvac = vam_to_uvac;
	}

	public boolean isVam_to_regione() {
		return vam_to_regione;
	}

	public void setVam_to_regione(boolean vam_to_regione) {
		this.vam_to_regione = vam_to_regione;
	}

	public boolean isVam_to_altro() {
		return vam_to_altro;
	}

	public void setVam_to_altro(boolean vam_to_altro) {
		this.vam_to_altro = vam_to_altro;
	}

	public String getVam_to_altro_testo() {
		return vam_to_altro_testo;
	}

	public void setVam_to_altro_testo(String vam_to_altro_testo) {
		this.vam_to_altro_testo = vam_to_altro_testo;
	}

	public Timestamp getRca_data() {
		return rca_data;
	}

	public void setRca_data(Timestamp rca_data) {
		this.rca_data = rca_data;
	}

	public String getRca_note() {
		return rca_note;
	}

	public void setRca_note(String rca_note) {
		this.rca_note = rca_note;
	}

	public Timestamp getSeqa_data() {
		return seqa_data;
	}

	public void setSeqa_data(Timestamp seqa_data) {
		this.seqa_data = seqa_data;
	}

	public Timestamp getAbb_data() {
		return abb_data;
	}

	public void setAbb_data(Timestamp abb_data) {
		this.abb_data = abb_data;
	}

	public String getAbb_veterinario() {
		return abb_veterinario;
	}

	public void setAbb_veterinario(String abb_veterinario) {
		this.abb_veterinario = abb_veterinario;
	}

	public String getAbb_veterinario_2() {
		return abb_veterinario_2;
	}

	public void setAbb_veterinario_2(String abb_veterinario_2) {
		this.abb_veterinario_2 = abb_veterinario_2;
	}

	public String getAbb_veterinario_3() {
		return abb_veterinario_3;
	}

	public void setAbb_veterinario_3(String abb_veterinario_3) {
		this.abb_veterinario_3 = abb_veterinario_3;
	}

	public String getAbb_motivo() {
		return abb_motivo;
	}

	public void setAbb_motivo(String abb_motivo) {
		this.abb_motivo = abb_motivo;
	}

	public boolean isAbb_dist_carcassa() {
		return abb_dist_carcassa;
	}

	public void setAbb_dist_carcassa(boolean abb_dist_carcassa) {
		this.abb_dist_carcassa = abb_dist_carcassa;
	}

	public Timestamp getLcpr_data_prevista_liber() {
		return lcpr_data_prevista_liber;
	}

	public void setLcpr_data_prevista_liber(Timestamp lcpr_data_prevista_liber) {
		this.lcpr_data_prevista_liber = lcpr_data_prevista_liber;
	}

	public Timestamp getLcpr_data_effettiva_liber() {
		return lcpr_data_effettiva_liber;
	}

	public void setLcpr_data_effettiva_liber(Timestamp lcpr_data_effettiva_liber) {
		this.lcpr_data_effettiva_liber = lcpr_data_effettiva_liber;
	}

	public Timestamp getLcso_data() {
		return lcso_data;
	}

	public void setLcso_data(Timestamp lcso_data) {
		this.lcso_data = lcso_data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_macello() {
		return id_macello;
	}

	public void setId_macello(int id_macello) {
		this.id_macello = id_macello;
	}

	public int getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public Timestamp getTrashed_date() {
		return trashed_date;
	}

	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}

	public String getStato_macellazione() {
		return stato_macellazione;
	}

	public void setStato_macellazione(String statoMacellazione) {
		stato_macellazione = statoMacellazione;
	}

	public boolean isIstopatologico_richiesta() {
		return istopatologico_richiesta;
	}

	public void setIstopatologico_richiesta(boolean istopatologico_richiesta) {
		this.istopatologico_richiesta = istopatologico_richiesta;
	}

	public Timestamp getIstopatologico_data_richiesta() {
		return istopatologico_data_richiesta;
	}

	public void setIstopatologico_data_richiesta(Timestamp istopatologico_data_richiesta) {
		this.istopatologico_data_richiesta = istopatologico_data_richiesta;
	}

	public int getIstopatologico_id() {
		return istopatologico_id;
	}

	public void setIstopatologico_id(int istopatologico_id) {
		this.istopatologico_id = istopatologico_id;
	}

	public GenericBean store(Connection db, ArrayList<Campione> cmps, ConfigTipo configTipo) throws Exception {
		try {
			setStato_macellazione(MacelliUtil.getStatoMacellazione(this, db, cmps));

			this.entered = new Timestamp(System.currentTimeMillis());
			this.modified = this.entered;

			String sql = "INSERT INTO " + configTipo.getNomeTabella() + "( ";

			Field[] f1 = this.getClass().getDeclaredFields();
			Method[] m1 = this.getClass().getMethods();
			Field[] f2 = this.getClass().getSuperclass().getDeclaredFields();
			Method[] m2 = this.getClass().getSuperclass().getMethods();
			Field[] f = (Field[]) ArrayUtils.addAll(f1, f2);
			Method[] m = (Method[]) ArrayUtils.addAll(m1, m2);

			Vector<Method> v = new Vector<Method>();
			Vector<Field> v2 = new Vector<Field>();
			boolean firstField = true;

			for (int i = 0; i < f.length; i++) {
				String field = f[i].getName();
				boolean trovato = false;
				for (int j = 0; j < m.length; j++) {
					String met = m[j].getName();
					if (!trovato && !field.equalsIgnoreCase("id")
							&& (met.equalsIgnoreCase("GET" + field) || met.equalsIgnoreCase("IS" + field))) {
						v.add(m[j]);
						v2.add(f[i]);
						sql += (firstField) ? ("") : (",");
						firstField = false;
						sql += " " + field;
						trovato = true;
					}
				}

			}

			sql += " ) VALUES (";
			firstField = true;

			for (int i = 0; i < v.size(); i++) {
				{
					sql += (firstField) ? ("") : (",");
					sql += " ?";
					firstField = false;
				}
			}

			sql += " )";

			PreparedStatement stat = db.prepareStatement(sql);

			for (int i = 1; i <= v.size(); i++) {
				Object o = v.elementAt(i - 1).invoke(this);

				if (o == null) {
					stat.setNull(i, parseType(v2.elementAt(i - 1).getType()));
				} else {
					switch (parseType(o.getClass())) {
					case INT:
						stat.setInt(i, (Integer) o);
						break;
					case STRING:
						String s = (String) o;
						s = s.replaceAll("u13", " ");
						s = s.replaceAll("u10", " ");
						s = s.replaceAll("\\r", " ");
						s = s.replaceAll("\\n", " ");
					
						stat.setString(i, s);
						break;
					case BOOLEAN:
						stat.setBoolean(i, (Boolean) o);
						break;
					case TIMESTAMP:
						stat.setTimestamp(i, (Timestamp) o);
						break;
					case DATE:
						stat.setDate(i, (Date) o);
						break;
					case FLOAT:
						stat.setFloat(i, (Float) o);
						break;
					case DOUBLE:
						stat.setDouble(i, (Double) o);
						break;
					}
				}
			}
			stat.execute();
			stat.close();
			return Capo.load("" + DatabaseUtils.getCurrVal(db, configTipo.getSequenceTabella(), -1), db, configTipo);
		} catch (Exception e) {
			System.out.println("Errore " + configTipo.getNomeBean() + ".store ->" + e.getMessage());
			throw e;
		}

	}

	protected static int parseType(Class<?> type) {
		int ret = -1;

		String name = type.getSimpleName();

		if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("integer")) {
			ret = INT;
		} else if (name.equalsIgnoreCase("string")) {
			ret = STRING;
		} else if (name.equalsIgnoreCase("double")) {
			ret = DOUBLE;
		} else if (name.equalsIgnoreCase("float")) {
			ret = FLOAT;
		} else if (name.equalsIgnoreCase("timestamp")) {
			ret = TIMESTAMP;
		} else if (name.equalsIgnoreCase("date")) {
			ret = DATE;
		} else if (name.equalsIgnoreCase("boolean")) {
			ret = BOOLEAN;
		}

		return ret;
	}

	public static GenericBean load(String id, Connection db, ConfigTipo configTipo) throws Exception {

		GenericBean ret = (GenericBean) ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()
				+ configTipo.getNomeBean());
		PreparedStatement stat = null;
		ResultSet res = null;

		if ((id != null) && (id.trim().length() > 0)) {
			try {
				int iid = Integer.parseInt(id);
				stat = db.prepareStatement("SELECT * FROM " + configTipo.getNomeTabella()
						+ "  WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC");
				stat.setInt(1, iid);
				res = stat.executeQuery();
				if (res.next()) {
					ret = loadResultSet(res, db, configTipo);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (res != null) {
						res.close();
						res = null;
					}

					if (stat != null) {
						stat.close();
						stat = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ret;

	}

	public static GenericBean loadByIdentificativo(String identificativo, Connection db, ConfigTipo configTipo)
			throws Exception {

		GenericBean ret = (GenericBean) ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()
				+ configTipo.getNomeBean());
		PreparedStatement stat = null;
		ResultSet res = null;

		if ((identificativo != null) && (identificativo.trim().length() > 0)) {
			try {

				stat = db.prepareStatement("SELECT * FROM " + configTipo.getNomeTabella() + "  WHERE "
						+ configTipo.getNomeCampoIdentificativoTabella() + " ilike ?  ORDER BY entered DESC");
				stat.setString(1, identificativo);
				res = stat.executeQuery();
				if (res.next()) {
					ret = loadResultSet(res, db, configTipo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (res != null) {
						res.close();
						res = null;
					}

					if (stat != null) {
						stat.close();
						stat = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ret;

	}

	private static GenericBean loadResultSet(ResultSet res, Connection db, ConfigTipo configTipo) throws Exception {
		GenericBean ret = (GenericBean) ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()
				+ configTipo.getNomeBean());

		Field[] f = ret.getClass().getDeclaredFields();
		Method[] m = ret.getClass().getMethods();
		caricaResultSet(f, m, res, ret);

		f = ret.getClass().getSuperclass().getDeclaredFields();
		m = ret.getClass().getSuperclass().getMethods();
		caricaResultSet(f, m, res, ret);

		ret.color = MacelliUtil.getRecordColor(ret, db, configTipo);

		return ret;
	}

	public static ArrayList<GenericBean> load(String asl, String identificativo, String provenienza, Connection db,
			ConfigTipo configTipo) {
		ArrayList<GenericBean> ret = new ArrayList<GenericBean>();
		PreparedStatement stat = null;
		ResultSet res = null;

		asl = (asl == null) ? ("") : (asl.trim());
		identificativo = (identificativo == null) ? ("") : (identificativo.trim());
		provenienza = (provenienza == null) ? ("") : (provenienza.trim());

		String sql = "SELECT * FROM "
				+ configTipo.getNomeTabella()
				+ " c o WHERE c.trashed_date IS NULL  ";

		try {

			if (asl.length() > 0) {
				sql += (" AND o.site_id = " + asl.replaceAll("'", "''") + " ");
			}
			if (identificativo.length() > 0) {
				sql += (" AND c.identificativo ILIKE '%" + identificativo.replaceAll("'", "''") + "%' ");
			}
			if (provenienza.length() > 0) {
				sql += (" AND c.provenienza ILIKE '%" + provenienza.replaceAll("'", "''") + "%' ");
			}

			sql += " ORDER BY asl, identificativo LIMIT 100 ";

			stat = db.prepareStatement(sql);

			res = stat.executeQuery();
			while (res.next()) {
				ret.add(loadResultSet(res, db, configTipo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public void update(Connection db, ArrayList<Campione> campioni, ConfigTipo configTipo) throws SQLException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, Exception {
		MacelliUtil.setNextNumeroMacellazione(this, db);
		setStato_macellazione(MacelliUtil.getStatoMacellazione(this, db, campioni));

		String sql = "UPDATE " + configTipo.getNomeTabella() + " SET ";

		Field[] f1 = this.getClass().getDeclaredFields();
		Method[] m1 = this.getClass().getMethods();
		Field[] f2 = this.getClass().getSuperclass().getDeclaredFields();
		Method[] m2 = this.getClass().getSuperclass().getMethods();
		Field[] f = (Field[]) ArrayUtils.addAll(f1, f2);
		Method[] m = (Method[]) ArrayUtils.addAll(m1, m2);

		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;

		for (int i = 0; i < f.length; i++) {
			String field = f[i].getName();
			boolean trovato = false;
			for (int j = 0; j < m.length; j++) {
				String met = m[j].getName();
				if (!trovato && !field.equalsIgnoreCase("id")
						&& (met.equalsIgnoreCase("GET" + field) || met.equalsIgnoreCase("IS" + field))) {
					v.add(m[j]);
					v2.add(f[i]);
					sql += (firstField) ? ("") : (",");
					firstField = false;
					sql += " " + field + " = ?";
					trovato = true;
				}
			}

		}

		sql += " WHERE id = ?";

		PreparedStatement stat = db.prepareStatement(sql);

		for (int i = 1; i <= v.size(); i++) {
			Object o = v.elementAt(i - 1).invoke(this);

			if (o == null) {
				stat.setNull(i, parseType(v2.elementAt(i - 1).getType()));
			} else {
				switch (parseType(o.getClass())) {
				case INT:
					stat.setInt(i, (Integer) o);
					break;
				case STRING:
					stat.setString(i, (String) o);
					break;
				case BOOLEAN:
					stat.setBoolean(i, (Boolean) o);
					break;
				case TIMESTAMP:
					stat.setTimestamp(i, (Timestamp) o);
					break;
				case DATE:
					stat.setDate(i, (Date) o);
					break;
				case FLOAT:
					stat.setFloat(i, (Float) o);
					break;
				case DOUBLE:
					stat.setDouble(i, (Double) o);
					break;
				}
			}
		}

		stat.setInt(v.size() + 1, id);

		stat.execute();
		stat.close();

	}

	public static void delete(int id, int user_id, Connection db, ConfigTipo configTipo) {
		PreparedStatement stat = null;
		try {
			stat = db.prepareStatement("UPDATE " + configTipo.getNomeTabella()
					+ " SET modified = CURRENT_TIMESTAMP, trashed_date = CURRENT_TIMESTAMP, modified_by = ? "
					+ "WHERE id = ? AND trashed_date IS NULL");

			stat.setInt(1, user_id);
			stat.setInt(2, id);

			stat.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<GenericBean> loadByStabilimento(String idStabilimento, Connection db, ConfigTipo configTipo) {
		ArrayList<GenericBean> ret = new ArrayList<GenericBean>();
		PreparedStatement stat = null;
		ResultSet res = null;

		ArrayList<String> date = GenericBean.loadDateMacellazioneByStabilimento(idStabilimento, db, configTipo);
		String data = null;
		if (date != null && !date.isEmpty())
			data = date.get(0);

		String sql = "SELECT c.* ";

		sql += " FROM "
				+ configTipo.getNomeTabella()
				+ " c "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? "
				+ " ";

		if (configTipo.hasSeduteMacellazione()) {
			sql += " AND ((c.vpm_data is not null and c.vpm_data = cast(? as timestamp)) or (select count(*) from "
					+ configTipo.getNomeTabella() + "_sedute as sedute where sedute."
					+ configTipo.getNomeCampoRifAltreTabelle()
					+ " = c.id and sedute.vpm_data = cast(? as timestamp)) > 0)";
		} else {
			sql += " AND (c.vpm_data is not null and c.vpm_data = cast(? as timestamp)) ";
		}

		sql += "ORDER BY c.entered DESC ";

		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));
			stat.setString(2, data);
			if (configTipo.hasSeduteMacellazione()) {
				stat.setString(3, data);
			}
			res = stat.executeQuery();
			while (res.next()) {
				GenericBean gb = loadResultSet(res, db, configTipo);
				ret.add(gb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static ArrayList<GenericBean> loadByStabilimentoPerDataMacellazione(String idStabilimento,
			Timestamp dataMacellazione, Connection db, ConfigTipo configTipo) {
		ArrayList<GenericBean> ret = new ArrayList<GenericBean>();
		PreparedStatement stat = null;
		ResultSet res = null;

		String sql = "SELECT c.* ";
		sql += " FROM "
				+ configTipo.getNomeTabella()
				+ " c "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? AND ";

		if (configTipo.hasSeduteMacellazione()) {
			sql += " (c.vpm_data = ? OR " + "   ( " + "     select count(*) from " + configTipo.getNomeTabella()
					+ "_sedute as sedute " + "     where sedute." + configTipo.getNomeCampoRifAltreTabelle()
					+ " = c.id AND " + "	       sedute.vpm_data = ? " + "   ) >0 ) ";
		} else
			sql += " c.vpm_data = ? ";

		sql += " ORDER BY c.entered DESC ";

		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));
			stat.setTimestamp(2, dataMacellazione);
			if (configTipo.hasSeduteMacellazione())
				stat.setTimestamp(3, dataMacellazione);

			res = stat.executeQuery();
			while (res.next()) {
				GenericBean gb = loadResultSet(res, db, configTipo);
				ret.add(gb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static ArrayList<GenericBean> loadByStabilimentoCapiNonMacellati(String idStabilimento, Connection db,
			ConfigTipo configTipo) {
		ArrayList<GenericBean> ret = new ArrayList<GenericBean>();
		PreparedStatement stat = null;
		ResultSet res = null;

		String sql = "SELECT c.* FROM "
				+ configTipo.getNomeTabella()
				+ " c "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? "
				+ "AND c.vpm_data IS NULL ";
		if (configTipo.hasSeduteMacellazione()) {
			sql += " AND (" + "      select count(*) from " + configTipo.getNomeTabella() + "_sedute as sedute "
					+ "      where sedute." + configTipo.getNomeCampoRifAltreTabelle() + " = c.id AND "
					+ "	        sedute.vpm_data is not null " + "       ) = 0 ";
		}
		sql += " ORDER BY c.entered DESC ";
		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));

			res = stat.executeQuery();
			while (res.next()) {
				GenericBean gb = loadResultSet(res, db, configTipo);
				ret.add(loadResultSet(res, db, configTipo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static ArrayList<String> loadDateMacellazioneByStabilimento(String idStabilimento, Connection db,
			ConfigTipo configTipo) {
		ArrayList<String> ret = new ArrayList<String>();
		PreparedStatement stat = null;
		ResultSet res = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String sql = "  select distinct data from (( " + "SELECT distinct c.vpm_data as data FROM "
				+ configTipo.getNomeTabella() + " c "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? AND c.vpm_data IS NOT NULL "
				+ "ORDER BY c.vpm_data DESC LIMIT ?  ) ";

		if (configTipo.hasSeduteMacellazione()) {
			sql += " union  ( SELECT distinct c.vpm_data as data FROM " + configTipo.getNomeTabella() + "_sedute c "
					+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? AND c.vpm_data IS NOT NULL "
					+ "ORDER BY c.vpm_data DESC LIMIT ? ) ";
		}

		sql += " ) as a order by a.data desc ";

		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));
			String numDateString = ApplicationProperties.getProperty("numero_date_macellazione_combo");
			int numDate = 10;
			if (numDateString != null && !numDateString.equals("")) {
				numDate = Integer.parseInt(numDateString);
			}
			stat.setInt(2, numDate);
			if (configTipo.hasSeduteMacellazione()) {
				stat.setInt(3, Integer.parseInt(idStabilimento));
				stat.setInt(4, numDate);
			}

			res = stat.executeQuery();
			while (res.next()) {
				ret.add(sdf.format(res.getTimestamp(1)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static ArrayList<String> loadDateMacellazioneByStabilimentoTamponi(String idStabilimento, Connection db,
			ConfigTipo configTipo) {
		ArrayList<String> ret = new ArrayList<String>();
		PreparedStatement stat = null;
		ResultSet res = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String sql = "SELECT distinct c.vpm_data FROM " + configTipo.getNomeTabella()
				+ " c JOIN m_vpm_capi_tamponi ON c.id = m_vpm_capi_tamponi."
				+ configTipo.getNomeCampoRifTabellaTamponi() + " "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello = ? AND c.vpm_data IS NOT NULL  "
				+ "ORDER BY c.vpm_data DESC LIMIT ?";
		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));
			String numDateString = ApplicationProperties.getProperty("numero_date_macellazione_combo");
			int numDate = 10;
			if (numDateString != null && !numDateString.equals("")) {
				numDate = Integer.parseInt(numDateString);
			}
			stat.setInt(2, numDate);

			res = stat.executeQuery();
			while (res.next()) {
				ret.add(sdf.format(res.getTimestamp(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static ArrayList<GenericBean> loadByStabilimentoConStato(String idStabilimento, Connection db,
			ConfigTipo configTipo) {
		ArrayList<GenericBean> ret = new ArrayList<GenericBean>();
		PreparedStatement stat = null;
		ResultSet res = null;

		String sql = "SELECT c.* FROM "
				+ configTipo.getNomeTabella()
				+ " c "
				+ "WHERE c.trashed_date IS NULL AND c.id_macello =  ? "
				+ "ORDER BY c.entered DESC ";
		try {
			stat = db.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(idStabilimento));

			res = stat.executeQuery();
			while (res.next()) {
				ret.add(loadResultSet(res, db, configTipo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	public void setCategoria() {
	}

	public boolean isCasl_to_asl_origine() {
		return casl_to_asl_origine;
	}

	public void setCasl_to_asl_origine(boolean casl_to_asl_origine) {
		this.casl_to_asl_origine = casl_to_asl_origine;
	}

	public boolean isCasl_to_proprietario_animale() {
		return casl_to_proprietario_animale;
	}

	public void setCasl_to_proprietario_animale(boolean casl_to_proprietario_animale) {
		this.casl_to_proprietario_animale = casl_to_proprietario_animale;
	}

	public boolean isCasl_to_azienda_origine() {
		return casl_to_azienda_origine;
	}

	public void setCasl_to_azienda_origine(boolean casl_to_azienda_origine) {
		this.casl_to_azienda_origine = casl_to_azienda_origine;
	}

	public boolean isCasl_to_proprietario_macello() {
		return casl_to_proprietario_macello;
	}

	public void setCasl_to_proprietario_macello(boolean casl_to_proprietario_macello) {
		this.casl_to_proprietario_macello = casl_to_proprietario_macello;
	}

	public boolean isCasl_to_pif() {
		return casl_to_pif;
	}

	public void setCasl_to_pif(boolean casl_to_pif) {
		this.casl_to_pif = casl_to_pif;
	}

	public boolean isCasl_to_uvac() {
		return casl_to_uvac;
	}

	public void setCasl_to_uvac(boolean casl_to_uvac) {
		this.casl_to_uvac = casl_to_uvac;
	}

	public boolean isCasl_to_regione() {
		return casl_to_regione;
	}

	public void setCasl_to_regione(boolean casl_to_regione) {
		this.casl_to_regione = casl_to_regione;
	}

	public boolean isCasl_to_altro() {
		return casl_to_altro;
	}

	public void setCasl_to_altro(boolean casl_to_altro) {
		this.casl_to_altro = casl_to_altro;
	}

	public String getCasl_to_altro_testo() {
		return casl_to_altro_testo;
	}

	public void setCasl_to_altro_testo(String casl_to_altro_testo) {
		this.casl_to_altro_testo = casl_to_altro_testo;
	}

	public String getMavam_descrizione_luogo_verifica() {
		return mavam_descrizione_luogo_verifica;
	}

	public void setMavam_descrizione_luogo_verifica(String mavam_descrizione_luogo_verifica) {
		this.mavam_descrizione_luogo_verifica = mavam_descrizione_luogo_verifica;
	}

	public String getMavam_impianto_termodistruzione() {
		return mavam_impianto_termodistruzione;
	}

	public void setMavam_impianto_termodistruzione(String mavam_impianto_termodistruzione) {
		this.mavam_impianto_termodistruzione = mavam_impianto_termodistruzione;
	}

	public String getMavam_note() {
		return mavam_note;
	}

	public void setMavam_note(String mavam_note) {
		this.mavam_note = mavam_note;
	}

	public String getMavam_motivo() {
		return mavam_motivo;
	}

	public void setMavam_motivo(String mavam_motivo) {
		this.mavam_motivo = mavam_motivo;
	}

	public boolean isMavam_to_azienda_origine() {
		return mavam_to_azienda_origine;
	}

	public void setMavam_to_azienda_origine(boolean mavam_to_azienda_origine) {
		this.mavam_to_azienda_origine = mavam_to_azienda_origine;
	}

	public boolean isMavam_to_asl_origine() {
		return mavam_to_asl_origine;
	}

	public void setMavam_to_asl_origine(boolean mavam_to_asl_origine) {
		this.mavam_to_asl_origine = mavam_to_asl_origine;
	}

	public boolean isMavam_to_proprietario_animale() {
		return mavam_to_proprietario_animale;
	}

	public void setMavam_to_proprietario_animale(boolean mavam_to_proprietario_animale) {
		this.mavam_to_proprietario_animale = mavam_to_proprietario_animale;
	}

	public boolean isMavam_to_pif() {
		return mavam_to_pif;
	}

	public void setMavam_to_pif(boolean mavam_to_pif) {
		this.mavam_to_pif = mavam_to_pif;
	}

	public boolean isMavam_to_uvac() {
		return mavam_to_uvac;
	}

	public void setMavam_to_uvac(boolean mavam_to_uvac) {
		this.mavam_to_uvac = mavam_to_uvac;
	}

	public boolean isMavam_to_regione() {
		return mavam_to_regione;
	}

	public void setMavam_to_regione(boolean mavam_to_regione) {
		this.mavam_to_regione = mavam_to_regione;
	}

	public boolean isMavam_to_altro() {
		return mavam_to_altro;
	}

	public void setMavam_to_altro(boolean mavam_to_altro) {
		this.mavam_to_altro = mavam_to_altro;
	}

	public String getMavam_to_altro_testo() {
		return mavam_to_altro_testo;
	}

	public void setMavam_to_altro_testo(String mavam_to_altro_testo) {
		this.mavam_to_altro_testo = mavam_to_altro_testo;
	}

	public boolean isMavam_to_proprietario_macello() {
		return mavam_to_proprietario_macello;
	}

	public void setMavam_to_proprietario_macello(boolean mavam_to_proprietario_macello) {
		this.mavam_to_proprietario_macello = mavam_to_proprietario_macello;
	}

	public String getCasl_note_prevvedimento() {
		return casl_note_prevvedimento;
	}

	public void setCasl_note_prevvedimento(String caslNotePrevvedimento) {
		casl_note_prevvedimento = caslNotePrevvedimento;
	}

	public Timestamp getCasl_data() {
		return casl_data;
	}

	public void setCasl_data(Timestamp casl_data) {
		this.casl_data = casl_data;
	}

	public String getCasl_info_richiesta() {
		return casl_info_richiesta;
	}

	public void setCasl_info_richiesta(String casl_info_richiesta) {
		this.casl_info_richiesta = casl_info_richiesta;
	}

	private static void caricaResultSet(Field[] f, Method[] m, ResultSet res, GenericBean ret) throws Exception {
		for (int i = 0; i < f.length; i++) {
			Method getter = null;
			Method setter = null;
			Field campo = f[i];
			String field = f[i].getName();
			for (int j = 0; j < m.length; j++) {
				String met = m[j].getName();
				if (met.equalsIgnoreCase("GET" + field) || met.equalsIgnoreCase("IS" + field)) {
					getter = m[j];
				} else if (met.equalsIgnoreCase("SET" + field)) {
					setter = m[j];
				}
			}

			if ((getter != null) && (setter != null) && (campo != null)) {
				Object o = null;

				switch (parseType(campo.getType())) {
				case INT:
					o = res.getInt(field);
					break;
				case STRING:
					o = res.getString(field);
					break;
				case BOOLEAN:
					o = res.getBoolean(field);
					break;
				case TIMESTAMP:
					o = res.getTimestamp(field);
					break;
				case DATE:
					o = res.getDate(field);
					break;
				case FLOAT:
					o = res.getFloat(field);
					break;
				case DOUBLE:
					o = res.getDouble(field);
					break;
				}

				setter.invoke(ret, o);

			}
		}

	}

	public static int nextId(Connection db, ConfigTipo configTipo) {

		ArrayList<PartitaSeduta> ret = new ArrayList<PartitaSeduta>();
		PreparedStatement stat = null;
		ResultSet res = null;

		int max = 1;

		String sql = "SELECT max(id) as max FROM " + configTipo.getNomeTabella();
		try {
			stat = db.prepareStatement(sql);

			res = stat.executeQuery();

			while (res.next()) {
				max = res.getInt("max") + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.close();
					res = null;
				}

				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return max;
	}

	public static ArrayList<Organi> checkOrganiTumorali(Connection con, int idBean, ConfigTipo tipoConfig) {

		ArrayList<Organi> organiTumorali = new ArrayList<Organi>();
		ArrayList<Organi> organi = new ArrayList<Organi>();
		
		organi.addAll(Organi.loadByOrgani(idBean, tipoConfig));

		if (tipoConfig.hasSeduteMacellazione()) {
		

			Iterator<PartitaSeduta> sedute = Partita.loadSeduteSmart(con, idBean, tipoConfig).iterator();
			if (sedute.hasNext()) {
				while (sedute.hasNext()) {

					PartitaSeduta temp = sedute.next();
					organi.addAll(temp.loadOrgani(temp.getId(), con))  ;


				}
			} 
			//else {
//				organi = Organi.loadByOrgani(idBean, tipoConfig);
//			}

		} 
		//else {
//
//			organi = Organi.loadByOrgani(idBean, tipoConfig);
//
//
//		}
		
		if (organi != null && organi.size() > 0) {
			Iterator i = organi.iterator();
			while (i.hasNext()) {
				Organi thisOrgano = (Organi) i.next();

				if (thisOrgano.getLcso_patologia() == 2) {
					organiTumorali.add(thisOrgano);
				}

			}

			// }
		}

		return organiTumorali;
	}

}
