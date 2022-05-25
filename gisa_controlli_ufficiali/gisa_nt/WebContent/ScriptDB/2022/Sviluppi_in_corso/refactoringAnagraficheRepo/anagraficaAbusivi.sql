
-- DROP FUNCTION digemon.dbi_get_anagrafica_abusivi(timestamp without time zone, timestamp without time zone,boolean);

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_abusivi(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, ignoto text, cognome text, nome text, "luogo di nascita" text, "partita iva" text, "comune residenza" text, 
  "via residenza" text, "data controllo" text, stato text, asl text, "comune rilevazione attivita abusiva" text, "provincia rilevazione attivita abusiva" text, 
  "indirizzo rilevazione attivita abusiva" text, "cap rilevazione attivita abusiva" text, "latitudine rilevazione attivita abusiva" text, 
  "longitudine rilevazione attivita abusiva" text, "tipo attivita" text, "norma" text, "codice macroarea" text, "codice aggregazione" text, 
  "codice attivita" text, macroarea text, aggregazione text, attivita text, "data inserimento" text, riferimento_id text, riferimento_id_nome_tab text,
  id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		select
		'progressivo|text|dig'
			, 'ignoto|text|dig'
			, 'cognome|text|dig'
			, 'nome|text|dig'
			, 'luogo di nascita|text|dig'
			, 'partita iva|text|dig'
			, 'comune residenza|text|dig'
			, 'via residenza|text|dig'
			, 'data controllo|dateISO8601|dig'
			, 'stato|text|dig'
			, 'asl|text|dig'
			, 'comune rilevazione attivita abusiva|text|dig'
			, 'provincia rilevazione attivita abusiva|text|dig'
			, 'indirizzo rilevazione attivita abusiva|text|dig'
			, 'cap rilevazione attivita abusiva|text|dig'
			, 'latitudine rilevazione attivita abusiva|text|dig'
			, 'longitudine rilevazione attivita abusiva|text|dig'
			, 'tipo attivita|text|dig'
			, 'norma|text|dig'
			, 'codice macroarea|text|dig'
			, 'codice aggregazione|text|dig'
			, 'codice attivita|text|dig'
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
				, e.ignoto
				, e.cognome_abusivo as cognome
				, e.nome_abusivo as nome
				, e.luogo_di_nascita_abusivo as luogo_di_nascita
				, r.partita_iva::text
				,  e.comune_residenza_abusivo as comune_residenza
				, e.via_residenza_abusivo as via_residenza
				, e.data_controllo_abusivo as data_controllo
				, e.stato_abusivo as stato
				, r.asl
				, r.comune as comune_rilevazione_attivita_abusiva
				, r.provincia_stab as provincia_rilevazione_attivita_abusiva
				, r.indirizzo as indirizzo_rilevazione_attivita_abusiva
				, r.cap_stab as cap_rilevazione_attivita_abusiva
				, r.latitudine_stab as latitudine_rilevazione_attivita_abusiva
				, r.longitudine_stab as longitudine_rilevazione_attivita_abusiva
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
			FROM 
				digemon.dbi_get_all_stabilimenti(_data_1::date,_data_2::date) r
				left join digemon.dbi_get_campi_estesi_anagrafica() e on e.org_id = r.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				join digemon.dbi_get_all_linee(_data_1::date,_data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = l.riferimento_id_nome_tab
			WHERE 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.codice_macroarea='OAB' and l.codice_aggregazione='OAB' and l.codice_attivita='X'  -- filtro su abusivi
		)
		 
		select
			'progressivo|text|dig'
			, 'ignoto|text|dig'
			, 'cognome|text|dig'
			, 'nome|text|dig'
			, 'luogo di nascita|text|dig'
			, 'partita iva|text|dig'
			, 'comune residenza|text|dig'
			, 'via residenza|text|dig'
			, 'data controllo|dateISO8601|dig'
			, 'stato|text|dig'
			, 'asl|text|dig'
			, 'comune rilevazione attivita abusiva|text|dig'
			, 'provincia rilevazione attivita abusiva|text|dig'
			, 'indirizzo rilevazione attivita abusiva|text|dig'
			, 'cap rilevazione attivita abusiva|text|dig'
			, 'latitudine rilevazione attivita abusiva|text|dig'
			, 'longitudine rilevazione attivita abusiva|text|dig'
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
		UNION ALL
		select 
			lpad(n::text,6,'0') as progressivo --[[#4]]
			, cte.ignoto
			, cte.cognome::text
			, cte.nome::text
			, cte.luogo_di_nascita::text
			, cte.partita_iva
			, cte.comune_residenza::text
			, cte.via_residenza::text
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF')
			, cte.stato
			, cte.asl::text
			, cte.comune_rilevazione_attivita_abusiva::text
			, cte.provincia_rilevazione_attivita_abusiva::text
			, cte.indirizzo_rilevazione_attivita_abusiva::text
			, cte.cap_rilevazione_attivita_abusiva::text
			, cte.latitudine_rilevazione_attivita_abusiva::text
			, cte.longitudine_rilevazione_attivita_abusiva::text
			, cte.tipo_attivita::text
			, cte.norma
			, cte.codice_macroarea
			, cte.codice_aggregazione
			, cte.codice_attivita
			, cte.macroarea
			, cte.aggregazione
			, cte.attivita
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inserimento
			, cte.riferimento_id::text
			, cte.riferimento_id_nome_tab
			, cte.id_linea::text
			, cte.id_norma::text
		FROM cte;
	END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_anagrafica_abusivi(text,text,text)
  OWNER TO postgres;

select * from digemon.dbi_get_anagrafica_abusivi('2020-01-01','2020-02-20')
select * from digemon.dbi_get_anagrafica_abusivi()