
--DROP FUNCTION digemon.dbi_get_anagrafica_punti_sbarco(timestamp without time zone, timestamp without time zone, boolean)
CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_punti_sbarco(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, asl text, "punto di sbarco" text, "descrizione punto di sbarco" text, comune text, "tipo attivita" text, norma text, "codice macroarea" text, 
  "codice aggregazione" text, "codice attivita" text, macroarea text, aggregazione text, attivita text, "data inserimento" text, riferimento_id text, riferimento_id_nome_tab text,
  id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		select 'progressivo|text|dig'
			, 'asl|text|dig'
			, 'punto di sbarco|text|dig'
			, 'descrizione punto di sbarco|text|dig'
			, 'comune|text|dig'
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
					, r.asl
					, r.ragione_sociale as punto_di_sbarco
					, e.descrizione_punto_sbarco as descrizione_punto_di_sbarco
					, r.comune
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
					digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
					left join digemon.dbi_get_campi_estesi_anagrafica() e on e.org_id = r.riferimento_id and r.riferimento_id_nome_tab = 'organization'
					join digemon.dbi_get_all_linee(_data_1::date,_data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				WHERE 
					r.data_inserimento between _data_1::date and _data_2::date and 
					l.codice_macroarea='PSB' and l.codice_aggregazione='PSB' and l.codice_attivita='X'  -- filtro su punti di sbarco
			)
			select 'progressivo|text|dig'
			, 'asl|text|dig'
			, 'punto di sbarco|text|dig'
			, 'descrizione punto di sbarco|text|dig'
			, 'comune|text|dig'
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
				, cte.asl
				, cte.punto_di_sbarco
				, cte.descrizione_punto_di_sbarco::text
				, cte.comune
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
ALTER FUNCTION digemon.dbi_get_anagrafica_punti_sbarco(text,text, text)
  OWNER TO postgres;

  select * from digemon.dbi_get_anagrafica_punti_sbarco('2021-01-01','2021-12-31');
  select * from digemon.dbi_get_anagrafica_punti_sbarco();
