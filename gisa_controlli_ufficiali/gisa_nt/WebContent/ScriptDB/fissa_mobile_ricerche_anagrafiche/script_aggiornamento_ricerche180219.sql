-- sequenza di drop e create
update master_list_flag_linee_attivita set fisso =true where fisso is false and mobile is false and riconoscibili;
-- disabilita trigger organization --------------------------
update organization set tipo_dest = 'Es. Commerciale' where tipo_dest ilike '%commerciale%';

drop view ricerca_anagrafiche; 
-- DROP VIEW public.apicoltura_apiari_denormalizzati_view cascade;
DROP VIEW public.apicoltura_apiari_denormalizzati_view cascade;

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
    sedeop.id AS id_indirizzo_operatore,
    sedeop.cap AS cap_sede_legale,
    sedeop.provincia AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
        CASE
            WHEN lokcla.code = 1 THEN 1
            WHEN lokcla.code = 2 THEN 2
            WHEN ml.fisso THEN 1
            WHEN ml.mobile THEN 2
            ELSE '-1'::integer
        END AS tipo_attivita,
        CASE
            WHEN lokcla.code = 1 THEN 'Con Sede Fissa'::text
            WHEN lokcla.code = 2 THEN 'Senza Sede Fissa'::text
            WHEN ml.fisso THEN 'Con Sede Fissa'::text
            WHEN ml.mobile THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS tipo_descrizione_attivita,
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
    sedestab.id AS id_indirizzo,
    sedestab.via AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    sedestab.provincia AS prov_stab,
    stab.categoria_rischio,
    soggsl.id AS id_soggetto_fisico,
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
    detentore.id AS id_detentore,
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
    COALESCE(latt.macroarea, ''::text) AS macroarea,
    COALESCE(latt.aggregazione, ''::text) AS aggregazione,
    COALESCE(latt.attivita, taa.description, ''::text) AS attivita,
    1 AS id_tipo_linea_produttiva,
    stab.id_controllo_ultima_categorizzazione,
    stab.data_assegnazione_censimento,
    aslperapicoltore.description AS asl_apicoltore,
    aslperapicoltore.code AS id_asl_apicoltore,
    to_char(stab.data_cessazione, 'dd/MM/yyyy'::text) AS data_cessazione_stab,
    COALESCE(latt.codice_macroarea, ''::text) AS codice_macroarea,
    COALESCE(latt.codice_aggregazione, ''::text) AS codice_aggregazione,
    COALESCE("substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)), ''::text) AS codice_attivita
   FROM apicoltura_imprese o
     LEFT JOIN suap_ric_scia_operatore scia ON scia.id = o.id_richiesta_suap
     LEFT JOIN suap_ric_scia_stabilimento sciastab ON sciastab.id_operatore = scia.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = sciastab.id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.codice_univoco = latt.codice
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

-------------------------- ricreo report_campioni_new--------------------------------------

CREATE OR REPLACE VIEW public.report_campioni_new AS 
 SELECT DISTINCT asl.code AS id_asl,
    asl.description AS asl,
    COALESCE(COALESCE(o.name, aziende.ragione_sociale), api.ragione_sociale) AS ragione_sociale,
    COALESCE(COALESCE(o.partita_iva, aziende.partita_iva::bpchar), api.partita_iva::bpchar) AS partita_iva,
        CASE
            WHEN aziende.id_stabilimento > 0 THEN 'Stabilimenti Nuova Gestione'::character varying
            WHEN o.tipologia = 201 THEN 'ZONE DI MOLLUSCHICOLTURA'::character varying
            WHEN lo.code > 0 THEN lo.description
            WHEN c.id_apiario > 0 THEN 'APIARIO'::character varying
            ELSE ''::character varying
        END AS tipo_operatore,
        CASE
            WHEN o.tipologia = 1 THEN (lin.categoria::text || '-->'::text) || lin.linea_attivita::text
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN (lass.linea_attivita_stabilimenti_soa || '-->'::text) || lass.linea_attivita_stabilimenti_soa_desc
            WHEN o.tipologia = 201 THEN lzp.description
            WHEN aziende.id_stabilimento > 0 THEN aziende.path_attivita_completo::text
            ELSE 'N.D'::text
        END AS tipologia_impianto,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((((dpatatt.descrizione || ' '::text) || '-'::text) || (ind.alias_indicatore || ' : '::text)) || ind.descrizione)::character varying
            WHEN lti.code > 0 AND lti.id_indicatore > 0 THEN ((((dpatattti.descrizione || ' '::text) || '-'::text) || (indti.alias_indicatore || ' : '::text)) || indti.descrizione)::character varying
            ELSE lti.description
        END AS motivazione_campione,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((lti.description::text || ':'::text) || lpm.description::text)::character varying
            ELSE lti.description
        END AS tipo_piano_monitoraggio_old,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date < '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            ELSE NULL::integer
        END AS id_piano,
        CASE
            WHEN c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
            ELSE lti.code
        END AS id_attivita,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text THEN 'P'::text
            WHEN lti.description IS NOT NULL THEN 'A'::text
            ELSE 'N.D.'::text
        END AS piano_attivita,
    c.ticketid AS id_campione,
    c.assigned_date AS data_prelievo,
    c.identificativo AS identificativo_campione,
    cu.componente_nucleo AS prelevatore_1_a4,
    cu.componente_nucleo_due AS prelevatore_2_a4,
    cu.componente_nucleo_tre AS prelevatore_3_a4,
        CASE
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS strategia_campionamento_a1,
        CASE
            WHEN lpm.description::text ~~* '%BSE%'::text THEN '1'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text
            WHEN lpm.description::text ~~* '%contaminanti%'::text THEN '3'::text
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text
            WHEN lpm.description::text ~~* '%OGM%'::text THEN '7'::text
            ELSE '9'::text
        END AS capitoli_piani_a3,
    lsp.description AS specie_alimento_b6,
        CASE
            WHEN cfv.valore_campione IS NOT NULL AND chf.label_campo::text ~~* '%B7%'::text THEN cfv.valore_campione
            ELSE c.check_circuito_ogm
        END AS metodo_produzione_b7,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_campione,
    to_date(c.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_campione,
    c.id_controllo_ufficiale,
        CASE
            WHEN c.sanzioni_amministrative > 0 AND ac.esito_id IS NULL THEN esito.description::text
            WHEN ac.esito_id > 0 THEN esitonew.description::text
            WHEN c.tipologia = 2 AND c.sanzioni_amministrative < 0 AND ac.esito_id < 0 THEN 'Da Attendere'::text
            ELSE 'N.D'::text
        END AS esito,
    COALESCE(ac.esito_punteggio, c.punteggio) AS punteggio_campione,
    COALESCE(resp_new.description, responsabilita.description::text::character varying) AS responsabilita_positivita,
    COALESCE(ac.esito_data, c.est_resolution_date) AS data_esito_analita,
    ac.esito_motivazione_respingimento,
    c.solution AS note_esito_campione,
    c.cause AS codice_accettazione,
    c.location AS num_verbale,
    b.barcode AS barcode_scheda,
    split_part(ac.cammino, '->'::text, 1) AS analita_lev_1,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 2) IS NOT NULL AND split_part(ac.cammino, '->'::text, 2) <> ''::text THEN split_part(ac.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS analita_lev_2,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 3) IS NOT NULL AND split_part(ac.cammino, '->'::text, 3) <> ''::text THEN split_part(ac.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS analita_lev_3,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 4) IS NOT NULL AND split_part(ac.cammino, '->'::text, 4) <> ''::text THEN split_part(ac.cammino, '->'::text, 4)
            ELSE 'N.D'::text
        END AS analita_lev_4,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 1) IS NOT NULL AND split_part(mc.cammino, '->'::text, 1) <> ''::text THEN split_part(mc.cammino, '->'::text, 1)
            ELSE 'N.D'::text
        END AS matrice_lev_1,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 2) IS NOT NULL AND split_part(mc.cammino, '->'::text, 2) <> ''::text THEN split_part(mc.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS matrice_lev_2,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 3) IS NOT NULL AND split_part(mc.cammino, '->'::text, 3) <> ''::text THEN split_part(mc.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS matrice_lev_3,
    c.problem AS note_campione,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_controllo,
    COALESCE(lpm.codice, lti.codice) AS codice_interno_piano_attivita
   FROM ticket c
     LEFT JOIN ticket cu ON cu.ticketid::text = c.id_controllo_ufficiale::text AND cu.tipologia = 3
     LEFT JOIN lookup_site_id asl ON c.site_id = asl.code
     LEFT JOIN linee_attivita_controlli_ufficiali lacu ON lacu.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN la_imprese_linee_attivita lla ON lla.id = lacu.id_linea_attivita
     LEFT JOIN la_rel_ateco_attivita rel ON rel.id = lla.id_rel_ateco_attivita
     LEFT JOIN la_linee_attivita lin ON rel.id_linee_attivita = lin.id
     LEFT JOIN linee_attivita_controlli_ufficiali_stab_soa lass ON lass.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN campioni_fields_value cfv ON cfv.id_campione = c.ticketid
     LEFT JOIN campioni_html_fields chf ON chf.id = cfv.id_campioni_html_fields
     LEFT JOIN lookup_piano_monitoraggio lppna ON lppna.code = chf.id_piano_monitoraggio AND lppna.codice_interno = 370
     LEFT JOIN lookup_specie_pnaa lsp ON lsp.code::text = cfv.valore_campione
     LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code
     LEFT JOIN lookup_responsabilita_positivita responsabilita ON responsabilita.code = c.responsabilita_positivita
     LEFT JOIN organization o ON o.org_id = c.org_id AND (o.trashed_date IS NULL OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone)
     LEFT JOIN lookup_zone_di_produzione lzp ON lzp.code = o.tipologia_acque
     LEFT JOIN opu_operatori_denormalizzati_view aziende ON aziende.id_stabilimento = c.id_stabilimento
     LEFT JOIN apicoltura_apiari_denormalizzati_view api ON api.id_apicoltura_apiari = c.id_apiario
     LEFT JOIN lookup_tipologia_operatore lo ON lo.code = o.tipologia
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = c.motivazione_piano_campione
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = c.motivazione_campione
     LEFT JOIN dpat_indicatore_new ind ON ind.id = lpm.code
     LEFT JOIN dpat_piano_attivita_new dpatatt ON dpatatt.id = ind.id_piano_attivita AND (dpatatt.stato = ANY (ARRAY[0, 2])) AND dpatatt.data_scadenza IS NULL AND dpatatt.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN dpat_indicatore_new indti ON indti.id = lti.code
     LEFT JOIN dpat_piano_attivita_new dpatattti ON dpatattti.id = indti.id_piano_attivita AND (dpatattti.stato = ANY (ARRAY[0, 2])) AND dpatattti.data_scadenza IS NULL AND dpatattti.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN analiti_campioni ac ON c.ticketid = ac.id_campione
     LEFT JOIN matrici_campioni mc ON mc.id_campione = c.ticketid
     LEFT JOIN lookup_responsabilita_positivita resp_new ON resp_new.code = ac.esito_responsabilita_positivita
     LEFT JOIN lookup_esito_campione esitonew ON ac.esito_id = esitonew.code
     LEFT JOIN barcode_osa b ON b.id_campione::text = c.ticketid::text
  WHERE c.tipologia = 2 AND c.trashed_date IS NULL AND cu.trashed_date IS NULL AND c.assigned_date IS NOT NULL AND c.location IS NOT NULL AND c.location::text <> ''::text;

ALTER TABLE public.report_campioni_new
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_campioni_new TO postgres;
GRANT ALL ON TABLE public.report_campioni_new TO report;

----------------------------------- aggiorno global_org_view ---------------------------------------
drop view public.global_org_view;
CREATE OR REPLACE VIEW public.global_org_view AS 
 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    '-1'::integer AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    '-1'::integer AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    o.entered AS data_inserimento,
    o.name AS ragione_sociale,
    o.site_id AS asl_rif,
    l_1.description AS asl,
    o.codice_fiscale,
    o.codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.prossimo_controllo,
        CASE
            WHEN o.tipologia <> 1 AND o.tipologia <> 201 AND COALESCE(btrim(o.numaut::text), btrim(o.account_number::text)) IS NOT NULL THEN COALESCE(o.account_number, o.numaut)
            WHEN o.tipologia = 201 THEN o.cun::character varying
            ELSE ''::character varying
        END AS num_riconoscimento,
    ''::text AS n_reg,
        CASE
            WHEN o.tipologia = 1 THEN btrim(o.account_number::text)
            WHEN o.tipologia <> 1 AND o.tipologia <> 201 AND COALESCE(btrim(o.numaut::text), btrim(o.account_number::text)) IS NOT NULL THEN COALESCE(o.account_number, o.numaut)::text
            WHEN o.tipologia = 201 THEN o.cun::character varying::text
            ELSE NULL::text
        END AS n_linea,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS nominativo_rappresentante,
        CASE
            WHEN o.tipologia = ANY (ARRAY[15,17,5,255,4,201,14,16,12,8]) THEN 'Con Sede Fissa'::text
            WHEN o.tipologia = ANY (ARRAY[22, 9]) THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS tipo_attivita_descrizione,
        CASE
	    WHEN o.tipologia = ANY (ARRAY[15,17,5,255,4,201,14,16,12,8]) THEN 1
            WHEN o.tipologia = ANY (ARRAY[22, 9]) THEN 2
            ELSE '-1'::integer
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date1
            ELSE NULL::timestamp without time zone
        END AS data_fine_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 'Sospeso'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 'Cessato'::text
                ELSE 'Attivo'::text
            END
            WHEN o.tipologia = 9 THEN o.stato_impresa
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Attivo'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Cessato'::text
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            WHEN o.tipologia = 201 AND o.stato_classificazione IS NOT NULL THEN lsc.description::text
            ELSE 'N.D'::text
        END AS stato,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 0
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 0
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 0
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 4
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 2
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN '-1'::integer
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 4
                ELSE 0
            END
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 0
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 1
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 2
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 3
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 4
            WHEN o.tipologia = 201 AND o.stato_classificazione IS NOT NULL THEN lsc.code
            ELSE 0
        END AS id_stato,
    COALESCE(oa5.city, oa1.city, oa7.city, oa.city, 'N.D.'::character varying) AS comune,
    COALESCE(oa5.state, oa1.state, oa7.state, oa.state, 'N.D.'::character varying) AS provincia_stab,
    COALESCE(oa5.addrline1, oa1.addrline1, oa7.addrline1, oa.addrline1, 'N.D.'::character varying) AS indirizzo,
    COALESCE(oa5.postalcode, oa1.postalcode, oa7.postalcode, oa.postalcode, 'N.D.'::character varying) AS cap_stab,
    COALESCE(oa5.latitude, oa1.latitude, oa7.latitude, oa.latitude) AS latitudine_stab,
    COALESCE(oa5.longitude, oa1.longitude, oa7.longitude, oa.longitude) AS longitudine_stab,
    COALESCE(oa1.city, oa5.city, oa.city, 'N.D.'::character varying) AS comune_leg,
    COALESCE(oa1.state, oa5.state, oa.state, 'N.D.'::character varying) AS provincia_leg,
    COALESCE(oa1.addrline1, oa5.addrline1, oa.addrline1, 'N.D.'::character varying) AS indirizzo_leg,
    COALESCE(oa1.postalcode, oa5.postalcode, oa.postalcode, 'N.D.'::character varying) AS cap_leg,
    COALESCE(oa1.latitude, oa5.latitude, oa.latitude) AS latitudine_leg,
    COALESCE(oa1.longitude, oa5.longitude, oa.longitude) AS longitudine_leg,
    COALESCE(ml8.macroarea, norme.macroarea_temp, mltemp.macroarea) AS macroarea,
    COALESCE(ml8.aggregazione, norme.aggregazione_temp, mltemp.aggregazione) AS aggregazione,
    COALESCE(ml8.attivita, norme.linea_temp, mltemp.path_descrizione::text) AS attivita,
        CASE
            WHEN ml8.macroarea IS NOT NULL THEN ml8.path_descrizione::text
            WHEN norme.macroarea_temp IS NOT NULL THEN concat_ws('->'::text, norme.macroarea_temp, norme.aggregazione_temp, norme.linea_temp)
            WHEN mltemp.macroarea IS NOT NULL THEN mltemp.path_descrizione::text
            ELSE ''::text
        END AS path_attivita_completo,
        CASE
            WHEN ml8.macroarea IS NOT NULL THEN NULL::text
            WHEN norme.macroarea_temp IS NOT NULL THEN NULL::text
            WHEN mltemp.macroarea IS NOT NULL THEN NULL::text
            ELSE 'Non previsto'::text
        END AS gestione_masterlist,
    nm.description AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
        CASE
            WHEN o.tipologia = 9 THEN o_a.targa::character varying
            ELSE o.nome_correntista
        END AS targa,
    0 AS tipo_ricerca_anagrafica,
    'red'::text AS color,
    NULL::text AS n_reg_old,
    '-1'::integer AS id_tipo_linea_produttiva,
    o.id_controllo_ultima_categorizzazione,
    o.org_id AS id_linea,
    COALESCE(ml8.codice_macroarea, mltemp.codice_macroarea) AS codice_macroarea,
    COALESCE(ml8.codice_aggregazione, mltemp.codice_aggregazione) AS codice_aggregazione,
    COALESCE("substring"(ml8.codice_attivita, length(ml8.codice_macroarea) + length(ml8.codice_aggregazione) + 3, length(ml8.codice_attivita)), "substring"(mltemp.codice_attivita, length(mltemp.codice_macroarea) + length(mltemp.codice_aggregazione) + 3, length(mltemp.codice_attivita))) AS codice_attivita
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN lookup_stato_classificazione lsc ON lsc.code = o.stato_classificazione
     LEFT JOIN organization_autoveicoli o_a ON o_a.org_id = o.org_id AND o_a.elimina IS NULL
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia AND (norme.tipo_molluschi_bivalvi IS NULL OR norme.tipo_molluschi_bivalvi = o.tipologia_acque)
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text AND o.tipologia = 2
     LEFT JOIN comuni_old caz ON caz.codiceistatcomune::text = az.cod_comune_azienda
     LEFT JOIN operatori_allevamenti op_detentore ON o.cf_correntista::text = op_detentore.cf
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type IS NULL
     LEFT JOIN organization_autoveicoli ov ON ov.org_id = o.org_id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata ml8 ON ml8.codice = norme.codice_univoco_ml
     LEFT JOIN linee_attivita_ml8_temp l ON l.org_id = o.org_id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml
  WHERE o.org_id <> 0 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND ((o.tipologia <> ALL (ARRAY[6, 11, 211, 1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = true);

ALTER TABLE public.global_org_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_org_view TO postgres;

----------------------------------------- global_ric_view e suap_ric_scia_operatori_denormalizzati_view -------------------------------------------
drop view global_ric_view; 


drop view public.suap_ric_scia_operatori_denormalizzati_view;
CREATE OR REPLACE VIEW public.suap_ric_scia_operatori_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    (((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text) ||
        CASE
            WHEN sedestab.civico IS NOT NULL THEN sedestab.civico
            ELSE ''::text
        END AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) ||
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) ||
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) ||
        CASE
            WHEN provsoggind.description IS NOT NULL THEN ('('::text || provsoggind.cod_provincia) || ')'::text
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale,
        CASE
            WHEN lps.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN lps.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN lps.stato = 2 THEN 'RESPINTO'::character varying(50)
            WHEN stab.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN stab.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN stab.stato = 2 THEN 'RESPINTO'::character varying(50)
            ELSE ''::character varying(50)
        END AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    COALESCE(lps.stato, stab.stato) AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    sedeop.id AS id_indirizzo_operatore,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    case 
     when ml.fisso then 1
     when ml.mobile then 2
    else -1 end as stab_id_attivita,
    case 
     when ml.fisso then 'Con Sede Fissa'::text
     when ml.mobile then 'Senza Sede Fissa'::text
    else 'N.D.'::text
    end as stab_descrizione_attivita, 
    --stab.tipo_attivita AS stab_id_attivita,
    --lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.id_tipo_richiesta,
    suapr.description AS descrizione_tipo_richiesta,
    o.validato,
    soggsl.data_nascita,
    soggsl.comune_nascita,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS linea_attivita,
    o.tipo_impresa AS id_tipo_impresa,
    o.tipo_societa AS id_topo_societa,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    stab.cessazione_stabilimento AS stab_cessazione_stabilimento,
    stab.numero_registrazione_variazione,
    stab.partita_iva_variazione,
    stab.alt_id,
    ll.short_description AS permesso,
    i.via AS via_sede_stab,
    i.civico AS civico_sede_stab,
    i.toponimo AS toponimo_sede_stab,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    comunisoggind.nome AS comune_residenza,
    stab.note_validazione,
    stab.stato_validazione,
    stab.sospensione_stabilimento,
    stab.data_inizio_sospensione,
    latt.id_lookup_configurazione_validazione AS id_tipo_linea_produttiva,
        CASE
            WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true
            WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true
            ELSE false
        END AS validabile,
    apicoltura.codice_azienda AS codice_azienda_apicoltura,
    stab.cessazione_stabilimento,
    rels.id_soggetto_fisico,
    NULL::integer AS id_controllo_ultima_categorizzazione,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    "substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)) AS codice_attivita_only
   FROM suap_ric_scia_operatore o
     LEFT JOIN apicoltura_imprese apicoltura ON o.id = apicoltura.id_richiesta_suap
     LEFT JOIN suap_lookup_tipo_richiesta suapr ON suapr.code = o.id_tipo_richiesta
     JOIN suap_ric_scia_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN suap_ric_scia_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN suap_ric_scia_stabilimento stab ON stab.id_operatore = o.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT join master_list_flag_linee_attivita ml on ml.codice_univoco = latt.codice
     LEFT JOIN lookup_flusso_originale_ml lconf ON lconf.code = latt.id_lookup_configurazione_validazione
     LEFT JOIN lookup_ente_scia ll ON lconf.ente_validazione = ll.code
     LEFT JOIN opu_lookup_norme_master_list nor ON nor.code = latt.id_norma
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.suap_ric_scia_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO report;

-- View: public.global_ric_view

-- DROP VIEW public.global_ric_view;

CREATE OR REPLACE VIEW public.global_ric_view AS 
 SELECT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'suap_ric_scia_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'suap_ric_scia_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'suap_ric_scia_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    NULL::integer AS categoria_rischio,
    NULL::text AS num_riconoscimento,
    NULL::text AS n_linea,
    o.numero_registrazione AS n_reg,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    'IN ITINERE'::character varying(50) AS stato,
    o.id_stato,
        CASE
            WHEN o.stab_id_attivita = 1 THEN o.comune_stab
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
            ELSE NULL::character varying
        END AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    nn.description AS norma,
    o.id_norma,
    1001 AS tipologia_operatore,
    NULL::text AS targa,
    2 AS tipo_ricerca_anagrafica,
    'blue'::text AS color,
    NULL::text AS n_reg_old,
    o.id_tipo_linea_produttiva,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita_only AS codice_attivita
   FROM suap_ric_scia_operatori_denormalizzati_view o
     JOIN opu_lookup_norme_master_list nn ON nn.code = o.id_norma
  WHERE (o.validato = false OR o.validato IS NULL) AND (o.id_tipo_richiesta = ANY (ARRAY[1, 2]));
ALTER TABLE public.global_ric_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_ric_view TO postgres;
GRANT SELECT ON TABLE public.global_ric_view TO report;



---------------------------------------------------------- parte OPU ----------------------------------------
DETAIL:  view estrazione_aziende_agricole_storico_categorizzazzioni depends on view opu_operatori_denormalizzati_view (rinominare in OLD)
view lista_imprese_852_old_ depends on view opu_operatori_denormalizzati_view (rinominare in old)
view global_org_opu_view_ depends on view opu_operatori_denormalizzati_view (rinominare in old)
view global_org_opu_view_tipo depends on view opu_operatori_denormalizzati_view (rinominare in old)
view tracciato_record_osm_opu_sinvsa depends on view opu_operatori_denormalizzati_view (rinominare in old)
view tracciato_record_soa_opu_sinvsa depends on view opu_operatori_denormalizzati_view (rinominare in old)

--------------------------------------------------------------------------------------------------------------
-- view global_opu_view depends on view opu_operatori_denormalizzati_view
--view opu_stabilimenti_linee_senza_numero_registrazione depends on view opu_operatori_denormalizzati_view
--view opu_stabilimenti_senza_numero_registrazione depends on view opu_operatori_denormalizzati_view
--view lista_opu_operatori_canili depends on view opu_operatori_denormalizzati_view 
--view lista_opu_operatori_commerciali depends on view opu_operatori_denormalizzati_view 
--view opu_operatori_denormalizzati_view_bdu depends on view opu_operatori_denormalizzati_view

drop view public.opu_operatori_denormalizzati_view cascade;
CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view AS 
 SELECT DISTINCT
        CASE
            WHEN stab.flag_dia IS NOT NULL THEN stab.flag_dia
            ELSE false
        END AS flag_dia,
    o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    concat_ws(' '::text, topsoggind.description, soggind.via, soggind.civico, comunisoggind.nome, concat('(', provsoggind.cod_provincia, ')'), soggind.cap) AS indirizzo_rapp_sede_legale,
    stati.description AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    stab.data_prossimo_controllo,
    stab.stato AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    -- stab.tipo_attivita AS stab_id_attivita,
    case 
     when ml.fisso then 1
     when ml.mobile then 2
    else -1 end as stab_id_attivita,
    case 
     when ml.fisso then 'Con Sede Fissa'::text
     when ml.mobile then 'Senza Sede Fissa'::text
    else 'N.D.'::text
    end as stab_descrizione_attivita,
    --lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
    provsedeop.cod_provincia AS sigla_prov_legale,
    provsoggind.cod_provincia AS sigla_prov_soggfisico,
    comunisoggind.nome AS comune_residenza,
    soggsl.data_nascita AS data_nascita_rapp_sede_legale,
    lotis.code AS impresa_id_tipo_societa,
    soggsl.comune_nascita AS comune_nascita_rapp_sede_legale,
    soggsl.sesso,
    soggind.civico,
    topsoggind.description AS toponimo_residenza,
    soggind.toponimo AS id_toponimo_residenza,
    sedeop.civico AS civico_sede_legale,
    sedeop.toponimo AS tiponimo_sede_legale,
    topsedeop.description AS toponimo_sede_legale,
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    latt.id_lookup_tipo_linee_mobili,
    '-1'::integer AS id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    stab.id_controllo_ultima_categorizzazione,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    "substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)) AS codice_attivita_only
   FROM opu_operatore o
     JOIN opu_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN opu_stabilimento stab ON stab.id_operatore = o.id
     JOIN opu_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN master_list_flag_linee_attivita ml on ml.codice_univoco = latt.codice	
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     --LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;


-- View: public.global_opu_view

-- DROP VIEW public.global_opu_view;

CREATE OR REPLACE VIEW public.global_opu_view AS 
 SELECT l.riferimento_id,
    l.riferimento_id_nome,
    l.ragione_sociale,
    l.asl_rif,
    l.asl,
    l.tipologia,
    l.codice_fiscale,
    l.data_cancellazione_operatore,
    l.codice_fiscale_rappresentante,
    l.partita_iva,
    l.descrizione,
    l.nome_correntista,
    l.tipologia_operatore,
    l.categoria_rischio,
    l.num_aut,
    l.n_reg,
    l.nominativo_rappresentante,
    l.attivita,
    l.stato_impresa,
    l.stato,
    l.comune,
    l.provincia,
    l.indirizzo,
    l.indirizzo_proprietario,
    l.cf_detentore,
    l.nome_detentore,
    l.indirizzo_detentore,
    l.import_opu,
    l.path_attivita_completo,
    l.tipo_attivita,
    l.id_stato,
    l.riferimento_id_nome_col,
    l.riferimento_id_nome_tab,
    COALESCE(l.linea_codice_nazionale, l.linea_codice_ufficiale_esistente) AS num_riconoscimento,
    l.id_tipo_linea_produttiva
   FROM ( SELECT o.id_stabilimento AS riferimento_id,
            'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'opu_stabilimento'::text AS riferimento_id_nome_tab,
            o.ragione_sociale,
            o.id_asl_stab AS asl_rif,
            o.stab_asl AS asl,
            999 AS tipologia,
            o.codice_fiscale_impresa AS codice_fiscale,
            NULL::timestamp without time zone AS data_cancellazione_operatore,
            o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
            o.partita_iva,
            ''::character varying AS descrizione,
            ''::text AS nome_correntista,
            'OPERATORE UNICO'::text AS tipologia_operatore,
            o.categoria_rischio,
            ''::character varying AS num_aut,
            o.numero_registrazione AS n_reg,
            (o.nome_rapp_sede_legale || ' '::text) || o.cognome_rapp_sede_legale AS nominativo_rappresentante,
            ''::text AS attivita,
            ''::text AS stato_impresa,
            o.stato,
                CASE
                    WHEN o.stab_id_attivita = 1 THEN o.comune_stab
                    WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
                    WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
                    ELSE NULL::character varying
                END AS comune,
            o.prov_stab AS provincia,
            o.indirizzo_stab AS indirizzo,
            ''::text AS indirizzo_proprietario,
            ''::text AS cf_detentore,
            ''::text AS nome_detentore,
            ''::text AS indirizzo_detentore,
            true AS import_opu,
            o.path_attivita_completo,
            o.stab_id_attivita AS tipo_attivita,
            o.id_stato,
            o.linea_codice_nazionale,
            o.linea_codice_ufficiale_esistente,
            o.id_tipo_linea_produttiva
           FROM opu_operatori_denormalizzati_view o
        UNION
         SELECT o.id_stabilimento AS riferimento_id,
            'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'apicoltura_imrese'::text AS riferimento_id_nome_tab,
            o.ragione_sociale,
            o.id_asl_stab AS asl_rif,
            o.stab_asl AS asl,
            1002 AS tipologia,
            o.codice_fiscale_impresa AS codice_fiscale,
            NULL::timestamp without time zone AS data_cancellazione_operatore,
            o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
            o.partita_iva,
            ''::character varying AS descrizione,
            ''::text AS nome_correntista,
            'APICOLTURA'::text AS tipologia_operatore,
            o.categoria_rischio,
            ''::character varying AS num_aut,
            o.numero_registrazione AS n_reg,
            (o.nome_rapp_sede_legale || ' '::text) || o.cognome_rapp_sede_legale AS nominativo_rappresentante,
            ''::text AS attivita,
            ''::text AS stato_impresa,
            o.stato,
            o.comune_stab AS comune,
            o.prov_stab AS provincia,
            o.indirizzo_stab AS indirizzo,
            ''::text AS indirizzo_proprietario,
            ''::text AS cf_detentore,
            ''::text AS nome_detentore,
            ''::text AS indirizzo_detentore,
            true AS import_opu,
            o.path_attivita_completo,
            o.stab_id_attivita AS tipo_attivita,
            o.id_stato,
            o.linea_codice_nazionale,
            o.linea_codice_ufficiale_esistente,
            o.id_tipo_linea_produttiva
           FROM apicoltura_operatori_denormalizzati_view o) l;

ALTER TABLE public.global_opu_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_opu_view TO postgres;
GRANT SELECT ON TABLE public.global_opu_view TO report;

----------------------------------------- Recupero oggetti dipendenti da OPU-------------------------------

-- View: public.opu_stabilimenti_linee_senza_numero_registrazione
-- DROP VIEW public.opu_stabilimenti_linee_senza_numero_registrazione;

CREATE OR REPLACE VIEW public.opu_stabilimenti_linee_senza_numero_registrazione AS 
 SELECT st.id_stabilimento,
    st.id_opu_operatore,
    st.id_linea_attivita,
    st.numero_registrazione,
    st.stab_id_attivita,
    st.partita_iva,
    st.comune_richiesta,
    ((((('update opu_relazione_stabilimento_linee_produttive set numero_registrazione=( select '''::text || st.numero_registrazione) || ''' || lpad(''''||dbi_opu_get_progressivo_linea_per_stabilimento('''::text) || st.numero_registrazione) || '''),3,''0'')) where id = '::text) || st.id_linea_attivita) || ';'::text
   FROM opu_operatori_denormalizzati_view st
     LEFT JOIN comuni1 ON comuni1.nome::text = st.comune_richiesta::text
  WHERE st.linea_numero_registrazione ~~* '%NULL%'::text OR st.linea_numero_registrazione IS NULL;

ALTER TABLE public.opu_stabilimenti_linee_senza_numero_registrazione
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_stabilimenti_linee_senza_numero_registrazione TO postgres;
GRANT SELECT ON TABLE public.opu_stabilimenti_linee_senza_numero_registrazione TO report;

-- View: public.opu_stabilimenti_senza_numero_registrazione

-- DROP VIEW public.opu_stabilimenti_senza_numero_registrazione;

CREATE OR REPLACE VIEW public.opu_stabilimenti_senza_numero_registrazione AS 
 SELECT st.id_stabilimento,
    st.id_opu_operatore,
    st.id_linea_attivita,
    ((((('update opu_stabilimento set numero_registrazione =(select '''::text || comuni1.codice_nuovo) || ''' || lpad(public.dbi_opu_get_progressivo_per_comune('::text) || comuni1.id) || ')||'''',6,''0'')) where id='::text) || st.id_stabilimento) || ';'::text,
    st.numero_registrazione,
    st.stab_id_attivita,
    st.partita_iva,
    st.comune_richiesta
   FROM opu_operatori_denormalizzati_view st
     LEFT JOIN comuni1 ON comuni1.nome::text = st.comune_richiesta::text
  WHERE st.numero_registrazione ~~* '%null%'::text OR st.numero_registrazione IS NULL;

ALTER TABLE public.opu_stabilimenti_senza_numero_registrazione
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_stabilimenti_senza_numero_registrazione TO postgres;
GRANT SELECT ON TABLE public.opu_stabilimenti_senza_numero_registrazione TO report;

-- View: public.lista_opu_operatori_canili

-- DROP VIEW public.lista_opu_operatori_canili;

CREATE OR REPLACE VIEW public.lista_opu_operatori_canili AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
    opu_operatori_denormalizzati_view.pregresso_o_import,
    opu_operatori_denormalizzati_view.riferimento_org_id,
    opu_operatori_denormalizzati_view.id_controllo_ultima_categorizzazione
   FROM opu_operatori_denormalizzati_view
  WHERE opu_operatori_denormalizzati_view.codice_attivita ~~* 'IUV-CAN-CAN'::text;

ALTER TABLE public.lista_opu_operatori_canili
  OWNER TO postgres;

-- View: public.lista_opu_operatori_commerciali

-- DROP VIEW public.lista_opu_operatori_commerciali;

CREATE OR REPLACE VIEW public.lista_opu_operatori_commerciali AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
    opu_operatori_denormalizzati_view.pregresso_o_import,
    opu_operatori_denormalizzati_view.riferimento_org_id,
    opu_operatori_denormalizzati_view.id_controllo_ultima_categorizzazione
   FROM opu_operatori_denormalizzati_view
  WHERE opu_operatori_denormalizzati_view.codice_attivita = ANY (ARRAY['IUV-CODAC-VED'::text, 'IUV-CODAC-VEDCG'::text, 'IUV-COIAC-VEI'::text]);

ALTER TABLE public.lista_opu_operatori_commerciali
  OWNER TO postgres;

-- View: public.opu_operatori_denormalizzati_view_bdu

-- DROP VIEW public.opu_operatori_denormalizzati_view_bdu;

CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view_bdu AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
        CASE
            WHEN opu_operatori_denormalizzati_view.codice_attivita = ANY (ARRAY['IUV-CAN-CAN'::text, 'VET-AMBV-PU'::text, 'VET-CLIV-PU'::text, 'VET-OSPV-PU'::text]) THEN 5
            WHEN opu_operatori_denormalizzati_view.codice_attivita = ANY (ARRAY['IUV-CODAC-VEDCG'::text, 'IUV-COIAC-VEICG'::text]) THEN 6
            ELSE NULL::integer
        END AS id_linea_bdu
   FROM opu_operatori_denormalizzati_view
     LEFT JOIN master_list_flag_linee_attivita ON master_list_flag_linee_attivita.codice_univoco = opu_operatori_denormalizzati_view.codice_attivita
  WHERE master_list_flag_linee_attivita.bdu = true;

ALTER TABLE public.opu_operatori_denormalizzati_view_bdu
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_operatori_denormalizzati_view_bdu TO postgres;
GRANT SELECT ON TABLE public.opu_operatori_denormalizzati_view_bdu TO report;

------------------------------------------ parte SINTESIS ------------------------------------------------

DROP VIEW public.sintesis_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.sintesis_operatori_denormalizzati_view AS 
 SELECT DISTINCT false AS flag_dia,
    o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
        CASE
            WHEN n.description ~~* '%1069%'::text THEN stab.livello_rischio
            ELSE stab.categoria_rischio
        END AS categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    n.description AS norma,
    n.codice_norma,
    stab.approval_number,
    n.code AS id_norma,
    ''::text AS cf_correntista,
    ''::text AS codice_attivita,
    true AS primario,
    latt.path_descrizione::text AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) ||
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) ||
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) ||
        CASE
            WHEN provsoggind.description IS NOT NULL THEN provsoggind.description
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale,
    stati.description AS stato,
    latt.attivita AS solo_attivita,
    lps.data_inizio AS data_inizio_attivita,
    lps.data_fine AS data_fine_attivita,
        CASE
            WHEN n.description ~~* '%1069%'::text THEN NULL::timestamp without time zone
            ELSE stab.data_prossimo_controllo
        END AS data_prossimo_controllo,
    stab.stato AS id_stato,
    latt.path_descrizione::text AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    false AS flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    case 
     when ml.fisso then 1
     when ml.mobile then 2
    else -1 end as stab_id_attivita,
    case 
     when ml.fisso then 'Con Sede Fissa'::text
     when ml.mobile then 'Senza Sede Fissa'::text
    else 'N.D.'::text
    end as stab_descrizione_attivita,
   -- stab.tipo_attivita AS stab_id_attivita
   -- lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
    provsedeop.cod_provincia AS sigla_prov_legale,
    provsoggind.cod_provincia AS sigla_prov_soggfisico,
    comunisoggind.nome AS comune_residenza,
    soggsl.data_nascita AS data_nascita_rapp_sede_legale,
    lotis.code AS impresa_id_tipo_societa,
    soggsl.comune_nascita AS comune_nascita_rapp_sede_legale,
    soggsl.sesso,
    soggind.civico,
    topsoggind.description AS toponimo_residenza,
    soggind.toponimo AS id_toponimo_residenza,
    sedeop.civico AS civico_sede_legale,
    sedeop.toponimo AS tiponimo_sede_legale,
    topsedeop.description AS toponimo_sede_legale,
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    '-1'::integer AS id_lookup_tipo_linee_mobili,
    '-1'::integer AS id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    stab.id_controllo_ultima_categorizzazione,
    stab.alt_id,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    "substring"(latt.codice_attivita, length(latt.codice_macroarea) + length(latt.codice_aggregazione) + 3, length(latt.codice_attivita)) AS codice_attivita_only
   FROM sintesis_operatore o
     LEFT JOIN sintesis_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     LEFT JOIN sintesis_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN sintesis_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN sintesis_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN sintesis_stabilimento stab ON stab.id_operatore = o.id
     JOIN sintesis_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_stabilimento_sintesis stati ON stati.code = stab.stato
     JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN master_list_flag_linee_attivita ml on ml.codice_univoco = latt.codice
     JOIN sintesis_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN sintesis_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code AND topsedeop.enabled
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code AND topsedestab.enabled
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code AND topsoggind.enabled
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_attivita_sintesis statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = 1
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = 1
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
     LEFT JOIN opu_lookup_norme_master_list n ON n.code = latt.id_norma AND n.enabled
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.sintesis_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.sintesis_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.sintesis_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE public.sintesis_operatori_denormalizzati_view TO report;

