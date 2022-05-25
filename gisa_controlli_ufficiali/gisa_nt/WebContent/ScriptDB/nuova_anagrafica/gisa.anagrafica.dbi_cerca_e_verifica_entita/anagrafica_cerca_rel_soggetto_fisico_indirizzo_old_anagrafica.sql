--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di una relazione soggetto fisico - indirizzo nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_rel_soggetto_fisico_indirizzo_old_anagrafica(integer, integer, date, date)

-- DROP FUNCTION anagrafica.anagrafica_cerca_rel_soggetto_fisico_indirizzo_old_anagrafica(integer, integer, date, date);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_rel_soggetto_fisico_indirizzo_old_anagrafica(
    IN _id_soggetto_fisico integer DEFAULT NULL::integer,
    IN _id_indirizzo integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date)
  RETURNS TABLE(id_soggetto_fisico integer, id_indirizzo integer, data_inserimento timestamp without time zone) AS
$BODY$
DECLARE

BEGIN

FOR id_soggetto_fisico, id_indirizzo, data_inserimento
in 
    select id, indirizzo_id, null
	from opu_soggetto_fisico
		where (_id_soggetto_fisico is null or id = _id_soggetto_fisico) 
		      and (_id_indirizzo is null or indirizzo_id = _id_indirizzo)
		      and trashed_date is null
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;   

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_rel_soggetto_fisico_indirizzo_old_anagrafica(integer, integer, date, date)
  OWNER TO postgres;
