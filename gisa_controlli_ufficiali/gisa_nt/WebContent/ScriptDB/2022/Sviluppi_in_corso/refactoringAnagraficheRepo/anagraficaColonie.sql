--DROP FUNCTION digemon.dbi_get_anagrafica_colonie(timestamp without time zone,timestamp without time zone,boolean)

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_colonie(
    IN _data_1 text default null::text,
    IN _data_2 text  default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, "numero protocollo" text, 
  asl text, "codice fiscale rappresentante" text, referente text, "num controlli aperti" text, "num controlli chiusi" text, 
  "comune stabilimento" text, "provincia stabilimento" text, "indirizzo stabilimento" text, "cap stabilimento" text, "latitudine stabilimento" text, 
  "longitudine stabilimento" text, "tipo attivita" text, norma text, "codice macroarea" text, "codice aggregazione" text, 
  "codice attivita" text, macroarea text, aggregazione text, attivita text, "data inserimento" text, riferimento_id text, riferimento_id_nome_tab text,
  id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		 select  'progressivo|text|dig'
			, 'numero protocollo|text|dig'
			, 'asl|text|dig'
			, 'codice fiscale rappresentante|text|dig'
			, 'referente|text|dig'
			, 'num controlli aperti|text|dig'
			, 'num controlli chiusi|text|dig'
			, 'comune stabilimento|text|dig'
			, 'provincia stabilimento|text|dig'
			, 'indirizzo stabilimento|text|dig'
			, 'cap stabilimento|text|dig'
			, 'latitudine stabilimento|text|dig'
			, 'longitudine stabilimento|text|dig'
			, 'tipo attivita|text|dig'
			, 'norma|text|dig'
			, 'codice macroarea|text|dig|ra'
			, 'codice aggregazione|text|dig|ra'
			, 'codice attivita|text|dig|ra'
			, 'macroarea|text|dig'
			, 'aggregazione|text|dig'
			, 'attivita|text|dig'
			, 'data inserimento|dateISO8601|dig'
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra';
		
	ELSE
		RETURN QUERY
		with cte as (
			select distinct
			row_number() OVER (ORDER BY concat(r.asl, r.comune, r.data_inserimento,current_timestamp) desc) AS n
			, r.ragione_sociale as numero_protocollo
			, r.asl
			, r.codice_fiscale_rappresentante
			, r.nominativo_rappresentante as referente
			, (select count(*) from ticket where org_id=r.riferimento_id and closed is null and trashed_date is null and tipologia=3) as num_controlli_aperti
			, (select count(*) from ticket where org_id=r.riferimento_id and closed is not null and trashed_date is null and tipologia=3) as num_controlli_chiusi
			, r.comune as comune_stabilimento
			, r.provincia_stab as provincia_stabilimento
			, r.indirizzo as indirizzo_stabilimento
			, r.cap_stab as cap_stabilimento
			, r.latitudine_stab as latitudine_stabilimento
			, r.longitudine_stab as longitudine_stabilimento
			, l.tipo_attivita_descrizione as tipo_attivita
			, l.norma
			, l.codice_macroarea
			, l.codice_aggregazione
			, l.codice_attivita
			, l.macroarea
			, l.aggregazione
			, l.attivita
			, r.data_inserimento
			, r.riferimento_id
			, r.riferimento_id_nome_tab
			, l.id_linea
			, l.id_norma
		from 
			digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
		join 
			digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = l.riferimento_id_nome_tab
		where 
			r.data_inserimento between _data_1::date and _data_2::date and 
			l.codice_macroarea='IUV' and l.codice_aggregazione='CF' and l.codice_attivita='CF'  -- filtro sulle colonie
		)
		select
			'progressivo|text|dig'
			, 'numero protocollo|text|dig'
			, 'asl|text|dig'
			, 'codice fiscale rappresentante|text|dig'
			, 'referente|text|dig'
			, 'num controlli aperti|text|dig'
			, 'num controlli chiusi|text|dig'
			, 'comune stabilimento|text|dig'
			, 'provincia stabilimento|text|dig'
			, 'indirizzo stabilimento|text|dig'
			, 'cap stabilimento|text|dig'
			, 'latitudine stabilimento|text|dig'
			, 'longitudine stabilimento|text|dig'
			, 'tipo attivita|text|dig'
			, 'norma|text|dig'
			, 'codice macroarea|text|dig|ra'
			, 'codice aggregazione|text|dig|ra'
			, 'codice attivita|text|dig|ra'
			, 'macroarea|text|dig'
			, 'aggregazione|text|dig'
			, 'attivita|text|dig'
			, 'data inserimento|dateISO8601|dig'
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra'
		union all
		select 	lpad(n::text,6,'0') as progressivo --[[#4]]
			, cte.numero_protocollo
			, cte.asl
			, cte.codice_fiscale_rappresentante
			, cte.referente
			, cte.num_controlli_aperti::text
			, cte.num_controlli_chiusi::text
			, cte.comune_stabilimento
			, cte.provincia_stabilimento
			, cte.indirizzo_stabilimento
			, cte.cap_stabilimento
			, cte.latitudine_stabilimento::text
			, cte.longitudine_stabilimento::text
			, cte.tipo_attivita::text
			, cte.norma
			, cte.codice_macroarea
			, cte.codice_aggregazione
			, cte.codice_attivita
			, cte.macroarea
			, cte.aggregazione
			, cte.attivita
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF')
			, cte.riferimento_id::text
			, cte.riferimento_id_nome_tab
			, cte.id_linea::text
			, cte.id_norma::text
		from cte;
	END IF;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_anagrafica_colonie(text,text, text)
  OWNER TO postgres;

  select * from digemon.dbi_get_anagrafica_colonie('2021-01-01','2021-12-31') limit3
  select * from digemon.dbi_get_anagrafica_colonie()