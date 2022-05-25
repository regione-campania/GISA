--STRUMENTO DI CALCOLO

drop view dpat_strumento_calcolo_congela_entita_per_reportistica;
drop view public.dpat_strumento_calcolo_nominativi;

alter table dpat_strumento_calcolo_nominativi_  drop column carico_lavoro_annuale;
alter table dpat_strumento_calcolo_nominativi_  drop column fattori_incidenti_su_carico;
alter table dpat_strumento_calcolo_nominativi_  drop column carico_lavoro_effettivo_annuale;
alter table dpat_strumento_calcolo_nominativi_  drop column percentuale_ui_da_sottrarre;
alter table dpat_strumento_calcolo_nominativi_  drop column uba_ui;





DROP VIEW public.dpat_programmazioni_piani;
drop function public.dbi_monitoraggio_get_programmazioni();
DROP VIEW public.dpat_programmazioni_attivita;
drop view dpat_programmazioni_saldo_ui_carico_annuo_calcolate_su_struttur;
drop view dpat_programmazioni_ui_carico_annuo_calcolate_su_strutture;
drop view obs_report_vista_controlli_ufficiali;
drop view report_vista_controlli_ufficiali;
DROP VIEW cu_inseriti_2016_da_mappare ;
DROP VIEW public.dpat_strutture_asl;
DROP VIEW public.oia_nodo;



CREATE OR REPLACE VIEW public.oia_nodo AS 
 SELECT a.id,
    a.id_padre,
    a.id_asl,
    a.descrizione_lunga,
    a.n_livello,
    a.entered,
    a.entered_by,
    a.modified,
    a.modified_by,
    a.trashed_date,
    a.tipologia_struttura,
    a.comune,
    a.enabled,
    a.obsoleto,
    a.confermato,
    a.id_strumento_calcolo,
    a.codice_interno_fk,
    a.nome,
    a.id_utente,
    a.mail,
    a.indirizzo,
    a.delegato,
    a.descrizione_comune,
    a.id_oia_nodo_temp,
    a.data_scadenza,
    a.disabilitata,
    a.stato,
    a.anno,
    a.descrizione_area_struttura_complessa,
    a.id_lookup_area_struttura_asl,
    a.ui_struttura_foglio_att_iniziale,
    a.ui_struttura_foglio_att_finale,
    a.id_utente_edit,
    a.percentuale_area_a,
    a.stato_all2,
    a.stato_all6,
    a.codice_interno_univoco,
    a.descrizione_area_struttura
   FROM ( SELECT strutture_asl.id,
            strutture_asl.id_padre,
            strutture_asl.id_asl,
            strutture_asl.descrizione_lunga,
            strutture_asl.n_livello,
            strutture_asl.entered,
            strutture_asl.entered_by,
            strutture_asl.modified,
            strutture_asl.modified_by,
            strutture_asl.trashed_date,
            strutture_asl.tipologia_struttura,
            strutture_asl.comune,
            strutture_asl.enabled,
            strutture_asl.obsoleto,
            strutture_asl.confermato,
            strutture_asl.id_strumento_calcolo,
            strutture_asl.codice_interno_fk,
            strutture_asl.nome,
            strutture_asl.id_utente,
            strutture_asl.mail,
            strutture_asl.indirizzo,
            strutture_asl.delegato,
            strutture_asl.descrizione_comune,
            strutture_asl.id_oia_nodo_temp,
            strutture_asl.data_scadenza,
            strutture_asl.stato,
            strutture_asl.anno,
                CASE
                    WHEN strutture_asl.data_scadenza > now() OR strutture_asl.data_scadenza IS NULL THEN false
                    ELSE true
                END AS disabilitata,
            strutture_asl.descrizione_area_struttura_complessa,
            strutture_asl.id_lookup_area_struttura_asl,
            strutture_asl.ui_struttura_foglio_att_iniziale,
            strutture_asl.ui_struttura_foglio_att_finale,
            strutture_asl.id_utente_edit,
            strutture_asl.percentuale_area_a,
            strutture_asl.stato_all2,
            strutture_asl.stato_all6,
            strutture_asl.codice_interno_univoco,
            area.description AS descrizione_area_struttura
           FROM strutture_asl
             LEFT JOIN lookup_area_struttura_asl area ON area.code = strutture_asl.id_lookup_area_struttura_asl) a
  ORDER BY a.codice_interno_fk, a.data_scadenza;

ALTER TABLE public.oia_nodo
  OWNER TO postgres;
ALTER TABLE public.oia_nodo ALTER COLUMN id SET DEFAULT nextval('oia_nodo_id_seq'::regclass);




