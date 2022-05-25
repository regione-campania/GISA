
--QUESTO SCRIPT SI COMPONE DI DUE PARTI:
	--LA PRIMA PARTE PUO' ESSERE LANCIATA TRANQUILLAMENTE, E NON ALTERA IL FUNZIONAMENTO PREESISTENTE VECCHIO (A PATTO DI NON ATTIVARE NEL CODICE DELL'APPLICATIVO LE MASCHERE CHE USANO NUOVA GESTIONE)
	--LA SECONDA PARTE INVECE VA A SOSTITUIRE QUELLE CHE SONO LE VECCHIE VERSIONI DELLE VISTE, CON LE NUOVE VERSIONI AGGANCIATE ALLE NUOVE TABELLE, QUINDI NON E' COMPATIBILE CON LA VECCHIA GESTIONE

	

--*************************************************************************************************PRIMA PARTE**********************************************************************

--------------------------------------------------------------------------------------------------DROP TABELLE EVENTUALMENTE CREATE----------------------------------------------------------------

drop table dpat_indicatore_new;
drop table dpat_piano_attivita_new;
drop table dpat_sez_new




--------------------------------------------------------------------------------------------------CREAZIONE TABELLE E SEQ---------------------------------------------------------------------------

 CREATE SEQUENCE public.dpat_sez_new_codice_raggruppamento_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_sez_new_codice_raggruppamento_seq
  OWNER TO postgres;
 
CREATE SEQUENCE public.dpat_sez_new_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_sez_new_id_seq
  OWNER TO postgres;

drop table dpat_sez_new
create table dpat_sez_new ( 
	id integer primary key DEFAULT nextval('dpat_sez_new_id_seq'::regclass),
	cod_raggruppamento integer not null DEFAULT nextval('dpat_sez_new_codice_raggruppamento_seq'::regclass),
	anno integer not null, 
	descrizione text,
	ordinamento integer not null,
	data_scadenza timestamp without time zone,
	stato integer not null
	,codice_interno integer); --ELIMINABILE ?

  
CREATE SEQUENCE public.dpat_piano_attivita_new_codice_raggruppamento_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_piano_attivita_new_codice_raggruppamento_seq
  OWNER TO postgres;

CREATE SEQUENCE public.dpat_piano_attivita_new_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_piano_attivita_new_id_seq
  OWNER TO postgres;

drop table dpat_piano_attivita_new;
create table dpat_piano_attivita_new ( 
	id integer primary key DEFAULT nextval('dpat_piano_attivita_new_id_seq'::regclass),
	cod_raggruppamento integer not null DEFAULT nextval('dpat_piano_attivita_new_codice_raggruppamento_seq'::regclass),
	id_sezione integer not null references dpat_sez_new(id),
	anno integer not null, 
	descrizione text,
	ordinamento integer not null,
	data_scadenza timestamp without time zone,
	stato integer not null,
	codice_esame text 
	,tipo_attivita text
	,codice_interno_piano integer --ELIMINABILE ?
	,codice_interno_attivita integer, --ELIMINABILE ?
	alias_piano text --ELIMINABILE ?
	,alias_attivita text --ELIMINABILE ?
	,codice_alias_attivita text);  --COME VA POPOLATO ??

 

CREATE SEQUENCE public.dpat_indicatore_new_cod_raggruppamento_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_indicatore_new_cod_raggruppamento_seq
  OWNER TO postgres;

 CREATE SEQUENCE public.dpat_indicatore_new_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE  public.dpat_indicatore_new_id_seq
  OWNER TO postgres;

drop table dpat_indicatore_new;
create table dpat_indicatore_new ( 
	id integer primary key DEFAULT nextval('dpat_indicatore_new_id_seq'::regclass),
	cod_raggruppamento integer not null DEFAULT nextval('dpat_indicatore_new_cod_raggruppamento_seq'::regclass),
	id_piano_attivita integer references dpat_piano_attivita_new(id),
	anno integer not null, 
	descrizione text,
	ordinamento integer not null,
	data_scadenza timestamp without time zone,
	stato integer not null
	,codice_esame text
	,tipo_attivita text
	,codice_interno_indicatore integer --ELIMINABILE ?
	,alias_indicatore text --ELIMINABILE ?
	,codice_interno_piani_gestione_cu integer
	,codice_interno_attivita_gestione_cu text
	,codice_interno_univoco_tipo_attivita_gestione_cu text
	,codice_alias_indicatore text); 
	
	
	
--------------------------------------------------------------------------------------------------------------------------------- CREAZIONE FUNZIONE POPOLAMENTO




drop function popola_dpat_new(integer);
create or replace function popola_dpat_new(in anno_i integer) returns void as

$BODY$
declare
	rec1 record;
	t integer;
	
