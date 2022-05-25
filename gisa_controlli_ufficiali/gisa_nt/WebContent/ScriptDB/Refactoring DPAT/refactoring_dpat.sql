--CONFIGURATORE
update dpat_piano_attivita_new set codice_esame = null where codice_esame ilike '%null%';
--Per allineamento diagnostica gisa con diagnostica digemon
select * from dpat_sez_new where anno = 2019 and  descrizione ilike '%sezione e%';
update dpat_piano_attivita_new set tipo_attivita = 'PIANO' where id_sezione in (select id from dpat_sez_new where anno in (2019,2020) and  descrizione ilike '%sezione e%');


CREATE OR REPLACE FUNCTION public.dpat_get_stato(IN anno_input integer)
  RETURNS integer AS
$BODY$
DECLARE
stato integer;
	 	
BEGIN
	stato := (select distinct d.stato from dpat_piano_attivita_new d where data_scadenza is null and anno = anno_input);
	RETURN stato;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_get_stato(integer)
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public.dpat_is_congelato(IN anno_input integer)
  RETURNS boolean AS
$BODY$
DECLARE
congelato boolean;
stato integer;	
BEGIN
	raise info 'DBI dpat_is_congelato -- inizio chiamata';
	raise info 'DBI dpat_is_congelato -- input: anno: %', anno_input;
	stato := (select * from dpat_get_stato(anno_input));
	raise info 'DBI dpat_is_congelato -- output: %', stato!=1;
	RETURN stato!=1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_is_congelato(integer)
  OWNER TO postgres;
  

CREATE OR REPLACE FUNCTION public.dpat_get_stato(IN anno_input integer)
  RETURNS integer AS
$BODY$
DECLARE
stato integer;
	 	
BEGIN
	stato := (select distinct d.stato from dpat_piano_attivita_new d where data_scadenza is null and anno = anno_input order by d.stato limit 1);
	RETURN stato;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_is_congelato(integer)
  OWNER TO postgres;







CREATE OR REPLACE FUNCTION public.dpat_get_indicatori(IN id_piano_attivita_input integer,IN stato_input text, IN includi_scaduti boolean)
  RETURNS TABLE( id integer , cod_raggruppamento integer , id_piano_attivita integer,  anno integer, descrizione text, ordinamento integer, data_scadenza timestamp without time zone,
  stato integer,  codice_esame text, alias_indicatore text,  
  codice_alias_indicatore text, extra_gisa boolean, flag_benessere boolean) AS
$BODY$
DECLARE
BEGIN
	raise info 'DBI dpat_get_indicatori -- inizio chiamata';
	raise info 'DBI dpat_get_indicatori -- input: stato: %, id_piano_attivita: %, includi_scaduti: %', stato_input, id_piano_attivita_input,includi_scaduti;
	FOR
	id  , cod_raggruppamento  , id_piano_attivita ,  anno , descrizione , ordinamento , data_scadenza, stato,codice_esame ,  
	alias_indicatore,    
	codice_alias_indicatore , extra_gisa , flag_benessere  
	in
	select d.id , d.cod_raggruppamento , d.id_piano_attivita,  d.anno, d.descrizione, d.ordinamento, d.data_scadenza,
  d.stato ,  d.codice_esame, d.alias_indicatore,  
  d.codice_alias_indicatore, d.extra_gisa, d.flag_benessere
   from dpat_indicatore_new d
	where (stato_input is null or d.stato::text = ANY (string_to_array(stato_input, ','))) and 
	(id_piano_attivita_input is null or d.id_piano_attivita = id_piano_attivita_input)  and
	((includi_scaduti is false and d.data_scadenza is null ) or includi_scaduti is true)
	order by ordinamento
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dpat_get_indicatori(integer,text,boolean)
  OWNER TO postgres;




  
--Da capire se funziona la dbi chiamandola a mano
  CREATE OR REPLACE FUNCTION public.dpat_delete_dummy_brother(IN id_indicatore integer, IN stato_input text , IN tipo_inserimento text,IN anno_input integer,IN id_piano integer)
  RETURNS void AS
$BODY$
declare
stato_to_pass text;
begin
	raise info 'DBI dpat_delete_dummy_brother -- inizio chiamata';
	raise info 'DBI dpat_delete_dummy_brother -- input: id_indicatore: % , stato_input: %   ,  tipo_inserimento: %  , anno_input: % , id_piano: %   ',   id_indicatore , stato_input  ,  tipo_inserimento , anno_input, id_piano;
	 
	case when (stato_input=null) then
		raise info 'DBI dpat_delete_dummy_brother -- entrato nell''if stato_input=null' ;
		stato_to_pass := '-1';
		raise info 'DBI dpat_delete_dummy_brother -- stato_to_pass: %', stato_to_pass;
	else
		raise info 'DBI dpat_delete_dummy_brother -- entrato nell''else stato_input!=null' ;
		stato_to_pass := stato_input;
		raise info 'DBI dpat_delete_dummy_brother -- stato_to_pass: %', stato_to_pass;
	end case;
	delete from dpat_indicatore_new where descrizione ilike 'INDICATORE DI DEFAULT DA SOSTITUIRE' and anno = anno_input and id_piano_attivita = id_piano;		
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_delete_dummy_brother(integer,text,text,integer,integer)
  OWNER TO postgres;





CREATE OR REPLACE FUNCTION public.dpat_get_stato_dopo_modifica(IN anno_input integer)
  RETURNS integer AS
$BODY$
DECLARE
max integer;
stato integer;
BEGIN
      raise info 'DBI dpat_get_stato_dopo_modifica -- inizio chiamata';
      raise info 'DBI dpat_get_stato_dopo_modifica -- input: anno: % ', anno_input;
	
      max := (select  greatest(max(a.stato), max(b.stato)) as massimo from dpat_piano_attivita_new a join dpat_indicatore_new b on a.id = b.id_piano_attivita where a.anno = anno_input and a.data_scadenza is null and b.data_scadenza is null);
      raise info 'DBI dpat_get_stato_dopo_modifica -- max: %', max;
      stato := max % 2; 
      raise info 'DBI dpat_get_stato_dopo_modifica -- stato: %',stato;
      raise info 'DBI dpat_get_stato_dopo_modifica -- output: %',stato;
      return stato;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_get_stato_dopo_modifica(integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.dpat_delete_indicatore(IN id_in integer)
RETURNS void AS
$BODY$
declare	
begin
	raise info 'DBI dpat_delete_indicatore -- inizio chiamata';
        raise info 'DBI dpat_delete_indicatore -- input: id_in: %', id_in;
	delete from dpat_indicatore_new  where id = id_in ;
			
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_delete_indicatore(integer)
  OWNER TO postgres;


  CREATE OR REPLACE FUNCTION public.dpat_delete_piano_attivita(IN id_in integer)
RETURNS void AS
$BODY$
declare	
begin
	raise info 'DBI dpat_delete_piano_attivita -- inizio chiamata';
        raise info 'DBI dpat_delete_piano_attivita -- input: id_in: %', id_in;
	delete from dpat_piano_attivita_new  where id = id_in ;
			
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_delete_piano_attivita(integer)
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public.peeknextcodicealias(
    tipologia text,
    anno_in text,
    codice_alias_indicatore_in text,
    id_indicatore_in integer)
  RETURNS text AS
$BODY$ 
declare
	tempn bigint;
	cod_piano_attivita text;
	result text;
begin
	if lower(tipologia) = 'piano-attivita' then 
	
		--select max(codice_alias_attivita::integer) from dpat_piano_attivita_new into tempn;
	 tempn := (select max(valore_codice_attivita) from codici_piani_attivita_indicatori where anno = anno_in::integer);
	
	 --where codice_alias_attivita  = lower(codice_alias_indicatore_in) and anno = anno_in::integer);
         result := lpad((tempn+1)::text,7,'0');
         
	else 	
	        cod_piano_attivita := (select codice_alias_attivita from codici_piani_attivita_indicatori where id_indicatore = id_indicatore_in);	        
		tempn := (select max(valore_codice) from codici_piani_attivita_indicatori where anno = anno_in::integer and codice_alias_attivita = lower(cod_piano_attivita));
		--id_indicatore = id_indicatore_in and codice_alias_indicatore = lower(codice_alias_indicatore_in) and anno = anno_in::integer and codice_alias_attivita = lower(cod_piano_attivita));
		--select max(codice_alias_indicatore::integer) from dpat_indicatore_new into tempn;
		result := lpad((tempn+1)::text,2,'0');
		if(result is null or result = '') then
			result := concat(cod_piano_attivita,'.1');
		end if;
		
	end if;
	return result;
