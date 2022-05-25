
insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (33,'ALLEGATO BOVINI BUFALINI 2020', false, 0, true,121, true, 6,6,'2022-01-01','3000-12-31', 6, 'isBoviniBufalini');

insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (34,'ALLEGATO SUINI 2019', false, 0, true,122, true, 20,6,'2022-01-01','3000-12-31', 20, 'isSuini');

insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (35,'ALLEGATO VITELLI 2021', false, 0, true,1211, true, 30,6,'2022-01-01','3000-12-31', 30, 'isVitelli');

insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (36,'ALLEGATO GALLINE 2021', false, 0, true,131, true, 10,6,'2022-01-01','3000-12-31', 10, 'isGallus');

-- verifica e poi lancia update lookup_chk_bns_mod set end_date='2021-12-31 00:00:00' where code=25;
update lookup_chk_bns_mod set end_date = '2021-12-31' where code in (29,24, 31,30,28);
update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where codice_raggruppamento in (5481,
5604,
5485,
5603,
5483,
5480,
5484,
5602,
5482,
5610) and id_lookup_chk_bns=29;

--query per costruzione dinamica
select 'insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values('||id||','||cod_raggruppamento||',<id_allegato>,true,''2022-01-01'',''3000-12-31'');' 
from dpat_indicatore_new  where cod_raggruppamento in(select codice_raggruppamento from rel_indicatore_chk_bns where id_lookup_chk_bns=25) and  anno=2022; 

insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10663,5480,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10664,5481,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10665,5482,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10666,5483,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10667,5484,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10668,5485,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10707,5602,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10708,5603,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10709,5604,33,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10713,5610,33,true,'2022-01-01','3000-12-31');
-- disattivare KA
select * from dpat_indicatore_new  where alias_indicatore ilike '%A13_KA%' and data_scadenza is null and anno=2022
update rel_indicatore_chk_bns set enabled =false where id_indicatore=10713 and id_lookup_chk_bns = 33
-- aggiungere bovini e bufalini bd, be non presenti in DEV.


update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where codice_raggruppamento in (5510,
5512,
5502,
5503,
5501,
5511) and id_lookup_chk_bns=31;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10684,5501,36,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10685,5502,36,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10686,5503,36,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10693,5510,36,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10694,5511,36,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10695,5512,36,true,'2022-01-01','3000-12-31');


update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where codice_raggruppamento in (5479,
5605,
5607,
5609,
5606,
5608,
5478
) and id_lookup_chk_bns=30;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10662,5479,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10705,5605,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10710,5607,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10712,5609,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10706,5606,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10711,5608,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10661,5478,35,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10713,5610,35,true,'2022-01-01','3000-12-31');
-- vitelli aggiungere BB, BC, BF non presenti in DEV


update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where codice_raggruppamento in (5475,5476,5477) and id_lookup_chk_bns=24;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10658,5475,34,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10659,5476,34,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10660,5477,34,true,'2022-01-01','3000-12-31');
-- inserire BA 

-- broiler e altre specie
insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (37,'ALLEGATO BROILER 2018', false, 0, true,1461, true, 5,3,'2022-01-01','3000-12-31', 5, 'isBroiler'); --ex 28

insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (38,'ALLEGATO ALTRE SPECIE 2018', false, 0, true,-2, true, 4,3,'2022-01-01','3000-12-31', 1, 'isAltreSpecie'); --ex 25

update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where id_lookup_chk_bns=28;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10687,5504,37,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10688,5505,37,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10689,5506,37,true,'2022-01-01','3000-12-31');

--query per costruzione dinamica
--select 'insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values('||id||','||cod_raggruppamento||',38,true,''2022-01-01'',''3000-12-31'');' 
--from dpat_indicatore_new  where cod_raggruppamento in(5507,
--5508,5509,5513,5514,....) and  anno=2022; 

update rel_indicatore_chk_bns set data_fine_relazione = '2021-12-31 00:00:00' where id_lookup_chk_bns=25;
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10669,5486,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10670,5487,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10671,5488,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10672,5489,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10673,5490,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10674,5491,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10675,5492,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10676,5493,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10677,5494,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10678,5495,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10679,5496,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10680,5497,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10681,5498,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10682,5499,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10683,5500,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10690,5507,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10691,5508,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10692,5509,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10696,5513,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10697,5514,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10698,5515,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10699,5516,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10700,5517,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10701,5518,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10702,5519,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10703,5520,38,true,'2022-01-01','3000-12-31');
insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns, enabled, data_inizio_relazione, data_fine_relazione) values(10704,5521,38,true,'2022-01-01','3000-12-31');
--specie altre specie -- aggiungere BG, BH, bi, bl, bm, bn, bo,bp,bq,br,bs,bt,bu
-- disattivare piani A13_AJ, A13_AP, A13_AS, A13_AO, A13_AN
update rel_indicatore_chk_bns set enabled = false where id_indicatore in (10692, 10698, 10701, 10697, 10696);

