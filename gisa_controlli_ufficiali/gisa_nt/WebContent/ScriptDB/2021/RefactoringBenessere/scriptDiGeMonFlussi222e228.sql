DROP FUNCTION digemon.dbi_get_report_ba_sa_allevamenti(timestamp without time zone,timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.dbi_get_report_ba_sa_allevamenti(
    IN _data1 timestamp without time zone,
    IN _data2 timestamp without time zone)
  RETURNS TABLE(id_bdn integer, ragione_sociale character varying, riferimento_id integer, riferimento_id_nome_tab text, id_controllo integer, data_controllo timestamp without time zone, stato_controllo text, codice_asl text, codice_azienda character varying, 
  id_fiscale_proprietario text, id_fiscale_detentore text, specie_allevata text, id_alleg integer, nome_checklist character varying, 
  data_invio timestamp without time zone, esito_import_bdn text, descrizione_errore_bdn text, flag_preavviso text, 
  data_preavviso text, flag_rilascio_copia_checklist text, id_chk_bns_mod_ist integer, stato_checklist text, alias_indicatore text, descrizione_indicatore text) AS
$BODY$
DECLARE 	
BEGIN
RETURN QUERY
select * from digemon.dbi_get_report_ba_sa_allevamenti_nuova_gestione(_data1, _data2)
UNION
select * from digemon.dbi_get_report_ba_sa_allevamenti_vecchia_gestione(_data1 , _data2);

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_report_ba_sa_allevamenti(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: digemon.dbi_get_report_ba_sa_allevamenti_nuova_gestione(timestamp without time zone, timestamp without time zone)

-- 
DROP FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_nuova_gestione(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_nuova_gestione(
    IN _data1 timestamp without time zone,
    IN _data2 timestamp without time zone)
  RETURNS TABLE(id_bdn integer, ragione_sociale character varying, riferimento_id integer, riferimento_id_nome_tab text, id_controllo integer, data_controllo timestamp without time zone, stato_controllo text, codice_asl text, codice_azienda character varying, id_fiscale_proprietario text, id_fiscale_detentore text, specie_allevata text, id_alleg integer, nome_checklist character varying, data_invio timestamp without time zone, esito_import_bdn text, descrizione_errore_bdn text, flag_preavviso text, 
  data_preavviso text, flag_rilascio_copia_checklist text, id_chk_bns_mod_ist integer, stato_checklist text, alias_indicatore text, descrizione_indicatore text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_bdn , ragione_sociale, riferimento_id, riferimento_id_nome_tab, id_controllo, data_controllo, stato_controllo, codice_asl,
		codice_azienda, id_fiscale_proprietario, id_fiscale_detentore, specie_allevata, id_alleg, nome_checklist, data_invio, 
		esito_import_bdn, descrizione_errore_bdn, flag_preavviso, data_preavviso, flag_rilascio_copia_checklist, id_chk_bns_mod_ist,  stato_checklist, alias_indicatore, descrizione_indicatore
		in
		select distinct
		ist.id_bdn,
		--COALESCE(ist.id_bdn, ticket.id_bdn, ticket.id_bdn_b11) as id_bdn,
		o.name AS ragione_sociale,
		o.org_id as riferimento_id,
		'organization' as riferimento_id_nome_tab,
		ticket.ticketid AS id_controllo,
		ticket.assigned_date AS data_controllo,
		CASE
			WHEN ticket.closed IS NULL THEN 'APERTO'::text
			WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
			ELSE 'CHIUSO'::text
		END AS stato,
		concat('R', ticket.site_id) AS codice_asl,
		o.account_number AS codice_azienda,
		o.codice_fiscale_rappresentante AS id_fiscale_proprietario,
		o.cf_detentore AS id_fiscale_detentore,
		o.specie_allev,
		lcm.code AS id_alleg,
		lcm.description as nome_checklist,
		COALESCE(ist.data_import, ticket.data_import, ticket.data_import_b11) as data_import,
		COALESCE(ist.esito_import, ticket.esito_import, ticket.esito_import_b11) as esito_import,
		COALESCE(ist.descrizione_errore, ticket.descrizione_errore, ticket.descrizione_errore_b11) as descrizione_errore,
		 CASE
			WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
			ELSE ticket.flag_preavviso
		END AS flag_preavviso,
		to_char(ticket.data_preavviso_ba,'dd-mm-yyyy') AS data_preavviso,
		CASE
			WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
			ELSE 'N'::text
		END AS flag_copia_checklist, ist.id as id_chk_bns_mod_ist,
		CASE WHEN ist.bozza is true then 'APERTA' when ist.bozza is false then 'CHIUSA' when ist.bozza is null then 'MANCANTE' end as stato_checklist, 
		piano.alias_indicatore, piano.descrizione 
		FROM ticket
		JOIN organization o ON ticket.org_id = o.org_id AND o.tipologia = 2 AND o.trashed_date IS NULL
		JOIN tipocontrolloufficialeimprese tcu ON ticket.ticketid = tcu.idcontrollo AND tcu.enabled AND tcu.pianomonitoraggio > 0
		JOIN dpat_indicatore_new piano on piano.id = tcu.pianomonitoraggio
		join rel_motivi_eventi_cu rel on rel.cod_raggrup_ind = piano.cod_raggruppamento
		join lookup_eventi_motivi_cu mot on mot.code = rel.id_evento_cu
		join rel_indicatore_chk_bns relind on relind.codice_raggruppamento = piano.cod_raggruppamento and (ticket.assigned_date between relind.data_inizio_relazione and relind.data_fine_relazione)
		LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = relind.id_lookup_chk_bns
		LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
		LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
		LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
		LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid AND ist.trashed_date IS NULL and ist.id_alleg = lcm.code
		where ticket.tipologia = 3 AND ticket.trashed_date IS NULL ---AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) 
		AND mot.codice_evento in ('isBenessereAnimale', 'isCondizionalita')
		-- sicuri di questo flag per nuova gestione
		AND (lcm.codice_checklist IS NULL OR lcm.codice_checklist = ANY (ARRAY['isBoviniBufalini'::text, 'isBroiler'::text, 'isVitelli'::text, 'isSuini'::text, 'isGallus'::text, 'isAltreSpecie'::text,'isCondizionalita'::text]))
		AND ticket.trashed_date IS NULL AND ticket.assigned_date > '2021-03-05'
		AND ticket.assigned_date between _data1 and _data2

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_nuova_gestione(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

-- Function: digemon.dbi_get_report_ba_sa_allevamenti_vecchia_gestione(timestamp without time zone, timestamp without time zone)

DROP FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_vecchia_gestione(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_vecchia_gestione(
    IN _data1 timestamp without time zone,
    IN _data2 timestamp without time zone)
  RETURNS TABLE(id_bdn integer, ragione_sociale character varying, riferimento_id integer, riferimento_id_nome_tab text, id_controllo integer, data_controllo timestamp without time zone, stato_controllo text, codice_asl text, codice_azienda character varying, id_fiscale_proprietario text, id_fiscale_detentore text, specie_allevata text, id_alleg integer, nome_checklist character varying, data_invio timestamp without time zone, esito_import_bdn text, descrizione_errore_bdn text, flag_preavviso text, 
  data_preavviso text, flag_rilascio_copia_checklist text, id_chk_bns_mod_ist integer, stato_checklist text, alias_indicatore text, descrizione_indicatore text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_bdn , ragione_sociale, riferimento_id, riferimento_id_nome_tab, id_controllo, data_controllo, stato_controllo, codice_asl,
		codice_azienda, id_fiscale_proprietario, id_fiscale_detentore, specie_allevata, id_alleg, nome_checklist, data_invio, 
		esito_import_bdn, descrizione_errore_bdn, flag_preavviso, data_preavviso, flag_rilascio_copia_checklist, id_chk_bns_mod_ist,  stato_checklist, alias_indicatore, descrizione_indicatore
		in
		select distinct
		case 
			when ist.id_alleg  in (23) then ist.id_bdn -- condizionalità nuova gestione SA
			when ist.id_alleg  in (22,20,15) then ticket.id_bdn_b11 -- condizionalità precedente gestione
			when ist.id_alleg not in (20,22,23,15) and lcm.nuova_gestione and lcm.end_date >'2999-12-31' then ist.id_bdn -- significa che sono di nuova gestione e valide attualmente per BA
			else ticket.id_bdn
		end as id_bdn,
		o.name AS ragione_sociale,
		o.org_id as riferimento_id,
		'organization' as riferimento_id_nome_tab,
		ticket.ticketid AS id_controllo,
		ticket.assigned_date AS data_controllo,		
		CASE
			WHEN ticket.closed IS NULL THEN 'APERTO'::text
			WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
			ELSE 'CHIUSO'::text
		END AS stato,
		concat('R', ticket.site_id) AS codice_asl,
		o.account_number AS codice_azienda,
		o.codice_fiscale_rappresentante AS id_fiscale_proprietario,
		o.cf_detentore AS id_fiscale_detentore,
		o.specie_allev,
		lcm.code AS id_alleg,
		lcm.description as nome_checklist,
		COALESCE(ist.data_import, ticket.data_import, ticket.data_import_b11) as data_import,
		COALESCE(ist.esito_import, ticket.esito_import, ticket.esito_import_b11) as esito_import,
		COALESCE(ist.descrizione_errore, ticket.descrizione_errore, ticket.descrizione_errore_b11) as descrizione_errore,
		 CASE
			WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
			ELSE ticket.flag_preavviso
		END AS flag_preavviso,
		to_char(ticket.data_preavviso_ba,'dd-mm-yyyy') AS data_preavviso,
		CASE
			WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
			ELSE 'N'::text
		END AS flag_copia_checklist, ist.id as id_chk_bns_mod_ist,
		CASE WHEN ist.bozza is true then 'APERTA' when ist.bozza is false then 'CHIUSA' when ist.bozza is null then 'MANCANTE' end as stato_checklist, 
		piano.alias_indicatore, piano.descrizione 
		FROM ticket
		JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid AND ist.trashed_date IS NULL 
		LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
		JOIN organization o ON ticket.org_id = o.org_id AND o.tipologia = 2 AND o.trashed_date IS NULL
		JOIN tipocontrolloufficialeimprese tcu ON ticket.ticketid = tcu.idcontrollo AND tcu.enabled AND tcu.pianomonitoraggio > 0
		JOIN dpat_indicatore_new piano on piano.id = tcu.pianomonitoraggio
		join rel_motivi_eventi_cu rel on rel.cod_raggrup_ind = piano.cod_raggruppamento
		join lookup_eventi_motivi_cu mot on mot.code = rel.id_evento_cu
		LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
		LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
		LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
		where ticket.tipologia = 3 AND ticket.trashed_date IS NULL ---AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) 
		AND mot.codice_evento in ('isBenessereAnimale', 'isCondizionalita')
		AND ticket.trashed_date IS NULL AND ticket.assigned_date < '2021-03-05'
		AND ticket.assigned_date between _data1 and _data2

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_report_ba_sa_allevamenti_vecchia_gestione(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

		select * from digemon.dbi_get_report_ba_sa_allevamenti('2021-01-01','2021-03-31')
