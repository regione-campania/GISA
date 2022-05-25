--1426
-- 651 cancella
select 'update organization set note_hd=concat_ws(''***'',note_hd,''Cancellazione anagrafica come concordato con ORSA in rif. Flusso 239 sulle anagrafiche di tipo FARMACIE/PARAFARMACIE/GROSSISTI senza CU''), 
trashed_date = now() where org_id='||riferimento_id||';'
from ricerche_anagrafiche_old_materializzata r
left join ticket t on t.org_id= r.riferimento_id
where r.tipologia_operatore =802 
and (t.ticketid is null or t.trashed_date is not null)

delete from ricerche_anagrafiche_old_materializzata where tipologia_operatore = 802;
insert into ricerche_anagrafiche_old_materializzata (select * from ricerca_anagrafiche where tipologia_operatore = 802);

-- mappata false va aggiustato nella view org_org_linee_attivita_view nel case when
--791 restanti
         

-- View: public.org_linee_attivita_view

-- DROP VIEW public.org_linee_attivita_view;

CREATE OR REPLACE VIEW public.org_linee_attivita_view AS 
 SELECT DISTINCT i.id,
    i.org_id,
    i.id_rel_ateco_attivita,
    i.id_attivita_masterlist,
    i.mappato,
    i.primario,
    i.entered,
    i.entered_by,
    i.modified,
    i.modified_by,
    i.trashed_date,
    opu.macroarea,
    opu.aggregazione,
    concat_ws('-'::text, cod.description, opu.attivita) AS attivita,
    opu.codice,
    la.categoria,
    la.linea_attivita,
    cod.description AS codice_istat,
    cod.short_description AS descrizione_codice_istat,
    1 AS tipo
   FROM la_imprese_linee_attivita i
     LEFT JOIN ml8_linee_attivita_nuove_materializzata opu ON opu.id_attivita = i.id_attivita_masterlist,
    la_rel_ateco_attivita rel,
    la_linee_attivita la,
    lookup_codistat cod
  WHERE i.trashed_date IS NULL AND i.id_rel_ateco_attivita = rel.id AND rel.id_linee_attivita = la.id AND rel.id_lookup_codistat = cod.code AND i.trashed_date IS NULL
UNION
 SELECT DISTINCT r.id_linea AS id,
    r.riferimento_id AS org_id,
    '-1'::integer AS id_rel_ateco_attivita,
    r.id_attivita AS id_attivita_masterlist,
        CASE
            WHEN r.tipologia_operatore <> 1 AND r.tipologia_operatore <> 2 AND (r.codice_macroarea IS NULL OR r.attivita IS NULL OR length(btrim(r.attivita)) <= 3 OR r.aggregazione IS NULL OR length(btrim(r.aggregazione)) <= 3 OR r.macroarea IS NULL OR length(btrim(r.macroarea)) <= 3) THEN false
            ELSE true
        END AS mappato,
    false AS primario,
    NULL::timestamp with time zone AS entered,
    '-1'::integer AS entered_by,
    NULL::timestamp with time zone AS modified,
    '-1'::integer AS modified_by,
    NULL::timestamp with time zone AS trashed_date,
    r.macroarea,
    r.aggregazione,
    r.attivita,
        CASE
            WHEN r.codice_aggregazione = 'N.D'::text THEN r.codice_macroarea
            ELSE concat_ws('-'::text, r.codice_macroarea, r.codice_aggregazione, r.codice_attivita)
        END AS codice,
    NULL::text AS categoria,
    NULL::text AS linea_attivita,
    NULL::text AS codice_istat,
    NULL::text AS descrizione_codice_istat,
    r.tipologia_operatore AS tipo
   FROM ricerche_anagrafiche_old_materializzata r
  WHERE r.tipologia_operatore <> 1 AND r.riferimento_id_nome_tab = 'organization'::text;

ALTER TABLE public.org_linee_attivita_view
  OWNER TO postgres;
