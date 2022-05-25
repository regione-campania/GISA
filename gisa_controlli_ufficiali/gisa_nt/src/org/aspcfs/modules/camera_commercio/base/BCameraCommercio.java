/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.camera_commercio.base;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.aspcfs.utils.CsvLunghezzaFissa;

import com.darkhorseventures.framework.beans.GenericBean;

public class BCameraCommercio extends GenericBean
{
	
	private static final long serialVersionUID = 7127566929908851546L;

	private static final int INT = Types.INTEGER;

	private static final int STRING = Types.VARCHAR;

	private static final int DOUBLE = Types.DOUBLE;

	private static final int FLOAT = Types.FLOAT;

	private static final int TIMESTAMP = Types.TIMESTAMP;

	private static final int DATE = Types.DATE;

	private static final int BOOLEAN = Types.BOOLEAN;
	
	private Timestamp data_importazione_osa;
	private int org_id;
	
	public int getOrg_id() {
		return org_id;
	}

	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}

	private int id = -1;
	
//	PROGRESSIVO DELLA POSIZIONE U.L. D'INTERESSE E RELATIVI ESTREMI DELLA POSIZIONE REA D'INTERESSE
	private String		provincia_cciaa;
	private String		num_iscrizione;
	private String		progressivo_ul;
	private String		cf_impresa;
	private String		num_registro_imprese;
	private Timestamp	data_iscrizione_posiz_rea;
	private Timestamp	data_caricamento_archivio_pos_rea;
	
//ESTREMI SOGGETTO IMPRESA ALLA QUALE APPARTIENE LA POSIZIONE REA D'INTERESSE
//	ESTREMI INDIRIZZO SEDE LEGALE
	private String	provincia_sede_legale;
	private String	codice_stato_sede_legale;
	private String	codice_istat_comune_sede_legale;
	private String	descrizione_comune_sede_legale;
	private String	descrizione_localita_sede_legale;
	private String	cod_toponimo_sede_legale;
	private String	via_sede_legale;
	private String	civico_sede_legale;
	private String	cap_sede_legale;
	private String	cod_stradario_sede_legale;
	
	private String data_iscrizione_registro_imprese;
	
//ESTREMI SEZIONI SPECIALI DEL R.I. (3 volte)
	private String		cod_sez_spec_1;
	private Timestamp	data_inizio_appartenenza_sez_spec_1;
	private boolean		flag_coltiv_diretto_1;
	private Timestamp	data_fine_appartenenza_sez_spec_1;
	
	private String		cod_sez_spec_2;
	private Timestamp	data_inizio_appartenenza_sez_spec_2;
	private boolean		flag_coltiv_diretto_2;
	private Timestamp	data_fine_appartenenza_sez_spec_2;
	
	private String		cod_sez_spec_3;
	private Timestamp	data_inizio_appartenenza_sez_spec_3;
	private boolean		flag_coltiv_diretto_3;
	private Timestamp 	data_fine_appartenenza_sez_spec_3;
	
	
	private String		ragione_sociale;
	private String		partita_iva;
	private String		cod_natura_giuridica;
	private String		stato_attivita_impresa;
	private String		capitale_sociale;
	private String		valuta_capitale_sociale;
	private String		oggetto_sociale_1;
	private String		oggetto_sociale_2;
	private String		oggetto_sociale_3;
	private String		oggetto_sociale_4;
	private boolean		flag_omissis_oggetto_sociale;
	private String		cod_tipo_liquidazione;
	private Timestamp	data_apertura_liquidazione;
	
//ESTREMI DELLA POSIZIONE U.L. D'INTERESSE
	private String		cod_tipo_ul;
	private String		tipo_localizzazione;
	private String		denominazione_ul;
	private String		insegna_ul;
	private Timestamp	data_apertura_ul;
	
//ESTREMI INDIRIZZO DELLA U.L.
	private String codice_stato_ul;
	private String provincia_ul;
	private String codice_istat_ul;
	private String descrizione_comune_ul;
	private String descrizione_localita_ul;
	private String cod_toponimo_ul;
	private String via_ul;
	private String civico_ul;
	private String cap_ul;
	private String cod_stradario_ul;
	
	private String		telefono_ul;
	private String		stato_attivita_ul;
	private String		cod_acusale_cessazione_ul;
	private Timestamp	data_cessazione_ul;
	private Timestamp 	data_denuncia_cessazione_ul;
	private String		anno_denuncia_addetti_ul;
	private String		num_addetti_familiari_ul;
	private String		num_addetti_subordinati_ul;
	private String		cod_attivita_istat_91_primario;
	