-------------------- creazione tabelle ---------------------
--ALLEGATO BOVINI BUFALINI 2020
--ALLEGATO SUINI 2019
--ALLEGATO VITELLI 2021
--ALLEGATO GALLINE 2021

CREATE TABLE public.chk_bns_mod_ist_v6_galline
(
  id integer NOT NULL DEFAULT nextval('chk_bns_mod_ist_v6_galline_id_seq'::regclass),
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  num_checklist text,
  veterinario_ispettore text,
  presenza_manuale text,
  extrapiano text,
  num_uova_anno text,
  ibrido_razza text,
  selezione_imballaggio text,
  selezione_imballaggio_destinazione text,
  muta text,
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
ALTER TABLE public.chk_bns_mod_ist_v6_galline
  OWNER TO postgres;



CREATE TABLE public.chk_bns_mod_ist_v6_vitelli
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  num_checklist text,
  veterinario_ispettore text,
  extrapiano text,
  num_capi text,
  num_capi_circolanti text,
  num_capi_morti text,
  mortalita text,
  num_capi_partoriti text,
  num_capi_partoriti_gemellari text,
  interparto text,
  flag_impianti_elettrici text,
  flag_dichiarazione_conformita text,
  presenza_manuale text,
  criteri_utilizzati text,
  criteri_utilizzati_altro_descrizione text,
  appartenente_condizionalita text,
  esito_controllo text,
  intenzionalita text,
  evidenze text,
  evidenze_tse text,
  evidenze_ir text,
  evidenze_sv text,
  prescrizioni_descrizione text,
  assegnate_prescrizioni text,
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
  data_chiusura_relazione text,
  note_hd text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_mod_ist_v6_vitelli
  OWNER TO postgres;


CREATE TABLE public.chk_bns_mod_ist_v6_suini
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  num_checklist text,
  veterinario_ispettore text,
  extrapiano text,
  cens_capi text,
  cens_decessi text,
  cens_scrofette text,
  cens_nascite text,
  cens_scrofe text,
  cens_verri text,
  cens_cinghiali text,
  cens_magroncelli text,
  cens_magroni text,
  cens_lattonzoli text,
  cens_grassi text,
  scrofe_morte_anno text,
  suinetti_svezzati_anno text,
  svezzamento_suini_presenti text,
  svezzamento_tutto text,
  svezzamento_suini_morti text,
  svezzamento_num_animali_ciclo text,
  svezzamento_num_cicli text,
  svezzamento_fessurato text,
  svezzamento_parzialmente_fessurato text,
  svezzamento_pieno text,
  svezzamento_parzialmente_grigliato text,
  svezzamento_lettiera text,
  ingrasso_suini_presenti text,
  ingrasso_tutto text,
  ingrasso_suini_morti text,
  ingrasso_num_animali_ciclo text,
  ingrasso_num_cicli text,
  ingrasso_fessurato text,
  ingrasso_pieno text,
  ingrasso_parzialmente_fessurato text,
  ingrasso_parzialmente_grigliato text,
  ingrasso_lettiera text,
  presenza_gruppi_animali_coda_tagliata text,
  presenza_animali_coda_tagliata text,
  presenza_animali_tipiche text,
  utilizzo_anestetici text,
  presenza_manuale text,
  criteri_utilizzati text,
  criteri_utilizzati_altro_descrizione text,
  appartenente_condizionalita text,
  esito_controllo text,
  intenzionalita text,
  evidenze text,
  evidenze_tse text,
  evidenze_ir text,
  evidenze_sv text,
  prescrizioni_descrizione text,
  assegnate_prescrizioni text,
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
ALTER TABLE public.chk_bns_mod_ist_v6_suini
  OWNER TO postgres;


CREATE TABLE public.chk_bns_mod_ist_v6_bovini_bufalini
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  num_checklist text,
  veterinario_ispettore text,
  extrapiano text,
  num_capi text,
  num_capi_6_mesi text,
  num_capi_morti text,
  num_bovine_lattazione text,
  num_bovine_asciutta text,
  prod_latte text,
  num_manze text,
  num_bovini_ingrasso text,
  tipo_stabulazione text,
  num_tori text,
  presenza_manuale text,
  criteri_utilizzati text,
  criteri_utilizzati_altro_descrizione text,
  appartenente_condizionalita text,
  esito_controllo text,
  intenzionalita text,
  evidenze text,
  evidenze_tse text,
  evidenze_ir text,
  evidenze_sv text,
  prescrizioni_descrizione text,
  assegnate_prescrizioni text,
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
  data_chiusura_relazione text,
  num_capi_adulti text,
  num_capi_adulti_morti text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_mod_ist_v6_bovini_bufalini
  OWNER TO postgres;


CREATE TABLE public.chk_bns_boxes_v6
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  indice integer,
  numero text,
  larghezza text,
  lunghezza text,
  peso text,
  travetti text,
  regolare text,
  animali text,
  categoria text,
  fessure text,
  pavimento text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.chk_bns_boxes_v6
  OWNER TO postgres;

  
CREATE OR REPLACE VIEW public.bdn_checklist_v6_bovini_bufalini AS 
 SELECT DISTINCT chk_bns_mod_ist_v6_bovini_bufalini.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v6_bovini_bufalini.num_checklist,
    chk_bns_mod_ist_v6_bovini_bufalini.veterinario_ispettore,
    chk_bns_mod_ist_v6_bovini_bufalini.extrapiano,
    chk_bns_mod_ist_v6_bovini_bufalini.num_capi,
    chk_bns_mod_ist_v6_bovini_bufalini.num_capi_6_mesi,
    chk_bns_mod_ist_v6_bovini_bufalini.num_capi_adulti,
    chk_bns_mod_ist_v6_bovini_bufalini.num_capi_adulti_morti,
    chk_bns_mod_ist_v6_bovini_bufalini.num_capi_morti,
    chk_bns_mod_ist_v6_bovini_bufalini.num_bovine_lattazione,
    chk_bns_mod_ist_v6_bovini_bufalini.num_bovine_asciutta,
    chk_bns_mod_ist_v6_bovini_bufalini.num_manze,
    chk_bns_mod_ist_v6_bovini_bufalini.num_bovini_ingrasso,
    chk_bns_mod_ist_v6_bovini_bufalini.num_tori,
    chk_bns_mod_ist_v6_bovini_bufalini.prod_latte,
    chk_bns_mod_ist_v6_bovini_bufalini.tipo_stabulazione,
    chk_bns_mod_ist_v6_bovini_bufalini.presenza_manuale,
    chk_bns_mod_ist_v6_bovini_bufalini.criteri_utilizzati,
    chk_bns_mod_ist_v6_bovini_bufalini.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v6_bovini_bufalini.appartenente_condizionalita,
    chk_bns_mod_ist_v6_bovini_bufalini.esito_controllo,
    chk_bns_mod_ist_v6_bovini_bufalini.evidenze,
    chk_bns_mod_ist_v6_bovini_bufalini.evidenze_tse,
    chk_bns_mod_ist_v6_bovini_bufalini.evidenze_ir,
    chk_bns_mod_ist_v6_bovini_bufalini.evidenze_sv,
    chk_bns_mod_ist_v6_bovini_bufalini.prescrizioni_descrizione,
    chk_bns_mod_ist_v6_bovini_bufalini.assegnate_prescrizioni,
    chk_bns_mod_ist_v6_bovini_bufalini.data_prescrizioni,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_blocco,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_amministrativa,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_abbattimento,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_sequestro,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_altro,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_informativa,
    chk_bns_mod_ist_v6_bovini_bufalini.sanz_altro_desc,
    chk_bns_mod_ist_v6_bovini_bufalini.note_controllore,
    chk_bns_mod_ist_v6_bovini_bufalini.note_proprietario,
    chk_bns_mod_ist_v6_bovini_bufalini.nome_proprietario,
    chk_bns_mod_ist_v6_bovini_bufalini.data_primo_controllo,
    chk_bns_mod_ist_v6_bovini_bufalini.nome_controllore,
    chk_bns_mod_ist_v6_bovini_bufalini.eseguite_prescrizioni,
    chk_bns_mod_ist_v6_bovini_bufalini.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v6_bovini_bufalini.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_bovini_bufalini.data_verifica,
    chk_bns_mod_ist_v6_bovini_bufalini.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_bovini_bufalini.data_chiusura_relazione,
    chk_bns_mod_ist_v6_bovini_bufalini.intenzionalita
   FROM chk_bns_mod_ist_v6_bovini_bufalini
  WHERE chk_bns_mod_ist_v6_bovini_bufalini.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v6_bovini_bufalini
  OWNER TO postgres;


CREATE OR REPLACE VIEW public.bdn_checklist_v6_galline AS 
 SELECT chk_bns_mod_ist_v6_galline.id,
    chk_bns_mod_ist_v6_galline.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v6_galline.trashed_date,
    chk_bns_mod_ist_v6_galline.entered_by,
    chk_bns_mod_ist_v6_galline.entered,
    chk_bns_mod_ist_v6_galline.num_checklist,
    chk_bns_mod_ist_v6_galline.veterinario_ispettore,
    chk_bns_mod_ist_v6_galline.presenza_manuale,
    chk_bns_mod_ist_v6_galline.extrapiano,
    chk_bns_mod_ist_v6_galline.num_uova_anno,
    chk_bns_mod_ist_v6_galline.ibrido_razza,
    chk_bns_mod_ist_v6_galline.selezione_imballaggio,
    chk_bns_mod_ist_v6_galline.selezione_imballaggio_destinazione,
    chk_bns_mod_ist_v6_galline.muta,
    chk_bns_mod_ist_v6_galline.appartenente_condizionalita,
    chk_bns_mod_ist_v6_galline.criteri_utilizzati,
    chk_bns_mod_ist_v6_galline.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v6_galline.esito_controllo,
    chk_bns_mod_ist_v6_galline.intenzionalita,
    chk_bns_mod_ist_v6_galline.evidenze,
    chk_bns_mod_ist_v6_galline.evidenze_ir,
    chk_bns_mod_ist_v6_galline.evidenze_tse,
    chk_bns_mod_ist_v6_galline.evidenze_sv,
    chk_bns_mod_ist_v6_galline.assegnate_prescrizioni,
    chk_bns_mod_ist_v6_galline.prescrizioni_descrizione,
    chk_bns_mod_ist_v6_galline.data_prescrizioni,
    chk_bns_mod_ist_v6_galline.sanz_blocco,
    chk_bns_mod_ist_v6_galline.sanz_amministrativa,
    chk_bns_mod_ist_v6_galline.sanz_abbattimento,
    chk_bns_mod_ist_v6_galline.sanz_sequestro,
    chk_bns_mod_ist_v6_galline.sanz_altro,
    chk_bns_mod_ist_v6_galline.sanz_informativa,
    chk_bns_mod_ist_v6_galline.sanz_altro_desc,
    chk_bns_mod_ist_v6_galline.note_controllore,
    chk_bns_mod_ist_v6_galline.note_proprietario,
    chk_bns_mod_ist_v6_galline.nome_proprietario,
    chk_bns_mod_ist_v6_galline.data_primo_controllo,
    chk_bns_mod_ist_v6_galline.nome_controllore,
    chk_bns_mod_ist_v6_galline.eseguite_prescrizioni,
    chk_bns_mod_ist_v6_galline.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v6_galline.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_galline.data_verifica,
    chk_bns_mod_ist_v6_galline.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_galline.data_chiusura_relazione
   FROM chk_bns_mod_ist_v6_galline
  WHERE chk_bns_mod_ist_v6_galline.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v6_galline
  OWNER TO postgres;


  
CREATE OR REPLACE VIEW public.bdn_checklist_v6_suini AS 
 SELECT DISTINCT chk_bns_mod_ist_v6_suini.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v6_suini.num_checklist,
    chk_bns_mod_ist_v6_suini.veterinario_ispettore,
    chk_bns_mod_ist_v6_suini.cens_capi,
    chk_bns_mod_ist_v6_suini.cens_decessi,
    chk_bns_mod_ist_v6_suini.cens_scrofette,
    chk_bns_mod_ist_v6_suini.cens_nascite,
    chk_bns_mod_ist_v6_suini.cens_scrofe,
    chk_bns_mod_ist_v6_suini.cens_verri,
    chk_bns_mod_ist_v6_suini.cens_cinghiali,
    chk_bns_mod_ist_v6_suini.cens_magroncelli,
    chk_bns_mod_ist_v6_suini.cens_magroni,
    chk_bns_mod_ist_v6_suini.cens_lattonzoli,
    chk_bns_mod_ist_v6_suini.cens_grassi,
    chk_bns_mod_ist_v6_suini.scrofe_morte_anno,
    chk_bns_mod_ist_v6_suini.suinetti_svezzati_anno,
    chk_bns_mod_ist_v6_suini.svezzamento_suini_presenti,
    chk_bns_mod_ist_v6_suini.svezzamento_tutto,
    chk_bns_mod_ist_v6_suini.svezzamento_suini_morti,
    chk_bns_mod_ist_v6_suini.svezzamento_num_animali_ciclo,
    chk_bns_mod_ist_v6_suini.svezzamento_num_cicli,
    chk_bns_mod_ist_v6_suini.svezzamento_fessurato,
    chk_bns_mod_ist_v6_suini.svezzamento_parzialmente_fessurato,
    chk_bns_mod_ist_v6_suini.svezzamento_pieno,
    chk_bns_mod_ist_v6_suini.svezzamento_parzialmente_grigliato,
    chk_bns_mod_ist_v6_suini.svezzamento_lettiera,
    chk_bns_mod_ist_v6_suini.ingrasso_suini_presenti,
    chk_bns_mod_ist_v6_suini.ingrasso_tutto,
    chk_bns_mod_ist_v6_suini.ingrasso_suini_morti,
    chk_bns_mod_ist_v6_suini.ingrasso_num_animali_ciclo,
    chk_bns_mod_ist_v6_suini.ingrasso_num_cicli,
    chk_bns_mod_ist_v6_suini.ingrasso_fessurato,
    chk_bns_mod_ist_v6_suini.ingrasso_pieno,
    chk_bns_mod_ist_v6_suini.ingrasso_parzialmente_fessurato,
    chk_bns_mod_ist_v6_suini.ingrasso_parzialmente_grigliato,
    chk_bns_mod_ist_v6_suini.ingrasso_lettiera,
    chk_bns_mod_ist_v6_suini.presenza_gruppi_animali_coda_tagliata,
    chk_bns_mod_ist_v6_suini.presenza_animali_coda_tagliata,
    chk_bns_mod_ist_v6_suini.presenza_animali_tipiche,
    chk_bns_mod_ist_v6_suini.utilizzo_anestetici,
    chk_bns_mod_ist_v6_suini.presenza_manuale,
    chk_bns_mod_ist_v6_suini.criteri_utilizzati,
    chk_bns_mod_ist_v6_suini.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v6_suini.appartenente_condizionalita,
    chk_bns_mod_ist_v6_suini.esito_controllo,
    chk_bns_mod_ist_v6_suini.evidenze,
    chk_bns_mod_ist_v6_suini.evidenze_tse,
    chk_bns_mod_ist_v6_suini.evidenze_ir,
    chk_bns_mod_ist_v6_suini.evidenze_sv,
    chk_bns_mod_ist_v6_suini.prescrizioni_descrizione,
    chk_bns_mod_ist_v6_suini.assegnate_prescrizioni,
    chk_bns_mod_ist_v6_suini.data_prescrizioni,
    chk_bns_mod_ist_v6_suini.sanz_blocco,
    chk_bns_mod_ist_v6_suini.sanz_amministrativa,
    chk_bns_mod_ist_v6_suini.sanz_abbattimento,
    chk_bns_mod_ist_v6_suini.sanz_sequestro,
    chk_bns_mod_ist_v6_suini.sanz_altro,
    chk_bns_mod_ist_v6_suini.sanz_informativa,
    chk_bns_mod_ist_v6_suini.sanz_altro_desc,
    chk_bns_mod_ist_v6_suini.note_controllore,
    chk_bns_mod_ist_v6_suini.note_proprietario,
    chk_bns_mod_ist_v6_suini.nome_proprietario,
    chk_bns_mod_ist_v6_suini.data_primo_controllo,
    chk_bns_mod_ist_v6_suini.nome_controllore,
    chk_bns_mod_ist_v6_suini.eseguite_prescrizioni,
    chk_bns_mod_ist_v6_suini.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v6_suini.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_suini.data_verifica,
    chk_bns_mod_ist_v6_suini.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_suini.data_chiusura_relazione,
    chk_bns_mod_ist_v6_suini.extrapiano,
    chk_bns_mod_ist_v6_suini.intenzionalita
   FROM chk_bns_mod_ist_v6_suini
  WHERE chk_bns_mod_ist_v6_suini.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v6_suini
  OWNER TO postgres;

  
CREATE OR REPLACE VIEW public.bdn_checklist_v6_vitelli AS 
 SELECT DISTINCT chk_bns_mod_ist_v6_vitelli.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v6_vitelli.num_checklist,
    chk_bns_mod_ist_v6_vitelli.veterinario_ispettore,
    chk_bns_mod_ist_v6_vitelli.extrapiano,
    chk_bns_mod_ist_v6_vitelli.num_capi,
    chk_bns_mod_ist_v6_vitelli.num_capi_circolanti,
    chk_bns_mod_ist_v6_vitelli.num_capi_morti,
    chk_bns_mod_ist_v6_vitelli.mortalita,
    chk_bns_mod_ist_v6_vitelli.num_capi_partoriti,
    chk_bns_mod_ist_v6_vitelli.num_capi_partoriti_gemellari,
    chk_bns_mod_ist_v6_vitelli.interparto,
    chk_bns_mod_ist_v6_vitelli.flag_impianti_elettrici,
    chk_bns_mod_ist_v6_vitelli.flag_dichiarazione_conformita,
    chk_bns_mod_ist_v6_vitelli.presenza_manuale,
    chk_bns_mod_ist_v6_vitelli.criteri_utilizzati,
    chk_bns_mod_ist_v6_vitelli.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v6_vitelli.appartenente_condizionalita,
    chk_bns_mod_ist_v6_vitelli.esito_controllo,
    chk_bns_mod_ist_v6_vitelli.evidenze,
    chk_bns_mod_ist_v6_vitelli.evidenze_tse,
    chk_bns_mod_ist_v6_vitelli.evidenze_ir,
    chk_bns_mod_ist_v6_vitelli.evidenze_sv,
    chk_bns_mod_ist_v6_vitelli.prescrizioni_descrizione,
    chk_bns_mod_ist_v6_vitelli.assegnate_prescrizioni,
    chk_bns_mod_ist_v6_vitelli.data_prescrizioni,
    chk_bns_mod_ist_v6_vitelli.sanz_blocco,
    chk_bns_mod_ist_v6_vitelli.sanz_amministrativa,
    chk_bns_mod_ist_v6_vitelli.sanz_abbattimento,
    chk_bns_mod_ist_v6_vitelli.sanz_sequestro,
    chk_bns_mod_ist_v6_vitelli.sanz_altro,
    chk_bns_mod_ist_v6_vitelli.sanz_informativa,
    chk_bns_mod_ist_v6_vitelli.sanz_altro_desc,
    chk_bns_mod_ist_v6_vitelli.note_controllore,
    chk_bns_mod_ist_v6_vitelli.note_proprietario,
    chk_bns_mod_ist_v6_vitelli.nome_proprietario,
    chk_bns_mod_ist_v6_vitelli.data_primo_controllo,
    chk_bns_mod_ist_v6_vitelli.nome_controllore,
    chk_bns_mod_ist_v6_vitelli.eseguite_prescrizioni,
    chk_bns_mod_ist_v6_vitelli.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v6_vitelli.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_vitelli.data_verifica,
    chk_bns_mod_ist_v6_vitelli.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v6_vitelli.data_chiusura_relazione,
    chk_bns_mod_ist_v6_vitelli.intenzionalita
   FROM chk_bns_mod_ist_v6_vitelli
  WHERE chk_bns_mod_ist_v6_vitelli.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v6_vitelli
  OWNER TO postgres;

  --chk_bns_domande_v3
CREATE TABLE public.chk_bns_domande_v3
(
  id serial,
  level integer,
  irr_id integer,
  dettirrid integer,
  requisito text,
  quesito text,
  idmod integer
)
WITH (
  OIDS=FALSE
);

--chk_bns_capannoni_v3
CREATE TABLE public.chk_bns_capannoni_v3
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  indice integer,
  numero text,
  capacita text,
  animali text,
  num_totale_box text,
  num_totale_box_attivi text,
  ispezionato text
 )
