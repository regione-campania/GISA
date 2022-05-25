
--da gestire la categorizzazione e i dati aggiuntivi select * from get_valori_anagrafica_linee(20215768)

DROP FUNCTION digemon.dbi_get_canili_org(text,text)
CREATE OR REPLACE FUNCTION digemon.dbi_get_canili_org(IN _data_1 text default null::text, IN _data_2 text default null::text)
  RETURNS TABLE("progressivo" text
			, "nome/ditta/ragione sociale/denominazione sociale" text
			 , "partita iva" text
			 , "codice fiscale" text
			 , "tipo impresa" text
			 , "domicilio digitale" text
			 , "telefono impresa" text
			 , "nome rappresentante legale" text
			 , "cognome rappresentante legale" text
			 , "sesso rappresentante legale" text
			 , "nazione nascita rappresentante legale" text
			 , "comune nascita estero rappresentante legale" text
			 , "data nascita rappresentante legale" text
			 , "codice fiscale rappresentante legale" text
			 , "email rappresentante legale" text
			 , "telefono rappresentante legale" text
			 , "nazione residenza rappresentante legale" text
			 , "indirizzo residenza rappresentante legale" text
			 , "comune residenza rappresentante rapp legale" text
			 , "indirizzo sede legale" text
			 , "nazione sede legale" text
			 , "comune estero sede legale" text
			 , "latitudine sede legale" text
			 , "longitudine sede legale" text
			 , "nazione stabilimento" text
			 , "indirizzo stabilimento" text
			 , "coordinate stabilimento" text
			 , "numero registrazione stabilimento" text
			 , "asl" text
			 , "note stabilimento" text
			 , "tipo attivita descrizione" text
			 , "data inizio attivita" text
			 , "norma" text
			 , "codice macroarea" text
			 , "codice aggregazione" text
			 , "codice attivita" text
			 , "macroarea" text
			 , "aggregazione" text
			 , "attivita" text
			 , "stato" text
			 , "data inserimento" text
			 , "riferimento_id" text
			 , "riferimento_nome_tab" text
			 , "id_linea" text
			 , "id_norma" text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
	with cte as (
			select distinct  
			 row_number() OVER (ORDER BY concat(asl.description, oaope.city, tab1.entered,current_timestamp) desc) AS n
			 , tab1.name as ragione_sociale_impresa
			 , tab1.partita_iva as partita_iva_impresa  
			 , tab1.codice_fiscale as codice_fiscale_impresa
			 , '' as tipo_impresa
			 , '' as email_impresa
			 , '' as telefono_impresa 
			 , tab1.nome_rappresentante as nome_rapp_legale
			 , tab1.cognome_rappresentante as cognome_rapp_legale 
			 , '' as sesso_rapp_legale --verifica in organization
			 , '' as nazione_nascita_rapp_legale 
			 , '' as comune_nascita_estero_rapp_legale
			 , tab1.data_nascita_rappresentante as data_nascita_rapp_legale 
			 , tab1.codice_fiscale_rappresentante as cf_rapp_legale 
			 , tab1.email_rappresentante as email_rapp_legale 
			 , tab1.telefono_rappresentante as telefono_rapp_legale 
			 , '' as nazione_residenza_rapp_legale 
			 , '' as indirizzo_residenza_rapp_legale  
			 , '' as comune_residenza_estero_rapp_legale
			 , concat_ws(' ',oaleg.state, oaleg.city, oaleg.addrline1, oaleg.postalcode) as indirizzo_sede_legale  
			 , oaleg.country as nazione_sede_legale 
			 , '' as comune_estero_sede_legale
			 , oaleg.latitude as lat_sede_legale
			 , oaleg.longitude as long_sede_legale
			 , oaope.country as nazione_stabilimento 
			 , concat_ws(' ', oaope.state, oaope.city, oaope.addrline1, oaope.postalcode) as indirizzo_sede_operativa  
			 , concat_ws(' ', oaope.latitude, oaope.longitude) as coordinate
			 , '' as numero_registrazione_stabilimento
			 , asl.description as asl_stabilimento
			 , '' as note_stabilimento
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
			 , tab1.entered as data_inserimento
			 , l.riferimento_id
			 , l.riferimento_id_nome_tab
			 , l.id_linea
			 , l.id_norma
			from 
				organization tab1
				left join organization_address oaleg on (tab1.org_id = oaleg.org_id and oaleg.address_type = 1) 
				left join organization_address oaope on (tab1.org_id = oaope.org_id and oaope.address_type = 5) 
				left join lookup_site_id asl on tab1.site_id = asl.code 
				left join digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on tab1.org_id = l.riferimento_id and l.riferimento_id_nome_tab = 'organization' 
			WHERE 
				tab1.entered between _data_1::date and _data_2::date --controllare il formato
				and tab1.tipologia in (10) and tab1.trashed_date is null
		)
		select 
			 lpad(n::text,6,'0') as progressivo --[[#4]]
			 , cte.ragione_sociale_impresa::text  
			 , cte.partita_iva_impresa::text  
			 , cte.codice_fiscale_impresa::text
			 , cte.tipo_impresa::text
			 , cte.email_impresa::text 
			 , cte.telefono_impresa::text 
			 , cte.nome_rapp_legale::text 
			 , cte.cognome_rapp_legale::text 
			 , cte.sesso_rapp_legale::text 
			 , cte.nazione_nascita_rapp_legale::text 
			 , cte.comune_nascita_estero_rapp_legale::text
			 , to_char(cte.data_nascita_rapp_legale::date ,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inizio_attivita
			 , cte.cf_rapp_legale::text 
			 , cte.email_rapp_legale::text 
			 , cte.telefono_rapp_legale::text 
			 , cte.nazione_residenza_rapp_legale::text 
			 , cte.indirizzo_residenza_rapp_legale::text  
			 , cte.comune_residenza_estero_rapp_legale::text
			 , cte.indirizzo_sede_legale::text  
			 , cte.nazione_sede_legale::text 
			 , cte.comune_estero_sede_legale::text
			 , cte.lat_sede_legale::text
			 , cte.long_sede_legale::text
			 , cte.nazione_stabilimento::text 
			 , cte.indirizzo_sede_operativa::text  
			 , cte.coordinate::text
			 , cte.numero_registrazione_stabilimento::text
			 , cte.asl_stabilimento::text
			 , cte.note_stabilimento::text
			 , cte.tipo_attivita_descrizione::text 
			 , to_char(cte.data_inizio_attivita,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inizio_attivita
			 , cte.norma::text
			 , cte.codice_macroarea::text
			 , cte.codice_aggregazione::text
			 , cte.codice_attivita::text
			 , cte.macroarea::text
			 , cte.aggregazione::text
			 , cte.attivita::text
			 , cte.stato
			 , to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inserimento
			 , cte.riferimento_id::text
			 , cte.riferimento_id_nome_tab::text
			 , cte.id_linea::text
			 , cte.id_norma::text
		FROM cte;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_canili_org(text,text)
  OWNER TO postgres;

select * from digemon.dbi_get_canili_org('2005-01-01','2020-12-31')