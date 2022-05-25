--DROP FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo3(timestamp without time zone,timestamp without time zone, boolean);

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo3(
    IN _data_1  text default null::text,
    IN _data_2  text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE("progressivo" text, "asl" text,
"data presentazione richiesta" text, "tipo trasporto" text, " denominazione allevamento" text,"animali trasportati" text, "comune sede legale" text, 
"provincia sede legale" text, "indirizzo sede legale" text, "cap sede legale" text, "latitudine sede legale" text, "longitudine sede legale" text,"stato" text, "codice azienda" text, 
"tipo attivita" text, "norma" text, "codice macroarea" text, "codice aggregazione" text, "codice attivita" text, "macroarea" text, "aggregazione" text, "attivita" text, "data inserimento" text,
 riferimento_id text, riferimento_id_nome_tab text, id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		select  
			'progressivo|text|dig'
			, 'asl|text|dig'
			, 'data presentazione richiesta|dateISO8601|dig'
			, 'tipo|text|dig'
			, 'denominazione allevamento|text|dig'
			, 'animali trasportati|text|dig'
			, 'comune sede legale|text|dig'
			, 'provincia sede legale|text|dig'
			, 'indirizzo sede legale|text|dig'
			, 'cap sede legale|text|dig'
			, 'latitudine sede legale|text|dig'
			, 'longitudine sede legale|text|dig'
			, 'stato|text|dig'
			, 'codice azienda|text|dig'
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
				row_number() OVER (ORDER BY concat(r.asl, r.comune_leg, r.data_inserimento,current_timestamp) desc) AS n
				, r.asl
				, e.data_presentazione_richiesta_trasporto as data_presentazione_richiesta
				, e.tipo_trasporto as tipo
				, r.ragione_sociale as denominazione_allevamento
				, string_agg(distinct e.animale_trasportato,'-') as animali_trasportati
				, r.comune_leg
				, r.provincia_leg
				, r.indirizzo_leg
				, r.cap_leg
				, r.latitudine_leg
				, r.longitudine_leg
				, e.stato_trasporto as stato
				, l.n_linea as codice_azienda
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
				left join digemon.dbi_get_campi_estesi_anagrafica() e on e.org_id = r.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				join digemon.dbi_get_all_linee(_data_1::date,_data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'organization'
			where 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.codice_macroarea='TAV' and l.codice_aggregazione='TAV' and l.codice_attivita='X' -- filtro su trasportatori di animali
				and e.tipo_trasporto in ('tipo3') 
				group by-- a causa dello string_agg ci vuole la group by
				r.asl, 
				e.data_presentazione_richiesta_trasporto,
				e.tipo_trasporto,
				r.ragione_sociale,
				r.comune_leg, r.provincia_leg, r.indirizzo_leg, r.cap_leg, r.latitudine_leg, r.longitudine_leg,
				e.stato_trasporto,
				l.n_linea ,
				l.tipo_attivita_descrizione, 
				l.norma,
				l.codice_macroarea, l.codice_aggregazione, l.codice_attivita, l.macroarea, l.aggregazione, l.attivita,
				r.data_inserimento, r.riferimento_id, r.riferimento_id_nome_tab, l.id_linea, l.id_norma
			)
			select 
				'progressivo|text|dig'
				, 'asl|text|dig'
				, 'data presentazione richiesta|dateISO8601|dig'
				, 'tipo|text|dig'
				, 'denominazione allevamento|text|dig'
				, 'animali trasportati|text|dig'
				, 'comune sede legale|text|dig'
				, 'provincia sede legale|text|dig'
				, 'indirizzo sede legale|text|dig'
				, 'cap sede legale|text|dig'
				, 'latitudine sede legale|text|dig'
				, 'longitudine sede legale|text|dig'
				, 'stato|text|dig'
				, 'codice azienda|text|dig'
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
				, cte.asl::text
				, to_char(cte.data_presentazione_richiesta::date,'YYYY-MM-DD"T"HH24:MI:SSOF')
				, cte.tipo::text
				, cte.denominazione_allevamento::text
				, cte.animali_trasportati
				, cte.comune_leg::text
				, cte.provincia_leg::text
				, cte.indirizzo_leg::text
				, cte.cap_leg::text
				, cte.latitudine_leg::text
				, cte.longitudine_leg::text
				, cte.stato
				, cte.codice_azienda::text
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
ALTER FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo3(text,text, text)
  OWNER TO postgres;

--select * from digemon.dbi_get_anagrafica_trasportoanimali_tipo3('2021-01-01','2021-01-31') 
--select * from digemon.dbi_get_anagrafica_trasportoanimali_tipo3() 