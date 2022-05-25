
-- Function: digemon.dbi_get_campi_estesi_allevamenti()

-- DROP FUNCTION digemon.dbi_get_campi_estesi_allevamenti();

CREATE OR REPLACE FUNCTION digemon.dbi_get_campi_estesi_sintesis()
  RETURNS TABLE(riferimento_id integer, tipo_impresa text, tipo_societa text, domicilio_digitale text, indirizzo_rapp text, stato text, denominazione text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select       
	       	s.alt_id, i.description::text as tipo_impresa, sc.description::text as tipo_societa,
	       	o.domicilio_digitale,
	       	concat_ws(' ',top.description, ind.via, ind.civico, com.nome, prov.description),
	       	stati.description::text as stato,
	       	s.denominazione
FROM 
		sintesis_stabilimento s 
		join sintesis_operatore o on s.id_operatore = o.id 
		left join lookup_opu_tipo_impresa i on o.tipo_impresa = i.code
		left join lookup_opu_tipo_impresa_societa sc on o.tipo_societa = sc.code
                join sintesis_rel_operatore_soggetto_fisico rel on rel.id_operatore = o.id and rel.enabled and rel.data_fine is null 
                left join sintesis_soggetto_fisico sogg on sogg.id = rel.id_soggetto_fisico 
                left join sintesis_indirizzo ind on ind.id = sogg.indirizzo_id 
                left join lookup_toponimi top on top.code = ind.toponimo 
                left join comuni1 com on com.id = ind.comune 
                left join lookup_province prov on prov.code::text = ind.provincia
		left join lookup_stato_stabilimento_sintesis stati on s.stato = stati.code
		
WHERE
		s.trashed_date is null and o.trashed_date is null ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_campi_estesi_sintesis()
  OWNER TO postgres;

--select * from sintesis_stabilimento where id=2007
--select * from digemon.dbi_get_campi_estesi_sintesis() where riferimento_id = 100002007


CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_sintesis(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(rec_num text, 
   "asl" text,
   "ragione sociale impresa" text, 
   "tipo impresa" text, 
   "tipo societa''" text, 
   "partita_iva" text, 
   "codice fiscale impresa" text, 
   "domicilio digitale" text,
   "indirizzo sede legale" text,  -- cumulativo
   "rappresentante legale" text,  
   "denominazione stabilimento" text,
   "stato" text, 
   "indirizzo stabilimento" text,
   "approval number" text,
   "macroarea" text,
   "aggregazione" text, 
   "attivita" text, 
   "codice macroarea" text, 
   "codice aggregazione" text, 
   "codice attivita" text, 
   "data inserimento" text,
   riferimento_id text, 
   riferimento_id_nome_tab text, 
   id_linea text, 
   id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY -- restituisce solo il record di intestazione
		select  'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'ragione sociale impresa|text|dig'
			, 'tipo impresa|text|dig'
			, 'tipo societa|text|dig' 
			, 'partita_iva|text|dig' 
			, 'codice fiscale impresa|text|dig' 
			, 'domicilio digitale|text|dig'  
			, 'indirizzo sede legale|text|dig'  
			, 'rappresentante legale|text|dig'  
			, 'denominazione stabilimento|text|dig' 
			, 'stato|text|dig' 
			, 'indirizzo stabilimento|text|dig' 
			, 'approval number|text|dig' 
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'data_inserimento|dateISO8601|dig'  
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra';
	
	ELSE 		
		RETURN QUERY    
		with cte as (
			SELECT 
			    row_number() OVER (ORDER BY concat(r.asl, r.comune, r.data_inserimento,current_timestamp) desc) AS n
			    , r.asl
			    , r.ragione_sociale 
			    , e.tipo_impresa
			    , e.tipo_societa
			    , r.partita_iva
			    , r.codice_fiscale
			    , e.domicilio_digitale
			    , concat_ws(' ', r.indirizzo_leg,  r.cap_leg, r.comune_leg,r.provincia_leg) as indirizzo_legale
			    , concat_ws(' ', r.nominativo_rappresentante, r.codice_fiscale_rappresentante, '', e.indirizzo_rapp) as indirizzo_rapp
			    , e.denominazione
			    , e.stato
			    -- indirizzo stabilimento
			    , concat_ws(' ', r.indirizzo, r.cap_stab, r.comune, r.provincia_stab, r.latitudine_stab, r.longitudine_stab) as indirizzo_stab
			    , l.n_linea
			    --dati categorizzazione
			    --, r.categoria_rischio 
			    --, r.id_controllo_ultima_categorizzazione 
			    --, r.prossimo_controllo as data_prossimo_controllo
			    , l.macroarea
			    , l.aggregazione
			    , l.attivita
			    , l.codice_macroarea
			    , l.codice_aggregazione
			    , l.codice_attivita
			    , r.data_inserimento
			    , r.riferimento_id
			    , r.riferimento_id_nome_tab
			    , l.id_linea
			    , l.id_norma
			FROM 
				digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
				left join digemon.dbi_get_campi_estesi_sintesis() e on e.riferimento_id = r.riferimento_id and r.riferimento_id_nome_tab = 'sintesis_stabilimento'
				join digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'sintesis_stabilimento'
				 
			WHERE 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.norma ilike 'REG CE 1069-09' or l.norma ilike 'REG CE 853-04'				
			ORDER BY 
				r.asl, r.comune,  --[[#3]
				r.data_inserimento 
			desc
		)
		select -- nome|tipo 
			'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'ragione sociale impresa|text|dig'
			, 'tipo impresa|text|dig'
			, 'tipo societa|text|dig' 
			, 'partita_iva|text|dig' 
			, 'codice fiscale impresa|text|dig' 
			, 'domicilio digitale|text|dig'  
			, 'indirizzo sede legale|text|dig'  
			, 'rappresentante legale|text|dig'  
			, 'denominazione stabilimento|text|dig' 
			, 'stato|text|dig' 
			, 'indirizzo stabilimento|text|dig' 
			, 'approval number|text|dig' 
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'data_inserimento|dateISO8601|dig'  
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra'
		UNION ALL
		select 
			lpad(n::text,6,'0') as progressivo --[[#4]]
			, cte.asl
			, cte.ragione_sociale 
			, cte.tipo_impresa
			, cte.tipo_societa
			, cte.partita_iva
			, cte.codice_fiscale
			, cte.domicilio_digitale
			, cte.indirizzo_legale
			, cte.indirizzo_rapp
			, cte.denominazione
			, cte.stato
			, cte.indirizzo_stab
			, cte.n_linea::text 
			, cte.macroarea
			, cte.aggregazione
			, cte.attivita
			, cte.codice_macroarea
			, cte.codice_aggregazione
			, cte.codice_attivita
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inserimento --[[#1]]
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
ALTER FUNCTION digemon.dbi_get_anagrafica_sintesis(text,text,text)
  OWNER TO postgres;
  
--select * from digemon.dbi_get_anagrafica_sintesis()
--select * from digemon.dbi_get_anagrafica_sintesis('2021-01-01','2021-05-31')