

  -- GISA
  

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
_msg := 'ERRORE GESTORI ACQUE. Esiste gia'' una richiesta per il gestore e il comune selezionati.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'API' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE API] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE API. Esiste gia'' una richiesta per il codice fiscale selezionato.';
END IF;
END IF;

IF _tipologia_richiesta ilike 'TRASPORTATORI' or _tipologia_richiesta ilike 'DISTRIBUTORI' THEN
select id into _idRichiesta from log_user_reg where codice_fiscale = _codice_fiscale and tipologia_richiesta = _tipologia_richiesta;
RAISE INFO '[CHECK VINCOLI RICHIESTE TRASP/DIST] Check _idRichiesta: %', _idRichiesta;
IF _idRichiesta >0 THEN
_msg := 'ERRORE TRASPORTATORI/DISTRIBUTORI. Esiste gia''  una richiesta per il codice fiscale e la tipologia selezionati.';
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
-- DB VAM

-- Function: public.dbi_elimina_utente(text, timestamp without time zone)

-- DROP FUNCTION public.dbi_elimina_utente(text, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idSuperUtente int;
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

select us.id into idSuperUtente from utenti_super_ us 
where us.username = _username and us.trashed_date is null and us.enabled and us.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idSuperUtente %', idSuperUtente;
	
	IF (idSuperUtente is null or idSuperUtente < 0) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE

	UPDATE utenti_super_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note = concat_ws(';', note, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE id = idSuperUtente;

	UPDATE utenti_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note_internal_use_only_hd = concat_ws(';', note_internal_use_only_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE superutente = idSuperUtente;
	
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;