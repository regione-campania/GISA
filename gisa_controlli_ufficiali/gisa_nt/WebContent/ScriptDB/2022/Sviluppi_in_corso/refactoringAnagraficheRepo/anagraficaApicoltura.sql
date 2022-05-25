DROP FUNCTION digemon.dbi_get_dati_apicoltura()
CREATE OR REPLACE FUNCTION digemon.dbi_get_dati_apicoltura()
  RETURNS TABLE(riferimento_id integer, data_sincronizzazione text, stato_impresa text,domicilio_digitale text, telefono_fisso text,telefono_cellulare text, fax text, progressivo text, stato_stab text,
                data_apertura_stab text, data_chiusura_stab text, tipo_descrizione_attivita text, nominativo_rapp text, cf_detentore text, classificazione text, sottospecie text,
		modalita text, num_alveari text, num_sciami text, num_pacchi text, num_regine text, data_assegnazione_censimento text, ubicazione text, data_cessazione text
                ) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select       
	       a.id_apicoltura_apiari, 
	       to_char(ap.data_sincronizzazione,'dd/mm/yyyy') as data_sincronizzazione, 
	       a.stato_impresa::text, a.domicilio_digitale, a.telefono_fisso, a.telefono_cellulare, a.fax, a.progressivo::text, a.stato_stab::text,
	       a.data_apertura_stab, a.data_chiusura_stab, 
	       a.tipo_descrizione_attivita::text, 
	       concat_ws(' ', cognome_detentore, nome_detentore), a.cf_detentore::text, a.classificazione, a.sottospecie,
	       a.modalita, a.num_alveari::text, a.num_sciami::text, a.num_pacchi::text, a.num_regine::text, 
	       to_char(a.data_assegnazione_censimento,'dd/mm/yyyy') as data_assegnazione_censimento,
	       case when a.flag_laboratorio=true then 'LABORATORIO DI SMIELATURA' else 
		concat_ws(' ',upper(a.indirizzo_stab), a.cap_stab,  upper(a.comune_stab), (select upper(description) from lookup_province where code::text = a.prov_stab::text), 'LATITUDINE: ', a.latitudine, 'LONGITUDINE: ', a.longitudine)
		end as ubicazione,
	       a.data_cessazione_stab as data_cessazione       
FROM 
	       apicoltura_apiari_denormalizzati_view a
	       left join apicoltura_apiari ap on ap.id = a.id_apicoltura_apiari
where
		ap.trashed_date is null;
		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_dati_apicoltura()
  OWNER TO postgres;

