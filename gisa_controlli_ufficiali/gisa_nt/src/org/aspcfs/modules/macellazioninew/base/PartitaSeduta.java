/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninew.base;

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
import java.util.Vector;

import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninew.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninew.utils.ReflectionUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;

public class PartitaSeduta extends Partita
{
	private static final long serialVersionUID = 8313006891554941893L;
	
	//campi usati per immagazzinare i dati delle tabelel in join al fine di contenere
	//tutte le informazioni del capo macellato nello storico (campioni, organi, non conformita' etc..
	//NB. campi pubblici senza metodi get e set
	public ArrayList<NonConformita>		storico_vam_non_conformita;
	public ArrayList<Organi>			storico_lcso_organi;
	public ArrayList<PatologiaRilevata>	storico_vpm_patologie_rilevate;
	public ArrayList<Esito>	            storico_vpm_esiti;
	public ArrayList<Campione>			storico_vpm_campioni;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private int id;
	private int id_macello;
	private int id_partita;
	private String stato_macellazione;
	private String		numero;
	// -->> CONTROLLO DOCUMENTALE <<--	
	//provenienza
	//
	
	private boolean		chiusa;
	private String		motivazione_chiusura;
	//FLAG DICHIARATA DAL GESTORE
	private boolean		solo_cd = true;
	private boolean		manca_BSE_Nmesi = false;
	private boolean		articolo17 = false;
	
	private boolean		modello10 = false;

// -->> TEST BSE <<--
	private Timestamp	bse_data_prelievo;
	private Timestamp	bse_data_ricezione_esito;
	private String		bse_esito;
	private String		bse_note;
		
// -->> VISITA ANTE MORTEM <<--
	private Timestamp	vam_data;
	//private boolean		vam_favorevole;
	//private boolean		vam_macellazione_con_osservazione;
	private int			vam_provvedimenti;
	private String		vam_provvedimenti_note;
	private String		vam_esito;
	private Integer		    vam_num_capi_ovini=0;
	private Integer         vam_num_capi_caprini=0;
	private boolean		vam_to_asl_origine;
	private boolean		vam_to_proprietario_animale;
	private boolean		vam_to_azienda_origine;
	private boolean		vam_to_proprietario_macello;
	private boolean		vam_to_pif;
	private boolean		vam_to_uvac;
	private boolean		vam_to_regione;
	private boolean		vam_to_altro;
	private String		vam_to_altro_testo;
	private String 		mvam_destinazione_carcassa ;
	// -->> MACELLAZIONE <<--
	private int			mac_progressivo;
	private int			progressivo_macellazione;
	private int			mac_tipo;

	// -->> VISITA POST MORTEM <<--
	private Timestamp	vpm_data;
	private Timestamp	vpm_data_esito;
	private String		vpm_veterinario;
	private int			vpm_esito;
	private String		vpm_note;
	private String		vpm_causa_patologia;
	private String		vpm_veterinario_2;
	private String		vpm_veterinario_3;
	private int		    vpm_num_capi;    
	
// -->> MORTE ANTECEDENTE VISITA AM <<--
	private Timestamp	mavam_data;
	private Integer		   mavam_num_capi_ovini=0;
	private Integer			mavam_num_capi_caprini=0;
	private int			mavam_luogo;
	private String		mavam_descrizione_luogo_verifica;
	private String		mavam_motivo;
	private String		mavam_impianto_termodistruzione;
	private String		mavam_note;
	private boolean		mavam_to_asl_origine;
	private boolean		mavam_to_proprietario_animale;
	private boolean		mavam_to_azienda_origine;
	private boolean		mavam_to_proprietario_macello;
	private boolean		mavam_to_pif;
	private boolean		mavam_to_uvac;
	private boolean		mavam_to_regione;
	private boolean		mavam_to_altro;
	private String		mavam_to_altro_testo;
	
// -->> RICEZIONE COMUNICAZIONE ALL'ASL <<--
	private Timestamp	rca_data;
	private String		rca_note;
	    
// -->> SEQUESTRO ANIMALE <<--
	private Timestamp	seqa_data;
	private Timestamp	seqa_data_sblocco;
	private int			seqa_destinazione_allo_sblocco;
	    
// -->> ABBATTIMENTO <<--
	private Timestamp	abb_data;
	private String		abb_veterinario;
	private String		abb_motivo;
	private boolean 	abb_dist_carcassa;
	private String		abb_veterinario_2;
	private String		abb_veterinario_3;
	    
// -->> LIBERO CONSUMO PREVIO RISANAMENTO <<--
	private Timestamp	lcpr_data_prevista_liber;
	private Timestamp	lcpr_data_effettiva_liber;
		    
// -->> LIBERO CONSUMO PREVIO RISANAMENTO <<--
	private Timestamp	lcso_data;
	    
	private int			entered_by;
	private int			modified_by;
	private Timestamp	entered;
	private Timestamp	modified;
	private Timestamp	trashed_date;
	
	/*
	 * 
	alter table m_capi add destinatario_3_id integer;
	alter table m_capi add destinatario_3_in_regione boolean;
	alter table m_capi add destinatario_3_nome text;


	alter table m_capi add destinatario_4_id integer;
	alter table m_capi add destinatario_4_in_regione boolean;
	alter table m_capi add destinatario_4_nome text;
	 * */
	
	// -->> DESTINAZIONE CARNI <<--
//	private int 		org_id;
//	private boolean 	in_regione;
//	private int			tipologia_destinazione;	
//	private String		ragione_sociale;
	private int 		destinatario_1_id = -1;
	private boolean 	destinatario_1_in_regione;
	private String		destinatario_1_nome;
	private int 		destinatario_2_id = -1;
	private boolean 	destinatario_2_in_regione;
	private String		destinatario_2_nome;
	
