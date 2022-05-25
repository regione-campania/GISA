insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (32,'ALLEGATO OVICAPRINI 2022', false, 0, true,124, true, 7,6,'2022-01-01','3000-12-31', 7, 'isOviCaprini');

-- Table: public.chk_bns_domande_v6

-- DROP TABLE public.chk_bns_domande_v6;

CREATE TABLE public.chk_bns_domande_v6
(
  id serial,
  level integer,
  irr_id integer,
  dettirrid integer,
  descrizione text,
  titolo text,
  quesito text,
  esito text,
  idmod integer,
  sottotitolo text,
  evidenze text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_domande_v6
  OWNER TO postgres;


CREATE TABLE public.chk_bns_domande_v6_esiti
(
  id serial,
  description text,
  short_description text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_domande_v6_esiti
  OWNER TO postgres;


CREATE TABLE public.chk_bns_risposte_v6
(
  id serial,
  id_chk_bns_mod_ist integer,
  id_domanda integer,
  risposta text,
  evidenze text,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now()
 )
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_risposte_v6
  OWNER TO postgres;
  
-- recupero i codici raggruppamento e disabilito la relazione per l'anno corrente
select * from rel_indicatore_chk_bns  where id_lookup_chk_bns =25 and codice_raggruppamento in (5489,5490,5491,5492,5493,5494) --> codice 25 è altre specie
-- configuro i nuovi piani recuperando ciò che mi serve
--A13 o; p;  q; r; s; t ---bg e bh FAULT
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_O%'
10672;5489
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_p%'
10673;5490
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_q%'
10674;5491
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_r%'
10675;5492
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_s%'
10676;5493
select id, cod_raggruppamento from dpat_indicatore_new  where anno=2022 and data_scadenza is null and alias_indicatore ilike '%A13_t'
10677;5494

update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where codice_raggruppamento in (5489,5490,5491,5492,5493,5494) and id_lookup_chk_bns=25;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10672,5489,32,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10673,5490,32,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10674,5491,32,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10675,5492,32,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10676,5493,32,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10677,5494,32,true,'2022-01-01','3000-12-31');

-- escludere a13_ha, a13_ga e A13_fa
update rel_indicatore_chk_bns set  enabled  = false where id_indicatore in(
10707,
10708,
10709)



CREATE TABLE public.chk_bns_mod_ist_v6_ovicaprini
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  num_checklist text,
  veterinario_ispettore text,
  presenza_manuale text,
  extrapiano text,
  kg_latte text,
  q_latte text,
  num_ovi_lattazione text,
  num_cap_lattazione text,
  num_ovi_asciutta text,
  num_cap_asciutta text,
  num_arieti text,
  num_becchi text,
  num_ovi_rimonta text,
  num_cap_rimonta text,
  num_agnelli_rimonta text,
  num_capretti_rimonta text,
  num_agnelli_3m text,
  num_capretti_3m text,
  appartenente_condizionalita text,
  criteri_utilizzati text,
  criteri_utilizzati_altro_descrizione text,
  esito_controllo text,
  intenzionalita text,
  evidenze text,
  evidenze_ir text,
  evidenze_tse text,
  evidenze_sv text,
  assegnate_prescrizioni text,
  prescrizioni_descrizione text,
  data_prescrizioni text,
  sanz_blocco text,
  sanz_amministrativa text,
  sanz_abbattimento text,
  sanz_sequestro text,
  sanz_altro text,
  sanz_informativa text,
  sanz_altro_desc text,
  note_controllore text,
  note_proprietario text,
  nome_proprietario text,
  data_primo_controllo text,
  nome_controllore text,
  eseguite_prescrizioni text,
  prescrizioni_eseguite_descrizione text,
  nome_proprietario_prescrizioni_eseguite text,
  data_verifica text,
  nome_controllore_prescrizioni_eseguite text,
  data_chiusura_relazione text
)
WITH (
  OIDS=FALSE
);

insert into chk_bns_domande_v6_esiti select * from chk_bns_domande_v5_esiti;