end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.peeknextcodicealias(text, text, text, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get_motivi_cu(IN _tipologiaoperatore integer, IN anno_input integer)
  RETURNS TABLE(cod_interno_ind text, id_tipo_ispezione integer, descrizione_tipo_ispezione character varying, codice_int_tipo_ispe text, id_piano integer, descrizione_piano character varying, codice_int_piano integer, ordinamento integer, ordinamento_figli integer, livello_tipo_ispezione integer, data_scadenza timestamp without time zone, codice_interno_ind text, anno integer, codice text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR cod_interno_ind , id_tipo_ispezione , descrizione_tipo_ispezione  , codice_int_tipo_ispe , id_piano , descrizione_piano  , codice_int_piano , ordinamento , ordinamento_figli , livello_tipo_ispezione , data_scadenza  , codice_interno_ind , anno , codice 
	
	in
	
        select a.cod_interno_ind , a.id_tipo_ispezione , a.descrizione_tipo_ispezione  , a.codice_int_tipo_ispe , a.id_piano , a.descrizione_piano  , a.codice_int_piano , a.ordinamento , a.ordinamento_figli , a.livello_tipo_ispezione , a.data_scadenza  , a.codice_interno_ind , a.anno ,a.codice  from (  select * from (select distinct on (a.codice_interno_ind) a.codice_interno_ind as cod_interno_ind ,a.* from (select c1.* from controlli_ufficiali_motivi_ispezione c1 where  c1.anno = anno_input and (c1.data_scadenza > (now() + '1 day'::interval) OR c1.data_scadenza IS NULL) ) a order by cod_interno_ind,data_scadenza )bb  union  select * from (select   a.codice_interno_ind as cod_interno_ind ,a.* from (select c.* from controlli_ufficiali_motivi_ispezione c where c.anno = anno_input and   (c.data_scadenza > (now() + '1 day'::interval) OR c.data_scadenza IS NULL) and (c.codice_interno_ind is null or c.codice_interno_ind = '0') ) a order by cod_interno_ind,data_scadenza )bb ) a 
        where 1=1 and
 (_tipologiaoperatore IN (select cmo.tipologia_operatore from cu_motivi_operatori cmo) AND a.codice in (select cmo.codice_piano_o_attivita from cu_motivi_operatori cmo where cmo.tipologia_operatore = _tipologiaoperatore))
 or (_tipologiaoperatore NOT IN (select cmo.tipologia_operatore from cu_motivi_operatori cmo))
         order by a.ordinamento,a.ordinamento_figli
        
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_motivi_cu(integer,integer)
  OWNER TO postgres;


create table dpat_tolleranza_inserimento_cu (
intervallo text
);

insert into dpat_tolleranza_inserimento_cu values('15 days');

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
            WHEN date_part('years'::text, now() - (select * from dpat_tolleranza_inserimento_cu)::interval ) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
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
            WHEN date_part('years'::text, now() - (select * from dpat_tolleranza_inserimento_cu)::interval ) = sez.anno::double precision AND p.data_scadenza IS NULL AND ind.data_scadenza IS NULL AND (ind.stato = ANY (ARRAY[0, 2])) THEN true
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


--STRUMENTO DI CALCOLO	
--select * from public.dpat_duplica_strumento_calcolo(2019,2020)
    CREATE OR REPLACE FUNCTION public.dpat_duplica_strumento_calcolo(
    anno_i integer,
    anno_target_i integer)
  RETURNS void AS
$BODY$
declare
rec_strumento_calcolo_old record;
rec_struttura_semplice_old record;
rec_struttura_complessa_old record;
rec_dpat_old record;
rec_nominativo_old record;
id_strumento_calcolo_inserito integer;
id_struttura_semplice_inserita integer;
id_struttura_complessa_inserita integer;
id_nominativo_inserito integer;
id_dpat_istanza_inserito integer;
next_id integer;
begin

--Azzero l'anno di destinazione
raise info 'Cancellazione dpat in corso';
delete from dpat where anno = anno_target_i;
raise info 'Cancellazione dpat_istanza in corso';
delete from dpat_istanza where anno = anno_target_i;
raise info 'Cancellazione dpat_strumento_calcolo in corso';
delete from dpat_strumento_calcolo where anno = anno_target_i;
raise info 'Cancellazione dpat_strumento_calcolo_nominativi_ in corso';
delete from dpat_strumento_calcolo_nominativi_ where id_dpat_strumento_calcolo_strutture in (select id from strutture_asl where anno = anno_target_i) and id_dpat_strumento_calcolo_strutture in (select codice_interno_fk from strutture_asl where anno = anno_target_i);
raise info 'Cancellazione strutture_asl in corso';
delete from strutture_asl where anno = anno_target_i;
   
insert into dpat_istanza(anno,entered,enteredby,stato,flag_import_piani) values(anno_target_i,current_timestamp,291,1,false) returning id into id_dpat_istanza_inserito;

for rec_dpat_old in 
select * from dpat where anno = anno_i 
loop
	INSERT INTO dpat(
            anno, id_asl,entered, entered_by, 
            modified, modified_by, enabled, completo, id_dpat_istanza, 
            congelato, data_congelamento)
    VALUES (anno_target_i, rec_dpat_old.id_asl, current_timestamp, 291,  
            current_timestamp, 291, rec_dpat_old.enabled, rec_dpat_old.completo, id_dpat_istanza_inserito, 
            false, null);
end loop;

for rec_strumento_calcolo_old in 
select * from dpat_strumento_calcolo where anno = anno_i 
loop
	insert INTO dpat_strumento_calcolo (anno,         completo,entered,          enteredby,id_asl,                                    modified,modifiedby,riaperto,stato) 
	                             values(anno_target_i,false,   current_timestamp,291,      rec_strumento_calcolo_old.id_asl, current_timestamp,291,       false,   1)
	returning id
        into id_strumento_calcolo_inserito;

        for rec_struttura_complessa_old in 
		select strutt.*
		from strutture_asl strutt
		LEFT JOIN lookup_tipologia_nodo_oia tipooia ON strutt.tipologia_struttura = tipooia.code 
		where tipologia_struttura in( 13,14) 
			and strutt.trashed_date is null
			and strutt.id_strumento_calcolo = rec_strumento_calcolo_old.id
			and (strutt.data_scadenza is null or strutt.data_scadenza> current_timestamp)
			and strutt.enabled 
		order by codice_interno_fk 
		loop
			raise info 'id struttra complessa old %', rec_struttura_complessa_old.id;
			next_id := (select max(id) +1 from strutture_asl where id <10000000);
			insert into strutture_asl(id,anno,       stato,  id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, tipologia_struttura,
						  comune, enabled, obsoleto, confermato, id_strumento_calcolo, codice_interno_fk, nome,id_utente,mail,indirizzo,delegato,descrizione_comune,id_oia_nodo_temp,
                                                  descrizione_area_struttura_complessa,id_lookup_area_struttura_asl,ui_struttura_foglio_att_iniziale,ui_struttura_foglio_att_finale,    
                                                  id_utente_edit, percentuale_area_a,stato_all2,stato_all6,codice_interno_univoco,id_struttura_anno_prec) 
		        values(next_id,anno_target_i,       1,  rec_struttura_complessa_old.id_padre, rec_struttura_complessa_old.id_asl, rec_struttura_complessa_old.descrizione_lunga, rec_struttura_complessa_old.n_livello, current_timestamp, 291, current_timestamp, 291, rec_struttura_complessa_old.tipologia_struttura,
                               rec_struttura_complessa_old.comune, rec_struttura_complessa_old.enabled, rec_struttura_complessa_old.obsoleto, rec_struttura_complessa_old.confermato, id_strumento_calcolo_inserito, next_id, rec_struttura_complessa_old.nome,rec_struttura_complessa_old.id_utente,rec_struttura_complessa_old.mail,rec_struttura_complessa_old.indirizzo,rec_struttura_complessa_old.delegato,rec_struttura_complessa_old.descrizione_comune,rec_struttura_complessa_old.id_oia_nodo_temp,
                               rec_struttura_complessa_old.descrizione_area_struttura_complessa,rec_struttura_complessa_old.id_lookup_area_struttura_asl,rec_struttura_complessa_old.ui_struttura_foglio_att_iniziale,rec_struttura_complessa_old.ui_struttura_foglio_att_finale,    
                               rec_struttura_complessa_old.id_utente_edit, rec_struttura_complessa_old.percentuale_area_a,rec_struttura_complessa_old.stato_all2,rec_struttura_complessa_old.stato_all6,next_id,rec_struttura_complessa_old.id_struttura_anno_prec)
                        returning id into id_struttura_complessa_inserita;
			raise info 'id struttra complessa new %', id_struttura_complessa_inserita;
 
                        for rec_struttura_semplice_old in 
			select *
			from strutture_asl 
			where id_padre = rec_struttura_complessa_old.id
			      and stato != 1 
			      and trashed_date is null
			      and enabled
			      and (data_scadenza is null or data_scadenza> current_timestamp)
			order by codice_interno_fk
			      loop
			        raise info 'id struttra semplice old %', rec_struttura_semplice_old.id;
			        next_id := (select max(id) +1 from strutture_asl where id <10000000);
				insert into strutture_asl(id,anno,       stato,  id_padre, id_asl, descrizione_lunga, n_livello, entered, entered_by, modified, modified_by, tipologia_struttura,
						  comune, enabled, obsoleto, confermato, id_strumento_calcolo, codice_interno_fk, nome,id_utente,mail,indirizzo,delegato,descrizione_comune,id_oia_nodo_temp,
                                                  descrizione_area_struttura_complessa,id_lookup_area_struttura_asl,ui_struttura_foglio_att_iniziale,ui_struttura_foglio_att_finale,    
                                                  id_utente_edit, percentuale_area_a,stato_all2,stato_all6,codice_interno_univoco,id_struttura_anno_prec) 
				values(next_id,anno_target_i,       1,  id_struttura_complessa_inserita, rec_struttura_semplice_old.id_asl, rec_struttura_semplice_old.descrizione_lunga, rec_struttura_semplice_old.n_livello, current_timestamp, 291, current_timestamp, 291, rec_struttura_semplice_old.tipologia_struttura,
					rec_struttura_semplice_old.comune, rec_struttura_semplice_old.enabled, rec_struttura_semplice_old.obsoleto, rec_struttura_semplice_old.confermato, id_strumento_calcolo_inserito, next_id, rec_struttura_semplice_old.nome,291,rec_struttura_semplice_old.mail,rec_struttura_semplice_old.indirizzo,rec_struttura_semplice_old.delegato,rec_struttura_semplice_old.descrizione_comune,rec_struttura_semplice_old.id_oia_nodo_temp,
                               rec_struttura_semplice_old.descrizione_area_struttura_complessa,rec_struttura_semplice_old.id_lookup_area_struttura_asl,rec_struttura_semplice_old.ui_struttura_foglio_att_iniziale,rec_struttura_semplice_old.ui_struttura_foglio_att_finale,    
                               291, rec_struttura_semplice_old.percentuale_area_a,rec_struttura_semplice_old.stato_all2,rec_struttura_semplice_old.stato_all6,next_id,rec_struttura_semplice_old.id_struttura_anno_prec)
                               returning id into id_struttura_semplice_inserita;
		               raise info 'id struttra semplice new %', id_struttura_semplice_inserita;

				for rec_nominativo_old in 
					select *
					from
					  dpat_strumento_calcolo_nominativi_
					where (id_dpat_strumento_calcolo_strutture = rec_struttura_semplice_old.id or id_dpat_strumento_calcolo_strutture = rec_struttura_semplice_old.codice_interno_fk)
					  and trashed_date is null
					  and (data_scadenza is null or data_scadenza> current_timestamp)
					order by codice_interno_fk
				      loop
				        raise info 'id nominativo old %', rec_nominativo_old.id;
				        next_id := (select max(id) +1 from dpat_strumento_calcolo_nominativi_ where id <10000000);
					insert into dpat_strumento_calcolo_nominativi_(id,stato,id_anagrafica_nominativo,id_lookup_qualifica,id_dpat_strumento_calcolo_strutture, id_old_anagrafica_nominativo,
                                                 note_hd ,confermato   , confermato_da,data_conferma, data_scadenza , codice_interno_fk, modified_by, modified ,
                                                 entered ,entered_by) 
					values(next_id,1,rec_nominativo_old.id_anagrafica_nominativo, rec_nominativo_old.id_lookup_qualifica, id_struttura_semplice_inserita, rec_nominativo_old.id_old_anagrafica_nominativo, 
						rec_nominativo_old.note_hd , rec_nominativo_old.confermato   , rec_nominativo_old.confermato_da, rec_nominativo_old.data_conferma, rec_nominativo_old.data_scadenza , next_id, 291, current_timestamp , 
						current_timestamp , 291) returning id into  id_nominativo_inserito ;
					raise info 'id nominativo new %', id_nominativo_inserito;

				end loop;

			end loop;

		

		end loop;


end loop;

end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_duplica_strumento_calcolo(integer, integer)
  OWNER TO postgres;

 
  
  CREATE OR REPLACE FUNCTION public.dpat_insert_struttura(anno_input integer, id_asl_input integer,tipo_struttura_input integer, descrizione_lunga_input text, id_utente_input integer, id_padre_input integer,stato_input integer, data_congelamento_input timestamp without time zone)
  RETURNS integer AS
$BODY$
DECLARE
next_id integer;
id_strumento_calcolo_to_insert integer;
id_padre_to_insert integer;
n_livello_to_insert integer;
	 	
BEGIN
	next_id := (select max(id) +1 from strutture_asl where id <10000000);
	id_strumento_calcolo_to_insert := (select id from dpat_strumento_calcolo where anno = anno_input and id_asl = id_asl_input);

	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		id_padre_to_insert := (select id from strutture_asl where id_padre = 8 and id_asl =  id_asl_input);
	--STRUTTURA SEMPLICE
	else  
		id_padre_to_insert := id_padre_input;
	end if;

	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		n_livello_to_insert := 2;
	--STRUTTURA SEMPLICE
	else  
		n_livello_to_insert := 3;
	end if;
	
        insert into strutture_asl(id,           id_padre,       id_asl,       descrizione_lunga,           n_livello,           entered,      entered_by,          modified,     modified_by, enabled,  tipologia_struttura, obsoleto, confermato,           id_strumento_calcolo, codice_interno_fk,                  stato,       anno, stato_all2, stato_all6,data_congelamento) 
        values (             next_id, id_padre_to_insert, id_asl_input, descrizione_lunga_input, n_livello_to_insert, current_timestamp, id_utente_input, current_timestamp, id_utente_input,    true, tipo_struttura_input,    false,       true, id_strumento_calcolo_to_insert,           next_id,     stato_input, anno_input,          0,          0,data_congelamento_input);

	return next_id;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_insert_struttura(integer,  integer, integer,  text, integer,integer,integer,timestamp without time zone )
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.dpat_disabilita_struttura(id_input integer, data_scadenza_input timestamp without time zone, id_asl_input integer, tipo_struttura_input integer, descrizione_lunga_input text, id_utente_input integer, id_padre_input integer)
  RETURNS integer AS
$BODY$
DECLARE
	data_scadenza_old timestamp without time zone;
	codice_interno_fk_old integer;
BEGIN
	select data_scadenza into data_scadenza_old from strutture_asl where id = id_input;
	select codice_interno_fk into codice_interno_fk_old from strutture_asl where id = id_input;
	if (data_scadenza_old is not null ) then /*E STATA ESEGUITA UNA MODIFICA SU UN RECORD CHE HA LA DATA SCADENZA FUTURA*/
		delete from strutture_asl where codice_interno_fk= codice_interno_fk_old and data_scadenza is null;
	end if;

	update strutture_asl set data_scadenza = data_scadenza_input,modified_by=id_utente_input,modified=current_timestamp where id = id_input; 

	return id_input;		 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.dpat_disabilita_struttura(integer, timestamp without time zone, integer, integer, text, integer, integer)
  OWNER TO postgres;

  

CREATE OR REPLACE FUNCTION public.dpat_update_struttura(    id_input integer,    data_scadenza_input timestamp without time zone,    id_asl_input integer,    tipo_struttura_input integer, descrizione_lunga_input text,id_utente_input integer,id_padre_input integer)
  RETURNS integer AS
$BODY$
DECLARE
  anno_old integer;
  id_inserito integer;
  rec_nominativo_old_struttura record;
  next_id integer;
  codice_interno_fk_inserito integer;
  stato_to_pass integer;
  data_congelamento_to_pass timestamp without time zone;
BEGIN
	select anno into anno_old from strutture_asl where id = id_input;
	
	select stato into stato_to_pass from strutture_asl where id = id_input;
	
	select data_congelamento into data_congelamento_to_pass from strutture_asl where id = id_input;
	
	
	perform dpat_disabilita_struttura(id_input, data_scadenza_input, id_asl_input, tipo_struttura_input, descrizione_lunga_input, id_utente_input, id_padre_input);
  
	id_inserito := (select * from dpat_insert_struttura(anno_old, id_asl_input, tipo_struttura_input, descrizione_lunga_input, id_utente_input,id_padre_input,stato_to_pass,data_congelamento_to_pass));

	codice_interno_fk_inserito := (select codice_interno_fk from strutture_asl where id = id_inserito );

	for rec_nominativo_old_struttura in 
		select *
		from
		  dpat_strumento_calcolo_nominativi_
		where (id_dpat_strumento_calcolo_strutture = (select codice_interno_fk  from strutture_asl where id = id_input) or id_dpat_strumento_calcolo_strutture = codice_interno_fk_inserito)
		  and trashed_date is null
		  and (data_scadenza is null or data_scadenza> current_timestamp)
		order by codice_interno_fk
	      loop
		next_id := (select max(id) +1 from dpat_strumento_calcolo_nominativi_ where id <10000000);
		insert into dpat_strumento_calcolo_nominativi_(id,stato,id_anagrafica_nominativo,id_lookup_qualifica,id_dpat_strumento_calcolo_strutture, id_old_anagrafica_nominativo,
			 confermato   , confermato_da,data_conferma, data_scadenza , codice_interno_fk, modified_by, modified ,
			 entered ,entered_by) 
		values(next_id,rec_nominativo_old_struttura.stato,rec_nominativo_old_struttura.id_anagrafica_nominativo, rec_nominativo_old_struttura.id_lookup_qualifica, id_inserito, rec_nominativo_old_struttura.id_old_anagrafica_nominativo, 
			 rec_nominativo_old_struttura.confermato   , rec_nominativo_old_struttura.confermato_da, rec_nominativo_old_struttura.data_conferma, rec_nominativo_old_struttura.data_scadenza , next_id, 291, current_timestamp , 
			current_timestamp , 291)  ;
		
	end loop;
    return id_inserito;		 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_update_struttura(integer, timestamp without time zone, integer, integer, text, integer, integer)
  OWNER TO postgres;

  
 CREATE OR REPLACE FUNCTION public.dpat_insert_nominativo()
  RETURNS integer AS
$BODY$
DECLARE
next_id integer;
id_strumento_calcolo_to_insert integer;
id_padre_to_insert integer;
n_livello_to_insert integer;
id_stato_organigramma integer;
id_stato_to_insert integer;
	 	
BEGIN
	next_id := (select max(id) +1 from strutture_asl where id <10000000);
	id_strumento_calcolo_to_insert := (select id from dpat_strumento_calcolo where anno = anno_input and id_asl = id_asl_input);

	id_stato_organigramma := (select stato from dpat_strumento_calcolo where anno = anno_input and id_asl = id_asl_input);
	id_stato_to_insert:=1;
	if(id_stato_organigramma=2 or id_stato_organigramma=0) then
		id_stato_to_insert := 0;
	end if;
	
	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		id_padre_to_insert := (select id from strutture_asl where id_padre = 8 and id_asl =  id_asl_input);
	--STRUTTURA SEMPLICE
	else  
		id_padre_to_insert := id_padre_input;
	end if;

	--STRUTTURA COMPLESSA o STRUTTURA SEMPLICE DIPARTIMENTALE
	if(tipo_struttura_input =13 or tipo_struttura_input=14) then
		n_livello_to_insert := 2;
	--STRUTTURA SEMPLICE
	else  
		n_livello_to_insert := 3;
	end if;
	
        insert into strutture_asl(id,           id_padre,       id_asl,       descrizione_lunga,           n_livello,           entered,      entered_by,          modified,     modified_by, enabled,  tipologia_struttura, obsoleto, confermato,           id_strumento_calcolo, codice_interno_fk,                  stato,       anno, stato_all2, stato_all6) 
        values (             next_id, id_padre_to_insert, id_asl_input, descrizione_lunga_input, n_livello_to_insert, current_timestamp, id_utente_input, current_timestamp, id_utente_input,    true, tipo_struttura_input,    false,       true, id_strumento_calcolo_to_insert,           next_id,     id_stato_to_insert, anno_input,          0,          0);

	return next_id;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;


 
 
update strutture_asl  set  id_lookup_area_struttura_asl = 4 where anno = 2020 and id_asl = 205 and descrizione_lunga ilike '%AREA TECNICI DELLA PREVENZIONE%';





CREATE OR REPLACE FUNCTION public.dpat_get_stato_avanzamento_organigramma(IN anno_input integer, IN tipologia_input integer, IN asl_input integer)
  RETURNS TABLE(stato text,id_stato integer,asl text,descrizione_struttura text,id_asl integer) AS
$BODY$
DECLARE
BEGIN
	raise info 'DBI dpat_get_stato_avanzamento_organigramma -- inizio chiamata';
	raise info 'DBI dpat_get_stato_avanzamento_organigramma -- input: anno: %, tipologia: %', anno_input, tipologia_input;
	FOR
        stato ,id_stato, asl ,descrizione_struttura ,id_asl 	 
	in
	select case when strutt.stato=1 then 'APERTO' 
	            when strutt.stato=2 THEN 'CHIUSO' 
		    when strutt.stato = 0 then 'CHIUSO-MODIFICATO' 
		    ELSE '' end as stato ,
	       strutt.stato as id_stato,
	       asl.description as asl,
	       strutt.descrizione_lunga as descrizione_struttura,
	       strutt.id_asl 
        from strutture_asl strutt 
        join lookup_site_id asl on asl.code = strutt.id_asl  
	where (anno = anno_input or anno_input is null) and 
	      (tipologia_struttura=tipologia_input or  tipologia_input is null )and 
	      (asl.code=asl_input or  asl_input is null ) and
		  strutt.trashed_date is null and
		  (strutt.data_scadenza is null or strutt.data_scadenza > current_timestamp)
	order by strutt.id_asl 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dpat_get_stato_avanzamento_organigramma( integer, integer, integer)
  OWNER TO postgres;


alter table strutture_asl add column data_congelamento timestamp(3) without time zone;
alter table strutture_asl add column id_utente_congelamento integer;


CREATE OR REPLACE VIEW public.oia_nodo
 AS
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
    a.descrizione_area_struttura,
    a.data_congelamento
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
            area.description AS descrizione_area_struttura,
	    strutture_asl.data_congelamento
           FROM strutture_asl
             LEFT JOIN lookup_area_struttura_asl area ON area.code = strutture_asl.id_lookup_area_struttura_asl) a
  ORDER BY a.codice_interno_fk, a.data_scadenza;

ALTER TABLE public.oia_nodo
    OWNER TO postgres;



CREATE OR REPLACE VIEW public.dpat_strutture_asl
 AS
 WITH RECURSIVE recursetree(id, descrizione, nonno, livello, pathid, pathdes, id_asl, idsc, tipostruttura, codicefk, datascadenza, disabilitato, id_padre, anno, stato, enabled, descrizione_area_struttura_complessa, id_lookup_area_struttura_asl, ui_struttura_foglio_att_iniziale, ui_struttura_foglio_att_finale, id_utente_edit, percentuale_area_a, stato_all2, stato_all6, descrizione_area_struttura, codice_interno_univoco,data_congelamento, entered, modified) AS (
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
            n.codice_interno_univoco,
            n.data_congelamento, 
            n.entered, 
            n.modified
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
            rt.codice_interno_univoco,
            rt.data_congelamento, 
            rt.entered, 
            rt.modified
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
    recursetree.codice_interno_univoco,
    recursetree.data_congelamento, 
    recursetree.entered, 
    recursetree.modified
   FROM recursetree
     LEFT JOIN oia_nodo padre ON padre.codice_interno_fk = recursetree.id_padre AND padre.disabilitata = false
  WHERE recursetree.nonno = 8
  ORDER BY recursetree.id_asl;

ALTER TABLE public.dpat_strutture_asl
    OWNER TO postgres;





--PERMESSI
insert into permission values(13381340,2,'dpat-initNuovoAnno',true,true,true,false,'DPAT INIZIALIZZA NUOVO ANNO',10,true,true,true);
insert into role_permission values(650204, 32, 13381340,true,true,true,false);
insert into role_permission values(650205, 1 , 13381340,true,true,true,false);
insert into permission values(13381341,2,'dpat-nuovaStrutturaComplessa',true,true,true,false,'DPAT NUOVA STRUTTURA COMPLESSA',10,true,true,true);
insert into role_permission values(650206, 32, 13381341,true,true,true,false);
insert into role_permission values(650207, 1 , 13381341,true,true,true,false);




--ALTRI BACHI PRE-ESISTENTI
--Utenti con ruoli non abilitati per risultare come qualifica nello strumento di calcolo venivano mostrati lo stesso

CREATE OR REPLACE VIEW public.lista_utenti_centralizzata AS 
 SELECT a.in_dpat,
    a.in_access,
    a.in_nucleo_ispettivo,
    a.username,
    a.password,
    a.role_id,
    a.last_login,
    a.manager_id,
    ad.site_id AS siteid,
    a.last_ip,
    a.timezone,
    a.startofday AS access_startofday,
    a.endofday AS access_endofday,
    a.expires,
    a.alias,
    a.contact_id AS contact_id_link,
    a.user_id AS access_user_id,
    r.carico_default AS carico_lavoro_annuale,
    a.enabled AS access_enabled,
    a.assistant AS access_assistant,
    a.entered AS access_entered,
    a.enteredby AS access_enteredby,
    a.modified AS access_modified,
    a.modifiedby AS access_modifiedby,
    a.currency,
    a.language,
    a.webdav_password,
    a.hidden,
    a.allow_webdav_access,
    a.allow_httpapi_access,
    a.data_scadenza,
    r.role AS systemrole,
    r.role_type,
    m_usr.enabled AS mgr_enabled,
    b.description AS site_id_name,
    c.contact_id,
    c.user_id,
    c.org_id,
    c.company,
    c.title,
    c.department,
    c.super,
    c.namesalutation,
    c.namelast,
    c.namefirst,
    c.namemiddle,
    c.namesuffix,
    c.assistant,
    c.birthdate,
    c.notes,
    c.site,
    c.locale,
    c.employee_id,
    c.employmenttype,
    c.startofday,
    c.endofday,
    c.entered,
    c.enteredby,
    c.modified,
    c.modifiedby,
    c.enabled,
    c.owner,
    c.custom1,
    c.url,
    c.primary_contact,
    c.employee,
    c.org_name,
    c.access_type,
    c.status_id,
    c.import_id,
    c.information_update_date,
    c.lead,
    c.lead_status,
    c.source,
    c.rating,
    c.comments,
    c.conversion_date,
    c.additional_names,
    c.nickname,
    c.role,
    c.trashed_date,
    c.secret_word,
    c.account_number,
    c.revenue,
    c.industry_temp_code,
    c.potential,
    c.no_email,
    c.no_mail,
    c.no_phone,
    c.no_textmessage,
    c.no_im,
    c.no_fax,
    ad.site_id,
    c.assigned_date,
    c.lead_trashed_date,
    c.employees,
    c.duns_type,
    c.duns_number,
    c.business_name_two,
    c.sic_code,
    c.year_started,
    c.sic_description,
    c.site_id_old,
    c.codice_fiscale,
    c.luogo,
    true AS orgenabled,
    'n.d'::text AS departmentname,
    ''::text AS city,
    ''::text AS postalcode,
    'n.d'::text AS industry_name,
    'n.d'::text AS source_name,
    'n.d'::text AS stage_name,
    'n.d'::text AS rating_name,
    'n.d'::text AS import_name,
    ''::text AS state,
    r.super_ruolo,
    r.descrizione_super_ruolo,
    ad.visibilita_delega,
    a.pec_suap,
    a.callback_suap,
    ''::text AS istat_comune,
    ad.num_registrazione_stab,
    a.callback_suap_ko,
    r.enabled_as_qualifica
   FROM access a
     LEFT JOIN contact_ c ON a.contact_id = c.contact_id
     LEFT JOIN access_ m_usr ON a.manager_id = m_usr.user_id
     LEFT JOIN role r ON a.role_id = r.role_id
     LEFT JOIN access_dati ad ON ad.user_id = a.user_id
     LEFT JOIN lookup_site_id b ON ad.site_id = b.code
  WHERE a.user_id > '-1'::integer AND a.trashed_date IS NULL
UNION
 SELECT a.in_dpat,
    a.in_access,
    a.in_nucleo_ispettivo,
    a.username,
    a.password,
    a.role_id,
    a.last_login,
    a.manager_id,
    ad.site_id AS siteid,
    a.last_ip,
    a.timezone,
    a.startofday AS access_startofday,
    a.endofday AS access_endofday,
    a.expires,
    a.alias,
    a.contact_id AS contact_id_link,
    a.user_id AS access_user_id,
    r.carico_default AS carico_lavoro_annuale,
    a.enabled AS access_enabled,
    a.assistant AS access_assistant,
    a.entered AS access_entered,
    a.enteredby AS access_enteredby,
    a.modified AS access_modified,
    a.modifiedby AS access_modifiedby,
    a.currency,
    a.language,
    a.webdav_password,
    a.hidden,
    a.allow_webdav_access,
    a.allow_httpapi_access,
    a.data_scadenza,
    r.role AS systemrole,
    r.role_type,
    m_usr.enabled AS mgr_enabled,
    b.description AS site_id_name,
    c.contact_id,
    c.user_id,
    c.org_id,
    c.company,
    c.title,
    c.department,
    c.super,
    c.namesalutation,
    c.namelast,
    c.namefirst,
    c.namemiddle,
    c.namesuffix,
    c.assistant,
    c.birthdate,
    c.notes,
    c.site,
    c.locale,
    c.employee_id,
    c.employmenttype,
    c.startofday,
    c.endofday,
    c.entered,
    c.enteredby,
    c.modified,
    c.modifiedby,
    c.enabled,
    c.owner,
    c.custom1,
    c.url,
    c.primary_contact,
    c.employee,
    c.org_name,
    c.access_type,
    c.status_id,
    c.import_id,
    c.information_update_date,
    c.lead,
    c.lead_status,
    c.source,
    c.rating,
    c.comments,
    c.conversion_date,
    c.additional_names,
    c.nickname,
    c.role,
    c.trashed_date,
    c.secret_word,
    c.account_number,
    c.revenue,
    c.industry_temp_code,
    c.potential,
    c.no_email,
    c.no_mail,
    c.no_phone,
    c.no_textmessage,
    c.no_im,
    c.no_fax,
    ad.site_id,
    c.assigned_date,
    c.lead_trashed_date,
    c.employees,
    c.duns_type,
    c.duns_number,
    c.business_name_two,
    c.sic_code,
    c.year_started,
    c.sic_description,
    c.site_id_old,
    c.codice_fiscale,
    c.luogo,
    true AS orgenabled,
    'n.d'::text AS departmentname,
    ''::text AS city,
    ''::text AS postalcode,
    'n.d'::text AS industry_name,
    'n.d'::text AS source_name,
    'n.d'::text AS stage_name,
    'n.d'::text AS rating_name,
    'n.d'::text AS import_name,
    ''::text AS state,
    r.super_ruolo,
    r.descrizione_super_ruolo,
    ad.visibilita_delega,
    a.pec_suap,
    a.callback_suap,
    a.istat_comune,
    ad.num_registrazione_stab,
    a.callback_suap_ko,
    r.enabled_as_qualifica
   FROM access_ext a
     LEFT JOIN contact_ext_ c ON a.contact_id = c.contact_id
     LEFT JOIN access_ext_ m_usr ON a.manager_id = m_usr.user_id
     LEFT JOIN role_ext r ON a.role_id = r.role_id
     LEFT JOIN access_dati ad ON ad.user_id = a.user_id
     LEFT JOIN lookup_site_id b ON ad.site_id = b.code
  WHERE a.user_id > '-1'::integer AND a.trashed_date IS NULL;

ALTER TABLE public.lista_utenti_centralizzata
  OWNER TO postgres;
GRANT ALL ON TABLE public.lista_utenti_centralizzata TO postgres;


update messaggio_mod4 set messaggio = 'L''organigramma pubblicato in questa pagina ha il solo obiettivo di mostrare i nominativi degli utenti, la loro qualifica e l''attribuzione alla struttura, per consentire una corretta visualizzazione nei nuclei ispettivi';

-- View: public.lookup_piano_monitoraggio_con_padri_per_digemon

-- DROP VIEW public.lookup_piano_monitoraggio_con_padri_per_digemon;

CREATE OR REPLACE VIEW public.lookup_piano_monitoraggio_con_padri_per_digemon
 AS
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
            WHEN (piano_att.data_scadenza IS NULL OR piano_att.data_scadenza > CURRENT_TIMESTAMP) AND (ind.data_scadenza IS NULL OR ind.data_scadenza > CURRENT_TIMESTAMP) THEN true
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
            WHEN piano_att.data_scadenza IS NULL OR piano_att.data_scadenza > CURRENT_TIMESTAMP THEN true
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


-- DROP FUNCTION public_functions.dbi_monitoraggio_get_strutture_oia_nodo();
CREATE OR REPLACE FUNCTION public_functions.dbi_monitoraggio_get_strutture_oia_nodo(
	)
    RETURNS TABLE(id integer, id_padre integer, id_asl integer, nome text, descrizione_lunga text, n_livello integer, id_utente integer, entered timestamp without time zone, entered_by integer, modified timestamp without time zone, modified_by integer, trashed_date timestamp without time zone, tipologia_struttura integer, mail text, comune text, indirizzo text, delegato integer, descrizione_comune text, enabled boolean, descrizione_area_struttura text, codice_interno_univoco integer, anno integer) 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
    ROWS 1000
    
AS $BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR id,id_padre,id_asl,nome,descrizione_lunga,n_livello,id_utente,entered,entered_by,modified,modified_by,trashed_Date,tipologia_struttura,mail,comune,indirizzo,delegato,descrizione_comune,enabled,descrizione_area_struttura,codice_interno_univoco,anno
		in
		



select
	n.id,
	n.id_padre,
	n.id_asl,
	n.nome,
	n.descrizione_lunga,
	n.n_livello,
	n.id_utente,
	n.entered,
	n.entered_by,
	n.modified,
	n.modified_by,
	n.trashed_Date,
	n.tipologia_struttura,
	n.mail,
	n.comune,
	n.indirizzo,
	n.delegato,
	n.descrizione_comune,
	case
		when n.data_scadenza is not null
		and n.data_scadenza <current_date then false
		else true
	end as enabled,
	s.descrizione_area_struttura ,
	CASE
		WHEN s.codice_interno_fk > 0 THEN s.codice_interno_fk
		ELSE n.codice_interno_fk
	END AS codice_interno_univoco_uo,
	n.anno,
	n.data_congelamento
from
	oia_nodo n
join dpat_strutture_asl s on
	s.id = n.id
left join dpat_strumento_calcolo sdc on
	sdc.id = s.id_strumento_calcolo
where
	n.n_livello != 1 and n.stato in (2) and 
	(case
		when n.data_scadenza is not null
		and n.data_scadenza <current_date then false
		else true
	end) is true

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$;

ALTER FUNCTION public_functions.dbi_monitoraggio_get_strutture_oia_nodo()
    OWNER TO postgres;




--CAMPI DI SERVIZIO CONFIGURATORE
alter table dpat_indicatore_new add column entered timestamp(3) without time zone;
alter table dpat_indicatore_new add column modified timestamp(3) without time zone;
alter table dpat_indicatore_new add column entered_by integer;
alter table dpat_indicatore_new add column modified_by integer;
alter table dpat_piano_attivita_new add column entered timestamp(3) without time zone;
alter table dpat_piano_attivita_new add column modified timestamp(3) without time zone;
alter table dpat_piano_attivita_new add column entered_by integer;
alter table dpat_piano_attivita_new add column modified_by integer;
alter table dpat_sez_new add column entered timestamp(3) without time zone;
alter table dpat_sez_new add column modified timestamp(3) without time zone;
alter table dpat_sez_new add column entered_by integer;
alter table dpat_sez_new add column modified_by integer;



--select * from  public.dpat_duplica_modello(2019,2020,6725)
CREATE OR REPLACE FUNCTION public.dpat_duplica_modello(
    anno_i integer,
    anno_target_i integer,
    user_id integer
    )
  RETURNS void AS
$BODY$
declare
rec1 record;
rec2 record;
rec3 record;
tempn1 integer;
tempn2 integer;
tempn3 integer;
t      integer;
begin

delete from dpat_indicatore_new where anno = anno_target_i;
delete from dpat_piano_attivita_new where anno = anno_target_i;
delete from dpat_sez_new   where anno = anno_target_i; 

--ciclo su tutti i nodi per l'anno indicato, che sicuramente non sono di template (modelli in stato 1) per hp puo' esserne al piu' uno
--e inserisco gli stessi nodi e che non sono scaduti, ovviamente utilizzando gli id dei nuovi elementi inseriti per mantenere le corrispondenze rispetto ai figli
--e ovviamente mettendoli con stato 2
--aggiorno i sequences per farli partire dagli ultimi oid fisici (e per i logici anche) assegnati dalle vecchie tabelle
--sez





select
  max(cod_raggruppamento)+1
into
  t
from
  dpat_sez_new
;

execute ('ALTER SEQUENCE dpat_sez_new_codice_raggruppamento_seq RESTART WITH ' || t::text );
select
  max(id)+1
into
  t
from
  dpat_sez_new
;

execute ('ALTER SEQUENCE dpat_sez_new_id_seq RESTART WITH ' || t::text );
--piano_attivita
select
  max(cod_raggruppamento)+1
into
  t
from
  dpat_piano_attivita_new
;

execute ('ALTER SEQUENCE dpat_piano_attivita_new_codice_raggruppamento_seq RESTART WITH ' || t::text );
select
  max(id)+1
into
  t
from
  dpat_piano_attivita_new
;

execute ('ALTER SEQUENCE dpat_piano_attivita_new_id_seq RESTART WITH ' || t::text );
--indicatore
select
  max(cod_raggruppamento)+1
into
  t
from
  dpat_indicatore_new
;

execute ('ALTER SEQUENCE dpat_indicatore_new_cod_raggruppamento_seq RESTART WITH ' || t::text );
select
  max(id)+1
into
  t
from
  dpat_indicatore_new
;

execute ('ALTER SEQUENCE dpat_indicatore_new_id_seq RESTART WITH ' || t::text );
--sezione
for rec1 in
select *
from
  dpat_sez_new
where
  anno                    = anno_i
  and stato              != 1 --prendo quelli che sicuramente non sono un template
  and data_scadenza is null loop
  insert into dpat_sez_new(anno,descrizione,ordinamento,data_scadenza,stato,codice_interno,color,entered,entered_by,modified,modified_by) 
		values(anno_target_i,rec1.descrizione,0,null,1,rec1.codice_interno,rec1.color,now(),user_id,now(),user_id)
  returning id
into
  tempn1
;

--piano_attivita
for rec2 in
select *
from
  dpat_piano_attivita_new
where
  anno                    = anno_i
  and stato              != 1
  and id_sezione          = rec1.id
  and data_scadenza is null loop
  insert into dpat_piano_attivita_new(cod_raggruppamento,id_sezione,anno,descrizione,ordinamento,data_scadenza,stato,codice_alias_attivita, codice_esame, codice_interno_piano,codice_interno_attivita,alias_piano,alias_attivita,tipo_attivita,entered,entered_by,modified,modified_by) 
			values(rec2.cod_raggruppamento,tempn1,anno_target_i,rec2.descrizione,rec2.ordinamento,rec2.data_scadenza,1,rec2.codice_alias_attivita, rec2.codice_esame,rec2.codice_interno_piano,rec2.codice_interno_attivita,rec2.alias_piano,rec2.alias_attivita,rec2.tipo_attivita,now(),user_id,now(),user_id)
  returning id
into
  tempn2
;

--indicatore
for rec3 in
select *
from
  dpat_indicatore_new
where
  anno                    = anno_i
  and stato              != 1
  and id_piano_attivita   = rec2.id
  and data_scadenza is null loop
insert into dpat_indicatore_new(cod_raggruppamento,id_piano_attivita,anno,descrizione,ordinamento,data_scadenza,stato,codice_alias_indicatore,codice_esame
				,alias_indicatore
				,extra_gisa, codice_interno_indicatore,codice_interno_univoco_tipo_attivita_gestione_cu, codice_interno_attivita_gestione_cu,codice_interno_piani_gestione_cu,entered,modified,entered_by,modified_by)
				values(rec3.cod_raggruppamento,tempn2,anno_target_i,rec3.descrizione,rec3.ordinamento,rec3.data_scadenza,1,rec3.codice_alias_indicatore,rec3.codice_esame
				,rec3.alias_indicatore
				,rec3.extra_gisa, rec3.codice_interno_indicatore,rec3.codice_interno_univoco_tipo_attivita_gestione_cu, rec3.codice_interno_attivita_gestione_cu,rec3.codice_interno_piani_gestione_cu,now(),now(),user_id,user_id);
end loop;
end loop;
end loop;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_duplica_modello(integer, integer)
  OWNER TO postgres;

 

   --Da capire se funziona la dbi chiamandola a mano
CREATE OR REPLACE FUNCTION public.dpat_insert_piano_attivita_before_or_after(IN id_piano integer, IN descrizione text, IN codice_esame text, IN asl text, IN tipo_attivita_input text, IN alias text,IN tipo_inserimento text,IN  stato_input text, IN stato_incrementa_ordine text,IN anno_input integer, IN codice_alias_input text, IN user_id integer)
  RETURNS integer AS
$BODY$
declare
id_sezione_piano integer;
ordine integer;
stato_to_pass integer;
id_ret integer;
id_dummy_child_inserito integer;
begin
	raise info 'DBI dpat_insert_piano_attivita_before_or_after -- inizio chiamata';
	raise info 'DBI dpat_insert_piano_attivita_before_or_after -- input:  id_piano: % ,  descrizione : %,  codice_esame : %, asl : %,  tipo_attivita_input : %,  alias : %,tipo_inserimento : %,  stato_input : %,  stato_incrementa_ordine : %, anno_input: %',  id_piano ,  descrizione ,  codice_esame , asl ,  tipo_attivita_input ,  alias ,tipo_inserimento ,  stato_input ,  stato_incrementa_ordine , anno_input ;
	
	id_sezione_piano := (select id_sezione from dpat_piano_attivita_new where id = id_piano);
	raise info 'DBI dpat_insert -- id_sezione_piano = %, ', id_sezione_piano;
	ordine := (select d.ordinamento from dpat_piano_attivita_new d where d.id = id_piano);
	raise info 'DBI dpat_insert -- ordine = %, ', ordine;
	stato_to_pass := stato_input::integer;
	if(stato_to_pass<>1) then
		raise info 'DBI dpat_insert_piano_attivita_before_or_after -- entrato nell''if stato_to_pass != 1' ;
		stato_to_pass := (select * from dpat_get_stato_dopo_modifica(anno_input));
		raise info 'DBI dpat_insert_piano_attivita_before_or_after -- stato_to_pass = %, ', stato_to_pass;
	end if;
			 
	if(lower(tipo_inserimento) != 'up') then
		raise info 'DBI dpat_insert_piano_attivita_before_or_after -- entrato nell''if tipo_inserimento != up' ;
		ordine := ordine+1;
		raise info 'DBI dpat_insert_piano_attivita_before_or_after -- ordine = %, ', ordine;
	end if;
			 
	insert into dpat_piano_attivita_new(id_sezione,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_attivita,tipo_attivita,alias_piano,codice_interno_attivita,codice_interno_piano,codice_alias_attivita,entered,modified,entered_by,modified_by)
	values(id_sezione_piano,anno_input,descrizione,ordine,NULL,stato_input::integer,codice_esame,alias,tipo_attivita_input,alias,null,null,codice_alias_input,now(),now(),user_id,user_id) returning id into id_ret;
				 
	update dpat_piano_attivita_new set modified_by = user_id, modified = now(), ordinamento = ordinamento+1 where ordinamento >= ordine and id<>id_ret and id_sezione = id_sezione_piano and anno = anno_input ;

	id_dummy_child_inserito := (select * from dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito(anno_input,user_id));
	raise info 'DBI dpat_insert_piano_attivita_before_or_after -- id_dummy_child_inserito = %, ', id_dummy_child_inserito;
	raise info 'DBI dpat_insert_piano_attivita_before_or_after -- id_ret: %', id_ret;
	return id_ret;	
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  ALTER FUNCTION public.dpat_insert_piano_attivita_before_or_after(integer,  text, text, text, text,          text,            text, text, text, integer, text)                                         
 OWNER TO postgres;


 
CREATE OR REPLACE FUNCTION public.dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito(IN anno_in integer,IN user_id integer)
  RETURNS integer AS
$BODY$
declare
id_piano_attivita_nuovo integer;
numero_figli integer;
stato integer;
id_ret integer;
begin
        raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- inizio chiamata';
	raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- input:  anno: % ',  anno_in;
	
	id_piano_attivita_nuovo := (select d.id             from dpat_piano_attivita_new d where d.id = (select max(d2.id) from dpat_piano_attivita_new d2 where d2.anno = anno_in  ));  
        raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- id_piano_attivita_nuovo: %', id_piano_attivita_nuovo;
	
	numero_figli := (select count(*) from dpat_indicatore_new d where d.id_piano_attivita = id_piano_attivita_nuovo );
	raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- numero_figli: %', numero_figli;
			
	if(numero_figli > 0) then
		raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- entrato nell''if numero_figli > 0' ;
		raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- output: -1';
		return -1;
	end if;
			 
	stato :=  (select * from dpat_get_stato_dopo_modifica(anno_in));
	raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- stato: %', stato;
			 
	insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_indicatore, codice_interno_indicatore,codice_interno_univoco_tipo_attivita_gestione_cu, codice_interno_attivita_gestione_cu,codice_interno_piani_gestione_cu,entered,modified,entered_by,modified_by)
	values(id_piano_attivita_nuovo, anno_in, 'INDICATORE DI DEFAULT DA SOSTITUIRE', 0, NULL, stato,'','DEFAULT', null, null, null, null,now(),now(),user_id,user_id) returning id into id_ret;
        raise info 'DBI dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito -- output: %', id_ret;
	return id_ret;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_insert_dummy_child_per_ultimo_piano_attivita_inserito(integer)
 OWNER TO postgres;




  --Da capire se funziona la dbi chiamandola a mano
CREATE OR REPLACE FUNCTION public.dpat_insert_indicatore_before_or_after(IN id_indicatore integer, IN descrizione text, IN codice_esame text, IN asl text, IN alias text,IN tipo_inserimento text,IN  stato_input text, IN stato_incrementa_ordine text, IN codice_alias text,IN user_id integer)
  RETURNS integer AS
$BODY$
declare
idpianoattivita integer;
id_ret integer;
anno_indicatore integer;
ordine integer;
begin
	 raise info 'DBI dpat_insert_indicatore_before_or_after -- inizio chiamata';
	 raise info 'DBI dpat_insert_indicatore_before_or_after -- input:  id_indicatore: % ,  descrizione: %  ,  codice_esame: % , asl: % , alias: % , tipo_inserimento: % ,  stato_input: % ,  stato_incrementa_ordine: % , codice_alias: %  ',  id_indicatore ,  descrizione ,  codice_esame, asl, alias, tipo_inserimento,  stato_input,  stato_incrementa_ordine, codice_alias ;
	 idpianoattivita := (select d.id_piano_attivita from dpat_indicatore_new d where d.id = id_indicatore);
         raise info 'DBI dpat_insert_indicatore_before_or_after -- idpianoattivita: %', idpianoattivita;
         anno_indicatore := (select d.anno from dpat_indicatore_new d where d.id = id_indicatore);
         raise info 'DBI dpat_insert_indicatore_before_or_after -- anno_indicatore: %', anno_indicatore;
	 ordine := (select d.ordinamento from dpat_indicatore_new d where d.id = id_indicatore);
         raise info 'DBI dpat_insert_indicatore_before_or_after -- ordine: %', ordine;
	 
	 
	 if(lower(tipo_inserimento) != 'up') then
		raise info 'DBI dpat_insert_indicatore_before_or_after -- entrato nell''if tipo_inserimento != up' ;
		ordine := ordine+1;
		raise info 'DBI dpat_insert_indicatore_before_or_after -- ordine: %', ordine;
	 end if;

	 update dpat_indicatore_new set modified=now(), modified_by = user_id, ordinamento = ordinamento+1 where ordinamento >= ordine and id_piano_attivita = idpianoattivita and anno = anno_indicatore  ;

	 insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,stato,codice_esame,alias_indicatore,codice_alias_indicatore,entered,modified,entered_by,modified_by)
         values(idpianoattivita,anno_indicatore,descrizione,ordine,stato_input::integer,codice_esame,alias,codice_alias,now(),now(),user_id,user_id) returning id into id_ret;

         raise info 'DBI dpat_insert_indicatore_before_or_after -- output: %', id_ret;
	 return id_ret;
	
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_insert_indicatore_before_or_after(  integer,   text,  text,  text, text, text, text, text, text)
 OWNER TO postgres;



 