	//AGGIUNTA DI ALTRI 2 DESTINATARI
	private int 		destinatario_3_id = -1;
	private boolean 	destinatario_3_in_regione;
	private String		destinatario_3_nome;
	private int 		destinatario_4_id = -1;
	private boolean 	destinatario_4_in_regione;
	private String		destinatario_4_nome;
	private Integer 	destinatario_5_id = -1;
	private Boolean 	destinatario_5_in_regione;
	private String		destinatario_5_nome;
	private Integer 	destinatario_6_id = -1;
	private Boolean 	destinatario_6_in_regione;
	private String		destinatario_6_nome;
	private Integer 	destinatario_7_id = -1;
	private Boolean 	destinatario_7_in_regione;
	private String		destinatario_7_nome;
	private Integer 	destinatario_8_id = -1;
	private Boolean 	destinatario_8_in_regione;
	private String		destinatario_8_nome;
	private Integer 	destinatario_9_id = -1;
	private Boolean 	destinatario_9_in_regione;
	private String		destinatario_9_nome;
	private Integer 	destinatario_10_id = -1;
	private Boolean 	destinatario_10_in_regione;
	private String		destinatario_10_nome;
	private Integer 	destinatario_11_id = -1;
	private Boolean 	destinatario_11_in_regione;
	private String		destinatario_11_nome;
	private Integer 	destinatario_12_id = -1;
	private Boolean 	destinatario_12_in_regione;
	private String		destinatario_12_nome;
	private Integer 	destinatario_13_id = -1;
	private Boolean 	destinatario_13_in_regione;
	private String		destinatario_13_nome;
	private Integer 	destinatario_14_id = -1;
	private Boolean 	destinatario_14_in_regione;
	private String		destinatario_14_nome;
	private Integer 	destinatario_15_id = -1;
	private Boolean 	destinatario_15_in_regione;
	private String		destinatario_15_nome;
	private Integer 	destinatario_16_id = -1;
	private Boolean 	destinatario_16_in_regione;
	private String		destinatario_16_nome;
	private Integer 	destinatario_17_id = -1;
	private Boolean 	destinatario_17_in_regione;
	private String		destinatario_17_nome;
	private Integer 	destinatario_18_id = -1;
	private Boolean 	destinatario_18_in_regione;
	private String		destinatario_18_nome;
	private Integer 	destinatario_19_id = -1;
	private Boolean 	destinatario_19_in_regione;
	private String		destinatario_19_nome;
	private Integer 	destinatario_20_id = -1;
	private Boolean 	destinatario_20_in_regione;
	private String		destinatario_20_nome;
	private Integer 	destinatario_5_num_capi_ovini = 0;
	private Integer 	destinatario_6_num_capi_ovini = 0;
	private Integer 	destinatario_7_num_capi_ovini = 0;
	private Integer 	destinatario_8_num_capi_ovini = 0;
	private Integer 	destinatario_9_num_capi_ovini = 0;
	private Integer 	destinatario_10_num_capi_ovini = 0;
	private Integer 	destinatario_11_num_capi_ovini = 0;
	private Integer 	destinatario_12_num_capi_ovini = 0;
	private Integer 	destinatario_13_num_capi_ovini = 0;
	private Integer 	destinatario_14_num_capi_ovini = 0;
	private Integer 	destinatario_15_num_capi_ovini = 0;
	private Integer 	destinatario_16_num_capi_ovini = 0;
	private Integer 	destinatario_17_num_capi_ovini = 0;
	private Integer 	destinatario_18_num_capi_ovini = 0;
	private Integer 	destinatario_19_num_capi_ovini = 0;
	private Integer 	destinatario_1_num_capi_ovini = 0;
	private Integer 	destinatario_2_num_capi_ovini = 0;
	private Integer 	destinatario_3_num_capi_ovini = 0;
	private Integer 	destinatario_4_num_capi_ovini = 0;
	private Integer 	destinatario_20_num_capi_ovini = 0;
	private Integer 	destinatario_5_num_capi_caprini = 0;
	private Integer 	destinatario_6_num_capi_caprini = 0;
	private Integer 	destinatario_7_num_capi_caprini = 0;
	private Integer 	destinatario_8_num_capi_caprini = 0;
	private Integer 	destinatario_9_num_capi_caprini = 0;
	private Integer 	destinatario_10_num_capi_caprini = 0;
	private Integer 	destinatario_11_num_capi_caprini = 0;
	private Integer 	destinatario_12_num_capi_caprini = 0;
	private Integer 	destinatario_13_num_capi_caprini = 0;
	private Integer 	destinatario_14_num_capi_caprini = 0;
	private Integer 	destinatario_15_num_capi_caprini = 0;
	private Integer 	destinatario_16_num_capi_caprini = 0;
	private Integer 	destinatario_17_num_capi_caprini = 0;
	private Integer 	destinatario_18_num_capi_caprini = 0;
	private Integer 	destinatario_19_num_capi_caprini = 0;
	private Integer 	destinatario_1_num_capi_caprini = 0;
	private Integer 	destinatario_2_num_capi_caprini = 0;
	private Integer 	destinatario_3_num_capi_caprini = 0;
	private Integer 	destinatario_4_num_capi_caprini = 0;
	private Integer 	destinatario_20_num_capi_caprini = 0;
	
	public String color = "#FFA500";
	
	private boolean specie_suina = false;
	
	
	public boolean isSpecie_suina() {
		return specie_suina;
	}

	public void setSpecie_suina(boolean specie_suina) {
		this.specie_suina = specie_suina;
		super.setSpecie_suina(specie_suina);
	}

	public String getMvam_destinazione_carcassa() {
		return mvam_destinazione_carcassa;
	}

	public void setMvam_destinazione_carcassa(String mvam_destinazione_carcassa) {
		this.mvam_destinazione_carcassa = mvam_destinazione_carcassa;
	}


	public Timestamp getDataSessioneMacellazione(){
		
		return this.getCd_data_arrivo_macello();
		
	}
	
	public int getDestinatario_5_id() {
		return destinatario_5_id;
	}
	public void setDestinatario_5_id(Integer destinatario_5_id) {
		this.destinatario_5_id = destinatario_5_id;
	}
	
	public boolean isDestinatario_5_in_regione() {
		return destinatario_5_in_regione;
	}
	public void setDestinatario_5_in_regione(Boolean destinatario_5_in_regione) {
		this.destinatario_5_in_regione = destinatario_5_in_regione;
	}
	
	public String getDestinatario_5_nome() {
		return destinatario_5_nome;
	}
	public void setDestinatario_5_nome(String destinatario_5_nome) {
		this.destinatario_5_nome = destinatario_5_nome;
	}
	
	public boolean isDestinatario_6_in_regione() {
		return destinatario_6_in_regione;
	}
	public void setDestinatario_6_in_regione(Boolean destinatario_6_in_regione) {
		this.destinatario_6_in_regione = destinatario_6_in_regione;
	}
	
	public String getDestinatario_6_nome() {
		return destinatario_6_nome;
	}
	public void setDestinatario_6_nome(String destinatario_6_nome) {
		this.destinatario_6_nome = destinatario_6_nome;
	}
	
	public boolean isDestinatario_7_in_regione() {
		return destinatario_7_in_regione;
	}
	public void setDestinatario_7_in_regione(Boolean destinatario_7_in_regione) {
		this.destinatario_7_in_regione = destinatario_7_in_regione;
	}
	
	public String getDestinatario_7_nome() {
		return destinatario_7_nome;
	}
	public void setDestinatario_7_nome(String destinatario_7_nome) {
		this.destinatario_7_nome = destinatario_7_nome;
	}
	
	public boolean isDestinatario_8_in_regione() {
		return destinatario_8_in_regione;
	}
	public void setDestinatario_8_in_regione(Boolean destinatario_8_in_regione) {
		this.destinatario_8_in_regione = destinatario_8_in_regione;
	}
	
	public String getDestinatario_8_nome() {
		return destinatario_8_nome;
	}
	public void setDestinatario_8_nome(String destinatario_8_nome) {
		this.destinatario_8_nome = destinatario_8_nome;
	}
	
	public boolean isDestinatario_9_in_regione() {
		return destinatario_9_in_regione;
	}
	public void setDestinatario_9_in_regione(Boolean destinatario_9_in_regione) {
		this.destinatario_9_in_regione = destinatario_9_in_regione;
	}
	
	public String getDestinatario_9_nome() {
		return destinatario_9_nome;
	}
	public void setDestinatario_9_nome(String destinatario_9_nome) {
		this.destinatario_9_nome = destinatario_9_nome;
	}
	
	public boolean isDestinatario_10_in_regione() {
		return destinatario_10_in_regione;
	}
	public void setDestinatario_10_in_regione(Boolean destinatario_10_in_regione) {
		this.destinatario_10_in_regione = destinatario_10_in_regione;
	}
	
	public String getDestinatario_10_nome() {
		return destinatario_10_nome;
	}
	public void setDestinatario_10_nome(String destinatario_10_nome) {
		this.destinatario_10_nome = destinatario_10_nome;
	}
	
	public int getDestinatario_1_id() {
		return destinatario_1_id;
	}
	public void setDestinatario_1_id(int destinatario_1_id) {
		this.destinatario_1_id = destinatario_1_id;
	}
	
