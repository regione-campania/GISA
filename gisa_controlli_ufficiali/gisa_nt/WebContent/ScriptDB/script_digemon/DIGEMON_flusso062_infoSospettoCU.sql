-- Function: public_functions.dbi_get_campi_estesi()

-- DROP FUNCTION public_functions.dbi_get_campi_estesi();

CREATE OR REPLACE FUNCTION public_functions.dbi_get_campi_aggiuntivi(data1 timestamp, data2 timestamp)
  RETURNS TABLE(id_controllo integer, tipo_sospetto text, codice_buffer text, descrizione_breve text, stato_buffer text, data_stato timestamp without time zone, 
  data_evento timestamp without time zone, comune_coinvolto_buffer text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
     FOR id_controllo, tipo_sospetto, codice_buffer, descrizione_breve, stato_buffer, data_stato, data_evento, comune_coinvolto_buffer
  in
	select t.ticketid, t.tipo_sospetto, t.codice_buffer,b.descrizione_breve, 
	case when b.stato=1 then 'APERTO' when stato=2 then 'CHIUSO' else 'NON PRESENTE' end as stato, b.data_stato, b.data_evento, c.nome
	from ticket t
	left join buffer b on b.id::text = t.codice_buffer
	left join buffer_comuni_coinvolti bc on bc.id_buffer = b.id
	left join comuni1 c on c.id = bc.id_comune
	where t.tipologia = 3 and t.trashed_date is null 
	and t.tipo_sospetto is not null and b.trashed_date is null 
	and t.assigned_date between data1 and data2 

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_campi_aggiuntivi(timestamp, timestamp)
  OWNER TO postgres;

select * from public_functions.dbi_get_campi_aggiuntivi('2017-01-01','2017-07-25')