--Req. 1
CREATE TYPE stato_punto_prelievo AS ENUM('ATTIVO', 'INATTIVO');

update gestori_acque_punti_prelievo set stato = 'ATTIVO' where id = 1112
select distinct stato from gestori_acque_punti_prelievo

ALTER TABLE gestori_acque_punti_prelievo ALTER COLUMN stato TYPE stato_punto_prelievo USING stato::stato_punto_prelievo;

CREATE SEQUENCE public.gestori_acque_punti_prelievo_log_modifica_stato_id_seq
  INCREMENT 1
  MINVALUE 1
  START 1;
ALTER TABLE public.gestori_acque_punti_prelievo_log_modifica_stato_id_seq
  OWNER TO postgres;



CREATE TABLE public.gestori_acque_punti_prelievo_log_modifica_stato
(
  id integer NOT NULL DEFAULT nextval('gestori_acque_punti_prelievo_log_modifica_stato_id_seq'::regclass),
  stato_precedente text,
  stato_attuale text,
  id_punto_prelievo integer,
  data_modifica timestamp without time zone DEFAULT now(),
  utente_modifica integer,
  CONSTRAINT gestori_acque_punti_prelievo_log_modifica_stato_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.gestori_acque_punti_prelievo_log_modifica_stato
  OWNER TO postgres;

--Req.2
alter table controlli_punti_di_prelievo_acque_rete add column alfa integer;
alter table controlli_punti_di_prelievo_acque_rete add column beta integer;
alter table controlli_punti_di_prelievo_acque_rete add column dose integer;
alter table controlli_punti_di_prelievo_acque_rete add column radon integer;
alter table controlli_punti_di_prelievo_acque_rete add column trizio integer;
alter table controlli_punti_di_prelievo_acque_rete add column tipo_decreto character varying(2);

--Req. 3
--Azzeramento punti prelievo
delete from gestori_acque_punti_prelievo;
delete from gestori_acque_log_import;
ALTER TABLE public.gestori_acque_punti_prelievo drop CONSTRAINT gestori_acque_punti_prelievo_denominazione_key;
ALTER TABLE public.gestori_acque_punti_prelievo ADD CONSTRAINT gestori_acque_punti_prelievo_codice_key UNIQUE(codice_gisa);
ALTER TABLE gestori_acque_punti_prelievo ALTER COLUMN stato TYPE stato_punto_prelievo USING stato::stato_punto_prelievo;

ALTER TABLE gestori_acque_punti_prelievo add COLUMN codice_gisa text;
ALTER TABLE gestori_acque_punti_prelievo ALTER COLUMN codice_gisa SET NOT NULL;

--Req. 4
--Funzioni DIGEMON
CREATE OR REPLACE FUNCTION public_functions.dbi_get_punti_prelievo(IN id_asl_in integer)
  RETURNS TABLE(id integer,denominazione text,id_asl integer,code_tipologia integer,codice text,id_gestore integer,data_inserimento timestamp without time zone,stato text,codice_gisa text,
       nome_gestore text, 
       indirizzo_via text,
       latitudine double precision,
       longitudine double precision, 
       descrizione_tipologia text, 
       id_comune integer,
       descrizione_comune text,
       descrizione_asl text) AS
$BODY$
BEGIN
		FOR id,
denominazione,
id_asl,
code_tipologia,
codice,
id_gestore,
data_inserimento,
stato,
codice_gisa ,
       nome_gestore , 
       indirizzo_via ,
       latitudine,
       longitudine , 
       descrizione_tipologia, 
       id_comune,
       descrizione_comune ,
       descrizione_asl
		in
		select pp.id,
pp.denominazione,
pp.id_asl,
pp.id_lookup_tipologia as code_tipologia,
pp.codice,
pp.id_gestore,
pp.data_inserimento,
pp.stato,
pp.codice_gisa ,
       gest.nome as nome_gestore , 
       ind.desc_via as indirizzo_via ,
       ind.latitudine,
       ind.longitudine , 
       tipologia.description as descrizione_tipologia, 
       comuni1.id as id_comune,
       comuni1.nome as descrizione_comune ,
       asl.description as descrizione_asl
from gestori_acque_punti_prelievo pp 
join gestori_acque_gestori gest on  gest.id = pp.id_gestore 
left join lookup_site_id asl on asl.code = pp.id_asl 
left join  gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia 
left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo 
left join comuni1 on ind.id_comune = comuni1.id 
where id_asl_in =-1 or id_asl_in = asl.code	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_punti_prelievo(integer)
  OWNER TO postgres;
  
  
  
-- Function: public_functions.dbi_get_controlli_acque_rete(integer, text)

-- DROP FUNCTION public_functions.dbi_get_controlli_acque_rete(integer, text);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_acque_rete(
    IN asl_in integer,
    IN tipo_decreto_in text)
  RETURNS TABLE(ticketid integer, entered timestamp without time zone, enteredby integer, modified timestamp without time zone, modifiedby integer, closed timestamp without time zone, assigned_date timestamp without time zone, temperatura text, cloro text, ore text, prot_routine boolean, prot_verifica boolean, prot_replica_micro boolean, prot_replica_chim boolean, 
  prot_radioattivita boolean, prot_ricerca_fitosanitari boolean, alfa text, beta text, radon text, dose text, trizio text, tipo_decreto text, desc_asl text, id_punto_prelievo integer) AS
$BODY$
BEGIN
		FOR  ticketid,
       entered,
       enteredby,
       modified,
       modifiedby,
       closed,
       assigned_Date,
       temperatura,
       cloro,
       ore,
       prot_routine,
       prot_verifica,
       prot_replica_micro,
       prot_replica_chim,
       prot_radioattivita,
       prot_ricerca_fitosanitari,
       alfa,
       beta,
       radon,
       dose,
       trizio,
       tipo_decreto,
       desc_asl,
       id_punto_prelievo
		in
		select ticket.ticketid,
       ticket.entered,
       ticket.enteredby,
       ticket.modified,
       ticket.modifiedby,
       ticket.closed,
       ticket.assigned_Date,
       cpp.temperatura,
       cpp.cloro,
       cpp.ore,
       cpp.prot_routine,
       cpp.prot_verifica,
       cpp.prot_replica_micro,
       cpp.prot_replica_chim,
       cpp.prot_radioattivita,
       cpp.prot_ricerca_fitosanitari,
       cpp.alfa,
       cpp.beta,
       cpp.radon,
       cpp.dose,
       cpp.trizio,
       cpp.tipo_decreto,
       asl.description as desc_asl,
       cpp.org_id_pdp as id_punto_prelievo
from controlli_punti_di_prelievo_acque_rete cpp 
join tipocontrolloufficialeimprese tcui on cpp.id_controllo = tcui.idcontrollo 
join ticket on ticket.ticketid = tcui.idcontrollo 
join lookup_site_id asl on asl.code = ticket.site_id 
join lookup_tipo_controllo tc on tc.code = ticket.provvedimenti_prescrittivi 
where tcui.ispezione = -1 and ticket.tipologia = 3 and (cpp.tipo_decreto = '31' or cpp.tipo_decreto = '28' ) and
      (cpp.tipo_decreto = tipo_decreto_in or tipo_decreto_in = '' ) and
      (asl_in = -1 or asl.code = asl_in ) and
      ticket.trashed_date is null	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_acque_rete(integer, text)
  OWNER TO postgres;

  
  
  
  
  
  
  
  