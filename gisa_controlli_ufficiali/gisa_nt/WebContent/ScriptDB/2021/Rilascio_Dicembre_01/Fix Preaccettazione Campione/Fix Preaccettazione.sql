-- Chi: Bartolo Sansone
-- Cosa: Preaccettazione - campione cliccabile
-- Quando: 26/07/21


CREATE OR REPLACE FUNCTION public.get_dettaglio_cu_da_campione(IN _idcampione integer)
  RETURNS TABLE(ticketid integer, org_id integer, id_stabilimento integer, id_apiario integer, alt_id integer, tipologia_operatore integer, riferimento_id integer, riferimento_id_nome_tab text, riferimento_id_nome_col text) AS
$BODY$
DECLARE
BEGIN
	FOR ticketid, org_id, id_stabilimento,id_apiario,alt_id,tipologia_operatore,riferimento_id,riferimento_id_nome_tab,riferimento_id_nome_col
	in

	select * from get_dettaglio_cu((select c.id_controllo_ufficiale::integer from ticket c where c.ticketid = _idcampione))
 
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