CREATE OR REPLACE VIEW public.dpat_strutture_asl AS 
 WITH RECURSIVE recursetree(id, descrizione, nonno, livello, pathid, pathdes, id_asl, idsc, tipostruttura, codicefk, datascadenza, disabilitato, id_padre, anno, stato, enabled, descrizione_area_struttura_complessa, id_lookup_area_struttura_asl, ui_struttura_foglio_att_iniziale, ui_struttura_foglio_att_finale, id_utente_edit, percentuale_area_a, stato_all2, stato_all6, descrizione_area_struttura, codice_interno_univoco) AS (
         SELECT n.id,
            n.descrizione_lunga,
            n.id_padre AS nonno,
            n.n_livello,
            n.id_padre::character varying(1000) AS path_id,
            n.descrizione_lunga::character varying(1000) AS pathdes,
            n.id_asl,
            n.id_strumento_calcolo,
            n.tipologia_struttura,
            n.codice_interno_fk,
            n.data_scadenza,
            n.disabilitata,
            n.id_padre,
            n.anno,
            n.stato,
            n.enabled,
            n.descrizione_area_struttura_complessa,
            n.id_lookup_area_struttura_asl,
            n.ui_struttura_foglio_att_iniziale,
            n.ui_struttura_foglio_att_finale,
            n.id_utente_edit,
            n.percentuale_area_a,
            n.stato_all2,
            n.stato_all6,
            area.description AS descrizione_area_struttura,
            n.codice_interno_univoco
           FROM oia_nodo n
             LEFT JOIN lookup_area_struttura_asl area ON area.code = n.id_lookup_area_struttura_asl
             JOIN lookup_site_id asl ON asl.code = n.id_asl
          WHERE n.id_padre > 0 AND n.trashed_date IS NULL
        UNION ALL
         SELECT rt.id,
            rt.descrizione,
            t.id_padre AS nonno,
            rt.livello,
            (((t.id_padre || ';'::text) || rt.pathid::text))::character varying(1000) AS "varchar",
            (((t.descrizione_lunga::text || '->'::text) || rt.pathdes::text))::character varying(1000) AS pathdes,
            rt.id_asl,
            rt.idsc,
            rt.tipostruttura,
            rt.codicefk,
            rt.datascadenza,
            rt.disabilitato,
            rt.id_padre,
            rt.anno,
            rt.stato,
            rt.enabled,
            rt.descrizione_area_struttura_complessa,
            COALESCE(rt.id_lookup_area_struttura_asl, t.id_lookup_area_struttura_asl) AS id_lookup_area_struttura_asl,
            rt.ui_struttura_foglio_att_iniziale,
            rt.ui_struttura_foglio_att_finale,
            rt.id_utente_edit,
            rt.percentuale_area_a,
            rt.stato_all2,
            rt.stato_all6,
            COALESCE(rt.descrizione_area_struttura, area.description) AS descrizione_area_struttura,
            rt.codice_interno_univoco
           FROM oia_nodo t
             LEFT JOIN lookup_area_struttura_asl area ON area.code = t.id_lookup_area_struttura_asl
             JOIN recursetree rt ON rt.nonno = t.id
          WHERE t.trashed_date IS NULL
        )
 SELECT recursetree.id,
    recursetree.descrizione,
    recursetree.descrizione AS descrizione_struttura,
    recursetree.nonno,
    recursetree.livello,
    recursetree.livello AS n_livello,
    recursetree.pathid,
    recursetree.pathdes,
    recursetree.id_asl,
    recursetree.tipostruttura AS tipologia_struttura,
    recursetree.idsc AS id_strumento_calcolo,
        CASE
            WHEN recursetree.tipostruttura = 3 THEN 'S'::text
            ELSE 'V'::text
        END AS flag_sian_veterinari,
    recursetree.tipostruttura AS tipologia_struttura_flag,
    recursetree.codicefk AS codice_interno_fk,
    recursetree.datascadenza AS data_scadenza,
    recursetree.disabilitato,
    recursetree.id_padre,
    recursetree.anno,
    recursetree.stato,
    recursetree.enabled,
    padre.descrizione_lunga AS descrizione_padre,
    recursetree.descrizione_area_struttura_complessa,
    recursetree.id_lookup_area_struttura_asl,
    recursetree.ui_struttura_foglio_att_iniziale,
    recursetree.ui_struttura_foglio_att_finale,
    recursetree.id_utente_edit,
    recursetree.percentuale_area_a,
    recursetree.stato_all2,
    recursetree.stato_all6,
    recursetree.descrizione_area_struttura,
    recursetree.codice_interno_univoco
   FROM recursetree
     LEFT JOIN oia_nodo padre ON padre.codice_interno_fk = recursetree.id_padre AND padre.disabilitata = false
  WHERE recursetree.nonno = 8
  ORDER BY recursetree.id_asl;

ALTER TABLE public.dpat_strutture_asl
  OWNER TO postgres;

  





alter table strutture_asl drop column carico_lavoro_annuo ;
alter table strutture_asl drop column  percentuale_da_sottrarre ;
alter table strutture_asl drop column  fattori_incidenti_su_carico ;
alter table strutture_asl drop column  uba_ui ;
alter table strutture_asl drop column  somma_ui_area ;
alter table strutture_asl drop column  somma_uba_area ;
alter table strutture_asl drop column  carico_lavoro_effettivo ;


DROP VIEW centralizzazione_match_utenti_sdc_contact_temp;
DROP VIEW centralizzazione_no_match_role_utenti_sdc_contact_temp_2;
DROP VIEW centralizzazione_no_match_role_utenti_sdc_contact_temp;


DROP FUNCTION public_functions.dbi_get_programmazione_dpat_all(integer, integer, integer);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_programmazione_dpat_all(
    IN id_aslinput integer,
    IN annoinput integer,
    IN idareaselezionata integer)
  RETURNS TABLE(progressivo_indicatore text, livello integer, id_dpat integer, id_sezione integer, id_piano_attivita integer, ordinamento_piano integer, anno integer, 
  id_indicatore integer, sezione text, piano_attivita text, descrizione_indicatore text, id_struttura integer, descrizione_struttura text, id_asl_struttura integer, 
  asl character varying, ordinamento_indicatore integer, ordinamento_figli integer, pathstrutt text, uba_area integer) AS
$BODY$
DECLARE
r RECORD;			
BEGIN
FOR 
progressivo_indicatore,
livello,
id_Dpat,
id_sezione  ,  
    id_piano_attivita , 
    ordinamento_piano , 
    anno , 
    id_indicatore , 
    sezione,
    piano_attivita,
    descrizione_indicatore , 
    id_struttura , 
    descrizione_struttura , 
    id_asl_struttura , 
    asl,
    ordinamento_indicatore,ordinamento_figli,pathstrutt
in

