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
import java.util.logging.Logger;

import org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninewopu.utils.MacelliUtil;
import org.aspcfs.modules.macellazioninewopu.utils.ReflectionUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;

public class Partita extends GenericBean
{
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private static SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
	private static Logger logger = Logger.getLogger("MainLogger");
	
	private static final long serialVersionUID = 8313006891554941893L;
	
	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;
	
	private String		cd_partita;
	private String		proprietario;
	private Integer		    cd_num_capi_ovini = 0;
	private Integer		    cd_num_capi_caprini = 0;
	private Integer		    cd_num_capi_ovini_18 = 0;
	private Integer		    cd_num_capi_caprini_18 = 0;
	private Integer		    cd_num_capi_ovini_da_testare = 0;
	private Integer		    cd_num_capi_caprini_da_testare = 0;
	private boolean		cd_bse;
	private boolean		chiusa;
	private String		motivazione_chiusura;
	private Integer	    vam_num_capi_ovini = 0;
	private Integer	    vam_num_capi_caprini = 0;
	private int		    vpm_num_capi = 0;    
	private Integer		    mavam_num_capi_ovini = 0;
	private Integer		    mavam_num_capi_caprini = 0;
	private Integer		    casl_num_capi_ovini = 0;
	private Integer		    casl_num_capi_caprini = 0;
	
	private Integer 	destinatario_5_id = -1;
	private Boolean 	destinatario_5_in_regione = false;
	private String		destinatario_5_nome;
	private Integer 	destinatario_6_id = -1;
	private Boolean 	destinatario_6_in_regione = false;
	private String		destinatario_6_nome;
	private Integer 	destinatario_7_id = -1;
	private Boolean 	destinatario_7_in_regione = false;
	private String		destinatario_7_nome;
	private Integer 	destinatario_8_id = -1;
	private Boolean 	destinatario_8_in_regione = false;
	private String		destinatario_8_nome;
	private Integer 	destinatario_9_id = -1;
	private Boolean 	destinatario_9_in_regione = false;
	private String		destinatario_9_nome;
	private Integer 	destinatario_10_id = -1;
	private Boolean 	destinatario_10_in_regione = false;
	private String		destinatario_10_nome;
	private Integer 	destinatario_11_id = -1;
	private Boolean 	destinatario_11_in_regione = false;
	private String		destinatario_11_nome;
	private Integer 	destinatario_12_id = -1;
	private Boolean 	destinatario_12_in_regione = false;
	private String		destinatario_12_nome;
	private Integer 	destinatario_13_id = -1;
	private Boolean 	destinatario_13_in_regione = false;
	private String		destinatario_13_nome;
	private Integer 	destinatario_14_id = -1;
	private Boolean 	destinatario_14_in_regione = false;
	private String		destinatario_14_nome;
	private Integer 	destinatario_15_id = -1;
	private Boolean 	destinatario_15_in_regione = false;
	private String		destinatario_15_nome;
	private Integer 	destinatario_16_id = -1;
	private Boolean 	destinatario_16_in_regione = false;
	private String		destinatario_16_nome;
	private Integer 	destinatario_17_id = -1;
	private Boolean 	destinatario_17_in_regione = false;
	private String		destinatario_17_nome;
	private Integer 	destinatario_18_id = -1;
	private Boolean 	destinatario_18_in_regione = false;
	private String		destinatario_18_nome;
	private Integer 	destinatario_19_id = -1;
	private Boolean 	destinatario_19_in_regione = false;
	private String		destinatario_19_nome;
	private Integer 	destinatario_20_id = -1;
	private Boolean 	destinatario_20_in_regione = false;
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
	private int		    capi_ovini_macellati = 0;   
	private int		    capi_caprini_macellati = 0;   
	
	private String rag_soc_azienda_prov ;
	private String denominazione_asl_azienda_prov ;
	private String id_asl_azienda_prov ;
	private String cod_asl_azienda_prov ;

	private int errata_corrige_generati = 0;
	
	private boolean specie_suina = false;
	
	//Cancellazione
	private int deleted_by=-1;
	private String note_cancellazione ="";
	
	public int getDeleted_by() {
		return deleted_by;
	}

	public void setDeleted_by(int deleted_by) {
		this.deleted_by = deleted_by;
	}

	public String getNote_cancellazione() {
		return note_cancellazione;
	}

