-- Function: spid.get_lista_richieste(text)

-- DROP FUNCTION spid.get_lista_richieste(text);

CREATE OR REPLACE FUNCTION spid.get_lista_richieste(IN _numero_richiesta text DEFAULT NULL::text)
  RETURNS TABLE(id integer, id_tipologia_utente integer, tipologia_utente text, id_tipo_richiesta integer, tipo_richiesta text, cognome text, nome text, codice_fiscale text, email text, telefono text, id_ruolo_gisa integer, ruolo_gisa text, id_ruolo_bdu integer, ruolo_bdu text, id_ruolo_vam integer, ruolo_vam text, id_ruolo_gisa_ext integer, ruolo_gisa_ext text, id_ruolo_digemon integer, ruolo_digemon text, id_clinica_vam integer[], clinica_vam text[], identificativo_ente text, piva_numregistrazione text, comune text, istat_comune text, nominativo_referente text, ruolo_referente text, email_referente text, telefono_referente text, data_richiesta timestamp without time zone, codice_gisa text, indirizzo text, id_gestore_acque integer, gestore_acque text, cap text, pec text, numero_richiesta text, esito_guc boolean, esito_gisa boolean, esito_gisa_ext boolean, esito_bdu boolean, esito_vam boolean, esito_digemon boolean, data_esito timestamp without time zone, stato integer, id_asl integer, asl text, provincia_ordine_vet text, numero_ordine_vet text, numero_decreto_prefettizio text, scadenza_decreto_prefettizio text, numero_autorizzazione_regionale_vet text, id_tipologia_trasp_dist integer, esito text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

SELECT r.id, r.id_tipologia_utente, tip.descr as tipologia_utente, r.id_tipo_richiesta, ric.descr as tipologia_richiesta, 
r.cognome::text, r.nome::text, r.codice_fiscale::text, r.email::text, r.telefono::text, r.id_ruolo_gisa::int, g.ruolo as ruolo_gisa, r.id_ruolo_bdu::int,
b.ruolo as ruolo_bdu, r.id_ruolo_vam::int, v.ruolo as ruolo_vam, r.id_ruolo_gisa_ext::int, ge.ruolo as ruolo_ext, r.id_ruolo_digemon::int, dg.ruolo as ruolo_digemon,
r.id_clinica_vam, 
--cli.nome_clinica, 
(select array_agg(nome_clinica)
   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			id_clinica, nome_clinica from dbi_get_cliniche_utente()')
t1(id_clinica integer, nome_clinica text) where id_clinica = ANY (r.id_clinica_vam)),
r.identificativo_ente::text, r.piva_numregistrazione::text, 
(select comuni.comune::text from comuni where codiceistatcomune::integer = (select case when length(r.comune)> 0 then r.comune::integer else -1 end)), coalesce(r.comune,'-1')::text as istat_comune, 
r.nominativo_referente::text,
r.ruolo_referente::text, r.email_referente::text, r.telefono_referente::text, r.data_richiesta, r.codice_gisa::text,
r.indirizzo::text, r.id_gestore_acque::integer, gac.nome, r.cap::text, r.pec::text, r.numero_richiesta::text,
es.esito_guc, es.esito_gisa, es.esito_gisa_ext, es.esito_bdu, es.esito_vam, es.esito_digemon, es.data_esito, es.stato,
r.id_asl, l.nome::text, r.provincia_ordine_vet::text, r.numero_ordine_vet::text, r.numero_decreto_prefettizio::text, r.scadenza_decreto_prefettizio::text,
r.numero_autorizzazione_regionale_vet::text, r.id_tipologia_trasp_dist, es.json_esito
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
--left join (
--select * 
--   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
--			id_clinica, nome_clinica from dbi_get_cliniche_utente()') 
--t1(id_clinica integer, nome_clinica text)
--) cli on cli.id_clinica::text = r.id_clinica_vam
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


-- Function: spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer)

-- DROP FUNCTION spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer);

CREATE OR REPLACE FUNCTION spid.get_dbi_per_endpoint(
    _numero_richiesta text,
    _endpoint text,
    _tipologia text,
    _id_utente integer,
    _username text,
    _password text,
    _data_scadenza timestamp without time zone,
    _id_ruolo_old integer,
    _indice integer)
  RETURNS text AS
$BODY$
declare
 _query text;
 
BEGIN

_query := '';

RAISE INFO '[get_dbi_per_endpoint] _numero_richiesta: %', _numero_richiesta;
RAISE INFO '[get_dbi_per_endpoint] _endpoint: %', _endpoint;
RAISE INFO '[get_dbi_per_endpoint] _tipologia: %', _tipologia;
RAISE INFO '[get_dbi_per_endpoint] _id_utente: %', _id_utente;
RAISE INFO '[get_dbi_per_endpoint] _username: %', _username;
RAISE INFO '[get_dbi_per_endpoint] _password: %', _password;
RAISE INFO '[get_dbi_per_endpoint] _data_scadenza: %', _data_scadenza;
RAISE INFO '[get_dbi_per_endpoint] _id_ruolo_old: %', _id_ruolo_old;
RAISE INFO '[get_dbi_per_endpoint] _indice: %', _indice;

IF _tipologia ilike 'INSERT' THEN

IF _endpoint ilike 'GUC' then

