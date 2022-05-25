
CREATE OR REPLACE VIEW public.registro_trasgressori_vista_backup AS 
 SELECT controllo.ticketid AS idcontrollo,
    date_part('year'::text, controllo.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
        CASE
            WHEN nucleo.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo.description::text
        END AS ente1,
        CASE
            WHEN nucleo2.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo2.description::text
        END AS ente2,
        CASE
            WHEN nucleo3.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo3.description::text
        END AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    controllo.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.ordinanza_emessa,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
    string_agg(DISTINCT sa_pv.header_allegato, ';'::text) AS allegato_pv,
    string_agg(DISTINCT sa_al.header_allegato, ';'::text) AS allegato_al,
    string_agg(DISTINCT concat_ws('#'::text, pagopa.identificativo_univoco_versamento, pagopa.url_file_avviso, pagopa.stato_pagamento), '##'::text) AS pagopa_iuv
   FROM ticket t
     JOIN ticket controllo ON controllo.tipologia = 3 AND t.id_controllo_ufficiale::text = controllo.id_controllo_ufficiale::text
     LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
     LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
     LEFT JOIN lookup_qualifiche nucleo2 ON nucleo2.description::text = mv_nucleo.nucleo_ispettivo_due::text
     LEFT JOIN lookup_qualifiche nucleo3 ON nucleo3.description::text = mv_nucleo.nucleo_ispettivo_tre::text
     LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
     LEFT JOIN sanzioni_allegati sa_pv ON sa_pv.id_sanzione = t.ticketid AND sa_pv.tipo_allegato = 'SanzionePV'::text AND sa_pv.trashed_date IS NULL
     LEFT JOIN sanzioni_allegati sa_al ON sa_al.id_sanzione = t.ticketid AND sa_al.tipo_allegato = 'SanzioneAL'::text AND sa_al.trashed_date IS NULL
     LEFT JOIN pagopa_pagamenti pagopa ON pagopa.id_sanzione = t.ticketid AND pagopa.trashed_date IS NULL
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND controllo.trashed_date IS NULL AND controllo.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND controllo.status_id <> 1 AND t.id_nonconformita > 0
  GROUP BY controllo.ticketid, t.ticketid, asl.description, nucleo.in_dpat, nucleo.description, nucleo2.in_dpat, nucleo2.description, nucleo3.in_dpat, nucleo3.description, rt.id, rt.id_sanzione, rt.id_controllo_ufficiale, rt.anno, rt.data_prot_entrata, rt.pv_oblato_ridotta, rt.fi_assegnatario, rt.presentati_scritti, rt.presentata_richiesta_riduzione, rt.presentata_richiesta_audizione, rt.ordinanza_emessa, rt.importo_sanzione_ingiunta, rt.data_emissione, rt.giorni_lavorazione, rt.ordinanza_ingiunzione_oblata, rt.sentenza_favorevole, rt.importo_stabilito, rt.ordinanza_ingiunzione_sentenza, rt.iscrizione_ruoli_sattoriali, rt.note1, rt.note2, rt.data_inserimento, rt.data_modifica, rt.id_utente_modifica, rt.presentata_opposizione, rt.trashed_date, rt.richiesta_rateizzazione, rt.rata1, rt.rata2, rt.rata3, rt.rata4, rt.rata5, rt.rata6, rt.rata7, rt.rata8, rt.rata9, rt.rata10, rt.allegato_documentale, rt.importo_effettivamente_versato1, rt.importo_effettivamente_versato2, rt.importo_effettivamente_versato3, rt.importo_effettivamente_versato4, rt.competenza_regionale, rt.pratica_chiusa, rt.data_ultima_notifica, rt.data_pagamento, rt.pagamento_ridotto_consentito, rt.data_ultima_notifica_ordinanza, rt.data_pagamento_ordinanza, rt.pagamento_ridotto_consentito_ordinanza
UNION
 SELECT NULL::integer AS idcontrollo,
    date_part('year'::text, t.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
    ''::text AS ente1,
    ''::text AS ente2,
    ''::text AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    t.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.ordinanza_emessa,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
    ''::text AS allegato_pv,
    ''::text AS allegato_al,
    ''::text AS pagopa_iuv
   FROM ticket t
     LEFT JOIN lookup_site_id asl ON asl.code = t.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND t.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND t.note_internal_use_only ~~* '%RECORD GENERATO AUTOMATICAMENTE PER INSERIMENTO SANZIONE FORZATA NEL REGISTRO TRASGRESSORI%'::text;

ALTER TABLE public.registro_trasgressori_vista_backup
  OWNER TO postgres;

-- View: public.registro_trasgressori_vista

 DROP VIEW public.registro_trasgressori_vista_test;
 DROP VIEW public.registro_trasgressori_vista;

CREATE OR REPLACE VIEW public.registro_trasgressori_vista AS 
 SELECT controllo.ticketid AS idcontrollo,
    date_part('year'::text, controllo.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
        CASE
            WHEN nucleo.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo.description::text
        END AS ente1,
        CASE
            WHEN nucleo2.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo2.description::text
        END AS ente2,
        CASE
            WHEN nucleo3.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo3.description::text
        END AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    controllo.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.ordinanza_emessa,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
    string_agg(DISTINCT sa_pv.header_allegato, ';'::text) AS allegato_pv,
    string_agg(DISTINCT sa_al.header_allegato, ';'::text) AS allegato_al,
    string_agg(DISTINCT concat_ws('#'::text, pagopa_pv.identificativo_univoco_versamento, pagopa_pv.url_file_avviso, pagopa_pv.stato_pagamento), '##'::text) AS pagopa_pv_iuv,
    string_agg(DISTINCT concat_ws('#'::text, pagopa_no.identificativo_univoco_versamento, pagopa_no.url_file_avviso, pagopa_no.stato_pagamento), '##'::text) AS pagopa_no_iuv,
    pagopa_pv_r.check as pagopa_pv_oblato_ridotta,
    pagopa_pv_ur.check as pagopa_pv_oblato_ultraridotta,
    COALESCE(pagopa_pv_r.data_esito_singolo_pagamento, pagopa_pv_ur.data_esito_singolo_pagamento)::timestamp without time zone as pagopa_pv_data_pagamento,
   string_agg( distinct concat( 
   case when pagopa_no_1.id is not null then 'PRIMA RATA; ' else '' end, 
   case when pagopa_no_2.id is not null then 'SECONDA RATA; ' else '' end,
   case when pagopa_no_3.id is not null then 'TERZA RATA; ' else '' end,
   case when pagopa_no_4.id is not null then 'QUARTA RATA; ' else '' end,
   case when pagopa_no_5.id is not null then 'QUINTA RATA; ' else '' end,
   case when pagopa_no_6.id is not null then 'SESTA RATA; ' else '' end,
   case when pagopa_no_7.id is not null then 'SETTIMA RATA; ' else '' end,
   case when pagopa_no_8.id is not null then 'OTTAVA RATA; ' else '' end,
   case when pagopa_no_9.id is not null then 'NONA RATA; ' else '' end,
   case when pagopa_no_10.id is not null then 'DECIMA RATA; ' else '' end
    ), '') AS pagopa_no_rate_pagate
	
   FROM ticket t
     JOIN ticket controllo ON controllo.tipologia = 3 AND t.id_controllo_ufficiale::text = controllo.id_controllo_ufficiale::text
     LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
     LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
     LEFT JOIN lookup_qualifiche nucleo2 ON nucleo2.description::text = mv_nucleo.nucleo_ispettivo_due::text
     LEFT JOIN lookup_qualifiche nucleo3 ON nucleo3.description::text = mv_nucleo.nucleo_ispettivo_tre::text
     LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
     LEFT JOIN sanzioni_allegati sa_pv ON sa_pv.id_sanzione = t.ticketid AND sa_pv.tipo_allegato = 'SanzionePV'::text AND sa_pv.trashed_date IS NULL
     LEFT JOIN sanzioni_allegati sa_al ON sa_al.id_sanzione = t.ticketid AND sa_al.tipo_allegato = 'SanzioneAL'::text AND sa_al.trashed_date IS NULL
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione

     LEFT JOIN pagopa_pagamenti pagopa_pv on pagopa_pv.id_sanzione = t.ticketid and pagopa_pv.trashed_date is null and pagopa_pv.tipo_pagamento = 'PV'
     LEFT JOIN pagopa_pagamenti pagopa_no on pagopa_no.id_sanzione = t.ticketid and pagopa_no.trashed_date is null and pagopa_no.tipo_pagamento = 'NO' 

     LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id, data_esito_singolo_pagamento, true as check from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'PV' and tipo_riduzione = 'R' and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_pv_r on pagopa_pv_r.id_sanzione = t.ticketid
     LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id, data_esito_singolo_pagamento, true as check from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'PV' and tipo_riduzione = 'U' and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_pv_ur on pagopa_pv_ur.id_sanzione = t.ticketid

   LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 1 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_1 on pagopa_no_1.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 2 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_2 on pagopa_no_2.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 3 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_3 on pagopa_no_3.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 4 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_4 on pagopa_no_4.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 5 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_5 on pagopa_no_5.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 6 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_6 on pagopa_no_6.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 7 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_7 on pagopa_no_7.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 8 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_8 on pagopa_no_8.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 9 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_9 on pagopa_no_9.id_sanzione = t.ticketid
  LEFT JOIN (select distinct on (id_sanzione) id_sanzione, id from pagopa_pagamenti where trashed_date is null and tipo_pagamento = 'NO' and indice = 10 and stato_pagamento = 'PAGAMENTO COMPLETATO' ) pagopa_no_10 on pagopa_no_10.id_sanzione = t.ticketid
         
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND controllo.trashed_date IS NULL AND controllo.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND controllo.status_id <> 1 AND t.id_nonconformita > 0 

 GROUP BY controllo.ticketid, t.ticketid, asl.description, nucleo.in_dpat, nucleo.description, nucleo2.in_dpat, nucleo2.description, nucleo3.in_dpat, nucleo3.description, rt.id, rt.id_sanzione, rt.id_controllo_ufficiale, rt.anno, rt.data_prot_entrata, rt.pv_oblato_ridotta, rt.fi_assegnatario, rt.presentati_scritti, rt.presentata_richiesta_riduzione, rt.presentata_richiesta_audizione, rt.ordinanza_emessa, rt.importo_sanzione_ingiunta, rt.data_emissione, rt.giorni_lavorazione, rt.ordinanza_ingiunzione_oblata, rt.sentenza_favorevole, rt.importo_stabilito, rt.ordinanza_ingiunzione_sentenza, rt.iscrizione_ruoli_sattoriali, rt.note1, rt.note2, rt.data_inserimento, rt.data_modifica, rt.id_utente_modifica, rt.presentata_opposizione, rt.trashed_date, rt.richiesta_rateizzazione, rt.rata1, rt.rata2, rt.rata3, rt.rata4, rt.rata5, rt.rata6, rt.rata7, rt.rata8, rt.rata9, rt.rata10, rt.allegato_documentale, rt.importo_effettivamente_versato1, rt.importo_effettivamente_versato2, rt.importo_effettivamente_versato3, rt.importo_effettivamente_versato4, rt.competenza_regionale, rt.pratica_chiusa, rt.data_ultima_notifica, rt.data_pagamento, rt.pagamento_ridotto_consentito, rt.data_ultima_notifica_ordinanza, rt.data_pagamento_ordinanza, rt.pagamento_ridotto_consentito_ordinanza, pagopa_pv_r.check, pagopa_pv_ur.check, pagopa_pv_r.data_esito_singolo_pagamento, pagopa_pv_ur.data_esito_singolo_pagamento
UNION
 SELECT NULL::integer AS idcontrollo,
    date_part('year'::text, t.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
    ''::text AS ente1,
    ''::text AS ente2,
    ''::text AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    t.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.ordinanza_emessa,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
    ''::text AS allegato_pv,
    ''::text AS allegato_al,
    ''::text AS pagopa_iuv_pv, 
    ''::text AS pagopa_iuv_no, 
    null::boolean AS pagopa_pv_oblato_ridotta, 
    null::boolean AS pagopa_pv_oblato_ultraridotta, 
    null::timestamp without time zone AS pagopa_pv_data_pagamento, 
    ''::text AS pagopa_no_rate_pagate 
   FROM ticket t
     LEFT JOIN lookup_site_id asl ON asl.code = t.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND t.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND t.note_internal_use_only ~~* '%RECORD GENERATO AUTOMATICAMENTE PER INSERIMENTO SANZIONE FORZATA NEL REGISTRO TRASGRESSORI%'::text;

ALTER TABLE public.registro_trasgressori_vista
  OWNER TO postgres;