--Da capire se funziona la dbi chiamandola a mano
CREATE OR REPLACE FUNCTION public.dpat_insert_indicatore(IN id_indicatore integer, IN descrizione_input text, IN codice_esame_input text, IN asl text, IN alias_input text, IN codice_alias_input text, IN stato_input integer, IN tipo_inserimento text, IN anno_input integer, IN id_piano integer, IN user_id integer)
  RETURNS integer AS
$BODY$
declare
codRaggrupp text;
stato integer;
ordine integer;
stato_to_pass text;
id_ret integer;
begin
	 raise info 'DBI dpat_insert_indicatore -- inizio chiamata';
	 raise info 'DBI dpat_insert_indicatore -- input:   id_indicatore:% ,  descrizione_input:%,  codice_esame_input:%,  asl:%, alias_input:%, codice_alias_input:%, stato_input:%, tipo_inserimento:%, anno_input:%, id_piano:%',  id_indicatore ,  descrizione_input,  codice_esame_input,  asl, alias_input, codice_alias_input, stato_input, tipo_inserimento, anno_input, id_piano;
	 
	 codRaggrupp := (select cod_raggruppamento from dpat_indicatore_new where id = id_indicatore);
	 raise info 'DBI dpat_insert_indicatore -- codRaggrupp: %', codRaggrupp;
	 
	 stato := stato_input;
	 raise info 'DBI dpat_insert_indicatore -- stato: %', stato;
	 if(stato!='1') then
	    raise info 'DBI dpat_insert_indicatore -- entrato nell''if stato!=1' ;
	    stato := dpat_get_stato_dopo_modifica(anno_input);
	    raise info 'DBI dpat_insert_indicatore -- stato: %', stato;
         end if;

	 if(tipo_inserimento!=null) then  
	        raise info 'DBI dpat_insert_indicatore -- entrato nell''if tipo_inserimento!=null' ;
		tipo_inserimento := lower(tipo_inserimento);
		raise info 'DBI dpat_insert_indicatore -- tipo_inserimento: %', tipo_inserimento;
	 else
		raise info 'DBI dpat_insert_indicatore -- entrato nell''else tipo_inserimento=null' ;
		tipo_inserimento :='down';
		raise info 'DBI dpat_insert_indicatore -- tipo_inserimento: %', tipo_inserimento;
	 end if;
		 
	 ordine := (select d.ordinamento from dpat_indicatore_new d where  d.id = id_indicatore);
	 raise info 'DBI dpat_insert_indicatore -- ordine: %', ordine;

	 if(tipo_inserimento != 'up') then
		raise info 'DBI dpat_insert_indicatore -- entrato nell''if tipo_inserimento != up' ;
		ordine := ordine+1;
	        raise info 'DBI dpat_insert_indicatore -- ordine: %', ordine;
	 end if;

	 case when stato =null then stato_to_pass :='-1' ;
	 else stato_to_pass :=stato ;
	 end case;
	 raise info 'DBI dpat_insert_indicatore -- stato_to_pass: %', stato_to_pass;
	 
	 update dpat_indicatore_new set modified=now(), modified_by = user_id, ordinamento = ordinamento+1 where ordinamento >= ordine and id_piano_attivita = id_piano and anno = anno_input ;

	 insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,stato,codice_esame,alias_indicatore,codice_alias_indicatore,entered,modified,entered_by,modified_by)
	 values(id_piano,anno_input,descrizione_input,ordine,stato_input,codice_esame_input,alias_input,codice_alias_input,now(),now(),user_id,user_id) returning id into id_ret;	

         raise info 'DBI dpat_insert_indicatore -- output: %', id_ret;
	 return id_ret;	
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




   --Da capire se funziona la dbi chiamandola a mano
   CREATE OR REPLACE FUNCTION public.dpat_insert_dummy(IN anno_input integer, IN id_piano integer,IN stato_input integer, IN user_id integer)
  RETURNS integer AS
