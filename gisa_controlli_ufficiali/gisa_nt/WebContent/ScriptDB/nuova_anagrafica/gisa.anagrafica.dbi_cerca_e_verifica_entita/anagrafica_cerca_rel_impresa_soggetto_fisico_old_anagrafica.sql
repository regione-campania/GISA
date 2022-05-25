--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di una relazione impresa - soggetto fisico nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_rel_impresa_soggetto_fisico_old_anagrafica(integer, integer, integer, date, date)

-- DROP FUNCTION anagrafica.anagrafica_cerca_rel_impresa_soggetto_fisico_old_anagrafica(integer, integer, integer, date, date);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_rel_impresa_soggetto_fisico_old_anagrafica(
    IN _id_impresa integer DEFAULT NULL::integer,
    IN _id_soggetto_fisico integer DEFAULT NULL::integer,
    IN _id_tipo_relazione integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date)
  RETURNS TABLE(id_impresa integer, id_soggetto_fisico integer, id_tipo_relazione integer, data_inserimento date) AS
$BODY$
DECLARE


BEGIN

FOR id_impresa ,id_soggetto_fisico ,id_tipo_relazione ,data_inserimento 
in 
    select opurel.id_operatore, opurel.id_soggetto_fisico, _id_tipo_relazione, null
	from opu_rel_operatore_soggetto_fisico opurel
		where (_id_impresa is null or opurel.id_operatore = _id_impresa) 
		       and (_id_soggetto_fisico is null or opurel.id_soggetto_fisico = _id_soggetto_fisico)
		       and opurel.enabled
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

     
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_rel_impresa_soggetto_fisico_old_anagrafica(integer, integer, integer, date, date)
  OWNER TO postgres;
