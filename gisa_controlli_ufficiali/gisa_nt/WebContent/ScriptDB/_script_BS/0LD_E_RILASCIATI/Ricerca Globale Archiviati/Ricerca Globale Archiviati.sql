-- CHI: Bartolo Sansone
--  COSA: Ricerca globale archiviati
--  QUANDO: 06/11/2018

-- View: public.view_globale_archiviati_minimale

-- DROP VIEW public.view_globale_archiviati_minimale;

CREATE OR REPLACE VIEW public.view_globale_archiviati_minimale AS 
 SELECT DISTINCT controllo.num_verbale,
    controllo.orgidt,
    controllo.id_stabilimento,
    controllo.id_apiario,
    controllo.alt_id,
    controllo.ticketid,
    controllo.identificativo,
    controllo.id_nc,
    controllo.tipo_attivita,
    controllo.data_cancellazione_attivita,
    controllo.id_cu,
    controllo.tipologia_campioni,
    controllo.motivazione_piano_campione,
    controllo.motivazione_campione,
    controllo.tipo_piano_monitoraggio,
    controllo.sottopiano,
    controllo.esito,
    controllo.analita,
    controllo.matrice,
    controllo.data_inizio_controllo,
    controllo.data_chiusura_controllo,
    controllo.anno_chiusura,
    anagrafica.riferimento_id AS org_id,
    anagrafica.ragione_sociale,
    anagrafica.asl_rif,
    anagrafica.asl,
    anagrafica.tipologia_operatore AS tipologia,
    lto.description AS tipologia_operatore,
    NULL::timestamp without time zone AS data_cancellazione_operatore
   FROM ( SELECT
                CASE
                    WHEN b.barcode IS NOT NULL AND t.location IS NOT NULL THEN (t.location::text || ' - '::text) || b.barcode::text
                    WHEN b.barcode IS NULL THEN t.location::text
                    ELSE NULL::text
                END AS num_verbale,
            t.org_id AS orgidt,
            t.id_stabilimento,
            t.id_apiario,
            t.alt_id,
            t.ticketid,
            t.identificativo,
            t.id_nonconformita AS id_nc,
            t.tipologia AS tipo_attivita,
            t.trashed_date AS data_cancellazione_attivita,
                CASE
                    WHEN t.tipologia = 1 OR t.tipologia = 9 OR t.tipologia = 15 THEN ( SELECT tt.id_controllo_ufficiale
                       FROM ticket tt
                      WHERE tt.ticketid = t.id_nonconformita)
                    ELSE t.id_controllo_ufficiale
                END AS id_cu,
                CASE
                    WHEN t.tipologia = 1 THEN 'Sanzioni'::text
                    WHEN t.tipologia = 2 THEN 'Campioni'::text
                    WHEN t.tipologia = 6 THEN 'Notizie di reato'::text
                    WHEN t.tipologia = 7 THEN 'Tamponi'::text
                    WHEN t.tipologia = 8 THEN 'Non conformitÃ '::text
                    WHEN t.tipologia = 9 THEN 'Sequestri'::text
                    WHEN t.tipologia = 15 THEN 'Follow up'::text
                    WHEN t.tipologia = 3 THEN 'Controlli Ufficiali'::text
                    ELSE 'Altro'::text
                END AS tipologia_campioni,
            t.motivazione_piano_campione,
                CASE
                    WHEN lti.code = 2 THEN lpiano.description::text::character varying
                    ELSE lti.description
                END AS motivazione_campione,
                CASE
                    WHEN lpianopadre.code > 0 THEN lpianopadre.description
                    ELSE lpiano.description
                END AS tipo_piano_monitoraggio,
            lpiano.description AS sottopiano,
                CASE
                    WHEN t.sanzioni_amministrative > 0 AND analiti.esito_id IS NULL THEN esito.description::text
                    WHEN analiti.esito_id > 0 THEN esitonew.description::text
                    WHEN t.tipologia = 2 AND t.sanzioni_amministrative < 0 AND analiti.esito_id < 0 THEN 'Da Attendere'::text
                    ELSE 'N.D'::text
                END AS esito,
            analiti.cammino AS analita,
            matrici.cammino AS matrice,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.closed IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.closed)::text
                END AS anno_chiusura
           FROM ticket t
             LEFT JOIN barcode_osa b ON b.id_campione::text = t.ticketid::text
             LEFT JOIN analiti_campioni analiti ON analiti.id_campione = t.ticketid
             LEFT JOIN matrici_campioni matrici ON matrici.id_campione = t.ticketid
             LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
             LEFT JOIN lookup_piano_monitoraggio lpiano ON lpiano.code = t.motivazione_piano_campione
             LEFT JOIN lookup_piano_monitoraggio lpianopadre ON lpianopadre.code = lpiano.id_padre
             LEFT JOIN lookup_esito_campione esito ON t.sanzioni_amministrative = esito.code
             LEFT JOIN lookup_esito_campione esitonew ON analiti.esito_id = esitonew.code) controllo
     JOIN ( SELECT global_arch_view.riferimento_id,
            global_arch_view.riferimento_id_nome,
            global_arch_view.riferimento_id_nome_col,
            global_arch_view.riferimento_id_nome_tab,
            global_arch_view.data_inserimento,
            global_arch_view.ragione_sociale,
            global_arch_view.asl_rif,
            global_arch_view.asl,
            global_arch_view.codice_fiscale,
            global_arch_view.codice_fiscale_rappresentante,
            global_arch_view.partita_iva,
            global_arch_view.categoria_rischio,
            global_arch_view.prossimo_controllo,
            global_arch_view.num_riconoscimento,
            global_arch_view.n_reg,
            global_arch_view.n_linea,
            global_arch_view.nominativo_rappresentante,
            global_arch_view.tipo_attivita_descrizione,
            global_arch_view.tipo_attivita,
            global_arch_view.data_inizio_attivita,
            global_arch_view.data_fine_attivita,
            global_arch_view.stato,
            global_arch_view.id_stato,
            global_arch_view.comune,
            global_arch_view.provincia_stab,
            global_arch_view.indirizzo,
            global_arch_view.cap_stab,
            global_arch_view.comune_leg,
            global_arch_view.provincia_leg,
            global_arch_view.indirizzo_leg,
            global_arch_view.cap_leg,
            global_arch_view.macroarea,
            global_arch_view.aggregazione,
            global_arch_view.attivita,
            null as path_attivita_completo,
           null as gestione_masterlist,
            global_arch_view.norma,
            global_arch_view.id_norma,
            global_arch_view.tipologia_operatore,
            global_arch_view.targa,
            global_arch_view.tipo_ricerca_anagrafica,
            global_arch_view.color,
            global_arch_view.n_reg_old,
           null as id_tipo_linea_reg_ric,
            global_arch_view.id_controllo_ultima_categorizzazione,
            global_arch_view.id_linea
           FROM global_arch_view) anagrafica ON controllo.orgidt = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'organization'::text OR controllo.id_stabilimento = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'opu_stabilimento'::text OR controllo.id_apiario = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'apicoltura_apiari'::text OR controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'suap_ric_scia_stabilimento'::text OR controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'sintesis_stabilimento'::text OR controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'stabilimenti'::text
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = anagrafica.tipologia_operatore;

ALTER TABLE public.view_globale_archiviati_minimale
  OWNER TO postgres;
