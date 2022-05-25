-- Function: aggiorna_stato_convocato_microchip(text, integer, timestamp without time zone)

-- DROP FUNCTION aggiorna_stato_convocato_microchip(text, integer, timestamp without time zone);

CREATE OR REPLACE FUNCTION aggiorna_stato_convocato_microchip(microchip_to_up text, new_stato integer, data_prelievo timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
   id_convocato_int integer;
   msg text ;
   cursore refcursor;
   BEGIN
	--da_convocare = 1;
	--convocato_non_presentato = 2;
	--convocato_ma_escluso_per_regolarizzazione = 3;
	--presentato = 4;
	
		update convocazioni set id_stato_presentazione  = new_stato where microchip = microchip_to_up ;
			--Aggiorno anche lo stato all'ultima convocazione?

  select id into id_convocato_int from convocazioni where microchip = microchip_to_up; 
raise info 'dati % ', id_convocato_int;
		update relazione_convocazione_convocati set id_stato_presentazione = new_stato where id_convocato = id_convocato_int;
		update convocazioni set data_prelievo_dna = data_prelievo where id = id_convocato_int;
		msg = 'OK';	
		
		
		
      RETURN msg;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION aggiorna_stato_convocato_microchip(text, integer, timestamp without time zone)
  OWNER TO postgres;