SELECT DISTINCT v1.progressivo_indicatore,v1.n_livello,v1.id_dpat, v1.id_sezione, 
    v1.id_piano_attivita, 
    v1.ordinamento_piano, 
    v1.anno, 
    v1.id_indicatore+2000, 
    v1.sezione ,
    v1.piano_attivita,
    v1.indicatore, 
    v1.id_struttura, 
    v1.descrizione_struttura, 
    v1.id_asl_struttura, 
    v1.asl,
    
        
        v1.ordinamento_indicatore ,  v1.ordinamento_figli,v1.pathdescstrutt
   FROM ( 

 
   SELECT 
   strutt.n_livello,
case when strutt.descrizione_area_struttura_complessa is not null then strutt.descrizione_area_struttura_complessa|| ' - ' else '' end || upper(regexp_replace(regexp_replace(strutt.descrizione_struttura::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), 'Â°'::text, '.'::text, 'g'::text)) AS descrizione_struttura
   , substring(ind.alias_indicatore from length(ind.alias_indicatore) for length(ind.alias_indicatore)) as progressivo_indicatore,
            strutt.id_asl AS id_asl_struttura, 
            dpat.id_asl AS id_asl_dpat, 
            ls.description as asl,
            dpat.id AS id_dpat, 
            dpat.anno, 
            strutt.id AS id_struttura,
	    sez.descrizione as sezione ,
piano.descrizione as piano_attivita, 
  regexp_replace(regexp_replace( CASE
                    WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore
                    ELSE ''::text end 
                    || ind.descrizione, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), 'Â°'::text, '.'::text, 'g'::text) as indicatore,

            ind.id AS id_indicatore, 
            sez.id AS id_sezione, 
            piano.id AS id_piano_attivita, 
            ind.ordinamento as ordinamento_indicatore,piano.ordinamento as ordinamento_piano , ind.ordinamento as ordinamento_figli,strutt.pathid||';'||strutt.codice_interno_fk as pathdescstrutt
           FROM dpat_strutture_asl strutt
	JOIN dpat_strumento_calcolo sc ON sc.id =strutt.id_strumento_calcolo AND sc.anno = annoinput AND  
    case when id_aslinput > 0 then sc.id_asl = id_aslinput
    else sc.id_asl > 0
    end  and  strutt.anno = annoinput
   ,dpat_istanza ist
join dpat on ist.id = dpat.id_dpat_istanza
join dpat_sez_new sez ON dpat.anno = sez.anno and dpat.enabled
   JOIN dpat_piano_attivita_new piano ON piano.id_sezione =sez.id 
  
   left join dpat_indicatore_new ind on ind.id_piano_attivita = piano.id
   LEFT JOIN lookup_site_id ls on ls.code= dpat.id_asl
  WHERE 

  case when idareaselezionata>0  then strutt.id = idareaselezionata else 1=1 end and dpat.anno = annoinput and
  case when id_aslinput > 0 then dpat.id_asl = id_aslinput 
  else dpat.id_asl > 0 
  end and dpat.id_asl = strutt.id_asl and case when ist.stato='2' then (ind.stato=2 or ind.stato is null) else ((ind.stato =1  ) or ( ind.stato is null)) and (piano.data_scadenza is null or piano.data_scadenza<current_timestamp) 
  end

  ) v1
  
  --select * from  
 LEFT JOIN dpat_struttura_indicatore valori ON valori.id_indicatore = v1.id_indicatore AND valori.id_struttura = v1.id_struttura
   AND valori.id_dpat = v1.id_dpat AND valori.enabled
  ORDER BY v1.ordinamento_piano ,  v1.ordinamento_figli,v1.pathdescstrutt
------------- 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_programmazione_dpat_all(integer, integer, integer)
  OWNER TO postgres;








CREATE OR REPLACE VIEW public.dpat_strumento_calcolo_nominativi AS 
 SELECT DISTINCT ON (a.codice_interno_fk) a.codice_interno_fk AS codice_interno_fk_,
    a.id_anagrafica_nominativo,
    a.id_lookup_qualifica,
    a.id,
    a.id_dpat_strumento_calcolo_strutture,
    a.trashed_date,
    a.id_old_anagrafica_nominativo,
    a.confermato,
    a.data_conferma,
    a.confermato_da,
    a.data_scadenza,
    a.stato,
    a.codice_interno_fk,
    a.entered_by,
    a.entered,
    a.modified,
    a.modified_by,
        CASE
            WHEN a.descrizione IS NOT NULL THEN a.descrizione
            ELSE ''::character varying
        END::text::character varying(1000) AS descrizione,
    a.id_struttura,
    a.level,
    a.id_asl,
    a.anno,
    a.disabilitato,
    a.id_strumento_calcolo,
    a.stato_struttura
   FROM ( SELECT dpat_strumento_calcolo_nominativi_.id_anagrafica_nominativo,
            dpat_strumento_calcolo_nominativi_.id_lookup_qualifica,
            dpat_strumento_calcolo_nominativi_.id,
            dpat_strumento_calcolo_nominativi_.id_dpat_strumento_calcolo_strutture,
            dpat_strumento_calcolo_nominativi_.trashed_date,
            dpat_strumento_calcolo_nominativi_.id_old_anagrafica_nominativo,
            dpat_strumento_calcolo_nominativi_.confermato,
            dpat_strumento_calcolo_nominativi_.data_conferma,
            dpat_strumento_calcolo_nominativi_.confermato_da,
            dpat_strumento_calcolo_nominativi_.data_scadenza,
            dpat_strumento_calcolo_nominativi_.stato,
            dpat_strumento_calcolo_nominativi_.codice_interno_fk,
            dpat_strumento_calcolo_nominativi_.entered_by,
            dpat_strumento_calcolo_nominativi_.entered,
            dpat_strumento_calcolo_nominativi_.modified,
            dpat_strumento_calcolo_nominativi_.modified_by,
            scstr.pathdes AS descrizione,
            scstr.id AS id_struttura,
            lq.livello_qualifiche_dpat AS level,
            scstr.id_asl,
            scstr.anno,
            scstr.disabilitato,
            scstr.id_strumento_calcolo,
            scstr.stato AS stato_struttura
           FROM dpat_strumento_calcolo_nominativi_
             LEFT JOIN dpat_strutture_asl scstr ON scstr.codice_interno_fk = dpat_strumento_calcolo_nominativi_.id_dpat_strumento_calcolo_strutture AND scstr.disabilitato = false
             LEFT JOIN lookup_qualifiche lq ON lq.code = dpat_strumento_calcolo_nominativi_.id_lookup_qualifica
          WHERE dpat_strumento_calcolo_nominativi_.trashed_date IS NULL AND (dpat_strumento_calcolo_nominativi_.data_scadenza > (now() + '1 day'::interval) OR dpat_strumento_calcolo_nominativi_.data_scadenza IS NULL)) a
  ORDER BY a.codice_interno_fk, a.data_scadenza;

