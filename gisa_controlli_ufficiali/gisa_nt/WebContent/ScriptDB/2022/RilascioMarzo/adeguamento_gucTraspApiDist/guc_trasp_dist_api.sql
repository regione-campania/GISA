-- GISA
-- View: public.opu_operatori_denormalizzati_view

-- DROP VIEW public.opu_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view AS 
 SELECT DISTINCT
        CASE
            WHEN stab.flag_dia IS NOT NULL THEN stab.flag_dia
            ELSE false
        END AS flag_dia,
    o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice AS codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    concat_ws(' '::text, topsoggind.description, soggind.via, soggind.civico,
        CASE
            WHEN comunisoggind.id > 0 THEN comunisoggind.nome::text
            ELSE soggind.comune_testo
        END, concat('(', provsoggind.cod_provincia, ')'), soggind.cap) AS indirizzo_rapp_sede_legale,
    stati.description AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    stab.data_prossimo_controllo,
    stab.stato AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
        CASE
            WHEN ml.fisso THEN 1
            WHEN stab.tipo_attivita = 1 THEN 1
            WHEN ml.mobile OR latt.codice_macroarea = 'MS.090'::text THEN 2
            WHEN stab.tipo_attivita = 2 THEN 2
            ELSE '-1'::integer
        END AS stab_id_attivita,
        CASE
            WHEN ml.fisso THEN 'Con Sede Fissa'::text
            WHEN stab.tipo_attivita = 1 THEN 'Con Sede Fissa'::text
            WHEN ml.mobile OR latt.codice_macroarea = 'MS.090'::text THEN 'Senza Sede Fissa'::text
            WHEN stab.tipo_attivita = 2 THEN 'Senza Sede Fissa'::text
            ELSE 'N.D.'::text
        END AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
    provsedeop.cod_provincia AS sigla_prov_legale,
    provsoggind.cod_provincia AS sigla_prov_soggfisico,
    comunisoggind.nome AS comune_residenza,
    soggsl.data_nascita AS data_nascita_rapp_sede_legale,
    lotis.code AS impresa_id_tipo_societa,
    soggsl.comune_nascita AS comune_nascita_rapp_sede_legale,
    soggsl.sesso,
    soggind.civico,
    topsoggind.description AS toponimo_residenza,
    soggind.toponimo AS id_toponimo_residenza,
    sedeop.civico AS civico_sede_legale,
    sedeop.toponimo AS tiponimo_sede_legale,
    topsedeop.description AS toponimo_sede_legale,
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN (o.tipo_impresa <> 1 or o.tipo_impresa is null) AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN (o.tipo_impresa <> 1 or o.tipo_impresa is null) AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN (o.tipo_impresa <> 1 or o.tipo_impresa is null) AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN (o.tipo_impresa <> 1 or o.tipo_impresa is null) AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_societa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_societa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN (o.tipo_societa <> 1 or o.tipo_societa is null) AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN (o.tipo_societa <> 1 or o.tipo_societa is null) AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_societa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_societa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN (o.tipo_societa <> 1 or o.tipo_societa is null) AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN (o.tipo_societa <> 1 or o.tipo_societa is null) AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    latt.id_lookup_tipo_linee_mobili,
    '-1'::integer AS id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    latt.codice_macroarea,
    latt.codice_aggregazione,
    latt.codice_attivita AS codice_attivita_only
   FROM opu_operatore o
     JOIN opu_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN opu_stabilimento stab ON stab.id_operatore = o.id
     JOIN opu_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled AND lps.escludi_ricerca IS NOT TRUE
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT JOIN master_list_flag_linee_attivita ml ON ml.id_linea = latt.id_nuova_linea_attivita
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.opu_operatori_denormalizzati_view
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.check_vincoli_richieste(
    _codice_fiscale text,
    _tipologia_richiesta text,
    _id_gestore_acque integer,
    _istat_comune text,
    _num_registrazione text)
  RETURNS text AS
$BODY$
declare
 _msg text;
 _idRichiesta integer;
BEGIN

_msg := '';
_idRichiesta := -1;

RAISE INFO '[CHECK VINCOLI RICHIESTE] _codice_fiscale: %', _codice_fiscale;
RAISE INFO '[CHECK VINCOLI RICHIESTE] _tipologia_richiesta: %', _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE] _id_gestore_acque: %', _id_gestore_acque;
RAISE INFO '[CHECK VINCOLI RICHIESTE] _istat_comune: %', _istat_comune;