WITH (
  OIDS=FALSE
);


--chk_bns_mod_ist_v3_generica
CREATE TABLE public.chk_bns_mod_ist_v3_generica
(
  id serial,
  id_chk_bns_mod_ist integer,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  extrapiano text,
  criteri_utilizzati text,
  criteri_utilizzati_altro_descrizione text,
  appartenente_condizionalita text,
  esito_controllo text,
  evidenze text,
  evidenze_tse text,
  evidenze_ir text,
  evidenze_sv text,
  prescrizioni_descrizione text,
  assegnate_prescrizioni text,
  data_prescrizioni text,
  sanz_blocco text,
  sanz_amministrativa text,
  sanz_abbattimento text,
  sanz_sequestro text,
  sanz_altro text,
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
  data_chiusura_relazione text,
  capacita_massima_allevamento text,
  num_uova_anno text,
  selezione_imballaggio text,
  destinazione_imballaggio text,
  muta text,
  num_vitelli text,
  num_vitelli_8_sett text,
  num_vitelli_max text,
  intenzionalita text,
  num_capi_presenti text,
  num_capannoni text,
  num_capannoni_attivi text,
  consegnata_copia text,
  flag_fase_produttiva text)
WITH (
  OIDS=FALSE
);


--chk_bns_risposte_v3
CREATE TABLE public.chk_bns_risposte_v3
(
  id serial,
  id_chk_bns_mod_ist integer,
  id_domanda integer,
  risposta text,
  evidenze text,
  num_irregolarita text,
  num_provv_a text,
  num_provv_b text,
  num_provv_c text,
  trashed_date timestamp without time zone,
  entered_by integer,
  entered timestamp without time zone DEFAULT now()
 )