ALTER TABLE public.dpat_strumento_calcolo_nominativi
  OWNER TO postgres;




  

CREATE OR REPLACE RULE "_RETURN" AS
    ON SELECT TO dpat_strumento_calcolo_nominativi DO INSTEAD  SELECT DISTINCT ON (a.codice_interno_fk) a.codice_interno_fk AS codice_interno_fk_,
    a.id_anagrafica_nominativo,
    a.id_lookup_qualifica,
    a.id,
    a.id_dpat_strumento_calcolo_strutture,
    a.trashed_date,
    a.id_old_anagrafica_nominativo,
    a.confermato,
    a.data_conferma,
    a.confermato_da,
    a.data_scadenza,
    a.stato,
    a.codice_interno_fk,
    a.entered_by,
    a.entered,
    a.modified,
    a.modified_by,
        CASE
            WHEN a.descrizione IS NOT NULL THEN a.descrizione
            ELSE ''::character varying
        END::text::character varying(1000) AS descrizione,
    a.id_struttura,
    a.level,
    a.id_asl,
    a.anno,
    a.disabilitato,
    a.id_strumento_calcolo,
    a.stato_struttura
   FROM ( SELECT dpat_strumento_calcolo_nominativi_.id_anagrafica_nominativo,
            dpat_strumento_calcolo_nominativi_.id_lookup_qualifica,
            dpat_strumento_calcolo_nominativi_.id,
            dpat_strumento_calcolo_nominativi_.id_dpat_strumento_calcolo_strutture,
            dpat_strumento_calcolo_nominativi_.trashed_date,
            dpat_strumento_calcolo_nominativi_.id_old_anagrafica_nominativo,
            dpat_strumento_calcolo_nominativi_.confermato,
            dpat_strumento_calcolo_nominativi_.data_conferma,
            dpat_strumento_calcolo_nominativi_.confermato_da,
            dpat_strumento_calcolo_nominativi_.data_scadenza,
            dpat_strumento_calcolo_nominativi_.stato,
            dpat_strumento_calcolo_nominativi_.codice_interno_fk,
            dpat_strumento_calcolo_nominativi_.entered_by,
            dpat_strumento_calcolo_nominativi_.entered,
            dpat_strumento_calcolo_nominativi_.modified,
            dpat_strumento_calcolo_nominativi_.modified_by,
            scstr.pathdes AS descrizione,
            scstr.id AS id_struttura,
            lq.livello_qualifiche_dpat AS level,
            scstr.id_asl,
            scstr.anno,
            scstr.disabilitato,
            scstr.id_strumento_calcolo,
            scstr.stato AS stato_struttura
           FROM dpat_strumento_calcolo_nominativi_
             LEFT JOIN dpat_strutture_asl scstr ON scstr.codice_interno_fk = dpat_strumento_calcolo_nominativi_.id_dpat_strumento_calcolo_strutture AND scstr.disabilitato = false
             LEFT JOIN lookup_qualifiche lq ON lq.code = dpat_strumento_calcolo_nominativi_.id_lookup_qualifica
          WHERE dpat_strumento_calcolo_nominativi_.trashed_date IS NULL AND (dpat_strumento_calcolo_nominativi_.data_scadenza > (now() + '1 day'::interval) OR dpat_strumento_calcolo_nominativi_.data_scadenza IS NULL)) a
  ORDER BY a.codice_interno_fk, a.data_scadenza;


CREATE OR REPLACE RULE nominativi_insert AS
    ON INSERT TO dpat_strumento_calcolo_nominativi DO INSTEAD  INSERT INTO dpat_strumento_calcolo_nominativi_ (id, id_anagrafica_nominativo, id_lookup_qualifica, id_dpat_strumento_calcolo_strutture, entered_by, entered, stato, codice_interno_fk)
  VALUES (new.id, new.id_anagrafica_nominativo, new.id_lookup_qualifica, new.id_dpat_strumento_calcolo_strutture, new.entered_by, new.entered, new.stato, new.codice_interno_fk);



CREATE OR REPLACE RULE nominativi_update AS
    ON UPDATE TO dpat_strumento_calcolo_nominativi DO INSTEAD  UPDATE dpat_strumento_calcolo_nominativi_ SET trashed_date = new.trashed_date, data_scadenza = new.data_scadenza, modified = new.modified, modified_by = new.modified_by, stato = new.stato
  WHERE dpat_strumento_calcolo_nominativi_.id = new.id;

  



