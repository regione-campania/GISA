-- canili (vecchia e nuova)
--select distinct norma, attivita from ricerche_anagrafiche_old_materializzata  where tipo_ricerca_anagrafica = 0 
--intersect  
--select distinct norma,attivita  from ricerche_anagrafiche_old_materializzata  where tipo_ricerca_anagrafica = 1 

--norma vecchia e nuova
--Altra normativa --> canili
--L. 30-91 ed altre norme --> riproduzione animale 
--L. 362-91 FARMACIE 
--REG CE 852-04 --> 852/04 separata
-- Function: digemon.dbi_get_anagrafica_scia_new(text, text, text)

-- DROP FUNCTION digemon.dbi_get_anagrafica_scia_new(text, text, text);
CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_scia_new(
    IN _data_1 text DEFAULT NULL::text,
    IN _data_2 text DEFAULT NULL::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, "nome/ditta/ragione sociale/denominazione sociale" text, "partita iva" text, "codice fiscale" text, "tipo impresa" text, "domicilio digitale" text, "telefono impresa" text, "nome rappresentante legale" text, 
  "cognome rappresentante legale" text, "sesso rappresentante legale" text, "nazione nascita rappresentante legale" text, 
  "comune nascita estero rappresentante legale" text, "data nascita rappresentante legale" text, 
  "codice fiscale rappresentante legale" text, "email rappresentante legale" text, "telefono rappresentante legale" text, "nazione residenza rappresentante legale" text, 
  "indirizzo residenza rappresentante legale" text, "comune residenza rappresentante rapp legale" text, 
  "indirizzo sede legale" text, "nazione sede legale" text, "comune estero sede legale" text, 
  "latitudine sede legale" text, "longitudine sede legale" text, "nazione stabilimento" text, "indirizzo stabilimento" text, 
  "coordinate stabilimento" text, "numero registrazione stabilimento" text, asl text, "note stabilimento" text, "tipo attivita descrizione" text, 
  "data inizio attivita" text, norma text, "codice macroarea" text, "codice aggregazione" text, 
  "codice attivita" text, macroarea text, aggregazione text, attivita text, stato text, "data inserimento" text, 
  riferimento_id text, riferimento_nome_tab text, id_linea text, id_norma text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY
		select
			'progressivo|text|dig'
			, 'nome/ditta/ragione sociale/denominazione sociale|text|dig'
			 , 'partita iva|text|dig'
			 , 'codice fiscale|text|dig'
			 , 'tipo impresa|text|dig'
			 , 'domicilio digitale|text|dig'
			 , 'telefono impresa|text|dig'
			 , 'nome rappresentante legale|text|dig'
			 , 'cognome rappresentante legale|text|dig'
			 , 'sesso rappresentante legale|text|dig'
			 , 'nazione nascita rappresentante legale|text|dig'
			 , 'comune nascita estero rappresentante legale|text|dig'
			 , 'data nascita rappresentante legale|dateISO8601|dig'
			 , 'codice fiscale rappresentante legale|text|dig'
			 , 'email rappresentante legale|text|dig'
			 , 'telefono rappresentante legale|text|dig'
			 , 'nazione residenza rappresentante legale|text|dig'
			 , 'indirizzo residenza rappresentante legale |text|dig'
			 , 'comune residenza rappresentante rapp legale|text|dig'
			 , 'indirizzo sede legale |text|dig'
			 , 'nazione sede legale|text|dig'
			 , 'comune estero sede legale|text|dig'
			 , 'latitudine sede legale|text|dig '
			 , 'longitudine sede legale|text|dig'
			 , 'nazione stabilimento|text|dig'
			 , 'indirizzo stabilimento |text|dig'
			 , 'coordinate stabilimento|text|dig'
			 , 'numero registrazione stabilimento|text|dig'
			 , 'asl|text|dig'
			 , 'note stabilimento|text|dig'
			 , 'tipo attivita descrizione|text|dig'
			 , 'data inizio attivita|dateISO8601|dig'
			 , 'norma|text|dig'
			 , 'codice macroarea|text|dig|ra'
			 , 'codice aggregazione|text|dig|ra'
			 , 'codice attivita|text|dig|ra'
			 , 'macroarea|text|dig'
			 , 'aggregazione|text|dig'
			 , 'attivita|text|dig'
			 , 'stato|text|dig'
			 , 'data inserimento|dateISO8601|dig'
			 , 'riferimento_id|text|ra'
			 , 'riferimento_nome_tab|text|ra'
			 , 'id_linea|text|ra'
			 , 'id_norma|text|ra';
	ELSE
		RETURN QUERY
		with cte as (
			select distinct  
			 row_number() OVER (ORDER BY concat(tab1.asl_stabilimento, tab1.comune_stabilimento, r.data_inserimento,current_timestamp) desc) AS n
			 , tab1.ragione_sociale_impresa  
			 , tab1.partita_iva_impresa  
			 , tab1.codice_fiscale_impresa
			 , tab1.tipo_impresa 
			 , tab1.email_impresa 
			 , tab1.telefono_impresa 
			 , tab1.nome_rapp_legale 
			 , tab1.cognome_rapp_legale 
			 , tab1.sesso_rapp_legale 
			 , tab1.nazione_nascita_rapp_legale 
			 , tab1.comune_nascita_estero_rapp_legale
			 , tab1.data_nascita_rapp_legale 
			 , tab1.cf_rapp_legale 
			 , tab1.email_rapp_legale 
			 , tab1.telefono_rapp_legale 
			 , tab1.nazione_residenza_rapp_legale 
			 , concat_ws(' ',tab1.provincia_residenza_rapp_legale, tab1.comune_residenza_rapp_legale, tab1.toponimo_residenza_rapp_legale, tab1.via_residenza_rapp_legale, tab1.civico_residenza_rapp_legale, tab1.cap_residenza_rapp_legale) as indirizzo_residenza_rapp_legale  
			 , tab1.comune_residenza_estero_rapp_legale
			 , concat_ws(' ',tab1.provincia_sede_legale, tab1.comune_sede_legale, tab1.toponimo_sede_legale, tab1.via_sede_legale, tab1.civico_sede_legale, tab1.cap_sede_legale) as indirizzo_sede_legale  
			 , tab1.nazione_sede_legale 
			 , tab1.comune_estero_sede_legale
			 , tab1.lat_sede_legale
			 , tab1.long_sede_legale
			 , tab1.nazione_stabilimento 
			 , concat_ws(' ', tab1.provincia_stabilimento, tab1.comune_stabilimento, tab1.toponimo_stabilimento, tab1.via_stabilimento, tab1.civico_stabilimento, tab1.cap_stabilimento) as indirizzo_sede_operativa  
			 , concat_ws(' ', tab1.lat_stabilimento, tab1.long_stabilimento) as coordinate
			 , tab1.numero_registrazione_stabilimento
			 , tab1.asl_stabilimento
			 , tab1.note_stabilimento
			 , l.tipo_attivita_descrizione 
			 , l.data_inizio_attivita
			 , l.norma
			 , l.codice_macroarea
			 , l.codice_aggregazione
			 , l.codice_attivita
			 , l.macroarea
			 , l.aggregazione
			 , l.attivita
			 , l.stato
			 , r.data_inserimento
			 , r.riferimento_id
			 , r.riferimento_id_nome_tab
			 , l.id_linea
			 , l.id_norma
			from 
				(select * from get_valori_anagrafica(-1)) tab1 
				--left join (select * from get_valori_anagrafica_linee(-1)) ag on tab1.alt_id = ag.alt_id
				join	digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r on r.riferimento_id = (select return_id from gestione_id_alternativo(tab1.alt_id, -1)) and r.riferimento_id_nome_tab = 'opu_stabilimento'
				join    digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on 
					r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'opu_stabilimento' 
			WHERE 
				r.data_inserimento between _data_1::date and _data_2::date --controllare il formato
		)
		select
			 'progressivo|text|dig'
			, 'nome/ditta/ragione sociale/denominazione sociale|text|dig'
			 , 'partita iva|text|dig'
			 , 'codice fiscale|text|dig'
			 , 'tipo impresa|text|dig'
			 , 'domicilio digitale|text|dig'
			 , 'telefono impresa|text|dig'
			 , 'nome rappresentante legale|text|dig'
			 , 'cognome rappresentante legale|text|dig'
			 , 'sesso rappresentante legale|text|dig'
			 , 'nazione nascita rappresentante legale|text|dig'
			 , 'comune nascita estero rappresentante legale|text|dig'
			 , 'data nascita rappresentante legale|dateISO8601|dig'
			 , 'codice fiscale rappresentante legale|text|dig'
			 , 'email rappresentante legale|text|dig'
			 , 'telefono rappresentante legale|text|dig'
			 , 'nazione residenza rappresentante legale|text|dig'
			 , 'indirizzo residenza rappresentante legale |text|dig'
			 , 'comune residenza rappresentante rapp legale|text|dig'
			 , 'indirizzo sede legale |text|dig'
			 , 'nazione sede legale|text|dig'
			 , 'comune estero sede legale|text|dig'
			 , 'latitudine sede legale|text|dig '
			 , 'longitudine sede legale|text|dig'
			 , 'nazione stabilimento|text|dig'
			 , 'indirizzo stabilimento |text|dig'
			 , 'coordinate stabilimento|text|dig'
			 , 'numero registrazione stabilimento|text|dig'
			 , 'asl|text|dig'
			 , 'note stabilimento|text|dig'
			 , 'tipo attivita descrizione|text|dig'
			 , 'data inizio attivita|dateISO8601|dig'
			 , 'norma|text|dig'
			 , 'codice macroarea|text|dig|ra'
			 , 'codice aggregazione|text|dig|ra'
			 , 'codice attivita|text|dig|ra'
			 , 'macroarea|text|dig'
			 , 'aggregazione|text|dig'
			 , 'attivita|text|dig'
			 , 'stato|text|dig'
			 , 'data inserimento|dateISO8601|dig'
			 , 'riferimento_id|text|ra'
			 , 'riferimento_nome_tab|text|ra'
			 , 'id_linea|text|ra'
			 , 'id_norma|text|ra'
			
		UNION ALL
		select 
			 lpad(n::text,6,'0') as progressivo --[[#4]]
			 , cte.ragione_sociale_impresa  
			 , cte.partita_iva_impresa  
			 , cte.codice_fiscale_impresa
			 , cte.tipo_impresa 
			 , cte.email_impresa 
			 , cte.telefono_impresa 
			 , cte.nome_rapp_legale 
			 , cte.cognome_rapp_legale 
			 , cte.sesso_rapp_legale 
			 , cte.nazione_nascita_rapp_legale 
			 , cte.comune_nascita_estero_rapp_legale
			 , to_char(cte.data_nascita_rapp_legale::date ,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inizio_attivita
			 , cte.cf_rapp_legale 
			 , cte.email_rapp_legale 
			 , cte.telefono_rapp_legale 
			 , cte.nazione_residenza_rapp_legale 
			 , cte.indirizzo_residenza_rapp_legale  
			 , cte.comune_residenza_estero_rapp_legale
			 , cte.indirizzo_sede_legale  
			 , cte.nazione_sede_legale 
			 , cte.comune_estero_sede_legale
			 , cte.lat_sede_legale
			 , cte.long_sede_legale
			 , cte.nazione_stabilimento 
			 , cte.indirizzo_sede_operativa  
			 , cte.coordinate
			 , cte.numero_registrazione_stabilimento
			 , cte.asl_stabilimento
			 , cte.note_stabilimento
			 , cte.tipo_attivita_descrizione::text 
			 , to_char(cte.data_inizio_attivita,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inizio_attivita
			 , cte.norma
			 , cte.codice_macroarea
			 , cte.codice_aggregazione
			 , cte.codice_attivita
			 , cte.macroarea
			 , cte.aggregazione
			 , cte.attivita
			 , cte.stato
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
ALTER FUNCTION digemon.dbi_get_anagrafica_scia_new(text, text, text)
  OWNER TO postgres;
