

CREATE OR REPLACE FUNCTION digemon.dbi_get_dati_acque_di_rete()
  RETURNS TABLE(riferimento_id integer, riferimento_id_nome_tab text, tipologia text, ente_gestore character varying, stato text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN

FOR riferimento_id,riferimento_id_nome_tab,tipologia, ente_gestore, stato
in
  select o.org_id as riferimento_id, 'organization'::text as riferimento_id_nome_tab, 
 	t.description || ' ('|| COALESCE(t.identificativo,'') || ')' as tipologia, banca as ente_gestore,
 	case when o.cessato = 0 then 'ATTIVO' else 'NON ATTIVO' end as stato
from organization o 
left join lookup_tipo_acque t on t.code = o.tipo_struttura
where 1=1 and o.tipologia=14 and o.trashed_date is null   


 LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_dati_acque_di_rete(integer, text)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_acque_di_rete(
    IN _data_1 text default null::text,
    IN _data_2 text  default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, denominazione text, "codice punto di prelievo" text, "tipologia" text, "asl" text, "ente gestore" text,
   ubicazione text, norma text, "codice macroarea" text, "codice aggregazione" text, "codice attivita" text, macroarea text, aggregazione text, attivita text, "data inserimento" text, riferimento_id text, riferimento_id_nome_tab text,
  id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		 select   
			'progressivo|text|dig'
			, 'denominazione|text|dig'
			, 'codice punto di prelievo|text|dig'
			, 'tipologia|text|dig'
			, 'asl|text|dig'
			, 'ente gestore|text|dig'
			, 'ubicazione|text|dig'
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
			, l.n_linea 
			, e.tipologia
			, r.asl
			, e.ente_gestore
			, concat_ws(' ', upper(r.indirizzo), upper(r.comune),r.cap_stab, '- LATITUDINE:', r.latitudine_stab, 'LONGITUDINE:', r.longitudine_stab) as ubicazione
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
			  digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'organization'
		left join digemon.dbi_get_dati_acque_di_rete() e on e.riferimento_id=r.riferimento_id and r.riferimento_id_nome_tab = 'organization'	
		where 
			r.data_inserimento between _data_1::date and _data_2::date and 
			l.codice_macroarea='AQRE' and l.codice_aggregazione='AQRE' and l.codice_attivita='AQRE'  -- filtro sulle colonie
		)
		select
			'progressivo|text|dig'
			, 'denominazione|text|dig'
			, 'codice punto di prelievo|text|dig'
			, 'tipologia|text|dig'
			, 'asl|text|dig'
			, 'ente gestore|text|dig'
			, 'ubicazione|text|dig'
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
			, cte.n_linea::text
			, cte.tipologia
			, cte.asl
			, cte.ente_gestore::text
			, cte.ubicazione::text			
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
ALTER FUNCTION digemon.dbi_get_anagrafica_acque_di_rete(text,text, text)
  OWNER TO postgres;

  select * from digemon.dbi_get_anagrafica_acque_di_rete('2020-01-01','2021-01-01')