CREATE OR REPLACE RULE strutture_asl_insert AS
    ON INSERT TO oia_nodo DO INSTEAD  INSERT INTO strutture_asl (id, id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, trashed_date, tipologia_struttura, comune, enabled, obsoleto, confermato, id_strumento_calcolo, codice_interno_fk, nome, id_utente, mail, indirizzo, delegato, descrizione_comune, id_oia_nodo_temp, data_scadenza, stato, anno, ui_struttura_foglio_att_iniziale, ui_struttura_foglio_att_finale, id_utente_edit, percentuale_area_a, codice_interno_univoco)
  VALUES (new.id, new.id_padre, new.id_asl, new.descrizione_lunga, new.n_livello, new.entered, new.entered_by, new.modified, new.modified_by, new.trashed_date, new.tipologia_struttura, new.comune, new.enabled, new.obsoleto, new.confermato, new.id_strumento_calcolo, new.codice_interno_fk, new.nome, new.id_utente, new.mail, new.indirizzo, new.delegato, new.descrizione_comune, new.id_oia_nodo_temp, new.data_scadenza, new.stato, new.anno, new.ui_struttura_foglio_att_iniziale, new.ui_struttura_foglio_att_finale, new.id_utente_edit, new.percentuale_area_a, new.codice_interno_univoco);


CREATE OR REPLACE RULE strutture_asl_update AS
    ON UPDATE TO oia_nodo DO INSTEAD  UPDATE strutture_asl SET id_utente_edit = new.id_utente_edit, ui_struttura_foglio_att_iniziale = new.ui_struttura_foglio_att_iniziale, ui_struttura_foglio_att_finale = new.ui_struttura_foglio_att_finale, descrizione_area_struttura_complessa = new.descrizione_area_struttura_complessa, descrizione_lunga = new.descrizione_lunga, trashed_date = new.trashed_date, modified = new.modified, modified_by = new.modified_by, data_scadenza = new.data_scadenza, stato = new.stato, percentuale_area_a = new.percentuale_area_a
  WHERE strutture_asl.id = new.id;



CREATE OR REPLACE VIEW public.dpat_strumento_calcolo_congela_entita_per_reportistica AS 
 SELECT aa.anno,
    aa.id_asl,
    aa.id_strumento_calcolo,
    aa.asl,
    aa.id_padre AS id_struttura_complessa,
    aa.descrizione_padre AS descrizione_struttura_complessa,
    aa.cifk,
    aa.descrizione_struttura_,
    aa.tipo_strutt,
    n.id_anagrafica_nominativo AS nominativo_id,
    c.namelast AS nominativo_cognome,
    c.namefirst AS nominativo_nome,
    lq.code AS nominativo_qualifica_id,
    lq.description AS nominativo_qualifica
   FROM ( SELECT DISTINCT ON (a.codice_interno_fk) a.codice_interno_fk AS cifk,
            a.livello,
            a.id,
            a.descrizione_struttura_,
            a.id_padre,
            a.descrizione_padre,
            a.asl,
            a.tipologia_struttura,
            a.tipo_strutt,
            a.pathid,
            a.codice_interno_fk,
            a.id_strumento_calcolo,
            a.anno,
            a.id_asl_sdc AS id_asl
           FROM ( SELECT oia_nodo.id,
                    oia_nodo.descrizione,
                    oia_nodo.descrizione_struttura,
                    oia_nodo.nonno,
                    oia_nodo.livello,
                    oia_nodo.n_livello,
                    oia_nodo.pathid,
                    oia_nodo.pathdes,
                    oia_nodo.id_asl,
                    oia_nodo.tipologia_struttura,
                    oia_nodo.id_strumento_calcolo,
                    oia_nodo.flag_sian_veterinari,
                    oia_nodo.tipologia_struttura_flag,
                    oia_nodo.codice_interno_fk,
                    oia_nodo.data_scadenza,
                    oia_nodo.disabilitato,
                    oia_nodo.id_padre,
                    oia_nodo.anno,
                    oia_nodo.stato,
                    oia_nodo.enabled,
                    oia_nodo.descrizione_padre,
                    oia_nodo.descrizione_area_struttura_complessa,
                    oia_nodo.id_lookup_area_struttura_asl,
                    oia_nodo.ui_struttura_foglio_att_iniziale,
                    oia_nodo.ui_struttura_foglio_att_finale,
                    oia_nodo.id_utente_edit,
                    oia_nodo.percentuale_area_a,
                    oia_nodo.stato_all2,
                    oia_nodo.stato_all6,
                    oia_nodo.descrizione_area_struttura,
                    oia_nodo.codice_interno_univoco,
                    o.tipologia,
                    o.org_id,
                    asl.description AS asl,
                    tipo.description AS tipo_strutt,
                    regexp_replace(oia_nodo.descrizione_struttura::text, '[

]'::text, ' '::text, 'g'::text) AS descrizione_struttura_,
                    oia_nodo.id_asl,
                    oia_nodo.pathid::text || ';'::text,
                    tipooia.description AS descrizione_tipologia_struttura,
                    oia_nodo.descrizione_area_struttura_complessa,
                    sdc.id_asl AS id_asl_sdc
                   FROM dpat_strutture_asl oia_nodo
                     JOIN lookup_tipologia_nodo_oia tipo ON tipo.code = oia_nodo.tipologia_struttura
                     JOIN lookup_site_id asl ON asl.code = oia_nodo.id_asl
                     JOIN dpat_strumento_calcolo sdc ON sdc.id = oia_nodo.id_strumento_calcolo
                     JOIN organization o ON o.site_id = oia_nodo.id_asl AND o.tipologia = 6
                     LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_nodo.tipologia_struttura = tipooia.code
                  WHERE sdc.anno = 2016 AND oia_nodo.disabilitato = false AND oia_nodo.tipologia_struttura <> 13
                  ORDER BY ((oia_nodo.pathid::text || ';'::text) || oia_nodo.id)) a(id, descrizione, descrizione_struttura, nonno, livello, n_livello, pathid, pathdes, id_asl, tipologia_struttura, id_strumento_calcolo, flag_sian_veterinari, tipologia_struttura_flag, codice_interno_fk, data_scadenza, disabilitato, id_padre, anno, stato, enabled, descrizione_padre, descrizione_area_struttura_complessa, id_lookup_area_struttura_asl, ui_struttura_foglio_att_iniziale, ui_struttura_foglio_att_finale, id_utente_edit, percentuale_area_a, stato_all2, stato_all6, descrizione_area_struttura, codice_interno_univoco, tipologia, org_id, asl, tipo_strutt, descrizione_struttura_, id_asl_1, "?column?", descrizione_tipologia_struttura, descrizione_area_struttura_complessa_1, id_asl_sdc)
          ORDER BY a.codice_interno_fk, a.data_scadenza) aa
     JOIN dpat_strumento_calcolo_nominativi n ON n.id_dpat_strumento_calcolo_strutture = aa.cifk AND n.trashed_date IS NULL AND n.disabilitato = false
     JOIN contact c ON c.user_id = n.id_anagrafica_nominativo
     JOIN lookup_qualifiche lq ON lq.code = n.id_lookup_qualifica
  WHERE 1 = 1 AND aa.anno = 2016
  ORDER BY aa.tipologia_struttura DESC, ((aa.pathid::text || ';'::text) || aa.codice_interno_fk);