	/**
	 * @return true se e' in regione false altrimenti (impresa se in regione, esercente se fuori regione)
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
	 * @return true se e' in regione false altrimenti (impresa se in regione, esercente se fuori regione)
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
	
	public boolean isDestinatario_11_in_regione() {
		return destinatario_11_in_regione;
	}
	public void setDestinatario_11_in_regione(Boolean destinatario_11_in_regione) {
		this.destinatario_11_in_regione = destinatario_11_in_regione;
	}
	
	public String getDestinatario_11_nome() {
		return destinatario_11_nome;
	}
	public void setDestinatario_11_nome(String destinatario_11_nome) {
		this.destinatario_11_nome = destinatario_11_nome;
	}
	
	public boolean isDestinatario_12_in_regione() {
		return destinatario_12_in_regione;
	}
	public void setDestinatario_12_in_regione(Boolean destinatario_12_in_regione) {
		this.destinatario_12_in_regione = destinatario_12_in_regione;
	}
	
	public String getDestinatario_12_nome() {
		return destinatario_12_nome;
	}
	public void setDestinatario_12_nome(String destinatario_12_nome) {
		this.destinatario_12_nome = destinatario_12_nome;
	}
	
	public boolean isDestinatario_13_in_regione() {
		return destinatario_13_in_regione;
	}
	public void setDestinatario_13_in_regione(Boolean destinatario_13_in_regione) {
		this.destinatario_13_in_regione = destinatario_13_in_regione;
	}
	
	public String getDestinatario_13_nome() {
		return destinatario_13_nome;
	}
	public void setDestinatario_13_nome(String destinatario_13_nome) {
		this.destinatario_13_nome = destinatario_13_nome;
	}
	
	public boolean isDestinatario_14_in_regione() {
		return destinatario_14_in_regione;
	}
	public void setDestinatario_14_in_regione(Boolean destinatario_14_in_regione) {
		this.destinatario_14_in_regione = destinatario_14_in_regione;
	}
	
	public String getDestinatario_14_nome() {
		return destinatario_14_nome;
	}
	public void setDestinatario_14_nome(String destinatario_14_nome) {
		this.destinatario_14_nome = destinatario_14_nome;
	}
	
	public boolean isDestinatario_15_in_regione() {
		return destinatario_15_in_regione;
	}
	public void setDestinatario_15_in_regione(Boolean destinatario_15_in_regione) {
		this.destinatario_15_in_regione = destinatario_15_in_regione;
	}
	
	public String getDestinatario_15_nome() {
		return destinatario_15_nome;
	}
	public void setDestinatario_15_nome(String destinatario_15_nome) {
		this.destinatario_15_nome = destinatario_15_nome;
	}
	
	public boolean isDestinatario_16_in_regione() {
		return destinatario_16_in_regione;
	}
	public void setDestinatario_16_in_regione(Boolean destinatario_16_in_regione) {
		this.destinatario_16_in_regione = destinatario_16_in_regione;
	}
	
	public String getDestinatario_16_nome() {
		return destinatario_16_nome;
	}
	public void setDestinatario_16_nome(String destinatario_16_nome) {
		this.destinatario_16_nome = destinatario_16_nome;
	}
	
	public boolean isDestinatario_17_in_regione() {
		return destinatario_17_in_regione;
	}
	public void setDestinatario_17_in_regione(Boolean destinatario_17_in_regione) {
		this.destinatario_17_in_regione = destinatario_17_in_regione;
	}
	
	public String getDestinatario_17_nome() {
		return destinatario_17_nome;
	}
	public void setDestinatario_17_nome(String destinatario_17_nome) {
		this.destinatario_17_nome = destinatario_17_nome;
	}
	
	public boolean isDestinatario_18_in_regione() {
		return destinatario_18_in_regione;
	}
	public void setDestinatario_18_in_regione(Boolean destinatario_18_in_regione) {
		this.destinatario_18_in_regione = destinatario_18_in_regione;
	}
	
	public String getDestinatario_18_nome() {
		return destinatario_18_nome;
	}
	public void setDestinatario_18_nome(String destinatario_18_nome) {
		this.destinatario_18_nome = destinatario_18_nome;
	}
	
	public boolean isDestinatario_19_in_regione() {
		return destinatario_19_in_regione;
	}
	public void setDestinatario_19_in_regione(Boolean destinatario_19_in_regione) {
		this.destinatario_19_in_regione = destinatario_19_in_regione;
	}
	
	public String getDestinatario_19_nome() {
		return destinatario_19_nome;
	}
	public void setDestinatario_19_nome(String destinatario_19_nome) {
		this.destinatario_19_nome = destinatario_19_nome;
	}
	
	public boolean isDestinatario_20_in_regione() {
		return destinatario_20_in_regione;
	}
	public void setDestinatario_20_in_regione(Boolean destinatario_20_in_regione) {
		this.destinatario_20_in_regione = destinatario_20_in_regione;
	}
	
	public String getDestinatario_20_nome() {
		return destinatario_20_nome;
	}
	public void setDestinatario_20_nome(String destinatario_20_nome) {
		this.destinatario_20_nome = destinatario_20_nome;
	}
	
	public int getDestinatario_6_id() {
		return destinatario_6_id;
	}
	public void setDestinatario_6_id(Integer destinatario_6_id) {
		this.destinatario_6_id = destinatario_6_id;
	}
	
	public int getDestinatario_7_id() {
		return destinatario_7_id;
	}
	public void setDestinatario_7_id(Integer destinatario_7_id) {
		this.destinatario_7_id = destinatario_7_id;
	}
	
	public int getDestinatario_8_id() {
		return destinatario_8_id;
	}
	public void setDestinatario_8_id(Integer destinatario_8_id) {
		this.destinatario_8_id = destinatario_8_id;
	}
	
	public int getDestinatario_9_id() {
		return destinatario_9_id;
	}
	public void setDestinatario_9_id(Integer destinatario_9_id) {
		this.destinatario_9_id = destinatario_9_id;
	}
	
	public int getDestinatario_10_id() {
		return destinatario_10_id;
	}
	public void setDestinatario_10_id(Integer destinatario_10_id) {
		this.destinatario_10_id = destinatario_10_id;
	}
	
	public int getDestinatario_11_id() {
		return destinatario_11_id;
	}
	public void setDestinatario_11_id(Integer destinatario_11_id) {
		this.destinatario_11_id = destinatario_11_id;
	}
	
	public int getDestinatario_12_id() {
		return destinatario_12_id;
	}
	public void setDestinatario_12_id(Integer destinatario_12_id) {
		this.destinatario_12_id = destinatario_12_id;
	}
	
	public int getDestinatario_13_id() {
		return destinatario_13_id;
	}
	public void setDestinatario_13_id(Integer destinatario_13_id) {
		this.destinatario_13_id = destinatario_13_id;
	}
	
	public int getDestinatario_14_id() {
		return destinatario_14_id;
	}
	public void setDestinatario_14_id(Integer destinatario_14_id) {
		this.destinatario_14_id = destinatario_14_id;
	}
	
	public int getDestinatario_15_id() {
		return destinatario_15_id;
	}
	public void setDestinatario_15_id(Integer destinatario_15_id) {
		this.destinatario_15_id = destinatario_15_id;
	}
	
	public int getDestinatario_16_id() {
		return destinatario_16_id;
	}
	public void setDestinatario_16_id(Integer destinatario_16_id) {
		this.destinatario_16_id = destinatario_16_id;
	}
	
	public int getDestinatario_17_id() {
		return destinatario_17_id;
	}
	public void setDestinatario_17_id(Integer destinatario_17_id) {
		this.destinatario_17_id = destinatario_17_id;
	}
	
	public int getDestinatario_18_id() {
		return destinatario_18_id;
	}
	public void setDestinatario_18_id(Integer destinatario_18_id) {
		this.destinatario_18_id = destinatario_18_id;
	}
	
	public int getDestinatario_19_id() {
		return destinatario_19_id;
	}
	public void setDestinatario_19_id(Integer destinatario_19_id) {
		this.destinatario_19_id = destinatario_19_id;
	}
	
	public int getDestinatario_20_id() {
		return destinatario_20_id;
	}
	public void setDestinatario_20_id(Integer destinatario_20_id) {
		this.destinatario_20_id = destinatario_20_id;
	}
	
	public int getDestinatario_1_num_capi_ovini() {
		return destinatario_1_num_capi_ovini;
	}
	public void setDestinatario_1_num_capi_ovini(Integer destinatario_1_num_capi_ovini) {
		this.destinatario_1_num_capi_ovini = destinatario_1_num_capi_ovini;
	}
	
	public int getDestinatario_2_num_capi_ovini() {
		return destinatario_2_num_capi_ovini;
	}
	public void setDestinatario_2_num_capi_ovini(Integer destinatario_2_num_capi_ovini) {
		this.destinatario_2_num_capi_ovini = destinatario_2_num_capi_ovini;
	}
	
	public int getDestinatario_3_num_capi_ovini() {
		return destinatario_3_num_capi_ovini;
	}
	public void setDestinatario_3_num_capi_ovini(Integer destinatario_3_num_capi_ovini) {
		this.destinatario_3_num_capi_ovini = destinatario_3_num_capi_ovini;
	}
	
	public int getDestinatario_4_num_capi_ovini() {
		return destinatario_4_num_capi_ovini;
	}
	public void setDestinatario_4_num_capi_ovini(Integer destinatario_4_num_capi_ovini) {
		this.destinatario_4_num_capi_ovini = destinatario_4_num_capi_ovini;
	}
	
	public int getDestinatario_5_num_capi_ovini() {
		return destinatario_5_num_capi_ovini;
	}
	public void setDestinatario_5_num_capi_ovini(Integer destinatario_5_num_capi_ovini) {
		this.destinatario_5_num_capi_ovini = destinatario_5_num_capi_ovini;
	}
	
	public int getDestinatario_6_num_capi_ovini() {
		return destinatario_6_num_capi_ovini;
	}
	public void setDestinatario_6_num_capi_ovini(Integer destinatario_6_num_capi_ovini) {
		this.destinatario_6_num_capi_ovini = destinatario_6_num_capi_ovini;
	}
	
	public int getDestinatario_7_num_capi_ovini() {
		return destinatario_7_num_capi_ovini;
	}
	public void setDestinatario_7_num_capi_ovini(Integer destinatario_7_num_capi_ovini) {
		this.destinatario_7_num_capi_ovini = destinatario_7_num_capi_ovini;
	}
	
	public int getDestinatario_8_num_capi_ovini() {
		return destinatario_8_num_capi_ovini;
	}
	public void setDestinatario_8_num_capi_ovini(Integer destinatario_8_num_capi_ovini) {
		this.destinatario_8_num_capi_ovini = destinatario_8_num_capi_ovini;
	}
	
	public int getDestinatario_9_num_capi_ovini() {
		return destinatario_9_num_capi_ovini;
	}
	public void setDestinatario_9_num_capi_ovini(Integer destinatario_9_num_capi_ovini) {
		this.destinatario_9_num_capi_ovini = destinatario_9_num_capi_ovini;
	}
	
	public int getDestinatario_10_num_capi_ovini() {
		return destinatario_10_num_capi_ovini;
	}
	public void setDestinatario_10_num_capi_ovini(Integer destinatario_10_num_capi_ovini) {
		this.destinatario_10_num_capi_ovini = destinatario_10_num_capi_ovini;
	}
	public int getDestinatario_11_num_capi_ovini() {
		return destinatario_11_num_capi_ovini;
	}
	public void setDestinatario_11_num_capi_ovini(Integer destinatario_11_num_capi_ovini) {
		this.destinatario_11_num_capi_ovini = destinatario_11_num_capi_ovini;
	}
	
	public int getDestinatario_12_num_capi_ovini() {
		return destinatario_12_num_capi_ovini;
	}
	public void setDestinatario_12_num_capi_ovini(Integer destinatario_12_num_capi_ovini) {
		this.destinatario_12_num_capi_ovini = destinatario_12_num_capi_ovini;
	}
	
	public int getDestinatario_13_num_capi_ovini() {
		return destinatario_13_num_capi_ovini;
	}
	public void setDestinatario_13_num_capi_ovini(Integer destinatario_13_num_capi_ovini) {
		this.destinatario_13_num_capi_ovini = destinatario_13_num_capi_ovini;
	}
	
	public int getDestinatario_14_num_capi_ovini() {
		return destinatario_14_num_capi_ovini;
	}
	public void setDestinatario_14_num_capi_ovini(Integer destinatario_14_num_capi_ovini) {
		this.destinatario_14_num_capi_ovini = destinatario_14_num_capi_ovini;
	}
	
	public int getDestinatario_15_num_capi_ovini() {
		return destinatario_15_num_capi_ovini;
	}
	public void setDestinatario_15_num_capi_ovini(Integer destinatario_15_num_capi_ovini) {
		this.destinatario_15_num_capi_ovini = destinatario_15_num_capi_ovini;
	}
	
	public int getDestinatario_16_num_capi_ovini() {
		return destinatario_16_num_capi_ovini;
	}
	public void setDestinatario_16_num_capi_ovini(Integer destinatario_16_num_capi_ovini) {
		this.destinatario_16_num_capi_ovini = destinatario_16_num_capi_ovini;
	}
	
	public int getDestinatario_17_num_capi_ovini() {
		return destinatario_17_num_capi_ovini;
	}
	public void setDestinatario_17_num_capi_ovini(Integer destinatario_17_num_capi_ovini) {
		this.destinatario_17_num_capi_ovini = destinatario_17_num_capi_ovini;
	}
	
	public int getDestinatario_18_num_capi_ovini() {
		return destinatario_18_num_capi_ovini;
	}
	public void setDestinatario_18_num_capi_ovini(Integer destinatario_18_num_capi_ovini) {
		this.destinatario_18_num_capi_ovini = destinatario_18_num_capi_ovini;
	}
	
	public int getDestinatario_19_num_capi_ovini() {
		return destinatario_19_num_capi_ovini;
	}
	public void setDestinatario_19_num_capi_ovini(Integer destinatario_19_num_capi_ovini) {
		this.destinatario_19_num_capi_ovini = destinatario_19_num_capi_ovini;
	}
	
	public int getDestinatario_20_num_capi_ovini() {
		return destinatario_20_num_capi_ovini;
	}
	public void setDestinatario_20_num_capi_ovini(Integer destinatario_20_num_capi_ovini) {
		this.destinatario_20_num_capi_ovini = destinatario_20_num_capi_ovini;
	}
	
	public int getDestinatario_1_num_capi_caprini() {
		return destinatario_1_num_capi_caprini;
	}
	public void setDestinatario_1_num_capi_caprini(Integer destinatario_1_num_capi_caprini) {
		this.destinatario_1_num_capi_caprini = destinatario_1_num_capi_caprini;
	}
	
	public int getDestinatario_2_num_capi_caprini() {
		return destinatario_2_num_capi_caprini;
	}
	public void setDestinatario_2_num_capi_caprini(Integer destinatario_2_num_capi_caprini) {
		this.destinatario_2_num_capi_caprini = destinatario_2_num_capi_caprini;
	}
	
	public int getDestinatario_3_num_capi_caprini() {
		return destinatario_3_num_capi_caprini;
	}
	public void setDestinatario_3_num_capi_caprini(Integer destinatario_3_num_capi_caprini) {
		this.destinatario_3_num_capi_caprini = destinatario_3_num_capi_caprini;
	}
	
	public int getDestinatario_4_num_capi_caprini() {
		return destinatario_4_num_capi_caprini;
	}
	public void setDestinatario_4_num_capi_caprini(Integer destinatario_4_num_capi_caprini) {
		this.destinatario_4_num_capi_caprini = destinatario_4_num_capi_caprini;
	}
	
	public int getDestinatario_5_num_capi_caprini() {
		return destinatario_5_num_capi_caprini;
	}
	public void setDestinatario_5_num_capi_caprini(Integer destinatario_5_num_capi_caprini) {
		this.destinatario_5_num_capi_caprini = destinatario_5_num_capi_caprini;
	}
	
	public int getDestinatario_6_num_capi_caprini() {
		return destinatario_6_num_capi_caprini;
	}
	public void setDestinatario_6_num_capi_caprini(Integer destinatario_6_num_capi_caprini) {
		this.destinatario_6_num_capi_caprini = destinatario_6_num_capi_caprini;
	}
	
	public int getDestinatario_7_num_capi_caprini() {
		return destinatario_7_num_capi_caprini;
	}
	public void setDestinatario_7_num_capi_caprini(Integer destinatario_7_num_capi_caprini) {
		this.destinatario_7_num_capi_caprini = destinatario_7_num_capi_caprini;
	}
	
	public int getDestinatario_8_num_capi_caprini() {
		return destinatario_8_num_capi_caprini;
	}
	public void setDestinatario_8_num_capi_caprini(Integer destinatario_8_num_capi_caprini) {
		this.destinatario_8_num_capi_caprini = destinatario_8_num_capi_caprini;
	}
	
	public int getDestinatario_9_num_capi_caprini() {
		return destinatario_9_num_capi_caprini;
	}
	public void setDestinatario_9_num_capi_caprini(Integer destinatario_9_num_capi_caprini) {
		this.destinatario_9_num_capi_caprini = destinatario_9_num_capi_caprini;
	}
	
	public int getDestinatario_10_num_capi_caprini() {
		return destinatario_10_num_capi_caprini;
	}
	public void setDestinatario_10_num_capi_caprini(Integer destinatario_10_num_capi_caprini) {
		this.destinatario_10_num_capi_caprini = destinatario_10_num_capi_caprini;
	}
	public int getDestinatario_11_num_capi_caprini() {
		return destinatario_11_num_capi_caprini;
	}
	public void setDestinatario_11_num_capi_caprini(Integer destinatario_11_num_capi_caprini) {
		this.destinatario_11_num_capi_caprini = destinatario_11_num_capi_caprini;
	}
	
	public int getDestinatario_12_num_capi_caprini() {
		return destinatario_12_num_capi_caprini;
	}
	public void setDestinatario_12_num_capi_caprini(Integer destinatario_12_num_capi_caprini) {
		this.destinatario_12_num_capi_caprini = destinatario_12_num_capi_caprini;
	}
	
	public int getDestinatario_13_num_capi_caprini() {
		return destinatario_13_num_capi_caprini;
	}
	public void setDestinatario_13_num_capi_caprini(Integer destinatario_13_num_capi_caprini) {
		this.destinatario_13_num_capi_caprini = destinatario_13_num_capi_caprini;
	}
	
	public int getDestinatario_14_num_capi_caprini() {
		return destinatario_14_num_capi_caprini;
	}
	public void setDestinatario_14_num_capi_caprini(Integer destinatario_14_num_capi_caprini) {
		this.destinatario_14_num_capi_caprini = destinatario_14_num_capi_caprini;
	}
	
	public int getDestinatario_15_num_capi_caprini() {
		return destinatario_15_num_capi_caprini;
	}
	public void setDestinatario_15_num_capi_caprini(Integer destinatario_15_num_capi_caprini) {
		this.destinatario_15_num_capi_caprini = destinatario_15_num_capi_caprini;
	}
	
	public int getDestinatario_16_num_capi_caprini() {
		return destinatario_16_num_capi_caprini;
	}
	public void setDestinatario_16_num_capi_caprini(Integer destinatario_16_num_capi_caprini) {
		this.destinatario_16_num_capi_caprini = destinatario_16_num_capi_caprini;
	}
	
	public int getDestinatario_17_num_capi_caprini() {
		return destinatario_17_num_capi_caprini;
	}
	public void setDestinatario_17_num_capi_caprini(Integer destinatario_17_num_capi_caprini) {
		this.destinatario_17_num_capi_caprini = destinatario_17_num_capi_caprini;
	}
	
	public int getDestinatario_18_num_capi_caprini() {
		return destinatario_18_num_capi_caprini;
	}
	public void setDestinatario_18_num_capi_caprini(Integer destinatario_18_num_capi_caprini) {
		this.destinatario_18_num_capi_caprini = destinatario_18_num_capi_caprini;
	}
	
	public int getDestinatario_19_num_capi_caprini() {
		return destinatario_19_num_capi_caprini;
	}
	public void setDestinatario_19_num_capi_caprini(Integer destinatario_19_num_capi_caprini) {
		this.destinatario_19_num_capi_caprini = destinatario_19_num_capi_caprini;
	}
	
	public int getDestinatario_20_num_capi_caprini() {
		return destinatario_20_num_capi_caprini;
	}
	public void setDestinatario_20_num_capi_caprini(Integer destinatario_20_num_capi_caprini) {
		this.destinatario_20_num_capi_caprini = destinatario_20_num_capi_caprini;
	}

	public int getSeqa_destinazione_allo_sblocco() {
		return seqa_destinazione_allo_sblocco;
	}
	public void setSeqa_destinazione_allo_sblocco(
			int seqa_destinazione_allo_sblocco) {
		this.seqa_destinazione_allo_sblocco = seqa_destinazione_allo_sblocco;
	}
	
	public Timestamp getSeqa_data_sblocco() {
		return seqa_data_sblocco;
	}
	public void setSeqa_data_sblocco(Timestamp seqa_data_sblocco) {
		this.seqa_data_sblocco = seqa_data_sblocco;
	}
	public String getMavam_impianto_termodistruzione() {
		return mavam_impianto_termodistruzione;
	}
	public void setMavam_impianto_termodistruzione(
			String mavam_impianto_termodistruzione) {
		this.mavam_impianto_termodistruzione = mavam_impianto_termodistruzione;
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
	
	
	

	public String getVam_provvedimenti_note() {
		return vam_provvedimenti_note;
	}

	public void setVam_provvedimenti_note(String vam_provvedimenti_note) {
		this.vam_provvedimenti_note = vam_provvedimenti_note;
	}

	public String getMavam_descrizione_luogo_verifica() {
		return mavam_descrizione_luogo_verifica;
	}
	public void setMavam_descrizione_luogo_verifica(
			String mavam_descrizione_luogo_verifica) {
		this.mavam_descrizione_luogo_verifica = mavam_descrizione_luogo_verifica;
	}
		
	
	public int getVam_num_capi_ovini() {
		return vam_num_capi_ovini;
	}
	public void setVam_num_capi_ovini(Integer vam_num_capi_ovini) {
		this.vam_num_capi_ovini = vam_num_capi_ovini;
	}
	public int getVam_num_capi_caprini() {
		return vam_num_capi_caprini;
	}
	public void setVam_num_capi_caprini(Integer vam_num_capi_caprini) {
		this.vam_num_capi_caprini = vam_num_capi_caprini;
	}
	public int getMavam_num_capi_ovini() {
		return mavam_num_capi_ovini;
	}
	public void setMavam_num_capi_ovini(int mavam_num_capi_ovini) {
		this.mavam_num_capi_ovini = mavam_num_capi_ovini;
	}
	public int getMavam_num_capi_caprini() {
		return mavam_num_capi_caprini;
	}
	public void setMavam_num_capi_caprini(int mavam_num_capi_caprini) {
		this.mavam_num_capi_caprini = mavam_num_capi_caprini;
	}
	
	public boolean isChiusa() {
		return chiusa;
	}
	public void setChiusa(boolean chiusa) {
		this.chiusa = chiusa;
	}
	
	public String getMotivazione_chiusura() {
		return motivazione_chiusura;
	}
	public void setMotivazione_chiusura(String motivazione_chiusura) {
		this.motivazione_chiusura = motivazione_chiusura;
	}
	

	public Timestamp getVam_data() {
		return vam_data;
	}
	public void setVam_data(Timestamp vam_data) {
		this.vam_data = vam_data;
	}
//	public boolean isVam_favorevole() {
//		return vam_favorevole;
//	}
//	public void setVam_favorevole(boolean vam_favorevole) {
//		this.vam_favorevole = vam_favorevole;
//	}
	
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
	public int getMavam_luogo() {
		return mavam_luogo;
	}
	public void setMavam_luogo(int mavam_luogo) {
		this.mavam_luogo = mavam_luogo;
	}
	public String getMavam_motivo() {
		return mavam_motivo;
	}
	public void setMavam_motivo(String mavam_motivo) {
		this.mavam_motivo = mavam_motivo;
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

	public String getMavam_note() {
		return mavam_note;
	}

	public void setMavam_note(String mavam_note) {
		this.mavam_note = mavam_note;
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

	public void setMavam_to_proprietario_animale(
			boolean mavam_to_proprietario_animale) {
		this.mavam_to_proprietario_animale = mavam_to_proprietario_animale;
	}

	public boolean isMavam_to_azienda_origine() {
		return mavam_to_azienda_origine;
	}

	public void setMavam_to_azienda_origine(boolean mavam_to_azienda_origine) {
		this.mavam_to_azienda_origine = mavam_to_azienda_origine;
	}

	public boolean isMavam_to_proprietario_macello() {
		return mavam_to_proprietario_macello;
	}

	public void setMavam_to_proprietario_macello(
			boolean mavam_to_proprietario_macello) {
		this.mavam_to_proprietario_macello = mavam_to_proprietario_macello;
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

//	public int getCasl_motivo() {
//		return casl_motivo;
//	}
//	public void setCasl_motivo(int casl_motivo) {
//		this.casl_motivo = casl_motivo;
//	}
	
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
	public int getId_partita() {
		return id_partita;
	}
	public void setId_partita(int id_partita) {
		this.id_partita = id_partita;
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
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public PartitaSeduta store( Connection db, ArrayList<Campione> cmps, ConfigTipo configTipo ) throws Exception
	{
		try
		{
//		MacelliUtil.setNextNumeroVerbaleSequestro( this, db );
		//MacelliUtil.setNextNumeroMacellazione( this, db );
		
		GenericBean partita = Partita.load(this.getId_partita()+"", db, configTipo);
			
		setStato_macellazione(MacelliUtil.getStatoMacellazioneSeduta(this, (Partita)partita, db, cmps ));
		
		this.entered	= new Timestamp( System.currentTimeMillis() );
		this.modified	= this.entered;
		
		String sql = "INSERT INTO m_partite_sedute( ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field;
	            }
	        }
	        
	    }
	    
	    sql += " ) VALUES (";
	    firstField = true;
	    
	    for( int i = 0; i < v.size(); i++ )
	    {
	        {
	            sql += (firstField) ? ("") : (",");
	            sql += " ?";
	            firstField = false;
	        }
	    }
	
	    sql += " )";
	    
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	            	String s = (String)o;
	            	s = s.replaceAll("u13", " ");
	            	s = s.replaceAll("u10", " ");
	            	s = s.replaceAll("\\r", " ");
	            	s = s.replaceAll("\\n", " ");
	            
	                stat.setString( i, s );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    stat.execute();
	    stat.close();
	    return PartitaSeduta.load( "" + DatabaseUtils.getCurrVal( db, "m_partite_sedute_id_seq", -1 ), db, configTipo );
		}
		catch(Exception e)
		{
			System.out.println("Errore Partita.store ->"+e.getMessage());
			throw e ;
		}
	  
	}

    protected static int parseType(Class<?> type)
    {
        int ret = -1;
        
        String name = type.getSimpleName();
        
        if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
        {
            ret = INT;
        }
        else if( name.equalsIgnoreCase( "string" ) )
        {
            ret = STRING;
        }
        else if( name.equalsIgnoreCase( "double" ) )
        {
            ret = DOUBLE;
        }
        else if( name.equalsIgnoreCase( "float" ) )
        {
            ret = FLOAT;
        }
        else if( name.equalsIgnoreCase( "timestamp" ) )
        {
            ret = TIMESTAMP;
        }
        else if( name.equalsIgnoreCase( "date" ) )
        {
            ret = DATE;
        }
        else if( name.equalsIgnoreCase( "boolean" ) )
        {
            ret = BOOLEAN;
        }
        
        return ret;
    }
    
	public static PartitaSeduta load(String id, Connection db, ConfigTipo configTipo )
	{

		PartitaSeduta				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM m_partite_sedute  WHERE id = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat.setInt( 1, iid );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db, configTipo );
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}
					
					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	
	}
	
	public static PartitaSeduta loadByNumero(String numero, Connection db, ConfigTipo configTipo )
	{

		PartitaSeduta				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (numero != null) && (numero.trim().length() > 0) )
		{
			try
			{
				stat	= db.prepareStatement( "SELECT * FROM m_partite_sedute  WHERE numero = ? and trashed_date IS NULL ORDER BY entered DESC" );
				stat.setString( 1, numero );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db, configTipo );
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}
					
					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	
	}
	
	public static PartitaSeduta loadByPartita(String partita, Connection db, int idMacello, ConfigTipo configTipo )
	{

		PartitaSeduta				ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (partita != null) && (partita.trim().length() > 0) )
		{
			try
			{
				
				stat	= db.prepareStatement( "SELECT * FROM m_partite_sedute  WHERE cd_partita ilike ? ORDER BY entered DESC" );
				stat.setString( 1, partita );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res, db, configTipo );
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if( res != null )
					{
						res.close();
						res = null;
					}
					
					if( stat != null )
					{
						stat.close();
						stat = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	
	}
	
	private static PartitaSeduta loadResultSet( ResultSet res, Connection db, ConfigTipo configTipo ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		PartitaSeduta ret = new PartitaSeduta();
	
		Field[]	f = ret.getClass().getDeclaredFields();
		Method[]	m = ret.getClass().getMethods();
		for( int i = 0; i < f.length; i++ )
		{
			Method getter	= null;
	    	 Method setter	= null;
	    	 Field	campo	= f[i];
	         String field = f[i].getName();
	         for( int j = 0; j < m.length; j++ )
	         {
	             String met = m[j].getName();
	             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
	             {
	                  getter = m[j];
	             }
	             else if( met.equalsIgnoreCase( "SET" + field ) )
	             {
	                 setter = m[j];
	             }
	         }	
	     
	     
	         if( (getter != null) && (setter != null) && (campo != null) )
	         {
	        	 Object o = null;
	             
	             switch ( parseType( campo.getType() ) )
	             {
	             case INT:
	                 o = res.getInt( field );
	                 break;
	             case STRING:
	                 o = res.getString( field );
	                 break;
	             case BOOLEAN:
	                 o = res.getBoolean( field );
	                 break;
	             case TIMESTAMP:
	                 o = res.getTimestamp( field );
	                 break;
	             case DATE:
	                 o = res.getDate( field );
	                 break;
	             case FLOAT:
	                 o = res.getFloat( field );
	                 break;
	             case DOUBLE:
	                 o = res.getDouble( field );
	                 break;
	             }
	             
	             setter.invoke( ret, o );
	         
	         }
		}
		
		ret.color = MacelliUtil.getRecordColor( ret, db, configTipo );
	//	Partita.loadPartita(ret, db, configTipo, ret.getId_partita());
		return ret;
	}

	public void update(Connection db, ArrayList<Campione>	campioni,ConfigTipo configTipo)
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,Exception
	{
		MacelliUtil.setNextNumeroMacellazione( this, db );
		GenericBean partita = Partita.load(this.getId_partita()+"", db, configTipo);
		setStato_macellazione(MacelliUtil.getStatoMacellazioneSeduta(this, (Partita)partita, db, campioni));
		
		String sql = "UPDATE m_partite_sedute SET ";
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		boolean firstField = true;
		
	    for( int i = 0; i < f.length; i++ )
	    {
	        String field = f[i].getName();
	        for( int j = 0; j < m.length; j++ )
	        {
	            String met = m[j].getName();
	            if( !field.equalsIgnoreCase( "id" ) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
	            {
	                 v.add( m[j] );
	                 v2.add( f[i] );
	                 sql += (firstField) ? ("") : (",");
	                 firstField = false;
	                 sql += " " + field + " = ?";
	            }
	        }
	        
	    }  
	    
	    sql += " WHERE id = ?";
	
	    PreparedStatement stat = db.prepareStatement( sql );
		
	    for( int i = 1; i <= v.size(); i++ )
	    {
	        Object o = v.elementAt( i - 1 ).invoke( this );
	        
	        if( o == null )
	        {
	            stat.setNull( i, parseType( v2.elementAt( i - 1 ).getType() ) );
	        }
	        else
	        {
	            switch ( parseType( o.getClass() ) )
	            {
	            case INT:
	                stat.setInt( i, (Integer)o );
	                break;
	            case STRING:
	                stat.setString( i, (String)o );
	                break;
	            case BOOLEAN:
	                stat.setBoolean( i, (Boolean)o );
	                break;
	            case TIMESTAMP:
	                stat.setTimestamp( i, (Timestamp)o );
	                break;
	            case DATE:
	                stat.setDate( i, (Date)o );
	                break;
	            case FLOAT:
	                stat.setFloat( i, (Float)o );
	                break;
	            case DOUBLE:
	                stat.setDouble( i, (Double)o );
	                break;
	            }
	        }
	    }
	    
	    stat.setInt( v.size() + 1, id );
	    
	    stat.execute();
	    stat.close();
			
	}

	public void chiudi(Connection db, String motivazione) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		MacelliUtil.setNextNumeroMacellazione( this, db );
		
		String sql = "UPDATE m_partite_sedute SET motivazione_chiusura = ?, chiusa = true WHERE id = ?";
	
	    PreparedStatement stat = db.prepareStatement( sql );
	    stat.setString( 1, motivazione );
	    stat.setInt   ( 2, id );
	    
	    stat.execute();
	    stat.close();
	}
	
	public static void delete( int id, int user_id, Connection db )
	{
		PreparedStatement	stat	= null;
		try
		{
			stat	= db.prepareStatement( 
					"UPDATE m_partite_sedute SET modified = CURRENT_TIMESTAMP, trashed_date = CURRENT_TIMESTAMP, modified_by = ? " +
					"WHERE id = ? AND trashed_date IS NULL" );

			stat.setInt( 1, user_id );
			stat.setInt( 2, id );
			
			stat.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void popolaCampiByPartita(Connection db, ConfigTipo configTipo) throws Exception
	{
		
		GenericBean p = GenericBean.load(this.getId_partita()+"", db, configTipo);
		this.setCd_partita(((Partita)p).getCd_partita());
		this.setCd_asl(p.getCd_asl());
		this.setCd_bse(((Partita)p).getCd_bse());
		//this.setCd_categoria_rischio(p.getCd_categoria_rischio());
		this.setCd_codice_azienda(p.getCd_codice_azienda());
		this.setCd_codice_azienda_provenienza(p.getCd_codice_azienda_provenienza());
		this.setCd_codice_speditore(p.getCd_codice_speditore());
		this.setCd_data_arrivo_macello(p.getCd_data_arrivo_macello());
		this.setCd_data_arrivo_macello_flag_dichiarata(p.isCd_data_arrivo_macello_flag_dichiarata());
		this.setCd_data_mod4(p.getCd_data_mod4());
		this.setCd_id_speditore(p.getCd_id_speditore());
		this.setCd_info_catena_alimentare(p.isCd_info_catena_alimentare());
		this.setCd_macellazione_differita(p.getCd_macellazione_differita());
		this.setCd_mod4(p.getCd_mod4());
		this.setCd_note(p.getCd_note());
		this.setCd_num_capi_caprini(((Partita)p).getCd_num_capi_caprini());
		this.setCd_num_capi_ovini_da_testare(((Partita)p).getCd_num_capi_ovini_da_testare());
		this.setCd_num_capi_caprini_da_testare(((Partita)p).getCd_num_capi_caprini_da_testare());
		this.setCd_num_capi_ovini(((Partita)p).getCd_num_capi_ovini());
		this.setCd_prov_stato_comunitario(p.isCd_prov_stato_comunitario());
		this.setCd_provenienza_comune(p.getCd_provenienza_comune());
		this.setCd_provenienza_regione(p.getCd_provenienza_regione());
		this.setCd_provenienza_stato(p.getCd_provenienza_stato());
		this.setCd_seduta_macellazione(p.getCd_seduta_macellazione());
		this.setCd_speditore(p.getCd_speditore());
		this.setCd_targa_mezzo_trasporto(p.getCd_targa_mezzo_trasporto());
		this.setCd_tipo_mezzo_trasporto(p.getCd_tipo_mezzo_trasporto());
		this.setCd_trasporto_superiore8ore(p.isCd_trasporto_superiore8ore());
		this.setCd_veterinario_1(p.getCd_veterinario_1());
		this.setCd_veterinario_2(p.getCd_veterinario_2());
		this.setCd_veterinario_3(p.getCd_veterinario_3());
		this.setCd_vincolo_sanitario(p.isCd_vincolo_sanitario());
		this.setCd_vincolo_sanitario_motivo(p.getCd_vincolo_sanitario_motivo());
		this.setCasl_num_capi_ovini(((Partita)p).getCasl_num_capi_ovini());
		this.setCasl_num_capi_caprini(((Partita)p).getCasl_num_capi_caprini());
		this.setCasl_to_altro(p.isCasl_to_altro());
		this.setCasl_to_altro_testo(p.getCasl_to_altro_testo());
		this.setCasl_to_asl_origine(p.isCasl_to_asl_origine());
		this.setCasl_to_azienda_origine(p.isCasl_to_azienda_origine());
		this.setCasl_to_pif(p.isCasl_to_pif());
		this.setCasl_to_proprietario_animale(p.isCasl_to_proprietario_animale());
		this.setCasl_to_proprietario_macello(p.isCasl_to_proprietario_macello());
		this.setCasl_to_regione(p.isCasl_to_regione());
		this.setCasl_to_uvac(p.isCasl_to_uvac());
		this.setBse_data_prelievo(p.getBse_data_prelievo());
		this.setBse_data_ricezione_esito(p.getBse_data_ricezione_esito());
		this.setBse_esito(p.getBse_esito());
		this.setBse_note(p.getBse_note());
		this.setCasl_note_prevvedimento(p.getCasl_note_prevvedimento());
		this.setCasl_data(p.getCasl_data());
		this.setCasl_info_richiesta(p.getCasl_info_richiesta());
		Object o = Class.forName(configTipo.getPackageBean()+configTipo.getNomeBean()).cast(p);
		Integer num_capi_ovini = (Integer)ReflectionUtil.invocaMetodo(o, "getCasl_num_capi_ovini", null);
		Integer num_capi_caprini = (Integer)ReflectionUtil.invocaMetodo(o, "getCasl_num_capi_caprini", null);
		this.setCasl_num_capi_caprini(num_capi_caprini);
		this.setCasl_num_capi_ovini(num_capi_ovini);
		this.setRca_data(p.getRca_data());
		this.setRca_note(p.getRca_note());
		this.setProprietario(((Partita)p).getProprietario());
	}
	
	public static ArrayList<Campione> loadCampioni( int id_capo, Connection db )
	{

		ArrayList<Campione>	ret		= new ArrayList<Campione>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_campioni WHERE id_seduta = ? and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetCampioni( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	
	}
	
	public static Tampone loadTampone( Integer id, Connection db )
	{

		Tampone	ret		= new Tampone();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT tamp.* " +
					"FROM m_vpm_tamponi as tamp, m_vpm_capi_tamponi as tamp2 where tamp.id = tamp2.id_m_vpm_tamponi and " +
					"tamp2.id_m_seduta = ? and "+
					"tamp.trashed_date IS NULL and tamp.trashed_date is null " );
			stat.setInt( 1, id );
						res		= stat.executeQuery();
			
			if (res.next())
				 ret =loadResultSetTampone( res ) ;
			loadTipoRicerca(db,ret);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	
	}
	
	public static ArrayList<Organi> loadOrgani( int id_capo, Connection db )
	{
		ArrayList<Organi>	ret		= new ArrayList<Organi>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( 
					"SELECT * FROM m_lcso_organi WHERE trashed_date IS NULL AND enabled AND id_seduta = ? ORDER BY id ASC" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	
	
	private static Organi loadResultSet( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Organi ret = new Organi();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		            	 if (!(( parseType( campo.getType() ) == INT)   && m[j].getParameterTypes() [0] == String.class))
		            		 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	
	private static Campione loadResultSetCampioni( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Campione ret = new Campione();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	private static Tampone loadResultSetTampone( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Tampone ret = new Tampone();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if(! field.equals("tipo_ricerca") && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ))
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	public static  void loadTipoRicerca (Connection db ,Tampone t) throws SQLException
	{
		ResultSet rs = null ;
		PreparedStatement pst = db.prepareStatement("select an.*,l.description as descrizione_ricerca from m_vpm_tamponi_analiti an join lookup_ricerca_tamponi_macelli l on l.code =an.id_tipo_ricerca where id_tampone = ?");
		pst.setInt(1, t.getId());
		rs = pst.executeQuery() ;
		while (rs.next())
		{
			TipoRicerca rr = new TipoRicerca();
			rr.setId(rs.getInt("id_tipo_ricerca"));
			rr.setDescrizione(rs.getString("descrizione_ricerca"));
			t.getTipo_ricerca().add(rr);
		}
		
	}
	
	public static ArrayList<NonConformita> loadNonConformita( int id_capo, Connection db )
	{

		ArrayList<NonConformita>	ret		= new ArrayList<NonConformita>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_capi_non_conformita WHERE id_seduta = ? and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetNonConformita( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	
	}
	
	private static NonConformita loadResultSetNonConformita( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			NonConformita ret = new NonConformita();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	public static ArrayList<PatologiaRilevata> loadPatologieRilevate( int id_capo, Connection db )
	{
	
		ArrayList<PatologiaRilevata>	ret		= new ArrayList<PatologiaRilevata>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_patologie_rilevate WHERE id_seduta = ? and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetPatologieRilevate( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	
	}
	
	public static ArrayList<Esito> loadEsito( int id_capo, Connection db )
	{
	
		ArrayList<Esito>	ret		= new ArrayList<Esito>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_esiti_riscontrati WHERE id_seduta = ? and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetEsito( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	
	}
	
	private static PatologiaRilevata loadResultSetPatologieRilevate( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			PatologiaRilevata ret = new PatologiaRilevata();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	private static Esito loadResultSetEsito( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Esito ret = new Esito();
		
			Field[]	f = ret.getClass().getDeclaredFields();
			Method[]	m = ret.getClass().getMethods();
			for( int i = 0; i < f.length; i++ )
			{
				Method getter	= null;
		    	 Method setter	= null;
		    	 Field	campo	= f[i];
		         String field = f[i].getName();
		         for( int j = 0; j < m.length; j++ )
		         {
		             String met = m[j].getName();
		             if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
		             {
		                  getter = m[j];
		             }
		             else if( met.equalsIgnoreCase( "SET" + field ) )
		             {
		                 setter = m[j];
		             }
		         }	
		     
		     
		         if( (getter != null) && (setter != null) && (campo != null) )
		         {
		        	 Object o = null;
		             
		             switch ( parseType( campo.getType() ) )
		             {
		             case INT:
		                 o = res.getInt( field );
		                 break;
		             case STRING:
		                 o = res.getString( field );
		                 break;
		             case BOOLEAN:
		                 o = res.getBoolean( field );
		                 break;
		             case TIMESTAMP:
		                 o = res.getTimestamp( field );
		                 break;
		             case DATE:
		                 o = res.getDate( field );
		                 break;
		             case FLOAT:
		                 o = res.getFloat( field );
		                 break;
		             case DOUBLE:
		                 o = res.getDouble( field );
		                 break;
		             }
		             
		             setter.invoke( ret, o );
		         
		         }
			}
		
			return ret;
		}
	
	public int getNumDestinatariSelezionati()
	{
		if(this.getDestinatario_20_id()>0 || this.getDestinatario_19_id()>0)
			return 10;
		else if(this.getDestinatario_18_id()>0 || this.getDestinatario_17_id()>0)
			return 9;	
		else if(this.getDestinatario_16_id()>0 || this.getDestinatario_15_id()>0)
			return 8;
		else if(this.getDestinatario_14_id()>0 || this.getDestinatario_13_id()>0)
			return 7;
		else if(this.getDestinatario_12_id()>0 || this.getDestinatario_11_id()>0)
			return 6;
		else if(this.getDestinatario_10_id()>0 || this.getDestinatario_9_id()>0)
			return 5;
		else if(this.getDestinatario_8_id()>0 || this.getDestinatario_7_id()>0)
			return 4;
		else if(this.getDestinatario_6_id()>0 || this.getDestinatario_5_id()>0)
			return 3;
		else 
			return 2;
	}
	
	public static int nextId(Connection db, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		int max = 1;
		
		String sql = "SELECT max(id) as max FROM m_partite_sedute " ;
		try
		{	
			stat = db.prepareStatement( sql );
			
			res = stat.executeQuery();
			
			while( res.next() )
			{
				max = res.getInt("max")+1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return max;
	}
}