IF _tipologia_richiesta ilike 'ACQUE' THEN
select id into _idRichiesta from log_user_reg where tipologia_richiesta = _tipologia_richiesta and id_tipo_iscrizione = _id_gestore_acque and comune_residenza ilike (select nome from comuni1 where istat::integer = _istat_comune::integer);
RAISE INFO '[CHECK VINCOLI RICHIESTE ACQUE] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE GESTORI ACQUE. Esiste giÃ  una richiesta per il gestore e il comune selezionati.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'API' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta and trim(numero_registrazione) ilike trim(_num_registrazione); 
RAISE INFO '[CHECK VINCOLI RICHIESTE API] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE API. Esiste giÃ  una richiesta per il codice fiscale selezionato.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'TRASPORTATORE' or _tipologia_richiesta ilike 'DISTRIBUTORE' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta and trim(numero_registrazione) ilike trim(_num_registrazione) ;
RAISE INFO '[CHECK VINCOLI RICHIESTE TRASP/DIST] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE TRASPORTATORE/DISTRIBUTORE. Esiste giÃ  una richiesta per il codice fiscale e la tipologia selezionati.';
END IF;
END IF;

RAISE INFO '[CHECK VINCOLI RICHIESTE] Esito finale (Vuoto=OK): %', _msg;

return _msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.check_vincoli_richieste(text, text, integer, text, text)
  OWNER TO postgres;



 -- GUC
 -- Function: spid.check_vincoli_ruoli_by_endpoint(text, integer, text)

-- DROP FUNCTION spid.check_vincoli_ruoli_by_endpoint(text, integer, text);
-- Function: spid.check_vincoli_ruoli_by_endpoint(text, integer, text)

-- DROP FUNCTION spid.check_vincoli_ruoli_by_endpoint(text, integer, text);

