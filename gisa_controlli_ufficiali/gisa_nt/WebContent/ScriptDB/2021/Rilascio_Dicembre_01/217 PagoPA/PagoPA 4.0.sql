-- Chi: Bartolo Sansone
-- Cosa: Flusso 234 - PagoPA 4.0 (post rev 7)
-- Quando: 02/11/2021 

CREATE TABLE lookup_sanzioni_autorita_competenti(code serial primary key, description text, default_item boolean default false, level integer, enabled boolean default true);

CREATE TABLE lookup_sanzioni_enti_destinatari(code serial primary key, description text, default_item boolean default false, level integer, enabled boolean default true);


insert into lookup_sanzioni_autorita_competenti(description) values ('ALTRO');
insert into lookup_sanzioni_autorita_competenti(description) values ('ICQ');
insert into lookup_sanzioni_autorita_competenti(description) values ('PREFETTO');
insert into lookup_sanzioni_autorita_competenti(description) values ('CAPO DEL COMPARTIMENTO MARITTIMO');
insert into lookup_sanzioni_autorita_competenti(description) values ('ASSESS. AGRICOLT');
insert into lookup_sanzioni_autorita_competenti(description) values ('SINDACO');
insert into lookup_sanzioni_autorita_competenti(description) values ('UVAC');



insert into lookup_sanzioni_enti_destinatari(description) values ('ALTRO');
insert into lookup_sanzioni_enti_destinatari(description) values ('TESORERIA PROVINCIALE DELLO STATO DI');
insert into lookup_sanzioni_enti_destinatari(description) values ('COMUNE DI');
insert into lookup_sanzioni_enti_destinatari(description) values ('MINISTERO DELLA SALUTE');
insert into lookup_sanzioni_enti_destinatari(description) values ('ASSESSORATO AGRICOLTURA DELLA REGIONE CAMPANIA');


CREATE TABLE sanzioni_competenze(id serial, id_sanzione integer, id_autorita_competente integer, descrizione_autorita_competente text, id_ente_destinatario integer, descrizione_ente_destinatario text,enteredby integer, entered timestamp without time zone default now(), trashed_date timestamp without time zone, note_hd text);

-- View: public.registro_trasgressori_vista

-- DROP VIEW public.registro_trasgressori_vista;

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
    string_agg(DISTINCT CONCAT_WS('#', pagopa.identificativo_univoco_versamento, pagopa.url_file_avviso, pagopa.stato_pagamento), '##'::text) AS pagopa_iuv
   FROM ticket t
     JOIN ticket controllo ON controllo.tipologia = 3 AND t.id_controllo_ufficiale::text = controllo.id_controllo_ufficiale::text
     LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
     LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
     LEFT JOIN lookup_qualifiche nucleo2 ON nucleo2.description::text = mv_nucleo.nucleo_ispettivo_due::text
     LEFT JOIN lookup_qualifiche nucleo3 ON nucleo3.description::text = mv_nucleo.nucleo_ispettivo_tre::text
     LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
     LEFT JOIN sanzioni_allegati sa_pv ON sa_pv.id_sanzione = t.ticketid AND sa_pv.tipo_allegato = 'SanzionePV'::text AND sa_pv.trashed_date IS NULL
     LEFT JOIN sanzioni_allegati sa_al ON sa_al.id_sanzione = t.ticketid AND sa_al.tipo_allegato = 'SanzioneAL'::text AND sa_al.trashed_date IS NULL
     LEFT JOIN pagopa_pagamenti pagopa on pagopa.id_sanzione = t.ticketid and pagopa.trashed_date is null
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
    ''::text as pagopa_iuv
   FROM ticket t
     LEFT JOIN lookup_site_id asl ON asl.code = t.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND t.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND t.note_internal_use_only ~~* '%RECORD GENERATO AUTOMATICAMENTE PER INSERIMENTO SANZIONE FORZATA NEL REGISTRO TRASGRESSORI%'::text;

ALTER TABLE public.registro_trasgressori_vista
  OWNER TO postgres;
