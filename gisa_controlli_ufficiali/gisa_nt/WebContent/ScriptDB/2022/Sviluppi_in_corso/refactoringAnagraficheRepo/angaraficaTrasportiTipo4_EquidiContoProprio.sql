-- Function:igemon.dbi_get_anagrafica_trasportoanimali_tipo4(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo4(timestamp without time zone, timestamp without time zone, boolean);
CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo4(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, "asl" text, "data presentazione autodichiarazione" text, "nominativo trasportatore" text, "
  codice fiscale trasportatore" text, "
  tipo trasporto" text, "
  proprietario detentore di" text, "comune residenza" text, "provincia residenza" text, "indirizzo residenza" text, 
  "cap residenza" text, "latitudine residenza" text, "longitudine residenza" text, "abitazione allevamento" text, 
   "comune abitazione allevamento" text, "provincia abitazione allevamento" text, "indirizzo abitazione allevamento" text,
   "cap abitazione allevamento" text, "latitudine abitazione allevamento" text, "longitudine abitazione allevamento" text, 
   "stato" text, "data cambio stato" text, "tipo attivita" text, "norma" text, "codice macroarea" text, "codice aggregazione" text, 
   "codice attivita" text, "macroarea" text, "aggregazione" text, "attivita" text, "data inserimento" text,
   riferimento_id text, riferimento_id_nome_tab text, id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN

IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		select  
			'progressivo|text|dig'
			, 'asl|text|dig'
			, 'data presentazione autodichiarazione|dateISO8601|dig'
			, 'nominativo trasportatore|text|dig'
			, 'codice fiscale trasportatore|text|dig'
			, 'tipo|text|dig'
			, 'proprietario detentore di|text|dig'
			, 'comune leg|text|dig'
			, 'provincia sede legale|text|dig'
			, 'indirizzo sede legale|text|dig'
			, 'cap sede legale|text|dig'
			, 'latitudine sede legale|text|dig'
			, 'longitudine sede legale|text|dig'
			, 'abitazione allevamento|text|dig'
			, 'comune abitazione allevamento|text|dig'
			, 'provincia abitazione allevamento|text|dig'
			, 'indirizzo abitazione allevamento|text|dig'
			, 'cap abitazione allevamento|text|dig'
			, 'latitudine abitazione allevamento|text|dig'
			, 'longitudine abitazione allevamento|text|dig'
			, 'stato|text|dig'
			, 'data cambio stato|dateISO8601|dig'
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
				, e.data_presentazione_richiesta_trasporto as data_presentazione_autodichiarazione
				, r.nominativo_rappresentante as nominativo_trasportatore
				, r.codice_fiscale_rappresentante as codice_fiscale_trasportatore
				, e.tipo_trasporto as tipo
				, string_agg(distinct e.animale_trasportato,'-') as proprietario_detentore_di
				, r.comune_leg
				, r.provincia_leg
				, r.indirizzo_leg
				, r.cap_leg
				, r.latitudine_leg
				, r.longitudine_leg
				, e.ente_gestore as abitazione_allevamento
				, r.comune as comune_abitazione_allevamento
				, r.provincia_stab as provincia_abitazione_allevamento
				, r.indirizzo as indirizzo_abitazione_allevamento
				, r.cap_stab as cap_abitazione_allevamento
				, r.latitudine_stab as latitudine_abitazione_allevamento
				, r.longitudine_stab as longitudine_abitazione_allevamento
				, e.stato_trasporto as stato
				, e.data_cambio_stato_trasporto as data_cambio_stato
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
				and e.tipo_trasporto in ('tipo4') 
				group by-- a causa dello string_agg ci vuole la group by
				r.riferimento_id, r.riferimento_id_nome_tab,r.asl, e.data_presentazione_richiesta_trasporto,
				r.nominativo_rappresentante, r.codice_fiscale_rappresentante, e.tipo_trasporto,
				r.comune_leg, r.provincia_leg, r.indirizzo_leg, r.cap_leg, r.latitudine_leg, r.longitudine_leg,
				e.ente_gestore, r.comune , r.provincia_stab , r.indirizzo , r.cap_stab , r.latitudine_stab , r.longitudine_stab , 
				e.stato_trasporto, e.data_cambio_stato_trasporto, l.tipo_attivita_descrizione, l.norma,	l.codice_macroarea, l.codice_aggregazione, 
				l.codice_attivita, l.macroarea, l.aggregazione, l.attivita, r.data_inserimento, r.riferimento_id
				, r.riferimento_id_nome_tab
				, l.id_linea
				, l.id_norma
		)
		select
			'progressivo|text|dig'
			, 'asl|text|dig'
			, 'data presentazione autodichiarazione|dateISO8601|dig'
			, 'nominativo trasportatore|text|dig'
			, 'codice fiscale trasportatore|text|dig'
			, 'tipo|text|dig'
			, 'proprietario detentore di|text|dig'
			, 'comune leg|text|dig'
			, 'provincia sede legale|text|dig'
			, 'indirizzo sede legale|text|dig'
			, 'cap sede legale|text|dig'
			, 'latitudine sede legale|text|dig'
			, 'longitudine sede legale|text|dig'
			, 'abitazione allevamento|text|dig'
			, 'comune abitazione allevamento|text|dig'
			, 'provincia abitazione allevamento|text|dig'
			, 'indirizzo abitazione allevamento|text|dig'
			, 'cap abitazione allevamento|text|dig'
			, 'latitudine abitazione allevamento|text|dig'
			, 'longitudine abitazione allevamento|text|dig'
			, 'stato|text|dig'
			, 'data cambio stato|dateISO8601|dig'
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
			select distinct 
				lpad(n::text,6,'0') as progressivo --[[#4]]
				, cte.asl::text
				, to_char(cte.data_presentazione_autodichiarazione::date,'YYYY-MM-DD"T"HH24:MI:SSOF')
				, cte.nominativo_trasportatore::text
				, cte.codice_fiscale_trasportatore::text
				, cte.tipo::text
				, cte.proprietario_detentore_di::text
				, cte.comune_leg::text
				, cte.provincia_leg::text
				, cte.indirizzo_leg::text
				, cte.cap_leg::text
				, cte.latitudine_leg::text
				, cte.longitudine_leg::text
				, cte.abitazione_allevamento::text
				, cte.comune_abitazione_allevamento::text
				, cte.provincia_abitazione_allevamento::text
				, cte.indirizzo_abitazione_allevamento::text
				, cte.cap_abitazione_allevamento::text
				, cte.latitudine_abitazione_allevamento::text
				, cte.longitudine_abitazione_allevamento::text
				, cte.stato::text
				, to_char(cte.data_cambio_stato,'YYYY-MM-DD"T"HH24:MI:SSOF')
				, cte.tipo_attivita::text
				, cte.norma::text
				, cte.codice_macroarea
				, cte.codice_aggregazione
				, cte.codice_attivita
				, cte.macroarea
				, cte.aggregazione
				, cte.attivita::text
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
ALTER FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo4(text, text, text)
  OWNER TO postgres;


 select * from digemon.dbi_get_anagrafica_trasportoanimali_tipo4('2021-01-01','2021-04-30')