ALTER TABLE public.dpat_strumento_calcolo_congela_entita_per_reportistica
  OWNER TO postgres;






--CONFIGURATORE

DROP VIEW public.lookup_tipo_ispezione_vista_new;

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
            WHEN date_part('years'::text, now() - '00:00:00'::interval) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
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
  WHERE sez.anno >= 2015 AND p.tipo_attivita ~~* 'attivita-ispezione'::text AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2])) AND ind.extra_gisa IS NOT TRUE
  ORDER BY p.ordinamento, ind.ordinamento);

ALTER TABLE public.lookup_tipo_ispezione_vista_new
  OWNER TO postgres;



DROP VIEW public.view_motivi_linearizzati_dpat;

CREATE OR REPLACE VIEW public.view_motivi_linearizzati_dpat AS 
 SELECT sezione.anno AS anno_sezione,
    sezione.id_fisico AS id_fisico_sezione,
    sezione.descrizione AS descrizione_sezione,
    piano_attivita.id_fisico AS id_fisico_piano,
    piano_attivita.cod_raggruppamento AS codice_univoco_piano_old,
    piano_attivita.descrizione AS descrizione_piano,
    piano_attivita.ordinamento AS ordinamento_piano,
    piano_attivita.id_fisico AS id_fisico_attivita,
    piano_attivita.cod_raggruppamento AS codice_univoco_attivita_old,
    piano_attivita.descrizione AS descrizione_attivita,
    indicatore.id_fisico AS id_fisico_indicatore,
    indicatore.cod_raggruppamento AS codice_univoco_indicatore_old,
    indicatore.descrizione AS descrizione_indicatore,
    indicatore.ordinamento AS ordinamento_indicatore,
    piano_attivita.codice_esame_attivita,
    indicatore.codice_esame_indicatore,
    piano_attivita.tipo_attivita AS tipo_attivita_piano,
    piano_attivita.tipo_attivita AS tipo_attivita_indicatore,
    sezione.codice_interno AS codice_interno_sezione,
    piano_attivita.alias_piano,
    piano_attivita.codice_interno_piano,
    piano_attivita.alias_attivita,
    piano_attivita.codice_interno_attivita,
    indicatore.codice_interno_indicatore,
    indicatore.alias_indicatore,
    indicatore.codice_interno_piani_gestione_cu,
    indicatore.codice_interno_attivita_gestione_cu,
    indicatore.codice_interno_univoco_tipo_attivita_gestione_cu,
    piano_attivita.codice_alias_attivita,
    indicatore.codice_alias_indicatore,
    indicatore.cod_raggruppamento AS codice_interno_univoco,
    indicatore.disabilitato AS indicatore_disabilitato,
    piano_attivita.disabilitato AS piano_disabilitato,
    piano_attivita.disabilitato AS attivita_disabilitato,
    indicatore.codice_alias_indicatore AS codice
   FROM ( SELECT dpat_sez_new.id AS id_fisico,
            dpat_sez_new.anno,
            dpat_sez_new.descrizione,
            NULL::text AS unknown,
            NULL::text AS unknown,
            dpat_sez_new.stato,
            dpat_sez_new.codice_interno
           FROM dpat_sez_new
          WHERE (dpat_sez_new.stato = ANY (ARRAY[0, 2])) AND dpat_sez_new.data_scadenza IS NULL) sezione(id_fisico, anno, descrizione, "?column?", "?column?_1", stato, codice_interno)
     JOIN ( SELECT dpat_piano_attivita_new.id AS id_fisico,
            dpat_piano_attivita_new.cod_raggruppamento,
            dpat_piano_attivita_new.anno,
            dpat_piano_attivita_new.descrizione,
            dpat_piano_attivita_new.ordinamento,
            NULL::text AS unknown,
            dpat_piano_attivita_new.stato,
            dpat_piano_attivita_new.id_sezione AS id_fisico_sezione,
            dpat_piano_attivita_new.tipo_attivita,
            dpat_piano_attivita_new.alias_piano,
            dpat_piano_attivita_new.alias_attivita,
            dpat_piano_attivita_new.codice_interno_piano,
            dpat_piano_attivita_new.codice_interno_attivita,
            dpat_piano_attivita_new.codice_esame AS codice_esame_attivita,
            dpat_piano_attivita_new.id AS id_logico,
            dpat_piano_attivita_new.codice_alias_attivita,
                CASE
                    WHEN dpat_piano_attivita_new.data_scadenza IS NULL THEN false
                    ELSE true
                END AS disabilitato
           FROM dpat_piano_attivita_new
          WHERE dpat_piano_attivita_new.stato = ANY (ARRAY[0, 2])) piano_attivita ON sezione.id_fisico = piano_attivita.id_fisico_sezione AND sezione.anno = piano_attivita.anno
     JOIN ( SELECT dpat_indicatore_new.id AS id_fisico,
            dpat_indicatore_new.cod_raggruppamento,
            dpat_indicatore_new.anno,
            dpat_indicatore_new.descrizione,
            dpat_indicatore_new.ordinamento,
            NULL::text AS unknown,
            dpat_indicatore_new.stato,
            dpat_indicatore_new.id_piano_attivita AS id_fisico_attivita,
            dpat_indicatore_new.codice_esame AS codice_esame_indicatore,
            dpat_indicatore_new.codice_interno_indicatore,
            dpat_indicatore_new.alias_indicatore,
            dpat_indicatore_new.codice_interno_piani_gestione_cu,
            dpat_indicatore_new.codice_interno_attivita_gestione_cu,
            dpat_indicatore_new.codice_interno_univoco_tipo_attivita_gestione_cu,
            dpat_indicatore_new.codice_alias_indicatore,
            NULL::text AS id_logico_attivita,
                CASE
                    WHEN dpat_indicatore_new.data_scadenza IS NULL THEN false
                    ELSE true
                END AS disabilitato
           FROM dpat_indicatore_new
          WHERE dpat_indicatore_new.stato = ANY (ARRAY[0, 2])) indicatore ON piano_attivita.id_fisico = indicatore.id_fisico_attivita AND piano_attivita.anno = indicatore.anno;

