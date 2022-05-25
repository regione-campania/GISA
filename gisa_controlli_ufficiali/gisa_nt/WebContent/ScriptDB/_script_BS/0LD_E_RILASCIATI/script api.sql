'<b>Detentore:</b> ' || cognome_detentore || ' ' || nome_detentore || ' ' || cf_detentore || '
' || '<br/><b>Classificazione:</b> ' || classificazione || ' <b>Sottospecie:</b> ' || sottospecie || ' <b>Modalita''</b> : ' || modalita || '
' || ' <br/><b>Num. alveari:</b> ' || num_alveari || ' <b>Num. sciami/nuclei:</b> ' || num_sciami || ' <br/><b>Data censimento:</b> '|| COALESCE(to_char(data_assegnazione_censimento, 'dd/MM/yyyy'),'') || '
' ||'<br/><b>Data inizio:</b> ' || data_apertura_stab || ' <b>Data chiusura:</b> '|| COALESCE(data_chiusura_stab,data_cessazione_stab,'') || '
' || '<br/><b>Ubicazione:</b> ' || comune_stab || ' - ' || indirizzo_stab || '
' || '<br/><b>Stato:</b> '|| stato_stab || '; <b>Sincronizzato BDN:</b> ' || sincronizzato_bdn || '<br/><b>Latitudine:</b> '|| latitudine ||' <b>Longitudine:</b> '||longitudine 


-- View: public.apicoltura_apiari_denormalizzati_view

-- DROP VIEW public.apicoltura_apiari_denormalizzati_view;

CREATE OR REPLACE VIEW public.apicoltura_apiari_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_apicoltura_imprese,
    stab.id AS id_apicoltura_apiari,
        CASE
            WHEN o.sincronizzato_bdn THEN 'SI'::text
            ELSE 'NO'::text
        END AS sincronizzato_bdn,
    las.description AS stato_impresa,
    lastab.description AS stato_stab,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    to_char(o.data_inizio, 'dd/MM/yyyy'::text) AS data_inizio_attivita,
    ((sedeop.via::text ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN ' '::text || sedeop.civico
            ELSE ''::text
        END))::character varying(300) AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    sedeop.provincia AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    taa.description AS tipo_attivita,
    o.telefono_fisso,
    o.telefono_cellulare,
    o.faxt AS fax,
    i.comune,
    asl.description AS asl_apiario,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.id_sinvsa,
    stab.descrizione_errore,
    stab.progressivo,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    sedestab.via AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    sedestab.provincia AS prov_stab,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    (
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text || ', '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text AS indirizzo_rapp_sede_legale,
    detentore.codice_fiscale AS cf_detentore,
    detentore.nome AS nome_detentore,
    detentore.cognome AS cognome_detentore,
    lokmod.description AS modalita,
    lokcla.description AS classificazione,
    loksot.description AS sottospecie,
    stab.num_alveari,
    stab.num_sciami,
    stab.num_pacchi,
    stab.num_regine,
    to_char(stab.data_inizio, 'dd/MM/yyyy'::text) AS data_apertura_stab,
    to_char(stab.data_chiusura, 'dd/MM/yyyy'::text) AS data_chiusura_stab,
    stab.data_prossimo_controllo,
    asl.code AS id_asl_apiario,
    i.latitudine,
    i.longitudine,
    o.codice_azienda,
    stab.flag_laboratorio,
    ''::text AS macroarea,
    ''::text AS aggregazione,
    taa.description AS attivita,
    1 AS id_tipo_linea_produttiva,
    stab.id_controllo_ultima_categorizzazione,
    stab.data_assegnazione_censimento,
    aslperapicoltore.description AS asl_apicoltore,
    aslperapicoltore.code AS id_asl_apicoltore,
        to_char(stab.data_cessazione, 'dd/MM/yyyy'::text) AS data_cessazione_stab

   FROM apicoltura_imprese o
     LEFT JOIN suap_ric_scia_operatore scia ON scia.id = o.id_richiesta_suap
     LEFT JOIN suap_ric_scia_stabilimento sciastab ON sciastab.id_operatore = scia.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = sciastab.id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN apicoltura_rel_impresa_soggetto_fisico rels ON rels.id_apicoltura_imprese = o.id AND rels.enabled
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     JOIN apicoltura_relazione_imprese_sede_legale ros ON ros.id_apicoltura_imprese = o.id AND ros.enabled
     JOIN opu_indirizzo sedeop ON sedeop.id = ros.id_indirizzo
     JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN apicoltura_apiari stab ON stab.id_operatore = o.id
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_apicoltura_stati_apiario las ON las.code = o.stato
     LEFT JOIN lookup_apicoltura_stati_apiario lastab ON lastab.code = stab.stato
     LEFT JOIN apicoltura_lookup_tipo_attivita taa ON taa.code = o.tipo_attivita_apicoltura
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
     LEFT JOIN lookup_site_id aslperapicoltore ON aslperapicoltore.code = o.id_asl
     LEFT JOIN opu_soggetto_fisico detentore ON detentore.id = stab.id_soggetto_fisico
     LEFT JOIN apicoltura_lookup_modalita lokmod ON lokmod.code = stab.id_apicoltura_lookup_modalita
     LEFT JOIN apicoltura_lookup_classificazione lokcla ON lokcla.code = stab.id_apicoltura_lookup_classificazione
     LEFT JOIN apicoltura_lookup_sottospecie loksot ON loksot.code = stab.id_apicoltura_lookup_sottospecie
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.apicoltura_apiari_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.apicoltura_apiari_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.apicoltura_apiari_denormalizzati_view TO report;
