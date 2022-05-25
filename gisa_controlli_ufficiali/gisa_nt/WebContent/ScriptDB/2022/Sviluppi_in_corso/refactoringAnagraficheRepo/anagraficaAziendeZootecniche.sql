--DROP FUNCTION digemon.dbi_get_campi_estesi_allevamenti();
-- Function: digemon.dbi_get_campi_estesi_allevamenti()

-- DROP FUNCTION digemon.dbi_get_campi_estesi_allevamenti();

CREATE OR REPLACE FUNCTION digemon.dbi_get_campi_estesi_allevamenti()
  RETURNS TABLE(riferimento_id integer, specie_allevata character varying, orientamento_produttivo text, tipologia_struttura text, modalita_allevamento text, numero_capi text, email_rappresentante character varying, telefono_rappresentante character varying, nominativo_proprietario text, codice_fiscale_proprietario text, indirizzo_proprietario text, nominativo_detentore text, codice_fiscale_detentore text, indirizzo_detentore text, note text, stato_allevamento text, piccioni_stima_popolazione text, piccioni_utilizzo_sistemi text, piccioni_reti_protezione text, piccioni_cannoncini_dissuasori text, piccioni_dissuasori_aghi text, piccioni_dissuasori_sonori text, piccioni_altro text, scorta_tipo_scorta_codice text, scorta_num_autorizzazione text, scorta_data_inizio text, scorta_asl_codice text, scorta_lovscorta_id text, scorta_veterinario_codice_fiscale text, scorta_veterinario_responsabile text, scorta_veterinario_scovet_id text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select       
	      o.org_id as riferimento_id
	    , o.specie_allev as specie_allevata
	    , o.orientamento_prod as orientamento_produttivo
	    , o.tipologia_strutt as tipologia_struttura
	    , case when codice_tipo_allevamento = '0' then 'All''aperto' when codice_tipo_allevamento = '1' then 'A terra' when codice_tipo_allevamento = '2' then 'Biologico' when codice_tipo_allevamento = '3' then 'In Gabbia' else null end as modalita_allevamento
	    , case when o.specie_allev ilike 'Suini' then concat('Numero Capi Totali: ' , o.numero_capi , '
		' , 'Numero Lattonzoli : ' , o.num_lattonzoli , '
		' , 'Numero Magroncelli : ' , o.num_magroncelli , '
		' , 'Numero Magroni : ' , o.num_magrnoni , '
		' , 'Numero Grassi : ' , o.num_grassi , '
		' , 'Numero Scrofe : ' , o.num_scrofe , '
		' , 'Numero Scrofette : ' , o.num_scrofette , '
		' , 'Numero Verri : ' , o.num_verri , '
		' , 'Numero Cinghiali : ' , o.num_cinghiali , '
		' , 'Data ultimo censimento : ' ,to_char(o.data_censimento, 'dd/MM/yy') , '
		') else '' end || case when o.specie_allevamento in (121,129) then concat('Numero Capi Totali: ' , o.numero_capi , '
		' , 'Numero Capi con più di 6 settimane: ' , o.num_capi_sei_mesi , '
		' , 'Numero Capi con più di un anno anno: ' , o.num_capi_mag_anno , '
		') else '' end || case when o.specie_allevamento in (124,125) then concat('Flag ovini: ' , o.flag_ovini , '
		' , 'Flag caprini: ' , o.flag_caprini , '
		' , 'Numero ovini: ' , o.num_ovini , '
		' , 'Numero caprini: ' , o.num_caprini , '
		' , 'Numero femmine: ' , o.num_femmine , '
		' , 'Numero maschi: ' , o.num_maschi , '
		' , 'Numero capi magg. sei mesi: ' , o.num_capi_sup_sei_mesi , '
		' , 'Numero capi min. sei mesi: ' , o.num_capi_min_sei_mesi , '
		' , 'Numero capi ovini tot.: ' , o.num_capi_ovini_totale , '
		') else '' end || case when o.specie_allevamento in (131,132,133,134,135,136,137,138,139,140,141,142,143,145,146) then concat('Capi: ' , o.capi , '
		' , 'Ciclo anno: ' , o.ciclo_anno , '
		' , 'Uova anno: ' , o.uova_anno) else '' end as numero_capi
	    , o.email_rappresentante
	    , o.telefono_rappresentante
	    , prop.nominativo as nominativo_proprietario
	    , prop.cf as codice_fiscale_proprietario
	    , prop.indirizzo|| ' ' || prop.comune || ' ' || prop.cap || ' ' || prop.prov as indirizzo_proprietario
	    , det.nominativo as nominativo_detentore
	    , det.cf as codice_fiscale_detentore -- cf_correntista o det.cf?
	    , det.indirizzo || ' ' || det.comune || ' ' || det.cap || ' ' || det.prov  as indirizzo_detentore
	    , o.notes as note
	    , case when o.date2 is not null then 'CESSATO IN DATA ' ||to_char(o.date2, 'dd/mm/yy') ELSE 'ATTIVO' END as stato_allevamento
	    , CASE WHEN picc.stima_popolazione = 'A' then '100/200' when picc.stima_popolazione = 'B' then '250/500' when picc.stima_popolazione = 'C' then 'Oltre 500' end as piccioni_stima_popolazione
	, case when picc.utilizzo_sistemi = 'S' then 'SI' when picc.utilizzo_sistemi = 'N' then 'NO' else '' end as piccioni_utilizzo_sistemi
	, case when picc.reti_protezione = 'S' then 'SI' else '' end as piccioni_reti_protezione
, case when picc.cannoncini_dissuasori = 'S' then 'SI' else '' end as piccioni_cannoncini_dissuasori
, case when picc.dissuasori_aghi = 'S' then 'SI' else '' end as piccioni_dissuasori_aghi
, case when picc.dissuasori_sonori = 'S' then 'SI' else '' end as piccioni_dissuasori_sonori
, case when picc.altro = 'S' then 'SI' else '' end as piccioni_altro
,scorta.tipo_scorta_codice as scorta_tipo_scorta_codice
,scorta.scorta_num_autorizzazione as scorta_num_autorizzazione
,scorta.scorta_data_inizio as scorta_data_inizio
,scorta.asl_codice as scorta_asl_codice
,scorta.lovscorta_id as scorta_lovscorta_id
,scortavet.vet_pers_id_fiscale as scorta_veterinario_codice_fiscale
,scortavet.flag_responsabile as scorta_veterinario_responsabile
,scortavet.scovet_id as scorta_veterinario_scovet_id	    
FROM
	 organization o 
	 left join operatori_allevamenti prop on (o.codice_fiscale_rappresentante = prop.cf) 
	 left join operatori_allevamenti det on (o.cf_correntista = det.cf) 
	 left join allevamenti_scheda_piccioni picc on picc.org_id = o.org_id and picc.trashed_date is null
	 left join scorta_farmaci scorta on scorta.cod_azienda = o.account_number and scorta.id_fiscale_allevamento = o.codice_fiscale_rappresentante
	 left join scorta_farmaci_veterinari scortavet on scorta.cod_azienda = scortavet.cod_azienda and scorta.id_fiscale_allevamento = scortavet.id_fiscale_allevamento

WHERE
	 o.tipologia=2 AND o.trashed_date is null; -- allevamenti non cancellati
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_campi_estesi_allevamenti()
  OWNER TO postgres;



--DROP FUNCTION digemon.dbi_get_anagrafica_aziende_zootecniche(timestamp without time zone, timestamp without time zone, boolean);

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_aziende_zootecniche(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(rec_num text, asl text, denominazione text, "specie allevata" text, "orientamento produttivo" text, "tipologia struttura" text, "modalita allevamento" text, 
  "codice fiscale rappresentante" text, "comune sede legale" text, "provincia sede legale" text, "indirizzo sede legale" text, 
  "cap sede legale" text, "email rappresentante" text, "telefono rappresentante" text, "nominativo proprietario" text, "codice fiscale proprietario" text, "indirizzo proprietario" text, 
  "nominativo detentore" text, "codice fiscale detentore" text, "indirizzo detentore" text, note text, "comune stabilimento" text, "provincia stabilimento" text, 
  "indirizzo stabilimento" text, "cap stabilimento" text, "latitudine stabilimento" text, "longitudine stabilimento" text, "data inizio attivita" text, "stato allevamento" text, 
  "codice azienda" text, "categoria di rischio" text, "id controllo ultima categorizzazione" text, "data prossimo controllo" text, macroarea text, aggregazione text, attivita text, 
  "codice macroarea" text, "codice aggregazione" text, "codice attivita" text, "codice tipo scorta farmaci" text, "numero autorizzazione scorta farmaci" text, "data inizio scorta farmaci" text, 
  "codice asl scorta farmaci" text, "codice fiscale veterinario scorta farmaci" text, "veterinario responsabile scorta farmaci" text, "stima popolazione piccioni" text, "sistema utilizzo piccioni" text, 
  "protezione reti piccioni" text, "cannoncini dissuasori piccioni" text, "aghi dissuasori piccioni" text, "dissuasori sonori piccioni" text, "altro per piccioni" text, "data inserimento" text
  ,riferimento_id text, riferimento_id_nome_tab text, id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY -- restituisce solo il record di intestazione
		select -- nome|tipo 
			'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'denominazione|text|dig' 
			, 'specie_allevata|text|dig' 
			, 'orientamento_produttivo|text|dig' 
			, 'tipologia_struttura|text|dig' 
			, 'modalita_allevamento|text|dig' 
			, 'codice_fiscale_rappresentante|text|dig' 
			, 'comune_leg|text|dig'  
			, 'provincia_leg|text|dig'  
			, 'indirizzo_leg|text|dig' 
			, 'cap_leg|text|dig' 
			, 'email_rappresentante|text|dig'  
			, 'telefono_rappresentante|text|dig'  
			, 'nominativo_proprietario|text|dig'  
			, 'codice_fiscale_proprietario|text|dig' 
			, 'indirizzo_proprietario|text|dig' 
			, 'nominativo_detentore|text|dig' 
			, 'codice_fiscale_detentore |text|dig'  
			, 'indirizzo_detentore|text|dig' 
			, 'note|text|dig' 
			, 'comune|text|dig'  
			, 'provincia_stab|text|dig'  
			, 'indirizzo|text|dig' 
			, 'cap_stab|text|dig'  
			, 'latitudine_stab|text|dig' 
			, 'longitudine_stab|text|dig'  
			, 'data_inizio_attivita|dateISO8601|dig'  
			, 'stato_allevamento|text|dig'  
			, 'codice_azienda|text|dig' 
			, 'categoria_rischio|text|dig'  
			, 'id_controllo_ultima_categorizzazione |text|dig' 
			, 'data_prossimo_controllo|dateISO8601|dig' 
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'scorta_tipo_scorta_codice|text|dig' 
			, 'scorta_num_autorizzazione|text|dig' 
			, 'scorta_data_inizio|dateISO8601|dig'  
			, 'scorta_asl_codice|text|dig'  
			, 'scorta_veterinario_codice_fiscale|text|dig'  
			, 'scorta_veterinario_responsabile|text|dig' 
			, 'piccioni_stima_popolazione|text|dig' 
			, 'piccioni_utilizzo_sistemi|text|dig' 
			, 'piccioni_reti_protezione|text|dig'  
			, 'piccioni_cannoncini_dissuasori|text|dig' 
			, 'piccioni_dissuasori_aghi|text|dig' 
			, 'piccioni_dissuasori_sonori|text|dig'  
			, 'piccioni_altro|text|dig'  
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
			    , r.ragione_sociale as denominazione
			    , e.specie_allevata
			    , e.orientamento_produttivo
			    , e.tipologia_struttura
			    , e.modalita_allevamento
			    , r.codice_fiscale_rappresentante
			   -- , e.numero_capi
			    , r.comune_leg
			    , r.provincia_leg
			    , r.indirizzo_leg
			    , r.cap_leg
			    , e.email_rappresentante
			    , e.telefono_rappresentante
			    , e.nominativo_proprietario
			    , e.codice_fiscale_proprietario
			    , e.indirizzo_proprietario
			    , e.nominativo_detentore
			    , e.codice_fiscale_detentore 
			    , e.indirizzo_detentore
			    , e.note
			    , r.comune
			    , r.provincia_stab
			    , r.indirizzo
			    , r.cap_stab
			    , r.latitudine_stab
			    , r.longitudine_stab
			    , l.data_inizio_attivita
			    , e.stato_allevamento
			    , l.n_linea as codice_azienda
			    , r.categoria_rischio 
			    , r.id_controllo_ultima_categorizzazione 
			    , r.prossimo_controllo as data_prossimo_controllo
			    , l.macroarea
			    , l.aggregazione
			    , l.attivita
			    , l.codice_macroarea
			    , l.codice_aggregazione
			    , l.codice_attivita
			    , e.scorta_tipo_scorta_codice
			    , e.scorta_num_autorizzazione
			    , e.scorta_data_inizio
			    , e.scorta_asl_codice
			    , e.scorta_veterinario_codice_fiscale
			    , e.scorta_veterinario_responsabile
			    , e.piccioni_stima_popolazione
			    , e.piccioni_utilizzo_sistemi
			    , e.piccioni_reti_protezione
			    , e.piccioni_cannoncini_dissuasori
			    , e.piccioni_dissuasori_aghi
			    , e.piccioni_dissuasori_sonori
			    , e.piccioni_altro
			    , r.data_inserimento
			    , r.riferimento_id
			    , r.riferimento_id_nome_tab
			    , l.id_linea
			    , l.id_norma
			FROM 
				digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
				join digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				left join digemon.dbi_get_campi_estesi_allevamenti() e on r.riferimento_id = e.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				 
			WHERE 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.norma = '852-AZIENDE ZOOTECNICHE'	
				
			ORDER BY 
				r.asl, r.comune,  --[[#3]
				r.data_inserimento 
			desc
		)
		select -- nome|tipo 
			'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'denominazione|text|dig' 
			, 'specie_allevata|text|dig' 
			, 'orientamento_produttivo|text|dig' 
			, 'tipologia_struttura|text|dig' 
			, 'modalita_allevamento|text|dig' 
			, 'codice_fiscale_rappresentante|text|dig' 
			, 'comune_leg|text|dig'  
			, 'provincia_leg|text|dig'  
			, 'indirizzo_leg|text|dig' 
			, 'cap_leg|text|dig' 
			, 'email_rappresentante|text|dig'  
			, 'telefono_rappresentante|text|dig'  
			, 'nominativo_proprietario|text|dig'  
			, 'codice_fiscale_proprietario|text|dig' 
			, 'indirizzo_proprietario|text|dig' 
			, 'nominativo_detentore|text|dig' 
			, 'codice_fiscale_detentore |text|dig'  
			, 'indirizzo_detentore|text|dig' 
			, 'note|text|dig' 
			, 'comune|text|dig'  
			, 'provincia_stab|text|dig'  
			, 'indirizzo|text|dig' 
			, 'cap_stab|text|dig'  
			, 'latitudine_stab|text|dig' 
			, 'longitudine_stab|text|dig'  
			, 'data_inizio_attivita|dateISO8601|dig'  
			, 'stato_allevamento|text|dig'  
			, 'codice_azienda|text|dig' 
			, 'categoria_rischio|text|dig'  
			, 'id_controllo_ultima_categorizzazione |text|dig' 
			, 'data_prossimo_controllo|dateISO8601|dig' 
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'scorta_tipo_scorta_codice|text|dig' 
			, 'scorta_num_autorizzazione|text|dig' 
			, 'scorta_data_inizio|dateISO8601|dig'  
			, 'scorta_asl_codice|text|dig'  
			, 'scorta_veterinario_codice_fiscale|text|dig'  
			, 'scorta_veterinario_responsabile|text|dig' 
			, 'piccioni_stima_popolazione|text|dig' 
			, 'piccioni_utilizzo_sistemi|text|dig' 
			, 'piccioni_reti_protezione|text|dig'  
			, 'piccioni_cannoncini_dissuasori|text|dig' 
			, 'piccioni_dissuasori_aghi|text|dig' 
			, 'piccioni_dissuasori_sonori|text|dig'  
			, 'piccioni_altro|text|dig'  
			, 'data_inserimento|dateISO8601|dig'  
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra'
		UNION ALL
		select 
			lpad(n::text,6,'0') as progressivo --[[#4]]
			, cte.asl
			, cte.denominazione
			, cte.specie_allevata::text
			, cte.orientamento_produttivo
			, cte.tipologia_struttura
			, cte.modalita_allevamento
			, cte.codice_fiscale_rappresentante
			--, cte.numero_capi
			, cte.comune_leg
			, cte.provincia_leg
			, cte.indirizzo_leg
			, cte.cap_leg
			, cte.email_rappresentante::Text
			, cte.telefono_rappresentante::text
			, cte.nominativo_proprietario
			, cte.codice_fiscale_proprietario
			, cte.indirizzo_proprietario
			, cte.nominativo_detentore
			, cte.codice_fiscale_detentore 
			, cte.indirizzo_detentore
			, cte.note
			, cte.comune
			, cte.provincia_stab
			, cte.indirizzo
			, cte.cap_stab
			, cte.latitudine_stab::text
			, cte.longitudine_stab::text
			, to_char(cte.data_inizio_attivita,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inizio_attivita
			, cte.stato_allevamento
			, cte.codice_azienda::text
			, cte.categoria_rischio::text 
			, cte.id_controllo_ultima_categorizzazione::text 
			, to_char(cte.data_prossimo_controllo,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_prossimo_controllo
			, cte.macroarea
			, cte.aggregazione
			, cte.attivita
			, cte.codice_macroarea
			, cte.codice_aggregazione
			, cte.codice_attivita
			, cte.scorta_tipo_scorta_codice
			, cte.scorta_num_autorizzazione
			, to_char(cte.scorta_data_inizio::date,'YYYY-MM-DD"T"HH24:MI:SSOF') as scorta_data_inizio
			, cte.scorta_asl_codice
			, cte.scorta_veterinario_codice_fiscale
			, cte.scorta_veterinario_responsabile
			, cte.piccioni_stima_popolazione
			, cte.piccioni_utilizzo_sistemi
			, cte.piccioni_reti_protezione
			, cte.piccioni_cannoncini_dissuasori
			, cte.piccioni_dissuasori_aghi
			, cte.piccioni_dissuasori_sonori
			, cte.piccioni_altro
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
ALTER FUNCTION digemon.dbi_get_anagrafica_aziende_zootecniche(text,text,text)
  OWNER TO postgres;
  
select * from digemon.dbi_get_anagrafica_aziende_zootecniche('2021-01-01','2021-02-28','true') -- chiamata completa
select * from digemon.dbi_get_anagrafica_aziende_zootecniche('2021-01-01','2021-02-28') -- senza ultimo parametro
select * from digemon.dbi_get_anagrafica_aziende_zootecniche() -- senza parametri
