--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di un indirizzo nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_indirizzo_old_anagrafica(integer, character varying, character, integer, integer, double precision, double precision, integer, integer, text)

-- DROP FUNCTION anagrafica.anagrafica_cerca_indirizzo_old_anagrafica(integer, character varying, character, integer, integer, double precision, double precision, integer, integer, text);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_indirizzo_old_anagrafica(
    IN _id integer DEFAULT NULL::integer,
    IN _via character varying DEFAULT NULL::character varying,
    IN _cap character DEFAULT NULL::bpchar,
    IN _provincia integer DEFAULT NULL::integer,
    IN _nazione integer DEFAULT NULL::integer,
    IN _latitudine double precision DEFAULT NULL::double precision,
    IN _longitudine double precision DEFAULT NULL::double precision,
    IN _comune integer DEFAULT NULL::integer,
    IN _toponimo integer DEFAULT NULL::integer,
    IN _civico text DEFAULT NULL::text)
  RETURNS TABLE(id integer, via character varying, cap character, provincia integer, nazione integer, latitudine double precision, longitudine double precision, comune integer, toponimo integer, civico text) AS
$BODY$
DECLARE

BEGIN

FOR id, via, cap, provincia, nazione, latitudine, longitudine, comune, toponimo, civico
in 
    select oi.id, oi.via, oi.cap, (oi.provincia::integer), ln.code, 
	   oi.latitudine, oi.longitudine, oi.comune, oi.toponimo, oi.civico
	
    from opu_indirizzo oi join lookup_nazioni ln on oi.nazione ilike ln.description
	 
    where 
    (_id is null or oi.id = _id) 
    and (_via is null or oi.via ilike _via || '%')
    and (_cap is null or oi.cap = _cap)
    and (_provincia is null or (oi.provincia::integer) = _provincia)
    and (_nazione is null or ln.code = _nazione)
    and (_latitudine is null or oi.latitudine = _latitudine)
    and (_longitudine is null or oi.longitudine = _longitudine)
    and (_comune is null or oi.comune = _comune)
    and (_toponimo is null or oi.toponimo = _toponimo)
    and (_civico is null or oi.civico = _civico)
    and ln.enabled
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
     
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_indirizzo_old_anagrafica(integer, character varying, character, integer, integer, double precision, double precision, integer, integer, text)
  OWNER TO postgres;
