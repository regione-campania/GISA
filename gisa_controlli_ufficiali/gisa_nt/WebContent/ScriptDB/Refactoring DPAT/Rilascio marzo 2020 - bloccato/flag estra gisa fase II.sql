--STRINGA "attivita-ispezione"
--delete? db:si, java:si, digemon;, dbi_get_disallineamento_piani_cu_configuatore_dpat
--delete? db:si, java:si, digemon;, attivita_dpat_attivita_configuratore
drop view attivita_dpat_attivita_configuratore;
drop function dbi_get_disallineamento_piani_cu_configuatore_dpat;

--VIEW public.lookup_tipo_ispezione_vista si dovrebbe cancellare, attendiamo l'analisi di Rita. Un ulteriore indizio della non usabilità è il fatto che il flag extra_gisa non era 
--gestito.

CREATE OR REPLACE VIEW public.lookup_tipo_ispezione_vista_new AS 
 SELECT lookup_tipo_ispezione_.code,
    lookup_tipo_ispezione_.description,
    lookup_tipo_ispezione_.default_item,
    lookup_tipo_ispezione_.level,
    lookup_tipo_ispezione_.enabled,
    lookup_tipo_ispezione_.codice_esame,
    lookup_tipo_ispezione_.codice_interno,
    lookup_tipo_ispezione_.anno,
    lookup_tipo_ispezione_.codice_interno_univoco,
    0 AS ordinamento,
    0 AS ordinamento_figli,
    NULL::timestamp without time zone AS data_scadenza,
    lookup_tipo_ispezione_.code AS codice_interno_ind,
    lookup_tipo_ispezione_.id_indicatore,
    ''::text AS alias,
    NULL::text AS codice,
    '-1'::integer AS id_padre
   FROM lookup_tipo_ispezione_
UNION
( SELECT DISTINCT ind.id AS code,
    ((((upper(p.descrizione) || ' '::text) || ' - '::text) ||
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore
            ELSE ''::text
        END) || ' >> '::text) || ind.descrizione AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN date_part('years'::text, now() - ((( SELECT dpat_tolleranza_inserimento_cu.intervallo
               FROM dpat_tolleranza_inserimento_cu))::interval)) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
            ELSE false
        END AS enabled,
    ind.codice_esame,
    ind.codice_interno_attivita_gestione_cu AS codice_interno,
    sez.anno,
    ind.codice_interno_univoco_tipo_attivita_gestione_cu AS codice_interno_univoco,
    p.ordinamento,
    ind.ordinamento AS ordinamento_figli,
    ind.data_scadenza,
    ind.id AS codice_interno_ind,
    ind.id AS id_indicatore,
    ind.alias_indicatore AS alias,
    (p.codice_alias_attivita || '.'::text) || ind.codice_alias_indicatore AS codice,
    p.id AS id_padre
   FROM dpat_indicatore_new ind
     JOIN dpat_piano_attivita_new p ON p.id = ind.id_piano_attivita AND p.anno = ind.anno
     JOIN dpat_sez_new sez ON sez.id = p.id_sezione AND sez.anno = p.anno
  WHERE sez.anno >= 2015 AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2])) AND ind.tipo_item_dpat = 3
  ORDER BY p.ordinamento, ind.ordinamento);

ALTER TABLE public.lookup_tipo_ispezione_vista_new
  OWNER TO postgres;





--STRINGA "piano"
--delete? db:si, java:si, digemon;, controlli_2015_piano_doppion
--delete? db:si, java:si, digemon;, piani_dpat_attivita
--delete? db:si, java:si, digemon;, piani_dpat_attivita_configuratore
--delete? db:si, java:si, digemon;, lookup_piano_monitoraggio_dpat_view
drop view controlli_2015_piano_doppion;
drop view piani_dpat_attivita;
drop view piani_dpat_attivita_configuratore;
drop view lookup_piano_monitoraggio_dpat_view;

-- Vista public.lookup_piano_monitoraggio_con_padri_per_digemon da capire cosa serve a digemon


--VIEW public.lookup_piano_monitoraggio_vista si dovrebbe cancellare, attendiamo l'analisi di Rita. Un ulteriore indizio della non usabilità è il fatto che il flag extra_gisa non era 
--gestito.