WITH (
  OIDS=FALSE
);


--bdn_checklist_v3 vista
CREATE OR REPLACE VIEW public.bdn_checklist_v3 AS 
 SELECT DISTINCT chk_bns_mod_ist_v3_generica.id_chk_bns_mod_ist,
    chk_bns_mod_ist_v3_generica.extrapiano,
    chk_bns_mod_ist_v3_generica.criteri_utilizzati,
    chk_bns_mod_ist_v3_generica.criteri_utilizzati_altro_descrizione,
    chk_bns_mod_ist_v3_generica.appartenente_condizionalita,
    chk_bns_mod_ist_v3_generica.esito_controllo,
    chk_bns_mod_ist_v3_generica.evidenze,
    chk_bns_mod_ist_v3_generica.evidenze_tse,
    chk_bns_mod_ist_v3_generica.evidenze_ir,
    chk_bns_mod_ist_v3_generica.evidenze_sv,
    chk_bns_mod_ist_v3_generica.prescrizioni_descrizione,
    chk_bns_mod_ist_v3_generica.assegnate_prescrizioni,
    chk_bns_mod_ist_v3_generica.data_prescrizioni,
    chk_bns_mod_ist_v3_generica.sanz_blocco,
    chk_bns_mod_ist_v3_generica.sanz_amministrativa,
    chk_bns_mod_ist_v3_generica.sanz_abbattimento,
    chk_bns_mod_ist_v3_generica.sanz_sequestro,
    chk_bns_mod_ist_v3_generica.sanz_altro,
    chk_bns_mod_ist_v3_generica.sanz_altro_desc,
    chk_bns_mod_ist_v3_generica.note_controllore,
    chk_bns_mod_ist_v3_generica.note_proprietario,
    chk_bns_mod_ist_v3_generica.nome_proprietario,
    chk_bns_mod_ist_v3_generica.data_primo_controllo,
    chk_bns_mod_ist_v3_generica.nome_controllore,
    chk_bns_mod_ist_v3_generica.eseguite_prescrizioni,
    chk_bns_mod_ist_v3_generica.prescrizioni_eseguite_descrizione,
    chk_bns_mod_ist_v3_generica.nome_proprietario_prescrizioni_eseguite,
    chk_bns_mod_ist_v3_generica.data_verifica,
    chk_bns_mod_ist_v3_generica.nome_controllore_prescrizioni_eseguite,
    chk_bns_mod_ist_v3_generica.data_chiusura_relazione,
    chk_bns_mod_ist_v3_generica.capacita_massima_allevamento,
    chk_bns_mod_ist_v3_generica.num_uova_anno,
    chk_bns_mod_ist_v3_generica.selezione_imballaggio,
    chk_bns_mod_ist_v3_generica.destinazione_imballaggio,
    chk_bns_mod_ist_v3_generica.muta,
    chk_bns_mod_ist_v3_generica.num_vitelli,
    chk_bns_mod_ist_v3_generica.num_vitelli_8_sett,
    chk_bns_mod_ist_v3_generica.num_vitelli_max,
    chk_bns_mod_ist_v3_generica.intenzionalita,
    chk_bns_mod_ist_v3_generica.num_capi_presenti,
    chk_bns_mod_ist_v3_generica.num_capannoni,
    chk_bns_mod_ist_v3_generica.num_capannoni_attivi,
    chk_bns_mod_ist_v3_generica.flag_fase_produttiva
   FROM chk_bns_mod_ist_v3_generica
  WHERE chk_bns_mod_ist_v3_generica.trashed_date IS NULL;