$BODY$
DECLARE
tot_figli integer; 
stato_padre integer;
stato_to_pass integer;
id_fisico_dummy integer;
id_ret integer;	
BEGIN
	raise info 'DBI dpat_insert_dummy -- inizio chiamata';
	raise info 'DBI dpat_insert_dummy -- input: anno: %, id_piano: %, stato_input: %  ', anno_input, id_piano, stato_input;
	
	tot_figli := (select count(*) from dpat_get_indicatori(id_piano, null, false));

	raise info 'DBI dpat_insert_dummy -- tot_figli: %', tot_figli;
	stato_padre := (select max(stato) from dpat_indicatore_new where id_piano_attivita = id_piano and data_scadenza is NULL );
	raise info 'DBI dpat_insert_dummy -- stato_padre: %', stato_padre;
	
	if(tot_figli > 0) then
		raise info 'DBI dpat_insert_dummy -- entrato nell''if tot_figli > 0' ;
		raise info 'DBI dpat_insert_dummy -- output: -1';
		return -1;
	end if;
	
	stato_to_pass := stato_input;
	if(stato_input!=1) then
		raise info 'DBI dpat_insert_dummy -- entrato nell''if stato_input!=1' ;
		stato_to_pass := case when stato_padre = 1 then 1 else dpat_get_stato_dopo_modifica(anno_input) end;
		raise info 'DBI dpat_insert_dummy -- stato_to_pass: %', stato_to_pass;
	end if;
	 
	insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,stato,codice_esame,alias_indicatore,entered,modified,entered_by,modified_by) values(id_piano, anno_input, 'INDICATORE DI DEFAULT DA SOSTITUIRE', 0, stato_to_pass,'','DEFAULT',now(),now(),user_id,user_id) returning id into id_ret;
        raise info 'DBI dpat_insert_dummy -- output: %', id_ret;
	return id_ret;
		
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
alter FUNCTION public.dpat_insert_dummy(integer, integer,integer)
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public.dpat_disabilita_piano_attivita(IN dpat_is_congelato boolean, IN id_in integer, IN anno integer, IN user_id integer)
RETURNS void AS
$BODY$
declare
stato_to_pass text;
stati_congelato text;
stati_pre_congelato text;
id_indicatore integer;
begin
	raise info 'DBI dpat_disabilita_piano_attivita -- inizio chiamata';
        raise info 'DBI dpat_disabilita_piano_attivita -- input: dpat_is_congelato: %, id_in: %, anno: %', dpat_is_congelato, id_in, anno;
	stati_congelato := '0,2';
	stati_pre_congelato := '1';
	stato_to_pass := (select * from dpat_get_stato_dopo_modifica(anno));
	raise info 'DBI dpat_elimina -- stato_to_pass: %',stato_to_pass;

	if(dpat_is_congelato) then
		raise info 'DBI dpat_elimina -- entrato nell''if dpat_is_congelato' ;
		update dpat_piano_attivita_new set modified=now(), modified_by=user_id, data_scadenza = CURRENT_TIMESTAMP , stato = stato_to_pass::integer where id = id_in; 
		FOR
			id_indicatore  
			in
			select id from dpat_get_indicatori(id_in,case when dpat_is_congelato then stati_congelato else stati_pre_congelato end  ,false)
			LOOP
				perform dpat_disabilita_indicatore(id_indicatore,anno, stato_to_pass::integer,stati_congelato, user_id);
			END LOOP;
	else
		raise info 'DBI dpat_elimina -- entrato nell''else dpat_is_congelato is false' ;
		FOR
			id_indicatore  
			in
			select id from dpat_get_indicatori(id_in,case when dpat_is_congelato then stati_congelato else stati_pre_congelato end  ,false)
			LOOP
				perform dpat_delete_indicatore(id_indicatore);
			END LOOP;

		perform dpat_delete_piano_attivita(id_in);
			
	end if;
	
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_disabilita_piano_attivita(boolean,integer,integer)
  OWNER TO postgres;


  