CREATE OR REPLACE VIEW public.lookup_piano_monitoraggio_vista_new AS 
 SELECT lookup_piano_monitoraggio_.code,
    lookup_piano_monitoraggio_.description,
    lookup_piano_monitoraggio_.default_item,
    lookup_piano_monitoraggio_.level,
    lookup_piano_monitoraggio_.enabled,
    lookup_piano_monitoraggio_.site_id,
    lookup_piano_monitoraggio_.id_sezione,
    lookup_piano_monitoraggio_.ordinamento,
    lookup_piano_monitoraggio_.ordinamento_figli,
    lookup_piano_monitoraggio_.codice_interno,
    lookup_piano_monitoraggio_.anno,
    lookup_piano_monitoraggio_.flag_condizionalita,
    lookup_piano_monitoraggio_.codice_interno_tipo_ispezione,
    lookup_piano_monitoraggio_.code AS codice_interno_ind,
    NULL::timestamp without time zone AS data_scadenza,
    lookup_piano_monitoraggio_.id_indicatore,
    lookup_piano_monitoraggio_.id_padre,
    lookup_piano_monitoraggio_.codice_esame,
    lookup_piano_monitoraggio_.flag_vitelli,
    lookup_piano_monitoraggio_.description AS short_description,
    ''::text AS alias,
    NULL::integer AS codice_interno_univoco,
    NULL::text AS codice,
    NULL::boolean AS flag_benessere
   FROM lookup_piano_monitoraggio_
UNION
( SELECT DISTINCT ind.id AS code,
    (
        CASE
            WHEN p.alias_piano IS NOT NULL THEN p.alias_piano || ' - '::text
            ELSE ''::text
        END || (upper(p.descrizione) || ' >> '::text)) || (
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore || ' - '::text
            ELSE ''::text
        END || ind.descrizione) AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN date_part('years'::text, now() - ((( SELECT dpat_tolleranza_inserimento_cu.intervallo
               FROM dpat_tolleranza_inserimento_cu))::interval)) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
            ELSE false
        END AS enabled,
    '-1'::integer AS site_id,
    sez.id AS id_sezione,
    p.ordinamento,
    ind.ordinamento AS ordinamento_figli,
    ind.codice_interno_piani_gestione_cu AS codice_interno,
    sez.anno,
        CASE
            WHEN ind.codice_interno_piani_gestione_cu = 1483 OR ind.codice_interno_piani_gestione_cu = 982 OR ind.codice_interno_piani_gestione_cu = 983 THEN true
            ELSE false
        END AS flag_condizionalita,
    '2a'::text AS codice_interno_tipo_ispezione,
    ind.codice_interno_indicatore AS codice_interno_ind,
    COALESCE(ind.data_scadenza, p.data_scadenza) AS data_scadenza,
    ind.codice_interno_indicatore AS id_indicatore,
    p.id AS id_padre,
    ind.codice_esame,
    false AS flag_vitelli,
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore || ':'::text
            ELSE ''::text
        END || ind.descrizione AS short_description,
    ind.alias_indicatore AS alias,
    ind.cod_raggruppamento AS codice_interno_univoco,
    (p.codice_alias_attivita || '.'::text) || ind.codice_alias_indicatore AS codice,
    ind.flag_benessere
   FROM dpat_indicatore_new ind
     JOIN dpat_piano_attivita_new p ON p.id = ind.id_piano_attivita AND p.anno = ind.anno
     JOIN dpat_sez_new sez ON sez.id = p.id_sezione AND sez.anno = p.anno
     LEFT JOIN dpat_codici_indicatore codici ON codici.codice_interno_univoco_indicatore = ind.cod_raggruppamento AND codici.data_scadenza IS NULL
  WHERE sez.anno >= 2015 AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2])) AND ind.tipo_item_dpat = 3
  ORDER BY p.ordinamento, ind.ordinamento)
  ORDER BY 11;

ALTER TABLE public.lookup_piano_monitoraggio_vista_new
  OWNER TO postgres;




--AVENDO TOLTO LA CONDIZIONE SU TIPO_ATTIVITA CI SARANNO DUPLICATI NEI MOTIVI DI ISPEZIONE PERCHE' SI FA LA UNION TRA lookup_tipo_ispezione_vista_new e lookup_piano_monitoraggio_vista_new
--PER EVITARE DUPLICATI MODIFICHIAMO LA PRIMA VISTA


drop view lookup_tipo_ispezione_vista_new;

CREATE OR REPLACE VIEW public.lookup_tipo_ispezione_vista_new AS 
 SELECT lookup_tipo_ispezione_.code,
    lookup_tipo_ispezione_.description::character varying ,
    lookup_tipo_ispezione_.default_item,
    lookup_tipo_ispezione_.level,
    lookup_tipo_ispezione_.enabled,
    lookup_tipo_ispezione_.codice_esame,
    lookup_tipo_ispezione_.codice_interno,
    lookup_tipo_ispezione_.anno,
    lookup_tipo_ispezione_.codice_interno_univoco,
    0 AS ordinamento,
    0 AS ordinamento_figli,
    NULL::timestamp without time zone AS data_scadenza,
    lookup_tipo_ispezione_.code AS codice_interno_ind,
    lookup_tipo_ispezione_.id_indicatore,
    ''::text AS alias,
    NULL::text AS codice,
    '-1'::integer AS id_padre
   FROM lookup_tipo_ispezione_ ;





