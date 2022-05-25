-- Function: public.calcola_cap_da_indirizzo(integer, integer, text)

-- DROP FUNCTION public.calcola_cap_da_indirizzo(integer, integer, text);

CREATE OR REPLACE FUNCTION public.calcola_cap_da_indirizzo(
    _comune integer DEFAULT NULL::integer,
    _toponimo integer DEFAULT NULL::integer,
    _via text DEFAULT NULL::text)
  RETURNS text AS
$BODY$
DECLARE

	cap_comune text;
	desc_toponimo text;

BEGIN
	select cap into cap_comune from opu_indirizzo where ((comune = _comune and toponimo = _toponimo) and trim(via) ilike trim(_via)) limit 1;

	
	IF trim(cap_comune) is null or trim(cap_comune) ilike '' THEN
	
		IF _comune = 5279 THEN


			select description into desc_toponimo from lookup_toponimi where code = _toponimo;
						

			select c.cap into cap_comune from cap c 
				where trim(c.toponimo) ilike trim(desc_toponimo) and trim(c.indirizzo) ilike '%' || _via || '%' limit 1;
										

		ELSE

			select cap into cap_comune from comuni1 where id = _comune;
		END IF;

	END IF;

	RETURN trim(cap_comune);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.calcola_cap_da_indirizzo(integer, integer, text)
  OWNER TO postgres;