	public void setNote_cancellazione(String note_cancellazione) {
		this.note_cancellazione = note_cancellazione;
	}

	
	public int getErrata_corrige_generati() {
		return errata_corrige_generati;
	}

	public void setErrata_corrige_generati(int errata_corrige_generati) {
		this.errata_corrige_generati = errata_corrige_generati;
	}


	public String getCod_asl_azienda_prov() {
	return cod_asl_azienda_prov;
}

public void setCod_asl_azienda_prov(String cod_asl_azienda_prov) {
	this.cod_asl_azienda_prov = cod_asl_azienda_prov;
}

	public String getId_asl_azienda_prov() {
		return id_asl_azienda_prov;
	}

	public void setId_asl_azienda_prov(String id_asl_azienda_prov) {
		this.id_asl_azienda_prov = id_asl_azienda_prov;
	}

	public String getRag_soc_azienda_prov() {
		return rag_soc_azienda_prov;
	}

	public void setRag_soc_azienda_prov(String rag_soc_azienda_prov) {
		this.rag_soc_azienda_prov = rag_soc_azienda_prov;
	}

	public String getDenominazione_asl_azienda_prov() {
		return denominazione_asl_azienda_prov;
	}

	public void setDenominazione_asl_azienda_prov(String denominazione_asl_azienda_prov) {
		this.denominazione_asl_azienda_prov = denominazione_asl_azienda_prov;
	}
	
	
	public String color = "#FFA500";
		
	public String getCd_partita() {
		return cd_partita;
	}
	public void setCd_partita(String cd_partita) {
		this.cd_partita = cd_partita;
	}
	
	public String getProprietario() {
		return proprietario;
	}
	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	
	public int getDestinatario_5_id() {
		return destinatario_5_id;
	}
	public void setDestinatario_5_id(Integer destinatario_5_id) {
		this.destinatario_5_id = destinatario_5_id;
	}
	
	public int getCapiOviniMacellati() {
		return capi_ovini_macellati;
	}
	public void setCapiOviniMacellati(Integer capi_ovini_macellati) {
		this.capi_ovini_macellati = capi_ovini_macellati;
	}
	