begin

	execute ('delete from dpat_indicatore_new where anno = ' || anno_i::text); 
	execute ('delete from dpat_piano_attivita_new where anno = ' || anno_i::text);
	execute ('delete from dpat_sez_new where anno = ' || anno_i::text);

	/*for loop che gira sulle entry delle vecchie views, facendo le join con gli oid logici (per hp non devono esserci cicli) e crea entry con id fisici */
	for rec1 in 

		select sezione.anno as anno,sezione.id_fisico id_fisico_sezione, sezione.description as descrizione_sezione, piano.id_fisico as id_fisico_piano, piano.codice_interno_univoco as codice_univoco_piano_old
		,piano.description as descrizione_piano, piano.ordinamento as ordinamento_piano, attivita.id_fisico as id_fisico_attivita, attivita.codice_interno_univoco as codice_univoco_attivita_old, 
		attivita.description as descrizione_attivita, indicatore.id_fisico as id_fisico_indicatore, indicatore.codice_interno_univoco as codice_univoco_indicatore_old, indicatore.description
		as descrizione_indicatore, indicatore.ordinamento as ordinamento_indicatore,
		
		codice_esame_attivita,
		codice_esame_indicatore
		,piano.tipo_attivita as tipo_attivita_piano
		,indicatore.tipo_attivita as tipo_attivita_indicatore 
		,sezione.codice_interno as codice_interno_sezione
		,piano.alias as alias_piano
		,piano.codice_interno as codice_interno_piano
		,attivita.alias as alias_attivita
		,attivita.codice_interno as codice_interno_attivita
		,indicatore.codice_interno as codice_interno_indicatore
		,indicatore.alias as alias_indicatore
		,indicatore.codice_interno_piani_gestione_cu
		,indicatore.codice_interno_attivita_gestione_cu
		,indicatore.codice_interno_univoco_tipo_attivita_gestione_cu
		,'' as codice_alias_attivita --COME LI DEVO POPOLARE ?
		,'' as codice_alias_indicatore --COME LI DEVO POPOLARE ?
		from 
		(
		select id as id_fisico,anno,description ,null ,null, stato, codice_interno from 
		dpat_sezione where anno = anno_i and stato in (0,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/
		--select * from dpat_sez_new
		) sezione
		/*query inserimento piano attivita*/
			join 
		(--select id_, count(*) from dpat_piano where anno = 2017 and stato in (1,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/ group by id_ having count(*) > 1
		select id_ as id_fisico, codice_interno_univoco ,anno,description ,ordinamento,null, stato, id_sezione as id_fisico_sezione, tipo_attivita
		, alias, codice_interno, id as id_logico from 
		dpat_piano where anno = anno_i and stato in (0,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/
		) piano
			on sezione.id_fisico = piano.id_fisico_sezione
			join
		/*attivita*/
		--select id_, count(*) from dpat_attivita where anno = 2017 and stato in (1,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/ group by id_ having count(*) > 1
		(select id_ as id_fisico, codice_interno_univoco ,anno,description ,ordinamento,null, stato, id_piano_ as id_fisico_piano,  codice_esame as codice_esame_attivita
		,alias, codice_interno, id_piano as id_logico_piano, id as id_logico   from 
		dpat_attivita where anno = anno_i and stato in (0,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/
		) attivita
			on piano.id_logico = attivita.id_logico_piano
			join
		/*indicatore*/
		--select id_, count(*) from dpat_indicatore where anno = 2017 and stato in (1,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/ group by id_ having count(*) > 1
		(select id_ as id_fisico, codice_interno_univoco ,anno,description ,ordinamento,null, stato, id_attivita_ as id_fisico_attivita, codice_esame as codice_esame_indicatore, tipo_attivita
		, codice_interno, alias, codice_interno_piani_gestione_cu, codice_interno_attivita_gestione_cu, codice_interno_univoco_tipo_attivita_gestione_cu,
		id_attivita as id_logico_attivita from 
		dpat_indicatore where anno = anno_i and stato in (0,2) and enabled = true /*disabilitazione per scadenza*/ and disabilitato = false /*disab esplicita*/
		) indicatore
			on attivita.id_logico = indicatore.id_logico_attivita
	loop
		 
		 /*inserisco la sezione, potrebbe esserci già!*/
		begin
			insert into dpat_sez_new(id,anno,descrizione,ordinamento,data_scadenza,stato,codice_interno ) values(rec1.id_fisico_sezione,rec1.anno,rec1.descrizione_sezione,0,null,2,rec1.codice_interno_sezione);
			
		exception 
			WHEN unique_violation THEN
			--ignora
		end;

 
		/*...piano_attivita UNITI*/
		begin
			insert into dpat_piano_attivita_new(id,cod_raggruppamento,id_sezione,anno,descrizione,ordinamento,data_scadenza,stato,codice_alias_attivita, codice_esame, tipo_attivita,codice_interno_piano,codice_interno_attivita,alias_piano,alias_attivita) 
			values(rec1.id_fisico_piano, rec1.codice_univoco_piano_old, rec1.id_fisico_sezione,rec1.anno, (rec1.descrizione_piano || '-' || rec1.descrizione_attivita), rec1.ordinamento_piano, null, 2, rec1.codice_alias_attivita, rec1.codice_esame_attivita, rec1.tipo_attivita_piano,rec1.codice_interno_piano,rec1.codice_interno_attivita,rec1.alias_piano,rec1.alias_attivita);
		exception
			WHEN unique_violation THEN
						--ignora
		end;

		/*indicatore...NON FARLI PUNTARE ALLEA TTIVITA MA AI PIANI ORA CHE SONO UNITI PIANI E ATTIVITA */
		 
		insert into dpat_indicatore_new(id,cod_raggruppamento,id_piano_attivita,anno,descrizione,ordinamento,data_scadenza,stato,codice_alias_indicatore,codice_esame,tipo_attivita
		,codice_interno_indicatore,alias_indicatore,codice_interno_piani_gestione_cu,codice_interno_attivita_gestione_cu,codice_interno_univoco_tipo_attivita_gestione_cu)
		values(rec1.id_fisico_indicatore,rec1.codice_univoco_indicatore_old,rec1.id_fisico_piano,rec1.anno,rec1.descrizione_indicatore,rec1.ordinamento_indicatore,null,2,rec1.codice_alias_indicatore, rec1.codice_esame_indicatore,/*rec1.tipo_attivita_indicatore*/rec1.tipo_attivita_indicatore
		,rec1.codice_interno_indicatore,rec1.alias_indicatore,rec1.codice_interno_piani_gestione_cu,rec1.codice_interno_attivita_gestione_cu,rec1.codice_interno_univoco_tipo_attivita_gestione_cu);
		 
		
	end loop;

	--aggiorno i sequences per farli partire dagli ultimi oid fisici (e per i logici anche) assegnati dalle vecchie tabelle

	--sez
	select max(cod_raggruppamento)+1 into t from dpat_sez_new;
	execute ('ALTER SEQUENCE dpat_sez_new_codice_raggruppamento_seq RESTART WITH ' || t::text );
	
	select max(id) into t from dpat_sez_new;
	execute ('ALTER SEQUENCE dpat_sez_new_id_seq RESTART WITH ' || t::text );

	--piano_attivita
	select max(cod_raggruppamento)+1 into t from dpat_piano_attivita_new;
	execute ('ALTER SEQUENCE dpat_piano_attivita_new_codice_raggruppamento_seq RESTART WITH ' || t::text );
	
	select max(id)+1 into t from dpat_piano_attivita_new;
	execute ('ALTER SEQUENCE dpat_piano_attivita_new_id_seq RESTART WITH ' || t::text );

	--indicatore
	select max(cod_raggruppamento)+1 into t from dpat_indicatore_new;
	execute ('ALTER SEQUENCE dpat_indicatore_new_cod_raggruppamento_seq RESTART WITH ' || t::text );
	
	select max(id)+1 into t from dpat_indicatore_new;
	execute ('ALTER SEQUENCE dpat_indicatore_new_id_seq RESTART WITH ' || t::text );



	
end;
$BODY$
language plpgsql;


 
	


	
	
	
	
---------------------------------------------------------------------------------POPOLAMENTO DLELE TABELLE-----------------------------------------------------------------------------------
	
select * from popola_dpat_new(anno)


 

--*************************************************************************************************SECONDA PARTE (QUELLA NON COMPATIBILE CON VECCHIA GESTIONE)**********************************************************************

 
--------------------------------------------------------------------------------- MODIFICA DELLE VISTE PER AGGANCIARLE ALLE NUOVE TABELLE --------------------------------------------
--PER PIANI MONITORAGGIO

CREATE OR REPLACE VIEW public.lookup_piano_monitoraggio_vista AS 
 --questa prima parte va lasciata cosi' com'e'
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
    NULL::text AS codice
   FROM lookup_piano_monitoraggio_
UNION

 --da riscrivere
( SELECT DISTINCT ind.id AS code,
    ((
     (  (  (upper(p.descrizione) || ' '::text) || '-'::text) ||
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore
            ELSE ''::text
        END
       ) || ':'::text) || ind.descrizione) ||
        CASE
            WHEN sez.anno > 0 THEN ''::text --??
            ELSE ''::text
        END AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN date_part('years'::text, now() - '22 days'::interval) = sez.anno::double precision /*AND ind.extra_gisa = false*/ AND (/*att.data_scadenza > now() OR*/  p.data_scadenza IS NULL) AND (/*ind.data_scadenza > now() OR*/  ind.data_scadenza IS NULL) AND 
            (ind.stato = ANY (ARRAY[0, 2])) THEN true
            ELSE false
        END AS enabled,
    '-1'::integer AS site_id,
    sez.id AS id_sezione,
    p.ordinamento,
    ind.ordinamento AS ordinamento_figli,
    ind.codice_interno_piani_gestione_cu AS codice_interno,
    sez.anno,
        CASE
            WHEN ind.codice_interno_piani_gestione_cu = 1483 OR ind.codice_interno_piani_gestione_cu = 982 THEN true
            ELSE false
        END AS flag_condizionalita,
    '2a'::text AS codice_interno_tipo_ispezione,
    ind.codice_interno_indicatore AS codice_interno_ind,
    COALESCE(ind.data_scadenza, p.data_scadenza) AS data_scadenza,
    ind.codice_interno_indicatore AS id_indicatore,
    p.id AS id_padre,
    ind.codice_esame,
    false as flag_vitelli, --??
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore || ':'::text
            ELSE ''::text
        END || ind.descrizione AS short_description,
    ind.alias_indicatore as alias,
    ind.cod_raggruppamento as codice_interno_univoco
    ,codici.codice
   FROM dpat_indicatore_new ind
     --JOIN dpat_attivita att ON att.id = ind.id_attivita AND ind.anno = att.anno
     JOIN dpat_piano_attivita_new p ON p.id = ind.id_piano_attivita and p.anno = ind.anno
     JOIN dpat_sez_new sez ON sez.id = p.id_sezione AND sez.anno = p.anno
    -- JOIN dpat_istanza ist ON ist.id = sez.id_dpat_istanza AND ist.anno = sez.anno
     LEFT JOIN dpat_codici_indicatore codici ON codici.codice_interno_univoco_indicatore = ind.cod_raggruppamento AND codici.data_scadenza IS NULL
  WHERE sez.anno >= 2015 AND ind.tipo_attivita ilike 'piano'::text AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2]))   
  ORDER BY p.ordinamento, ind.ordinamento)
  ORDER BY 11;

ALTER TABLE public.lookup_piano_monitoraggio_vista
  OWNER TO postgres;
  
  
  
  
  
  
----PER TIPO ISPEZIONI

CREATE OR REPLACE VIEW public.lookup_tipo_ispezione_vista AS 
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
    ((
     (  (  (upper(p.descrizione) || ' '::text) || '-'::text) ||
        CASE
            WHEN ind.alias_indicatore IS NOT NULL THEN ind.alias_indicatore
            ELSE ''::text
        END
       ) || ':'::text) || ind.descrizione) ||
        CASE
            WHEN sez.anno > 0 THEN ''::text --??
            ELSE ''::text
        END AS description,
    false AS default_item,
    0 AS level,
        CASE
            WHEN date_part('years'::text, now() - '22 days'::interval) = sez.anno::double precision /*AND ind.extra_gisa = false*/ AND (/*att.data_scadenza > now() OR*/  p.data_scadenza IS NULL) AND (/*ind.data_scadenza > now() OR*/  ind.data_scadenza IS NULL) AND 
            (ind.stato = ANY (ARRAY[0, 2])) THEN true
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
    ind.alias_indicatore as alias,
    codici.codice,
    p.id AS id_padre
   FROM dpat_indicatore_new ind
     --JOIN dpat_attivita att ON att.id = ind.id_attivita AND ind.anno = att.anno
     JOIN dpat_piano_attivita_new p ON p.id = ind.id_piano_attivita AND p.anno = ind.anno
     JOIN dpat_sez_new sez ON sez.id = p.id_sezione AND sez.anno = p.anno
     --JOIN dpat_istanza ist ON ist.id = sez.id_dpat_istanza AND ist.anno = sez.anno
     LEFT JOIN dpat_codici_indicatore codici ON codici.codice_interno_univoco_indicatore = ind.cod_raggruppamento AND codici.data_scadenza IS NULL
  WHERE sez.anno >= 2015 AND ind.tipo_attivita ~~* 'attivita-ispezione'::text AND (ind.stato = ANY (ARRAY[0, 2])) AND (p.stato = ANY (ARRAY[0, 2]))  
  ORDER BY p.ordinamento, ind.ordinamento);

ALTER TABLE public.lookup_tipo_ispezione_vista
  OWNER TO postgres;
  
  
  
  
  
---- -------------------------------------------------------------------------- PER LO SCARICO DALLE VISTE NELLE TABELLE EFFETTIVAMENTE INTERROGATE DALLE MASCHERE (NON IL CONFIGURATORE) ---------------------------------------------
--va chiamata ogni volta che si aggiunge /modifica/Elimina motivo
select * from public.refresh_motivi_cu(anno)