ALTER TABLE public.bdn_checklist_v3
  OWNER TO postgres;

  
--chk_bns_get_domande_v3 funzione

CREATE OR REPLACE FUNCTION public.chk_bns_get_domande_v3(
    IN _codicespecie integer,
    IN _numallegato integer,
    IN _versione integer,
    IN _idcontrollo integer)
  RETURNS TABLE(id integer, level integer, irr_id integer, dettirrid integer, idmod integer, requisito text, quesito text) AS
$BODY$
DECLARE
BEGIN
	FOR id, level, irr_id , dettirrid , idmod , requisito , quesito 
	in

	select distinct c.id, c.level, c.irr_id , c.dettirrid , c.idmod , c.requisito , c.quesito 

from chk_bns_domande_v3 c where c.idmod in (select code from lookup_chk_bns_mod where codice_specie = _codiceSpecie and versione = _versione and num_allegato = _numAllegato and start_date < (select assigned_date from ticket where ticketid = _idcontrollo) and end_date >(select assigned_date from ticket where ticketid = _idcontrollo) ) order by c.level asc

LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.chk_bns_get_domande_v3(integer, integer, integer, integer)
  OWNER TO postgres;

 CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v3(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN
          
  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v3_controlli_allevamenti from get_chiamata_ws_insert_ba_v3_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v3_dettaglio_checklist from get_chiamata_ws_insert_ba_v3_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
      
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v3(text, text, integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v3_capannoni(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select string_agg(concat('<capannoni>
               <clBoxId>0</clBoxId>
               <clbId>0</clbId>
               <identificativo>',numero,'</identificativo>
               <capacita>',capacita,'</capacita>
               <animaliPresenti>',animali,'</animaliPresenti>
               <numeroBox>',num_totale_box,'</numeroBox>
               <numeroBoxAttivi>',num_totale_box_attivi,'</numeroBoxAttivi>
               <flagIspezionato>',ispezionato,'</flagIspezionato>
            </capannoni>'),'
            ') into msg 
   from
  chk_bns_capannoni_v3 where trashed_date is null and id_chk_bns_mod_ist = _id;
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v3_capannoni(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v3_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN


select 
concat('<controlliallevamenti>
	  <caId>0</caId>
             <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
            ', case when bdn.codice_specie_chk_bns in ('0128','0155') then concat('<propIdFiscale>',id_fiscale_allevamento,'</propIdFiscale>
	    ') else '' end,'            
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',v3.esito_controllo,'</flagEsito>
            <flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
            <flagExtrapiano>',v3.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            ', case when bdn.codice_specie_chk_bns in ('0144','30','32','33','34','35','36','37','38','40','41','42','46','0128') then concat('<tipoProdCodice>',bdn.tipo_produzione_ba,'</tipoProdCodice>
            <tipoAllevCodice>',bdn.tipo_allevamento_codice,'</tipoAllevCodice>
            <orientamentoCodice>',bdn.codice_orientamento_ba,'</orientamentoCodice>') else '' end,
	     case when bdn.codice_specie_chk_bns in ('0144','30','32','33','34','35','36','37','38','40','41','42','46') then concat('<flagFaseProduttiva>',v3.flag_fase_produttiva,'</flagFaseProduttiva>
            ') else '' end,
            '<flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>
	    ') else '' end,'            
            <primoControllore>',v3.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>

	    ',case when v3.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',v3.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',v3.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',v3.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',v3.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzAltro>',v3.sanz_altro,'</sanzAltro>
            ',case when v3.sanz_altro <> '0' then concat('<sanzAltroDesc>',v3.sanz_altro_desc,'</sanzAltroDesc>
            ')
	    else '' end,'
            <noteControllore>',v3.note_controllore,'</noteControllore>
            <noteDetentore>',v3.note_proprietario,'</noteDetentore>
            <flagEvidenze>',v3.evidenze,'</flagEvidenze>
            ',case when v3.evidenze = 'S' then 
						concat('<evidenzaIr>',v3.evidenze_ir,'</evidenzaIr>
						<evidenzaSv>',v3.evidenze_sv,'</evidenzaSv>
						<evidenzaSa>',v3.evidenze_tse,'</evidenzaSa>'
						)
						else '' end) 
	    else '' end,'
            <flagCondizionalita>',v3.appartenente_condizionalita,'</flagCondizionalita>
             ',case when v3.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(v3.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>
             ')
	    else '' end,'    
         </controlliallevamenti>
         ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v3 v3 on v3.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v3_controlli_allevamenti(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v3_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
	<critCodice>',v3.criteri_utilizzati,'</critCodice>
	',
	case when v3.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',v3.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when v3.esito_controllo = 'N' then concat('<flagIntenzionalita>',v3.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',v3.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when v3.esito_controllo <> 'A' then concat('<numCapannoni>',v3.num_capannoni,'</numCapannoni>
	<numCapannoniAttivi>',v3.num_capannoni_attivi,'</numCapannoniAttivi>',
		case when bdn.tipo_allegato = '1' then concat('<numUovaAnno>',v3.num_uova_anno,'</numUovaAnno>
		<flagSelImbal>',v3.selezione_imballaggio,'</flagSelImbal>
		<indirizzoSelImbal>',v3.destinazione_imballaggio,'</indirizzoSelImbal>
		<mutaInAllev>',v3.muta,'</mutaInAllev>') else '' end,
		case when bdn.tipo_allegato = '3' then concat('<numVitelliMax>',v3.num_vitelli_max,'</numVitelliMax>
		<numVitelliPresenti>',v3.num_vitelli,'</numVitelliPresenti>
		<numVitelli8Sett>',v3.num_vitelli_8_sett,'</numVitelli8Sett>') else '' end
						    ) else '' end,
	case when v3.esito_controllo <> 'A' then concat( 
	case when bdn.tipo_allegato = '5' then concat('<flagCapMaxAut>','','</flagCapMaxAut>') else '' end) else '' 
	end,
	case when v3.esito_controllo = 'N' then 
		concat('<prescrizioni>',v3.prescrizioni_descrizione,'</prescrizioni>
		', 
		case when v3.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(v3.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',v3.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when v3.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(v3.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',v3.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',v3.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               ) end
		      )else '' end,
	'<parametro>2018</parametro>',
	case when v3.esito_controllo = 'N' then (select get_chiamata_ws_insert_ba_v3_requisiti from public.get_chiamata_ws_insert_ba_v3_requisiti(_id)) else '' 
	END,
	case when v3.esito_controllo = 'N' then (select get_chiamata_ws_insert_ba_v3_capannoni from public.get_chiamata_ws_insert_ba_v3_capannoni(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v3 v3 on v3.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v3_dettaglio_checklist(integer)
  OWNER TO postgres;

 
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v3_requisiti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select string_agg(concat('<requisiti>
               <cldbId>0</cldbId>
               <irrId>',dom.irr_id,'</irrId>
               <dettirrId>',dom.dettirrid,'</dettirrId>
               <flagEsitoDett>',risp.risposta,'</flagEsitoDett>
	       ',case when risp.risposta = 'N' then concat('<numIrregolarita>',risp.num_irregolarita,'</numIrregolarita>
               <numProvvCatA>',risp.num_provv_a,'</numProvvCatA>
               <numProvvCatB>',risp.num_provv_b,'</numProvvCatB>
               <numProvvCatC>',risp.num_provv_c,'</numProvvCatC>') else '' end,'               
               <osservazioni>',risp.evidenze,'</osservazioni>
               <operation>insert</operation>
            </requisiti>'),'
            ')
into msg
from
chk_bns_risposte_v3 risp  
left join chk_bns_domande_v3 dom on risp.id_domanda = dom.id
where risp.trashed_date is null and risp.id_chk_bns_mod_ist = _id;   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v3_requisiti(integer)
  OWNER TO postgres;