---------------------- ricreo ricerca_anagrafiche (controlla se ultima versione)---------------------------

CREATE OR REPLACE VIEW public.ricerca_anagrafiche AS 
 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    '-1'::integer AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    '-1'::integer AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    true AS pregresso_o_import,
    o.org_id AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.name AS ragione_sociale,
    o.site_id AS asl_rif,
    l_1.description AS asl,
    o.codice_fiscale,
    o.codice_fiscale_rappresentante,
    o.partita_iva,
        CASE
            WHEN o.tipologia = 97 AND o.categoria_rischio IS NULL THEN 3
            WHEN o.tipologia = 97 AND o.categoria_rischio = '-1'::integer THEN 3
            ELSE o.categoria_rischio
        END AS categoria_rischio,
    o.prossimo_controllo,
        CASE
            WHEN o.tipologia = ANY (ARRAY[3, 97]) THEN concat_ws(' '::text, o.numaut, o.tipo_stab)::character varying
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS num_riconoscimento,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            ELSE ''::character varying
        END AS n_reg,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS n_linea,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS nominativo_rappresentante,
        CASE
            WHEN o.tipologia in (151,802,152,10,20,2,800,801) then 'Con Sede Fissa'
            WHEN o.tipologia = 1 AND (o.tipo_dest::text = 'Es. Commerciale'::text or length(trim(o.tipo_dest))=0)  THEN 'Con Sede Fissa'::text
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 'Senza Sede Fissa'::text
            --WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 'Distributore'::text::character varying
            ELSE 'N.D.'::text
        END AS tipo_attivita_descrizione,
        CASE
            WHEN o.tipologia in (151,802,152,10,20,2,800,801) then 1
            WHEN o.tipologia = 1 AND (o.tipo_dest::text = 'Es. Commerciale'::text or length(trim(o.tipo_dest))=0)  THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 2
            ELSE -1
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 152 AND o.stato ~~* '%attivo%'::text THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
            WHEN o.tipologia = 152 AND (o.stato ~~* '%sospeso%'::text OR o.stato ~~* '%cessato%'::text OR o.stato ~~* '%sospeso%'::text) THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date1
            ELSE NULL::timestamp without time zone
        END AS data_fine_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 'Sospeso'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 'Cessato'::text
                ELSE 'Attivo'::text
            END
            WHEN o.tipologia = 20 AND o.data_chiusura_canile IS NOT NULL THEN 'Cessato'::text
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Attivo'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Cessato'::text
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.description::text
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            ELSE 'N.D'::text
        END AS stato,
        CASE
            WHEN o.tipologia = 20 AND o.data_chiusura_canile IS NOT NULL THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 0
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 0
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 0
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 4
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 2
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN '-1'::integer
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 4
                ELSE 0
            END
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 0
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 1
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 2
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 3
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 4
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.code
            ELSE 0
        END AS id_stato,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(c.nome, 'N.D'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.city, 'N.D.'::character varying)
            ELSE COALESCE(oa5.city, oa7.city, oa1.city, 'N.D.'::character varying)
        END AS comune,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.prov_sede_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.state, 'N.D.'::character varying)
            WHEN o.site_id = 201 AND o.tipologia <> 2 THEN 'AV'::text::character varying
            WHEN o.site_id = 202 AND o.tipologia <> 2 THEN 'BN'::text::character varying
            WHEN o.site_id = 203 AND o.tipologia <> 2 THEN 'CE'::text::character varying
            WHEN o.tipologia <> 2 AND (o.site_id = 204 OR o.site_id = 205 OR o.site_id = 206) THEN 'NA'::text::character varying
            WHEN o.site_id = 207 AND o.tipologia <> 2 THEN 'SA'::text::character varying
            ELSE COALESCE(oa5.state, oa7.state, oa1.state, 'N.D.'::character varying)
        END AS provincia_stab,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.indrizzo_azienda, ''::text)::character varying
            ELSE COALESCE(oa5.addrline1, oa7.addrline1, oa1.addrline1, 'N.D.'::character varying)
        END AS indirizzo,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.cap_azienda, ''::text)::character varying
            ELSE COALESCE(oa5.postalcode, oa7.postalcode, oa1.addrline1, 'N.D.'::character varying)
        END AS cap_stab,
    COALESCE(oa5.latitude, oa7.latitude, oa1.latitude) AS latitudine_stab,
    COALESCE(oa5.longitude, oa7.longitude, oa1.longitude) AS longitudine_stab,
    COALESCE(oa1.city, oa5.city, 'N.D.'::character varying) AS comune_leg,
    COALESCE(oa1.state, oa5.state, 'N.D.'::character varying) AS provincia_leg,
    COALESCE(oa1.addrline1, oa5.addrline1, 'N.D.'::character varying) AS indirizzo_leg,
    COALESCE(oa1.postalcode, oa5.postalcode, 'N.D.'::character varying) AS cap_leg,
    COALESCE(oa1.latitude, oa5.latitude) AS latitudine_leg,
    COALESCE(oa1.longitude, oa5.longitude) AS longitudine_leg,
    COALESCE(mltemp2.macroarea, mltemp.macroarea, tsa.macroarea) AS macroarea,
    COALESCE(mltemp2.aggregazione, mltemp.aggregazione, tsa.aggregazione) AS aggregazione,
    concat_ws('->'::text, COALESCE(mltemp2.macroarea, mltemp.macroarea, tsa.macroarea), COALESCE(mltemp2.aggregazione, mltemp.aggregazione, tsa.aggregazione), COALESCE(mltemp2.attivita, mltemp.attivita, tsa.attivita)) AS attivita,
        CASE
            WHEN o.tipologia = 1 THEN concat(COALESCE(lcd.description, ''::character varying), '->', COALESCE(mltemp2.macroarea, mltemp.macroarea, tsa.macroarea), '->', COALESCE(mltemp2.aggregazione, mltemp.aggregazione, tsa.aggregazione), '->', COALESCE(mltemp2.attivita, mltemp.attivita, tsa.attivita))::character varying::text
            WHEN mltemp.macroarea IS NOT NULL THEN mltemp.path_descrizione::text
            ELSE ''::text
        END AS path_attivita_completo,
        CASE
            WHEN mltemp.macroarea IS NOT NULL THEN NULL::text
            ELSE 'Non previsto'::text
        END AS gestione_masterlist,
        CASE
            WHEN o.tipologia = 20 THEN concat(nm.description, ' (ex ', lto.description, ')')
            ELSE nm.description
        END AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
    o.nome_correntista AS targa,
        CASE
            WHEN o.trashed_date IS NULL THEN 0
            ELSE 3
        END AS tipo_ricerca_anagrafica,
    'red'::text AS color,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number::text
            ELSE NULL::text
        END AS n_reg_old,
    tsa.id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    COALESCE(tsa.id, lai.id, el.id, o.org_id) AS id_linea,
    NULL::text AS matricola,
    COALESCE(mltemp.codice_macroarea, ml8.codice_macroarea) AS codice_macroarea,
    COALESCE(mltemp.codice_aggregazione, ml8.codice_aggregazione) AS codice_aggregazione,
    COALESCE("substring"(mltemp.codice_attivita, length(mltemp.codice_macroarea) + length(mltemp.codice_aggregazione) + 3, length(mltemp.codice_attivita)), "substring"(ml8.codice_attivita, length(ml8.codice_macroarea) + length(ml8.codice_aggregazione) + 3, length(ml8.codice_attivita))) AS codice_attivita
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia AND COALESCE(norme.tipo_molluschi_bivalvi, '-1'::integer) = COALESCE(o.tipologia_acque, '-1'::integer)
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN ml8_linee_attivita_nuove_materializzata ml8 ON ml8.codice = norme.codice_univoco_ml
     LEFT JOIN elenco_attivita_osm_reg el ON el.org_id = o.org_id
     LEFT JOIN lookup_attivita_osm_reg reg ON reg.code = el.id_attivita
     LEFT JOIN linee_attivita_ml8_temp l ON l.org_id = o.org_id AND (l.tipo_attivita_osm IS NULL OR l.tipo_attivita_osm = reg.code)
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp2 ON mltemp2.id_nuova_linea_attivita = lai.id_attivita_masterlist
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia tsa ON tsa.org_id = o.org_id
     LEFT JOIN lookup_stati_stabilimenti lss ON lss.code = o.stato_istruttoria
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5 AND oa5.trasheddate IS NULL
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1 AND oa1.trasheddate IS NULL
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7 AND oa7.trasheddate IS NULL
     LEFT JOIN la_rel_ateco_attivita rat ON lai.id_rel_ateco_attivita = rat.id
     LEFT JOIN lookup_codistat lcd ON rat.id_lookup_codistat = lcd.code
  WHERE o.org_id <> 0 and o.org_id <> 10000000 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND ((o.tipologia = ANY (ARRAY[1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = false)
UNION
 SELECT global_org_view.riferimento_id,
    global_org_view.riferimento_id_nome,
    global_org_view.riferimento_id_nome_col,
    global_org_view.riferimento_id_nome_tab,
    global_org_view.id_indirizzo_impresa,
    global_org_view.id_sede_operativa,
    global_org_view.sede_mobile_o_altro,
    global_org_view.riferimento_nome_tab_indirizzi,
    global_org_view.id_impresa,
    global_org_view.riferimento_nome_tab_impresa,
    global_org_view.id_soggetto_fisico,
    global_org_view.riferimento_nome_tab_soggetto_fisico,
    global_org_view.id_attivita,
    true AS pregresso_o_import,
    global_org_view.riferimento_id AS riferimento_org_id,
    global_org_view.data_inserimento,
    global_org_view.ragione_sociale,
    global_org_view.asl_rif,
    global_org_view.asl,
    global_org_view.codice_fiscale,
    global_org_view.codice_fiscale_rappresentante,
    global_org_view.partita_iva,
    global_org_view.categoria_rischio,
    global_org_view.prossimo_controllo,
    global_org_view.num_riconoscimento,
    global_org_view.n_reg,
    global_org_view.n_linea,
    global_org_view.nominativo_rappresentante,
    global_org_view.tipo_attivita_descrizione,
    global_org_view.tipo_attivita,
    global_org_view.data_inizio_attivita,
    global_org_view.data_fine_attivita,
    global_org_view.stato,
    global_org_view.id_stato,
    global_org_view.comune,
    global_org_view.provincia_stab,
    global_org_view.indirizzo,
    global_org_view.cap_stab,
    global_org_view.latitudine_stab,
    global_org_view.longitudine_stab,
    global_org_view.comune_leg,
    global_org_view.provincia_leg,
    global_org_view.indirizzo_leg,
    global_org_view.cap_leg,
    global_org_view.latitudine_leg,
    global_org_view.longitudine_leg,
    global_org_view.macroarea,
    global_org_view.aggregazione,
    global_org_view.attivita,
    global_org_view.path_attivita_completo,
    global_org_view.gestione_masterlist,
    global_org_view.norma,
    global_org_view.id_norma,
    global_org_view.tipologia_operatore,
    global_org_view.targa,
    global_org_view.tipo_ricerca_anagrafica,
    global_org_view.color,
    global_org_view.n_reg_old,
    global_org_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_org_view.id_controllo_ultima_categorizzazione,
    global_org_view.id_linea,
    NULL::text AS matricola,
    global_org_view.codice_macroarea,
    global_org_view.codice_aggregazione,
    global_org_view.codice_attivita
   FROM global_org_view
UNION
 SELECT global_ric_view.riferimento_id,
    global_ric_view.riferimento_id_nome,
    global_ric_view.riferimento_id_nome_col,
    global_ric_view.riferimento_id_nome_tab,
    global_ric_view.id_indirizzo_impresa,
    global_ric_view.id_sede_operativa,
    global_ric_view.sede_mobile_o_altro,
    global_ric_view.riferimento_nome_tab_indirizzi,
    global_ric_view.id_impresa,
    global_ric_view.riferimento_nome_tab_impresa,
    global_ric_view.id_soggetto_fisico,
    global_ric_view.riferimento_nome_tab_soggetto_fisico,
    global_ric_view.id_attivita,
    true AS pregresso_o_import,
    global_ric_view.riferimento_id AS riferimento_org_id,
    global_ric_view.data_inserimento,
    global_ric_view.ragione_sociale,
    global_ric_view.asl_rif,
    global_ric_view.asl,
    global_ric_view.codice_fiscale,
    global_ric_view.codice_fiscale_rappresentante,
    global_ric_view.partita_iva,
    global_ric_view.categoria_rischio,
    NULL::timestamp without time zone AS prossimo_controllo,
    global_ric_view.num_riconoscimento,
    global_ric_view.n_reg,
    global_ric_view.n_linea,
    global_ric_view.nominativo_rappresentante,
    global_ric_view.tipo_attivita_descrizione,
    global_ric_view.tipo_attivita,
    global_ric_view.data_inizio_attivita,
    global_ric_view.data_fine_attivita,
    global_ric_view.stato,
    global_ric_view.id_stato,
    global_ric_view.comune,
    global_ric_view.provincia_stab,
    global_ric_view.indirizzo,
    global_ric_view.cap_stab,
    global_ric_view.latitudine_stab,
    global_ric_view.longitudine_stab,
    global_ric_view.comune_leg,
    global_ric_view.provincia_leg,
    global_ric_view.indirizzo_leg,
    global_ric_view.cap_leg,
    global_ric_view.latitudine_leg,
    global_ric_view.longitudine_leg,
    global_ric_view.macroarea,
    global_ric_view.aggregazione,
    global_ric_view.attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    global_ric_view.norma,
    global_ric_view.id_norma,
    global_ric_view.tipologia_operatore,
    global_ric_view.targa,
    global_ric_view.tipo_ricerca_anagrafica,
    global_ric_view.color,
    global_ric_view.n_reg_old,
    global_ric_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_ric_view.id_controllo_ultima_categorizzazione,
    global_ric_view.id_linea_attivita AS id_linea,
    NULL::text AS matricola,
    global_ric_view.codice_macroarea,
    global_ric_view.codice_aggregazione,
    global_ric_view.codice_attivita
   FROM global_ric_view
  WHERE global_ric_view.id_stato = ANY (ARRAY[0, 7, 2])
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'opu_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'opu_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    o.pregresso_o_import,
    o.riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
        CASE
            WHEN o.linea_codice_ufficiale_esistente ~~* 'U150%'::text THEN o.linea_codice_nazionale
            ELSE COALESCE(o.linea_codice_nazionale, o.linea_codice_ufficiale_esistente)
        END AS num_riconoscimento,
    o.numero_registrazione AS n_reg,
        CASE
            WHEN norme.codice_norma = '852-04'::text THEN COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_numero_registrazione, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text))
            ELSE COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text), NULLIF(o.linea_numero_registrazione, ''::text))
        END AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
        CASE
            WHEN o.stab_id_attivita = 1 THEN o.comune_stab
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
            ELSE NULL::character varying
        END AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
        CASE
            WHEN o.flag_nuova_gestione IS NULL OR o.flag_nuova_gestione = false THEN 'LINEA NON AGGIORNATA SECONDO MASTER LIST.'::text
            ELSE NULL::text
        END AS gestione_masterlist,
    norme.description AS norma,
    o.id_norma,
    999 AS tipologia_operatore,
    m.targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita AS id_linea,
    d.matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita_only AS codice_attivita
   FROM opu_operatori_denormalizzati_view o
     LEFT JOIN opu_stabilimento_mobile m ON m.id_stabilimento = o.id_stabilimento
     LEFT JOIN opu_stabilimento_mobile_distributori d ON d.id_rel_stab_linea = o.id_linea_attivita
     LEFT JOIN opu_lookup_norme_master_list norme ON o.id_norma = norme.code
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'apicoltura_imprese'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_apicoltura_imprese AS id_impresa,
    'apicoltura_imprese'::text AS riferimento_nome_tab_impresa,
    COALESCE(o.id_soggetto_fisico, o.id_detentore) AS id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_apicoltore AS asl_rif,
    o.asl_apicoltore AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    COALESCE(o.codice_azienda, ''::text) AS num_riconoscimento,
    ''::text AS n_reg,
    COALESCE(o.codice_azienda, ''::text) AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.tipo_descrizione_attivita,
    o.tipo_attivita,
    o.data_inizio_attivita::timestamp without time zone AS data_inizio_attivita,
    NULL::timestamp without time zone AS data_fine_attivita,
    o.stato_stab AS stato,
    0 AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.latitudine AS latitudine_stab,
    o.longitudine AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    COALESCE(o.attivita, ''::text) AS attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    'APICOLTURA'::text AS norma,
    17 AS id_norma,
    1000 AS tipologia_operatore,
    NULL::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    NULL::text AS n_reg_old,
    1 AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_stabilimento AS id_linea,
    NULL::text AS matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita
   FROM apicoltura_apiari_denormalizzati_view o
