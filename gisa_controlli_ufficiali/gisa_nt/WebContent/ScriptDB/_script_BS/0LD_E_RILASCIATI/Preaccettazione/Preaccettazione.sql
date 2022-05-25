-- CHI: Bartolo Sansone	
-- COSA: Preaccettazione
-- QUANDO: 20/09/2018


CREATE OR REPLACE FUNCTION get_lista_sottoattivita(id_controllo integer, tipo_sottoattivita integer, cerca_trashed boolean)
  RETURNS TABLE(ticketid integer, descrizione_sottoattivita text, trashed_date timestamp without time zone) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
ticketid,	descrizione_sottoattivita, trashed_date
		in
		
		select t.ticketid, l.description, t.trashed_date
		from ticket t left join lookup_tipologia_cu_e_sottoattivita l on t.tipologia = l.code
		where t.id_controllo_ufficiale = id_controllo::text
		and ((tipo_sottoattivita>0 and t.tipologia = tipo_sottoattivita) or (tipo_sottoattivita=-1))
		and ((cerca_trashed is false and t.trashed_date is null) or (cerca_trashed is true))

		
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;

