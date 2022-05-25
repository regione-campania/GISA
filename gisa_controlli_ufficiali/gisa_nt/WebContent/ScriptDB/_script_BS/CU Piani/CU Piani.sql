-- Chi: Bartolo Sansone
-- Quando: 11/12/2018
-- Cosa: Configurazione cu piani per operatore

-- View: public.controlli_ufficiali_motivi_ispezione_vista

-- DROP VIEW public.controlli_ufficiali_motivi_ispezione_vista;

CREATE OR REPLACE VIEW public.controlli_ufficiali_motivi_ispezione_vista AS 
 SELECT ti.code AS id_tipo_ispezione,
    ti.description AS descrizione_tipo_ispezione,
    ti.codice_interno AS codice_int_tipo_ispe,
    p.code AS id_piano,
    p.description AS descrizione_piano,
    p.codice_interno AS codice_int_piano,
    COALESCE(p.ordinamento, ti.ordinamento) AS ordinamento,
    COALESCE(p.ordinamento_figli, ti.ordinamento_figli) AS ordinamento_figli,
    ti.level AS livello_tipo_ispezione,
        CASE
            WHEN p.code > 0 THEN p.data_scadenza
            ELSE ti.data_scadenza
        END AS data_scadenza,
        CASE
            WHEN p.code > 0 THEN p.codice_interno_ind::text
            ELSE ti.codice_interno_ind || ''::text
        END AS codice_interno_ind,
    COALESCE(p.anno, ti.anno) AS anno,
    COALESCE(p.data_scadenza, ti.data_scadenza) AS "coalesce",
    COALESCE(p.codice, ti.codice) AS codice
   FROM lookup_tipo_ispezione ti
     LEFT JOIN lookup_piano_monitoraggio p ON ti.codice_interno ~~* p.codice_interno_tipo_ispezione AND p.enabled
  WHERE ti.enabled
  ORDER BY p.ordinamento, p.ordinamento_figli;

ALTER TABLE public.controlli_ufficiali_motivi_ispezione_vista
  OWNER TO postgres;

drop table controlli_ufficiali_motivi_ispezione;
create table controlli_ufficiali_motivi_ispezione as select * from controlli_ufficiali_motivi_ispezione_vista;


CREATE TABLE cu_motivi_operatori (id serial primary key, codice_piano_o_attivita text, tipologia_operatore integer);
--Inserisco i motivi C20 solo per le acque
insert into cu_motivi_operatori (codice_piano_o_attivita, tipologia_operatore) select codice, 14 from controlli_ufficiali_motivi_ispezione where codice in ('00078.00', '00078.02');

 CREATE TABLE cu_motivi_esclusivi (id serial primary key, tipologia_operatore integer, codice1 text, codice2 text, notes text);
 insert into cu_motivi_esclusivi (tipologia_operatore, codice1, codice2, notes) values (14, '00078.00', '00078.02', 'Piani acque di rete selezionabili in modo esclusivo');
 
  -- Function: public.get_motivi_cu(integer)

-- DROP FUNCTION public.get_motivi_cu(integer);

CREATE OR REPLACE FUNCTION public.get_motivi_cu(
    IN _tipologiaoperatore integer)
  RETURNS TABLE(cod_interno_ind text, id_tipo_ispezione integer, descrizione_tipo_ispezione character varying, codice_int_tipo_ispe text, id_piano integer, descrizione_piano character varying, codice_int_piano integer, ordinamento integer, ordinamento_figli integer, livello_tipo_ispezione integer, data_scadenza timestamp without time zone, codice_interno_ind text, anno integer, codice text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR cod_interno_ind , id_tipo_ispezione , descrizione_tipo_ispezione  , codice_int_tipo_ispe , id_piano , descrizione_piano  , codice_int_piano , ordinamento , ordinamento_figli , livello_tipo_ispezione , data_scadenza  , codice_interno_ind , anno , codice 
	
	in
	
        select a.cod_interno_ind , a.id_tipo_ispezione , a.descrizione_tipo_ispezione  , a.codice_int_tipo_ispe , a.id_piano , a.descrizione_piano  , a.codice_int_piano , a.ordinamento , a.ordinamento_figli , a.livello_tipo_ispezione , a.data_scadenza  , a.codice_interno_ind , a.anno ,a.codice  from (  select * from (select distinct on (a.codice_interno_ind) a.codice_interno_ind as cod_interno_ind ,a.* from (select c1.* from controlli_ufficiali_motivi_ispezione c1 where  c1.data_scadenza > (now() + '1 day'::interval) OR c1.data_scadenza IS NULL ) a order by cod_interno_ind,data_scadenza )bb  union  select * from (select   a.codice_interno_ind as cod_interno_ind ,a.* from (select c.* from controlli_ufficiali_motivi_ispezione c where   (c.data_scadenza > (now() + '1 day'::interval) OR c.data_scadenza IS NULL) and (c.codice_interno_ind is null or c.codice_interno_ind = '0') ) a order by cod_interno_ind,data_scadenza )bb ) a 
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
ALTER FUNCTION public.get_motivi_cu(integer)
  OWNER TO postgres;

  
  
CREATE OR REPLACE FUNCTION public.get_cu_motivi_compatibilita(IN _tipologiaoperatore integer, IN _codice1 text, IN _codice2 text)
  RETURNS text AS
$BODY$
DECLARE  
 msg text;
nome1 text;
nome2 text;
idcompatibilita integer;
BEGIN 

msg := '';
nome1 := '';
nome2 := '';
idcompatibilita := -1;

select COALESCE(descrizione_piano, descrizione_tipo_ispezione) into nome1 from controlli_ufficiali_motivi_ispezione where codice = _codice1;
select COALESCE(descrizione_piano, descrizione_tipo_ispezione) into nome2 from controlli_ufficiali_motivi_ispezione where codice = _codice2;

select id into idcompatibilita from cu_motivi_esclusivi where tipologia_operatore = _tipologiaoperatore and ((codice1 = _codice1 and codice2 = _codice2) or (codice1 = _codice2 and codice2 = _codice1));

IF (idcompatibilita>0) THEN
msg := 'Errore nella selezione del motivo ispezione. I seguenti motivi non possono essere selezionati insieme:    '||nome1||'     e     '||nome2;
END IF;
return msg;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



