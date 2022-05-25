/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.utils;

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

import org.aspcfs.modules.macellazioninewsintesis.utils.MacelliUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class ConfigTipo
{
	private static final long serialVersionUID = 8313006891554941893L;
	
	private int idTipo;
	
	public ConfigTipo(int idTipo)
	{
		this.idTipo=idTipo;
	}
	
	
	public int getIdTipo() 
	{
		return idTipo;
	}

	public String getNomeTipo() 
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_tipo_"+idTipo);
	}
	
	public void setIdTipo(int idTipo) 
	{
		this.idTipo = idTipo;
	}

	public String getNomeTabella()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_tabella_"+idTipo);
	}
	
	public String getNomeCampoIdTabella()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_campo_id_tabella_"+idTipo);
	}
	
	public String getNomeBean()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_bean_"+idTipo);
	}
	
	public String getNomeBeanAjax()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_bean_ajax_"+idTipo);
	}
	
	public String getDescrizioneBean()
	{
		return ApplicationPropertiesConfigTipo.getProperty("descrizione_bean_"+idTipo);
	}
	
	public String getNomeCampoIdentificativoTabella()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_campo_identificativo_tabella_"+idTipo);
	}
	
	public String getDescrizioneCampoIdentificativoTabella()
	{
		return ApplicationPropertiesConfigTipo.getProperty("descrizione_campo_identificativo_tabella_"+idTipo);
	}
	
	public String getNomeBeanJsp()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_bean_jsp_"+idTipo);
	}
	
	public String getNomeVariabileGlobaleActionMacellazioni()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_variabile_globale_action_macellazioni_"+idTipo);
	}
	
	public String getMetodoLoadRequest()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_load_request_"+idTipo);
	}
	
	public String getMetodoIdMacello()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_id_macello_"+idTipo);
	}
	
	public String getFiltroLookup()
	{
		return ApplicationPropertiesConfigTipo.getProperty("filtro_lookup_"+idTipo);
	}
	
	public String getMetodoId()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_id_"+idTipo);
	}
	
	public String getMetodoLoadById()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_load_by_id_"+idTipo);
	}
	
	public boolean getSezioneSpeditore()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("sezione_speditore_"+idTipo));
	}
	
	public String getNomeCampoRifAltreTabelle()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_campo_rif_altre_tabelle_"+idTipo);
	}
	
	public String getNomeMetodoRifAltreTabelle()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_metodo_rif_altre_tabelle_"+idTipo);
	}
	
	public String getNomeCampoRifTabellaTamponi()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_campo_rif_tabella_tamponi_"+idTipo);
	}
	
	public boolean getEsitiMultipli()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("esiti_multipli_"+idTipo));
	}
	
	public String getNumeroColonneList()
	{
		return ApplicationPropertiesConfigTipo.getProperty("numero_colonne_list_"+idTipo);
	}
	