UNION
 SELECT DISTINCT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'sintesis_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'sintesis_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'sintesis_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    o.approval_number AS num_riconoscimento,
    ''::text AS n_reg,
    o.approval_number AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
    NULL::text AS gestione_masterlist,
    o.norma,
    o.id_norma,
    2000 AS tipologia_operatore,
    ''::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita AS id_linea,
    NULL::text AS matricola,
    o.codice_macroarea,
    o.codice_aggregazione,
    o.codice_attivita_only AS codice_attivita
   FROM sintesis_operatori_denormalizzati_view o
UNION
 SELECT DISTINCT anagrafica_operatori_view.riferimento_id,
    anagrafica_operatori_view.riferimento_id_nome,
    anagrafica_operatori_view.riferimento_id_nome_col,
    anagrafica_operatori_view.riferimento_id_nome_tab,
    anagrafica_operatori_view.id_indirizzo_impresa,
    anagrafica_operatori_view.id_sede_operativa,
    anagrafica_operatori_view.sede_mobile_o_altro,
    'indirizzi'::text AS riferimento_nome_tab_indirizzi,
    anagrafica_operatori_view.id_impresa,
    'imprese'::text AS riferimento_nome_tab_impresa,
    anagrafica_operatori_view.id_soggetto_fisico,
    'soggetti_fisici'::text AS riferimento_nome_tab_soggetto_fisico,
    anagrafica_operatori_view.id_linea AS id_attivita,
    NULL::boolean AS pregresso_o_import,
    NULL::integer AS riferimento_org_id,
    anagrafica_operatori_view.data_inserimento,
    anagrafica_operatori_view.ragione_sociale,
    anagrafica_operatori_view.asl_rif,
    anagrafica_operatori_view.asl,
    anagrafica_operatori_view.codice_fiscale,
    anagrafica_operatori_view.codice_fiscale_rappresentante,
    anagrafica_operatori_view.partita_iva,
    anagrafica_operatori_view.categoria_rischio,
    anagrafica_operatori_view.prossimo_controllo,
    anagrafica_operatori_view.num_riconoscimento,
    anagrafica_operatori_view.n_reg,
    anagrafica_operatori_view.n_linea,
    anagrafica_operatori_view.nominativo_rappresentante,
    anagrafica_operatori_view.tipo_attivita_descrizione,
    anagrafica_operatori_view.tipo_attivita,
    anagrafica_operatori_view.data_inizio_attivita,
    anagrafica_operatori_view.data_fine_attivita,
    anagrafica_operatori_view.stato,
    anagrafica_operatori_view.id_stato,
    anagrafica_operatori_view.comune,
    anagrafica_operatori_view.provincia_stab,
    anagrafica_operatori_view.indirizzo,
    anagrafica_operatori_view.cap_stab,
    anagrafica_operatori_view.latitudine_stab,
    anagrafica_operatori_view.longitudine_stab,
    anagrafica_operatori_view.comune_leg,
    anagrafica_operatori_view.provincia_leg,
    anagrafica_operatori_view.indirizzo_leg,
    anagrafica_operatori_view.cap_leg,
    anagrafica_operatori_view.latitudine_leg,
    anagrafica_operatori_view.longitudine_leg,
    anagrafica_operatori_view.macroarea,
    anagrafica_operatori_view.aggregazione,
    anagrafica_operatori_view.attivita,
    anagrafica_operatori_view.path_attivita_completo,
    anagrafica_operatori_view.gestione_masterlist,
    anagrafica_operatori_view.norma,
    anagrafica_operatori_view.id_norma,
    anagrafica_operatori_view.tipologia_operatore,
    anagrafica_operatori_view.targa,
    anagrafica_operatori_view.tipo_ricerca_anagrafica,
    anagrafica_operatori_view.color,
    anagrafica_operatori_view.n_reg_old,
    anagrafica_operatori_view.id_tipo_linea_reg_ric,
    anagrafica_operatori_view.id_controllo_ultima_categorizzazione,
    anagrafica_operatori_view.id_linea,
    anagrafica_operatori_view.matricola,
    anagrafica_operatori_view.codice_macroarea,
    anagrafica_operatori_view.codice_aggregazione,
    anagrafica_operatori_view.codice_attivita
   FROM anagrafica.anagrafica_operatori_view;

ALTER TABLE public.ricerca_anagrafiche
  OWNER TO postgres;
---------------------------------------------------- REFRESH ANAGRAFICHE --------------------------------------
delete from ricerche_anagrafiche_old_materializzata ;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche) ;
