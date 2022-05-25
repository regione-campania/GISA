-- Function: digemon.dbi_get_registro_trasgressori()

-- DROP FUNCTION digemon.dbi_get_registro_trasgressori();

CREATE OR REPLACE FUNCTION digemon.dbi_get_registro_trasgressori()
  RETURNS TABLE("n. prog" text, "Id controllo" text, "ASL di competenza" text, "Ente accertatore 1" text, "Ente accertatore 2" text, "Ente accertatore 3" text, "PV n." text, "Num. sequestro eventualmente effettuato" text, "Data accertamento" text, "Data prot. in entrata in regione" text, "Trasgressore" text, "Obbligato in solido" text, "Importo sanzione ridotta" text, "Importo sanzione ridotta del 30%" text, "Illecito di competenza della U.O.D. 01" text, "Data ultima notifica" text, "PV oblato in misura ridotta" text, "Importo effettivamente introitato (1)" text, "Data pagamento" text, "Funzionario assegnatario" text, "Presentati scritti difensivi" text, "Presentata richiesta riduzione sanzione e/o rateizzazione" text, "Presentata richiesta audizione" text, "Ordinanza emessa" text, "Num. Ordinanza" text, "Data di emissione dell'Ordinanza" text, "Giorni di lavorazione pratica" text, "Importo sanzione ingiunta" text, "Data Ultima Notifica Ordinanza" text, "Data Pagamento Ordinanza" text, "Pagamento Ordinanza Effettuato nei Termini" text, "Concessa rateizzazione dell'ordinanza-ingiunzione" text, "Rate pagate" text, "Ordinanza ingiunzione oblata" text, "Importo effettivamente introitato (2)" text, "Presentata opposizione all'ordinanza-ingiunzione" text, "Sentenza favorevole al ricorrente" text, "Importo stabilito dalla A.G." text, "Ordinanza-ingiunzione oblata secondo sentenza" text, "Importo effettivamente introitato (3)" text, "Avviata per esecuzione forzata" text, "Importo effettivamente introitato (4)" text, "Note Gruppo 1" text, "Note Gruppo 2" text, "Pratica chiusa" text) AS
$BODY$
DECLARE

BEGIN
		RETURN QUERY 
			select concat_ws('\',progressivo::text,substring(anno::text,3,4)), 
				idcontrollo::text, asl::text, 
				-----------------
				case 	
					when length(ente1::text)>0 and trim(ente2::text)is null then ente1::text
					when ente3::text is not null and ente1::text = ente2::text and ente1::text = ente3::text then ente1::text
					when ente3::text is not null and ente1::text = ente2::text and ente2::text <> ente3::text then ente1::text 
					when ente3::text is null and ente1::text = ente2::text then ente1::text
					when ente1::text <> ente2::text then ente1::text
				end,
				case 	
					when length(ente1::text)>0 and trim(ente2::text)is null then ''::text
					when ente3::text is not null and ente1::text = ente2::text and ente1::text = ente3::text then ''::text
					when ente3::text is not null and ente1::text = ente2::text and ente2::text <> ente3::text then ente3::text 
					when ente3::text is not null and ente1::text <> ente2::text and ente2::text = ente3::text then ente2::text 
					when ente3::text is null and ente1::text = ente2::text then ''::text
					when ente1::text <> ente2::text then ente2::text
				end , 
				case 		
					when length(ente1::text)>0 and trim(ente2::text)is null then ''::text
					when ente3::text is not null and ente1::text = ente2::text and ente1::text = ente3::text then ''::text 
					when ente3::text is not null and ente1::text = ente2::text and ente2::text <> ente3::text then ''::text
					when ente3::text is not null and ente1::text <> ente2::text and ente2::text = ente3::text then ''::text 
					when ente3::text is null and ente1::text = ente2::text then ''::text
					when ente1::text <> ente2::text then ente3::text
				end , 
				-------------------
				numeroverbale::text, numeroverbalesequestri::text, dataaccertamento::text, 
				data_prot_entrata::text, trasgressore::text, obbligato::text, importosanzioneridotta::text, 
				importosequestroriduzione,
				case when competenza_regionale then 'SI' else 'NO' end, data_ultima_notifica::text, case when pv_oblato_ridotta then 'SI' else 'NO' end, 
				importo_effettivamente_versato1::text, 
				data_pagamento::text, fi_assegnatario::text, case when presentati_scritti then 'SI' else 'NO' end, case when presentata_richiesta_riduzione then 'SI' else 'NO' end, 
				case when presentata_richiesta_audizione then 'SI' else 'NO' end, 
				case when ordinanza_emessa::text = 'A' then 'ord. archiviazione' when ordinanza_emessa::text = 'B' then 'ord. ingiunzione' when ordinanza_emessa::text = 'C' then 'pratica non lavorata' end, num_ordinanza::text, 
				data_emissione::text, case when giorni_lavorazione > 0 then giorni_lavorazione::text else '' end, 
				importo_sanzione_ingiunta::text, 
				data_ultima_notifica_ordinanza::text,
				data_pagamento_ordinanza::text, case when pagamento_ridotto_consentito then 'SI' else 'NO' end, case when richiesta_rateizzazione then 'SI' else 'NO' end, 
				concat_ws('', case when rata1 then 'RATA 1: SI ' else '' end, case when rata2 then 'RATA 2: SI ' else '' end, case when rata3 then 'RATA 3: SI ' else '' end, case when rata4 then 'RATA 4: SI ' else '' end, 
				case when rata5 then 'RATA 5: SI ' else '' end, case when rata6 then 'RATA 6: SI ' else '' end, case when rata7 then 'RATA 7: SI ' else '' end, case when rata8 THEN 'RATA 8: SI ' else '' end, 
				case when rata9 then 'RATA 9: SI ' else '' end, 
				case when rata10 then 'RATA 10: SI' else '' end),
				case when ordinanza_ingiunzione_oblata then 'SI' else 'NO' end, importo_effettivamente_versato2::text, case when presentata_opposizione then 'SI' else 'NO' end, case when sentenza_favorevole then 'SI' else 'NO' end, 
				importo_stabilito::text, case when ordinanza_ingiunzione_sentenza then 'SI' else 'NO' end, importo_effettivamente_versato3::text, case when iscrizione_ruoli_sattoriali then 'SI' else 'NO' end,
				importo_effettivamente_versato4::text, note1::text, note2::text, case when pratica_chiusa then 'SI' else 'NO' end
			from 
				registro_trasgressori_vista 
			order by anno desc, progressivo desc;
			

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_registro_trasgressori()
  OWNER TO postgres;