--select * from digemon.dbi_get_dati_apicoltura() limit 4
--select * from ricerche_anagrafiche_old_materializzata  where riferimento_id_nome_tab ilike '%apicoltur%' limit 3

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_apicoltura(
    IN _data_1 text default null::text,
    IN _data_2 text  default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, 
  denominazione text, 
  "stato apicoltura" text,
  "partita iva" text, 
  "proprietario (cognome e nome)" text,
  "codice fiscale proprietario" text,
  "data registrazione BDN" text,
  "data inizio attivita" text,
  "tipo attivita" text,
  "indirizzo sede legale" text,	
  --macroarea text, aggregazione text, attivita text --> sono null. Le anagrafiche apicoltura vanno riviste in ottica di ml 
  "domicilio digitale (PEC)" text,
  "telefono fisso" text,
  "telefono cellulare" text,
  "fax" text,
  "asl di competenza" text,
  "progressivo apiario" text,
  "stato apiario" text,
  "data apertura apiario" text,
  "data chiusura apiario" text,
  "detentore apiario" text,
  "codice fiscale detentore apiario" text,
  "classificazione" text,
  "sottospecie" text,
  "modalita" text,
  "numero alveari" text,
  "sciami/nuclei" text,
  "numero pacchi api" text,
  "numero api regine" text,
  "data censimento" text,
  "ubicazione" text,
  "data cessazione" text,
  "codice azienda" text,
  "norma" text,
  "codice macroarea" text, 
  "codice aggregazione" text, 
  "codice attivita" text, 
  "macroarea" text,
  "aggregazione" text,
  "attivita" text,
  "data inserimento" text,
  riferimento_id text, 
  riferimento_id_nome_tab text,
  id_linea text, 
  id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
	raise info 'entro qui?';
		RETURN QUERY
		 select  
			 'progressivo|text|dig'
			 , 'denominazione|text|dig' 
			 , 'stato apicoltura|text|dig'
			 , 'partita iva|text|dig'
			 , 'proprietario (cognome e nome)|text|dig'
			 , 'codice fiscale proprietario|text|dig'
			 , 'data registrazione BDN|dateISO8601|dig'
			 , 'codice azienda|text|dig'
			 , 'data inizio attivita|dateISO8601|dig'
			 , 'tipo attivita|text|dig'
			 , 'indirizzo sede legale|text|dig'
			 , 'domicilio digitale (PEC)|text|dig'
			 , 'telefono fisso|text|dig'
			 , 'telefono cellulare|text|dig'
			 , 'fax|text|dig'
			 , 'asl di competenza|text|dig'
			 , 'progressivo apiario|text|dig'
			 , 'stato apiario|text|dig'
			 , 'data apertura apiario|dateISO8601|dig'
			 , 'data chiusura apiario|dateISO8601|dig'
			 , 'detentore apiario|text|dig'
			 , 'codice fiscale detentore apiario|text|dig'
			 , 'classificazione|text|dig'
			 , 'sottospecie|text|dig'
			 , 'modalita|text|dig'
			 , 'numero alveari|text|dig'
			 , 'sciami/nuclei|text|dig'
			 , 'numero pacchi api|text|dig'
			 , 'numero api regine|text|dig'
			 , 'data censimento|dateISO8601|dig'
			 , 'ubicazione|text|dig'
			 , 'data cessazione|dateISO8601|dig'
			 , 'codice azienda|text|dig'
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
			, r.ragione_sociale
                        , e.stato_impresa 
                        , r.partita_iva 
                        , r.nominativo_rappresentante
                        , r.codice_fiscale_rappresentante
                        , e.data_sincronizzazione
                        , l.data_inizio_attivita
                        , e.tipo_descrizione_attivita
                        , concat_ws(' ', upper(r.indirizzo_leg), r.cap_leg, upper(r.comune_leg),r.provincia_leg) as indirizzo_legale
			, e.domicilio_digitale
			, e.telefono_fisso
			, e.telefono_cellulare
			, e.fax
			, r.asl
			, e.progressivo
			, e.stato_stab               
			, e.data_apertura_stab 
			, e.data_chiusura_stab
			, e.nominativo_rapp
			, e.cf_detentore
			, e.classificazione
			, e.sottospecie
			, e.modalita
			, e.num_alveari
			, e.num_sciami
			, e.num_pacchi
			, e.num_regine
			, e.data_assegnazione_censimento
			, e.ubicazione
			, e.data_cessazione
			, l.n_linea 
			, l.norma
			, l.codice_macroarea
			, l.codice_aggregazione
			, l.codice_attivita
			, l.macroarea
			, l.aggregazione
			, l.attivita
			, r.data_inserimento
			, r.riferimento_id::text
			, r.riferimento_id_nome_tab::text
			, l.id_linea::text
			, l.id_norma::text
		from 
			  digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
		join 
			  digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'apicoltura_imprese'
		left join digemon.dbi_get_dati_apicoltura() e on e.riferimento_id=r.riferimento_id and r.riferimento_id_nome_tab = 'apicoltura_imprese'	
		where 
			r.data_inserimento between _data_1::date and _data_2::date and 
			l.norma = 'APICOLTURA'
		
		)
		
		select  
			 'progressivo|text|dig'
			 , 'denominazione|text|dig' 
			 , 'stato apicoltura|text|dig'
			 , 'partita iva|text|dig'
			 , 'proprietario (cognome e nome)|text|dig'
			 , 'codice fiscale proprietario|text|dig'
			 , 'data registrazione BDN|dateISO8601|dig'
			 , 'data inizio attivita|dateISO8601|dig'
			 , 'tipo attivita|text|dig'
			 , 'indirizzo sede legale|text|dig'
			 , 'domicilio digitale (PEC)|text|dig'
			 , 'telefono fisso|text|dig'
			 , 'telefono cellulare|text|dig'
			 , 'fax|text|dig'
			 , 'asl di competenza|text|dig'
			 , 'progressivo apiario|text|dig'
			 , 'stato apiario|text|dig'
			 , 'data apertura apiario|dateISO8601|dig'
			 , 'data chiusura apiario|dateISO8601|dig'
			 , 'detentore apiario|text|dig'
			 , 'codice fiscale detentore apiario|text|dig'
			 , 'classificazione|text|dig'
			 , 'sottospecie|text|dig'
			 , 'modalita|text|dig'
			 , 'numero alveari|text|dig'
			 , 'sciami/nuclei|text|dig'
			 , 'numero pacchi api|text|dig'
			 , 'numero api regine|text|dig'
			 , 'data censimento|dateISO8601|dig'
			 , 'ubicazione|text|dig'
			 , 'data cessazione|dateISO8601|dig'
			 , 'codice azienda|text|dig'
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
			, cte.ragione_sociale
                        , cte.stato_impresa 
                        , cte.partita_iva 
                        , cte.nominativo_rappresentante
                        , cte.codice_fiscale_rappresentante
                        , to_char(cte.data_sincronizzazione::date,'YYYY-MM-DD"T"HH24:MI:SSOF') --text
                        , to_char(cte.data_inizio_attivita,'YYYY-MM-DD"T"HH24:MI:SSOF')
                        , cte.tipo_descrizione_attivita
                        , cte.indirizzo_legale
			, cte.domicilio_digitale
			, cte.telefono_fisso
			, cte.telefono_cellulare
			, cte.fax
			, cte.asl
			, cte.progressivo
			, cte.stato_stab              
			, to_char(cte.data_apertura_stab::date,'YYYY-MM-DD"T"HH24:MI:SSOF')  --text
			, to_char(cte.data_chiusura_stab::date,'YYYY-MM-DD"T"HH24:MI:SSOF')  --text
			, cte.nominativo_rapp
			, cte.cf_detentore
			, cte.classificazione
			, cte.sottospecie
			, cte.modalita
			, cte.num_alveari 
			, cte.num_sciami
			, cte.num_pacchi
			, cte.num_regine
			, to_char(cte.data_assegnazione_censimento::date,'YYYY-MM-DD"T"HH24:MI:SSOF')  --text
			, cte.ubicazione
			, to_char(cte.data_cessazione::date,'YYYY-MM-DD"T"HH24:MI:SSOF') -- già text
			, cte.n_linea::text 
			, cte.norma
			, cte.codice_macroarea
			, cte.codice_aggregazione
			, cte.codice_attivita
			, cte.macroarea
			, cte.aggregazione
			, cte.attivita
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF')
			, cte.riferimento_id
			, cte.riferimento_id_nome_tab
			, cte.id_linea
			, cte.id_norma
		from cte;
	END IF;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_anagrafica_apicoltura(text,text, text)
  OWNER TO postgres;

--select * from  digemon.dbi_get_anagrafica_apicoltura()
--select * from  digemon.dbi_get_anagrafica_apicoltura('2021-01-01','2021-05-05')