//	public String getNomeVariabileGlobaleActionMacellazioniArray()
//	{
//		return ApplicationPropertiesConfigTipo.getProperty("nome_variabile_globale_action_macellazioni_array_"+idTipo);
//	}
	
	public String[] getColonneLista()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_lista_"+idTipo).split(",");
	}
	
	public String[] getColonneRegistroMacellazione()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_registro_macellazione_"+idTipo).split(",");
	}
	
	public float[] getSizesRegistroMacellazione()
	{
		String[] temp = ApplicationPropertiesConfigTipo.getProperty("sizes_registro_macellazione_"+idTipo).split(",");
		int i=0;
		float[] sizes = new float[temp.length];
		while(i<temp.length)
		{
			sizes[i]= Float.parseFloat(temp[i]);
			i++;
		}
		
		return sizes;
	}
	
	public String[] getDescrizioneColonneLista()
	{
		return ApplicationPropertiesConfigTipo.getProperty("descrizione_colonne_lista_"+idTipo).split(",");
	}
	
	public String getNomeVariabileOld()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_variabile_old_"+idTipo);
	}
	
	public String getMetodoLoad()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_load_"+idTipo);
	}

	public String getSequenceTabella()
	{
		return ApplicationPropertiesConfigTipo.getProperty("sequence_tabella_"+idTipo);
	}
	
	public String getPackageBean()
	{
		return ApplicationPropertiesConfigTipo.getProperty("package_bean_"+idTipo);
	}
	
	public String getPackageAction()
	{
		return ApplicationPropertiesConfigTipo.getProperty("package_action_"+idTipo);
	}
	
	public String getNomeAction()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_action_"+idTipo);
	}
	
	public String getNomeMetodoIdentificativo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_metodo_identificativo_"+idTipo);
	}
	public String getNomeVariabileIdJsp()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_variabile_id_jsp_"+idTipo);
	}
	public String getNomeBeanList()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_bean_list_"+idTipo);
	}
	
	public boolean hasSeduteMacellazione()
	{
		if (ApplicationPropertiesConfigTipo.getProperty("sedute_macellazione_"+idTipo)!=null && !ApplicationPropertiesConfigTipo.getProperty("sedute_macellazione_"+idTipo).equals(""))
			return Boolean.valueOf(ApplicationPropertiesConfigTipo.getProperty("sedute_macellazione_"+idTipo));
		return false;
	}
	
	public String getSuffissoTabelle()
	{
		return ApplicationPropertiesConfigTipo.getProperty("suffisso_tabelle_"+idTipo);
	}
	
	public String getSuffissoViste()
	{
		return ApplicationPropertiesConfigTipo.getProperty("suffisso_viste_"+idTipo);
	}
	
	public String[] getColonneRegistroMacellazioneCodici()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_registro_macellazione_codici_"+idTipo).split(",");
	}
	
	public String[] getColonneRegistroMacellazioneCodiciTipi()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_registro_macellazione_codici_tipi_"+idTipo).split(",");
	}
	
	public String getLabelCapiMacellati()
	{
		return ApplicationPropertiesConfigTipo.getProperty("label_capi_macellati_"+idTipo);
	}
	
	public String[] getColonneArt17()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_art17_"+idTipo).split(",");
	}
	
	public String[] getColonneArt17Codici()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_art17_codici_"+idTipo).split(",");
	}
	
	public String[] getColonneArt17CodiciTipi()
	{
		return ApplicationPropertiesConfigTipo.getProperty("colonne_art17_codici_tipi_"+idTipo).split(",");
	}
	
	public float[] getSizesArt17()
	{
		String[] temp = ApplicationPropertiesConfigTipo.getProperty("sizes_art17_"+idTipo).split(",");
		int i=0;
		float[] sizes = new float[temp.length];
		while(i<temp.length)
		{
			sizes[i]= Float.parseFloat(temp[i]);
			i++;
		}
		
		return sizes;
	}
	
	public int getNumeroColonneArt17()
	{
		return Integer.parseInt(ApplicationPropertiesConfigTipo.getProperty("numero_colonne_art17_"+idTipo));
	}
	
	public String getMessaggioCapoAggiunto()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_capo_aggiunto_"+idTipo);
	}
	
	public String getMessaggioCapoAggiornato()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_capo_aggiornato_"+idTipo);
	}
	
	public String getMessaggioCapoNonInserito()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_capo_non_inserito_"+idTipo);
	}
	
	public String getMessaggioCapoNonModificato()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_capo_non_modificato_"+idTipo);
	}
	
	public String getQueryEsistenzaCapo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("query_esistenza_capo_"+idTipo);
	}
	
	public String getMetodoSetIdentificativo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_set_identificativo_ajax_"+idTipo);
	}
	
	public String getMetodoCostruisciQueryEsistenzaCapo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_costruisci_query_esistenza_capo_"+idTipo);
	}
	
	public String getMetodoCostruisciStatementEsistenzaCapo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("metodo_costruisci_statement_esistenza_capo_"+idTipo);
	}
	
	public boolean controlloUnivocitaUpdate()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("controllo_univocita_update_"+idTipo));
	}
	
	public boolean hasMoreDestinatari()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("has_more_destinatari_"+idTipo));
	}
	
	public int getNumDestinatari()
	{
		return Integer.parseInt(ApplicationPropertiesConfigTipo.getProperty("get_num_destinatari_"+idTipo));
	}
	
	public boolean hasDestinatariNumCapi()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("has_destinatari_num_capi_"+idTipo));
	}
	
	public boolean hasNumCapiVam()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("has_num_capi_vam_"+idTipo));
	}
	
	public boolean hasProgressivoMacellazione()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("has_progressivo_macellazione_"+idTipo));
	}
	
	public String getNomeFunzioneModificaCapo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_funzione_modifica_capo_"+idTipo);
	}
	
	public String getNomeFunzioneAggiungiCapo()
	{
		return ApplicationPropertiesConfigTipo.getProperty("nome_funzione_aggiungi_capo_"+idTipo);
	}
	
	public String getMessaggioNessunCapoMacellato()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_nessun_capo_macellato_"+idTipo);
	}
	
	public String getMessaggioNessunTamponePerCapiMacellati()
	{
		return ApplicationPropertiesConfigTipo.getProperty("messaggio_nessun_tampone_per_capi_macellati_"+idTipo);
	}
	
	public boolean hasDestinatarioCarni()
	{
		return Boolean.parseBoolean(ApplicationPropertiesConfigTipo.getProperty("has_destinatario_carni_"+idTipo));
	}
	
}
