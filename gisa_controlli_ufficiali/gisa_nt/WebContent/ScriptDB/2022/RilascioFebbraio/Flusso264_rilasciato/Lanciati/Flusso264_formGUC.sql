CREATE TABLE spid.spid_registrazioni_esiti (
id serial primary key,
numero_richiesta text, 
esito_guc boolean default NULL, 
esito_gisa boolean default NULL, 
esito_gisa_ext boolean default  NULL, 
esito_bdu boolean default  NULL, 
esito_vam boolean default  NULL, 
esito_digemon boolean default  NULL, 
data_esito timestamp without time zone default now(),
id_utente_esito integer,
trashed_date timestamp without time zone,
stato integer default 0,
json_esito text); 


insert into PERMESSI_RUOLI (id, descrizione, nome) values (3, 'Amministra richieste di validazione', 'GestoreRichiesteValidazione');

-- Inserire da Gestione utenti l'utente per questo ruolo


------------------------------------------------------------------------------------- 
  -- Function: spid.get_lista_richieste(text)

-- DROP FUNCTION spid.get_lista_richieste(text);
CREATE OR REPLACE FUNCTION spid.get_lista_richieste(IN _numero_richiesta text DEFAULT NULL::text)
  RETURNS TABLE(id integer, id_tipologia_utente integer, tipologia_utente text, id_tipo_richiesta integer, tipo_richiesta text, cognome text, nome text, codice_fiscale text, email text, telefono text, id_ruolo_gisa integer, ruolo_gisa text, id_ruolo_bdu integer, ruolo_bdu text, id_ruolo_vam integer, ruolo_vam text, id_ruolo_gisa_ext integer, ruolo_gisa_ext text, id_ruolo_digemon integer, ruolo_digemon text, id_clinica_vam integer, clinica_vam text, identificativo_ente text, piva_numregistrazione text, comune text, istat_comune text, nominativo_referente text, ruolo_referente text, email_referente text, telefono_referente text, data_richiesta timestamp without time zone, codice_gisa text, indirizzo text, id_gestore_acque integer, gestore_acque text, cap text, pec text, numero_richiesta text, esito_guc boolean, esito_gisa boolean, esito_gisa_ext boolean, esito_bdu boolean, esito_vam boolean, esito_digemon boolean, data_esito timestamp without time zone, stato integer, id_asl integer, asl text, provincia_ordine_vet text, numero_ordine_vet text, numero_decreto_prefettizio text, scadenza_decreto_prefettizio text, numero_autorizzazione_regionale_vet text, id_tipologia_trasp_dist integer) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

