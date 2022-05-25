-- CONTROLLI UFFICIALI RITA
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M16 a MG-DG-M14 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-14' where codice_linea ='MG-DG-M16' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --22
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M18 a MG-DG-M15 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-15' where codice_linea ='MG-DG-M18' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --230
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M39 a MG-DG-M18 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-18' where codice_linea ='MG-DG-M39' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --87
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M40 a MG-DG-M14 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-14' where codice_linea ='MG-DG-M40' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --3
-- CONTROLLI UFFICIALI RITA
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M16 a MG-DG-M14 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-14' where codice_linea ='MG-DG-M16' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --22
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M18 a MG-DG-M15 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-15' where codice_linea ='MG-DG-M18' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --230
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M39 a MG-DG-M18 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-18' where codice_linea ='MG-DG-M39' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --87
update linee_attivita_controlli_ufficiali set note_internal_use_only = concat_ws('***',note_internal_use_only,'Aggiornamento codice univoco da a seguito di aggiornamento da MG-DG-M40 a MG-DG-M14 da parte dell''ORSA come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21'),  
codice_linea = 'MG-DG-14' where codice_linea ='MG-DG-M40' and id_controllo_ufficiale in (select ticketid from ticket where trashed_date is null)  and trashed_date is null; --3


CREATE OR REPLACE VIEW public.master_list_8_view AS 
 SELECT master_list_macroarea.id AS id_nuova_linea_attivita,
    true AS enabled,
    master_list_macroarea.id AS id_macroarea,
    NULL::integer AS id_aggregazione,
    NULL::integer AS id_attivita,
    master_list_macroarea.codice_sezione AS codice_macroarea,
    ''::text AS codice_aggregazione,
    ''::text AS codice_attivita,
    master_list_macroarea.macroarea,
    ''::text AS aggregazione,
    ''::text AS attivita,
    master_list_macroarea.id_norma,
    master_list_macroarea.norma,
    master_list_macroarea.macroarea AS descrizione,
    1 AS livello,
    '-1'::integer AS id_padre,
    '-1'::character varying(1000) AS path_id,
    master_list_macroarea.macroarea::character varying(1000) AS path_desc,
    master_list_macroarea.codice_sezione AS codice,
    master_list_macroarea.codice_sezione::character varying(1000) AS path_codice,
    false AS consenti_valori_multipli,
    master_list_macroarea.codice_norma
   FROM master_list_macroarea
     LEFT JOIN opu_lookup_norme_master_list norme ON norme.code = master_list_macroarea.id_norma
  WHERE master_list_macroarea.trashed_date IS NULL AND master_list_macroarea.rev = 8
UNION ALL
 SELECT t.id AS id_nuova_linea_attivita,
    true AS enabled,
    t.id_macroarea,
    t.id AS id_aggregazione,
    NULL::integer AS id_attivita,
    rt.codice_sezione AS codice_macroarea,
    t.codice_attivita AS codice_aggregazione,
    ''::text AS codice_attivita,
    rt.macroarea,
    t.aggregazione,
    ''::text AS attivita,
    rt.id_norma,
    rt.norma,
    t.aggregazione AS descrizione,
    2 AS livello,
    t.id_macroarea AS id_padre,
    (((rt.id::text || ';'::text) || t.id))::character varying(1000) AS path_id,
    (((rt.macroarea || '->'::text) || t.aggregazione))::character varying(1000) AS path_desc,
    t.codice_attivita AS codice,
    (((rt.codice_sezione || '->'::text) || t.codice_attivita))::character varying(1000) AS path_codice,
    false AS consenti_valori_multipli,
    rt.codice_norma
   FROM master_list_aggregazione t
     JOIN master_list_macroarea rt ON rt.id = t.id_macroarea
     LEFT JOIN opu_lookup_norme_master_list norme2 ON norme2.code = rt.id_norma
  WHERE t.trashed_date IS NULL AND rt.trashed_date IS NULL AND rt.rev = 8 AND t.rev = 8
UNION ALL
 SELECT a.id AS id_nuova_linea_attivita,
    true AS enabled,
    rt3.id AS id_macroarea,
    rt2.id AS id_aggregazione,
    a.id AS id_attivita,
    rt3.codice_sezione AS codice_macroarea,
    rt2.codice_attivita AS codice_aggregazione,
    a.codice_prodotto_specie AS codice_attivita,
    rt3.macroarea,
    rt2.aggregazione,
    a.linea_attivita AS attivita,
    rt3.id_norma,
    norme3.codice_norma AS norma,
    a.linea_attivita AS descrizione,
    3 AS livello,
    a.id_aggregazione AS id_padre,
    ((((rt3.id::text || ';'::text) || (rt2.id::text || ';'::text)) || a.id))::character varying(1000) AS path_id,
    ((((rt3.macroarea || '->'::text) || (rt2.aggregazione || '->'::text)) || a.linea_attivita))::character varying(1000) AS path_desc,
    concat_ws('-'::text, rt3.codice_sezione, rt2.codice_attivita, a.codice_prodotto_specie) AS codice,
    ((((rt3.codice_sezione || '->'::text) || (rt2.codice_attivita || '->'::text)) || a.codice_prodotto_specie))::character varying(1000) AS path_codice,
    a.consenti_valori_multipli,
    rt3.codice_norma
   FROM master_list_linea_attivita a
     JOIN master_list_aggregazione rt2 ON rt2.id = a.id_aggregazione
     JOIN master_list_macroarea rt3 ON rt3.id = rt2.id_macroarea
     LEFT JOIN opu_lookup_norme_master_list norme3 ON norme3.code = rt3.id_norma
  WHERE a.trashed_date IS NULL AND rt2.trashed_date IS NULL AND rt3.trashed_date IS NULL AND a.rev = 8 AND rt2.rev = 8 AND rt3.rev = 8;

