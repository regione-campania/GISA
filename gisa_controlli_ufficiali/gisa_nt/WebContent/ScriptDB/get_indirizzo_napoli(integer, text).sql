-- Function: public.get_indirizzo_napoli(integer, text)

-- DROP FUNCTION public.get_indirizzo_napoli(integer, text);

CREATE OR REPLACE FUNCTION public.get_indirizzo_napoli(
    IN _toponimo integer DEFAULT NULL::integer,
    IN _comune_start text DEFAULT NULL::text)
  RETURNS TABLE(via text, descrizione_provincia text, id integer, comune integer, cod_provincia text, provincia text, cap text, descrizione_comune text, nazione text, latitudine double precision, longitudine double precision, tipologia_sede integer, comune_testo text, toponimo integer, civico text, descrizione_toponimo text) AS
$BODY$
DECLARE

BEGIN


FOR "via", "descrizione_provincia", "id", "comune", "cod_provincia", "provincia", "cap", "descrizione_comune", "nazione",
    "latitudine", "longitudine", "tipologia_sede", "comune_testo", "toponimo", "civico", "descrizione_toponimo"
    in
    select
	   distinct c.indirizzo, 'napoli', null, 5279, '063', '63', trim(c.cap), 'napoli', 'italia', 
	   null, null, null, 'napoli', lt.code, c.numeri, c.toponimo  
	from cap c join lookup_toponimi lt on trim(c.toponimo) ilike trim(lt.description)
			where (lt.code = _toponimo) and c.indirizzo ilike concat('%',_comune_start, '%') order by c.indirizzo limit 50
    /*select
	   distinct i.via, 'napoli', i.id, 5279, '063', i.provincia, c.cap, 'napoli', i.nazione, 
	   i.latitudine, i.longitudine, null, i.comune_testo, i.toponimo, i.civico, c.toponimo  
	from opu_indirizzo i join cap c on (i.via ilike c.indirizzo) 
			where (i.comune = 5279 and i.toponimo = _toponimo) and i.via ilike concat('%',_comune_start, '%') order by i.via limit 50*/ 
		
       LOOP	 
	   RETURN NEXT;
	END LOOP;
	RETURN;
   
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_indirizzo_napoli(integer, text)
  OWNER TO postgres;
