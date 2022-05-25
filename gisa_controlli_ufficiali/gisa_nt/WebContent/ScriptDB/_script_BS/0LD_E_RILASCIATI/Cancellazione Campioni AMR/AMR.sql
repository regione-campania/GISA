
-- Chi: Bartolo Sansone
-- Quando: 19/12/2017
-- Cosa: Cancellazione campione AMR

CREATE OR REPLACE FUNCTION public.is_campione_cancellabile(
    IN_idcampione integer)
  RETURNS text AS
$BODY$
DECLARE
	codice integer;
	errore text;
	id_coda_amr integer;
BEGIN

codice := -1;
errore := '';

id_coda_amr := -1;

id_coda_amr := (select idcampione from coda_amr where idcampione = IN_idcampione::text and trashed is null);

if (id_coda_amr) > 0 THEN
codice = 1;
errore = 'IMPOSSIBILE CANCELLARE CAMPIONE. RISULTA INVIATO PER AMR.';
END IF;

 RETURN concat_ws(';;', codice, errore);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
