/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.camera_commercio.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.aspcfs.utils.CsvLunghezzaFissa;
import org.aspcfs.utils.CsvLunghezzaFissa.Colonna;

public class CsvLunghezzaFissaTest {

	public static void main(String[] args)
	{
		
		Colonna[] colonne = 
		{
				
//		PROGRESSIVO DELLA POSIZIONE U.L. D'INTERESSE E RELATIVI ESTREMI DELLA POSIZIONE REA D'INTERESSE
			new Colonna( 2, "provincia_cciaa" ),
			new Colonna( 9, "num_iscrizione" ),
			new Colonna( 4, "progressivo_ul" ),
			new Colonna( 16, "cf_impresa" ),
			new Colonna( 21, "num_registro_imprese" ),
			new Colonna( 8, "data_iscrizione_posiz_rea" ),
			new Colonna( 8, "data_caricamento_archivio_pos_rea" ),
			
//		ESTREMI SOGGETTO IMPRESA ALLA QUALE APPARTIENE LA POSIZIONE REA D'INTERESSE
//			ESTREMI INDIRIZZO SEDE LEGALE
			new Colonna( 2, "provincia_sede_legale" ),
			new Colonna( 3, "codice_stato_sede_legale" ),
			new Colonna( 3, "codice_istat_comune_sede_legale" ),
			new Colonna( 30, "descrizione_comune_sede_legale" ),
			new Colonna( 30, "descrizione_localita_sede_legale" ),
			new Colonna( 3, "cod_toponimo_sede_legale" ),
			new Colonna( 30, "via_sede_legale" ),
			new Colonna( 6, "civico_sede_legale" ),
			new Colonna( 5, "cap_sede_legale" ),
			new Colonna( 5, "cod_stradario_sede_legale" ),
			
			new Colonna( 8, "data_iscrizione_registro_imprese" ),
			
//		ESTREMI SEZIONI SPECIALI DEL R.I. (3 volte)
			new Colonna( 3, "cod_sez_spec_1" ),
			new Colonna( 8, "data_inizio_appartenenza_sez_spec_1" ),
			new Colonna( 1, "flag_coltiv_diretto_1" ),
			new Colonna( 8, "data_fine_appartenenza_sez_spec_1" ),
			
			new Colonna( 3, "cod_sez_spec_2" ),
			new Colonna( 8, "data_inizio_appartenenza_sez_spec_2" ),
			new Colonna( 1, "flag_coltiv_diretto_2" ),
			new Colonna( 8, "data_fine_appartenenza_sez_spec_2" ),
			
			new Colonna( 3, "cod_sez_spec_3" ),
			new Colonna( 8, "data_inizio_appartenenza_sez_spec_3" ),
			new Colonna( 1, "flag_coltiv_diretto_3" ),
			new Colonna( 8, "data_fine_appartenenza_sez_spec_3" ),
			
			
			new Colonna( 305, "ragione_sociale" ),
			new Colonna( 11, "partita_iva" ),
			new Colonna( 2, "cod_natura_giuridica" ),
			new Colonna( 1, "stato_attivita_impresa" ),
			new Colonna( 17, "capitale_sociale" ),
			new Colonna( 28, "valuta_capitale_sociale" ),
			new Colonna( 80, "oggetto_sociale_1" ),
			new Colonna( 80, "oggetto_sociale_2" ),
			new Colonna( 80, "oggetto_sociale_3" ),
			new Colonna( 80, "oggetto_sociale_4" ),
			new Colonna( 1, "flag_omissis_oggetto_sociale" ),
			new Colonna( 2, "cod_tipo_liquidazione" ),
			new Colonna( 8, "data_apertura_liquidazione" ),
			
//		ESTREMI DELLA POSIZIONE U.L. D'INTERESSE
			new Colonna( 3, "cod_tipo_ul" ),
			new Colonna( 2, "tipo_localizzazione" ),
			new Colonna( 305, "denominazione_ul" ),
			new Colonna( 50, "insegna_ul" ),
			new Colonna( 8, "data_apertura_ul" ),
			
//		ESTREMI INDIRIZZO DELLA U.L.
			new Colonna( 3, "codice_stato_ul" ),
			new Colonna( 2, "provincia_ul" ),
			new Colonna( 3, "codice_istat_ul" ),
			new Colonna( 30, "descrizione_comune_ul" ),
			new Colonna( 30, "descrizione_localita_ul" ),
			new Colonna( 3, "cod_toponimo_ul" ),
			new Colonna( 30, "via_ul" ),
			new Colonna( 6, "civico_ul" ),
			new Colonna( 5, "cap_ul" ),
			new Colonna( 5, "cod_stradario_ul" ),
			
			new Colonna( 16, "telefono_ul" ),
			new Colonna( 1, "stato_attivita_ul" ),
			new Colonna( 2, "cod_acusale_cessazione_ul" ),
			new Colonna( 8, "data_cessazione_ul" ),
			new Colonna( 8, "data_denuncia_cessazione_ul" ),
			new Colonna( 4, "anno_denuncia_addetti_ul" ),
			new Colonna( 6, "num_addetti_familiari_ul" ),
			new Colonna( 6, "num_addetti_subordinati_ul" ),
			new Colonna( 6, "cod_attivita_istat_91_primario" ),
			
//		ATTIVITA' DELLA U.L.
			new Colonna( 80, "descrizione_attivita_ul_1" ),
			new Colonna( 80, "descrizione_attivita_ul_2" ),
			new Colonna( 80, "descrizione_attivita_ul_3" ),
			new Colonna( 80, "descrizione_attivita_ul_4" ),
			new Colonna( 1, "flag_omissis_descrizione_attivita_ul" ),
			
//		ESTREMI ALBI/RUOLI DELLA U.L. (5 volte)
			new Colonna( 2, "cod_tipo_ruolo_1" ),
			new Colonna( 1, "cod_forma_ruolo_1" ),
			new Colonna( 7, "num_ruolo_1" ),
			new Colonna( 8, "data_inizio_ruolo_1" ),
			new Colonna( 8, "data_cessazione_ruolo_1" ),
			
			new Colonna( 2, "cod_tipo_ruolo_2" ),
			new Colonna( 1, "cod_forma_ruolo_2" ),
			new Colonna( 7, "num_ruolo_2" ),
			new Colonna( 8, "data_inizio_ruolo_2" ),
			new Colonna( 8, "data_cessazione_ruolo_2" ),

			new Colonna( 2, "cod_tipo_ruolo_3" ),
			new Colonna( 1, "cod_forma_ruolo_3" ),
			new Colonna( 7, "num_ruolo_3" ),
			new Colonna( 8, "data_inizio_ruolo_3" ),
			new Colonna( 8, "data_cessazione_ruolo_3" ),

			new Colonna( 2, "cod_tipo_ruolo_4" ),
			new Colonna( 1, "cod_forma_ruolo_4" ),
			new Colonna( 7, "num_ruolo_4" ),
			new Colonna( 8, "data_inizio_ruolo_4" ),
			new Colonna( 8, "data_cessazione_ruolo_4" ),

			new Colonna( 2, "cod_tipo_ruolo_5" ),
			new Colonna( 1, "cod_forma_ruolo_5" ),
			new Colonna( 7, "num_ruolo_5" ),
			new Colonna( 8, "data_inizio_ruolo_5" ),
			new Colonna( 8, "data_cessazione_ruolo_5" ),
			
			
			new Colonna( 1, "flag_omissis_albi_ruoli" )
		};
		
		CsvLunghezzaFissa csv = new CsvLunghezzaFissa( colonne );
		
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection db = DriverManager.getConnection("jdbc:postgresql:"+"gisa",
					"postgres","admin");
			
			BufferedReader br = new BufferedReader( 
					new InputStreamReader( 
							new FileInputStream( "D:\\file_dati\\gisa Infocamere\\izsm\\OUTPUT_SK_COMPLESSA.txt" ) ) );
			String riga = br.readLine();
			while( (riga != null) && (riga.length() > 0) )
			{
				csv.setRiga( riga );
				
				BCameraCommercio bc = new BCameraCommercio();
				bc.carica( csv );
				bc.store( db );
				
				riga = br.readLine();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
