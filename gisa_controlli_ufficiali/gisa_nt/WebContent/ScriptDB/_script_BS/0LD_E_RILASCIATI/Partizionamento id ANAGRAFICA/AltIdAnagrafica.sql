-- gisa

-- Inserisco i partizionamenti

insert into alt_partizioni(code, description, nome_campo, nome_tabella, valore_start, valore_end) values (7, 'id di imprese sul db anagrafica', 'id', 'imprese', 120000000,139999999);
insert into alt_partizioni(code, description, nome_campo, nome_tabella, valore_start, valore_end) values (8, 'id di stabilimenti sul db anagrafica', 'id', 'stabilimenti', 140000000,159999999);

-- anagrafica

-- Aggiungo la colonna alt_id

alter table imprese add column alt_id integer;
alter table stabilimenti add column alt_id integer;

-- Creo la tabella remota su alt_partizioni di gisa

-- View: public.r_alt_partizioni

-- DROP VIEW public.r_alt_partizioni;

CREATE OR REPLACE VIEW public.r_alt_partizioni AS 
 SELECT tl.code,
    tl.description,
    tl.nome_campo,
    tl.nome_tabella,
    tl.valore_start,
    tl.valore_end
   FROM dblink('dbname=gisa'::text, 'select code, description, nome_campo, nome_tabella, valore_start, valore_end from alt_partizioni'::text) tl(code integer, description text, nome_campo text, nome_tabella text, valore_start integer, valore_end integer);

ALTER TABLE public.r_alt_partizioni
  OWNER TO postgres;

-- Creo le funzioni di gestione id alternativo

  -- Function: public.gestione_id_alternativo(integer, integer)

-- DROP FUNCTION public.gestione_id_alternativo(integer, integer);

CREATE OR REPLACE FUNCTION public.gestione_id_alternativo(
    IN idinput integer,
    IN tipoinput integer)
  RETURNS TABLE(return_id integer, return_alt_id integer, return_code integer, return_nome_campo text, return_nome_tabella text) AS
$BODY$
DECLARE
	r RECORD;	
BEGIN
	FOR return_id, return_alt_id, return_code,  return_nome_campo, return_nome_tabella
	in

select 

case when tipoInput=-1 then (idInput - valore_start) 
else -1 end as id, 
case when tipoInput>-1 then (idInput + valore_start) 
else -1 end as alt_id, code, nome_campo, nome_tabella
from r_alt_partizioni where 1=1

and ((tipoInput=-1 and valore_start <= idInput and valore_end >= idInput)
or tipoInput>-1)

and ((tipoInput>-1 and code = tipoInput)
or tipoInput=-1)

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.gestione_id_alternativo(integer, integer)
  OWNER TO postgres;

-- Function: public.inserisci_id_alternativo_imprese()

-- DROP FUNCTION public.inserisci_id_alternativo_imprese();

CREATE OR REPLACE FUNCTION public.inserisci_id_alternativo_imprese()
  RETURNS trigger AS
$BODY$
   DECLARE
   alternativeId integer;
   
   BEGIN

   alternativeId = (select return_alt_id from  gestione_id_alternativo(NEW.id, 7));
   update imprese set alt_id = alternativeId where id = NEW.id;
     
    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.inserisci_id_alternativo_imprese()
  OWNER TO postgres;
  
  
  -- Function: public.inserisci_id_alternativo_stabilimenti()

-- DROP FUNCTION public.inserisci_id_alternativo_stabilimenti();

CREATE OR REPLACE FUNCTION public.inserisci_id_alternativo_stabilimenti()
  RETURNS trigger AS
$BODY$
   DECLARE
   alternativeId integer;
   
   BEGIN

   alternativeId = (select return_alt_id from  gestione_id_alternativo(NEW.id, 8));
   update stabilimenti set alt_id = alternativeId where id = NEW.id;
     
    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.inserisci_id_alternativo_stabilimenti()
  OWNER TO postgres;

-- Creo i trigger

-- Trigger: imprese_id_alternativo on public.imprese

-- DROP TRIGGER imprese_id_alternativo ON public.imprese;

CREATE TRIGGER imprese_id_alternativo
  AFTER INSERT
  ON public.imprese
  FOR EACH ROW
  EXECUTE PROCEDURE public.inserisci_id_alternativo_imprese();

  -- Trigger: stabilimenti_id_alternativo on public.stabilimenti

-- DROP TRIGGER stabilimenti_id_alternativo ON public.stabilimenti;

CREATE TRIGGER stabilimenti_id_alternativo
  AFTER INSERT
  ON public.stabilimenti
  FOR EACH ROW
  EXECUTE PROCEDURE public.inserisci_id_alternativo_stabilimenti();