CREATE OR REPLACE FUNCTION public.dpat_disabilita_indicatore(IN id_in integer,IN anno integer, IN stato_new integer, IN stato_old text, IN user_id integer)
RETURNS void AS
$BODY$
declare
begin
	raise info 'DBI dpat_disabilita_indicatore -- inizio chiamata';
        raise info 'DBI dpat_disabilita_indicatore -- input: id_in: %, anno: %, stato_new: %, stato_old: %', id_in, anno, stato_new, stato_old;
	update dpat_indicatore_new set modified=now(), modified_by=user_id, data_scadenza = CURRENT_TIMESTAMP, stato = stato_new where id = id_in;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_disabilita_indicatore(integer,integer,integer,text)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.dpat_insert( IN id_indicatore integer, IN tipo_inserimento text, IN id_piano integer,IN descrizione text,IN codice_esame text,IN asl text, IN tipo_attivita text,IN alias text, IN codice_alias text, IN tipo_piano_att_ind text, IN anno integer, IN user_id integer)
	--da capire cosa restituire
  RETURNS void AS
$BODY$
declare
num_indicatori integer;
dpat_is_congelato boolean;
stati_congelato text;
stati_pre_congelato text;
id_indicatore_riferimento_per_inserimento integer;
id_indicatore_inserito integer;
id_piano_inserito integer;
begin
	raise info 'DBI dpat_insert -- inizio chiamata';
	raise info 'DBI dpat_insert -- input: id_indicatore: %, tipo_inserimento: %, id_piano: %, descrizione: %, codice_esame: %, asl: %, tipo_attivita: %, alias: %, codice_alias: %, tipo_piano_att_ind: %, anno: %', id_indicatore, tipo_inserimento,id_piano,descrizione, codice_esame, asl, tipo_attivita, alias, codice_alias, tipo_piano_att_ind, anno;
	dpat_is_congelato := ( select *  from dpat_is_congelato(anno));
	raise info 'DBI dpat_insert -- dpat_is_congelato = %, ', dpat_is_congelato;
	--da capire come gestire tramite oggetto array
	stati_congelato := '0,2';
	stati_pre_congelato := '1';

	if lower(tipo_inserimento)= 'firstchild' then 
		raise info 'DBI dpat_insert -- entrato nell''if firstchild' ;
		num_indicatori := (select count(*) from dpat_get_indicatori(id_piano,case when dpat_is_congelato then stati_congelato else '1' end  ,false));
		raise info 'DBI dpat_insert -- num_indicatori = %, ', num_indicatori;
		if(num_indicatori=0) then
			raise info 'DBI dpat_insert -- entrato nell''if num_indicatori=0' ;
			id_indicatore_riferimento_per_inserimento := (select * from dpat_insert_dummy(anno, id_piano,case when dpat_is_congelato then null else 1 end, user_id));
			raise info 'DBI dpat_insert -- id_indicatore_riferimento_per_inserimento = %, ', id_indicatore_riferimento_per_inserimento;
		else
			raise info 'DBI dpat_insert -- entrato nell''else num_indicatori!=0' ;
			id_indicatore_riferimento_per_inserimento := (select id from dpat_get_indicatori(id_piano,case when dpat_is_congelato then stati_congelato else '1' end  ,false) order by ordinamento desc limit 1);
			raise info 'DBI dpat_insert -- id_indicatore_riferimento_per_inserimento = %, ', id_indicatore_riferimento_per_inserimento;
		end if;

		id_indicatore_inserito := (select * from dpat_insert_indicatore(id_indicatore_riferimento_per_inserimento, descrizione, codice_esame, asl, alias, codice_alias,case when dpat_is_congelato then null else 1 end, 'down',anno,id_piano,user_id));
		raise info 'DBI dpat_insert -- id_indicatore_inserito = %, ', id_indicatore_inserito;

		perform dpat_delete_dummy_brother(id_indicatore_riferimento_per_inserimento, case when dpat_is_congelato then stati_congelato else stati_pre_congelato end, 'down',anno,id_piano);
		
		--Da capire, da fare nella classe Java ma si deve vedere come passarlo		
		--context.getRequest().setAttribute("IndicatoreNewDPat", toInsert);
	else
		raise info 'DBI dpat_insert -- entrato nell''else (not firstchild)' ;
		if(lower(tipo_piano_att_ind) = 'dpat_attivita') then
			raise info 'DBI dpat_insert -- entrato nell''if tipo_piano_att_ind = dpat_attivita' ;
			id_piano_inserito := (select * from dpat_insert_piano_attivita_before_or_after(id_piano, descrizione, codice_esame, asl, tipo_attivita, alias,tipo_inserimento,(select * from dpat_get_stato(anno))::text ,(select * from dpat_get_stato(anno))::text,anno, codice_alias,user_id));
			raise info 'DBI dpat_insert -- id_indicatore_inserito = %, ', id_piano_inserito;
			--Da capire, da fare nella classe Java ma si deve vedere come passarlo		
			--context.getRequest().setAttribute("PianoAttivitaNewDPat", toInsert );
		else 
		
			raise info 'DBI dpat_insert -- entrato nell''else tipo_piano_att_ind != dpat_attivita' ;
			id_indicatore_inserito := (select * from dpat_insert_indicatore_before_or_after(id_indicatore, descrizione, codice_esame, asl, alias,tipo_inserimento,(select * from dpat_get_stato(anno))::text ,(select * from dpat_get_stato(anno))::text, codice_alias,user_id));
			raise info 'DBI dpat_insert -- id_indicatore_inserito = %, ', id_indicatore_inserito;
			perform dpat_delete_dummy_brother(id_indicatore, case when dpat_is_congelato then stati_congelato else stati_pre_congelato end, 'down',anno,id_piano);
	
			--Da capire come gestire
			--context.getRequest().setAttribute("IndicatoreNewDPat", toInsert);

		end if;
				
	end if;			


	perform refresh_motivi_cu(anno,true);

			
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
 ALTER FUNCTION public.dpat_insert(  integer,              text,   integer,              text,              text,              text,              text,               text,             text, text, integer)
  OWNER TO postgres;


  