SELECT r.id, r.id_tipologia_utente, tip.descr as tipologia_utente, r.id_tipo_richiesta, ric.descr as tipologia_richiesta, 
r.cognome::text, r.nome::text, r.codice_fiscale::text, r.email::text, r.telefono::text, r.id_ruolo_gisa::int, g.ruolo as ruolo_gisa, r.id_ruolo_bdu::int,
b.ruolo as ruolo_bdu, r.id_ruolo_vam::int, v.ruolo as ruolo_vam, r.id_ruolo_gisa_ext::int, ge.ruolo as ruolo_ext, r.id_ruolo_digemon::int, dg.ruolo as ruolo_digemon,
r.id_clinica_vam::int, cli.nome_clinica, r.identificativo_ente::text, r.piva_numregistrazione::text, 
(select comuni.comune::text from comuni where codiceistatcomune::integer = (select case when length(r.comune)> 0 then r.comune::integer else -1 end)), coalesce(r.comune,'-1')::text as istat_comune, 
r.nominativo_referente::text,
r.ruolo_referente::text, r.email_referente::text, r.telefono_referente::text, r.data_richiesta, r.codice_gisa::text,
r.indirizzo::text, r.id_gestore_acque::integer, gac.nome, r.cap::text, r.pec::text, r.numero_richiesta::text,
es.esito_guc, es.esito_gisa, es.esito_gisa_ext, es.esito_bdu, es.esito_vam, es.esito_digemon, es.data_esito, es.stato,
r.id_asl, l.nome::text, r.provincia_ordine_vet::text, r.numero_ordine_vet::text, r.numero_decreto_prefettizio::text, r.scadenza_decreto_prefettizio::text,
r.numero_autorizzazione_regionale_vet::text, r.id_tipologia_trasp_dist
from spid.spid_registrazioni r 
left join asl l on l.id = r.id_asl
join spid.spid_tipo_richiesta ric on ric.id = r.id_tipo_richiesta
join spid.spid_tipologia_utente tip on tip.id = r.id_tipologia_utente
left join spid.spid_registrazioni_esiti es on es.numero_richiesta = r.numero_richiesta and es.trashed_date is null
left join (
select * 
   FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT 
			id, nome::text from public_functions.dbi_get_gestori_acque(-1)')  -----> perchï¿½ non va bene passare il valore???
t1(id integer, nome text)
) gac on gac.id = r.id_gestore_acque::integer
left join (
select * 
   FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT 
			id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') 
t1(id integer, ruolo text)
) g on r.id_ruolo_gisa = g.id
left join (
select * 
   FROM dblink('host=dbBDUL dbname=bdu'::text, 'SELECT 
			id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') 
t1(id integer, ruolo text)
) b on r.id_ruolo_bdu = b.id
left join (
select * 
   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') 
t1(id integer, ruolo text)
) v on r.id_ruolo_vam = v.id 
left join (
select * 
   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			id_clinica, nome_clinica from dbi_get_cliniche_utente()') 
t1(id_clinica integer, nome_clinica text)
) cli on cli.id_clinica = r.id_clinica_vam
left join (
select * 
   FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT 
			id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente_ext()') 
t1(id integer, ruolo text)
) ge on r.id_ruolo_gisa_ext = ge.id 
left join (
select * 
   FROM dblink('host=dbDIGEMONL dbname=digemon_u'::text, 'SELECT 
			id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') 
t1(id integer, ruolo text)
) dg on r.id_ruolo_digemon = dg.id 
where 1=1 
and (_numero_richiesta is null or r.numero_richiesta = _numero_richiesta)
order by r.data_richiesta desc, r.numero_richiesta;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION spid.get_lista_richieste(text)
  OWNER TO postgres;



--select * from spid.get_lista_richieste('2021-RIC-00000338')

CREATE OR REPLACE FUNCTION spid.check_validita_ruolo_cf(
    _codice_fiscale text,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$
 DECLARE 
	_idutente integer;
	conta integer;	
	out text;
	esito text;
	descrizione_errore text;
  BEGIN

	descrizione_errore ='';
	esito='';
	_idutente = -1;
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 1) then
		raise info 'trovati piu'' utenti';
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare l''utente per questo codice fiscale e ruolo';
	elsif (conta = 0) then
		raise info 'nessun utente trovato';
		esito = 'KO';
		descrizione_errore = 'Non esiste alcun utente con questo codice fiscale e ruolo.';
	else 
		raise info 'Trovato utente!';
		select u.id into _idutente from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo;

		raise info 'id utente GUC: %', _idutente;
		
		esito = 'OK';
	end if;
	 
				   
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'", "IdRuolo": "'||_id_ruolo||'", "IdUtente": "'||_idutente||'"}';

	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.check_validita_ruolo_cf(text, integer, text)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_json_valore(_jsontext text, _chiave text)
  RETURNS text AS
$BODY$
declare
 _json json; 
 _valore text;
BEGIN

_valore := '';

RAISE INFO '[get_parametro_da_json] _jsontext: %', _jsontext;

BEGIN
SELECT _jsontext::json into _json;
exception when others then raise info 'ERRORE JSON NON VALIDO';
return '';
END;

SELECT _json ->> _chiave into _valore;

return _valore;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

 
-- DROP FUNCTION spid.processa_richiesta(text, integer);
-- Function: spid.processa_richiesta(text, integer)

-- DROP FUNCTION spid.processa_richiesta(text, integer);
-- Function: spid.processa_richiesta(text, integer)

-- DROP FUNCTION spid.processa_richiesta(text, integer);
-- Function: spid.processa_richiesta(text, integer)

-- DROP FUNCTION spid.processa_richiesta(text, integer);
-- Function: spid.processa_richiesta(text, integer)

-- DROP FUNCTION spid.processa_richiesta(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
 idtiporichiesta integer;
 out_insert text;
 out_modify text;
 out_delete text;
 output text;
 codicefiscale text;
 query text;
BEGIN
	-- recupero id tipo richiesta
	idtiporichiesta := (select id_tipo_richiesta from spid.spid_registrazioni  where numero_richiesta = _numero_richiesta);
	codicefiscale := (select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta);
	
	if (idtiporichiesta = 1) then -- inserimento
		query := 'select * from spid.processa_richiesta_insert('''||_numero_richiesta||''', '||_id_utente||');';
		output := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	elsif (idtiporichiesta = 2) then --modifica
		query := 'select * from spid.processa_richiesta_modifica('''||_numero_richiesta||''', '||_id_utente||');';
		output := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	elsif (idtiporichiesta = 3) then
		query := 'select * from spid.processa_richiesta_elimina('''||_numero_richiesta||''', '||_id_utente||');';
		output := (select * from spid.esegui_query(query, 'guc', _id_utente,''));	else 
		return 'qualcosa ï¿½ andato storto';
	end if;
	
	return output;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta(text, integer)
  OWNER TO postgres;




create table spid.spid_registrazioni_flag(id serial primary key, numero_richiesta text, in_nucleo boolean, in_dpat boolean, trashed_date timestamp without time zone);


CREATE OR REPLACE FUNCTION spid.aggiorna_flag(
    _numero_richiesta text,
    _in_nucleo boolean,
    _in_dpat boolean)
  RETURNS text AS
$BODY$
declare
 _id integer;
BEGIN
update spid.spid_registrazioni_flag set trashed_date = now() where numero_richiesta = _numero_richiesta;
insert into spid.spid_registrazioni_flag (numero_richiesta, in_nucleo, in_dpat) values (_numero_richiesta, _in_nucleo, _in_dpat);
return 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION spid.rifiuta_richiesta(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
 _id integer;
BEGIN

UPDATE spid.spid_registrazioni_esiti set id_utente_esito= _id_utente,  stato=2 where numero_richiesta = _numero_richiesta and trashed_date is null;
insert into spid.spid_registrazioni_esiti(numero_richiesta, stato,id_utente_esito)  values (_numero_richiesta,2, _id_utente);
return '{"Esito" : "OK"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

 
  ------- new 22/11 db guc
  
CREATE OR REPLACE FUNCTION spid.insert_guc_clinica(_id_utente integer, _id_clinica integer, _descrizione_clinica text)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
 
  BEGIN
   
    INSERT INTO guc_cliniche_vam (id_utente, id_clinica, descrizione_clinica) VALUES (_id_utente, _id_clinica, _descrizione_clinica)		
    RETURNING id into ret_id;
  
  RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION  spid.insert_guc_clinica(integer, integer, text)
  OWNER TO postgres;

------------ 23/11
  
  -----------------------------------------------------------------------------------------
-------------------------------------- GUC ----------------------------------------------
-----------------------------------------------------------------------------------------
-- Function: spid.check_ruoli_by_endpoint(text, integer, text)

-- 
DROP FUNCTION spid.check_ruoli_by_endpoint(text, integer, text);

CREATE OR REPLACE FUNCTION spid.check_vincoli_ruoli_by_endpoint(
    _numero_richiesta text,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$
declare
 _msg text;
BEGIN

_msg := '';

RAISE INFO '[CHECK RUOLI BY ENDPOINT] _numero_richiesta: %', _numero_richiesta;
RAISE INFO '[CHECK RUOLI BY ENDPOINT] _id_ruolo: %', _id_ruolo;
RAISE INFO '[CHECK RUOLI BY ENDPOINT] _endpoint: %', _endpoint;

-- gestore acque
IF _id_ruolo = 10000006 and _endpoint ilike 'GISA_EXT' THEN 
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Avvio check per il ruolo GESTORE ACQUE';
-- Controlla se non c'è un'altra richiesta con lo stesso gestore e lo stesso comune
select t1.esito_check into _msg FROM dblink('dbname=gisa'::text, 'SELECT * from check_vincoli_richieste(''''::text,'''||('ACQUE')||'''::text, '||(select id_gestore_acque from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'::integer, ''' || (select comune from spid.spid_registrazioni where numero_richiesta = _numero_richiesta) || '''::text);') as t1(esito_check text);
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check esistenza RICHIESTE: %', _msg;

-- Controlla se il comune esiste in banca dati
-- Non faccio nulla. Il comune è già mappato come istat.

END IF;

--apicoltore
IF _id_ruolo = 10000002 and _endpoint ilike 'GISA_EXT' THEN
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Avvio check per il ruolo API';
-- Controlla se non c'è un'altra richiesta con lo stesso codice fiscale
select t1.esito_check into _msg FROM dblink('dbname=gisa'::text, 'SELECT * from check_vincoli_richieste('''||(select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,'''||('API')||'''::text, -1, ''''::text);') as t1(esito_check text);
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
select t1.esito_check into _msg FROM dblink('dbname=gisa'::text, 'SELECT * from check_vincoli_richieste('''||(select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,'''||(select CASE WHEN id_tipologia_trasp_dist = 1 then 'TRASPORTATORI' WHEN id_tipologia_trasp_dist = 2 then 'DISTRIBUTORI' end from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text, -1, ''''::text);') as t1(esito_check text);
RAISE INFO '[CHECK RUOLI BY ENDPOINT] Esito del check esistenza RICHIESTE: %', _msg;

IF (_msg = '') THEN 

select t1.esito_check into _msg FROM dblink('dbname=gisa'::text, 'SELECT * from check_vincoli_utente_trasp_dist('''||(select codice_gisa from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text,'''||(select id_tipologia_trasp_dist from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::integer,'''||(select comune from spid.spid_registrazioni where numero_richiesta = _numero_richiesta)||'''::text);') as t1(esito_check text);
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


-----------------------------------------------------------------------------------------
-------------------------------------- GISA ---------------------------------------------
-----------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION check_vincoli_utente_trasp_dist(
    _num_registrazione text,
    _tipo_trasp_dist integer,
    _istat_comune_richiesta text)
  RETURNS text AS
$BODY$
declare
 _msg text;
 _id_stabilimento integer;
BEGIN

_msg := '';
_id_stabilimento := -1;

RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] _num_registrazione: %', _num_registrazione;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] _istat_comune_richiesta: %', _istat_comune_richiesta;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] _tipo_trasp_dist: %', _tipo_trasp_dist;

-- Controlla se esiste il numero registrazione
SELECT id_stabilimento into _id_stabilimento from opu_operatori_denormalizzati_view where numero_registrazione = _num_registrazione;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] Check esistenza numreg _id_stabilimento: %', _id_stabilimento;
IF _id_stabilimento is null THEN
_msg := 'NUMERO REGISTRAZIONE non esistente nel sistema.';
END IF;

IF _id_stabilimento > 0 THEN
-- Controlla se il numero registrazione corrisponde a un'anagrafica con tipologia mobili = a quella della richiesta
SELECT id_stabilimento into _id_stabilimento from opu_operatori_denormalizzati_view where numero_registrazione = _num_registrazione and id_lookup_tipo_linee_mobili = _tipo_trasp_dist;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] Check esistenza numreg + tipo mobile _id_stabilimento: %', _id_stabilimento;
IF _id_stabilimento is null THEN
_msg := 'NUMERO REGISTRAZIONE esistente nel sistema ma non associato ad anagrafica con TIPOLOGIA TRASPORTATORE/DISTRIBUTORE della richiesta.';
END IF;
END IF;

IF _id_stabilimento > 0 THEN
-- Controlla se esiste il numero registrazione su stabilimento attivo
SELECT id_stabilimento into _id_stabilimento from opu_operatori_denormalizzati_view where numero_registrazione = _num_registrazione and id_lookup_tipo_linee_mobili = _tipo_trasp_dist and id_stato = 0;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] Check esistenza numreg + tipo mobile + attivo _id_stabilimento: %', _id_stabilimento;
IF _id_stabilimento is null THEN
_msg := 'NUMERO REGISTRAZIONE esistente nel sistema ma non associato ad anagrafica ATTIVA.';
END IF;
END IF;

IF _id_stabilimento > 0 THEN
-- Controlla se il comune richiesta Ã¨ uguale al comune stabilimento
SELECT s.id_stabilimento into _id_stabilimento from opu_operatori_denormalizzati_view s join comuni1 c on c.id = s.id_comune_richiesta where s.numero_registrazione = _num_registrazione and id_lookup_tipo_linee_mobili = _tipo_trasp_dist and s.id_stato = 0 and c.istat = _istat_comune_richiesta;
RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] Check esistenza numreg + tipo mobile + attivo + comune _id_stabilimento: %', _id_stabilimento;
if _id_stabilimento is null THEN
_msg := 'NUMERO REGISTRAZIONE esistente nel sistema ma non associato ad anagrafica con COMUNE = COMUNE RICHIESTA.';
END IF;
END IF;

RAISE INFO '[CHECK VINCOLI UTENTE TRASP DIST] Esito finale (Vuoto=OK): %', _msg;

return _msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-----------------------------------------------------------------------------------------
-------------------------------------- GISA ---------------------------------------------
-----------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION check_vincoli_richieste(
    _codice_fiscale text,
    _tipologia_richiesta text,
    _id_gestore_acque integer,
    _istat_comune text)
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
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE API] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE API. Esiste giÃ  una richiesta per il codice fiscale selezionato.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'TRASPORTATORI' or _tipologia_richiesta ilike 'DISTRIBUTORI' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE TRASP/DIST] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE TRASPORTATORI/DISTRIBUTORI. Esiste giÃ  una richiesta per il codice fiscale e la tipologia selezionati.';
END IF;
END IF;

RAISE INFO '[CHECK VINCOLI RICHIESTE] Esito finale (Vuoto=OK): %', _msg;

return _msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- 24/11/2021
-- Function: spid.processa_richiesta_insert(text, integer)

-- DROP FUNCTION spid.processa_richiesta_insert(text, integer);
-- Function: spid.processa_richiesta_insert(text, integer)

-- DROP FUNCTION spid.processa_richiesta_insert(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_insert(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
DECLARE
	--r RECORD;
	
	ep_guc text;
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;
	clinica_out integer;
	_id_tipo_richiesta integer;
	username_out text;
	password_out text;
	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	esito_processa text;
	check_gisa text;
	check_vam text;
	check_bdu text;
	check_digemon text;
	check_gisa_ext text;

	query text;
	query_endpoint text;
	esito_api text;
	esito_dist text;
	esito_acque text;
	esito_guc text;
BEGIN

	output_gisa = '';
	output_gisa_ext = '';
	output_bdu = '' ;
	output_vam = '';
	output_digemon = '';
	ep_gisa = -1;
	ep_gisa_ext = -1;
	ep_bdu = -1;
	ep_vam = -1;
	ep_digemon = -1;
	

	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_id_tipo_richiesta := (select id_tipo_richiesta from spid.spid_registrazioni  where numero_richiesta = _numero_richiesta);
	--codicefiscale := (select codice_fiscale from spid.spid_registrazioni where numero_richiesta = _numero_richiesta);
	

	-- chiamo dbi dei check
	if(ep_gisa > 0) then
		query := 'select * from spid.check_vincoli_ruoli_by_endpoint('''||_numero_richiesta||''', '||ep_gisa||', ''gisa'');';
		check_gisa := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	end if;

	if(ep_gisa_ext > 0) then
		query := 'select * from spid.check_vincoli_ruoli_by_endpoint('''||_numero_richiesta||''', '||ep_gisa_ext||', ''gisa_ext'');';
		check_gisa_ext := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	end if;

	if(ep_bdu > 0) then
		query := 'select * from spid.check_vincoli_ruoli_by_endpoint('''||_numero_richiesta||''', '||ep_bdu||', ''bdu'');';
		check_bdu := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	end if;
	
	if(ep_vam > 0) then
		query := 'select * from spid.check_vincoli_ruoli_by_endpoint('''||_numero_richiesta||''', '||ep_vam||', ''vam'');';
		check_vam := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	end if;
	
	if(ep_vam > 0) then
		query := 'select * from spid.check_vincoli_ruoli_by_endpoint('''||_numero_richiesta||''', '||ep_digemon||', ''digemon'');';
		check_digemon := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
	end if;
	
	if (length(check_gisa)>0 or length(check_gisa_ext)>0 or length(check_bdu)>0 or length(check_vam)>0 or length(check_digemon)>0) then --errore
		return '{Esito:"KO", DescrizioneErrore:"'||concat(check_gisa, check_gisa_ext, check_bdu, check_vam, check_digemon)||'"}';

	else --continuo con il resto
	
		if (_id_tipo_richiesta = 1) then -- inserimento utente
			query := (select * from spid.get_dbi_per_endpoint(_numero_richiesta,'guc','insert',_id_utente,null,null,null::timestamp without time zone,-1)); 
			ep_guc:= (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		end if;

		username_out := (select split_part(ep_guc,';;','3'));
		raise info ' username %', username_out;
		password_out := (select split_part(ep_guc,';;','4'));
		raise info ' password %', password_out;
		esito_guc := (select split_part(ep_guc,';;','1'));
		raise info ' esito insert guc %', esito_guc;

		if (ep_gisa > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1));
			output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			output_gisa := '{"Esito" : "'||output_gisa ||'"}';
		end if;

		if (ep_gisa_ext > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1));
			output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			output_gisa_ext := '{"Esito" : "'||output_gisa_ext ||'"}';
		end if;
	    
		if (ep_bdu > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1));
			output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
			output_bdu := '{"Esito" : "'||output_bdu ||'"}';
		end if;

		if (ep_vam > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1));
			output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			output_vam := '{"Esito" : "'||output_vam ||'"}';
		end if;

		if (ep_digemon > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1));
			output_digemon := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbDIGEMONL dbname=digemon_u'));
			output_digemon := '{"Esito" : "'||output_digemon ||'"}';
		end if;

		esito_processa = '{"EndPoint" : "GUC", "Esito": "'||(select split_part(ep_guc,';;','1'))||'", 
				   "Username" : "'||username_out||'", "ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}
				   ]
				}';		

		RAISE INFO 'esito_processa= %', esito_processa;

		if(get_json_valore(output_gisa_ext, 'Esito')='OK') then
			-- gestione ruolo gestore acque
			if ep_gisa_ext = 10000006 then 
				query = (select concat('SELECT * from dbi_insert_log_user_reg(''', (select nome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select cognome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', (select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', (select coalesce(id_gestore_acque,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'ACQUE', ''')'));
				raise info 'stampa query insert gisa_ext acque%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert acque: %', query;
			end if;
			-- gestione ruolo apicoltore autoconsumo/commercio/delegato
			if ep_gisa_ext = 10000002 or ep_gisa_ext = 10000001 then 
				raise info 'inserisco una richiesta per apicoltura';
				query = (select concat('SELECT * from dbi_insert_log_user_reg(''', (select nome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select cognome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', (select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'API', ''')'));
				raise info 'stampa query insert gisa_ext api%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert apicoltura: %', query;							
			end if;
			
			-- gestione ruolo dist e trasportatore
			if ep_gisa_ext = 10000004 then 
				query := (select concat('SELECT * from dbi_insert_log_user_reg(''', (select nome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select cognome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', 
				(select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', tipo_dist_trasp, ''')'));
				raise info 'stampa query insert gisa_ext trasp/dist%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert distributori: %', query;	
			end if;
		end if;

		if(get_json_valore(output_vam, 'Esito')='OK') then
				query = (select concat('insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) values(', (select id_clinica_vam from spid.get_lista_richieste(_numero_richiesta)), ',''', (select clinica_vam from spid.get_lista_richieste(_numero_richiesta)), ''', ', (select split_part(ep_guc,';;','2')), ') returning ''OK'';'));
				raise info 'stampa query insert vam cliniche%', query;
				query := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
				raise info 'output query insert vam cliniche: %', query;
			end if;
		
		-- update e insert richieste
UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when (select split_part(ep_guc,';;','1')) = 'OK' then 1 end)
						,esito_processa);
						
	end if;

     RETURN esito_processa;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION spid.processa_richiesta_insert(text, integer)
  OWNER TO postgres;







   --------- db gisa
  alter table log_user_reg add column tipologia_richiesta text;

  -- Function: public.dbi_insert_log_user_reg(text, text, text, text, text, text, text, text, text, text, text, integer, text, timestamp with time zone, text, text, text)

-- DROP FUNCTION public.dbi_insert_log_user_reg(text, text, text, text, text, text, text, text, text, text, text, integer, text, timestamp with time zone, text, text, text);

CREATE OR REPLACE FUNCTION public.dbi_insert_log_user_reg(
    _nome text,
    _cognome text,
    _codice_fiscale text,
    _sesso text,
    _pec text,
    _comune_residenza text,
    _provincia_residenza text,
    _cap_residenza text,
    _indirizzo_residenza text,
    _telefono text,
    _ip text,
    _id_tipo_iscrizione integer,
    _comune_nascita text,
    _data_nascita timestamp with time zone,
    _numero_registrazione text,
    _user_agent text,
    _tipologia_richiesta text)
  RETURNS text AS
$BODY$

 DECLARE 
BEGIN

   INSERT INTO log_user_reg(nome, cognome, codice_fiscale, sesso, pec, comune_residenza, provincia_residenza, cap_residenza, indirizzo_residenza, telefono, ip, user_agent, id_tipo_iscrizione, comune_nascita, data_nascita, numero_registrazione, data_richiesta,tipologia_richiesta)VALUES 
                            ( _nome, _cognome, _codice_fiscale, _sesso::character, _pec, (select nome from comuni1 where istat::integer=_comune_residenza::integer), _provincia_residenza, _cap_residenza, _indirizzo_residenza, _telefono, _ip, _user_agent, _id_tipo_iscrizione, _comune_nascita, 
                            _data_nascita, _numero_registrazione, current_timestamp, _tipologia_richiesta);
	  
  RETURN 'OK';

  
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_log_user_reg(text, text, text, text, text, text, text, text, text, text, text, integer, text, timestamp with time zone, text, text, text)
  OWNER TO postgres;