//ATTIVITA' DELLA U.L.
	private String	descrizione_attivita_ul_1;
	private String	descrizione_attivita_ul_2;
	private String	descrizione_attivita_ul_3;
	private String	descrizione_attivita_ul_4;
	private boolean	flag_omissis_descrizione_attivita_ul;
	
//ESTREMI ALBI/RUOLI DELLA U.L. (5 volte)
	private String cod_tipo_ruolo_1;

	private String 		cod_forma_ruolo_1;
	private String		num_ruolo_1;
	private Timestamp	data_inizio_ruolo_1;
	private Timestamp	data_cessazione_ruolo_1;
	
	private String 		cod_tipo_ruolo_2;
	private String		cod_forma_ruolo_2;
	private String		num_ruolo_2;
	private Timestamp	data_inizio_ruolo_2;
	private Timestamp	data_cessazione_ruolo_2;

	private String		cod_tipo_ruolo_3;
	private String		cod_forma_ruolo_3;
	private String		num_ruolo_3;
	private Timestamp	data_inizio_ruolo_3;
	private Timestamp	data_cessazione_ruolo_3;

	private String		cod_tipo_ruolo_4;
	private String		cod_forma_ruolo_4;
	private String		num_ruolo_4;
	private Timestamp	data_inizio_ruolo_4;
	private Timestamp	data_cessazione_ruolo_4;

	private String		cod_tipo_ruolo_5;
	private String		cod_forma_ruolo_5;
	private String		num_ruolo_5;
	private Timestamp	data_inizio_ruolo_5;
	private Timestamp 	data_cessazione_ruolo_5;
	
	
	private boolean flag_omissis_albi_ruoli;



	public Timestamp getData_importazione_osa() {
		return data_importazione_osa;
	}

	public void setData_importazione_osa(Timestamp data_importazione_osa) {
		this.data_importazione_osa = data_importazione_osa;
	}

	public String getProvincia_cciaa() {
		return provincia_cciaa;
	}

	public void setProvincia_cciaa(String provincia_cciaa) {
		this.provincia_cciaa = provincia_cciaa;
	}

	public String getNum_iscrizione() {
		return num_iscrizione;
	}

	public void setNum_iscrizione(String num_iscrizione) {
		this.num_iscrizione = num_iscrizione;
	}

	public String getProgressivo_ul() {
		return progressivo_ul;
	}

	public void setProgressivo_ul(String progressivo_ul) {
		this.progressivo_ul = progressivo_ul;
	}

	public String getCf_impresa() {
		return cf_impresa;
	}

	public void setCf_impresa(String cf_impresa) {
		this.cf_impresa = cf_impresa;
	}

	public String getNum_registro_imprese() {
		return num_registro_imprese;
	}

	public void setNum_registro_imprese(String num_registro_imprese) {
		this.num_registro_imprese = num_registro_imprese;
	}

	public Timestamp getData_iscrizione_posiz_rea() {
		return data_iscrizione_posiz_rea;
	}

	public void setData_iscrizione_posiz_rea(Timestamp data_iscrizione_posiz_rea) {
		this.data_iscrizione_posiz_rea = data_iscrizione_posiz_rea;
	}

	public Timestamp getData_caricamento_archivio_pos_rea() {
		return data_caricamento_archivio_pos_rea;
	}

	public void setData_caricamento_archivio_pos_rea(
			Timestamp data_caricamento_archivio_pos_rea) {
		this.data_caricamento_archivio_pos_rea = data_caricamento_archivio_pos_rea;
	}

	public String getProvincia_sede_legale() {
		return provincia_sede_legale;
	}

	public void setProvincia_sede_legale(String provincia_sede_legale) {
		this.provincia_sede_legale = provincia_sede_legale;
	}

	public String getCodice_stato_sede_legale() {
		return codice_stato_sede_legale;
	}

	public void setCodice_stato_sede_legale(String codice_stato_sede_legale) {
		this.codice_stato_sede_legale = codice_stato_sede_legale;
	}

	public String getCodice_istat_comune_sede_legale() {
		return codice_istat_comune_sede_legale;
	}

	public void setCodice_istat_comune_sede_legale(
			String codice_istat_comune_sede_legale) {
		this.codice_istat_comune_sede_legale = codice_istat_comune_sede_legale;
	}

	public String getDescrizione_comune_sede_legale() {
		return descrizione_comune_sede_legale;
	}

	public void setDescrizione_comune_sede_legale(
			String descrizione_comune_sede_legale) {
		this.descrizione_comune_sede_legale = descrizione_comune_sede_legale;
	}

	public String getDescrizione_localita_sede_legale() {
		return descrizione_localita_sede_legale;
	}

	public void setDescrizione_localita_sede_legale(
			String descrizione_localita_sede_legale) {
		this.descrizione_localita_sede_legale = descrizione_localita_sede_legale;
	}

	public String getCod_toponimo_sede_legale() {
		return cod_toponimo_sede_legale;
	}

	public void setCod_toponimo_sede_legale(String cod_toponimo_sede_legale) {
		this.cod_toponimo_sede_legale = cod_toponimo_sede_legale;
	}

	public String getVia_sede_legale() {
		return via_sede_legale;
	}

	public void setVia_sede_legale(String via_sede_legale) {
		this.via_sede_legale = via_sede_legale;
	}

	public String getCivico_sede_legale() {
		return civico_sede_legale;
	}

	public void setCivico_sede_legale(String civico_sede_legale) {
		this.civico_sede_legale = civico_sede_legale;
	}

	public String getCap_sede_legale() {
		return cap_sede_legale;
	}

	public void setCap_sede_legale(String cap_sede_legale) {
		this.cap_sede_legale = cap_sede_legale;
	}

	public String getCod_stradario_sede_legale() {
		return cod_stradario_sede_legale;
	}

	public void setCod_stradario_sede_legale(String cod_stradario_sede_legale) {
		this.cod_stradario_sede_legale = cod_stradario_sede_legale;
	}

	public String getData_iscrizione_registro_imprese() {
		return data_iscrizione_registro_imprese;
	}

	public void setData_iscrizione_registro_imprese(
			String data_iscrizione_registro_imprese) {
		this.data_iscrizione_registro_imprese = data_iscrizione_registro_imprese;
	}

	public String getCod_sez_spec_1() {
		return cod_sez_spec_1;
	}

	public void setCod_sez_spec_1(String cod_sez_spec_1) {
		this.cod_sez_spec_1 = cod_sez_spec_1;
	}

	public Timestamp getData_inizio_appartenenza_sez_spec_1() {
		return data_inizio_appartenenza_sez_spec_1;
	}

	public void setData_inizio_appartenenza_sez_spec_1(
			Timestamp data_inizio_appartenenza_sez_spec_1) {
		this.data_inizio_appartenenza_sez_spec_1 = data_inizio_appartenenza_sez_spec_1;
	}

	public boolean isFlag_coltiv_diretto_1() {
		return flag_coltiv_diretto_1;
	}

	public void setFlag_coltiv_diretto_1(boolean flag_coltiv_diretto_1) {
		this.flag_coltiv_diretto_1 = flag_coltiv_diretto_1;
	}

	public Timestamp getData_fine_appartenenza_sez_spec_1() {
		return data_fine_appartenenza_sez_spec_1;
	}

	public void setData_fine_appartenenza_sez_spec_1(
			Timestamp data_fine_appartenenza_sez_spec_1) {
		this.data_fine_appartenenza_sez_spec_1 = data_fine_appartenenza_sez_spec_1;
	}

	public String getCod_sez_spec_2() {
		return cod_sez_spec_2;
	}

	public void setCod_sez_spec_2(String cod_sez_spec_2) {
		this.cod_sez_spec_2 = cod_sez_spec_2;
	}

	public Timestamp getData_inizio_appartenenza_sez_spec_2() {
		return data_inizio_appartenenza_sez_spec_2;
	}

	public void setData_inizio_appartenenza_sez_spec_2(
			Timestamp data_inizio_appartenenza_sez_spec_2) {
		this.data_inizio_appartenenza_sez_spec_2 = data_inizio_appartenenza_sez_spec_2;
	}

	public boolean isFlag_coltiv_diretto_2() {
		return flag_coltiv_diretto_2;
	}

	public void setFlag_coltiv_diretto_2(boolean flag_coltiv_diretto_2) {
		this.flag_coltiv_diretto_2 = flag_coltiv_diretto_2;
	}

	public Timestamp getData_fine_appartenenza_sez_spec_2() {
		return data_fine_appartenenza_sez_spec_2;
	}

	public void setData_fine_appartenenza_sez_spec_2(
			Timestamp data_fine_appartenenza_sez_spec_2) {
		this.data_fine_appartenenza_sez_spec_2 = data_fine_appartenenza_sez_spec_2;
	}

	public String getCod_sez_spec_3() {
		return cod_sez_spec_3;
	}

	public void setCod_sez_spec_3(String cod_sez_spec_3) {
		this.cod_sez_spec_3 = cod_sez_spec_3;
	}

	public Timestamp getData_inizio_appartenenza_sez_spec_3() {
		return data_inizio_appartenenza_sez_spec_3;
	}

	public void setData_inizio_appartenenza_sez_spec_3(
			Timestamp data_inizio_appartenenza_sez_spec_3) {
		this.data_inizio_appartenenza_sez_spec_3 = data_inizio_appartenenza_sez_spec_3;
	}

	public boolean isFlag_coltiv_diretto_3() {
		return flag_coltiv_diretto_3;
	}

	public void setFlag_coltiv_diretto_3(boolean flag_coltiv_diretto_3) {
		this.flag_coltiv_diretto_3 = flag_coltiv_diretto_3;
	}

	public Timestamp getData_fine_appartenenza_sez_spec_3() {
		return data_fine_appartenenza_sez_spec_3;
	}

	public void setData_fine_appartenenza_sez_spec_3(
			Timestamp data_fine_appartenenza_sez_spec_3) {
		this.data_fine_appartenenza_sez_spec_3 = data_fine_appartenenza_sez_spec_3;
	}

	public String getRagione_sociale() {
		return ragione_sociale;
	}

	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}

	public String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public String getCod_natura_giuridica() {
		return cod_natura_giuridica;
	}

	public void setCod_natura_giuridica(String cod_natura_giuridica) {
		this.cod_natura_giuridica = cod_natura_giuridica;
	}

	public String getStato_attivita_impresa() {
		return stato_attivita_impresa;
	}

	public void setStato_attivita_impresa(String stato_attivita_impresa) {
		this.stato_attivita_impresa = stato_attivita_impresa;
	}

	public String getCapitale_sociale() {
		return capitale_sociale;
	}

	public void setCapitale_sociale(String capitale_sociale) {
		this.capitale_sociale = capitale_sociale;
	}

	public String getValuta_capitale_sociale() {
		return valuta_capitale_sociale;
	}

	public void setValuta_capitale_sociale(String valuta_capitale_sociale) {
		this.valuta_capitale_sociale = valuta_capitale_sociale;
	}

	public String getOggetto_sociale_1() {
		return oggetto_sociale_1;
	}

	public void setOggetto_sociale_1(String oggetto_sociale_1) {
		this.oggetto_sociale_1 = oggetto_sociale_1;
	}

	public String getOggetto_sociale_2() {
		return oggetto_sociale_2;
	}

	public void setOggetto_sociale_2(String oggetto_sociale_2) {
		this.oggetto_sociale_2 = oggetto_sociale_2;
	}

	public String getOggetto_sociale_3() {
		return oggetto_sociale_3;
	}

	public void setOggetto_sociale_3(String oggetto_sociale_3) {
		this.oggetto_sociale_3 = oggetto_sociale_3;
	}

	public String getOggetto_sociale_4() {
		return oggetto_sociale_4;
	}

	public void setOggetto_sociale_4(String oggetto_sociale_4) {
		this.oggetto_sociale_4 = oggetto_sociale_4;
	}

	public boolean isFlag_omissis_oggetto_sociale() {
		return flag_omissis_oggetto_sociale;
	}

	public void setFlag_omissis_oggetto_sociale(boolean flag_omissis_oggetto_sociale) {
		this.flag_omissis_oggetto_sociale = flag_omissis_oggetto_sociale;
	}

	public String getCod_tipo_liquidazione() {
		return cod_tipo_liquidazione;
	}

	public void setCod_tipo_liquidazione(String cod_tipo_liquidazione) {
		this.cod_tipo_liquidazione = cod_tipo_liquidazione;
	}

	public Timestamp getData_apertura_liquidazione() {
		return data_apertura_liquidazione;
	}

	public void setData_apertura_liquidazione(Timestamp data_apertura_liquidazione) {
		this.data_apertura_liquidazione = data_apertura_liquidazione;
	}

	public String getCod_tipo_ul() {
		return cod_tipo_ul;
	}

	public void setCod_tipo_ul(String cod_tipo_ul) {
		this.cod_tipo_ul = cod_tipo_ul;
	}

	public String getTipo_localizzazione() {
		return tipo_localizzazione;
	}

	public void setTipo_localizzazione(String tipo_localizzazione) {
		this.tipo_localizzazione = tipo_localizzazione;
	}

	public String getDenominazione_ul() {
		return denominazione_ul;
	}

	public void setDenominazione_ul(String denominazione_ul) {
		this.denominazione_ul = denominazione_ul;
	}

	public String getInsegna_ul() {
		return insegna_ul;
	}

	public void setInsegna_ul(String insegna_ul) {
		this.insegna_ul = insegna_ul;
	}

	public Timestamp getData_apertura_ul() {
		return data_apertura_ul;
	}

	public void setData_apertura_ul(Timestamp data_apertura_ul) {
		this.data_apertura_ul = data_apertura_ul;
	}

	public String getCodice_stato_ul() {
		return codice_stato_ul;
	}

	public void setCodice_stato_ul(String codice_stato_ul) {
		this.codice_stato_ul = codice_stato_ul;
	}

	public String getProvincia_ul() {
		return provincia_ul;
	}

	public void setProvincia_ul(String provincia_ul) {
		this.provincia_ul = provincia_ul;
	}

	public String getCodice_istat_ul() {
		return codice_istat_ul;
	}

	public void setCodice_istat_ul(String codice_istat_ul) {
		this.codice_istat_ul = codice_istat_ul;
	}

	public String getDescrizione_comune_ul() {
		return descrizione_comune_ul;
	}

	public void setDescrizione_comune_ul(String descrizione_comune_ul) {
		this.descrizione_comune_ul = descrizione_comune_ul;
	}

	public String getDescrizione_localita_ul() {
		return descrizione_localita_ul;
	}

	public void setDescrizione_localita_ul(String descrizione_localita_ul) {
		this.descrizione_localita_ul = descrizione_localita_ul;
	}

	public String getCod_toponimo_ul() {
		return cod_toponimo_ul;
	}

	public void setCod_toponimo_ul(String cod_toponimo_ul) {
		this.cod_toponimo_ul = cod_toponimo_ul;
	}

	public String getVia_ul() {
		return via_ul;
	}

	public void setVia_ul(String via_ul) {
		this.via_ul = via_ul;
	}

	public String getCivico_ul() {
		return civico_ul;
	}

	public void setCivico_ul(String civico_ul) {
		this.civico_ul = civico_ul;
	}

	public String getCap_ul() {
		return cap_ul;
	}

	public void setCap_ul(String cap_ul) {
		this.cap_ul = cap_ul;
	}

	public String getCod_stradario_ul() {
		return cod_stradario_ul;
	}

	public void setCod_stradario_ul(String cod_stradario_ul) {
		this.cod_stradario_ul = cod_stradario_ul;
	}

	public String getTelefono_ul() {
		return telefono_ul;
	}

	public void setTelefono_ul(String telefono_ul) {
		this.telefono_ul = telefono_ul;
	}

	public String getStato_attivita_ul() {
		return stato_attivita_ul;
	}

	public void setStato_attivita_ul(String stato_attivita_ul) {
		this.stato_attivita_ul = stato_attivita_ul;
	}

	public String getCod_acusale_cessazione_ul() {
		return cod_acusale_cessazione_ul;
	}

	public void setCod_acusale_cessazione_ul(String cod_acusale_cessazione_ul) {
		this.cod_acusale_cessazione_ul = cod_acusale_cessazione_ul;
	}

	public Timestamp getData_cessazione_ul() {
		return data_cessazione_ul;
	}

	public void setData_cessazione_ul(Timestamp data_cessazione_ul) {
		this.data_cessazione_ul = data_cessazione_ul;
	}

	public Timestamp getData_denuncia_cessazione_ul() {
		return data_denuncia_cessazione_ul;
	}

	public void setData_denuncia_cessazione_ul(Timestamp data_denuncia_cessazione_ul) {
		this.data_denuncia_cessazione_ul = data_denuncia_cessazione_ul;
	}

	public String getAnno_denuncia_addetti_ul() {
		return anno_denuncia_addetti_ul;
	}

	public void setAnno_denuncia_addetti_ul(String anno_denuncia_addetti_ul) {
		this.anno_denuncia_addetti_ul = anno_denuncia_addetti_ul;
	}

	public String getNum_addetti_familiari_ul() {
		return num_addetti_familiari_ul;
	}

	public void setNum_addetti_familiari_ul(String num_addetti_familiari_ul) {
		this.num_addetti_familiari_ul = num_addetti_familiari_ul;
	}

	public String getNum_addetti_subordinati_ul() {
		return num_addetti_subordinati_ul;
	}

	public void setNum_addetti_subordinati_ul(String num_addetti_subordinati_ul) {
		this.num_addetti_subordinati_ul = num_addetti_subordinati_ul;
	}

	public String getCod_attivita_istat_91_primario() {
		return cod_attivita_istat_91_primario;
	}

	public void setCod_attivita_istat_91_primario(
			String cod_attivita_istat_91_primario) {
		this.cod_attivita_istat_91_primario = cod_attivita_istat_91_primario;
	}

	public String getDescrizione_attivita_ul_1() {
		return descrizione_attivita_ul_1;
	}

	public void setDescrizione_attivita_ul_1(String descrizione_attivita_ul_1) {
		this.descrizione_attivita_ul_1 = descrizione_attivita_ul_1;
	}

	public String getDescrizione_attivita_ul_2() {
		return descrizione_attivita_ul_2;
	}

	public void setDescrizione_attivita_ul_2(String descrizione_attivita_ul_2) {
		this.descrizione_attivita_ul_2 = descrizione_attivita_ul_2;
	}

	public String getDescrizione_attivita_ul_3() {
		return descrizione_attivita_ul_3;
	}

	public void setDescrizione_attivita_ul_3(String descrizione_attivita_ul_3) {
		this.descrizione_attivita_ul_3 = descrizione_attivita_ul_3;
	}

	public String getDescrizione_attivita_ul_4() {
		return descrizione_attivita_ul_4;
	}

	public void setDescrizione_attivita_ul_4(String descrizione_attivita_ul_4) {
		this.descrizione_attivita_ul_4 = descrizione_attivita_ul_4;
	}

	public boolean isFlag_omissis_descrizione_attivita_ul() {
		return flag_omissis_descrizione_attivita_ul;
	}

	public void setFlag_omissis_descrizione_attivita_ul(
			boolean flag_omissis_descrizione_attivita_ul) {
		this.flag_omissis_descrizione_attivita_ul = flag_omissis_descrizione_attivita_ul;
	}

	public String getCod_tipo_ruolo_1() {
		return cod_tipo_ruolo_1;
	}

	public void setCod_tipo_ruolo_1(String cod_tipo_ruolo_1) {
		this.cod_tipo_ruolo_1 = cod_tipo_ruolo_1;
	}

	public String getCod_forma_ruolo_1() {
		return cod_forma_ruolo_1;
	}

	public void setCod_forma_ruolo_1(String cod_forma_ruolo_1) {
		this.cod_forma_ruolo_1 = cod_forma_ruolo_1;
	}

	public String getNum_ruolo_1() {
		return num_ruolo_1;
	}

	public void setNum_ruolo_1(String num_ruolo_1) {
		this.num_ruolo_1 = num_ruolo_1;
	}

	public Timestamp getData_inizio_ruolo_1() {
		return data_inizio_ruolo_1;
	}

	public void setData_inizio_ruolo_1(Timestamp data_inizio_ruolo_1) {
		this.data_inizio_ruolo_1 = data_inizio_ruolo_1;
	}

	public Timestamp getData_cessazione_ruolo_1() {
		return data_cessazione_ruolo_1;
	}

	public void setData_cessazione_ruolo_1(Timestamp data_cessazione_ruolo_1) {
		this.data_cessazione_ruolo_1 = data_cessazione_ruolo_1;
	}

	public String getCod_tipo_ruolo_2() {
		return cod_tipo_ruolo_2;
	}

	public void setCod_tipo_ruolo_2(String cod_tipo_ruolo_2) {
		this.cod_tipo_ruolo_2 = cod_tipo_ruolo_2;
	}

	public String getCod_forma_ruolo_2() {
		return cod_forma_ruolo_2;
	}

	public void setCod_forma_ruolo_2(String cod_forma_ruolo_2) {
		this.cod_forma_ruolo_2 = cod_forma_ruolo_2;
	}

	public String getNum_ruolo_2() {
		return num_ruolo_2;
	}

	public void setNum_ruolo_2(String num_ruolo_2) {
		this.num_ruolo_2 = num_ruolo_2;
	}

	public Timestamp getData_inizio_ruolo_2() {
		return data_inizio_ruolo_2;
	}

	public void setData_inizio_ruolo_2(Timestamp data_inizio_ruolo_2) {
		this.data_inizio_ruolo_2 = data_inizio_ruolo_2;
	}

	public Timestamp getData_cessazione_ruolo_2() {
		return data_cessazione_ruolo_2;
	}

	public void setData_cessazione_ruolo_2(Timestamp data_cessazione_ruolo_2) {
		this.data_cessazione_ruolo_2 = data_cessazione_ruolo_2;
	}

	public String getCod_tipo_ruolo_3() {
		return cod_tipo_ruolo_3;
	}

	public void setCod_tipo_ruolo_3(String cod_tipo_ruolo_3) {
		this.cod_tipo_ruolo_3 = cod_tipo_ruolo_3;
	}

	public String getCod_forma_ruolo_3() {
		return cod_forma_ruolo_3;
	}

	public void setCod_forma_ruolo_3(String cod_forma_ruolo_3) {
		this.cod_forma_ruolo_3 = cod_forma_ruolo_3;
	}

	public String getNum_ruolo_3() {
		return num_ruolo_3;
	}

	public void setNum_ruolo_3(String num_ruolo_3) {
		this.num_ruolo_3 = num_ruolo_3;
	}

	public Timestamp getData_inizio_ruolo_3() {
		return data_inizio_ruolo_3;
	}

	public void setData_inizio_ruolo_3(Timestamp data_inizio_ruolo_3) {
		this.data_inizio_ruolo_3 = data_inizio_ruolo_3;
	}

	public Timestamp getData_cessazione_ruolo_3() {
		return data_cessazione_ruolo_3;
	}

	public void setData_cessazione_ruolo_3(Timestamp data_cessazione_ruolo_3) {
		this.data_cessazione_ruolo_3 = data_cessazione_ruolo_3;
	}

	public String getCod_tipo_ruolo_4() {
		return cod_tipo_ruolo_4;
	}

	public void setCod_tipo_ruolo_4(String cod_tipo_ruolo_4) {
		this.cod_tipo_ruolo_4 = cod_tipo_ruolo_4;
	}

	public String getCod_forma_ruolo_4() {
		return cod_forma_ruolo_4;
	}

	public void setCod_forma_ruolo_4(String cod_forma_ruolo_4) {
		this.cod_forma_ruolo_4 = cod_forma_ruolo_4;
	}

	public String getNum_ruolo_4() {
		return num_ruolo_4;
	}

	public void setNum_ruolo_4(String num_ruolo_4) {
		this.num_ruolo_4 = num_ruolo_4;
	}

	public Timestamp getData_inizio_ruolo_4() {
		return data_inizio_ruolo_4;
	}

	public void setData_inizio_ruolo_4(Timestamp data_inizio_ruolo_4) {
		this.data_inizio_ruolo_4 = data_inizio_ruolo_4;
	}

	public Timestamp getData_cessazione_ruolo_4() {
		return data_cessazione_ruolo_4;
	}

	public void setData_cessazione_ruolo_4(Timestamp data_cessazione_ruolo_4) {
		this.data_cessazione_ruolo_4 = data_cessazione_ruolo_4;
	}

	public String getCod_tipo_ruolo_5() {
		return cod_tipo_ruolo_5;
	}

	public void setCod_tipo_ruolo_5(String cod_tipo_ruolo_5) {
		this.cod_tipo_ruolo_5 = cod_tipo_ruolo_5;
	}

	public String getCod_forma_ruolo_5() {
		return cod_forma_ruolo_5;
	}

	public void setCod_forma_ruolo_5(String cod_forma_ruolo_5) {
		this.cod_forma_ruolo_5 = cod_forma_ruolo_5;
	}

	public String getNum_ruolo_5() {
		return num_ruolo_5;
	}

	public void setNum_ruolo_5(String num_ruolo_5) {
		this.num_ruolo_5 = num_ruolo_5;
	}

	public Timestamp getData_inizio_ruolo_5() {
		return data_inizio_ruolo_5;
	}

	public void setData_inizio_ruolo_5(Timestamp data_inizio_ruolo_5) {
		this.data_inizio_ruolo_5 = data_inizio_ruolo_5;
	}

	public Timestamp getData_cessazione_ruolo_5() {
		return data_cessazione_ruolo_5;
	}

	public void setData_cessazione_ruolo_5(Timestamp data_cessazione_ruolo_5) {
		this.data_cessazione_ruolo_5 = data_cessazione_ruolo_5;
	}

	public boolean isFlag_omissis_albi_ruoli() {
		return flag_omissis_albi_ruoli;
	}

	public void setFlag_omissis_albi_ruoli(boolean flag_omissis_albi_ruoli) {
		this.flag_omissis_albi_ruoli = flag_omissis_albi_ruoli;
	}

	public Vector<BCameraCommercio> load( Connection db )
	{
		Vector<BCameraCommercio>	ret		= new Vector<BCameraCommercio>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM camera_commercio LIMIT 100" );
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
	
	private static BCameraCommercio loadResultSet( ResultSet res ) 
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		BCameraCommercio ret = new BCameraCommercio();
		
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
	
	public void store( Connection db )
		throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		String sql = "INSERT INTO camera_commercio( ";
		
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
        stat.execute();
        stat.close();
	}
	
	public void carica( CsvLunghezzaFissa csv  ) 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Field[] f = this.getClass().getDeclaredFields();
        Method[] m = this.getClass().getMethods();
		
		for( int i = 0; i < f.length; i++ )
        {
			
			 for( int j = 0; j < m.length; j++ )
             {
                String met = m[j].getName();
                String field = f[i].getName();
	            String value = csv.get( field );
	            Object o = null;
                if( met.equalsIgnoreCase( "SET" + field ) && (value != null) && (value.trim().length() > 0) )
                {
                	value = value.trim();
		            switch ( parseType( f[i].getType() ) )
		            {
		            case INT:
		                o = Integer.parseInt( value );
		                break;
		            case STRING:
		                o = value;
		                break;
		            case BOOLEAN:
		                o = ( ("0".equals(value)) ? (false) : (true) );
		                break;
		            case TIMESTAMP:
		                o = parseDate( value );
		                break;
		            }
                    m[j].invoke( this, o );
                 }
             }
			
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

     private static Timestamp parseDate( String date )
     {
    	 Timestamp ret = null;
    	 
    	 SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
    	 try 
    	 {
			ret = new Timestamp( sdf.parse( date ).getTime() );
    	 }
    	 catch (ParseException e)
    	 {
			e.printStackTrace();
    	 }
    	 
    	 return ret;
     }

	public static Vector<BCameraCommercio> load( String ragione_sociale, String partita_iva,
			String codice_fiscale, String duplicati, Connection db )
	{
		Vector<BCameraCommercio>	ret		= new Vector<BCameraCommercio>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		ragione_sociale	= (ragione_sociale == null)	? ("") : (ragione_sociale.trim());
		partita_iva		= (partita_iva == null)		? ("") : (partita_iva.trim());
		codice_fiscale	= (codice_fiscale == null)	? ("") : (codice_fiscale.trim());
		
		String sql = "SELECT * FROM camera_commercio where 1=1 ";
		if( duplicati != null )
		{
			sql = "SELECT " +
					"distinct on ( ragione_sociale, partita_iva, cf_impresa, provincia_sede_legale, " +
					"via_sede_legale, civico_sede_legale ) " +
					"* FROM camera_commercio WHERE 1=1 ";
		}
		
		try
		{
			boolean where = false;
			
			if( ragione_sociale.length() > 0 )
			{
				sql += ( " and ragione_sociale ILIKE '%" + ragione_sociale.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			if( partita_iva.length() > 0 )
			{
				
				
				sql += ( " and partita_iva ILIKE '%" + partita_iva.replaceAll( "'", "''" ) + "%' " );
			}
			if( codice_fiscale.length() > 0 )
			{
				
				sql += ( " and cf_impresa ILIKE '%" + codice_fiscale.replaceAll( "'", "''" ) + "%' " );
			}
			
			if( duplicati != null )
			{
				sql += " ORDER BY ragione_sociale, partita_iva, cf_impresa, provincia_sede_legale, " +
						"via_sede_legale, civico_sede_legale LIMIT 100 ";
			}
			else
			{
				sql += " ORDER BY ragione_sociale LIMIT 100 ";
			}
			
			stat = db.prepareStatement( sql );
			
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static BCameraCommercio load(String id, Connection db )
	{

		BCameraCommercio	ret		= null;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		if( (id != null) && (id.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id );
				stat	= db.prepareStatement( "SELECT * FROM camera_commercio WHERE id = ? " );
				stat.setInt( 1, iid );
				res		= stat.executeQuery();
				if( res.next() )
				{
					ret = loadResultSet( res );
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

	public static void importato(String id_cc, int orgId, Connection db)
	{
		PreparedStatement	stat	= null;
		String				sql		= "UPDATE camera_commercio SET data_importazione_osa = ?, org_id = ? WHERE id = ? ";
		
		if( (id_cc != null) && (id_cc.trim().length() > 0) )
		{
			try
			{
				int iid = Integer.parseInt( id_cc );
				stat	= db.prepareStatement( sql );
				stat.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
				stat.setInt( 2, orgId );
				stat.setInt( 3, iid );

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
		
	}
	
}