CREATE OR REPLACE VIEW public.bdn_checklist_v6_ovicaprini AS 
 SELECT chk_bns_mod_ist_v6_ovicaprini.id,
    chk_bns_mod_ist_v6_ovicaprini.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v6_ovicaprini.trashed_date,
    chk_bns_mod_ist_v6_ovicaprini.entered_by,
    chk_bns_mod_ist_v6_ovicaprini.entered,
    chk_bns_mod_ist_v6_ovicaprini.num_checklist,
    chk_bns_mod_ist_v6_ovicaprini.veterinario_ispettore,
    chk_bns_mod_ist_v6_ovicaprini.presenza_manuale,
    chk_bns_mod_ist_v6_ovicaprini.extrapiano,
    chk_bns_mod_ist_v6_ovicaprini.appartenente_condizionalita,
    chk_bns_mod_ist_v6_ovicaprini.criteri_utilizzati,
    chk_bns_mod_ist_v6_ovicaprini.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v6_ovicaprini.esito_controllo,
    chk_bns_mod_ist_v6_ovicaprini.intenzionalita,
    chk_bns_mod_ist_v6_ovicaprini.evidenze,
    chk_bns_mod_ist_v6_ovicaprini.evidenze_ir,
    chk_bns_mod_ist_v6_ovicaprini.evidenze_tse,
    chk_bns_mod_ist_v6_ovicaprini.evidenze_sv,
    chk_bns_mod_ist_v6_ovicaprini.assegnate_prescrizioni,
    chk_bns_mod_ist_v6_ovicaprini.prescrizioni_descrizione,
    chk_bns_mod_ist_v6_ovicaprini.data_prescrizioni,
    chk_bns_mod_ist_v6_ovicaprini.sanz_blocco,
    chk_bns_mod_ist_v6_ovicaprini.sanz_amministrativa,
    chk_bns_mod_ist_v6_ovicaprini.sanz_abbattimento,
    chk_bns_mod_ist_v6_ovicaprini.sanz_sequestro,
    chk_bns_mod_ist_v6_ovicaprini.sanz_altro,
    chk_bns_mod_ist_v6_ovicaprini.sanz_informativa,
    chk_bns_mod_ist_v6_ovicaprini.sanz_altro_desc,
    chk_bns_mod_ist_v6_ovicaprini.kg_latte,
    chk_bns_mod_ist_v6_ovicaprini.num_ovi_lattazione,
    chk_bns_mod_ist_v6_ovicaprini.num_cap_lattazione,
    chk_bns_mod_ist_v6_ovicaprini.num_ovi_asciutta,
    chk_bns_mod_ist_v6_ovicaprini.num_cap_asciutta,
    chk_bns_mod_ist_v6_ovicaprini.num_arieti,
    chk_bns_mod_ist_v6_ovicaprini.num_becchi,
    chk_bns_mod_ist_v6_ovicaprini.num_ovi_rimonta,
    chk_bns_mod_ist_v6_ovicaprini.num_cap_rimonta,
    chk_bns_mod_ist_v6_ovicaprini.num_agnelli_rimonta,
    chk_bns_mod_ist_v6_ovicaprini.num_capretti_rimonta,
    chk_bns_mod_ist_v6_ovicaprini.num_agnelli_3m,
    chk_bns_mod_ist_v6_ovicaprini.num_capretti_3m,
    chk_bns_mod_ist_v6_ovicaprini.q_latte,
    chk_bns_mod_ist_v6_ovicaprini.note_controllore,
    chk_bns_mod_ist_v6_ovicaprini.note_proprietario,
    chk_bns_mod_ist_v6_ovicaprini.nome_proprietario,
    chk_bns_mod_ist_v6_ovicaprini.data_primo_controllo,
    chk_bns_mod_ist_v6_ovicaprini.nome_controllore,
    chk_bns_mod_ist_v6_ovicaprini.eseguite_prescrizioni,
    chk_bns_mod_ist_v6_ovicaprini.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v6_ovicaprini.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_ovicaprini.data_verifica,
    chk_bns_mod_ist_v6_ovicaprini.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_ovicaprini.data_chiusura_relazione
   FROM chk_bns_mod_ist_v6_ovicaprini
  WHERE chk_bns_mod_ist_v6_ovicaprini.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v6_ovicaprini
  OWNER TO postgres;
  
  -- View: public.bdn_cu_chiusi_benessere_animale

-- DROP VIEW public.bdn_cu_chiusi_benessere_animale;

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_benessere_animale AS 
 SELECT DISTINCT ist.id_alleg,
    ist.id_bdn,
    specie.short_description_ba AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
    concat('R', ticket.site_id) AS codice_asl,
    upper(o.account_number::text) AS codice_azienda,
    COALESCE(btrim(o.partita_iva::text), btrim(o.codice_fiscale_rappresentante::text)) AS id_fiscale_allevamento,
        CASE
            WHEN ROW(nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci)::text ~~* '%nas_%'::text THEN '001'::text
            WHEN ROW(nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci)::text ~~* '%forestale%'::text THEN '002'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_correntista::text AS id_fiscale_detentore,
    op.tipo_produzione_ba,
        CASE
            WHEN op.codice_orientamento_ba IS NOT NULL THEN op.codice_orientamento_ba
            ELSE ''::text
        END AS codice_orientamento_ba,
    ist.data_import,
    ist.esito_import,
    ist.descrizione_errore,
    o.specie_allev,
    lcm.tipo_allegato_bdn AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lcm.codice_checklist = 'isVitelli'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text OR o.specie_allev::text ~~* 'conigli'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '11'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    ist.id AS id_chk_bns_mod_ist,
    ticket.flag_checklist,
    lcm.description AS nome_checklist,
    o.codice_fiscale_rappresentante AS id_fiscale_proprietario,
    lcm.codice_checklist
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id AND o.tipologia = 2 AND o.account_number IS NOT NULL AND o.trashed_date IS NULL
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled AND tipi.pianomonitoraggio > 0
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     JOIN rel_motivi_eventi_cu rel ON rel.cod_raggrup_ind = lp.codice_interno_univoco
     JOIN lookup_eventi_motivi_cu ev ON ev.code = rel.id_evento_cu AND ev.codice_evento::text = 'isBenessereAnimale'::text
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid AND ist.trashed_date IS NULL AND ist.bozza IS FALSE AND (ist.esito_import IS NULL OR ist.esito_import = ''::text OR ist.esito_import ~~* '%KO%'::text)
     JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg AND (lcm.codice_checklist IS NULL OR (lcm.codice_checklist = ANY (ARRAY['isBoviniBufalini'::text, 'isBroiler'::text, 'isVitelli'::text, 'isSuini'::text, 'isGallus'::text, 'isOviCaprini'::text, 'isAltreSpecie'::text])))
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND ticket.trashed_date IS NULL AND ticket.assigned_date > (now() - '2 years'::interval);

ALTER TABLE public.bdn_cu_chiusi_benessere_animale
  OWNER TO postgres;

  
