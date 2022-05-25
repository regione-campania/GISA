
CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo1_tipo2(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, asl text, 
  "data presentazione richiesta" text , "tipo trasporto" text , "denominazione" text , "partita iva" text , 
"codice fiscale" text , "animali trasportati" text , "nominativo rappresentante" text , "codice fiscale rappresentante" text , 
"data nascita titolare" text , "luogo nascita titolare" text , "email titolare" text , "telefono titolare" text , "fax titolare" text , "comune leg" text , 
"provincia leg" text , "indirizzo leg" text , "cap leg" text , "latitudine leg" text , "longitudine leg" text , "comune stabilimento" text , 
"provincia stabilimento" text , "indirizzo stabilimento" text , "cap stabilimento" text , "latitudine stabilimento" text , "longitudine stabilimento" text , 
"lavaggio autorizzato" text , "numero registrazione" text , "stato" text , "data cambio stato" text , "tipo attivita" text , "norma" text , "codice macroarea" text , 
"codice aggregazione" text , "codice attivita" text , "macroarea" text , "aggregazione" text , "attivita" text , "data inserimento" text,
riferimento_id text, riferimento_id_nome_tab text,
  id_linea text, id_norma text) AS
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
			, 'denominazione|text|dig'
			, 'partita iva|text|dig'
			, 'codice fiscale|text|dig'
			, 'animali trasportati|text|dig'
			, 'nominativo rappresentante|text|dig'
			, 'codice fiscale rappresentante|text|dig'
			, 'data nascita titolare trasporto|dateISO8601|dig'
			, 'luogo nascita titolare trasporto|text|dig'
			, 'email titolare trasporto|text|dig'
			, 'telefono titolare trasporto|text|dig'
			, 'fax titolare trasporto|text|dig'
			, 'comune leg|text|dig'
			, 'provincia leg|text|dig'
			, 'indirizzo leg|text|dig'
			, 'cap leg|text|dig'
			, 'latitudine leg|text|dig'				
			, 'longitudine leg|text|dig'
			, 'comune stabilimento|text|dig'
			, 'provincia stabilimento|text|dig'
			, 'rindirizzo stabilimento|text|dig'
			, 'cap stabilimento|text|dig'
			, 'latitudine stabilimento|text|dig'
			, 'longitudine stabilimento|text'
			, 'lavaggio autorizzato trasporto|text'
			, 'numero registrazione|text|dig'
			, 'stato|text|dig'
			, 'data cambio stato trasporto|dateISO8601|dig'
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
				, e.data_presentazione_richiesta_trasporto as data_presentazione_richiesta
				, e.tipo_trasporto as tipo
				, r.ragione_sociale as denominazione
				, r.partita_iva::text 
				, r.codice_fiscale::text
				, concat_ws(case when length(e.descrizione_animale)>0 then ' Descrizione: ' else '' end, string_agg(distinct e.animale_trasportato,'-')
				, e.descrizione_animale) as animali_trasportati
				, r.nominativo_rappresentante
				, r.codice_fiscale_rappresentante
				, e.data_nascita_titolare_trasporto
				, e.luogo_nascita_titolare_trasporto
				, e.email_titolare_trasporto
				, e.telefono_titolare_trasporto
				, e.fax_titolare_trasporto
				, r.comune_leg
				, r.provincia_leg
				, r.indirizzo_leg
				, r.cap_leg
				, r.latitudine_leg
				, r.longitudine_leg
				, r.comune as comune_stabilimento
				, r.provincia_stab as provincia_stabilimento
				, r.indirizzo as indirizzo_stabilimento
				, r.cap_stab as cap_stabilimento
				, r.latitudine_stab as latitudine_stabilimento
				, r.longitudine_stab as longitudine_stabilimento
				, e.lavaggio_autorizzato_trasporto
				, l.num_riconoscimento as numero_registrazione
				, e.stato_trasporto as stato
				, e.data_cambio_stato_trasporto
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
				join digemon.dbi_get_all_linee(_data_1::date,_data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = l.riferimento_id_nome_tab
			where 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.codice_macroarea='TAV' and l.codice_aggregazione='TAV' and l.codice_attivita='X' -- filtro su trasportatori di animali
				and e.tipo_trasporto in ('tipo1','tipo2') 
				group by-- a causa dello string_agg ci vuole la group by
				r.asl, e.data_presentazione_richiesta_trasporto, e.tipo_trasporto,
				r.ragione_sociale, r.partita_iva, r.codice_fiscale, r.nominativo_rappresentante, r.codice_fiscale_rappresentante, e.data_nascita_titolare_trasporto, 
				e.luogo_nascita_titolare_trasporto, e.email_titolare_trasporto, e.telefono_titolare_trasporto, e.fax_titolare_trasporto,
				r.comune_leg, r.provincia_leg, r.indirizzo_leg, r.cap_leg, r.latitudine_leg, r.longitudine_leg, r.comune, r.provincia_stab, r.indirizzo, 
				r.cap_stab, r.latitudine_stab, r.longitudine_stab, e.lavaggio_autorizzato_trasporto, l.num_riconoscimento,
				e.stato_trasporto, e.data_cambio_stato_trasporto,
				l.tipo_attivita_descrizione, l.norma, l.codice_macroarea, l.codice_aggregazione, l.codice_attivita, l.macroarea, l.aggregazione, l.attivita,
				r.data_inserimento, e.descrizione_animale, r.riferimento_id, r.riferimento_id_nome_tab	, l.id_linea, l.id_norma
			) 
			select 
				'progressivo|text|dig'
			, 'asl|text|dig'
			, 'data presentazione richiesta|dateISO8601|dig'
			, 'tipo|text|dig'
			, 'denominazione|text|dig'
			, 'partita iva|text|dig'
			, 'codice fiscale|text|dig'
			, 'animali trasportati|text|dig'
			, 'nominativo rappresentante|text|dig'
			, 'codice fiscale rappresentante|text|dig'
			, 'data nascita titolare trasporto|dateISO8601|dig'
			, 'luogo nascita titolare trasporto|text|dig'
			, 'email titolare trasporto|text|dig'
			, 'telefono titolare trasporto|text|dig'
			, 'fax titolare trasporto|text|dig'
			, 'comune leg|text|dig'
			, 'provincia leg|text|dig'
			, 'indirizzo leg|text|dig'
			, 'cap leg|text|dig'
			, 'latitudine leg|text|dig'				
			, 'longitudine leg|text|dig'
			, 'comune stabilimento|text|dig'
			, 'provincia stabilimento|text|dig'
			, 'rindirizzo stabilimento|text|dig'
			, 'cap stabilimento|text|dig'
			, 'latitudine stabilimento|text|dig'
			, 'longitudine stabilimento|text'
			, 'lavaggio autorizzato trasporto|text'
			, 'numero registrazione|text|dig'
			, 'stato|text|dig'
			, 'data cambio stato trasporto|dateISO8601|dig'
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
				, cte.denominazione::text
				, cte.partita_iva::text 
				, cte.codice_fiscale::text
				, cte.animali_trasportati
				, cte.nominativo_rappresentante
				, cte.codice_fiscale_rappresentante::text
				, to_char(cte.data_nascita_titolare_trasporto::date,'YYYY-MM-DD"T"HH24:MI:SSOF')
				, cte.luogo_nascita_titolare_trasporto::text
				, cte.email_titolare_trasporto::text
				, cte.telefono_titolare_trasporto::text
				, cte.fax_titolare_trasporto::text
				, cte.comune_leg::text
				, cte.provincia_leg::text
				, cte.indirizzo_leg::text
				, cte.cap_leg::text
				, cte.latitudine_leg::text
				, cte.longitudine_leg::text
				, cte.comune_stabilimento::text
				, cte.provincia_stabilimento::text
				, cte.indirizzo_stabilimento::text
				, cte.cap_stabilimento::text
				, cte.latitudine_stabilimento::text
				, cte.longitudine_stabilimento::text
				, cte.lavaggio_autorizzato_trasporto
				, cte.numero_registrazione::text
				, cte.stato::text
				, to_char(cte.data_cambio_stato_trasporto,'YYYY-MM-DD"T"HH24:MI:SSOF')
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
ALTER FUNCTION digemon.dbi_get_anagrafica_trasportoanimali_tipo1_tipo2(text,text, text)
  OWNER TO postgres;
select * from digemon.dbi_get_anagrafica_trasportoanimali_tipo1_tipo2('2021-01-01','2021-06-30') 
select * from digemon.dbi_get_anagrafica_trasportoanimali_tipo1_tipo2() 