select concat(
concat('select * from dbi_insert_utente_guc(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', '1', '''', ', ',
_id_utente, ', ',
'NULL::timestamp without time zone', ', ', 
_id_utente, ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', null::character varying, '''', ', ', 
'''', null::character varying, '''', ', ', 
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', null::character varying, '''', ', ', 
-1, ', ',
'''', '', '''', ', '), 
concat('''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select coalesce(numero_autorizzazione_regionale_vet,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', 
-1, ', ',
-1, ', ',
'''', '', '''', ', ',
'''', '', '''', ', ',
(select case  when provincia_ordine_vet ilike '%avellino%' then 64 when provincia_ordine_vet ilike '%benevento%' then 62 when provincia_ordine_vet ilike '%caserta%' then 61 when provincia_ordine_vet ilike '%napoli%' then 63 when provincia_ordine_vet ilike '%salerno%' then 65 else -1 end from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select numero_ordine_vet from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', 
(select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_gisa_ext,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_vam,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
-1, ', ',
'''', '', '''', ', ',
(select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
END IF;

IF _endpoint ilike 'GISA' then

select concat(
'select * from dbi_insert_utente(',
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
-1, ', ',
'''', 'true', '''', ', ',
'''', (select COALESCE((select coalesce(in_dpat::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;
END IF;

IF _endpoint ilike 'GISA_EXT' then

select concat(
'select * from dbi_insert_utente_ext(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
'''', 'true', '''', ', ',
'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', '', '''', ', ',
-1, ', ',
'''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', '', '''', ', ',
-1, ', ', 
'''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
-1, ', ', 
'''', '', '''', ', ',
'''', '', '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'BDU' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
-1, ', ',
-1, ', ',
(select case  when provincia_ordine_vet ilike '%avellino%' then 64 when provincia_ordine_vet ilike '%benevento%' then 62 when provincia_ordine_vet ilike '%caserta%' then 61 when provincia_ordine_vet ilike '%napoli%' then 63 when provincia_ordine_vet ilike '%salerno%' then 65 else -1 end from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select numero_ordine_vet from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'VAM' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_vam,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', '1', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
(select coalesce(id_clinica_vam[_indice],-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', '-1', '''', ', ',
-1, ', ',
'''', null::character varying, '''', ', ', 
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'DIGEMON' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', '1', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select coalesce(comune,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;
			
END IF;

END IF;

IF _tipologia ilike 'ELIMINA' THEN

IF _endpoint ilike 'GISA' then

select concat(
'select * from dbi_elimina_utente(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
(select coalesce(_id_ruolo_old,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'GISA_EXT' then

select concat(
'select * from dbi_elimina_utente_ext(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
(select coalesce(_id_ruolo_old,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'BDU' then

select concat(
'select * from dbi_elimina_utente(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
(select coalesce(_id_ruolo_old,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'VAM' then

select concat(
'select * from dbi_elimina_utente(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
(select coalesce(_id_ruolo_old,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'DIGEMON' then

select concat(
'select * from dbi_elimina_utente(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
(select coalesce(_id_ruolo_old,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

END IF;

RAISE INFO '[get_dbi_per_endpoint] %', _query;

return _query;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer)
  OWNER TO postgres;



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

	indice integer;
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
	
	if (length(check_gisa)>0 or length(check_gisa_ext)>0 or length(check_bdu)>0 or length(check_vam)>0 or length(check_digemon)>0) then --errore
		return '{Esito:"KO", DescrizioneErrore:"'||concat(check_gisa, check_gisa_ext, check_bdu, check_vam, check_digemon)||'"}';

	else --continuo con il resto
	
		if (_id_tipo_richiesta = 1) then -- inserimento utente
			query := (select * from spid.get_dbi_per_endpoint(_numero_richiesta,'guc','insert',_id_utente,null,null,null::timestamp without time zone,-1, -1)); 
			ep_guc:= (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		end if;

		username_out := (select split_part(ep_guc,';;','3'));
		raise info ' username %', username_out;
		password_out := (select split_part(ep_guc,';;','4'));
		raise info ' password %', password_out;
		esito_guc := (select split_part(ep_guc,';;','1'));
		raise info ' esito insert guc %', esito_guc;

		if (ep_gisa > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, -1));
			output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			output_gisa := '{"Esito" : "'||output_gisa ||'"}';
		end if;

		if (ep_gisa_ext > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, -1));
			output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			output_gisa_ext := '{"Esito" : "'||output_gisa_ext ||'"}';
		end if;
	    
		if (ep_bdu > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, -1));
			output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
			output_bdu := '{"Esito" : "'||output_bdu ||'"}';
		end if;

		if (ep_vam > 0 and esito_guc = 'OK') then

		indice := 0;
		WHILE indice < (select array_length (id_clinica_vam, 1) from spid.get_lista_richieste(_numero_richiesta)) LOOP

			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, (indice+1)));
			output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));

				output_vam := '{"Esito" : "'||output_vam ||'"}';
			indice = indice+1;
		END LOOP;	
		end if;

		if (ep_digemon > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, -1));
			output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbDIGEMONL dbname=digemon_u'));
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
				(select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', (select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', (select coalesce(id_gestore_acque,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'ACQUE', ''')'));
				raise info 'stampa query insert gisa_ext acque%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert acque: %', query;
			end if;
			-- gestione ruolo apicoltore autoconsumo/commercio/delegato
			if ep_gisa_ext = 10000002 or ep_gisa_ext = 10000001 then 
				raise info 'inserisco una richiesta per apicoltura';
				query = (select concat('SELECT * from dbi_insert_log_user_reg(''', (select nome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', (select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'API', ''')'));
				raise info 'stampa query insert gisa_ext api%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert apicoltura: %', query;							
			end if;
			
			-- gestione ruolo dist e trasportatore
			if ep_gisa_ext = 10000004 then 
				query := (select concat('SELECT * from dbi_insert_log_user_reg(''', (select nome from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', 
				(select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', tipo_dist_trasp, ''')'));
				raise info 'stampa query insert gisa_ext trasp/dist%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert distributori: %', query;	
			end if;
		end if;

		if(get_json_valore(output_vam, 'Esito')='OK') then
		indice := 0;
		WHILE indice < (select array_length (id_clinica_vam, 1) from spid.get_lista_richieste(_numero_richiesta)) LOOP
		
				query = (select concat('insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) values(', (select id_clinica_vam[indice+1] from spid.get_lista_richieste(_numero_richiesta)), ',''', (select replace(clinica_vam[indice+1], '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ', (select split_part(ep_guc,';;','2')), ') returning ''OK'';'));
				raise info 'stampa query insert vam cliniche%', query;
				query := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
				raise info 'output query insert vam cliniche: %', query;

				indice = indice+1;
		END LOOP;
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


  -- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_elimina(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	_ruolo_gisa text;
	_ruolo_gisa_ext text;
	_ruolo_bdu text;
	_ruolo_vam text;
	_ruolo_digemon text;
	esito_processa text;

	esito_check text;
	query_endpoint text;
	query text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	output_guc text;
	_codice_fiscale text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	output_guc_gisa text;
	
BEGIN
	output_gisa = '';
	output_gisa_ext = '';
	output_bdu = '' ;
	output_vam = '';
	output_digemon = '';
	output_guc = '[]';
	-- elimino utente prima in guc e poi nell'EP.
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_ruolo_gisa := (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_gisa_ext := (select coalesce(ruolo_gisa_ext,'')from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_bdu := (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_vam := (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_digemon :=(select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta));


	if (ep_gisa > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa||', ''gisa'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che e'' ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''Gisa'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, '', '', now()::timestamp without time zone - interval '1 DAY',get_json_valore(esito_check, 'IdRuolo')::integer));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa = esito_check;		        
		end if;
        end if; -- fine ep_gisa

	if (ep_gisa_ext > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa_ext||', ''gisa_ext'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che e'' ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),get_json_valore(esito_check, 'IdRuolo')::integer));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa_ext = esito_check;		        
		end if;
        end if; -- fine ep_gisa_ext
        
	if (ep_bdu > 0) then
		-- richiamo su BDU
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_bdu||', ''bdu'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che e'' ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),get_json_valore(esito_check, 'IdRuolo')::integer));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		else
			output_bdu = esito_check;		        
		end if;
        end if; -- fine ep_bdu
        
	if (ep_vam > 0) then
		-- richiamo su vam
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_vam||', ''vam'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che e'' ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),get_json_valore(esito_check, 'IdRuolo')::integer));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		else
			output_vam = esito_check;		        
		end if;
        end if; -- fine ep_vam

	if (ep_digemon > 0) then
		-- richiamo su digemon
		query := 'select * from spid.check_validita_ruolo_cf('''||_codice_fiscale||''', '||ep_digemon||', ''digemon'')';
		esito_check := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		if get_json_valore(esito_check, 'Esito')='OK' then -- significa che e'' ok
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(esito_check, 'IdUtente')||','||get_json_valore(esito_check, 'IdRuolo')||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _id_utente, '', '', (now()::timestamp without time zone - interval '1 DAY'),get_json_valore(esito_check, 'IdRuolo')::integer));
				output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbdigemonL dbname=digemon_u'));
			end if;	
		else
			output_digemon = esito_check;		        
		end if;
        end if; -- fine ep_digemon
       
	esito_processa = '{"EndPoint" : "GUC", "Risultato": '||output_guc||', "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';	
				   
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;
	

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when output_guc = 'OK' then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_elimina(text, integer)
  OWNER TO postgres;

-- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_modifica(
    _numero_richiesta text,
    _idutente integer)
  RETURNS text AS
$BODY$
declare

	esito integer;
	output_guc_gisa text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	username_out text;
	password_out text;
	
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	check_endpoint text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	query text;
	
	_codice_fiscale text;
	esito_processa text;
	query_endpoint text;
	output_guc text;

	indice integer;

BEGIN
	-- recupero CF e id_ruoli per ogni EP
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	output_gisa='{}';
	output_gisa_ext='{}';
	output_bdu='{}';
	output_vam='{}';
	output_digemon='{}';
	output_guc ='{}';
	
	if (ep_gisa > 0) then
		-- richiamo su Gisa
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa||', ''gisa'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_gisa := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che e'' ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''Gisa'')';
			output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_gisa||',''Gisa'')';			
				output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer, -1));
					output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa := '{"Esito" : "'||output_gisa ||'"}';
					end if;
				end if;
			end if;	
				esito =1;	
		end if;
		
	end if; -- fine ep_gisa

	if (ep_gisa_ext > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_gisa_ext||', ''gisa_ext'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_gisa_ext := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che e'' ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''Gisa_ext'')';
			output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_gisa_ext||',''Gisa_ext'')';			
				output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer, -1));
					output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa_ext, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa_ext := '{"Esito" : "'||output_gisa_ext||'"}';
					end if;
				end if;
			end if;			        
				esito =1;
		end if;
			
	end if; -- fine ep_gisa_ext

	if (ep_bdu > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_bdu||', ''bdu'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_bdu := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che e'' ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''bdu'')';
			output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_bdu||',''bdu'')';			
				output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer, -1));
					output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
					if (get_json_valore(output_bdu, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
						output_bdu := '{"Esito" : "'||output_bdu||'"}';
					end if;
				end if;
			end if;		
				esito =1;	        
		end if;
	end if; -- fine ep_bdu
				  
	if (ep_vam > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_vam||', ''vam'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_vam := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che e'' ok
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''vam'')';
			output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_vam||',''vam'')';			
				output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer, -1));
					output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
					if (get_json_valore(output_vam, 'Esito') = 'OK') then -- insert utente

					indice := 0;
		WHILE indice < (select array_length (id_clinica_vam, 1) from spid.get_lista_richieste(_numero_richiesta)) LOOP
		
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
						output_vam := '{"Esito" : "'||output_vam||'"}';
						indice = indice+1;
		END LOOP;
					end if;
				end if;
			end if;		
				esito =1;
		end if;
	end if; -- fine ep_vam	  

	if (ep_digemon > 0) then
		query := 'select * from spid.check_validita_gruppo_ruolo_cf('''||_codice_fiscale||''', '||ep_digemon||', ''digemon'');';
		check_endpoint := (select * from spid.esegui_query(query, 'guc', _idutente,''));
		output_digemon := check_endpoint;
		if get_json_valore(check_endpoint, 'Esito') = 'OK' then -- significa che e'' ok
			-- chiamo guc e gestisco l'output
			username_out := get_json_valore(check_endpoint, 'Username');
			password_out := get_json_valore(check_endpoint, 'Password');
			query  := 'select * from spid.elimina_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||get_json_valore(check_endpoint, 'IdRuolo')||',''digemon'')';
			output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||get_json_valore(check_endpoint, 'IdUtente')||','||ep_digemon||',''digemon'')';			
				output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _idutente, '', '', (now()::timestamp without time zone - interval '1 DAY'), get_json_valore(check_endpoint, 'IdRuolo')::integer, -1));
					output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
					if (get_json_valore(output_digemon, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
						output_digemon := '{"Esito" : "'||output_digemon||'"}';
					end if;
				end if;
			end if;	
				esito =1;
		end if;
	end if; -- fine ep_digemon
	


	esito_processa = '{"EndPoint" : "GUC", "Username" : "' || username_out || '", "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;

		
	-- update e insert richieste
	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),_idutente, (case when esito = 1 then 1 end),esito_processa);
		

	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_modifica(text, integer)
  OWNER TO postgres;

  alter table spid.spid_registrazioni alter column id_clinica_vam type text using id_clinica_vam::text;
update spid.spid_registrazioni set id_clinica_vam = '{'||id_clinica_vam||'}';
alter table spid.spid_registrazioni alter column id_clinica_vam type integer[] using id_clinica_vam::integer[];

----- modifiche del 20/12

CREATE OR REPLACE FUNCTION spid.get_lista_ruoli_utente_guc(IN _cf text, IN _endpoint text)
  RETURNS TABLE(id_utente integer, username text, id_ruolo integer, id_guc_ruoli integer, ruolo text, id_asl integer, asl text, endpoint text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

select
u.id, u.username::text, r.ruolo_integer, r.id, r.ruolo_string::text, u.asl_id, a.nome::text, r.endpoint::text
from guc_utenti u
left join guc_ruoli r on u.id = r.id_utente and r.trashed is not true and r.data_scadenza is null and r.ruolo_integer > 0
left join asl a on a.id = u.asl_id
where u.codice_fiscale ilike _cf and r.endpoint ilike _endpoint;
        
 END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- Function: spid.get_lista_richieste(text)

DROP FUNCTION spid.get_lista_richieste(text);

CREATE OR REPLACE FUNCTION spid.get_lista_richieste(IN _numero_richiesta text DEFAULT NULL::text)
  RETURNS TABLE(id integer, id_tipologia_utente integer, tipologia_utente text, id_tipo_richiesta integer, tipo_richiesta text, cognome text, nome text, codice_fiscale text, email text, telefono text, id_ruolo_gisa integer, ruolo_gisa text, id_ruolo_bdu integer, ruolo_bdu text, id_ruolo_vam integer, ruolo_vam text, id_ruolo_gisa_ext integer, ruolo_gisa_ext text, id_ruolo_digemon integer, ruolo_digemon text, id_clinica_vam integer[], clinica_vam text[], identificativo_ente text, piva_numregistrazione text, comune text, istat_comune text, nominativo_referente text, ruolo_referente text, email_referente text, telefono_referente text, data_richiesta timestamp without time zone, codice_gisa text, indirizzo text, id_gestore_acque integer, gestore_acque text, cap text, pec text, numero_richiesta text, esito_guc boolean, esito_gisa boolean, esito_gisa_ext boolean, esito_bdu boolean, esito_vam boolean, esito_digemon boolean, data_esito timestamp without time zone, stato integer, id_asl integer, asl text, provincia_ordine_vet text, numero_ordine_vet text, numero_decreto_prefettizio text, scadenza_decreto_prefettizio text, numero_autorizzazione_regionale_vet text, id_tipologia_trasp_dist integer, esito text, id_guc_ruoli integer, endpoint_guc_ruoli text, ruolo_guc_ruoli text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

SELECT r.id, r.id_tipologia_utente, tip.descr as tipologia_utente, r.id_tipo_richiesta, ric.descr as tipologia_richiesta, 
r.cognome::text, r.nome::text, r.codice_fiscale::text, r.email::text, r.telefono::text, r.id_ruolo_gisa::int, g.ruolo as ruolo_gisa, r.id_ruolo_bdu::int,
b.ruolo as ruolo_bdu, r.id_ruolo_vam::int, v.ruolo as ruolo_vam, r.id_ruolo_gisa_ext::int, ge.ruolo as ruolo_ext, r.id_ruolo_digemon::int, dg.ruolo as ruolo_digemon,
r.id_clinica_vam, 
--cli.nome_clinica, 
(select array_agg(nome_clinica)
   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			id_clinica, nome_clinica from dbi_get_cliniche_utente()')
t1(id_clinica integer, nome_clinica text) where id_clinica = ANY (r.id_clinica_vam)),
r.identificativo_ente::text, r.piva_numregistrazione::text, 
(select comuni.comune::text from comuni where codiceistatcomune::integer = (select case when length(r.comune)> 0 then r.comune::integer else -1 end)), coalesce(r.comune,'-1')::text as istat_comune, 
r.nominativo_referente::text,
r.ruolo_referente::text, r.email_referente::text, r.telefono_referente::text, r.data_richiesta, r.codice_gisa::text,
r.indirizzo::text, r.id_gestore_acque::integer, gac.nome, r.cap::text, r.pec::text, r.numero_richiesta::text,
es.esito_guc, es.esito_gisa, es.esito_gisa_ext, es.esito_bdu, es.esito_vam, es.esito_digemon, es.data_esito, es.stato,
r.id_asl, l.nome::text, r.provincia_ordine_vet::text, r.numero_ordine_vet::text, r.numero_decreto_prefettizio::text, r.scadenza_decreto_prefettizio::text,
r.numero_autorizzazione_regionale_vet::text, r.id_tipologia_trasp_dist, es.json_esito, r.id_guc_ruoli, gr.endpoint::text, gr.ruolo_string::text
from spid.spid_registrazioni r 
left join asl l on l.id = r.id_asl
join spid.spid_tipo_richiesta ric on ric.id = r.id_tipo_richiesta
join spid.spid_tipologia_utente tip on tip.id = r.id_tipologia_utente
left join spid.spid_registrazioni_esiti es on es.numero_richiesta = r.numero_richiesta and es.trashed_date is null
left join guc_ruoli gr on gr.id = r.id_guc_ruoli
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
--left join (
--select * 
--   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
--			id_clinica, nome_clinica from dbi_get_cliniche_utente()') 
--t1(id_clinica integer, nome_clinica text)
--) cli on cli.id_clinica::text = r.id_clinica_vam
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
  
  -- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);

-- Function: spid.processa_richiesta_modifica(text, integer)

-- DROP FUNCTION spid.processa_richiesta_modifica(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_modifica(
    _numero_richiesta text,
    _idutente integer)
  RETURNS text AS
$BODY$
declare

	esito integer;
	output_guc_gisa text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	username_out text;
	password_out text;
	
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	check_endpoint text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	query text;
	
	_codice_fiscale text;
	esito_processa text;
	query_endpoint text;
	output_guc text;

	indice integer;

	_id_guc_ruoli integer;
	_endpoint_guc_ruoli text;
	_id_utente_guc_ruoli integer;
	_id_ruolo_guc_ruoli integer;

BEGIN
	-- recupero CF e id_ruoli per ogni EP
	_id_guc_ruoli := (select id_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_endpoint_guc_ruoli := (select endpoint_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_id_utente_guc_ruoli := (select id_utente from guc_ruoli where id = _id_guc_ruoli);
	_id_ruolo_guc_ruoli := (select ruolo_integer from guc_ruoli where id = _id_guc_ruoli);
	username_out := (select username from guc_utenti where id = _id_utente_guc_ruoli);
	password_out := (select password from guc_utenti where id = _id_utente_guc_ruoli);

	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	output_gisa='{}';
	output_gisa_ext='{}';
	output_bdu='{}';
	output_vam='{}';
	output_digemon='{}';
	output_guc ='{}';
	
	if (ep_gisa > 0 and _endpoint_guc_ruoli ilike 'gisa') then
		
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa'')';
			output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_gisa||',''Gisa'')';			
				output_guc_gisa := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa := '{"Esito" : "'||output_gisa ||'"}';
					end if;
					else
			output_gisa := output_guc_gisa;
				end if;
			end if;	
				esito =1;	
		
	end if; -- fine ep_gisa

	if (ep_gisa_ext > 0 and _endpoint_guc_ruoli ilike 'gisa_ext') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_gisa_ext||',''Gisa_ext'')';			
				output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_gisa_ext, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
					if (get_json_valore(output_gisa_ext, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _idutente,'host=dbGISAL dbname=gisa'));
						output_gisa_ext := '{"Esito" : "'||output_gisa_ext||'"}';
					end if;
					else
			output_gisa_ext := output_guc_gisa_ext;
				end if;
			end if;			        
				esito =1;
			
	end if; -- fine ep_gisa_ext

	if (ep_bdu > 0 and _endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_bdu||',''bdu'')';			
				output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_bdu, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
					if (get_json_valore(output_bdu, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _idutente,'host=dbBDUL dbname=bdu'));
						output_bdu := '{"Esito" : "'||output_bdu||'"}';
					end if;
					else
			output_bdu := output_guc_bdu;
				end if;
			end if;		
				esito =1;	        
		
	end if; -- fine ep_bdu
				  
	if (ep_vam > 0 and _endpoint_guc_ruoli ilike 'vam') then
		
			-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_vam||',''vam'')';			
				output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_vam, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
					if (get_json_valore(output_vam, 'Esito') = 'OK') then -- insert utente

					indice := 0;
		WHILE indice < (select array_length (id_clinica_vam, 1) from spid.get_lista_richieste(_numero_richiesta)) LOOP
		
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, (indice+1)));
						output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _idutente,'host=dbVAML dbname=vam'));
						output_vam := '{"Esito" : "'||output_vam||'"}';
						indice = indice+1;
		END LOOP;
					end if;
					else
			output_vam := output_guc_vam;
				end if;
			end if;		
				esito =1;
		
	end if; -- fine ep_vam	  

	if (ep_digemon > 0 and _endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
				query := 'select * from spid.insert_ruolo_utente_guc('||_id_utente_guc_ruoli||','||ep_digemon||',''digemon'')';			
				output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
				if (get_json_valore(output_guc_digemon, 'Esito') = 'OK') then
					-- chiamo la dbi elimina utente
					query_endpoint :=( select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _idutente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'), _id_ruolo_guc_ruoli, -1));
					output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
					if (get_json_valore(output_digemon, 'Esito') = 'OK') then -- insert utente
						query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'insert', _idutente, username_out, password_out, null::timestamp without time zone, -1, -1));
						output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _idutente,'host=dbDIGEMONL dbname=digemon_u'));
						output_digemon := '{"Esito" : "'||output_digemon||'"}';
					end if;
					else
			output_digemon := output_guc_digemon;
				end if;
			end if;	
				esito =1;
		
	end if; -- fine ep_digemon
	


	esito_processa = '{"EndPoint" : "GUC", "Username" : "' || username_out || '", "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;

		
	-- update e insert richieste
	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
					       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),_idutente, (case when esito = 1 then 1 end),esito_processa);
		

	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_modifica(text, integer)
  OWNER TO postgres;




  -- GISA
CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	where a.username ilike _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente_ext(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente_ext] _username %', _username;
RAISE INFO '[dbi_elimina_utente_ext] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access_ext a 
	where a.username ilike _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente_ext] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ext_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente_ext') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente_ext(text, timestamp without time zone)
  OWNER TO postgres;

-- GISA EXT
-- Function: public.dbi_elimina_utente_ext(text, timestamp without time zone)

-- DROP FUNCTION public.dbi_elimina_utente_ext(text, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente_ext(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente_ext] _username %', _username;
RAISE INFO '[dbi_elimina_utente_ext] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access_ext a 
	where a.username ilike _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente_ext] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ext_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente_ext') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente_ext(text, timestamp without time zone)
  OWNER TO postgres;
  
  
  
-- BDU
CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	where a.username ilike _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;


-- DIGEMON

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select a.user_id into idUtente from access a 
	where a.username ilike _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;

  -- VAM
  -- Function: public.dbi_elimina_utente(text, integer, timestamp without time zone)

-- DROP FUNCTION public.dbi_elimina_utente(text, integer, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idUtente int;   
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

	select count(a.id) into idUtente from utenti a 
	where a.username = _username and a.trashed_date is null and a.enabled and a.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idUtente %', idUtente;
	
	IF (idUtente is null or idUtente < 0) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE utenti_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note_internal_use_only_hd = concat_ws(';', note_internal_use_only_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE id in (select a.id from utenti a 
	where a.username = _username and a.trashed_date is null and a.enabled and a.data_scadenza is null);
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;

-- guc

  -- Function: spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer)

-- DROP FUNCTION spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer);

CREATE OR REPLACE FUNCTION spid.get_dbi_per_endpoint(
    _numero_richiesta text,
    _endpoint text,
    _tipologia text,
    _id_utente integer,
    _username text,
    _password text,
    _data_scadenza timestamp without time zone,
    _id_ruolo_old integer,
    _indice integer)
  RETURNS text AS
$BODY$
declare
 _query text;
 
BEGIN

_query := '';

RAISE INFO '[get_dbi_per_endpoint] _numero_richiesta: %', _numero_richiesta;
RAISE INFO '[get_dbi_per_endpoint] _endpoint: %', _endpoint;
RAISE INFO '[get_dbi_per_endpoint] _tipologia: %', _tipologia;
RAISE INFO '[get_dbi_per_endpoint] _id_utente: %', _id_utente;
RAISE INFO '[get_dbi_per_endpoint] _username: %', _username;
RAISE INFO '[get_dbi_per_endpoint] _password: %', _password;
RAISE INFO '[get_dbi_per_endpoint] _data_scadenza: %', _data_scadenza;
RAISE INFO '[get_dbi_per_endpoint] _id_ruolo_old: %', _id_ruolo_old;
RAISE INFO '[get_dbi_per_endpoint] _indice: %', _indice;

IF _tipologia ilike 'INSERT' THEN

IF _endpoint ilike 'GUC' then

select concat(
concat('select * from dbi_insert_utente_guc(',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', '1', '''', ', ',
_id_utente, ', ',
'NULL::timestamp without time zone', ', ', 
_id_utente, ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', null::character varying, '''', ', ', 
'''', null::character varying, '''', ', ', 
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', null::character varying, '''', ', ', 
-1, ', ',
'''', '', '''', ', '), 
concat('''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select coalesce(numero_autorizzazione_regionale_vet,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', 
-1, ', ',
-1, ', ',
'''', '', '''', ', ',
'''', '', '''', ', ',
(select case  when provincia_ordine_vet ilike '%avellino%' then 64 when provincia_ordine_vet ilike '%benevento%' then 62 when provincia_ordine_vet ilike '%caserta%' then 61 when provincia_ordine_vet ilike '%napoli%' then 63 when provincia_ordine_vet ilike '%salerno%' then 65 else -1 end from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select numero_ordine_vet from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', 
(select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_gisa_ext,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
(select coalesce(id_ruolo_vam,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
-1, ', ',
'''', '', '''', ', ',
(select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
END IF;

IF _endpoint ilike 'GISA' then

select concat(
'select * from dbi_insert_utente(',
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
-1, ', ',
'''', 'true', '''', ', ',
'''', (select COALESCE((select coalesce(in_dpat::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;
END IF;

IF _endpoint ilike 'GISA_EXT' then

select concat(
'select * from dbi_insert_utente_ext(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
'''', 'true', '''', ', ',
'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
'''', '', '''', ', ',
-1, ', ',
'''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', '', '''', ', ',
-1, ', ', 
'''', (select coalesce(indirizzo,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
-1, ', ', 
'''', '', '''', ', ',
'''', '', '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'BDU' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', 'true', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
-1, ', ',
-1, ', ',
(select case  when provincia_ordine_vet ilike '%avellino%' then 64 when provincia_ordine_vet ilike '%benevento%' then 62 when provincia_ordine_vet ilike '%caserta%' then 61 when provincia_ordine_vet ilike '%napoli%' then 63 when provincia_ordine_vet ilike '%salerno%' then 65 else -1 end from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select numero_ordine_vet from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'VAM' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_vam,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', '1', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
(select coalesce(id_clinica_vam[_indice],-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', '-1', '''', ', ',
-1, ', ',
'''', null::character varying, '''', ', ', 
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;

END IF;

IF _endpoint ilike 'DIGEMON' then

select concat(
'select * from dbi_insert_utente(', 
'''', _username, '''', ', ',
'''', _password, '''', ', ',
(select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
_id_utente, ', ',
_id_utente, ', ',
'''', '1', '''', ', ',
(select coalesce(id_asl,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
'''', (select nome from spid.get_lista_richieste(_numero_richiesta)), '''',  ', ',
'''', (select replace(cognome,'''', '''''') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', 'INSERIMENTO UTENTE PER RICHIESTA PROCESSATA NUMERO '||_numero_richiesta, '''', ', ',
'''', (select replace(coalesce(comune,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', null::character varying, '''', ', ', 
'''', (select email from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'NULL::timestamp with time zone', ', ', 
'''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), '''', '); ') into _query;
			
END IF;

END IF;

IF _tipologia ilike 'ELIMINA' THEN

IF _endpoint ilike 'GISA' then

select concat(
'select * from dbi_elimina_utente(',
'''', _username, '''',  ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'GISA_EXT' then

select concat(
'select * from dbi_elimina_utente_ext(',
'''', _username, '''',  ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'BDU' then

select concat(
'select * from dbi_elimina_utente(',
'''', _username, '''',  ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'VAM' then

select concat(
'select * from dbi_elimina_utente(',
'''', _username, '''',  ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

IF _endpoint ilike 'DIGEMON' then

select concat(
'select * from dbi_elimina_utente(',
'''', _username, '''',  ', ',
'''', _data_scadenza, '''', '); ') into _query;

END IF;

END IF;

RAISE INFO '[get_dbi_per_endpoint] %', _query;

return _query;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer)
  OWNER TO postgres;

-- BDU
  
  -- Function: public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text, text)

-- DROP FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text, text);

CREATE OR REPLACE FUNCTION public.dbi_insert_utente(
    usr character varying,
    password character varying,
    role_id integer,
    enteredby integer,
    modifiedby integer,
    enabled boolean,
    site_id integer,
    namefirst character varying,
    namelast character varying,
    cf character varying,
    notes text,
    luogo text,
    nickname character varying,
    email character varying,
    expires timestamp with time zone,
    id_importatore integer,
    id_canile integer,
    id_prov_iscr_albo integer,
    nr_iscrizione_albo text,
    input_telefono text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;
   
BEGIN

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	IF (id_importatore=-1) THEN
		id_importatore:=null;
	END IF;

	IF (id_canile=-1) THEN
		id_canile:=null;
	END IF;

	
		us_id=nextVal('access_user_id_seq');
		con_id=nextVal('contact_contact_id_seq');
		INSERT INTO access_ ( user_id, username, password, contact_id, site_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires,id_importatore,id_linea_produttiva_riferimento ) 
		VALUES (  us_id, usr, password, con_id, site_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone,id_importatore,id_canile); 

		--con_id=currVal('contact_contact_id_seq');
		--us_id=currVal('access_user_id_seq');		
		INSERT INTO contact_ ( contact_id, user_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled,luogo,nickname,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato, telefono) 
		VALUES ( con_id, us_id, namefirst, namelast, 964, 964, site_id, cf, notes, enabled,luogo,nickname,id_prov_iscr_albo,nr_iscrizione_albo, input_telefono);
			
		--con_id=currVal('contact_contact_id_seq');
		INSERT INTO contact_emailaddress(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email, 964, 964, true);
	

	IF (id_canile is not null) THEN
		us_id2=nextVal('access_collegamento_id_seq');
		INSERT INTO access_collegamento (id,id_utente,id_collegato,enabled) 
		VALUES (us_id2,us_id,id_canile,enabled); 
	END IF;

	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text, text)
  OWNER TO postgres;

  -- GUC
  
  -- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_elimina(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
	ep_gisa integer;
	ep_gisa_ext integer;
	ep_bdu integer;
	ep_vam integer;
	ep_digemon integer;

	_ruolo_gisa text;
	_ruolo_gisa_ext text;
	_ruolo_bdu text;
	_ruolo_vam text;
	_ruolo_digemon text;
	esito_processa text;

	esito_check text;
	query_endpoint text;
	query text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	output_guc text;
	_codice_fiscale text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	output_guc_gisa text;

	_id_guc_ruoli integer;
	_endpoint_guc_ruoli text;
	_id_utente_guc_ruoli integer;
	_id_ruolo_guc_ruoli integer;

	username_out text;
BEGIN
	output_gisa = '';
	output_gisa_ext = '';
	output_bdu = '' ;
	output_vam = '';
	output_digemon = '';
	output_guc = '[]';
	-- elimino utente prima in guc e poi nell'EP.
	_id_guc_ruoli := (select id_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_endpoint_guc_ruoli := (select endpoint_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_id_utente_guc_ruoli := (select id_utente from guc_ruoli where id = _id_guc_ruoli);
	_id_ruolo_guc_ruoli := (select ruolo_integer from guc_ruoli where id = _id_guc_ruoli);
	username_out := (select username from guc_utenti where id = _id_utente_guc_ruoli);
	
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_ruolo_gisa := (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_gisa_ext := (select coalesce(ruolo_gisa_ext,'')from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_bdu := (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_vam := (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_digemon :=(select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta));


	if (ep_gisa > 0 and _endpoint_guc_ruoli ilike 'gisa') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, username_out, '', now()::timestamp without time zone - interval '1 DAY',_id_ruolo_guc_ruoli, -1));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa = esito_check;		        
	
        end if; -- fine ep_gisa

	if (ep_gisa_ext > 0 and _endpoint_guc_ruoli ilike 'gisa_ext') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		else
			output_gisa_ext = esito_check;		        
		
        end if; -- fine ep_gisa_ext
        
	if (ep_bdu > 0 and _endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		else
			output_bdu = esito_check;		        
		
        end if; -- fine ep_bdu
        
	if (ep_vam > 0 and _endpoint_guc_ruoli ilike 'vam') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		else
			output_vam = esito_check;		        
		
        end if; -- fine ep_vam

	if (ep_digemon > 0 and _endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbdigemonL dbname=digemon_u'));
			end if;	
		else
			output_digemon = esito_check;		        
		
        end if; -- fine ep_digemon
       
	esito_processa = '{"EndPoint" : "GUC", "Risultato": '||output_guc||', "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';	
				   
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;
	

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when output_guc = 'OK' then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_elimina(text, integer)
  OWNER TO postgres;
  
  -- GUC 
  
  -- Function: spid.insert_ruolo_utente_guc(integer, integer, text)

-- DROP FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text);

CREATE OR REPLACE FUNCTION spid.insert_ruolo_utente_guc(
    _id_utente integer,
    _id_ruolo integer,
    _endpoint text)
  RETURNS text AS
$BODY$

  DECLARE 

	conta integer;	
	esito text;
	descrizione_errore text;
	descrizione_ruolo text;
  BEGIN
	descrizione_errore='';
	esito='';


	if (_endpoint ilike 'gisa') THEN
	descrizione_ruolo = (select ruolo FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') t1(id integer, 				ruolo text) where id = _id_ruolo);
	END IF;
	if (_endpoint ilike 'gisa_ext') THEN
	descrizione_ruolo = (select ruolo FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente_ext()') t1(id integer, 				ruolo text) where id = _id_ruolo);
	END IF;
		if (_endpoint ilike 'bdu') THEN
	descrizione_ruolo = (select ruolo FROM dblink('host=dbBDUL dbname=bdu'::text, 'SELECT id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') t1(id integer, 				ruolo text) where id = _id_ruolo);
	END IF;
	if (_endpoint ilike 'vam') THEN
	descrizione_ruolo = (select ruolo FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') t1(id integer, 				ruolo text) where id = _id_ruolo);
	END IF;
	if (_endpoint ilike 'digemon') THEN
	descrizione_ruolo = (select ruolo FROM dblink('host=dbDIGEMONL dbname=digemon_u'::text, 'SELECT id_ruolo, descrizione_ruolo from dbi_get_ruoli_utente()') t1(id integer, 				ruolo text) where id = _id_ruolo);
	END IF;

	raise info '[insert_ruolo_utente_guc] id_ruolo %', _id_ruolo;
	raise info '[insert_ruolo_utente_guc] id_utente %', _id_utente;
	raise info '[insert_ruolo_utente_guc] descrizione_ruolo %', descrizione_ruolo;
	raise info '[insert_ruolo_utente_guc] _endpoint %', _endpoint;
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 0) then
		esito = 'KO';
		descrizione_errore = 'Esiste già un utente per questo codice fiscale e ruolo';
	else
	insert into guc_ruoli (id_utente, ruolo_integer, ruolo_string, endpoint) values (_id_utente, _id_ruolo, descrizione_ruolo, (select endpoint from guc_ruoli where endpoint ilike _endpoint limit 1));
		esito = 'OK';
	end if;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.insert_ruolo_utente_guc(integer, integer, text)
  OWNER TO postgres;

  
  -- Function: spid.check_validita_ruolo_cf(text, integer, text)

-- DROP FUNCTION spid.check_validita_ruolo_cf(text, integer, text);

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
		  u.codice_fiscale ilike _codice_fiscale and r.data_scadenza is null
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
		  u.codice_fiscale ilike _codice_fiscale and r.data_scadenza is null
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

  -- Function: spid.get_lista_ruoli_utente_guc(text, text)

-- DROP FUNCTION spid.get_lista_ruoli_utente_guc(text, text);

-- Function: spid.get_lista_ruoli_utente_guc(text, text)

-- DROP FUNCTION spid.get_lista_ruoli_utente_guc(text, text);

CREATE OR REPLACE FUNCTION spid.get_lista_ruoli_utente_guc(
    IN _cf text,
    IN _endpoint text)
  RETURNS TABLE(id_utente integer, username text, id_ruolo integer, id_guc_ruoli integer, ruolo text, id_asl integer, asl text, endpoint text, tipologia_utente text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
 
select
u.id, u.username::text, r.ruolo_integer, r.id, r.ruolo_string::text, u.asl_id, a.nome::text, r.endpoint::text, string_agg(sture.id_tipologia_utente::text,',')
from guc_utenti u
left join guc_ruoli r on u.id = r.id_utente and r.trashed is not true and r.data_scadenza is null and r.ruolo_integer > 0
left join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike _endpoint
left join asl a on a.id = u.asl_id
where (u.codice_fiscale is not null and u.codice_fiscale != '' and u.codice_fiscale ilike _cf ) and r.endpoint ilike _endpoint
group by u.id, u.username::text, r.ruolo_integer, r.id, r.ruolo_string::text, u.asl_id, a.nome::text, r.endpoint::text;
 END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION spid.get_lista_ruoli_utente_guc(text, text)
  OWNER TO postgres;
  
  -- GUC
  
  -- Function: spid.processa_richiesta_elimina(text, integer)

-- DROP FUNCTION spid.processa_richiesta_elimina(text, integer);

CREATE OR REPLACE FUNCTION spid.processa_richiesta_elimina(
    _numero_richiesta text,
    _id_utente integer)
  RETURNS text AS
$BODY$
declare
	
	esito_processa text;

	query_endpoint text;
	query text;

	output_gisa text;
	output_gisa_ext text;
	output_bdu text;
	output_vam text;
	output_digemon text;
	output_guc text;
	_codice_fiscale text;
	output_guc_gisa_ext text;
	output_guc_bdu text;
	output_guc_vam text;
	output_guc_digemon text;
	output_guc_gisa text;

	_id_guc_ruoli integer;
	_endpoint_guc_ruoli text;
	_id_utente_guc_ruoli integer;
	_id_ruolo_guc_ruoli integer;

	username_out text;
BEGIN
	output_gisa = '{}';
	output_gisa_ext = '{}';
	output_bdu = '{}' ;
	output_vam = '{}';
	output_digemon = '{}';
	output_guc = '{}';
	-- elimino utente prima in guc e poi nell'EP.
	_id_guc_ruoli := (select id_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_endpoint_guc_ruoli := (select endpoint_guc_ruoli from spid.get_lista_richieste(_numero_richiesta));
	_id_utente_guc_ruoli := (select id_utente from guc_ruoli where id = _id_guc_ruoli);
	_id_ruolo_guc_ruoli := (select ruolo_integer from guc_ruoli where id = _id_guc_ruoli);
	username_out := (select username from guc_utenti where id = _id_utente_guc_ruoli);
	
	_codice_fiscale := (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta));
	/*ep_gisa := (select coalesce(id_ruolo_gisa,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_gisa_ext := (select coalesce(id_ruolo_gisa_ext,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_bdu := (select coalesce(id_ruolo_bdu,-1) from spid.get_lista_richieste(_numero_richiesta));
	ep_vam := (select coalesce(id_ruolo_vam, -1) from spid.get_lista_richieste(_numero_richiesta));
	ep_digemon := (select coalesce(id_ruolo_digemon,-1) from spid.get_lista_richieste(_numero_richiesta));

	_ruolo_gisa := (select coalesce(ruolo_gisa,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_gisa_ext := (select coalesce(ruolo_gisa_ext,'')from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_bdu := (select coalesce(ruolo_bdu,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_vam := (select coalesce(ruolo_vam,'') from spid.get_lista_richieste(_numero_richiesta));
	_ruolo_digemon :=(select coalesce(ruolo_digemon,'') from spid.get_lista_richieste(_numero_richiesta));
*/

	if (_endpoint_guc_ruoli ilike 'gisa') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, username_out, '', now()::timestamp without time zone - interval '1 DAY',_id_ruolo_guc_ruoli, -1));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		  end if; -- fine ep_gisa

	if (_endpoint_guc_ruoli ilike 'gisa_ext') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		end if; -- fine ep_gisa_ext
        
	if (_endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		end if; -- fine ep_bdu
        
	if (_endpoint_guc_ruoli ilike 'vam') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		  end if; -- fine ep_vam

	if (_endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'digemon', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_digemon := (select * from spid.esegui_query(query_endpoint, 'digemon', _id_utente,'host=dbdigemonL dbname=digemon_u'));
			end if;	
		 end if; -- fine ep_digemon
       
	esito_processa = '{"EndPoint" : "GUC", "Risultato": '||output_guc||', "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';	
				   
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;
	

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when output_guc = 'OK' then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION spid.processa_richiesta_elimina(text, integer)
  OWNER TO postgres;

  -- GISA
  -- Function: public.check_vincoli_richieste(text, text, integer, text)

-- DROP FUNCTION public.check_vincoli_richieste(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.check_vincoli_richieste(
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
_msg := 'ERRORE GESTORI ACQUE. Esiste gia''�una richiesta per il gestore e il comune selezionati.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'API' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE API] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE API. Esiste gia''�una richiesta per il codice fiscale selezionato.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'TRASPORTATORI' or _tipologia_richiesta ilike 'DISTRIBUTORI' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE TRASP/DIST] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE TRASPORTATORI/DISTRIBUTORI. Esiste gia''� una richiesta per il codice fiscale e la tipologia selezionati.';
END IF;
END IF;

RAISE INFO '[CHECK VINCOLI RICHIESTE] Esito finale (Vuoto=OK): %', _msg;

return _msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.check_vincoli_richieste(text, text, integer, text)
  OWNER TO postgres;

-- Function: spid.get_lista_richieste(text)

-- DROP FUNCTION spid.get_lista_richieste(text);

CREATE OR REPLACE FUNCTION spid.get_lista_richieste(IN _numero_richiesta text DEFAULT NULL::text)
  RETURNS TABLE(id integer, id_tipologia_utente integer, tipologia_utente text, id_tipo_richiesta integer, tipo_richiesta text, cognome text, nome text, codice_fiscale text, email text, telefono text, id_ruolo_gisa integer, ruolo_gisa text, id_ruolo_bdu integer,
   ruolo_bdu text, id_ruolo_vam integer, ruolo_vam text, id_ruolo_gisa_ext integer, ruolo_gisa_ext text, id_ruolo_digemon integer, ruolo_digemon text, id_clinica_vam integer[], 
   clinica_vam text[], identificativo_ente text, piva_numregistrazione text, comune text, istat_comune text, nominativo_referente text, 
   ruolo_referente text, email_referente text, telefono_referente text, data_richiesta timestamp without time zone, codice_gisa text, indirizzo text, 
   id_gestore_acque integer, gestore_acque text, cap text, pec text, numero_richiesta text, esito_guc boolean, esito_gisa boolean, esito_gisa_ext boolean, 
   esito_bdu boolean, esito_vam boolean, esito_digemon boolean, data_esito timestamp without time zone, stato integer, 
   id_asl integer, asl text, provincia_ordine_vet text, numero_ordine_vet text, numero_decreto_prefettizio text, 
   scadenza_decreto_prefettizio text, numero_autorizzazione_regionale_vet text, id_tipologia_trasp_dist integer, 
   esito text, id_guc_ruoli integer, endpoint_guc_ruoli text, ruolo_guc_ruoli text,
   in_nucleo boolean, in_dpat boolean) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

SELECT r.id, r.id_tipologia_utente, tip.descr as tipologia_utente, r.id_tipo_richiesta, ric.descr as tipologia_richiesta, 
r.cognome::text, r.nome::text, r.codice_fiscale::text, r.email::text, r.telefono::text, r.id_ruolo_gisa::int, g.ruolo as ruolo_gisa, r.id_ruolo_bdu::int,
b.ruolo as ruolo_bdu, r.id_ruolo_vam::int, v.ruolo as ruolo_vam, r.id_ruolo_gisa_ext::int, ge.ruolo as ruolo_ext, r.id_ruolo_digemon::int, dg.ruolo as ruolo_digemon,
r.id_clinica_vam, 
--cli.nome_clinica, 
(select array_agg(nome_clinica)
   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			id_clinica, nome_clinica from dbi_get_cliniche_utente()')
t1(id_clinica integer, nome_clinica text) where id_clinica = ANY (r.id_clinica_vam)),
r.identificativo_ente::text, r.piva_numregistrazione::text, 
(select comuni.comune::text from comuni where codiceistatcomune::integer = (select case when length(r.comune)> 0 then r.comune::integer else -1 end)), coalesce(r.comune,'-1')::text as istat_comune, 
r.nominativo_referente::text,
r.ruolo_referente::text, r.email_referente::text, r.telefono_referente::text, r.data_richiesta, r.codice_gisa::text,
r.indirizzo::text, r.id_gestore_acque::integer, gac.nome, r.cap::text, r.pec::text, r.numero_richiesta::text,
es.esito_guc, es.esito_gisa, es.esito_gisa_ext, es.esito_bdu, es.esito_vam, es.esito_digemon, es.data_esito, es.stato,
r.id_asl, l.nome::text, r.provincia_ordine_vet::text, r.numero_ordine_vet::text, r.numero_decreto_prefettizio::text, r.scadenza_decreto_prefettizio::text,
r.numero_autorizzazione_regionale_vet::text, r.id_tipologia_trasp_dist, es.json_esito, r.id_guc_ruoli, gr.endpoint::text, gr.ruolo_string::text,
f.in_nucleo, f.in_dpat
from spid.spid_registrazioni r 
left join asl l on l.id = r.id_asl
join spid.spid_tipo_richiesta ric on ric.id = r.id_tipo_richiesta
join spid.spid_tipologia_utente tip on tip.id = r.id_tipologia_utente
left join spid.spid_registrazioni_esiti es on es.numero_richiesta = r.numero_richiesta and es.trashed_date is null
left join spid.spid_registrazioni_flag f on f.numero_richiesta = r.numero_richiesta and f.trashed_date is null
left join guc_ruoli gr on gr.id = r.id_guc_ruoli
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
--left join (
--select * 
--   FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
--			id_clinica, nome_clinica from dbi_get_cliniche_utente()') 
--t1(id_clinica integer, nome_clinica text)
--) cli on cli.id_clinica::text = r.id_clinica_vam
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