CREATE OR REPLACE FUNCTION public.dpat_elimina(IN tipo_classe text, IN anno integer, IN id integer,IN user_id integer)
	--da capire cosa restituire
  RETURNS void AS
$BODY$
declare
dpat_is_congelato boolean;
stato_to_pass text;
stati_congelato text;
	
begin
	stati_congelato := '0,2';
	raise info 'DBI dpat_elimina -- inizio chiamata';
        raise info 'DBI dpat_elimina -- input: tipo_classe: %, anno: %, id: % ', tipo_classe, anno, id;
      
	stato_to_pass := (select * from dpat_get_stato_dopo_modifica(anno));
        raise info 'DBI dpat_elimina -- stato_to_pass: %',stato_to_pass;
	dpat_is_congelato := ( select *  from dpat_is_congelato(anno));
        raise info 'DBI dpat_elimina -- dpat_is_congelato: %',dpat_is_congelato;
	
	if (tipo_classe = 'dpat_attivita') then
		raise info 'DBI dpat_elimina -- entrato nell''if tipo_classe = dpat_attivita' ;
		perform dpat_disabilita_piano_attivita(dpat_is_congelato,id,anno,user_id);

	else
		if(dpat_is_congelato) then
			raise info 'DBI dpat_elimina -- entrato nell''if dpat_is_congelato' ;
			perform dpat_disabilita_indicatore(id,anno, stato_to_pass::integer,stati_congelato,user_id);
		else
			perform dpat_delete_indicatore(id);

		end if;				
	end if;

	perform refresh_motivi_cu(anno,true);	

			
end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dpat_elimina(text,integer,integer)
  OWNER TO postgres;