ALTER TABLE public.master_list_8_view
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.refresh_ml_8_materializzata(_id_linea integer DEFAULT NULL::integer)
  RETURNS integer AS
$BODY$
DECLARE
BEGIN

delete from ml8_linee_attivita_nuove_materializzata where 1=1 
-- solo le vecchie linee 
and id_nuova_linea_attivita in (select id_nuova_linea_attivita from master_list_8_view)
and (_id_linea is null or id_nuova_linea_attivita = _id_linea);

insert into ml8_linee_attivita_nuove_materializzata (
 id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, descrizione, livello, id_padre, path_id, path_descrizione, codice, path_codice,rev, consenti_valori_multipli, codice_norma
) select id_nuova_linea_attivita, enabled, id_macroarea, id_aggregazione, id_attivita, codice_macroarea, codice_aggregazione, codice_attivita, macroarea, aggregazione,
 attivita, id_norma, norma, descrizione, livello, id_padre, path_id, path_desc, codice, path_codice, 8 as rev ,consenti_valori_multipli, codice_norma 
 from master_list_8_view 
 where 1=1
 and (_id_linea is null or id_nuova_linea_attivita = _id_linea);

return 1;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.refresh_ml_8_materializzata(integer)
  OWNER TO postgres;

-- AGGIORNAMENTO LINEE MASTER LIST

 -- CONTROLLARE PRIMA TUTTI GLI ID DATO CHE I PRIMI UPDATE SCASINERANNO TUTTO
 
-- M16 -> M14
-- CODICE_ATTIVITA -> M14
-- CODICE_UNIVOCO -> MG-DG-M14

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M16'
and rev = 10;

update master_list_linea_attivita set codice_prodotto_specie = 'M14', codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M16 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40596,40597);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M16 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40596,40597);

select * from refresh_ml_materializzata(40596);
select * from refresh_ml_materializzata(40597);

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M16'
and rev = 8;

update master_list_linea_attivita set codice_prodotto_specie = 'M14', codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M16 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40236);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M16 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40236);

select * from refresh_ml_8_materializzata(40236);

-- M18 -> M15
-- CODICE_ATTIVITA -> M15
-- CODICE_UNIVOCO -> MG-DG-M15

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M18'
and rev = 10;

update master_list_linea_attivita set codice_prodotto_specie = 'M15', codice_univoco = 'MG-DG-M15', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M18 a M15 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40599,40600);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M15', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M18 a M15 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40599,40600);

select * from refresh_ml_materializzata(40599);
select * from refresh_ml_materializzata(40600);

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M18'
and rev = 8;

update master_list_linea_attivita set codice_prodotto_specie = 'M15', codice_univoco = 'MG-DG-M15', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M18 a M15 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40238);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M15', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M18 a M15 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40238);

select * from refresh_ml_8_materializzata(40238);

-- M39 -> M18
-- CODICE_ATTIVITA -> M18
-- CODICE_UNIVOCO -> MG-DG-M18

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M39'
and rev = 10;

update master_list_linea_attivita set codice_prodotto_specie = 'M18', codice_univoco = 'MG-DG-M18', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M39 a M18 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40604);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M18', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M39 a M18 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40604);

select * from refresh_ml_materializzata(40604);

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M39'
and rev = 8;

update master_list_linea_attivita set codice_prodotto_specie = 'M18', codice_univoco = 'MG-DG-M18', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M39 a M18 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40241);

update master_list_flag_linee_attivita set codice_univoco = 'MG-DG-M18', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M39 a M18 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40241);

select * from refresh_ml_8_materializzata(40241);

-- M40 -> M14
-- CODICE_ATTIVITA -> M14
-- CODICE_UNIVOCO -> MG-DG-M14

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M40'
and rev = 10;

update master_list_linea_attivita set codice_prodotto_specie = 'M14', codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M40 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40605,40606,40607);

update master_list_flag_linee_attivita set  codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M40 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40605,40606,40607);

select * from refresh_ml_materializzata(40605);
select * from refresh_ml_materializzata(40606);
select * from refresh_ml_materializzata(40607);

select * from ml8_linee_attivita_nuove_materializzata where
codice_macroarea ='MG'
and codice_aggregazione = 'DG'
and codice_attivita = 'M40'
and rev = 8;

update master_list_linea_attivita set codice_prodotto_specie = 'M14', codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M40 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id in (40242);

update master_list_flag_linee_attivita set  codice_univoco = 'MG-DG-M14', note_hd = concat_ws(';', note_hd, 'Codice prodotto specie e univoco modificato da M40 a M14 come da mail Master list rev 10_condividere_unlimited.xlsx G. Colarusso 7/9/21') where id_linea in (40242);

select * from refresh_ml_8_materializzata(40242);

-- REFRESH ANAGRAFICHE

delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata (select * from ricerca_anagrafiche);

-- Casi di test
http://srv.gisacampania.it/gisa_nt/GestioneAnagraficaAction.do?command=Details&stabId=162257
http://srv.gisacampania.it/gisa_nt/GestioneAnagraficaAction.do?command=Details&stabId=162193