ALTER TABLE public.view_motivi_linearizzati_dpat
  OWNER TO postgres;


DROP VIEW public.lookup_piano_monitoraggio_con_padri_per_digemon;

CREATE OR REPLACE VIEW public.lookup_piano_monitoraggio_con_padri_per_digemon AS 
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
    lookup_piano_monitoraggio_.codice_interno AS codice_interno_univoco,
    NULL::text AS codice
   FROM lookup_piano_monitoraggio_
UNION
( SELECT ind.id AS code,
    (ind.alias_indicatore || ': '::text) || ind.descrizione AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN piano_att.data_scadenza IS NULL AND ind.data_scadenza IS NULL THEN true
            ELSE false
        END AS enabled,
    '-1'::integer AS site_id,
    sez.id AS id_sezione,
    piano_att.ordinamento,
    ind.ordinamento AS ordinamento_figli,
    ind.codice_interno_piani_gestione_cu AS codice_interno,
    piano_att.anno,
        CASE
            WHEN ind.codice_interno_piani_gestione_cu = 1483 OR ind.codice_interno_piani_gestione_cu = 982 THEN true
            ELSE false
        END AS flag_condizionalita,
    '2a'::text AS codice_interno_tipo_ispezione,
    ind.cod_raggruppamento AS codice_interno_ind,
    ind.data_scadenza,
    ind.codice_interno_indicatore AS id_indicatore,
    piano_att.id + 100000 AS id_padre,
    ind.codice_esame,
    ind.cod_raggruppamento AS codice_interno_univoco,
    (piano_att.codice_alias_attivita || '.'::text) || ind.codice_alias_indicatore AS codice
   FROM dpat_indicatore_new ind
     JOIN dpat_piano_attivita_new piano_att ON piano_att.id = ind.id_piano_attivita AND ind.anno = piano_att.anno AND ind.stato <> 1 AND piano_att.stato <> 1
     JOIN dpat_sez_new sez ON sez.id = piano_att.id_sezione AND sez.anno = piano_att.anno AND sez.stato <> 1
  WHERE sez.anno >= 2015 AND piano_att.tipo_attivita ~~* 'piano'::text
  ORDER BY piano_att.ordinamento, ind.ordinamento)
UNION
 SELECT DISTINCT piano_att.id + (+ 100000) AS code,
    ((
        CASE
            WHEN piano_att.tipo_attivita ~~* '%PIANO%'::text THEN 'PIANO '::text
            ELSE 'ATTIVITA '::text
        END || piano_att.alias_piano) || ' '::text) || (piano_att.descrizione || ' '::text) AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN piano_att.data_scadenza IS NULL THEN true
            ELSE false
        END AS enabled,
    '-1'::integer AS site_id,
    sez.id AS id_sezione,
    piano_att.ordinamento,
    0 AS ordinamento_figli,
    piano_att.codice_interno_attivita AS codice_interno,
    piano_att.anno,
    false AS flag_condizionalita,
    '0'::text AS codice_interno_tipo_ispezione,
    piano_att.cod_raggruppamento AS codice_interno_ind,
    piano_att.data_scadenza,
    piano_att.codice_interno_attivita AS id_indicatore,
    '-1'::integer AS id_padre,
    piano_att.codice_esame,
    piano_att.cod_raggruppamento AS codice_interno_univoco,
    piano_att.codice_alias_attivita AS codice
   FROM dpat_indicatore_new ind
     JOIN dpat_piano_attivita_new piano_att ON piano_att.id = ind.id_piano_attivita AND ind.anno = piano_att.anno AND ind.anno = piano_att.anno AND ind.stato <> 1 AND piano_att.stato <> 1
     JOIN dpat_sez_new sez ON sez.id = piano_att.id_sezione AND sez.anno = piano_att.anno AND sez.stato <> 1
  WHERE sez.anno >= 2015 AND piano_att.tipo_attivita ~~* 'piano'::text;

ALTER TABLE public.lookup_piano_monitoraggio_con_padri_per_digemon
  OWNER TO postgres;
GRANT ALL ON TABLE public.lookup_piano_monitoraggio_con_padri_per_digemon TO postgres;


DROP VIEW public.lookup_piano_monitoraggio_vista_new;

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
            WHEN date_part('years'::text, now() - '00:00:00'::interval) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
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
  WHERE sez.anno >= 2015 AND p.tipo_attivita ~~* 'piano'::text AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2])) AND ind.extra_gisa IS NOT TRUE
  ORDER BY p.ordinamento, ind.ordinamento)
  ORDER BY 11;