	public int getCapiCapriniMacellati() {
		return capi_caprini_macellati;
	}
	public void setCapiCapriniMacellati(Integer capi_caprini_macellati) {
		this.capi_caprini_macellati = capi_caprini_macellati;
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
	
	public int getCd_num_capi_ovini() {
		return cd_num_capi_ovini;
	}
	public void setCd_num_capi_ovini(int cd_num_capi_ovini) {
		this.cd_num_capi_ovini = cd_num_capi_ovini;
	}
	public int getCd_num_capi_caprini() {
		return cd_num_capi_caprini;
	}
	public void setCd_num_capi_caprini(int cd_num_capi_caprini) {
		this.cd_num_capi_caprini = cd_num_capi_caprini;
	}
	public int getCd_num_capi_ovini_da_testare() {
		return cd_num_capi_ovini_da_testare;
	}
	public void setCd_num_capi_ovini_da_testare(int cd_num_capi_ovini_da_testare) {
		this.cd_num_capi_ovini_da_testare = cd_num_capi_ovini_da_testare;
	}
	public int getCd_num_capi_caprini_da_testare() {
		return cd_num_capi_caprini_da_testare;
	}
	public void setCd_num_capi_caprini_da_testare(int cd_num_capi_caprini_da_testare) {
		this.cd_num_capi_caprini_da_testare = cd_num_capi_caprini_da_testare;
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
	public int getVpm_num_capi() {
		return vpm_num_capi;
	}
	public void setVpm_num_capi(int vpm_num_capi) {
		this.vpm_num_capi = vpm_num_capi;
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
	public int getCasl_num_capi_ovini() {
		return casl_num_capi_ovini;
	}
	public void setCasl_num_capi_ovini(int casl_num_capi_ovini) {
		this.casl_num_capi_ovini = casl_num_capi_ovini;
	}
	public int getCasl_num_capi_caprini() {
		return casl_num_capi_caprini;
	}
	public void setCasl_num_capi_caprini(int casl_num_capi_caprini) {
		this.casl_num_capi_caprini = casl_num_capi_caprini;
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
	
	public ArrayList <DestinatarioCarni> getListaDestinatariCarni(){
		ArrayList<DestinatarioCarni> lista = new ArrayList<DestinatarioCarni>();
		
		for(int i=1;i<=20;i++)
		{
        	try {
				Boolean isDestinatarioInRegione = (Boolean)ReflectionUtil.invocaMetodo(this, "isDestinatario_"+i+"_in_regione", null, null);
				String destinatarioNome  = (String)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_nome", null, null);
	        	Integer destinatarioId  = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_id", null, null);
	        	Integer destinatarioNumCapiOviniInt    = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_num_capi_ovini", null, null);
	        	Integer destinatarioNumCapiCapriniInt  = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_num_capi_caprini", null, null);
	        	
	        	if (destinatarioId>0 || destinatarioId==-999){
	        		DestinatarioCarni dest = new DestinatarioCarni (destinatarioId, isDestinatarioInRegione, destinatarioNome, destinatarioNumCapiOviniInt, destinatarioNumCapiCapriniInt);
	    			lista.add(dest);
	        	}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		}
		return lista;
		
//		if (getDestinatario_1_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_1_id(), isDestinatario_1_in_regione(), getDestinatario_1_nome(), getDestinatario_1_num_capi_ovini(), getDestinatario_1_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_2_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_2_id(), isDestinatario_2_in_regione(), getDestinatario_2_nome(), getDestinatario_2_num_capi_ovini(), getDestinatario_2_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_3_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_3_id(), isDestinatario_3_in_regione(), getDestinatario_3_nome(), getDestinatario_3_num_capi_ovini(), getDestinatario_3_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_4_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_4_id(), isDestinatario_4_in_regione(), getDestinatario_4_nome(), getDestinatario_4_num_capi_ovini(), getDestinatario_4_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_5_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_5_id(), isDestinatario_5_in_regione(), getDestinatario_5_nome(), getDestinatario_5_num_capi_ovini(), getDestinatario_5_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_6_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_6_id(), isDestinatario_6_in_regione(), getDestinatario_6_nome(), getDestinatario_6_num_capi_ovini(), getDestinatario_6_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_7_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_7_id(), isDestinatario_7_in_regione(), getDestinatario_7_nome(), getDestinatario_7_num_capi_ovini(), getDestinatario_7_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_8_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_8_id(), isDestinatario_8_in_regione(), getDestinatario_8_nome(), getDestinatario_8_num_capi_ovini(), getDestinatario_8_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_9_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_9_id(), isDestinatario_9_in_regione(), getDestinatario_9_nome(), getDestinatario_9_num_capi_ovini(), getDestinatario_9_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_10_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_10_id(), isDestinatario_10_in_regione(), getDestinatario_10_nome(), getDestinatario_10_num_capi_ovini(), getDestinatario_10_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_11_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_11_id(), isDestinatario_11_in_regione(), getDestinatario_11_nome(), getDestinatario_11_num_capi_ovini(), getDestinatario_11_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_12_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_12_id(), isDestinatario_12_in_regione(), getDestinatario_12_nome(), getDestinatario_12_num_capi_ovini(), getDestinatario_12_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_13_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_13_id(), isDestinatario_13_in_regione(), getDestinatario_13_nome(), getDestinatario_13_num_capi_ovini(), getDestinatario_13_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_14_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_14_id(), isDestinatario_14_in_regione(), getDestinatario_14_nome(), getDestinatario_14_num_capi_ovini(), getDestinatario_14_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_15_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_15_id(), isDestinatario_15_in_regione(), getDestinatario_15_nome(), getDestinatario_15_num_capi_ovini(), getDestinatario_15_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_16_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_16_id(), isDestinatario_16_in_regione(), getDestinatario_16_nome(), getDestinatario_16_num_capi_ovini(), getDestinatario_16_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_17_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_17_id(), isDestinatario_17_in_regione(), getDestinatario_17_nome(), getDestinatario_17_num_capi_ovini(), getDestinatario_17_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_18_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_18_id(), isDestinatario_18_in_regione(), getDestinatario_18_nome(), getDestinatario_18_num_capi_ovini(), getDestinatario_18_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_19_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_19_id(), isDestinatario_19_in_regione(), getDestinatario_19_nome(), getDestinatario_19_num_capi_ovini(), getDestinatario_19_num_capi_caprini());
//			lista.add(dest);
//		}
//		if (getDestinatario_20_id()>0){
//			DestinatarioCarni dest = new DestinatarioCarni (getDestinatario_20_id(), isDestinatario_20_in_regione(), getDestinatario_20_nome(), getDestinatario_20_num_capi_ovini(), getDestinatario_20_num_capi_caprini());
//			lista.add(dest);
//		}
//		return lista;
	}
	
	public ArrayList <DestinatarioCarni> getListaDestinatariCarniConIndice(){
		ArrayList<DestinatarioCarni> lista = new ArrayList<DestinatarioCarni>();
		
		for(int i=1;i<=20;i++)
		{
        	try {
				Boolean isDestinatarioInRegione = (Boolean)ReflectionUtil.invocaMetodo(this, "isDestinatario_"+i+"_in_regione", null, null);
				String destinatarioNome  = (String)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_nome", null, null);
	        	Integer destinatarioId  = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_id", null, null);
	        	Integer destinatarioNumCapiOviniInt    = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_num_capi_ovini", null, null);
	        	Integer destinatarioNumCapiCapriniInt  = (Integer)ReflectionUtil.invocaMetodo(this, "getDestinatario_"+i+"_num_capi_caprini", null, null);
	        	
	        	if (destinatarioId>0 || destinatarioId==-999){
	        		DestinatarioCarni dest = new DestinatarioCarni (destinatarioId, isDestinatarioInRegione, destinatarioNome, destinatarioNumCapiOviniInt, destinatarioNumCapiCapriniInt, i);
	    			lista.add(dest);
	        	}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		}
		return lista;
	}
	
	public static ArrayList<PartitaSeduta> loadSedute(Connection db, int idPartita, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_partite_sedute  WHERE id_partita = ? ORDER BY entered DESC";
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetSeduta( res, db, configTipo, idPartita ) );
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
	
	public static ArrayList<PartitaSeduta> loadSeduteMinimale(Connection db, int idPartita, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT sedute.destinatario_1_num_capi_ovini, " +
			"sedute.destinatario_2_num_capi_ovini , " +
			"sedute.destinatario_3_num_capi_ovini , " +
			"sedute.destinatario_4_num_capi_ovini , " +
			"sedute.destinatario_5_num_capi_ovini , " +
			"sedute.destinatario_6_num_capi_ovini , " +
			"sedute.destinatario_7_num_capi_ovini , " +
			"sedute.destinatario_8_num_capi_ovini , " +
			"sedute.destinatario_9_num_capi_ovini , " +
			"sedute.destinatario_10_num_capi_ovini , " +
			"sedute.destinatario_11_num_capi_ovini , " +
			"sedute.destinatario_12_num_capi_ovini , " +
			"sedute.destinatario_13_num_capi_ovini , " +
			"sedute.destinatario_14_num_capi_ovini , " +
			"sedute.destinatario_15_num_capi_ovini , " +
			"sedute.destinatario_16_num_capi_ovini , " +
			"sedute.destinatario_17_num_capi_ovini , " +
			"sedute.destinatario_18_num_capi_ovini , " +
			"sedute.destinatario_19_num_capi_ovini , " +
			"sedute.destinatario_20_num_capi_ovini , " +
			"sedute.destinatario_1_num_capi_caprini , " +
			"sedute.destinatario_2_num_capi_caprini , " +
			"sedute.destinatario_3_num_capi_caprini , " +
			"sedute.destinatario_4_num_capi_caprini , " +
			"sedute.destinatario_5_num_capi_caprini , " +
			"sedute.destinatario_6_num_capi_caprini , " +
			"sedute.destinatario_7_num_capi_caprini , " +
			"sedute.destinatario_8_num_capi_caprini , " +
			"sedute.destinatario_9_num_capi_caprini , " +
			"sedute.destinatario_10_num_capi_caprini , " +
			"sedute.destinatario_11_num_capi_caprini , " +
			"sedute.destinatario_12_num_capi_caprini , " +
			"sedute.destinatario_13_num_capi_caprini , " +
			"sedute.destinatario_14_num_capi_caprini , " +
			"sedute.destinatario_15_num_capi_caprini , " +
			"sedute.destinatario_16_num_capi_caprini , " +
			"sedute.destinatario_17_num_capi_caprini , " +
			"sedute.destinatario_18_num_capi_caprini , " +
			"sedute.destinatario_19_num_capi_caprini , " +
			"sedute.destinatario_20_num_capi_caprini, sedute.id, sedute.numero, sedute.id_partita, sedute.id_macello, sedute.stato_macellazione, partite.cd_partita, partite.cd_codice_azienda_provenienza, sedute.mavam_num_capi_ovini, sedute.mavam_num_capi_caprini, sedute.vpm_data, sedute.articolo17 FROM m_partite_sedute sedute LEFT JOIN m_partite partite on partite.id = sedute.id_partita WHERE sedute.id_partita = ? ORDER BY sedute.entered DESC";
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetSedutaMinimale( res, db, configTipo, idPartita ) );
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
	
	
	public static int loadSeduteArt17(Connection db, int idPartita, ConfigTipo configTipo, boolean presente)
	{
		int ret = 0;
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT count(*) FROM m_partite_sedute sedute LEFT JOIN m_partite partite on partite.id = sedute.id_partita WHERE sedute.id_partita = ? and sedute.articolo17 = " + presente;
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			if( res.next() )
				ret = res.getInt(1);
			else
				ret = 0;
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
	
	
	
	
	public static ArrayList<PartitaSeduta> loadSeduteSmart(Connection db, int idPartita, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_partite_sedute  WHERE id_partita = ? ORDER BY entered DESC";
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetSedutaSmart( res, db, configTipo, idPartita ) );
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
	
	public static ArrayList<PartitaSeduta> loadSeduteWhitoutPartita(Connection db, int idPartita, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_partite_sedute  WHERE id_partita = ? ORDER BY entered DESC";
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetSedutaWithoutPartita( res, db, configTipo, idPartita ) );
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
	
	public static ArrayList<PartitaSeduta> loadSeduteWithoutPartita(Connection db, int idPartita, ConfigTipo configTipo)
	{

		ArrayList<PartitaSeduta>		ret		= new ArrayList<PartitaSeduta>();
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_partite_sedute  WHERE id_partita = ? ORDER BY entered DESC";
		try
		{	
			stat = db.prepareStatement( sql );
			stat.setInt( 1, idPartita );
			
			res = stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetSedutaWithoutPartita( res, db, configTipo, idPartita ) );
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
	
	private static PartitaSeduta loadResultSetSeduta( ResultSet res, Connection db, ConfigTipo configTipo, int idPartita ) 
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
			loadPartita(ret, db, configTipo, idPartita);
			return ret;
		}
	
	private static PartitaSeduta loadResultSetSedutaSmart( ResultSet res, Connection db, ConfigTipo configTipo, int idPartita ) 
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
			
			loadPartita(ret, db, configTipo, idPartita);
			return ret;
		}
	
	private static PartitaSeduta loadResultSetSedutaWithoutPartita( ResultSet res, Connection db, ConfigTipo configTipo, int idPartita ) 
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
			
			return ret;
		}

	private static PartitaSeduta loadResultSetSedutaMinimale( ResultSet res, Connection db, ConfigTipo configTipo, int idPartita ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			PartitaSeduta ret = new PartitaSeduta();
			ret.setId(res.getInt("id"));
			ret.setId_partita(res.getInt("id_partita"));
			ret.setId_macello(res.getInt("id_macello"));
			ret.setStato_macellazione(res.getString("stato_macellazione"));
			ret.setCd_partita(res.getString("cd_partita"));
			ret.setCd_codice_azienda_provenienza(res.getString("cd_codice_azienda_provenienza"));
			//ret.setCd_num_capi_ovini(res.getInt("cd_num_capi_ovini"));
			//ret.setCd_num_capi_caprini(res.getInt("cd_num_capi_caprini"));
			ret.setMavam_num_capi_ovini(res.getInt("mavam_num_capi_ovini"));
			ret.setMavam_num_capi_caprini(res.getInt("mavam_num_capi_caprini"));
			//ret.setCd_categoria_rischio(res.getInt("cd_categoria_rischio"));
			ret.setVpm_data(res.getTimestamp("vpm_data"));
			ret.setNumero(res.getString("numero"));
			ret.setDestinatario_1_num_capi_ovini(res.getInt("destinatario_1_num_capi_ovini"));
			ret.setDestinatario_2_num_capi_ovini(res.getInt("destinatario_2_num_capi_ovini"));
			ret.setDestinatario_3_num_capi_ovini(res.getInt("destinatario_3_num_capi_ovini"));
			ret.setDestinatario_4_num_capi_ovini(res.getInt("destinatario_4_num_capi_ovini"));
			ret.setDestinatario_5_num_capi_ovini(res.getInt("destinatario_5_num_capi_ovini"));
			ret.setDestinatario_6_num_capi_ovini(res.getInt("destinatario_6_num_capi_ovini"));
			ret.setDestinatario_7_num_capi_ovini(res.getInt("destinatario_7_num_capi_ovini"));
			ret.setDestinatario_8_num_capi_ovini(res.getInt("destinatario_8_num_capi_ovini"));
			ret.setDestinatario_9_num_capi_ovini(res.getInt("destinatario_9_num_capi_ovini"));
			ret.setDestinatario_10_num_capi_ovini(res.getInt("destinatario_10_num_capi_ovini"));
			ret.setDestinatario_11_num_capi_ovini(res.getInt("destinatario_11_num_capi_ovini"));
			ret.setDestinatario_12_num_capi_ovini(res.getInt("destinatario_12_num_capi_ovini"));
			ret.setDestinatario_13_num_capi_ovini(res.getInt("destinatario_13_num_capi_ovini"));
			ret.setDestinatario_14_num_capi_ovini(res.getInt("destinatario_14_num_capi_ovini"));
			ret.setDestinatario_15_num_capi_ovini(res.getInt("destinatario_15_num_capi_ovini"));
			ret.setDestinatario_16_num_capi_ovini(res.getInt("destinatario_16_num_capi_ovini"));
			ret.setDestinatario_17_num_capi_ovini(res.getInt("destinatario_17_num_capi_ovini"));
			ret.setDestinatario_18_num_capi_ovini(res.getInt("destinatario_18_num_capi_ovini"));
			ret.setDestinatario_19_num_capi_ovini(res.getInt("destinatario_19_num_capi_ovini"));
			ret.setDestinatario_20_num_capi_ovini(res.getInt("destinatario_20_num_capi_ovini"));
			ret.setDestinatario_1_num_capi_caprini(res.getInt("destinatario_1_num_capi_caprini"));
			ret.setDestinatario_2_num_capi_caprini(res.getInt("destinatario_2_num_capi_caprini"));
			ret.setDestinatario_3_num_capi_caprini(res.getInt("destinatario_3_num_capi_caprini"));
			ret.setDestinatario_4_num_capi_caprini(res.getInt("destinatario_4_num_capi_caprini"));
			ret.setDestinatario_5_num_capi_caprini(res.getInt("destinatario_5_num_capi_caprini"));
			ret.setDestinatario_6_num_capi_caprini(res.getInt("destinatario_6_num_capi_caprini"));
			ret.setDestinatario_7_num_capi_caprini(res.getInt("destinatario_7_num_capi_caprini"));
			ret.setDestinatario_8_num_capi_caprini(res.getInt("destinatario_8_num_capi_caprini"));
			ret.setDestinatario_9_num_capi_caprini(res.getInt("destinatario_9_num_capi_caprini"));
			ret.setDestinatario_10_num_capi_caprini(res.getInt("destinatario_10_num_capi_caprini"));
			ret.setDestinatario_11_num_capi_caprini(res.getInt("destinatario_11_num_capi_caprini"));
			ret.setDestinatario_12_num_capi_caprini(res.getInt("destinatario_12_num_capi_caprini"));
			ret.setDestinatario_13_num_capi_caprini(res.getInt("destinatario_13_num_capi_caprini"));
			ret.setDestinatario_14_num_capi_caprini(res.getInt("destinatario_14_num_capi_caprini"));
			ret.setDestinatario_15_num_capi_caprini(res.getInt("destinatario_15_num_capi_caprini"));
			ret.setDestinatario_16_num_capi_caprini(res.getInt("destinatario_16_num_capi_caprini"));
			ret.setDestinatario_17_num_capi_caprini(res.getInt("destinatario_17_num_capi_caprini"));
			ret.setDestinatario_18_num_capi_caprini(res.getInt("destinatario_18_num_capi_caprini"));
			ret.setDestinatario_19_num_capi_caprini(res.getInt("destinatario_19_num_capi_caprini"));
			ret.setDestinatario_20_num_capi_caprini(res.getInt("destinatario_20_num_capi_caprini"));
			ret.setArticolo17(res.getBoolean("articolo17"));
			ret.color = MacelliUtil.getRecordColorSeduta(ret, db, configTipo);
			return ret;
		}

	
	public static void loadPartita( Partita partita, Connection db, ConfigTipo configTipo, int idPartita ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
		
		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		
		String sql = "SELECT * FROM m_partite  WHERE id = ? ORDER BY entered DESC";
		stat = db.prepareStatement( sql );
		stat.setInt( 1, idPartita );
		res = stat.executeQuery();
		Partita ret = new Partita();
		if (res.next()){
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
		             
		             setter.invoke( partita, o );
		         
		         }
			}
		}
			
		}
	
	public void chiudi(Connection db, String motivazione) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		MacelliUtil.setNextNumeroMacellazione( this, db );
		
		String sql = "UPDATE m_partite SET motivazione_chiusura = ?, chiusa = true WHERE id = ?";
	
	    PreparedStatement stat = db.prepareStatement( sql );
	    stat.setString( 1, motivazione );
	    stat.setInt   ( 2, super.getId() );
	    
	    stat.execute();
	    stat.close();
	}
	
	public void setCategoria()
	{
	}
	
	public boolean getCd_bse() {
		return cd_bse;
	}
	public void setCd_bse(boolean cd_bse) {
		this.cd_bse = cd_bse;
	}
	
	public String getCd_specie() 
	{
		String specie = "";
		String specie1 = "Ovini";
		String specie2= "Caprini";
		if (specie_suina){
			specie1 = "Cinghiali";
			specie2 = "Suini";
		}
		if(getCd_num_capi_ovini()>0)
			specie = specie1;
		if(getCd_num_capi_caprini()>0)
		{
			if(!specie.equals(""))
				specie += ", "+specie2;
			else
				specie = specie2;
		}
		return specie;
	}
	
	private static void logga(ConfigTipo configTipo, ActionContext context, Connection db, GenericBean b) throws Exception
	{
			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();
	
			capoLog.setCodiceAziendaNascita(b.getCd_codice_azienda());
			capoLog.setEnteredBy(b.getEntered_by());
			capoLog.setIdMacello(b.getId_macello());
			capoLog.setMatricola(((Partita)b).getCd_partita());
			capoLog.setModifiedBy(b.getModified_by());
			capoLog.setTrashedDate(b.getTrashed_date());
			
			capoLogDao.log(db, capoLog);
	}
	
	public String costruisciQueryPartitaEsistente(ConfigTipo configTipo, Integer numOvini, Integer numCaprini)
	{
		String select = configTipo.getQueryEsistenzaCapo();
		
		if(numOvini>0 && numCaprini<=0)
			select += " and cd_num_capi_ovini > 0 ";
		else if(numCaprini>0 && numOvini<=0)
			select += " and cd_num_capi_caprini > 0 "; 
		else if(numCaprini>0 && numOvini>0)
			select += " and (cd_num_capi_ovini > 0  or cd_num_capi_caprini > 0) "; 
		
		return select;
	}
	
	public PreparedStatement costruisciStatementPartitaEsistente(PreparedStatement stat, String identificativo,String codiceAzienda) throws SQLException
	{
		stat.setString( 1, identificativo );
		stat.setString( 2, codiceAzienda);
		return stat;
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
	
	public String getStampatoArt17(Connection db, ConfigTipo configTipo)
	{
		String stato = getStatoMacellazione(db, configTipo);
		if(stato.equals("Non Macellata"))
			return "NO";
		else if(stato.equals("Completamente Macellata") )
		{
			if(this.isArticolo17() && tutteSeduteStampate(db,configTipo))
				return "SI";
			else if(this.isArticolo17() || almenoUnaSedutaStampata(db, configTipo))
				return "Parzialmente";
			else
				return "NO";
		}
		else if(stato.equals("Parzialmente Macellata") )
		{
			if(this.isArticolo17() || almenoUnaSedutaStampata(db,configTipo))
				return "Parzialmente";
			else 
				return "NO";
		}
		else
			return "NO";
	}
	
	private boolean tutteSeduteStampate(Connection db,ConfigTipo configTipo)
	{
		int num = Partita.loadSeduteArt17(db,this.getId() , configTipo, false);
		if(num>0)
			return false;
		else
			return true;	
	}
	
	private boolean almenoUnaSedutaStampata(Connection db,ConfigTipo configTipo)
	{
		int num = Partita.loadSeduteArt17(db,this.getId() , configTipo, true);
		if(num>0)
			return true;
		else
			return false;	
	}
	
	public String getStatoMacellazione(Connection db, ConfigTipo configTipo)
	{
		String stato = "";
		int numCapiOviniDestMavamPartitaSedute = 0;
		int numCapiCapriniDestMavamPartitaSedute = 0;
			
		Iterator<PartitaSeduta> sedute = Partita.loadSeduteWithoutPartita(db,this.getId() , configTipo).iterator();
		while(sedute.hasNext())
		{
			PartitaSeduta temp = sedute.next();
			numCapiOviniDestMavamPartitaSedute+=temp.getVam_num_capi_ovini();
			numCapiCapriniDestMavamPartitaSedute+=temp.getVam_num_capi_caprini();
			numCapiOviniDestMavamPartitaSedute+=temp.getMavam_num_capi_ovini();
			numCapiCapriniDestMavamPartitaSedute+=temp.getMavam_num_capi_caprini();
		}
		
		numCapiOviniDestMavamPartitaSedute+=this.getVam_num_capi_ovini();
		numCapiCapriniDestMavamPartitaSedute+=this.getVam_num_capi_caprini();
		numCapiOviniDestMavamPartitaSedute+=this.getMavam_num_capi_ovini();
		numCapiCapriniDestMavamPartitaSedute+=this.getMavam_num_capi_caprini();
	
		if((numCapiOviniDestMavamPartitaSedute==0 || this.getCd_num_capi_ovini() <0)  && (numCapiCapriniDestMavamPartitaSedute==0 || this.getCd_num_capi_caprini() <0))
			stato = "Non Macellata";
		else if(numCapiOviniDestMavamPartitaSedute<this.getCd_num_capi_ovini() ||
		   numCapiCapriniDestMavamPartitaSedute<this.getCd_num_capi_caprini())
			stato = "Parzialmente Macellata";
		else
			stato = "Completamente Macellata";
		
		return stato;
		
		
		
		
		
		
	}
	
	public String getColorStampatoArt17(Connection db, ConfigTipo configTipo, String stampato)
	{
		if(stampato.equals("SI"))
			return "#00FF00";
		else if(stampato.equals("NO"))
			return "red";
		else 
			return "yellow";
	}
	
	
	public static ArrayList<CategoriaRischio> loadCategorieRischio( int id_capo, Connection db )
	{
	
		ArrayList<CategoriaRischio>	ret		= new ArrayList<CategoriaRischio>();
		PreparedStatement				stat	= null;
		ResultSet						res		= null;
		
		try
		{
			stat	= db.prepareStatement( "SELECT * FROM m_vpm_categorie_riscontrate WHERE id_partita = ? and trashed_date IS NULL" );
			stat.setInt( 1, id_capo );
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSetCategoria( res ) );
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
	
	
	private static CategoriaRischio loadResultSetCategoria( ResultSet res ) 
			throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
		CategoriaRischio ret = new CategoriaRischio();
		
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

	public boolean isSpecie_suina() {
		return specie_suina;
	}

	public void setSpecie_suina(boolean specie_suina) {
		this.specie_suina = specie_suina;
	}

	public Integer getCd_num_capi_ovini_18() {
		return cd_num_capi_ovini_18;
	}

	public void setCd_num_capi_ovini_18(Integer cd_num_capi_ovini_18) {
		this.cd_num_capi_ovini_18 = cd_num_capi_ovini_18;
	}

	public Integer getCd_num_capi_caprini_18() {
		return cd_num_capi_caprini_18;
	}

	public void setCd_num_capi_caprini_18(Integer cd_num_capi_caprini_18) {
		this.cd_num_capi_caprini_18 = cd_num_capi_caprini_18;
	}


	public boolean isCancellabile(Connection db, ConfigTipo configTipo){
		
		if (this.isArticolo17())
			return false;
		
		if (!getStampatoArt17(db, configTipo).equals("NO"))
			return false;
		
		return true;
		
		
	}
	
	public void cancella(Connection db, int idPartita) throws SQLException{
		PreparedStatement pst = null;
		String sql = "update m_partite set trashed_date = now(), deleted_by = ?, note_cancellazione = ? where id = ?";
		pst = db.prepareStatement(sql);
		int i = 0;
		pst.setInt(++i, deleted_by);
		pst.setString(++i, note_cancellazione);
		pst.setInt(++i, idPartita);
		pst.executeUpdate();
		
		PreparedStatement pst2 = null;
		String sql2 = "update m_partite_sedute set trashed_date = now(), deleted_by = ?, note_cancellazione = ? where id_partita = ?";
		pst2 = db.prepareStatement(sql2);
		int i2 = 0;
		pst2.setInt(++i2, deleted_by);
		pst2.setString(++i2, note_cancellazione);
		pst2.setInt(++i2, idPartita);
		pst2.executeUpdate();
	}
	
}