CREATE OR REPLACE FUNCTION spid.check_vincoli_ruoli_by_endpoint(
    _numero_richiesta text,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$
declare
 _query text;
 _msg text;
 conta_guc integer;
BEGIN

_query := '';
_msg := '';
conta_guc = 0;

RAISE INFO '[CHECK RUOLI BY ENDPOINT] _numero_richiesta: %', _numero_richiesta;
RAISE INFO '[CHECK RUOLI BY ENDPOINT] _id_ruolo: %', _id_ruolo;
RAISE INFO '[CHECK RUOLI BY ENDPOINT] _endpoint: %', _endpoint;

-- CHECK SU GUC PRELIMINARE
conta_guc := (select count(*) from spid.get_lista_ruoli_utente_guc((select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), _endpoint) 
where id_ruolo = _id_ruolo);
    
if conta_guc > 0 then 
	_msg := 'Per il codice fiscale selezionato, esiste gia'' un account con questo ruolo.';
end if;


-- gestore acque
IF _id_ruolo = 10000006 and _endpoint ilike 'GISA_EXT' THEN 
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Avvio check per il ruolo GESTORE ACQUE';
-- Controlla se non c'è un'altra richiesta con lo stesso gestore e lo stesso comune

SELECT 'SELECT * from check_vincoli_richieste(''''::text,'''||('ACQUE')||'''::text, '||(select id_gestore_acque from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'::integer, 
''' || (select comune from spid.spid_registrazioni where numero_richiesta = _numero_richiesta) || '''::text, ''''::text);' into _query;

_msg := (select * from spid.esegui_query(_query, 'gisa', -1,'host=dbGISAL dbname=gisa'));

RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check esistenza RICHIESTE: %', _msg;

-- Controlla se il comune esiste in banca dati
-- Non faccio nulla. Il comune è già mappato come istat.

END IF;

--apicoltore
IF _id_ruolo = 10000002 and _endpoint ilike 'GISA_EXT' THEN
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Avvio check per il ruolo API';
-- Controlla se non c'è un'altra richiesta con lo stesso codice fiscale

SELECT 'SELECT * from check_vincoli_richieste('''||(select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,'''||('API')||'''::text, -1, ''''::text,
'''||(select piva_numregistrazione from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text);' into _query;

_msg := (select * from spid.esegui_query(_query, 'gisa', -1,'host=dbGISAL dbname=gisa'));

RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check esistenza RICHIESTE: %', _msg;

-- Controlla se il comune esiste in banca dati
-- Non faccio nulla. Il comune è già mappato come istat.

END IF;

--trasportatore/distributore
IF _id_ruolo = 10000004 and _endpoint ilike 'GISA_EXT' THEN
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Avvio check per il ruolo TRASPORTATORE/DISTRIBUTORE';
-- Controlla se il CF è valido
-- Non faccio nulla. Lo fa già il modulo e se non lo fa lo deve fare lui e non una dbi

-- Controlla se il comune esiste in banca dati
-- Non faccio nulla. Il comune è già mappato come istat.

-- Controlla se esiste una richiesta con lo stesso codice fiscale e tipologia (trasporto/distr)
SELECT 'SELECT * from check_vincoli_richieste('''||(select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,
'''||(select CASE WHEN id_tipologia_trasp_dist = 1 then 'TRASPORTATORE' WHEN id_tipologia_trasp_dist = 2 then 'DISTRIBUTORE' end from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text, -1, 
''''::text, '''||(select codice_gisa from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text);' into _query;

_msg := (select * from spid.esegui_query(_query, 'gisa', -1,'host=dbGISAL dbname=gisa'));

RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check esistenza RICHIESTE: %', _msg;

IF (_msg = '') THEN 

SELECT 'SELECT * from check_vincoli_utente_trasp_dist('''||(select codice_gisa from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,
'''||(select id_tipologia_trasp_dist from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::integer,
'''||(select comune from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text);' into _query;

_msg := (select * from spid.esegui_query(_query, 'gisa', -1,'host=dbGISAL dbname=gisa'));

RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check VINCOLI: %', _msg;

END IF;

END IF;
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito finale (Vuoto=OK): %', _msg;

return _msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.check_vincoli_ruoli_by_endpoint(text, integer, text)
  OWNER TO postgres;

  -- Function: public.raggruppa_utente_vam_bdu_asl(integer, integer, integer, integer, text)

-- DROP FUNCTION public.raggruppa_utente_vam_bdu_asl(integer, integer, integer, integer, text);

CREATE OR REPLACE FUNCTION public.raggruppa_utente_vam_bdu_asl(
    _id_guc_utente_1 integer,
    _id_ruolo_guc_bdu integer,
    _id_guc_utente_2 integer,
    _id_ruolo_guc_vam integer,
    username_rif text)
  RETURNS text AS
$BODY$

  DECLARE 
	esito text;
	descrizione_errore text;
	conta integer;
	username_out text;
	password_out text;
	id_guc_out text;
	output_guc text;
	output_bdu text;
	output_vam text;
	_query text;
	indice integer;
	_asl_id integer;
	msg text;
	ruolo_bdu text;
	ruolo_vam text;
	num_clinica integer;
	clinica integer[];
	id_cl integer;
	output text;
  BEGIN
	descrizione_errore='';
	esito='';
	output_bdu = '';
	output_vam = '';
	output_guc := '';
	output := '';

	--recupero asl
	_asl_id := (select asl_id from guc_utenti where username ilike username_rif);

	ruolo_bdu := (select ruolo_string from guc_ruoli where id_utente= _id_guc_utente_1 and ruolo_integer = _id_ruolo_guc_bdu and (trashed is null or trashed = false) and endpoint ilike 'bdu');
	ruolo_vam := (select ruolo_string from guc_ruoli where id_utente= _id_guc_utente_2 and ruolo_integer = _id_ruolo_guc_vam and (trashed is null or trashed = false) and endpoint ilike 'vam');

	--disabilito tutti e 2 gli utenti
	output_bdu := (select * from spid.elimina_ruolo_utente_guc(_id_guc_utente_1,_id_ruolo_guc_bdu,'bdu'));
	raise info 'output bdu %', output_bdu;

	output_vam := (select * from spid.elimina_ruolo_utente_guc(_id_guc_utente_2,_id_ruolo_guc_vam,'vam'));

	-- ne creo uno nuovo per guc, bdu e vam 
	select concat(
	concat('select * from dbi_insert_utente_guc(',
	'''', (select codice_fiscale from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select cognome from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
	'''', '1', '''', ', ',
	6567, ', ',
	'NULL::timestamp without time zone', ', ', 
	6567, ', ',
	'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
	'''', (select nome from guc_utenti where username ilike username_rif), '''',  ', ',
	'''', null::character varying, '''', ', ', 
	'''', null::character varying, '''', ', ', 
	(_asl_id), ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	'''', '', '''', ', '), 
	concat('''', (select coalesce(luogo,'') from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select coalesce(num_autorizzazione,'') from  guc_utenti where username ilike username_rif), '''', ', ', 
	-1, ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', (select coalesce(id_provincia_iscrizione_albo_vet_privato,-1) from guc_utenti where username ilike username_rif), '''', ', ',
	'''', (select nr_iscrione_albo_vet_privato from guc_utenti where username ilike username_rif), '''', ', ',
	0, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	0, ', '), 
	concat('''', null::character varying, '''', ', ', 
	'''', (select telefono from guc_utenti where username ilike username_rif), '''', ', ', 
	-1, ', ',--id ruolo gisa
	'''', '', '''', ', ', --descrizione ruolo gisa
	-1, ', ', -- id ruolo gisa_ext
	'''', '' , '''', ', ',--descrizione ruolo gisa_ext
	(_id_ruolo_guc_bdu), ', ',
	'''', (ruolo_bdu), '''', ', ',
	(_id_ruolo_guc_vam), ', ',
	'''', (ruolo_vam), '''', ', ',
	-1, ', ',
	'''', '', '''', ', ',
	-1 , ', ', -- digemon
	'''', '', '''', ', ', -- ruolo digemon
	'''', '-1', '''', ', ',
	-1, ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', null::character varying, '''', ', ', 
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', ', ',
	-1, ', ',
	'''', '', '''', ', ',
	'''', '', '''', '); ')) into _query;

	EXECUTE FORMAT(_query) INTO output_guc;
	output_guc := '{"Esito" : "'||output_guc ||'"}';
	raise info 'output guc: %', output_guc;
	
	-- se inserimento di GUC è andato a buon fine
	
	if(position('OK' in output_guc) > 0) then
		raise info 'entro qui???';
		username_out := (select split_part(output_guc,';;','3'));
		raise info ' username %', username_out;
		id_guc_out := (select split_part(output_guc,';;','2'));
		raise info ' id_guc_out %', id_guc_out;
	
		-- bdu
		select concat(
		'select * from dbi_insert_utente(', 
		'''', username_out, '''', ', ',
		'''', md5(username_out), '''', ', ',
		_id_ruolo_guc_bdu, ', ',
		6567, ', ',
		6567, ', ',
		'''', 'true', '''', ', ',
		_asl_id, ', ',
		'''', (select nome from guc_utenti  where username ilike username_rif), '''',  ', ',
		'''', (select cognome from guc_utenti  where username ilike username_rif), '''', ', ',
		'''', (select codice_fiscale from guc_utenti  where username ilike username_rif), '''', ', ',
		'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
		'''', (select luogo from guc_utenti where username ilike username_rif), '''', ', ',
		'''', null::character varying, '''', ', ', 
		'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
		'NULL::timestamp with time zone', ', ', 
		-1, ', ',
		-1, ', ',	
		'''', (select coalesce(id_provincia_iscrizione_albo_vet_privato,-1) from guc_utenti where username ilike username_rif), '''', ', ',
		'''', (select nr_iscrione_albo_vet_privato from guc_utenti where username ilike username_rif), '''', ', ',
		'''', (select telefono from guc_utenti  where username ilike username_rif), '''', '); ') into _query;	

		--output_bdu := (select * from spid.esegui_query(_query, 'bdu', 6567,'dbname=bdu'));
		output_bdu  := (select t1.output FROM dblink('dbname=bdu'::text, _query) as t1(output text));

		raise info 'output bdu: %', output_bdu;
		output_bdu := '{"Esito" : "'||output_bdu ||'"}';

		-- recupero cliniche vam
		num_clinica :=(select count(*) from guc_cliniche_vam  where id_utente = _id_guc_utente_2);
                clinica:= (select array(select distinct id_clinica  from guc_cliniche_vam  where id_utente = _id_guc_utente_2));
		
		indice := 0;
		WHILE indice < num_clinica 
		LOOP
			id_cl = clinica[indice+1];
			raise info 'clinica %', id_cl;
			select concat(
			'select * from dbi_insert_utente(', 
			'''', username_out, '''', ', ',
			'''', md5(username_out), '''', ', ',
			(_id_ruolo_guc_vam), ', ',
			6567, ', ',
			6567, ', ',
			'''', '1', '''', ', ',
			_asl_id, ', ',
			'''', (select nome from guc_utenti where username ilike username_rif), '''',  ', ',
			'''', (select cognome from guc_utenti where username ilike username_rif), '''', ', ',
			'''', (select codice_fiscale from guc_utenti where username ilike username_rif), '''', ', ',
			'''', 'INSERIMENTO UTENTE PER RICHIESTA DI RAGGRUPPAMENTO UTENTE', '''', ', ',
			'''', (select coalesce(luogo,'') from guc_utenti where username ilike username_rif), '''', ', ',
			'''', null::character varying, '''', ', ', 
			'''', (select email from guc_utenti where username ilike username_rif), '''', ', ',
			'NULL::timestamp with time zone', ', ', 
			id_cl::int, ', ', -----------------------------------inserisco clinica poi ciclo dopo
			'''', '-1', '''', ', ',
			-1, ', ',
			'''', null::character varying, '''', ', ', 
			'''', (select telefono from guc_utenti where username ilike username_rif), '''', '); ') into _query;

			output_vam  := (select t1.output FROM dblink('dbname=vam'::text, _query) as t1(output text));
			output_vam := '{"Esito" : "'||output_vam ||'"}';

			indice = indice+1;
		END LOOP;	
		
		--vam
		if(get_json_valore(output_vam, 'Esito')='OK') then
		-- aggiungere le cliniche in GUC

			_query = (select concat('insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) (select id_clinica, descrizione_clinica, '||id_guc_out||' from guc_cliniche_vam  where id_utente = '||_id_guc_utente_2||') returning ''OK'';'));
			raise info 'stampa query insert vam cliniche%', _query;
			EXECUTE FORMAT(_query) INTO output_guc;
		end if;

		msg := 'OK';
		-- richiamo disattiva utente su utente BDU e VAM
		output := (select * from public.disattiva_utente(_id_guc_utente_1));
		output := (select * from public.disattiva_utente(_id_guc_utente_2));

	else 
		msg := 'KO';
		descrizione_errore := 'Inserimento in GUC fallito.';
	end if;
	return '{"Esito" : "'||msg||'", "DescrizioneErrore" : "'||descrizione_errore||'","username" : "'||username_out||'"}';
	return msg;

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.raggruppa_utente_vam_bdu_asl(integer, integer, integer, integer, text)
  OWNER TO postgres;
  
  -- Function: public.insert_soggetto_fisico_per_apicoltore(text, text, text, text, integer, text, text, text, text, text, text, text)

-- DROP FUNCTION public.insert_soggetto_fisico_per_apicoltore(text, text, text, text, integer, text, text, text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.insert_soggetto_fisico_per_apicoltore(
    _nome text,
    _cognome text,
    _codice_fiscale text,
    _sesso text,
    _enteredby integer,
    _telefono text,
    _email text,
    _provincia_residenza text,
    _cap_residenza text,
    _indirizzo_residenza text,
    _comune_testo text,
    _istat_comune text)
  RETURNS integer AS
$BODY$
DECLARE 

	conta_soggetto integer;
        _id_indirizzo integer;
        _id_sogg integer;
BEGIN	

	conta_soggetto :=0;
	_id_indirizzo := 0;
	conta_soggetto := (select count(*) from opu_soggetto_fisico where upper(codice_fiscale) = upper(_codice_fiscale) and trashed_date is null);
	if conta_soggetto > 0 then
		_id_sogg= -1; -- soggetto già esistente nel sistema
	ELSE
		-- recupera indirizzo 
		_id_indirizzo :=(select id from opu_indirizzo where trim(via) ilike trim(_indirizzo_residenza) and trim(upper(comune_testo))= trim(upper(_comune_testo)) and comune = (select id from comuni1 where istat_comune_provincia = _istat_comune));

		if _id_indirizzo > 0 then 
			
			insert into opu_soggetto_fisico(cognome, nome, codice_fiscale, enteredby, sesso, telefono, email, indirizzo_id)
			values(_cognome, _nome, _codice_fiscale::character varying, _enteredby, _sesso::character, _telefono::character, _email::character varying, _id_indirizzo) returning id into _id_sogg;
		else
			-- inserisco indirizzo e soggetto fisico
			insert into opu_indirizzo(via, cap, provincia, comune, comune_testo) values (_indirizzo_residenza::character varying, _cap_residenza::character varying, (select cod_provincia::character varying from comuni1 where istat_comune_provincia = _istat_comune), 
			(select id from comuni1 where istat_comune_provincia = _istat_comune), _comune_testo) returning id into _id_indirizzo;
			insert into opu_soggetto_fisico(cognome, nome, codice_fiscale, enteredby, sesso, telefono, email, indirizzo_id)
			values(_cognome, _nome, _codice_fiscale::character varying, _enteredby, _sesso::character varying, _telefono::character varying, _email::character varying, _id_indirizzo) returning id into _id_sogg;
		end if;
	END IF;

	return _id_sogg;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_soggetto_fisico_per_apicoltore(text, text, text, text, integer, text, text, text, text, text, text, text)
  OWNER TO postgres;