ALTER TABLE public.lookup_piano_monitoraggio_vista_new
  OWNER TO postgres;


DROP VIEW public.view_motivi_linearizzati_dpat;

CREATE OR REPLACE VIEW public.view_motivi_linearizzati_dpat AS 
 SELECT sezione.anno AS anno_sezione,
    sezione.id_fisico AS id_fisico_sezione,
    sezione.descrizione AS descrizione_sezione,
    piano_attivita.id_fisico AS id_fisico_piano,
    piano_attivita.cod_raggruppamento AS codice_univoco_piano_old,
    piano_attivita.descrizione AS descrizione_piano,
    piano_attivita.ordinamento AS ordinamento_piano,
    piano_attivita.id_fisico AS id_fisico_attivita,
    piano_attivita.cod_raggruppamento AS codice_univoco_attivita_old,
    piano_attivita.descrizione AS descrizione_attivita,
    indicatore.id_fisico AS id_fisico_indicatore,
    indicatore.cod_raggruppamento AS codice_univoco_indicatore_old,
    indicatore.descrizione AS descrizione_indicatore,
    indicatore.ordinamento AS ordinamento_indicatore,
    piano_attivita.codice_esame_attivita,
    indicatore.codice_esame_indicatore,
    piano_attivita.tipo_attivita AS tipo_attivita_piano,
    piano_attivita.tipo_attivita AS tipo_attivita_indicatore,
    sezione.codice_interno AS codice_interno_sezione,
    piano_attivita.alias_piano,
    piano_attivita.codice_interno_piano,
    piano_attivita.alias_attivita,
    piano_attivita.codice_interno_attivita,
    indicatore.codice_interno_indicatore,
    indicatore.alias_indicatore,
    indicatore.codice_interno_piani_gestione_cu,
    indicatore.codice_interno_attivita_gestione_cu,
    indicatore.codice_interno_univoco_tipo_attivita_gestione_cu,
    piano_attivita.codice_alias_attivita,
    indicatore.codice_alias_indicatore,
    indicatore.cod_raggruppamento AS codice_interno_univoco,
    indicatore.disabilitato AS indicatore_disabilitato,
    piano_attivita.disabilitato AS piano_disabilitato,
    piano_attivita.disabilitato AS attivita_disabilitato,
    indicatore.codice_alias_indicatore AS codice
   FROM ( SELECT dpat_sez_new.id AS id_fisico,
            dpat_sez_new.anno,
            dpat_sez_new.descrizione,
            NULL::text AS unknown,
            NULL::text AS unknown,
            dpat_sez_new.stato,
            dpat_sez_new.codice_interno
           FROM dpat_sez_new
          WHERE (dpat_sez_new.stato = ANY (ARRAY[0, 2])) AND dpat_sez_new.data_scadenza IS NULL) sezione(id_fisico, anno, descrizione, "?column?", "?column?_1", stato, codice_interno)
     JOIN ( SELECT dpat_piano_attivita_new.id AS id_fisico,
            dpat_piano_attivita_new.cod_raggruppamento,
            dpat_piano_attivita_new.anno,
            dpat_piano_attivita_new.descrizione,
            dpat_piano_attivita_new.ordinamento,
            NULL::text AS unknown,
            dpat_piano_attivita_new.stato,
            dpat_piano_attivita_new.id_sezione AS id_fisico_sezione,
            dpat_piano_attivita_new.tipo_attivita,
            dpat_piano_attivita_new.alias_piano,
            dpat_piano_attivita_new.alias_attivita,
            dpat_piano_attivita_new.codice_interno_piano,
            dpat_piano_attivita_new.codice_interno_attivita,
            dpat_piano_attivita_new.codice_esame AS codice_esame_attivita,
            dpat_piano_attivita_new.id AS id_logico,
            dpat_piano_attivita_new.codice_alias_attivita,
                CASE
                    WHEN dpat_piano_attivita_new.data_scadenza IS NULL THEN false
                    ELSE true
                END AS disabilitato
           FROM dpat_piano_attivita_new
          WHERE dpat_piano_attivita_new.stato = ANY (ARRAY[0, 2])) piano_attivita ON sezione.id_fisico = piano_attivita.id_fisico_sezione AND sezione.anno = piano_attivita.anno
     JOIN ( SELECT dpat_indicatore_new.id AS id_fisico,
            dpat_indicatore_new.cod_raggruppamento,
            dpat_indicatore_new.anno,
            dpat_indicatore_new.descrizione,
            dpat_indicatore_new.ordinamento,
            NULL::text AS unknown,
            dpat_indicatore_new.stato,
            dpat_indicatore_new.id_piano_attivita AS id_fisico_attivita,
            dpat_indicatore_new.codice_esame AS codice_esame_indicatore,
            dpat_indicatore_new.codice_interno_indicatore,
            dpat_indicatore_new.alias_indicatore,
            dpat_indicatore_new.codice_interno_piani_gestione_cu,
            dpat_indicatore_new.codice_interno_attivita_gestione_cu,
            dpat_indicatore_new.codice_interno_univoco_tipo_attivita_gestione_cu,
            dpat_indicatore_new.codice_alias_indicatore,
            NULL::text AS id_logico_attivita,
                CASE
                    WHEN dpat_indicatore_new.data_scadenza IS NULL THEN false
                    ELSE true
                END AS disabilitato
           FROM dpat_indicatore_new
          WHERE dpat_indicatore_new.stato = ANY (ARRAY[0, 2])) indicatore ON piano_attivita.id_fisico = indicatore.id_fisico_attivita AND piano_attivita.anno = indicatore.anno;

ALTER TABLE public.view_motivi_linearizzati_dpat
  OWNER TO postgres;


alter table dpat_indicatore_new drop column tipo_attivita;


